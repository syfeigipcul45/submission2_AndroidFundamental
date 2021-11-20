package com.example.submission2_githubuser.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.submission2_githubuser.R;
import com.example.submission2_githubuser.Service.ApiConfig;
import com.example.submission2_githubuser.adapter.FollowersAdapter;
import com.example.submission2_githubuser.form.DetailUserActivity;
import com.example.submission2_githubuser.model.ResponseFollowersModelItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerFragment extends Fragment {
    private ProgressBar progressBar;
    RecyclerView rvFollower;
    FollowersAdapter followersAdapter;
    ArrayList<ResponseFollowersModelItem> list = new ArrayList<>();

    public FollowerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailUserActivity detailUser = (DetailUserActivity) getActivity();
        String username = detailUser.getIntent().getExtras().getString("username");

        rvFollower = view.findViewById(R.id.rv_follower);
        progressBar = view.findViewById(R.id.progressBar_follower);
        rvFollower.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar.setVisibility(View.VISIBLE);
        Call<List<ResponseFollowersModelItem>> client = ApiConfig.getApiEndPointService().getFollowers(username);
        client.enqueue(new Callback<List<ResponseFollowersModelItem>>() {
            @Override
            public void onResponse(Call<List<ResponseFollowersModelItem>> call, Response<List<ResponseFollowersModelItem>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        list.addAll(response.body());
                        followersAdapter = new FollowersAdapter(list, getContext());
                        rvFollower.setAdapter(followersAdapter);
                    }
                } else {
                    if (response.body() != null) {
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseFollowersModelItem>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG RESULT", "onFailure: " +t.getMessage());
                Toast.makeText(getContext(), "Request Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}