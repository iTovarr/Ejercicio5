package com.example.ejerciciolab10.ejerciciola109

import retrofit2.http.GET
import retrofit2.http.Path

interface PictogramaApiService {
    @GET("pictograms/es/search/{palabra}")
    suspend fun buscarPictograma(@Path("palabra") palabra: String): List<PictogramaModel>
}