package org.wesj.jbluealliance;

import org.json.JSONException;
import org.json.JSONObject;

public class Team implements Comparable<Team>
{
	private String website;
	private String name;
	private String long_name;
	private String locality;
	private String state;
	private String country;
	private String location;
	private String key;
	private int teamNum;
	private int rookie_year;

	public Team(JSONObject source)
	{
		try {
			website = source.opt("website").toString();
			name = source.optString("nickname");
			long_name = source.optString("name");
			locality = source.optString("locality");
			state = source.optString("region");
			country = source.optString("country_name");
			location = source.optString("location");
			teamNum = source.optInt("team_number");
			key = source.optString("key");
			rookie_year = source.optInt("rookie_year", 0);
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println(source.toString(5));
		}
	}

	@Override
	public String toString()
	{
		return getTeamNum() + ": " + getName();
	}

	@Override
	public int compareTo(Team o)
	{
		return getTeamNum() - o.getTeamNum();
	}

	public String getWebsite()
	{
		return website;
	}

	public String getName()
	{
		return name;
	}

	public String getLong_name()
	{
		return long_name;
	}

	public String getLocality()
	{
		return locality;
	}

	public String getState()
	{
		return state;
	}

	public String getCountry()
	{
		return country;
	}

	public String getLocation()
	{
		return location;
	}

	public String getKey()
	{
		return key;
	}

	public int getTeamNum()
	{
		return teamNum;
	}

	public int getRookie_year()
	{
		return rookie_year;
	}
}
