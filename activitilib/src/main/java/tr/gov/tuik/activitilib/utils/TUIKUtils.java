package tr.gov.tuik.activitilib.utils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

public class TUIKUtils 
{

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

	public void logError(Logger logger, Exception e) 
	{
		if (e != null) {
			logger.error(e.getMessage());
		}
	}
	
	public Map<String, String> convertJSONStringToOptions(String optionsInJSON)
	{
		Map<String, String> options = new LinkedHashMap<String,String>();

		if (logger.isDebugEnabled()) {
			logger.debug("Parsing options from JSON String : " + optionsInJSON);
		}
		
		if (optionsInJSON != null) {
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(optionsInJSON);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONArray innerJsonArray = (JSONArray) jsonArray.get(i);
					if (innerJsonArray.length() == 2) {
						options.put(innerJsonArray.getString(0), innerJsonArray.getString(1));
						if (logger.isDebugEnabled()) {
							logger.debug(" >>> option : " + innerJsonArray.getString(0) + " label: " + innerJsonArray.getString(1));
						}
					} else {
						throw new RuntimeException("Invalid options syntax. Check if options are defined as JSON array");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid options syntax. Check if options are defined as JSON array");
			} 
		}
		
		return options;
	}
	
}
