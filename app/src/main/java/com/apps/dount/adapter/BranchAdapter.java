package com.apps.dount.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.databinding.BranchCompleteRowBinding;
import com.apps.dount.databinding.BranchRowBinding;
import com.apps.dount.databinding.CartRowBinding;
import com.apps.dount.model.BranchModel;
import com.apps.dount.model.ItemCartModel;
import com.apps.dount.model.LocationModel;
import com.apps.dount.uis.activity_cart.CartActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BranchModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int currentPos = 0;
    private int oldPos = currentPos;
    private final int branch = 1;
    private final int branch_compelte = 2;
    private LocationModel locationModel;

    public BranchAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == branch) {
            BranchRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.branch_row, parent, false);
            return new MyHolder(binding);
        } else {
            BranchCompleteRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.branch_complete_row, parent, false);
            return new MyHolderComplete(binding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.binding.setModel(list.get(position));


//            ViewGroup.MarginLayoutParams lp =
//                    (ViewGroup.MarginLayoutParams) myHolder.binding.card.getLayoutParams();
//            lp.setMargins(5, 240, 5, 5);

            // myHolder.itemView.setLayoutParams(lp);

        } else if (holder instanceof MyHolderComplete) {
            MyHolderComplete myHolderComplete = (MyHolderComplete) holder;
            double distance = SphericalUtil.computeDistanceBetween(new LatLng(locationModel.getLat(), locationModel.getLng()), new LatLng(Double.parseDouble(list.get(position).getLatitude()), Double.parseDouble(list.get(position).getLongitude())))/1000;
            myHolderComplete.binding.setDistance(distance);
            myHolderComplete.binding.setModel(list.get(position));


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPos = holder.getLayoutPosition();
                BranchModel model = list.get(currentPos);

                if (!model.isSelected()) {

                    BranchModel oldModel = list.get(oldPos);
                    oldModel.setSelected(false);
                    list.set(oldPos, oldModel);
                    notifyItemChanged(oldPos);

                    model.setSelected(true);
                    list.set(currentPos, model);
                    oldPos = currentPos;
                    notifyItemChanged(currentPos);
                    // notifyDataSetChanged();

                }
                // Log.e("d'd;d;;d", oldPos + "");


                //notifyItemChanged(currentPos);


            }
        });
//        if (currentPos == position) {
//            ((MyHolder) holder).binding.expandLayout.expand(true);
//        }
//        else{
//            ((MyHolder) holder).binding.expandLayout.collapse(true);
//
//        }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<BranchModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public void updateLocation(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public BranchRowBinding binding;

        public MyHolder(@NonNull BranchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public class MyHolderComplete extends RecyclerView.ViewHolder {
        public BranchCompleteRowBinding binding;

        public MyHolderComplete(@NonNull BranchCompleteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @Override
    public int getItemViewType(int position) {

        BranchModel branchModel = list.get(position);


        if (branchModel.isSelected()) {


            return branch_compelte;

        } else {
            return branch;

        }


    }
}
