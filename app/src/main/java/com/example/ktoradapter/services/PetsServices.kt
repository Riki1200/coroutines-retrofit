package com.example.ktoradapter.services

import com.example.ktoradapter.model.Pets
import com.example.ktoradapter.model.SomePets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PetServices {
    @GET
    suspend fun getDogsByName(@Url url: String): Response<Pets>

    @GET
    suspend fun getDogs(@Url url: String): Response<SomePets>
}