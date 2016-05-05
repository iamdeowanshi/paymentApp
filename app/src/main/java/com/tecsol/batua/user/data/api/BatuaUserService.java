package com.tecsol.batua.user.data.api;

import com.tecsol.batua.user.Config;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.Merchant.Review;
import com.tecsol.batua.user.data.model.User.ChangePassword;
import com.tecsol.batua.user.data.model.User.Otp;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.CustomResponse;
import com.tecsol.batua.user.data.model.User.User;

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
 * @author Farhan Ali
 */
public interface BatuaUserService {

    @POST(Config.NORMAL_SIGN_UP)
    Observable<Response<CustomResponse>> normalSignUp(@Body User user);

    @FormUrlEncoded
    @POST(Config.SOCIAL_SIGN_UP)
    Observable<Response<CustomResponse>> socialGoogleSignUp(@Field("email") String email, @Field("name") String password, @Field("googleId") String googleId);

    @FormUrlEncoded
    @POST(Config.SOCIAL_SIGN_UP)
    Observable<Response<CustomResponse>> socialFaceBookSignUp(@Field("email") String email, @Field("name") String password, @Field("facebookId") String facebookId);

    @FormUrlEncoded
    @POST(Config.NORMAL_LOGIN)
    Observable<Response<User>> normalLogin(@Field("userName") String email, @Field("password") String password, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST(Config.SOCIAL_LOGIN)
    Observable<Response<User>> socialGoogleLogin(@Field("email") String email, @Field("deviceId") String deviceId, @Field("googleId") String googleId);

    @FormUrlEncoded
    @POST(Config.SOCIAL_LOGIN)
    Observable<Response<User>> socialFaceBookLogin(@Field("email") String email, @Field("deviceId") String deviceId, @Field("facebookId") String facebookId);

    @GET(Config.GET_MERCHANTS)
    Observable<Response<List<Merchant>>> getMerchants(@Path("userId") int userId, @Path("merchantId") int merchantId, @Path("latitude") double latitude, @Path("longitude") double longitude);

    @GET(Config.GET_PARTICULAR_MERCHANTS)
    Observable<Response<List<Merchant>>> getParticularMerchant(@Path("userId") int userId, @Path("merchantId") int merchantId);

    @GET(Config.GET_PARTICULAR_MERCHANTS)
    Observable<Response<Review>> getReview(@Path("userId") int userId, @Path("merchantId") int merchantId);

    @GET(Config.SAVE_PIN)
    Observable<Response<Review>> savePin(@Path("userId") int userId, @Path("PIN") String pin);

    @PUT(Config.SEND_SIGN_UP_OTP)
    Observable<Response<CustomResponse>> sendSignUpOtp(@Body Otp otp);

    @PUT(Config.VERIFY_SIGN_UP_OTP)
    Observable<Response<CustomResponse>> verifySignUpOtp(@Body Otp otp);

    @PUT(Config.UPDATE_PROFILE)
    Observable<Response<User>> updateProfile(@Body User user);

    @GET(Config.GET_PROFILE)
    Observable<Response<User>> getPinStatus(@Path("userId") int userId);

    @PUT(Config.SET_PIN)
    Observable<Response<String>> setPin(@Body Pin pin);

    @PUT(Config.UPDATE_PIN_STATUS)
    Observable<Response<User>> updatePinStatus(@Body Pin pin);

    @PUT(Config.CHANGE_PIN)
    Observable<Response<CustomResponse>> changePin(@Body Pin pin);

    @PUT(Config.LOGIN_BY_PIN)
    Observable<Response<User>> loginByPin(@Body Pin pin, @Header("Access-Token") String accessToken);

    @PUT(Config.CHANGE_PASSWORD)
    Observable<Response<CustomResponse>> changePassword(@Body ChangePassword changePassword);

    @Multipart
    @POST(Config.UPLOAD_IMAGE)
    Observable<Response<String>> uploadPhoto(@Header("Access-Token") String header, @Part MultipartBody.Part imageFile);

}
