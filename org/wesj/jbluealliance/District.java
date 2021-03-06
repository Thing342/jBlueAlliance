package org.wesj.jbluealliance;

public enum District
{
	REGIONAL (0, "", "", ""),
	MICHIGAN (1, "Michigan", "fim", "FIRST in Michigan"),
	MID_ATLANTIC (2, "Mid Atlantic", "mar", "Mid-Atlantic Robotics"),
	NEW_ENGLAND (3, "New England", "ne", "New England"),
	PACIFIC_NORTHWEST (4, "Pacific Northwest", "pnw", "Pacific Northwest"),
	INDIANA (5, "Indiana", "in", "IndianaFIRST"),
	CHESAPEAKE (6, "Chesapeake", "chs", "FIRST Chesapeake"),
	NORTH_CAROLINA (7, "North Carolina", "nc", "North Carolina"),
	GEORGIA (8, "Georgia", "pch", "Peachtree District");

	private final int value;
	private final String name;
	private final String abbrev;
	private final String long_name;

	public static District getInstance(int constant)
	{
		for(District e : values()) {
			if(e.value == constant) return e;
		}

		return REGIONAL;
	}

	private District(int value, String name, String abbrev, String long_name)
	{
		this.value = value;
		this.name = name;
		this.abbrev = abbrev;
		this.long_name = long_name;
	}

	public int getValue()
	{
		return value;
	}

	public String getName()
	{
		return name;
	}

	public String getAbbrev()
	{
		return abbrev;
	}

	public String getLong_name()
	{
		return long_name;
	}
}
