Robodova
========

Cordova bindings and annotation processing tool for RoboVM. Robodova is licensed under Apache License 2.0.

1. Setup
--------
  Robodova needs Eclipse with RoboVM plugin and Xcode.
  * Eclipse - http://www.eclipse.org/downloads/
  * RoboVM Plugin - http://download.robovm.org/eclipse/
  * Xcode - download from Apple's site or AppStore

2. Clone repository and import projects
--------
  Clone this repository and import projects at:
  * robodova/platforms/ios/robovm/
    * RobodovaAPT - Source code of Annotation Processing Tool for developing cordova plugin.
    * RobodovaCommon - Common sources including Cordova bindings such as AppDelegate or CDVPlugin.
    * RobodovaSampleApp - Sample application.
    * RobodovaSampleAppWithApt - Sample application using RobodovaAPT.

3. Run sample application
--------
  Before run, be sure Xcode ran once at your system.
  * Right-click RobodovaSampleApp project and select Run As -> iOS Simulator App
  
  The following window appeard if you successfully setup and run.
  <img src="http://i.gyazo.com/ee6d4d4981530a7acb92be7d8ce3e8b1.png">

4. Run sample application (with apt)
--------
  * Right-click RobodovaSampleAppWithApt/libs-apt/*.jardesc and select Create Jar.
  * Clear (rebuild) the project.
  * Right-click RobodovaSampleAppWithApt project and select Run As -> iOS Simulator App.
  
  The same window as RobodovaSampleApp appeard if you successfully setup and run.

With or without apt
========
Without apt, you have to write cordova dependent code when you develop a plugin.
```java
@CustomClass("RDVSamplePlugin")
public class SamplePlugin extends CDVPlugin{
	@Method
	public void hello(final CDVInvokedUrlCommand command){
		getCommandDelegate().runInBackground(new Runnable() {
			@Override
			public void run() {
				getCommandDelegate().sendPluginResult(
					CDVPluginResult.resultWithStatus(
						CDVCommandStatus.OK,
						"hello" + command.getArguments().get(0).toString()
						),
					command.getCallbackId());
			}
		});
	}
}
```

With apt, the code you have to write is lesser and more essential.
```java
@PluginClass("RDVSamplePlugin")
public class SamplePlugin extends CDVPlugin{
	@PluginMethod
	public String hello(String name){
		return "hello " + name;
	}
}
```

Some restrictions
========
* You have to register plugin class before start UIApplication.
```java
		NSAutoreleasePool pool = new NSAutoreleasePool();
		try{
			/* when using apt, the name of plugin class will be original class name plus "_" */
			ObjCClass.registerCustomClass(SamplePlugin_.class);
			UIApplication.main(args, null, AppDelegate.class);
		} finally{
			pool.close();
		}
```
