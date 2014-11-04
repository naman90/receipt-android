package ie.rm.activities;


import ie.rm.receiptadapters.ReceiptFilter;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchFragment extends ReceiptFragment 
								implements OnItemSelectedListener, TextWatcher {
	
	protected ReceiptFilter receiptFilter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		receiptFilter = new ReceiptFilter("description", listAdapter);
		ArrayAdapter<CharSequence> spinnerAdapter = 
				ArrayAdapter.createFromResource(activity, 
												R.array.receiptSearchTypes, 
												android.R.layout.simple_spinner_item);

		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner spinner = ((Spinner) activity.findViewById(R.id.searchReceiptTypeSpinner));
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(this);

		activity.getEditText(R.id.searchReceiptNameEditText).addTextChangedListener(this);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
	
		String selected = adapterView.getItemAtPosition(position).toString();

		if (selected != null) {
			if (selected.equals("Description")) {
				receiptFilter.setFilter("description");
			} else if (selected.equals("Store")) {
				receiptFilter.setFilter("store");
			}
		}
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		receiptFilter.filter(s);
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}


}