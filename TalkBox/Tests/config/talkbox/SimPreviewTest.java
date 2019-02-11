package config.talkbox;
 //package import


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
	void test() throws InterruptedException {
		assertEquals(sp.buttons.size(), 20);
		sp.updateButtons(5);
		Thread.sleep(1000);
		assertEquals(sp.buttons.size(), 5);
		sp.updateButtons(25);
		Thread.sleep(1000);
		assertEquals(sp.buttons.size(), 25);
	}

}
