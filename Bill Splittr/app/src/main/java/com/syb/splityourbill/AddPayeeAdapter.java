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
    private LinkedList<String> payeeData;
    private LinkedList<Integer> payeeAmountData;
    private ViewHolder holder;

    AddPayeeAdapter(Context context, LinkedList<String> list,LinkedList<Integer> amount) {
        mContext = context;
        this.payeeData = list;
        this.payeeAmountData = amount;

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
        String x = payeeData.get(position);int y = payeeAmountData.get(position);
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
            holder.payeeamount.setText(payeeAmountData.get(position)+"");
            holder.payeeemail.setText(payeeData.get(position));
            vi.setTag(holder);

        return vi;
    }


}

