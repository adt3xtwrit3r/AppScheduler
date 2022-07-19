package com.mubin.appscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubin.appscheduler.R
import com.mubin.appscheduler.api.models.IssueModel
import com.mubin.appscheduler.databinding.FragmentHomeBinding
import com.mubin.appscheduler.ui.HomeCommunicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    private val appAdapter: IssueAdapter = IssueAdapter()


    private lateinit var communicator: HomeCommunicator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        communicator = activity as HomeCommunicator
        communicator.initToolbarToggle()
        initView()

    } // this method containing some functions which are called when the View is created

    private fun initView() {

        val list: MutableList<IssueModel> = mutableListOf()

        list.add(IssueModel(R.drawable.ic_road,"Road"))
        list.add(IssueModel(R.drawable.ic_mosquito,"Mosquito"))
        list.add(IssueModel(R.drawable.ic_garbage,"Garbage"))
        list.add(IssueModel(R.drawable.ic_street_light,"Street Light"))
        list.add(IssueModel(R.drawable.ic_public_toilet,"Public Toilet"))
        list.add(IssueModel(R.drawable.ic_drainage,"Drainage"))
        list.add(IssueModel(R.drawable.ic_illegal_structure,"Illegal Structure"))
        list.add(IssueModel(R.drawable.ic_watering,"Watering"))

        binding?.recycleView?.let { view ->
            with(view) {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = appAdapter
                itemAnimator = null
            }
        }

        appAdapter.initLoad(list)

        appAdapter.invokeIssue = {
            findNavController().navigate(R.id.action_homeFragment_to_submitIssueFragment)
        }

    } // initializing views





    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}