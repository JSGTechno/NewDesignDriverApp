package com.example.fleetech.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.activities.ui.MovieViewHolder
import com.example.fleetech.activities.ui.MyTrip
import com.example.fleetech.adapter.viewholder.IncentiveViewolder
import com.example.fleetech.adapter.viewholder.PaymentViewolder
import com.example.fleetech.retrofit.model.Incentive
import com.example.fleetech.retrofit.model.Payment

class IncentiveListAdapter(private val list: List<Incentive>)
    : RecyclerView.Adapter<IncentiveViewolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncentiveViewolder {
        val inflater = LayoutInflater.from(parent.context)
        return IncentiveViewolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: IncentiveViewolder, position: Int) {
        val movie: Incentive = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size
}