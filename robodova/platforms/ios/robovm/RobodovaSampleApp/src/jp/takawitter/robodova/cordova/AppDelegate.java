package jp.takawitter.robodova.cordova;

import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public class AppDelegate extends UIApplicationDelegateAdapter{
	static {
		ObjCRuntime.bind(AppDelegate.class);
	}
}
