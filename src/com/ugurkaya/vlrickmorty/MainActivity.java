package com.ugurkaya.vlrickmorty;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ugurkaya.vlrickmorty.model.CharactersResponse;
import com.ugurkaya.vlrickmorty.model.Info;
import com.ugurkaya.vlrickmorty.model.Location;
import com.ugurkaya.vlrickmorty.model.Origin;
import com.ugurkaya.vlrickmorty.model.Result;
import com.ugurkaya.vlrickmorty.network.RetrofitClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Result> resultList = new ArrayList<Result>();
    Button next, prev;


    RickMortyViewModel rickMortyViewModel;
    RecyclerView resultsRecyclerView;

    private int totalPage = 0;
    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);



        LinearLayoutManager linearLayoutManagerOne = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultsRecyclerView.setLayoutManager(linearLayoutManagerOne);
        rickMortyViewModel = new RickMortyViewModel(resultList, this, resultsRecyclerView, new RickMortyViewModel.RecyclerOnItemClickListenerInterface() {
            @Override
            public void onItemClick(Result item, int position) {

                Location location;
                Origin origin;
                location = new Location();
                List<String> episodes;
                origin = new Origin();
                episodes = new ArrayList<String>();

                origin = item.getOrigin();
                episodes = item.getEpisode();


                location = item.getLocation();
                Intent intent = new Intent(MainActivity.this, CharacterDetailActivity.class);
                intent.putExtra("episodes",(Serializable) episodes);
                intent.putExtra("location", location.getUrl());
                intent.putExtra("gender", item.getGender());
                intent.putExtra("status", item.getStatus());
                intent.putExtra("name",item.getName());
                intent.putExtra("type", item.getType());
                intent.putExtra("species", item.getSpecies());
                intent.putExtra("image",item.getImage());


                intent.putExtra("origin", origin.getName());



                startActivity(intent);
            }
        });
        resultsRecyclerView.setAdapter(rickMortyViewModel);
        rickMortyViewModel.notifyDataSetChanged();

        try{
            getCharacters(pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pageNum != totalPage){
                    pageNum++;
                    getCharacters(pageNum);
                }

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pageNum != 1){
                    pageNum--;
                    getCharacters(pageNum);
                }
            }
        });




    }


    private void getCharacters(int pageNum){
        RetrofitClient.getApi().getCharacters(pageNum).enqueue(new Callback<CharactersResponse>() {
            @Override
            public void onResponse(Call<CharactersResponse> call, Response<CharactersResponse> response) {
                   if (response.isSuccessful()){
                    if (response.body() != null){
                        Info info = new Info();
                        info = response.body().getInfo();
                        resultList = response.body().getResults();
                        totalPage = info.getPages();

                        rickMortyViewModel.setResults(resultList);


                        if(pageNum != 1){
                            prev.setAlpha(1.0f);
                        }else{
                            prev.setAlpha(0.2f);
                        }
                        if(pageNum == totalPage){
                            next.setAlpha(0.2f);
                        }else{
                            next.setAlpha(1.0f);
                        }
                    }else{
                        rickMortyViewModel.setResults(new ArrayList<Result>());
                    }

                }else{
                       rickMortyViewModel.setResults(new ArrayList<Result>());
                   }


            }

            @Override
            public void onFailure(Call<CharactersResponse> call, Throwable t) {

            }
        });
    }


}