package tr.gov.tuik.activitilib.types;

import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractEnumFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 3868249214623992954L;
	public static final String NAME = "enum";

	private Map<String,String> values;

	public AbstractFormType parseInput(FormProperty property)
	{
		values = new HashMap<String,String>();
		for (FormValue fv : property.getFormValues()) {
			values.put(fv.getId(), fv.getName());
		}

		super.parseInput(property);
		
		return this;
	}

	public Map<String, String> getValues() 
	{
		return values;
	}

	public void setValues(Map<String, String> values) 
	{
		this.values = values;
	}

	public String getName() 
	{
		return  AbstractEnumFormType.NAME;
	}

	@Override
	public Object getInformation(String key) 
	{
		if ("values".equals(key)) {
			return values;
		}
		return null;
	}

	@Override
	public Object convertFormValueToModelValue(String propertyValue) 
	{
		validateValue(propertyValue);
		return propertyValue;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) 
	{
		if (modelValue != null) {
			validateValue(modelValue.toString());
			return (String) modelValue;
		}
		
		return null;
	}

	protected void validateValue(String value) 
	{
		if(value != null && values != null && !values.containsKey(value)) {
				throw new TUIKFormTypeException(value + " is not acceptable for the property...");
		}
	}

}
