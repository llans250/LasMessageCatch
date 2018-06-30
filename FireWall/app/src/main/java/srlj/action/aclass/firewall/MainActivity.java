package srlj.action.aclass.firewall;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import srlj.action.aclass.firewall.activity.AddFromComtactsActivity;
import srlj.action.aclass.firewall.activity.AddShoudongActivity;
import srlj.action.aclass.firewall.activity.DarkFromActivity;
import srlj.action.aclass.firewall.activity.MessageActivity;
import srlj.action.aclass.firewall.activity.PhoneCallActivity;
import srlj.action.aclass.firewall.activity.SettingActivity;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.service.PhoneService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout messageLayout, phoneLayout, settingLayout,darkLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startObserver();
        DbManager.createInstance(getBaseContext());

        initView();
    }
    public void startObserver(){
        Intent intent = new Intent(this, PhoneService.class);
        startService(intent);
    }

    private void initView() {
        messageLayout = (RelativeLayout) findViewById(R.id.message_layout);
        phoneLayout = (RelativeLayout) findViewById(R.id.phone_layout);
        settingLayout = (RelativeLayout) findViewById(R.id.setting_setting);
        darkLayout = (RelativeLayout) findViewById(R.id.dark_layout);

        messageLayout.setOnClickListener(this);
        phoneLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        darkLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dark_layout:
                startActivity(new Intent(this, DarkFromActivity.class));
                break;
            case R.id.message_layout:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.phone_layout:
                startActivity(new Intent(this, PhoneCallActivity.class));
                break;
            case R.id.setting_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }


}

