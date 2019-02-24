package config.talkbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimPreviewTest {

	private SimPreview sp;
	private TalkBoxConfig tbc;
	
	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		tbc = new TalkBoxConfig();
		sp = ((SimRecorderSplit) tbc.controlsProfileSplit.getLeftComponent()).simPreview;
		sp.buttons.get(0).audioFile = new File("audio/hello.wav");
	}


	@Test
	void testUpdatingButtons() throws InterruptedException {
		assertEquals(30, sp.buttons.size());
		assertEquals(30, sp.buttonsPanel.getComponentCount());

		sp.updateButtons(5);
		assertEquals(5, sp.buttons.size());
		assertEquals(5, sp.buttonsPanel.getComponentCount());

		sp.updateButtons(25);
		assertEquals(25, sp.buttons.size());
		assertEquals(25, sp.buttonsPanel.getComponentCount());
	}

	@Test
	void testPlayingSound() throws InterruptedException {
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
		sp.buttons.get(0).doClick();
		assertEquals("", sperr.toString());
	}
	
	
	
	@Test
	void testErrorPlayingSound() throws Exception {
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
	//	sp.buttons.get(0).fileName = "doesNotExist";
		assertEquals("", sperr.toString());
	}

	@Test
	void testPlayingMissingSoundFile() throws InterruptedException {
		sp.buttons.get(0).audioFile = null;
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
		sp.buttons.get(0).doClick();
		assertNotEquals("", sperr.toString());
	}
	
	@AfterEach
	void tearDown() throws Exception {
		return;
	}
	
}
