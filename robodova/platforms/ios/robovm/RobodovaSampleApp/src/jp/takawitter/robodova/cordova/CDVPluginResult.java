/*
 * Copyright (c) 2014 Takao Nakaguchi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
