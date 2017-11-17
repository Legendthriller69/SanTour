package com.example.group3.santour;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

import com.example.group3.santour.DTO.Role;
import com.example.group3.santour.DTO.User;
import com.example.group3.santour.Firebase.FirebaseDB;

public class MainActivity extends AppCompatActivity {

    private Role role;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase write into DB
        FirebaseDB writeDatabase = new FirebaseDB();

        //create a new role into the db
        role = new Role("admin");
        writeDatabase.writeNewRole(role);

        

        //create a new
        user = new User("root", "root", "root@root.ch", role);
        writeDatabase.writeNewUser(user);
    }
}
