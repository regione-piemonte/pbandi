/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandiutil.common;

import it.csi.pbandi.pbweberog.pbandiutil.common.dto.ContestoIdentificativoDTO;

import java.beans.IntrospectionException;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 
 * @author Gruppo bandi
 * 
 *         Classe wrapper di Log4j
 *         <p>
 *         Espone i metodi classici di output recuperando dallo stack trace il
 *         nome della classe e del metodo invocato. <br>
 */
public class LoggerUtil {
	private static final int MAX_DUMP_LEVEL = 5;
	public static final int MAX_DUMPABLE_ELEMENTS = 100;
	private ContestoIdentificativoDTO contestoIdentificativoDTO;
	
	
	public ContestoIdentificativoDTO getContestoIdentificativoDTO() {
		return contestoIdentificativoDTO;
	}

	public void setContestoIdentificativoDTO(ContestoIdentificativoDTO contestoIdentificativoDTO) {
		this.contestoIdentificativoDTO = contestoIdentificativoDTO;
	}

	public void debug(String message) {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		Logger logger = Logger.getLogger(className);
			
		if (logger.isDebugEnabled()) {
			String methodName = traceElement.getMethodName();
			if(contestoIdentificativoDTO!=null && contestoIdentificativoDTO.getIdUtente()!=null){
				message+=" - idUtente="+contestoIdentificativoDTO.getIdUtente();
			}
			logger.debug("[" + className + "::" + methodName + "] " + message);
		}
	}

	public void info(String message) {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		if(contestoIdentificativoDTO!=null && contestoIdentificativoDTO.getIdUtente()!=null){
			message+=" - idUtente="+contestoIdentificativoDTO.getIdUtente();
		}
		logger.info("[" + className + "::" + methodName + "] " + message);

	}

	public void error(String message, Throwable ex) {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		if(contestoIdentificativoDTO!=null && contestoIdentificativoDTO.getIdUtente()!=null){
			message+=" - idUtente="+contestoIdentificativoDTO.getIdUtente();
		}
		logger.error("[" + className + "::" + methodName + "] " + message, ex);

	}

	public void fatal(String message, Throwable ex) {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		if(contestoIdentificativoDTO!=null && contestoIdentificativoDTO.getIdUtente()!=null){
			message+=" - idUtente="+contestoIdentificativoDTO.getIdUtente();
		}
		logger.fatal("[" + className + "::" + methodName + "] " + message, ex);

	}

	public void warn(String message) {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		if(contestoIdentificativoDTO!=null && contestoIdentificativoDTO.getIdUtente()!=null){
			message+=" - idUtente="+contestoIdentificativoDTO.getIdUtente();
		}
		logger.warn("[" + className + "::" + methodName + "] " + message);

	}

	/**
	 * traccia l'ingresso di un metodo su livello INFO
	 */
	public void begin() {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		logger.debug("[" + className + "::" + methodName + "] BEGIN");
	}

	/**
	 * traccia l'uscita di un metodo su livello INFO
	 */
	public void end() {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		logger.debug("[" + className + "::" + methodName + "] END\n");
	}

	public Logger getLogger() {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		Logger logger = Logger.getLogger(className);
		return logger;
	}

	public void dumpError(Object o) {
		try {
			StackTraceElement[] traceElements = new Throwable().getStackTrace();
			StackTraceElement traceElement = traceElements[1];
			String className = traceElement.getClassName();
			Logger logger = Logger.getLogger(className);
			String methodName = traceElement.getMethodName();
			logger.error("[" + className + "::" + methodName + "] " + doDump(o));
		} catch (Throwable t) {
			this.error("Dump fallito: " + t.getMessage(), t);
		}
	}

	/**
	 * Effettua il dump dell'oggetto in ingresso su livello DEBUG
	 * 
	 * @param o
	 */
	public void dump(Object o) {
		try {
			StackTraceElement[] traceElements = new Throwable().getStackTrace();
			StackTraceElement traceElement = traceElements[1];
			String className = traceElement.getClassName();
			Logger logger = Logger.getLogger(className);
			if (logger.isDebugEnabled()) {
				String methodName = traceElement.getMethodName();
				logger.debug("[" + className + "::" + methodName + "] "
						+ doDump(o));
			}
		} catch (Throwable t) {
			this.error("Dump fallito: " + t.getMessage(), t);
		}
	}

	private String doDump(Object o) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("beginning dump of: \n");
		dump(o, buffer, new HashSet<Object>(), 0);
		String string = buffer.toString();
		return string;
	}

	private void dump(Object o, StringBuffer buffer, Set<Object> alreadyDumped,
			int level) {
		if (o == null) {
			buffer.append("<null object>");
		} else if (level > MAX_DUMP_LEVEL) {
			buffer.append("<object too deeply nested>");
		} else {
			Class<?> cl = o.getClass();
			buffer.append("(").append(cl.getName()).append(")").append(" <")
					.append(o.toString()).append("> ");
			if (alreadyDumped.contains(o)) {
				buffer.append("object already dumped");
			} else {
				alreadyDumped.add(o);

				boolean dumpedTooManyObjects = false;

				if (cl.isArray()) {
					int length = Array.getLength(o);
					buffer.append("size: ").append(length);
					if (!byte[].class.equals(cl)) {
						for (int i = 0; i < length; i++) {
							buffer.append("\n");
							indent(buffer, level);
							buffer.append("[").append(i).append("] ");
							dump(Array.get(o, i), buffer, alreadyDumped,
									level + 1);

							if (i >= MAX_DUMPABLE_ELEMENTS) {
								dumpedTooManyObjects = true;
								break;
							}
						}
					}
				} else if (!String.class.equals(cl)) {
					try {
						Set<String> properties = BeanUtil.getProperties(o);
						properties.remove("class");

						int propCount = 0;
						for (String propName : properties) {
							buffer.append("\n");
							indent(buffer, level);
							buffer.append(propName).append(" ");
							dump(BeanUtil.getPropertyValueByName(o, propName),
									buffer, alreadyDumped, level + 1);

							propCount++;
							if (propCount >= MAX_DUMPABLE_ELEMENTS) {
								dumpedTooManyObjects = true;
								break;
							}
						}
					} catch (IntrospectionException e) {
						buffer.append("object not introspectable");
					}
				}

				if (dumpedTooManyObjects) {
					buffer.append("\n");
					indent(buffer, level);
					buffer.append("... (more elements omitted)");
				}
			}

		}
	}

	private void indent(StringBuffer buffer, int level) {
		for (int i = 0; i < (level + 1) * 2; i++) {
			buffer.append(" ");
		}
	}

	public void error(String message) {
		StackTraceElement[] traceElements = new Throwable().getStackTrace();
		StackTraceElement traceElement = traceElements[1];
		String className = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		Logger logger = Logger.getLogger(className);
		if(contestoIdentificativoDTO!=null && contestoIdentificativoDTO.getIdUtente()!=null){
			message+=" - idUtente="+contestoIdentificativoDTO.getIdUtente();
		}
		logger.error("[" + className + "::" + methodName + "] " + message);
	}
	
	
	public void shallowDump(Object obj,String...level) {
		if(obj==null) return;
		Set<String> properties;
		try {
			properties = BeanUtil.getProperties(obj);
			properties.remove("class");
			properties.remove("tableNameForValueObject"); 
			StringBuilder sb=new StringBuilder("\nclass: "+obj.getClass());
			for (String propName : properties) {
				Object val = BeanUtil.getPropertyValueByName(obj, propName);
				sb.append("\n"+propName+":"+val);
			}
			StackTraceElement[] traceElements = new Throwable().getStackTrace();
			StackTraceElement traceElement = traceElements[1];
			String className = traceElement.getClassName();
			String methodName = traceElement.getMethodName();
			Logger logger = Logger.getLogger(className);
			if(level!=null && level.length>0 ){
				if(level[0].equalsIgnoreCase("error"))
		 			logger.error("[" + className + "::" + methodName + "] " + sb.toString());
				else if(level[0].equalsIgnoreCase("warn"))
					logger.warn("[" + className + "::" + methodName + "] " + sb.toString());
				else if(level[0].equalsIgnoreCase("info"))
					logger.info("[" + className + "::" + methodName + "] " + sb.toString());
				else  
					logger.debug("[" + className + "::" + methodName + "] " + sb.toString());
			}else{
				logger.debug("[" + className + "::" + methodName + "] " + sb.toString()+"\n\n");
			}
		} catch (Exception e) {
			//no need to stop business
			System.out.println(e.getMessage());
		}
	}
	
}
