package jp.takawitter.robodova.sample.plugin;

import jp.takawitter.robodova.cordova.CDVCommandStatus;
import jp.takawitter.robodova.cordova.CDVInvokedUrlCommand;
import jp.takawitter.robodova.cordova.CDVPlugin;
import jp.takawitter.robodova.cordova.CDVPluginResult;

import org.robovm.objc.Selector;
import org.robovm.objc.annotation.BindSelector;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.rt.bro.annotation.Callback;

@CustomClass("RDVSamplePlugin")
public class SamplePlugin extends CDVPlugin{
	@Callback @BindSelector("hello:")
	public static void hello(SamplePlugin __self__, Selector __cmd__,
			CDVInvokedUrlCommand command){
		__self__.hello(command);
	}

	private void hello(CDVInvokedUrlCommand command){
		String arg = command.getArguments().get(0).toString();
		getCommandDelegate().sendPluginResult(
				CDVPluginResult.resultWithStatus(CDVCommandStatus.OK, arg),
				command.getCallbackId());
	}
}
