package com.example.group3.santour.Logic;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by aleks on 28.11.2017.
 */

public class Camera extends Activity {

    //Camera
    //If Request_image_capture > 0 return -> OnActivityResult
    private final int REQUEST_IMAGE_CAPTURE = 1;

    //Gallery
    private final int RESULT_LOAD_IMG = 1;
    private final int REQUEST_TAKE_PHOTO = 1;

    private String imgDecodableString;
    private String mCurrentPhotoPath;
    private Bitmap bitmap;

    private String absPathPicture ;


    public String getAbsPathPicture(){
        return absPathPicture ;
    }

    public void setAbsPathPicture(String absPathPicture){
        this.absPathPicture = absPathPicture ;
    }

    //Choice made by the user - Camera or Gallery
    private String choice ;

    //Getter and Setter for the choice
    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Camera() {

    }

    //Launching Camera
    public void launchCamera(Fragment fragment) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private File createImageFile(Fragment fragment) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = fragment.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        setAbsPathPicture(image.getAbsolutePath());
        return image;
    }


    public void takePictureIntent(Fragment fragment) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(fragment);
                Log.e("TEST-TEST", "dispatchTakePictureIntent: ROUTE : " + getAbsPathPicture());
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(fragment.getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Launching Import Gallery
    public void launchImportImage(Fragment fragment){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        if (galleryIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
        //fragment.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    public void addToImageViewCamera(int requestCode, int resultCode, Bitmap bitmap, Activity activity, ImageView imgView) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == activity.RESULT_OK) {
            imgView.setImageBitmap(bitmap);
        }
    }

    public void addToImageViewGallery(int requestCode, int resultCode, Activity activity, ImageView imgView, Intent data){

        // When an Image is picked
        if (requestCode == RESULT_LOAD_IMG && resultCode == activity.RESULT_OK) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = activity.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            // Set the Image in ImageView after decoding the String
            bitmap = BitmapFactory.decodeFile(imgDecodableString);
            imgView.setImageBitmap(bitmap);
        }

    }

    public String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public String encodeImageWithGallery(){
        return encodeBitmap(bitmap);
    }

    /**
     * Decode the b64 String directly when encoded to test - NOT FROM DB
     * @param encodedImage
     * @param imgView
     */
    public void decodeB64Bitmap(String encodedImage, ImageView imgView) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgView.setImageBitmap(decodedByte);
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public Bitmap testRotate(Bitmap bitmap, Uri uri) throws IOException {

        ExifInterface ei = new ExifInterface(uri.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        return rotatedBitmap ;
    }

}
