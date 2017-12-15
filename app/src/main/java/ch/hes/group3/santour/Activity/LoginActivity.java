package ch.hes.group3.santour.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ch.hes.group3.santour.Activity.R;

import ch.hes.group3.santour.Firebase.Authentication;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.StoragePicture;
import ch.hes.group3.santour.Logic.Permissions;

public class LoginActivity extends AppCompatActivity {

    //elements
    private EditText txtMail;
    private EditText txtPassword;
    private Button btnSignIn;
    private ImageView imgView;

    // progress bar
    private ProgressDialog progressing;

    private StoragePicture storagePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ask for permissions
        Permissions permissions = new Permissions();
        permissions.checkPermissions(this);

        setTitle(getString(R.string.Login));
        //initialize elements
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //button listener
        btnSignIn.setOnClickListener(new SignIn());
    }

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

    private class SignIn implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Log.e("FORM", "FORM VALIDATION : " + formValidation());
            if (formValidation()) {
                progressing = new ProgressDialog(LoginActivity.this);
                progressing.setMessage(getString(R.string.waiting)); // Setting Message
                progressing.setTitle(getString(R.string.loading)); // Setting Title
                progressing.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressing.show(); // Display Progress Dialog
                progressing.setCancelable(false);
                Authentication.signIn(txtMail.getText().toString(), txtPassword.getText().toString(), new DataListener() {
                    @Override
                    public void onSuccess(Object object) {
                        progressing.dismiss(); // dismiss progress bar
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
}


