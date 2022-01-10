package com.apps.dount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;


import com.apps.dount.R;
import com.apps.dount.databinding.SliderBinding;
import com.apps.dount.model.SliderDataModel;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<SliderDataModel.SliderModel> list;
    private Context context;
    private LayoutInflater inflater;

    public SliderAdapter(List<SliderDataModel.SliderModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SliderBinding binding = DataBindingUtil.inflate(inflater, R.layout.slider, container, false);
        binding.setPhoto(list.get(position).getPhoto());
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
