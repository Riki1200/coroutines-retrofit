package com.example.ktoradapter.model
import com.google.gson.annotations.SerializedName;


data class Pets(
    @SerializedName("status") var status: String,
    @SerializedName("message") var messages: List<String>,
  //  @SerializedName("message") var msg: List<Map<String, List<String>>>

    )
data class SomePets(
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: Map<String, List<String>>
)
