package ie.rm.activities.util;

import ie.rm.activities.ReceiptFragment;
import ie.rm.activities.model.Receipt;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DeleteReceiptWithAlert {


	public static AlertDialog deleteReceipt(Object context,final Receipt receipt){
	    AlertDialog.Builder builder =null;
	    if(context instanceof Activity){
	    	builder = new AlertDialog.Builder((Activity)context);
	    }else{
	    	builder = new AlertDialog.Builder((Context)context);
	    }
	    builder.setMessage("Are you sure you want to Delete the \'Receipt\' " + receipt.getDescription() + "?");
	    builder.setCancelable(false);
	    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    { 
	      public void onClick(DialogInterface dialog, int id)
	      {
	        if(PersistenceManager.getInstance().deleteReceipt(receipt)) // remove from our list
	        {
	           ReceiptFragment.listAdapter.receiptList.remove(receipt); // update adapters data
	           ReceiptFragment.listAdapter.notifyDataSetChanged();
	           receipt.setReceiptId(null);
	           dialog.dismiss();
	           // refresh adapter
	        }else{
	        	dialog.dismiss();
	        }
	      }
	    }).setNegativeButton("No", new DialogInterface.OnClickListener()
	    {
	      public void onClick(DialogInterface dialog, int id)
	      {
	        dialog.cancel();
	      }
	    });
	    AlertDialog alert = builder.create();
	    return alert;
	}
}
