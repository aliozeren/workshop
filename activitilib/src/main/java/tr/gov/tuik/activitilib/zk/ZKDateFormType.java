package tr.gov.tuik.activitilib.zk;


import org.activiti.engine.form.FormProperty;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;

import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class ZKDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public HtmlBasedComponent renderInput(FormProperty property) 
	{
		Div div= ZKInputUtils.getInstance().getInputDiv(this);
		
		Datebox component = new Datebox();
		component.setFormat(this.getDatePattern());
		component= (Datebox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		div.appendChild(component);
		
		return div;
	};


}
