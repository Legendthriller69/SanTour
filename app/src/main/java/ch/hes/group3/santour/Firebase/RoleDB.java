package ch.hes.group3.santour.Firebase;

import android.support.annotation.NonNull;

import ch.hes.group3.santour.DTO.Role;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aleks on 20.11.2017.
 */

public class RoleDB {

    private final static DatabaseReference ROLE_REFERENCE = FirebaseDatabase.getInstance().getReference("roles");

    private RoleDB() {

    }

    /**
     * creates a role into the db
     * @param role
     * @param dataListener
     */
    public static void createRole(final Role role, final DataListener dataListener) {
        final DatabaseReference id = ROLE_REFERENCE.push();
        id.setValue(role).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getRoleById(id.getKey(), dataListener);
            }
        });
    }

    /**
     * get role by id
     * @param id
     * @param dataListener
     */
    public static void getRoleById(final String id, final DataListener dataListener) {
        Query query = ROLE_REFERENCE.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Role role = dataSnapshot.getValue(Role.class);
                role.setId(id);
                dataListener.onSuccess(role);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }

}
