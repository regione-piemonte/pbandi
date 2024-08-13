/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbwebbo.exception.DaoException;

public class FileUtil {
	
	static Logger logger = Logger.getLogger(DateUtil.class.getName());
 
	public static byte[] getBytesFromFile(File file) throws IOException {
		FileUtil fileUtil = new FileUtil();
		return fileUtil.getBytes(file);
	}

	public byte[] readBytesFromInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		final int FILE_BLOCK_SIZE = 16384; // Numero "magico". Preso dai
		// sorgenti di ADOBE - DV
		byte[] buf = new byte[FILE_BLOCK_SIZE];

		int readed;
		try {
			while ((readed = is.read(buf)) != -1) {
				os.write(buf, 0, readed);
			}
			os.flush();
		} finally {
			is.close();
			os.close();
		}
		return os.toByteArray();
	}


	public byte[] getBytes(File file) throws IOException {

		byte[] bytes;
		try {
			InputStream is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();

			logger.debug("File length: " + length);

			// Create the byte array to hold the data
			bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}

			// Close the input stream and return bytes
			is.close();
		} finally {
		}
		return bytes;
	}
	
	public static void scriviFile(String nomeFile,byte[]bytes) throws IOException{
		
		FileOutputStream fos = new FileOutputStream(nomeFile);
		fos.write(bytes);
		fos.close();
		
	}

	public static ArrayList<FileDTO> leggiFilesDaMultipart(List<InputPart> files, Long idFolder ) throws Exception {
		String prf = "[FileUtil::leggiFilesDaMultipart] ";
		logger.info(prf + " BEGIN");
		
		ArrayList<FileDTO> result = new ArrayList<FileDTO>();
		if (files == null || files.size() == 0) {
			return result;
		}
				
		try {

			String nomeFile = null;
			String mimeType = null;
			byte[] content;
			MultivaluedMap<String, String> header;
			
			for (InputPart inputPart : files) {
				
				header = inputPart.getHeaders();				
				nomeFile = getFileName(header);
				logger.info(prf+"file estratto: nome = " + nomeFile);
				content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
								
				FileDTO fileDTO = new FileDTO();
				fileDTO.setBytes(content);
				fileDTO.setNomeFile(nomeFile);				
				fileDTO.setSizeFile(new Long(content.length));
				fileDTO.setIdFolder(idFolder);	
				logger.info(prf+"file estratto: nome = " + fileDTO.getNomeFile()+"; size = "+fileDTO.getSizeFile());
			
				result.add(fileDTO);
			}
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura dei file da MultipartFormDataInput.", e);
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	private static String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst(Constants.CONTENT_DISPOSITION).split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith(Constants.FILE_NAME_KEY)) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}
	
	public static FileDTO leggiFileDaMultipart(List<InputPart> files, Long idFolder) throws Exception {
		String prf = "[FileUtil::leggiFileDaMultipart] ";
		logger.info(prf + " BEGIN");
		
		if (files == null || files.size() == 0) {
			return null;
		}
		
		FileDTO result = null;
		try {
			
			ArrayList<FileDTO> listaFileDTO = FileUtil.leggiFilesDaMultipart(files, idFolder);
			
			if (listaFileDTO == null || listaFileDTO.size() == 0) {
				return null;
			}
			
			String nomeFile = listaFileDTO.get(0).getNomeFile();
			byte[] bytes = listaFileDTO.get(0).getBytes();
			
			if (StringUtil.isEmpty(nomeFile)) {
				logger.info(prf+"Nome file non valorizzato.");
				return null;
			}
			
			if (bytes == null || bytes.length == 0) {
				logger.info(prf+"File non valorizzato.");
				return null;
			}
				
			result = new FileDTO();
			result.setNomeFile(nomeFile);
			result.setBytes(bytes);
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return null;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}

}
