package com.apps.dount.uis.activity_home.fragments_home_navigaion;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.apps.dount.R;
import com.apps.dount.databinding.FragmentProfileBinding;
import com.apps.dount.uis.activity_base.BaseFragment;
import com.apps.dount.uis.activity_contact_us.ContactUsActivity;
import com.apps.dount.uis.activity_favourite.FavouriteActivity;
import com.apps.dount.uis.activity_home.HomeActivity;
import com.apps.dount.uis.activity_language.LanguageActivity;
import com.apps.dount.uis.activity_login.LoginActivity;
import com.apps.dount.uis.activity_my_orders.MyOrderActivity;
import com.apps.dount.uis.activity_previous_orders.PreviousOrderActivity;
import com.apps.dount.uis.activity_share.ShareActivity;
import com.apps.dount.uis.activity_sign_up.SignUpActivity;
import com.apps.dount.uis.activity_wallet.WalletActivity;

import java.util.List;


public class FragmentProfile extends BaseFragment {
    private static final String TAG = FragmentProfile.class.getName();
    private HomeActivity activity;
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                binding.setModel(getUserModel());
            } else if (req == 2 && result.getResultCode() == Activity.RESULT_OK&&result.getData()!=null) {
                String lang = result.getData().getStringExtra("lang");
                activity.refreshActivity(lang);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        Log.e(TAG, "onViewCreated: ");

    }

    private void initView() {
        if (getUserModel() != null) {
            binding.setModel(getUserModel());
        }
        binding.setLang(getLang());



        binding.llLanguage.setOnClickListener(v -> {
            req = 2;
            Intent intent = new Intent(activity, LanguageActivity.class);
            launcher.launch(intent);
        });

        binding.llRate.setOnClickListener(v -> rateApp());

        binding.tvName.setOnClickListener(v -> {
            if (getUserModel() == null) {
                navigateToLoginActivity();
            }
        });

        binding.cardFavourite.setOnClickListener(view -> {
            if (getUserModel() != null) {
                Intent intent = new Intent(activity, FavouriteActivity.class);
                startActivity(intent);
            } else {
                navigateToLoginActivity();
            }
        });
        binding.imSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getUserModel() == null) {
                    navigateToLoginActivity();
                } else {
                    navigationToSignupActivity();
                }
            }
        });
        binding.cardOrder.setOnClickListener(view -> {
            if (getUserModel() != null) {
                Intent intent = new Intent(activity, MyOrderActivity.class);
                startActivity(intent);
            } else {
                navigateToLoginActivity();
            }
        });

//        binding.cardpreviousOrder.setOnClickListener(view -> {
//            if (getUserModel() != null) {
//                Intent intent = new Intent(activity, PreviousOrderActivity.class);
//                startActivity(intent);
//            } else {
//                navigateToLoginActivity();
//            }
//        });
        binding.cardWallet.setOnClickListener(view -> {
            if (getUserModel() != null) {
                Intent intent = new Intent(activity, WalletActivity.class);
                startActivity(intent);
            } else {
                navigateToLoginActivity();
            }
        });
        binding.llSahre.setOnClickListener(view -> {
            if (getUserModel() != null) {
                Intent intent = new Intent(activity, ShareActivity.class);
                startActivity(intent);
            } else {
                navigateToLoginActivity();
            }
        });
        binding.llWallet.setOnClickListener(view -> {
            if (getUserModel() != null) {
                Intent intent = new Intent(activity, WalletActivity.class);
                startActivity(intent);
            } else {
                navigateToLoginActivity();
            }
        });
        binding.llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ContactUsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToLoginActivity() {
        req = 1;
        Intent intent = new Intent(activity, LoginActivity.class);
        launcher.launch(intent);

    }
    private void navigationToSignupActivity() {
        req = 1;
        Intent intent = new Intent(activity, SignUpActivity.class);
        launcher.launch(intent);
    }

    private void logout() {
        clearUserModel(activity);
        binding.setModel(null);
        binding.image.setImageResource(R.drawable.circle_avatar);
    }

    private void rateApp() {
        String appId = activity.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        final List<ResolveInfo> otherApps = activity.getPackageManager()
                .queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            if (otherApp.activityInfo.applicationInfo.packageName
                    .equals("com.apps.dount")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                rateIntent.setComponent(componentName);
                startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appId));
            startActivity(webIntent);
        }

    }




}