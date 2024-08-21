/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.dto.contoeconomico.ContoEconomicoItem;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VociDiSpesaCulturaDTO;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VociDiSpesaCulturaDTO;
//import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionecronoprogramma.GestioneCronoprogrammaBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.PropostaRimodulazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VoceDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.FonteFinanziaria;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ModalitaAgevolazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.AppaltoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiRinunciaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.EnteAppartenenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.FileAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.MacroSezione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.MicroSezione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.VoceDiCostoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.AppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ContoEconomicoConStatoContribVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ContoEconomicoConStatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaDichiarazioneTotalePagamentiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.EnteDiAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoPre2016VO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TemplateJasperVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaFineProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico.ProceduraAggiudicazioneProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.ErogazioneReportVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAffidServtecArVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCeAltriCostiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCspSoggRuoloEnteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRibassoAstaVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DynamicTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.EnteManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TemplateDbManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DomandaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IterVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoFaseMonitVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDatiProgettoQtesVO;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseBand;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

/**
 * Per sfruttare appieno le potenzialita di PopolaTemplate, bisogna seguire le
 * seguenti regole: 1) stabilire il tipo modello che si vuole generare
 * (Rimodulazione,Comunicazione Fine Progetto ecc) 2) settare le chiavi di
 * ricerca dei dati di popolamento (id progetto,id soggetto beneficiario ecc) 3)
 * invocare il method popolaModello()
 */
public class PopolaTemplateManager {

	private static final String PADDING = " ";
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	@Autowired
	private DocumentoManager documentoManager;
	@Autowired
	private DynamicTemplateManager dynamicTemplateManager;
	@Autowired
	private EnteManager enteManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;

	// private DichiarazioneDiSpesaConTipoVO dichiarazioneFinale;

	// private ProgettoBandoLineaVO progettoBandoLinea;
	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private RappresentanteLegaleManager rappresentanteLegaleManager;
	@Autowired
	private RegolaManager regolaManager;	
	@Autowired
	private SedeManager sedeManager ;
	@Autowired
	private SoggettoManager soggettoManager;
	@Autowired
	private TemplateDbManager templateDbManager;
	
	private TemplateJasperVO actualTemplateVO;
	@Autowired
	private GestioneCronoprogrammaBusinessImpl gestioneCronoprogrammaBusiness;
	@Autowired
	private it.csi.pbandi.pbservizit.integration.dao.impl.ContoEconomicoDAOImpl contoEconomicoDAOImpl;
	
	@Autowired
	private it.csi.pbandi.pbservizit.integration.dao.CronoProgrammaFasiDAO cronoProgrammaDAO;
	
	public GestioneCronoprogrammaBusinessImpl getGestioneCronoprogrammaBusiness() {
		return gestioneCronoprogrammaBusiness;
	}

	public void setGestioneCronoprogrammaBusiness(
			GestioneCronoprogrammaBusinessImpl gestioneCronoprogrammaBusiness) {
		this.gestioneCronoprogrammaBusiness = gestioneCronoprogrammaBusiness;
	}

	public void setRegolaManager(RegolaManager regolaManager) {
		this.regolaManager = regolaManager;
	}

	public RegolaManager getRegolaManager() {
		return regolaManager;
	}

	static final Set<String> SEZIONI = new HashSet<String>();
	
	
	public static final String MODELLO_DICHIARAZIONE_DI_SPESA = "DS";
	public static final String MODELLO_CHECK_LIST_VALIDAZIONE = "CL";
	public static final String MODELLO_CHECK_LIST_CERTIFICAZIONE_PER_PROPOSTA = "CLC";
	public static final String MODELLO_CHECK_LIST_CERTIFICAZIONE_PER_PROGETTO = "CLCP";
	public static final String MODELLO_DICHIARAZIONE_FINALE_DI_CERTIFICAZIONE = "DFC";
	public static final String MODELLO_RICHIESTA_EROGAZIONE = "RE";
	public static final String MODELLO_VERBALE_CONTROLLO_IN_LOCO = "VCV";
	public static final String MODELLO_PROPOSTA_DI_RIMODULAZIONE = "PR";
	public static final String MODELLO_RIMODULAZIONE = "RM";
	public static final String MODELLO_CHECK_LIST_IN_LOCO = "CLIL";
	public static final String MODELLO_SCHEDA_OLAF_IRREGOLARITA = "SOI";
	public static final String MODELLO_DATI_AGGIUNTIVI_IRREGOLARITA = "DAI";
	public static final String MODELLO_COMUNICAZIONE_DI_RINUNCIA = "RIN";
	public static final String MODELLO_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE = "FPC";
	public static final String MODELLO_RELAZIONE_TECNICA = "RT";
	public static final String MODELLO_MODELLO_VALIDAZIONE_DELLA_SPESA = "MVDS";
	public static final String MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO = "CFP";
	public static final String MODELLO_RICHIESTA_II_ACCONTO = "R2A";
	public static final String MODELLO_RICHIESTA_ULTERIORE_ACCONTO = "RUA";
	public static final String MODELLO_RICHIESTA_ANTICIPAZIONE = "RA";
	public static final String MODELLO_ATTO_DI_LIQUIDAZIONE = "ATTO";
	public static final String MODELLO_RICHIESTA_SALDO = "SLD";
	
	public static final String CHIAVE_ALL_DICH_REGIME_IVA = "CHIAVE_ALL_DICH_REGIME_IVA";
	public static final String CHIAVE_BENEFICIARIO = "CHIAVE_BENEFICIARIO";
	public static final String CHIAVE_CONTOECONOMICO_COPY_ISTR = "CHIAVE_CONTOECONOMICO_COPY_ISTR";
	public static final String CHIAVE_CONTOECONOMICO_COPY_BEN = "CHIAVE_CONTOECONOMICO_COPY_BEN";
	public static final String CHIAVE_ESTREMI_BANCARI = "CHIAVE_ESTREMI_BANCARI";
	public static final String CHIAVE_ID_PROGETTO = "CHIAVE_ID_PROGETTO";
	public static final String CHIAVE_ID_BANDO_LINEA = "CHIAVE_ID_BANDO_LINEA";
	public static final String CHIAVE_ID_RAPPRESENTANTE_LEGALE = "CHIAVE_ID_RAPPRESENTANTE_LEGALE";
	public static final String CHIAVE_IMPORTO_RICHIESTA_SALDO = "CHIAVE_IMPORTO_RICHIESTA_SALDO";
	public static final String CHIAVE_PROGETTO = "CHIAVE_PROGETTO";
	public static final String CHIAVE_RAPPRESENTANTE_LEGALE = "CHIAVE_RAPPRESENTANTE_LEGALE";
	public static final String CHIAVE_RIMODULAZIONE_DTO = "CHIAVE_RIMODULAZIONE_DTO";
	public static final String CHIAVE_PROPOSTA_RIMODULAZIONE_DTO = "CHIAVE_PROPOSTA_RIMODULAZIONE_DTO";
	public static final String CHIAVE_SEDE_LEGALE = "CHIAVE_SEDE_LEGALE";
	public static final String CHIAVE_SEDE_INTERVENTO = "CHIAVE_SEDE_INTERVENTO";
	public static final String CHIAVE_SEDE_AMMINISTRATIVA = "CHIAVE_SEDE_AMMINISTRATIVA";
	public static final String CHIAVE_IS_BOZZA = "CHIAVE_IS_BOZZA";
	public static final String CHIAVE_ENTE_BENEFICIARIO = "CHIAVE_ENTE_BENFICIARIO";
	public static final String CHIAVE_PROGRAMMA = "CHIAVE_PROGRAMMA";
	public static final String CHIAVE_ALLEGATI = "CHIAVE_ALLEGATI";
	/*
	 * CHIAVI DICHIARAZIONE DI SPESA
	 */

	public static final String CHIAVE_DS_DICHIARAZIONE_DI_SPESA = "CHIAVE_DS_DICHIARAZIONE_DI_SPESA";
	public static final String CHIAVE_ITER = "CHIAVE_ITER";

	/*
	 * CHIAVI RIMODULAZIONE
	 */
	public static final String CHIAVE_CONTOECONOMICO_RIMOD = "CHIAVE_CONTOECONOMICO_RIMOD";
	public static final String CHIAVE_MODALITA_AGEVOLAZIONE = "CHIAVE_MODALITA_AGEVOLAZIONE";
	public static final String CHIAVE_FONTI_FINANZIARIE = "CHIAVE_FONTI_FINANZIARIE";
	public static final String CHIAVE_PROCEDURA_AGGIUDICAZIONE = "CHIAVE_PROCEDURA_AGGIUDICAZIONE";
	public static final String CHIAVE_ID_BENEFICIARIO = "CHIAVE_ID_BENEFICIARIO";
	public static final String CHIAVE_CONTOECONOMICO_PROPOSTA_RIMOD = "CHIAVE_CONTOECONOMICO_PROPOSTA_RIMOD";
	
	/*
	 * CHIAVI RICHIESTA ANTICIPAZIONE ID 20 TIPO DOC RA 
	 */
	public static final String CHIAVE_DATA_RICHIESTA_EROG = "CHIAVE_DATA_RICHIESTA_EROG";
	public static final String CHIAVE_RICHIESTA_EROGAZIONE = "CHIAVE_RICHIESTA_EROGAZIONE";
	public static final String CHIAVE_TOTALE_QUIETANZIATO = "CHIAVE_TOTALE_QUIETANZIATO";
	
	/*
	 * CHIAVI PER COMUNICAZIONE DI FINE PROGETTO
	 */
	public static final String CHIAVE_NOTE = "CHIAVE_NOTE";
	public static final String CHIAVE_PERCENTUALE_SPESA = "CHIAVE_PERCENTUALE_SPESA";
	public static final String CHIAVE_PERCENTUALE_RAGG_SPESA = "CHIAVE_PERCENTUALE_RAGG_SPESA";
	
	
	/*
	 * CHIAVI PER LA COMUNICAZIONE DI RINUNCIA
	 */
	public static final String CHIAVE_COMUNICAZIONE_DI_RINUNCIA = "CHIAVE_COMUNICAZIONE_DI_RINUNCIA";
	public static final String CHIAVE_NOTE_CONTO_ECONOMICO = "CHIAVE_NOTE_CONTO_ECONOMICO";
	public static final String CHIAVE_NUMERO_DOC = "CHIAVE_NUMERO_DOC";
	
	// +Green
	public static final String CHIAVE_ID_PROGETTO_PIU_GREEN = "CHIAVE_ID_PROGETTO_PIU_GREEN";
	
	/*
	 * CULTURA
	 */

	public static final String CHIAVE_IVA_CULTURA = "CHIAVE_IVA_CULTURA";
	public static final String CHIAVE_RITENUTA_ACCONTO_CULTURA = "CHIAVE_RITENUTA_ACCONTO_CULTURA";
	public static final String CHIAVE_ORGANI_CULTURA = "CHIAVE_ORGANI_CULTURA";
	public static final String CHIAVE_DOCUMENTO_UNICO_CULTURA = "CHIAVE_DOCUMENTO_UNICO_CULTURA ";
	public static final String CHIAVE_RICHIESTA_CONTRIBUTI_CULTURA = "CHIAVE_RICHIESTA_CONTRIBUTI_CULTURA";
	public static final String CHIAVE_DENOMINAZIONE_SETTORE_CULTURA = "CHIAVE_DENOMINAZIONE_SETTORE_CULTURA";
	public static final String CHIAVE_RICHIESTA_CONTRIBUTI_STATALI_CULTURA = "CHIAVE_RICHIESTA_CONTRIBUTI_STATALI_CULTURA";
	public static final String CHIAVE_DENOMINAZIONE_SETTORE_STATALI_CULTURA = "CHIAVE_DENOMINAZIONE_SETTORE_STATALI_CULTURA";
	public static final String CHIAVE_EVENTUALI_CONTRIBUTI_CULTURA = "CHIAVE_EVENTUALI_CONTRIBUTI_CULTURA";
	public static final String CHIAVE_VOCI_DI_ENTRATA = "CHIAVE_VOCI_DI_ENTRATA";

	private final Map<String, Object> mapChiavi = new HashMap<String, Object>();
	private String tipoModello;



	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setSedeManager(SedeManager sedeManager) {
		this.sedeManager = sedeManager;
	}

	public SedeManager getSedeManager() {
		return sedeManager;
	}

	public void addChiave(String chiave, Object value) {
		mapChiavi.put(chiave, value);
	}

	public Object getChiave(String chiave) {
		return mapChiavi.get(chiave);
	}

	public void setDynamicTemplateManager(
			DynamicTemplateManager dynamicTemplateManager) {
		this.dynamicTemplateManager = dynamicTemplateManager;
	}

	public DynamicTemplateManager getDynamicTemplateManager() {
		return dynamicTemplateManager;
	}

	public void setActualTemplateVO(TemplateJasperVO actualTemplateVO) {
		this.actualTemplateVO = actualTemplateVO;
	}

	public TemplateJasperVO getActualTemplateVO() {
		return actualTemplateVO;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setEnteManager(EnteManager enteManager) {
		this.enteManager = enteManager;
	}

	public EnteManager getEnteManager() {
		return enteManager;
	}

	public void setDichiarazioneDiSpesaManager(
			DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
		this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
	}

	public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
		return dichiarazioneDiSpesaManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setTemplateDbManager(TemplateDbManager templateDbManager) {
		this.templateDbManager = templateDbManager;
	}

	public TemplateDbManager getTemplateDbManager() {
		return templateDbManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}


	private <T extends BeneficiarioDTO> BeneficiarioDTO getBeneficiario() {
		return (T) getChiave(CHIAVE_BENEFICIARIO);		
	}

	/**
	 * Gestisce null,Double,Date,Long e aggiunge un padding per evitare l'
	 * "a capo" dentro il report
	 */
	private Object wrap(Object value) {
		if (!ObjectUtil.isNull(value)) {
			if (value instanceof Date) {
				value = DateUtil.getDate((Date) value);
			} else if (value instanceof Double) {
				value = NumberUtil.getStringValueItalianFormat((Double) value);
			} else if (value instanceof Long) {
				value = (Long) value;
			}
		}
		return ObjectUtil.nvl(value, " ") + PADDING;
	}

	/** M.E. � una grandissima porcata ma non ho trovato ancora una soluzione per lasciare intatto 
	 * il sistema implementato prima per gli allegati.
	 * La soluzione � quella di ricreare il subreport per gli allegati, di iniettare i valori attesi nel subreport, 
	 * e di inserire il subreport generato all'interno del report master.
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneAll(Map<String, Object> reportParameters) throws Exception {

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/
		List<TipoAllegatoDTO> tipoAllegati = (ArrayList<TipoAllegatoDTO>) getChiave(CHIAVE_ALLEGATI);

		MacroSezione macroSezione = templateDbManager.findMacroSezione(getActualTemplateVO().getIdTipoDocumentoIndex(), 
				getActualTemplateVO().getProgrBandolineaIntervento(), 
				getActualTemplateVO().getIdMacroSezioneModulo());
		
		MicroSezione microNoAllegatiPerCategoria = new MicroSezione();
		
		microNoAllegatiPerCategoria.setTesto("Nessun allegato per questa categoria.");
		//TODO: filtra le le microSezioni
		List<MicroSezione> microSezioni = new ArrayList<MicroSezione>();
		
		//microSezioni.add(macroSezione.getMicroSezioni()[0]);
		String spanToRemove = "<span class='alt'>[__]</span>";
		
		if(tipoAllegati == null || tipoAllegati.isEmpty()){
			logger.info("popolaSezioneAll(): tipoAllegati VUOTO");
			microSezioni.add(microNoAllegatiPerCategoria);
		} else {
			logger.info("popolaSezioneAll(): tipoAllegati.size = "+tipoAllegati.size());
			
			MicroSezione titoloSezione = new MicroSezione();
			titoloSezione.setTesto(tipoAllegati.get(0).getDescTipoAllegato());
			microSezioni.add(titoloSezione);

			for(int i = 1; i < tipoAllegati.size(); i++){
				logger.info("popolaSezioneAll(): tipoAllegato = "+tipoAllegati.get(i).getFlagAllegato()+" - "+tipoAllegati.get(i).getDescTipoAllegato());
				if(!tipoAllegati.get(i).getDescTipoAllegato().contains("noallegati")){
					MicroSezione micro = new MicroSezione();
					//Rimuovo lo <span class='alt'>[__]</span>
					String testo = tipoAllegati.get(i).getDescTipoAllegato().startsWith(spanToRemove) ?
							tipoAllegati.get(i).getDescTipoAllegato().substring(spanToRemove.length()) : tipoAllegati.get(i).getDescTipoAllegato();

							micro.setTesto(testo);
							microSezioni.add(micro);

							if(tipoAllegati.get(i).getDescTipoAllegato().startsWith("<b>")){
								if(i+1 < tipoAllegati.size()){
									if(tipoAllegati.get(i+1).getDescTipoAllegato().startsWith("<b>")){
										microSezioni.add(microNoAllegatiPerCategoria);
									}
								}else
									microSezioni.add(microNoAllegatiPerCategoria);
							}
				}
			}
		}
		
		macroSezione.setMicroSezioni(microSezioni.toArray(new MicroSezione[microSezioni.size()]));
		
		JasperReport report = getDynamicTemplateManager().crateSezioneAlt(macroSezione);
		
		reportParameters.put(getActualTemplateVO().getSezReportParamName(), report);
	/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/
	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneBan(Map<String, Object> reportParameters) {

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		Long idSoggettoBeneficiario = (Long) getChiave(CHIAVE_ID_BENEFICIARIO);
		
		/*
		 * Beneficiario
		 */
		BeneficiarioDTO beneficiario = getBeneficiario();
		String intestazione_conto=null;
		if(ObjectUtil.isNull(beneficiario)){
			logger.warn("\n\n\nAttenzione!!! Beneficiario non trovati per progetto "+idProgetto);
		}else{
			 intestazione_conto = beneficiario.getDenominazioneBeneficiario();
		}
		/*
		 * Estremi bancari
		 */
		
		EstremiBancariDTO estremiBancariDTO = getEstremiBancari();
		String iban=null;
		if(ObjectUtil.isNull(estremiBancariDTO)){
			logger.warn("\n\n\nAttenzione!!! Estremi bancari non trovati per progetto "+idProgetto);
		}else{
			iban=estremiBancariDTO.getIban();
		}

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/


		reportParameters.put("intestazione_conto", wrap(intestazione_conto));
		reportParameters.put("codice_iban", wrap(iban));
		logger.debug("iban="+iban);

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/

	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneChiede(Map<String, Object> reportParameters) {

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		
		if (tipoModello.equals(MODELLO_RICHIESTA_ANTICIPAZIONE) ||
			tipoModello.equals(MODELLO_RICHIESTA_II_ACCONTO) ||
			tipoModello.equals(MODELLO_RICHIESTA_ULTERIORE_ACCONTO) ||
			tipoModello.equals(MODELLO_RICHIESTA_SALDO) ) {
			//l'erogazione del $P{causale_erogazione} del contributo/finanziamento concesso (pari al 
			// $P{perc_erogazione}%   del totale spettante) e pari a Euro 
			//$P{imp_richiesto_erogazione}
			String causale_erogazione="";
			Double perc_erogazione=null;
			Double imp_richiesto_erogazione=null;
			RichiestaErogazioneReportDTO richiestaErogazioneReportDTO =(RichiestaErogazioneReportDTO)getChiave(CHIAVE_RICHIESTA_EROGAZIONE);
			if(richiestaErogazioneReportDTO==null){
				logger.warn("\n\n\nAttenzione!!! RichiestaErogazioneReportDTO null,controllare il metodo chiamante");
			}else{
				causale_erogazione=richiestaErogazioneReportDTO.getDescCausaleErogazione();
				perc_erogazione=richiestaErogazioneReportDTO.getPercentualeErogazione();
				imp_richiesto_erogazione=richiestaErogazioneReportDTO.getImportoRichiesto();
			}
					
			reportParameters.put("causale_erogazione",wrap(causale_erogazione));
			reportParameters.put("perc_erogazione",wrap(perc_erogazione));
			reportParameters.put("imp_richiesto_erogazione",wrap(imp_richiesto_erogazione));
			
			impostaSaldo(reportParameters, idProgetto);
			
		}else	if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO) ) {

			impostaSaldo(reportParameters, idProgetto);
		}

	

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/

	}

	private void impostaSaldo(Map<String, Object> reportParameters,
			Long idProgetto) {
		Double perc_erogaz_saldo = 0d;
		Double totaleImportoAgevolato = 0d;

		Double imp_richiesto_saldo=(Double)getChiave(PopolaTemplateManager.CHIAVE_IMPORTO_RICHIESTA_SALDO);
		
		
		BigDecimal percentualeErogazione= new BigDecimal(0);
		List<ModalitaDiAgevolazioneDTO> modalita = null;
		if(!ObjectUtil.isNull(imp_richiesto_saldo)){
			
			modalita = contoEconomicoManager .getModalitaAgevolazione(idProgetto, modalita);

			if (!ObjectUtil.isEmpty(modalita)) {
				
				ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO =
					NumberUtil.accumulate(modalita,"importoAgevolatoUltimo");
				totaleImportoAgevolato=	modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo();
				percentualeErogazione= NumberUtil.percentage(
						imp_richiesto_saldo,
						modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo());
			}
			
			if (imp_richiesto_saldo > 0 && totaleImportoAgevolato!=null &&  totaleImportoAgevolato > 0) {
				perc_erogaz_saldo = (imp_richiesto_saldo / totaleImportoAgevolato) * 100;
			}
		}
		
		
		reportParameters.put("imp_richiesto_saldo",wrap(imp_richiesto_saldo));
		reportParameters.put("perc_erogaz_saldo", wrap(perc_erogaz_saldo));
	}

	

	/**
	 * 
	 * @param reportParameters
	 * @throws ContoEconomicoNonTrovatoException
	 * @throws FormalParameterException 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneCor(Map<String, Object> reportParameters)
			throws ContoEconomicoNonTrovatoException, FormalParameterException {
		

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		Long idSoggettoBeneficiario = (Long) getChiave(CHIAVE_ID_BENEFICIARIO);
		
		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/
		
		/*
		 * Progetto
		 */
		ProgettoDTO progetto = getProgetto();
		
		/*
		 * Bandolinea
		 */
		ProgettoBandoLineaVO progettoBandoLinea = getBandoLinea(idProgetto);
		
		
		/*
		 * ContoEconomico
		 */
		PbandiTContoEconomicoVO contoEconomicoMain = getContoEconomicoManager().findContoEconomicoMainApertoOChiuso(new BigDecimal(idProgetto));

		
		/*
		 * Rappresentante legale
		 */
		RappresentanteLegaleDTO rappresentanteLegale = getRappresentanteLegale();
		
		
		/*
		 * Beneficiario
		 */
		BeneficiarioDTO beneficiario = getBeneficiario();
		
		/*
		 * Estremi provvedimento
		 */
		String estremi_provvedimento = contoEconomicoMain.getRiferimento();
		
		/*
		 * Dati bandolinea
		 */
		reportParameters.put("nome_bando_linea",wrap(progettoBandoLinea.getDescrizioneBando()));
		
		/*
		 * Dati contoeconomico
		 */
		reportParameters.put("estremi_provvedimento",wrap(estremi_provvedimento));
		
		
		Date data_concessione=progetto.getDataConcessione();
		reportParameters.put("data_concessione",wrap(data_concessione));
		
		/*
		 * Dati del progetto
		 */
		reportParameters.put("titolo_progetto",wrap(progetto.getTitoloProgetto()));
		reportParameters.put("codice_visualizzato",wrap(progetto.getCodiceVisualizzato()));
		reportParameters.put("codice_cup", wrap(progetto.getCup()));

		/*
		 * Dati del rappresentante legale
		 */
		String nome = rappresentanteLegale.getNome() != null ? rappresentanteLegale.getNome() : "";
		String cognome = rappresentanteLegale.getCognome() != null ? rappresentanteLegale.getCognome() : "";
		reportParameters.put("rappresentante_legale",wrap(nome + " " + cognome));
		reportParameters.put("comune_nascita",wrap(rappresentanteLegale.getLuogoNascita()));
		reportParameters.put("prov_nascita", "  ");// data la complessita della
													// query conviene lasciarla
													// accorpata al comune
													// nascita
		reportParameters.put("data_nascita",
				wrap(rappresentanteLegale.getDataNascita()));
		reportParameters.put("comune_residenza",
				wrap(rappresentanteLegale.getComuneResidenza()));
		reportParameters.put("provincia_residenza",
				wrap(rappresentanteLegale.getProvinciaResidenza()));
		reportParameters.put("indirizzo_residenza",
				wrap(rappresentanteLegale.getIndirizzoResidenza()));
		
		
		
		/*
		 * Dati del beneficiario
		 */
		reportParameters.put("denominazione_ente",wrap(beneficiario.getDenominazioneBeneficiario()));

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/
		// presente in pbservizit
		// PK : estraggo il valore da mettere nel campo "contributo_definitivo"
		ContoEconomicoConStatoContribVO contEcoCont = getContoEconomicoManager().findContoEconomicoMainImpContrib(new BigDecimal(idProgetto));
		if(contEcoCont!=null && contEcoCont.getQuotaImportoAgevolato()!=null) {
			logger.info("QuotaImportoAgevolato="+contEcoCont.getQuotaImportoAgevolato());
			reportParameters.put("contributo_definitivo", wrap(new Double(contEcoCont.getQuotaImportoAgevolato().doubleValue())));
		}else {
			logger.info("contEcoCont NULL");
		}
		// presente in pbweb
		String numeroDomanda = getNumeroDomanda(idProgetto);
		reportParameters.put("numero_domanda", wrap(numeroDomanda));
		
		SedeDTO sedeInt = getSedeIntevento();
		logger.info("getSedeIntevento()="+getSedeIntevento());
		
		String sede_intervento = "";
		if (!ObjectUtil.isNull(sedeInt)) {
			String indirizzoSede = StringUtil.getStringaVuotaIfNull(sedeInt.getDescIndirizzo());
//					+ " " + StringUtil.getStringaVuotaIfNull(sedeInt.getCivico());
			String localita = StringUtil.getStringaVuotaIfNull(sedeInt.getDescComune())
					+ "(" + StringUtil.getStringaVuotaIfNull(sedeInt.getSiglaProvincia()) + ")";
			sede_intervento = localita + " - " + indirizzoSede;
		}
		logger.info("sede_intervento="+sede_intervento);
		reportParameters.put("sede_intervento", wrap(sede_intervento));
		
		
		EstremiBancariDTO estremiBancariDTO = getEstremiBancari();
		String iban = "";
		if (ObjectUtil.isNull(estremiBancariDTO)) {
			logger.warn("Attenzione!!! Estremi bancari non trovati per progetto " + idProgetto );
		} else {
			iban = estremiBancariDTO.getIban();
		}
		reportParameters.put("codice_iban", wrap(iban));
		logger.debug("iban="+iban);
	}

	private String getNumeroDomanda(Long idProgetto) {
		String numDomanda = null;
		DomandaProgettoVO dom = new DomandaProgettoVO();
		dom.setIdProgetto(new BigDecimal(idProgetto));

		try {
			DomandaProgettoVO prj = genericDAO.findSingleWhere(dom);
			numDomanda = prj.getNumeroDomanda();
			logger.debug("numDomanda="+numDomanda);
		}catch(Exception e) {
			logger.error("Nessun o troppi progetti trovati " + e.getMessage());
		}
		return numDomanda;
	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneDest(Map<String, Object> reportParameters) {
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);

		EnteAppartenenzaDTO destinatario = getEnteBeneficiario(idProgetto);
		
		
		String descEnte  ="";
		String indirizzo ="";
		String cap       ="";
		String comune    ="";
		String siglaProvincia="";
		
		if(ObjectUtil.isNull(destinatario)){
			logger.warn("\n\n\nAttenzione!!! Destinatario non trovato per progetto "+idProgetto+", controllare i dati\n\n\n");
		}else{
			descEnte=destinatario.getDescEnte();
			indirizzo=destinatario.getIndirizzo();
			cap=destinatario.getCap();
			comune=destinatario.getComune();
			siglaProvincia=destinatario.getSiglaProvincia();
		}
		reportParameters.put("ente_direzione", wrap(descEnte));
		reportParameters.put("indirizzo", wrap(indirizzo));
		reportParameters.put("cap", wrap(cap));
		reportParameters.put("comune", wrap(comune));
		reportParameters.put("provincia",wrap(siglaProvincia));
		
		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/

	}

/**
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneDich(Map<String, Object> reportParameters) throws Exception {

		logger.begin();
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		Long idSoggettoBeneficiario = (Long) getChiave(CHIAVE_ID_BENEFICIARIO);

		/*
		 * Bandolinea
		 */
		ProgettoBandoLineaVO progettoBandoLinea = getBandoLinea(idProgetto);
		
		/*
		 * Ente beneficiario
		 */
		EnteAppartenenzaDTO enteBeneficiario = getEnteBeneficiario(idProgetto);
		
		/*
		 * Sede Legale
		 */
		SedeDTO sedeIntervento = getSedeIntevento();
				
		String indirizzoSede = null;
		String localita = null;
		if(!ObjectUtil.isNull(sedeIntervento)){
			indirizzoSede = StringUtil.getStringaVuotaIfNull(sedeIntervento.getDescIndirizzo())
			+ " "+StringUtil.getStringaVuotaIfNull(sedeIntervento.getCivico());
			localita = StringUtil.getStringaVuotaIfNull(sedeIntervento.getDescComune())
			+ "("+StringUtil.getStringaVuotaIfNull(sedeIntervento.getSiglaProvincia())+")";
		}
		
		/*
		 * Sede Amministrativa
		 */
		SedeDTO sedeAmministrativa = getSedeAmministrativa();
		
		String indirizzoSedeAmministrativa = null;
		String localitaAmministrativa = null;
		if(!ObjectUtil.isNull(sedeAmministrativa)){
			indirizzoSedeAmministrativa = StringUtil.getStringaVuotaIfNull(sedeAmministrativa.getDescIndirizzo())
			+ " "+StringUtil.getStringaVuotaIfNull(sedeAmministrativa.getCivico());
			localitaAmministrativa = StringUtil.getStringaVuotaIfNull(sedeAmministrativa.getDescComune())
			+ "("+StringUtil.getStringaVuotaIfNull(sedeAmministrativa.getSiglaProvincia())+")";
		}
		
		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/
		
		 if (tipoModello.equals(MODELLO_DICHIARAZIONE_DI_SPESA)) {
			DichiarazioneDiSpesaDTO dichiarazione = getDichiarazioneDiSpesa();
			
			ProgettoDTO progetto = getProgetto();
			reportParameters.put("totale_spese", wrap(progetto.getTotaleQuietanzato()));
			reportParameters.put("data_spese", wrap(dichiarazione.getDataFineRendicontazione()));
		} else if (tipoModello.equals(MODELLO_RICHIESTA_ANTICIPAZIONE)) {

			RichiestaErogazioneReportDTO richiestaErogazioneReportDTO =(RichiestaErogazioneReportDTO) getChiave(CHIAVE_RICHIESTA_EROGAZIONE);
			
			Date data_inizio_lavori = null;
			String dir_lav = null;
			String resid_dir_lav = null;
			Date data_contratti =  null;
			
			if(richiestaErogazioneReportDTO==null){
				logger.warn("\n\n\nAttenzione!!! Richiesta report  non trovato per progetto "+idProgetto+
						", controllare il metodo chiamante\n\n\n");
			}else{
				data_inizio_lavori = richiestaErogazioneReportDTO.getDataInizioLavori();
				dir_lav = richiestaErogazioneReportDTO.getDirettoreLavori();
				resid_dir_lav = richiestaErogazioneReportDTO.getResidenzaDirettoreLavori();
				data_contratti =  richiestaErogazioneReportDTO.getDataStipulazioneContratti();
			}
			
			reportParameters.put("data_inizio_lavori", wrap(data_inizio_lavori));
			reportParameters.put("dir_lav", wrap(dir_lav));
			reportParameters.put("resid_dir_lav", wrap(resid_dir_lav));
			reportParameters.put("data_contratti", wrap(data_contratti));

		}else if(tipoModello.equals(MODELLO_RICHIESTA_II_ACCONTO) ||
				tipoModello.equals(MODELLO_RICHIESTA_ULTERIORE_ACCONTO) || 
				tipoModello.equals(MODELLO_RICHIESTA_SALDO)){
			Double ammontare_spese  =(Double)getChiave(CHIAVE_TOTALE_QUIETANZIATO);
			Double percentuale_spesa=(Double)getChiave(CHIAVE_PERCENTUALE_SPESA);
//			Indicare la percentuale di soglia di spesa del bando SOLO per ULTERIORE ACCONTO.
//			Tabella di relazione PBANDI_R_BANDO_CAUSALE_EROGAZ.perc_soglia_spesa_quietanzata
			Double perc_raggiung_spesa =(Double)getChiave(CHIAVE_PERCENTUALE_RAGG_SPESA);
			
			
			if(ammontare_spese==null){
				logger.warn("\n\n\nAttenzione! ammontare_spese(totale quietanziato) � null. controllare i dati per il progetto "+idProgetto);
			}
			
			if(percentuale_spesa==null){
				logger.warn("\n\n\nAttenzione! percentuale_spesa � null. controllare i dati per il progetto "+idProgetto);
			}
			
			
			reportParameters.put("ammontare_spese", wrap(ammontare_spese));
			reportParameters.put("percentuale_spesa", wrap(percentuale_spesa));
			reportParameters.put("perc_raggiung_spesa", wrap(perc_raggiung_spesa));
		
		} else if(tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO)){
					                   	                                                                           
			DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = getDichiarazioneFinale(idProgetto);
			
			Date dtDichiarazione=null;
			
			if (dichiarazioneFinale != null) {
				dtDichiarazione=dichiarazioneFinale.getDtDichiarazione();

			}else{
				logger.warn("\n\n\nAttenzione!!! Non trovata dichiarazione finale per progetto "+idProgetto+" ,controllare i dati\n\n\n");
			}
			Double importo_finale_spesa =(Double)getChiave(CHIAVE_TOTALE_QUIETANZIATO);
			
			reportParameters.put("data_finale_spesa", wrap(dtDichiarazione));
			reportParameters.put("importo_finale_spesa", wrap(importo_finale_spesa));
		} else if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_RINUNCIA)) {
			
			DichiarazioneDiRinunciaDTO rinuncia = (DichiarazioneDiRinunciaDTO) getChiave(CHIAVE_COMUNICAZIONE_DI_RINUNCIA);
			
			Long ggRestituito = rinuncia == null ? null : rinuncia.getGiorniRinuncia();
			Double importoDaRestituire = rinuncia == null ? null : rinuncia.getImportoDaRestituire();
			reportParameters.put("gg_restituito", wrap(ggRestituito));
			reportParameters.put("imp_restituito", wrap(importoDaRestituire));
			
		}

		
		/*
		 * Beneficiario
		 */
		String denomBeneficiario="";
		BeneficiarioDTO beneficiario = getBeneficiario();
		if(ObjectUtil.isNull(beneficiario)){
			logger.warn("\n\n\nAttenzione!!! Beneficiario non trovato per progetto "+idProgetto+", controllare i dati\n\n\n");
		}else{
			denomBeneficiario=beneficiario.getDenominazioneBeneficiario();
		}
		
		
		
		reportParameters.put("nome_bando_linea", wrap(progettoBandoLinea.getDescrizioneBando()));
		reportParameters.put("denominazione_ente", wrap(denomBeneficiario));
		reportParameters.put("sede_legale", wrap(indirizzoSede));
		reportParameters.put("localita_legale", wrap(localita));
		
		/*
		 * SEDE AMMINISTRATIVA 
		 */
		reportParameters.put("sede_amm", wrap(indirizzoSedeAmministrativa));
		reportParameters.put("localita_amm", wrap(localitaAmministrativa));
		
		
		String descEnteTesoreria = enteBeneficiario == null ? "":enteBeneficiario.getDescEnte();
		reportParameters.put("ente_tesoreria", wrap(descEnteTesoreria));

		
		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/
		logger.end();
	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneFir(Map<String, Object> reportParameters) {

	}
	
	@SuppressWarnings("unused")
	private void popolaSezioneTabaffsti(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
		RichiestaErogazioneReportDTO richiestaErogazioneReportDTO = (RichiestaErogazioneReportDTO) getChiave(CHIAVE_RICHIESTA_EROGAZIONE);
		
		// metodo che popola RichiestaErogazioneServTecIng.jrxml
		
		if(richiestaErogazioneReportDTO!=null) {
			logger.info("NumeroRichiestaErogazione="+richiestaErogazioneReportDTO.getNumeroRichiestaErogazione());
		}else {
			logger.info("richiestaErogazioneReportDTO NULLO");
		}
		// qui estraggo i valori per popolare la tabella Affidamento dei servizi tecnici di ingegneria
		
		PbandiTAffidServtecArVO r = new PbandiTAffidServtecArVO();
		r.setFlagAffidServtec("S");
		r.setIdRichiestaErogazione(richiestaErogazioneReportDTO.getNumeroRichiestaErogazione());
		
		List<PbandiTAffidServtecArVO> affidamentoservizio = genericDAO.findListWhere(r);
		if(affidamentoservizio!=null) {
			logger.info("affidamentoservizio.size="+affidamentoservizio.size());
			for (PbandiTAffidServtecArVO item : affidamentoservizio) {
				logger.info("item.Fornitore="+item.getFornitore());
			}
		}else {
			logger.info("affidamentoservizio NULLO");
		}
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(affidamentoservizio);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,PbandiTAffidServtecArVO.class,
				getActualTemplateVO(), reportParameters);
		logger.end();
	}
	
	
	@SuppressWarnings("unused")
	private void popolaSezioneTabafflfs(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
		
		// metodo che popola RichiestaErogazioneLavFornServ.jrxml
		RichiestaErogazioneReportDTO richiestaErogazioneReportDTO = (RichiestaErogazioneReportDTO) getChiave(CHIAVE_RICHIESTA_EROGAZIONE);
		
		if(richiestaErogazioneReportDTO!=null) {
			logger.info("NumeroRichiestaErogazione="+richiestaErogazioneReportDTO.getNumeroRichiestaErogazione());
		}else {
			logger.info("richiestaErogazioneReportDTO NULLO");
		}
		
		// qui estraggo i valori per popolare la tabella Affidamento dei lavori/forniture/servizi
		PbandiTAffidServtecArVO r = new PbandiTAffidServtecArVO();
		r.setFlagAffidServtec("L");
		r.setIdRichiestaErogazione(richiestaErogazioneReportDTO.getNumeroRichiestaErogazione());
		
		List<PbandiTAffidServtecArVO> affidamentoservizio = genericDAO.findListWhere(r);
		if(affidamentoservizio!=null) {
			logger.info("affidamentoservizio.size="+affidamentoservizio.size());
			for (PbandiTAffidServtecArVO item : affidamentoservizio) {
				logger.info("item.Fornitore="+item.getFornitore());
			}
		}else {
			logger.info("affidamentoservizio NULLO");
		}
		
		JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(affidamentoservizio);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource2,PbandiTAffidServtecArVO.class,
				getActualTemplateVO(), reportParameters);
		logger.end();
	}

	@SuppressWarnings("unused")
	private void popolaSezioneTabvocosto(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
		
		// metodo che popola VociCosto.jrxml
		
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		logger.info("idProgetto="+idProgetto);
		
//		//PBANDI_T_CE_ALTRI_COSTI
		PbandiTCeAltriCostiVO tce = new PbandiTCeAltriCostiVO();
		tce.setIdProgetto(idProgetto);
		
		List<PbandiTCeAltriCostiVO> listaTce = genericDAO.findListWhere(tce);
		if(listaTce!=null) {
			logger.info("listaTce.size="+listaTce.size());
			for (PbandiTCeAltriCostiVO item : listaTce) {
				logger.info("item.DescBreveCeAltriCosti="+item.getIdDCeAltriCosti() +", item.DescBreveCeAltriCosti="+item.getDescBreveCeAltriCosti());
			}
		}else {
			logger.info("listaTce NULLO");
		}

		JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(listaTce);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource2,PbandiTCeAltriCostiVO.class,
				getActualTemplateVO(), reportParameters);
		logger.end();
				
	}
	
	@SuppressWarnings("unused")
	private void popolaSezioneALLST(Map<String, Object> reportParameters) {
		logger.info("popolaSezioneALLST");
		// PK : questo metodo serve per visualizzare la sezione degli allegati quando non ci sono allegati ma solo cose statiche del tipo
//		 DICHIARA
//			la necessita di apportare modifiche al Progetto ammesso a finanziamento, con specifico riferimento a: 
//			- A. Quadro Economico
//			- B. Cronoprogramma
//			...
		
	}
	
	
	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneInfo(Map<String, Object> reportParameters) {

	}

	/**
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneInt(Map<String, Object> reportParameters) throws Exception {

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/

		logger.info("popolaSezioneInt(): idProgetto = "+idProgetto+"; tipoModello = "+tipoModello);
		
		if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO)) {
			
			DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = getDichiarazioneFinale(idProgetto);
			
			BigDecimal idDichiarazioneSpesa = new BigDecimal(0);
			Date dtDichiarazione=null;
			
			if (dichiarazioneFinale != null) {
				dtDichiarazione=dichiarazioneFinale.getDtDichiarazione();
				idDichiarazioneSpesa=dichiarazioneFinale.getIdDichiarazioneSpesa();
				logger.info("popolaSezioneInt(): id = "+idDichiarazioneSpesa);
			}else{
				logger.warn("\n\n\nAttenzione!!! Non trovata dichiarazione finale per progetto "+idProgetto+" ,controllare i dati\n\n\n");
			}
			
			// Jira PBANDI-2720: in caso di CFP +Green, si aggiunge "C" all'id della DS.
			String idDS = "0";
			if (idDichiarazioneSpesa != null) {
				idDS = ""+idDichiarazioneSpesa.intValue();
				if (getChiave(CHIAVE_ID_PROGETTO_PIU_GREEN) != null) {
					idDS = idDS + "C";
				}
				logger.info("\n\npopolaSezioneInt(): CHIAVE_ID_PROGETTO_PIU_GREEN = "+getChiave(CHIAVE_ID_PROGETTO_PIU_GREEN));
				logger.info("\n\npopolaSezioneInt(): idDichiarazioneSpesa = "+idDichiarazioneSpesa);
				logger.info("\n\npopolaSezioneInt(): idDS = "+idDS);
			}
			logger.info("popolaSezioneInt(): numero_dich_spesa_finale = "+idDS);

			//reportParameters.put("numero_dich_spesa_finale",wrap(idDichiarazioneSpesa));  codice pre jira PBANDI-2720.
			reportParameters.put("numero_dich_spesa_finale",wrap(idDS));
			
			reportParameters.put("data_dich_spesa_finale",
					wrap(dtDichiarazione));
			
		} else if (tipoModello.equals(MODELLO_DICHIARAZIONE_DI_SPESA)) {
			DichiarazioneDiSpesaDTO dichiarazione = getDichiarazioneDiSpesa();
			Date dtAl=null;
			String numero=null;
			String descTipo=null;
			if (dichiarazione != null) {
				dtAl=dichiarazione.getDataAl();
				numero=dichiarazione.getNumero();
				descTipo=dichiarazione.getDescTipoDichiarazione();
			}else{
				logger.warn("\n\n\nAttenzione!!! Non trovata dichiarazione  per progetto "+idProgetto+" ,controllare i dati\n\n\n");
			}
			reportParameters.put("numero_dich_spesa", wrap(numero));
			reportParameters.put("tipo_dich_spesa", wrap(descTipo));
			reportParameters.put("data_dich_spesa",wrap(dtAl));
			
			
			// PK - Sett 2023 - per bandi PNRR Welfare Abitativo

			IterVO iterVo = getIterVO(idProgetto);
			logger.info(" iterVo= " + iterVo);
			
			String codiceIgrue = null;
			String percentualeImpContributo = null;
			if(iterVo!=null) {
				codiceIgrue = iterVo.getCodIgrueT35();
				percentualeImpContributo = iterVo.getPercImportoContrib();
			}
			
			logger.info("popolaSezioneInt(): codiceIgrue="+codiceIgrue);
			logger.info("popolaSezioneInt(): percentualeImpContributo="+percentualeImpContributo);
			
			reportParameters.put("codiceIgrue",codiceIgrue);  
			reportParameters.put("percentualeImpContributo",percentualeImpContributo);
			
		}else if (tipoModello.equals(MODELLO_RIMODULAZIONE)) {
			
			DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazione = (DatiPerConclusioneRimodulazioneDTO)
				getChiave(CHIAVE_RIMODULAZIONE_DTO);
			
			String ribasso_asta="";
			String riferimento = datiPerConclusioneRimodulazione.getRiferimento();			
			if (ObjectUtil.isEmpty(riferimento))
				riferimento = "Nessuno";
			
			ContoEconomicoConStatoVO contoEconomicoLocalCopyIstr = (ContoEconomicoConStatoVO)
			getChiave(CHIAVE_CONTOECONOMICO_COPY_ISTR);
			
			if(ObjectUtil.isNull(contoEconomicoLocalCopyIstr)){
				logger.warn("\n\nATTENZIONE!!!!Controllare i dati,non � stato trovato il conto economico local copy istr per il progetto "+idProgetto+"\n\n\n");
				return;
			}
			Date  data_rimodulazione=contoEconomicoLocalCopyIstr.getDtFineValidita();
			 
			
			reportParameters.put("data_rimodulazione", wrap(data_rimodulazione));
			reportParameters.put("riferimento", wrap(riferimento));

//			Indica se la conclusione della rimodulazione del conto economico effettuata
//			dall�Istruttore � per un ribasso d'asta [OPECON004]
//			Accedere alla tabella PBANDI_T_RIBASSO_ASTA con l�identificativo del progetto
//			e l�identificativo dell�istanza local copy del conto economico in rimodulazione.
//			Se si � trovata un�istanza, indicare il testo: �per ribasso d'asta
//			al nn,nn %� dove nn,nn � la percentuale del ribasso d�asta.
//			Attributo percentuale della tabella PB8697
			//ANDI_T_RIBASSO_ASTA
			
			ribasso_asta = getPercentualeRibassoDasta(
					contoEconomicoLocalCopyIstr);
			reportParameters.put("ribasso_asta", wrap(ribasso_asta));
		}else if (tipoModello.equals(MODELLO_PROPOSTA_DI_RIMODULAZIONE)) {
			
			PropostaRimodulazioneReportDTO datiPerConclusioneRimodulazione = (PropostaRimodulazioneReportDTO)
				getChiave(CHIAVE_PROPOSTA_RIMODULAZIONE_DTO);
			
			
			
			ContoEconomicoConStatoVO contoEconomicoLocalCopyBen = (ContoEconomicoConStatoVO)
			getChiave(CHIAVE_CONTOECONOMICO_PROPOSTA_RIMOD);
			
			if(ObjectUtil.isNull(contoEconomicoLocalCopyBen)){
				logger.warn("\n\nATTENZIONE!!!!Controllare i dati,non � stato trovato il conto economico localcopy beneficiario per il progetto "+idProgetto+"\n\n\n");
				return;
			}
			Date  data_proposta=contoEconomicoLocalCopyBen.getDtFineValidita();
			 
			
			reportParameters.put("data_proposta", wrap(data_proposta));
			
            // per il beneficiaro non c'e' i lriferimento
			//reportParameters.put("riferimento", wrap(riferimento));


			String ribasso_asta = getPercentualeRibassoDasta(
					contoEconomicoLocalCopyBen);
			reportParameters.put("ribasso_asta", wrap(ribasso_asta));
		}else if (tipoModello.equals(MODELLO_RICHIESTA_ANTICIPAZIONE) ||
				tipoModello.equals(MODELLO_RICHIESTA_II_ACCONTO) ||
				tipoModello.equals(MODELLO_RICHIESTA_ULTERIORE_ACCONTO)||
				tipoModello.equals(MODELLO_RICHIESTA_SALDO)) {
			//Indicare l�identificativo ID_RICHIESTA del file pdf generato [OPEROG001]
			//Indicare la data della richiesta DATA_RICHIESTA del file pdf generato  [OPEROG001]
			Long numero_richiesta=null;
			RichiestaErogazioneReportDTO richiestaErogazioneReportDTO =(RichiestaErogazioneReportDTO)getChiave(CHIAVE_RICHIESTA_EROGAZIONE);
			if(richiestaErogazioneReportDTO==null){
				logger.warn("\n\n\nAttenzione!!! RichiestaErogazioneReportDTO null,controllare il metodo chiamante");
			}else{
				numero_richiesta=richiestaErogazioneReportDTO.getNumeroRichiestaErogazione();
			}
			Date data_richiesta=(Date)getChiave(CHIAVE_DATA_RICHIESTA_EROG);
			reportParameters.put("numero_richiesta", wrap(numero_richiesta));
			reportParameters.put("data_richiesta", wrap(data_richiesta));
		} else if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_RINUNCIA)) {
			DichiarazioneDiRinunciaDTO rinuncia =  (DichiarazioneDiRinunciaDTO) getChiave(CHIAVE_COMUNICAZIONE_DI_RINUNCIA);
			Date datRinuncia = rinuncia == null ? null : rinuncia.getDataRinuncia();
			Long numeroRinuncia = rinuncia == null ? null : rinuncia.getIdDichiarazione();
			
			reportParameters.put("numero_rinuncia", wrap(numeroRinuncia));
			reportParameters.put("data_rinuncia", wrap(datRinuncia));
		}


		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/
	}

	private String getPercentualeRibassoDasta(ContoEconomicoConStatoVO contoEconomico) {
		String ribasso_asta="";
		PbandiTRibassoAstaVO pbandiTRibassoAstaVO=new PbandiTRibassoAstaVO();
		pbandiTRibassoAstaVO.setIdContoEconomico(contoEconomico.getIdContoEconomico());
		List<PbandiTRibassoAstaVO> listRibassiAsta= genericDAO.findListWhere(pbandiTRibassoAstaVO);
		if(!ObjectUtil.isEmpty(listRibassiAsta)){
			BigDecimal percentuale = listRibassiAsta.get(0).getPercentuale();
			if(!ObjectUtil.isNull(percentuale)){					
				ribasso_asta="per ribasso d'asta al "+NumberUtil.getStringValue(percentuale)+"%";
			}
		}
		return ribasso_asta;
	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneNote1(Map<String, Object> reportParameters) {
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		String nota_aggiuntiva_rimod=null;
		String nota_osservazioni_rimod=null;
		String proc_agg_codice=null;
		String proc_agg_descrizione=null;
		//ProceduraAggiudicazioneProgettoVO procedura = (ProceduraAggiudicazioneProgettoVO)getChiave(CHIAVE_PROCEDURA_AGGIUDICAZIONE);
		List<ProceduraAggiudicazioneProgettoVO> procedureList = (ArrayList<ProceduraAggiudicazioneProgettoVO>)getChiave(CHIAVE_PROCEDURA_AGGIUDICAZIONE);
		
		if (tipoModello.equals(MODELLO_RIMODULAZIONE)) {
			DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazione = (DatiPerConclusioneRimodulazioneDTO)
				getChiave(CHIAVE_RIMODULAZIONE_DTO);
		
			
			nota_aggiuntiva_rimod=datiPerConclusioneRimodulazione.getContrattiDaStipulare();
			
			if(ObjectUtil.isEmpty(nota_aggiuntiva_rimod))
				nota_aggiuntiva_rimod="Nessun contratto/incarico";		
			
			nota_osservazioni_rimod=datiPerConclusioneRimodulazione.getNote();
			
		} 
//		else if (tipoModello.equals(MODELLO_PROPOSTA_DI_RIMODULAZIONE)) {
//			DatiPerInvioPropostaRimodulazioneDTO datiPerConclusioneRimodulazione = (DatiPerInvioPropostaRimodulazioneDTO)
//			getChiave(CHIAVE_PROPOSTA_RIMODULAZIONE_DTO);
		
//			nota_aggiuntiva_rimod=datiPerConclusioneRimodulazione.getContrattiDaStipulare();
//			
//			if(ObjectUtil.isEmpty(nota_aggiuntiva_rimod))
//				nota_aggiuntiva_rimod="Nessun contratto/incarico";		
//			
//			nota_osservazioni_rimod=datiPerConclusioneRimodulazione.getNote();
		
//		}
		/*			Accedere alla tabella PBANDI_T_RIBASSO_ASTA con l�identificativo del progetto
		           e l�identificativo dell�istanza nuova proposta inviata del conto economico .
					Se si � trovata un�istanza, accedere alla tabella PBANDI_T_PROCEDURA_AGGIUDICAZ con l�identificativo della procedura di aggiudicazione (ID_PROCEDURA_AGGIUDICAZIONE)  e indicare il codice della procedura.
					Se valorizzato, indicare PBANDI_T_PROCEDURA_AGGIUDICAZ.cod_proc_agg 
					altrimenti indicare PBANDI_T_PROCEDURA_AGGIUDICAZ.cig_proc_agg
					Se si � trovata un�istanza di ribasso d�asta, indicare la descrizione della procedura di aggiudicazione:
					PBANDI_T_PROCEDURA_AGGIUDICAZ.desc_proc_agg
		*/
		if(ObjectUtil.isNull(procedureList) || procedureList.isEmpty()){
			logger.warn("\n\nATTENZIONE!!!! Controllare i dati,non e' stata trovata la procedura di aggiudicazione per il progetto :  " + idProgetto+"\n\n\n");
		}else{
			ProceduraAggiudicazioneProgettoVO procedura = procedureList.get(0); //PK : perche dovrebbe restituirne piu di una???
			proc_agg_codice= procedura.getCodice();
			proc_agg_descrizione=procedura.getDescProcAgg() ;
		}
		reportParameters.put("nota_aggiuntiva_rimod", wrap(nota_aggiuntiva_rimod));
		reportParameters.put("nota_osservazioni_rimod", wrap(nota_osservazioni_rimod));
		reportParameters.put("proc_agg_codice", wrap(proc_agg_codice));
		reportParameters.put("proc_agg_descrizione", wrap(proc_agg_descrizione));
		
	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneNote2(Map<String, Object> reportParameters) {

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * ************************************/
		// reportParameters.put("xxx",wrap(""));
		
		if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO)) {
			String nota_osservazioni_finale = (String) getChiave(CHIAVE_NOTE);
			reportParameters.put("nota_osservazioni_finale",wrap(nota_osservazioni_finale));
		}

	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneRag(Map<String, Object> reportParameters) {

	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneRiservata(Map<String, Object> reportParameters) {

	}

	@SuppressWarnings("unused")
	private void popolaSezioneDichpnrrwap1(Map<String, Object> reportParameters) throws Exception {
		logger.begin();

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		
		// PK - Sett 2023 - per bandi PNRR Welfare Abitativo

		String estremiAttoAmministrativoWPR1 = " ... ";
		String estremiAttoAmministrativoWPR2 = " ... ";
		String estremiAttoAmministrativoWPR3 = " ... ";
		String estremiAttoAmministrativoWPR4 = " ... ";
		String estremiAttoApprovazioneQTES = " ... ";
	
		String dataFineEffettivaWPR1 = " ... ";
		String dataFineEffettivaWPR2 = " ... ";
		String dataFineEffettivaWPR3 = " ... ";
		String dataFineEffettivaWPR4 = " ... ";
		
		IterVO iterVO = getIterVO(idProgetto);
		logger.info("iterVO="+iterVO);
		
		String codIgrue = null;
		
		if(iterVO!=null && "WAP1".equals(iterVO.getCodIgrueT35())){
			
			logger.info("CodIgrueT35=WAP1, idProgetto="+idProgetto + " , IdIter="+iterVO.getIdIter());
			
			codIgrue  = "WAP1";
			
			ProgettoFaseMonitVO filtroPrj = new ProgettoFaseMonitVO();
			filtroPrj.setIdProgetto(new BigDecimal(idProgetto));
			filtroPrj.setIdIter(iterVO.getIdIter().longValue());
			
			try {
				filtroPrj.setCodIgrueT35("WPR1");
				
				ProgettoFaseMonitVO prj = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj="+prj);
				estremiAttoAmministrativoWPR1 = prj.getEstremiAttoAmministrativo();
				dataFineEffettivaWPR1 = DateUtil.getDate(prj.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj, e="+e.getMessage());
			}
			
			try {
				filtroPrj.setCodIgrueT35("WPR2");
				
				ProgettoFaseMonitVO prj2 = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj2="+prj2);
				estremiAttoAmministrativoWPR2 = prj2.getEstremiAttoAmministrativo();
				dataFineEffettivaWPR2 = DateUtil.getDate(prj2.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj2, e="+e.getMessage());
			}
			
			try {
				filtroPrj.setCodIgrueT35("WPR3");
				
				ProgettoFaseMonitVO prj3 = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj3="+prj3);
				estremiAttoAmministrativoWPR3 = prj3.getEstremiAttoAmministrativo();
				dataFineEffettivaWPR3 = DateUtil.getDate(prj3.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj3, e="+e.getMessage());
			}
			
			try {
				filtroPrj.setCodIgrueT35("WPR4");
				
				ProgettoFaseMonitVO prj4 = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj4="+prj4);
				estremiAttoAmministrativoWPR4 = prj4.getEstremiAttoAmministrativo();
				dataFineEffettivaWPR4 = DateUtil.getDate(prj4.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj4, e="+e.getMessage());
			}
			
			try {
				
				PbandiTDatiProgettoQtesVO filtroQtes = new PbandiTDatiProgettoQtesVO();
				filtroQtes.setIdProgetto(new BigDecimal(idProgetto));
				filtroQtes.setIdColonnaQtes(new BigDecimal(1));
				
				PbandiTDatiProgettoQtesVO prjQtes = genericDAO.findSingleWhere(filtroQtes);
				logger.info("prjQtes="+prjQtes);
				estremiAttoApprovazioneQTES = prjQtes.getEstremiAttoApprovazione();
				
			}catch(Exception e){
				logger.warn(" problemi prjQtes, e="+e.getMessage());
			}
			
		}
		
		reportParameters.put("codiceIgrue", codIgrue);
		reportParameters.put("estremiAttoAmministrativoWPR1", estremiAttoAmministrativoWPR1);
		reportParameters.put("dataFineEffettivaWPR1", dataFineEffettivaWPR1);
		reportParameters.put("estremiAttoAmministrativoWPR2", estremiAttoAmministrativoWPR2);
		reportParameters.put("dataFineEffettivaWPR2", dataFineEffettivaWPR2);
		reportParameters.put("estremiAttoAmministrativoWPR3", estremiAttoAmministrativoWPR3);
		reportParameters.put("dataFineEffettivaWPR3", dataFineEffettivaWPR3);
		reportParameters.put("estremiAttoAmministrativoWPR4", estremiAttoAmministrativoWPR4);
		reportParameters.put("dataFineEffettivaWPR4", dataFineEffettivaWPR4);
		reportParameters.put("estremiAttoApprovazioneQTES", estremiAttoApprovazioneQTES);
		
		logger.end();
	}
	
	private IterVO getIterVO(Long idProgetto) {
		IterVO iterVo = (IterVO)getChiave(CHIAVE_ITER);
		
		if(iterVo==null || (iterVo!=null && iterVo.getIdProgetto()!=idProgetto)) {
			//iterVO in sessione nullo o derivato da un progetto precedente, ricalcolo

			try {
				IterVO filtroIter = new IterVO();
				filtroIter.setIdProgetto(idProgetto);
				List<IterVO> iterList = genericDAO.findListWhere(filtroIter);
				
				logger.info(" iterList="+iterList);
				if(iterList!=null && !iterList.isEmpty()) {
					iterVo = iterList.get(0);
					addChiave(CHIAVE_ITER,iterVo);
					logger.info(" iterVo.getCodIgrueT35()= " + iterVo.getCodIgrueT35());
					logger.info(" iterVo.getIdProgetto()= " + iterVo.getIdProgetto());
				}
			}catch(Exception e) {
				logger.warn(" iterVo non trovato, e="+e.getMessage());
			}
		}
		return iterVo;
	}

	@SuppressWarnings("unused")
	private void popolaSezioneDichpnrrwac1(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
		
		// PK - Sett 2023 - per bandi PNRR Welfare Abitativo
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		IterVO iterVo = getIterVO(idProgetto);
		logger.info(" iterVo= " + iterVo);
		
		String codiceIgrue = null;
		if(iterVo!=null) {
			codiceIgrue = iterVo.getCodIgrueT35();
		}
		
		String estremiAttoApprovazione = " ... "; //WAP1
		String dataFineEffettivaWC11 = " ... "; //WAC1
		String dataFineEffettivaWC12 = " ... ";
		String dataFineEffettivaWC21 = " ... "; //WAC2
		String dataFineEffettivaWC31 = " ... "; //WAC3
		String dataFineEffettivaWC32 = " ... ";
		String attoAmministrativoQTES = " ... ";
		String attoAmministrativoQTESccc = " ... ";
		
		if(codiceIgrue!=null && "WAP1".equals(codiceIgrue)) {
			
			try {
				PbandiTDatiProgettoQtesVO filtroQtes = new PbandiTDatiProgettoQtesVO();
				filtroQtes.setIdProgetto(new BigDecimal(idProgetto));
				filtroQtes.setIdColonnaQtes(new BigDecimal(2));
				
				PbandiTDatiProgettoQtesVO prjQtes = genericDAO.findSingleWhere(filtroQtes);
				logger.info("prjQtes="+prjQtes);
				estremiAttoApprovazione = prjQtes.getEstremiAttoApprovazione();
				
			}catch(Exception e){
				logger.warn("problemi prjQtes, e="+e.getMessage());
			}
			
			
		} else if(codiceIgrue!=null && "WAC1".equals(codiceIgrue)) {
			
			ProgettoFaseMonitVO filtroPrj = new ProgettoFaseMonitVO();
			filtroPrj.setIdProgetto(new BigDecimal(idProgetto));
			filtroPrj.setIdIter(iterVo.getIdIter().longValue());
			
			try {
				filtroPrj.setCodIgrueT35("WC11");
				
				ProgettoFaseMonitVO prj = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj="+prj);
				dataFineEffettivaWC11 = DateUtil.getDate(prj.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj, e="+e.getMessage());
			}
			
			try {
				filtroPrj.setCodIgrueT35("WC12");
				
				ProgettoFaseMonitVO prj2 = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj2="+prj2);
				dataFineEffettivaWC12 = DateUtil.getDate(prj2.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj2, e="+e.getMessage());
			}
			
		} else if(codiceIgrue!=null && "WAC2".equals(codiceIgrue)) {
			
			ProgettoFaseMonitVO filtroPrj = new ProgettoFaseMonitVO();
			filtroPrj.setIdProgetto(new BigDecimal(idProgetto));
			filtroPrj.setIdIter(iterVo.getIdIter().longValue());
			
			try {
				filtroPrj.setCodIgrueT35("WC21");
				
				ProgettoFaseMonitVO prj = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj="+prj);
				dataFineEffettivaWC21 = DateUtil.getDate(prj.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj, e="+e.getMessage());
			}
			
		} else if(codiceIgrue!=null && "WAC3".equals(codiceIgrue)) {
			
			try {
				PbandiTDatiProgettoQtesVO filtroQtes = new PbandiTDatiProgettoQtesVO();
				filtroQtes.setIdProgetto(new BigDecimal(idProgetto));
				filtroQtes.setIdColonnaQtes(new BigDecimal(3));
				
				PbandiTDatiProgettoQtesVO prjQtes = genericDAO.findSingleWhere(filtroQtes);
				logger.info("prjQtes="+prjQtes);
				attoAmministrativoQTES = prjQtes.getEstremiAttoApprovazione();
				attoAmministrativoQTESccc = prjQtes.getEstremiAttoApprovazioneCcc();
				
			}catch(Exception e){
				logger.warn("problemi prjQtes, e="+e.getMessage());
			}
			
			ProgettoFaseMonitVO filtroPrj = new ProgettoFaseMonitVO();
			filtroPrj.setIdProgetto(new BigDecimal(idProgetto));
			filtroPrj.setIdIter(iterVo.getIdIter().longValue());
			
			try {
				filtroPrj.setCodIgrueT35("WC31");
				
				ProgettoFaseMonitVO prj = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj="+prj);
				dataFineEffettivaWC31 = DateUtil.getDate(prj.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj, e="+e.getMessage());
			}
			
			try {
				filtroPrj.setCodIgrueT35("WC32");
				
				ProgettoFaseMonitVO prj2 = genericDAO.findSingleWhere(filtroPrj);
				logger.info("prj2="+prj2);
				dataFineEffettivaWC32 = DateUtil.getDate(prj2.getDtFineEffettiva());
			}catch(Exception e){
				logger.warn(" problemi prj2, e="+e.getMessage());
			}
		}else {
			logger.info(" nessun codice Igrue corrispondente : codiceIgrue="+codiceIgrue); 
		}
		
		reportParameters.put("codiceIgrue", codiceIgrue);
		reportParameters.put("estremiAttoApprovazione", estremiAttoApprovazione);
		reportParameters.put("dataFineEffettivaWC11", dataFineEffettivaWC11);
		reportParameters.put("dataFineEffettivaWC12", dataFineEffettivaWC12);
		reportParameters.put("dataFineEffettivaWC21", dataFineEffettivaWC21);
		reportParameters.put("dataFineEffettivaWC31", dataFineEffettivaWC31);
		reportParameters.put("dataFineEffettivaWC32", dataFineEffettivaWC32);
		reportParameters.put("attoAmministrativoQTES", attoAmministrativoQTES);
		reportParameters.put("attoAmministrativoQTESccc", attoAmministrativoQTESccc);
		
		logger.end();
	}
	
	@SuppressWarnings("unused")
	private void popolaSezioneDichpnrrwap1crnprg(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
	
		// PK - Sett 2023 - per bandi PNRR Welfare Abitativo
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		IterVO iterVo = getIterVO(idProgetto);
		logger.info(" iterVo= " + iterVo);
		
		String codiceIgrue = null;
		if(iterVo!=null) {
			codiceIgrue = iterVo.getCodIgrueT35();
		}
		
		if("WAP1".equals(codiceIgrue)) {
			// faccio vedere il cronoprogramma
		
			// PK copio da it.csi.pbandi.pbservizit.business.api.impl.CronoProgrammaFasiApiImpl --> getDataCronoprogramma
			// Recupero delle informazioni principali
			List<CronoprogrammaFasiItem> cronoprogrammaFasiItemList = cronoProgrammaDAO.getDataCronoprogramma(idProgetto);
			
			if(cronoprogrammaFasiItemList!=null) {
				logger.info("cronoprogrammaFasiItemList="+cronoprogrammaFasiItemList.toString());
			} else {
				logger.info("cronoprogrammaFasiItemList nullo");
			}
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cronoprogrammaFasiItemList);
			getDynamicTemplateManager().addReportToTemplateJasper(dataSource, it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem.class,
					getActualTemplateVO(), reportParameters);
		}else {
			logger.info("codice IGRUE non consente visualizzazzione della tabella del cronoprogramma");
		}
		
		reportParameters.put("codiceIgrue", codiceIgrue);
		logger.end();
	}
	
	@SuppressWarnings("unused")
	private void popolaSezioneDichpnrrtabce(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
		logger.info("POKE - TABELLA CE");
		
		// PK - Sett 2023 - per bandi PNRR Welfare Abitativo
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		IterVO iterVo = getIterVO(idProgetto);
		logger.info(" iterVo= " + iterVo);
		
		String codiceIgrue = null;
		if(iterVo!=null) {
			codiceIgrue = iterVo.getCodIgrueT35();
		}
		
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO conto = contoEconomicoDAOImpl.trovaContoEconomico(idProgetto);
		if(conto!=null) {
			logger.info("iconto.getImportoSpesaQuietanziata()="+ conto.getImportoSpesaQuietanziata());
			
//			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO[] figl = conto.getFigli();
//			if(figl!=null) {
//				for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO f : figl) {
//					logger.info("figlio: idVoce="+ f.getIdVoce() + ", idTipVoc=" + f.getIdTipologiaVoceDiSpesa());
//					logger.info( "ImportoSpesaQuietanziata=" + f.getImportoSpesaQuietanziata());
//					logger.info( "ImportoSpesaAmmessa=" + f.getImportoSpesaAmmessa());
//					logger.info( "ImportoSpesaRendicontata=" + f.getImportoSpesaRendicontata());
//					logger.info( "ImportoSpesaValidataTotale=" + f.getImportoSpesaValidataTotale());
//				}
//			}
		}else {
			logger.warn("conto nullo");
		}
		
		ArrayList<it.csi.pbandi.pbservizit.dto.contoeconomico.ContoEconomicoItem> itemConto = contoEconomicoDAOImpl.trasformaPerVisualizzazione(conto);
		if(itemConto!=null) {
			logger.info("itemConto="+itemConto.toString());
			for (ContoEconomicoItem item : itemConto) {
				logger.info("item: id="+ item.getId()+", idTipologiaVoceDiSpesa="+item.getIdTipologiaVoceDiSpesa()+
						", importoSpesaQuietanziata="+item.getImportoSpesaQuietanziata()); //importoSpesaQuietanziata
			}
		}
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemConto);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource, it.csi.pbandi.pbservizit.dto.contoeconomico.ContoEconomicoItem.class,
				getActualTemplateVO(), reportParameters);

//TODO PK pulire
/*
		Long idUtente = 27345L;
		String idIride = "AAAAAA00A11O000W/CSI PIEMONTE/DEMO 34/ACTALIS_EU/20210209102032/16";
//		idProgetto = 119577; idUtente = 27345, idIride=AAAAAA00A11O000W/CSI PIEMONTE/DEMO 34/ACTALIS_EU/20210209102032/16>
		InizializzaVisualizzaContoEconomicoDTO inz = contoEconomicoDAOImpl.inizializzaVisualizzaContoEconomico(idProgetto, idUtente, idIride);
		
		ArrayList<ContoEconomicoItem> ce = inz.getContoEconomico();
		if(ce!=null) {
			logger.info("itemConto="+ce.toString());
			for (ContoEconomicoItem item : ce) {
				logger.info("item: id="+ item.getId()+", idTipologiaVoceDiSpesa="+item.getIdTipologiaVoceDiSpesa()+
						", importoSpesaQuietanziata="+item.getImportoSpesaQuietanziata()); //importoSpesaQuietanziata
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ce);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource, it.csi.pbandi.pbservizit.dto.contoeconomico.ContoEconomicoItem.class,
				getActualTemplateVO(), reportParameters);
*/		
		
		reportParameters.put("codiceIgrue", codiceIgrue);
		reportParameters.put("ritenuta_acconto", "ritenuta_acconto_pk");
		logger.end();
	}
	
	
	/**
	 * elenco erogazioni
	 * 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabero(Map<String, Object> reportParameters)
			throws Exception {

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);

		/*************************************
		 * AGGIUNGERE QUA EVENTUALI CHIAVI NEL BISOGNO Object
		 * xxx=(Object)getChiave(CHIAVE_XXX);
		 * ************************************/

		List<ErogazioneReportVO> erogazioni = getErogazioni(idProgetto);


		JRBeanCollectionDataSource erogazioniDS = new JRBeanCollectionDataSource(
				erogazioni);
		
		getDynamicTemplateManager().addReportToTemplateJasper(erogazioniDS,ErogazioneReportVO.class,
				getActualTemplateVO(), reportParameters);

		BigDecimal totaleImportoErogazione = new BigDecimal("0");
		ErogazioneReportVO totale = new ErogazioneReportVO();
		if (!ObjectUtil.isEmpty(erogazioni)) {
			totale = NumberUtil.accumulate(erogazioni, "importoErogazione");
			totaleImportoErogazione = totale.getImportoErogazione();
		}

		reportParameters
				.put("totaleImportoErogazione", totaleImportoErogazione);
		/*************************************
		 * AGGIUNGERE QUA EVENTUALI PARAMETRI NEL BISOGNO
		 * reportParameters.put("xxx",wrap(""));
		 * ************************************/

	}

	/**
	 * dichiarazioni
	 * 
	 * @param reportParameters
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabdic(Map<String, Object> reportParameters)
			throws Exception {
		
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		
		List<DichiarazioneDiSpesaDTO> dichiarazioni = getDichiarazioni(idProgetto);


		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				dichiarazioni);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,DichiarazioneDiSpesaDTO.class,
				getActualTemplateVO(), reportParameters);

	}

	/**
	 * SPESE PAGATE E QUIETANZATE DOCUMENTI CONTABILI
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabspq(Map<String, Object> reportParameters) throws Exception {
		
		// +Green
		// Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);	// codice originale.
		Long idProgetto = this.getIdProgetto();
		logger.debug("[PopolaTemplateManager::popolaSezioneTabspq] idProgetto=" + idProgetto);
		logger.debug("[PopolaTemplateManager::popolaSezioneTabspq] tipoModello=" + tipoModello);

		if (tipoModello.equals(MODELLO_DICHIARAZIONE_DI_SPESA)) {
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesa = getDichiarazioneDiSpesa();
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO> documentiContabili = null;
			if (ObjectUtil.isNull(dichiarazioneDiSpesa)) {
				logger.warn("\n\nATTENZIONE!!! controllare i dati,chiave DichiarazioneDiSpesaDTO null");
			} else {
				documentiContabili = dichiarazioneDiSpesa.getDocumentiContabili();

				for (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO d : documentiContabili) {
					logger.info("IdDocumentoDiSpesa=" + d.getIdDocumentoDiSpesa() + ", Ruolo=" + d.getRuolo()
							+ ", DataFirmaContratto=" + d.getDataFirmaContratto());
				}
			}
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(documentiContabili);
			
			getDynamicTemplateManager().addReportToTemplateJasper(dataSource,it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO.class,getActualTemplateVO(), reportParameters);
		} else {
			logger.debug("[PopolaTemplateManager::popolaSezioneTabspq] else");
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO> documentiContabiliDTO = getDocumentiContabili(idProgetto);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					documentiContabiliDTO);
			
			getDynamicTemplateManager().addReportToTemplateJasper(dataSource,it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO.class,
					getActualTemplateVO(), reportParameters);
		}
	}

	@SuppressWarnings("unused")
	private void popolaSezioneFileallegati(Map<String, Object> reportParameters)throws Exception {
		DichiarazioneDiSpesaDTO dichiarazioneDiSpesa = getDichiarazioneDiSpesa();
		List<FileAllegatoDTO> fileAllegati =null;
		if(ObjectUtil.isNull(dichiarazioneDiSpesa)){
			logger.warn("\n\nATTENZIONE!!! controllare i dati,chiave DichiarazioneDiSpesaDTO null");
		}else{
			fileAllegati = dichiarazioneDiSpesa.getFileAllegati();
		}
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fileAllegati);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,String.class,getActualTemplateVO(), reportParameters);
	
	}

	/**
	 * STATO AVANZAMENTO SPESA QUIETANZATA VOCI DI COSTO
	 * 
	 * @param reportParameters
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabsas(Map<String, Object> reportParameters)
			throws Exception {
		logger.begin();
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);	
		logger.debug("idProgetto=" + idProgetto);

		Long idProgettoPG = (Long) getChiave(CHIAVE_ID_PROGETTO_PIU_GREEN);
		logger.debug("idProgettoPG=" + idProgettoPG);

		List<VoceDiCostoDTO> vociDiCosto = getVociDiCosto(idProgetto);
		logger.debug("vociDiCosto=" + vociDiCosto);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				vociDiCosto);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,VoceDiCostoDTO.class,
				getActualTemplateVO(), reportParameters);
		logger.end();
	}

	private List<VoceDiCostoDTO> getVociDiCosto(Long idProgetto)
			throws Exception {
		List<VoceDiCostoDTO> vociDiCosto = new ArrayList<VoceDiCostoDTO>();
		logger.begin();
		if (tipoModello.equals(MODELLO_DICHIARAZIONE_DI_SPESA)) {
			DichiarazioneDiSpesaDTO dichiarazione = getDichiarazioneDiSpesa();
			vociDiCosto = beanUtil.transformList(dichiarazione.getVociDiCosto(), VoceDiCostoDTO.class);
		} else {
			DichiarazioneDiSpesaConTipoVO dichiarazione = getDichiarazioneFinale(idProgetto);
			if(dichiarazione!=null) {
				logger.debug("dichiarazione.getIdDichiarazioneSpesa="+dichiarazione.getIdDichiarazioneSpesa());
				vociDiCosto = getVociDiCosto(dichiarazione.getIdDichiarazioneSpesa());
			}else {
				logger.debug("dichiarazione NULL");
			}
		}
		logger.end();
		return vociDiCosto;
	}

	/**
	 * RAFFRONTO QUADRO ECONOMICO INTERVENTO/SAL
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabrqe(Map<String, Object> reportParameters) throws Exception {
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		List<ContoEconomicoItemDTO> vociQuadroEconomico = contoEconomicoManager.estraiVociDiSpesaFoglieDaContoEconomicoMaster(NumberUtil.toBigDecimal(idProgetto));
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				vociQuadroEconomico);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,ContoEconomicoItemDTO.class,
				getActualTemplateVO(), reportParameters);
		
	}
	
	
	/**
	 * PROCEDURE DI AGGIUDICAZIONE
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabproagg(Map<String, Object> reportParameters) throws Exception {
	
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		
		List<ProceduraAggiudicazioneDTO> procedureAgg = (ArrayList<ProceduraAggiudicazioneDTO>) getChiave(CHIAVE_PROCEDURA_AGGIUDICAZIONE);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				procedureAgg);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,ProceduraAggiudicazioneDTO.class,
				getActualTemplateVO(), reportParameters);
		
	}
	

	/**
	 * QUADRO RAPPRESENTATIVO GRADO REALIZZAZIONE INTERVENTO
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabqrg(Map<String, Object> reportParameters) throws Exception {
		
		// +Green
		//Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);	// codice originale.
		Long idProgetto = this.getIdProgetto();
		logger.debug("idProgetto=" + idProgetto);
		ContoEconomicoDTO contoEconomicoMaster = contoEconomicoManager
		.findContoEconomicoMasterPotato(new BigDecimal(
				idProgetto));
		logger.debug("contoEconomicoMaster calcolato");

		List<ContoEconomicoItemDTO> contoEconomico=null;
		if(ObjectUtil.isNull(contoEconomicoMaster)){
			logger.warn("\n\n\nATTENZIONE!!! controllare i dati,contoEconomicoMaster non trovato per progetto "+idProgetto+"\n\n\n");
		}else{
			List<ContoEconomicoItemDTO> contoEconomicoReport = new ArrayList<ContoEconomicoItemDTO>();
			contoEconomico=contoEconomicoManager.getContoEconomicoPerReport(
					contoEconomicoMaster,contoEconomicoReport);
		}
		logger.debug("contoEconomicoReport calcolato");

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				contoEconomico);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,ContoEconomicoItemDTO.class,
				getActualTemplateVO(), reportParameters);
	}

	/**
	 * LIQUIDAZIONI
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabliq(Map<String, Object> reportParameters) {
	}

	/**
	 * INDICATORI DI PROGETTO
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabind(Map<String, Object> reportParameters) throws Exception {
		
		// +Green
		// Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);	// codice originale.
		Long idProgetto = this.getIdProgetto();

		List<IndicatoreDomandaVO> indicatori = getIndicatori(idProgetto);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				indicatori);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,IndicatoreDomandaVO.class,
				getActualTemplateVO(), reportParameters);
	}

	/**
	 * FONTI FINANZIARIE
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabfon(Map<String, Object> reportParameters) throws Exception {
		

		List<FonteFinanziaria>fonti =  (List<FonteFinanziaria>)
		getChiave(CHIAVE_FONTI_FINANZIARIE);;

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				fonti);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,FonteFinanziaria.class,
				getActualTemplateVO(), reportParameters);
	}

	/**
	 * QUADRO ECONOMICO INTERVENTO
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabeco(Map<String, Object> reportParameters) throws Exception {
		logger.info("tipoModello="+tipoModello);
		
		if (tipoModello.equals(MODELLO_RIMODULAZIONE)) {
			List<ContoEconomicoItemDTO> contoEconomico = (List<ContoEconomicoItemDTO>)
				getChiave(CHIAVE_CONTOECONOMICO_RIMOD);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					contoEconomico);
			
			getDynamicTemplateManager().addReportToTemplateJasper(dataSource,ContoEconomicoItemDTO.class,
					getActualTemplateVO(), reportParameters);
			
		}else if (tipoModello.equals(MODELLO_PROPOSTA_DI_RIMODULAZIONE)) {
		
			List<ContoEconomicoItemDTO> contoEconomico = (List<ContoEconomicoItemDTO>)
			getChiave(CHIAVE_CONTOECONOMICO_COPY_BEN);
		
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				contoEconomico);
		
			getDynamicTemplateManager().addReportToTemplateJasper(dataSource,ContoEconomicoItemDTO.class,
				getActualTemplateVO(), reportParameters);
		
		} 
	}

	/**
	 * CRONOPROGRAMMA INTERVENTO
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabcro(Map<String, Object> reportParameters) throws Exception {
		
		// +Green
		Long idProgetto = this.getIdProgetto();		

		List<FaseMonitoraggioProgettoVO> fasiMonitoraggio = getFasiMonitoraggio(idProgetto);
				
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				fasiMonitoraggio);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,FaseMonitoraggioProgettoVO.class,
				getActualTemplateVO(), reportParameters);
		
	}


	/**
	 * APPALTI/CONTRATTI/INCARICHI
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabapp(Map<String, Object> reportParameters) throws Exception {

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);

		List<AppaltoDTO> appalti = getAppalti(idProgetto);

		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				appalti);
		
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource,AppaltoDTO.class,
				getActualTemplateVO(), reportParameters);
		
	}
	

	private List<AppaltoDTO> getAppalti(Long idProgetto) {
		AppaltoVO filtro=new AppaltoVO();
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO progetto= (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO) getChiave(CHIAVE_PROGETTO);
		filtro.setIdProgetto(new BigDecimal(progetto.getIdProgetto()));
		List<AppaltoVO> appalti=genericDAO.findListWhere(filtro);
		
		//impresaAppaltatrice	//descTipologiaAppalto	//importo	//iva 	//totale //interventoPisu
		List<AppaltoDTO> ret=new ArrayList<AppaltoDTO>();
		if(!ObjectUtil.isEmpty(appalti)){
			for (AppaltoVO appaltoVO : appalti) {
					AppaltoDTO appaltoDTO=beanUtil.transform(appaltoVO, AppaltoDTO.class);
					BigDecimal importo=appaltoVO.getImporto();
					BigDecimal iva=appaltoVO.getIva();
					BigDecimal totale=importo;
					if(importo!=null){
						if(iva==null)
							iva=new BigDecimal(0);				
						totale=(NumberUtil.sum(importo,iva));
					}
					appaltoDTO.setTotale(NumberUtil.toDouble(totale));
					ret.add(appaltoDTO);
			}
			
		}
		return ret;
	}

	/**
	 * MODALITA AGEVOLAZIONE
	 * 
	 * @param reportParameters
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabage(Map<String, Object> reportParameters) throws Exception {
	
		if (tipoModello.equals(MODELLO_RIMODULAZIONE) ||
				tipoModello.equals(MODELLO_PROPOSTA_DI_RIMODULAZIONE)	) {
			
			
			List <ModalitaAgevolazione> modalitaAgevolazione= (List <ModalitaAgevolazione>)
			getChiave(CHIAVE_MODALITA_AGEVOLAZIONE);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					modalitaAgevolazione);
			
			getDynamicTemplateManager().addReportToTemplateJasper(dataSource,ModalitaAgevolazione.class,
					getActualTemplateVO(), reportParameters);
		}

	}

	/**
	 * 
	 * @param reportParameters
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTit(Map<String, Object> reportParameters) {
	}

	
	@SuppressWarnings("unused")
	private void popolaSezioneVar(Map<String, Object> reportParameters) {
		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		String nota_aggiuntiva_rimod=null;
		String nota_osservazioni_rimod=null;
		String proc_agg_codice=null;
		String proc_agg_descrizione=null;
		if (tipoModello.equals(MODELLO_RIMODULAZIONE) 
				) {
			DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazione = (DatiPerConclusioneRimodulazioneDTO)
				getChiave(CHIAVE_RIMODULAZIONE_DTO);
		
			
			nota_aggiuntiva_rimod=datiPerConclusioneRimodulazione.getContrattiDaStipulare();
			
			if(ObjectUtil.isEmpty(nota_aggiuntiva_rimod))
				nota_aggiuntiva_rimod="Nessun contratto/incarico";		
			
			nota_osservazioni_rimod=datiPerConclusioneRimodulazione.getNote();
			
			/*
			 * Accedere alla tabella PBANDI_T_RIBASSO_ASTA con l'identificativo del progetto
			 * e l'identificativo dell'istanza nuova proposta inviata del conto economico .
			 * Se si e' trovata una istanza, accedere alla tabella
			 * PBANDI_T_PROCEDURA_AGGIUDICAZ con l'identificativo della procedura di
			 * aggiudicazione (ID_PROCEDURA_AGGIUDICAZIONE) e indicare il codice della
			 * procedura.
			 * Se valorizzato, indicare PBANDI_T_PROCEDURA_AGGIUDICAZ.cod_proc_agg
			 * altrimenti indicare PBANDI_T_PROCEDURA_AGGIUDICAZ.cig_proc_agg
			 * Se si e' trovata una istanza di ribasso d'asta, indicare la descrizione della
			 * procedura di aggiudicazione:
			 * PBANDI_T_PROCEDURA_AGGIUDICAZ.desc_proc_agg
			 */
		}else if (tipoModello.equals(MODELLO_PROPOSTA_DI_RIMODULAZIONE) ) {
			PropostaRimodulazioneReportDTO datiPerConclusioneRimodulazione = (PropostaRimodulazioneReportDTO)
			getChiave(CHIAVE_PROPOSTA_RIMODULAZIONE_DTO);
	
			// TODO per adesso e' sempre vuota in attesa di Modificare interfaccia web
			if(ObjectUtil.isEmpty(nota_aggiuntiva_rimod))
				nota_aggiuntiva_rimod="Nessun contratto/incarico";		
			
			nota_osservazioni_rimod=(String)getChiave(CHIAVE_NOTE_CONTO_ECONOMICO);
		}
		List<ProceduraAggiudicazioneProgettoVO> procedura = (ArrayList<ProceduraAggiudicazioneProgettoVO>)
		getChiave(CHIAVE_PROCEDURA_AGGIUDICAZIONE);
		
		if(ObjectUtil.isNull(procedura)){
			logger.warn("\n\nATTENZIONE!!!!Controllare i dati,non � stato trovata la procedura di aggiudicazione per il progetto : "+idProgetto+"\n\n\n");
		}else{
//			proc_agg_codice= procedura.getCodice();
//			proc_agg_descrizione=procedura.getDescProcAgg() ;
		}
		
	
		reportParameters.put("nota_aggiuntiva_rimod", wrap(nota_aggiuntiva_rimod));
		reportParameters.put("nota_osservazioni_rimod", wrap(nota_osservazioni_rimod));
		reportParameters.put("proc_agg_codice", wrap(proc_agg_codice));
		reportParameters.put("proc_agg_descrizione", wrap(proc_agg_descrizione));
	
		
	}
	
	
	/**
	 * 
	 * @param idProgetto
	 * @return
	 */
	private List<DichiarazioneDiSpesaDTO> getDichiarazioni(Long idProgetto) {

		PbandiTDichiarazioneSpesaVO filtro = new PbandiTDichiarazioneSpesaVO();
		filtro.setIdProgetto(new BigDecimal(idProgetto));
		filtro.setAscendentOrder("idDichiarazioneSpesa");
		List<PbandiTDichiarazioneSpesaVO> listVo = genericDAO
				.findListWhere(filtro);
		Map<String, String> map = new HashMap<String, String>();
		map.put("idDichiarazioneSpesa", "numero");
		map.put("dtDichiarazione", "data");
		List<DichiarazioneDiSpesaDTO> dichiarazioni = beanUtil.transformList(
				listVo, DichiarazioneDiSpesaDTO.class, map);
		return dichiarazioni;
	}

	/**
	 * 
	 * @param idProgetto
	 * @return
	 */
	private List<ErogazioneReportVO> getErogazioni(Long idProgetto) {
		ErogazioneReportVO filtroVO = new ErogazioneReportVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setAscendentOrder("idModalitaAgevolazione");

		BigDecimal importoTotaleErogazioni = new BigDecimal(0D);
		List<ErogazioneReportVO> erogazioni = genericDAO
				.findListWhere(filtroVO);
		if (!ObjectUtil.isEmpty(erogazioni)) {
			for (ErogazioneReportVO vo : erogazioni) {
				importoTotaleErogazioni = NumberUtil.sum(
						importoTotaleErogazioni, vo.getImportoErogazione());
			}
		}
		return erogazioni;
	}

	
/**
 * 
 * @param idDocumentiValidi
 * @param dataAl
 * @param idProgetto
 * @return
 */
	private List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO> getDocumentiContabili(
			Long idProgetto
			) {
		logger.begin();

		PbandiTDichiarazioneSpesaVO dichiarazioneFinale = getDichiarazioneFinale(idProgetto);
		logger.debug(" dichiarazioneFinale=" + dichiarazioneFinale);

		List<DocumentoDiSpesaDichiarazioneTotalePagamentiVO> documentiDichFinale = documentoManager.findDocumentiDichiarazione(dichiarazioneFinale.getIdDichiarazioneSpesa());
		logger.debug(" documentiDichFinale=" + documentiDichFinale);

		List<Long> idDocumentiValidi = new ArrayList<Long>();
		List<DocumentoContabileVO> documentiContabiliVO =new ArrayList<DocumentoContabileVO>();
		if(!ObjectUtil.isEmpty(documentiDichFinale)){
			idDocumentiValidi=beanUtil.extractValues(
					documentiDichFinale, "idDocumentoDiSpesa",
				Long.class);
		}
		
		if(!ObjectUtil.isEmpty(idDocumentiValidi)){
			documentiContabiliVO = dichiarazioneDiSpesaManager
			.findDocumentiContabili(
					idProgetto,
					dichiarazioneFinale.getDtDichiarazione(), idDocumentiValidi,
					NumberUtil.toLong(dichiarazioneFinale.getIdDichiarazioneSpesa()));
		}

		if (!ObjectUtil.isEmpty(documentiContabiliVO)) {
			for (DocumentoContabileVO doc : documentiContabiliVO) {
				logger.info("PK -> estraggo i valori Ruolo e DataFirmaContratto, IdDocumentoDiSpesa="
						+ doc.getIdDocumentoDiSpesa());
				if (doc.getIdDocumentoDiSpesa() != null) {
					DocumentoContabileVO dcVo = dichiarazioneDiSpesaManager
							.findRuoloFirmaDocumentiContabili(doc.getIdDocumentoDiSpesa());
					if (dcVo != null) {
						logger.info(
								"Ruolo=" + dcVo.getRuolo() + ", DataFirmaContratto=" + dcVo.getDataFirmaContratto());
						doc.setRuolo(dcVo.getRuolo());
						doc.setDataFirmaContratto(dcVo.getDataFirmaContratto());
					} else {
						logger.warn("DocumentoContabileVO non trovato");
					}
				}
			}
		}

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO[] documentiContabiliDTO=getBeanUtil().transform(documentiContabiliVO,
				it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO.class);
		logger.end();
		return Arrays.asList(documentiContabiliDTO);
	}

	private DichiarazioneDiSpesaConTipoVO getDichiarazioneFinale(Long idProgetto) {
		//logger.info("PRE dichiarazioneFinale = "+dichiarazioneFinale + ",
		// idProgetto="+idProgetto);
		logger.info(" idProgetto=" + idProgetto);

		// if(dichiarazioneFinale==null)
		DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = dichiarazioneDiSpesaManager.getDichiarazioneFinale(idProgetto);
		logger.info("dichiarazioneFinale = " + dichiarazioneFinale);
		return dichiarazioneFinale;
	}

	/**
	 * 
	 * @param idProgetto
	 * @return
	 */
	private List<FaseMonitoraggioProgettoVO> getFasiMonitoraggio(Long idProgetto) {
		
		// PBANDI-2703: Cerco i dati in maniera diversa in base alla programmazione.
		String programmazione = "";
		try {
			programmazione = gestioneCronoprogrammaBusiness.getProgrammazioneByIdProgetto(-1L, "idFinto", idProgetto);
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("programmazione per idProgetto "+idProgetto+" = "+programmazione);
		List<FaseMonitoraggioProgettoVO> fasiProgetto = null;
		if (Constants.PROGRAMMAZIONE_PRE_2016.equals(programmazione)) {				
			FaseMonitoraggioProgettoPre2016VO vo = new FaseMonitoraggioProgettoPre2016VO();
			vo.setIdProgetto(idProgetto);
			List<FaseMonitoraggioProgettoPre2016VO> fasi = genericDAO.findListWhere(vo);
			fasiProgetto = beanUtil.transformList(fasi, FaseMonitoraggioProgettoVO.class);
		} else {
			FaseMonitoraggioProgettoVO vo = new FaseMonitoraggioProgettoVO();
			vo.setIdProgetto(idProgetto);
			fasiProgetto = genericDAO.findListWhere(vo);
		}
		
		return fasiProgetto;
	}

	/**
	 * 
	 * @param idProgetto
	 * @return
	 */
	private List<IndicatoreDomandaVO> getIndicatori(Long idProgetto) {
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

		IndicatoreDomandaVO indicatoreDomandaVO = new IndicatoreDomandaVO();
		indicatoreDomandaVO.setId_domanda(pbandiTProgettoVO.getIdDomanda());
		List<IndicatoreDomandaVO> indicatoriDomanda = genericDAO
				.findListWhere(indicatoreDomandaVO);

		return indicatoriDomanda;

	}
	
	
	/**
	 * 
	 * @param idDichiarazione
	 * @return
	 * @throws Exception
	 */
	private List<VoceDiCostoDTO> getVociDiCosto(BigDecimal idDichiarazione)
			throws Exception {
		VoceDiSpesaFineProgettoVO filtro = new VoceDiSpesaFineProgettoVO();
		filtro.setIdDichiarazioneSpesa(idDichiarazione);
		List<VoceDiSpesaFineProgettoVO> vociVO = genericDAO
				.findListWhere(filtro);
		List<VoceDiCostoDTO> vociDTO = beanUtil.transformList(vociVO,
				VoceDiCostoDTO.class);
		return vociDTO;
	}
	
	/**
	 * 
	 * @param idProgetto
	 * @param progrBandoLinea
	 * @throws Exception
	 */
	public Modello popolaModello(Long idProgetto) throws Exception {
		
		logger.info("popolaModello() INIZIO , idProgetto="+idProgetto);
		logger.info("tipoModello="+tipoModello);
		
		/*
		 * Aggiungo la chiave del progetto
		 */
		addChiave(CHIAVE_ID_PROGETTO, idProgetto);

		
		/*
		 * Aggiungo la chiave del id_bando_linea
		 */
		ProgettoBandoLineaVO progettoBandoLinea = progettoManager.getProgettoBandoLinea(idProgetto);
		logger.info("pre ADD chiave: progettoBandoLinea="+progettoBandoLinea);
		addChiave(CHIAVE_ID_BANDO_LINEA, progettoBandoLinea.getIdBandoLinea());

		logger.info("POST progettoBandoLinea="+progettoBandoLinea);
		
		/*
		 * Verifico il beneficiario
		 */
		Long idSoggettoBeneficiario = progettoManager.getBeneficiario((idProgetto)).getId_soggetto();
		addChiave(CHIAVE_ID_BENEFICIARIO, idSoggettoBeneficiario);
		logger.info("POST idSoggettoBeneficiario="+idSoggettoBeneficiario);
		
		BeneficiarioProgettoVO filtro = new BeneficiarioProgettoVO();
		filtro.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		filtro.setIdSoggetto(new BigDecimal((Long) getChiave(CHIAVE_ID_BENEFICIARIO)));
		BeneficiarioDTO beneficiario = beanUtil.transform(progettoManager.findBeneficiario(filtro), BeneficiarioDTO.class);
		addChiave(CHIAVE_BENEFICIARIO, beneficiario);

		/*
		 * Progetto
		 */
		ProgettoDTO progetto = beanUtil.transform(progettoManager.getProgetto(idProgetto),
				ProgettoDTO.class);
		addChiave(CHIAVE_PROGETTO, progetto);
		
		if(progetto!=null)
			logger.info("progetto.getIdProgetto() =" + progetto.getIdProgetto());

		/*
		 * Rappresentante legale
		 */
		// TODO : PK -> devo ottenere il rappresentante legale senza passare dalla getChiave
		// comunicazioneFineProgettoDTO.getIdRappresentanteLegale()...
		//
		RappresentanteLegaleDTO rappresentanteLegale = getRappresentanteLegale();
		logger.info("popolaModello() rappresentanteLegale="+rappresentanteLegale);
		
		if(rappresentanteLegale==null){		
			Long idRappLegale=(Long)getChiave(CHIAVE_ID_RAPPRESENTANTE_LEGALE);
			logger.info("popolaModello() idRappLegale="+idRappLegale);
			if(idRappLegale==null)
				throw new Exception("Chiave id rappresentante legale non settata");
			 rappresentanteLegale = rappresentanteLegaleManager.findRappresentanteLegale(idProgetto, 
					 idRappLegale);
			addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegale);
		}


		/*
		 * Sede d'intevento
		 */
		SedeVO  sedeIntervento = getSedeManager().findSedeIntervento(idProgetto, idSoggettoBeneficiario);
		addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,beanUtil.transform(sedeIntervento,SedeDTO.class));

		logger.info(" sedeIntervento calcolata ");
		if(sedeIntervento!=null)
			logger.info(" DescIndirizzo "+ sedeIntervento.getDescIndirizzo() + " , civico=" + sedeIntervento.getCivico());
		else
			logger.info(" null ");

		/*
		 * Ente di competenza beneficiario
		 */
		EnteAppartenenzaDTO enteBeneficiario = null;
		EnteDiAppartenenzaVO enteVO = enteManager.findEnteDestinatario(idProgetto);
		
		Map<String,String> propsEnteFromVOtoDTO = new HashMap<String, String>();
		propsEnteFromVOtoDTO.put("descEnte", "descEnte");
		propsEnteFromVOtoDTO.put("indirizzo", "indirizzo");
		propsEnteFromVOtoDTO.put("cap", "cap");
		propsEnteFromVOtoDTO.put("descComune", "comune");
		propsEnteFromVOtoDTO.put("siglaProvincia", "siglaProvincia");
		
		
		enteBeneficiario = beanUtil.transform(enteVO, EnteAppartenenzaDTO.class, propsEnteFromVOtoDTO);
		addChiave(CHIAVE_ENTE_BENEFICIARIO, enteBeneficiario);

		BigDecimal progrBandoLinea = new BigDecimal((Long)getChiave(CHIAVE_ID_BANDO_LINEA));
		logger.info("BigDecimal progrBandoLinea="+progrBandoLinea);
		
		DecodificaDTO tipoDocumentoIndex = getTipoDocumentoIndex();
		if(tipoDocumentoIndex!=null)
			logger.info(" tipoDocumentoIndex.getId()="+tipoDocumentoIndex.getId());
		
		Modello modello = getTemplateDbManager().recuperaModello(
				beanUtil.transform(tipoDocumentoIndex.getId(), BigDecimal.class), progrBandoLinea);

		Map<String, Object> reportParameters = modello.getMasterParameters();
		reportParameters.put("footer", "");
		DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX,tipoDocumentoIndex.getId());
		
		String tipoTemplate=decodifica.getDescrizione() ;
		logger.info("decodifica.getDescrizione() = tipoTemplate = "+ tipoTemplate);
		
		String numeroDoc=(String) getChiave(CHIAVE_NUMERO_DOC);
		if(numeroDoc!=null)
			tipoTemplate+=" numero "+numeroDoc;
		logger.info("***************** numeroDoc: "+numeroDoc);
		
		reportParameters.put("tipoTemplate",tipoTemplate);
		
		List<TemplateJasperVO> templateReports = modello.getTemplateReports();


		if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO)) {
			reportParameters.put("IS_BR02", regolaManager
					.isRegolaApplicabileForBandoLinea((Long)
							getChiave(CHIAVE_ID_BANDO_LINEA),
							RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA));
			
			reportParameters.put("IS_BR05", regolaManager
					.isRegolaApplicabileForBandoLinea((Long)
							getChiave(CHIAVE_ID_BANDO_LINEA),
							RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA));
			reportParameters.put("IS_BR16", regolaManager
					.isRegolaApplicabileForBandoLinea((Long)
							getChiave(CHIAVE_ID_BANDO_LINEA),
							RegoleConstants.BR16_GESTIONE_CAMPO_TASK));
	    }


		reportParameters.put("IS_BR35", regolaManager
				.isRegolaApplicabileForBandoLinea((Long)
						getChiave(CHIAVE_ID_BANDO_LINEA),
						RegoleConstants.BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA));
		reportParameters.put("IS_BR34", regolaManager
				.isRegolaApplicabileForBandoLinea((Long)
						getChiave(CHIAVE_ID_BANDO_LINEA),
						RegoleConstants.BR34_CONTROLLO_ATTIVITA_CRONOPROGRAMMA_VALIDA));
		
		boolean piuGreen = false;
		setFooter(idProgetto, reportParameters, piuGreen);  



		/*
		 * Gestione del flag bozza
		 */
		if (getIsBozza() != null ) {
			reportParameters.put("IS_BOZZA", getIsBozza().booleanValue());
			if (getIsBozza())
				caricaImmagineBozza(reportParameters);
		}
		
		String relativeResoursesPath = ResourceBundle.getBundle("server").getString("resourcesPath");
		logger.debug("relativeResoursesPath="+relativeResoursesPath);
		reportParameters.put("IMG_PATH", relativeResoursesPath);

		Iterator<TemplateJasperVO> iterator = templateReports.iterator();
		logger.info("Numero templateReports = "+templateReports.size());
		long start = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder("\n\n\n\n\n\n\n\n\n\n\n");
		while (iterator.hasNext()) {
			TemplateJasperVO templateVO = iterator.next();
			
			sb.append("\n templateVO.getNomeTemplate()" +templateVO.getNomeTemplate());
			sb.append(" templateVO.getSubDsParamName(): " +templateVO.getSubDsParamName()+
					" getSubReportParamName(): "+templateVO.getSubReportParamName()+"\n");
			
			setActualTemplateVO(templateVO); // necessario per gestire i
												// sottoreport/tabella
			long startMethod = System.currentTimeMillis();
			String methodName = getMethodName(templateVO);
			sb.append("\nmetodo da invocare " + methodName);
			logger.info("\nmetodo da invocare " + methodName);
			Method m = null;
			
			logger.info("Template : NomeTemplate = " +templateVO.getNomeTemplate()+"; SubDsParamName = "+templateVO.getSubDsParamName()+"; SubReportParamName = "+templateVO.getSubReportParamName()+"; Metodo = "+methodName);
			
			try {
				m = this.getClass().getDeclaredMethod(methodName, Map.class);
				m.invoke(this, reportParameters);
				sb.append("\nmetodo invocato CORRETTAMENTE : <<<< "
						+ methodName + " >>>> in "
						+ (System.currentTimeMillis() - startMethod) + "ms\n\n");
			} catch (NoSuchMethodException e) {
				// se il metodo non e' trovato forse non e' volutamente implementato,
				// in ogni caso non deve fallire il business
				sb.append("Non trovato metodo : <<<< " + methodName
						+ " >>>>\n\n");
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			iterator.remove();
		}
		
		logger.info(sb.toString() +"\npopolaModello() eseguito in " + (System.currentTimeMillis() - start) + "ms\n\n\n");
		return modello;
	}
	
	// +Green: inizio
	// Variante del metodo popolaModello() per gestire 
	// la stampa per il progetto a contributo +green dei seguenti pdf:
	//  - dichiarazione di spesa integrativa
	//  - doc di fine progetto 
	public Modello popolaModelloPiuGreen(
			Long idProgettoFinanziamento, Long idProgettoContributo) throws Exception {
		
		logger.begin();
		logger.debug("idProgettoFinanziamento=" + idProgettoFinanziamento); // id del progetto originale , idProgetto
		logger.debug("idProgettoContributo=" + idProgettoContributo); // id del progetto clone

		// BANDO-LINEA
		ProgettoBandoLineaVO progettoBandoLinea = getBandoLinea(idProgettoContributo);
		// ProgettoBandoLineaVO progettoBandoLinea =
		// getBandoLinea(idProgettoFinanziamento); //TODO PK fix JIRA PBANDI-3556

		addChiave(CHIAVE_ID_PROGETTO, idProgettoFinanziamento); // TODO PK fix JIRA PBANDI-3556

		addChiave(CHIAVE_ID_BANDO_LINEA, progettoBandoLinea.getIdBandoLinea());
		logger.debug("progettoBandoLinea.getIdBandoLinea()=" + progettoBandoLinea.getIdBandoLinea());

		// ENTE DI COMPETENZA BENEFICIARIO
		EnteAppartenenzaDTO enteBeneficiario = null;
		EnteDiAppartenenzaVO enteVO = enteManager.findEnteDestinatario(idProgettoContributo);
		// EnteDiAppartenenzaVO enteVO =
		// enteManager.findEnteDestinatario(idProgettoFinanziamento); //TODO PK fix JIRA
		// PBANDI-3556
		logger.debug(" EnteDiAppartenenzaVO caricato");

		Map<String,String> propsEnteFromVOtoDTO = new HashMap<String, String>();
		propsEnteFromVOtoDTO.put("descEnte", "descEnte");
		propsEnteFromVOtoDTO.put("indirizzo", "indirizzo");
		propsEnteFromVOtoDTO.put("cap", "cap");
		propsEnteFromVOtoDTO.put("descComune", "comune");
		propsEnteFromVOtoDTO.put("siglaProvincia", "siglaProvincia");
		enteBeneficiario = beanUtil.transform(enteVO, EnteAppartenenzaDTO.class, propsEnteFromVOtoDTO);
		
		addChiave(CHIAVE_ENTE_BENEFICIARIO, enteBeneficiario);

		BigDecimal progrBandoLinea = new BigDecimal((Long)getChiave(CHIAVE_ID_BANDO_LINEA));		
		logger.debug("progrBandoLinea=" + progrBandoLinea);

		DecodificaDTO tipoDocumentoIndex = getTipoDocumentoIndex();		
		Modello modello = getTemplateDbManager().recuperaModello(
				beanUtil.transform(tipoDocumentoIndex.getId(), BigDecimal.class), progrBandoLinea);

		logger.debug("modello caricato");

		Map<String, Object> reportParameters = modello.getMasterParameters();
		reportParameters.put("footer", "");
		DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX,tipoDocumentoIndex.getId());
		logger.info("decodifica.getDescrizione() : " + decodifica.getDescrizione());

		String tipoTemplate=decodifica.getDescrizione() ;
		logger.debug("tipoTemplate=" + tipoTemplate);

		String numeroDoc=(String) getChiave(CHIAVE_NUMERO_DOC);
		logger.debug("numeroDoc=" + numeroDoc);

		if(numeroDoc!=null)
			tipoTemplate+=" numero "+numeroDoc;
		logger.info(" numeroDoc: " + numeroDoc);
		reportParameters.put("tipoTemplate",tipoTemplate);
		
		List<TemplateJasperVO> templateReports = modello.getTemplateReports();
		
	 
		if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO)) {
			reportParameters.put("IS_BR02", regolaManager
					.isRegolaApplicabileForBandoLinea((Long)
							getChiave(CHIAVE_ID_BANDO_LINEA),
							RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA));
			
			reportParameters.put("IS_BR05", regolaManager
					.isRegolaApplicabileForBandoLinea((Long)
							getChiave(CHIAVE_ID_BANDO_LINEA),
							RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA));
			reportParameters.put("IS_BR16", regolaManager
					.isRegolaApplicabileForBandoLinea((Long)
							getChiave(CHIAVE_ID_BANDO_LINEA),
							RegoleConstants.BR16_GESTIONE_CAMPO_TASK));
	    }
		
		
		reportParameters.put("IS_BR35", regolaManager
				.isRegolaApplicabileForBandoLinea((Long)
						getChiave(CHIAVE_ID_BANDO_LINEA),
						RegoleConstants.BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA));
		reportParameters.put("IS_BR34", regolaManager
				.isRegolaApplicabileForBandoLinea((Long)
						getChiave(CHIAVE_ID_BANDO_LINEA),
						RegoleConstants.BR34_CONTROLLO_ATTIVITA_CRONOPROGRAMMA_VALIDA));
		
		boolean piuGreen = true;
		setFooter(idProgettoFinanziamento, reportParameters, piuGreen);  

		
		
		/*
		 * Gestione del flag bozza
		 */
		if (getIsBozza() != null ) {
			reportParameters.put("IS_BOZZA", getIsBozza().booleanValue());
			if (getIsBozza())
				caricaImmagineBozza(reportParameters);
		}

		logger.debug("reportParameters=" + reportParameters);

		Iterator<TemplateJasperVO> iterator = templateReports.iterator();
		long start = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder("");
		while (iterator.hasNext()) {
			TemplateJasperVO templateVO = iterator.next();
			sb.append("\n templateVO.getNomeTemplate()" +templateVO.getNomeTemplate());
			sb.append("\ttemplateVO.getSubDsParamName(): " +templateVO.getSubDsParamName()+
					"getSubReportParamName(): "+templateVO.getSubReportParamName()+"\n");
			
			setActualTemplateVO(templateVO); // necessario per gestire i sottoreport/tabella
			long startMethod = System.currentTimeMillis();
			String methodName = getMethodName(templateVO);
			sb.append("\nmetodo da invocare " + methodName);
			Method m = null;
			
			logger.info("NOME TEMPLATE = "+templateVO.getNomeTemplate());
			logger.info("SUB DS PARAM NAME = "+templateVO.getSubDsParamName());
			logger.info("SUB REPORT PARAM NAME = "+templateVO.getSubReportParamName());
			logger.info("NOME METODO = "+methodName);
			
			try {
				m = this.getClass().getDeclaredMethod(methodName, Map.class);
				m.invoke(this, reportParameters);
				sb.append("\nmetodo invocato CORRETTAMENTE : <<<< "
						+ methodName + " >>>> in "
						+ (System.currentTimeMillis() - startMethod) + "ms\n\n");
			} catch (NoSuchMethodException e) {
				// se il metodo non viene trovato forse non e' stato volutamente implementato,
				// in ogni caso non deve fallire il business
				sb.append("Non trovato metodo : <<<< " + methodName + " >>>>");
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			iterator.remove();
		}
		logger.info(sb.toString() + " eseguito in " + (System.currentTimeMillis() - start) + "ms");
		logger.end();
		return modello;
	}
	// +Green: fine
	
	// +Green Jira PBANDI-2720: aggiunto parametro piuGreen.
	private void setFooter(Long idProgetto, Map<String, Object> reportParameters, boolean piuGreen)
			throws FormalParameterException {
		logger.begin();
		logger.debug("idProgetto=" + idProgetto + ", piuGreen=" + piuGreen);

		if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO)) {
				DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = getDichiarazioneFinale(idProgetto);
				String idDS = dichiarazioneFinale.getIdDichiarazioneSpesa().toString(); 
				// Jira PBANDI-2720: nelle CFP +Green si aggiunge una "C" all'id della DS.
				if (piuGreen)
					idDS = idDS + "C";
				String footer="Dichiarazione di spesa "+dichiarazioneFinale.getDescTipoDichiarazioneSpesa()+" "
				+idDS+" del "+wrap(dichiarazioneFinale.getDtDichiarazione());
				reportParameters.put("footer", footer);
		} else if (tipoModello.equals(MODELLO_DICHIARAZIONE_DI_SPESA)) {
			DichiarazioneDiSpesaDTO dichiarazione = getDichiarazioneDiSpesa();
			reportParameters.put("IS_BR02",dichiarazione.getIsBr02());
			reportParameters.put("IS_BR05",dichiarazione.getIsBr05());
			reportParameters.put("IS_BR16",dichiarazione.getIsBR16());
			logger.info("\n\n\n\n\n ***************************************  PROGRAMMA ***********");

			String programma = (String)getChiave(CHIAVE_PROGRAMMA);
			if(programma!=null){
				logger.info("\n\n\n\n\n\nprogramma ----> "+programma);
				reportParameters.put("Programma", wrap(programma));
			}
			else{
				logger.info("\n\nsetto programma vuoto  ");
				reportParameters.put("Programma", "");
			}
			
			String footer="Dichiarazione di spesa "+dichiarazione.getDescTipoDichiarazione()+" " +dichiarazione.getNumero()
					+" del "+wrap(dichiarazione.getDataAl());
			reportParameters.put("footer", footer);
		}  else if (tipoModello.equals(MODELLO_COMUNICAZIONE_DI_RINUNCIA)) {
			String footer="";
			DichiarazioneDiRinunciaDTO rinuncia = (DichiarazioneDiRinunciaDTO) getChiave(CHIAVE_COMUNICAZIONE_DI_RINUNCIA);
			footer="Dichiarazione di rinuncia "+rinuncia.getIdDichiarazione()+" del "+wrap(rinuncia.getDataRinuncia());
			reportParameters.put("footer", footer);
		}  else if (tipoModello.equals(MODELLO_RIMODULAZIONE)) {
			String footer="";
	 
			ContoEconomicoConStatoVO contoEconomicoLocalCopyIstr = (ContoEconomicoConStatoVO)
					getChiave(CHIAVE_CONTOECONOMICO_COPY_ISTR);
			Date  data_rimodulazione=contoEconomicoLocalCopyIstr.getDtFineValidita();
			footer="Rimodulazione del "+wrap(data_rimodulazione);
			
			reportParameters.put("footer", footer);
		} else 	if (tipoModello.equals(MODELLO_RICHIESTA_ANTICIPAZIONE) ||
				tipoModello.equals(MODELLO_RICHIESTA_II_ACCONTO) ||
				tipoModello.equals(MODELLO_RICHIESTA_ULTERIORE_ACCONTO) ||
				tipoModello.equals(MODELLO_RICHIESTA_SALDO) ){
			String footer="";
			RichiestaErogazioneReportDTO richiestaErogazioneReportDTO =(RichiestaErogazioneReportDTO)getChiave(CHIAVE_RICHIESTA_EROGAZIONE);
	 
			String causale_erogazione=richiestaErogazioneReportDTO.getDescCausaleErogazione();
			footer=causale_erogazione+"  del "+wrap(new Date());
			reportParameters.put("footer", footer);
		}
		 else if (tipoModello.equals(MODELLO_RICHIESTA_EROGAZIONE)) {
			String footer="";
			
			footer="Richiesta erogazioen del "+wrap(new Date());
			reportParameters.put("footer", footer);
		}
		logger.end();
	}

	private ProgettoBandoLineaVO getBandoLinea(Long idProgetto) {
		logger.debug("idProgetto=" + idProgetto);
		// if(progettoBandoLinea==null)
		ProgettoBandoLineaVO progettoBandoLinea = progettoManager.getProgettoBandoLinea(idProgetto);
		logger.debug("POST progettoBandoLinea=" + progettoBandoLinea);

		return progettoBandoLinea;
	}

	/**
	 * Esempio: descBreveMacroSezione = 'TAB10', il nome del metodo da invocare
	 * sara' : popolaSezioneTab10
	 * 
	 * @param templateVO
	 * @return
	 */
	private String getMethodName(TemplateJasperVO templateVO) {
		String descBreveMacroSezione = templateVO.getDescBreveMacroSezione();
		descBreveMacroSezione = descBreveMacroSezione.toLowerCase();
		descBreveMacroSezione = (descBreveMacroSezione.substring(0, 1))
				.toUpperCase() + descBreveMacroSezione.substring(1);
		String methodName = "popolaSezione" + descBreveMacroSezione;
		return methodName;
	}
	
	/**
	 * Restituisce la decodifica del tipo di documento index
	 * associato al tipo di modello. Se il tipo di modello non e'
	 * stato definito, viene rilanciata una Exception
	 * @return
	 * @throws Exception
	 */
	private DecodificaDTO getTipoDocumentoIndex() throws Exception {
		logger.debug("getTipoDocumentoIndex() tipoModello="+tipoModello);
		if (tipoModello == null) {
			Exception e = new Exception("Errore. Tipo di modello non specificato");
			throw e;
		}
		
		return getDecodificheManager().findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX, 
				tipoModello);
		
	}

	private DichiarazioneDiSpesaDTO getDichiarazioneDiSpesa() {
		return (DichiarazioneDiSpesaDTO) getChiave(CHIAVE_DS_DICHIARAZIONE_DI_SPESA);

	}
	@SuppressWarnings("unchecked")
	private <T extends ProgettoDTO> T getProgetto() {
		return (T) getChiave(CHIAVE_PROGETTO);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends RappresentanteLegaleDTO>  T getRappresentanteLegale () {
		return (T) getChiave(CHIAVE_RAPPRESENTANTE_LEGALE);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends SedeDTO>  T getSedeLegale () {
		return (T) getChiave(CHIAVE_SEDE_LEGALE);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends SedeDTO>  T getSedeIntevento () {
		return (T) getChiave(CHIAVE_SEDE_INTERVENTO);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends SedeDTO>  T getSedeAmministrativa() {
		return (T) getChiave(CHIAVE_SEDE_AMMINISTRATIVA);
	}
	
	@SuppressWarnings("unchecked")
	private EstremiBancariDTO getEstremiBancari () {
		EstremiBancariDTO estremiBancari = soggettoManager.getEstremiBancari((Long)getChiave(CHIAVE_ID_PROGETTO),
				(Long) getChiave(CHIAVE_ID_BENEFICIARIO)) ;
		return estremiBancari;
	}
	
	@SuppressWarnings("unchecked")
	private Boolean   getIsBozza () {
		return  (Boolean) getChiave(CHIAVE_IS_BOZZA);
	}
	
	private void caricaImmagineBozza(Map<String, Object> parameters) {
		long caricaGif = System.currentTimeMillis();
		InputStream imageBozza = this.getClass().getClassLoader()
				.getResourceAsStream("bozza.gif");
		if(imageBozza==null)
			logger.warn("\n\nAttenzione!!! bozza.gif non trovata\n\n");
		logger.info("immagine  bozza caricata in  : "
				+ (System.currentTimeMillis() - caricaGif) + " ms");
		parameters.put("imgBozza", imageBozza);
	}
	
	
	private <T extends EnteAppartenenzaDTO> T getEnteBeneficiario(Long idProgetto) {
		
		return (T) getChiave(CHIAVE_ENTE_BENEFICIARIO);
		
	}
	
	// +Green: restituisce l'idProgetto da usare per popolare un subreport, tenendo conto del caso +Green.
	// Caso normale:
	//  - CHIAVE_ID_PROGETTO = progetto 
	//  - CHIAVE_ID_PROGETTO_PIU_GREEN = null
	// Caso +Green:
	//  - CHIAVE_ID_PROGETTO = progetto a contributo
	//  - CHIAVE_ID_PROGETTO_PIU_GREEN = progetto a finanziamento.
	private Long getIdProgetto() {
		Long idProgetto = null;
		if (getChiave(CHIAVE_ID_PROGETTO_PIU_GREEN) != null)
			idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO_PIU_GREEN);
		else
			idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);
		logger.info("CHIAVE_ID_PROGETTO = "+(Long) getChiave(CHIAVE_ID_PROGETTO)+"; CHIAVE_ID_PROGETTO_PIU_GREEN "+(Long) getChiave(CHIAVE_ID_PROGETTO_PIU_GREEN)+"; idProgetto scelto = "+idProgetto);
		return idProgetto;
	} 
		
	
	@SuppressWarnings("unused")
	private void popolaSezioneDeclaratoria(Map<String, Object> reportParameters) throws Exception {

		Long idProgetto = (Long) getChiave(CHIAVE_ID_PROGETTO);

		//prendo gli attributi valorizzati da anteprimaDichiarazioneDiSpesaCultura
		Object iva = getChiave(CHIAVE_IVA_CULTURA);
		Object ritenuta_acconto = getChiave(CHIAVE_RITENUTA_ACCONTO_CULTURA);
		Object organi = getChiave(CHIAVE_ORGANI_CULTURA);
		Object documento_unico = getChiave(CHIAVE_DOCUMENTO_UNICO_CULTURA);
		Object richiesta_contributi = getChiave(CHIAVE_RICHIESTA_CONTRIBUTI_CULTURA);
		Object denominazione_settore = getChiave(CHIAVE_DENOMINAZIONE_SETTORE_CULTURA);
		Object richiesta_contributi_statali = getChiave(CHIAVE_RICHIESTA_CONTRIBUTI_STATALI_CULTURA);
		Object denominazione_settore_statali = getChiave(CHIAVE_DENOMINAZIONE_SETTORE_STATALI_CULTURA);
		Object eventuali_contributi = getChiave(CHIAVE_EVENTUALI_CONTRIBUTI_CULTURA);

		//inserisco gli attributi nel pdf nei placeholder segnati in configurazione bando sull'interfaccia di pbwebbo
		reportParameters.put("ritenuta_acconto", wrap(ritenuta_acconto));
		reportParameters.put("iva", wrap(iva));
		reportParameters.put("organi", wrap(organi));
		reportParameters.put("documento_unico", wrap(documento_unico));
		reportParameters.put("richiesta_contributi", wrap(richiesta_contributi));
		reportParameters.put("denominazione_settore", wrap(denominazione_settore));
		reportParameters.put("richiesta_contributi_statali", wrap(richiesta_contributi_statali));
		reportParameters.put("denominazione_settore_statali", wrap(denominazione_settore_statali));
		reportParameters.put("eventuali_contributi", wrap(eventuali_contributi));

	}

	@SuppressWarnings("unused")
	private void popolaSezioneTabvoentrate(Map<String, Object> reportParameters) throws Exception {
		logger.begin();
		logger.info("Tabella voci di entrata");

		// Sett 2023 - per bandi cultura
		List<VoceDiEntrataCulturaDTO> vociDiEntrata = (List<VoceDiEntrataCulturaDTO>) getChiave(CHIAVE_VOCI_DI_ENTRATA);

		// Per escludere le righe con valori null
				//		vociDiEntrata = vociDiEntrata.stream().filter(
				//				v -> v.getImportoRichiesto() != null && v.getImportoRichiesto().compareTo(BigDecimal.ZERO) > 0  ||
				//				v.getImportoAmmesso() != null && v.getImportoAmmesso().compareTo(BigDecimal.ZERO) > 0 ||
				//				v.getImportoConsuntivoPresentato() != null && v.getImportoConsuntivoPresentato().compareTo(BigDecimal.ZERO) > 0
				//				)
				//				.collect(Collectors.toList());

		//Calcolo riga dei totali
		VoceDiEntrataCulturaDTO totali = new VoceDiEntrataCulturaDTO();
		totali.setDescrizione("ENTRATE NETTE");
		totali.setImportoRichiesto(vociDiEntrata.stream().map(VoceDiEntrataCulturaDTO::getImportoRichiesto).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
		totali.setImportoAmmesso(vociDiEntrata.stream().map(VoceDiEntrataCulturaDTO::getImportoAmmesso).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
		totali.setImportoConsuntivoPresentato(vociDiEntrata.stream().map(VoceDiEntrataCulturaDTO::getImportoConsuntivoPresentato).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
		vociDiEntrata.add(0, totali);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vociDiEntrata);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource, VoceDiEntrataCulturaDTO.class, getActualTemplateVO(), reportParameters);
		logger.end();
	}



	/**
	 * QUADRO RAPPRESENTATIVO GRADO REALIZZAZIONE INTERVENTO CULTURA
	 *
	 * @param reportParameters
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void popolaSezioneTabqrgcultura(Map<String, Object> reportParameters) throws Exception {

		Long idProgetto = this.getIdProgetto();
		logger.debug("idProgetto=" + idProgetto);

		VociDiSpesaCulturaDTO vociDiSpesa = contoEconomicoDAOImpl.vociDiSpesaCultura(idProgetto, 1L, "1");

		ContoEconomicoRimodulazioneDTO rigoPadre = vociDiSpesa.getEsitoFindContoEconomicoDTO().getContoEconomico();

		ArrayList<ContoEconomicoRimodulazioneDTO> righe = new ArrayList<>();
		rigoPadre.setLabel("CONTO ECONOMICO");

		righe.add(rigoPadre);
		Arrays.stream(rigoPadre.getFigli()).map(ContoEconomicoRimodulazioneDTO::getFigli).forEach(collection -> righe.addAll(Arrays.asList(collection)));

		double totaleSpesaPreventivata = 0;
		for(ContoEconomicoRimodulazioneDTO riga : righe) {
			if(riga.getImportoSpesaPreventivata() != null)
				totaleSpesaPreventivata += riga.getImportoSpesaPreventivata();
		}
		righe.get(0).setImportoSpesaPreventivata(totaleSpesaPreventivata);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(righe);
		getDynamicTemplateManager().addReportToTemplateJasper(dataSource, ContoEconomicoRimodulazioneDTO.class,
				getActualTemplateVO(), reportParameters);
	}
}
