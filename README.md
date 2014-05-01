robodova
========

Cordova bindings and annotation processing tool for RoboVM.

========
1. Setup

Robodova needs Eclipse with RoboVM plugin and Xcode.
* Eclipse - http://www.eclipse.org/downloads/
* RoboVM Plugin - http://download.robovm.org/eclipse/
* Xcode - download from Apple's site or AppStore

2. Clone repository and import projects

Clone this repository and import projects at:
* robodova/platforms/ios/robovm/
  * RobodovaAPT - Source code of Annotation Processing Tool for developing cordova plugin.
  * RobodovaCommon - Common sources including Cordova bindings such as AppDelegate or CDVPlugin.
  * RobodovaSampleApp - Sample application.
  * RobodovaSampleAppWithApt - Sample application using RobodovaAPT.

3. Run sample application

Before run, you must do setup and be sure Xcode ran once at your system.

* Right-click RobodovaSampleApp project and select Run As -> iOS Simulator App

The following window appeard if you successfully setup and run.

http://i.gyazo.com/ee6d4d4981530a7acb92be7d8ce3e8b1.png
