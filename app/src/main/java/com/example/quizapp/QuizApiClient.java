package com.example.quizapp;

import com.example.quizapp.QuizApiResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class QuizApiClient {
    private static final String BASE_URL = "https://opentdb.com/";
    private static QuizApiService quizApiService;

    public static QuizApiService getQuizApiService() {
        if (quizApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            quizApiService = retrofit.create(QuizApiService.class);
        }
        return quizApiService;
    }

    public interface QuizApiService {
        @GET("api.php?")
        Call<QuizApiResponse> getQuizzes(
                @Query("amount") int amount,
                @Query("category") int category,
                @Query("difficulty") String difficulty,
                @Query("type") String type
        );
    }
}
