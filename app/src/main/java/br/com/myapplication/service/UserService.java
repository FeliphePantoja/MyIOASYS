package br.com.myapplication.service;

import br.com.myapplication.model.Catalog;
import br.com.myapplication.model.Enterprise;
import br.com.myapplication.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/auth/sign_in")
    Call<User> userLogin(@Body User user);

    @GET("enterprises/")
    Call<Enterprise> listEnterprises(
            @Header("access-token") String token,
            @Header("client") String client,
            @Header("uid") String uid);
}
