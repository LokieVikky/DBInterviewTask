package com.test.doodleblue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.test.doodleblue.databinding.LayoutMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements ItemClickListener {

    LayoutMenuBinding mBinding;
    MenuAdapter mMenuAdapter;
    Context mContext = this;
    List<MenuModel> mMenuList;
    LocalRepository mRepo;
    int mCartItemCount;
    private static final String TAG = "MenuActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.layout_menu);

        mRepo = LocalRepository.getInstance();
        mMenuList = new ArrayList<>();
        mMenuAdapter = new MenuAdapter(mMenuList, this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(mMenuAdapter);
        mBinding.btnCart.setText("View Cart");
        mBinding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCartItemCount == 0) {
                    Toast.makeText(mContext, "No Items Added", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(MenuActivity.this, CartActivity.class));
            }
        });
        setData();
    }

    @Override
    public void OnItemClick(int id, int val) {
        mRepo.updateItem(id, val);
        updateCartButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMenuAdapter != null) {
            setData();
            updateCartButton();
        }
    }

    private void updateCartButton() {
        mCartItemCount = mRepo.getTotalItemCount();
        if (mCartItemCount == 0) {
            mBinding.btnCart.setText("View Cart");
        }else if(mCartItemCount==1){
            mBinding.btnCart.setText("View Cart (1 Item)");
        }else {
            mBinding.btnCart.setText("View Cart (" + mCartItemCount + " Items)");
        }
    }

    private void setData() {
        mMenuList = mRepo.getMenuList();
        mMenuAdapter.setData(mMenuList);
    }
}