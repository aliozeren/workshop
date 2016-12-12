package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import tr.gov.tuik.activitilib.types.AbstractComboboxFormType;

public class ZKComboboxFormType extends AbstractComboboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInputForType(FormProperty property) 
	{
		Combobox component = new Combobox();
		component= (Combobox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		for ( String key : super.getOptions().keySet()) {
			Comboitem item = new Comboitem();
			item.setLabel(super.getOptions().get(key));
			item.setValue(key);
			component.appendChild(item);
		}
		
		component.setValue(property.getValue());

		return  ZKInputUtils.getInstance().getDynamicModel(this, component);
	}
	
}
