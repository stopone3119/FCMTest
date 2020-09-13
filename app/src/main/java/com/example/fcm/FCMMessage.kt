package com.example.fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// 이 Service는 파이어베이스 메세지를 받습니다.
class FCMMessage : FirebaseMessagingService() { // Service는 백그라운드에서 실행되는 동작
    override fun onNewToken(token : String){ // 앱 처음 깔렸을 때, 가져오는 기기의 토큰 값
        Log.d("FCM -> ", token)
    }

    @SuppressLint("WrongConstant")
    override fun onMessageReceived(remoteMessage: RemoteMessage) { // 수신된 메시지 기준으로 작업을 실행!
        super.onMessageReceived(remoteMessage)
        if(remoteMessage.notification != null){ // 받아온 메시지가 널이 아니면
            val message_body : String? = remoteMessage.notification!!.body // 내용 저장
            val message_title : String? = remoteMessage.notification!!.title //  제목 저장

            val channel_id : String = "Channel ID"
            val channel_name : String = "Channel Name"

           // val defalut_sound : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val intent = Intent(this, MainActivity::class.java) // 인텐트에 Activity정보(MainActivity)를 담음
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            // 노티 알림 클릭시 MainActivity로 이동 일회용으로 생성(최초 클릭에만 작동하고 그 다음 클릭부터는 작동X)
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            val noti_builder = NotificationCompat.Builder(this, channel_id) // 노티 객체 만들어줌(해당 들어온 메세지 기준)
                .setBadgeIconType(R.drawable.ic_stat_name)
                .setSmallIcon(R.drawable.ic_stat_name) // 제목 옆 아이콘
                .setContentTitle(message_title) // 제목
                .setContentText(message_body) // 내용
                .setAutoCancel(true) // 자동닫힘?
              //  .setSound(defalut_sound)
                .setContentIntent(pendingIntent) // 클릭이벤트 하려면 setContentIntent를 설정해줘야 함!

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // 오레오버전부터 채널을 만드어주지 안흥면 알림이 오지 않음.
                val noti_channel  = NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH)
                // 채널을 통해서 노티를 여려가지 용도로 나누어서 관리할 수 있음.
                // ex) 페이스북의 새 친구 알림, 새 게시글 알림, 친구추천 등등
                notificationManager.createNotificationChannel(noti_channel) // 채널 생성
            }
            notificationManager.notify(0, noti_builder.build()) // 해당 채널에 노티!
        }
    }

}