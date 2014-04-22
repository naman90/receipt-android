package ie.rm.activities;

import ie.rm.activities.model.Receipt;

import java.io.File;
import java.io.IOException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

public class Source extends Base {
	static final int REQUEST_TAKE_PHOTO = 1;
    protected    String  mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_source);

	}
	
	public void intentToStartCamera(View view){
		if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    // Ensure that there's a camera activity to handle the intent
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			        // Create the File where the photo should go
			        File photoFile = null;
			        try {
			            photoFile = createImageFile();
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
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    File storageDir = Environment.getExternalStorageDirectory();
	    File image = File.createTempFile(
	        "temp",  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    mCurrentPhotoPath =  image.getAbsolutePath();
	    return image;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent intent = new Intent(this, ReceiptDisplay.class);
		Bundle imageLocation = new Bundle();
		 receipt = new Receipt();
		 receipt.setImage(mCurrentPhotoPath);
		imageLocation.putSerializable("receipt", receipt);
		intent.putExtras(imageLocation);
        startActivity(intent);
	}
	
	

}
