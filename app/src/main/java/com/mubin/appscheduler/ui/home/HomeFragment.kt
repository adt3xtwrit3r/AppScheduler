package com.mubin.appscheduler.ui.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mubin.appscheduler.api.models.app_model.AppTable
import com.mubin.appscheduler.broadcast_receiver.OpenAppBroadcast
import com.mubin.appscheduler.databinding.FragmentHomeBinding
import com.mubin.appscheduler.helper.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    private val viewModel: HomeViewModel by viewModels()

    private val appAdapter: AppAdapter = AppAdapter()

    private var installedAppsList: MutableList<AppTable> = mutableListOf()

    private var selectedTimeStamp: Long = 0L

    private var selectedTab = 0

    private var showSystemApp = false

    private val sdf2 = SimpleDateFormat("h:mm:ss a", Locale.US)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initView()
        initTabLayout()
        initClickListener()
        fetchAllInstalledApps()

    } // this method containing some functions which are called when the View is created

    private fun initView() {

        binding?.recycleView?.let { view ->
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = appAdapter
                itemAnimator = null
            }
        }

        binding?.toggleSystemApps?.isEnabled = true

    } // initializing views

    private fun initTabLayout() {

        if (selectedTab == 0){
            binding?.tabLayout?.selectTab(binding?.tabLayout?.getTabAt(0))
        } else if (selectedTab == 1){
            binding?.tabLayout?.selectTab(binding?.tabLayout?.getTabAt(1))
        }

        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        selectedTab = 0
                        binding?.toggleSystemApps?.isEnabled = true
                        binding?.toggleSystemApps?.visibility = View.VISIBLE
                        fetchAllInstalledApps()
                    }

                    1 -> {
                        selectedTab = 1
                        binding?.toggleSystemApps?.isEnabled = false
                        fetchScheduledApps()
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding?.toggleSystemApps?.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                showSystemApp = true
                fetchAllInstalledApps()
            } else {
                showSystemApp = false
                fetchAllInstalledApps()
            }

        }
    } //initializing tabLayout

    private fun initClickListener() {

        binding?.swipeRefresh?.setOnRefreshListener {
            binding?.swipeRefresh?.isRefreshing = true
            if (selectedTab == 0) {
                fetchAllInstalledApps()
            } else if (selectedTab == 1) {
                fetchScheduledApps()
            }
        }

        appAdapter.invokeApp = { AppInfo ->
            checkIfAlreadyScheduled(AppInfo)
        }

        appAdapter.invokeEditSchedule = { AppInfo ->
            timePicker(AppInfo, Constants.FLAG_UPDATE)
        }

        appAdapter.invokeDeleteSchedule = { AppInfo ->
            alert("Confirmation", "Do you want to delete this Schedule?", true, "Okay", "Cancel") {
                if (it == AlertDialog.BUTTON_POSITIVE) {
                    cancelSchedule(AppInfo)
                }
            }.show()
        }
    } //handling click listeners

    @SuppressLint("LogNotTimber") //using Log instead of Timber as timber doesn't catch Long data type
    private fun checkIfAlreadyScheduled(AppInfo: AppTable) {
        val progressDialog = progressDialog()
        progressDialog.setCancelable(false)
        progressDialog.show()
        viewModel.getAppInfoFromPackageName(AppInfo.packageName ?:"").observe(viewLifecycleOwner, androidx.lifecycle.Observer { appInfoDb ->
            Log.d("ScheduledAppsId", "$appInfoDb")
            progressDialog.dismiss()
            if (appInfoDb != null) {
                if (appInfoDb.packageName == AppInfo.packageName){

                    alert ("Confirmation", "App is already scheduled\nat ${sdf2.format(appInfoDb.appSchedule)}.\nDo you want to reschedule ?", true, "Okay", "Cancel" ) {

                        if (it == AlertDialog.BUTTON_POSITIVE) {
                            timePicker(appInfoDb, Constants.FLAG_UPDATE)
                        }

                    }.show()

                }
            } else {

                timePicker(AppInfo, Constants.FLAG_NEW)

            }


        })

    } // Check Local Database if the app is already scheduled for launch

    private fun timePicker(appInfo: AppTable, flag: String) {
        val calender = Calendar.getInstance()
        TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            calender.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            selectedTimeStamp = calender.timeInMillis
            //selectedHour24 = "$hourOfDay:$minute"
            initAlarm(selectedTimeStamp, appInfo, flag)
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false)
            .show()
    } // for picking time

    @SuppressLint("UnspecifiedImmutableFlag") //handled with version check
    private fun initAlarm(selectedTimeStamp: Long, appInfo: AppTable, flag: String) {

        val intent = Intent(requireActivity(), OpenAppBroadcast::class.java).apply {
            putExtra("package", "${appInfo.packageName}")
        }
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), appInfo.id, intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmManager: AlarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, selectedTimeStamp, AlarmManager.INTERVAL_DAY, pendingIntent)

        when(flag) {
            Constants.FLAG_NEW -> {
                addToDb(selectedTimeStamp, appInfo)
            }

            Constants.FLAG_UPDATE -> {
                updateSchedule(selectedTimeStamp, appInfo)
            }
        }


    } // initializing broadcast receiver with alarm manager and pending intent

    private fun addToDb(selectedTimeStamp: Long, appInfo: AppTable) {
        val progressDialog = progressDialog()
        progressDialog.setCancelable(false)
        progressDialog.show()
        appInfo.appSchedule = selectedTimeStamp
        appInfo.appScheduled = true
        viewModel.scheduleApp(appInfo).observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            progressDialog.dismiss()
            if (response!=null) {
                context?.toast("App is Scheduled", Toast.LENGTH_LONG)
            }
        })
    } // Adding Schedule app info to Room Local Storage

    private fun updateSchedule(selectedTimeStamp: Long, appInfo: AppTable) {

        val progressDialog = progressDialog()
        progressDialog.setCancelable(false)
        progressDialog.show()

        appInfo.appSchedule = selectedTimeStamp

        viewModel.updateSchedule(appInfo).observe(viewLifecycleOwner, androidx.lifecycle.Observer { int ->

            progressDialog.dismiss()

            if (int == 1) {
                context?.toast("App is Scheduled", Toast.LENGTH_LONG)
                if (selectedTab == 1) {
                    fetchScheduledApps()
                }
            } else {
                context?.toast("Failed", Toast.LENGTH_LONG)
            }

        })

    } // Updating Scheduled app info on Room Local Storage

    @SuppressLint("SetTextI18n") //doesn't create that much problem
    private fun fetchAllInstalledApps() {
        val progressDialog = progressDialog()
        progressDialog.setCancelable(false)
        progressDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            installedAppsList.clear()
            populateDataList()
            progressDialog.dismiss()
            binding?.swipeRefresh?.isRefreshing = false
            if (installedAppsList.size == 0){
                binding?.emptyView?.visibility = View.VISIBLE
                binding?.totalAppCount?.text = ""
                appAdapter.clearAdapter()
            } else {
                binding?.emptyView?.visibility = View.GONE
                installedAppsList.sortBy { it.appName?.capitalized() }
                binding?.totalAppCount?.text = "Total Installed Apps: ${installedAppsList.size}"
                appAdapter.initLoad(installedAppsList, 0)
            }

        }, 500) // used handler to postpone data load to adapter as it takes takes a while to fetch installed app data

    } // fetching all installed app info

    @SuppressLint("QueryPermissionsNeeded") //should ask for manual permission on lower android os versions
    private fun populateDataList() {
        val apps = requireActivity().packageManager.getInstalledPackages(0)
        for (i in apps.indices) {
            val p = apps[i]
            if (showSystemApp){
                val appName = p.applicationInfo.loadLabel(requireActivity().packageManager).toString()
                val icon = p.applicationInfo.loadIcon(requireActivity().packageManager)
                //drawable to base64
                val appIcon64 = encodeToBase64(getBitmapFromDrawable(icon), Bitmap.CompressFormat.PNG, 100)
                val packageName = p.applicationInfo.packageName
                installedAppsList.add(AppTable(appName = appName, packageName =  packageName, appIcon = appIcon64))
            } else {
                if (!isSystemPackage(p)) {
                    val appName = p.applicationInfo.loadLabel(requireActivity().packageManager).toString()
                    val icon = p.applicationInfo.loadIcon(requireActivity().packageManager)
                    //drawable to base64
                    val appIcon64 = encodeToBase64(getBitmapFromDrawable(icon), Bitmap.CompressFormat.PNG, 100)
                    val packageName = p.applicationInfo.packageName
                    installedAppsList.add(AppTable(appName = appName, packageName =  packageName, appIcon = appIcon64))
                }
            }
        }
    } // insert app info into the data list to view on adapter

    @SuppressLint("LogNotTimber")
    private fun fetchScheduledApps() {

        viewModel.getAllScheduledApps().observe(viewLifecycleOwner, androidx.lifecycle.Observer { ScheduledAppList ->

            binding?.swipeRefresh?.isRefreshing = false

            if (ScheduledAppList.isEmpty()) {
                binding?.toggleSystemApps?.visibility = View.GONE
                binding?.emptyView?.visibility = View.VISIBLE
                binding?.totalAppCount?.text = ""
                appAdapter.clearAdapter()
            } else {
                appAdapter.initLoad(ScheduledAppList, 1)
                Log.d("ScheduledApps", "$ScheduledAppList")
                binding?.totalAppCount?.text = "Total Scheduled Apps: ${ScheduledAppList.size}"
            }


        })

    } // getting scheduled app info from Local Storage

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun cancelSchedule(appInfo: AppTable) {
        val intent = Intent(requireContext(), OpenAppBroadcast::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(requireContext(), appInfo.id, intent, PendingIntent.FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getBroadcast(requireContext(), appInfo.id, intent, FLAG_UPDATE_CURRENT)
        }
        val alarmManager: AlarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            deleteFromDb(appInfo)
        }
    } // canceling alarm manager

    private fun deleteFromDb(appInfo: AppTable) {

        val progressDialog = progressDialog()
        progressDialog.setCancelable(false)
        progressDialog.show()

        viewModel.deleteSchedule(appInfo.id).observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            progressDialog.dismiss()
            if (it == 1) {
                context?.toast("App schedule deleted successfully.")
                fetchScheduledApps()
            }

        })

    } // delete scheduled app info from Local Storage on Alarm Manager cancellation


    private fun String.capitalized(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    } // for capitalizing the app names


    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    } // check is system app

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}