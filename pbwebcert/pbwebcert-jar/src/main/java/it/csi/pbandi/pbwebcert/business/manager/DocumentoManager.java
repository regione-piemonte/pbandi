/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;

import it.csi.pbandi.pbwebcert.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbwebcert.dto.manager.ReportCampionamentoDTO;
import it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper.DocumentoIndexRowMapper;
import it.csi.pbandi.pbwebcert.integration.vo.DocumentoIndexVO;
import it.csi.pbandi.pbwebcert.integration.vo.PbandiTCampionamentoVO;
import it.csi.pbandi.pbwebcert.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbwebcert.util.BeanUtil;
import it.csi.pbandi.pbwebcert.util.Constants;
import it.csi.pbandi.pbwebcert.util.DateUtil;
import it.csi.pbandi.pbwebcert.util.StringUtil;


public class DocumentoManager extends JdbcDaoSupport {
	
	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	public DocumentoManager(DataSource dataSource) {
		setDataSource(dataSource);
	
	}

	public DocumentoManager() {
	}
	
	/* Commentato perchè non funziona.
	public PbandiTDocumentoIndexVO creaDocumento(Long idUtente, Object dto, Object codStatoTipoDocIndex,
			String shaHex, Long idRappLegale, Long idDelegato) throws Exception {
        String prf = "[DocumentoManager :: creaDocumento] ";
        LOG.info(prf + " BEGIN");
		try {
			String nomeFile = getNomeFile(dto);			
			BeanUtil.setPropertyValueByName(dto, "nomeFile", nomeFile);
			LOG.info("DocumentoManager::creaDocumento: nomeFile = "+nomeFile+"; codStatoTipoDocIndex = "+codStatoTipoDocIndex);
			
			EsitoSalvaModuloCheckListDTO DTO = null;
			if("7".equals(codStatoTipoDocIndex) || "07".equals(codStatoTipoDocIndex)){
				DTO = (EsitoSalvaModuloCheckListDTO) dto;
				LOG.info(prf + " DTOgetIdChecklist: " + DTO.getIdChecklist());
				LOG.info(prf + " DTOgetIdDocumentoIndex: " + DTO.getIdDocumentoIndex());
				LOG.info(prf +" DTOgetByteModulo: " + (DTO.getByteModulo() != null ? DTO.getByteModulo().length : "niente"));
				LOG.info(prf +" DTOgetEsito: " + DTO.getEsito());
				nomeFile += codStatoTipoDocIndex;
			} 

			// salva su file storage
			StringBuilder querySelect = new StringBuilder();
			querySelect.append("SELECT r.*, s.* FROM PBANDI_T_DOCUMENTO_INDEX r, PBANDI_C_TIPO_DOCUMENTO_INDEX s "
							 + "WHERE r.ID_TIPO_DOCUMENTO_INDEX = s.ID_TIPO_DOCUMENTO_INDEX AND ID_DOCUMENTO_INDEX= ")
					   .append( DTO.getIdDocumentoIndex());
			LOG.info("\n"+querySelect);
			DocumentoIndexVO vo = getJdbcTemplate().queryForObject(querySelect.toString(), new DocumentoIndexRowMapper());
			if (vo != null)
				LOG.info(prf+"vo.IdDocumentoIndex = "+vo.getIdDocumentoIndex());
			
			FileApiServiceImpl fileApiServiceImpl = new FileApiServiceImpl(ROOT_FILE_SYSTEM);
			InputStream inputStream = new ByteArrayInputStream(DTO.getByteModulo());
			LOG.info(prf+"eseguo fileApiServiceImpl.uploadFile");
			Boolean isUploaded = fileApiServiceImpl.uploadFile(inputStream, nomeFile, vo.getDescBreveTipoDocIndex());
			if(isUploaded) {
				LOG.info(prf + " File caricato su file storage con successo!");
			} else {
				throw new Exception("File non caricato su file storage!");
			}
			//salva su db
		    LOG.info(prf + " END");
			return salvaInfoNodoIndexSuDb(vo, nomeFile, vo.getIdTarget(), null, null, idUtente, shaHex);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
	}
	*/

	public PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDb(DocumentoIndexVO vo, String nomeFile, BigDecimal idTarget,
			Long idRappLegale,Long idDelegato,Long idUtente,String shaHex) throws Exception {
		String prf = "[ DocumentoManager :: salvaInfoNodoIndexSuDb ]";
		LOG.info(prf + " BEGIN");
		try {	
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_DOCUMENTO_INDEX ");
			sql.append("(ID_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,DT_INSERIMENTO_INDEX,DT_MARCA_TEMPORALE,DT_VERIFICA_FIRMA,FLAG_FIRMA_CARTACEA,");
			sql.append("ID_CATEG_ANAGRAFICA_MITT,ID_ENTITA,ID_MODELLO,ID_PROGETTO,ID_SOGG_DELEGATO,ID_SOGG_RAPPR_LEGALE,ID_STATO_DOCUMENTO,");
			sql.append("ID_TARGET,ID_TIPO_DOCUMENTO_INDEX,ID_UTENTE_AGG,ID_UTENTE_INS,MESSAGE_DIGEST,NOME_FILE,NOTE_DOCUMENTO_INDEX,");
			sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO)");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			LOG.info("\n"+sql);
			
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
			params[9] = vo.getIdProgetto();
			params[10] = vo.getIdSoggDelegato();
			params[11] = vo.getIdSoggRapprLegale();
			params[12] = vo.getIdStatoDocumento();
			params[13] = vo.getIdTarget();
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
			
			LOG.info(prf+"inserito record in PBANDI_T_DOCUMENTO_INDEX con id = "+vo.getIdDocumentoIndex());
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new Exception(e.getMessage());
		}
		
		StringBuilder querySelect = new StringBuilder();
		querySelect.append("SELECT r.*, s.* FROM PBANDI_T_DOCUMENTO_INDEX r, PBANDI_C_TIPO_DOCUMENTO_INDEX s "
						 + "WHERE r.ID_TIPO_DOCUMENTO_INDEX = s.ID_TIPO_DOCUMENTO_INDEX AND ID_DOCUMENTO_INDEX= ")
				   .append(vo.getIdDocumentoIndex());
		PbandiTDocumentoIndexVO vo1 = getJdbcTemplate().queryForObject(querySelect.toString(), new DocumentoIndexRowMapper());
		LOG.info(prf + " BEGIN");
		return vo1;
	}

	public static final Map<Class<? extends Object>, Class<?>> MAP_DTO_DOCUMENTO_VO_ENTITA = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, Class<?>>() {
				{
					
					this.put(ReportCampionamentoDTO.class, PbandiTCampionamentoVO.class);
					
				}
			});
	
	public <T> String getNomeFile(T dto) {
		LOG.info(dto);

		String nomeFileFromDTO = beanUtil.getPropertyValueByName(dto,
				"nomeFile", String.class);

		String nomeFile = "nomeFileGenerico.bin";
		if (!StringUtil.isEmpty(nomeFileFromDTO)) {
			LOG.debug("nome file preso dal dto");
			nomeFile = nomeFileFromDTO;
		} else {
			@SuppressWarnings("unchecked")
			GeneratoreNomeFile<T> generatoreNomeFile = (GeneratoreNomeFile<T>) MAP_DTO_GENERATORE_NOME_FILE
					.get(dto.getClass());
			if (generatoreNomeFile != null) {
				nomeFile = generatoreNomeFile.generaNomeFile(dto);
			} else {
				String baseName = MAP_DTO_NOME_FILE.get(dto.getClass());
				if (baseName == null) {
					baseName = nomeFile;
				}

				nomeFile = baseName
						.replace(
								".",
								("_"
										+ beanUtil.getPropertyValueByName(dto,
												"idProgetto", String.class)
										+ "_" + DateUtil
										.getTime(
												new java.util.Date(),
												Constants.DATEHOUR_FORMAT_PER_NOME_FILE))
										+ ".");
			}
		}

		LOG.info("nome file: " + nomeFile);
		return nomeFile;
	}
	
	private interface GeneratoreNomeFile<T> {

		String generaNomeFile(T dto);
	};
	
	public static final Map<Class<? extends Object>, String> MAP_DTO_NOME_FILE = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, String>() {
				{
					
				}
			});
	
	private static final Map<Class<?>, GeneratoreNomeFile<?>> MAP_DTO_GENERATORE_NOME_FILE = new HashMap<Class<?>, GeneratoreNomeFile<?>>();

	static {

		
		MAP_DTO_GENERATORE_NOME_FILE.put(ReportCampionamentoDTO.class,
				new GeneratoreNomeFile<ReportCampionamentoDTO>() {

					public String generaNomeFile(ReportCampionamentoDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return dto.getTemplateName()
								+ "_" + time
								+ ".xls";
					}
				});
	}
	
	
}
