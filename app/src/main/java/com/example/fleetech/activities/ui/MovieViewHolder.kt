package com.example.fleetech.activities.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var mSrNo: TextView? = null
    private var mDate: TextView? = null
    private var mOrderId: TextView? = null
    private var mDesination: TextView? = null
    private var incentive: TextView? = null


    init {
        mSrNo = itemView.findViewById(R.id.srNo)
        mDate = itemView.findViewById(R.id.podsubmit)
        mOrderId = itemView.findViewById(R.id.orderId)

        mDesination = itemView.findViewById(R.id.destination)
        incentive = itemView.findViewById(R.id.paymentType)
    }

    fun bind(movie: MyTrip) {
        mOrderId?.text = movie.orderId
        mDate?.text = movie.valpodsubmit
        mDesination?.text = movie.destination
        incentive?.text = movie.startEnd
        mSrNo?.text = movie.sNo
    }

}