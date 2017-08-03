package com.my.shopvisit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.shopvisit.R;

/**
 *
 * Created by zj on 2017/7/26.
 */

public class TrainFrament extends BaseFrament {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=View.inflate(mContext, R.layout.train_fragment,null);
        return view;
    }
}
