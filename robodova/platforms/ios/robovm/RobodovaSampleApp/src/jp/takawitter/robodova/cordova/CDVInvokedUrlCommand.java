package jp.takawitter.robodova.cordova;

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.objc.annotation.Property;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public class CDVInvokedUrlCommand extends NSObject{
	static {
		ObjCRuntime.bind(CDVInvokedUrlCommand.class);
	}

	@Property(selector="arguments")
	public native NSArray<?> getArguments();

	@Property(selector="callbackId")
	public native String getCallbackId();

	@Property(selector="className")
	public native String getClassName();

	@Property(selector="methodName")
	public native String getMethodName();
}
