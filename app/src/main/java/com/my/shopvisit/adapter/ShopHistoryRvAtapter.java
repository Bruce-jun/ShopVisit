package com.my.shopvisit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.shopvisit.R;
import com.my.shopvisit.bean.Historys;

import java.util.List;

/**
 * 巡店的适配器
 * Created by admin on 2017/7/31.
 */

public class ShopHistoryRvAtapter extends RecyclerView.Adapter<ShopHistoryRvAtapter.ShopHistoryViewHolder> {
    private Context mContext;
    private List<Historys> mList;

    public ShopHistoryRvAtapter(Context context,List<Historys> list){
        mContext=context;
        mList=list;
    }

    public  void setHistorys(List<Historys> list){
        if (list.size()>0){
            mList.addAll(list);
        }
    }

    @Override
    public ShopHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.shop_history_rv_item,null);
        return new ShopHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopHistoryViewHolder holder, int position) {
        Historys history=mList.get(position);
        holder.date.setText("巡店时间:"+history.visitdate);
        holder.shopName.setText("店面名称:"+history.name);
        if (position==mList.size()){
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ShopHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView shopName;
        RelativeLayout root;
        View line;

        public ShopHistoryViewHolder(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.shop_item_date);
            shopName=(TextView) itemView.findViewById(R.id.shop_item_name);
            root= (RelativeLayout) itemView.findViewById(R.id.shop_item_root);
            line=itemView.findViewById(R.id.shop_item_line);
        }
    }
}
