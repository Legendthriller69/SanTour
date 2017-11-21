package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseError;

/**
 * Created by aleks on 21.11.2017.
 */

public class ResetDB {

    public static void resetDB(){
        RoleDB.createRole("admin", new DataListener() {
            @Override
            public void onSuccess(Object object) {
                String idRole = (String) object;
                UserDB.createUser("root", "root", "root@root.ch", idRole);
            }

            @Override
            public void onFailed(DatabaseError dbError) {

            }
        });
    }
}
