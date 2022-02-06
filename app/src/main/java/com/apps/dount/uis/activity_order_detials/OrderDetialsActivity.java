package com.apps.dount.uis.activity_order_detials;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dount.R;
import com.apps.dount.adapter.Order2ProductAdapter;
import com.apps.dount.databinding.ActivityNotificationBinding;
import com.apps.dount.databinding.ActivityOrderDetialsBinding;
import com.apps.dount.model.OrderModel;
import com.apps.dount.mvvm.ActivityOrderDetialsMvvm;
import com.apps.dount.uis.activity_base.BaseActivity;

public class OrderDetialsActivity extends BaseActivity {

    private ActivityOrderDetialsBinding binding;
    private ActivityOrderDetialsMvvm activityOrderDetialsMvvm;
    private String order_id;
    private OrderModel orderModel;
    private Order2ProductAdapter order2ProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detials);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }


    private void initView() {
        order2ProductAdapter = new Order2ProductAdapter(this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(order2ProductAdapter);
        binding.setLang(getLang());
        activityOrderDetialsMvvm = ViewModelProviders.of(this).get(ActivityOrderDetialsMvvm.class);
        activityOrderDetialsMvvm.getIsLoading().observe(this, loading -> {
            if (loading) {
                binding.progBar.setVisibility(View.VISIBLE);
                binding.nested.setVisibility(View.GONE);
            } else {
                binding.progBar.setVisibility(View.GONE);
                binding.nested.setVisibility(View.VISIBLE);
            }
        });
        activityOrderDetialsMvvm.getOrder().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if (orderModel != null) {
                    binding.setModel(orderModel);
                    updateData(orderModel);
                }
            }
        });
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // updateUi1();
        activityOrderDetialsMvvm.getorderDetials(order_id,getUserModel());

    }

    private void updateData(OrderModel orderModel) {
        this.orderModel = orderModel;
        order2ProductAdapter.updateList(orderModel.getDetials());
        if (orderModel.getStatus().equals("append")) {
            updateUi2();
        } else if (orderModel.getStatus().equals("accepted")) {
            updateUi3();
        } else if (orderModel.getStatus().equals("on_way")) {
            updateUi4();
        } else if (orderModel.getStatus().equals("done")) {
            updateUi5();
        }
    }

    private void updateUi1() {
        binding.tv1.setTextColor(getResources().getColor(R.color.color9));
        binding.tv2.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv3.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv4.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv5.setTextColor(getResources().getColor(R.color.gray11));
        binding.tvOrder1.setVisibility(View.VISIBLE);
        binding.tvOrder2.setVisibility(View.GONE);
        binding.tvOrder3.setVisibility(View.GONE);
        binding.tvOrder4.setVisibility(View.GONE);
        binding.tvOrder5.setVisibility(View.GONE);
        binding.image1.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image2.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image3.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image4.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image5.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));

        binding.image1.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image2.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.image3.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.image4.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.image5.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.view11.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view1.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view21.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view2.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view31.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view3.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view41.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view4.setBackgroundColor(getResources().getColor(R.color.gray13));

    }

    private void updateUi2() {
        binding.tv1.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv2.setTextColor(getResources().getColor(R.color.color9));
        binding.tv3.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv4.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv5.setTextColor(getResources().getColor(R.color.gray11));
        binding.tvOrder1.setVisibility(View.VISIBLE);
        binding.tvOrder2.setVisibility(View.VISIBLE);
        binding.tvOrder3.setVisibility(View.GONE);
        binding.tvOrder4.setVisibility(View.GONE);
        binding.tvOrder5.setVisibility(View.GONE);
        binding.image1.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image2.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image3.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image4.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image5.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image1.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image2.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image3.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.image4.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.image5.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.view11.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view1.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view21.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view2.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view31.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view3.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view41.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view4.setBackgroundColor(getResources().getColor(R.color.gray13));


    }

    private void updateUi3() {
        binding.tv1.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv2.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv3.setTextColor(getResources().getColor(R.color.color9));
        binding.tv4.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv5.setTextColor(getResources().getColor(R.color.gray11));
        binding.tvOrder1.setVisibility(View.VISIBLE);
        binding.tvOrder2.setVisibility(View.VISIBLE);
        binding.tvOrder3.setVisibility(View.VISIBLE);
        binding.tvOrder4.setVisibility(View.GONE);
        binding.tvOrder5.setVisibility(View.GONE);
        binding.image1.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image2.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image3.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image4.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image5.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image1.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image2.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image3.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image4.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.image5.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.view11.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view1.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view21.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view2.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view31.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view3.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view41.setBackgroundColor(getResources().getColor(R.color.gray13));
        binding.view4.setBackgroundColor(getResources().getColor(R.color.gray13));


    }

    private void updateUi4() {
        binding.tv1.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv2.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv3.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv4.setTextColor(getResources().getColor(R.color.color9));
        binding.tv5.setTextColor(getResources().getColor(R.color.gray11));
        binding.tvOrder1.setVisibility(View.VISIBLE);
        binding.tvOrder2.setVisibility(View.VISIBLE);
        binding.tvOrder3.setVisibility(View.VISIBLE);
        binding.tvOrder4.setVisibility(View.VISIBLE);
        binding.tvOrder5.setVisibility(View.GONE);
        binding.image1.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image2.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image3.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image4.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image5.setBackground(getResources().getDrawable(R.drawable.circle_not_bg));
        binding.image1.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image2.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image3.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image4.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image5.setColorFilter(ContextCompat.getColor(this, R.color.gray11), PorterDuff.Mode.SRC_IN);
        binding.view11.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view1.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view21.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view2.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view31.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view3.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view41.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view4.setBackgroundColor(getResources().getColor(R.color.gray13));


    }

    private void updateUi5() {
        binding.tv1.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv2.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv3.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv4.setTextColor(getResources().getColor(R.color.gray11));
        binding.tv5.setTextColor(getResources().getColor(R.color.color9));
        binding.tvOrder1.setVisibility(View.VISIBLE);
        binding.tvOrder2.setVisibility(View.VISIBLE);
        binding.tvOrder3.setVisibility(View.VISIBLE);
        binding.tvOrder4.setVisibility(View.VISIBLE);
        binding.tvOrder5.setVisibility(View.VISIBLE);
        binding.image1.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image2.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image3.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image4.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image5.setBackground(getResources().getDrawable(R.drawable.circle_color9));
        binding.image1.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image2.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image3.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image4.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.image5.setColorFilter(ContextCompat.getColor(this, R.color.color9), PorterDuff.Mode.SRC_IN);
        binding.view11.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view1.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view21.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view2.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view31.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view3.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view41.setBackgroundColor(getResources().getColor(R.color.color9));
        binding.view4.setBackgroundColor(getResources().getColor(R.color.color9));


    }
}