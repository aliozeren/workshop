package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;

import tr.gov.tuik.activitilib.types.AbstractBooleanFormType;

public class ZKBooleanFormType extends AbstractBooleanFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Div div= ZKInputUtils.getInstance().getInputDiv(this);
		
		Checkbox component = new Checkbox();
		component= (Checkbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		div.appendChild(component);
		
		return div;
		
	}
}
