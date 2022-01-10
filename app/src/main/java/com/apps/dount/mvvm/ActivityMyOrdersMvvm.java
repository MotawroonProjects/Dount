package com.apps.dount.mvvm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dount.model.OrderDataModel;
import com.apps.dount.model.OrderModel;
import com.apps.dount.model.UserModel;
import com.apps.dount.remote.Api;
import com.apps.dount.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityMyOrdersMvvm extends AndroidViewModel {
    private static final String TAG = "ActivitymyordersMvvm";
    private Context context;


    private MutableLiveData<Boolean> isLoadingLiveData;
    private MutableLiveData<List<OrderModel>> listMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityMyOrdersMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLiveData == null) {
            isLoadingLiveData = new MutableLiveData<>();
        }
        return isLoadingLiveData;
    }

    public MutableLiveData<List<OrderModel>> getOrders() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public void getOrders(UserModel userModel) {


        isLoadingLiveData.setValue(true);

        Api.getService(Tags.base_url)
                .getMyOrders(userModel.getData().getId() + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<OrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<OrderDataModel> response) {
                        isLoadingLiveData.postValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                // List<ProductModel> list = response.body().getData();
                                listMutableLiveData.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
