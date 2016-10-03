package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;

import tr.gov.tuik.activitilib.types.AbstractLongFormType;

public class ZKLongFormType extends AbstractLongFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		
		Div div= ZKInputUtils.getInstance().getInputDiv(this);
		
		Decimalbox component = new Decimalbox();
		component= (Decimalbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		div.appendChild(component);
		
		return div;
	}
	
}
