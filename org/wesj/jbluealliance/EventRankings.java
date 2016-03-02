package org.wesj.jbluealliance;

import org.json.JSONArray;

public class EventRankings
{
	private final String[][] rankings;

	public EventRankings(JSONArray jsonArray)
	{
		if(jsonArray.length() == 0) {
			rankings = new String[0][0];
			return;
		}
		else rankings = new String[jsonArray.length()][jsonArray.getJSONArray(0).length()];
		for(int i=1; i<rankings.length; i++) {
			JSONArray rowJson = jsonArray.getJSONArray(i);
			for (int j=0; j<rankings[i].length; j++) rankings[i][j] = rowJson.optString(j);
		}
	}

	public String[][] getRankings() { return rankings; }

	public String[] getTeamRanking (int team)
	{
		try {
			for (String[] row : rankings) {
				if(row[1] == null) {}
				else if (Integer.valueOf(row[1]) == team) return row;
			}
			return null;
		} catch (NullPointerException e) { return null; }
	}

	public String[] getTeamRanking (Team team)
	{
		return getTeamRanking(team.getTeamNum());
	}
}
