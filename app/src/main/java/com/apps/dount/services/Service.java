package com.apps.dount.services;


import com.apps.dount.model.BranchDataModel;
import com.apps.dount.model.CartDataModel;
import com.apps.dount.model.DepartmentDataModel;
import com.apps.dount.model.OrderDataModel;
import com.apps.dount.model.PlaceMapDetailsData;
import com.apps.dount.model.ProductDataModel;
import com.apps.dount.model.NotificationDataModel;
import com.apps.dount.model.PlaceGeocodeData;
import com.apps.dount.model.SettingDataModel;
import com.apps.dount.model.SettingModel;
import com.apps.dount.model.ShipModel;
import com.apps.dount.model.SingleDepartmentDataModel;
import com.apps.dount.model.SingleOrderDataModel;
import com.apps.dount.model.SingleProductDataModel;
import com.apps.dount.model.SliderDataModel;
import com.apps.dount.model.StatusResponse;
import com.apps.dount.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("geocode/json")
    Single<Response<PlaceGeocodeData>> getGeoData(@Query(value = "latlng") String latlng,
                                                  @Query(value = "language") String language,
                                                  @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/auth/login")
    Single<Response<UserModel>> login(@Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("api/auth/register")
    Single<Response<UserModel>> signUp(@Field("first_name") String first_name,
                                       @Field("last_name") String last_name,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("register_by") String register_by


    );

    @GET("place/findplacefromtext/json")
    Single<Response<PlaceMapDetailsData>> searchOnMap(@Query(value = "inputtype") String inputtype,
                                                      @Query(value = "input") String input,
                                                      @Query(value = "fields") String fields,
                                                      @Query(value = "language") String language,
                                                      @Query(value = "key") String key);


    @Multipart
    @POST("api/auth/register")
    Observable<Response<UserModel>> signUpwithImage(@Part("name") RequestBody name,
                                                    @Part("phone_number") RequestBody phone_number,
                                                    @Part("register_by") RequestBody register_by,
                                                    @Part MultipartBody.Part logo


    );

    @FormUrlEncoded
    @POST("api/updateProfile")
    Single<Response<UserModel>> editprofile(@Field("first_name") String first_name,
                                            @Field("last_name") String last_name,
                                            @Field("user_id") String user_id


    );

    @Multipart
    @POST("api/auth/update_profile")
    Observable<Response<UserModel>> editprofilewithImage(
            @Part("name") RequestBody first_name,
            @Part("id") RequestBody id,
            @Part MultipartBody.Part logo


    );

    @FormUrlEncoded
    @POST("api/auth/logout")
    Single<Response<StatusResponse>> logout(@Header("Authorization") String authorization,
                                            @Field("phone_token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/auth/inser_token")
    Single<Response<StatusResponse>> updateFirebasetoken(@Field("phone_token") String phone_token,
                                                         @Field("user_id") String user_id,
                                                         @Field("type") String type


    );


    @GET("api/notification/notification")
    Single<Response<NotificationDataModel>> getNotifications(@Query(value = "customer_id") String customer_id);


    @GET("api/profile/favoriteProducts")
    Single<Response<ProductDataModel>> getFavourites(@Header("lang") String lang,
                                                     @Header("Authorization") String authorization);

    @GET("api/home/slider")
    Single<Response<SliderDataModel>> getSlider();

    @GET("api/home/second_slider")
    Single<Response<SliderDataModel>> getofferSlider();

    @GET("api/home/categories")
    Single<Response<DepartmentDataModel>> getDepartments();

    @GET("api/home/products")
    Single<Response<ProductDataModel>> getSingleDepartment(@Query(value = "category_ids[]") List<String> id,
                                                           @Query("search") String search
    );

    @GET("api/home/offers")
    Single<Response<ProductDataModel>> getOffers(@Header("Authorization") String authorization);

    @GET("api/home/products")
    Single<Response<ProductDataModel>> getProducts(@Header("Authorization") String authorization);

    @GET("api/home/branches")
    Single<Response<BranchDataModel>> getBranches();

    @GET("api/box")
    Single<Response<SingleProductDataModel>> getBox(@Header("lang") String lang);

    @GET("api/featured")
    Single<Response<DepartmentDataModel>> getFeatured(@Header("lang") String lang);

    @GET("api/home/one_product")
    Single<Response<SingleProductDataModel>> getSingleProduct(@Header("Authorization") String authorization,
                                                              @Query("product_id") String product_id);


    @FormUrlEncoded
    @POST("api/contact/contact")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String subject,
                                               @Field("message") String message);

    @FormUrlEncoded
    @POST("api/home/add-deleteFavorite")
    Single<Response<StatusResponse>> addRemoveFav(@Header("Authorization") String authorization,
                                                  @Field("product_id") String product_id);

    @GET("api/shipping_price")
    Single<Response<ShipModel>> getship(@Query("latitude") String latitude,
                                        @Query("longitude") String longitude);

    @POST("api/order/storeOrder")
    Single<Response<StatusResponse>> sendOrder(@Header("Authorization") String authorization,
                                               @Body CartDataModel cartDataModel
    );

    @GET("api/order/newOrders")
    Single<Response<OrderDataModel>> getMyOrders(@Header("Authorization") String authorization);

    @GET("api/order/one_order")
    Single<Response<SingleOrderDataModel>> getSingleOrders(@Header("Authorization") String authorization,
                                                           @Query(value = "order_id") String order_id);

    @GET("api/profile/getProfile")
    Single<Response<UserModel>> getProfile(@Header("Authorization") String authorization);

    @GET("api/terms/taxes")
    Single<Response<SettingDataModel>> getSetting();

    @GET("api/terms/about")
    Single<Response<SettingModel>> getAbout();
}