package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Decimalbox;

import tr.gov.tuik.activitilib.types.AbstractLongFormType;

public class ZKLongFormType extends AbstractLongFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Decimalbox component = new Decimalbox();
		return ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
	}
	
}
