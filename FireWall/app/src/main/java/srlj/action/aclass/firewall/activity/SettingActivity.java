package srlj.action.aclass.firewall.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.dao.DbManager;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch smart_switch = findViewById(R.id.switch_smart);
        smart_switch.setChecked(DbManager.getInstance().getSmartSetting());;
        smart_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DbManager.getInstance().setSmartSetting(b);
                Log.i("message","smart:"+b);
            }
        });

        Switch global_switch = findViewById(R.id.switch_global);
        global_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DbManager.getInstance().setGlobalSetting(b);
                Log.i("message","global:"+b);
            }
        });
        global_switch.setChecked(DbManager.getInstance().getGlobalSetting());;
        RelativeLayout bug_layout = findViewById(R.id.layout_bug);
        bug_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","client@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report Bugs");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "No Bugs");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    public void backOnClick(View view) {
        this.finish();
    }
}
