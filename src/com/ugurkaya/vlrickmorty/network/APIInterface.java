package com.ugurkaya.vlrickmorty.network;

import com.ugurkaya.vlrickmorty.model.CharactersResponse;
import com.ugurkaya.vlrickmorty.model.EpisodeResponse;
import com.ugurkaya.vlrickmorty.model.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {


    /**
     *
     * @param pageNum page number - it's used for pagination(prev/next buttons)
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("character")
    Call<CharactersResponse> getCharacters(@Query("page") int pageNum);


    /**
     *
     * @param episode episodeNumber - keeps episode information
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("episode/{episode}")
    Call<EpisodeResponse> getEpisode(@Path("episode") String episode);


    /**
     *
     * @param location locationNumber - keeps location information
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("location/{location}")
    Call<LocationResponse> getLocation(@Path("location") String location);






}
