package tr.gov.tuik.activitilib;



public class DeployModelApp 
{
	public static void main( String[] args )
	{
		TUIKProcessEngine.getInstance().deployModel("Event Test","tr/gov/tuik/activitilib/samplebpm/eventTestProcess.bpmn20.xml");
		TUIKProcessEngine.destroy();
	}

}
