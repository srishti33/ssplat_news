package com.example.kinnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Toast;

import com.example.kinnect.api.ApiInterface;
import com.example.kinnect.api.AppClient;
import com.example.kinnect.models.Article;
import com.example.kinnect.models.headlines;

import java.util.ArrayList;
import java.util.List;

public class news extends AppCompatActivity {
    public static final String API_KEY="55290baa63fa4399b5f90139f5c39b58";
    private  RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> article=new ArrayList<>();
    private Adapter adapter;
    private String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView=findViewById(R.id.recycle);
        layoutManager=new LinearLayoutManager(news.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();
    }
    public void LoadJson(){
        ApiInterface apiInterface=AppClient.getApiClient().create(ApiInterface.class);
        String country= Utils.getCountry();
        Call<headlines> call;
        call=apiInterface.getNews(country,API_KEY);
        call.enqueue(new Callback<headlines>() {
            @Override
            public void onResponse(Call<headlines> call, Response<headlines> response) {
                if(response.isSuccessful()&& response.body().getArticle()!=null){
                    if(!article.isEmpty()){
                        article.clear();
                    }
                    article=response.body().getArticle();
                    adapter=new Adapter(article,news.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(news.this,"No result!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<headlines> call, Throwable t) {

            }
        });
        }
    }
