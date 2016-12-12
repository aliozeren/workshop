package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Decimalbox;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.types.AbstractDecimalboxFormType;

public class ZKDecimalboxFormType extends AbstractDecimalboxFormType
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInputForType(FormProperty property) 
	{
		Decimalbox component = new Decimalbox();
		component= (Decimalbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setFormat(super.getMap().get("format"));
		
		if (property.getValue() != null) {
			try {
				component.setValue(property.getValue());
			} catch (Exception e) {
				throw new TUIKProcessEngineException("Invalid decimal format for input "+ property.getName() + ":" + property.getValue(), e);
			} 
		}

		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
