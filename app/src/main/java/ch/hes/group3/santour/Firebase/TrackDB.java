package ch.hes.group3.santour.Firebase;

import android.support.annotation.NonNull;

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

import ch.hes.group3.santour.DTO.Track;

/**
 * Created by kevin on 21/11/17.
 */

public class TrackDB {

    private final static DatabaseReference TRACK_REFERENCE = FirebaseDatabase.getInstance().getReference("tracks");

    private TrackDB() {

    }

    /**
     * creates a track into the firebase database
     * @param track
     * @param dataListener
     */
    public static void createTrack(Track track, final DataListener dataListener) {
        final DatabaseReference id = TRACK_REFERENCE.push();
        id.setValue(track).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getTrackById(id.getKey(), dataListener);
            }
        });
    }

    /**
     * create a track into the db
     * @param track
     */
    public static void createTrack(Track track) {
        final DatabaseReference id = TRACK_REFERENCE.push();
        id.setValue(track);
    }

    /**
     * get a track by id
     * @param id
     * @param dataListener
     */
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

    /**
     * get all tracks for the user
     * @param idUser
     * @param dataListener
     */
    public static void getAllTracksByIdUser(final String idUser, final DataListener dataListener){
        TRACK_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Track> tracks = new ArrayList<>();
                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()) {
                    Track track = trackSnapshot.getValue(Track.class);
                    if(track.getIdUser().equals(idUser)){
                        tracks.add(track);
                    }
                }
                dataListener.onSuccess(tracks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * get all tracks from the firebase
     * @param dataListener
     */
    public static void getAllTracks(final DataListener dataListener){
        TRACK_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Track> tracks = new ArrayList<>();
                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()) {
                    Track track = trackSnapshot.getValue(Track.class);
                    tracks.add(track) ;
                }
                dataListener.onSuccess(tracks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
