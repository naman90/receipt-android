package ie.rm.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Home extends Base {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		if(savedInstanceState==null){
		receiptFragment = new ReceiptFragment(); //create a new Fragment
	    getFragmentManager().beginTransaction().add(R.id.fragment_layout, receiptFragment).commit();
		}
		
	}
	
	 @Override
		protected void onResume() {
		  super.onResume();	
			
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actiobaritems, menu);
		return super.onCreateOptionsMenu(menu);

	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
	        goToActivity(this, Settings.class,null);
			return true;
			
		case R.id.action_new:
			goToActivity(this, Source.class,null);
		default:
            return super.onOptionsItemSelected(item);

		}
	}

}
