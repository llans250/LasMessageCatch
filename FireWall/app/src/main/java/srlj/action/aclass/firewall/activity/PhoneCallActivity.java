package srlj.action.aclass.firewall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import srlj.action.aclass.firewall.R;

public class PhoneCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
    }

    public void backOnClick(View view) {
        this.finish();
    }
}
