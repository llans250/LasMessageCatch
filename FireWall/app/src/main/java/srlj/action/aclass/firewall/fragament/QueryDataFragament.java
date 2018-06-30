package srlj.action.aclass.firewall.fragament;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import srlj.action.aclass.firewall.R;
import srlj.action.aclass.firewall.adapter.DarkAdapter;
import srlj.action.aclass.firewall.adapter.MessageAdapter;
import srlj.action.aclass.firewall.adapter.PhoneCallAdapter;
import srlj.action.aclass.firewall.dao.DbManager;
import srlj.action.aclass.firewall.pojo.Info;
import srlj.action.aclass.firewall.pojo.Message;
import srlj.action.aclass.firewall.pojo.PhoneCall;

public class QueryDataFragament extends Fragment implements View.OnClickListener {

    TextView textView;
    ImageButton back,querk;
    ListView listView;
    EditText editText;
    private QueryDateListener listener;
    private String type;
    public QueryDataFragament() {

    }

    @SuppressLint("ValidFragment")
    public QueryDataFragament(String type) {
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_data_fragament, container, false);
        textView = view.findViewById(R.id.query_title);
        back = view.findViewById(R.id.query_back);
        querk = view.findViewById(R.id.query_querydata);
        listView = view.findViewById(R.id.query_list);
        editText = view.findViewById(R.id.query_edit);
        textView.setText(type);

        back.setOnClickListener(this);
        querk.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case  R.id.query_back :
                listener.Back(type);
                break;
            case R.id.query_querydata:
                QueryDataAccordingType();
                break;
        }

    }


    public void QueryDataAccordingType(){
        if(TextUtils.isEmpty(editText.getText().toString())){
            Toast.makeText(getContext(),"搜索号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
            switch (type){
                case "黑名单查询":
                    List<Info> infoList = DbManager.getInstance().queryDark(editText.getText().toString());
                    if(infoList.size() <= 0){
                        Toast.makeText(getContext(),"OUT OF FIND1",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listView.setAdapter(new DarkAdapter(getContext(),infoList));
                    break;
                case "短信查询":
                    List<Message> messageList = DbManager.getInstance().queryMessage(editText.getText().toString());
                    if(messageList.size() <= 0){
                        Toast.makeText(getContext(),"OUT OF FIND2",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listView.setAdapter(new MessageAdapter(getContext(),messageList));
                    break;
                case "通话记录查询":
                    List<PhoneCall> phoneList = DbManager.getInstance().queryPhoneCall(editText.getText().toString());
                    if(phoneList.size() <= 0){
                        Toast.makeText(getContext(),"OUT OF FIND3",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listView.setAdapter(new PhoneCallAdapter(getContext(),phoneList));
                    break;
            }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof QueryDateListener)) {
            throw new IllegalStateException("DarkFragment所在的activity必须实现Callbacks接口");
        }
        listener = (QueryDateListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface QueryDateListener {
        public void Back(String type);
    }

}
