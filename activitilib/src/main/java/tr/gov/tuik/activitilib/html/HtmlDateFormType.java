package tr.gov.tuik.activitilib.html;


import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class HtmlDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;


	public Object renderInputForType(FormProperty property) {
		return "<input type='date'/>";
	}


}
