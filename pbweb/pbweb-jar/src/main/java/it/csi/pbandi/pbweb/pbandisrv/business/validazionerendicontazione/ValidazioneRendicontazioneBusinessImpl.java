package it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.checklisthtml.ChecklistHtmlBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.CheckListManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.AllegatoIntegrazioneSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DettaglioOperazioneAutomatica;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EnteDestinatarioComunicazioniDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoCheckOperazioneAutomatica;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoControlloDocumenti;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoDettaglioDocumento;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneAutomatica;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneDocumentoDiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneModifica;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoReportDettaglioDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidaTutti;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidazioneRendicontazione;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.MessaggioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.NotaDiCreditoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneNoteDiCreditoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentidispesa.GestioneDocumentiDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.exception.validazionerendicontazione.ValidazioneRendicontazioneException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiContoEconomicoDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiFornitoriDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiValidazioneRendicontazioneDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioOperazioneAutomaticaValidazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentiDiSpesaPerChiusuraValidazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentiDiSpesaPerValidazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaPerControlloUnivocitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDestinatarioComunicazioniVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.NotaDiCreditoConVociDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ReportDettaglioDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.FornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.IntegrazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoDocumentoDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoValidazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.VoceDiSpesaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.VoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagQuotParteDocSpVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagamentoDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTIntegrazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.validazionerendicontazione.ValidazioneRendicontazioneSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;
import it.doqui.index.ecmengine.dto.Node;


public class ValidazioneRendicontazioneBusinessImpl extends BusinessImpl
implements ValidazioneRendicontazioneSrv {
	
	private static final BidiMap DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO = new TreeBidiMap();
	static {
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"codiceVisualizzato", "codiceProgetto");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"dtEmissioneDocumento", "dataDocumentoDiSpesa");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"descDocumento", "descrizioneDocumentoDiSpesa");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"descTipoDocumentoSpesa", "descTipoDocumentoDiSpesa");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"descBreveTipoDocSpesa", "descBreveTipoDocumentoDiSpesa");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"idStatoDocumentoSpesa", "idStatoDocumentoDiSpesa");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"idTipoDocumentoSpesa", "idTipoDocumentoDiSpesa");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"idTipoSoggetto", "idTipoFornitore");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"importoIvaCosto", "importoIvaACosto");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"importoRendicontazione", "importoRendicontabile");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"importoTotaleDocumento", "importoTotaleDocumentoIvato");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"tipoInvio", "tipoInvio");
		DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO.put(
				"idAppalto", "idAppalto");
	}

	private static final String NOTIFICA_VALIDAZIONE="NotificaValidazione";
	private static final String NOTIFICA_VALIDAZIONEDISPESAFINALE="NotificaValidazioneDiSpesaFinale";

	@Autowired
	private GenericDAO genericDAO;	
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	@Autowired
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;

	@Autowired
	private CheckListManager checkListManager;
	
	@Autowired
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private it.csi.pbandi.pbweb.business.manager.DocumentoManager documentoWebManager;
	
//	@Autowired
//	private it.csi.pbandi.pbservizit.business.manager.DocumentiFSManager documentoSFManager;
	
	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	
	private IndexDAO indexDAO;
	
	@Autowired
	private NeofluxSrv neofluxBusiness;
	
	private PbandiContoEconomicoDAOImpl pbandiContoEconomicoDAO;
	
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	
	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	
	@Autowired
	private PbandiFornitoriDAOImpl pbandiFornitoriDAO;
	
	@Autowired
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	
	@Autowired
	private PbandiValidazioneRendicontazioneDAOImpl pbandiValidazioneRendicontazioneDAO;
	
	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private SoggettoManager soggettoManager;
	
	@Autowired
	private GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusiness;
	
	@Autowired
	protected ChecklistHtmlBusinessImpl checklistHtmlBusinessImpl;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.gestionepagamenti.GestionePagamentiBusinessImpl gestionePagamentiBusinessImpl;
	
	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}
	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}
	public CheckListManager getCheckListManager() {
		return checkListManager;
	}
	public void setCheckListManager(CheckListManager checkListManager) {
		this.checkListManager = checkListManager;
	}
	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}
	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}
	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}
	public void setDocumentoDiSpesaManager(DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}
	public IndexDAO getIndexDAO() {
		return indexDAO;
	}
	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}
	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}
	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}
	public PbandiContoEconomicoDAOImpl getPbandiContoEconomicoDAO() {
		return pbandiContoEconomicoDAO;
	}
	public void setPbandiContoEconomicoDAO(PbandiContoEconomicoDAOImpl pbandiContoEconomicoDAO) {
		this.pbandiContoEconomicoDAO = pbandiContoEconomicoDAO;
	}
	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}
	public void setPbandiDichiarazioneDiSpesaDAO(PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}
	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}
	public void setPbandiDocumentiDiSpesaDAO(PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}
	public PbandiFornitoriDAOImpl getPbandiFornitoriDAO() {
		return pbandiFornitoriDAO;
	}
	public void setPbandiFornitoriDAO(PbandiFornitoriDAOImpl pbandiFornitoriDAO) {
		this.pbandiFornitoriDAO = pbandiFornitoriDAO;
	}
	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}
	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}
	public PbandiValidazioneRendicontazioneDAOImpl getPbandiValidazioneRendicontazioneDAO() {
		return pbandiValidazioneRendicontazioneDAO;
	}
	public void setPbandiValidazioneRendicontazioneDAO(
			PbandiValidazioneRendicontazioneDAOImpl pbandiValidazioneRendicontazioneDAO) {
		this.pbandiValidazioneRendicontazioneDAO = pbandiValidazioneRendicontazioneDAO;
	}
	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}
	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}
	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}
	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}
	public GestioneDatiGeneraliBusinessImpl getGestioneDatiGeneraliBusiness() {
		return gestioneDatiGeneraliBusiness;
	}
	public void setGestioneDatiGeneraliBusiness(GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusiness) {
		this.gestioneDatiGeneraliBusiness = gestioneDatiGeneraliBusiness;
	}
	
	// Restituisce le richieste di integrazione e i relativi allegati appartenenti ad un dato progetto.
	// Sono usati per popolare la tabella nella finestra elencoRichiesteIntegrazioneDs.
	public IntegrazioneSpesaDTO[] findIntegrazioniSpesaByIdProgetto(Long idUtente, String identitaDigitale, Long idProgetto)
	throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",	"idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idProgetto);
				
		IntegrazioneSpesaVO vo = new IntegrazioneSpesaVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));	
		vo.setDescendentOrder("dataRichiesta");
		
		// Legge le richieste APERTE ordinate dalla pi� recente.
		FilterCondition filterAperte = new FilterCondition<IntegrazioneSpesaVO>(vo);
		NullCondition<IntegrazioneSpesaVO> nullCondAperte = new NullCondition<IntegrazioneSpesaVO>(IntegrazioneSpesaVO.class,"dataInvio");
		AndCondition andCondAperte = new AndCondition<IntegrazioneSpesaVO>(filterAperte,nullCondAperte);		
		List<IntegrazioneSpesaVO> lista = genericDAO.findListWhere(andCondAperte);
		
		// Legge le richieste CHIUSE ordinate dalla pi� recente.
		FilterCondition filterChiuse = new FilterCondition<IntegrazioneSpesaVO>(vo);
		NullCondition<IntegrazioneSpesaVO> nullCondChiuse = new NullCondition<IntegrazioneSpesaVO>(IntegrazioneSpesaVO.class,"dataInvio");
		NotCondition<IntegrazioneSpesaVO> notNullCondChiuse = new NotCondition<IntegrazioneSpesaVO>(nullCondChiuse);
		AndCondition andCondChiuse = new AndCondition<IntegrazioneSpesaVO>(filterChiuse,notNullCondChiuse);		
		List<IntegrazioneSpesaVO> listaChiuse = genericDAO.findListWhere(andCondChiuse);
		
		// Accodo le chiuse alle aperte.
		for (IntegrazioneSpesaVO i : listaChiuse) {
			lista.add(i);
		}
		
		IntegrazioneSpesaDTO[] integrazioneSpesaDTO = new IntegrazioneSpesaDTO[lista.size()];
		beanUtil.valueCopy(lista.toArray(), integrazioneSpesaDTO);
		
		PbandiCEntitaVO entita = new PbandiCEntitaVO();
		entita.setNomeEntita(NOME_ENTITA_PBANDI_T_INTEGRAZIONE_SPESA);
		entita = genericDAO.findSingleWhere(entita);

		// Per ogni integrazione trova gli eventuali allegati associati.
		for (IntegrazioneSpesaDTO is : integrazioneSpesaDTO) {
			List<ArchivioFileVO> files = pbandiArchivioFileDAOImpl.getFiles(
					new BigDecimal(is.getIdIntegrazioneSpesa()), entita.getIdEntita());
			AllegatoIntegrazioneSpesaDTO[] allegati = new AllegatoIntegrazioneSpesaDTO[files.size()];
			int i = 0;
			for (ArchivioFileVO f : files) {	
				AllegatoIntegrazioneSpesaDTO a = new AllegatoIntegrazioneSpesaDTO();
				a.setIdIntegrazioneSpesa(is.getIdIntegrazioneSpesa().longValue());
				a.setIdDocumentoIndex(f.getIdDocumentoIndex().longValue());
				a.setNomeFile(f.getNomeFile());
				a.setFlagEntita(f.getFlagEntita());		// Jira PBANDI-2815.
				allegati[i] = a;
				i++;
			}
			is.setAllegati(allegati);
		}
		
		return integrazioneSpesaDTO;		
	}

	// Se idIntegrazione nullo, è l'istruttore che crea la richiesta di integrazione (inserimento).
	// Altrimenti è il beneficiario che invia l'integrazione all'istruttore (modifica). 
	public Long salvaIntegrazioneSpesa(Long idUtente, String identitaDigitale, IntegrazioneSpesaDTO dto)
		throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {
					
		String[] nameParameter = { "idUtente", "identitaDigitale",	"dto" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,dto);
		
		PbandiTIntegrazioneSpesaVO vo = beanUtil.transform(dto, PbandiTIntegrazioneSpesaVO.class);
		
		try {
						
			if (vo.getIdIntegrazioneSpesa() == null) {
				// L'istruttore sta inserendo una nuova richiesta di integrazione.
				logger.info("salvaIntegrazioneSpesa(): inserimento.");
				vo = genericDAO.insert(vo);
				
				// Recupera la DS
				PbandiTDichiarazioneSpesaVO ds = new PbandiTDichiarazioneSpesaVO();
				ds.setIdDichiarazioneSpesa(vo.getIdDichiarazioneSpesa());
				ds = genericDAO.findSingleWhere(ds);
				
				// Invia una notifica al beneficiario.
				List<MetaDataVO>metaDatas = creaParametriNotificaRespingi(vo, ds);
				
				logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
				genericDAO.callProcedure().putNotificationMetadata(metaDatas);
				
				logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
				String descrBreveTemplateNotifica=Notification.NOTIFICA_RICHIESTA_INTEGRAZIONE_DICHIARAZIONE;
				genericDAO.callProcedure().sendNotificationMessage(ds.getIdProgetto(),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente);
				
			} else {
				
				// Il beneficiario sta chiudendo la richiesta di integrazione.
				logger.info("salvaIntegrazioneSpesa(): modifica per id "+vo.getIdIntegrazioneSpesa());
				genericDAO.update(vo);
				
				// Recupera l'integrazione per avere l'id della dichiarazione.
				PbandiTIntegrazioneSpesaVO integrazione = new PbandiTIntegrazioneSpesaVO();
				integrazione.setIdIntegrazioneSpesa(vo.getIdIntegrazioneSpesa());
				integrazione = genericDAO.findSingleWhere(integrazione);
				
				// Recupera la DS
				PbandiTDichiarazioneSpesaVO ds = new PbandiTDichiarazioneSpesaVO();
				ds.setIdDichiarazioneSpesa(integrazione.getIdDichiarazioneSpesa());
				ds = genericDAO.findSingleWhere(ds);
				
				// Invia una notifica all'istruttore.
				List<MetaDataVO>metaDatas = creaParametriNotificaInvia(integrazione, ds);
				
				logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
				genericDAO.callProcedure().putNotificationMetadata(metaDatas);
				
				logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
				String descrBreveTemplateNotifica=Notification.NOTIFICA_INTEGRAZIONE_DICHIARAZIONE;
				genericDAO.callProcedure().sendNotificationMessage(ds.getIdProgetto(),descrBreveTemplateNotifica,Notification.ISTRUTTORE,idUtente);
				
			}
		} catch (Exception e) {
			logger.error("salvaIntegrazioneSpesa(): si è verificato l'errore: "+e);
			throw new ValidazioneRendicontazioneException("Errore durante il salvataggio dell'integrazione di spesa.", e);
		}
		
		return vo.getIdIntegrazioneSpesa().longValue();
	}
	
	private List<MetaDataVO> creaParametriNotificaRespingi(PbandiTIntegrazioneSpesaVO vo, PbandiTDichiarazioneSpesaVO ds) {
		
		List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
		
		MetaDataVO metadata1 = new MetaDataVO(); 
		metadata1.setNome(Notification.DATA_RICHIESTA_INTEGRAZ_DIC);
		metadata1.setValore(DateUtil.getDate(vo.getDataRichiesta()));
		metaDatas.add(metadata1);
		
		MetaDataVO metadata2 = new MetaDataVO(); 
		metadata2.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
		metadata2.setValore(vo.getIdDichiarazioneSpesa().toString());
		metaDatas.add(metadata2);
		
		MetaDataVO metadata3 = new MetaDataVO(); 
		metadata3.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
		metadata3.setValore(DateUtil.getDate(ds.getDtDichiarazione()));
		metaDatas.add(metadata3);
		
		MetaDataVO metadata4 = new MetaDataVO(); 
		metadata4.setNome(Notification.NOTE_RICHIESTA_INTEGRAZ_DIC);
		metadata4.setValore(vo.getDescrizione());
		metaDatas.add(metadata4);
		
		return metaDatas;
	}
	

	private List<MetaDataVO> creaParametriNotificaInvia(PbandiTIntegrazioneSpesaVO vo, PbandiTDichiarazioneSpesaVO ds) {
		
		List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
		
		MetaDataVO metadata1 = new MetaDataVO(); 
		metadata1.setNome(Notification.DATA_INTEGRAZ_DIC);
		metadata1.setValore(DateUtil.getDate(vo.getDataInvio()));
		metaDatas.add(metadata1);
		
		MetaDataVO metadata2 = new MetaDataVO(); 
		metadata2.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
		metadata2.setValore(vo.getIdDichiarazioneSpesa().toString());
		metaDatas.add(metadata2);
		
		MetaDataVO metadata3 = new MetaDataVO(); 
		metadata3.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
		metadata3.setValore(DateUtil.getDate(ds.getDtDichiarazione()));
		metaDatas.add(metadata3);
		
		return metaDatas;
	}
	
	public EsitoValidazioneRendicontazione updateDocumentoEVociDiSpesa(
			Long idUtente, String identitaDigitale,
			ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
	throws CSIException, SystemException, UnrecoverableException,
	ValidazioneRendicontazioneException {

		try {

			String[] nameParameter = { "idUtente", "identitaDigitale","validazioneRendicontazioneDTO" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, validazioneRendicontazioneDTO);

			PagamentoDTO[] pagamenti = validazioneRendicontazioneDTO.getPagamenti();
			ValidatorInput.verifyArrayNotEmpty("pagamenti", pagamenti);

			EsitoValidazioneRendicontazione esito = new EsitoValidazioneRendicontazione();
			Double totaleAssociato = new Double(0);
			Double totaleValidato = new Double(0);
			for (PagamentoDTO pagamentoDTO : pagamenti) {

				VoceDiSpesaDTO[] vociDiSpesa = null;

				vociDiSpesa = pagamentoDTO.getVociDiSpesa();

				if (vociDiSpesa != null) {
					logger.warn("\n\n\nvociDiSpesa for pagamento: "+vociDiSpesa.length);
					VoceDiSpesaPagamentoVO[] vociDiSpesaPagamentoVO = new VoceDiSpesaPagamentoVO[vociDiSpesa.length];
					getBeanUtil().valueCopy(vociDiSpesa,vociDiSpesaPagamentoVO);
					for (VoceDiSpesaPagamentoVO voceDiSpesaPagamentoVO : vociDiSpesaPagamentoVO) {
						try {
							PbandiRPagQuotParteDocSpVO filter = new PbandiRPagQuotParteDocSpVO();
							filter.setIdQuotaParteDocSpesa(new BigDecimal(voceDiSpesaPagamentoVO.getIdQuotaParte()));
							filter.setIdDichiarazioneSpesa(new BigDecimal(validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa()));
							logger.warn("\nvoceDiSpesaPagamentoVO.getIdDichiarazioneDiSpesa(): "+voceDiSpesaPagamentoVO.getIdDichiarazioneSpesa()
									+"\nvoceDiSpesaPagamentoVO.getIdQuotaParte(): "+voceDiSpesaPagamentoVO.getIdQuotaParte()
									+"\nvoceDiSpesaPagamentoVO.getImportoValidato(): "+voceDiSpesaPagamentoVO.getImportoValidato()
									+"\nvoceDiSpesaPagamentoVO.getIdPagamento(): "+voceDiSpesaPagamentoVO.getIdPagamento());
							List<PbandiRPagQuotParteDocSpVO> rQuoteDb = genericDAO.findListWhere(filter);
							// nel caso di più quote parte per la stessa voce di spesa, devo rispatarrare
							if(rQuoteDb.size()>1){
								rispatarramento(idUtente, totaleAssociato,voceDiSpesaPagamentoVO,rQuoteDb);
							} else if(rQuoteDb.size()==1){
								PbandiRPagQuotParteDocSpVO pbandiRPagQuotParteDocSpVO =	rQuoteDb.get(0);
								pbandiRPagQuotParteDocSpVO.setImportoValidato(new BigDecimal(voceDiSpesaPagamentoVO.getImportoValidato()));
								pbandiRPagQuotParteDocSpVO.setIdUtenteAgg(new BigDecimal(idUtente));
								genericDAO.update(pbandiRPagQuotParteDocSpVO);
								if(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato()!=null)
									totaleAssociato = NumberUtil.sum(totaleAssociato, pbandiRPagQuotParteDocSpVO.getImportoQuietanzato().doubleValue());
							}

							totaleValidato = NumberUtil.sum(totaleValidato, voceDiSpesaPagamentoVO.getImportoValidato());
							totaleAssociato = NumberUtil.sum(totaleAssociato, voceDiSpesaPagamentoVO.getImportoAssociato());

						} catch (Exception e) {
							logger.error(
									"Errore nella update di PBANDI_R_PAG_QUOT_PARTE_DOC_SP ",
									e);
							throw new ValidazioneRendicontazioneException(ERRORE_SERVER);
						}	
					}

				}

			}

			/*
			 * Determino lo stato del documento
			 * - VALIDATO se totaleValidato = totaleAssociato
			 * - NON VALIDATO se totaleValidato = 0
			 * - PARZIALMENTE VALIDATO SEI totaleValidato > 0 AND totaleValidato < totaleAssociato
			 */

			String descBreveStatoValidazioneDocumento = null;
			logger.warn("\ntotaleValidato "+totaleValidato+" ,totaleAssociato: "+totaleAssociato );
			if (NumberUtil.compare(totaleValidato, totaleAssociato) == 0) {
				/*
				 * VALIDATO
				 */
				descBreveStatoValidazioneDocumento = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO;

			} else if (NumberUtil.compare(totaleValidato, new Double(0.0)) > 0 && NumberUtil.compare(totaleValidato, totaleAssociato) < 0) {
				/*
				 * PARZIALMENTE VALIDATO
				 */
				descBreveStatoValidazioneDocumento = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO;
			} else if (NumberUtil.compare(totaleValidato, new Double(0.0)) == 0) {
				/*
				 * NON VALIDATO
				 */
				descBreveStatoValidazioneDocumento = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO;
			}

			try {
				updateStatoValidazioneDocumento(descBreveStatoValidazioneDocumento, validazioneRendicontazioneDTO.getIdDocumentoDiSpesa(), idUtente, validazioneRendicontazioneDTO.getIdProgetto());

				/*
				 * Aggiorno lo stato documento valid anche per le note di
				 * credito associate al documento progetto
				 */
				List<DocumentoDiSpesaProgettoVO> notecredito = documentoDiSpesaManager.findNoteCreditoFattura(validazioneRendicontazioneDTO.getIdProgetto(), validazioneRendicontazioneDTO.getIdDocumentoDiSpesa());
				for (DocumentoDiSpesaProgettoVO nota : notecredito) {
					updateStatoValidazioneDocumento(descBreveStatoValidazioneDocumento, NumberUtil.toLong(nota.getIdDocumentoDiSpesa()), idUtente, validazioneRendicontazioneDTO.getIdProgetto());
				}

			} catch (Exception e) {
				logger.error(
						"\n\n\nErrore nella update di PBANDI_R_DOC_SPESA_PROGETTO ",
						e);
				throw new ValidazioneRendicontazioneException(
						ERRORE_SERVER);
			}
			esito.setMessage(STATO_DOCUMENTO_DICHIARAZIONE_AGGIORNATO);
			esito.setEsito(true);
			return esito;
		} catch (Exception e) {
			logger.error("Errore durante updateDocumentoEVociDiSpesa",e);
			if (e instanceof ValidazioneRendicontazioneException)
				throw (ValidazioneRendicontazioneException)e;
			else {
				throw new ValidazioneRendicontazioneException(
						ERRORE_SERVER);
			}
		}  
	}



	private void rispatarramento(Long idUtente, Double totaleAssociato,
			VoceDiSpesaPagamentoVO voceDiSpesaPagamentoVO,
			List<PbandiRPagQuotParteDocSpVO> rQuoteDb) throws Exception {
		double importoValidato = voceDiSpesaPagamentoVO.getImportoValidato();
		double numQuoteParte=rQuoteDb.size();
		logger.warn("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nimportoValidato da rispatarrare: "+importoValidato+" , quote da rispatarrare: "+numQuoteParte);
		double importoQuotaParteDaSanare= importoValidato/numQuoteParte;
		logger.warn("importoQuotaParteDaSanare: "+importoQuotaParteDaSanare);
		int decimalPlace = 2;
		BigDecimal importoQuotaParte = new BigDecimal(importoQuotaParteDaSanare);
		importoQuotaParte = importoQuotaParte.setScale(decimalPlace, BigDecimal.ROUND_DOWN);
		logger.warn("importo da rispatarrare sulla quota: "+importoQuotaParte);
		double importoSenzaResiduo=importoQuotaParte.doubleValue()*numQuoteParte;
		logger.warn("importo senza residuo da spatarrare sulle quote : "+importoSenzaResiduo);
		double rest= NumberUtil.subtract(importoValidato,new BigDecimal(importoSenzaResiduo).doubleValue());
		logger.warn("importo residuo da aggiungere a una quota : "+rest);
		BigDecimal quotaMaggiorata=new BigDecimal(rest+importoQuotaParte.doubleValue());
		logger.warn("quotaMaggiorata: "+quotaMaggiorata.doubleValue());

		//boolean restAssigned=false;

		for (PbandiRPagQuotParteDocSpVO pbandiRPagQuotParteDocSpVO : rQuoteDb) {
			if(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato()!=null && 
					(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato().doubleValue()>=importoValidato)){
				//metto tutto sulla prima,le altre si beccano 0
				logger.info(" quota parte > di importo validato, setto importoValidato: "+importoValidato);
				pbandiRPagQuotParteDocSpVO.setImportoValidato(BigDecimal.valueOf(importoValidato));
				importoValidato=0;
			}else{

				//importoValidato=NumberUtil.subtract(totaleAssociato, totaleAssociato);
				//pbandiRPagQuotParteDocSpVO.setImportoValidato(BigDecimal.valueOf(importoValidato));
				logger.info(" quota parte < di importo validato "+importoValidato+" , tengo il valore che aveva pbandiRPagQuotParteDocSpVO.getImportoQuietanzato() : "+pbandiRPagQuotParteDocSpVO.getImportoQuietanzato());
				pbandiRPagQuotParteDocSpVO.setImportoValidato(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato());
				importoValidato = NumberUtil.subtract(importoValidato , pbandiRPagQuotParteDocSpVO.getImportoQuietanzato().doubleValue() );
			}
			pbandiRPagQuotParteDocSpVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(pbandiRPagQuotParteDocSpVO);
			if(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato()!=null)
				totaleAssociato = NumberUtil.sum(totaleAssociato, pbandiRPagQuotParteDocSpVO.getImportoQuietanzato().doubleValue());
		}

	}



	public ValidazioneRendicontazioneDTO findPagamentiEVociDiSpesa(
			Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, ValidazioneRendicontazioneException {


		ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO = new ValidazioneRendicontazioneDTO();

		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoDiSpesa", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idDocumentoDiSpesa, idProgetto);

		validazioneRendicontazioneDTO.setIdDocumentoDiSpesa(idDocumentoDiSpesa);

		// 1) cercare i pagamenti
		List<PagamentoValidazioneVO> pagamentiAssociatiVO = pbandiValidazioneRendicontazioneDAO.findPagamentiAssociati(idDocumentoDiSpesa, null);
		logger.info("Found " + pagamentiAssociatiVO.size()+ " pagamenti associated");
		PagamentoDTO[] pagamentiAssociatiDTO = new PagamentoDTO[pagamentiAssociatiVO.size()];
		getBeanUtil().valueCopy(pagamentiAssociatiVO.toArray(),pagamentiAssociatiDTO);

		// 2) per ogni pagamento cercare le voci di spesa correlate
		// e settare isSospeso e isRespinto in base allo stato
		VoceDiSpesaDTO[] vociDiSpesaDTO = null;
		List<VoceDiSpesaPagamentoVO> vociDiSpesaVO = null;
		for (int i = 0; i < pagamentiAssociatiDTO.length; i++) {
			vociDiSpesaVO = pbandiValidazioneRendicontazioneDAO
			.findVociDiSpesaAssociateAPagamento(pagamentiAssociatiDTO[i].getIdPagamento());
			vociDiSpesaDTO = new VoceDiSpesaDTO[vociDiSpesaVO.size()];
			getBeanUtil().valueCopy(vociDiSpesaVO.toArray(), vociDiSpesaDTO);
			for (VoceDiSpesaDTO voce : vociDiSpesaDTO) {
				logger.info("voce.getImportoAssociato(): "+voce.getImportoAssociato()+" ,voce.getImportoValidato():"+voce.getImportoValidato());
			}
			pagamentiAssociatiDTO[i].setVociDiSpesa(vociDiSpesaDTO);
		}

		validazioneRendicontazioneDTO.setPagamenti(pagamentiAssociatiDTO);


		return validazioneRendicontazioneDTO;
	}

	public EsitoDettaglioDocumento findDettaglioDocumentoDiSpesa(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa, Long idProgetto,
			Long idDichiarazioneDiSpesa) throws CSIException, SystemException,
			UnrecoverableException, ValidazioneRendicontazioneException {

		EsitoDettaglioDocumento esito = new EsitoDettaglioDocumento();
		DocumentoDiSpesaDTO documentoDiSpesaDTO = new DocumentoDiSpesaDTO();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idDocumentoDiSpesa", "idProgetto", "idDichiarazioneDiSpesa" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa);
			// }L{ idDichiarazione aggiunto per PBANDI-565

			/*
			 * 23/09/2009 70228 Dopo accordo con Sanseverino il controllo non è
			 * necessario,il doc è sicuramente associato
			 */

			// Verifico se il documento di spesa e' associato al progetto. Se e'
			// associato richiamo findDettaglioDocumentoDiSpesa altrimenti
			// findDettaglioDocumentoDiSpesaNonAssociato

			// if
			// (isDocumentoDiSpesaAssociatoProgetto(idDocumentoDiSpesa,idProgetto))

			/*
			 * DocumentoDiSpesaVO documentoDiSpesaVO = new DocumentoDiSpesaVO();
			 * documentoDiSpesaVO = pbandiDocumentiDiSpesaDAO
			 * .findDettaglioDocumentoDiSpesa(idDocumentoDiSpesa, idProgetto);
			 */
			documentoDiSpesaDTO = findDocumentoDiSpesaDTO(idDocumentoDiSpesa,
					idProgetto);

			/**
			 * FIX : vittorio. Gestione del caso in cui il documento non ha un
			 * fornitore (SPESA-FORFETTARIA
			 * 
			 */
			FornitoreVO fornitore = null;
			if (documentoDiSpesaDTO.getIdFornitore() != null) {
				boolean checkDataFine = false;
				fornitore = pbandiFornitoriDAO.findDettaglioFornitore(
						documentoDiSpesaDTO.getIdFornitore(),documentoDiSpesaDTO.getProgrFornitoreQualifica(), checkDataFine);
			}
			String denominazioneFornitore = "";
			if (!isNull(fornitore)) {
				logger.debug("TROVATO Il fornitore del documento di spesa");
				if (!isEmpty(fornitore.getDenominazioneFornitore())) {
					logger.debug("Il fornitore del documento di spesa è un ente giuridico,setto la denominazione: "
							+ fornitore.getDenominazioneFornitore());
					denominazioneFornitore = fornitore
					.getDenominazioneFornitore();
				} else if (!isEmpty(fornitore.getCognomeFornitore())) {
					denominazioneFornitore = fornitore.getCognomeFornitore();
					if (!isEmpty(fornitore.getNomeFornitore()))
						denominazioneFornitore += " "
							+ fornitore.getNomeFornitore();

					logger.debug("Il fornitore del documento di spesa è una persona fisica ,setto la denominazione: "
							+ denominazioneFornitore);
				}

				if (fornitore.getCostoOrario() != null)
					documentoDiSpesaDTO.setCostoOrario(fornitore
							.getCostoOrario());

			}
			documentoDiSpesaDTO
			.setDenominazioneFornitore(denominazioneFornitore);

			/*
			 * Numero documento di riferimento Stringa Nel caso in cui il tipo
			 * di documento di spesa sia NOTA CREDITO indicare in questo campo
			 * il numero della fattura alla quale la nota si riferisce (è
			 * riferito a). Data documento di riferimento Stringa Nel caso in
			 * cui il tipo di documento di spesa sia NOTA CREDITO indicare in
			 * questo campo la data della fattura alla quale la nota si
			 * riferisce (è riferito a).
			 */
			if (isNotaDiCredito(documentoDiSpesaDTO.getIdTipoDocumentoDiSpesa())) {
				Long idDocDiRiferimento = documentoDiSpesaDTO
				.getIdDocRiferimento();

				logger.debug("il doc di spesa è una NOTA di CREDITO,ci deve essere una fattura collegata");
				DocumentoDiSpesaVO documentoDiSpesaDiRiferimentoVO = pbandiDocumentiDiSpesaDAO
				.findDettaglioDocumentoDiSpesa(idDocDiRiferimento,
						idProgetto);
				if (!isNull(documentoDiSpesaDiRiferimentoVO)) {
					logger.debug("setto numero e data del doc di spesa di riferimento");
					documentoDiSpesaDTO
					.setNumeroDocumentoDiSpesaDiRiferimento(documentoDiSpesaDiRiferimentoVO
							.getNumeroDocumento());
					documentoDiSpesaDTO
					.setDataDocumentoDiSpesaDiRiferimento(documentoDiSpesaDiRiferimentoVO
							.getDataDocumentoDiSpesa());
				}

			}
			esito.setDocumentoDiSpesa(documentoDiSpesaDTO);
			/**
			 * TODO: eseguire i controlli per verificare se e' possibile
			 * eseguire la validazione del documento.
			 */
			esito.setMessaggi(controlliValidazione(documentoDiSpesaDTO,
					idDichiarazioneDiSpesa));


			if (esito.getMessaggi() != null)
				esito.setEsito(Boolean.FALSE);
			else
				esito.setEsito(Boolean.TRUE);
			return esito;
		} finally {
		}

	}

	private DocumentoDiSpesaDTO findDocumentoDiSpesaDTO(
			Long idDocumentoDiSpesa, Long idProgetto) {
		DocumentoDiSpesaDTO documentoDiSpesaDTO;
		DettaglioDocumentoDiSpesaVO documentoDiSpesaVO = new DettaglioDocumentoDiSpesaVO();
		documentoDiSpesaVO.setIdDocumentoDiSpesa(beanUtil.transform(
				idDocumentoDiSpesa, BigDecimal.class));
		documentoDiSpesaVO.setIdProgetto(beanUtil.transform(idProgetto,
				BigDecimal.class));

		documentoDiSpesaVO = genericDAO.where(documentoDiSpesaVO)
		.selectSingle();

		/*
		 * else documentoDiSpesaVO = pbandiDocumentiDiSpesaDAO
		 * .findDettaglioDocumentoDiSpesaNonAssociata( idDocumentoDiSpesa,
		 * idProgetto);
		 */
		documentoDiSpesaDTO = beanUtil.transform(documentoDiSpesaVO,
				DocumentoDiSpesaDTO.class);
		beanUtil.valueCopy(documentoDiSpesaVO, documentoDiSpesaDTO,
				DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO);
		
		return documentoDiSpesaDTO;
	}

	public DocumentoDiSpesaDTO[] findDocumentiDiSpesa(Long idUtente,
			String identitaDigitale, Long idDichiarazioneDiSpesa,
			DocumentoDiSpesaDTO filtro) throws CSIException, SystemException,
			UnrecoverableException, ValidazioneRendicontazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idDichiarazioneDiSpesa", "filtroDocumentoDiSpesa" };
		ValidatorInput.verifyNullValue(nameParameter,
				idDichiarazioneDiSpesa, filtro);


		// nuova query ottimizzata 
		it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO nuovofiltro=
			new it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO();

		nuovofiltro.setCodiceFiscaleFornitore(filtro.getCodiceFiscaleFornitore()); // codiceFiscaleFornitore - PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE 
		nuovofiltro.setCognomeFornitore(filtro.getCognomeFornitore()); // cognomeFornitore
		nuovofiltro.setDataDocumentoDiSpesa(filtro.getDataDocumentoDiSpesa()); //dtEmissioneDocumento
		nuovofiltro.setDenominazioneFornitore(filtro.getDenominazioneFornitore());//denominazioneFornitore
		if(isTrue(filtro.getIsGestitiNelProgetto()))
			nuovofiltro.setIdProgetto(filtro.getIdProgetto());//idProgetto
		nuovofiltro.setIdTipoDocumentoDiSpesa(filtro.getIdTipoDocumentoDiSpesa());//idTipoDocumentoDiSpesa
		nuovofiltro.setIdTipoFornitore(filtro.getIdTipoFornitore());//idTipoFornitore
		nuovofiltro.setNomeFornitore(filtro.getNomeFornitore());//nomeFornitore
		nuovofiltro.setNumeroDocumento(filtro.getNumeroDocumento());//numeroDocumento
		nuovofiltro.setPartitaIvaFornitore(filtro.getPartitaIvaFornitore());//partitaIvaFornitore

		nuovofiltro.setIdSoggetto(filtro.getIdSoggetto());//idSoggetto
		nuovofiltro.setTask(filtro.getTask());//task
		nuovofiltro.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa));//idDichiarazioneSpesa




		java.util.List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO> filtri=new 
		ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO> ();

		filtri.add(nuovofiltro);


		if (!isEmpty(filtro.getStatiDocumento())) {
			for (String idStatoDocumentoDispesa : filtro.getStatiDocumento()) {
				it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO filtroStatoDoc=
					new it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO();
				filtroStatoDoc.setIdStatoDocumentoDiSpesa(NumberUtil.toLong(idStatoDocumentoDispesa));
				filtri.add(filtroStatoDoc);
			}
		}


		java.util.List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO> ret=
			genericDAO.findListWhere(filtri);


		// zozzata piu zozza di quella a seguire 
		Iterator<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO>iter=ret.iterator();
		Map <Long,Long> map=new HashMap<Long,Long>();
		while(iter.hasNext()){
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO doc= iter.next();			
			if(map.containsKey( doc.getIdDocumentoDiSpesa()))
				iter.remove();		
			else
				map.put(doc.getIdDocumentoDiSpesa(), doc.getIdDocumentoDiSpesa());	
		}


		if(filtro.getIdProgetto()!=null){
			// zozzeria di grado infinito per gestire il campo task
			for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO documentoDiSpesaVO : ret) {
				documentoDiSpesaVO.setTask(documentoDiSpesaVO.getTaskIdProgetto());
			}
		}

		List <DocumentoDiSpesaDTO> documentiDiSpesaDTO = new  ArrayList<DocumentoDiSpesaDTO>();

		for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaVO vo : ret) {

			DocumentoDiSpesaDTO dto = getBeanUtil().transform(vo, DocumentoDiSpesaDTO.class);
			dto.setDescBreveTipoDocumentoDiSpesa(vo.getDescBreveTipoDocSpesa());
			documentiDiSpesaDTO.add(dto);
		}


		return documentiDiSpesaDTO.toArray(new DocumentoDiSpesaDTO[0]);
	}

	public ValidazioneNoteDiCreditoDTO findNoteDiCredito(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa, Long idProgetto)
	throws CSIException, SystemException, UnrecoverableException,
	ValidazioneRendicontazioneException {

		ValidazioneNoteDiCreditoDTO response = new ValidazioneNoteDiCreditoDTO();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idDocumentoDiSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idDocumentoDiSpesa, idProgetto);

			NotaDiCreditoConVociDiSpesaVO query = new NotaDiCreditoConVociDiSpesaVO();
			query.setIdDocRiferimento(new BigDecimal(idDocumentoDiSpesa));
			query.setIdProgetto(new BigDecimal(idProgetto));

			List<NotaDiCreditoConVociDiSpesaVO> noteDiCreditoVociDiSpesa = genericDAO
			.findListWhere(query);

			ArrayList<NotaDiCreditoDTO> ndcList = new ArrayList<NotaDiCreditoDTO>();
			NotaDiCreditoDTO ndcDTO = new NotaDiCreditoDTO();
			ArrayList<VoceDiSpesaDTO> vdsList = new ArrayList<VoceDiSpesaDTO>();
			VoceDiSpesaDTO vdsDTO = new VoceDiSpesaDTO();

			for (NotaDiCreditoConVociDiSpesaVO vds : noteDiCreditoVociDiSpesa) {
				// le voci di spesa sono ordinate per id della NDC
				if (ndcDTO.getIdNotaDiCredito() == null
						|| NumberUtil.compare(vds.getIdNotaDiCredito(),
								new BigDecimal(ndcDTO.getIdNotaDiCredito())) != 0) {
					if (ndcDTO.getIdNotaDiCredito() != null) {
						// salvo la ndc vecchia con le sue voci di spesa
						ndcDTO.setVociDiSpesa(vdsList
								.toArray(new VoceDiSpesaDTO[vdsList.size()]));
						ndcList.add(ndcDTO);
					}
					// nuova ndc
					ndcDTO = beanUtil.transform(vds, NotaDiCreditoDTO.class,
							new HashMap<String, String>() {
						{
							put("dtEmissioneDocumento", "dtDocumento");
							put("idNotaDiCredito", "idNotaDiCredito");
							put("importoTotaleDocumento",
							"importoDocumento");
							put("numeroDocumento", "numeroDocumento");
							put("descStatoDocumentoSpesa",
							"statoDocumento");
						}
					});
					vdsList = new ArrayList<VoceDiSpesaDTO>();
					// e prima vds associata
					vdsDTO = beanUtil.transform(vds, VoceDiSpesaDTO.class,
							new HashMap<String, String>() {
						{
							put("descVoceDiSpesa", "descVoceSpesa");
							put("idDocRiferimento",
							"idDocumentoDiSpesa");
							put("importoVoceDiSpesa",
							"importoAssociato");
						}
					});
					vdsList.add(vdsDTO);
				} else {
					// devo aggiungere una nuova vds
					vdsDTO = beanUtil.transform(vds, VoceDiSpesaDTO.class,
							new HashMap<String, String>() {
						{
							put("descVoceDiSpesa", "descVoceSpesa");
							put("idDocRiferimento",
							"idDocumentoDiSpesa");
							put("importoVoceDiSpesa",
							"importoAssociato");
						}
					});
					vdsList.add(vdsDTO);
				}
			}
			// rimane ancora da salvare una NDC
			if (ndcDTO.getIdNotaDiCredito() != null) {
				ndcDTO.setVociDiSpesa(vdsList
						.toArray(new VoceDiSpesaDTO[vdsList.size()]));
				ndcList.add(ndcDTO);
				response.setNoteDiCredito(ndcList
						.toArray(new NotaDiCreditoDTO[ndcList.size()]));
			}

		} finally {
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbandisrv.interfacecsi.validazionerendicontazione.
	 * ValidazioneRendicontazioneSrv#modificaDocumentoDiSpesa(java.lang.Long,
	 * java.lang.String,
	 * it.csi.pbandi.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO
	 * )
	 */
	public EsitoOperazioneModifica modificaDocumentoDiSpesa(Long idUtente,
			String identitaDigitale, DocumentoDiSpesaDTO documentoDiSpesa)
	throws CSIException, SystemException, UnrecoverableException,
	ValidazioneRendicontazioneException {
		boolean fattureInvalidatePerNotaDiCredito = false;
		try {

			/**
			 * Validazione Campi Obbligatori
			 */
			validazioneInput(idUtente, identitaDigitale, documentoDiSpesa);

			PbandiRDocSpesaProgettoVO vo = new PbandiRDocSpesaProgettoVO();
			vo.setIdDocumentoDiSpesa(new BigDecimal(documentoDiSpesa
					.getIdDocumentoDiSpesa()));
			vo.setIdProgetto(new BigDecimal(documentoDiSpesa
					.getIdProgetto()));

			/*
			 * Lo stato del documento di spesa non e' piu' su
			 * PBANDI_R_DOC_SPESA_PROGETTO ma e' su PBANDI_T_DOCUMENTO_DI_SPESA
			 */
			PbandiRDocSpesaProgettoVO ris[] = genericDAO.findWhere(vo);
			if (!isEmpty(ris))
				vo = ris[0];
			Long idStatoDocDiSpesa = NumberUtil.toLong(vo
					.getIdStatoDocumentoSpesa());
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					idStatoDocDiSpesa);
			String codiceRespinto = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO;
			String codiceInserito = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
			if (!isNull(decodifica)) {
				if (decodifica.getDescrizioneBreve().equalsIgnoreCase(
						codiceInserito)
						|| decodifica.getDescrizioneBreve().equalsIgnoreCase(
								codiceRespinto)) {

					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_DOCUMENTO_DI_SPESA_NON_MODIFICABILE);
					logger.error(
							"Errore: il documento di spesa non e' modificabile a causa dello stato ",
							vde);
					throw vde;
				}
			}

			/**
			 * Validazioni. Nel caso di tipo di documento di spesa
			 * SPESA_Forfettaria eseguo solo il controllo sulla data di
			 * presentazione della domanda
			 */

			if (!isSpeseForfettaria(documentoDiSpesa
					.getIdTipoDocumentoDiSpesa())) {
				/**
				 * Esistenza fornitore
				 */
				if (!checkEsisteFornitore(documentoDiSpesa)) {
					/**
					 * Warning W019
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_DOCUMENTO_DI_SPESA_FONITORE_NON_PRESENTE);
					logger.error(
							"Attenzione! Il fornitore indicato non e' presente nell'anagrafica ",
							vde);
					throw vde;
				}
				/**
				 * Univocita' del documento di spesa
				 */
				if (!checkDocumentoDiSpesaUnico(documentoDiSpesa)) {
					/**
					 * Errore W021
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_DOCUMENTO_DI_SPESA_GIA_INSERITO);
					logger.error(
							"Errore: il documento di spesa e' gia' stato inserito nella banca dati.",
							vde);
					throw vde;
				}

				/**
				 * FIX: 227,264 Per il cedolino non devo eseguire i controlli su
				 * iva a costo, iva e importo rendicontabile
				 */

				if (!documentoDiSpesaManager
						.isCedolinoOAutodichiarazioneSoci(documentoDiSpesa
								.getIdTipoDocumentoDiSpesa())) {
					/**
					 * Importo iva a costo <= importo iva
					 */
					if (!checkImportoIvaACosto(documentoDiSpesa)) {
						/**
						 * Errore W024
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								ERRORE_DOCUMENTO_DI_SPESA_IVA_A_COSTO_SUPERIORE);
						logger.error(
								"Attenzione! L'importo dell'iva a costo non deve essere superiore all'imposta.",
								vde);
						throw vde;
					}
					/**
					 * importo rendicontabile <= (imponibile + importo iva a
					 * costo)
					 */
					if (!checkImportoRendicontabile(documentoDiSpesa)) {
						/**
						 * Errore W023
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE);
						logger.error(
								"Attenzione! L'importo rendicontabile non deve essere superiore all'imponibile più l'iva a costo.",
								vde);
						throw vde;
					}
					/**
					 * totaleRendicontabile <= (imponibile + importo iva a
					 * costo)
					 */
					if (!checkTotaleRendicontabile(documentoDiSpesa)) {
						/**
						 * Errore W020
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE);
						logger.error(
								"Attenzione! Il totale del rendicontabile associato ai progetti e' maggiore all'importo massimo rendicontabile del documento di spesa.",
								vde);
						throw vde;
					}
				}
				/*
				 * FIX : PBandi-548. Per tutti i documenti, compresi il CEDOLINO
				 * vengono eseguiti i controlli sul totale del documento e l'
				 * importo rendicontabile
				 */
				else {
					if (!checkImportoRendicontabile(documentoDiSpesa)) {
						/**
						 * Errore W023
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								ERRORE_DOCUMENTO_DI_SPESA_CEDOLINO_O_AUTODICHIARAZIONE_SOCI_IMPORTO_RENDICONTABILE_SUPERIORE);
						logger.error(
								"Attenzione! L'importo rendicontabile non deve essere superiore all'imponibile.",
								vde);
						throw vde;
					}
					/**
					 * totaleRendicontabile <= (imponibile + importo iva a
					 * costo)
					 */
					if (!checkTotaleRendicontabile(documentoDiSpesa)) {
						/**
						 * Errore W020
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE);
						logger.error(
								"Attenzione! Il totale del rendicontabile associato ai progetti e' maggiore all'importo massimo rendicontabile del documento di spesa.",
								vde);
						throw vde;
					}
				}

				/**
				 * totale quote parte <= importo rendicontabile
				 */
				if (!checkTotaleQuotaParte(documentoDiSpesa)) {
					/**
					 * Errore W022
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							WARNING_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE);
					logger.error(
							"Attenzione! La somma del rendicontato è maggiore del rendicontabile.",
							vde);
					throw vde;
				}
				/**
				 * Totale importo pagamenti <= totale importo documento
				 */
				if (!checkTotalePagamenti(documentoDiSpesa)) {
					/**
					 * Errore W025
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							WARNING_DOCUMENTO_DI_SPESA_TOTALE_PAGAMENTI_MAGGIORE_TOTALE_DOCUMENTO);
					logger.error(
							"Attenzione! La somma del quietanzato e' maggiore del totale del documento di spesa.",
							vde);
					throw vde;
				}
				/**
				 * Congruita' dati nel caso di CEDOLINO
				 */
				if (!checkCongruitaDati(documentoDiSpesa)) {
					/**
					 * Errore E023
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_DOCUMENTO_DI_SPESA_DATI_NON_CONGRUI);
					logger.error(
							"Errore: integrare i dati del fornitore con l'indicazione del monte ore e del costo della risorsa.",
							vde);
					throw vde;
				}
			}
			/**
			 * Data documento >= data presentazione domanda
			 */
			if (!checkDataDocumento(documentoDiSpesa)) {
				/**
				 * Errore W026
				 */
				ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
						ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA);
				logger.error(
						"Attenzione! La data del documento di spesa deve essere successivo alla data di presentazione della domanda.",
						vde);
				throw vde;
			}
			/**
			 * Data documento <= data odierna
			 */
			if (!checkData(documentoDiSpesa)) {
				/**
				 * Errore W030
				 */
				ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
						ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_OGGI);
				logger.error(
						"La data del documento di spesa non puo' essere maggiore alla data odierna.",
						vde);
				throw vde;

			}
			DocumentoDiSpesaVO documentoDiSpesaVO = beanUtil.transform(
					documentoDiSpesa, DocumentoDiSpesaVO.class);
			DocumentoDiSpesaDTO documentoDiSpesaBefore = null;
			if(isNotaDiCredito(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
				// }L{ PBANDI-1709 per confrontare le modifiche
				documentoDiSpesaBefore = findDocumentoDiSpesaDTO(documentoDiSpesa.getIdDocumentoDiSpesa(), documentoDiSpesa.getIdProgetto());
			}

			/*
			 * Inserisco lo stato del documento
			 */
			documentoDiSpesaVO.setIdStatoDocumentoDiSpesa(idStatoDocDiSpesa);

			/**
			 * Aggiorno gli attributi del documento di spesa
			 */
			if (!pbandiDocumentiDiSpesaDAO.updateDocumentoDiSpesa(idUtente,
					documentoDiSpesaVO)) {
				/**
				 * Errore E002
				 */
				ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
						ERRORE_SERVER);
				logger.error(
						"Non e' stato possibile portare a termine l'operazione.",
						vde);
				throw vde;
			}

			/**
			 * FIX: PBandi 310. Verifico se esiste l' associazione tra il
			 * documento di spesa ed il progetto. Se esiste allora eseguo
			 * l'update, altrimenti eseguo l' insert
			 */

			if (!isDocumentoDiSpesaAssociatoProgetto(
					documentoDiSpesaVO.getIdDocumentoDiSpesa(),
					documentoDiSpesaVO.getIdProgetto())) {
				if (!pbandiDocumentiDiSpesaDAO
						.inserisciAssociazioneDocumentoDiSpesaProgetto(
								idUtente, documentoDiSpesaVO.getIdProgetto(),
								documentoDiSpesaVO.getIdDocumentoDiSpesa(),
								documentoDiSpesaVO.getImportoRendicontabile(),
								documentoDiSpesaVO.getTask(),
								idStatoDocDiSpesa)) {
					/**
					 * Errore E002
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_SERVER);
					logger.error(
							"Non e' stato possibile portare a termine l'operazione.",
							vde);
					throw vde;
				}

			} else {

				/**
				 * Aggiorno l' importo rendicontabile Progetto/Documento di
				 * spesa
				 */
				if (!pbandiDocumentiDiSpesaDAO
						.updateAssociazioneProgettoDocumentoDiSpesa(idUtente,
								documentoDiSpesaVO)) {
					/**
					 * Errore E002
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_SERVER);
					logger.error(
							"Non e' stato possibile portare a termine l'operazione.",
							vde);
					throw vde;
				}
			}
			if(isNotaDiCredito(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
				fattureInvalidatePerNotaDiCredito = invalidaFatturePerNotaDiCredito(documentoDiSpesaBefore, documentoDiSpesa,idUtente);
			}

		} catch (Exception e) {
			if (e instanceof ValidazioneRendicontazioneException) {
				throw (ValidazioneRendicontazioneException)e;
			} else {
				ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
						ERRORE_SERVER);
				logger.error(
						"Non e' stato possibile portare a termine l'operazione: "
						+ e.getMessage(), vde);
				throw vde;
			}
		}  
		EsitoOperazioneModifica esito = new EsitoOperazioneModifica();
		/**
		 * Devo restituire il messaggio N011 
		 * Restituire un EsitoOperazioneModifica
		 * }L{ DONE
		 */
		esito.setEsito(true);
		if(fattureInvalidatePerNotaDiCredito) {
			esito.setMessage(MessaggiConstants.KEY_WARNING_FATTURE_INVALIDATE_PER_NOTA_DI_CREDITO_MODIFICATA);
		} else {
			esito.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		}
		return esito;
	}

	public EsitoOperazioneDocumentoDiSpesa modificaDocumentoDiSpesaSemplificazione(Long idUtente,
			String identitaDigitale, DocumentoDiSpesaDTO documentoDiSpesa)
	throws CSIException, SystemException, UnrecoverableException,
	ValidazioneRendicontazioneException {
		boolean fattureInvalidatePerNotaDiCredito = false;
		try {
			
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"documentoDiSpesa", "documentoDiSpesa.idProgetto",
					"documentoDiSpesa.idSoggetto",
			"documentoDiSpesa.idTipoDocumentoSpesa" };
			
			ValidatorInput.verifyNullValue(nameParameter, documentoDiSpesa,
					documentoDiSpesa.getIdProgetto(), documentoDiSpesa.getIdSoggetto(),
					documentoDiSpesa.getIdTipoDocumentoDiSpesa());
			
			EsitoOperazioneDocumentoDiSpesa esito = new EsitoOperazioneDocumentoDiSpesa();
			esito.setEsito(Boolean.FALSE);
			List<MessaggioDTO> messaggi = new ArrayList<MessaggioDTO>();
			boolean hasError = false;
			
			PbandiRDocSpesaProgettoVO filterVO = new PbandiRDocSpesaProgettoVO();
			filterVO.setIdDocumentoDiSpesa(new BigDecimal(documentoDiSpesa
					.getIdDocumentoDiSpesa()));
			filterVO.setIdProgetto(new BigDecimal(documentoDiSpesa
					.getIdProgetto()));

			
			
			PbandiRDocSpesaProgettoVO vo = genericDAO.findSingleWhere(filterVO);
			Long idStatoDocDiSpesa = NumberUtil.toLong(vo.getIdStatoDocumentoSpesa());

			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					idStatoDocDiSpesa);
			String codiceRespinto = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO;
			String codiceInserito = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
			if (!isNull(decodifica)) {
				if (decodifica.getDescrizioneBreve().equalsIgnoreCase(
						codiceInserito)
						|| decodifica.getDescrizioneBreve().equalsIgnoreCase(
								codiceRespinto)) {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_NON_MODIFICABILE);
					messaggi.add(msg);
					esito.setMessaggi(beanUtil.transform(messaggi, MessaggioDTO.class));
					hasError = true;
					return esito;
				}
			}

			/**
			 * Validazioni. Nel caso di tipo di documento di spesa
			 * SPESA_Forfettaria eseguo solo il controllo sulla data di
			 * presentazione della domanda
			 */
			
			if (!isSpeseForfettaria(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
				/*
				 * Esistenza fornitore
				 */
				if (!checkEsisteFornitore(documentoDiSpesa)) {
					
					/**
					 * Warning W019
					 */
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_FONITORE_NON_PRESENTE);
					messaggi.add(msg);
					esito.setMessaggi(beanUtil.transform(messaggi, MessaggioDTO.class));
					hasError = true;
					return esito;

				}
				/*
				 * Univocita' del documento di spesa
				 */
				if (!checkDocumentoDiSpesaUnico(documentoDiSpesa)) {
					/**
					 * Errore W021
					 */
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_GIA_INSERITO);
					messaggi.add(msg);
					esito.setMessaggi(beanUtil.transform(messaggi, MessaggioDTO.class));
					hasError = true;
					return esito;

				}

				/*
				 * Per il CEDOLINO e AUTODICHIARAZIONE SOCI non eseguo il controllo
				 * sull' iva a costo
				 * 
				 */
				if (
						!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO) &&
						!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO) &&
						!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCIE)
				) {
					/**
					 * Importo iva a costo <= importo iva
					 */
					if (!checkImportoIvaACosto(documentoDiSpesa)) {
						/**
						 * Errore W024
						 */
						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_IVA_A_COSTO_SUPERIORE);
						messaggi.add(msg);
						hasError = true;


					}
				}

				/*
				 * importo rendicontabile <= (imponibile + importo iva a
				 * costo)
				 */
				if (!checkImportoRendicontabile(documentoDiSpesa)) {
					/**
					 * Errore W023
					 */
					MessaggioDTO msg = new MessaggioDTO();
					if ( documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO) ||
							documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO) ) {

						msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_CEDOLINO_O_AUTODICHIARAZIONE_SOCI_IMPORTO_RENDICONTABILE_SUPERIORE);
					} else {
						msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE);
					}
					messaggi.add(msg);
					hasError = true;


				}
				
				/*
				 * totaleRendicontabile <= (imponibile + importo iva a
				 * costo)
				 */
				if (!checkTotaleRendicontabile(documentoDiSpesa)) {
					/**
					 * Errore W020
					 */
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE);
					messaggi.add(msg);
					hasError = true;
				}


				/*
				 * totale quote parte <= importo rendicontabile
				 */
				if (!checkTotaleQuotaParte(documentoDiSpesa)) {
					/**
					 * Errore W022
					 */
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(WARNING_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE);
					messaggi.add(msg);
					hasError = true;


				}

				/*
				 * Totale importo pagamenti <= totale importo documento
				 */
				if (!checkTotalePagamenti(documentoDiSpesa)) {
					/**
					 * Errore W025
					 */
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(WARNING_DOCUMENTO_DI_SPESA_TOTALE_PAGAMENTI_MAGGIORE_TOTALE_DOCUMENTO);
					messaggi.add(msg);
					hasError = true;				
				}

				/*
				 * Congruita' dati nel caso di CEDOLINO
				 */
				if (!checkCongruitaDati(documentoDiSpesa)) {
					/**
					 * Errore E023
					 */
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_DATI_NON_CONGRUI);
					messaggi.add(msg);
					hasError = true;		
				}
			}

			/*
			 * Se il documento di spesa e' diverso da CEDOLINO e CEDOLINO
			 * CO.CO.PRO allora verifico che la somma degli importi
			 * rendicontabile del documento di spesa associato ai progetti non
			 * sia superiore al massimo rendicontabile ( imponibile + importo
			 * iva a costo)
			 */
			if (
					!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO) &&
					!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO) ) {
				if (!documentoDiSpesaManager.checkMassimRendicontabileDocumentoProgetti(
						documentoDiSpesa.getIdDocumentoDiSpesa(), 
						documentoDiSpesa.getIdProgetto(), 
						documentoDiSpesa.getImponibile(), 
						documentoDiSpesa.getImportoIvaACosto(), 
						documentoDiSpesa.getImportoRendicontabile())) {

					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE);
					messaggi.add(msg);
					hasError = true;		
				}
			}
			
			/*
			 * Se il documento non e' di tipo QUOTA DI AMMORTAMENTO
			 */
			if (!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_QUOTA_AMMORTAMENTO)) {
				/*
				 * Se : - il bando segue la regola BR30 verificho che se il
				 * beneficiario e' un ENTE GIURIDICO - la data di costituizione
				 * dell' ente esiste allora : la data del documento deve essere
				 * superiore alla data di costituzione dell' ente altrimenti: la
				 * data del documento deve essere superiore alla data di
				 * presentazione della domanda
				 */
				if (regolaManager.isRegolaApplicabileForProgetto(
						documentoDiSpesa.getIdProgetto(),
						RegoleConstants.BR30_DATA_AMMISSIBILITA_PER_PROGETTTI)) {

					BeneficiarioEnteGiuridicoVO enteGiuridicoSoggetto = soggettoManager
					.findEnteGiuridicoBeneficiario(
							documentoDiSpesa.getIdProgetto(),
							documentoDiSpesa.getIdSoggetto());

					if (!ObjectUtil.isNull(enteGiuridicoSoggetto)
							&& enteGiuridicoSoggetto.getDtCostituzione() != null) {
						/*
						 * Verifico che la data del documento sia superiore alla
						 * data di costituzione dell' ente
						 */
						if (DateUtil.utilToSqlDate(
								documentoDiSpesa.getDataDocumentoDiSpesa())
								.before(enteGiuridicoSoggetto
										.getDtCostituzione()) ||
										DateUtil.utilToSqlDate(
												documentoDiSpesa.getDataDocumentoDiSpesa())
												.before(enteGiuridicoSoggetto
														.getDtCostituzione())) {
							MessaggioDTO msg = new MessaggioDTO();
							msg.setMsgKey(WARNING_DOCUMENTO_DI_SPESA_DATA_DOCUMENTO_INFERIORE_DATA_COSTITUZIONE);
							messaggi.add(msg);
							hasError = true;	
						}
					} else {
						/*
						 * Verifico che la data del documento sia superiore alla
						 * data di presentazione della domanda
						 */
						if (!checkDataDocumento(documentoDiSpesa)) {
							MessaggioDTO msg = new MessaggioDTO();
							msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA);
							messaggi.add(msg);
							hasError = true;	
						}
					}

				} else {
					/*
					 * Verifico che la data del documento sia superiore alla
					 * data di presentazione della domanda
					 */
					if (!checkDataDocumento(documentoDiSpesa)) {
						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA);
						messaggi.add(msg);
						hasError = true;
					}
				}
			}
			
			/*
			 * Se il documento e' una nota di credito allora verificare che
			 * l'importo rendicontabile inserito per una nota di credito sia
			 * minore o uguale al rendicontabile netto (ovvero corretto già da
			 * eventuali altri rendicontabili di note di credito già esistenti
			 * per il documento di riferimento).
			 */
			if (documentoDiSpesaManager.isNotaDiCredito(documentoDiSpesa
					.getIdTipoDocumentoDiSpesa())) {

				/*
				 * FIX PBANDI-2314
				 * Verifico che il totale (impobibile e iva a costo) della fattura alla quale si riferisce
				 * la nota di credito, al netto delle note di credito, non sia
				 * minore del totale dei rendicontabili
				 */
				if (checkNotaCreditoTotaleRendicontabileFatturaSuperioreRendicontabileMassimo(documentoDiSpesa)) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTI_DI_SPESA_TOTALE_FATTURA_MINORE_TOTALE_RENDICONTABILI);
					messaggi.add(msg);

				}

				if (!documentoDiSpesaManager.checkRendicontabileNotaCredito(
						documentoDiSpesa.getIdDocumentoDiSpesa(), 
						documentoDiSpesa.getIdDocRiferimento(), 
						documentoDiSpesa.getIdProgetto(), 
						documentoDiSpesa.getImportoRendicontabile())) {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_NOTA_CREDITO);
					messaggi.add(msg);
					hasError = true;
				}
			}

			/*
			 * FIX PBANDI-2314: Nel caso di 
			 * fattura devo verificare che il totale (imponibile + iva a costo) del documento al netto
			 * delle note di credito sia maggiore o uguale al totale dei rendicontabili
			 */
			if (( documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA) ||
					documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA) || 
					documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING)
			) &&  documentoDiSpesa.getIdDocumentoDiSpesa() != null ){
				if (checkTotaleRendicontabileSuperioreRendicontabileMassimoDocumento(documentoDiSpesa)) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ERRORE_DOCUMENTI_DI_SPESA_TOTALE_DOCUMENTO_MINORE_TOTALE_RENDICONTABILI);
					messaggi.add(msg);
				}
			}


			/*
			 * Data documento <= data odierna
			 */
			if (!checkData(documentoDiSpesa)) {
				/**
				 * Errore W030
				 */
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_OGGI);
				messaggi.add(msg);
				hasError = true;
			}

			
			if (!hasError) {

				DocumentoDiSpesaVO documentoDiSpesaVO = beanUtil.transform(
						documentoDiSpesa, DocumentoDiSpesaVO.class);
				DocumentoDiSpesaDTO documentoDiSpesaBefore = null;
				if(isNotaDiCredito(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
					// }L{ PBANDI-1709 per confrontare le modifiche
					documentoDiSpesaBefore = findDocumentoDiSpesaDTO(documentoDiSpesa.getIdDocumentoDiSpesa(), documentoDiSpesa.getIdProgetto());
				}

				/*
				 * Inserisco lo stato del documento
				 */
				documentoDiSpesaVO.setIdStatoDocumentoDiSpesa(idStatoDocDiSpesa);

				/**
				 * Aggiorno gli attributi del documento di spesa
				 */
				if (!pbandiDocumentiDiSpesaDAO.updateDocumentoDiSpesa(idUtente,
						documentoDiSpesaVO)) {
					/**
					 * Errore E002
					 */
					ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
							ERRORE_SERVER);
					logger.error(
							"Non e' stato possibile portare a termine l'operazione.",
							vde);
					throw vde;
				}

				/**
				 * FIX: PBandi 310. Verifico se esiste l' associazione tra il
				 * documento di spesa ed il progetto. Se esiste allora eseguo
				 * l'update, altrimenti eseguo l' insert
				 */
				
				if (!isDocumentoDiSpesaAssociatoProgetto(
						documentoDiSpesaVO.getIdDocumentoDiSpesa(),
						documentoDiSpesaVO.getIdProgetto())) {
					if (!pbandiDocumentiDiSpesaDAO
							.inserisciAssociazioneDocumentoDiSpesaProgetto(
									idUtente, documentoDiSpesaVO.getIdProgetto(),
									documentoDiSpesaVO.getIdDocumentoDiSpesa(),
									documentoDiSpesaVO.getImportoRendicontabile(),
									documentoDiSpesaVO.getTask(),
									idStatoDocDiSpesa)) {
						/**
						 * Errore E002
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								ERRORE_SERVER);
						logger.error(
								"Non e' stato possibile portare a termine l'operazione.",
								vde);
						throw vde;
					}

				} else {

					/**
					 * Aggiorno l' importo rendicontabile Progetto/Documento di
					 * spesa
					 */
					if (!pbandiDocumentiDiSpesaDAO
							.updateAssociazioneProgettoDocumentoDiSpesa(idUtente,
									documentoDiSpesaVO)) {
						/**
						 * Errore E002
						 */
						ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
								ERRORE_SERVER);
						logger.error(
								"Non e' stato possibile portare a termine l'operazione.",
								vde);
						throw vde;
					}
				}
				if(isNotaDiCredito(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
					fattureInvalidatePerNotaDiCredito = invalidaFatturePerNotaDiCredito(documentoDiSpesaBefore, documentoDiSpesa,idUtente);
				}

				esito.setEsito(Boolean.TRUE);
				if (fattureInvalidatePerNotaDiCredito) {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(KEY_WARNING_FATTURE_INVALIDATE_PER_NOTA_DI_CREDITO_MODIFICATA);
					messaggi.add(msg);
				} else {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					messaggi.add(msg);
				}
			} 
			esito.setMessaggi(beanUtil.transform(messaggi, MessaggioDTO.class));
			return esito;

		} catch (Exception e) {
			if (e instanceof ValidazioneRendicontazioneException) {
				throw (ValidazioneRendicontazioneException)e;
			} else {
				ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
						ERRORE_SERVER);
				logger.error(
						"Non e' stato possibile portare a termine l'operazione: "
						+ e.getMessage(), vde);
				throw vde;
			}
		} finally {
		}
	}

	private boolean invalidaFatturePerNotaDiCredito(DocumentoDiSpesaDTO documentoDiSpesaBefore,
			DocumentoDiSpesaDTO documentoDiSpesa,Long idUtente) throws Exception {
		boolean invalidated = false;
		if(NumberUtil.compare(documentoDiSpesaBefore.getIdDocRiferimento(), documentoDiSpesa.getIdDocRiferimento()) != 0) {
			// cambiata la fattura di riferimento, invalido entrambe
			invalidated = true;
			invalidaFatturaPerNotaDiCredito(documentoDiSpesaBefore.getIdDocRiferimento(),idUtente);
			invalidaFatturaPerNotaDiCredito(documentoDiSpesa.getIdDocRiferimento(),idUtente);
		} else if ((NumberUtil.compare(documentoDiSpesaBefore.getImponibile(),
				documentoDiSpesa.getImponibile()) != 0)
				|| (NumberUtil.compare(documentoDiSpesaBefore.getImportoIva(),
						documentoDiSpesa.getImportoIva()) != 0)
						|| (NumberUtil.compare(documentoDiSpesaBefore.getImportoIvaACosto(),
								documentoDiSpesa.getImportoIvaACosto()) != 0)
								|| (NumberUtil.compare(documentoDiSpesaBefore.getImportoRendicontabile(),
										documentoDiSpesa.getImportoRendicontabile()) != 0)
										|| (NumberUtil.compare(documentoDiSpesaBefore.getImportoRitenutaDAcconto(),
												documentoDiSpesa.getImportoRitenutaDAcconto()) != 0)
												|| (NumberUtil.compare(documentoDiSpesaBefore.getImportoTotaleDocumentoIvato(),
														documentoDiSpesa.getImportoTotaleDocumentoIvato()) != 0)) {
			// un importo è cambiato
			invalidated = true;
			invalidaFatturaPerNotaDiCredito(documentoDiSpesaBefore.getIdDocRiferimento(),idUtente);
		}

		return invalidated;
	}

	private void invalidaFatturaPerNotaDiCredito(Long idDocRiferimento,Long idUtente) throws Exception {
		Long idStatoValidazioneSpesaDichiarato = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_VALIDAZ_SPESA,
				it.csi.pbandi.pbweb.pbandiutil.common.Constants.StatiValidazionePagamenti.DICHIARATO.getDescBreve()).getId();
		PbandiRPagamentoDocSpesaVO pagamentiQuery = new PbandiRPagamentoDocSpesaVO();
		pagamentiQuery.setIdDocumentoDiSpesa(beanUtil.transform(idDocRiferimento, BigDecimal.class));
		List<PbandiRPagamentoDocSpesaVO> pagamenti = genericDAO.where(pagamentiQuery).select();
		for (PbandiRPagamentoDocSpesaVO pagamento : pagamenti) {
			PbandiRPagQuotParteDocSpVO quoteParteQuery = new PbandiRPagQuotParteDocSpVO();
			quoteParteQuery.setIdPagamento(pagamento.getIdPagamento());
			List<PbandiRPagQuotParteDocSpVO> quoteParte = genericDAO.where(quoteParteQuery).select();
			for (PbandiRPagQuotParteDocSpVO quotaParte : quoteParte) {
				// azzero il validato
				quotaParte.setImportoValidato(null);
				quotaParte.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.updateNullables(quotaParte);
			}
			// pagamento in stato DICHIARATO
			/*
			 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
			PbandiTPagamentoVO pagamentoVO = new PbandiTPagamentoVO(pagamento.getIdPagamento());
			pagamentoVO.setIdStatoValidazioneSpesa(new BigDecimal(idStatoValidazioneSpesaDichiarato));
			pagamentoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(pagamentoVO);
			 */
		}
	}

	/**
	 * Verifica se il documento di spesa e' associato al progetto.
	 * 
	 * @param idDocumentoDiSpesa
	 * @param idProgetto
	 * @return
	 */
	private boolean isDocumentoDiSpesaAssociatoProgetto(
			Long idDocumentoDiSpesa, Long idProgetto) {
		PbandiRDocSpesaProgettoVO vo = new PbandiRDocSpesaProgettoVO();
		vo.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		vo.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		PbandiRDocSpesaProgettoVO[] result = genericDAO.findWhere(vo);
		if (result == null || result.length == 0)
			return false;
		else
			return true;

	}

	/**
	 * 
	 * @param idTipoDocumentoDiSpesa
	 * @return
	 * @throws GestioneDocumentiDiSpesaException
	 */
	private boolean isFattura(Long idTipoDocumentoDiSpesa)
	throws ValidazioneRendicontazioneException {
		try {
			boolean result = false;
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoDiSpesa);
			if (!isNull(decodifica)
					&& !isEmpty(decodifica.getDescrizioneBreve())) {
				logger.debug("documento di spesa con idTipo "
						+ idTipoDocumentoDiSpesa + " è di tipo:"
						+ decodifica.getDescrizioneBreve());
				String descBreve = decodifica.getDescrizioneBreve();

				if (descBreve
						.equalsIgnoreCase(DocumentoDiSpesaManager
								.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA))
								|| descBreve
								.equalsIgnoreCase(DocumentoDiSpesaManager
										.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA))
										|| descBreve
										.equalsIgnoreCase(DocumentoDiSpesaManager
												.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING))) {
					logger.debug("documento di spesa è una FATTURA ");
					result = true;
				} else {
					logger.debug("documento di spesa NON è una FATTURA ");
					result = false;
				}
			} else {
				logger.warn("decodifica del tipo documento di spesa NON è stata TROVATA !!!");
				throw new ValidazioneRendicontazioneException(
						"decodifica del tipo documento di spesa NON è stata TROVATA per idTipo:"
						+ idTipoDocumentoDiSpesa);
			}
			return result;
		} finally {
		}
	}

	/**
	 * 
	 * @param idTipoDocumentoSpesa
	 * @return
	 */
	private boolean isNotaDiCredito(Long idTipoDocumentoSpesa)
	throws ValidazioneRendicontazioneException {
		try {
			boolean result = false;
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoSpesa);
			if (!isNull(decodifica)
					&& !isEmpty(decodifica.getDescrizioneBreve())) {
				if (decodifica
						.getDescrizioneBreve()
						.equals(DocumentoDiSpesaManager
								.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO)))
					result = true;
				else
					result = false;
			} else {
				logger.warn("decodifica del tipo documento di spesa NON è stata TROVATA !!!");
				throw new ValidazioneRendicontazioneException(
						"decodifica del tipo documento di spesa NON è stata TROVATA per idTipo:"
						+ idTipoDocumentoSpesa);
			}
			return result;
		} finally {
		}
	}

	/**
	 * 
	 * @param doc
	 * @return
	 * @throws FormalParameterException
	 * @throws GestioneDocumentiDiSpesaException
	 */
	private String[] controlliValidazione(DocumentoDiSpesaDTO doc,
			Long idDichiarazioneDiSpesa) throws FormalParameterException,
			ValidazioneRendicontazioneException {
		List<String> errori = new ArrayList<String>();
		try {
			Long idDocumentoDiSpesa = doc.getIdDocumentoDiSpesa();
			Long idProgetto = doc.getIdProgetto();
			Long idTipoDocumentoDiSpesa = doc.getIdTipoDocumentoDiSpesa();

			boolean isBr01 = regolaManager.isRegolaApplicabileForProgetto(
					idProgetto,
					RegoleConstants.BR01_GESTIONE_ATTRIBUTI_QUALIFICA);
			boolean isBr02 = regolaManager.isRegolaApplicabileForProgetto(
					idProgetto, RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);
			boolean isBr03 = regolaManager
			.isRegolaApplicabileForProgetto(idProgetto,
					RegoleConstants.BR03_RILASCIO_SPESE_QUIETANZATE);
			boolean isBr07 = regolaManager
			.isRegolaApplicabileForProgetto(
					idProgetto,
					RegoleConstants.BR07_CONTROLLO_SPESE_TOTALMENTE_QUIETANZATE);

			boolean isFinaleOIntegrativa = isDichiarazioneFinaleOIntegrativa(idDichiarazioneDiSpesa);

			logger.debug("isBr01:" + isBr01);
			logger.debug("isBr02:" + isBr02);
			logger.debug("isBr03:" + isBr03);
			logger.debug("isBr07:" + isBr07);

			/*
			 * @since 2.0.0 <br> il metodo NumberUtil.getDoubleValue effettua
			 * anch'esso l'arrotondamento, esattamente come il metodo
			 * NumberUtil.toRoundDouble
			 */
			BigDecimal totaleDocumento = beanUtil.transformToBigDecimal(doc
					.getImportoTotaleDocumentoIvato());
			BigDecimal importoRendicontabileDocumento = BeanUtil
			.transformToBigDecimal(doc.getImportoRendicontabile());

			boolean isFattura = isFattura(idTipoDocumentoDiSpesa);
			boolean isCedolino = documentoDiSpesaManager
			.isCedolinoOAutodichiarazioneSoci(idTipoDocumentoDiSpesa);
			boolean isNotaCredito = isNotaDiCredito(idTipoDocumentoDiSpesa);

			/*
			 * FIX PBandi-966 Controllo le voci di spesa del documento
			 */
			String errorCode = documentoDiSpesaManager
			.controlliVociSpesaNoteCreditoDocumento(
					NumberUtil.toBigDecimal(idDocumentoDiSpesa),
					NumberUtil.toBigDecimal(idProgetto), isNotaCredito);
			if (errorCode != null) {
				errori.add(errorCode);
			}

			/*
			 * Se il documento selezionato e' di tipo NOTA DI CREDITO la
			 * validazione fallisce
			 */
			if (isNotaCredito) {
				return errori.toArray(new String[] {});
			}

			/**
			 * Se il documento e' una fattura ricerco le note di credito
			 * associate
			 */
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> noteDiCredito = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>();
			if (isFattura) {
				noteDiCredito = pbandiDocumentiDiSpesaDAO
				.findNoteDiCreditoAssociateFattura(idDocumentoDiSpesa);
			}

			/**
			 * Recupero le quote parte per il documento ed il progetto corrente
			 */
			List<PbandiTQuotaParteDocSpesaVO> quoteParteVO = pbandiDocumentiDiSpesaDAO
			.findImportiQuotaParteByDocumentoDiSpesaProgetto(
					doc.getIdDocumentoDiSpesa(), doc.getIdProgetto(),
					null);

			/**
			 * Controllo che l' importo rendicontabile del documento sia minore
			 * o uguale alla somma degli importi delle voci di spese associate
			 * 
			 * @since 2.0.0 <br>
			 *        il metodo NumberUtil.getDoubleValue effettua anch'esso
			 *        l'arrotondamento, esattamente come il metodo
			 *        NumberUtil.toRoundDouble
			 */
			if (quoteParteVO != null && quoteParteVO.size() > 0) {
				BigDecimal totaleQuoteParte = new BigDecimal(0);
				for (PbandiTQuotaParteDocSpesaVO quotaVO : quoteParteVO) {
					totaleQuoteParte = NumberUtil.sum(totaleQuoteParte,
							quotaVO.getImportoQuotaParteDocSpesa());
				}
				if (NumberUtil.compare(importoRendicontabileDocumento,
						totaleQuoteParte) > 0) {
					/**
					 * Errore M2
					 */

					errori.add(ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE);
				}
			}


			/*
			 * Calcolo il totale di pagamenti associati al documento
			 */
			List<PbandiTPagamentoVO> pagamentiDocumento = pbandiDocumentiDiSpesaDAO
			.findPagamentiAssociatiADocumento(idDocumentoDiSpesa);

			BigDecimal totalePagamenti = new BigDecimal(0);
			for (PbandiTPagamentoVO pagamento : pagamentiDocumento) {
				BigDecimal importoPagamento = pagamento.getImportoPagamento();
				totalePagamenti = NumberUtil.sum(totalePagamenti,
						importoPagamento);
			}

			/*
			 * Se il documento e' di tipo fattura Al totale del documento,
			 * sottraggo le note di credito
			 */

			if (isFattura && noteDiCredito != null) {

				for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO notadiCredito : noteDiCredito) {
					BigDecimal importoTotaleNotaCredito = beanUtil
					.transformToBigDecimal(notadiCredito
							.getImportoTotaleDocumento());
					totaleDocumento = NumberUtil.subtract(totaleDocumento,
							importoTotaleNotaCredito);
				}
			}
			/*
			 * FIX: PBandi-423 Se il documento e' di tipo CEDOLINO allora l'
			 * importo sul quale verificare i pagamenti e' dato dal massimo tra
			 * l'importo rendicontabile e l' importo totale del documento.
			 */
			if (isCedolino) {
				BigDecimal importoRendicontabileCedolino = BeanUtil
				.transformToBigDecimal(doc.getImportoRendicontabile());
				if (NumberUtil.compare(importoRendicontabileCedolino,
						totaleDocumento) > 0)
					totaleDocumento = importoRendicontabileCedolino;
			}

			if (NumberUtil.compare(totaleDocumento, totalePagamenti) < 0) {
				/**
				 * Errore M4
				 */
				errori.add(ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_IMPORTO_QUIETANZATO_MAGGIORE_TOTALE_DOCUMENTO);
			}

			/*
			 * FIX - PBandi-488. Per i bandi che seguono la regola BR07 questo
			 * controllo non viene ancora eseguito.
			 */
			if ((isBr03 || (isBr07 && isFinaleOIntegrativa) )
					&& NumberUtil.compare(totaleDocumento, totalePagamenti) > 0) {
				/**
				 * Errore M3
				 */

				errori.add(ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_DOCUMENTO_NON_TOTALMENTE_QUIETANZATO);
			}




			if (errori.size() > 0)
				return errori.toArray(new String[] {});
			else
				return null;

		} catch (Exception e) {
			ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
					ERRORE_SERVER);
			logger.error(
					"Non e' stato possibile portare a termine l'operazione: "
					+ e.getMessage(), vde);
			throw vde;
		} finally {
			logger.end();
		}

	}

	private boolean isDichiarazioneFinaleOIntegrativa(Long idDichiarazioneSpesa) {
		/*
		 * Recupero i dati della dichiarazione
		 */
		PbandiTDichiarazioneSpesaVO dichiarazioneVO = new PbandiTDichiarazioneSpesaVO();
		dichiarazioneVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneSpesa));
		dichiarazioneVO = genericDAO.findSingleWhere(dichiarazioneVO);

		PbandiDTipoDichiarazSpesaVO tipoDichiarazioneVO = new PbandiDTipoDichiarazSpesaVO();
		tipoDichiarazioneVO.setIdTipoDichiarazSpesa(dichiarazioneVO.getIdTipoDichiarazSpesa());
		tipoDichiarazioneVO = genericDAO.findSingleWhere(tipoDichiarazioneVO);

		boolean isFinaleOIntegrativa = it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE.equalsIgnoreCase(tipoDichiarazioneVO.getDescBreveTipoDichiaraSpesa()) ||
		it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA.equalsIgnoreCase(tipoDichiarazioneVO.getDescBreveTipoDichiaraSpesa());


		return isFinaleOIntegrativa;
	}

	private void validazioneInput(Long idUtente, String identitaDigitale,
			DocumentoDiSpesaDTO documentoDiSpesa)
	throws FormalParameterException,
	ValidazioneRendicontazioneException, Exception {

		List<String> nameParameters = new ArrayList<String>();
		nameParameters.add("idUtente");
		nameParameters.add("identitaDigitale");
		nameParameters.add("idTipologiaDocumento");
		nameParameters.add("dataDocumento");
		nameParameters.add("importoTotaleDocumento");
		nameParameters.add("idProgetto");

		List<Object> inputObjs = new ArrayList<Object>();
		inputObjs.add(idUtente);
		inputObjs.add(identitaDigitale);
		inputObjs.add(documentoDiSpesa.getIdTipoDocumentoDiSpesa());
		inputObjs.add(documentoDiSpesa.getDataDocumentoDiSpesa());
		inputObjs.add(documentoDiSpesa.getImportoTotaleDocumentoIvato());
		inputObjs.add(documentoDiSpesa.getIdProgetto());

		/**
		 * Campi obbligatori in relazione al tipo di documento di spesa
		 */
		if (!isSpeseForfettaria(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
			nameParameters.add("numeroDocumento");
			nameParameters.add("descrizioneDocumento");
			nameParameters.add("idTipoFornitore");
			nameParameters.add("imponibile");
			// nameParameters.add("importoIvaACosto");
			nameParameters.add("codiceFiscaleFornitore");

			if (!documentoDiSpesaManager
					.isCedolinoOAutodichiarazioneSoci(documentoDiSpesa
							.getIdTipoDocumentoDiSpesa())) {
				nameParameters.add("importoRendicontabile");
				inputObjs.add(documentoDiSpesa.getImportoRendicontabile());
			}

			inputObjs.add(documentoDiSpesa.getNumeroDocumento());
			inputObjs.add(documentoDiSpesa.getDescrizioneDocumentoDiSpesa());
			inputObjs.add(documentoDiSpesa.getIdTipoFornitore());
			inputObjs.add(documentoDiSpesa.getImponibile());
			// inputObjs.add(documentoDiSpesa.getImportoIvaACosto());
			inputObjs.add(documentoDiSpesa.getCodiceFiscaleFornitore());

			if (isNotaDiCredito(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
				nameParameters.add("idDocRiferimento");
				inputObjs.add(documentoDiSpesa.getIdDocRiferimento());
			}

			/**
			 * Campi obbligatori in relazione al tipo di fornitore
			 */

		}

		ValidatorInput.verifyNullValue(nameParameters.toArray(new String[] {}),
				inputObjs.toArray());
	}

	private boolean isSpeseForfettaria(Long idTipoDocumentoSpesa)
	throws ValidazioneRendicontazioneException {
		boolean result = false;
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				idTipoDocumentoSpesa);
		if (!isNull(decodifica)
				&& !isEmpty(decodifica.getDescrizioneBreve())) {
			if (decodifica
					.getDescrizioneBreve()
					.equals(DocumentoDiSpesaManager
							.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE)))
				result = true;
		} else {
			logger.warn("decodifica del tipo documento di spesa NON è stata TROVATA !!!");
			throw new ValidazioneRendicontazioneException(
					"decodifica del tipo documento di spesa NON è stata TROVATA per idTipo:"
					+ idTipoDocumentoSpesa);
		}
		return result;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 * @throws ManagerException
	 */
	private boolean checkEsisteFornitore(DocumentoDiSpesaDTO dto)
	throws ManagerException {

		boolean isCedolino = documentoDiSpesaManager
		.isCedolinoOAutodichiarazioneSoci(dto
				.getIdTipoDocumentoDiSpesa());
		String[] codQualificheDaEscludere = null;

		if (isCedolino) {
			codQualificheDaEscludere = new String[] { "NN" };
		}
		List<Map> fornitori = pbandiDocumentiDiSpesaDAO.findFornitore(
				dto.getCodiceFiscaleFornitore(), isCedolino,
				codQualificheDaEscludere);
		if (fornitori != null && fornitori.size() > 0)
			return true;
		else
			return false;

	}

	/**
	 * Verifica che il documento di spesa sia unico
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkDocumentoDiSpesaUnico(DocumentoDiSpesaDTO dto) {
		HashMap<String, String> trsMap = new HashMap<String, String>();
		trsMap.put("numeroDocumento", "numeroDocumento");
		trsMap.put("dataDocumentoDiSpesa", "dtEmissioneDocumento");
		trsMap.put("codiceFiscaleFornitore", "codiceFiscaleFornitore");
		// trsMap.put("idDocumentoDiSpesa","idDocumentoDiSpesa");
		trsMap.put("idSoggetto", "idSoggettoFornitore");
		DocumentoDiSpesaPerControlloUnivocitaVO vo = getBeanUtil()
		.transform(dto,
				DocumentoDiSpesaPerControlloUnivocitaVO.class,
				trsMap);
		DocumentoDiSpesaPerControlloUnivocitaVO voNot = new DocumentoDiSpesaPerControlloUnivocitaVO();
		voNot.setIdDocumentoDiSpesa(getBeanUtil().transform(
				dto.getIdDocumentoDiSpesa(), BigDecimal.class));
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				dto.getIdTipoDocumentoDiSpesa());
		vo.setDescBreveTipoDocSpesa(decodifica.getDescrizioneBreve());
		return (genericDAO.count(Condition.filterBy(vo).and(
				Condition.not(Condition.filterBy(voNot)))) == 0);
	}

	/**
	 * Verifica che l' importo iva a costo sia minore o uguale all' importo
	 * dell' imposta
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkImportoIvaACosto(DocumentoDiSpesaDTO dto) {
		double importoIvaACosto = dto.getImportoIvaACosto() == null ? 0
				: dto.getImportoIvaACosto();
		double importoIva = NumberUtil.getDoubleValue(dto.getImportoIva());
		if (NumberUtil.toRoundDouble(importoIvaACosto) <= NumberUtil
				.toRoundDouble(importoIva))
			return true;
		else
			return false;
	}

	/**
	 * Verifica che l' importo rendicontabile sia minore o uguale alla somma
	 * (importo imponibile + importo iva a costo)
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkImportoRendicontabile(DocumentoDiSpesaDTO dto) {
		double importoRendicontabile = NumberUtil.getDoubleValue(dto
				.getImportoRendicontabile());
		double imponibile = NumberUtil.getDoubleValue(dto.getImponibile());
		double importoIvaACosto = NumberUtil.getDoubleValue(dto
				.getImportoIvaACosto());
		if (NumberUtil.toRoundDouble(importoRendicontabile) <= (NumberUtil
				.toRoundDouble(imponibile) + NumberUtil
				.toRoundDouble(importoIvaACosto)))
			return true;
		else
			return false;
	}

	/**
	 * Verifica che la somma degli importi rendicontabili sui vari progetti sia
	 * <= al massimo rendicontabile (imponibile + importo iva a costo) del
	 * documento di spesa
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkTotaleRendicontabile(DocumentoDiSpesaDTO dto) {
		/**
		 * Nel calcolo della somma degli importi rendicontabili, non considero
		 * quelli relativi al progetto in oggetto, poiche' l' utente li sta
		 * modificando.
		 */
		List<DocumentoDiSpesaVO> documentiVO = pbandiDocumentiDiSpesaDAO
		.findImportiDocumentoDiSpesaProgetto(
				dto.getIdDocumentoDiSpesa(), null);
		double imponibile = NumberUtil.getDoubleValue(dto.getImponibile());
		double importoIvaACosto = NumberUtil.getDoubleValue(dto
				.getImportoIvaACosto());
		double maxRendicontabile = imponibile + importoIvaACosto;
		double importoRendicontabile = NumberUtil.getDoubleValue(dto
				.getImportoRendicontabile());
		double totaleRendicontabile = 0.0;
		for (DocumentoDiSpesaVO vo : documentiVO) {
			if (!vo.getIdProgetto().equals(dto.getIdProgetto()))
				totaleRendicontabile += NumberUtil.getDoubleValue(vo
						.getImportoRendicontabile());
		}
		totaleRendicontabile += importoRendicontabile;
		if (NumberUtil.toRoundDouble(totaleRendicontabile) <= NumberUtil
				.toRoundDouble(maxRendicontabile))
			return true;
		else
			return false;
	}

	/**
	 * Verifica che il totale delle quote parte delle voci di spesa associate al
	 * documento di spesa/progetto sia <= all' importo rendicontabile del
	 * documento di spesa/progetto.
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkTotaleQuotaParte(DocumentoDiSpesaDTO dto) {
		/**
		 * Se esistono quote parte associate al documento di spesa/progetto
		 * allora eseguo il test, altrimenti no
		 */
		double importoRendicontabile = NumberUtil.getDoubleValue(dto
				.getImportoRendicontabile());
		List<PbandiTQuotaParteDocSpesaVO> quoteVO = pbandiDocumentiDiSpesaDAO
		.findImportiQuotaParteByDocumentoDiSpesaProgetto(
				dto.getIdDocumentoDiSpesa(), dto.getIdProgetto(),
				null);
		if (quoteVO != null && quoteVO.size() > 0) {
			logger.debug("Il documento di spesa["
					+ dto.getIdDocumentoDiSpesa()
					+ "]  ha quote parte associate.");
			double totaleQuoteParte = 0.0;
			for (PbandiTQuotaParteDocSpesaVO quotaVO : quoteVO) {
				totaleQuoteParte += NumberUtil.getDoubleValue(quotaVO
						.getImportoQuotaParteDocSpesa());
			}
			if (NumberUtil.toRoundDouble(totaleQuoteParte) <= NumberUtil
					.toRoundDouble(importoRendicontabile))
				return true;
			else
				return false;
		} else {
			logger.debug("Il documento di spesa[" + importoRendicontabile
					+ "] non ha quote parte associate.");
			return true;
		}

	}

	/**
	 * Verifica che il totale degli importi di pagamenti associati al documento
	 * di spesa sia <= all' importo totale del documento.
	 * 
	 * @param dto
	 * @return
	 * @throws ValidazioneRendicontazioneException
	 */
	private boolean checkTotalePagamenti(DocumentoDiSpesaDTO dto)
	throws ValidazioneRendicontazioneException {
		try {
			/**
			 * Se esistono pagamenti associati allora eseguo il test, altrimenti
			 * no.
			 */

			List<PagamentoQuotePartiVO> pagamentiVO = pbandipagamentiDAO
			.findPagamentiAssociati(dto.getIdDocumentoDiSpesa(),
					dto.getIdProgetto());
			double importoTotaleDocumentoIvato = NumberUtil.getDoubleValue(dto
					.getImportoTotaleDocumentoIvato());
			/*
			 * FIX PBandi-433 Nel caso di CEDOLINO l' importo da confrontare con
			 * il pagamenti e' dato dal massimo tra il totale documento ed l'
			 * importo rendicontabile
			 */
			if (documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(dto
					.getIdTipoDocumentoDiSpesa())) {
				if (NumberUtil.getDoubleValue(dto.getImportoRendicontabile()) > importoTotaleDocumentoIvato)
					importoTotaleDocumentoIvato = NumberUtil.getDoubleValue(dto
							.getImportoRendicontabile());
			}

			if (pagamentiVO != null && pagamentiVO.size() > 0) {
				logger.debug("Il documento di spesa["
						+ dto.getIdDocumentoDiSpesa()
						+ "] ha pagamenti associati.");
				double totalePagamenti = 0.0;
				for (PagamentoQuotePartiVO pagamentoQuotePartiVO : pagamentiVO) {
					totalePagamenti += NumberUtil
					.getDoubleValue(pagamentoQuotePartiVO
							.getImportoPagamento());
				}
				if (NumberUtil.toRoundDouble(totalePagamenti) <= NumberUtil
						.toRoundDouble(importoTotaleDocumentoIvato))
					return true;
				else
					return false;
			} else {
				logger.debug("Il documento di spesa["
						+ importoTotaleDocumentoIvato
						+ "] non ha pagamenti associati.");
				return true;
			}

		} catch (Exception e) {
			ValidazioneRendicontazioneException vde = new ValidazioneRendicontazioneException(
					ERRORE_SERVER);
			logger.error(
					"Non e' stato possibile portare a termine l'operazione: "
					+ e.getMessage(), vde);
			throw vde;
		} 
	}

	/**
	 * Verifica la conguita dei dati. Nel caso in cui il tipo di documento e'
	 * CEDOLINO devo verificare che: - se il bando segue la regola BR01 allora
	 *  
	 * 
	 * @param dto
	 * @return
	 * @throws FormalParameterException
	 * @throws ManagerException
	 */
	private boolean checkCongruitaDati(DocumentoDiSpesaDTO dto)
	throws FormalParameterException, ManagerException {

		String codiceRegolaBr01 = DocumentoDiSpesaManager
		.getCodiceRegolaDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.REGOLA_BR01);
		if (documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(dto
				.getIdTipoDocumentoDiSpesa())) {
			Long progrLineaIntervento = pbandiDocumentiDiSpesaDAO
			.getProgrLineaIntervento(dto.getIdProgetto());
			if (regolaManager.isRegolaApplicabileForBandoLinea(
					progrLineaIntervento, codiceRegolaBr01)) {
				List<Map> lista = pbandiDocumentiDiSpesaDAO
				.getDatiFornitoreQualifica(dto
						.getCodiceFiscaleFornitore());
				if (lista != null && lista.size() > 0)
					return true;
				else
					return false;
			} else {
				return true;
			}
		} else
			return true;
	}

	/**
	 * Verifica che la data di emissione del documento sia successiva o uguale
	 * alla data di presentazione della domanda. Questo controllo non viene
	 * eseguito nel caso in cui la tipologia di spesa e' QUOTA AMMORTAMENTO
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkDataDocumento(DocumentoDiSpesaDTO dto) {
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				dto.getIdTipoDocumentoDiSpesa());
		if (decodifica
				.getDescrizioneBreve()
				.equals(DocumentoDiSpesaManager
						.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_QUOTA_DI_AMMORTAMENTO)))
			return true;
		else {
			DocumentoDiSpesaVO vo = new DocumentoDiSpesaVO();
			getBeanUtil().valueCopy(dto, vo);
			List<Map> lista = pbandiDocumentiDiSpesaDAO
			.getDomandeWithDataPresentazionePrecedenteDocumento(vo);
			if (lista != null && lista.size() > 0)
				return true;
			else
				return false;
		}

	}

	/**
	 * Verifica che la data del documento non sia superiore alla data odierna
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkData(DocumentoDiSpesaDTO dto) {
		boolean result = false;
		if (dto.getDataDocumentoDiSpesa().before(new java.util.Date()))
			result = true;
		return result;
	}


	/*
	 * **********************************************************************************************************************************
	 * 70228 23/09/2009 NUOVA VALIDAZIONE : VALIDA TUTTI
	 */
	@Deprecated
	// by validaMultiplo
	public EsitoValidaTutti validaTuttiIPagamentiEVociDiSpesa(Long idUtente,
			String identitaDigitale,
			ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
	throws CSIException, SystemException, UnrecoverableException,
	ValidazioneRendicontazioneException {

		try {

			String[] nameParameter = { "idUtente", "identitaDigitale",
			"validazioneRendicontazioneDTO" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, validazioneRendicontazioneDTO);

			EsitoValidaTutti esito = new EsitoValidaTutti();

			//List<String> statiAmmessiPagamento = getStatiValidiPagamento();

			List<String> returnParams = new ArrayList<String>();

			Long idDichiarazioneDiSpesa = validazioneRendicontazioneDTO
			.getIdDichiarazioneDiSpesa();

			Long idBandoLinea = validazioneRendicontazioneDTO.getIdBandoLinea();

			logger.debug("idBandoLinea ---> " + idBandoLinea+" ,idDichiarazioneDiSpesa ---> "
					+ idDichiarazioneDiSpesa);

			// a) cerco tutti i doc di spesa			
			/*
			List<it.csi.pbandi.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO> documenti = pbandiDocumentiDiSpesaDAO
					.findDocumentiDiSpesaPerValidazione(idDichiarazioneDiSpesa,
							statiAmmessiPagamento);
			 */

			/*
			 * Ricerco i documenti per la validazione
			 */
			DocumentiDiSpesaPerValidazioneVO filterDichiarazioneVO = new DocumentiDiSpesaPerValidazioneVO();
			filterDichiarazioneVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa));

			List<DocumentiDiSpesaPerValidazioneVO> documenti = genericDAO.findListWhere(filterDichiarazioneVO);


			logger.debug("documenti totali: " + documenti.size());
			if (!isEmpty(documenti)) {
				scartaDocumentiNonValidi(idBandoLinea, idDichiarazioneDiSpesa, documenti);
			}
			logger.debug("documenti da validare : " + documenti.size());
			if (isEmpty(documenti)) {
				// params.add("" + idPagamentiSospesi.size());
				esito.setEsito(Boolean.FALSE);
				esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE);
				esito.setParams(returnParams.toArray(new String[0]));
				return esito;
			}

			List<Long> idDocumentiDiSpesa = new ArrayList<Long>();
			for (DocumentiDiSpesaPerValidazioneVO documentoDiSpesaVO : documenti) {
				idDocumentiDiSpesa.add(NumberUtil.toLong(documentoDiSpesaVO.getIdDocumentoDiSpesa()));
			}

			logger.debug("cerco i pagamenti collegati ai doc");
			// id_dichiarazione, stato_pagamento (DICHIARATO),id_documenti

			/*
			 * FIX PBANDI-2314.
			 * Non esistono piu' gli stati dei pagamenti
			List<Long> idPagamenti = pbandipagamentiDAO.findIdPagamentiByStato(
					idDichiarazioneDiSpesa, statiAmmessiPagamento,
					idDocumentiDiSpesa);
			 */

			List<PagamentoDichiarazioneVO> pagamenti = findPagamentiDocumentiDichiarazione(idDichiarazioneDiSpesa, idDocumentiDiSpesa);

			if (ObjectUtil.isEmpty(pagamenti)) {
				logger.warn("ATTENZIONE ! Nessun pagamento trovato!");
			}

			List<Long> idPagamenti =  beanUtil.extractValues(pagamenti,
					"idPagamento", Long.class);

			long pagamentiValidatiOra;
			try {
				pagamentiValidatiOra = validaTotalmenteVociDiSpesa(idUtente, idPagamenti);
			} catch (Exception e) {
				throw new UnrecoverableException("Errore nel validaPagamenti",
						e);
			}

			esito.setPagamentiValidatiOra(pagamentiValidatiOra);

			List<Map> list = pbandipagamentiDAO
			.findDatiSuPagamenti(idDichiarazioneDiSpesa);

			for (Map map : list) {
				String descStato = ((String) map.get("DESC_STATO"));
				Long val = (NumberUtil.toLong((BigDecimal) map
						.get("NUM_PAGAMENTI")));
				if (descStato.equalsIgnoreCase("G"))
					esito.setPagamentiDaRespingere(val);
				else if (descStato.equalsIgnoreCase("R"))
					esito.setPagamentiDichiarati(val);
				else if (descStato.equalsIgnoreCase("N"))
					esito.setPagamentiNonValidati(val);
				else if (descStato.equalsIgnoreCase("P"))
					esito.setPagamentiParzialmenteValidati(val);
				else if (descStato.equalsIgnoreCase("S"))
					esito.setPagamentiSospesi(val);
				else if (descStato.equalsIgnoreCase("V")) {
					if (val > pagamentiValidatiOra)
						esito.setPagamentiValidatiInPrecedenza(val
								- pagamentiValidatiOra);
					else
						esito.setPagamentiValidatiInPrecedenza(0L);
				} else if (descStato.equalsIgnoreCase("totale"))
					esito.setTotalePagamenti(val);
			}

			esito.setParams(returnParams.toArray(new String[0]));
			esito.setEsito(Boolean.TRUE);
			esito.setMessage(MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_VALIDAZIONE);
			return esito;
		} finally {
		}
	}


	/*
	 * FIX PBANDI-2314. Non esiste piu' lo stato dei pagamenti
	private long validaPagamenti(Long idUtente, List<Long> idPagamenti)
			throws Exception {

		long pagamentiValidatiOra = setStatoPagamenti(idUtente, idPagamenti,
				Constants.DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_VALIDATO);

		validaTotalmenteVociDiSpesa(idUtente, idPagamenti);
		return new Long(idPagamenti.size());
	}
	 */






	private long invalidaPagamenti(Long idUtente, List<Long> idPagamenti)
	throws Exception {
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato dei pagamenti
		long pagamentiInvalidatiOra = setStatoPagamenti(
				idUtente,
				idPagamenti,
				Constants.DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_NON_VALIDATO);
		 */
		invalidaVociDiSpesa(idUtente, idPagamenti);
		return new Long(idPagamenti.size());
	}




	private long respingiPagamenti(Long idUtente, List<Long> idPagamenti)
	throws Exception {
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato dei pagamenti
		long pagamentiRespintiOra = setStatoPagamenti(
				idUtente,
				idPagamenti,
				Constants.DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_DA_RESPINGERE);
		 */
		respingiVociDiSpesa(idUtente, idPagamenti);
		return new Long(idPagamenti.size());
	}


	private Long respingiDocumenti (Long idUtente, Long idDichiarazione, List<Long> documentiDiSpesa) throws Exception {
		/*
		 * recupero i dati relativi alla dichiarazione di spesa
		 */
		PbandiTDichiarazioneSpesaVO filterDichVO = new PbandiTDichiarazioneSpesaVO();
		filterDichVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
		PbandiTDichiarazioneSpesaVO dichiarazioneVO = genericDAO.findSingleWhere(filterDichVO);


		/*
		 * Recupero i pagamenti associati ai documenti delle dichiarazione di spesa
		 */
		List<PagamentoDichiarazioneVO> pagamentiDichidarazione = findPagamentiDocumentiDichiarazione(idDichiarazione, documentiDiSpesa);


		/*
		 * Imposto a 0 l'importo validato dei pagamenti della dichiarazione
		 * relativi ai documenti da respingere
		 */
		List<BigDecimal> pagamenti = new ArrayList<BigDecimal>();
		for (PagamentoDichiarazioneVO p : pagamentiDichidarazione)
			pagamenti.add(p.getIdPagamento());
		updateMassivoImportoValidatoPagamentiDichiarazione(idDichiarazione, idUtente, pagamenti, new BigDecimal(0));


		/*
		 * Aggiungo ai documenti le note di credito associate
		 * al documento progetto
		 */
		List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager.findNoteCreditoDocumenti(NumberUtil.toLong(dichiarazioneVO.getIdProgetto()), documentiDiSpesa);

		List<Long> idNoteCredito = beanUtil.extractValues(noteCredito, "idDocumentoDiSpesa", Long.class);
		if (!ObjectUtil.isEmpty(idNoteCredito)) {
			for(Long idNota : idNoteCredito)
				documentiDiSpesa.add(idNota);
		}


		/*
		 * Aggiorno lo stato validazione dei documenti respinti in RESPINTO
		 */
		updateMassivoStatoValidazioneDocumenti(idUtente, NumberUtil.toLong(dichiarazioneVO.getIdProgetto()), GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO, documentiDiSpesa);


		return new Long(documentiDiSpesa.size());

	}




	private int validaTotalmenteVociDiSpesa(Long idUtente,
			List<Long> idPagamenti) throws Exception {
		List<PbandiRPagQuotParteDocSpVO> quote = genericDAO.where(
				new FilterCondition(beanUtil.beanify(idPagamenti,
						"idPagamento", PbandiRPagQuotParteDocSpVO.class)))
						.select();
		BigDecimal idUtenteAgg = beanUtil.transform(idUtente, BigDecimal.class);
		for (PbandiRPagQuotParteDocSpVO quota : quote) {
			quota.setImportoValidato(quota.getImportoQuietanzato());
			quota.setIdUtenteAgg(idUtenteAgg);
			genericDAO.update(quota);
		}
		return quote.size();
	}

	private int invalidaVociDiSpesa(Long idUtente, List<Long> idPagamenti)
	throws Exception {
		List<PbandiRPagQuotParteDocSpVO> quote = genericDAO.where(
				new FilterCondition(beanUtil.beanify(idPagamenti,
						"idPagamento", PbandiRPagQuotParteDocSpVO.class)))
						.select();
		BigDecimal idUtenteAgg = beanUtil.transform(idUtente, BigDecimal.class);
		for (PbandiRPagQuotParteDocSpVO quota : quote) {
			quota.setImportoValidato(new BigDecimal(0));
			quota.setIdUtenteAgg(idUtenteAgg);
			genericDAO.update(quota);
		}
		return quote.size();
	}

	private int respingiVociDiSpesa(Long idUtente, List<Long> idPagamenti)
	throws Exception {
		List<PbandiRPagQuotParteDocSpVO> quote = genericDAO.where(
				new FilterCondition(beanUtil.beanify(idPagamenti,
						"idPagamento", PbandiRPagQuotParteDocSpVO.class)))
						.select();
		BigDecimal idUtenteAgg = beanUtil.transform(idUtente, BigDecimal.class);
		for (PbandiRPagQuotParteDocSpVO quota : quote) {
			quota.setImportoValidato(null);
			quota.setIdUtenteAgg(idUtenteAgg);
			genericDAO.updateNullables(quota);
		}
		return quote.size();
	}

	/*
	 * FIX PBANDI-2314. Non esiste piu' lo stato dei pagamenti.
	private int setStatoPagamenti(Long idUtente, List<Long> idPagamenti,
			String descBreveStato) throws Exception {
		BigDecimal idStatoValidazioneSpesa = beanUtil.transform(
				decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_VALIDAZ_SPESA,
						descBreveStato).getId(), BigDecimal.class);
		List<PbandiTPagamentoVO> pagamenti = beanUtil.beanify(idPagamenti,
				"idPagamento", PbandiTPagamentoVO.class);

		PbandiTPagamentoVO newValue = new PbandiTPagamentoVO();
		newValue.setIdUtenteAgg(beanUtil.transform(idUtente, BigDecimal.class));
		newValue.setIdStatoValidazioneSpesa(idStatoValidazioneSpesa);

		int updated = 0;
		try {
			genericDAO.update(Condition.filterBy(pagamenti), newValue);
			// non ho altro modo per trovare i modificati
			updated = genericDAO.count(Condition.filterBy(pagamenti).and(
					Condition.filterBy(newValue)));
		} catch (Exception e) {
			logger.error(
					"Impossibile eseguire la UPDATE di PBANDI_T_PAGAMENTO: stato="
							+ descBreveStato + "(" + idStatoValidazioneSpesa
							+ "), pagamenti=" + idPagamenti, e);
			throw e;
		}
		return updated;
	}
	 */

	@Deprecated
	public EsitoValidaTutti checkPagamentiPerValidaTutti(Long idUtente,
			String identitaDigitale,
			ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
	throws CSIException, SystemException, UnrecoverableException,
	ValidazioneRendicontazioneException {


		try {

			String[] nameParameter = { "idUtente", "identitaDigitale",
			"validazioneRendicontazioneDTO" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, validazioneRendicontazioneDTO);

			EsitoValidaTutti esito = new EsitoValidaTutti();

			/*
			 * FIX PBANDI-2314.
			 * Non esistono piu' gli stati dei pagamenti.
			List<String> statiAmmessiPagamento = getStatiValidiPagamento();
			 */

			List<String> returnParams = new ArrayList<String>();

			List<Long> idPagamenti = new ArrayList<Long>();

			Long idDichiarazioneDiSpesa = validazioneRendicontazioneDTO
			.getIdDichiarazioneDiSpesa();

			Long idBandoLinea = validazioneRendicontazioneDTO.getIdBandoLinea();

			logger.debug("idBandoLinea ---> " + idBandoLinea+" ,idDichiarazioneDiSpesa ---> "
					+ idDichiarazioneDiSpesa);

			// a) cerco tutti i doc di spesa
			/*
			 * FIX PBANDI-2314.
			 * Non esistono piu' gli stati del pagamento.
			List<it.csi.pbandi.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO> documenti = pbandiDocumentiDiSpesaDAO
					.findDocumentiDiSpesaPerValidazione(idDichiarazioneDiSpesa,
							statiAmmessiPagamento);
			 */



			DocumentiDiSpesaPerValidazioneVO filterDidichiarazioneVO = new DocumentiDiSpesaPerValidazioneVO();
			filterDidichiarazioneVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa));
			List<DocumentiDiSpesaPerValidazioneVO> documenti = genericDAO.findListWhere(filterDidichiarazioneVO);


			if (isEmpty(documenti)) {
				esito.setEsito(Boolean.FALSE);
				esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE);
				returnParams.add("0");
				esito.setParams(returnParams.toArray(new String[0]));
				return esito;
			}
			logger.debug("documenti totali : " + documenti.size());
			scartaDocumentiNonValidi(idBandoLinea,idDichiarazioneDiSpesa, documenti);
			logger.debug("documenti rimasti da validare: " + documenti.size());

			if (isEmpty(documenti)) {
				/*
				 * FIX PBANDI-2314.
				 * Non esiste piu' lo stato del pagamento
				if (!isEmpty(idDocumentiDiSpesa)) {
					idPagamenti = pbandipagamentiDAO.findIdPagamentiByStato(
							idDichiarazioneDiSpesa, statiAmmessiPagamento,
							idDocumentiDiSpesa);
				}
				 */
				esito.setEsito(Boolean.FALSE);
				esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE);
				/*
				 * FIX PBANDI-2314.
				 * Non esiste piu' lo stato del pagamento
				int pagamentiInStatoDichiarato = 0;
				if (!isEmpty(idPagamenti))
					pagamentiInStatoDichiarato = idPagamenti.size();
				returnParams.add("" + pagamentiInStatoDichiarato);
				esito.setParams(returnParams.toArray(new String[0]));
				 */
				return esito;
			}
			logger.debug("documenti.size() dopo i controlli : "
					+ documenti.size());



			logger.debug("cerco i pagamenti collegati ai doc");
			// id_dichiarazione, stato_pagamento (DICHIARATO),id_documenti

			/*
			 * FIX PBANDI-2314.
			 * Non esiste piu' lo stato del pagamento
			idPagamenti = pbandipagamentiDAO.findIdPagamentiByStato(
					idDichiarazioneDiSpesa, statiAmmessiPagamento,
					idDocumentiDiSpesa);
			 */



			boolean isImportoMaxSuperato = pbandipagamentiDAO
			.controllaTotaliImportiAmmessiPerVociDiSpesa(idPagamenti);

			logger.debug("isImportoMaxSuperato: " + isImportoMaxSuperato);


			if (isImportoMaxSuperato) {
				esito.setMessage(ErrorConstants.ERRORE_IMPORTO_AMMISSIBILE_SUPERATO);
			} else {
				esito.setMessage(MessaggiConstants.STATO_DOCUMENTO_DICHIARAZIONE_AGGIORNATO);

			}
			esito.setEsito(Boolean.TRUE);
			return esito;
		} finally {
		}

	}

	/*
	 * FIX PBANDI-2314. 
	 * Lo stato del pagamento non esiste piu'
	private List<String> getStatiValidiPagamento() {
		List<String> statiValidiPagamento = new ArrayList<String>();
		statiValidiPagamento
				.add(it.csi.pbandi.pbandiutil.common.Constants.StatiValidazionePagamenti.DICHIARATO
						.getDescBreve());
		return statiValidiPagamento;
	}
	 */

	/*
	 * Questo metodo è simile ma non uguale a quello del verifica dichiarazione
	 * di spesa non c'e' il controllo sulla regola br02 e sulla nota di credito
	 * perchè i documenti in stato dichiarato
	 */
	private void scartaDocumentiNonValidi(
			Long idBandoLinea,
			Long idDichiarazioneSpesa,
			List<DocumentiDiSpesaPerValidazioneVO> documenti)
	throws ValidazioneRendicontazioneException,
            FormalParameterException, ManagerException {
        boolean isBr01 = regolaManager
                .isRegolaApplicabileForBandoLinea(idBandoLinea,
                        RegoleConstants.BR01_GESTIONE_ATTRIBUTI_QUALIFICA);

        boolean isBr02 = regolaManager.isRegolaApplicabileForBandoLinea(
                idBandoLinea, RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);

        boolean isBr03 = regolaManager.isRegolaApplicabileForBandoLinea(
                idBandoLinea, RegoleConstants.BR03_RILASCIO_SPESE_QUIETANZATE);

        boolean isBr07 = regolaManager.isRegolaApplicabileForBandoLinea(
                idBandoLinea,
                RegoleConstants.BR07_CONTROLLO_SPESE_TOTALMENTE_QUIETANZATE);

        boolean isDichiarazioneFinaleOIntegrativa = isDichiarazioneFinaleOIntegrativa(idDichiarazioneSpesa);

        StringBuilder sb = new StringBuilder();
        sb.append("applicabilita' regola br01: " + isBr01 + "\n");
        sb.append("applicabilita' regola br02: " + isBr02 + "\n");
        sb.append("applicabilita' regola br03: " + isBr03 + "\n");
        sb.append("applicabilita' regola br07: " + isBr07 + "\n");

        Iterator<DocumentiDiSpesaPerValidazioneVO> iter = documenti
                .iterator();
        while (iter.hasNext()) {
            DocumentiDiSpesaPerValidazioneVO documentoDiSpesaVO = iter.next();

            boolean importoQuietanzatoSuperioreImportoDocumento = isImportoQuietanzatoSuperioreImportoDocumento(
                    documentoDiSpesaVO);
            boolean cedolino = documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(
                    NumberUtil.toLong(documentoDiSpesaVO.getIdTipoDocumentoDiSpesa()));
            boolean completamenteQuietanziato = isCompletamenteQuietanziato(documentoDiSpesaVO);
            // boolean notaDiCredito =
            // isNotaDiCredito(documentoDiSpesaVO.getIdTipoDocumentoDiSpesa());

            if (!cedolino) {
                if (!isPagamentiCompletamenteAssociati(documentoDiSpesaVO)) {
                    sb.append("rimuovo documento con id "
                            + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                            + " perchè PAGAMENTI NON COMPLETAMENTE ASSOCIATI \n");
                    iter.remove();
                    continue;
                }
            }

            /*
             * if (isBr01) {
             * logger.debug("Regola BR01 applicabile ! controllo che il doc "
             * + documentoDiSpesaVO.getIdDocumentoDiSpesa()
             * + " sia completamente quietanziato (solo se non è una spesa forfettaria)");
             * if (cedolino) {
             * logger.debug("BR01 - il doc <"
             * + documentoDiSpesaVO.getIdDocumentoDiSpesa()
             * + "> E' UN DOC di spesa di tipo CEDOLINO");
             * if (isMonteOreSuperato(documentoDiSpesaVO.getIdFornitore(),
             * documentoDiSpesaVO.getIdDocumentoDiSpesa())) {
             * logger.debug("BR01 - il doc <"
             * + documentoDiSpesaVO.getIdDocumentoDiSpesa()
             * + "> NON È una completamente quietanziato !");
             * sb.append("rimuovo documento con id "
             * + documentoDiSpesaVO.getIdDocumentoDiSpesa()
             * + " perchè BR01 e MONTE ORE SUPERATO \n");
             * iter.remove();
             * continue;
             * }
             * }
             * }
             */

            if (isBr03) {
                logger.debug("Regola BR03 applicabile ! controllo che il doc "
                        + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                        + " sia completamente quietanziato (solo se non è una spesa forfettaria)");
                /*
                 * FIX: PBandi-387 e PBandi-400 Applico la regola BR03 anche per
                 * i documenti di tipo SPESA FORFETTARIA, mentre non la applico
                 * per i documenti di tipo NOTA DI CREDITO
                 */

                // if (!notaDiCredito) {
                if (!completamenteQuietanziato) {
                    logger.debug("BR03 - il doc <"
                            + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                            + "> NON È  completamente quietanziato !");
                    sb.append("rimuovo documento con id "
                            + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                            + " perchè BR03 e NON COMPLETAMENTE QUIETANZIATO \n");
                    iter.remove();
                    continue;
                }
                // }
            }
            /**
             * FIXME: Gli analisti non hanno ancora le idee chiare
             */

            if (isBr07 && isDichiarazioneFinaleOIntegrativa) {
                logger.debug("Regola BR07 applicabile ! controllo che il doc "
                        + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                        + " sia completamente quietanziato (solo se non è una spesa forfettaria)");
                /*
                 * FIX: PBandi-387 PBandi-400. Applico la regola BR07 anche per
                 * i documenti di tipo SPESA FORFETTARIA, mentre non la applico
                 * per i documenti di tipo NOTA DI CREDITO
                 */
                // if (!notaDiCredito) {
                if (!completamenteQuietanziato) {
                    logger.debug("BR07 - il doc <"
                            + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                            + "> NON È  completamente quietanziato !");
                    sb.append("rimuovo documento con id "
                            + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                            + " perchè BR07 e  NON COMPLETAMENTE QUIETANZIATO \n");
                    iter.remove();
                    continue;
                }
                // }
            }

            if (!isRendicontabileCompletamenteAssociato(documentoDiSpesaVO)) {
                sb.append("rimuovo documento con id "
                        + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                        + " perchè Rendicontabile Completamente Associato \n");
                iter.remove();
                continue;
            }
            if (isSpesaForfettaria(NumberUtil.toLong(documentoDiSpesaVO.getIdTipoDocumentoDiSpesa()))) {
                // le spese forfettarie hanno superato la misura
                // massima stabilita
                sb.append("rimuovo documento con id "
                        + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                        + " perchè Spesa Forfettaria \n");
                iter.remove();
                continue;
            }
            if (isFattura(NumberUtil.toLong(documentoDiSpesaVO.getIdTipoDocumentoDiSpesa()))) {
                if (importoQuietanzatoSuperioreImportoDocumento) {
                    sb.append("rimuovo documento con id "
                            + documentoDiSpesaVO.getIdDocumentoDiSpesa()
                            + " perchè IMPORTO QUIETANZATO SUPERIORE IMPORTO DOCUMENTO \n");
                    iter.remove();
                    continue;
                }
            }
        }

        logger.info(sb.toString());
    }

    private boolean isCompletamenteQuietanziato(
            it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO documentoDiSpesaVO)
            throws ManagerException {
        boolean result = true;
        double importoTotaleDocumento = 0;
        /*
         * Se il documento e' un CEDOLINO allora l' importo sul quale applicare
         * l' algoritmo e' dato dal massimo tra l' importo rendicontabile e
         * l'importo del documento. In tutti gli altri casi l' importo e'
         * rappresentato dal totale del documento.
         */

        if (documentoDiSpesaManager
                .isCedolinoOAutodichiarazioneSoci(documentoDiSpesaVO
                        .getIdTipoDocumentoDiSpesa())) {
            double totaleDocumento = NumberUtil
                    .getDoubleValue(documentoDiSpesaVO
                            .getImportoTotaleDocumento());
            double importoRendicontabile = NumberUtil
                    .getDoubleValue(documentoDiSpesaVO
                            .getImportoRendicontabile());
            if (totaleDocumento > importoRendicontabile)
                importoTotaleDocumento = totaleDocumento;
            else
                importoTotaleDocumento = importoRendicontabile;
        } else {
            importoTotaleDocumento = NumberUtil
                    .getDoubleValue(documentoDiSpesaVO
                            .getImportoTotaleDocumento());
        }

        /*
         * Recupero le eventuali note di credito alle quali il documento e'
         * associato e calcolo il totale del documento al netto di eventuali
         * note di credito
         */
        /*
         * FIX. PBandi-471. Riattivo il controllo per le note di credito.
         */

        importoTotaleDocumento = importoTotaleDocumento
                - NumberUtil.getDoubleValue(documentoDiSpesaVO
                        .getTotaleNoteCredito());

        /*
         * FIX: PBandi-550 Correzione arrotondamento
         */
        if (NumberUtil.toRoundDouble(importoTotaleDocumento) > NumberUtil
                .toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO
                        .getTotaleTuttiPagamenti())))
            result = false;

        return result;

    }

    private boolean isCompletamenteQuietanziato(
            DocumentiDiSpesaPerValidazioneVO documento) throws ManagerException {
        boolean result = true;
        double importoTotaleDocumento = 0;
        /*
         * Se il documento e' un CEDOLINO allora l' importo sul quale applicare
         * l' algoritmo e' dato dal massimo tra l' importo rendicontabile e
         * l'importo del documento. In tutti gli altri casi l' importo e'
         * rappresentato dal totale del documento.
         */

        if (documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(beanUtil
                .transform(documento.getIdTipoDocumentoDiSpesa(), Long.class))) {
            double totaleDocumento = NumberUtil.getDoubleValue(documento
                    .getImportoTotaleDocumento());
            double importoRendicontabile = NumberUtil.getDoubleValue(documento
                    .getImportoRendicontabile());
            if (totaleDocumento > importoRendicontabile)
                importoTotaleDocumento = totaleDocumento;
            else
                importoTotaleDocumento = importoRendicontabile;
        } else {
            importoTotaleDocumento = NumberUtil.getDoubleValue(documento
                    .getImportoTotaleDocumento());
        }

        /*
         * Recupero le eventuali note di credito alle quali il documento e'
         * associato e calcolo il totale del documento al netto di eventuali
         * note di credito
         */
        /*
         * FIX. PBandi-471. Riattivo il controllo per le note di credito.
         */

        importoTotaleDocumento = importoTotaleDocumento
                - NumberUtil.getDoubleValue(documento.getTotaleNoteCredito());

        /*
         * FIX: PBandi-550 Correzione arrotondamento
         */
        if (NumberUtil.toRoundDouble(importoTotaleDocumento) > NumberUtil
                .toRoundDouble(NumberUtil.getDoubleValue(documento
                        .getTotaleTuttiPagamenti())))
            result = false;

        return result;

    }

    private boolean isImportoQuietanzatoSuperioreImportoDocumento(
            it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO documentoDiSpesaVO) {
        double totaleDocumento = documentoDiSpesaVO.getImportoTotaleDocumento() == null ? 0
                : documentoDiSpesaVO.getImportoTotaleDocumento();
        /*
         * FIX: PBandi-550 Correzione arrotondamento
         */
        if (NumberUtil.toRoundDouble(totaleDocumento
                - NumberUtil.toRoundDouble(NumberUtil
                        .getDoubleValue(documentoDiSpesaVO
                                .getTotaleNoteCredito()))) < NumberUtil
                                        .toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO
                                                .getTotaleTuttiPagamenti())))
            return true;
        else
            return false;

    }

    // }L{ rifattorizzato: arrotondamenti già a posto (PBANDI-550)
    private boolean isImportoQuietanzatoSuperioreImportoDocumento(
            DocumentiDiSpesaPerValidazioneVO documento) {
        BigDecimal totaleDocumento = ObjectUtil.nvl(
                documento.getImportoTotaleDocumento(), new BigDecimal(0));
        BigDecimal totaleTuttiPagamenti = ObjectUtil.nvl(
                documento.getTotaleTuttiPagamenti(), new BigDecimal(0));
        BigDecimal totaleNoteDiCredito = ObjectUtil.nvl(
                documento.getTotaleNoteCredito(), new BigDecimal(0));

        return NumberUtil.compare(
                NumberUtil.subtract(totaleDocumento, totaleNoteDiCredito),
                totaleTuttiPagamenti) < 0;
    }

    @Deprecated
    private boolean isRendicontabileCompletamenteAssociato(
            it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO documentoDiSpesaVO) {
        /**
         * Recupero le voci di spesa associate al documento/progetto
         */

        /*
         * FIX: PBandi-550 Correzione arrotondamento
         */
        if (NumberUtil.toRoundDouble(documentoDiSpesaVO
                .getImportoRendicontabile()) == NumberUtil
                        .toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO
                                .getTotaleImportoQuotaParte())))
            return true;
        else
            return false;
    }

    private boolean isRendicontabileCompletamenteAssociato(
            DocumentiDiSpesaPerValidazioneVO documento) {
        return NumberUtil.compare(documento.getImportoRendicontabile(),
                documento.getTotaleImportoQuotaParte()) == 0;
    }

    private boolean isPagamentiCompletamenteAssociati(
            it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO documentoDiSpesaVO) {
        boolean result = true;

        PagamentoQuotePartiVO sommaImportiQuietanzatiVO = pbandiDichiarazioneDiSpesaDAO
                .getTotaleQuietanzati(
                        documentoDiSpesaVO.getIdDocumentoDiSpesa(),
                        documentoDiSpesaVO.getIdProgetto());
        Double sommaImportiQuietanzati = 0D;

        if (!isNull(sommaImportiQuietanzatiVO))
            sommaImportiQuietanzati = NumberUtil
                    .getDoubleValue(sommaImportiQuietanzatiVO
                            .getTotaleImportiQuietanzati());

        List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> noteCredito = pbandiDocumentiDiSpesaDAO
                .findNoteDiCreditoAssociateFattura(documentoDiSpesaVO
                        .getIdDocumentoDiSpesa());
        double totaleNoteCreditoRendicontabile = 0.0;
        for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO notaCredito : noteCredito) {
            double totaleRendicontabile = notaCredito
                    .getImportoRendicontabile() == null ? 0
                            : notaCredito
                                    .getImportoRendicontabile();
            totaleNoteCreditoRendicontabile += totaleRendicontabile;
        }

        List<PagamentoQuotePartiVO> pagamentiAssociatiAlProgettoVO = pbandiDichiarazioneDiSpesaDAO
                .getPagamentiAssociatiAVociDiSpesa(
                        documentoDiSpesaVO.getIdDocumentoDiSpesa(),
                        documentoDiSpesaVO.getIdProgetto());
        Double sommaPagamenti = new Double(0);
        if (pagamentiAssociatiAlProgettoVO != null) {
            for (PagamentoQuotePartiVO pagamentoQuotePartiVO : pagamentiAssociatiAlProgettoVO) {
                sommaPagamenti += NumberUtil
                        .getDoubleValue(pagamentoQuotePartiVO
                                .getImportoPagamento());
            }
        }
        /*
         * Se la differenza tra il totale dei pagamenti ed il totale delle voci
         * di spesa quietanzate e' maggiore di 0 allora confronto il totale
         * delle voci di spesa quietanzate con l' importo rendicontabile. Se l'
         * importo rendicontabile e' maggiore del totale delle voci di spesa
         * quietanzate allora restituisco false
         */
        /*
         * FIX: PBandi-550 Correzione arrotondamento
         */

        Double importoRendicontabile = documentoDiSpesaVO
                .getImportoRendicontabile() != null
                        ? documentoDiSpesaVO
                                .getImportoRendicontabile() - totaleNoteCreditoRendicontabile
                        : 0d;
        // totaleNoteCreditoRendicontabile
        if (NumberUtil.toRoundDouble(sommaPagamenti) > NumberUtil
                .toRoundDouble(sommaImportiQuietanzati)) {
            if (NumberUtil.toRoundDouble(importoRendicontabile) > NumberUtil
                    .toRoundDouble(sommaImportiQuietanzati))
                result = false;
        }
        return result;

    }

    private boolean isSpesaForfettaria(Long idTipoDocumentoDiSpesa) {
        DecodificaDTO decodifica = getDecodificheManager().findDecodifica(
                GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
                idTipoDocumentoDiSpesa);
        return (decodifica.getDescrizioneBreve()
                .equals(DocumentoDiSpesaManager
                        .getCodiceTipoDocumentoDiSpesa(
                                GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE)));
    }

    public EsitoValidazioneRendicontazione chiudiValidazione(
            Long idUtente,
            String identitaDigitale,
            it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IstanzaAttivitaDTO istanzaAttivitaDTO,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        logger.info("\n\n\n\n\n\n\nchiudiValidazione");

        EsitoValidazioneRendicontazione esitoValidazioneRendicontazione = new EsitoValidazioneRendicontazione();
        BigDecimal idProgetto = null;
        BigDecimal idDichiarazione = null;

        String[] nameParameter = { "idUtente", "identitaDigitale",
                "validazioneRendicontazioneDTO",
                "validazioneRendicontazioneDTO.bytesFile",
                "validazioneRendicontazioneDTO.idProgetto" };

        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale,
                validazioneRendicontazioneDTO,
                validazioneRendicontazioneDTO.getBytesFile());

        ValidatorInput.verifyBeansNotEmpty(
                new String[] { "istanzaAttivitaDTO" }, istanzaAttivitaDTO);

        try {

            PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = chiudiDichiarazione(idUtente,
                    validazioneRendicontazioneDTO);
            idDichiarazione = dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa();

            modificaStatoDocDiSpesaCoinvolti(idUtente, validazioneRendicontazioneDTO);

            idProgetto = dichiarazioneDiSpesaVO.getIdProgetto();
            validazioneRendicontazioneDTO.setDataChiusura(new Date());

            EsitoSalvaModuloCheckListDTO esito = checkListManager
                    .salvaChecklistValidazione(
                            idUtente,
                            idProgetto.longValue(),
                            validazioneRendicontazioneDTO.getBytesFile(),
                            GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO,
                            validazioneRendicontazioneDTO
                                    .getIdDichiarazioneDiSpesa());

            esitoValidazioneRendicontazione.setIdDocIndexDichiarazione(esito.getIdDocumentoIndex());

            if (!esito.getEsito()) {
                if (esito.getMessage().equals(
                        ERRORE_CHECKLIST_TRASCORSO_TIMEOUT)) {
                    throw new ValidazioneRendicontazioneException(
                            esito.getMessage() + "-" + esito.getParams()[0]);
                } else {
                    throw new ValidazioneRendicontazioneException(
                            esito.getMessage());
                }
            }

            /*
             * 20/01/2014
             * La checklist vene chiusa,elimino l'associazione tra appalti e checklist
             */

            /**
             * 11/04/2014 cassata: le associazioni devono rimanere per gestione checklist
             * PbandiRProgettiAppaltiVO vo = new PbandiRProgettiAppaltiVO();
             * vo.setIdProgetto(BigDecimal.valueOf(idProgetto.longValue()));
             * genericDAO.deleteWhere(new FilterCondition<PbandiRProgettiAppaltiVO>(vo));
             */
            /**/

            /* GIUGNO 2014, added report geneneration */
            EsitoReportDettaglioDocumentiDiSpesaDTO esitoGeneraReport = generaReportDettaglioDocumentoDiSpesa(idUtente,
                    identitaDigitale, idDichiarazione.longValue());
            if (esitoGeneraReport != null) {
                // save report dettaglio documenti di spesa on index
                String nomeFile = esitoGeneraReport.getNomeFile();
                byte[] reportBytes = esitoGeneraReport.getExcelBytes();
                Node node = null;
                nomeFile = indexDAO.addTimestampToFileName(nomeFile);
                logger.warn("\n\n\n\n\n\n\n\n\n\n\nvalidazioneRendicontazioneDTO: idprogetto :"
                        + validazioneRendicontazioneDTO.getIdProgetto() +
                        " codiceProgetto: " + validazioneRendicontazioneDTO.getCodiceProgetto() +
                        " validazioneRendicontazioneDTO.getIdSoggettoBen() "
                        + validazioneRendicontazioneDTO.getIdSoggettoBen() +
                        " validazioneRendicontazioneDTO.getCfBeneficiario() "
                        + validazioneRendicontazioneDTO.getCfBeneficiario() + "\n\n\n\n");

                node = indexDAO.creaContentReportDettaglioDocSpesa(idUtente,
                        validazioneRendicontazioneDTO.getIdProgetto(),
                        validazioneRendicontazioneDTO.getCodiceProgetto(),
                        idDichiarazione.longValue(),
                        validazioneRendicontazioneDTO.getIdSoggettoBen(),
                        validazioneRendicontazioneDTO.getCfBeneficiario(), nomeFile, reportBytes);

                BigDecimal idTarget = idDichiarazione;

                String shaHex = null;
                if (reportBytes != null)
                    shaHex = DigestUtils.shaHex(reportBytes);
                PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoManager.salvaInfoNodoIndexSuDb("UUID",
                        nomeFile,
                        idTarget, null, null, new BigDecimal(validazioneRendicontazioneDTO.getIdProgetto()),
                        GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS,
                        PbandiTDichiarazioneSpesaVO.class, null, null, idUtente, shaHex);
                esitoValidazioneRendicontazione
                        .setIdReportDettaglioDocSpesa(reportDettaglioDocSpesa.getIdDocumentoIndex().longValue());
            }
            /* GIUGNO 2014, added report geneneration */

            String tipoDichiarazioneCorrente = decodificheManager
                    .findDescBreve(PbandiDTipoDichiarazSpesaVO.class,
                            dichiarazioneDiSpesaVO.getIdTipoDichiarazSpesa());

            String descrBreveTemplateNotifica = "";
            List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();

            if (tipoDichiarazioneCorrente
                    .equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA)
                    || tipoDichiarazioneCorrente
                            .equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE)) {
                descrBreveTemplateNotifica = Notification.NOTIFICA_VALIDAZIONE_DI_SPESA_FINALE;
                if (validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa() != null
                        && validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa().equalsIgnoreCase("S")) {
                    logger.warn(
                            "\n\n\n############################ NEOFLUX unlock VALID_DICH_SPESA_FINALE ##############################\n");
                    neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                            Task.VALID_DICH_SPESA_FINALE);
                    logger.warn(
                            "############################ NEOFLUX unlock VALID_DICH_SPESA_FINALE ##############################\n\n\n\n");
                } else {
                    logger.warn(
                            "\n\n\n############################ NEOFLUX end VALID_DICH_SPESA_FINALE ##############################\n");
                    neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                            Task.VALID_DICH_SPESA_FINALE);
                    logger.warn(
                            "############################ NEOFLUX end VALID_DICH_SPESA_FINALE ##############################\n\n\n\n");
                }
                /*
                 * ${desc_dichiarazione_di_spesa}
                 * ${data_chiusura_validazione}
                 * ${data_dichiarazione_di_spesa}
                 * ${num_dichiarazione_di_spesa}
                 */

                MetaDataVO metadata1 = new MetaDataVO();
                metadata1.setNome(Notification.DATA_CHIUSURA_VALIDAZIONE);
                metadata1.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtChiusuraValidazione()));
                metaDatas.add(metadata1);

                MetaDataVO metadata2 = new MetaDataVO();
                metadata2.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
                metadata2.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtDichiarazione()));
                metaDatas.add(metadata2);

                MetaDataVO metadata3 = new MetaDataVO();
                metadata3.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
                metadata3.setValore(dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa().toString());
                metaDatas.add(metadata3);
            } else {
                descrBreveTemplateNotifica = Notification.NOTIFICA_VALIDAZIONE;
                logger.warn(
                        "\n\n\n############################ NEOFLUX UNLOCK  VALID_DICH_SPESA ##############################\n");
                neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                        Task.VALID_DICH_SPESA);
                logger.warn(
                        "############################ NEOFLUX UNLOCK VALID_DICH_SPESA ##############################\n\n\n\n");

                MetaDataVO metadata1 = new MetaDataVO();
                metadata1.setNome(Notification.DESC_DICHIARAZIONE_DI_SPESA);
                metadata1.setValore(dichiarazioneDiSpesaVO.getNoteChiusuraValidazione());
                metaDatas.add(metadata1);

                MetaDataVO metadata2 = new MetaDataVO();
                metadata2.setNome(Notification.DATA_CHIUSURA_VALIDAZIONE);
                metadata2.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtChiusuraValidazione()));
                metaDatas.add(metadata2);

                MetaDataVO metadata3 = new MetaDataVO();
                metadata3.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
                metadata3.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtDichiarazione()));
                metaDatas.add(metadata3);

                MetaDataVO metadata4 = new MetaDataVO();
                metadata4.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
                metadata4.setValore(dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa().toString());
                metaDatas.add(metadata4);
            }

            logger.info("calling genericDAO.callProcedure().putNotificationMetadata VALID_DICH_SPESA");
            genericDAO.callProcedure().putNotificationMetadata(metaDatas);

            logger.info("calling genericDAO.callProcedure().sendNotificationMessage VALID_DICH_SPESA");
            genericDAO.callProcedure().sendNotificationMessage(idProgetto, descrBreveTemplateNotifica,
                    Notification.BENEFICIARIO, idUtente);

            esitoValidazioneRendicontazione.setEsito(Boolean.TRUE);

            esitoValidazioneRendicontazione.setMessage(MessaggiConstants.DICHIARAZIONE_CHIUSA);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            if (e instanceof ValidazioneRendicontazioneException) {
                throw (ValidazioneRendicontazioneException) e;
            } else {
                // RuntimeException re = new RuntimeException(
                // "Errore in chiudiValidazione,non creo doc su INDEX e rollback delle modifiche
                // sul db");
                logger.error(e.getMessage(), e);
                throw new ValidazioneRendicontazioneException(e.getMessage(), e);
            }
        }

        return esitoValidazioneRendicontazione;
    }

    private PbandiTDichiarazioneSpesaVO chiudiDichiarazione(Long idUtente,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
            throws Exception {

        logger.info("chiudiDichiarazione ");
        PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
        pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(
                validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa()));
        pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);
        pbandiTDichiarazioneSpesaVO.setIdUtenteAgg(new BigDecimal(idUtente));
        pbandiTDichiarazioneSpesaVO.setNoteChiusuraValidazione(validazioneRendicontazioneDTO.getNote());

        pbandiTDichiarazioneSpesaVO.setFlagRichiestaIntegrativa(validazioneRendicontazioneDTO
                .getFlagRichiestaIntegrativa());

        pbandiTDichiarazioneSpesaVO.setDtChiusuraValidazione(DateUtil.getSysdate());
        pbandiTDichiarazioneSpesaVO.setIdUtenteAgg(new BigDecimal(idUtente));
        genericDAO.update(pbandiTDichiarazioneSpesaVO);

        // +Green: inizio modifica DS Integrativa del progetto contributo.

        // Verifico se la DS è di tipo INTEGRATIVA.
        Long idTipoDichiarazSpesa = pbandiTDichiarazioneSpesaVO.getIdTipoDichiarazSpesa().longValue();
        logger.info("chiudiDichiarazione idTipoDichiarazSpesa=" + idTipoDichiarazSpesa);

        if (Constants.ID_TIPO_DICHIARAZIONE_INTEGRATIVA.intValue() == idTipoDichiarazSpesa.intValue()) {

            // Verifico se si tratta di progetto +Green.
            Long idProgetto = pbandiTDichiarazioneSpesaVO.getIdProgetto().longValue();
            DatiPiuGreenDTO datiPiuGreen = gestioneDatiGeneraliBusiness.findDatiPiuGreen(idUtente, "idFinto",
                    idProgetto);
            if (datiPiuGreen != null && datiPiuGreen.getIdProgettoContributo() != null) {

                Long idProgettoContributo = datiPiuGreen.getIdProgettoContributo();
                logger.info("Il progetto con id " + idProgetto + " è +Green: id progetto contributo = "
                        + idProgettoContributo);

                // Recupero la DS relativa al progetto contributo.
                // PBANDI_T_DICHIARAZIONE_SPESA
                PbandiTDichiarazioneSpesaVO dsContributoFiltro = new PbandiTDichiarazioneSpesaVO();
                dsContributoFiltro.setIdProgetto(new BigDecimal(idProgettoContributo));
                dsContributoFiltro.setIdTipoDichiarazSpesa(new BigDecimal(idTipoDichiarazSpesa));
                dsContributoFiltro.setDescendentOrder("idDichiarazioneSpesa");
                List<PbandiTDichiarazioneSpesaVO> listaDS = genericDAO.findListWhere(dsContributoFiltro);
                if (listaDS.size() > 0) {

                    PbandiTDichiarazioneSpesaVO dsContributo = listaDS.get(0);
                    logger.info("+Green: trovato id dichSpesaContributo = " + dsContributo.getIdDichiarazioneSpesa());

                    // Modifico la DS Integrativa del progetto contributo
                    dsContributo.setIdUtenteAgg(new BigDecimal(idUtente));
                    dsContributo.setNoteChiusuraValidazione(validazioneRendicontazioneDTO.getNote());
                    dsContributo
                            .setFlagRichiestaIntegrativa(validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa());
                    dsContributo.setDtChiusuraValidazione(DateUtil.getSysdate());
                    dsContributo.setIdUtenteAgg(new BigDecimal(idUtente));
                    genericDAO.update(dsContributo);
                }

            } else {
                logger.info("Il progetto con id " + idProgetto + " non è +Green.");
            }
        }
        // +Green: fine.

        return pbandiTDichiarazioneSpesaVO;
    }

    private void modificaStatoDocDiSpesaCoinvolti(Long idUtente,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
            throws ManagerException, Exception {

        logger.info("modificaStatoDocDiSpesaCoinvolti ");

				boolean isBR62Attiva = regolaManager.isRegolaApplicabileForProgetto(validazioneRendicontazioneDTO.getIdProgetto(), RegoleConstants.BR62_DICHIARAZIONE_CON_QUIETANZA_ZERO);

        String codiceStatoDichiarabile = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;

        String codiceStatoValidato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO;

        String codiceStatoNonValidato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO;

        String codiceStatoParzialmenteValidato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO;

        String codiceStatoDaCompletare = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE;

        DecodificaDTO decodificaStatoDichiarabile = decodificheManager
                .findDecodifica(
                        GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                        codiceStatoDichiarabile);

        DecodificaDTO decodificaStatoValidato = decodificheManager
                .findDecodifica(
                        GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                        codiceStatoValidato);

        DecodificaDTO decodificaStatoNonValidato = decodificheManager
                .findDecodifica(
                        GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                        codiceStatoNonValidato);

        DecodificaDTO decodificaStatoParzialmenteValidato = decodificheManager
                .findDecodifica(
                        GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                        codiceStatoParzialmenteValidato);

        DecodificaDTO decodificaStatoDaCompletare = decodificheManager
                .findDecodifica(
                        GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                        codiceStatoDaCompletare);

        DocumentiDiSpesaPerChiusuraValidazioneVO filter = new DocumentiDiSpesaPerChiusuraValidazioneVO();
        filter.setIdDichiarazioneSpesa(beanUtil.transform(
                validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa(),
                BigDecimal.class));
        List<DocumentiDiSpesaPerChiusuraValidazioneVO> documentiDiSpesa = genericDAO
                .where(filter).select();

        it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
                Constants.STOPWATCH_LOGGER);

        List<PbandiTDocumentoDiSpesaVO> updateDocInDichiarabile = new ArrayList<PbandiTDocumentoDiSpesaVO>();
        List<PbandiTDocumentoDiSpesaVO> updateDocInValidato = new ArrayList<PbandiTDocumentoDiSpesaVO>();
        List<PbandiTDocumentoDiSpesaVO> updateDocInNonValidato = new ArrayList<PbandiTDocumentoDiSpesaVO>();
        List<PbandiTDocumentoDiSpesaVO> updateDocInParzialmenteValidato = new ArrayList<PbandiTDocumentoDiSpesaVO>();
        List<PbandiTDocumentoDiSpesaVO> updateDocInDaCompletare = new ArrayList<PbandiTDocumentoDiSpesaVO>();

        for (DocumentiDiSpesaPerChiusuraValidazioneVO documentoDiSpesa : documentiDiSpesa) {
            watcher.start();
            Double totalePagamenti = NumberUtil.toDouble(documentoDiSpesa
                    .getTotalePagamenti());
            Double totaleRendicontabiliNoteDiCredito = NumberUtil
                    .toDouble(documentoDiSpesa.getNoteCreditoTotaleRen());
            Double noteCreditoTotale = NumberUtil.toDouble(documentoDiSpesa
                    .getNoteCreditoTotale());
            Long numeroDichAperte = null;
            /*
             * FIX PBANDI-2314. Non esiste piu' lo stato dei pagamenti
             * Long numeroDichAperte =
             * beanUtil.transform(documentoDiSpesa.getNumeroDichiarazioniAperte(),Long.class
             * );
             */

            if (isNull(totalePagamenti))
                totalePagamenti = 0.0;
            if (isNull(totaleRendicontabiliNoteDiCredito))
                totaleRendicontabiliNoteDiCredito = 0.0;
            if (isNull(noteCreditoTotale))
                noteCreditoTotale = 0.0;
            if (isNull(numeroDichAperte))
                numeroDichAperte = 0L;

            Double rendicontabileTotale = beanUtil.transform(documentoDiSpesa.getTotaleRendicontabili(), Double.class);
            Double importoTotaleDocumento = NumberUtil.toDouble(documentoDiSpesa.getImportoTotaleDocumento());
            if (isNull(rendicontabileTotale)) {
                rendicontabileTotale = 0.0;
            }
            if (isNull(importoTotaleDocumento)) {
                importoTotaleDocumento = 0.0;
            }

            if (documentoDiSpesaManager
                    .isCedolinoOAutodichiarazioneSoci(NumberUtil
                            .toLong(documentoDiSpesa
                                    .getIdTipoDocumentoSpesa()))) {

                if (NumberUtil.toRoundDouble(importoTotaleDocumento
                        .doubleValue()) > NumberUtil
                                .toRoundDouble(rendicontabileTotale.doubleValue())) {

                } else {
                    importoTotaleDocumento = rendicontabileTotale;
                }

            }

            totalePagamenti += noteCreditoTotale;

            boolean isAllDichiarazioniChiuse = (numeroDichAperte == 0);

            boolean modificaStatoDocInDichiarabile = false;
            /*
             * I documenti in stato RESPINTO vengono portati nello stato DICHIARABILE
             */
            if (documentoDiSpesa.getDescBreveStatoDocValid() != null
                    && documentoDiSpesa.getDescBreveStatoDocValid()
                            .equals(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO)) {

                modificaStatoDocInDichiarabile = true;
                /*
                 * Se per i documenti respinti possono essere ancora
                 * inseriti dei pagamenti allora il documento e' da portare in
                 * stato DA COMPLETARE
                 */
                if (NumberUtil.compare(NumberUtil.toRoundDouble(importoTotaleDocumento.doubleValue()),
                        NumberUtil.toRoundDouble(totalePagamenti.doubleValue())) > 0
                        && isAllDichiarazioniChiuse) {

                    boolean modificaInDaCompletare = true;

                    /*
                     * Se il documento e' associato solo ad un progetto e non ci esistono altre
                     * validazioni
                     * per il documento-progetto, allora il documento non deve essere portato nello
                     * stato DA_COMPLETARE
                     * ma in DICHIARABILE
                     */

                    /*
                     * Verifico che il documento sia associato solo al progetto corrente
                     */
                    PbandiRDocSpesaProgettoVO filterDocPrj = new PbandiRDocSpesaProgettoVO();
                    filterDocPrj.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());

                    PbandiRDocSpesaProgettoVO filterPrj = new PbandiRDocSpesaProgettoVO();
                    filterPrj.setIdProgetto(documentoDiSpesa.getIdProgetto());

                    AndCondition<PbandiRDocSpesaProgettoVO> andCondition = new AndCondition<PbandiRDocSpesaProgettoVO>(
                            Condition.filterBy(filterDocPrj),
                            Condition.not(new FilterCondition<PbandiRDocSpesaProgettoVO>(filterPrj)));

                    List<PbandiRDocSpesaProgettoVO> listDocPrj = genericDAO.findListWhere(andCondition);

                    if (ObjectUtil.isEmpty(listDocPrj)) {
                        /*
                         * Verifico che non devono esistere altre validazione per il documento-progetto
                         */
                        PagamentoDocumentoDichiarazioneVO filterDocumento = new PagamentoDocumentoDichiarazioneVO();
                        filterDocumento.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                        filterDocumento.setIdProgetto(documentoDiSpesa.getIdProgetto());

                        PagamentoDocumentoDichiarazioneVO filterDichiarazione = new PagamentoDocumentoDichiarazioneVO();
                        filterDichiarazione.setIdDichiarazioneSpesa(documentoDiSpesa.getIdDichiarazioneSpesa());

                        AndCondition<PagamentoDocumentoDichiarazioneVO> andConditionDocPrj = new AndCondition<PagamentoDocumentoDichiarazioneVO>(
                                Condition.filterBy(filterDocumento), Condition.not(
                                        new FilterCondition<PagamentoDocumentoDichiarazioneVO>(filterDichiarazione)));

                        List<PagamentoDocumentoDichiarazioneVO> listDichiarazioni = genericDAO
                                .findListWhere(andConditionDocPrj);

                        if (ObjectUtil.isEmpty(listDichiarazioni))

                            modificaInDaCompletare = false;
                    }

                    if (modificaInDaCompletare) {
                        PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                        filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                        updateDocInDaCompletare.add(filtroVO);
                    }
                }

            }

            Long pagamenti = null;
            /*
             * FIX PBANDI-2314. Non esiste piu' lo stato dei pagamenti
             * Long pagamenti = beanUtil.transform(
             * documentoDiSpesa.getNumeroPagamenti(), Long.class);
             * 
             * Long pagamentiAperti =
             * beanUtil.transform(documentoDiSpesa.getNumeroPagamentiAperti(), Long.class);
             */

            if (isNull(pagamenti))
                pagamenti = 0L;
            /*
             * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
             * if (isNull(pagamentiAperti))
             * pagamentiAperti = 0L;
             * 
             * if (NumberUtil.compare(pagamenti, pagamentiAperti) == 0) {
             * modificaStatoDocInDichiarabile = true;
             * }
             */

            if (modificaStatoDocInDichiarabile) {
                PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                updateDocInDichiarabile.add(filtroVO);
								//controllo se BR62, se c'è un pagamento con flag_pagamento lo rimuovo
							if(isBR62Attiva) {
								PbandiRPagamentoDocSpesaVO pagamentiFilterVO = new PbandiRPagamentoDocSpesaVO();
								pagamentiFilterVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
								List<PbandiRPagamentoDocSpesaVO> pagamentiDocumento = genericDAO.findListWhere(pagamentiFilterVO);
								if(pagamentiDocumento.size() == 1) {
									PbandiTPagamentoVO vo = new PbandiTPagamentoVO();
									vo.setIdPagamento(pagamentiDocumento.get(0).getIdPagamento());
									List <PbandiTPagamentoVO> pagamentiAssociati = genericDAO.findListWhere(vo);
									for (PbandiTPagamentoVO pagamento : pagamentiAssociati) {
										if(pagamento.getFlagPagamento() != null && pagamento.getFlagPagamento().equals("S")) {
											it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO dto = new it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO();
											dto.setIdPagamento(pagamento.getIdPagamento().longValue());
											PbandiRPagQuotParteDocSpVO pagQuotParteDocSp = new PbandiRPagQuotParteDocSpVO();
											pagQuotParteDocSp.setIdPagamento(pagamento.getIdPagamento());
											pagQuotParteDocSp.setIdDichiarazioneSpesa(documentoDiSpesa.getIdDichiarazioneSpesa());
											try{
												genericDAO.deleteWhere(Condition.filterBy(pagQuotParteDocSp));
											}
											catch (Exception e) {
												logger.error("Errore durante l'eliminazione della pagQuotaParteDocSpesa con idPagamento " + pagQuotParteDocSp.getIdPagamento(), e);
											}
											try {
												gestionePagamentiBusinessImpl.eliminaPagamento(idUtente, "identita", dto, validazioneRendicontazioneDTO.getIdBandoLinea());
											}
											catch (Exception e) {
												logger.error("Errore durante l'eliminazione del pagamento con idPagamento " + pagamento.getIdPagamento(), e);
											}
										}
									}
								}
							}
            } else {

                if (// FIX PBANDI-2314: Non esiste piu' lo stato del pagamento. pagamentiAperti == 0
                    // &&
                NumberUtil.toRoundDouble(totalePagamenti
                        .doubleValue()) == NumberUtil
                                .toRoundDouble(importoTotaleDocumento
                                        .doubleValue())
                        && isAllDichiarazioniChiuse) {

                    BigDecimal totaleValidatoProgetto = documentoDiSpesa.getTotaleValidatoPrj() == null
                            ? new BigDecimal(0.0)
                            : documentoDiSpesa.getTotaleValidatoPrj();
                    BigDecimal importoRendicontazione = documentoDiSpesa.getImportoRendicontazione() == null
                            ? new BigDecimal(0.0)
                            : documentoDiSpesa.getImportoRendicontazione();

                    /*
                     * FIX PBANDI-2314
                     * Al totale rendicontabili non deve essere sottratto il totale rendicontabile
                     * delle note di credito poichè la somma delle note di credito
                     * viene gia' considerate in fase di dichiarazione.
                     */
                    // rendicontabileTotale = rendicontabileTotale -
                    // totaleRendicontabiliNoteDiCredito;

                    if (NumberUtil.compare(totaleValidatoProgetto, importoRendicontazione) == 0) {
                        /*
                         * STATO VALIDATO: totale validato = totale rendicontabile, al netto delle
                         * somma tra imponibile e iva a costo delle note di credito.
                         */

                        PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                        filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                        updateDocInValidato.add(filtroVO);

                    } else if (NumberUtil.compare(totaleValidatoProgetto, importoRendicontazione) > 0) {
                        /*
                         * STATO VALIDATO: totale validato > totale rendicontabile, al netto delle
                         * somma tra imponibile e iva a costo delle note di credito.
                         */
                        PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                        filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                        updateDocInValidato.add(filtroVO);

                    } else if (NumberUtil.compare(totaleValidatoProgetto, new BigDecimal(0.0)) == 0) {
                        /*
                         * STATO NON VALIDATO: totale validato = 0
                         */

                        PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                        filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa
                                .getIdDocumentoDiSpesa());
                        updateDocInNonValidato.add(filtroVO);

                    } else {

                        PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                        filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                        updateDocInParzialmenteValidato.add(filtroVO);

                    }

                } else {
                    /*
                     * Il documento va in stato DA COMPLETARE
                     * poiche' devono essere aggiunti altri pagamenti
                     */
                    PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
                    filtroVO.setIdDocumentoDiSpesa(documentoDiSpesa.getIdDocumentoDiSpesa());
                    updateDocInDaCompletare.add(filtroVO);
                }

            }
            watcher.dumpElapsed("ValidazioneRendicontanzioneImpl",
                    "chiudiValidazione", "durata ciclo for ", ""
                            + documentoDiSpesa.getIdDocumentoDiSpesa());

        }

        if (!ObjectUtil.isEmpty(updateDocInDichiarabile)) {
            updateStatoDocumenti(decodificaStatoDichiarabile,
                    updateDocInDichiarabile, idUtente, validazioneRendicontazioneDTO.getIdProgetto());
        }
        if (!ObjectUtil.isEmpty(updateDocInNonValidato)) {
            updateStatoDocumenti(decodificaStatoNonValidato,
                    updateDocInNonValidato, idUtente, validazioneRendicontazioneDTO.getIdProgetto());
        }
        if (!ObjectUtil.isEmpty(updateDocInParzialmenteValidato)) {
            updateStatoDocumenti(decodificaStatoParzialmenteValidato,
                    updateDocInParzialmenteValidato, idUtente, validazioneRendicontazioneDTO.getIdProgetto());
        }
        if (!ObjectUtil.isEmpty(updateDocInValidato)) {
            updateStatoDocumenti(decodificaStatoValidato, updateDocInValidato, idUtente,
                    validazioneRendicontazioneDTO.getIdProgetto());
        }
        if (!ObjectUtil.isEmpty(updateDocInDaCompletare)) {
            updateStatoDocumenti(decodificaStatoDaCompletare, updateDocInDaCompletare, idUtente,
                    validazioneRendicontazioneDTO.getIdProgetto());
        }

        pbandiDocumentiDiSpesaDAO.updateStatoNoteDiCredito(idUtente,
                validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa(),
                validazioneRendicontazioneDTO.getIdProgetto());

        /*
         * La cancellazione della ripartizione per i documenti che devono essere
         * portati nella stato DICHIARABILE deve essere eseguita dopo aver
         * aggiornato lo stato delle note di credito, altrimenti non si
         * aggiorna lo stato di queste ultime
         */
        if (!ObjectUtil.isEmpty(updateDocInDichiarabile)) {
            cancellaRipartizioneAutomantica(validazioneRendicontazioneDTO.getIdProgetto(),
                    validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa(), idUtente, updateDocInDichiarabile);
        }

    }

    private void cancellaRipartizioneAutomantica(Long idProgetto, Long idDichiarazioneDiSpesa, Long idUtente,
            List<PbandiTDocumentoDiSpesaVO> documenti) throws Exception {
        /*
         * Richiamo il plsql che si occupa di cancellare la ripartizione
         * automatica per i documenti delle dichiarazione
         */
        if (!ObjectUtil.isEmpty(documenti)) {
            List<Long> idDocumenti = new ArrayList<Long>();
            for (PbandiTDocumentoDiSpesaVO doc : documenti) {
                idDocumenti.add(NumberUtil.toLong(doc.getIdDocumentoDiSpesa()));
            }
            genericDAO.callProcedure().revertRipartizionePagamenti(NumberUtil.toBigDecimal(idProgetto),
                    idDocumenti, NumberUtil.toBigDecimal(idDichiarazioneDiSpesa), NumberUtil.toBigDecimal(idUtente));
        }

    }

    private void updateStatoDocumenti(DecodificaDTO decodificaStato,
            List<PbandiTDocumentoDiSpesaVO> updateDocumenti, Long idUtente, Long idProgetto) throws Exception {

        PbandiRDocSpesaProgettoVO filterPrj = new PbandiRDocSpesaProgettoVO();
        filterPrj.setIdProgetto(new BigDecimal(idProgetto));
        List<PbandiRDocSpesaProgettoVO> filterDocumento = new ArrayList<PbandiRDocSpesaProgettoVO>();
        for (PbandiTDocumentoDiSpesaVO doc : updateDocumenti) {
            PbandiRDocSpesaProgettoVO docProgetto = new PbandiRDocSpesaProgettoVO();
            docProgetto.setIdDocumentoDiSpesa(doc.getIdDocumentoDiSpesa());
            filterDocumento.add(docProgetto);
        }

        AndCondition<PbandiRDocSpesaProgettoVO> andCondition = new AndCondition<PbandiRDocSpesaProgettoVO>(
                Condition.filterBy(filterPrj), Condition.filterBy(filterDocumento));

        PbandiRDocSpesaProgettoVO newValueVO = new PbandiRDocSpesaProgettoVO();
        newValueVO.setIdStatoDocumentoSpesa(NumberUtil
                .toBigDecimal(decodificaStato.getId()));
        newValueVO.setIdUtenteAgg(new BigDecimal(idUtente));
        genericDAO.update(andCondition, newValueVO);
    }

    private void updateStatoDocumentiDaCompletare(DecodificaDTO decodificaStato,
            List<PbandiTDocumentoDiSpesaVO> updateDocumenti, Long idUtente) throws Exception {

        if (!ObjectUtil.isEmpty(updateDocumenti)) {
            List<PbandiRDocSpesaProgettoVO> filterDocumento = new ArrayList<PbandiRDocSpesaProgettoVO>();
            for (PbandiTDocumentoDiSpesaVO doc : updateDocumenti) {
                PbandiRDocSpesaProgettoVO docProgetto = new PbandiRDocSpesaProgettoVO();
                docProgetto.setIdDocumentoDiSpesa(doc.getIdDocumentoDiSpesa());
                filterDocumento.add(docProgetto);
            }

            PbandiRDocSpesaProgettoVO newValueVO = new PbandiRDocSpesaProgettoVO();
            newValueVO.setIdStatoDocumentoSpesa(NumberUtil
                    .toBigDecimal(decodificaStato.getId()));
            newValueVO.setIdUtenteAgg(new BigDecimal(idUtente));
            genericDAO.update(Condition.filterBy(filterDocumento), newValueVO);
        }
    }

    private void updateStatoValidazioneDocumenti(String descBreveStatoValidazioneDocumento,
            List<Long> documenti, Long idUtente, Long idProgetto) throws Exception {

        if (descBreveStatoValidazioneDocumento == null) {
            throw new Exception("Descrizione breve stato documento non valorizzata.");
        }

        DecodificaDTO statoValidazioneDocumento = decodificheManager
                .findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, descBreveStatoValidazioneDocumento);

        List<PbandiRDocSpesaProgettoVO> filterDocumentoProgetto = new ArrayList<PbandiRDocSpesaProgettoVO>();
        for (Long idDoc : documenti) {
            PbandiRDocSpesaProgettoVO docProgetto = new PbandiRDocSpesaProgettoVO();
            docProgetto.setIdProgetto(new BigDecimal(idProgetto));
            docProgetto.setIdDocumentoDiSpesa(new BigDecimal(idDoc));
            filterDocumentoProgetto.add(docProgetto);
        }
        FilterCondition<PbandiRDocSpesaProgettoVO> filter = new FilterCondition<PbandiRDocSpesaProgettoVO>(
                filterDocumentoProgetto);

        PbandiRDocSpesaProgettoVO newValueVO = new PbandiRDocSpesaProgettoVO();
        newValueVO.setIdStatoDocumentoSpesaValid(NumberUtil
                .toBigDecimal(statoValidazioneDocumento.getId()));
        newValueVO.setIdUtenteAgg(new BigDecimal(idUtente));
        genericDAO.update(filter, newValueVO);
    }

    private void updateStatoValidazioneDocumento(String descBreveStatoValidazioneDocumento,
            Long idDocumento, Long idUtente, Long idProgetto) throws Exception {
        List<Long> documenti = new ArrayList<Long>();
        documenti.add(idDocumento);
        updateStatoValidazioneDocumenti(descBreveStatoValidazioneDocumento, documenti, idUtente, idProgetto);

    }

    public VoceDiSpesaDTO[] findVociDiSpesaConImportiValidati(Long idUtente,
            String identitaDigitale,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        VoceDiSpesaDTO voceDiSpesaDTO[] = null;

        String[] nameParameter = { "idUtente", "identitaDigitale",
                "validazioneRendicontazioneDTO" };

        ValidatorInput.verifyNullValue(nameParameter,
                validazioneRendicontazioneDTO);

        java.util.List<VoceDiSpesaVO> listaVO = new ArrayList<VoceDiSpesaVO>();

        listaVO = pbandiContoEconomicoDAO.findVociImporti(
                validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa(),
                validazioneRendicontazioneDTO.getIdDocumentoDiSpesa(),
                validazioneRendicontazioneDTO.getIdProgetto());

        voceDiSpesaDTO = new VoceDiSpesaDTO[listaVO.size()];
        beanUtil.valueCopy(listaVO.toArray(), voceDiSpesaDTO);

        return voceDiSpesaDTO;

    }

    public EsitoValidazioneRendicontazione checkUpdateDocumentoEVociDiSpesa(
            Long idUtente, String identitaDigitale,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        logger.info("\n\n\n\n\n\n\n\n\ncheckUpdateDocumentoEVociDiSpesa ############");
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "validazioneRendicontazioneDTO" };

        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, validazioneRendicontazioneDTO);

        PagamentoDTO[] pagamenti = validazioneRendicontazioneDTO
                .getPagamenti();
        ValidatorInput.verifyArrayNotEmpty("pagamenti", pagamenti);

        EsitoValidazioneRendicontazione esito = new EsitoValidazioneRendicontazione();

        for (PagamentoDTO pagamentoDTO : pagamenti) {

            if (validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa()
                    .equals(pagamentoDTO.getIdDichiarazioneDiSpesa())) {

                logger.info("\n\n\n\n\n\n\n\n\nidPagamento: " + pagamentoDTO.getIdPagamento());

                VoceDiSpesaDTO[] vociDiSpesa = pagamentoDTO.getVociDiSpesa();
                logger.info("vociDiSpesa : " + (vociDiSpesa != null ? vociDiSpesa.length : 0));
                VoceDiSpesaPagamentoVO[] vociDiSpesaPagamentoVO = new VoceDiSpesaPagamentoVO[vociDiSpesa.length];
                getBeanUtil().valueCopy(vociDiSpesa, vociDiSpesaPagamentoVO);

                for (VoceDiSpesaPagamentoVO voceDiSpesaPagamentoVO : vociDiSpesaPagamentoVO) {
                    logger.info("voceDiSpesaPagamentoVO: " + voceDiSpesaPagamentoVO.getDescVoceSpesa() +
                            " , importo validato: " + voceDiSpesaPagamentoVO.getImportoValidato() +
                            " , importo associato: " + voceDiSpesaPagamentoVO.getImportoAssociato());
                    double importoAss = NumberUtil
                            .toRoundDouble(voceDiSpesaPagamentoVO
                                    .getImportoAssociato());
                    double importoVal = NumberUtil
                            .toRoundDouble(voceDiSpesaPagamentoVO
                                    .getImportoValidato());

                    if (importoVal < 0) {
                        logger.error("importoVal < 0 : " + importoVal);
                        throw new ValidazioneRendicontazioneException(
                                ERRORE_PAGAMENTO_VALORE_ERRATO);
                    }
                    if (importoVal > importoAss) {
                        logger.error("importoVal(" + importoVal + ") > importoAss (" + importoAss + ")  ");
                        // @fixme modificare il messaggio di errore
                        throw new ValidazioneRendicontazioneException(
                                ERRORE_PAGAMENTO_VALORE_ERRATO);
                    }

                }
            }
        }

        esito.setEsito(new Boolean(true));
        return esito;

    }

    public void salvaNoteChiusuraValidazione(Long idUtente,
            String identitaDigitale, Long idDichiarazione,
            String noteChiusuraValidazione) throws CSIException,
            SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        try {

            String[] nameParameter = { "idUtente", "identitaDigitale",
                    "idDichiarazione", "noteChiusuraValidazione" };

            ValidatorInput.verifyNullValue(nameParameter, idUtente,
                    idDichiarazione, noteChiusuraValidazione);
            PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
            pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(
                    idDichiarazione));
            pbandiTDichiarazioneSpesaVO
                    .setNoteChiusuraValidazione(noteChiusuraValidazione);

            pbandiTDichiarazioneSpesaVO.setIdUtenteAgg(new BigDecimal(idUtente));

            genericDAO.update(pbandiTDichiarazioneSpesaVO);
        } catch (Exception e) {
            logger.error(
                    "Errore nell'aggiornare le note chiusura validazione per id dichiarazione "
                            + idDichiarazione,
                    e);
            throw new ValidazioneRendicontazioneException(
                    "Errore nell'aggiornare le note chiusura validazione per id dichiarazione "
                            + idDichiarazione);
        }
    }

    public EsitoReportDettaglioDocumentiDiSpesaDTO generaReportDettaglioDocumentoDiSpesa(
            Long idUtente, String identitaDigitale, Long idDichiarazioneSpesa)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        logger.info("generaReportDettaglioDocumentoDiSpesa");

        try {

            String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazioneSpesa" };
            ValidatorInput.verifyNullValue(nameParameter, idUtente,
                    identitaDigitale, idDichiarazioneSpesa);

            ReportDettaglioDocumentiSpesaVO reportVO = new ReportDettaglioDocumentiSpesaVO();
            reportVO.setIdDichiarazioneSpesa(idDichiarazioneSpesa);
            logger.info("\n\nseek ReportDettaglioDocumentiSpesaVO per idDichiarazioneSpesa : " + idDichiarazioneSpesa);
            List<ReportDettaglioDocumentiSpesaVO> elementiReport = genericDAO.findListWhere(reportVO);
            logger.info("\nfound: " + elementiReport.size() + "\n\n");
            EsitoReportDettaglioDocumentiDiSpesaDTO esito = null;
            if (!isEmpty(elementiReport)) {
                byte[] reportDettaglioDocumentiSpesaFileData = TableWriter
                        .writeTableToByteArray(
                                "reportDettaglioDocumentiSpesa",
                                new ExcelDataWriter(idDichiarazioneSpesa
                                        .toString()),
                                elementiReport);

                // String nomeFile = "reportValidazione"
                String nomeFile = "reportValidazione"
                        + idDichiarazioneSpesa + ".xls";

                esito = new EsitoReportDettaglioDocumentiDiSpesaDTO();
                esito.setExcelBytes(reportDettaglioDocumentiSpesaFileData);
                esito.setNomeFile(nomeFile);

            }

            return esito;

        } catch (Exception e) {
            logger.error(
                    "Errore nella creazione del report dettaglio documenti di spesa per id dichiarazione "
                            + idDichiarazioneSpesa,
                    e);
            throw new ValidazioneRendicontazioneException(
                    "Errore nella creazione del report dettaglio documenti di spesa per id dichiarazione "
                            + idDichiarazioneSpesa);
        }
    }

    public void setDichiarazioneDiSpesaManager(
            DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
        this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
    }

    public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
        return dichiarazioneDiSpesaManager;
    }

    public EsitoCheckOperazioneAutomatica checkPagamentiPerOperazioneAutomaticaSelettiva(
            Long idUtente, String identitaDigitale, Long idBandoLinea,
            Long idDichiarazioneDiSpesa, Long[] idDocumenti)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {
        EsitoCheckOperazioneAutomatica esito;
        try {
            String[] nameParameter = { "idUtente", "identitaDigitale",
                    "idDocumenti" };
            ValidatorInput.verifyNullValue(nameParameter, idUtente,
                    identitaDigitale, idDocumenti);

            DecodificaDTO decodificaStatoInValidazione = decodificheManager
                    .findDecodifica(
                            GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                            GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE);

            // Dei documenti passati, estraggo quelli in stato IN VALIDAZIONE
            DocumentiDiSpesaPerValidazioneVO queryStato = new DocumentiDiSpesaPerValidazioneVO();
            queryStato.setIdDichiarazioneSpesa(beanUtil.transform(idDichiarazioneDiSpesa, BigDecimal.class));
            /*
             * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
             * QUindi ricerco i documenti in stato IN VALIDAZIONE
             * queryStato
             * .setDescBreveStatoValidazSpesa(it.csi.pbandi.pbandiutil.common.Constants.
             * StatiValidazionePagamenti.DICHIARATO
             * .getDescBreve());
             */
            /*
             * FIX PBANDI-2314.
             * Ricerco per lo stato di validazione del documento di spesa
             * (idStatoDocumentoValid)
             */
            queryStato.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(decodificaStatoInValidazione.getId()));

            List<DocumentiDiSpesaPerValidazioneVO> queryIds = beanUtil.beanify(
                    Arrays.asList(idDocumenti), "idDocumentoDiSpesa",
                    DocumentiDiSpesaPerValidazioneVO.class);
            List<DocumentiDiSpesaPerValidazioneVO> documentiInValidazione = genericDAO
                    .findListWhere(Condition.filterBy(queryStato).and(
                            Condition.filterBy(queryIds)));
            if (isEmpty(documentiInValidazione)) {
                esito = new EsitoCheckOperazioneAutomatica();
                esito.setEsito(Boolean.FALSE);
                esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE);
                esito.setParams(new String[] { "0" });
            } else {
                List<DocumentiDiSpesaPerValidazioneVO> documentoDaControllare = new ArrayList<DocumentiDiSpesaPerValidazioneVO>();

                /*
                 * FIX PBandi-966.
                 */
                for (DocumentiDiSpesaPerValidazioneVO documento : documentiInValidazione) {
                    if (doCheckVociSpesaNotaCreditoDocumento(documento))
                        documentoDaControllare.add(documento);
                }
                /*
                 * FIX PBANDI-2314.
                 * Lo stato dei pagamenti non esiste piu'
                 * if (isEmpty(idDocumentiList)) {
                 * List<Long> idDocumentiValidazione = beanUtil.extractValues(
                 * documentiInValidazione, "idDocumentoDiSpesa",
                 * Long.class);
                 * List<Long> pagamentiTotaliInStatoDichiarato =
                 * findPagamentiTotaliInStatoDichiarato(
                 * idDichiarazioneDiSpesa, idDocumentiValidazione);
                 * esito = new EsitoCheckOperazioneAutomatica();
                 * esito.setEsito(Boolean.FALSE);
                 * esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE)
                 * ;
                 * List<String> returnParams = new ArrayList<String>();
                 * esito.setParams(new String[] { ""
                 * + pagamentiTotaliInStatoDichiarato.size() });
                 * return esito;
                 * }
                 */

                esito = doCheckPagamentiPerOperazioneAutomaticaSelettiva(
                        idBandoLinea,
                        idDichiarazioneDiSpesa,
                        documentoDaControllare);
            }
        } finally {
        }
        return esito;
    }

    private EsitoCheckOperazioneAutomatica doCheckPagamentiPerOperazioneAutomaticaSelettiva(
            Long idBandoLinea, Long idDichiarazioneDiSpesa, List<DocumentiDiSpesaPerValidazioneVO> documenti)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        EsitoCheckOperazioneAutomatica esito = new EsitoCheckOperazioneAutomatica();
        List<DocumentiDiSpesaPerValidazioneVO> documentiValidi = ottieniDocumentiValidi(documenti, idBandoLinea,
                idDichiarazioneDiSpesa);
        /*
         * FIX PBANDI-2314.
         * Lo stato dei pagamenti non esiste piu'
         * List<String> statiAmmessiPagamento = getStatiValidiPagamento();
         */
        if (isEmpty(documentiValidi)) {
            esito.setEsito(Boolean.FALSE);
            esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE);
            esito.setParams(new String[] { "" + documenti.size() });
            return esito;
        }
        esito.setEsito(Boolean.TRUE);
        List<Long> idDocumentiValidi = beanUtil.extractValues(documentiValidi,
                "idDocumentoDiSpesa", Long.class);
        /*
         * Ricerco i pagamenti associati alla dichiarazione per i documenti
         * validi
         */
        List<PagamentoDichiarazioneVO> pagamentiDichiarazione = findPagamentiDocumentiDichiarazione(
                idDichiarazioneDiSpesa, idDocumentiValidi);

        List<Long> idPagamenti = beanUtil.extractValues(pagamentiDichiarazione,
                "idPagamento", Long.class);
        /*
         * FIX PBANDI-2314.
         * Lo stato del pagamento non esiste piu'
         * List<Long> idPagamenti = pbandipagamentiDAO.findIdPagamentiByStato(
         * idDichiarazioneDiSpesa, statiAmmessiPagamento,
         * idDocumentiValidi);
         */
        esito.setIdDocumenti(idDocumentiValidi.toArray(new Long[idDocumentiValidi.size()]));
        boolean isImportoMaxSuperato = pbandipagamentiDAO.controllaTotaliImportiAmmessiPerVociDiSpesa(idPagamenti);
        if (isImportoMaxSuperato) {
            esito.setMessage(ErrorConstants.ERRORE_IMPORTO_AMMISSIBILE_SUPERATO);
        } else {
            // non usato, ma sempre meglio che tenere un message null
            esito.setMessage(MessaggiConstants.STATO_DOCUMENTO_DICHIARAZIONE_AGGIORNATO);
        }

        return esito;
    }

    private List<DocumentiDiSpesaPerValidazioneVO> ottieniDocumentiValidi(
            List<DocumentiDiSpesaPerValidazioneVO> documenti, Long idBandoLinea, Long idDichiarazioneDiSpesa)
            throws FormalParameterException, ManagerException,
            ValidazioneRendicontazioneException {
        List<DocumentiDiSpesaPerValidazioneVO> documentiValidi = new ArrayList<DocumentiDiSpesaPerValidazioneVO>();
        boolean isBr01 = regolaManager
                .isRegolaApplicabileForBandoLinea(idBandoLinea,
                        RegoleConstants.BR01_GESTIONE_ATTRIBUTI_QUALIFICA);

        // non usato
        // boolean isBr02 =
        // regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea,
        // RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);

        boolean isBr03 = regolaManager.isRegolaApplicabileForBandoLinea(
                idBandoLinea, RegoleConstants.BR03_RILASCIO_SPESE_QUIETANZATE);

        boolean isBr07 = regolaManager.isRegolaApplicabileForBandoLinea(
                idBandoLinea,
                RegoleConstants.BR07_CONTROLLO_SPESE_TOTALMENTE_QUIETANZATE);

        /*
         * FIX PBandi-2314.
         * Controllo per le dichiarazioni finali o integrative
         */
        boolean isFinaleOIntegrativa = isDichiarazioneFinaleOIntegrativa(idDichiarazioneDiSpesa);

        /*
         * Verifico se ogni singolo documento e'valido
         */
        for (DocumentiDiSpesaPerValidazioneVO documento : documenti) {
            boolean isImportoQuietanzatoSuperioreImportoDocumento = isImportoQuietanzatoSuperioreImportoDocumento(
                    documento);
            boolean isCedolino = documentoDiSpesaManager
                    .isCedolinoOAutodichiarazioneSoci(beanUtil.transform(
                            documento.getIdTipoDocumentoDiSpesa(), Long.class));
            boolean isCompletamenteQuietanziato = isCompletamenteQuietanziato(documento);

            // if (!isCedolino) FIX PBANDI-1042 : ora vanno controllati tutti
            if (!isPagamentiCompletamenteAssociati(documento)) {
                continue;
            }

            /*
             * if (isBr01) {
             * if (isCedolino) {
             * if (isMonteOreSuperato(beanUtil.transform(
             * documento.getIdFornitore(), Long.class),
             * beanUtil.transform(
             * documento.getIdDocumentoDiSpesa(),
             * Long.class))) {
             * continue;
             * }
             * }
             * }
             */
            if (isBr03) {
                /*
                 * FIX: PBandi-387 e PBandi-400 Applico la regola BR03 anche per
                 * i documenti di tipo SPESA FORFETTARIA, mentre non la applico
                 * per i documenti di tipo NOTA DI CREDITO
                 */
                // if (!notaDiCredito) ?
                if (!isCompletamenteQuietanziato) {
                    continue;
                }
            }

            if (isBr07 && isFinaleOIntegrativa) {
                /*
                 * FIX: PBandi-387 PBandi-400. Applico la regola BR07 anche per
                 * i documenti di tipo SPESA FORFETTARIA, mentre non la applico
                 * per i documenti di tipo NOTA DI CREDITO
                 */
                // if (!notaDiCredito) ?
                if (!isCompletamenteQuietanziato) {
                    // FIXME Quando verranno gestite le DICHIARAZIONI FINALI,
                    // aggiungere && isDichiarazioneFinale
                    continue;
                }
            }

            if (!isRendicontabileCompletamenteAssociato(documento)) {
                continue;
            }

            if (isFattura(beanUtil.transform(
                    documento.getIdTipoDocumentoDiSpesa(), Long.class))) {
                if (isImportoQuietanzatoSuperioreImportoDocumento) {
                    continue;
                }
            }
            // I controlli sono stati superati
            documentiValidi.add(documento);
        }
        return documentiValidi;
    }

    private boolean isPagamentiCompletamenteAssociati(
            DocumentiDiSpesaPerValidazioneVO documento) {
        Long idDocumentoDiSpesa = beanUtil.transform(
                documento.getIdDocumentoDiSpesa(), Long.class);
        Long idProgetto = beanUtil.transform(documento.getIdProgetto(),
                Long.class);

        boolean result = true;

        PagamentoQuotePartiVO sommaImportiQuietanzatiVO = pbandiDichiarazioneDiSpesaDAO
                .getTotaleQuietanzati(idDocumentoDiSpesa, idProgetto);
        Double sommaImportiQuietanzati = 0D;

        if (!isNull(sommaImportiQuietanzatiVO)) {
            sommaImportiQuietanzati = NumberUtil
                    .getDoubleValue(sommaImportiQuietanzatiVO
                            .getTotaleImportiQuietanzati());
        }
        List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> noteCredito = pbandiDocumentiDiSpesaDAO
                .findNoteDiCreditoAssociateFattura(idDocumentoDiSpesa);
        double totaleNoteCreditoRendicontabile = 0.0;
        for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO notaCredito : noteCredito) {
            double totaleRendicontabile = notaCredito
                    .getImportoRendicontabile() == null ? 0
                            : notaCredito
                                    .getImportoRendicontabile();
            totaleNoteCreditoRendicontabile += totaleRendicontabile;
        }

        List<PagamentoQuotePartiVO> pagamentiAssociatiAlProgettoVO = pbandiDichiarazioneDiSpesaDAO
                .getPagamentiAssociatiAVociDiSpesa(idDocumentoDiSpesa,
                        idProgetto);
        Double sommaPagamenti = new Double(0);
        if (pagamentiAssociatiAlProgettoVO != null) {
            for (PagamentoQuotePartiVO pagamentoQuotePartiVO : pagamentiAssociatiAlProgettoVO) {
                sommaPagamenti += NumberUtil
                        .getDoubleValue(pagamentoQuotePartiVO
                                .getImportoPagamento());
            }
        }
        /*
         * Se la differenza tra il totale dei pagamenti ed il totale delle voci
         * di spesa quietanzate e' maggiore di 0 allora confronto il totale
         * delle voci di spesa quietanzate con l' importo rendicontabile. Se l'
         * importo rendicontabile e' maggiore del totale delle voci di spesa
         * quietanzate allora restituisco false
         */
        /*
         * FIX: PBandi-550 Correzione arrotondamento
         */

        Double importoRendicontabile = beanUtil.transform(NumberUtil.subtract(
                documento.getImportoRendicontabile(), beanUtil.transform(
                        totaleNoteCreditoRendicontabile, BigDecimal.class)),
                Double.class);
        if ((NumberUtil.compare(sommaPagamenti, sommaImportiQuietanzati) > 0)
                && (NumberUtil.compare(importoRendicontabile,
                        sommaImportiQuietanzati) > 0)) {
            result = false;
        }
        return result;
    }

    public EsitoCheckOperazioneAutomatica checkPagamentiPerOperazioneAutomaticaTutti(
            Long idUtente, String identitaDigitale, Long idBandoLinea,
            Long idDichiarazioneDiSpesa) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {
        EsitoCheckOperazioneAutomatica esito;
        try {
            String[] nameParameter = { "idUtente", "identitaDigitale",
                    "idBandoLinea", "idDichiarazioneDiSpesa" };
            ValidatorInput.verifyNullValue(nameParameter, idUtente,
                    identitaDigitale, idBandoLinea, idDichiarazioneDiSpesa);

            DecodificaDTO decodificaStatoInValidazione = decodificheManager
                    .findDecodifica(
                            GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
                            GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE);

            DocumentiDiSpesaPerValidazioneVO query = new DocumentiDiSpesaPerValidazioneVO();
            query.setIdDichiarazioneSpesa(beanUtil.transform(
                    idDichiarazioneDiSpesa, BigDecimal.class));
            /*
             * FIX PBANDI-2314. Non esiste piu' lo stato dal pagamento.
             * Quindi considero i documenti in stato IN VALIDAZIONE
             * query.setDescBreveStatoValidazSpesa(it.csi.pbandi.pbandiutil.common.Constants
             * .StatiValidazionePagamenti.DICHIARATO
             * .getDescBreve());
             */

            /*
             * FIX PBANDI-2314.
             * Ricerco i documenti con stato di validazione
             * del documento IN VALIDAZIONE
             */
            query.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(decodificaStatoInValidazione.getId()));

            List<DocumentiDiSpesaPerValidazioneVO> documenti = genericDAO.findListWhere(query);

            if (isEmpty(documenti)) {
                esito = new EsitoCheckOperazioneAutomatica();
                esito.setEsito(Boolean.FALSE);
                esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE);
                List<String> returnParams = new ArrayList<String>();
                returnParams.add("0");
                esito.setParams(returnParams.toArray(new String[0]));
                return esito;
            } else {
                List<DocumentiDiSpesaPerValidazioneVO> documentiDaControllare = new ArrayList<DocumentiDiSpesaPerValidazioneVO>();
                /*
                 * FIX PBandi-966.
                 */
                for (DocumentiDiSpesaPerValidazioneVO documento : documenti) {
                    if (doCheckVociSpesaNotaCreditoDocumento(documento))
                        documentiDaControllare.add(documento);
                }
                /*
                 * FIX PBANDI-2314.
                 * Lo stato dei pagamenti non esiste piu'
                 * if (isEmpty(idDocumenti)) {
                 * List<Long> idDocumentiValidazione = beanUtil.extractValues(
                 * documenti, "idDocumentoDiSpesa", Long.class);
                 * List<Long> pagamentiTotaliInStatoDichiarato =
                 * findPagamentiTotaliInStatoDichiarato(idDichiarazioneDiSpesa,
                 * idDocumentiValidazione);
                 * esito = new EsitoCheckOperazioneAutomatica();
                 * esito.setEsito(Boolean.FALSE);
                 * esito.setMessage(ErrorConstants.ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE)
                 * ;
                 * List<String> returnParams = new ArrayList<String>();
                 * esito.setParams(new String[] { ""
                 * + pagamentiTotaliInStatoDichiarato.size() });
                 * return esito;
                 * }
                 */

                esito = doCheckPagamentiPerOperazioneAutomaticaSelettiva(
                        idBandoLinea, idDichiarazioneDiSpesa,
                        documentiDaControllare);
            }
        } finally {
        }

        return esito;
    }

    public EsitoOperazioneAutomatica invalidaMultiplo(Long idUtente,
            String identitaDigitale, Long[] idDocumenti,
            Long idDichiarazioneDiSpesa) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {
        EsitoOperazioneAutomatica esito = new EsitoOperazioneAutomatica();
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDocumenti" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumenti);

        List<Long> documentiDiSpesa = new ArrayList<Long>();
        for (Long idDoc : idDocumenti) {
            documentiDiSpesa.add(idDoc);
        }
        Long documentiInvalidatiOra = null;
        try {
            documentiInvalidatiOra = invalidaDocumenti(idUtente, idDichiarazioneDiSpesa, documentiDiSpesa);
        } catch (Exception e) {
            throw new UnrecoverableException("Errore nel invalidaDocumenti",
                    e);
        }

        esito.setDettaglio(getDettaglioDocumenti(idDichiarazioneDiSpesa, documentiInvalidatiOra));
        esito.setEsito(Boolean.TRUE);
        esito.setMessage(MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_VALIDAZIONE);
        return esito;

    }

    public EsitoOperazioneAutomatica respingiMultiplo(Long idUtente,
            String identitaDigitale, Long[] idDocumentiDiSpesa,
            Long idDichiarazioneDiSpesa) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {
        EsitoOperazioneAutomatica esito = new EsitoOperazioneAutomatica();
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDocumentiDiSpesa" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumentiDiSpesa);

        List<Long> documentiDiSpesa = new ArrayList<Long>();
        for (Long idDoc : idDocumentiDiSpesa) {
            documentiDiSpesa.add(idDoc);
        }
        Long docucmentiRespinti = null;
        try {
            docucmentiRespinti = respingiDocumenti(idUtente, idDichiarazioneDiSpesa, documentiDiSpesa);
        } catch (Exception e) {
            throw new UnrecoverableException("Errore nel validaPagamenti",
                    e);
        }

        esito.setDettaglio(getDettaglioDocumenti(idDichiarazioneDiSpesa, docucmentiRespinti));
        esito.setEsito(Boolean.TRUE);
        esito.setMessage(MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_VALIDAZIONE);
        return esito;
    }

    public EsitoOperazioneAutomatica validaMultiplo(Long idUtente,
            String identitaDigitale, Long[] idDocumenti,
            Long idDichiarazioneDiSpesa) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {
        EsitoOperazioneAutomatica esito = new EsitoOperazioneAutomatica();
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDocumenti" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumenti);

        List<Long> documenti = new ArrayList<Long>();
        for (Long idDoc : idDocumenti) {
            documenti.add(idDoc);
        }

        try {
            Long documentiElaborati = validaDocumenti(idUtente, idDichiarazioneDiSpesa, documenti);
            esito.setDettaglio(getDettaglioDocumenti(idDichiarazioneDiSpesa, documentiElaborati));
            esito.setEsito(Boolean.TRUE);
            esito.setMessage(MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_VALIDAZIONE);
        } catch (Exception e) {
            throw new UnrecoverableException("Errore nel validaPagamenti",
                    e);
        }
        return esito;
    }
    
    public EsitoOperazioneAutomatica sospendiMultiplo(Long idUtente,
            String identitaDigitale, Long[] idDocumenti,
            Long idDichiarazioneDiSpesa, Boolean fromAttivitaValidazione) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {
        EsitoOperazioneAutomatica esito = new EsitoOperazioneAutomatica();
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDocumenti" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumenti);

        List<Long> documenti = new ArrayList<Long>();
        for (Long idDoc : idDocumenti) {
            documenti.add(idDoc);
        }

        try {
            Long documentiElaborati = sospendiDocumenti(idUtente, idDichiarazioneDiSpesa, documenti, fromAttivitaValidazione);
            esito.setDettaglio(getDettaglioDocumenti(idDichiarazioneDiSpesa, documentiElaborati));
            esito.setEsito(Boolean.TRUE);
            esito.setMessage(MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_VALIDAZIONE);
        } catch (Exception e) {
            throw new UnrecoverableException("Errore nel sospendiPagamenti",
                    e);
        }
        return esito;
    }
    

    // XXX WAS private
    public DettaglioOperazioneAutomatica getDettaglioDocumenti(
            Long idDichiarazioneDiSpesa, Long documentiElaboratiOra) {
        DettaglioOperazioneAutomatica dettaglio = new DettaglioOperazioneAutomatica();
        DettaglioOperazioneAutomaticaValidazioneVO dettaglioVO = new DettaglioOperazioneAutomaticaValidazioneVO();
        dettaglioVO.setIdDichiarazioneSpesa(beanUtil.transform(
                idDichiarazioneDiSpesa, BigDecimal.class));
        List<DettaglioOperazioneAutomaticaValidazioneVO> dettagliVO = genericDAO
                .where(dettaglioVO).select();

        dettaglio.setDocumentiElaborati(documentiElaboratiOra);
        Long initVal = 0L;
        dettaglio.setDocumentiDichiarati(initVal);
        dettaglio.setDocumentiRespinti(initVal);
        dettaglio.setDocumentiNonValidati(initVal);
        dettaglio.setDocumentiParzialmenteValidati(initVal);
        dettaglio.setDocumentiValidati(initVal);
        dettaglio.setDocumentiSospesi(initVal);
        dettaglio.setDocumentiInValidazione(initVal);
        dettaglio.setTotaleDocumenti(initVal);

        for (DettaglioOperazioneAutomaticaValidazioneVO dettaglioAggiornatoVO : dettagliVO) {
            String descStato = dettaglioAggiornatoVO.getDescStato();
            Long numDocumenti = beanUtil.transform(
                    dettaglioAggiornatoVO.getNumDocumenti(), Long.class);
            if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE)) {
                dettaglio.setDocumentiDichiarati(dettaglio.getDocumentiDichiarati() + numDocumenti);
            } else if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO)) {
                dettaglio.setDocumentiRespinti(dettaglio.getDocumentiRespinti() + numDocumenti);
            } else if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO)) {
                dettaglio.setDocumentiNonValidati(dettaglio.getDocumentiNonValidati() + numDocumenti);
            } else if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO)) {
                dettaglio.setDocumentiParzialmenteValidati(dettaglio.getDocumentiParzialmenteValidati() + numDocumenti);
            } else if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO)) {
                dettaglio.setDocumentiValidati(dettaglio.getDocumentiValidati()
                        + numDocumenti);
            } else if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_SOSPESO)) {
                dettaglio.setDocumentiSospesi(dettaglio.getDocumentiSospesi()
                        + numDocumenti);
            } else if (descStato
                    .equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE)) {
                dettaglio.setDocumentiInValidazione(dettaglio.getDocumentiInValidazione() + numDocumenti);
            } else if (descStato.equalsIgnoreCase("totale")) {
                dettaglio.setTotaleDocumenti(dettaglio.getTotaleDocumenti() + numDocumenti);
            }
        }

        return dettaglio;
    }

    private boolean doCheckVociSpesaNotaCreditoDocumento(
            DocumentiDiSpesaPerValidazioneVO documento)
            throws ValidazioneRendicontazioneException {
        boolean isNotaCredito = isNotaDiCredito(NumberUtil.toLong(documento
                .getIdTipoDocumentoDiSpesa()));
        String error = documentoDiSpesaManager
                .controlliVociSpesaNoteCreditoDocumento(
                        documento.getIdDocumentoDiSpesa(),
                        documento.getIdProgetto(), isNotaCredito);
        if (error == null) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * FIX PBANDI-2314.
     * Lo stato dei pagamenti non esiste piu
     * private List<Long> findPagamentiTotaliInStatoDichiarato(
     * Long idDichiarazioneDiSpesa, List<Long> idDocumentiValidazione) {
     * List<String> statiAmmessiPagamento = getStatiValidiPagamento();
     * List<Long> pagamentiTotaliInStatoDichiarato = ObjectUtil.nvl(
     * pbandipagamentiDAO.findIdPagamentiByStato(
     * idDichiarazioneDiSpesa, statiAmmessiPagamento,
     * idDocumentiValidazione), new ArrayList<Long>());
     * return pagamentiTotaliInStatoDichiarato == null ? new ArrayList<Long>()
     * : pagamentiTotaliInStatoDichiarato;
     * }
     */

    private boolean checkNotaCreditoTotaleRendicontabileFatturaSuperioreRendicontabileMassimo(
            DocumentoDiSpesaDTO notacredito) {
        /*
         * Recupero i dati della fattura di riferimento
         */
        PbandiTDocumentoDiSpesaVO filterDocumentoVO = new PbandiTDocumentoDiSpesaVO();
        filterDocumentoVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(notacredito.getIdDocRiferimento()));
        PbandiTDocumentoDiSpesaVO fattura = genericDAO.findSingleOrNoneWhere(filterDocumentoVO);

        BigDecimal imponibileFattura = fattura.getImponibile() == null ? new BigDecimal(0) : fattura.getImponibile();
        BigDecimal ivaACostoFattura = fattura.getImportoIvaCosto() == null ? new BigDecimal(0)
                : fattura.getImportoIvaCosto();

        BigDecimal rendicontabileMssimoFattura = NumberUtil.sum(imponibileFattura, ivaACostoFattura);

        /*
         * Recupero i rendicontabili della fattura
         */
        BigDecimal totaleRendicontabili = new BigDecimal(0);
        PbandiRDocSpesaProgettoVO filterDocPrjVO = new PbandiRDocSpesaProgettoVO();
        filterDocPrjVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(notacredito.getIdDocRiferimento()));
        List<PbandiRDocSpesaProgettoVO> rendicontabiliAssociatiFattura = genericDAO.findListWhere(filterDocPrjVO);
        for (PbandiRDocSpesaProgettoVO docPrj : rendicontabiliAssociatiFattura) {
            totaleRendicontabili = NumberUtil.sum(totaleRendicontabili, docPrj.getImportoRendicontazione());
        }

        /*
         * Ricerco le note di credito associate alla fattura,
         * escludendo eventualmente la nota di credito attuale (in caso di modifica)
         */
        /*
         * FIX PBandi-2314. Poiche' le note di credito possono
         * essere associate a piu' progetti e devo considerare
         * solamente gli importi relativi alla nota di credito
         * devo eliminare le eventuali note duplicate
         */
        List<DocumentoDiSpesaProgettoVO> noteCreditoFattura = documentoDiSpesaManager.eliminaNoteCreditoDuplicate(
                documentoDiSpesaManager.findNoteCreditoFattura(null, notacredito.getIdDocRiferimento()));

        /*
         * Dal rendicontabile massimo della fattura sottraggo l' imponibile e l'iva a
         * costo
         * delle note di credito
         */
        BigDecimal totaleNettoFattura = rendicontabileMssimoFattura;
        for (DocumentoDiSpesaProgettoVO nota : noteCreditoFattura) {
            if (notacredito.getIdDocumentoDiSpesa() == null ||
                    NumberUtil.compare(NumberUtil.toBigDecimal(notacredito.getIdDocumentoDiSpesa()),
                            nota.getIdDocumentoDiSpesa()) != 0) {
                BigDecimal imponibileNota = nota.getImponibile() == null ? new BigDecimal(0) : nota.getImponibile();
                BigDecimal ivaACostoNota = nota.getImportoIvaCosto() == null ? new BigDecimal(0)
                        : nota.getImportoIvaCosto();
                totaleNettoFattura = NumberUtil.subtract(totaleNettoFattura,
                        NumberUtil.sum(imponibileNota, ivaACostoNota));
            }
        }

        /*
         * Sottraggo al totale della fattura il totale delle nota di
         * credito corrente
         */
        BigDecimal imponibileNota = notacredito.getImponibile() == null ? new BigDecimal(0)
                : new BigDecimal(notacredito.getImponibile());
        BigDecimal ivaACostoNota = notacredito.getImportoIvaACosto() == null ? new BigDecimal(0)
                : new BigDecimal(notacredito.getImportoIvaACosto());
        totaleNettoFattura = NumberUtil.subtract(totaleNettoFattura, NumberUtil.sum(imponibileNota, ivaACostoNota));

        if (NumberUtil.compare(totaleRendicontabili, totaleNettoFattura) > 0) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkTotaleRendicontabileSuperioreRendicontabileMassimoDocumento(
            DocumentoDiSpesaDTO documentoDiSpesa) {
        /*
         * FIX PBandi-2314. Poiche' le note di credito possono
         * essere associate a piu' progetti e devo considerare
         * solamente gli importi relativi alla nota di credito
         * devo eliminare le eventuali note duplicate
         */
        List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager.eliminaNoteCreditoDuplicate(
                documentoDiSpesaManager.findNoteCreditoFattura(null, documentoDiSpesa.getIdDocumentoDiSpesa()));
        PbandiRDocSpesaProgettoVO filterVO = new PbandiRDocSpesaProgettoVO();
        filterVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(documentoDiSpesa.getIdDocumentoDiSpesa()));

        /*
         * Calcolo il totale delle note di credito associate al documento
         */
        BigDecimal totaleNoteCredito = new BigDecimal(0);
        for (DocumentoDiSpesaProgettoVO nota : noteCredito) {
            BigDecimal imponibileNota = nota.getImponibile() == null ? new BigDecimal(0) : nota.getImponibile();
            BigDecimal ivaacostoNota = nota.getImportoIvaCosto() == null ? new BigDecimal(0)
                    : nota.getImportoIvaCosto();
            totaleNoteCredito = NumberUtil.sum(totaleNoteCredito, NumberUtil.sum(imponibileNota, ivaacostoNota));
        }

        BigDecimal imponibileFattura = documentoDiSpesa.getImponibile() == null ? new BigDecimal(0)
                : new BigDecimal(documentoDiSpesa.getImponibile());
        BigDecimal ivaacostoFattura = documentoDiSpesa.getImportoIvaACosto() == null ? new BigDecimal(0)
                : new BigDecimal(documentoDiSpesa.getImportoIvaACosto());
        BigDecimal rendicontabileMassimoFattura = NumberUtil.sum(imponibileFattura, ivaacostoFattura);
        rendicontabileMassimoFattura = NumberUtil.subtract(rendicontabileMassimoFattura, totaleNoteCredito);

        PbandiRDocSpesaProgettoVO notFilter = new PbandiRDocSpesaProgettoVO();
        notFilter.setIdProgetto(NumberUtil.toBigDecimal(documentoDiSpesa.getIdProgetto()));

        List<PbandiRDocSpesaProgettoVO> documentoAssociato = genericDAO
                .findListWhere(new AndCondition<PbandiRDocSpesaProgettoVO>(Condition.filterBy(filterVO),
                        Condition.not(Condition.filterBy(notFilter))));

        BigDecimal totaleRendicontabile = new BigDecimal(0);
        for (PbandiRDocSpesaProgettoVO docprj : documentoAssociato) {
            totaleRendicontabile = NumberUtil.sum(totaleRendicontabile, docprj.getImportoRendicontazione());
        }

        /*
         * Aggiungo al totale rendicontabile per il documento per gli altri progetti
         * l' importo rendicontabile per il documento-progetto corrente
         */
        totaleRendicontabile = NumberUtil.sum(new BigDecimal(documentoDiSpesa.getImportoRendicontabile()),
                totaleRendicontabile);

        if (NumberUtil.compare(rendicontabileMassimoFattura, totaleRendicontabile) < 0) {
            return true;
        } else {
            return false;
        }

    }

    private void updateMassivoImportoValidatoPagamentiDichiarazione(Long idDichiarazione, Long idUtente,
            List<BigDecimal> pagamenti, BigDecimal importoValidato) throws Exception {
        PbandiRPagQuotParteDocSpVO filterDichiarazioneVO = new PbandiRPagQuotParteDocSpVO();
        filterDichiarazioneVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));

        List<PbandiRPagQuotParteDocSpVO> listFilterPagamentoVO = new ArrayList<PbandiRPagQuotParteDocSpVO>();

        for (BigDecimal idPag : pagamenti) {
            PbandiRPagQuotParteDocSpVO filterPagVO = new PbandiRPagQuotParteDocSpVO();
            filterPagVO.setIdPagamento(idPag);
            listFilterPagamentoVO.add(filterPagVO);
        }

        AndCondition<PbandiRPagQuotParteDocSpVO> andConditionUpdate = new AndCondition<PbandiRPagQuotParteDocSpVO>(
                Condition.filterBy(filterDichiarazioneVO), Condition.filterBy(listFilterPagamentoVO));
        PbandiRPagQuotParteDocSpVO newValue = new PbandiRPagQuotParteDocSpVO();
        newValue.setImportoValidato(importoValidato);
        newValue.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
        genericDAO.update(andConditionUpdate, newValue);
    }

    private void updateMassivoStatoValidazioneDocumenti(Long idUtente, Long idProgetto, String descBreveStatoDocumento,
            List<Long> documenti) throws Exception {
        PbandiRDocSpesaProgettoVO filterDocPrjVO = new PbandiRDocSpesaProgettoVO();
        filterDocPrjVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));

        List<PbandiRDocSpesaProgettoVO> listFilterDocPrjVO = new ArrayList<PbandiRDocSpesaProgettoVO>();
        for (Long idDocumento : documenti) {
            PbandiRDocSpesaProgettoVO filterDocVO = new PbandiRDocSpesaProgettoVO();
            filterDocVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumento));
            listFilterDocPrjVO.add(filterDocVO);
        }

        DecodificaDTO statoValidazioneDocumento = decodificheManager
                .findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, descBreveStatoDocumento);

        AndCondition<PbandiRDocSpesaProgettoVO> andConditionDocPrj = new AndCondition<PbandiRDocSpesaProgettoVO>(
                Condition.filterBy(filterDocPrjVO), Condition.filterBy(listFilterDocPrjVO));

        PbandiRDocSpesaProgettoVO newValueDocPrj = new PbandiRDocSpesaProgettoVO();
        newValueDocPrj.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoValidazioneDocumento.getId()));
        newValueDocPrj.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));

        genericDAO.update(andConditionDocPrj, newValueDocPrj);
    }

    private List<PagamentoDichiarazioneVO> findPagamentiDocumentiDichiarazione(Long idDichiarazione,
            List<Long> documentiDiSpesa) {
        /*
         * Ricerco i pagamenti associati alla dichiarazione di spesa
         * per i documenti.
         */
        PagamentoDocumentoDichiarazioneVO filterVO = new PagamentoDocumentoDichiarazioneVO();
        filterVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));

        List<PagamentoDocumentoDichiarazioneVO> listFilterDocumentiVO = new ArrayList<PagamentoDocumentoDichiarazioneVO>();
        for (Long idDocumento : documentiDiSpesa) {
            PagamentoDocumentoDichiarazioneVO filterDocVO = new PagamentoDocumentoDichiarazioneVO();
            filterDocVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumento));
            listFilterDocumentiVO.add(filterDocVO);
        }

        AndCondition<PagamentoDocumentoDichiarazioneVO> andCondition = new AndCondition<PagamentoDocumentoDichiarazioneVO>(
                Condition.filterBy(filterVO), Condition.filterBy(listFilterDocumentiVO));

        List<PagamentoDichiarazioneVO> pagamentiDichidarazione = genericDAO.findListWhereDistinct(andCondition,
                PagamentoDichiarazioneVO.class);

        return pagamentiDichidarazione;
    }

    private long invalidaDocumenti(Long idUtente, Long idDichiarazione, List<Long> documentiDiSpesa) throws Exception {

        /*
         * recupero i dati relativi alla dichiarazione di spesa
         */
        PbandiTDichiarazioneSpesaVO filterDichVO = new PbandiTDichiarazioneSpesaVO();
        filterDichVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
        PbandiTDichiarazioneSpesaVO dichiarazioneVO = genericDAO.findSingleWhere(filterDichVO);

        /*
         * Ricerco i pagamenti associati alla dichiarazione per
         * le i documenti.
         */
        List<PagamentoDichiarazioneVO> pagamentiDichiarazione = findPagamentiDocumentiDichiarazione(idDichiarazione,
                documentiDiSpesa);

        /*
         * Imposto a 0 l'importo validato
         */
        List<BigDecimal> pagamenti = new ArrayList<BigDecimal>();
        for (PagamentoDichiarazioneVO pag : pagamentiDichiarazione) {
            pagamenti.add(pag.getIdPagamento());
        }
        updateMassivoImportoValidatoPagamentiDichiarazione(idDichiarazione, idUtente, pagamenti, new BigDecimal(0));

        /*
         * Aggiungo ai documenti le note di credito associate
         * al documento progetto
         */
        List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager
                .findNoteCreditoDocumenti(NumberUtil.toLong(dichiarazioneVO.getIdProgetto()), documentiDiSpesa);

        List<Long> idNoteCredito = beanUtil.extractValues(noteCredito, "idDocumentoDiSpesa", Long.class);
        if (!ObjectUtil.isEmpty(idNoteCredito)) {
            for (Long idNota : idNoteCredito)
                documentiDiSpesa.add(idNota);
        }

        /*
         * imposto a NON_VALIDATO lo stato dei documenti di spesa
         */

        updateMassivoStatoValidazioneDocumenti(idUtente, NumberUtil.toLong(dichiarazioneVO.getIdProgetto()),
                GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO, documentiDiSpesa);

        return new Long(documentiDiSpesa.size());
    }

    private long validaDocumenti(Long idUtente, Long idDichiarazione, List<Long> documentiDiSpesa) throws Exception {

        /*
         * recupero i dati relativi alla dichiarazione di spesa
         */
        PbandiTDichiarazioneSpesaVO filterDichVO = new PbandiTDichiarazioneSpesaVO();
        filterDichVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
        PbandiTDichiarazioneSpesaVO dichiarazioneVO = genericDAO.findSingleWhere(filterDichVO);

        /*
         * Ricerco i pagamenti associati alla dichiarazione per
         * le i documenti.
         */
        List<PagamentoDichiarazioneVO> pagamentiDichiarazione = findPagamentiDocumentiDichiarazione(idDichiarazione,
                documentiDiSpesa);

        /*
         * Per ogni pagamento del documento della dichiarazione imposto
         * l' importo validato pari al quietanzato
         */
        PbandiRPagQuotParteDocSpVO filterVO = new PbandiRPagQuotParteDocSpVO();
        filterVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));

        List<PbandiRPagQuotParteDocSpVO> listFilter = new ArrayList<PbandiRPagQuotParteDocSpVO>();
        for (PagamentoDichiarazioneVO p : pagamentiDichiarazione) {
            PbandiRPagQuotParteDocSpVO f = new PbandiRPagQuotParteDocSpVO();
            f.setIdPagamento(p.getIdPagamento());
            listFilter.add(f);
        }

        AndCondition<PbandiRPagQuotParteDocSpVO> andCondition = new AndCondition<PbandiRPagQuotParteDocSpVO>(
                Condition.filterBy(filterVO), Condition.filterBy(listFilter));

        List<PbandiRPagQuotParteDocSpVO> quote = genericDAO.findListWhere(andCondition);
        for (PbandiRPagQuotParteDocSpVO q : quote) {
            q.setImportoValidato(q.getImportoQuietanzato());
            q.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
            genericDAO.update(q);
        }

        /*
         * Aggiungo ai documenti le note di credito associate
         * al documento progetto
         */
        List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager
                .findNoteCreditoDocumenti(NumberUtil.toLong(dichiarazioneVO.getIdProgetto()), documentiDiSpesa);

        List<Long> idNoteCredito = beanUtil.extractValues(noteCredito, "idDocumentoDiSpesa", Long.class);
        if (!ObjectUtil.isEmpty(idNoteCredito)) {
            for (Long idNota : idNoteCredito)
                documentiDiSpesa.add(idNota);
        }

        /*
         * imposto a VALIDATO lo stato dei documenti di spesa
         */
        updateMassivoStatoValidazioneDocumenti(idUtente, NumberUtil.toLong(dichiarazioneVO.getIdProgetto()),
                GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO, documentiDiSpesa);

        return new Long(documentiDiSpesa.size());
    }
    
    private long sospendiDocumenti(Long idUtente, Long idDichiarazione, List<Long> documentiDiSpesa, Boolean fromAttivitaValidazione) throws Exception {

        /*
         * recupero i dati relativi alla dichiarazione di spesa
         */
        PbandiTDichiarazioneSpesaVO filterDichVO = new PbandiTDichiarazioneSpesaVO();
        filterDichVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
        PbandiTDichiarazioneSpesaVO dichiarazioneVO = genericDAO.findSingleWhere(filterDichVO);
        
        if(fromAttivitaValidazione.equals(Boolean.TRUE)) {
        	/*
	         * Ricerco i pagamenti associati alla dichiarazione per
	         * le i documenti.
	         */
        	List<PagamentoDichiarazioneVO> pagamentiDichiarazione = findPagamentiDocumentiDichiarazione(idDichiarazione,
                    documentiDiSpesa);
	        /*
	         * Per ogni pagamento del documento della dichiarazione imposto
	         * l' importo validato pari al quietanzato
	         */
	        PbandiRPagQuotParteDocSpVO filterVO = new PbandiRPagQuotParteDocSpVO();
	        filterVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
	
	        List<PbandiRPagQuotParteDocSpVO> listFilter = new ArrayList<PbandiRPagQuotParteDocSpVO>();
	        for (PagamentoDichiarazioneVO p : pagamentiDichiarazione) {
	            PbandiRPagQuotParteDocSpVO f = new PbandiRPagQuotParteDocSpVO();
	            f.setIdPagamento(p.getIdPagamento());
	            listFilter.add(f);
	        }
	
	        AndCondition<PbandiRPagQuotParteDocSpVO> andCondition = new AndCondition<PbandiRPagQuotParteDocSpVO>(
	                Condition.filterBy(filterVO), Condition.filterBy(listFilter));
	
	        List<PbandiRPagQuotParteDocSpVO> quote = genericDAO.findListWhere(andCondition);
	        for (PbandiRPagQuotParteDocSpVO q : quote) {
	            q.setImportoValidato(BigDecimal.ZERO);
	            q.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
	            genericDAO.update(q);
	        }
        }

        /*
         * Aggiungo ai documenti le note di credito associate
         * al documento progetto
         */
        List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager
                .findNoteCreditoDocumenti(NumberUtil.toLong(dichiarazioneVO.getIdProgetto()), documentiDiSpesa);

        List<Long> idNoteCredito = beanUtil.extractValues(noteCredito, "idDocumentoDiSpesa", Long.class);
        if (!ObjectUtil.isEmpty(idNoteCredito)) {
            for (Long idNota : idNoteCredito)
                documentiDiSpesa.add(idNota);
        }

        /*
         * imposto a SOSPESO lo stato dei documenti di spesa
         */
        updateMassivoStatoValidazioneDocumenti(idUtente, NumberUtil.toLong(dichiarazioneVO.getIdProgetto()),
                GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_SOSPESO, documentiDiSpesa);

        return new Long(documentiDiSpesa.size());
    }

    public EsitoOperazioneDocumentoDiSpesa respingiDocumento(Long idUtente,
            String identitaDigitale, Long idDocumento, Long idDichiarazione)
            throws CSIException, SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDocumento", "idDichiarazione" };
        EsitoOperazioneDocumentoDiSpesa esito = new EsitoOperazioneDocumentoDiSpesa();
        esito.setEsito(Boolean.FALSE);
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumento, idDichiarazione);
        List<Long> documenti = new ArrayList<Long>();
        documenti.add(idDocumento);
        try {
            respingiDocumenti(idUtente, idDichiarazione, documenti);
            esito.setEsito(Boolean.TRUE);
            MessaggioDTO msg = new MessaggioDTO();
            msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

            esito.setMessaggi(new MessaggioDTO[] { msg });
            return esito;
        } catch (Exception e) {
            throw new ValidazioneRendicontazioneException("Errore durante il respingiDocumento", e);
        }

    }

    public EsitoControlloDocumenti checkStatoDocumenti(Long idUtente,
            String identitaDigitale, Long idDichiarazione) throws CSIException,
            SystemException, UnrecoverableException,
            ValidazioneRendicontazioneException {

        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDichiarazione" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDichiarazione);
        /*
         * Verifico se tutti i documenti per la chiusura validazione
         * sono nello stato validazione IN VALIDAZIONE
         */
        EsitoControlloDocumenti esito = new EsitoControlloDocumenti();

        DocumentiDiSpesaPerChiusuraValidazioneVO filtroDichiarazione = new DocumentiDiSpesaPerChiusuraValidazioneVO();
        filtroDichiarazione.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
        filtroDichiarazione.setDescBreveStatoDocValid(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE);

        DocumentiDiSpesaPerChiusuraValidazioneVO filtroStatoValid = new DocumentiDiSpesaPerChiusuraValidazioneVO();
        filtroStatoValid.setDescBreveStatoDocValid(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE);
        /*
         * Ricerco i documenti in stato validazione IN VALIDAZIONE
         */
        List<DocumentiDiSpesaPerChiusuraValidazioneVO> documenti = genericDAO
                .findListWhere(Condition.filterBy(filtroDichiarazione));

        if (!ObjectUtil.isEmpty(documenti)) {
            esito.setEsito(Boolean.FALSE);
            esito.setMessage(ErrorConstants.ERRORE_VALIDAZIONE_PRESENTI_DOCUMENTI_IN_VALIDAZIONE);
        } else {
            esito.setEsito(Boolean.TRUE);
        }

        return esito;

    }

    public EsitoDettaglioDocumento findDettaglioDocumentoDiSpesaRettifica(
            Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa,
            Long idProgetto) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {

        EsitoDettaglioDocumento esito = new EsitoDettaglioDocumento();
        DocumentoDiSpesaDTO documentoDiSpesaDTO = new DocumentoDiSpesaDTO();
        String[] nameParameter = { "idUtente", "identitaDigitale",
                "idDocumentoDiSpesa", "idProgetto" };

        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumentoDiSpesa, idProgetto);

        documentoDiSpesaDTO = findDocumentoDiSpesaDTO(idDocumentoDiSpesa,
                idProgetto);

        /**
         * FIX : vittorio. Gestione del caso in cui il documento non ha un
         * fornitore (SPESA-FORFETTARIA
         * 
         */
        FornitoreVO fornitore = null;
        if (documentoDiSpesaDTO.getIdFornitore() != null) {
            logger.debug("cerco i dati del fornitore del documento di spesa");
            boolean checkDataFine = false;
            fornitore = pbandiFornitoriDAO.findDettaglioFornitore(
                    documentoDiSpesaDTO.getIdFornitore(), documentoDiSpesaDTO.getProgrFornitoreQualifica(),
                    checkDataFine);
        }
        String denominazioneFornitore = "";
        if (!isNull(fornitore)) {
            logger.debug("TROVATO Il fornitore del documento di spesa");
            if (!isEmpty(fornitore.getDenominazioneFornitore())) {
                logger.debug("Il fornitore del documento di spesa è un ente giuridico,setto la denominazione: "
                        + fornitore.getDenominazioneFornitore());
                denominazioneFornitore = fornitore
                        .getDenominazioneFornitore();
            } else if (!isEmpty(fornitore.getCognomeFornitore())) {
                denominazioneFornitore = fornitore.getCognomeFornitore();
                if (!isEmpty(fornitore.getNomeFornitore()))
                    denominazioneFornitore += " "
                            + fornitore.getNomeFornitore();

                logger.debug("Il fornitore del documento di spesa è una persona fisica ,setto la denominazione: "
                        + denominazioneFornitore);
            }

            if (fornitore.getCostoOrario() != null)
                documentoDiSpesaDTO.setCostoOrario(fornitore
                        .getCostoOrario());

        }
        documentoDiSpesaDTO
                .setDenominazioneFornitore(denominazioneFornitore);

        /*
         * Numero documento di riferimento Stringa Nel caso in cui il tipo
         * di documento di spesa sia NOTA CREDITO indicare in questo campo
         * il numero della fattura alla quale la nota si riferisce (è
         * riferito a). Data documento di riferimento Stringa Nel caso in
         * cui il tipo di documento di spesa sia NOTA CREDITO indicare in
         * questo campo la data della fattura alla quale la nota si
         * riferisce (è riferito a).
         */
        if (isNotaDiCredito(documentoDiSpesaDTO.getIdTipoDocumentoDiSpesa())) {
            Long idDocDiRiferimento = documentoDiSpesaDTO
                    .getIdDocRiferimento();

            logger.debug("il doc di spesa è una NOTA di CREDITO,ci deve essere una fattura collegata");
            DocumentoDiSpesaVO documentoDiSpesaDiRiferimentoVO = pbandiDocumentiDiSpesaDAO
                    .findDettaglioDocumentoDiSpesa(idDocDiRiferimento,
                            idProgetto);
            if (!isNull(documentoDiSpesaDiRiferimentoVO)) {
                logger.debug("setto numero e data del doc di spesa di riferimento");
                documentoDiSpesaDTO
                        .setNumeroDocumentoDiSpesaDiRiferimento(documentoDiSpesaDiRiferimentoVO
                                .getNumeroDocumento());
                documentoDiSpesaDTO
                        .setDataDocumentoDiSpesaDiRiferimento(documentoDiSpesaDiRiferimentoVO
                                .getDataDocumentoDiSpesa());
            }

        }
        esito.setDocumentoDiSpesa(documentoDiSpesaDTO);
        esito.setEsito(Boolean.TRUE);
        return esito;

    }

    public void saveNoteValidazioneDoc(java.lang.Long idUtente, java.lang.String identitaDigitale,
            java.lang.Long idDocumentoDiSpesa, java.lang.Long idProgetto,
            java.lang.String noteValidazione) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {
        String[] nameParameter = { "idUtente", "identitaDigitale", "idDocumentoDiSpesa", "idProgetto" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente,
                identitaDigitale, idDocumentoDiSpesa, idProgetto);

        PbandiRDocSpesaProgettoVO vo = new PbandiRDocSpesaProgettoVO();
        vo.setIdDocumentoDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa));
        vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
        vo = genericDAO.findSingleWhere(vo);
        vo.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
        vo.setNoteValidazione(noteValidazione);
        try {
            genericDAO.updateNullables(vo);
        } catch (Exception e) {
            logger.error("Error saving noteValidazione for idProgetto " + idProgetto + " and idDocumentoDiSpesa:"
                    + idDocumentoDiSpesa, e);
            throw new ValidazioneRendicontazioneException(e.getMessage());
        }

    }

    private void chiudiRichiesteIntegrazioneDsAperte(Long idUtente, String identitaDigitale,
            Long idDichiarazioneDiSpesa)
            throws ValidazioneRendicontazioneException, SystemException, UnrecoverableException, CSIException {
        IntegrazioneSpesaDTO[] lista = this.findIntegrazioniSpesa(idUtente, identitaDigitale, idDichiarazioneDiSpesa);
        logger.info("chiudiRichiesteIntegrazioneDsAperte(): num richieste integrazione aventi idDichiarazioneDiSpesa = "
                + idDichiarazioneDiSpesa + " : " + lista.length);
        for (IntegrazioneSpesaDTO dto : lista) {
            if (dto.getDataInvio() == null) {
                // Chiude la richiesta.
                logger.info("chiudiRichiesteIntegrazioneDsAperte(): chiudo PBANDI_T_INTEGRAZIONE_SPESA con id "
                        + dto.getIdIntegrazioneSpesa());
                dto.setDataInvio(DateUtil.getDataOdierna());
                dto.setIdUtenteInvio(idUtente);
                this.salvaIntegrazioneSpesa(idUtente, identitaDigitale, dto);
            }
        }
    }

    // PK vecchio metodo con 4 parametri IN
    public EsitoValidazioneRendicontazione chiudiValidazioneChecklistHtml(
            Long idUtente, String identitaDigitale,
            IstanzaAttivitaDTO istanzaAttivitaDTO,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO)
            throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {

        logger.info("chiudiValidazioneChecklistHtml BEGIN");

        EsitoValidazioneRendicontazione esitoValidazioneRendicontazione = new EsitoValidazioneRendicontazione();
        BigDecimal idProgetto = null;
        BigDecimal idDichiarazione = null;

        String[] nameParameter = { "idUtente", "identitaDigitale", "validazioneRendicontazioneDTO", "checkListHtml" };

        ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
                validazioneRendicontazioneDTO, validazioneRendicontazioneDTO.getCheckListHtml());

        // PK : istanzaAttivitaDTO non serve a nulla???
        ValidatorInput.verifyBeansNotEmpty(new String[] { "istanzaAttivitaDTO" }, istanzaAttivitaDTO);

        try {

            PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = chiudiDichiarazione(idUtente,
                    validazioneRendicontazioneDTO);
            idDichiarazione = dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa();
            logger.info("chiudiValidazioneChecklistHtml idDichiarazione=" + idDichiarazione);

            modificaStatoDocDiSpesaCoinvolti(idUtente, validazioneRendicontazioneDTO);

            // Chiude le eventuali richieste di integrazione della DS ancora aperte.
            chiudiRichiesteIntegrazioneDsAperte(idUtente, identitaDigitale,
                    validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa());

            // FIXME: M:E Fix +GREEN
            // idProgetto = dichiarazioneDiSpesaVO.getIdProgetto();

            idProgetto = new BigDecimal(validazioneRendicontazioneDTO.getIdProgetto());

            validazioneRendicontazioneDTO.setDataChiusura(new Date());

            ChecklistHtmlDTO checklistHtmlDTO = new ChecklistHtmlDTO();
            checklistHtmlDTO.setContentHtml(validazioneRendicontazioneDTO.getCheckListHtml());

            EsitoSalvaModuloCheckListDTO esito = checkListManager.saveChecklistValidazioneHtml(
                    idUtente,
                    idProgetto.longValue(),
                    GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO,
                    validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa(),
                    checklistHtmlDTO);
            logger.info("chiudiValidazioneChecklistHtml esito=" + esito);

            esitoValidazioneRendicontazione.setIdDocIndexDichiarazione(esito.getIdDocumentoIndex());

            if (!esito.getEsito()) {
                if (esito.getMessage().equals(ERRORE_CHECKLIST_TRASCORSO_TIMEOUT)) {
                    throw new ValidazioneRendicontazioneException(
                            esito.getMessage() + "-" + esito.getParams()[0]);
                } else {
                    throw new ValidazioneRendicontazioneException(
                            esito.getMessage());
                }
            }

            // GIUGNO 2014, added report geneneration
            // M:E report non neccessario per il progetto contributo
            boolean isProgettoContributo = progettoManager.isProgettoContributo(idProgetto.longValue());
            logger.info("chiudiValidazioneChecklistHtml isProgettoContributo=" + isProgettoContributo);

            if (!isProgettoContributo) {
                EsitoReportDettaglioDocumentiDiSpesaDTO esitoGeneraReport = generaReportDettaglioDocumentoDiSpesa(
                        idUtente, identitaDigitale, idDichiarazione.longValue());
                logger.info("chiudiValidazioneChecklistHtml esitoGeneraReport generato");

                if (esitoGeneraReport != null) {
                    // save report dettaglio documenti di spesa on index
                    String nomeFile = esitoGeneraReport.getNomeFile();
                    logger.info("chiudiValidazioneChecklistHtml nomeFile=" + nomeFile);
                    // nomeFile = "reportValidazione" + idDichiarazioneSpesa + ".xls";

                    // Id del nuovo record della PBANDI_T_DOCUMENTO_INDEX.
                    BigDecimal idTDocumentoIndex = documentoWebManager.nuovoIdTDocumentoIndex();
                    if (idTDocumentoIndex == null) {
                        logger.error("chiudiValidazioneChecklistHtml Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.");
                        throw new Exception("Impossibile ottenere l'identificativo dalla tabella dei documenti ");
                    }

                    // Nome univoco con cui il file verrà salvato su File System.
                    String newName = nomeFile.replaceFirst("\\.", "_" + idTDocumentoIndex.longValue() + ".");
                    logger.info("chiudiValidazioneChecklistHtml newName=" + newName);

                    byte[] reportBytes = esitoGeneraReport.getExcelBytes();

                    // PK : elimino Invocazione Index
//					Node node =null;
//					nomeFile = indexDAO.addTimestampToFileName(nomeFile);
                    logger.info("validazioneRendicontazioneDTO: idprogetto :"
                            + validazioneRendicontazioneDTO.getIdProgetto() +
                            " codiceProgetto: " + validazioneRendicontazioneDTO.getCodiceProgetto() +
                            " validazioneRendicontazioneDTO.getIdSoggettoBen() "
                            + validazioneRendicontazioneDTO.getIdSoggettoBen() +
                            " validazioneRendicontazioneDTO.getCfBeneficiario() "
                            + validazioneRendicontazioneDTO.getCfBeneficiario());

                    // metto reportBytes su Index
//					node = indexDAO.creaContentReportDettaglioDocSpesa(idUtente,validazioneRendicontazioneDTO.getIdProgetto(),
//							validazioneRendicontazioneDTO.getCodiceProgetto(), 
//							idDichiarazione.longValue(),
//							validazioneRendicontazioneDTO.getIdSoggettoBen(),
//							validazioneRendicontazioneDTO.getCfBeneficiario(), nomeFile, reportBytes);

                    // PK : salvo su Storage
                    // il salvataggio sulle tabelle del DB e' gestito dopo...
                    InputStream is = new ByteArrayInputStream(reportBytes);
                    documentoWebManager.salvaSuFileSystem(is, newName,
                            GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS);

//					//PK : questo salva anche nella tabella documento_index
//					boolean y = documentoWebManager.salvaFile(reportBytes, vo);  
//					//PK meglio usare ?? 
//					documentoWebManager.salvaSuFileSystem(inputStream, strFileName, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS);

                    BigDecimal idTarget = idDichiarazione;

                    String shaHex = null;
                    if (reportBytes != null)
                        shaHex = DigestUtils.shaHex(reportBytes);

                    it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoWebManager
                            .salvaInfoNodoIndexSuDbExl(
                                    nomeFile, idTarget, null, null,
                                    new BigDecimal(validazioneRendicontazioneDTO.getIdProgetto()),
                                    GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS,
                                    it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO.class,
                                    null, null, idUtente, shaHex, idTDocumentoIndex, newName);

//					it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoWebManager.salvaInfoNodoIndexSuDb( 
//							nomeFile,idTarget,null,null,new BigDecimal( validazioneRendicontazioneDTO.getIdProgetto()),
//							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS, it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO.class, 
//							null, null, idUtente,shaHex);

                    // OLD
//					PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoManager.salvaInfoNodoIndexSuDb(node, nomeFile,
//							idTarget,null,null,new BigDecimal( validazioneRendicontazioneDTO.getIdProgetto()),
//							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS, 
//							PbandiTDichiarazioneSpesaVO.class, 
//							null, null, idUtente,shaHex);

                    esitoValidazioneRendicontazione
                            .setIdReportDettaglioDocSpesa(reportDettaglioDocSpesa.getIdDocumentoIndex().longValue());
                }
                // GIUGNO 2014, added report geneneration

                String tipoDichiarazioneCorrente = decodificheManager.findDescBreve(PbandiDTipoDichiarazSpesaVO.class,
                        dichiarazioneDiSpesaVO.getIdTipoDichiarazSpesa());
                logger.info("validazioneRendicontazioneDTO: tipoDichiarazioneCorrente :" + tipoDichiarazioneCorrente);

                String descrBreveTemplateNotifica = "";
                List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();

                if (tipoDichiarazioneCorrente
                        .equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA)
                        || tipoDichiarazioneCorrente
                                .equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE)) {
                    descrBreveTemplateNotifica = Notification.NOTIFICA_VALIDAZIONE_DI_SPESA_FINALE;
                    if (validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa() != null
                            && validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa().equalsIgnoreCase("S")) {
                        logger.warn(
                                "\n\n\n############################ NEOFLUX unlock VALID_DICH_SPESA_FINALE ##############################\n");
                        neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                                Task.VALID_DICH_SPESA_FINALE);
                        logger.warn(
                                "############################ NEOFLUX unlock VALID_DICH_SPESA_FINALE ##############################\n\n\n\n");
                    } else {
                        logger.warn(
                                "\n\n\n############################ NEOFLUX end VALID_DICH_SPESA_FINALE ##############################\n");
                        neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                                Task.VALID_DICH_SPESA_FINALE);
                        logger.warn(
                                "############################ NEOFLUX end VALID_DICH_SPESA_FINALE ##############################\n\n\n\n");
                    }

                    MetaDataVO metadata1 = new MetaDataVO();
                    metadata1.setNome(Notification.DATA_CHIUSURA_VALIDAZIONE);
                    metadata1.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtChiusuraValidazione()));
                    metaDatas.add(metadata1);

                    MetaDataVO metadata2 = new MetaDataVO();
                    metadata2.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
                    metadata2.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtDichiarazione()));
                    metaDatas.add(metadata2);

                    MetaDataVO metadata3 = new MetaDataVO();
                    metadata3.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
                    metadata3.setValore(dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa().toString());
                    metaDatas.add(metadata3);
                } else {
                    descrBreveTemplateNotifica = Notification.NOTIFICA_VALIDAZIONE;
                    logger.warn(
                            "\n\n\n############################ NEOFLUX UNLOCK  VALID_DICH_SPESA ##############################\n");
                    neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                            Task.VALID_DICH_SPESA);
                    logger.warn(
                            "############################ NEOFLUX UNLOCK VALID_DICH_SPESA ##############################\n\n\n\n");

                    MetaDataVO metadata1 = new MetaDataVO();
                    metadata1.setNome(Notification.DESC_DICHIARAZIONE_DI_SPESA);
                    metadata1.setValore(dichiarazioneDiSpesaVO.getNoteChiusuraValidazione());
                    metaDatas.add(metadata1);

                    MetaDataVO metadata2 = new MetaDataVO();
                    metadata2.setNome(Notification.DATA_CHIUSURA_VALIDAZIONE);
                    metadata2.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtChiusuraValidazione()));
                    metaDatas.add(metadata2);

                    MetaDataVO metadata3 = new MetaDataVO();
                    metadata3.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
                    metadata3.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtDichiarazione()));
                    metaDatas.add(metadata3);

                    MetaDataVO metadata4 = new MetaDataVO();
                    metadata4.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
                    metadata4.setValore(dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa().toString());
                    metaDatas.add(metadata4);
                }

                logger.info("validazioneRendicontazioneDTO: descrBreveTemplateNotifica :" + descrBreveTemplateNotifica);

                logger.info("calling genericDAO.callProcedure().putNotificationMetadata VALID_DICH_SPESA");
                genericDAO.callProcedure().putNotificationMetadata(metaDatas);

                logger.info("calling genericDAO.callProcedure().sendNotificationMessage VALID_DICH_SPESA");
                genericDAO.callProcedure().sendNotificationMessage(idProgetto, descrBreveTemplateNotifica,
                        Notification.BENEFICIARIO, idUtente);
            }

            esitoValidazioneRendicontazione.setEsito(Boolean.TRUE);
            esitoValidazioneRendicontazione.setMessage(MessaggiConstants.DICHIARAZIONE_CHIUSA);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            if (e instanceof ValidazioneRendicontazioneException) {
                throw (ValidazioneRendicontazioneException) e;
            } else {
                logger.error(e.getMessage(), e);
                throw new ValidazioneRendicontazioneException(e.getMessage(), e);
            }
        }

        return esitoValidazioneRendicontazione;
    }

    public EsitoValidazioneRendicontazione chiudiValidazioneChecklistHtml(
            Long idUtente, String identitaDigitale,
            IstanzaAttivitaDTO istanzaAttivitaDTO,
            ValidazioneRendicontazioneDTO validazioneRendicontazioneDTO,
            Long checklistSelezionata,
            FileDTO[] verbali)
            throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {

        logger.info("chiudiValidazioneChecklistHtml BEGIN");

        EsitoValidazioneRendicontazione esitoValidazioneRendicontazione = new EsitoValidazioneRendicontazione();
        BigDecimal idProgetto = null;
        BigDecimal idDichiarazione = null;

        String[] nameParameter = { "idUtente", "identitaDigitale", "validazioneRendicontazioneDTO", "checkListHtml" };

        ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
                validazioneRendicontazioneDTO, validazioneRendicontazioneDTO.getCheckListHtml());

        // PK : istanzaAttivitaDTO non serve a nulla???
        ValidatorInput.verifyBeansNotEmpty(new String[] { "istanzaAttivitaDTO" }, istanzaAttivitaDTO);

        try {

            PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = chiudiDichiarazione(idUtente,
                    validazioneRendicontazioneDTO);
            idDichiarazione = dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa();
            logger.info("chiudiValidazioneChecklistHtml idDichiarazione=" + idDichiarazione);

            modificaStatoDocDiSpesaCoinvolti(idUtente, validazioneRendicontazioneDTO);

            // Chiude le eventuali richieste di integrazione della DS ancora aperte.
            chiudiRichiesteIntegrazioneDsAperte(idUtente, identitaDigitale,
                    validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa());

            // FIXME: M:E Fix +GREEN
            // idProgetto = dichiarazioneDiSpesaVO.getIdProgetto();

            idProgetto = new BigDecimal(validazioneRendicontazioneDTO.getIdProgetto());

            validazioneRendicontazioneDTO.setDataChiusura(new Date());

            ChecklistHtmlDTO checklistHtmlDTO = new ChecklistHtmlDTO();
            checklistHtmlDTO.setContentHtml(validazioneRendicontazioneDTO.getCheckListHtml());
            checklistHtmlDTO.setAllegati(verbali);

            // Long checklistSelezionata = null;
            if (checklistSelezionata != null) {
                logger.info("chiudiValidazioneChecklistHtml " + "chk diversa da null,checklistSelezionata "
                        + checklistSelezionata);
                checklistHtmlDTO.setIdChecklist(checklistSelezionata);

                // recupero da DB IdDocumentoIndex
                PbandiTDocumentoIndexVO docIndexVOTmp = checklistHtmlBusinessImpl
                        .getDocumentoIndexByCheckListSelezionata(idProgetto.longValue(), checklistSelezionata);
                if (docIndexVOTmp != null) {
                    checklistHtmlDTO.setIdDocumentoIndex(docIndexVOTmp.getIdDocumentoIndex().longValue());
                } else {
                    checklistHtmlDTO.setIdDocumentoIndex(null);
                }
            }

            // #####->
            EsitoSalvaModuloCheckListDTO esito = checkListManager.saveChecklistValidazioneHtml(
                    idUtente,
                    idProgetto.longValue(),
                    GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO,
                    validazioneRendicontazioneDTO.getIdDichiarazioneDiSpesa(),
                    checklistHtmlDTO);
            logger.info("chiudiValidazioneChecklistHtml esito=" + esito);

            esitoValidazioneRendicontazione.setIdDocIndexDichiarazione(esito.getIdDocumentoIndex());

            if (!esito.getEsito()) {
                if (esito.getMessage().equals(ERRORE_CHECKLIST_TRASCORSO_TIMEOUT)) {
                    throw new ValidazioneRendicontazioneException(
                            esito.getMessage() + "-" + esito.getParams()[0]);
                } else {
                    throw new ValidazioneRendicontazioneException(
                            esito.getMessage());
                }
            }

            // GIUGNO 2014, added report geneneration
            // M:E report non neccessario per il progetto contributo
            boolean isProgettoContributo = progettoManager.isProgettoContributo(idProgetto.longValue());
            logger.info("chiudiValidazioneChecklistHtml isProgettoContributo=" + isProgettoContributo);

            if (!isProgettoContributo) {
                EsitoReportDettaglioDocumentiDiSpesaDTO esitoGeneraReport = generaReportDettaglioDocumentoDiSpesa(
                        idUtente, identitaDigitale, idDichiarazione.longValue());
                logger.info("chiudiValidazioneChecklistHtml esitoGeneraReport generato");

                if (esitoGeneraReport != null) {
                    // save report dettaglio documenti di spesa on index
                    String nomeFile = esitoGeneraReport.getNomeFile();
                    logger.info("chiudiValidazioneChecklistHtml nomeFile=" + nomeFile);
                    // nomeFile = "reportValidazione" + idDichiarazioneSpesa + ".xls";

                    // Id del nuovo record della PBANDI_T_DOCUMENTO_INDEX.
                    BigDecimal idTDocumentoIndex = documentoWebManager.nuovoIdTDocumentoIndex();
                    if (idTDocumentoIndex == null) {
                        logger.error("chiudiValidazioneChecklistHtml Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.");
                        throw new Exception("Impossibile ottenere l'identificativo dalla tabella dei documenti ");
                    }

                    // Nome univoco con cui il file verrà salvato su File System.
                    String newName = nomeFile.replaceFirst("\\.", "_" + idTDocumentoIndex.longValue() + ".");
                    logger.info("chiudiValidazioneChecklistHtml newName=" + newName);

                    byte[] reportBytes = esitoGeneraReport.getExcelBytes();

                    // PK : elimino Invocazione Index
//					Node node =null;
//					nomeFile = indexDAO.addTimestampToFileName(nomeFile);
                    logger.info("validazioneRendicontazioneDTO: idprogetto :"
                            + validazioneRendicontazioneDTO.getIdProgetto() +
                            " codiceProgetto: " + validazioneRendicontazioneDTO.getCodiceProgetto() +
                            " validazioneRendicontazioneDTO.getIdSoggettoBen() "
                            + validazioneRendicontazioneDTO.getIdSoggettoBen() +
                            " validazioneRendicontazioneDTO.getCfBeneficiario() "
                            + validazioneRendicontazioneDTO.getCfBeneficiario());

                    // metto reportBytes su Index
//					node = indexDAO.creaContentReportDettaglioDocSpesa(idUtente,validazioneRendicontazioneDTO.getIdProgetto(),
//							validazioneRendicontazioneDTO.getCodiceProgetto(), 
//							idDichiarazione.longValue(),
//							validazioneRendicontazioneDTO.getIdSoggettoBen(),
//							validazioneRendicontazioneDTO.getCfBeneficiario(), nomeFile, reportBytes);

                    // PK : salvo su Storage
                    // il salvataggio sulle tabelle del DB e' gestito dopo...
                    InputStream is = new ByteArrayInputStream(reportBytes);
                    documentoWebManager.salvaSuFileSystem(is, newName,
                            GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS);

//					//PK : questo salva anche nella tabella documento_index
//					boolean y = documentoWebManager.salvaFile(reportBytes, vo);  
//					//PK meglio usare ?? 
//					documentoWebManager.salvaSuFileSystem(inputStream, strFileName, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS);

                    BigDecimal idTarget = idDichiarazione;

                    String shaHex = null;
                    if (reportBytes != null)
                        shaHex = DigestUtils.shaHex(reportBytes);

                    it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoWebManager
                            .salvaInfoNodoIndexSuDbExl(
                                    nomeFile, idTarget, null, null,
                                    new BigDecimal(validazioneRendicontazioneDTO.getIdProgetto()),
                                    GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS,
                                    it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO.class,
                                    null, null, idUtente, shaHex, idTDocumentoIndex, newName);

//					it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoWebManager.salvaInfoNodoIndexSuDb( 
//							nomeFile,idTarget,null,null,new BigDecimal( validazioneRendicontazioneDTO.getIdProgetto()),
//							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS, it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO.class, 
//							null, null, idUtente,shaHex);

                    // OLD
//					PbandiTDocumentoIndexVO reportDettaglioDocSpesa = documentoManager.salvaInfoNodoIndexSuDb(node, nomeFile,
//							idTarget,null,null,new BigDecimal( validazioneRendicontazioneDTO.getIdProgetto()),
//							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS, 
//							PbandiTDichiarazioneSpesaVO.class, 
//							null, null, idUtente,shaHex);

                    esitoValidazioneRendicontazione
                            .setIdReportDettaglioDocSpesa(reportDettaglioDocSpesa.getIdDocumentoIndex().longValue());
                }
                // GIUGNO 2014, added report geneneration

                String tipoDichiarazioneCorrente = decodificheManager.findDescBreve(PbandiDTipoDichiarazSpesaVO.class,
                        dichiarazioneDiSpesaVO.getIdTipoDichiarazSpesa());
                logger.info("validazioneRendicontazioneDTO: tipoDichiarazioneCorrente :" + tipoDichiarazioneCorrente);

                String descrBreveTemplateNotifica = "";
                List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();

                if (tipoDichiarazioneCorrente
                        .equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA)
                        || tipoDichiarazioneCorrente
                                .equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE)) {
                    descrBreveTemplateNotifica = Notification.NOTIFICA_VALIDAZIONE_DI_SPESA_FINALE;
                    if (validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa() != null
                            && validazioneRendicontazioneDTO.getFlagRichiestaIntegrativa().equalsIgnoreCase("S")) {
                        logger.warn(
                                "\n\n\n############################ NEOFLUX unlock VALID_DICH_SPESA_FINALE ##############################\n");
                        neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                                Task.VALID_DICH_SPESA_FINALE);
                        logger.warn(
                                "############################ NEOFLUX unlock VALID_DICH_SPESA_FINALE ##############################\n\n\n\n");
                    } else {
                        logger.warn(
                                "\n\n\n############################ NEOFLUX end VALID_DICH_SPESA_FINALE ##############################\n");
                        neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                                Task.VALID_DICH_SPESA_FINALE);
                        logger.warn(
                                "############################ NEOFLUX end VALID_DICH_SPESA_FINALE ##############################\n\n\n\n");
                    }

                    MetaDataVO metadata1 = new MetaDataVO();
                    metadata1.setNome(Notification.DATA_CHIUSURA_VALIDAZIONE);
                    metadata1.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtChiusuraValidazione()));
                    metaDatas.add(metadata1);

                    MetaDataVO metadata2 = new MetaDataVO();
                    metadata2.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
                    metadata2.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtDichiarazione()));
                    metaDatas.add(metadata2);

                    MetaDataVO metadata3 = new MetaDataVO();
                    metadata3.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
                    metadata3.setValore(dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa().toString());
                    metaDatas.add(metadata3);
                } else {
                    descrBreveTemplateNotifica = Notification.NOTIFICA_VALIDAZIONE;
                    logger.warn(
                            "\n\n\n############################ NEOFLUX UNLOCK  VALID_DICH_SPESA ##############################\n");
                    neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(),
                            Task.VALID_DICH_SPESA);
                    logger.warn(
                            "############################ NEOFLUX UNLOCK VALID_DICH_SPESA ##############################\n\n\n\n");

                    MetaDataVO metadata1 = new MetaDataVO();
                    metadata1.setNome(Notification.DESC_DICHIARAZIONE_DI_SPESA);
                    metadata1.setValore(dichiarazioneDiSpesaVO.getNoteChiusuraValidazione());
                    metaDatas.add(metadata1);

                    MetaDataVO metadata2 = new MetaDataVO();
                    metadata2.setNome(Notification.DATA_CHIUSURA_VALIDAZIONE);
                    metadata2.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtChiusuraValidazione()));
                    metaDatas.add(metadata2);

                    MetaDataVO metadata3 = new MetaDataVO();
                    metadata3.setNome(Notification.DATA_DICHIARAZIONE_DI_SPESA);
                    metadata3.setValore(DateUtil.getDate(dichiarazioneDiSpesaVO.getDtDichiarazione()));
                    metaDatas.add(metadata3);

                    MetaDataVO metadata4 = new MetaDataVO();
                    metadata4.setNome(Notification.NUM_DICHIARAZIONE_DI_SPESA);
                    metadata4.setValore(dichiarazioneDiSpesaVO.getIdDichiarazioneSpesa().toString());
                    metaDatas.add(metadata4);
                }

                logger.info("validazioneRendicontazioneDTO: descrBreveTemplateNotifica :" + descrBreveTemplateNotifica);

                logger.info("calling genericDAO.callProcedure().putNotificationMetadata VALID_DICH_SPESA");
                genericDAO.callProcedure().putNotificationMetadata(metaDatas);

                logger.info("calling genericDAO.callProcedure().sendNotificationMessage VALID_DICH_SPESA");
                genericDAO.callProcedure().sendNotificationMessage(idProgetto, descrBreveTemplateNotifica,
                        Notification.BENEFICIARIO, idUtente);
            }

            esitoValidazioneRendicontazione.setEsito(Boolean.TRUE);
            esitoValidazioneRendicontazione.setMessage(MessaggiConstants.DICHIARAZIONE_CHIUSA);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            if (e instanceof ValidazioneRendicontazioneException) {
                throw (ValidazioneRendicontazioneException) e;
            } else {
                logger.error(e.getMessage(), e);
                throw new ValidazioneRendicontazioneException(e.getMessage(), e);
            }
        }

        return esitoValidazioneRendicontazione;
    }

    // Jira PBANDI-2768
    public Double findImportoValidatoSuVoceDiSpesa(Long idUtente, String identitaDigitale,
            Long idDocumentoDiSpesa, Long idProgetto) {
        return pbandiValidazioneRendicontazioneDAO.findImportoValidatoSuVoceDiSpesa(idDocumentoDiSpesa, idProgetto);
    }

    // Jira PBANDI-2768
    public Boolean validatoMaggioreAmmesso(Long idUtente, String identitaDigitale, Long idProgetto) {
        return pbandiValidazioneRendicontazioneDAO.validatoMaggioreAmmesso(idProgetto);
    }

    public IntegrazioneSpesaDTO[] findIntegrazioniSpesa(Long idUtente, String identitaDigitale,
            Long idDichiarazioneSpesa)
            throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {

        String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazioneSpesa" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDichiarazioneSpesa);

        PbandiTIntegrazioneSpesaVO filtro = new PbandiTIntegrazioneSpesaVO();
        filtro.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneSpesa));
        List<PbandiTIntegrazioneSpesaVO> listaVO = genericDAO.findListWhere(filtro);

        IntegrazioneSpesaDTO[] integrazioneSpesaDTO = new IntegrazioneSpesaDTO[listaVO.size()];
        beanUtil.valueCopy(listaVO.toArray(), integrazioneSpesaDTO);

        return integrazioneSpesaDTO;
    }

    public IntegrazioneSpesaDTO[] findIntegrazioniSpesaCFP(Long idUtente, String identitaDigitale, Long idCFP)
            throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {

        String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazioneSpesa" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idCFP);

        PbandiTComunicazFineProgVO pbandiTComunicazFineProgVO = new PbandiTComunicazFineProgVO();
        pbandiTComunicazFineProgVO.setIdComunicazFineProg(new BigDecimal(idCFP));
        pbandiTComunicazFineProgVO = genericDAO.findSingleWhere(pbandiTComunicazFineProgVO);
           
        PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
        pbandiTDichiarazioneSpesaVO.setIdProgetto(pbandiTComunicazFineProgVO.getIdProgetto());
        pbandiTDichiarazioneSpesaVO.setIdTipoDichiarazSpesa(new BigDecimal(3));
        List<PbandiTDichiarazioneSpesaVO> pbandiTDichiarazioneSpesaVOs = genericDAO.findListWhere(pbandiTDichiarazioneSpesaVO);

        if(pbandiTDichiarazioneSpesaVOs!=null && pbandiTDichiarazioneSpesaVOs.size()>0) {
            PbandiTIntegrazioneSpesaVO filtro = new PbandiTIntegrazioneSpesaVO();
            filtro.setIdDichiarazioneSpesa(pbandiTDichiarazioneSpesaVOs.get(0).getIdDichiarazioneSpesa());
            List<PbandiTIntegrazioneSpesaVO> listaVO = genericDAO.findListWhere(filtro);
            IntegrazioneSpesaDTO[] integrazioneSpesaDTO = new IntegrazioneSpesaDTO[listaVO.size()];
            beanUtil.valueCopy(listaVO.toArray(), integrazioneSpesaDTO);

            return integrazioneSpesaDTO;
        }
        return new IntegrazioneSpesaDTO[0];
    }

    public IntegrazioneSpesaDTO findIntegrazioneSpesaById(Long idUtente, String identitaDigitale,
            Long idIntegrazioneSpesa)
            throws CSIException, SystemException, UnrecoverableException, ValidazioneRendicontazioneException {

        String[] nameParameter = { "idUtente", "identitaDigitale", "idIntegrazioneSpesa" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idIntegrazioneSpesa);

        PbandiTIntegrazioneSpesaVO filtro = new PbandiTIntegrazioneSpesaVO();
        filtro.setIdIntegrazioneSpesa(new BigDecimal(idIntegrazioneSpesa));
        PbandiTIntegrazioneSpesaVO vo = genericDAO.findSingleWhere(filtro);

        IntegrazioneSpesaDTO integrazioneSpesaDTO = beanUtil.transform(vo, IntegrazioneSpesaDTO.class);
        return integrazioneSpesaDTO;
    }

    public EnteDestinatarioComunicazioniDTO findEnteDestinatarioComunicazioniByIdProgetto(Long idUtente,
            String identitaDigitale, Long idProgetto) throws CSIException, SystemException,
            UnrecoverableException, ValidazioneRendicontazioneException {

        String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
        ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);

        EnteDestinatarioComunicazioniVO vo = new EnteDestinatarioComunicazioniVO();
        vo.setIdProgetto(new BigDecimal(idProgetto));
        vo = genericDAO.findSingleOrNoneWhere(vo);

        EnteDestinatarioComunicazioniDTO dto = null;
        if (vo != null) {
            dto = beanUtil.transform(vo, EnteDestinatarioComunicazioniDTO.class);
        }
        return dto;
    }

}
