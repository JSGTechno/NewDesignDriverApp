package com.example.fleetech.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.activities.ui.MyTrip
import com.example.fleetech.retrofit.model.Payment

class PaymentViewolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.payment_list_item, parent, false)) {
    private var mSrNo: TextView? = null
    private var mDate: TextView? = null
    private var mOrderId: TextView? = null
    private var mDesination: TextView? = null
    private var mPmttype: TextView? = null
    private var mAmount: TextView? = null
    private var mUTRNo: TextView? = null



    init {
        mSrNo = itemView.findViewById(R.id.srNo)
        mDate = itemView.findViewById(R.id.dateTv)
        mOrderId = itemView.findViewById(R.id.orderId)

        mDesination = itemView.findViewById(R.id.destination)
        mPmttype = itemView.findViewById(R.id.paymentType)
        mAmount = itemView.findViewById(R.id.amount)
        mUTRNo = itemView.findViewById(R.id.utrno)

    }

    fun bind(movie: Payment) {
        mOrderId?.text = movie.orderId
        mPmttype?.text = movie.pmtType
        mDate?.text = movie.date
        mDesination?.text = movie.destination
        mAmount?.text = movie.amnount
        mUTRNo?.text = movie.utrNo
        mSrNo?.text = movie.srNo


    }
}
