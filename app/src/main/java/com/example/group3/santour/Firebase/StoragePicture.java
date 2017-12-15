package com.example.group3.santour.Firebase;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by kevin on 14/12/17.
 */

public class StoragePicture {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    private  StorageReference storageRef = storage.getReference();

    //"Folder" name
    private  String imageFolder = "images" ;

    // Create a child reference
    // imagesRef now points to "images"
    private  StorageReference imagesRef = storageRef.child(imageFolder);

    private  String filename ;
    //Getter and Setter for the filename
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    //private static StorageReference picImagesRef = storageRef.child(imageFolder+"/"+filename);


    public void uploadPicture(String pathPicture, String filename){

        StorageReference picRef = storageRef.child(filename);

        InputStream stream = null;

        try {
            stream = new FileInputStream(new File(pathPicture));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        UploadTask uploadTask = picRef.putStream(stream);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e("TEST-UPLOAD-FILE", "upload failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.e("TEST-UPLOAD-FILE", "upload successfull");
            }
        });
    }







}
