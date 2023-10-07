package com.example.anaghafish.user.userui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.anaghafish.R;
import com.example.anaghafish.cartactivity.ProductFragment;
import com.example.anaghafish.user.adapter.AdvertisementAdapter;
import com.example.anaghafish.user.adapter.FishProductAdapter;
import com.example.anaghafish.user.model.FishProduct;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPager viewPager;
    private int currentPage = 0;
    private Timer timer;
    final long delay = 5000;

    private RecyclerView recyclerView;
    private FishProductAdapter adapter;
    private CartFragment cartFragment;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // RecyclerView and FishProduct list
        recyclerView = view.findViewById(R.id.rawFishRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<FishProduct> fishProducts = new ArrayList<>();
        fishProducts.add(new FishProduct("Ravas", 900, R.drawable.fish1));
        fishProducts.add(new FishProduct("Surmai", 900, R.drawable.fish2));
        fishProducts.add(new FishProduct("Bombill", 300, R.drawable.fish3));
        fishProducts.add(new FishProduct("Prawn", 500, R.drawable.fish4));
        fishProducts.add(new FishProduct("Paplet", 400, R.drawable.raw));
        fishProducts.add(new FishProduct("fish", 350, R.drawable.driedfish));
        fishProducts.add(new FishProduct("fish2", 450, R.drawable.dish));
        // Add more fish products as needed

        adapter = new FishProductAdapter(fishProducts);
        recyclerView.setAdapter(adapter);

        // ViewPager for advertisements
        viewPager = view.findViewById(R.id.adv);
        AdvertisementAdapter viewPagerAdapter = new AdvertisementAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewPagerAdapter.getCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, delay, delay);

        // ShapeableImageView for navigation to ProductFragment
        ShapeableImageView rawCategoryImageView = view.findViewById(R.id.rawcategory);

        // Initialize your CartFragment
        cartFragment = new CartFragment();

        rawCategoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the initialized cartFragment to ProductFragment
                ProductFragment productFragment = new ProductFragment(cartFragment);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, productFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
