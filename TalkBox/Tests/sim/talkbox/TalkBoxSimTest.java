package sim.talkbox;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import config.talkbox.Recorder;
import config.talkbox.SimRecorderSplit;
import config.talkbox.TalkBoxConfig;

class TalkBoxSimTest {

	private TalkBoxConfig tbc;
	private Recorder recObj;
	private ButtonPanel btnPnl;
	private TalkBoxSim tbs; 
	
	@BeforeEach
	void setUp() throws Exception {
		tbc = new TalkBoxConfig();
		recObj = ((SimRecorderSplit) tbc.controlsProfileSplit.getLeftComponent()).recorder;
	}
	
	@AfterEach
	void tearDown() throws Exception{
	}

	@Test
	void testUpdatingButtons() throws InterruptedException {
		recObj.txtNumberOfButtons.setText("30");
		recObj.updateNumberOfButtons.doClick();
		Thread.sleep(1000);
		recObj.launchSimulator.doClick();
		assertEquals(30,TalkBoxConfig.numAudButtons);
	}

}
