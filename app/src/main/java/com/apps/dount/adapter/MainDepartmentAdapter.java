package com.apps.dount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.MainDepartmentItemRowBinding;
import com.apps.dount.model.DepartmentModel;

import java.util.List;

public class MainDepartmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DepartmentModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;


    public MainDepartmentAdapter(Context context, Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainDepartmentItemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_department_item_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
//Minced
        SubProductAdapter subProductAdapter = new SubProductAdapter(list.get(position).getProducts(), context, fragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        myHolder.binding.recyclerMinced.setLayoutManager(layoutManager);
        myHolder.binding.recyclerMinced.setAdapter(subProductAdapter);
//Others

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
        public MainDepartmentItemRowBinding binding;

        public MyHolder(MainDepartmentItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<DepartmentModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
