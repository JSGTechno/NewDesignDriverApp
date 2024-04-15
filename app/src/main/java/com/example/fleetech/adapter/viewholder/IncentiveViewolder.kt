package com.example.fleetech.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.activities.ui.MyTrip
import com.example.fleetech.retrofit.model.Incentive
import com.example.fleetech.retrofit.model.Payment

class IncentiveViewolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.incentive_list_item, parent, false)) {
    private var mSrNo: TextView? = null
    private var mDate: TextView? = null
    private var mOrderId: TextView? = null
    private var mDesination: TextView? = null
    private var incentive: TextView? = null



    init {
        mSrNo = itemView.findViewById(R.id.srNo)
        mDate = itemView.findViewById(R.id.dateTv)
        mOrderId = itemView.findViewById(R.id.orderId)

        mDesination = itemView.findViewById(R.id.destination)
        incentive = itemView.findViewById(R.id.incentive)


    }

    fun bind(movie: Incentive) {
        mOrderId?.text = movie.orderId
        mDate?.text = movie.date
        mDesination?.text = movie.destination
        incentive?.text = movie.incentive
        mSrNo?.text = movie.srNo


    }
}
