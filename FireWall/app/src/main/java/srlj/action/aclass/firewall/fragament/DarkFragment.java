package srlj.action.aclass.firewall.fragament;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.adapter.DarkAdapter;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.pojo.Info;


public class DarkFragment extends Fragment {
    private Callbacks mCallbacks;
    private ListView listView;
    private DarkAdapter adapter;
    private List<Info> infoList;
    int position;

    public interface Callbacks {
        public void addOnClick(View view);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("DarkFragment所在的activity必须实现Callbacks接口");
        }
        mCallbacks = (Callbacks) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //从数据库中得到黑名单的集合
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dark, container, false);
        infoList = DbManager.getInstance().getDarkList();
        listView = (ListView) view.findViewById(R.id.dark);
        adapter = new DarkAdapter(getActivity(), infoList);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        infoList.clear();
        infoList.addAll(DbManager.getInstance().getDarkList());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        position = 0;
        if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            position = info.position;
        }
        menu.add("删除：" + infoList.get(position).getPhonenumber());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Info info = infoList.get(position);
        DbManager.getInstance().deleteDark(info);
        Log.i("message",info.getPhonenumber());
        infoList.clear();
        infoList.addAll(DbManager.getInstance().getDarkList());
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
}
