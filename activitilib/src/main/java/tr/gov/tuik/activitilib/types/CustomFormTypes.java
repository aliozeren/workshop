package tr.gov.tuik.activitilib.types;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.engine.impl.form.FormTypes;

public class CustomFormTypes extends FormTypes 
{
	FormTypeFactoryInterface factory;
	
	@Override
	public AbstractFormType parseFormPropertyType(FormProperty formProperty) 
	{
		if (factory == null) {
			throw new TUIKFormTypeException("No Form Type Factory is assigned for CustomFormTypes");
		}
				
		ActivitiFormTypeInterface formObject = factory.getFormTypeInstance(formProperty.getType());

		if (formObject != null) {
			formObject.setVariable(formProperty.getVariable());
			return formObject.parseInput(formProperty);
		} else {
			throw new TUIKFormTypeException("No form type is implemented for " + formProperty.getType()); 
		}
	}

	public FormTypeFactoryInterface getFactory() 
	{
		return factory;
	}

	public void setFactory(FormTypeFactoryInterface factory) 
	{
		this.factory = factory;
	}	
}