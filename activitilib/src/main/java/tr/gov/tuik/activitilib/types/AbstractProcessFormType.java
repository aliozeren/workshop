package tr.gov.tuik.activitilib.types;


public abstract class AbstractProcessFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 2262742328986137324L;
	
	public static final String NAME = "process_name";
	
	
	public String getName() 
	{
		return AbstractProcessFormType.NAME;
	}
}
