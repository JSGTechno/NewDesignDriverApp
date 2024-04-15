package com.example.fleetech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.retrofit.response.UpdateData
import com.example.fleetech.util.AdminClickInterface


class AdminAdapter(
    private val mList: ArrayList<UpdateData>,
    val adminInterFace: AdminClickInterface


    ) :
    RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList.size > 0) {
            val UpdateDataList = mList[position]

            if (position % 2 == 0) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.bg_color))
            }

            // sets the image to the imageview from our itemHolder class
            holder.name.text = UpdateDataList.DriverName

            // sets the text to the textview from our itemHolder class
            holder.vehicleNo.text = UpdateDataList.RegNo
            holder.mobileNo.text = UpdateDataList.MobileNo
            holder.login_dt.text = UpdateDataList.LoginDt
            holder.login_tv.text = UpdateDataList.LoginTime
            holder.status.text= UpdateDataList.VehStatus
            holder.vehicleStatus.text= UpdateDataList.VehSubStatus


        }
        holder.sNoTv.text= (position+1).toString()

        holder.itemView.setOnClickListener {
            adminInterFace.onDriverClick(mList.get(position).MobileNo, mList.get(position).Pwd,mList.get(position).RegNo,mList.get(position).DriverName)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.driverName)
        val vehicleNo: TextView = itemView.findViewById(R.id.vehicleNotv)
        val login_tv:TextView= itemView.findViewById(R.id.login_tv)
        val login_dt :TextView=itemView.findViewById(R.id.login_dt)
        val mobileNo :TextView= itemView.findViewById(R.id.mobile_vvo)
        val sNoTv:TextView= itemView.findViewById(R.id.sNoTv)
        val status:TextView= itemView.findViewById(R.id.status)
        val vehicleStatus:TextView= itemView.findViewById(R.id.vehicle_status)
    }
}