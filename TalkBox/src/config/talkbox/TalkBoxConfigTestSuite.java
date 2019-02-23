package config.talkbox;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@SelectPackages("config.talkbox")
@Suite.SuiteClasses({ PlayEditToggleTest.class,ProfilesPanelTest.class, RecorderTest.class, SimPreviewTest.class, TalkBoxConfigTest.class, TalkBoxSimTest.class})
public class TalkBoxConfigTestSuite {
	
}
