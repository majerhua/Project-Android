package com.example.pruebacodqr;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JsonPlaceHolderApi {

    @GET("disciplinaGeneral")
    Call<List<Disciplinas>> getDisciplinas();
}
