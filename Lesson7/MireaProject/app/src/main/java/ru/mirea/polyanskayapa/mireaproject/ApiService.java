package ru.mirea.polyanskayapa.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts/1")
    Call<Post> getPost();
}