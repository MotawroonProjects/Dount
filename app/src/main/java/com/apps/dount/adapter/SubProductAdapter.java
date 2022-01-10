package com.apps.dount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.SubProductItemRowBinding;
import com.apps.dount.model.ProductModel;
import com.apps.dount.uis.activity_home.fragments_home_navigaion.FragmentHome;

import java.util.List;

public class SubProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public SubProductAdapter(List<ProductModel> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubProductItemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.sub_product_item_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment instanceof FragmentHome) {
                    FragmentHome fragmentHome = (FragmentHome) fragment;
                    fragmentHome.showProductDetials(list.get(holder.getLayoutPosition()).getId());
                }
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
        public SubProductItemRowBinding binding;

        public MyHolder(SubProductItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
