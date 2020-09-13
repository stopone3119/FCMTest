package com.example.fcm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

class MainActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId // 현재 등록 토큰 검색
            // 안드로이드 앱이 서버로부터 FCM푸시 메세지를 받기 위해서는 기기 등록 토큰이 필요.
                // 이것을 서버가 알고 있어야 해당 기기에 푸시 메세지를 보낼수 있음
                // deviceID가 토큰값으로?
            .addOnCompleteListener(OnCompleteListener<InstanceIdResult>(){
                if(!it.isSuccessful){
                    Log.d("Exception -> ", it.exception.toString())
                    return@OnCompleteListener
                }
                val token : String = it.result!!.token
                Log.d("토큰", token)
                Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
            })
    }
}