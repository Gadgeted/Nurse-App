package com.fire1.nurseapplication.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fire1.nurseapplication.InvoiceDetails

import com.fire1.nurseapplication.R
import com.fire1.nurseapplication.models.Nurse_Assignment
import com.google.android.material.textview.MaterialTextView

class ViewAssignmentsAdapter (var context: Context):
    RecyclerView.Adapter<ViewAssignmentsAdapter.ViewHolder>() {


    //Create a list and connect it with our model
    var itemList : List<Nurse_Assignment> = listOf() //Its empty

    //Create a class here, will hold our views in single_lab xml
    inner class ViewHolder(itemViews: View): RecyclerView.ViewHolder(itemViews)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAssignmentsAdapter.ViewHolder {
        //access/inflate the single_lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_view_assignment,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

//    override fun onBindViewHolder(holder: ViewAssignmentsAdapter.ViewHolder, position: Int) {
//        //Find your 3 text view
//        val  invoice_no = holder.itemView.findViewById<MaterialTextView>(R.id.invoice_no)
//        // Assume one Lab
//        val item = itemList[position]
//        invoice_no.text = item.invoice_no
//        holder.itemView.setOnClickListener {
//            val i = Intent(context, InvoiceDetails::class.java)
////            i.putExtra("invoice_no", item.invoice_no)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(i)
//        }
//
//    }

    override fun onBindViewHolder(holder: ViewAssignmentsAdapter.ViewHolder, position: Int) {
        val invoice_no = holder.itemView.findViewById<MaterialTextView>(R.id.invoice_no)
        val item = itemList[position]
        invoice_no.text = item.invoice_no
        holder.itemView.setOnClickListener {
            val i = Intent(context, InvoiceDetails::class.java)
            i.putExtra("INVOICE_NUMBER", item.invoice_no) // Pass invoice number as an extra
            if (context is Activity) {
                context.startActivity(i)
            } else {
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            }

        }
    }


    override fun getItemCount(): Int {
        return itemList.size // Count how many Items in the list

    }

    fun filterList(filterList: List<Nurse_Assignment>){
        itemList = filterList
        notifyDataSetChanged()
    }

    //Earlier we mentioned item List is Empty!
    // We will get data from our API, then bring it to below function
    fun setListItems(data: List<Nurse_Assignment>){
        itemList = data //map/link the data to itemList
        notifyDataSetChanged()
    //Tell this adapter class that now itemList i loaded with data
    }
}