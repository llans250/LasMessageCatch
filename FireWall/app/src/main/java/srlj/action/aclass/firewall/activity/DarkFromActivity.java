package srlj.action.aclass.firewall.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import srlj.action.aclass.firewall.MainActivity;
import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.adapter.MainFragmentPagerAdapter;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.fragament.DarkFragment;
import srlj.action.aclass.firewall.fragament.MessageFragment;
import srlj.action.aclass.firewall.fragament.PhoneFragment;
import srlj.action.aclass.firewall.fragament.QueryDataFragament;
import srlj.action.aclass.firewall.service.PhoneService;

public class DarkFromActivity extends FragmentActivity implements DarkFragment.Callbacks,QueryDataFragament.QueryDateListener{

    FragmentManager fragmentManager;
    QueryDataFragament darkQueryFragament,MessageQueryFragament,phoneCallFragament;
    ImageButton imageButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark_from);

        fragmentManager = getSupportFragmentManager();//获取到fragment的管理对象
        imageButton = findViewById(R.id.query);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.query:
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        if (darkQueryFragament == null) {
                            darkQueryFragament = new QueryDataFragament("黑名单查询");
                            //加入事务
                            fragmentTransaction.add(R.id.content,darkQueryFragament);
                            fragmentTransaction.commit();
                        } else {
                            //如果王超fragment不为空就显示出来
                            fragmentTransaction.show(darkQueryFragament);
                            fragmentTransaction.commit();
                        }
                        break;
                }
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DarkFragment());
        fragments.add(new MessageFragment());
        fragments.add(new PhoneFragment());
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(
                getSupportFragmentManager(),
                fragments
        );

    }

    public void backOnClick(View view) {
        this.finish();
    }

    //实现darkfragment中的接口方法
    @Override
    public void addOnClick(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.add_menu);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId) {
                    //点击了从联系人添加的响应事件
                    case R.id.addfromcontacts:
                        Intent intent = new Intent(DarkFromActivity.this, AddFromComtactsActivity.class);
                        startActivity(intent);
                        break;
                    //点击了手动添加的响应事件
                    case R.id.addshoudong:
                        Intent intent2 = new Intent(DarkFromActivity.this, AddShoudongActivity.class);
                        startActivity(intent2);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void Back(String type) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (type){
            case "黑名单查询":
                if (darkQueryFragament != null) {
                    fragmentTransaction.hide(darkQueryFragament);
                }
                break;
            case "短信查询":
                if (MessageQueryFragament != null) {
                    fragmentTransaction.hide(MessageQueryFragament);
                }
                break;
            case "通话记录查询":
                if (phoneCallFragament != null) {
                    fragmentTransaction.hide(phoneCallFragament);
                }
                break;
        }
        fragmentTransaction.commit();
    }

}
