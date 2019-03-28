package config.talkbox;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TBCLogTest {
	
	TalkBoxConfig config;
	TBCLog log;

	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		config = new TalkBoxConfig();
		log = new TBCLog(TalkBoxConfig.talkBoxDataPath.toString());
		
	}

	@AfterEach
	void tearDown() throws Exception {
		log = null;
		}

	@Test
	void nextLogPrevLogtest() {
		
		String text = log.textArea.getText();
		log.btnPreviousLog.doClick();
		log.btnPreviousLog.doClick();
		String text2 = log.textArea.getText();
		
		assertFalse(text.equals(text2));

		log.btnNextLog.doClick();
		
		String text3 = log.textArea.getText();
		assertFalse(text2.equals(text3));
		assertTrue(text.equals(text3));
	
	}
	
	@Test
	void loadLogTest() throws InterruptedException {
		
		String textBefore = log.textArea.getText();
		log.readCurrentLog("current");
		log.btnPreviousLog.doClick();
		String textAfter = log.textArea.getText();
		//assertFalse(textAfter.equals(textBefore));
	}
	

}
