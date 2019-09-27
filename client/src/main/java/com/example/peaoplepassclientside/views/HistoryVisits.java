package com.example.peaoplepassclientside.views;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.adapter.VisitorsAdapter;
import com.example.peaoplepassclientside.clallbacks.AdapterCallBack;
import com.example.peaoplepassclientside.clallbacks.HomePageCallBacks;
import com.example.peaoplepassclientside.model.BodyVisitors;
import com.example.peaoplepassclientside.model.Filters;
import com.example.peaoplepassclientside.model.ResponseEmploye;
import com.example.peaoplepassclientside.model.Sort;
import com.example.peaoplepassclientside.model.State;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitorsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryVisits extends Fragment implements AdapterCallBack {


    public HistoryVisits() {
        // Required empty public constructor
    }
    SearchView searchView;
    Toolbar toolbar;
    RelativeLayout relativeLayout;
    HomePageCallBacks fragmentClicks;
    RecyclerView recyclerView;
    TextView emptyTxt,toolbarTxt,sortingTxt,sortingHistory;
    ProgressBar progressBar;
    VisitorsAdapter visitorsAdapter;
    NavController navController;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof HomePageCallBacks)
            fragmentClicks = (HomePageCallBacks) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {

               // searchView.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_rectangle_2));

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
               // toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                return true;
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history_visits, container, false);
        navController = Navigation.findNavController(getActivity(),R.id.frame_main);

        progressBar = v.findViewById(R.id.progress_history);
        emptyTxt = v.findViewById(R.id.empty_txt_history);
        sortingTxt  = v.findViewById(R.id.soring_txt_history);
        sortingHistory = v.findViewById(R.id.sorting_history_text);
        toolbarTxt = v.findViewById(R.id.txt_history_toolbar);
        Font.montSerrat(getContext(),emptyTxt);
        Font.montSerrat(getContext(),sortingTxt);
        Font.montSerrat(getContext(),sortingHistory);
        Font.montSerratMedium(getContext(),toolbarTxt);
        recyclerView = v.findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toolbar = (Toolbar) v.findViewById(R.id.toolbar_history);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        relativeLayout = v.findViewById(R.id.top_layout_history);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClicks.gotToFilterFragment("b");
            }
        });
        Sort sort = new Sort();
        sort.setVisitDate("DESC");
        loadHistoryVisits(sort);
    }

    private void loadHistoryVisits(Sort sort) {
        ApiInterFace apiInterface = ApiClient.apiRequest().create(ApiInterFace.class);
        BodyVisitors bodyVisitors = new BodyVisitors(1,10,sort,new Filters(new State("EQUAL","ON_BOARD")));
        Call<VisitorsResult> call = apiInterface.getVisitors(Constants.token,bodyVisitors);
        call.enqueue(new Callback<VisitorsResult>() {
            @Override
            public void onResponse(Call<VisitorsResult> call, Response<VisitorsResult> response) {
                progressBar.setVisibility(View.GONE);

                Log.d("tagged", "msg: "+response.message());
                if (response.isSuccessful()){

                    VisitorsResult visitorsResult = response.body();
                    if (visitorsResult.getTotal() > 0){
                        emptyTxt.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                    visitorsAdapter = new VisitorsAdapter(visitorsResult.getItems(),getContext());
                    visitorsAdapter.setAdapterCallBack(HistoryVisits.this);
                    recyclerView.setAdapter(visitorsAdapter);
                    Log.d("tagged", "onResponse: "+visitorsResult.getTotal());
                }else {
                    Log.d("tagged", "onResponse: "+response.isSuccessful());
                }
            }

            @Override
            public void onFailure(Call<VisitorsResult> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
               // emptyTxt.setVisibility(View.GONE);
                Log.d("tagged", "onFailure: ");
                Toast.makeText(getContext(), "Ошибка с интернетом", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getItem(Item item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",item);
        navController.navigate(R.id.visitFragment,bundle);
    }

    @Override
    public void returnEmploye(ResponseEmploye.EmployeList employeList) {

    }
}
