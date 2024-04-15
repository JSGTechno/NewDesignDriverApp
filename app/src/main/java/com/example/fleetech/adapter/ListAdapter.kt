package com.example.fleetech.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.activities.ui.MovieViewHolder
import com.example.fleetech.activities.ui.MyTrip

class ListAdapter(private val list: List<MyTrip>)
    : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: MyTrip = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}