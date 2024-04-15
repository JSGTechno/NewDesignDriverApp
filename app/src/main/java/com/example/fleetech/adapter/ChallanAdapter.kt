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
import com.example.fleetech.retrofit.response.JMyChallanList
import com.example.fleetech.retrofit.response.JMySettlementList
import com.example.fleetech.retrofit.response.JMyTrip
import com.example.fleetech.util.PdfClick


class ChallanAdapter(private val mList: List<JMyChallanList>):RecyclerView.Adapter<ChallanAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_challan_layout, parent, false)

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

            // sets the text to the textview from our itemHolder class
            holder.Statustv.text = UpdateDataList.Status
            holder.amtTv.text = UpdateDataList.Amount.toString()
            holder.ChallanLocTv.text= UpdateDataList.ChallLocation
            holder.ChallanTypeTv.text= UpdateDataList.ChallanType




        }
        holder.sNoTv.text= (position+1).toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val Statustv: TextView = itemView.findViewById(R.id.Statustv)
        val amtTv:TextView= itemView.findViewById(R.id.amtTv)
        val ChallanLocTv:TextView= itemView.findViewById(R.id.ChallanLocTv)
        val ChallanTypeTv:TextView= itemView.findViewById(R.id.ChallanTypeTv)
        val sNoTv:TextView= itemView.findViewById(R.id.sNoTv)


    }
}