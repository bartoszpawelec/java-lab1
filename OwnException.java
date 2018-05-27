import java.io.IOException;

public class OwnException extends IOException {

	public OwnException(String name) 
	{
		super(name);
		System.out.println("There is no "+ name + " exception");
	}
}
