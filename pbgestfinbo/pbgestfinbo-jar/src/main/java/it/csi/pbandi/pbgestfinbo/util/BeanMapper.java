/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;


public class BeanMapper {

	/**
	 * Special array value used by <code>mapColumnsToProperties</code> that
	 * indicates there is no bean property that matches a column from a
	 * <code>ResultSet</code>.
	 */
	protected static final int PROPERTY_NOT_FOUND = -1;

	/**
	 * Set a bean's primitive properties to these defaults when SQL NULL is
	 * returned. These are the same as the defaults that ResultSet get* methods
	 * return in the event of a NULL column.
	 */
	private static final Map<Object, Object> primitiveDefaults = new HashMap<Object, Object>();

	static {
		primitiveDefaults.put(Integer.TYPE, new Integer(0));
		primitiveDefaults.put(Short.TYPE, new Short((short) 0));
		primitiveDefaults.put(Byte.TYPE, new Byte((byte) 0));
		primitiveDefaults.put(Float.TYPE, new Float(0));
		primitiveDefaults.put(Double.TYPE, new Double(0));
		primitiveDefaults.put(Long.TYPE, new Long(0));
		primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
		primitiveDefaults.put(Character.TYPE, new Character('\u0000'));
	}

	/**
	 * Ottiene il valore di una property a partire dal nome del campo sul db
	 * 
	 * @param fieldName
	 *            nome del campo
	 * @return
	 */
	public static Object getBeanValueByDBFieldNameByIntrospection(Object bean,
			String dbFieldName) throws Exception {
		return BeanUtil.getPropertyValueByName(bean,
				getPropertyNameByDBFieldName(dbFieldName));
	}

	public static void setBeanValueByDBFieldNameByIntrospection(Object bean,
			String dbFieldName, Object value) throws Exception {
		BeanUtil.setPropertyValueByName(bean,
				getPropertyNameByDBFieldName(dbFieldName), value);
	}

	/**
	 * implementa la strategia di converzione "NOME_CAMPO_DB" -> "nomeCampoDb"
	 * 
	 * @param dbFieldName
	 * @return
	 */
	public static String getPropertyNameByDBFieldName(String dbFieldName) {
		// tutto in minuscolo
		dbFieldName = dbFieldName.toLowerCase();

		// split sul carattere "_"
		String[] namePart = dbFieldName.split("_");

		String propertyName = "";
		String part = "";
		for (int i = 0; i < namePart.length; i++) {
			if (i == 0) // nella prima parte lascio tutto minuscolo
				part = namePart[i];
			else
				// nelle successive metto loa prima maiuscola
				part = namePart[i].substring(0, 1).toUpperCase()
						+ namePart[i].substring(1);
			propertyName = propertyName + part;
		}

		return propertyName;
	}

	/**
	 * Constructor for BeanMapper.
	 */
//	public BeanMapper() {
//	}

	/**
	 * Convert a <code>ResultSet</code> row into a JavaBean. This implementation
	 * uses reflection and <code>BeanInfo</code> classes to match column names
	 * to bean property names. Properties are matched to columns based on
	 * several factors: <br/>
	 * <ol>
	 * <li>
	 * The class has a writable property with the same name as a column. The
	 * name comparison is case insensitive.</li>
	 * 
	 * <li>
	 * The column type can be converted to the property's set method parameter
	 * type with a ResultSet.get* method. If the conversion fails (ie. the
	 * property was an int and the column was a Timestamp) an SQLException is
	 * thrown.</li>
	 * </ol>
	 * 
	 * <p>
	 * Primitive bean properties are set to their defaults when SQL NULL is
	 * returned from the <code>ResultSet</code>. Numeric fields are set to 0 and
	 * booleans are set to false. Object bean properties are set to
	 * <code>null</code> when SQL NULL is returned. This is the same behavior as
	 * the <code>ResultSet</code> get* methods.
	 * </p>
	 * 
	 * @param rs
	 *            ResultSet that supplies the bean data
	 * @param type
	 *            Class from which to create the bean instance
	 * @throws SQLException
	 *             if a database access error occurs
	 * @return the newly created bean
	 */
	public Object toBean(ResultSet rs, Class<?> type) throws SQLException {

		PropertyDescriptor[] props = this.propertyDescriptors(type);
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

		return this.createBean(rs, type, props, columnToProperty);
	}

	/**
	 * Convert a <code>ResultSet</code> into a <code>List</code> of JavaBeans.
	 * This implementation uses reflection and <code>BeanInfo</code> classes to
	 * match column names to bean property names. Properties are matched to
	 * columns based on several factors: <br/>
	 * <ol>
	 * <li>
	 * The class has a writable property with the same name as a column. The
	 * name comparison is case insensitive.</li>
	 * 
	 * <li>
	 * The column type can be converted to the property's set method parameter
	 * type with a ResultSet.get* method. If the conversion fails (ie. the
	 * property was an int and the column was a Timestamp) an SQLException is
	 * thrown.</li>
	 * </ol>
	 * 
	 * <p>
	 * Primitive bean properties are set to their defaults when SQL NULL is
	 * returned from the <code>ResultSet</code>. Numeric fields are set to 0 and
	 * booleans are set to false. Object bean properties are set to
	 * <code>null</code> when SQL NULL is returned. This is the same behavior as
	 * the <code>ResultSet</code> get* methods.
	 * </p>
	 * 
	 * @param rs
	 *            ResultSet that supplies the bean data
	 * @param type
	 *            Class from which to create the bean instance
	 * @throws SQLException
	 *             if a database access error occurs
	 * @return the newly created List of beans
	 */
	public List toBeanList(ResultSet rs, Class<?> type) throws SQLException {
		List results = new ArrayList();

		if (!rs.next()) {
			return results;
		}

		PropertyDescriptor[] props = this.propertyDescriptors(type);
		ResultSetMetaData rsmd = rs.getMetaData();
		int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

		do {
			results.add(this.createBean(rs, type, props, columnToProperty));
		} while (rs.next());

		return results;
	}

	/**
	 * Creates a new object and initializes its fields from the ResultSet.
	 * 
	 * @param rs
	 *            The result set.
	 * @param type
	 *            The bean type (the return type of the object).
	 * @param props
	 *            The property descriptors.
	 * @param columnToProperty
	 *            The column indices in the result set.
	 * @return An initialized object.
	 * @throws SQLException
	 *             if a database error occurs.
	 */
	private Object createBean(ResultSet rs, Class type,
			PropertyDescriptor[] props, int[] columnToProperty)
			throws SQLException {

		Object bean = this.newInstance(type);

		for (int i = 1; i < columnToProperty.length; i++) {

			if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
				continue;
			}

			PropertyDescriptor prop = props[columnToProperty[i]];
			Class propType = prop.getPropertyType();

			Object value = this.extractColumnValue(rs, i, propType);

			if (propType != null && value == null && propType.isPrimitive()) {
				value = primitiveDefaults.get(propType);
			}

			this.callSetter(bean, prop, value);
		}

		return bean;
	}

	/**
	 * Calls the setter method on the target object for the given property. If
	 * no setter method exists for the property, this method does nothing.
	 * 
	 * @param target
	 *            The object to set the property on.
	 * @param prop
	 *            The property to set.
	 * @param value
	 *            The value to pass into the setter.
	 * @throws SQLException
	 *             if an error occurs setting the property.
	 */
	private void callSetter(Object target, PropertyDescriptor prop, Object value)
			throws SQLException {

		Method setter = prop.getWriteMethod();

		if (setter == null) {
			return;
		}

		Class[] params = setter.getParameterTypes();
		try {
			// convert types for some popular ones
			if (value != null) {
				if (value instanceof java.util.Date) {
					if (params[0].getName().equals("java.sql.Date")) {
						value = new java.sql.Date(((java.util.Date) value)
								.getTime());
					} else if (params[0].getName().equals("java.sql.Time")) {
						value = new java.sql.Time(((java.util.Date) value)
								.getTime());
					} else if (params[0].getName().equals("java.sql.Timestamp")) {
						value = new java.sql.Timestamp(((java.util.Date) value)
								.getTime());
					}
				} else if (value instanceof Long) {
					if (params[0].equals(Double.class)) {
						value = new Double((Long) value);
					}
				//} else if (value instanceof oracle.sql.BLOB) {
				//	value=((oracle.sql.BLOB) value).getBytes();					
				}
			}

			// Don't call setter if the value object isn't the right type
			if (this.isCompatibleType(value, params[0])) {
				setter.invoke(target, new Object[] { value });
			} else {
				throw new SQLException("Cannot set " + prop.getName()
						+ ": incompatible types.");
			}

		} catch (IllegalArgumentException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": "
					+ e.getMessage());

		} catch (InvocationTargetException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * ResultSet.getObject() returns an Integer object for an INT column. The
	 * setter method for the property might take an Integer or a primitive int.
	 * This method returns true if the value can be successfully passed into the
	 * setter method. Remember, Method.invoke() handles the unwrapping of
	 * Integer into an int.
	 * 
	 * @param value
	 *            The value to be passed into the setter method.
	 * @param type
	 *            The setter's parameter type.
	 * @return boolean True if the value is compatible.
	 */
	private boolean isCompatibleType(Object value, Class type) {
		// Do object check first, then primitives
		if (value == null || type.isInstance(value)) {
			return true;

		} else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value)) {
			return true;

		} else if (type.equals(Long.TYPE) && Long.class.isInstance(value)) {
			return true;

		} else if (type.equals(Double.TYPE) && Double.class.isInstance(value)) {
			return true;

		} else if (type.equals(Float.TYPE) && Float.class.isInstance(value)) {
			return true;

		} else if (type.equals(Short.TYPE) && Short.class.isInstance(value)) {
			return true;

		} else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value)) {
			return true;

		} else if (type.equals(Character.TYPE)
				&& Character.class.isInstance(value)) {
			return true;

		} else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value)) {
			return true;
		//} else if (type.isArray() && oracle.sql.BLOB.class.isInstance(value)) {
		//	return true;
		} else {
			return false;
		}

	}

	/**
	 * Factory method that returns a new instance of the given Class. This is
	 * called at the start of the bean creation process and may be overridden to
	 * provide custom behavior like returning a cached bean instance.
	 * 
	 * @param c
	 *            The Class to create an object from.
	 * @return A newly created object of the Class.
	 * @throws SQLException
	 *             if creation failed.
	 */
	protected Object newInstance(Class c) throws SQLException {
		try {
			return c.newInstance();

		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * Returns a PropertyDescriptor[] for the given Class.
	 * 
	 * @param c
	 *            The Class to retrieve PropertyDescriptors for.
	 * @return A PropertyDescriptor[] describing the Class.
	 * @throws SQLException
	 *             if introspection failed.
	 */
	private PropertyDescriptor[] propertyDescriptors(Class c)
			throws SQLException {
		// Introspector caches BeanInfo classes for better performance
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(c);

		} catch (IntrospectionException e) {
			throw new SQLException("Bean introspection failed: "
					+ e.getMessage());
		}

		return beanInfo.getPropertyDescriptors();
	}

	/**
	 * The positions in the returned array represent column numbers. The values
	 * stored at each position represent the index in the
	 * <code>PropertyDescriptor[]</code> for the bean property that matches the
	 * column name. If no bean property was found for a column, the position is
	 * set to <code>PROPERTY_NOT_FOUND</code>.
	 * 
	 * @param rsmd
	 *            The <code>ResultSetMetaData</code> containing column
	 *            information.
	 * 
	 * @param props
	 *            The bean property descriptors.
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 * 
	 * @return An int[] with column index to property index mappings. The 0th
	 *         element is meaningless because JDBC column indexing starts at 1.
	 */
	protected int[] mapColumnsToProperties(ResultSetMetaData rsmd,
			PropertyDescriptor[] props) throws SQLException {

		int cols = rsmd.getColumnCount();
		int columnToProperty[] = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);
		
		// Trasforma "idAttributo" in "id_attributo".
		String[] newPropName = new String[props.length];
		for (int j = 0; j < props.length; j++) {
			String s = ""+this.getDBFieldNameByPropertyName(props[j].getName());
			newPropName[j] = s;
		}

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnName(col);
			for (int i = 0; i < props.length; i++) {
				//if (columnName.equalsIgnoreCase(props[i].getName())) {
				if (columnName.equalsIgnoreCase(newPropName[i])) {
					columnToProperty[col] = i;
					break;
				}
			}
		}

		return columnToProperty;
	}

	/**
	 * Convert a <code>ResultSet</code> column into an object. Simple
	 * implementations could just call <code>rs.getObject(index)</code> while
	 * more complex implementations could perform type manipulation to match the
	 * column's type to the bean property type.
	 * 
	 * <p>
	 * This implementation calls the appropriate <code>ResultSet</code> getter
	 * method for the given property type to perform the type conversion. If the
	 * property type doesn't match one of the supported <code>ResultSet</code>
	 * types, <code>getObject</code> is called.
	 * </p>
	 * 
	 * @param rs
	 *            The <code>ResultSet</code> currently being processed. It is
	 *            positioned on a valid row before being passed into this
	 *            method.
	 * 
	 * @param index
	 *            The current column index being processed.
	 * 
	 * @param propType
	 *            The bean property type that this column needs to be converted
	 *            into.
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 * 
	 * @return The object from the <code>ResultSet</code> at the given column
	 *         index after optional type processing or <code>null</code> if the
	 *         column value was SQL NULL.
	 */
	@SuppressWarnings("unchecked")
	public  <T> T extractColumnValue(ResultSet rs, int index,
			Class<T> propType) throws SQLException {

		if (propType.equals(String.class)) {
			return (T) rs.getString(index);

		} else if (propType.equals(Integer.TYPE)
				|| propType.equals(Integer.class)) {
			if (rs.getObject(index) != null)
				return (T) new Integer(rs.getInt(index));
			else
				return null;
		} else if (propType.equals(Boolean.TYPE)
				|| propType.equals(Boolean.class)) {
			return (T) new Boolean(rs.getBoolean(index));

		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			if (rs.getObject(index) != null)
				return (T) new Long(rs.getLong(index));
			else
				return null;

		} else if (propType.equals(Double.TYPE)
				|| propType.equals(Double.class)) {
			if (rs.getObject(index) != null)
				return (T) new Double(rs.getDouble(index));
			else
				return null;

		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return (T) new Float(rs.getFloat(index));

		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return (T) new Short(rs.getShort(index));

		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return (T) new Byte(rs.getByte(index));

		} else if (propType.equals(Timestamp.class)) {
			return (T) rs.getTimestamp(index);

		} else if (propType.equals(java.sql.Date.class)) {
			Timestamp timestamp = rs.getTimestamp(index);
			return timestamp == null ? null : (T) new java.sql.Date(timestamp
					.getTime());
		}
		else if(propType.isArray() && propType.getComponentType().equals(byte.class)){	
			return (T)lobHandler.getBlobAsBytes(rs, index);
		}
		else {
			return (T) rs.getObject(index);
		}
		

	}

	
	
	private LobHandler lobHandler; // di tipo OracleLobHandler
	private JdbcTemplate jdbcTemplate;
	
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	
	
	static public String getDBFieldNameByPropertyName(String propertyName) {
		StringBuffer sb = new StringBuffer();
		char lastChar = 'a';
		for (int i = 0; i < propertyName.length(); i++) {
			char currentChar = propertyName.charAt(i);
			if (Character.isUpperCase(currentChar)
					&& !Character.isUpperCase(lastChar)) {
				sb.append('_');
			}
			sb.append(Character.toUpperCase(currentChar));
			lastChar = currentChar;
		}
		return sb.toString();
	}
}
