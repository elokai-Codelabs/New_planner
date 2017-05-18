package com.group.routeplanner.data;

public class AltRouteDTO 
{
	Station start;
	Station end;
	
	public AltRouteDTO(Station start, Station end )
	{
		super();
		this.start = start;
		this.end = end;
	}

	public Station getStart() 
	{
		return start;
	}

	public void setStart(Station start) 
	{
		this.start = start;
	}

	public Station getEnd() 
	{
		return end;
	}

	public void setEnd(Station end) 
	{
		this.end = end;
	}
}
