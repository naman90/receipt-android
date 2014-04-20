package ie.rm.activities;

import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.ExifUtil;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.touch.TouchImageView;

public class ReceiptDisplay extends Base {
    String mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt_display);
		TouchImageView imageView=(TouchImageView)findViewById(R.id.receiptImageView);
	    if(getIntent().getExtras()!=null &&getIntent().getExtras().getSerializable("receipt")!=null)
		   receipt= (Receipt)getIntent().getExtras().getSerializable("receipt");
		mCurrentPhotoPath=receipt.getImage();
		if(mCurrentPhotoPath!=null && mCurrentPhotoPath.length()>0){
			setPic(imageView);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept_menu, menu);
		return true;
	}
	
	
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuAccept:
			Bundle receiptBundle = new Bundle();
			receiptBundle.putSerializable("receipt", receipt);
	        goToActivity(this, AddReceipt.class, receiptBundle);
			return true;
		default:
            return super.onOptionsItemSelected(item);

		}
	}


	Bitmap readImage(String imagePath){
		File imgFile = new  File(imagePath);
		if(imgFile.exists()){
		    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    return bitmap;
		}
		return null;
	}

	 private void setPic(ImageView imageView) {
		 File file = new File(mCurrentPhotoPath);
		    // Get the dimensions of the bitmap
		    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		    bmOptions.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		    int photoW = bmOptions.outWidth;
		    int photoH = bmOptions.outHeight;
		    // Determine how much to scale down the image
		    // Decode the image file into a Bitmap sized to fill the View
		    bmOptions.inJustDecodeBounds = false;
		    bmOptions.inPurgeable = true;
		    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		    bitmap=ExifUtil.rotateBitmap(file.getAbsolutePath(), bitmap);
		    imageView.setImageBitmap(bitmap);
		}


}
