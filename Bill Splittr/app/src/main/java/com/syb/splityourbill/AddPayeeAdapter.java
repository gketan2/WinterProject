package com.syb.splityourbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

class AddPayeeAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<HashMap<String,String>> payeeData;// = new LinkedList<HashMap<String, String>>();
    private ViewHolder holder;

    AddPayeeAdapter(Context context, LinkedList<HashMap<String,String>> list) {
        mContext = context;
        this.payeeData = list;

    }


    private final class ViewHolder {
        public TextView payeeemail;
        public TextView payeeamount;
    }



    @Override
    public int getCount() {
        return payeeData.size();
    }

    @Override
    public HashMap<String,Integer> getItem(int position) {
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        String x = payeeData.get(position).keySet().toArray()[0].toString();int y = Integer.parseInt(payeeData.get(position).get(x));
        map.put(x,y);
        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null){
            vi = LayoutInflater.from(mContext).inflate(R.layout.newpayee_list_layout,parent,false);
        }
            holder = new ViewHolder();
            holder.payeeemail = vi.findViewById(R.id.payeeemail);
            holder.payeeamount = vi.findViewById(R.id.payeeamount);
            String x = payeeData.get(position).keySet().toArray()[0].toString();
            holder.payeeamount.setText(payeeData.get(position).get(x));
            holder.payeeemail.setText(x);
            vi.setTag(holder);

        return vi;
    }


}

