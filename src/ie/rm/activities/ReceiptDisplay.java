package ie.rm.activities;

import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.ApplicationUtils;
import ie.rm.async.DisplayImageTask;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.touch.TouchImageView;

public class ReceiptDisplay extends Base  {
    String mCurrentPhotoPath;
    TouchImageView imageView;
    public ProgressDialog progressDialog;
    Bitmap bitmap=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt_display);
	    imageView=(TouchImageView)findViewById(R.id.receiptImageView);
	    if(getIntent().getExtras()!=null &&getIntent().getExtras().getSerializable("receipt")!=null)
		receipt= (Receipt)getIntent().getExtras().getSerializable("receipt");
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		new DisplayImageTask(this).execute(receipt); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(receipt.getReceiptId()!=null){
			getMenuInflater().inflate(R.menu.edit_menu, menu);
			getActionBar().setDisplayHomeAsUpEnabled(false);
		}else{
		getMenuInflater().inflate(R.menu.accept_menu, menu);
		}
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
		case R.id.menuEdit:
			Bundle receiptEditBundle = new Bundle();
			receiptEditBundle.putSerializable("receipt", receipt);
	        goToActivity(this, AddReceipt.class, receiptEditBundle);
			return true;
		default:
            return super.onOptionsItemSelected(item);
			
		}			
	}

	@Override
	protected void onStop() {
		bitmap=null;
		super.onStop();
		
	}
	 
	@Override
	protected void onPause() {
		if(bitmap!=null &&!bitmap.isRecycled())
		   bitmap.recycle();
		super.onPause();
	}
	
	public void setBitmapToView(Bitmap bitmap){
	    this.bitmap=bitmap;
		imageView.setImageBitmap(bitmap);	
	}
	

	


}
