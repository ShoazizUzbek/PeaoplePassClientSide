package com.example.peaoplepassclientside.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.JWTUtils;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.HomePageCallBacks;
import com.example.peaoplepassclientside.model.AddVisitResponse;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements HomePageCallBacks {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("token", "onCreate: "+ Constants.token);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.item_back_gr_white));
        }
        button = findViewById(R.id.order_pass_btn);
        navController = Navigation.findNavController(this,R.id.frame_main);
        bottomNavigationView = findViewById(R.id.bottom_nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                return;
            }
        });
        Font.montSerrat(this,bottomNavigationView);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ic_home:
                    navController.popBackStack();
                    navController.navigate(R.id.upComingVisits);
                    return true;
                case R.id.ic_history:
                    navController.popBackStack();
                    navController.navigate(R.id.historyVisits);

                    return true;
                case R.id.ic_support:
                    navController.popBackStack();
                    navController.navigate(R.id.supportFragment);

                    return true;
                case R.id.ic_profile:
                    navController.popBackStack();
                    navController.navigate(R.id.profileFragment);

                    return true;
            }
            return false;
        }
    };

    @Override
    public void goToVisit(Item item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item",item);
        navController.navigate(R.id.visitFragment,bundle);
    }

    @Override
    public void goToHomePage() {
        navController.popBackStack();
        navController.navigate(R.id.upComingVisits);
    }


    @Override
    public void goToVisitFinish(AddVisitResponse addVisitResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("visit",addVisitResponse);
        navController.popBackStack();
        navController.navigate(R.id.aboutPassFragment,bundle);
    }



    @Override
    public void onBackPressed() {
        int cur = navController.getCurrentDestination().getId();
        if (cur == R.id.historyVisits||cur == R.id.supportFragment||cur == R.id.profileFragment){
            bottomNavigationView.setSelectedItemId(R.id.ic_home);
            navController.popBackStack();
            navController.navigate(R.id.upComingVisits);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public void gotToFilterFragment(String choose) {
        Bundle bundle = new Bundle();
        bundle.putString("choose",choose);
        navController.navigate(R.id.filterFragment,bundle);
    }
}
