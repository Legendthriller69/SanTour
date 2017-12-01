package com.example.group3.santour.Firebase;

import android.support.annotation.NonNull;

import com.example.group3.santour.DTO.Role;
import com.example.group3.santour.DTO.Track;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kevin on 21/11/17.
 */

public class TrackDB {

    private final static DatabaseReference TRACK_REFERENCE = FirebaseDatabase.getInstance().getReference("tracks");

    private TrackDB() {

    }

    public static void createTrack(Track track, final DataListener dataListener) {
        final DatabaseReference id = TRACK_REFERENCE.push();
        id.setValue(track).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getTrackById(id.getKey(), dataListener);
            }
        });
    }

    public static void createTrack(Track track) {
        final DatabaseReference id = TRACK_REFERENCE.push();
        id.setValue(track);
    }

    public static void getTrackById(final String id, final DataListener dataListener) {
        Query query = TRACK_REFERENCE.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Track track = dataSnapshot.getValue(Track.class);
                track.setId(id);
                dataListener.onSuccess(track);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataListener.onFailed(databaseError);
            }
        });
    }


}
