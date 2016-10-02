package tr.gov.tuik.activitilib.zk;


import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Datebox;

import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class ZKDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Datebox component = new Datebox();
		return ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
	}


}
