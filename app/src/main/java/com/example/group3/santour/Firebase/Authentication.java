package com.example.group3.santour.Firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.example.group3.santour.DTO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by aleks on 07.12.2017.
 */

public class Authentication {

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static User currentUser;

    private Authentication() {

    }

    public static void signIn(final String mail, String password, final DataListener dataListener) {
        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserDB.getUserByMail(mail, new DataListener() {
                        @Override
                        public void onSuccess(Object object) {
                            currentUser = (User) object;
                            dataListener.onSuccess(true);
                        }
                        @Override
                        public void onFailed(Object object) {
                        }
                    });
                } else {
                    dataListener.onFailed(false);
                }
            }
        });
    }

    public static void logout(Activity activity){
        firebaseAuth.signOut();
        currentUser = null;
        activity.finish();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
