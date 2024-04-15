package com.example.fleetech.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.retrofit.response.JDocument
import com.example.fleetech.retrofit.response.JMySettlementList
import com.example.fleetech.retrofit.response.JMyTrip
import com.example.fleetech.util.PdfClick


class SettlementAdapter(private val mList: List<JMySettlementList>, var pdfClick: PdfClick):RecyclerView.Adapter<SettlementAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_settlement_layout, parent, false)

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
            holder.vehicleNoTv.text = UpdateDataList.VehicleNo.toString()

            // sets the text to the textview from our itemHolder class
            holder.SettlementDateTv.text = UpdateDataList.SettlementDate
            holder.settlement_amt.text = UpdateDataList.SettlementAmount.toString()
            holder.incentive_tv.text= UpdateDataList.Incentive.toString()
           // holder.url_tv.text= UpdateDataList.SettleURL

            if(!UpdateDataList.SettleURL.isEmpty()){
                holder.url_tv.visibility = View.VISIBLE
            }else{
                holder.url_tv.visibility = View.INVISIBLE

            }
            holder.url_tv.setOnClickListener {

                pdfClick.pdfCLick(position,UpdateDataList.SettleURL)
            }




        }
        holder.sNoTv.text= (position+1).toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val vehicleNoTv: TextView = itemView.findViewById(R.id.vehicleNo_tv)
        val SettlementDateTv: TextView = itemView.findViewById(R.id.SettlementDateTv)
        val settlement_amt:TextView= itemView.findViewById(R.id.settlement_amt)
        val incentive_tv:TextView= itemView.findViewById(R.id.incentive_tv)
        val url_tv:ImageView= itemView.findViewById(R.id.url_tv)
        val sNoTv:TextView= itemView.findViewById(R.id.sNoTv)


    }
}