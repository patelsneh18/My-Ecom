package com.streamliners.myecom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.streamliners.myecom.databinding.ActivityMainBinding;
import com.streamliners.myecom.databinding.ItemVbProductBinding;
import com.streamliners.myecom.databinding.ItemWbProductBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ItemVbProductBinding vbProductBinding;
    ItemWbProductBinding wbProductBinding;
    int currentQty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showItems();
    }

    void showItems(){
        vbProductBinding = ItemVbProductBinding.inflate(getLayoutInflater());
        vbProductBinding.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vbProductBinding.variantsName.getVisibility() == View.GONE) {
                    vbProductBinding.variantsName.setVisibility(View.VISIBLE);
                    vbProductBinding.dropdown.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                } else {
                    vbProductBinding.variantsName.setVisibility(View.GONE);
                    vbProductBinding.dropdown.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                }
            }
        });


        vbProductBinding.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vbProductBinding.currentQty.setText(++currentQty + "");
                vbProductBinding.nonZeroQtyGrp.setVisibility(View.VISIBLE);
            }
        });

        vbProductBinding.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQty--;
                vbProductBinding.currentQty.setText(currentQty + "");
                if (currentQty == 0)
                    vbProductBinding.nonZeroQtyGrp.setVisibility(View.GONE);
            }
        });
        binding.list.addView(vbProductBinding.getRoot());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vbProductBinding.imageLoader.setVisibility(View.GONE);
                vbProductBinding.productImage.setVisibility(View.VISIBLE);
            }
        }, 2000);

        wbProductBinding = ItemWbProductBinding.inflate(getLayoutInflater());
        wbProductBinding.nonZeroQtyGroup.setVisibility(View.GONE);
        wbProductBinding.addBtn.setVisibility(View.VISIBLE);
        binding.list.addView(wbProductBinding.getRoot());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wbProductBinding.progressBar.setVisibility(View.GONE);
                wbProductBinding.productImage.setVisibility(View.VISIBLE);
            }
        }, 2000);

        for (int i=0; i<12; i++){
            ItemVbProductBinding b = ItemVbProductBinding.inflate(getLayoutInflater());
            binding.list.addView(b.getRoot());
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    assert b.imageLoader != null;
                    b.imageLoader.setVisibility(View.GONE);
                    b.productImage.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }
    }
}