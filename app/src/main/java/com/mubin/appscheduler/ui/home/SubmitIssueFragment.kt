package com.mubin.appscheduler.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mubin.appscheduler.R
import com.mubin.appscheduler.databinding.FragmentSubmitIssueBinding


class SubmitIssueFragment : Fragment() {

    private var binding: FragmentSubmitIssueBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentSubmitIssueBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.submitBtn?.setOnClickListener {
            Toast.makeText(requireContext(), "Your issue has been submitted", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_submitIssueFragment_to_homeFragment)
        }

    }

}