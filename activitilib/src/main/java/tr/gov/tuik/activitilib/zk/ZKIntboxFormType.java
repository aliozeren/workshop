package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Intbox;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.types.AbstractIntboxFormType;

public class ZKIntboxFormType extends AbstractIntboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInputForType(FormProperty property) 
	{
		Intbox component = new Intbox();
		component= (Intbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setFormat(super.getMap().get("format"));
		
		if (property.getValue() != null) {
			try {
				component.setValue(Integer.parseInt(property.getValue()));
			} catch (Exception e) {
				throw new TUIKProcessEngineException("Invalid integer value for input "+ property.getName() + ":" + property.getValue(), e);
			} 
		}
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
