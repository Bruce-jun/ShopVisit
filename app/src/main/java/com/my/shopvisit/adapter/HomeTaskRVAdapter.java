package com.my.shopvisit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.shopvisit.R;
import com.my.shopvisit.bean.Task;

import java.util.List;

/**
 *
 * Created by admin on 2017/7/28.
 */

public class HomeTaskRVAdapter extends RecyclerView.Adapter<HomeTaskRVAdapter.HomeTaskViewHolder> {
        private Context mContext;
        private int count;//显示条目的数量
        private List<Task> mTasks;

    public HomeTaskRVAdapter(Context context,List<Task> tasks,int count){
        mContext=context;
        mTasks=tasks;
        this.count=count;
    }

    @Override
    public HomeTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.home_task_item, null);
        return new HomeTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeTaskViewHolder holder, int position) {
        holder.title.setText(mTasks.get(position).title);
        if (position == count - 1) {
            holder.view.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        int c=0;
        if (mTasks.size() > 0){
             c=Math.min(count,mTasks.size());
        }
        return c;
    }

    class HomeTaskViewHolder extends  RecyclerView.ViewHolder{
        ImageView arrow;
        TextView title;
        LinearLayout root;
        View view;

        public HomeTaskViewHolder(View itemView) {
            super(itemView);
            arrow = (ImageView) itemView.findViewById(R.id.home_task_item_arrow);
            title = (TextView) itemView.findViewById(R.id.home_task_item_title);
            root = (LinearLayout) itemView.findViewById(R.id.task_item_root_home);
            view = itemView.findViewById(R.id.home_item_task);
        }
    }
}
