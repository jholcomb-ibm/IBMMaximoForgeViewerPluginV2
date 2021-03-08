package psdi.app.bim.viewer.lmv.util;

public class MarkupObject {
	
	public MarkupObject(String type, String position, String size) 
	{
		this.type = type;
		this.position = position;
		this.size = size;
	}

	public String getType() 
	{
		return type;
	}

	public String getPosition() 
	{
		return position;
	}

	public String getSize() 
	{
		return size;
	}

	private String type;
	
	private String position;
	
	private String size;

}
