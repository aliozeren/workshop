package tr.gov.tuik.activitilib.zk;

import tr.gov.tuik.activitilib.types.CustomFormTypes;

public class ZKCustomFormTypes extends CustomFormTypes 
{

	public ZKCustomFormTypes()
	{
		super.setFactory(new ZKFormTypeFactory());
	}
}