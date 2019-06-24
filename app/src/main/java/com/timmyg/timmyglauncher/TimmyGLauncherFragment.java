package com.timmyg.timmyglauncher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimmyGLauncherFragment extends Fragment {

    private RecyclerView mRecyclerView;


    public static TimmyGLauncherFragment newInstance(){
        return new TimmyGLauncherFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timmy_launcher, container, false);
        mRecyclerView = v.findViewById(R.id.fragment_timmy_launcher_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }
}
