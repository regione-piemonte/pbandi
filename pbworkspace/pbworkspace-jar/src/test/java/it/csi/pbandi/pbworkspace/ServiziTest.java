/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.PersonaFisicaRowMapper;
import it.csi.pbandi.pbworkspace.integration.vo.PersonaFisicaVO;

public class ServiziTest {

	@Test
	public void cambioPath()
	{
		System.out.print("cambioPath");
		
		String packageOld = "it.csi.pbandi.pbandisrv.integration.db.vo";
		String path="D:\\eclipse\\eclipse202009b\\workspace\\pbweb\\pbweb-jar\\src\\main\\java\\it\\csi\\pbandi\\pbweb\\pbandisrv\\integration\\db\\vo\\";
		String fileName = "PbandiDBancaVO.java";
		
		String newStr = "it.csi.pbandi.pbweb.pbandisrv."; 
		String oldStr = "it.csi.pbandi.pbandisrv.";
		
//		try {
//			
//			File folder = new File(path);
//			File[] listOfFiles = folder.listFiles();
//
//			for (int i = 0; i < listOfFiles.length; i++) {
//			  if (listOfFiles[i].isFile()) {
//			    System.out.println("File " + listOfFiles[i].getName());
//			    
//			    rimpiazza(path , listOfFiles[i].getName() , oldStr, newStr);
//			    
////			    if(i==3) break;
//			  } else if (listOfFiles[i].isDirectory()) {
//			    System.out.println("Directory " + listOfFiles[i].getName());
//			  }
//			}
//		} catch (Exception e) {
//	        System.out.println("Problem reading file.");
//	    }
	}
	
	private void rimpiazza(String path, String fileName,String oldStr, String newStr) {
		
		try {
			// input the file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader(path + fileName));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;

	        while ((line = file.readLine()) != null) {
	            inputBuffer.append(line);
	            inputBuffer.append('\n');
	        }
	        file.close();
	        String inputStr = inputBuffer.toString();

	        System.out.println(inputStr); // display the original file for debugging

	        inputStr = inputStr.replace( oldStr, newStr); 

//	        System.out.println("----------------------------------\n" + inputStr);

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream(fileName);
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}
	
	
//	RestTemplate restTemplate = new RestTemplate();
//	
//	@Test
//    public void testName()
//    {
//		System.out.print("ss");
////		Assert.assertSame("Name is not the same as expected: ", "Default Random Number Generator", randomGenerator.name());
//		
//		
//		String url = "http://127.0.0.1:8080/pbservizit/profilazione/soggetti/soggetto/uu";
//		//YourBean obj = restTemplate.getForObject(url, YourBean.class);
////		String obj = restTemplate.getForObject(url, String.class);
////		
////		System.out.print("obj="+obj);
//    }
//	
//	@Test
//    public void testReadSQL()
//    {
//	   try{
//	       BufferedReader bufferedReader = new BufferedReader( new FileReader("src\\test\\java\\it\\csi\\pbandi\\pbworkspace\\query.sql") );
//	        
//	       StringBuilder sb = new StringBuilder();
//	 	   String line;
//	 	   while ((line = bufferedReader.readLine()) != null)
//	 	   {
//	 	       sb.append(line);
//	 	   }
//	 	   String query = sb.toString();
//	 	   System.out.print("query="+query);
//	 	   
//	    }
//	    catch (FileNotFoundException e){
//	        e.printStackTrace();
//	    }
//	    catch (IOException e){
//	        e.printStackTrace();
//	    }
//	   
//    }
//	
//	@Test
//    public void tesServizio_getInfoUtente()
//    {
//		System.out.print("tesServizio_getInfoUtente");
//		
//		String url = "http://127.0.0.1:8080/pbworkspace/utente/infoUtente/";
//		
//		String cf = "pchmrc72a05h355f";
//		
////		double  t1 = System.currentTimeMillis();
////		for (int i = 0; i < 167768; i++) {
////			Math.random();
////		}
////		double  t2 = System.currentTimeMillis();
////		
////		double  d = t2-t1;
////		double  x = d/1000;
////		
////		System.out.print("t1="+t1 + ", t2="+t2+" , diff="+d + " ms, s=" + x);
//		
//		
//		
////		String obj = restTemplate.getForObject(url+cf, String.class);
////		System.out.print("obj="+obj);
//		
////		http://127.0.0.1:8080/pbworkspace/utente/funzionario/{identificativo_azienda}
//    }
//	
//	@Test
//    public void tesServizio_getFunzionarioByCodiceFiscale()
//    {
//		System.out.print("tesServizio_getFunzionarioByCodiceFiscale");
//		
//		String url = "http://127.0.0.1:8080/pbworkspace/utente/funzionario/";
//		
//		String idAz = "22";
//		
////		String obj = restTemplate.getForObject(url+idAz, String.class);
////		System.out.print("obj="+obj);
//		
//    }
}
