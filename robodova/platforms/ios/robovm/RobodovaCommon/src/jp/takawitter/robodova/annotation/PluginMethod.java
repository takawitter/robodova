package jp.takawitter.robodova.annotation;

public @interface PluginMethod {
	boolean async() default true;
}
