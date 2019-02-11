package config.talkbox;

import static org.junit.Assert.assertEquals;

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
		sp = new SimPreview();
		tbc = new TalkBoxConfig();
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
		final ByteArrayOutputStream myErr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(myErr));
		tbc.audFileNames[0][0] = "audio/hello.wav";
		sp.buttons.get(0).doClick();
		
		assertEquals("", myErr.toString());
	}
}
