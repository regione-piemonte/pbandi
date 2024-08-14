/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper.*;
import it.csi.pbandi.pbwebcert.integration.vo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.AttachmentVO;
import it.csi.pbandi.pbwebcert.business.MailUtil;
import it.csi.pbandi.pbwebcert.business.manager.DocumentoManager;
import it.csi.pbandi.pbwebcert.business.manager.ReportManager;
import it.csi.pbandi.pbwebcert.dto.ProgettoNuovaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BandoLineaDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BeneficiarioDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.CodiceDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.EsitoOperazioni;
import it.csi.pbandi.pbwebcert.dto.certificazione.FiltroRicercaDocumentoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.LineaDiInterventoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.StatoPropostaDTO;
import it.csi.pbandi.pbwebcert.dto.manager.ReportCampionamentoDTO;
import it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbwebcert.exception.CertificazioneException;
import it.csi.pbandi.pbwebcert.exception.DaoException;
import it.csi.pbandi.pbwebcert.integration.dao.CertificazioneDao;
import it.csi.pbandi.pbwebcert.util.BeanUtil;
import it.csi.pbandi.pbwebcert.util.Constants;
import it.csi.pbandi.pbwebcert.util.DateUtil;
import it.csi.pbandi.pbwebcert.util.ErrorMessages;
import it.csi.pbandi.pbwebcert.util.FileSqlUtil;
import it.csi.pbandi.pbwebcert.util.MessageConstants;
import it.csi.pbandi.pbwebcert.util.StringUtil;
import it.csi.pbandi.pbwebcert.util.UploadUtil;
import it.csi.pbandi.pbwebcert.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbwebcert.util.tablewriter.TableWriter;

@Component
@EnableAsync
public class CertificazioneDaoImpl extends JdbcDaoSupport implements CertificazioneDao {

	@Autowired
	private it.csi.pbandi.pbwebcert.business.pbandisrv.business.certificazione.CertificazioneBusinessImpl certificazioneBusinessImpl;

	// private
	// it.csi.pbandi.pbservizit.pbandisrv.business.certificazione.CertificazioneBusinessImpl
	// certificazioneBusinessImpl;
	public it.csi.pbandi.pbwebcert.business.pbandisrv.business.certificazione.CertificazioneBusinessImpl getCertificazioneBusinessImpl() {
		return certificazioneBusinessImpl;
	}

	public void setCertificazioneBusinessImpl(
			it.csi.pbandi.pbwebcert.business.pbandisrv.business.certificazione.CertificazioneBusinessImpl certificazioneBusinessImpl) {
		this.certificazioneBusinessImpl = certificazioneBusinessImpl;
	}

	@Autowired
	protected BeanUtil beanUtil;

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	protected FileApiServiceImpl fileApiServiceImpl;

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private ReportManager reportManager;

	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager reportManagerPbservizit;

	@Autowired
	private DocumentoManager documentoManager;

	@Autowired
	private it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerPbservizit;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	protected MailUtil mailUtil;

	@Autowired
	public CertificazioneDaoImpl(DataSource dataSource) throws Exception {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}

	public CertificazioneDaoImpl() {
	}

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////// METHODI PER CHECKLIST E DICHIARAZIONE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FINALE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public PropostaCertificazioneDTO[] findProposteCertificazione(Long idUtente, String identitaDigitale,
			List<String> statiProposta) throws Exception {
		String prf = "[CertificazioneDaoImpl :: findProposteCertificazione()]";
		LOG.info(prf + " - BEGIN");
		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT p.*, r.*,"+
//				   "s.DESC_BREVE_STATO_PROPOSTA_CERT, "+
//				   "s.DESC_STATO_PROPOSTA_CERTIF "+
//				   "FROM PBANDI_T_PROPOSTA_CERTIFICAZ  p,"+
//				   "PBANDI_D_STATO_PROPOSTA_CERTIF s, "+
//				   "PBANDI_R_PROPOSTA_CERTIF_LINEA r "+
//				   "WHERE p.ID_STATO_PROPOSTA_CERTIF = s.ID_STATO_PROPOSTA_CERTIF "+
//				   "AND   p.ID_PROPOSTA_CERTIFICAZ = r.ID_PROPOSTA_CERTIFICAZ"
//		);
		sql.append("SELECT p.*, r.*, " + "s.DESC_BREVE_STATO_PROPOSTA_CERT, " + "s.DESC_STATO_PROPOSTA_CERTIF, "
				+ "d.ID_DOCUMENTO_INDEX, d.NOME_DOCUMENTO " + "FROM PBANDI_T_PROPOSTA_CERTIFICAZ p "
				+ "INNER JOIN PBANDI_D_STATO_PROPOSTA_CERTIF s ON p.ID_STATO_PROPOSTA_CERTIF = s.ID_STATO_PROPOSTA_CERTIF "
				+ "INNER JOIN PBANDI_R_PROPOSTA_CERTIF_LINEA r ON p.ID_PROPOSTA_CERTIFICAZ = r.ID_PROPOSTA_CERTIFICAZ "
				+ "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX d ON d.ID_TARGET = p.ID_PROPOSTA_CERTIFICAZ AND d.ID_TIPO_DOCUMENTO_INDEX = 28 ");
		try {
			if (statiProposta != null && statiProposta.size() > 0) {
				StringBuilder sql2 = new StringBuilder();
				sql2.append(sql.toString());
				sql2.append(" and  s.desc_breve_stato_proposta_cert in (");
				for (String descBreve : statiProposta) {
					sql2.append("'" + descBreve + "',");
				}
				sql2.append(") order by p.ID_PROPOSTA_CERTIFICAZ");
				String query = sql2.toString().replace(",)", ")");
				LOG.info(prf + " :" + query);
				List<PropostaCertificazioneVO> lista = getJdbcTemplate().query(query,
						new ProposteCertificazioneRowMapper());
				LOG.debug(prf + " - END");
				return beanUtil.transform(lista, PropostaCertificazioneDTO.class);
			} else {
				LOG.info(prf + " :" + sql);
				List<PropostaCertificazioneVO> lista = getJdbcTemplate().query(sql.toString(),
						new ProposteCertificazioneRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transform(lista, PropostaCertificazioneDTO.class);
			}
		} catch (RecordNotFoundException e) {
			LOG.warn(prf + "Proposte non presente");
			throw new RecordNotFoundException(e.getMessage());
		} catch (Exception e) {
			LOG.warn(prf + "Proposta non presente");
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
	}

	@Override
	@Transactional
	public LineaDiInterventoDTO[] getLineeDiInterventoFromIdLinee(List<Long> idLineeIntervento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: getLineeDiInterventoFromIdLinee()]";
		LOG.info(prf + " - BEGIN");
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT * FROM PBANDI_D_LINEA_DI_INTERVENTO WHERE ID_LINEA_DI_INTERVENTO IN (");
			for (Long id : idLineeIntervento) {
				if (idLineeIntervento != null)
					sql.append(id + ",");
			}
			sql.append(")");
			String query = sql.toString().replace(",)", ")");
			LOG.info("\n" + query);
			List<PbandiDLineaDiInterventoVO> lineeIntervento = getJdbcTemplate().query(query,
					new LineaInterventoRowMapper());
			LOG.info(prf + " - END");
			return beanUtil.transform(lineeIntervento, LineaDiInterventoDTO.class);
		} catch (RecordNotFoundException e) {
			LOG.warn(prf + " Linea di intervento non presente");
			throw new RecordNotFoundException(e.getMessage());
		} catch (Exception e) {
			LOG.warn(prf + " Linea di intervento non presente");
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
	}

	@Override
	@Transactional
	public PropostaCertificazioneDTO findPropostaCertificazione(BigDecimal idProposta) throws Exception {
		String prf = "[CertificazioneDaoImpl :: findPropostaCertificazione()]";
		LOG.info(prf + " - BEGIN");
		StringBuilder queryProposta = new StringBuilder();
		queryProposta.append(
				"SELECT p.*, r.ID_LINEA_DI_INTERVENTO, s.DESC_BREVE_STATO_PROPOSTA_CERT, s.DESC_STATO_PROPOSTA_CERTIF "
						+ "FROM PBANDI_T_PROPOSTA_CERTIFICAZ p, PBANDI_R_PROPOSTA_CERTIF_LINEA r, PBANDI_D_STATO_PROPOSTA_CERTIF s "
						+ "WHERE p.ID_PROPOSTA_CERTIFICAZ=r.ID_PROPOSTA_CERTIFICAZ "
						+ "AND p.ID_STATO_PROPOSTA_CERTIF = s.ID_STATO_PROPOSTA_CERTIF "
						+ "AND p.ID_PROPOSTA_CERTIFICAZ=");
		queryProposta.append(idProposta.toString());
		LOG.debug(prf + " queryProposta: " + queryProposta.toString());
		try {
			PropostaCertificazioneVO proposta = getJdbcTemplate().queryForObject(queryProposta.toString(),
					new PropostaCertificazioneRowMapper());
			LOG.info(prf + " - END");
			return beanUtil.transform(proposta, PropostaCertificazioneDTO.class);
		} catch (RecordNotFoundException e) {
			LOG.error(prf + "Proposta non presente");
			throw new RecordNotFoundException(e.getMessage());
		} catch (Exception e) {
			LOG.error(prf + "Proposta non presente");
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
	}

	@Override
	@Transactional
	public DocumentoCertificazioneDTO[] findAllegatiPropostaCertificazione(BigDecimal idPropostaCertificazione,
			List<String> codiciTipoDocumento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: findAllegatiPropostaCertificazione()]";
		LOG.info(prf + " - BEGIN");
		StringBuilder sql = new StringBuilder();
		sql.append("select d.*, t.desc_breve_tipo_doc_index, t.desc_tipo_doc_index "
				+ "from PBANDI_T_DOCUMENTO_INDEX d, PBANDI_C_TIPO_DOCUMENTO_INDEX t "
				+ "where d.id_tipo_documento_index = t.id_tipo_documento_index and ID_TARGET =");
		sql.append(idPropostaCertificazione);
		try {
			if (codiciTipoDocumento != null && codiciTipoDocumento.size() > 0) {
				StringBuilder sql2 = new StringBuilder();
				sql2.append(sql.toString());
				sql2.append(" and  t.DESC_BREVE_TIPO_DOC_INDEX in (");
				for (String descBreve : codiciTipoDocumento) {
					sql2.append("'" + descBreve + "',");
				}
				sql2.append(")");
				String query = sql2.toString().replace(",)", ")");
				List<DocumentoIndexVO> documenti = getJdbcTemplate().query(query, new DocumentoIndexRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transform(documenti, DocumentoCertificazioneDTO.class);
			} else {
				List<DocumentoIndexVO> documenti = getJdbcTemplate().query(sql.toString(),
						new DocumentoIndexRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transform(documenti, DocumentoCertificazioneDTO.class);
			}
		} catch (RecordNotFoundException e) {
			LOG.warn(prf + "Proposta non presente");
			throw new RecordNotFoundException(e.getMessage());
		} catch (Exception e) {
			LOG.warn(prf + "Proposta non presente");
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni cancellaAllegati(List<String> idDocumentiSelezionati) throws Exception {
		String prf = "[CertificazioneDaoImpl :: cancellaAllegati()]";
		LOG.info(prf + " - BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		int countDocCancellati = 0;
		try {
			if (idDocumentiSelezionati.size() > 0) {
				for (String idDocumentoIndex : idDocumentiSelezionati) {
					// Ottienilo dal db
					StringBuilder querySelect = new StringBuilder();
					querySelect
							.append("SELECT r.*, s.* FROM PBANDI_T_DOCUMENTO_INDEX r, PBANDI_C_TIPO_DOCUMENTO_INDEX s "
									+ "WHERE r.ID_TIPO_DOCUMENTO_INDEX = s.ID_TIPO_DOCUMENTO_INDEX AND ID_DOCUMENTO_INDEX= ")
							.append(idDocumentoIndex);
					DocumentoIndexVO vo = getJdbcTemplate().queryForObject(querySelect.toString(),
							new DocumentoIndexRowMapper());
					// cancellalo da FS
					Boolean statoCancellaFS = cancellaSuFileSystem(vo.getNomeDocumento(),
							vo.getDescBreveTipoDocIndex());
					if (!statoCancellaFS) {
						esito.setEsito(false);
						esito.setMsg(ErrorMessages.ERROR_FILE_NON_CANCELLATO_DA_FS + " : idDocumentoIndex: "
								+ idDocumentoIndex);
						return esito;
					}

					// cancellalo dal db
					Boolean statoCancella = cancellaFile(vo);
					if (!statoCancella) {
						esito.setEsito(false);
						esito.setMsg(ErrorMessages.ERROR_FILE_NON_CANCELLATO_DA_INDEX + " : idDocumentoIndex: "
								+ idDocumentoIndex);
						return esito;
					}
					countDocCancellati++;
				}
			}
			if (countDocCancellati != idDocumentiSelezionati.size()) {
				esito.setEsito(Boolean.FALSE);
				esito.setMsg(MessageConstants.MSG_CANCELLAZIONE_TUTTI_DOCUMENTI_NON_ESEGUITA);
				esito.setParams(new String[] { String.valueOf(countDocCancellati),
						String.valueOf(idDocumentiSelezionati.size()) });
			} else {
				esito.setEsito(Boolean.TRUE);
				esito.setMsg(MessageConstants.MSG_CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
			}
			LOG.info(prf + " - END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " " + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
	}

	private Boolean cancellaSuFileSystem(String strFileName, String strTipoDocumento) {
		String prf = "[CertificazioneDaoImpl::cancellaSuFileSystem] ";
		LOG.info(prf + " BEGIN");
		Boolean esito = false;
		try {
			if (fileApiServiceImpl == null) {
				LOG.error(prf + "fileApiServiceImpl non istanziato.");
				return false;
			}
			esito = fileApiServiceImpl.deleteFile(strFileName, strTipoDocumento);
			LOG.info(prf + " - END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			return false;
		} finally {
			LOG.info(prf + " - END");
		}
	}

	private Boolean cancellaFile(DocumentoIndexVO vo) {
		String prf = "[CertificazioneDaoImpl::cancellaFile]";
		LOG.info(prf + " BEGIN");
		Boolean esito = false;
		try {
			if (vo.getIdDocumentoIndex() == null || vo.getIdDocumentoIndex().intValue() == 0) {
				LOG.error(prf + "idDocumentoIndex non valorizzato.");
				return false;
			}
			// Cancella da PBANDI_T_DOCUMENTO_INDEX.
			String sql1 = "DELETE FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_DOCUMENTO_INDEX = "
					+ vo.getIdDocumentoIndex().longValue();
			LOG.info("\n" + sql1);
			int numRecord = getJdbcTemplate().update(sql1);
			if (numRecord == 0) {
				LOG.error(prf + "Cancellazione su PBANDI_T_DOCUMENTO_INDEX fallita.");
				return false;
			} else
				esito = true;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			return false;
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoOperazioni allegaFileProposta(Long idUtente, Long idProposta, Long idProgetto, List<InputPart> file,
			String tipoDocumento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: allegaFileProposta()]";
		LOG.info(prf + " - BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();

		try {
			List<AllegatoVO> allegati = null;
			allegati = UploadUtil.getAllegatiFromInput(file);
			// PASSO1: salva su PBANDI_T_DOCUMENTO_INDEX
			// Id del nuovo record della PBANDI_T_DOCUMENTO_INDEX.
			BigDecimal idTDocumentoIndex = nuovoIdTDocumentoIndex();
			if (idTDocumentoIndex == null) {
				LOG.error(prf + "Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.");
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERROR_ID_DOC_INDEX_NON_GENERATO);
				return esito;
			}

			List<Boolean> uploadResult = new ArrayList<>();

			for (AllegatoVO allegato : allegati) {
				// Nome univoco con cui il file verrà salvato su File System.
				PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();
				// Completo alcuni campi.
				vo.setIdDocumentoIndex(idTDocumentoIndex);
				vo.setUuidNodo("UUID"); // Ci metto una stringa a caso, poichè su db il campo è obbligatorio.
				java.sql.Date oggi = DateUtil.getSysdate();
				vo.setDtInserimentoIndex(oggi);

				if (tipoDocumento == Constants.TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROGETTO) {
					BigDecimal idEntita = idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
							Constants.ENTITA_C_PROPOSTA_DETT);
					vo.setIdEntita(idEntita);
				} else {
					BigDecimal idEntita = idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
							Constants.ENTITA_C_PROPOSTA);
					vo.setIdEntita(idEntita);
				}

				vo.setIdTipoDocumentoIndex(idTipoIndexDaDescBreve(tipoDocumento));
				vo.setRepository(tipoDocumento); // il nome cartella sul nuovo storage
				String newName = allegato.getFileName();
				vo.setNomeFile(newName);
				vo.setNomeDocumento(newName.replaceFirst("\\.", "_" + idTDocumentoIndex.longValue() + "."));

				// salva su documento_index
				Boolean salvato = salvaAllegato(idUtente, idProposta, idProgetto, vo);
				if (!salvato) {
					LOG.error(prf + "Il file non salvato su PBANDI_T_DOCUMENTO_INDEX");
					esito.setEsito(false);
					esito.setMsg(ErrorMessages.ERROR_FILE_NON_SALVATO_SU_INDEX);
					return esito;
				}
				// PASSO2: Crea file su pbandi filestorage
				Boolean dirEsiste = fileApiServiceImpl.dirExists(tipoDocumento, true);
				if (dirEsiste) {
					LOG.info(prf + "dirEsiste: " + dirEsiste);
					InputStream inputStream = new ByteArrayInputStream(allegato.getContent());
					Boolean isUploaded = fileApiServiceImpl.uploadFile(inputStream, vo.getNomeDocumento(),
							tipoDocumento);
					uploadResult.add(isUploaded);
				} else {
					// Se la cartella non viene trovata, deve prima inserirla sotto \PBAN.
					FileApiServiceImpl fileApi = new FileApiServiceImpl(Constants.DIR_PBAN);
					InputStream inputStream = new ByteArrayInputStream(allegato.getContent());
					Boolean isUploaded = fileApi.uploadFile(inputStream, vo.getNomeDocumento(), tipoDocumento);
					uploadResult.add(isUploaded);
				}

			}

			if (uploadResult.contains(false)) {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERROR_UPLOAD_FILE);
			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_UPLOAD_FILE_ESEGUITA_CON_SUCCESSO);
			}
			LOG.info(prf + " - END");
			return esito;
		} catch (IncorrectUploadPathException e) {
			LOG.info(prf + " " + e.getMessage());
			throw new IncorrectUploadPathException(e.getMessage());
		} catch (Exception e) {
			LOG.info(prf + " " + e.getMessage());
			throw new Exception(e.getMessage());
		}

	}

	private Boolean salvaAllegato(Long idUtente, Long idProposta, Long idProgetto, PbandiTDocumentoIndexVO vo)
			throws Exception {
		String prf = "[ CertificazioneDaoImpl :: salvaAllegato ]";
		LOG.info(prf + " BEGIN");
		boolean esito = true;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_DOCUMENTO_INDEX ");
			sql.append(
					"(ID_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,DT_INSERIMENTO_INDEX,DT_MARCA_TEMPORALE,DT_VERIFICA_FIRMA,FLAG_FIRMA_CARTACEA,");
			sql.append(
					"ID_CATEG_ANAGRAFICA_MITT,ID_ENTITA,ID_MODELLO,ID_PROGETTO,ID_SOGG_DELEGATO,ID_SOGG_RAPPR_LEGALE,ID_STATO_DOCUMENTO,");
			sql.append(
					"ID_TARGET,ID_TIPO_DOCUMENTO_INDEX,ID_UTENTE_AGG,ID_UTENTE_INS,MESSAGE_DIGEST,NOME_FILE,NOTE_DOCUMENTO_INDEX,");
			sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO)");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			LOG.info("\n" + sql);

			Object[] params = new Object[25];
			params[0] = vo.getIdDocumentoIndex();
			params[1] = vo.getDtAggiornamentoIndex();
			params[2] = vo.getDtInserimentoIndex();
			params[3] = vo.getDtMarcaTemporale();
			params[4] = vo.getDtVerificaFirma();
			params[5] = vo.getFlagFirmaCartacea();
			params[6] = vo.getIdCategAnagraficaMitt();
			params[7] = vo.getIdEntita();
			params[8] = vo.getIdModello();
			params[9] = idProgetto;
			params[10] = vo.getIdSoggDelegato();
			params[11] = vo.getIdSoggRapprLegale();
			params[12] = vo.getIdStatoDocumento();
			params[13] = idProposta;
			params[14] = vo.getIdTipoDocumentoIndex();
			params[15] = vo.getIdUtenteAgg();
			params[16] = idUtente;
			params[17] = vo.getMessageDigest();
			params[18] = vo.getNomeFile();
			params[19] = vo.getNoteDocumentoIndex();
			params[20] = vo.getNumProtocollo();
			params[21] = vo.getRepository();
			params[22] = vo.getUuidNodo();
			params[23] = vo.getVersione();
			params[24] = vo.getNomeDocumento();

			getJdbcTemplate().update(sql.toString(), params);

			LOG.info(prf + "inserito record inPBANDI_T_DOCUMENTO_INDEX con id = " + vo.getIdDocumentoIndex());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new Exception(e.getMessage());
		}
		return esito;

	}

	@Override
	@Transactional
	public List<LineaDiInterventoDTO> findAttivitaLineaIntervento(Long idLineaIntervento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: findAttivitaLineaIntervento()]";
		LOG.info(prf + " - BEGIN");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO as idLineaDiIntervento  ,"
				+ "PBANDI_D_LINEA_DI_INTERVENTO.DESC_LINEA as descLinea ,"
				+ "SUBSTR(SYS_CONNECT_BY_PATH(PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA,'.'),2) as descBreveCompleta "
				+ " FROM  PBANDI_D_LINEA_DI_INTERVENTO "
				+ " WHERE nvl(trunc(PBANDI_D_LINEA_DI_INTERVENTO.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) ");

		if (idLineaIntervento != null)
			sql.append("START WITH ID_LINEA_DI_INTERVENTO = (SELECT t.ID_LINEA_DI_INTERVENTO "
					+ " FROM PBANDI_D_LINEA_DI_INTERVENTO t  WHERE t.ID_LINEA_DI_INTERVENTO=").append(idLineaIntervento)
					.append(")");
		else
			sql.append(" START WITH ID_LINEA_DI_INTERVENTO_PADRE is null ");

		sql.append("CONNECT by prior ID_LINEA_DI_INTERVENTO = ID_LINEA_DI_INTERVENTO_PADRE");

		try {
			List<LineaDiInterventoVO> lineeDiIntervento = getJdbcTemplate().query(sql.toString(),
					new AttivitaLineaInterventoRowMapper());
			LOG.info(prf + " - END");
			return beanUtil.transformList(lineeDiIntervento, LineaDiInterventoDTO.class);
		} catch (RecordNotFoundException e) {
			LOG.warn(prf + "Proposta non presente");
			throw new RecordNotFoundException(e.getMessage());
		} catch (Exception e) {
			LOG.debug(prf + " " + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
	}

	@Override
	@Transactional
	public List<ProgettoDTO> findAllProgetti(Long idLineaIntervento, Long idProposta, Long idBeneficiario) {
		String prf = "[CertificazioneDaoImpl :: findAllProgetti()]";
		LOG.info(prf + " - BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();
		try {
			/*
			 * Query per l' alberatura della linea di intervento
			 */
			StringBuilder sqlLineaIntervento = new StringBuilder(
					"SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento");
			StringBuilder tablesLineaIntervento = new StringBuilder("PBANDI_D_LINEA_DI_INTERVENTO");
			sqlLineaIntervento.append(" FROM ").append(tablesLineaIntervento);

			List<StringBuilder> conditionListLineaIntervento = new ArrayList<StringBuilder>();
			setWhereClause(conditionListLineaIntervento, sqlLineaIntervento, tablesLineaIntervento);

			sqlLineaIntervento.append(" START WITH ID_LINEA_DI_INTERVENTO = :idLineaIntervento");
			sqlLineaIntervento.append(" CONNECT BY prior id_linea_di_intervento = id_linea_di_intervento_padre");

			/*
			 * Select per la migrazione delle linee di intervento
			 */
			StringBuilder sqlSelectMigrazione = new StringBuilder(
					"SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata");
			StringBuilder tablesMigrazione = new StringBuilder("PBANDI_R_LINEA_INTERV_MAPPING");
			sqlSelectMigrazione.append(" FROM ").append(tablesMigrazione);
			List<StringBuilder> conditionListMigrazione = new ArrayList<StringBuilder>();
			conditionListMigrazione.add(new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_attuale IN (" + sqlLineaIntervento + ")"));
			setWhereClause(conditionListMigrazione, sqlSelectMigrazione, tablesMigrazione);

			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
					.append(" PBANDI_T_PROGETTO.codice_progetto as codiceProgetto,")
					.append(" PBANDI_T_PROGETTO.codice_visualizzato as codiceVisualizzato,")
					.append(" PBANDI_T_PROGETTO.titolo_progetto as titolo_progetto_attuale ");

			StringBuilder tables = new StringBuilder(" PBANDI_T_PROGETTO").append(",PBANDI_T_DETT_PROPOSTA_CERTIF");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(
					new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto "));

			if (idProposta != null) {
				conditionList
						.add(new StringBuilder(" PBANDI_T_DETT_PROPOSTA_CERTIF.id_proposta_certificaz = :idProposta"));
				params.addValue("idProposta", idProposta);
			}

			if (idLineaIntervento != null) {
				tables.append(",PBANDI_R_BANDO_LINEA_INTERVENT");
				tables.append(",PBANDI_T_DOMANDA");
				conditionList.add(new StringBuilder(
						" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
				conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
				conditionList.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ( "
						+ " (" + sqlLineaIntervento + " UNION " + sqlSelectMigrazione + ")" + ")"));
				params.addValue("idLineaIntervento", idLineaIntervento);
			}

			if (idBeneficiario != null) {
				tables.append(",PBANDI_R_SOGGETTO_PROGETTO");
				tables.append(",PBANDI_D_TIPO_ANAGRAFICA");
				tables.append(",PBANDI_D_TIPO_BENEFICIARIO");
				conditionList.add(new StringBuilder(
						" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
				conditionList.add(new StringBuilder(
						" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
				conditionList.add(
						new StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
				conditionList.add(new StringBuilder(
						" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
				conditionList.add(new StringBuilder(
						" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto"));
				conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idBeneficiario"));
				params.addValue("idBeneficiario", idBeneficiario);
			}

			sqlSelect.append(" FROM ").append(tables);
			setWhereClause(conditionList, sqlSelect, tables);

			/*
			 * ORDER BY
			 */
			sqlSelect.append(" ORDER BY PBANDI_T_PROGETTO.codice_visualizzato ");

			LOG.info(prf + " " + sqlSelect);

			List<PbandiTProgettoVO> progetti = namedJdbcTemplate.query(sqlSelect.toString(), params,
					new ProgettoRowMapper());
			LOG.info(prf + " - END");
			return beanUtil.transformList(progetti, ProgettoDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " " + e.getMessage());
			throw e;
		}

	}

	@Override
	@Transactional
	public List<BeneficiarioDTO> findAllBeneficiari(Long idProposta, Long idLineaIntervento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: findAllProgetti()]";
		LOG.info(prf + " - BEGIN");
		MapSqlParameterSource params = new MapSqlParameterSource();
		try {
			/*
			 * Query per l' alberatura della linea di intervento
			 */
			StringBuilder sqlLineaIntervento = new StringBuilder(
					"SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento");
			StringBuilder tablesLineaIntervento = new StringBuilder("PBANDI_D_LINEA_DI_INTERVENTO");
			sqlLineaIntervento.append(" FROM ").append(tablesLineaIntervento);
			List<StringBuilder> conditionListLineaIntervento = new ArrayList<StringBuilder>();
			setWhereClause(conditionListLineaIntervento, sqlLineaIntervento, tablesLineaIntervento);
			sqlLineaIntervento.append(" START WITH id_linea_di_intervento = :idLineaIntervento");
			sqlLineaIntervento.append(" CONNECT BY prior id_linea_di_intervento = id_linea_di_intervento_padre");
			/*
			 * Select persone fisiche
			 */
			StringBuilder sqlSelectPF = new StringBuilder(
					" SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome ");
			StringBuilder tablesPF = new StringBuilder("PBANDI_T_PERSONA_FISICA");
			sqlSelectPF.append(" FROM ").append(tablesPF);
			List<StringBuilder> conditionListPF = new ArrayList<StringBuilder>();
			conditionListPF.add(new StringBuilder(
					"PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica"));
			setWhereClause(conditionListPF, sqlSelectPF, tablesPF);

			/*
			 * Select ente giuridico
			 */
			StringBuilder sqlSelectEG = new StringBuilder(
					"SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico");
			StringBuilder tablesEG = new StringBuilder("PBANDI_T_ENTE_GIURIDICO");
			sqlSelectEG.append(" FROM ").append(tablesEG);
			List<StringBuilder> conditionListEG = new ArrayList<StringBuilder>();
			conditionListEG.add(new StringBuilder(
					"PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico"));
			setWhereClause(conditionListEG, sqlSelectEG, tablesEG);

			/*
			 * Select per la migrazione delle linee di intervento
			 */
			StringBuilder sqlSelectMigrazione = new StringBuilder(
					"SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata");
			StringBuilder tablesMigrazione = new StringBuilder("PBANDI_R_LINEA_INTERV_MAPPING");
			sqlSelectMigrazione.append(" FROM ").append(tablesMigrazione);
			List<StringBuilder> conditionListMigrazione = new ArrayList<StringBuilder>();
			conditionListMigrazione.add(new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_attuale IN (" + sqlLineaIntervento + ")"));
			setWhereClause(conditionListMigrazione, sqlSelectMigrazione, tablesMigrazione);

			/*
			 * Query complessiva
			 */
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto AS id_soggetto,").append(
							" CASE " + "  WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is not null " + "  THEN ("
									+ sqlSelectPF + ")" + "  ELSE (" + sqlSelectEG + ")" + " END AS descrizione, ")
							.append(" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO  AS codiceFiscale");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO").append(",PBANDI_D_TIPO_ANAGRAFICA")
					.append(",PBANDI_D_TIPO_BENEFICIARIO").append(",PBANDI_T_DETT_PROPOSTA_CERTIF")
					.append(",PBANDI_T_SOGGETTO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList
					.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			conditionList
					.add(new StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList.add(
					new StringBuilder(" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto"));

			if (idProposta != null) {
				conditionList
						.add(new StringBuilder(" PBANDI_T_DETT_PROPOSTA_CERTIF.id_proposta_certificaz = :idProposta"));
				params.addValue("idProposta", idProposta);
			}

			if (idLineaIntervento != null) {
				tables.append(",PBANDI_R_BANDO_LINEA_INTERVENT");
				tables.append(",PBANDI_T_DOMANDA");
				tables.append(",PBANDI_T_PROGETTO");
				conditionList.add(new StringBuilder(
						" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
				conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
				conditionList.add(
						new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
				conditionList.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ( "
						+ " (" + sqlLineaIntervento + " UNION " + sqlSelectMigrazione + ")" + ")"));
				params.addValue("idLineaIntervento", idLineaIntervento);
			}

			sqlSelect.append(" FROM ").append(tables);
			setWhereClause(conditionList, sqlSelect, tables);
			sqlSelect.append(" ORDER BY descrizione ");

			List<it.csi.pbandi.pbwebcert.integration.vo.BeneficiarioVO> beneficiari = namedJdbcTemplate
					.query(sqlSelect.toString(), params, new BeneficiariRowMapper());

			LOG.info(prf + " - END");
			return beanUtil.transformList(beneficiari, BeneficiarioDTO.class);
		} catch (Exception e) {
			LOG.debug(prf + " " + e.getMessage());
			throw new Exception(e.getMessage());
		}

	}

	@Override
	@Transactional
	public List<PropostaCertificazioneDTO> findProgettiProposta(Long idProgetto, Long idProposta,
			Long idLineaIntervento, Long idBeneficiario) {
		String prf = "[CertificazioneDaoImpl :: findProgettiProposta()]";
		LOG.info(prf + " - BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			/*
			 * Costruzione della subquery per le linee di intervento
			 */
			StringBuilder sqlSelectLinea = new StringBuilder(
					"SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento");
			StringBuilder tablesLinea = new StringBuilder("PBANDI_D_LINEA_DI_INTERVENTO");
			sqlSelectLinea.append(" FROM ").append(tablesLinea);
			sqlSelectLinea.append(" START WITH id_linea_di_intervento = :idLineaIntervento "
					+ " CONNECT BY PRIOR id_linea_di_intervento = id_linea_di_intervento_padre");

			/*
			 * Select per la migrazione delle linee di intervento
			 */
			StringBuilder sqlSelectMigrazione = new StringBuilder(
					"SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata");
			StringBuilder tablesMigrazione = new StringBuilder("PBANDI_R_LINEA_INTERV_MAPPING");
			sqlSelectMigrazione.append(" FROM ").append(tablesMigrazione);
			List<StringBuilder> conditionListMigrazione = new ArrayList<StringBuilder>();
			conditionListMigrazione.add(new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_attuale IN (" + sqlSelectLinea + ")"));
			setWhereClause(conditionListMigrazione, sqlSelectMigrazione, tablesMigrazione);

			/*
			 * Costruzione della query complessiva
			 */
			StringBuilder sqlSelect = new StringBuilder(
					" SELECT DISTINCT pc.ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz")
							.append(" ,pc.DT_ORA_CREAZIONE as dtOraCreazione")
							.append(" ,pc.DESC_PROPOSTA as descProposta")
							.append(" ,pc.DT_CUT_OFF_PAGAMENTI as dtCutOffPagamenti")
							.append(" ,pc.DT_CUT_OFF_VALIDAZIONI as dtCutOffValidazioni")
							.append(" ,pc.DT_CUT_OFF_EROGAZIONI as dtCutOffErogazioni")
							.append(" ,pc.DT_CUT_OFF_FIDEIUSSIONI as dtCutOffFideiussioni")
							.append(" ,pc.ID_STATO_PROPOSTA_CERTIF as idStatoPropostaCertif")
							.append(" ,p.codice_visualizzato as codiceVisualizzato")
							.append(" ,p.titolo_progetto as titoloProgetto")
							.append(" ,sc.desc_breve_stato_proposta_cert as descBreveStatoPropostaCert")
							.append(" ,sc.desc_stato_proposta_certif as descStatoPropostaCertif")
							.append(" ,dc.costo_ammesso as costoAmmesso")
							.append(" ,dc.id_dett_proposta_certif as idDettPropostaCertif")
							.append(" ,dc.id_progetto as idProgetto")
							.append(" ,dc.importo_eccendenze_validazione as importoEccendenzeValidazione")
							.append(" ,dc.importo_erogazioni as importoErogazioni")
							.append(" ,dc.importo_fideiussioni as importoFideiussioni")
							.append(" ,dc.importo_pagamenti_validati as importoPagamentiValidati")
							.append(" ,dc.spesa_certificabile_lorda as spesaCertificabileLorda")
							.append(" ,PBANDI_D_LINEA_DI_INTERVENTO.desc_linea as attivita")
							.append(" ,( SELECT substr(sys_connect_by_path(v.desc_breve_linea,'.'),2) "
									+ "    FROM PBANDI_D_LINEA_DI_INTERVENTO v "
									+ "    WHERE v.id_linea_di_intervento = PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento "
									+ "    START WITH v.id_linea_di_intervento_padre is null "
									+ "    CONNECT BY prior v.id_linea_di_intervento = v.id_linea_di_intervento_padre "
									+ "  ) as descBreveCompletaAttivita ")
							.append(" ,( SELECT SUM(PBANDI_R_DET_PROP_CER_SOGG_FIN.perc_tipo_sogg_finanziatore) "
									+ "    FROM PBANDI_R_DET_PROP_CER_SOGG_FIN, "
									+ "         PBANDI_D_TIPO_SOGG_FINANZIAT "
									+ "    WHERE PBANDI_R_DET_PROP_CER_SOGG_FIN.id_dett_proposta_certif = dc.id_dett_proposta_certif "
									+ "      AND PBANDI_R_DET_PROP_CER_SOGG_FIN.id_tipo_sogg_finanziat      = PBANDI_D_TIPO_SOGG_FINANZIAT.id_tipo_sogg_finanziat "
									+ "      AND PBANDI_D_TIPO_SOGG_FINANZIAT.desc_breve_tipo_sogg_finanziat LIKE 'CPUPOR%' "
									+ "   ) as percContributoPubblico ")
							.append(",( select distinct PBANDI_R_DET_PROP_CER_SOGG_FIN.perc_tipo_sogg_finanziatore "
									+ "   from PBANDI_R_DET_PROP_CER_SOGG_FIN,  "
									+ "        PBANDI_D_TIPO_SOGG_FINANZIAT "
									+ "  where PBANDI_R_DET_PROP_CER_SOGG_FIN.id_dett_proposta_certif = dc.id_dett_proposta_certif "
									+ "    AND PBANDI_R_DET_PROP_CER_SOGG_FIN.id_tipo_sogg_finanziat      = PBANDI_D_TIPO_SOGG_FINANZIAT.id_tipo_sogg_finanziat "
									+ "    AND PBANDI_D_TIPO_SOGG_FINANZIAT.desc_breve_tipo_sogg_finanziat = 'COFPOR' "
									+ "  ) as percCofinFesr ")
							.append(", CASE " + "    WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is null "
									+ "    THEN (  "
									+ "           SELECT DISTINCT eg.denominazione_ente_giuridico || '  ' || sg.codice_fiscale_soggetto "
									+ "           FROM PBANDI_T_ENTE_GIURIDICO eg, "
									+ "                PBANDI_T_SOGGETTO sg "
									+ "           WHERE eg.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico "
									+ "             AND sg.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto "
									+ "         ) " + "    ELSE (  "
									+ "          SELECT DISTINCT pf.cognome || ' ' || pf.nome || '  ' || sg.codice_fiscale_soggetto "
									+ "          FROM PBANDI_T_PERSONA_FISICA pf, "
									+ "               PBANDI_T_SOGGETTO sg "
									+ "          WHERE pf.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica "
									+ "            AND sg.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto "
									+ "         ) " + " END as beneficiario");

			StringBuilder tables = new StringBuilder("PBANDI_T_DETT_PROPOSTA_CERTIF dc").append(",PBANDI_T_PROGETTO p")
					.append(",PBANDI_T_PROPOSTA_CERTIFICAZ pc").append(",PBANDI_D_STATO_PROPOSTA_CERTIF sc")
					.append(",PBANDI_T_DOMANDA").append(",PBANDI_R_BANDO_LINEA_INTERVENT")
					.append(",PBANDI_D_LINEA_DI_INTERVENTO").append(",PBANDI_R_SOGGETTO_PROGETTO")
					.append(",PBANDI_D_TIPO_ANAGRAFICA").append(",PBANDI_D_TIPO_BENEFICIARIO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("dc.id_progetto = p.id_progetto"));
			conditionList.add(new StringBuilder("pc.id_proposta_certificaz = dc.id_proposta_certificaz"));
			conditionList.add(new StringBuilder("pc.id_stato_proposta_certif = sc.id_stato_proposta_certif"));
			conditionList.add(new StringBuilder("pc.id_proposta_certificaz = :idPropostaCertificazione"));
			conditionList.add(new StringBuilder("p.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			conditionList.add(new StringBuilder(
					"PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO = PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList
					.add(new StringBuilder("PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList.add(
					new StringBuilder("PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
			conditionList.add(new StringBuilder("p.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));

			if (idProgetto != null) {
				conditionList.add(new StringBuilder(" p.id_progetto = :idProgetto"));
				params.addValue("idProgetto", idProgetto);
			}
			if (idLineaIntervento != null) {
				conditionList.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ( "
						+ " (" + sqlSelectLinea + " UNION " + sqlSelectMigrazione + ")" + ")"));
				params.addValue("idLineaIntervento", idLineaIntervento);
			}
			if (idBeneficiario != null) {
				conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idBeneficiario"));
				conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = p.id_progetto"));
				params.addValue("idBeneficiario", idBeneficiario);
			}
			sqlSelect.append(" FROM ").append(tables);
			params.addValue("idPropostaCertificazione", idProposta);
			setWhereClause(conditionList, sqlSelect, tables);

			List<it.csi.pbandi.pbwebcert.integration.vo.DettPropostaCertifVO> proposte = namedJdbcTemplate
					.query(sqlSelect.toString(), params, new DettPropostaCertifRowMapper());

			LOG.info(prf + " - END");
			return beanUtil.transformList(proposte, PropostaCertificazioneDTO.class);
		} catch (Exception e) {
			LOG.debug(prf + " " + e.getMessage());
			throw e;
		}

	}

	@Override
	@Transactional
	public PropostaCertificazioneDTO findDettaglioProposta(Long idProposta) throws Exception {
		String prf = "[CertificazioneDaoImpl :: findDettaglioProposta()]";
		LOG.info(prf + " - BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("DettaglioProposta.sql");
			Object[] param = new Object[] { idProposta };

			DettPropostaCertifVO vo = getJdbcTemplate().queryForObject(sql.toString(), param,
					new DettPropostaCertifRowMapper());
			LOG.info(prf + " - END");
			return beanUtil.transform(vo, PropostaCertificazioneDTO.class);
		} catch (RecordNotFoundException e) {
			LOG.warn(prf + "Proposta non presente");
			throw new RecordNotFoundException(e.getMessage());
		} catch (Exception e) {
			LOG.debug(prf + " " + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////// METHODI PER GESTIONE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// PROPOSTE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public EsitoOperazioni creaIntermediaFinale(Long idUtente)
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: creaIntermediaFinale()]";
		LOG.info(prf + " - BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME)
					.withProcedureName("fnc_CreaPropCertificazFoto").withCatalogName("PCK_PBANDI_CERTIFICAZIONE")
					.withoutProcedureColumnMetaDataAccess();

			jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
			jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_UTENTE, Types.NUMERIC));
			jdbcCall.setFunction(true);
			jdbcCall.compile();

			// SqlParameterSource in = new MapSqlParameterSource().addValue(P_ID_UTENTE,
			// BigDecimal.valueOf(idUtente));
			Map<String, Object> input = new HashMap<>();
			input.put(Constants.P_ID_UTENTE, BigDecimal.valueOf(idUtente));
			Map<String, Object> out = jdbcCall.execute(input);

			int procedureResult = -1;
			if (out.containsKey("result")) {
				procedureResult = ((BigDecimal) out.get("result")).intValue();
			}

			LOG.info(prf + " procedureResult= " + procedureResult);
			switch (procedureResult) {
			case 0:
				esito.setEsito(Boolean.TRUE);
				esito.setMsg(MessageConstants.MSG_OPERAZIONE_ESEGUITA_CON_SUCCESSO);
				break;

			// 2: 'esiste già una certificazione intermedia finale per il periodo contabile
			// in corso'
			case 2:
				esito.setEsito(Boolean.FALSE);
				esito.setMsg(ErrorMessages.ERROR_FOTOGRAFIA_ESISTE);
				break;

			// 3: 'non esiste una certificazione approvata per il periodo contabile in
			// corso'.
			case 3:
				esito.setEsito(Boolean.FALSE);
				esito.setMsg(ErrorMessages.ERROR_FOTOGRAFIA_NESSUN_APPROVATA_TROVATA);
				break;

			// 1: errore generico
			default:
				esito.setEsito(Boolean.FALSE);
				esito.setMsg(ErrorMessages.ERRORE_GENERICO);
				break;
			}
		} catch (Exception ex) {
			LOG.error("Errore durante nella creazione intermedia finale: " + ex);
			throw new CertificazioneException("Errore durante nella creazione intermedia finale: " + ex);
		}
		LOG.info(prf + " - END");
		return esito;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Integer aggiornaStatoProposta(Long idUtente, Long idProposta, Long idStatoNuovo)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::aggiornaStatoProposta]";
		LOG.info(prf + " BEGIN");
		Integer esito = null;
		try {
			// aggiorna lo stato su db
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PBANDI_T_PROPOSTA_CERTIFICAZ set ID_STATO_PROPOSTA_CERTIF = ").append(idStatoNuovo)
					.append(" ,ID_UTENTE_AGG = ").append(idUtente).append(" WHERE ID_PROPOSTA_CERTIFICAZ = ")
					.append(idProposta);
			getJdbcTemplate().update(sql.toString());

			LOG.info(prf + " aggiornato stato su db");

			try {
				// controlla se il nuovo stato é 07appr
				StringBuilder sql1 = new StringBuilder();
				sql1.append(
						"SELECT DESC_BREVE_STATO_PROPOSTA_CERT FROM PBANDI_D_STATO_PROPOSTA_CERTIF WHERE ID_STATO_PROPOSTA_CERTIF = ")
						.append(idStatoNuovo);
				String descBreveStato = getJdbcTemplate().queryForObject(sql1.toString(), String.class);
				LOG.info(prf + "Nuovo stato descBreve: " + descBreveStato);
				if (descBreveStato.equals(Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA)) {
					// Esegui la procedura PCK_PBANDI_NOTIFICHE.FNC_CREA_NOTIF_ANTICIPI
					esito = eseguiProceduraCreaNotifAnticipi(idProposta);
					LOG.info(prf + " aggiornaStato(): esito procedura 1(FNC_CREA_NOTIF_ANTICIPI) = " + esito); // Può
																												// essere
																												// 0, -1
																												// o -2.

					if (esito.intValue() == 0) {
						// Chiama la seconda procedura -che manda la mail
						Integer esitoMail = eseguiProceduraCreaNotifAnticipiMail();
						LOG.info(prf + " aggiornaStato(): esito procedura 2(FNC_CREA_NOTIF_ANTICIPI_MAIL) = "
								+ esitoMail); // Può essere 0, -1 o -2.
						if (esitoMail == null || esitoMail.intValue() == -1) {
							esito = -3;
						} else if (esitoMail.intValue() == 0) {
							// In output restituisco 0.
							esito = 0;
						}
					}

				}
			} catch (Exception e) {
				LOG.error(prf + " aggiornaStato(): ERRORE nella gestione degli anticipi: " + e);
			}
			LOG.info(prf + " END");
			return esito;

		} catch (Exception e) {
			CertificazioneException ce = new CertificazioneException(
					"Errore durante l'esecuzione delle procedure FNC_CREA_NOTIF_ANTICIPI o FNC_CREA_NOTIF_ANTICIPI_MAIL"
							+ e.getMessage());
			ce.initCause(e);
			throw ce;
		}

	}

	@Override
	@Transactional
	public List<StatoPropostaDTO> getStatiSelezionabili() throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: getStatiSelezionabili]";
		LOG.info(prf + " - BEGIN");
		try {
			// Ottieni idTipoStatoPropCert da PBANDI_D_TIPO_STATO_PROP_CERT
			StringBuilder queryTipoStato = new StringBuilder();
			queryTipoStato.append(
					"SELECT ID_TIPO_STATO_PROP_CERT FROM PBANDI_D_TIPO_STATO_PROP_CERT WHERE DESC_BREVE_TIPO_STATO_PRO_CERT = ")
					.append("'").append(Constants.TIPO_STATO_PROPOSTA_CERTIFICAZIONE_ADC).append("'");
			BigDecimal idTipoStato = getJdbcTemplate().queryForObject(queryTipoStato.toString(), BigDecimal.class);

			String sql = fileSqlUtil.getQuery("StatiPropostaSelezionabili.sql");
			Object[] param = new Object[] { idTipoStato };

			List<PbandiDStatoPropostaCertifVO> stati = getJdbcTemplate().query(sql, param,
					new TipoStatoPropostaCertifRowMapper());

			return beanUtil.transformList(stati, StatoPropostaDTO.class);
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<ProgettoNuovaCertificazioneDTO> getGestisciProposta(Long idProposta, String nomeBandoLinea,
			String codiceProgetto, String denominazioneBeneficiario, boolean progettiModificati)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: getGestisciProposta]";
		LOG.info(prf + " - BEGIN");
		try {
			if (progettiModificati) {
				List<ProgettoNuovaCertificazioneDTO> progetti = refreshGestisciProgetti(idProposta, nomeBandoLinea,
						codiceProgetto, denominazioneBeneficiario);
				return progetti;
			} else {
				StringBuilder sql1 = new StringBuilder();
				sql1.append("SELECT * FROM PBANDI_T_DETT_PROPOSTA_CERTIF WHERE ID_PROPOSTA_CERTIFICAZ = ")
						.append(idProposta);
				Object[] params = new Object[] { idProposta };
				if (StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
						&& StringUtil.isEmpty(denominazioneBeneficiario)) {
					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
						&& StringUtil.isEmpty(denominazioneBeneficiario)) {
					if (nomeBandoLinea.contains("'")) {
						nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
					}
					sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'");

					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
						&& StringUtil.isEmpty(denominazioneBeneficiario)) {
					if (codiceProgetto.contains("'")) {
						codiceProgetto = codiceProgetto.replaceAll("'", "''");
					}
					sql1.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'");

					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				} else if (StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
						&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
					if (denominazioneBeneficiario.contains("'")) {
						denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
					}
					sql1.append(" AND DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
							.append("'");

					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				}

				else if (!StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
						&& StringUtil.isEmpty(denominazioneBeneficiario)) {
					if (nomeBandoLinea.contains("'")) {
						nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
					}
					if (codiceProgetto.contains("'")) {
						codiceProgetto = codiceProgetto.replaceAll("'", "''");
					}
					sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
							.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'");

					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
						&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
					if (nomeBandoLinea.contains("'")) {
						nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
					}
					if (denominazioneBeneficiario.contains("'")) {
						denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
					}
					sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
							.append(" AND  DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
							.append("'");

					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
						&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
					if (codiceProgetto.contains("'")) {
						codiceProgetto = codiceProgetto.replaceAll("'", "''");
					}
					if (denominazioneBeneficiario.contains("'")) {
						denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
					}
					sql1.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'")
							.append(" AND  DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
							.append("'");

					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());
					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				} else {
					if (nomeBandoLinea.contains("'")) {
						nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
					}
					if (codiceProgetto.contains("'")) {
						codiceProgetto = codiceProgetto.replaceAll("'", "''");
					}
					if (denominazioneBeneficiario.contains("'")) {
						denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
					}

					sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
							.append(" AND  CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'")
							.append(" AND  DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
							.append("'");
					List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
							new ProgettoNuovaCertificazioneRowMapper());

					LOG.info(prf + " - END");
					return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
				}
			}

		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}

	}

	private List<ProgettoNuovaCertificazioneDTO> refreshGestisciProgetti(Long idProposta, String nomeBandoLinea,
			String codiceProgetto, String denominazioneBeneficiario) {
		String prf = "[CertificazioneDaoImpl::refreshGestisciProgetti]";
		StringBuilder sql1 = new StringBuilder();
		sql1.append("SELECT * FROM PBANDI_T_DETT_PROPOSTA_CERTIF WHERE ID_PROPOSTA_CERTIFICAZ = ").append(idProposta);
		Object[] params = new Object[] { idProposta };
		if (StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
				&& StringUtil.isEmpty(denominazioneBeneficiario)) {
			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			LOG.info(prf + " - END");
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
				&& StringUtil.isEmpty(denominazioneBeneficiario)) {
			if (nomeBandoLinea.contains("'")) {
				nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
			}
			sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'");

			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
				&& StringUtil.isEmpty(denominazioneBeneficiario)) {
			if (codiceProgetto.contains("'")) {
				codiceProgetto = codiceProgetto.replaceAll("'", "''");
			}
			sql1.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'");

			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		} else if (StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
				&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
			if (denominazioneBeneficiario.contains("'")) {
				denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
			}
			sql1.append(" AND DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario).append("'");

			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		}

		else if (!StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
				&& StringUtil.isEmpty(denominazioneBeneficiario)) {
			if (nomeBandoLinea.contains("'")) {
				nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
			}
			if (codiceProgetto.contains("'")) {
				codiceProgetto = codiceProgetto.replaceAll("'", "''");
			}
			sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
					.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'");

			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
				&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
			if (nomeBandoLinea.contains("'")) {
				nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
			}
			if (denominazioneBeneficiario.contains("'")) {
				denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
			}
			sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
					.append(" AND  DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
					.append("'");

			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			LOG.info(prf + " - END");
			return beanUtil.transformList(vo, ProgettoNuovaCertificazioneDTO.class);
		} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
				&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
			if (codiceProgetto.contains("'")) {
				codiceProgetto = codiceProgetto.replaceAll("'", "''");
			}
			if (denominazioneBeneficiario.contains("'")) {
				denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
			}
			sql1.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'")
					.append(" AND  DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
					.append("'");

			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		} else {
			if (nomeBandoLinea.contains("'")) {
				nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
			}
			if (codiceProgetto.contains("'")) {
				codiceProgetto = codiceProgetto.replaceAll("'", "''");
			}
			if (denominazioneBeneficiario.contains("'")) {
				denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
			}

			sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
					.append(" AND  CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'")
					.append(" AND  DENOMINAZIONE_BENEFICIARIO = ").append("'").append(denominazioneBeneficiario)
					.append("'");
			List<ProgettoNuovaCertificazioneVO> vo = getJdbcTemplate().query(sql1.toString(),
					new ProgettoNuovaCertificazioneRowMapper());
			ArrayList<ProgettoNuovaCertificazioneVO> progettiModificati = new ArrayList<ProgettoNuovaCertificazioneVO>();
			for (ProgettoNuovaCertificazioneVO progetto : vo) {
				if (progetto.getImportoCertificazioneNetto().compareTo(progetto.getImpCertifNettoPremodifica()) != 0) {
					progettiModificati.add(progetto);
				}
			}
			return beanUtil.transformList(progettiModificati, ProgettoNuovaCertificazioneDTO.class);
		}

	}

	@Override
	@Transactional
	public List<BandoLineaDTO> getBandoLinea(Long idProposta) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: getBandoLinea]";
		LOG.info(prf + " - BEGIN");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT p.*, s.* FROM PBANDI_T_DETT_PROPOSTA_CERTIF p , PBANDI_R_BANDO_LINEA_INTERVENT s WHERE p.ID_PROPOSTA_CERTIFICAZ = ")
					.append(idProposta).append(" AND p.NOME_BANDO_LINEA = s.NOME_BANDO_LINEA")
					.append(" ORDER BY p.NOME_BANDO_LINEA ASC");
			LOG.info(prf + "\n" + sql);

			List<BandoLineaVO> vo = getJdbcTemplate().query(sql.toString(), new BandoLineaRowMapper());

			// Filtra il risultato per elimare righe ripetute
			Map<String, BandoLineaVO> voMap = new HashMap<>();
			for (BandoLineaVO v : vo) {
				voMap.put(v.getNomeBandoLinea(), v);
			}
			List<BandoLineaVO> voFiltrato = new ArrayList<>();
			voFiltrato.addAll(voMap.values());
			LOG.info(prf + " - END");
			return beanUtil.transformList(voFiltrato, BandoLineaDTO.class);
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}

	}

	@Override
	@Transactional
	public List<BeneficiarioDTO> findAllBeneficiariDaProposta(Long idProposta, String nomeBandoLinea)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findAllBeneficiariDaProposta]";
		LOG.info(prf + " - BEGIN");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT * from PBANDI_T_DETT_PROPOSTA_CERTIF p, PBANDI_R_SOGGETTO_PROGETTO s where p.ID_PROPOSTA_CERTIFICAZ = ")
					.append(idProposta).append(" and p.NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea)
					.append("'").append("and p.id_progetto = s.id_progetto")
					.append(" ORDER BY p.DENOMINAZIONE_BENEFICIARIO ASC");
			List<BeneficiarioVO> vo = getJdbcTemplate().query(sql.toString(), new DenominazioneBeneficiarioRowMapper());

			// filtro vo
			Map<String, BeneficiarioVO> voMap = new HashMap<>();
			for (BeneficiarioVO v : vo) {
				voMap.put(v.getDenominazioneBeneficiario(), v);
			}
			List<BeneficiarioVO> voFiltrato = new ArrayList<>();
			voFiltrato.addAll(voMap.values());

			LOG.info(prf + " - END");
			return beanUtil.transformList(voFiltrato, BeneficiarioDTO.class);
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<ProgettoDTO> findAllProgettiDaProposta(Long idProposta, String nomeBandoLinea,
			String denominazioneBeneficiario) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findAllProgettiDaProposta]";
		LOG.info(prf + " - BEGIN");
		try {
			LOG.info(prf + " BEFORE denominazioneBeneficiario = " + denominazioneBeneficiario);
			if (denominazioneBeneficiario.contains("'")) {
				denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
			}
			LOG.info(prf + " AFTER denominazioneBeneficiario = " + denominazioneBeneficiario);
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT DISTINCT ID_PROGETTO as idProgetto , CODICE_PROGETTO as codiceProgetto from PBANDI_T_DETT_PROPOSTA_CERTIF WHERE ID_PROPOSTA_CERTIFICAZ = ")
					.append(idProposta).append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea)
					.append("'").append(" AND DENOMINAZIONE_BENEFICIARIO = ").append("'")
					.append(denominazioneBeneficiario).append("'").append(" ORDER BY DENOMINAZIONE_BENEFICIARIO ASC");
			List<PbandiTProgettoVO> vo = getJdbcTemplate().query(sql.toString(), new ProgettiPropostaRowMapper());

			LOG.info(prf + " - END");
			return beanUtil.transformList(vo, ProgettoDTO.class);
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni aggiornaImportoNetto(Long idUtente, Long idProposta, Long idDettProposta,
			Double nuovoImportoNetto, String nota, Long idProgetto) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: aggiornaImportoNetto]";
		LOG.info(prf + " - BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			// Aggiorna la tabella PBANDI_T_DETT_PROPOSTA_CERTIF
			StringBuilder sql = new StringBuilder();
			LOG.info(prf + " NOTA: " + nota);
			sql.append("UPDATE PBANDI_T_DETT_PROPOSTA_CERTIF set NOTA = ").append("'").append(nota).append("'")
					.append(", ID_UTENTE_AGG = ").append(idUtente).append(", IMPORTO_CERTIFICAZIONE_NETTO = ")
					.append(nuovoImportoNetto).append(" WHERE ID_DETT_PROPOSTA_CERTIF = ").append(idDettProposta);
			int res = getJdbcTemplate().update(sql.toString());
			if (res > 0) {
				// Chiama la procedura update_anticipi
				eseguiProceduraUpdateAnticipi(idProposta, idProgetto);
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_AGGIORNAMENTO_IMPORTO_SUCCESSO);
			} else {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERROR_AGGIORNA_IMPORTO_NETTO_NON_ESEGUITO);
			}
			LOG.info(prf + " - END");
			return esito;
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	private void eseguiProceduraUpdateAnticipi(Long idProposta, Long idProgetto) {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraUpdateAnticipi]";
		LOG.info(prf + " START");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME)
				.withProcedureName("update_anticipi").withCatalogName("PCK_PBANDI_CERTIFICAZIONE")
				.withoutProcedureColumnMetaDataAccess();
		jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, Types.NUMERIC));
		jdbcCall.declareParameters(new SqlParameter(Constants.ID_PROGETTO, Types.NUMERIC));

		// jdbcCall.setFunction(true);

		jdbcCall.compile();

		Map<String, Object> in = new HashMap<String, Object>();
		in.put(Constants.ID_PROGETTO, BigDecimal.valueOf(idProgetto));
		in.put(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, BigDecimal.valueOf(idProposta));

		jdbcCall.execute(in);

		LOG.info(prf + " END");
	}

	@Override
	@Transactional
	public EsitoOperazioni inviaReportPostGestione(Long idUtente, PropostaCertificazioneDTO dto,
			Long[] idLineeDiIntervento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: inviaReportPostGestione]";
		LOG.info(prf + " BEGIN");

		// String sql = fileSqlUtil.getQuery("Proposta.sql");
		// Object[] params = new Object[] {dto.getIdPropostaCertificaz()};
		// PropostaCertificazioneVO propostaVO = getJdbcTemplate().queryForObject(sql,
		// params, new PropostaCertificazioneRowMapper());

		// BigDecimal idProposta =
		// beanUtil.transform(propostaVO.getIdPropostaCertificaz(), BigDecimal.class);

		String idIride = "idIrideFinto";

		it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO propostaCertificazioneSrv;
		propostaCertificazioneSrv = beanUtil.transform(dto,
				it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO.class);

		certificazioneBusinessImpl.inviaReportPostGestione(idUtente, idIride, propostaCertificazioneSrv,
				idLineeDiIntervento);

		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(true);
		esito.setMsg("Il report \u00E8 stato inviato con successo.");

		return esito;
	}

	// Servizio fatto ex-novo da Fassil; sostituito con un altro servizo
	// che richiama il codice di pbandisrv
	// certificazioneBusinessImpl.inviaReportPostGestione().
	// @Override
	// @Transactional
	public EsitoOperazioni inviaReportPostGestioneOLD(Long idUtente, PropostaCertificazioneDTO dto,
			Long[] idLineeDiIntervento) throws Exception {
		String prf = "[CertificazioneDaoImpl :: inviaReportPostGestione]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		String sql = fileSqlUtil.getQuery("Proposta.sql");
		Object[] params = new Object[] { dto.getIdPropostaCertificaz() };
		PropostaCertificazioneVO propostaVO = getJdbcTemplate().queryForObject(sql, params,
				new PropostaCertificazioneRowMapper());
		BigDecimal idProposta = beanUtil.transform(propostaVO.getIdPropostaCertificaz(), BigDecimal.class);
		MailVO mailVO = new MailVO();
		mailVO.setSubject("Esito gestione proposta certificazione num. " + propostaVO.getIdPropostaCertificaz()
				+ (isBozza(dto) ? " (bozza)" : ""));
		mailVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
		mailVO.setToAddresses(caricaDestinatariEmailNotifica(dto.getIdPropostaCertificaz(), idLineeDiIntervento));

		try {
			creaESalvaReportPropostaCertificazione(idUtente, dto, propostaVO, mailVO, idLineeDiIntervento, true);

			addProgettiModificatiToMailContent(mailVO, idProposta);
			LOG.info(prf + "\n\n\nbefore mailUtil.send\n\n");
			LOG.info(prf + "----- MAIL CONTENT ------ \n " + mailVO.getContent());
			mailUtil.send(mailVO);
			LOG.info(prf + "\n\n\nafter mailUtil.send\n\n");
			esito.setEsito(true);
			esito.setMsg(MessageConstants.MSG_INVIA_REPORT_SUCCESSO);
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw e;
		}

	}

	private void addProgettiModificatiToMailContent(MailVO mailVO, BigDecimal idProposta) throws Exception {
		List<PbandiTDettPropostaCertifVO> dettProgettiPropostaModificati = new ArrayList<PbandiTDettPropostaCertifVO>();

		try {
			for (PbandiTDettPropostaCertifVO dettProgetto : getDettaglioProgettiProposta(idProposta)) {
				if (dettProgetto.getImportoCertificazioneNetto()
						.compareTo(dettProgetto.getImpCertifNettoPremodifica()) != 0) {
					dettProgettiPropostaModificati.add(dettProgetto);
				}
			}

			if (!dettProgettiPropostaModificati.isEmpty()) {
				mailVO.setContent(mailVO.getContent()
						+ TableWriter.writeHtmlTableToString("progettiModificati", dettProgettiPropostaModificati));
			}
		} catch (CertificazioneException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private List<PbandiTDettPropostaCertifVO> getDettaglioProgettiProposta(BigDecimal idProposta)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: getDettaglioProgettiProposta()]";
		LOG.info(prf + " - BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("DettPropostaCertif.sql");
			Object[] params = new Object[] { idProposta };
			List<PbandiTDettPropostaCertifVO> result = getJdbcTemplate().query(sql.toString(), params,
					new PbandiTDettPropCertifRowMapper());
			LOG.info(prf + " - END");
			return result;
		} catch (Exception e) {
			String msg = "Errore nell'ottenimento progetti da gestire :" + e.getMessage();
			LOG.error(msg, e);
			throw new CertificazioneException(msg, e);
		}
	}

	//////////////////////////////////////////////////////////
	//////////////////// INTERMEDIA FINALE////////////////////
	///////////////////////////////////////////////////////////
	@Override
	@Transactional
	public List<BandoLineaDTO> findBandoLineaIntermediaFinale(Long idProposta) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findBandoLineaIntermediaFinale]";
		LOG.info(prf + " - BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("BandoLineaIntermediaFinale.sql");
			Object[] params = new Object[] { idProposta };

			List<BandoLineaVO> vo = getJdbcTemplate().query(sql, params, new BandoLineaRowMapper());

			LOG.info(prf + " - END");
			return beanUtil.transformList(vo, BandoLineaDTO.class);
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<BeneficiarioDTO> findBeneficiariIntermediaFinale(Long idProposta, String nomeBandoLinea)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findBeneficiariIntermediaFinale]";
		LOG.info(prf + " - BEGIN");
		try {
			LOG.info(prf + " BEFORE nomeBandoLinea = " + nomeBandoLinea);

			String sql = fileSqlUtil.getQuery("BeneficiariIntermediaFinale.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);
			if (StringUtil.isEmpty(nomeBandoLinea)) {
				sql1.append("ORDER BY BENEFICIARIO ASC");
				Object[] params = new Object[] { idProposta };
				List<BeneficiarioIntFinale> vo = getJdbcTemplate().query(sql1.toString(), params,
						new BeneficiarioIntFinaleRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, BeneficiarioDTO.class);
			} else {
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				sql1.append(" AND NOME_BANDO_LINEA = '").append(nomeBandoLinea).append("'")
						.append(" ORDER BY BENEFICIARIO ASC");
				Object[] params = new Object[] { idProposta };
				List<BeneficiarioIntFinale> vo = getJdbcTemplate().query(sql1.toString(), params,
						new BeneficiarioIntFinaleRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, BeneficiarioDTO.class);
			}

		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<ProgettoDTO> findProgettiIntermediaFinale(Long idProposta, String nomeBandoLinea,
			String denominazioneBeneficiario) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findProgettiIntermediaFinale]";
		LOG.info(prf + " - BEGIN");
		try {

			String sql = fileSqlUtil.getQuery("ProgettiIntermediaFinale.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);
			if (!StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(denominazioneBeneficiario)) {
				// CASO 1 - Entrambi nomeBandoLinea e denominazioneBeneficiario non sono null
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				if (denominazioneBeneficiario.contains("'")) {
					denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
				}
				LOG.info(prf + " CASO 1 - Entrambi nomeBandoLinea e denominazioneBeneficiario non sono null");

				sql1.append(" AND NOME_BANDO_LINEA = '").append(nomeBandoLinea).append("'")
						.append("AND BENEFICIARIO = '").append(denominazioneBeneficiario).append("'")
						.append(" ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTProgettoVO> vo = getJdbcTemplate().query(sql1.toString(), params, new ProgettoRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);

			} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(denominazioneBeneficiario)) {
				// CASO 2 - nomeBandoLinea no è null
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				LOG.info(prf + " CASO 2 - nomeBandoLinea no è null");

				sql1.append(" AND NOME_BANDO_LINEA = '").append(nomeBandoLinea).append("'")
						.append("  ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTProgettoVO> vo = getJdbcTemplate().query(sql1.toString(), params, new ProgettoRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);
			} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(denominazioneBeneficiario)) {
				// CASO 3 - denominazioneBeneficiario no è null
				if (denominazioneBeneficiario.contains("'")) {
					denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
				}
				LOG.info(prf + " CASO 3 - denominazioneBeneficiario no è null");

				sql1.append(" AND BENEFICIARIO = '").append(denominazioneBeneficiario).append("'")
						.append("  ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTProgettoVO> vo = getJdbcTemplate().query(sql1.toString(), params, new ProgettoRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);
			} else {
				// CASO 4 -Restituisce tutti progetti
				LOG.info(prf + "CASO 4 -Restituisce tutti progetti");

				sql1.append(" ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTProgettoVO> vo = getJdbcTemplate().query(sql1.toString(), params, new ProgettoRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);
			}

		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<ProgettoCertificazioneIntermediaFinaleDTO> getGestisciPropostaIntermediaFinale(Long idProposta,
			String nomeBandoLinea, String codiceProgetto, String denominazioneBeneficiario, Long idLineaIntervento)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: getGestisciPropostaIntermediaFinale]";
		LOG.info(prf + " - BEGIN");

		try {
			String sql = fileSqlUtil.getQuery("GestisciProgettiIntermediaFinale.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);
			// le 3 parametri nomeBandoLinea, codiceProgetto e denominazioneBeneficiario
			// sono validati al servizio
			// pulisci le stringhe
			Object[] params = new Object[] { idProposta };
			if (StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
					&& StringUtil.isEmpty(denominazioneBeneficiario)) {
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
					&& StringUtil.isEmpty(denominazioneBeneficiario)) {
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'");

				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
					&& StringUtil.isEmpty(denominazioneBeneficiario)) {
				if (codiceProgetto.contains("'")) {
					codiceProgetto = codiceProgetto.replaceAll("'", "''");
				}
				sql1.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'");
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			} else if (StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
					&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
				if (denominazioneBeneficiario.contains("'")) {
					denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
				}
				sql1.append(" AND BENEFICIARIO = ").append("'").append(denominazioneBeneficiario).append("'");
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			}

			else if (!StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
					&& StringUtil.isEmpty(denominazioneBeneficiario)) {
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				if (codiceProgetto.contains("'")) {
					codiceProgetto = codiceProgetto.replaceAll("'", "''");
				}
				sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
						.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'");
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			} else if (!StringUtil.isEmpty(nomeBandoLinea) && StringUtil.isEmpty(codiceProgetto)
					&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				if (denominazioneBeneficiario.contains("'")) {
					denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
				}
				sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
						.append(" AND  BENEFICIARIO = ").append("'").append(denominazioneBeneficiario).append("'");
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			} else if (StringUtil.isEmpty(nomeBandoLinea) && !StringUtil.isEmpty(codiceProgetto)
					&& !StringUtil.isEmpty(denominazioneBeneficiario)) {
				if (codiceProgetto.contains("'")) {
					codiceProgetto = codiceProgetto.replaceAll("'", "''");
				}
				if (denominazioneBeneficiario.contains("'")) {
					denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
				}
				sql1.append(" AND CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'")
						.append(" AND  BENEFICIARIO = ").append("'").append(denominazioneBeneficiario).append("'");
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			} else {
				if (nomeBandoLinea.contains("'")) {
					nomeBandoLinea = nomeBandoLinea.replaceAll("'", "''");
				}
				if (codiceProgetto.contains("'")) {
					codiceProgetto = codiceProgetto.replaceAll("'", "''");
				}
				if (denominazioneBeneficiario.contains("'")) {
					denominazioneBeneficiario = denominazioneBeneficiario.replaceAll("'", "''");
				}

				sql1.append(" AND NOME_BANDO_LINEA = ").append("'").append(nomeBandoLinea).append("'")
						.append(" AND  CODICE_PROGETTO = ").append("'").append(codiceProgetto).append("'")
						.append(" AND  BENEFICIARIO = ").append("'").append(denominazioneBeneficiario).append("'");
				List<DettPropostaCertifAnnualVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new DettPropostaCertifAnnualRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoCertificazioneIntermediaFinaleDTO.class);
			}

		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}

	}

	@Override
	@Transactional
	public EsitoOperazioni modificaProgettiIntermediaFinale(
			List<ProgettoCertificazioneIntermediaFinaleDTO> progettiCertificazioneIntermediaFinale)
			throws FileNotFoundException, IOException, CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: modificaProgettiIntermediaFinale]";
		LOG.info(prf + " - BEGIN");
		String msg = "";
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			String sql = fileSqlUtil.getQuery("DettPropCertAnnual.sql");
			for (ProgettoCertificazioneIntermediaFinaleDTO dto : progettiCertificazioneIntermediaFinale) {
				// LOG.info(prf + " id = "+dto.getIdDettPropCertAnnual()+";
				// importoCertifNettoAnnual = "+dto.getImportoCertifNettoAnnual()+"; colonnaC =
				// "+dto.getColonnaC());

				// PBANDI-3125: prima si modificavano solo gli importi minori di quelli
				// originali, ora non più.

				// Legge da db il record per avere i valori vecchi degli importi.
				// Object[] params = new Object[] {dto.getIdDettPropCertAnnual()};
				// DettPropostaCertifAnnualVO vo = getJdbcTemplate().queryForObject(sql, params,
				// new PropostaCertifAnnualRowMapper());

				// if(modificaAmmessa(beanUtil.transform(vo.getImportoCertifNettoAnnual(),
				// Double.class) , dto.getImportoCertifNettoAnnual())
				// && modificaAmmessa(beanUtil.transform(vo.getColonnaC(), Double.class),
				// dto.getColonnaC()) ) {

				// Aggiorna il record con i nuovi valori in input.
				StringBuilder queryUpdate = new StringBuilder();
				queryUpdate.append("UPDATE PBANDI_T_DETT_PROP_CERT_ANNUAL SET IMPORTO_CERTIF_NETTO_ANNUAL = ")
						.append(dto.getImportoCertifNettoAnnual()).append(", COLONNA_C = ").append(dto.getColonnaC())
						.append(" WHERE ID_DETT_PROP_CERT_ANNUAL = ").append(dto.getIdDettPropCertAnnual());
				getJdbcTemplate().update(queryUpdate.toString());

				// } else {
				// Scrive il progetto nel messaggio da restituire in output.
				// LOG.info(prf + " errori");
				// if (msg.length() > 0)
				// msg = msg + ";";
				// msg = msg + dto.getCodiceProgetto();
				// }

			}
			if (msg.length() > 0) {
				msg = "Attenzione, i seguenti progetti non sono stati aggiornati : " + msg;
				esito.setEsito(false);
				esito.setMsg(msg);
			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_AGGIORNAMENTO_PROGETTI_INT_FINALE_SUCCESSO);
			}
			LOG.info(prf + " - END");
			return esito;
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}

	}

	private boolean modificaAmmessa(Double importoVecchio, Double importoNuovo) {
		String prf = "[CertificazioneDaoImpl :: modificaAmmessa]";
		LOG.info(prf + "  importoVecchio: " + importoVecchio + " importoNuovo: " + importoNuovo);
		if (importoVecchio == null)
			return false;
		if (importoVecchio < importoNuovo)
			return false;
		LOG.info(prf + " ");
		return true;

	}

	//////////////////////////////////////////////////////////
	//////////////////// CHIUSURA CONTI //////////////////////
	///////////////////////////////////////////////////////////
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, CertificazioneException.class,
			NullPointerException.class })
	public EsitoOperazioni chiusuraContiPropostaIntermediaFinale(Long idUtente, Long idProposta) throws Exception {
		String prf = "[CertificazioneDaoImpl :: chiusuraContiPropostaIntermediaFinale]";
		LOG.info(prf + " - BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		LOG.info(" idUtente = " + idUtente + " idProposta = " + idProposta);
		try {
			// 1. Aggiorna lo stato della proposta
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PBANDI_T_PROPOSTA_CERTIFICAZ set ID_STATO_PROPOSTA_CERTIF = ")
					.append(Constants.STATO_PROPOSTA_CERTIFICAZIONE_CHIUSURA_CONTI)
					.append("WHERE ID_PROPOSTA_CERTIFICAZ = ").append(idProposta);
			getJdbcTemplate().update(sql.toString());

			LOG.info(prf + " ********** PASSO 1 - AGGIORNAMENTO STATO SU DB ********** ");
			// 2) genero Excell
			// genero Excell "Rport Chiusura Conti.xls"
			byte[] reportBytes = null;
			try {
				reportBytes = creaESalvaReportChiusuraContiPropostaCertificazione(idUtente, idProposta);
			} catch (CertificazioneException e) {
				throw new CertificazioneException(
						"CertificazioneException - Errore durante la generazione report Chiusura conti:");
			} catch (NullPointerException e) {
				LOG.info(prf + " - Errore durante la generazione report Chiusura conti: " + e.getMessage());
				throw new NullPointerException(
						" NullPointerException - Errore durante la generazione report Chiusura conti:");
			} catch (Exception e) {
				LOG.info(prf + " - Errore durante la generazione report Chiusura conti: " + e.getMessage());
				throw new Exception("Errore durante la generazione report Chiusura conti:");
			}

			LOG.info(prf + " ********** PASSO 2 - CREA E SALVA REPORT ********** ");

			// 3) lo carico su fileStorage e documento_index
			String nomeFile = "report" + idProposta + "ChiusuraContiCertificazione.xls";
			esito = persistiDocumentoCC(idUtente, nomeFile, reportBytes, idProposta);

			LOG.info(prf + " ********** PASSO 3 - PERSIST DOCUMENTOCC SU DOCUMENTO_INDEX E FILESTORAGE ********** ");
			LOG.info(prf + " - EsitoOperazione: " + esito.getEsito() + " : " + esito.getMsg());
			LOG.info(prf + " - END");
			return esito;
		} catch (Exception e) {
			LOG.info(prf + " - Errore durante la generazione report Chiusura conti: " + e.getMessage());
			throw e;
		}
	}

	private byte[] creaESalvaReportChiusuraContiPropostaCertificazione(Long idUtente, Long idProposta)
			throws Exception {
		String prf = "[CertificazioneDaoImpl::creaESalvaReportChiusuraContiPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		// legge la proposta da db
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT p.*, r.ID_LINEA_DI_INTERVENTO, s.DESC_BREVE_STATO_PROPOSTA_CERT, s.DESC_STATO_PROPOSTA_CERTIF "
						+ "FROM PBANDI_T_PROPOSTA_CERTIFICAZ p, PBANDI_R_PROPOSTA_CERTIF_LINEA r, PBANDI_D_STATO_PROPOSTA_CERTIF s "
						+ "WHERE p.ID_PROPOSTA_CERTIFICAZ=r.ID_PROPOSTA_CERTIFICAZ "
						+ "AND p.ID_STATO_PROPOSTA_CERTIF = s.ID_STATO_PROPOSTA_CERTIF "
						+ "AND p.ID_PROPOSTA_CERTIFICAZ=")
				.append(idProposta);
		// sql.append("SELECT * FROM PBANDI_T_PROPOSTA_CERTIFICAZ WHERE
		// ID_PROPOSTA_CERTIFICAZ = ").append(idProposta);
		byte[] reportBytes = null;
		try {
			PbandiTPropostaCertificazVO propostaVO = getJdbcTemplate().queryForObject(sql.toString(),
					new PropostaCertificazioneRowMapper());
			// genero Report

			// reportBytes = generaReportChiusuraConti(propostaVO, idUtente);

			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO propostaServizit;
			propostaServizit = beanUtil.transform(propostaVO,
					it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO.class);
			reportBytes = certificazioneBusinessImpl.generaReportChiusuraConti(propostaServizit, idUtente, "");

		} catch (CertificazioneException e) {
			throw e;
		} catch (NullPointerException e) {
			LOG.info(prf + " - Errore durante la generazione report Chiusura conti: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			throw e;
		}

		if (reportBytes == null) {
			throw new CertificazioneException("report chiusura conti non generato!");
		}
		LOG.info(prf + " END");
		return reportBytes;
	}

	private EsitoOperazioni persistiDocumentoCC(Long idUtente, String nomeFile, byte[] reportBytes, Long idProposta)
			throws Exception {
		String prf = "[CertificazioneDaoImpl :: persistiDocumentoCC]";
		LOG.info(prf + " START");
		// 1) lo carico su fileStorage
		EsitoOperazioni esito = new EsitoOperazioni();
		boolean error = false;
		String tipoDocumento = Constants.TIPO_DOCUMENTO_INDEX_REPORT_CHIUSURA_CONTI;
		Boolean dirEsiste = fileApiServiceImpl.dirExists(tipoDocumento, true);
		if (dirEsiste) {
			LOG.info(prf + "dirEsiste: " + dirEsiste);
			InputStream inputStream = new ByteArrayInputStream(reportBytes);
			Boolean isUploaded = fileApiServiceImpl.uploadFile(inputStream, nomeFile, tipoDocumento);
			if (!isUploaded)
				error = true;
		} else {
			// Se la cartella non viene trovata, deve prima inserirla sotto \PBAN.
			FileApiServiceImpl fileApi = new FileApiServiceImpl(Constants.DIR_PBAN);
			InputStream inputStream = new ByteArrayInputStream(reportBytes);
			Boolean isUploaded = fileApi.uploadFile(inputStream, nomeFile, tipoDocumento);
			if (!isUploaded)
				error = true;
		}

		// controlla se è salvato correttamente
		if (error) {
			esito.setEsito(false);
			esito.setMsg(ErrorMessages.ERROR_UPLOAD_FILE);
			return esito;
		} else {
			// 2) lo persisto su documento_index
			// Nome univoco con cui il file verrà salvato su File System.
			PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();
			// Completo alcuni campi.
			// Id del nuovo record della PBANDI_T_DOCUMENTO_INDEX.
			BigDecimal idTDocumentoIndex = nuovoIdTDocumentoIndex();
			vo.setIdDocumentoIndex(idTDocumentoIndex);
			vo.setUuidNodo("UUID"); // Ci metto una stringa a caso, poichè su db il campo è obbligatorio.
			java.sql.Date oggi = DateUtil.getSysdate();
			vo.setDtInserimentoIndex(oggi);
			BigDecimal idEntita = idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_C_FILE);
			vo.setIdEntita(idEntita);
			vo.setIdTipoDocumentoIndex(idTipoIndexDaDescBreve(tipoDocumento));
			vo.setRepository(tipoDocumento); // il nome cartella sul nuovo storage
			vo.setNomeDocumento(nomeFile);
			vo.setNomeFile(nomeFile);
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO PBANDI_T_DOCUMENTO_INDEX ");
				sql.append(
						"(ID_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,DT_INSERIMENTO_INDEX,DT_MARCA_TEMPORALE,DT_VERIFICA_FIRMA,FLAG_FIRMA_CARTACEA,");
				sql.append(
						"ID_CATEG_ANAGRAFICA_MITT,ID_ENTITA,ID_MODELLO,ID_SOGG_DELEGATO,ID_SOGG_RAPPR_LEGALE,ID_STATO_DOCUMENTO,");
				sql.append(
						"ID_TARGET,ID_TIPO_DOCUMENTO_INDEX,ID_UTENTE_AGG,ID_UTENTE_INS,MESSAGE_DIGEST,NOME_FILE,NOTE_DOCUMENTO_INDEX,");
				sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO)");
				sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				LOG.info("\n" + sql);

				Object[] params = new Object[24];
				params[0] = vo.getIdDocumentoIndex();
				params[1] = vo.getDtAggiornamentoIndex();
				params[2] = vo.getDtInserimentoIndex();
				params[3] = vo.getDtMarcaTemporale();
				params[4] = vo.getDtVerificaFirma();
				params[5] = vo.getFlagFirmaCartacea();
				params[6] = vo.getIdCategAnagraficaMitt();
				params[7] = vo.getIdEntita();
				params[8] = vo.getIdModello();
				params[9] = vo.getIdSoggDelegato();
				params[10] = vo.getIdSoggRapprLegale();
				params[11] = vo.getIdStatoDocumento();
				params[12] = idProposta;
				params[13] = vo.getIdTipoDocumentoIndex();
				params[14] = vo.getIdUtenteAgg();
				params[15] = idUtente;
				params[16] = vo.getMessageDigest();
				params[17] = vo.getNomeFile();
				params[18] = vo.getNoteDocumentoIndex();
				params[19] = vo.getNumProtocollo();
				params[20] = vo.getRepository();
				params[21] = vo.getUuidNodo();
				params[22] = vo.getVersione();
				params[23] = vo.getNomeDocumento();

				int res = getJdbcTemplate().update(sql.toString(), params);
				if (res > 0) {
					LOG.info(prf + " inserito record inPBANDI_T_DOCUMENTO_INDEX con id = " + vo.getIdDocumentoIndex());
					esito.setEsito(true);
					esito.setMsg(MessageConstants.MSG_CHIUSURA_CONTI_AVVENUTA_CON_SUCCESSO);
					LOG.info(prf + " END");
					return esito;
				} else {
					esito.setEsito(false);
					esito.setMsg(ErrorMessages.ERRORE_CHIUSURA_CONTI);
					LOG.info(prf + " END");
					return esito;
				}

			} catch (Exception e) {
				LOG.error(prf + " ERRORE: ", e);
				throw new Exception(e.getMessage());
			}
		}

	}

	// Rimuovo l'annotation di transazionalita' e imposto il metodo come asyncrono
	// per un test
	@Override
	@Async
	public String aggiornaDatiIntermediaFinale(Long idUtente, Long idProposta) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: aggiornaDatiIntermediaFinale()]";
		LOG.info(prf + " - BEGIN");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"select ID_LINEA_DI_INTERVENTO  from PBANDI_R_PROPOSTA_CERTIF_LINEA  where ID_PROPOSTA_CERTIFICAZ = ")
					.append(idProposta);
			BigDecimal idLineaIntervento = getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);
			List<String> destinatariEmail = caricaDestinatariIntermediaFinaleEmail(idProposta,
					new BigDecimal[] { idLineaIntervento });

			int esitoProcedura = eseguiProceduraaggiornaPropostaIntermediaFinale(BigDecimal.valueOf(idUtente));

			MailVO mailVO = new MailVO();
			mailVO.setToAddresses(destinatariEmail);
			mailVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
			String message = null;
			LOG.info(prf + " - esitoProcedura: " + esitoProcedura);
			if (esitoProcedura == 0) {
				mailVO.setSubject(Constants.SUBJECT_SUCCESSO_AVVIO_PROCEDURA_AGGIORNAMENTO_INT_FIN);
				message = "L'aggiornamento della proposta di certificazione " + idProposta + " è avvenuta con successo";
			} else {
				mailVO.setSubject(Constants.SUBJECT_ERRORE_AVVIO_PROCEDURA_AGGIORNAMENTO_INT_FIN);
				message = "L'aggiornamento della proposta di certificazione " + idProposta
						+ " non ha avuto esito positivo.";
			}
			mailVO.setContent(message);
			mailUtil.send(mailVO);

			LOG.info(prf + " - END");
			return "END";
		} catch (Exception e) {
			throw new CertificazioneException(prf + " :" + e.getMessage());
		}
	}

	private int eseguiProceduraaggiornaPropostaIntermediaFinale(BigDecimal idUtente) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: eseguiProceduraaggiornaPropostaIntermediaFinale()]";
		LOG.info(prf + " - BEGIN");

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME)
				.withCatalogName("PCK_PBANDI_CERTIFICAZIONE").withFunctionName("CreaPropostaCertificazAggInt")
				.withoutProcedureColumnMetaDataAccess();

		jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
		jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_UTENTE, Types.NUMERIC));

		jdbcCall.setFunction(true);
		jdbcCall.compile();

		Map<String, Object> in = new HashMap<String, Object>();
		in.put(Constants.P_ID_UTENTE, idUtente);
		int res = -1;
		Map<String, Object> results = jdbcCall.execute(in);
		if (results.containsKey(Constants.RESULT)) {
			res = ((BigDecimal) results.get(Constants.RESULT)).intValue();
		}
		LOG.info(prf + " esito procedura: " + res);
		return res;

	}

	private List<String> caricaDestinatariIntermediaFinaleEmail(Long idProposta, BigDecimal[] idLineeDiIntervento)
			throws CertificazioneException, FileNotFoundException, IOException {

		String prf = "[CertificazioneDaoImpl :: caricaDestinatariIntermediaFinaleEmail()]";
		LOG.info(prf + " - BEGIN");

		List<String> emailRecipients = new ArrayList<String>();

		try {

			String sql = fileSqlUtil.getQuery("InvioPropCertIntermediaFinale.sql");
			Object[] params = new Object[] { idLineeDiIntervento[0] };

			List<PbandiDInvioPropostaCertifVO> invioPropostaVos = getJdbcTemplate().query(sql, params,
					new InvioPropostaRowMapper());
			for (PbandiDInvioPropostaCertifVO p : invioPropostaVos) {
				emailRecipients.add(p.getEmail());
			}
			if (emailRecipients.size() == 0) {
				throw new CertificazioneException("Non sono stati trovati indirizzi email a cui inviare la proposta "
						+ idProposta + ", impossibile inviarla.");
			}

			LOG.info(prf + " - END");
			return emailRecipients;
		} catch (Exception e) {
			throw new CertificazioneException(prf + " :" + e.getMessage());
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////// METHODI PER CAMPIONAMENTO
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public List<CodiceDescrizione> getAnnoContabile() throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: getAnnoContabile()]";
		LOG.info(prf + " - BEGIN");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM PBANDI_T_PERIODO WHERE ID_TIPO_PERIODO = ")
				.append(MessageConstants.TIPO_PERIODO_ANNO_CONTABILE);
		try {
			List<it.csi.pbandi.pbwebcert.integration.vo.PbandiTPeriodoVO> listPeriodo = getJdbcTemplate()
					.query(sql.toString(), new PeriodoRowMapper());
			HashMap<String, String> transMap = new HashMap<String, String>();
			transMap.put("idPeriodo", "codice");
			transMap.put("descPeriodoVisualizzata", "descrizione");
			LOG.info(prf + " - END");

			return beanUtil.transformList(listPeriodo, CodiceDescrizione.class, transMap);
		} catch (Exception e) {
			LOG.error(prf + "  Errore durante l'esecuzione del servizio getComboAnnoContabile: " + e);
			throw new CertificazioneException(prf + " Errore durante l'esecuzione del servizio getComboAnnoContabile:",
					e);
		}

	}

	@Override
	@Transactional
	public List<CodiceDescrizione> getLineeInterventoNormative(boolean isConsultazione, String flagCampionRilev) {
		String prf = "[CertificazioneDaoImpl :: getLineeInterventoNormative()]";
		LOG.info(prf + " - BEGIN");
		try {
			if (isConsultazione) {
				return null;
			} else {
				StringBuilder queryTipoLinea = new StringBuilder();
				queryTipoLinea.append("select LIVELLO_TIPO_LINEA as livelloTipoLinea , "
						+ "ID_TIPO_LINEA_INTERVENTO as idTipoLineaIntervento ,"
						+ "DESC_TIPO_LINEA_FAS as descTipoLineaFas ," + "DT_INIZIO_VALIDITA as dtInizioValidita ,"
						+ "COD_TIPO_LINEA as codTipoLinea , " + "DESC_TIPO_LINEA as descTipoLinea , "
						+ "DT_FINE_VALIDITA as dtFineValidita "
						+ " from PBANDI_D_TIPO_LINEA_INTERVENTO where DESC_TIPO_LINEA = 'Normativa'");

				PbandiDTipoLineaInterventoVO tipoLineaIntervento = new PbandiDTipoLineaInterventoVO();
				tipoLineaIntervento = getJdbcTemplate().queryForObject(queryTipoLinea.toString(),
						new TipoLineaInterventoRowMapper());

				String queryLinea = "select * from PBANDI_D_LINEA_DI_INTERVENTO ";
				queryLinea += "where ID_TIPO_LINEA_INTERVENTO = " + tipoLineaIntervento.getIdTipoLineaIntervento();
				queryLinea += " and upper(FLAG_CAMPION_RILEV) LIKE '%' ||upper('"
						+ Constants.FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE + "') ||'%'";
				// .append(" and upper(FLAG_CAMPION_RILEV) =
				// '").append(Constants.FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE).append("'");
				LOG.info("\n" + queryLinea.toString());
				List<PbandiDLineaDiInterventoVO> listaLinee = new ArrayList<>();
				listaLinee = getJdbcTemplate().query(queryLinea.toString(), new LineaInterventoRowMapper());

				if (flagCampionRilev.equalsIgnoreCase(Constants.FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE)) {
					List<CodiceDescrizione> elencoNormative = new ArrayList<CodiceDescrizione>();
					CodiceDescrizione cd = null;
					for (PbandiDLineaDiInterventoVO normativa : listaLinee) {
						cd = new CodiceDescrizione();
						cd.setCodice(normativa.getIdLineaDiIntervento().toString());
						cd.setDescrizione(normativa.getDescBreveLinea());
						elencoNormative.add(cd);
					}
					LOG.info(prf + " - END");
					return elencoNormative;
				} else {
					HashMap<String, String> trsMap = new HashMap<String, String>();
					trsMap.put("descLinea", "descrizione");
					trsMap.put("idLineaDiIntervento", "codice");
					LOG.info(prf + " - END");
					return beanUtil.transformList(listaLinee, CodiceDescrizione.class, trsMap);
				}
			}
		} catch (Exception e) {
			LOG.debug(prf + " " + e.getMessage());
			throw e;
		}

	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public EsitoGenerazioneReportDTO eseguiEstrazioneCampionamento(Long idNormativa, Long idUtente) throws Exception {
		String prf = "[CertificazioneDaoImpl :: eseguiEstrazioneCampionamento()] ";
		LOG.info(prf + " - BEGIN");
		EsitoGenerazioneReportDTO esito = new EsitoGenerazioneReportDTO();
		esito.setEsito(Boolean.FALSE);

		BigDecimal idCampione = new BigDecimal(-1);
		try {
			// Ottieni la linea di intervento
			StringBuilder queryLinea = new StringBuilder();
			queryLinea.append("select * " + " from PBANDI_D_LINEA_DI_INTERVENTO " + " where ID_LINEA_DI_INTERVENTO = ")
					.append(idNormativa);
			PbandiDLineaDiInterventoVO lineaIntervento = getJdbcTemplate().queryForObject(queryLinea.toString(),
					new LineaInterventoRowMapper());
			if (lineaIntervento != null) {
				String descBreveLinea = null;
				if (!lineaIntervento.getDescBreveLinea().startsWith("POR-FSE"))
					descBreveLinea = lineaIntervento.getDescBreveLinea();

				// Chiama procedura avviaProceduraCampionamento
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME1)
						.withProcedureName("fnc_MainCampionamento").withCatalogName("PCK_PBANDI_CAMPIONAMENTO")
						.withoutProcedureColumnMetaDataAccess();

				jdbcCall.declareParameters(new SqlOutParameter(Constants.ID_CAMPIONE, Types.NUMERIC));
				jdbcCall.declareParameters(new SqlParameter(Constants.P_DESC_BREVE_LINEA, Types.VARCHAR));
				jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_UTENTE_INS, Types.NUMERIC));

				jdbcCall.setFunction(true);
				Map<String, Object> input = new HashMap<>();
				input.put(Constants.P_DESC_BREVE_LINEA, descBreveLinea);
				input.put(Constants.P_ID_UTENTE_INS, BigDecimal.valueOf(idUtente));
				jdbcCall.compile();

				LOG.info(prf + "Eseguo PCK_PBANDI_CAMPIONAMENTO.fnc_MainCampionamento() con linea = " + descBreveLinea
						+ "; idUtente = " + idUtente);
				Map<String, Object> out = jdbcCall.execute(input);

				if (!out.isEmpty()) {
					if (out.get(Constants.ID_CAMPIONE) != null) {
						idCampione = (BigDecimal) out.get(Constants.ID_CAMPIONE);
					}
				}

				if (idCampione == null) {
					LOG.info(prf + "Valore di ritorno NULLO.");
				} else {
					LOG.info(prf + "Valore di ritorno = " + idCampione.longValue());
				}

				// controlla il risultato della procedura
				if (idCampione != null && idCampione.longValue() == -2L) {
					esito.setEsito(Boolean.FALSE);
					esito.setMessagge(ErrorMessages.ERROR_PROCEDURA_CAMPIONAMENTO_PRESENTE);
				} else if (idCampione != null && idCampione.longValue() == -3L) {
					esito.setEsito(Boolean.FALSE);
					esito.setMessagge(
							"Attenzione! Non è possibile procedere al campionamento in assenza di una proposta di certificazione in stato APERTA");
				} else {

					// LISTA DI ELEMENTI che andranno a popolare il foglio excel denominato
					// "Prog estratti"
					String sql1 = "SELECT TCAMPIONAMENTO.ID_CAMPIONE, tcampionamento.DATA_CAMPIONAMENTO, tcampionamento.ID_PROPOSTA_CERTIFICAZ, tcampionamento.ID_LINEA_DI_INTERVENTO, tcampionamento.ID_CATEG_ANAGRAFICA, tcampionamento.ID_PERIODO, RCAMPIONAMENTO.AVANZAMENTO, RCAMPIONAMENTO.PROGR_OPERAZIONE, RCAMPIONAMENTO.TITOLO_PROGETTO, RCAMPIONAMENTO.UNIVERSO, RCAMPIONAMENTO.ID_PROGETTO, TPROGETTO.ID_TIPO_OPERAZIONE, dto.DESC_TIPO_OPERAZIONE, TPROGETTO.CODICE_VISUALIZZATO, INTERVENTO.DESC_LINEA, RCAMPIONAMENTO.ASSE, PERIODO.DESC_PERIODO_VISUALIZZATA, TCAMPIONAMENTO.TIPO_CONTROLLI ";
					sql1 += "FROM ";
					sql1 += "PBANDI_T_CAMPIONAMENTO tcampionamento LEFT JOIN pbandi_t_periodo periodo ON TCAMPIONAMENTO.ID_PERIODO = PERIODO.ID_PERIODO, ";
					sql1 += "PBANDI_R_CAMPIONAMENTO rcampionamento LEFT JOIN PBANDI_T_PROGETTO tprogetto ON RCAMPIONAMENTO.ID_PROGETTO = TPROGETTO.ID_PROGETTO LEFT JOIN PBANDI_D_TIPO_OPERAZIONE dto ON TPROGETTO.ID_TIPO_OPERAZIONE = dto.ID_TIPO_OPERAZIONE, ";
					sql1 += "PBANDI_D_LINEA_DI_INTERVENTO intervento ";
					sql1 += "WHERE ";
					sql1 += "TCAMPIONAMENTO.ID_CAMPIONE = RCAMPIONAMENTO.ID_CAMPIONE ";
					sql1 += "AND TCAMPIONAMENTO.ID_LINEA_DI_INTERVENTO = INTERVENTO.ID_LINEA_DI_INTERVENTO ";
					sql1 += "and TCAMPIONAMENTO.ID_CAMPIONE = " + idCampione.longValue() + " ";
					sql1 += "order by RCAMPIONAMENTO.PROGR_OPERAZIONE";
					LOG.info("\n" + sql1);
					List<CampioneCertificazioneVO> elementiExcelCampionamenti = getJdbcTemplate().query(sql1,
							new CampionamentoCertificazioneRowMapper());

					for (CampioneCertificazioneVO vo : elementiExcelCampionamenti) {
						if (vo.getIdTipoOperazione() != null) {
							if (vo.getIdTipoOperazione().longValue() == 3L) {
								vo.setDescTipoOperazione("S");
							} else {
								vo.setDescTipoOperazione("N");
							}
						} else {
							vo.setDescTipoOperazione("N");
						}
					}

					// LISTA DI ELEMENTI che andranno a popolare il foglio excel denominato
					// "Progetti"
					StringBuilder sql2 = new StringBuilder();
					sql2.append("SELECT * FROM PBANDI_W_CAMPIONAMENTO WHERE ID_CAMPIONE = ").append(idCampione)
							.append(" ORDER BY PROGR_OPERAZIONE ASC");
					LOG.info("\n" + sql2.toString());
					List<PbandiWCampionamentoVO> elementiExcelProgetti = getJdbcTemplate().query(sql2.toString(),
							new PbandiWCampionamentoRowMapper());

					// Dati presi dalla tabella pbandi_r_dett_campionamento che andranno a popolare
					// il figlio "Dati estratti"
					StringBuilder sql3 = new StringBuilder();
					sql3.append("SELECT * FROM PBANDI_R_DETT_CAMPIONAMENTO WHERE ID_CAMPIONE = ").append(idCampione);
					LOG.info("\n" + sql3.toString());
					PbandiRDettCampionamentoVO dettCampionamento = getJdbcTemplate().queryForObject(sql3.toString(),
							new PbandiRDettCampionamentoRowMapper());

					ArrayList<Long> linesToJump = new ArrayList<Long>();
					linesToJump.add(1L);

					String templateKey = null;

					LOG.info("DESC BREVE LINEA INTERVENTO " + descBreveLinea);

					String templateFoglioProgetti = Constants.TEMPLATE_REPORT_CAMPIONI_FOGLIO_PROGETTI;
					String nomeFoglioEstrazione = "Prog estratti";
					String nomeFoglioProgettiEstratti = "Progetti";

					if ("POR-FESR-2014-2020".equalsIgnoreCase(lineaIntervento.getDescBreveLinea())) {
						templateKey = Constants.TEMPLATE_REPORT_CAMPIONI_POR_FESR2014_20;
					} else if ("PAR-FAS".equalsIgnoreCase(lineaIntervento.getDescBreveLinea())) {
						templateKey = Constants.TEMPLATE_REPORT_CAMPIONI_PAR_FAS;
					} else {
						templateKey = Constants.TEMPLATE_REPORT_CAMPIONI_POR_FSE;
						templateFoglioProgetti = Constants.TEMPLATE_REPORT_CAMPIONI_FOGLIO_OPERAZIONI;
						nomeFoglioEstrazione = "Operaz estratte";
						nomeFoglioProgettiEstratti = "Operazioni";
					}

					esito = salvaExcelCampionamento(beanUtil.transform(idCampione, BigDecimal.class), idUtente,
							elementiExcelCampionamenti, elementiExcelProgetti, dettCampionamento,
							templateFoglioProgetti, nomeFoglioEstrazione, nomeFoglioProgettiEstratti, templateKey);
				}

			}
		} catch (Exception e) {
			LOG.error(prf + "Errore durante l'esecuzione del servizio eseguiProceduraEstrazioneCampionamento:", e);
			throw e;
		}

		LOG.info(prf + " - END");
		return esito;
	}

	@Override
	@Transactional
	public List<it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO> getElencoReportCampionamento(
			Long idNormativa, Long idAnnoContabile) throws FileNotFoundException, IOException {
		String prf = "[CertificazioneDaoImpl::getElencoReportCampionamento]";
		LOG.info(prf + " BEGIN");
		try {
			// Legge ReportCampionamentoVO query.
			String sql = fileSqlUtil.getQuery("ReportCampionamentoVO.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);

			LOG.info(prf + " idNormativa = " + idNormativa + " idAnnoContabile = " + idAnnoContabile);
			if (idNormativa != null && idAnnoContabile != null) {
				Object[] param = new Object[2];
				param[0] = idAnnoContabile;
				param[1] = idNormativa;
				sql1.append(" WHERE ID_PERIODO = ? AND ID_NORMATIVA = ? ");

				List<ReportCampionamentoVO> vo = getJdbcTemplate().query(sql1.toString(), param,
						new ReportCampionamentoRowMapper());
				return beanUtil.transformList(vo,
						it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO.class);
			} else if (idNormativa != null && idAnnoContabile == null) {
				Object[] param = new Object[1];
				param[0] = idNormativa;
				sql1.append(" WHERE ID_NORMATIVA = ? ");
				List<ReportCampionamentoVO> vo = getJdbcTemplate().query(sql1.toString(), param,
						new ReportCampionamentoRowMapper());
				return beanUtil.transformList(vo,
						it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO.class);
			} else if (idNormativa == null && idAnnoContabile != null) {
				Object[] param = new Object[1];
				param[0] = idAnnoContabile;
				sql1.append(" WHERE ID_PERIODO = ? ");
				List<ReportCampionamentoVO> vo = getJdbcTemplate().query(sql1.toString(), param,
						new ReportCampionamentoRowMapper());
				return beanUtil.transformList(vo,
						it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO.class);
			} else {
				List<ReportCampionamentoVO> vo = getJdbcTemplate().query(sql, new ReportCampionamentoRowMapper());
				return beanUtil.transformList(vo,
						it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO.class);
			}

		} catch (Exception e) {
			LOG.error(prf + " " + e.getMessage());
			throw e;
		}

	}

	private EsitoGenerazioneReportDTO salvaExcelCampionamento(BigDecimal idCampione, Long idUtente,
			List<CampioneCertificazioneVO> elementiExcelCampionamenti,
			List<PbandiWCampionamentoVO> elementiExcelProgetti, PbandiRDettCampionamentoVO dettCampionamento,
			String templateFoglioProgetti, String nomeFoglioEstrazione, String nomeFoglioProgettiEstratti,
			String templateKey) throws Exception {
		String prf = "[CertificazioneDaoImpl :: salvaExcel]";
		LOG.info(prf + " START");
		LOG.info(prf + "templateFoglioProgetti = " + templateFoglioProgetti + "; nomeFoglioEstrazione = "
				+ nomeFoglioEstrazione + "; nomeFoglioProgettiEstratti = " + nomeFoglioProgettiEstratti
				+ "; templateKey = " + templateKey);

		EsitoGenerazioneReportDTO esito = new EsitoGenerazioneReportDTO();
		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(1L);
		byte[] singlePageProgEstratti = TableWriter.writeTableToByteArray(templateKey, new ExcelDataWriter(templateKey),
				elementiExcelCampionamenti, linesToJump, false);

		byte[] singlePageProgetti = TableWriter.writeTableToByteArray(templateFoglioProgetti,
				new ExcelDataWriter(templateKey), elementiExcelProgetti, linesToJump, false);

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put(nomeFoglioEstrazione, singlePageProgEstratti);
		singlePageTables.put(nomeFoglioProgettiEstratti, singlePageProgetti);

		Map<String, Object> testataVars = new HashMap<String, Object>();

		if (elementiExcelCampionamenti != null && !elementiExcelCampionamenti.isEmpty()) {
			testataVars.put("descProgramma", elementiExcelCampionamenti.get(0).getDescLinea());
			testataVars.put("descAnnoContabile", elementiExcelCampionamenti.get(0).getDescPeriodoVisualizzata());
			testataVars.put("idPropostaCertificazione", elementiExcelCampionamenti.get(0).getIdPropostaCertificaz());
			testataVars.put("dataCampione", elementiExcelCampionamenti.get(0).getDataCampionamento());
		}

		Map<String, Object> datiEstrazione = new HashMap<String, Object>();
		if (dettCampionamento != null && dettCampionamento.getIdCampione() != null) {
			datiEstrazione.put("valoreMaxAvanzamentoU", dettCampionamento.getValoreMaxAvanzamentoU());
			datiEstrazione.put("valoreMinAvanzamentoU", dettCampionamento.getValoreMinAvanzamentoU());
			datiEstrazione.put("valoreMedAvanzamentoU", dettCampionamento.getValoreMedAvanzamentoU());
			datiEstrazione.put("valoreM75AvanzamentoU", dettCampionamento.getValoreM75AvanzamentoU());
			datiEstrazione.put("numerositaCampioneU1", dettCampionamento.getNumerositaCampioneU1());
			datiEstrazione.put("numeroUnitaEstratteU1", dettCampionamento.getNumeroUnitaEstratteU1());
			datiEstrazione.put("progrOperazPrimoEstrattoU1", dettCampionamento.getProgrOperazPrimoEstrattoU1());
			datiEstrazione.put("avanzamentoPrimoEstrattoU1", dettCampionamento.getAvanzamentoPrimoEstrattoU1());
			datiEstrazione.put("sommaAvanzamentiCampioneU1", dettCampionamento.getSommaAvanzamentiCampioneU1());
			datiEstrazione.put("totaleAvanzamentiEstrattiU1", dettCampionamento.getTotaleAvanzamentiEstrattiU1());
			datiEstrazione.put("rapportoNumerositaU1", dettCampionamento.getRapportoNumerositaU1());
			datiEstrazione.put("valoreMaxAvanzamentoU2", dettCampionamento.getValoreMaxAvanzamentoU2());
			datiEstrazione.put("valoreMinAvanzamentoU2", dettCampionamento.getValoreMinAvanzamentoU2());
			datiEstrazione.put("numerositaCampioneU2", dettCampionamento.getNumerositaCampioneU2());
			datiEstrazione.put("numeroUnitaEstratteU2", dettCampionamento.getNumeroUnitaEstratteU2());
			datiEstrazione.put("rapportoNumerositaU2", dettCampionamento.getRapportoNumerositaU2());
			datiEstrazione.put("valoreV", dettCampionamento.getValoreV());
			datiEstrazione.put("sommaAvanzamentiCampioneU2", dettCampionamento.getSommaAvanzamentiCampioneU2());
			datiEstrazione.put("progrOperazPrimoEstrattoU2", dettCampionamento.getProgrOperazPrimoEstrattoU2());
			datiEstrazione.put("totaleAvanzamentiEstrattiU2", dettCampionamento.getTotaleAvanzamentiEstrattiU2());
			datiEstrazione.put("totaleAvanzamentiEstrattiU", dettCampionamento.getTotaleAvanzamentiEstrattiU());
			datiEstrazione.put("sommaAvanzamentiCampioneU", dettCampionamento.getSommaAvanzamentiCampioneU());
			datiEstrazione.put("rapportoAvanzamentiU", dettCampionamento.getRapportoAvanzamentiU());
			datiEstrazione.put("numeroUnitaEstratteU", dettCampionamento.getNumeroUnitaEstratteU());
			datiEstrazione.put("numerositaCampioneU", dettCampionamento.getNumerositaCampioneU());
			datiEstrazione.put("rapportoNumerositaU", dettCampionamento.getRapportoNumerositaU());
			datiEstrazione.put("avanzamentoPrimoEstrattoU2", dettCampionamento.getAvanzamentoPrimoEstrattoU2());
			if (elementiExcelCampionamenti != null)
				datiEstrazione.put("dataCampione", elementiExcelCampionamenti.get(0).getDataCampionamento());

		}

		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Dati estrazione", datiEstrazione);

		byte[] reportResult = reportManagerPbservizit.getMergedDocumentFromTemplate(templateKey, singlePageTables,
				singleCellVars);

		ReportCampionamentoDTO reportDTO = new ReportCampionamentoDTO();
		reportDTO.setExcelByte(reportResult);
		reportDTO.setTemplateName(templateKey);
		reportDTO.setIdCampione(idCampione);
		reportDTO.setIdUtenteIns(beanUtil.transform(idUtente, BigDecimal.class));
		reportDTO.setCategAnag("AdC");
		reportDTO.setTipoDocumento("07");

		if (reportResult != null) {

			// PbandiTDocumentoIndexVO docIndex = documentoManager.creaDocumento(idUtente,
			// reportDTO, null, null, null, null);

			String sqlEntita = "SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_CAMPIONAMENTO'";
			LOG.info("\n" + sqlEntita);
			BigDecimal idEntita = (BigDecimal) getJdbcTemplate().queryForObject(sqlEntita, BigDecimal.class);

			String descBreveTipoDocIndex = "FCC";
			String sqlTipoDoc = "SELECT ID_TIPO_DOCUMENTO_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE DESC_BREVE_TIPO_DOC_INDEX='"
					+ descBreveTipoDocIndex + "'";
			LOG.info("\n" + sqlTipoDoc);
			BigDecimal idTipoDoc = (BigDecimal) getJdbcTemplate().queryForObject(sqlTipoDoc, BigDecimal.class);

			String sqlStatoDoc = "SELECT ID_STATO_DOCUMENTO FROM PBANDI_D_STATO_DOCUMENTO_INDEX WHERE DESC_BREVE='GENERATO'";
			LOG.info("\n" + sqlStatoDoc);
			BigDecimal idStatoDoc = (BigDecimal) getJdbcTemplate().queryForObject(sqlStatoDoc, BigDecimal.class);

			it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO docIndex = new it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO();
			docIndex.setNomeFile(documentoManager.getNomeFile(reportDTO));
			docIndex.setIdUtenteIns(new BigDecimal(idUtente));
			docIndex.setIdEntita(idEntita);
			docIndex.setIdTarget(idCampione);
			docIndex.setIdTipoDocumentoIndex(idTipoDoc);
			docIndex.setRepository(descBreveTipoDocIndex);
			docIndex.setIdStatoDocumento(idStatoDoc);

			boolean esitoSalvaFile = documentoManagerPbservizit.salvaFile(reportResult, docIndex);

			if (docIndex != null && docIndex.getIdDocumentoIndex() != null) {
				esito.setEsito(Boolean.TRUE);
				esito.setIdDocumentoIndex(docIndex.getIdDocumentoIndex().longValue());
				esito.setMessagge(MessageConstants.KEY_MSG_PROCEDURA_CAMPIONAMENTO_SUCCESSO);
				esito.setReport(reportResult);
				esito.setNomeDocumento(docIndex.getNomeFile());
			} else
				esito.setEsito(Boolean.FALSE);
		} else {
			esito.setEsito(Boolean.FALSE);
		}
		LOG.info(prf + " END");
		return esito;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// METHODI DI RICERCA DOCUMENTI
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public EsitoGenerazioneReportDTO getContenutoDocumentoById(Long idDocumentoIndex) throws Exception {
		String prf = "[CertificazioneDaoImpl::getContenutoDocumentoById]";
		LOG.info(prf + " BEGIN");
		EsitoGenerazioneReportDTO esito = new EsitoGenerazioneReportDTO();
		try {
			// ottieni il nomeDocumento da db
			StringBuilder sql = new StringBuilder();
			sql.append("select d.*, t.desc_breve_tipo_doc_index, t.desc_tipo_doc_index "
					+ "from PBANDI_T_DOCUMENTO_INDEX d, PBANDI_C_TIPO_DOCUMENTO_INDEX t "
					+ "where d.id_tipo_documento_index = t.id_tipo_documento_index and ID_DOCUMENTO_INDEX =");
			sql.append(idDocumentoIndex);
			LOG.info("\n" + sql.toString());
			DocumentoIndexVO documento = getJdbcTemplate().queryForObject(sql.toString(),
					new DocumentoIndexRowMapper());

			LOG.info(prf + " Nome file da scaricare: " + documento.getNomeDocumento() + " " + documento.getNomeFile());
			// scarica file da file storage
			java.io.File file = fileApiServiceImpl.downloadFile(documento.getNomeDocumento(),
					documento.getDescBreveTipoDocIndex());
			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			esito.setEsito(true);
			esito.setIdDocumentoIndex(idDocumentoIndex);
			esito.setNomeDocumento(documento.getNomeDocumento());
			esito.setReport(fileBytes);
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public List<DocumentoCertificazioneDTO> ricercaDocumenti(FiltroRicercaDocumentoDTO filtro) throws Exception {
		String prf = "[CertificazioneDaoImpl::ricercaDocumenti]";
		LOG.info(prf + " BEGIN");
		try {
			// Legge gli affidamenti del progetto.
			String sql = fileSqlUtil.getQuery("RicercaDocumenti.sql");
			int flagVirgola = 0;
			String idProposta = "";

			if (filtro.getIdProposta() != null) {
				idProposta = "  AND rpcl.ID_PROPOSTA_CERTIFICAZ = " + filtro.getIdProposta();
			}

			StringBuilder descBreveTipoIndex = new StringBuilder();
			LOG.info(prf + " ++++++++ FILTRO DELLA RICERCA ++++++++++");
			LOG.info(prf + "  dichiarazioneFinale: " + filtro.getIsDichiarazioneFinale());
			LOG.info(prf + "  Checklist: " + filtro.getIsChecklist());
			LOG.info(prf + "  PropostaDiCertificazione: " + filtro.getIsPropostaDiCertificazione());

			LOG.info(prf + " isRicercaProposte: " + filtro.getIsRicercaProposta());
			LOG.info(prf + " isRicercaPropostaProgetto: " + filtro.getIsRicercaPropostaProgetto());

			if (filtro.getIsDichiarazioneFinale() || filtro.getIsChecklist()
					|| filtro.getIsPropostaDiCertificazione()) {
				LOG.info(prf + " idTipoDocIndex aggiunto nella query");
				descBreveTipoIndex.append("AND DESC_BREVE_TIPO_DOC_INDEX in (");
				boolean empty = true;
				if (filtro.getIsDichiarazioneFinale() != null && filtro.getIsDichiarazioneFinale()) {
					descBreveTipoIndex.append("'")
							.append(Constants.TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_FINALE_DI_CERTIFICAZIONE).append("',");
					empty = false;
				}

				if (filtro.getIsChecklist() != null && filtro.getIsChecklist()) {
					if (filtro.getIsRicercaProposta() != null && filtro.getIsRicercaProposta()) {
						descBreveTipoIndex.append("'")
								.append(Constants.TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROPOSTA)
								.append("',");
					}
					if (filtro.getIsRicercaPropostaProgetto() != null && filtro.getIsRicercaPropostaProgetto()) {
						descBreveTipoIndex.append("'")
								.append(Constants.TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROGETTO)
								.append("',");
					}
					empty = false;
				}

				if (filtro.getIsPropostaDiCertificazione() != null && filtro.getIsPropostaDiCertificazione()) {
					flagVirgola = 1;
					descBreveTipoIndex.append("'")
							.append(Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE).append("'");
					empty = false;
				}
				descBreveTipoIndex.append(")");

				if (flagVirgola != 1 && !empty) {

					StringBuilder sql2 = new StringBuilder();
					sql2.append(descBreveTipoIndex.toString());

					String descBreveTipoIndexFinale = descBreveTipoIndex.toString().replace(",)", ")");
					descBreveTipoIndex.setLength(0);
					descBreveTipoIndex.append(" ");

					descBreveTipoIndex.append(descBreveTipoIndexFinale);

				}

				if (empty) {
					// cancella descBreveTipoIndex
					descBreveTipoIndex.setLength(0);
					descBreveTipoIndex.append(" ");
				}

			} else {
				// cancella descBreveTipoIndex
				descBreveTipoIndex.setLength(0);
				descBreveTipoIndex.append(" ");
			}

			String linea = " ";
			if (filtro.getIdLineaIntervento() != null) {
				linea += "AND rpcl.id_linea_di_intervento = " + filtro.getIdLineaIntervento();
			}

			StringBuilder descBreveStatoProposta = new StringBuilder();
			if (filtro.getStatiProposte() != null && filtro.getStatiProposte().length > 0) {

				descBreveStatoProposta.append(" AND DESC_BREVE_STATO_PROPOSTA_CERT in (");
				for (String stato : filtro.getStatiProposte()) {
					descBreveStatoProposta.append("'").append(stato).append("',");
				}
			}

			// taglia l'ultimo carattere virgola
			String descBreveStatoProposta1 = " ";
			if ((descBreveStatoProposta != null) && (descBreveStatoProposta.length() > 0)) {
				descBreveStatoProposta1 = descBreveStatoProposta.substring(0, descBreveStatoProposta.length() - 1);
			}
			descBreveStatoProposta1 = descBreveStatoProposta1 + ")";

			StringBuilder queryFinale = new StringBuilder();
			queryFinale.append(sql).append(descBreveTipoIndex).append(descBreveStatoProposta1).append(idProposta)
					.append(linea);
			LOG.info(prf + "\n" + queryFinale.toString());
			List<DocumentoCertificazioneVO> vo = getJdbcTemplate().query(queryFinale.toString(),
					new DocumentoCertificazioneRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(vo, DocumentoCertificazioneDTO.class);
		} catch (Exception e) {
			throw e;
		}

	}

	/**************************************************
	 * METHODI PER ACCESSO A DB
	 ************************************************************/

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected void setWhereClause(List<StringBuilder> conditionList, StringBuilder sqlSelect, StringBuilder tables) {
		String prf = "[CertificazioneDaoImpl :: setWhereClause]";
		LOG.info(prf + " START");
		// addDataFine(tablesControlloData, conditionList);
		if (!conditionList.isEmpty()) {
			sqlSelect.append(" WHERE");
			for (int i = 0; i < conditionList.size(); i++) {
				if (i > 0) {
					sqlSelect.append(" AND");
				}
				sqlSelect.append(" ").append(conditionList.get(i));
			}
		}
		LOG.info(prf + " END");
	}

	protected Set<String> findTableNamesWithAlias(String sqlInsert) {
		String prf = "[CertificazioneDaoImpl :: findTableNamesWithAlias]";
		LOG.info(prf + " START");
		Set<String> tableNames = new HashSet<String>();
		// non supporta le tabelle con nomi case sensitive
		sqlInsert = sqlInsert.toLowerCase().trim();

		Pattern p = Pattern.compile(Constants.TABLE_NAME_PREFIX + "[a-z_]*[ a-z_]*");
		Matcher matcher = p.matcher(sqlInsert);
		while (matcher.find()) {
			tableNames.add(matcher.group().trim());
		}
		LOG.info(prf + " END");
		return tableNames;
	}

	private BigDecimal nuovoIdTDocumentoIndex() {
		String prf = "[CertificazioneDaoImpl::nuovoIdTDocumentoIndex] ";
		LOG.info(prf + " START");
		BigDecimal id = null;
		try {
			String sqlSeq = "SELECT SEQ_PBANDI_T_DOCUMENTO_INDEX.nextval from dual ";
			LOG.info("\n" + sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			LOG.info("Nuovo id PBANDI_T_DOCUMENTO_INDEX = " + id.longValue());
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw e;
		}
		return id;
	}

	private BigDecimal nuovoIdPropostaCertificaz() {
		String prf = "[CertificazioneDaoImpl::nuovoIdTDocumentoIndex] ";
		LOG.info(prf + " START");
		BigDecimal id = null;
		try {
			String sqlSeq = "SELECT SEQ_PBANDI_T_PROPOSTA_CERTIF.nextval from dual ";
			LOG.info("\n" + sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			LOG.info("Nuovo id PBANDI_T_PROPOSTA_CERTIFICAZ = " + id.longValue());
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw e;
		}
		return id;
	}

	public String descrizioneDaId(String tabella, String colonnaId, String colonnaDescrizione, Long valoreId)
			throws DaoException {
		String prf = "[CertificazioneDaoImpl::descrizioneDaId() ]";
		LOG.info(prf + " START");
		String descrizione = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + colonnaDescrizione + " FROM " + tabella + " WHERE " + colonnaId + " = "
					+ valoreId.toString());
			LOG.debug("\n" + sql.toString());
			descrizione = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);
		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella ricerca di una descriziozne da un id: ", e);
			throw new DaoException("ERRORE nella ricerca di una descriziozne da un id: ", e);
		} finally {
			LOG.info(prf + " END");
		}

		return descrizione;
	}

	public BigDecimal idDaDescrizione(String tabella, String colonnaId, String colonnaDescrizione,
			String valoreDescrizione) throws DaoException {
		String prf = "[CertificazioneDaoImpl::idDaDescrizione() ]";
		LOG.info(prf + " START");
		BigDecimal id = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + colonnaId + " FROM " + tabella + " WHERE " + colonnaDescrizione + " = '"
					+ valoreDescrizione + "'");
			LOG.debug("\n" + sql.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);
		} catch (Exception e) {
			LOG.error(prf + "ERRORE nella ricerca di un id da una descriziozne: ", e);
			throw new DaoException("ERRORE nella ricerca di un id da una descriziozne: ", e);
		} finally {
			LOG.info(prf + " END");
		}
		LOG.info(prf + " END");
		return id;
	}

	public BigDecimal idTipoIndexDaDescBreve(String descBreve) {
		String prf = "[CertificazioneDaoImpl::idTipoIndexDaDescBreve]";
		LOG.info(prf + " START");
		StringBuilder queryTipoIndex = new StringBuilder();
		queryTipoIndex.append("SELECT * from PBANDI_C_TIPO_DOCUMENTO_INDEX where DESC_BREVE_TIPO_DOC_INDEX = ")
				.append("'").append(descBreve).append("'");
		TipoDocumentoIndexVO tipoDocumentoVO = getJdbcTemplate().queryForObject(queryTipoIndex.toString(),
				new TipoDocumentoIndexMapper());
		LOG.info(prf + " END");
		return beanUtil.transform(tipoDocumentoVO.getIdTipoDocumentoIndex(), BigDecimal.class);
	}

	public BigDecimal idStatoDaDescBreve(String descBreve) {
		String prf = "[CertificazioneDaoImpl::idStatoDaDescBreve]";
		LOG.info(prf + " START");
		StringBuilder queryTipoIndex = new StringBuilder();
		queryTipoIndex.append(
				"SELECT ID_STATO_PROPOSTA_CERTIF from PBANDI_D_STATO_PROPOSTA_CERTIF where DESC_BREVE_STATO_PROPOSTA_CERT = ")
				.append("'").append(descBreve).append("'");
		BigDecimal idStato = getJdbcTemplate().queryForObject(queryTipoIndex.toString(), BigDecimal.class);
		LOG.info(prf + " END");
		return idStato;
	}

	@Override
	@Transactional
	public EsitoOperazioni modificaAllegati(List<DocumentoDescrizione> documentiDescrizioni) {
		String prf = "[CertificazioneDaoImpl::modificaAllegati]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		int countErrore = 0;
		try {
			for (DocumentoDescrizione documentoDescrizione : documentiDescrizioni) {
				StringBuilder queryUpdate = new StringBuilder();
				if (StringUtil.isEmpty(documentoDescrizione.getDescrizione())) {
					queryUpdate.append("UPDATE PBANDI_T_DOCUMENTO_INDEX SET NOTE_DOCUMENTO_INDEX = null")
							.append(" WHERE ID_DOCUMENTO_INDEX = ").append(documentoDescrizione.getIdDocumentoIndex());
				} else {
					queryUpdate.append("UPDATE PBANDI_T_DOCUMENTO_INDEX SET NOTE_DOCUMENTO_INDEX = ").append("'")
							.append(documentoDescrizione.getDescrizione()).append("'")
							.append(" WHERE ID_DOCUMENTO_INDEX = ").append(documentoDescrizione.getIdDocumentoIndex());
				}
				int numRecord = getJdbcTemplate().update(queryUpdate.toString());
				if (numRecord == 0) {
					countErrore++;
				}

			}
			if (countErrore > 0) {
				if (countErrore == documentiDescrizioni.size()) {
					esito.setEsito(false);
					esito.setMsg(ErrorMessages.ERROR_DESCRIZIONE_TUTTI_ALLEGATI_NON_MODIFICATI);
				} else {
					esito.setEsito(false);
					esito.setMsg(ErrorMessages.ERROR_DESCRIZIONE_ALLEGATO_NON_MODIFICATO);
				}

			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_DESCRIZIONE_MODIFICATO_CON_SUCCESSO);
			}

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return esito;

	}

	private Integer eseguiProceduraCreaNotifAnticipi(Long idProposta) {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraCreaNotifAnticipi]";
		LOG.info(prf + " START");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME1)
				.withProcedureName("FNC_CREA_NOTIF_ANTICIPI").withCatalogName("PCK_PBANDI_NOTIFICHE")
				.withoutProcedureColumnMetaDataAccess();

		jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
		jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_PROPOSTA_CERTIFICAZ, Types.NUMERIC));
		jdbcCall.setFunction(true);
		jdbcCall.compile();

		Map<String, Object> in = new HashMap<String, Object>();
		in.put(Constants.P_ID_PROPOSTA_CERTIFICAZ, idProposta);

		Integer result = 1;
		Map<String, Object> results = jdbcCall.execute(in);
		if (results.containsKey(Constants.RESULT)) {
			result = ((BigDecimal) results.get(Constants.RESULT)).intValue();
		}
		LOG.info(prf + " END");
		return result;
	}

	private Integer eseguiProceduraCreaNotifAnticipiMail() {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraCreaNotifAnticipiMail]";
		LOG.info(prf + " START");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME1)
				.withProcedureName("FNC_CREA_NOTIF_ANTICIPI_MAIL").withCatalogName("PCK_PBANDI_NOTIFICHE")
				.withoutProcedureColumnMetaDataAccess();

		jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
		jdbcCall.setFunction(true);
		jdbcCall.compile();

		// Map<String, Object> in = new HashMap<String, Object>();

		int result = 1;
		Map<String, Object> results = jdbcCall.execute();
		if (results.containsKey(Constants.RESULT)) {
			result = ((BigDecimal) results.get(Constants.RESULT)).intValue();
		}
		LOG.info(prf + " END");
		return result;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// SERVIZI DI
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ANTEPRIMA///////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////// CREA PROPOSTA
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public EsitoOperazioni creaAnteprimaPropostaCertificazione(Long idUtente,
			PropostaCertificazioneDTO propostaCertificazione, Long[] idLineeDiIntervento)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::creaAnteprimaPropostaCertificazione] ";
		LOG.info(prf + " - BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();

		try {
			aggiustaDescrizioneProposta(propostaCertificazione);
			// PASSO 1 - INSERT SULLA TABELLA PBANDI_T_PROPOSTA_CERTIFICAZ
			PbandiTPropostaCertificazVO vo = new PbandiTPropostaCertificazVO();
			vo.setDescProposta(propostaCertificazione.getDescProposta());
			vo.setDtCutOffErogazioni(new java.sql.Date(propostaCertificazione.getDataErogazioni().getTime()));
			vo.setDtCutOffFideiussioni(new java.sql.Date(propostaCertificazione.getDataFideiussioni().getTime()));
			vo.setDtCutOffPagamenti(new java.sql.Date(propostaCertificazione.getDataPagamenti().getTime()));
			vo.setDtCutOffValidazioni(new java.sql.Date(propostaCertificazione.getDataValidazioni().getTime()));
			vo.setIdUtenteIns(beanUtil.transform(idUtente, BigDecimal.class));
			vo.setDtOraCreazione(new java.sql.Date(new GregorianCalendar().getTimeInMillis()));
			String statoProposta = null;
			if (isBozza(propostaCertificazione)) {
				statoProposta = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_PREVIEW;
			} else {
				statoProposta = Constants.STATO_PROPOSTA_CERTIFICAZIONE_PREVIEW;
			}
			vo.setIdStatoPropostaCertif(idStatoDaDescBreve(statoProposta));
			salvaProposta(vo);
			BigDecimal idProposta = getIdPropostaUltima();
			// PASSO 2 - INSERT SULLA TABELLA PBANDI_R_PROPOSTA_CERTIF_LINEA
			LOG.info(prf + " idLineeDiIntervento.length=" + idLineeDiIntervento.length);
			for (Long idLineaDiIntervento : idLineeDiIntervento) {
				PbandiRPropostaCertifLineaVO pbandiRPropostaCertifLineaVO = new PbandiRPropostaCertifLineaVO(
						vo.getIdPropostaCertificaz(), new BigDecimal(idLineaDiIntervento));
				pbandiRPropostaCertifLineaVO.setIdUtenteIns(new BigDecimal(idUtente));
				salvaPropostaLinea(pbandiRPropostaCertifLineaVO);
			}

			// PASSO 3 - CHIAMA LA PROCEDURA MainControlliCertificazione
			long start = System.currentTimeMillis();
			int es = eseguiProceduraCreaAntePrimaProposta(idUtente, idProposta);
			long end = System.currentTimeMillis();
			LOG.info(prf + " PCK_PBANDI_CERTIFICAZIONE.MainControlliCertificazione eseguita in " + (end - start)
					+ " ms");
			LOG.info(prf + " esito plsql : " + es);
			if (es == 0) {
				esito.setEsito(true);
				esito.setMsg(Constants.MSG_CERTIFICAZION_RICHIESTA_SALVATA);
				String[] params = new String[1];
				params[0] = beanUtil.transform(idProposta, String.class);
				esito.setParams(params);
			} else {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERRORE_SERVER);
				LOG.error("Esecuzione plsql fallita.");
			}
		} catch (Exception ex) {
			LOG.error("Errore durante nella creazione proposta certificazione: " + ex);
			throw new CertificazioneException("Errore durante nella creazione proposta certificazione: " + ex);
		}
		LOG.info(prf + " - END");
		return esito;
	}

	private int eseguiProceduraCreaAntePrimaProposta(Long idUtente, BigDecimal idProposta) {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraCreaAntePrimaProposta]";
		LOG.info(prf + " START");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME)
					.withProcedureName("MainControlliCertificazione").withCatalogName("PCK_PBANDI_CERTIFICAZIONE")
					.withoutProcedureColumnMetaDataAccess();

			jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
			jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, Types.NUMERIC));
			jdbcCall.setFunction(true);
			jdbcCall.compile();

			// SqlParameterSource in = new MapSqlParameterSource().addValue(P_ID_UTENTE,
			// BigDecimal.valueOf(idUtente));
			Map<String, Object> input = new HashMap<>();
			input.put(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, idProposta);
			Map<String, Object> out = jdbcCall.execute(input);

			int procedureResult = 1;
			if (out.containsKey("result")) {
				procedureResult = ((BigDecimal) out.get("result")).intValue();
			}
			LOG.info(prf + " END");
			return procedureResult;
		} catch (Exception ex) {
			LOG.error(prf + " Errore durante nella creazione proposta certificazione: " + ex);
			throw ex;
		}
	}

	private void salvaPropostaLinea(PbandiRPropostaCertifLineaVO vo) throws Exception {
		String prf = "[ CertificazioneDaoImpl :: salvaPropostaLinea ]";
		LOG.info(prf + " BEGIN");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_R_PROPOSTA_CERTIF_LINEA ");
			sql.append("(ID_PROPOSTA_CERTIFICAZ,ID_LINEA_DI_INTERVENTO,ID_UTENTE_INS,ID_UTENTE_AGG)");
			sql.append("VALUES (?,?,?,?)");

			Object[] params = new Object[4];
			params[0] = getIdPropostaUltima();
			params[1] = vo.getIdLineaDiIntervento();
			params[2] = vo.getIdUtenteIns();
			params[3] = vo.getIdUtenteAgg();

			getJdbcTemplate().update(sql.toString(), params);

			LOG.info(prf + "inserito record in PBANDI_R_PROPOSTA_CERTIF_LINEA con id = " + params[0]);

			LOG.info(prf + " END");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new Exception(e.getMessage());
		}

	}

	private Boolean salvaProposta(PbandiTPropostaCertificazVO vo) throws Exception {
		String prf = "[ CertificazioneDaoImpl :: salvaProposta ]";
		LOG.info(prf + " BEGIN");
		boolean esito = true;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_PROPOSTA_CERTIFICAZ ");
			sql.append(
					"(ID_PROPOSTA_CERTIFICAZ, DT_ORA_CREAZIONE,DESC_PROPOSTA,DT_CUT_OFF_PAGAMENTI,DT_CUT_OFF_VALIDAZIONI,DT_CUT_OFF_EROGAZIONI,");
			sql.append(
					"DT_CUT_OFF_FIDEIUSSIONI,ID_STATO_PROPOSTA_CERTIF,ID_UTENTE_INS,ID_UTENTE_AGG,ID_PROPOSTA_PREC) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			LOG.info(prf + "\n" + sql);

			Object[] params = new Object[11];
			params[0] = nuovoIdPropostaCertificaz();
			params[1] = vo.getDtOraCreazione();
			params[2] = vo.getDescProposta();
			params[3] = vo.getDtCutOffPagamenti();
			params[4] = vo.getDtCutOffValidazioni();
			params[5] = vo.getDtCutOffErogazioni();
			params[6] = vo.getDtCutOffFideiussioni();
			params[7] = vo.getIdStatoPropostaCertif();
			params[8] = vo.getIdUtenteIns();
			params[9] = vo.getIdUtenteAgg();
			params[10] = vo.getIdPropostaPrec();

			getJdbcTemplate().update(sql.toString(), params);

			LOG.info(prf + "inserito record in PBANDI_T_PROPOSTA_CERTIFICAZ con id = " + params[0]);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new Exception(e.getMessage());
		}
		return esito;
	}

	private void aggiustaDescrizioneProposta(PropostaCertificazioneDTO propostaCertificazione) {
		if (StringUtil.isEmpty(propostaCertificazione.getDescProposta())) {
			propostaCertificazione.setDescProposta(" ");
		}
	}

	private boolean isBozza(PropostaCertificazioneDTO propostaCertificazione) {
		return propostaCertificazione.getIsBozza() != null && propostaCertificazione.getIsBozza();
	}

	public BigDecimal getIdPropostaUltima() {
		String sql = "SELECT MAX(ID_PROPOSTA_CERTIFICAZ) FROM PBANDI_T_PROPOSTA_CERTIFICAZ";
		BigDecimal idProposta = getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		return idProposta;
	}

	@Override
	@Transactional
	public Boolean checkProceduraAggiornamentoTerminata() throws Exception {
		String prf = "[ CertificazioneDaoImpl :: checkProceduraAggiornamentoTerminata ]";
		LOG.info(prf + " BEGIN");
		try {
			StringBuilder sql1 = new StringBuilder();
			sql1.append(
					"select DESC_BATCH as descBatch , ID_NOME_BATCH as idNomeBatch , NOME_BATCH as nomeBatch from PBANDI_D_NOME_BATCH where NOME_BATCH = '")
					.append(Constants.NOME_BATCH_AGGIORNA_DATI_INTERMEDIA).append("'");

			PbandiDNomeBatchVO vo = getJdbcTemplate().queryForObject(sql1.toString(), new NomePathRowMapper());

			String sql = fileSqlUtil.getQuery("ProcessoBatch.sql");
			Object[] params = new Object[] { vo.getIdNomeBatch() };
			List<PbandiLProcessoBatchVO> processi = getJdbcTemplate().query(sql, params, new ProcessoBatchRowMapper());
			if (processi != null && !processi.isEmpty()) {
				return (processi.get(0).getDtFineElaborazione() != null);
			}

			LOG.info(prf + " END");
			return Boolean.FALSE;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new Exception(e.getMessage());
		}

	}

	@Override
	@Transactional
	public List<BandoLineaDTO> findBandoLineaPerAnteprima(Long idProposta) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findBandoLineaPerAnteprima]";
		LOG.info(prf + " - BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("PreviewBandoPropCer.sql");
			Object[] params = new Object[] { idProposta };

			List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql, params,
					new PreviewBandoDettPropCerRowMaper());

			LOG.info(prf + " - END");
			return beanUtil.transformList(vo, BandoLineaDTO.class);
		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<BeneficiarioDTO> findBeneficiariPerAnteprima(Long idProposta, Long progrBandoLineaIntervento)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findBeneficiariPerAnteprima]";
		LOG.info(prf + " - BEGIN");
		try {

			String sql = fileSqlUtil.getQuery("PreviewBeneficiariPropCer.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);
			if (progrBandoLineaIntervento == null) {
				sql1.append("ORDER BY DENOMINAZIONE_BENEFICIARIO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new PreviewBeneficiariDettPropCerRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, BeneficiarioDTO.class);
			} else {

				sql1.append(" AND PROGR_BANDO_LINEA_INTERVENTO = ").append(progrBandoLineaIntervento)
						.append(" ORDER BY DENOMINAZIONE_BENEFICIARIO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new PreviewBeneficiariDettPropCerRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, BeneficiarioDTO.class);
			}

		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<ProgettoDTO> findProgettiPerAnteprima(Long idProposta, Long progrBandoLineaIntervento,
			Long idBeneficiario) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl :: findProgettiPerAnteprima]";
		LOG.info(prf + " - BEGIN");
		try {

			String sql = fileSqlUtil.getQuery("PreviewProgettiPropCer.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);
			if (progrBandoLineaIntervento != null && idBeneficiario != null) {
				// CASO 1 - Entrambi progrBandoLineaIntervento e idBeneficiario non sono null
				LOG.info(
						prf + " CASO 1 - Entrambi progrBandoLineaIntervento e denominazioneBeneficiario non sono null");

				sql1.append(" AND PROGR_BANDO_LINEA_INTERVENTO = ").append(progrBandoLineaIntervento)
						.append("AND ID_SOGGETTO_BENEFICIARIO = ").append(idBeneficiario)
						.append(" ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new PreviewProgettiDettPropCerRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);

			} else if (progrBandoLineaIntervento != null && idBeneficiario == null) {
				// CASO 2 - nomeBandoLinea no è null

				sql1.append(" AND PROGR_BANDO_LINEA_INTERVENTO = ").append(progrBandoLineaIntervento)
						.append("  ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new PreviewProgettiDettPropCerRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);
			} else if (progrBandoLineaIntervento == null && idBeneficiario != null) {
				// CASO 3 - denominazioneBeneficiario no è null
				sql1.append(" AND ID_SOGGETTO_BENEFICIARIO = ").append(idBeneficiario)
						.append("  ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new PreviewProgettiDettPropCerRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);
			} else {
				// CASO 4 -Restituisce tutti progetti
				LOG.info(prf + "CASO 4 -Restituisce tutti progetti per anteprima");

				sql1.append(" ORDER BY CODICE_PROGETTO ASC");
				Object[] params = new Object[] { idProposta };
				List<PbandiTPreviewDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
						new PreviewProgettiDettPropCerRowMapper());
				LOG.info(prf + " - END");
				return beanUtil.transformList(vo, ProgettoDTO.class);
			}

		} catch (Exception e) {
			throw new CertificazioneException(prf + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<LineaDiInterventoDTO> findLineeDiInterventoDisponibili(Long idSoggetto, String codiceRuolo)
			throws FileNotFoundException, IOException, CertificazioneException {
		String prf = "[CertificazioneDaoImpl::findLineeDiInterventoDisponibili]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("LineeInterventoDisponibili.sql");
			List<PbandiDLineaDiInterventoVO> vo = getJdbcTemplate().query(sql, new LineeInterventoRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(vo, LineaDiInterventoDTO.class);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<PropostaProgettoDTO> findAntePrimaProposta(Long idProposta, Long progrBandoLineaIntervento,
			Long idBeneficiario, Long idProgetto, Long idLineaDiIntervento) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::findAntePrimaProposta]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("AnteprimaProposta.sql");
			StringBuilder sql1 = new StringBuilder();
			sql1.append(sql);
			Object[] params = new Object[] { idProposta, idProposta };

			if (progrBandoLineaIntervento != null) {
				sql1.append(" AND PROGR_BANDO_LINEA_INTERVENTO = ").append(progrBandoLineaIntervento);
			}
			if (idBeneficiario != null) {
				sql1.append(" AND ID_SOGGETTO_BENEFICIARIO = ").append(idBeneficiario);
			}
			if (idProgetto != null) {
				sql1.append(" AND ID_PROGETTO = ").append(idProgetto);
			}
			if (idLineaDiIntervento != null) {
				sql1.append(" AND ID_LINEA_DI_INTERVENTO = ").append(idLineaDiIntervento);
			}
			List<AnteprimaDettPropCerVO> vo = getJdbcTemplate().query(sql1.toString(), params,
					new AnteprimaDettPropCerRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(vo, PropostaProgettoDTO.class);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni sospendiProgettiProposta(Long idUtente, Long[] idsPreviewDettPropCer)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::sospendiProgettiProposta]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("SospendiProgetti.sql");
			EsitoOperazioni esito = new EsitoOperazioni();
			int errorCount = 0;
			for (Long idPreview : idsPreviewDettPropCer) {
				Object[] params = new Object[] { idUtente, idPreview };
				int res = getJdbcTemplate().update(sql, params);
				if (res == 0)
					errorCount++;
			}
			if (errorCount > 0) {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERRORE_SERVER);
			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_SOSPENSIONI_PROGETTI_SUCCESSO);
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	//Questa funzione e' simile alla precedente ma opera su un'altra tabella nella quale l'operazione deve anche essere fatta
	@Override
	@Transactional
	public EsitoOperazioni sospendiProgettiPropostaRev(Long idUtente, Long[] idsPreviewDettPropCer)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::sospendiProgettiProposta]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("SospendiProgettiRev.sql");
			EsitoOperazioni esito = new EsitoOperazioni();
			int errorCount = 0;
			for (Long idPreview : idsPreviewDettPropCer) {
				Object[] params = new Object[] { idUtente, idPreview };
				int res = getJdbcTemplate().update(sql, params);
				if (res == 0)
					errorCount++;
			}
			if (errorCount > 0) {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERRORE_SERVER);
			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_SOSPENSIONI_PROGETTI_SUCCESSO);
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni ammettiProgettiProposta(Long idUtente, Long[] idsPreviewDettPropCer)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::ammettiProgettiProposta]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("AmmettiProgetti.sql");
			EsitoOperazioni esito = new EsitoOperazioni();
			int errorCount = 0;
			for (Long idPreview : idsPreviewDettPropCer) {
				Object[] params = new Object[] { idUtente, idPreview };
				int res = getJdbcTemplate().update(sql, params);
				if (res == 0)
					errorCount++;
			}
			if (errorCount > 0) {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERRORE_SERVER);
			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_AMMISSIONI_PROGETTI_SUCCESSO);
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni ammettiProgettiPropostaRev(Long idUtente, Long[] idsPreviewDettPropCer)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::ammettiProgettiProposta]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("AmmettiProgettiRev.sql");
			EsitoOperazioni esito = new EsitoOperazioni();
			int errorCount = 0;
			for (Long idPreview : idsPreviewDettPropCer) {
				Object[] params = new Object[] { idUtente, idPreview };
				int res = getJdbcTemplate().update(sql, params);
				if (res == 0)
					errorCount++;
			}
			if (errorCount > 0) {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERRORE_SERVER);
			} else {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.MSG_AMMISSIONI_PROGETTI_SUCCESSO);
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni accodaPropostaCertificazione(Long idUtente, PropostaCertificazioneDTO proposta)
			throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::accodaPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoOperazioni esito = new EsitoOperazioni();

			if (!isProposteInCoda()) {
				String statoProposta = null;
				if (isBozza(proposta)) {
					statoProposta = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_CODA;
				} else {
					statoProposta = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_CODA;
				}
				BigDecimal idStato = idStatoDaDescBreve(statoProposta);
				LOG.info("statoProposta=" + statoProposta);
				String sql = fileSqlUtil.getQuery("AccodaProposta.sql");
				Object[] params = new Object[] { idStato, idUtente, proposta.getIdPropostaCertificaz() };
				getJdbcTemplate().update(sql, params);
				esito.setEsito(true);
				// esito.setMsg(ErrorMessages.ERRORE_CERTIFICAZIONE_PROPOSTA_GIA_PRESENTE);
			} else {
				esito.setEsito(false);
				esito.setMsg(ErrorMessages.ERRORE_CERTIFICAZIONE_PROPOSTA_GIA_PRESENTE);
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	private boolean isProposteInCoda() {
		String codiceStatoInCoda = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_CODA;
		String codiceStatoInCodaBozza = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_CODA;
		String codiceStatoInEsecuzione = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_ELABORAZIONE;
		String codiceStatoInEsecuzioneBozza = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_ELABORAZIONE;

		BigDecimal idStatoInCoda = idStatoDaDescBreve(codiceStatoInCoda);
		BigDecimal idStatoInCodaBozza = idStatoDaDescBreve(codiceStatoInCodaBozza);
		BigDecimal idStatoInEsecuzione = idStatoDaDescBreve(codiceStatoInEsecuzione);
		BigDecimal idStatoInEsecuzioneBozza = idStatoDaDescBreve(codiceStatoInEsecuzioneBozza);

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT COUNT(*) FROM PBANDI_T_PROPOSTA_CERTIFICAZ WHERE ID_STATO_PROPOSTA_CERTIF IN (? , ? , ? , ?)");

		Object[] params = new Object[] { idStatoInCoda, idStatoInCodaBozza, idStatoInEsecuzione,
				idStatoInEsecuzioneBozza };
		Integer res = getJdbcTemplate().queryForObject(sql.toString(), params, Integer.class);
		if (res > 0)
			return true;
		return false;

	}

	@Override
	// Rimuovo l'annotation di transazionalita' e imposto il metodo come asyncrono
	// per un test
	@Async
	public EsitoOperazioni lanciaCreazioneProposta(Long idUtente, PropostaCertificazioneDTO proposta,
			Long[] idLineeDiIntervento) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::lanciaCreazioneProposta]";
		LOG.info(prf + " BEGIN");
		try {

			if (idLineeDiIntervento == null)
				LOG.info("idLineeDiIntervento = NULL");
			else {
				LOG.info("idLineeDiIntervento.size = " + idLineeDiIntervento.length);
				for (Long l : idLineeDiIntervento)
					LOG.info("  idLinea = " + l);
			}

			String idIride = "idIrideFinto";

			it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO propostaCertificazioneSrv;
			propostaCertificazioneSrv = beanUtil.transform(proposta,
					it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO.class);

			certificazioneBusinessImpl.lanciaCreazionePropostaCertificazione(idUtente, idIride,
					propostaCertificazioneSrv, idLineeDiIntervento);

			EsitoOperazioni esito = new EsitoOperazioni();
			esito.setEsito(true);

			return esito;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	// Servizio fatto ex-novo da Fassil; sostituito con un altro servizo
	// che richiama il codice di pbandisrv
	// certificazioneBusinessImpl.lanciaCreazionePropostaCertificazione().
	// @Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public EsitoOperazioni lanciaCreazionePropostaOLD(Long idUtente, PropostaCertificazioneDTO proposta,
			Long[] idLineeDiIntervento) throws CertificazioneException {
		String prf = "[CertificazioneDaoImpl::lanciaCreazioneProposta]";
		LOG.info(prf + " BEGIN");
		try {
			// just in case...
			aggiustaDescrizioneProposta(proposta);
			EsitoOperazioni esito = new EsitoOperazioni();
			String nuovoStato = null;
			LOG.info(prf + "idProposta = " + proposta.getIdPropostaCertificaz());
			if (isBozza(proposta)) {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_CODA;
			} else {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_CODA;
			}
			LOG.info(prf + " nuovoStato=" + nuovoStato);
			BigDecimal idStatoNuovo = idStatoDaDescBreve(nuovoStato);
			String sql = fileSqlUtil.getQuery("Proposta.sql");
			Object[] params = new Object[1];
			params[0] = proposta.getIdPropostaCertificaz();
			PropostaCertificazioneVO propostaVO = getJdbcTemplate().queryForObject(sql, params,
					new PropostaCertificazioneRowMapper());
			propostaVO.setDtOraCreazione(new java.sql.Date(new java.util.Date().getTime()));

			if (isBozza(proposta)) {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_ELABORAZIONE;
			} else {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_ELABORAZIONE;
			}

			aggiornaStatoPropostaCertificazione(nuovoStato, propostaVO, idUtente);
			LOG.info(prf + " aggiornaStatoPropostaCertificazione OK 1 ");

			eseguiProceduraCreazioneProposta(propostaVO);
			LOG.info(prf + " eseguiProceduraCreazioneProposta ");

			// imposto la proposta come aperta, l'elaborazione ï¿½ terminata con
			// successo
			if (isBozza(proposta)) {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA;
			} else {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_APERTA;
			}

			aggiornaStatoPropostaCertificazione(nuovoStato, propostaVO, idUtente);
			LOG.info(prf + "aggiornaStatoPropostaCertificazione OK 2 ");

			MailVO mailVO = new MailVO();
			mailVO.setSubject("Esito creazione proposta certificazione num. " + proposta.getIdPropostaCertificaz()
					+ (isBozza(proposta) ? " (bozza)" : ""));
			mailVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
			mailVO.setToAddresses(
					caricaDestinatariEmailNotifica(proposta.getIdPropostaCertificaz(), idLineeDiIntervento));
//			mailVO.setCcAddresses(getAssistenzaEmailAddress());

			try {
				LOG.info(prf + "propostaCertificazVO=" + propostaVO.toString());
				LOG.info(prf + "propostaCertificazione=" + propostaVO.toString());
				LOG.info(prf + "idUtente=" + idUtente);
				LOG.info(prf + "mailVO=" + mailVO.toString());
				LOG.info(prf + "idLineeDiIntervento=" + idLineeDiIntervento);

				creaESalvaReportPropostaCertificazione(idUtente, proposta, propostaVO, mailVO, idLineeDiIntervento,
						false);

			} catch (Exception e) {
				LOG.error(
						prf + "Errore nella creazione del report per la proposta " + proposta.getIdPropostaCertificaz()
								+ ", impossibile inviarla. Verificare il log applicativo. Causa: " + e.getMessage(),
						e);
				throw new CertificazioneException(
						prf + " Errore nella creazione del report per la proposta " + proposta.getIdPropostaCertificaz()
								+ ", impossibile inviarla. Verificare il log applicativo. Causa: " + e.getMessage());
			}

			LOG.info(prf + "\n\n\n++++++++++++++++++++++++++++before mailDAO.send++++++++++++++++++++++++++++\n\n");
			mailUtil.send(mailVO);
			LOG.info(prf + "\n\n\n++++++++++++++++++++++++++++after mailDAO.send++++++++++++++++++++++++++++\n\n");

			////////////////////////// INIZIO ELABORAZIONE REV. //////////////////////////
			/////////////////////////////////////////////////////////////////////////////
			String destinatariReportRev = leggiCostanteNonObbligatoria("indirizzoEmailReportCertificazioneRev");
			if (StringUtil.isEmpty(destinatariReportRev)) {
				LOG.info(" ");
				LOG.info(prf + " Salto la generazione del REPORT CERTIFICAZIONE REV.");
				LOG.info(" ");
			} else {
				try {
					LOG.info(prf + " Inizio la generazione del REPORT CERTIFICAZIONE REV.");

					// Passa da una stringa di mail separate da virgola ad una lista di stringhe.
					List<String> separatedMailAddresses = new ArrayList<String>();
					for (String mailAddress : destinatariReportRev.split(Constants.MAIL_ADDRESSES_SEPARATOR)) {
						separatedMailAddresses.add(mailAddress.trim());
					}

					LOG.info(prf
							+ " INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE_REV.MainCreaPropostaCertificazione()");
					BigDecimal idPropostaCertificaz = beanUtil.transform(proposta.getIdPropostaCertificaz(),
							BigDecimal.class);
					LOG.info(prf + " Parametro pIdPropostaCertificaz = " + idPropostaCertificaz);
					if (!eseguiProceduraCreaPropostaCertificazioneRev(idPropostaCertificaz)) {
						LOG.error(prf
								+ " lanciaCreazionePropostaCertificazione(): PCK_PBANDI_CERTIFICAZIONE_REV.MainCreaPropostaCertificazione() ha restituito un codice di errore.");
					} else {
						MailVO mailRevVO = new MailVO();
						mailRevVO.setSubject(
								"Esito creazione proposta certificazione num. " + propostaVO.getIdPropostaCertificaz()
										+ (isBozza(proposta) ? " (bozza)" : "") + " Revisione");
						mailRevVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
						mailRevVO.setToAddresses(separatedMailAddresses);
//						mailRevVO.setCcAddresses(getAssistenzaEmailAddress());

						// Cambia i sinonimi per far puntare alle tabelle REV.
//						LOG.info(prf + " INVOCAZIONE FUNZIONE PLSQL PR_SW_SYNONYM_REV_CERTIF");
//						if (!eseguiProceduraCambiaSinonimiCertificazioneRev()) {
//							LOG.error(prf
//									+ " lanciaCreazionePropostaCertificazione(): PR_SW_SYNONYM_REV_CERTIF() ha restituito un codice di errore: SINONIMI NON MODIFICATI.");
//						} else {

							try {
								creaESalvaReportPropostaCertificazioneREV(idUtente, proposta, propostaVO, mailRevVO,
										idLineeDiIntervento, false);

							} catch (Exception e) {
								LOG.error(prf + "Errore nella creazione del report REV per la proposta "
										+ proposta.getIdPropostaCertificaz()
										+ ", impossibile inviarla. Verificare il log applicativo. Causa: "
										+ e.getMessage(), e);
							}

							LOG.info(prf
									+ "\n\n\n++++++++++++++++++++++++++++before mailUtil.send REV++++++++++++++++++++++++++++\n\n");
							mailUtil.send(mailRevVO);
							LOG.info(prf
									+ "\n\n\n++++++++++++++++++++++++++++after mailUtil.send REV++++++++++++++++++++++++++++\n\n");

						}

//					}

				} catch (Exception e) {
					LOG.error(prf + " lanciaCreazionePropostaCertificazione(): ERRORE nel REPORT CERTIFICAZIONE REV",
							e);
				} finally {

					// Ripristina i sinonimi.
//					LOG.info(prf + " INVOCAZIONE FUNZIONE PLSQL FN_UNDO_SYNONYM_REV_CERTIF()");
//					if (!eseguiProceduraRipristinaSinonimiCertificazioneRev()) {
//						LOG.error(prf
//								+ "lanciaCreazionePropostaCertificazione(): FN_UNDO_SYNONYM_REV_CERTIF() ha restituito un codice di errore: SINONIMI NON RIPRISTINATI.");
//					}

					LOG.info(prf + "Fine la generazione del REPORT CERTIFICAZIONE REV.");
				}
			}

			esito.setEsito(true);
			esito.setMsg(MessageConstants.MSG_PROPOSTA_LANCIATA_SUCCESSO);
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new CertificazioneException(e.getMessage());
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////
	private boolean eseguiProceduraCreaPropostaCertificazioneRev(BigDecimal idPropostaCertificaz) throws Exception {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraCreaPropostaCertificazioneRev]";
		LOG.info(prf + " START");
		try {

			LOG.info(prf + " INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE.EXECUTE");
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME)
					.withProcedureName("MainCreaPropostaCertificazione")
					.withCatalogName("PCK_PBANDI_CERTIFICAZIONE_REV").withoutProcedureColumnMetaDataAccess();

			jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
			jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, Types.NUMERIC));
			jdbcCall.setFunction(true);
			jdbcCall.compile();

			Map<String, Object> input = new HashMap<>();
			input.put(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, idPropostaCertificaz);
			Map<String, Object> out = jdbcCall.execute(input);

			int procedureResult = 1;
			if (out.containsKey("result")) {
				procedureResult = ((BigDecimal) out.get("result")).intValue();
			}
			if (procedureResult != 0) {
				throw new CertificazioneException(prf + " Esecuzione plsql fallita.");
			}
			LOG.info(prf + " FINE INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE_REV.EXECUTE");
			LOG.info(prf + " END");
			return true;
		} catch (Exception ex) {
			LOG.error(prf + " Errore durante nella creazione proposta certificazione Rev: " + ex);
			throw ex;
		}

	}

	private boolean eseguiProceduraRipristinaSinonimiCertificazioneRev() throws Exception {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraRipristinaSinonimiCertificazioneRev]";
		LOG.info(prf + " START");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withFunctionName("FN_UNDO_SYNONYM_REV_CERTIF");

		jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
		jdbcCall.setFunction(true);
		jdbcCall.compile();

		// Map<String, Object> in = new HashMap<String, Object>();

		int result = 1;
		Map<String, Object> results = jdbcCall.execute();
		if (results.containsKey(Constants.RESULT)) {
			result = ((BigDecimal) results.get(Constants.RESULT)).intValue();
		}
		if (result != 0) {
			LOG.info(prf + " FINE INVOCAZIONE PROCEDURA FN_SW_SYNONYM_REV_CERTIF.EXECUTE");
			LOG.info(prf + " END");
			return false;
		}
		LOG.info(prf + " FINE INVOCAZIONE PROCEDURA FN_SW_SYNONYM_REV_CERTIF.EXECUTE");
		LOG.info(prf + " END");
		return true;
//		try {
//			LOG.info(prf +" INVOCAZIONE PROCEDURA FN_UNDO_SYNONYM_REV_CERTIF.EXECUTE");
//			String sql = fileSqlUtil.getQuery("RipristinaSinonimiCertificazioneRev.sql");
//			BigDecimal procedureResult = getJdbcTemplate().queryForObject(sql, BigDecimal.class);
//
//			if(procedureResult.intValue() != 0) {
//				LOG.info(prf + " FINE INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE_REV.EXECUTE");
//				LOG.info(prf + " END");
//				return false;
//			}
//			LOG.info(prf + " FINE INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE_REV.EXECUTE");
//			LOG.info(prf + " END");
//			return true;
//		} catch(Exception ex){
//			LOG.error(prf + " Errore durante nella esecuzione pl/sql - FN_UNDO_SYNONYM_REV_CERTIF: " + ex);
//			throw ex;
//		}

	}

	private boolean eseguiProceduraCambiaSinonimiCertificazioneRev() throws Exception {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraCambiaSinonimiCertificazioneRev]";
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withFunctionName("FN_SW_SYNONYM_REV_CERTIF");

		jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
		jdbcCall.setFunction(true);
		jdbcCall.compile();

		// Map<String, Object> in = new HashMap<String, Object>();

		int result = 1;
		Map<String, Object> results = jdbcCall.execute();
		if (results.containsKey(Constants.RESULT)) {
			result = ((BigDecimal) results.get(Constants.RESULT)).intValue();
		}
		if (result != 0) {
			LOG.info(prf + " FINE INVOCAZIONE PROCEDURA FN_SW_SYNONYM_REV_CERTIF.EXECUTE");
			LOG.info(prf + " END");
			return false;
		}
		LOG.info(prf + " FINE INVOCAZIONE PROCEDURA FN_SW_SYNONYM_REV_CERTIF.EXECUTE");
		LOG.info(prf + " END");
		return true;
//		LOG.info(prf + " START");
//		try {
//			String sql = fileSqlUtil.getQuery("CambiaSinonimiCertificazioneRev.sql");
//			BigDecimal procedureResult = getJdbcTemplate().queryForObject(sql, BigDecimal.class);
//
//			if(procedureResult.intValue() != 0) {
//				LOG.info(prf + " FINE INVOCAZIONE PROCEDURA FN_SW_SYNONYM_REV_CERTIF.EXECUTE");
//				LOG.info(prf + " END");
//				return false;
//			}
//			LOG.info(prf + " FINE INVOCAZIONE PROCEDURA FN_SW_SYNONYM_REV_CERTIF.EXECUTE");
//			LOG.info(prf + " END");
//			return true;
//		} catch(Exception ex){
//			LOG.error(prf + " Errore durante nella esecuzione pl/sql - FN_SW_SYNONYM_REV_CERTIF: " + ex);
//			throw ex;
//		}
	}

	private void creaESalvaReportPropostaCertificazioneREV(Long idUtente, PropostaCertificazioneDTO proposta,
			PropostaCertificazioneVO propostaVO, MailVO mailVO, Long[] idLineeDiIntervento, boolean isPostGestione)
			throws FileNotFoundException, IOException, Exception {
		String prf = "[CertificazioneDaoImpl::creaESalvaReportPropostaCertificazioneREV]";
		LOG.info(prf + " BEGIN");
		BigDecimal idPropostaCertificaz = beanUtil.transform(proposta.getIdPropostaCertificaz(), BigDecimal.class);
		LOG.info(prf + " idPropostaCertificaz=" + idPropostaCertificaz);

		List<AttachmentVO> attachments = new ArrayList<AttachmentVO>();

		if (mailVO.getContent() == null) {
			mailVO.setContent("");
		}

		for (Long idLineaDiIntervento : idLineeDiIntervento) {

			/*
			 * carico il report dal database
			 */
			LOG.info(prf + " idLineaDiIntervento " + idLineaDiIntervento + " ++++");

			String queryElementiReport = fileSqlUtil.getQuery("ReportPropostaCertificazionePorFesr.sql");
			String queryElementiPercettori = fileSqlUtil.getQuery("ReportPercettoriPropostaCertificazionePorFesr.sql");
			Object[] paramsI = new Object[] { beanUtil.transform(idLineaDiIntervento, BigDecimal.class),
					idPropostaCertificaz };

			LOG.info(prf + " Eseguo queryElementiReport con idLineaDiIntervento = " + idLineaDiIntervento
					+ " e idPropostaCertificaz = " + idPropostaCertificaz);
			LOG.info("\n" + queryElementiReport);
			List<ReportPropostaCertificazionePorFesrVO> elementiReport = getJdbcTemplate().query(queryElementiReport,
					paramsI, new ReportPropostaPorFesrRowMapper());

			LOG.info(prf + " Eseguo queryElementiPercettori con idLineaDiIntervento = " + idLineaDiIntervento
					+ " e idPropostaCertificaz = " + idPropostaCertificaz);
			LOG.info("\n" + queryElementiPercettori);
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori = getJdbcTemplate()
					.query(queryElementiPercettori, paramsI, new ReportPropostaPorFesrRowMapper());

			if (elementiReport != null && elementiReport.size() > 0) {
				LOG.info(prf + " num elementiReport = " + elementiReport.size());
			} else {
				LOG.info(prf + " elementiReport vuoto");
			}

			Map<String, ReportPropostaCertificazionePorFesrVO> elementiMap = new HashMap<>();
			for (ReportPropostaCertificazionePorFesrVO r : elementiReport) {
				elementiMap.put(r.getIdProgetto().toString(), r);
			}

			List<ReportPropostaCertificazionePorFesrVO> elementiReportFiltrati = new ArrayList<ReportPropostaCertificazionePorFesrVO>(
					elementiMap.values());

			String queryDescBreve = fileSqlUtil.getQuery("DescBreveDaIdLineaIntervento.sql");

			Object[] params = new Object[] { idLineaDiIntervento };
			String descBreveLinea = getJdbcTemplate().queryForObject(queryDescBreve, params, String.class);
			LOG.info(prf + " lineaDiIntervento=" + descBreveLinea);

			mailVO.setContent(mailVO.getContent() + creaTestoMail(descBreveLinea,
					(elementiReportFiltrati == null || elementiReportFiltrati.size() == 0),
					propostaVO.getDescProposta(), caricaScostamenti(idPropostaCertificaz),
					isScostamentoCompensato(idPropostaCertificaz), caricaProgettiPerCompensazione(idPropostaCertificaz))
					+ "\n\n");

			LOG.info(prf + " mailVO.setContent OK");

			if (elementiReportFiltrati != null && elementiReportFiltrati.size() > 0) {

				byte[] reportPropostaCertificazioneFileData = generaReport(propostaVO, elementiReport, descBreveLinea,
						elementiPercettori);

				if (reportPropostaCertificazioneFileData == null)
					throw new Exception("Il report REV per la certificazione è null");
				LOG.info(prf + "reportPropostaCertificazioneFileData.length = "
						+ reportPropostaCertificazioneFileData.length);

				String postGestione = "";
				if (isPostGestione) {
					postGestione = "PostGestione";
				}
				String nomeFile = "report" + postGestione + propostaVO.getIdPropostaCertificaz() + descBreveLinea
						+ "_REV.xls";
				LOG.info(prf + " nomeFile=" + nomeFile);
				LOG.info(prf + " elementiReport !=null , creo attachment");
				AttachmentVO attach = new AttachmentVO();
				attach.setData(reportPropostaCertificazioneFileData);
				attach.setName(nomeFile);
				attach.setContentType(Constants.MIME_APPLICATION_XSL);

				attachments.add(attach);
				LOG.info(prf + " attachment added to list");

			}

			mailVO.setAttachments(attachments);

			for (AttachmentVO attachment : attachments) {
				// deve essere fatta per ultima, perché non è transazionale
				persistiDocumento(idUtente, attachment.getName(), attachment.getData(), propostaVO);
			}

			LOG.info(prf + "settati attachments");
		}
		LOG.info(prf + "\n\n\n   END creaESalvaReportPropostaCertificazioneREV()\n\n\n");

	}

	private String leggiCostanteNonObbligatoria(String attributo) throws Exception {
		String valore = null;
		try {
			String sql = fileSqlUtil.getQuery("Costanti.sql");
			Object[] params = new Object[] { attributo };
			PbandiCCostantiVO c = getJdbcTemplate().queryForObject(sql, params, new CostantiRowMapper());
			valore = c.getValore();
			LOG.info("Valore della costante " + attributo + " = " + valore);

		} catch (Exception e) {
			LOG.info("Costante " + attributo + " non valorizzata.");
			throw e;
		}
		return valore;
	}

	private void creaESalvaReportPropostaCertificazione(Long idUtente, PropostaCertificazioneDTO proposta,
			PropostaCertificazioneVO propostaVO, MailVO mailVO, Long[] idLineeDiIntervento, boolean isPostGestione)
			throws Exception {
		String prf = "[CertificazioneDaoImpl::creaESalvaReportPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		BigDecimal idPropostaCertificaz = beanUtil.transform(proposta.getIdPropostaCertificaz(), BigDecimal.class);

		logger.info(" idPropostaCertificaz=" + idPropostaCertificaz);

		List<AttachmentVO> attachments = new ArrayList<AttachmentVO>();
		if (mailVO.getContent() == null) {
			mailVO.setContent("");
		}
		for (Long idLineaDiIntervento : idLineeDiIntervento) {
			/*
			 * carico il report dal database
			 */
			LOG.info(prf + " idLineaDiIntervento " + idLineaDiIntervento + " ++++");

			String queryElementiReport = fileSqlUtil.getQuery("ReportPropostaCertificazionePorFesr.sql");
			String queryElementiPercettori = fileSqlUtil.getQuery("ReportPercettoriPropostaCertificazionePorFesr.sql");
			Object[] paramsI = new Object[] { beanUtil.transform(idLineaDiIntervento, BigDecimal.class),
					idPropostaCertificaz };

			LOG.info(prf + " Eseguo queryElementiReport con idLineaDiIntervento = " + idLineaDiIntervento
					+ " e idPropostaCertificaz = " + idPropostaCertificaz);
			LOG.info("\n" + queryElementiReport);
			List<ReportPropostaCertificazionePorFesrVO> elementiReport = getJdbcTemplate().query(queryElementiReport,
					paramsI, new ReportPropostaPorFesrRowMapper());

			LOG.info(prf + " Eseguo queryElementiPercettori con idLineaDiIntervento = " + idLineaDiIntervento
					+ " e idPropostaCertificaz = " + idPropostaCertificaz);
			LOG.info("\n" + queryElementiPercettori);
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori = getJdbcTemplate()
					.query(queryElementiPercettori, paramsI, new ReportPropostaPorFesrRowMapper());

			Map<String, ReportPropostaCertificazionePorFesrVO> elementiMap = new HashMap<>();
			for (ReportPropostaCertificazionePorFesrVO r : elementiReport) {
				elementiMap.put(r.getIdProgetto().toString(), r);
			}
			List<ReportPropostaCertificazionePorFesrVO> elementiReportFiltrati = new ArrayList<ReportPropostaCertificazionePorFesrVO>(
					elementiMap.values());
			LOG.info(prf + "size filtrato : " + elementiReportFiltrati.size());
			String queryDescBreve = fileSqlUtil.getQuery("DescBreveDaIdLineaIntervento.sql");

			Object[] params = new Object[] { idLineaDiIntervento };
			String descBreveLinea = getJdbcTemplate().queryForObject(queryDescBreve, params, String.class);

			LOG.info(prf + "lineaDiIntervento=" + descBreveLinea);
			mailVO.setContent(mailVO.getContent() + creaTestoMail(descBreveLinea,
					(elementiReport == null || elementiReport.size() == 0), propostaVO.getDescProposta(),
					caricaScostamenti(idPropostaCertificaz), isScostamentoCompensato(idPropostaCertificaz),
					caricaProgettiPerCompensazione(idPropostaCertificaz)) + "\n\n");
			LOG.info(prf + " mailVO.setContent OK");

			if (elementiReport != null && elementiReport.size() > 0) {

				byte[] reportPropostaCertificazioneFileData = generaReport(propostaVO, elementiReportFiltrati,
						descBreveLinea, elementiPercettori);

				if (reportPropostaCertificazioneFileData == null)
					throw new Exception("Il report per la certificazione è null");

				String postGestione = "";
				if (isPostGestione) {
					postGestione = "PostGestione";
				}
				String nomeFile = "report" + postGestione + propostaVO.getIdPropostaCertificaz() + descBreveLinea
						+ ".xls";
				LOG.info(prf + " nomeFile=" + nomeFile);
				LOG.info(prf + " elementiReport !=null , creo attachment");
				AttachmentVO attach = new AttachmentVO();
				attach.setData(reportPropostaCertificazioneFileData);
				attach.setName(nomeFile);
				attach.setContentType(Constants.MIME_APPLICATION_XSL);

				attachments.add(attach);
				LOG.info(prf + " attachment added to list");
			}

			mailVO.setAttachments(attachments);

			for (AttachmentVO attachment : attachments) {
				// deve essere fatta per ultima, perché non è transazionale
				persistiDocumento(idUtente, attachment.getName(), attachment.getData(), propostaVO);
			}
			LOG.info(prf + " settati attachments");

		}
		LOG.info(prf + " END");
	}

	private void persistiDocumento(Long idUtente, String nomeDocumento, byte[] data,
			PropostaCertificazioneVO propostaVO) throws Exception {
		String prf = "[CertificazioneDaoImpl::persistiDocumento]";
		LOG.info(prf + " BEGIN");
		try {
			// persisto su filestorage
			Boolean dirEsiste = fileApiServiceImpl
					.dirExists(Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE, true);
			if (dirEsiste) {
				LOG.info(prf + "dirEsiste: " + dirEsiste);
				InputStream inputStream = new ByteArrayInputStream(data);
				fileApiServiceImpl.uploadFile(inputStream, nomeDocumento,
						Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE);
			} else {
				// Se la cartella non viene trovata, deve prima inserirla sotto \PBAN.
				FileApiServiceImpl fileApi = new FileApiServiceImpl(Constants.DIR_PBAN);
				InputStream inputStream = new ByteArrayInputStream(data);
				fileApi.uploadFile(inputStream, nomeDocumento,
						Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE);
			}

			// persisto su db
			String shaHex = null;
			if (data != null)
				shaHex = DigestUtils.shaHex(data);

			LOG.info(prf + " saving reportPropostaCertificazioneFileData on db +++++++++++++++++ ");
			salvaDocumentoIndexSuDb(idUtente, "UUID", nomeDocumento,
					beanUtil.transform(propostaVO.getIdPropostaCertificaz(), Long.class), null, null, null,
					Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE,
					PbandiTPropostaCertificazVO.class, null, shaHex);

			LOG.info(prf + " END");
		} catch (Exception e) {
			throw e;
		}

	}

	private PbandiTDocumentoIndexVO salvaDocumentoIndexSuDb(Long idUtente, String string, String nomeDocumento,
			Long idTarget, Long idRappLegale, Long idDelegato, Long idProgetto, String tipoDocIndex,
			Class<PbandiTPropostaCertificazVO> class1, String codStatoTipoDocIndex, String shaHex) throws Exception {
		String prf = "[CertificazioneDaoImpl::salvaDocumentoIndexSuDb]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " nomeFile: " + nomeDocumento);
		LOG.info(prf + " idTarget: " + idTarget);
		LOG.info(prf + " idRappLegale: " + idRappLegale);
		LOG.info(prf + " idDelegato: " + idDelegato);
		LOG.info(prf + " idProgetto: " + idProgetto);
		LOG.info(prf + " tipoDocIndex: " + tipoDocIndex);
		LOG.info(prf + " codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		LOG.info(prf + " shaHex: " + shaHex);
		BigDecimal idEntita = idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", Constants.ENTITA_C_FILE);
		PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();

		Object[] params = new Object[25];
		try {

			vo.setDtInserimentoIndex(DateUtil.getSysdate());
			vo.setNomeFile(nomeDocumento);
			vo.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			vo.setIdEntita(idEntita);
			vo.setIdTarget(beanUtil.transform(idTarget, BigDecimal.class));
			vo.setIdTipoDocumentoIndex(idTipoIndexDaDescBreve(tipoDocIndex));
			// vo.setVersione(versione);
			vo.setRepository(tipoDocIndex);
			vo.setUuidNodo("UUID");
			if (idRappLegale != null)
				vo.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if (idDelegato != null)
				vo.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			vo.setMessageDigest(shaHex);
			// legge id stato documento index per GENERATO
			String queryStato = "SELECT ID_STATO_DOCUMENTO FROM PBANDI_D_STATO_DOCUMENTO_INDEX WHERE DESC_BREVE = '"
					+ Constants.STATO_DOCUMENTO_INDEX_GENERATO + "'";
			BigDecimal idStato = getJdbcTemplate().queryForObject(queryStato, BigDecimal.class);
			vo.setIdStatoDocumento(idStato);
			vo.setIdUtenteIns(new BigDecimal(idUtente));

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_DOCUMENTO_INDEX ");
			sql.append(
					"(ID_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,DT_INSERIMENTO_INDEX,DT_MARCA_TEMPORALE,DT_VERIFICA_FIRMA,FLAG_FIRMA_CARTACEA,");
			sql.append(
					"ID_CATEG_ANAGRAFICA_MITT,ID_ENTITA,ID_MODELLO,ID_PROGETTO,ID_SOGG_DELEGATO,ID_SOGG_RAPPR_LEGALE,ID_STATO_DOCUMENTO,");
			sql.append(
					"ID_TARGET,ID_TIPO_DOCUMENTO_INDEX,ID_UTENTE_AGG,ID_UTENTE_INS,MESSAGE_DIGEST,NOME_FILE,NOTE_DOCUMENTO_INDEX,");
			sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO)");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			LOG.info("\n" + sql);

			params[0] = nuovoIdTDocumentoIndex();
			params[1] = vo.getDtAggiornamentoIndex();
			params[2] = vo.getDtInserimentoIndex();
			params[3] = vo.getDtMarcaTemporale();
			params[4] = vo.getDtVerificaFirma();
			params[5] = vo.getFlagFirmaCartacea();
			params[6] = vo.getIdCategAnagraficaMitt();
			params[7] = vo.getIdEntita();
			params[8] = vo.getIdModello();
			params[9] = vo.getIdProgetto();
			params[10] = vo.getIdSoggDelegato();
			params[11] = vo.getIdSoggRapprLegale();
			params[12] = vo.getIdStatoDocumento();
			params[13] = vo.getIdTarget();
			params[14] = vo.getIdTipoDocumentoIndex();
			params[15] = vo.getIdUtenteAgg();
			params[16] = vo.getIdUtenteIns();
			params[17] = vo.getMessageDigest();
			params[18] = vo.getNomeFile();
			params[19] = vo.getNoteDocumentoIndex();
			params[20] = vo.getNumProtocollo();
			params[21] = vo.getRepository();
			params[22] = vo.getUuidNodo();
			params[23] = vo.getVersione();
			params[24] = vo.getNomeDocumento();

			getJdbcTemplate().update(sql.toString(), params);

			LOG.info(prf + "inserito record inPBANDI_T_DOCUMENTO_INDEX con id = " + vo.getIdDocumentoIndex());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw new Exception(e.getMessage());
		}

		StringBuilder querySelect = new StringBuilder();
		querySelect
				.append("SELECT r.*, s.* FROM PBANDI_T_DOCUMENTO_INDEX r, PBANDI_C_TIPO_DOCUMENTO_INDEX s "
						+ "WHERE r.ID_TIPO_DOCUMENTO_INDEX = s.ID_TIPO_DOCUMENTO_INDEX AND ID_DOCUMENTO_INDEX= ")
				.append(params[0]);
		PbandiTDocumentoIndexVO vo1 = getJdbcTemplate().queryForObject(querySelect.toString(),
				new DocumentoIndexRowMapper());
		LOG.info(prf + " BEGIN");
		return vo1;

	}

	private byte[] generaReport(PropostaCertificazioneVO propostaVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport, String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception {
		String prf = "[CertificazioneDaoImpl::generaReport]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "descBreveLinea: " + descBreveLinea);

		byte[] reportBytes = null;
		if (Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR.equals(descBreveLinea)) {
			LOG.info(prf + " prima di generaReportPorFesr");

			reportBytes = generaReportPorFesr(propostaVO, elementiReport, descBreveLinea);

		} else if (Constants.LINEA_DI_INTERVENTO_RADICE_PAR_FAS.equals(descBreveLinea)) {
			reportBytes = generaReportParFas(propostaVO, elementiReport, descBreveLinea);

		} else if (Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR_2014_20.equals(descBreveLinea)) {
			LOG.info(prf + " di generaReportPorFesr2014_20");
			reportBytes = generaReportPorFesr2014_20(propostaVO, elementiReport, descBreveLinea, elementiPercettori);
		} else if (Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR_2021_27.equals(descBreveLinea)) {
			LOG.info(prf + " di generaReportPorFesr2021_27");
			reportBytes = generaReportPorFesr2021_27(propostaVO, elementiReport, descBreveLinea, elementiPercettori);
		}
		if (reportBytes != null)
			LOG.info(prf + " reportPropostaCertificazioneFileData : " + Arrays.toString(reportBytes));
		else
			LOG.info(prf + " reportPropostaCertificazioneFileData NULL ");

		LOG.info(" END");
		return reportBytes;
	}

	private byte[] generaReportPorFesr(PropostaCertificazioneVO propostaVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport, String descBreveLinea) throws Exception {
		String prf = "[CertificazioneDaoImpl::generaReportPorFesr]";
		LOG.info(prf + " BEGIN");
		try {
			byte[] reportPropostaCertificazioneFileData;
			// conf/report/xls_templates/reportPropostaCertificazionePorFesr.xls
			String templateKey = Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR;
			LOG.info(prf + "Popolo il template =" + templateKey);

			ArrayList<Long> linesToJump = new ArrayList<Long>();
			linesToJump.add(2L);
			reportPropostaCertificazioneFileData = TableWriter.writeTableToByteArray(templateKey,
					new ExcelDataWriter(propostaVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);

			ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
			LOG.info(prf + "elemento report: " + elemento.getIdProgetto());
			Map<String, Object> testataVars = beanUtil.transformToMap(elemento);
			Map<String, Object> asseVars = new HashMap<String, Object>();

			String queryPorFesrVO = fileSqlUtil.getQuery("LineaInterventoDaDescBreve.sql");
			Object[] params = new Object[] { descBreveLinea };
			PbandiDLineaDiInterventoVO porFesrVO = getJdbcTemplate().queryForObject(queryPorFesrVO, params,
					new LineaInterventoRowMapper());
			asseVars.put("codIgrueT13T14", porFesrVO.getCodIgrueT13T14());

			String queryImportiAnticipi = fileSqlUtil.getQuery("ImportiAnticipoPropostaCertificazione.sql");
			Object[] paramsI = new Object[] { propostaVO.getIdPropostaCertificaz() };
			List<ImportiAnticipoPropostaCertificazioneVO> importiVO = getJdbcTemplate().query(queryImportiAnticipi,
					new ImportiAnticipiPropostaRowMapper());

			Map<String, String> allegatoV = beanUtil.map(importiVO, "descBreveLinea", "importoAnticipo");

			final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";

			Map<String, Object> allegatoVVars = new HashMap<String, Object>();
			for (String key : new String[] { "I", "II", "III", "IV" }) {
				String value = allegatoV.get(key);
				if (value != null) {
					allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, new BigDecimal(value));
				} else {
					allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, new BigDecimal(0));
				}
			}

			Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
			singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);

			Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
			singleCellVars.put("Testata", testataVars);

			// Non più neccessaria per la nuova programmazione
			singleCellVars.put("Per Asse", asseVars);
			singleCellVars.put("Allegato V", allegatoVVars);

			reportPropostaCertificazioneFileData = reportManager.getMergedDocumentFromTemplate(templateKey,
					singlePageTables, singleCellVars);

			LOG.info(prf + " END");
			return reportPropostaCertificazioneFileData;
		} catch (Exception e) {
			LOG.info(prf + " Errore durante la generazione report POR-FESR " + e.getMessage());
			throw e;
		}

	}

	private byte[] generaReportParFas(PropostaCertificazioneVO propostaVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport, String descBreveLinea) throws Exception {
		String prf = "[CertificazioneDaoImpl::generaReportParFas]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "descBreveLinea: " + descBreveLinea);

		final BigDecimal idPropostaCertificaz = propostaVO.getIdPropostaCertificaz();
		LOG.info(prf + " idPropostaCertificaz: " + idPropostaCertificaz);
		byte[] reportPropostaCertificazioneFileData;
		String templateKey = Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_PAR_FAS;
		LOG.info(prf + " Popolo il template " + templateKey);
		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);

		reportPropostaCertificazioneFileData = TableWriter.writeTableToByteArray(templateKey,
				new ExcelDataWriter(idPropostaCertificaz.toString()), elementiReport, linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);

		RipartizioneSpesaValidataParFASVO ripartizioniVO = new RipartizioneSpesaValidataParFASVO();
		ripartizioniVO.setIdPropostaCertificaz(idPropostaCertificaz);
		Map<String, Object> ripartizioni = new HashMap<String, Object>() {
			// le ripartizioni non trovate vanno messe a null
			{
				put("I-1_CPUFAS-STA", null);
				put("I-1_OTHFIN", null);
				put("I-1_CPUFAS-REG", null);
				put("I-2_CPUFAS-STA", null);
				put("I-2_OTHFIN", null);
				put("I-2_CPUFAS-REG", null);
				put("I-3_CPUFAS-STA", null);
				put("I-3_OTHFIN", null);
				put("I-3_CPUFAS-REG", null);
				put("I-4_CPUFAS-STA", null);
				put("I-4_OTHFIN", null);
				put("I-4_CPUFAS-REG", null);
				put("II-1_CPUFAS-STA", null);
				put("II-1_OTHFIN", null);
				put("II-1_CPUFAS-REG", null);
				put("II-2_CPUFAS-STA", null);
				put("II-2_OTHFIN", null);
				put("II-2_CPUFAS-REG", null);
				put("II-3_CPUFAS-STA", null);
				put("II-3_OTHFIN", null);
				put("II-3_CPUFAS-REG", null);
				put("III-1_CPUFAS-STA", null);
				put("III-1_OTHFIN", null);
				put("III-1_CPUFAS-REG", null);
				put("III-2_CPUFAS-STA", null);
				put("III-2_OTHFIN", null);
				put("III-2_CPUFAS-REG", null);
				put("III-3_CPUFAS-STA", null);
				put("III-3_OTHFIN", null);
				put("III-3_CPUFAS-REG", null);
				put("III-4_CPUFAS-STA", null);
				put("III-4_OTHFIN", null);
				put("III-4_CPUFAS-REG", null);
				put("III-5_CPUFAS-STA", null);
				put("III-5_OTHFIN", null);
				put("III-5_CPUFAS-REG", null);
				put("III-6_CPUFAS-STA", null);
				put("III-6_OTHFIN", null);
				put("III-6_CPUFAS-REG", null);
				put("IV-1_CPUFAS-STA", null);
				put("IV-1_OTHFIN", null);
				put("IV-1_CPUFAS-REG", null);
				put("IV-2_CPUFAS-STA", null);
				put("IV-2_OTHFIN", null);
				put("IV-2_CPUFAS-REG", null);
				put("V-511_CPUFAS-STA", null); // modified 17/02/16 pcl, MODIFIED ALSA TEMPLATE XLS
				put("V-511_OTHFIN", null);// modified 17/02/16 pcl
				put("V-511_CPUFAS-REG", null);// modified 17/02/16 pcl
				put("VI-611_CPUFAS-STA", null);
				put("VI-611_OTHFIN", null);
				put("VI-611_CPUFAS-REG", null);
			}
		};
		String queryRipart = fileSqlUtil.getQuery("RipartizioneSpesaValidataParFAS.sql");
		Object[] params = new Object[] { propostaVO.getIdPropostaCertificaz() };
		List<RipartizioneSpesaValidataParFASVO> ripartizioniVO1 = getJdbcTemplate().query(queryRipart, params,
				new RipartizioneSpesaValidataParFASRowMapper());

		for (RipartizioneSpesaValidataParFASVO ripartizione : ripartizioniVO1) {
			ripartizioni.put(ripartizione.getReportKey(), ripartizione.getSpesaValidata());
			LOG.info(prf + " ripartizione.getReportKey(): " + ripartizione.getReportKey()
					+ "  ,ripartizione.getSpesaValidata(): " + ripartizione.getSpesaValidata());
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Per Linea", ripartizioni);

		reportPropostaCertificazioneFileData = reportManager.getMergedDocumentFromTemplate(templateKey,
				singlePageTables, singleCellVars);

		LOG.info(prf + " END");
		return reportPropostaCertificazioneFileData;

	}

	private byte[] generaReportPorFesr2014_20(PropostaCertificazioneVO propostaVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport, String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception {
		String prf = "[CertificazioneDaoImpl::generaReportPorFesr2014_20]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "descBreveLinea: " + descBreveLinea);

		byte[] reportPropostaCertificazioneFileData;
		// conf/report/xls_templates/nReportPropostaCertificazionePorFesr2014_20.xls
		String templateKey = Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2014_20;
		LOG.info(prf + " Popolo il template " + templateKey);

		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		reportPropostaCertificazioneFileData = TableWriter.writeTableToByteArray(templateKey,
				new ExcelDataWriter(propostaVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);

		// Jira PBANDI-2882: foglio Percettoti.
		LOG.info(prf + "\n foglio Percettori \n");
		String templateKeyPercettori = Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI;
		byte[] reportPropostaCertificazioneFileDataPercettori;
		reportPropostaCertificazioneFileDataPercettori = TableWriter.writeTableToByteArray(templateKeyPercettori,
				new ExcelDataWriter(propostaVO.getIdPropostaCertificaz().toString() + "Percettori"), elementiPercettori,
				linesToJump);
		ReportPropostaCertificazionePorFesrVO elementoPercettori = new ReportPropostaCertificazionePorFesrVO();
		if (elementiPercettori.size() > 0) {
			elementoPercettori = elementiPercettori.get(0);
		}
		Map<String, Object> testataVarsPercettori = beanUtil.transformToMap(elementoPercettori);
		// Jira PBANDI-2882: foglio Percettoti - fine.

		PbandiDLineaDiInterventoVO porFesrVO = new PbandiDLineaDiInterventoVO();
		porFesrVO.setDescBreveLinea(descBreveLinea);

		// Importo Anticipo
		ImportiAnticipoPropostaCertificazioneVO importiVO = new ImportiAnticipoPropostaCertificazioneVO();
		importiVO.setIdPropostaCertificaz(propostaVO.getIdPropostaCertificaz());

		// ******* PREFIXES **************************************
		final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";
		final String IMPORTO_NONCOPERTO_PREFIX = "importoNonCoperto";
		final String DIFF_IMPORTI_ANTICIPO_PREFIX = "diffAnticipoImpNonCop";
		final String IMPORTI_PAGVALIDATINETTI_PREFIX = "importoPagValidatiNetti";
		final String IMPORTO_CERTNETTO_PREFIX = "importoCertNetto";

		// Inserire gli assi in una costante
		Map<String, BigDecimal> asseLineaInterventoMap = new HashMap<String, BigDecimal>();
		asseLineaInterventoMap.put("I", Constants.ID_LINEA_DI_INTERVENTO_ASSE_I);
		asseLineaInterventoMap.put("II", Constants.ID_LINEA_DI_INTERVENTO_ASSE_II);
		asseLineaInterventoMap.put("III", Constants.ID_LINEA_DI_INTERVENTO_ASSE_III);
		asseLineaInterventoMap.put("IV", Constants.ID_LINEA_DI_INTERVENTO_ASSE_IV);
		asseLineaInterventoMap.put("V", Constants.ID_LINEA_DI_INTERVENTO_ASSE_V);
		asseLineaInterventoMap.put("VI", Constants.ID_LINEA_DI_INTERVENTO_ASSE_VI);
		asseLineaInterventoMap.put("VII", Constants.ID_LINEA_DI_INTERVENTO_ASSE_VII);

		String queryImportiAnticipi = fileSqlUtil.getQuery("ImportiAnticipoPropostaCertificazione.sql");
		Object[] paramsI = new Object[] { propostaVO.getIdPropostaCertificaz() };
		List<ImportiAnticipoPropostaCertificazioneVO> anticipi = getJdbcTemplate().query(queryImportiAnticipi,
				new ImportiAnticipiPropostaRowMapper());

		Map<String, Object> allegatoVVars = new HashMap<String, Object>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> anticipoMap = new HashMap<String, BigDecimal>();

		for (String key : asseLineaInterventoMap.keySet()) {

			BigDecimal idLineaDiIntervento = asseLineaInterventoMap.get(key);
			BigDecimal value = null;

			for (ImportiAnticipoPropostaCertificazioneVO anticipo : anticipi) {
				if (anticipo.getIdLineaDiIntervento().compareTo(idLineaDiIntervento) == 0) {
					value = anticipo.getImportoAnticipo();
				}
			}

			anticipoMap.put(key, value);
		}

		// cerco i dettagli delle proposte di certificazioni appartenenti a progetti
		// SIFP
		String queryProposteSIF = fileSqlUtil.getQuery("ProposteSIF.sql");
		Object[] paramsII = new Object[] { propostaVO.getIdPropostaCertificaz() };
		List<BigDecimal> proposteSIFList = getJdbcTemplate().query(queryProposteSIF, paramsII,
				new ProposteSIFRowMapper());
		LOG.info(prf + " proposteSIFList=" + proposteSIFList);
		List<BigDecimal> listaII = null;

		// popolo una lista con gli id dei progetti SIF per l'id certificazione dato
		if (proposteSIFList != null && !proposteSIFList.isEmpty()) {

			listaII = new ArrayList<BigDecimal>();
			for (BigDecimal id : proposteSIFList) {
				LOG.info(prf + " ID_PROGETTO= " + id);
				listaII.add(id);
			}
		}

		// impCertNettiMap = sommo colonna AW (ImportiCertificabiliNetti) per Asse
		Map<String, BigDecimal> impCertNettiMap = new HashMap<String, BigDecimal>();

		// impPagValidazMap = somma colonna AE (ImportoPagamentiValidati) per Asse
		// TODO : dubbio : questo valore è corretto o devo prendere
		// ImportoPagValidatiOrig?
		Map<String, BigDecimal> impPagValidazMap = new HashMap<String, BigDecimal>();

		// impEccedValidazMap = somma colonna AF (ImportoEccendenzeValidazione) per Asse
		Map<String, BigDecimal> impEccedValidazMap = new HashMap<String, BigDecimal>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> sommaValoriNMap = new HashMap<String, BigDecimal>();

		for (ReportPropostaCertificazionePorFesrVO el : elementiReport) {

			String asse = el.getDescAsse();

			if (listaII != null && listaII.contains(el.getIdProgetto())) {

				BigDecimal imp = el.getImpCertificabileNettoSoppr();

				LOG.info(prf + " SIF: Asse Progetto IdLineaDiIntervento= [" + asse + "] [" + el.getIdProgetto() + "] ["
						+ el.getIdLineaDiIntervento() + "] ");

				BigDecimal tmpA = impCertNettiMap.get(asse);
				if (tmpA != null) {
					BigDecimal somA = imp.add(tmpA);
					impCertNettiMap.put(asse, somA);
				} else {
					impCertNettiMap.put(asse, imp);
				}

				BigDecimal impB = el.getImportoPagamentiValidati();
				BigDecimal tmpB = impPagValidazMap.get(asse);
				if (tmpB != null) {
					BigDecimal somB = impB.add(tmpB);
					impPagValidazMap.put(asse, somB);
				} else {
					impPagValidazMap.put(asse, impB);
				}

				BigDecimal impC = el.getImportoEccendenzeValidazione();
				BigDecimal tmpC = impEccedValidazMap.get(asse);
				if (tmpB != null) {
					BigDecimal somC = impC.add(tmpC);
					impEccedValidazMap.put(asse, somC);
				} else {
					impEccedValidazMap.put(asse, impC);
				}
			} else {
				LOG.info(prf + " NON SIF: Asse Progetto IdLineaDiIntervento= [" + asse + "] [" + el.getIdProgetto()
						+ "] [" + el.getIdLineaDiIntervento() + "] ");
			}

			// Jira PBANDI-2882: al posto di SommaValoriN ora si usa ValoreN.
			// BigDecimal impD = el.getSommaValoriN();
			BigDecimal impD = el.getValoreN();
			BigDecimal tmpD = sommaValoriNMap.get(asse);
			if (tmpD != null) {
				BigDecimal somD = impD.add(tmpD);
				sommaValoriNMap.put(asse, somD);
				LOG.info(" somD=" + somD);
			} else {
				sommaValoriNMap.put(asse, impD);
			}

		}
		LOG.info(prf + " impCertNettiMap=" + impCertNettiMap);
		LOG.info(prf + " impPagValidazMap=" + impPagValidazMap);
		LOG.info(prf + " impEccedValidazMap=" + impEccedValidazMap);
		LOG.info(prf + " sommaValoriNMap=" + sommaValoriNMap);

		for (String key : new String[] { "I", "II", "III", "IV", "V", "VI", "VII" }) {

			BigDecimal valueSomm = new BigDecimal(0);
			BigDecimal valueAnt = new BigDecimal(0);

			if (sommaValoriNMap != null && sommaValoriNMap.containsKey(key) && sommaValoriNMap.get(key) != null) {
				LOG.info(prf + " asse =" + key + " , sommaValoriNMap.get(key)=" + sommaValoriNMap.get(key));
				valueSomm = valueSomm.add(sommaValoriNMap.get(key));
			}

			if (anticipoMap != null && anticipoMap.containsKey(key) && anticipoMap.get(key) != null) {
				LOG.info(prf + " asse =" + key + " , anticipoMap.get(key)=" + anticipoMap.get(key));
				valueAnt = valueAnt.add(anticipoMap.get(key));
			}

			LOG.info(prf + " asse =" + key + " , valueSomm =" + valueSomm + " , valueAnt=" + valueAnt);

			// valorizzo la colonna "C" = "Importo complessivo versato come anticipo...."
			allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, valueSomm);

			// valorizzo la colonna "E" = "Importo che non e' stato coperto dalle
			// spese....."
			if (valueAnt.compareTo(valueSomm) <= 0) {
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueAnt);
			} else {
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueSomm);
			}

			// valorizzo la colonna "D" = "C" - "E"
			// calcolo la differenza tra IMPORTO_ANTICIPO_PREFIX e IMPORTO_NONCOPERTO_PREFIX
			BigDecimal diffA = new BigDecimal(0);
			BigDecimal diffB = new BigDecimal(0);

			if (allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key) != null)
				diffA = diffA.add((BigDecimal) allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key));

			if (allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key) != null)
				diffB = diffB.add((BigDecimal) allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key));

			BigDecimal diffe = diffA.subtract(diffB);
			LOG.info(prf + " asse =" + key + " , diffA=" + diffA + " ,diffB =" + diffB + " , diffe=" + diffe);
			allegatoVVars.put(DIFF_IMPORTI_ANTICIPO_PREFIX + key, diffe);

			if (impCertNettiMap.get(key) != null) {
				allegatoVVars.put(IMPORTO_CERTNETTO_PREFIX + key, impCertNettiMap.get(key));
			} else {
				allegatoVVars.put(IMPORTO_CERTNETTO_PREFIX + key, new BigDecimal(0));
			}

			// impPagValidazMap = somma colonna AE per Asse
			// impEccedValidazMap = somma colonna AF per Asse

			BigDecimal AE = impPagValidazMap.get(key);
			LOG.info(prf + " AE=" + AE);

			if (AE != null) {
				BigDecimal AF;
				if (impEccedValidazMap.get(key) != null) {
					AF = impEccedValidazMap.get(key);
				} else {
					AF = new BigDecimal(0);
				}
				BigDecimal diff = AE.subtract(AF);
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, diff);
			} else {
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, new BigDecimal(0));
			}
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Allegato V", allegatoVVars);
		// Jira PBANDI-2882.
		singlePageTables.put("Percettori", reportPropostaCertificazioneFileDataPercettori);

		reportPropostaCertificazioneFileData = reportManager.getMergedDocumentFromTemplate(templateKey,
				singlePageTables, singleCellVars);

		LOG.info(prf + " END");
		return reportPropostaCertificazioneFileData;

	}

	private byte[] generaReportPorFesr2021_27(PropostaCertificazioneVO propostaVO,
																						List<ReportPropostaCertificazionePorFesrVO> elementiReport, String descBreveLinea,
																						List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception {
		String prf = "[CertificazioneDaoImpl::generaReportPorFesr2021_27]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "descBreveLinea: " + descBreveLinea);

		byte[] reportPropostaCertificazioneFileData;
		// conf/report/xls_templates/nReportPropostaCertificazionePorFesr2021_27.xls
		String templateKey = Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2021_27;
		LOG.info(prf + " Popolo il template " + templateKey);

		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		reportPropostaCertificazioneFileData = TableWriter.writeTableToByteArray(templateKey,
				new ExcelDataWriter(propostaVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);

		// Jira PBANDI-2882: foglio Percettoti.
		LOG.info(prf + "\n foglio Percettori \n");
		String templateKeyPercettori = Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI;
		byte[] reportPropostaCertificazioneFileDataPercettori;
		reportPropostaCertificazioneFileDataPercettori = TableWriter.writeTableToByteArray(templateKeyPercettori,
				new ExcelDataWriter(propostaVO.getIdPropostaCertificaz().toString() + "Percettori"), elementiPercettori,
				linesToJump);
		ReportPropostaCertificazionePorFesrVO elementoPercettori = new ReportPropostaCertificazionePorFesrVO();
		if (elementiPercettori.size() > 0) {
			elementoPercettori = elementiPercettori.get(0);
		}
		Map<String, Object> testataVarsPercettori = beanUtil.transformToMap(elementoPercettori);
		// Jira PBANDI-2882: foglio Percettoti - fine.

		PbandiDLineaDiInterventoVO porFesrVO = new PbandiDLineaDiInterventoVO();
		porFesrVO.setDescBreveLinea(descBreveLinea);

		// Importo Anticipo
		ImportiAnticipoPropostaCertificazioneVO importiVO = new ImportiAnticipoPropostaCertificazioneVO();
		importiVO.setIdPropostaCertificaz(propostaVO.getIdPropostaCertificaz());

		// ******* PREFIXES **************************************
		final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";
		final String IMPORTO_NONCOPERTO_PREFIX = "importoNonCoperto";
		final String DIFF_IMPORTI_ANTICIPO_PREFIX = "diffAnticipoImpNonCop";
		final String IMPORTI_PAGVALIDATINETTI_PREFIX = "importoPagValidatiNetti";
		final String IMPORTO_CERTNETTO_PREFIX = "importoCertNetto";
		final String IMPORTO_CONTRIBUTIVERSATI_PREFIX = "importoContributiVersati";
		final String IMPORTO_CONTRIBUTOPUBBLICO_PREFIX = "importoContributoPubblico";

		// Inserire gli assi in una costante
		Map<String, BigDecimal> asseLineaInterventoMap = new HashMap<String, BigDecimal>();
		asseLineaInterventoMap.put("PRI", Constants.ID_LINEA_DI_INTERVENTO_ASSE_I);
		asseLineaInterventoMap.put("PRII", Constants.ID_LINEA_DI_INTERVENTO_ASSE_II);
		asseLineaInterventoMap.put("PRIII", Constants.ID_LINEA_DI_INTERVENTO_ASSE_III);
		asseLineaInterventoMap.put("PRIV", Constants.ID_LINEA_DI_INTERVENTO_ASSE_IV);
		asseLineaInterventoMap.put("PRV", Constants.ID_LINEA_DI_INTERVENTO_ASSE_V);
		asseLineaInterventoMap.put("PRVI", Constants.ID_LINEA_DI_INTERVENTO_ASSE_VI);
		asseLineaInterventoMap.put("PRVII", Constants.ID_LINEA_DI_INTERVENTO_ASSE_VII);

		String queryImportiAnticipi = fileSqlUtil.getQuery("ImportiAnticipoPropostaCertificazione.sql");
		String queryImportoNettoAndImportoPubblicoConc = fileSqlUtil.getQuery("GenerazioneExcelCertificazione20212027.sql");

		List<AllegatoVExcel20212027VO> resultQuery20212017 = getJdbcTemplate().query(queryImportoNettoAndImportoPubblicoConc, new AllegatoVExcel20212027RowMapper());

		LOG.debug("QUERY NUOVA 2021 2027:" + resultQuery20212017.toString());

		Object[] paramsI = new Object[] { propostaVO.getIdPropostaCertificaz() };
		List<ImportiAnticipoPropostaCertificazioneVO> anticipi = getJdbcTemplate().query(queryImportiAnticipi,
				new ImportiAnticipiPropostaRowMapper());

		Map<String, Object> allegatoVVars = new HashMap<String, Object>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> anticipoMap = new HashMap<String, BigDecimal>();

		for (String key : asseLineaInterventoMap.keySet()) {

			BigDecimal idLineaDiIntervento = asseLineaInterventoMap.get(key);
			BigDecimal value = null;

			for (ImportiAnticipoPropostaCertificazioneVO anticipo : anticipi) {
				if (anticipo.getIdLineaDiIntervento().compareTo(idLineaDiIntervento) == 0) {
					value = anticipo.getImportoAnticipo();
				}
			}

			anticipoMap.put(key, value);
		}

		// cerco i dettagli delle proposte di certificazioni appartenenti a progetti
		// SIFP
		String queryProposteSIF = fileSqlUtil.getQuery("ProposteSIF.sql");
		Object[] paramsII = new Object[] { propostaVO.getIdPropostaCertificaz() };
		List<BigDecimal> proposteSIFList = getJdbcTemplate().query(queryProposteSIF, paramsII,
				new ProposteSIFRowMapper());
		LOG.info(prf + " proposteSIFList=" + proposteSIFList);
		List<BigDecimal> listaII = null;

		// popolo una lista con gli id dei progetti SIF per l'id certificazione dato
		if (proposteSIFList != null && !proposteSIFList.isEmpty()) {

			listaII = new ArrayList<BigDecimal>();
			for (BigDecimal id : proposteSIFList) {
				LOG.info(prf + " ID_PROGETTO= " + id);
				listaII.add(id);
			}
		}

		// impCertNettiMap = sommo colonna AW (ImportiCertificabiliNetti) per Asse
		// Map<String, BigDecimal> impCertNettiMap = new HashMap<String, BigDecimal>();

		Map<String, BigDecimal> importoCertificazioneNettoMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> contributoPubblicoConcessoMap = new HashMap<String, BigDecimal>();

		for(AllegatoVExcel20212027VO allegatoVExcel20212027VO : resultQuery20212017) {
			String asse = allegatoVExcel20212027VO.getDescAsse();
			importoCertificazioneNettoMap.put(asse, allegatoVExcel20212027VO.getImportoCertificazioneNetto());
			contributoPubblicoConcessoMap.put(asse, allegatoVExcel20212027VO.getImportoCertificazioneNetto());
		}


		// impPagValidazMap = somma colonna AE (ImportoPagamentiValidati) per Asse
		// TODO : dubbio : questo valore è corretto o devo prendere
		// ImportoPagValidatiOrig?
		Map<String, BigDecimal> impPagValidazMap = new HashMap<String, BigDecimal>();

		// impEccedValidazMap = somma colonna AF (ImportoEccendenzeValidazione) per Asse
		Map<String, BigDecimal> impEccedValidazMap = new HashMap<String, BigDecimal>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> sommaValoriNMap = new HashMap<String, BigDecimal>();

		for (ReportPropostaCertificazionePorFesrVO el : elementiReport) {

			String asse = el.getDescAsse();

			if (listaII != null && listaII.contains(el.getIdProgetto())) {

				BigDecimal imp = el.getImpCertificabileNettoSoppr();

				LOG.info(prf + " SIF: Asse Progetto IdLineaDiIntervento= [" + asse + "] [" + el.getIdProgetto() + "] ["
						+ el.getIdLineaDiIntervento() + "] ");

// 			Vecchia logica 2014 2020
//				BigDecimal tmpA = impCertNettiMap.get(asse);
//				if (tmpA != null) {
//					BigDecimal somA = imp.add(tmpA);
//					impCertNettiMap.put(asse, somA);
//				} else {
//					impCertNettiMap.put(asse, imp);
//				}

				BigDecimal impB = el.getImportoPagamentiValidati();
				BigDecimal tmpB = impPagValidazMap.get(asse);
				if (tmpB != null) {
					BigDecimal somB = impB.add(tmpB);
					impPagValidazMap.put(asse, somB);
				} else {
					impPagValidazMap.put(asse, impB);
				}

				BigDecimal impC = el.getImportoEccendenzeValidazione();
				BigDecimal tmpC = impEccedValidazMap.get(asse);
				if (tmpB != null) {
					BigDecimal somC = impC.add(tmpC);
					impEccedValidazMap.put(asse, somC);
				} else {
					impEccedValidazMap.put(asse, impC);
				}
			} else {
				LOG.info(prf + " NON SIF: Asse Progetto IdLineaDiIntervento= [" + asse + "] [" + el.getIdProgetto()
						+ "] [" + el.getIdLineaDiIntervento() + "] ");
			}

			// Jira PBANDI-2882: al posto di SommaValoriN ora si usa ValoreN.
			// BigDecimal impD = el.getSommaValoriN();
			BigDecimal impD = el.getValoreN();
			BigDecimal tmpD = sommaValoriNMap.get(asse);
			if (tmpD != null) {
				BigDecimal somD = impD.add(tmpD);
				sommaValoriNMap.put(asse, somD);
				LOG.info(" somD=" + somD);
			} else {
				sommaValoriNMap.put(asse, impD);
			}

		}
		LOG.info(prf + " contributoPubblicoConcessoMap=" + contributoPubblicoConcessoMap);
		LOG.info(prf + " importoCertificazioneNettoMap=" + importoCertificazioneNettoMap);
		LOG.info(prf + " impPagValidazMap=" + impPagValidazMap);
		LOG.info(prf + " impEccedValidazMap=" + impEccedValidazMap);
		LOG.info(prf + " sommaValoriNMap=" + sommaValoriNMap);

		for (String key : new String[] { "I", "II", "III", "IV", "V", "VI", "VII" }) {

			BigDecimal valueSomm = new BigDecimal(0);
			BigDecimal valueAnt = new BigDecimal(0);

			if (sommaValoriNMap != null && sommaValoriNMap.containsKey(key) && sommaValoriNMap.get(key) != null) {
				LOG.info(prf + " asse =" + key + " , sommaValoriNMap.get(key)=" + sommaValoriNMap.get(key));
				valueSomm = valueSomm.add(sommaValoriNMap.get(key));
			}

			if (anticipoMap != null && anticipoMap.containsKey(key) && anticipoMap.get(key) != null) {
				LOG.info(prf + " asse =" + key + " , anticipoMap.get(key)=" + anticipoMap.get(key));
				valueAnt = valueAnt.add(anticipoMap.get(key));
			}

			LOG.info(prf + " asse =" + key + " , valueSomm =" + valueSomm + " , valueAnt=" + valueAnt);

			// valorizzo la colonna "C" = "Importo complessivo versato come anticipo...."
			allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, valueSomm);

			// valorizzo la colonna "E" = "Importo che non e' stato coperto dalle
			// spese....."
			if (valueAnt.compareTo(valueSomm) <= 0) {
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueAnt);
			} else {
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueSomm);
			}

			// valorizzo la colonna "D" = "C" - "E"
			// calcolo la differenza tra IMPORTO_ANTICIPO_PREFIX e IMPORTO_NONCOPERTO_PREFIX
			BigDecimal diffA = new BigDecimal(0);
			BigDecimal diffB = new BigDecimal(0);

			if (allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key) != null)
				diffA = diffA.add((BigDecimal) allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key));

			if (allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key) != null)
				diffB = diffB.add((BigDecimal) allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key));

			BigDecimal diffe = diffA.subtract(diffB);
			LOG.info(prf + " asse =" + key + " , diffA=" + diffA + " ,diffB =" + diffB + " , diffe=" + diffe);
			allegatoVVars.put(DIFF_IMPORTI_ANTICIPO_PREFIX + key, diffe);

			//Sostituzione placeholders con nuovi valori certificazione 2021 2027
			if (importoCertificazioneNettoMap.get(key) != null) {
				allegatoVVars.put(IMPORTO_CONTRIBUTIVERSATI_PREFIX + key, importoCertificazioneNettoMap.get(key));
			} else {
				allegatoVVars.put(IMPORTO_CONTRIBUTIVERSATI_PREFIX + key, new BigDecimal(0));
			}

			if (contributoPubblicoConcessoMap.get(key) != null) {
				allegatoVVars.put(IMPORTO_CONTRIBUTOPUBBLICO_PREFIX + key, contributoPubblicoConcessoMap.get(key));
			} else {
				allegatoVVars.put(IMPORTO_CONTRIBUTOPUBBLICO_PREFIX  + key, new BigDecimal(0));
			}

			// impPagValidazMap = somma colonna AE per Asse
			// impEccedValidazMap = somma colonna AF per Asse

			BigDecimal AE = impPagValidazMap.get(key);
			LOG.info(prf + " AE=" + AE);

			if (AE != null) {
				BigDecimal AF;
				if (impEccedValidazMap.get(key) != null) {
					AF = impEccedValidazMap.get(key);
				} else {
					AF = new BigDecimal(0);
				}
				BigDecimal diff = AE.subtract(AF);
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, diff);
			} else {
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, new BigDecimal(0));
			}
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Allegato V", allegatoVVars);
		// Jira PBANDI-2882.
		singlePageTables.put("Percettori", reportPropostaCertificazioneFileDataPercettori);

		reportPropostaCertificazioneFileData = reportManager.getMergedDocumentFromTemplate(templateKey,
				singlePageTables, singleCellVars);

		LOG.info(prf + " END");
		return reportPropostaCertificazioneFileData;
	}
	private String creaTestoMail(String descBreveLinea, boolean reportNotEmpty, String descProposta,
			List<ScostamentoAssePropostaCertificazioneVO> scostamenti, boolean scostamentoCompensato,
			List<ProgettoPerCompensazioneVO> progettiPerCompensazione) throws Exception {
		StringBuilder message = new StringBuilder();
		descProposta = descProposta == null ? "" : descProposta;

		if (reportNotEmpty) {
			message.append("Report per la proposta di certificazione \"" + descProposta + "\" per la linea "
					+ descBreveLinea + " generato.").append(System.getProperty("line.separator"));
		} else {
			message.append("La proposta di certificazione \"" + descProposta + "\" per la linea " + descBreveLinea
					+ " non ha coinvolto nessun progetto.").append(System.getProperty("line.separator"));
		}

		if (Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR.equals(descBreveLinea)) {
			// SOLO POR-FESR
			message.append("\n");

			if (scostamenti != null && scostamenti.size() > 0) {
				message.append("Esiste uno scostamento rispetto alle percentuali standard.")
						.append(System.getProperty("line.separator"));
				message.append(System.getProperty("line.separator"));
				message.append(TableWriter.writeTableToString("scostamenti", scostamenti));
				message.append(System.getProperty("line.separator"));

				if (scostamentoCompensato) {
					message.append("Lo scostamento è stato compensato completamente.")
							.append(System.getProperty("line.separator"));
				} else if (progettiPerCompensazione != null && progettiPerCompensazione.size() > 0) {
					message.append("Lo scostamento è stato compensato parzialmente.")
							.append(System.getProperty("line.separator"));
				} else {
					message.append(
							"Lo scostamento non è stato compensato, nessun progetto disponibile per la compensazione.")
							.append(System.getProperty("line.separator"));
				}
			}
			message.append("\n");
			if (progettiPerCompensazione != null && progettiPerCompensazione.size() > 0) {
				if (progettiPerCompensazione.size() > 1) {
					message.append(
							"Sono stati utilizzati " + progettiPerCompensazione.size() + " per la compensazione: ")
							.append(System.getProperty("line.separator"));
				} else {
					message.append("è stato utilizzato un solo progetto per la compensazione:")
							.append(System.getProperty("line.separator"));
				}
				message.append(System.getProperty("line.separator"));
				message.append(TableWriter.writeTableToString("progettiPerCompensazione", progettiPerCompensazione));
			}
		}
		return message.toString();
	}

	private boolean isScostamentoCompensato(BigDecimal idPropostaCertificaz) throws FileNotFoundException, IOException {
		String queryScostamentoComp = fileSqlUtil.getQuery("ScostamentoAssePropostaCompensato.sql");
		Object[] params = new Object[] { idPropostaCertificaz };
		Integer res = getJdbcTemplate().queryForObject(queryScostamentoComp, params, Integer.class);
		return res == 0;
	}

	private List<ProgettoPerCompensazioneVO> caricaProgettiPerCompensazione(BigDecimal idPropostaCertificaz)
			throws FileNotFoundException, IOException {
		String queryProgetti = fileSqlUtil.getQuery("ProgettoPerCompensazione.sql");
		Object[] params = new Object[] { idPropostaCertificaz };
		List<ProgettoPerCompensazioneVO> progetti = getJdbcTemplate().query(queryProgetti, params,
				new ProgettoPerCompensazioneRowMapper());
		return progetti;
	}

	private List<ScostamentoAssePropostaCertificazioneVO> caricaScostamenti(BigDecimal idPropostaCertificaz)
			throws FileNotFoundException, IOException {
		String queryScostamento = fileSqlUtil.getQuery("ScostamentoAsseProposta.sql");
		Object[] params = new Object[] { idPropostaCertificaz };
		List<ScostamentoAssePropostaCertificazioneVO> scostamenti = getJdbcTemplate().query(queryScostamento, params,
				new ScostamentoAssePropostaRowMapper());
		return scostamenti;
	}

//	private List<String> getAssistenzaEmailAddress() throws Exception {
//		return mailUtil.getAssistenzaEmailAddress();
//	}

	@SuppressWarnings("unchecked")
	private List<String> caricaDestinatariEmailNotifica(Long idPropostaCertificaz, Long[] idLineeDiIntervento)
			throws Exception {
		String prf = "[CertificazioneDaoImpl::caricaDestinatariEmailNotifica]";
		LOG.info(prf + " BEGIN");
		List<String> emailRecipients = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();

		LOG.info(prf + " idPropostaCertificaz = " + idPropostaCertificaz);
		List<PbandiDInvioPropostaCertifVO> linee = new ArrayList<PbandiDInvioPropostaCertifVO>();
		for (Long idLineaDiIntervento : idLineeDiIntervento) {
			LOG.info(prf + " idLineaDiIntervento = " + idLineaDiIntervento);
			PbandiDInvioPropostaCertifVO temp = new PbandiDInvioPropostaCertifVO();
			temp.setIdLineaDiIntervento(new BigDecimal(idLineaDiIntervento));
			linee.add(temp);
		}

		String sql = fileSqlUtil.getQuery("Proposta.sql");
		Object[] params = new Object[] { beanUtil.transform(idPropostaCertificaz, BigDecimal.class) };
		PbandiTPropostaCertificazVO propostaVO = getJdbcTemplate().queryForObject(sql, params,
				new PropostaCertificazioneRowMapper());

		map.put("idStatoPropostaCertif", "idStatoPropostaCertif");
		PbandiDInvioPropostaCertifVO invioPropostaVO = beanUtil.transform(propostaVO,
				PbandiDInvioPropostaCertifVO.class, map);

		List<PbandiDLineaDiInterventoVO> lineeVO = caricaLineeRadiceDaCertificare(idPropostaCertificaz);

		List<PbandiDInvioPropostaCertifVO> invioVOs = new ArrayList<>();
		for (PbandiDLineaDiInterventoVO linea : lineeVO) {
			StringBuilder sql1 = new StringBuilder();
			sql1.append("SELECT * FROM PBANDI_D_INVIO_PROPOSTA_CERTIF WHERE ID_LINEA_DI_INTERVENTO = ")
					.append(linea.getIdLineaDiIntervento());
			List<PbandiDInvioPropostaCertifVO> invioVO = getJdbcTemplate().query(sql1.toString(),
					new InvioPropostaRowMapper());
			invioVOs.addAll(invioVO);
		}

		emailRecipients.addAll((Collection<? extends String>) beanUtil.index(invioVOs, "email").keySet());

		if (emailRecipients.size() == 0) {
			throw new CertificazioneException("Non sono stati trovati indirizzi email a cui inviare la proposta "
					+ idPropostaCertificaz + ", impossibile inviarla.");
		}
		LOG.info(prf + " END");
		return emailRecipients;
	}

	private List<PbandiDLineaDiInterventoVO> caricaLineeRadiceDaCertificare(Long idPropostaCertificaz)
			throws CertificazioneException {

		PbandiDLineaDiInterventoVO pbandiDLineaDiIntervento1VO = new PbandiDLineaDiInterventoVO();
		pbandiDLineaDiIntervento1VO.setDescBreveLinea(Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM PBANDI_D_LINEA_DI_INTERVENTO where desc_breve_linea = '")
				.append(Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR).append("'");
		List<PbandiDLineaDiInterventoVO> lineeRadiceCoinvolte = getJdbcTemplate().query(sql.toString(),
				new LineaInterventoRowMapper());

		if (lineeRadiceCoinvolte == null || lineeRadiceCoinvolte.size() == 0) {
			throw new CertificazioneException("Non sono state trovate  linee di intervento per cui inviare la proposta "
					+ idPropostaCertificaz + ", impossibile inviarla.");
		}
		return lineeRadiceCoinvolte;
	}

	private void eseguiProceduraCreazioneProposta(PropostaCertificazioneVO proposta) throws Exception {
		String prf = "[CertificazioneDaoImpl::eseguiProceduraCreazioneProposta]";
		LOG.info(prf + " START");
		try {
			BigDecimal idPropostaCertificaz = beanUtil.transform(proposta.getIdPropostaCertificaz(), BigDecimal.class);

			// controllo le 4 tabelle r e la dett, devono essere tutte vuote
			String sql1 = fileSqlUtil.getQuery("DetPropCerQpDocDettProp.sql");
			String sql2 = fileSqlUtil.getQuery("DettPropCerFideiusDettProp.sql");
			String sql3 = fileSqlUtil.getQuery("DettPropCertErogazDettProp.sql");
			String sql4 = fileSqlUtil.getQuery("PbandiTDettPropostaCertif.sql");

			Object[] params = new Object[] { idPropostaCertificaz };
			Integer count1 = getJdbcTemplate().queryForObject(sql1, params, Integer.class);
			Integer count2 = getJdbcTemplate().queryForObject(sql2, params, Integer.class);
			Integer count3 = getJdbcTemplate().queryForObject(sql3, params, Integer.class);
			Integer count4 = getJdbcTemplate().queryForObject(sql4, params, Integer.class);

			if (count1 + count2 + count3 + count4 > 0) {
				throw new CertificazioneException(
						prf + " Dettagli per la proposta " + proposta.getIdPropostaCertificaz() + " gia presenti.");
			}


			LOG.info(prf + " INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE.EXECUTE");
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withSchemaName(Constants.SCHEMA_NAME1)
					.withProcedureName("MainCreaPropostaCertificazione").withCatalogName("PCK_PBANDI_CERTIFICAZIONE")
					.withoutProcedureColumnMetaDataAccess();

			jdbcCall.declareParameters(new SqlOutParameter(Constants.RESULT, Types.NUMERIC));
			jdbcCall.declareParameters(new SqlParameter(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, Types.NUMERIC));
			jdbcCall.setFunction(true);
			jdbcCall.compile();

			// SqlParameterSource in = new MapSqlParameterSource().addValue(P_ID_UTENTE,
			// BigDecimal.valueOf(idUtente));
			Map<String, Object> input = new HashMap<>();
			input.put(Constants.P_ID_PROPOSTA_CERTIFICAZIONE, idPropostaCertificaz);

			int procedureResult = 1;
			Map<String, Object> out = jdbcCall.execute(input);
			if (out.containsKey(Constants.RESULT)) {
				LOG.info(prf + " dentro if " + procedureResult);
				procedureResult = ((BigDecimal) out.get(Constants.RESULT)).intValue();
			}
			LOG.info(prf + " esito procedura: " + procedureResult);
//			if(procedureResult != 0) {
//				throw new CertificazioneException(prf + " Esecuzione plsql fallita.");
//			}
			LOG.info(prf + " FINE INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE.EXECUTE");
			LOG.info(prf + " END");
		} catch (Exception ex) {
			LOG.error(prf + " Errore durante nella creazione proposta certificazione: " + ex);
			throw ex;
		}

	}

	private void aggiornaStatoPropostaCertificazione(String nuovoStato, PropostaCertificazioneVO vo, Long idUtente)
			throws FileNotFoundException, IOException {
		String prf = "[CertificazioneDaoImpl::aggiornaStatoPropostaCertificazione]";
		LOG.info(prf + " START");
		BigDecimal idStatoNuovo = idStatoDaDescBreve(nuovoStato);
		LOG.info(prf + " nuovoStato = " + nuovoStato + "; idStatoNuovo = " + idStatoNuovo + "; IdStatoPropostaCertif = "
				+ vo.getIdStatoPropostaCertif() + "; ID_PROPOSTA_CERTIFICAZ = "
				+ vo.getIdPropostaCertificaz().longValue());
		String sql = fileSqlUtil.getQuery("AggiornaStatoProposta.sql");
		Object[] params = new Object[] { vo.getDtOraCreazione(), vo.getDtCutOffValidazioni(),
				vo.getDtCutOffFideiussioni(), idStatoNuovo, vo.getDtCutOffPagamenti(), idUtente,
				vo.getDtCutOffErogazioni(), vo.getDescProposta(), vo.getIdPropostaCertificaz() };
		int i = getJdbcTemplate().update(sql, params);
		LOG.info(prf + " record modificati da update = " + i);
		LOG.info(prf + " END");
	}

	@Override
	public LineaDiInterventoDTO findLineaDiInterventoFromProposta(Long idUtente, Long idProposta) {
		try {
			StringBuilder queryPropLinea = new StringBuilder();
			queryPropLinea.append("SELECT * FROM PBANDI_R_PROPOSTA_CERTIF_LINEA WHERE ID_PROPOSTA_CERTIFICAZ = ")
					.append(idProposta);
			PbandiRPropostaCertifLineaVO idLineaVO = getJdbcTemplate().queryForObject(queryPropLinea.toString(),
					new PropostaCertifLineaRowMapper());

			if (idLineaVO != null) {
				StringBuilder queryLinea = new StringBuilder();
				queryLinea.append("SELECT * FROM PBANDI_D_LINEA_DI_INTERVENTO WHERE ID_LINEA_DI_INTERVENTO = ")
						.append(idLineaVO.getIdLineaDiIntervento());

				PbandiDLineaDiInterventoVO lineaResult = getJdbcTemplate().queryForObject(queryLinea.toString(),
						new LineaInterventoRowMapper());
				if (lineaResult != null) {
					HashMap<String, String> trans = new HashMap<String, String>();
					trans.put("idLineaDiIntervento", "idLineaDiIntervento");
					trans.put("descBreveLinea", "descBreveLinea");
					trans.put("descLinea", "descLinea");
					return beanUtil.transform(lineaResult, LineaDiInterventoDTO.class, trans);
				} else {
					return null;
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			String msg = "Errore nell'ottenimento del id linea di intervento :" + e.getMessage();
			logger.error(msg, e);
			return null;
			// throw new CertificazioneException(msg, e);
		}
	}

	// @Override
	public it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO[] propostePerRicercaDocumenti(
			Long idUtente, String identitaDigitale, List<String> statiProposta) throws Exception {
		String prf = "[CertificazioneDaoImpl :: propostePerRicercaDocumenti()]";
		LOG.info(prf + " - BEGIN");
		it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO[] proposteDTO = null;
		try {
			it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO[] proposteDTOsrv;
			proposteDTOsrv = certificazioneBusinessImpl.findProposteCertificazione(idUtente, identitaDigitale,
					statiProposta);
			proposteDTO = beanUtil.transform(proposteDTOsrv,
					it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO.class);
			for (it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO dto : proposteDTO) {
				if (dto.getIdPropostaCertificaz() != null) {
					Long id = certificazioneBusinessImpl
							.findIdLineaDiInterventoFromProposta(dto.getIdPropostaCertificaz());
					dto.setIdLineaDiIntervento(id);
				}
			}
		} catch (Exception e) {
			LOG.error(prf + "ERRPRE: " + e);
			throw new Exception(e.getMessage());
		} finally {
			LOG.info(prf + " - END");
		}
		return proposteDTO;
	}

	/*
	 * Metodi sostituiti con analoghi di CertificazioneBusinessImpl private byte[]
	 * generaReportChiusuraConti(PbandiTPropostaCertificazVO propostaVO, Long
	 * idUtente) throws Exception { String prf =
	 * "[CertificazioneDaoImpl::generaReportChiusuraConti]"; LOG.info(prf +
	 * " BEGIN");
	 * 
	 * byte[] reportCC = null; // File master si trova in:
	 * conf/report/xls_templates/nReportChiusuraConti.xls String templateKey =
	 * Constants.TEMPLATE_REPORT_CHIUSURA_CONTI; String nomeBandoLinea = null;
	 * String codiceProgetto = null; String denominazioneBeneficiario = null; Long
	 * idLineaDiIntervento = null;
	 * 
	 * try { ProgettoCertificazioneIntermediaFinaleDTO[] pcif =
	 * findProgettiCertificazioneIntermediaFinaleCC(idUtente,
	 * propostaVO.getIdPropostaCertificaz().longValue(), nomeBandoLinea,
	 * codiceProgetto, denominazioneBeneficiario, idLineaDiIntervento);
	 * 
	 * List<ProgettoCertificazioneIntermediaFinaleDTO> elementiReport =
	 * Arrays.asList(pcif); ArrayList<Long> linesToJump = new ArrayList<Long>();
	 * linesToJump.add(2L);
	 * 
	 * LOG.info("\nelementiReport"); for (ProgettoCertificazioneIntermediaFinaleDTO
	 * dto : elementiReport)
	 * LOG.info("\n"+dto.getAsse()+"  -  "+dto.getCodiceProgetto());
	 * 
	 * reportCC = TableWriter.writeTableToByteArrayCC(templateKey, new
	 * ExcelDataWriter(propostaVO.getIdPropostaCertificaz().toString()),
	 * elementiReport, linesToJump);
	 * 
	 * if(reportCC!=null) LOG.info(prf + " reportCC.length = "+reportCC.length);
	 * else LOG.info(prf + " reportCC NULL");
	 * 
	 * Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
	 * Map<String, Map<String, Object>> singleCellVars = new HashMap<String,
	 * Map<String, Object>>();
	 * 
	 * //////////////////////////////////// // Foglio "Testata" Map<String, Object>
	 * testataVars = new HashMap<String, Object>();
	 * 
	 * String pattern = "yyyy-MM-dd HH:mm"; SimpleDateFormat simpleDateFormat = new
	 * SimpleDateFormat(pattern);
	 * 
	 * String sql = fileSqlUtil.getQuery("BandoPropostaUnionePeriodo.sql"); Object[]
	 * params = new Object[] {propostaVO.getIdPropostaCertificaz()};
	 * BandoPropostaCertificazUnionPeriodoVO bp =
	 * getJdbcTemplate().queryForObject(sql, params, new
	 * BandoPropostaUnionePeriodoRowMapper());
	 * 
	 * testataVars.put("idPropostaCertificaz",
	 * propostaVO.getIdPropostaCertificaz()); testataVars.put("descProposta",
	 * bp.getDescProposta()); testataVars.put("dtOraCreazione",
	 * simpleDateFormat.format(bp.getDtOraCreazione()));
	 * testataVars.put("dtAnnoContabile", bp.getDescPeriodoVisualizzata());
	 * 
	 * // estraggo il valore da inserire nel campo PROGRAMMA String sql1 =
	 * fileSqlUtil.getQuery("BandoLineaInterventoCertificazione.sql"); Object[]
	 * paramsI = new Object[] {propostaVO.getIdPropostaCertificaz()};
	 * 
	 * BandoLineaInterventoCertificazioneVO bb =
	 * getJdbcTemplate().queryForObject(sql1, paramsI, new
	 * BandoLineaInterventoCertificazRowMapper());
	 * 
	 * testataVars.put("descProgramma", bb.getDescLinea());
	 * testataVars.put("dtChiusuraConti", simpleDateFormat.format(new Date()));
	 * singleCellVars.put("Testata", testataVars);
	 * 
	 * //////////////////////////////////// // Foglio "Dettaglio Chiusura Conti"
	 * singlePageTables.put("Dettaglio Chiusura Conti", reportCC);
	 * 
	 * //////////////////////////////////// // Foglio "Per Asse" Map<String, Object>
	 * perAsseVVars = new HashMap<String, Object>();
	 * 
	 * final String IMPORTO_CERTNETTOANN_PREFIX = "importoCertNettAnn_"; final
	 * String IMPORTO_COLONNAC_PREFIX = "importoColonnaC_"; final String
	 * IMPORTO_DIFFCNA_PREFIX = "importoDiffCNA_"; final String
	 * IMPORTO_DIFFREV_PREFIX = "importoDiffREV_"; final String
	 * IMPORTO_DIFFREC_PREFIX = "importoDiffREC_"; final String
	 * IMPORTO_DIFFSOPPR_PREFIX = "importoDiffSoppr_";
	 * 
	 * // prepopolo i valori delle pagina "Per Asse" for (String key : new String[]
	 * { "I", "II", "III", "IV" , "V" , "VI" , "VII"}) {
	 * perAsseVVars.put(IMPORTO_CERTNETTOANN_PREFIX + key, new BigDecimal(0));
	 * perAsseVVars.put(IMPORTO_COLONNAC_PREFIX + key, new BigDecimal(0));
	 * perAsseVVars.put(IMPORTO_DIFFCNA_PREFIX + key, new BigDecimal(0));
	 * perAsseVVars.put(IMPORTO_DIFFREV_PREFIX + key, new BigDecimal(0));
	 * perAsseVVars.put(IMPORTO_DIFFREC_PREFIX + key, new BigDecimal(0));
	 * perAsseVVars.put(IMPORTO_DIFFSOPPR_PREFIX + key, new BigDecimal(0)); }
	 * 
	 * // calcolo le somme for (ProgettoCertificazioneIntermediaFinaleDTO ele :
	 * elementiReport) { String asse = ele.getAsse(); LOG.info(prf+ " asse="+asse +
	 * ", progetto="+ele.getCodiceProgetto());
	 * 
	 * BigDecimal impCNA =
	 * beanUtil.transform(ele.getImportoCertifNettoAnnual(),BigDecimal.class);
	 * BigDecimal tmpImpCNA =
	 * (BigDecimal)perAsseVVars.get(IMPORTO_CERTNETTOANN_PREFIX + asse);
	 * perAsseVVars.put(IMPORTO_CERTNETTOANN_PREFIX + asse, tmpImpCNA.add(impCNA));
	 * 
	 * BigDecimal impColonnaC =
	 * beanUtil.transform(ele.getColonnaC(),BigDecimal.class); BigDecimal tmpImpColC
	 * = (BigDecimal)perAsseVVars.get(IMPORTO_COLONNAC_PREFIX + asse);
	 * perAsseVVars.put(IMPORTO_COLONNAC_PREFIX + asse,
	 * tmpImpColC.add(impColonnaC));
	 * 
	 * BigDecimal impDiffCna =
	 * beanUtil.transform(ele.getDiffCna(),BigDecimal.class); BigDecimal tmpDiffCna
	 * = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFCNA_PREFIX + asse);
	 * perAsseVVars.put(IMPORTO_DIFFCNA_PREFIX + asse, tmpDiffCna.add(impDiffCna));
	 * 
	 * BigDecimal impDiffRev =
	 * beanUtil.transform(ele.getDiffRev(),BigDecimal.class); BigDecimal tmpDiffRev
	 * = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFREV_PREFIX + asse);
	 * perAsseVVars.put(IMPORTO_DIFFREV_PREFIX + asse, tmpDiffRev.add(impDiffRev));
	 * 
	 * BigDecimal impDiffRec =
	 * beanUtil.transform(ele.getDiffRec(),BigDecimal.class); BigDecimal tmpDiffRec
	 * = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFREC_PREFIX + asse);
	 * perAsseVVars.put(IMPORTO_DIFFREC_PREFIX + asse, tmpDiffRec.add(impDiffRec));
	 * 
	 * BigDecimal impDiffSop =
	 * beanUtil.transform(ele.getDiffSoppr(),BigDecimal.class); BigDecimal
	 * tmpDiffSop = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFSOPPR_PREFIX + asse);
	 * perAsseVVars.put(IMPORTO_DIFFSOPPR_PREFIX + asse,
	 * tmpDiffSop.add(impDiffSop));
	 * 
	 * }
	 * 
	 * singleCellVars.put("Per Asse", perAsseVVars); LOG.info(prf +
	 * " singleCellVars popolate");
	 * 
	 * // genero l'excell reportCC =
	 * reportManager.getMergedDocumentFromTemplate(templateKey, singlePageTables,
	 * singleCellVars); if(reportCC == null) { throw new CertificazioneException(prf
	 * + " Errore durante la generazione report per chiusura conti."); }
	 * 
	 * LOG.info(prf + " END"); return reportCC; } catch (CertificazioneException e)
	 * { throw e; } catch (NullPointerException e) {
	 * LOG.info(prf+" - Errore durante la generazione report Chiusura conti: "+
	 * e.getMessage()); throw e; } catch (Exception e) { throw e; }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * private ProgettoCertificazioneIntermediaFinaleDTO[]
	 * findProgettiCertificazioneIntermediaFinaleCC(Long idUtente, Long idProposta,
	 * String nomeBandoLinea, String codiceProgetto, String
	 * denominazioneBeneficiario, Long idLineaDiIntervento) throws
	 * CertificazioneException { String prf =
	 * "[CertificazioneDaoImpl::findProgettiCertificazioneIntermediaFinaleCC]";
	 * LOG.info(prf + " START"); try{ String sql =
	 * fileSqlUtil.getQuery("DettPropostaCertifAnnualCC.sql"); Object[] params = new
	 * Object[] {idProposta}; List<DettPropostaCertifAnnualCCVO> result =
	 * getJdbcTemplate().query(sql, params, new
	 * DettPropostaCertifAnnualCCRowMapper()); LOG.info(prf + " END"); return
	 * (ProgettoCertificazioneIntermediaFinaleDTO[]) beanUtil.transform(result,
	 * ProgettoCertificazioneIntermediaFinaleDTO.class); }catch(Exception ex){ LOG.
	 * error("Errore durante dell'esecuzione di findProgettiCertificazioneIntermediaFinale: "
	 * + ex); LOG.info(prf + " END"); throw new
	 * CertificazioneException("Errore durante dell'esecuzione di findProgettiCertificazioneIntermediaFinale: "
	 * + ex); } }
	 */

}
