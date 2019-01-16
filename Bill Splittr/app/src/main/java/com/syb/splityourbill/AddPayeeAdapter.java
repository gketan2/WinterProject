package com.syb.splityourbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
//import java.util.List;

class AddPayeeAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<String> mpayeeEmail;
    private LinkedList<String> mAmount;
    private ViewHolder holder;

    AddPayeeAdapter(Context context, LinkedList<String> list) {
        mContext = context;
        mpayeeEmail = new LinkedList<String>();
        mAmount  = new LinkedList<String>();
        for(int i=0;i<list.size();i++){mpayeeEmail.add(list.get(i));i++;mAmount.add(list.get(i));}


    }


    public final class ViewHolder {
        public TextView payeeemail;
        public TextView payeeamount;
    }

//    public void remove(int position) {
//        mEmail.remove(position);
//        mAmount.remove(position);
//
//    }


    @Override
    public int getCount() {
        return mpayeeEmail.size();
    }

    @Override
    public Object getItem(int position) {
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        map.put(mpayeeEmail.get(position),Integer.parseInt(mAmount.get(position)));
        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = LayoutInflater.from(mContext).inflate(R.layout.newpayee_list_layout,parent);
            holder = new ViewHolder();
            holder.payeeemail = (TextView) vi.findViewById(R.id.payeeemail);
            holder.payeeamount = (TextView) vi.findViewById(R.id.payeeamount);
            holder.payeeamount.setText(mAmount.get(position));
            holder.payeeemail.setText(mpayeeEmail.get(position));
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        return vi;
    }


}

