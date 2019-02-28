package com.example.giftishare.view.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.giftishare.Event;
import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.data.model.CouponsCategoryType;
import com.example.giftishare.utils.ActivityUtils;
import com.example.giftishare.view.addcoupon.AddCouponActivity;
import com.example.giftishare.view.buysellcoupons.BuySellCouponsActivity;
import com.example.giftishare.view.onsalecoupons.OnSaleCouponsActivity;

import static com.example.giftishare.view.buysellcoupons.BuySellCouponsFragment.INTENT_IS_SALE;

// @TODO MVVM 패턴으로 변경
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CATEGORY_COUPONS = "CATEGORY_COUPONS";

    private DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupNavigationDrawer();
        setupViewFragment();
        mViewModel = obtainViewModel(this);

        mViewModel.getOpenOnSaleCouponsEvent().observe(this,
                (Event<CouponsCategoryType> CouponIdEvent) -> {
            CouponsCategoryType category = CouponIdEvent.getContentIfNotHandled();
            if (category != null) {
                openOnSaleActivity(category);
            }
        });
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public static MainViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        MainViewModel viewModel = ViewModelProviders.of(activity, factory).get(MainViewModel.class);
        return viewModel;
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupViewFragment() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), mainFragment, R.id.content_frame);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_buy_list:
                openBuySellCouponsActivity(BuySellCouponsActivity.class, false);
                break;
            case R.id.nav_sell_list:
                openBuySellCouponsActivity(BuySellCouponsActivity.class, true);
                break;
            case R.id.nav_contact_us:

                break;
            case R.id.nav_opensource_lisence:

                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_sell_coupon:
                openActivity(AddCouponActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openOnSaleActivity(CouponsCategoryType category) {
        Intent intent = new Intent(this, OnSaleCouponsActivity.class);
        intent.putExtra(CATEGORY_COUPONS, category);
        startActivity(intent);
    }

    public <T extends AppCompatActivity> void openActivity(Class<T> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    public  <T extends AppCompatActivity> void openBuySellCouponsActivity(Class<T> className, boolean isSale) {
        Intent intent = new Intent(this, className);
        intent.putExtra(INTENT_IS_SALE, isSale);
        startActivity(intent);
    }
}
