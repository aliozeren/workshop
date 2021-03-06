package tr.gov.tuik.activitilib.types;

import java.util.Map;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;

import tr.gov.tuik.activitilib.utils.TUIKUtils;

public abstract class AbstractComboboxFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 3868249214623992954L;
	public static final String NAME = "combobox";

	private Map<String,String> options;

	public AbstractFormType parseInput(FormProperty property)
	{
		super.parseInput(property);
		
		String optionsStr= super.getMap().get("options");
		
		if (optionsStr == null) {
			options = super.getMap();
		} else {
			options= TUIKUtils.getInstance().convertJSONStringToOptions(optionsStr);
		}
		
		return this;
	}

	public String getName() 
	{
		return  AbstractComboboxFormType.NAME;
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
		if(super.isRequired() && value != null && options != null && !options.containsKey(value)) {
				throw new TUIKFormTypeException(value + " is not acceptable for the property...");
		}
	}

	public Map<String, String> getOptions() {
		return options;
	}

}
