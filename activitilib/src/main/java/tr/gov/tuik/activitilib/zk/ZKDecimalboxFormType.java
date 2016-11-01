package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Decimalbox;

import tr.gov.tuik.activitilib.types.AbstractDecimalboxFormType;

public class ZKDecimalboxFormType extends AbstractDecimalboxFormType
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property) 
	{
		Decimalbox component = new Decimalbox();
		component= (Decimalbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setFormat(super.getMap().get("format"));

		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
