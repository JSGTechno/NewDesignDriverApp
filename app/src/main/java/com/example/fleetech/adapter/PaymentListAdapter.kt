package com.example.fleetech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.activities.ui.MovieViewHolder
import com.example.fleetech.activities.ui.MyTrip
import com.example.fleetech.adapter.viewholder.IncentiveViewolder
import com.example.fleetech.adapter.viewholder.PaymentViewolder
import com.example.fleetech.retrofit.model.Payment
import com.example.fleetech.retrofit.response.JMyTrip
import com.example.fleetech.retrofit.response.PmtDetail

class PaymentListAdapter(private val mList: List<PmtDetail>):RecyclerView.Adapter<PaymentListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.trip_expanses_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList.size > 0) {
            val UpdateDataList = mList[position]

            // sets the image to the imageview from our itemHolder class
            holder.odidTv.text = UpdateDataList.OrderID.toString()

            // sets the text to the textview from our itemHolder class

            holder.utrNoTv.text = UpdateDataList.UTRNo
            holder.amountTv.text = UpdateDataList.Amount
            holder.pmtDateTv.text = UpdateDataList.PmtDate
            holder.PmtType.text = UpdateDataList.PmtType



        }
        holder.sNoTv.text = (position + 1).toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val odidTv: TextView = itemView.findViewById(R.id.odidTv)
        val sNoTv: TextView = itemView.findViewById(R.id.sNoTv)
        val utrNoTv: TextView = itemView.findViewById(R.id.utr_no_tv)
        val pmtDateTv: TextView = itemView.findViewById(R.id.PmtDate)
        val amountTv: TextView = itemView.findViewById(R.id.Amount_tv)
        val PmtType: TextView = itemView.findViewById(R.id.PmtType)

    }
}