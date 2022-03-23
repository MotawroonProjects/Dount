package com.apps.dount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apps.dount.R;
import com.apps.dount.databinding.DepartFilterRowBinding;
import com.apps.dount.model.DepartmentModel;
import com.apps.dount.model.ProductModel;
import com.apps.dount.uis.activity_filter.FilterActivity;

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DepartmentModel> list;
    private LayoutInflater inflater;
    private Context context;
    private AppCompatActivity activity;

    public CategoryFilterAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        activity = (AppCompatActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DepartFilterRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.depart_filter_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.binding.rb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (context instanceof FilterActivity) {
                FilterActivity activity = (FilterActivity) context;
                DepartmentModel model = list.get(myHolder.getLayoutPosition());
                model.setChecked(isChecked);
                activity.addDepartid(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        private DepartFilterRowBinding binding;

        public MyHolder(DepartFilterRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<DepartmentModel> list) {
        if (list != null) {
            this.list = list;

        }
        notifyDataSetChanged();
    }
}
