package tr.gov.tuik.activitilib.html;

import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractComboboxFormType;

public class HtmlEnumFormType extends AbstractComboboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInputForType(FormProperty property) 
	{
		return "<input type='enum'/>";
	}
	
	
}
