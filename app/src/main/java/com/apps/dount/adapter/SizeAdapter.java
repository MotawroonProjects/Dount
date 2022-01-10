
package com.apps.dount.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.SizeRowBinding;
import com.apps.dount.databinding.TypeRowBinding;
import com.apps.dount.model.SizeModel;
import com.apps.dount.uis.activity_product_detials.ProductDetialsActivity;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SizeModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int currentPos = 0;
    private int oldPos = 0;


    public SizeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @androidx.annotation.NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {


        SizeRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.size_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;

        myHolder.binding.priceOld.setPaintFlags(myHolder.binding.priceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        myHolder.binding.amountOld.setPaintFlags(myHolder.binding.amountOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            currentPos = myHolder.getAdapterPosition();
            if (oldPos != -1) {
                SizeModel old = list.get(oldPos);
                if (old.isIsselected()) {
                    old.setIsselected(false);
                    list.set(oldPos, old);
                    notifyItemChanged(oldPos);
                }

            }
            SizeModel currentModel = list.get(currentPos);
            if (!currentModel.isIsselected()) {
                currentModel.setIsselected(true);
                list.set(currentPos, currentModel);
                notifyItemChanged(currentPos);
                oldPos = currentPos;
            }
            if (context instanceof ProductDetialsActivity) {
                ProductDetialsActivity productDetialsActivity = (ProductDetialsActivity) context;
                productDetialsActivity.choosesize(currentModel);
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
        public SizeRowBinding binding;

        public MyHolder(SizeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateData(List<SizeModel> list) {

        if (list != null) {
            this.list = list;

        }
        notifyDataSetChanged();
    }

    public void updateslection() {
        if (oldPos != -1) {
            SizeModel old = list.get(oldPos);
            if (old.isIsselected()) {
                old.setIsselected(false);
                list.set(oldPos, old);
                notifyItemChanged(oldPos);
            }

        }

    }

}
