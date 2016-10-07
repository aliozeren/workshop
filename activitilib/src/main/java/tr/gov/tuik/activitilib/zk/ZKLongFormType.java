package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Decimalbox;

import tr.gov.tuik.activitilib.types.AbstractLongFormType;

public class ZKLongFormType extends AbstractLongFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property) 
	{
		Decimalbox component = new Decimalbox();
		component= (Decimalbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
