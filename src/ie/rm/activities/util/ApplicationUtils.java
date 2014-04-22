package ie.rm.activities.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationUtils {
	
    private static SimpleDateFormat dateFormatter= new SimpleDateFormat("dd/MM/yyyy");
    
	public static String dateToString(Date date){
		 return dateFormatter.format(date);
	}
}
