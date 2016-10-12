package tr.gov.tuik.activitilib.zk;

import tr.gov.tuik.activitilib.types.CustomFormTypes;
import tr.gov.tuik.activitilib.types.FormTypeFactoryInterface;

public class ZKCustomFormTypes extends CustomFormTypes 
{

	public ZKCustomFormTypes()
	{
		FormTypeFactoryInterface factory= new ZKFormTypeFactory();
		super.setFactory(factory);
	}
}