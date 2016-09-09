package tr.gov.tuik.activitilib.zk;

import tr.gov.tuik.activitilib.types.AbstractTestFormType;

public class ZKTestFormType extends AbstractTestFormType 
{
	private static final long serialVersionUID = 3761287922801320051L;

	public String renderInput(org.activiti.engine.form.FormProperty property)
	{
		StringBuffer str = new StringBuffer();
		str.append("<input type='text' id='")
		.append(property.getId())
		.append("' width='").append(super.getWidth()).append("' ")
		.append("' height='").append(super.getHeight()).append("' ")
		.append(" />");

		return str.toString();
	}


}