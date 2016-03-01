package org.wesj.jbluealliance.test;

import org.junit.Test;
import org.wesj.jbluealliance.BlueAllianceAPIClient;
import org.wesj.jbluealliance.CachingBlueAllianceAPIClient;
import org.wesj.jbluealliance.Team;

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
}