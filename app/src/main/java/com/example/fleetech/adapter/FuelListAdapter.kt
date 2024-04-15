package com.example.fleetech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R

import com.example.fleetech.retrofit.response.FuelDetail


class FuelListAdapter(private val mListFuel: List<FuelDetail>):RecyclerView.Adapter<FuelListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fuel_expanse_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mListFuel.size > 0) {
            val UpdateDataList22 = mListFuel[position]

            // sets the image to the imageview from our itemHolder class
            holder.odidTv.text = UpdateDataList22.OrderID.toString()

            // sets the text to the textview from our itemHolder class

            holder.FillDateTv.text = UpdateDataList22.FillDate
            holder.fuelFillTv.text = UpdateDataList22.FuelFill
            holder.FuelPumpTv.text = UpdateDataList22.FuelPump



        }
        holder.sNoTv.text = (position + 1).toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mListFuel.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val odidTv: TextView = itemView.findViewById(R.id.odidTv)
        val sNoTv: TextView = itemView.findViewById(R.id.sNoTv)
        val fuelFillTv: TextView = itemView.findViewById(R.id.fuelFillTv)
        val FillDateTv: TextView = itemView.findViewById(R.id.FillDateTv)
        val FuelPumpTv: TextView = itemView.findViewById(R.id.FuelPumpTv)


    }
}