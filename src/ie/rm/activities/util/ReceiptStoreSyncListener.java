package ie.rm.activities.util;

import ie.rm.activities.ReceiptFragment;
import ie.rm.activities.model.Receipt;

import java.util.Map;
import java.util.Set;

import android.util.Log;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastore.SyncStatusListener;
import com.dropbox.sync.android.DbxRecord;

public class ReceiptStoreSyncListener implements SyncStatusListener {

	@Override
	public void onDatastoreStatusChange(DbxDatastore datastore) {
		if(datastore.getSyncStatus().hasIncoming){
			try{
			 Map<String, Set<DbxRecord>> changes = datastore.sync();
			 Set<DbxRecord> downloadedRecords=changes.get("receipts");
			 PersistenceManager pManager= PersistenceManager.getInstance();
			 for(DbxRecord record  :downloadedRecords){
				Receipt receipt= pManager.getReceiptFromDbRecord(record);
				pManager.syncAddReceipt(receipt);
			 }
			ReceiptFragment.listAdapter.notifyDataSetChanged();
			}catch(Exception exception){
				Log.w(this.getClass().getName(), exception);
			}
		}
		

	}

}
