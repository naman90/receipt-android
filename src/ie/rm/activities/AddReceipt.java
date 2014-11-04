package ie.rm.activities;

import ie.rm.activities.util.ApplicationUtils;
import ie.rm.activities.util.DeleteReceiptWithAlert;
import ie.rm.activities.util.PersistenceManager;
import java.text.DecimalFormat;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddReceipt extends Base implements OnDismissListener{
	TextView storeName;
	TextView description;
	EditText price;
	DatePicker datePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_receipt);
		storeName=(TextView)findViewById(R.id.storeName);
		description=(TextView)findViewById(R.id.description);
		price=(EditText)findViewById(R.id.price);
		datePicker=(DatePicker)findViewById(R.id.datePicker);
		if(receipt.getReceiptId()!=null && receipt.getReceiptId().length()>0){
			storeName.setText(receipt.getStore());
			description.setText(receipt.getDescription());
			price.setText(new DecimalFormat("0.00").format(receipt.getPrice()));
			datePicker.updateDate(ApplicationUtils.getYearOfDate(receipt.getDate()),
					ApplicationUtils.getMonthOfDate(receipt.getDate()),
					ApplicationUtils.getDayOfDate(receipt.getDate()));
		}
    
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		if(receipt.getReceiptId()!=null && receipt.getReceiptId().length()>0){
			getMenuInflater().inflate(R.menu.accept_reject_menu, menu);
			getActionBar().setDisplayHomeAsUpEnabled(false);
			getActionBar().setTitle("Receipt Details");
		}
		else{
			getMenuInflater().inflate(R.menu.accept_menu, menu);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menuAccept) {
			saveOrUpdateReceipt();
			return true;
		}
		if (id == R.id.menuDelete) {
			AlertDialog deleteDialog =DeleteReceiptWithAlert.deleteReceipt(AddReceipt.this, receipt);
			deleteDialog.setOnDismissListener(this);
			deleteDialog.show();
			//goToActivity(this, Home.class, null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	void saveOrUpdateReceipt(){
		Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		if(storeName.getText()==null || storeName.getText().length()==0){
			toast.setText("Enter Store Name");
			toast.show();
			return;
		}
		if(description.getText()==null || description.getText().length()==0){
			toast.setText("Enter Description");
			toast.show();
			return;
		}
		if(price.getText()==null || price.getText().length()==0){
			toast.setText("Enter Price");
			toast.show();
			return;
		}
		receipt.setStore(storeName.getText().toString());
		receipt.setDescription(description.getText().toString());
		receipt.setPrice(Double.parseDouble(price.getText().toString()));
		receipt.setDate(ApplicationUtils.getDate(datePicker));
		if(receipt.getReceiptId()!=null){
			PersistenceManager.getInstance().updateReceipt(receipt);
		}else{
		PersistenceManager.getInstance().addReceipt(receipt);
		}
		ReceiptFragment.listAdapter.notifyDataSetChanged();
        goToActivity(this, Home.class, null);
	}


	@Override
	public void onDismiss(DialogInterface arg0) {
		if(receipt.getReceiptId()==null)
			goToActivity(this, Home.class, null);
	}
	
	@Override
	public void onBackPressed() {
		if(receipt!=null && receipt.getReceiptId()!=null && receipt.getReceiptId().length()>0){
			goToActivity(this, Home.class, null);
		}else{
			super.onBackPressed();
		}
	}


}
