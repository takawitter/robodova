package jp.takawitter.robodova.cordova;

import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.objc.annotation.Property;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public class CDVPlugin extends NSObject{
	static {
		ObjCRuntime.bind(CDVPlugin.class);
		CDVCommandDelegateImpl.class.getName();
	}

	@Property(selector = "commandDelegate")
	public native CDVCommandDelegate getCommandDelegate();
}
