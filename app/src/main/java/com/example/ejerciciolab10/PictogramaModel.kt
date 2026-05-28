package com.example.ejerciciolab10.ejerciciola109

import com.google.gson.annotations.SerializedName

data class PictogramaModel(
    @SerializedName("_id")
    val id: Int,
    val keywords: List<Keyword>? = null,
    var nameLocal: String = "",
    var categoryLocal: String = "",
    var imagenesExtraidas: List<String> = emptyList()
)

data class Keyword(
    val keyword: String
)