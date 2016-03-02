package org.wesj.jbluealliance.test;

import org.junit.Test;
import org.wesj.jbluealliance.*;
import static org.junit.Assert.*;

public class BlueAllianceAPIClientTest
{
	BlueAllianceAPIClient blue;

	@org.junit.Before
	public void setUp() throws Exception
	{
		blue = new CachingBlueAllianceAPIClient("wesj:jbluealliance_test:1", "./cache");
	}

	@Test
	public void testTeamRequest() {
		Team result = blue.teamRequest(2363);
		assertEquals(result.getName(), "Triple Helix");
		assertEquals(result.getLocation(), "Newport News, Virginia, USA");
	}

	@Test
	public void testTeamEventRequest() {
		Event[] result = blue.teamEventRequest(2363, 2015);
		assertEquals(result[0].getShort_name(), "Archimedes");
		assertEquals(result[1].getShort_name(), "Chesapeake");
		assertEquals(result[2].getShort_name(), "Virginia");
	}

	@Test
	public void testTeamEventRewardsRequest() {
		Award[] results = blue.teamEventAwardRequest(2363, 2015, "mdcp");
		assertEquals(results[0].getName(), "Regional Winners");
		assertEquals(results[0].getType(), Award.AwardType.WINNER);
		assertEquals(results[1].getName(), "Innovation in Control Award sponsored by Rockwell Automation");
		assertEquals(results[1].getType(), Award.AwardType.INNOVATION_IN_CONTROL);
	}

	@Test
	public void testEventRankingsRequest() {
		EventRankings results = blue.eventRankingsRequest(2015, "mdcp");
		assertTrue(results != null);
		assertEquals(results.getTeamRanking(2363)[0], "2");
		assertEquals(results.getTeamRanking(2363)[2], "82.55");
	}

	@Test
	public void testDistrictRankingsRequest() {
		DistrictRanking[] results = blue.districtRankingsRequest(District.MICHIGAN, 2015);
		DistrictRanking bedford = results[0];
		assertEquals(bedford.getTeamNum(), 1023);
		assertEquals(bedford.getEventsVisited().size(), 3);

		DistrictRanking.EventPoints micmp = bedford.getEventResult("2015micmp");
		assertEquals(micmp.getElimPoints(), 90);
		assertTrue(micmp.isDistrictChampionship());
	}
}