package org.androidtown.foodtruckgram.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSReceiver extends BroadcastReceiver {

    //private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    //private static final String TAG = "SMSReceiver";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Bundle bundle = intent.getExtras();

        Object messages[] = (Object[]) bundle.get("pdus");

        SmsMessage smsMessage[] = new SmsMessage[messages.length];


        for (int i = 0; i < messages.length; i++) {

            // PDU 포맷으로 되어 있는 메시지를 복원합니다.

            smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);

        }


// SMS 수신 시간 확인

        Date curDate = new Date(smsMessage[0].getTimestampMillis());

        Log.d("문자 수신 시간", curDate.toString());


// SMS 발신 번호 확인

        String origNumber = smsMessage[0].getOriginatingAddress();


// SMS 메시지 확인

        String message = smsMessage[0].getMessageBody().toString();

        Log.d("문자 내용", "발신자 : " + origNumber + ", 내용 : " + message);


    }

//    // 액티비티로 메세지의 내용을 전달해줌
//    private void sendToActivity(Context context, String sender, String contents, Date receivedDate){
//        Intent intent = new Intent(context, SMSActivity.class);
//
//        // Flag 설정
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // 메세지의 내용을 Extra에 넣어줌
//        intent.putExtra("sender", sender);
//        intent.putExtra("contents", contents);
//        intent.putExtra("receivedDate", format.format(receivedDate));
//
//        context.startActivity(intent);
//    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for (int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }

        return messages;
    }
}
