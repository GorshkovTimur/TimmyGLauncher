package com.timmyg.timmyglauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimmyGLauncherFragment extends Fragment {

    private static final String TAG = "TimmyGFragment";

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
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent,0);
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(o1.loadLabel(pm).toString(),o2.loadLabel(pm).toString());
            }
        });

        Log.i(TAG," " + activities.size());
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }


    public class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);

        }

        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent i = new Intent(Intent.ACTION_MAIN).setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);

            startActivity(i);
        }
    }

        private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

            private final List<ResolveInfo> mActivities;

            public ActivityAdapter(List<ResolveInfo> mActivities) {
                this.mActivities = mActivities;
            }

            @NonNull
            @Override
            public ActivityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                return new ActivityHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ActivityHolder activytyHolder, int i) {
            ResolveInfo resolveInfo = mActivities.get(i);
            activytyHolder.bindActivity(resolveInfo);
            }

            @Override
            public int getItemCount() {
                return mActivities.size();
            }
        }

    }


