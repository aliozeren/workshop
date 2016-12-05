package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Combobox;

import tr.gov.tuik.activitilib.TUIKConstants;
import tr.gov.tuik.activitilib.types.AbstractComboboxFormType;

public class ZKMailComboboxFormType extends AbstractComboboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public static final String NAME = "mail";
	
	public static final String GROUP_PROPERTY = "group";
	

	public DynamicModel renderInput(FormProperty property, String value) 
	{
		Combobox component = new Combobox();
		component= (Combobox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);

		component.setAttribute(TUIKConstants.COMBOBOX_TYPE_IDENTIFIER,ZKMailComboboxFormType.NAME);
		component.setAttribute(ZKMailComboboxFormType.GROUP_PROPERTY, super.getMap().get(ZKMailComboboxFormType.GROUP_PROPERTY));
		

		if (value != null) {
			component.setValue(value);
		}		
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);
	}

	
	@Override
	public String getName() 
	{
		return ZKMailComboboxFormType.NAME;
	}

	@Override
	protected void validateValue(String value) 
	{
	}
	
	
}
