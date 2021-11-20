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
import com.example.submission2_githubuser.adapter.FollowingAdapter;
import com.example.submission2_githubuser.form.DetailUserActivity;
import com.example.submission2_githubuser.model.ResponseFollowingModelItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragment extends Fragment {
    private ProgressBar progressBar;
    RecyclerView rvFollowing;
    FollowingAdapter followingAdapter;
    ArrayList<ResponseFollowingModelItem> list = new ArrayList<>();


    public FollowingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailUserActivity detailUser = (DetailUserActivity) getActivity();
        String username = detailUser.getIntent().getExtras().getString("username");

        rvFollowing = view.findViewById(R.id.rv_following);
        progressBar = view.findViewById(R.id.progressBar_following);
        rvFollowing.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar.setVisibility(View.VISIBLE);
        Call<List<ResponseFollowingModelItem>> client = ApiConfig.getApiEndPointService().getFollowing(username);
        client.enqueue(new Callback<List<ResponseFollowingModelItem>>() {
            @Override
            public void onResponse(Call<List<ResponseFollowingModelItem>> call, Response<List<ResponseFollowingModelItem>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        list.addAll(response.body());
                        followingAdapter = new FollowingAdapter(list, getContext());
                        rvFollowing.setAdapter(followingAdapter);
                    }
                } else {
                    if (response.body() != null) {
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseFollowingModelItem>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG RESULT", "onFailure: " +t.getMessage());
                Toast.makeText(getContext(), "Request Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}