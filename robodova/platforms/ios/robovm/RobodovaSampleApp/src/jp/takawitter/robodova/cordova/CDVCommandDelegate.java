package jp.takawitter.robodova.cordova;

import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSObjectProtocol;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.BindSelector;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.objc.annotation.NotImplemented;
import org.robovm.rt.bro.annotation.Callback;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public interface CDVCommandDelegate extends NSObjectProtocol{
	void sendPluginResult(CDVPluginResult result, String callbackId);

	public static class Adapter extends NSObject implements CDVCommandDelegate {
		@NotImplemented("sendPluginResult:callbackId:")
		public void sendPluginResult(CDVPluginResult result, String callbackId){
			throw new UnsupportedOperationException();
		}
	}

	static class Callbacks {
		@Callback @BindSelector("sendPluginResult:callbackId:")
		public static void sendPluginResult(
				CDVCommandDelegate __self__, Selector __cmd__,
				CDVPluginResult result, String callbackId){
			__self__.sendPluginResult(result, callbackId);
		}
	}
}
