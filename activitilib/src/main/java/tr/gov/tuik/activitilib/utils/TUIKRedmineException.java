package tr.gov.tuik.activitilib.utils;


public class TUIKRedmineException extends RuntimeException 
{
	private static final long serialVersionUID = 295585069041299882L;

	public TUIKRedmineException()
	{
		super();
	}
	
	public TUIKRedmineException(Throwable exception)
	{
		super(exception);
	}
	
	public TUIKRedmineException(String message)
	{
		super(message);
	}
	
	
	public TUIKRedmineException(String message, Throwable exception)
	{
		super(message, exception);
	}

}
