package ie.rm.receiptadapters;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import ie.rm.activities.R;
import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.ApplicationUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiptItem {
	View view;

	public ReceiptItem(Context context, ViewGroup parent,
			OnClickListener deleteListener, Receipt receipt) 
	{
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.receiptrow, parent, false);
		view.setTag(receipt);
		//view.setId(coffee.getCoffeeId());
		updateControls(receipt);
		ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
		imgDelete.setTag(receipt);
		imgDelete.setOnClickListener(deleteListener);
	}

	private void updateControls(Receipt receipt) {
		((TextView) view.findViewById(R.id.rowReceiptDescription)).setText(receipt.getDescription());
		( (TextView)view.findViewById(R.id.rowReceiptStore)).setText(receipt.getStore());
		( (TextView)view.findViewById(R.id.rowDate)).setText(ApplicationUtils.dateToString(receipt.getDate()));
		( (TextView)view.findViewById(R.id.rowPrice)).setText("€ "+ new DecimalFormat("0.00").format(receipt.getPrice()));
	}
}
