package ie.rm.receiptadapters;

import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.PersistenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.location.Location;
import android.widget.Filter;

public class ReceiptFilter extends Filter {
	private String 				filterText;
	private ReceiptListAdapter 	adapter;

	public ReceiptFilter(String filterText,
			ReceiptListAdapter adapter) {
		super();
		this.filterText = filterText;
		this.adapter = adapter;
	}

	public void setFilter(String filterText) {
		this.filterText = filterText;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();

		if (prefix == null || prefix.length() == 0) {
			results.values = PersistenceManager.getInstance().receiptList();
			results.count = PersistenceManager.getInstance().receiptList().size();
			/*if (filterText.equals("all")) {
				results.values = PersistenceManager.getInstance().receiptList();
				results.count = PersistenceManager.getInstance().receiptList().size();
			} else {
				if (filterText.equals("favourites")) {
					for (Coffee c : originalCoffeeList)
						if (c.getFavourite() == 1)
							newCoffees.add(c);
				}
				results.values = newCoffees;
				results.count = newCoffees.size();
				results.values = PersistenceManager.getInstance().receiptList();
				results.count = PersistenceManager.getInstance().receiptList().size();
			}*/
		} else {
			String prefixString = prefix.toString().toLowerCase();
			final ArrayList<Receipt> filteredReceipt = new ArrayList<Receipt>();
			if(filterText.equals("description")){
				for(Receipt receipt: PersistenceManager.getInstance().receiptList()){
					if(receipt.getDescription().toLowerCase().startsWith(prefixString.toLowerCase())){
						filteredReceipt.add(receipt);
					}
				}
			}else if(filterText.equals("store")){
				for(Receipt receipt: PersistenceManager.getInstance().receiptList()){
					if(receipt.getStore().toLowerCase().startsWith(prefixString.toLowerCase())){
						filteredReceipt.add(receipt);
					}
				}
			}
			results.values = filteredReceipt;
			results.count = filteredReceipt.size();
			/*String prefixString = prefix.toString().toLowerCase();
			final ArrayList<Receipt> newCoffees = new ArrayList<Receipt>();

			for (Coffee c : originalCoffeeList) {
				final String itemName = c.getCoffeeName().toString()
						.toLowerCase();
				if (itemName.contains(prefixString)) {
					if (filterText.equals("all")) {
						newCoffees.add(c);
					} else if (c.getFavourite() == 1) {
						newCoffees.add(c);
					}}}*/
			
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence prefix, FilterResults results) {

		adapter.receiptList = (ArrayList<Receipt>) results.values;
		if (results.count >= 0)
			adapter.notifyDataSetChanged();
		else {
			adapter.notifyDataSetInvalidated();
			adapter.receiptList = PersistenceManager.getInstance().receiptList();
		}
	}
}
