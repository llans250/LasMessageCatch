package srlj.action.aclass.firewall.fragament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.adapter.MessageAdapter;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.pojo.Message;


public class MessageFragment extends Fragment {
    private ListView listView;
    private MessageAdapter adapter;
    private List<Message> messageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //从数据库中得到拦截短信的集合
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        messageList = DbManager.getInstance().getMessageList();
        listView = (ListView) view.findViewById(R.id.smslistview);
        adapter = new MessageAdapter(getActivity(), messageList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        return view;
    }
}