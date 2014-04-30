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
package jp.takawitter.robodova.sample.plugin;

import jp.takawitter.robodova.cordova.CDVCommandStatus;
import jp.takawitter.robodova.cordova.CDVInvokedUrlCommand;
import jp.takawitter.robodova.cordova.CDVPlugin;
import jp.takawitter.robodova.cordova.CDVPluginResult;

import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.Method;

@CustomClass("RDVSamplePlugin")
public class SamplePlugin extends CDVPlugin{
	@Method
	public void hello(final CDVInvokedUrlCommand command){
		getCommandDelegate().runInBackground(new Runnable() {
			@Override
			public void run() {
				String arg = command.getArguments().get(0).toString();
				getCommandDelegate().sendPluginResult(
						CDVPluginResult.resultWithStatus(CDVCommandStatus.OK, arg),
						command.getCallbackId());
			}
		});
	}
}
