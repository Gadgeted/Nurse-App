package com.fire1.nurseapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.fire1.nurseapplication.R
import com.fire1.nurseapplication.models.Invoive_Details
import com.fire1.nurseapplication.models.Nurse_Assignment
import com.google.android.material.textview.MaterialTextView

class InvoiceAdapter (var context: Context):
    RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {


    //Create a list and connect it with our model
    var itemList : List<Invoive_Details> = listOf() //Its empty

    //Create a class here, will hold our views in single_lab xml
    inner class ViewHolder(itemViews: View): RecyclerView.ViewHolder(itemViews)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceAdapter.ViewHolder {
        //access/inflate the single_lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_invoice_details,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: InvoiceAdapter.ViewHolder, position: Int) {
        //Find your 3 text view
        val  invoice_no = holder.itemView.findViewById<MaterialTextView>(R.id.invoice_no)
        val  booked_for = holder.itemView.findViewById<MaterialTextView>(R.id.booked_for)
        val  where_taken = holder.itemView.findViewById<MaterialTextView>(R.id.where_taken)
        val  status = holder.itemView.findViewById<MaterialTextView>(R.id.status)
        val  book_id = holder.itemView.findViewById<MaterialTextView>(R.id.book_id)
        val  appointment_time = holder.itemView.findViewById<MaterialTextView>(R.id.appointment_time)
        val  appointment_date = holder.itemView.findViewById<MaterialTextView>(R.id.appointment_date)

        booked_for.text = itemList[position].booked_for
        where_taken.text = itemList[position].where_taken
        status.text = itemList[position].status
        book_id.text = itemList[position].book_id.toString()
        appointment_time.text = itemList[position].appointment_time
        appointment_date.text = itemList[position].appointment_date
        // Assume one Lab
        val item = itemList[position]
        invoice_no.text = item.invoice_no
//        holder.itemView.setOnClickListener {
//            val i = Intent(context, SingleLabTest::class.java)
//            i.putExtra("lab_id",item.lab_id)
//            i.putExtra("test_id",item.test_id)
//            i.putExtra("test_discount",item.test_discount)
//            i.putExtra("test_cost",item.test_cost)
//            i.putExtra("test_name",item.test_name)
//            i.putExtra("test_description",item.test_description)
//            i.putExtra("availability",item.availability)
//            i.putExtra("more_info",item.more_info)
//            i.putExtra("reg_date",item.reg_date)

//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(i)
//        }

    }

    override fun getItemCount(): Int {
        return itemList.size // Count how many Items in the list

    }

    fun filterList(filterList: List<Invoive_Details>){
        itemList = filterList
        notifyDataSetChanged()
    }

    //Earlier we mentioned item List is Empty!
    // We will get data from our API, then bring it to below function
    fun setListItems(data: List<Invoive_Details>){
        itemList = data //map/link the data to itemList
        notifyDataSetChanged()
    //Tell this adapter class that now itemList i loaded with data
    }
}