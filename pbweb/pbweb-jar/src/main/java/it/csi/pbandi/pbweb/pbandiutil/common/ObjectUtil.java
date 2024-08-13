/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ObjectUtil {

	public static boolean isNull(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String o) {
		if (o == null || o.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object[] o) {
		if (o == null || o.length == 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Map o) {
		if (o == null || o.size() == 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Collection o) {
		if (o == null || o.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isTrue(Boolean bool) {
		if (bool != null)
			return bool.booleanValue();
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] arrayMerge(T[]... arrays) {
		// Determine required size of new array

		int count = 0;
		for (T[] array : arrays) {
			count += array.length;
		}

		// create new array of required class
		T[] mergedArray = null;
		if (count == 0) {
			mergedArray = arrays[0];
		} else {
			// Merge each array into new array
			int start = 0;
			for (T[] array : arrays) {
				if (array.length > 0) {
					if (mergedArray == null) {
						mergedArray = (T[]) Array.newInstance(
								array[0].getClass(), count);
					}

					System.arraycopy(array, 0, mergedArray, start, array.length);
					start += array.length;
				}
			}
		}
		return mergedArray;
	}

	/**
	 * Questa funzione emula l'omonima funzione Oracle.
	 * 
	 * @author 71732
	 * @param object
	 *            oggetto da ottenere
	 * @param objectIfNull
	 *            valore da restituire nel caso l'oggetto sia null
	 * @return object se esso non � null, objectIfNull altrimenti
	 */
	public static <T> T nvl(T object, T objectIfNull) {
		if (object == null) {
			return objectIfNull;
		} else {
			return object;
		}
	}

	/**
	 * Questa funzione emula l'omonima funzione Oracle.
	 * 
	 * @author 71732
	 * @param conditionObject
	 *            oggetto da testare
	 * @param objectIfNotNull
	 *            valore da restituire nel caso l'oggetto condizionale non sia
	 *            null
	 * @param objectIfNull
	 *            valore da restituire nel caso l'oggetto condizionale sia null
	 * @return objectIfNotNull se conditionObject non � null, objectIfNull
	 *         altrimenti
	 */
	public static <T, E> E nvl2(T conditionObject, E objectIfNotNull,
			E objectIfNull) {
		if (conditionObject == null) {
			return objectIfNull;
		} else {
			return objectIfNotNull;
		}
	}

	/**
	 * Verifica in una serie di oggetti la presenza di un oggetto uguale a uno
	 * dato. Il tipo di oggetto deve avere un implementazione di
	 * Object.equals(Object anobject) che permetta di verificare l'uguaglianza
	 * 
	 * @author 71732
	 * @param conditionObject
	 *            l'oggetto di cui verificare la presenza
	 * @param checkObjects
	 *            gli oggetti che compongono la lista in cui conditionObject pu�
	 *            o meno essere presente
	 * @return true se conditionObject risponde positivamente a una
	 *         Object.equals() su uno dei checkObjects, false altrimenti o se
	 *         conditionObject � null
	 */
	public static <T> Boolean in(T conditionObject, T... checkObjects) {
		if (conditionObject == null) {
			return false;
		}
		for (T checkObject : checkObjects) {
			if (conditionObject.equals(checkObject)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param iterable
	 *            un qualunque Iterable che potrebbe essere null
	 * @return un Iterable sicuramente non null
	 */
	public static <T> Iterable<T> lazyIterator(Iterable<T> iterable) {
		Iterable<T> result;
		if (iterable != null) {
			result = iterable;
		} else {
			result = new Iterable<T>() {
				public Iterator<T> iterator() {
					return new Iterator<T>() {
						public boolean hasNext() {
							return false;
						}

						public T next() {
							return null;
						}

						public void remove() {
						}
					};
				}
			};
		}
		return result;
	}

	public static Object createNewInstance(String clazzName) {
		Object result = null;
		try {
			Class<?> clazz = Class.forName(clazzName);
			result = clazz.newInstance();
		} catch (Exception e) {
		}
		return result;
	}

	public static String getSimpleName(Class<?> voClass) {
		Class<?> currentClass = voClass;
		String voClassName = currentClass.getSimpleName();

		while (voClassName.equals("")) {
			currentClass = currentClass.getSuperclass();
			voClassName = currentClass.getSimpleName();
		}
		return voClassName;
	}

	public static String getClassSimpleName(Object o) {
		if (o == null) {
			return null;
		} else {
			return getSimpleName(o.getClass());
		}
	}

	public static Method findMethod(Object objectBusinessClass,
			String methodName, Object[] params) throws NoSuchMethodException {
		// TODO non fa il check del numero e del tipo dei parametri per gestire
		// i casi di overlaod di metodi

		Class<?>[] classParams = null;
		if (params != null) {
			Method[] metodiClasseDaInvocare = objectBusinessClass.getClass()
					.getMethods();
			for (int i = 0; i < metodiClasseDaInvocare.length; i++) {
				if (methodName.equals(metodiClasseDaInvocare[i].getName())) {
					classParams = metodiClasseDaInvocare[i].getParameterTypes();
					break;
				}
			}
		}

		Method method = objectBusinessClass.getClass().getMethod(methodName,
				classParams);
		return method;
	}

}
