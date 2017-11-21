package com.example.group3.santour.Firebase;

import com.example.group3.santour.DTO.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleks on 20.11.2017.
 */

public class UserDB {

    private final static DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users");

    private UserDB(){

    }

    /**
     * Create new user into the firebase db
     * @param username
     * @param password
     * @param mail
     * @param idRole
     */
    public static void createUser(String username, String password, String mail, String idRole){
        DatabaseReference id = userReference.push();
        id.child("id").setValue(id.getKey());
        id.child("username").setValue(username);
        id.child("password").setValue(password);
        id.child("mail").setValue(mail);
        id.child("idRole").setValue(idRole);
    }

    /**
     * get all users from firebase
     * dataListener is used as callback because of async
     * @param dataListener
     */
    public static void getAllUsers(final DataListener dataListener){
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    users.add(user);
                }
                dataListener.onSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }

    /**
     * get user by id
     * @param id
     * @param dataListener
     */
    public static void getUserById(String id, final DataListener dataListener){
        Query query = userReference.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                dataListener.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }
}