package it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DynamicTemplateManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
//import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.EsitoReportAppalti;
//import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoOperazioneDocumentoDiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoOperazioneElimina;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoReportDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.FiltroRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.ItemRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.MessaggioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.TaskDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.TipoDocumentoDiSPesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.VoceDiSpesaPadreDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentidispesa.GestioneDocumentiDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionevocidispesa.GestioneVociDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiFornitoriDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTipoDocumentoSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaPerControlloUnivocitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaSemplificazioneVO;
//import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaDaInviareVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoSoggettoPartnerVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VociDiSpesaPadriFigliContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.columnfilter.DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.ProgettoAssociatoDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoDocumentoDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.BetweenCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.OrCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagamentoDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionepagamenti.GestionePagamentiSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbweb.util.ErrorMessages;

public class GestioneDocumentiDiSpesaBusinessImpl extends BusinessImpl
		implements it.csi.pbandi.pbweb.pbandisrv.interfacecsi.GestioneDocumentiDiSpesaSrv {
	
	String SEPARATORE_SOGGETTO_PROGETTO_PARTNER = ";";

	private static final String STATO_PAGAMENTO_INSERITO="I";
	static BidiMap fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO = new TreeBidiMap();
	static {
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("idProgetto",
				"idProgetto");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idDocumentoDiSpesa", "idDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"noteValidazione", "noteValidazione");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idDocRiferimento", "idDocRiferimento");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idTipoDocumentoSpesa", "idTipoDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("idFornitore",
				"idFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idTipoFornitore", "idTipoFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"descTipoDocumentoSpesa", "descTipologiaDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"descBreveTipoDocSpesa", "descBreveTipoDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"numeroDocumento", "numeroDocumento");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"dtEmissioneDocumento", "dataDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("task", "task");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"denominazioneFornitore", "denominazioneFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("nomeFornitore",
				"nomeFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"cognomeFornitore", "cognomeFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"codiceFiscaleFornitore", "codiceFiscaleFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"partitaIvaFornitore", "partitaIvaFornitore");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"importoTotaleDocumento", "importoTotaleDocumentoIvato");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"importoRendicontazione", "importoRendicontabile");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"importoTotaleRendicontato", "importoTotaleRendicontato");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"progrFornitoreQualifica", "progrFornitoreQualifica");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("imponibile",
				"imponibile");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("importoIva",
				"importoIva");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"importoIvaCosto", "importoIvaACosto");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"importoTotaleQuietanzato", "importoTotaleQuietanzato");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idStatoDocumentoSpesa", "idStatoDocumentoSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("idSoggetto",
				"idSoggetto");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idTipoOggettoAttivita", "idTipoOggettoAttivita");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put("descDocumento",
				"descrizioneDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"rendicontabileQuietanzato", "rendicontabileQuietanzato");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"descBreveStatoDocSpesa", "descBreveStatoDocumentoSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"descStatoDocumentoSpesa", "descStatoDocumentoSpesa");
		// }L{ PBANDI-2338
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"dtEmissioneDocumento", "dataDocumentoDiSpesa");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"destinazioneTrasferta", "destinazioneTrasferta");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"durataTrasferta", "durataTrasferta");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"tipoInvio", "tipoInvio");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"numProtocollo", "numProtocollo");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"flagElettronico", "flagElettronico");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idAppalto", "idAppalto");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"descrizioneAppalto", "descrizioneAppalto");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"flagElettXml", "flagElettXml");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"idParametroCompenso", "idParametroCompenso");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"ggLavorabiliMese", "ggLavorabiliMese");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"sospBrevi", "sospBrevi");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"sospLungheGgTot", "sospLungheGgTot");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"sospLungheGgLav", "sospLungheGgLav");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"oreMeseLavorate", "oreMeseLavorate");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"mese", "mese");
		fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO.put(
				"anno", "anno");
	}

	static BidiMap fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO = new TreeBidiMap();
	static {
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("destinazioneTrasferta",
				"destinazioneTrasferta");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idStatoDocumentoSpesa",
				"idStatoDocumentoSpesa");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idTipoOggettoAttivita",
				"idTipoOggettoAttivita");
		/*
		 * Solo in caso di CEDOLINO
		 */
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"progrFornitoreQualifica", "progrFornitoreQualifica");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("imponibile",
				"imponibile");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"importoTotaleDocumentoIvato", "importoTotaleDocumento");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idFornitore",
				"idFornitore");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("dataDocumentoDiSpesa",
				"dtEmissioneDocumento");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idDocRiferimento",
				"idDocRiferimento");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("numeroDocumento",
				"numeroDocumento");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"idTipoDocumentoDiSpesa", "idTipoDocumentoSpesa");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idSoggetto",
				"idSoggetto");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("importoIvaACosto",
				"importoIvaCosto");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("durataTrasferta",
				"durataTrasferta");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"descrizioneDocumentoDiSpesa", "descDocumento");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("importoIva",
				"importoIva");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idDocumentoDiSpesa",
				"idDocumentoDiSpesa");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("idSoggetto",
				"idSoggetto");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("tipoInvio",
				"tipoInvio");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put("numProtocollo",
				"numProtocollo");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"flagElettronico", "flagElettronico");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"flagElettXml", "flagElettXml");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"idParametroCompenso", "idParametroCompenso");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"ggLavorabiliMese", "ggLavorabiliMese");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"sospBrevi", "sospBrevi");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"sospLungheGgTot", "sospLungheGgTot");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"sospLungheGgLav", "sospLungheGgLav");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"oreMeseLavorate", "oreMeseLavorate");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"mese", "mese");
		fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO.put(
				"anno", "anno");
		
	}

	@Autowired
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	
	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	
	@Autowired
	private PbandiFornitoriDAOImpl pbandiFornitoriDAO;
	
	@Autowired
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	
	@Autowired
	private SoggettoManager soggettoManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private ContoEconomicoDAO contoEconomicoDAO;
	
	@Autowired
	private ProfilazioneDAO profilazioneDAO;
	
	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private ConfigurationManager configurationManager;
	
	@Autowired
	private DocumentoManager documentoManager;
	
	private DynamicTemplateManager dynamicTemplateManager;
	/*
	 * � Se l�Attore ha chiesto i documenti di spesa gestiti nel progetto allora
	 * il Sistema considera solo i documenti di spesa associati allo stesso e
	 * aventi, quindi, una relazione con il progetto. �Il Sistema pone come non
	 * selezionabili i documenti di spesa aventi stato diverso da INSERITO o NON
	 * VALIDATO.
	 */

	/*
	 * [Interfaccia dei risultati di ricerca dei documenti di spesa]Il Sistema
	 * popola la tabella con i risultati di ricerca in base alla selezione della
	 * rendicontazione:� capofila � il sistema presenta l�elenco dei documenti
	 * contabili appartenenti al soggetto capofila che si � loggato al sistema e
	 * sta rendicontando. La colonna partner pu� non essere valorizzata:
	 * contiene la denominazione del capofila.� tutti � il sistema presenta
	 * l�elenco dei documenti contabili appartenenti sia la soggetto capofila
	 * che si � loggato al sistema e sta rendicontando sia i documenti contabili
	 * di tutti i partners. La colonna "partner" � valorizzato con la
	 * denominazione del proprietario del documento contabile (capofila o
	 * partner) � partners � il sistema presenta l�elenco dei documenti
	 * contabili appartenenti al soggetto partner selezionato dalla DropList. La
	 * colonna partner pu� non essere valorizzata: contiene la denominazione del
	 * partner selezionato dalla lista.
	 */

	public DynamicTemplateManager getDynamicTemplateManager() {
		return dynamicTemplateManager;
	}

	public void setDynamicTemplateManager(DynamicTemplateManager dynamicTemplateManager) {
		this.dynamicTemplateManager = dynamicTemplateManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}

	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}

	public PbandiFornitoriDAOImpl getPbandiFornitoriDAO() {
		return pbandiFornitoriDAO;
	}

	public void setPbandiFornitoriDAO(PbandiFornitoriDAOImpl pbandifornitoriDAO) {
		this.pbandiFornitoriDAO = pbandifornitoriDAO;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setDocumentoDiSpesaManager(
			DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO[] findPartners(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentidispesa.GestioneDocumentiDiSpesaException

	{
		List<PartnerDTO> partnerDTO = new ArrayList<PartnerDTO>();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			partnerDTO = doFindPartners(idProgetto);
		} finally {
			logger.end();
		}
		return partnerDTO
				.toArray(new it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO[partnerDTO
						.size()]);
	}

	private List<PartnerDTO> doFindPartners(java.lang.Long idProgetto) {
		List<PartnerDTO> result = null;

		// }L{ PBANDI-750: rifatta la ricerca con il genericDAO
		// }L{ PBANDI-1124: rifatta la logica di ricerca [5/1/2011]
		PbandiTProgettoVO progettoMasterVO = new PbandiTProgettoVO();
		progettoMasterVO.setIdProgetto(new BigDecimal(idProgetto));
		progettoMasterVO.setFlagProgettoMaster(Constants.FLAG_TRUE);
		List<ProgettoSoggettoPartnerVO> listaVO = new ArrayList<ProgettoSoggettoPartnerVO>();
		// verifico che ci sia il flag_progetto_master
		if (genericDAO.count(progettoMasterVO) == 1) {
			ProgettoSoggettoPartnerVO vo = beanUtil.transform(progettoMasterVO,
					ProgettoSoggettoPartnerVO.class);
			vo.setAscendentOrder("descSoggettoPartner");
			listaVO = genericDAO.findListWhere(vo);
		}

		try {
			Map<String, String> beanMap = new HashMap<String, String>();
			beanMap.put("idSoggettoPartner", "idSoggetto");
			beanMap.put("idProgettoPartner", "idProgettoPartner");
			beanMap.put("descSoggettoPartner", "descrizione");
			result = beanUtil
					.transformList(
							listaVO,
							it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO.class,
							beanMap);
		} catch (Exception e) {
			logger.error("Errore durante la copia dei " + listaVO.size()
					+ " elementi trovati nel DTO", e);
		}
		return result;
	}

	

	private boolean isReferenziatoInNoteDiCredito(Long idDocumentoDiSpesa,
			Long idProgetto) {
		logger.begin();
		try {

			List<DocumentoDiSpesaProgettoVO> noteDiCredito = documentoDiSpesaManager
					.findNoteCreditoFattura(idProgetto, idDocumentoDiSpesa);
			if (!isEmpty(noteDiCredito)) {
				logger.debug("il documento di tipo fattura con id "
						+ idDocumentoDiSpesa
						+ " � referenziato in altri documenti di spesa di tipo Nota Di Credito");
				return true;
			}

		} finally {
			logger.end();
		}
		return false;
	}

	private boolean isNotaDiCredito(Long idTipoDocumentoDiSpesa)
			throws GestioneDocumentiDiSpesaException {
		logger.begin();
		try {
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoDiSpesa);
			if (!isNull(decodifica)) {
				logger.debug("documento di spesa con idTipo "
						+ idTipoDocumentoDiSpesa + " � di tipo:"
						+ decodifica.getDescrizioneBreve());
				if (decodifica
						.getDescrizioneBreve()
						.equalsIgnoreCase(
								DocumentoDiSpesaManager
										.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO))) {
					logger.debug("documento di spesa � una FATTURA ");
					return true;
				} else {
					logger.debug("documento di spesa NON � una FATTURA ");
					return false;
				}
			} else {
				logger.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
				throw new GestioneDocumentiDiSpesaException(
						"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
								+ idTipoDocumentoDiSpesa);
			}
		} finally {
			logger.end();
		}
	}

	private boolean isFattura(Long idTipoDocumentoDiSpesa)
			throws GestioneDocumentiDiSpesaException {
		logger.begin();
		try {
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoDiSpesa);
			if (!isNull(decodifica)
					&& !isEmpty(decodifica.getDescrizioneBreve())) {
				logger.debug("documento di spesa con idTipo "
						+ idTipoDocumentoDiSpesa + " � di tipo:"
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
					logger.debug("documento di spesa � una FATTURA ");
					return true;
				} else {
					logger.debug("documento di spesa NON � una FATTURA ");
					return false;
				}
			} else {
				logger.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
				throw new GestioneDocumentiDiSpesaException(
						"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
								+ idTipoDocumentoDiSpesa);
			}

		} finally {
			logger.end();
		}

	}

	private void cancellaDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		logger.begin();
		try {
			boolean delete = pbandiDocumentiDiSpesaDAO
					.cancellaDocumentoDiSpesa(idDocumentoDiSpesa);
			logger.debug("documentoDiSpesa cancellato ----> " + delete);
		} finally {
			logger.end();
		}
	}

	private void cancellaAssociazioniDocDiSpesaConVociDispesa(
			Long idDocumentoDiSpesa, Long idProgetto) {
		logger.begin();
		try {
			boolean delete = pbandiDocumentiDiSpesaDAO
					.cancellaAssociazioniDocDiSpesaConVociDispesa(
							idDocumentoDiSpesa, idProgetto);
			logger.debug("ssociazioniDocDiSpesaConVociDispesa cancellato ----> "
					+ delete);
		} finally {
			logger.end();
		}
	}

	private void cancellaAssociazioneDocDiSpesaProgetto(
			Long idDocumentoDiSpesa, Long idProgetto) {

		logger.begin();

		try {
			boolean delete = pbandiDocumentiDiSpesaDAO
					.cancellaAssociazioneDocumentoDiSpesaProgetto(
							idDocumentoDiSpesa, idProgetto);
			logger.debug("associazioneDocDiSpesaProgetto cancellato ----> "
					+ delete);
		} finally {
			logger.end();
		}

	}

	private boolean hasPagamentiAssociati(Long idDocumentoDiSpesa,
			Long idProgetto) {
		logger.begin();
		try {
			java.util.List<PagamentoQuotePartiVO> list = pbandipagamentiDAO
					.findPagamentiAssociati(idDocumentoDiSpesa, idProgetto);
			if (!isEmpty(list))
				return true;
			// PBANDI_R_PAGAMENTO_DOC_SPESA,
			// ID_PAGAMENTO
			// ID_DOCUMENTO_DI_SPESA
		} finally {
			logger.end();
		}
		return false;

	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO[] findDocumentiDiSpesaPerBeneficiario(
			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idBeneficiario

	)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentidispesa.GestioneDocumentiDiSpesaException

	{
		logger.begin();
		try {

		} finally {
			logger.end();
		}
		return null;
	}

 

	private boolean checkEsistonoPagamentiFattura(Long idDocumentoDiSpesa) {
		PbandiRPagamentoDocSpesaVO filterVO = new PbandiRPagamentoDocSpesaVO();
		filterVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(idDocumentoDiSpesa));

		List<PbandiRPagamentoDocSpesaVO> pagamenti = genericDAO
				.findListWhere(filterVO);
		if (ObjectUtil.isEmpty(pagamenti))
			return false;
		else
			return true;

	}

	private boolean checkRendicontabileNotaCredito(
			DocumentoDiSpesaDTO notaDiCredito) {
		/*
		 * Ricerco il totale rendicontabile del documento di spesa di
		 * riferimento
		 */
		DocumentoDiSpesaProgettoVO docRiferimentoVO = new DocumentoDiSpesaProgettoVO();
		docRiferimentoVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(notaDiCredito.getIdDocRiferimento()));
		docRiferimentoVO.setIdProgetto(NumberUtil.toBigDecimal(notaDiCredito
				.getIdProgetto()));
		docRiferimentoVO = genericDAO.findSingleWhere(docRiferimentoVO);

		/*
		 * Ricerco l'importo rendicontabile delle note di credito associate al
		 * documento escludento la nota di credito corrente
		 */
		DocumentoDiSpesaProgettoVO filtroNoteCreditoVO = new DocumentoDiSpesaProgettoVO();
		filtroNoteCreditoVO.setIdDocRiferimento(NumberUtil
				.toBigDecimal(notaDiCredito.getIdDocRiferimento()));
		filtroNoteCreditoVO.setIdProgetto(NumberUtil.toBigDecimal(notaDiCredito
				.getIdProgetto()));

		DocumentoDiSpesaProgettoVO filtroNotaCorrenteVO = new DocumentoDiSpesaProgettoVO();
		filtroNotaCorrenteVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(notaDiCredito.getIdDocumentoDiSpesa()));
		NotCondition<DocumentoDiSpesaProgettoVO> notCondition = new NotCondition<DocumentoDiSpesaProgettoVO>(
				new FilterCondition<DocumentoDiSpesaProgettoVO>(
						filtroNotaCorrenteVO));

		Condition<DocumentoDiSpesaProgettoVO> condition = new AndCondition<DocumentoDiSpesaProgettoVO>(
				new FilterCondition<DocumentoDiSpesaProgettoVO>(
						filtroNoteCreditoVO), notCondition);

		List<DocumentoDiSpesaProgettoVO> noteDiCreditoVO = genericDAO
				.findListWhere(condition);
		BigDecimal totaleRendicontabileNoteCredito = new BigDecimal(0);
		for (DocumentoDiSpesaProgettoVO nc : noteDiCreditoVO) {
			totaleRendicontabileNoteCredito = NumberUtil.sum(
					totaleRendicontabileNoteCredito,
					nc.getImportoRendicontazione());
		}

		BigDecimal totaleRendicontabileNetto = NumberUtil.subtract(
				docRiferimentoVO.getImportoRendicontazione(),
				totaleRendicontabileNoteCredito);
		BigDecimal impotoRendicontabileNotaCredito = BeanUtil
				.transformToBigDecimal(notaDiCredito.getImportoRendicontabile());

		if (NumberUtil.compare(impotoRendicontabileNotaCredito,
				totaleRendicontabileNetto) > 0) {
			return false;
		} else {
			return true;
		}

	}

	private boolean checkTotaleRendicontabileSuperioreRendicontabileMassimoDocumento(
			DocumentoDiSpesaDTO documentoDiSpesa) {

		/*
		 * FIX PBandi-2314. Poiche' le note di credito possono essere associate
		 * a piu' progetti e devo considerare solamente gli importi relativi
		 * alla nota di credito devo eliminare le eventuali note duplicate
		 */
		List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager
				.eliminaNoteCreditoDuplicate(documentoDiSpesaManager
						.findNoteCreditoFattura(null,
								documentoDiSpesa.getIdDocumentoDiSpesa()));

		PbandiRDocSpesaProgettoVO filterVO = new PbandiRDocSpesaProgettoVO();
		filterVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(documentoDiSpesa
				.getIdDocumentoDiSpesa()));

		/*
		 * Calcolo il totale delle note di credito associate al documento
		 */
		BigDecimal totaleNoteCredito = new BigDecimal(0);
		for (DocumentoDiSpesaProgettoVO nota : noteCredito) {
			BigDecimal imponibileNota = nota.getImponibile() == null ? new BigDecimal(
					0) : nota.getImponibile();
			BigDecimal ivaacostoNota = nota.getImportoIvaCosto() == null ? new BigDecimal(
					0) : nota.getImportoIvaCosto();
			totaleNoteCredito = NumberUtil.sum(totaleNoteCredito,
					NumberUtil.sum(imponibileNota, ivaacostoNota));
		}

		BigDecimal imponibileFattura = documentoDiSpesa.getImponibile() == null ? new BigDecimal(
				0) : new BigDecimal(documentoDiSpesa.getImponibile());
		BigDecimal ivaacostoFattura = documentoDiSpesa.getImportoIvaACosto() == null ? new BigDecimal(
				0) : new BigDecimal(documentoDiSpesa.getImportoIvaACosto());
		BigDecimal rendicontabileMassimoFattura = NumberUtil.sum(
				imponibileFattura, ivaacostoFattura);
		rendicontabileMassimoFattura = NumberUtil.subtract(
				rendicontabileMassimoFattura, totaleNoteCredito);

		PbandiRDocSpesaProgettoVO notFilter = new PbandiRDocSpesaProgettoVO();
		notFilter.setIdProgetto(NumberUtil.toBigDecimal(documentoDiSpesa
				.getIdProgetto()));

		List<PbandiRDocSpesaProgettoVO> documentoAssociato = genericDAO
				.findListWhere(new AndCondition<PbandiRDocSpesaProgettoVO>(
						Condition.filterBy(filterVO), Condition.not(Condition
								.filterBy(notFilter))));

		BigDecimal totaleRendicontabile = new BigDecimal(0);
		for (PbandiRDocSpesaProgettoVO docprj : documentoAssociato) {
			totaleRendicontabile = NumberUtil.sum(totaleRendicontabile,
					docprj.getImportoRendicontazione());
		}

		/*
		 * Aggiungo al totale rendicontabile per il documento per gli altri
		 * progetti l' importo rendicontabile per il documento-progetto corrente
		 */
		totaleRendicontabile = NumberUtil.sum(
				new BigDecimal(documentoDiSpesa.getImportoRendicontabile()),
				totaleRendicontabile);

		if (NumberUtil.compare(rendicontabileMassimoFattura,
				totaleRendicontabile) < 0) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.
	 * GestioneDocumentiDiSpesaSrv
	 * #isDocumentoDiSpesaAssociatoProgetto(java.lang.Long, java.lang.String,
	 * java.lang.Long, java.lang.Long)
	 */
	public Boolean isDocumentoDiSpesaAssociatoProgetto(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		logger.begin();
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa, idProgetto);
			Boolean result = isDocumentoDiSpesaAssociatoProgetto(
					idDocumentoDiSpesa, idProgetto);
			return result;

		} catch (Exception e) {
			throw new GestioneDocumentiDiSpesaException(
					"Errore durante isDocumentoDiSpesaAssociatoProgetto", e);
		} finally {
			logger.end();
		}
	}

	public TaskDTO[] findTask(Long idUtente, String identitaDigitale,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneDocumentiDiSpesaException {
		logger.begin();
		it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.TaskDTO[] taskDTO = null;
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			// java.util.List<PartnerVO> listaVO = new ArrayList<PartnerVO>();
			// listaVO = pbandiDocumentiDiSpesaDAO.findPartners(idProgetto);
			// }L{ PBANDI-750: rifatta la ricerca con il generiDAO
			java.util.List<PbandiRDocSpesaProgettoVO> listaVO = new ArrayList<PbandiRDocSpesaProgettoVO>();

			PbandiRDocSpesaProgettoVO vo = new PbandiRDocSpesaProgettoVO();
			vo.setIdProgetto(new BigDecimal(idProgetto));
			listaVO = genericDAO.findListWhere(vo);
			if (listaVO != null || !listaVO.isEmpty()) {
				taskDTO = new it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.TaskDTO[listaVO
						.size()];

				logger.debug("rimappo i VO della persistence in DTO per il client");

				Map<String, String> beanMap = new HashMap<String, String>();
				beanMap.put("task", "codice");
				beanMap.put("task", "descrizione");
				try {
					getBeanUtil()
							.valueCopy(listaVO.toArray(), taskDTO, beanMap);
				} catch (Exception e) {
					logger.error(
							"Errore durante la copia dei " + listaVO.size()
									+ " elementi trovati nel DTO", e);
				}

				logger.debug("restituisco i DTO per il client");
			}
		}

		finally {
			logger.end();
		}
		return taskDTO;

	}

 

	/**
	 * Regole di validazione per l'inserimento
	 * 
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	private boolean checkEsisteFornitore(DocumentoDiSpesaDTO dto)
			throws ManagerException {
		logger.begin();
		try {
			/*
			 * DecodificaDTO decodifica = decodificheManager.findDecodifica(
			 * GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA, dto
			 * .getIdTipoDocumentoDiSpesa());
			 */
			boolean isCedolino = documentoDiSpesaManager
					.isCedolinoOAutodichiarazioneSoci(dto
							.getIdTipoDocumentoDiSpesa());
			String[] codQualificheDaEscludere = null;
			if (isCedolino)
				codQualificheDaEscludere = new String[] { "NN" };

			/*
			 * if (decodifica .getDescrizioneBreve() .equals(
			 * DocumentoDiSpesaManager
			 * .getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv
			 * .TIPO_DOCUMENTO_DI_SPESA_CEDOLINO))) { isCedolino = true; }
			 */

			List<Map> fornitori = pbandiDocumentiDiSpesaDAO.findFornitore(
					dto.getCodiceFiscaleFornitore(), isCedolino,
					codQualificheDaEscludere);
			if (fornitori != null && fornitori.size() > 0)
				return true;
			else
				return false;

		} finally {
			logger.end();
		}
	}

	/**
	 * Verifica che il documento di spesa sia unico
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkDocumentoDiSpesaUnico(DocumentoDiSpesaDTO dto) {
		logger.begin();
		try {
			HashMap<String, String> trsMap = new HashMap<String, String>();
			trsMap.put("numeroDocumento", "numeroDocumento");
			trsMap.put("dataDocumentoDiSpesa", "dtEmissioneDocumento");
			trsMap.put("codiceFiscaleFornitore", "codiceFiscaleFornitore");
			// trsMap.put("idDocumentoDiSpesa","idDocumentoDiSpesa");
			// tnt
			trsMap.put("idFornitore", "idFornitore");
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
		} finally {
			logger.end();
		}
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
	 * Verifica che l' importo iva a costo sia minore o uguale all' importo
	 * dell' imposta
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkImportoIvaACosto(DocumentoDiSpesaDTO dto) {
		logger.begin();
		try {
			double importoIvaACosto = dto.getImportoIvaACosto() == null ? 0
					: dto.getImportoIvaACosto();
			double importoIva = dto.getImportoIva() == null ? 0 : dto
					.getImportoIva();
			if (NumberUtil.toRoundDouble(importoIvaACosto) <= NumberUtil
					.toRoundDouble(importoIva))
				return true;
			else
				return false;
		} finally {
			logger.end();
		}
	}

	/**
	 * Verifica che l' importo rendicontabile sia minore o uguale alla somma
	 * (importo imponibile + importo iva a costo)
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkImportoRendicontabile(DocumentoDiSpesaDTO dto) {
		boolean isBandoCultura = false;
		//Controllo per non fare la somma nel caso in cui si tratti di un Bando Cultura
		if(dto.getImponibile() == null) {
			isBandoCultura = true;
		}
		double importoRendicontabile = dto.getImportoRendicontabile() == null ? 0
				: dto.getImportoRendicontabile();
		double imponibile = dto.getImponibile() == null ? 0 : dto
				.getImponibile();
		double importoIvaACosto = dto.getImportoIvaACosto() == null ? 0 : dto
				.getImportoIvaACosto();
		
		if(isBandoCultura == true) {
			return true;
		}
		else if (NumberUtil.toRoundDouble(importoRendicontabile) <= NumberUtil
				.toRoundDouble((imponibile + importoIvaACosto)))
			return true;
		else
			return false;

	}

	/**
	 * Verifica la conguita dei dati. Nel caso in cui il tipo di documento e'
	 * CEDOLINO devo verificare che: - se il bando segue la regola BR01 allora
	 * per il fornitore devono essere stati indicati il costoRisorsa ed il
	 * monteOre
	 * 
	 * @param dto
	 * @return
	 * @throws FormalParameterException
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	private boolean checkCongruitaDati(DocumentoDiSpesaDTO dto)
			throws FormalParameterException, ManagerException {
	 
			/*
			 * DecodificaDTO decodifica = decodificheManager.findDecodifica(
			 * GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA, dto
			 * .getIdTipoDocumentoDiSpesa());
			 */
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
	 * Verifica che la somma degli importi rendicontabili sui vari progetti sia
	 * <= al massimo rendicontabile (imponibile + importo iva a costo) del
	 * documento di spesa
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkTotalaRendicontabile(DocumentoDiSpesaDTO dto) {
		/**
		 * Nel calcolo della somma degli importi rendicontabili, non considero
		 * quelli relativi al progetto in oggetto, poiche' l' utente li sta
		 * modificando.
		 */
			List<DocumentoDiSpesaVO> documentiVO = pbandiDocumentiDiSpesaDAO
					.findImportiDocumentoDiSpesaProgetto(
							dto.getIdDocumentoDiSpesa(), null);
			double imponibile = dto.getImponibile() == null ? 0 : dto
					.getImponibile();
			double importoIvaACosto = dto.getImportoIvaACosto() == null ? 0
					: dto.getImportoIvaACosto();
			double maxRendicontabile = imponibile + importoIvaACosto;
			double importoRendicontabile = dto.getImportoRendicontabile() == null ? 0
					: dto.getImportoRendicontabile();
			double totaleRendicontabile = 0.0;
			for (DocumentoDiSpesaVO vo : documentiVO) {
				if (!vo.getIdProgetto().equals(dto.getIdProgetto()))
					totaleRendicontabile += vo.getImportoRendicontabile();
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
			double importoRendicontabile = dto.getImportoRendicontabile() == null ? 0
					: dto.getImportoRendicontabile();
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
	 * Verifica se il tipo di documento di spesa e' NOTA_DI_CREDITO
	 * 
	 * @param dto
	 * @return
	 */
	private boolean isNotaCredito(DocumentoDiSpesaDTO dto) {
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					dto.getIdTipoDocumentoDiSpesa());
			if (decodifica
					.getDescrizioneBreve()
					.equals(DocumentoDiSpesaManager
							.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO)))
				return true;
			else
				return false;
	}

	/**
	 * Verifica se il documento di spesa e' associato al progetto.
	 * 
	 * @param idDocumentoDiSpesa
	 * @param idProgetto
	 * @return
	 * @throws Exception
	 */
	private boolean isDocumentoDiSpesaAssociatoProgetto(
			Long idDocumentoDiSpesa, Long idProgetto) {
		try {
			PbandiRDocSpesaProgettoVO filter = new PbandiRDocSpesaProgettoVO();
			filter.setIdDocumentoDiSpesa(NumberUtil
					.toBigDecimal(idDocumentoDiSpesa));
			filter.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			PbandiRDocSpesaProgettoVO vo = genericDAO.findSingleWhere(filter);
			return true;
		} catch (RecordNotFoundException e) {
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

	/**
	 * Verifica che l'importo del documento di spesa al netto delle note di
	 * credito sia maggiore o uguale all'importo del totale dei pagamenti
	 * 
	 */

	private boolean checkImportoDocumentoDiSpesaMaggioreImportoTotalePagamenti(
			DocumentoDiSpesaDTO dto) throws Exception {
			double importoTotaleDocumentoIvato = NumberUtil.getDoubleValue(dto
					.getImportoTotaleDocumentoIvato());
			/*
			 * FIX PBandi-433. Nel caso del cedolino l' importo da controllare
			 * e' dato dal massimo tra l'importo totale del documento e l'
			 * importo rendicontabile
			 */
			if (documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(dto
					.getIdTipoDocumentoDiSpesa())) {
				if (NumberUtil.toRoundDouble(dto.getImportoRendicontabile()) > NumberUtil
						.toRoundDouble(importoTotaleDocumentoIvato))
					importoTotaleDocumentoIvato = NumberUtil.getDoubleValue(dto
							.getImportoRendicontabile());
			}

			Double totaleDocumentoNetto = importoTotaleDocumentoIvato
					- NumberUtil.getDoubleValue(getSommaImportiNoteDiCredito(
							dto.getIdDocumentoDiSpesa(), dto));
			if (NumberUtil.toRoundDouble(totaleDocumentoNetto) >= NumberUtil
					.toRoundDouble(getSommaImportiPagamenti(dto
							.getIdDocumentoDiSpesa())))
				return true;
			else
				return false;
		 
	}

	private Double getSommaImportiNoteDiCredito(
			java.lang.Long idDocumentoDiSpesa, DocumentoDiSpesaDTO dto) {
		Double importoNoteDiCredito = 0.0;
		List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> noteDiCredito = pbandiDocumentiDiSpesaDAO
				.findNoteDiCreditoAssociateFattura(idDocumentoDiSpesa);
		Long idDocumentoCorrente = dto.getIdDocumentoDiSpesa();
		// L.P SE SONO IN INS AGGIUNGO L'IMPORTO NUOVO ALLE NOTE DI CREDITO GIA
		// INSERITE
		if (idDocumentoCorrente == null) {
			importoNoteDiCredito = importoNoteDiCredito
					+ dto.getImportoTotaleDocumentoIvato();
		}
		// ///////////////////////////////
		if (!isEmpty(noteDiCredito)) {
			for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO notaDiCredito : noteDiCredito) {
				if (notaDiCredito.getIdDocumentoDiSpesa().equals(
						idDocumentoCorrente))
					notaDiCredito.setImportoTotaleDocumento(dto
							.getImportoTotaleDocumentoIvato());
				if (!isNull(notaDiCredito.getImportoTotaleDocumento()))
					importoNoteDiCredito += notaDiCredito
							.getImportoTotaleDocumento().doubleValue();
			}
		}
		return importoNoteDiCredito;
	}

	private double getSommaImportiPagamenti(java.lang.Long idDocumentoDiSpesa) {
		Double importoPagamenti = 0.0;
		List<PbandiTPagamentoVO> pagamenti = pbandiDocumentiDiSpesaDAO
				.findPagamentiAssociatiADocumento(idDocumentoDiSpesa);
		if (!isEmpty(pagamenti)) {
			for (it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO pagamento : pagamenti) {
				if (!isNull(pagamento.getImportoPagamento()))
					importoPagamenti += pagamento.getImportoPagamento()
							.doubleValue();
			}
		}
		return NumberUtil.toRoundDouble(importoPagamenti.doubleValue());
	}

	public TipoDocumentoDiSPesaDTO[] findTipologieDocumentiDiSpesa(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			/*
			 * Recupero il progressivo bando linea del progetto
			 */
			BandoLineaProgettoVO bandoLineaVO = getProgettoManager()
					.findBandoLineaForProgetto(idProgetto);

			BandoLineaTipoDocumentoSpesaVO filtro = new BandoLineaTipoDocumentoSpesaVO();
			filtro.setProgrBandoLineaIntervento(bandoLineaVO
					.getProgrBandoLineaIntervento());
			filtro.setAscendentOrder("descTipoDocumentoSpesa");

			TipoDocumentoDiSpesaVO groupByVO = new TipoDocumentoDiSpesaVO();

			List<TipoDocumentoDiSpesaVO> listTipoDocumentoDiSpesa = genericDAO
					.findListWhereGroupBy(
							new FilterCondition<BandoLineaTipoDocumentoSpesaVO>(
									filtro), groupByVO);
			return beanUtil.transform(listTipoDocumentoDiSpesa,
					TipoDocumentoDiSPesaDTO.class);
 
	}

	public VoceDiSpesaPadreDTO[] findAllVociDiSpesaPadreConFigli(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			/*
			 * Ricerco il contoeconomico master del progetto
			 */
			BigDecimal idContoMaster = null;
			try {
				idContoMaster = contoEconomicoManager
						.getIdContoMaster(NumberUtil.toBigDecimal(idProgetto));
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error(
						"Errore durante la ricerca del conto economico master per il progetto["
								+ idProgetto + "]", e);
				throw new GestioneVociDiSpesaException(
						"Errore durante la ricerca del conto economico master per il progetto["
								+ idProgetto + "]", e);
			}

			VociDiSpesaPadriFigliContoEconomicoVO query = new VociDiSpesaPadriFigliContoEconomicoVO();
			query.setIdContoEconomico(idContoMaster);
			query.setAscendentOrder("descVoceDiSpesaPadre", "descVoceDiSpesa");

			List<VociDiSpesaPadriFigliContoEconomicoVO> voci = genericDAO
					.where(query).select();
			Map<BigDecimal, VoceDiSpesaPadreDTO> padri = new LinkedHashMap<BigDecimal, VoceDiSpesaPadreDTO>();
			Map<BigDecimal, List<CodiceDescrizioneDTO>> figli = new HashMap<BigDecimal, List<CodiceDescrizioneDTO>>();
			for (VociDiSpesaPadriFigliContoEconomicoVO vds : voci) {
				final BigDecimal idVoceDiSpesaPadre = vds
						.getIdVoceDiSpesaPadre();
				if (idVoceDiSpesaPadre == null) {
					final VoceDiSpesaPadreDTO temp = beanUtil.transform(vds,
							VoceDiSpesaPadreDTO.class,
							new HashMap<String, String>() {
								{
									put("idVoceDiSpesa", "idVoceDiSpesa");
									put("descVoceDiSpesa", "descVoceDiSpesa");
								}
							});
					padri.put(vds.getIdVoceDiSpesaPadre(), temp);
				} else if (padri.get(idVoceDiSpesaPadre) == null) {
					final VoceDiSpesaPadreDTO temp = beanUtil.transform(vds,
							VoceDiSpesaPadreDTO.class,
							new HashMap<String, String>() {
								{
									put("idVoceDiSpesaPadre", "idVoceDiSpesa");
									put("descVoceDiSpesaPadre",
											"descVoceDiSpesa");
								}
							});
					padri.put(vds.getIdVoceDiSpesaPadre(), temp);
				} else {
					List<CodiceDescrizioneDTO> list = ObjectUtil.nvl(
							figli.get(idVoceDiSpesaPadre),
							new ArrayList<CodiceDescrizioneDTO>());
					CodiceDescrizioneDTO temp = beanUtil.transform(vds,
							CodiceDescrizioneDTO.class,
							new HashMap<String, String>() {
								{
									put("idVoceDiSpesa", "codice");
									put("descVoceDiSpesa", "descrizione");
								}
							});
					list.add(temp);
					figli.put(idVoceDiSpesaPadre, list);
				}
			}

			final Collection<VoceDiSpesaPadreDTO> result = padri.values();
			for (VoceDiSpesaPadreDTO padre : result) {
				List<CodiceDescrizioneDTO> vdsFiglie = figli.get(beanUtil
						.transform(padre.getIdVoceDiSpesa(), BigDecimal.class));
				if (vdsFiglie != null) {
					padre.setVociDiSpesaFiglie(vdsFiglie
							.toArray(new CodiceDescrizioneDTO[0]));
				}
			}

			return result.toArray(new VoceDiSpesaPadreDTO[0]);

	}
	public VoceDiSpesaPadreDTO[] findAllVociDiSpesaPadreConFigliCultura(Long idUtente,
																															 String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);

		/*
		 * Ricerco il contoeconomico master del progetto
		 */
		BigDecimal idContoMaster = null;
		try {
			idContoMaster = contoEconomicoManager
					.getIdContoMaster(NumberUtil.toBigDecimal(idProgetto));
		} catch (ContoEconomicoNonTrovatoException e) {
			logger.error(
					"Errore durante la ricerca del conto economico master per il progetto["
							+ idProgetto + "]", e);
			throw new GestioneVociDiSpesaException(
					"Errore durante la ricerca del conto economico master per il progetto["
							+ idProgetto + "]", e);
		}

		VociDiSpesaPadriFigliContoEconomicoVO query = new VociDiSpesaPadriFigliContoEconomicoVO();
		query.setIdContoEconomico(idContoMaster);
		query.setAscendentOrder("descVoceDiSpesaPadre", "descVoceDiSpesa");

		List<VociDiSpesaPadriFigliContoEconomicoVO> voci = genericDAO
				.where(query).select();
		Map<BigDecimal, VoceDiSpesaPadreDTO> padri = new LinkedHashMap<BigDecimal, VoceDiSpesaPadreDTO>();
		Map<BigDecimal, List<CodiceDescrizioneDTO>> figli = new HashMap<BigDecimal, List<CodiceDescrizioneDTO>>();
		for (VociDiSpesaPadriFigliContoEconomicoVO vds : voci) {
			final BigDecimal idVoceDiSpesaPadre = vds
					.getIdVoceDiSpesaPadre();
			if (idVoceDiSpesaPadre == null) {
				final VoceDiSpesaPadreDTO temp = beanUtil.transform(vds,
						VoceDiSpesaPadreDTO.class,
						new HashMap<String, String>() {
							{
								put("idVoceDiSpesa", "idVoceDiSpesa");
								put("descVoceDiSpesa", "descVoceDiSpesa");
							}
						});
				padri.put(vds.getIdVoceDiSpesaPadre(), temp);
			} else if (padri.get(idVoceDiSpesaPadre) == null) {
				final VoceDiSpesaPadreDTO temp = beanUtil.transform(vds,
						VoceDiSpesaPadreDTO.class,
						new HashMap<String, String>() {
							{
								put("idVoceDiSpesaPadre", "idVoceDiSpesa");
								put("descVoceDiSpesaPadre",
										"descVoceDiSpesa");
							}
						});
				padri.put(vds.getIdVoceDiSpesaPadre(), temp);

				List<CodiceDescrizioneDTO> list = ObjectUtil.nvl(
						figli.get(idVoceDiSpesaPadre),
						new ArrayList<CodiceDescrizioneDTO>());
				CodiceDescrizioneDTO temp2 = beanUtil.transform(vds,
						CodiceDescrizioneDTO.class,
						new HashMap<String, String>() {
							{
								put("idVoceDiSpesa", "codice");
								put("descVoceDiSpesa", "descrizione");
							}
						});
				System.out.println("1398 temp da else finale[" + temp + "]");
				list.add(temp2);
				figli.put(idVoceDiSpesaPadre, list);
			} else {
				List<CodiceDescrizioneDTO> list = ObjectUtil.nvl(
						figli.get(idVoceDiSpesaPadre),
						new ArrayList<CodiceDescrizioneDTO>());
				CodiceDescrizioneDTO temp = beanUtil.transform(vds,
						CodiceDescrizioneDTO.class,
						new HashMap<String, String>() {
							{
								put("idVoceDiSpesa", "codice");
								put("descVoceDiSpesa", "descrizione");
							}
						});
				list.add(temp);
				figli.put(idVoceDiSpesaPadre, list);
			}
		}

		final Collection<VoceDiSpesaPadreDTO> result = padri.values();
		for (VoceDiSpesaPadreDTO padre : result) {
			List<CodiceDescrizioneDTO> vdsFiglie = figli.get(beanUtil
					.transform(padre.getIdVoceDiSpesa(), BigDecimal.class));
			if (vdsFiglie != null) {
				padre.setVociDiSpesaFiglie(vdsFiglie
						.toArray(new CodiceDescrizioneDTO[0]));
			}
		}

		return result.toArray(new VoceDiSpesaPadreDTO[0]);

	}

	public PartnerDTO[] findPartnersPerVisualizzaContoEconomico(Long idUtente,
			String identitaDigitale, Long idProgettoPadre) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		List<PartnerDTO> partnerDTO = new ArrayList<PartnerDTO>();
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgettoPadre" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgettoPadre);

		// }L{ PBANDI-750: rifatta la ricerca con il genericDAO
		// }L{ PBANDI-1124: rifatta la logica di ricerca [5/1/2011]
		PbandiTProgettoVO progettoMasterVO = new PbandiTProgettoVO();
		progettoMasterVO.setIdProgetto(new BigDecimal(idProgettoPadre));
		progettoMasterVO.setFlagProgettoMaster(Constants.FLAG_TRUE);
		List<ProgettoSoggettoPartnerVO> listaVO = new ArrayList<ProgettoSoggettoPartnerVO>();
		// verifico che ci sia il flag_progetto_master
		if (genericDAO.count(progettoMasterVO) == 1) {
			ProgettoSoggettoPartnerVO vo = beanUtil.transform(progettoMasterVO,
					ProgettoSoggettoPartnerVO.class);
			vo.setAscendentOrder("descSoggettoPartner");
			listaVO = genericDAO.findListWhere(vo);
		}

		try {
			Map<String, String> beanMap = new HashMap<String, String>();
			beanMap.put("idProgettoPartner", "idProgettoPartner");
			beanMap.put("descSoggettoPartner", "descrizione");
			partnerDTO = beanUtil
					.transformList(
							listaVO,
							it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO.class,
							beanMap);
		} catch (Exception e) {
			logger.error("Errore durante la copia dei " + listaVO.size()
					+ " elementi trovati nel DTO", e);
		}
		return partnerDTO
				.toArray(new it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO[partnerDTO
						.size()]);
	}

	public ItemRicercaDocumentiSpesa[] findDocumentiDiSpesaSemplificazione(
			Long idUtente, String identitaDigitale,
			FiltroRicercaDocumentiSpesa filtro) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {

			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, filtro);

			if (StringUtil.isEmpty(filtro.getNumero())) {
				filtro.setNumero(null);
			}

			Map<String, String> trsMap = new HashMap<String, String>() {
				{
					put("idFornitore", "idFornitore");
					put("idProgetto", "idProgetto");
					put("idSoggettoBeneficiario", "idSoggetto");
					put("idStato", "idStatoDocumentoSpesa");
					put("idTipologia", "idTipoDocumentoDiSpesa");
					put("idVoceDiSpesa", "idVoceDiSpesa");
					put("numero", "numeroDocumento");
					put("idCategoria", "idCategoria");
				}
			};

			DocumentoDiSpesaSemplificazioneVO filtroVO = beanUtil.transform(
					filtro, DocumentoDiSpesaSemplificazioneVO.class, trsMap);

			/*
			 *   se sul client si seleziona stato doppio (da
			 * completare/dichiarabile) passo uno 0, annullo il filtro e lo
			 * gestisco pi� sotto con una or condition
			 */
			if (filtro.getIdStato() != null
					&& filtro.getIdStato() == 0L) {
				filtroVO.setIdStatoDocumentoSpesa(null);
			}
			//  

			Boolean isGestitiNelProgetto = SELETTORE_RICERCA_DOCUMENTI_GESTITI_NEL_PROGETTO
					.equals(filtro.getDocumentiDiSpesa());
			final String rendicontazione = filtro.getRendicontazione();
			Boolean isRicercaPerPartner = !SELETTORE_RICERCA_RENDICONTAZIONE_CAPOFILA
					.equals(rendicontazione);
			final int indexOfSeparator = rendicontazione
					.indexOf(SEPARATORE_SOGGETTO_PROGETTO_PARTNER);
			final BigDecimal idProgettoBeneficiario = filtroVO.getIdProgetto();
			final BigDecimal idSoggettoBeneficiario = filtroVO.getIdSoggetto();

			// START doFind
			// inizializzo per sicurezza
			Condition<DocumentoDiSpesaSemplificazioneVO> filterCondition = new FilterCondition<>(filtroVO);

			/*
			 * Ordinamento
			 */
			filtroVO.setAscendentOrder("descTipoDocumentoDiSpesa",
					"dataDocumentoDiSpesa",
					"codiceFiscaleFornitore",
					"idDocumentoDiSpesa");

			/*
			 * Documenti gestiti nel progetto
			 */
			if (isGestitiNelProgetto) {
				filtroVO.setFlagGestInProgetto(Constants.FLAG_TRUE);
				filterCondition = new FilterCondition<DocumentoDiSpesaSemplificazioneVO>(
						filtroVO);

				/*
				 * Condizione per la ricerca dei partner
				 */
				if (isRicercaPerPartner) {
					filtroVO.setIdProgetto(null);
					filtroVO.setIdSoggetto(null);

					boolean isRicercaPerTuttiPartners = SELETTORE_RICERCA_RENDICONTAZIONE_TUTTI_I_PARTNERS
							.equals(rendicontazione);
					if (isRicercaPerTuttiPartners) {
						List<PartnerDTO> partners = doFindPartners(filtro
								.getIdProgetto());
						List<DocumentoDiSpesaSemplificazioneVO> partnersVO = beanUtil
								.transformList(
										partners,
										DocumentoDiSpesaSemplificazioneVO.class,
										new HashMap<String, String>() {
											{
												put("idSoggetto", "idSoggetto");
												put("idProgettoPartner","idProgetto");
											}
										});
						filterCondition = new AndCondition(
								Condition.filterBy(filtroVO),
								Condition.filterBy(partnersVO));
					} else {
						if (indexOfSeparator > -1) {
							BigDecimal idSoggettoPartner = beanUtil
									.transform(rendicontazione.substring(0,
											indexOfSeparator), BigDecimal.class);
							BigDecimal idProgettoPartner = beanUtil.transform(
									rendicontazione.substring(
											indexOfSeparator + 1,
											rendicontazione.length()),
									BigDecimal.class);
							filtroVO.setIdProgetto(idProgettoPartner);
							filtroVO.setIdSoggetto(idSoggettoPartner);
							filterCondition = new FilterCondition<DocumentoDiSpesaSemplificazioneVO>(
									filtroVO);
						}
					}
				}
			} else {
				filterCondition = new FilterCondition<DocumentoDiSpesaSemplificazioneVO>(
						filtroVO);
			}

			//   se sul client si seleziona stato doppio (da
			// completare/dichiarabile) passo uno 0

			if (filtro.getIdStato() != null
					&& filtro.getIdStato().longValue() == 0l) {
				DocumentoDiSpesaSemplificazioneVO daCompletare = new DocumentoDiSpesaSemplificazioneVO();
				DocumentoDiSpesaSemplificazioneVO dichiarabile = new DocumentoDiSpesaSemplificazioneVO();
				daCompletare.setIdStatoDocumentoSpesa(new BigDecimal(8));
				dichiarabile.setIdStatoDocumentoSpesa(new BigDecimal(1));
				Condition<DocumentoDiSpesaSemplificazioneVO> statiOrCondition = new OrCondition<DocumentoDiSpesaSemplificazioneVO>(
						Condition.filterBy(daCompletare),
						Condition.filterBy(dichiarabile));

				filterCondition = new AndCondition<DocumentoDiSpesaSemplificazioneVO>(
						filterCondition, statiOrCondition);// between condition
															// mettere qua la
															// data?

			}
			DocumentoDiSpesaSemplificazioneVO dataDa = new DocumentoDiSpesaSemplificazioneVO();
			dataDa.setDataDocumentoDiSpesa(DateUtil.utilToSqlDate(filtro
					.getData()));
			DocumentoDiSpesaSemplificazioneVO dataA = new DocumentoDiSpesaSemplificazioneVO();
			dataA.setDataDocumentoDiSpesa(DateUtil.utilToSqlDate(filtro
					.getDataA()));
			BetweenCondition<DocumentoDiSpesaSemplificazioneVO> conditionDate = new BetweenCondition<DocumentoDiSpesaSemplificazioneVO>(
					dataDa, dataA);
			filterCondition = new AndCondition<DocumentoDiSpesaSemplificazioneVO>(
					filterCondition, conditionDate);

			//  

			List<DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO> result = null;
			List<DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO> filteredResult = new ArrayList<DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO>();

			result = genericDAO.findListWhereDistinct(filterCondition,
					DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO.class);

			/*
			 * Controllo che il numero di record restituiti non superi il limite
			 * definito nella tabella PBandi_C_Costanti dalla proprieta'
			 * maxNumDocumentiRicerca
			 */
			Long maxNumDocumenti = 0L;

			try {
				maxNumDocumenti = configurationManager
						.getCurrentConfiguration().getMaxNumDocumentiRicerca();
			} catch (Exception e) {
				logger.error(
						"Errore nella lettura della configurazione del ConfigurationManager.",
						e);
			}

			if (maxNumDocumenti > 0 && result.size() > maxNumDocumenti) {
				throw new GestioneDocumentiDiSpesaException(
						ErrorMessages.ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI);
			}

			/*
			 * FIX: PBandi-316 Recupero i codici di tutti i progetti legati ad
			 * ogni documento di spesa
			 */

			BigDecimal idTipoDocumentoNotaCredito = new BigDecimal(
					decodificheManager.findDecodifica(
							GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
							DocumentoDiSpesaManager.DESC_BREVE_NOTA_CREDITO)
							.getId());
			BigDecimal idStatoDocumentoDichiarabile = new BigDecimal(
					decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE)
							.getId());
			BigDecimal idStatoDocumentoInValidazione = new BigDecimal(
					decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE)
							.getId());

			BigDecimal idStatoDocumentoDaCompletare = new BigDecimal(
					decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE)
							.getId());

			boolean isBr03 = regolaManager.isRegolaApplicabileForProgetto(
					filtro.getIdProgetto(),
					RegoleConstants.BR03_RILASCIO_SPESE_QUIETANZATE);

			boolean isBr40 = regolaManager.isRegolaApplicabileForProgetto(
					filtro.getIdProgetto(),
					RegoleConstants.BR40_ABILITAZIONE_ASSOCIA_DOCUMENTO);

			/*
			 * Utilizzo la query del genericDAO
			 */
			if (!ObjectUtil.isEmpty(result)) {
				BigDecimal idDocumentoPrec = null;
				DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO documentoPrec = null;
				boolean isProcessato = false;
				for (DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO documentoVO : result) {
					if(documentoVO.getFornitoreTabella()!=null){
						String fornitoreTabella = documentoVO.getFornitoreTabella();
						if(fornitoreTabella.contains("PBAN")){
							fornitoreTabella=fornitoreTabella.substring(0, fornitoreTabella.indexOf("PBAN"));
							fornitoreTabella+=" n.d - Fornitore estero";
							documentoVO.setFornitoreTabella(fornitoreTabella);
						}
					}
						
					if (idDocumentoPrec == null) {
						idDocumentoPrec = documentoVO.getIdDocumentoDiSpesa();
						documentoPrec = documentoVO;
					}
					if (idDocumentoPrec.compareTo(documentoVO
							.getIdDocumentoDiSpesa()) == 0) {
						if (documentoVO.getFlagGestInProgetto().equals(Constants.FLAG_TRUE)) {
							documentoPrec.setImportoTotaleValidato(documentoVO.getImportoTotaleValidato());
						}

						if (!isProcessato) {
							isProcessato = true;
							/*
							 * Processo il documento per ricercare gli eventuali
							 * progetti associati e verificare se il documento
							 * e' associabile, modificabile, cancellabile e
							 * clonabile.
							 */
							processaDocumentoPerDatiAggregati(documentoPrec,
									filtro.getTask(),
									idTipoDocumentoNotaCredito,
									idSoggettoBeneficiario,
									idProgettoBeneficiario,
									idStatoDocumentoDichiarabile,
									idStatoDocumentoInValidazione,
									idStatoDocumentoDaCompletare, isBr03,
									isBr40);
						}
					} else {
						/*
						 * Documento corrente diverso dal documento precedente.
						 */
						filteredResult.add(documentoPrec);
						// isProcessato = false;
						idDocumentoPrec = documentoVO.getIdDocumentoDiSpesa();
						documentoPrec = documentoVO;
						if (documentoVO.getFlagGestInProgetto().equals(
								Constants.FLAG_TRUE)) {
							documentoPrec.setImportoTotaleValidato(documentoVO
									.getImportoTotaleValidato());
						}
						processaDocumentoPerDatiAggregati(documentoPrec,
								filtro.getTask(), idTipoDocumentoNotaCredito,
								idSoggettoBeneficiario, idProgettoBeneficiario,
								idStatoDocumentoDichiarabile,
								idStatoDocumentoInValidazione,
								idStatoDocumentoDaCompletare, isBr03, isBr40);

					}
				}

				/*
				 * Aggiungo l'ultimo documento della lista se non e' stato
				 * processato
				 */
				if (!isProcessato) {
					processaDocumentoPerDatiAggregati(documentoPrec,
							filtro.getTask(), idTipoDocumentoNotaCredito,
							idSoggettoBeneficiario, idProgettoBeneficiario,
							idStatoDocumentoDichiarabile,
							idStatoDocumentoInValidazione,
							idStatoDocumentoDaCompletare, isBr03, isBr40);
				}
				filteredResult.add(documentoPrec);
			}

			// END doFind

			List<ItemRicercaDocumentiSpesa> items = beanUtil.transformList(
					filteredResult, ItemRicercaDocumentiSpesa.class,
					new HashMap<String, String>() {
						{
							put("codiceProgetto", "progetto");
							put("idProgetto", "idProgetto");
							put("idDocumentoDiSpesa", "idDocumento");
							put("importiTotaliDocumentiIvati", "importi");
							put("importoTotaleDocumentoIvato", "importo");
							put("importoTotaleValidato", "validato");
							put("fornitoreTabella", "fornitore");
							put("descStatoDocumentoSpesa", "stato");
							put("estremiTabella", "estremi");
							put("descTipoDocumentoDiSpesa", "tipologia");
							put("flagModificabile", "isModificabile");
							put("flagClonabile", "isClonabile");
							put("flagEliminabile", "isEliminabile");
							put("flagAssociabile", "isAssociabile");
							put("flagAssociato", "isAssociato");
							put("tipoInvio", "tipoInvio");
							put("flagAllegati", "isAllegatiPresenti");
						}
					});

			return items.toArray(new ItemRicercaDocumentiSpesa[items.size()]);
		 
	}

	public CodiceDescrizioneDTO[] findFornitoriRendicontazione(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			FornitoreProgettoVO query = new FornitoreProgettoVO();
			query.setIdProgetto(new BigDecimal(idProgetto));
			query.setAscendentOrder(true);
			query.setAscendentOrder("fornitoreTabella");

			List<FornitoreProgettoVO> fornitori = genericDAO
					.findListWhereDistinct(
							Condition
									.filterBy(query)
									.and(Condition
											.validOnly(FornitoreProgettoVO.class)),
							FornitoreProgettoVO.class);
			for (FornitoreProgettoVO f : fornitori) {
				if(f.getCodiceFiscaleFornitore().startsWith("PBAN")){
					f.setCodiceFiscaleFornitore("n.d.");
				}
				if(f.getDenominazioneFornitore()!=null){
					f.setFornitoreTabella(f.getDenominazioneFornitore()+" - "+f.getCodiceFiscaleFornitore());
				} else{
					f.setFornitoreTabella(f.getCognomeFornitore()+" "+f.getNomeFornitore()+" - "+f.getCodiceFiscaleFornitore());
				}
			}
			 
			return beanUtil.transform(fornitori, CodiceDescrizioneDTO.class,
					new HashMap<String, String>() {
						{
							put("idFornitore", "codice");
							put("fornitoreTabella", "descrizione");
						}
					});
		 
	}

	public DocumentoDiSpesaDTO findDettaglioDocumentoDiSpesaSemplificazione(Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa, Long idProgetto) throws CSIException, SystemException, UnrecoverableException, GestioneDocumentiDiSpesaException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "idDocumentoDiSpesa", "idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDocumentoDiSpesa, idProgetto);

		DocumentoDiSpesaProgettoVO filterVO = new DocumentoDiSpesaProgettoVO();
		filterVO.setIdDocumentoDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa));
		filterVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		DocumentoDiSpesaProgettoVO documento = null;

		try {
			documento = genericDAO.findSingleWhere(filterVO);
		} catch (Exception e) {
			logger.error("Errore durante la ricerca del dettaglio del documento di spesa.", e);
			throw new GestioneDocumentiDiSpesaException("Errore durante la ricerca del dettaglio del documento di spesa.", e);
		}

		DocumentoDiSpesaDTO result = beanUtil.transform(documento, DocumentoDiSpesaDTO.class, fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO);

		if (documento.getProgrFornitoreQualifica() != null) {
			/*
			 * Recupero il costo orario
			 */
			PbandiRFornitoreQualificaVO filterQualificaVO = new PbandiRFornitoreQualificaVO();
			filterQualificaVO.setProgrFornitoreQualifica(documento.getProgrFornitoreQualifica());
			try {
				PbandiRFornitoreQualificaVO qualificaFornitore = genericDAO.findSingleWhere(filterQualificaVO);
				result.setCostoOrario(NumberUtil.toDouble(qualificaFornitore.getCostoOrario()));
			} catch (Exception e) {
				logger.error("Errore durante la ricerca della qualificaFornitore del documento di spesa.", e);
				throw new GestioneDocumentiDiSpesaException("Errore durante la ricerca della qualificaFornitore del documento di spesa.", e);
			}
		}

		return result;
	}

	public DocumentoDiSpesaDTO[] findFattureFornitoreSemplificazione(
			Long idUtente, String identitaDigitale, Long idFornitore,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneDocumentiDiSpesaException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idFornitore", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idFornitore, idProgetto);
			/*
			 * Si ricercano i documenti di spesa di tipo: - fattura - fattura di
			 * leasing - fattura fideiussoria
			 */
			DocumentoDiSpesaProgettoVO filtroFattura = new DocumentoDiSpesaProgettoVO();
			filtroFattura
					.setDescBreveTipoDocSpesa(Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA);

			DocumentoDiSpesaProgettoVO filtroFatturaFideiussoria = new DocumentoDiSpesaProgettoVO();
			filtroFatturaFideiussoria
					.setDescBreveTipoDocSpesa(Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA);

			DocumentoDiSpesaProgettoVO filtroFatturaLeasing = new DocumentoDiSpesaProgettoVO();
			filtroFatturaLeasing
					.setDescBreveTipoDocSpesa(Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING);

			DocumentoDiSpesaProgettoVO filtro = new DocumentoDiSpesaProgettoVO();
			filtro.setIdFornitore(NumberUtil.toBigDecimal(idFornitore));
			filtro.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));

			List<DocumentoDiSpesaProgettoVO> filtriTipologiaDocumento = new ArrayList<DocumentoDiSpesaProgettoVO>();
			filtriTipologiaDocumento.add(filtroFatturaLeasing);
			filtriTipologiaDocumento.add(filtroFatturaFideiussoria);
			filtriTipologiaDocumento.add(filtroFattura);

			List<DocumentoDiSpesaProgettoVO> elencoFatture = genericDAO
					.findListWhere(Condition.filterBy(filtro).and(
							Condition.filterBy(filtriTipologiaDocumento)));

			return beanUtil.transform(elencoFatture, DocumentoDiSpesaDTO.class,
					fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO);

		 
	}

	public DocumentoDiSpesaDTO[] findNoteCreditoFattura(Long idUtente,
			String identitaDigitale, Long idFattura) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idFattura" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idFattura);

			List<DocumentoDiSpesaProgettoVO> elencoNoteDiCredito = documentoDiSpesaManager
					.findNoteCreditoFattura(null, idFattura);
			return beanUtil.transform(elencoNoteDiCredito,
					DocumentoDiSpesaDTO.class,
					fromDocumentoDiSpesaProgettoVO2DocumentoDiSpesaDTO);

		 
	}

	public EsitoOperazioneDocumentoDiSpesa salvaDocumentoDiSpesa(Long idUtente,
			String identitaDigitale, DocumentoDiSpesaDTO documentoDiSpesa, Long progrBandoLinea)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException, DaoException {
	 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"documentoDiSpesa", "documentoDiSpesa.idProgetto",
					"documentoDiSpesa.idSoggetto",
					"documentoDiSpesa.idTipoDocumentoSpesa" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, documentoDiSpesa,
					documentoDiSpesa.getIdProgetto(),
					documentoDiSpesa.getIdSoggetto(),
					documentoDiSpesa.getIdTipoDocumentoDiSpesa());

			ValidatorInput.verifyAtLeastOneNotNullValue(documentoDiSpesa);
			EsitoOperazioneDocumentoDiSpesa result = new EsitoOperazioneDocumentoDiSpesa();
			result.setEsito(Boolean.FALSE);
			List<MessaggioDTO> messaggi = new ArrayList<MessaggioDTO>();
			boolean hasError = false;

			/*
			 * Controllo che data del documento non sia superiore alla data
			 * odierna
			 */
			if (!checkData(documentoDiSpesa)) {
				hasError = true;
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_OGGI);
				messaggi.add(msg);
			}

			/*
			 * PBANDI-2278 Se il tipo di documento non e' SPESE FORFETTARIE
			 * allora verifico l' univocita' del documento
			 */
			if ( !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesa
					.getDescBreveTipoDocumentoDiSpesa()) &&
					!documentoDiSpesaManager.isSpeseForfettarie(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isCompensoImpresaArtigiana(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa()) 
					&&!documentoDiSpesaManager.isCompensoSoggettoGestore(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa()) 
					&& !checkDocumentoDiSpesaUnico(documentoDiSpesa)) {
				hasError = true;
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_GIA_INSERITO);
				messaggi.add(msg);
			}

			/*
			 * Se il documento non e' di tipo QUOTA DI AMMORTAMENTO
			 */
			if (!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(
					Constants.TIPO_DOCUMENTO_DI_SPESA_QUOTA_AMMORTAMENTO)) {
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
										.getDtCostituzione())
								|| DateUtil.utilToSqlDate(
										documentoDiSpesa
												.getDataDocumentoDiSpesa())
										.equals(enteGiuridicoSoggetto
												.getDtCostituzione())) {
							hasError = true;
							MessaggioDTO msg = new MessaggioDTO();
							msg.setMsgKey(ErrorMessages.WARNING_DOCUMENTO_DI_SPESA_DATA_DOCUMENTO_INFERIORE_DATA_COSTITUZIONE);
							messaggi.add(msg);
						}
					} else {
						/*
						 * Verifico che la data del documento sia superiore alla
						 * data di presentazione della domanda
						 */
						if (!checkDataDocumento(documentoDiSpesa)) {
							hasError = true;
							MessaggioDTO msg = new MessaggioDTO();
							msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA);
							messaggi.add(msg);
						}
					}

				} else {
					/*
					 * Verifico che la data del documento sia superiore alla
					 * data di presentazione della domanda
					 */
					if (!checkDataDocumento(documentoDiSpesa)) {
						hasError = true;
						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA);
						messaggi.add(msg);
					}
				}

			}

			/*
			 * Se il documento e' una nota di credito allora verificare che
			 * l'importo rendicontabile inserito per una nota di credito sia
			 * minore o uguale al rendicontabile netto (ovvero corretto gi� da
			 * eventuali altri rendicontabili di note di credito gi� esistenti
			 * per il documento di riferimento).
			 */
			if (documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(
					Constants.TIPO_DOCUMENTO_DI_SPESA_NOTA_CREDITO)) {
				/*
				 * FIX PBANDI-2314. Non si puo' inseririre o modificare una nota
				 * di credito se per la fattura di riferimento sono stati gia'
				 * inseriti dei pagamenti.
				 */

				if (checkEsistonoPagamentiFattura(documentoDiSpesa
						.getIdDocRiferimento())) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO_NON_ASSOCIABILE_PER_PAGAMENTI_PRESENTI);
					messaggi.add(msg);
				}

				/*
				 * FIX PBANDI-2314 Verifico che il totale (impobibile e iva a
				 * costo) della fattura alla quale si riferisce la nota di
				 * credito, al netto delle note di credito, non sia minore del
				 * totale dei rendicontabili
				 */
				if (checkNotaCreditoTotaleRendicontabileFatturaSuperioreRendicontabileMassimo(documentoDiSpesa)) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTI_DI_SPESA_TOTALE_FATTURA_MINORE_TOTALE_RENDICONTABILI);
					messaggi.add(msg);

				}

				if (!documentoDiSpesaManager.checkRendicontabileNotaCredito(
						documentoDiSpesa.getIdDocumentoDiSpesa(),
						documentoDiSpesa.getIdDocRiferimento(),
						documentoDiSpesa.getIdProgetto(),
						documentoDiSpesa.getImportoRendicontabile())) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_NOTA_CREDITO);
					messaggi.add(msg);
				}
			}

			/*
			 * FIX PBANDI-2314: Nel caso di fattura devo verificare che il
			 * totale (imponibile + iva a costo) del documento al netto delle
			 * note di credito sia maggiore o uguale al totale dei
			 * rendicontabili
			 */
			if ((documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(
					Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA)
					|| documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa()
							.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA) || documentoDiSpesa
					.getDescBreveTipoDocumentoDiSpesa().equals(
							Constants.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING))
					&& documentoDiSpesa.getIdDocumentoDiSpesa() != null) {
				if (checkTotaleRendicontabileSuperioreRendicontabileMassimoDocumento(documentoDiSpesa)) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					
					Boolean isBR65 = profilazioneDAO.isRegolaApplicabileForBandoLinea(progrBandoLinea, "BR65", idUtente, identitaDigitale);
					ContoEconomicoDTO contoEconomico = contoEconomicoDAO.trovaContoEconomico(documentoDiSpesa.getIdProgetto());
					
					if(isBR65!= null && isBR65.equals(Boolean.TRUE) && contoEconomico.getImportoSpesaAmmessa() != null && contoEconomico.getImportoSpesaAmmessa().compareTo(new Double(5000000)) <= 0) {
					//non ho l'iva a costo / indetraibile
						msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTI_DI_SPESA_TOTALE_DOCUMENTO_MINORE_TOTALE_RENDICONTABILI_NO_IVA_COSTO);
					} else {
							msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTI_DI_SPESA_TOTALE_DOCUMENTO_MINORE_TOTALE_RENDICONTABILI);
					}				
					messaggi.add(msg);
				}
			}

			/*
			 * Verifico che l'importo iva a costo non sia superiore all' iva
			 * solo se il documento non e' CEDOLINO o AUTODICHIARAZIONE SOCI,
			 * SPESE FORFETTARIE, DOCUMENTO GENERICO
			 */
			if (!documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(
					Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO)
					&& !documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa()
							.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO)
					&& !documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa()
							.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCIE)
					&&  !documentoDiSpesaManager.isSpeseForfettarie(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(Constants.TIPO_DOCUMENTO_DI_SPESA_DOCUMENTO_GENERICO)
					&& !documentoDiSpesaManager.isCompensoImpresaArtigiana(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isCompensoSoggettoGestore(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa())) {
				if (!checkImportoIvaACosto(documentoDiSpesa)) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_IVA_A_COSTO_SUPERIORE);
					messaggi.add(msg);
				}
			}

			/*
			 * Se il documento non e' SPESE FORFETTARIE o DOCUMENTO GENERICO verifico che l' importo
			 * rendicontabile non sia superiore della somma tra importo
			 * imponibile ed importo iva a costo.
			 */
			if (!documentoDiSpesaManager.isSpeseForfettarie(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) &&
					!documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa())
							&& !documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa().equals(Constants.TIPO_DOCUMENTO_DI_SPESA_DOCUMENTO_GENERICO)
							&& !documentoDiSpesaManager.isCompensoImpresaArtigiana(documentoDiSpesa
									.getDescBreveTipoDocumentoDiSpesa())
							&& !documentoDiSpesaManager.isCompensoSoggettoGestore(documentoDiSpesa
									.getDescBreveTipoDocumentoDiSpesa())) {

				if (!checkImportoRendicontabile(documentoDiSpesa)) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					if (documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()
							.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO)
							|| documentoDiSpesa
									.getDescBreveTipoDocumentoDiSpesa()
									.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO)) {
						msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_CEDOLINO_O_AUTODICHIARAZIONE_SOCI_IMPORTO_RENDICONTABILE_SUPERIORE);
					} else {
						
						Boolean isBR65 = profilazioneDAO.isRegolaApplicabileForBandoLinea(progrBandoLinea, "BR65", idUtente, identitaDigitale);
						ContoEconomicoDTO contoEconomico = contoEconomicoDAO.trovaContoEconomico(documentoDiSpesa.getIdProgetto());
						
						if(isBR65!= null && isBR65.equals(Boolean.TRUE) && contoEconomico.getImportoSpesaAmmessa() != null && contoEconomico.getImportoSpesaAmmessa().compareTo(new Double(5000000)) <= 0) {
						//non ho l'iva a costo / indetraibile
							msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE_NO_IVA_COSTO);
						} else {
							msg.setMsgKey(ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE);
						}	
					}
					messaggi.add(msg);
				}
			}

			/*
			 * Se sono in modifica verifico che l' importo documento di spesa
			 * <=(importo totale pagamenti) Controllo valido solo in modifica
			 * documenti di spesa L.P JIRA PBANDI-430
			 */
			try {
				if (!ObjectUtil
						.isNull(documentoDiSpesa.getIdDocumentoDiSpesa())
						&& !checkImportoDocumentoDiSpesaMaggioreImportoTotalePagamenti(documentoDiSpesa)) {

					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.WARNING_DOCUMENTO_DI_SPESA_TOTALE_PAGAMENTI_MAGGIORE_TOTALE_DOCUMENTO);
					messaggi.add(msg);

				}
			} catch (Exception e1) {
				logger.error("errore", e1);
				hasError = true;
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_SERVER);
				messaggi.add(msg);
			}

			/*
			 * Se sono in modifica ed il documento non e' di tipo SPESE
			 * FORFETTARIE Verifico che il totale delle quote parte non sia
			 * superiore al rendicontabile
			 */
			if (!ObjectUtil.isNull(documentoDiSpesa.getIdDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isSpeseForfettarie(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isCompensoImpresaArtigiana(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())
					&& !documentoDiSpesaManager.isCompensoSoggettoGestore(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())
					&& !checkTotaleQuotaParte(documentoDiSpesa)) {
				hasError = true;
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.WARNING_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE);
				messaggi.add(msg);
			}

			/*
			 * Controllo da eseguire solo in caso di MODIFICA DOCUMENTO DI SPESA
			 * Se il documento di spesa e' diverso da CEDOLINO e CEDOLINO
			 * CO.CO.PRO allora verifico che la somma degli importi
			 * rendicontabile del documento di spesa associato ai progetti non
			 * sia superiore al massimo rendicontabile ( imponibile + importo
			 * iva a costo)
			 */
			/*
			 * FIX PBandi-2378. Questa segnalazione indica che 
			 * il controllo si deve fare anche per il CEDOLINO e CEDOLINO_COCOPRO
			 *
			if (documentoDiSpesa.getIdDocumentoDiSpesa() != null
					&& !documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()
							.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO)
					&& !documentoDiSpesa
							.getDescBreveTipoDocumentoDiSpesa()
							.equals(Constants.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO)
							) {
			*/
			if (documentoDiSpesa.getIdDocumentoDiSpesa() != null) {
				if (!documentoDiSpesaManager
						.checkMassimRendicontabileDocumentoProgetti(
								documentoDiSpesa.getIdDocumentoDiSpesa(),
								documentoDiSpesa.getIdProgetto(),
								documentoDiSpesa.getImponibile(),
								documentoDiSpesa.getImportoIvaACosto(),
								documentoDiSpesa.getImportoRendicontabile())) {
					hasError = true;
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE);
					messaggi.add(msg);
				}
			}

			if (!hasError) {

				PbandiTDocumentoDiSpesaVO documentoVO = beanUtil.transform(
						documentoDiSpesa, PbandiTDocumentoDiSpesaVO.class,
						fromDocumentoDiSpesaDTO2DocumentoDiSpesaVO);

				if (documentoDiSpesa.getIdDocumentoDiSpesa() == null) {
					/*
					 * Inserimento
					 */
					DecodificaDTO decodificaStato = decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
									Constants.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE);
					documentoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));

					try {
						documentoVO = genericDAO.insert(documentoVO);

						PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
						pbandiRDocSpesaProgettoVO.setIdProgetto(BigDecimal.valueOf(documentoDiSpesa.getIdProgetto()));
						pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(documentoVO.getIdDocumentoDiSpesa());
						pbandiRDocSpesaProgettoVO.setTask(documentoDiSpesa.getTask());
						pbandiRDocSpesaProgettoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));

						pbandiRDocSpesaProgettoVO
								.setImportoRendicontazione(NumberUtil
										.createScaledBigDecimal(documentoDiSpesa
												.getImportoRendicontabile()));
						pbandiRDocSpesaProgettoVO
								.setIdStatoDocumentoSpesa(NumberUtil
										.toBigDecimal(decodificaStato.getId()));
						pbandiRDocSpesaProgettoVO.setTipoInvio(documentoDiSpesa.getTipoInvio());
						if (documentoDiSpesa.getIdAppalto() != null)
							pbandiRDocSpesaProgettoVO.setIdAppalto(new BigDecimal(documentoDiSpesa.getIdAppalto()));

						genericDAO.insert(pbandiRDocSpesaProgettoVO);

						result.setIdDocumentoDiSpesa(documentoVO.getIdDocumentoDiSpesa().longValue());

						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
						messaggi.add(msg);
						result.setEsito(Boolean.TRUE);
						
						if(documentoDiSpesaManager.isCedolinoCostiStandard(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) || 
								documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) ||
								documentoDiSpesaManager.isAutocertificazioneSpese(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) || 
								documentoDiSpesaManager.isSALSenzaQuietanza(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) ||
								documentoDiSpesaManager.isCompensoMensileTirocinante(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) ||
								documentoDiSpesaManager.isCompensoImpresaArtigiana(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa()) ||
								documentoDiSpesaManager.isCompensoSoggettoGestore(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
							
							Long idModalitaPagamento=null;
							if(documentoDiSpesaManager.isCedolinoCostiStandard(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ cedolino costi standard, forzo inserimento di un pagamento fittizio");
								 idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							}else if (documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ spese generali forfettarie costi standard, forzo inserimento di un pagamento fittizio");
								idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							}else if (documentoDiSpesaManager.isAutocertificazioneSpese(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ autocertificazione spese, forzo inserimento di un pagamento fittizio");
								idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							} else if (documentoDiSpesaManager.isSALSenzaQuietanza(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ sal senza quietanza, forzo inserimento di un pagamento fittizio");
								idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							} else if (documentoDiSpesaManager.isCompensoMensileTirocinante(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ compenso mensile tirocinante, forzo inserimento di un pagamento fittizio");
								idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							} else if (documentoDiSpesaManager.isCompensoImpresaArtigiana(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ compenso impresa artigiana, forzo inserimento di un pagamento fittizio");
								idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							} else if (documentoDiSpesaManager.isCompensoSoggettoGestore(documentoDiSpesa.getDescBreveTipoDocumentoDiSpesa())){
								logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n++++++++++ compenso soggetto gestore, forzo inserimento di un pagamento fittizio");
								idModalitaPagamento= getIdModalitaPagamentoCostiStandard();
							}
							
							Long idCausale=null;
							String rifPagamento=null;
							String tipoDataPagamento="";
							Long idProgetto = documentoDiSpesa.getIdProgetto();
							BandoLineaProgettoVO bandoLinea = progettoManager.findBandoLineaForProgetto(idProgetto);
							if (regolaManager.isRegolaApplicabileForBandoLinea(bandoLinea.getProgrBandoLineaIntervento().longValue(),
									BR05_VISUALIZZA_DATA_EFFETTIVA)) {
								tipoDataPagamento= DATA_EFFETTIVO_PAGAMENTO;
							}else if (regolaManager.isRegolaApplicabileForBandoLinea(bandoLinea.getProgrBandoLineaIntervento().longValue(),
									BR04_VISUALIZZA_DATA_VALUTA)) {
								tipoDataPagamento= DATA_VALUTA_PAGAMENTO;
							}
							
							logger.info("tipoDataPagamento ---> "+tipoDataPagamento);
							logger.info("\n\n\n\n\n\n\n\n\n\n\n Importo Rendicontabile documento di spesa prima di inserimento:::: " + documentoDiSpesa.getImportoRendicontabile());
							
							//PBANDI - 2792
							Long idNuovoPagamentoAssociato = pbandipagamentiDAO
									.inserisciPagamento(
											idModalitaPagamento,
											new java.sql.Date(documentoDiSpesa.getDataDocumentoDiSpesa().getTime()),
											documentoDiSpesa.getImportoRendicontabile(),
											idUtente,
											STATO_PAGAMENTO_INSERITO,
											tipoDataPagamento,
											idCausale,
											rifPagamento, null);
							logger.info("\n\n\ninserito pagamento forzato con id "+idNuovoPagamentoAssociato+ "data pagamento = "+ new java.sql.Date(documentoDiSpesa.getDataDocumentoDiSpesa().getTime()));
							
							pbandipagamentiDAO.inserisciPagamentoRDocSpesa(
									idNuovoPagamentoAssociato, documentoVO.getIdDocumentoDiSpesa().longValue(), 
									idUtente);
							logger.info("inserito PagamentoRDocSpesa\n\n\n");
							
						}
						
						
					} catch (Exception e) {
						logger.error(
								"Errore durante l' inserimento del documento di spesa",
								e);
						logger.dump(documentoVO);
						throw new GestioneDocumentiDiSpesaException(
								"Errore durante l' inserimento del documento di spesa",
								e);
					}
				} else {
					/*
					 * Modifica
					 */
					//old 
					
					DocumentoDiSpesaProgettoVO oldDocumentoVO = new DocumentoDiSpesaProgettoVO();
					oldDocumentoVO.setIdDocumentoDiSpesa(documentoVO.getIdDocumentoDiSpesa());
					oldDocumentoVO.setIdProgetto(BigDecimal.valueOf(documentoDiSpesa.getIdProgetto()));
					oldDocumentoVO = genericDAO.findSingleWhere(oldDocumentoVO);
					String oldTipoInvio = oldDocumentoVO.getTipoInvio();
					
					documentoVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
					try {
						genericDAO.updateNullables(documentoVO);
						

						PbandiRDocSpesaProgettoVO associaDocumentoProgettoVO = new PbandiRDocSpesaProgettoVO();
						associaDocumentoProgettoVO.setIdProgetto(BigDecimal.valueOf(documentoDiSpesa.getIdProgetto()));
						associaDocumentoProgettoVO.setIdDocumentoDiSpesa(BigDecimal.valueOf(documentoDiSpesa.getIdDocumentoDiSpesa()));
						associaDocumentoProgettoVO.setTask(documentoDiSpesa.getTask());
						associaDocumentoProgettoVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));

						associaDocumentoProgettoVO
								.setImportoRendicontazione(NumberUtil
										.createScaledBigDecimal(documentoDiSpesa.getImportoRendicontabile()));
						
						associaDocumentoProgettoVO.setTipoInvio(documentoDiSpesa.getTipoInvio());
						if (documentoDiSpesa.getIdAppalto() != null)
							associaDocumentoProgettoVO.setIdAppalto(new BigDecimal(documentoDiSpesa.getIdAppalto()));
						
						associaDocumentoProgettoVO.setNoteValidazione(documentoDiSpesa.getNoteValidazione());

						genericDAO.update(associaDocumentoProgettoVO);

						result.setIdDocumentoDiSpesa(documentoVO.getIdDocumentoDiSpesa().longValue());

						
						/* 30/04/2014
						 *  Documento di spesa - disassociate   files allegati to document
						 *  in case of changing   tipo   invio from Elettronico or Digitale  to Cartaceo */
						
					//if ante was cartaceo do nothing	
						if (oldTipoInvio.equalsIgnoreCase(TIPO_INVIO_DOCUMENTO_DIGITALE)){
							if(documentoDiSpesa.getTipoInvio().equalsIgnoreCase(TIPO_INVIO_DOCUMENTO_CARTACEO)){
								documentoManager.disassociateArchivioFileAssociatedDocDiSpesa(idUtente,
										documentoVO.getIdDocumentoDiSpesa().longValue(),documentoDiSpesa.getIdProgetto());
								
								/*
								 * Le associazione dei file allegati ai pagamenti del documento
								 * vengono cancellate solo se non esiste nessun altro progetto a cui il
								 * progetto e' associato che ha tipo invio DIGITALE
								 */
								PbandiRDocSpesaProgettoVO filterDocumentoDigitale = new PbandiRDocSpesaProgettoVO();
								filterDocumentoDigitale.setIdDocumentoDiSpesa(BigDecimal.valueOf(documentoDiSpesa.getIdDocumentoDiSpesa()));
								filterDocumentoDigitale.setTipoInvio(GestioneDocumentiDiSpesaSrv.TIPO_INVIO_DOCUMENTO_DIGITALE);
								
								
								PbandiRDocSpesaProgettoVO filtroProgetto = new PbandiRDocSpesaProgettoVO();
								filtroProgetto.setIdProgetto(BigDecimal.valueOf(documentoDiSpesa.getIdProgetto()));
								
								
								List<PbandiRDocSpesaProgettoVO> altriDocumentoProgetto = genericDAO.findListWhere(Condition.filterBy(filterDocumentoDigitale).
										and(new NotCondition<PbandiRDocSpesaProgettoVO>(Condition.filterBy(filtroProgetto))));
								
								if (ObjectUtil.isEmpty(altriDocumentoProgetto)) {
									
									int fileDissociati = pbandiArchivioFileDAOImpl.dissociateArchivioFilePagamenti(idUtente,documentoVO.getIdDocumentoDiSpesa().longValue(),null);
									logger.warn("n\n\n\file linked to pagamenti dissociated: "+fileDissociati);
									
									
								}
							} else {
								/*
								 * Se passo da documento elettronico a documento non elettronico
								 * allora devo impostare a null l'eventuale numero di protocollo
								 */
								String flagElettronicoOld = oldDocumentoVO.getFlagElettronico();
								if ("S".equalsIgnoreCase(flagElettronicoOld) && "N".equalsIgnoreCase(documentoVO.getFlagElettronico())) {
									pbandiDocumentiDiSpesaDAO.setNullNumeroProtocollo(NumberUtil.toBigDecimal(idUtente), documentoVO.getIdDocumentoDiSpesa(), documentoManager.getIdEntita(PbandiTDocumentoDiSpesaVO.class));
								}
							}
						}
							
						
						
						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
						messaggi.add(msg);
						result.setEsito(Boolean.TRUE);
					} catch (Exception e) {
						logger.error(
								"Errore durante la modifica del documento di spesa",
								e);
						logger.dump(documentoVO);
						throw new GestioneDocumentiDiSpesaException(
								"Errore durante la modifica del documento di spesa",
								e);
					}
				}
			}

			result.setMessaggi(beanUtil.transform(messaggi, MessaggioDTO.class));

			return result;

		 
	}

	private Long getIdModalitaPagamentoCostiStandard() {
		String chiave = GestioneDatiDiDominioSrv.MODALITA_PAGAMENTO;
		String descrizioneBreve = GestionePagamentiSrv.MODALITA_PAGAMENTO_COSTI_STANDARD;
		DecodificaDTO decodifica = decodificheManager
				.findDecodifica( chiave, descrizioneBreve );
		Long idModalitaPagamento=decodifica.getId();
		return idModalitaPagamento;
	}

	private boolean checkNotaCreditoTotaleRendicontabileFatturaSuperioreRendicontabileMassimo(
			DocumentoDiSpesaDTO notacredito) {
		/*
		 * Recupero i dati della fattura di riferimento
		 */
		PbandiTDocumentoDiSpesaVO filterDocumentoVO = new PbandiTDocumentoDiSpesaVO();
		filterDocumentoVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(notacredito.getIdDocRiferimento()));
		PbandiTDocumentoDiSpesaVO fattura = genericDAO
				.findSingleOrNoneWhere(filterDocumentoVO);

		BigDecimal imponibileFattura = fattura.getImponibile() == null ? new BigDecimal(
				0) : fattura.getImponibile();
		BigDecimal ivaACostoFattura = fattura.getImportoIvaCosto() == null ? new BigDecimal(
				0) : fattura.getImportoIvaCosto();

		BigDecimal rendicontabileMssimoFattura = NumberUtil.sum(
				imponibileFattura, ivaACostoFattura);

		/*
		 * Recupero i rendicontabili della fattura
		 */
		BigDecimal totaleRendicontabili = new BigDecimal(0);
		PbandiRDocSpesaProgettoVO filterDocPrjVO = new PbandiRDocSpesaProgettoVO();
		filterDocPrjVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(notacredito.getIdDocRiferimento()));
		List<PbandiRDocSpesaProgettoVO> rendicontabiliAssociatiFattura = genericDAO
				.findListWhere(filterDocPrjVO);
		for (PbandiRDocSpesaProgettoVO docPrj : rendicontabiliAssociatiFattura) {
			totaleRendicontabili = NumberUtil.sum(totaleRendicontabili,
					docPrj.getImportoRendicontazione());
		}

		/*
		 * Ricerco le note di credito associate alla fattura, escludendo
		 * eventualmente la nota di credito attuale (in caso di modifica)
		 */

		/*
		 * FIX PBandi-2314. Poiche' le note di credito possono essere associate
		 * a piu' progetti e devo considerare solamente gli importi relativi
		 * alla nota di credito devo eliminare le eventuali note duplicate
		 */
		List<DocumentoDiSpesaProgettoVO> noteCreditoFattura = documentoDiSpesaManager
				.eliminaNoteCreditoDuplicate(documentoDiSpesaManager
						.findNoteCreditoFattura(null,
								notacredito.getIdDocRiferimento()));

		/*
		 * Dal rendicontabile massimo della fattura sottraggo l' imponibile e
		 * l'iva a costo delle note di credito
		 */
		BigDecimal totaleNettoFattura = rendicontabileMssimoFattura;
		for (DocumentoDiSpesaProgettoVO nota : noteCreditoFattura) {
			if (notacredito.getIdDocumentoDiSpesa() == null
					|| NumberUtil.compare(NumberUtil.toBigDecimal(notacredito
							.getIdDocumentoDiSpesa()), nota
							.getIdDocumentoDiSpesa()) != 0) {
				BigDecimal imponibileNota = nota.getImponibile() == null ? new BigDecimal(
						0) : nota.getImponibile();
				BigDecimal ivaACostoNota = nota.getImportoIvaCosto() == null ? new BigDecimal(
						0) : nota.getImportoIvaCosto();
				totaleNettoFattura = NumberUtil.subtract(totaleNettoFattura,
						NumberUtil.sum(imponibileNota, ivaACostoNota));
			}
		}

		/*
		 * Sottraggo al totale della fattura il totale delle nota di credito
		 * corrente
		 */
		BigDecimal imponibileNota = notacredito.getImponibile() == null ? new BigDecimal(
				0) : new BigDecimal(notacredito.getImponibile());
		BigDecimal ivaACostoNota = notacredito.getImportoIvaACosto() == null ? new BigDecimal(
				0) : new BigDecimal(notacredito.getImportoIvaACosto());
		totaleNettoFattura = NumberUtil.subtract(totaleNettoFattura,
				NumberUtil.sum(imponibileNota, ivaACostoNota));

		if (NumberUtil.compare(totaleRendicontabili, totaleNettoFattura) > 0) {
			return true;
		} else {
			return false;
		}

	}

	public EsitoOperazioneDocumentoDiSpesa associaDocumentoAProgetto(
			Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa,
			Long idProgetto, Long idProgettoDocumento, String task, Double importoRendicontabile)
			throws CSIException, SystemException, UnrecoverableException {
		logger.begin();
		EsitoOperazioneDocumentoDiSpesa esito = new EsitoOperazioneDocumentoDiSpesa();
		esito.setEsito(Boolean.FALSE);
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa, idProgetto);
			// Boolean result = isDocumentoDiSpesaAssociatoProgetto(
			// idDocumentoDiSpesa, idProgetto);
			Double massimoRendicontabile = doFindMassimoRendicontabile(idDocumentoDiSpesa);
			MessaggioDTO msg = new MessaggioDTO();

			if (NumberUtil
					.compare(importoRendicontabile, massimoRendicontabile) <= 0) {

				String statoDocumentoDichiarabile = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
				DecodificaDTO decodificaStatoDichiarabile = decodificheManager
						.findDecodifica(
								GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
								statoDocumentoDichiarabile);

				PbandiRDocSpesaProgettoVO vo = new PbandiRDocSpesaProgettoVO();
				vo.setIdDocumentoDiSpesa(beanUtil.transform(idDocumentoDiSpesa,
						BigDecimal.class));
				vo.setIdProgetto(beanUtil.transform(idProgetto,
						BigDecimal.class));
				vo.setImportoRendicontazione(beanUtil.transform(
						importoRendicontabile, BigDecimal.class));
				vo.setTask(StringUtil.isEmpty(task) ? null : task);
				vo.setIdStatoDocumentoSpesa(NumberUtil
						.toBigDecimal(decodificaStatoDichiarabile.getId()));
				vo.setIdUtenteIns(beanUtil
						.transform(idUtente, BigDecimal.class));
				vo.setTipoInvio("D");
				vo = genericDAO.insert(vo);
				esito.setEsito(Boolean.TRUE);
				
				//recupero gli allegati dalla pbandi_t_file_entita dal progetto del documento
				PbandiTFileEntitaVO pbandiTFileEntitaVO = new PbandiTFileEntitaVO();
				pbandiTFileEntitaVO.setIdProgetto(new BigDecimal(idProgettoDocumento));
				pbandiTFileEntitaVO.setIdTarget(new BigDecimal(idDocumentoDiSpesa));
				pbandiTFileEntitaVO.setIdEntita(documentoManager.getIdEntita(PbandiTDocumentoDiSpesaVO.class));
				List<PbandiTFileEntitaVO> pbandiTFileEntitaVOs = genericDAO.findListWhere(pbandiTFileEntitaVO);
				
				if(pbandiTFileEntitaVOs!=null && pbandiTFileEntitaVOs.size()>0) {
					//aggiungo gli allegati alla pbandi_t_file_entita con il progetto corrente
					for(PbandiTFileEntitaVO fileEntita : pbandiTFileEntitaVOs) {
						fileEntita.setIdProgetto(new BigDecimal(idProgetto));
						fileEntita.setIdFileEntita(null);
					}
					genericDAO.insert(pbandiTFileEntitaVOs);
				}
				
				
				msg.setMsgKey(MessaggiConstants.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
			} else {
				msg.setMsgKey(ErrorConstants.WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE);
			}
			esito.setMessaggi(new MessaggioDTO[] { msg });

		} catch (Exception e) {
			throw new GestioneDocumentiDiSpesaException(
					"Errore durante isDocumentoDiSpesaAssociatoProgetto", e);
		} finally {
			logger.end();
		}

		return esito;
	}

	 

	public EsitoOperazioneElimina eliminaDocumentoDiSpesaConPagamenti(
			Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException {
		EsitoOperazioneElimina esito = new EsitoOperazioneElimina();
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa, idProgetto);

			/*
			 * Il documento puo' essere eliminato se e' in stato DICHIARABILE
			 * per il progetto ( e di conseguenza associato al progetto)
			 * Verifico lo stato del documento
			 */
			PbandiRDocSpesaProgettoVO filterDocPrjVO = new PbandiRDocSpesaProgettoVO();
			filterDocPrjVO.setIdDocumentoDiSpesa(new BigDecimal(
					idDocumentoDiSpesa));
			filterDocPrjVO.setIdProgetto(new BigDecimal(idProgetto));
			PbandiRDocSpesaProgettoVO docprjVO = null;
			
			try {
				docprjVO = genericDAO.findSingleWhere(filterDocPrjVO);
			} catch (RecordNotFoundException e) {
				GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
						ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_NON_ELIMINABILE_NON_ASSOCIATO_AL_PROGETTO);
				logger.error(
						"Errore: il documento di spesa["
								+ idDocumentoDiSpesa
								+ "] non e' eliminabile poiche' non e' associato al progetto["
								+ idProgetto + "].", e);
				throw gde;
			}

			DecodificaDTO statoDichiarabile = decodificheManager
					.findDecodifica(
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE);

			if (NumberUtil.compare(docprjVO.getIdStatoDocumentoSpesa(),
					NumberUtil.toBigDecimal(statoDichiarabile.getId())) != 0) {
				GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
						ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_NON_ELIMINABILE);
				logger.error("Errore: il documento di spesa["
						+ idDocumentoDiSpesa
						+ "] non e' modificabile a causa dello stato["
						+ docprjVO.getIdStatoDocumentoSpesa() + "].", gde);
				throw gde;
			}

			/*
			 * Se il documento e' una fattura ed ha delle note di credito
			 * associate (al documento-progetto) allora non si puo' eliminare il
			 * documento
			 */
			PbandiTDocumentoDiSpesaVO filterDocVO = new PbandiTDocumentoDiSpesaVO();
			filterDocVO
					.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa));
			PbandiTDocumentoDiSpesaVO doc = null;
			try {
				doc = genericDAO.findSingleWhere(filterDocVO);
			} catch (RecordNotFoundException e) {
				Exception gde = new Exception(
						ErrorMessages.ERRORE_SERVER);
				logger.error(
						"Errore: il documento di spesa["
								+ idDocumentoDiSpesa
								+ "] non e' stato trovato sulla PbandiTDocumentoDiSpesa.",
						e);
				throw gde;
			}

			if (isFattura(NumberUtil.toLong(doc.getIdTipoDocumentoSpesa()))) {
				if (isReferenziatoInNoteDiCredito(idDocumentoDiSpesa,
						idProgetto))
					throw new GestioneDocumentiDiSpesaException(
							ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_RIFERITO_A_NOTA_DI_CREDITO);
			}

			/*
			 * Se il documento e' associato ad altri progetti allora non
			 * cancello i pagamenti ed il documento, ma cancello l' associazione
			 * tra il documento ed il progetto e le voci di spesa associate al
			 * documento-progetto
			 */

			List<Long> altriProgetti = pbandiDocumentiDiSpesaDAO
					.findAltriProgettiCollegati(idDocumentoDiSpesa, idProgetto);

			/*
			 * Controllo di sicurezza. Non si puo' cancellare un documento di
			 * spesa se i pagamenti sono stati utilizzati in dichiarazioni di
			 * spesa del progetto
			 */
			PagamentoDocumentoDichiarazioneVO filterPagDocDich = new PagamentoDocumentoDichiarazioneVO();
			filterPagDocDich.setIdDocumentoDiSpesa(new BigDecimal(
					idDocumentoDiSpesa));
			filterPagDocDich.setIdProgetto(new BigDecimal(idProgetto));
			List<PagamentoDocumentoDichiarazioneVO> pagDocDich = genericDAO
					.findListWhere(filterPagDocDich);
			if (!ObjectUtil.isEmpty(pagDocDich)) {
				throw new GestioneDocumentiDiSpesaException(
						ErrorMessages.ERRORE_DOCUMENTO_DI_SPESA_PAGAMENTI_NON_ELIMINABILI);
			}

			/*
			 * Cancello l'associazione delle voci di spesa al documento-progetto
			 */
			try {
				cancellaAssociazioniDocDiSpesaConVociDispesa(
						idDocumentoDiSpesa, idProgetto);
			} catch (Exception e) {
				GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
						ErrorMessages.ERRORE_SERVER);
				logger.error(
						"Errore durante la cancellazione dell' associazione tra il documento di spesa["
								+ idDocumentoDiSpesa
								+ "] ed il progetto["
								+ idProgetto
								+ "] alle voci di spesa su PBandiTQuotaparteDocSpesa.",
						e);
				throw gde;
			}

			/*
			 * Cancello l'associazione documento-progetto
			 */
			try {
				cancellaAssociazioneDocDiSpesaProgetto(idDocumentoDiSpesa,
						idProgetto);
			} catch (Exception e) {
				GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
						ErrorMessages.ERRORE_SERVER);
				logger.error(
						"Errore durante la cancellazione dell' associazione tra il documento di spesa["
								+ idDocumentoDiSpesa + "] ed il progetto["
								+ idProgetto + "].", e);
				throw gde;
			}

			if (ObjectUtil.isEmpty(altriProgetti)) {

				/*
				 * Il documento e' associato solamente al progetto corrente,
				 * quindi posso cancellare il documento, le relazioni
				 * documento-pagamenti ed i pagamenti.
				 */

				PbandiRPagamentoDocSpesaVO filterPagDoc = new PbandiRPagamentoDocSpesaVO();
				filterPagDoc.setIdDocumentoDiSpesa(NumberUtil
						.toBigDecimal(idDocumentoDiSpesa));
				List<PbandiRPagamentoDocSpesaVO> pagamentiDoc = genericDAO
						.findListWhere(filterPagDoc);

				if (!ObjectUtil.isEmpty(pagamentiDoc)) {
					/*
					 * Cancellazione relazione documento-pagamento
					 */
					try {
						genericDAO
								.deleteWhere(Condition.filterBy(filterPagDoc));
					} catch (Exception e) {
						GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
								ErrorMessages.ERRORE_SERVER);
						logger.error(
								"Errore durante la cancellazione dell' associazione tra il documento di spesa["
										+ idDocumentoDiSpesa
										+ "] ed i pagamenti.", e);
						throw gde;
					}
 
					/*
					 * Cancellazione dei pagamenti
					 */
					List<PbandiTPagamentoVO> filterPagamenti = new ArrayList<PbandiTPagamentoVO>();
					for (PbandiRPagamentoDocSpesaVO pag : pagamentiDoc) {
						PbandiTPagamentoVO p = new PbandiTPagamentoVO();
						p.setIdPagamento(pag.getIdPagamento());
						filterPagamenti.add(p);
						documentoManager.disassociateArchivioFileAssociatedPagamento(  idUtente,  pag.getIdPagamento().longValue());
					}
					try {
						
						genericDAO.deleteWhere(Condition.filterBy(filterPagamenti));
					} catch (Exception e) {
						GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
								ErrorMessages.ERRORE_SERVER);
						logger.error(
								"Errore durante la cancellazione dei pagamenti sulla PBandiTPagamento associati al documento di spesa["
										+ idDocumentoDiSpesa + "]", e);
						throw gde;
					}
				}

				/* 30/04/2014
				 *  Documento di spesa - disassociazione dei file allegati al documento  
				 *  */
				try {
					documentoManager.disassociateArchivioFileAssociatedDocDiSpesa(idUtente,idDocumentoDiSpesa,idProgetto);
				} catch (Exception e) {
					GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
							ErrorMessages.ERRORE_SERVER);
					logger.error(
							"Errore durante la cancellazione del documento di spesa["
									+ idDocumentoDiSpesa + "]", e);
					throw gde;
				}
				
				/*
				 * Cancellazione del documento
				 */
				try {
					cancellaDocumentoDiSpesa(idDocumentoDiSpesa);
				} catch (Exception e) {
					GestioneDocumentiDiSpesaException gde = new GestioneDocumentiDiSpesaException(
							ErrorMessages.ERRORE_SERVER);
					logger.error(
							"Errore durante la cancellazione del documento di spesa["
									+ idDocumentoDiSpesa + "]", e);
					throw gde;
				}

			}

			List<String> messaggi = new ArrayList<String>();
			messaggi.add(ErrorMessages.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
			esito.setMessaggi(messaggi.toArray(new String[] {}));
		} catch (Exception e) {
			if (e instanceof GestioneDocumentiDiSpesaException)
				throw (GestioneDocumentiDiSpesaException) e;
			else {
				logger.error("Errore imprevisto durante la cancellazione del documento di spesa["+ idDocumentoDiSpesa + "]", e);
				throw new GestioneDocumentiDiSpesaException(ErrorMessages.ERRORE_SERVER, e);
			}
		} finally {
			logger.end();
		}

		return esito;
	}

	public Double findMassimoRendicontabile(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		logger.begin();
		Double rendicontabile = NESSUN_MASSIMO_RENDICONTABILE;
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa);
			rendicontabile = doFindMassimoRendicontabile(idDocumentoDiSpesa);
		} catch (Exception e) {
			throw new GestioneDocumentiDiSpesaException(
					"Errore durante findMassimoRendicontabile", e);
		} finally {
			logger.end();
		}

		return rendicontabile;
	}

	private Double doFindMassimoRendicontabile(Long idDocumentoDiSpesa) {
		Double rendicontabile;
		ProgettoAssociatoDocumentoDiSpesaVO filter = new ProgettoAssociatoDocumentoDiSpesaVO();
		filter.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa));
		List<ProgettoAssociatoDocumentoDiSpesaVO> progetti = genericDAO.where(
				filter).select();
		// boolean tipoDocumentoChecked = false;
		BigDecimal rendicontato = new BigDecimal(0);
		BigDecimal massimoRendicontabile = new BigDecimal(0);
		for (ProgettoAssociatoDocumentoDiSpesaVO progetto : progetti) {
			// }L{ PBANDI-2235: i Cedolini non centrano
			// if (!tipoDocumentoChecked) {
			// PbandiDTipoDocumentoSpesaVO tipoVO = genericDAO
			// .findSingleWhere(new PbandiDTipoDocumentoSpesaVO(
			// progetto.getIdTipoDocumentoSpesa()));
			// if (tipoVO
			// .getDescBreveTipoDocSpesa()
			// .equals(DocumentoDiSpesaManager
			// .getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO))
			// || tipoVO
			// .getDescBreveTipoDocSpesa()
			// .equals(DocumentoDiSpesaManager
			// .getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_CO_CO_PRO)))
			// {
			// return NESSUN_MASSIMO_RENDICONTABILE;
			// } else {
			// tipoDocumentoChecked = true;
			// }
			// }

			massimoRendicontabile = progetto.getMassimoRendicontabile();
			rendicontato = NumberUtil.sum(rendicontato,
					progetto.getImportoRendicontazione());
		}

		/*
		 * FIX PBANDI-2314. Al massimo rendicontabile si deve sottrarre la somma
		 * tra l'mponibile e l' iva a costo delle note di credito
		 */
		/*
		 * FIX PBandi-2314. Poiche' le note di credito possono essere associate
		 * a piu' progetti e devo considerare solamente gli importi relativi
		 * alla nota di credito devo eliminare le eventuali note duplicate
		 */
		/*
		 * Ricerco le note di credito
		 */
		List<DocumentoDiSpesaProgettoVO> noteCredito = documentoDiSpesaManager
				.eliminaNoteCreditoDuplicate(documentoDiSpesaManager
						.findNoteCreditoFattura(null, idDocumentoDiSpesa));
		for (DocumentoDiSpesaProgettoVO nota : noteCredito) {
			BigDecimal imponibileNota = nota.getImponibile() == null ? new BigDecimal(
					0) : nota.getImponibile();
			BigDecimal importoIvaCostoNota = nota.getImportoIvaCosto() == null ? new BigDecimal(
					0) : nota.getImportoIvaCosto();

			massimoRendicontabile = NumberUtil.subtract(massimoRendicontabile,
					NumberUtil.sum(imponibileNota, importoIvaCostoNota));
		}

		rendicontabile = NumberUtil.toDouble(NumberUtil.subtract(
				massimoRendicontabile, rendicontato));
		return rendicontabile;
	}

	public Boolean isDocumentoTotalmenteQuietanzato(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		logger.begin();
		Boolean isDocumentoTotalmenteQuietanzato = Boolean.FALSE;
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa);

			isDocumentoTotalmenteQuietanzato = isDocumentoTotalmenteQuietanzato(idDocumentoDiSpesa);

		} catch (Exception e) {
			throw new GestioneDocumentiDiSpesaException(
					"Errore durante isDocumentoTotalmenteQuietanzato", e);
		} finally {
			logger.end();
		}

		return isDocumentoTotalmenteQuietanzato;

	}

	private Boolean isDocumentoTotalmenteQuietanzato(Long idDocumentoDiSpesa) {
		Boolean isDocumentoTotalmenteQuietanzato;
		final BigDecimal idDocumentoDiSpesaB = new BigDecimal(
				idDocumentoDiSpesa);
		PbandiTDocumentoDiSpesaVO documentoVO = genericDAO
				.findSingleWhere(new PbandiTDocumentoDiSpesaVO(
						idDocumentoDiSpesaB));
		PbandiRPagamentoDocSpesaVO filtroPagamenti = new PbandiRPagamentoDocSpesaVO();
		filtroPagamenti.setIdDocumentoDiSpesa(idDocumentoDiSpesaB);

		List<Pair<GenericVO, PbandiRPagamentoDocSpesaVO, PbandiTPagamentoVO>> pagamentiDocumento = genericDAO
				.join(Condition.filterBy(filtroPagamenti),
						Condition.validOnly(PbandiTPagamentoVO.class))
				.by("idPagamento").select();
		BigDecimal importoTotalePagamenti = new BigDecimal(0);
		for (Pair<GenericVO, PbandiRPagamentoDocSpesaVO, PbandiTPagamentoVO> pair : pagamentiDocumento) {
			importoTotalePagamenti = NumberUtil.sum(importoTotalePagamenti,
					pair.getSecond().getImportoPagamento());
		}

		isDocumentoTotalmenteQuietanzato = (NumberUtil
				.compare(documentoVO.getImportoTotaleDocumento(),
						importoTotalePagamenti) == 0);
		return isDocumentoTotalmenteQuietanzato;
	}

	public Boolean isDocumentoDichiarabileInAllProgetti(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		logger.begin();
		Boolean result = false;
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa, idProgetto);

			String statoDocumentoDichiarabile = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
			DecodificaDTO decodificaStato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					statoDocumentoDichiarabile);

			PbandiRDocSpesaProgettoVO filterVO = new PbandiRDocSpesaProgettoVO();
			filterVO.setIdDocumentoDiSpesa(NumberUtil
					.toBigDecimal(idDocumentoDiSpesa));

			PbandiRDocSpesaProgettoVO notConditionFilterProgettoVO = new PbandiRDocSpesaProgettoVO();
			notConditionFilterProgettoVO.setIdProgetto(NumberUtil
					.toBigDecimal(idProgetto));

			PbandiRDocSpesaProgettoVO notConditionFilterStatoDocumentoVO = new PbandiRDocSpesaProgettoVO();
			notConditionFilterStatoDocumentoVO
					.setIdStatoDocumentoSpesa(NumberUtil
							.toBigDecimal(decodificaStato.getId()));

			AndCondition<PbandiRDocSpesaProgettoVO> condition = new AndCondition<PbandiRDocSpesaProgettoVO>(
					Condition.filterBy(filterVO), Condition.not(Condition
							.filterBy(notConditionFilterProgettoVO)),
					Condition.not(Condition
							.filterBy(notConditionFilterStatoDocumentoVO)));

			List<PbandiRDocSpesaProgettoVO> list = genericDAO
					.findListWhere(condition);
			if (ObjectUtil.isEmpty(list)) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			throw new GestioneDocumentiDiSpesaException(
					"Errore durante isDocumentoDichiarabileInAllProgetti", e);
		} finally {
			logger.end();
		}
		return result;
	}

	private boolean isDocumentoTotalmenteQuietanzato(
			DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO documentoVO) {
		Boolean isDocumentoTotalmenteQuietanzato = false;
		;
		BigDecimal importoTotaleDocumentoIvato = documentoVO
				.getImportoTotaleDocumentoIvato();
		BigDecimal totaleNoteCredito = documentoVO.getTotaleNoteCredito();
		BigDecimal totalePagamenti = documentoVO.getTotalePagamenti();
		if (importoTotaleDocumentoIvato == null)
			importoTotaleDocumentoIvato = new BigDecimal(0);
		BigDecimal importoAlNettoNoteDiCredito = NumberUtil.subtract(
				importoTotaleDocumentoIvato, totaleNoteCredito);
		isDocumentoTotalmenteQuietanzato = (NumberUtil.compare(
				importoAlNettoNoteDiCredito, totalePagamenti) == 0);
		return isDocumentoTotalmenteQuietanzato;
	}

	public TipoDocumentoDiSPesaDTO getTipoDocumentoDiSpesa(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
		logger.begin();
		try {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa);

			PbandiTDocumentoDiSpesaVO filter = new PbandiTDocumentoDiSpesaVO();
			filter.setIdDocumentoDiSpesa(NumberUtil
					.toBigDecimal(idDocumentoDiSpesa));
			PbandiTDocumentoDiSpesaVO doc = genericDAO.findSingleWhere(filter);

			DecodificaDTO decodificaTipo = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					doc.getIdTipoDocumentoSpesa());

			TipoDocumentoDiSPesaDTO result = new TipoDocumentoDiSPesaDTO();
			result.setIdTipoDocumentoSpesa(decodificaTipo.getId());
			result.setDescBreveTipoDocSpesa(decodificaTipo
					.getDescrizioneBreve());
			result.setDescTipoDocumentoSpesa(decodificaTipo.getDescrizione());

			return result;

		} finally {
			logger.end();
		}
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(
			ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	private void processaDocumentoPerDatiAggregati(
			DocumentoDiSpesaSemplificazioneFilterOutVociSpesaVO documento,
			String taskProgetto, BigDecimal idTipoDocumentoNotaCredito,
			BigDecimal idSoggettoBeneficiario,
			BigDecimal idProgettoBeneficiario,
			BigDecimal idStatoDocumentoDichiarabile,
			BigDecimal idStatoDocumentoInValidazione,
			BigDecimal idStatoDocumentoDaCompletare, boolean isBr03,
			boolean isBr40) {
		/*
		 * Ricerco i progetti associati al documento precedente
		 */
		ProgettoAssociatoDocumentoDiSpesaVO filtroProgettoAssociatoDocSpesaVO = new ProgettoAssociatoDocumentoDiSpesaVO();
		filtroProgettoAssociatoDocSpesaVO.setIdDocumentoDiSpesa(documento
				.getIdDocumentoDiSpesa());
		if (!StringUtil.isEmpty(taskProgetto)) {
			filtroProgettoAssociatoDocSpesaVO.setTask(taskProgetto);
		}
		List<ProgettoAssociatoDocumentoDiSpesaVO> progettiAssociati = genericDAO
				.findListWhere(filtroProgettoAssociatoDocSpesaVO);
		if (progettiAssociati.size() > 0) {
			StringBuilder codiceProgetti = new StringBuilder();
			StringBuilder importiTotaliDocumentiIvati = new StringBuilder();
			StringBuilder statiDocumento = new StringBuilder();
			StringBuilder tasks = new StringBuilder();

			final String APERTURA = "";
			final String CHIUSURA = "";
			final String SEPARATORE = "\n";

			BigDecimal idProgetto = null; //idProgetto di uno dei progetti a cui è associato il documento
			BigDecimal massimoRendicontabile = null;
			BigDecimal importoRendicontato = new BigDecimal(0);
			boolean isProgettoAssociato = false;
			boolean isDocumentoAssociatoBeneficiario = NumberUtil.compare(
					documento.getIdSoggetto(), idSoggettoBeneficiario) == 0;
			boolean isStatoDocumentoDichiarabile = false;
			boolean isStatoDocumentoInValidazione = false;
			boolean isStatoDocumentoDaCompletare = false;
			boolean statoChecked = false;
			boolean modificabile = false;
			boolean eliminabile = false;
			boolean clonabile = false;
			boolean isNotaDiCredito = false;
			boolean isDocRiferito = false;
			/*
			 * FIX PBANDI-2314: non esiste piu' lo stato del pagamento. boolean
			 * isPagamentiModificabili = false;
			 */

			for (ProgettoAssociatoDocumentoDiSpesaVO vo : progettiAssociati) {
				boolean isProgettoCorrente = NumberUtil.compare(
						idProgettoBeneficiario, vo.getIdProgetto()) == 0;
				if (isProgettoCorrente) {
					isProgettoAssociato = true;
				}
				if (isProgettoCorrente && !statoChecked) {
					isStatoDocumentoDichiarabile = isStatoDocumentoDichiarabile
							|| NumberUtil.compare(
									vo.getIdStatoDocumentoSpesa(),
									idStatoDocumentoDichiarabile) == 0;
					isStatoDocumentoInValidazione = isStatoDocumentoInValidazione
							|| NumberUtil.compare(
									vo.getIdStatoDocumentoSpesa(),
									idStatoDocumentoInValidazione) == 0;
					isStatoDocumentoDaCompletare = isStatoDocumentoDaCompletare
							|| NumberUtil.compare(
									vo.getIdStatoDocumentoSpesa(),
									idStatoDocumentoDaCompletare) == 0;

					isNotaDiCredito = NumberUtil.compare(
							vo.getIdTipoDocumentoSpesa(),
							idTipoDocumentoNotaCredito) == 0;
					isDocRiferito = Constants.FLAG_TRUE.equals(vo
							.getFlagDocRiferito());
					/*
					 * FIX PBANDI-2314: non esiste piu' lo stato del pagamento.
					 * isPagamentiModificabili = Constants.FLAG_TRUE
					 * .equals(vo.getFlagPagModificabili());
					 */
					statoChecked = true;
				}

				massimoRendicontabile = vo.getMassimoRendicontabile();
				BigDecimal importoRendicontazione = vo
						.getImportoRendicontazione();
				importoRendicontato = NumberUtil.sum(importoRendicontazione,
						importoRendicontato);

				codiceProgetti.append(vo.getCodiceVisualizzato() + SEPARATORE);
				idProgetto = vo.getIdProgetto();
				statiDocumento.append(vo.getDescStatoDocumentoSpesa()
						+ SEPARATORE);

				importiTotaliDocumentiIvati.append(NumberUtil
						.getStringValue(importoRendicontazione) + SEPARATORE);

				tasks.append(ObjectUtil.nvl(vo.getTask(), "") + SEPARATORE);

			}
			/*
			 * FIX PBANDI-2314. Il controllo dello totalmente quietanzato non
			 * serve piu' poiche' un documento e' modificabile se e' in stato
			 * DICHIARABILe o se e' in stato DA COMPLETARE e non esiste la
			 * regola BR03 (cioe' il documento puo' essere inviato in
			 * validazione anche senza che i pagamenti siano uguale al totale
			 * del documento)
			 * 
			 * boolean isDocumentoTotalmenteQuietanzato = false; if
			 * (isStatoDocumentoDaCompletare) { isDocumentoTotalmenteQuietanzato
			 * = isDocumentoTotalmenteQuietanzato(documentoVO); }
			 */

			// MODIFICABILE SE:
			// 1) e' nel progetto
			// E
			// 2a) e' in stato dichiarabile (non e' ancora andata in
			// validazione)
			// OPPURE
			// 2b) e' possibile inviare documenti non totalmente
			// quietanzati, il documento NON E' totalmente quietanzato
			// OPPURE e' in stato DA COMPLETARE

			/*
			 * FIX PBANDI-2314:non esiste piu' lo stato del pagamento.
			 * modificabile = isProgettoAssociato &&
			 * (isStatoDocumentoDichiarabile || (!isBr03 &&
			 * (isStatoDocumentoInValidazione &&
			 * (!isDocumentoTotalmenteQuietanzato || isPagamentiModificabili)))
			 * && isPagamentiModificabili);
			 */
			modificabile = isProgettoAssociato
					&& (isStatoDocumentoDichiarabile || (!isBr03 && (isStatoDocumentoDaCompletare)));
			eliminabile = modificabile && isStatoDocumentoDichiarabile
					&& !isDocRiferito;
			clonabile = isProgettoAssociato && !isNotaDiCredito;

			documento
					.setFlagAssociato(isProgettoAssociato ? Constants.FLAG_TRUE
							: Constants.FLAG_FALSE);

			documento.setFlagEliminabile(eliminabile ? Constants.FLAG_TRUE
					: Constants.FLAG_FALSE);
			documento.setFlagModificabile(modificabile ? Constants.FLAG_TRUE
					: Constants.FLAG_FALSE);
			documento.setFlagClonabile(clonabile ? Constants.FLAG_TRUE
					: Constants.FLAG_FALSE);

			if (codiceProgetti.length() > 0) {
				boolean associabile = isDocumentoAssociatoBeneficiario
						// Non serve piu'&& isStatoDocumentoDichiarabile
						&& !isProgettoAssociato
						&& NumberUtil.compare(importoRendicontato,
								massimoRendicontabile) < 0 && isBr40;

				/*
				 * FIX PBANDI-2314. Se il documento e' una nota di credito
				 * allora e' associabile se e solo se esiste il legame tra il
				 * progetto-fattura di riferimento
				 */
				if (associabile
						&& NumberUtil.compare(
								documento.getIdTipoDocumentoDiSpesa(),
								idTipoDocumentoNotaCredito) == 0) {

					PbandiRDocSpesaProgettoVO filterFattura = new PbandiRDocSpesaProgettoVO();
					filterFattura.setIdDocumentoDiSpesa(documento
							.getIdDocRiferimento());
					filterFattura.setIdProgetto(idProgettoBeneficiario);
					List<PbandiRDocSpesaProgettoVO> fatturaPrj = genericDAO
							.findListWhere(filterFattura);
					if (ObjectUtil.isEmpty(fatturaPrj)) {
						associabile = false;
					}
				}

				documento.setFlagAssociabile(associabile ? Constants.FLAG_TRUE
						: Constants.FLAG_FALSE);

				documento.setCodiceProgetto(APERTURA
						+ codiceProgetti.substring(0,
								codiceProgetti.lastIndexOf(SEPARATORE))
						+ CHIUSURA);
				documento.setIdProgetto(idProgetto);
				documento.setDescStatoDocumentoSpesa(APERTURA
						+ statiDocumento.substring(0,
								statiDocumento.lastIndexOf(SEPARATORE))
						+ CHIUSURA);

				documento.setImportiTotaliDocumentiIvati(APERTURA
						+ importiTotaliDocumentiIvati.substring(0,
								importiTotaliDocumentiIvati
										.lastIndexOf(SEPARATORE)) + CHIUSURA);

				documento.setEstremiTabella(APERTURA
						+ documento
								.getEstremiTabella()
								.concat(SEPARATORE)
								.concat(tasks.substring(0,
										tasks.lastIndexOf(SEPARATORE)))
						+ CHIUSURA);
			}

		}
	}
	
	
	
	public Boolean existBR42ForOneBandolinea(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa);
			
			boolean result = false;
			
			/*
			 * Ricerco tutti i progetti ai quali
			 * il documento e' associato
			 */
			PbandiRDocSpesaProgettoVO filter = new PbandiRDocSpesaProgettoVO();
			filter.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
			List<PbandiRDocSpesaProgettoVO> progetti = genericDAO.findListWhere(filter);
			if (!ObjectUtil.isEmpty(progetti)) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("idProgetto", "idProgetto");
				
				/*
				 * Ricerco di bandolinea dei progetti
				 */
				 List<BandoLineaProgettoVO> filterProgetti = beanUtil.transformList(progetti, BandoLineaProgettoVO.class, map);
				 List<BandoLineaProgettoVO> bandolinea = genericDAO.findListWhere(filterProgetti);
				 if (!ObjectUtil.isEmpty(bandolinea)) {
					 /*
					  * Verifico che esiste la regola BR42 per almeno un
					  * bandolinea
					  */
					 for (BandoLineaProgettoVO b : bandolinea) {
						 RegolaAssociataBandoLineaVO filterBandolinea = new RegolaAssociataBandoLineaVO();
						 filterBandolinea.setProgrBandoLineaIntervento(b.getProgrBandoLineaIntervento());
						 filterBandolinea.setDescBreveRegola(RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
						 if (genericDAO.findSingleOrNoneWhere(filterBandolinea) != null) {
							 result = true;
							 break;
						 }
					 }
				 }
			} 
			
			return result;
			
		
	}
	
	
	public Boolean existBR42ForAllBandolinea(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa);
			
			boolean result = true;
			
			/*
			 * Ricerco tutti i progetti ai quali
			 * il documento e' associato
			 */
			PbandiRDocSpesaProgettoVO filter = new PbandiRDocSpesaProgettoVO();
			filter.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
			List<PbandiRDocSpesaProgettoVO> progetti = genericDAO.findListWhere(filter);
			if (!ObjectUtil.isEmpty(progetti)) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("idProgetto", "idProgetto");
				
				/*
				 * Ricerco di bandolinea dei progetti
				 */
				 List<BandoLineaProgettoVO> filterProgetti = beanUtil.transformList(progetti, BandoLineaProgettoVO.class, map);
				 List<BandoLineaProgettoVO> bandolinea = genericDAO.findListWhere(filterProgetti);
				 if (!ObjectUtil.isEmpty(bandolinea)) {
					 /*
					  * Verifico se esiste la regola BR42 per tutti
					  * i bandolinea
					  */
					 for (BandoLineaProgettoVO b : bandolinea) {
						 RegolaAssociataBandoLineaVO filterBandolinea = new RegolaAssociataBandoLineaVO();
						 filterBandolinea.setProgrBandoLineaIntervento(b.getProgrBandoLineaIntervento());
						 filterBandolinea.setDescBreveRegola(RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
						 if (genericDAO.findSingleOrNoneWhere(filterBandolinea) == null) {
							 result = false;
							 break;
						 }
					 }
				 }
			} else {
				result = false;
			}
			
			return result;
			
		
	}

	public byte[] generateTimbro(Long idUtente, String identitaDigitale,
			Long idDocumentoDiSpesa, Long idProgetto, Long idDocumentoIndex) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentiDiSpesaException {
			String[] nameParams = new String[] { "idUtente",
					"identitaDigitale", "idDocumentoDiSpesa", "idProgetto","idDocumentoIndex" };
			ValidatorInput.verifyNullValue(nameParams, idUtente,
					identitaDigitale, idDocumentoDiSpesa,idProgetto, idDocumentoIndex);
			try {
				byte[] bytes = dynamicTemplateManager.createTimbro(idProgetto, idDocumentoDiSpesa, idDocumentoIndex);
				return bytes;
			} catch (Exception e) {
				throw new GestioneDocumentiDiSpesaException(e.getMessage(),e);
			}
		
	}
	
	
	
	
	
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoReportDocumentiDiSpesaDTO getReportDocumentiDiSpesa(
			 Long idUtente, String identitaDigitale, FiltroRicercaDocumentiSpesa filter)
					throws CSIException,
					SystemException, UnrecoverableException,
					GestioneDocumentiDiSpesaException {
		
		String[] nameParams = new String[] { "idUtente","identitaDigitale", "filter"};
		ValidatorInput.verifyNullValue(nameParams, idUtente,identitaDigitale, filter);
		logger.info("generating report documenti filter :"+filter);
		logger.shallowDump(filter, "info");
		EsitoReportDocumentiDiSpesaDTO esito=new EsitoReportDocumentiDiSpesaDTO();
		try {
				 
			ItemRicercaDocumentiSpesa[] documenti=findDocumentiDiSpesaSemplificazione(idUtente, identitaDigitale, filter);
			 ArrayList<DocumentoDiSpesa> reportElements = beanUtil.transformToArrayList(documenti, DocumentoDiSpesa.class);
			 for (DocumentoDiSpesa documentoDiSpesa : reportElements) {
				if(documentoDiSpesa.getFornitore()!=null && documentoDiSpesa.getFornitore().contains("\n")){
					String fornitore = documentoDiSpesa.getFornitore();
					documentoDiSpesa.setFornitore(fornitore.replace("\n", "  "));
				}
				if(documentoDiSpesa.getEstremi()!=null && documentoDiSpesa.getEstremi().contains("\n")){
					String estremi = documentoDiSpesa.getEstremi();
					documentoDiSpesa.setEstremi(estremi.replace("\n", "  "));
				}
				if(documentoDiSpesa.getStato()!=null && documentoDiSpesa.getStato().contains("\n")){
					String stato = documentoDiSpesa.getStato();
					documentoDiSpesa.setStato(stato.replace("\n", "  "));
				}
				if(documentoDiSpesa.getProgetto()!=null && documentoDiSpesa.getProgetto().contains("\n")){
					String progetto = documentoDiSpesa.getProgetto();
					documentoDiSpesa.setProgetto(progetto.replace("\n", "  "));
				}
			}
			byte[] bytes = TableWriter
					.writeTableToByteArray("reportDocumentiDiSpesa",
							new ExcelDataWriter("reportDocumentiDiSpesa"),
							reportElements);

			String nomeFile = "reportDocumentiDiSpesa.xls";

			esito = new EsitoReportDocumentiDiSpesaDTO();
			esito.setExcelBytes(bytes);
			esito.setNomeFile(nomeFile); 
			return esito;
			} catch (Exception e) {
				throw new GestioneDocumentiDiSpesaException(e.getMessage(),e);
			}
	}
	
	
}
