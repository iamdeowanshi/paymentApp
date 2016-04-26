package com.batua.android.merchant.data.api;

import com.batua.android.merchant.Config;
import com.batua.android.merchant.data.model.Merchant.Category;
import com.batua.android.merchant.data.model.Merchant.City;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.data.model.Merchant.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Aaditya Deowanshi
 */
public interface BatuaMerchantService {

    @POST(Config.MERCHANT)
    Observable<Response<Merchant>> addMerchant(@Body MerchantRequest request, @Header("Access-Token") String header);

    @PUT(Config.MERCHANT)
    Observable<Response<Merchant>> updateMerchant(@Body MerchantRequest request, @Header("Access-Token") String header);

    @GET(Config.LIST_MERCHANT)
    Observable<Response<List<Merchant>>> getMerchants(@Path("salesAgentId") int salesId, @Header("Access-Token") String header);

    @GET(Config.CATEGORY)
    Observable<Response<List<Category>>> getCategories(@Header("Access-Token") String header);

    @GET(Config.CITY)
    Observable<Response<List<City>>> getCities(@Header("Access-Token") String header);

    @Multipart
    @POST(Config.UPLOAD_IMAGE)
    Observable<Response<String>> uploadPhoto(@Header("Access-Token") String header, @Part MultipartBody.Part imageFile);

    @GET(Config.PROFILE)
    Observable<Response<User>> getProfile(@Path("salesagentId") int salesId, @Header("Access-Token") String header);

    @PUT(Config.PROFILE)
    Observable<Response<User>> updateProfile(@Path("salesagentId") int salesId, @Body User user, @Header("Access-Token") String header);

    @FormUrlEncoded
    @POST(Config.NORMAL_LOGIN)
    Observable<Response<User>> normalLogin(@Field("email") String email, @Field("password") String password, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST(Config.SOCIAL_LOGIN)
    Observable<Response<User>> socialLogin(@Field("email") String email,@Field("googleId") String socialId, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @PUT(Config.LOGOUT)
    Observable<Response<String>> logout(@Field("deviceId") String deviceId, @Field("userId") int id);

    @FormUrlEncoded
    @PUT(Config.SEND_OTP)
    Observable<Response<String>> sendOtp(@Field("phone") String phone);

    @FormUrlEncoded
    @PUT(Config.VERIFY_OTP)
    Observable<Response<String>> verifyOtp(@Field("phone") String phone, @Field("otp") String otp, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @PUT(Config.PASSWORD_RESET)
    Observable<Response<String>> resetPassword(@Field("password") String password, @Field("userId") int userId);

}
