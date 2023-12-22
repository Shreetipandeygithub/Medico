package com.shreeti.medico.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shreeti.medico.Model.TaskManagementModel
import com.shreeti.medico.R

class TaskManagementAdapter(
    private val mList:ArrayList<TaskManagementModel>
    ):RecyclerView.Adapter<TaskManagementAdapter.ViewHolder>(){


    private lateinit var myListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        myListener=clickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.each_task_recyclerview,parent,false)
        return ViewHolder(itemView,myListener)
    }



    override fun onBindViewHolder(holder: TaskManagementAdapter.ViewHolder, position: Int) {
        val currentTask=mList[position]
        holder.tvDrugName.text=currentTask.drugName
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView:View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val tvDrugName:TextView=itemView.findViewById(R.id.tvDrugName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}