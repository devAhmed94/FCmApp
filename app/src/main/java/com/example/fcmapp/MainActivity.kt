package com.example.fcmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fcmapp.databinding.ActivityMainBinding
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

const val TOPICS = "topics/myTopic"

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseService.sharePerf = getSharedPreferences("sharePref", MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            binding.etToken.setText(it.token)
        }



        binding.btnSend.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty() && binding.etMessage.text.toString()
                    .isNotEmpty() &&binding.etToken.text.toString().isNotEmpty()
            ) {

                sendNotification(PushNotification(
                    NotificationData(binding.etTitle.text.toString(), binding.etMessage.text.toString())
                    , binding.etToken.text.toString()))
            }
        }


    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response = RetrofitInstance.Api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d("TAG", "sendNotification 1: ${Gson().toJson(response.body().toString())}")
                } else {
                    Log.e("TAG", "sendNotification 2 : ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
                Log.e("TAG", "sendNotification 3 :${e.message.toString()} ")
            }

        }
}