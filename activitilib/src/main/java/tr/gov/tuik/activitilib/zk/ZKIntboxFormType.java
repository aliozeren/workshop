package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Intbox;

import tr.gov.tuik.activitilib.types.AbstractIntboxFormType;

public class ZKIntboxFormType extends AbstractIntboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property) 
	{
		Intbox component = new Intbox();
		component= (Intbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setFormat(super.getMap().get("format"));
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
