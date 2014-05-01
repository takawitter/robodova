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
package jp.takawitter.robodova.apt;

import java.io.PrintWriter;

public class IndentedPrinter {
	public IndentedPrinter(PrintWriter w){
		this.w = w;
	}

	public IndentedPrinter indent(){
		indent++;
		return this;
	}

	public IndentedPrinter unindent(){
		indent--;
		return this;
	}

	public IndentedPrinter print(String format, Object... args){
		for(int i = 0; i < indent; i++){
			w.print('\t');
		}
		w.print(String.format(format, args));
		return this;
	}

	public IndentedPrinter println(String format, Object... args){
		for(int i = 0; i < indent; i++){
			w.print('\t');
		}
		w.println(String.format(format, args));
		return this;
	}

	public IndentedPrinter println(){
		printIndent();
		w.println();
		return this;
	}

	private void printIndent(){
		for(int i = 0; i < indent; i++){
			w.print('\t');
		}
	}

	private PrintWriter w;
	private int indent;
}
