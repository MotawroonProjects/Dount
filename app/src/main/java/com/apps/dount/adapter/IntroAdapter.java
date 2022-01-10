package com.apps.dount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.apps.dount.R;
import com.apps.dount.databinding.IntroRowBinding;


import java.util.ArrayList;
import java.util.List;

public class IntroAdapter extends PagerAdapter {
    private List<Integer> imagetopList;
    private List<String> title;
    private List<String> content;
    private Context context;
    private LayoutInflater inflater;

    public IntroAdapter(Context context) {

        imagetopList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        title = new ArrayList<>();
        content = new ArrayList<>();
        imagetopList.add(R.drawable.intro1);
        imagetopList.add(R.drawable.intro2);
        imagetopList.add(R.drawable.intro3);
        imagetopList.add(R.drawable.intro4);

        this.context = context;

    }

    @Override
    public int getCount() {
        return imagetopList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object.equals(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        IntroRowBinding rowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.intro_row, container, false);
        container.addView(rowBinding.getRoot());
        rowBinding.topImage.setImageResource(imagetopList.get(position));
        return rowBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
