package com.batua.android.merchant.data.api;

import com.batua.android.merchant.Config;
import com.batua.android.merchant.data.model.Merchant.Category;
import com.batua.android.merchant.data.model.Merchant.City;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

}
