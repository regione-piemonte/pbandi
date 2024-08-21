/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbservizit.business.manager.DocumentiFSManager;
import it.csi.pbandi.pbservizit.dto.archivioFile.Esito;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoInsertFiles;
import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.GestioneFileDAO;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.UserSpaceDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileVO;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.DateUtil;
import it.csi.pbandi.pbservizit.util.ErrorMessages;
import it.csi.pbandi.pbservizit.util.NumberUtil;
import it.csi.pbandi.pbservizit.util.StringUtil;

public class GestioneFileDAOImpl extends JdbcDaoSupport implements GestioneFileDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public GestioneFileDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	protected DecodificheDAOImpl decodificheDAOImpl;

	@Autowired
	protected DocumentiFSManager documentiFSManager;

	@Autowired
	protected BeanUtil beanUtil;

	@Override
	public UserSpaceDTO spazioDisco(long idSoggettoBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[GestioneFileDAOImpl::spazioDisco] ";
		LOG.info(prf + " BEGIN");
		UserSpaceDTO userSpaceDTO = new UserSpaceDTO();
		try {

			// Spazio totale.
			String valore = decodificheDAOImpl.costante(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.COSTANTE_USER_SPACE_LIMIT, true);

			// Spazio già usato.
			StringBuilder sql = new StringBuilder("SELECT SUM (SIZE_FILE) AS userSpaceUsed");
			sql.append(" FROM pbandi_t_file")
			.append(" JOIN pbandi_t_folder USING(id_folder)") 
			.append(" where ID_SOGGETTO_BEN = ?");
			LOG.info("\n"+sql.toString());			
			Long usato = (Long) getJdbcTemplate().queryForObject(sql.toString(), new Object[] { idSoggettoBeneficiario }, Long.class);			

			userSpaceDTO.setTotal(new Long(valore));
			userSpaceDTO.setUsed(usato);

		} catch (Exception e) {
			LOG.error(prf+" ERROREin inizializzaArchivioFile(): ", e);
			throw new DaoException("Errore durante la ricerca dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}

		return userSpaceDTO;
	}


	public BigDecimal insertPbandiTFile(PbandiTFileVO vo) throws Exception {
		String prf = "[DocumentoManager::insertPbandiTFile] ";
		logger.info(prf + " BEGIN");
		BigDecimal id = null;
		try {
			logger.info(vo.toString());

			java.sql.Date oggi = DateUtil.getSysdate();
			vo.setDtInserimento(oggi);

			String sqlSeq = "select SEQ_PBANDI_T_FILE.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_FILE ");
			sql.append("(ID_FILE,DT_INSERIMENTO,ID_DOCUMENTO_INDEX,ID_FOLDER,ID_UTENTE_INS,NOME_FILE,SIZE_FILE)");
			sql.append("VALUES (?,?,?,?,?,?,?)");
			logger.info("\n"+sql);

			Object[] params = new Object[7];
			params[0] = id;
			params[1] = vo.getDtInserimento();
			params[2] = vo.getIdDocumentoIndex();
			params[3] = vo.getIdFolder();
			params[4] = vo.getIdUtenteIns();
			params[5] = vo.getNomeFile();
			params[6] = vo.getSizeFile();

			getJdbcTemplate().update(sql.toString(), params);			
			logger.info(prf+"id nuovo record inserito = "+id);

		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante l'inserimento in PBANDI_T_FILE.", e);
		}
		finally {
			logger.info(prf+" END");
		}

		return id;
	}

	public PbandiTDocumentoIndexVO salvaFileCL(Long idUtente, FileDTO fileDTO, String codTipoDocIndex) throws InvalidParameterException, Exception {
		String prf = "[GestioneFileDAOImpl::salvaFileCL] ";
		LOG.info(prf + " BEGIN");

		ArrayList<EsitoInsertFiles> result = new ArrayList<EsitoInsertFiles>();
		PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();


		if (idUtente == null || idUtente.intValue() == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (fileDTO == null || fileDTO.getBytes() == null) {
			throw new InvalidParameterException("file non valorizzato.");
		}

		LOG.info(prf + " idUtente = "+idUtente+"; codTipoDocIndex = "+codTipoDocIndex + "; fileDTO="+fileDTO);

		try {

			Esito esito = null;

			logger.info(prf+"  SALVATAGGIO FILE "+fileDTO.getNomeFile());

			// PASSO 1 : Inserimento su PBANDI_T_DOCUMENTO_INDEX.

			BigDecimal idTarget = BigDecimal.valueOf(fileDTO.getIdFolder()); // idFolder

			BigDecimal idTipoDocIndex = decodificheDAOImpl.idDaDescrizione("PBANDI_C_TIPO_DOCUMENTO_INDEX", "ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", codTipoDocIndex);
			// select ID_TIPO_DOCUMENTO_INDEX from PBANDI_C_TIPO_DOCUMENTO_INDEX where DESC_BREVE_TIPO_DOC_INDEX = 'CLA'; == 29

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", Constants.ENTITA_C_FILE);

			vo.setIdEntita(idEntita);
			vo.setIdTarget(idTarget);
			vo.setIdTipoDocumentoIndex(idTipoDocIndex);
			vo.setIdUtenteIns(new BigDecimal(idUtente));
			vo.setNomeFile(fileDTO.getNomeFile());
			vo.setRepository(codTipoDocIndex);

			// Salva il file su file system e inserisce un record nella PBANDI_T_DOCUMENTO_INDEX.
			boolean esitoSalva = documentiFSManager.salvaFile(fileDTO.getBytes(), vo);
			if (!esitoSalva) {
				logger.error(prf+"Errore in documentoManager.salvaFile()");
				//throw new DaoException("Salvataggio del file fallito.");
				// Restituisce in output l'esito del salvataggio.
				EsitoInsertFiles fileResult = new EsitoInsertFiles();
				fileResult.setEsito(false);
				fileResult.setMessaggio("Salvataggio del file fallito.");
				fileResult.setNomeFile(fileDTO.getNomeFile());
				result.add(fileResult);
			}
			BigDecimal idDocumentoIndex = vo.getIdDocumentoIndex(); 

			// PASSO 2 : Inserimento su PBANDI_T_FILE.

			PbandiTFileVO fileVO=beanUtil.transform(fileDTO, PbandiTFileVO.class);
			fileVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			fileVO.setIdDocumentoIndex(idDocumentoIndex);
			fileVO.setSizeFile(BigDecimal.valueOf(fileDTO.getBytes().length));
			logger.info(prf+"Inserimento del file "+fileVO.getNomeFile()+" , idDocumentoIndex: "+idDocumentoIndex +" con idFolder "+fileVO.getIdFolder());

			try{
				BigDecimal id = insertPbandiTFile(fileVO);				 
			} catch (Exception e) {
				// Cancella il file su file system e su PBANDI_T_DOCUMENTO_INDEX.
				documentiFSManager.cancellaFile(idDocumentoIndex);
				if (e instanceof DataIntegrityViolationException) {
					logger.error(prf+"DataIntegrityViolationException: ",e);
					//throw new DaoException(ErrorMessages.ERROR_DUPLICATED_FILE_NAME_PER_FOLDER);
					// Restituisce in output l'esito del salvataggio.
					EsitoInsertFiles fileResult = new EsitoInsertFiles();
					fileResult.setEsito(false);
					fileResult.setMessaggio(ErrorMessages.ERROR_DUPLICATED_FILE_NAME_PER_FOLDER);
					fileResult.setNomeFile(fileDTO.getNomeFile());
					result.add(fileResult);
				} else  {
					logger.error(prf+"Errore in insertPbandiTFile");
					//throw new DaoException("Salvataggio del file fallito.");
					// Restituisce in output l'esito del salvataggio.
					EsitoInsertFiles fileResult = new EsitoInsertFiles();
					fileResult.setEsito(false);
					fileResult.setMessaggio("Salvataggio del file fallito.");
					fileResult.setNomeFile(fileDTO.getNomeFile());
					result.add(fileResult);
				}
			}

			// Restituisce in output nome e id del file salvato.
			EsitoInsertFiles fileResult = new EsitoInsertFiles();
			fileResult.setEsito(true);
			fileResult.setNomeFile(vo.getNomeFile());
			fileResult.setIdDocumentoIndex(idDocumentoIndex.longValue());
			result.add(fileResult);			 

			if(result.isEmpty()) {
				return vo;
			}else {
				LOG.error(prf+" result non vuoto " );
				int i = 0;
				for (EsitoInsertFiles esit : result) {
					LOG.error(prf+" EsitoInsertFiles["+i+"]= "+esit );
					i++;
				}
				throw new Exception("Errore durante il salvataggio dei file.");
			}

		} catch (DaoException e) {
			LOG.error(prf+" DaoException: ", e);
			throw new DaoException(e.getMessage());
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante il salvataggio dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
	}

}
