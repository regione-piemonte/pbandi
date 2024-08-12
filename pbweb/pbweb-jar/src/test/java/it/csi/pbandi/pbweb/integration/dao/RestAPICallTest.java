package it.csi.pbandi.pbweb.integration.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.SerializationUtils;

import it.csi.findom.finservrest.dto.DocumentoListResponse;
import it.csi.findom.finservrest.dto.DocumentoResponse;
import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;



@RunWith(PbwebJunitClassRunner.class)
public class RestAPICallTest extends TestBaseService {
	
	public static final String CLASS_NAME = "RestAPICallTest";
	private static final String ENDPOINT_BASE = "http://localhost/finservrest/api/storageDocumenti";
	
	
	public void setUp() throws Exception {
		final String methodName = "setUp";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		System.out.println(logprefix + " BEGIN-END");
	}
	
	public void tearDown() throws Exception {
		final String methodName = "tearDown";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		System.out.println(logprefix + " BEGIN-END");
	}
	
	
	
	@Test
	public void APIRestDocumentoListCallTest() {
		final String methodName = "APIRestDocumentoListCallTest";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		System.out.println(logprefix + " BEGIN");
		
		try {
			//10.1.2 Richiamo di API REST tramite JAX-RS client standard
			Client client = ClientBuilder.newBuilder().build();
			System.out.println(logprefix + " client OK");
			
			/*
			 * 49992>
webe=http://localhost/finservrest/api/storageDocumenti>
			 */
//			http://localhost/finservrest/api/storageDocumenti/documentoList?idDomanda=54657
	
			//WebTarget target = client.target(ENDPOINT_BASE+"/documentoList/{idDomanda}");
//			WebTarget target = client.target(ENDPOINT_BASE+"/documentoList");
			WebTarget target = client.target("http://localhost/finservrest/api/storageDocumenti/documentoList");
			
			System.out.println(logprefix + " target OK");
//			WebTarget target1000 = target.resolveTemplate("idDomanda", 466);
//			System.out.println(logprefix + " target1000 OK");
			
			WebTarget target1000 = target.queryParam("idDomanda", 49992);
			
			Response resp = (Response) target1000.request().header("invocante", "PK").get();
			System.out.println(logprefix + "response.getStatus(): "+resp.getStatus());
			
			DocumentoListResponse dlr = resp.readEntity(DocumentoListResponse.class);

			System.out.println(logprefix + " DocumentoListResponse=" + dlr);
			
			
//			System.out.println(logprefix + " DocumentoResponse=" + dr.getDocumento().getFile());
			
//			int d = dr.getDocumento().getFile();
//			byte[] dataFile = SerializationUtils.serialize(dr.getDocumento().getFile());
//			System.out.println(logprefix + " dataFile=" +dataFile.length);
//			String nomeFile = dr.getDocumento().getNomeFile();
			
//			FileUtils.writeByteArrayToFile(new File(nomeFile), dr.getDocumento().getFile());
			
//			InputStream in = (InputStream)dr.getDocumento().getFile();
//			System.out.println(logprefix + " in=" +in);
//			
//			File tempFile = new File("ppp.pdf");
//			//tempFile.deleteOnExit();
//			FileOutputStream out = new FileOutputStream(tempFile);
//			IOUtils.copy(in, out);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(logprefix + " END");
	}
	
}
