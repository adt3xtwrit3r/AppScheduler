package com.mubin.appscheduler.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mubin.appscheduler.api.models.IssueModel
import com.mubin.appscheduler.api.models.app_model.AppTable
import com.mubin.appscheduler.databinding.ItemViewEachAppBinding
import com.mubin.appscheduler.databinding.ItemViewEachIssueBinding
import com.mubin.appscheduler.databinding.ItemViewEachScheduledAppBinding
import com.mubin.appscheduler.helper.Constants
import com.mubin.appscheduler.helper.base64ToImage
import java.text.SimpleDateFormat
import java.util.*

class IssueAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<IssueModel> = mutableListOf()

    var invokeIssue:((modeL: IssueModel)->Unit)? = null

    private val sdf2 = SimpleDateFormat("h:mm:ss a", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemViewEachIssueBinding = ItemViewEachIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewModel){
            val model = dataList[position]
            val binding = holder.binding

            Glide.with(binding.productImage).load(model.IssueImage).into(binding.productImage)
            binding.productName.text = model.IssueName

        }
    }

    override fun getItemCount(): Int = dataList.size

    internal inner class ViewModel(val binding: ItemViewEachIssueBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                invokeIssue?.invoke(dataList[adapterPosition])
            }
        }
    }

    fun initLoad(list: MutableList<IssueModel>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        dataList.clear()
        notifyDataSetChanged()
    }
}