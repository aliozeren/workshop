package tr.gov.tuik.activitilib.zk;

import java.util.HashMap;
import java.util.Map;

import tr.gov.tuik.activitilib.types.AbstractComboboxFormType;
import tr.gov.tuik.activitilib.types.AbstractDateFormType;
import tr.gov.tuik.activitilib.types.AbstractDecimalboxFormType;
import tr.gov.tuik.activitilib.types.AbstractFileFormType;
import tr.gov.tuik.activitilib.types.AbstractIntboxFormType;
import tr.gov.tuik.activitilib.types.AbstractPasswordFormType;
import tr.gov.tuik.activitilib.types.AbstractProcessFormType;
import tr.gov.tuik.activitilib.types.AbstractTextareaFormType;
import tr.gov.tuik.activitilib.types.AbstractTextboxFormType;
import tr.gov.tuik.activitilib.types.ActivitiFormTypeInterface;
import tr.gov.tuik.activitilib.types.FormTypeFactoryInterface;
import tr.gov.tuik.activitilib.types.TUIKFormTypeException;

public class ZKFormTypeFactory implements FormTypeFactoryInterface
{

	private static final Map<String, Class<?>> formTypes;
	static {
		formTypes= new HashMap<String, Class<?>>();
		// activiti-explorer form types
		formTypes.put("boolean", ZKCheckboxFormType.class);
		formTypes.put("enum", ZKComboboxFormType.class);
		formTypes.put("string", ZKTextboxFormType.class);
		formTypes.put("long", ZKDecimalboxFormType.class);
		
		// custom form types (date is used for both)
		formTypes.put(AbstractComboboxFormType.NAME, ZKComboboxFormType.class);
		formTypes.put(AbstractIntboxFormType.NAME, ZKIntboxFormType.class);
		formTypes.put(AbstractDecimalboxFormType.NAME, ZKDecimalboxFormType.class);
		formTypes.put(AbstractTextboxFormType.NAME, ZKTextboxFormType.class);
		formTypes.put(AbstractTextareaFormType.NAME, ZKTextareaFormType.class);
		formTypes.put(AbstractDateFormType.NAME, ZKDateFormType.class);
		formTypes.put(AbstractPasswordFormType.NAME, ZKPasswordFormType.class);
		formTypes.put(AbstractFileFormType.NAME, ZKFileFormType.class);
		formTypes.put(ZKStudyComboboxFormType.NAME, ZKStudyComboboxFormType.class);
		formTypes.put(ZKStaffComboboxFormType.NAME, ZKStaffComboboxFormType.class);
		formTypes.put(AbstractProcessFormType.NAME, ZKProcessFormType.class);
	}
	
	public ActivitiFormTypeInterface getFormTypeInstance(String name)
	{
		if (formTypes.containsKey(name)) {
			try {
				return (ActivitiFormTypeInterface) formTypes.get(name).newInstance();
			} catch (Exception e) {
			} 
		}
		
		throw new TUIKFormTypeException("Error in creating object with the type named " + name );
	}

}
