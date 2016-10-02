package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Combobox;

import tr.gov.tuik.activitilib.types.AbstractEnumFormType;

public class ZKEnumFormType extends AbstractEnumFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Combobox component = new Combobox();
		return ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
	}
	
}
