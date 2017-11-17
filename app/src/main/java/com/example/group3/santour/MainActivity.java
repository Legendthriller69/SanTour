package com.example.group3.santour;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

import com.example.group3.santour.DTO.Role;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.DTO.Type;
import com.example.group3.santour.DTO.User;
import com.example.group3.santour.Firebase.FirebaseDB;

public class MainActivity extends AppCompatActivity {

    private Role role;
    private User user;
    private Type type;
    private Track track;


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

        //create new type
        type = new Type("verticality");
        writeDatabase.writeNewType(type);

        //create new track
        track = new Track ("track1", "Test of track1", 4, 5, type);
        writeDatabase.writeNewTrack(track);
    }
}
