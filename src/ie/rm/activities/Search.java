package ie.rm.activities;


import android.os.Bundle;

public class Search extends Base {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

	}

	@Override
	protected void onResume() {
		super.onResume();
		receiptFragment = new SearchFragment();
		getFragmentManager().beginTransaction()
		.add(R.id.fragment_layout, receiptFragment).commit();

	}
}
