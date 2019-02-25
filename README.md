# TalkBox
### TalkBox Group Project
TalkBox is a device that helps anybody, who is unable to talk, communicate. Each TalkBox has a number of buttons that the user can press to play pre-recorded audio files. Some of the buttons on the TalkBox may be used to load different sets of audio files.
![TalkBox](https://wiki.eecs.yorku.ca/course_archive/2018-19/W/2311/_media/talkbox.jpeg?cache=)


## Pre-requisites 
* System must have Java Runtime Environment installed

## Launch
To run the project, install it locally and run: 
```
java -jar TalkBox.jar
```
## Features 
![TalkBox Config App](https://i.imgur.com/VgQQDpI.png)

### Sim Preview Panel 
The Sim Preview Panel is used to display the state of the TalkBox device as it will be when launched. 
### Recording and Editing Panel 
The recording and editing panel is used to configure the state of the TalkBox device. The panel can be toggled between "Playback" and "Edit" modes using the Switch Modes toggle button. Configurable functionality includes updating the number of buttons, button labels, and to record and associate audio files to a button. 
### Profiles Panel 
The profiles panel is used to associate, save, and load audio sets to the TalkBox device. 

## Testing
Testing of the project is done using JUnit testing framework. Testing is used to test the implementation of the panels and their functionality. 
* PlayEditToggleTest
* ProfilesPanelTest
* RecorderTest
* SimPreviewTest
* TalkBoxConfigTest
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
