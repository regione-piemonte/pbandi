/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.business.contoeconomico;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoCopiaFallitaException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoRibaltamentoFallitoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico.ProceduraAggiudicazioneProgetto1420VO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico.ProceduraAggiudicazioneProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbwebrce.dto.DocumentoAllegato;
import it.csi.pbandi.pbwebrce.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.ContoEconomicoMaxDataFineVO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.doqui.index.ecmengine.dto.Node;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ContoeconomicoBusinessImpl extends BusinessImpl implements ContoeconomicoSrv {

	private static final String PERIODO_UNICO = "9999";

	static final private Map<String, String> mapVisualizzazioneMaster = new HashMap<String, String>();
	static final private Map<String, String> mapMain = new HashMap<String, String>();
	static final private Map<String, String> mapMaster = new HashMap<String, String>();
	static final private Map<String, String> mapCopyBen = new HashMap<String, String>();
	static final private Map<String, String> mapCopyIst = new HashMap<String, String>();
	static final private Map<String, String> mapCommon = new HashMap<String, String>();

	private static final BidiMap MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE = new TreeBidiMap();
	private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO;
	static final private Map<String, Map<String, String>> mappaturaPerTipoContoEconomico = new HashMap<String, Map<String, String>>();

	static {
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("dtEffettiva", "dtEffettiva");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("dtPrevista", "dtPrevista");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("idMotivoScostamento", "idMotivoScostamento");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("idStepAggiudicazione", "idStepAggiudicazione");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("importoStep", "importo");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("descStep", "descStepAggiudicazione");

		MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO = MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE
				.inverseBidiMap();
	}

	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO genericDAO;
	@Autowired
	private it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO genericDAOpbwebrce;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager progettoManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager popolaTemplateManager;
	// NOTA: suoi bean senza autowired
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager rappresentanteLegaleManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager sedeManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl neofluxBusinessImpl;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager soggettoManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager decodificheManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager documentoManager;
	@Autowired
	private it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione;

	{
		mapVisualizzazioneMaster.put("importoAmmesso", "importoSpesaAmmessa");
		mapVisualizzazioneMaster.put("importoQuietanzato", "importoSpesaQuietanziata");
		mapVisualizzazioneMaster.put("importoRendicontato", "importoSpesaRendicontata");
		mapVisualizzazioneMaster.put("importoValidato", "importoSpesaValidataTotale");
		mapVisualizzazioneMaster.put("descVoceDiSpesa", "label");
		mapVisualizzazioneMaster.put("idVoceDiSpesa", "idVoce");
		mapVisualizzazioneMaster.put("idContoEconomico", "idContoEconomico");
		mapVisualizzazioneMaster.put("idVoceDiSpesaPadre", "idVocePadre");
		// Cultura
		mapVisualizzazioneMaster.put("idTipologiaVoceDiSpesa", "idTipologiaVoceDiSpesa");
		mapVisualizzazioneMaster.put("percSpGenFunz", "percSpGenFunz");

		mapMain.put("importoRichiesto", "importoRichiestoInDomanda");
		mapMain.put("importoAmmesso", "importoSpesaAmmessaInDetermina");

		mapMaster.put("importoRichiesto", "importoRichiestoUltimaProposta");
		mapMaster.put("importoAmmesso", "importoSpesaAmmessaUltima");
		mapMaster.put("importoRendicontato", "importoSpesaRendicontata");
		mapMaster.put("importoQuietanzato", "importoSpesaQuietanziata");
		mapMaster.put("importoValidato", "importoSpesaValidataTotale");

		//Architetture Rurali
		mapMaster.put("delta", "delta");

		mapCopyBen.put("importoRichiesto", "importoRichiestoNuovaProposta");

		mapCopyIst.put("importoAmmesso", "importoSpesaAmmessaRimodulazione");

		mapCommon.put("descVoceDiSpesa", "label");
		mapCommon.put("massimoImportoAmmissibile", "importoAmmessoDaBando");
		mapCommon.put("idVoceDiSpesa", "idVoce");
		mapCommon.put("idRigoContoEconomico", "idRigoContoEconomico");
		mapCommon.put("idContoEconomico", "idContoEconomico");
		mapCommon.put("voceAssociataARigo", "voceAssociataARigo");

		mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN, mapMain);
		mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER, mapMaster);
		mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN, mapCopyBen);
		mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST, mapCopyIst);
	}

	public it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO getGenericDAOpbwebrce() {
		return genericDAOpbwebrce;
	}

	public void setGenericDAOpbwebrce(
			it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO genericDAOpbwebrce) {
		this.genericDAOpbwebrce = genericDAOpbwebrce;
	}

	public it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	// Jira PBANDI-2853; Alex: copiato da CertificazioneBusinessImpl su pbandisrv.
	public Double findImportoTotaleDisimpegni(Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException {

		ImportoTotaleDisimpegniVO vo = new ImportoTotaleDisimpegniVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));
		vo = genericDAO.findSingleOrNoneWhere(vo);

		if (vo == null || vo.getImportoTotaleDisimpegni() == null)
			return new Double(0);
		else
			return vo.getImportoTotaleDisimpegni();
	}

	public ArrayList<DocumentoAllegato> getAllegatiPropostaRimodulazioneByIdProgetto(Long idUtente, Long idProgetto)
			throws Exception {

		List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO> list;
		list = pbandiArchivioFileDAOImpl.getFilesAssociatedByIdProgetto(BigDecimal.valueOf(idProgetto),
				"PBANDI_T_CONTO_ECONOMICO");

		ArrayList<DocumentoAllegato> docs = new ArrayList<DocumentoAllegato>();
		for (it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO vo : list) {
			DocumentoAllegato documentoAllegato = new DocumentoAllegato();
			if (vo.getIdDocumentoIndex() != null)
				documentoAllegato.setId(vo.getIdDocumentoIndex().longValue());
			documentoAllegato.setNome(vo.getNomeFile());
			if (vo.getSizeFile() != null)
				documentoAllegato.setSizeFile(vo.getSizeFile().longValue());
			documentoAllegato.setIsDisassociabile(true); // Alex: se disassociabile lo stabilisce Angular.
			docs.add(documentoAllegato);
		}

		return docs;
	}

	public EsitoFindContoEconomicoDTO findContoEconomicoPerPropostaRimodulazione(Long idUtente, String identitaDigitale,
																																							 ProgettoDTO progetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

		ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO = null;

		try {
			// la proposta di rimodulazione prevede che si lavori su 3 possibili tipologie
			// di conto economico
			// - MAIN - MASTER - COPY_BEN
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN;
			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti,
					tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			contoEconomicoRimodulazioneDTO = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMaster);

			EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoDTO();
			esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoRimodulazioneDTO);
			esitoFindContoEconomicoDTO.setLocked(isLockedByIstruttore(contoMaster));
			esitoFindContoEconomicoDTO.setCopiaModificataPresente(mappaConti.get(tipologiaContoEconomicoCopy) != null);
			esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(
					progetto.getIdProgetto(), RegoleConstants.BR27_RIMODULAZIONE_DISPONIBILE));

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			if (contoMain != null) {
				if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA
						.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
					esitoFindContoEconomicoDTO.setInIstruttoria(true);
				else
					esitoFindContoEconomicoDTO.setInIstruttoria(false);
				if (Constants.STATO_CONTO_ECONOMICO_RICHIESTO
						.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
					esitoFindContoEconomicoDTO.setInStatoRichiesto(true);
				else
					esitoFindContoEconomicoDTO.setInStatoRichiesto(false);

			}

			ContoEconomicoMaxDataFineVO datiUltimaProposta = getDatiUltimaProposta(progetto);
			Date dataUltimaProposta = null;

			if (datiUltimaProposta != null) {
				dataUltimaProposta = datiUltimaProposta.getDtFineValidita();
				// VN: Ribasso d' asta. Resituisco il flag dell' ultimo ribasso d' asta in
				// proposta
				if (esitoFindContoEconomicoDTO != null) {
					if (datiUltimaProposta.getPercRibassoAsta() != null) {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
						esitoFindContoEconomicoDTO.setImportoUltimoRibassoAstaInProposta(
								NumberUtil.toDouble(datiUltimaProposta.getImportoRibassoAsta()));
						esitoFindContoEconomicoDTO.setPercUltimoRibassoAstaInProposta(
								NumberUtil.toDouble(datiUltimaProposta.getPercRibassoAsta()));
					} else {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
					}

				}
			}

			ContoEconomicoMaxDataFineVO datiUltimaRimodulazione = getDatiUltimaRimodulazione(progetto);
			Date dataUltimaRimodulazione = null;
			if (datiUltimaRimodulazione != null) {
				dataUltimaRimodulazione = datiUltimaRimodulazione.getDtFineValidita();
				// VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima
				// rimodulazione
				if (esitoFindContoEconomicoDTO != null) {
					if (datiUltimaRimodulazione.getPercRibassoAsta() != null) {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
					} else {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
					}

				}
			}

			esitoFindContoEconomicoDTO.setDataUltimaProposta(dataUltimaProposta);

			esitoFindContoEconomicoDTO.setDataUltimaRimodulazione(dataUltimaRimodulazione);

			esitoFindContoEconomicoDTO
					.setDataFineIstruttoria(mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN).getDtFineValidita());

			esitoFindContoEconomicoDTO
					.setDataPresentazioneDomanda(mapDataPresentazioneDomanda.get(DATA_PRESENTAZIONE_DOMANDA));

			return esitoFindContoEconomicoDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ContoEconomicoMaxDataFineVO getDatiUltimaProposta(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA);
	}

	private ContoEconomicoMaxDataFineVO getDatiContoEconomicoByStato(ProgettoDTO progetto, String stato) {
		ContoEconomicoMaxDataFineVO contoEconomicoMaxDataFineVO = new ContoEconomicoMaxDataFineVO();
		contoEconomicoMaxDataFineVO.setIdProgetto(NumberUtil.createScaledBigDecimal(progetto.getIdProgetto()));
		contoEconomicoMaxDataFineVO.setDescBreveStatoContoEconom(stato);
		ContoEconomicoMaxDataFineVO[] voz = genericDAOpbwebrce.findWhere(contoEconomicoMaxDataFineVO);
		if (!isEmpty(voz))
			return voz[0];
		else
			return null;
	}

	private ContoEconomicoRimodulazioneDTO convertiContoEconomicoPerRimodulazione(List<String> tipiDiConti,
																																								Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici,
																																								RigoContoEconomicoDTO rigoConto) throws Exception {

		Map<String, RigoContoEconomicoDTO> righiContiEconomici = new HashMap<String, RigoContoEconomicoDTO>(
				contiEconomici.size());
		for (String key : contiEconomici.keySet()) {
			righiContiEconomici.put(key, contiEconomici.get(key));
		}
		ContoEconomicoRimodulazioneDTO contoEconomicoConvertito = convertiFigliContoEconomicoPerRimodulazione(
				tipiDiConti, righiContiEconomici, rigoConto);

		for (String tipoDiConto : tipiDiConti) {
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoCaricato = contiEconomici
					.get(tipoDiConto);
			contoEconomicoConvertito.setFlagRibassoAsta(contoEconomicoCaricato.getFlagRibassoAsta());
		}

		return contoEconomicoConvertito;
	}

	private ContoEconomicoRimodulazioneDTO convertiFigliContoEconomicoPerRimodulazione(List<String> tipiDiConti,
																																										 Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
		ContoEconomicoRimodulazioneDTO contoEconomicoDTO = mappaContoRimodulazione(tipiDiConti, contiEconomici,
				rigoConto);

		List<RigoContoEconomicoDTO> figli = contiEconomici.get(tipiDiConti.get(0)).getFigli();

		ContoEconomicoRimodulazioneDTO[] figliTrasformati = null;
		if (figli != null && figli.size() > 0) {
			List<ContoEconomicoRimodulazioneDTO> listaTrasformati = new ArrayList<ContoEconomicoRimodulazioneDTO>();
			for (int i = 0; i < figli.size(); i++) {
				Map<String, RigoContoEconomicoDTO> mappaFigliCorrenti = new HashMap<String, RigoContoEconomicoDTO>();
				for (String tipoDiConto : tipiDiConti) {
					mappaFigliCorrenti.put(tipoDiConto, contiEconomici.get(tipoDiConto).getFigli().get(i));
				}

				RigoContoEconomicoDTO rigoContoFiglio = rigoConto == null ? null : rigoConto.getFigli().get(i);

				ContoEconomicoRimodulazioneDTO figlioTrasformato = convertiFigliContoEconomicoPerRimodulazione(
						tipiDiConti, mappaFigliCorrenti, rigoContoFiglio);

				listaTrasformati.add(figlioTrasformato);
			}
			figliTrasformati = listaTrasformati.toArray(new ContoEconomicoRimodulazioneDTO[listaTrasformati.size()]);
		}
		contoEconomicoDTO.setFigli(figliTrasformati);

		return contoEconomicoDTO;
	}

	private void caricaContiEconomici(BigDecimal idProgetto, List<String> listaTipi,
																		Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti,
																		String tipologiaContoEconomicoCopy, Map<String, Date> mapDataPresentazioneDomanda) throws Exception {

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomico;
		Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomiciPerRimodulazione = contoEconomicoManager
				.findContiEconomici(idProgetto, tipologiaContoEconomicoCopy);
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = contiEconomiciPerRimodulazione
				.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);

		if (contoMain != null) {
			mapDataPresentazioneDomanda.put(DATA_PRESENTAZIONE_DOMANDA, contoMain.getDataPresentazioneDomanda());
			listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MAIN, contoMain);
		}

		contoEconomico = contiEconomiciPerRimodulazione.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
		if (contoEconomico != null) {
			listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MASTER, contoEconomico);
		}
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoCopy = contiEconomiciPerRimodulazione
				.get(tipologiaContoEconomicoCopy);
		if (contoCopy != null) {
			listaTipi.add(tipologiaContoEconomicoCopy);
			mappaConti.put(tipologiaContoEconomicoCopy, contoCopy);
		}
	}

	private ContoEconomicoRimodulazioneDTO mappaContoRimodulazione(List<String> tipiDiConti,
																																 Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
		ContoEconomicoRimodulazioneDTO contoEconomicoDTO = new ContoEconomicoRimodulazioneDTO();

		for (String tipoDiConto : tipiDiConti) {
			RigoContoEconomicoDTO rigo = contiEconomici.get(tipoDiConto);
			getBeanUtil().copyNotNullValues(rigo, contoEconomicoDTO, mappaturaPerTipoContoEconomico.get(tipoDiConto));
			getBeanUtil().copyNotNullValues(rigo, contoEconomicoDTO, mapCommon);
		}

		if (rigoConto != null) {
			contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil
					.toDouble(NumberUtil.percentage(rigoConto.getImportoQuietanzato(), rigoConto.getImportoAmmesso())));
			contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil
					.toDouble(NumberUtil.percentage(rigoConto.getImportoValidato(), rigoConto.getImportoAmmesso())));
		}
		return contoEconomicoDTO;
	}

	private ContoEconomicoMaxDataFineVO getDatiUltimaRimodulazione(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA);
	}

	private ContoEconomicoMaxDataFineVO getDatiUltimaIstruttoria(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_APPROVATO_IN_ISTRUTTORIA);
	}

	private boolean isLockedByIstruttore(it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster) {
		return contoMaster != null
				&& contoMaster.getDescBreveStatoContoEconom().equals(Constants.STATO_CONTO_ECONOMICO_IN_RIMODULAZIONE);
	}

	public EsitoPropostaRimodulazioneDTO proponiRimodulazione(Long idUtente, String identitaDigitale,
																														ContoEconomicoRimodulazioneDTO contoEconomico)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "contoEconomico",
				"contoEconomico.idContoEconomico"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, contoEconomico,
				contoEconomico.getIdContoEconomico());

		try {
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN;

			Map<String, String> map = new HashMap<String, String>();
			map.put("importoRichiestoNuovaProposta", "importoRichiesto");
			map.put("importoSpesaAmmessaUltima", "importoAmmesso");

			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(
					contoEconomicoManager.findIdProgetto(new BigDecimal(contoEconomico.getIdContoEconomico())),
					listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			boolean locked = isLockedByIstruttore(contoMaster);

			EsitoPropostaRimodulazioneDTO esitoPropostaRimodulazioneDTO = new EsitoPropostaRimodulazioneDTO();
			esitoPropostaRimodulazioneDTO.setLocked(locked);
			esitoPropostaRimodulazioneDTO.setContoEconomico(copiaEStoricizza(idUtente, contoEconomico, listaTipi,
					mappaConti, tipologiaContoEconomicoCopy, map, contoMaster, locked));

			return esitoPropostaRimodulazioneDTO;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ContoEconomicoRimodulazioneDTO copiaEStoricizza(Long idUtente,
																													ContoEconomicoRimodulazioneDTO contoEconomico, List<String> listaTipi,
																													Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti,
																													String tipologiaContoEconomicoCopy, Map<String, String> map,
																													it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster, boolean locked)
			throws ContoEconomicoNonTrovatoException, ContoEconomicoCopiaFallitaException,
			ContoEconomicoRibaltamentoFallitoException, Exception {

		ContoEconomicoRimodulazioneDTO nuovoContoEconomico;

		if (locked) {
			nuovoContoEconomico = contoEconomico;
		} else {
			if (!listaTipi.contains(tipologiaContoEconomicoCopy)) {
				listaTipi.add(tipologiaContoEconomicoCopy);
			}
			// creo la copia a meno che non sia gi� una copia

			// TODO non serve
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO copiaContoEconomico;
			copiaContoEconomico = contoEconomicoManager.creaCopia(new BigDecimal(contoEconomico.getIdContoEconomico()),
					tipologiaContoEconomicoCopy, new BigDecimal(idUtente),
					contoEconomicoManager.calcolaStatoInzialeDallaCopiaDalTipo(tipologiaContoEconomicoCopy),
					contoEconomicoManager.calcolaStatoContoMasterDalTipoCopia(tipologiaContoEconomicoCopy));

			// salvo i cambiamenti della copia

			// TODO farlo non sulla copia ma sul mail (quello passato dal client)
			// attenzione a controllare che nel DTO del manager non ci sia
			// qualche campo da impostare (ad esempio idContoEconomico)
			// può darsi che funzioni già cosi
			getBeanUtil().recursiveValueCopy("figli", contoEconomico, copiaContoEconomico, map);
			contoEconomicoManager.aggiornaStoricizzandoRighiContoEconomico(copiaContoEconomico,
					new BigDecimal(idUtente));

			mappaConti.put(tipologiaContoEconomicoCopy,
					contoEconomicoManager.loadContoEconomico(copiaContoEconomico.getIdContoEconomico()));

			nuovoContoEconomico = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMaster);

			// RIBASSO D'ASTA

			// Se il nuovo conto economico ha un ribasso d' asta allora devo
			// verificare che: - se esisteva gia' un ribasso d' asta eseguo l'
			// update - se non esisteva un ribasso d' asta eseguo l' insert
			//
			// Mentre se il nuovo conto economico non ha un ribasso d'asta
			// allora devo verificare che: - se esisteva gia' un ribasso d'asta
			// eseguo la delete - altrimenti niente.

			PbandiTRibassoAstaVO ribassoVO = new PbandiTRibassoAstaVO();
			ribassoVO.setIdContoEconomico(NumberUtil.toBigDecimal(nuovoContoEconomico.getIdContoEconomico()));
			List<PbandiTRibassoAstaVO> ribassi = genericDAO.findListWhere(ribassoVO);
			if (ribassi != null) {
				if (!ribassi.isEmpty()) {
					if (ribassi.size() > 1) {
						ContoEconomicoNonTrovatoException e = new ContoEconomicoNonTrovatoException(
								"Errore trovati piu' di un ribasso associato al conto economino("
										+ contoEconomico.getIdContoEconomico() + ")");
						throw e;
					} else {
						ribassoVO = ribassi.get(0);
					}
				}
			}
			if (contoEconomico.getFlagRibassoAsta() != null && contoEconomico.getFlagRibassoAsta()
					.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {

				ribassoVO.setImporto(BeanUtil.transformToBigDecimal(contoEconomico.getImportoRibassoAsta()));
				ribassoVO.setPercentuale(BeanUtil.transformToBigDecimal(contoEconomico.getPercRibassoAsta()));
				if (ribassoVO.getIdRibassoAsta() != null) {
					ribassoVO.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
					genericDAO.update(ribassoVO);
				} else {
					ribassoVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
					genericDAO.insert(ribassoVO);
				}
			} else {
				if (ribassoVO.getIdRibassoAsta() != null) {
					genericDAO.delete(ribassoVO);
				}
			}
		}
		return nuovoContoEconomico;
	}

	public DatiPerInvioPropostaRimodulazioneDTO caricaDatiPerInvioPropostaRimodulazione(Long idUtente,
																																											String identitaDigitale, ProgettoDTO progetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto,
					progetto.getIdProgetto());

			DatiPerInvioPropostaRimodulazioneDTO datiDTO = new DatiPerInvioPropostaRimodulazioneDTO();

			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici;
			contiEconomici = contoEconomicoManager.findContiEconomici(new BigDecimal(progetto.getIdProgetto()),
					Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN);

			BigDecimal importo = new BigDecimal(0);
			if (contiEconomici.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN) != null) {
				importo = contiEconomici.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN).getImportoRichiesto();
			}
			Double totaleRichiestoNuovaProposta = NumberUtil.toDouble(importo);

			Double importoSpesaAmmessaUltima = NumberUtil
					.toDouble(contiEconomici.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER).getImportoAmmesso());

			ModalitaDiAgevolazioneDTO[] modalitaAgevolazione = caricaModalitaAgevolazionePerProposta(
					progetto.getIdProgetto(), importoSpesaAmmessaUltima, totaleRichiestoNuovaProposta,
					Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			datiDTO.setModalitaDiAgevolazione(modalitaAgevolazione);
			datiDTO.setTotaliModalitaDiAgevolazione(calcolaTotaliModalitaAgevolazione(modalitaAgevolazione));

			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> map = contoEconomicoManager
					.findContiEconomici(new BigDecimal(progetto.getIdProgetto()),
							Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN);
			if (map != null) {
				if (map.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN) != null) {
					BigDecimal importoRichiesto = map.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN)
							.getImportoRichiesto();

					if (importoRichiesto != null) {
						datiDTO.setImportoSpesaRichiesto(NumberUtil.toDouble(importoRichiesto));
					}
				}
			}

			datiDTO.setProgetto(progetto);

			Boolean importiModificabili = regolaManager.isRegolaApplicabileForProgetto(progetto.getIdProgetto(),
					RegoleConstants.BR17_RICHIESTA_NUOVI_IMPORTI);

			logger.debug("\n\n\n\nIMPORTI MODIFICABILI DAL BENEFICIARIO: " + importiModificabili + "\n\n\n\n");
			datiDTO.setIsImportiModificabili(importiModificabili);

			PbandiTProgettoVO progettoVO = progettoManager.getProgetto(progetto.getIdProgetto());

			datiDTO.setIdTipoOperazione(
					progettoVO.getIdTipoOperazione() != null ? progettoVO.getIdTipoOperazione().longValue() : null);

			return datiDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ModalitaDiAgevolazioneDTO[] caricaModalitaAgevolazionePerProposta(double idProgetto,
																																						Double importoSpesaAmmessaUltima, Double totaleRichiestoNuovaProposta, String tipologiaContoEconomico)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// dati caricati dal db
		map.put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
		map.put("descModalitaAgevolazione", "descrizione");
		map.put("massimoImportoAgevolato", "importoMassimoAgevolato");
		map.put("importoErogazioni", "importoErogato");
		map.put("quotaImportoAgevolato", "importoAgevolatoUltimo");
		map.put("quotaImportoRichiesto", "importoRichiestoUltimo");
		map.put("percImportoAgevolatoBando", "percImportoMassimoAgevolato");

		List<ModalitaDiAgevolazioneDTO> list = getBeanUtil().transformList(
				contoEconomicoManager.caricaModalitaAgevolazione(new BigDecimal(idProgetto), tipologiaContoEconomico),
				ModalitaDiAgevolazioneDTO.class, map);
		// dati caricati come default per la presentation
		Map<String, String> mapDefaults = new HashMap<String, String>();
		// metto nel default l'importo ammesso per ultimo dall'istruttore
		mapDefaults.put("importoAgevolatoUltimo", "importoRichiestoNuovo");

		getBeanUtil().valueCopy(list, list, mapDefaults);

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : list) {
			modalitaDiAgevolazioneDTO.setPercImportoRichiestoNuovo(NumberUtil.toDouble(NumberUtil
					.percentage(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), totaleRichiestoNuovaProposta)));
			modalitaDiAgevolazioneDTO.setPercImportoAgevolatoUltimo(NumberUtil.toDouble(NumberUtil
					.percentage(modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo(), importoSpesaAmmessaUltima)));

		}

		return list.toArray(new ModalitaDiAgevolazioneDTO[list.size()]);
	}

	private ModalitaDiAgevolazioneDTO calcolaTotaliModalitaAgevolazione(
			ModalitaDiAgevolazioneDTO[] modalitaAgevolazione) {
		logger.begin();
		ModalitaDiAgevolazioneDTO totale = NumberUtil.accumulate(modalitaAgevolazione, "importoAgevolatoNuovo",
				"importoAgevolatoUltimo", "importoErogato", "importoMassimoAgevolato", "importoRichiestoNuovo",
				"importoRichiestoUltimo", "percImportoAgevolatoNuovo", "percImportoAgevolatoUltimo",
				"percImportoRichiestoUltimo", "percImportoRichiestoNuovo", "percImportoMassimoAgevolato");
		totale.setDescrizione("Totale");
		totale.setIdModalitaDiAgevolazione(null);
		logger.end();
		return totale;
	}

	public ProceduraAggiudicazioneDTO[] findProcedureAggiudicazione(Long idUtente, String identitaDigitale,
																																	ProceduraAggiudicazioneDTO filtro)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "filtro"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, filtro);

		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);

		PbandiDLineaDiInterventoVO bandoLinea = progettoManager.getLineaDiInterventoNormativa(filtro.getIdProgetto());

		if (bandoLinea.getIdLineaDiIntervento().equals(new BigDecimal(202))) {

			ProceduraAggiudicazioneProgetto1420VO filtroVO = new ProceduraAggiudicazioneProgetto1420VO();
			filtroVO.setIdProgetto(new BigDecimal(filtro.getIdProgetto()));

			List<ProceduraAggiudicazioneProgetto1420VO> result = genericDAO.findListWhere(filtroVO);

			return beanUtil.transform(result, ProceduraAggiudicazioneDTO.class);

		} else {

			ProceduraAggiudicazioneProgettoVO filtroVO = beanUtil.transform(filtro,
					ProceduraAggiudicazioneProgettoVO.class, new HashMap<String, String>() {
						{
							put("idTipologiaAggiudicaz", "idTipologiaAggiudicaz");
							put("idProceduraAggiudicaz", "idProceduraAggiudicaz");
							put("idProgetto", "idProgetto");
						}
					});

			ProceduraAggiudicazioneProgettoVO likeConditionVO = new ProceduraAggiudicazioneProgettoVO();
			likeConditionVO.setCodProcAgg(filtro.getCodProcAgg());
			likeConditionVO.setCigProcAgg(filtro.getCigProcAgg());
			LikeContainsCondition<ProceduraAggiudicazioneProgettoVO> likeCondition = new LikeContainsCondition<ProceduraAggiudicazioneProgettoVO>(
					likeConditionVO);

			FilterCondition<ProceduraAggiudicazioneProgettoVO> filterCondition = new FilterCondition<ProceduraAggiudicazioneProgettoVO>(
					filtroVO);

			AndCondition<ProceduraAggiudicazioneProgettoVO> andCondition = new AndCondition<ProceduraAggiudicazioneProgettoVO>(
					filterCondition, likeCondition);

			List<ProceduraAggiudicazioneProgettoVO> result = genericDAO.findListWhere(andCondition);

			return beanUtil.transform(result, ProceduraAggiudicazioneDTO.class);
		}
	}

	public EsitoInvioPropostaRimodulazioneDTO inviaPropostaRimodulazione(Long idUtente, String identitaDigitale,
																																			 IstanzaAttivitaDTO istanzaAttivita, DatiPerInvioPropostaRimodulazioneDTO datiPerInvioPropostaRimodulazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		logger.begin();
		Long idDocIndexPerRollBack = null;

		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "istanzaAttivita", "istanzaAttivita.codUtente",
					"istanzaAttivita.id", "datiPerInvioPropostaRimodulazione",
					"datiPerInvioPropostaRimodulazione.modalitaDiAgevolazione",
					"datiPerInvioPropostaRimodulazione.progetto",
					"datiPerInvioPropostaRimodulazione.progetto.idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, istanzaAttivita,
					istanzaAttivita.getCodUtente(), istanzaAttivita.getId(), datiPerInvioPropostaRimodulazione,
					datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione(),
					datiPerInvioPropostaRimodulazione.getProgetto(),
					datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			Long idSoggetto = datiPerInvioPropostaRimodulazione.getIdSoggetto();
			Long idSoggettoRappresentante = datiPerInvioPropostaRimodulazione.getIdSoggettoRappresentante();
			Double finanziamentoBancario = datiPerInvioPropostaRimodulazione.getImportoFinanziamentoRichiesto();
			BigDecimal idProgetto = new BigDecimal(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			// CHIUSURA PROPOSTA

			aggiornaModalitaPropostaRimodulazione(idUtente,
					datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione(), idProgetto);

			Long idContoEconomico = contoEconomicoManager.chiudiInvioPropostaRimodulazione(idProgetto,
					new BigDecimal(idUtente), datiPerInvioPropostaRimodulazione.getNoteContoEconomico(),
					finanziamentoBancario);

			// VN: Gestione della procedura di aggiudicazione. Verificare se
			// esiste una sola procedura di aggiudicazione (per ribasso d'asta)
			// legata al conto ecomonico (local copy). - se esiste una procedura
			// di aggiudicazione e l'utente ha indicato una nuova procedura di
			// aggiudicazione allora eseguo l'update
			//
			// - se esiste piu' di una procedura di aggiudicazione allora
			// rilancio una eccezione

			// FIND DATI E MAPPING CONTO ECONOMICO PER REPORT

			ProgettoDTO progetto = new ProgettoDTO();
			progetto.setIdProgetto(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			EsitoFindContoEconomicoDTO esitoFindContoEconomico = findContoEconomicoPerPropostaRimodulazione(idUtente,
					identitaDigitale, progetto);

			List<ContoEconomicoItemDTO> contoEconomicoItemList = contoEconomicoManager.mappaContoEconomicoPerReport(
					esitoFindContoEconomico.getContoEconomico(), false, true, false,
					esitoFindContoEconomico.getDataFineIstruttoria(), esitoFindContoEconomico.getInIstruttoria());

			for (ContoEconomicoItemDTO c : contoEconomicoItemList) {
				c.setImportoSpesaAmmessaRimodulazione(c.getImportoSpesaAmmessaUltima());
				logger.info("idVOce=" + c.getIdVoce() + ", delta=" + c.getDelta() + ", label=" + c.getLabel());
			}
			datiPerInvioPropostaRimodulazione.setTotaliModalitaDiAgevolazione(
					calcolaTotaliModalitaAgevolazione(datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione()));
			List<ModalitaAgevolazione> listModalita = contoEconomicoManager
					.mappaModalitaDiAgevolazionePerReport(datiPerInvioPropostaRimodulazione);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_MODALITA_AGEVOLAZIONE, listModalita);

			Date currentDate = new Date(System.currentTimeMillis());
			ProgettoBandoLineaVO progettoBandoLinea = getBandoLinea(
					datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());
			PropostaRimodulazioneReportDTO propostaRimodulazioneDTO = creaPropostaRimodulazioneReportDTO(
					progettoBandoLinea, idContoEconomico, esitoFindContoEconomico, datiPerInvioPropostaRimodulazione,
					idSoggetto, idSoggettoRappresentante, currentDate);

			// CREAZIONE REPORT

			byte bytesPdf[] = null;

			// new report

			// Progetto

			PbandiTProgettoVO progettoVO = progettoManager.getProgetto(idProgetto.longValue());
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGETTO, beanUtil.transform(progettoVO,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));

			popolaTemplateManager.setTipoModello(PopolaTemplateManager.MODELLO_PROPOSTA_DI_RIMODULAZIONE);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgetto.longValue());

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROPOSTA_RIMODULAZIONE_DTO,
					propostaRimodulazioneDTO);

			ContoEconomicoConStatoVO contoEconomicoLocalCopyBen = contoEconomicoManager
					.findContoEconomicoLocalCopyBen(idProgetto);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_CONTOECONOMICO_PROPOSTA_RIMOD,
					contoEconomicoLocalCopyBen);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_CONTOECONOMICO_COPY_BEN,
					contoEconomicoItemList);// PK

			BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
			filtroBeneficiario.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			filtroBeneficiario.setIdSoggetto(beanUtil.transform(idSoggetto, BigDecimal.class));

			BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_BENEFICIARIO, beanUtil
					.transform(beneficiarioVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));

			String notecontoEconomico = datiPerInvioPropostaRimodulazione.getNoteContoEconomico();
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NOTE_CONTO_ECONOMICO, notecontoEconomico);

			// Rappresentante legale

			RappresentanteLegaleDTO rappresentanteLegaleDTO = rappresentanteLegaleManager
					.findRappresentanteLegale(idProgetto.longValue(), idSoggettoRappresentante); // }L{ PBANDI-2029
			// mancava
			// idSoggettoRappresentante

			if (datiPerInvioPropostaRimodulazione.getIdDelegato() != null) {
				logger.info("il delegato non � NULL " + datiPerInvioPropostaRimodulazione.getIdDelegato()
						+ ", lo metto al posto del rapp legale");
				DelegatoVO delegatoVO = new DelegatoVO();
				delegatoVO.setIdSoggetto(datiPerInvioPropostaRimodulazione.getIdDelegato());
				delegatoVO.setIdProgetto(idProgetto.longValue());
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

			SedeVO sedeIntervento = null;
			try {
				sedeIntervento = sedeManager.findSedeIntervento(idProgetto.longValue(), idSoggetto.longValue());
			} catch (Exception e) {
				DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
						"Errore durante la ricerca della sede di intervento");
				throw dse;
			}

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,
					beanUtil.transform(sedeIntervento, SedeDTO.class));

			ProgettoDTO progettoDTO = new ProgettoDTO();
			progettoDTO.setIdProgetto(idProgetto.longValue());
			List<ProceduraAggiudicazioneDTO> proceduraVO = getProcedureAggiudicazione(progettoDTO.getIdProgetto());
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROCEDURA_AGGIUDICAZIONE, proceduraVO);


			//PK : valorizzo le voci degli allegati da far vedere in stampa

			BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, "PR");
			logger.info("idTipoDocumentoIndex=" + idTipoDocumentoIndex);

			AllegatiMacroSezioneStampaRimodulazCEVO filtroAll = new AllegatiMacroSezioneStampaRimodulazCEVO();
			filtroAll.setIdProgetto(idProgetto);
			filtroAll.setProgrBandoLineaIntervento(new BigDecimal(progettoBandoLinea.getIdBandoLinea()));
			filtroAll.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			filtroAll.setFlagAllegato("S");

			List<AllegatiMacroSezioneStampaRimodulazCEVO> listaAll = genericDAO.findListWhere(filtroAll);

			// trasformo listaAll in listTipoAllegati e lo setto in listTipoAllegati
			List<TipoAllegatoDTO> listTipoAllegati = null;
			if (listaAll != null && listaAll.size() > 0) {
				listTipoAllegati = new ArrayList<TipoAllegatoDTO>();
				for (AllegatiMacroSezioneStampaRimodulazCEVO amssr : listaAll) {
					logger.info("amssr=" + amssr);

					TipoAllegatoDTO ti = new TipoAllegatoDTO();
					ti.setDescTipoAllegato(amssr.getDescTipoAllegato());
					ti.setFlagAllegato(amssr.getFlagAllegato());
					//ti.setIdDichiarazioneSpesa(amssr.getI);
					ti.setIdMicroSezioneModulo(amssr.getIdMicroSezioneModulo().longValue());
					ti.setIdProgetto(amssr.getIdProgetto().longValue());
					ti.setIdTipoDocumentoIndex(amssr.getIdTipoDocumentoIndex().longValue());
					ti.setNumOrdinamentoMicroSezione(amssr.getNumOrdinamentoMicroSezione().longValue());
					ti.setProgrBandoLineaIntervento(amssr.getProgrBandoLineaIntervento().longValue());
					listTipoAllegati.add(ti);
				}
			}
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI, listTipoAllegati);

			if (listTipoAllegati != null) {
				logger.info("popolaTemplateManager.addChiave aggiunta CHIAVE_ALLEGATI non nulla, dimensione=" + listTipoAllegati.size());
			} else {
				logger.info("popolaTemplateManager.addChiave aggiunta CHIAVE_ALLEGATI nulla");
			}


			// 11/12/15 added footer
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,
					"" + propostaRimodulazioneDTO.getIdContoEconomico());

			Modello modello = popolaTemplateManager.popolaModello(idProgetto.longValue());


			// SERVE JASPERREPORT 4 1 1 .JAR
			long startFillReport = System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(),
					modello.getMasterParameters(), new JREmptyDataSource());
			logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in "
					+ (System.currentTimeMillis() - startFillReport) + " ms\n");

			long startExport = System.currentTimeMillis();
			bytesPdf = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("\n\n\n########################\nJasperPrint esportato to pdf in "
					+ (System.currentTimeMillis() - startExport) + " ms\n");


			//PK TODO eliminare
			// stapo il pdf e lancio eccezione per non sputtanare il caso prova
//			Path path = Paths.get("C:\\tmp\\rimodulazioneCE.pdf");
//			Files.write(path, bytesPdf);
//
//			if(true) {
//				throw new ContoEconomicoException(
//					"ERRORE GRAVE FITTIZIO PK : report PROPOSTA RIMODULAZIONE salvato in locale");
//			}
			// PK

			if (bytesPdf == null) {
				logger.warn("\n\n\n\nERRORE GRAVE : report PROPOSTA RIMODULAZIONE non creato ,bytes==null");
				throw new ContoEconomicoException(
						"ERRORE GRAVE : report PROPOSTA RIMODULAZIONE non creato ,bytes==null");
			}

			// CREAZIONE NODO INDEX

			propostaRimodulazioneDTO.setBytes(bytesPdf);

			// if (1<2)
			// throw new Exception("ECCEZIONE FINTA !!!!!!");

			// DISMISSIONE INDEX !!!!!!!!!!!!!!
			// nodoIndex =
			// indexDAO.creaContentPropostaDiRimodulazione(propostaRimodulazioneDTO,
			// idUtente);
			// DISMISSIONE INDEX !!!!!!!!!!!!!!

			Node nodoIndex = new Node();

			// ID CONTO ECONOMICO DEV'ESSERE QUELLO DELLA LOCAL COPY

			String shaHex = null;
			if (bytesPdf != null)
				shaHex = DigestUtils.shaHex(bytesPdf);

			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO;
			pbandiTDocumentoIndexVO = documentoManager.salvaInfoNodoIndexSuDbSenzaInsert(idUtente, nodoIndex,
					propostaRimodulazioneDTO.getNomeFile(), idContoEconomico, idSoggettoRappresentante,
					datiPerInvioPropostaRimodulazione.getIdDelegato(),
					datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto(),
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE,
					PbandiTContoEconomicoVO.class, null, shaHex);

			// **********************************************************************************
			// AGGIUNTA PER SALVATAGGIO SU FILESYSTEM.
			pbandiTDocumentoIndexVO
					.setRepository(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE);
			this.salvaFileSuFileSystem(bytesPdf, pbandiTDocumentoIndexVO);
			// **********************************************************************************

			if (pbandiTDocumentoIndexVO.getIdDocumentoIndex() != null)
				idDocIndexPerRollBack = pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue();

			boolean isBrDemat = regolaManager.isRegolaApplicabileForBandoLinea(progettoBandoLinea.getIdBandoLinea(),
					RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
			if (isBrDemat)
				associateAllegati(BigDecimal.valueOf(idContoEconomico), idProgetto);

			logger.warn("\n\n############################ NEOFLUX UNLOCK PROP_RIM_CE ##############################\n");
			neofluxBusinessImpl.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(), Task.PROP_RIM_CE);
			logger.warn(
					"############################ NEOFLUX UNLOCK PROP_RIM_CE ##############################\n\n\n\n");

			List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica = Notification.NOTIFICA_PROPOSTA_RIMODULAZIONE;
			MetaDataVO metadata1 = new MetaDataVO();
			metadata1.setNome(Notification.DATA_INVIO_PROPOSTA);
			metadata1.setValore(DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);

			logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);

			logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
			genericDAO.callProcedure().sendNotificationMessage(idProgetto, descrBreveTemplateNotifica,
					Notification.ISTRUTTORE, idUtente);
			logger.info("calling genericDAO.callProcedure().sendNotificationMessage ok");

			EsitoInvioPropostaRimodulazioneDTO e = new EsitoInvioPropostaRimodulazioneDTO();

			e.setIdDocIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			e.setFileName(propostaRimodulazioneDTO.getNomeFile());

			e.setIdContoEconomicoLocal(idContoEconomico);
			e.setSuccesso(true);

			logger.end();
			return e;

		} catch (Exception e) {
			logger.info(
					"ROLLBACK APPLICATIVO: cancello nodo appena creato su index perche' c'e' stato un errore successivo");
			try {

				// DISMISSIONE INDEX !!!!!!!!!!!!!!
				// indexDAO.cancellaNodo(nodoIndex);
				// DISMISSIONE INDEX !!!!!!!!!!!!!!

				if (idDocIndexPerRollBack != null) {
					try {
						logger.info("Cancello pdf proposta di rimodulazione.");
						BigDecimal bd = new BigDecimal(idDocIndexPerRollBack);
						this.cancellaFileSuFileSystem(bd);
					} catch (Exception ex) {
					}
				}

			} catch (Exception e1) {
				logger.info("ATTENZIONE: ERRORE NEL CANCELLARE nodo appena creato su index " + e1.getMessage());
				RuntimeException re = new RuntimeException("Documento non cancellato su servizio INDEX");
				re.initCause(e);
				throw re;
			}
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private void salvaFileSuFileSystem(byte[] file,
																		 it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO vo) throws Exception {
		logger.info("salvaFileSuFileSystem(): file.length = " + file.length + "; NomeFile = " + vo.getNomeFile());

		// Trasformo PbandiTDocumentoIndexVO di un DocumentoManager
		// nel PbandiTDocumentoIndexVO di un altro DocumentoManager (sigh)
		it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO newVO = beanUtil.transform(vo,
				it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO.class);

		InputStream is = new ByteArrayInputStream(file);
		boolean esitoSalva = true;
		esitoSalva = documentoManagerNuovaVersione.salvaFile(file, newVO);

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

	private void associateAllegati(BigDecimal idContoEconomico, BigDecimal idProgetto) {

		logger.info("\n\n\nassociating allegati to idContoEconomico " + idContoEconomico + " ,idProgetto ");
		pbandiArchivioFileDAOImpl.associateAllegatiToContoEconomico(idContoEconomico, idProgetto);

	}

	private void aggiornaModalitaPropostaRimodulazione(Long idUtente,
																										 ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione, BigDecimal idProgetto)
			throws Exception, ContoEconomicoNonTrovatoException {
		aggiornaModalita(idUtente, modalitaDiAgevolazione, idProgetto);
	}

	private void aggiornaModalita(Long idUtente, ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione,
																BigDecimal idProgetto) throws Exception {

		Map<?, ModalitaDiAgevolazioneContoEconomicoVO> indexModalitaPresenti = getBeanUtil().index(contoEconomicoManager
						.caricaModalitaAgevolazione(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER),
				"idModalitaAgevolazione");

		BigDecimal idContoEconomico = contoEconomicoManager.getIdContoMaster(idProgetto);

		Map<String, String> map = new HashMap<String, String>();
		map.put("idContoEconomico", "idContoEconomico");
		map.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
		map.put("importoRichiestoNuovo", "quotaImportoRichiesto");
		map.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : modalitaDiAgevolazione) {
			ModalitaDiAgevolazioneContoEconomicoVO modalitaPrecedente = indexModalitaPresenti
					.get(new BigDecimal(modalitaDiAgevolazioneDTO.getIdModalitaDiAgevolazione()));
			if (modalitaPrecedente.getFlagLvlprj()
					.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {

				// insert del valore gi� esistente della modalit� associata al master

				PbandiRContoEconomModAgevVO modAgevVO = getBeanUtil().transform(modalitaPrecedente,
						PbandiRContoEconomModAgevVO.class);
				getBeanUtil().copyNotNullValues(modalitaDiAgevolazioneDTO, modAgevVO, map);
				modAgevVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(modAgevVO);

			} else if (NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo(), 0d) > 0
					|| NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), 0d) > 0) {
				PbandiRContoEconomModAgevVO nuovaModalita = new PbandiRContoEconomModAgevVO();
				nuovaModalita.setIdContoEconomico(idContoEconomico);

				Map<String, String> mapDTOtoVO = new HashMap<String, String>();
				mapDTOtoVO.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
				mapDTOtoVO.put("importoRichiestoNuovo", "quotaImportoRichiesto");
				mapDTOtoVO.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

				getBeanUtil().valueCopy(modalitaDiAgevolazioneDTO, nuovaModalita, mapDTOtoVO);

				nuovaModalita.setIdUtenteIns(new BigDecimal(idUtente));
				nuovaModalita.setPercImportoAgevolato(new BigDecimal(0));

				genericDAO.insert(nuovaModalita);
			}
		}
	}

	private PropostaRimodulazioneReportDTO creaPropostaRimodulazioneReportDTO(ProgettoBandoLineaVO progettoBandoLinea,
																																						Long idContoEconomico, EsitoFindContoEconomicoDTO esitoFindContoEconomico,
																																						DatiPerInvioPropostaRimodulazioneDTO datiPerInvioPropostaRimodulazione, Long idSoggetto,
																																						Long idSoggettoRappresentante, Date currentDate) {

		PropostaRimodulazioneReportDTO propostaRimodulazioneDTO = new PropostaRimodulazioneReportDTO();

		EnteAppartenenzaDTO enteAppartenenza = findEnteAppartenenza(
				datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto(),
				Constants.CODICE_RUOLO_ENTE_DESTINATARIO);

		RappresentanteLegale rappresentanteLegale = getRappresentanteLegale(
				datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto(), idSoggettoRappresentante);

		BeneficiarioVO beneficiarioVO = pbandiDichiarazioneDiSpesaDAO.findBeneficiario(idSoggetto,
				datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

		String nomeFile = creaNomeFileProposta(idContoEconomico, currentDate);

		String note = datiPerInvioPropostaRimodulazione.getNoteContoEconomico();
		if (ObjectUtil.isEmpty(note))
			note = "Nessuna";

		// MAPPING
		propostaRimodulazioneDTO.setBeneficiario(beneficiarioVO.getDenominazioneBeneficiario());
		propostaRimodulazioneDTO.setCodiceProgettoVisualizzato(progettoBandoLinea.getCodiceVisualizzato());
		propostaRimodulazioneDTO.setDataInvio(esitoFindContoEconomico.getDataUltimaProposta());
		propostaRimodulazioneDTO.setDescBandoLineaIntervento(progettoBandoLinea.getDescrizioneBando());
		propostaRimodulazioneDTO.setEnteAppartenenza(enteAppartenenza);
		propostaRimodulazioneDTO.setIdContoEconomico(idContoEconomico);
		propostaRimodulazioneDTO.setIdProgetto(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());
		propostaRimodulazioneDTO.setNoteContoEconomico(note);
		propostaRimodulazioneDTO.setNomeFile(nomeFile);
		propostaRimodulazioneDTO.setRappresentanteLegale(rappresentanteLegale);
		propostaRimodulazioneDTO.setTitoloProgetto(progettoBandoLinea.getTitoloProgetto());

		// VN: Verifico se la proposta di rimodulazione e' relativa ad un ribasso d'asta

		if (datiPerInvioPropostaRimodulazione.getIdProceduraAggiudicazione() != null) {
			PbandiTRibassoAstaVO ribassoVO = new PbandiTRibassoAstaVO();
			ribassoVO.setIdContoEconomico(NumberUtil.toBigDecimal(idContoEconomico));
			ribassoVO = genericDAO.findSingleWhere(ribassoVO);
			propostaRimodulazioneDTO.setPercRibassoAsta(NumberUtil.toDouble(ribassoVO.getPercentuale()));

			// VN. Fix PBandi-1462.

			PbandiTProceduraAggiudicazVO proceduraVO = new PbandiTProceduraAggiudicazVO();
			proceduraVO.setIdProceduraAggiudicaz(
					NumberUtil.toBigDecimal(datiPerInvioPropostaRimodulazione.getIdProceduraAggiudicazione()));
			proceduraVO = genericDAO.findSingleWhere(proceduraVO);

			ProceduraAggiudicazioneDTO proceduraDTO = beanUtil.transform(proceduraVO, ProceduraAggiudicazioneDTO.class);

			propostaRimodulazioneDTO.setProceduraAggiudicazione(proceduraDTO);
		}

		return propostaRimodulazioneDTO;
	}

	private RappresentanteLegale getRappresentanteLegale(Long idProgetto, Long idSoggettoRappresentante) {
		List<RappresentanteLegaleDTO> rappLegali = soggettoManager.findRappresentantiLegali(idProgetto,
				idSoggettoRappresentante);
		return beanUtil.transform(rappLegali.get(0), RappresentanteLegale.class);
	}

	private String creaNomeFileProposta(Long idContoEconomico, Date currentDate) {
		String nomeFile;
		nomeFile = "PropostaDiRimodulazione_" + idContoEconomico + "_"
				+ DateUtil.getTime(currentDate, TIME_FORMAT_PER_NOME_FILE) + ".pdf";
		logger.info("nomeFile del file della proposta : " + nomeFile);
		return nomeFile;
	}

	private ProgettoBandoLineaVO getBandoLinea(Long idProgetto) {
		ProgettoBandoLineaVO progettoBandoLineaVO = new ProgettoBandoLineaVO();
		progettoBandoLineaVO.setIdProgetto(new BigDecimal(idProgetto));
		ProgettoBandoLineaVO progettoBandoLinea = genericDAO.findSingleWhere(progettoBandoLineaVO);
		return progettoBandoLinea;
	}

	private EnteAppartenenzaDTO findEnteAppartenenza(Long idProgetto, String codiceTipoRuoloEnte) {
		EnteAppartenenzaVO enteVO = pbandiDichiarazioneDiSpesaDAO.findEnteAppartenenza(idProgetto, codiceTipoRuoloEnte);
		return getBeanUtil().transform(enteVO, EnteAppartenenzaDTO.class);
	}

	private List<ProceduraAggiudicazioneDTO> getProcedureAggiudicazione(Long idProgetto) {

		PbandiDLineaDiInterventoVO bandoLinea = progettoManager.getLineaDiInterventoNormativa(idProgetto);

		if (bandoLinea.getIdLineaDiIntervento().equals(new BigDecimal(202))) {

			ProceduraAggiudicazioneProgetto1420VO filtroVO = new ProceduraAggiudicazioneProgetto1420VO();
			filtroVO.setIdProgetto(new BigDecimal(idProgetto));

			List<ProceduraAggiudicazioneProgetto1420VO> result = genericDAO.findListWhere(filtroVO);

			return beanUtil.transformList(result, ProceduraAggiudicazioneDTO.class);

		} else {

			ProceduraAggiudicazioneProgettoVO filtroVO = new ProceduraAggiudicazioneProgettoVO();
			filtroVO.setIdProgetto(new BigDecimal(idProgetto));

			List<ProceduraAggiudicazioneProgettoVO> result = genericDAO.findListWhere(filtroVO);

			return beanUtil.transformList(result, ProceduraAggiudicazioneDTO.class);

		}
	}

	public EsitoFindContoEconomicoDTO findContoEconomicoPerRimodulazione(Long idUtente, String identitaDigitale,
																																			 ProgettoDTO progetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

		ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO = null;

		try {
			// la rimodulazione prevede che si lavori su 3 possibili tipologie di conto
			// economico
			// - MAIN - MASTER - COPY_IST
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;
			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			// TNT modificato per istruttoria con master non presente
			caricaContiEconomici(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti,
					tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

			// TNT modificato per istruttoria con master non presente
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			// TNT modificare per istruttoria ,eliminato riferimento a master nei nomi
			// variabili.
			contoEconomicoRimodulazioneDTO = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMaster);

			EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoDTO();
			esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoRimodulazioneDTO);
			esitoFindContoEconomicoDTO.setLocked(isLockedByBeneficiario(contoMaster));
			esitoFindContoEconomicoDTO.setCopiaModificataPresente(mappaConti.get(tipologiaContoEconomicoCopy) != null);
			esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(
					progetto.getIdProgetto(), RegoleConstants.BR12_RIMODULAZIONE_DISPONIBILE));

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			if (contoMain != null) {
				if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA
						.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
					esitoFindContoEconomicoDTO.setInIstruttoria(true);
			}

			ContoEconomicoMaxDataFineVO datiUltimaProposta = getDatiUltimaProposta(progetto);
			Date dataUltimaProposta = null;

			if (datiUltimaProposta != null) {
				dataUltimaProposta = datiUltimaProposta.getDtFineValidita();
				// VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima proposta
				if (esitoFindContoEconomicoDTO != null) {
					if (datiUltimaProposta.getPercRibassoAsta() != null) {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
						esitoFindContoEconomicoDTO.setImportoUltimoRibassoAstaInProposta(
								NumberUtil.toDouble(datiUltimaProposta.getImportoRibassoAsta()));
						esitoFindContoEconomicoDTO.setPercUltimoRibassoAstaInProposta(
								NumberUtil.toDouble(datiUltimaProposta.getPercRibassoAsta()));
					} else {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
					}
				}
			}

			ContoEconomicoMaxDataFineVO datiUltimaRimodulazione = getDatiUltimaRimodulazione(progetto);
			Date dataUltimaRimodulazione = null;
			if (datiUltimaRimodulazione != null) {
				dataUltimaRimodulazione = datiUltimaRimodulazione.getDtFineValidita();
				// VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima
				// rimodulazione
				if (esitoFindContoEconomicoDTO != null) {
					if (datiUltimaRimodulazione.getPercRibassoAsta() != null) {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
					} else {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
								it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
					}
				}
			}

			esitoFindContoEconomicoDTO.setDataUltimaProposta(dataUltimaProposta);

			esitoFindContoEconomicoDTO.setDataUltimaRimodulazione(dataUltimaRimodulazione);

			esitoFindContoEconomicoDTO
					.setDataFineIstruttoria(mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN).getDtFineValidita());

			esitoFindContoEconomicoDTO
					.setDataPresentazioneDomanda(mapDataPresentazioneDomanda.get(DATA_PRESENTAZIONE_DOMANDA));

			return esitoFindContoEconomicoDTO;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	public EsitoRimodulazioneDTO rimodula(Long idUtente, String identitaDigitale,
																				ContoEconomicoRimodulazioneDTO contoEconomico)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "contoEconomico",
				"contoEconomico.idContoEconomico"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, contoEconomico,
				contoEconomico.getIdContoEconomico());

		try {
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;
			Map<String, String> map = new HashMap<String, String>();
			map.put("importoSpesaAmmessaRimodulazione", "importoAmmesso");
			map.put("importoRichiestoUltimaProposta", "importoRichiesto");

			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(
					contoEconomicoManager.findIdProgetto(new BigDecimal(contoEconomico.getIdContoEconomico())),
					listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			boolean locked = isLockedByBeneficiario(contoMaster);

			EsitoRimodulazioneDTO esitoRimodulazioneDTO = new EsitoRimodulazioneDTO();
			esitoRimodulazioneDTO.setLocked(locked);
			// TODO solo storicizza
			esitoRimodulazioneDTO.setContoEconomico(copiaEStoricizza(idUtente, contoEconomico, listaTipi, mappaConti,
					tipologiaContoEconomicoCopy, map, contoMaster, locked));

			return esitoRimodulazioneDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private boolean isLockedByBeneficiario(
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster) {
		return contoMaster != null && contoMaster.getDescBreveStatoContoEconom()
				.equals(Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA);
	}

	public DatiPerConclusioneRimodulazioneDTO caricaDatiPerConclusioneRimodulazione(Long idUtente,
																																									String identitaDigitale, ProgettoDTO progetto, Long idBandoLinea)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		logger.begin();
		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto,
					progetto.getIdProgetto());

			logger.info(" idProgetto: " + progetto.getIdProgetto());

			DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO = new DatiPerConclusioneRimodulazioneDTO();

			BigDecimal idProgetto = new BigDecimal(progetto.getIdProgetto());

			PbandiTProgettoVO pbandiTProgettoVO = progettoManager.getProgetto(progetto.getIdProgetto());

			datiPerConclusioneRimodulazioneDTO
					.setTipoOperazione("" + pbandiTProgettoVO.getIdTipoOperazione().intValue());

			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici = contoEconomicoManager
					.findContiEconomici(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST);

			logger.info(" contiEconomici: " + contiEconomici);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoCopy = contiEconomici
					.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST);

			logger.info(" contoCopy: " + contoCopy);

			if (contoCopy == null) {
				logger.error("Errore fatal! conto economico copy istr non esiste per idProgetto: "
						+ progetto.getIdProgetto());
				throw new Exception("Errore fatal: conto economico copy istr non esiste per idProgetto: "
						+ progetto.getIdProgetto());
			}

			// il nuovo valore dalla LOCAL COPY
			Double importoSpesaAmmessa = NumberUtil.toDouble(contoCopy.getImportoAmmesso());
			logger.info(" importoSpesaAmmessa: " + importoSpesaAmmessa);

			// Imposto i totali per popolare gli impori sulle percentuali
			// il vecchio valore dal MASTER
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = contiEconomici
					.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			logger.info(" contoMaster: " + contoMaster);

			Double importoSpesaAmmessaUltima = 0D;
			if (contoMaster != null) {
				importoSpesaAmmessaUltima = NumberUtil.toDouble(contoMaster.getImportoAmmesso());
			}
			logger.info(" importoSpesaAmmessaUltima: " + importoSpesaAmmessaUltima);

			setImportoVincolante(datiPerConclusioneRimodulazioneDTO, contoMaster);

			ModalitaDiAgevolazioneDTO totaleModalitaAgevolazione = setModalitaDiAgevolazione(progetto,
					datiPerConclusioneRimodulazioneDTO, importoSpesaAmmessa, importoSpesaAmmessaUltima);

			logger.shallowDump(totaleModalitaAgevolazione, "debug");
			logger.info(" totaleModalitaAgevolazione: " + totaleModalitaAgevolazione);

			// ###############################################################################################
			// FONTI FINAZIARIE

			calcolaFontiFinanziarie(datiPerConclusioneRimodulazioneDTO, idProgetto, idBandoLinea, importoSpesaAmmessa,
					importoSpesaAmmessaUltima, totaleModalitaAgevolazione.getPercImportoAgevolatoNuovo(),
					totaleModalitaAgevolazione.getImportoAgevolatoNuovo());

			// FONTI FINAZIARIE
			// ###############################################################################################

			datiPerConclusioneRimodulazioneDTO.setImportoSpesaAmmessa(importoSpesaAmmessa);

			datiPerConclusioneRimodulazioneDTO.setProgetto(progetto);

			setImportoFinanziamentoBanca(datiPerConclusioneRimodulazioneDTO, contiEconomici, contoMaster);

			setDataConcessione(datiPerConclusioneRimodulazioneDTO, pbandiTProgettoVO);

			setProceduraDiAggiudicazione(progetto, datiPerConclusioneRimodulazioneDTO);

			datiPerConclusioneRimodulazioneDTO.setImportoSpesaAmmessaUltima(importoSpesaAmmessaUltima);

			Boolean isPeriodoUnico = isPeriodoUnico(datiPerConclusioneRimodulazioneDTO.getFontiFinanziarieNonPrivate());
			datiPerConclusioneRimodulazioneDTO.setIsPeriodoUnico(isPeriodoUnico);

			StringBuilder sb = new StringBuilder();
			sb.append("Totale Spesa ammessa rimodulazione: " + importoSpesaAmmessaUltima);
			sb.append("\nImporto spesa ammessa: " + datiPerConclusioneRimodulazioneDTO.getImportoSpesaAmmessa());
			sb.append("\nPeriodo unico: " + datiPerConclusioneRimodulazioneDTO.getIsPeriodoUnico());

			if (datiPerConclusioneRimodulazioneDTO.getFontiFinanziarieNonPrivate() != null)
				for (FontiFinanziarieDTO fonte : datiPerConclusioneRimodulazioneDTO.getFontiFinanziarieNonPrivate()) {
					sb.append("\nFonte: " + fonte.getDescrizione() + " - importo: "
							+ fonte.getQuotaFonteFinanziariaNuova() + " - perc: "
							+ fonte.getPercQuotaFonteFinanziariaNuova() + " - perc default: "
							+ fonte.getPercQuotaDefault());
				}
			if (datiPerConclusioneRimodulazioneDTO.getFontiFinanziariePrivate() != null)
				for (FontiFinanziarieDTO fonte : datiPerConclusioneRimodulazioneDTO.getFontiFinanziariePrivate()) {
					sb.append("\nFonte: " + fonte.getDescrizione() + " - importo: "
							+ fonte.getQuotaFonteFinanziariaNuova() + " - perc: "
							+ fonte.getPercQuotaFonteFinanziariaNuova() + " - perc default: "
							+ fonte.getPercQuotaDefault());
				}

			logger.info(sb.toString() + "\n\n\n");
			return datiPerConclusioneRimodulazioneDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} finally {
			logger.end();
		}
	}

	private FontiFinanziarieDTO[] getFonti(BigDecimal idProgetto, BigDecimal idBandoLinea,
																				 Double importoNuovaSpesaAmmessa, Map<String, String> mapFin, String flagAgevolato) {
		FontiFinanziarieDTO[] fonti = getBeanUtil().transform(
				contoEconomicoManager.getSoggettiFinanziatori(idProgetto, flagAgevolato), FontiFinanziarieDTO.class,
				mapFin);
		if (fonti != null) {
			logger.debug("trovate " + fonti.length + "fonti");
		} else {
			logger.warn("fonti NULL");
		}
		for (FontiFinanziarieDTO fonte : fonti) {
			if (fonte.getPercQuotaFonteFinanziariaUltima() != null && fonte.getQuotaFonteFinanziariaUltima() == null) {
				if (fonte.getPercQuotaFonteFinanziariaUltima() == 0) {
					fonte.setQuotaFonteFinanziariaUltima(0d);
				}
				Double importo = NumberUtil.toDouble(
						NumberUtil.percentageOf(fonte.getPercQuotaFonteFinanziariaUltima(), importoNuovaSpesaAmmessa));
				fonte.setQuotaFonteFinanziariaNuova(importo);
				fonte.setPercQuotaFonteFinanziariaNuova(fonte.getPercQuotaFonteFinanziariaUltima());
			}
		}

		List<SoggettiFinanziatoriPerBandoVO> soggettiFinanziatoriPerBandoVOs = contoEconomicoManager
				.getSoggettiFinanziatoriBando(idBandoLinea, flagAgevolato);
		if (soggettiFinanziatoriPerBandoVOs != null && soggettiFinanziatoriPerBandoVOs.size() > 0) {
			List<FontiFinanziarieDTO> fontiFinanziarieDTOs = new ArrayList<FontiFinanziarieDTO>(Arrays.asList(fonti));
			for (SoggettiFinanziatoriPerBandoVO vo : soggettiFinanziatoriPerBandoVOs) {
				boolean found = false;
				for (FontiFinanziarieDTO fonte : fonti) {
					if (fonte.getIdFonteFinanziaria().equals(new Long(vo.getIdSoggettoFinanziatore().longValue()))) {
						found = true;
					}
				}
				if (!found) {
					FontiFinanziarieDTO dto = new FontiFinanziarieDTO();
					dto.setIdFonteFinanziaria(new Long(vo.getIdSoggettoFinanziatore().longValue()));
					dto.setDescBreveTipoSoggFinanziat(vo.getDescBreveSoggFinanziatore());
					dto.setDescrizione(vo.getDescSoggFinanziatore());
					dto.setFlagCertificazione(vo.getFlagCertificazione());
					dto.setPercQuotaDefault(0D);
					dto.setQuotaFonteFinanziariaNuova(0D);
					dto.setPercQuotaFonteFinanziariaNuova(0D);
					dto.setFlagLvlprj("N");
					if (fonti != null && fonti.length > 0) {
						dto.setIdPeriodo(fonti[0].getIdPeriodo());
						dto.setDescPeriodo(fonti[0].getDescPeriodo());
						dto.setDescPeriodoVisualizzata(fonti[0].getDescPeriodoVisualizzata());
					}
					fontiFinanziarieDTOs.add(dto);
				}
			}
			return fontiFinanziarieDTOs.toArray(new FontiFinanziarieDTO[0]);
		}
		return fonti;
	}

	private Boolean isPeriodoUnico(FontiFinanziarieDTO[] fontiFinanziarieNonPrivate) {
		boolean ret = Boolean.FALSE;
		if (fontiFinanziarieNonPrivate != null) {
			for (FontiFinanziarieDTO fonte : fontiFinanziarieNonPrivate) {
				if (fonte.getDescPeriodo() != null && fonte.getDescPeriodo().equals(PERIODO_UNICO)) {
					fonte.setIsPeriodoUnico(Boolean.TRUE);
					ret = Boolean.TRUE;
				}
			}
		}
		return ret;
	}

	private ProceduraAggiudicazioneProgettoVO getProceduraAggiudicazione(ProgettoDTO progetto) {

		ProceduraAggiudicazioneProgettoVO proceduraVO = null;

		// jira 1971 MODIFICA per procedura di aggiudicazione non deve prendere una o
		// l'altra,ma la + nuova

		// ContoEconomicoMaxDataFineVO datiUltimaProposta =
		// getDatiUltimaProposta(progetto);
		List<String> stati = new ArrayList<String>();
		stati.add(Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA);
		stati.add(Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA);
		ContoEconomicoMaxDataFineVO datiUltimoContoEconomico = getDatiContoEconomicoByStati(progetto, stati);

		if (datiUltimoContoEconomico != null && datiUltimoContoEconomico.getIdProceduraAggiudicaz() != null) {
			proceduraVO = new ProceduraAggiudicazioneProgettoVO();
			proceduraVO.setIdProceduraAggiudicaz(datiUltimoContoEconomico.getIdProceduraAggiudicaz());
			proceduraVO = genericDAO.findSingleWhere(proceduraVO);
		}
		return proceduraVO;
	}

	private ContoEconomicoMaxDataFineVO getDatiContoEconomicoByStati(ProgettoDTO progetto, List<String> stati) {
		ContoEconomicoMaxDataFineVO contoEconomicoMaxDataFineVO = new ContoEconomicoMaxDataFineVO();
		contoEconomicoMaxDataFineVO.setIdProgetto(NumberUtil.createScaledBigDecimal(progetto.getIdProgetto()));
		List<ContoEconomicoMaxDataFineVO> filtriStati = new ArrayList<ContoEconomicoMaxDataFineVO>();
		for (String stato : stati) {
			ContoEconomicoMaxDataFineVO vo = new ContoEconomicoMaxDataFineVO();
			vo.setDescBreveStatoContoEconom(stato);
			filtriStati.add(vo);
		}

		ContoEconomicoMaxDataFineVO[] voz = genericDAOpbwebrce
				.findWhere(it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.Condition.filterBy(filtriStati)
						.and(it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.Condition
								.filterBy(contoEconomicoMaxDataFineVO)));

		if (!isEmpty(voz))
			return voz[0];
		else
			return null;
	}

	private void setDataConcessione(DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO,
																	PbandiTProgettoVO pbandiTProgettoVO) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (pbandiTProgettoVO.getDtConcessione() != null)
			datiPerConclusioneRimodulazioneDTO
					.setDataConcessione(dateFormat.format(pbandiTProgettoVO.getDtConcessione()));
	}

	private void calcolaFontiFinanziarie(DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO,
																			 BigDecimal idProgetto, Long idBandoLinea, Double importoNuovaSpesaAmmessa, // conto economico COPY_ISTR,
																			 // quello della rimod in corso
																			 Double importoUltimaSpesaAmmessa, Double percentualeTotaleModalitaNuovaAgevolazione,
																			 Double nuovoImportoAgevolato) {// conto economico MASTER ,quello dell'ultima rimod

		Map<String, String> mapFin = creaMapFonteFinanziaria();

		FontiFinanziarieDTO[] fontiNonAgevolate = getFonti(idProgetto, new BigDecimal(idBandoLinea),
				importoNuovaSpesaAmmessa, mapFin, "N");

		FontiFinanziarieDTO[] fontiAgevolate = getFonti(idProgetto, new BigDecimal(idBandoLinea),
				importoNuovaSpesaAmmessa, mapFin, "S");
		// setto il periodo se manca
		if (fontiNonAgevolate != null && fontiNonAgevolate.length > 0 && fontiNonAgevolate[0].getIdPeriodo() == null) {
			for (FontiFinanziarieDTO f : fontiNonAgevolate) {
				f.setIdPeriodo(fontiNonAgevolate[0].getIdPeriodo());
				f.setDescPeriodo(fontiNonAgevolate[0].getDescPeriodo());
				f.setDescPeriodoVisualizzata(fontiNonAgevolate[0].getDescPeriodoVisualizzata());
			}
		} else if (fontiAgevolate != null && fontiAgevolate.length > 0 && fontiAgevolate[0].getIdPeriodo() == null) {
			for (FontiFinanziarieDTO f : fontiAgevolate) {
				f.setIdPeriodo(fontiAgevolate[0].getIdPeriodo());
				f.setDescPeriodo(fontiAgevolate[0].getDescPeriodo());
				f.setDescPeriodoVisualizzata(fontiAgevolate[0].getDescPeriodoVisualizzata());
			}
		}
		logger.info("\ncalcolaFontiFinanziarie(): importoNuovaSpesaAmmessa = " + importoNuovaSpesaAmmessa
				+ "; importoUltimaSpesaAmmessa = " + importoUltimaSpesaAmmessa);

		FontiFinanziarieDTO[] fontiSenzaTotali = ObjectUtil.arrayMerge(fontiAgevolate, fontiNonAgevolate);

		// in base a percentuale trovata sul db per quella fonte e importo globale nuova
		// spesa ammessa
		calcolaNuovaQuotaEPercentualeFonteFinanziaria(importoUltimaSpesaAmmessa, importoNuovaSpesaAmmessa,
				fontiSenzaTotali);

		FontiFinanziarieDTO totale = NumberUtil.accumulate(fontiSenzaTotali, "percQuotaFonteFinanziariaUltima", // devo
				// riaccumularli
				// senno
				// mi
				// setta
				// a
				// null
				"percQuotaFonteFinanziariaNuova", // i valori precedenti
				"quotaFonteFinanziariaUltima", "quotaFonteFinanziariaNuova");

		FontiFinanziarieDTO subTotale = NumberUtil.accumulate(fontiAgevolate, "percQuotaFonteFinanziariaUltima",
				"percQuotaFonteFinanziariaNuova", "quotaFonteFinanziariaUltima", "quotaFonteFinanziariaNuova");

		// }L{ PBANDI-XXX NumberUtil.accumulate restituisce null in caso di 0 fonti
		totale = totale == null ? new FontiFinanziarieDTO() : totale;
		subTotale = subTotale == null ? new FontiFinanziarieDTO() : subTotale;

		double percentualeSubTotale = zeroDoubleIfNull(subTotale.getPercQuotaFonteFinanziariaNuova());// B
		double percentualeTotale = zeroDoubleIfNull(totale.getPercQuotaFonteFinanziariaNuova());// C
		double importoSubTotale = zeroDoubleIfNull(subTotale.getQuotaFonteFinanziariaNuova());

		// A = percentuale totale modalit� agevolaz (me lo devo portare dietro)
		// B = percentuale fonti private
		// (subTotale.percQuotaFonteFinanziariaNuova) C = percentuale tutte le
		// fonti (totale.percQuotaFonteFinanziariaNuova)

		// calcolo subtotale

		// if(A=B && importoTotaleAgevolato !=
		// subTotale.quotaFonteFinanziariaNuova ){ sottraggo
		// importoTotaleAgevolato(cio� importoNuovaSpesaAmmessa)-somma(tutte le
		// fonti meno l'ultima significativa del subTotale) e lo metto come
		// importo dell'ultima fonte esclusa dalla somma (NB: la percentuale
		// rimane quela) }

		if (percentualeTotaleModalitaNuovaAgevolazione == percentualeSubTotale
				&& importoNuovaSpesaAmmessa != importoSubTotale) {

			int indiceUltimaFonteSignificativa = getIndiceUltimaFonteSignificativa(fontiAgevolate);

			double sommaFontiNonPrivate = getSommaFontiTranneUltimaSignificativa(fontiAgevolate,
					indiceUltimaFonteSignificativa);

			double ultimoImportoNonPrivato = NumberUtil.subtract(nuovoImportoAgevolato, sommaFontiNonPrivate);

			// }L{ prima non c'era controllo e se non c'erano fonti agevolate si rompeva
			// tutto
			if (fontiAgevolate.length > 0) {
				fontiAgevolate[indiceUltimaFonteSignificativa].setQuotaFonteFinanziariaNuova(ultimoImportoNonPrivato);
			}

			subTotale.setQuotaFonteFinanziariaNuova(nuovoImportoAgevolato);

			fontiSenzaTotali = ObjectUtil.arrayMerge(fontiAgevolate, fontiNonAgevolate);

			// ricalcolo il totale
			FontiFinanziarieDTO temp = NumberUtil.accumulate(fontiSenzaTotali, "quotaFonteFinanziariaNuova");
			totale.setQuotaFonteFinanziariaNuova(temp.getQuotaFonteFinanziariaNuova());
		}

		// calcolo totale nuova quota fonte finanziaria

		// if(C=100% and B!=C) sottraggo ammesso - somma (tutte le fonti tranne
		// l'ultima significativa) e lo metto come importo dell'ultima fonte
		// esclusa dalla somma (NB: la percentuale rimane quela)

		if (percentualeTotale == 100 && percentualeSubTotale != percentualeTotale) {
			int indiceUltimaFonteSignificativa = getIndiceUltimaFonteSignificativa(fontiSenzaTotali);

			double sommaTutteFontiTranneUltimaSignificativa = getSommaFontiTranneUltimaSignificativa(fontiSenzaTotali,
					indiceUltimaFonteSignificativa);
			double ultimoImporto = NumberUtil.subtract(importoNuovaSpesaAmmessa,
					sommaTutteFontiTranneUltimaSignificativa);

			fontiSenzaTotali[indiceUltimaFonteSignificativa].setQuotaFonteFinanziariaNuova(ultimoImporto);

			totale.setQuotaFonteFinanziariaNuova(importoNuovaSpesaAmmessa);

		}

		if (percentualeSubTotale == 100 && percentualeSubTotale != percentualeTotaleModalitaNuovaAgevolazione) {
			int indiceUltimaFonteSignificativa = getIndiceUltimaFonteSignificativa(fontiAgevolate);

			double sommaFontiNonPrivate = getSommaFontiTranneUltimaSignificativa(fontiAgevolate,
					indiceUltimaFonteSignificativa);

			double ultimoImportoNonPrivato = NumberUtil.subtract(importoNuovaSpesaAmmessa, sommaFontiNonPrivate);

			fontiAgevolate[indiceUltimaFonteSignificativa].setQuotaFonteFinanziariaNuova(ultimoImportoNonPrivato);

			subTotale.setQuotaFonteFinanziariaNuova(sommaFontiNonPrivate + ultimoImportoNonPrivato);

			fontiSenzaTotali = ObjectUtil.arrayMerge(fontiAgevolate, fontiNonAgevolate);
			// ricalcolo il totale
			FontiFinanziarieDTO temp = NumberUtil.accumulate(fontiSenzaTotali, "quotaFonteFinanziariaNuova");
			totale.setQuotaFonteFinanziariaNuova(temp.getQuotaFonteFinanziariaNuova());
		}

		datiPerConclusioneRimodulazioneDTO.setFontiFinanziarieNonPrivate(fontiAgevolate);
		datiPerConclusioneRimodulazioneDTO.setFontiFinanziariePrivate(fontiNonAgevolate);
		datiPerConclusioneRimodulazioneDTO.setSubTotaliFontiFinanziarieNonPrivate(subTotale);
		datiPerConclusioneRimodulazioneDTO.setTotaliFontiFinanziarie(totale);
	}

	private double getSommaFontiTranneUltimaSignificativa(FontiFinanziarieDTO[] fonti,
																												int indiceUltimaFonteSignificativa) {
		double sommaFontiNonPrivate = 0d;
		for (int i = 0; i < indiceUltimaFonteSignificativa; i++) {
			if (fonti[i].getQuotaFonteFinanziariaNuova() != null && fonti[i].getQuotaFonteFinanziariaNuova() > 0d) {
				sommaFontiNonPrivate = NumberUtil.sum(sommaFontiNonPrivate, fonti[i].getQuotaFonteFinanziariaNuova());
			}
		}
		return sommaFontiNonPrivate;
	}

	// Jira PBANDI-2696: esegue il ricalcolo dell'importo fonte solo se ultima e
	// nuova spesa ammessa sono diversi.
	private void calcolaNuovaQuotaEPercentualeFonteFinanziaria(Double importoUltimaSpesaAmmessa,
																														 Double importoNuovaSpesaAmmessa, FontiFinanziarieDTO[] arrayMerge) {
		for (FontiFinanziarieDTO fonte : arrayMerge) {
			if (fonte.getPercQuotaFonteFinanziariaUltima() != null && fonte.getPercQuotaFonteFinanziariaUltima() > 0) {

				if (importoNuovaSpesaAmmessa.compareTo(importoUltimaSpesaAmmessa) == 0) {
					// Importo precedente.
					fonte.setQuotaFonteFinanziariaNuova(fonte.getQuotaFonteFinanziariaUltima());
				} else {
					// Eseguo ricalcolo.
					BigDecimal importo = NumberUtil.percentageOf(fonte.getPercQuotaFonteFinanziariaUltima(),
							importoNuovaSpesaAmmessa);
					fonte.setQuotaFonteFinanziariaNuova(NumberUtil.toDouble(importo));
				}

				fonte.setPercQuotaFonteFinanziariaNuova(fonte.getPercQuotaFonteFinanziariaUltima());
			}
		}
	}

	private int getIndiceUltimaFonteSignificativa(FontiFinanziarieDTO[] fonti) {
		int indiceUltimaFonteSignificativa = 0;
		int indice = 0;
		for (FontiFinanziarieDTO fonte : fonti) {
			if (fonte.getQuotaFonteFinanziariaNuova() != null && fonte.getQuotaFonteFinanziariaNuova() > 0d) {
				indiceUltimaFonteSignificativa = indice;
			}
			indice++;
		}
		return indiceUltimaFonteSignificativa;
	}

	private void setProceduraDiAggiudicazione(ProgettoDTO progetto,
																						DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO) {

		// VN: Ricerco l' eventuale procedura di aggiudicazione associata al conto
		// economico del beneficiario.

		ProceduraAggiudicazioneProgettoVO proceduraVO = getProceduraAggiudicazione(progetto);
		if (proceduraVO != null) {
			datiPerConclusioneRimodulazioneDTO.setProceduraAggiudicazioneBeneficiario(
					beanUtil.transform(proceduraVO, ProceduraAggiudicazioneDTO.class));
		}
	}

	private double zeroDoubleIfNull(Double value) {
		return ObjectUtil.nvl(value, 0D);
	}

	private Map<String, String> creaMapFonteFinanziaria() {
		Map<String, String> mapFin = new LinkedHashMap<String, String>();
		mapFin.put("descBreveTipoSoggFinanziat", "descBreveTipoSoggFinanziat");
		mapFin.put("descPeriodo", "descPeriodo");
		mapFin.put("descPeriodoVisualizzata", "descPeriodoVisualizzata");
		mapFin.put("descSoggFinanziatore", "descrizione");
		mapFin.put("estremiProvv", "estremiProvv");
		mapFin.put("flagCertificazione", "flagCertificazione");
		mapFin.put("flagEconomie", "flagEconomie");
		mapFin.put("flagLvlprj", "flagLvlprj");
		mapFin.put("idComune", "idComune");
		mapFin.put("idDelibera", "idDelibera");
		mapFin.put("idNorma", "idNorma");
		mapFin.put("idPeriodo", "idPeriodo");
		mapFin.put("idProvincia", "idProvincia");
		mapFin.put("idSoggetto", "idSoggetto");
		mapFin.put("idSoggettoFinanziatore", "idFonteFinanziaria");
		mapFin.put("impQuotaSoggFinanziatore", "quotaFonteFinanziariaUltima");
		mapFin.put("note", "note");
		mapFin.put("percQuotaSoggFinanziatore", "percQuotaFonteFinanziariaUltima");
		mapFin.put("progrProgSoggFinanziat", "progrProgSoggFinanziat");
		mapFin.put("percQuotaDefault", "percQuotaDefault");
		return mapFin;
	}

	// 1)master 2) main
	private void setImportoFinanziamentoBanca(DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO,
																						Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici,
																						it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster) {

		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		if (contoMaster != null)
			contoEconomicoVO.setIdContoEconomico(contoMaster.getIdContoEconomico());
		contoEconomicoVO = genericDAO.findSingleWhere(contoEconomicoVO);
		BigDecimal importoFinanziamentoBanca = null;
		if (contoEconomicoVO.getImportoFinanziamentoBanca() != null) {
			importoFinanziamentoBanca = contoEconomicoVO.getImportoFinanziamentoBanca();
		}
		if (importoFinanziamentoBanca == null) {
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = contiEconomici
					.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			contoEconomicoVO.setIdContoEconomico(contoMain.getIdContoEconomico());
			contoEconomicoVO = genericDAO.findSingleWhere(Condition.filterByKeyOf(contoEconomicoVO));
			if (contoEconomicoVO.getImportoFinanziamentoBanca() != null) {
				importoFinanziamentoBanca = contoEconomicoVO.getImportoFinanziamentoBanca();
			}
		}
		datiPerConclusioneRimodulazioneDTO
				.setImportoFinanziamentoBancario(NumberUtil.toDouble(importoFinanziamentoBanca));
	}

	private void setImportoVincolante(DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO,
																		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster) {
		Double importoVincolante = 0D;
		if (contoMaster != null) {
			importoVincolante = NumberUtil.toDouble(contoMaster.getImportoImpegnoVincolante());
		}
		datiPerConclusioneRimodulazioneDTO.setImportoImpegnoVincolante(importoVincolante);
	}

	// Ex GestioneEconomieBusinessImpl.isProgettoRicevente()
	public Boolean isProgettoRicevente(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException {
		PbandiTEconomieVO vo = new PbandiTEconomieVO();
		vo.setIdProgettoRicevente(new BigDecimal(idProgetto));
		List<PbandiTEconomieVO> lista = genericDAO.findListWhere(vo);
		boolean esito = (lista.size() > 0);
		return esito;
	}

	// Ex GestioneEconomieBusinessImpl.findSommaEconomieUtilizzate()
	// Calcola la somma degli importi ceduti delle economie già utilizzate
	// (data_utilizzo valorizzata) e
	// aventi il progetto in input come progetto ricevente.
	public Double findSommaEconomieUtilizzate(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException {

		PbandiTEconomieVO vo = new PbandiTEconomieVO();
		vo.setIdProgettoRicevente(new BigDecimal(idProgetto));
		FilterCondition filter = new FilterCondition<PbandiTEconomieVO>(vo);
		NotCondition<PbandiTEconomieVO> dtUtilizzoValorizzata = new NotCondition<PbandiTEconomieVO>(
				new NullCondition<PbandiTEconomieVO>(PbandiTEconomieVO.class, "dataUtilizzo"));
		AndCondition andCond = new AndCondition<PbandiTEconomieVO>(filter, dtUtilizzoValorizzata);
		List<PbandiTEconomieVO> lista = genericDAO.findListWhere(andCond);

		Double somma = new Double(0.0);
		for (PbandiTEconomieVO e : lista) {
			somma = somma + e.getImportoCeduto();
		}
		return somma;
	}

	private ModalitaDiAgevolazioneDTO setModalitaDiAgevolazione(ProgettoDTO progetto,
																															DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO, Double importoSpesaAmmessaNuova,
																															Double importoSpesaAmmessaUltima) throws Exception {
		logger.begin();
		// carico le modalita di agevolazione
		ModalitaDiAgevolazioneDTO[] modalitaAgevolazione = caricaModalitaAgevolazionePerRimodulazione(
				progetto.getIdProgetto(), importoSpesaAmmessaUltima, importoSpesaAmmessaNuova,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);

		datiPerConclusioneRimodulazioneDTO.setModalitaDiAgevolazione(modalitaAgevolazione);
		ModalitaDiAgevolazioneDTO totaleModalitaAgevolazione = calcolaTotaliModalitaAgevolazione(modalitaAgevolazione);
		datiPerConclusioneRimodulazioneDTO.setTotaliModalitaDiAgevolazione(totaleModalitaAgevolazione);
		logger.end();
		return totaleModalitaAgevolazione;
	}

	private ModalitaDiAgevolazioneDTO[] caricaModalitaAgevolazionePerRimodulazione(double idProgetto,
																																								 Double importoSpesaAmmessaUltima, Double importoSpesaAmmessaNuova, String tipologiaContoEconomico)
			throws Exception {

		logger.begin();
		Map<String, String> map = new HashMap<String, String>();
		map.put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
		map.put("descModalitaAgevolazione", "descrizione");
		map.put("massimoImportoAgevolato", "importoMassimoAgevolato");
		map.put("importoErogazioni", "importoErogato");
		map.put("quotaImportoAgevolato", "importoAgevolatoNuovo");
		map.put("quotaImportoAgevolato", "importoAgevolatoUltimo");
		map.put("quotaImportoRichiesto", "importoRichiestoUltimo");
		map.put("percImportoAgevolatoBando", "percImportoMassimoAgevolato");

		List<ModalitaDiAgevolazioneDTO> list = getBeanUtil().transformList(
				contoEconomicoManager.caricaModalitaAgevolazione(new BigDecimal(idProgetto), tipologiaContoEconomico),
				ModalitaDiAgevolazioneDTO.class, map);

		if (list != null) {
			logger.debug("Trovata una List<ModalitaDiAgevolazioneDTO> di dimensione=" + list.size());
		}

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : list) {
			if (modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo() != null)
				modalitaDiAgevolazioneDTO
						.setImportoAgevolatoNuovo(modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo());
			else
				modalitaDiAgevolazioneDTO.setImportoAgevolatoNuovo(0d);

			modalitaDiAgevolazioneDTO.setPercImportoAgevolatoNuovo(NumberUtil.toDouble(NumberUtil
					.percentage(modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo(), importoSpesaAmmessaNuova)));
			modalitaDiAgevolazioneDTO.setPercImportoAgevolatoUltimo(NumberUtil.toDouble(NumberUtil
					.percentage(modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo(), importoSpesaAmmessaUltima)));
		}
		logger.end();
		return list.toArray(new ModalitaDiAgevolazioneDTO[list.size()]);
	}

	public EsitoScaricaRimodulazioneDTO scaricaRimodulazione(Long idUtente, String identitaDigitale, Long idProgetto,
																													 Long idSoggettoBeneficiario, Long idContoEconomico)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		EsitoScaricaRimodulazioneDTO esito = new EsitoScaricaRimodulazioneDTO();
		byte bytesPdf[] = null;
		String nomeFile = null;
		try {

			logger.info("idProgetto : " + idProgetto + " , idSoggettoBeneficiario : " + idSoggettoBeneficiario);

			String[] nameParameter = {"idUtente", "identitaDigitale", "idProgetto", "idSoggetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto,
					idSoggettoBeneficiario);

			ProgettoDTO progetto = new ProgettoDTO();
			progetto.setIdProgetto(idProgetto);

			// ID CONTO ECONOMICO DEV'ESSERE QUELLO DELLA LOCAL COPY

			// Alex: la select fallisce (anche nella versione vecchia) causa join su
			// PBANDI_D_STATO_DOCUMENTO_INDEX,
			// dove non esiste alcun record con l'idDocumentoIndex del pdf della
			// rimodulazione.
			// PbandiTDocumentoIndexVO documentoIndexVO =
			// documentoManager.getDocumentoIndexSuDb(
			// idContoEconomico,
			// idProgetto,
			// GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RIMODULAZIONE,
			// new PbandiTContoEconomicoVO().getTableNameForValueObject());

			// Sostituzione della select sopra.
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdEntita(new BigDecimal(42));
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(9));
			documentoIndexVO.setIdTarget(new BigDecimal(idContoEconomico));
			documentoIndexVO.setIdProgetto(new BigDecimal(idProgetto));
			documentoIndexVO = genericDAO.findSingleWhere(documentoIndexVO);

			if (!isNull(documentoIndexVO)) {

				// DISMISSIONE INDEX
				// bytesPdf = indexDAO.recuperaContenuto(documentoIndexVO.getUuidNodo());

				// **********************************************************************************
				// AGGIUNTA PER LETTURA SU FILESYSTEM.
				it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO fileDTO;
				fileDTO = documentoManagerNuovaVersione.leggiFile(documentoIndexVO.getIdDocumentoIndex().longValue());
				bytesPdf = fileDTO.getBytes();
				// **********************************************************************************

				nomeFile = documentoIndexVO.getNomeFile();

			} else {
				logger.warn("\n\n\n\n\nERRORE!!! Non trovato il documento di rimodulazione pdf.");
				throw new ContoEconomicoException("");
			}

			esito.setNomeFile(nomeFile);
			esito.setBytesPdf(bytesPdf);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}

		return esito;
	}

	public EsitoConclusioneRimodulazioneDTO concludiRimodulazione(Long idUtente, String identitaDigitale,
																																IstanzaAttivitaDTO istanzaAttivita, DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		// Node nodoIndex = null;

		Long idDocIndexPerRollBack = null;

		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "istanzaAttivita", "istanzaAttivita.codUtente",
					"istanzaAttivita.id", "datiPerConclusioneRimodulazione",
					"datiPerConclusioneRimodulazione.modalitaDiAgevolazione",
					"datiPerConclusioneRimodulazione.progetto", "datiPerConclusioneRimodulazione.progetto.idProgetto",
					"datiPerConclusioneRimodulazione.idSoggetto"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, istanzaAttivita,
					istanzaAttivita.getCodUtente(), istanzaAttivita.getId(), datiPerConclusioneRimodulazione,
					datiPerConclusioneRimodulazione.getModalitaDiAgevolazione(),
					datiPerConclusioneRimodulazione.getProgetto(),
					datiPerConclusioneRimodulazione.getProgetto().getIdProgetto(),
					datiPerConclusioneRimodulazione.getIdSoggetto());

			Long idSoggetto = datiPerConclusioneRimodulazione.getIdSoggetto();
			Double finanziamentoBancario = datiPerConclusioneRimodulazione.getImportoFinanziamentoBancario();

			// CHIUSURA RIMODULAZIONE

			BigDecimal idProgetto = new BigDecimal(datiPerConclusioneRimodulazione.getProgetto().getIdProgetto());

			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;

			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(idProgetto, listaTipi, mappaConti, tipologiaContoEconomicoCopy,
					mapDataPresentazioneDomanda);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoLocalCopy = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_COPY_IST);

			aggiornaModalitaRimodulazione(idUtente, datiPerConclusioneRimodulazione.getModalitaDiAgevolazione(),
					idProgetto, contoLocalCopy.getIdContoEconomico());

			aggiornaFontiFinanziarie(idUtente, identitaDigitale,
					ObjectUtil.arrayMerge(datiPerConclusioneRimodulazione.getFontiFinanziarieNonPrivate(),
							datiPerConclusioneRimodulazione.getFontiFinanziariePrivate()),
					idProgetto);

			Double importoGiuridicoVincolante = datiPerConclusioneRimodulazione.getImportoImpegnoVincolante();

			Long idContoEconomico = contoEconomicoManager.chiudiCopiaRimodulazione(idProgetto, new BigDecimal(idUtente),
					datiPerConclusioneRimodulazione.getNote(),
					datiPerConclusioneRimodulazione.getContrattiDaStipulare(),
					datiPerConclusioneRimodulazione.getRiferimento(), finanziamentoBancario,
					importoGiuridicoVincolante);

			// AGGIORNA DATA CONCESSIONE

			if (datiPerConclusioneRimodulazione.getDataConcessione() != null)
				aggiornaDataConcessione(idUtente, datiPerConclusioneRimodulazione.getDataConcessione(), idProgetto);

			// VN: Gestione della procedura di aggiudicazione. Verificare se
			// esiste una sola procedura di aggiudicazione (per ribasso d'asta)
			// legata al conto ecomonico (local copy). - se esiste una procedura
			// di aggiudicazione e l'utente ha indicato una nuova procedura di
			// aggiudicazione allora eseguo l'update
			//
			// - se esiste piu' di una procedura di aggiudicazione allora rilancio una
			// eccezione

			associaProceduraAggiudicazioneContoEconomico(contoLocalCopy.getIdContoEconomico(),
					NumberUtil.toBigDecimal(datiPerConclusioneRimodulazione.getIdProceduraAggiudicazione()));

			// FIND DATI E MAPPING CONTO ECONOMICO PER REPORT

			ProgettoDTO progetto = new ProgettoDTO();
			progetto.setIdProgetto(datiPerConclusioneRimodulazione.getProgetto().getIdProgetto());

			EsitoFindContoEconomicoDTO esitoFindContoEconomico = findContoEconomicoPerRimodulazione(idUtente,
					identitaDigitale, progetto);

			List<ContoEconomicoItemDTO> contoEconomicoItemList = getContoEconomicoPerRimodulazione(
					esitoFindContoEconomico);

			datiPerConclusioneRimodulazione.setTotaliModalitaDiAgevolazione(
					calcolaTotaliModalitaAgevolazione(datiPerConclusioneRimodulazione.getModalitaDiAgevolazione()));

			List<ModalitaAgevolazione> listModalita = contoEconomicoManager
					.mappaModalitaDiAgevolazionePerReport(datiPerConclusioneRimodulazione);

			FontiFinanziarieDTO[] arrayMerge = ObjectUtil.arrayMerge(
					datiPerConclusioneRimodulazione.getFontiFinanziarieNonPrivate(),
					datiPerConclusioneRimodulazione.getFontiFinanziariePrivate());
			FontiFinanziarieDTO totale = NumberUtil.accumulate(arrayMerge, "percQuotaFonteFinanziariaUltima",
					"percQuotaFonteFinanziariaNuova");
			totale.setQuotaFonteFinanziariaNuova(null);

			totale.setQuotaFonteFinanziariaUltima(datiPerConclusioneRimodulazione.getImportoSpesaAmmessa());

			FontiFinanziarieDTO subTotale = NumberUtil.accumulate(
					datiPerConclusioneRimodulazione.getFontiFinanziarieNonPrivate(), "percQuotaFonteFinanziariaUltima",
					"percQuotaFonteFinanziariaNuova", "quotaFonteFinanziariaUltima", "quotaFonteFinanziariaNuova");

			totale = NumberUtil.accumulate(arrayMerge, "percQuotaFonteFinanziariaUltima",
					"percQuotaFonteFinanziariaNuova", "quotaFonteFinanziariaUltima", "quotaFonteFinanziariaNuova");

			datiPerConclusioneRimodulazione.setSubTotaliFontiFinanziarieNonPrivate(subTotale);

			datiPerConclusioneRimodulazione.setTotaliFontiFinanziarie(totale);

			List<FonteFinanziaria> listFontiFinanziarie = contoEconomicoManager
					.mappaFontiFinanziariePerReport(datiPerConclusioneRimodulazione);

			Date currentDate = new Date(System.currentTimeMillis());

			// Progetto

			PbandiTProgettoVO progettoVO = progettoManager.getProgetto(idProgetto.longValue());
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGETTO, beanUtil.transform(progettoVO,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));

			popolaTemplateManager.setTipoModello(PopolaTemplateManager.MODELLO_RIMODULAZIONE);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgetto.longValue());

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RIMODULAZIONE_DTO,
					datiPerConclusioneRimodulazione);

			ContoEconomicoConStatoVO contoEconomicoLocalCopyIstr = contoEconomicoManager
					.findContoEconomicoLocalCopyIstr(idProgetto);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_CONTOECONOMICO_COPY_ISTR,
					contoEconomicoLocalCopyIstr);

			BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
			filtroBeneficiario.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			filtroBeneficiario.setIdSoggetto(beanUtil.transform(idSoggetto, BigDecimal.class));

			BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_BENEFICIARIO, beanUtil
					.transform(beneficiarioVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));

			// Rappresentante legale

			RappresentanteLegaleDTO rappresentanteLegale = rappresentanteLegaleManager
					.findRappresentanteLegale(datiPerConclusioneRimodulazione.getProgetto().getIdProgetto(), null);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegale);

			SedeVO sedeIntervento = null;
			try {
				sedeIntervento = sedeManager.findSedeIntervento(idProgetto.longValue(), idSoggetto.longValue());
			} catch (Exception e) {
				DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
						"Errore durante la ricerca della sede di intervento");
				throw dse;
			}

			// Sede intervento

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,
					beanUtil.transform(sedeIntervento, SedeDTO.class));

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_CONTOECONOMICO_RIMOD, contoEconomicoItemList);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_MODALITA_AGEVOLAZIONE, listModalita);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_FONTI_FINANZIARIE, listFontiFinanziarie);

			ProgettoDTO progettoDTO = new ProgettoDTO();
			progettoDTO.setIdProgetto(idProgetto.longValue());
			ProceduraAggiudicazioneProgettoVO proceduraVO = getProceduraAggiudicazione(progettoDTO);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROCEDURA_AGGIUDICAZIONE, proceduraVO);

			// 11/12/15 added footer
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC, "" + idContoEconomico);

			Modello modello = popolaTemplateManager.popolaModello(idProgetto.longValue());

			long startFillReport = System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(),
					modello.getMasterParameters(), new JREmptyDataSource());
			logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in "
					+ (System.currentTimeMillis() - startFillReport) + " ms\n");

			long startExport = System.currentTimeMillis();
			byte[] bytesPdf = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("\n\n\n########################\nJasperPrint esportato to pdf in "
					+ (System.currentTimeMillis() - startExport) + " ms\n");

			if (bytesPdf == null) {
				logger.warn("\n\n\n\nERRORE GRAVE : report PROPOSTA RIMODULAZIONE non creato ,bytes==null");
				throw new ContoEconomicoException("PDF NON CREATO");
			}

			RimodulazioneReportDTO rimodulazioneReportDTO = new RimodulazioneReportDTO();

			rimodulazioneReportDTO.setBytes(bytesPdf);
			rimodulazioneReportDTO.setNomeFile(creaNomeFileRimodulazione(idContoEconomico, currentDate));
			rimodulazioneReportDTO.setCodiceProgettoVisualizzato(progettoVO.getCodiceVisualizzato());
			rimodulazioneReportDTO.setIdProgetto(progettoVO.getIdProgetto().longValue());
			rimodulazioneReportDTO.setIdContoEconomico(idContoEconomico);
			rimodulazioneReportDTO.setDataInvio(currentDate);

			// PER TEST
//			logger.info("========================================================================================================");
//
//			logger.info("[ContoeconomicoBusinessImpl :: concludiRimodulazione()] - Test download file to local");
//			FileUtil.scriviFile("Rimodulazione_"+System.currentTimeMillis()+".pdf", bytesPdf);
//			logger.info("========================================================================================================");

			// CREO NODO INDEX E LO SALVO SUL DB

			// nodoIndex = indexDAO.creaContentRimodulazione(rimodulazioneReportDTO,
			// idUtente);

			Node nodoIndex = new Node();

			// ID CONTO ECONOMICO DEV'ESSERE QUELLO DELLA LOCAL COPY

			String shaHex = null;
			if (bytesPdf != null)
				shaHex = DigestUtils.shaHex(bytesPdf);

			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO;
			pbandiTDocumentoIndexVO = documentoManager.salvaInfoNodoIndexSuDbSenzaInsert(idUtente, nodoIndex,
					rimodulazioneReportDTO.getNomeFile(), idContoEconomico, null, null,
					datiPerConclusioneRimodulazione.getProgetto().getIdProgetto(),
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RIMODULAZIONE, PbandiTContoEconomicoVO.class,
					null, shaHex);

			// **********************************************************************************
			// AGGIUNTA PER SALVATAGGIO SU FILESYSTEM.
			pbandiTDocumentoIndexVO.setRepository(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RIMODULAZIONE);
			this.salvaFileSuFileSystem(bytesPdf, pbandiTDocumentoIndexVO);
			// **********************************************************************************

			if (pbandiTDocumentoIndexVO.getIdDocumentoIndex() != null)
				idDocIndexPerRollBack = pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue();

			logger.warn("\n\n\n\n############################ NEOFLUX UNLOCK RIM_CE ##############################\n");
			try {
				neofluxBusinessImpl.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(), Task.RIM_CE);
			} catch (Exception e) {
				logger.error("Errore durante lo sblocco dell'attività di Rimodulazione conto economico: IdUtente: " + idUtente + " identitaDigitale: " + identitaDigitale + " idProgetto : " + idProgetto.longValue() + " Task: " + Task.RIM_CE, e);
			}
			logger.warn("############################ NEOFLUX UNLOCK RIM_CE ##############################\n\n\n\n");

			List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica = Notification.NOTIFICA_RIMODULAZIONE;
			MetaDataVO metadata1 = new MetaDataVO();
			metadata1.setNome(Notification.DATA_CHIUSURA_RIMODULAZIONE);
			metadata1.setValore(DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);

			logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);

			logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
			genericDAO.callProcedure().sendNotificationMessage(idProgetto, descrBreveTemplateNotifica,
					Notification.BENEFICIARIO, idUtente);

			EsitoConclusioneRimodulazioneDTO e = new EsitoConclusioneRimodulazioneDTO();
			e.setIdContoEconomicoLocal(idContoEconomico);
			e.setSuccesso(true);

			return e;

		} catch (Exception e) {
			logger.info(
					"ROLLBACK APPLICATIVO: cancello nodo appena creato su index perch� c'e' stato un errore successivo");
			try {

				// DISMISSIONE INDEX !!!!!!!!!!!!!!
				// indexDAO.cancellaNodo(nodoIndex);
				// DISMISSIONE INDEX !!!!!!!!!!!!!!

				if (idDocIndexPerRollBack != null) {
					try {
						logger.info("Cancello pdf rimodulazione.");
						BigDecimal bd = new BigDecimal(idDocIndexPerRollBack);
						this.cancellaFileSuFileSystem(bd);
					} catch (Exception ex) {
					}
				}

			} catch (Exception e1) {
				logger.info("ATTENZIONE: ERRORE NEL CANCELLARE nodo appena creato su index " + e1.getMessage());
				RuntimeException re = new RuntimeException("Documento non cancellato su servizio INDEX");
				re.initCause(e);
				throw re;
			}

			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);

		}
	}

	// RIMODULAZIONE NORMALE :
	// 1) ribalto le vecchie modalita del master sulla local copy
	// 2) aggiorna le modalita del master(idUtente, modalitaDiAgevolazione,
	// idProgetto);
	// quindi ci sar� un disallineamento tra modalita di master e local copy OK
	private void aggiornaModalitaRimodulazione(Long idUtente, ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione,
																						 BigDecimal idProgetto, BigDecimal idContoEconomicoLocalCopy)
			throws Exception, ContoEconomicoNonTrovatoException {

		Map<?, ModalitaDiAgevolazioneContoEconomicoVO> indexModalitaPresenti = getBeanUtil().index(contoEconomicoManager
						.caricaModalitaAgevolazione(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER),
				"idModalitaAgevolazione");

		BigDecimal idContoEconomicoMaster = contoEconomicoManager.getIdContoMaster(idProgetto);

		Map<String, String> map = new HashMap<String, String>();
		map.put("idContoEconomico", "idContoEconomico");
		map.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
		map.put("importoRichiestoNuovo", "quotaImportoRichiesto");
		map.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : modalitaDiAgevolazione) {
			ModalitaDiAgevolazioneContoEconomicoVO modalitaPrecedente = indexModalitaPresenti
					.get(new BigDecimal(modalitaDiAgevolazioneDTO.getIdModalitaDiAgevolazione()));
			if (modalitaPrecedente.getFlagLvlprj()
					.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {

				PbandiRContoEconomModAgevVO modAgevVO = getBeanUtil().transform(modalitaPrecedente,
						PbandiRContoEconomModAgevVO.class);

				// duplico la modalita di agevolazione vecchia presa dal db
				// associandola alla local copy per storicizzarla
				modAgevVO.setIdContoEconomico(idContoEconomicoLocalCopy);
				modAgevVO.setIdUtenteIns(new BigDecimal(idUtente));
				genericDAO.insert(modAgevVO);

				// aggiornamento della modalit� gi� esistente associata al master con i dati
				// web.
				getBeanUtil().copyNotNullValues(modalitaDiAgevolazioneDTO, modAgevVO, map);
				modAgevVO.setIdUtenteIns(modalitaPrecedente.getIdUtenteIns());
				modAgevVO.setIdContoEconomico(idContoEconomicoMaster);
				modAgevVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(modAgevVO);

			} else if (NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo(), 0d) > 0
					|| NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), 0d) > 0) {

				PbandiRContoEconomModAgevVO nuovaModalita = new PbandiRContoEconomModAgevVO();
				nuovaModalita.setIdContoEconomico(idContoEconomicoMaster);

				Map<String, String> mapDTOtoVO = new HashMap<String, String>();
				mapDTOtoVO.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
				mapDTOtoVO.put("importoRichiestoNuovo", "quotaImportoRichiesto");
				mapDTOtoVO.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

				getBeanUtil().valueCopy(modalitaDiAgevolazioneDTO, nuovaModalita, mapDTOtoVO);

				nuovaModalita.setIdUtenteIns(new BigDecimal(idUtente));
				nuovaModalita.setPercImportoAgevolato(new BigDecimal(0));

				// associo la modalita al master
				genericDAO.insert(nuovaModalita);

			}
		}
	}

	// va bene sia per istruttoria che non
	private void aggiornaFontiFinanziarie(Long idUtente, String identitaDigitale,
																				FontiFinanziarieDTO[] fontiFinanziarie, BigDecimal idProgetto) throws Exception {

		Map<String, String> fonteMap = new HashMap<String, String>() {
			{
				put("percQuotaFonteFinanziariaNuova", "percQuotaSoggFinanziatore");
				put("progrProgSoggFinanziat", "progrProgSoggFinanziat");
				put("idProvincia", "idProvincia");
				put("idDelibera", "idDelibera");
				put("flagEconomie", "flagEconomie");
				put("idSoggetto", "idSoggetto");
				put("note", "note");
				put("estremiProvv", "estremiProvv");
				put("idComune", "idComune");
				put("idNorma", "idNorma");
				put("idPeriodo", "idPeriodo");
				put("idFonteFinanziaria", "idSoggettoFinanziatore");
				put("quotaFonteFinanziariaNuova", "impQuotaSoggFinanziatore");
			}
		};

		for (FontiFinanziarieDTO fonteFinanziariaDTO : fontiFinanziarie) {
			Double importo = fonteFinanziariaDTO.getQuotaFonteFinanziariaNuova();
			logger.info("QuotaFonteFinanziariaNuova = " + importo);
			if (fonteFinanziariaDTO.getProgrProgSoggFinanziat() != null) {
				logger.info("aggiorno pbandiRProgSoggFinanziatVO per " + fonteFinanziariaDTO.getDescrizione()
						+ " percentuale nuova " + fonteFinanziariaDTO.getPercQuotaFonteFinanziariaNuova());
				// arriva dalla RProgSoggFin, UPDATE
				PbandiRProgSoggFinanziatVO fonteVO = beanUtil.transform(fonteFinanziariaDTO,
						PbandiRProgSoggFinanziatVO.class, fonteMap);
				PbandiRProgSoggFinanziatVO existingVO = new PbandiRProgSoggFinanziatVO();
				existingVO.setProgrProgSoggFinanziat(new BigDecimal(fonteFinanziariaDTO.getProgrProgSoggFinanziat()));

				existingVO = genericDAO.findSingleWhere(existingVO);

				fonteVO.setIdProgetto(idProgetto);

				fonteVO.setIdUtenteIns(existingVO.getIdUtenteIns());
				fonteVO.setIdUtenteAgg(new BigDecimal(idUtente));

				// Jira PBANDI-2672: se importo = 0, si cancella anzich� modificare.
				if (importo != null && importo.intValue() == 0) {
					logger.info(
							"ramo 1: ImpQuotaSoggFinanziatore vale zero: cancello il record PbandiRProgSoggFinanziat invece di modificarlo.");
					genericDAO.delete(fonteVO);
				} else {
					genericDAO.updateNullables(fonteVO);
				}

			} else if (fonteFinanziariaDTO.getFlagLvlprj()
					.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {
				// � stato inserito dal web, INSERT/UPDATE
				// in ogni caso va verificato se CASUALMENTE qualcuno ha inserito
				// gi� qualcosa per quel soggetto e quel periodo
				PbandiRProgSoggFinanziatVO fonteVO = new PbandiRProgSoggFinanziatVO();
				fonteVO.setIdProgetto(idProgetto);
				fonteVO.setIdSoggetto(new BigDecimal(fonteFinanziariaDTO.getIdFonteFinanziaria()));
				fonteVO.setIdPeriodo(new BigDecimal(fonteFinanziariaDTO.getIdPeriodo()));
				PbandiRProgSoggFinanziatVO[] found = genericDAO.findWhere(fonteVO);
				if (found.length > 0) {
					// esiste gi� qualcosa, qualcuno ha inserito nel durante
					if (found.length > 1) {
						logger.warn("ATTENZIONE: sono state trovate " + found.length + " occorrenze con ID_PROGETTO="
								+ idProgetto + " ID_SOGGETTO=" + fonteVO.getIdSoggetto() + " ID_PERIODO="
								+ fonteVO.getIdPeriodo() + ".");
					} else {
						logger.info("aggiorno pbandiRProgSoggFinanziatVO fonte esistente per "
								+ fonteFinanziariaDTO.getDescrizione() + " percentuale nuova "
								+ fonteFinanziariaDTO.getPercQuotaFonteFinanziariaNuova());

						fonteVO = beanUtil.transform(fonteFinanziariaDTO, PbandiRProgSoggFinanziatVO.class, fonteMap);
						fonteVO.setProgrProgSoggFinanziat(found[0].getProgrProgSoggFinanziat());

						fonteVO.setIdUtenteAgg(new BigDecimal(idUtente));

						// Jira PBANDI-2672: se importo = 0, si cancella anzich� modificare.
						if (importo != null && importo.intValue() == 0) {
							logger.info(
									"ramo 2: ImpQuotaSoggFinanziatore vale zero: cancello il record PbandiRProgSoggFinanziat invece di modificarlo.");
							genericDAO.delete(fonteVO);
						} else {
							genericDAO.updateNullables(fonteVO);
						}
					}

				} else {

					// Jira PBANDI-2672: se importo = 0, inutile inserire.
					if (importo != null && importo.intValue() == 0) {
						logger.info(
								"ramo 3: ImpQuotaSoggFinanziatore vale zero: non inserisco il record PbandiRProgSoggFinanziat.");
					} else {
						// da creare nuovo
						logger.info("inserisco NUOVA FONTE pbandiRProgSoggFinanziatVO per "
								+ fonteFinanziariaDTO.getDescrizione() + " percentuale nuova "
								+ fonteFinanziariaDTO.getPercQuotaFonteFinanziariaNuova());

						fonteVO = beanUtil.transform(fonteFinanziariaDTO, PbandiRProgSoggFinanziatVO.class, fonteMap);
						fonteVO.setIdProgetto(idProgetto);
						fonteVO.setIdUtenteIns(new BigDecimal(idUtente));
						genericDAO.insert(fonteVO);
					}
				}
			} // else IGNORA
		}
	}

	private void aggiornaDataConcessione(Long idUtente, String dataConcessione, BigDecimal idProgetto)
			throws ParseException, Exception {
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(idProgetto);
		pbandiTProgettoVO.setIdUtenteAgg(new BigDecimal(idUtente));
		pbandiTProgettoVO.setDtConcessione(
				new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dataConcessione).getTime()));
		genericDAO.update(pbandiTProgettoVO);
	}

	private void associaProceduraAggiudicazioneContoEconomico(BigDecimal idContoEconomico,
																														BigDecimal idProceduraAggiudicazione) throws Exception {

		// VN: Gestione della procedura di aggiudicazione. Verificare se esiste
		// una sola procedura di aggiudicazione (per ribasso d'asta) legata al
		// conto ecomonico (local copy). - se esiste una procedura di
		// aggiudicazione e l'utente ha indicato una nuova procedura di
		// aggiudicazione allora eseguo l'update
		//
		// - se esiste piu' di una procedura di aggiudicazione allora rilancio una
		// eccezione

		PbandiTRibassoAstaVO ribassoVO = new PbandiTRibassoAstaVO();
		ribassoVO.setIdContoEconomico(idContoEconomico);
		List<PbandiTRibassoAstaVO> ribassi = genericDAO.findListWhere(ribassoVO);

		if (ribassi != null && !ribassi.isEmpty()) {
			if (ribassi.size() > 1) {
				ContoEconomicoException e = new ContoEconomicoException(
						"Errore trovati piu' di un ribasso associato al conto economino(" + idContoEconomico + ")");
				throw e;
			} else {
				if (idProceduraAggiudicazione != null) {
					ribassoVO = ribassi.get(0);
					ribassoVO.setIdProceduraAggiudicaz(idProceduraAggiudicazione);
					genericDAO.update(ribassoVO);
				}
			}
		}
	}

	private List<ContoEconomicoItemDTO> getContoEconomicoPerRimodulazione(
			EsitoFindContoEconomicoDTO esitoFindContoEconomico) {
		List<ContoEconomicoItemDTO> contoEconomicoItemList = contoEconomicoManager.mappaContoEconomicoPerReport(
				esitoFindContoEconomico.getContoEconomico(), true, esitoFindContoEconomico.getCopiaModificataPresente(),
				false, esitoFindContoEconomico.getDataFineIstruttoria(), esitoFindContoEconomico.getInIstruttoria());
		return contoEconomicoItemList;
	}

	private String creaNomeFileRimodulazione(Long idContoEconomico, Date currentDate) {
		String nomeFile;
		nomeFile = "Rimodulazione_" + idContoEconomico + "_" + DateUtil.getTime(currentDate, TIME_FORMAT_PER_NOME_FILE)
				+ ".pdf";
		logger.info("nomeFile del file della rimodulazione_ : " + nomeFile);
		return nomeFile;
	}

	public EsitoFindContoEconomicoDTO findContoEconomicoPerRimodulazioneIstruttoria(Long idUtente,
																																									String identitaDigitale, ProgettoDTO progetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

		ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO = null;

		try {
			// la rimodulazione prevede che si lavori su 3 possibili tipologie di conto
			// economico
			// - MAIN - MASTER - COPY_IST
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_MAIN;
			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti,
					tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

			// TNT modificato per istruttoria con master non presente
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);

			if (contoMain == null) {
				logger.warn("\n\n\nORRORE!!! non esiste conto main, LA FUNZIONALITA' FALLISCE!!!!");
			}

			// TNT modificato per istruttoria ,eliminato riferimento a master nei nomi
			// variabil
			contoEconomicoRimodulazioneDTO = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMain);

			EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoDTO();

			esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoRimodulazioneDTO);

			esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(
					progetto.getIdProgetto(), RegoleConstants.BR12_RIMODULAZIONE_DISPONIBILE));

			if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA
					.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom())) {
				esitoFindContoEconomicoDTO.setInIstruttoria(true);
				esitoFindContoEconomicoDTO.setInStatoRichiesto(false);
			} else if (Constants.STATO_CONTO_ECONOMICO_RICHIESTO
					.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom())) {
				esitoFindContoEconomicoDTO.setInStatoRichiesto(true);
				esitoFindContoEconomicoDTO.setInIstruttoria(false);
			}

			esitoFindContoEconomicoDTO
					.setDataPresentazioneDomanda(mapDataPresentazioneDomanda.get(DATA_PRESENTAZIONE_DOMANDA));

			return esitoFindContoEconomicoDTO;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}

	}

	// Il contoEconomicoDaCreare è il contoEconomico trovato sul db e modificato dal
	// client web:
	// quello sul db è il main, quello modificato va salvato come COPY_ISTR.
	// Al concludi rimodulazione per istruttoria diventa il MASTER.
	public EsitoRimodulazioneDTO rimodulaIstruttoria(Long idUtente, String identitaDigitale,
																									 ContoEconomicoRimodulazioneDTO contoEconomicoDaCreare)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "contoEconomico",
				"contoEconomico.idContoEconomico"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, contoEconomicoDaCreare,
				contoEconomicoDaCreare.getIdContoEconomico());

		try {
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;
			// mappa dei dati che mi interessa copiare per la storicizzazione
			Map<String, String> map = new HashMap<String, String>();
			map.put("importoSpesaAmmessaRimodulazione", "importoAmmesso");
			map.put("importoRichiestoInDomanda", "importoRichiesto");

			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(
					contoEconomicoManager.findIdProgetto(new BigDecimal(contoEconomicoDaCreare.getIdContoEconomico())),
					listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);

			EsitoRimodulazioneDTO esitoRimodulazioneDTO = new EsitoRimodulazioneDTO();

			esitoRimodulazioneDTO.setContoEconomico(storicizza(idUtente, contoEconomicoDaCreare, listaTipi, mappaConti,
					tipologiaContoEconomicoCopy, map, contoMain));

			return esitoRimodulazioneDTO;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ContoEconomicoRimodulazioneDTO storicizza(Long idUtente,
																										ContoEconomicoRimodulazioneDTO contoEconomicoSorgente, List<String> listaTipi,
																										Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti,
																										String tipologiaContoEconomicoCopy, Map<String, String> map,
																										it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoDestinazione)
			throws ContoEconomicoNonTrovatoException, ContoEconomicoCopiaFallitaException,
			ContoEconomicoRibaltamentoFallitoException, Exception {

		if (!listaTipi.contains(tipologiaContoEconomicoCopy)) {
			listaTipi.add(tipologiaContoEconomicoCopy);
		}

		getBeanUtil().recursiveValueCopy("figli", contoEconomicoSorgente, contoDestinazione, map);

		contoEconomicoManager.aggiornaStoricizzandoRighiContoEconomico(contoDestinazione, new BigDecimal(idUtente));

		contoEconomicoManager.aggiornaStatoContoEconomico(contoEconomicoSorgente.getIdContoEconomico(),
				Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA, new BigDecimal(idUtente));

		mappaConti.put(tipologiaContoEconomicoCopy,
				contoEconomicoManager.loadContoEconomico(contoDestinazione.getIdContoEconomico()));

		contoEconomicoSorgente = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoDestinazione);

		return contoEconomicoSorgente;
	}

	public DatiPerConclusioneRimodulazioneDTO caricaDatiPerConclusioneIstruttoria(Long idUtente,
																																								String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);

			ProgettoDTO progetto = new ProgettoDTO();
			progetto.setIdProgetto(idProgetto);

			DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO = new DatiPerConclusioneRimodulazioneDTO();
			ModalitaDiAgevolazioneDTO[] modalitaAgevolazione = null;

			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici;
			contiEconomici = contoEconomicoManager.findContiEconomici(new BigDecimal(progetto.getIdProgetto()),
					Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = contiEconomici
					.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);

			if (contoMain == null) {
				logger.error(
						"Errore fatal! conto economico main non esiste per idProgetto: " + progetto.getIdProgetto());
				throw new Exception(
						"Errore fatal! conto economico main non esiste per idProgetto: " + progetto.getIdProgetto());
			}

			Double importoSpesaAmmessa = NumberUtil.toDouble(contoMain.getImportoAmmesso());

			modalitaAgevolazione = caricaModalitaAgevolazionePerIstruttoria(progetto.getIdProgetto(),
					importoSpesaAmmessa, contoMain.getIdContoEconomico());

			datiPerConclusioneRimodulazioneDTO.setModalitaDiAgevolazione(modalitaAgevolazione);
			datiPerConclusioneRimodulazioneDTO
					.setTotaliModalitaDiAgevolazione(calcolaTotaliModalitaAgevolazione(modalitaAgevolazione));

			Map<String, String> mapFin = creaMapFonteFinanziaria();

			BigDecimal idProgettoBD = new BigDecimal(progetto.getIdProgetto());

			FontiFinanziarieDTO[] fontiPrivate = beanUtil.transform(
					contoEconomicoManager.getSoggettiFinanziatori(idProgettoBD, "N"), FontiFinanziarieDTO.class,
					mapFin);

			FontiFinanziarieDTO[] fontiNonPrivate = beanUtil.transform(
					contoEconomicoManager.getSoggettiFinanziatori(idProgettoBD, "S"), FontiFinanziarieDTO.class,
					mapFin);

			Boolean isPeriodoUnico = isPeriodoUnico(fontiNonPrivate);
			logger.info("\n\n\n\n ************** isPeriodoUnico: " + isPeriodoUnico);
			datiPerConclusioneRimodulazioneDTO.setIsPeriodoUnico(isPeriodoUnico);
			datiPerConclusioneRimodulazioneDTO.setFontiFinanziarieNonPrivate(fontiNonPrivate);
			datiPerConclusioneRimodulazioneDTO.setFontiFinanziariePrivate(fontiPrivate);

			if (isPeriodoUnico) {
				// spatarramento su fonti TNT 19 lug 2016
				setDefaultPercentualiImportiFontiNonPrivate(progetto, importoSpesaAmmessa,
						datiPerConclusioneRimodulazioneDTO);
			}

			Map<String, String> mapDefault = new HashMap<String, String>();
			mapDefault.put("percQuotaFonteFinanziariaUltima", "percQuotaFonteFinanziariaNuova");

			getBeanUtil().valueCopy(fontiPrivate, fontiPrivate, mapDefault);
			getBeanUtil().valueCopy(fontiNonPrivate, fontiNonPrivate, mapDefault);

			FontiFinanziarieDTO[] arrayMerge = ObjectUtil.arrayMerge(fontiNonPrivate, fontiPrivate);
			FontiFinanziarieDTO totale = NumberUtil.accumulate(arrayMerge, "percQuotaFonteFinanziariaUltima",
					"percQuotaFonteFinanziariaNuova");
			totale.setQuotaFonteFinanziariaNuova(importoSpesaAmmessa);

			for (FontiFinanziarieDTO f : arrayMerge) {
				calcolaQuotaFonteFinanziaria(totale, f);
			}

			FontiFinanziarieDTO subTotale = NumberUtil.accumulate(fontiNonPrivate, "percQuotaFonteFinanziariaUltima",
					"percQuotaFonteFinanziariaNuova");
			calcolaQuotaFonteFinanziaria(totale, subTotale);

			totale = NumberUtil.accumulate(arrayMerge, "percQuotaFonteFinanziariaUltima",
					"percQuotaFonteFinanziariaNuova", "quotaFonteFinanziariaUltima", "quotaFonteFinanziariaNuova");

			datiPerConclusioneRimodulazioneDTO.setFontiFinanziarieNonPrivate(fontiNonPrivate);
			datiPerConclusioneRimodulazioneDTO.setFontiFinanziariePrivate(fontiPrivate);
			datiPerConclusioneRimodulazioneDTO.setSubTotaliFontiFinanziarieNonPrivate(subTotale);
			datiPerConclusioneRimodulazioneDTO.setImportoSpesaAmmessa(importoSpesaAmmessa);
			datiPerConclusioneRimodulazioneDTO.setTotaliFontiFinanziarie(totale);
			datiPerConclusioneRimodulazioneDTO.setProgetto(progetto);

			setDatiAccessori(progetto, datiPerConclusioneRimodulazioneDTO, contiEconomici, contoMain);
			logger.info("\n\n\nfontiNonPrivate :");
			if (fontiNonPrivate != null) {
				for (FontiFinanziarieDTO fonte : fontiNonPrivate) {
					logger.info(fonte.getDescrizione() + " " + fonte.getDescPeriodo() + " "
							+ fonte.getImpQuotaSoggFinanziatore() + " " + fonte.getPercQuotaFonteFinanziariaNuova());
				}
			}
			logger.info("fonti private:");
			if (fontiPrivate != null) {
				for (FontiFinanziarieDTO fonte : fontiPrivate) {
					logger.info(fonte.getDescrizione() + " " + fonte.getDescPeriodo() + " "
							+ fonte.getImpQuotaSoggFinanziatore() + " " + fonte.getPercQuotaFonteFinanziariaNuova());
				}
			}
			logger.info("\n\n\n\n\n\n");

			return datiPerConclusioneRimodulazioneDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ModalitaDiAgevolazioneDTO[] caricaModalitaAgevolazionePerIstruttoria(double idProgetto,
																																							 Double totaleSpesaAmmessaNuova, BigDecimal idContoEconomico) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
		map.put("descModalitaAgevolazione", "descrizione");
		map.put("massimoImportoAgevolato", "importoMassimoAgevolato");
		map.put("importoErogazioni", "importoErogato");
		map.put("quotaImportoAgevolato", "importoAgevolatoNuovo");
		map.put("quotaImportoRichiesto", "importoRichiestoUltimo");
		map.put("percImportoAgevolatoBando", "percImportoMassimoAgevolato");

		List<ModalitaDiAgevolazioneDTO> list = beanUtil.transformList(
				contoEconomicoManager.caricaModalitaAgevolazione(idContoEconomico), ModalitaDiAgevolazioneDTO.class,
				map);

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : list) {
			if (modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo() == null)
				modalitaDiAgevolazioneDTO.setImportoAgevolatoNuovo(0d);
			modalitaDiAgevolazioneDTO.setPercImportoAgevolatoNuovo(NumberUtil.toDouble(NumberUtil
					.percentage(modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo(), totaleSpesaAmmessaNuova)));

		}

		return list.toArray(new ModalitaDiAgevolazioneDTO[list.size()]);
	}

	private void setDefaultPercentualiImportiFontiNonPrivate(ProgettoDTO progetto, Double importoSpesaAmmessa,
																													 DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO) {
		logger.info("setPercentualiDefaultFontiNonPrivate ");
		FontiFinanziarieDTO[] fontiFinanziarieNonPrivate = datiPerConclusioneRimodulazioneDTO
				.getFontiFinanziarieNonPrivate();
		if (importoSpesaAmmessa != null && importoSpesaAmmessa.doubleValue() > 0
				&& !isEmpty(fontiFinanziarieNonPrivate)) {
			BigDecimal idBando = findIdBando(progetto.getIdProgetto());
			logger.info("idBando:" + idBando);
			BandoSoggettoFinanziatoreAssociatoVO filter = new BandoSoggettoFinanziatoreAssociatoVO();
			filter.setIdBando(idBando);
			List<BandoSoggettoFinanziatoreAssociatoVO> soggetti = genericDAO.findListWhere(filter);
			List<PercentualeBandoSoggFinanzDTO> percBandoSogg = new ArrayList<PercentualeBandoSoggFinanzDTO>();
			for (BandoSoggettoFinanziatoreAssociatoVO soggettoFin : soggetti) {
				BigDecimal percentualeQuotaSoggFinanz = soggettoFin.getPercentualeQuotaSoggFinanz();
				BigDecimal idSoggettoFinanziatore = soggettoFin.getIdSoggettoFinanziatore();
				if (percentualeQuotaSoggFinanz != null) {
					for (FontiFinanziarieDTO fonte : fontiFinanziarieNonPrivate) {
						if (idSoggettoFinanziatore.longValue() == fonte.getIdFonteFinanziaria()) {
							fonte.setPercQuotaDefault(percentualeQuotaSoggFinanz.doubleValue());
							fonte.setPercQuotaFonteFinanziariaNuova(percentualeQuotaSoggFinanz.doubleValue());
							fonte.setPercQuotaFonteFinanziariaUltima(percentualeQuotaSoggFinanz.doubleValue());
							BigDecimal importoDefault = NumberUtil.divide(BigDecimal.valueOf(importoSpesaAmmessa),
									BigDecimal.valueOf(100));
							importoDefault = NumberUtil.multiply(importoDefault, percentualeQuotaSoggFinanz);
							fonte.setImpQuotaSoggFinanziatore(importoDefault.doubleValue());
							break;
						}
					}
				}
			}
		}
	}

	private BigDecimal findIdBando(Long idProgetto) {
		BandoProgettoVO vo = new BandoProgettoVO();
		vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
		vo = genericDAO.findSingleWhere(vo);
		return vo.getIdBando();
	}

	private void calcolaQuotaFonteFinanziaria(FontiFinanziarieDTO totale, FontiFinanziarieDTO f) {
		f.setQuotaFonteFinanziariaNuova(NumberUtil.toDouble(NumberUtil
				.percentageOf(f.getPercQuotaFonteFinanziariaNuova(), totale.getQuotaFonteFinanziariaNuova())));
		f.setQuotaFonteFinanziariaUltima(NumberUtil.toDouble(NumberUtil
				.percentageOf(f.getPercQuotaFonteFinanziariaUltima(), totale.getQuotaFonteFinanziariaUltima())));
	}

	private void setDatiAccessori(ProgettoDTO progetto,
																DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO,
																Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici,
																it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain) {
		// 1)master 2) main
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = contiEconomici
				.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		if (contoMaster != null) {
			contoEconomicoVO.setIdContoEconomico(contoMaster.getIdContoEconomico());
		} else {
			contoEconomicoVO.setIdContoEconomico(contoMain.getIdContoEconomico());
		}

		contoEconomicoVO = genericDAO.findSingleWhere(contoEconomicoVO);
		BigDecimal importoFinanziamentoBanca = null;
		if (contoEconomicoVO.getImportoFinanziamentoBanca() != null) {
			importoFinanziamentoBanca = contoEconomicoVO.getImportoFinanziamentoBanca();
		}
		if (importoFinanziamentoBanca == null) {
			contoEconomicoVO.setIdContoEconomico(contoMain.getIdContoEconomico());
			contoEconomicoVO = genericDAO.findSingleWhere(Condition.filterByKeyOf(contoEconomicoVO));
			if (contoEconomicoVO.getImportoFinanziamentoBanca() != null) {
				importoFinanziamentoBanca = contoEconomicoVO.getImportoFinanziamentoBanca();
			}
		}

		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(progetto.getIdProgetto()));
		pbandiTProgettoVO = genericDAO.findSingleWhere(Condition.filterByKeyOf(pbandiTProgettoVO));
		setDataConcessione(datiPerConclusioneRimodulazioneDTO, pbandiTProgettoVO);

		datiPerConclusioneRimodulazioneDTO
				.setImportoFinanziamentoBancario(NumberUtil.toDouble(importoFinanziamentoBanca));
	}

	public EsitoConclusioneRimodulazioneDTO concludiRimodulazioneIstruttoria(Long idUtente, String identitaDigitale,
																																					 IstanzaAttivitaDTO istanzaAttivita, DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "istanzaAttivita", "istanzaAttivita.codUtente",
					"istanzaAttivita.id", "datiPerConclusioneRimodulazione",
					"datiPerConclusioneRimodulazione.modalitaDiAgevolazione",
					"datiPerConclusioneRimodulazione.progetto", "datiPerConclusioneRimodulazione.progetto.idProgetto",
					"datiPerConclusioneRimodulazione.idSoggetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, istanzaAttivita,
					istanzaAttivita.getCodUtente(), istanzaAttivita.getId(), datiPerConclusioneRimodulazione,
					datiPerConclusioneRimodulazione.getModalitaDiAgevolazione(),
					datiPerConclusioneRimodulazione.getProgetto(),
					datiPerConclusioneRimodulazione.getProgetto().getIdProgetto(),
					datiPerConclusioneRimodulazione.getIdSoggetto());

			logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nconcludiRimodulazioneIstruttoria");

			Double importoFinanziamentoBancario = datiPerConclusioneRimodulazione.getImportoFinanziamentoBancario();

			// CHIUSURA RIMODULAZIONE

			BigDecimal idProgetto = new BigDecimal(datiPerConclusioneRimodulazione.getProgetto().getIdProgetto());

			FontiFinanziarieDTO[] fontiFinanziarieNonPrivate = datiPerConclusioneRimodulazione
					.getFontiFinanziarieNonPrivate();

			// if(fontiFinanziarieNonPrivate != null){
			// for (FontiFinanziarieDTO fonte : fontiFinanziarieNonPrivate) {
			// logger.info("\n\n\nfonte non privata:");
			// logger.shallowDump(fonte, "info");
			// }
			// }

			aggiornaFontiFinanziarie(idUtente, identitaDigitale,
					ObjectUtil.arrayMerge(datiPerConclusioneRimodulazione.getFontiFinanziarieNonPrivate(),
							datiPerConclusioneRimodulazione.getFontiFinanziariePrivate()),
					idProgetto);

			// 1)trovare il main
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;
			// mappa dei dati che mi interessa copiare per la storicizzazione
			Map<String, String> map = new HashMap<String, String>();
			map.put("importoSpesaAmmessaRimodulazione", "importoAmmesso");
			map.put("importoRichiestoInDomanda", "importoRichiesto");

			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(new BigDecimal(datiPerConclusioneRimodulazione.getProgetto().getIdProgetto()),
					listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti
					.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			if (contoMaster == null) {
				contoMaster = contoEconomicoManager.creaMaster(contoMain, idUtente,
						datiPerConclusioneRimodulazione.getNote(), datiPerConclusioneRimodulazione.getRiferimento(),
						importoFinanziamentoBancario);
			} else {
				contoEconomicoManager.chiudiMain(contoMain, idUtente, datiPerConclusioneRimodulazione.getNote(),
						datiPerConclusioneRimodulazione.getRiferimento(), importoFinanziamentoBancario);
			}

			aggiornaModalitaRimodulazioneIstruttoria(idUtente,
					datiPerConclusioneRimodulazione.getModalitaDiAgevolazione(), idProgetto,
					contoMain.getIdContoEconomico(), contoMaster.getIdContoEconomico());

			// AGGIORNA DATA CONCESSIONE

			if (datiPerConclusioneRimodulazione.getDataConcessione() != null)
				aggiornaDataConcessione(idUtente, datiPerConclusioneRimodulazione.getDataConcessione(), idProgetto);

			logger.warn(
					"\n\n\n############################ NEOFLUX END ATTIVITA RIM_CE_ISTR ##############################\n");
			neofluxBusinessImpl.endAttivita(idUtente, identitaDigitale, idProgetto.longValue(), Task.RIM_CE_ISTR);
			logger.warn(
					"############################ NEOFLUX END ATTIVITA  RIM_CE_ISTR ##############################\n\n\n\n");

			EsitoConclusioneRimodulazioneDTO e = new EsitoConclusioneRimodulazioneDTO();
			e.setSuccesso(true);

			return e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	// RIMODULAZIONE ISTRUTTORIA:
	// insert o update (nel caso le avesse create il beneficiario) delle modalita
	// collegate al main
	// ribaltamento collegandole al master
	// quindi ci sarà una equivalenza tra modalita di main e master.
	private void aggiornaModalitaRimodulazioneIstruttoria(Long idUtente,
																												ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione, BigDecimal idProgetto, BigDecimal idContoEconomicoMain,
																												BigDecimal idContoEconomicoMaster) throws Exception {

		Map<?, ModalitaDiAgevolazioneContoEconomicoVO> indexModalitaPresenti = getBeanUtil().index(contoEconomicoManager
						.caricaModalitaAgevolazioneContoChiuso(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN),
				"idModalitaAgevolazione");

		Map<String, String> map = new HashMap<String, String>();
		map.put("idContoEconomico", "idContoEconomico");
		map.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
		map.put("importoRichiestoNuovo", "quotaImportoRichiesto");
		map.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : modalitaDiAgevolazione) {
			ModalitaDiAgevolazioneContoEconomicoVO modalitaPrecedente = indexModalitaPresenti
					.get(new BigDecimal(modalitaDiAgevolazioneDTO.getIdModalitaDiAgevolazione()));
			if (modalitaPrecedente.getFlagLvlprj()
					.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {

				// insert del valore già esistente della modalità associata al master.

				PbandiRContoEconomModAgevVO modAgevVO = getBeanUtil().transform(modalitaPrecedente,
						PbandiRContoEconomModAgevVO.class);

				getBeanUtil().copyNotNullValues(modalitaDiAgevolazioneDTO, modAgevVO, map);
				modAgevVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(modAgevVO);

				modAgevVO.setIdUtenteIns(new BigDecimal(idUtente));
				modAgevVO.setIdContoEconomico(idContoEconomicoMaster);
				genericDAO.insert(modAgevVO);

			} else if (NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo(), 0d) > 0
					|| NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), 0d) > 0) {
				PbandiRContoEconomModAgevVO nuovaModalita = new PbandiRContoEconomModAgevVO();
				nuovaModalita.setIdContoEconomico(idContoEconomicoMain);

				Map<String, String> mapDTOtoVO = new HashMap<String, String>();
				mapDTOtoVO.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
				mapDTOtoVO.put("importoRichiestoNuovo", "quotaImportoRichiesto");
				mapDTOtoVO.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

				getBeanUtil().valueCopy(modalitaDiAgevolazioneDTO, nuovaModalita, mapDTOtoVO);

				nuovaModalita.setIdUtenteIns(new BigDecimal(idUtente));
				nuovaModalita.setPercImportoAgevolato(new BigDecimal(0));

				genericDAO.insert(nuovaModalita);

				nuovaModalita.setIdContoEconomico(idContoEconomicoMaster);
				genericDAO.insert(nuovaModalita);
			}
		}
	}

	public EsitoFindContoEconomicoDTO findContoEconomicoInDomanda(Long idUtente, String identitaDigitale,
																																ProgettoDTO progetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

		ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO = null;

		EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoDTO();

		try {

			// cerco il main,se non lo trovo lo creo

			BigDecimal idProgetto = new BigDecimal(progetto.getIdProgetto());

			PbandiTContoEconomicoVO pbandiTContoEconomicoVO = null;
			try {
				pbandiTContoEconomicoVO = contoEconomicoManager.findContoEconomicoMainApertoOChiuso(idProgetto);
			} catch (Exception e) {
				logger.debug("conto economico main non trovato");
			}

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = null;
			if (pbandiTContoEconomicoVO == null) {
				contoMain = contoEconomicoManager.createMain(idProgetto, idUtente);
			} else {
				contoMain = contoEconomicoManager.loadContoEconomico(pbandiTContoEconomicoVO.getIdContoEconomico());
			}

			PbandiTRigoContoEconomicoVO rigoContoEconomicoVO = new PbandiTRigoContoEconomicoVO();
			rigoContoEconomicoVO.setIdContoEconomico(contoMain.getIdContoEconomico());
			List<PbandiTRigoContoEconomicoVO> righi = genericDAO.findListWhere(rigoContoEconomicoVO);
			if (isEmpty(righi)) {
				esitoFindContoEconomicoDTO.setIsContoMainNew(true);
			} else
				esitoFindContoEconomicoDTO.setIsContoMainNew(false);

			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MAIN, contoMain);
			listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			contoEconomicoRimodulazioneDTO = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMain);

			esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoRimodulazioneDTO);

			esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(
					progetto.getIdProgetto(), RegoleConstants.BR27_RIMODULAZIONE_DISPONIBILE));
			if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA
					.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
				esitoFindContoEconomicoDTO.setInIstruttoria(true);
			else
				esitoFindContoEconomicoDTO.setInIstruttoria(false);
			if (Constants.STATO_CONTO_ECONOMICO_RICHIESTO.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
				esitoFindContoEconomicoDTO.setInStatoRichiesto(true);
			else
				esitoFindContoEconomicoDTO.setInStatoRichiesto(false);

			logger.debug("conto economico id:" + contoMain.getIdContoEconomico());

			return esitoFindContoEconomicoDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}

	}

	public EsitoSalvaContoEconomicoDTO salvaContoEconomicoRichiestoInDomanda(Long idUtente, String identitaDigitale,
																																					 ContoEconomicoRimodulazioneDTO contoEconomico, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		try {

			EsitoSalvaContoEconomicoDTO esitoSalvaContoEconomicoDTO = new EsitoSalvaContoEconomicoDTO();

			String[] nameParameter = {"idUtente", "identitaDigitale", "contoEconomico"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, contoEconomico);

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMainOld = contoEconomicoManager
					.loadContoEconomico(new BigDecimal(contoEconomico.getIdContoEconomico()));

			logger.debug("conto economico MAIN da aggiornare id:" + contoMainOld.getIdContoEconomico());

			Map<String, String> mapMain = new HashMap<String, String>();
			mapMain.put("importoRichiestoInDomanda", "importoRichiesto");
			mapMain.put("label", "descVoceDiSpesa");
			mapMain.put("idVoce", "idVoceDiSpesa");
			mapMain.put("idContoEconomico", "idContoEconomico");
			mapMain.put("idRigoContoEconomico", "idRigoContoEconomico");
			mapMain.put("idVocePadre", "idVoceDiSpesaPadre");

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMainNew = convertiContoEconomicoMain(
					contoEconomico, mapMain);

			logger.debug("conto economico MAINNEW da aggiornare id:" + contoMainNew.getIdContoEconomico());
			logger.debug("conto economico MAINOLD id:" + contoMainNew.getIdContoEconomico());

			contoEconomicoManager.aggiornaMain(contoMainNew, contoMainOld, idUtente);

			// ritornare il conto letto dal db

			contoMainOld = contoEconomicoManager
					.loadContoEconomico(new BigDecimal(contoEconomico.getIdContoEconomico()));

			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoMain = TIPOLOGIA_CONTO_ECONOMICO_MAIN;
			listaTipi.add(tipologiaContoEconomicoMain);
			mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MAIN, contoMainOld);
			ContoEconomicoRimodulazioneDTO nuovoContoEconomico = convertiContoEconomicoPerRimodulazione(listaTipi,
					mappaConti, contoMainOld);

			esitoSalvaContoEconomicoDTO.setContoEconomico(nuovoContoEconomico);
			return esitoSalvaContoEconomicoDTO;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO convertiContoEconomicoMain(
			ContoEconomicoRimodulazioneDTO contoEconomicoSorgente, Map<String, String> mapMain) {

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoDestinazione;

		contoEconomicoDestinazione = getBeanUtil().transform(contoEconomicoSorgente,
				it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO.class, mapMain);

		if (!isEmpty(contoEconomicoSorgente.getFigli())) {

			List<ContoEconomicoRimodulazioneDTO> figli = Arrays.asList(contoEconomicoSorgente.getFigli());

			List<RigoContoEconomicoDTO> figliTrasformati = new ArrayList<RigoContoEconomicoDTO>();
			if (figli != null && figli.size() > 0) {
				for (ContoEconomicoRimodulazioneDTO figlioCorrente : figli) {
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO figlioTrasformato = convertiContoEconomicoMain(
							figlioCorrente, mapMain);
					figliTrasformati.add(figlioTrasformato);
				}
			}
			contoEconomicoDestinazione.setFigli(figliTrasformati);
		}

		return contoEconomicoDestinazione;
	}

	public DatiPerInvioPropostaRimodulazioneDTO caricaDatiPerRichiestaContoEconomico(Long idUtente,
																																									 String identitaDigitale, ProgettoDTO progetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto,
					progetto.getIdProgetto());

			DatiPerInvioPropostaRimodulazioneDTO datiDTO = new DatiPerInvioPropostaRimodulazioneDTO();

			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici;
			contiEconomici = contoEconomicoManager.findContiEconomici(new BigDecimal(progetto.getIdProgetto()),
					Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN);

			BigDecimal importoRichiesto = new BigDecimal(0);
			if (contiEconomici.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN) != null) {
				importoRichiesto = contiEconomici.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN).getImportoRichiesto();
				if (importoRichiesto != null) {
					datiDTO.setImportoSpesaRichiesto(NumberUtil.toDouble(importoRichiesto));
				}
			}

			Double totaleRichiestoInDomanda = NumberUtil.toDouble(importoRichiesto);

			ModalitaDiAgevolazioneDTO[] modalitaAgevolazione = caricaModalitaAgevolazionePerRichiestaContoEconomico(
					progetto.getIdProgetto(), totaleRichiestoInDomanda, Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);

			datiDTO.setModalitaDiAgevolazione(modalitaAgevolazione);
			datiDTO.setTotaliModalitaDiAgevolazione(calcolaTotaliModalitaAgevolazione(modalitaAgevolazione));

			datiDTO.setProgetto(progetto);

			Boolean importiModificabili = regolaManager.isRegolaApplicabileForProgetto(progetto.getIdProgetto(),
					RegoleConstants.BR17_RICHIESTA_NUOVI_IMPORTI);

			datiDTO.setIsImportiModificabili(importiModificabili);

			return datiDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ModalitaDiAgevolazioneDTO[] caricaModalitaAgevolazionePerRichiestaContoEconomico(double idProgetto,
																																													 Double totaleRichiestoInDomanda, String tipologiaContoEconomico) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// dati caricati dal db
		map.put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
		map.put("descModalitaAgevolazione", "descrizione");
		map.put("quotaImportoRichiesto", "importoRichiestoNuovo");
		map.put("massimoImportoAgevolato", "importoMassimoAgevolato");
		map.put("percImportoAgevolato", "percImportoMassimoAgevolato");

		List<ModalitaDiAgevolazioneDTO> list = getBeanUtil().transformList(contoEconomicoManager
						.caricaModalitaAgevolazioneContoChiuso(new BigDecimal(idProgetto), tipologiaContoEconomico),
				ModalitaDiAgevolazioneDTO.class, map);

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : list) {
			modalitaDiAgevolazioneDTO.setPercImportoRichiestoNuovo(NumberUtil.toDouble(NumberUtil
					.percentage(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), totaleRichiestoInDomanda)));
		}

		return list.toArray(new ModalitaDiAgevolazioneDTO[list.size()]);
	}

	public EsitoRichiestaContoEconomicoDTO inviaRichiestaContoEconomico(Long idUtente, String identitaDigitale,
																																			DatiPerInvioPropostaRimodulazioneDTO datiPerInvioPropostaRimodulazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		EsitoRichiestaContoEconomicoDTO esitoRichiestaContoEconomicoDTO = new EsitoRichiestaContoEconomicoDTO();
		try {
			String[] nameParameter = {"idUtente", "identitaDigitale", "datiPerInvioPropostaRimodulazione",
					"datiPerInvioPropostaRimodulazione.modalitaDiAgevolazione",
					"datiPerInvioPropostaRimodulazione.progetto",
					"datiPerInvioPropostaRimodulazione.progetto.idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, datiPerInvioPropostaRimodulazione,
					datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione(),
					datiPerInvioPropostaRimodulazione.getProgetto(),
					datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			Double importoFinanziamentoBancario = datiPerInvioPropostaRimodulazione.getImportoFinanziamentoRichiesto();
			Long idContoEconomico = datiPerInvioPropostaRimodulazione.getIdContoEconomico();

			BigDecimal idProgetto = new BigDecimal(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			// CHIUSURA PROPOSTA

			aggiornaModalitaRichiestoDomanda(idUtente, datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione(),
					idProgetto, new BigDecimal(idContoEconomico));

			contoEconomicoManager.aggiornaContoEconomicoBeneficiario(idContoEconomico,
					Constants.STATO_CONTO_ECONOMICO_RICHIESTO, new BigDecimal(idUtente), importoFinanziamentoBancario);

			logger.warn(
					"\n\n\n############################ NEOFLUX END ATTIVITA RICH_CE_DOM ##############################\n");
			neofluxBusinessImpl.endAttivita(idUtente, identitaDigitale, idProgetto.longValue(), Task.RICH_CE_DOM);
			logger.warn(
					"############################ NEOFLUX END ATTIVITA RICH_CE_DOM ##############################\n\n\n\n");

			esitoRichiestaContoEconomicoDTO.setSuccesso(true);

			esitoRichiestaContoEconomicoDTO.setIdContoEconomico(idContoEconomico);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		return esitoRichiestaContoEconomicoDTO;
	}

	private void aggiornaModalitaRichiestoDomanda(Long idUtente, ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione,
																								BigDecimal idProgetto, BigDecimal idContoEconomico) throws Exception {

		Map<?, ModalitaDiAgevolazioneContoEconomicoVO> indexModalitaPresenti = getBeanUtil().index(contoEconomicoManager
						.caricaModalitaAgevolazioneContoChiuso(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN),
				"idModalitaAgevolazione");

		Map<String, String> map = new HashMap<String, String>();
		map.put("idContoEconomico", "idContoEconomico");
		map.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
		map.put("importoRichiestoNuovo", "quotaImportoRichiesto");
		map.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : modalitaDiAgevolazione) {
			ModalitaDiAgevolazioneContoEconomicoVO modalitaPrecedente = indexModalitaPresenti
					.get(new BigDecimal(modalitaDiAgevolazioneDTO.getIdModalitaDiAgevolazione()));
			if (modalitaPrecedente.getFlagLvlprj()
					.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {

				// insert del valore gi� esistente della modalit� associata al conto

				PbandiRContoEconomModAgevVO modAgevVO = getBeanUtil().transform(modalitaPrecedente,
						PbandiRContoEconomModAgevVO.class);
				getBeanUtil().copyNotNullValues(modalitaDiAgevolazioneDTO, modAgevVO, map);
				modAgevVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(modAgevVO);

			} else if (NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), 0d) > 0) {
				PbandiRContoEconomModAgevVO nuovaModalita = new PbandiRContoEconomModAgevVO();
				nuovaModalita.setIdContoEconomico(idContoEconomico);

				Map<String, String> mapDTOtoVO = new HashMap<String, String>();
				mapDTOtoVO.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
				mapDTOtoVO.put("importoRichiestoNuovo", "quotaImportoRichiesto");
				mapDTOtoVO.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

				getBeanUtil().valueCopy(modalitaDiAgevolazioneDTO, nuovaModalita, mapDTOtoVO);

				nuovaModalita.setIdUtenteIns(new BigDecimal(idUtente));
				nuovaModalita.setPercImportoAgevolato(new BigDecimal(0));

				genericDAO.insert(nuovaModalita);
			}
		}
	}


	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	// METODI VUOTI CREATI PER MOTIVI DI COMPILAZIONE.
	// CORRISPONDONO A METODI PRESENTI NELLA VECCHIA VERSIONE, MA NON USATI IN
	// ContoEconomicoDAOImpl.
	// I METODI ORIGINALI SONO PIU' SOTTO, COMMENTATI.

	@Override
	public ContoEconomicoDTO findDatiContoEconomico(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EsitoScaricaPropostaDTO scaricaProposta(Long idUtente, String identitaDigitale, Long idProgetto,
																								 Long idSoggetto, Long idContoEconomico)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EsitoFindQuadroPrevisionaleDTO findQuadroPrevisionale(Long idUtente, String identitaDigitale,
																															 Long idProgetto) throws CSIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EsitoSalvaQuadroPrevisionaleDTO salvaQuadroPrevisionale(Long idUtente, String identitaDigitale,
																																 QuadroPrevisionaleDTO quadroPrevizionaleDTO, IstanzaAttivitaDTO istanzaAttivitaDTO)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProceduraAggiudicazioneDTO findDettaglioProceduraAggiudicazione(Long idUtente, String identitaDigitale,
																																				 Long idProceduraAggiudicazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EsitoProceduraAggiudicazioneDTO inserisciProceduraAggiudicazione(Long idUtente, String identitaDigitale,
																																					ProceduraAggiudicazioneDTO proceduraAggiudicazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EsitoProceduraAggiudicazioneDTO modificaProceduraAggiudicazione(Long idUtente, String identitaDigitale,
																																				 ProceduraAggiudicazioneDTO proceduraAggiudicazione)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContoEconomicoDTO findDatiContoEconomicoComplessivo(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContoEconomicoDTO findDatiContoEconomicoPartner(Long idUtente, String identitaDigitale, Long idProgetto,
																												 Long idSoggettoPartner)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean aggiornaFonteFinanziaria(Long idUtente, String identitaDigitale,
																					FontiFinanziarieDTO fonteFinanziaria, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProceduraAggiudicazioneDTO[] findTutteProcedureAggiudicazionePerProgetto(Long idUtente,
																																									String identitaDigitale, ProceduraAggiudicazioneDTO filtro)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContoEconomicoDTO findDatiContoEconomicoCultura(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RigaContoEconomicoCulturaDTO[] findVociDiEntrataCultura(Long idUtente, String identitaDigitale,
																																 Long idProgetto) throws CSIException {
		// TODO Auto-generated method stub
		return null;
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/*
	 * private BeanUtil beanUtil;
	 *
	 * private DocumentoManager documentoManager; //private IndexDAO indexDAO;
	 * private NeofluxSrv neofluxBusiness; private PopolaTemplateManager
	 * popolaTemplateManager; private PbandiArchivioFileDAOImpl
	 * pbandiArchivioFileDAOImpl; private ProgettoManager progettoManager; private
	 * RappresentanteLegaleManager rappresentanteLegaleManager; private
	 * ReportManager reportManager; private SedeManager sedeManager; private
	 * SoggettoManager soggettoManager;
	 *
	 *
	 * public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() { return
	 * pbandiArchivioFileDAOImpl; }
	 *
	 * public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl
	 * pbandiArchivioFileDAOImpl) { this.pbandiArchivioFileDAOImpl =
	 * pbandiArchivioFileDAOImpl; }
	 *
	 *
	 * public NeofluxSrv getNeofluxBusiness() { return neofluxBusiness; }
	 *
	 * public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
	 * this.neofluxBusiness = neofluxBusiness; } public void
	 * setSedeManager(SedeManager sedeManager) { this.sedeManager = sedeManager; }
	 *
	 * public SedeManager getSedeManager() { return sedeManager; }
	 *
	 * public void setRappresentanteLegaleManager( RappresentanteLegaleManager
	 * rappresentanteLegaleManager) { this.rappresentanteLegaleManager =
	 * rappresentanteLegaleManager; }
	 *
	 * public RappresentanteLegaleManager getRappresentanteLegaleManager() { return
	 * rappresentanteLegaleManager; }
	 *
	 * public BeanUtil getBeanUtil() { return beanUtil; }
	 *
	 * public void setPopolaTemplateManager(PopolaTemplateManager
	 * popolaTemplateManager) { this.popolaTemplateManager = popolaTemplateManager;
	 * }
	 *
	 * public PopolaTemplateManager getPopolaTemplateManager() { return
	 * popolaTemplateManager; }
	 *
	 * public void setBeanUtil(BeanUtil beanUtil) { this.beanUtil = beanUtil; }
	 *
	 *
	 *
	 * public void setContoEconomicoManager( ContoEconomicoManager
	 * contoEconomicoManager) { this.contoEconomicoManager = contoEconomicoManager;
	 * }
	 *
	 * public ContoEconomicoManager getContoEconomicoManager() { return
	 * contoEconomicoManager; }
	 *
	 * public void setReportManager(ReportManager reportManager) {
	 * this.reportManager = reportManager; }
	 *
	 * public ReportManager getReportManager() { return reportManager; }
	 *
	 * public void setDocumentoManager(DocumentoManager documentoManager) {
	 * this.documentoManager = documentoManager; }
	 *
	 * public DocumentoManager getDocumentoManager() { return documentoManager; }
	 *
	 * public ContoEconomicoDTO findDatiContoEconomico(Long idUtente, String
	 * identitaDigitale, Long idProgetto) throws CSIException, SystemException,
	 * UnrecoverableException, ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProgetto);
	 *
	 * try { it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO
	 * contoEconomicoMaster = contoEconomicoManager
	 * .findContoEconomicoMasterPotato(new BigDecimal(idProgetto));
	 *
	 * ContoEconomicoDTO contoEconomicoDTO =
	 * convertiContoEconomicoMaster(contoEconomicoMaster);
	 *
	 * return contoEconomicoDTO;
	 *
	 * } catch (Exception e) { throw new UnrecoverableException(e.getMessage(), e);
	 * } }
	 *
	 * // Modifiche: // ContoEconomicoDTO: idTipologiaVoceDiSpesa. percSpGenFunz
	 *
	 * public ContoEconomicoDTO findDatiContoEconomicoCultura(Long idUtente, String
	 * identitaDigitale, Long idProgetto) throws CSIException, SystemException,
	 * UnrecoverableException, ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProgetto);
	 *
	 * try { it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO
	 * contoEconomicoMaster = contoEconomicoManager
	 * .findContoEconomicoMasterPotatoCultura(new BigDecimal(idProgetto));
	 *
	 * ContoEconomicoDTO contoEconomicoDTO =
	 * convertiContoEconomicoMaster(contoEconomicoMaster);
	 *
	 * return contoEconomicoDTO;
	 *
	 * } catch (Exception e) { throw new UnrecoverableException(e.getMessage(), e);
	 * } }
	 *
	 * private ContoEconomicoDTO convertiContoEconomicoMaster( RigoContoEconomicoDTO
	 * rigo) { ContoEconomicoDTO contoEconomicoDTO;
	 *
	 * contoEconomicoDTO = getBeanUtil().transform(rigo, ContoEconomicoDTO.class,
	 * mapVisualizzazioneMaster);
	 *
	 * List<RigoContoEconomicoDTO> figli = rigo.getFigli();
	 *
	 * ContoEconomicoDTO[] figliTrasformati = null; if (figli != null &&
	 * figli.size() > 0) { List<ContoEconomicoDTO> listaTrasformati = new
	 * ArrayList<ContoEconomicoDTO>(); for (RigoContoEconomicoDTO figlioCorrente :
	 * figli) { ContoEconomicoDTO figlioTrasformato =
	 * convertiContoEconomicoMaster(figlioCorrente);
	 *
	 * listaTrasformati.add(figlioTrasformato); } figliTrasformati =
	 * listaTrasformati .toArray(new ContoEconomicoDTO[listaTrasformati.size()]); }
	 * contoEconomicoDTO.setFigli(figliTrasformati);
	 *
	 * contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil
	 * .toDouble(NumberUtil.percentage(rigo.getImportoQuietanzato(),
	 * rigo.getImportoAmmesso())));
	 * contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil
	 * .toDouble(NumberUtil.percentage(rigo.getImportoValidato(),
	 * rigo.getImportoAmmesso())));
	 *
	 * return contoEconomicoDTO; }
	 *
	 * private ContoEconomicoRimodulazioneDTO
	 * convertiContoEconomicoPerRimodulazione( List<String> tipiDiConti, Map<String,
	 * it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>
	 * contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
	 *
	 * Map<String, RigoContoEconomicoDTO> righiContiEconomici = new HashMap<String,
	 * RigoContoEconomicoDTO>( contiEconomici.size()); for (String key :
	 * contiEconomici.keySet()) { righiContiEconomici.put(key,
	 * contiEconomici.get(key)); } ContoEconomicoRimodulazioneDTO
	 * contoEconomicoConvertito = convertiFigliContoEconomicoPerRimodulazione(
	 * tipiDiConti, righiContiEconomici, rigoConto);
	 *
	 * for (String tipoDiConto : tipiDiConti) {
	 * it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO
	 * contoEconomicoCaricato = contiEconomici .get(tipoDiConto);
	 * contoEconomicoConvertito.setFlagRibassoAsta(contoEconomicoCaricato
	 * .getFlagRibassoAsta()); }
	 *
	 * return contoEconomicoConvertito; }
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 * // -importoMassimoAgevolazione -percentualeImportoMassimoAgevolazione //
	 * -ultimoImportoRichiesto -nuovoImportoRichiesto(vuoto,editabile dal // client)
	 * -percentualeNuovoImportoRichiesto(vuoto,editabile dal client) //
	 * -importoGiaErogato -ultimoImportoAgevolato //
	 * -percentualeUltimoImportoAgevolato
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 * private RimodulazioneReportDTO creaRimodulazioneReportDTO( Long
	 * idContoEconomico, EsitoFindContoEconomicoDTO esitoFindContoEconomico,
	 * DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazione, Long
	 * idSoggetto, Date currentDate) {
	 *
	 * // OK ProgettoBandoLineaVO progettoBandoLinea =
	 * getBandoLinea(datiPerConclusioneRimodulazione
	 * .getProgetto().getIdProgetto());
	 *
	 * // OK EnteAppartenenzaDTO enteAppartenenza = findEnteAppartenenza(
	 * datiPerConclusioneRimodulazione.getProgetto().getIdProgetto(),
	 * Constants.CODICE_RUOLO_ENTE_DESTINATARIO);
	 *
	 * // OK BeneficiarioVO beneficiarioVO = pbandiDichiarazioneDiSpesaDAO
	 * .findBeneficiario(idSoggetto, datiPerConclusioneRimodulazione
	 * .getProgetto().getIdProgetto());
	 *
	 * String riferimento = datiPerConclusioneRimodulazione.getRiferimento(); if
	 * (ObjectUtil.isEmpty(riferimento)) riferimento = "Nessuno";
	 *
	 * String note = datiPerConclusioneRimodulazione.getNote(); if
	 * (ObjectUtil.isEmpty(note)) note = "Nessuna";
	 *
	 * String nomeFile = creaNomeFileRimodulazione(idContoEconomico, currentDate);
	 *
	 * // MAPPING RimodulazioneReportDTO rimodulazioneReportDTO = new
	 * RimodulazioneReportDTO();
	 * rimodulazioneReportDTO.setBeneficiario(beneficiarioVO
	 * .getDenominazioneBeneficiario());
	 * rimodulazioneReportDTO.setCodiceProgettoVisualizzato(progettoBandoLinea
	 * .getCodiceVisualizzato());
	 * rimodulazioneReportDTO.setCup(progettoBandoLinea.getCup());
	 * rimodulazioneReportDTO.setDataInvio(esitoFindContoEconomico
	 * .getDataUltimaRimodulazione());
	 * rimodulazioneReportDTO.setDataCreazioneDocumento(currentDate);
	 * rimodulazioneReportDTO.setDescBandoLineaIntervento(progettoBandoLinea
	 * .getDescrizioneBando());
	 * rimodulazioneReportDTO.setEnteAppartenenza(enteAppartenenza);
	 * rimodulazioneReportDTO.setIdContoEconomico(idContoEconomico);
	 * rimodulazioneReportDTO.setIdProgetto(datiPerConclusioneRimodulazione
	 * .getProgetto().getIdProgetto());
	 * rimodulazioneReportDTO.setNomeFile(nomeFile);
	 * rimodulazioneReportDTO.setNoteContoEconomico(note); //
	 * rimodulazioneReportDTO.setRappresentanteLegale(rappresentanteLegale);
	 * rimodulazioneReportDTO.setRiferimento(riferimento);
	 * rimodulazioneReportDTO.setTitoloProgetto(progettoBandoLinea
	 * .getTitoloProgetto());
	 *
	 * // VN: Verifico se la rimodulazione e' per ribasso d'asta
	 *
	 * if (datiPerConclusioneRimodulazione.getIdProceduraAggiudicazione() != null) {
	 * PbandiTRibassoAstaVO ribassoVO = new PbandiTRibassoAstaVO();
	 * ribassoVO.setIdContoEconomico(NumberUtil .toBigDecimal(idContoEconomico));
	 * ribassoVO = genericDAO.findSingleWhere(ribassoVO);
	 * rimodulazioneReportDTO.setPercRibassoAsta(NumberUtil
	 * .toDouble(ribassoVO.getPercentuale()));
	 *
	 * PbandiTProceduraAggiudicazVO proceduraVO = new
	 * PbandiTProceduraAggiudicazVO();
	 * proceduraVO.setIdProceduraAggiudicaz(NumberUtil
	 * .toBigDecimal(datiPerConclusioneRimodulazione
	 * .getIdProceduraAggiudicazione())); proceduraVO =
	 * genericDAO.findSingleWhere(proceduraVO);
	 *
	 * ProceduraAggiudicazioneDTO proceduraDTO = beanUtil.transform( proceduraVO,
	 * ProceduraAggiudicazioneDTO.class);
	 * rimodulazioneReportDTO.setProceduraAggiudicazione(proceduraDTO); } return
	 * rimodulazioneReportDTO; }
	 *
	 * private List<ContoEconomicoItemDTO> getContoEconomicoPerRimodulazione(
	 * EsitoFindContoEconomicoDTO esitoFindContoEconomico) {
	 * List<ContoEconomicoItemDTO> contoEconomicoItemList = contoEconomicoManager
	 * .mappaContoEconomicoPerReport( esitoFindContoEconomico.getContoEconomico(),
	 * true, esitoFindContoEconomico.getCopiaModificataPresente(), false,
	 * esitoFindContoEconomico.getDataFineIstruttoria(),
	 * esitoFindContoEconomico.getInIstruttoria()); return contoEconomicoItemList; }
	 *
	 *
	 *
	 *
	 *
	 *
	 * // PROPOSTA RIMODULAZIONE RIMANE COSI COM'E' // OK
	 *
	 *
	 *
	 *
	 *
	 *
	 * // solo insert
	 *
	 *
	 * public void setGenericDAO(GenericDAO genericDAO) { this.genericDAO =
	 * genericDAO; }
	 *
	 * public GenericDAO getGenericDAO() { return genericDAO; }
	 *
	 * private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	 *
	 * public void setPbandiDichiarazioneDiSpesaDAO(
	 * PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
	 * this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO; }
	 *
	 * public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
	 * return pbandiDichiarazioneDiSpesaDAO; }
	 *
	 * public EsitoScaricaPropostaDTO scaricaProposta(Long idUtente, String
	 * identitaDigitale, Long idProgetto, Long idSoggetto, Long idContoEconomico)
	 * throws CSIException, SystemException, UnrecoverableException,
	 * ContoEconomicoException {
	 *
	 * EsitoScaricaPropostaDTO esitoScaricaPropostaDTO = new
	 * EsitoScaricaPropostaDTO();
	 *
	 * byte bytesPdf[] = null; String nomeFile = null; try {
	 * logger.info("idProgetto : " + idProgetto + " , idSoggetto : " + idSoggetto);
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto",
	 * "idSoggetto" };
	 *
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProgetto, idSoggetto);
	 *
	 * ProgettoDTO progetto = new ProgettoDTO(); progetto.setIdProgetto(idProgetto);
	 *
	 * // ID CONTO ECONOMICO DEV'ESSERE QUELLO DELLA LOCAL COPY
	 * PbandiTDocumentoIndexVO documentoIndexVO = documentoManager
	 * .getDocumentoIndexSuDb( idContoEconomico, idProgetto,
	 * GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE, new
	 * PbandiTContoEconomicoVO() .getTableNameForValueObject());
	 *
	 * if (!isNull(documentoIndexVO)) {
	 *
	 * bytesPdf = indexDAO.recuperaContenuto(documentoIndexVO .getUuidNodo());
	 *
	 * nomeFile = documentoIndexVO.getNomeFile();
	 *
	 * } else {
	 *
	 * logger.
	 * warn("\n\n\n\nERRORE GRAVE!!! documento di proposta rimodulazione non presente su index !!!\n\n\n"
	 * ); throw new ContoEconomicoException("");
	 *
	 * }
	 *
	 * esitoScaricaPropostaDTO.setBytesPdf(bytesPdf);
	 *
	 * esitoScaricaPropostaDTO.setNomeFile(nomeFile);
	 *
	 * } catch (Exception e) { logger.error(e.getMessage(), e); throw new
	 * UnrecoverableException(e.getMessage(), e); }
	 *
	 * return esitoScaricaPropostaDTO; }
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 * public void setIndexDAO(IndexDAO indexDAO) { this.indexDAO = indexDAO; }
	 *
	 * public IndexDAO getIndexDAO() { return indexDAO; }
	 *
	 * public void setSoggettoManager(SoggettoManager soggettoManager) {
	 * this.soggettoManager = soggettoManager; }
	 *
	 * public SoggettoManager getSoggettoManager() { return soggettoManager; }
	 *
	 * // -importoMassimoAgevolazione -percentualeImportoMassimoAgevolazione //
	 * -nuovoImportoRichiesto -percentualeNuovoImportoRichiesto
	 *
	 *
	 * public EsitoFindQuadroPrevisionaleDTO findQuadroPrevisionale(Long idUtente,
	 * String identitaDigitale, Long idProgetto) throws CSIException,
	 * SystemException, UnrecoverableException, ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "progetto",
	 * "idProgetto" };
	 *
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProgetto);
	 *
	 * EsitoFindQuadroPrevisionaleDTO esito = new EsitoFindQuadroPrevisionaleDTO();
	 * try { // periodo iniziale=data presentazione domanda // periodo finale = il
	 * default � data presentazione domanda + 2 (es // 2008 - 2010) // periodo
	 * finale = data effettiva chiusura progetto indicata nelle // fasi monitoraggio
	 *
	 * Set<String> periodi = calcolaPeriodi(idProgetto);
	 *
	 * VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmessoDB =
	 * getVociDiSpesaConAmmesso(idProgetto); logger.info("trovati " +
	 * vociDiSpesaConAmmessoDB.length + " voci con ammesso");
	 *
	 * Date dtAmmessaUltima = getDataAmmessaUltima(idProgetto);
	 * esito.setDataUltimaSpesaAmmessa(dtAmmessaUltima);
	 *
	 * QuadroPrevisionaleDTO quadroPrevisionaleDTO = getQuadroPrevisionale(
	 * idProgetto, esito);
	 *
	 * List<QuadroPrevisionaleDTO> vociDiSpesaPerPeriodo =
	 * clonaVociDiSpesaConAmmessoPerPeriodo( periodi, vociDiSpesaConAmmessoDB);
	 *
	 * VoceDiSpesaConRealizzatoVO[] vociDiSpesaConRealizzato =
	 * getVociDiSpesaConRealizzato(idProgetto);
	 *
	 * if (!isEmpty(vociDiSpesaConRealizzato)) {
	 * mappaVociDispesaConRealizzato(vociDiSpesaPerPeriodo,
	 * vociDiSpesaConRealizzato); }
	 *
	 * List<QuadroPrevisionaleDTO> righiQuadroPrevisionaleDB = null;
	 * righiQuadroPrevisionaleDB = getRighiQuadroPrevisionale(idProgetto);
	 * logger.info("trovati " + righiQuadroPrevisionaleDB.size() + " righi quadro");
	 *
	 * if (!isEmpty(righiQuadroPrevisionaleDB)) {
	 * mappaRighiQuadroPrevisionale(vociDiSpesaPerPeriodo,
	 * righiQuadroPrevisionaleDB); }
	 *
	 * quadroPrevisionaleDTO.setFigli(vociDiSpesaPerPeriodo .toArray(new
	 * QuadroPrevisionaleDTO[vociDiSpesaPerPeriodo .size()]));
	 *
	 * setImportiPerMacroVoce(quadroPrevisionaleDTO);
	 *
	 * aggiungiFigliPeriodi(quadroPrevisionaleDTO, periodi);
	 *
	 * setImportoDaRealizzare(quadroPrevisionaleDTO);
	 *
	 * List<QuadroPrevisionaleComplessivoDTO> quadroPrevisionaleComplessivoDTO =
	 * mappaQuadroComplessivo( quadroPrevisionaleDTO, vociDiSpesaConAmmessoDB);
	 *
	 * esito.setQuadroPrevisionale(quadroPrevisionaleDTO);
	 *
	 * esito.setQuadroPrevisionaleComplessivo(quadroPrevisionaleComplessivoDTO
	 * .toArray(new QuadroPrevisionaleComplessivoDTO[0]));
	 *
	 * esito.setIsVociVisibili(isVociVisibili(idProgetto));
	 *
	 * esito.setIsControlloNuovoImportoBloccante(isControlloNuovoImportoBloccante(
	 * idProgetto));
	 *
	 * return esito;
	 *
	 * } catch (Exception e) { logger.error(e.getMessage(), e); throw new
	 * UnrecoverableException(e.getMessage(), e); } }
	 *
	 * private Set<String> calcolaPeriodi(Long idProgetto) throws Exception {
	 * PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO( new
	 * BigDecimal(idProgetto)); pbandiTProgettoVO =
	 * genericDAO.findSingleWhere(pbandiTProgettoVO); PbandiTDomandaVO
	 * pbandiTDomandaVO = new PbandiTDomandaVO();
	 * pbandiTDomandaVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
	 * pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO); Date
	 * dataInizio = pbandiTDomandaVO.getDtPresentazioneDomanda(); int annoInizio =
	 * DateUtil.getYear(dataInizio); int annoFine = annoInizio + 2;// default int
	 * annoChiusuraProgetto = getDataChiusuraProgettoFasiMonitoraggio(idProgetto);
	 * if (annoChiusuraProgetto > annoFine) annoFine = annoChiusuraProgetto;
	 * Set<String> periodi = new LinkedHashSet<String>(); int currentYear =
	 * DateUtil.getAnno(); if (annoFine < currentYear) annoFine = currentYear; while
	 * (annoInizio <= annoFine) { periodi.add(Integer.toString(annoInizio));
	 * annoInizio++; }
	 *
	 * return periodi; }
	 *
	 * private void mappaVociDispesaConRealizzato( List<QuadroPrevisionaleDTO>
	 * vociDiSpesaPerPeriodo, VoceDiSpesaConRealizzatoVO[] vociDiSpesaConRealizzato)
	 * { List<VoceDiSpesaConRealizzatoVO> listVociDiSpesaConRealizzato = Arrays
	 * .asList(vociDiSpesaConRealizzato); for (QuadroPrevisionaleDTO
	 * voceDiSpesaConAmmesso : vociDiSpesaPerPeriodo) { for
	 * (VoceDiSpesaConRealizzatoVO voceDiSpesaConRealizzato :
	 * listVociDiSpesaConRealizzato) {
	 *
	 * if (voceDiSpesaConAmmesso.getIdVoce().longValue() == voceDiSpesaConRealizzato
	 * .getIdVoceDiSpesa().longValue() && voceDiSpesaConAmmesso.getPeriodo().equals(
	 * voceDiSpesaConRealizzato.getPeriodo())) { voceDiSpesaConAmmesso
	 * .setImportoRealizzato(NumberUtil .toDouble(voceDiSpesaConRealizzato
	 * .getRealizzato())); break; }
	 *
	 * } } }
	 *
	 * private QuadroPrevisionaleDTO getQuadroPrevisionale(Long idProgetto,
	 * EsitoFindQuadroPrevisionaleDTO esito) { QuadroPrevisionaleDTO
	 * quadroPrevisionaleDTO = new QuadroPrevisionaleDTO(); try {
	 * PbandiTQuadroPrevisionaleVO quadroPrevisionaleVO = null;
	 * PbandiTQuadroPrevisionaleVO filtro = new PbandiTQuadroPrevisionaleVO();
	 * filtro.setIdProgetto(new BigDecimal(idProgetto));
	 * Condition<PbandiTQuadroPrevisionaleVO> conditionDataFineNull = new
	 * AndCondition<PbandiTQuadroPrevisionaleVO>( new
	 * FilterCondition<PbandiTQuadroPrevisionaleVO>(filtro),
	 * Condition.validOnly(PbandiTQuadroPrevisionaleVO.class)); quadroPrevisionaleVO
	 * = genericDAO .findSingleWhere(conditionDataFineNull); if
	 * (quadroPrevisionaleVO != null) {
	 * quadroPrevisionaleDTO.setIdProgetto(quadroPrevisionaleVO
	 * .getIdProgetto().longValue());
	 *
	 * quadroPrevisionaleDTO.setIdQuadro(quadroPrevisionaleVO
	 * .getIdQuadroPrevisionale().longValue());
	 *
	 * quadroPrevisionaleDTO.setNote(quadroPrevisionaleVO.getNote());
	 *
	 * esito.setDataUltimoPreventivo(quadroPrevisionaleVO .getDtAggiornamento()); }
	 * } catch (Exception e) { logger.
	 * debug("non � stato salvato ancora nessun quadro previsionale per il progetto "
	 * + idProgetto); } return quadroPrevisionaleDTO; }
	 *
	 * private Date getDataAmmessaUltima(Long idProgetto) throws Exception {
	 * ProgettoDTO progetto = new ProgettoDTO(); progetto.setIdProgetto(idProgetto);
	 * ContoEconomicoMaxDataFineVO datiUltimaRimodulazione =
	 * getDatiUltimaRimodulazione(progetto); Date dataUltimaRimodulazione = null; if
	 * (datiUltimaRimodulazione != null) { dataUltimaRimodulazione =
	 * datiUltimaRimodulazione .getDtFineValidita(); } else {
	 * ContoEconomicoMaxDataFineVO datiUltimaIstruttoria =
	 * getDatiUltimaIstruttoria(progetto); dataUltimaRimodulazione =
	 * datiUltimaIstruttoria.getDtFineValidita(); } return dataUltimaRimodulazione;
	 * }
	 *
	 * private void mappaRighiQuadroPrevisionale( List<QuadroPrevisionaleDTO>
	 * vociDiSpesaPerPeriodo, List<QuadroPrevisionaleDTO> righiQuadroPrevisionaleDB)
	 * { for (QuadroPrevisionaleDTO voceDiSpesaConAmmesso : vociDiSpesaPerPeriodo) {
	 * for (QuadroPrevisionaleDTO rigoQuadroPrevisionaleDB :
	 * righiQuadroPrevisionaleDB) { if
	 * (voceDiSpesaConAmmesso.getIdVoce().longValue() == rigoQuadroPrevisionaleDB
	 * .getIdVoce().longValue() && voceDiSpesaConAmmesso.getPeriodo().equals(
	 * rigoQuadroPrevisionaleDB.getPeriodo())) { voceDiSpesaConAmmesso
	 * .setImportoPreventivo(rigoQuadroPrevisionaleDB .getImportoPreventivo());
	 * voceDiSpesaConAmmesso .setIdRigoQuadro(rigoQuadroPrevisionaleDB
	 * .getIdRigoQuadro()); break; } } } }
	 *
	 * private void aggiungiFigliPeriodi( QuadroPrevisionaleDTO
	 * quadroPrevisionaleDTO, Set<String> periodi) { List<QuadroPrevisionaleDTO>
	 * figliConPeriodi = new ArrayList<QuadroPrevisionaleDTO>(); Map<String,
	 * QuadroPrevisionaleDTO> mapPeriodi = new HashMap<String,
	 * QuadroPrevisionaleDTO>();
	 *
	 * // sommo solo le macrovoci! for (QuadroPrevisionaleDTO figlio :
	 * quadroPrevisionaleDTO.getFigli()) { if
	 * (mapPeriodi.containsKey(figlio.getPeriodo())) { if (figlio.getMacroVoce()) {
	 * QuadroPrevisionaleDTO periodo = mapPeriodi.get(figlio .getPeriodo()); Double
	 * preventivo = periodo.getImportoPreventivo(); Double realizzato =
	 * periodo.getImportoRealizzato(); Double spesaAmmessa =
	 * periodo.getImportoSpesaAmmessa(); if (!isNull(figlio.getImportoPreventivo()))
	 * { if (preventivo == null) preventivo = 0d;
	 * periodo.setImportoPreventivo(NumberUtil.sum(preventivo,
	 * figlio.getImportoPreventivo())); } if
	 * (!isNull(figlio.getImportoRealizzato())) { if (realizzato == null) realizzato
	 * = 0d; periodo.setImportoRealizzato(NumberUtil.sum(realizzato,
	 * figlio.getImportoRealizzato())); } if
	 * (!isNull(figlio.getImportoSpesaAmmessa())) { if (spesaAmmessa == null)
	 * spesaAmmessa = 0d; periodo.setImportoSpesaAmmessa(NumberUtil.sum(
	 * spesaAmmessa, figlio.getImportoSpesaAmmessa())); } } } else { if
	 * (figlio.getMacroVoce()) { QuadroPrevisionaleDTO figlioPeriodo = new
	 * QuadroPrevisionaleDTO(); figlioPeriodo.setImportoPreventivo(figlio
	 * .getImportoPreventivo()); figlioPeriodo.setImportoRealizzato(figlio
	 * .getImportoRealizzato()); figlioPeriodo.setImportoSpesaAmmessa(figlio
	 * .getImportoSpesaAmmessa());
	 *
	 * mapPeriodi.put(figlio.getPeriodo(), figlioPeriodo); } } }
	 *
	 * for (QuadroPrevisionaleDTO figlio : quadroPrevisionaleDTO.getFigli()) { if
	 * (periodi.contains(figlio.getPeriodo())) { QuadroPrevisionaleDTO periodo =
	 * mapPeriodi.get(figlio .getPeriodo());
	 * periodo.setDescVoce(figlio.getPeriodo());
	 * periodo.setPeriodo(figlio.getPeriodo()); periodo.setIsPeriodo(true);
	 * periodo.setMacroVoce(false); periodo.setHaFigli(false);
	 * figliConPeriodi.add(periodo); periodi.remove(figlio.getPeriodo()); }
	 * figlio.setIsPeriodo(false); figliConPeriodi.add(figlio); }
	 *
	 * quadroPrevisionaleDTO.setFigli(figliConPeriodi .toArray(new
	 * QuadroPrevisionaleDTO[figliConPeriodi.size()]));
	 *
	 * }
	 *
	 * private void setImportiPerMacroVoce( QuadroPrevisionaleDTO
	 * quadroPrevisionaleDTO) { QuadroPrevisionaleDTO[] figli =
	 * quadroPrevisionaleDTO.getFigli(); Map<String, Map<Long,
	 * QuadroPrevisionaleDTO>> periodoMacroVoci = new HashMap<String, Map<Long,
	 * QuadroPrevisionaleDTO>>(); for (QuadroPrevisionaleDTO figlio : figli) {
	 * figlio.setHaFigli(false); Long idVoce = figlio.getIdVocePadre(); if (idVoce
	 * == null) idVoce = figlio.getIdVoce();
	 *
	 * if (periodoMacroVoci.containsKey(figlio.getPeriodo())) { Map<Long,
	 * QuadroPrevisionaleDTO> macroVoci = periodoMacroVoci
	 * .get(figlio.getPeriodo()); if (macroVoci.containsKey(idVoce)) {
	 * QuadroPrevisionaleDTO macroVoce = macroVoci.get(idVoce); if
	 * (figlio.getIdVocePadre() != null) macroVoce.setHaFigli(true); Double
	 * preventivo = macroVoce.getImportoPreventivo(); Double realizzato =
	 * macroVoce.getImportoRealizzato(); Double spesaAmmessa =
	 * macroVoce.getImportoSpesaAmmessa(); if
	 * (!isNull(figlio.getImportoPreventivo())) { if (preventivo == null) preventivo
	 * = 0d; macroVoce.setImportoPreventivo(NumberUtil.sum( preventivo,
	 * figlio.getImportoPreventivo())); } if
	 * (!isNull(figlio.getImportoRealizzato())) { if (realizzato == null) realizzato
	 * = 0d; macroVoce.setImportoRealizzato(NumberUtil.sum( realizzato,
	 * figlio.getImportoRealizzato())); } if
	 * (!isNull(figlio.getImportoSpesaAmmessa())) { if (spesaAmmessa == null)
	 * spesaAmmessa = 0d; macroVoce.setImportoSpesaAmmessa(NumberUtil.sum(
	 * spesaAmmessa, figlio.getImportoSpesaAmmessa())); }
	 *
	 * } else { macroVoci.put(idVoce, figlio); } } else { Map<Long,
	 * QuadroPrevisionaleDTO> macroVoci = new HashMap<Long,
	 * QuadroPrevisionaleDTO>(); macroVoci.put(idVoce, figlio);
	 * periodoMacroVoci.put(figlio.getPeriodo(), macroVoci); } }
	 *
	 * }
	 *
	 * private List<QuadroPrevisionaleDTO> clonaVociDiSpesaConAmmessoPerPeriodo(
	 * Set<String> periodi, VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmessoDB) {
	 * List<QuadroPrevisionaleDTO> vociDiSpesaPerPeriodo = new
	 * ArrayList<QuadroPrevisionaleDTO>(); for (String periodo : periodi) { for
	 * (VoceDiSpesaConAmmessoVO voceConAmmesso : vociDiSpesaConAmmessoDB) {
	 * QuadroPrevisionaleDTO voce = new QuadroPrevisionaleDTO();
	 * voce.setDescVoce(voceConAmmesso.getDescVoceDiSpesa()); voce.setIdVoce(new
	 * Long(voceConAmmesso.getIdVoceDiSpesa() .longValue())); if
	 * (voceConAmmesso.getIdVoceDiSpesaPadre() != null) {
	 * voce.setIdVocePadre(voceConAmmesso.getIdVoceDiSpesaPadre() .longValue());
	 * voce.setMacroVoce(false); } else { voce.setMacroVoce(true); }
	 * voce.setPeriodo(periodo);
	 *
	 * vociDiSpesaPerPeriodo.add(voce); } } logger.debug("da " +
	 * vociDiSpesaConAmmessoDB.length + " vociDiSpesaConAmmessoDB ho creato " +
	 * (vociDiSpesaPerPeriodo == null ? 0 : vociDiSpesaPerPeriodo .size()) +
	 * " voci con periodo"); return vociDiSpesaPerPeriodo; }
	 *
	 * private int getDataChiusuraProgettoFasiMonitoraggio(Long idProgetto) throws
	 * Exception { int annoChiusura = 0; PbandiRProgettoFaseMonitVO
	 * pbandiRProgettoFaseMonitVO = new PbandiRProgettoFaseMonitVO();
	 * pbandiRProgettoFaseMonitVO.setIdProgetto(new BigDecimal(idProgetto));
	 * List<PbandiRProgettoFaseMonitVO> listFasiMonitoraggio = genericDAO
	 * .findListWhere(pbandiRProgettoFaseMonitVO); Date dataFineEffettiva = null;
	 * Date dataFinePrevista = null; for (PbandiRProgettoFaseMonitVO
	 * faseMonitoraggio : listFasiMonitoraggio) { if (dataFineEffettiva == null) {
	 * dataFineEffettiva = faseMonitoraggio.getDtFineEffettiva(); } else if
	 * (faseMonitoraggio.getDtFineEffettiva() != null &&
	 * faseMonitoraggio.getDtFineEffettiva().after( dataFineEffettiva)) {
	 * dataFineEffettiva = faseMonitoraggio.getDtFineEffettiva(); } if
	 * (dataFinePrevista == null) { dataFinePrevista =
	 * faseMonitoraggio.getDtFinePrevista(); } else if
	 * (faseMonitoraggio.getDtFinePrevista() != null &&
	 * faseMonitoraggio.getDtFinePrevista().after( dataFinePrevista)) {
	 * dataFinePrevista = faseMonitoraggio.getDtFinePrevista(); }
	 *
	 * } if (dataFineEffettiva != null) { annoChiusura =
	 * DateUtil.getYear(dataFineEffettiva); } else if (dataFinePrevista != null) {
	 * annoChiusura = DateUtil.getYear(dataFinePrevista); } return annoChiusura; }
	 *
	 * private VoceDiSpesaConAmmessoVO[] getVociDiSpesaConAmmesso(Long idProgetto) {
	 * VoceDiSpesaConAmmessoVO voceDiSpesaConAmmesso = new
	 * VoceDiSpesaConAmmessoVO(); voceDiSpesaConAmmesso.setIdProgetto(new
	 * BigDecimal(idProgetto)); VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmesso =
	 * genericDAO .findWhere(voceDiSpesaConAmmesso); return vociDiSpesaConAmmesso; }
	 *
	 * private void setImportoDaRealizzare( QuadroPrevisionaleDTO
	 * quadroPrevisionaleDTO) {
	 *
	 * for (QuadroPrevisionaleDTO figlio : quadroPrevisionaleDTO.getFigli()) {
	 * Double preventivo = figlio.getImportoPreventivo(); Double realizzato =
	 * figlio.getImportoRealizzato(); if (preventivo == null) preventivo = 0d; if
	 * (realizzato == null) realizzato = 0d; Double daRealizzare =
	 * NumberUtil.toRoundDouble(preventivo - realizzato);
	 * figlio.setImportoDaRealizzare(daRealizzare); }
	 *
	 * }
	 *
	 * private ArrayList<QuadroPrevisionaleComplessivoDTO> mappaQuadroComplessivo(
	 * QuadroPrevisionaleDTO quadroPrevisionaleDTO, VoceDiSpesaConAmmessoVO[]
	 * vociDiSpesaConAmmessoDB) { Map<Long, QuadroPrevisionaleComplessivoDTO>
	 * mapQuadroPrevisionaleComplessivo = new LinkedHashMap<Long,
	 * QuadroPrevisionaleComplessivoDTO>(); Set<Long> macroVociConFigli = new
	 * HashSet<Long>();
	 *
	 * creaQuadroComplessivo(quadroPrevisionaleDTO,
	 * mapQuadroPrevisionaleComplessivo, macroVociConFigli);
	 *
	 * ArrayList<QuadroPrevisionaleComplessivoDTO> list = new
	 * ArrayList<QuadroPrevisionaleComplessivoDTO>(
	 * mapQuadroPrevisionaleComplessivo.values());
	 *
	 * Map<Long, QuadroPrevisionaleComplessivoDTO> mapMacroVoci = new HashMap<Long,
	 * QuadroPrevisionaleComplessivoDTO>();
	 *
	 * for (QuadroPrevisionaleComplessivoDTO figlio : list) {
	 *
	 * Double newSpesaAmmessa = null; for (VoceDiSpesaConAmmessoVO
	 * voceDiSpesaConAmmessoDB : vociDiSpesaConAmmessoDB) { if
	 * (figlio.getIdVoce().longValue() == voceDiSpesaConAmmessoDB
	 * .getIdVoceDiSpesa().longValue()) { newSpesaAmmessa = NumberUtil
	 * .toDouble(voceDiSpesaConAmmessoDB .getUltimaSpesaAmmessa()); break; } } if
	 * (newSpesaAmmessa == null) newSpesaAmmessa = 0d;
	 * figlio.setSpesaAmmessa(newSpesaAmmessa);
	 *
	 * Long idVoce = figlio.getIdVocePadre(); if (idVoce == null) idVoce =
	 * figlio.getIdVoce(); if (mapMacroVoci.containsKey(idVoce)) {
	 * QuadroPrevisionaleComplessivoDTO macroVoce = mapMacroVoci .get(idVoce);
	 * Double spesaAmmessa = figlio.getSpesaAmmessa(); Double oldSpesaAmmessa =
	 * macroVoce.getSpesaAmmessa(); if (oldSpesaAmmessa == null) oldSpesaAmmessa =
	 * 0d;
	 *
	 * if (spesaAmmessa != null) { spesaAmmessa = NumberUtil .sum(oldSpesaAmmessa,
	 * spesaAmmessa); macroVoce.setSpesaAmmessa(spesaAmmessa); } } else {
	 * mapMacroVoci.put(idVoce, figlio); }
	 *
	 * }
	 *
	 * setVoceQuadroComplessivoHaFigli(macroVociConFigli, list, mapMacroVoci);
	 *
	 *
	 * return list; }
	 *
	 * private void creaQuadroComplessivo( QuadroPrevisionaleDTO
	 * quadroPrevisionaleDTO, Map<Long, QuadroPrevisionaleComplessivoDTO>
	 * mapQuadroPrevisionaleComplessivo, Set<Long> macroVociConFigli) {
	 * QuadroPrevisionaleDTO figli[] = quadroPrevisionaleDTO.getFigli(); for
	 * (QuadroPrevisionaleDTO figlio : figli) { QuadroPrevisionaleComplessivoDTO
	 * quadroPrevisionaleComplessivoDTO = null;
	 *
	 * if (figlio.getIdVoce() != null) { if (figlio.getIdVocePadre() != null) {
	 * macroVociConFigli.add(figlio.getIdVocePadre()); } if
	 * (mapQuadroPrevisionaleComplessivo.containsKey(figlio .getIdVoce())) {
	 *
	 * Double currentRealizzato = figlio.getImportoRealizzato(); if
	 * (currentRealizzato == null) currentRealizzato = 0d; Double currentPreventivo
	 * = figlio.getImportoPreventivo(); if (currentPreventivo == null)
	 * currentPreventivo = 0d;
	 *
	 * quadroPrevisionaleComplessivoDTO = mapQuadroPrevisionaleComplessivo
	 * .get(figlio.getIdVoce());
	 *
	 * Double realizzatoComplessivo = quadroPrevisionaleComplessivoDTO
	 * .getRealizzato(); realizzatoComplessivo = NumberUtil.sum(
	 * realizzatoComplessivo, currentRealizzato);
	 *
	 * quadroPrevisionaleComplessivoDTO .setRealizzato(realizzatoComplessivo);
	 *
	 * Double preventivoComplessivo = quadroPrevisionaleComplessivoDTO
	 * .getPreventivo();
	 *
	 * preventivoComplessivo = NumberUtil.sum( preventivoComplessivo,
	 * currentPreventivo); quadroPrevisionaleComplessivoDTO
	 * .setPreventivo(preventivoComplessivo);
	 *
	 * Double daRealizzare = NumberUtil .toRoundDouble(preventivoComplessivo -
	 * realizzatoComplessivo); quadroPrevisionaleComplessivoDTO
	 * .setDaRealizzare(daRealizzare);
	 *
	 *
	 *
	 * } else { quadroPrevisionaleComplessivoDTO = new
	 * QuadroPrevisionaleComplessivoDTO();
	 * quadroPrevisionaleComplessivoDTO.setDescVoce(figlio .getDescVoce());
	 * quadroPrevisionaleComplessivoDTO.setIdVoce(figlio .getIdVoce());
	 * quadroPrevisionaleComplessivoDTO.setMacroVoce(figlio .getIdVocePadre() ==
	 * null); quadroPrevisionaleComplessivoDTO.setIdVocePadre(figlio
	 * .getIdVocePadre());
	 *
	 * Double preventivo = figlio.getImportoPreventivo(); Double realizzato =
	 * figlio.getImportoRealizzato(); if (preventivo == null) preventivo = 0d; if
	 * (realizzato == null) realizzato = 0d; Double daRealizzare =
	 * NumberUtil.toRoundDouble(preventivo - realizzato);
	 * quadroPrevisionaleComplessivoDTO .setDaRealizzare(daRealizzare);
	 * quadroPrevisionaleComplessivoDTO.setRealizzato(realizzato);
	 * quadroPrevisionaleComplessivoDTO.setPreventivo(preventivo);
	 *
	 *
	 * } mapQuadroPrevisionaleComplessivo.put(figlio.getIdVoce(),
	 * quadroPrevisionaleComplessivoDTO); } } }
	 *
	 * private void setVoceQuadroComplessivoHaFigli(Set<Long> macroVociConFigli,
	 * ArrayList<QuadroPrevisionaleComplessivoDTO> list, Map<Long,
	 * QuadroPrevisionaleComplessivoDTO> mapMacroVoci) { for
	 * (QuadroPrevisionaleComplessivoDTO figlio : list) {
	 *
	 * figlio.setHaFigli(false); if (mapMacroVoci.containsKey(figlio.getIdVoce())) {
	 * figlio = mapMacroVoci.get(figlio.getIdVoce()); if
	 * (macroVociConFigli.contains(figlio.getIdVoce())) { figlio.setHaFigli(true); }
	 * else { figlio.setHaFigli(false); } } } }
	 *
	 * private void setHasFigli( ArrayList<QuadroPrevisionaleDTO> figli, Map<String,
	 * Map<Long, String>> mapMacroVociPeriodo, Map<String, Map<Long,
	 * QuadroPrevisionaleDTO>> mapRealizzatoMacroVocePeriodo) {
	 *
	 * for (QuadroPrevisionaleDTO figlio : figli) { figlio.setHaFigli(false); }
	 * Set<Entry<String, Map<Long, String>>> setMacroVociPeriodo =
	 * mapMacroVociPeriodo .entrySet(); for (Entry<String, Map<Long, String>>
	 * entryPeriodo : setMacroVociPeriodo) { String periodo = entryPeriodo.getKey();
	 * Map<Long, String> mapPeriodo = entryPeriodo.getValue(); Set<Entry<Long,
	 * String>> macroVoci = mapPeriodo.entrySet(); for (Entry<Long, String>
	 * entryMacroVoce : macroVoci) { Long idPadre = entryMacroVoce.getKey(); for
	 * (QuadroPrevisionaleDTO figlio : figli) { if
	 * (periodo.equals(figlio.getPeriodo()) && idPadre != null && idPadre ==
	 * figlio.getIdVocePadre()) { Map<Long, QuadroPrevisionaleDTO> map =
	 * mapRealizzatoMacroVocePeriodo .get(periodo); QuadroPrevisionaleDTO
	 * macroVoceConFigli = map .get(idPadre); macroVoceConFigli.setHaFigli(true); }
	 *
	 * } } } }
	 *
	 * private VoceDiSpesaConRealizzatoVO[] getVociDiSpesaConRealizzato( Long
	 * idProgetto) { VoceDiSpesaConRealizzatoVO voceDiSpesaConRealizzatoVO = new
	 * VoceDiSpesaConRealizzatoVO();
	 * voceDiSpesaConRealizzatoVO.setIdProgetto(idProgetto);
	 * VoceDiSpesaConRealizzatoVO[] vociDiSpesaConRealizzato = genericDAO
	 * .findWhere(voceDiSpesaConRealizzatoVO); logger.debug("trovati " +
	 * (vociDiSpesaConRealizzato == null ? 0 : vociDiSpesaConRealizzato.length) +
	 * " voci con realizzato"); return vociDiSpesaConRealizzato; }
	 *
	 * private List<QuadroPrevisionaleDTO> getRighiQuadroPrevisionale( Long
	 * idProgetto) { PbandiTRigoQuadroPrevisioVO pbandiTRigoQuadroPrevisioVO = new
	 * PbandiTRigoQuadroPrevisioVO(); pbandiTRigoQuadroPrevisioVO.setIdProgetto(new
	 * BigDecimal(idProgetto)); List<QuadroPrevisionaleDTO> figli = new
	 * ArrayList<QuadroPrevisionaleDTO>(); Condition<PbandiTRigoQuadroPrevisioVO>
	 * filtroDataFineNull = new AndCondition<PbandiTRigoQuadroPrevisioVO>( new
	 * FilterCondition<PbandiTRigoQuadroPrevisioVO>( pbandiTRigoQuadroPrevisioVO),
	 * Condition.validOnly(PbandiTRigoQuadroPrevisioVO.class));
	 *
	 * PbandiTRigoQuadroPrevisioVO righiDb[] = genericDAO
	 * .findWhere(filtroDataFineNull); Map<String, String> mapPropsToCopy = new
	 * HashMap<String, String>(); mapPropsToCopy.put("idRigoQuadroPrevisio",
	 * "idRigoQuadro"); mapPropsToCopy.put("idVoceDiSpesa", "idVoce");
	 * mapPropsToCopy.put("importoPreventivo", "importoPreventivo");
	 * mapPropsToCopy.put("periodo", "periodo");
	 *
	 * for (PbandiTRigoQuadroPrevisioVO rigoQuadroDb : righiDb) {
	 * QuadroPrevisionaleDTO rigoFiglio = new QuadroPrevisionaleDTO();
	 * getBeanUtil().valueCopy(rigoQuadroDb, rigoFiglio, mapPropsToCopy);
	 * figli.add(rigoFiglio); } return figli; }
	 *
	 * private boolean isVociVisibili(Long idProgetto) throws
	 * FormalParameterException { // if il bandolinea legato al progetto ha la
	 * regola return regolaManager.isRegolaApplicabileForProgetto(idProgetto,
	 * RegoleConstants.BR31_VOCI_VISIBILI_PER_QUADRO_PREVISIONALE); }
	 *
	 * private boolean isControlloNuovoImportoBloccante(Long idProgetto) throws
	 * FormalParameterException { return
	 * regolaManager.isRegolaApplicabileForProgetto(idProgetto,
	 * RegoleConstants.BR32_CONTROLLO_NUOVO_PREVENTIVO_BLOCCANTE); }
	 *
	 * public EsitoSalvaQuadroPrevisionaleDTO salvaQuadroPrevisionale( Long
	 * idUtente, String identitaDigitale, QuadroPrevisionaleDTO
	 * quadroPrevizionaleDTO, IstanzaAttivitaDTO istanzaAttivitaDTO) throws
	 * CSIException, SystemException, UnrecoverableException,
	 * ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale",
	 * "quadroPrevizionaleDTO", "istanzaAttivitaDTO" };
	 *
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * quadroPrevizionaleDTO, istanzaAttivitaDTO);
	 *
	 * EsitoSalvaQuadroPrevisionaleDTO esito = new
	 * EsitoSalvaQuadroPrevisionaleDTO();
	 *
	 * try { // solo se le note del client non corrispondono con quelle del db o //
	 * il quadro non esiste ancora salvaEStoricizzaQuadroPrevisionale(idUtente,
	 * quadroPrevizionaleDTO);
	 *
	 * salvaEStoricizzaRighiQuadroPrevisionale(idUtente, quadroPrevizionaleDTO);
	 *
	 * EsitoFindQuadroPrevisionaleDTO esitoFind = findQuadroPrevisionale( idUtente,
	 * identitaDigitale, quadroPrevizionaleDTO.getIdProgetto());
	 *
	 * esito.setQuadroPrevisionale(esitoFind.getQuadroPrevisionale());
	 *
	 * Long idProgetto = quadroPrevizionaleDTO.getIdProgetto();
	 *
	 * // 20/01/2015 la chiusura del task sul quadro previsionale avviene in chiudi
	 * progetto tramite pl/sql, qui faccio UNLOCK logger.
	 * warn("\n\n\n############################ NEOFLUX UNLOCK QUADRO_PREVISIO ##############################\n"
	 * ); neofluxBusiness.unlockAttivita(idUtente, identitaDigitale,
	 * idProgetto,Task.QUADRO_PREVISIO); logger.
	 * warn("############################ NEOFLUX UNLOCK QUADRO_PREVISIO ##############################\n\n\n\n"
	 * );
	 *
	 * return esito; } catch (Exception e) { logger.error(e.getMessage(), e); throw
	 * new UnrecoverableException(e.getMessage(), e); }
	 *
	 * }
	 *
	 *
	 * // 1) importo nullo(macro voce con figli): non inserisco // // 2) record non
	 * esiste : inserisco un nuovo record // // 3) record esiste e importo � diverso
	 * : storicizzo e inserisco un nuovo // record // // 4) record esiste e importo
	 * � lo stesso: non faccio nulla
	 *
	 * private void salvaEStoricizzaRighiQuadroPrevisionale(Long idUtente,
	 * QuadroPrevisionaleDTO quadroPrevizionaleDTO) throws Exception {
	 * QuadroPrevisionaleDTO[] figli = quadroPrevizionaleDTO.getFigli(); for
	 * (QuadroPrevisionaleDTO figlio : figli) {
	 *
	 * if (figlio.getImportoPreventivo() != null) { PbandiTRigoQuadroPrevisioVO
	 * pbandiTRigoQuadroPrevisioVO = new PbandiTRigoQuadroPrevisioVO(); if
	 * (figlio.getIdRigoQuadro() == null) { // INSERISCO ,caso 2)
	 * pbandiTRigoQuadroPrevisioVO .setIdVoceDiSpesa(new
	 * BigDecimal(figlio.getIdVoce())); pbandiTRigoQuadroPrevisioVO
	 * .setImportoPreventivo(new BigDecimal(figlio .getImportoPreventivo()));
	 * pbandiTRigoQuadroPrevisioVO.setIdProgetto(new BigDecimal(
	 * quadroPrevizionaleDTO.getIdProgetto()));
	 * pbandiTRigoQuadroPrevisioVO.setPeriodo(figlio.getPeriodo());
	 * pbandiTRigoQuadroPrevisioVO.setIdUtenteIns(new BigDecimal( idUtente));
	 * genericDAO.insert(pbandiTRigoQuadroPrevisioVO); } else {
	 * pbandiTRigoQuadroPrevisioVO .setIdRigoQuadroPrevisio(new BigDecimal(figlio
	 * .getIdRigoQuadro())); pbandiTRigoQuadroPrevisioVO = genericDAO
	 * .findSingleWhere(pbandiTRigoQuadroPrevisioVO); if
	 * (NumberUtil.compare(pbandiTRigoQuadroPrevisioVO .getImportoPreventivo(),
	 * beanUtil.transform( figlio.getImportoPreventivo(), BigDecimal.class)) != 0) {
	 * // STORICIZZO E INSERISCO,caso 3)
	 * pbandiTRigoQuadroPrevisioVO.setDtFineValidita(DateUtil .getSysdate());
	 * pbandiTRigoQuadroPrevisioVO .setIdUtenteAgg(new BigDecimal(idUtente));
	 * genericDAO.update(pbandiTRigoQuadroPrevisioVO); pbandiTRigoQuadroPrevisioVO
	 * .setIdVoceDiSpesa(new BigDecimal(figlio .getIdVoce()));
	 * pbandiTRigoQuadroPrevisioVO.setIdUtenteAgg(null); pbandiTRigoQuadroPrevisioVO
	 * .setIdUtenteIns(new BigDecimal(idUtente)); pbandiTRigoQuadroPrevisioVO
	 * .setIdRigoQuadroPrevisio(null);
	 * pbandiTRigoQuadroPrevisioVO.setDtFineValidita(null);
	 * pbandiTRigoQuadroPrevisioVO.setDtInizioValidita(null);
	 * pbandiTRigoQuadroPrevisioVO .setImportoPreventivo(new BigDecimal(figlio
	 * .getImportoPreventivo())); genericDAO.insert(pbandiTRigoQuadroPrevisioVO); }
	 * else { // caso 4) } } } else { // NON FO NULLA: caso 1)
	 * logger.debug("macro voce con figli,non inserisco"); } } }
	 *
	 *
	 * // 1)il quadro non esiste : lo inserisco // // 2)OLD (sostituito da 4) ): il
	 * quadro esiste e le note SONO cambiate : // storicizza il vecchio e ne
	 * inserisce uno nuovo // // 3)OLD (sostituito da 4) )il quadro esiste e le note
	 * NON sono cambiate : // aggiorna solo la data aggiornamento // // 4)il quadro
	 * esiste : storicizza il vecchio e ne inserisce uno nuovo
	 *
	 *
	 * private BigDecimal salvaEStoricizzaQuadroPrevisionale(Long idUtente,
	 * QuadroPrevisionaleDTO quadroPrevizionaleDTO) throws Exception {
	 * PbandiTQuadroPrevisionaleVO pbandiTQuadroPrevisionaleVO = new
	 * PbandiTQuadroPrevisionaleVO(); BigDecimal idQuadro = null; if
	 * (quadroPrevizionaleDTO.getIdQuadro() == null) { // caso 1)
	 * pbandiTQuadroPrevisionaleVO .setNote(quadroPrevizionaleDTO.getNote());
	 * pbandiTQuadroPrevisionaleVO .setIdUtenteIns(new BigDecimal(idUtente));
	 * pbandiTQuadroPrevisionaleVO.setIdProgetto(new BigDecimal(
	 * quadroPrevizionaleDTO.getIdProgetto()));
	 * pbandiTQuadroPrevisionaleVO.setDtAggiornamento(DateUtil .getSysdate());
	 * idQuadro = ((PbandiTQuadroPrevisionaleVO) genericDAO
	 * .insert(pbandiTQuadroPrevisionaleVO)) .getIdQuadroPrevisionale(); } else {
	 * idQuadro = new BigDecimal(quadroPrevizionaleDTO.getIdQuadro());
	 * pbandiTQuadroPrevisionaleVO.setIdQuadroPrevisionale(idQuadro);
	 *
	 * pbandiTQuadroPrevisionaleVO = genericDAO
	 * .findSingleWhere(pbandiTQuadroPrevisionaleVO);
	 *
	 * if (isNull(quadroPrevizionaleDTO.getNote()))
	 * quadroPrevizionaleDTO.setNote("");
	 *
	 * java.sql.Date currentDate = DateUtil.getSysdate();
	 *
	 * // if (!pbandiTQuadroPrevisionaleVO.getNote().equals( //
	 * quadroPrevizionaleDTO.getNote())) {
	 *
	 * // caso 2) pbandiTQuadroPrevisionaleVO.setDtFineValidita(currentDate);
	 * pbandiTQuadroPrevisionaleVO.setDtAggiornamento(currentDate);
	 * pbandiTQuadroPrevisionaleVO .setIdUtenteAgg(new BigDecimal(idUtente));
	 * genericDAO.update(pbandiTQuadroPrevisionaleVO);
	 *
	 * pbandiTQuadroPrevisionaleVO.setIdQuadroPrevisionale(null);
	 * pbandiTQuadroPrevisionaleVO.setDtInizioValidita(null);
	 * pbandiTQuadroPrevisionaleVO.setDtFineValidita(null);
	 * pbandiTQuadroPrevisionaleVO.setDtAggiornamento(currentDate);
	 * pbandiTQuadroPrevisionaleVO .setNote(quadroPrevizionaleDTO.getNote());
	 * pbandiTQuadroPrevisionaleVO .setIdUtenteIns(new BigDecimal(idUtente));
	 * idQuadro = ((PbandiTQuadroPrevisionaleVO) genericDAO
	 * .insert(pbandiTQuadroPrevisionaleVO)) .getIdQuadroPrevisionale();
	 *
	 * } return idQuadro; }
	 *
	 * public ProceduraAggiudicazioneDTO findDettaglioProceduraAggiudicazione( Long
	 * idUtente, String identitaDigitale, Long idProceduraAggiudicazione) throws
	 * CSIException, SystemException, UnrecoverableException,
	 * ContoEconomicoException {
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale",
	 * "idProceduraAggiudicazione" };
	 *
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProceduraAggiudicazione);
	 *
	 * ProceduraAggiudicazioneProgettoAppaltoVO filtroVO = new
	 * ProceduraAggiudicazioneProgettoAppaltoVO();
	 * filtroVO.setIdProceduraAggiudicaz(beanUtil
	 * .transform(idProceduraAggiudicazione, BigDecimal.class)); filtroVO =
	 * genericDAO.findSingleWhere(filtroVO);
	 *
	 * IterProcAggVO procAggVO = new IterProcAggVO();
	 * procAggVO.setIdProceduraAggiudicaz(filtroVO.getIdProceduraAggiudicaz());
	 * procAggVO.setAscendentOrder("codIgrueT48"); List<IterProcAggVO> iter =
	 * genericDAO.findListWhere(procAggVO);
	 *
	 * ProceduraAggiudicazioneDTO result = beanUtil.transform(filtroVO,
	 * ProceduraAggiudicazioneDTO.class); result.setIter(beanUtil.transform(iter,
	 * StepAggiudicazione.class, MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE));
	 *
	 * return result;
	 *
	 *
	 * }
	 *
	 *
	 *
	 * public EsitoProceduraAggiudicazioneDTO inserisciProceduraAggiudicazione( Long
	 * idUtente, String identitaDigitale, ProceduraAggiudicazioneDTO
	 * proceduraAggiudicazione) throws CSIException, SystemException,
	 * UnrecoverableException, ContoEconomicoException {
	 *
	 * EsitoProceduraAggiudicazioneDTO result = new
	 * EsitoProceduraAggiudicazioneDTO();
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale",
	 * "proceduraAggiudicazione" }; ValidatorInput.verifyNullValue(nameParameter,
	 * idUtente, identitaDigitale, proceduraAggiudicazione);
	 *
	 * try { result =
	 * insertUpdateProceduraAggiudicazione(idUtente,proceduraAggiudicazione); }
	 * catch (Exception e) { logger.error(
	 * "Errore di inserimento in PBANDI_T_PROCEDURA_AGGIUDICAZ", e);
	 * result.setEsito(Boolean.FALSE); MessaggioDTO msg = new MessaggioDTO();
	 * msg.setMsgKey(ERRORE_SERVER); result.setMsgs(new MessaggioDTO[] { msg }); }
	 * return result;
	 *
	 *
	 * }
	 *
	 * private EsitoProceduraAggiudicazioneDTO
	 * insertUpdateProceduraAggiudicazione(Long idUtente, ProceduraAggiudicazioneDTO
	 * proceduraAggiudicazione) throws Exception {
	 * ValidatorInput.verifyNullValue(new String[] { "idProgetto",
	 * "idTipologiaAggiudicaz", "importo", "descProcAgg" },
	 * proceduraAggiudicazione.getIdProgetto(),
	 * proceduraAggiudicazione.getIdTipologiaAggiudicaz(),
	 * proceduraAggiudicazione.getImporto(),
	 * proceduraAggiudicazione.getDescProcAgg());
	 * ValidatorInput.verifyAtLeastOneNotNullValue(new String[] { "cigProcAgg",
	 * "codProcAgg" }, proceduraAggiudicazione.getCigProcAgg(),
	 * proceduraAggiudicazione.getCodProcAgg());
	 *
	 * // check univocita' CIG e CPA PbandiTProceduraAggiudicazVO vo = new
	 * PbandiTProceduraAggiudicazVO();
	 * vo.setCigProcAgg(proceduraAggiudicazione.getCigProcAgg());
	 * Condition<PbandiTProceduraAggiudicazVO> cigCondition =
	 * Condition.filterBy(vo); vo = new PbandiTProceduraAggiudicazVO();
	 * vo.setCodProcAgg(proceduraAggiudicazione.getCodProcAgg());
	 * vo.setIdProgetto(beanUtil.transform(proceduraAggiudicazione.getIdProgetto(),
	 * BigDecimal.class)); Condition<PbandiTProceduraAggiudicazVO> cpaCondition =
	 * Condition.filterBy(vo); if(proceduraAggiudicazione.getIdProceduraAggiudicaz()
	 * != null) { // devo escludere la procedura stessa dal controllo vo = new
	 * PbandiTProceduraAggiudicazVO(beanUtil.transform(proceduraAggiudicazione.
	 * getIdProceduraAggiudicaz(), BigDecimal.class)); cigCondition =
	 * cigCondition.and(Condition.filterBy(vo).negate()); cpaCondition =
	 * cpaCondition.and(Condition.filterBy(vo).negate()); }
	 *
	 * EsitoProceduraAggiudicazioneDTO result = new
	 * EsitoProceduraAggiudicazioneDTO(); MessaggioDTO msg = new MessaggioDTO(); try
	 * { vo = genericDAO.where(cigCondition).selectFirst(); } catch
	 * (RecordNotFoundException e) { // tutto OK vo = null; } if (vo != null) {
	 * result.setEsito(false);
	 * msg.setMsgKey(ERRORE_PROCEDURA_AGGIUDICAZIONE_CIG_GIA_ASSEGNATO);
	 * msg.setParams(new String[] { genericDAO.findSingleWhere( new
	 * PbandiTProgettoVO(vo.getIdProgetto())) .getCodiceVisualizzato() });
	 * result.setMsgs(new MessaggioDTO[] { msg }); } else if
	 * (genericDAO.count(cpaCondition) != 0) { result.setEsito(false);
	 * msg.setMsgKey(ERRORE_PROCEDURA_AGGIUDICAZIONE_CPA_GIA_ASSEGNATO);
	 * result.setMsgs(new MessaggioDTO[] { msg }); } else { result.setEsito(true);
	 * msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO); result.setMsgs(new
	 * MessaggioDTO[] { msg }); PbandiTProceduraAggiudicazVO
	 * pbandiTProceduraAggiudicazVO = beanUtil.transform( proceduraAggiudicazione,
	 * PbandiTProceduraAggiudicazVO.class);
	 * pbandiTProceduraAggiudicazVO.setIdUtenteIns(new BigDecimal(idUtente));
	 * ProceduraAggiudicazioneDTO newProcedura = beanUtil.transform(genericDAO
	 * .insertOrUpdateExisting(pbandiTProceduraAggiudicazVO),
	 * ProceduraAggiudicazioneDTO.class);
	 * result.setProceduraAggiudicazione(newProcedura);
	 *
	 * // pulisci iter aggiudicazione se sono in modifica if
	 * (newProcedura.getIdProceduraAggiudicaz() != null) { PbandiRIterProcAggVO
	 * procAggVO = new PbandiRIterProcAggVO();
	 * procAggVO.setIdProceduraAggiudicaz(new BigDecimal(
	 * newProcedura.getIdProceduraAggiudicaz()));
	 * genericDAO.deleteWhere(Condition.filterBy(procAggVO)); } // salva iter
	 * aggiudicazione se esistente final StepAggiudicazione[] newIter =
	 * proceduraAggiudicazione.getIter(); if(newIter != null && newIter.length>0) {
	 * ArrayList<PbandiRIterProcAggVO> iter = beanUtil
	 * .transformToArrayList(newIter, PbandiRIterProcAggVO.class,
	 * MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO); for
	 * (PbandiRIterProcAggVO iterItem : iter) {
	 * iterItem.setIdProceduraAggiudicaz(new BigDecimal(
	 * newProcedura.getIdProceduraAggiudicaz())); iterItem.setIdUtenteIns(new
	 * BigDecimal(idUtente)); } genericDAO.insert(iter); }
	 *
	 * } return result; }
	 *
	 * public EsitoProceduraAggiudicazioneDTO modificaProceduraAggiudicazione( Long
	 * idUtente, String identitaDigitale, ProceduraAggiudicazioneDTO
	 * proceduraAggiudicazione) throws CSIException, SystemException,
	 * UnrecoverableException, ContoEconomicoException {
	 * EsitoProceduraAggiudicazioneDTO result = new
	 * EsitoProceduraAggiudicazioneDTO();
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale",
	 * "proceduraAggiudicazione" }; ValidatorInput.verifyNullValue(nameParameter,
	 * idUtente, identitaDigitale, proceduraAggiudicazione);
	 *
	 * try { result =
	 * insertUpdateProceduraAggiudicazione(idUtente,proceduraAggiudicazione); }
	 * catch (Exception e) { logger.error(
	 * "Errore di update in PBANDI_T_PROCEDURA_AGGIUDICAZ", e);
	 * result.setEsito(Boolean.FALSE); MessaggioDTO msg = new MessaggioDTO();
	 * msg.setMsgKey(ERRORE_SERVER); result.setMsgs(new MessaggioDTO[] { msg }); }
	 * return result;
	 *
	 * }
	 *
	 *
	 * // Gestione della procedura di aggiudicazione. Verificare se esiste una sola
	 * // procedura di aggiudicazione (per ribasso d'asta) legata al conto //
	 * ecomonico (local copy). - se esiste una procedura di aggiudicazione e //
	 * l'utente ha indicato una nuova procedura di aggiudicazione allora eseguo
	 * l'update // // - se esiste piu' di una procedura di aggiudicazione allora
	 * rilancio una eccezione //
	 *
	 *
	 *
	 * public ContoEconomicoDTO findDatiContoEconomicoComplessivo(Long idUtente,
	 * String identitaDigitale, Long idProgetto) throws CSIException,
	 * SystemException, UnrecoverableException, ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProgetto);
	 *
	 * try { ProgettoSoggettoPartnerVO progettoPartnerVO = new
	 * ProgettoSoggettoPartnerVO(); progettoPartnerVO.setIdProgetto(new
	 * BigDecimal(idProgetto));
	 *
	 * List<BigDecimal> progettiPartner = beanUtil.extractValues(
	 * genericDAO.findListWhere(progettoPartnerVO), "idProgettoPartner",
	 * BigDecimal.class); progettiPartner.add(0, new BigDecimal(idProgetto));
	 *
	 * List<ContoEconomicoDTO> contiEconomici = new ArrayList<ContoEconomicoDTO>();
	 * for (BigDecimal idProgettoPartner : progettiPartner) {
	 * it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO
	 * contoEconomicoMaster = contoEconomicoManager
	 * .findContoEconomicoMasterPotato(idProgettoPartner);
	 *
	 * contiEconomici .add(convertiContoEconomicoMaster(contoEconomicoMaster)); }
	 *
	 * ContoEconomicoDTO contoEconomicoComplessivo = null; HashMap<Long,
	 * ContoEconomicoDTO> macroVoci = new LinkedHashMap<Long, ContoEconomicoDTO>();
	 * HashMap<Long, HashMap<Long, ContoEconomicoDTO>> microVoci = new HashMap<Long,
	 * HashMap<Long, ContoEconomicoDTO>>();
	 *
	 * for (ContoEconomicoDTO contoEconomico : contiEconomici) {
	 * contoEconomicoComplessivo = lazyAccumulate( contoEconomicoComplessivo,
	 * contoEconomico); for (ContoEconomicoDTO macroVoce :
	 * contoEconomico.getFigli()) { macroVoci.put( macroVoce.getIdVoce(),
	 * lazyAccumulate( macroVoci.get(macroVoce.getIdVoce()), macroVoce)); for
	 * (ContoEconomicoDTO microVoce : ObjectUtil.nvl( macroVoce.getFigli(), new
	 * ContoEconomicoDTO[0])) { if
	 * (!microVoci.containsKey(microVoce.getIdVocePadre())) {
	 * microVoci.put(microVoce.getIdVocePadre(), new HashMap<Long,
	 * ContoEconomicoDTO>()); } // ottengo le sotto-voci della macro-voce in esame
	 * HashMap<Long, ContoEconomicoDTO> microvociPerMacro = microVoci
	 * .get(microVoce.getIdVocePadre()); // ottengo la sotto-voce se esiste gi� o
	 * null ContoEconomicoDTO microvoceAccumulate = microvociPerMacro
	 * .get(microVoce.getIdVoce()); // accumulo gli importi della sotto-voce in
	 * esame o ne // creo una nuova microvoceAccumulate = lazyAccumulate(
	 * microvoceAccumulate, microVoce); // metto la nuova sotto-voce nella mappa
	 * microvociPerMacro.put(microVoce.getIdVoce(), microvoceAccumulate); } } }
	 *
	 * List<ContoEconomicoDTO> vociDiSpesa = new ArrayList<ContoEconomicoDTO>();
	 *
	 * for (Long key : macroVoci.keySet()) { ContoEconomicoDTO voceDiSpesa =
	 * beanUtil.transform( macroVoci.get(key), ContoEconomicoDTO.class); if
	 * (microVoci.containsKey(key)) { Collection<ContoEconomicoDTO> sottovociDiSpesa
	 * = microVoci .get(key).values(); voceDiSpesa.setFigli(sottovociDiSpesa
	 * .toArray(new ContoEconomicoDTO[sottovociDiSpesa .size()])); }
	 * vociDiSpesa.add(voceDiSpesa); }
	 *
	 * contoEconomicoComplessivo.setFigli(vociDiSpesa .toArray(new
	 * ContoEconomicoDTO[vociDiSpesa.size()]));
	 *
	 * calcolaPercentuali(contoEconomicoComplessivo);
	 *
	 * return contoEconomicoComplessivo;
	 *
	 * } catch (Exception e) { throw new UnrecoverableException(e.getMessage(), e);
	 * } }
	 *
	 * private void calcolaPercentuali(ContoEconomicoDTO contoEconomico) { if
	 * (contoEconomico != null && contoEconomico.getFigli() != null &&
	 * contoEconomico.getFigli().length > 0) { for (ContoEconomicoDTO
	 * contoEconomicoFiglio : contoEconomico .getFigli()) {
	 * calcolaPercentuali(contoEconomicoFiglio); }
	 *
	 * }
	 *
	 * contoEconomico.setPercSpesaValidataSuAmmessa(NumberUtil
	 * .toDouble(NumberUtil.percentage(
	 * contoEconomico.getImportoSpesaValidataTotale(),
	 * contoEconomico.getImportoSpesaAmmessa())));
	 *
	 * contoEconomico.setPercSpesaQuietanziataSuAmmessa(NumberUtil
	 * .toDouble(NumberUtil.percentage(
	 * contoEconomico.getImportoSpesaQuietanziata(),
	 * contoEconomico.getImportoSpesaAmmessa())));
	 *
	 * }
	 *
	 * private ContoEconomicoDTO lazyAccumulate(ContoEconomicoDTO accumulator,
	 * ContoEconomicoDTO adder) { ContoEconomicoDTO accumulate = new
	 * ContoEconomicoDTO();
	 *
	 * if (accumulator == null) { accumulate = beanUtil.transform(adder,
	 * ContoEconomicoDTO.class); } else { accumulate =
	 * beanUtil.transform(accumulator, ContoEconomicoDTO.class); if
	 * (accumulator.getImportoSpesaAmmessa() != null ||
	 * adder.getImportoSpesaAmmessa() != null) {
	 * accumulate.setImportoSpesaAmmessa(ObjectUtil.nvl(
	 * accumulator.getImportoSpesaAmmessa(), 0D) +
	 * ObjectUtil.nvl(adder.getImportoSpesaAmmessa(), 0D)); } if
	 * (accumulator.getImportoSpesaQuietanziata() != null ||
	 * adder.getImportoSpesaQuietanziata() != null) {
	 * accumulate.setImportoSpesaQuietanziata(ObjectUtil.nvl(
	 * accumulator.getImportoSpesaQuietanziata(), 0D) +
	 * ObjectUtil.nvl(adder.getImportoSpesaQuietanziata(), 0D)); } if
	 * (accumulator.getImportoSpesaRendicontata() != null ||
	 * adder.getImportoSpesaRendicontata() != null) {
	 * accumulate.setImportoSpesaRendicontata(ObjectUtil.nvl(
	 * accumulator.getImportoSpesaRendicontata(), 0D) +
	 * ObjectUtil.nvl(adder.getImportoSpesaRendicontata(), 0D)); } if
	 * (accumulator.getImportoSpesaValidataTotale() != null ||
	 * adder.getImportoSpesaValidataTotale() != null) {
	 * accumulate.setImportoSpesaValidataTotale(ObjectUtil.nvl(
	 * accumulator.getImportoSpesaValidataTotale(), 0D) +
	 * ObjectUtil.nvl(adder.getImportoSpesaValidataTotale(), 0D)); }
	 *
	 * }
	 *
	 * return accumulate; }
	 *
	 * public ContoEconomicoDTO findDatiContoEconomicoPartner(Long idUtente, String
	 * identitaDigitale, Long idProgetto, Long idSoggettoPartner) throws
	 * CSIException, SystemException, UnrecoverableException,
	 * ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto",
	 * "idSoggettoPartner" }; ValidatorInput.verifyNullValue(nameParameter,
	 * idUtente, identitaDigitale, idProgetto, idSoggettoPartner);
	 *
	 * try { ProgettoSoggettoPartnerVO progettoPartner = new
	 * ProgettoSoggettoPartnerVO(); progettoPartner.setIdProgettoPartner(new
	 * BigDecimal( idSoggettoPartner)); progettoPartner =
	 * genericDAO.findSingleWhere(progettoPartner);
	 * it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO
	 * contoEconomicoMaster = contoEconomicoManager
	 * .findContoEconomicoMasterPotato(progettoPartner .getIdProgettoPartner());
	 *
	 * ContoEconomicoDTO contoEconomicoDTO =
	 * convertiContoEconomicoMaster(contoEconomicoMaster);
	 *
	 * return contoEconomicoDTO;
	 *
	 * } catch (Exception e) { throw new UnrecoverableException(e.getMessage(), e);
	 * } }
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 * // Somma tutte le fonti tranne l'ultima
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 * // Se c'e' la percentuale ma non c'e' l'importo inizializzo l'importo della
	 * nuova quota fontef
	 *
	 *
	 *
	 *
	 * public void setProgettoManager(ProgettoManager progettoManager) {
	 * this.progettoManager = progettoManager; }
	 *
	 * public ProgettoManager getProgettoManager() { return progettoManager; }
	 *
	 *
	 *
	 *
	 * // MARZO 2015 DEMAT II
	 *
	 *
	 *
	 *
	 * public java.lang.Boolean aggiornaFonteFinanziaria( java.lang.Long idUtente,
	 * java.lang.String identitaDigitale,
	 * it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO
	 * fonteFinanziaria, java.lang.Long idProgetto ) throws
	 * it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	 * it.csi.csi.wrapper.UnrecoverableException ,
	 * it.csi.pbandi.pbwebrce.pbandisrv.business.contoeconomico.
	 * ContoEconomicoException {
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale","fonteFinanziaria",
	 * "idProgetto" }; logger.info("aggiornaFonteFinanziaria\n\n\n\n");
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * fonteFinanziaria, idProgetto); logger.shallowDump(fonteFinanziaria,"info");
	 * FontiFinanziarieDTO[] fontiFinanziarie= new FontiFinanziarieDTO[1];
	 * fontiFinanziarie[0]=fonteFinanziaria; try {
	 * aggiornaFontiFinanziarie(idUtente, identitaDigitale, fontiFinanziarie,
	 * BigDecimal.valueOf(idProgetto)); } catch (Exception e) {
	 * logger.error("error aggiornaFontiFinanziarie",e); } return false; }
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 * /////////////////////////////////////////////////////////////////////////////
	 * ///////////////////
	 *
	 *
	 *
	 * public DatiPerConclusioneRimodulazioneDTO
	 * caricaDatiPerConclusioneIstruttoria( Long idUtente, String identitaDigitale,
	 * ProgettoDTO progetto) throws CSIException, SystemException,
	 * UnrecoverableException, ContoEconomicoException { try {
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale","progetto",
	 * "progetto.idProgetto" }; ValidatorInput.verifyNullValue(nameParameter,
	 * idUtente,identitaDigitale, progetto, progetto.getIdProgetto());
	 *
	 * logger.
	 * info("\n\n\n\n\n\ncaricaDatiPerConclusioneIstruttoria per idProgetto: "
	 * +progetto.getIdProgetto());
	 *
	 *
	 * DatiPerConclusioneRimodulazioneDTO datiPerConclusioneRimodulazioneDTO = new
	 * DatiPerConclusioneRimodulazioneDTO(); ModalitaDiAgevolazioneDTO[]
	 * modalitaAgevolazione = null;
	 *
	 * Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>
	 * contiEconomici = contoEconomicoManager .findContiEconomici( new
	 * BigDecimal(progetto.getIdProgetto()),
	 * Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
	 *
	 * it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain =
	 * contiEconomici.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
	 *
	 * if (contoMain == null) {
	 * logger.error("Errore fatal! conto economico main non esiste per idProgetto: "
	 * +progetto.getIdProgetto()); throw new
	 * Exception("Errore fatal! conto economico main non esiste per idProgetto: "
	 * +progetto.getIdProgetto()); }
	 *
	 * Double importoSpesaAmmessa =
	 * NumberUtil.toDouble(contoMain.getImportoAmmesso());
	 *
	 * modalitaAgevolazione = caricaModalitaAgevolazionePerIstruttoria(
	 * progetto.getIdProgetto(), importoSpesaAmmessa,
	 * contoMain.getIdContoEconomico());
	 *
	 * datiPerConclusioneRimodulazioneDTO.setModalitaDiAgevolazione(
	 * modalitaAgevolazione);
	 * datiPerConclusioneRimodulazioneDTO.setTotaliModalitaDiAgevolazione(
	 * calcolaTotaliModalitaAgevolazione(modalitaAgevolazione));
	 *
	 * Map<String, String> mapFin = creaMapFonteFinanziaria();
	 *
	 * BigDecimal idProgetto = new BigDecimal(progetto.getIdProgetto());
	 *
	 * FontiFinanziarieDTO[] fontiPrivate= getBeanUtil() .transform(
	 * contoEconomicoManager .getSoggettiFinanziatori(idProgetto,"N"),
	 * FontiFinanziarieDTO.class, mapFin);
	 *
	 * FontiFinanziarieDTO[] fontiNonPrivate= getBeanUtil()
	 * .transform(contoEconomicoManager .getSoggettiFinanziatori(idProgetto,"S"),
	 * FontiFinanziarieDTO.class, mapFin);
	 *
	 *
	 *
	 * Boolean isPeriodoUnico = isPeriodoUnico(fontiNonPrivate);
	 * logger.info("\n\n\n\n ************** isPeriodoUnico: "+isPeriodoUnico);
	 * datiPerConclusioneRimodulazioneDTO.setIsPeriodoUnico(isPeriodoUnico);
	 * datiPerConclusioneRimodulazioneDTO.setFontiFinanziarieNonPrivate(
	 * fontiNonPrivate);
	 * datiPerConclusioneRimodulazioneDTO.setFontiFinanziariePrivate(fontiPrivate);
	 *
	 * if(isPeriodoUnico){ //spatarramento su fonti TNT 19 lug 2016
	 * setDefaultPercentualiImportiFontiNonPrivate(progetto,importoSpesaAmmessa,
	 * datiPerConclusioneRimodulazioneDTO); }
	 *
	 * Map<String, String> mapDefault = new HashMap<String, String>();
	 * mapDefault.put("percQuotaFonteFinanziariaUltima",
	 * "percQuotaFonteFinanziariaNuova");
	 *
	 * getBeanUtil().valueCopy(fontiPrivate, fontiPrivate, mapDefault);
	 * getBeanUtil().valueCopy(fontiNonPrivate, fontiNonPrivate, mapDefault);
	 *
	 * FontiFinanziarieDTO[] arrayMerge =
	 * ObjectUtil.arrayMerge(fontiNonPrivate,fontiPrivate); FontiFinanziarieDTO
	 * totale = NumberUtil.accumulate(arrayMerge, "percQuotaFonteFinanziariaUltima",
	 * "percQuotaFonteFinanziariaNuova");
	 * totale.setQuotaFonteFinanziariaNuova(importoSpesaAmmessa);
	 *
	 * for (FontiFinanziarieDTO f : arrayMerge) {
	 * calcolaQuotaFonteFinanziaria(totale, f); }
	 *
	 * FontiFinanziarieDTO subTotale = NumberUtil.accumulate(fontiNonPrivate,
	 * "percQuotaFonteFinanziariaUltima", "percQuotaFonteFinanziariaNuova");
	 * calcolaQuotaFonteFinanziaria(totale, subTotale);
	 *
	 * totale = NumberUtil.accumulate(arrayMerge, "percQuotaFonteFinanziariaUltima",
	 * "percQuotaFonteFinanziariaNuova", "quotaFonteFinanziariaUltima",
	 * "quotaFonteFinanziariaNuova");
	 *
	 * datiPerConclusioneRimodulazioneDTO.setFontiFinanziarieNonPrivate(
	 * fontiNonPrivate);
	 * datiPerConclusioneRimodulazioneDTO.setFontiFinanziariePrivate(fontiPrivate);
	 * datiPerConclusioneRimodulazioneDTO.setSubTotaliFontiFinanziarieNonPrivate(
	 * subTotale);
	 * datiPerConclusioneRimodulazioneDTO.setImportoSpesaAmmessa(importoSpesaAmmessa
	 * ); datiPerConclusioneRimodulazioneDTO.setTotaliFontiFinanziarie(totale);
	 * datiPerConclusioneRimodulazioneDTO.setProgetto(progetto);
	 *
	 * setDatiAccessori(progetto, datiPerConclusioneRimodulazioneDTO,contiEconomici,
	 * contoMain); logger.info("\n\n\nfontiNonPrivate :");
	 * if(fontiNonPrivate!=null){ for (FontiFinanziarieDTO fonte : fontiNonPrivate)
	 * { logger.info(fonte.getDescrizione() +" "+fonte.getDescPeriodo()
	 * +" "+fonte.getImpQuotaSoggFinanziatore()
	 * +" "+fonte.getPercQuotaFonteFinanziariaNuova()); } }
	 * logger.info("fonti private:"); if(fontiPrivate!=null){ for
	 * (FontiFinanziarieDTO fonte : fontiPrivate) {
	 * logger.info(fonte.getDescrizione() +" "+fonte.getDescPeriodo()
	 * +" "+fonte.getImpQuotaSoggFinanziatore()
	 * +" "+fonte.getPercQuotaFonteFinanziariaNuova()); } }
	 * logger.info("\n\n\n\n\n\n");
	 *
	 * return datiPerConclusioneRimodulazioneDTO; } catch (Exception e) {
	 * logger.error(e.getMessage(), e); throw new
	 * UnrecoverableException(e.getMessage(), e); } }
	 *
	 *
	 *
	 *
	 *
	 * public ProceduraAggiudicazioneDTO[]
	 * findTutteProcedureAggiudicazionePerProgetto( Long idUtente, String
	 * identitaDigitale, ProceduraAggiudicazioneDTO filtro) throws CSIException,
	 * SystemException, UnrecoverableException, ContoEconomicoException { String[]
	 * nameParameter = { "idUtente", "identitaDigitale", "filtro" };
	 *
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * filtro);
	 *
	 * ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
	 *
	 * // ProceduraAggiudicazioneProgettoVO filtroVO = new
	 * ProceduraAggiudicazioneProgettoVO(); // filtroVO.setIdProgetto(new
	 * BigDecimal(filtro.getIdProgetto()));
	 *
	 * ProceduraAggiudicazioneProgettoAppaltoVO filtroVO = beanUtil.transform(
	 * filtro, ProceduraAggiudicazioneProgettoAppaltoVO.class, new HashMap<String,
	 * String>() { { put("idTipologiaAggiudicaz", "idTipologiaAggiudicaz");
	 * put("idProceduraAggiudicaz", "idProceduraAggiudicaz"); put("idProgetto",
	 * "idProgetto"); } });
	 *
	 * ProceduraAggiudicazioneProgettoAppaltoVO likeConditionVO = new
	 * ProceduraAggiudicazioneProgettoAppaltoVO();
	 * likeConditionVO.setCodProcAgg(filtro.getCodProcAgg());
	 * likeConditionVO.setCigProcAgg(filtro.getCigProcAgg());
	 * LikeContainsCondition<ProceduraAggiudicazioneProgettoAppaltoVO> likeCondition
	 * = new LikeContainsCondition<ProceduraAggiudicazioneProgettoAppaltoVO>(
	 * likeConditionVO);
	 *
	 * FilterCondition<ProceduraAggiudicazioneProgettoAppaltoVO> filterCondition =
	 * new FilterCondition<ProceduraAggiudicazioneProgettoAppaltoVO>( filtroVO);
	 *
	 * AndCondition<ProceduraAggiudicazioneProgettoAppaltoVO> andCondition = new
	 * AndCondition<ProceduraAggiudicazioneProgettoAppaltoVO>( filterCondition,
	 * likeCondition);
	 *
	 *
	 * List<ProceduraAggiudicazioneProgettoAppaltoVO> result = genericDAO
	 * .findListWhere(andCondition);
	 *
	 *
	 * // List<ProceduraAggiudicazioneProgettoVO> result = genericDAO //
	 * .findListWhere(filtroVO);
	 *
	 * return beanUtil.transform(result, ProceduraAggiudicazioneDTO.class);
	 *
	 * }
	 *
	 *
	 * public RigaContoEconomicoCulturaDTO[] findVociDiEntrataCultura(Long idUtente,
	 * String identitaDigitale, Long idProgetto) throws CSIException,
	 * SystemException, UnrecoverableException, ContoEconomicoException {
	 *
	 *
	 * String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
	 * idProgetto);
	 *
	 * try {
	 *
	 * RigaContoEconomicoCulturaDTO[] dto =
	 * contoEconomicoManager.findVociDiEntrataCultura(new BigDecimal(idProgetto));
	 *
	 * return dto;
	 *
	 * } catch (Exception e) { throw new UnrecoverableException(e.getMessage(), e);
	 * } }
	 *
	 */

}
