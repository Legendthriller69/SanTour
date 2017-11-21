package com.example.group3.santour.Firebase;

import android.support.annotation.NonNull;

import com.example.group3.santour.DTO.Category;
import com.example.group3.santour.DTO.Role;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aleks on 21.11.2017.
 */

public class CategoryDB {

    private final static DatabaseReference CATEGORY_REFERENCE = FirebaseDatabase.getInstance().getReference("categories");

    private CategoryDB() {

    }

    public static void createCategory(Category category, final DataListener dataListener) {
        final DatabaseReference id = CATEGORY_REFERENCE.push();
        id.setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getCategoryById(id.getKey(), dataListener);
            }
        });
    }

    public static void getCategoryById(final String id, final DataListener dataListener) {
        Query query = CATEGORY_REFERENCE.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Category category = dataSnapshot.getValue(Category.class);
                category.setId(id);
                dataListener.onSuccess(category);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }

}
