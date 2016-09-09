package tr.gov.tuik.activitilib.types;

import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractCommonFormType extends AbstractFormType implements ActivitiFormTypeInterface 
{
	private static final long serialVersionUID = 9174362796522080281L;
	
	private String variable;

	public void setVariable(String variable) 
	{
		this.variable= variable;
	}
	
	public String getVariable() 
	{
		return variable;
	}
	
	@Override
	public Object convertFormValueToModelValue(String propertyValue) 
	{
		return propertyValue;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) 
	{
		return modelValue != null ? modelValue.toString() : null;
	}
	
	public String getMimeType() 
	{
		return "plain/text";
	}

}
