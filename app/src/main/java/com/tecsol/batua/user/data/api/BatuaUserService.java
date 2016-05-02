package com.tecsol.batua.user.data.api;

import com.tecsol.batua.user.Config;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.Merchant.Review;
import com.tecsol.batua.user.data.model.User.CustomResponse;
import com.tecsol.batua.user.data.model.User.User;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    Observable<Response<CustomResponse>> socialGoogleSignUp(@Field("email") String email, @Field("name") String password, @Field("googleId") String googleid);

    @FormUrlEncoded
    @POST(Config.SOCIAL_SIGN_UP)
    Observable<Response<CustomResponse>> socialFaceBookSignUp(@Field("email") String email, @Field("name") String password, @Field("facebookId") String facebookId);

    @FormUrlEncoded
    @POST(Config.NORMAL_LOGIN)
    Observable<Response<User>> normalLogin(@Field("userName") String email, @Field("password") String password, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST(Config.SOCIAL_LOGIN)
    Observable<Response<User>> socialGoogleLogin(@Field("email") String email, @Field("deviceId") String deviceId,
                                                 @Field("facebookId") String facebookId, @Field("googleId") String googleId);

    @FormUrlEncoded
    @POST(Config.SOCIAL_LOGIN)
    Observable<Response<User>> socialFaceBookLogin(@Field("email") String email, @Field("deviceId") String deviceId, @Field("facebookId") String facebookId);

    @GET(Config.GET_MERCHANTS)
    Observable<Response<List<Merchant>>> getMerchants(@Path("userId") int userId);

    @GET(Config.GET_PARTICULAR_MERCHANTS)
    Observable<Response<List<Merchant>>> getParticularMerchant(@Path("userId") int userId, @Path("merchantId") int merchantId);

    @GET(Config.GET_PARTICULAR_MERCHANTS)
    Observable<Response<Review>> getReview(@Path("userId") int userId, @Path("merchantId") int merchantId);

}
