package com.example.group3.santour.Firebase;

import com.example.group3.santour.DTO.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kevin on 21/11/17.
 */

public class TypeDB {

    private final static DatabaseReference TYPE_REFERENCE = FirebaseDatabase.getInstance().getReference("types");

    private TypeDB() {

    }

    /**
     * Create a new Type of track
     *
     * @param name
     */
    public static void createType(String name) {
        DatabaseReference id = TYPE_REFERENCE.push();
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
    }

    /**
     * Get Type of track by his ID
     * @param id
     * @param dataListener
     */
    public static void getTypeById(String id, final DataListener dataListener) {
        Query query = TYPE_REFERENCE.child(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Type type = dataSnapshot.getValue(Type.class);
                dataListener.onSuccess(type);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }
}