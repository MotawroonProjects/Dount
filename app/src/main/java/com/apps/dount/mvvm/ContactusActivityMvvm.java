package com.apps.dount.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dount.R;
import com.apps.dount.model.ContactUsModel;
import com.apps.dount.model.SettingDataModel;
import com.apps.dount.model.SettingModel;
import com.apps.dount.model.StatusResponse;
import com.apps.dount.remote.Api;
import com.apps.dount.share.Common;
import com.apps.dount.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ContactusActivityMvvm extends AndroidViewModel {
    private Context context;

    private MutableLiveData<SettingModel> mutableLiveData;
    private MutableLiveData<Boolean> isLoading;

    public MutableLiveData<SettingModel> getMutableLiveData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<Boolean> send = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ContactusActivityMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public void getSetting(String lang) {


        isLoading.setValue(true);

        Api.getService(Tags.base_url)
                .getSetting(lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SettingDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SettingDataModel> response) {
                        isLoading.postValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {

                                mutableLiveData.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoading.setValue(false);
                    }
                });
    }


    public void contactus(Context context, ContactUsModel contactUsModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .contactUs(contactUsModel.getName(), contactUsModel.getEmail(), contactUsModel.getTitle(), contactUsModel.getMessage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> statusResponseResponse) {
                        dialog.dismiss();
                        if (statusResponseResponse.isSuccessful()) {
                            if (statusResponseResponse.body().getStatus() == 200) {
                                send.postValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        dialog.dismiss();
                    }
                });


    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
