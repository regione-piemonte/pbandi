/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbwebrce.exception.DaoException;
import it.csi.pbandi.pbwebrce.business.CheckListBusinessImpl;
import it.csi.pbandi.pbwebrce.business.MailUtil;
import it.csi.pbandi.pbwebrce.dto.affidamenti.AffidamentoCheckListDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.FornitoreDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.MotiveAssenzaDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.NormativaAffidamentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipoAffidamentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAppaltoDTO;
import it.csi.pbandi.pbwebrce.exception.GestioneAffidamentiException;
import it.csi.pbandi.pbwebrce.integration.dao.AffidamentiDAO;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.AffidamentiProgettoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.AffidamentiRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.AffidamentoChecklistRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.AppaltoChecklistRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.ArchivioFileRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.ChecklistAppaltoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.DocumentoIndexRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.EmailRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.EsitiNoteAffidamentoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.FileEntitaRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.FornitoreAffidamentoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.MotiveAssenzaRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.NormativeAffidamentoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.PbandiFornitoriRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.SubcontrattoAffidRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.ProceduraAggiudicazRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.TipoAffidamentoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.TipoAppaltoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.TipoDocumentoIndexMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.TipologiaAggiudicazRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.VarianteAffidamentoRowMapper;
import it.csi.pbandi.pbwebrce.integration.request.SubcontrattoRequest;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.AffidamentoVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.ArchivioFileVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.CheckListAppaltoVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.DocumentoIndexMaxVersioneDefinitivoVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.Email;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.FornitoreAffidamentoVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.FornitoreVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.SubcontrattoAffidVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.VariantiAffidamentoDescrizioneVO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.FileDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ParamInviaInVerificaDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.SubcontrattoAffidDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDMotivoAssenzaCigVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDNormativaAffidamentoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDTipoAffidamentoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDTipologiaAggiudicazVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDTipologiaAppaltoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiRFornitoreAffidamentoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiRProgettiAppaltiVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTAffidamentoChecklistVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTAppaltoChecklistVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTEsitiNoteAffidamentVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTProceduraAggiudicazVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTVariantiAffidamentiVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.TipoDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbwebrce.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbwebrce.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbwebrce.util.BeanRowMapper;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.DateUtil;
import it.csi.pbandi.pbwebrce.util.ErrorMessages;
import it.csi.pbandi.pbwebrce.util.FileSqlUtil;
import it.csi.pbandi.pbwebrce.util.MessageConstants;

@Component
public class AffidamentiDAOImpl extends JdbcDaoSupport implements AffidamentiDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	private ConfigurationManager configurationManager;

	protected FileApiServiceImpl fileApiServiceImpl;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;

	public static final String MAIL_ADDRESSES_SEPARATOR = ",";

	@Autowired
	protected MailUtil mailUtil;

	@Autowired
	private CheckListBusinessImpl checkListBusiness;

	@Autowired
	public AffidamentiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
		this.genericDAO = new GenericDAO(dataSource);
	}

	public AffidamentiDAOImpl() {
	}

	@Override
	@Transactional
	public List<AffidamentoDTO> getElencoAffidamenti(Long idProgetto, String codiceRuolo) throws Exception {
		String prf = "[AffidamentiDAOImpl::getElencoAffidamenti]";
		LOG.info(prf + " BEGIN");
		try {
			// Legge gli affidamenti del progetto.
			String queryAffidProgetto = fileSqlUtil.getQuery("AffidamentiProgetto.sql");
			Long idTipoDoc = Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO;
			Object[] paramA = new Object[] { idTipoDoc, idTipoDoc, idProgetto };
			List<AffidamentoVO> lista = getJdbcTemplate().query(queryAffidProgetto, paramA,
					new AffidamentiProgettoRowMapper());

			// Se ruolo valorizzato e diverso da beneficiario, vanno tolti gli affidamenti
			// in stato DAINVIARE.
			if (!StringUtil.isEmpty(codiceRuolo)
					&& !Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER.equalsIgnoreCase(codiceRuolo)
					&& !Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA.equalsIgnoreCase(codiceRuolo)) {
				this.selezionaAffidamentiPerIstruttore(lista);
			}

			List<AffidamentoDTO> elenco = null;
			if (lista != null) {
				elenco = beanUtil.transformList(lista, AffidamentoDTO.class);

				LOG.info(prf + " trovata una lista di " + lista.size() + " affidamenti");

				// Jira PBANDI-2829: serve sotto.
				BigDecimal idTipoDocChecklistDocumentale = idTipoIndexDaDescBreve(
						Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE);

				// Popola i campi NumFornitoriAssociati, NumAllegatiAssociati e
				// EsisteAllegatoNonInviato.
				for (AffidamentoDTO dto : elenco) {
					FornitoreAffidamentoDTO fornitore = new FornitoreAffidamentoDTO();
					fornitore.setIdAppalto(dto.getIdAppalto());
					List<FornitoreAffidamentoDTO> fornitori = this.findFornitoriAffidamento(fornitore);
					dto.setNumFornitoriAssociati(new Long(fornitori.size()));
					dto.setFornitori(fornitori.toArray(new FornitoreAffidamentoDTO[0]));

					FileDTO[] allegati = null;
					allegati = getFilesAssociatedAffidamento(dto.getIdAppalto());
					dto.setNumAllegatiAssociati(new Long(allegati.length));
					
					dto.setEsisteAllegatoNonInviato(false);
					for (FileDTO f : allegati) {
						if (f.getDtEntita() == null)
							dto.setEsisteAllegatoNonInviato(true);
					}
					// Jira PBANDI-2829: legge l'eventuale esito (positivo\negativo).
					this.popolaEsitiChecklistAffidamento(dto, idTipoDocChecklistDocumentale);
				}
			}
			LOG.info(prf + " END");
			return elenco;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}

	}

	private void popolaEsitiChecklistAffidamento(AffidamentoDTO dto, BigDecimal idTipoDocChecklistDocumentale)
			throws Exception {
		String prf = "[AffidamentiDAOImpl::popolaEsitiChecklistAffidamento]";
		LOG.info(prf + " START");
		try {
			Long idAffidamento = dto.getIdAppalto();
			Long idProgetto = dto.getIdProgetto();

			// Jira PBANDI-2863: prima usava DocumentoIndexMaxVersioneVO, ora
			// DocumentoIndexMaxVersioneDefinitivoVO.
			// poichè non trovava gli esiti se l'ultima checklist salvata era una bozza.

			// get id entita
			String queryEntita = fileSqlUtil.getQuery("IdEntitaDaNome.sql");
			Object[] param = new Object[] { Constants.NOME_ENTITA_APPALTO };
			BigDecimal idEntita = getJdbcTemplate().queryForObject(queryEntita, param, BigDecimal.class);

			// PK non funziona perche e' cambiato la tabella PBANDI_T_DOCUMENTO_INDEX
//			DocumentoIndexMaxVersioneDefinitivoVO docIndex = genericDAO.findSingleOrNoneWhere(documentoIndexVO);

			String queryIdDocIndex = fileSqlUtil.getQuery("DocumentoIndex.sql");
			LOG.info(prf + " queryIdDocIndex=" + queryIdDocIndex);
			LOG.info(prf + " param 1 idEntita=" + idEntita);
			LOG.info(prf + " param 2 idAffidamento =" + idAffidamento);
			LOG.info(prf + " param 3 idTipoDocChecklistDocumentale =" + idTipoDocChecklistDocumentale);
			LOG.info(prf + " param 4 idProgetto =" + idProgetto);

//			Object[] paramI = new Object[5];
//			paramI[0] = null; paramI[1] = idEntita; paramI[2] = BigDecimal.valueOf(idAffidamento); 
//			paramI[3] = idTipoDocChecklistDocumentale; paramI[4] = BigDecimal.valueOf(idProgetto);
			Object[] paramI = new Object[4];
			paramI[0] = idEntita;
			paramI[1] = BigDecimal.valueOf(idAffidamento);
			paramI[2] = idTipoDocChecklistDocumentale;
			paramI[3] = BigDecimal.valueOf(idProgetto);

			DocumentoIndexMaxVersioneDefinitivoVO docIndex = null;
			try {
				docIndex = getJdbcTemplate().queryForObject(queryIdDocIndex, paramI, new DocumentoIndexRowMapper());
			} catch (EmptyResultDataAccessException e) {
				LOG.info(prf + " NESSUN DOCUMENTO TROVATO..." + e.getMessage());
			}

			if (docIndex == null) {
				logger.info("popolaEsitiChecklistAffidamento(): idDocumentoIndex NON TROVATO.");
				return;
			}
			BigDecimal idDocumentoIndex = docIndex.getIdDocumentoIndex();
			LOG.info(prf + " popolaEsitiChecklistAffidamento(): idDocumentoIndex = " + idDocumentoIndex);

			String queryChecklist = fileSqlUtil.getQuery("ChecklistAppalto.sql");
			LOG.info(prf + " queryChecklist=" + queryChecklist);

			Object[] params = new Object[] { idDocumentoIndex, idAffidamento };
			List<CheckListAppaltoVO> list = getJdbcTemplate().query(queryChecklist, params,
					new ChecklistAppaltoRowMapper());

			BigDecimal idChecklist = null;
			if (list.size() == 1) {
				idChecklist = list.get(0).getIdChecklist();
			}
			LOG.info(prf + " popolaEsitiChecklistAffidamento(): list.size() = " + list.size() + "; idChecklist = "
					+ idChecklist);

			String queryEsiti = fileSqlUtil.getQuery("EsitiNoteAffidamento.sql");
			LOG.info(prf + " queryEsiti=" + queryEsiti);

			Object[] paramsI = new Object[] { idChecklist };
			PbandiTEsitiNoteAffidamentVO filtroEsito = new PbandiTEsitiNoteAffidamentVO();
			filtroEsito.setIdChecklist(idChecklist);
			List<PbandiTEsitiNoteAffidamentVO> esiti = getJdbcTemplate().query(queryEsiti, paramsI,
					new EsitiNoteAffidamentoRowMapper());

			dto.setFase1Esito("");
			dto.setFase1Rettifica("");
			dto.setFase2Esito("");
			dto.setFase2Rettifica("");
			for (PbandiTEsitiNoteAffidamentVO esito : esiti) {
				if (esito.getFase().intValue() == 1) {
					dto.setFase1Esito(esito.getEsito());
					dto.setFase1Rettifica(esito.getFlagRettifica()); // Jira PBANDI-2829.
				} else if (esito.getFase().intValue() == 2) {
					dto.setFase2Esito(esito.getEsito());
					dto.setFase2Rettifica(esito.getFlagRettifica()); // Jira PBANDI-2829.
				}
			}
			LOG.info("popolaEsitiChecklistAffidamento(): fase1Esito = " + dto.getFase1Esito() + "; fase1Rettifica = "
					+ dto.getFase1Rettifica() + "; fase2Esito = " + dto.getFase2Esito() + "; fase2Rettifica = "
					+ dto.getFase2Rettifica());
			LOG.info(prf + " END");
		} catch (Exception e) {
			LOG.error("popolaEsitiChecklistAffidamento(): ERRORE: " + e);
			LOG.info(prf + " END");
			throw e;
		}

	}

	private FileDTO[] getFilesAssociatedAffidamento(Long idAppalto) throws FileNotFoundException, IOException {
		String prf = "[AffidamentiDAOImpl::getFilesAssociatedAffidamento]";
		LOG.info(prf + " START");
		List<FileDTO> filez = new ArrayList<FileDTO>();
		String sql = fileSqlUtil.getQuery("AffidamentoFiles.sql");
		Object[] params = new Object[] { idAppalto };
		List<ArchivioFileVO> list = getJdbcTemplate().query(sql, params, new ArchivioFileRowMapper());
		for (ArchivioFileVO archivioFile : list) {
			FileDTO dto = createFileDTO(archivioFile);
			// Cerco PBANDI_T_FILE_ENTITA per il flag per invio verifica.
			String queryFileEntita = fileSqlUtil.getQuery("FileEntita.sql");
			Object[] param = new Object[] { archivioFile.getIdFileEntita() };
			PbandiTFileEntitaVO voFile = getJdbcTemplate().queryForObject(queryFileEntita, param,
					new FileEntitaRowMapper());

			// Aggancio il flag per invio verifica.
			dto.setDtEntita(voFile.getDtEntita());
			dto.setFlagEntita(voFile.getFlagEntita());

			filez.add(dto);
		}
		LOG.info(prf + " END");
		return filez.toArray(new FileDTO[0]);
	}

	private FileDTO createFileDTO(ArchivioFileVO file) {
		String prf = "[AffidamentiDAOImpl::createFileDTO]";
		LOG.info(prf + " START");
		FileDTO fileDTO = new FileDTO();
		fileDTO.setDtAggiornamento(file.getDtAggiornamentoFile());
		fileDTO.setDtInserimento(file.getDtInserimentoFile());
		if (file.getIdDocumentoIndex() != null)
			fileDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		if (file.getIdFolder() != null)
			fileDTO.setIdFolder(file.getIdFolder().longValue());
		fileDTO.setNomeFile(file.getNomeFile());
		fileDTO.setSizeFile(file.getSizeFile() != null ? file.getSizeFile().longValue() : 0l);
		if (file.getIdProgetto() != null)
			fileDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		if (file.getEntitiesAssociated() != null && file.getEntitiesAssociated().longValue() > 0) {
			fileDTO.setEntityAssociated(file.getEntitiesAssociated().longValue());
		}
		if (file.getIslocked() != null && file.getIslocked().equalsIgnoreCase("S"))
			fileDTO.setIsLocked(true);
		else
			fileDTO.setIsLocked(false);
		fileDTO.setNumProtocollo(file.getNumProtocollo());

		if (file.getIdEntita() != null) {
			fileDTO.setIdEntita(file.getIdEntita().longValue());
		}
		if (file.getIdTarget() != null) {
			fileDTO.setIdTarget(file.getIdTarget().longValue());
		}
		fileDTO.setFlagEntita(file.getFlagEntita()); // Jira PBANDI-2815

		fileDTO.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());
		LOG.info(prf + " END");
		return fileDTO;
	}

	private List<FornitoreAffidamentoDTO> findFornitoriAffidamento(FornitoreAffidamentoDTO fornitore) throws Exception {
		String prf = "[AffidamentiDAOImpl::findFornitoriAffidamento]";
		LOG.info(prf + " START");
		try {
			String sql = fileSqlUtil.getQuery("FornitoriAffidamento.sql");
			Object[] params = new Object[] { fornitore.getIdAppalto() };

			List<FornitoreAffidamentoVO> vo = getJdbcTemplate().query(sql, params, new FornitoreAffidamentoRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(vo, FornitoreAffidamentoDTO.class);

		} catch (Exception ex) {
			logger.error("[findFornitoriAffidamento] errore nell'ottenimento dei fornitori.", ex);
			throw ex;
		}
	}

	private void selezionaAffidamentiPerIstruttore(List<AffidamentoVO> vo) {
		String prf = "[AffidamentiDAOImpl::findFornitoriAffidamento]";
		LOG.info(prf + " BEGIN");
		if (vo != null) {
			Iterator<AffidamentoVO> iterator = vo.iterator();
			while (iterator.hasNext()) {
				AffidamentoVO item = iterator.next();
				int idStato = item.getIdStatoAffidamento().intValue();
				if (idStato == Constants.ID_STATO_AFFIDAMENTO_DAINVIARE) {
					iterator.remove();
				}
			}
		}
		LOG.info(prf + " END");
	}

	public BigDecimal idTipoIndexDaDescBreve(String descBreve) {
		String prf = "[AffidamentiDAOImpl::idTipoIndexDaDescBreve]";
		LOG.info(prf + " START");
		StringBuilder queryTipoIndex = new StringBuilder();
		queryTipoIndex.append("SELECT * from PBANDI_C_TIPO_DOCUMENTO_INDEX where DESC_BREVE_TIPO_DOC_INDEX = ")
				.append("'").append(descBreve).append("'");
		TipoDocumentoIndexVO tipoDocumentoVO = getJdbcTemplate().queryForObject(queryTipoIndex.toString(),
				new TipoDocumentoIndexMapper());
		LOG.info(prf + " END");
		return beanUtil.transform(tipoDocumentoVO.getIdTipoDocumentoIndex(), BigDecimal.class);
	}

	@Override
	@Transactional
	public List<FornitoreDTO> findFornitori(FornitoreDTO filtro)
			throws FileNotFoundException, IOException, GestioneAffidamentiException {
		String prf = "[AffidamentiDAOImpl::findFornitori]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + "idSoggettoFornitore " + filtro.getIdSoggettoFornitore());
		List<FornitoreVO> result = new ArrayList<FornitoreVO>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sqlSelect = new StringBuilder();
		sqlSelect.append(fileSqlUtil.getQuery("FornitoriAssociabili.sql"));

		if (!isEmpty(filtro.getCodiceFiscaleFornitore())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE LIKE :codiceFiscale");
			params.addValue("codiceFiscale", filtro.getCodiceFiscaleFornitore().toUpperCase() + "%", Types.VARCHAR);
		}
		if (!isEmpty(filtro.getCognomeFornitore())) {
			sqlSelect.append("AND PBANDI_T_FORNITORE.COGNOME_FORNITORE LIKE :cognomeFornitore");
			params.addValue("cognomeFornitore", filtro.getCognomeFornitore().toUpperCase() + "%", Types.VARCHAR);
		}
		if (!isEmpty(filtro.getDenominazioneFornitore())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE LIKE :denominazioneFornitore");
			params.addValue("denominazioneFornitore", filtro.getDenominazioneFornitore().toUpperCase() + "%",
					Types.VARCHAR);
		}
		if (!isNull(filtro.getIdTipoSoggetto())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO=:idTipoSoggetto");
			params.addValue("idTipoSoggetto", filtro.getIdTipoSoggetto(), Types.BIGINT);
		}
		if (!isNull(filtro.getIdSoggettoFornitore())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.ID_SOGGETTO_FORNITORE = :idSoggettoFornitore");
			params.addValue("idSoggettoFornitore", filtro.getIdSoggettoFornitore(), Types.BIGINT);
		}
		if (!isEmpty(filtro.getAltroCodice())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.ALTRO_CODICE LIKE :altroCodice");
			params.addValue("altroCodice", filtro.getAltroCodice().toUpperCase() + "%", Types.VARCHAR);
		}
		if (!isNull(filtro.getIdFornitore())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.ID_FORNITORE = :idFornitore");
			params.addValue("idFornitore", filtro.getIdFornitore(), Types.BIGINT);
		}
		if (!isEmpty(filtro.getNomeFornitore())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.NOME_FORNITORE LIKE :nomeFornitore");
			params.addValue("nomeFornitore", filtro.getNomeFornitore().toUpperCase() + "%", Types.VARCHAR);
		}
		if (!isEmpty(filtro.getPartitaIvaFornitore())) {
			sqlSelect.append(" AND PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE LIKE :partitaIvaFornitore");
			params.addValue("partitaIvaFornitore", filtro.getPartitaIvaFornitore() + "%", Types.VARCHAR);
		}
		if (!isNull(filtro.getIdQualifica())) {
			sqlSelect.append(" AND PBANDI_R_FORNITORE_QUALIFICA.ID_QUALIFICA =:idQualifica");
			params.addValue("idQualifica", filtro.getIdQualifica(), Types.BIGINT);
		}
		if (filtro.getCodQualificheNotIn() != null && filtro.getCodQualificheNotIn().length > 0) {
			StringBuilder qualificheNotIn = new StringBuilder();
			for (String codiceQualifica : filtro.getCodQualificheNotIn()) {
				qualificheNotIn.append("'" + codiceQualifica + "',");
			}
			sqlSelect.append(" AND PBANDI_D_QUALIFICA.DESC_BREVE_QUALIFICA NOT IN ( ")
					.append(qualificheNotIn.substring(0, qualificheNotIn.lastIndexOf(","))).append(" )");
		}
		if (filtro.getCodQualificheIn() != null && filtro.getCodQualificheIn().length > 0) {
			StringBuilder qualificheIn = new StringBuilder();
			for (String codiceQualifica : filtro.getCodQualificheIn()) {
				qualificheIn.append("'" + codiceQualifica + "',");
			}
			sqlSelect.append(" AND PBANDI_D_QUALIFICA.DESC_BREVE_QUALIFICA  IN ( ")
					.append(qualificheIn.substring(0, qualificheIn.lastIndexOf(","))).append(" )");
		}
		sqlSelect.append(" ORDER BY cognomeFornitore asc, nomeFornitore asc, denominazioneFornitore ASC");

		LOG.info(prf + "\n" + sqlSelect.toString());
		result = namedJdbcTemplate.query(sqlSelect.toString(), params, new PbandiFornitoriRowMapper());

		/*
		 * Controllo che il numero di record restituiti non superi il limite definito
		 * nella tabella PBandi_C_Costanti
		 */
		Long maxNumFornitori = 0L;
		try {
			maxNumFornitori = configurationManager.getCurrentConfiguration().getMaxNumFornitoriRicerca();
			LOG.debug(prf + " maxNumFornitori=" + maxNumFornitori);

		} catch (Exception e) {
			LOG.error(prf + " Errore nella lettura della configurazione del ConfigurationManager.", e);
		}

		if (maxNumFornitori > 0 && result.size() > maxNumFornitori) {
			GestioneAffidamentiException gfe = new GestioneAffidamentiException(
					MessageConstants.ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI);
			throw gfe;
		}

		LOG.info(prf + " END");
		return beanUtil.transformList(result, FornitoreDTO.class);

	}

	@Override
	@Transactional
	public AffidamentoDTO findAffidamento(Long idAppalto)
			throws FileNotFoundException, IOException, GestioneAffidamentiException {
		String prf = "[AffidamentiDAOImpl::findAffidamento]";
		LOG.info(prf + " BEGIN");
		try {
			String queryAffidamento = fileSqlUtil.getQuery("Affidamento.sql");
			Object[] param = new Object[] { idAppalto };
			PbandiTAppaltoVO appalto = getJdbcTemplate().queryForObject(queryAffidamento, param,
					new AffidamentiRowMapper());
			if (appalto == null || appalto.getIdAppalto() == null)
				throw new GestioneAffidamentiException("Affidamento " + idAppalto + " non trovato.");

			String descStatoAffidamento = descStatoDaIdStatoAffidamento(appalto.getIdStatoAffidamento());

			// PBANDI_T_PROCEDURA_AGGIUDICAZ.
			String queryProcedura = fileSqlUtil.getQuery("ProceduraAggiudicaz.sql");
			Object[] paramI = new Object[] { appalto.getIdProceduraAggiudicaz() };
			PbandiTProceduraAggiudicazVO procAgg = getJdbcTemplate().queryForObject(queryProcedura, paramI,
					new ProceduraAggiudicazRowMapper());
			if (procAgg == null || procAgg.getIdProceduraAggiudicaz() == null)
				throw new GestioneAffidamentiException(
						"Proceudra di aggiudicazione " + appalto.getIdProceduraAggiudicaz() + " non trovato.");

			// VARIANTI.
			String queryVariante = fileSqlUtil.getQuery("VarianteAffidamento.sql");
			Object[] paramII = new Object[] { idAppalto };
			List<VariantiAffidamentoDescrizioneVO> varianti = getJdbcTemplate().query(queryVariante, paramII,
					new VarianteAffidamentoRowMapper());

			// FORNITORI.
			String queryFornitori = fileSqlUtil.getQuery("FornitoriAffidamento.sql");
			Object[] paramF = new Object[] { idAppalto };
			List<FornitoreAffidamentoVO> fornitori = getJdbcTemplate().query(queryFornitori, paramF,
					new FornitoreAffidamentoRowMapper());
			
			if (fornitori != null && fornitori.size() > 0) {
				for (FornitoreAffidamentoVO fornitore : fornitori) {
					String sql = "SELECT \r\n"
							+ "	CASE ptf.ID_TIPO_SOGGETTO\r\n"
							+ "		WHEN 1 THEN CONCAT(CONCAT(ptf.NOME_FORNITORE, ' '), ptf.COGNOME_FORNITORE)\r\n"
							+ "		ELSE ptf.DENOMINAZIONE_FORNITORE \r\n"
							+ "	END DENOMINAZIONE_SUBCONTRAENTE,\r\n"
							+ "	CASE ptf.CODICE_FISCALE_FORNITORE \r\n"
							+ "		WHEN NULL THEN ptf.PARTITA_IVA_FORNITORE \r\n"
							+ "		ELSE ptf.CODICE_FISCALE_FORNITORE\r\n"
							+ "	END CF_PIVA_SUBCONTRAENTE,\r\n"
							+ "	prsa.ID_SUBCONTRATTO_AFFIDAMENTO,\r\n"
							+ "	prsa.ID_FORNITORE,\r\n"
							+ "	prsa.ID_APPALTO,\r\n"
							+ "	prsa.ID_SUBCONTRAENTE,\r\n"
							+ "	prsa.DT_SUBCONTRATTO,\r\n"
							+ "	prsa.RIFERIMENTO_SUBCONTRATTO,\r\n"
							+ "	prsa.IMPORTO_SUBCONTRATTO\r\n"
							+ "FROM PBANDI_R_SUBCONTRATTO_AFFID prsa \r\n"
							+ "JOIN PBANDI_T_FORNITORE ptf ON ptf.ID_FORNITORE = prsa.ID_SUBCONTRAENTE\r\n"
							+ "WHERE prsa.ID_FORNITORE = ? AND prsa.ID_APPALTO = ?";
					Object[] paramS = new Object[] { fornitore.getIdFornitore(), idAppalto };
					List<SubcontrattoAffidVO> subcontratti = getJdbcTemplate().query(sql, paramS,
							new SubcontrattoAffidRowMapper());
					if (subcontratti != null && subcontratti.size() > 0) {
						fornitore.setSubcontrattiAffid(subcontratti);
					}
				}
			}

			// Trasforma i VO in DTO.
			ProceduraAggiudicazioneDTO procAggDTO = beanUtil.transform(procAgg, ProceduraAggiudicazioneDTO.class);
			FornitoreAffidamentoDTO[] fornitoriDTO = beanUtil.transform(fornitori, FornitoreAffidamentoDTO.class);
			if (fornitoriDTO != null && fornitoriDTO.length > 0) {
				for (FornitoreAffidamentoDTO dto : fornitoriDTO) {
					for (FornitoreAffidamentoVO vo : fornitori) {
						if (vo.getIdFornitore().equals(new BigDecimal(dto.getIdFornitore().longValue())) && vo.getSubcontrattiAffid() != null && vo.getSubcontrattiAffid().size() > 0) {
							dto.setSubcontrattiAffid(beanUtil.transformList(vo.getSubcontrattiAffid(), SubcontrattoAffidDTO.class));
						}
					}
				}
			}
			VarianteAffidamentoDTO[] variantiDTO = beanUtil.transform(varianti, VarianteAffidamentoDTO.class);

			// Verifica che non esistano esiti associati all'affidamento.
			boolean isAffidamentoRespingibile = true;
			String queryAppChecklist = fileSqlUtil.getQuery("AppaltoChecklist.sql");
			Object[] paramA = new Object[] { idAppalto };
			List<PbandiTAppaltoChecklistVO> listaChecklist = getJdbcTemplate().query(queryAppChecklist, paramA,
					new AppaltoChecklistRowMapper());

			String queryEsitiNote = fileSqlUtil.getQuery("EsitiNoteAffidamento.sql");

			for (PbandiTAppaltoChecklistVO ac : listaChecklist) {
				PbandiTEsitiNoteAffidamentVO esitoVO = new PbandiTEsitiNoteAffidamentVO();
				esitoVO.setIdChecklist(ac.getIdChecklist());
				Object[] paramE = new Object[] { ac.getIdChecklist() };
				List<PbandiTEsitiNoteAffidamentVO> esiti = getJdbcTemplate().query(queryEsitiNote, paramE,
						new EsitiNoteAffidamentoRowMapper());
				// Se esiste un esito, l'affidamento non può essere respinto.
				if (esiti.size() > 0)
					isAffidamentoRespingibile = false;
			}

			AffidamentoDTO affDTO = new AffidamentoDTO();
			affDTO = beanUtil.transform(appalto, AffidamentoDTO.class);
			affDTO.setDescStatoAffidamento(descStatoAffidamento);
			affDTO.setProceduraAggiudicazioneDTO(procAggDTO);
			affDTO.setVarianti(variantiDTO);
			affDTO.setFornitori(fornitoriDTO);
			// Jira PBANDI-2773
			affDTO.setRespingibile(isAffidamentoRespingibile);

			LOG.info(prf + " END");
			return affDTO;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}

	}

	private String descStatoDaIdStatoAffidamento(BigDecimal idStatoAffidamento) {
		String prf = "[AffidamentiDAOImpl::descStatoDaIdStatoAffidamento]";
		LOG.info(prf + " START");
		try {
			String sql = "select DESC_STATO_AFFIDAMENTO from PBANDI_D_STATO_AFFIDAMENTO where ID_STATO_AFFIDAMENTO = ?";
			Object[] param = new Object[] { idStatoAffidamento };
			String descStato = getJdbcTemplate().queryForObject(sql, param, String.class);
			LOG.info(prf + " END");
			return descStato;
		} catch (Exception ex) {
			throw ex;
		}

	}

	@Override
	@Transactional
	public VarianteAffidamentoDTO[] findVarianti(Long idUtente, VarianteAffidamentoDTO filtro)
			throws FormalParameterException {
		String prf = "[AffidamentiDAOImpl::findVarianti]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaIride", "filtroVarianteDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, filtro);

		try {

			PbandiTVariantiAffidamentiVO filtroSql = beanUtil.transform(filtro, PbandiTVariantiAffidamentiVO.class);
			LOG.info(prf + " END");
			return beanUtil.transform(genericDAO.findListWhere(filtroSql), VarianteAffidamentoDTO.class);

		} catch (Exception e) {
			LOG.error(prf + " errore nell'ottenimento delle varianti ", e);
			throw e;
		}
	}

	@Override
	@Transactional
	public String findCodiceProgetto(Long idProgetto) {
		String prf = "[AffidamentiDAOImpl::findCodiceProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] { idProgetto };
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			LOG.info(prf + " END");
			return codiceProgetto;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// METODI DELLE COMBO
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public List<NormativaAffidamentoDTO> findNormativeAffidamento() {
		String prf = "[AffidamentiDAOImpl::findCodiceProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT * FROM PBANDI_D_NORMATIVA_AFFIDAMENTO";
			List<PbandiDNormativaAffidamentoVO> normative = getJdbcTemplate().query(sql,
					new NormativeAffidamentoRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(normative, NormativaAffidamentoDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public List<TipoAffidamentoDTO> findTipologieAffidamento() {
		String prf = "[AffidamentiDAOImpl::findTipologieAffidamento]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT * FROM PBANDI_D_TIPO_AFFIDAMENTO ORDER BY DESC_TIPO_AFFIDAMENTO , DESC_TIPO_AFFIDAMENTO ASC";
			List<PbandiDTipoAffidamentoVO> tipologie = getJdbcTemplate().query(sql, new TipoAffidamentoRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(tipologie, TipoAffidamentoDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public List<TipologiaAppaltoDTO> findTipologieAppalto() {
		String prf = "[AffidamentiDAOImpl::findTipologieAppalto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT * FROM PBANDI_D_TIPOLOGIA_APPALTO WHERE  ( trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA),trunc(sysdate) -1) "
					+ " AND trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1) ) "
					+ "   ORDER BY DESC_TIPOLOGIA_APPALTO ASC";
			List<PbandiDTipologiaAppaltoVO> tipologie = getJdbcTemplate().query(sql, new TipoAppaltoRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(tipologie, TipologiaAppaltoDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public List<TipologiaAggiudicazioneDTO> findTipologieProcedureAggiudicazione(Long progrBandoLinea)
			throws Exception {
		String prf = "[AffidamentiDAOImpl::findTipologieProcedureAggiudicazione]";
		LOG.info(prf + " BEGIN");
		try {
			String queryBandoLinea = "SELECT ID_BANDO from PBANDI_R_BANDO_LINEA_INTERVENT where PROGR_BANDO_LINEA_INTERVENTO = ?";
			Object[] paramB = new Object[] { progrBandoLinea };
			BigDecimal idBando = getJdbcTemplate().queryForObject(queryBandoLinea, paramB, BigDecimal.class);

			String queryLinea = "SELECT ID_LINEA_DI_INTERVENTO FROM PBANDI_T_BANDO where ID_BANDO = ?";
			Object[] paramL = new Object[] { idBando };
			BigDecimal idLineaIntervento = getJdbcTemplate().queryForObject(queryLinea, paramL, BigDecimal.class);

			LOG.info(prf + " bando.IdLineaDiIntervento = " + idLineaIntervento);
			// Determina il tipo di programmazione
			Long idLineaDiIntervento = null;
			if (idLineaIntervento == null) {
				// Vecchia programmazione.
				LOG.info(prf + " Vecchia programmazione: forzo idLineaDiIntervento = 5");
				idLineaDiIntervento = new Long(5);
			} else {
				// Nuova programmazione.
				idLineaDiIntervento = idLineaIntervento.longValue();
			}
			LOG.info(prf + " idLineaDiIntervento = " + idLineaDiIntervento);

			// Restituisce le tipologie abbinate all'idLineaDiIntervento.
			String sql = new FileSqlUtil().getQuery("TipologieAggiudicaz.sql");
			Object[] param = new Object[] { idLineaDiIntervento };

			List<PbandiDTipologiaAggiudicazVO> vo = getJdbcTemplate().query(sql, param,
					new TipologiaAggiudicazRowMapper());

			LOG.info(prf + " END");
			return beanUtil.transformList(vo, TipologiaAggiudicazioneDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public List<MotiveAssenzaDTO> findMotiveAssenza() throws Exception {
		String prf = "[AffidamentiDAOImpl::findMotiveAssenza]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = fileSqlUtil.getQuery("MotiveAssenza.sql");
			List<PbandiDMotivoAssenzaCigVO> motive = getJdbcTemplate().query(sql, new MotiveAssenzaRowMapper());
			LOG.info(prf + " END");
			return beanUtil.transformList(motive, MotiveAssenzaDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti salvaAffidamento(Long idUtente, AffidamentoDTO affidamentoDTO) throws Exception {
		String prf = "[AffidamentiDAOImpl::salvaAffidamento]";
		LOG.info(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.TRUE);

		try {

			// Verifica se l'affidamento è presente nella PBANDI_T_AFFIDAMENTO_CHECKLIST.
			if (this.affidamentoChecklistMancante(affidamentoDTO)) {
				LOG.error(prf + " ERRORE: l'affidamento non ha alcuna checklist associata.");
				esito.setEsito(false);
				// esito.setMessage("L&rsquo;affidamento non ha alcuna checklist associata.");
				esito.setMessage("Salvataggio non effettuato!");
				return esito;
			}

			boolean nuovoAppalto = (affidamentoDTO.getIdAppalto() == null);

			// Salva la procedura di aggiudicazione in PBANDI_T_PROCEDURA_AGGIUDICAZ.
			// NOTA: va salvata prima prima la proc di agg poichè il suo ID va inserito poi
			// in PBANDI_T_APPALTO.
			BigDecimal idProcAgg = this.salvaProceduraAggiudicazione(idUtente,
					affidamentoDTO.getProceduraAggiudicazioneDTO());
			affidamentoDTO.setIdProceduraAggiudicaz(idProcAgg.longValue());
			affidamentoDTO.getProceduraAggiudicazioneDTO().setIdProceduraAggiudicaz(idProcAgg.longValue());

			// Salva l'affidamento in PBANDI_T_APPALTO.
			BigDecimal idAppalto = this.salvaAppalto(idUtente, affidamentoDTO);
			affidamentoDTO.setIdAppalto(idAppalto.longValue());

			if (nuovoAppalto) {
				// Insert in PBANDI_R_PROGETTI_APPALTI.
				PbandiRProgettiAppaltiVO progAppaltiVO = new PbandiRProgettiAppaltiVO();
				progAppaltiVO.setIdAppalto(idAppalto);
				progAppaltiVO.setIdProgetto(new BigDecimal(affidamentoDTO.getIdProgetto()));
				progAppaltiVO.setIdTipoDocumentoIndex(
						new BigDecimal(Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO));
				genericDAO.insert(progAppaltiVO);
			}

			// Restituisce in output l'id dell'affidamento e della procedura di
			// aggiudicazione.
			esito.setAffidamentoDTO(affidamentoDTO);
			esito.setParams(new String[] { affidamentoDTO.getIdAppalto().toString() });
			esito.setMessage(MessageConstants.MSG_SALVA_SUCCESSO);

		} catch (Exception ex) {
			LOG.error(prf + " errore nel salvataggio dell'affidamento. ", ex);
			throw ex;
		}
		LOG.info(prf + " END");
		return esito;
//		EsitoOperazioni esito = new EsitoOperazioni();
//		esito.setEsito(Boolean.TRUE);
//		try {			
//			
//			// Verifica se l'affidamento è presente nella PBANDI_T_AFFIDAMENTO_CHECKLIST.
//			if (this.affidamentoChecklistMancante(affidamentoDTO)) {
//				logger.error("ERRORE: l'affidamento non ha alcuna checklist associata.");
//				esito.setEsito(false);
//				//esito.setMessage("L&rsquo;affidamento non ha alcuna checklist associata.");
//				esito.setMsg("Salvataggio non effettuato!");
//				return esito;
//			}	
//			
//			boolean nuovoAppalto = (affidamentoDTO.getIdAppalto() == null);
//			LOG.info(prf + "nuovoAppalto: "+ nuovoAppalto);
//			// Salva la procedura di aggiudicazione in PBANDI_T_PROCEDURA_AGGIUDICAZ.
//			// NOTA: va salvata prima prima la proc di agg poichè il suo ID va inserito poi in PBANDI_T_APPALTO.
//			BigDecimal idProcAgg = this.salvaProceduraAggiudicazione(idUtente, affidamentoDTO.getProceduraAggiudicazioneDTO());
//			
//			LOG.info(prf + "idProcAgg: "+ idProcAgg);
//			
//			
//			affidamentoDTO.setIdProceduraAggiudicaz(idProcAgg.longValue());
//			affidamentoDTO.getProceduraAggiudicazioneDTO().setIdProceduraAggiudicaz(idProcAgg.longValue());
//			
//			// Salva l'affidamento in PBANDI_T_APPALTO.
//			BigDecimal idAppalto = this.salvaAppalto(idUtente, affidamentoDTO);
//			affidamentoDTO.setIdAppalto(idAppalto.longValue());
//			
//			if (nuovoAppalto) {
//				// Insert in PBANDI_R_PROGETTI_APPALTI.
//				String queryProgAppalti = fileSqlUtil.getQuery("SalvaProgettiAppalti.sql");
//				Object[] param = new Object[] {affidamentoDTO.getIdProgetto(), idAppalto, new BigDecimal(Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO)};
//				getJdbcTemplate().update(queryProgAppalti, param);
//								
//			}
//			
//			esito.setEsito(true);
//			esito.setMsg("Salvataggio avvenuto con successo.");
//			// Restituisce in output l'id dell'affidamento e della procedura di aggiudicazione.
//			String[] res = new String[] {affidamentoDTO.getIdAppalto().toString(), affidamentoDTO.getIdProceduraAggiudicaz().toString()};
//			esito.setParams(res);
//			
//			
//			return esito;
//		} catch(Exception ex){
//			LOG.error(prf +"  errore nel salvataggio dell'affidamento. " + ex.getMessage() + "caused by "+ ex.getCause());
//			throw ex;
//		}	

	}

	private BigDecimal salvaAppalto(Long idUtente, AffidamentoDTO dto) throws Exception {
		String prf = "[AffidamentiDAOImpl::salvaAppalto]";
		LOG.info(prf + " BEGIN");
		java.sql.Date oggi = DateUtil.getSysdate();
		// Salva l'affidamento in PBANDI_T_APPALTO.
		PbandiTAppaltoVO appaltoVO = new PbandiTAppaltoVO();
		appaltoVO = beanUtil.transform(dto, PbandiTAppaltoVO.class);

		if (appaltoVO.getIdAppalto() == null) {
			// Inserimento.
			appaltoVO.setDtInserimento(oggi);
			appaltoVO.setIdUtenteIns(new BigDecimal(idUtente));
			appaltoVO = genericDAO.insert(appaltoVO);
			if (appaltoVO.getIdAppalto() == null) {
				throw new Exception("L'inserimento non ha restituito alcun IdAppalto.");
			} else {
				LOG.info(prf + " inserito record con id " + appaltoVO.getIdAppalto());
			}
		} else {
			// Modifica.
			appaltoVO.setDtAggiornamento(oggi);
			appaltoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(appaltoVO);
		}
		return appaltoVO.getIdAppalto();
//		try {
//			BigDecimal idAppalto = nuovoId(Constants.NOME_SEQ_APPLATO);
//			
//			String sql = fileSqlUtil.getQuery("SalvaAppalto.sql");
//			LOG.info(prf + " query: "+ sql);
//			Object[] param = new Object[16];
//			param[0] = dto.getImportoContratto();
//			param[1] = dto.getIdTipoAffidamento();
//			param[2] = dto.getOggettoAppalto();
//			param[3] = dto.getIdTipologiaAppalto();			
//			param[4] = dto.getDtFirmaContratto();
//			param[5] = dto.getDtInserimento();
//			param[6] = dto.getIdStatoAffidamento();		
//			param[7] = dto.getBilancioPreventivo();	
//			param[8] = dto.getImpRendicontabile();
//			param[9] = dto.getIdProceduraAggiudicaz();
//			param[10] = dto.getInterventoPisu();
//			param[11] = dto.getIdNorma();
//			param[12] = idUtente;
//			param[13] = dto.getDtInizioPrevista();
//			param[14] = dto.getIdAppalto();
//			param[15] = dto.getSopraSoglia();
//			
//			getJdbcTemplate().update(sql, param);	
//			
//			LOG.info(prf + " END");
//			return idAppalto;
//		} catch (Exception e) {
//			throw e;
//		}
	}

	private BigDecimal salvaProceduraAggiudicazione(Long idUtente, ProceduraAggiudicazioneDTO dto) throws Exception {
		String prf = "[AffidamentiDAOImpl::salvaProceduraAggiudicazione]";
		LOG.info(prf + " BEGIN");
		java.sql.Date oggi = DateUtil.getSysdate();
		PbandiTProceduraAggiudicazVO procAggVO = new PbandiTProceduraAggiudicazVO();
		procAggVO = beanUtil.transform(dto, PbandiTProceduraAggiudicazVO.class);
		// logger.shallowDump(procAggDTO, "info");
		// logger.shallowDump(procAggVO, "info");
		if (procAggVO.getIdProceduraAggiudicaz() == null) {
			// Inserimento.
			procAggVO.setDtInizioValidita(oggi);
			procAggVO.setIdUtenteIns(new BigDecimal(idUtente));
			procAggVO = genericDAO.insert(procAggVO);
			if (procAggVO.getIdProceduraAggiudicaz() == null) {
				throw new Exception("L'inserimento non ha restituito alcun IdProceduraAggiudicaz.");
			} else {
				logger.info("salvaProceduraAggiudicazione(): inserito record con id "
						+ procAggVO.getIdProceduraAggiudicaz());
			}
		} else {
			// Modifica.
			procAggVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(procAggVO);
		}
		return procAggVO.getIdProceduraAggiudicaz();
//		try {
//			BigDecimal idProceduraAggiudicaz = nuovoId(Constants.NOME_SEQ_PROCEDURA_AGGIUDI);
//			
//			String sql = fileSqlUtil.getQuery("SalvaProceduraAggiudicaz.sql");
//			LOG.info(prf + " query: "+ sql);
//			Object[] param = new Object[10];
//			param[0] = dto.getDtAggiudicazione();
//			param[1] = dto.getImporto();
//			param[2] = dto.getIdMotivoAssenzaCIG();
//			param[3] = idProceduraAggiudicaz;
//			param[4] = dto.getIdTipologiaAggiudicaz();
//			param[5] = dto.getCodProcAgg();
//			param[6] = idUtente;
//			param[7] = dto.getDescProcAgg();
//			param[8] = dto.getIdProgetto();
//			param[9] = dto.getDtInizioValidita();
//			
//			getJdbcTemplate().update(sql, param);	
//			
//			LOG.info(prf + " END");
//			return idProceduraAggiudicaz;
//		} catch (Exception e) {
//			throw e;
//		}

	}

	private BigDecimal nuovoId(String nomeSequence) {
		String prf = "[AffidamentiDAOImpl::nuovoId] ";
		LOG.info(prf + " START");
		LOG.info(prf + "nomeSequence: " + nomeSequence);
		BigDecimal id = null;
		try {
			StringBuilder sqlSeq = new StringBuilder();
			sqlSeq.append("SELECT ").append(nomeSequence).append(".nextval from dual");
			LOG.info("\n" + sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			LOG.info("Nuovo id = " + id.longValue());
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			throw e;
		}
		return id;
	}

	private boolean affidamentoChecklistMancante(AffidamentoDTO affidamentoDTO) throws Exception {
		String prf = "[AffidamentiDAOImpl::affidamentoChecklistMancante]";
		LOG.info(prf + " START");
		try {
			PbandiTAffidamentoChecklistVO affChecklist = new PbandiTAffidamentoChecklistVO();
			if (affidamentoDTO.getIdNorma() != null)
				affChecklist.setIdNorma(new BigDecimal(affidamentoDTO.getIdNorma()));
			if (affidamentoDTO.getIdTipoAffidamento() != null)
				affChecklist.setIdTipoAffidamento(new BigDecimal(affidamentoDTO.getIdTipoAffidamento()));
			if (affidamentoDTO.getIdTipologiaAppalto() != null)
				affChecklist.setIdTipoAppalto(new BigDecimal(affidamentoDTO.getIdTipologiaAppalto()));
			if (affidamentoDTO.getProceduraAggiudicazioneDTO() != null
					&& affidamentoDTO.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz() != null)
				affChecklist.setIdTipologiaAggiudicaz(
						new BigDecimal(affidamentoDTO.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz()));
			affChecklist.setSopraSoglia(affidamentoDTO.getSopraSoglia());

			List<PbandiTAffidamentoChecklistVO> lista = null;
			if (StringUtil.isEmpty(affChecklist.getSopraSoglia())) {
				NullCondition<PbandiTAffidamentoChecklistVO> nullIdLineaCondition = new NullCondition<PbandiTAffidamentoChecklistVO>(
						PbandiTAffidamentoChecklistVO.class, "sopraSoglia");

				AndCondition<PbandiTAffidamentoChecklistVO> filtro = new AndCondition<PbandiTAffidamentoChecklistVO>(
						new FilterCondition<PbandiTAffidamentoChecklistVO>(affChecklist), nullIdLineaCondition);

				lista = genericDAO.findListWhere(filtro);
			} else {
				lista = genericDAO.findListWhere(affChecklist);
			}
			return (lista.size() == 0);
		} catch (Exception e) {
			LOG.info(prf + " error: " + e.getMessage() + " END");
			throw e;
		}
//		try {
//			PbandiTAffidamentoChecklistVO affChecklist = new PbandiTAffidamentoChecklistVO();
//			if (affidamentoDTO.getIdNorma() != null)
//				affChecklist.setIdNorma(new BigDecimal(affidamentoDTO.getIdNorma()));
//			if (affidamentoDTO.getIdTipoAffidamento() != null)
//				affChecklist.setIdTipoAffidamento(new BigDecimal(affidamentoDTO.getIdTipoAffidamento()));
//			if (affidamentoDTO.getIdTipologiaAppalto() != null)
//				affChecklist.setIdTipoAppalto(new BigDecimal(affidamentoDTO.getIdTipologiaAppalto()));
//			if (affidamentoDTO.getProceduraAggiudicazioneDTO() != null &&
//				affidamentoDTO.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz() != null)
//				affChecklist.setIdTipologiaAggiudicaz(new BigDecimal(affidamentoDTO.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz()));
//			
//			affChecklist.setSopraSoglia(affidamentoDTO.getSopraSoglia());
//			BigDecimal idTipoAffidamento = affChecklist.getIdTipoAffidamento();
//			BigDecimal idTipologiaAggiudicaz = affChecklist.getIdTipologiaAggiudicaz();
//			BigDecimal idNorma = affChecklist.getIdNorma();
//			BigDecimal idTipoAppalto = affChecklist.getIdTipoAppalto();
//			
//			List<PbandiTAffidamentoChecklistVO> lista = null;
//			String sql = fileSqlUtil.getQuery("AffidamentoChecklist.sql");	
//			StringBuilder sql1 = new StringBuilder();
//			sql1.append(sql);			
//			if (StringUtil.isEmpty(affChecklist.getSopraSoglia())) {
//				sql1.append(" and SOPRA_SOGLIA is null");
//				Object[] param = new Object[] {idTipoAffidamento, idTipologiaAggiudicaz, idNorma, idTipoAppalto};
//				lista = getJdbcTemplate().query(sql1.toString(), param, new AffidamentoChecklistRowMapper());				
//				
//			} else {
//				sql1.append(" and SOPRA_SOGLIA = ?");
//				Object[] param = new Object[] {idTipoAffidamento, idTipologiaAggiudicaz, idNorma, idTipoAppalto, affChecklist.getSopraSoglia()};
//				lista = getJdbcTemplate().query(sql1.toString(), param, new AffidamentoChecklistRowMapper());	
//			}
//			
//			
//			return lista.size() == 0;
//		} catch (Exception e) {
//			LOG.info(prf + " END");
//			throw e;
//		}

	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti cancellaAffidamento(Long idUtente, String idIride, Long idAppalto)
			throws FormalParameterException, GestioneAffidamentiException {
		String prf = "[AffidamentiDAOImpl::cancellaAffidamento]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idAppalto);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(true);
		try {
			logger.info("eliminaAffidamento(): idAppalto = " + idAppalto);

			// Cancello le varianti associate.
			PbandiTVariantiAffidamentiVO variante = new PbandiTVariantiAffidamentiVO();
			variante.setIdAppalto(new BigDecimal(idAppalto));
			genericDAO.deleteWhere(new FilterCondition<PbandiTVariantiAffidamentiVO>(variante));

			// Cancello gli allegati associati.
			PbandiTFileEntitaVO allegato = new PbandiTFileEntitaVO();
			allegato.setIdTarget(new BigDecimal(idAppalto));
			allegato.setIdEntita(new BigDecimal(Constants.ID_ENTITA_PBANDI_T_APPALTO));
			genericDAO.deleteWhere(new FilterCondition<PbandiTFileEntitaVO>(allegato));

			// Cancello i fornitori associati.
			PbandiRFornitoreAffidamentoVO fornitore = new PbandiRFornitoreAffidamentoVO();
			fornitore.setIdAppalto(new BigDecimal(idAppalto));
			genericDAO.deleteWhere(new FilterCondition<PbandiRFornitoreAffidamentoVO>(fornitore));
			
			// Cancello i subcontratti associati
			String sql = "DELETE FROM PBANDI_R_SUBCONTRATTO_AFFID WHERE ID_APPALTO = ?";
			Object[] paramS = new Object[] { idAppalto };
			getJdbcTemplate().update(sql, paramS);

			// Leggo l'affidamento per ottenere l'id della procedura di aggiudicazione
			// associata.
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));
			appalto = genericDAO.findSingleWhere(appalto);
			if (appalto == null || appalto.getIdAppalto() == null)
				throw new GestioneAffidamentiException(
						"Cancellazione affidamento fallita: Affidamento " + idAppalto + " non trovato.");
			if (appalto.getIdProceduraAggiudicaz() == null)
				throw new GestioneAffidamentiException(
						"Cancellazione affidamento fallita: IdProceduraAggiudicaz non trovato.");

			// Leggo la procedura di aggiudicazione per recuperare l'id del progetto.
			PbandiTProceduraAggiudicazVO procAgg = new PbandiTProceduraAggiudicazVO();
			procAgg.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
			procAgg = genericDAO.findSingleWhere(procAgg);
			if (procAgg == null || procAgg.getIdProceduraAggiudicaz() == null)
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita: ProceduraAggiudicaz "
						+ procAgg.getIdProceduraAggiudicaz() + " non trovata.");
			if (procAgg.getIdProgetto() == null)
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita: IdProgetto non trovato.");

			// Cancello relazione in PBANDI_R_PROGETTI_APPALTI.
			PbandiRProgettiAppaltiVO pbandiRProgettiAppaltiVO = new PbandiRProgettiAppaltiVO();
			pbandiRProgettiAppaltiVO.setIdAppalto(appalto.getIdAppalto());
			pbandiRProgettiAppaltiVO.setIdProgetto(procAgg.getIdProgetto());
			pbandiRProgettiAppaltiVO = genericDAO.findSingleWhere(pbandiRProgettiAppaltiVO);
			if (!genericDAO.delete(pbandiRProgettiAppaltiVO))
				throw new GestioneAffidamentiException("Cancellazione relazione affidamento-progetto fallita.");

			// Cancello l'affidamento in PBANDI_T_APPALTO.
			PbandiTAppaltoVO vo1 = new PbandiTAppaltoVO();
			vo1.setIdAppalto(appalto.getIdAppalto());
			if (!genericDAO.delete(vo1))
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita.");

			// Cancello la procedura di aggiudicazione associata all'affidamento in
			// PBANDI_T_PROCEDURA_AGGIUDICAZ.
			PbandiTProceduraAggiudicazVO vo2 = new PbandiTProceduraAggiudicazVO();
			vo2.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
			if (!genericDAO.delete(vo2))
				throw new GestioneAffidamentiException("Cancellazione procedura aggiudicazione fallita.");

			esito.setMessage(MessageConstants.MSG_CANCELLA_AFFIDAMENTO_SUCCESSO);

		} catch (GestioneAffidamentiException ex) {
			throw ex;
		} catch (Exception ex) {
			String msg = "Errore nell'eliminazione dell'affidamento.";
			logger.error(msg, ex);
			throw new GestioneAffidamentiException(msg, ex);
		}
		return esito;
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti inviaInVerifica(Long idUtente, ParamInviaInVerificaDTO dto) throws Exception {
		String prf = "[AffidamentiDAOImpl::inviaInVerifica]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaIride", "paramInviaInVerificaDTO" };
		// ValidatorInput.verifyNullValue(nameParameter, idUtente, dto);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.TRUE);

		LOG.info(prf + " inviaInVerifica(): inizio");
		try {
			//Lettura dei destinatari messa qui in modo che se non ne venissero trovati 
			//viene lanciato un errore prima di fare le altre operazioni 
			//puer essendoci @Transactional sembra che in caso di errore il roolback non avvenga
			Long idProgetto = dto.getIdProgetto();
			String descBreveRuoloEnte = dto.getDescBreveRuoloEnte();
			String destinatari = leggiIndirizzoMail(idProgetto, descBreveRuoloEnte);			
			LOG.info(prf + " Destinatari: " + destinatari);
			
			
			
			
			
			java.sql.Date oggi = DateUtil.getSysdate();
			String flagInviato = "S";
			Long idAppalto = dto.getIdAppalto();

			// Aggiorna lo stato dell'appalto in IN_VERIFICA (id 2).
			LOG.info(prf + " stato affidamento = IN_VERIFICA");
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));
			appalto.setIdStatoAffidamento(new BigDecimal(Constants.ID_STATO_AFFIDAMENTO_INVERIFICA));
			appalto.setDtAggiornamento(oggi);
			appalto.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(appalto);

			// Marca come inviati le varianti.
			LOG.info("inviaInVerifica(): marca le varianti");
			PbandiTVariantiAffidamentiVO affidamentoVO = new PbandiTVariantiAffidamentiVO();
			affidamentoVO.setIdAppalto(new BigDecimal(idAppalto));
			List<PbandiTVariantiAffidamentiVO> affidamenti = genericDAO.findListWhere(affidamentoVO);
			for (PbandiTVariantiAffidamentiVO item : affidamenti) {
				if (nonAncoraInviato(item.getFlgInvioVerificaAffidamento())) {
					PbandiTVariantiAffidamentiVO vo = new PbandiTVariantiAffidamentiVO(item.getIdVariante());
					vo.setFlgInvioVerificaAffidamento(flagInviato);
					vo.setDtInvioVerificaAffidamento(oggi);
					genericDAO.update(vo);
				}
			}

			// Marca come inviati gli allegati.
			LOG.info(prf + " inviaInVerifica(): marca gli allegati");
			String sql = fileSqlUtil.getQuery("AffidamentoFiles.sql");
			Object[] params = new Object[] { idAppalto };
			List<ArchivioFileVO> allegati = getJdbcTemplate().query(sql, params, new ArchivioFileRowMapper());
			for (ArchivioFileVO item : allegati) {
				// Cerco il record PBANDI_T_FILE_ENTITA dove salvare il flag per invio verifica.
				PbandiTFileEntitaVO vo = new PbandiTFileEntitaVO();
				vo.setIdFileEntita(item.getIdFileEntita());
				vo = genericDAO.findSingleWhere(vo);
				// Setto i campi da salvare e aggiorno il record.
				if (nonAncoraInviato(vo.getFlagEntita())) {
					vo.setFlagEntita(flagInviato);
					vo.setDtEntita(oggi);
					genericDAO.update(vo);
				}
			}

			// Marca come inviati i fornitori.
			LOG.info(prf + " inviaInVerifica(): marca i fornitori");
			PbandiRFornitoreAffidamentoVO fornitoreVO = new PbandiRFornitoreAffidamentoVO();
			fornitoreVO.setIdAppalto(new BigDecimal(idAppalto));
			List<PbandiRFornitoreAffidamentoVO> fornitori = genericDAO.findListWhere(fornitoreVO);
			for (PbandiRFornitoreAffidamentoVO item : fornitori) {
				if (nonAncoraInviato(item.getFlgInvioVerificaAffidamento())) {
					PbandiRFornitoreAffidamentoVO vo = new PbandiRFornitoreAffidamentoVO();
					vo.setIdFornitore(item.getIdFornitore());
					vo.setIdAppalto(item.getIdAppalto());
					vo.setIdTipoPercettore(item.getIdTipoPercettore());
					vo.setFlgInvioVerificaAffidamento(flagInviato);
					vo.setDtInvioVerificaAffidamento(oggi);
					genericDAO.update(vo);
				}
			}
			// Invia una mail di notifica all'Istruttore affidamenti.
			this.inviaMail(dto, destinatari);

		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
		LOG.info(prf + " END");
		esito.setMessage(MessageConstants.MSG_INVIA_IN_VERIFICA_SUCCESSO);
		return esito;
	}

	@Override
	@Transactional
	public ArrayList<CodiceDescrizioneDTO> findTipologieVarianti(UserInfoSec userInfo, String tipologieVarianti)
			throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException {
		String prf = "[AffidamentiDAOImpl::findTipologieVarianti]";
		LOG.info(prf + " BEGIN");

		Decodifica[] tipologie = gestioneDatiDiDominioBusinessImpl.findDecodifiche(userInfo.getIdUtente(),
				userInfo.getIdIride(), tipologieVarianti);
		LOG.info(prf + " END");
		return this.getListCodiciDescrizione(tipologie);
	}

	@Override
	@Transactional
	public ArrayList<CodiceDescrizioneDTO> findRuoli(UserInfoSec userInfo, String tipoPercettore)
			throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException {
		String prf = "[AffidamentiDAOImpl::findRuoli]";
		LOG.info(prf + " BEGIN");

		Decodifica[] ruoli = gestioneDatiDiDominioBusinessImpl.findDecodifiche(userInfo.getIdUtente(),
				userInfo.getIdIride(), tipoPercettore);
		LOG.info(prf + " END");

		return this.getListCodiciDescrizione(ruoli);

	}

	private ArrayList<CodiceDescrizioneDTO> getListCodiciDescrizione(Decodifica[] list) {
		ArrayList<CodiceDescrizioneDTO> listCD = new ArrayList<CodiceDescrizioneDTO>();
		for (int i = 0; i < list.length; i++) {
			Decodifica d = list[i];
			CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
			cd.setCodice(beanUtil.transform(d.getId(), String.class));
			cd.setDescrizione(d.getDescrizione());
			listCD.add(cd);
		}
		return listCD;
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti creaVariante(Long idUtente, String idIride, VarianteAffidamentoDTO dto)
			throws Exception {
		String prf = "[AffidamentiDAOImpl::creaVariante]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "identitaIride", "varianteDTO" };
		// ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, dto);

		LOG.info(prf + " " + dto.getDescrizioneTipologiaVariante());

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiTVariantiAffidamentiVO newVariante = beanUtil.transform(dto, PbandiTVariantiAffidamentiVO.class);
			if (newVariante != null) {
				if (newVariante.getIdVariante() == null)
					genericDAO.insert(newVariante);
				else
					genericDAO.update(newVariante);
				esito.setEsito(Boolean.TRUE);
				esito.setMessage(Constants.MSG_SALVA_SUCCESSO);
			}
		} catch (Exception e) {
			LOG.error(prf + " Errore nel salvataggio della variante " + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti creaFornitore(Long idUtente, String idIride, FornitoreAffidamentoDTO dto)
			throws Exception {
		String prf = "[AffidamentiDAOImpl::creaFornitore]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "identitaIride", "varianteDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, dto);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);
		try {
			esito.setEsito(Boolean.FALSE);

			PbandiRFornitoreAffidamentoVO vo = beanUtil.transform(dto, PbandiRFornitoreAffidamentoVO.class);
			if (vo == null)
				throw new Exception("fornitore nullo.");

			// vo = genericDAO.insert(vo) ;
			if (genericDAO.insert(vo) == null)
				throw new Exception("Fallita insert su pbandi_r_fornitore_affidamento.");
			esito.setEsito(Boolean.TRUE);
			esito.setMessage(Constants.MSG_SALVA_SUCCESSO);
		} catch (Exception e) {
			LOG.error(prf + " Errore nel salvataggio della variante " + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti cancellaVariante(Long idUtente, String idIride, Long idVariante)
			throws FormalParameterException, GestioneAffidamentiException {
		String prf = "[AffidamentiDAOImpl::cancellaVariante]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaIride", "idVariante" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idVariante);
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);

		try {
			PbandiTVariantiAffidamentiVO varianteToDelete = new PbandiTVariantiAffidamentiVO(
					new BigDecimal(idVariante));
			Boolean esitoOperatione = genericDAO.delete(varianteToDelete);
			esito.setEsito(esitoOperatione);
			esito.setEsito(esitoOperatione);
			if (esitoOperatione)
				esito.setMessage(MessageConstants.MSG_CANCELLA_VARIANTE_SUCCESSO);
			else
				esito.setMessage(ErrorMessages.ERRORE_CANCELLA_VARIANTE);

		} catch (Exception ex) {
			LOG.error(prf + "[eliminaVariante] errore nell'eliminazione della Variante ", ex);
			throw new GestioneAffidamentiException("[eliminaVariante] errore nell'eliminazione della Variante ", ex);
		}
		return esito;
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti cancellaFornitore(Long idUtente, String idIride, Long idFornitore, Long idAppalto,
			Long idTipoPercettore) throws FormalParameterException, GestioneAffidamentiException {
		String prf = "[AffidamentiDAOImpl::cancellaFornitore]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "idIride", "idFornitore", "idAppalto", "idTipoPercettore" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idFornitore, idAppalto, idTipoPercettore);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiRFornitoreAffidamentoVO vo = new PbandiRFornitoreAffidamentoVO();
			vo.setIdFornitore(new BigDecimal(idFornitore));
			vo.setIdAppalto(new BigDecimal(idAppalto));
			vo.setIdTipoPercettore(new BigDecimal(idTipoPercettore));
			Boolean esitoOperatione = genericDAO.delete(vo);
			esito.setEsito(esitoOperatione);
			if (esitoOperatione)
				esito.setMessage(MessageConstants.MSG_CANCELLA_FORNITORE_SUCCESSO);
			else
				esito.setMessage(ErrorMessages.ERRORE_CANCELLA_FORNITORE);

		} catch (Exception ex) {
			logger.error("[eliminaFornitoreAffidamento] errore nell'eliminazione del fornitore.", ex);
			throw new GestioneAffidamentiException("[eliminaVariante] errore nell'eliminazione del fornitore.", ex);
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoScaricaDocumentoDTO findDocumento(Long idUtente, String idIride, Long idDocumentoIndex)
			throws Exception {
		String prf = "[AffidamentiDAOImpl::findDocumento]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "identitaDigitale", "idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idDocumentoIndex);

		LOG.info(prf + " scaricaDocumento for idDocumentoIndex:" + idDocumentoIndex);
		EsitoScaricaDocumentoDTO esitoScaricaDocumentoDTO = new EsitoScaricaDocumentoDTO();
		esitoScaricaDocumentoDTO.setEsito(Boolean.FALSE);

		try {

			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO(
					BigDecimal.valueOf(idDocumentoIndex));
			documentoIndexVO = genericDAO.findSingleWhere(documentoIndexVO);

			String tipoDocumento = descBreveDaIdTipoIndex(documentoIndexVO.getIdTipoDocumentoIndex());

			LOG.info(prf + " nomeDocumento: " + documentoIndexVO.getNomeDocumento() + " tipoDocumento: "
					+ tipoDocumento);

			java.io.File file = fileApiServiceImpl.downloadFile(documentoIndexVO.getNomeDocumento(), tipoDocumento);

			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			esitoScaricaDocumentoDTO.setBytesDocumento(fileBytes);

			LOG.info(prf + " sdocumentoIndexVO.getMessageDigest(): " + documentoIndexVO.getMessageDigest());
			if (documentoIndexVO.getMessageDigest() == null) {
				BigDecimal idTipoDocumentoIndex = documentoIndexVO.getIdTipoDocumentoIndex();
				PbandiCTipoDocumentoIndexVO pbandiCTipoDocumentoIndexVO = new PbandiCTipoDocumentoIndexVO(
						idTipoDocumentoIndex);
				pbandiCTipoDocumentoIndexVO = genericDAO.findSingleWhere(pbandiCTipoDocumentoIndexVO);
				LOG.info("pbandiCTipoDocumentoIndexVO.getFlagFirmabile(): "
						+ pbandiCTipoDocumentoIndexVO.getFlagFirmabile());
				if (pbandiCTipoDocumentoIndexVO.getFlagFirmabile() != null
						&& pbandiCTipoDocumentoIndexVO.getFlagFirmabile().equalsIgnoreCase("S")) {
					String shaHex = DigestUtils.shaHex(fileBytes);
					documentoIndexVO.setMessageDigest(shaHex);
					LOG.info(prf + " ****** UPDATING DOC INDEX with MESSGE DIGEST ****** " + shaHex);
					genericDAO.update(documentoIndexVO);
				}
			}

			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null

			esitoScaricaDocumentoDTO.setNomeFile(documentoIndexVO.getNomeFile());
			// esitoScaricaDocumentoDTO.setParams(new String[] { doc.getMimeType() });
			esitoScaricaDocumentoDTO.setEsito(Boolean.TRUE);
		} catch (Exception e) {
			LOG.error(prf + " Impossibile recuperare il documento: " + e.getMessage());
			throw e;
		}

		return esitoScaricaDocumentoDTO;
	}

	@Override
	@Transactional
	public AffidamentoCheckListDTO[] findAllAffidamentoChecklist() {
		String prf = "[AffidamentiDAOImpl::findAllAffidamentoChecklist]";
		LOG.info(prf + " BEGIN");

		try {
			String sql = "select ID_TIPO_AFFIDAMENTO as idTipoAffidamento , ID_TIPOLOGIA_AGGIUDICAZ as idTipologiaAggiudicaz ,"
					+ " ID_NORMA as idNorma , ID_MODELLO_CD as idModelloCd , RIF_CHECKLIST_AFFIDAMENTI as rifChecklistAffidamenti , "
					+ " ID_AFFIDAMENTO_CHECKLIST as idAffidamentoChecklist , ID_TIPO_APPALTO as idTipoAppalto ,"
					+ "  ID_MODELLO_CL as idModelloCl , SOPRA_SOGLIA as sopraSoglia, ID_LINEA_DI_INTERVENTO as idLineaDiIntervento from PBANDI_T_AFFIDAMENTO_CHECKLIST ";
			LOG.debug(prf + " sql=" + sql);
			List<it.csi.pbandi.pbwebrce.integration.vo.affidamenti.PbandiTAffidamentoChecklistVO> vo = getJdbcTemplate()
					.query(sql, new AffidamentoChecklistRowMapper());
			return beanUtil.transform(vo, AffidamentoCheckListDTO.class);
		} catch (Exception e) {
			LOG.error(prf + "Exception = " + e.getMessage());
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
	}

	public String descBreveDaIdTipoIndex(BigDecimal idTipoIndex) {
		String prf = "[AffidamentiDAOImpl::descBreveDaIdTipoIndex]";
		LOG.info(prf + " START");
		StringBuilder queryTipoIndex = new StringBuilder();
		queryTipoIndex.append("SELECT * from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX = ")
				.append(idTipoIndex);
		TipoDocumentoIndexVO tipoDocumentoVO = getJdbcTemplate().queryForObject(queryTipoIndex.toString(),
				new TipoDocumentoIndexMapper());
		LOG.info(prf + " END");
		return beanUtil.transform(tipoDocumentoVO.getDescBreveTipoDocIndex(), String.class);
	}

	private void inviaMail(ParamInviaInVerificaDTO dto, String emailDestinatario) throws Exception {
		String prf = "AffidamentiDAOImpl::inviaMail";
		LOG.info(prf + " START");
		String mittente = leggiCostante(Constants.COSTANTE_INDIRIZZO_EMAIL_MITTENTE);
		
		
		String destinatari;
		if (emailDestinatario != null && emailDestinatario.length() > 0) {
			destinatari = emailDestinatario;
		} else {
			destinatari = leggiCostante(Constants.COSTANTE_INDIRIZZO_EMAIL_AFFIDAMENTI); // più mail separate da
																							// virgola
		}

		// String mittente = "assistenzapiattaforma.bandi@csi.it";
		// String destinatari =
		// "controllo.affidamentifesr@regione.piemonte.it,fassilabebe.ayele@consulenti.csi.it";
		// // più mail separate da virgola

		// Passa da una stringa di mail separate da virgola ad una lista di stringhe.
		List<String> separatedMailAddresses = new ArrayList<String>();
		for (String mailAddress : destinatari.split(Constants.MAIL_ADDRESSES_SEPARATOR)) {
			separatedMailAddresses.add(mailAddress.trim());
		}

		// PbandiTProgettoVO progetto =
		// getProgettoByIdAppalto(BigDecimal.valueOf(idAppalto));
		// PbandiRBandoLineaInterventVO bandoLinea =
		// getBandoLineaByIdProgetto(progetto.getIdProgetto());

		PbandiTAppaltoVO appalto = new PbandiTAppaltoVO(new BigDecimal(dto.getIdAppalto()));
		appalto = genericDAO.findSingleWhere(appalto);

		String sql = "select OGGETTO_APPALTO from PBANDI_T_APPALTO where ID_APPALTO = ?";
		Object[] param = new Object[] { dto.getIdAppalto() };
		String oggetoAppalto = getJdbcTemplate().queryForObject(sql, param, String.class);

		String oggi = DateUtil.getData();

		String oggetto = "Verifica Affidamento - " + dto.getNomeBandoLinea() + " - Cod. Progetto "
				+ dto.getCodiceProgettoVisualizzato();
		// oggetto = oggetto.replace('“', '"');

		String testo = "Con la presente si notifica che in data " + oggi + " il beneficiario " + dto.getBeneficiario()
				+ " ha inviato in verifica l'affidamento con oggetto " + oggetoAppalto + "' per il progetto '"
				+ dto.getTitoloProgetto() + "' bando '" + dto.getNomeBandoLinea()
				+ "'.\n\nI dettagli dell'affidamento sono consultabili sul Gestionale Finanziamenti \nall'indirizzo http://www.sistemapiemonte.it/finanziamenti/bandi/";
		// testo = testo.replace('“', '"');

		MailVO mailVO = new MailVO();
		mailVO.setToAddresses(separatedMailAddresses);
		mailVO.setFromAddress(mittente);
		mailVO.setSubject(oggetto);
		mailVO.setContent(testo);

		mailUtil.send(mailVO);
		LOG.info(prf + " END ****EMAIL SENT SUCCESSFULLY*******");
	}
	
	
	private String leggiIndirizzoMail(Long idProgetto, String  descrBreveRuoloEnte)throws Exception {
		
		if(idProgetto == null)
			throw new Exception("IdProgetto non valorizzato");
		if(descrBreveRuoloEnte == null)
			throw new Exception("DescBreveRuoloEnte non valorizzata");
		
		String mail = null;
		
		
		
		StringBuilder query = new StringBuilder();
		query.append(
						  "SELECT prblec.indirizzo_mail \r\n"
						+ "FROM PBANDI_V_BANDO_LINEA_ENTE_COMP pvblec \r\n"
						+ "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec \r\n"
						+ "	ON  pvblec.PROGR_BANDO_LINEA_ENTE_COMP = prblec.PROGR_BANDO_LINEA_ENTE_COMP \r\n"
						+ "JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA pdrec \r\n"
						+ "	ON pvblec.ID_RUOLO_ENTE_COMPETENZA = pdrec.ID_RUOLO_ENTE_COMPETENZA \r\n"
						+ "WHERE pvblec.DESC_BREVE_RUOLO_ENTE = ? \r\n"
						+ "AND prblec.PROGR_BANDO_LINEA_INTERVENTO = (\r\n"
						+ "											SELECT progr_bando_linea_intervento \r\n"
						+ "											FROM PBANDI_V_PROGETTI_BANDO pvpb \r\n"
						+ "											WHERE id_progetto= ? \r\n"
						+ "										  ) ");
		
		//LOG.info("\nQuery mail:\n" + query.toString());
		Object[] param = new Object[] {descrBreveRuoloEnte, idProgetto};
		
		List<Email> tipoDocumentoVOs = getJdbcTemplate().query(query.toString(),param, new EmailRowMapper());	
		
		if(tipoDocumentoVOs != null && tipoDocumentoVOs.size() > 0 && tipoDocumentoVOs.get(0) != null && tipoDocumentoVOs.get(0).getMail() != null) {
			mail = tipoDocumentoVOs.get(0).getMail();
		}

		//se non trova la mail, la prendo dalla costante

//		else {
//			throw new Exception(Constants.MAIL_NON_TROVATA_COD);
//		}
		
		
		return mail;
	}

	private String leggiCostante(String attributo) throws Exception {
		String valore = "";
		try {
			PbandiCCostantiVO c = new PbandiCCostantiVO();
			c.setAttributo(attributo);
			c = genericDAO.findSingleWhere(c);
			valore = c.getValore();
			logger.info("Valore della costante " + attributo + " = " + valore);
			if (StringUtil.isEmpty(valore)) {
				throw new Exception("Costante " + attributo + " non valorizzata.");
			}
		} catch (Exception e) {
//			logger.error("ERRORE in leggiCostante(): "+e);
			LOG.info("ERRORE in leggiCostante() " + e.getMessage());
			throw e;
		}
		return valore;
	}

	private boolean nonAncoraInviato(String flag) {
		return (StringUtil.isEmpty(flag) || "N".equalsIgnoreCase(flag));
	}

	public static boolean isEmpty(String o) {

		return ObjectUtil.isEmpty(o);
	}

	public static boolean isNull(Object o) {

		return ObjectUtil.isNull(o);
	}

	@Override
	@Transactional
	public EsitoGestioneAffidamenti respingiAffidamento(Long idUtente, String idIride, Long idAppalto, String note)
			throws GestioneAffidamentiException, FormalParameterException, FileNotFoundException, IOException,
			Exception {
		String prf = "[AffidamentiDAOImpl::respingiAffidamento]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "idIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idAppalto);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.TRUE);

		// Cancella eventuale bozza associata all'affidamento.
		try {
			checkListBusiness.eliminaBozzaCheckListDocumentaleAffidamento(idUtente, idIride, idAppalto);
		} catch (Exception e) {
			String msg = "Errore nella cancellazione della checklist in bozza.";
			logger.error(msg, e);
			throw new GestioneAffidamentiException(msg, e);
		}

		// Aggiorna lo stato dell'appalto in DA_INVIARE (id 1).
		try {
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));
			appalto.setIdStatoAffidamento(new BigDecimal(Constants.ID_STATO_AFFIDAMENTO_DAINVIARE));
			appalto.setDtAggiornamento(DateUtil.getSysdate());
			appalto.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(appalto);
		} catch (Exception e) {
			String msg = "Errore nella modifica dello stato dell'affidamento.";
			logger.error(msg, e);
			throw new GestioneAffidamentiException(msg, e);
		}

		// Tramite Appalto -> Proc Aggiudicazione, recupera l'id progetto.
		PbandiTAppaltoVO appaltoVO = new PbandiTAppaltoVO();
		appaltoVO.setIdAppalto(new BigDecimal(idAppalto));
		appaltoVO = genericDAO.findSingleWhere(appaltoVO);

		PbandiTProceduraAggiudicazVO proc = new PbandiTProceduraAggiudicazVO();
		proc.setIdProceduraAggiudicaz(appaltoVO.getIdProceduraAggiudicaz());
		proc = genericDAO.findSingleWhere(proc);

		try {
			// Marca gli allegati come non inviati
			LOG.info(prf + " marca gli allegati come non inviati");
			String sql = fileSqlUtil.getQuery("AffidamentoFiles.sql");
			Object[] params = new Object[] { idAppalto };
			List<ArchivioFileVO> allegati = getJdbcTemplate().query(sql, params, new ArchivioFileRowMapper());
			for (ArchivioFileVO item : allegati) {
				// Cerco il record PBANDI_T_FILE_ENTITA dove salvare il flag per invio verifica.
				PbandiTFileEntitaVO vo = new PbandiTFileEntitaVO();
				vo.setIdFileEntita(item.getIdFileEntita());
				vo = genericDAO.findSingleWhere(vo);
				vo.setFlagEntita("N");
				vo.setDtEntita(null);
				genericDAO.updateNullables(vo);
			}
		} catch (Exception e) {
			LOG.error(prf + " errore nel marcare i file come non inviati: " + e);
			throw e;
		}

		// Invia una notifica al beneficiario.
		String importo = NumberUtil.getStringValue(appaltoVO.getImportoContratto());
		List<MetaDataVO> metaDatas = creaParametriNotificaRespingi(appaltoVO.getOggettoAppalto(), importo, note);

		logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
		genericDAO.callProcedure().putNotificationMetadata(metaDatas);

		logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
		String descrBreveTemplateNotifica = "NotificaRespingiAffidamento";
		genericDAO.callProcedure().sendNotificationMessage2(proc.getIdProgetto(), descrBreveTemplateNotifica,
				"Beneficiario", idUtente, idAppalto);

		esito.setMessage(MessageConstants.MSG_AFFIDAMENTO_RESPINTO);
		return esito;
	}

	private List<MetaDataVO> creaParametriNotificaRespingi(String oggettoAppalto, String importo, String note) {
		List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();

		MetaDataVO metadata1 = new MetaDataVO();
		metadata1.setNome("DATA_RESPINGI_AFFIDAMENTO");
		metadata1.setValore(DateUtil.getDate(new Date()));
		metaDatas.add(metadata1);

		MetaDataVO metadata2 = new MetaDataVO();
		metadata2.setNome("OGGETTO_AFFIDAMENTO");
		metadata2.setValore(oggettoAppalto);
		metaDatas.add(metadata2);

		MetaDataVO metadata3 = new MetaDataVO();
		metadata3.setNome("IMP_AGGIUDICATO_AFFIDAMENTO");
		metadata3.setValore(importo);
		metaDatas.add(metadata3);

		MetaDataVO metadata4 = new MetaDataVO();
		metadata4.setNome("NOTE_RESPINGI_AFFIDAMENTO");
		metadata4.setValore(note);
		metaDatas.add(metadata4);

		return metaDatas;
	}

	@Override
	public EsitoGestioneAffidamenti salvaSubcontratto(Long idUtente, String idIride, SubcontrattoRequest request) throws FormalParameterException {
		String prf = "[AffidamentiDAOImpl::salvaSubcontratto]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "identitaIride", "request" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, request);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);
		try {
			if(request.getIdSubcontrattoAffidamento() == null) {
				String sql = "INSERT INTO PBANDI_R_SUBCONTRATTO_AFFID (ID_SUBCONTRATTO_AFFIDAMENTO, ID_FORNITORE, ID_APPALTO, ID_SUBCONTRAENTE, DT_SUBCONTRATTO, RIFERIMENTO_SUBCONTRATTO, IMPORTO_SUBCONTRATTO) \r\n"
						+ "VALUES(SEQ_PBANDI_R_SUBCONTR_AFFID.nextval, ?, ?, ?, ?, ?, ?)";
				Object[] paramS = new Object[] { request.getIdFornitore(), request.getIdAppalto(), request.getIdSubcontraente() ,request.getDtSubcontratto(),
						request.getRiferimentoSubcontratto(), request.getImportoSubcontratto() };
				getJdbcTemplate().update(sql, paramS);
			} else {
				String sql = "UPDATE PBANDI_R_SUBCONTRATTO_AFFID \r\n"
						+ "SET ID_FORNITORE = ?, ID_APPALTO = ?, ID_SUBCONTRAENTE = ?, DT_SUBCONTRATTO = ?,\r\n"
						+ "    RIFERIMENTO_SUBCONTRATTO = ?, IMPORTO_SUBCONTRATTO = ?\r\n"
						+ "WHERE ID_SUBCONTRATTO_AFFIDAMENTO = ?";
				Object[] paramS = new Object[] { request.getIdFornitore(), request.getIdAppalto(), request.getIdSubcontraente() ,request.getDtSubcontratto(),
						request.getRiferimentoSubcontratto(), request.getImportoSubcontratto(), request.getIdSubcontrattoAffidamento() };
				getJdbcTemplate().update(sql, paramS);
			}
			esito.setEsito(Boolean.TRUE);
			esito.setMessage(Constants.MSG_SALVA_SUCCESSO);
		} catch (Exception e) {
			LOG.error(prf + " Errore nel salvataggio del subcontratto " + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	public EsitoGestioneAffidamenti cancellaSubcontratto(Long idUtente, String idIride, Long idSubcontrattoAffidamento)
			throws FormalParameterException, GestioneAffidamentiException {
		String prf = "[AffidamentiDAOImpl::cancellaSubcontratto]";
		LOG.info(prf + " BEGIN");

		String[] nameParameter = { "idUtente", "idIride", "idSubcontrattoAffidamento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idSubcontrattoAffidamento);

		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);
		try {
			
			String sql = "DELETE FROM PBANDI_R_SUBCONTRATTO_AFFID WHERE ID_SUBCONTRATTO_AFFIDAMENTO = ?";
			Object[] paramS = new Object[] { idSubcontrattoAffidamento };
			int rows = getJdbcTemplate().update(sql, paramS);
			
			if (rows == 1) {
				esito.setEsito(Boolean.TRUE);
				esito.setMessage("Subcontratto cancellato con successo.");
			} else {
				esito.setMessage("Errore nell'eliminazione del subcontratto.");
			}

		} catch (Exception ex) {
			logger.error("[cancellaSubcontratto] errore nell'eliminazione del subcontratto.", ex);
			throw new GestioneAffidamentiException("[cancellaSubcontratto] errore nell'eliminazione del subcontratto.", ex);
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	public List<DocumentoAllegatoDTO> allegatiVerbaleChecklist(Long idDocIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::allegatiVerbaleChecklist] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idUtente = " + idUtente);

		if (idDocIndex == null) {
			throw new InvalidParameterException("idDocIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			FileDTO[] filesAssociated = null;
			filesAssociated = checkListBusiness.getFilesAssociatedVerbaleChecklist(idUtente, idIride, idDocIndex);

			if (filesAssociated == null)
				return result;

			for (FileDTO fileDto : filesAssociated) {
				DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
				allegato.setNome(fileDto.getNomeFile());
				allegato.setSizeFile(fileDto.getSizeFile());
				allegato.setId(fileDto.getIdDocumentoIndex());
				result.add(allegato);
			}

			LOG.info(prf + result.toString());

			// String estensioniAllegatiStr =
			// validazioneRendicontazioneDAO.getCostantiAllegati(idUtente,
			// idIride).getAttributo();
			// List <String> estensioniAllegati = Arrays.asList(
			// estensioniAllegatiStr.split(",") );

			// LOG.info(prf + " " + estensioniAllegati);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca del verbale checklist: ", e);
			throw new DaoException("Errore durante la la ricerca del verbale checklist.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}
}
