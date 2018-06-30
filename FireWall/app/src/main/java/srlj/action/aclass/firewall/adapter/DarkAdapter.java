package srlj.action.aclass.firewall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.pojo.Info;

/**
 * Created by Administrator on 2018/6/27.
 */

public class DarkAdapter extends BaseAdapter {
    private Context context;
    private List<Info> list;

    public DarkAdapter(Context context, List<Info> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dark_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.darkname);
            viewHolder.number = (TextView) view.findViewById(R.id.darknumber);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.number.setText(list.get(i).getPhonenumber());
        return view;
    }

    private class ViewHolder {
        private TextView name;
        private TextView number;
    }
}

