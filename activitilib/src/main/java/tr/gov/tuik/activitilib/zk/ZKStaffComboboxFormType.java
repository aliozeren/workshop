package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Combobox;

import tr.gov.tuik.activitilib.TUIKConstants;
import tr.gov.tuik.activitilib.types.AbstractComboboxFormType;

public class ZKStaffComboboxFormType extends AbstractComboboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public static final String NAME = "staff";
	
	public static final String DEPARTMENT_PROPERTY = "department";
	

	public DynamicModel renderInputForType(FormProperty property) 
	{
		Combobox component = new Combobox();
		component= (Combobox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);

		component.setAttribute(TUIKConstants.COMBOBOX_TYPE_IDENTIFIER,ZKStaffComboboxFormType.NAME);
		component.setAttribute(ZKStaffComboboxFormType.DEPARTMENT_PROPERTY, super.getMap().get(ZKStaffComboboxFormType.DEPARTMENT_PROPERTY));
		

		if (property.getValue() != null) {
			component.setValue(property.getValue());
		}		
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);
	}

	
	@Override
	public String getName() 
	{
		return ZKStaffComboboxFormType.NAME;
	}

	@Override
	protected void validateValue(String value) 
	{
	}
	
	
}
