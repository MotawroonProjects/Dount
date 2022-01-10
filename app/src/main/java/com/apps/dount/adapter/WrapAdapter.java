
package com.apps.dount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.WayRowBinding;
import com.apps.dount.databinding.WrapRowBinding;
import com.apps.dount.model.WrapModel;
import com.apps.dount.uis.activity_product_detials.ProductDetialsActivity;

import java.util.List;

public class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WrapModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int currentPos = 0;
    private int oldPos = 0;


    public WrapAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @androidx.annotation.NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {


        WrapRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.wrap_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;


        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            currentPos = myHolder.getAdapterPosition();
            if (oldPos != -1) {
                WrapModel old = list.get(oldPos);
                if (old.isIsselected()) {
                    old.setIsselected(false);
                    list.set(oldPos, old);
                    notifyItemChanged(oldPos);
                }

            }
            WrapModel currentModel = list.get(currentPos);
            if (!currentModel.isIsselected()) {
                currentModel.setIsselected(true);
                list.set(currentPos, currentModel);
                notifyItemChanged(currentPos);
                oldPos = currentPos;
            }

            if (context instanceof ProductDetialsActivity) {
                ProductDetialsActivity productDetialsActivity = (ProductDetialsActivity) context;
                productDetialsActivity.choosewrap(currentModel);
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

    public void updateslection() {
        if (oldPos != -1) {
            WrapModel old = list.get(oldPos);
            if (old.isIsselected()) {
                old.setIsselected(false);
                list.set(oldPos, old);
                notifyItemChanged(oldPos);
            }

        }

    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public WrapRowBinding binding;

        public MyHolder(WrapRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateData(List<WrapModel> list) {

        if (list != null) {
            oldPos = 0;
            currentPos = 0;
            //  Log.e("dlldldl", list.size() + "");
            this.list = list;

        }
        notifyDataSetChanged();
    }


}
