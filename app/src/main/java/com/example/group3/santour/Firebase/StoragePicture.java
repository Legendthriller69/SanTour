package com.example.group3.santour.Firebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private StorageReference storageRef = storage.getReference();

    public void uploadPicture(String pathPicture, String filename) {
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

    public void downloadPicture(String pathPicture, final DataListener dataListener) {
        storageRef.child(pathPicture).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                dataListener.onSuccess(bytes);
            }
        });
    }

    public void deletePicture(String pathPicture) {
        storageRef.child(pathPicture).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("DELETE", "FILE DELETED");
            }
        });
    }

}
