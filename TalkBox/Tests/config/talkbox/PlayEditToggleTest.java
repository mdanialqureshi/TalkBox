package config.talkbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PlayEditToggleTest {

	
	
	private TalkBoxConfig tbc;
	private PlayEditToggle pet;
	private SimPreview sp;
	private Recorder r;
	
	@BeforeEach
	void setUp() throws Exception {
		tbc = new TalkBoxConfig();
		sp = new SimPreview();
		r = new Recorder(sp);	
	}



	@Test
	void testAddingAudioToButtons() throws InterruptedException {
		r.toggle.toggleBtn.doClick();
		r.fileChooser.setCurrentDirectory((new File  
				(System.getProperty("user.home") + "/Desktop")));
		r.fileChooser.setSelectedFile(new File("test2"));
		Thread.sleep(500);
		r.recordBtn.doClick();		
		Thread.sleep(2000);
		r.recordBtn.doClick();
		sp.buttons.get(0).doClick();
		r.toggle.addToButton.doClick();	
		r.toggle.toggleBtn.doClick();
	}

	
	
	@Test
	void testChangingButtonLabel() throws AWTException {
		assertEquals("Button Label", r.toggle.buttonLbl.getText());
		r.toggle.toggleBtn.doClick();
		sp.buttons.get(1).doClick();
		r.toggle.buttonLbl.setText("hey");
		//button click not yet implemented
		assertEquals("2",sp.buttons.get(1).getText());
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	
	
	

}
