package com.esime.gloves.UI.ActivityAssistan;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityPresentation.ActivityPresentation;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class ActivityAssistan extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_CLOSE = 0;
    private static final int POS_ASSISTANT1 = 1;
    private static final int POS_ASSISTANT2 = 2;
    private static final int POS_SETTINGS = 3;
    private static final int POS_LOGOUT = 5;

    private  String[] screenTitlte;
    private Drawable[] screenIcosn;

    private SlidingRootNav slidingRootNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();
        
        screenIcosn = loadScreenIcons(); 
        screenTitlte = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_ASSISTANT1).setChecked(true),
                createItemFor(POS_ASSISTANT2),
                createItemFor(POS_SETTINGS),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_ASSISTANT1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcosn[position], screenTitlte[position])
                .withIconTint(color(R.color.PrimaryBlue))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.PrimaryBlueText))
                .withSelectedTextTint(color(R.color.PrimaryBlueText));
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this, res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activitySreenIcon);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i = 0; i < ta.length(); i++){
            int id = ta.getResourceId(i, 0);
            if(id!=0){
                icons[i] = ContextCompat.getDrawable(this,id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if(position == POS_ASSISTANT1){
                Assistan_01 assistan_01 = new Assistan_01();
                transaction.replace(R.id.container,assistan_01);
            }

            else if(position == POS_ASSISTANT2){
                Assistan_02 assistan_02 = new Assistan_02();
                transaction.replace(R.id.container,assistan_02);
            }

            else if(position == POS_SETTINGS){
                Assistan_03 assistan_03 = new Assistan_03();
                transaction.replace(R.id.container,assistan_03);
            }

            else if (position == POS_LOGOUT){
                AuthProvider authProvider = AuthProvider.getInstance();
                authProvider.logOut();
                SharedPreferenceManager.ClearPreference();
                Intent intent = new Intent(InstanceApp.getContext(), ActivityPresentation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            slidingRootNav.closeMenu();
            transaction.addToBackStack(null);
            transaction.commit();
    }
}