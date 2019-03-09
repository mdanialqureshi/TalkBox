package config.talkbox;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TalkBoxConfigTest {

	TalkBoxConfig obj;
	SimRecorderSplit SRS;
	ProfilesPanel PP;
	Recorder R;
	SimPreview SP;

	@BeforeEach
	void setUp() throws Exception {
		TalkBoxConfig.testmode = true;
		obj = new TalkBoxConfig();
		TalkBoxConfig.main(new String[] {});

	}

	@Test
	void testSetNumAudioButtons() {
		obj.setNumAudButtons(50);
		assertEquals(50, TalkBoxConfig.numAudButtons);
		obj.setNumAudButtons(20);
	}

//	@Test
//	void testInitialFields() {
//		//assertEquals(1, TalkBoxConfig.numAudButtons);
//		//assertEquals("button-1.wav", TalkBoxConfig.audFileNames[0][0]);
//	//	assertEquals(null, TalkBoxConfig.buttonsMap.get(0));
//	}

	@Test
	void testJPaneSplits() {
		SRS = (SimRecorderSplit) obj.controlsProfileSplit.getLeftComponent();
		PP = (ProfilesPanel) obj.controlsProfileSplit.getRightComponent();
		R = (Recorder) SRS.getBottomComponent();
		SP = (SimPreview) SRS.getTopComponent();
	}

	@AfterEach
	void tearDown() throws Exception {
		return;
	}

}
