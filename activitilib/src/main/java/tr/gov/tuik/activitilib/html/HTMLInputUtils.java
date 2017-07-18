package tr.gov.tuik.activitilib.html;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;


public class HTMLInputUtils 
{

	private static HTMLInputUtils instance;
	
	public static String LABEL_LOCATOR_STRING = "__LABEL_LOCATOR_STRING__";
	public static String INPUT_LOCATOR_STRING = "__INPUT_LOCATOR_STRING__";
	
	private HTMLInputUtils() {}
	
	public static HTMLInputUtils getInstance() 
	{
		if (instance == null) {
			instance = new HTMLInputUtils();
		}
		
		return instance;
	}
	
	public StringBuffer prepareStandartInputDiv(StringBuffer str)
	{
		if (str == null) {
			str= new StringBuffer();
		}
		str.append("<div class='inputRowDiv'>");
		
		str.append("<div class='labelDiv'>")
			.append(HTMLInputUtils.LABEL_LOCATOR_STRING)
			.append("</div>");
		
		str.append("<div class='inputDiv'>")
			.append(HTMLInputUtils.INPUT_LOCATOR_STRING)
			.append("</div>");
		
		str.append("</div>");
		
		return str;
		
	}
	
	public String createStandartInputDiv( String label, String inputDefinition)
	{
		StringBuffer str= prepareStandartInputDiv(null);
		
		return StringUtils.replace(
					StringUtils.replace(str.toString(), HTMLInputUtils.LABEL_LOCATOR_STRING, label), 
					HTMLInputUtils.INPUT_LOCATOR_STRING, 
					inputDefinition);
		
	}
	
	public String prepareAttributes(Map<String,String> attributes, final String... except) 
	{
			return 
					attributes.entrySet()
							   .stream().filter(new Predicate<Map.Entry<String,String>>() {
									@Override
									public boolean test(Map.Entry<String,String> entry) {
										if (except != null) {
											for (int i=0; i<except.length; i++) {
												if (entry.getKey().equals(except[i])) {
													return false;
												}
											}
										}
										return true;
									}
								 })
								.map(e -> e.getKey()+"='"+ e.getValue() + "' ")
								.collect(Collectors.joining(" "));
	}
}
