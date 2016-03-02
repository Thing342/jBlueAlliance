package org.wesj.jbluealliance;

import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DistrictRanking implements Comparable<DistrictRanking>
{
	private final JSONObject source;
	private final HashMap<String, EventPoints> eventPoints;

	public DistrictRanking(JSONObject source) {
		this.source = source;
		JSONObject events = source.getJSONObject("event_points");
		Set<String> keys = events.keySet();
		eventPoints = new HashMap<>();
		for(String event : keys) {
			eventPoints.put(event, new EventPoints(events.getJSONObject(event), event));
		}
	}

	public int getRank()
	{
		return source.getInt("rank");
	}

	public int getRookieBonus()
	{
		return source.getInt("rookie_bonus");
	}

	public int points()
	{
		return source.getInt("point_total");
	}

	public int getTeamNum()
	{
		String teamKey = source.getString("team_key");
		return new Integer(teamKey.replace("frc", ""));
	}

	public HashMap<String, EventPoints> getEventPoints()
	{
		//Shallow copy
		HashMap<String, EventPoints> copy = new HashMap<>();
		copy.putAll(eventPoints);
		return copy;
	}

	public EventPoints getEventResult(String eventKey)
	{
		return eventPoints.get(eventKey);
	}

	public Set<String> getEventsVisited()
	{
		return eventPoints.keySet();
	}

	@Override
	public int compareTo(DistrictRanking o)
	{
		return o.getRank() - getRank();
	}

	public class EventPoints {

		private final JSONObject source;
		private final String key;

		public EventPoints(JSONObject source, String eventKey)
		{
			this.source = source;
			this.key = eventKey;
		}

		/**
		 * @return District points awarded for being a captain, first, or second pick
		 */
		public int getAlliancePoints() {
			return source.getInt("alliance_points");
		}

		/**
		 * @return District points awarded for winning certain awards
		 */
		public int getAwardPoints() {
			return source.getInt("award_points");
		}

		/**
		 * @return District points awarded for progressing in the playoffs
		 */
		public int getElimPoints() {
			return source.getInt("elim_points");
		}

		/**
		 * @return True if the event was a District Championship and thus had its points multiplied
		 */
		public boolean isDistrictChampionship() {
			return source.getBoolean("district_cmp");
		}

		/**
		 * @return Total district points awarded to this team for this event
		 */
		public int getTotal() {
			return source.getInt("total");
		}

		/**
		 * @return District points awarded for qual seed
		 */
		public int getQualPoints() {
			return source.getInt("qual_points");
		}
	}
}
