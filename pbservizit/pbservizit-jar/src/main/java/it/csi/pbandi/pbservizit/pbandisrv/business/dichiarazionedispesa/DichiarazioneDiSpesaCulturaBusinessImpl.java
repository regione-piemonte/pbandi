/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.dichiarazionedispesa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.manager.DocumentiFSManager;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.impl.ContoEconomicoDAOImpl;
import it.csi.pbandi.pbservizit.integration.vo.ComunicazioneFineProgettoDTO;
import it.csi.pbandi.pbservizit.integration.vo.DeclaratoriaDTO;
import it.csi.pbandi.pbservizit.integration.vo.EsitoOperazioneSalvaComunicazioneFineProgetto;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
//import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TimerManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TipoAllegatiManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoContabileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaComunicazioneFineProgetto;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneInviaDichiarazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneVerificaDichiarazioneSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.InfoDichiarazioneSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.fineprogetto.DocumentoDiSpesaComunicazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VociDiEntrataCulturaDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoRelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.DocumentNotCreatedException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaDichiarazioneTotalePagamentiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaDaInviareVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.NotaDiCreditoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.PagamentoDocumentoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.QuotaParteTipoDocumentoFornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoIndexAssociatoDocSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoDocumentoDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LessThanOrEqualCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPagamentoDichSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPagamentoDocSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.dichiarazionedispesa.DichiarazioneDiSpesaCulturaSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 */
public class DichiarazioneDiSpesaCulturaBusinessImpl extends BusinessImpl implements DichiarazioneDiSpesaCulturaSrv {
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	@Autowired
	private DocumentoManager documentoManager;
	@Autowired
	private DocumentiFSManager documentiFSManager;
	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private NeofluxSrv neofluxBusiness;
	@Autowired
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAOImpl;
	
	@Autowired
	private PopolaTemplateManager popolaTemplateManager;
	@Autowired
	private ProgettoManager progettoManager;
	@Autowired
	private RappresentanteLegaleManager rappresentanteLegaleManager;
	@Autowired
	private SedeManager sedeManager;
	@Autowired
	private SoggettoManager soggettoManager;
	@Autowired
	private TimerManager timerManager;
	@Autowired
	private TipoAllegatiManager tipoAllegatiManager;
	@Autowired
	private ReportManager reportManager;
	@Autowired
	private ContoEconomicoDAOImpl contoEconomicoDAO;
	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	private static final String PREFIX_REPORT_COMUNICAZIONE_FP = "ComunicazioneDiFineProgetto_";

	@Autowired
	it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione;

	public it.csi.pbandi.pbservizit.business.manager.DocumentoManager getDocumentoManagerNuovaVersione() {
		return documentoManagerNuovaVersione;
	}

	public void setDocumentoManagerNuovaVersione(
			it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione) {
		this.documentoManagerNuovaVersione = documentoManagerNuovaVersione;
	}

	public EsitoOperazioneInviaDichiarazione inviaDichiarazioneDiSpesaCultura(Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, String codUtente,
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO istanzaAttivita,
			RappresentanteLegaleDTO rappresentanteLegale, Long idDelegato, RelazioneTecnicaDTO relazioneTecnica,
			ComunicazioneFineProgettoDTO comunicazioneFineProgettoDTO, DeclaratoriaDTO declaratoriaDTO)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		logger.shallowDump(dichiarazioneDiSpesaDTO, "info");

		if (isEmpty(dichiarazioneDiSpesaDTO.getTipoDichiarazione()))
			throw new FormalParameterException("TIPO DICHIARAZIONE");

		if (Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE.equalsIgnoreCase(dichiarazioneDiSpesaDTO.getTipoDichiarazione())) {
			return inviaDichiarazioneFinaleCultura(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO, codUtente, istanzaAttivita, relazioneTecnica, comunicazioneFineProgettoDTO, declaratoriaDTO);
		}
		else {
			throw new DichiarazioneDiSpesaException("Attenzione! Tipo dichiarazione "
					+ dichiarazioneDiSpesaDTO.getTipoDichiarazione() + " non supportato!");
		}
	}

	// Unisce le modifiche al db dell'anteprima della DS e dell'anteprima della CFP.
	// Anteprima CFP: ex FineProgettoBusinessImpl.creaReportFineProgettoDinamico()
	// Il pdf restituito è quello della CFP.
	private EsitoOperazioneInviaDichiarazione inviaDichiarazioneFinaleCultura(Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, String codUtente,
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO istanzaAttivita,
			RelazioneTecnicaDTO relazioneTecnica, ComunicazioneFineProgettoDTO comunicazioneFineProgettoDTO, DeclaratoriaDTO declaratoriaDTO)
			throws DichiarazioneDiSpesaException {

		logger.begin();
		EsitoOperazioneInviaDichiarazione result = new EsitoOperazioneInviaDichiarazione();
		try {

			// ***************************************************************
			// DICHIARAZIONE DI SPESA
			// ***************************************************************

			long start = getTimerManager().start();

			EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa = verificaDichiarazioneDiSpesa(
					idUtente, identitaDigitale, dichiarazioneDiSpesaDTO);

			DocumentoDiSpesaDTO[] documentiDiSpesa = verificaDichiarazioneDiSpesa.getDocumentiDiSpesa();

			List<Long> idDocumenti = extractId(documentiDiSpesa);

			logger.debug("	pbandiDichiarazioneDiSpesaDAOImpl="+pbandiDichiarazioneDiSpesaDAOImpl);
			// Long idDichiarazioneDiSpesa =
			// pbandiDichiarazioneDiSpesaDAO.getSeqPbandiTDichSpesa().nextLongValue();
			Long idDichiarazioneDiSpesa = pbandiDichiarazioneDiSpesaDAOImpl.creaNuovoIdDichiarazioneDiSpesa();
			
			// 1) Salvo la dichiarazione di spesa

			PbandiTDomandaVO pbandiTDomandaVO = progettoManager
					.getDomandaByProgetto(dichiarazioneDiSpesaDTO.getIdProgetto());

			Date currentDate = new Date(System.currentTimeMillis());

			PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = new PbandiTDichiarazioneSpesaVO();

			dichiarazioneDiSpesaVO.setIdProgetto(new BigDecimal(dichiarazioneDiSpesaDTO.getIdProgetto()));

			dichiarazioneDiSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa));

			dichiarazioneDiSpesaVO.setDtDichiarazione(new java.sql.Date(currentDate.getTime()));

			dichiarazioneDiSpesaVO.setIdTipoDichiarazSpesa(
					new BigDecimal(getIdTipoDichiarazione(dichiarazioneDiSpesaDTO.getTipoDichiarazione())));
			dichiarazioneDiSpesaVO
					.setPeriodoAl(new java.sql.Date(dichiarazioneDiSpesaDTO.getDataFineRendicontazione().getTime()));
			dichiarazioneDiSpesaVO.setPeriodoDal(pbandiTDomandaVO.getDtPresentazioneDomanda());
			dichiarazioneDiSpesaVO.setIdUtenteIns(new BigDecimal(idUtente));

			// Se almeno 1 dei doc di questa ds e' cartaceo, TipoInvioDs vale "C" (null
			// altrimenti).
			logger.info("TipoInvioDs prima di inserire = " + dichiarazioneDiSpesaDTO.getTipoInvioDs());
			dichiarazioneDiSpesaVO.setTipoInvioDs(dichiarazioneDiSpesaDTO.getTipoInvioDs());

			genericDAO.insert(dichiarazioneDiSpesaVO);

			// 2) aggiorna stato documenti di spesa a dichiarato.

			if (!isEmpty(idDocumenti)) {
				aggiornaStatoDocumentiDiSpesa(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO,
						idDichiarazioneDiSpesa, idDocumenti);
			}

			// 3) Ripartisco i pagamenti.

			if (isEmpty(idDocumenti)) {
				idDocumenti = new ArrayList<Long>();
			} else {
				ripartisciPagamenti(idUtente, dichiarazioneDiSpesaDTO, idDocumenti, idDichiarazioneDiSpesa);
			}

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

			// Cambio lo stato del conto economico master solo se il suo stato e' IPG
			ContoEconomicoDTO ce = contoEconomicoManager
					.findContoEconomicoMaster(NumberUtil.toBigDecimal(dichiarazioneDiSpesaDTO.getIdProgetto()));

			if (STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA
					.equalsIgnoreCase(ce.getDescBreveStatoContoEconom())) {
				contoEconomicoManager.aggiornaStatoContoEconomico(NumberUtil.toLong(ce.getIdContoEconomico()),
						STATO_CONTO_ECONOMICO_APPROVATO, NumberUtil.toBigDecimal(idUtente));
			}

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

			// Salva la relazione tecnica.

			if (relazioneTecnica != null && (!StringUtil.isEmpty(relazioneTecnica.getNomeFile()))) {
				allegaRelazioneTecnica(idUtente, dichiarazioneDiSpesaDTO, relazioneTecnica, idDichiarazioneDiSpesa,
						currentDate);
			}

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

			// ***************************************************************
			// ASSEGNA I TIPO ALLEGATI ALLA DS FINALE CREATA PRIMA
			// ***************************************************************

			// Sposta i Tipo Allegati dalla tabella di supporto alla tabella principale
			tipoAllegatiManager.spostaTipoAllegatiCultura(dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					dichiarazioneDiSpesaDTO.getIdProgetto(), idDichiarazioneDiSpesa, "CFP");

			// ***************************************************************
			// COMUNICAZIONE DI FINE PROGETTO
			// ***************************************************************

			// +Green: gestisce il caso +green.
			// NOTA: il progetto a contributo va gestito PRIMA di quello a finanziamento,
			// poichè l'UPDATE su PBANDI_T_DICHIARAZIONE_SPESA provocherebbe un errore
			// in salvaComunicazioneFineProgettoPiuGreen().
//			Long idProgettoContributo = comunicazioneFineProgettoDTO.getIdProgettoContributoPiuGreen();
//			EsitoOperazioneSalvaComunicazioneFineProgetto esitoPiuGreen = null;
//			if (idProgettoContributo != null) {
//
//				logger.info("idProgettoContributo = " + idProgettoContributo
//						+ " : chiamo salvaComunicazioneFineProgettoPiuGreen().");
//				esitoPiuGreen = salvaComunicazioneFineProgettoPiuGreen(idUtente, identitaDigitale,
//						comunicazioneFineProgettoDTO);
//
//				DichiarazioneDiSpesaDTO dsDTOpiuGreen = new DichiarazioneDiSpesaDTO();
//				dsDTOpiuGreen.setNomeFile(esitoPiuGreen.getFileName());
//				dsDTOpiuGreen.setIdDocIndex(esitoPiuGreen.getIdDocIndex());
//				dsDTOpiuGreen.setIdDichiarazioneSpesa(esitoPiuGreen.getIdComunicazione());
//				result.setEsito(esitoPiuGreen.getEsito());
//				result.setDichiarazionePiuGreenDTO(dsDTOpiuGreen);
//			}

			logger.info("\n\ns\nsalvaComunicazioneFineProgetto for " + comunicazioneFineProgettoDTO.getIdProgetto());

			Map<String, String> trsMap = new HashMap<String, String>();
			trsMap.put("idProgetto", "idProgetto");
			trsMap.put("importoRichiestaSaldo", "importoRichErogazioneSaldo");
			trsMap.put("note", "noteComunicazFineProgetto");

			PbandiTComunicazFineProgVO comunicazione = beanUtil.transform(comunicazioneFineProgettoDTO,
					PbandiTComunicazFineProgVO.class, trsMap);
			comunicazione.setDtComunicazione(DateUtil.getSysdate());
			comunicazione.setIdUtenteIns(new BigDecimal(idUtente));
			comunicazione = genericDAO.insert(comunicazione);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ComunicazioneFineProgettoDTO dto = beanUtil.transform(
					comunicazione,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ComunicazioneFineProgettoDTO.class);

			dto.setIdBeneficiario(new BigDecimal(comunicazioneFineProgettoDTO.getIdSoggettoBeneficiario()));
			dto.setCfBeneficiario(comunicazioneFineProgettoDTO.getCfBeneficiario());
			dto.setCodiceProgetto(comunicazioneFineProgettoDTO.getCodiceProgetto());

			boolean isBozza = false; // sempre true per anteprima
			Long idComunicazione = comunicazione.getIdComunicazFineProg().longValue();// sempre 0 per anteprima
			logger.info("idComunicazione = " + idComunicazione);

			EsitoOperazioneAnteprimaComunicazioneFineProgetto esitoOperazioneAnteprimaComunicazioneFineProgetto = creaReportFineProgettoDinamicoCultura(
					idUtente, identitaDigitale, comunicazioneFineProgettoDTO, isBozza, idComunicazione, declaratoriaDTO);

			dto.setBytesModuloPdf(esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes());
			String time = DateUtil.getTime(new Date(System.currentTimeMillis()), Constants.TIME_FORMAT_PER_NOME_FILE);
			dto.setNomeFile("comunicazioneFineProgetto_" + idComunicazione.toString() + "_" + time + ".pdf");

			DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = dichiarazioneDiSpesaManager
					.getDichiarazioneFinale(comunicazioneFineProgettoDTO.getIdProgetto());
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazSpesaVO.setIdDichiarazioneSpesa((dichiarazioneFinale.getIdDichiarazioneSpesa()));
			pbandiTDichiarazSpesaVO.setIdTipoDichiarazSpesa(
					decodificheManager.decodeDescBreveStorico(PbandiDTipoDichiarazSpesaVO.class,
							Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE));

			pbandiTDichiarazSpesaVO.setIdUtenteAgg(new BigDecimal(idUtente));

			genericDAO.update(pbandiTDichiarazSpesaVO);

			String shaHex = null;
			if (esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes() != null)
				shaHex = DigestUtils.shaHex(esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes());

//			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentoManager.creaDocumento(idUtente, dto, null,
//					shaHex, comunicazioneFineProgettoDTO.getIdRappresentanteLegale(),
//					comunicazioneFineProgettoDTO.getIdDelegato());
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentiFSManager.creaDocumento(idUtente, dto, null,
					shaHex, comunicazioneFineProgettoDTO.getIdRappresentanteLegale(),
					comunicazioneFineProgettoDTO.getIdDelegato());

			logger.info("pbandiTDocumentoIndexVO="+pbandiTDocumentoIndexVO.toString());
			// **********************************************************************************
			// AGGIUNTA PER SALVATAGGIO SU FILESYSTEM.
			// TODO Verificare se è ancora necessario
			pbandiTDocumentoIndexVO
					.setRepository(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_FINE_PROGETTO);
			this.salvaFileSuFileSystemSenzaInsert(esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes(),
					pbandiTDocumentoIndexVO);

			// **********************************************************************************

			ProgettoBandoLineaVO progettoBandoLinea = progettoManager
					.getProgettoBandoLinea(comunicazioneFineProgettoDTO.getIdProgetto());
			Long progBandoLinea = progettoBandoLinea.getIdBandoLinea();
			boolean isBrDemat = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(), RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE)
					|| regolaManager.isRegolaApplicabileForBandoLinea(dichiarazioneDiSpesaDTO.getIdBandoLinea(),
							RegoleConstants.BR51_UPLOAD_ALLEGATI_SPESA)
					|| regolaManager.isRegolaApplicabileForBandoLinea(dichiarazioneDiSpesaDTO.getIdBandoLinea(),
							RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA)
					|| regolaManager.isRegolaApplicabileForBandoLinea(dichiarazioneDiSpesaDTO.getIdBandoLinea(),
							RegoleConstants.BR53_UPLOAD_ALLEGATI_GENERICI);
			if (isBrDemat)
				associateAllegati(idComunicazione, comunicazioneFineProgettoDTO.getIdProgetto());

			pbandiTDocumentoIndexVO.getNomeFile();

			DichiarazioneDiSpesaDTO dsDTO = new DichiarazioneDiSpesaDTO();
			dsDTO.setNomeFile(pbandiTDocumentoIndexVO.getNomeFile());
			dsDTO.setIdDocIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			dsDTO.setIdDichiarazioneSpesa(idComunicazione);
			result.setEsito(true);
			result.setDichiarazioneDTO(dsDTO);

			// Pezza +Green per settare il campo
			// PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA
			// del record contributo con lo stesso valore del record finanziamento.
			if (result.getDichiarazionePiuGreenDTO() != null) {
				setFlagFirmaCartaceaDocIndexContributo(result.getDichiarazionePiuGreenDTO().getIdDocIndex(),
						pbandiTDocumentoIndexVO.getFlagFirmaCartacea());
			}

			// *********** SOLO PER FORZARE ROLLBACK DURANTE I TEST ************
			// if (1 < 2)
			// throw new DaoException("DAOEXCEPTION FINTA!!!!!!!!!!!!!");
			// *****************************************************************

			/*
			 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ATTENZIONE: indicatori, cronoprogramma e
			 * CFP sono chiusi tutti insieme, mentre ds finale resta aperta per essere
			 * visibile in sola visualizzazione vengono chiusi tramite una function pl\sql
			 * fatta ad hoc FUNCTION FNC_DSFINALE_CFP (p_id_progetto IN
			 * PBANDI_T_PROGETTO.ID_PROGETTO%TYPE, p_id_utente IN
			 * PBANDI_T_UTENTE.ID_UTENTE%TYPE ) RETURN INTEGER definita nel package
			 * "pck_pbandi_utility_online".
			 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			 */

			Long idProgetto = comunicazioneFineProgettoDTO.getIdProgetto();
			logger.info(
					"\n\n############## NEOFLUX CHIUSURA INDICATORI, CRONOPROGRAMMA, DS FINALE E CFP ##############\n");
			neofluxBusiness.endAttivitaDsFinale(idUtente, identitaDigitale, idProgetto);
			logger.info(
					"############## NEOFLUX CHIUSURA INDICATORI, CRONOPROGRAMMA, DS FINALE E CFP ##############\n\n\n\n");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			// Se il pdf è stato creato, lo cancello.
			logger.info(
					"Si è verificato un errore: come parte del rollback, cancello il pdf normale e +Green della DS dal file system.");
      if (result.getDichiarazionePiuGreenDTO() != null
          && result.getDichiarazionePiuGreenDTO().getIdDocIndex() != null) {
        try {
          logger.info("Cancello pdf +Green");
          BigDecimal bd = new BigDecimal(result.getDichiarazionePiuGreenDTO().getIdDocIndex());
          this.cancellaFileSuFileSystem(bd);
        } catch (Exception ex) {
					logger.error("Errore durante la cancellazione del pdf +Green: " + ex.getMessage(), ex);
        }
      }

      if (result.getDichiarazioneDTO() != null && result.getDichiarazioneDTO().getIdDocIndex() != null) {
        try {
          logger.info("Cancello pdf normale");
          BigDecimal bd = new BigDecimal(result.getDichiarazioneDTO().getIdDocIndex());
          this.cancellaFileSuFileSystem(bd);
        } catch (Exception ex) {
					logger.error("Errore durante la cancellazione del pdf normale: " + ex.getMessage(), ex);
        }
      }

      if (e instanceof DichiarazioneDiSpesaException) {
				throw (DichiarazioneDiSpesaException) e;
			} else {
        throw new RuntimeException("Attivit� non conclusa su flux ");
			}
		}
	}

	private EsitoOperazioneAnteprimaComunicazioneFineProgetto creaReportFineProgettoDinamicoCultura(Long idUtente,
			String identitaDigitale, ComunicazioneFineProgettoDTO comunicazioneFineProgettoDTO, boolean isBozza,
			Long idComunicazione, DeclaratoriaDTO allegatiCultura) throws CSIException {
		logger.begin();
		
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IVA_CULTURA, allegatiCultura.getIva());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RITENUTA_ACCONTO_CULTURA, allegatiCultura.getRitenutaIres());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ORGANI_CULTURA, allegatiCultura.getCollegiali());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_DOCUMENTO_UNICO_CULTURA, allegatiCultura.getDocumentoUnico());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RICHIESTA_CONTRIBUTI_CULTURA, allegatiCultura.getContributiStrutture());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_DENOMINAZIONE_SETTORE_CULTURA, allegatiCultura.getStruttureString());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RICHIESTA_CONTRIBUTI_STATALI_CULTURA, allegatiCultura.getContributiStataliComunitarie());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_DENOMINAZIONE_SETTORE_STATALI_CULTURA, allegatiCultura.getStataleComunitariaString());
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_EVENTUALI_CONTRIBUTI_CULTURA, allegatiCultura.getContributiSuccessivi());

		try {
			VociDiEntrataCulturaDTO vociDiEntrata = contoEconomicoDAO.vociDiEntrataCultura(comunicazioneFineProgettoDTO.getIdProgetto(), idUtente, identitaDigitale);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_VOCI_DI_ENTRATA, vociDiEntrata.getVociDiEntrataCultura());
		} catch (Exception e) {
			throw new DichiarazioneDiSpesaException("Voci di entrata cultura non trovate!");
		}
		
		EsitoOperazioneAnteprimaComunicazioneFineProgetto esito = new EsitoOperazioneAnteprimaComunicazioneFineProgetto();

		Long idProgetto = comunicazioneFineProgettoDTO.getIdProgetto();
		Long idSoggettoBeneficiario = comunicazioneFineProgettoDTO.getIdSoggettoBeneficiario();
		Long idRappresentanteLegale = comunicazioneFineProgettoDTO.getIdRappresentanteLegale();

		// Carico i dati del beneficiario
		BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
		filtroBeneficiario.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		filtroBeneficiario.setIdSoggetto(beanUtil.transform(idSoggettoBeneficiario, BigDecimal.class));
		BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);

		// Carico di dati del rappresentante legale.
		// NOTA: stesso tizio ma classe java diversa rispetto al parametro
		// rappresentanteLegale in input.
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = rappresentanteLegaleManager
				.findRappresentanteLegale(idProgetto, idRappresentanteLegale);

		if (comunicazioneFineProgettoDTO.getIdDelegato() != null) {
			logger.info("il delegato non è NULL " + comunicazioneFineProgettoDTO.getIdDelegato()
					+ ", lo metto al posto del rapp legale");
			DelegatoVO delegatoVO = new DelegatoVO();
			delegatoVO.setIdSoggetto(comunicazioneFineProgettoDTO.getIdDelegato());
			delegatoVO.setIdProgetto(idProgetto);
			List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
			if (delegati != null && !delegati.isEmpty()) {
				delegatoVO = delegati.get(0);
			}
			logger.shallowDump(delegatoVO, "info");
			rappresentanteLegaleDTO = beanUtil.transform(delegatoVO,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
			logger.shallowDump(rappresentanteLegaleDTO, "info");
		}

		// Rappresentante legale
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegaleDTO);

		// Carico dati del progetto
		PbandiTProgettoVO progetto = progettoManager.getProgetto(idProgetto);

		// Carico i dati della sede di intervento
		SedeVO sedeIntervento = null;
		SedeVO sedeAmministrativa = null;

		try {
			sedeIntervento = sedeManager.findSedeIntervento(idProgetto, idSoggettoBeneficiario);
			sedeAmministrativa = sedeManager.findSedeAmministrativa(idProgetto, idSoggettoBeneficiario);
			/* JIRA PBANDI-2764 - FASSIL */
			if (sedeAmministrativa != null) {
				sedeIntervento = sedeAmministrativa;
			}
			logger.info(" sede intervento: ");
			logger.shallowDump(sedeIntervento, "info");
			logger.info(" sede amministrativa: ");
			logger.shallowDump(sedeAmministrativa, "info");
		} catch (DaoException e) {
			logger.error("Errore durante la ricerca della sede di intervento: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante la ricerca della sede di intervento");
		} catch (Exception e) {
			logger.error("Errore durante la ricerca della sede di intervento: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante la ricerca della sede di intervento");
		}

		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_AMMINISTRATIVA,
				beanUtil.transform(sedeAmministrativa, SedeDTO.class));

		popolaTemplateManager.setTipoModello(PopolaTemplateManager.MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgetto);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IMPORTO_RICHIESTA_SALDO,
				comunicazioneFineProgettoDTO.getImportoRichiestaSaldo());

		// Beneficiario
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_BENEFICIARIO,
				beanUtil.transform(beneficiarioVO, BeneficiarioDTO.class));

		// Progetto
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGETTO,
				beanUtil.transform(progetto, ProgettoDTO.class));

		// Sede intervento
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,
				beanUtil.transform(sedeIntervento, SedeDTO.class));

		// Note
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NOTE, comunicazioneFineProgettoDTO.getNote());

		DocumentoDiSpesaComunicazioneDTO[] documentiDichFinale = findDocumentiDichiarazioneFinale(idUtente,
				identitaDigitale, comunicazioneFineProgettoDTO.getIdProgetto());
		List<Long> idDocumentiValidi = new ArrayList<Long>();
		if (!isEmpty(documentiDichFinale)) {
			idDocumentiValidi = beanUtil.extractValues(Arrays.asList(documentiDichFinale), "idDocumentoDiSpesa",
					Long.class);
		}

		DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = dichiarazioneDiSpesaManager
				.getDichiarazioneFinale(idProgetto);
		if (dichiarazioneFinale == null || dichiarazioneFinale.getIdDichiarazioneSpesa() == null) {
			logger.error("Errore durante la ricerca della dichiarazione finale.");
			throw new DichiarazioneDiSpesaException("Errore durante la ricerca della dichiarazione finale.");
		}
		logger.info(" dichiarazioneFinale.getIdDichiarazioneSpesa()= " + dichiarazioneFinale.getIdDichiarazioneSpesa());

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO progettoDTO = progettoManager.getProgetto(
				idProgetto, dichiarazioneFinale.getDtDichiarazione(), idDocumentiValidi,
				NumberUtil.toLong(dichiarazioneFinale.getIdDichiarazioneSpesa()));

		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_TOTALE_QUIETANZIATO,
				progettoDTO.getTotaleQuietanzato());

		// Bozza
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IS_BOZZA, isBozza);

		// 11/12/15 added footer
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,
				"" + dichiarazioneFinale.getIdDichiarazioneSpesa());

		// Allegati
		ProgettoBandoLineaVO progettoBandoLinea = progettoManager.getProgettoBandoLinea(idProgetto);
		Long progBandoLinea = progettoBandoLinea.getIdBandoLinea();
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI, tipoAllegatiManager.findTipoAllegati(
				dichiarazioneFinale.getIdDichiarazioneSpesa().longValue(), idProgetto, progBandoLinea, "CFP"));

		logger.info("creaReportFineProgettoDinamico(): CHIAVE_ID_PROGETTO_PIU_GREEN = NULL");
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO_PIU_GREEN, null);

		Modello modello = null;
		try {
			modello = popolaTemplateManager.popolaModello(idProgetto);
		} catch (Exception e) {
			logger.error("Errore durante il popolamento del modello: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante il popolamento del modello");
		}

		long startFillReport = System.currentTimeMillis();
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(), modello.getMasterParameters(),
					new JREmptyDataSource());
		} catch (JRException e) {
			logger.error("Errore durante il popolamento del report Jasper: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante il popolamento del report Jasper");
		}
		logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in "
				+ (System.currentTimeMillis() - startFillReport) + " ms\n");

		long startExport = System.currentTimeMillis();
		byte[] bytes;
		try {
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			logger.error("Errore durante la trasformazione del modello in pdf: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante la trasformazione del modello in pdf");
		}
		logger.info("\n\n\n########################\nJasperPrint esportato to pdf in "
				+ (System.currentTimeMillis() - startExport) + " ms\n");

		esito.setPdfBytes(bytes);

		Date currentDate = new Date(System.currentTimeMillis());
		String time = DateUtil.getTime(currentDate, Constants.TIME_FORMAT_PER_NOME_FILE);
		String nomeFile = PREFIX_REPORT_COMUNICAZIONE_FP + idComunicazione.toString() + "_" + time + ".pdf";
		esito.setNomeFile(nomeFile);
		esito.setEsito(true);

		return esito;
	}

	private EsitoOperazioneSalvaComunicazioneFineProgetto salvaComunicazioneFineProgettoPiuGreen(Long idUtente,
			String identitaDigitale, ComunicazioneFineProgettoDTO comunicazioneFineProgettoDTO)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		EsitoOperazioneSalvaComunicazioneFineProgetto esito = new EsitoOperazioneSalvaComunicazioneFineProgetto();
		esito.setEsito(Boolean.FALSE);

		try {

			Long idProgettoContributo = comunicazioneFineProgettoDTO.getIdProgettoContributoPiuGreen();

			// Se non è più +Green, termina subito.
			if (idProgettoContributo == null)
				return esito;

			logger.info(
					"\n\ns\nsalvaComunicazioneFineProgetto +GREEN : idProgettoContributo = " + idProgettoContributo);

			Map<String, String> trsMap = new HashMap<String, String>();
			trsMap.put("idProgetto", "idProgetto");
			trsMap.put("importoRichiestaSaldo", "importoRichErogazioneSaldo");
			trsMap.put("note", "noteComunicazFineProgetto");

			PbandiTComunicazFineProgVO comunicazione = beanUtil.transform(comunicazioneFineProgettoDTO,
					PbandiTComunicazFineProgVO.class, trsMap);
			comunicazione.setDtComunicazione(DateUtil.getSysdate());
			comunicazione.setIdUtenteIns(new BigDecimal(idUtente));
			// Variante +Green: insert con idProgetto = id progetto contributo.
			comunicazione.setIdProgetto(new BigDecimal(idProgettoContributo));
			comunicazione = genericDAO.insert(comunicazione);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ComunicazioneFineProgettoDTO dto = beanUtil.transform(
					comunicazione, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ComunicazioneFineProgettoDTO.class);

			dto.setIdBeneficiario(new BigDecimal(comunicazioneFineProgettoDTO.getIdSoggettoBeneficiario()));
			dto.setCfBeneficiario(comunicazioneFineProgettoDTO.getCfBeneficiario());
			dto.setCodiceProgetto(comunicazioneFineProgettoDTO.getCodiceProgetto());

			boolean isBozza = false; // sempre true per anteprima
			Long idComunicazione = comunicazione.getIdComunicazFineProg().longValue();// sempre 0 per anteprima

			// Variante +Green: chiamo creaReportFineProgettoDinamicoPiuGreen().
			EsitoOperazioneAnteprimaComunicazioneFineProgetto esitoOperazioneAnteprimaComunicazioneFineProgetto = creaReportFineProgettoDinamicoPiuGreen(
					idUtente, identitaDigitale, comunicazioneFineProgettoDTO, isBozza, idComunicazione);

			dto.setBytesModuloPdf(esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes());
			String time = DateUtil.getTime(new Date(System.currentTimeMillis()), Constants.TIME_FORMAT_PER_NOME_FILE);
			dto.setNomeFile("comunicazioneFineProgetto_" + idComunicazione.toString() + "_" + time + ".pdf");

			// Variante +Green: update su PBANDI_T_DICHIARAZIONE_SPESA non è da fare: codice
			// eliminato.

			String shaHex = null;
			if (esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes() != null)
				shaHex = DigestUtils.shaHex(esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes());

			// Variante +Green: si passa l'id progetto contributo in modo che venga salvato
			// in PBANDI_T_DOCUMENTO_INDEX.
			dto.setIdProgetto(new BigDecimal(idProgettoContributo));
//			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentoManager.creaDocumento(idUtente, dto, null,
//					shaHex, comunicazioneFineProgettoDTO.getIdRappresentanteLegale(),
//					comunicazioneFineProgettoDTO.getIdDelegato());
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentiFSManager.creaDocumento(idUtente, dto, null,
					shaHex, comunicazioneFineProgettoDTO.getIdRappresentanteLegale(),
					comunicazioneFineProgettoDTO.getIdDelegato());
			
			logger.info("pbandiTDocumentoIndexVO="+pbandiTDocumentoIndexVO.toString());

			// **********************************************************************************
			// AGGIUNTA PER SALVATAGGIO SU FILESYSTEM.
			pbandiTDocumentoIndexVO
					.setRepository(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_FINE_PROGETTO);
			if(!this.salvaFileSuFileSystem(esitoOperazioneAnteprimaComunicazioneFineProgetto.getPdfBytes(), pbandiTDocumentoIndexVO)) {
				throw new RuntimeException("Errore durante il salvataggio del file su filesystem.");
			}
			// **********************************************************************************

			// Variante +Green: nessuna gestione allegati: codice eliminato.

			// Variante +Green: il flusso avanza già in salvaComunicazioneFineProgetto():
			// codice eliminato.

			pbandiTDocumentoIndexVO.getNomeFile();
			esito.setFileName(pbandiTDocumentoIndexVO.getNomeFile());
			esito.setIdDocIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			esito.setIdComunicazione(idComunicazione);
			esito.setEsito(Boolean.TRUE);

			logger.info("\n\ns\nFINE salvaComunicazioneFineProgetto +GREEN : IdDocIndex = " + esito.getIdDocIndex()
					+ "; FileName = " + esito.getFileName() + "; IdComunicazione = " + esito.getIdComunicazione());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			RuntimeException re = new RuntimeException("Errore nella creazione del documento");
			throw re;
		}
		return esito;
	}

	private boolean salvaFileSuFileSystem(byte[] file, PbandiTDocumentoIndexVO vo) throws Exception {
		logger.info("salvaFileSuFileSystem(): file.length = " + file.length + "; NomeFile = " + vo.getNomeFile());

		// Trasformo PbandiTDocumentoIndexVO di pbandisrv in PbandiTDocumentoIndexVO di
		// pbweb.
		it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO newVO = beanUtil.transform(vo,
				it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO.class);

    boolean esitoSalva = false;
		esitoSalva = documentoManagerNuovaVersione.salvaFile(file, newVO);

		if (!esitoSalva)
			throw new Exception("File " + vo.getNomeFile() + " non salvato su file system.");

		vo.setIdDocumentoIndex(newVO.getIdDocumentoIndex());
		return esitoSalva;
	}

	private void salvaFileSuFileSystemSenzaInsert(byte[] file, PbandiTDocumentoIndexVO vo) throws Exception {
		logger.info("salvaFileSuFileSystem(): file.length = " + file.length + "; NomeFile = " + vo.getNomeFile());

		// Trasformo PbandiTDocumentoIndexVO di pbandisrv in PbandiTDocumentoIndexVO di
		// pbweb.
		it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO newVO = beanUtil.transform(vo,
				it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO.class);

		boolean esitoSalva = false;
		esitoSalva = documentoManagerNuovaVersione.salvaFileSenzaInsert(file, newVO);

		if (!esitoSalva)
			throw new Exception("File " + vo.getNomeFile() + " non salvato su file system.");

		vo.setIdDocumentoIndex(newVO.getIdDocumentoIndex());
	}

	private void cancellaFileSuFileSystem(BigDecimal idDocumentoIndex) throws Exception {
		logger.info("cancellaFileSuFileSystem(): idDocumentoIndex = " + idDocumentoIndex);

		if (idDocumentoIndex == null) {
			logger.info("cancellaFileSuFileSystem(): idDocumentoIndex non valorizzato: non faccio nulla.");
			return;
		}

		boolean esitoCancella = true;
		esitoCancella = documentoManagerNuovaVersione.cancellaFile(idDocumentoIndex);

		if (!esitoCancella)
			throw new Exception("File con idDocumentoIndex " + idDocumentoIndex + " non cancellato dal file system.");

	}

	// Ex FineProgettoBusinessImpl.findDocumentiDichiarazioneFinale()
	private DocumentoDiSpesaComunicazioneDTO[] findDocumentiDichiarazioneFinale(Long idUtente, String identitaDigitale,
			Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);
		List<DocumentoDiSpesaDichiarazioneTotalePagamentiVO> documenti = null;

		PbandiTDichiarazioneSpesaVO dichiarazioneFinale = dichiarazioneDiSpesaManager
				.getDichiarazioneFinale(idProgetto);
		if (isNull(dichiarazioneFinale)) {
			logger.warn("Nessuna dichiarazione di tipo FINALE trovata per il progetto " + idProgetto);
		} else {
			documenti = documentoManager.findDocumentiDichiarazione(dichiarazioneFinale.getIdDichiarazioneSpesa());
		}

		return rimappa(documenti);
	}

	// Ex FineProgettoBusinessImpl.rimappa()
	private DocumentoDiSpesaComunicazioneDTO[] rimappa(List<DocumentoDiSpesaDichiarazioneTotalePagamentiVO> documenti) {
		DocumentoDiSpesaComunicazioneDTO[] documentiDichFinale = null;
		if (!isEmpty(documenti)) {
			Map<String, String> trsMap = new HashMap<String, String>();
			trsMap.put("dtEmissioneDocumento", "dataEmissioneDocumentoDiSpesa");
			trsMap.put("codiceFiscaleFornitore", "fornitore");
			trsMap.put("idDocumentoDiSpesa", "idDocumentoDiSpesa");
			trsMap.put("importoTotaleDocumento", "importoDocumento");
			trsMap.put("importoRendicontazione", "importoRendicontabile");
			trsMap.put("numeroDocumento", "numeroDocumentoDiSpesa");
			trsMap.put("task", "task");
			trsMap.put("descTipoDocumentoSpesa", "tipoDocumentoDiSpesa");
			trsMap.put("totalePagamenti", "totalePagamentiDichiarazione");
			documentiDichFinale = beanUtil.transform(documenti, DocumentoDiSpesaComunicazioneDTO.class, trsMap);
		}
		return documentiDichFinale;
	}

	// Ex FineProgettoBusinessImpl.creaReportFineProgettoDinamicoPiuGreen()
	// Usato sia per l'anteprima sia per la generazione della CFP +Green.
	// Dati del report CFP:
	// - beneficiario : idProgettoFinanziamento (è lo stesso per finanaziamento e
	// contributo)
	// - rappresentante legale \ delegato : idProgettoFinanziamento (è lo stesso per
	// finanaziamento e contributo)
	// - progetto : idProgettoContributo
	// - sede di intervento : idProgettoFinanziamento
	// - totale quietanziato : idProgettoFinanziamento
	private EsitoOperazioneAnteprimaComunicazioneFineProgetto creaReportFineProgettoDinamicoPiuGreen(Long idUtente,
			String identitaDigitale, ComunicazioneFineProgettoDTO comunicazioneFineProgettoDTO, boolean isBozza,
			Long idComunicazione)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		logger.info("[DichiarazioneDiSpesaBusinessImpl::creaReportFineProgettoDinamicoPiuGreen() ] INIZIO");

		EsitoOperazioneAnteprimaComunicazioneFineProgetto esito = new EsitoOperazioneAnteprimaComunicazioneFineProgetto();

		Long idProgettoFinanziamento = comunicazioneFineProgettoDTO.getIdProgetto();
		Long idProgettoContributo = comunicazioneFineProgettoDTO.getIdProgettoContributoPiuGreen();
		Long idSoggettoBeneficiario = comunicazioneFineProgettoDTO.getIdSoggettoBeneficiario();
		Long idRappresentanteLegale = comunicazioneFineProgettoDTO.getIdRappresentanteLegale();
		logger.info(
				"[DichiarazioneDiSpesaBusinessImpl::creaReportFineProgettoDinamicoPiuGreen() ] idProgettoFinanziamento = "
						+ idProgettoFinanziamento + "; idProgettoContributo = " + idProgettoContributo + "\n\n");

		// BENEFICIARIO
		BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
		filtroBeneficiario.setIdProgetto(beanUtil.transform(idProgettoFinanziamento, BigDecimal.class));
		filtroBeneficiario.setIdSoggetto(beanUtil.transform(idSoggettoBeneficiario, BigDecimal.class));
		BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_BENEFICIARIO,
				beanUtil.transform(beneficiarioVO, BeneficiarioDTO.class));
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_BENEFICIARIO, idSoggettoBeneficiario);

		// RAPPRESENTANTE LEGALE
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = rappresentanteLegaleManager
				.findRappresentanteLegale(idProgettoFinanziamento, idRappresentanteLegale);
		if (comunicazioneFineProgettoDTO.getIdDelegato() != null) {
			logger.info("il delegato non è NULL " + comunicazioneFineProgettoDTO.getIdDelegato()
					+ ", lo metto al posto del rapp legale");
			DelegatoVO delegatoVO = new DelegatoVO();
			delegatoVO.setIdSoggetto(comunicazioneFineProgettoDTO.getIdDelegato());
			delegatoVO.setIdProgetto(idProgettoContributo);
			List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
			if (delegati != null && !delegati.isEmpty()) {
				delegatoVO = delegati.get(0);
			}
			logger.shallowDump(delegatoVO, "info");
			rappresentanteLegaleDTO = beanUtil.transform(delegatoVO,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
			logger.shallowDump(rappresentanteLegaleDTO, "info");
		}
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegaleDTO);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_RAPPRESENTANTE_LEGALE, idRappresentanteLegale);

		// PROGETTO
		PbandiTProgettoVO progetto = progettoManager.getProgetto(idProgettoContributo);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGETTO,
				beanUtil.transform(progetto, ProgettoDTO.class));
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgettoContributo);

		// SEDE DI INTERVENTO
		SedeVO sedeIntervento = null;
		SedeVO sedeAmministrativa = null;
		try {
			sedeIntervento = sedeManager.findSedeIntervento(idProgettoFinanziamento, idSoggettoBeneficiario);
			sedeAmministrativa = sedeManager.findSedeAmministrativa(idProgettoFinanziamento, idSoggettoBeneficiario);
		} catch (Exception e) {
			logger.error("Errore durante la ricerca della sede di intervento: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante la ricerca della sede di intervento");
		}
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,
				beanUtil.transform(sedeIntervento, SedeDTO.class));
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_AMMINISTRATIVA,
				beanUtil.transform(sedeAmministrativa, SedeDTO.class));

		// TOTALE QUIETANZIATO
		DocumentoDiSpesaComunicazioneDTO[] documentiDichFinale = findDocumentiDichiarazioneFinale(idUtente,
				identitaDigitale, idProgettoFinanziamento);

		List<Long> idDocumentiValidi = new ArrayList<Long>();
		if (!isEmpty(documentiDichFinale)) {
			idDocumentiValidi = beanUtil.extractValues(Arrays.asList(documentiDichFinale), "idDocumentoDiSpesa",
					Long.class);
		}
		DichiarazioneDiSpesaConTipoVO dichiarazioneFinale = dichiarazioneDiSpesaManager
				.getDichiarazioneFinale(idProgettoFinanziamento);
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO progettoDTO = progettoManager.getProgetto(
				idProgettoFinanziamento, dichiarazioneFinale.getDtDichiarazione(), idDocumentiValidi,
				NumberUtil.toLong(dichiarazioneFinale.getIdDichiarazioneSpesa()));
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_TOTALE_QUIETANZIATO,
				progettoDTO.getTotaleQuietanzato());

		// ALLEGATI
		ProgettoBandoLineaVO progettoBandoLinea = progettoManager.getProgettoBandoLinea(idProgettoContributo);
//			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO progettoBandoLinea = progettoManager
//					.getProgettoBandoLinea(idProgettoFinanziamento); //TODO PK fix JIRA PBANDI-3556

		Long progBandoLinea = progettoBandoLinea.getIdBandoLinea();
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI,
				tipoAllegatiManager.findTipoAllegati(dichiarazioneFinale.getIdDichiarazioneSpesa().longValue(),
						idProgettoContributo, progBandoLinea, "CFP"));
//			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI,
//					tipoAllegatiManager.findTipoAllegati(dichiarazioneFinale.getIdDichiarazioneSpesa().longValue(),
//							idProgettoFinanziamento, progBandoLinea, "CFP")); //TODO PK fix JIRA PBANDI-3556
		logger.info(" allegati caricati");

		// CHIAVI MISTE E COLORATE
		logger.info(" CHIAVE_ID_PROGETTO_PIU_GREEN = " + idProgettoFinanziamento);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO_PIU_GREEN, idProgettoFinanziamento); // PK
																														// sicuro
																														// di
																														// passare
																														// idPorogetto
																														// originale?????

		popolaTemplateManager.setTipoModello(PopolaTemplateManager.MODELLO_COMUNICAZIONE_DI_FINE_PROGETTO);

		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IMPORTO_RICHIESTA_SALDO,
				comunicazioneFineProgettoDTO.getImportoRichiestaSaldo());

		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NOTE, comunicazioneFineProgettoDTO.getNote());

		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IS_BOZZA, isBozza);

		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,
				"" + dichiarazioneFinale.getIdDichiarazioneSpesa());

		Modello modello;
		try {
			modello = popolaTemplateManager.popolaModelloPiuGreen(idProgettoFinanziamento, idProgettoContributo);
		} catch (Exception e) {
			logger.error("Errore durante il popolamento del modello: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante il popolamento del modello");
		}

		long startFillReport = System.currentTimeMillis();
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(), modello.getMasterParameters(),
					new JREmptyDataSource());
		} catch (JRException e) {
			logger.error("Errore durante il popolamento del report Jasper: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante il popolamento del report Jasper");
		}
		logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in "
				+ (System.currentTimeMillis() - startFillReport) + " ms\n");

		long startExport = System.currentTimeMillis();
		byte[] bytes;
		try {
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			logger.error("Errore durante la trasformazione del modello in pdf: " + e);
			throw new DichiarazioneDiSpesaException("Errore durante la trasformazione del modello in pdf");
		}
		logger.info("\n\n\n########################\nJasperPrint esportato to pdf in "
				+ (System.currentTimeMillis() - startExport) + " ms\n");

		esito.setPdfBytes(bytes);

		Date currentDate = new Date(System.currentTimeMillis());
		String time = DateUtil.getTime(currentDate, Constants.TIME_FORMAT_PER_NOME_FILE);
		String nomeFile = PREFIX_REPORT_COMUNICAZIONE_FP + idComunicazione.toString() + "_" + time + ".pdf";
		esito.setNomeFile(nomeFile);
		esito.setEsito(true);

		logger.info("[DichiarazioneDiSpesaBusinessImpl::creaReportFineProgettoDinamicoPiuGreen() ] FINE");

		return esito;
	}

	// Assegna il flag firma cartacea del doc index finanziamento a quello
	// contributo; usato per +Green.
	private void setFlagFirmaCartaceaDocIndexContributo(Long idDocIndexContributo, String flag) {
		logger.info("setFlagFirmaCartaceaDocIndexContributo(): idDocIndexContributo = " + idDocIndexContributo
				+ "; flag = " + flag);
		try {
			if (idDocIndexContributo != null) {
				PbandiTDocumentoIndexVO docContr = new PbandiTDocumentoIndexVO();
				docContr.setIdDocumentoIndex(new BigDecimal(idDocIndexContributo));
				docContr.setFlagFirmaCartacea(flag);
				genericDAO.update(docContr);
			}
		} catch (Exception e) {
			logger.error("ERRORE in setFlagFirmaCartaceaDocIndexContributo(): \n" + e);
			return;
		}
	}

	private void allegaRelazioneTecnica(Long idUtente, DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			RelazioneTecnicaDTO relazioneTecnica, Long idDichiarazioneSpesa, Date currentDate)
			throws DocumentNotCreatedException {
		AllegatoRelazioneTecnicaDTO allegatoRelazioneTecnica = beanUtil.transform(relazioneTecnica,
				AllegatoRelazioneTecnicaDTO.class);
		allegatoRelazioneTecnica.setBytesDocumento(relazioneTecnica.getByteAllegato());
		allegatoRelazioneTecnica
				.setIdProgetto(beanUtil.transform(dichiarazioneDiSpesaDTO.getIdProgetto(), BigDecimal.class));
		BeneficiarioProgettoVO beneficiarioProgettoVO = new BeneficiarioProgettoVO();
		beneficiarioProgettoVO
				.setIdProgetto(beanUtil.transform(dichiarazioneDiSpesaDTO.getIdProgetto(), BigDecimal.class));
		beneficiarioProgettoVO
				.setIdSoggetto(beanUtil.transform(dichiarazioneDiSpesaDTO.getIdSoggetto(), BigDecimal.class));
		beneficiarioProgettoVO = genericDAO.findSingleWhere(beneficiarioProgettoVO);
		allegatoRelazioneTecnica.setCodiceProgetto(beneficiarioProgettoVO.getCodiceVisualizzatoProgetto());
		allegatoRelazioneTecnica.setCfBeneficiario(beneficiarioProgettoVO.getCodiceFiscaleSoggetto());
		allegatoRelazioneTecnica.setIdBeneficiario(beneficiarioProgettoVO.getIdSoggetto());
		allegatoRelazioneTecnica.setDataDichiarazione(currentDate);
		allegatoRelazioneTecnica.setIdDichiarazioneSpesa(beanUtil.transform(idDichiarazioneSpesa, BigDecimal.class));
		String shaHex = null;
		if (relazioneTecnica.getByteAllegato() != null)
			shaHex = DigestUtils.shaHex(relazioneTecnica.getByteAllegato());
//		documentoManager.creaDocumento(idUtente, allegatoRelazioneTecnica, null, shaHex, null, null);
		documentiFSManager.creaDocumento(idUtente, allegatoRelazioneTecnica, null, shaHex, null, null);
	}

	/**
	 * *************************************************************************
	 * ***************************************************************** DAL BRANCH
	 */

	/**
	 * Verifica che il totale dei pagamenti � superiore al totale della fattura, al
	 * netto delle note di credito.
	 * 
	 * @param documentoDiSpesaVO
	 * @param totaleNoteCredito
	 * @param totalePagamenti
	 * @return
	 */
	private boolean isImportoQuietanzatoSuperioreImportoDocumento(DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
		double totaleDocumento = documentoDiSpesaVO.getImportoTotaleDocumento() == null ? 0
				: documentoDiSpesaVO.getImportoTotaleDocumento().doubleValue();
		/*
		 * FIX: PBandi-550 Correzione arrotondamento
		 */
		if (NumberUtil.toRoundDouble(totaleDocumento - NumberUtil
				.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO.getTotaleNoteCredito()))) < NumberUtil
						.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO.getTotaleImportoPagamenti())))
			return true;
		else
			return false;

	}

	// private boolean scarta(
	// List<it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO>
	// scarti,
	// DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
	// boolean scartato;
	// it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO
	// scartoDTO = new
	// it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO();
	// beanUtil.valueCopy(documentoDiSpesaVO, scartoDTO);
	// scarti.add(scartoDTO);
	// scartato = true;
	// return scartato;
	// }

	private void ripartisciPagamenti(Long idUtente, DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			List<Long> idDocumenti, Long idDichiarazione) throws FormalParameterException {
		/*
		 * 1) Se il bando non segue la regola BR02 allora applico l' algoritmo che
		 * ripartisce l' importo del pagamento tra le voci di spesa associate per ogni
		 * documento di spesa valido
		 */
		Boolean isBr02 = regolaManager.isRegolaApplicabileForBandoLinea(dichiarazioneDiSpesaDTO.getIdBandoLinea(),
				RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);
		Long idProgetto = dichiarazioneDiSpesaDTO.getIdProgetto();

		if (!isBr02) {
			StringBuilder idDocumentiConcatenati = new StringBuilder();
			for (Long idDocumento : idDocumenti) {
				idDocumentiConcatenati.append(idDocumento.toString());
				idDocumentiConcatenati.append(",");
			}
			logger.warn("new BigDecimal(idProgetto) : " + idProgetto + "\nidDocumentiConcatenati.toString() : "
					+ idDocumentiConcatenati.toString() + "\nidDichiarazione:" + idDichiarazione + "\nidUtente:"
					+ idUtente);

			if (!isEmpty(idDocumentiConcatenati.toString())) {
				genericDAO.callProcedure().ripartizionePagamenti(new BigDecimal(idProgetto),
						idDocumentiConcatenati.toString(), idDichiarazione, new BigDecimal(idUtente));
			}
		}

		/*
		 * Recupero le note di credito associate ai documenti validi e li aggiungo a
		 * questi ultimi
		 */
		// if(!isEmpty(idDocumenti)){
		// List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>
		// noteDiCredito = findNoteDiCredito(
		// dichiarazioneDiSpesaDTO.getIdProgetto(), documentiValidi);
		//
		// documentiValidi.addAll(noteDiCredito);
		// }
	}

	public RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String identitaDigitale, Long idProgetto,
			Long idSoggettoRappresentante)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentanti = soggettoManager
				.findRappresentantiLegali(idProgetto, idSoggettoRappresentante);
		return beanUtil.transform(rappresentanti, RappresentanteLegaleDTO.class);

	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setDocumentoDiSpesaManager(DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}

	public String findNoteChiusuraValidazione(Long idUtente, String identitaDigitale, Long idDichiarazione)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazione" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDichiarazione);

		PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
		pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazione));
		pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);
		return pbandiTDichiarazioneSpesaVO.getNoteChiusuraValidazione();

	}

	public boolean hasDichiarazioneFinale(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		boolean ret = false;
		try {

			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);

			ret = dichiarazioneDiSpesaManager.hasDichiarazioneFinale(idUtente, identitaDigitale, idProgetto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		return ret;
	}

	public boolean hasDichiarazioneFinaleOFinaleComunicazione(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		boolean ret = false;
		try {

			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);

			ret = dichiarazioneDiSpesaManager.hasDichiarazioneFinaleOFinaleComunicazione(idUtente, identitaDigitale,
					idProgetto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		return ret;
	}

	private List<Long> extractId(DocumentoDiSpesaDTO[] documentiDiSpesa) {
		List<Long> listIdDoc = new ArrayList<Long>();
		if (isEmpty(documentiDiSpesa)) {
			return listIdDoc;
		}
		List<DocumentoDiSpesaDTO> listDocsValidi = new ArrayList<DocumentoDiSpesaDTO>();
		for (DocumentoDiSpesaDTO documentoDiSpesaDTO : documentiDiSpesa) {
			if (isEmpty(documentoDiSpesaDTO.getMotivazione())) {
				listDocsValidi.add(documentoDiSpesaDTO);
			}
		}
		listIdDoc = beanUtil.extractValues(listDocsValidi, "idDocumentoDiSpesa", Long.class);

		return listIdDoc;
	}

	// OTTOBRE 2015 DEMAT II
	private void associateAllegati(Long idDichiarazioneDiSpesa, Long idProgetto) {

		// where idPRogetto is null && id_entita=t_dicha and id_target= id_progetto
		logger.info("associating allegati to idDichiarazione " + idDichiarazioneDiSpesa + " ,idProgetto ");
		pbandiArchivioFileDAOImpl.associateAllegatiToDichiarazione(idDichiarazioneDiSpesa, idProgetto);

	}

	private Long getIdTipoDichiarazione(String descBreve) {

		return beanUtil.transform(decodificheManager.decodeDescBreve(PbandiDTipoDichiarazSpesaVO.class, descBreve),
				Long.class);

	}

	public InfoDichiarazioneSpesaDTO getInfoDichiarazioneDiSpesa(Long idUtente, String identitaDigitale,
			Long idDichiarazioneDiSpesa, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazioneDiSpesa", "idProgetto" };

		InfoDichiarazioneSpesaDTO infoDichiarazioneSpesaDTO = new InfoDichiarazioneSpesaDTO();
		try {

			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDichiarazioneDiSpesa,
					idProgetto);

			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			String descBreveTipoDich = getDecodificheManager().findDescBreveStorico(PbandiDTipoDichiarazSpesaVO.class,
					pbandiTDichiarazioneSpesaVO.getIdTipoDichiarazSpesa());

			if (descBreveTipoDich.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA)
					|| descBreveTipoDich.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE)
					|| descBreveTipoDich
							.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE)) {
				infoDichiarazioneSpesaDTO.setIsFinaleOIntegrativa(true);
				// aggiungo le info sulle altre integrative non ancora validate
				infoDichiarazioneSpesaDTO.setAltreDichiarazioniDiSpesaIntermedieNonValidate(
						dichiarazioneDiSpesaManager.isDichiarazioniDaValidareById(idProgetto));
			} else if (descBreveTipoDich.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA)) {
				infoDichiarazioneSpesaDTO.setIsIntermedia(true);
				infoDichiarazioneSpesaDTO.setAltreDichiarazioniDiSpesaIntermedieNonValidate(false);
			}
			infoDichiarazioneSpesaDTO.setDichiarazioneFinalePresente(dichiarazioneDiSpesaManager
					.hasDichiarazioneFinaleOFinaleComunicazione(idUtente, identitaDigitale, idProgetto));

			infoDichiarazioneSpesaDTO.setPagamentiDaRespingere(false);
			infoDichiarazioneSpesaDTO.setPagamentiRespinti(false);
			infoDichiarazioneSpesaDTO.setIsProgettoChiuso(false);

			List<String> messaggi = new ArrayList<String>();
			infoDichiarazioneSpesaDTO.setMessaggi(messaggi.toArray(new String[] {}));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		return infoDichiarazioneSpesaDTO;
	}

	private boolean isTipoInvioDigitale(String tipoInvio) {
		if (tipoInvio != null && tipoInvio.equalsIgnoreCase(GestioneDocumentiDiSpesaSrv.TIPO_INVIO_DOCUMENTO_DIGITALE))
			return true;
		else
			return false;
	}

	public java.lang.Long findIdDocIndexReport(java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idDichiarazioneDiSpesa)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazioneDiSpesa" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDichiarazioneDiSpesa);

		PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();
		vo.setIdTarget(new BigDecimal(idDichiarazioneDiSpesa));
		BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS);
		vo.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		logger.warn("\n\n\n\nfinding idDocIndex for report dettaglio doc spesa validazione");
		vo = genericDAO.findSingleOrNoneWhere(vo);
		if (vo != null) {
			logger.warn("\nidDocIndex found !!! : " + vo.getIdDocumentoIndex());
			return vo.getIdDocumentoIndex().longValue();
		} else {
			logger.warn("\nidDocIndex NOT found !!!   ");
			return null;
		}
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}

	public void setRappresentanteLegaleManager(RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public DichiarazioneDiSpesaDTO findDichiarazioneFinale(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);

		return beanUtil.transform(dichiarazioneDiSpesaManager.getDichiarazioneFinale(idProgetto),
				DichiarazioneDiSpesaDTO.class);
	}

	private void aggiornaStatoDocumentiDiSpesa(java.lang.Long idUtente, java.lang.String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, Long idDichiarazioneDiSpesa, List<Long> idDocumentiDiSpesa)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		// // a) cerco tutti i doc di spesa
		// java.util.List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>
		// allDocumenti = pbandiDocumentiDiSpesaDAO
		// .findDocumentiDiSpesa(dichiarazioneDiSpesaDTO,
		// statiAmmessiDocumento,statiAmmessiPagamento);
		//
		// // 25 maggio: elisabetta conferma che cambiano stato solo quelli
		// // bonificati
		//
		if (!isEmpty(idDocumentiDiSpesa)) {

			// d) cerco stato dichiarato
			String codiceStatoDichiarato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE;

			DecodificaDTO decodificaStatoDichiarato = decodificheManager
					.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, codiceStatoDichiarato);
			if (isNull(decodificaStatoDichiarato) || isNull(decodificaStatoDichiarato.getId()))
				throw new RuntimeException("Non trovato id per codice " + codiceStatoDichiarato);

			pbandiDocumentiDiSpesaDAO.updateStatoDocumentiDiSpesa(idUtente, idDocumentiDiSpesa,
					dichiarazioneDiSpesaDTO.getIdProgetto(), decodificaStatoDichiarato.getId());

			/*
			 * FIX PBandi-2314. Se il documento e' in stato DA COMPLETARE ed il totale del
			 * documento al netto delle note di credito e' minore o uguale al totale dei
			 * pagamenti allora lo stato per il documento-progetto che ha subito gia' una
			 * validazione deve passare da DA COMPLETARE allo stato della validazione.
			 */
			DecodificaDTO statoDaCompletare = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE);
			DecodificaDTO statoValidato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO);
			DecodificaDTO statoNonValidato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO);
			DecodificaDTO statoParzValidato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO);
			DecodificaDTO statoDichiarabile = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE);
			DecodificaDTO statoRespinto = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO);

			for (Long idDoc : idDocumentiDiSpesa) {
				/*
				 * Verifico se esiste almento un progetto in cui lo stato del documento e' DA
				 * COMPLETARE
				 */
				PbandiRDocSpesaProgettoVO filterDocPrj = new PbandiRDocSpesaProgettoVO();
				filterDocPrj.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDoc));
				filterDocPrj.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(statoDaCompletare.getId()));

				List<PbandiRDocSpesaProgettoVO> docPrjDaCompletare = genericDAO.findListWhere(filterDocPrj);

				if (!ObjectUtil.isEmpty(docPrjDaCompletare)) {
					PbandiTDocumentoDiSpesaVO filter = new PbandiTDocumentoDiSpesaVO();
					filter.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDoc));
					PbandiTDocumentoDiSpesaVO documento = genericDAO.findSingleWhere(filter);

					BigDecimal importoTotaleDocumento = documento.getImportoTotaleDocumento() == null
							? new BigDecimal(0.0)
							: documento.getImportoTotaleDocumento();

					/*
					 * Calcolo il totale dei pagamenti
					 */
					BigDecimal importoTotalePagamenti = documentoDiSpesaManager
							.getImportoTotalePagamenti(dichiarazioneDiSpesaDTO.getIdProgetto(), idDoc);

					/*
					 * Calcolo il totale delle note di credito
					 */
					BigDecimal importoTotaleNote = documentoDiSpesaManager.getImportoTotaleNoteCredito(idDoc);

					BigDecimal totaleDocumentoNetto = NumberUtil.subtract(importoTotaleDocumento, importoTotaleNote);
					if (NumberUtil.compare(totaleDocumentoNetto, importoTotalePagamenti) <= 0) {
						/*
						 * Aggiorno lo stato dei documento-progetto in stato DA_COMPLETARE. Per i
						 * documento-progetto che hanno lo stato validazione in VALIDATO, NON VALIDATO o
						 * PARZIALMENTE VALIDATO alllora lo stato del documento-progetto sara' lo stato
						 * della validazione,mentre per i documento-progetti per i quali lo stato della
						 * validazione e' RESPINTO allora lo stato della documento-progetto sara'
						 * DICHIARABILE
						 */

						PbandiRDocSpesaProgettoVO filtroDoc = new PbandiRDocSpesaProgettoVO();
						filtroDoc.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDoc));

						PbandiRDocSpesaProgettoVO filtroPrj = new PbandiRDocSpesaProgettoVO();
						filtroPrj.setIdDocumentoDiSpesa(
								NumberUtil.toBigDecimal(dichiarazioneDiSpesaDTO.getIdProgetto()));

						List<PbandiRDocSpesaProgettoVO> filtroStatiValidazione = new ArrayList<PbandiRDocSpesaProgettoVO>();
						// stato validazione VALIDATO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneValidato = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneValidato
								.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoValidato.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneValidato);
						// stato validazione NON VALIDATO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneNonValidato = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneNonValidato
								.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoNonValidato.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneNonValidato);
						// stato validazione PARZIALMENTO VALIDATO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneParzValidato = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneParzValidato
								.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoParzValidato.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneParzValidato);
						// stato validazione RESPINTO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneRespinto = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneRespinto
								.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoRespinto.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneRespinto);

						// stato DA COMPLETARE
						PbandiRDocSpesaProgettoVO filtroStatoDaCompletare = new PbandiRDocSpesaProgettoVO();
						filtroStatoDaCompletare
								.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(statoDaCompletare.getId()));

						AndCondition<PbandiRDocSpesaProgettoVO> andCondition = new AndCondition<PbandiRDocSpesaProgettoVO>(
								Condition.filterBy(filtroDoc), Condition.not(Condition.filterBy(filtroPrj)),
								Condition.filterBy(filtroStatiValidazione),
								Condition.filterBy(filtroStatoDaCompletare));

						List<PbandiRDocSpesaProgettoVO> documenti = genericDAO.findListWhere(andCondition);

						for (PbandiRDocSpesaProgettoVO d : documenti) {
							/*
							 * Se lo stato validazione e' RESPINTO allora lo stato del documento sara'
							 * DICHIARABILE, mentre in tutti gli altri casi lo stato del documento sara' lo
							 * stesso dello stato della validazione
							 */
							if (NumberUtil.compare(d.getIdStatoDocumentoSpesaValid(),
									NumberUtil.toBigDecimal(statoRespinto.getId())) == 0) {
								d.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(statoDichiarabile.getId()));
							} else {
								d.setIdStatoDocumentoSpesa(d.getIdStatoDocumentoSpesaValid());
							}
							d.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
							try {
								logger.warn("aggiorno stato doc di spesa id " + d.getIdDocumentoDiSpesa()
										+ " con idstato:" + d.getIdStatoDocumentoSpesa());
								genericDAO.update(d);
							} catch (Exception e) {
								String msg = "Errore durante l'update su PBANDI_R_DOCSPESA_PROGETTO per il documento["
										+ d.getIdDocumentoDiSpesa() + "] ed il progetto [" + d.getIdProgetto() + "]. ";
								logger.error(msg, e);
								throw new RuntimeException(msg);
							}

							/*
							 * Aggiorno anche lo stato delle note di credito con lo stato del
							 * documento-progetto
							 */
							PbandiTDocumentoDiSpesaVO filterNote = new PbandiTDocumentoDiSpesaVO();
							filterNote.setIdDocRiferimento(d.getIdDocumentoDiSpesa());
							List<PbandiTDocumentoDiSpesaVO> note = genericDAO.findListWhere(filterNote);
							for (PbandiTDocumentoDiSpesaVO nota : note) {
								PbandiRDocSpesaProgettoVO notaprj = new PbandiRDocSpesaProgettoVO();
								notaprj.setIdDocumentoDiSpesa(nota.getIdDocumentoDiSpesa());
								notaprj.setIdProgetto(d.getIdProgetto());
								notaprj.setIdStatoDocumentoSpesa(d.getIdStatoDocumentoSpesa());
								notaprj.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
								try {
									genericDAO.update(notaprj);
								} catch (Exception e) {
									String msg = "Errore durante l'update su PBANDI_R_DOCSPESA_PROGETTO per il documento["
											+ nota.getIdDocumentoDiSpesa() + "] ed il progetto [" + d.getIdProgetto()
											+ "]. ";
									logger.error(msg, e);
									throw new RuntimeException(msg);
								}
							}
						}

					}

				}
			}

			/*
			 * aggiorna pagamenti
			 * 
			 * 
			 * 2. Pagamento per tutti i pagamenti dei documenti di spesa che sono stati
			 * �dichiarati" � StatoValidazioneSpesa uguale a DICHIARATO o RILASCIATO �
			 * Inserire la relazione con la DichiarazioneDiSpesa
			 */

			if (idDocumentiDiSpesa != null && idDocumentiDiSpesa.size() > 0) {

				List<PagamentoDocumentoProgettoVO> listFilterDoc = new ArrayList<PagamentoDocumentoProgettoVO>();
				List<PagamentoDocumentoDichiarazioneVO> listFilterDocDich = new ArrayList<PagamentoDocumentoDichiarazioneVO>();

				for (Long idDocumento : idDocumentiDiSpesa) {
					PagamentoDocumentoProgettoVO filtroDoc = new PagamentoDocumentoProgettoVO();
					filtroDoc.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumento));
					listFilterDoc.add(filtroDoc);
					PagamentoDocumentoDichiarazioneVO filtroDocDich = new PagamentoDocumentoDichiarazioneVO();
					filtroDocDich.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumento));
					listFilterDocDich.add(filtroDocDich);
				}

				PagamentoDocumentoProgettoVO filtroProgetto = new PagamentoDocumentoProgettoVO();
				filtroProgetto.setIdProgetto(NumberUtil.toBigDecimal(dichiarazioneDiSpesaDTO.getIdProgetto()));
				/*
				 * I Pagamenti non devono essere stati utilizzati in altre dichiarazioni del
				 * progetto
				 */
				filtroProgetto.setFlagIsUsedDichPrj(Constants.FLAG_FALSE);

				AndCondition<PagamentoDocumentoProgettoVO> andCondition = new AndCondition<PagamentoDocumentoProgettoVO>(
						Condition.filterBy(filtroProgetto), Condition.filterBy(listFilterDoc));

				List<PagamentoDocumentoProgettoVO> pagamentiDisponibili = genericDAO.findListWhere(andCondition);
				List<PbandiRPagamentoDichSpesaVO> pagamenti = new ArrayList<PbandiRPagamentoDichSpesaVO>();

				for (PagamentoDocumentoProgettoVO pag : pagamentiDisponibili) {
					/*
					 * FIX PBANDI-2314 I pagamenti da inserire sono i pagamenti associati al
					 * documento per il quali esiste ancora del residuo e non sono stati utilizzati
					 * in altre dichiarazioni del progetto
					 */
					BigDecimal residuoPagamento = pag.getResiduoUtilePagamento() == null ? new BigDecimal(0.0)
							: pag.getResiduoUtilePagamento();
					if (NumberUtil.compare(residuoPagamento, new BigDecimal(0.0)) > 0
							&& pag.getFlagIsUsedDichPrj().equals(Constants.FLAG_FALSE)) {
						PbandiRPagamentoDichSpesaVO pagamento = new PbandiRPagamentoDichSpesaVO();
						pagamento.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa));
						pagamento.setIdPagamento(pag.getIdPagamento());
						pagamenti.add(pagamento);
					}
				}

				if (!ObjectUtil.isEmpty(pagamenti)) {
					try {
						genericDAO.insert(pagamenti);
					} catch (Exception e) {
						throw new DichiarazioneDiSpesaException(
								"Errore durante l'inserimento in PBANDI_R_PAGAMENTO_DICH_SPESA.", e);
					}
				}

				/*
				 * Inserisco i dati dei pagamenti, della dichiarazionne di spesa e del documento
				 * nella tabella PBANDI_S_DICH_DOC_SPESA. Ricerco i pagamenti dei documenti
				 * associati alla dichiarazione di spesa corrente
				 */
				PagamentoDocumentoDichiarazioneVO filterDich = new PagamentoDocumentoDichiarazioneVO();
				filterDich.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa));
				filterDich.setAscendentOrder("idDocumentoDiSpesa");

				List<PagamentoDocumentoDichiarazioneVO> pagamentiDocumentiDichiarazione = genericDAO
						.findListWhere(Condition.filterBy(filterDich).and(Condition.filterBy(listFilterDocDich)));

				BigDecimal idDoc = null;
				List<BigDecimal> idPagamentiDocumento = new ArrayList<BigDecimal>();
				for (PagamentoDocumentoDichiarazioneVO pagDocDich : pagamentiDocumentiDichiarazione) {
					if (idDoc == null)
						idDoc = pagDocDich.getIdDocumentoDiSpesa();

					if (NumberUtil.compare(idDoc, pagDocDich.getIdDocumentoDiSpesa()) == 0) {
						idPagamentiDocumento.add(pagDocDich.getIdPagamento());
					} else {
						/*
						 * Rottura per il documento di spesa. Inserisco i
						 * pagamenti-documento-dichiarazione precedenti
						 */
						try {
							pbandiDichiarazioneDiSpesaDAOImpl.inserisciDocumentoPagamentiDichiarazioneCultura(
									NumberUtil.toBigDecimal(idDichiarazioneDiSpesa), idDoc, idPagamentiDocumento);
						} catch (Exception e) {

							String msg = "Errore durante l'insert su PBANDI_S_DICH_DOC_SPESA per il documento[" + idDoc
									+ "] e la dichiarazione [" + idDichiarazioneDiSpesa + "]. ";
							logger.error(msg, e);
							throw new RuntimeException(msg);

						}
						/*
						 * IL documento corrente diviene il nuovo idDoc
						 */
						idDoc = pagDocDich.getIdDocumentoDiSpesa();

						/*
						 * Reset della lista dei pagamenti del documento
						 */
						idPagamentiDocumento = new ArrayList<BigDecimal>();

						/*
						 * Inserisco nella lista dei pagamenti, il pagamento corrente
						 */
						idPagamentiDocumento.add(pagDocDich.getIdPagamento());
					}
				}

				/*
				 * Inserisco l' ultima lista dei pagamenti
				 */
				if (!ObjectUtil.isEmpty(idPagamentiDocumento)) {
					try {
						pbandiDichiarazioneDiSpesaDAOImpl.inserisciDocumentoPagamentiDichiarazioneCultura(
								NumberUtil.toBigDecimal(idDichiarazioneDiSpesa), idDoc, idPagamentiDocumento);
					} catch (Exception e) {

						String msg = "Errore durante l'insert su PBANDI_S_DICH_DOC_SPESA per il documento[" + idDoc
								+ "] e la dichiarazione [" + idDichiarazioneDiSpesa + "]. ";
						logger.error(msg, e);
						throw new RuntimeException(msg);

					}

				}

			}

		} else {
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
			logger.error("Non sono stati trovati documenti di spesa.", ex);
			throw ex;
			// Errore! Non sono stati trovati dei documenti di spesa da
			// rilasciare nella dichiarazione per il periodo selezionato
		}
	}

	@Override
	public DocumentoContabileDTO[] findDocumentiContabili(Long idUtente, String identitaDigitale, Long idProgetto,
			Date dal, Date al)
			throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa

	) throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtroDichiarazioneDiSpesaDTO" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, filtroDichiarazioneDiSpesa);
		logger.info("\n\n\n\n\nverificaDichiarazioneDiSpesa new timeout mgmnt.... *******************");
		logger.shallowDump(filtroDichiarazioneDiSpesa, "info");
		long start = getTimerManager().start();
		EsitoOperazioneVerificaDichiarazioneSpesa esito = new EsitoOperazioneVerificaDichiarazioneSpesa();
		try {
			boolean isFinaleOIntegrativa = Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE
					.equalsIgnoreCase(filtroDichiarazioneDiSpesa.getTipoDichiarazione())
					|| Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA
							.equalsIgnoreCase(filtroDichiarazioneDiSpesa.getTipoDichiarazione());

			// documentiDaInviare: tutti i documenti che non sono note di credito
			List<DocumentoSpesaDaInviareVO> documentiDaInviare = findDocumentiDaInviare(filtroDichiarazioneDiSpesa);

			String statoDocumentoDaCompletare = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE;
			DecodificaDTO decodificaStatoDaCompletare = decodificheManager
					.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, statoDocumentoDaCompletare);

			// documentiDaRestituire: tutti i documenti compresi le note di
			// credito
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO> documentiDaRestituire = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO>();

			int totDocumentiDaInviare = 0;
			java.util.Set<BigDecimal> nonInviabili = new HashSet<BigDecimal>();
			/*
			 * FIX PBANDI-2314
			 */
			// int pagamenti = 0;
			if (!isEmpty(documentiDaInviare)) {
				totDocumentiDaInviare = documentiDaInviare.size();

				boolean isBr01 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(),
						RegoleConstants.BR01_GESTIONE_ATTRIBUTI_QUALIFICA);

				boolean isBr02 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(), RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);

				boolean isBr03 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(), RegoleConstants.BR03_RILASCIO_SPESE_QUIETANZATE);

				boolean isBr04 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(), RegoleConstants.BR04_VISUALIZZA_DATA_VALUTA);

				boolean isBr05 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(), RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA);

				boolean isBr07 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(),
						RegoleConstants.BR07_CONTROLLO_SPESE_TOTALMENTE_QUIETANZATE);

				boolean isBr39 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(),
						RegoleConstants.BR39_CONTROLLO_QUIETANZA_NON_INFERIORE_RENDICONTABILE);

				boolean isBr42 = regolaManager.isRegolaApplicabileForBandoLinea(
						filtroDichiarazioneDiSpesa.getIdBandoLinea(), RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);

				if (isBr07 && isFinaleOIntegrativa)
					isBr03 = true;

				List<DocumentoSpesaDaInviareVO> noteDiCredito = new ArrayList<DocumentoSpesaDaInviareVO>();

				List<BigDecimal> listIdDoc = beanUtil.extractValues(documentiDaInviare, "idDocumentoDiSpesa",
						BigDecimal.class);
				for (BigDecimal idDocRiferimento : listIdDoc) {
					NotaDiCreditoVO nota = new NotaDiCreditoVO();
					nota.setIdDocDiRiferimento(idDocRiferimento);
					nota.setIdProgetto(new BigDecimal(filtroDichiarazioneDiSpesa.getIdProgetto()));
					nota.setIdSoggetto(new BigDecimal(filtroDichiarazioneDiSpesa.getIdSoggetto()));
					noteDiCredito.add(nota);
				}
				noteDiCredito = genericDAO.findListWhere(noteDiCredito);

				// cerco eventuali note di credito associate a quei documenti,
				// se le trovo le faccio vedere al client,ma non rientrano negli
				// eventuali scarti

				documentiDaInviare.addAll(noteDiCredito);

				List<Long> fattureInvalidateDaNotaDiCredito = new ArrayList<Long>();

				for (DocumentoSpesaDaInviareVO documentoDiSpesaVO : documentiDaInviare) {
					if (!getTimerManager().checkTimeout(start))
						throw new DichiarazioneDiSpesaException(ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

					it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO docDaRestituire = new it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO();
					beanUtil.valueCopy(documentoDiSpesaVO, docDaRestituire);

					String cfFornitore = documentoDiSpesaVO.getCodiceFiscaleFornitore();
					if (cfFornitore != null) {
						String nomeFornitore = documentoDiSpesaVO.getDenominazioneFornitore();
						if (nomeFornitore == null) {
							nomeFornitore = documentoDiSpesaVO.getNomeFornitore() + " "
									+ documentoDiSpesaVO.getCognomeFornitore();
						}
						if (cfFornitore.startsWith("PBAN")) {
							cfFornitore = "n.d. - Fornitore estero";
						}
						docDaRestituire.setNomeFornitore(nomeFornitore + " - " + cfFornitore);
					}

					if (documentoDiSpesaVO.getFlagAllegati() != null
							&& documentoDiSpesaVO.getFlagAllegati().equalsIgnoreCase("S"))
						docDaRestituire.setAllegatiPresenti(true);
					else
						docDaRestituire.setAllegatiPresenti(false);

					documentiDaRestituire.add(docDaRestituire);
					boolean completamenteQuietanziato = isCompletamenteQuietanziato(documentoDiSpesaVO,
							filtroDichiarazioneDiSpesa);
					boolean importoQuietanzatoSuperioreImportoDocumento = isImportoQuietanzatoSuperioreImportoDocumento(
							documentoDiSpesaVO);
					boolean cedolino = documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(documentoDiSpesaVO);
					boolean notaDiCredito = isNotaDiCredito(documentoDiSpesaVO);

					boolean scartato = false;

					if (isNull(documentoDiSpesaVO.getTotaleImportoPagamenti()) && !notaDiCredito && !scartato) {
						logger.debug("Non ci sono pagamenti per il documento "
								+ documentoDiSpesaVO.getIdDocumentoDiSpesa() + " e non e' una nota di credito");
						docDaRestituire.setMotivazione(DOCUMENTO_SENZA_PAGAMENTI);
						scartato = true;
						nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
					}

					boolean documentoElettronico = documentoDiSpesaVO.getFlagElettronico() != null
							&& documentoDiSpesaVO.getFlagElettronico().equalsIgnoreCase("S") ? true : false;
					boolean documentoConAllegatiDigitali = isTipoInvioDigitale(documentoDiSpesaVO.getTipoInvio());

					/*
					 * Se il documento prevede il tipo di invio ELETTRONICO (E) o
					 * ALLEGATI_DIGITALI(S) allora verifico se esistono dei file associati al
					 * documento ed ai pagamenti
					 */
					if (!scartato && isBr42 && (documentoConAllegatiDigitali || documentoElettronico)) {
						/*
						 * Ricerco i file allegati al documento-progetto
						 */

						logger.info("\n\n\n\n\n\n\n\n\n\nfiltroDichiarazioneDiSpesa.getIdProgetto()"
								+ filtroDichiarazioneDiSpesa.getIdProgetto());
						DocumentoIndexAssociatoDocSpesaVO filterDocIndexDocSpesa = new DocumentoIndexAssociatoDocSpesaVO();
						filterDocIndexDocSpesa.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						filterDocIndexDocSpesa
								.setIdProgetto(new BigDecimal(filtroDichiarazioneDiSpesa.getIdProgetto()));
						List<DocumentoIndexAssociatoDocSpesaVO> filesAssociatiDocSpesa = genericDAO
								.findListWhere(filterDocIndexDocSpesa);

						if (ObjectUtil.isEmpty(filesAssociatiDocSpesa)
								&& !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(
										documentoDiSpesaVO.getDescBreveTipoDocSpesa())) {
							logger.warn(
									"Nessun file associato al documento " + documentoDiSpesaVO.getIdDocumentoDiSpesa());
							scartato = true;
							docDaRestituire.setMotivazione(WARNING_DOCUMENTO_DI_SPESA_SENZA_ALLEGATO);
							nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						} else if (!documentoDiSpesaManager
								.isCedolinoCostiStandard(documentoDiSpesaVO.getDescBreveTipoDocSpesa())
								&& !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(
										documentoDiSpesaVO.getDescBreveTipoDocSpesa())) {
							/*
							 * Ricerco i pagamenti del documento e verifico se esiste almeno un file
							 * associato per ogni pagamento. Non serve idProgetto, panarace 12/06/2015
							 */
							PbandiRPagamentoDocSpesaVO pagamentiFilterVO = new PbandiRPagamentoDocSpesaVO();
							pagamentiFilterVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							List<PbandiRPagamentoDocSpesaVO> pagamentiDocumento = genericDAO
									.findListWhere(pagamentiFilterVO);
							if (!ObjectUtil.isEmpty(pagamentiDocumento)) {
								BigDecimal idEntitaPagamento = documentoManager.getIdEntita(PbandiTPagamentoVO.class);
								for (PbandiRPagamentoDocSpesaVO pagDoc : pagamentiDocumento) {

									PbandiTFileEntitaVO pbandiTFileEntitaVO = new PbandiTFileEntitaVO();
									pbandiTFileEntitaVO.setIdEntita(idEntitaPagamento);
									pbandiTFileEntitaVO.setIdTarget(pagDoc.getIdPagamento());

									List<PbandiTFileEntitaVO> rfileEntitaPag = genericDAO
											.findListWhere(pbandiTFileEntitaVO);
									if (ObjectUtil.isEmpty(rfileEntitaPag)) {
										logger.warn("\n\nNo file associated to pagamento[" + pagDoc.getIdPagamento()
												+ "] ,  documento di spesa["
												+ documentoDiSpesaVO.getIdDocumentoDiSpesa() + "]. Doc DESCARDED!!!");
										scartato = true;
										docDaRestituire.setMotivazione(
												WARNING_DOCUMENTO_DI_SPESA_CON_PAGAMENTI_SENZA_ALLEGATI);
										nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
										break;
									}
								}
							}

						}

						// check if exist at least one T_doc_index with numero_protocollo not null for
						// allegati doc/progetto

						/*
						 * COMMENTATO PER PNC 17/12/2015 if(documentoElettronico){ boolean
						 * hasNumeroProtocollo=false; for (DocumentoIndexAssociatoDocSpesaVO
						 * documentoIndexAssociatoDocSpesaVO : filesAssociatiDocSpesa) {
						 * if(!ObjectUtil.isEmpty(documentoIndexAssociatoDocSpesaVO.getNumProtocollo()))
						 * { hasNumeroProtocollo=true; break; } }
						 * 
						 * if(!hasNumeroProtocollo){ logger.warn("NO NUM PROTOCOLLO, doc DESCARDED!");
						 * scartato = true; docDaRestituire.setMotivazione(
						 * WARNING_DOCUMENTO_DI_SPESA_SENZA_NUMERO_PROTOCOLLO);
						 * nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa()); } } /*
						 * COMMENTATO PER PNC 17/12/2015
						 */

					}

					/*
					 * Se il documento e' in stato DA COMPLETARE e non e' presente la regola BR03,
					 * devo verificare che siano stati inseriti nuovi pagamenti
					 */
					if (documentoDiSpesaVO.getIdStatoDocumentoSpesa()
							.compareTo(NumberUtil.toBigDecimal(decodificaStatoDaCompletare.getId())) == 0 && !scartato
							&& !notaDiCredito && !isBr03) {

						BigDecimal numPagamentiInviabili = isNull(documentoDiSpesaVO.getNumPagamentiInviabili())
								? new BigDecimal(0)
								: documentoDiSpesaVO.getNumPagamentiInviabili();

						if (NumberUtil.compare(numPagamentiInviabili, new BigDecimal(0)) == 0) {
							scartato = true;
							nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							logger.warn("No  new  pagamenti per   doc " + documentoDiSpesaVO.getIdDocumentoDiSpesa()
									+ " e non e' una nota di credito, DESCARDED!");
							docDaRestituire.setMotivazione(DOCUMENTO_SENZA_NUOVI_PAGAMENTI);
						}

					}

					if (!scartato && !isRendicontabileCompletamenteAssociato(documentoDiSpesaVO,
							filtroDichiarazioneDiSpesa)) {
						logger.warn("RENDICONTABILE_NON_COMPLETAMENTE_ASSOCIATO, doc DESCARDED!");
						docDaRestituire.setMotivazione(RENDICONTABILE_NON_COMPLETAMENTE_ASSOCIATO);
						scartato = true;
						if (!notaDiCredito) {
							nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						}
					}

					if (isBr03 && !scartato) {
						logger.debug("Regola BR03 applicabile ! controllo che il doc "
								+ documentoDiSpesaVO.getIdDocumentoDiSpesa()
								+ " sia completamente quietanziato (solo se non � una spesa forfettaria)");
						if (!notaDiCredito && !completamenteQuietanziato) {
							logger.debug("BR03 - il doc <" + documentoDiSpesaVO.getIdDocumentoDiSpesa()
									+ "> NON �  completamente quietanziato !");
							docDaRestituire.setMotivazione(SPESA_NON_TOTALMENTE_QUIETANZATA);
							scartato = true;
							nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						}

					}

					if (!scartato && isFattura(documentoDiSpesaVO)) {
						logger.debug("E'una fattura");
						if (importoQuietanzatoSuperioreImportoDocumento) {
							docDaRestituire.setMotivazione(FATTURA_QUIETANZATO_MAGGIORE_DEL_DOCUMENTO);
							scartato = true;
							nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						}
					}

					if (!scartato) {
						String errorCode = documentoDiSpesaManager.controlliVociSpesaNoteCreditoDocumento(
								documentoDiSpesaVO.getIdDocumentoDiSpesa(), documentoDiSpesaVO.getIdProgetto(),
								notaDiCredito);
						if (errorCode != null) {
							docDaRestituire.setMotivazione(errorCode);
							scartato = true;
							if (!notaDiCredito) {
								nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							}
						}
					}

					if (isBr01 && !scartato) {
						if (cedolino && isMonteOreSuperato(documentoDiSpesaVO, filtroDichiarazioneDiSpesa)) {
							docDaRestituire.setMotivazione(MONTE_ORE_SUPERATO);
							scartato = true;
							if (!notaDiCredito) {
								nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							}
						}
					}

					// }L{ PBANDI-2280
					if (!notaDiCredito && !scartato && isBr39) {
						boolean importoQuietanzatoInferioreRendicontabileProgetto = isImportoQuietanzatoInferioreRendicontabileProgetto(
								documentoDiSpesaVO);
						if (importoQuietanzatoInferioreRendicontabileProgetto) {
							docDaRestituire.setMotivazione(QUIETANZATO_INFERIORE_RENDICONTABILE);
							scartato = true;
							if (!notaDiCredito) {
								nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							}
						}
					}

					// }L{ PBANDI-1723
					// se una NdC non passa i controlli, non deve passarli
					// neanche la fattura associata
					if (notaDiCredito && scartato) {
						fattureInvalidateDaNotaDiCredito
								.add(beanUtil.transform(documentoDiSpesaVO.getIdDocDiRiferimento(), Long.class));
					}

				}
				// }L{ PBANDI-1723
				if (!fattureInvalidateDaNotaDiCredito.isEmpty()) {
					for (DocumentoDiSpesaDTO documento : documentiDaRestituire) {
						if (fattureInvalidateDaNotaDiCredito.contains(documento.getIdDocumentoDiSpesa())) {
							logger.debug("Il documento " + documento.getIdDocumentoDiSpesa() + " di tipo "
									+ documento.getDescTipoDocumentoDiSpesa()
									+ " � invalidato dalla nota di credito associata");
							documento.setMotivazione(NOTA_DI_CREDITO_ASSOCIATA_INVALIDA);
							// }L{ il documento referenziato, NON la nota di
							// credito, che e' stata gia' scartata
							nonInviabili.add(new BigDecimal(documento.getIdDocumentoDiSpesa()));
						}
					}
				}
			}

			esito.setDocumentiDiSpesa(documentiDaRestituire
					.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO[] {}));

			int documentiInviabili = totDocumentiDaInviare - nonInviabili.size();

			List<String> messages = new ArrayList<String>();

			// DICH INTERMEDIA
			if (Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA
					.equalsIgnoreCase(filtroDichiarazioneDiSpesa.getTipoDichiarazione())) {

				if (totDocumentiDaInviare == 0) {
					logger.info("Non sono stati trovati dei documenti di spesa nel periodo indicato.");
					esito.setEsito(Boolean.FALSE);
					esito.setMessage(ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
				} else if (totDocumentiDaInviare > 0) {
					if (documentiInviabili == 0) {
						esito.setEsito(Boolean.FALSE);
						esito.setMessage(MessaggiConstants.KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI);
					} else if (documentiInviabili == totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.VERIFICA_DOCUMENTI_SUPERATA);
					} else if (documentiInviabili < totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI);
					}
				}
			} else if (isFinaleOIntegrativa) {
				// DICH FINALE o INTEGRATIVA
				if (totDocumentiDaInviare == 0) {
					logger.info("Non sono stati trovati dei documenti di spesa nel periodo indicato.");
					esito.setEsito(Boolean.TRUE);
					messages.add(ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
					messages.add(MessaggiConstants.KEY_MSG_POSSIBILE_DICHIARARE_CONCLUSA_DICHIARAZIONE_SENZA_DOC);
				} else if (totDocumentiDaInviare > 0) {
					if (documentiInviabili == 0 || documentiInviabili < totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI);
						messages.add(MessaggiConstants.KEY_MSG_POSSIBILE_DICHIARARE_CONCLUSA_DICHIARAZIONE_SENZA_DOC);
					} else if (documentiInviabili == totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.VERIFICA_DOCUMENTI_SUPERATA);
					}
				}
			}

			esito.setMessaggi(messages.toArray(new String[] {}));
		} catch (DichiarazioneDiSpesaException ex) {
			logger.error("Errore DichiarazioneDiSpesaException" + ex.getMessage(), ex);
			throw ex;
		} catch (Throwable t) {
			logger.error("Errore throwable" + t.getMessage(), t);
			if (t instanceof DichiarazioneDiSpesaException)
				throw (DichiarazioneDiSpesaException) t;
			else {
				logger.info("erorre timeout? ms:" + (System.currentTimeMillis() - start));
				if (!getTimerManager().checkTimeout(start))
					throw new DichiarazioneDiSpesaException(ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);
				throw new UnrecoverableException(t.getMessage());
			}
		}
		return esito;
	}

	private List<DocumentoSpesaDaInviareVO> findDocumentiDaInviare(
			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa) {
		DocumentoSpesaDaInviareVO documentoSpesaVO = new DocumentoSpesaDaInviareVO();
		documentoSpesaVO.setIdProgetto(new BigDecimal(filtroDichiarazioneDiSpesa.getIdProgetto()));
		documentoSpesaVO.setIdSoggetto(new BigDecimal(filtroDichiarazioneDiSpesa.getIdSoggetto()));
		/*
		 * FIX PBANDI-2314 Aggiundo il filtro sullo stato del documento. Possono essere
		 * inviati solamente i documenti in stato dichiarabile o DA COMPLETARE
		 */
		String statoDocumentoDichiarabile = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
		String statoDocumentoDaCompletare = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE;
		DecodificaDTO decodificaStatoDichiarabile = decodificheManager
				.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, statoDocumentoDichiarabile);
		DecodificaDTO decodificaStatoDaCompletare = decodificheManager
				.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, statoDocumentoDaCompletare);

		List<DocumentoSpesaDaInviareVO> filterStatiDocumento = new ArrayList<DocumentoSpesaDaInviareVO>();
		DocumentoSpesaDaInviareVO filterStatoDichiarabile = new DocumentoSpesaDaInviareVO();
		filterStatoDichiarabile.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(decodificaStatoDichiarabile.getId()));
		filterStatiDocumento.add(filterStatoDichiarabile);

		DocumentoSpesaDaInviareVO filterStatoDaCompletare = new DocumentoSpesaDaInviareVO();
		filterStatoDaCompletare.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(decodificaStatoDaCompletare.getId()));
		filterStatiDocumento.add(filterStatoDaCompletare);

		DocumentoSpesaDaInviareVO documentoSpesaVOConData = new DocumentoSpesaDaInviareVO();
		documentoSpesaVOConData.setDataDocumentoDiSpesa(
				DateUtil.utilToSqlDate(filtroDichiarazioneDiSpesa.getDataFineRendicontazione()));

		LessThanOrEqualCondition<DocumentoSpesaDaInviareVO> lessCondition = new LessThanOrEqualCondition<DocumentoSpesaDaInviareVO>(
				documentoSpesaVOConData);

		List<DocumentoSpesaDaInviareVO> documentiDaInviare = genericDAO
				.findListWhere(lessCondition.and(new FilterCondition<DocumentoSpesaDaInviareVO>(documentoSpesaVO),
						new FilterCondition<DocumentoSpesaDaInviareVO>(filterStatiDocumento)));
		return documentiDaInviare;
	}

	private boolean isCompletamenteQuietanziato(DocumentoSpesaDaInviareVO documentoDiSpesaVO,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO) throws ManagerException {
		boolean result = true;
		double importoTotaleDocumento = 0;
		/*
		 * Se il documento e' un CEDOLINO allora l' importo sul quale applicare l'
		 * algoritmo e' dato dal massimo tra l' importo rendicontabile e l'importo del
		 * documento. In tutti gli altri casi l' importo e' rappresentato dal totale del
		 * documento.
		 */
		if (documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(documentoDiSpesaVO)) {
			double totaleDocumento = NumberUtil.getDoubleValue(documentoDiSpesaVO.getImportoTotaleDocumento());
			double importoRendicontabile = NumberUtil.getDoubleValue(documentoDiSpesaVO.getImportoRendicontazione());
			if (totaleDocumento > importoRendicontabile)
				importoTotaleDocumento = totaleDocumento;
			else
				importoTotaleDocumento = importoRendicontabile;
		} else {
			importoTotaleDocumento = NumberUtil.getDoubleValue(documentoDiSpesaVO.getImportoTotaleDocumento());
		}
		List<Long> idDocumenti = new ArrayList<Long>();
		idDocumenti.add(documentoDiSpesaVO.getIdDocumentoDiSpesa().longValue());
		/*
		 * Recupero le eventuali note di credito alle quali il documento e' associato e
		 * calcolo il totale del documento al netto di eventuali note di credito
		 */
		/*
		 * FIX. PBandi-471. Riattivo il controllo per le note di credito.
		 */

		importoTotaleDocumento = importoTotaleDocumento
				- NumberUtil.getDoubleValue(documentoDiSpesaVO.getTotaleNoteCredito());

		/*
		 * FIX: PBandi-550 Correzione arrotondamento
		 */
		if (NumberUtil.toRoundDouble(importoTotaleDocumento) > NumberUtil
				.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO.getTotaleImportoPagamenti())))
			result = false;

		return result;

	}

	private boolean isNotaDiCredito(DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
		if (documentoDiSpesaVO.getDescBreveTipoDocSpesa()
				.equalsIgnoreCase(DocumentoDiSpesaManager.DESC_BREVE_NOTA_CREDITO))
			return true;
		return false;
	}

	/**
	 * Verifica che la somma di tutte le quote parte del documento di spesa/progetto
	 * sia maggiore o uguale all' importo rendicontabile del documento di spesa
	 * 
	 * @param documentoDiSpesaVO
	 * @param filtroDichiarazioneDiSpesa
	 * @return
	 */
	private boolean isRendicontabileCompletamenteAssociato(DocumentoSpesaDaInviareVO documentoDiSpesaVO,
			DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa) {

		if (NumberUtil.toRoundDouble(NumberUtil.toDouble(documentoDiSpesaVO.getTotaleImportoQuotaParte())) >= NumberUtil
				.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO.getImportoRendicontazione()))) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Verifico se il documento � di tipo: - Fattura FT - Fattura fideiussoria FF -
	 * Fattura leasing FL
	 * 
	 * @param documentoDiSpesaVO
	 * @return
	 */
	private boolean isFattura(DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
		Long idTipoDocumentoDiSpesa = documentoDiSpesaVO.getIdTipoDocumentoDiSpesa().longValue();
		DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
				idTipoDocumentoDiSpesa);
		if (decodifica != null && (decodifica.getDescrizioneBreve()
				.equals(DocumentoDiSpesaManager
						.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA))
				|| decodifica.getDescrizioneBreve()
						.equals(DocumentoDiSpesaManager.getCodiceTipoDocumentoDiSpesa(
								GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA))
				|| decodifica.getDescrizioneBreve().equals(DocumentoDiSpesaManager.getCodiceTipoDocumentoDiSpesa(
						GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING)))) {
			return true;

		} else {
			return false;
		}

	}

	/**
	 * Verifica che il numero totale delle ore lavorate non superi il monte ore
	 * orario del fornitore
	 * 
	 * @param documentoDiSpesaVO
	 * @param filtroDichiarazioneDiSpesa
	 * @return
	 */
	private boolean isMonteOreSuperato(DocumentoSpesaDaInviareVO documentoDiSpesaVO,
			DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa) {

		/*
		 * Calcolo il totale delle ore lavorate
		 */
		/*
		 * FIX PBandi-580: Il totale delle ore lavorate e' calcolato su tutti i cedolini
		 * del fornitore
		 */

		QuotaParteTipoDocumentoFornitoreVO filtroCedolino = new QuotaParteTipoDocumentoFornitoreVO();
		filtroCedolino.setIdFornitore(documentoDiSpesaVO.getIdFornitore().longValue());
		filtroCedolino.setDescBreveTipoDocSpesa(DocumentoDiSpesaManager
				.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO));

		QuotaParteTipoDocumentoFornitoreVO filtroAutodichiarazioneSoci = new QuotaParteTipoDocumentoFornitoreVO();
		filtroAutodichiarazioneSoci.setIdFornitore(documentoDiSpesaVO.getIdFornitore().longValue());
		filtroAutodichiarazioneSoci.setDescBreveTipoDocSpesa(DocumentoDiSpesaManager.getCodiceTipoDocumentoDiSpesa(
				GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI));
		List<QuotaParteTipoDocumentoFornitoreVO> quote = genericDAO
				.where(Condition.filterBy(filtroCedolino).or(Condition.filterBy(filtroAutodichiarazioneSoci))).select();

		double totaleOreLavorate = 0.0;
		for (QuotaParteTipoDocumentoFornitoreVO quota : quote) {
			Double oreLavorate = quota.getOreLavorate() == null ? 0 : NumberUtil.toDouble(quota.getOreLavorate());
			totaleOreLavorate += oreLavorate;
		}

		/*
		 * Ricavo il monte ore del fornitore
		 */
		Long idFornitore = documentoDiSpesaVO.getIdFornitore().longValue();
		PbandiRFornitoreQualificaVO qualifica = pbandiDichiarazioneDiSpesaDAOImpl.findQualificaFornitore(idFornitore);
		if (qualifica == null)
			return true;

		// FIXME }L{ da rimuovere
		double monteOreFornitore = 0d; // NumberUtil.getDoubleValue(qualifica.getMonteOre());
		/*
		 * FIX: PBandi-550 Correzione arrotondamento
		 */
		if (NumberUtil.toRoundDouble(totaleOreLavorate) > NumberUtil.toRoundDouble(monteOreFornitore))
			return true;
		else
			return false;

	}

	/**
	 * Verifica se il quietanzato residuo del documento e' strettamente minore del
	 * rendicontabile residuo
	 * 
	 * @see PBANDI-2280
	 */

	private boolean isImportoQuietanzatoInferioreRendicontabileProgetto(DocumentoSpesaDaInviareVO documentoDiSpesaVO) {

		// QUIETANZATO: somma di T_PAGAMENTO.importo_pagamento
		PagamentoDocumentoProgettoVO rpdsVO = new PagamentoDocumentoProgettoVO();
		rpdsVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
		rpdsVO.setIdProgetto(documentoDiSpesaVO.getIdProgetto());
		List<PagamentoDocumentoProgettoVO> pagamentiDocSpesa = genericDAO.findListWhere(Condition.filterBy(rpdsVO));

		BigDecimal residuoTotPagamento = new BigDecimal(0);
		BigDecimal quietanzatoTotale = new BigDecimal(0);
		for (PagamentoDocumentoProgettoVO pag : pagamentiDocSpesa) {

			BigDecimal residuoPagamento = pag.getResiduoUtilePagamento() == null ? new BigDecimal(0)
					: pag.getResiduoUtilePagamento();
			BigDecimal quietanzato = pag.getImportoQuietanzato() == null ? new BigDecimal(0)
					: pag.getImportoQuietanzato();
			residuoTotPagamento = NumberUtil.sum(residuoTotPagamento, residuoPagamento);
			quietanzatoTotale = NumberUtil.sum(quietanzato, quietanzatoTotale);

		}

		BigDecimal rendicontabileResiduoProgetto = NumberUtil.subtract(documentoDiSpesaVO.getImportoRendicontazione(),
				quietanzatoTotale);

		if (NumberUtil.compare(residuoTotPagamento, rendicontabileResiduoProgetto) < 0) {
			return true;
		}

		return false;

	}


}
