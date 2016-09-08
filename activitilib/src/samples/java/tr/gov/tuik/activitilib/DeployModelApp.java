package tr.gov.tuik.activitilib;


public class DeployModelApp 
{
	public static void main( String[] args )
	{
		TUIKProcessEngine.getInstance().deployModel("Form Property Test","tr/gov/tuik/activitilib/samplebpm/formPropTest.bpmn20.xml");
		TUIKProcessEngine.destroy();
	}

}
