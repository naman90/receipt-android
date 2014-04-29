package ie.rm.activities.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.DatePicker;

import com.dropbox.sync.android.DbxFile;

public class ApplicationUtils {
	
    private static SimpleDateFormat dateFormatter= new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat dateFormatterFlat= new SimpleDateFormat("dd-MM-yyyy");
    private static Calendar calendar = Calendar.getInstance();
    
	public static String dateToString(Date date){
		 return dateFormatter.format(date);
	}
	public static String dateToFlatString(Date date){
		return dateFormatterFlat.format(date);
	}
	
	public static Date stringToDate(String dateString){
		Date date=null;
		try{
		 date = dateFormatter.parse(dateString);
		}catch(ParseException exception){
			Log.w("ApplicationUtils", exception);
		}
		return date;
	}
	
	public static Bitmap loadImage(String mCurrentPhotoPath) {
		if(mCurrentPhotoPath!=null && mCurrentPhotoPath.length()>0){
		File file = new File(mCurrentPhotoPath);
	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	   /* int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;*/
	    // Determine how much to scale down the image
	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inPurgeable = true;
	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    bitmap=ExifUtil.rotateBitmapFromSrc(file.getAbsolutePath(), bitmap);
	    return bitmap;
		}
		return null;
	}
	
	public static Bitmap loadImageFromFileSystem(String mCurrentPhotoPath) {
		DbxFile dbXfile=PersistenceManager.getInstance().getDbxFile(mCurrentPhotoPath);
		
		try{
		Bitmap bitmap= BitmapFactory.decodeStream(dbXfile.getReadStream());
		dbXfile.close();
		bitmap=ExifUtil.rotateBitmap90Degrees(bitmap);
	    return bitmap;
		}catch(Exception e){
			System.out.print(e);
		}
		
		
	    // Get the dimensions of the bitmap
	  /*  BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(pathName, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	    // Determine how much to scale down the image
	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inPurgeable = true;
	    Bitmap bitmap = BitmapFactory.decodeFile(pathName, bmOptions);*/
	   // bitmap=ExifUtil.rotateBitmap(file.getAbsolutePath(), bitmap);
	    return null;
	}
	
	public static int getMonthOfDate(Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}
	public static int getDayOfDate(Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH)+1;
	}
	public static int getYearOfDate(Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	public static Date getDate(DatePicker datePicker){
		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		return calendar.getTime();
	}
	
	public static File createImageFile() throws IOException {
	    // Create an image file name
	    File storageDir = Environment.getExternalStorageDirectory();
	    File image = File.createTempFile(
	        "temp",  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    return image;
	}
}
