package jp.takawitter.robodova.sample;

import jp.takawitter.robodova.cordova.AppDelegate;
import jp.takawitter.robodova.sample.plugin.SamplePlugin;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.objc.ObjCClass;

public class SampleApp {
	public static void main(String[] args) throws Exception{
		NSAutoreleasePool pool = new NSAutoreleasePool();
		try{
			ObjCClass.registerCustomClass(SamplePlugin.class);
			UIApplication.main(args, null, AppDelegate.class);
		} finally{
			pool.close();
		}
	}

}
