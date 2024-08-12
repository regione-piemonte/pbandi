package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
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
import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.EsitoRicercaFornitori;
import it.csi.pbandi.pbweb.dto.Fornitore;
import it.csi.pbandi.pbweb.dto.QualificaFormDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.integration.dao.impl.FornitoreDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificheRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;


@RunWith(PbwebJunitClassRunner.class)
public class FornitoreDAOImplTest extends TestBaseService {
	
	Long idUtente = new Long(21957);
	String idIride = "idIrideFinto";
	Long idSoggetto = new Long(2130091);

	protected static Logger LOG = Logger.getLogger(FornitoreDAOImplTest.class);

	@Before()
	public void before() {
		LOG.info("[FornitoreDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[FornitoreDAOImplTest::after]START-STOP");
	}

	@Autowired
	FornitoreDAOImpl fornitoreDAOImpl;
	
	@Test
	public void ricercaElencoFornitori() {
		/*
		String prf = "[FornitoreDAOImplTest::ricercaElencoFornitori] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			FornitoreDTO dto = new FornitoreDTO();
			dto.setIdTipoSoggetto(new Long(1));
			
			Long idSoggettoBeneficiario = new Long(1134);
			Long idProgetto = new Long(19268);
			
			EsitoRicercaFornitori esito = fornitoreDAOImpl.ricercaElencoFornitori(dto, idSoggettoBeneficiario, idProgetto, idUtente, idIride);
			
			LOG.info("\n\nEsito: "+esito.getEsito()+"  -  "+esito.getMessaggio());
			if (esito.getFornitori() != null) {
				List<Fornitore> lista = esito.getFornitori();
				LOG.info("\n\nFORNITORI TROVATI = "+lista.size()+"\n");
				for (Fornitore p : lista)
					LOG.info("\n\n"+p.toString());	
			}
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void associaAllegatiAFornitore() {
		/*
		String prf = "[FornitoreDAOImplTest::associaAllegatiAFornitore] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
						
			ArrayList<Long> idDocIndex1 = new ArrayList<Long>();
			idDocIndex1.add(new Long(230438));
			
			AssociaFilesRequest req1 = new AssociaFilesRequest();
			req1.setElencoIdDocumentoIndex(idDocIndex1);
			req1.setIdProgetto(new Long(19268));
			req1.setIdTarget(new Long(75052));		// id fornitore
			
			// --------------------------------------------------
			
			idUtente = 26300L;
			ArrayList<Long> idDocIndex2 = new ArrayList<Long>();
			idDocIndex2.add(new Long(129752));
			//idDocIndex2.add(new Long(230321));
			
			AssociaFilesRequest req2 = new AssociaFilesRequest();
			req2.setElencoIdDocumentoIndex(idDocIndex2);
			req2.setIdProgetto(new Long(19233));
			req2.setIdTarget(new Long(97111));		// id fornitore
			
			
			EsitoAssociaFilesDTO esito = fornitoreDAOImpl.associaAllegatiAFornitore(req2, idUtente);
			LOG.info(prf+esito.toString());		
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void salvaQualifiche() {
		/*
		String prf = "[FornitoreDAOImplTest::salvaQualifiche] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			QualificaFormDTO f1 = new QualificaFormDTO();
			f1.setIdFornitore(new Long(75052));
			f1.setIdQualifica(new Long(2));
			f1.setCostoOrario(new Double(10));
			f1.setNoteQualifica("nota 1");
			
			QualificaFormDTO f2 = new QualificaFormDTO();
			f2.setIdFornitore(new Long(75052));
			f2.setIdQualifica(new Long(12));
			f2.setCostoOrario(new Double(20));
			f2.setNoteQualifica("nota 2");
			
			ArrayList<QualificaFormDTO> listaQualificaFormDTO = new ArrayList<QualificaFormDTO>();
			listaQualificaFormDTO.add(f1);
			listaQualificaFormDTO.add(f2);
						
			SalvaQualificheRequest req = new SalvaQualificheRequest();
			req.setListaQualificaFormDTO(listaQualificaFormDTO);
						
			EsitoDTO esito = fornitoreDAOImpl.salvaQualifiche(req, idUtente, idIride);
			
			LOG.info("\n\nEsito: "+esito.getEsito()+"  -  "+esito.getMessaggio());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void testTransactionalJUnit() {
		/*
		String prf = "[FornitoreDAOImplTest::testTransactionalJUnit] ";		
		try {
			fornitoreDAOImpl.testTransactionalJUnit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
}	
