# TalkBox
### TalkBox Group Project
TalkBox is a device that helps anybody who is speech impaired, and has trouble communicating in general. Each TalkBox has a number of buttons that the user can press to play recorded audio files. Some of the buttons on the TalkBox may be used to load different sets of audio files, which are called profiles.
![TalkBox](https://wiki.eecs.yorku.ca/course_archive/2018-19/W/2311/_media/talkbox.jpeg?cache=)


## Pre-requisites 
* System must have Java Runtime Environment installed

## Launch
To run the project, install it locally and run the Configuration Application with : 
```
java -jar TalkBoxConfig.jar 

```
Run the Simulator Application with : 
```
java -jar TalkBoxSim.jar 

```
Run the TalkBox Configuration Log Application with : 
```
java -jar TBCLog.jar 

```
## Features 
![TalkBox Config App](https://imgur.com/ff8Mysp.png)

### Sim Preview Panel 
The Sim Preview Panel is used to display the state of the TalkBox device as it will be when launched. 
### Recording and Editing Panel 
The recording and editing panel is used to configure the state of the TalkBox device. The panel can be toggled between "Playback" and "Edit" modes using the Switch Modes toggle button. Configurable functionality includes updating the number of buttons, button labels, and to record and associate audio files to a button. 
### Profiles Panel 
The profiles panel is used to associate, save, and load audio sets to the TalkBox device. 
In this panel there is also a visualization of the logs from the TalkBox Simulator. 

## Testing
Testing of the project is done using JUnit testing framework. Testing is used to test the implementation of the panels and their functionality. 
* PlayEditToggleTest
* ProfilesPanelTest
* RecorderTest
* SimPreviewTest
* TalkBoxConfigTest
* TBCLogTest
* TalkBoxSimTest

## Technologies 
Project is created with: 
* Java 
* Eclipse

## Authors 
* Muhammad Danial Qureshi
* Jessie Leung
* Manish Bhasin 

## Acknowledgement 
The project is part of a Group Project for the course Winter 2019 EECS2311: Software Development Project at York University. 
