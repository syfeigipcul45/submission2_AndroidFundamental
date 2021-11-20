package com.example.submission2_githubuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.submission2_githubuser.Service.ApiConfig;
import com.example.submission2_githubuser.adapter.GithubUserAdapter;
import com.example.submission2_githubuser.form.DetailUserActivity;
import com.example.submission2_githubuser.model.ItemsItem;
import com.example.submission2_githubuser.model.UserSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GithubUserAdapter githubUserAdapter;
    ProgressBar progressBar;
    RecyclerView rvUser;
    List<ItemsItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvUser = findViewById(R.id.rv_githubUser);
        progressBar = findViewById(R.id.progressBar);
        rvUser.setHasFixedSize(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Github User");
        }
        showRecyclerList();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String username) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String username) {
                    getUserSearch(username);
                    return true;
                }
            });
        }
        return true;
    }

    public void getUserSearch(String username) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            Call<UserSearch> client = ApiConfig.getApiEndPointService().getUserSearch(username);
            client.enqueue(new Callback<UserSearch>() {
                @Override
                public void onResponse(Call<UserSearch> call, Response<UserSearch> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            githubUserAdapter.setListUser(response.body().getItems());
                            githubUserAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (response.body() != null) {
                            Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserSearch> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRecyclerList() {
        githubUserAdapter = new GithubUserAdapter(list, this);
        rvUser.setAdapter(githubUserAdapter);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
    }
}