package ie.rm.activities;

import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.ApplicationUtils;
import java.io.File;
import java.io.IOException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class Source extends Base {
	static final int REQUEST_TAKE_PHOTO = 1;
	static final int REQUEST_GALLERY_PHOTO = 2;
    protected    String  mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_source);

	}
	
	public void imageFromGallery(View view){
		Intent captureImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(captureImageIntent,REQUEST_GALLERY_PHOTO);
	}
	
	public void intentToStartCamera(View view){
		if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    // Ensure that there's a camera activity to handle the intent
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			        // Create the File where the photo should go
			        File photoFile = null;
			        try {
			            photoFile = ApplicationUtils.createImageFile();
			            mCurrentPhotoPath =  photoFile.getAbsolutePath();
			        } catch (IOException ex) {
			            // Error occurred while creating the File
			           System.out.print(ex);
			           toastMessage("Error in accessing file system");
			        }
			        // Continue only if the File was successfully created
			        if (photoFile != null) {
			            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
			                    Uri.fromFile(photoFile));
			            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			        }
			    }
		}else{
			toastMessage("No Camera Present");
		}
		
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUEST_GALLERY_PHOTO && resultCode== RESULT_OK && data!=null){
			 Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         mCurrentPhotoPath = cursor.getString(columnIndex);
	         cursor.close();
		}
		Intent intent = new Intent(this, ReceiptDisplay.class);
		Bundle imageLocation = new Bundle();
		 receipt = new Receipt();
		 receipt.setImage(mCurrentPhotoPath);
		imageLocation.putSerializable("receipt", receipt);
		intent.putExtras(imageLocation);
        startActivity(intent);
	}
	
	
	
	

}
