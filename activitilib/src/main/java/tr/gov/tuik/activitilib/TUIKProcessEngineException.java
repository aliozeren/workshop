package tr.gov.tuik.activitilib;

public class TUIKProcessEngineException extends RuntimeException 
{

	private static final long serialVersionUID = 295585069041299882L;

	public TUIKProcessEngineException()
	{
		super();
	}
	
	public TUIKProcessEngineException(Throwable exception)
	{
		super(exception);
	}
	
	public TUIKProcessEngineException(String message)
	{
		super(message);
	}
	
}
