package tr.gov.tuik.activitilib.html;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.types.AbstractComboboxFormType;
import tr.gov.tuik.activitilib.types.AbstractDateFormType;
import tr.gov.tuik.activitilib.types.AbstractIntboxFormType;
import tr.gov.tuik.activitilib.types.AbstractPasswordFormType;
import tr.gov.tuik.activitilib.types.AbstractTextboxFormType;
import tr.gov.tuik.activitilib.types.ActivitiFormTypeInterface;
import tr.gov.tuik.activitilib.types.FormTypeFactoryInterface;
import tr.gov.tuik.activitilib.types.TUIKFormTypeException;
import tr.gov.tuik.activitilib.utils.TUIKUtils;

public class HtmlFormTypeFactory implements FormTypeFactoryInterface
{
	
	private final static Logger logger = Logger.getLogger(HtmlFormTypeFactory.class);
	
	private static final Map<String, Class<?>> formTypes;
	static {
		formTypes= new HashMap<String, Class<?>>();
		formTypes.put(AbstractComboboxFormType.NAME, HtmlEnumFormType.class);
		formTypes.put(AbstractIntboxFormType.NAME, HtmlLongFormType.class);
		formTypes.put(AbstractTextboxFormType.NAME, HtmlStringFormType.class);
		formTypes.put(AbstractDateFormType.NAME, HtmlDateFormType.class);
		formTypes.put(AbstractPasswordFormType.NAME, HtmlBooleanFormType.class);
	}
	
	public ActivitiFormTypeInterface getFormTypeInstance(String name)
	{
		if (formTypes.containsKey(name)) {
			try {
				return (ActivitiFormTypeInterface) formTypes.get(name).newInstance();
			} catch (Exception e) {
				TUIKUtils.getInstance().logError(logger, e);
			} 
		}
		
		throw new TUIKFormTypeException("Error in creating object with the type named " + name );
	}

}
