package com.tapifolti.entrytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anya on 3/31/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, PhotoFragment.newInstance())
                    .commit();
        }
    }

}
