package com.example.monitorbudimas.API;

import com.example.monitorbudimas.Model.faktur.responseFaktur;
import com.example.monitorbudimas.Model.mobil.responseLocMobil;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.Model.loading.responseStart;
import com.example.monitorbudimas.Model.loading.responseUstart;
import com.example.monitorbudimas.Model.toko.responseToko;
import com.example.monitorbudimas.Model.mobil.responseMobil;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIreq {
    @FormUrlEncoded
    @POST("login.php")
    Call<responseLogin> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("retrievemobil.php")
    Call<responseMobil> ardretrievemobil();

    @GET("retrievemobilpulang.php")
    Call<responseMobil> ardretrievemobilpulang();

    @FormUrlEncoded
    @POST("retrievetoko.php")
    Call<responseToko> ardretrievetoko(
            @Field("user_id") String user_id,
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("retrievetokoselesaip.php")
    Call<responseToko> ardretrievetokoselesaip(
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("retrievefaktur.php")
    Call<responseFaktur> ardretrievefaktur(
            @Field("id_toko") String id_toko
    );
    
    @FormUrlEncoded
    @POST("getlocmobil.php")
    Call<responseLocMobil> getlocmobil(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("updateloadingstatusselesai.php")
    Call<responseMobil> ardupdateloadingstatusselesai(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("update_status_faktur.php")
    Call<responseFaktur> ardupdatefaktur(
            @Field("id_toko") String id_toko,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("update_pulang.php")
    Call<responseStart> ardPulang(
            @Field("user_id") String user_id
    );
    
    @FormUrlEncoded
    @POST("req_confirm_delivery.php")
    Call<responseStart> ardReqConfirmDelivery(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("retrievetokoselesai.php")
    Call<responseToko> ardRetrieveDataSelesai(
            @Field("user_id") String user_id,
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("update_status_toko.php")
    Call<responseToko> ardupdateToko(
            @Field("id_toko") String id_toko
    );

    @FormUrlEncoded
    @POST("update_finish_toko.php")
    Call<responseToko> ardfinishToko(
            @Field("id_toko") String id_toko
    );

    @FormUrlEncoded
    @POST("updateloadingstart.php")
    Call<responseUstart> ardstart(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("retrievestatusloading.php")
    Call<responseStart> cekstart(
            @Field("user_id") String user_id,
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("updateloc.php")
    Call<responseLogin> updateloc(
            @Field("user_id") String user_id,
            @Field("longitude") double longitude,
            @Field("latitude") double latitude
    );
}
