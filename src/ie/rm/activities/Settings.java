package ie.rm.activities;

import ie.rm.activities.util.PersistenceManager;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class Settings extends Activity {
	static final int REQUEST_LINK_TO_DBX = 0;
	private Button dropboxButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		dropboxButton=(Button)findViewById(R.id.linkDropboxButton);
		if(PersistenceManager.getInstance().isAccountSetup()){
			dropboxButton.setText(R.string.dropboxButtonUnlinkText);
		}else{
			dropboxButton.setText(R.string.dropboxButtonText);
			getActionBar().setDisplayHomeAsUpEnabled(false);
		}
		PersistenceManager.getInstance().initializePersistence(getApplicationContext());
	
	}
	
	public void dropboxButtonAction(View view){
		if(dropboxButton.getText().equals( getResources().getString(R.string.dropboxButtonUnlinkText))){
			PersistenceManager.getInstance().unlinkDropbox();
			PersistenceManager.getInstance().clearReceiptArray();
			ReceiptFragment.listAdapter.notifyDataSetChanged();
			getActionBar().setDisplayHomeAsUpEnabled(false);
			dropboxButton.setText(R.string.dropboxButtonText);
			return;
		}
		boolean isSetup=PersistenceManager.getInstance().linkDropBox(this,REQUEST_LINK_TO_DBX);
		if(isSetup){
			dropboxButton.setText(R.string.dropboxButtonUnlinkText);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_LINK_TO_DBX) {
	        if (resultCode == Activity.RESULT_OK) {
	            if(PersistenceManager.getInstance().setupPersistenceIfLinked()){
	            	//Update the Button text to unlink
	            	dropboxButton.setText(R.string.dropboxButtonUnlinkText);
	            	getActionBar().setDisplayHomeAsUpEnabled(true);
	            }
	        } else {
	            // ... Link failed or was cancelled by the user.Do nothing
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public void onBackPressed() {
	//Do nothing	
	}


}
