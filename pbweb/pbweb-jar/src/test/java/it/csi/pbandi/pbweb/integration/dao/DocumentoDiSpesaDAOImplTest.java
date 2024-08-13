/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.dto.ElencoDocumentiSpesaItem;
import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.FiltroRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.DocumentoIndexDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.FiltroRicercaDocumentiDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiPagamentoDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.EsitoRicercaDocumentiDiSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.PagamentoFormDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.impl.DocumentiDiProgettoDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.impl.DocumentoDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaPagamentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;


@RunWith(PbwebJunitClassRunner.class)
public class DocumentoDiSpesaDAOImplTest extends TestBaseService {
	
	Long idUtente = new Long(21957);
	String idIride = "idIrideFinto";
	Long idSoggetto = new Long(2130091);

	protected static Logger LOG = Logger.getLogger(DocumentoDiSpesaDAOImplTest.class);

	@Before()
	public void before() {
		LOG.info("[DocumentoDiSpesaDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[DocumentoDiSpesaDAOImplTest::after]START-STOP");
	}

	@Autowired
	DocumentoDiSpesaDAOImpl documentoDiSpesaDAOImpl;
	
	
	@Test
	public void associaAllegatiAPagamento() {
		/*
		String prf = "[DocumentoDiSpesaDAOImplTest::associaAllegatiAPagamento] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {			
			AssociaFilesRequest req = new AssociaFilesRequest();
			req.setElencoIdDocumentoIndex(new ArrayList<Long>());
			req.getElencoIdDocumentoIndex().add(new Long(230360));
			req.getElencoIdDocumentoIndex().add(new Long(230361));
			req.setIdTarget(new Long(795599));
			req.setIdProgetto(new Long(19268));
			// id entita = 70; id file = 159598
			
			EsitoAssociaFilesDTO esito = documentoDiSpesaDAOImpl.associaAllegatiAPagamento(req, idUtente);
			LOG.info(prf+esito.toString());			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void associaAllegatiADocSpesa() {
		/*
		String prf = "[DocumentoDiSpesaDAOImplTest::associaAllegatiADocSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {			
			AssociaFilesRequest req = new AssociaFilesRequest();
			req.setElencoIdDocumentoIndex(new ArrayList<Long>());
			req.getElencoIdDocumentoIndex().add(new Long(230360));
			req.getElencoIdDocumentoIndex().add(new Long(230361));
			req.setIdTarget(new Long(575100));
			req.setIdProgetto(new Long(19268));
			
			EsitoAssociaFilesDTO esito = documentoDiSpesaDAOImpl.associaAllegatiADocSpesa(req, idUtente);
			LOG.info(prf+esito.toString());			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void ricercaDocumentiDiSpesa() {
		/*
		String prf = "[DocumentoDiSpesaDAOImplTest::ricercaDocumentiDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {	
			
			String codiceRuolo = "BEN-MASTER";
			String codiceProgettoCorrente = "codProgettoCorrente";
			
			//Long idProgetto = new Long(19268);
			//Long idSoggettoBeneficiario = new Long(1134);
			
			// Politecnico torino
			Long idProgetto = new Long(19200);
			Long idSoggettoBeneficiario = new Long(17);
			
			FiltroRicercaDocumentiSpesa filtro = new FiltroRicercaDocumentiSpesa();
			filtro.setIdStato(new Long(0));
			filtro.setPartner("rbTipoCapofila");
			//filtro.setDocumentiDiSpesa("rbGestiti");
			filtro.setDocumentiDiSpesa("rbTutti");			
			
			EsitoRicercaDocumentiDiSpesa esito = documentoDiSpesaDAOImpl.ricercaDocumentiDiSpesa(idProgetto, codiceProgettoCorrente, idSoggettoBeneficiario, codiceRuolo, filtro, idUtente, idIride, idSoggetto);
			List<ElencoDocumentiSpesaItem> result = esito.getDocumenti();
			if (esito.getEsito()) {
				LOG.info(prf+"\n\nESITO: "+esito.getEsito()+"  -  "+esito.getMessaggio()+"  -  num doc trovati = "+esito.getDocumenti().size());
				for (ElencoDocumentiSpesaItem item : result) {
					LOG.info("\n");
					LOG.info(item.toString());
					LOG.info("\n");
				}
			} else {
				LOG.info(prf+"\n\nESITO: "+esito.getEsito()+"  -  "+esito.getMessaggio());
			}
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void associaPagamento() {
		/*
		String prf = "[DocumentoDiSpesaDAOImplTest::associaPagamento] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			
			Long idDocumentoDiSpesa = new Long(575112);
			Long idProgetto = new Long(19268);
			Long idBandoLinea = new Long(272);
			Boolean forzaOperazione = false;
			Boolean validazione = false;
			
			PagamentoFormDTO pagamentoFormDTO = new PagamentoFormDTO();
			pagamentoFormDTO.setIdPagamento(null);								// null = inserimento			
			pagamentoFormDTO.setIdModalitaPagamento(new Long(1));				// assegno					
			pagamentoFormDTO.setImportoPagamento(new Double(12));
			pagamentoFormDTO.setIdCausalePagamento(new Long(3));				// saldo
			pagamentoFormDTO.setRifPagamento("rif finto");
			
			pagamentoFormDTO.setDtPagamento(DateUtil.getDate(DateUtil.getData()));
			// usare questo per generare un errore.
			//pagamentoFormDTO.setDtPagamento(DateUtil.getDataOdierna());
			
			AssociaPagamentoRequest req = new AssociaPagamentoRequest();
			req.setIdDocumentoDiSpesa(idDocumentoDiSpesa);
			req.setIdProgetto(idProgetto);
			req.setIdBandoLinea(idBandoLinea);
			req.setForzaOperazione(forzaOperazione);
			req.setValidazione(validazione);
			req.setPagamentoFormDTO(pagamentoFormDTO);
				
			EsitoDTO esito = documentoDiSpesaDAOImpl.associaPagamento(req, idUtente, idIride);
			LOG.info(esito.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void pagamentiAssociati() {
		/*
		String prf = "[DocumentoDiSpesaDAOImplTest::pagamentiAssociati] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			//PagamentiAssociatiRequest req = new PagamentiAssociatiRequest();			
			//req.setIdDocumentoDiSpesa(new Long(575112));
			//req.setTipoInvioDocumentoDiSpesa("D");
			//req.setDescBreveStatoDocSpesa(Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE);
			//req.setTipoOperazioneDocSpesa(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA);
			//req.setIdProgetto(new Long(19268));
			//req.setIdBandoLinea(new Long(272));
			//req.setCodiceRuolo(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER);
			//req.setValidazione(false);
			
			PagamentiAssociatiRequest req = new PagamentiAssociatiRequest();			
			req.setIdDocumentoDiSpesa(new Long(575104));
			req.setTipoInvioDocumentoDiSpesa("D");
			req.setDescBreveStatoDocSpesa(Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE);
			//req.setTipoOperazioneDocSpesa(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA);
			req.setTipoOperazioneDocSpesa(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO);
			req.setIdProgetto(new Long(19233));
			req.setIdBandoLinea(new Long(271));
			req.setCodiceRuolo(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER);
			req.setValidazione(false);
			
			List<DocumentoDiPagamentoDTO> lista = documentoDiSpesaDAOImpl.pagamentiAssociati(req, idUtente, idIride, idSoggetto);
			
			LOG.info("\n\nPAGAMENTI TROVATI = "+lista.size()+"\n");
			for (DocumentoDiPagamentoDTO p : lista)
				LOG.info("\n\n"+p.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	
	
	
	// ******************************************************
	//		DOCUMENTI DI PROGETTO
	// ******************************************************
	
	@Autowired
	DocumentiDiProgettoDAOImpl documentiDiProgettoDAOImpl;
	
	@Test
	public void cercaDocumenti() {
		/*
		String prf = "[DocumentoDiSpesaDAOImplTest::cercaDocumenti] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {		

			FiltroRicercaDocumentiDTO f = new FiltroRicercaDocumentiDTO();
			f.setIdProgetto(17999L);
			f.setIdSoggetto(2130090L);
			f.setDocInviati(false);
			f.setDocInFirma(false);
			f.setIdTipoDocumentoIndex(1L);
			f.setCodiceRuolo("BEN-MASTER");
			f.setIdSoggettoBeneficiario(3550L);

			ArrayList<DocumentoIndexDTO> lista = documentiDiProgettoDAOImpl.cercaDocumenti(f, idUtente, idIride);
			
			LOG.info("\n\nDOCUMENTI TROVATI = "+lista.size()+"\n");
			for (DocumentoIndexDTO d : lista)
				LOG.info("prot = "+d.getProtocollo());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
}	
