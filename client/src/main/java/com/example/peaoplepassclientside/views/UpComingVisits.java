package com.example.peaoplepassclientside.views;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.adapter.VisitorsAdapter;
import com.example.peaoplepassclientside.clallbacks.AdapterCallBack;
import com.example.peaoplepassclientside.clallbacks.FragmentClicks;
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
public class UpComingVisits extends Fragment  implements AdapterCallBack {


    public UpComingVisits() {
        // Required empty public constructor
    }
    Button button;
    SearchView searchView;
    Toolbar toolbar;
    RelativeLayout relativeLayout;
    HomePageCallBacks fragmentClicks;
    RecyclerView recyclerView;
    TextView emptyTxt,txtSorting,txtSortingByDate;
    ProgressBar progressBar;
    VisitorsAdapter visitorsAdapter;
    String sortData=" ";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof HomePageCallBacks)
            fragmentClicks = (HomePageCallBacks) getActivity();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
             sortData = getArguments().getString("sort");
        }
        setHasOptionsMenu(true);
    }

    NavController navController;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {

                searchView.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_bg));

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                return true;
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_up_coming_visits, container, false);
        txtSorting = v.findViewById(R.id.soring_txt_upcoming);
        txtSortingByDate = v.findViewById(R.id.sorting_by_date);
        button = v.findViewById(R.id.order_pass_btn);
        navController = Navigation.findNavController(getActivity(),R.id.frame_main);
        progressBar = v.findViewById(R.id.progress_upcoming);
        emptyTxt = v.findViewById(R.id.empty_txt_upcoming);
        recyclerView = v.findViewById(R.id.recycler_upcoming);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toolbar = (Toolbar) v.findViewById(R.id.toolbar_upcoming);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        

        relativeLayout = v.findViewById(R.id.top_layout_upcoming);
        Font.montSerrat(getContext(),emptyTxt);
        Font.montSerrat(getContext(),emptyTxt);
        Font.montSerrat(getContext(),emptyTxt);
        Font.montSerrat(getContext(),emptyTxt);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClicks.gotToFilterFragment("a");
            }
        });
        Sort sort = new Sort();
        if (sortData.equals("a")){
            sort.setVisitDate("ASC");
        }else if (sortData.equals("b")){
            sort.setVisitorSurname("DESC");
        }else if(sortData.equals("c")){
            sort.setVisitorSurname("ASC");
        }else {
            sort.setVisitDate("DESC");
        }

        loadUpcomingVisitorsList(sort);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.orderPass);
            }
        });

    }

    private void loadUpcomingVisitorsList(Sort sort) {
        ApiInterFace apiInterface = ApiClient.apiRequest().create(ApiInterFace.class);
        BodyVisitors bodyVisitors = new BodyVisitors(1,10,sort,new Filters(new State("EQUAL","COMPLETED")));
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
                    visitorsAdapter.setAdapterCallBack(UpComingVisits.this);
                    recyclerView.setAdapter(visitorsAdapter);
                    Log.d("tagged", "onResponse: "+visitorsResult.getTotal());
                }else {
                    Log.d("tagged", "onResponse: "+response.isSuccessful());
                }
            }

            @Override
            public void onFailure(Call<VisitorsResult> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                emptyTxt.setVisibility(View.GONE);
                Log.d("tagged", "onFailure: "+t.getMessage());
                Toast.makeText(getContext(), "Ошибка с интернетом", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void getItem(Item item) {
        if (item.getState().equals("NEW")){
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",item);
            navController.navigate(R.id.receiverFragment,bundle);
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",item);
            navController.navigate(R.id.visitFragment,bundle);
        }

    }

    @Override
    public void returnEmploye(ResponseEmploye.EmployeList employeList) {

    }
}
