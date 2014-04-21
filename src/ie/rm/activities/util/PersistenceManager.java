package ie.rm.activities.util;

import ie.rm.activities.model.Receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxFileSystem;

public class PersistenceManager {
	  
	private  DbxAccountManager mAccountManager;
	private  DbxAccount mAccount;
	private  DbxDatastore store;
	private  DbxFileSystem dbxFs;
	private static PersistenceManager self;
	
	public static PersistenceManager getInstance(){
		if(self==null){
			self= new PersistenceManager();
		}
		return self;
	}
	
	public void initializePersistence(Context application){
		mAccountManager = DbxAccountManager.getInstance(application, "v3c5yh4aunuc0su", "6zms1t85qxcdjoh");
	}
	public boolean linkDropBox(Activity initiatingActivity,int requestCode){
		if(mAccountManager.hasLinkedAccount())
		  return setupPersistenceIfLinked();
		  else
		  mAccountManager.startLink((Activity)initiatingActivity, requestCode);
		return false;
	}
	
	public boolean setupPersistenceIfLinked(){
		 mAccount = mAccountManager.getLinkedAccount();
         if(mAccount!=null){
        	//try to open default store and filesystem
        	try{
        	if(store==null){
        		store=DbxDatastore.openDefault(mAccount);
        	}
        	if(dbxFs==null){
        		dbxFs = DbxFileSystem.forAccount(mAccountManager.getLinkedAccount());
        	}
        	}catch(Exception e){
        		 return false;
        	}
         	return true;
         }else{
         	return false;
         }
	}
	
	public List<Receipt> receiptList(){
		List<Receipt> list= new ArrayList<Receipt>();
		Receipt receipt= new Receipt();
		receipt.setDate(new Date());
		receipt.setReceiptId("1");
		receipt.setPrice(12.22);
		receipt.setStore("Tam");
		receipt.setDescription("Mela Store");
		list.add(receipt);
		return list;
	}
	
	public void deleteReceipt(Receipt receipt){
		
	}
	

}
