package com.apps.dount.uis.activity_web_view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.apps.dount.R;
import com.apps.dount.databinding.ActivityAboutAppBinding;
import com.apps.dount.uis.activity_base.BaseActivity;

import io.paperdb.Paper;

public class AboutUsActivity extends BaseActivity {

    private ActivityAboutAppBinding binding;
    private String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_app);
        binding.setLang(getLang());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setAllowContentAccess(true);
        binding.webView.getSettings().setAllowFileAccess(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.getSettings().setBuiltInZoomControls(false);
        binding.webView.loadUrl(url);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                binding.progBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progBar.setVisibility(View.GONE);
            }
        });

        binding.llBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}