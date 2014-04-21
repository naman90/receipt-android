package ie.rm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Home extends Base {
	private ReceiptFragment receiptFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}
	
	 @Override
		protected void onResume() {
		  super.onResume();		
		  receiptFragment = new ReceiptFragment(); //create a new Fragment
	      getFragmentManager().beginTransaction().add(R.id.fragment_layout, receiptFragment).commit();
			
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actiobaritems, menu);
		return super.onCreateOptionsMenu(menu);

	}
	public void addNewReceipt(View view){
		Intent intent = new Intent(this, Source.class);
		startActivity(intent);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
	        goToActivity(this, Settings.class,null);
			return true;
		default:
            return super.onOptionsItemSelected(item);

		}
	}

}
