package config.talkbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecorderTest {

	private Recorder recObj;
	private TalkBoxConfig configObj;
	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		configObj = new TalkBoxConfig();
		recObj = ((SimRecorderSplit) configObj.controlsProfileSplit.getLeftComponent()).recorder;
	
	}

	@Test
	void testInitialFields() {
		assertEquals(false, recObj.isRecording);
		assertEquals("Switch to edit mode to begin recording.",recObj.recordInfo.getText());		
	}

	
	@Test
	void testRecording() throws InterruptedException, AWTException {
	//	Robot robot = new Robot();
		recObj.toggle.toggleBtn.doClick();
		recObj.recordBtn.doClick();
//		robot.keyPress(KeyEvent.VK_ENTER);
//		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		recObj.recordBtn.doClick();
	//	String h = System.getProperty("user.home") + "/button-1.wav";
		assertEquals(null,recObj.filePath);
		
	}
	
	@Test
	void testButtons() throws InterruptedException {
		

//        // Simulate a mouse click
//        robot.mousePress(InputEvent.BUTTON1_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		Thread.sleep(3000);
//        robot.keyPress(KeyEvent.VK_ENTER);
//        Thread.sleep(1000);
//        robot.keyRelease(KeyEvent.VK_ENTER);
		recObj.txtNumberOfButtons.setText("30");
		recObj.updateNumberOfButtons.doClick();
		Thread.sleep(1000);
		recObj.launchSimulator.doClick();
		assertEquals(30,TalkBoxConfig.numAudButtons);
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		return;
	}
	
	
	

}
