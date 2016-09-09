package tr.gov.tuik.activitilib.zk;


import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class ZKDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public String renderInput(FormProperty property) 
	{
		StringBuffer str = new StringBuffer();
		str.append("<input type='date' id='")
			.append(property.getId()).append("' ")
			.append(" format='").append(super.getDatePattern()).append("' ")
			.append(" />");
		
		return str.toString();
	}


}
