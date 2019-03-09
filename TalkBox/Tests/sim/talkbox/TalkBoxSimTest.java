package sim.talkbox;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.*;

import config.talkbox.TalkBoxConfig;

public class TalkBoxSimTest {

	private TalkBoxSim tbs;
	private ButtonPanel btnPnl;

	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		TalkBoxConfig g = new TalkBoxConfig();
		tbs = new TalkBoxSim(TalkBoxConfig.talkBoxDataPath.toString());
		btnPnl = (ButtonPanel) tbs.buttonPanel;

	}

	@AfterEach
	void tearDown() throws Exception {
		return;
	}

	@Test
	void updateButtonsNumber() {
//		btnPnl.updateButtons(20);
//		assertEquals(20, btnPnl.nButtons);
//
//		btnPnl.updateButtons(30);
//		assertEquals(30, btnPnl.nButtons);
	}

	@Test
	void testPlayingSound() throws InterruptedException {
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
		btnPnl.buttons.get(0).doClick();
		//assertEquals("", sperr.toString());
	}

}
