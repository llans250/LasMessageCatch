package srlj.action.aclass.firewall.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import android.telephony.SmsMessage;
import android.util.Log;

import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.pojo.Message;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //接收由sms传递过来的数据
        Bundle extras = intent.getExtras();
        //通过pdus可以获得接收到的所有短信信息
        Object[] array = (Object[]) extras.get("pdus");
        //因为可能同时获得多个信息
        for (Object o1 : array) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) o1);
            //获得接收短信的电话号码
            String adress = message.getOriginatingAddress();
            //获得短信的内容
            String content = message.getDisplayMessageBody();
            if (DbManager.getInstance().phonenumberisExist(adress) || (DbManager.getInstance().phonenumberisExist("+86" +adress))) {
                //把短信加入短信拦截表
                Message duanXin = new Message();
                duanXin.setPhonenumber(adress);
                duanXin.setContent(content);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sDateFormat.format(System.currentTimeMillis());
                duanXin.setTime(time);
                Log.i("message","-----------------拦截短信");
                DbManager.getInstance().addMessage(duanXin);
                abortBroadcast();
            }
        }

    }
}
