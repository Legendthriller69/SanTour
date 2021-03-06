package ch.hes.group3.santour.Logic;

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
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by aleks on 28.11.2017.
 */

public class Camera extends Activity {

    //Gallery
    private final int RESULT_LOAD_IMG = 1;
    private final int REQUEST_TAKE_PHOTO = 1;

    private String imgDecodableString;

    public String getImgDecodableString() {
        return imgDecodableString;
    }
    private Bitmap bitmap;
    private String absPathPicture ;
    private Uri selectedImage ;

    //Getter and setter for the Absolute path of the picture
    public String getAbsPathPicture(){
        return absPathPicture ;
    }
    public void setAbsPathPicture(String absPathPicture){
        this.absPathPicture = absPathPicture ;
    }

    //Choice made by the user - Camera or Gallery
    private String choice ;

    private String mCurrentPhotoPath ;

    //Getter and Setter for the choice
    public String getChoice() {
        return choice;
    }
    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Camera() {

    }

    /**
     * Create an image when capturing it on the phone and storing it in the external storage of the phone
     * @param fragment
     * @return
     * @throws IOException
     */
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

    /**
     * Take a picture method from the phone directly
     * @param fragment
     */
    public void takePictureIntent(Fragment fragment) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(fragment);
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

    /**
     * Launch the Gallery from the phone
     * @param fragment
     */
    public void launchImportImage(Fragment fragment){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        if (galleryIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
    }

    /**
     * Retrieve the picture from the phone gallery and display it on the ImageView
     * @param requestCode
     * @param resultCode
     * @param activity
     * @param imgView
     * @param data
     */
    public void addToImageViewGallery(int requestCode, int resultCode, Activity activity, ImageView imgView, Intent data){

        // When an Image is picked
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            // Get the Image from data

            selectedImage = data.getData();
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

    /**
     * Rotating the picture
     * @param source
     * @param angle
     * @return
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /**
     * Implemanting the Rotating the picture method on the phone when displaying on the phone
     * @param bitmap
     * @return
     * @throws IOException
     */
    public Bitmap rotatePicture(Bitmap bitmap) throws IOException {

        //Need to bypass ExifInterface Constructor to be able to rotate the bitmap
        ExifInterface ei = new ExifInterface(getAbsPathPicture());
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
