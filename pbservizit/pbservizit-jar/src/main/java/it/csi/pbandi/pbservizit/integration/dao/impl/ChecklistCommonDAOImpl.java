/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbservizit.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbservizit.integration.dao.ChecklistCommonDAO;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.util.Constants;

@Component
public class ChecklistCommonDAOImpl extends JdbcDaoSupport implements ChecklistCommonDAO {

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private DecodificheManager decodificheManager;

	@Autowired
	private DocumentoManager documentoManager;

	@Autowired
	public ChecklistCommonDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ChecklistCommonDAOImpl() {
	}

	@Override
	public EsitoOperazioneDTO allegaFilesChecklist(Long idUtente, String idIride, Long idProgetto,
			String codTipoDocumento, String idDocumentoIndexChecklist, FileDTO[] allegati) throws Exception {
		String prf = "[ChecklistCommonDAOImpl::allegaFilesChecklist]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " idUtente=" + idUtente);
		LOG.info(prf + " idIride=" + idIride);
		LOG.info(prf + " codTipoDocumento=" + codTipoDocumento);
		LOG.info(prf + " idProgetto=" + idProgetto);
		LOG.info(prf + " idDocumentoIndexChecklist=" + idDocumentoIndexChecklist);

		EsitoOperazioneDTO ret = new EsitoOperazioneDTO();
		try {
			String sql;
			sql = "SELECT ID_CHECKLIST \r\n" + "FROM PBANDI_T_CHECKLIST \r\n" + "WHERE ID_DOCUMENTO_INDEX = ?";

			Object[] args = new Object[] { idDocumentoIndexChecklist };
			LOG.info("<idDocumentoIndex>: " + idDocumentoIndexChecklist);
			LOG.info(prf + "\n" + sql + "\n");

			List<Long> idChecklists = getJdbcTemplate().queryForList(sql, args, Long.class);
			Long idChecklist = null;
			if (idChecklists != null && idChecklists.size() > 0) {
				idChecklist = idChecklists.get(0);
			} else {
				throw new Exception("Checklist non trovata");
			}
			LOG.info(prf + " idChecklist: " + idChecklist);

			BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
					codTipoDocumento);
			LOG.info(prf + " idTipoDocumentoIndex: " + idTipoDocumentoIndex);
		
			sql = "SELECT ID_ENTITA \r\n"
					+ "FROM PBANDI_C_ENTITA \r\n"
					+ "WHERE NOME_ENTITA = ?";

			args = new Object[] { Constants.ENTITA_C_CHECKLIST };
			
			LOG.info(prf + "\n" + sql + "\n");

			List<Long> idEntitas = getJdbcTemplate().queryForList(sql, args, Long.class);
			Long idEntita = null;
			if (idEntitas != null && idEntitas.size() > 0) {
				idEntita = idEntitas.get(0);
			} else {
				throw new Exception("idEntita non trovato");
			}
			LOG.info(prf + " idEntita: " + idEntita);

			for (FileDTO fileDTO : allegati) {

				sql = "INSERT INTO PBANDI.PBANDI_T_DOCUMENTO_INDEX\r\n"
						+ "(ID_DOCUMENTO_INDEX, ID_TARGET, UUID_NODO, REPOSITORY, DT_INSERIMENTO_INDEX, ID_TIPO_DOCUMENTO_INDEX, ID_ENTITA, ID_UTENTE_INS, \r\n"
						+ "NOME_FILE, ID_PROGETTO, NOME_DOCUMENTO)\r\n"
						+ "VALUES(SEQ_PBANDI_T_DOCUMENTO_INDEX.nextval, ?, 'UUID', ?, SYSDATE, ?, ?, ?, ?, ?, ?)";

				args = new Object[] { idChecklist, codTipoDocumento, idTipoDocumentoIndex, idEntita, idUtente,
						fileDTO.getNome(), idProgetto, fileDTO.getNome() };
				LOG.info(prf + "\n" + sql + "\n");

				int row = getJdbcTemplate().update(sql, args);
				if (row == 0) {
					LOG.error(prf + "Salvataggio su pbandi_t_documento_index fallito.");
					ret.setEsito(false);
					ret.setCodiceMessaggio("Errore durante il salvataggio del file " + fileDTO.getNome());
					return ret;
				}
				InputStream is = new ByteArrayInputStream(fileDTO.getBytes());

				boolean esitoSalva = documentoManager.salvaSuFileSystem(is, fileDTO.getNome(), codTipoDocumento);
				if (!esitoSalva) {
					LOG.error(prf + "Salvataggio su file system fallito: termino la procedura.");
					ret.setEsito(false);
					ret.setCodiceMessaggio("Errore durante il salvataggio del file " + fileDTO.getNome());
					return ret;
				}
			}

			ret.setEsito(true);
			ret.setCodiceMessaggio("Salvataggio degli allegati avvenuto con successo.");

		} catch (Exception e) {
			LOG.error(prf + " Exception e=" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		LOG.info(prf + " END");
		return ret;
	}

}
