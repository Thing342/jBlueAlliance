package org.wesj.jbluealliance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wesj.jbluealliance.Match.MatchCompetitionLevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class BlueAllianceAPIClient
{
	private final String appId;
	private int currYear = new Date().getYear();

	public BlueAllianceAPIClient (String appId)
	{
		this.appId = appId;
	}

	public Team teamRequest (int number)
	{
		String url = "https://www.thebluealliance.com/api/v2/team/frc" + number,
				code = "team_"+number;

		JSONObject jsonTeam = new JSONObject(getHTML(url));
		return new Team(jsonTeam);
	}

	public Team[] teamListRequest(int pageNum)
	{
		String url = "http://www.thebluealliance.com/api/v2/teams/" + pageNum;
		JSONArray jsonTeams = new JSONArray(getHTML(url));
		Team[] teams = new Team[jsonTeams.length()];
		for (int i = 0; i < teams.length; i++) {
			teams[i] = new Team(jsonTeams.getJSONObject(i));
		}

		return teams;
	}

	public Event[] teamEventRequest (int team, int year)
	{
		String url = "https://www.thebluealliance.com/api/v2/team/frc" + team + "/" + year + "/events";
		JSONArray jsonEvents = new JSONArray(getHTML(url));
		Event[] events = new Event[jsonEvents.length()];
		for (int i = 0; i < events.length; i++)
			events[i] = new Event(jsonEvents.getJSONObject(i), false);

		return events;
	}

	public Event[] teamEventRequest (Team team, int year)
	{
		String url = "http://www.thebluealliance.com/api/v2/team/" + team.getKey() + "/" + year + "/events";
		JSONArray jsonEvents = new JSONArray(getHTML(url));
		Event[] events = new Event[jsonEvents.length()];
		for (int i = 0; i < events.length; i++)
			events[i] = new Event(jsonEvents.getJSONObject(i), true);

		return events;
	}

	public Award[] teamEventAwardRequest (int team, int year, String eventCode)
	{
		String url = "https://www.thebluealliance.com/api/v2/team/frc" + team + "/event/" + year + eventCode + "/awards";
		JSONArray jsonAwards = new JSONArray(getHTML(url));
		Award[] awards = new Award[jsonAwards.length()];

		for(int i=0; i<awards.length; i++) awards[i] = new Award(jsonAwards.getJSONObject(i));

		return awards;
	}

	public Match[] teamEventMatchRequest (int team, int year, String eventCode)
	{
		String url = "https://www.thebluealliance.com/api/v2/team/frc" + team + "/event/" + year + eventCode + "/matches";
		JSONArray jsonMatches = new JSONArray(getHTML(url));
		Match[] matches = new Match[jsonMatches.length()];

		for(int i=0; i<matches.length; i++) matches[i] = new Match(jsonMatches.getJSONObject(i));

		return matches;
	}

	public Match[] teamEventMatchRequest (Team team, Event event)
	{
		String url = "http://www.thebluealliance.com/api/v2/team/" + team.getKey() + "/event/" + event.getKey() + "/matches";
		JSONArray jsonMatches = new JSONArray(getHTML(url));
		Match[] matches = new Match[jsonMatches.length()];

		for(int i=0; i<matches.length; i++) matches[i] = new Match(jsonMatches.getJSONObject(i));

		return matches;
	}

	public int[] teamYearsParticipatedRequest (int team)
	{
		String url = "https://www.thebluealliance.com/api/v2/team/frc" + team + "/years_participated";
		JSONArray jsonYears = new JSONArray(getHTML(url));
		int[] years = new int[jsonYears.length()];
		for(int i=0; i<years.length; i++) years[i] = jsonYears.getInt(i);
		return years;
	}

	public int[] teamYearsParticipatedRequest (Team team)
	{
		String url = "http://www.thebluealliance.com/api/v2/team/" + team.getKey() + "/years_participated";
		JSONArray jsonYears = new JSONArray(getHTML(url));
		int[] years = new int[jsonYears.length()];
		for(int i=0; i<years.length; i++) years[i] = jsonYears.getInt(i);
		return years;
	}

	public TeamMedia[] teamMediaRequest (int team, int year)
	{
		String url = "https://www.thebluealliance.com/api/v2/team/frc" + team + "/" + year + "/media";
		JSONArray jsonMedia = new JSONArray(getHTML(url));
		TeamMedia[] media = new TeamMedia[jsonMedia.length()];
		for(int i=0; i<media.length; i++) media[i] = new TeamMedia(jsonMedia.getJSONObject(i));
		return media;
	}

	public TeamMedia[] teamMediaRequest (Team team, int year)
	{
		String url = "http://www.thebluealliance.com/api/v2/team/" + team.getKey() + "/" + year + "/media";
		JSONArray jsonMedia = new JSONArray(getHTML(url));
		TeamMedia[] media = new TeamMedia[jsonMedia.length()];
		for(int i=0; i<media.length; i++) media[i] = new TeamMedia(jsonMedia.getJSONObject(i));
		return media;
	}

	public Event[] eventListRequest() {
		return eventListRequest(currYear);
	}

	public Event[] eventListRequest (int year)
	{
		String url = "https://www.thebluealliance.com/api/v2/events/" + year;
		JSONArray jsonEvents = new JSONArray(getHTML(url));
		Event[] events = new Event[jsonEvents.length()];
		for(int i=0; i<events.length; i++) events[i] = new Event(jsonEvents.getJSONObject(i), true);
		return events;
	}

	public Event eventRequest (int year, String code)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code;
		return new Event(new JSONObject(getHTML(url)), true);
	}

	public Team[] eventTeamRequest (int year, String code)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code + "/teams";
		JSONArray jsonTeams = new JSONArray(getHTML(url));
		Team[] teams = new Team[jsonTeams.length()];
		for(int i=0; i<teams.length; i++) teams[i] = new Team(jsonTeams.getJSONObject(i));
		return teams;
	}

	public Match[] eventMatchRequest (int year, String code)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code + "/matches";
		JSONArray jsonMatches = new JSONArray(getHTML(url));
		Match[] matches = new Match[jsonMatches.length()];
		for(int i=0; i<matches.length; i++) matches[i] = new Match(jsonMatches.getJSONObject(i));
		Arrays.sort(matches, (m1, m2) -> m1.getMatchNumber() - m2.getMatchNumber());
		return matches;
	}

	public Match[] eventMatchRequest (Event event)
	{
		String url = "http://www.thebluealliance.com/api/v2/event/" + event.getKey() + "/matches";
		JSONArray jsonMatches = new JSONArray(getHTML(url));
		Match[] matches = new Match[jsonMatches.length()];
		for(int i=0; i<matches.length; i++) matches[i] = new Match(jsonMatches.getJSONObject(i));
		return matches;
	}

	public Team[] eventTeamRequest (Event e)
	{
		String url = "http://www.thebluealliance.com/api/v2/event/" + e.getKey() + "/teams";
		JSONArray jsonTeams = new JSONArray(getHTML(url));
		Team[] teams = new Team[jsonTeams.length()];
		for (int i = 0; i < teams.length; i++) teams[i] = new Team(jsonTeams.getJSONObject(i));
		return teams;
	}

	public List<Match> eventMatchRequest (int year, String code, MatchCompetitionLevel level)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code + "/matches";
		JSONArray jsonMatches = new JSONArray(getHTML(url));
		List<Match> matches = new ArrayList<Match>();
		for(int i=0; i<jsonMatches.length(); i++) {
			Match m = new Match(jsonMatches.getJSONObject(i));
			if(m.getLevel() == level) matches.add(m);
		}
		matches.sort((m1, m2) -> m1.getMatchNumber() - m2.getMatchNumber());
		return matches;
	}

	public EventStats eventStatsRequest (int year, String code)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code + "/stats";
		return new EventStats(new JSONObject(getHTML(url)));
	}

	public EventRankings eventRankingsRequest (int year, String code)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code + "/rankings";
		return new EventRankings(new JSONArray(getHTML(url)));
	}

	public EventRankings eventRankingsRequest (Event event)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + event.getKey() + "/rankings";
		return new EventRankings(new JSONArray(getHTML(url)));
	}
	
	public Award[] eventAwardRequest(int year, String code)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + year + code + "/awards";
		JSONArray jsonAwards = new JSONArray(getHTML(url));
		Award[] awards = new Award[jsonAwards.length()];
		for(int i=0; i<awards.length; i++) awards[i] = new Award(jsonAwards.getJSONObject(i));
		return awards;
	}

	public Award[] eventAwardRequest (Event event)
	{
		String url = "https://www.thebluealliance.com/api/v2/event/" + event.getKey() + "/awards";
		JSONArray jsonAwards = new JSONArray(getHTML(url));
		Award[] awards = new Award[jsonAwards.length()];
		for(int i=0; i<awards.length; i++) awards[i] = new Award(jsonAwards.getJSONObject(i));
		return awards;
	}

	public Match singleMatchRequest (int year, String code, MatchCompetitionLevel level, int levelNum, int setNumber)
	{
		String set = year + code + "_" + level.getTag() + levelNum + "m" + setNumber;
		String url = "https://www.thebluealliance.com/api/v2/match/" + set;
		return new Match(new JSONObject(getHTML(url)));
	}

	public Match singleMatchRequest (Event event, Match.MatchCompetitionLevel level, int levelNum, int setNumber)
	{
		String set = event.getKey() + "_" + level.getTag() + levelNum + "m" + setNumber;
		String url = "https://www.thebluealliance.com/api/v2/match/" + set;
		return new Match(new JSONObject(getHTML(url)));
	}

	public Match.MatchCompetitionLevel getEliminationLevel(Team team, Event event)
	{
		Match[] matches = teamEventMatchRequest(team, event);
		Match.MatchCompetitionLevel maxlevel = Match.MatchCompetitionLevel.QUALIFICATION;
		for (Match match : matches) {
			if(match.getLevel().ordinal() > maxlevel.ordinal()) maxlevel = match.getLevel();
		}
		return maxlevel;
	}

	public Event[] districtEventsRequest(District district) {
		return districtEventsRequest(district, currYear);
	}

	public Event[] districtEventsRequest(District district, int year) {
		String url = "https://www.thebluealliance.com/api/v2/district" + district.getAbbrev() +
				"/" + year + "/events";
		JSONArray jsonEvents = new JSONArray(getHTML(url));
		Event[] events = new Event[jsonEvents.length()];
		for(int i=0; i<events.length; i++) events[i] = new Event(jsonEvents.getJSONObject(i), true);
		return events;
	}

	public DistrictRanking[] districtRankingsRequest(District district) {
		return districtRankingsRequest(district, currYear);
	}

	public DistrictRanking[] districtRankingsRequest(District district, int year)
	{
		String url = "https://www.thebluealliance.com/api/v2/district/" + district.getAbbrev() +
				"/" + year + "/rankings";
		JSONArray jsonRankings = new JSONArray(getHTML(url));
		DistrictRanking[] rankings = new DistrictRanking[jsonRankings.length()];
		for (int i = 0; i < rankings.length; i++) {
			rankings[i] = new DistrictRanking(jsonRankings.getJSONObject(i));
		}

		return rankings;
	}

	public Team[] districtTeamsRequest (District district) {
		return districtTeamsRequest(district, currYear);
	}

	public Team[] districtTeamsRequest (District district, int year)
	{
		String url = "https://www.thebluealliance.com/api/v2/district/" + district.getAbbrev() +
				"/" + year + "/teams";
		JSONArray jsonTeams = new JSONArray(getHTML(url));
		Team[] teams = new Team[jsonTeams.length()];
		for(int i=0; i<teams.length; i++) teams[i] = new Team(jsonTeams.getJSONObject(i));
		return teams;
	}

	public String getHTML(String url)
	{
		URL oracle;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		StringBuilder result = new StringBuilder();
		try {
			oracle = new URL(url + "?X-TBA-App-Id=" + appId);
			conn = (HttpURLConnection) oracle.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result.append(line).append("\n");
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getAppId()
	{
		return appId;
	}
}
