package ie.rm.activities.util;

import ie.rm.activities.model.Receipt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
	  
	private  DbxAccountManager mAccountManager;
	private  DbxAccount mAccount;
	private  DbxDatastore store;
	private  DbxFileSystem dbxFs;
	private  DbxTable table;
	private static PersistenceManager self;
	private List<Receipt> receiptArray;
	
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
		Receipt receipt= new Receipt();
		receipt.setDate(new Date());
		receipt.setReceiptId("1");
		receipt.setPrice(12.22);
		receipt.setStore("Tam");
		receipt.setDescription("Mela Store");
		receiptArray.add(receipt);
		return receiptArray;
		
	}
	
	public void deleteReceipt(Receipt receipt){
		
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
			} catch (DbxException e) {
				Log.w(this.getClass().getName(), e);
			}
			
		}
	}
	
	
	public String storeImageToFileSystemForReceipt(Receipt receipt){
		String fileName= receipt.getStore()+ApplicationUtils.dateToString(receipt.getDate())+UUID.randomUUID().toString()+".png";
		File tempFile = new File(receipt.getImage());
		try{
		if(tempFile.exists()){
		DbxFile receiptImageFile = dbxFs.create(new DbxPath(fileName));
		receiptImageFile.writeFromExistingFile(tempFile, false);
		receipt.setImage(fileName);
		tempFile.delete();
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
	

}
