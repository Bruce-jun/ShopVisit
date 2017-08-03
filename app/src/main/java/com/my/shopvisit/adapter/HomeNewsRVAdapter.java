package com.my.shopvisit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.my.shopvisit.R;
import com.my.shopvisit.bean.NewsBody;

import java.util.List;

/**
 *
 * Created by admin on 2017/7/28.
 */

public class HomeNewsRVAdapter extends RecyclerView.Adapter<HomeNewsRVAdapter.HomeNewsViewHolder> {
        private Context mContext;
        private int count;//显示条目的数量
        private List<NewsBody> mNews;

    public HomeNewsRVAdapter(Context context, List<NewsBody> tasks, int count){
        mContext=context;
        mNews=tasks;
        this.count=count;
    }


    @Override
    public HomeNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.home_news_item, null);
        return new HomeNewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(HomeNewsViewHolder holder, int position) {
        NewsBody newsBody=mNews.get(position);
        Glide.with(mContext).load(newsBody.imgurl).into(holder.imageView);
        holder.title.setText(newsBody.title);
        holder.from.setText(newsBody.summary);
        if (position==count){
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        if (mNews.size() > 0){
            int c=Math.min(count,mNews.size());
            return c;
        }
        return 0;
    }

    class HomeNewsViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView from;
        View line;

        public HomeNewsViewHolder(View itemView) {
            super(itemView);

            imageView= (ImageView) itemView.findViewById(R.id.home_new_item_img);
            title= (TextView) itemView.findViewById(R.id.home_new_item_title);
            from= (TextView) itemView.findViewById(R.id.home_new_item_from);
            line= itemView.findViewById(R.id.home_new_item_line);

        }
    }
}
