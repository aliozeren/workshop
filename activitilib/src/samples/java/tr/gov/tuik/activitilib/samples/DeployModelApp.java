package tr.gov.tuik.activitilib.samples;

import tr.gov.tuik.activitilib.TUIKProcessEngine;



public class DeployModelApp 
{
	public static void main( String[] args )
	{
		TUIKProcessEngine.getInstance().deployModel("Redirect Test","tr/gov/tuik/activitilib/samplebpm/testRedirect.bpmn20.xml");
		TUIKProcessEngine.destroy();
	}

}
