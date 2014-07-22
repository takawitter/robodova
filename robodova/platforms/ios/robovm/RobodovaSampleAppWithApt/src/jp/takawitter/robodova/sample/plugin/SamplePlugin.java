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

import java.util.Arrays;
import java.util.List;

import jp.takawitter.robodova.annotation.PluginClass;
import jp.takawitter.robodova.annotation.PluginMethod;

@PluginClass("RDVSamplePlugin")
public class SamplePlugin{
	@PluginMethod
	public String hello(String name){
		return "hello " + name;
	}

	@PluginMethod(async=false)
	public String syncHello(String name){
		return "hello " + name;
	}

	@PluginMethod
	public List<String> hellos(String name){
		return Arrays.asList(
				"hello " + name,
				"goodmorning " + name
				);
	}
}
