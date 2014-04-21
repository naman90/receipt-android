package ie.rm.activities;

import ie.rm.activities.R;
import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.PersistenceManager;
import ie.rm.receiptadapters.ReceiptListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ReceiptFragment  extends ListFragment implements  OnClickListener
{ 
  protected Base 						activity;
  protected static ReceiptListAdapter 	listAdapter;
  protected ListView 					listView;

@Override
  public void onAttach(Activity activity)
  {
    super.onAttach(activity);
    this.activity = (Base) activity;   
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    listAdapter = new ReceiptListAdapter(activity, this, PersistenceManager.getInstance().receiptList());
    setListAdapter(listAdapter);

  }
     
  @Override
  public void onStart()
  {
    super.onStart();
  }

  @Override
  public void onClick(View view)
  {
	  if(view.getTag() instanceof Receipt){
		  onReceiptDelete((Receipt)view.getTag());
	  }
  } 
  
  public void onReceiptDelete(final Receipt receipt)
  {
    String stringDesc = receipt.getDescription();
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setMessage("Are you sure you want to Delete the \'Receipt\' " + stringDesc + "?");
    builder.setCancelable(false);

    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int id)
      {
       PersistenceManager.getInstance().deleteReceipt(receipt); // remove from our list
        listAdapter.receiptList.remove(receipt); // update adapters data
        listAdapter.notifyDataSetChanged(); // refresh adapter
      }
    }).setNegativeButton("No", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int id)
      {
        dialog.cancel();
      }
    });
    AlertDialog alert = builder.create();
    alert.show();
  }

@Override
public void onListItemClick(ListView l, View v, int position, long id) {
	
	  /*  Bundle activityInfo = new Bundle(); // Creates a new Bundle object
	    activityInfo.putInt("coffeeID", v.getId() );
	    
	    Intent goEdit = new Intent(getActivity(), Edit.class); // Creates a new Intent
	     Add the bundle to the intent here 
	    goEdit.putExtras(activityInfo);
	    getActivity().startActivity(goEdit); // Launch the Intent
*/}
  
}

  