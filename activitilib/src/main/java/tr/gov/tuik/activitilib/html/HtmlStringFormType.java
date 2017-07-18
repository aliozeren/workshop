package tr.gov.tuik.activitilib.html;

import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractTextboxFormType;

public class HtmlStringFormType extends AbstractTextboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInputForType(FormProperty property) 
	{
		StringBuffer str = new StringBuffer("<input type='text' ");
		
		str.append(" id='")
			.append(super.getId())
			.append("' name='")
			.append(super.getId())
			.append("' ");
		
		
		if (property.getValue() != null) {
			str.append(" value='").append(property.getValue()).append("' ");
		}
		
		str.append(HTMLInputUtils.getInstance().prepareAttributes(super.getMap()));

		str.append(" />");
		

		return HTMLInputUtils.getInstance().createStandartInputDiv(super.getLabel(), str.toString());
	}

}
