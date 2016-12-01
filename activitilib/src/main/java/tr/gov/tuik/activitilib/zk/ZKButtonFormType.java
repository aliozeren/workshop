package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Button;

import tr.gov.tuik.activitilib.types.AbstractButtonFormType;

public class ZKButtonFormType extends AbstractButtonFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property, String value) 
	{
		
		Button component = new Button();
		component= (Button) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setLabel(super.getLabel());
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component, false);

	}
	
}
