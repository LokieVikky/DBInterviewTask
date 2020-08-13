package com.test.doodleblue;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.test.doodleblue.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.List;

import static com.test.doodleblue.MenuModel.TYPE_FOOTER;

public class CartActivity extends AppCompatActivity implements ItemClickListener, CartAdapter.ShowMoreClickListener {
    static int DEFAULT_ITEMS_TO_SHOW = 2;
    ActivityCartBinding mBinding;
    CartAdapter mCartAdapter;
    Context mContext = this;
    List<MenuModel> mCartList;
    LocalRepository mRepo;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mRepo = LocalRepository.getInstance();
        mCartList = new ArrayList<>();
        mCartAdapter = new CartAdapter(mCartList, this, this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(mCartAdapter);
        mCartList = mRepo.getCartItemList();
        loadData();
        mBinding.txtPrice.setText("€" + mRepo.getPayableAmount());
        mBinding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Order placed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        if (mCartList.size() > DEFAULT_ITEMS_TO_SHOW) {
            showMinimalData();
        } else {
            showMore();
        }
    }

    private void showMinimalData() {
        List<MenuModel> tempList = new ArrayList<>();
        int i = 0;
        while (i < DEFAULT_ITEMS_TO_SHOW) {
            tempList.add(mCartList.get(i));
            i++;
        }
        tempList.add(new MenuModel(TYPE_FOOTER));
        mCartAdapter.setData(tempList);
    }

    private void showMore() {
        mCartAdapter.setData(mCartList);
    }

    @Override
    public void OnItemClick(int id, int val) {
        mRepo.updateItem(id, val);
        mBinding.txtPrice.setText("€" + mRepo.getPayableAmount());
    }

    @Override
    public void OnShowMoreClicked() {
        showMore();
    }
}