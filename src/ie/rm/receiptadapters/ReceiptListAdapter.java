package ie.rm.receiptadapters;


import ie.rm.activities.model.Receipt;

import java.util.List;

import ie.rm.activities.R;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ReceiptListAdapter extends ArrayAdapter<Receipt> 
{
  private Context context;
  private OnClickListener deleteListener;
  public List<Receipt> receiptList;

  public ReceiptListAdapter(Context context, OnClickListener deleteListener, List<Receipt> receiptList)
  {
    super(context,R.layout.receiptrow, receiptList);
    this.context = context;
    this.deleteListener = deleteListener;
    this.receiptList=receiptList;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    ReceiptItem receiptItem = new ReceiptItem(context, parent, deleteListener, receiptList.get(position));
    return receiptItem.view;
  }

  @Override
  public int getCount()
  {
    return receiptList.size();
  }
  
  @Override
  public Receipt getItem(int position)
  {
	  return receiptList.get(position);
  }

/*  @Override
  public long getItemId(int position)
  {
    return receiptList.get(position).getReceiptId();
  }
*/
  @Override
  public int getPosition(Receipt c)
  { 
	return receiptList.indexOf(c);
  }
}
