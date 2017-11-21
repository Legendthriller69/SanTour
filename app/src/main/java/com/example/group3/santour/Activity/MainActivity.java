package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.group3.santour.Firebase.ResetDB;
import com.example.group3.santour.R;
import com.example.group3.santour.Utils.Permissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResetDB.resetDB();

    }
}
