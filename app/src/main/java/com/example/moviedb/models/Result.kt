package com.example.moviedb.models

data class Result<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)