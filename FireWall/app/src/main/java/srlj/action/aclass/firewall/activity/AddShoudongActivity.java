package srlj.action.aclass.firewall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.pojo.Info;

public class AddShoudongActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shoudong);
        editText1 = (EditText) findViewById(R.id.shoudongname);
        editText2 = (EditText) findViewById(R.id.shoudongnumber);
    }

    public void backOnClick(View view) {
        this.finish();
    }

    public void concelOnClick(View view) {
        editText1.setText("");
        editText2.setText("");

    }

    public void ensureOnClick(View view) {
        String name = editText1.getText().toString();
        String number = editText2.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "名字不能为空", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        } else {
            String result = number;
            boolean regular = false;
            if(number.length() < 11){
                result = number + ".\\S{0,}";
                regular = true;
            }
            Info info = new Info();
            info.setName(name);
            info.setPhonenumber(result);
            DbManager.getInstance().addInfo(info,regular);
            Toast.makeText(this, "黑名单添加成功", Toast.LENGTH_LONG).show();
        }
    }
}
