package com.example.peaoplepassclientside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.peaoplepassclientside.clallbacks.FragmentClicks;

public class MainActivity extends AppCompatActivity implements FragmentClicks {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.item_back_gr_white));

        }

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);

    }

    @Override
    public void goToEnterNumberFragment() {
        navController.popBackStack();
    }

    @Override
    public void goToVerificationFragment(String phoneNum) {
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber",phoneNum);
        navController.navigate(R.id.verificationFragment,bundle);
    }


}
