package com.example.fleetech.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetech.R
import com.example.fleetech.retrofit.response.JDocument
import com.example.fleetech.retrofit.response.JMyPayment
import com.example.fleetech.util.PaymentClick
import com.example.fleetech.util.PdfClick


//class PaymentAdapter(private val mList: List<JMyPayment>, var pdfClick: PaymentClick):RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
//
//    // create new views
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        // inflates the card_view_design view
//        // that is used to hold list item
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_list_item, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    // binds the list items to a view
//    @SuppressLint("SetTextI18n")
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (mList.size > 0) {
//            val UpdateDataList = mList[position]
//
//            // sets the image to the imageview from our itemHolder class
//            holder.name.text = UpdateDataList.DocName
//
//            // sets the text to the textview from our itemHolder class
//            holder.vehicleNo.text = UpdateDataList.DocNo
//            holder.login_dt.text = UpdateDataList.DocID.toString()
//            holder.expiryDate.text= UpdateDataList.ExpiryDate
//            holder.status.setOnClickListener {
//                pdfClick.pdfCLick(position,UpdateDataList.DocURL)
//            }
//
//        }
//        holder.sNoTv.text= (position+1).toString()
//
//    }
//
//    // return the number of the items in the list
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//
//    // Holds the views for adding it to image and text
//    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val name: TextView = itemView.findViewById(R.id.driverName)
//        val vehicleNo: TextView = itemView.findViewById(R.id.vehicleNotv)
//        val login_dt : TextView =itemView.findViewById(R.id.login_dt)
//        val sNoTv: TextView = itemView.findViewById(R.id.sNoTv)
//        val status: TextView = itemView.findViewById(R.id.status)
//        val expiryDate:TextView= itemView.findViewById(R.id.expiry_dt)
//    }
//}