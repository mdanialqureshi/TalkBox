package config.talkbox;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sim.talkbox.ButtonPanel;
import sim.talkbox.TalkBoxSim;

class TalkBoxSimTest {
	
	private TalkBoxSim tbs; 
	private TalkBoxConfig tbc; 
	private ButtonPanel btnPnl;
	
	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		tbc = new TalkBoxConfig();
		tbs = new TalkBoxSim(tbc.talkBoxDataPath.toString());
		btnPnl = (ButtonPanel) tbs.buttonPanel;
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		return;
	}

	@Test 
	void updateButtonsNumber() {
		btnPnl.updateButtons(20);
		assertEquals(20, btnPnl.nButtons);
		
		btnPnl.updateButtons(30);
		assertEquals(30, btnPnl.nButtons);
	}

	@Test
	void testPlayingSound() throws InterruptedException {
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
		btnPnl.buttons.get(0).doClick();
		assertEquals("", sperr.toString());
	}
	

}
