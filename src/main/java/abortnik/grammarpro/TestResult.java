package abortnik.grammarpro;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import abortnik.grammarpro.data.ProgressBarAnimation;

import static abortnik.grammarpro.iHomeActivity.MY_PREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestResult extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<ResultConstructor> resultList = new ArrayList<>();

    public TestResult() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_result);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, 40);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ResultList", "");
        Type type = new TypeToken<List<ResultConstructor>>(){}.getType();
        resultList = gson.fromJson(json, type);
        if(resultList.size() > 0) {
           mAdapter = new ResultAdapter(resultList);
           mRecyclerView.setAdapter(mAdapter);
       } else {
           Toast.makeText(getActivity(), "Size is 0", Toast.LENGTH_SHORT).show();
       }



        // specify an adapter (see also next example)

    }

    private void prepareResults(Runnable runnable) {
        ResultConstructor resultConstructor = new ResultConstructor("Whare are you doing?", "0.30", true);
        resultList.add(resultConstructor);
        resultConstructor = new ResultConstructor("Whats your name?", "0.56s", false);
        resultList.add(resultConstructor);
        runnable.run();
    }
}
