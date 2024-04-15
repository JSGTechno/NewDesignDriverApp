package com.example.fleetech.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.retrofit.response.JDocument
import com.example.fleetech.retrofit.response.JMyTrip
import com.example.fleetech.util.PdfClick


class MyTripHistoryAdapter(private val mList: List<JMyTrip>):RecyclerView.Adapter<MyTripHistoryAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_history_layout, parent, false)

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
            holder.odidTv.text = UpdateDataList.OrderID.toString()

            // sets the text to the textview from our itemHolder class
            holder.dispatch_dt.text = UpdateDataList.DispatchDate
            holder.dispatch_time.text = UpdateDataList.DispatchTime
            holder.deliver_dt.text= UpdateDataList.DeliveryDate
            holder.delivertime.text= UpdateDataList.DeliveryTime
            holder.deliver_dt.text= UpdateDataList.DeliveryDate
            holder.source_tv.text= UpdateDataList.Source
            holder.destination_tv.text= UpdateDataList.Destination
            holder.podSubmit.text= UpdateDataList.PODSubmit




        }
        holder.sNoTv.text= (position+1).toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val odidTv: TextView = itemView.findViewById(R.id.odidTv)
        val source_tv: TextView = itemView.findViewById(R.id.source_tv)
        val destination_tv : TextView =itemView.findViewById(R.id.destination_tv)
        val dispatch_dt: TextView = itemView.findViewById(R.id.dispatch_dt)
        val dispatch_time: TextView = itemView.findViewById(R.id.dispatch_time)
        val deliver_dt:TextView= itemView.findViewById(R.id.deliver_dt)
        val delivertime:TextView= itemView.findViewById(R.id.delivertime)
        val podSubmit:TextView= itemView.findViewById(R.id.podSubmit)
        val sNoTv:TextView= itemView.findViewById(R.id.sNoTv)


    }
}