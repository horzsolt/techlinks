package horzsolt.examples.htmlemailgen.common;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TransformObjectToMap {

	public static Map<String, String> transform(Object object)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, String> result = new HashMap<>();

		for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(object.getClass())
				.getPropertyDescriptors()) {

			String methodName = propertyDescriptor.getName();

			if (!methodName.endsWith("class")) {
				Logger.debug(methodName);
				
				Object rawValue = propertyDescriptor.getReadMethod().invoke(object);
				String value = rawValue == null ? "" : rawValue.toString();
				result.put(methodName, value);
			}
		}

		return result;

	}
}
