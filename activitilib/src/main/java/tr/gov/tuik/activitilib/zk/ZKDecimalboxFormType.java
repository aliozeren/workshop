package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Decimalbox;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.types.AbstractDecimalboxFormType;

public class ZKDecimalboxFormType extends AbstractDecimalboxFormType
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property, String value) 
	{
		Decimalbox component = new Decimalbox();
		component= (Decimalbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setFormat(super.getMap().get("format"));
		
		if (value != null) {
			try {
				component.setValue(value);
			} catch (Exception e) {
				e.printStackTrace();
				throw new TUIKProcessEngineException("Invalid decimal format for input "+ property.getName() + ":" + value);
			} 
		}

		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
