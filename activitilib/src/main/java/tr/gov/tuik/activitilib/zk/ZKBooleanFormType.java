package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Checkbox;

import tr.gov.tuik.activitilib.types.AbstractBooleanFormType;

public class ZKBooleanFormType extends AbstractBooleanFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Checkbox component = new Checkbox();
		return ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
	}
}
