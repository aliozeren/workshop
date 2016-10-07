package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Combobox;

import tr.gov.tuik.activitilib.types.AbstractEnumFormType;

public class ZKEnumFormType extends AbstractEnumFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property) 
	{
		Combobox component = new Combobox();
		component= (Combobox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);
	}
	
}
