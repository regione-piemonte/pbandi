/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweb.business.manager.FatturaElettronicaManager;
import it.csi.pbandi.pbweb.dto.*;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.*;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.ParametriCompensiDTORowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.TipoDocumentiSpesaRowMapper;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaPagamentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.integration.vo.AffidamentoVO;
import it.csi.pbandi.pbweb.integration.vo.FornitoreAffidamentoVO;
import it.csi.pbandi.pbweb.integration.vo.TipoDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoOperazioneDocumentoDiSpesa;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.VoceDiSpesaPadreDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentidispesa.GestioneDocumentiDiSpesaException;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

@Service
public class DocumentoDiSpesaDAOImpl extends JdbcDaoSupport implements DocumentoDiSpesaDAO {

	@Autowired
	protected FileSqlUtil fileSqlUtil;
	@Autowired
	protected BeanUtil beanUtil;
	@Autowired
	protected InizializzazioneDAOImpl inizializzazioneDAOImpl;
	@Autowired
	protected DecodificheDAOImpl decodificheDAOImpl;
	@Autowired
	// protected ArchivioFileDAOImpl archivioFileDAOImpl;
	protected it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl archivioFileDAOImpl;
	@Autowired
	protected ProfilazioneDAO profilazioneDao;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa.GestioneDocumentiDiSpesaBusinessImpl gestioneDocumentiDiSpesaBusinessImp;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.gestionevocidispesa.GestioneVociDiSpesaBusinessImpl gestioneVociDiSpesaBusinessImpl;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.gestionepagamenti.GestionePagamentiBusinessImpl gestionePagamentiBusinessImpl;
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager progettoManager;
	// Selettori di ricerca documenti di spesa
	String SELETTORE_RICERCA_DOCUMENTI_GESTITI_NEL_PROGETTO = "GESTITI";
	String SELETTORE_RICERCA_DOCUMENTI_TUTTI = "TUTTI";
	String SELETTORE_RICERCA_RENDICONTAZIONE_CAPOFILA = "CAPOFILA";
	String SELETTORE_RICERCA_RENDICONTAZIONE_TUTTI_I_PARTNERS = "PARTNERS";
	String SEPARATORE_SOGGETTO_PROGETTO_PARTNER = ";";
	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;
	@Autowired
	FatturaElettronicaManager fatturaElettronicaManager;
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public DocumentoDiSpesaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<TipoDocumentiSpesaVO> ottieniTipologieDocumentiDiSpesaByBandoLinea(int idBandoLinea) {
		String prf = "[DocumentoDiSpesaDAOImpl::ottieniTipologieDocumentiDiSpesaByBandoLinea] ";

		LOG.info(prf + "ottieniTipologieDocumentiDiSpesaByBandoLinea: idBandoLinea = " + idBandoLinea);
		List<TipoDocumentiSpesaVO> listTipoDocumentiSpesa = new ArrayList<TipoDocumentiSpesaVO>();
		String sql;
		try {
			sql = fileSqlUtil.getQuery("OttieniTipologieDocumentiDiSpesa.sql");
			listTipoDocumentiSpesa = getJdbcTemplate().query(sql, new Object[] { idBandoLinea },
					new TipoDocumentiSpesaRowMapper());
			LOG.info(prf + "ottieniTipologieDocumentiDiSpesaByBandoLinea: listTipoDocumentiSpesa.size = "
					+ listTipoDocumentiSpesa.size());
		} catch (Exception e) {
			System.out.println("Eccezione EXc" + e.getMessage());
		}

		return listTipoDocumentiSpesa;
	}

	@Override
	public List<AffidamentoRendicontazioneDTO> elencoAffidamenti(long idProgetto, long idFornitoreDocSpesa,
			String codiceRuolo) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::elencoAffidamenti] ";
		LOG.info(prf + "BEGIN");
		List<AffidamentoRendicontazioneDTO> elencoDTO = new ArrayList<AffidamentoRendicontazioneDTO>();
		try {
			LOG.info(prf + "idProgetto = " + idProgetto + "idFornitoreDocSpesa = " + idFornitoreDocSpesa
					+ "; codiceRuolo = " + codiceRuolo);

			if (idProgetto == 0) {
				throw new InvalidParameterException("idProgetto non valorizzato");
			}

			// Legge gli affidamenti del progetto.
			String sql = fileSqlUtil.getQuery("AffidamentoVO.sql");
			Long idTipoDocIndex = Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO;
			LOG.info(prf + "\n" + sql + "\n con parametri: idProgetto = " + idProgetto + "; idTipoDocIndex = "
					+ idTipoDocIndex);
			Object[] param = new Object[] { idProgetto, idTipoDocIndex };
			List<AffidamentoVO> lista = getJdbcTemplate().query(sql, param, new BeanRowMapper(AffidamentoVO.class));
			LOG.info(prf + "Record trovati = " + lista.size());

			// Se ruolo valorizzato e' diverso da beneficiario, vanno tolti gli affidamenti
			// in stato DAINVIARE.
			if (!StringUtil.isEmpty(codiceRuolo)
					&& !Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER.equalsIgnoreCase(codiceRuolo)
					&& !Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA.equalsIgnoreCase(codiceRuolo)) {
				this.selezionaAffidamentiPerIstruttore(lista);
			}

			elencoDTO = beanUtil.transformList(lista, AffidamentoRendicontazioneDTO.class);

			// Popola l'elenco dei fornitori associati all'affidamento.
			for (AffidamentoRendicontazioneDTO dto : elencoDTO) {

				List<FornitoreAffidamentoVO> fornitori = this.fornitoriAffidamento(dto.getIdAppalto());

				ArrayList<String> listaFornitori = new ArrayList<String>();
				boolean fornitoreDocSpesaPresente = false;
				for (FornitoreAffidamentoVO vo : fornitori) {

					String descrizione = null;
					if (vo.getIdTipoSoggetto().intValue() == 1) {
						descrizione = vo.getCognomeFornitore() + " " + vo.getNomeFornitore() + " - "
								+ vo.getCodiceFiscaleFornitore();
					} else {
						descrizione = vo.getDenominazioneFornitore() + " - " + vo.getCodiceFiscaleFornitore();
					}
					listaFornitori.add(descrizione);

					// L'affidamento è selezionabile a video solo se tra i suoi fornitori c'è quello
					// del documento di spesa.
					if (idFornitoreDocSpesa == vo.getIdFornitore().longValue()) {
						fornitoreDocSpesaPresente = true;
					}
				}
				dto.setFornitori(listaFornitori);
				dto.setSelezionabile(fornitoreDocSpesaPresente);

			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca degli affidamenti: ", e);
			throw new DaoException(" ERRORE nella ricerca degli affidamenti di un progetto.");
		} finally {
			LOG.info(prf + " END");
		}

		return elencoDTO;
	}

	private void selezionaAffidamentiPerIstruttore(List<AffidamentoVO> lista) {
		if (lista != null) {
			Iterator<AffidamentoVO> iterator = lista.iterator();
			while (iterator.hasNext()) {
				AffidamentoVO item = iterator.next();
				int idStato = item.getIdStatoAffidamento().intValue();
				if (idStato == Constants.ID_STATO_AFFIDAMENTO_DAINVIARE) {
					iterator.remove();
				}
			}
		}
	}

	private List<FornitoreAffidamentoVO> fornitoriAffidamento(Long idAppalto) throws DaoException {
		String prf = "[DocumentoDiSpesaDAOImpl::fornitoriAffidamento] ";
		LOG.info(prf + "BEGIN");
		List<FornitoreAffidamentoVO> result = null;
		try {
			LOG.info(prf + "idAppalto = " + idAppalto);

			if (idAppalto == null) {
				return new ArrayList<FornitoreAffidamentoVO>();
			}

			String sql = fileSqlUtil.getQuery("FornitoreAffidamentoVO.sql");
			LOG.info(prf + "\n" + sql + "\n con parametri: idAppalto = " + idAppalto);
			Object[] param = new Object[] { idAppalto };
			result = getJdbcTemplate().query(sql, param, new BeanRowMapper(FornitoreAffidamentoVO.class));
			LOG.info(prf + "Record trovati = " + result.size());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dei fornitori di un affidamento: ", e);
			throw new DaoException(" ERRORE nella ricerca dei fornitori di un affidamento.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public List<DocumentoAllegatoDTO> allegatiFornitore(long idFornitore, long idProgetto, long idUtente,
			String idIride) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::allegatiFornitore] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idFornitore = " + idFornitore + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idFornitore == 0) {
			throw new InvalidParameterException("idFornitore non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_FORNITORE);
			if (idEntita == null)
				throw new DaoException("Id entita non trovato.");

			FileDTO[] files = archivioBusinessImpl.getFilesAssociatedFornitoriOrQualifiche(idUtente, idIride,
					idFornitore, idEntita.longValue(), idProgetto);

			for (FileDTO fileDTO : files) {
				DocumentoAllegatoDTO temp = new DocumentoAllegatoDTO();
				temp.setId(fileDTO.getIdDocumentoIndex());
				temp.setNome(fileDTO.getNomeFile());
				temp.setIdProgetto(fileDTO.getIdProgetto());
				temp.setDisassociabile(false);
				temp.setCodiceVisualizzatoProgetto(fileDTO.getCodiceVisualizzato());
				temp.setDtAssociazione(fileDTO.getDtAssociazione()); // Jira PBANDI-2890.

				result.add(temp);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca degli allegati del fornitore: ", e);
			throw new DaoException(" ERRORE nella ricerca degli allegati del fornitore.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	// Analogo ad allegatiFornitore(); al momento non è esposto come servizio.
	public List<DocumentoAllegatoDTO> allegatiQualifica(Long progrFornitoreQualifica, Long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::allegatiQualifica] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "progrFornitoreQualifica = " + progrFornitoreQualifica + "; idUtente = " + idUtente);

		if (progrFornitoreQualifica == null) {
			throw new InvalidParameterException("progrFornitoreQualifica non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_R_FORNITORE_QUALIFICA);
			if (idEntita == null)
				throw new DaoException("Id entita non trovato.");

			FileDTO[] files = archivioBusinessImpl.getFilesAssociatedFornitoriOrQualifiche(idUtente, idIride,
					progrFornitoreQualifica, idEntita.longValue(), null);

			for (FileDTO fileDTO : files) {
				DocumentoAllegatoDTO temp = new DocumentoAllegatoDTO();
				temp.setId(fileDTO.getIdDocumentoIndex());
				temp.setNome(fileDTO.getNomeFile());
				temp.setIdProgetto(fileDTO.getIdProgetto());
				temp.setCodiceVisualizzatoProgetto(fileDTO.getCodiceVisualizzato());

				// Al momento questo metodo è chiamato solo dalla Validazione, quindi metto
				// false; in futuro si vedrà.
				temp.setDisassociabile(false);

				result.add(temp);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca degli allegati della qualifica: ", e);
			throw new DaoException(" ERRORE nella ricerca degli allegati della qualifica.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	
	@Override
	// Ex pbandiweb GestioneDocumentiDiSpesaBusinessImpl.findDocumentiAllegati()
	public List<DocumentoAllegatoDTO> allegatiDocumentoDiSpesa(long idDocumentoDiSpesa, String flagDocElettronico,
			long idProgetto, long idUtente, String idIride) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::allegatiDocumentoDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; flagDocElettronico = " + flagDocElettronico
				+ "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			// *************************
			// NOTA : valore da passare ad angular in fase di inizializzazione.
			// Boolean isRegola =
			// profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride,
			// idProgetto, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
			// LOG.info(prf + "isRegola = "+isRegola);
			// *************************

			FileDTO[] files = archivioBusinessImpl.getFilesAssociatedDocDiSpesa(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto);
			LOG.info(prf + "numero allegati = " + files.length);

			boolean isDocumentoElettronico = "S".equalsIgnoreCase(flagDocElettronico);

			boolean hasDocumentoNumProtocollo = false;
			if (!ObjectUtil.isEmpty(files)) {
				for (FileDTO file : files) {
					if (isDocumentoElettronico && !hasDocumentoNumProtocollo) {
						if (!StringUtil.isEmpty(file.getNumProtocollo()) && file.getDescBreveStatoDocSpesa() != null
								&& (file.getDescBreveStatoDocSpesa().equals(
										it.csi.pbandi.pbweb.pbandiutil.common.Constants.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE)
										|| file.getDescBreveStatoDocSpesa().equals(
												it.csi.pbandi.pbweb.pbandiutil.common.Constants.STATO_DOCUMENTO_DI_SPESA_RESPINTO))) {
							hasDocumentoNumProtocollo = true;
							break;
						}
					} else {
						break;
					}
				}

				for (FileDTO file : files) {
					DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
					allegato.setId(file.getIdDocumentoIndex());
					allegato.setNome(file.getNomeFile());
					allegato.setIdParent(file.getIdFolder());
					allegato.setProtocollo(file.getNumProtocollo());
					if (file.getIsLocked() == null || !file.getIsLocked()) {
						allegato.setDisassociabile(true);
						allegato.setDocumentoDiSpesaElettronico(isDocumentoElettronico);
						allegato.setHasDocumentoDiSpesaProtocollo(hasDocumentoNumProtocollo);
					}
					result.add(allegato);
				}
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca degli allegati del documento di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca degli allegati del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb ArchivioFileAction.dissociate()
	public EsitoDTO disassociaAllegatoDocumentoDiSpesa(long idDocumentoIndex, long idDocumentoDiSpesa, long idProgetto,
			long idUtente, HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::disassociaAllegatoDocumentoDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idDocumentoDiSpesa = " + idDocumentoDiSpesa
				+ "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_DOCUMENTO_DI_SPESA);
			if (idEntita == null)
				throw new DaoException("Id entita non trovato.");

			Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex,
					idEntita.longValue(), idDocumentoDiSpesa, idProgetto);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggio(esitoPbandisrv.getMessage());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel disassociare gli allegati del documento di spesa: ", e);
			throw new DaoException(" ERRORE nel disassociare gli allegati del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex pbandiweb ArchivioFileAction.dissociate()
	public EsitoDTO disassociaAllegatoPagamento(long idDocumentoIndex, long idPagamento, long idProgetto, long idUtente,
			String idIride) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::disassociaAllegatoPagamento] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idPagamento = " + idPagamento + "; idProgetto = "
				+ idProgetto + "; idUtente = " + idUtente);

		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idPagamento == 0) {
			throw new InvalidParameterException("idPagamento non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_PAGAMENTO);
			if (idEntita == null)
				throw new DaoException("Id entita non trovato.");

			Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex,
					idEntita.longValue(), idPagamento, idProgetto);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggio(esitoPbandisrv.getMessage());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel disassociare gli allegati della quietanza: ", e);
			throw new DaoException(" ERRORE nel disassociare gli allegati della quietanza.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex pbandiweb
	// GestioneDocumentiDiSpesaBusinessImpl.salvaDocumentoDiSpesaSemplificazione()
	@Transactional(rollbackFor = { Exception.class })
	public EsitoDTO salvaDocumentoDiSpesa(DocumentoDiSpesaDTO documentoDiSpesaDTO, Long progrBandoLinea, long idUtente,
			HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::salvaDocumentoDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idUtente = " + idUtente);

		if (documentoDiSpesaDTO == null) {
			throw new InvalidParameterException("documentoDiSpesaDTO non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		LOG.info(documentoDiSpesaDTO.toString());

		EsitoDTO esito = new EsitoDTO();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			String descBreveTipoDocumentoDiSpesa = documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa();
			LOG.info(prf + "descBreveTipoDocumentoDiSpesa = " + descBreveTipoDocumentoDiSpesa);

			// Calcolo il totale del documento come:
			// CEDOLINO COSTI STANDARD:
			// - CEDOLINO o CEDOLINO COCOPRO : uguale all' imponibile
			// - SPESE FORFETTARIE: e' inserito dall' utente
			// - tutti gli altri casi e' dato dalla somma tra imponibil ed iva
			if (isCedolinoCostiStandard(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isDocumentoGenerico(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isAutocertificazioneSpese(descBreveTipoDocumentoDiSpesa)
					|| isSALSenzaQuietanza(descBreveTipoDocumentoDiSpesa)
					|| isSALConQuietanza(descBreveTipoDocumentoDiSpesa)
					|| isCompensoMensileTirocinante(descBreveTipoDocumentoDiSpesa)) {
				LOG.info(prf + documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa()
						+ ": setto importo totale e imponibile col rendicontabile: "
						+ documentoDiSpesaDTO.getImportoRendicontabile());
				documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(documentoDiSpesaDTO.getImportoRendicontabile());
				documentoDiSpesaDTO.setImponibile(documentoDiSpesaDTO.getImportoRendicontabile());
			} else if (isCedolino(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isCedolinoCOCOPRO(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())) {
				documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(documentoDiSpesaDTO.getImponibile());
			} else if (!isSpeseForfettarie(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					&& !isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					&& !isDocumentoGenerico(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					&& !isCompensoImpresaArtigiana(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					&& !isCompensoSoggettoGestore(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					&& !isSpesaExtraAffidamento(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())) {
				Double importoIva = NumberUtil.toRoundDouble(documentoDiSpesaDTO.getImportoIva());
				Double imponibile = documentoDiSpesaDTO.getImponibile();

				documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(NumberUtil.sum(imponibile, importoIva));
			}

			// Se il tipo di documento e' SPESE FORFETTARIE allora
			// l'importo rendicondabile e l'imponibile sono uguali all'importo totale del
			// documento.
			if (isSpeseForfettarie(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isSpesaExtraAffidamento(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isCompensoImpresaArtigiana(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
					|| isCompensoSoggettoGestore(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())) {
				documentoDiSpesaDTO.setImportoRendicontabile(documentoDiSpesaDTO.getImportoTotaleDocumentoIvato());
				documentoDiSpesaDTO.setImponibile(documentoDiSpesaDTO.getImportoTotaleDocumentoIvato());
			}

			// modifica per bug clona/nuovo documento
			documentoDiSpesaDTO.setCodiceFiscaleFornitore(null);

			LOG.info("SALVA DOCUMENTO DI SPESA =====> FLAG ELETT XML ====> " + documentoDiSpesaDTO.getFlagElettXml());

			EsitoOperazioneDocumentoDiSpesa esitoPbandisrv = gestioneDocumentiDiSpesaBusinessImp
					.salvaDocumentoDiSpesa(idUtente, idIride, documentoDiSpesaDTO, progrBandoLinea);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setId(esitoPbandisrv.getIdDocumentoDiSpesa());
			if (esitoPbandisrv.getMessaggi() != null && esitoPbandisrv.getMessaggi()[0] != null)
				esito.setMessaggio(esitoPbandisrv.getMessaggi()[0].getMsgKey());
			if (esito.getEsito() && StringUtil.isEmpty(esito.getMessaggio()))
				esito.setMessaggio(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
			LOG.info(esito.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel salvare il documento di spesa: ", e);
			throw new DaoException(" ERRORE nel salvare il documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex pbandiweb
	// ValidazioneDichiarazioneDiSpesaBusinessImpl.modificaDocumentoDiSpesaSemplificazione()
	@Transactional(rollbackFor = { Exception.class })
	public EsitoOperazioneDTO salvaDocumentoDiSpesaValidazione(DocumentoDiSpesaDTO documentoDiSpesaDTO, long idUtente,
			HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::salvaDocumentoDiSpesaValidazione] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idUtente = " + idUtente);

		if (documentoDiSpesaDTO == null) {
			throw new InvalidParameterException("documentoDiSpesaDTO non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		LOG.info(documentoDiSpesaDTO.toString());

		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			String descBreveTipoDocumentoDiSpesa = documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa();
			LOG.info(prf + "descBreveTipoDocumentoDiSpesa = " + descBreveTipoDocumentoDiSpesa);

			// Calcolo il totale del documento come: - CEDOLINO o CEDOLINO COCOPRO :
			// uguale all' imponibile - SPESE FORFETTARIE: e' inserito dall' utente
			// - tutti gli altri casi e' dato dalla somma tra imponibil ed iva

			String descBreveTipoDocumento = documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa();

			if (descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO)
					|| descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO_COCOPRO)) {
				documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(documentoDiSpesaDTO.getImponibile());
			} else if (descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_FORFETTARIE)) {
				Double importoIva = NumberUtil.toRoundDouble(documentoDiSpesaDTO.getImportoIva());
				Double imponibile = documentoDiSpesaDTO.getImponibile();
				if (imponibile == null) {
					imponibile = documentoDiSpesaDTO.getImportoTotaleDocumentoIvato();
					documentoDiSpesaDTO.setImponibile(documentoDiSpesaDTO.getImportoTotaleDocumentoIvato());
				}
				documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(NumberUtil.sum(imponibile, importoIva));
				LOG.info("SPESE_FORFETTARIE: ImportoTotaleDocumentoIvato = "
						+ documentoDiSpesaDTO.getImportoTotaleDocumentoIvato());
			}

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO docSrv;
			docSrv = this.trasforma(documentoDiSpesaDTO);

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneDocumentoDiSpesa esitoSrv;
			esitoSrv = validazioneRendicontazioneBusinessImpl.modificaDocumentoDiSpesaSemplificazione(idUtente, idIride,
					docSrv);

			esito.setEsito(esitoSrv.getEsito());

			if (esitoSrv.getMessaggi() != null) {
				ArrayList<String> lista = new ArrayList<String>();
				for (it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.MessaggioDTO dto : esitoSrv
						.getMessaggi()) {
					lista.add(dto.getMsgKey());
				}
				esito.setMessaggi(TraduttoreMessaggiPbandisrv.traduci(lista));
			}

			LOG.info(esito.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel salvare il documento di spesa da validazione : ", e);
			throw new DaoException(" ERRORE nel salvare il documento di spesa da validazione.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO trasforma(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO dto) {

		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO newDto = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO();

		newDto.setCodiceFiscaleFornitore(dto.getCodiceFiscaleFornitore());
		newDto.setCodiceProgetto(dto.getCodiceProgetto());
		newDto.setCognomeFornitore(dto.getCognomeFornitore());
		newDto.setDataDocumentoDiSpesa(dto.getDataDocumentoDiSpesa());
		newDto.setDataDocumentoDiSpesaDiRiferimento(dto.getDataDocumentoDiSpesaDiRiferimento());
		newDto.setDescrizioneDocumentoDiSpesa(dto.getDescrizioneDocumentoDiSpesa());
		newDto.setDestinazioneTrasferta(dto.getDestinazioneTrasferta());
		newDto.setDenominazioneFornitore(dto.getDenominazioneFornitore());
		newDto.setDescStatoDocumentoSpesa(dto.getDescStatoDocumentoSpesa());
		newDto.setDescTipoDocumentoDiSpesa(dto.getDescTipologiaDocumentoDiSpesa());
		newDto.setDescTipologiaFornitore(dto.getDescTipologiaFornitore());
		newDto.setDurataTrasferta(dto.getDurataTrasferta());
		newDto.setIdBeneficiario(dto.getIdBeneficiario());
		newDto.setIdDocRiferimento(dto.getIdDocRiferimento());
		newDto.setIdDocumentoDiSpesa(dto.getIdDocumentoDiSpesa());
		newDto.setIdFornitore(dto.getIdFornitore());
		newDto.setIdProgetto(dto.getIdProgetto());
		newDto.setIdSoggetto(dto.getIdSoggetto());
		newDto.setIdSoggettoPartner(dto.getIdSoggettoPartner());
		newDto.setIdTipoDocumentoDiSpesa(dto.getIdTipoDocumentoDiSpesa());
		newDto.setIdTipoFornitore(dto.getIdTipoFornitore());
		newDto.setIdTipoOggettoAttivita(dto.getIdTipoOggettoAttivita());
		newDto.setImponibile(dto.getImponibile());
		newDto.setImportoIva(dto.getImportoIva());
		newDto.setImportoIvaACosto(dto.getImportoIvaACosto());
		newDto.setImportoRendicontabile(dto.getImportoRendicontabile());
		newDto.setImportoTotaleDocumentoIvato(dto.getImportoTotaleDocumentoIvato());
		newDto.setImportoRitenutaDAcconto(dto.getImportoRitenutaDAcconto());
		newDto.setIsGestitiNelProgetto(dto.getIsGestitiNelProgetto());
		newDto.setIsRicercaPerCapofila(dto.getIsRicercaPerCapofila());
		newDto.setIsRicercaPerTutti(dto.getIsRicercaPerTutti());
		newDto.setIsRicercaPerPartner(dto.getIsRicercaPerPartner());
		newDto.setNomeFornitore(dto.getNomeFornitore());
		newDto.setNumeroDocumento(dto.getNumeroDocumento());
		newDto.setNumeroDocumentoDiSpesa(dto.getNumeroDocumentoDiSpesa());
		newDto.setNumeroDocumentoDiSpesaDiRiferimento(dto.getNumeroDocumentoDiSpesaDiRiferimento());
		newDto.setPartitaIvaFornitore(dto.getPartitaIvaFornitore());
		newDto.setPartner(dto.getPartner());
		newDto.setCostoOrario(dto.getCostoOrario());
		newDto.setStatiDocumento(null);
		newDto.setTask(dto.getTask());
		newDto.setImportoTotaleValidato(dto.getImportoTotaleValidato());
		newDto.setDescBreveTipoDocumentoDiSpesa(dto.getDescBreveTipoDocumentoDiSpesa());
		newDto.setImportoTotaleRendicontato(dto.getImportoTotaleRendicontato());
		newDto.setProgrFornitoreQualifica(dto.getProgrFornitoreQualifica());
		newDto.setImportoTotaleQuietanzato(dto.getImportoTotaleQuietanzato());
		newDto.setIdStatoDocumentoSpesa(dto.getIdStatoDocumentoSpesa());
		newDto.setRendicontabileQuietanzato(dto.getRendicontabileQuietanzato());
		newDto.setIsValidabile(null);
		newDto.setMsgNonValidabile(null);
		newDto.setTipoInvio(dto.getTipoInvio());
		newDto.setIdAppalto(dto.getIdAppalto());
		newDto.setNoteValidazione(dto.getNoteValidazione());

		LOG.info("\n\n IdTipoDocumentoDiSpesa() = " + newDto.getIdTipoDocumentoDiSpesa() + "  --  "
				+ newDto.getIdTipoDocumentoDiSpesa());
		LOG.info("\n\n CodiceFiscaleFornitore() = " + newDto.getCodiceFiscaleFornitore() + "  --  "
				+ newDto.getCodiceFiscaleFornitore());

		return newDto;
	}

	private boolean isSpesaExtraAffidamento(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_EXTRA_AFFIDAMENTO);
		return result;
	}

	private boolean isSpeseGeneraliForfettarieCostiStandard(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null && descBreveTipoDocumento
				.equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_STANDARD);
		return result;
	}

	private boolean isCedolinoCOCOPRO(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO_COCOPRO);
		return result;
	}

	private boolean isCedolino(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO);
		return result;
	}

	private boolean isSpeseForfettarie(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_FORFETTARIE);
		return result;
	}

	private boolean isDocumentoGenerico(String descBreveTipoDocumento) {
		return descBreveTipoDocumento != null
				&& (descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_DOCUMENTO_GENERICO));
	}

	private boolean isCedolinoCostiStandard(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO_COSTI_STANDARD);
		return result;
	}

	private boolean isAutocertificazioneSpese(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_AUTOCERTIFICAZIONE_SPESE);
		return result;
	}

	private boolean isSALSenzaQuietanza(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SAL_SENZA_QUIETANZA);
		return result;
	}

	private boolean isSALConQuietanza(String descBreveTipoDocumento) {
		boolean result = descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SAL_CON_QUIETANZA);
		return result;
	}

	private boolean isCompensoMensileTirocinante(String descBreveTipoDocumento) {
		boolean result = false;
		if (descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_COMPENSO_MENSILE_TIROCINANTE)) {
			result = true;
		}
		return result;
	}

	private boolean isCompensoImpresaArtigiana(String descBreveTipoDocumento) {
		boolean result = false;
		if (descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_COMPENSO_IMPRESA_ARTIGIANA)) {
			result = true;
		}
		return result;
	}


	private boolean isCompensoSoggettoGestore(String descBreveTipoDocumento) {
		boolean result = false;
		if (descBreveTipoDocumento != null
				&& descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_COMPENSO_SOGGETTO_GESTORE)) {
			result = true;
		}
		return result;
	}

	@Override
	// Ex pbandiweb
	// GestioneVociDiSpesaBusinessImpl.findVociDiSpesaAssociateDocumentoSemplificazione()
	// tipoOperazioneDocSpesa = 'dettaglio', 'inserisci'. 'clona' etc
	// descBreveStatoDocSpesa =
	// PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_BREVE_STATO_DOC_SPESA
	public List<VoceDiSpesaDTO> vociDiSpesaAssociateRendicontazione(long idDocumentoDiSpesa, long idProgetto,
			long idSoggetto, String codiceRuolo, String tipoOperazioneDocSpesa, String descBreveStatoDocSpesa,
			long idUtente, HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::vociDiSpesaAssociateRendicontazione] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idSoggetto = "
				+ idSoggetto + "; codiceRuolo = " + codiceRuolo + "; tipoOperazioneDocSpesa = " + tipoOperazioneDocSpesa
				+ "; descBreveStatoDocSpesa = " + descBreveStatoDocSpesa + "; idUtente = " + idUtente);

		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idSoggetto == 0) {
			throw new InvalidParameterException("idSoggetto non valorizzato");
		}
		if (StringUtil.isEmpty(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (StringUtil.isEmpty(tipoOperazioneDocSpesa)) {
			throw new InvalidParameterException("tipoOperazioneDocSpesa non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<VoceDiSpesaDTO> result = new ArrayList<VoceDiSpesaDTO>();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			// Legge le voci di spesa associate tramite il servizio di pandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO[] vociSpesaPbandisrv = null;
			vociSpesaPbandisrv = gestioneVociDiSpesaBusinessImpl.findVociDiSpesaAssociateSemplificazione(idUtente,
					idIride, idDocumentoDiSpesa, idProgetto);

			result = beanUtil.transformToArrayList(vociSpesaPbandisrv,
					it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO.class);

			// Il link "cancella" e' visualizzabile solo se
			// l'utente e' abilitato al caso d'uso OPEREN035 ed il tipo di operazione non e'
			// DETTAGLIO
			// oppure se
			// l'utente e' abilitato al caso d'uso OPEREN035, l'attivita' e' DICHIARAZIONE
			// DI SPESA (detta anche RENDICONTAZIONE)
			// ed il documento e' in stato DICHIARABILE.
			/*
			 * boolean cuOPEREN035abiliato = profilazioneDao.hasPermesso(idSoggetto,
			 * idUtente, codiceRuolo, UseCaseConstants.UC_OPEREN035);
			 *
			 * boolean cancellazioneAbilitata = (cuOPEREN035abiliato &&
			 * !Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO.equalsIgnoreCase(
			 * tipoOperazioneDocSpesa) || (cuOPEREN035abiliato &&
			 * Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(
			 * descBreveStatoDocSpesa))); LOG.info(prf +
			 * "cuOPEREN035abiliato = "+cuOPEREN035abiliato+"; cancellazioneAbilitata = "
			 * +cancellazioneAbilitata);
			 */
			boolean isCancellabile = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN035);
			if (isCancellabile) {
				if (Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO.equalsIgnoreCase(tipoOperazioneDocSpesa)) {
					isCancellabile = false;
				} else if (!Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(descBreveStatoDocSpesa)) {
					isCancellabile = false;
				}
			}

			// Il link "modifica" e' visualizzabile solo se
			// l'utente e' abilitato al caso d'uso OPEREN034 ed il tipo di operazione non e'
			// DETTAGLIO
			// oppure se
			// l'utente e' abilitato al caso d'uso OPEREN034 e l'attivita' e' DICHIARAZIONE
			// DI SPESA ed il documento e' in stato DICHIARABILE
			/*
			 * boolean cuOPEREN034abiliato = profilazioneDao.hasPermesso(idSoggetto,
			 * idUtente, codiceRuolo, UseCaseConstants.UC_OPEREN034); boolean
			 * modificaAbilitata = (cuOPEREN034abiliato &&
			 * !Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO.equalsIgnoreCase(
			 * tipoOperazioneDocSpesa) || (cuOPEREN034abiliato &&
			 * Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(
			 * descBreveStatoDocSpesa))); LOG.info(prf +
			 * "cuOPEREN034abiliato = "+cuOPEREN034abiliato+"; modificaAbilitata = "
			 * +modificaAbilitata);
			 */
			boolean isModificabile = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN034);
			if (isModificabile) {
				if (Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO.equalsIgnoreCase(tipoOperazioneDocSpesa)) {
					isModificabile = false;
				} else if (!Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(descBreveStatoDocSpesa)) {
					isModificabile = false;
				}
			}

			for (VoceDiSpesaDTO dto : result) {
				dto.setCancellazioneAbilitata(isCancellabile);
				dto.setModificaAbilitata(isModificabile);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle voci di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca delle voci di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb
	// GestioneVociDiSpesaBusinessImpl.findVociDiSpesaAssociateDocumentoSemplificazione()
	// Stessa cosa di vociDiSpesaAssociateRendicontazione, ma la cancellazione è
	// sempre impedita.
	public List<VoceDiSpesaDTO> vociDiSpesaAssociateValidazione(long idDocumentoDiSpesa, long idProgetto,
			long idSoggetto, String codiceRuolo, String tipoOperazioneDocSpesa, String descBreveStatoDocSpesa,
			long idUtente, HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::vociDiSpesaAssociateValidazione] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idSoggetto = "
				+ idSoggetto + "; codiceRuolo = " + codiceRuolo + "; tipoOperazioneDocSpesa = " + tipoOperazioneDocSpesa
				+ "; descBreveStatoDocSpesa = " + descBreveStatoDocSpesa + "; idUtente = " + idUtente);

		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idSoggetto == 0) {
			throw new InvalidParameterException("idSoggetto non valorizzato");
		}
		if (StringUtil.isEmpty(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (StringUtil.isEmpty(tipoOperazioneDocSpesa)) {
			throw new InvalidParameterException("tipoOperazioneDocSpesa non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<VoceDiSpesaDTO> result = new ArrayList<VoceDiSpesaDTO>();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			// Legge le voci di spesa associate tramite il servizio di pandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO[] vociSpesaPbandisrv = null;
			vociSpesaPbandisrv = gestioneVociDiSpesaBusinessImpl.findVociDiSpesaAssociateSemplificazione(idUtente,
					idIride, idDocumentoDiSpesa, idProgetto);

			// Da oggetto pbandisrv a oggetto pbweb.
			result = beanUtil.transformToArrayList(vociSpesaPbandisrv,
					it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO.class);

			// In validazione il link "cancella" non è mai abilitato.
			boolean isCancellabile = false;

			// Il link "modifica" e' visualizzabile solo se
			// l'utente e' abilitato al caso d'uso OPEREN034 ed il tipo di operazione non e'
			// DETTAGLIO
			// oppure se
			// l'utente e' abilitato al caso d'uso OPEREN034 e l'attivita' e' DICHIARAZIONE
			// DI SPESA ed il documento e' in stato DICHIARABILE
			boolean isModificabile = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN034);
			if (isModificabile) {
				if (Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO.equalsIgnoreCase(tipoOperazioneDocSpesa)) {
					isModificabile = false;
				}
				/*
				 * else if (!Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(
				 * descBreveStatoDocSpesa)) { isModificabile = false; }
				 */
			}

			for (VoceDiSpesaDTO dto : result) {
				dto.setCancellazioneAbilitata(isCancellabile);
				dto.setModificaAbilitata(isModificabile);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle voci di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca delle voci di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb VociDiSpesaAction.loadMacroVoci() +
	// GestioneVociDiSpesaBusinessImpl.findMacroVociDiSpesaSemplificazione()
	// NOTA: ogni voce di spesa macro contiene l'elenco delle relative voci di spesa
	// micro.
	public List<VoceDiSpesaPadre> macroVociDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente,
			HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::macroVociDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = "
				+ idUtente);

		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<VoceDiSpesaPadre> macroVoci = new ArrayList<VoceDiSpesaPadre>();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			// Legge le voci di spesa da pbandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO[] vociDTO = null;
			vociDTO = gestioneVociDiSpesaBusinessImpl.findVociDiSpesaProgettoSemplificazione(idUtente, idIride,
					idProgetto, idDocumentoDiSpesa);
			VoceDiSpesaPadre vocePadre = null;
			ArrayList<VoceDiSpesaFiglia> vociFiglie = null;
			for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO dto : vociDTO) {
				if (dto.getIdVoceDiSpesaPadre() != null) {
					if (vocePadre == null) {
						vocePadre = new VoceDiSpesaPadre();
						vocePadre.setIdVoceDiSpesa(dto.getIdVoceDiSpesaPadre());
						vocePadre.setDescVoceDiSpesa(dto.getDescVoceDiSpesaPadre());
						vocePadre.setIdTipologiaVoceDiSpesa(dto.getIdTipologiaVoceDiSpesa());
						vociFiglie = new ArrayList<VoceDiSpesaFiglia>();
					}

					if (vocePadre.getIdVoceDiSpesa().intValue() != dto.getIdVoceDiSpesaPadre().intValue()) {
						// Nuova voce di spesa padre

						// Inserisco la precendente macro voce nella lista.
						vocePadre.setVociDiSpesaFiglie(vociFiglie);
						macroVoci.add(vocePadre);

						// Creo la nuova voce di spesa padre, inizializzando la lista delle voci figlie
						vocePadre = new VoceDiSpesaPadre();
						vocePadre.setIdVoceDiSpesa(dto.getIdVoceDiSpesaPadre());
						vocePadre.setDescVoceDiSpesa(dto.getDescVoceDiSpesaPadre());
						vocePadre.setIdTipologiaVoceDiSpesa(dto.getIdTipologiaVoceDiSpesa());

						vociFiglie = new ArrayList<VoceDiSpesaFiglia>();
					}

					VoceDiSpesaFiglia voceFiglia = new VoceDiSpesaFiglia();
					voceFiglia.setIdVoceDiSpesa(dto.getIdVoceDiSpesa());
					voceFiglia.setDescVoceDiSpesa(dto.getDescVoceDiSpesa());
					voceFiglia.setImportoAmmessoFinanziamento(dto.getImportoFinanziamento());
					voceFiglia.setIdRigoContoEconomico(dto.getIdRigoContoEconomico());
					voceFiglia.setImportoResiduoAmmesso(dto.getImportoResiduoAmmesso());
					voceFiglia.setIdTipologiaVoceDiSpesa(dto.getIdTipologiaVoceDiSpesa());
					vociFiglie.add(voceFiglia);

				} else {
					// La voce e' una macro

					if (vocePadre != null) {
						// Inserisco la precendente macro voce nella lista
						vocePadre.setVociDiSpesaFiglie(vociFiglie);
						macroVoci.add(vocePadre);
					}

					// Creo la nuova voce di spesa padre, inizializzando la lista delle voci figlie.
					vocePadre = new VoceDiSpesaPadre();
					vocePadre.setIdVoceDiSpesa(dto.getIdVoceDiSpesa());
					vocePadre.setDescVoceDiSpesa(dto.getDescVoceDiSpesa());
					vocePadre.setImportoAmmessoFinanziamento(dto.getImportoFinanziamento());
					vocePadre.setIdRigoContoEconomico(dto.getIdRigoContoEconomico());
					vocePadre.setImportoResiduoAmmesso(dto.getImportoResiduoAmmesso());
					vocePadre.setIdTipologiaVoceDiSpesa(dto.getIdTipologiaVoceDiSpesa());
					vociFiglie = new ArrayList<VoceDiSpesaFiglia>();

				}
			}

			// Inserisco l'ultima macro voce nella lista
			if (vocePadre != null) {
				vocePadre.setVociDiSpesaFiglie(vociFiglie);
				macroVoci.add(vocePadre);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle macro voci di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca delle macro voci di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return macroVoci;
	}

	@Override
	// Ex pbandiweb
	// GestioneDocumentiDiSpesaBusinessImpl.salvaDocumentoDiSpesaSemplificazione()
	@Transactional(rollbackFor = { Exception.class })
	public EsitoDTO associaVoceDiSpesa(VoceDiSpesaDTO voceDiSpesaDTO, long idUtente, HttpServletRequest req)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::associaVoceDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idUtente = " + idUtente);

		if (voceDiSpesaDTO == null) {
			throw new InvalidParameterException("voceDiSpesaDTO non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoDTO esito = new EsitoDTO();
		try {
			LOG.info(prf + "voceDiSpesa.IdQuotaParteDocSpesa = " + voceDiSpesaDTO.getIdQuotaParteDocSpesa());

			String idIride = RequestUtil.idIrideInSessione(req);

			Long idUtenteLong = idUtente;
			Long idDocSpesa = voceDiSpesaDTO.getIdDocSpesa();
			Long idProgetto = voceDiSpesaDTO.getIdProgetto();
			Boolean forzaSalvataggio = true;

			// Da oggetto pbweb a oggetto pbandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO dtoPbandisrv = beanUtil.transform(
					voceDiSpesaDTO, it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO.class);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.EsitoOperazioneVociDiSpesa esitpPbandisrv = null;
			if (voceDiSpesaDTO.getIdQuotaParteDocSpesa() == null
					|| voceDiSpesaDTO.getIdQuotaParteDocSpesa().intValue() == 0) {
				LOG.info(prf + "Eseguo gestioneVociDiSpesaBusinessImpl.associaVoceDiSpesaDocumentoSemplificazione");
				esitpPbandisrv = gestioneVociDiSpesaBusinessImpl.associaVoceDiSpesaDocumentoSemplificazione(
						idUtenteLong, idIride, idProgetto, idDocSpesa, dtoPbandisrv, forzaSalvataggio);
			} else {
				LOG.info(prf + "Eseguo gestioneVociDiSpesaBusinessImpl.modificaVoceDiSpesaDocumentoSemplificazione");
				esitpPbandisrv = gestioneVociDiSpesaBusinessImpl.modificaVoceDiSpesaDocumentoSemplificazione(
						idUtenteLong, idIride, idProgetto, idDocSpesa, dtoPbandisrv, forzaSalvataggio);
			}

			esito.setEsito(esitpPbandisrv.getEsito());
			esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitpPbandisrv.getMessage()));
			if (esitpPbandisrv.getVoceDiSpesa() != null) {
				esito.setId(esitpPbandisrv.getVoceDiSpesa().getIdQuotaParteDocSpesa());
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel salvare il documento di spesa: ", e);
			throw new DaoException(" ERRORE nel salvare il documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex pbandiweb VoceDiSpesaAction.cancella()
	public EsitoDTO disassociaVoceDiSpesa(long idQuotaParteDocSpesa, long idUtente, HttpServletRequest req)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::disassociaVoceDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idQuotaParteDocSpesa = " + idQuotaParteDocSpesa + "; idUtente = " + idUtente);

		if (idQuotaParteDocSpesa == 0) {
			throw new InvalidParameterException("idQuotaParteDocSpesa non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.EsitoOperazioneVociDiSpesa esitoPbandisrv = null;
			esitoPbandisrv = gestioneVociDiSpesaBusinessImpl.eliminaVoceDiSpesaDocumentoSemplificazione(idUtente,
					idIride, idQuotaParteDocSpesa);

			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggio(esitoPbandisrv.getMessage());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel disassociare la voce di spesa del documento di spesa: ", e);
			throw new DaoException(" ERRORE nel disassociare la voce di spesa del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex pbandiweb PagamentoAction.caricaModalita()
	public List<DecodificaDTO> modalitaPagamento(long idDocumentoDiSpesa, long idProgetto, long idUtente,
			HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::modalitaPagamento] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = "
				+ idUtente);

		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
		try {

			String idIride = RequestUtil.idIrideInSessione(req);

			String sqlSeq = "select ID_TIPO_DOCUMENTO_SPESA from PBANDI_T_DOCUMENTO_DI_SPESA where ID_DOCUMENTO_DI_SPESA = "
					+ idDocumentoDiSpesa;
			LOG.info("\n" + sqlSeq);
			BigDecimal idTipoDocumentoSpesa = getJdbcTemplate().queryForObject(sqlSeq, BigDecimal.class);
			if (idTipoDocumentoSpesa == null)
				throw new DaoException("idTipoDocumentoSpesa non trovato.");
			LOG.info(prf + "ID_TIPO_DOCUMENTO_SPESA = " + idTipoDocumentoSpesa);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.ModalitaPagamentoDTO[] modalitaPbandisrv = null;
			modalitaPbandisrv = gestionePagamentiBusinessImpl.findModalitaPagamento(idUtente, idIride, idProgetto,
					idTipoDocumentoSpesa.longValue());

			for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.ModalitaPagamentoDTO dto : modalitaPbandisrv) {
				DecodificaDTO decodifica = new DecodificaDTO();
				decodifica.setId(dto.getIdModalitaPagamento());
				decodifica.setDescrizione(dto.getDescModalitaPagamento());
				decodifica.setDescrizioneBreve(dto.getDescBreveModalitaPagamento());
				result.add(decodifica);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle modalita di pagamento del documento di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca delle modalita di pagamento del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb PagamentoAction.getCausaliPagamento()
	public List<DecodificaDTO> causaliPagamento(HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::causaliPagamento] ";
		LOG.info(prf + "BEGIN");

		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
		try {

			String sql = "select ID_CAUSALE_PAGAMENTO as id, DESC_CAUSALE_PAGAMENTO as descrizione, DESC_BREVE_CAUSALE_PAGAMENTO as descrizioneBreve from PBANDI_D_CAUSALE_PAGAMENTO where TIPO_PAGAMENTO = 'P' order by DESC_CAUSALE_PAGAMENTO";
			LOG.info("\n" + sql);
			result = getJdbcTemplate().query(sql, new Object[] {}, new BeanRowMapper(DecodificaDTO.class));
			LOG.info(prf + "Record trovati = " + result.size());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle causali di pagamento del documento di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca delle causali di pagamento del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb PagamentoAction.cancella(); usato nel tab "Quietanze"
	// nell'attività "Rendicontazione" (ex "Dichiarazione di Spesa").
	public EsitoDTO disassociaPagamento(long idPagamento, HttpServletRequest req) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::disassociaPagamento] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idPagamento = " + idPagamento);

		if (idPagamento == 0) {
			throw new InvalidParameterException("idPagamento non valorizzato");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.EsitoOperazioneCancellazionePagamento esitoPbandisrv = null;
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamentoPbandisrv = new it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO();
			pagamentoPbandisrv.setIdPagamento(idPagamento);
			Long idBando = null; // lo metto a null poichè in realtà l'idPbando non è usato in
			// eliminaPagamento().
			esitoPbandisrv = gestionePagamentiBusinessImpl.eliminaPagamento(userInfo.getIdUtente(),
					userInfo.getIdIride(), pagamentoPbandisrv, idBando);

			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggio(esitoPbandisrv.getMessage());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel disassociare la voce di spesa del documento di spesa: ", e);
			throw new DaoException(" ERRORE nel disassociare la voce di spesa del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Associa un allegato al pagamento\quietanza di un documento di spesa.
	public EsitoAssociaFilesDTO associaAllegatiAPagamento(AssociaFilesRequest associaFilesRequest, Long idUtente)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::associaAllegatiAPagamento] ";
		LOG.info(prf + "BEGIN");

		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_PAGAMENTO);

			esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
			LOG.info(prf + esito.toString());

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel associare allegati alla quietanza: ", e);
			throw new DaoException(" ERRORE nel associare allegati alla quietanza.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Associa gli allegati ad un documento di spesa.
	public EsitoAssociaFilesDTO associaAllegatiADocSpesa(AssociaFilesRequest associaFilesRequest, Long idUtente)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::associaAllegatiADocSpesa] ";
		LOG.info(prf + "BEGIN");

		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_DOCUMENTO_DI_SPESA);

			esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
			LOG.info(prf + esito.toString());

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel associare allegati al documento di spesa: ", e);
			throw new DaoException(" ERRORE nel associare al documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Popola la combo delle voci di spesa nella finestra di ricerca dei Documenti
	// Di Spesa (Rendicontazione).
	// Ex GestioneDocumentiDiSpesaBusinessImpl.findAllVociDiSpesaPadreConFigli()
	public List<VoceDiSpesaPadre> vociDiSpesaRicerca(long idProgetto, HttpServletRequest req) throws Exception {
		String prf = "[DecodificheDAOImpl::vociDiSpesaRicerca] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " input: idProgetto = " + idProgetto);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}

		List<VoceDiSpesaPadre> result = new ArrayList<VoceDiSpesaPadre>();
		try {
			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			VoceDiSpesaPadreDTO[] vdsDto = null;
			vdsDto = gestioneDocumentiDiSpesaBusinessImp.findAllVociDiSpesaPadreConFigli(userInfo.getIdUtente(),
					userInfo.getIdIride(), idProgetto);
			LOG.info(Arrays.toString(vdsDto));
			result = new ArrayList<VoceDiSpesaPadre>(vdsDto.length);
			Map<String, String> figlieMap = new HashMap<String, String>() {
				{
					put("codice", "idVoceDiSpesa");
					put("descrizione", "descVoceDiSpesa");
				}
			};
			for (VoceDiSpesaPadreDTO voceDiSpesaPadreDTO : vdsDto) {
				VoceDiSpesaPadre temp = beanUtil.transform(voceDiSpesaPadreDTO, VoceDiSpesaPadre.class);
				temp.setVociDiSpesaFiglie(beanUtil.transformToArrayList(voceDiSpesaPadreDTO.getVociDiSpesaFiglie(),
						VoceDiSpesaFiglia.class, figlieMap));
				result.add(temp);
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new DaoException(" ERRORE nella ricerca delle voci di spesa: ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Popola la combo "Rendicontazione" nella finestra di ricerca dei Documenti Di
	// Spesa (Rendicontazione).
	// Contiene i partners di un beneficiario capofila.
	// Ex
	// GestioneDocumentiDiSpesaBusinessImpl.findFiltroRicercaRendicontazionePartners()
	public FiltroRicercaRendicontazionePartners partners(long idProgetto, long idBandoLinea, HttpServletRequest req)
			throws Exception {
		String prf = "[DecodificheDAOImpl::partners] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " input: idProgetto = " + idProgetto + "; idBandoLinea = " + idBandoLinea);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == 0) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}

		FiltroRicercaRendicontazionePartners result = new FiltroRicercaRendicontazionePartners();
		try {

			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO[] partnersrv = null;
			partnersrv = gestioneDocumentiDiSpesaBusinessImp.findPartners(userInfo.getIdUtente(), userInfo.getIdIride(),
					idProgetto);

			ArrayList<DecodificaDTO> elenco = new ArrayList<DecodificaDTO>();
			for (int i = 0; i < partnersrv.length; i++) {
				DecodificaDTO obj = new DecodificaDTO();
				obj.setDescrizioneBreve(partnersrv[i].getIdSoggetto() + Constants.SEPARATORE_SOGGETTO_PROGETTO_PARTNER
						+ partnersrv[i].getIdProgettoPartner());
				obj.setDescrizione(partnersrv[i].getDescrizione());
				elenco.add(obj);
			}

			ArrayList<DecodificaDTO> opzioni = new ArrayList<DecodificaDTO>();
			DecodificaDTO capofila = new DecodificaDTO();
			capofila.setDescrizioneBreve(Constants.VALUE_RADIO_RENDICONTAZIONE_CAPOFILA);
			capofila.setDescrizione("Capofila");
			opzioni.add(capofila);

			if (elenco != null && elenco.size() != 0) {
				DecodificaDTO tuttiPartners = new DecodificaDTO();
				tuttiPartners.setDescrizioneBreve(Constants.VALUE_RADIO_RENDICONTAZIONE_PARTNERS);
				tuttiPartners.setDescrizione("Tutti i Partners");
				opzioni.add(tuttiPartners);
			}

			boolean regolaAttiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(userInfo.getIdUtente(),
					userInfo.getIdIride(), idBandoLinea, RegoleConstants.BR08_FILTRI_RICERCA_BENEFICIARIO_CAPOFILA);

			boolean beneficiarioCapofila = false;
			if (regolaAttiva) {
				beneficiarioCapofila = progettoManager.isCapofila(new BigDecimal(idProgetto));
			}
			LOG.info(prf + "regolaAttiva = " + regolaAttiva + "; beneficiarioCapofila = " + beneficiarioCapofila);

			result.setOpzioni(opzioni);
			result.setPartners(elenco);
			result.setVisibile(beneficiarioCapofila);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new DaoException(" ERRORE nella ricerca dei partners: ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ricerca dei i documenti di spesa in Rendicontazione
	// Ex GestioneDocumentiDiSpesaBusinessImpl.ricercaDocumentiDiSpesa()
	public EsitoRicercaDocumentiDiSpesa ricercaDocumentiDiSpesa(Long idProgetto, String codiceProgettoCorrente,
			Long idSoggettoBeneficiario, String codiceRuolo, FiltroRicercaDocumentiSpesa filtro, Long idUtente,
			String idIride, Long idSoggetto) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::ricercaDocumentiDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idProgetto = " + idProgetto + "; codiceProgettoCorrente = " + codiceProgettoCorrente
				+ "; idSoggettoBeneficiario = " + idSoggettoBeneficiario + "; codiceRuolo = " + codiceRuolo);
		LOG.info(prf + filtro.toString());

		EsitoRicercaDocumentiDiSpesa esito = new EsitoRicercaDocumentiDiSpesa();
		esito.setEsito(true);
		esito.setMessaggio(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
		try {

			// UserInfoSec userInfo = (UserInfoSec)
			// req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			// Long idUtente = userInfo.getIdUtente();
			// String idIride = userInfo.getIdIride();
			// Long idSoggetto = userInfo.getIdSoggetto();
			LOG.info(prf + "input: idUtente = " + idUtente + "; idSoggetto = " + idSoggetto);

			// Da filtro web a filtro pbandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.FiltroRicercaDocumentiSpesa filtroDto = new it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.FiltroRicercaDocumentiSpesa();
			filtroDto.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
			filtroDto.setIdProgetto(idProgetto);
			filtroDto.setData(filtro.getData());
			filtroDto.setDataA(filtro.getDataA());
			filtroDto.setIdFornitore(filtro.getIdFornitore());
			filtroDto.setIdStato(filtro.getIdStato());
			filtroDto.setIdTipologia(filtro.getIdTipologia());
			filtroDto.setIdVoceDiSpesa(filtro.getIdVoceDiSpesa());
			filtroDto.setNumero(filtro.getNumero());
			filtroDto.setIdCategoria(filtro.getIdCategoria());

			final String rendicontazione = filtro.getPartner();
			final boolean rendicontazioneCapofila = Constants.VALUE_RADIO_RENDICONTAZIONE_CAPOFILA
					.equals(rendicontazione);

			if (rendicontazioneCapofila) {
				filtroDto.setRendicontazione(SELETTORE_RICERCA_RENDICONTAZIONE_CAPOFILA);
			} else if (Constants.VALUE_RADIO_RENDICONTAZIONE_PARTNERS.equals(rendicontazione)) {
				filtroDto.setRendicontazione(SELETTORE_RICERCA_RENDICONTAZIONE_TUTTI_I_PARTNERS);
			} else {
				filtroDto.setRendicontazione(rendicontazione);
			}

			final String documentiDiSpesa = filtro.getDocumentiDiSpesa();
			// }L{ PBANDI-2198 : tutti solo se cerco per capofila
			final boolean gestitiNelProgetto = !rendicontazioneCapofila
					|| Constants.VALUE_RADIO_DOCUMENTI_DI_SPESA_GESTITI.equals(documentiDiSpesa);

			if (gestitiNelProgetto) {
				filtroDto.setDocumentiDiSpesa(SELETTORE_RICERCA_DOCUMENTI_GESTITI_NEL_PROGETTO);
				filtroDto.setTask(filtro.getTask());
			} else {
				filtroDto.setDocumentiDiSpesa(SELETTORE_RICERCA_DOCUMENTI_TUTTI);
			}

			boolean isClonabile = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN128_1);
			boolean isAssociabile = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN128);
			boolean operen012 = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN012);
			boolean operen013 = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN013);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.ItemRicercaDocumentiSpesa[] docs = null;
			try {

				docs = gestioneDocumentiDiSpesaBusinessImp.findDocumentiDiSpesaSemplificazione(idUtente, idIride,
						filtroDto);
			} catch (GestioneDocumentiDiSpesaException e) {
				LOG.error(prf + "GestioneDocumentiDiSpesaException: " + e.getMessage());
				esito.setEsito(false);
				esito.setMessaggio(e.getMessage());
				return esito;
			}

			// Da oggetto pbandisrv a oggetto web
			ArrayList<ElencoDocumentiSpesaItem> result = new ArrayList<ElencoDocumentiSpesaItem>();
			result = beanUtil.transformToArrayList(docs, ElencoDocumentiSpesaItem.class, new HashMap<String, String>() {
				{
					put("tipologia", "tipologia");
					put("estremi", "estremi");
					put("fornitore", "fornitore");
					put("importi", "importi");
					put("importo", "importo");
					put("validato", "validato");
					put("stato", "stato");
					put("progetto", "progetti");
					put("idDocumento", "idDocumento");
					put("isClonabile", "clonabile");
					put("isEliminabile", "eliminabile");
					put("isModificabile", "modificabile");
					put("isAssociabile", "associabile");
					put("isAssociato", "associato");
					put("tipoInvio", "tipoInvio");
					put("isAllegatiPresenti", "allegatiPresenti");
					put("idProgetto", "idProgetto");
				}
			});

			for (ElencoDocumentiSpesaItem item : result) {
				if (!operen013) {
					item.setEliminabile(false);
				}
				if (!operen012) {
					item.setModificabile(false);
				}
				if (!isAssociabile) {
					item.setAssociabile(false);
				}
				if (!isClonabile) {
					item.setClonabile(false);
				}
				item.setProgetto(codiceProgettoCorrente);
			}

			esito.setDocumenti(result);

		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella ricerca dei documenti di spesa. ", e);
			esito.setMessaggio(ErrorMessages.ERRORE_GENERICO);
			// throw new DaoException("ERRORE nella ricerca dei documenti di spesa. ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Dato l'id recupera il dettaglio di un documento di spesa.
	// Ex
	// GestioneDocumentiDiSpesaBusinessImpl.findDettaglioDocumentoDiSpesaSemplificazione()
	public DocumentoDiSpesaDTO documentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::documentoDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
				+ "; idUtente = " + idUtente);

		DocumentoDiSpesaDTO result = null;
		try {

			result = gestioneDocumentiDiSpesaBusinessImp.findDettaglioDocumentoDiSpesaSemplificazione(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto);
			LOG.info(result.toString());

		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella ricerca del dettaglio del documento di spesa. ", e);
			throw new DaoException("ERRORE nella ricerca del dettaglio del documento di spesa ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Dato l'id recupera il dettaglio di un documento di spesa.
	// Ex
	// GestioneDocumentiDiSpesaBusinessImpl.findDettaglioDocumentoDiSpesaSemplificazione()
	@Transactional(rollbackFor = { Exception.class })
	public EsitoDTO eliminaDocumentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::eliminaDocumentoDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
				+ "; idUtente = " + idUtente);

		EsitoDTO result = new EsitoDTO();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoOperazioneElimina esitoPbandisrv = null;
			esitoPbandisrv = gestioneDocumentiDiSpesaBusinessImp.eliminaDocumentoDiSpesaConPagamenti(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto);

			result.setEsito(true);
			if (esitoPbandisrv.getMessaggi() != null && esitoPbandisrv.getMessaggi()[0] != null) {
				result.setMessaggio(esitoPbandisrv.getMessaggi()[0]);
			}

		} catch (GestioneDocumentiDiSpesaException e) {
			result.setEsito(false);
			result.setMessaggio(e.getMessage());
			LOG.error(prf + result.getMessaggio());
			// Alex: aggiungo l'eccezione per forzare il rollback.
			throw new DaoException("ERRORE nella eliminazione del documento di spesa ", e);
		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella eliminazione del documento di spesa. ", e);
			throw new DaoException("ERRORE nella eliminazione del documento di spesa ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb PagamentoAction: salva() + conferma().
	@Transactional(rollbackFor = { UnrecoverableException.class, Exception.class })
	public EsitoDTO associaPagamento(AssociaPagamentoRequest associaPagamentoRequest, Long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::associaPagamento] ";
		LOG.info(prf + "BEGIN");

		if (associaPagamentoRequest == null) {
			throw new InvalidParameterException("associaPagamentoRequest non valorizzato");
		}
		LOG.info(associaPagamentoRequest.toString());

		Long idDocumentoDiSpesa = associaPagamentoRequest.getIdDocumentoDiSpesa();
		Long idProgetto = associaPagamentoRequest.getIdProgetto();
		Long idBandoLinea = associaPagamentoRequest.getIdBandoLinea();
		Boolean forzaOperazione = associaPagamentoRequest.getForzaOperazione(); // se true alcuni controlli vengono
		// ignorati.
		Boolean validazione = associaPagamentoRequest.getValidazione(); // true se si è in Validazione o in
		// ValidazioneFinale.


		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("IdDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("IdProgetto non valorizzato");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (forzaOperazione == null) {
			throw new InvalidParameterException("forzaOperazione non valorizzato");
		}
		if (validazione == null) {
			throw new InvalidParameterException("validazione non valorizzato");
		}
		// LOG.info(prf+"idUtente = "+idUtente+"; idDocumentoDiSpesa =
		// "+idDocumentoDiSpesa+"; idProgetto = "+idProgetto+"; idBandoLinea =
		// "+idBandoLinea);

		PagamentoFormDTO pagamentoFormDTO = associaPagamentoRequest.getPagamentoFormDTO();
		if (pagamentoFormDTO == null) {
			throw new InvalidParameterException("pagamentoFormDTO non valorizzato");
		}
		// LOG.info(pagamentoFormDTO.toString());

		// Aggiunta BR62 - Quietanza non ancora disponibile

		Boolean BR62Attiva = progettoManager.isProgettoAssociatoRegola(idProgetto, RegoleConstants.BR62_DICHIARAZIONE_CON_QUIETANZA_ZERO);


		EsitoDTO esito = new EsitoDTO();
		try {

			// Popolo l'oggetto da passare al servizio di pbandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamentoDTOpbandisrv = new it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO();
			pagamentoDTOpbandisrv.setIdPagamento(pagamentoFormDTO.getIdPagamento());
			pagamentoDTOpbandisrv.setIdModalitaPagamento(pagamentoFormDTO.getIdModalitaPagamento());
			pagamentoDTOpbandisrv.setIdCausalePagamento(pagamentoFormDTO.getIdCausalePagamento());
			pagamentoDTOpbandisrv.setDtPagamento(pagamentoFormDTO.getDtPagamento());
			pagamentoDTOpbandisrv.setImportoPagamento(pagamentoFormDTO.getImportoPagamento());
			pagamentoDTOpbandisrv.setRifPagamento(pagamentoFormDTO.getRifPagamento());
			pagamentoDTOpbandisrv.setFlagPagamento(pagamentoFormDTO.getFlagPagamento());

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] vociDiSpesaSrv = null;
			// Alex
			// Nella vecchia versione le voci di spesa arrivavano dalla proprietà
			// "vociDiSpesa" della request gestita in PagamentoAction.
			// Non ho capito però quale js dovrebbe popolare tali voci di spesa.
			// L'unico candidato mi sembrava pagamentiVDS.js, che però non mi sembra essere
			// usato da nessuno.
			// Quindi per ora lascio la variabile a null.

			// PK 8/7/2022
			// recupero l'elenco delle voci di spesa
			ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO> elencoVociDiSpesa = associaPagamentoRequest
					.getVociDiSpesa();
			if (elencoVociDiSpesa != null && !elencoVociDiSpesa.isEmpty()) {
				LOG.info(" trovato un elencoVociDiSpesa.size=" + elencoVociDiSpesa.size());

				vociDiSpesaSrv = elencoVociDiSpesa.toArray(
						new it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[elencoVociDiSpesa
								.size()]);
			}

			if (pagamentoFormDTO.getIdPagamento() == null) {

				it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.EsitoOperazioneInserimentoPagamento esitoPbadisrv = null;
				if (BR62Attiva) {
					esitoPbadisrv = gestionePagamentiBusinessImpl.inserisciPagamentoBR62(idUtente, idIride, vociDiSpesaSrv,
							pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
				} else if (forzaOperazione) {
					esitoPbadisrv = gestionePagamentiBusinessImpl.forzaInserisciPagamento(idUtente, idIride,
							vociDiSpesaSrv, pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
				}
				else {
					esitoPbadisrv = gestionePagamentiBusinessImpl.inserisciPagamento(idUtente, idIride, vociDiSpesaSrv,
							pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
				}
				esito.setEsito(esitoPbadisrv.getEsito());
				try {
					esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoPbadisrv.getMessage()));
				}
				catch (Exception e) {
					esito.setMessaggio(esitoPbadisrv.getMessage());
				}
				LOG.info(prf + "Msg: " + esitoPbadisrv.getMessage() + " ==> " + esito.getMessaggio());

			} else {

				it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.EsitoOperazioneModificaPagamento esitoPbadisrv = null;
				if (validazione) {
					esitoPbadisrv = gestionePagamentiBusinessImpl.modificaPagamentoPerValidazione(idUtente, idIride,
							vociDiSpesaSrv, pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
				} else {
					if (BR62Attiva) {
						esitoPbadisrv = gestionePagamentiBusinessImpl.modificaPagamentoBR62(idUtente, idIride,
								vociDiSpesaSrv, pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
						//cerco allegati associati al pagamento
						FileDTO[] files = archivioBusinessImpl.getFilesAssociatedPagamento(idUtente, idIride, pagamentoDTOpbandisrv.getIdPagamento());
						if (files.length > 0 && pagamentoDTOpbandisrv.getFlagPagamento() != null && pagamentoDTOpbandisrv.getFlagPagamento().equals("S")) {
							//se ci sono allegati li elimino
							for (FileDTO allegato : files) {
								disassociaAllegatoPagamento(allegato.getIdDocumentoIndex(), pagamentoDTOpbandisrv.getIdPagamento(), idProgetto, idUtente, idIride);
							}
						}
					} else if (forzaOperazione) {
						esitoPbadisrv = gestionePagamentiBusinessImpl.forzaModificaPagamento(idUtente, idIride,
								vociDiSpesaSrv, pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
					} else {
						esitoPbadisrv = gestionePagamentiBusinessImpl.modificaPagamento(idUtente, idIride,
								vociDiSpesaSrv, pagamentoDTOpbandisrv, idBandoLinea, idDocumentoDiSpesa, idProgetto);
					}
				}
				esito.setEsito(esitoPbadisrv.getEsito());
				try {
					esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoPbadisrv.getMessage()));
				} catch (Exception e) {
					esito.setMessaggio(esitoPbadisrv.getMessage());
				}
				LOG.info(prf + "Msg: " + esitoPbadisrv.getMessage() + " ==> " + esito.getMessaggio());

			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel salvataggio del pagamento del documento di spesa: ", e);
			throw new DaoException(" ERRORE nel salvataggio del pagamento del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex pbandiweb
	// GestioneDocumentiDiPagamentoBusinessImpl.findPagamentiAssociatiDocumentoDiSpesaSemplificazione()
	// validazione: true se si arrviva da Validazione o da ValidazioneFinale.
	public List<DocumentoDiPagamentoDTO> pagamentiAssociati(PagamentiAssociatiRequest pagamentiAssociatiRequest,
			long idUtente, String idIride, long idSoggetto) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::pagamentiAssociati] ";
		LOG.info(prf + "BEGIN");

		if (pagamentiAssociatiRequest == null) {
			throw new InvalidParameterException("pagamentiAssociatiRequest non valorizzato");
		}

		Long idDocumentoDiSpesa = pagamentiAssociatiRequest.getIdDocumentoDiSpesa();
		String tipoInvioDocumentoDiSpesa = pagamentiAssociatiRequest.getTipoInvioDocumentoDiSpesa();
		String descBreveStatoDocSpesa = pagamentiAssociatiRequest.getDescBreveStatoDocSpesa();
		String tipoOperazioneDocSpesa = pagamentiAssociatiRequest.getTipoOperazioneDocSpesa();
		Long idProgetto = pagamentiAssociatiRequest.getIdProgetto();
		Long idBandoLinea = pagamentiAssociatiRequest.getIdBandoLinea();
		String codiceRuolo = pagamentiAssociatiRequest.getCodiceRuolo();
		Boolean validazione = pagamentiAssociatiRequest.getValidazione();

		if (idDocumentoDiSpesa == 0) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (StringUtil.isEmpty(tipoInvioDocumentoDiSpesa)) {
			throw new InvalidParameterException("tipoInvioDocumentoDiSpesa non valorizzato");
		}
		if (StringUtil.isEmpty(descBreveStatoDocSpesa)) {
			throw new InvalidParameterException("descBreveStatoDocSpesa non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idBandoLinea == 0) {
			throw new InvalidParameterException("idBandoLinea non valorizzato");
		}
		if (StringUtil.isEmpty(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; tipoInvioDocumentoDiSpesa = "
				+ tipoInvioDocumentoDiSpesa + "; descBreveStatoDocSpesa = " + descBreveStatoDocSpesa
				+ "; tipoOperazioneDocSpesa = " + tipoOperazioneDocSpesa + "; idProgetto = " + idProgetto
				+ "; idBandoLinea = " + idBandoLinea + "; codiceRuolo = " + codiceRuolo + "; validazione = "
				+ validazione + "; idSoggetto = " + idSoggetto + "; idUtente = " + idUtente);

		List<DocumentoDiPagamentoDTO> list = new ArrayList<DocumentoDiPagamentoDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO[] listPagamentiAssociati = null;
			if (validazione) {
				listPagamentiAssociati = gestionePagamentiBusinessImpl.findPagamentiAssociatiPerValidazione(idUtente,
						idIride, idDocumentoDiSpesa, idBandoLinea, idProgetto);
			} else {
				listPagamentiAssociati = gestionePagamentiBusinessImpl.findPagamentiAssociati(idUtente, idIride,
						idDocumentoDiSpesa, idBandoLinea, idProgetto);
			}

			boolean isAbilitatoModificaPagamento = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN016); // OPEREN016 Modifica
			boolean isAbilitatoEliminaPagamento = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN019); // OPEREN019 Elimina

			boolean isDocumentoDichiarabileAllProgetti = gestioneDocumentiDiSpesaBusinessImp
					.isDocumentoDichiarabileInAllProgetti(idUtente, idIride, idDocumentoDiSpesa, idProgetto);

			if (listPagamentiAssociati != null && listPagamentiAssociati.length > 0) {

				for (int i = 0; i < listPagamentiAssociati.length; i++) {
					// 26 giugno 2015
					boolean isPagamentoModificabile = isAbilitatoModificaPagamento
							&& isDocumentoDichiarabileAllProgetti;

					boolean isPagamentoEliminabile = isAbilitatoEliminaPagamento && isDocumentoDichiarabileAllProgetti;
					DocumentoDiPagamentoDTO dto = rimappaObjPagamentoClient(listPagamentiAssociati[i]);

					// Se sono in validazione l' utente puo' solamente modificare i
					// pagamenti che sono in stato diverso da INSERITO e RESPINTO se
					// e' abilitato al caso d'uso

					// FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
					// String statoPagamento = dto.getDescStatoValidazioneSpesa();

					// }L{ tolto dal blocco non-validazione: e' buono per entrambi
					if (Constants.TIPO_INVIO_DIGITALE.equalsIgnoreCase(tipoInvioDocumentoDiSpesa)) {

						// Ricerco i documenti allegati al pagamento

						LOG.info(prf + "cerco gli allegati al pagamento" + dto.getId()
								+ " tramite archivioBusinessImpl.getFilesAssociatedPagamento");
						FileDTO[] files = archivioBusinessImpl.getFilesAssociatedPagamento(idUtente, idIride,
								dto.getId());

						// E' possibile allegare file ai pagamenti se e solo se il
						// tipo di invio del documento di spesa e' D e per il
						// bandolinea e' abilitata la regola BR42
						boolean regolaFileAttiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
								idIride, idBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
						regolaFileAttiva = regolaFileAttiva
								|| profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
										idBandoLinea, RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA);
						LOG.info(prf + "regolaFileAttiva = " + regolaFileAttiva);

						if (regolaFileAttiva) {

							// }L{ l'istruttore non allega files
							dto.setLinkAllegaFileVisible(!validazione);

							if (!ObjectUtil.isEmpty(files)) {
								ArrayList<DocumentoAllegatoDTO> allegati = new ArrayList<DocumentoAllegatoDTO>();
								for (FileDTO file : files) {
									DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
									allegato.setId(file.getIdDocumentoIndex());
									allegato.setNome(file.getNomeFile());
									allegato.setIdParent(file.getIdFolder());

									// Un allegato e' dissocibile se il documento e' in stato a DICHIARABILE o
									// RESPINTO per tutti i progetti
									// e se l'attivita' corrente e' quella della dichiarazione.
									if (!validazione) {
										// boolean isDocumentoDichiarabileAllProgetti =
										// gestioneDocumentiDiSpesaBusinessImpl
										// .isDocumentoDichiarabileInAllProgetti(user, idProgetto, documento.getId());
										allegato.setDisassociabile(isDocumentoDichiarabileAllProgetti);
									} else {
										allegato.setDisassociabile(false);
									}
									allegati.add(allegato);
								}
								dto.setDocumentiAllegati(allegati);
							}

						} else {
							// }L{ 2410: deve comunque essere possibile vedere i
							// file allegati
							LOG.info("tipo invio non digitale: disabilito LinkAllegaFile ");
							dto.setLinkAllegaFileVisible(false);

							if (!ObjectUtil.isEmpty(files)) {
								ArrayList<DocumentoAllegatoDTO> allegati = new ArrayList<DocumentoAllegatoDTO>();
								for (FileDTO file : files) {
									DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
									allegato.setId(file.getIdDocumentoIndex());
									allegato.setNome(file.getNomeFile());
									allegato.setIdParent(file.getIdFolder());
									// In questo caso non posso fare nulla all'allegato
									allegato.setDisassociabile(false);
									allegati.add(allegato);
								}
								dto.setDocumentiAllegati(allegati);
							}
						}

					} // fine if tipo invio = D

					boolean isPagamentoQuietanzato = listPagamentiAssociati[i].getIsQuietanzato();
					boolean isPagamentoUsedDichiarazioni = listPagamentiAssociati[i].getIsUsedDichiarazioni();
					if (validazione) {
						isPagamentoEliminabile = false;
						if (isPagamentoModificabile &&
						// FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
						// (statoPagamento.equalsIgnoreCase("INSERITO") ||
						// statoPagamento.equalsIgnoreCase("RESPINTO"))
								isPagamentoQuietanzato)
							isPagamentoModificabile = false;
					} else {

						// Sono modificabili i pagamenti che:
						// - utente abilitato al caso d'uso
						// - tipo di operazione non e' DETTAGLIO
						// - il documento e' in stato DICHIARABILE o DA COMPLETARE
						// - se il documento e' in stato DICHIARABILE allora il pagamento non deve
						// essere stato
						// ripartito tra le voci di spesa
						// - se il documento e' in stato DA COMPLETARE allora il pagamento non deve
						// essere stato
						// ripartito tra le voci di spesa e non deve essere stato utilizzato nelle
						// validazioni.
						// - il doc ï¿½ isDocumentoDichiarabileInAltriprogetti

						if (!Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(descBreveStatoDocSpesa)
								&& !Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE
										.equalsIgnoreCase(descBreveStatoDocSpesa)) {
							isPagamentoModificabile = false;
						}

						if (isPagamentoModificabile
						// FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
						// (!statoPagamento.equalsIgnoreCase("INSERITO") &&
						// !statoPagamento.equalsIgnoreCase("RESPINTO"))
						) {

							if (Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(descBreveStatoDocSpesa)
									&& isPagamentoQuietanzato)
								isPagamentoModificabile = false;
							else if (Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE
									.equalsIgnoreCase(descBreveStatoDocSpesa) && isPagamentoUsedDichiarazioni)
								isPagamentoModificabile = false;
						}

						if (isPagamentoModificabile && tipoOperazioneDocSpesa != null && tipoOperazioneDocSpesa
								.equals(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO)) {
							isPagamentoModificabile = false;
						}

						// Sono modificabili i pagamenti che:
						// - utente abilitato al caso d'uso
						// - tipo di operazione non e' DETTAGLIO
						// - il documento e' in stato DICHIARABILE o DA COMPLETARE
						// - se il documento e' in stato DICHIARABILE allora il pagamento non deve
						// essere stato
						// ripartito tra le voci di spesa o non deve essere stato utilizzato nelle
						// validazioni.
						// - se il documento e' in stato DA COMPLETARE allora il pagamento non deve
						// essere stato
						// ripartito tra le voci di spesa e non deve essere stato utilizzato nelle
						// validazioni.

						if (!descBreveStatoDocSpesa.equalsIgnoreCase(Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE)
								&& !descBreveStatoDocSpesa
										.equalsIgnoreCase(Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE)) {
							isPagamentoEliminabile = false;
						}

						if (isPagamentoEliminabile
						// FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
						// (!statoPagamento.equalsIgnoreCase("INSERITO") &&
						// !statoPagamento.equalsIgnoreCase("RESPINTO"))) {
						) {
							if (descBreveStatoDocSpesa.equalsIgnoreCase(Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE)
									&& (isPagamentoQuietanzato || isPagamentoUsedDichiarazioni))
								isPagamentoEliminabile = false;
							else if (descBreveStatoDocSpesa.equalsIgnoreCase(
									Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE) && isPagamentoUsedDichiarazioni)
								isPagamentoEliminabile = false;
						}

						if (isPagamentoEliminabile && tipoOperazioneDocSpesa != null && tipoOperazioneDocSpesa
								.equals(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO)) {
							isPagamentoEliminabile = false;
						}

					}
					dto.setPagamentoModificabile(isPagamentoModificabile);
					dto.setPagamentoEliminabile(isPagamentoEliminabile);

					// Alex: in visualizzazione non posso associare o disassociare gli allegati al
					// pagamento.
					if (tipoOperazioneDocSpesa != null
							&& tipoOperazioneDocSpesa.equals(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO)) {
						dto.setLinkAllegaFileVisible(false);
						if (dto.getDocumentiAllegati() != null) {
							for (DocumentoAllegatoDTO d : dto.getDocumentiAllegati()) {
								d.setDisassociabile(false);
							}
						}
					}

					list.add(dto);

				}
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella lettura dei pagamenti del documento di spesa: ", e);
			throw new DaoException(" ERRORE nella lettura dei pagamenti del documento di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return list;
	}

	private DocumentoDiPagamentoDTO rimappaObjPagamentoClient(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamentoDTO) {
		DocumentoDiPagamentoDTO documentoPagamento = new DocumentoDiPagamentoDTO();
		if (pagamentoDTO.getIdPagamento() != null)
			documentoPagamento.setId(pagamentoDTO.getIdPagamento());
		documentoPagamento.setDtPagamento(DateUtil.getDate(pagamentoDTO.getDtPagamento()));
		documentoPagamento.setCausalePagamento(pagamentoDTO.getCausalePagamento());
		documentoPagamento.setDescBreveModalitaPagamento(pagamentoDTO.getDescBreveModalitaPagamento());
		documentoPagamento.setDescrizioneModalitaPagamento(pagamentoDTO.getDescrizioneModalitaPagamento());
		if (pagamentoDTO.getImportoQuietanzato() == null || pagamentoDTO.getImportoQuietanzato().doubleValue() == 0d) {
			documentoPagamento.setImportoResiduoUtilizzabileVuoto(Boolean.TRUE);
		} else {
			documentoPagamento.setImportoResiduoUtilizzabileVuoto(Boolean.FALSE);
			documentoPagamento.setImportoResiduoUtilizzabile(pagamentoDTO.getImportoResiduoUtilizzabile());
		}

		documentoPagamento.setDestinatarioPagamento(pagamentoDTO.getDestinatarioPagamento());
		documentoPagamento.setEstremiPagamento(pagamentoDTO.getEstremiPagamento());
		if (pagamentoDTO.getIdModalitaPagamento() != null)
			documentoPagamento.setIdModalitaPagamento(pagamentoDTO.getIdModalitaPagamento());
		if (pagamentoDTO.getIdSoggetto() != null)
			documentoPagamento.setIdSoggetto(pagamentoDTO.getIdSoggetto());
		if (pagamentoDTO.getImportoPagamento() != null)
			documentoPagamento.setImportoPagamento(pagamentoDTO.getImportoPagamento());
		if (pagamentoDTO.getImportoRendicontabilePagato() != null)
			documentoPagamento.setImportoRendicontabilePagato(pagamentoDTO.getImportoRendicontabilePagato());
		documentoPagamento.setIdCausalePagamento(pagamentoDTO.getIdCausalePagamento());
		documentoPagamento.setRifPagamento(pagamentoDTO.getRifPagamento());
		if (pagamentoDTO.getFlagPagamento() != null) {
			documentoPagamento.setFlagPagamento(pagamentoDTO.getFlagPagamento());
		}
		return documentoPagamento;
	}

	@Override
	// Trova le note di credito associate ad una fattura.
	// Ex DocumentoDiSpesaAction.findNoteCredito() +
	// GestioneDocumentiDiSpesaBusinessImpl.findNoteCreditoFattura()
	public List<DocumentoDiSpesaDTO> noteDiCreditoFattura(long idDocumentoDiSpesa, long idProgetto, long idUtente,
			String idIride) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::documentoDiSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
				+ "; idUtente = " + idUtente);

		List<DocumentoDiSpesaDTO> elencoNoteCredito = new ArrayList<DocumentoDiSpesaDTO>();
		try {

			DocumentoDiSpesaDTO[] notedicredito;
			notedicredito = gestioneDocumentiDiSpesaBusinessImp.findNoteCreditoFattura(idUtente, idIride,
					idDocumentoDiSpesa);

			// Poiche' le note di credito possono essere duplicate, eseguo la pulizia della
			// lista.
			Long idNotaPrec = null;
			DocumentoDiSpesaDTO notaPrec = null;
			for (DocumentoDiSpesaDTO nota : notedicredito) {
				if (idNotaPrec == null) {
					idNotaPrec = nota.getIdDocumentoDiSpesa();
					notaPrec = nota;
				}

				Long idNotaCorrente = nota.getIdDocumentoDiSpesa();
				Long idProgettoNotaCorrente = nota.getIdProgetto();

				if (idNotaPrec.compareTo(idNotaCorrente) == 0) {
					// Sono sulla stessa nota, quello che cambia e'idProgetto associato alla nota.
					if (idProgettoNotaCorrente.compareTo(idProgetto) == 0) {
						notaPrec = nota;
					}
				} else {
					// Cambia la nota, quindi inserisco nella lista la nota precedente.
					// DocumentoDiSpesa notaCredito = beanUtil.transform(notaPrec,
					// DocumentoDiSpesa.class,fromDocumentoDiSpesaDTO2DocumentoDiSpesa);
					// elencoNoteCredito.add(notaCredito);
					elencoNoteCredito.add(notaPrec);
					notaPrec = nota;
					idNotaPrec = nota.getIdDocumentoDiSpesa();
				}
			}
			// Inserisco l'ultimo record
			if (notaPrec != null) {
				// DocumentoDiSpesa notaCredito = beanUtil.transform(notaPrec,
				// DocumentoDiSpesa.class,fromDocumentoDiSpesaDTO2DocumentoDiSpesa);
				// elencoNoteCredito.add(notaCredito);
				elencoNoteCredito.add(notaPrec);
			}

			Iterator iter = elencoNoteCredito.iterator();

		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella ricerca delle note di credito. ", e);
			throw new DaoException("ERRORE nella ricerca delle note di credito ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return elencoNoteCredito;
	}

	@Override
	// Trova i dati da usare nella popup per associare un doc di spesa ad un
	// progetto.
	// Ex RicercaDocSpesaAction.inizializza()
	public FormAssociaDocSpesa popolaFormAssociaDocSpesa(Long idDocumentoDiSpesa, Long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::popolaFormAssociaDocSpesa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idUtente = " + idUtente);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		FormAssociaDocSpesa form = new FormAssociaDocSpesa();
		try {

			form.setIdDocumentoDiSpesa(idDocumentoDiSpesa);

			final Double massimoRendicontabile = gestioneDocumentiDiSpesaBusinessImp.findMassimoRendicontabile(idUtente,
					idIride, idDocumentoDiSpesa);
			form.setMassimoRendicontabile(massimoRendicontabile);

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.TipoDocumentoDiSPesaDTO tipo;
			tipo = gestioneDocumentiDiSpesaBusinessImp.getTipoDocumentoDiSpesa(idUtente, idIride, idDocumentoDiSpesa);
			form.setDescBreveTipoDocumento(tipo.getDescBreveTipoDocSpesa());

			// Se il documento e' di tipo NOTA DI CREDITO allora l'importo rendicontabile e'
			// sempre 0
			if (tipo.getDescBreveTipoDocSpesa().equals(Constants.COD_TIPO_DOC_DI_SPESA_NOTA_CREDITO)) {
				form.setImportoRendicontabile(0D);
			} else {
				form.setImportoRendicontabile(massimoRendicontabile > 0 ? massimoRendicontabile : 0D);
			}

			// FIX PBANDI-2314: Non si controlla piu' se il documento e' totalmente
			// quitanzato
			// per poter associare ad un altro progetto.
			// L' unica condizione necessaria e sufficiente per poter associare un documento
			// ad un progetto e' la disponibilita' di rendicontabile
			// FIX PBANDI-2314 final Boolean isDocumentoTotalmenteQuietanzato =
			// gestioneDocumentiDiSpesaBusinessImpl.isDocumentoTotalmenteQuietanzato(user,
			// BeanUtil.convert(Long.class, idDocumentoDiSpesa));
			// form.setIsDocumentoTotalmenteQuietanzato(isDocumentoTotalmenteQuietanzato);

			final Boolean isTotalmenteRendicontato = massimoRendicontabile <= 0;
			form.setIsDocumentoTotalmenteRendicontato(isTotalmenteRendicontato);

		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella ricerca dei dati per associare il documento di spesa. ", e);
			throw new DaoException("ERRORE nella ricerca dei dati per associare il documento di spesa ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return form;
	}

	@Override
	// Trova i dati da usare nella popup per associare un doc di spesa ad un
	// progetto.
	// Ex RicercaDocSpesaAction.inizializza()
	public EsitoOperazioneDTO associaDocumentoDiSpesaAProgetto(Long idDocumentoDiSpesa, Long idProgetto,
			Long idProgettoDocumento, String task, Double importoRendicontabile, Long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::associaDocumentoDiSpesaAProgetto] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
				+ "; idUtente = " + idUtente);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (importoRendicontabile == null) {
			throw new InvalidParameterException("importoRendicontabile non valorizzato");
		}
		if (StringUtil.isEmpty(task)) {
			throw new InvalidParameterException("task non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		try {

			EsitoOperazioneDocumentoDiSpesa esitoPbandisrv = gestioneDocumentiDiSpesaBusinessImp
					.associaDocumentoAProgetto(idUtente, idIride, idDocumentoDiSpesa, idProgetto, idProgettoDocumento,
							task, importoRendicontabile == null ? 0D : importoRendicontabile);
			esito.setEsito(esitoPbandisrv.getEsito());
			if (esitoPbandisrv.getMessaggi() != null) {
				for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.MessaggioDTO msg : esitoPbandisrv
						.getMessaggi()) {
					String s = TraduttoreMessaggiPbandisrv.traduci(msg.getMsgKey());
					esito.getMessaggi().add(s);
				}
			}

		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella associazione del documento di spesa. ", e);
			// throw new DaoException("ERRORE nella associazione del documento di spesa. ",
			// e);
			esito.setEsito(false);
			esito.getMessaggi().add(ErrorMessages.ERRORE_GENERICO);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex DocumentoDiSpesaAction.esitoLetturaXmlFattElett()
	// Legge i dati dall'xml selezionato tramite Archivio File e
	// restituisce in output l'esito della lettura e del confronto tra fornitore in
	// fattura e su db.
	// - idDocumentoIndex = id della fatt elett selezionata tramite Archivio File.
	// - idSoggettoBeneficiario = user.getBeneficiarioSelezionato().getIdSoggetto().
	public EsitoLetturaXmlFattElett esitoLetturaXmlFattElett(Long idDocumentoIndex, Long idSoggettoBeneficiario,
			Long idTipoOperazioneProgetto, Long idUtente, String idIride) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::esitoLetturaXmlFattElett] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "input: idDocumentoIndex = " + idDocumentoIndex + "; idSoggettoBeneficiario = "
				+ idSoggettoBeneficiario + "; idTipoOperazioneProgetto = " + idTipoOperazioneProgetto + "; idUtente = "
				+ idUtente);

		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idSoggettoBeneficiario == null) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato");
		}
		if (idTipoOperazioneProgetto == null) {
			throw new InvalidParameterException("idTipoOperazioneProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoLetturaXmlFattElett esito = new EsitoLetturaXmlFattElett();
		try {

			esito = fatturaElettronicaManager.esitoLetturaXmlFattElett(idDocumentoIndex, idSoggettoBeneficiario,
					idTipoOperazioneProgetto, idUtente, idIride);

		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella lettura della fattura elettronica. ", e);
			esito.setEsito(Constants.FATT_ELETT_ERRORE_NON_PREVISTO);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public Double getQuotaImportoAgevolato(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente,
			String idIride, Long idBeneficiario) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::getQuotaImportoAgevolato] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		Double result = null;
		try {
			String sql;
			sql = "select rce.quota_importo_agevolato \r\n"
					+ "from pbandi_t_progetto p, pbandi_t_domanda d, pbandi_t_conto_economico ce, pbandi_r_conto_econom_mod_agev rce\r\n"
					+ "where p.id_progetto=?\r\n" + "and p.id_domanda=d.id_domanda\r\n"
					+ "and ce.id_domanda= d.id_domanda\r\n" + "and rce.id_conto_economico= ce.id_conto_economico\r\n"
					+ "and ce.dt_fine_validita is null";

			Object[] args = new Object[] { idProgetto };
			LOG.info("<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<Double> records = getJdbcTemplate().queryForList(sql, args, Double.class);
			if (records != null && records.size() > 0) {
				result = records.get(0);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getQuotaImportoAgevolato: ", e);
			throw new Exception(" ERRORE in getQuotaImportoAgevolato.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public BigDecimal getTipoBeneficiario(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::getTipoBeneficiario] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}

		Integer result = null;
		try {
			String sql;
			sql = "SELECT FLAG_PUBBLICO_PRIVATO\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO T, PBANDI_T_ENTE_GIURIDICO EG, PBANDI_T_SOGGETTO S\r\n"
					+ "WHERE T.ID_ENTE_GIURIDICO = EG.ID_ENTE_GIURIDICO AND T.ID_SOGGETTO = S.ID_SOGGETTO\r\n"
					+ "	AND T.ID_TIPO_ANAGRAFICA = 1 AND T.ID_TIPO_BENEFICIARIO != 4 \r\n"
					+ "	AND T.ID_PROGETTO = ?";

			Object[] args = new Object[] { idProgetto };
			LOG.info("<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<Integer> records = getJdbcTemplate().queryForList(sql, args, Integer.class);
			if (records != null && records.size() > 0) {
				result = records.get(0);
				LOG.info(prf + " <flagPubblicoPrivato>: "+ result);
				return new BigDecimal(result);
			}else {
				LOG.info(prf + " <flagPubblicoPrivato>: null");
				return null;
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getTipoBeneficiario: ", e);
			throw new Exception(" ERRORE in getTipoBeneficiario.");
		} finally {
			LOG.info(prf + " END");
		}
	}
	@Override
	public BigDecimal getTipoBeneficiario(Long idProgetto) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::getTipoBeneficiario] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}

		Integer result = null;
		try {
			String sql;
			sql = "SELECT FLAG_PUBBLICO_PRIVATO\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO T, PBANDI_T_ENTE_GIURIDICO EG, PBANDI_T_SOGGETTO S\r\n"
					+ "WHERE T.ID_ENTE_GIURIDICO = EG.ID_ENTE_GIURIDICO AND T.ID_SOGGETTO = S.ID_SOGGETTO\r\n"
					+ "	AND T.ID_TIPO_ANAGRAFICA = 1 AND T.ID_TIPO_BENEFICIARIO != 4 \r\n"
					+ "	AND T.ID_PROGETTO = ?";

			Object[] args = new Object[] { idProgetto };
			LOG.info("<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<Integer> records = getJdbcTemplate().queryForList(sql, args, Integer.class);
			if (records != null && records.size() > 0) {
				result = records.get(0);
				LOG.info(prf + " <flagPubblicoPrivato>: "+ result);
				return new BigDecimal(result);
			}else {
				LOG.info(prf + " <flagPubblicoPrivato>: null");
				return null;
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getTipoBeneficiario: ", e);
			throw new Exception(" ERRORE in getTipoBeneficiario.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public String getDocumentazioneDaAllegare(Long progrBandoLineaIntervento, Long idTipoDocumentoSpesa, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (progrBandoLineaIntervento == 0) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato");
		}
		if (idTipoDocumentoSpesa == 0) {
			throw new InvalidParameterException("idTipoDocumentoSpesa non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT a.HTML_ALLEGATO \r\n" + "FROM PBANDI_R_AL_TIP_DOC_BAN_LI_INT atd\r\n"
					+ "JOIN PBANDI_C_ALLEGATI a ON a.ID_ALLEGATO = atd.ID_ALLEGATO\r\n"
					+ "WHERE atd.PROGR_BANDO_LINEA_INTERVENTO = ? AND atd.ID_TIPO_DOCUMENTO_SPESA = ?";

			Object[] args = new Object[] { progrBandoLineaIntervento, idTipoDocumentoSpesa };
			LOG.info("<progrBandoLineaIntervento>: " + progrBandoLineaIntervento + ", <idTipoDocumentoSpesa>: "
					+ idTipoDocumentoSpesa);
			LOG.info(prf + "\n" + sql + "\n");

			List<String> htmls = getJdbcTemplate().queryForList(sql, args, String.class);
			if (htmls != null && htmls.size() > 0) {
				return htmls.get(0);
			}
			return "";
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getDocumentazioneDaAllegare: ", e);
			throw new ErroreGestitoException(" ERRORE in getDocumentazioneDaAllegare.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public List<ParametroCompensiDTO> getParametriCompensi(Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT ID_PARAMETRO_COMPENSO, CATEGORIA, ORE_SETTIMANALI, COMPENSO_DOVUTO_MENSILE, \r\n"
					+ "GIORNI_LAVORABILI_SETTIMANALI, ORARIO_MEDIO_GIORNALIERO, BUDGET_INIZIALE_TIROCINANTE, BUDGET_INIZIALE_IMPRESA\r\n"
					+ "FROM PBANDI_C_PARAMETRI_COMPENSI\r\n"
					+ "WHERE DATA_FINE_VALIDITA IS NULL OR DATA_FINE_VALIDITA > SYSDATE";

			LOG.info(prf + "\n" + sql + "\n");

			List<ParametroCompensiDTO> result = getJdbcTemplate().query(sql, new ParametriCompensiDTORowMapper());
			return result;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getParametriCompensi: ", e);
			throw new ErroreGestitoException(" ERRORE in getParametriCompensi.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public EsitoImportoSaldoDTO getImportoASaldo(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride,
			Long idBeneficiario) throws Exception {
		String prf = "[DocumentoDiSpesaDAOImpl::getImportoASaldo] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		EsitoImportoSaldoDTO esito = new EsitoImportoSaldoDTO();
		Double importoSpeso = null;
		Double sommaErogato = null;
		try {
			String sql;
			LOG.info(prf + "Calcolo importo speso");
			sql = "select  a.importo\r\n" + "from PBANDI_T_IMPORTO_FONTI_QTES a, pbandi_t_dati_progetto_qtes b, \r\n"
					+ "pbandi_d_fasi_qtes c,  PBANDI_D_SOGGETTO_FINANZIATORE d\r\n"
					+ "where a.id_dati_qtes = b.id_dati_qtes\r\n" + "and c.id_colonna_qtes = b.id_colonna_qtes\r\n"
					+ "and d.id_soggetto_finanziatore= a.id_soggetto_finanziatore\r\n"
					+ "and c.desc_breve_colonna_qtes = ? \r\n" + "and d.desc_breve_sogg_finanziatore = ? \r\n"
					+ "and b.id_progetto = ? \r\n";

			Object[] args = new Object[] { Constants.DESC_BREVE_COLONNA_QTES_AGG_DEF,
					Constants.DESC_BREVE_SOGGETTO_FINANZIATORE_FONDO_COMPLEMENTARE, idProgetto };
			LOG.info("<descBreveColonnaQte>: " + Constants.DESC_BREVE_COLONNA_QTES_AGG_DEF
					+ ", <descBreveSoggFinanziatore>: " + Constants.DESC_BREVE_SOGGETTO_FINANZIATORE_FONDO_COMPLEMENTARE
					+ ", <idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<Double> records = getJdbcTemplate().queryForList(sql, args, Double.class);
			if (records != null && records.size() > 0) {
				importoSpeso = records.get(0);
				if (importoSpeso == null) {
					importoSpeso = 0D;
				}
				LOG.info(prf + "Importo speso: " + importoSpeso);

				LOG.info(prf + "Calcolo somma erogato");
				sql = "SELECT sum(t.importo_erogazione) FROM PBANDI_T_EROGAZIONE T\r\n" + "WHERE T.ID_PROGETTO = ? ";

				args = new Object[] { idProgetto };
				LOG.info("<idProgetto>: " + idProgetto);
				LOG.info(prf + "\n" + sql + "\n");

				records = getJdbcTemplate().queryForList(sql, args, Double.class);
				if (records != null && records.size() > 0) {
					sommaErogato = records.get(0);
					if (sommaErogato == null) {
						sommaErogato = 0D;
					}
					LOG.info(prf + "Somma erogato: " + sommaErogato);

				} else {
					LOG.info(prf + "Somma erogato non trovato");
					esito.setEsito(Boolean.FALSE);
					esito.setMessaggio("ATTENZIONE! Impossibile calcolare l’importo a SALDO  -  Non sono state trovate le Erogazioni. Contattare l'ente istruttore!");
					return esito;
				}
			} else {
				LOG.info(prf + "Importo speso non trovato");
				esito.setEsito(Boolean.FALSE);
				esito.setMessaggio("ATTENZIONE! Impossibile calcolare l’importo a SALDO -  Non è stato trovato l’ IMPORTO SPESO (Q.E. rendicontazione).  Completare prima  il QTES !");
				return esito;
			}
			esito.setEsito(Boolean.TRUE);
			esito.setImportoSpeso(importoSpeso);
			esito.setSommaErogato(sommaErogato);

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getImportoASaldo: ", e);
			throw new Exception(" ERRORE in getImportoASaldo.");
		} finally {
			LOG.info(prf + " END");
		}
		return esito;
	}

	@Override
	public Long getGiorniMassimiQuietanzaPerBando(Long idBandoLinea, Long idUtente, String idIride) throws DataAccessException {
		String prf = "[DocumentoDiSpesaDAOImpl::getGiorniMassimiQuietanzaPerBando] ";
		LOG.info(prf + " BEGIN");
		String sql = "SELECT bando.GG_QUIETANZA FROM PBANDI_T_BANDO bando \n" +
				"JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandolinea ON BANDOLINEA.ID_BANDO = bando.ID_BANDO \n" +
				"WHERE bandolinea.PROGR_BANDO_LINEA_INTERVENTO = ?";
		Object[] args = new Object[]{idBandoLinea};
		Long result;
		try {
			result = getJdbcTemplate().queryForObject(sql, args, Long.class);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il recupero di gg_quietanza in getGiorniMassimiQuietanzaPerBando: ", e);
			result = null;
		}
		LOG.info(prf + " END");
		return result;
	}

}
