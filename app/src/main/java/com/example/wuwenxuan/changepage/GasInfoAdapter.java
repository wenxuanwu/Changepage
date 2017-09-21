package com.example.wuwenxuan.changepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wuwenxuan on 2017/8/30 10:31.
 * 功能 ：Gas检测adapter
 */

public class GasInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<GasInfo> mlist;
    private LayoutInflater mInflater;
    private TextView tv_address, tv_id, tv_time, tv_type;

    public GasInfoAdapter(Context context, List<GasInfo> list) {
        this.mContext = context;
        this.mlist = list;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.graininfo_list_item, parent, false);
        tv_id = v.findViewById(R.id.tv_id);
        tv_time =  v.findViewById(R.id.tv_time);
        tv_address =  v.findViewById(R.id.tv_address);
        tv_type =  v.findViewById(R.id.tv_type);

        GasInfo gasInfo = mlist.get(position);
        int id = gasInfo.getId();
        String data = gasInfo.getDate();
        String address = gasInfo.getAddress_id();
        String type = gasInfo.getType();


        tv_id.setText(id + "");
        tv_time.setText(data);
        tv_address.setText(address);
        tv_type.setText(type);


        return v;
    }
}
