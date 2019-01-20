package com.syb.splityourbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class AddParticipantAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<String> participantData = new LinkedList<String>();
    private ViewHolder holder;

    AddParticipantAdapter(Context context,LinkedList<String> list){
        mContext = context;
        this.participantData = list;
    }

    @Override
    public int getCount() {
        return participantData.size();
    }

    @Override
    public String getItem(int position) {
        return participantData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null){
            vi = LayoutInflater.from(mContext).inflate(R.layout.newparticipant_list_layout,parent,false);
        }
        holder = new ViewHolder();
        holder.payeeemail = vi.findViewById(R.id.participantEmail);
        holder.payeeemail.setText(participantData.get(position));
        vi.setTag(holder);
        return null;
    }

    private final class ViewHolder {
        public TextView payeeemail;
    }
}
