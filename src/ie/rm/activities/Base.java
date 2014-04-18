package ie.rm.activities;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ie.rm.activities.R;
import ie.rm.activities.model.Receipt;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Base extends Activity {

	protected 		Bundle 				activityInfo;
	public 			Fragment 			coffeeFragment;
	
; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setOverFlowMenu(true, false); // true,false turns in on
	}
	
	protected void goToActivity(Activity current,Class<? extends Activity> activity,
			 Bundle bundle) {
		Intent newActivity = new Intent(current, activity);

		if (bundle != null)
			newActivity.putExtras(bundle);

		current.startActivity(newActivity);
	}

	public EditText getEditText(int id) {
		return ((EditText) findViewById(id));
	}

	protected String getEditString(int id) {
		return (getEditText(id)).getText().toString();
	}

	protected void setEditString(int id, String str) {
		(getEditText(id)).setText(str);
	}

	protected void setTextViewString(int id, String str) {
		((TextView) findViewById(id)).setText(str);
	}

	protected void setEditDouble(int id, Double d) {
		((EditText) findViewById(id)).setText(d.toString());
	}

	protected double getRatingBarValue(int id) {
		RatingBar bar = (RatingBar) findViewById(id);
		return bar.getRating();
	}

	protected void setRatingBarValue(int id, float d) {
		RatingBar bar = (RatingBar) findViewById(id);
		bar.setRating(d);
	}

	protected double getEditDouble(int id) {
		return Double.parseDouble(getEditString(id));
	}

	protected Button getButton(int id) {
		return (Button) findViewById(id);
	}

	protected CheckBox getCheckBox(int id) {
		return (CheckBox) findViewById(id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public Fragment getCoffeeFragment() {
		return coffeeFragment;
	}
	
	public void setSpinnerListener(int id, int data,
			OnItemSelectedListener listener) {
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, data,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner) findViewById(id)).setAdapter(spinnerAdapter);
		((Spinner) findViewById(id)).setOnItemSelectedListener(listener);
	}

	protected void setEditTextListener(int id, TextWatcher listener) {
		((EditText) findViewById(id)).addTextChangedListener(listener);
	}

	protected void toastMessage(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
	
	public void setOverFlowMenu(boolean accessibleValue, boolean configValue) {
		// Hack for Overflow Menu
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(accessibleValue);
				menuKeyField.setBoolean(config, configValue);
			}
		} catch (Exception ex) {
			// Ignore
		}
	}
}
