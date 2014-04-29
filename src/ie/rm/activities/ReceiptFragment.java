package ie.rm.activities;

import ie.rm.activities.model.Receipt;
import ie.rm.activities.util.DeleteReceiptWithAlert;
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
  public static ReceiptListAdapter 	listAdapter;
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
		  DeleteReceiptWithAlert.deleteReceipt(view.getContext(), ((Receipt)view.getTag())).show();
	  }
  } 
  


@Override
public void onListItemClick(ListView l, View v, int position, long id) {
	  Object tag= v.getTag();
	  if(tag instanceof Receipt){
		  Receipt receipt =(Receipt) tag;
		  Bundle receiptBundle = new Bundle();
		  receiptBundle.putSerializable("receipt", receipt);
		  Intent goEdit = new Intent(getActivity(), ReceiptDisplay.class);
		  goEdit.putExtras(receiptBundle);
		  getActivity().startActivity(goEdit);  
	  }
	  /*  Bundle activityInfo = new Bundle(); // Creates a new Bundle object
	    activityInfo.putInt("coffeeID", v.getId() );
	    
	    Intent goEdit = new Intent(getActivity(), Edit.class); // Creates a new Intent
	     Add the bundle to the intent here 
	    goEdit.putExtras(activityInfo);
	    getActivity().startActivity(goEdit); // Launch the Intent
*/}
  
}

  