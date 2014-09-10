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
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import jp.takawitter.robodova.annotation.AfterInvocation;
import jp.takawitter.robodova.annotation.BeforeInvocation;
import jp.takawitter.robodova.annotation.PluginClass;
import jp.takawitter.robodova.annotation.PluginMethod;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({
	"jp.takawitter.robodova.annotation.PluginClass"
	})
public class RobodovaAnnotationProcessor extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for(TypeElement annotation : annotations){
			if(!annotation.getQualifiedName().toString().equals(PluginClass.class.getName())) continue;
			for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
				if(!(element instanceof TypeElement)) continue;
				TypeElement te = (TypeElement)element;
				try{
					generateSource(te, te.getAnnotation(PluginClass.class));
				} catch(Exception e){
					processingEnv.getMessager().printMessage(Kind.ERROR, e.toString(), te);
				}
			}
		}
		return true;
	}

	private void generateSource(TypeElement classElement, PluginClass pluginClass) {
		try{
			String packageName = getPackageElement(classElement).getQualifiedName().toString();
			String fqClassName = classElement.getQualifiedName() + "_";
			String className = classElement.getSimpleName() + "_";
			JavaFileObject file = processingEnv.getFiler().createSourceFile(fqClassName);
			PrintWriter w = new PrintWriter(file.openWriter(), true);
			try{
				IndentedPrinter p = new IndentedPrinter(w);
				p.println("package " + packageName + ";");
				p.println();
				p.println("import jp.takawitter.robodova.cordova.CDVCommandDelegate;");
				p.println("import jp.takawitter.robodova.cordova.CDVCommandStatus;");
				p.println("import jp.takawitter.robodova.cordova.CDVInvokedUrlCommand;");
				p.println("import jp.takawitter.robodova.cordova.CDVPluginResult;");
				p.println("import jp.takawitter.robodova.plugin.RobodovaPlugin_;");
				p.println("import org.robovm.objc.annotation.CustomClass;");
				p.println("import org.robovm.objc.annotation.Method;");
				p.println();
				p.println("/* This class is auto generated. Don't rewrite by yourself. */");
				p.println("@CustomClass(\"%s\")", pluginClass.value());
				p.println("public class %s extends RobodovaPlugin_{", className);

				writeListMethodsMethod(p, classElement);
				writeMethods(p, classElement);

				p.println();
				p.indent().println("private synchronized void init(){");
				p.indent().println(
						"if(instance == null) instance = new %s();",
						classElement.getSimpleName());
				p.unindent().println("}");
				p.unindent().println();
				p.indent().println(
						"private %s instance;",
						classElement.getSimpleName()).unindent();
				p.println("}");
			} finally{
				w.close();
			}
		} catch(Exception e){
			Messager messager = processingEnv.getMessager();
			messager.printMessage(Kind.ERROR, e.toString(), classElement);
		}
	}

	private void writeListMethodsMethod(IndentedPrinter p, TypeElement classElement){
		p.indent();
		p.println("@Method(selector=\"__listMethods__:\")");
		p.println("public void listMethods(CDVInvokedUrlCommand command){").indent();
		p.println("StringBuilder b = new StringBuilder();");
		p.println("Class<?> clazz = %s.class;", classElement.getSimpleName());
		p.println("while(!clazz.equals(Object.class)){").indent();
		p.println("for(java.lang.reflect.Method m : clazz.getDeclaredMethods()){").indent();
		p.println("if((m.getModifiers() & java.lang.reflect.Modifier.PUBLIC) == 0) continue;");
		p.println("if(m.getAnnotation(jp.takawitter.robodova.annotation.PluginMethod.class) != null){").indent();
		p.println("if(b.length() > 0){ b.append(\",\"); }");
		p.println("b.append(\"\\\"\").append(m.getName()).append(\"\\\"\");");
		p.unindent().println("}");
		p.unindent().println("}");
		p.println("clazz = clazz.getSuperclass();");
		p.unindent().println("}");
		p.println("String ret = \"[\" + b.toString() + \"]\";");
		p.println("getCommandDelegate().sendPluginResult(").indent();
		p.println("CDVPluginResult.resultWithStatus(CDVCommandStatus.OK, ").indent();
		p.println("ret),");
		p.println("command.getCallbackId());");
		p.unindent().unindent().unindent().println("}");
		p.unindent();
	}
	
	private void writeMethods(IndentedPrinter p, TypeElement classElement){
		String beforeMethod = null, afterMethod = null;
		for(Element e : classElement.getEnclosedElements()){
			if(!(e instanceof ExecutableElement)) continue;
			ExecutableElement ee = (ExecutableElement)e;
			BeforeInvocation bi = ee.getAnnotation(BeforeInvocation.class);
			if(bi != null){
				beforeMethod = ee.getSimpleName().toString();
			}
			AfterInvocation ai = ee.getAnnotation(AfterInvocation.class);
			if(ai != null){
				afterMethod = ee.getSimpleName().toString();
			}
		}
		for(Element e : classElement.getEnclosedElements()){
			if(!(e instanceof ExecutableElement)) continue;
			ExecutableElement ee = (ExecutableElement)e;
			p.indent();
			p.println("/* " + e + " */");
			PluginMethod pm = ee.getAnnotation(PluginMethod.class);
			if(pm == null){
				p.unindent().println();
				continue;
			}
			p.println("@Method");
			p.println(
					"public void %s(final CDVInvokedUrlCommand command){",
					e.getSimpleName()
					).indent();
			p.println("init();");
			p.println("final CDVCommandDelegate delegate = getCommandDelegate();");
			if(pm.async()){
				p.println("delegate.runInBackground(new Runnable() {").indent();
				p.println("@Override");
				p.println("public void run() {").indent();
			}
			if(beforeMethod != null){
				p.println("instance.%s(command, delegate);", beforeMethod);
			}
			p.println("try{").indent();
			boolean voidReturn = ee.getReturnType().getKind().equals(TypeKind.VOID);
			if(!voidReturn){
				p.println("delegate.sendPluginResult(").indent();
				p.println("CDVPluginResult.resultWithStatus(").indent();
				p.println("CDVCommandStatus.OK,");
			}
			String convMethod = getConvertToNSMethod(ee.getReturnType());
			if(convMethod != null){
				p.println("%s(instance.%s(", convMethod, ee.getSimpleName()).indent();
				writeArguments(p, ee, ee.getParameters());
				p.unindent().println("))");
			} else{
				p.println("instance.%s(", ee.getSimpleName()).indent();
				writeArguments(p, ee, ee.getParameters());
				p.unindent().println(")");
			}
			if(!voidReturn){
				p.unindent();
				p.println("),");
			} else{
				p.println(";");
				p.println("delegate.sendPluginResult(").indent();
				p.println("CDVPluginResult.resultWithStatus(").indent();
				p.println("CDVCommandStatus.OK");
				p.unindent().println("),");
			}
			p.println("command.getCallbackId());").unindent().unindent();
			p.println("} catch(Throwable e){").indent();
//			p.println("if(e instanceof RuntimeException){").indent();
//			p.println("throw (RuntimeException)e;");
//			p.unindent().println("}");
			p.println("e.printStackTrace();");
			p.println("delegate.sendPluginResult(").indent();
			p.println("CDVPluginResult.resultWithStatus(").indent();
			p.println("CDVCommandStatus.Error, e.toString().replaceAll(\"\\\"\", \"\\\\\\\"\").replaceAll(\"\\r\", \"\\\\r\").replaceAll(\"\\n\", \"\\\\n\")");
			p.unindent().println("),");
			p.println("command.getCallbackId());").unindent();
			p.unindent().println("}");
			if(afterMethod != null){
				p.println("instance.%s(command, delegate);", afterMethod);
			}
			if(pm.async()){
				p.unindent().println("}");
				p.unindent().println("});");
			}
			p.unindent().println("}");
			p.unindent().println();
		}
	}

	private void writeArguments(final IndentedPrinter p,
			ExecutableElement ee, List<? extends VariableElement> arguments){
		final int n = arguments.size();
		for(int i = 0; i < n; i++){
			VariableElement ve = arguments.get(i);
			TypeMirror tm = ve.asType();
			String m = "";
			if(tm.toString().equals("java.lang.String")){
				m = ".toString()";
			}
			p.println(
					"command.getArguments().get(" + i + ")" + m +
						((i != (n - 1)) ? "," : "")
						);
		}
	}

	private String getConvertToNSMethod(TypeMirror target){
		return target.accept(new TypeAdapter<String, Void>() {
			@Override
			public String visitArray(ArrayType t, Void p) {
				return "convertArray";
			}
			@Override
			public String visitPrimitive(PrimitiveType t, Void p) {
				switch(t.getKind()){
					case INT:
					case BOOLEAN:
					case DOUBLE:
						// no conversion needed
						return null;
					default:
						return "convert";
				}
			}
			@Override
			public String visitDeclared(DeclaredType t, Void p) {
				if(t.asElement() instanceof TypeElement){
					TypeElement te = (TypeElement)t.asElement();
					String name = te.getQualifiedName().toString();
					if(name.equals("java.lang.Object")){
						return "convertObject";
					} else if(name.equals("java.util.Collection")){
						return "convertCollection";
					} else if(name.equals("java.lang.String")){
						// no conversion needed
						return null; 
					} else{
						String m = te.getSuperclass().accept(this, null);
						if(m == null){
							for(TypeMirror tm : te.getInterfaces()){
								m = tm.accept(this, null);
								if(m != null) return m;
							}
						}
						return null;
					}
				} else{
					return "convert";
				}
			}
		}, null);
	}

	private PackageElement getPackageElement(Element element){
		while(!(element instanceof PackageElement)){
			element = element.getEnclosingElement();
		}
		return (PackageElement)element;
	}
}
