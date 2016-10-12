package tr.gov.tuik.activitilib.types;

import java.util.Map;

public interface FormToMapConverter 
{
	public Map<String, String> formToMap(Object formProperties);
}
