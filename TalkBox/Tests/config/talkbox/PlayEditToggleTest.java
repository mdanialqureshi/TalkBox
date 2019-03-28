package config.talkbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayEditToggleTest {

	private TalkBoxConfig tbc;
	private SimPreview sp;
	private Recorder r;

	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		tbc = new TalkBoxConfig();

		sp = SimRecorderSplit.simPreview;
		r = ((SimRecorderSplit) tbc.controlsProfileSplit.getLeftComponent()).recorder;
	}

	@Test
	void testChangingButtonLabel() throws AWTException, InterruptedException {
		r.toggle.switchModesButton.doClick();
		assertEquals("Button Label", r.toggle.updateButtonLabelTextField.getText());
		sp.buttons.get(0).doClick();
		r.toggle.updateButtonLabelTextField.setText("hey");
		r.toggle.updateButtonLabelButton.doClick();
		r.toggle.switchModesButton.doClick();
		// button click not yet implemented
		assertEquals("hey", sp.buttons.get(0).getText());
	}

	@Test
	void testAddingAudioToButtons() throws InterruptedException {
		Thread.sleep(500);
		r.toggle.switchModesButton.doClick();
		// r.fileChooser.setCurrentDirectory((new File
		// (System.getProperty("user.home") + "/Desktop")));
		Thread.sleep(500);
		sp.buttons.get(0).doClick();
		r.recordBtn.doClick();
		Thread.sleep(2000);
		r.recordBtn.doClick();
		final ByteArrayOutputStream sperr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(sperr));
		sp.buttons.get(0).doClick();
		//assertEquals("", sperr.toString());
		r.toggle.switchModesButton.doClick();

	}

	@AfterEach
	void tearDown() throws Exception {
		return;
	}

}
