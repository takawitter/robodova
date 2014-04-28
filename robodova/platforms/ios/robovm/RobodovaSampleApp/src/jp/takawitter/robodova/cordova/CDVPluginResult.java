package jp.takawitter.robodova.cordova;

import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.ObjCClass;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Bridge;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public class CDVPluginResult extends NSObject{
	static {
		ObjCRuntime.bind(CDVPluginResult.class);
	}

	private static Selector s_resultWithStatus = Selector.register("resultWithStatus:messageAsString:");
	@Bridge
	private static native CDVPluginResult objc_resultWithStatus(
			ObjCClass __class__, Selector __cmd__,
			CDVCommandStatus status, String message);
	public static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, String message){
		return objc_resultWithStatus(
				ObjCClass.getByType(CDVPluginResult.class), s_resultWithStatus,
				status, message);
	}
}
