package com.example.fleetech.adapter

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.fleetech.retrofit.response.JMyOrder
import com.example.fleetech.util.DispatchClick
import com.example.fleetech.util.PdfClick
import com.example.fleetech.util.Session


class DispatchOrderListAdapter(
    private val mList: List<JMyOrder>,
    var pdfClick: DispatchClick,
    var requireContext: Context
) : RecyclerView.Adapter<DispatchOrderListAdapter.ViewHolder>() {
    lateinit var sessionManager: Session

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dispatch_doc_list, parent, false)
        sessionManager = Session(requireContext)

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
            holder.ODID.text = UpdateDataList.OrderID.toString()

            // sets the text to the textview from our itemHolder class
            holder.source_tv.text = UpdateDataList.Source
            holder.destination_tv.text = UpdateDataList.Destination
            holder.trip_status.text = UpdateDataList.TripStatus
            holder.order_type.text = UpdateDataList.OrderType


            holder.sNoTv.text = (position + 1).toString()

            if (UpdateDataList.ReportedFlag.equals("Y")) {
                holder.reported_flag.isEnabled = true
                //api it reported flag
            } else {
                holder.reported_flag.isEnabled = false
                holder.reported_flag.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                holder.reported_flag.text = UpdateDataList.ReportedFlag

            }
            if (UpdateDataList.PODFlag.equals("Y")) {
                sessionManager.keyPodtype = "PODTYPE"
                //open pod submit data here
                holder.POD_FLAG.isEnabled = true
            } else {
                holder.POD_FLAG.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                holder.POD_FLAG.isEnabled = false
                holder.POD_FLAG.text = UpdateDataList.PODFlag
            }
            holder.POD_FLAG.setOnClickListener {
                pdfClick.pdfCLick(position, "")


            }
            holder.reported_flag.setOnClickListener {
                pdfClick.reportedCLick(position, "")

            }

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ODID: TextView = itemView.findViewById(R.id.odidTv)
        val source_tv: TextView = itemView.findViewById(R.id.source_tv)
        val destination_tv: TextView = itemView.findViewById(R.id.destination_tv)
        val sNoTv: TextView = itemView.findViewById(R.id.sNoTv)
        val trip_status: TextView = itemView.findViewById(R.id.trip_status)
        val reported_flag: TextView = itemView.findViewById(R.id.reported_flag)
        val POD_FLAG: TextView = itemView.findViewById(R.id.POD_FLAG)
        val order_type: TextView = itemView.findViewById(R.id.order_type)


    }
}