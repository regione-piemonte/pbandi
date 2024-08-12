package it.csi.pbandi.pbweb.integration.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.integration.dao.impl.SpesaValidataDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiSpesaValidataRequest;


@RunWith(PbwebJunitClassRunner.class)
public class SpesaValidataDAOImplTest extends TestBaseService {

	Long idUtente = new Long(21957);
	String idIride = "idIrideFinto";
	
	protected static Logger LOG = Logger.getLogger(SpesaValidataDAOImplTest.class);

	@Before()
	public void before() {
		LOG.info("[SpesaValidataDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[SpesaValidataDAOImplTest::after]START-STOP");
	}

	@Autowired
	SpesaValidataDAOImpl spesaValidataDAOImpl;

	@Test
	public void cercaDocumentiSpesaValidata() {
		/*
		String prf = "[SpesaValidataDAOImplTest::cercaDocumentiSpesaValidata] ";
    	LOG.info( prf + "START");
		
		try {
			
			CercaDocumentiSpesaValidataRequest req = new CercaDocumentiSpesaValidataRequest();
			req.setIdProgetto(new Long(19014));
			
			 ArrayList<DocumentoDiSpesa> lista = spesaValidataDAOImpl.cercaDocumentiSpesaValidata(req, idUtente, idIride);
			
			 LOG.info("\n\nlista.size = "+lista.size()+"\n\n");
			for (DocumentoDiSpesa d : lista)
				LOG.info("\n"+d.getDescrizioneTipologiaDocumento()+" - "+d.getNumeroDocumento()+" - "+d.getDescrizioneStato()+"\n");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
	 }
	
	@Test
	public void reportDettaglioDocumentoDiSpesa() {
		/*
		String prf = "[SpesaValidataDAOImplTest::reportDettaglioDocumentoDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			Long idProgetto = new Long(19014);
			EsitoLeggiFile file  = spesaValidataDAOImpl.reportDettaglioDocumentoDiSpesa(idProgetto, idUtente, idIride);
			
			LOG.info("\n\nbyteFile.size = "+file.getBytes().length);
			LOG.info("\n\nnome file = "+file.getNomeFile());
			
			FileOutputStream fileOuputStream = new FileOutputStream("C:\\temp\\"+file.getNomeFile());
			try {
				fileOuputStream.write(file.getBytes());
			} catch (IOException e) {
				LOG.error("IOException: ", e);
			}
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	 
}
