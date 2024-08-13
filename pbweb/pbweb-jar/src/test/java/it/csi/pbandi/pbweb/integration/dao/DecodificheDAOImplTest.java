/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.integration.dao.impl.DecodificheDAOImpl;


@RunWith(PbwebJunitClassRunner.class)
public class DecodificheDAOImplTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(DecodificheDAOImplTest.class);

	@Before()
	public void before() {
		LOG.info("[AttivitaDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[AttivitaDAOImplTest::after]START-STOP");
	}

	@Autowired
	DecodificheDAOImpl decodificheDAOImpl;

	@Test
	public void attivitaComboTest() {
		/*
		String prf = "[DecodificheDAOImplTest::attivitaComboTest] ";
    	LOG.info( prf + "START");
    	
    	long idProgetto = 7L;
		long idDocumentoDiSpesa = 464L; // 461, 462
		HttpServletRequest request = null;
		
		try {
			decodificheDAOImpl.attivitaCombo(request);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
	 }
	
	@Test
	public void tipologieFornitore() {
		/*
		String prf = "[DecodificheDAOImplTest::tipologieFornitore] ";
    	LOG.info(prf + "START");
    	
		try {
			
			List<DecodificaDTO> lista = decodificheDAOImpl.ottieniTipologieFornitore();
			for (DecodificaDTO dto : lista) {
				LOG.info( prf+dto.getDescrizione());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
	 }
	
	@Test
	public void statiDocumentoDiSpesa() {
		/*
		String prf = "[DecodificheDAOImplTest::statiDocumentoDiSpesa] ";
    	LOG.info(prf + "START");
    	
		try {
			
			List<DecodificaDTO> lista = decodificheDAOImpl.statiDocumentoDiSpesa();
			for (DecodificaDTO dto : lista) {
				LOG.info( prf+dto.getDescrizione());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		 */	
	 }
	 
	@Test
	public void popolaRBandoIndicatori() {
		/*
		String prf = "[DecodificheDAOImplTest::popolaRBandoIndicatori] ";
    	LOG.info(prf + "START");
    	
		try {
			
			// Cartella contenente i file da elaborare.
			final File folder = new File("C:/indicatori");
			
			// Cartella in cui verrà scritto il file generato in output.
			PrintWriter fileOutput = new PrintWriter("C:/indicatoriOutput/updateRBandoIndicatori.sql");			
			
			int fileElaborati = 0;
			int fileErrati = 0;	
			
			// Scorre i file presenti nella cartella.
			fileOutput.println("SET DEFINE OFF;");	// evita che &, presente nel testo dei file, sia interpretata come una variabile da sqldeveloper.
			for (File f : folder.listFiles()) {
				
				String nomeFile = f.getName();
				fileElaborati = fileElaborati + 1;
				
				LOG.info("");
				LOG.info(nomeFile);
				
				// Estrae informazioni dal nome del file corrente (es: 191_101_Fin.html)
				String[] parts = nomeFile.split("_");
				String idBando = parts[0];
				String idIndicatore = parts[1];
				String tipoFile = parts[2];
				String campoDaModificare = (tipoFile.indexOf("Fin") == -1 ? "INFO_INIZIALE" : "INFO_FINALE");				
				LOG.info("idBando = "+idBando+"; idIndicatore = "+idIndicatore+"; tipoFile = "+tipoFile+"; campoDaModificare = "+campoDaModificare);
				
				// Legge il contenuto del file corrente.
				FileReader fr = new FileReader("C:/indicatori/"+nomeFile);
				BufferedReader br = new BufferedReader(fr);				
				StringBuilder sb = new StringBuilder();
	            String val;
	            while ((val = br.readLine()) != null) {
	                sb.append(val);
	            }	            
	            br.close();
	            
	            String html = sb.toString();
	            //LOG.info(html);
	            
	            int inizio = html.indexOf("title");
	            if (inizio == -1) {
	            	LOG.info("title non trovato.");
	            	fileErrati = fileErrati + 1;
	            	continue;
	            } else {
	            	inizio = inizio + 7; 
	            }
	            
	            int fine = html.indexOf("><i class");
	            if (fine == -1) {
	            	LOG.info("i class non trovato.");
	            	fileErrati = fileErrati + 1;
	            	continue;
	            } else {
	            	fine = fine - 1; 
	            }
	            
	            // Estrae dal contenuto del file la stringa assegnata a TITLE.
	            String title = html.substring(inizio, fine);
	            title = title.replaceAll("'", "''");
	            
	            //LOG.info(title);
	            title = title.replaceAll("�", "à");		// à nei file	      
	            
	            //LOG.info(title);
	            	            
	            String update ="UPDATE PBANDI_R_BANDO_INDICATORI SET "+campoDaModificare+" = '"+title+ "' WHERE ID_BANDO = "+idBando+" AND ID_INDICATORI = "+idIndicatore+";";
	            LOG.info(update);
	            
	            // Aggiunge l'update al file generato in output.
	            fileOutput.println(update);
	            
				// su 191_101_Fin.html dBuilder.parse() provoca l'errore
				// [Fatal Error] 191_101_Fin.html:1:54: Il valore dell'attributo "title" associato a un tipo di elemento "a" non deve essere contenere il carattere '<'.
	    		//DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    		//DocumentBuilder dBuilder;
	    		//dBuilder = dbFactory.newDocumentBuilder();
	    		//LOG.info("carica file");
	    		//Document doc = dBuilder.parse(f);
	    		//LOG.info("normalizza");
	    		//doc.getDocumentElement().normalize();
	    		//LOG.info("legge <a>");
	    		//String a = getNodeValue("a" , doc);
	    		//LOG.info(a);
	    		
			}

			fileOutput.println("SET DEFINE ON;");
			fileOutput.close();
			
			LOG.info("file elaborati = " + fileElaborati);
			LOG.info("file errati = " + fileErrati);
						
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		LOG.info(prf + "END");
		*/
	 }
	
	private String getNodeValue(String tagName, Document doc) throws Exception{
		try {
			NodeList nodeList = doc.getElementsByTagName(tagName).item(0).getChildNodes();
			Node node =(Node) nodeList.item(0);
			return node.getNodeValue();
		} catch (Exception e) {
			return "";
		}
	}
	 
}
