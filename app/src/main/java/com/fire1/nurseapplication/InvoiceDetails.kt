package com.fire1.nurseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fire1.nurseapplication.adapters.InvoiceAdapter
import com.fire1.nurseapplication.constants.Constants
import com.fire1.nurseapplication.helper.ApiHelper
import com.fire1.nurseapplication.models.Invoive_Details
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class InvoiceDetails : AppCompatActivity() {
    lateinit var itemList: List<Invoive_Details>
    private lateinit var recyclerView: RecyclerView
    private lateinit var invoiceAdapter : InvoiceAdapter
    private lateinit var progress: ProgressBar
    private lateinit var swiperefresh: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_details)

        invoiceAdapter = InvoiceAdapter(this)
        recyclerView = findViewById(R.id.recycler)
        itemList  = listOf()
        progress = findViewById(R.id.progress)
        recyclerView.adapter  = invoiceAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        invoiceAdapter = InvoiceAdapter(applicationContext)

        post_fetch()

        swiperefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swiperefresh.setOnRefreshListener {
            post_fetch()// fetch data again
        }//end refresh

        //Filter labs
        val etsearch = findViewById<EditText>(R.id.etsearch)
        etsearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(texttyped: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(texttyped.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }//end oncreate

    fun post_fetch(){
        val api = Constants.BASE_URL+"/view_invoice_details"
        //Above APi needs a Body, So we have to build it
        val body = JSONObject()
        //Retrieve the id from Intent Extras
        val id = intent.extras?.getString("INVOICE_NUMBER")
        //Toast.makeText(applicationContext, "ID $id", Toast.LENGTH_SHORT).show()
        //provide the ID to the API
        body.put("invoice_no", id) //NB: 4 is static
        val helper = ApiHelper(applicationContext)
        helper.post(api, body, object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(),
                    Array<Invoive_Details>::class.java).toList()
                //Finally, our adapter has the data
                invoiceAdapter.setListItems(itemList)
                //For the sake of recycling/Looping items, add the adapter to recycler
                recyclerView.adapter = invoiceAdapter
                progress.visibility = View.GONE
                swiperefresh.isRefreshing = false
            }
            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(),
                    Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "Error:"+result.toString(),
                    Toast.LENGTH_SHORT).show()
                Log.d("failureerrors", result.toString())
            }
        })
    }//end fetch


    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Invoive_Details> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.invoice_no.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            invoiceAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            invoiceAdapter.filterList(filteredlist)
        }
    }
}