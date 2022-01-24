package com.apps.dount.uis.activity_share;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.apps.dount.R;
import com.apps.dount.databinding.ActivityShareBinding;
import com.apps.dount.model.UserModel;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.google.zxing.WriterException;

import java.net.MalformedURLException;
import java.net.URL;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ShareActivity extends BaseActivity {
    private ActivityShareBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String barcode;
    private QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());

        preferences = Preferences.getInstance();
        userModel = getUserModel();

        String app_url = "https://play.google.com/store/apps/details?id=" + this.getPackageName();

        if (userModel != null) {
            try {
                barcode = getResources().getString(R.string.app_link) + new URL(app_url) + "\n" + getResources().getString(R.string.shareContent) + userModel.getData().getUser().getCode() + getResources().getString(R.string.to_buy)+ "\n";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            barcode = app_url;
        }
        if (userModel != null) {
            binding.setModel(userModel);
        }
        binding.llShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, "تطبيق اكابر للذبائح");
            intent.putExtra(Intent.EXTRA_TEXT, barcode);
            startActivity(intent);
        });

        binding.llBack.setOnClickListener(view -> finish());
       GenerateCode();
    }

    private void GenerateCode() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //initializing a variable for default display.
        Display display = manager.getDefaultDisplay();
        //creating a variable for point which is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);
        //getting width and height of a point
        int width = point.x;
        int height = point.y;
        //generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;
        qrgEncoder = new QRGEncoder(barcode, null, QRGContents.Type.TEXT, dimen);

        Bitmap bm = null;
        try {
            bm = qrgEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            //  e.printStackTrace();
        }
        if (bm != null) {
            binding.image.setImageBitmap(bm);
        }
        else{
            binding.image.setVisibility(View.GONE);
        }

    }


}