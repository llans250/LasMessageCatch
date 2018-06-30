package srlj.action.aclass.firewall.fragament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.adapter.PhoneCallAdapter;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.pojo.Info;
import srlj.action.aclass.firewall.pojo.PhoneCall;

public class PhoneFragment extends Fragment {
    private ListView listView;
    private PhoneCallAdapter adapter;
    private List<PhoneCall> phoneCallList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //从数据库中得到拦截电话的集合
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        phoneCallList = DbManager.getInstance().getPhoneCallList();
        listView = (ListView) view.findViewById(R.id.tonghuaListView);
        adapter = new PhoneCallAdapter(getActivity(), phoneCallList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
