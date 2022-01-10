package com.apps.dount.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.OfferItemRowBinding;
import com.apps.dount.model.ProductModel;
import com.apps.dount.uis.activity_home.fragments_home_navigaion.FragmentHome;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public OffersAdapter(Context context, Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OfferItemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.offer_item_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.priceOld.setPaintFlags(myHolder.binding.priceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        myHolder.binding.amountOld.setPaintFlags(myHolder.binding.amountOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment instanceof FragmentHome) {
                    FragmentHome fragmentHome = (FragmentHome) fragment;
                    fragmentHome.showProductDetials(list.get(holder.getLayoutPosition()).getProduct_id());
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
        public OfferItemRowBinding binding;

        public MyHolder(OfferItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        if (list != null) {
            this.list = list;

        }
        notifyDataSetChanged();
    }
}
