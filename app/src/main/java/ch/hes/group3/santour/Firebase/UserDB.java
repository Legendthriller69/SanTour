package ch.hes.group3.santour.Firebase;

import android.support.annotation.NonNull;

import ch.hes.group3.santour.DTO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    private final static DatabaseReference USER_REFERENCE = FirebaseDatabase.getInstance().getReference("users");

    private UserDB() {

    }

    /**
     * creates a user into the firebase
     * @param user
     * @param dataListener
     */
    public static void createUser(User user, final DataListener dataListener) {
        final DatabaseReference id = USER_REFERENCE.push();
        id.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getUserById(id.getKey(), dataListener);
            }
        });
    }

    /**
     * get all users from firebase
     * dataListener is used as callback because of async
     *
     * @param dataListener
     */
    public static void getAllUsers(final DataListener dataListener) {
        USER_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    user.setId(userSnapshot.getKey());
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
     *
     * @param id
     * @param dataListener
     */
    public static void getUserById(String id, final DataListener dataListener) {
        Query query = USER_REFERENCE.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setId(dataSnapshot.getKey());
                dataListener.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }
}