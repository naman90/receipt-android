package ie.rm.activities.util;

import ie.rm.activities.model.Receipt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;

public class PersistenceManager {
	  
	private  static DbxAccountManager mAccountManager;
	private  static DbxAccount mAccount;
	private  static DbxDatastore store;
	private  static DbxFileSystem dbxFs;
	private  static DbxTable table;
	private static PersistenceManager self;
	private List<Receipt> receiptArray;
	
	public static PersistenceManager getInstance(){
		if(self==null){
			self= new PersistenceManager();
			self.receiptArray= new ArrayList<Receipt>();
		}
		return self;
	}
	
	public void initializePersistence(Context application){
		mAccountManager = DbxAccountManager.getInstance(application, "v3c5yh4aunuc0su", "6zms1t85qxcdjoh");
	}
	
	public boolean isAccountSetup(){
		return mAccountManager!=null &&mAccount!=null && mAccount.isLinked();
	}
	public boolean linkDropBox(Activity initiatingActivity,int requestCode){
		if(mAccountManager.hasLinkedAccount())
		  return setupPersistenceIfLinked();
		  else
		  mAccountManager.startLink((Activity)initiatingActivity, requestCode);
		return false;
	}
	
	public void unlinkDropbox(){
		if(mAccount!=null)
		mAccount.unlink();
		
	}
	
	public boolean setupPersistenceIfLinked(){
		 mAccount = mAccountManager.getLinkedAccount();
         if(mAccount!=null){
        	//try to open default store and filesystem
        	try{
        	if(store==null){
        		store=DbxDatastore.openDefault(mAccount);
        		store.addSyncStatusListener(new ReceiptStoreSyncListener());
        		
        	}
        	if(dbxFs==null){
        		dbxFs = DbxFileSystem.forAccount(mAccountManager.getLinkedAccount());
        	}
        	if(table==null){
        		table = store.getTable("receipts");
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
		if(receiptArray.size()==0){
			loadRecordsInArray();
		}
		return receiptArray;
	}
	
	public void loadRecordsInArray(){
		if(mAccount!=null && mAccount.isLinked()){
		if(table!=null){
			try{
			DbxTable.QueryResult queryResult = table.query();
			Iterator<DbxRecord> receiptIterator=queryResult.iterator();
			while(receiptIterator.hasNext()){
				DbxRecord record = receiptIterator.next();
				receiptArray.add(getReceiptFromDbRecord(record));
			}
			}catch(Exception exception){
				Log.e(this.getClass().getName(), exception.getMessage());
				return;
			}
		}
		}
	}
	
	public Receipt getReceiptFromDbRecord(DbxRecord record){
		Receipt receipt = new Receipt();
		receipt.setReceiptId(record.getId());
		receipt.setStore(record.getString("store"));
		receipt.setDescription(record.getString("description"));
		receipt.setPrice(Double.parseDouble(record.getString("price")));
		receipt.setImage(record.getString("image"));
		receipt.setDate(ApplicationUtils.stringToDate(record.getString("date")));
		return receipt;
	}
	
	public boolean deleteReceipt(Receipt receipt){
	try{
	  DbxRecord receiptRecord=	table.get(receipt.getReceiptId());
	  if(receiptRecord!=null){
		  String imageLocation= receipt.getImage();
		  receiptRecord.deleteRecord();
		  receiptArray.remove(receiptArray.indexOf(receipt));
		  deleteFileAtLocation(imageLocation);
		  store.sync();
	  }
	  return true;
	}catch(Exception e){
		Log.w(this.getClass().getName(), e);
		return false;
	}
		
	}
	
	public void addReceipt(Receipt receipt){
		String imageName = storeImageToFileSystemForReceipt(receipt);
		if(imageName!=null && imageName.length()>0){
			DbxRecord newRecord=table.insert().set("description", receipt.getDescription()).set("store", receipt.getStore())
			.set("price", receipt.getPrice().toString()).set("date", ApplicationUtils.dateToString(receipt.getDate()))
			.set("image", imageName);
			receipt.setReceiptId(newRecord.getId());
			receiptArray.add(receipt);
			try {
				store.sync();
				Log.v(this.getClass().getName(), "Saved Receipt"+receipt);
			} catch (DbxException e) {
				Log.w(this.getClass().getName(), e);
			}
			
		}
	}
	
	
	public String storeImageToFileSystemForReceipt(Receipt receipt){
		String fileName= receipt.getStore()+ApplicationUtils.dateToFlatString(receipt.getDate())+UUID.randomUUID().toString()+".png";
		File tempFile = new File(receipt.getImage());
		try{
		if(tempFile.exists()){
		DbxFile receiptImageFile = dbxFs.create(new DbxPath(fileName));
		receiptImageFile.writeFromExistingFile(tempFile, false);
		receipt.setImage(fileName);
		tempFile.delete();
		receiptImageFile.close();
		}
		}catch(DbxException dbxEcxeption){
			Log.e(this.getClass().getName(), "Error in DropBox File Creation");
			return "";
		}
		catch (IOException e) {
			Log.e(this.getClass().getName(), "Error in Saving file to Device.Check Memeory Status");
			return "";
		}
		return fileName;	
		
	}
	
	public DbxFile getDbxFile(String  locationPath){
	 try{
	  DbxFile file= dbxFs.open(new DbxPath(locationPath));
	  return file;
	 }catch(Exception e){
		 Log.e(this.getClass().getName(), e.getMessage());
		 return null;
	 }
	  
	}
	
	public void syncAddReceipt(Receipt receipt)
	{
	   receiptArray.add(receipt);
	}


public void deleteAllRecords()
{
    if(table!=null&& dbxFs!=null){
    	try{
    	DbxTable.QueryResult queryResult = store.getTable("receipts").query();
    	Iterator<DbxRecord> receiptIterator=queryResult.iterator();
		while(receiptIterator.hasNext()){
			DbxRecord record = receiptIterator.next();
			String image = record.getString("image");
			record.deleteRecord();
			//if(image!=null && image.length()>0){
			//	deleteFileAtLocation(image);
		     //}
         }
		store.sync();
    	}catch(Exception exception){
    		Log.w(this.getClass().getName(),exception.getMessage());
    	}
    }
}

  void deleteFileAtLocation(String location)
{
	  DbxPath path = new DbxPath(location);
      try{
	  dbxFs.delete(path);
      }catch(Exception exception){
    	  Log.w(this.getClass().getName(),exception.getMessage());
      }
    
}
  
  public void updateReceipt(Receipt receipt)
  {
	  try{
	  DbxRecord receiptRecord=	table.get(receipt.getReceiptId());
	  receiptRecord.set("description", receipt.getDescription()).set("store", receipt.getStore())
	  .set("price", receipt.getPrice().toString()).set("date", ApplicationUtils.dateToString(receipt.getDate()));
      store.sync();
      for(Receipt receiptInArray:receiptArray){
    	  if(receiptInArray.getReceiptId().equals(receipt.getReceiptId())){
    		  receiptInArray.setDescription(receipt.getDescription());
    		  receiptInArray.setStore(receipt.getStore());
    		  receiptInArray.setPrice(receipt.getPrice());
    		  receiptInArray.setDate(receipt.getDate());
    	  }
      }
	  }catch(Exception e){
		  Log.w(this.getClass().getName(), e);
	  }
	 

  }



}
