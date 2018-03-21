package com.bignerdranch.android.beatbox;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ivo Georgiev (IfChyy)
 * Singleto class
 * Create fragment abstract method giving a class the ability to call a fragment it wants to display
 * after that SingleFragmentAcitivty is invoked which creates a new fragment manager
 * gets our frame layout with fragment ( here we place the fragment we called)
 * and the beginstransaction ( eg. places our fragment class into fragmentContainer ( frame layout)
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    private FragmentManager fm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //init fragment manager
        fm = getFragmentManager();
        //get the framelayout id with the manager( here we are going to host our fragment view/activity)
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        //if fragment null create it
        if (fragment == null) {
            //create the new fragment we invoked from other class
            fragment = createFragment();
            //place the invoked fragment into our frameLayout( fragmentContainer)
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }

    }
    //get the layout for the view we arr going to inflate
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
