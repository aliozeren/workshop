package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Checkbox;

import tr.gov.tuik.activitilib.types.AbstractCheckboxFormType;

public class ZKCheckboxFormType extends AbstractCheckboxFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInputForType(FormProperty property) 
	{
		
		Checkbox component = new Checkbox();
		component= (Checkbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		if (property.getValue() != null){
			component.setValue(property.getValue());
		}
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	}
}
