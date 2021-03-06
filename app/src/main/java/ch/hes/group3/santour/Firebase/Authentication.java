package ch.hes.group3.santour.Firebase;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ch.hes.group3.santour.Activity.LoginActivity;
import ch.hes.group3.santour.DTO.Role;
import ch.hes.group3.santour.DTO.User;

/**
 * Created by aleks on 07.12.2017.
 */

public class Authentication {

    private static Role role ;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static User currentUser;

    private Authentication() {

    }

    /**
     * method to sign in the application with the mail and the password
     *
     * @param mail
     * @param password
     * @param dataListener
     */
    public static void signIn(final String mail, String password, final DataListener dataListener) {
        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserDB.getUserById(firebaseAuth.getCurrentUser().getUid(), new DataListener() {
                        @Override
                        public void onSuccess(Object object) {
                            currentUser = (User) object;
                            dataListener.onSuccess(true);
                            RoleDB.getRoleById(Authentication.getCurrentUser().getIdRole(), new DataListener() {
                                @Override
                                public void onSuccess(Object object) {
                                    Role role = (Role) object ;
                                    Authentication.role = role ;
                                }

                                @Override
                                public void onFailed(Object object) {

                                }
                            });
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

    /**
     * method to logout from the application
     * closes the current activity passed in parameter
     * @param activity
     */
    public static void logout(Activity activity){
        firebaseAuth.signOut();
        currentUser = null;
        activity.finish();
        role = null ;
    }

    /**
     * get the current user
     * @return
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * get the current role
     * @return
     */
    public static Role getCurrentRole() {
        return role ;
    }
}
