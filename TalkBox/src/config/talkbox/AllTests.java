package config.talkbox;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sim.talkbox.TalkBoxSimTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PlayEditToggleTest.class,ProfilesPanelTest.class, 
	RecorderTest.class, SimPreviewTest.class, 
	TalkBoxConfigTest.class, TalkBoxSimTest.class}) 

class AllTests {
	
	
}
