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
			self.receiptArray= new ArrayList<Receipt>();
			Receipt receipt= new Receipt();
			receipt.setDate(new Date());
			receipt.setReceiptId("1");
			receipt.setPrice(12.22);
			receipt.setStore("Tam");
			receipt.setDescription("Mela Store");
			Receipt receipt1= new Receipt();
			receipt1.setDate(new Date());
			receipt1.setReceiptId("1");
			receipt1.setPrice(12.22);
			receipt1.setStore("Tam");
			receipt1.setDescription("Mela Store");
			Receipt receipt2= new Receipt();
			receipt2.setDate(new Date());
			receipt2.setReceiptId("1");
			receipt2.setPrice(12.22);
			receipt2.setStore("Tam");
			receipt2.setDescription("Mela Store");
			Receipt receipt3= new Receipt();
			receipt3.setDate(new Date());
			receipt3.setReceiptId("1");
			receipt3.setPrice(12.22);
			receipt3.setStore("Tam");
			receipt3.setDescription("Mela Store");
			Receipt receipt4= new Receipt();
			receipt4.setDate(new Date());
			receipt4.setReceiptId("1");
			receipt4.setPrice(12.22);
			receipt4.setStore("Tam");
			receipt4.setDescription("Mela Store");
			Receipt receipt5= new Receipt();
			receipt5.setDate(new Date());
			receipt5.setReceiptId("1");
			receipt5.setPrice(12.22);
			receipt5.setStore("Tam");
			receipt5.setDescription("Mela Store");
			Receipt receipt6= new Receipt();
			receipt6.setDate(new Date());
			receipt6.setReceiptId("1");
			receipt6.setPrice(12.22);
			receipt6.setStore("Tam");
			receipt6.setDescription("Mela Store");
			Receipt receipt7= new Receipt();
			receipt7.setDate(new Date());
			receipt7.setReceiptId("1");
			receipt7.setPrice(12.22);
			receipt7.setStore("Tam");
			receipt7.setDescription("Mela Store");
			Receipt receipt8= new Receipt();
			receipt8.setDate(new Date());
			receipt8.setReceiptId("1");
			receipt8.setPrice(12.22);
			receipt8.setStore("Tam");
			receipt8.setDescription("Mela Store");
			
			self.receiptArray.add(receipt);
			self.receiptArray.add(receipt1);
			self.receiptArray.add(receipt2);
			self.receiptArray.add(receipt3);
			self.receiptArray.add(receipt4);
			self.receiptArray.add(receipt5);
			self.receiptArray.add(receipt6);
			self.receiptArray.add(receipt7);
			self.receiptArray.add(receipt8);
			

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
