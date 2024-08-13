/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.integration.dao.BoStorageDAO;
import it.csi.pbandi.pbwebbo.integration.dao.impl.rowmapper.BoStorageDocumentoIndexDTORowMapper;
import it.csi.pbandi.pbwebbo.integration.dao.impl.rowmapper.ProgettoSuggestVORowMapper;
import it.csi.pbandi.pbwebbo.integration.vo.BoStorageDocumentoIndexDTO;
import it.csi.pbandi.pbwebbo.integration.vo.ProgettoSuggestVO;
import it.csi.pbandi.pbwebbo.util.BeanUtil;
import it.csi.pbandi.pbwebbo.util.Constants;

@Service
public class BoStorageDAOImpl extends JdbcDaoSupport implements BoStorageDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public BoStorageDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	protected GenericDAO genericDAO;

	@Autowired
	private DocumentoManager documentoManager;

	@Override
	public List<CodiceDescrizioneDTO> getTipiDocIndex(UserInfoSec userInfo)
			throws InvalidParameterException, Exception {
		String prf = "[BoStorageDAOImpl::getTipiDocIndex]";
		LOG.info(prf + " BEGIN");

		if (userInfo == null || userInfo.getIdIride() == null || userInfo.getCodiceRuolo() == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		List<CodiceDescrizioneDTO> codiceDescrizioneDTOs = new ArrayList<CodiceDescrizioneDTO>();
		try {
			PbandiCTipoDocumentoIndexVO pbandiCTipoDocumentoIndexVO = new PbandiCTipoDocumentoIndexVO();
			List<PbandiCTipoDocumentoIndexVO> pbandiCTipoDocumentoIndexVOs = genericDAO
					.findListWhere(pbandiCTipoDocumentoIndexVO);

			if (pbandiCTipoDocumentoIndexVOs != null && pbandiCTipoDocumentoIndexVOs.size() > 0) {
				for (PbandiCTipoDocumentoIndexVO vo : pbandiCTipoDocumentoIndexVOs) {
					CodiceDescrizioneDTO dto = new CodiceDescrizioneDTO();
					dto.setId(new Long(vo.getIdTipoDocumentoIndex().longValue()));
					dto.setDescrizione(vo.getDescTipoDocIndex());
					dto.setDescrizioneBreve(vo.getDescBreveTipoDocIndex());
					codiceDescrizioneDTOs.add(dto);
				}
			}
		} catch (Exception e) {
			String msg = "Errore durante l'inizializzazione dei tipi documento.";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return codiceDescrizioneDTOs;
	}

	@Override
	public List<ProgettoSuggestVO> getProgettiByDesc(String descrizione, UserInfoSec userInfo)
			throws InvalidParameterException, Exception {
		String prf = "[BoStorageDAOImpl::getProgettiByDesc]";
		LOG.info(prf + " BEGIN");

		if (descrizione == null || descrizione.length() == 0) {
			throw new InvalidParameterException("descrizione non valorizzata.");
		}

		if (userInfo == null || userInfo.getIdIride() == null || userInfo.getCodiceRuolo() == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		List<ProgettoSuggestVO> progettoSuggestVOs = new ArrayList<ProgettoSuggestVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("from PBANDI_T_PROGETTO ptp  ");
			sql.append("where LOWER(CODICE_VISUALIZZATO) like LOWER(?) ");
			sql.append("and ROWNUM <= 100 ");

			LOG.debug(sql.toString());
			String newDescrizione = "%" + descrizione.trim() + "%";

			progettoSuggestVOs = getJdbcTemplate().query(sql.toString(), new Object[] { newDescrizione },
					new ProgettoSuggestVORowMapper());

		} catch (Exception e) {
			String msg = "Errore durante l'inizializzazione dei tipi documento.";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return progettoSuggestVOs;
	}

	@Override
	public List<BoStorageDocumentoIndexDTO> ricercaDocumenti(String nomeFile, Long idTipoDocumentoIndex,
			Long idProgetto, UserInfoSec userInfo) throws InvalidParameterException, Exception {
		String prf = "[BoStorageDAOImpl::ricercaDocumenti]";
		LOG.info(prf + " BEGIN");

		if (userInfo == null || userInfo.getIdIride() == null || userInfo.getCodiceRuolo() == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		List<BoStorageDocumentoIndexDTO> boStorageDocumentoIndexDTOs = new ArrayList<BoStorageDocumentoIndexDTO>();
		try {
			ArrayList<Object> args = new ArrayList<>();
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT ptdi.ID_DOCUMENTO_INDEX, ptdi.NOME_FILE, ptdi.NOME_DOCUMENTO_FIRMATO, ptdi.NOME_DOCUMENTO_MARCATO, ptdi.FLAG_FIRMA_AUTOGRAFA, pctdi.ID_TIPO_DOCUMENTO_INDEX, \r\n");
			sql.append(
					"pctdi.DESC_BREVE_TIPO_DOC_INDEX, pctdi.DESC_TIPO_DOC_INDEX, ptp.ID_PROGETTO, ptp.CODICE_VISUALIZZATO \r\n");
			sql.append(
					"FROM PBANDI_T_DOCUMENTO_INDEX ptdi \r\n");
			sql.append(
					"JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX pctdi ON ptdi.ID_TIPO_DOCUMENTO_INDEX = pctdi.ID_TIPO_DOCUMENTO_INDEX \r\n");
			sql.append(
					"LEFT JOIN PBANDI_T_PROGETTO ptp ON ptdi.ID_PROGETTO = ptp.ID_PROGETTO \r\n");
			sql.append(
					"WHERE ");
			if (nomeFile != null && nomeFile.length() > 0) {
				sql.append("LOWER(ptdi.NOME_FILE) like LOWER(?) AND ");
				String newNomeFile = "%" + nomeFile.trim() + "%";
				args.add(newNomeFile);
			}
			if (idTipoDocumentoIndex != null && idTipoDocumentoIndex != 0) {
				sql.append("ptdi.ID_TIPO_DOCUMENTO_INDEX = ? AND ");
				args.add(idTipoDocumentoIndex);
			}
			if (idProgetto != null && idProgetto != 0) {
				sql.append("ptdi.ID_PROGETTO = ? AND ");
				args.add(idProgetto);
			}
			String sqlNew = sql.substring(0, sql.length()-4);
			LOG.info(sqlNew);

			boStorageDocumentoIndexDTOs = getJdbcTemplate().query(sqlNew, ps -> {
				for (int i = 0; i < args.size(); i++) {
					ps.setObject(i + 1, args.get(i));
				}
			}, new BoStorageDocumentoIndexDTORowMapper());
			LOG.info("trovati " + boStorageDocumentoIndexDTOs.size() + " risultati");
		} catch (Exception e) {
			String msg = "Errore durante la ricerca.";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return boStorageDocumentoIndexDTOs;

	}

	@Override
	public ResponseCodeMessage sostituisciDocumento(FileDTO file, String descrizioneBreveTipoDocIndex,
			Long idDocumentoIndex, Boolean flagFileFirmato, Boolean flagFileMarcato, Boolean flagFileFirmaAutografa,
			UserInfoSec userInfo) throws InvalidParameterException, Exception {
		String prf = "[BoStorageDAOImpl::sostituisciDocumento]";
		LOG.info(prf + " BEGIN");

		if (userInfo == null || userInfo.getIdIride() == null || userInfo.getCodiceRuolo() == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		ResponseCodeMessage resp = new ResponseCodeMessage();
		try {

			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new PbandiTDocumentoIndexVO();
			pbandiTDocumentoIndexVO.setIdDocumentoIndex(new BigDecimal(idDocumentoIndex));
			pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
			boolean esito = false;
			InputStream is = new ByteArrayInputStream(file.getBytes());
			String nomeFile = "";
			if (!flagFileFirmato && !flagFileMarcato) {
				nomeFile = pbandiTDocumentoIndexVO.getNomeDocumento();
			} else if (flagFileMarcato) {
				nomeFile = pbandiTDocumentoIndexVO.getNomeDocumentoMarcato();
			} else if (flagFileFirmato) {
				nomeFile = pbandiTDocumentoIndexVO.getNomeDocumentoFirmato();
			}
			esito = documentoManager.salvaSuFileSystem(is, nomeFile, descrizioneBreveTipoDocIndex);
			if (esito) {
				resp.setCode("OK");
				resp.setMessage("Documento sostituito correttamente.");
			} else {
				resp.setCode("KO");
				resp.setMessage("Errore durante la sostituzione del documento.");
			}
		} catch (

		Exception e) {
			String msg = "Errore durante la sostituzione del documento.";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return resp;
	}

}
