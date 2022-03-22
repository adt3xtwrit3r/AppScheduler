package com.mubin.appscheduler.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mubin.appscheduler.api.models.app_model.AppTable
import com.mubin.appscheduler.databinding.ItemViewEachAppBinding
import com.mubin.appscheduler.databinding.ItemViewEachScheduledAppBinding
import com.mubin.appscheduler.helper.Constants
import com.mubin.appscheduler.helper.base64ToImage
import java.text.SimpleDateFormat
import java.util.*

class AppAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<AppTable> = mutableListOf()
    private var viewType: Int = 0

    var invokeApp:((modeL: AppTable)->Unit)? = null
    var invokeEditSchedule:((modeL: AppTable)->Unit)? = null
    var invokeDeleteSchedule:((modeL: AppTable)->Unit)? = null

    private val sdf2 = SimpleDateFormat("h:mm:ss a", Locale.US)

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0){
            val binding: ItemViewEachAppBinding = ItemViewEachAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewModel(binding)
        } else {
            val binding: ItemViewEachScheduledAppBinding = ItemViewEachScheduledAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewModelScheduled(binding)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewModel){
            val model = dataList[position]
            val binding = holder.binding

            Glide.with(binding.appIcon).load(base64ToImage(model.appIcon ?:Constants.placeholder64)).into(binding.appIcon)
            binding.appName.text = model.appName
            binding.appPackage.text = model.packageName

        } else if (holder is ViewModelScheduled) {
            val model = dataList[position]
            val binding = holder.binding

            Glide.with(binding.appIcon).load(base64ToImage(model.appIcon ?:Constants.placeholder64)).into(binding.appIcon)
            binding.appName.text = model.appName
            binding.appSchedule.text = "Launch scheduled at ${sdf2.format(model.appSchedule)}"
        }
    }

    override fun getItemCount(): Int = dataList.size

    internal inner class ViewModel(val binding: ItemViewEachAppBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                invokeApp?.invoke(dataList[adapterPosition])
            }

            binding.addSchedule.setOnClickListener {
                invokeApp?.invoke(dataList[adapterPosition])
            }
        }
    }

    internal inner class ViewModelScheduled(val binding: ItemViewEachScheduledAppBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.editSchedule.setOnClickListener {
                invokeEditSchedule?.invoke(dataList[adapterPosition])
            }

            binding.deleteSchedule.setOnClickListener{
                invokeDeleteSchedule?.invoke(dataList[adapterPosition])
            }
        }
    }

    fun initLoad(list: List<AppTable>, viewType: Int) {
        dataList.clear()
        dataList.addAll(list)
        this.viewType = viewType
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        dataList.clear()
        notifyDataSetChanged()
    }
}