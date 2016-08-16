package tr.gov.tuik.activitilib;

import java.util.Date;

import org.apache.log4j.Logger;

public class TUIKUtils {

	private final static Logger logger = Logger.getLogger(TUIKUtils.class);

	private static TUIKUtils instance = null;
	
	private TUIKUtils() 
	{
	}

	public static TUIKUtils getInstance()
	{
		if (instance == null) {
			instance = new TUIKUtils();
		}
		return instance;
	}
	
	public boolean isEmpty(String str)
	{
		if ("".equals(str) || str == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean isEmpty(Date date)
	{
		if (date == null) {
			return true;
		}
		
		return false;
	}
	
	public String getCurrentUser() 
	{
		logger.warn("No implementation found for TUIKUtils.getCurrentUser()");
		return "admin";
	}
}
