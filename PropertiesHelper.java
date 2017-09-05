/*
 * 
 * Helper class for properties, originally for the 
 */

package ec.app.basestation;

import java.util.*;

public class PropertiesHelper {
	public static int getIntProp(Properties prop, String name) {
		return getIntProp(prop, name, null);
	}
	public static int getIntProp(Properties prop, String name, String defaultName) {
		assert(prop != null);
		assert(name != null);
		String value = prop.getProperty(name);
		if(value == null) {
			assert(defaultName != null);
			value = prop.getProperty(defaultName);
		}
		assert(value != null);
		int intValue = 0;
		try {
			intValue = Integer.parseInt(value);
		}
		catch(NumberFormatException nfe) {
			throw new RuntimeException(name + " not int");
		}
		return intValue;
	}
	public static double getDoubleProp(Properties prop, String name) {
		return getIntProp(prop, name, null);
	}
	public static double getDoubleProp(Properties prop, String name, String defaultName) {
		assert(prop != null);
		assert(name != null);
		String value = prop.getProperty(name);
		if(value == null) {
			assert(defaultName != null);
			value = prop.getProperty(defaultName);
		}
		assert(value != null);
	    double doubleValue = 0;
		try {
			doubleValue = Double.parseDouble(value);
		}
		catch(NumberFormatException nfe) {
			throw new RuntimeException(name + " not double");
		}
		return doubleValue;
	}
	
}
