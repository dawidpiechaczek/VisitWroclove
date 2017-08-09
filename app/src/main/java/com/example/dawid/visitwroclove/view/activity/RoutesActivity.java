package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerRoutesAdapter;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 09.08.2017.
 */

public class RoutesActivity extends BaseActivity {

    @BindView(R.id.ar_rv_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.ar_t_toolbar)
    Toolbar toolbar;
    @Inject
    public ObjectDAOImpl mRepo;

    private RecyclerRoutesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        ButterKnife.bind(this);
        getComponent().inject(this);
        setToolbar();

        adapter = new RecyclerRoutesAdapter(this);
        recyclerView.setAdapter(adapter);
        setCardViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case (android.R.id.home):
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

    private void setCardViews() {
        int numberOfColumns = 2;
        if (this.getApplicationContext().getResources().getConfiguration().orientation ==
                this.getApplicationContext().getResources().getConfiguration()
                        .ORIENTATION_LANDSCAPE)
            numberOfColumns = 3;
        this.recyclerView.setLayoutManager(new GridLayoutManager
                (this.getApplicationContext(),
                        numberOfColumns,
                        GridLayoutManager.VERTICAL, false));

        adapter.setOnClickListener(new RecyclerRoutesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(RoutesActivity.this, RoutesListActivity.class);
               // intent.putExtra(Constants.EXTRA_POSIOTION, position);
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar.setTitle("Trasy");
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
