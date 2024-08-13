/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class FileSqlUtil {

	public static final String PATH = "sql/";
	
	private static Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);
	
	public String getQuery(String nomeFile) throws FileNotFoundException, IOException {

		String prf = "[FileSqlUtil::getQuery] ";
		logger.debug(prf + "BEGIN");
		
		String query = null;
		
//		try{
			if(StringUtils.isNotBlank(nomeFile)) {

				InputStream file = getClass().getClassLoader().getResourceAsStream( PATH + nomeFile);
				if(file!=null) {
					logger.debug(prf + "file caricato "+ file.available());
				}else {
					logger.debug(prf + "file nullo");
				}
				query = IOUtils.toString(file, StandardCharsets.UTF_8.name());
			}
//		}
//		catch (FileNotFoundException e){
//			e.printStackTrace();
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
			logger.debug(prf + "nomeFile ["+nomeFile+"] caricato");
		return query;
	}



}
