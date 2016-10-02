package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Textbox;

import tr.gov.tuik.activitilib.types.AbstractStringFormType;

public class ZKStringFormType extends AbstractStringFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;


	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Textbox component = new Textbox();
		return ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
	}
	
}
