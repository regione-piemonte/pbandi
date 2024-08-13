/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_DICHIARABILE;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_IN_VALIDAZIONE;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_NON_VALIDATO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_PARZIALMENTE_VALIDATO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_RESPINTO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_SOSPESO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDATO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDAZIONE_INSERITO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDAZIONE_NON_VALIDATO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDAZIONE_RILASCIATO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDAZIONE_SOSPESO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDAZIONE_VALIDATO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.STATO_VALIDAZIONE_VALIDATO_PARZIALMENTE;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_CO_CO_PRO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_COMPENSO_PRESTAZIONI_SOCI;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_SPESE;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_QUOTA_DI_AMMORTAMENTO;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_RICEVUTA_DI_LOCAZIONE;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE;
import static it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_SPESE_GENERALI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentidispesa.GestioneDocumentiDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaDaInviareVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PagamentoDocumentoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaDocumentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaDichiarazioneTotaleValidatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;

/**
 * <p>
 * Classe con costanti che rappresentano le descrizoni brevi delle decodifiche
 * (assunte come univoche). PBANDI_D_STATO_DOCUMENTO_SPESA
 * </p>
 * 
 */
public class DocumentoDiSpesaManager {
	
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
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

	
	public static final String DESC_BREVE_AUTODICHIARAZIONE="CS";
	public static final String DESC_BREVE_FATTURA_LEASING="FL";
	public static final String DESC_BREVE_RICEVUTA_DI_LOCAZIONE="RL";
	public static final String DESC_BREVE_CEDOLINO="CD";
	public static final String DESC_BREVE_FATTURA="FT";
	public static final String DESC_BREVE_FATTURA_FIDEIUSSORIA="FF";
	public static final String DESC_BREVE_NOTA_CREDITO="NC";
	public static final String DESC_BREVE_NOTA_SPESE="NS";
	public static final String DESC_BREVE_QUOTA_DI_AMMORTAMENTO="QA";
	public static final String DESC_BREVE_SPESE_FORFETTARIE="SF";
	public static final String DESC_BREVE_SPESE_GENERALI="SG";
	public static final String DESC_BREVE_ATTO_COSTITUZIONE_FONDO="CF";
	public static final String DESC_BREVE_CEDOLINO_COSTI_STANDARD="CDCS";
	public static final String DESC_BREVE_SPESE_GENERALI_FORFETTARIE_COSTI_STANDARD="SGFCS";
	public static final String DESC_BREVE_AUTOCERTIFICAZIONE_SPESE="AS";
	public static final String DESC_BREVE_SAL_SENZA_QUIETANZA="SALSQ";
	public static final String DESC_BREVE_COMPENSO_MENSILE_TIROCINANTE="CMT";
	public static final String DESC_BREVE_COMPENSO_IMPRESA_ARTIGIANA = "CIA";
	public static final String DESC_BREVE_COMPENSO_SOGGETTO_GESTORE = "CSG";

	
	/**
	 * identificativo dell'applicativo.
	 */
	private static Map<String, String> mapStatiDocumentiDiSpesa = new HashMap<String, String>();
	static {
		mapStatiDocumentiDiSpesa.put(STATO_IN_VALIDAZIONE, "D");
		mapStatiDocumentiDiSpesa.put(STATO_DICHIARABILE, "I");
		mapStatiDocumentiDiSpesa.put(STATO_NON_VALIDATO, "N");
		mapStatiDocumentiDiSpesa.put(STATO_PARZIALMENTE_VALIDATO, "P");
		mapStatiDocumentiDiSpesa.put(STATO_SOSPESO, "S");
		mapStatiDocumentiDiSpesa.put(STATO_VALIDATO, "V");
		mapStatiDocumentiDiSpesa.put(STATO_RESPINTO, "R");
	}

	private static Map<String, String> mapStatiValidazioneSpesa = new HashMap<String, String>();
	static {

		mapStatiValidazioneSpesa.put(STATO_VALIDAZIONE_INSERITO, "I");
		mapStatiValidazioneSpesa.put(STATO_VALIDAZIONE_NON_VALIDATO, "N");
		mapStatiValidazioneSpesa.put(STATO_VALIDAZIONE_VALIDATO_PARZIALMENTE,
				"P");
		mapStatiValidazioneSpesa.put(STATO_VALIDAZIONE_RILASCIATO, "R");
		mapStatiValidazioneSpesa.put(STATO_VALIDAZIONE_SOSPESO, "S");
		mapStatiValidazioneSpesa.put(STATO_VALIDAZIONE_VALIDATO, "V");
	}

	private static Map<String, String> mapTipoDocumentiDiSpesa = new HashMap<String, String>();
	static {
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_CEDOLINO, "CD");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_CO_CO_PRO, "CC");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI, "CS");
		mapTipoDocumentiDiSpesa.put(
				TIPO_DOCUMENTO_DI_SPESA_COMPENSO_PRESTAZIONI_SOCI, "PS");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_FATTURA, "FT");
		mapTipoDocumentiDiSpesa.put(
				TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA, "FF");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING,
				"FL");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO,
				"NC");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_NOTA_SPESE, "NS");
		mapTipoDocumentiDiSpesa.put(
				TIPO_DOCUMENTO_DI_SPESA_QUOTA_DI_AMMORTAMENTO, "QA");
		mapTipoDocumentiDiSpesa.put(
				TIPO_DOCUMENTO_DI_SPESA_RICEVUTA_DI_LOCAZIONE, "RL");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE,
				"SF");
		mapTipoDocumentiDiSpesa.put(TIPO_DOCUMENTO_DI_SPESA_SPESE_GENERALI,
				"SG");
	}

	private static Map<String, String> mapRegolaDocumentoDiSpesa = new HashMap<String, String>();
	static {
		mapRegolaDocumentoDiSpesa.put("REGOLA_BR01", "BR01");
	}

	public static String getCodiceStatoDocumentoDiSpesa(String chiave) {
		return (String) mapStatiDocumentiDiSpesa.get(chiave);
	}

	public static String getCodiceStatoValidazioneDiSpesa(String chiave) {
		return (String) mapStatiValidazioneSpesa.get(chiave);
	}

	public static String getCodiceTipoDocumentoDiSpesa(String chiave) {
		return (String) mapTipoDocumentiDiSpesa.get(chiave);
	}

	public static String getCodiceRegolaDocumentoDiSpesa(String chiave) {
		return (String) mapRegolaDocumentoDiSpesa.get(chiave);
	}

	@Autowired
	private DecodificheManager decodificheManager;

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	
	public boolean isCedolinoOAutodichiarazioneSoci(Long idTipoDocumentoSpesa) throws ManagerException 
	{
		boolean result=false;
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				idTipoDocumentoSpesa);
		if (!ObjectUtil.isNull(decodifica)
				&& !ObjectUtil.isEmpty(decodifica.getDescrizioneBreve())) {
			String descBreve=decodifica
			.getDescrizioneBreve();
			if (descBreve
					.equals(
							getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO) )||
				descBreve
							.equals(
									getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI)
									))
				result = true;
			else
				result = false;
		} else {
			logger
					.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
			throw new ManagerException(
					"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
							+ idTipoDocumentoSpesa);
		}
		return result;
	}
	
	public boolean isCedolinoOAutodichiarazioneSoci(DocumentoSpesaDaInviareVO documentoDiSpesaVO) 
	{
		if(documentoDiSpesaVO.getDescBreveTipoDocSpesa().
				equalsIgnoreCase(DocumentoDiSpesaManager.DESC_BREVE_CEDOLINO)
				|| documentoDiSpesaVO.getDescBreveTipoDocSpesa().
				equalsIgnoreCase(DocumentoDiSpesaManager.DESC_BREVE_AUTODICHIARAZIONE))
			return true;
		
		return false;
	
	}

	/**
	 * 
	 * @param idTipoDocumentoDiSpesa
	 * @return
	 * @throws GestioneDocumentiDiSpesaException
	 */
	public boolean isFattura(Long idTipoDocumentoDiSpesa)
			throws ManagerException {
		logger.begin();
		try {
			boolean result = false;
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoDiSpesa);
			if (!ObjectUtil.isNull(decodifica)
					&& !ObjectUtil.isEmpty(decodifica.getDescrizioneBreve())) {
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
					result = true;
				} else {
					logger.debug("documento di spesa NON � una FATTURA ");
					result = false;
				}
			} else {
				logger
						.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
				throw new ManagerException(
						"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
								+ idTipoDocumentoDiSpesa);
			}
			return result;
		} finally {
			logger.end();
		}
	}
	
	
	public boolean isQuotaAmmortamento (Long idTipoDocumentoDiSpesa) throws ManagerException{
		boolean result = false;
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				idTipoDocumentoDiSpesa);
		if (!ObjectUtil.isNull(decodifica)
				&& !ObjectUtil.isEmpty(decodifica.getDescrizioneBreve())) {
			if (decodifica
					.getDescrizioneBreve()
					.equalsIgnoreCase(
							DocumentoDiSpesaManager
									.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_QUOTA_DI_AMMORTAMENTO))) {
				logger.debug("documento di spesa � una QUOTA_DI_AMMORTAMENTO ");
				result = true;
			} else {
				logger.debug("documento di spesa NON � una QUOTA_DI_AMMORTAMENTO");
				result = false;
			}
			
		} else {
			logger
			.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
				throw new ManagerException(
						"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
								+ idTipoDocumentoDiSpesa);
		}
		return result;
	}

	public boolean isNotaDiCredito(Long idTipoDocumentoDiSpesa)
			throws ManagerException {
		boolean result = false;
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				idTipoDocumentoDiSpesa);
		if (!ObjectUtil.isNull(decodifica)
				&& !ObjectUtil.isEmpty(decodifica.getDescrizioneBreve())) {
			if (decodifica
					.getDescrizioneBreve()
					.equalsIgnoreCase(
							DocumentoDiSpesaManager
									.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO))) {
				logger.debug("documento di spesa � una NOTADICREDITO ");
				result = true;
			} else {
				logger.debug("documento di spesa NON � una NOTADICREDITO");
				result = false;
			}
		} else {
			logger
					.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
			throw new ManagerException(
					"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
							+ idTipoDocumentoDiSpesa);
		}
		return result;
	}
	
	


	public boolean isSpesaForfettaria(Long idTipoDocumentoSpesa)
			throws ManagerException {
			boolean result = false;
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoSpesa);
			if (!ObjectUtil.isNull(decodifica)
					&& !ObjectUtil.isEmpty(decodifica.getDescrizioneBreve())) {
				if (decodifica
						.getDescrizioneBreve()
						.equals(
								DocumentoDiSpesaManager
										.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE)))
					result = true;
			} else {
				logger
						.warn("decodifica del tipo documento di spesa NON � stata TROVATA !!!");
				throw new ManagerException(
						"decodifica del tipo documento di spesa NON � stata TROVATA per idTipo:"
								+ idTipoDocumentoSpesa);
			}
			return result;
	}
	
	
	public BigDecimal getImportoValidatoTotaleDocumento(Long idDocumentoDiSpesa,Long idDichiarazione, Long idProgetto) throws Exception {
		DocumentoDiSpesaDichiarazioneTotaleValidatoVO filtroVO = new DocumentoDiSpesaDichiarazioneTotaleValidatoVO();
		filtroVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazione));
		filtroVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
	
		List<DocumentoDiSpesaDichiarazioneTotaleValidatoVO>  result = getGenericDAO().findListWhere(filtroVO);
		if (result != null ) {
			if (result.isEmpty()) {
				return null;
			} else if (result.size() == 1) {
				return result.get(0).getImportoTotaleValidato();
			} else {
				/*
				 * Piu' di un risultato
				 * lancio una eccezione
				 */
				Exception e = new Exception ("Trovato piu' di un risultato.");
				logger.error("Errore durante la ricerca del totale importo validato per il documento di spesa.", e);
				throw e;
				
			}
		}
		return null;
	}
	
	
	/*
	 * Esegue il controllo delle voci di spesa del documento (anche per la  nota di credito).
	 */
	public String controlliVociSpesaNoteCreditoDocumento (BigDecimal idDocumentoDiSpesa, BigDecimal idProgetto,boolean isNotaCredito) {
		DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				DocumentoDiSpesaManager
				.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO));
		
		PbandiTDocumentoDiSpesaVO documentoDiSpesaVO = new PbandiTDocumentoDiSpesaVO();
		documentoDiSpesaVO.setIdDocumentoDiSpesa(idDocumentoDiSpesa);
		documentoDiSpesaVO = genericDAO.findSingleWhere(documentoDiSpesaVO);
		
		String error = null;
		
		
		/*
		 * Imposto il filtro per la ricerca le voci di spesa delle note di credito associato al
		 * documento. Nel caso della nota di credito utilizzo nel filtro
		 * l'idDocDiRiferimento.
		 */
		VoceDiSpesaDocumentoVO filtroNoteCreditoVO = new VoceDiSpesaDocumentoVO();
		filtroNoteCreditoVO.setIdProgetto(idProgetto);
		filtroNoteCreditoVO.setIdTipoDocumentoSpesa(NumberUtil.toBigDecimal(decodifica.getId()));
		filtroNoteCreditoVO.setAscendentOrder("idVoceDiSpesa");
		
		/*
		 * Imposto il filtro per la ricerca le voci di spesa associate al documento. Nel caso
		 * in cui il documento corrente e' una NOTA DI CREDITO utilizzo nel filtro l'idDocDiRiferimento e
		 * le voci di spesa si riferiscono al documento idDocRiferimento e non
		 * al documento corrente.
		 * 
		 */
		VoceDiSpesaDocumentoVO filtroVoceDocumentoVO = new VoceDiSpesaDocumentoVO();
		filtroVoceDocumentoVO.setIdProgetto(idProgetto);
		filtroVoceDocumentoVO.setAscendentOrder("idVoceDiSpesa");
		
		if (!isNotaCredito) {
			filtroVoceDocumentoVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
		} else {
			filtroVoceDocumentoVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocRiferimento());
		}
		
		/*
		 * Voci di spesa del documento con note di credito associate.
		 * Nel caso in cui il documento corrente e' una NOTA DI CREDITO, allora le
		 * voci si referisco al documento con idDocDiRiferimento, altrimenti si riferiscono
		 * al documento corrente.
		 */
		List<VoceDiSpesaDocumentoVO> vociSpesaDocumento = genericDAO.findListWhere(filtroVoceDocumentoVO);
		
		BigDecimal importoVoceSpesaDocumento = null;
		BigDecimal importoTotaleVoceSpesaNoteCredito = new BigDecimal(0);
		List<VoceDiSpesaDocumentoVO> vociSpesaNoteCredito = null;
		/*
		 * Per ogni voce di spesa del documento verifico che: 
		 * - l' importo della voce di spesa sia maggiore o uguale al totale degli
		 * 	 importi della voce di spesa associata alle note di credito.
		 * 
		 * - le voci di spesa associate alle note di credito del documento
		 *    siano un sottoinsieme delle voci di spesa del documento
		 */
		if (!isNotaCredito) {
			filtroNoteCreditoVO.setIdDocRiferimento(documentoDiSpesaVO.getIdDocumentoDiSpesa());
			/*
			 * Voci di spesa delle note di credito del documento
			 */
			vociSpesaNoteCredito = genericDAO.findListWhere(filtroNoteCreditoVO);
			List<BigDecimal> listIdVoceDiSpesaDocumento = beanUtil.extractValues(vociSpesaDocumento, "idVoceDiSpesa", BigDecimal.class);
			boolean isVoceNotaCreditoNonCompresa = false;
			for (VoceDiSpesaDocumentoVO voceSpesaDocumento : vociSpesaDocumento){
				importoVoceSpesaDocumento = voceSpesaDocumento.getImportoVoceDiSpesa();
				importoTotaleVoceSpesaNoteCredito = new BigDecimal(0);
				for (VoceDiSpesaDocumentoVO voceSpesaNotaCredito : vociSpesaNoteCredito){
					if (NumberUtil.compare(voceSpesaNotaCredito.getIdVoceDiSpesa(),voceSpesaDocumento.getIdVoceDiSpesa()) == 0) {
						importoTotaleVoceSpesaNoteCredito = NumberUtil.sum(importoTotaleVoceSpesaNoteCredito,voceSpesaNotaCredito.getImportoVoceDiSpesa());
					} else if (!listIdVoceDiSpesaDocumento.contains(voceSpesaNotaCredito.getIdVoceDiSpesa())) {
						isVoceNotaCreditoNonCompresa = true;
						break;
					}
				}
				if (isVoceNotaCreditoNonCompresa || NumberUtil.compare(importoVoceSpesaDocumento, importoTotaleVoceSpesaNoteCredito) < 0 ) {
					error = MessaggiConstants.IMPORTO_NON_CORRETTO_VOCI_SPESA_DOCUMENTO_E_NOTE_CREDITO;
					break;
				}
			}
			
		} else  {
			/*
			 * Ricerco le voci di spesa della nota di credito
			 */
			VoceDiSpesaDocumentoVO filtroVociNotaCreditoCorrenteVO = new VoceDiSpesaDocumentoVO();
			filtroVociNotaCreditoCorrenteVO.setIdProgetto(idProgetto);
			filtroVociNotaCreditoCorrenteVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
			filtroVociNotaCreditoCorrenteVO.setAscendentOrder("idVoceDiSpesa");
			List<VoceDiSpesaDocumentoVO> vociSpesaNotaCreditoCorrente = genericDAO.findListWhere(filtroVociNotaCreditoCorrenteVO);
			
			/*
			 * Ricerco tra tutte le note di credito del documento quelle che hanno
			 * associate le stesse voci di spesa della nota di credito corrente.
			 */
			
			Map<String,String> m = new HashMap<String, String>();
			m.put("idVoceDiSpesa", "idVoceDiSpesa");
			List<VoceDiSpesaDocumentoVO> listIdVociSpesaNotaCreditoCorrente = beanUtil.transformList(vociSpesaNotaCreditoCorrente, VoceDiSpesaDocumentoVO.class, m);
			FilterCondition<VoceDiSpesaDocumentoVO> filterConditionIdVociSpesaNotaCreditoCorrente = new FilterCondition<VoceDiSpesaDocumentoVO>(listIdVociSpesaNotaCreditoCorrente);
			
			filtroNoteCreditoVO.setIdDocRiferimento(documentoDiSpesaVO.getIdDocRiferimento());
			filtroNoteCreditoVO.setIdTipoDocumentoSpesa(NumberUtil.toBigDecimal(decodifica.getId()));
			filtroNoteCreditoVO.setAscendentOrder("idVoceDiSpesa");
			FilterCondition<VoceDiSpesaDocumentoVO> filterCondition = new FilterCondition<VoceDiSpesaDocumentoVO>(filtroNoteCreditoVO);

			AndCondition<VoceDiSpesaDocumentoVO> andCondition = new AndCondition<VoceDiSpesaDocumentoVO>(filterCondition,filterConditionIdVociSpesaNotaCreditoCorrente);
			
			vociSpesaNoteCredito = genericDAO.findListWhere(andCondition);
			/*
			 * Per ogni voce di spesa della note di credito verifico che l' importo
			 * della voce di spesa associata al documento sia maggiore o uguale al
			 * totale degli importi della voce di spesa delle note di credito.
			 * Le voci di spesa sono ordinate per idVoceDiSpesa.
			 */
			importoTotaleVoceSpesaNoteCredito = new BigDecimal(0);
			BigDecimal idVoceSpesaPrec = null;
			for (VoceDiSpesaDocumentoVO voceSpesaNotaCredito : vociSpesaNoteCredito){
				if (idVoceSpesaPrec != null && NumberUtil.compare(voceSpesaNotaCredito.getIdVoceDiSpesa(),idVoceSpesaPrec) == 0) {
					/*
					 * Stiamo considerando la stessa voce di spesa di quella precedente.
					 * 
					 */
					importoTotaleVoceSpesaNoteCredito = NumberUtil.sum(importoTotaleVoceSpesaNoteCredito,voceSpesaNotaCredito.getImportoVoceDiSpesa());
				} else {
					idVoceSpesaPrec = voceSpesaNotaCredito.getIdVoceDiSpesa();
					importoTotaleVoceSpesaNoteCredito = voceSpesaNotaCredito.getImportoVoceDiSpesa();
				}
				
				/*
				 * Ricerco tra le voci di spesa del documento di riferimento della nota 
				 * di credito la voce di spesa corrente
				 */
				importoVoceSpesaDocumento = null;
				for (VoceDiSpesaDocumentoVO voceSpesaDocumento : vociSpesaDocumento){
					if (NumberUtil.compare(voceSpesaDocumento.getIdVoceDiSpesa(),voceSpesaNotaCredito.getIdVoceDiSpesa()) == 0) {
						importoVoceSpesaDocumento = voceSpesaDocumento.getImportoVoceDiSpesa();
						break;
					}
				}
				if (importoVoceSpesaDocumento == null || NumberUtil.compare(importoVoceSpesaDocumento, importoTotaleVoceSpesaNoteCredito) < 0) {
					error = MessaggiConstants.IMPORTO_NON_CORRETTO_VOCI_SPESA_NOTA_CREDITO_DOCUMENTO;
					break;
				}
			}
		}
		return error;
	}
	
	/*
	 * verifico che la somma degli importi
	 * rendicontabile del documento di spesa associato ai progetti non
	 * sia superiore al massimo rendicontabile ( imponibile + importo
	 * iva a costo)
	 */
	public boolean checkMassimRendicontabileDocumentoProgetti(Long idDocumentoDiSpesa, Long idProgetto, Double importoImponibile, Double importoIvaACosto, Double importoRendicontabile ) {
		
		boolean result = true;
		PbandiRDocSpesaProgettoVO filtroVO = new PbandiRDocSpesaProgettoVO();
		filtroVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(idDocumentoDiSpesa));

		PbandiRDocSpesaProgettoVO filtroProgettoVO = new PbandiRDocSpesaProgettoVO();
		filtroProgettoVO.setIdProgetto(NumberUtil
				.toBigDecimal(idProgetto));

		List<PbandiRDocSpesaProgettoVO> progettiAssociatiDocumentoVO = genericDAO
				.findListWhere(new AndCondition<PbandiRDocSpesaProgettoVO>(
						Condition.filterBy(filtroVO), Condition
								.not(Condition
										.filterBy(filtroProgettoVO))));

		if (!ObjectUtil.isEmpty(progettiAssociatiDocumentoVO)) {
			BigDecimal imponibile = NumberUtil
					.createScaledBigDecimal(importoImponibile);
			BigDecimal importoIvaCosto = NumberUtil
					.createScaledBigDecimal(importoIvaACosto);
			BigDecimal massimoRendicontabile = NumberUtil.sum(
					imponibile, importoIvaCosto);

			BigDecimal totaleRendicontabile = NumberUtil
					.createScaledBigDecimal(importoRendicontabile);

			for (PbandiRDocSpesaProgettoVO doc : progettiAssociatiDocumentoVO) {
				totaleRendicontabile = NumberUtil.sum(
						totaleRendicontabile, NumberUtil
								.createScaledBigDecimal(doc
										.getImportoRendicontazione()));
			}

			if (NumberUtil.compare(totaleRendicontabile,
					massimoRendicontabile) > 0) {
				result = false;
			}
		}
		return result;
	}
	
	
	/* 
	 * Verifica che l'importo rendicontabile inserito
	 * per una nota di credito sia minore o uguale al rendicontabile
	 * netto (ovvero corretto gi� da eventuali altri rendicontabili di
	 * note di credito gi� esistenti per il documento di riferimento).
	 */
	
	public boolean checkRendicontabileNotaCredito( Long idNotaCredito, 
			Long idFatturaDiRiferimento, 
			Long idProgetto, 
			Double importoRendicontabileNotaCredito) {
		/*
		 * Ricerco il totale rendicontabile del documento di spesa di
		 * riferimento
		 */
		DocumentoDiSpesaProgettoVO docRiferimentoVO = new DocumentoDiSpesaProgettoVO();
		docRiferimentoVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idFatturaDiRiferimento));
		docRiferimentoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		docRiferimentoVO = genericDAO.findSingleWhere(docRiferimentoVO);

		/*
		 * Ricerco l'importo rendicontabile delle note di credito associate al
		 * documento escludento la nota di credito corrente
		 */
		DocumentoDiSpesaProgettoVO filtroNoteCreditoVO = new DocumentoDiSpesaProgettoVO();
		filtroNoteCreditoVO.setIdDocRiferimento(NumberUtil
				.toBigDecimal(idFatturaDiRiferimento));
		filtroNoteCreditoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));

		DocumentoDiSpesaProgettoVO filtroNotaCorrenteVO = new DocumentoDiSpesaProgettoVO();
		filtroNotaCorrenteVO.setIdDocumentoDiSpesa(NumberUtil
				.toBigDecimal(idNotaCredito));
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
				.transformToBigDecimal(importoRendicontabileNotaCredito);

		if (NumberUtil.compare(impotoRendicontabileNotaCredito,
				totaleRendicontabileNetto) > 0) {
			return false;
		} else {
			return true;
		}

	}
	
	
	public List<DocumentoDiSpesaProgettoVO> findNoteCreditoFattura (Long idProgetto, Long idFattura) {

		DocumentoDiSpesaProgettoVO filtro = new DocumentoDiSpesaProgettoVO();
		filtro.setIdDocRiferimento(NumberUtil.toBigDecimal(idFattura));
		if (!ObjectUtil.isNull(idProgetto)) {
			filtro.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		}
		filtro.setDescBreveTipoDocSpesa(Constants.TIPO_DOCUMENTO_DI_SPESA_NOTA_CREDITO);
		filtro.setDescendentOrder("dtEmissioneDocumento");

		List<DocumentoDiSpesaProgettoVO> elencoNoteDiCredito = genericDAO.findListWhere(filtro);
		
		return elencoNoteDiCredito;
	}
	
	public List<DocumentoDiSpesaProgettoVO> eliminaNoteCreditoDuplicate (List<DocumentoDiSpesaProgettoVO> noteCredito) {
		List<DocumentoDiSpesaProgettoVO> result = new ArrayList<DocumentoDiSpesaProgettoVO>();
		if (!ObjectUtil.isEmpty(noteCredito)) {
			BigDecimal idNotaPrec = null;
			DocumentoDiSpesaProgettoVO notaPrec = null;
			for (DocumentoDiSpesaProgettoVO nota : noteCredito) {
				if (idNotaPrec == null) {
					idNotaPrec = nota.getIdDocumentoDiSpesa();
					notaPrec = nota;
				}
				
				if (NumberUtil.compare(idNotaPrec, nota.getIdDocumentoDiSpesa()) != 0) {
					result.add(notaPrec);
					notaPrec = nota;
					idNotaPrec = nota.getIdDocumentoDiSpesa();
				}
			}
			/*
			 * Inserisco l'ultimo record
			 */
			result.add(notaPrec);
		}
		return result;
	}
	
	public List<DocumentoDiSpesaProgettoVO> findNoteCreditoDocumenti (Long idProgetto, List<Long> documenti) {
		
		List<DocumentoDiSpesaProgettoVO> filtroDocumenti = new ArrayList();
		for (Long id : documenti) {
			DocumentoDiSpesaProgettoVO filtro = new DocumentoDiSpesaProgettoVO();
			filtro.setIdDocRiferimento(NumberUtil.toBigDecimal(id));
			filtroDocumenti.add(filtro);
		}
		
		DocumentoDiSpesaProgettoVO filtro = new DocumentoDiSpesaProgettoVO();
		filtro.setDescBreveTipoDocSpesa(Constants.TIPO_DOCUMENTO_DI_SPESA_NOTA_CREDITO);
		if (!ObjectUtil.isNull(idProgetto)) {
			filtro.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		}
		
		List<DocumentoDiSpesaProgettoVO> elencoNoteDiCredito = genericDAO.findListWhere(Condition.filterBy(filtroDocumenti).and(Condition.filterBy(filtro)));

		return elencoNoteDiCredito;
	}
	
	public BigDecimal getImportoTotalePagamenti(Long idProgetto, Long idDocumentoDiSpesa) {
		BigDecimal importoTotalePagamenti = new BigDecimal(0.0);
		PagamentoDocumentoProgettoVO filter = new PagamentoDocumentoProgettoVO();
		filter.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		filter.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PagamentoDocumentoProgettoVO> pagamenti = genericDAO.findListWhere(filter);
		
		for (PagamentoDocumentoProgettoVO pag : pagamenti) {
			BigDecimal importoPagamento = pag.getImportoPagamento() == null ?  new BigDecimal(0.0) : pag.getImportoPagamento();
			importoTotalePagamenti = NumberUtil.sum(importoTotalePagamenti, importoPagamento);
		}
		
		return importoTotalePagamenti;
		 
	}
	
	public BigDecimal getImportoTotaleNoteCredito(Long idDocumentoDiSpesa) {
		BigDecimal importoTotaleNote = new BigDecimal(0.0);
		PbandiTDocumentoDiSpesaVO filter = new PbandiTDocumentoDiSpesaVO();
		filter.setIdDocRiferimento(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		List<PbandiTDocumentoDiSpesaVO> noteCredito = genericDAO.findListWhere(filter);
		for (PbandiTDocumentoDiSpesaVO nota : noteCredito) {
			BigDecimal importoNota = nota.getImportoTotaleDocumento() == null ? new BigDecimal(0.0) : nota.getImportoTotaleDocumento();
			importoTotaleNote = NumberUtil.sum(importoTotaleNote, importoNota);
		}
		return importoTotaleNote;
		
		
		
	}

	
	public boolean isCedolinoCostiStandard(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_CEDOLINO_COSTI_STANDARD))
			return true;
		return false;
	}
	public boolean isSpeseGeneraliForfettarieCostiStandard(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_SPESE_GENERALI_FORFETTARIE_COSTI_STANDARD))
			return true;
		return false;
	}
	public boolean isSpeseForfettarie(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_SPESE_FORFETTARIE))
			return true;
		return false;
	}
	public boolean isAutocertificazioneSpese(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_AUTOCERTIFICAZIONE_SPESE))
			return true;
		return false;
	}
	public boolean isSALSenzaQuietanza(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_SAL_SENZA_QUIETANZA))
			return true;
		return false;
	}
	public boolean isCompensoMensileTirocinante(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_COMPENSO_MENSILE_TIROCINANTE))
			return true;
		return false;
	}
	public boolean isCompensoImpresaArtigiana(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_COMPENSO_IMPRESA_ARTIGIANA))
			return true;
		return false;
	}
	public boolean isCompensoSoggettoGestore(String descBreve) {
		if (descBreve.equalsIgnoreCase(
				DESC_BREVE_COMPENSO_SOGGETTO_GESTORE))
			return true;
		return false;
	}

}
