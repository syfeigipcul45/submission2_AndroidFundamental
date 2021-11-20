package com.example.submission2_githubuser.form;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.submission2_githubuser.R;
import com.example.submission2_githubuser.Service.ApiConfig;
import com.example.submission2_githubuser.adapter.PageAdapter;
import com.example.submission2_githubuser.model.DetailUserModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_follower,
            R.string.tab_following
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_detail);
        progressBar = findViewById(R.id.progressBar_detail);

        String username = getIntent().getExtras().getString("username");
        Call<DetailUserModel> client = ApiConfig.getApiEndPointService().getDetailUser(username);
        client.enqueue(new Callback<DetailUserModel>() {
            @Override
            public void onResponse(Call<DetailUserModel> call, Response<DetailUserModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ImageView avatar = findViewById(R.id.img_avatar_detail);
                        TextView username = findViewById(R.id.tv_username_detail);
                        TextView name = findViewById(R.id.tv_name_detail);
                        TextView company = findViewById(R.id.tv_company_detail);
                        TextView location = findViewById(R.id.tv_location_detail);
                        TextView repository = findViewById(R.id.tv_repository_detail);
                        TextView followers = findViewById(R.id.tv_followers_detail);
                        TextView following = findViewById(R.id.tv_following_detail);

                        int repos = response.body().getPublicRepos();
                        int numFollowers = response.body().getFollowers();
                        int numFolling = response.body().getFollowing();
                        String public_repos = String.valueOf(repos);

                        Glide.with(DetailUserActivity.this)
                                .load(response.body().getAvatarUrl())
                                .circleCrop()
                                .into(avatar);
                        username.setText(response.body().getLogin());
                        name.setText(response.body().getName());
                        company.setText(response.body().getCompany());
                        location.setText(response.body().getLocation());
                        repository.setText(public_repos + " " + getString(R.string.repository));
                        followers.setText(String.valueOf(numFollowers));
                        following.setText(String.valueOf(numFolling));
                    }
                } else {
                    if (response.body() != null) {
                        Toast.makeText(DetailUserActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailUserModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailUserActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        PageAdapter pageAdapter = new PageAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getResources()
                        .getString(TAB_TITLES[position]))
        ).attach();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail User");
            getSupportActionBar().setElevation(0);
        }
    }
}
