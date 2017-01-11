package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Textbox;

import tr.gov.tuik.activitilib.TUIKConstants;
import tr.gov.tuik.activitilib.types.AbstractRedmineFormType;

public class ZKRedmineFormType extends AbstractRedmineFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInputForType(FormProperty property) 
	{
		
		Textbox component = new Textbox();
		component= (Textbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setAttribute(TUIKConstants.COMBOBOX_TYPE_IDENTIFIER, ZKRedmineFormType.NAME);
		component.setAttribute(TUIKConstants.LABEL, super.getLabel());
		component.setAttribute(AbstractRedmineFormType.PROJECTKEY, super.getProjectKey());

		return  ZKInputUtils.getInstance().getDynamicModel(this, component, false);
	}
	
}
