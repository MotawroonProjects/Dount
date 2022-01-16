package com.apps.dount.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.BranchRowBinding;
import com.apps.dount.databinding.CartRowBinding;
import com.apps.dount.model.BranchModel;
import com.apps.dount.model.ItemCartModel;
import com.apps.dount.uis.activity_cart.CartActivity;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BranchModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int currentPos = 0;
    private int oldPos = currentPos;

    public BranchAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        BranchRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.branch_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPos = holder.getLayoutPosition();
                BranchModel model = list.get(currentPos);

                notifyDataSetChanged();
               // Log.e("d'd;d;;d", oldPos + "");


                //notifyItemChanged(currentPos);


            }
        });
        if (currentPos == position) {
            ((MyHolder) holder).binding.expandLayout.expand(true);
        }
        else{
            ((MyHolder) holder).binding.expandLayout.collapse(true);

        }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<BranchModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public BranchRowBinding binding;

        public MyHolder(@NonNull BranchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
