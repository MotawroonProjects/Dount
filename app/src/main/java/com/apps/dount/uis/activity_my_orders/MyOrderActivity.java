package com.apps.dount.uis.activity_my_orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apps.dount.R;
import com.apps.dount.adapter.OrdersAdapter;
import com.apps.dount.databinding.ActivityCategoryDetialsBinding;
import com.apps.dount.databinding.ActivityOrdersBinding;
import com.apps.dount.model.OrderModel;
import com.apps.dount.model.UserModel;
import com.apps.dount.mvvm.ActivityMyOrdersMvvm;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_order_detials.OrderDetialsActivity;

import java.util.List;

public class MyOrderActivity extends BaseActivity {
    private ActivityOrdersBinding binding;
    private ActivityMyOrdersMvvm activityMyOrdersMvvm;
    private UserModel userModel;
    private Preferences preferences;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);
        initView();

    }

    private void initView() {
        ordersAdapter = new OrdersAdapter(this);
        preferences = Preferences.getInstance();
        userModel = getUserModel();
        activityMyOrdersMvvm = ViewModelProviders.of(this).get(ActivityMyOrdersMvvm.class);
        //  setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.setLang(getLang());
        activityMyOrdersMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progBar.setVisibility(View.GONE);
                }
            }
        });
        activityMyOrdersMvvm.getOrders().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
                if (orderModels != null) {
                    ordersAdapter.updateList(orderModels);
                }
            }
        });
        binding.recView.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recView.setAdapter(ordersAdapter);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityMyOrdersMvvm.getOrders(userModel);
    }


    public void show(OrderModel orderModel) {
        Intent intent = new Intent(this, OrderDetialsActivity.class);
        intent.putExtra("order_id", orderModel.getId());
        startActivity(intent);
    }
}