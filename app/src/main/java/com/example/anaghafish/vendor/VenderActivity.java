package com.example.anaghafish.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;
import com.example.anaghafish.R;

public class VenderActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);
        //meow bottom navigation
        bottomNavigation = findViewById(R.id.bottomNavigation);

        //navigation icon
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.baseline_home_24));
      //  bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.category));
      //  bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_settings_24));

        meownavigation();// calling meow function
        bottomNavigation.show(1, true);// 1 is the ID of the Home item
        replaceFragment(new vendorhomefragment());//for default page

        //slider

    }//----------on create end---------

    //meow function
    private void meownavigation(){
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model)
            {

                switch (model.getId()){

                    case 1:
                        replaceFragment(new vendorhomefragment());
                        break;
                    case 2:
                        replaceFragment(new vendorsettingfragment());
                        break;
                 //   case 3:
                  //      replaceFragment(new CartFragment());
                 //       break;
                //    case 4:
                //        replaceFragment(new ProfileFragment());
                 //       break;

                }
                return null;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Begin a transaction to replace the current fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new one
        transaction.replace(R.id.fragment_container, fragment);

        // Commit the transaction
        transaction.commit();
    }
    //ending meow
}