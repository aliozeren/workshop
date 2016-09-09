package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractEnumFormType;

public class ZKEnumFormType extends AbstractEnumFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInput(FormProperty property) 
	{
		StringBuffer str = new StringBuffer();
		str.append("<input type='numeric' id='")
			.append(property.getId()).append("' ")
			.append(" />");
		
		return str.toString();
	}
	
}
