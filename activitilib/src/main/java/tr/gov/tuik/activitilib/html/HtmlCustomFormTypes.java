package tr.gov.tuik.activitilib.html;

import tr.gov.tuik.activitilib.types.CustomFormTypes;

public class HtmlCustomFormTypes extends CustomFormTypes 
{

	public HtmlCustomFormTypes()
	{
		super.setFactory(new HtmlFormTypeFactory());
	}
}