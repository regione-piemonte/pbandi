/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandiutil.common;

import it.csi.pbandi.pbweberog.pbandiutil.common.util.json.JSONObject;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException; 
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.iterators.ArrayIterator;



/**
 * Classe per il trattamento dei bean puri (senza riferimenti al DB)
 */

@SuppressWarnings("serial")
public class BeanUtil {
	public static final String FLUX_FAKE_TOKEN_SEPARATOR = "\u00a3F\u00a3";// \u00a3 : � - pound
	public static final String FLUX_FAKE_ASSIGNMENT_OPERATOR = "\u00a3f\u00a3";
	public static final String ACTIVITY_TOKEN_SEPARATOR = "\u00a3A\u00a3";
	public static final String ACTIVITY_ASSIGNMENT_OPERATOR = "\u00a3a\u00a3";

	private static LoggerUtil logger = new LoggerUtil();

	private static final Set<Class<?>> IMMUTABLE_CLASSES = new HashSet<Class<?>>();
	static {
		IMMUTABLE_CLASSES.add(String.class);
		IMMUTABLE_CLASSES.add(Boolean.class);
		IMMUTABLE_CLASSES.add(BigDecimal.class);
		IMMUTABLE_CLASSES.add(Integer.class);
		IMMUTABLE_CLASSES.add(Long.class);
		IMMUTABLE_CLASSES.add(Double.class);
		IMMUTABLE_CLASSES.add(java.sql.Date.class);
		IMMUTABLE_CLASSES.add(java.util.Date.class);
		IMMUTABLE_CLASSES.add(java.lang.Class.class);
		IMMUTABLE_CLASSES.add(byte[].class);
	}

	private static final Set<Class<?>> NUMERIC_CLASSES = new HashSet<Class<?>>();
	static {
		NUMERIC_CLASSES.add(BigDecimal.class);
		NUMERIC_CLASSES.add(Integer.class);
		NUMERIC_CLASSES.add(Long.class);
		NUMERIC_CLASSES.add(Double.class);
	}

	private static final Set<Class<?>> INTEGER_CLASSES = new HashSet<Class<?>>();
	static {
		INTEGER_CLASSES.add(Integer.class);
		INTEGER_CLASSES.add(Long.class);
	}
	
	private static final Set<Class<?>> TIME_CLASSES = new HashSet<Class<?>>();
	static {
		TIME_CLASSES.add(java.util.Date.class);
		TIME_CLASSES.add(java.sql.Date.class);
		TIME_CLASSES.add(java.sql.Timestamp.class);
	}

	private static final int BEAN_TYPE_CACHE_CAPACITY = 5;

	private static final LinkedHashMap<Class<? extends Object>, Bean.Type> beanTypeCache = new LinkedHashMap<Class<? extends Object>, Bean.Type>(
			BEAN_TYPE_CACHE_CAPACITY, 0.9f, true) {
		@Override
		protected boolean removeEldestEntry(
				java.util.Map.Entry<Class<? extends Object>, Bean.Type> eldest) {
			return size() > BEAN_TYPE_CACHE_CAPACITY;
		}
	};

	public <T> T getPropertyValueByName(Object obj, String propName,
			Class<T> clazz) {
		try {
			return convert(clazz, getPropertyValueByName(obj, propName));
		} catch (Exception e) {
			logConvertException(obj, propName, clazz);
			return null;
		}
	}

	private static void logConvertException(Object obj, String propName,
			Object destination) {
		logger.debug("Eccezione in conversione:" + propName + " in "
				+ destination.toString() + "\nOggetto: " + obj.toString());
	}

	public <T> T getPropertyValueByName(Object obj, String[] propNames,
			Class<T> clazz) {
		T result = null;

		if (propNames != null) {
			for (String propName : propNames) {
				result = getPropertyValueByName(obj, propName, clazz);
				if (result != null) {
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Utilizzato per estrarre una lista di valori singoli da una lista di beans
	 * 
	 * @param list
	 *            la lista di beans
	 * @param propName
	 *            il nome della property da estrarre
	 * @param clazz
	 *            il tipo della property da estrarre
	 * @return una List di oggetti di tipo clazz valorizzati coi differenti
	 *         propName della list
	 */
	public <T> List<T> extractValues(Iterable<?> list, String propName,
			Class<T> clazz) {
		ArrayList<T> arrayList = new ArrayList<T>();
		for (Object obj : list) {
			arrayList.add(getPropertyValueByName(obj, propName, clazz));
		}

		return arrayList;
	}

	/**
	 * Utilizzato generare una lista di beans partendo da una lista di valori
	 * 
	 * @param list
	 *            la lista di oggetti che popoleranno i beans
	 * @param propName
	 *            il nome della property da valorizzare
	 * @param clazz
	 *            il tipo del bean da generare
	 * @return una List beans il cui attributo propName � valorizzato con gli
	 *         elementi di list
	 */
	public <T> List<T> beanify(Iterable<?> list, String propName, Class<T> clazz) {
		try {
			ArrayList<T> arrayList = new ArrayList<T>();
			for (Object obj : list) {
				T bean = clazz.newInstance();
				setPropertyValueByName(bean, propName, obj);
				arrayList.add(bean);
			}

			return arrayList;
		} catch (Exception e) {
			logConvertException(list, propName, clazz);
			return null;
		}
	}

	public static Object getPropertyValueByName(Object obj, String propName) {
		if (propName == null) {
			return null;
		}

		for (String prop : propName.split("\\.")) {
			if (isIndexed(prop)) {
				obj = getIndexedPropertyValueByName(obj, prop);
			} else {
				obj = getSimplePropertyValueByName(obj, prop);
			}
		}

		return obj;
	}

	private static Object getIndexedPropertyValueByName(Object obj,
			String propName) {
		Object result = null;
		if (obj != null) {
			Object array = getSimplePropertyValueByName(obj,
					getPropNameWithoutIndex(propName));
			if (array != null && array.getClass().isArray()) {
				try {
					int index = getPropertyIndex(propName);
					result = Array.get(array, index);
				} catch (NumberFormatException e) {
					logger.debug("Impossibile determinare l'indice della propriet� indicizzata \""
							+ propName + "\": " + e.getMessage());
				}
			}
		}
		return result;
	}

	private static String getPropNameWithoutIndex(String propName) {
		return propName.substring(0, propName.indexOf("["));
	}

	private static Object getSimplePropertyValueByName(Object obj,
			String propName) {
		Object result = null;
		if (obj != null) {
			result = getAsBean(obj).withProperty(propName).get();
		}
		return result;
	}

	public static void setPropertyValueByName(Object obj, String propName,
			Object value) throws Exception {
		setPropertyValueByName(obj, propName, value, true, false);
	}

	public static void setPropertyIfValueIsNullByName(Object obj,
			String propName, Object value) {
		try {
			setPropertyValueByName(obj, propName, value, true, true);
		} catch (Exception e) {
			logConvertException(obj, propName, value);
		}
	}

	public static void setPropertyValueByNameNoCopy(Object obj,
			String propName, Object value) throws Exception {
		setPropertyValueByName(obj, propName, value, false, false);
	}

	private static void setPropertyValueByName(Object obj, String propName,
			Object value, boolean doCopy, boolean doSetIfCurrentValueIsNull)
			throws Exception {
		Object prevObj = obj;
		Object currentObj = obj;
		String[] props = propName.split("\\.");

		boolean failed = false;
		for (int i = 0; i < props.length - 1 && !failed; i++) {
			boolean indexed = false;
			int index = 0;

			String prop = props[i];

			if (isIndexed(prop)) {
				indexed = true;
				index = getPropertyIndex(prop);
				prop = getPropNameWithoutIndex(prop);
			}

			if (prevObj != null) {
				Bean.Property prevObjProperty = getAsBean(prevObj)
						.withProperty(prop);
				if (prevObjProperty.isGettable()) {
					currentObj = prevObjProperty.get();

					if (currentObj == null) {
						Class<?> returnType = prevObjProperty.getType();
						if (returnType.isArray()) {
							currentObj = Array.newInstance(
									returnType.getComponentType(), index + 1);
						} else {
							currentObj = returnType.newInstance();
						}
						if (prevObjProperty.isSettable()) {
							prevObjProperty.set(currentObj);
						} else {
							failed = true;
						}
					}

					if (indexed) {
						Object arrayElement = null;
						Class<?> componentType = currentObj.getClass()
								.getComponentType();

						int length = Array.getLength(currentObj);
						if (index < length) {
							arrayElement = Array.get(currentObj, index);
						} else {
							Object array = Array.newInstance(componentType,
									index + 1);
							for (int j = 0; j < length; j++) {
								Array.set(array, j, Array.get(currentObj, j));
							}
							currentObj = array;
							if (prevObjProperty.isSettable()) {
								prevObjProperty.set(currentObj);
							} else {
								failed = true;
							}
						}

						if (arrayElement == null) {
							arrayElement = componentType.newInstance();
						}
						Array.set(currentObj, index, arrayElement);
						currentObj = arrayElement;
					}

					prevObj = currentObj;
				} else {
					failed = true;
				}
			}
		}

		if (!failed) {
			setSimplePropertyValueByName(currentObj, props[props.length - 1],
					value, doCopy, doSetIfCurrentValueIsNull);
		}
	}

	private static class Bean {
		public static class Type {
			private Class<? extends Object> beanClass;
			private Map<String, Property.Type> propertyTypesMap = new HashMap<String, Property.Type>() {
				public it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil.Bean.Property.Type get(
						Object key) {
					return super.get(((String) key).toLowerCase());
				};

				public it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil.Bean.Property.Type put(
						String key,
						it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil.Bean.Property.Type value) {
					return super.put(key.toLowerCase(), value);
				};
			};

			private PropertyDescriptor[] beanPropertyDescriptors;
			private int currentPropertyIndex = 0;
			private boolean readAllProperties = false;

			public Type(Class<? extends Object> clazz) {
				this.beanClass = clazz;

				try {
					beanPropertyDescriptors = Introspector.getBeanInfo(
							beanClass).getPropertyDescriptors();
				} catch (IntrospectionException e) {
					logger.debug("Class "
							+ beanClass
							+ " is not introspectable, it will be considered as a JavaBean with no properties.");
					beanPropertyDescriptors = new PropertyDescriptor[] {};
				}
			}

			public Property getPropertyFor(Object object, String propName) {
				Property.Type pt = propertyTypesMap.get(propName);
				if (pt == null) {
					pt = cachePropertyType(propName);
				}

				Property p = new Property();
				p.setPropertyType(pt);
				p.setObject(object);
				return p;
			}

			private Property.Type cachePropertyType(String propName) {
				Property.Type result = null;
				while (!readAllProperties) {
					readAllProperties = currentPropertyIndex >= beanPropertyDescriptors.length;
					if (!readAllProperties) {
						PropertyDescriptor propertyDescriptorSrc = beanPropertyDescriptors[currentPropertyIndex];
						currentPropertyIndex++;

						String currentPropName = propertyDescriptorSrc
								.getName();

						Property.Type pt = new Property.Type(currentPropName,
								propertyDescriptorSrc.getPropertyType(),
								propertyDescriptorSrc.getReadMethod(),
								propertyDescriptorSrc.getWriteMethod());

						propertyTypesMap.put(currentPropName, pt);

						if (currentPropName.equals(propName)) {
							result = pt;
							break;
						}
					} else {
						beanPropertyDescriptors = null;
					}
				}
				return result;
			}

			public Iterable<Property> getPropertiesFor(final Object object) {
				cachePropertyType(null);
				final Iterator<Bean.Property.Type> propertyTypes = propertyTypesMap
						.values().iterator();

				return new Iterable<Property>() {

					public Iterator<Property> iterator() {
						return new Iterator<Property>() {

							public void remove() {
								// pass
							}

							public Property next() {
								Property p = null;
								if (propertyTypes.hasNext()) {
									p = new Property();
									p.setPropertyType(propertyTypes.next());
									p.setObject(object);
								}
								return p;
							}

							public boolean hasNext() {
								return propertyTypes.hasNext();
							}
						};
					}
				};
			}

			public Iterable<Property.Type> getPropertyTypes() {
				cachePropertyType(null);
				return propertyTypesMap.values();
			}
		}

		public static class Property {
			public static class Type {
				private String name;
				private Class<?> type;
				private Method getMethod;
				private Method setMethod;

				public Type(String name, Class<?> type, Method getMethod,
						Method setMethod) {
					super();
					this.name = name;
					this.type = type;
					this.getMethod = getMethod;
					this.setMethod = setMethod;
				}

				public Class<?> getType() {
					return type;
				}

				public String getName() {
					return name;
				}

				public boolean isGettable() {
					return getMethod != null;
				}

				public boolean isSettable() {
					return setMethod != null;
				}

				public Object getFor(Object object) {
					Object result = null;
					if (object != null) {
						try {
							result = getMethod.invoke(object);
						} catch (Exception e) {
							logger.debug("Get failed, passing by (reason: "
									+ e.getMessage() + ")");
						}
					}
					return result;
				}

				public void setFor(Object object, Object value) {
					if (object != null) {
						try {
							setMethod.invoke(object, value);
						} catch (Exception e) {
							logger.debug("Set failed, passing by (reason: "+ e.getMessage() + ")");
						}
					}
				}

			}

			private Object object;
			private Type propertyType;

			public boolean isGettable() {
				boolean result;
				if (propertyType != null) {
					result = propertyType.isGettable();
				} else {
					result = false;
				}

				return result;
			}

			public boolean isSettable() {
				boolean result;
				if (propertyType != null) {
					result = propertyType.isSettable();
				} else {
					result = false;
				}

				return result;
			}

			public Object get() {
				Object result;
				if (propertyType != null) {
					result = propertyType.getFor(object);
				} else {
					result = null;
				}

				return result;
			}

			public Bean getAsBean() {
				return BeanUtil.getAsBean(get());
			}

			public void set(Object value) {
				if (propertyType != null) {
					propertyType.setFor(object, value);
				}
			}

			public Class<?> getType() {
				Class<?> result;
				if (propertyType != null) {
					result = propertyType.getType();
				} else {
					result = Object.class;
				}

				return result;
			}

			public String getName() {
				String result;
				if (propertyType != null) {
					result = propertyType.getName();
				} else {
					result = null;
				}

				return result;
			}

			public Object getObject() {
				return this.object;
			}

			public void setObject(Object obj) {
				this.object = obj;
			}

			public void setPropertyType(Type propertyType) {
				this.propertyType = propertyType;
			}

			public Type getPropertyType() {
				return propertyType;
			}
		}

		private Object object;
		private Type type;

		public Property withProperty(String propName) {
			return type.getPropertyFor(object, propName);
		}

		public Iterable<Property> getProperties() {
			return type.getPropertiesFor(object);
		}

		public Object getObject() {
			return this.object;
		}

		public void setObject(Object obj) {
			this.object = obj;
		}

		public void setType(Type beanType) {
			this.type = beanType;
		}

		public Type getType() {
			return type;
		}
	}

	private static Bean getAsBean(Object obj) {
		Bean b = null;
		if (obj != null) {
			b = new Bean();
			b.setObject(obj);
			b.setType(getAsBeanType(obj.getClass()));
		}

		return b;
	}

	private static Bean.Type getAsBeanType(Class<? extends Object> clazz) {
		Bean.Type bt = beanTypeCache.get(clazz);

		if (bt == null) {
			bt = new Bean.Type(clazz);
			beanTypeCache.put(clazz, bt);
		}
		return bt;
	}

	private static void setSimplePropertyValueByName(Object obj,
			String propName, Object value, boolean doCopy,
			boolean doSetIfCurrentValueIsNull) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException, Exception {
		boolean indexed = false;
		int index = 0;
		if (isIndexed(propName)) {
			indexed = true;
			index = getPropertyIndex(propName);
			propName = getPropNameWithoutIndex(propName);
		}

		Bean b = getAsBean(obj);
		Bean.Property p = b.withProperty(propName);

		if (indexed) {
			Object array = p.get();
			if (array != null) {
				if (doCopy) {
					Array.set(array, index,
							convert(array.getClass().getComponentType(), value));
				} else {
					Array.set(array, index, value);
				}
			}
		} else {
			if (p.isSettable()
					&& (!doSetIfCurrentValueIsNull || (p.isGettable() && p
							.get() == null))) {
				if (doCopy) {
					p.set(convert(p.getType(), value));
				} else {
					p.set(value);
				}
			}
		}
	}

	public static int getPropertyIndex(String propName) {
		int index = Integer.valueOf(propName.substring(
				propName.indexOf("[") + 1, propName.indexOf("]")));
		return index;
	}

	private static boolean isIndexed(String prop) {
		return prop.endsWith("]");
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(Class<T> destinationClass, Object value)
			throws Exception {
		T result = null;
		/*
		 * TODO se non usiamo ognl o qualche altro framework di conversione
		 * questo if diverr lunghissimo
		 * 
		 * XXX 16/7/2010 effettivamente sta diventando ingestibile, occorre
		 * trovare una soluzione (convert private come quella sulla data?)
		 */

		Class<?> sourceClass = null;

		if (value != null) {
			sourceClass = value.getClass();
		}

		if (destinationClass == null) {
			result = null;
		} else if (value == null
				|| ((destinationClass.equals(sourceClass) || destinationClass
						.equals(Object.class)) && isTypeImmutable(sourceClass))
				|| (isWrappedEqualsToPrimitive(destinationClass, sourceClass))) {
			result = (T) value;
		} else if (sourceClass.equals(java.lang.String.class)
				&& !destinationClass.equals(java.lang.String.class)
				&& (StringUtil.isEmpty((java.lang.String) value) || value
						.equals("null"))) {
			result = null;
		} else if (sourceClass.equals(destinationClass)
				&& sourceClass.equals(java.util.Date.class)) {
			result = (T) new java.util.Date(((java.util.Date) value).getTime());
		} else if (sourceClass.equals(destinationClass)
				&& sourceClass.equals(java.sql.Date.class)) {
			result = (T) new java.sql.Date(((java.sql.Date) value).getTime());
		} else if (sourceClass.equals(java.math.BigDecimal.class)) {
			java.math.BigDecimal bd = (java.math.BigDecimal) value;
			if (destinationClass.equals(Long.class)) {
				java.lang.Long in = new Long(bd.longValue());
				value = in;
			} else if (destinationClass.equals(Double.class)) {
				java.lang.Double in = new Double(bd.doubleValue());
				value = in;
			} else if (destinationClass.equals(String.class)) {
				java.lang.String in = bd.toString();
				value = in;
			} else if (destinationClass.equals(Integer.class)) {
				java.lang.Integer in = getIntegerRoundedHalfUp(bd);
				value = in;
			}
			result = (T) value;
		} else if (sourceClass.equals(java.lang.String.class)
				&& (destinationClass.equals(java.util.Date.class) || destinationClass
						.equals(java.sql.Date.class))) {
			java.lang.String source = (java.lang.String) value;
			if (DateUtil.isValidDate(source)) {
				java.util.Date in = DateUtil.getDate(source);
				value = in;
				if (destinationClass.equals(java.sql.Date.class)) {
					result = (T) convert(java.sql.Date.class,
							(java.util.Date) value);
				} else {
					result = (T) value;
				}
			} else {
				throw new Exception(
						"Invalid date format in conversion. Valid example: "
								+ DateUtil.getData());
			}
		} else if (sourceClass.equals(java.lang.String.class)
				&& isTypeNumeric(destinationClass)) {
			java.lang.String source = (java.lang.String) value;
			if (!NumberUtil.isNumber(source)) {
				throw new Exception("Cannot convert '" + source
						+ "' to a numeric format");
			}
			if (destinationClass.equals(Long.class)) {
				Long in;
				try {
					in = Long.parseLong(source);
				} catch (Exception e) {
					in = new Long(0);
				}
				value = in;
			} else if (destinationClass.equals(BigDecimal.class)) {
				BigDecimal in = new BigDecimal(source);
				value = in;
			} else if (destinationClass.equals(Double.class)) {
				Double in = NumberUtil.toNullableDoubleItalianFormat(source);
				value = in;
			} else if (destinationClass.equals(Integer.class)) {
				Integer in = getIntegerRoundedHalfUp(new BigDecimal(source));
				value = in;
			}
			result = (T) value;
		} else if (sourceClass.equals(java.sql.Date.class)
				&& destinationClass.equals(java.util.Date.class)) {
			java.sql.Date bd = (java.sql.Date) value;
			java.util.Date in = new java.util.Date(bd.getTime());
			value = in;
			result = (T) value;
		} else if (sourceClass.equals(Long.class)) {
			Long source = (Long) value;
			if (destinationClass.equals(Double.class)) {
				value = new Double(source);
			} else if (destinationClass.equals(BigDecimal.class)) {
				value = new BigDecimal(source);
			} else if (destinationClass.equals(String.class)) {
				value = source.toString();
			} else if (destinationClass.equals(Integer.class)) {
				value = getIntegerRoundedHalfUp(new BigDecimal(source));
			}
			result = (T) value;
		} else if (sourceClass.equals(java.lang.String.class)
				&& (destinationClass.equals(java.lang.Boolean.class) || destinationClass
						.equals(Boolean.TYPE))) {
			java.lang.String source = (java.lang.String) value;
			if (source.equals(Constants.FLAG_TRUE)) {
				java.lang.Boolean in = new Boolean(true);
				value = in;
			} else if (source.equals(Constants.FLAG_FALSE)) {
				java.lang.Boolean in = new Boolean(false);
				value = in;
			}
			result = (T) value;
		} else if (sourceClass.equals(Double.class)
				&& destinationClass.equals(String.class)) {
			result = (T) ((Double) value).toString();
		} else if (sourceClass.equals(Double.class)
				&& destinationClass.equals(BigDecimal.class)) {
			result = (T) new BigDecimal((Double) value);
		} else if (sourceClass.equals(Integer.class)) {
			Integer source = (Integer) value;
			if (destinationClass.equals(BigDecimal.class)) {
				value = new BigDecimal(source);
			} else if (destinationClass.equals(String.class)) {
				value = source.toString();
			} else if (destinationClass.equals(Double.class)) {
				value = new Double(source);
			} else if (destinationClass.equals(Long.class)) {
				value = new Long(source);
			}
			result = (T) value;
		} else if (sourceClass.equals(java.util.Date.class)
				&& destinationClass.equals(java.sql.Date.class)) {
			result = (T) convert(java.sql.Date.class, (java.util.Date) value);
		} else if ((sourceClass.equals(java.util.Date.class) || sourceClass
				.equals(java.sql.Date.class))
				&& destinationClass.equals(String.class)) {
			java.util.Date bd = (java.util.Date) value;
			String in = DateUtil.getDate(bd);
			value = in;
			result = (T) value;
		} else if ((sourceClass.equals(java.lang.Boolean.class) || sourceClass
				.equals(Boolean.TYPE))
				&& destinationClass.equals(java.lang.String.class)) {
			java.lang.Boolean source = (java.lang.Boolean) value;
			result = (T) ((source) ? Constants.FLAG_TRUE : Constants.FLAG_FALSE);
		} else if (sourceClass.equals(JSONObject.class)) {
			result = destinationClass.newInstance();

			JSONObject jsonObject = (JSONObject) value;
			Iterator<?> keys = jsonObject.keys();

			while (keys.hasNext()) {
				String key = keys.next().toString();
				setPropertyValueByName(result, key, jsonObject.get(key));
			}
		} else {
			try {
				boolean supposeIsBean = false;
				if (destinationClass.isArray()) {
					Class<?> componentType = destinationClass
							.getComponentType();
					if (sourceClass.isArray()) {
						result = (T) Array.newInstance(componentType,
								Array.getLength(value));
						for (int i = 0; i < Array.getLength(value); ++i) {
							Array.set(
									result,
									i,
									convert(result.getClass()
											.getComponentType(), Array.get(
											value, i)));
						}
					} else if (arrayContains(sourceClass.getInterfaces(),
							List.class)
							|| arrayContains(sourceClass.getInterfaces(),
									Set.class)) {
						ArrayList list = new ArrayList();
						Iterator iterator = ((Iterable) value).iterator();
						while (iterator.hasNext()) {
							list.add(convert(componentType, iterator.next()));
						}
						result = (T) Array.newInstance(componentType,
								list.size());
						int i = 0;
						for (Object object : list) {
							Array.set(result, i, object);
							i++;
						}
					} else {
						supposeIsBean = true;
					}
				} else if (arrayContains(destinationClass.getInterfaces(),
						List.class)) {
					if (sourceClass.isArray()) {
						// TODO IMPOSSIBILE le liste non hanno modo di fornire
						// l'informazione sul tipo che dovrebbero contenere
						// for (int i = 0; i < Array.getLength(value); ++i) {
						// Array.set(result, i, (T) result.getClass()
						// .getComponentType().newInstance());
						// // TODO ma non dovremmo chiamare il convert che �
						// // pi� generale?
						// valueCopy(Array.get(value, i), Array.get(result, i));
						//
						// }
					} else {
						supposeIsBean = true;
					}
				} else {
					supposeIsBean = true;
				}
				if (supposeIsBean) {
					result = destinationClass.newInstance();
					valueCopy(value, result);
				}
			} catch (Exception e) {
				logger.debug("Trasformazione impossibile perch� "
						+ destinationClass.toString() + " non � istanziabile");
				throw new Exception("Unhandled type conversion: source class "
						+ sourceClass + " dest class " + destinationClass, e);
			}
		}

		return result;
	}

	private static java.lang.Integer getIntegerRoundedHalfUp(BigDecimal bd) {
		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		java.lang.Integer in = new Integer(bd.intValueExact());
		return in;
	}

	private static java.sql.Date convert(Class<java.sql.Date> clazz,
			java.util.Date value) {
		java.util.Date bd = (java.util.Date) value;
		java.sql.Date in = new java.sql.Date(bd.getTime());
		return in;
	}

	private static <T> boolean arrayContains(T[] array, T obj) {
		boolean found = false;
		if (obj != null) {
			for (T t : array) {
				found = obj.equals(t);
				if (found) {
					break;
				}
			}
		}
		return found;
	}

	private static <T> boolean isWrappedEqualsToPrimitive(
			Class<T> destinationClass, Class<?> sourceClass) {
		return destinationClass.isPrimitive()
				&& ((sourceClass.equals(Boolean.class) && destinationClass
						.equals(Boolean.TYPE))
						|| (sourceClass.equals(Integer.class) && destinationClass
								.equals(Integer.TYPE))
						|| (sourceClass.equals(Double.class) && destinationClass
								.equals(Double.TYPE)) || (sourceClass
						.equals(Long.class) && destinationClass
						.equals(Long.TYPE)));
	}

	private static boolean isTypeImmutable(Class<?> sourceClass) {
		return sourceClass.isPrimitive()
				|| IMMUTABLE_CLASSES.contains(sourceClass);
	}

	public static boolean isTypeNumeric(Class<?> sourceClass) {
		return NUMERIC_CLASSES.contains(sourceClass);
	}
	
	public static boolean isTypeInteger(Class<?> sourceClass) {
		return INTEGER_CLASSES.contains(sourceClass);
	}

	public static boolean isTypeTime(Class<?> sourceClass) {
		return TIME_CLASSES.contains(sourceClass);
	}

	/**
	 * Usa l'introspezione per copiare le property comuni di due bean
	 * preservando eventuali valori presenti sulla destinazione.
	 * 
	 * @param objSrc
	 * @param objDest
	 * @throws Exception
	 */
	public static void valueCopy(Object objSrc, Object objDest) {
		valueCopy(objSrc, objDest, true);
	}

	private static void valueCopy(Object objSrc, Object objDest,
			boolean ignoreNulls) {
		if (objSrc != null) {
			for (Bean.Property p : getAsBean(objSrc).getProperties()) {
				if (p.isGettable()) {
					Object value = p.get();
					if (!ignoreNulls || value != null) {
						try {
							setPropertyValueByName(objDest, p.getName(), value);
						} catch (Exception e) {
							logger.debug("Impossibile effettuare la conversione, valore non impostato per "
									+ p.getName()
									+ ", ragione: "
									+ e.getMessage());
						}
					}
				}
			}
		}
	}

	/**
	 * Usa l'introspezione per copiare le property comuni di due bean
	 * preservando eventuali valori presenti sulla destinazione - questa
	 * versione va in profondit� nei bean complessi
	 * 
	 * @param objSrc
	 * @param objDest
	 * @throws Exception
	 */
	public <T extends Object> void deepValueCopy(T objSrc, T objDest) {
		for (String propName : enumerateProperties(objSrc)) {
			try {
				Object value = getPropertyValueByName(objSrc, propName);
				if (value != null) {
					setPropertyValueByName(objDest, propName, value, true,
							false);
				}
			} catch (Exception e) {
				logger.debug("Impossibile effettuare la conversione, valore non impostato per "
						+ propName + ", ragione: " + e.getMessage());
			}
		}
	}

	/**
	 * Vedi valueCopy di object
	 * 
	 * @param arrayObjSrc
	 * @param arrayObjDest
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public void valueCopy(Object[] arrayObjSrc, Object[] arrayObjDest) {
		try {
			if (arrayObjSrc != null && arrayObjDest != null
					&& arrayObjSrc.length <= arrayObjDest.length) {
				for (int c = 0; c < arrayObjSrc.length; c++) {
					if (arrayObjDest[c] == null) {
						arrayObjDest[c] = arrayObjDest.getClass()
								.getComponentType().newInstance();
					}
					valueCopy(arrayObjSrc[c], arrayObjDest[c]);
				}
			}
		} catch (Exception e) {
			// FIXME non mi sembra prorio corretta questa gestione...
			logger.debug(e.getMessage());
		}
	}

	public <T> T transform(Object objSrc, Class<T> c) {
		T result = null;
		try {
			result = convert(c, objSrc);
		} catch (Exception e) {
			// FIXME sarebbe bello che venisse lanciata fuori
			logConvertException(objSrc, "", c);
		}
		return result;
	}

	public <T> T transform(Map<String, ?> aMap, Class<T> c) {
		return transform(aMap, "", c);
	}

	public <T> T transform(Map<String, ?> aMap, String prefix, Class<T> c) {
		T result = null;
		try {
			result = c.newInstance();
			for (String propertyName : aMap.keySet()) {
				if (propertyName.startsWith(prefix)) {
					String propertyNameWithoutPrefix = propertyName
							.substring(prefix.length());
					setPropertyValueByName(result, propertyNameWithoutPrefix,
							aMap.get(propertyName));
				}
			}
		} catch (Exception e) {
			// ritorno null
			logConvertException(aMap, prefix, c);
		}
		return result;
	}

	public <T> T transform(Object objSrc, Class<T> c,
			Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		if (c == null || objSrc == null) {
			return null;
		}
		T a = null;
		try {
			a = c.newInstance();
			valueCopy(objSrc, a, mapObjSrcProperiesToObjDestProperies);
		} catch (Exception e) {
			logger.debug("Trasformazione impossibile perch� " + c.toString()
					+ " non � istanziabile");
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] transform(Object[] arrayObjSrc, Class<T> c) {
		T[] result = null;
		try {
			result = (T[]) convert(Array.newInstance(c, 0).getClass(),
					arrayObjSrc);
		} catch (Exception e) {
			logger.debug("Impossibile trasformare l'array: " + e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> transformToArrayList(Object[] arrayObjSrc,
			Class<T> c) {
		T[] t = transform(arrayObjSrc, c);
		ArrayList<T> result;
		if (t != null) {
			result = new ArrayList(Arrays.asList(t));
		} else {
			result = new ArrayList<T>();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] transform(Object[] arrayObjSrc, Class<T> c,
			Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		if (arrayObjSrc == null) {
			return null;
		}
		T[] a = (T[]) Array.newInstance(c, arrayObjSrc.length);
		valueCopy(arrayObjSrc, a, mapObjSrcProperiesToObjDestProperies);
		return a;
	}

	public <T, E> T[] transform(List<E> listObjSrc, Class<T> c) {
		if (listObjSrc == null) {
			return null;
		} else {
			return transform(listObjSrc.toArray(), c);
		}
	}

	public <T, E> List<T> transformList(List<E> listObjSrc, Class<T> c) {
		if (listObjSrc == null) {
			return null;
		}
		ArrayList<T> result = null;
		try {
			result = new ArrayList<T>();
			while (result.size() < listObjSrc.size()) {
				result.add(c.newInstance());
			}
			valueCopy(listObjSrc, result);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return result;
	}

	public <T, E> List<T> transformList(List<E> listObjSrc, Class<T> c,
			Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		if (listObjSrc == null) {
			return null;
		}
		ArrayList<T> result = null;
		try {
			result = new ArrayList<T>();
			while (result.size() < listObjSrc.size()) {
				result.add(c.newInstance());
			}
			valueCopy(listObjSrc, result, mapObjSrcProperiesToObjDestProperies);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return result;
	}

	public <T> ArrayList<T> transformToArrayList(final Object[] arrayObjSrc,
			Class<T> c, Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		if (arrayObjSrc == null) {
			return null;
		}
		ArrayList<T> result = null;
		try {
			result = new ArrayList<T>();
			while (result.size() < arrayObjSrc.length) {
				result.add(c.newInstance());
			}
			valueCopy(new Iterable<Object>() {

				@SuppressWarnings("unchecked")
				public Iterator<Object> iterator() {
					return (Iterator<Object>) createIterator(arrayObjSrc);
				}

			}, result, mapObjSrcProperiesToObjDestProperies);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return result;
	}

	public <T, E> T[] transform(List<E> listObjSrc, Class<T> c,
			Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		if (listObjSrc == null) {
			return null;
		}
		return transform(listObjSrc.toArray(), c,
				mapObjSrcProperiesToObjDestProperies);
	}

	public interface Transformation<E, T> {
		public void transform(E objSrc, T objDest) throws Exception;
	}

	public class MapTransformation<E, T> implements Transformation<E, T> {
		private Map<String, String> map;

		public MapTransformation(Map<String, String> map) {
			this.map = map;
		}

		public void transform(E objSrc, T objDest) {
			valueCopy(objSrc, objDest, map);
		};
	}

	public class TransformationSequence<E, T> implements Transformation<E, T> {
		List<Transformation<E, T>> transforms = new ArrayList<Transformation<E, T>>();

		public TransformationSequence(Transformation<E, T> t1,
				Transformation<E, T> t2, Transformation<E, T>... tother) {
			transforms.add(t1);
			transforms.add(t2);
			transforms.addAll(Arrays.asList(tother));
		}

		public void transform(E objSrc, T objDest) throws Exception {
			for (Transformation<E, T> t : transforms) {
				t.transform(objSrc, objDest);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <E, T> T[] transform(Iterable<E> listObjSrc, Class<T> c,
			Transformation<E, T> t) {
		ArrayList<T> newList = new ArrayList<T>();
		for (E e : listObjSrc) {
			T objDest;
			try {
				objDest = c.newInstance();
				t.transform(e, objDest);
				newList.add(objDest);
			} catch (Exception ex) {
				logger.debug("Impossibile istanziare " + c + ", ragione: "
						+ ex.getMessage());
			}
		}

		return newList.toArray((T[]) Array.newInstance(c, newList.size()));
	}

	public interface Operation<E> {
		public void operateOn(E e);
	}

	public <T extends Iterable<E>, E> T foreach(T i, Operation<E> o) {
		for (E e : i) {
			o.operateOn(e);
		}
		return i;
	}

	public void valueCopy(Object[] arrayObjSrc, Object[] arrayObjDest,
			Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		try {
			for (int i = 0; i < arrayObjDest.length; i++) {
				if (arrayObjDest[i] == null) {
					arrayObjDest[i] = arrayObjDest.getClass()
							.getComponentType().newInstance();
				}
				valueCopy(arrayObjSrc[i], arrayObjDest[i],
						mapObjSrcProperiesToObjDestProperies);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}

	public void valueCopy(Iterable<? extends Object> lsrc,
			Iterable<? extends Object> ldst,
			Map<String, String> mapObjSrcProperiesToObjDestProperies)
			throws Exception {
		if (lsrc != null && ldst != null) {
			Iterator<? extends Object> srcIterator = lsrc.iterator();
			Iterator<? extends Object> dstIterator = ldst.iterator();

			while (srcIterator.hasNext() && dstIterator.hasNext()) {
				valueCopy(srcIterator.next(), dstIterator.next(),
						mapObjSrcProperiesToObjDestProperies);
			}
		}
	}

	// FIXME rimuovere dupicazione
	public void valueCopy(Iterable<? extends Object> lsrc,
			Iterable<? extends Object> ldst) throws Exception {
		if (lsrc != null && ldst != null) {
			Iterator<? extends Object> srcIterator = lsrc.iterator();
			Iterator<? extends Object> dstIterator = ldst.iterator();

			while (srcIterator.hasNext() && dstIterator.hasNext()) {
				valueCopy(srcIterator.next(), dstIterator.next());
			}
		}
	}

	// TODO rinominare, copia anche i null, mentre gli altri value copy no
	public void valueCopy(Object objSrc, Object objDest,
			Map<String, String> mapObjSrcProperiesToObjDestProperies) {
		try {
			for (String propertyNameSource : mapObjSrcProperiesToObjDestProperies
					.keySet()) {
				setPropertyValueByName(objDest,
						mapObjSrcProperiesToObjDestProperies
								.get(propertyNameSource),
						getPropertyValueByName(objSrc, propertyNameSource));
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}

	public static void valueCopyNulls(Object objSrc, Object objDest) {
		valueCopy(objSrc, objDest, false);
	}

	// FIXME non si chiama valueCopy, ma ne ha la stessa semantica, andrebbe
	// rinominato al posto del valueCopy con firma analoga
	public void copyNotNullValues(Object objSrc, Object objDest,
			Map<String, String> mapObjSrcProperiesToObjDestProperies)
			throws Exception {
		for (String propertyNameSource : mapObjSrcProperiesToObjDestProperies
				.keySet()) {
			Object value = getPropertyValueByName(objSrc, propertyNameSource);

			if (value != null) {
				setPropertyValueByName(objDest,
						mapObjSrcProperiesToObjDestProperies
								.get(propertyNameSource), value);
			}
		}
	}

	public static Set<String> getProperties(Object object)
			throws IntrospectionException {
		return getProperties(object.getClass());
	}

	public static Set<String> getProperties(Class<?> clazz)
			throws IntrospectionException {
		return getPropertiesAndTypes(clazz).keySet();
	}

	public static Map<String, Class<?>> getPropertiesAndTypes(Class<?> clazz)
			throws IntrospectionException {
		Map<String, Class<?>> result = new HashMap<String, Class<?>>();
		for (Bean.Property.Type pt : getAsBeanType(clazz).getPropertyTypes()) {
			if (pt.isGettable()) {
				result.put(pt.getName(), pt.getType());
			}
		}

		return result;
	}

	public <T> Map<?, T> index(List<T> list, String propertyName)
			throws Exception {
		HashMap<Object, T> indexMap = new HashMap<Object, T>();

		for (T t : list) {
			indexMap.put(getPropertyValueByName(t, propertyName), t);
		}

		return indexMap;
	}

	public <T> Map<?, List<T>> indexByPropertyAsList(Iterable<T> iterable,
			String propertyName) throws Exception {
		HashMap<Object, List<T>> indexMap = new HashMap<Object, List<T>>();

		for (T t : iterable) {
			Object value = getPropertyValueByName(t, propertyName);
			List<T> list = indexMap.get(value);
			if (list == null) {
				list = new ArrayList<T>();
			}
			list.add(t);
			indexMap.put(value, list);
		}

		return indexMap;
	}

	public void recursiveValueCopy(String childrenPropertyName,
			Object sourceObj, Object destinationObj,
			Map<String, String> mapObjSrcProperiesToObjDestProperies)
			throws Exception {
		valueCopy(sourceObj, destinationObj,
				mapObjSrcProperiesToObjDestProperies);
		Iterator<?> sourceIterator = createIterator(getPropertyValueByName(
				sourceObj, childrenPropertyName));
		Iterator<?> destinationIterator = createIterator(getPropertyValueByName(
				destinationObj, childrenPropertyName));

		if (sourceIterator != null && destinationIterator != null) {
			while (sourceIterator.hasNext() && destinationIterator.hasNext()) {
				recursiveValueCopy(childrenPropertyName, sourceIterator.next(),
						destinationIterator.next(),
						mapObjSrcProperiesToObjDestProperies);
			}
		}
	}

	private Iterator<?> createIterator(Object hopefullyIterableOrArrayObject) {
		Iterator<?> i = null;
		if (hopefullyIterableOrArrayObject != null) {
			if (hopefullyIterableOrArrayObject.getClass().isArray()) {
				i = new ArrayIterator(hopefullyIterableOrArrayObject);
			} else {
				try {
					i = ((Iterable<?>) hopefullyIterableOrArrayObject)
							.iterator();
				} catch (ClassCastException e) {
					logger.debug(e.getMessage());
				}
			}
		}
		return i;
	}

	// dubbia utilit�
	public boolean propertiesNull(Object aBean, Set<String> keySet) {
		boolean result = true;

		for (String propertyName : keySet) {
			try {
				if (getPropertyValueByName(aBean, propertyName) != null) {
					result = false;
					break;
				}
			} catch (Exception e) {
				// a property not found is considered null
			}
		}

		return result;
	}

	/**
	 * Trasforma tutte le stringhe vuote ("") del bean inserito in valori null.
	 * Utile per la conversione da application data a DTO.
	 * 
	 * @throws Exception
	 * @author 71732
	 */
	public static void clearEmptyStrings(Object bean) throws Exception {
		Set<String> props = getProperties(bean);
		for (String prop : props) {
			Object value = getPropertyValueByName(bean, prop);
			if (value != null
					&& value.getClass().equals(java.lang.String.class)
					&& StringUtil.isEmpty((java.lang.String) value)) {
				setPropertyValueByName(bean, prop, null);
			}
		}
	}

	public <T> Map<String, String> map(final T[] sourceData,
			String keyPropertyName, String valuePropertyName) {
		return map(new Iterable<T>() {

			@SuppressWarnings("unchecked")
			public Iterator<T> iterator() {
				return (Iterator<T>) createIterator(sourceData);
			}
		}, keyPropertyName, valuePropertyName);
	}

	public <T> Map<String, String> map(Iterable<T> sourceData,
			String keyPropertyName, String valuePropertyName) {
		Map<String, String> result = new HashMap<String, String>();
		for (T t : sourceData) {
			try {
				String keyNameValue = convert(String.class,
						getPropertyValueByName(t, keyPropertyName));
				String value = convert(String.class,
						getPropertyValueByName(t, valuePropertyName));

				if (keyNameValue != null && value != null) {
					result.put(keyNameValue, value);
				}
			} catch (Exception e) {
				// not found
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T clone(T aBean) {
		T result = null;
		if (aBean != null) {
			result = (T) transform(aBean, aBean.getClass());
		}
		return result;
	}

	public <T> T createEmptyInstance(Class<T> clazz) {
		T result = null;
		String message = "Impossibile istanziare " + clazz
				+ " imposto il suo valore a null.";
		try {
			result = clazz.newInstance();
			Map<String, Class<?>> propertiesAndTypes = getPropertiesAndTypes(clazz);
			for (String propName : propertiesAndTypes.keySet()) {
				try {
					Class<?> newType = propertiesAndTypes.get(propName);
					if (!isTypeImmutable(newType)) {
						setPropertyValueByName(result, propName,
								createEmptyInstance(newType));
					}
				} catch (Exception e) {
					logger.debug(message);
				}
			}
		} catch (Exception e) {
			logger.debug(message);
		}
		return result;
	}

	public Iterable<String> enumerateProperties(final Object obj) {
		return enumerateProperties(obj, obj.getClass(), false);
	}

	public Iterable<String> enumerateProperties(final Class<?> clazz) {
		return enumerateProperties(createEmptyInstance(clazz), clazz, false);
	}

	private Iterable<String> enumerateProperties(final Object obj,
			final Class<? extends Object> clazz, final boolean prependPrefix) {
		return new Iterable<String>() {

			public Iterator<String> iterator() {

				Iterator<String> result;
				if (obj != null && clazz != null && clazz.isArray()) {
					result = new Iterator<String>() {
						private Iterator<String> childIterator = null;
						private int currentItemPosition = -1;

						public boolean hasNext() {
							return currentItemPosition + 1 < Array
									.getLength(obj)
									|| (childIterator != null && childIterator
											.hasNext());
						}

						public String next() {
							String result;

							if (childIterator == null
									|| !childIterator.hasNext()) {
								currentItemPosition++;
								if (currentItemPosition < Array.getLength(obj)) {
									childIterator = enumerateProperties(
											Array.get(obj, currentItemPosition),
											clazz.getComponentType(), true)
											.iterator();
								} else {
									childIterator = null;
								}
							}
							result = "[" + currentItemPosition + "]";
							if (!(childIterator == null || !childIterator
									.hasNext())) {
								result = result + childIterator.next();
							}
							return result;
						}

						public void remove() {
							// nop

						}
					};
				} else if (clazz == null || isTypeImmutable(clazz)) {
					result = new Iterator<String>() {

						public boolean hasNext() {
							return false;
						}

						public String next() {
							return null;
						}

						public void remove() {
						}
					};
				} else {
					result = new Iterator<String>() {
						private String current = null;
						private Iterator<String> iterator = null;
						private Iterator<String> childIterator = null;
						private Map<String, Class<?>> propertiesAndTypes = null;

						public void remove() {
							// nop
						}

						public String next() {
							String next = null;

							boolean empty = prepare();

							if (!empty
									&& (childIterator == null || !childIterator
											.hasNext()) && iterator.hasNext()) {
								current = iterator.next();
								next = current;
								Class<?> childClass = propertiesAndTypes
										.get(current);
								if (!isTypeImmutable(childClass)) {
									try {
										childIterator = null;
										childIterator = enumerateProperties(
												getPropertyValueByName(obj,
														current), childClass,
												true).iterator();
									} catch (Exception e) {
										// impossibile enumerarlo
									}
								}
							}

							if (childIterator != null
									&& childIterator.hasNext()) {
								next = current + childIterator.next();
							} else {
								childIterator = null;
							}

							if (prependPrefix) {
								next = "." + next;
							}

							//logger.debug("PropName: " + next);

							return next;
						}

						private boolean prepare() {
							boolean empty = false;
							if (propertiesAndTypes == null) {
								try {
									if (obj == null) {
										empty = true;
									} else {
										propertiesAndTypes = getPropertiesAndTypes(clazz);
										iterator = propertiesAndTypes.keySet()
												.iterator();
									}
								} catch (IntrospectionException e) {
									empty = true;
								}
							}
							return empty;
						}

						public boolean hasNext() {
							return !prepare()
									&& (iterator.hasNext() || (childIterator != null ? childIterator
											.hasNext() : false));
						}
					};
				}
				return result;
			}
		};
	}

	// TODO rifattorizzare
	public <T> T createNewInstance(Class<T> xmlClass) {
		try {
			return xmlClass.newInstance();
		} catch (Exception e) {
			String message = "Impossibile istanziare " + xmlClass
					+ ", errore fatale: " + e.getMessage();
			logger.fatal(message, e);
			throw new RuntimeException(message, e);
		}
	}

	public static BigDecimal transformToBigDecimal(Double value) {
		try {
			return convert(BigDecimal.class, value);
		} catch (Exception e) {
			logConvertException(value, "", BigDecimal.class);
			return null;
		}
	}

	public Map<String, Object> transformToMap(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String propName : enumerateProperties(bean)) {
			map.put(propName, getPropertyValueByName(bean, propName));
		}
		return map;
	}

	public String encodeMap(Map<String, Object> map,
			String REGEX_TOKEN_SEPARATOR, String REGEX_ASSIGNMENT_OPERATOR) {
		StringBuffer buffer = new StringBuffer();
		for (String propName : map.keySet()) {
			buffer.append(propName).append(REGEX_ASSIGNMENT_OPERATOR)
					.append(transform(map.get(propName), String.class))
					.append(REGEX_TOKEN_SEPARATOR);
		}

		String result;
		if (buffer.length() > REGEX_TOKEN_SEPARATOR.length()) {
			result = buffer.substring(0, buffer.length()
					- REGEX_TOKEN_SEPARATOR.length());
		} else {
			result = buffer.toString();
		}

		return result;
	}

	public Map<String, String> decodeMap(String string,
			String REGEX_TOKEN_SEPARATOR, String REGEX_ASSIGNMENT_OPERATOR) {
		Map<String, String> result;
		result = new HashMap<String, String>();
		if (string != null) {
			for (String token : string.split(REGEX_TOKEN_SEPARATOR)) {
				String[] strings = token.split(REGEX_ASSIGNMENT_OPERATOR);
				if (strings.length == 2) {
					result.put(strings[0], strings[1]);
				}
			}
		}
		return result;
	}
}
