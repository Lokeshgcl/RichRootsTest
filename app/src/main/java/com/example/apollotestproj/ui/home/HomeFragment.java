package com.example.apollotestproj.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apollotestproj.Adapter.CustomAdapter;
import com.example.apollotestproj.R;
import com.example.apollotestproj.Util.EndlessRecyclerViewScrollListener;
import com.example.apollotestproj.ui.VM.ItemCenterVM;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private CustomAdapter customAdapter;
    List<ItemCenterVM> lstItemCenterVM;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        RecyclerView rView = (RecyclerView) root.findViewById(R.id.recyclerView);
        rView.invalidate();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(layoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.v(" loading more Data "," have to load more Items page: " + page + " totalItemsCount: " + totalItemsCount);
                loadNextDataFromApi(page, totalItemsCount, view);
            }
        };
        // Adds the scroll listener to RecyclerView
        rView.addOnScrollListener(scrollListener);

        lstItemCenterVM = new ArrayList<ItemCenterVM>();
        for (int i = 0; i<20; i++){
            ItemCenterVM itemCenterVM = new ItemCenterVM();
            itemCenterVM.setMarketName("test Market " + i);
            itemCenterVM.setProductName("test Product " + i);
            itemCenterVM.setQuantity(25 + i);
            itemCenterVM.setQuantityQualifier("pieces " + i);
            itemCenterVM.setMinPrice(200 + i);
            itemCenterVM.setMaxPrice(1500 + i);
            lstItemCenterVM.add(itemCenterVM);
        }

        customAdapter = new CustomAdapter(getContext(), lstItemCenterVM);
        rView.setAdapter(customAdapter);

        //Here code

//        ItemCenterVM itemCenterVM = new ItemCenterVM();
//        itemCenterVM.setMarketName("test Market");
//        itemCenterVM.setProductName("test Product");
//        itemCenterVM.setQuantity(25);
//        itemCenterVM.setQuantityQualifier("pieces");
//        itemCenterVM.setMinPrice(200);
//        itemCenterVM.setMaxPrice(1500);
//        lstItemCenterVM.add(itemCenterVM);

//        customAdapter.notifyItemRangeInserted(1,10);
//        customAdapter.notifyItemRemoved(1);
//        customAdapter.notifyItemChanged(1);
//        customAdapter.notifyItemInserted(1);

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset, int totalItemsCount, RecyclerView view) {
        Log.v(" loading ","loading more Items " + offset);
        Log.v(" totalItemsCount "," Items " + totalItemsCount);
        Log.v("adapter.getItemCount "," Items " + customAdapter.getItemCount());
        final int curSize = customAdapter.getItemCount();
        int start = offset * 20;
        List<ItemCenterVM> items = new ArrayList<ItemCenterVM>();
        for (int i = start; i<start + 20; i++){
            ItemCenterVM itemCenterVM = new ItemCenterVM();
            itemCenterVM.setMarketName("test Market " + i);
            itemCenterVM.setProductName("test Product " + i);
            itemCenterVM.setQuantity(25 + i);
            itemCenterVM.setQuantityQualifier("pieces " + i);
            itemCenterVM.setMinPrice(200 + i);
            itemCenterVM.setMaxPrice(1500 + i);
            items.add(itemCenterVM);
        }
        lstItemCenterVM.addAll(items);
        view.post(new Runnable() {
            @Override
            public void run() {
                customAdapter.notifyItemRangeInserted(curSize, lstItemCenterVM.size() - 1);
            }
        });

        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }
}