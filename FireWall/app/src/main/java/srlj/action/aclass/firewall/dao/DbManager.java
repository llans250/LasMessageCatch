package srlj.action.aclass.firewall.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import srlj.action.aclass.firewall.pojo.Message;
import srlj.action.aclass.firewall.pojo.Info;
import srlj.action.aclass.firewall.pojo.PhoneCall;

/**
 * Created by Administrator on 2018/6/27.
 */


public class DbManager {
    private static DbManager dbManager;
    private static PhoneGuardSQLiteOpenHelper phoneGuardSQLiteOpenHelper;
    private Context context;

    public static DbManager createInstance(Context ct) {
        if (dbManager == null) {

            dbManager = new DbManager(ct);//这里已经把ct传给构造函数了
            SQLiteDatabase database =  phoneGuardSQLiteOpenHelper.getWritableDatabase();
            database.beginTransaction();
            Cursor cursor = database.query("setting",null,null,null,null,null,null);
            Log.i("message",cursor.getCount()+"");
            if(cursor.getCount() <= 0) {
                ContentValues c = new ContentValues();
                c.put("smart", 0);
                c.put("global", 0);
                database.insert("setting", null, c);
            }
            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
        }
        return dbManager;
    }

    public static DbManager getInstance() {
        if (dbManager == null) {
            throw new IllegalStateException("must call createInstance method before getInstance method");
        }
        return dbManager;
    }

    private DbManager(Context ct) {
        phoneGuardSQLiteOpenHelper =
                new PhoneGuardSQLiteOpenHelper(ct);
        this.context = ct;

    }

    //往数据库中添加一条记录黑名单记录
    public boolean addInfo(Info info,Boolean isRegular) {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("name", info.getName());
            String phonenumber = info.getPhonenumber().replace(" ", "").replace("-", "");

            if (! info.getPhonenumber().contains("+86")) {
                /*
                if(!isRegular) {
                    phonenumber = "+86" + phonenumber;
                }
                */
                phonenumber = "\\+86" + phonenumber;
            }
            values.put("phonenumber", phonenumber);
            if(isRegular){
                long rid = db.insert("regularDark", null, values);
            }else {
                long rid = db.insert("dark", null, values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } else
            return false;
    }

    //删除数据库中的一条黑名单记录
    public Boolean deleteDark(Info info) {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();

        if (db.isOpen()) {
            db.beginTransaction();
            long rid = db.delete("dark", "phonenumber = ?", new String[]{"\\"+info.getPhonenumber()});
            rid = db.delete("regularDark", "phonenumber = ?", new String[]{"\\"+info.getPhonenumber() + ".\\S{0,}"});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } else
            return false;
    }

    //往数据库中添加一条记录拦截到的短信记录
    public boolean addMessage(Message message) {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("phonenumber", message.getPhonenumber());
            values.put("content", message.getContent());
            values.put("time", message.getTime());
            long rid = db.insert("duanxin", null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } else
            return false;
    }


    //往数据库中添加一条记录拦截到的通话记录的信息
    public boolean addPhoneCall(PhoneCall phoneCall) {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("phonenumber", phoneCall.getPhonenumber());
            values.put("time", phoneCall.getTime());
            long rid = db.insert("tonghua", null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } else
            return false;
    }


    //删除数据库中的一条拦截短信记录
    public Boolean deleteMessage(Message message) {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.beginTransaction();
            long rid = db.delete("duanxin", "_id = ?", new String[]{message.getId() + ""});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } else
            return false;
    }


    //删除数据库中的一条拦截的通话记录
    public Boolean deletePhoneCall(PhoneCall phoneCall) {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.beginTransaction();
            long rid = db.delete("tonghua", "_id = ?", new String[]{phoneCall.getId() + ""});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } else
            return false;
    }


    //得到所有的黑名单的集合
    public List<Info> getDarkList() {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        List<Info> list = new ArrayList<>();
        Cursor cursor = db.query("dark", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Info info = new Info();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber")).replace("\\","");
                info.setName(name);
                info.setPhonenumber(phonenumber);
                list.add(info);
            }

        }
        cursor = db.query("regularDark", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Info info = new Info();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber")).replace(".\\S{0,}","").replace("\\","");
                info.setName(name);
                info.setPhonenumber(phonenumber);
                list.add(info);
            }

        }
        cursor.close();
        db.close();
        return list;
    }


    //根据一个info查询他是否在黑名单中
    public boolean phonenumberisExist(String phonenumber) {
        boolean flag = false;
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("regularDark", null, null, null, null, null, null);
        while (cursor.moveToNext()){

            boolean hasmatch = phonenumber.matches(cursor.getString(cursor.getColumnIndex("phonenumber")));
            if(hasmatch){
                cursor.close();
                db.close();
                return hasmatch;
            }
        }

        cursor = db.query("dark", null, "phonenumber=?", new String[]{"\\"+phonenumber}, null, null, null);
        if (cursor.getCount() == 0)
            flag = flag;
        else
            flag = true;
        cursor.close();
        db.close();
        return flag;
    }

    //得到所有的拦截到短信的集合
    public List<Message> getMessageList() {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        List<Message> list = new ArrayList<>();
        Cursor cursor = db.query("duanxin", null, null, null, null, null, "time desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Message message = new Message();
                int id = cursor.getInt(0);
                String phonenumber = cursor.getString(1);
                String content = cursor.getString(2);
                String time = cursor.getString(3);
                message.setPhonenumber(phonenumber);
                message.setContent(content);
                message.setId(id);
                message.setTime(time);
                list.add(message);
            }
            cursor.close();
            db.close();
        }
        return list;
    }


    //得到所有的拦截到短信的集合
    public List<PhoneCall> getPhoneCallList() {
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        List<PhoneCall> list = new ArrayList<>();
        Cursor cursor = db.query("tonghua", null, null, null, null, null, "time desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneCall phoneCall = new PhoneCall();
                int id = cursor.getInt(0);
                String phonenumber = cursor.getString(1);
                String time = cursor.getString(2);
                phoneCall.setId(id);
                phoneCall.setPhonenumber(phonenumber);
                phoneCall.setTime(time);
                list.add(phoneCall);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    //根据号码查找指定的黑名单数据
    public List<Info> queryDark(String phoneNumber){
        String phone = "\\+86" + phoneNumber;
        List<Info> lists = new ArrayList<>();
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = db.query("regularDark", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            //String phone = phonenumber.replace("+86", "");
            boolean hasmatch = phone.matches(cursor.getString(cursor.getColumnIndex("phonenumber")));
            if(hasmatch){
                Info info = new Info();
                info.setPhonenumber(cursor.getString(cursor.getColumnIndex("phonenumber")));
                info.setName(cursor.getString(cursor.getColumnIndex("name")));
                lists.add(info);
            }
        }
        cursor = db.query("dark", null, "phonenumber=?", new String[]{phone}, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Info info = new Info();
                info.setPhonenumber(cursor.getString(cursor.getColumnIndex("phonenumber")));
                info.setName(cursor.getString(cursor.getColumnIndex("name")));
                lists.add(info);
            }
            cursor.close();
            db.close();
        }
        return lists;
    }

    //根据号码查找指定的通话信息数据
    public List<PhoneCall> queryPhoneCall(String phoneNumber){
        String phone = "\\+86" + phoneNumber;
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        List<PhoneCall> list = new ArrayList<>();
        Cursor cursor = db.query("tonghua", null, "phonenumber=?", new String[]{phone}, null, null, "time desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneCall phoneCall = new PhoneCall();
                int id = cursor.getInt(0);
                String phonenumber = cursor.getString(1);
                String time = cursor.getString(2);
                phoneCall.setId(id);
                phoneCall.setPhonenumber(phonenumber);
                phoneCall.setTime(time);
                list.add(phoneCall);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    //根据号码查找指定的短信信息数据
    public List<Message> queryMessage(String phoneNumber) {
        String phone = "\\+86" + phoneNumber;
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        List<Message> list = new ArrayList<>();
        Cursor cursor = db.query("duanxin", null, "phonenumber=?", new String[]{phone}, null, null, "time desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Message message = new Message();
                int id = cursor.getInt(0);
                String phonenumber = cursor.getString(1);
                String content = cursor.getString(2);
                String time = cursor.getString(3);
                message.setPhonenumber(phonenumber);
                message.setContent(content);
                message.setId(id);
                message.setTime(time);
                list.add(message);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    //
    public boolean setSmartSetting(boolean state){
        ContentValues values = new ContentValues();
        if(state){
           SQLiteDatabase db =  phoneGuardSQLiteOpenHelper.getWritableDatabase();
            values.put("smart",1);
            db.update("setting",values,"_id=?",new String[]{1+""});
            db.close();
        }else {
            SQLiteDatabase db =  phoneGuardSQLiteOpenHelper.getWritableDatabase();
            values.put("smart",0);
            db.update("setting",values,"_id=?",new String[]{1+""});
            db.close();
        }
        return true;
    }

    public boolean setGlobalSetting(boolean state){
        ContentValues values = new ContentValues();
        if(state){
            SQLiteDatabase db =  phoneGuardSQLiteOpenHelper.getWritableDatabase();
            values.put("global",1);
            db.update("setting",values,"_id=?",new String[]{1+""});
            db.close();
        }else {
            SQLiteDatabase db =  phoneGuardSQLiteOpenHelper.getWritableDatabase();
            values.put("global",0);
            db.update("setting",values,"_id=?",new String[]{1+""});
            db.close();
        }
        return true;
    }


    public boolean getGlobalSetting(){
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("setting",null,null,null,null,null,null);
        cursor.moveToFirst();
        String global = cursor.getString(cursor.getColumnIndex("global"));
        if(TextUtils.equals(global,"0")){
            return false;
        }else {
            return true;
        }
    }

    public boolean getSmartSetting(){
        SQLiteDatabase db = phoneGuardSQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("setting",null,null,null,null,null,null);
        cursor.moveToFirst();
        String global = cursor.getString(cursor.getColumnIndex("smart"));
        if (TextUtils.equals(global, "0")) {
            return false;
        } else {
            return true;
        }
    }
}