package com.rajkumar.smsreader

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.rajkumar.smsreader.database.InboxMessageData
import com.rajkumar.smsreader.database.MessageDatabase
import com.rajkumar.smsreader.database.SpamMessageData
import kotlin.random.Random


class MySMSReceiver : BroadcastReceiver() {
    val TAG: String = MySMSReceiver::class.java.simpleName
    val pdu_type = "pdus"
    private var NOTIFICATION_ID = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, _int: Intent) {
        val mDatabase = MessageDatabase.getDatabase(context)
        val bundle = _int.extras
        val msgs: Array<SmsMessage?>
        var strMessage = ""
        val format = bundle!!.getString("format")
        val pdus = bundle[pdu_type] as Array<*>?
        if (pdus != null) {
            // Check the Android version.
            val isVersionM = Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.M
            msgs = arrayOfNulls(pdus.size)
            for (i in msgs.indices) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu(
                        pdus[i] as ByteArray?,
                        format
                    )
                } else {
                    // If Android version L or older:
                    msgs[i] =
                        SmsMessage.createFromPdu(pdus[i] as ByteArray?)
                }

                // Build the message to show.
                strMessage += "SMS from " + msgs[i]?.originatingAddress;
                strMessage += " :" + msgs[i]?.messageBody + "\n";
                strMessage += " :" + msgs[i]?.serviceCenterAddress;
                strMessage += " :" + msgs[i]?.protocolIdentifier

                Log.d(TAG, "onReceive: $strMessage");
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val pendingIntent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                val defaultSoundUri: Uri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val CHANNEL_ID = "my_channel_01"
                    val name: CharSequence = "my_channel"
                    val Description = "This is my channel"
                    val importance = NotificationManager.IMPORTANCE_HIGH
                    val mChannel =
                        NotificationChannel(CHANNEL_ID, name, importance)
                    mChannel.description = Description
                    mChannel.enableLights(true)
                    mChannel.lightColor = Color.RED
                    mChannel.enableVibration(true)
                    mChannel.vibrationPattern = longArrayOf(
                        100,
                        200,
                        300,
                        400,
                        500,
                        400,
                        300,
                        200,
                        400
                    )
                    mChannel.setShowBadge(false)
                    val mNotifyBuilder =
                        NotificationCompat.Builder(context,CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_messages_icon)
                            .setContentTitle(msgs[i]?.originatingAddress)
                            .setContentText(msgs[i]?.displayMessageBody)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setStyle(NotificationCompat.BigTextStyle().bigText(msgs[i]?.displayMessageBody))
                            .setContentIntent(pendingIntent)

                    val notificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
                    notificationManager?.createNotificationChannel(mChannel)
                    notificationManager
                        ?.notify(NOTIFICATION_ID++, mNotifyBuilder.build())

                }

                Thread {
                    if (!(msgs[i]?.serviceCenterAddress.isNullOrEmpty() || msgs[i]?.serviceCenterAddress.equals(null))) {
                        val inbox = InboxMessageData(
                            Random.nextInt().toString(),
                            msgs[i]?.originatingAddress, msgs[i]?.messageBody,
                            msgs[i]?.timestampMillis ?: 0,
                            msgs[i]?.serviceCenterAddress, false
                        )
                        mDatabase.messageDao().insertInboxMessage(inbox)
                    } else {
                        val spam = SpamMessageData(
                            Random.nextInt().toString(),
                            msgs[i]?.originatingAddress, msgs[i]?.messageBody,
                            msgs[i]?.timestampMillis ?: 0,
                            msgs[i]?.protocolIdentifier ?: -1, false
                        )
                        mDatabase.messageDao().insertSpamMessage(spam)
                    }
                }.start()

            }

            context.sendBroadcast(Intent("NEW_MESSAGE"));
        }
    }
}