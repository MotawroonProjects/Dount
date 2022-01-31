
package com.apps.dount.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;


import com.apps.dount.R;
import com.apps.dount.databinding.SpinnerRowBinding;
import com.apps.dount.model.BranchModel;

import java.util.List;

public class SpinnerBranchAdapter extends BaseAdapter {
    private List<BranchModel> list;
    private Context context;

    public SpinnerBranchAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_row, viewGroup, false);

        binding.setTitle(list.get(i).getName());

        return binding.getRoot();
    }

    public void updateData(List<BranchModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
