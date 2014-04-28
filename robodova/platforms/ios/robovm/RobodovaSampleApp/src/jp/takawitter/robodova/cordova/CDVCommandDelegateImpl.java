package jp.takawitter.robodova.cordova;

import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Bridge;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public class CDVCommandDelegateImpl
extends NSObject
implements CDVCommandDelegate{
	static{
		ObjCRuntime.bind(CDVCommandDelegateImpl.class);
	}

	private static Selector s_sendPluginResult = Selector.register("sendPluginResult:callbackId:");
	@Bridge
	private static native void objc_sendPluginResult(
			CDVCommandDelegateImpl __self__, Selector __cmd__,
			CDVPluginResult result, String callbackId);
	public void sendPluginResult(
			CDVPluginResult result, String callbackId){
		objc_sendPluginResult(this, s_sendPluginResult, result, callbackId);
	}
}
