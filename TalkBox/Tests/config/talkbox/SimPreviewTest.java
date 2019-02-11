package config.talkbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimPreviewTest {

	private SimPreview sp;
	private TalkBoxConfig tbc;
	@BeforeEach
	void setUp() throws Exception {
		tbc = new TalkBoxConfig();
		sp = ((SimRecorderSplit) tbc.controlsProfileSplit.getLeftComponent()).simPreview;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUpdatingButtons() throws InterruptedException {
		assertEquals(20, sp.buttons.size());
		assertEquals(20, sp.buttonsPanel.getComponentCount());

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
		TalkBoxConfig.audFileNames[0][0] = "audio/hello.wav";
		sp.buttons.get(0).doClick();

		assertEquals("", sperr.toString());
	}

	@Test
	void testPlayingMissingSoundFile() throws InterruptedException {
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
		sp.buttons.get(0).doClick();
		assertNotEquals("", sperr.toString());
	}
}
