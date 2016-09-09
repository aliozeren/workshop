package tr.gov.tuik.activitilib.types;

public class TUIKFormTypeException extends RuntimeException 
{

	private static final long serialVersionUID = 295511169041299882L;

	public TUIKFormTypeException()
	{
		super();
	}
	
	public TUIKFormTypeException(Throwable exception)
	{
		super(exception);
	}
	
	public TUIKFormTypeException(String message)
	{
		super(message);
	}
	
}
