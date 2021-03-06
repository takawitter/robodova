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

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.annotation.Method;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)
@NativeClass
public class CDVPluginResult extends NSObject{
	static {
		ObjCRuntime.bind(CDVPluginResult.class);
	}

	@Method(selector="resultWithStatus:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status);

	@Method(selector="resultWithStatus:messageAsString:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, String message);

	@Method(selector="resultWithStatus:messageAsArray:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, NSArray<?> message);

	@Method(selector="resultWithStatus:messageAsInt:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, int message);

	@Method(selector="resultWithStatus:messageAsDouble:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, double message);

	@Method(selector="resultWithStatus:messageAsBool:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, boolean message);

	@Method(selector="resultWithStatus:messageAsDictionary:")
	public native static CDVPluginResult resultWithStatus(
			CDVCommandStatus status, NSDictionary<?, ?> message);
}
