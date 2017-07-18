package tr.gov.tuik.activitilib.samples;

import tr.gov.tuik.activitilib.TUIKProcessEngine;



public class DeployModelApp 
{
	public static void main( String[] args )
	{
		TUIKProcessEngine.getInstance().deployModel("Test Redirect 2","tr/gov/tuik/activitilib/samplebpm/testRedirect_2.bpmn20.xml");
		TUIKProcessEngine.destroy();
	}
}
