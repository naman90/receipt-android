package ie.rm.async;

import ie.rm.activities.ReceiptDisplay;
import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.ApplicationUtils;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class DisplayImageTask extends AsyncTask<Receipt, Void, Void>{
    private ReceiptDisplay receiptDisplayActivity;
    Bitmap bitmap;
	
    public DisplayImageTask(ReceiptDisplay activity) {
		this.receiptDisplayActivity=activity;
	}
    
	@Override
	protected Void doInBackground(Receipt... params) {
		Receipt receipt= params[0];
		if(receipt.getReceiptId()!=null && receipt.getReceiptId().length()>0){
			bitmap=ApplicationUtils.loadImageFromFileSystem(receipt.getImage());
		}else{
			bitmap = ApplicationUtils.loadImage(receipt.getImage());
	
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		receiptDisplayActivity.setBitmapToView(bitmap);
		receiptDisplayActivity.progressDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		receiptDisplayActivity.progressDialog = new ProgressDialog(receiptDisplayActivity);
		receiptDisplayActivity.progressDialog.setTitle("Loading Image");
		receiptDisplayActivity.progressDialog.show();
		super.onPreExecute();
	}
	
	

}
