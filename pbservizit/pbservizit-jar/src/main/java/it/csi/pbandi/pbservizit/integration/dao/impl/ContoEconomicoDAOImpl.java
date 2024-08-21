/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.impl.ContoeconomicoBusinessImpl;
import it.csi.pbandi.pbservizit.dto.ContoEconomico;
import it.csi.pbandi.pbservizit.dto.EsitoDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.dto.contoeconomico.ContoEconomicoItem;
import it.csi.pbandi.pbservizit.dto.contoeconomico.InizializzaVisualizzaContoEconomicoDTO;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VoceDiSpesaPreventivataDTO;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VociDiSpesaCulturaDTO;
import it.csi.pbandi.pbservizit.exception.ContoEconomicoException;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.VoceDiEntrataCulturaRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.business.dichiarazionedispesa.DichiarazioneDiSpesaBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.dichiarazionedispesa.DichiarazioneDiSpesaCulturaBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneInviaDichiarazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.ConsuntivoEntrataDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VoceDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VociDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.EsitoOperazioneSalvaTipoAllegati;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ModalitaAgevolazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RigoContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico.ProceduraAggiudicazioneProgetto1420VO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico.ProceduraAggiudicazioneProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.util.*;
import it.doqui.index.ecmengine.dto.Node;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

@Service
public class ContoEconomicoDAOImpl extends JdbcDaoSupport implements ContoEconomicoDAO {

	private static final String[] TYPES = new String[]{"ContoEconomico", "MacroVoceDiSpesa", "MicroVoceDiSpesa"};
	static final private Map<String, Map<String, String>> mappaturaPerTipoContoEconomico = new HashMap<String, Map<String, String>>();
	static final private Map<String, String> mapMain = new HashMap<String, String>();
	static final private Map<String, String> mapMaster = new HashMap<String, String>();
	static final private Map<String, String> mapCopyBen = new HashMap<String, String>();
	static final private Map<String, String> mapCopyIst = new HashMap<String, String>();
	static final private Map<String, String> mapCommon = new HashMap<String, String>();
	@Autowired
	protected BeanUtil beanUtil;
	@Autowired
	protected DichiarazioneDiSpesaBusinessImpl dichiarazioneDiSpesaBusinessImpl;
	@Autowired
	protected DichiarazioneDiSpesaCulturaBusinessImpl dichiarazioneDiSpesaCulturaBusinessImpl;
	@Autowired
	ContoeconomicoBusinessImpl contoeconomicoBusinessImpl;
	private Logger LOG = Logger.getLogger(it.csi.pbandi.pbservizit.util.Constants.COMPONENT_NAME);
	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private RegolaManager regolaManager;

	@Autowired
	private ProgettoManager progettoManager;

	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager popolaTemplateManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager rappresentanteLegaleManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager sedeManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager decodificheManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager documentoManager;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl neofluxBusinessImpl;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	@Autowired
	private it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione;
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager soggettoManager;
	@Autowired
	private GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;

	{
		mapMain.put("importoRichiesto", "importoRichiestoInDomanda");
		mapMain.put("importoAmmesso", "importoSpesaAmmessaInDetermina");

		mapMaster.put("importoRichiesto", "importoRichiestoUltimaProposta");
		mapMaster.put("importoAmmesso", "importoSpesaAmmessaUltima");
		mapMaster.put("importoRendicontato", "importoSpesaRendicontata");
		mapMaster.put("importoQuietanzato", "importoSpesaQuietanziata");
		mapMaster.put("importoValidato", "importoSpesaValidataTotale");

		// Architetture Rurali
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

	@Autowired
	public ContoEconomicoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ContoEconomicoDAOImpl() {
	}

	private static VoceDiEntrataCulturaDTO convertiVociDiEntrata(ConsuntivoEntrataDTO voce) {
		VoceDiEntrataCulturaDTO voceDiEntrataCulturaDTO = new VoceDiEntrataCulturaDTO();
		if (voce.getIdConsuntivoEntrata() != null)
			voceDiEntrataCulturaDTO.setIdConsuntivoEntrata(voce.getIdConsuntivoEntrata().longValue());
		if (voce.getIdVoceDiEntrata() != null)
			voceDiEntrataCulturaDTO.setIdVoceDiEntrata(voce.getIdVoceDiEntrata().longValue());
		if (voce.getIdRigoContoEconomico() != null)
			voceDiEntrataCulturaDTO.setIdRigoContoEconomico(voce.getIdRigoContoEconomico().longValue());
		voceDiEntrataCulturaDTO.setImportoConsuntivoPresentato(voce.getImportoEntrata());
		voceDiEntrataCulturaDTO.setCompletamento(voce.getCompletamento());
		if (voce.getDtInizioValidita() != null)
			voceDiEntrataCulturaDTO.setDtInizioValidita(DateUtil.formatDate(voce.getDtInizioValidita()));
		if (voce.getDtFineValidita() != null)
			voceDiEntrataCulturaDTO.setDtFineValidita(DateUtil.formatDate(voce.getDtFineValidita()));
		if (voce.getIdUtenteIns() != null) voceDiEntrataCulturaDTO.setIdUtenteIns(voce.getIdUtenteIns().longValue());
		if (voce.getIdUtenteAgg() != null) voceDiEntrataCulturaDTO.setIdUtenteAgg(voce.getIdUtenteAgg().longValue());
		if (voce.getFlagEdit() != null) voceDiEntrataCulturaDTO.setFlagEdit(voce.getFlagEdit());
		if (voce.getDescrizione() != null) voceDiEntrataCulturaDTO.setDescrizione(voce.getDescrizione());
		return voceDiEntrataCulturaDTO;
	}

	@Override
	public InizializzaVisualizzaContoEconomicoDTO inizializzaVisualizzaContoEconomico(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaVisualizzaContoEconomico] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + ", idIride=" + idIride);

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		InizializzaVisualizzaContoEconomicoDTO result = new InizializzaVisualizzaContoEconomicoDTO();
		try {

			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[]{idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);

			Long idBandoLinea = this.idBandoLineaDaIdProgetto(idProgetto);
			LOG.info(prf + "idBandoLinea = " + idBandoLinea);

			boolean regola08attiva = regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea, RegoleConstants.BR08_FILTRI_RICERCA_BENEFICIARIO_CAPOFILA);
			boolean beneficiarioCapofila = progettoManager.isCapofila(new BigDecimal(idProgetto));
			result.setRicercaCapofila(regola08attiva && beneficiarioCapofila);
			LOG.info(prf + "regola08attiva = " + regola08attiva + "; beneficiarioCapofila = " + beneficiarioCapofila + "; RicercaCapofila = " + result.getRicercaCapofila());

			if (result.getRicercaCapofila()) {
				result.setPartnersCapofila(this.trovaPartnerCapofila(idProgetto));
			}

			ContoEconomicoDTO conto = this.trovaContoEconomico(idProgetto);
			ArrayList<ContoEconomicoItem> itemConto = this.trasformaPerVisualizzazione(conto);
			result.setContoEconomico(itemConto);
			if (itemConto != null) LOG.info(prf + "itemConto=" + itemConto.toString());

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf, e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public ArrayList<ContoEconomicoItem> aggiornaVisualizzaContoEconomico(Long idProgetto, String tipoRicerca, Long idPartner, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::aggiornaVisualizzaContoEconomico] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; tipoRicerca = " + tipoRicerca + "; idPartner = " + idPartner + "; idUtente = " + idUtente);

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (StringUtils.isBlank(tipoRicerca)) {
			throw new InvalidParameterException("tipoRicerca non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<ContoEconomicoItem> result = null;
		try {

			String capofila = "CAPOFILA";
			String complessivo = "COMPLESSIVO";
			String partner = "PARTNER";

			ContoEconomicoDTO conto = null;
			if (capofila.equalsIgnoreCase(tipoRicerca)) {
				LOG.info(prf + "Eseguo trovaContoEconomico()");
				conto = this.trovaContoEconomico(idProgetto);
			} else if (complessivo.equalsIgnoreCase(tipoRicerca)) {
				LOG.info(prf + "Eseguo trovaContoEconomicoComplessivo()");
				conto = this.trovaContoEconomicoComplessivo(idProgetto);
			} else if (partner.equalsIgnoreCase(tipoRicerca)) {
				if (idPartner != null) {
					LOG.info(prf + "Eseguo trovaContoEconomicoPartner()");
					conto = this.trovaContoEconomicoPartner(idProgetto, idPartner);
				}
			}

			result = this.trasformaPerVisualizzazione(conto);

		} catch (Exception e) {
			LOG.error(prf, e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	private ArrayList<CodiceDescrizioneDTO> trovaPartnerCapofila(Long idProgetto) {

		PartnerDTO[] partners = progettoManager.findPartnersPerVisualizzaContoEconomico(idProgetto);

		ArrayList<CodiceDescrizioneDTO> elenco = new ArrayList<CodiceDescrizioneDTO>();
		for (int i = 0; i < partners.length; i++) {
			CodiceDescrizioneDTO obj = new CodiceDescrizioneDTO();
			obj.setCodice(String.valueOf(partners[i].getIdProgettoPartner()));
			obj.setDescrizione(partners[i].getDescrizione());
			elenco.add(obj);
		}

		return elenco;
	}

	@Override
	public ContoEconomicoDTO trovaContoEconomico(Long idProgetto) {
		String prf = "[ContoEconomicoDAOImpl::trovaContoEconomico] ";
		logger.info(prf + "BEGIN, idProgetto=" + idProgetto);

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoMaster = null;
		try {
			contoEconomicoMaster = contoEconomicoManager.findContoEconomicoMasterPotato(new BigDecimal(idProgetto));

			if (contoEconomicoMaster != null) {
				logger.info(prf + "contoEconomicoMaster.getImportoQuietanzato=" + contoEconomicoMaster.getImportoQuietanzato());

				List<RigoContoEconomicoDTO> fi = contoEconomicoMaster.getFigli();

				if (fi != null && fi.size() > 0) {
					logger.info(prf + " fi.size()=" + fi.size());
					for (RigoContoEconomicoDTO f : fi) {
						logger.info(prf + " IdVoceDiSpesa=" + f.getIdVoceDiSpesa() + ",ImportoQuietanzato=" + f.getImportoQuietanzato());
					}
				}
			}
		} catch (ContoEconomicoNonTrovatoException e) {
			LOG.error("trovaContoEconomicoPerVisualizzazione(): errore in contoEconomicoManager.findContoEconomicoMasterPotato(): " + e);
			return null;
		}

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO contoEconomicoDTO;
		contoEconomicoDTO = convertiContoEconomicoMaster(contoEconomicoMaster);

		return contoEconomicoDTO;

	}

	@Override
	public ContoEconomicoConStatoVO trovaContoEconomicoProposta(Long idProgetto, Long idUtente) {

		ContoEconomicoConStatoVO contoEconomicoProposta = null;
		try {
			contoEconomicoProposta = contoEconomicoManager.findContoEconomicoProposta(new BigDecimal(idProgetto), idUtente);
		} catch (ContoEconomicoNonTrovatoException e) {
			LOG.error("trovaContoEconomicoPerVisualizzazione(): errore in contoEconomicoManager.findContoEconomicoMasterPotato(): " + e);
			return null;
		}


		return contoEconomicoProposta;

	}

	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO convertiContoEconomicoMaster(RigoContoEconomicoDTO rigo) {

		String prf = "[ContoEconomicoDAOImpl::convertiContoEconomicoMaster] ";
		logger.info(prf + "BEGIN");

		Map<String, String> mapVisualizzazioneMaster = new HashMap<String, String>();
		mapVisualizzazioneMaster.put("importoAmmesso", "importoSpesaAmmessa");
		mapVisualizzazioneMaster.put("importoQuietanzato", "importoSpesaQuietanziata");
		mapVisualizzazioneMaster.put("importoRendicontato", "importoSpesaRendicontata");
		mapVisualizzazioneMaster.put("importoValidato", "importoSpesaValidataTotale");
		mapVisualizzazioneMaster.put("descVoceDiSpesa", "label");
		mapVisualizzazioneMaster.put("idVoceDiSpesa", "idVoce");
		mapVisualizzazioneMaster.put("idContoEconomico", "idContoEconomico");
		mapVisualizzazioneMaster.put("idVoceDiSpesaPadre", "idVocePadre");
		mapVisualizzazioneMaster.put("idTipologiaVoceDiSpesa", "idTipologiaVoceDiSpesa");  // Cultura
		mapVisualizzazioneMaster.put("percSpGenFunz", "percSpGenFunz");            // Cultura

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO contoEconomicoDTO;
		contoEconomicoDTO = beanUtil.transform(rigo, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO.class, mapVisualizzazioneMaster);

		logger.info(prf + " >>> POST trasformazione rigo");
		if (contoEconomicoDTO != null) {
			logger.info(prf + "contoEconomicoDTO.getImportoSpesaQuietanziata=" + contoEconomicoDTO.getImportoSpesaQuietanziata());

			ContoEconomicoDTO[] fi = contoEconomicoDTO.getFigli();

			if (fi != null && fi.length > 0) {
				logger.info(prf + " fi.size()=" + fi.length);
				for (ContoEconomicoDTO f : fi) {
					logger.info(prf + " IdVoce=" + f.getIdVoce() + ",ImportoQuietanzato=" + f.getImportoSpesaQuietanziata());
				}
			}
		}

		List<RigoContoEconomicoDTO> figli = rigo.getFigli();

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO[] figliTrasformati = null;
		if (figli != null && figli.size() > 0) {
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO> listaTrasformati = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO>();
			for (RigoContoEconomicoDTO figlioCorrente : figli) {
				it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO figlioTrasformato = convertiContoEconomicoMaster(figlioCorrente);
				listaTrasformati.add(figlioTrasformato);
			}
			figliTrasformati = listaTrasformati.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO[listaTrasformati.size()]);
		}

		contoEconomicoDTO.setFigli(figliTrasformati);

		contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigo.getImportoQuietanzato(), rigo.getImportoAmmesso())));

		contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigo.getImportoValidato(), rigo.getImportoAmmesso())));

		return contoEconomicoDTO;
	}

	private int creaItem(ArrayList<ContoEconomicoItem> result, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO c, int l, int uniqueIdPadre, int uniqueIdCorrente) {

		int nextUniqueId = uniqueIdCorrente + 1;

		ContoEconomicoItem item = new ContoEconomicoItem();
		item.setIdContoEconomico(c.getIdContoEconomico());
		if (c.getIdVoce() != null) item.setIdVoce(c.getIdVoce());
		if (c.getIdVocePadre() != null) item.setIdVocePadre(c.getIdVocePadre());
		item.setId(Integer.toString(uniqueIdCorrente));
		item.setIdPadre(Integer.toString(uniqueIdPadre));
		item.setLevel(l);
		item.setType(TYPES[l >= TYPES.length ? TYPES.length - 1 : l]);
		String label = c.getLabel();
		if (label == null && l == 0) {
			label = "Conto Economico";
		}
		item.setLabel(label);
		item.setImportoSpesaAmmessa(c.getImportoSpesaAmmessa());
		item.setImportoSpesaValidataTotale(c.getImportoSpesaValidataTotale());
		item.setImportoSpesaQuietanziata(c.getImportoSpesaQuietanziata());
		item.setImportoSpesaRendicontata(c.getImportoSpesaRendicontata());
		item.setPercentualeSpesaQuietanziataSuAmmessa(c.getPercSpesaQuietanziataSuAmmessa());
		item.setPercentualeSpesaValidataSuAmmessa(c.getPercSpesaValidataSuAmmessa());
		item.setIdTipologiaVoceDiSpesa(c.getIdTipologiaVoceDiSpesa());
		result.add(item);

		if (c.getFigli() != null && c.getFigli().length > 0) {
			item.setHaFigli(true);
			for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO figlio : c.getFigli()) {
				nextUniqueId = creaItem(result, figlio, l + 1, uniqueIdCorrente, nextUniqueId);
			}
		} else {
			item.setHaFigli(false);
		}
		return nextUniqueId;
	}

	private ContoEconomicoDTO trovaContoEconomicoComplessivo(Long idProgetto) throws ContoEconomicoNonTrovatoException {

		String prf = "[ContoEconomicoDAOImpl::trovaContoEconomicoComplessivo] ";
		logger.info(prf + "BEGIN, idProgetto=" + idProgetto);

		ProgettoSoggettoPartnerVO progettoPartnerVO = new ProgettoSoggettoPartnerVO();
		progettoPartnerVO.setIdProgetto(new BigDecimal(idProgetto));

		List<BigDecimal> progettiPartner = beanUtil.extractValues(genericDAO.findListWhere(progettoPartnerVO), "idProgettoPartner", BigDecimal.class);
		progettiPartner.add(0, new BigDecimal(idProgetto));

		List<ContoEconomicoDTO> contiEconomici = new ArrayList<ContoEconomicoDTO>();
		for (BigDecimal idProgettoPartner : progettiPartner) {

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoMaster;
			contoEconomicoMaster = contoEconomicoManager.findContoEconomicoMasterPotato(idProgettoPartner);

			contiEconomici.add(convertiContoEconomicoMaster(contoEconomicoMaster));
		}

		ContoEconomicoDTO contoEconomicoComplessivo = null;
		HashMap<Long, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO> macroVoci = new LinkedHashMap<Long, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO>();
		HashMap<Long, HashMap<Long, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO>> microVoci = new HashMap<Long, HashMap<Long, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO>>();

		for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO contoEconomico : contiEconomici) {
			contoEconomicoComplessivo = lazyAccumulate(contoEconomicoComplessivo, contoEconomico);
			for (ContoEconomicoDTO macroVoce : contoEconomico.getFigli()) {
				macroVoci.put(macroVoce.getIdVoce(), lazyAccumulate(macroVoci.get(macroVoce.getIdVoce()), macroVoce));
				for (ContoEconomicoDTO microVoce : ObjectUtil.nvl(macroVoce.getFigli(), new ContoEconomicoDTO[0])) {
					if (!microVoci.containsKey(microVoce.getIdVocePadre())) {
						microVoci.put(microVoce.getIdVocePadre(), new HashMap<Long, ContoEconomicoDTO>());
					}
					// ottengo le sotto-voci della macro-voce in esame
					HashMap<Long, ContoEconomicoDTO> microvociPerMacro = microVoci.get(microVoce.getIdVocePadre());
					// ottengo la sotto-voce se esiste già o null
					ContoEconomicoDTO microvoceAccumulate = microvociPerMacro.get(microVoce.getIdVoce());
					// accumulo gli importi della sotto-voce in esame o ne creo una nuova
					microvoceAccumulate = lazyAccumulate(microvoceAccumulate, microVoce);
					// metto la nuova sotto-voce nella mappa
					microvociPerMacro.put(microVoce.getIdVoce(), microvoceAccumulate);
				}
			}
		}

		List<ContoEconomicoDTO> vociDiSpesa = new ArrayList<ContoEconomicoDTO>();

		for (Long key : macroVoci.keySet()) {
			ContoEconomicoDTO voceDiSpesa = beanUtil.transform(macroVoci.get(key), ContoEconomicoDTO.class);
			if (microVoci.containsKey(key)) {
				Collection<ContoEconomicoDTO> sottovociDiSpesa = microVoci.get(key).values();
				voceDiSpesa.setFigli(sottovociDiSpesa.toArray(new ContoEconomicoDTO[sottovociDiSpesa.size()]));
			}
			vociDiSpesa.add(voceDiSpesa);
		}

		contoEconomicoComplessivo.setFigli(vociDiSpesa.toArray(new ContoEconomicoDTO[vociDiSpesa.size()]));

		calcolaPercentuali(contoEconomicoComplessivo);

		return contoEconomicoComplessivo;
	}

	private void calcolaPercentuali(ContoEconomicoDTO contoEconomico) {
		if (contoEconomico != null && contoEconomico.getFigli() != null && contoEconomico.getFigli().length > 0) {
			for (ContoEconomicoDTO contoEconomicoFiglio : contoEconomico.getFigli()) {
				calcolaPercentuali(contoEconomicoFiglio);
			}
		}

		contoEconomico.setPercSpesaValidataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(contoEconomico.getImportoSpesaValidataTotale(), contoEconomico.getImportoSpesaAmmessa())));

		contoEconomico.setPercSpesaQuietanziataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(contoEconomico.getImportoSpesaQuietanziata(), contoEconomico.getImportoSpesaAmmessa())));
	}

	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO lazyAccumulate(it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO accumulator, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO adder) {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO accumulate = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO();

		if (accumulator == null) {
			accumulate = beanUtil.transform(adder, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO.class);
		} else {
			accumulate = beanUtil.transform(accumulator, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO.class);
			if (accumulator.getImportoSpesaAmmessa() != null || adder.getImportoSpesaAmmessa() != null) {
				accumulate.setImportoSpesaAmmessa(ObjectUtil.nvl(accumulator.getImportoSpesaAmmessa(), 0D) + ObjectUtil.nvl(adder.getImportoSpesaAmmessa(), 0D));
			}
			if (accumulator.getImportoSpesaQuietanziata() != null || adder.getImportoSpesaQuietanziata() != null) {
				accumulate.setImportoSpesaQuietanziata(ObjectUtil.nvl(accumulator.getImportoSpesaQuietanziata(), 0D) + ObjectUtil.nvl(adder.getImportoSpesaQuietanziata(), 0D));
			}
			if (accumulator.getImportoSpesaRendicontata() != null || adder.getImportoSpesaRendicontata() != null) {
				accumulate.setImportoSpesaRendicontata(ObjectUtil.nvl(accumulator.getImportoSpesaRendicontata(), 0D) + ObjectUtil.nvl(adder.getImportoSpesaRendicontata(), 0D));
			}
			if (accumulator.getImportoSpesaValidataTotale() != null || adder.getImportoSpesaValidataTotale() != null) {
				accumulate.setImportoSpesaValidataTotale(ObjectUtil.nvl(accumulator.getImportoSpesaValidataTotale(), 0D) + ObjectUtil.nvl(adder.getImportoSpesaValidataTotale(), 0D));
			}
		}

		return accumulate;
	}

	private ContoEconomicoDTO trovaContoEconomicoPartner(Long idProgetto, Long idPartner) throws ContoEconomicoNonTrovatoException {

		ProgettoSoggettoPartnerVO progettoPartner = new ProgettoSoggettoPartnerVO();
		progettoPartner.setIdProgettoPartner(new BigDecimal(idPartner));
		progettoPartner = genericDAO.findSingleWhere(progettoPartner);
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoMaster;
		contoEconomicoMaster = contoEconomicoManager.findContoEconomicoMasterPotato(progettoPartner.getIdProgettoPartner());

		ContoEconomicoDTO contoEconomicoDTO = convertiContoEconomicoMaster(contoEconomicoMaster);
		return contoEconomicoDTO;
	}

	public ArrayList<ContoEconomicoItem> trasformaPerVisualizzazione(ContoEconomicoDTO conto) {

		if (conto == null) return null;

		ArrayList<ContoEconomicoItem> result = new ArrayList<ContoEconomicoItem>();
		creaItem(result, conto, 0, 0, 1);
		return result;
	}

	private Long idBandoLineaDaIdProgetto(Long idProgetto) {

		if (idProgetto == null) return null;

		// Codice visualizzato del progetto.
		String sql = "SELECT PROGR_BANDO_LINEA_INTERVENTO FROM PBANDI_T_DOMANDA WHERE ID_DOMANDA = (SELECT ID_DOMANDA FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = " + idProgetto + ") ";
		Long idBandoLinea = getJdbcTemplate().queryForObject(sql, Long.class);
		return idBandoLinea;
	}

	@Override
	public VociDiSpesaCulturaDTO vociDiSpesaCultura(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::vociDiSpesaCultura] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		VociDiSpesaCulturaDTO result = new VociDiSpesaCulturaDTO();
		try {

			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[]{idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);

			// CONTO ECONOMICO LETTO DA DB.

			EsitoFindContoEconomicoDTO esito = this.findContoEconomicoPerRimodulazione(idProgetto, idUtente, idIride);
			result.setEsitoFindContoEconomicoDTO(esito);

			// DATI DEL CONTO ECONOMICO.

			ContoEconomico conto = this.getDatiContoEconomico(esito, false);
			result.setDatiContoEconomico(conto);

			logger.info(prf + result.toString());

			// PERCENTUALE SPESE GENERALI DI FUNZIONAMENTO
			try {
				sql = "SELECT perc_sp_gen_funz FROM PBANDI_T_CONTO_ECONOMICO WHERE ID_CONTO_ECONOMICO = ?";
				param = new Object[]{esito.getContoEconomico().getIdContoEconomico()};
				result.setPercSpGenFunz(getJdbcTemplate().queryForObject(sql, param, Double.class));
				if (result.getPercSpGenFunz() == null) throw new Exception();
			} catch (Exception e) {
				logger.error("Manca la percentuale spese generali di funzionamento nel db!");
			}

			// contributo concesso
			try {
				sql = "SELECT quota_importo_agevolato FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV WHERE ID_CONTO_ECONOMICO = ? ORDER BY ID_MODALITA_AGEVOLAZIONE";
				param = new Object[]{esito.getContoEconomico().getIdContoEconomico()};
				List<Double> contributiConcessi = getJdbcTemplate().queryForList(sql, param, Double.class);
				if (contributiConcessi == null || contributiConcessi.isEmpty()) throw new Exception();
				result.setContributoConcesso(contributiConcessi.get(0));
			} catch (Exception e) {
				logger.error("Manca il contributo concesso (PBANDI_R_CONTO_ECONOM_MOD_AGEV.quota_importo_agevolato) nel db!");
			}
		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		} finally {
			logger.info(prf + " END");
		}

		return result;
	}

	private EsitoFindContoEconomicoDTO findContoEconomicoPerRimodulazione(Long idProgetto, Long idUtente, String idIride) throws SystemException, UnrecoverableException, CSIException {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progetto.setIdProgetto(idProgetto);

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esito;
		esito = contoeconomicoBusinessImpl.findContoEconomicoPerRimodulazione(idUtente, null, idIride, progetto);

		return esito;
	}

	private EsitoFindContoEconomicoCulturaDTO findContoEconomicoVociDiEntrata(Long idProgetto, Long idUtente, String idIride) throws SystemException, UnrecoverableException, CSIException {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progetto.setIdProgetto(idProgetto);


		EsitoFindContoEconomicoCulturaDTO esito;
		esito = contoeconomicoBusinessImpl.findContoEconomicoPerRimodulazioneCultura(idUtente, 3, idIride, progetto);

		return esito;
	}

	private ContoEconomico getDatiContoEconomico(EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO, boolean isModifica) throws Exception {

		Boolean copiaPresente = esitoFindContoEconomicoDTO.getCopiaModificataPresente();

		ContoEconomico contoEconomico = new ContoEconomico();

		contoEconomico.setHasCopiaPresente(copiaPresente);

		contoEconomico.setDataFineIstruttoria(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataFineIstruttoria()));

		contoEconomico.setDataPresentazioneDomanda(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataPresentazioneDomanda()));

		contoEconomico.setDataUltimaProposta(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataUltimaProposta()));

		contoEconomico.setDataUltimaRimodulazione(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataUltimaRimodulazione()));

		return contoEconomico;
	}

	private ContoEconomico getDatiContoEconomicoCultura(EsitoFindContoEconomicoCulturaDTO esitoFindContoEconomicoDTO, boolean isModifica) throws Exception {

		Boolean copiaPresente = esitoFindContoEconomicoDTO.getCopiaModificataPresente();

		ContoEconomico contoEconomico = new ContoEconomico();

		contoEconomico.setHasCopiaPresente(copiaPresente);

		contoEconomico.setDataFineIstruttoria(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataFineIstruttoria()));

		contoEconomico.setDataPresentazioneDomanda(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataPresentazioneDomanda()));

		contoEconomico.setDataUltimaProposta(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataUltimaProposta()));

		contoEconomico.setDataUltimaRimodulazione(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataUltimaRimodulazione()));

		return contoEconomico;
	}

	@Override
	public VociDiEntrataCulturaDTO vociDiEntrataCultura(Long idProgetto, Long idUtente, String idIride) throws Exception {
		String prf = "[ContoEconomicoDAOImpl::vociDiEntrataCultura] ";
		logger.info(prf + " BEGIN");

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		String idContoEconomicoAmmessoFinanziamento;
		try {
			String sql = "SELECT ID_CONTO_ECONOMICO FROM PBANDI_T_CONTO_ECONOMICO ptce JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptce.ID_DOMANDA JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptd.ID_DOMANDA WHERE PTP.ID_PROGETTO = ? AND ptce.ID_STATO_CONTO_ECONOMICO = 7 AND ROWNUM <= 1";
			Object[] param = new Object[]{idProgetto};
			idContoEconomicoAmmessoFinanziamento = getJdbcTemplate().queryForObject(sql, param, String.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("Errore durante la ricerca del conto economico.");
		}

		logger.info(prf + " END");
		return findRigheVociDiEntrataByIDContoEconomico(idContoEconomicoAmmessoFinanziamento);
	}

	private VociDiEntrataCulturaDTO findRigheVociDiEntrataByIDContoEconomico(String idContoEconomico) {
		List<ConsuntivoEntrataDTO> consuntivoEntrate = new ArrayList<>();
		VociDiEntrataCulturaDTO result = new VociDiEntrataCulturaDTO();
		try {
			String sql = new FileSqlUtil().getQuery("CEVociDiEntrataCultura.sql");
			ArrayList<VoceDiEntrataCulturaDTO> vociDiEntrataCultura = new ArrayList<>(getJdbcTemplate().query(sql, new Object[]{idContoEconomico}, new VoceDiEntrataCulturaRowMapper()));

			sql = "SELECT ptce.*, ve.DESCRIZIONE, ve.FLAG_EDIT FROM PBANDI_T_CONSUNTIVO_ENTRATA ptce LEFT JOIN PBANDI_D_VOCE_DI_ENTRATA ve ON ve.ID_VOCE_DI_ENTRATA = ptce.ID_VOCE_DI_ENTRATA WHERE ptce.ID_RIGO_CONTO_ECONOMICO = ?";
			for (VoceDiEntrataCulturaDTO voce : vociDiEntrataCultura) {
				if (voce.getIdRigoContoEconomico() > 0) {
					List<ConsuntivoEntrataDTO> temp = getJdbcTemplate().query(sql, new Object[]{voce.getIdRigoContoEconomico()}, new BeanPropertyRowMapper<>(ConsuntivoEntrataDTO.class));
					if (temp != null && !temp.isEmpty()) consuntivoEntrate.addAll(temp);
				}
			}
			result.setVociDiEntrataCultura(new ArrayList<>(setUnisciVociProposta(vociDiEntrataCultura, consuntivoEntrate)));

		} catch (IOException | DataAccessException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	private VociDiEntrataCulturaDTO findRigheVociDiEntrataByIDProgetto(String idProgetto) {
		logger.info("[ContoEconomicoDAOImpl::findRigheVociDiEntrataByIDProgetto] BEGIN");
		List<ConsuntivoEntrataDTO> consuntivoEntrate = new ArrayList<>();
		VociDiEntrataCulturaDTO result = new VociDiEntrataCulturaDTO();
		try {
			String sql = new FileSqlUtil().getQuery("CEVociDiEntrataCulturaPerRimodulazione.sql");
			ArrayList<VoceDiEntrataCulturaDTO> vociDiEntrataCultura = new ArrayList<>(getJdbcTemplate().query(sql, new Object[]{idProgetto, idProgetto, idProgetto}, new VoceDiEntrataCulturaRowMapper()));

			sql = "SELECT ptce.*, ve.DESCRIZIONE, ve.FLAG_EDIT FROM PBANDI_T_CONSUNTIVO_ENTRATA ptce LEFT JOIN PBANDI_D_VOCE_DI_ENTRATA ve ON ve.ID_VOCE_DI_ENTRATA = ptce.ID_VOCE_DI_ENTRATA WHERE ptce.ID_RIGO_CONTO_ECONOMICO = ?";
			for (VoceDiEntrataCulturaDTO voce : vociDiEntrataCultura) {
				if (voce.getIdRigoContoEconomico() > 0) {
					List<ConsuntivoEntrataDTO> temp = getJdbcTemplate().query(sql, new Object[]{voce.getIdRigoContoEconomico()}, new BeanPropertyRowMapper<>(ConsuntivoEntrataDTO.class));
					if (temp != null && !temp.isEmpty()) consuntivoEntrate.addAll(temp);
				}
			}
			result.setVociDiEntrataCultura(new ArrayList<>(setUnisciVociProposta(vociDiEntrataCultura, consuntivoEntrate)));

		} catch (IOException | DataAccessException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		logger.info("[ContoEconomicoDAOImpl::findRigheVociDiEntrataByIDProgetto] END");
		return result;
	}

	private ArrayList<VoceDiEntrataCulturaDTO> setUnisciVociProposta(ArrayList<VoceDiEntrataCulturaDTO> vociDiEntrataCultura, List<ConsuntivoEntrataDTO> vociDiEntrataCulturaProposta) {
		if (vociDiEntrataCultura == null || vociDiEntrataCultura.isEmpty()) {
			throw new IllegalArgumentException("vociDiEntrataCultura e vociDiEntrataCulturaProposta non possono essere nulli o vuoti!");
		}
		ArrayList<VoceDiEntrataCulturaDTO> temp = new ArrayList<>(vociDiEntrataCultura);

		for (ConsuntivoEntrataDTO voce : vociDiEntrataCulturaProposta) {
			temp.add(convertiVociDiEntrata(voce));
		}

		return temp;

	}

	@Override
	public boolean salvaVociDiEntrataCultura(List<VoceDiEntrataCulturaDTO> vociDiEntrata, Long idProgetto, Long idUtente, String idIride) {
		boolean result = true;
		for (VoceDiEntrataCulturaDTO voce : vociDiEntrata) {
			logger.debug(voce);
			if (voce.getIdConsuntivoEntrata() > 0) {
				PbandiTConsuntivoEntrataVO vo = genericDAO.findSingleOrNoneWhere(new PbandiTConsuntivoEntrataVO(voce.getIdConsuntivoEntrata()));
				if (vo != null) {
					vo.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
					vo.setDtModifica(new java.sql.Date(new Date().getTime()));
					vo.setImportoEntrata(voce.getImportoAmmesso());
					vo.setCompletamento(voce.getCompletamento());
				}
        try {
          genericDAO.update(vo);
        } catch (Exception e) {
					logger.error(e.toString());
					logger.error("Errore durante l'aggiornamento della voce di entrata" + vo);
					result = false;
        }
			} else if (voce.getIdRigoContoEconomico() > 0) {

				//controllo che non si possano inserire più righe se flag edit è diverso da S
				PbandiTRigoContoEconomicoVO voRigo = genericDAO.findSingleOrNoneWhere(new PbandiTRigoContoEconomicoVO(new BigDecimal(voce.getIdRigoContoEconomico())));
				if (voRigo == null)
					throw new RuntimeException("Non esiste un rigo conto economico con id " + voce.getIdRigoContoEconomico());
				PbandiDVoceDiEntrataVO voVoce = genericDAO.findSingleOrNoneWhere(new PbandiDVoceDiEntrataVO(voRigo.getIdVoceDiEntrata()));
				if (voVoce == null)
					throw new RuntimeException("Non esiste una voce di entrata con id " + voRigo.getIdVoceDiEntrata());

				PbandiTConsuntivoEntrataVO vo = new PbandiTConsuntivoEntrataVO();
				vo.setIdRigoContoEconomico(BigDecimal.valueOf(voce.getIdRigoContoEconomico()));
				if (voVoce.getFlagEdit() == null) {
					List<PbandiTConsuntivoEntrataVO> check = genericDAO.findListWhere(vo);
					if (!check.isEmpty())
						throw new RuntimeException("Esiste già una voce di entrata per il rigo conto economico " + voce.getIdRigoContoEconomico());
				}

				vo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				vo.setDtInizioValidita(new java.sql.Date(new Date().getTime()));
				vo.setIdVoceDiEntrata(BigDecimal.valueOf(voce.getIdVoceDiEntrata()));
				vo.setImportoEntrata(voce.getImportoAmmesso());
				vo.setCompletamento(voce.getCompletamento());
				try {
					genericDAO.insert(vo);
				} catch (Exception e) {
					logger.error("Errore durante l'inserimento della voce di entrata" + vo);
					logger.error(e.toString());
					result = false;
				}
			} else {
				//trovo id conto economico approvato in istruttoria
				String sql = "SELECT\n" + "\tce.ID_CONTO_ECONOMICO \n" + "FROM\n" + "\tPBANDI_T_CONTO_ECONOMICO ce\n" + "JOIN PBANDI_T_DOMANDA ptd ON\n" + "\tptd.ID_DOMANDA = ce.ID_DOMANDA\n" + "JOIN PBANDI_T_PROGETTO ptp ON\n" + "\tptp.ID_DOMANDA = ptd.ID_DOMANDA\n" + "WHERE\n" + "ce.ID_STATO_CONTO_ECONOMICO IN (7)\n" + "\tAND ptp.ID_PROGETTO = ?";
				Object[] param = new Object[]{idProgetto};
				BigDecimal idContoEconomico = getJdbcTemplate().queryForObject(sql, param, BigDecimal.class);
				if (idContoEconomico == null || idContoEconomico.compareTo(BigDecimal.ZERO) <= 0)
					throw new RuntimeException("Non esiste un conto economico per il progetto " + idProgetto);

				//controllo che non esista già una riga
				PbandiTConsuntivoEntrataVO vo = new PbandiTConsuntivoEntrataVO();
				PbandiTRigoContoEconomicoVO voRigo = new PbandiTRigoContoEconomicoVO();
				voRigo.setIdVoceDiEntrata(BigDecimal.valueOf(voce.getIdVoceDiEntrata()));
				voRigo.setIdContoEconomico(idContoEconomico);
				List<PbandiTRigoContoEconomicoVO> listaRighe = genericDAO.findListWhere(voRigo);
				PbandiTRigoContoEconomicoVO rigoEsistente = new PbandiTRigoContoEconomicoVO();

				if (listaRighe.isEmpty()) {
					//aggiungo un nuovo rigo
					voRigo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
					voRigo.setDtInizioValidita(new java.sql.Date(new Date().getTime()));
					try {
						rigoEsistente = genericDAO.insert(voRigo);
					} catch (Exception e) {
						logger.error("Errore durante l'inserimento del rigo conto economico" + e);
						throw new RuntimeException(e);
					}
				} else rigoEsistente = listaRighe.get(0);

				//controllo flag
				PbandiDVoceDiEntrataVO voVoce = genericDAO.findSingleOrNoneWhere(new PbandiDVoceDiEntrataVO(voRigo.getIdVoceDiEntrata()));
				if (voVoce == null)
					throw new RuntimeException("Non esiste una voce di entrata con id " + voRigo.getIdVoceDiEntrata());

				if (voVoce.getFlagEdit() == null) {
					vo.setIdRigoContoEconomico(rigoEsistente.getIdRigoContoEconomico());
					List<PbandiTConsuntivoEntrataVO> check = genericDAO.findListWhere(vo);
					if (!check.isEmpty())
						throw new RuntimeException("Esiste già una voce di entrata per il rigo conto economico " + voce.getIdRigoContoEconomico());
				}

				//inserisco riga
				vo.setIdRigoContoEconomico(rigoEsistente.getIdRigoContoEconomico());
				vo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				vo.setDtInizioValidita(new java.sql.Date(new Date().getTime()));
				vo.setIdVoceDiEntrata(BigDecimal.valueOf(voce.getIdVoceDiEntrata()));
				vo.setImportoEntrata(voce.getImportoAmmesso());
				vo.setCompletamento(voce.getCompletamento());

				try {
					genericDAO.insert(vo);
				} catch (Exception e) {
					logger.error("Errore durante l'inserimento della voce di entrata" + vo);
					result = false;
				}

			}
		}

		return result;
	}

	@Override
	public Double getPercentualeImportoAgevolato(Long idBando, Long idUtente) {
		String sql = "SELECT DISTINCT PERCENTUALE_IMPORTO_AGEVOLATO FROM PBANDI_R_BANDO_MODALITA_AGEVOL agevolazione WHERE ID_BANDO = ?";
		return getJdbcTemplate().queryForObject(sql, new Object[]{idBando}, Double.class);
	}

	@Override
	public boolean salvaSpesePreventivate(ArrayList<VoceDiSpesaPreventivataDTO> vociDiSpesa, Long idUtente, String idIride) {
		boolean result = true;
		for (VoceDiSpesaPreventivataDTO voceDiSpesa : vociDiSpesa) {
			PbandiTSpesaPreventivataVO vo = new PbandiTSpesaPreventivataVO();

			if (voceDiSpesa.getImportoSpesaPreventivata().compareTo(BigDecimal.ZERO) >= 0) {
				vo.setImportoSpesaPreventivata(voceDiSpesa.getImportoSpesaPreventivata());
			}
			if (voceDiSpesa.getIdRigoContoEconomico() != null && voceDiSpesa.getIdRigoContoEconomico().compareTo(BigDecimal.ZERO) > 0) {
				vo.setIdRigoContoEconomico(voceDiSpesa.getIdRigoContoEconomico());
			} else {
				//controllo che non ci sia un problema di invio e in realtà esiste già un rigo conto economico
				PbandiTRigoContoEconomicoVO rigo = new PbandiTRigoContoEconomicoVO();
				rigo.setIdVoceDiSpesa(voceDiSpesa.getIdVoceDiSpesa());
				rigo.setIdContoEconomico(voceDiSpesa.getIdContoEconomico());
				PbandiTRigoContoEconomicoVO rigoEsistente = genericDAO.findSingleOrNoneWhere(rigo);
				if (rigoEsistente != null) {
					//ci sta già una riga, quindi per sbaglio mi è arrivato un idRigo non valorizzato
					logger.warn("IdRigoContoEconomico non valorizzato ma esiste già una riga per la voce di spesa " + voceDiSpesa.getIdVoceDiSpesa());
					vo.setIdRigoContoEconomico(rigoEsistente.getIdRigoContoEconomico());
				} else {
					//aggiungo una nuova riga al conto economico
					rigo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
					rigo.setDtInizioValidita(new java.sql.Date(new Date().getTime()));
					try {
						rigo = genericDAO.insert(rigo);
					} catch (Exception e) {
						logger.error("Errore durante l'inserimento del rigo conto economico" + e);
						return false;
					}
					if (rigo.getIdRigoContoEconomico() != null && rigo.getIdRigoContoEconomico().compareTo(BigDecimal.ZERO) > 0)
						vo.setIdRigoContoEconomico(rigo.getIdRigoContoEconomico());
					else {
						logger.error("Errore l'ottenimento dell'id rigo conto economico" + rigo);
						return false;
					}
				}
			}
			result = contoeconomicoBusinessImpl.updateOrAddSpesaPreventivata(vo, idUtente);
		}
		return result;
	}

	@Override
	// Crea e salva il pdf di una nuova Dichiarazione di Spesa.
	// Ex CPBEPDichiarazioneDiSpesa.goToCreaDichiarazioneSpesa()
	@Transactional(rollbackFor = {Exception.class})
	public EsitoOperazioneInviaDichiarazioneDTO inviaDichiarazioneDiSpesaCultura(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inviaDichiarazioneDiSpesaCultura] ";
		LOG.info(prf + "BEGIN");

		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}

		// Legge i parametri dal multipart, eccetto il file della relazione tecnica.
		Long idBandoLinea = multipartFormData.getFormDataPart("idBandoLinea", Long.class, null);
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		Long idProgettoContributoPiuGreen = multipartFormData.getFormDataPart("idProgettoContributoPiuGreen", Long.class, null); // non obbligatorio.
		Long idSoggetto = multipartFormData.getFormDataPart("idSoggetto", Long.class, null);
		Long idSoggettoBeneficiario = multipartFormData.getFormDataPart("idSoggettoBeneficiario", Long.class, null);
		Long idRappresentanteLegale = multipartFormData.getFormDataPart("idRappresentanteLegale", Long.class, null);
		Long idDelegato = multipartFormData.getFormDataPart("idDelegato", Long.class, null); // non obbligatorio.
		String codiceTipoDichiarazioneDiSpesa = multipartFormData.getFormDataPart("codiceTipoDichiarazioneDiSpesa", String.class, null);
		String note = multipartFormData.getFormDataPart("note", String.class, null);
		Double importoRichiestaSaldo = multipartFormData.getFormDataPart("importoRichiestaSaldo", Double.class, null);
		Boolean isBR58 = multipartFormData.getFormDataPart("isBR58", Boolean.class, null);

		// Legge i dati della declaratoria
		DeclaratoriaDTO declaratoriaDTO = new DeclaratoriaDTO();
		String stringaDeclaratoria = multipartFormData.getFormDataPart("allegatiCultura", String.class, null);
		if (!StringUtil.isBlank(stringaDeclaratoria)) {
			ObjectMapper mapper = new ObjectMapper();
			declaratoriaDTO = mapper.readValue(stringaDeclaratoria, new TypeReference<DeclaratoriaDTO>() {
			});
		}

		//creo conto economico proposta se non esiste
		contoEconomicoManager.findContoEconomicoProposta(BigDecimal.valueOf(idProgetto), idUtente);

		// La data arriva come stringa "dd/MM/yyyy".
		String stringDataLimiteDocumentiRendicontabili = multipartFormData.getFormDataPart("dataLimiteDocumentiRendicontabili", String.class, null);
		stringDataLimiteDocumentiRendicontabili = DateUtil.getData();
		Date dataLimiteDocumentiRendicontabili = DateUtil.getDate(stringDataLimiteDocumentiRendicontabili);

		// Legge la relazione tecnica dal multipart; non obbligatoria.
		String nomeFileRelazioneTecnica = null;
		byte[] bytesRelazioneTecnica = null;
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> listInputPart = map.get("fileRelazioneTecnica");
		if (listInputPart != null && listInputPart.size() > 0) {
			ArrayList<FileDTO> listFileDTO = FileUtil.leggiFilesDaMultipart(listInputPart, null);
			if (listFileDTO != null && listFileDTO.size() > 0) {
				nomeFileRelazioneTecnica = listFileDTO.get(0).getNomeFile();
				bytesRelazioneTecnica = listFileDTO.get(0).getBytes();
			}
		}

		// Legge la lista dei tipo allegati dal multipart; non obbligatoria.
		ArrayList<TipoAllegatoDTO> listaTipoAllegati = new ArrayList<TipoAllegatoDTO>();
		String stringa = multipartFormData.getFormDataPart("listaTipoAllegati", String.class, null);
		if (!StringUtil.isBlank(stringa)) {
			ObjectMapper mapper = new ObjectMapper();
			List<TipoAllegatoDTO> lista = mapper.readValue(stringa, new TypeReference<List<TipoAllegatoDTO>>() {
			});
			listaTipoAllegati = (ArrayList<TipoAllegatoDTO>) lista;
		}

		// Un po' di log.
		LOG.info(prf + "input idBandoLinea = " + idBandoLinea);
		LOG.info(prf + "input idProgetto = " + idProgetto);
		LOG.info(prf + "input idProgettoContributoPiuGreen = " + idProgettoContributoPiuGreen);
		LOG.info(prf + "input idSoggetto = " + idSoggetto);
		LOG.info(prf + "input idRappresentanteLegale = " + idRappresentanteLegale);
		LOG.info(prf + "input idDelegato = " + idDelegato);
		LOG.info(prf + "input dataLimiteDocumentiRendicontabili stringa = " + stringDataLimiteDocumentiRendicontabili);
		LOG.info(prf + "input dataLimiteDocumentiRendicontabili date = " + dataLimiteDocumentiRendicontabili);
		LOG.info(prf + "input codiceTipoDichiarazioneDiSpesa = " + codiceTipoDichiarazioneDiSpesa);
		LOG.info(prf + "input nomeFileRelazioneTecnica = " + nomeFileRelazioneTecnica);
		LOG.info(prf + "input importoRichiestaSaldo = " + importoRichiestaSaldo);
		LOG.info(prf + "input note = " + note);
		if (bytesRelazioneTecnica == null) LOG.info(prf + "input bytesRelazioneTecnica = NULL");
		else LOG.info(prf + "input bytesRelazioneTecnica.length = " + bytesRelazioneTecnica.length);
		if (listaTipoAllegati == null) {
			LOG.info(prf + "input listaTipoAllegati = NULL");
		} else {
			LOG.info(prf + "input listaTipoAllegati.size = " + listaTipoAllegati.size());
			for (TipoAllegatoDTO dto : listaTipoAllegati)
				LOG.info(prf + "   input TipoAllegato: " + dto.getFlagAllegato() + " - " + dto.getDescTipoAllegato());
		}

		// Verifica campi obbligatori.
		if (idBandoLinea == null) {
			throw new InvalidParameterException("IdBandoLinea non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("IdProgetto non valorizzato");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("IdSoggetto non valorizzato");
		}
		if (idRappresentanteLegale == null) {
			throw new InvalidParameterException("idRappresentanteLegale non valorizzato");
		}
		if (dataLimiteDocumentiRendicontabili == null) {
			throw new InvalidParameterException("dataLimiteDocumentiRendicontabili non valorizzato");
		}
		if (StringUtil.isBlank(codiceTipoDichiarazioneDiSpesa)) {
			throw new InvalidParameterException("CodiceTipoDichiarazioneDiSpesa non valorizzato");
		}
		if (!StringUtil.isBlank(nomeFileRelazioneTecnica) && (bytesRelazioneTecnica == null || bytesRelazioneTecnica.length == 0)) {
			throw new InvalidParameterException("BytesRelazioneTecnica non valorizzato");
		}
		if (bytesRelazioneTecnica != null && bytesRelazioneTecnica.length > 0 && StringUtil.isBlank(nomeFileRelazioneTecnica)) {
			throw new InvalidParameterException("NomeFileRelazioneTecnica non valorizzato");
		}

		EsitoOperazioneInviaDichiarazioneDTO esito = new EsitoOperazioneInviaDichiarazioneDTO();
		esito.setEsito(true);
		try {

			// Salva l'elenco dei tipoAllegato prima di procedere con l'enteprima.
			EsitoDTO esitoTipoAllegati = eseguiSalvaTipoAllegati(listaTipoAllegati, codiceTipoDichiarazioneDiSpesa, idUtente, idIride);
			if (esitoTipoAllegati == null || !esitoTipoAllegati.getEsito()) {
				throw new Exception("Errore nel salvataggio dei tipo allegati.");
			}

			// controllo che sia privato cittadino e cerco nella tabella adeguata

			boolean isPrivatoCittadino = this.getIsBeneficiarioPrivatoCittadino(idProgetto, idUtente, idIride);
			RappresentanteLegaleDTO rappresentante = null;

			if (isPrivatoCittadino) {
				// Recupera i dati del privato cittadino e lo mette nella variabile
				// rappresenatnti legali
				rappresentante = getPrivatoCittadinoForRappresentanteLegale(idSoggettoBeneficiario);
				IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = getIndirizzoPrivatoCittadino(idSoggettoBeneficiario, idProgetto);
				if (indirizzoPrivatoCittadino != null) {
					rappresentante.setIndirizzoResidenza(indirizzoPrivatoCittadino.getDescIndirizzo());
					rappresentante.setCapResidenza(indirizzoPrivatoCittadino.getCap());
					rappresentante.setIdProvinciaResidenza(indirizzoPrivatoCittadino.getIdProvincia());
					rappresentante.setIdComuneResidenza(indirizzoPrivatoCittadino.getIdComune());
				}

			} else {
				// Recupera i dati del Rappresentante Legale (se il servizio ne restituisce
				// molti, prende il primo).
				RappresentanteLegaleDTO[] lista = null;
				lista = findRappresentantiLegali(idUtente, idIride, idProgetto, idRappresentanteLegale);
				if (lista != null && lista.length > 0) {
					rappresentante = lista[0];
				}
			}

			DichiarazioneDiSpesaDTO dichSpesaDto;
			dichSpesaDto = new DichiarazioneDiSpesaDTO();
			dichSpesaDto.setIdBandoLinea(idBandoLinea);
			dichSpesaDto.setIdProgetto(idProgetto);
			dichSpesaDto.setIdSoggetto(idSoggettoBeneficiario);
			dichSpesaDto.setDataFineRendicontazione(dataLimiteDocumentiRendicontabili);
			dichSpesaDto.setTipoDichiarazione(codiceTipoDichiarazioneDiSpesa);
			dichSpesaDto.setIdProgettoContributoPiuGreen(idProgettoContributoPiuGreen);

			RelazioneTecnicaDTO relazTecnica = null;
			if (!StringUtil.isBlank(nomeFileRelazioneTecnica)) {
				relazTecnica = new RelazioneTecnicaDTO();
				relazTecnica.setByteAllegato(bytesRelazioneTecnica);
				relazTecnica.setNomeFile(nomeFileRelazioneTecnica);
			}

			boolean isFinale = (Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE.equalsIgnoreCase(codiceTipoDichiarazioneDiSpesa));

			ComunicazioneFineProgettoDTO cfpDto = null;
			if (isFinale) {
				// cfpDto = popolaComunicazioneFineProgettoDTO(request);
				cfpDto = new ComunicazioneFineProgettoDTO();
				cfpDto.setIdDelegato(idDelegato);
				cfpDto.setIdProgetto(idProgetto);
				cfpDto.setIdProgettoContributoPiuGreen(idProgettoContributoPiuGreen);
				cfpDto.setIdRappresentanteLegale(idRappresentanteLegale);
				cfpDto.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
				cfpDto.setImportoRichiestaSaldo(importoRichiestaSaldo);
				cfpDto.setNote(note);
				// Campi che non servono.
				cfpDto.setCfBeneficiario(null); // FORSE NON SERVE, PER IL MOMENTO LO LASCIO A NULL.
				cfpDto.setCodiceProgetto(null);
			}

			// Nota: String codUtente e IstanzaAttivitaDTO istanzaAttivita non sono usati.
			String codUtente = null;
			IstanzaAttivitaDTO istanza = null;

			EsitoOperazioneInviaDichiarazione esitoSrv;
			esitoSrv = dichiarazioneDiSpesaCulturaBusinessImpl.inviaDichiarazioneDiSpesaCultura(idUtente, idIride, dichSpesaDto, codUtente, istanza, rappresentante, idDelegato, relazTecnica, cfpDto, declaratoriaDTO);

			esito.setEsito(esitoSrv.getEsito());
			if (esito.getEsito()) {

				// UPDATE R PROGETTO ITER CON ID DICHIARAZIONE
				if (isBR58 != null && isBR58.equals(Boolean.TRUE)) {
					if (aggiornaProgettoIter(idProgetto, esitoSrv.getDichiarazioneDTO().getIdDichiarazioneSpesa()).equals(Boolean.TRUE)) {
						esito.setMsg(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
					} else {
						esito.setEsito(Boolean.FALSE);
						esito.setMsg("Errore in fase di aggiornamento della fase del cronoprogramma.");
					}
				} else {
					esito.setMsg(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
				}
			} else {
				esito.setMsg(TraduttoreMessaggiPbandisrv.traduci(esitoSrv.getMsg()));
			}
			LOG.info(prf + "Esito = " + esito.getEsito() + "; msg = " + esito.getMsg());

			// NOTA: nel caso di DS Finale, esitoSrv.IdDichiarazioneSpesa contiene l'id
			// della CFP.
			// IdDichiarazioneSpesa serve ad Angular per ricostruire il nome del pdf
			// che viene scritto nella pagina di upload del file firmato.

			if (esitoSrv.getDichiarazioneDTO() != null) {
				esito.setNomeFileDichiarazioneSpesa(esitoSrv.getDichiarazioneDTO().getNomeFile());
				esito.setIdDocumentoIndex(esitoSrv.getDichiarazioneDTO().getIdDocIndex());
				esito.setIdDichiarazioneSpesa(esitoSrv.getDichiarazioneDTO().getIdDichiarazioneSpesa());
				LOG.info(prf + "IdDocIndex = " + esito.getIdDocumentoIndex() + "; NoneFile = " + esito.getNomeFileDichiarazioneSpesa() + "; IdDichiarazioneSpesa = " + esito.getIdDichiarazioneSpesa());
			}

			if (esitoSrv.getDichiarazionePiuGreenDTO() != null) {
				esito.setNomeFileDichiarazioneSpesaPiuGreen(esitoSrv.getDichiarazionePiuGreenDTO().getNomeFile());
				esito.setIdDocumentoIndexPiuGreen(esitoSrv.getDichiarazionePiuGreenDTO().getIdDocIndex());
				esito.setIdDichiarazioneSpesaPiuGreen(esitoSrv.getDichiarazionePiuGreenDTO().getIdDichiarazioneSpesa());
				LOG.info(prf + "IdDocIndexPiuGreen = " + esito.getIdDocumentoIndexPiuGreen() + "; NoneFilePiuGreen = " + esito.getNomeFileDichiarazioneSpesaPiuGreen() + "; IdDichiarazioneSpesaPiuGreen = " + esito.getIdDichiarazioneSpesaPiuGreen());
			}

//			inviaPropostaRimodulazione(idUtente, identitaDigitale, istanza, datiPerInvioPropostaRimodulazione);
			InviaPropostaRimodulazioneRequest request = new InviaPropostaRimodulazioneRequest();
			request.setIdProgetto(idProgetto);
			request.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
			inviaPropostaRimodulazione(request, idUtente, idIride);

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE nell'invio della Dichiarazione di Spesa: ", e);
			throw new DaoException(" ERRORE nell'invio della Dichiarazione di Spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;

	}


	public EsitoInviaPropostaRimodulazioneDTO inviaPropostaRimodulazione(InviaPropostaRimodulazioneRequest inviaPropostaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inviaPropostaRimodulazione] ";
		logger.info(prf + " BEGIN");

		if (inviaPropostaRimodulazioneRequest == null) {
			throw new InvalidParameterException("inviaPropostaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		EsitoInviaPropostaRimodulazioneDTO esitoInvia = new EsitoInviaPropostaRimodulazioneDTO();
		try {

			// Salvo i tipi allegato
			TipoAllegatoDTO[] tipoAllegati = inviaPropostaRimodulazioneRequest.getTipiAllegato();
			if (tipoAllegati != null && tipoAllegati.length > 0) {
				LOG.info(prf + "Salvataggio tipi allegato - inizio");
				for (int i = 0; i < tipoAllegati.length; i++) {
					tipoAllegati[i].setIdProgetto(inviaPropostaRimodulazioneRequest.getIdProgetto());
				}
				EsitoOperazioneSalvaTipoAllegati esitoTipiAllegati;
				esitoTipiAllegati = gestioneDatiGeneraliBusinessImpl.salvaTipoAllegati(idUtente, idIride, tipoAllegati);
				if (esitoTipiAllegati == null || !esitoTipiAllegati.getEsito()) {
					throw new Exception("Errore durante il salvataggio dei tipi allegato.");
				}
				LOG.info(prf + "Salvataggio tipi allgato - fine");
			} else {
				LOG.info(prf + "Nessun tipo allgato in input.");
			}

			//salvo le procedure di aggiudicazione
			Long idProceduraAggiudicazione = null;
			String idDelegato = ((inviaPropostaRimodulazioneRequest.getIdDelegato() == null) ? null : inviaPropostaRimodulazioneRequest.getIdDelegato().toString());
			EsitoInvioPropostaRimodulazioneDTO esito = inviaPropostaRimodulazione(idUtente, idIride, inviaPropostaRimodulazioneRequest.getListaModalitaAgevolazione(), inviaPropostaRimodulazioneRequest.getIdProgetto(), inviaPropostaRimodulazioneRequest.getIdSoggettoBeneficiario(), inviaPropostaRimodulazioneRequest.getNote(), inviaPropostaRimodulazioneRequest.getIdRapprensentanteLegale(), inviaPropostaRimodulazioneRequest.getIdContoEconomico(), inviaPropostaRimodulazioneRequest.getImportoFinanziamentoRichiesto(), idProceduraAggiudicazione, idDelegato);

			esitoInvia.setEsito(true);
			esitoInvia.setIdDocumentoIndex(esito.getIdDocIndex());
			esitoInvia.setIdContoEconomico(esito.getIdContoEconomicoLocal());
			esitoInvia.setNomeFile(esito.getFileName());
			esitoInvia.setDataProposta(DateUtil.getDataOdierna());

			logger.info(prf + esitoInvia.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw new Exception("Invio proposta fallito.");
		} finally {
			logger.info(prf + " END");
		}

		return esitoInvia;
	}

	private EsitoInvioPropostaRimodulazioneDTO inviaPropostaRimodulazione(Long idUtente, String idIride, List<ModalitaAgevolazione> listModalita, Long idProgetto, Long idSoggetto, String noteContoEconomico, Long idSoggettoRappresentante, Long idContoEconomico, Double importoFinanziamentoRichiesto, Long idProceduraAggiudicazione, String idDelegato) throws Exception {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progettoDTO.setIdProgetto(idProgetto);

		// Alex: metto valori a caso poichè il metodo di pbandisrv li vuole obbligatori ma non li usa.
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO istanza = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO();
		istanza.setId("TaskIdentity");          //istanza.setId(istanzaAttivita.getTaskIdentity());
		istanza.setCodUtente("CodUtenteFlux");      //istanza.setCodUtente(userInfoHelper.getCodUtenteFlux(currentUser));

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO datiDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO();
		List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO> listModalitaDTO = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO>();
		if (listModalita != null && !listModalita.isEmpty()) {
			for (int i = 0; i < listModalita.size() - 1; ++i) {
				ModalitaAgevolazione modalita = listModalita.get(i);
				it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO dto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO();
				dto.setDescrizione(modalita.getDescModalita());
				dto.setIdModalitaDiAgevolazione(modalita.getIdModalitaAgevolazione());
				dto.setImportoAgevolatoNuovo(modalita.getImportoAgevolato());
				dto.setImportoAgevolatoUltimo(modalita.getUltimoImportoAgevolato());
				dto.setImportoErogato(modalita.getImportoErogato());
				dto.setImportoMassimoAgevolato(modalita.getImportoMassimoAgevolato());
				dto.setImportoRichiestoNuovo(modalita.getImportoRichiesto());
				dto.setImportoRichiestoUltimo(modalita.getUltimoImportoRichiesto());
				dto.setPercImportoAgevolatoNuovo(modalita.getPercentualeImportoAgevolato());
				dto.setPercImportoAgevolatoUltimo(modalita.getPercentualeUltimoImportoAgevolato());
				dto.setPercImportoMassimoAgevolato(modalita.getPercentualeMassimoAgevolato());
				dto.setPercImportoRichiestoNuovo(modalita.getPercentualeImportoRichiesto());
				listModalitaDTO.add(dto);
			}
			datiDTO.setModalitaDiAgevolazione(listModalitaDTO.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[]{}));
		} else {
			datiDTO.setModalitaDiAgevolazione(null);
		}
		datiDTO.setProgetto(progettoDTO);
		datiDTO.setNoteContoEconomico(noteContoEconomico);

		datiDTO.setIdSoggetto(idSoggetto);
		datiDTO.setIdSoggettoRappresentante(idSoggettoRappresentante);
		datiDTO.setIdContoEconomico(idContoEconomico);
		datiDTO.setImportoFinanziamentoRichiesto(importoFinanziamentoRichiesto);
		datiDTO.setIdProceduraAggiudicazione(idProceduraAggiudicazione);

		if (!ObjectUtil.isEmpty(idDelegato)) datiDTO.setIdDelegato(NumberUtil.toNullableLong(idDelegato));

		EsitoInvioPropostaRimodulazioneDTO esito = inviaPropostaRimodulazione(idUtente, idIride, istanza, datiDTO);

		return esito;
	}

	public EsitoInvioPropostaRimodulazioneDTO inviaPropostaRimodulazione(Long idUtente, String identitaDigitale, IstanzaAttivitaDTO istanzaAttivita, DatiPerInvioPropostaRimodulazioneDTO datiPerInvioPropostaRimodulazione) throws CSIException, SystemException, UnrecoverableException {

		String prf = "[ContoEconomicoDAOImpl::inviaPropostaRimodulazione] ";
		logger.info(prf + "BEGIN");
		Long idDocIndexPerRollBack = null;

		try {

			String[] nameParameter = {"idUtente", "identitaDigitale", "istanzaAttivita", "istanzaAttivita.codUtente", "istanzaAttivita.id", "datiPerInvioPropostaRimodulazione", "datiPerInvioPropostaRimodulazione.modalitaDiAgevolazione", "datiPerInvioPropostaRimodulazione.progetto", "datiPerInvioPropostaRimodulazione.progetto.idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, istanzaAttivita, istanzaAttivita.getCodUtente(), istanzaAttivita.getId(), datiPerInvioPropostaRimodulazione, datiPerInvioPropostaRimodulazione.getProgetto(), datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			Long idSoggetto = datiPerInvioPropostaRimodulazione.getIdSoggetto();
			Long idSoggettoRappresentante = datiPerInvioPropostaRimodulazione.getIdSoggettoRappresentante();
			Double finanziamentoBancario = datiPerInvioPropostaRimodulazione.getImportoFinanziamentoRichiesto();
			BigDecimal idProgetto = new BigDecimal(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

			// CHIUSURA PROPOSTA

			if (datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione() != null) {
				aggiornaModalitaPropostaRimodulazione(idUtente, datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione(), idProgetto);
			}

			Long idContoEconomico = contoEconomicoManager.chiudiInvioPropostaRimodulazione(idProgetto, new BigDecimal(idUtente), datiPerInvioPropostaRimodulazione.getNoteContoEconomico(), finanziamentoBancario);

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

			EsitoFindContoEconomicoDTO esitoFindContoEconomico = findContoEconomicoPerPropostaRimodulazione(idUtente, identitaDigitale, progetto);

			List<ContoEconomicoItemDTO> contoEconomicoItemList = contoEconomicoManager.mappaContoEconomicoPerReport(esitoFindContoEconomico.getContoEconomico(), false, true, false, esitoFindContoEconomico.getDataFineIstruttoria(), esitoFindContoEconomico.getInIstruttoria());

			for (ContoEconomicoItemDTO c : contoEconomicoItemList) {
				c.setImportoSpesaAmmessaRimodulazione(c.getImportoSpesaAmmessaUltima());
				logger.info("idVOce=" + c.getIdVoce() + ", delta=" + c.getDelta() + ", label=" + c.getLabel());
			}
			if (datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione() != null) {
				datiPerInvioPropostaRimodulazione.setTotaliModalitaDiAgevolazione(calcolaTotaliModalitaAgevolazione(datiPerInvioPropostaRimodulazione.getModalitaDiAgevolazione()));
			}
			List<ModalitaAgevolazione> listModalita = contoEconomicoManager.mappaModalitaDiAgevolazionePerReport(datiPerInvioPropostaRimodulazione);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_MODALITA_AGEVOLAZIONE, listModalita);

			Date currentDate = new Date(System.currentTimeMillis());
			ProgettoBandoLineaVO progettoBandoLinea = getBandoLinea(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());
			PropostaRimodulazioneReportDTO propostaRimodulazioneDTO = creaPropostaRimodulazioneReportDTO(progettoBandoLinea, idContoEconomico, esitoFindContoEconomico, datiPerInvioPropostaRimodulazione, idSoggetto, idSoggettoRappresentante, currentDate);

			// CREAZIONE REPORT

			byte bytesPdf[] = null;

			// new report

			// Progetto

			PbandiTProgettoVO progettoVO = progettoManager.getProgetto(idProgetto.longValue());
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGETTO, beanUtil.transform(progettoVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));

			popolaTemplateManager.setTipoModello(PopolaTemplateManager.MODELLO_PROPOSTA_DI_RIMODULAZIONE);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgetto.longValue());

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROPOSTA_RIMODULAZIONE_DTO, propostaRimodulazioneDTO);

			ContoEconomicoConStatoVO contoEconomicoLocalCopyBen = contoEconomicoManager.findContoEconomicoLocalCopyBen(idProgetto);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_CONTOECONOMICO_PROPOSTA_RIMOD, contoEconomicoLocalCopyBen);

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_CONTOECONOMICO_COPY_BEN, contoEconomicoItemList);// PK

			BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
			filtroBeneficiario.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			filtroBeneficiario.setIdSoggetto(beanUtil.transform(idSoggetto, BigDecimal.class));

			BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_BENEFICIARIO, beanUtil.transform(beneficiarioVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));

			String notecontoEconomico = datiPerInvioPropostaRimodulazione.getNoteContoEconomico();
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NOTE_CONTO_ECONOMICO, notecontoEconomico);

			// Rappresentante legale

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = rappresentanteLegaleManager.findRappresentanteLegale(idProgetto.longValue(), idSoggettoRappresentante); // }L{ PBANDI-2029
			// mancava
			// idSoggettoRappresentante

			if (datiPerInvioPropostaRimodulazione.getIdDelegato() != null) {
				logger.info("il delegato non � NULL " + datiPerInvioPropostaRimodulazione.getIdDelegato() + ", lo metto al posto del rapp legale");
				DelegatoVO delegatoVO = new DelegatoVO();
				delegatoVO.setIdSoggetto(datiPerInvioPropostaRimodulazione.getIdDelegato());
				delegatoVO.setIdProgetto(idProgetto.longValue());
				List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
				if (delegati != null && !delegati.isEmpty()) {
					delegatoVO = delegati.get(0);
				}
				rappresentanteLegaleDTO = beanUtil.transform(delegatoVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
			}

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegaleDTO);

			SedeVO sedeIntervento = null;
			try {
				sedeIntervento = sedeManager.findSedeIntervento(idProgetto.longValue(), idSoggetto.longValue());
			} catch (Exception e) {
				DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException("Errore durante la ricerca della sede di intervento");
				throw dse;
			}

			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO, beanUtil.transform(sedeIntervento, SedeDTO.class));

			ProgettoDTO progettoDTO = new ProgettoDTO();
			progettoDTO.setIdProgetto(idProgetto.longValue());
			List<ProceduraAggiudicazioneDTO> proceduraVO = getProcedureAggiudicazione(progettoDTO.getIdProgetto());
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROCEDURA_AGGIUDICAZIONE, proceduraVO);

			// PK : valorizzo le voci degli allegati da far vedere in stampa

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
					// ti.setIdDichiarazioneSpesa(amssr.getI);
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
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC, "" + propostaRimodulazioneDTO.getIdContoEconomico());

			VociDiEntrataCulturaDTO vociDiEntrata = vociDiEntrataCultura(idProgetto.longValue(), idUtente, identitaDigitale);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_VOCI_DI_ENTRATA, vociDiEntrata.getVociDiEntrataCultura());

			// SERVE JASPERREPORT 4 1 1 .JAR
			long startFillReport = System.currentTimeMillis();
			logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in " + (System.currentTimeMillis() - startFillReport) + " ms\n");

			long startExport = System.currentTimeMillis();
			logger.info("\n\n\n########################\nJasperPrint esportato to pdf in " + (System.currentTimeMillis() - startExport) + " ms\n");

			if (bytesPdf == null) {
				logger.warn("\n\n\n\nERRORE GRAVE : report PROPOSTA RIMODULAZIONE non creato ,bytes==null");
			}

			// CREAZIONE NODO INDEX

			propostaRimodulazioneDTO.setBytes(bytesPdf);

			Node nodoIndex = new Node();

			// ID CONTO ECONOMICO DEV'ESSERE QUELLO DELLA LOCAL COPY

			String shaHex = null;
			if (bytesPdf != null) shaHex = DigestUtils.shaHex(bytesPdf);

			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO;
			pbandiTDocumentoIndexVO = documentoManager.salvaInfoNodoIndexSuDbSenzaInsert(idUtente, nodoIndex, propostaRimodulazioneDTO.getNomeFile(), idContoEconomico, idSoggettoRappresentante, datiPerInvioPropostaRimodulazione.getIdDelegato(), datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto(), GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE, PbandiTContoEconomicoVO.class, null, shaHex);

			// **********************************************************************************
			// AGGIUNTA PER SALVATAGGIO SU FILESYSTEM.
//			pbandiTDocumentoIndexVO
//					.setRepository(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE);
//			this.salvaFileSuFileSystem(bytesPdf, pbandiTDocumentoIndexVO);
			// **********************************************************************************

			if (pbandiTDocumentoIndexVO.getIdDocumentoIndex() != null)
				idDocIndexPerRollBack = pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue();

			boolean isBrDemat = regolaManager.isRegolaApplicabileForBandoLinea(progettoBandoLinea.getIdBandoLinea(), RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
			if (isBrDemat) associateAllegati(BigDecimal.valueOf(idContoEconomico), idProgetto);

			/**
			 * Non è necessario fare l'unlock poiché sui bandi Cultura la rimodulazione non è un'attività disponibile per l'operatore,
			 * viene eseguita solo in automatico assieme all'invio dichiarazione
			 */
//			logger.warn("\n\n############################ NEOFLUX UNLOCK PROP_RIM_CE ##############################\n");
//			neofluxBusinessImpl.unlockAttivita(idUtente, identitaDigitale, idProgetto.longValue(), Task.PROP_RIM_CE);
//			logger.warn(
//					"############################ NEOFLUX UNLOCK PROP_RIM_CE ##############################\n\n\n\n");

			List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica = Notification.NOTIFICA_PROPOSTA_RIMODULAZIONE;
			MetaDataVO metadata1 = new MetaDataVO();
			metadata1.setNome(Notification.DATA_INVIO_PROPOSTA);
			metadata1.setValore(DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);

			logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);

			logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
			genericDAO.callProcedure().sendNotificationMessage(idProgetto, descrBreveTemplateNotifica, Notification.ISTRUTTORE, idUtente);
			logger.info("calling genericDAO.callProcedure().sendNotificationMessage ok");

			EsitoInvioPropostaRimodulazioneDTO e = new EsitoInvioPropostaRimodulazioneDTO();

			// e.setIdDocIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			e.setFileName(propostaRimodulazioneDTO.getNomeFile());

			e.setIdContoEconomicoLocal(idContoEconomico);
			e.setSuccesso(true);

			logger.info("FINE");
			return e;

		} catch (Exception e) {
			logger.info("ROLLBACK APPLICATIVO: cancello nodo appena creato su index perche' c'e' stato un errore successivo");
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

	private void aggiornaModalitaPropostaRimodulazione(Long idUtente, ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione, BigDecimal idProgetto) throws Exception, ContoEconomicoNonTrovatoException {
		aggiornaModalita(idUtente, modalitaDiAgevolazione, idProgetto);
	}

	private void aggiornaModalita(Long idUtente, ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione, BigDecimal idProgetto) throws Exception, ContoEconomicoNonTrovatoException {

		Map<?, ModalitaDiAgevolazioneContoEconomicoVO> indexModalitaPresenti = beanUtil.index(contoEconomicoManager.caricaModalitaAgevolazione(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER), "idModalitaAgevolazione");

		BigDecimal idContoEconomico = contoEconomicoManager.getIdContoMaster(idProgetto);

		Map<String, String> map = new HashMap<String, String>();
		map.put("idContoEconomico", "idContoEconomico");
		map.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
		map.put("importoRichiestoNuovo", "quotaImportoRichiesto");
		map.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

		for (ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO : modalitaDiAgevolazione) {
			ModalitaDiAgevolazioneContoEconomicoVO modalitaPrecedente = indexModalitaPresenti.get(new BigDecimal(modalitaDiAgevolazioneDTO.getIdModalitaDiAgevolazione()));
			if (modalitaPrecedente.getFlagLvlprj().equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {

				// insert del valore gi� esistente della modalit� associata al master

				PbandiRContoEconomModAgevVO modAgevVO = beanUtil.transform(modalitaPrecedente, PbandiRContoEconomModAgevVO.class);
				beanUtil.copyNotNullValues(modalitaDiAgevolazioneDTO, modAgevVO, map);
				modAgevVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(modAgevVO);

			} else if (NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoAgevolatoNuovo(), 0d) > 0 || NumberUtil.compare(modalitaDiAgevolazioneDTO.getImportoRichiestoNuovo(), 0d) > 0) {
				PbandiRContoEconomModAgevVO nuovaModalita = new PbandiRContoEconomModAgevVO();
				nuovaModalita.setIdContoEconomico(idContoEconomico);

				Map<String, String> mapDTOtoVO = new HashMap<String, String>();
				mapDTOtoVO.put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
				mapDTOtoVO.put("importoRichiestoNuovo", "quotaImportoRichiesto");
				mapDTOtoVO.put("importoAgevolatoNuovo", "quotaImportoAgevolato");

				beanUtil.valueCopy(modalitaDiAgevolazioneDTO, nuovaModalita, mapDTOtoVO);

				nuovaModalita.setIdUtenteIns(new BigDecimal(idUtente));
				nuovaModalita.setPercImportoAgevolato(new BigDecimal(0));

				genericDAO.insert(nuovaModalita);
			}
		}
	}

	private EsitoDTO eseguiSalvaTipoAllegati(ArrayList<TipoAllegatoDTO> listaTipoAllegati, String codiceTipoDichiarazioneDiSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::eseguiSalvaTipoAllegati] ";
		LOG.info(prf + "BEGIN");

		EsitoDTO esito = new EsitoDTO();
		try {

			if (listaTipoAllegati == null || listaTipoAllegati.size() == 0) {
				LOG.info(prf + "listaTipoAllegati vuota: non faccio nulla.");
				esito.setEsito(true);
				return esito;
			}

			// Da dto di pbweb a dto di pbandisrv.
			TipoAllegatoDTO[] listaTipoAllegatiSrv;
			listaTipoAllegatiSrv = beanUtil.transform(listaTipoAllegati, TipoAllegatoDTO.class);

			esito = salvaTipoAllegati(idUtente, idIride, listaTipoAllegatiSrv);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dei tipo allegati: ", e);
			throw new DaoException(" ERRORE nella ricerca dei tipo allegati.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	public EsitoDTO salvaTipoAllegati(Long idUtente, String identitaIride, TipoAllegatoDTO[] tipoAllegatiDTO) throws CSIException, SystemException, UnrecoverableException, GestioneDatiGeneraliException {
		String[] nameParameter = {"idUtente", "identitaIride"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride);
		ValidatorInput.verifyArrayNotEmpty("tipoAllegatiDTO", tipoAllegatiDTO);
		EsitoDTO esito = new EsitoDTO();
		// Memorizzare i tipo allegati nella tabella PBANDI_R_DICH_SPESA_DOC_ALLEG
		Long idDichiarazioneSpesa = tipoAllegatiDTO[0].getIdDichiarazioneSpesa();
		Long idProgetto = tipoAllegatiDTO[0].getIdProgetto();
		logger.info("salvaTipoAllegati(): idDichiarazioneSpesa = " + idDichiarazioneSpesa + "; idProgetto = " + idProgetto);
		for (TipoAllegatoDTO dto : tipoAllegatiDTO)
			logger.info("salvaTipoAllegati(): tipoAllegato: " + dto.getFlagAllegato() + " - " + dto.getDescTipoAllegato());

		if ((idDichiarazioneSpesa == null || idDichiarazioneSpesa == 0) && idProgetto != null) {
			List<PbandiWProgettoDocAllegVO> progettoDocAlleg = beanUtil.transformToArrayList(tipoAllegatiDTO, PbandiWProgettoDocAllegVO.class);
			if (progettoDocAlleg != null && progettoDocAlleg.size() > 0) {
				try {
					List<TipoAllegatoProgettoVO> arrayFiltroVO = beanUtil.transformToArrayList(tipoAllegatiDTO, TipoAllegatoProgettoVO.class);
					List<TipoAllegatoProgettoVO> tipoAllegatiList = genericDAO.findListWhere(Condition.filterBy(arrayFiltroVO));
					if (tipoAllegatiList == null || tipoAllegatiList.isEmpty()) {
						genericDAO.insert(progettoDocAlleg);
					} else {
						genericDAO.insertOrUpdateExisting(progettoDocAlleg);
					}
					esito.setEsito(Boolean.TRUE);
					esito.setMessaggio("Salvataggio concluso con successo");
				} catch (Exception ex) {
					esito.setEsito(Boolean.FALSE);
					esito.setMessaggio("Errore durante il salvataggio");
				}
			}
		} else if (idDichiarazioneSpesa != null) {
			List<PbandiRDichSpesaDocAllegVO> dichSpesaDocAlleg = beanUtil.transformToArrayList(tipoAllegatiDTO, PbandiRDichSpesaDocAllegVO.class);
			if (dichSpesaDocAlleg != null && dichSpesaDocAlleg.size() > 0) {
				try {
					TipoAllegatoDichiarazioneVO filtroVO = new TipoAllegatoDichiarazioneVO();
					filtroVO.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazioneSpesa));
					List<TipoAllegatoDichiarazioneVO> tipoAllegatiList = genericDAO.findListWhere(filtroVO);
					if (tipoAllegatiList == null || tipoAllegatiList.isEmpty()) {
						genericDAO.insert(dichSpesaDocAlleg);
					} else {
						genericDAO.insertOrUpdateExisting(dichSpesaDocAlleg);
					}
					esito.setEsito(Boolean.TRUE);
					esito.setMessaggio("Salvataggio concluso con successo");
				} catch (Exception ex) {
					esito.setEsito(Boolean.FALSE);
					esito.setMessaggio("Errore durante il salvataggio");
				}
			} else {
				esito.setEsito(Boolean.FALSE);
				esito.setMessaggio("Nessun elemento da salvare");
			}
		}
		return esito;
	}

	public RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String identitaDigitale, Long idProgetto, Long idSoggettoRappresentante) throws CSIException, SystemException, UnrecoverableException, DichiarazioneDiSpesaException {

		List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentanti = soggettoManager.findRappresentantiLegali(idProgetto, idSoggettoRappresentante);
		return beanUtil.transform(rappresentanti, RappresentanteLegaleDTO.class);

	}

	public Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::getIsBeneficiarioPrivatoCittadino] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		// BeneficiarioVO beneficiario = new BeneficiarioVO();
		Boolean isPersinaFisica = null;

		try {

			long idTipoAnagrafica = 1L;
			long idTipoBeneficiario = 4L;

			String sql = "select count(id_soggetto) as value					\r\n" + "from pbandi_r_soggetto_progetto 						\r\n" + "where id_progetto = ?								\r\n" + "and id_tipo_anagrafica = ?							\r\n" + "and id_tipo_beneficiario != ?						\r\n" + "and id_persona_fisica is not null 	 					";

			Object[] par = {idProgetto, idTipoAnagrafica, idTipoBeneficiario};

			logger.info(prf + "\n" + sql);
			Integer num = (getJdbcTemplate().queryForObject(sql, par, Integer.class));

			if (num == null) isPersinaFisica = false;
			else isPersinaFisica = num > 0;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dei dati beneficirio: ", e);
			throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
		} finally {
			LOG.info(prf + " END");
		}

		return isPersinaFisica;
	}

	private RappresentanteLegaleDTO getPrivatoCittadinoForRappresentanteLegale(Long idSoggetto) throws DaoException {

		String prf = "[ContoEconomicoDAOImpl::getPrivatoCittadinoForRappresentanteLegale] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idSoggetto = " + idSoggetto + " ; ");

		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato");
		}

		RappresentanteLegaleDTO rappresentanteLegaleDTO = null;

		try {

			String sql = "select 														\r\n" + "ptpf.id_soggetto,											\r\n" + "ptpf.id_persona_fisica,										\r\n" + "ptpf.cognome,												\r\n" + "ptpf.nome,													\r\n" + "ptpf.dt_nascita as data_nascita,								\r\n" + "ptpf.id_comune_italiano_nascita,								\r\n" + "ptpf.id_comune_estero_nascita,								\r\n" + "pts.codice_fiscale_soggetto,									\r\n" + "case						 									\r\n" + "	when ptpf.id_comune_italiano_nascita is null				\r\n" + "		then pdce.desc_comune_estero							\r\n" + "	else pdc.desc_comune										\r\n" + "	end as luogo_nascita										\r\n" + "from pbandi_t_persona_fisica ptpf							\r\n" + "join pbandi_t_soggetto pts									\r\n" + "	on ptpf.id_soggetto = pts.id_soggetto						\r\n" + "left join pbandi_d_comune pdc								\r\n" + "	on ptpf.id_comune_italiano_nascita = pdc.id_comune			\r\n" + "left join pbandi_d_comune_estero pdce						\r\n" + "	on ptpf.id_comune_estero_nascita = pdce.id_comune_estero	\r\n" + "where ptpf.id_soggetto = ? 									\r\n" + "and ROWNUM <=1													";

			Object[] par = {idSoggetto.toString()};

			logger.info(prf + "\n" + sql);
			rappresentanteLegaleDTO = (RappresentanteLegaleDTO) getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(RappresentanteLegaleDTO.class));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca del rappresentante legale nel caso del privato cittadino: ", e);
			throw new DaoException(" ERRORE nella ricerca del rappresentante legale nel caso del privato cittadino: ");
		} finally {
			LOG.info(prf + " END");
		}

		return rappresentanteLegaleDTO;

	}

	private IndirizzoPrivatoCittadino getIndirizzoPrivatoCittadino(Long idSoggetto, Long idProgetto) throws DaoException {

		String prf = "[ContoEconomicoDAOImpl::getIndirizzoPrivatoCittadino] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idSoggetto = " + idSoggetto + ", " + "idProgetto = " + idProgetto + " ; ");

		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}

		IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = null;

		try {

			String sql = "select 														\r\n" + "pti.desc_indirizzo, 											\r\n" + "pti.cap,														\r\n" + "pti.id_provincia,											\r\n" + "pti.id_comune												\r\n" + "from pbandi_r_soggetto_progetto prsp							\r\n" + "join pbandi_t_indirizzo pti									\r\n" + "	on prsp.id_indirizzo_persona_fisica = pti.id_indirizzo		\r\n" + "where prsp.id_progetto = ?									\r\n" + "and prsp.id_soggetto = ?											";

			Object[] par = {idProgetto.toString(), idSoggetto.toString()};

			logger.info(prf + "\n" + sql);
			indirizzoPrivatoCittadino = (IndirizzoPrivatoCittadino) getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(IndirizzoPrivatoCittadino.class));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dell' indirizzo privato cittadino: ", e);
			throw new DaoException(" ERRORE nella ricerca dell' indirizzo privato cittadino: ");
		} finally {
			LOG.info(prf + " END");
		}

		return indirizzoPrivatoCittadino;

	}

	private Boolean aggiornaProgettoIter(Long idProgetto, Long idDichiarazioneDiSpesa) throws Exception {

		String prf = "[ContoEconomicoDAOImpl::aggiornaProgettoIter] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto);

		if (idProgetto == 0 || idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}

		if (idDichiarazioneDiSpesa == 0 || idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}

		try {
			String sql;
			sql = "UPDATE PBANDI_R_PROGETTO_ITER SET ID_DICHIARAZIONE_SPESA = ? \r\n" + "WHERE id_progetto = ? AND FLAG_FASE_CHIUSA = 1 AND ID_DICHIARAZIONE_SPESA  IS NULL";

			Object[] args = new Object[]{idDichiarazioneDiSpesa, idProgetto};
			logger.info("<idProgetto>: " + idProgetto + ", <idDichiarazioneDiSpesa>: " + idDichiarazioneDiSpesa);
			logger.info(prf + "\n" + sql + "\n");

			int rows = getJdbcTemplate().update(sql, args);
			if (rows > 0) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in aggiornaProgettoIter: ", e);
			throw new Exception(" ERRORE in aggiornaProgettoIter.");
		} finally {
			LOG.info(prf + " END");
		}

		return Boolean.FALSE;
	}

	public EsitoFindContoEconomicoDTO findContoEconomicoPerPropostaRimodulazione(Long idUtente, String identitaDigitale, ProgettoDTO progetto) throws CSIException, SystemException, UnrecoverableException, ContoEconomicoException {

		String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

		ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO = null;

		try {
			// la proposta di rimodulazione prevede che si lavori su 3 possibili tipologie
			// di conto economico
			// - MAIN - MASTER - COPY_BEN
			List<String> listaTipi = new ArrayList<String>();
			Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti = new HashMap<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO>();
			String tipologiaContoEconomicoCopy = Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN;
			Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

			caricaContiEconomici(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);

			contoEconomicoRimodulazioneDTO = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMaster);

			EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoDTO();
			esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoRimodulazioneDTO);
			esitoFindContoEconomicoDTO.setLocked(isLockedByIstruttore(contoMaster));
			esitoFindContoEconomicoDTO.setCopiaModificataPresente(mappaConti.get(tipologiaContoEconomicoCopy) != null);
			esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(progetto.getIdProgetto(), RegoleConstants.BR27_RIMODULAZIONE_DISPONIBILE));

			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO) mappaConti.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			if (contoMain != null) {
				if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
					esitoFindContoEconomicoDTO.setInIstruttoria(true);
				else esitoFindContoEconomicoDTO.setInIstruttoria(false);
				if (Constants.STATO_CONTO_ECONOMICO_RICHIESTO.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
					esitoFindContoEconomicoDTO.setInStatoRichiesto(true);
				else esitoFindContoEconomicoDTO.setInStatoRichiesto(false);

			}

			ContoEconomicoMaxDataFineVO datiUltimaProposta = getDatiUltimaProposta(progetto);
			Date dataUltimaProposta = null;

			if (datiUltimaProposta != null) {
				dataUltimaProposta = datiUltimaProposta.getDtFineValidita();
				// VN: Ribasso d' asta. Resituisco il flag dell' ultimo ribasso d' asta in
				// proposta
				if (esitoFindContoEconomicoDTO != null) {
					if (datiUltimaProposta.getPercRibassoAsta() != null) {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
						esitoFindContoEconomicoDTO.setImportoUltimoRibassoAstaInProposta(NumberUtil.toDouble(datiUltimaProposta.getImportoRibassoAsta()));
						esitoFindContoEconomicoDTO.setPercUltimoRibassoAstaInProposta(NumberUtil.toDouble(datiUltimaProposta.getPercRibassoAsta()));
					} else {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
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
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
					} else {
						esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
					}

				}
			}

			esitoFindContoEconomicoDTO.setDataUltimaProposta(dataUltimaProposta);

			esitoFindContoEconomicoDTO.setDataUltimaRimodulazione(dataUltimaRimodulazione);

			esitoFindContoEconomicoDTO.setDataFineIstruttoria(mappaConti.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN).getDtFineValidita());

			esitoFindContoEconomicoDTO.setDataPresentazioneDomanda(mapDataPresentazioneDomanda.get(Constants.DATA_PRESENTAZIONE_DOMANDA));

			return esitoFindContoEconomicoDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	private ModalitaDiAgevolazioneDTO calcolaTotaliModalitaAgevolazione(ModalitaDiAgevolazioneDTO[] modalitaAgevolazione) {
		String prf = "[ContoEconomicoDAOImpl::calcolaTotaliModalitaAgevolazione] ";
		LOG.info(prf + "BEGIN");
		ModalitaDiAgevolazioneDTO totale = NumberUtil.accumulate(modalitaAgevolazione, "importoAgevolatoNuovo", "importoAgevolatoUltimo", "importoErogato", "importoMassimoAgevolato", "importoRichiestoNuovo", "importoRichiestoUltimo", "percImportoAgevolatoNuovo", "percImportoAgevolatoUltimo", "percImportoRichiestoUltimo", "percImportoRichiestoNuovo", "percImportoMassimoAgevolato");
		totale.setDescrizione("Totale");
		totale.setIdModalitaDiAgevolazione(null);
		LOG.info(prf + "END");
		return totale;
	}

	private ProgettoBandoLineaVO getBandoLinea(Long idProgetto) {
		ProgettoBandoLineaVO progettoBandoLineaVO = new ProgettoBandoLineaVO();
		progettoBandoLineaVO.setIdProgetto(new BigDecimal(idProgetto));
		ProgettoBandoLineaVO progettoBandoLinea = genericDAO.findSingleWhere(progettoBandoLineaVO);
		return progettoBandoLinea;
	}

	private PropostaRimodulazioneReportDTO creaPropostaRimodulazioneReportDTO(ProgettoBandoLineaVO progettoBandoLinea, Long idContoEconomico, EsitoFindContoEconomicoDTO esitoFindContoEconomico, DatiPerInvioPropostaRimodulazioneDTO datiPerInvioPropostaRimodulazione, Long idSoggetto, Long idSoggettoRappresentante, Date currentDate) {

		PropostaRimodulazioneReportDTO propostaRimodulazioneDTO = new PropostaRimodulazioneReportDTO();

		EnteAppartenenzaDTO enteAppartenenza = findEnteAppartenenza(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto(), Constants.CODICE_RUOLO_ENTE_DESTINATARIO);

		RappresentanteLegale rappresentanteLegale = getRappresentanteLegale(datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto(), idSoggettoRappresentante);

		BeneficiarioVO beneficiarioVO = pbandiDichiarazioneDiSpesaDAO.findBeneficiario(idSoggetto, datiPerInvioPropostaRimodulazione.getProgetto().getIdProgetto());

		String nomeFile = creaNomeFileProposta(idContoEconomico, currentDate);

		String note = datiPerInvioPropostaRimodulazione.getNoteContoEconomico();
		if (ObjectUtil.isEmpty(note)) note = "Nessuna";

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
			proceduraVO.setIdProceduraAggiudicaz(NumberUtil.toBigDecimal(datiPerInvioPropostaRimodulazione.getIdProceduraAggiudicazione()));
			proceduraVO = genericDAO.findSingleWhere(proceduraVO);

			ProceduraAggiudicazioneDTO proceduraDTO = beanUtil.transform(proceduraVO, ProceduraAggiudicazioneDTO.class);

			propostaRimodulazioneDTO.setProceduraAggiudicazione(proceduraDTO);
		}

		return propostaRimodulazioneDTO;
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

	private void associateAllegati(BigDecimal idContoEconomico, BigDecimal idProgetto) {

		logger.info("\n\n\nassociating allegati to idContoEconomico " + idContoEconomico + " ,idProgetto ");
		pbandiArchivioFileDAOImpl.associateAllegatiToContoEconomico(idContoEconomico, idProgetto);

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

	private void caricaContiEconomici(BigDecimal idProgetto, List<String> listaTipi, Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> mappaConti, String tipologiaContoEconomicoCopy, Map<String, Date> mapDataPresentazioneDomanda) throws Exception {

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomico;
		Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomiciPerRimodulazione = contoEconomicoManager.findContiEconomici(idProgetto, tipologiaContoEconomicoCopy);
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = contiEconomiciPerRimodulazione.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);

		if (contoMain != null) {
			mapDataPresentazioneDomanda.put(Constants.DATA_PRESENTAZIONE_DOMANDA, contoMain.getDataPresentazioneDomanda());
			listaTipi.add(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
			mappaConti.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN, contoMain);
		}

		contoEconomico = contiEconomiciPerRimodulazione.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
		if (contoEconomico != null) {
			listaTipi.add(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			mappaConti.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER, contoEconomico);
		}
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoCopy = contiEconomiciPerRimodulazione.get(tipologiaContoEconomicoCopy);
		if (contoCopy != null) {
			listaTipi.add(tipologiaContoEconomicoCopy);
			mappaConti.put(tipologiaContoEconomicoCopy, contoCopy);
		}
	}

	private ContoEconomicoRimodulazioneDTO convertiContoEconomicoPerRimodulazione(List<String> tipiDiConti, Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {

		Map<String, RigoContoEconomicoDTO> righiContiEconomici = new HashMap<String, RigoContoEconomicoDTO>(contiEconomici.size());
		for (String key : contiEconomici.keySet()) {
			righiContiEconomici.put(key, contiEconomici.get(key));
		}
		ContoEconomicoRimodulazioneDTO contoEconomicoConvertito = convertiFigliContoEconomicoPerRimodulazione(tipiDiConti, righiContiEconomici, rigoConto);

		for (String tipoDiConto : tipiDiConti) {
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoCaricato = contiEconomici.get(tipoDiConto);
			contoEconomicoConvertito.setFlagRibassoAsta(contoEconomicoCaricato.getFlagRibassoAsta());
		}

		return contoEconomicoConvertito;
	}

	private boolean isLockedByIstruttore(it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster) {
		return contoMaster != null && contoMaster.getDescBreveStatoContoEconom().equals(Constants.STATO_CONTO_ECONOMICO_IN_RIMODULAZIONE);
	}

	private ContoEconomicoMaxDataFineVO getDatiUltimaProposta(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA);
	}

	private ContoEconomicoMaxDataFineVO getDatiUltimaRimodulazione(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA);
	}

	private ContoEconomicoMaxDataFineVO getDatiContoEconomicoByStato(ProgettoDTO progetto, String stato) {
		ContoEconomicoMaxDataFineVO contoEconomicoMaxDataFineVO = new ContoEconomicoMaxDataFineVO();
		contoEconomicoMaxDataFineVO.setIdProgetto(NumberUtil.createScaledBigDecimal(progetto.getIdProgetto()));
		contoEconomicoMaxDataFineVO.setDescBreveStatoContoEconom(stato);
		ContoEconomicoMaxDataFineVO[] voz = genericDAO.findWhere(contoEconomicoMaxDataFineVO);
		if (!ObjectUtil.isEmpty(voz)) return voz[0];
		else return null;
	}

	private EnteAppartenenzaDTO findEnteAppartenenza(Long idProgetto, String codiceTipoRuoloEnte) {
		EnteAppartenenzaVO enteVO = pbandiDichiarazioneDiSpesaDAO.findEnteAppartenenza(idProgetto, codiceTipoRuoloEnte);
		return beanUtil.transform(enteVO, EnteAppartenenzaDTO.class);
	}

	private RappresentanteLegale getRappresentanteLegale(Long idProgetto, Long idSoggettoRappresentante) {
		List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappLegali = soggettoManager.findRappresentantiLegali(idProgetto, idSoggettoRappresentante);
		return beanUtil.transform(rappLegali.get(0), RappresentanteLegale.class);
	}

	private String creaNomeFileProposta(Long idContoEconomico, Date currentDate) {
		String nomeFile;
		nomeFile = "PropostaDiRimodulazione_" + idContoEconomico + "_" + DateUtil.getTime(currentDate, Constants.TIME_FORMAT_PER_NOME_FILE) + ".pdf";
		logger.info("nomeFile del file della proposta : " + nomeFile);
		return nomeFile;
	}

	private ContoEconomicoRimodulazioneDTO convertiFigliContoEconomicoPerRimodulazione(List<String> tipiDiConti, Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
		ContoEconomicoRimodulazioneDTO contoEconomicoDTO = mappaContoRimodulazione(tipiDiConti, contiEconomici, rigoConto);

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

				ContoEconomicoRimodulazioneDTO figlioTrasformato = convertiFigliContoEconomicoPerRimodulazione(tipiDiConti, mappaFigliCorrenti, rigoContoFiglio);

				listaTrasformati.add(figlioTrasformato);
			}
			figliTrasformati = listaTrasformati.toArray(new ContoEconomicoRimodulazioneDTO[listaTrasformati.size()]);
		}
		contoEconomicoDTO.setFigli(figliTrasformati);

		return contoEconomicoDTO;
	}

	private ContoEconomicoRimodulazioneDTO mappaContoRimodulazione(List<String> tipiDiConti, Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
		ContoEconomicoRimodulazioneDTO contoEconomicoDTO = new ContoEconomicoRimodulazioneDTO();

		for (String tipoDiConto : tipiDiConti) {
			RigoContoEconomicoDTO rigo = contiEconomici.get(tipoDiConto);
			beanUtil.copyNotNullValues(rigo, contoEconomicoDTO, mappaturaPerTipoContoEconomico.get(tipoDiConto));
			beanUtil.copyNotNullValues(rigo, contoEconomicoDTO, mapCommon);
		}

		if (rigoConto != null) {
			contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigoConto.getImportoQuietanzato(), rigoConto.getImportoAmmesso())));
			contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigoConto.getImportoValidato(), rigoConto.getImportoAmmesso())));
		}
		return contoEconomicoDTO;
	}

	@Override
	public VociDiEntrataCulturaDTO vociDiEntrataPerRimodulazione(Long idProgetto, Long idUtente, String idIride) throws Exception {
		LOG.info("[ContoEconomicoDAOImpl::vociDiEntrataPerRimodulazione] BEGIN");
		LOG.debug("[ContoEconomicoDAOImpl::vociDiEntrataPerRimodulazione] idProgetto = " + idProgetto + ", idUtente = " + idUtente + ", idIride = " + idIride);
    VociDiEntrataCulturaDTO result = findRigheVociDiEntrataByIDProgetto(String.valueOf(idProgetto));
		LOG.info("[ContoEconomicoDAOImpl::vociDiEntrataPerRimodulazione] END");
		return result;
	}

}
