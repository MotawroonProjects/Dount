package com.apps.dount.uis.activity_home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.apps.dount.interfaces.Listeners;
import com.apps.dount.model.UserModel;
import com.apps.dount.mvvm.HomeActivityMvvm;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.tags.Tags;
import com.apps.dount.uis.activity_base.BaseActivity;

import com.apps.dount.R;

import com.apps.dount.databinding.ActivityHomeBinding;
import com.apps.dount.language.Language;
import com.apps.dount.uis.activity_cart.CartActivity;
import com.apps.dount.uis.activity_contact_us.ContactUsActivity;
import com.apps.dount.uis.activity_language.LanguageActivity;
import com.apps.dount.uis.activity_login.LoginActivity;
import com.apps.dount.uis.activity_my_orders.MyOrderActivity;
import com.apps.dount.uis.activity_notification.NotificationActivity;
import com.apps.dount.uis.activity_share.ShareActivity;
import com.apps.dount.uis.activity_sign_up.SignUpActivity;
import com.apps.dount.uis.activity_wallet.WalletActivity;
import com.apps.dount.uis.activity_favourite.FavouriteActivity;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity implements Listeners.VerificationListener {
    private ActivityHomeBinding binding;
    private NavController navController;
    private HomeActivityMvvm homeActivityMvvm;
    private ActionBarDrawerToggle toggle;
    private Preferences preferences;
    private UserModel userModel;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();


    }

    private void initView() {

        preferences = Preferences.getInstance();
        updateCartCount();
        userModel = preferences.getUserData(this);
        if (userModel != null) {
            binding.setModel(userModel);
        }
        homeActivityMvvm = ViewModelProviders.of(this).get(HomeActivityMvvm.class);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                userModel = getUserModel();
                // Log.e("sssssss", Tags.base_url+userModel.getData().getPhoto());
                if (userModel.getData().getPhoto() != null) {
                    Picasso.get().load(Tags.base_url + userModel.getData().getPhoto()).into(binding.image);
                }
                binding.setModel(userModel);
                updateFirebase();
            } else if (req == 2 && result.getResultCode() == Activity.RESULT_OK) {
                updateCartCount();
                userModel = getUserModel();
                binding.setModel(getUserModel());
                updateFirebase();
            }
            else if (req == 3 && result.getResultCode() == Activity.RESULT_OK&&result.getData()!=null) {
                String lang = result.getData().getStringExtra("lang");
                refreshActivity(lang);
            }
        });

        setSupportActionBar(binding.toolBar);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (binding.toolBar.getNavigationIcon() != null) {
                binding.toolBar.getNavigationIcon().setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.black), PorterDuff.Mode.SRC_ATOP);

            }
        });
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, R.string.open, R.string.close);
//
//        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);


        toggle.syncState();
        binding.imgNotification.setOnClickListener(v -> {


        });
        homeActivityMvvm.logout.observe(this, aBoolean -> {
            if (aBoolean) {
                logout();
            }
        });
        homeActivityMvvm.firebase.observe(this, token -> {
            if (getUserModel() != null) {
                UserModel userModel = getUserModel();
                userModel.getData().setFirebase_token(token);
                setUserModel(userModel);
            }
        });

        binding.imgNotification.setOnClickListener(v -> {
            if (getUserModel() != null) {
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
        if (getUserModel() != null) {
            homeActivityMvvm.updateFirebase(this, getUserModel());
        }
        binding.wallet.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(HomeActivity.this, WalletActivity.class);
                startActivity(intent);
            } else {
                navigationToLoginActivity();
            }
        });
        binding.llEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel == null) {
                    navigationToLoginActivity();
                } else {
                    navigationToSignupActivity();
                }
            }
        });
        binding.contactUs.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
            startActivity(intent);

        });
        binding.favourite.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
                startActivity(intent);
            } else {
                navigationToLoginActivity();
            }

        });
        binding.llMyOrders.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(HomeActivity.this, MyOrderActivity.class);
                startActivity(intent);
            } else {
                navigationToLoginActivity();
            }
        });
        binding.imLogOut.setOnClickListener(view -> {
            if (getUserModel() == null) {
                logout();
            } else {
                homeActivityMvvm.logout(this, getUserModel());
            }

        });
        binding.shareApp.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(intent);
            } else {
                navigationToLoginActivity();
            }
        });


        binding.home.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);


        });
        binding.flCart.setOnClickListener(v -> {
            req = 2;
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            launcher.launch(intent);

        });
        binding.llcart.setOnClickListener(v -> {
            req = 2;
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            launcher.launch(intent);

        });
        binding.llChangeLangeuage.setOnClickListener(v -> {
            req = 3;
            Intent intent = new Intent(this, LanguageActivity.class);
            launcher.launch(intent);
        });

        if (userModel == null) {
            binding.tvName.setOnClickListener(view -> navigationToLoginActivity());

        }


    }

    private void navigationToLoginActivity() {
        req = 1;
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        launcher.launch(intent);
    }

    private void navigationToSignupActivity() {
        req = 1;
        Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
        launcher.launch(intent);
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }

    @Override
    public boolean onNavigateUp() {

        return NavigationUI.navigateUp(navController, binding.drawerLayout);
    }

    @Override
    public void onBackPressed() {
        int currentFragmentId = navController.getCurrentDestination().getId();
        if (currentFragmentId == R.id.home) {
            finish();

        } else {
            navController.popBackStack();
        }

    }

    @Override
    public void onVerificationSuccess() {

    }


    public void updateFirebase() {
        if (getUserModel() != null) {
            homeActivityMvvm.updateFirebase(this, getUserModel());
        }
    }

    private void logout() {

        clearUserModel(this);
        userModel = getUserModel();
        binding.setModel(null);
        navigationToLoginActivity();
    }

    public void updateCartCount() {
        if (preferences.getCartData(this) != null) {
            binding.setCartCount(preferences.getCartData(this).getDetails().size() + "");
        } else {
            binding.setCartCount(String.valueOf(0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }
}
