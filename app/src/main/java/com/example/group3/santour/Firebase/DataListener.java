package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseError;

/**
 * Created by aleks on 20.11.2017.
 */

public interface DataListener {

    void onSuccess(Object object);
    void onFailed(Object object);
}
