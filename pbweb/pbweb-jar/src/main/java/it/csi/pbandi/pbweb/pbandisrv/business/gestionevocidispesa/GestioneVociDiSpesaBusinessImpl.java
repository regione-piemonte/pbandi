/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.gestionevocidispesa;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.EsitoOperazioneVociDiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionevocidispesa.GestioneVociDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiFornitoreQualificaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiVociDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaDocumentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaTipoDocVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VociDiSpesaPadriFigliContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionevocidispesa.VoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagQuotParteDocSpVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionevocidispesa.GestioneVociDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbweb.util.ErrorMessages;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;



public class GestioneVociDiSpesaBusinessImpl extends BusinessImpl implements
		GestioneVociDiSpesaSrv {

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private PbandiVociDiSpesaDAOImpl pbandiVociDiSpesaDAO;
	
	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	
	@Autowired
	private PbandiFornitoreQualificaDAOImpl pbandiRFornitoreQualificaDAO;

	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	
	
	public static final BidiMap fromVoceDiSpesaVO2VoceDiSpesaDTO = new TreeBidiMap();
	static {
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idDocumentoDiSpesa", "idDocSpesa");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idProgetto", "idProgetto");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idTipoDocumentoSpesa", "idTipoDocumentoDiSpesa");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idVoceDiSpesa", "idVoceDiSpesa");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idVoceDiSpesaPadre", "idVoceDiSpesaPadre");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idQuotaParteDocSpesa", "idQuotaParteDocSpesa");	
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("descVoceDiSpesa", "descVoceDiSpesa");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("descVoceDiSpesaCompleta", "descVoceDiSpesaCompleta");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("descVoceDiSpesaPadre", "descVoceDiSpesaPadre");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("importoVoceDiSpesa", "importo");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("importoAmmessoFinanziamento", "importoFinanziamento");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("oreLavorate", "oreLavorate");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idRigoContoEconomico", "idRigoContoEconomico");
		fromVoceDiSpesaVO2VoceDiSpesaDTO.put("idTipologiaVoceDiSpesa", "idTipologiaVoceDiSpesa");
	}
	

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public PbandiFornitoreQualificaDAOImpl getPbandiRFornitoreQualificaDAO() {
		return pbandiRFornitoreQualificaDAO;
	}

	public void setPbandiRFornitoreQualificaDAO(
			PbandiFornitoreQualificaDAOImpl pbandiRFornitoreQualificaDAO) {
		this.pbandiRFornitoreQualificaDAO = pbandiRFornitoreQualificaDAO;
	}

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public PbandiVociDiSpesaDAOImpl getPbandiVociDiSpesaDAO() {
		return pbandiVociDiSpesaDAO;
	}

	public void setPbandiVociDiSpesaDAO(
			PbandiVociDiSpesaDAOImpl pbandiVociDiSpesaDAO) {
		this.pbandiVociDiSpesaDAO = pbandiVociDiSpesaDAO;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setDocumentoDiSpesaManager(
			DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}


	private void controllaStato(Long idDocSpesa, Long idProgetto)
			throws GestioneVociDiSpesaException {
		logger.debug("controllo lo stato per idProgetto() " + idProgetto
				+ ",idDocSpesa():" + idDocSpesa);
		PbandiRDocSpesaProgettoVO vo = new PbandiRDocSpesaProgettoVO();
		vo.setIdDocumentoDiSpesa(new BigDecimal(idDocSpesa));
		vo.setIdProgetto(new BigDecimal(idProgetto));
		// vo.setIdProgetto(new BigDecimal(idProgetto));
		PbandiRDocSpesaProgettoVO[] ris = getGenericDAO().findWhere(vo);
		logger.debug("rdocSpesaProgetto trovati!");

		if (!isEmpty(ris))
			vo = ris[0];
		Long idStatoDocDiSpesa = NumberUtil.toLong(vo
				.getIdStatoDocumentoSpesa());
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
				idStatoDocDiSpesa);
		// String codiceRespinto=
		// DocumentoDiSpesaManager.getCodiceStatoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.STATO_RESPINTO);
		String codiceInserito = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
		if (!isNull(decodifica)) {
			if (!(decodifica.getDescrizioneBreve()
					.equalsIgnoreCase(codiceInserito))) {
				// ||decodifica.getDescrizioneBreve().equalsIgnoreCase(codiceRespinto)
				// )){

				GestioneVociDiSpesaException gde = new GestioneVociDiSpesaException(
						ERRORE_DOCUMENTO_DI_SPESA_NON_MODIFICABILE);
				logger
						.error(
								"Errore: il documento di spesa non e' modificabile a causa dello stato ",
								gde);
				throw gde;
			}
		}
	}


	/**
	 * Verifica che l'importo inserito dall' utente non sia superiore al residuo
	 * rendicontabile.
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkResiduoRedicontabileDocumento(VoceDiSpesaDTO dto) {
		logger.begin();
		try {

			List<VoceDiSpesaVO> vociVO = pbandiVociDiSpesaDAO
					.findVociDiSpesaAssociateADocumento(dto.getIdDocSpesa(),
							dto.getIdProgetto());
			List<DocumentoDiSpesaVO> documentiVO = pbandiDocumentiDiSpesaDAO
					.findImportiDocumentoDiSpesaProgetto(dto.getIdDocSpesa(),
							null);
			double importoRendicontabileDocumento = 0.0;

			for (DocumentoDiSpesaVO documentoVO : documentiVO) {
				if (documentoVO.getIdProgetto().equals(dto.getIdProgetto())
						&& documentoVO.getIdDocumentoDiSpesa().equals(
								dto.getIdDocSpesa())) {
					importoRendicontabileDocumento = documentoVO
							.getImportoRendicontabile() == null ? 0
							: documentoVO.getImportoRendicontabile();
					break;
				}
			}
			List<PbandiTQuotaParteDocSpesaVO> quoteVO = pbandiDocumentiDiSpesaDAO
					.findImportiQuotaParteByDocumentoDiSpesaProgetto(dto
							.getIdDocSpesa(), dto.getIdProgetto(), null);
			double totaleQuoteParte = 0.0;
			/**
			 * Nella somma dei totali non devo considerare l' importo gia'
			 * memorizzato per la voce di spesa che si sta inserendo o
			 * modificando
			 */
			for (PbandiTQuotaParteDocSpesaVO quotaVO : quoteVO) {
				if (dto.getIdQuotaParteDocSpesa() != null) {
					if (!(NumberUtil.toLong(quotaVO.getIdQuotaParteDocSpesa()))
							.equals(dto.getIdQuotaParteDocSpesa()))
						totaleQuoteParte += NumberUtil.getDoubleValue(quotaVO
								.getImportoQuotaParteDocSpesa());
				} else {
					totaleQuoteParte += NumberUtil.getDoubleValue(quotaVO
							.getImportoQuotaParteDocSpesa());
				}
			}

			double residuoRendicontabile = importoRendicontabileDocumento
					- totaleQuoteParte;
			logger
					.debug("residuoRendicontabile[importoRendicontabileDocumento:"
							+ importoRendicontabileDocumento
							+ "-totaleQuoteParte:"
							+ totaleQuoteParte
							+ "]="
							+ residuoRendicontabile+
							" ,importo=" + dto.getImporto());

			/*
			 * FIX: PBandi-304
			 */
			/*
			 * FIX: PBandi-550 Correzione arrotondamento
			 */
			if (NumberUtil.toRoundDouble(dto.getImporto()) <= NumberUtil
					.toRoundDouble(residuoRendicontabile))
				return true;
			else
				return false;
		} finally {
			logger.end();
		}
	}

	private Double getImportoResiduoRendicontabile(VoceDiSpesaDTO dto) {
		List<VoceDiSpesaVO> vociVO = pbandiVociDiSpesaDAO
				.findVociDiSpesaAssociateADocumento(dto.getIdDocSpesa(), dto
						.getIdProgetto());
		List<DocumentoDiSpesaVO> documentiVO = pbandiDocumentiDiSpesaDAO
				.findImportiDocumentoDiSpesaProgetto(dto.getIdDocSpesa(), null);
		double importoRendicontabileDocumento = 0.0;

		for (DocumentoDiSpesaVO documentoVO : documentiVO) {
			if (documentoVO.getIdProgetto().equals(dto.getIdProgetto())
					&& documentoVO.getIdDocumentoDiSpesa().equals(
							dto.getIdDocSpesa())) {
				importoRendicontabileDocumento = documentoVO
						.getImportoRendicontabile() == null ? 0 : documentoVO
						.getImportoRendicontabile();
				break;
			}
		}
		List<PbandiTQuotaParteDocSpesaVO> quoteVO = pbandiDocumentiDiSpesaDAO
				.findImportiQuotaParteByDocumentoDiSpesaProgetto(dto
						.getIdDocSpesa(), dto.getIdProgetto(), null);
		double totaleQuoteParte = 0.0;

		for (PbandiTQuotaParteDocSpesaVO quotaVO : quoteVO) {
			totaleQuoteParte += NumberUtil.getDoubleValue(quotaVO
					.getImportoQuotaParteDocSpesa());
		}

		double residuoRendicontabile = importoRendicontabileDocumento
				- totaleQuoteParte;
		/**
		 * FIX: PBandi-304
		 */
		// return residuoRendicontabile;
		/*
		 * FIX: PBandi-550 Correzione arrotondamento
		 */
		return NumberUtil.toRoundDouble(residuoRendicontabile);
	}

	/**
	 * Verifica che l'importo inserito dall' utente non sia superiore al residuo
	 * ammesso per la voce di spesa.
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkResiduoAmmessoVoceDiSpesa(VoceDiSpesaDTO dto) {
		logger.begin();
		try {
			double importoAmmessoFinanziamento = 0.0;
			List<VoceDiSpesaVO> vociVO = pbandiVociDiSpesaDAO.findVociDiSpesa(
					dto.getIdVoceDiSpesa(), dto.getIdRigoContoEconomico(), dto
							.getIdVoceDiSpesaPadre());
			for (VoceDiSpesaVO voceVO : vociVO) {
				if (voceVO.getIdVoceDiSpesa().equals(dto.getIdVoceDiSpesa())
						&& voceVO.getIdRigoContoEconomico().equals(
								dto.getIdRigoContoEconomico())) {
					// attenzione, se � null si rompe, per� � voluto
					importoAmmessoFinanziamento = voceVO
							.getImportoFinanziamento();
					break;
				}
			}

			List<PbandiTQuotaParteDocSpesaVO> quoteVO = pbandiDocumentiDiSpesaDAO
					.findImportiQuotaParteByDocumentoDiSpesaProgetto(null, dto
							.getIdProgetto(), dto.getIdRigoContoEconomico());
			double totaleQuoteParte = 0.0;
			/*
			 * Dal totale quote parte non cosidero l' importo relativo alla voce
			 * di spesa che viene inserita o modificata
			 */
			for (PbandiTQuotaParteDocSpesaVO quotaVO : quoteVO) {
				if (dto.getIdRigoContoEconomico().doubleValue() != NumberUtil
						.getDoubleValue(quotaVO.getIdRigoContoEconomico())) {
					totaleQuoteParte += NumberUtil.getDoubleValue(quotaVO
							.getImportoQuotaParteDocSpesa());
				}
			}

			double residuoAmmesso = importoAmmessoFinanziamento
					- totaleQuoteParte;
			logger.debug("residuoAmmesso=[importoAmmessoFinanziamento:"
					+ importoAmmessoFinanziamento + " - totaleQuoteParte:"
					+ totaleQuoteParte + "]=" + residuoAmmesso+
					",importo=" + dto.getImporto());

			/*
			 * FIX: PBandi-304
			 */
			/*
			 * FIX: PBandi-550 Correzione arrotondamento
			 */
			if (NumberUtil.toRoundDouble(dto.getImporto()) <= NumberUtil
					.toRoundDouble(residuoAmmesso))
				return true;
			else
				return false;
		} finally {
			logger.end();
		}
	}

	private Double getImportoResiduoAmmessoVoceDiSpesa(VoceDiSpesaDTO dto) {
		logger.begin();
		try {
			double importoAmmessoFinanziamento = 0.0;
			List<VoceDiSpesaVO> vociVO = pbandiVociDiSpesaDAO.findVociDiSpesa(
					dto.getIdVoceDiSpesa(), dto.getIdRigoContoEconomico(), dto
							.getIdVoceDiSpesaPadre());
			for (VoceDiSpesaVO voceVO : vociVO) {
				if (voceVO.getIdVoceDiSpesa().equals(dto.getIdVoceDiSpesa())
						&& voceVO.getIdRigoContoEconomico().equals(
								dto.getIdRigoContoEconomico())) {
					importoAmmessoFinanziamento = voceVO
							.getImportoFinanziamento() == null ? 0 : voceVO
							.getImportoFinanziamento();
					break;
				}
			}

			List<PbandiTQuotaParteDocSpesaVO> quoteVO = pbandiDocumentiDiSpesaDAO
					.findImportiQuotaParteByDocumentoDiSpesaProgetto(null, dto
							.getIdProgetto(), dto.getIdRigoContoEconomico());
			double totaleQuoteParte = 0.0;

			for (PbandiTQuotaParteDocSpesaVO quotaVO : quoteVO) {
				if (dto.getIdRigoContoEconomico().doubleValue() != NumberUtil
						.getDoubleValue(quotaVO.getIdRigoContoEconomico())) {
					totaleQuoteParte += NumberUtil.getDoubleValue(quotaVO
							.getImportoQuotaParteDocSpesa());
				}
			}

			double residuoAmmesso = importoAmmessoFinanziamento
					- totaleQuoteParte;
			/**
			 * FIX:PBandi-304
			 */
			// return residuoAmmesso;
			/*
			 * FIX: PBandi-550 Correzione arrotondamento
			 */
			return NumberUtil.toRoundDouble(residuoAmmesso);
		} finally {
			logger.end();
		}
	}

	private boolean checkVoceGiaAssegnata(VoceDiSpesaDTO dto) {
		logger.begin();
		try {
			boolean result = false;
			List<VoceDiSpesaVO> vociVO = pbandiVociDiSpesaDAO
					.findVociDiSpesaAssociateADocumento(dto.getIdDocSpesa(),
							dto.getIdProgetto());
			if (vociVO == null)
				return false;
			else {
				for (VoceDiSpesaVO voceVO : vociVO) {
					if (voceVO.getIdVoceDiSpesa()
							.equals(dto.getIdVoceDiSpesa())
							&& voceVO.getIdRigoContoEconomico().equals(
									dto.getIdRigoContoEconomico())
							&& !voceVO.getIdQuotaParteDocSpesa().equals(
									dto.getIdQuotaParteDocSpesa())) {
						result = true;
						break;
					}

				}
			}
			return result;
		} finally {
			logger.end();
		}
	}

	private void validazioneInput(Long idUtente, String identitaDigitale,
			VoceDiSpesaDTO dto) throws FormalParameterException,
			ManagerException {
		logger.begin();
		try {

			List<String> nameParameters = new ArrayList<String>();
			List<Object> inputObjs = new ArrayList<Object>();

			nameParameters.add("idUtente");
			nameParameters.add("identitaDigitale");
			nameParameters.add("idDocumentoDiSpesa");
			nameParameters.add("idProgetto");
			nameParameters.add("idRigoContoEconomico");
			nameParameters.add("idVoceDiSpesa");
			nameParameters.add("importo");
			nameParameters.add("idTipoDocumentoDiSpesa");

			inputObjs.add(idUtente);
			inputObjs.add(identitaDigitale);
			inputObjs.add(dto.getIdDocSpesa());
			inputObjs.add(dto.getIdProgetto());
			inputObjs.add(dto.getIdRigoContoEconomico());
			inputObjs.add(dto.getIdVoceDiSpesa());
			inputObjs.add(dto.getImporto());
			inputObjs.add(dto.getIdTipoDocumentoDiSpesa());
			ValidatorInput.verifyNullValue(nameParameters
					.toArray(new String[] {}), inputObjs.toArray());

			if (documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(dto
					.getIdTipoDocumentoDiSpesa())) {

				logger
						.debug("La voce di spesa e' relativa ad un documento di tipo CEDOLINO o AUTODICHIARAZIONE PRESTAZIONE SOCI");

				// nameParameters.add("costoOrario");
				nameParameters.add("oreLavorate");

				// inputObjs.add(dto.getCostoOrario());
				inputObjs.add(dto.getOreLavorate());
			}

			ValidatorInput.verifyNullValue(nameParameters
					.toArray(new String[] {}), inputObjs.toArray());
		} finally {
			logger.end();
		}
	}

	/**
	 * Verifica che l' importo inserito non sia inferiore alla somma dei
	 * pagamenti associati alla voce di spesa per il progetto.
	 * 
	 * @param dto
	 * @return
	 */
	private boolean checkImportoPagamenti(VoceDiSpesaDTO dto) {
		logger.begin();
		try {
			List<PbandiRPagQuotParteDocSpVO> pagamentiVO = pbandiVociDiSpesaDAO
					.findPagamentiAssociatiVoceDiSpesa(dto
							.getIdQuotaParteDocSpesa());
			double totalePagamenti = 0.0;
			double importo = dto.getImporto() == null ? 0 : dto.getImporto();
			for (PbandiRPagQuotParteDocSpVO pagamentoVO : pagamentiVO) {
				totalePagamenti += NumberUtil.getDoubleValue(pagamentoVO
						.getImportoQuietanzato());
			}
			logger.debug("totalePagamenti:" + totalePagamenti + " importo:"
					+ importo);
			/*
			 * FIX: PBandi-304
			 */
			// if (importo < totalePagamenti)
			/*
			 * FIX: PBandi-550 Correzione arrotondamento
			 */
			if (NumberUtil.toRoundDouble(importo) < (NumberUtil
					.toRoundDouble(totalePagamenti)))
				return false;
			else
				return true;

		} finally {
			logger.end();
		}
	}

	private Double getImportoPagamenti(VoceDiSpesaDTO dto) {
		logger.begin();
		try {
			List<PbandiRPagQuotParteDocSpVO> pagamentiVO = pbandiVociDiSpesaDAO
					.findPagamentiAssociatiVoceDiSpesa(dto
							.getIdQuotaParteDocSpesa());
			double totalePagamenti = 0.0;
			for (PbandiRPagQuotParteDocSpVO pagamentoVO : pagamentiVO) {
				totalePagamenti += NumberUtil.getDoubleValue(pagamentoVO
						.getImportoQuietanzato());
			}
			/*
			 * FIX: Pbandi-304
			 */
			// return totalePagamenti;
			/*
			 * FIX: PBandi-550 Correzione arrotondamento
			 */
			return NumberUtil.toRoundDouble(totalePagamenti);

		} finally {
			logger.end();
		}
	}

	private VoceDiSpesaVO findVoceDiSpesa(VoceDiSpesaDTO dto) {
		logger.begin();
		try {
			VoceDiSpesaVO vo = pbandiVociDiSpesaDAO.findVoceDiSpesa(dto
					.getIdVoceDiSpesa(), dto.getIdRigoContoEconomico());
			return vo;

		} finally {
			logger.end();
		}
	}

	public VoceDiSpesaDTO[] findVociDiSpesaAssociateSemplificazione(
			Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneVociDiSpesaException {
		logger.begin();
		try {
			List<VoceDiSpesaDTO> result = new ArrayList<VoceDiSpesaDTO>();
			
			String[] nameParameter = { "idUtente", "identitaDigitale", "idDocumentoDiSpesa","idProgetto"};
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoDiSpesa, idProgetto);
			
			VoceDiSpesaDocumentoVO filterVO = new VoceDiSpesaDocumentoVO();
			filterVO.setIdDocumentoDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa));
			filterVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			List<VoceDiSpesaDocumentoVO> listVociAssociate = genericDAO.findListWhere(filterVO);
			
			if (!ObjectUtil.isEmpty(listVociAssociate)) {
				/*
				 * Ricerco tutte le voci di spesa associate al progetto
				 * per tutti i documenti
				 */
				VoceDiSpesaDocumentoVO filterTuttiDocumentiVO = new VoceDiSpesaDocumentoVO();
				filterTuttiDocumentiVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
				filterTuttiDocumentiVO.setAscendentOrder("idVoceDiSpesa");
				List<VoceDiSpesaDocumentoVO> listVociAssociateTuttiDocumenti = genericDAO.findListWhere(filterTuttiDocumentiVO);
				
				for (VoceDiSpesaDocumentoVO vo : listVociAssociate) {
					
					VoceDiSpesaDTO dto = beanUtil.transform(vo, VoceDiSpesaDTO.class, fromVoceDiSpesaVO2VoceDiSpesaDTO);
					/*
					 * Calcolo il costo orario come il rapporto tra il l' importo della quota parte
					 * ed il numero di ore lavorate
					 */
			
					if (!ObjectUtil.isNull(dto.getImporto()) && !ObjectUtil.isNull(dto.getOreLavorate())) {
						if (dto.getImporto() != 0 && dto.getOreLavorate() != 0) {
							BigDecimal	costoOrario = NumberUtil.divide(dto.getImporto(), dto.getOreLavorate());
							dto.setCostoOrario(NumberUtil.toDouble(costoOrario));
						}
					}
					
					/*
					 * Calcolo il residuo ammesso come la differenza tra l' ammesso a finanziamento
					 * ed il rendicontato sulla voce di spesa per tutti i documenti
					 */
					BigDecimal totaleRendicontatoVoce = new BigDecimal(0);
					for (VoceDiSpesaDocumentoVO voce : listVociAssociateTuttiDocumenti) {
						if (voce.getIdVoceDiSpesa().compareTo(vo.getIdVoceDiSpesa()) == 0) {
							/*
							 * PBANDI-2243 Nel caso di Nota di credito, l' importo associato alla voce
							 * deve essere sottratto al totale rendicontato
							 */
							if (voce.getDescBreveTipoDocSpesa().equals(it.csi.pbandi.pbweb.pbandiutil.common.Constants.TIPO_DOCUMENTO_DI_SPESA_NOTA_CREDITO))
								totaleRendicontatoVoce = NumberUtil.subtract(totaleRendicontatoVoce, voce.getImportoVoceDiSpesa());
							else
								totaleRendicontatoVoce = NumberUtil.sum(totaleRendicontatoVoce, voce.getImportoVoceDiSpesa());
						}
					}
					BigDecimal importoAmmesso = vo.getImportoAmmessoFinanziamento() == null ? new BigDecimal(0) : vo.getImportoAmmessoFinanziamento();
					BigDecimal importoResiduoAmmesso = NumberUtil.subtract(importoAmmesso, totaleRendicontatoVoce);
					dto.setImportoResiduoAmmesso(NumberUtil.toDouble(importoResiduoAmmesso));
					result.add(dto);	
				}
			}
			return result.toArray(new VoceDiSpesaDTO[]{});
		} finally {
			logger.end();
		}
	}

	
	public VoceDiSpesaDTO[] findVociDiSpesaProgettoSemplificazione(
			Long idUtente, String identitaDigitale, Long idProgetto, Long idDocumentoDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneVociDiSpesaException {
			String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "idDocumentoDiSpesa"};
				
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto, idDocumentoDiSpesa);
			logger.info("\n\n\nfindVociDiSpesaProgettoSemplificazione for idProgetto: "+idProgetto+" ,idDocumentoDiSpesa:"+idDocumentoDiSpesa);
			/*
			 * Se il documento e' una nota di credito allora le voci di spesa
			 * sono quelle associate alla documento al quale la nota e' legata.
			 */
			PbandiTDocumentoDiSpesaVO documentoVO = new PbandiTDocumentoDiSpesaVO();
			documentoVO.setIdDocumentoDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa));
			documentoVO = genericDAO.findSingleWhere(documentoVO);
			logger.info("findVociDiSpesaProgettoSemplificazione(): IdTipoDocumentoSpesa = "+documentoVO.getIdTipoDocumentoSpesa());			
			if (documentoDiSpesaManager.isNotaDiCredito(NumberUtil.toLong(documentoVO.getIdTipoDocumentoSpesa())) ){
				Long idDocumentoDiRiferimento = NumberUtil.toLong(documentoVO.getIdDocRiferimento());
				logger.info("� una notadicredito, findVociDiSpesaAssociateSemplificazione con id doc di riferimento "+idDocumentoDiRiferimento);
				return findVociDiSpesaAssociateSemplificazione(idUtente, identitaDigitale, idDocumentoDiRiferimento, idProgetto);
				
			} else {
				
				/*
				 * Ricerco il contoeconomico master del progetto
				 */
				BigDecimal idContoMaster = null;
				try {
					logger.info("contoEconomicoManager.getIdContoMaster for  idProgetto:"+idProgetto);
					idContoMaster = contoEconomicoManager.getIdContoMaster(BigDecimal.valueOf(idProgetto));
					logger.info("idContoMaster : "+idContoMaster);
				} catch (ContoEconomicoNonTrovatoException e) {
					logger.error("Errore durante la ricerca del conto economico master per il progetto["+idProgetto+"]",e);
					throw new GestioneVociDiSpesaException("Errore durante la ricerca del conto economico master per il progetto["+idProgetto+"]",e);
				}
				
				/*
				 * Ricerco le voci di spesa assegnate al conto economico master del progetto
				 */
				VociDiSpesaPadriFigliContoEconomicoVO filterVO = new VociDiSpesaPadriFigliContoEconomicoVO();
				filterVO.setIdContoEconomico(idContoMaster);
				filterVO.setAscendentOrder("descVoceDiSpesaPadre","descVoceDiSpesa");
				List<VociDiSpesaPadriFigliContoEconomicoVO> voci = genericDAO.findListWhere(filterVO);
				
				VoceDiSpesaDTO[] result = beanUtil.transform(voci, VoceDiSpesaDTO.class, fromVoceDiSpesaVO2VoceDiSpesaDTO);
				if (!ObjectUtil.isEmpty(result)) {
					
					/*
					 * Ricerco le voci di spesa associate a tutti i documenti del progetto
					 */
					VoceDiSpesaDocumentoVO filterTuttiDocumentiVO = new VoceDiSpesaDocumentoVO();
					filterTuttiDocumentiVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
					filterTuttiDocumentiVO.setAscendentOrder("idVoceDiSpesa");
					List<VoceDiSpesaDocumentoVO> listVociAssociateTuttiDocumenti = genericDAO.findListWhere(filterTuttiDocumentiVO);
					for (VoceDiSpesaDTO dto : result) {
						/*
						 * Calcolo il residuo ammesso come la differenza tra l' ammesso a finanziamento
						 * ed il rendicontato sulla voce di spesa per tutti i documenti
						 */
						BigDecimal importoAmmesso = dto.getImportoFinanziamento() == null ? new BigDecimal(0) : NumberUtil.createScaledBigDecimal(dto.getImportoFinanziamento());
						BigDecimal totaleRendicontatoVoce = new BigDecimal(0);
						for (VoceDiSpesaDocumentoVO voceDocumento : listVociAssociateTuttiDocumenti) {
							if (voceDocumento.getIdVoceDiSpesa().compareTo(NumberUtil.createScaledBigDecimal(dto.getIdVoceDiSpesa())) == 0) {
								totaleRendicontatoVoce = NumberUtil.sum(totaleRendicontatoVoce, voceDocumento.getImportoVoceDiSpesa());
							}
						}
						BigDecimal importoResiduoAmmesso = NumberUtil.subtract(importoAmmesso, totaleRendicontatoVoce);
						dto.setImportoResiduoAmmesso(NumberUtil.toDouble(importoResiduoAmmesso));
					}
				}
				logger.info("Trovate vds: "+(result!=null?result.length:0));
				
				// Tengo conto di eventuali voci di spesa presenti in PBANDI_R_BANDO_VOCE_SP_TIP_DOC.
				return riduciVodiDiSpesa(
						BigDecimal.valueOf(idProgetto), 
						documentoVO.getIdTipoDocumentoSpesa(), 
						result);
			}		
	}
	
	private boolean isPresente(VoceDiSpesaDTO dto, ArrayList<VoceDiSpesaTipoDocVO> elenco) {
		if (elenco == null)
			return false;
		for (VoceDiSpesaTipoDocVO vo : elenco) {
			if (dto.getIdVoceDiSpesa().intValue() == vo.getIdVoceDiSpesa().intValue())
				return true;
		}
		return false;
	}
	
	// Verifico se ci sono voci di spesa nella PBANDI_R_BANDO_VOCE_SP_TIP_DOC:
	// in caso affermativo dalle voci di spesa in input vanno considerate 
	// solo quelle presenti in PBANDI_R_BANDO_VOCE_SP_TIP_DOC.
	private VoceDiSpesaDTO[] riduciVodiDiSpesa(BigDecimal idProgetto, BigDecimal idTipoDocumentoSpesa, VoceDiSpesaDTO[] input) {		
		ArrayList<VoceDiSpesaTipoDocVO> elenco = null;
		elenco = vociDiSpesaByIdTipoDocumentoSpesa(idProgetto, idTipoDocumentoSpesa);
		if (elenco == null || elenco.size() == 0) {
			// Le voci di spesa in input vanno bene.
			return input;
		} else {
			logElencoVociDiSpesaDTO("ELENCO VOCI DI SPESA ORIGINALE", input);
			logElencoVociDiSpesaVO("ELENCO VOCI DI SPESA PBANDI_R_BANDO_VOCE_SP_TIP_DOC", elenco);
			// Creo un nuovo elenco di voci contenente solo quelle presenti in PBANDI_R_BANDO_VOCE_SP_TIP_DOC
			ArrayList<VoceDiSpesaDTO> output = new ArrayList<VoceDiSpesaDTO>();
			for (VoceDiSpesaDTO voceInput : input) {
				if (isPresente(voceInput, elenco)) {
					output.add(voceInput);
				}
			}
			VoceDiSpesaDTO[] output1 = output.toArray(new VoceDiSpesaDTO[]{});		
			logElencoVociDiSpesaDTO("ELENCO VOCI DI SPESA RIDOTTO", output1);
			return output1;
		}
	}
	
	private void logElencoVociDiSpesaDTO(String titolo, VoceDiSpesaDTO[] elenco) {
		if (elenco == null)
			return;
		String s = "\n\n"+titolo+"\n";
		for (VoceDiSpesaDTO v : elenco) {
			if (v != null)
				s += "   "+v.getIdVoceDiSpesa()+"  "+v.getDescVoceDiSpesa()+"  -  VOCE PADRE: "+v.getIdVoceDiSpesaPadre()+"  "+v.getDescVoceDiSpesaPadre()+"\n";
		}
		s += "\n";
		logger.info(s);
	}
	
	private void logElencoVociDiSpesaVO(String titolo, ArrayList<VoceDiSpesaTipoDocVO> elenco) {
		if (elenco == null)
			return;
		String s = "\n\n"+titolo+"\n";
		for (VoceDiSpesaTipoDocVO v : elenco) {
			if (v != null)
				s += "   "+v.getIdVoceDiSpesa()+"  "+v.getDescVoceDiSpesa()+"  -  VOCE PADRE: "+v.getIdVoceDiSpesaPadre()+"  "+v.getDescVoceDiSpesaPadre()+"\n";
		}
		s += "\n";
		logger.info(s);
	}
	
	// Legge le voci di spesa da PBANDI_R_BANDO_VOCE_SP_TIP_DOC.
	private ArrayList<VoceDiSpesaTipoDocVO> vociDiSpesaByIdTipoDocumentoSpesa(BigDecimal idProgetto, BigDecimal idTipoDocumentoSpesa) {
		// Trovo il bando associato al Progetto.
		BandoLineaProgettoVO bando = new BandoLineaProgettoVO();
		bando.setIdProgetto(idProgetto);
		bando = genericDAO.findSingleWhere(bando);
		// Recupero le voci di spesa (possono essere sia padri sia figli).
		VoceDiSpesaTipoDocVO filtro = new VoceDiSpesaTipoDocVO();
		filtro.setIdBando(bando.getIdBando());
		filtro.setIdTipoDocumentoSpesa(idTipoDocumentoSpesa);
		filtro.setAscendentOrder("descVoceDiSpesaPadre","descVoceDiSpesa");
		ArrayList<VoceDiSpesaTipoDocVO> elenco = null;
		elenco = (ArrayList<VoceDiSpesaTipoDocVO>) genericDAO.findListWhere(filtro);
		for (VoceDiSpesaTipoDocVO item : elenco)
			logger.info("vociDiSpesaByIdTipoDocumentoSpesa(): "+item.toString());
		//return beanUtil.transform(elenco, VoceDiSpesaDTO.class, fromVoceDiSpesaVO2VoceDiSpesaDTO);
		return elenco;
	}
	
	/**
	 * Verifica se la voce di spesa e' gia' associata al documento di spesa ed al progetto
	 * @param idDocumentoDiSpesa
	 * @param idProgetto
	 * @param idVoceDiSpesa
	 * @return
	 */
	private boolean isVoceAssociata(Long idDocumentoDiSpesa, Long idProgetto, Long idVoceDiSpesa) {
		boolean isAssociata = false;
		VoceDiSpesaDocumentoVO filterVO = new VoceDiSpesaDocumentoVO();
		filterVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filterVO.setAscendentOrder("idVoceDiSpesa");

		List<VoceDiSpesaDocumentoVO> listVociAssociateDocumento = genericDAO.findListWhere(filterVO);
		
		if (!ObjectUtil.isEmpty(listVociAssociateDocumento)) {
			for (VoceDiSpesaDocumentoVO voce : listVociAssociateDocumento) {		
				if (idVoceDiSpesa.compareTo(NumberUtil.toLong(voce.getIdVoceDiSpesa())) == 0) {
					isAssociata = true;
					break;
				}
			}
		}
		return isAssociata;
	}
	

	public EsitoOperazioneVociDiSpesa associaVoceDiSpesaDocumentoSemplificazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDocumentoDiSpesa, VoceDiSpesaDTO dto,
			Boolean forzaSalvataggio) throws CSIException, SystemException,
			UnrecoverableException, GestioneVociDiSpesaException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "idDocumentoDiSpesa", "voce", "voce.importo"};
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto, idDocumentoDiSpesa, dto, dto.getImporto());
			
			EsitoOperazioneVociDiSpesa esito = new EsitoOperazioneVociDiSpesa();
			esito.setEsito(Boolean.FALSE);
			
			BigDecimal importoQuotaParte = NumberUtil.createScaledBigDecimal(dto.getImporto());
			boolean isError = false;

			/*
			 * Verifico che non esista gia' l' associazione tra la voce di spesa ed il documento
			 */
			
			boolean isAssociata = isVoceAssociata(idDocumentoDiSpesa, idProgetto, dto.getIdVoceDiSpesa());
			
			
			if (isAssociata) {
				esito.setMessage(ErrorConstants.ERRORE_SEMPL_VOCE_GIA_ASSOCIATA);
				isError = true;
			} else {
				/*
				 * Verifico che il totale rendicontato ( + l'importo dell' associazione) non sia maggiore del rendicontabile del documento
				 * per il progetto
				 */
				if (!verificaImportoQuotaParteConResiduoRendicontabile(idDocumentoDiSpesa, idProgetto, null, importoQuotaParte) ) {
					esito.setMessage(ErrorConstants.ERRORE_SEMPL_VOCE_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO);
					isError = true;
				} else if (!forzaSalvataggio) {
					/*
					 * Verifico che la somma delle associazioni non superi il valore ammesso a finanziamento
					 * per la voce di spesa
					 */
					BigDecimal importoAmmessoFinanziamento = NumberUtil.createScaledBigDecimal(dto.getImportoFinanziamento());
					
					if (!verificaAmmessoVoce(dto.getIdVoceDiSpesa(), idProgetto, null, importoAmmessoFinanziamento, importoQuotaParte)) {
						esito.setMessage(MessaggiConstants.MSG_WARNING_VOCE_DI_SPESA_SUPERATO_RESIDUO_AMMESSO);
						isError = true;
					}
				}
			}
			
			
			if (!isError) {
				/*
				 * Eseguo l'inserimento
				 */
				PbandiTQuotaParteDocSpesaVO quotaParteVO = new PbandiTQuotaParteDocSpesaVO();
				quotaParteVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
				quotaParteVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
				quotaParteVO.setIdRigoContoEconomico(NumberUtil.toBigDecimal(dto.getIdRigoContoEconomico()));
				quotaParteVO.setImportoQuotaParteDocSpesa(importoQuotaParte);
				quotaParteVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
				quotaParteVO.setOreLavorate(NumberUtil.createScaledBigDecimal(dto.getOreLavorate()));
				
				try {
					genericDAO.insert(quotaParteVO);
				} catch (Exception e) {
					logger.error("Errore durante l' inserimento della quota parte della voce di spesa per il documento["+idDocumentoDiSpesa+"] del progetto ["+idProgetto+"]", e);
					throw new GestioneVociDiSpesaException("Errore durante l' inserimento della quota parte della voce di spesa per il documento["+idDocumentoDiSpesa+"] del progetto ["+idProgetto+"]", e);
				}
				
				esito.setEsito(Boolean.TRUE);
				esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			}
			
			return esito;
			
		} finally {
			logger.end();
		}
		
		
	}

	public EsitoOperazioneVociDiSpesa eliminaVoceDiSpesaDocumentoSemplificazione(
			Long idUtente, String identitaDigitale, Long idQuotaParteDocSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneVociDiSpesaException {
		
			EsitoOperazioneVociDiSpesa esito = new EsitoOperazioneVociDiSpesa();
			esito.setEsito(Boolean.FALSE);
			String[] nameParameter = { "idUtente", "identitaDigitale","idQuotaParteDocSpesa"};
			logger.info("eliminaVoceDiSpesaDocumentoSemplificazione: idQuotaParteDocSpesa "+idQuotaParteDocSpesa);
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idQuotaParteDocSpesa);
			
			/*
			 * Se esistono dei pagamenti associati alla quota parte che si vuole
			 * cancellare, la cancellazione non e' possibile
			 */
			PbandiRPagQuotParteDocSpVO filterVO = new PbandiRPagQuotParteDocSpVO();
			filterVO.setIdQuotaParteDocSpesa(NumberUtil.toBigDecimal(idQuotaParteDocSpesa));
			
			List<PbandiRPagQuotParteDocSpVO> pagamentiAssociatiVoce = genericDAO.findListWhere(filterVO);
			
			if (ObjectUtil.isEmpty(pagamentiAssociatiVoce)) {
				PbandiTQuotaParteDocSpesaVO quotaParteVO = new PbandiTQuotaParteDocSpesaVO();
				quotaParteVO.setIdQuotaParteDocSpesa(NumberUtil.toBigDecimal(idQuotaParteDocSpesa));				
				try {
					genericDAO.delete(quotaParteVO);
					esito.setEsito(Boolean.TRUE);
					esito.setMessage(ErrorMessages.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
				} catch (Exception e) {
					logger.error("Errore durante la cancellazione della quota parte ["+idQuotaParteDocSpesa+"]",e);
					throw new GestioneVociDiSpesaException("Errore durante la cancellazione della quota parte ["+idQuotaParteDocSpesa+"]",e);
				}
			} else {
				esito.setMessage(ErrorMessages.ERRORE_VOCE_DI_SPESA_ASSOCIATA_PAGAMENTI);
			}
			
			return esito;
			
		 
	}

	public EsitoOperazioneVociDiSpesa modificaVoceDiSpesaDocumentoSemplificazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDocumentoDiSpesa, VoceDiSpesaDTO voce, Boolean forzaSalvataggio) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneVociDiSpesaException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "idDocumentoDiSpesa", "voce", "voce.importo","voce.idQuotaParteDocSpesa"};
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto, idDocumentoDiSpesa, voce, voce.getImporto(), voce.getIdQuotaParteDocSpesa());
			
			EsitoOperazioneVociDiSpesa esito = new EsitoOperazioneVociDiSpesa();
			esito.setEsito(Boolean.FALSE);
			
			boolean isError = false;
			boolean isAssociata = false;
			
			BigDecimal importoQuotaParte = NumberUtil.createScaledBigDecimal(voce.getImporto());
			
			/*
			 * Verifico che non esista gia' l' associazione tra la voce di spesa ed il documento
			 * solo se l'utente ha effettivamente modificato le voci di spesa.
			 * Per fare questo controllo idRigoContoEconomico della quota parte con quello del dto
			 */
			PbandiTQuotaParteDocSpesaVO filtroQuotaParteVO = new PbandiTQuotaParteDocSpesaVO();
			filtroQuotaParteVO.setIdQuotaParteDocSpesa(NumberUtil.toBigDecimal(voce.getIdQuotaParteDocSpesa()));
			filtroQuotaParteVO = genericDAO.findSingleWhere(filtroQuotaParteVO);
			
			if (NumberUtil.toBigDecimal(voce.getIdRigoContoEconomico()).compareTo(filtroQuotaParteVO.getIdRigoContoEconomico()) != 0) {
				 isAssociata = isVoceAssociata(idDocumentoDiSpesa, idProgetto, voce.getIdVoceDiSpesa());
			}
			
			
			
			
			if (isAssociata) {
				esito.setMessage(ErrorConstants.ERRORE_SEMPL_VOCE_GIA_ASSOCIATA);
				isError = true;
			} else {
				
				/*
				 * Verifico che il totale rendicontato ( + l'importo dell' associazione) non sia maggiore del rendicontabile del documento
				 * per il progetto
				 */
				if (!verificaImportoQuotaParteConResiduoRendicontabile(idDocumentoDiSpesa, idProgetto, voce.getIdQuotaParteDocSpesa(), importoQuotaParte) ) {
					esito.setMessage(ErrorConstants.ERRORE_SEMPL_VOCE_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO);
					isError = true;
				} else if (!forzaSalvataggio) {
					/*
					 * Verifico che la somma delle associazioni non superi il valore ammesso a finanziamento
					 * per la voce di spesa
					 */
					BigDecimal importoAmmessoFinanziamento = NumberUtil.createScaledBigDecimal(voce.getImportoFinanziamento());
					
					if (!verificaAmmessoVoce(voce.getIdVoceDiSpesa(), idProgetto, voce.getIdQuotaParteDocSpesa(), importoAmmessoFinanziamento, importoQuotaParte)) {
						esito.setMessage(MessaggiConstants.MSG_WARNING_VOCE_DI_SPESA_SUPERATO_RESIDUO_AMMESSO);
						isError = true;
					}
				}
				
				if (!isError && regolaManager.isRegolaApplicabileForProgetto(idProgetto, RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA)) {
					String msgParam = new String();
					if (!verificaImportoQuotaParteConPagamentiQuietanzatiVoce(voce.getIdQuotaParteDocSpesa(), importoQuotaParte, msgParam)) {
						esito.setMessage(ErrorConstants.ERRORE_SEMPL_VOCE_IMPORTO_MINORE_ASSOCIATO_PAGAMENTI);
						esito.setParams(new String []{msgParam});
						isError = true;
					}
				}
				
			}
			
			
			
			if (!isError) {
				/*
				 * Eseguo l'aggiornamento dell' associazione tra la voce di spesa ed il documento
				 */
				PbandiTQuotaParteDocSpesaVO quotaParteVO = new PbandiTQuotaParteDocSpesaVO();
				quotaParteVO.setIdQuotaParteDocSpesa(NumberUtil.toBigDecimal(voce.getIdQuotaParteDocSpesa()));
				quotaParteVO.setImportoQuotaParteDocSpesa(importoQuotaParte);
				quotaParteVO.setOreLavorate(NumberUtil.createScaledBigDecimal(voce.getOreLavorate()));
				quotaParteVO.setIdRigoContoEconomico(NumberUtil.toBigDecimal(voce.getIdRigoContoEconomico()));
				quotaParteVO.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
				try {
					genericDAO.update(quotaParteVO);
					esito.setEsito(Boolean.TRUE);
					esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} catch (Exception e) {
					logger.error("Errore durante la modifica della quota parte ["+voce.getIdQuotaParteDocSpesa()+"]",e);
					throw new GestioneVociDiSpesaException("Errore durante la modifica della quota parte ["+voce.getIdQuotaParteDocSpesa()+"]",e);
				}
				
			}
			return esito;
			
			
		} finally {
			logger.end();
		}
		
	}	
	
	private boolean verificaImportoQuotaParteConResiduoRendicontabile(Long idDocumentoDiSpesa, Long idProgetto, Long idQuotaParteDocSpesa, BigDecimal importoQuotaParte ) {
		VoceDiSpesaDocumentoVO filterVO = new VoceDiSpesaDocumentoVO();
		filterVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filterVO.setAscendentOrder("idVoceDiSpesa");

		List<VoceDiSpesaDocumentoVO> listVociAssociateDocumento = genericDAO.findListWhere(filterVO);
		
		boolean isAssociata = false;
		boolean isError = false;
		BigDecimal totaleRendicontato = new BigDecimal(0);
		
		if (!ObjectUtil.isEmpty(listVociAssociateDocumento)) {
			for (VoceDiSpesaDocumentoVO voce : listVociAssociateDocumento) {
				/*
				 * Nel caso in cui e' valorizzato idQuotaParteDocSpesa, nel totale rendicontanto non devo
				 * sommare l' importo della quota parte che si sta modificando.
				 */
				if (idQuotaParteDocSpesa == null || voce.getIdQuotaParteDocSpesa().compareTo(NumberUtil.toBigDecimal(idQuotaParteDocSpesa)) != 0)
					totaleRendicontato = NumberUtil.sum(totaleRendicontato, voce.getImportoVoceDiSpesa());
			}
		} 
		
		
		PbandiRDocSpesaProgettoVO documentoVO = new PbandiRDocSpesaProgettoVO();
		documentoVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		documentoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		
		documentoVO = genericDAO.findSingleWhere(documentoVO);
		
		
		BigDecimal importoRendicontabile = documentoVO.getImportoRendicontazione();
		BigDecimal importoResiduoRendicontabile = NumberUtil.subtract(importoRendicontabile, totaleRendicontato);
		
		/*
		 * Verifico che il totale rendicontato ( + l'importo dell' associazione) non sia maggiore del rendicontabile del documento
		 * per il progetto
		 */
		if (importoQuotaParte.compareTo(importoResiduoRendicontabile) > 0 ) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean verificaAmmessoVoce(Long idVoceDiSpesa, Long idProgetto, Long idQuotaParteDocSpesa, BigDecimal importoAmmessoFinanziamento, BigDecimal importoQuotaParte) {
		/*
		 * Verifico che la somma delle associazioni non superi il valore ammesso a finanziamento
		 * per la voce di spesa
		 */
		
		VoceDiSpesaDocumentoVO filterVO = new VoceDiSpesaDocumentoVO();
		filterVO.setIdVoceDiSpesa(NumberUtil.toBigDecimal(idVoceDiSpesa));
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filterVO.setAscendentOrder("idVoceDiSpesa");
		BigDecimal totaleAssociatoVoce = new BigDecimal(0);

		List<VoceDiSpesaDocumentoVO> listVociAssociateProgetto = genericDAO.findListWhere(filterVO);
		if (ObjectUtil.isEmpty(listVociAssociateProgetto)) {
			for (VoceDiSpesaDocumentoVO voce : listVociAssociateProgetto) {
				/*
				 * Nel caso in cui e' valorizzato idQuotaParteDocSpesa, nel totale associato non devo
				 * sommare l' importo della quota parte che si sta modificando.
				 */
				if (idQuotaParteDocSpesa != null && voce.getIdQuotaParteDocSpesa().compareTo(NumberUtil.toBigDecimal(idQuotaParteDocSpesa)) != 0)
					totaleAssociatoVoce = NumberUtil.sum(totaleAssociatoVoce, voce.getImportoVoceDiSpesa());
			}
		}
		BigDecimal importoResiduoAmmesso = NumberUtil.subtract(importoAmmessoFinanziamento, totaleAssociatoVoce);
		if (importoQuotaParte.compareTo(importoResiduoAmmesso) > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean verificaImportoQuotaParteConPagamentiQuietanzatiVoce (Long idQuotaParteDocSpesa, BigDecimal importoQuotaParte, String paramsForMessage ) {
		/*
		 * Ricerco i pagamenti associati alla quota parte
		 */
		PbandiRPagQuotParteDocSpVO filterVO = new PbandiRPagQuotParteDocSpVO();
		filterVO.setIdQuotaParteDocSpesa(NumberUtil.toBigDecimal(idQuotaParteDocSpesa));
		List<PbandiRPagQuotParteDocSpVO> pagamentiAssociatiVoce = genericDAO.findListWhere(filterVO);
		
		BigDecimal totalePagamentiQuietanzati = new BigDecimal(0);
		if (!ObjectUtil.isEmpty(pagamentiAssociatiVoce)) {
			for (PbandiRPagQuotParteDocSpVO pagamentoAssociato : pagamentiAssociatiVoce) {
				totalePagamentiQuietanzati = NumberUtil.sum(totalePagamentiQuietanzati, pagamentoAssociato.getImportoQuietanzato());
			}
		}
		
		if (importoQuotaParte.compareTo(totalePagamentiQuietanzati) < 0) {
			paramsForMessage = NumberUtil.getStringValue(totalePagamentiQuietanzati);
			
			return false;
		} else {
			return true;
		}
		
		
		
	}

	
	
	
}
