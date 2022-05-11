package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Api key = 737b6966b5ea4c958245f0075ed9bb90
public class MainActivity extends AppCompatActivity implements CategoriesAdapter.CategoryClickInterface{

    private RecyclerView CategoryRV, NewsRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<NewsCategoryModel> newsCategoryModelArrayList;
    private CategoriesAdapter categoriesAdapter;
    private newsArticlesAdapter newsArticlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoryRV = findViewById(R.id.newsCategories);
        NewsRV = findViewById(R.id.news_list);
        loadingPB = findViewById(R.id.loading);
        articlesArrayList = new ArrayList<>();
        newsCategoryModelArrayList = new ArrayList<>();
        newsArticlesAdapter = new newsArticlesAdapter(articlesArrayList,this);
        categoriesAdapter = new CategoriesAdapter(newsCategoryModelArrayList,this,this::onCategoryClick);
        NewsRV.setLayoutManager(new LinearLayoutManager(this));
        NewsRV.setAdapter(newsArticlesAdapter);
        CategoryRV.setAdapter(categoriesAdapter);
        getCategories();
        getNews("All");
        newsArticlesAdapter.notifyDataSetChanged();


    }

    private void getCategories(){
        newsCategoryModelArrayList.add(new NewsCategoryModel("All","https://images.unsplash.com/photo-1605294572486-591783f147b0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTJ8fGFsbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("Technology","https://images.unsplash.com/photo-1562408590-e32931084e23?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("Science","https://images.unsplash.com/photo-1496065187959-7f07b8353c55?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("Sports","https://images.unsplash.com/photo-1484482340112-e1e2682b4856?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fHNwb3J0c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("General","https://images.unsplash.com/photo-1512314889357-e157c22f938d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("Business","https://images.unsplash.com/photo-1460925895917-afdab827c52f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("Entertainment","https://images.unsplash.com/photo-1561174356-638d86f24f04?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        newsCategoryModelArrayList.add(new NewsCategoryModel("Health","https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));


        categoriesAdapter.notifyDataSetChanged();
    }

    private void getNews(String Category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category="+ Category +"&apiKey=737b6966b5ea4c958245f0075ed9bb90";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=737b6966b5ea4c958245f0075ed9bb90";
        String Base_URL = "https://newsapi.org/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);


        Call<NewsModel> call;

        if (Category.equals("All")){
            call = retrofitApi.getAllNews(url);
        }else {
            call = retrofitApi.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i= 0; i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),
                            articles.get(i).getUrl(),articles.get(i).getUrlToImage(),articles.get(i).getContent()));
                    newsArticlesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = newsCategoryModelArrayList.get(position).getCategories();
        getNews(category);
    }
}