package com.example.ktoradapter



object constants {
    const val URL: String = "https://dog.ceo/api/breed"
    public fun String.getByName(type: String): String {
        return "$URL/$type/images"
    }
}