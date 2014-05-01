package jp.takawitter.robodova.annotation;

public @interface PluginClass {
	/**
	 * Specify objective-c class name that matches plugin-class definition in config.xml.
	 * @return objective-c class name.
	 */
	String value();
}
