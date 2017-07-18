package tr.gov.tuik.activitilib.html;

import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractCheckboxFormType;

public class HtmlCheckboxFormType extends AbstractCheckboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInputForType(FormProperty property) 
	{
		StringBuffer str = new StringBuffer("<input type='checkbox' ");
		
		str.append(" id='")
			.append(super.getId())
			.append("' name='")
			.append(super.getId())
			.append("' ");
		
		
		String checkedValue= super.getMap().get("checkValue");
		if ( checkedValue != null) {
			str.append(" value='").append(super.getMap().get("checkValue")).append("' ");
			if (checkedValue.equals(property.getValue())) {
				str.append(" checked ");
			}
		}
		
		str.append(" />");
		
		return HTMLInputUtils.getInstance().createStandartInputDiv(super.getLabel(), str.toString());
	}

}
