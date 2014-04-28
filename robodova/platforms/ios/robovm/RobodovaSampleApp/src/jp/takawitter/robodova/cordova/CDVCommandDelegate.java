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
