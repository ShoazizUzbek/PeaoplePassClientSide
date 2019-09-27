package com.example.peaoplepassclientside.views;


import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.HomePageCallBacks;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements View.OnClickListener{


    public FilterFragment() {
        // Required empty public constructor
    }
    RelativeLayout r1,r2,r3,r4,r5;
    ImageView img1,img2,img3,img4,img5,img;
    HomePageCallBacks  fragmentClicks;
    NavController navController;
    TextView txt1, txt2,txt3,txt4,txt5,tool;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof HomePageCallBacks)
            fragmentClicks = (HomePageCallBacks) getActivity();
    }
    Bundle bundle;
    String choose;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            choose  = getArguments().getString("choose");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_filter, container, false);


        r1 = v.findViewById(R.id.filter1);
        r2 = v.findViewById(R.id.filter2);
        r3 = v.findViewById(R.id.filter3);
        r4 = v.findViewById(R.id.filter4);
        r5 = v.findViewById(R.id.filter5);
        img = v.findViewById(R.id.back_filter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        navController = Navigation.findNavController(getActivity(),R.id.frame_main);
        img1 = v.findViewById(R.id.sort_one);
        img2 = v.findViewById(R.id.sort_two);
        img3 = v.findViewById(R.id.sort_three);
        img4 = v.findViewById(R.id.sort_four);
        img5 = v.findViewById(R.id.sort_five);
        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);
        r5.setOnClickListener(this);
        txt1 = v.findViewById(R.id.sort_txt_one);
        txt2 = v.findViewById(R.id.sort_txt_two);
        txt3 = v.findViewById(R.id.sort_txt_three);
        txt4 = v.findViewById(R.id.sort_txt_four);
        txt5 = v.findViewById(R.id.sort_txt_five);
        Font.montSerrat(getContext(),txt1);
        Font.montSerrat(getContext(),txt2);
        Font.montSerrat(getContext(),txt3);
        Font.montSerrat(getContext(),txt4);
        Font.montSerrat(getContext(),txt5);
        tool = v.findViewById(R.id.tool_filter);
        Font.montSerrat(getContext(),tool);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter1 :
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
                img5.setVisibility(View.INVISIBLE);
                navController.popBackStack();
                 bundle = new Bundle();
                bundle.putString("sort","a");
                if (choose.equals("a")){
                    navController.navigate(R.id.upComingVisits,bundle);
                }else {
                    navController.navigate(R.id.historyVisits,bundle);
                }


                break;

            case R.id.filter2 :
                img2.setVisibility(View.VISIBLE);
                img1.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
                img5.setVisibility(View.INVISIBLE);
                navController.popBackStack();
                 bundle = new Bundle();
                bundle.putString("sort","b");
                if (choose.equals("a")){
                    navController.navigate(R.id.upComingVisits,bundle);
                }else {
                    navController.navigate(R.id.historyVisits,bundle);
                }
                break;

            case R.id.filter3 :
                img3.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img1.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
                img5.setVisibility(View.INVISIBLE);
                navController.popBackStack();
                bundle = new Bundle();
                bundle.putString("sort","c");
                if (choose.equals("a")){
                    navController.navigate(R.id.upComingVisits,bundle);
                }else {
                    navController.navigate(R.id.historyVisits,bundle);
                }
                break;

            case R.id.filter4 :
                img4.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img1.setVisibility(View.INVISIBLE);
                img5.setVisibility(View.INVISIBLE);
                navController.popBackStack();
                bundle = new Bundle();
                bundle.putString("sort","d");
                if (choose.equals("a")){
                    navController.navigate(R.id.upComingVisits,bundle);
                }else {
                    navController.navigate(R.id.historyVisits,bundle);
                }
                break;

            case R.id.filter5:
                img5.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
                img1.setVisibility(View.INVISIBLE);
                navController.popBackStack();
                bundle = new Bundle();
                bundle.putString("sort","e");
                if (choose.equals("a")){
                    navController.navigate(R.id.upComingVisits,bundle);
                }else {
                    navController.navigate(R.id.historyVisits,bundle);
                }
                break;
        }
    }

}
