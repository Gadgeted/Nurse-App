package com.fire1.nurseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.fire1.nurseapplication.constants.Constants
import com.fire1.nurseapplication.helper.ApiHelper
import com.fire1.nurseapplication.helper.PrefsHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val current_pass = findViewById<TextInputEditText>(R.id.current_pass)
        val new_pass = findViewById<TextInputEditText>(R.id.new_pass)
        val confirm_pass = findViewById<TextInputEditText>(R.id.confirm_pass)
        val update_pass = findViewById<MaterialButton>(R.id.update_pass)
        update_pass.setOnClickListener {
            val api = Constants.BASE_URL + "/change_pass"
            val helper = ApiHelper(applicationContext)
            val body = JSONObject()
            body.put("nurse_id", "2")
            body.put("current_password", current_pass.text.toString())
            body.put("new_password", new_pass.text.toString())
            body.put("confirm_password", confirm_pass.text.toString())
            helper.post(api, body, object : ApiHelper.CallBack {
                override fun onSuccess(result: JSONArray?) {
                }

//                override fun onSuccess(result: JSONObject?) {
//                    Toast.makeText(
//                        applicationContext, result.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                }

                //user: bob101
                //pass: Qwerty1234
                override fun onSuccess(result: JSONObject?) {
                    Toast.makeText(
                        applicationContext, result.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    //Consume the JSON - access keys
                    if (result!!.has("refresh_token")) {
                        val refresh_token = result.getString("refresh_token")
                        val access_token = result.getString("access_token")
                        val message = result.getString("message")
                        Toast.makeText(
                            applicationContext, "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        PrefsHelper.savePrefs(
                            applicationContext,
                            "refresh_token", refresh_token
                        )

                        PrefsHelper.savePrefs(
                            applicationContext,
                            "access_token", access_token
                        )

                        PrefsHelper.savePrefs(
                            applicationContext,
                            "userObject", message
                        )

                        //Proceed to Homepage
                    } else {
                        Toast.makeText(
                            applicationContext, result.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(
                        applicationContext, "Error:" + result.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("failurerrors", result.toString())
                }

            });
            //

        }
    }
}