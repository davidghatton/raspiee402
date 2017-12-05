package ee402;

import java.io.*;


public class Fish implements Serializable
{
	private String name;
	private String type;
	
	public Fish (String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName() { return this.name; }
	
	public String getType() { return this.type; }
}