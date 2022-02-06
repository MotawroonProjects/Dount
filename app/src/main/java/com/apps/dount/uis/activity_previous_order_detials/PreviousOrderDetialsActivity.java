package com.apps.dount.uis.activity_previous_order_detials;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.dount.R;
import com.apps.dount.adapter.Order2ProductAdapter;
import com.apps.dount.databinding.ActivityPreviousOrderDetialsBinding;
import com.apps.dount.model.OrderModel;
import com.apps.dount.mvvm.ActivityOrderDetialsMvvm;
import com.apps.dount.mvvm.ActivityPreviousOrderDetialsMvvm;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_base.FragmentMapTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class PreviousOrderDetialsActivity extends BaseActivity implements OnMapReadyCallback {

    private ActivityPreviousOrderDetialsBinding binding;
    private ActivityPreviousOrderDetialsMvvm activityPreviousOrderDetialsMvvm;
    private String order_id;
    private OrderModel orderModel;
    private Order2ProductAdapter order2ProductAdapter;
    private GoogleMap mMap;
    private float zoom = 15.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_order_detials);
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
        activityPreviousOrderDetialsMvvm = ViewModelProviders.of(this).get(ActivityPreviousOrderDetialsMvvm.class);
        activityPreviousOrderDetialsMvvm.getIsLoading().observe(this, loading -> {
            if (loading) {
                binding.progBar.setVisibility(View.VISIBLE);
                binding.nested.setVisibility(View.GONE);
            } else {
                binding.progBar.setVisibility(View.GONE);
                binding.nested.setVisibility(View.VISIBLE);
            }
        });
        activityPreviousOrderDetialsMvvm.getOrder().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if (orderModel != null) {
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
        activityPreviousOrderDetialsMvvm.getorderDetials(order_id,getUserModel());
        updateUI();

    }

    private void updateData(OrderModel orderModel) {
        this.orderModel = orderModel;
        order2ProductAdapter.updateList(orderModel.getDetials());
        binding.setModel(orderModel);
        if(mMap!=null){
            addMarker(Double.parseDouble(orderModel.getLatitude()), Double.parseDouble(orderModel.getLatitude()));

        }
    }
    private void updateUI() {
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        FragmentMapTouchListener fragmentMapTouchListener = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map);
        fragmentMapTouchListener.setListener(() -> binding.nested.requestDisallowInterceptTouchEvent(true));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            if (activityPreviousOrderDetialsMvvm.getGoogleMap().getValue() == null) {
                activityPreviousOrderDetialsMvvm.setmMap(mMap);

            }


            if (orderModel != null) {

                addMarker(Double.parseDouble(orderModel.getLatitude()), Double.parseDouble(orderModel.getLatitude()));
            }

        }
    }

    private void addMarker(double lat, double lng) {
        if (activityPreviousOrderDetialsMvvm.getGoogleMap().getValue() != null) {
            mMap = activityPreviousOrderDetialsMvvm.getGoogleMap().getValue();
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));

    }


}