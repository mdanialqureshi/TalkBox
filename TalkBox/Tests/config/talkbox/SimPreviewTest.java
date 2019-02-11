package config.talkbox;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import config.talkbox.*;

class SimPreviewTest {

	private SimPreview sp;

	@BeforeEach
	void setUp() throws Exception {
		sp = new SimPreview();
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
}
