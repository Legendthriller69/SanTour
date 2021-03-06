package ch.hes.group3.santour.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import ch.hes.group3.santour.Firebase.Authentication;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Logic.Permissions;

public class LoginActivity extends AppCompatActivity {

    //elements
    private EditText txtMail;
    private EditText txtPassword;
    private Button btnSignIn;

    // progress bar
    private ProgressDialog progressing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLastLanguage();
        setContentView(R.layout.activity_login);


        //ask for permissions
        Permissions permissions = new Permissions();
        permissions.checkPermissions(this);

        setTitle(getString(R.string.Login));

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        //initialize elements
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //button listener
        btnSignIn.setOnClickListener(new SignIn());
    }

    /**
     * check if all the input text are well filled in
     * show toast if not
     *
     * @return
     */
    private boolean formValidation() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtMail.getText()).matches()) {
            Toast.makeText(this, R.string.enterValidMail, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(txtPassword.getText())) {
            Toast.makeText(this, R.string.enterPassword, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * listener to sign in will compare the form's input with the authentication from firebase
     */
    private class SignIn implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            TextView myTitle = new TextView(getApplicationContext());
            myTitle.setText(R.string.loading);
            myTitle.setTextSize(20);
            myTitle.setTextColor(getResources().getColor(R.color.red_main));
            myTitle.setPadding(80, 30, 10, 10);
            if (formValidation()) {
                progressing = new ProgressDialog(LoginActivity.this);
                progressing.setMessage(getString(R.string.waiting)); // Setting Message
                progressing.setCustomTitle(myTitle);
                progressing.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressing.show(); // Display Progress Dialog
                progressing.setCancelable(false);
                progressing.setIndeterminate(true);

                Drawable drawable = new ProgressBar(getApplicationContext()).getIndeterminateDrawable().mutate();
                drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red_main),
                        PorterDuff.Mode.SRC_IN);
                progressing.setIndeterminateDrawable(drawable);

                Authentication.signIn(txtMail.getText().toString(), txtPassword.getText().toString(), new DataListener() {
                    @Override
                    public void onSuccess(Object object) {
                        progressing.dismiss(); // dismiss progress bar
                        finish();
                        Intent intent = new Intent(LoginActivity.this, WelcomePage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(Object object) {
                        progressing.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.userNotFound, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    /**
     * load the last language from the database by using the shared preferences
     */
    private void loadLastLanguage() {
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("LANGUAGE", "en-rGB");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}


