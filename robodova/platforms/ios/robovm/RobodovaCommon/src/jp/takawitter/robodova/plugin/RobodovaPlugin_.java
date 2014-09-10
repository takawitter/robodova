package jp.takawitter.robodova.plugin;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jp.takawitter.robodova.cordova.CDVPlugin;

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;

public class RobodovaPlugin_ extends CDVPlugin{
	protected NSArray<?> convertCollection(Collection<?> value){
		Collection<NSObject> ret = new ArrayList<NSObject>();
		for(Object v : value){
			ret.add(convert(v));
		}
		return new NSArray<NSObject>(ret);
	}

	protected NSArray<?> convertArray(Object value){
		Collection<NSObject> ret = new ArrayList<NSObject>();
		int n = Array.getLength(value);
		for(int i = 0; i < n; i++){
			ret.add(convert(Array.get(value, i)));
		}
		if(ret.size() == 0){
			return new NSArray<NSObject>();
		} else{
			return new NSArray<NSObject>(ret);
		}
	}

	protected NSDictionary<NSString, NSObject> convertMap(Map<?, ?> value){
		Map<NSString, NSObject> ret = new HashMap<NSString, NSObject>();
		for(Map.Entry<?, ?> e : value.entrySet()){
			ret.put(new NSString(e.getKey().toString()), convert(e.getValue()));
		}
		return new NSDictionary<NSString, NSObject>(ret);
	}

	protected NSDictionary<NSString, NSObject> convertObject(Object value){
		Map<NSString, NSObject> ret = new HashMap<NSString, NSObject>();
		for(java.lang.reflect.Method m : value.getClass().getMethods()){
			if(m.getReturnType().equals(void.class)) continue; 
			if(m.getParameterTypes().length > 0) continue; 
			if(m.getDeclaringClass().equals(Object.class)) continue;
			String name = m.getName();
			if(name.startsWith("get") && name.length() >= 4){
				name = name.substring(3, 4).toLowerCase() + name.substring(4);
			} else if(name.startsWith("is") && name.length() >= 3){
				name = name.substring(2, 3).toLowerCase() + name.substring(3);
			} else{
				continue;
			}
			try {
				ret.put(new NSString(name), convert(m.invoke(value)));
			} catch (IllegalArgumentException | IllegalAccessException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return new NSDictionary<NSString, NSObject>(ret);
	}

	protected NSObject convert(Object value){
		Class<?> clazz = value.getClass();
		if(clazz.isPrimitive()){
			if(clazz.equals(boolean.class)){
				return convert((Boolean)value);
			} else if(clazz.equals(byte.class)){
				return convert((Byte)value);
			} else if(clazz.equals(char.class)){
				return convert((Character)value);
			} else if(clazz.equals(short.class)){
				return convert((Short)value);
			} else if(clazz.equals(int.class)){
				return convert((Integer)value);
			} else if(clazz.equals(long.class)){
				return convert((Long)value);
			} else if(clazz.equals(float.class)){
				return convert((Float)value);
			} else if(clazz.equals(double.class)){
				return convert((Double)value);
			} else{
				throw new RuntimeException("Unknown primitive class: " + clazz.getName());
			}
		} else if(clazz.isArray()){
			return convertArray(value);
		} else if(value instanceof Boolean){
			return convert((Boolean)value);
		} else if(value instanceof Byte){
			return convert((Boolean)value);
		} else if(value instanceof Character){
			return convert((Character)value);
		} else if(value instanceof Number){
			return convert((Number)value);
		} else if(value instanceof String){
			return convert((String)value);
		} else if(value instanceof Collection){
			return convertCollection((Collection<?>)value);
		} else if(value instanceof Map){
			return convertMap((Map<?, ?>)value);
		} else{
			return convertObject(value);
		}
	}
	
	protected NSObject convert(Boolean value){
		return NSNumber.valueOf(value);
	}

	protected NSObject convert(Byte value){
		return NSNumber.valueOf(value);
	}

	protected NSObject convert(Character value){
		return NSNumber.valueOf(value);
	}

	protected NSObject convert(Number value){
		if(value instanceof Short){
			return NSNumber.valueOf((Short)value);
		} else if(value instanceof Integer){
			return NSNumber.valueOf((Integer)value);
		} else if(value instanceof Long){
			return NSNumber.valueOf((Long)value);
		} else if(value instanceof Float){
			return NSNumber.valueOf((Float)value);
		} else if(value instanceof Double){
			return NSNumber.valueOf((Double)value);
		} else{
			throw new RuntimeException("Unknown Number class: " + value.getClass().getName());
		}
	}

	protected NSString convert(String value){
		return new NSString(value);
	}
}
