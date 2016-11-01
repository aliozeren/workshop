package tr.gov.tuik.activitilib.html;

import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractIntboxFormType;

public class HtmlLongFormType extends AbstractIntboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInput(FormProperty property) 
	{
		return "<input type='long'/>";
	}
	
}
