package org.slf4j;

public class LoggerFactory {

	public static Logger getLogger(String name) {
		return new GdxLogger(name);
	}

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clazz) {
		return getLogger(clazz.getSimpleName());
	}

}
