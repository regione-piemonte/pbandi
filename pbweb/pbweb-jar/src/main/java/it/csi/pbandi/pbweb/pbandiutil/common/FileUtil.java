package it.csi.pbandi.pbweb.pbandiutil.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	private LoggerUtil logger;
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}
 
	public static byte[] getBytesFromFile(File file) throws IOException {
		FileUtil fileUtil = new FileUtil();
		fileUtil.setLogger(new LoggerUtil());
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


}
