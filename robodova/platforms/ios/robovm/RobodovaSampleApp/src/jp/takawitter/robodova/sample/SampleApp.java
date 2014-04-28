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
