package config.talkbox;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.AWTException;
import java.awt.Robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfilesPanelTest {

	private ProfilesPanel pp;
	private TalkBoxConfig tbc;

	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		tbc = new TalkBoxConfig();

		pp = ((ProfilesPanel) tbc.controlsProfileSplit.getRightComponent());
	}

	@Test
	void test() throws AWTException {
		Robot robot = new Robot();
		assertEquals(1, pp.profilesListModel.size());
		assertEquals("profile-1", pp.getProfileName(0));

		pp.newProf.doClick();
		pp.newProf.doClick();
		pp.newProf.doClick();
		assertEquals(4, pp.profilesListModel.size());
		assertEquals("profile-1", pp.getProfileName(0));
		assertEquals("profile-2", pp.getProfileName(1));
		assertEquals("profile-3", pp.getProfileName(2));
		assertEquals("profile-4", pp.getProfileName(3));

		pp.delProf.doClick();
		assertEquals(3, pp.profilesListModel.size());
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> pp.getProfileName(3));

		// pp.profilesJList.grabFocus();
		// assertEquals("profile-3", pp.getSelectedProfileName());
		//
		// robot.keyPress(KeyEvent.VK_DOWN);
		// robot.keyRelease(KeyEvent.VK_DOWN);
		// robot.keyPress(KeyEvent.VK_DOWN);
		// robot.keyRelease(KeyEvent.VK_DOWN);
		// robot.keyPress(KeyEvent.VK_DOWN);
		// robot.keyRelease(KeyEvent.VK_DOWN);
		//
		// assertEquals("profile-3", pp.getSelectedProfileName());
		//
		// pp.loadProf.doClick();

	}

}
