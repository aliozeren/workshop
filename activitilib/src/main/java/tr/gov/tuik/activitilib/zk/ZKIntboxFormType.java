package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Intbox;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.types.AbstractIntboxFormType;

public class ZKIntboxFormType extends AbstractIntboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property, String value) 
	{
		Intbox component = new Intbox();
		component= (Intbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setFormat(super.getMap().get("format"));
		
		if (value != null) {
			try {
				component.setValue(Integer.parseInt(value));
			} catch (Exception e) {
				e.printStackTrace();
				throw new TUIKProcessEngineException("Invalid integer value for input "+ property.getName() + ":" + value);
			} 
		}
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
	
}
