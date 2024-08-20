/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.ws.rs.core.MultivaluedMap;

import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.integration.dao.AreaControlliDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ControlloLocoVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaIntegrazioneControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaProrogaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

import org.springframework.stereotype.Component;

@Component
public class AreaControlliDAOImpl extends JdbcDaoSupport implements AreaControlliDAO {
	@Autowired
	public AreaControlliDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	@Autowired
	DocumentoManager documentoManager;
	@Autowired
	IterAutorizzativiDAO iterAutoDao; 
	@Autowired
	SuggestRevocheDAO suggestRevocaDao; 
	@Autowired 
	PropostaRevocaDAO propostaRevocaDao;

	@Override
	public List<AttivitaDTO> getListaSugg(RicercaControlliDTO suggestDTO, int id) {
		String prf = "[AreaControlliDAOImpl::getListaSugg ]";
		logger.info(prf + "BEGIN");
		
//		denominazioneBeneficiario: 1
//		bando:2
//		codiceVisualizzato:3

		List<AttivitaDTO> result = new ArrayList<>();

		switch (id) {
		case 1:
			result = listaDenomiazioni(suggestDTO);
			break;
		case 2:
			result = listaBando(suggestDTO);
			break;
		case 3:
			result = listaCodiceProgetto(suggestDTO);
			break;
		case 4:
			result = getListaProgettoAltriControlli(suggestDTO);
			break;
			
		default:
			break;
		}

		return result;
	}
	
	public List<AttivitaDTO> getListaProgettoAltriControlli(RicercaControlliDTO suggestDTO) {
		String prf = "[AreaControlliDAOImpl::getListaProgetto ]";
		logger.info(prf+"BEGIN");
		List<AttivitaDTO> listaCodiceProgetto = new ArrayList<AttivitaDTO>();

		try {
			
			StringBuilder sql = new StringBuilder(); 
			
			sql.append("SELECT\r\n"
					+ "    DISTINCT prsp.ID_PROGETTO,\r\n"
					+ "    ptp.CODICE_VISUALIZZATO, prsp.id_soggetto\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "WHERE\r\n"
					+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.DT_FINE_VALIDITA IS NULL\r\n");
			
			sql.append("AND  UPPER(ptp.CODICE_VISUALIZZATO) LIKE CONCAT('%', CONCAT(UPPER("+suggestDTO.getValue()+"), '%'))\n");
			
			if(suggestDTO.getIdSoggetto() != null && suggestDTO.getIdSoggetto().intValue()>0) {
				sql.append( " AND prsp.ID_SOGGETTO ="+suggestDTO.getIdSoggetto()+"\r\n");
			}
			if(suggestDTO.getProgrBandoLinea() != null && suggestDTO.getProgrBandoLinea().intValue()>0) {
				sql.append("    AND prbli.PROGR_BANDO_LINEA_INTERVENTO = "+suggestDTO.getProgrBandoLinea()+"\r\n");
			}
			
			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO codProg = new AttivitaDTO();
					codProg.setDescAttivita(rs.getString("CODICE_VISUALIZZATO"));
					codProg.setIdAttivita(rs.getLong("ID_PROGETTO"));
					codProg.setIdSoggetto(rs.getLong("id_soggetto"));
					return codProg;
				}
			};

			listaCodiceProgetto = getJdbcTemplate().query(sql.toString(), lista);
		} catch (Exception e) {
			logger.error(prf+" "+ e);
		}
		return listaCodiceProgetto;
	}
	
	public List<AttivitaDTO> listaDenomiazioni(RicercaControlliDTO suggestDTO) {
		String prf = "[AreaControlliDAOImpl::listaDenomiazioni ]";

		List<AttivitaDTO> listaDenominazioni = new ArrayList<>();

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("WITH denom AS (\n" +
					"    SELECT DISTINCT \n" +
					"    PERSFIS.ID_SOGGETTO,\n" +
					"    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione,\n" +
					"    MAX(persfis.ID_PERSONA_FISICA) AS id\n" +
					"    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
					"    WHERE persfis.ID_PERSONA_FISICA IN (\n" +
					"\t\tSELECT MAX(prsp2.ID_PERSONA_FISICA)\n" +
					"\t\tFROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
					"\t\tGROUP BY prsp2.ID_SOGGETTO\n" +
					"    )\n" +
					"    GROUP BY\n" +
					"    persfis.ID_SOGGETTO,\n" +
					"    persfis.NOME,\n" +
					"    persfis.COGNOME\n" +
					"    UNION\n" +
					"    SELECT DISTINCT \n" +
					"    ENTEGIU.ID_SOGGETTO,\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\n" +
					"    MAX(entegiu.ID_ENTE_GIURIDICO) AS id\n" +
					"    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
					"    WHERE entegiu.ID_ENTE_GIURIDICO IN (\n" +
					"        SELECT MAX(prsp2.ID_ENTE_GIURIDICO)\n" +
					"        FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
					"        GROUP BY prsp2.ID_SOGGETTO\n" +
					"    )\n" +
					"    GROUP BY\n" +
					"    ID_SOGGETTO,\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO\n" +
					")\n" +
					"SELECT DISTINCT \n" +
					"prsp.ID_SOGGETTO,\n" +
					"denom.denominazione, \n" +
					"pts.CODICE_FISCALE_SOGGETTO\n" +
					"FROM PBANDI_R_SOGGETTO_PROGETTO prsp\n" +
					"LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\n" +
					"LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
					"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
					"LEFT JOIN denom ON denom.id_soggetto = pts.ID_SOGGETTO\n" +
					"WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
					"AND prsp.ID_TIPO_ANAGRAFICA = 1\n"
					+"AND prsp.DT_FINE_VALIDITA IS NULL\r\n");
			if(suggestDTO.getProgrBandoLinea()!=null) {
				sql.append("AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\n");
			}
			if(suggestDTO.getIdProgetto()!=null) {
				sql.append("AND prsp.id_progetto = ?\n");
			}
			if(suggestDTO.getValue().trim().length()>0) {
				sql.append("AND UPPER(denom.denominazione) LIKE CONCAT('%', CONCAT(UPPER(?), '%'))\n");
			}
			sql.append(" AND ROWNUM<=100\n" +
					"ORDER BY denom.denominazione \n");

			listaDenominazioni = getJdbcTemplate().query(
					sql.toString(),
					ps -> {
						int k = 1;
						if(suggestDTO.getProgrBandoLinea()!=null) {
							ps.setBigDecimal(k++, suggestDTO.getProgrBandoLinea());
						}
						if(suggestDTO.getIdProgetto()!=null) {
							ps.setBigDecimal(k++, suggestDTO.getIdProgetto());
						}
						if(suggestDTO.getValue().trim().length()>0) {
							ps.setString(k, suggestDTO.getValue());
						}
					},
					(rs, rowNum) -> {
						AttivitaDTO denom = new AttivitaDTO();
						String codFisc = rs.getString("CODICE_FISCALE_SOGGETTO");
						denom.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						denom.setDescAttivita(rs.getString("denominazione") + " - "+ codFisc);
						return denom;
			});
		} catch (Exception e) {
			logger.error(prf + "Exception while trying to read PBANDI_T_ENTE_GIURIDICO", e);
		} finally {
			logger.info(prf + " END");
		}

		return listaDenominazioni;
	}
	public List<AttivitaDTO> listaCodiceProgetto(RicercaControlliDTO suggestDTO) {
		String prf = "[AreaControlliDAOImpl::listaCodiceProgetto ]";

		List<AttivitaDTO> listaCodiceProgetto = new ArrayList<>();

		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("WITH denom AS (\n" +
					"    SELECT DISTINCT \n" +
					"    PERSFIS.ID_SOGGETTO,\n" +
					"    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione,\n" +
					"    MAX(persfis.ID_PERSONA_FISICA) AS id\n" +
					"    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
					"    WHERE persfis.ID_PERSONA_FISICA IN (\n" +
					"        SELECT MAX(prsp2.ID_PERSONA_FISICA)\n" +
					"        FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
					"        GROUP BY prsp2.ID_SOGGETTO\n" +
					"    )\n" +
					"    GROUP BY\n" +
					"    persfis.ID_SOGGETTO,\n" +
					"    persfis.NOME,\n" +
					"    persfis.COGNOME\n" +
					"    UNION\n" +
					"    SELECT DISTINCT \n" +
					"    ENTEGIU.ID_SOGGETTO,\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\n" +
					"    MAX(entegiu.ID_ENTE_GIURIDICO) AS id\n" +
					"    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
					"    WHERE\n" +
					"    entegiu.ID_ENTE_GIURIDICO IN (\n" +
					"        SELECT MAX(prsp2.ID_ENTE_GIURIDICO)\n" +
					"        FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
					"        GROUP BY prsp2.ID_SOGGETTO\n" +
					"    )\n" +
					"    GROUP BY\n" +
					"    ID_SOGGETTO,\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO\n" +
					")\n" +
					"SELECT DISTINCT\n" +
					"prsp.ID_PROGETTO,\n" +
					"prsp.ID_SOGGETTO,\n" +
					"--prsp.ID_PROGETTO,\n" +
					"prbli.PROGR_BANDO_LINEA_INTERVENTO,\n" +
					"prbli.NOME_BANDO_LINEA,ptp.CODICE_VISUALIZZATO,\n" +
					"denom.denominazione\n" +
					"FROM PBANDI_R_SOGGETTO_PROGETTO prsp\n" +
					"LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\n" +
					"LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
					"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
					"LEFT JOIN denom ON denom.id_soggetto = pts.ID_SOGGETTO\n" +
					"WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
					"AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "AND prsp.DT_FINE_VALIDITA IS NULL\r\n");
			
			if(suggestDTO.getProgrBandoLinea()!=null) {
				sql.append("AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\r\n");
			}
			if(suggestDTO.getIdSoggetto()!=null) {
				sql.append("AND prsp.id_soggetto = ?\r\n");
			}
			if(suggestDTO.getValue().trim().length()>0) {
				sql.append("AND  UPPER(ptp.CODICE_VISUALIZZATO) LIKE CONCAT('%', CONCAT(UPPER(?), '%'))\n");
			}
			sql.append(" AND ROWNUM<=100\n" +
					"ORDER BY prsp.ID_SOGGETTO \n");

			listaCodiceProgetto = getJdbcTemplate().query(
					sql.toString(),
					ps -> {
						int k = 1;
						if(suggestDTO.getProgrBandoLinea()!=null) {
							ps.setBigDecimal(k++, suggestDTO.getProgrBandoLinea());
						}
						if(suggestDTO.getIdSoggetto()!=null) {
							ps.setBigDecimal(k++, suggestDTO.getIdProgetto());
						}
						if(suggestDTO.getValue().trim().length()>0) {
							ps.setString(k, suggestDTO.getValue());
						}
					},
					(rs, rowNum) -> {
						AttivitaDTO codProg = new AttivitaDTO();
						codProg.setDescAttivita(rs.getString("CODICE_VISUALIZZATO"));
						codProg.setIdAttivita(rs.getLong("ID_PROGETTO"));
						codProg.setIdSoggetto(rs.getLong("id_soggetto"));
						return codProg;
			});

		} catch (Exception e) {
			logger.error(prf + "Exception while trying to read PBANDI_D_STATO_PROP_VAR_CRE", e);
		} finally {
			logger.info(prf + " END");
		}

		return listaCodiceProgetto;
	}
	public List<AttivitaDTO> listaBando(RicercaControlliDTO suggestDTO) {
		String prf = "[AreaControlliDAOImpl::listaBando ]";

		List<AttivitaDTO> listaBandi = new ArrayList<>();

		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("WITH denom AS (\n" +
					"    SELECT DISTINCT \n" +
					"    PERSFIS.ID_SOGGETTO,\n" +
					"    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione,\n" +
					"    MAX(persfis.ID_PERSONA_FISICA) AS id\n" +
					"    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
					"    WHERE persfis.ID_PERSONA_FISICA IN (\n" +
					"        SELECT MAX(prsp2.ID_PERSONA_FISICA)\n" +
					"        FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
					"        GROUP BY prsp2.ID_SOGGETTO\n" +
					"    )\n" +
					"    GROUP BY\n" +
					"    persfis.ID_SOGGETTO,\n" +
					"    persfis.NOME,\n" +
					"    persfis.COGNOME\n" +
					"    UNION\n" +
					"    SELECT DISTINCT \n" +
					"    ENTEGIU.ID_SOGGETTO,\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\n" +
					"    MAX(entegiu.ID_ENTE_GIURIDICO) AS id\n" +
					"    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
					"    WHERE entegiu.ID_ENTE_GIURIDICO IN (\n" +
					"        SELECT MAX(prsp2.ID_ENTE_GIURIDICO)\n" +
					"        FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
					"        GROUP BY prsp2.ID_SOGGETTO\n" +
					"    )\n" +
					"    GROUP BY\n" +
					"    ID_SOGGETTO,\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO\n" +
					")\n" +
					"SELECT DISTINCT \n" +
					"prbli.ID_BANDO ,\n" +
					"prbli.PROGR_BANDO_LINEA_INTERVENTO,\n" +
					"prbli.NOME_BANDO_LINEA\n" +
					"FROM PBANDI_R_SOGGETTO_PROGETTO prsp\n" +
					"LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\n" +
					"LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
					"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
					"LEFT JOIN denom ON denom.id_soggetto = pts.ID_SOGGETTO\n" +
					"WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
					"AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"+
				    " AND prsp.DT_FINE_VALIDITA IS NULL\r\n");
			
			if(suggestDTO.getIdSoggetto()!=null) {
				sql.append("and prsp.id_soggetto= ? \n");
			}
			if(suggestDTO.getIdProgetto()!=null) {
				sql.append("and prsp.id_progetto= ? \n");
			}
			sql.append("and  UPPER(prbli.NOME_BANDO_LINEA) LIKE CONCAT('%', CONCAT(UPPER(?), '%'))\n");

			sql.append(" AND ROWNUM<=100\n" +
					"ORDER BY prbli.NOME_BANDO_LINEA \n");

			listaBandi = getJdbcTemplate().query(
					sql.toString(),
					ps -> {
						int k = 1;
						if(suggestDTO.getIdSoggetto()!=null) {
							ps.setBigDecimal(k++, suggestDTO.getIdSoggetto());
						}
						if(suggestDTO.getIdProgetto()!=null) {
							ps.setBigDecimal(k++, suggestDTO.getIdProgetto());
						}
						if(suggestDTO.getValue().trim().length()>0) {
							ps.setString(k, suggestDTO.getValue());
						}
					},
					(rs, rowNum) -> {
				AttivitaDTO bando = new AttivitaDTO();
				bando.setDescAttivita(rs.getString("NOME_BANDO_LINEA"));
				bando.setProgBandoLinea(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
				bando.setIdAttivita(rs.getLong("ID_BANDO"));
				return bando;
			});

		} catch (Exception e) {

			logger.error(prf + "Exception while trying to read PBANDI_T_BANDO", e);
		} finally {
			logger.info(prf + " END");
		}

		return listaBandi;
	}

	@Override
	public List<AttivitaDTO> getStatoControllo() {
		String prf = "[AreaControlliDAOImpl::getStatoControllo ]";
		logger.info(prf+"BEGIN");
		
		List<AttivitaDTO> stati = new ArrayList<>();
		
		try {
			String query =
					"SELECT DISTINCT \n" +
					"ID_STATO_CONTR_LOCO,\n" +
					"DESC_STATO_CONTR_LOCO\n" +
					"FROM PBANDI_D_STATO_CONTR_LOCO \n" +
					"ORDER BY DESC_STATO_CONTR_LOCO\n";

			stati = getJdbcTemplate().query(query, (rs, rowNum) -> {
				AttivitaDTO cf = new AttivitaDTO();
				cf.setDescAttivita(rs.getString("DESC_STATO_CONTR_LOCO"));
				cf.setIdAttivita(rs.getLong("ID_STATO_CONTR_LOCO"));
				return cf;
			});
		} catch (Exception e) {
			logger.error(prf + "Exception while trying to read PBANDI_D_STATO_CONTR_LOCO", e);
		}
		
		return stati;
	}

	@Override
	public List<ControlloLocoVO> getElencoControlli(RicercaControlliDTO searchDTO, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::getElencoControlli ]";
		logger.info(prf+"BEGIN");
		
		List<ControlloLocoVO> elenco = new ArrayList<>();
		try {
			
			StringBuilder sql = new StringBuilder(); 
			sql.append("WITH denom AS (\n" +
					"    SELECT\n" +
					"    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\n" +
					"    PERSFIS.ID_SOGGETTO,\n" +
					"    persfis.ID_PERSONA_FISICA \n" +
					"    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
					"),\n" +
					"denom2 AS (\n" +
					"    SELECT\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\n" +
					"    ENTEGIU.ID_SOGGETTO,\n" +
					"    entegiu.ID_ENTE_GIURIDICO\n" +
					"    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
					")\n" +
					"SELECT DISTINCT \n" +
					"ptp.CODICE_VISUALIZZATO,\n" +
					"pbli.NOME_BANDO_LINEA,\n" +
					"pdscl.DESC_STATO_CONTR_LOCO,pdscl.ID_STATO_CONTR_LOCO ,\n" +
					"ptcl.IMP_DA_CONTROLLARE,\n" +
					"padc.DESC_ATTIV_CONTR_LOCO,padc.ID_ATTIV_CONTR_LOCO ,\n" +
					"denom.denominazione_fis, denom.ID_PERSONA_FISICA,\n" +
					"denom2.denominazione_ente,denom2.ID_ENTE_GIURIDICO,\n" +
					"prsp.ID_PROGETTO,ptcl.ID_CONTROLLO_LOCO ,pbli.FLAG_SIF ,\n" +
					"prsp.ID_SOGGETTO,\n" +
					"ptcl.DT_AVVIO_CONTROLLI,\n" +
					"ptcl.DT_INIZIO_CONTROLLI,\n" +
					"ptcl.DT_FINE_CONTROLLI,\n" +
					"ptcl.IMP_IRREGOLARITA,\n" +
					"ptcl.IMP_AGEVOLAZ_IRREG,\n" +
					"ptcl.DT_VISITA_CONTROLLO,\n" +
					"ptcl.TIPO_VISITA,\n" +
					"ptcl.ISTRUTTORE_VISITA \n" +
					"FROM PBANDI_T_CONTROLLO_LOCO ptcl\n" +
					"LEFT JOIN PBANDI_R_CAMP_CONTR_LOCO prccl on prccl.ID_CONTROLLO_LOCO = ptcl.ID_CONTROLLO_LOCO\n"+ 
					"LEFT JOIN PBANDI_T_CAMPIONAMENTO_FINP ptcf ON ptcf.ID_CAMPIONAMENTO = prccl.ID_CAMPIONAMENTO \n" +
					"LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.id_progetto = prccl.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\n" +
					"LEFT JOIN PBANDI_T_PROGETTO ptp on ptp.id_progetto = prsp.id_progetto\n" +
					"LEFT JOIN PBANDI_T_DOMANDA ptd on ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
					"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT pbli on pbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"LEFT JOIN PBANDI_D_STATO_CONTR_LOCO pdscl on ptcl.ID_STATO_CONTR_LOCO = pdscl.ID_STATO_CONTR_LOCO\n" +
					"LEFT JOIN PBANDI_D_ATTIV_CONTROLLO_LOCO padc ON padc.ID_ATTIV_CONTR_LOCO = ptcl.ID_ATTIV_CONTR_LOCO\n" +
					"LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\n" +
					"LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\n" +
					"WHERE prsp.ID_TIPO_ANAGRAFICA = 1\n" +
					"AND prsp.ID_TIPO_BENEFICIARIO <> 4");

			if(searchDTO.getIdProgetto()!=null) 
				sql.append("and prsp.id_progetto = ? \n");
			if(searchDTO.getNumeroCampionamento()!=null)
				sql.append("and ptcf.NUM_CAMPIONAMENTO_ESTERNO = ?\n");
			if(searchDTO.getIdSoggetto()!=null)
				sql.append(" and prsp.id_soggetto = ?\n");
			if(searchDTO.getIdStatoControllo()!=null)
				sql.append("and ptcl.ID_STATO_CONTR_LOCO = ? \n");
			if (searchDTO.getProgrBandoLinea()!=null) 
				sql.append("and pbli.PROGR_BANDO_LINEA_INTERVENTO = ? \n");
			if(searchDTO.getImportoDaControllareA()!=null)
				sql.append("and ptcl.IMP_DA_CONTROLLARE <= ? \n");
			if(searchDTO.getImportoDaControllareDa()!=null)
				sql.append("and ptcl.IMP_DA_CONTROLLARE >= ? \n");
			if(searchDTO.getImportoIrregolareA()!=null)
				sql.append("and ptcl.IMP_IRREGOLARITA <= ? \n");
			if(searchDTO.getImportoIrregolareDa()!=null)
				sql.append("and ptcl.IMP_IRREGOLARITA >= ? \n");
			
			sql.append(" order by prsp.id_progetto");

			elenco = getJdbcTemplate().query(
					sql.toString(),
					ps -> {
						int k = 1;
						if(searchDTO.getIdProgetto()!=null){
							ps.setBigDecimal(k++, searchDTO.getIdProgetto());
						}
						if(searchDTO.getNumeroCampionamento()!=null){
							ps.setBigDecimal(k++, searchDTO.getNumeroCampionamento());
						}
						if(searchDTO.getIdSoggetto()!=null){
							ps.setBigDecimal(k++, searchDTO.getIdSoggetto());
						}
						if(searchDTO.getIdStatoControllo()!=null){
							ps.setBigDecimal(k++, searchDTO.getIdStatoControllo());
						}
						if(searchDTO.getProgrBandoLinea()!=null){
							ps.setBigDecimal(k++, searchDTO.getProgrBandoLinea());
						}
						if(searchDTO.getImportoDaControllareA()!=null){
							ps.setBigDecimal(k++, searchDTO.getImportoDaControllareA());
						}
						if(searchDTO.getImportoDaControllareDa()!=null){
							ps.setBigDecimal(k++, searchDTO.getImportoDaControllareDa());
						}
						if(searchDTO.getImportoIrregolareA()!=null){
							ps.setBigDecimal(k++, searchDTO.getImportoIrregolareA());
						}
						if(searchDTO.getImportoIrregolareDa()!=null){
							ps.setBigDecimal(k, searchDTO.getImportoIrregolareDa());
						}
					},
					(rs, rowNum) -> {
						ControlloLocoVO controllo = new ControlloLocoVO();
						controllo.setCodVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
						String denom = rs.getString("denominazione_ente");
						if(denom!=null) {
							controllo.setIdGiuPersFis(rs.getBigDecimal("ID_ENTE_GIURIDICO"));
							controllo.setDenominazione(denom);
							controllo.setPersonaGiuridica(true);
						}else {
							controllo.setDenominazione(rs.getString("denominazione_fis"));
							controllo.setIdGiuPersFis(rs.getBigDecimal("ID_PERSONA_FISICA"));
							controllo.setPersonaGiuridica(false);
						}
						controllo.setDescAttivita(rs.getString("DESC_ATTIV_CONTR_LOCO"));
						controllo.setDescBando(rs.getString("NOME_BANDO_LINEA"));
						controllo.setDescStatoControllo(rs.getString("DESC_STATO_CONTR_LOCO"));
						controllo.setDescTipoControllo("In loco");
						controllo.setIdControllo(rs.getBigDecimal("ID_CONTROLLO_LOCO"));
						controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
						controllo.setFlagSif(rs.getString("FLAG_SIF"));
						controllo.setDataAvvioControlli(rs.getDate("DT_AVVIO_CONTROLLI"));
						controllo.setDataFineControlli(rs.getDate("DT_FINE_CONTROLLI"));
						controllo.setDataInizioControlli(rs.getDate("DT_INIZIO_CONTROLLI"));
						controllo.setDataVisitaControllo(rs.getDate("DT_VISITA_CONTROLLO"));
						controllo.setIstruttoreVisita(rs.getString("ISTRUTTORE_VISITA"));
						controllo.setImportoAgevIrreg(rs.getBigDecimal("IMP_AGEVOLAZ_IRREG"));
						controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
						controllo.setImportoIrregolarita(rs.getBigDecimal("IMP_IRREGOLARITA"));
						controllo.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
						controllo.setIdStatoControllo(rs.getInt("ID_STATO_CONTR_LOCO"));

						return controllo;
			});

		} catch (Exception e) {
			logger.error("errore lettura tabella"+e);
		}
		
		return elenco;
	}
	
	// -------- ALTRI CONTROLLI------------
	
	@Override
	public List<AttivitaDTO> getAutoritaControllante(HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::getAutoritaControllante ]";
		logger.info(prf+"BEGIN");
		
		List<AttivitaDTO> elenco = new ArrayList<>();
		
		try {
			
			String sql =
					"SELECT \n" +
					"pdca.DESC_CATEG_ANAGRAFICA, \n" +
					"pdca.ID_CATEG_ANAGRAFICA \n" +
					"FROM PBANDI_D_CATEG_ANAGRAFICA pdca  \n" +
					"order by pdca.DESC_CATEG_ANAGRAFICA";
			
			elenco = getJdbcTemplate().query(
					sql,
					(rs, rowNum) -> {
						AttivitaDTO cat = new AttivitaDTO();
						cat.setIdAttivita(rs.getLong("ID_CATEG_ANAGRAFICA"));
						cat.setDescAttivita(rs.getString("DESC_CATEG_ANAGRAFICA"));
						return cat;
			});
			
		} catch (Exception e) {
			logger.error("errore lettura tabella PBANDI_D_CATEG_ANAGRAFICA: " + e);
		}
		
		return elenco;
	}
	// ------- GESTIONE CONTROLLO LOCO ------------
	@Override
	public ControlloLocoVO getControllo(BigDecimal idControllo, BigDecimal idProgetto, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::getControllo ]";
		logger.info(prf+"BEGIN");
		
		ControlloLocoVO controllo = new ControlloLocoVO();
		
		try {
			
			String sql =
					"WITH denom AS (\n" +
					"    SELECT\n" +
					"    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\n" +
					"    PERSFIS.ID_SOGGETTO,\n" +
					"    persfis.ID_PERSONA_FISICA\n" +
					"    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
					"),\n" +
					"denom2 AS (\n" +
					"    SELECT\n" +
					"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\n" +
					"    ENTEGIU.ID_SOGGETTO,\n" +
					"    entegiu.ID_ENTE_GIURIDICO\n" +
					"    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
					")\n" +
					"SELECT DISTINCT \n" +
					"ptp.CODICE_VISUALIZZATO,\n" +
					"pbli.NOME_BANDO_LINEA, pbli.ID_LINEA_DI_INTERVENTO,\n" +
					"pdscl.DESC_STATO_CONTR_LOCO,pdscl.ID_STATO_CONTR_LOCO ,\n" +
					"ptcl.IMP_DA_CONTROLLARE,\n" +
					"padc.DESC_ATTIV_CONTR_LOCO,padc.ID_ATTIV_CONTR_LOCO ,\n" +
					"denom.denominazione_fis,\n" +
					"denom.ID_PERSONA_FISICA,\n" +
					"denom2.denominazione_ente,\n" +
					"denom2.ID_ENTE_GIURIDICO,\n" +
					"prsp.ID_PROGETTO,\n" +
					"ptcl.ID_CONTROLLO_LOCO,\n" +
					"pbli.FLAG_SIF,\n" +
					"prsp.ID_SOGGETTO,\n" +
					"ptcl.DT_AVVIO_CONTROLLI,\n" +
					"ptcl.DT_INIZIO_CONTROLLI,\n" +
					"ptcl.DT_FINE_CONTROLLI,\n" +
					"ptcl.IMP_IRREGOLARITA,\n" +
					"ptcl.IMP_AGEVOLAZ_IRREG,\n" +
					"ptcl.DT_VISITA_CONTROLLO,\n" +
					"ptcl.TIPO_VISITA,\n" +
					"ptcl.ISTRUTTORE_VISITA, prsp.id_soggetto\n" +
					"FROM PBANDI_T_CONTROLLO_LOCO ptcl\n" +
					"LEFT JOIN PBANDI_R_CAMP_CONTR_LOCO prccl on prccl.ID_CONTROLLO_LOCO = ptcl.ID_CONTROLLO_LOCO\n" +
					"LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.id_progetto = prccl.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\n" +
					"LEFT JOIN PBANDI_T_PROGETTO ptp on ptp.id_progetto = prsp.id_progetto\n" +
					"LEFT JOIN PBANDI_T_DOMANDA ptd on ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
					"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT pbli on pbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"LEFT JOIN PBANDI_D_STATO_CONTR_LOCO pdscl on ptcl.ID_STATO_CONTR_LOCO = pdscl.ID_STATO_CONTR_LOCO\n" +
					"LEFT JOIN PBANDI_D_ATTIV_CONTROLLO_LOCO padc ON padc.ID_ATTIV_CONTR_LOCO = ptcl.ID_ATTIV_CONTR_LOCO\n" +
					"LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\n" +
					"LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\n" +
					"WHERE prsp.ID_TIPO_ANAGRAFICA = 1\n" +
					"AND prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
					"AND ptcl.ID_CONTROLLO_LOCO = ? " + //idControllo
					"AND prsp.ID_PROGETTO = ? "; //idProgetto
			
//			controllo = getJdbcTemplate().query(sql,  new ResultSetExtractor<ControlloLocoVO>() {
//
//				@Override
//				public ControlloLocoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
//					ControlloLocoVO c = new ControlloLocoVO(); 
//					
//					while(rs.next()){
//						c.setDescTipoVisita(rs.getString("TIPO_VISITA"));
//						c.setIstruttoreVisita(rs.getString("ISTRUTTORE_VISITA"));
//						c.setDataAvvioControlli(rs.getDate("DT_AVVIO_CONTROLLI"));
//						c.setDataFineControlli(rs.getDate("DT_FINE_CONTROLLI"));
//						c.setDataInizioControlli(rs.getDate("DT_INIZIO_CONTROLLI"));
//						c.setDataVisitaControllo(rs.getDate("DT_VISITA_CONTROLLO"));
//						c.setImportoAgevIrreg(rs.getBigDecimal("IMP_AGEVOLAZ_IRREG"));
//						c.setImportoIrregolarita(rs.getBigDecimal("IMP_IRREGOLARITA"));
//						
//					}			
//					return c;
//				}
//				
//			});
			List<ControlloLocoVO> list = getJdbcTemplate().query(
					sql,
					ps -> {
						ps.setBigDecimal(1, idControllo);
						ps.setBigDecimal(2, idProgetto);
					},
					new ControlloLocoVORowMapper());
			controllo = list.get(0);
			
		} catch (Exception e) {
			logger.error("errore lettura tabella PBANDI_T_CONTROLLO_LOCO "+ e);
		}
		
		return controllo;
	}
	
	
	// --------- GESTIONE CONTROLLI -----------
	@Override
	public BigDecimal gestioneControllo(ControlloLocoVO controllo, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::gestiioneControllo ]";
		logger.info(prf+"BEGIN");
		try {
			
			
			
		} catch (Exception e) {
			logger.error("errore gestione controllo "+e);
		}
		return null;
	}

	@Override
	public RichiestaIntegrazioneControlloLocoVO checkRichiestaIntegrazione(BigDecimal idControllo, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::checkRichiestaIntegrazione ]";
		logger.info(prf+"BEGIN");
		RichiestaIntegrazioneControlloLocoVO rich = new RichiestaIntegrazioneControlloLocoVO();
		try {
			
		Long idRichiesta; 
		String idEntita = "570";
		idRichiesta = controllaRichiesta (idControllo, idEntita); 
		if(idRichiesta==null) {
			return null;
		} else {
			// recupero i dati della prorooga  
			String sqlProroga ="SELECT\r\n"
					+ "	*\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_RICHIESTA_INTEGRAZ ptri\r\n"
					+ "	JOIN PBANDI_D_STATO_RICH_INTEGRAZ pdsri ON pdsri.ID_STATO_RICHIESTA = ptri.ID_STATO_RICHIESTA \r\n"
					+ "	WHERE ptri.ID_RICHIESTA_INTEGRAZ ="+ idRichiesta; 	
			rich= getJdbcTemplate().query(sqlProroga,  new ResultSetExtractor<RichiestaIntegrazioneControlloLocoVO>() {

				@Override
				public RichiestaIntegrazioneControlloLocoVO extractData(ResultSet rs)
						throws SQLException, DataAccessException {
					RichiestaIntegrazioneControlloLocoVO rich = new RichiestaIntegrazioneControlloLocoVO();
					
					while(rs.next()){
						rich.setDataNotifica(rs.getDate("DT_NOTIFICA"));
						rich.setDataRichiesta(rs.getDate("DT_RICHIESTA"));
						rich.setDataScadenza(rs.getDate("DT_SCADENZA"));
						rich.setNumGiorniScadenza(rs.getInt("NUM_GIORNI_SCADENZA"));
						rich.setDescStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));
						rich.setIdStatoRichiesta(rs.getLong("ID_STATO_RICHIESTA"));
						rich.setIdRichiestaIntegr(idRichiesta);
						//rich.setMotivazione(rs.getString(""));
						
					}
					return rich;
				}
			});
			
			
		}
						
		} catch (Exception e) {
			logger.error("errore lettura tabella richiesta integrazione "+e);
			return null;
		}
		return rich;
	}

	private Long controllaRichiesta(BigDecimal idControllo, String idEntita) {
		Long idRichiesta = null;
		try {
			String sql = "WITH selection AS (SELECT\r\n"
					+ "	ptri.ID_RICHIESTA_INTEGRAZ \r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_RICHIESTA_INTEGRAZ ptri\r\n"
					+ "WHERE\r\n"
					+ "	ID_ENTITA = ?\r\n"
					+ "	AND ID_TARGET =?\r\n"
					+ "ORDER BY ptri.ID_RICHIESTA_INTEGRAZ DESC )\r\n"
					+ "SELECT *\r\n"
					+ "FROM selection \r\n"
					+ "WHERE rownum <2"; 
			
			idRichiesta = getJdbcTemplate().queryForObject(sql,Long.class, new Object[] {idEntita,idControllo});

		} catch (Exception e) {
			logger.error("errore lettura tabella PBANDI_T_RICHIESTA_INTEGRAZ"+ e);
		}
		
		return idRichiesta;
	}

//	@Override
//	public List<AttivitaDTO> getElencoAllegati(BigDecimal idControllo) {
//		String prf = "[AreaControlliDAOImpl::getElencoAllegati ]";
//		logger.info(prf+"BEGIN");
//
//		List<AttivitaDTO> allegati = new ArrayList<AttivitaDTO>();
//
//		try {
//
//			String sql = "SELECT * FROM PBANDI_T_DOCUMENTO_INDEX \r\n"
//					+ "WHERE \r\n"
//					+ "ID_ENTITA =570r\n"
//					+ "AND id_target="+idControllo;
//
//			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {
//
//				@Override
//				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//					AttivitaDTO documento = new AttivitaDTO();
//
//					documento.setIdAttivita(rs.getLong("ID_DOCUMENTO_INDEX"));
//					documento.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
//					documento.setIdTipodocumentoIndex(rs.getBigDecimal("ID_TIPO_DOCUMENTO_INDEX"));
//					documento.setDescAttivita(rs.getString("NOME_FILE"));
//
//					return documento;
//				}
//			};
//
//			allegati = getJdbcTemplate().query(sql, lista);
//
//		} catch (Exception e) {
//			logger.error("errore lettura tabella pbandi_t_documento_index"+e);
//		}
//
//
//		return allegati;
//	}

	@Override
	public Boolean updateControlloFinp(ControlloLocoVO controlloVO, BigDecimal idUtente, int idAttivitaControllo) {
		String prf = "[AreaControlliDAOImpl::avvia Iter ]";
		logger.info(prf+"BEGIN");
		Boolean result = true;
		try {
			String dataInizioControllo = null, dataFinecontrollo = null,dataVisitaControllo = null, dataAvvioControlli=null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt3 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			if(controlloVO.getDataInizioControlli()!=null) {
				 dataInizioControllo = dt.format(controlloVO.getDataInizioControlli());
			} 
			
			if(controlloVO.getDataFineControlli()!=null) {
				dataFinecontrollo=  dt1.format(controlloVO.getDataFineControlli());
			}
	
			if(controlloVO.getDataVisitaControllo()!=null) {
				 dataVisitaControllo = dt2.format(controlloVO.getDataVisitaControllo());
			}
			
			if(controlloVO!=null && controlloVO.getDataAvvioControlli()!=null) {
				dataAvvioControlli=  dt3.format(controlloVO.getDataAvvioControlli());
			}
		
			
			// prima di fare l'update devo recuperare dal dB l'ultimo stato del controllo
			int idPreviousState = getPreviousStateControllo(controlloVO.getIdControllo(),1);
			// update tabella pbandi_t_controllo_loco
			String updateTcontrollo="update PBANDI_T_CONTROLLO_LOCO set "
					+ " ID_STATO_CONTR_LOCO=?," + "\r\n"
					//+ " ID_ATTIV_CONTR_LOCO=?," + "\r\n"
					+ " DT_INIZIO_CONTROLLI=?," + "\r\n"
					+ " DT_FINE_CONTROLLI=?," + "\r\n"
					+ " IMP_DA_CONTROLLARE=?," + "\r\n"
					+ " IMP_IRREGOLARITA=?," + "\r\n"
					+ " IMP_AGEVOLAZ_IRREG=?," + "\r\n"
					+ " DT_VISITA_CONTROLLO=?," + "\r\n"
					+ " TIPO_VISITA=?," + "\r\n"
					+ " ISTRUTTORE_VISITA=?," + "\r\n"
					+ " ID_UTENTE_AGG=?," + "\r\n"
					+ " DT_AVVIO_CONTROLLI=?," + "\r\n"
					+ "DT_AGGIORNAMENTO=trunc(sysdate)\r\n"
					+ " where ID_CONTROLLO_LOCO ="+controlloVO.getIdControllo(); 
			//int id=3;
			getJdbcTemplate().update(updateTcontrollo, new Object[] {
				controlloVO.getIdStatoControllo(), 
				//idAttivitaControllo,
				(dataInizioControllo!=null&&dataInizioControllo.trim().length()>0)? Date.valueOf(dataInizioControllo):null, 
				(dataFinecontrollo!=null&&dataFinecontrollo.trim().length()>0)?Date.valueOf(dataFinecontrollo):null, 
				controlloVO.getImportoDaControllare(), 
				controlloVO.getImportoIrregolarita(), 
				controlloVO.getImportoAgevIrreg(),
				(dataVisitaControllo!=null&&dataVisitaControllo.trim().length()>0)?Date.valueOf(dataVisitaControllo):null,
				controlloVO.getDescTipoVisita(), 
				controlloVO.getIstruttoreVisita(),
				idUtente, 
				(dataAvvioControlli!=null&&dataAvvioControlli.trim().length()>0)?Date.valueOf(dataAvvioControlli):null,
			});
			
			// chiamata al servizio iter _autorizzativi_ quando faccio sia avvio controllo_loco che avvio richiesta integrazione
//			Boolean isAvvioIterInteg; 
//			isAvvioIterInteg = iterAutoDao.avviaIterAutorizzativo((long)5, richiesta.getIdEntita(), controlloVO.getid.longValue(), controlloVO.getIdProgetto(), idUtente.longValue());
	
			// parametri per servizio di revoca
//			idAutoritaControllante :  null
//			idCause: 18
//			id_Utente: 
//			numeroRevoca:  nextval
//			dataPropostaRevoca: dataAttuale, 
//			idProgetto:
			// 4=parz neg, neg =5 
			// se lo stato attuale è parzialemente negativo o negativo chiamare il servizio della revoca 
			//Date date = ;
			
			//String date = dataRevoca.format(LocalDate.now());
	
			
			
		} catch (Exception e) {
			logger.error("errore inserimento tabella pbandi_t_controllo"+e);
			result = false;
		}
		return result;
	}

	public int getPreviousStateControllo(BigDecimal idControllo, int idTabella) {
		int idStatoControllo=0; 
		try {
			if(idTabella==1) {				
				idStatoControllo =
						getJdbcTemplate().queryForObject("select ID_STATO_CONTR_LOCO from PBANDI_T_CONTROLLO_LOCO where ID_CONTROLLO_LOCO="+ idControllo, int.class);
			} else {
				idStatoControllo =
						getJdbcTemplate().queryForObject("select ID_STATO_CONTR_LOCO from PBANDI_T_CONTROLLO_LOCO_ALTRI where ID_CONTROLLO="+ idControllo, int.class);
			}
			
		} catch (Exception e) {
			logger.error(e);
			return 0; 
		}
		return idStatoControllo;
	}

	@Override
	public Boolean salvaAllegato(MultipartFormDataInput multipartFormData) {
		String prf = "[AreaControlliDAOImpl:: salva Allegato]";
		logger.info(prf+"BEGIN");
		
		//  lettera avvio controllo in locco :  id_tipo_documento_index =56
		//  lettera accompagnatoria controllo in locco : id_tipo... =57 
		// entita =1 corrisponde alla tabella PBANDI_T_CONTROLLO_LOCO
		// entita =2 corrisponde alla tabella PBANDI_T_RICHIESTA_INTEGRAZ
		// entita =3 corrisponde alla tabella PBANDI_T_CONTROLLO_LOCO_ALTRI
		Boolean result = null; 
		try {
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			BigDecimal idTarget = multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null); 
			BigDecimal idTipoDocumentoIndex = multipartFormData.getFormDataPart("idTipoDocumentoIndex", BigDecimal.class, null);
			BigDecimal idProgetto = multipartFormData.getFormDataPart("idProgetto", BigDecimal.class, null);
			int entita= multipartFormData.getFormDataPart("entita", int.class, null);
			String getEntita=null ;
			if(entita==1) {				
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_CONTROLLO_LOCO'\r\n";
			} 
			if(entita==2) {
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_RICHIESTA_INTEGRAZ'\r\n";
			}
			if(entita==3) {
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_CONTROLLO_LOCO_ALTRI'\r\n";
			}
			if(entita==4) {
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_GESTIONE_REVOCA'\r\n";
			}
			
			BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				logger.info("listInputPart NULLO");
			} else {
				logger.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					logger.info("SET = "+x);
				}
			}
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) ;
			
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX \r\n"
					+ " where ID_TIPO_DOCUMENTO_INDEX="+idTipoDocumentoIndex, String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(numEntita);
			documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
			//PBANDI_T_PROGETTO
			documentoIndexVO.setIdProgetto(idProgetto);			//id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
			
			
		} catch (Exception e) {
			logger.error("errore inserimento pbandi_t_documento_index "+e);
			result= false; 
		}
		return result;
	}
	
//	GESTIONE RICHIESTA INTEGRAZIONE 

	@Transactional
	@Override
	public BigDecimal avviaIterIntegrazione(RichiestaIntegrazioneControlloLocoVO richiesta, BigDecimal idUtente) {
		String prf = "[AreaControlliDAOImpl::avviaIterIntegrazione ]";
		logger.info(prf+"BEGIN");
		BigDecimal idRichiesta=null; 
		try {
			String sqlSeq = "SELECT SEQ_PBANDI_T_RICH_INTEGRAZ.nextval FROM dual";									
			idRichiesta = getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			
			
			// prima cosa da fare :  avvio iter richiesta di integrazione. 
			String erroreIter = iterAutoDao.avviaIterAutorizzativo((long)5, (long)569, idRichiesta.longValue(), richiesta.getIdProgetto(), idUtente.longValue());
			if(!Objects.equals(erroreIter, "")){
				throw new ErroreGestitoException(erroreIter);
			}

			// inserisco richiesta di integrazione
			String sql ="insert into PBANDI_T_RICHIESTA_INTEGRAZ (ID_RICHIESTA_INTEGRAZ,\r\n"
					+ "ID_ENTITA,\r\n"
					+ "ID_TARGET,\r\n"
					//+ "DESCRIZIONE,\r\n" // motivazione
					+ "DT_RICHIESTA,\r\n"
					+ "ID_UTENTE_RICHIESTA,\r\n"
					+ "ID_STATO_RICHIESTA,\r\n"
					+ "NUM_GIORNI_SCADENZA,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO\r\n"
					+ ") values(?,?,?, trunc(sysdate), ?, 4,?,trunc(sysdate),?,trunc(sysdate))";
			getJdbcTemplate().update(sql,
					idRichiesta,
					richiesta.getIdEntita(),
					richiesta.getIdTarget(),
					//richiesta.getMotivazione(),
					idUtente,
					richiesta.getNumGiorniScadenza(),
					idUtente);
			// aggiorno la tabella controli in loco o altri controlli 
			if (richiesta.getIdEntita() == (long)570) {
				getJdbcTemplate().update("update PBANDI_T_CONTROLLO_LOCO SET ID_ATTIV_CONTR_LOCO = 4,"
						+ " DT_AGGIORNAMENTO=TRUNC(SYSDATE) WHERE ID_CONTROLLO_LOCO="+ richiesta.getIdTarget());
				
				// update stato controllo nella tabella ptcl 
				String updatePtcl="update PBANDI_T_CONTROLLO_LOCO set ID_STATO_CONTR_LOCO=3 WHERE ID_CONTROLLO_LOCO="+richiesta.getIdTarget();
				getJdbcTemplate().update(updatePtcl); 
			} else {
				getJdbcTemplate().update("update PBANDI_T_CONTROLLO_LOCO_ALTRI SET ID_ATTIV_CONTR_LOCO = 4 ,"
						+ "DT_AGGIORNAMENTO=TRUNC(SYSDATE) WHERE ID_CONTROLLO="+ richiesta.getIdTarget());
				
				// update stato controllo nella tabella ptcl 
				String updatePtcl="update PBANDI_T_CONTROLLO_LOCO_ALTRI set ID_STATO_CONTR_LOCO=3 WHERE ID_CONTROLLO="+richiesta.getIdTarget();
				getJdbcTemplate().update(updatePtcl); 
			}
		
			
			
			// INSERIMENTO TABELLA PBANDI_T_PROROGA PIU AVANTI VEDREMMO COME GESTIRE LA PROROGA
//			String insert ="insert into PBANDI_T_PROROGA (ID_RICHIESTA_PROROGA,\r\n"
//					+ "ID_ENTITA,\r\n"
//					+ "ID_TARGET,\r\n"
//					+ "DT_RICHIESTA,\r\n"
//					+ "NUM_GIORNI_RICH,\r\n"
//					//+ "MOTIVAZIONE,\r\n"
//					+ "ID_STATO_PROROGA,\r\n"
//					+ "ID_UTENTE_INS,\r\n"
//					+ "DT_INSERIMENTO,\r\n"
//					+ "DT_AGGIORNAMENTO) VALUES (SEQ_PBANDI_T_PROROGA.nextval,569,?,trunc(sysdate),?,1,?,trunc(sysdate),trunc(sysdate))"; 
//			
//			getJdbcTemplate().update(insert, new Object[] { idRichiesta, richiesta.getNumGiorniScadenza(),
					//richiesta.getMotivazione(),
//					idUtente });
	} catch (Exception e) {
		logger.error("errore inserimento pbandi_t_richiesta_integrazione " + e);
		return null;
	}

	return idRichiesta;
}

	@Transactional
	@Override
	public Boolean chiamaIterAuto002(MultipartFormDataInput multipartFormData) {
		String prf = "[AreaControlliDAOImpl::chiamaIterAuto002 ]";
		logger.info(prf+"BEGIN");
		Boolean result =true; 
		try {
			
			
			// id = 1 corrisponde a avvio controllo in loco
			// id = 2 corrisponde a richiesta integrazione
			// isControlloFinp = 1 corrisponde a un controllo su progetto Finpiemonte.
			// isControlloFinp = 2 corrisponde a un controllo su altri progetto pbandi_t_controllo_altri.
			// se avvio controllo  = 2 si tratta dell'avvio della richiesta di integrazione
			// se avvio controllo = 1 si tratta dell'avvio del controllo in loco. 
			// chiamata al servizio iter _autorizzativi_ quando faccio sia avvio controllo_loco che avvio richiesta integrazione con id_entita = 569 per integrazione
			// id_entita = 570 per avvio controllo_locco, •	id_tipo_iter = 6 
			// avvia iter cdu ITERAUT002 passando i parametri : id_tipo_iter = 6 entita = 570, target = controlloVO.getIdControllo(); id_progetto =  controlloVO.getIdProgetto();
			// e ricevo un esito: successo
			
			Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
			Long idTarget = multipartFormData.getFormDataPart("idTarget", Long.class, null); 
			Long idControllo = multipartFormData.getFormDataPart("idControllo", Long.class, null);
			Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
			Long idEntita = multipartFormData.getFormDataPart("idEntita", Long.class, null);
			int isAvvioControllo = multipartFormData.getFormDataPart("isAvvioControllo", int.class, null);
			int isControlloFinp = multipartFormData.getFormDataPart("isControlloFinp", int.class, null);
			
			switch (isAvvioControllo) {
			case 1:
				Long idTipoIter= (long) 6; 
				if (isControlloFinp==1) {
					// chiamo il servizio di iter
					String erroreIter = iterAutoDao.avviaIterAutorizzativo(idTipoIter, idEntita, idTarget, idProgetto, idUtente);
					if(!Objects.equals(erroreIter, "")){
						result = false;
						throw new ErroreGestitoException(erroreIter);
					}
					// aggiorno lo stato attivita nella tabelle pbandi_t_controllo_loco_finp A 1
					if(result)
						getJdbcTemplate().update("update PBANDI_T_CONTROLLO_LOCO SET ID_ATTIV_CONTR_LOCO =1 ,"
								+ "DT_AGGIORNAMENTO=TRUNC(SYSDATE) WHERE ID_CONTROLLO_LOCO="+ idControllo);
					
				} else {
					// chiamo il servizio di iter
					String erroreIter = iterAutoDao.avviaIterAutorizzativo(idTipoIter, idEntita, idTarget, idProgetto, idUtente);
//					result = iterAutoDao.avviaIterAutorizzativo((long)2, (long)63, (long)29624, (long)22157, idUtente);
					if(!Objects.equals(erroreIter, "")){
						result = false;
						throw new ErroreGestitoException(erroreIter);
					}
					// aggiorno lo stato attivita nella tabelle pbandi_t_controllo_loco_altri 1
					if(result)
						getJdbcTemplate().update("update PBANDI_T_CONTROLLO_LOCO_ALTRI SET ID_ATTIV_CONTR_LOCO =1,"
								+ "DT_AGGIORNAMENTO=TRUNC(SYSDATE) WHERE ID_CONTROLLO="+ idControllo);
				}
				break;
			case 2:
				Long idTipoIterIntegrazione= (long) 5; 
				if (isControlloFinp==1) {
					// chiamo il servizio iter002
					String erroreIter = iterAutoDao.avviaIterAutorizzativo(idTipoIterIntegrazione, idEntita, idTarget, idProgetto, idUtente);
					if(!Objects.equals(erroreIter, "")){
						throw new ErroreGestitoException(erroreIter);
					}
					result = true;
					// aggiorno id_attvitia controllo a 4 nella T_controllo
					if(result)
						getJdbcTemplate().update("update PBANDI_T_CONTROLLO_LOCO SET ID_ATTIV_CONTR_LOCO = 4,"
								+ " DT_AGGIORNAMENTO=TRUNC(SYSDATE) WHERE ID_CONTROLLO_LOCO="+ idControllo);
				} else {
					// chiamo il servizio iter002
					String erroreIter = iterAutoDao.avviaIterAutorizzativo(idTipoIterIntegrazione, idEntita, idTarget, idProgetto, idUtente);
					if(!Objects.equals(erroreIter, "")){
						throw new ErroreGestitoException(erroreIter);
					}
					result = true;
					// aggiorno id_attvitia controllo a 4 nella T_controllo_altri
					if(result)
						getJdbcTemplate().update("update PBANDI_T_CONTROLLO_LOCO_ALTRI SET ID_ATTIV_CONTR_LOCO = 4 ,"
								+ "DT_AGGIORNAMENTO=TRUNC(SYSDATE) WHERE ID_CONTROLLO="+ idControllo);
				}
				
				break;

			default:
				break;
			}
					
		} catch (Exception e) {
			logger.error("errore chiamata servizio "+e);
			return false; 
		}
		return result;
	}

	@Override
	public Boolean chiudiRichiestaIntegr(BigDecimal idRichiestaIntegr, BigDecimal idUtente) {
		String prf = "[AreaControlliDAOImpl::chiudiRichiestaIntegr ]";
		logger.info(prf+"BEGIN");
		try {

			
			String update="update PBANDI_T_RICHIESTA_INTEGRAZ "
					+ "set ID_STATO_RICHIESTA=3,"
					+ "DT_FINE_VALIDITA = trunc(sysdate),\r\n"
					+ "DT_AGGIORNAMENTO = trunc(sysdate),\r\n"
					+ "ID_UTENTE_AGG =" +idUtente +"\r\n"
					+ " WHERE ID_RICHIESTA_INTEGRAZ=" + idRichiestaIntegr;
			
			getJdbcTemplate().update(update); 
		} catch (Exception e) {
			logger.error("errore "+e);
			return false; 
		}
		return true;
	}

	@Override
	public RichiestaProrogaVO getRichProroga(BigDecimal idRichiestaIntegr) {
		String prf = "[AreaControlliDAOImpl::getRichProroga ]";
		logger.info(prf+"BEGIN");
		RichiestaProrogaVO proroga = new RichiestaProrogaVO();
		
		try {
			String get ="SELECT\r\n"
					+ "	*\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_PROROGA p\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_PROROGA pdsp ON p.ID_STATO_PROROGA  = pdsp.ID_STATO_PROROGA \r\n"
					+ "WHERE\r\n"
					+ "	ID_ENTITA = 569"
					+ " AND ID_TARGET="+ idRichiestaIntegr +"\r\n"
					+ " AND p.ID_STATO_PROROGA=1"
					+ "AND rownum =1\r\n"
					+ "ORDER BY p.ID_RICHIESTA_PROROGA desc";
			
			proroga= getJdbcTemplate().query(get,  new ResultSetExtractor<RichiestaProrogaVO>() {
				@Override
				public RichiestaProrogaVO extractData(ResultSet rs)
						throws SQLException, DataAccessException {
					RichiestaProrogaVO rich = new RichiestaProrogaVO();
					
					while(rs.next()){
						rich.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
						rich.setDataRichiesta(rs.getDate("DT_RICHIESTA"));
						rich.setNumGiorniRichiesta(rs.getInt("NUM_GIORNI_RICH"));
						//rich.setDescStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));
						rich.setDescStatoProroga(rs.getString("DESC_STATO_PROROGA"));
						rich.setIdRichiestaProroga(rs.getBigDecimal("ID_RICHIESTA_PROROGA"));;
						rich.setMotivazione(rs.getString("MOTIVAZIONE"));
						rich.setIdStatoProroga(rs.getInt("ID_STATO_PROROGA"));
						rich.setNumGiorniApprov(rs.getInt("NUM_GIORNI_APPROV"));
						rich.setIdTarget(idRichiestaIntegr);
						
					}
					return rich;
				}
			});
			
		} catch (Exception e) {
			logger.error(prf+" "+e);
		}
		
		
		return proroga;
	}
	@Override
	public List< RichiestaProrogaVO> getElencoRichProroga(BigDecimal idRichiestaIntegr) {
		String prf = "[AreaControlliDAOImpl::getRichProroga ]";
		logger.info(prf+"BEGIN");
		List<RichiestaProrogaVO> elenco = new ArrayList<RichiestaProrogaVO>(); 
		
		try {
			String get ="SELECT\r\n"
					+ "	*\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_PROROGA p\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_PROROGA pdsp ON p.ID_STATO_PROROGA  = pdsp.ID_STATO_PROROGA \r\n"
					+ "WHERE\r\n"
					+ "	ID_ENTITA = 569"
					+ " AND p.ID_STATO_PROROGA<>1\r\n"
					+ "AND ID_TARGET="+ idRichiestaIntegr +"\r\n"
					+ "ORDER BY p.ID_RICHIESTA_PROROGA desc";
			
			elenco= getJdbcTemplate().query(get,
					
					(rs, rowNum)->{		
					RichiestaProrogaVO rich = new RichiestaProrogaVO(); 
						rich.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
						rich.setDataRichiesta(rs.getDate("DT_RICHIESTA"));
						rich.setNumGiorniRichiesta(rs.getInt("NUM_GIORNI_RICH"));
						//rich.setDescStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));
						rich.setDescStatoProroga(rs.getString("DESC_STATO_PROROGA"));
						rich.setIdRichiestaProroga(rs.getBigDecimal("ID_RICHIESTA_PROROGA"));;
						rich.setMotivazione(rs.getString("MOTIVAZIONE"));
						rich.setIdStatoProroga(rs.getInt("ID_STATO_PROROGA"));
						rich.setNumGiorniApprov(rs.getInt("NUM_GIORNI_APPROV"));
						rich.setIdTarget(idRichiestaIntegr);	
						return rich;
				});
			
		} catch (Exception e) {
			logger.error(prf+" "+e);
		}
		
		
		return elenco;
	}

	@Override
	public Boolean gestisciProroga(RichiestaProrogaVO proroga, BigDecimal idUtente, int id) {
		String prf = "[AreaControlliDAOImpl::gestisciProroga ]";
		logger.info(prf+"BEGIN");
		// id=1 Approvata; 
		// id=2 Respinta;		
		
		try {
			
				if(id==1 ) {
					String update ="update PBANDI_T_PROROGA set \r\n"
							+ " id_stato_proroga=2,\r\n"
							+ " NUM_GIORNI_APPROV=?,\r\n"
							+ "DT_AGGIORNAMENTO=TRUNC (SYSDATE),\r\n"
							+ "DT_ESITO_RICHIESTA=TRUNC (SYSDATE), \r\n"
							+ "ID_UTENTE_AGG=?\r\n"
							+ " where id_entita  = 569 \r\n"
							+ " and  id_target="+ proroga.getIdTarget() ;
					
					getJdbcTemplate().update(update, new Object[] {
							proroga.getNumGiorniApprov(),	idUtente
					});
					
					// aggiorno la data di scadenza della richiesta di integazione
					String updateDtScadenza="update PBANDI_T_RICHIESTA_INTEGRAZ set DT_SCADENZA =(  SELECT TO_DATE(ptri.DT_SCADENZA + ?)\r\n"
							+ "    FROM PBANDI_T_RICHIESTA_INTEGRAZ ptri\r\n"
							+ "    WHERE ptri.ID_RICHIESTA_INTEGRAZ =?) where ID_RICHIESTA_INTEGRAZ =?";
					
					getJdbcTemplate().update(updateDtScadenza, new Object[] {
						proroga.getNumGiorniApprov(), proroga.getIdTarget(), proroga.getIdTarget()	
					});
					
				} else {
					String update ="update PBANDI_T_PROROGA set \r\n"
							+ " id_stato_proroga=3,\r\n"
							+ "DT_AGGIORNAMENTO=TRUNC (SYSDATE),\r\n"
							+ "DT_ESITO_RICHIESTA=TRUNC (SYSDATE), \r\n"
							+ "ID_UTENTE_AGG="+ idUtente + "\r\n"
							+ " where id_entita  = 569 \r\n"
							+ " and  id_target="+ proroga.getIdTarget() ;
					getJdbcTemplate().update(update);
				}
			
		} catch (Exception e) {
			logger.error(prf + " " +e);
			return false;
		}
		
		return true;
	}

	@Override
	public Boolean salvaDataNotifica(RichiestaIntegrazioneControlloLocoVO richiesta, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::salvaDataNotifica ]";
		logger.info(prf+"BEGIN");
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {
//			String dataNotifica = null;
//			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			 Calendar dataScadenza = Calendar.getInstance();
             dataScadenza.setTime(richiesta.getDataNotifica());
//			if(richiesta.getDataNotifica()!=null) {
//				dataNotifica = dt.format(richiesta.getDataNotifica());
//			} 
//			
//			String sql = "update PBANDI_T_RICHIESTA_INTEGRAZ set DT_NOTIFICA =?, DT_SCADENZA =?\r\n"
//					+ "WHERE ID_RICHIESTA_INTEGRAZ=?";
//			getJdbcTemplate().update(sql, new Object[] {
//					(dataNotifica!=null&&dataNotifica.trim().length()>0)? Date.valueOf(dataNotifica):null,
//					(dataNotifica!=null&&dataNotifica.trim().length()>0)? Date.valueOf(dataNotifica):null,
//					richiesta.getIdRichiestaIntegr()
//			});
		//UPDATE RICHIESTA INTEGRAZIONE
        //Calendar dataScadenza = Calendar.getInstance();
        dataScadenza.setTime(richiesta.getDataNotifica());
        dataScadenza.add(Calendar.DATE, richiesta.getNumGiorniScadenza());
        String query =
                "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ  \n" +
                        "SET DT_NOTIFICA = ? \n" +
                        ", DT_SCADENZA = ?\n" +
                        ", ID_UTENTE_AGG = ? \n" +
                        ", DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE ID_RICHIESTA_INTEGRAZ=?";
        logger.info(prf + "\nQuery: \n\n" + query + "\n\n");
        getJdbcTemplate().update(query, richiesta.getDataNotifica(), dataScadenza.getTime(), userInfoSec.getIdUtente(),richiesta.getIdRichiestaIntegr() );
			
			
		} catch (Exception e) {
			logger.error(prf + " " +e);
			return false; 
		}
		return true;
	}

	@Override
	public BigDecimal salvaAltroControllo(ControlloLocoVO controlloVO, BigDecimal idUtente, int idAttivitaControllo) {
		String prf = "[AreaControlliDAOImpl::salvaDataNotifica ]";
		logger.info(prf+"BEGIN");
		
		BigDecimal idControllo = null; 
		
		try {
			
			String dataInizioControllo = null, dataFinecontrollo = null,dataVisitaControllo = null, dataAvvioControllo=null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt3 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			
			if(controlloVO.getDataInizioControlli()!=null) {
				 dataInizioControllo = dt.format(controlloVO.getDataInizioControlli());
			} 
			
			if(controlloVO.getDataFineControlli()!=null) {
				dataFinecontrollo=  dt1.format(controlloVO.getDataFineControlli());
			}
			
			if(controlloVO.getDataVisitaControllo()!=null) {
				 dataVisitaControllo = dt2.format(controlloVO.getDataVisitaControllo());
			}
			if(controlloVO.getDataAvvioControlli()!=null) {
				dataAvvioControllo = dt3.format(controlloVO.getDataAvvioControlli());
			}
			
			
			
			idControllo = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_CONTR_LOCO_ALTRI.nextval FROM dual", BigDecimal.class);
			
			String insert = "INSERT INTO PBANDI_T_CONTROLLO_LOCO_ALTRI(ID_CONTROLLO,\r\n"
					+ "ID_PROGETTO,\r\n"
					+ "ID_STATO_CONTR_LOCO,\r\n"
					+ "DT_INIZIO_CONTROLLI,\r\n"
					+ "DT_FINE_CONTROLLI,\r\n"
					+ "IMP_DA_CONTROLLARE,\r\n"
					+ "IMP_IRREGOLARITA,\r\n"
					+ "IMP_AGEVOLAZ_IRREG,\r\n"
					+ "ID_CATEG_ANAGRAFICA,\r\n"
					+ "TIPO_CONTROLLI,\r\n"
					+ "ISTRUTTORE_VISITA,\r\n"
					+ "DT_AVVIO_CONTROLLI,\r\n"
					+ "DT_VISITA_CONTROLLO,\r\n"
					+ "TIPO_VISITA,\r\n"
					+ "NUM_PROTOCOLLO,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO\r\n"
					+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TRUNC(SYSDATE),?,TRUNC(SYSDATE))";
			
			getJdbcTemplate().update(insert, new Object[] {
					idControllo, 
					controlloVO.getIdProgetto(), 
					controlloVO.getIdStatoControllo(),
					(dataInizioControllo!=null&&dataInizioControllo.trim().length()>0)? Date.valueOf(dataInizioControllo):null, 
					(dataFinecontrollo!=null&&dataFinecontrollo.trim().length()>0)?Date.valueOf(dataFinecontrollo):null, 
					controlloVO.getImportoDaControllare(), 
					controlloVO.getImportoIrregolarita(), 
					controlloVO.getImportoAgevIrreg(), 
					controlloVO.getIdAutoritaControllante(), 
					controlloVO.getTipoControllo().trim(), 
					(controlloVO.getIstruttoreVisita()!=null)?controlloVO.getIstruttoreVisita():null,
					(dataAvvioControllo!=null&&dataAvvioControllo.trim().length()>0)?Date.valueOf(dataAvvioControllo):null,
					(dataVisitaControllo!=null&&dataVisitaControllo.trim().length()>0)?Date.valueOf(dataVisitaControllo):null,
					(controlloVO.getDescTipoVisita()!=null)?controlloVO.getDescTipoVisita():null,
					controlloVO.getNumProtocollo(),
					idUtente,
			});
			
			
			
		} catch (Exception e) {
			logger.error(prf+" "+ e);
			return null; 
		}
		
		
		return idControllo;
	}

	@Override
	public List<AttivitaDTO> getListaBando(BigDecimal idSoggetto) {
		String prf = "[AreaControlliDAOImpl::getListaBando ]";
		logger.info(prf+"BEGIN");

		List<AttivitaDTO> listaBandi = new ArrayList<AttivitaDTO>();

		try {
			String sql = "SELECT\r\n"
					+ "    DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "    prbli.NOME_BANDO_LINEA,\r\n"
					+ "    prbli.id_bando\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "WHERE\r\n"
					+ "     prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.ID_SOGGETTO ="+idSoggetto;

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO bando = new AttivitaDTO();
					bando.setDescAttivita(rs.getString("NOME_BANDO_LINEA"));
					bando.setProgBandoLinea(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
					bando.setIdAttivita(rs.getLong("ID_BANDO"));
					return bando;
				}
			};

			listaBandi = getJdbcTemplate().query(sql, lista);

		} catch (Exception e) {

			logger.error(prf + "Exception while trying to read PBANDI_T_BANDO", e);
		}

		return listaBandi;
	}

	@Override
	public List<AttivitaDTO> getListaProgetto(BigDecimal idSoggetto, BigDecimal progBandoLinea) {
		String prf = "[AreaControlliDAOImpl::getListaProgetto ]";
		logger.info(prf+"BEGIN");
		List<AttivitaDTO> listaCodiceProgetto = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT\r\n"
					+ "    DISTINCT prsp.ID_PROGETTO,\r\n"
					+ "    ptp.CODICE_VISUALIZZATO, prsp.id_soggetto\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "WHERE\r\n"
					+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prbli.PROGR_BANDO_LINEA_INTERVENTO = "+progBandoLinea+"\r\n"
					+ "    AND prsp.ID_SOGGETTO ="+idSoggetto;
			
			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO codProg = new AttivitaDTO();
					codProg.setDescAttivita(rs.getString("CODICE_VISUALIZZATO"));
					codProg.setIdAttivita(rs.getLong("ID_PROGETTO"));
					codProg.setIdSoggetto(rs.getLong("id_soggetto"));
					return codProg;
				}
			};

			listaCodiceProgetto = getJdbcTemplate().query(query, lista);
		} catch (Exception e) {
			logger.error(prf+" "+ e);
		}
		return listaCodiceProgetto;
	}

	@Override
	public List<ControlloLocoVO> getAltriControlli(RicercaControlliDTO searchDTO) {
		String prf = "[AreaControlliDAOImpl::getAltriControlli ]";
		logger.info(prf+"BEGIN");
		
		List<ControlloLocoVO> elenco = new ArrayList<ControlloLocoVO>(); 
		
		try {
			
			StringBuilder sql = new StringBuilder(); 
			
			sql.append("WITH denom AS (\r\n"
					+ "    SELECT\r\n"
					+ "        concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\r\n"
					+ "        PERSFIS.ID_SOGGETTO,\r\n"
					+ "        persfis.ID_PERSONA_FISICA\r\n"
					+ "    FROM\r\n"
					+ "        PBANDI_T_PERSONA_FISICA persfis\r\n"
					+ "),\r\n"
					+ "denom2 AS (\r\n"
					+ "    SELECT\r\n"
					+ "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\r\n"
					+ "        ENTEGIU.ID_SOGGETTO,\r\n"
					+ "        entegiu.ID_ENTE_GIURIDICO\r\n"
					+ "    FROM\r\n"
					+ "        PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
					+ ")\r\n"
					+ "SELECT\r\n"
					+ "    DISTINCT ptp.CODICE_VISUALIZZATO,\r\n"
					+ "    pbli.NOME_BANDO_LINEA,\r\n"
					+ "    pdscl.DESC_STATO_CONTR_LOCO,\r\n"
					+ "    pdscl.ID_STATO_CONTR_LOCO,\r\n"
					+ "    ptcla.IMP_DA_CONTROLLARE,\r\n"
					+ "    padc.DESC_ATTIV_CONTR_LOCO,\r\n"
					+ "    padc.ID_ATTIV_CONTR_LOCO,\r\n"
					+ "    denom.denominazione_fis,\r\n"
					+ "    denom.ID_PERSONA_FISICA,\r\n"
					+ "    denom2.denominazione_ente,\r\n"
					+ "    denom2.ID_ENTE_GIURIDICO,\r\n"
					+ "    prsp.ID_PROGETTO,\r\n"
					+ "    ptcla.ID_CONTROLLO,\r\n"
					+ "    pbli.FLAG_SIF,\r\n"
					+ "    prsp.ID_SOGGETTO,\r\n"
					+ "    ptcla.DT_AVVIO_CONTROLLI,\r\n"
					+ "    ptcla.DT_INIZIO_CONTROLLI,\r\n"
					+ "    ptcla.DT_FINE_CONTROLLI,\r\n"
					+ "    ptcla.IMP_IRREGOLARITA,\r\n"
					+ "    ptcla.IMP_AGEVOLAZ_IRREG,\r\n"
					+ "    ptcla.DT_VISITA_CONTROLLO,\r\n"
					+ "    ptcla.TIPO_VISITA,\r\n"
					+ "    ptcla.ISTRUTTORE_VISITA,\r\n"
					+ "    ptcla.ID_CATEG_ANAGRAFICA ,\r\n"
					+ "    pdca.DESC_CATEG_ANAGRAFICA ,\r\n"
					+ "    ptcla.TIPO_CONTROLLI , ptcla.NUM_PROTOCOLLO, ptcla.ID_STATO_CHECKLIST \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_CONTROLLO_LOCO_ALTRI  ptcla\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.id_progetto = ptcla.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
					+ "    LEFT JOIN PBANDI_T_PROGETTO ptp on ptp.id_progetto = prsp.id_progetto\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd on ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT pbli on pbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_CONTR_LOCO pdscl on ptcla.ID_STATO_CONTR_LOCO = pdscl.ID_STATO_CONTR_LOCO\r\n"
					+ "    LEFT JOIN PBANDI_D_ATTIV_CONTROLLO_LOCO padc ON padc.ID_ATTIV_CONTR_LOCO = ptcla.ID_ATTIV_CONTR_LOCO\r\n"
					+ "    LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
					+ "    LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "    LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA pdca ON pdca.ID_CATEG_ANAGRAFICA  = ptcla.ID_CATEG_ANAGRAFICA \r\n"
					+ "WHERE\r\n"
					+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4");
			
			
			
			if(searchDTO.getIdProgetto()!=null) 
				sql.append("and prsp.id_progetto="+ searchDTO.getIdProgetto()+ "\r\n");
			if(searchDTO.getNumProtocollo()!=null)
				sql.append("and ptcla.NUM_PROTOCOLLO="+ searchDTO.getNumProtocollo()+ "\r\n");
			if(searchDTO.getIdSoggetto()!=null)
				sql.append(" and prsp.id_soggetto="+ searchDTO.getIdSoggetto()+ "\r\n");
			if(searchDTO.getIdStatoControllo()!=null)
				sql.append("and ptcla.ID_STATO_CONTR_LOCO = "+searchDTO.getIdStatoControllo()+ "\r\n");
			if (searchDTO.getProgrBandoLinea()!=null) 
				sql.append("and pbli.PROGR_BANDO_LINEA_INTERVENTO ="+ searchDTO.getProgrBandoLinea()+ "\r\n");
			if(searchDTO.getImportoDaControllareA()!=null)
				sql.append("and ptcla.IMP_DA_CONTROLLARE <="+ searchDTO.getImportoDaControllareA() +"\r\n");
			if(searchDTO.getImportoDaControllareDa()!=null)
				sql.append("and ptcla.IMP_DA_CONTROLLARE >="+ searchDTO.getImportoDaControllareDa()+"\r\n");
			if(searchDTO.getImportoIrregolareA()!=null)
				sql.append("and ptcla.IMP_IRREGOLARITA <="+ searchDTO.getImportoIrregolareA()+"\r\n");
			if(searchDTO.getImportoIrregolareDa()!=null)
				sql.append("and ptcla.IMP_IRREGOLARITA >="+ searchDTO.getImportoIrregolareDa()+"\r\n");
			if(searchDTO.getTipoControllo()!=null)
				sql.append("and ptcla.TIPO_CONTROLLI='"+ searchDTO.getTipoControllo()+"'\r\n");
			if(searchDTO.getIdAutoritaControllante()>0)
				sql.append("and ptcla.ID_CATEG_ANAGRAFICA="+ searchDTO.getIdAutoritaControllante()+"\r\n");
			
			sql.append(" order by prsp.id_progetto");
			
			
			RowMapper<ControlloLocoVO> lista = new RowMapper<ControlloLocoVO>() {		
				@Override
				public ControlloLocoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ControlloLocoVO controllo = new ControlloLocoVO(); 
					controllo.setCodVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
					String denom = rs.getString("denominazione_ente");
					if(denom!=null) {
						controllo.setIdGiuPersFis(rs.getBigDecimal("ID_ENTE_GIURIDICO"));
						controllo.setDenominazione(denom);
						controllo.setPersonaGiuridica(true);
					}else {
						controllo.setDenominazione(rs.getString("denominazione_fis"));
						controllo.setIdGiuPersFis(rs.getBigDecimal("ID_PERSONA_FISICA"));
						controllo.setPersonaGiuridica(false);
					}
					controllo.setDescAttivita(rs.getString("DESC_ATTIV_CONTR_LOCO"));
					controllo.setDescBando(rs.getString("NOME_BANDO_LINEA"));
					controllo.setDescStatoControllo(rs.getString("DESC_STATO_CONTR_LOCO"));
					
					String tipoControllo = rs.getString("TIPO_CONTROLLI"); 
					
					if(tipoControllo.trim().equals("L")) {	
						controllo.setDescTipoControllo("In loco");
					} else {						
						controllo.setDescTipoControllo("Documentale"); 
					}
					
					controllo.setIdControllo(rs.getBigDecimal("ID_CONTROLLO"));
					controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
					controllo.setFlagSif(rs.getString("FLAG_SIF"));
					controllo.setDataAvvioControlli(rs.getDate("DT_AVVIO_CONTROLLI"));
					controllo.setDataFineControlli(rs.getDate("DT_FINE_CONTROLLI"));
					controllo.setDataInizioControlli(rs.getDate("DT_INIZIO_CONTROLLI"));
					controllo.setDataVisitaControllo(rs.getDate("DT_VISITA_CONTROLLO"));
					controllo.setIstruttoreVisita(rs.getString("ISTRUTTORE_VISITA"));
					controllo.setImportoAgevIrreg(rs.getBigDecimal("IMP_AGEVOLAZ_IRREG"));
					controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
					controllo.setImportoIrregolarita(rs.getBigDecimal("IMP_IRREGOLARITA"));
					controllo.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
					controllo.setIdStatoControllo(rs.getInt("ID_STATO_CONTR_LOCO"));
					controllo.setDescAutoritaControllante(rs.getString("DESC_CATEG_ANAGRAFICA"));
					controllo.setIdAutoritaControllante(rs.getInt("ID_CATEG_ANAGRAFICA"));
					controllo.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
					controllo.setIdSoggettoBenef(rs.getBigDecimal("ID_SOGGETTO"));
					controllo.setIdStatoChecklist(rs.getInt("ID_STATO_CHECKLIST"));
					return controllo;
				}
			};
			
			elenco = getJdbcTemplate().query(sql.toString(), lista);
			
		} catch (Exception e) {
			logger.error(prf+" "+ e);
		}
		
		return elenco;
	}

	@Override
	public ControlloLocoVO getAltroControllo(BigDecimal idControllo, BigDecimal idProgetto) {
		String prf = "[AreaControlliDAOImpl::getAltriControlli ]";
		logger.info(prf+"BEGIN");
		
		List<ControlloLocoVO> elenco = new ArrayList<ControlloLocoVO>(); 
		
		
		try {
			String sql = "WITH denom AS (\r\n"
					+ "    SELECT\r\n"
					+ "        concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\r\n"
					+ "        PERSFIS.ID_SOGGETTO,\r\n"
					+ "        persfis.ID_PERSONA_FISICA\r\n"
					+ "    FROM\r\n"
					+ "        PBANDI_T_PERSONA_FISICA persfis\r\n"
					+ "),\r\n"
					+ "denom2 AS (\r\n"
					+ "    SELECT\r\n"
					+ "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\r\n"
					+ "        ENTEGIU.ID_SOGGETTO,\r\n"
					+ "        entegiu.ID_ENTE_GIURIDICO\r\n"
					+ "    FROM\r\n"
					+ "        PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
					+ ")\r\n"
					+ "SELECT\r\n"
					+ "    DISTINCT ptp.CODICE_VISUALIZZATO,\r\n"
					+ "    pbli.NOME_BANDO_LINEA, pbli.ID_LINEA_DI_INTERVENTO, \r\n"
					+ "    pdscl.DESC_STATO_CONTR_LOCO,\r\n"
					+ "    pdscl.ID_STATO_CONTR_LOCO,\r\n"
					+ "    ptcla.IMP_DA_CONTROLLARE,\r\n"
					+ "    padc.DESC_ATTIV_CONTR_LOCO,\r\n"
					+ "    padc.ID_ATTIV_CONTR_LOCO,\r\n"
					+ "    denom.denominazione_fis,\r\n"
					+ "    denom.ID_PERSONA_FISICA,\r\n"
					+ "    denom2.denominazione_ente,\r\n"
					+ "    denom2.ID_ENTE_GIURIDICO,\r\n"
					+ "    prsp.ID_PROGETTO,\r\n"
					+ "    ptcla.ID_CONTROLLO,\r\n"
					+ "    pbli.FLAG_SIF,\r\n"
					+ "    prsp.ID_SOGGETTO,\r\n"
					+ "    ptcla.DT_AVVIO_CONTROLLI,\r\n"
					+ "    ptcla.DT_INIZIO_CONTROLLI,\r\n"
					+ "    ptcla.DT_FINE_CONTROLLI,\r\n"
					+ "    ptcla.IMP_IRREGOLARITA,\r\n"
					+ "    ptcla.IMP_AGEVOLAZ_IRREG,\r\n"
					+ "    ptcla.DT_VISITA_CONTROLLO,\r\n"
					+ "    ptcla.TIPO_VISITA,\r\n"
					+ "    ptcla.ISTRUTTORE_VISITA,\r\n"
					+ "    ptcla.ID_CATEG_ANAGRAFICA,\r\n"
					+ "    pdca.DESC_CATEG_ANAGRAFICA,\r\n"
					+ "    ptcla.TIPO_CONTROLLI,\r\n"
					+ "    ptcla.NUM_PROTOCOLLO, prsp.id_soggetto, pdsc.ID_STATO_CHECKLIST, pdsc.DESC_STATO_CHECKLIST\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_CONTROLLO_LOCO_ALTRI ptcla\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.id_progetto = ptcla.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
					+ "    LEFT JOIN PBANDI_T_PROGETTO ptp on ptp.id_progetto = prsp.id_progetto and prsp.DT_FINE_VALIDITA  IS NULL\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd on ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT pbli on pbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_CONTR_LOCO pdscl on ptcla.ID_STATO_CONTR_LOCO = pdscl.ID_STATO_CONTR_LOCO\r\n"
					+ "    LEFT JOIN PBANDI_D_ATTIV_CONTROLLO_LOCO padc ON padc.ID_ATTIV_CONTR_LOCO = ptcla.ID_ATTIV_CONTR_LOCO\r\n"
					+ "    LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto\r\n"
					+ "    AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
					+ "    LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto\r\n"
					+ "    AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "    LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA pdca ON pdca.ID_CATEG_ANAGRAFICA = ptcla.ID_CATEG_ANAGRAFICA"
					+ "	   left join PBANDI_D_STATO_CHECKLIST pdsc on pdsc.ID_STATO_CHECKLIST = ptcla.ID_STATO_CHECKLIST\r\n"
					+ "WHERE\r\n"
					+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    and ptcla.ID_CONTROLLO="+idControllo;
			
			RowMapper<ControlloLocoVO> lista = new RowMapper<ControlloLocoVO>() {		
				@Override
				public ControlloLocoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ControlloLocoVO controllo = new ControlloLocoVO(); 
					controllo.setCodVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
					String denom = rs.getString("denominazione_ente");
					if(denom!=null) {
						controllo.setIdGiuPersFis(rs.getBigDecimal("ID_ENTE_GIURIDICO"));
						controllo.setDenominazione(denom);
						controllo.setPersonaGiuridica(true);
					}else {
						controllo.setDenominazione(rs.getString("denominazione_fis"));
						controllo.setIdGiuPersFis(rs.getBigDecimal("ID_PERSONA_FISICA"));
						controllo.setPersonaGiuridica(false);
					}
					controllo.setDescAttivita(rs.getString("DESC_ATTIV_CONTR_LOCO"));
					controllo.setDescBando(rs.getString("NOME_BANDO_LINEA"));
					controllo.setDescStatoControllo(rs.getString("DESC_STATO_CONTR_LOCO"));
					String tipoControllo = rs.getString("TIPO_CONTROLLI"); 
					
					if(tipoControllo.trim().equals("L")) {	
						controllo.setDescTipoControllo("In loco");
					} else {						
						controllo.setDescTipoControllo("Documentale"); 
					}
					controllo.setIdAttivitaContrLoco(rs.getInt("ID_ATTIV_CONTR_LOCO"));
					controllo.setIdControllo(rs.getBigDecimal("ID_CONTROLLO"));
					controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
					controllo.setFlagSif(rs.getString("FLAG_SIF"));
					controllo.setDataAvvioControlli(rs.getDate("DT_AVVIO_CONTROLLI"));
					controllo.setDataFineControlli(rs.getDate("DT_FINE_CONTROLLI"));
					controllo.setDataInizioControlli(rs.getDate("DT_INIZIO_CONTROLLI"));
					controllo.setDataVisitaControllo(rs.getDate("DT_VISITA_CONTROLLO"));
					controllo.setIstruttoreVisita(rs.getString("ISTRUTTORE_VISITA"));
					controllo.setImportoAgevIrreg(rs.getBigDecimal("IMP_AGEVOLAZ_IRREG"));
					controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
					controllo.setImportoIrregolarita(rs.getBigDecimal("IMP_IRREGOLARITA"));
					controllo.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
					controllo.setIdStatoControllo(rs.getInt("ID_STATO_CONTR_LOCO"));
					controllo.setDescAutoritaControllante(rs.getString("DESC_CATEG_ANAGRAFICA"));
					controllo.setIdAutoritaControllante(rs.getInt("ID_CATEG_ANAGRAFICA"));
					controllo.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
					controllo.setDescTipoVisita(rs.getString("TIPO_VISITA"));
					controllo.setProgrBandoLinea(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
					controllo.setIdSoggettoBenef(rs.getBigDecimal("id_soggetto"));
					controllo.setIdStatoChecklist(rs.getInt("ID_STATO_CHECKLIST"));
					controllo.setDescStatoChecklist(rs.getString("DESC_STATO_CHECKLIST"));
					return controllo;
				}
			};
			
			elenco = getJdbcTemplate().query(sql.toString(), lista);
		} catch (Exception e) {
			logger.error(prf+" "+ e);
		}
		
		return elenco.get(0);
	}

	@Transactional
	@Override
	public Boolean updateAltroControllo(ControlloLocoVO controlloVO, BigDecimal idUtente) {	
		String prf = "[AreaControlliDAOImpl::updateAltroControllo ]";
		logger.info(prf+"BEGIN");
		
		
		
		try {
			
			
			String dataInizioControllo = null, dataFinecontrollo = null,dataVisitaControllo = null, dataAvvioControllo=null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt3 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			
			if(controlloVO.getDataInizioControlli()!=null) {
				 dataInizioControllo = dt.format(controlloVO.getDataInizioControlli());
			} 
			
			if(controlloVO.getDataFineControlli()!=null) {
				dataFinecontrollo=  dt1.format(controlloVO.getDataFineControlli());
			}
			
			if(controlloVO.getDataVisitaControllo()!=null) {
				 dataVisitaControllo = dt2.format(controlloVO.getDataVisitaControllo());
			}
			if(controlloVO.getDataAvvioControlli()!=null) {
				dataAvvioControllo = dt3.format(controlloVO.getDataAvvioControlli());
			}
						
			String update =" UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI SET\r\n"
					+ "ID_STATO_CONTR_LOCO=?, \r\n"
					+ "DT_INIZIO_CONTROLLI=?,  \r\n"
					+ "DT_FINE_CONTROLLI=?,\r\n"
					+ "IMP_DA_CONTROLLARE=?,\r\n"
					+ "IMP_IRREGOLARITA=?,\r\n"
					+ "IMP_AGEVOLAZ_IRREG=?,\r\n"
					+ "TIPO_CONTROLLI=?,\r\n"
					+ "ISTRUTTORE_VISITA=?,\r\n"
					+ "DT_VISITA_CONTROLLO=?,\r\n"
					+ "TIPO_VISITA=?,\r\n"
					+ "NUM_PROTOCOLLO=?,\r\n"
					+ " DT_AGGIORNAMENTO= TRUNC(SYSDATE), "
					+ " ID_UTENTE_AGG=? , DT_AVVIO_CONTROLLI=? WHERE ID_CONTROLLO=?";

			
			getJdbcTemplate().update(update, new Object[] {
					controlloVO.getIdStatoControllo(),
					(dataInizioControllo!=null&&dataInizioControllo.trim().length()>0)? Date.valueOf(dataInizioControllo):null, 
					(dataFinecontrollo!=null&&dataFinecontrollo.trim().length()>0)?Date.valueOf(dataFinecontrollo):null, 
					controlloVO.getImportoDaControllare(), 
					controlloVO.getImportoIrregolarita(), 
					controlloVO.getImportoAgevIrreg(), 
					controlloVO.getTipoControllo(), 
					(controlloVO.getIstruttoreVisita()!=null)?controlloVO.getIstruttoreVisita():null,
					(dataVisitaControllo!=null&&dataVisitaControllo.trim().length()>0)?Date.valueOf(dataVisitaControllo):null,
					(controlloVO.getDescTipoVisita()!=null)?controlloVO.getDescTipoVisita():null,
					controlloVO.getNumProtocollo(),
					idUtente,
					(dataAvvioControllo!=null&&dataAvvioControllo.trim().length()>0)?Date.valueOf(dataAvvioControllo):null,
					controlloVO.getIdControllo()
			});
			
			// per salvare lostato della checklist in loco a definitivo oppure in bozza
			if(controlloVO.getIdStatoChecklist()>0) {
				getJdbcTemplate().update("UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI SET ID_STATO_CHECKLIST=? where id_controllo=?", new Object[] {
						controlloVO.getIdStatoChecklist(), controlloVO.getIdControllo()
				});
			}
	
			
		} catch (Exception e) {
			logger.error(prf+" "+ e);
			return false; 
		}
		
		return true;
	}
	
//	public Boolean callIter002AndUpdateTable(Long idProgetto, Long idTarget, Boolean isAltriControlli, Long idUtente) {
//		String prf = "[AreaControlliDAOImpl::avviaIterEsitoPositivoControllo]";
//		logger.info(prf+" BEGIN");
//
//		//UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
//
//		boolean result;
//
//		try {
//			Long idEntita;
//			if(isAltriControlli){
//				idEntita = getJdbcTemplate().queryForObject("SELECT pce.ID_ENTITA FROM PBANDI_C_ENTITA pce WHERE pce.NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO_ALTRI'\n", Long.class);
//			}else{
//				idEntita = getJdbcTemplate().queryForObject("SELECT pce.ID_ENTITA FROM PBANDI_C_ENTITA pce WHERE pce.NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO'\n", Long.class);
//			}
//
//			String errore = iterAutoDao.avviaIterAutorizzativo(7L, idEntita, idTarget, idProgetto,idUtente);
//
//			if(errore.equals("")){
//				result = true;
//			}else{
//				throw new Exception(errore);
//			}
//
//			//Aggiorno attività controllo
//			if(isAltriControlli){
//				getJdbcTemplate().update(
//						"UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI SET \n" +
//								"ID_ATTIV_CONTR_LOCO = 7,\n" +
//								"ID_UTENTE_AGG = ?,\n" +
//								"DT_AGGIORNAMENTO = CURRENT_DATE\n" +
//								"WHERE ID_CONTROLLO = ?",idUtente, idTarget);
//			}else{
//				getJdbcTemplate().update(
//						"UPDATE PBANDI_T_CONTROLLO_LOCO SET \n" +
//								"ID_ATTIV_CONTR_LOCO = 7,\n" +
//								"ID_UTENTE_AGG = ?,\n" +
//								"DT_AGGIORNAMENTO = CURRENT_DATE\n" +
//								"WHERE ID_CONTROLLO_LOCO = ?", idUtente, idTarget);
//			}
//		}catch (Exception e){
//			result = false;
//			logger.error("Exception while trying to avviaIterEsitoPositivoControllo: " + e);
//		}finally {
//			logger.info(prf+" END");
//		}
//
//		return result;
//	}

	@Override
	public RichiestaIntegrazioneControlloLocoVO checkRichiestaIntegrAltroControllo(BigDecimal idControllo) {
		String prf = "[AreaControlliDAOImpl::checkRichiestaIntegrAltraControllo ]";
		logger.info(prf+"BEGIN");
		RichiestaIntegrazioneControlloLocoVO rich = new RichiestaIntegrazioneControlloLocoVO();
		try {
			
		Long idRichiesta; 
		
		idRichiesta = controllaRichiesta (idControllo, "608"); 
		if(idRichiesta<0) {
			return null;
		} else {
			// recupero i dati della prorooga  
			String sqlProroga ="SELECT\r\n"
					+ "	*\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_RICHIESTA_INTEGRAZ ptri\r\n"
					+ "	JOIN PBANDI_D_STATO_RICH_INTEGRAZ pdsri ON pdsri.ID_STATO_RICHIESTA = ptri.ID_STATO_RICHIESTA \r\n"
					+ "	WHERE ptri.ID_RICHIESTA_INTEGRAZ ="+ idRichiesta; 	
			rich= getJdbcTemplate().query(sqlProroga,  new ResultSetExtractor<RichiestaIntegrazioneControlloLocoVO>() {

				@Override
				public RichiestaIntegrazioneControlloLocoVO extractData(ResultSet rs)
						throws SQLException, DataAccessException {
					RichiestaIntegrazioneControlloLocoVO rich = new RichiestaIntegrazioneControlloLocoVO();
					
					while(rs.next()){
						rich.setDataNotifica(rs.getDate("DT_NOTIFICA"));
						rich.setDataRichiesta(rs.getDate("DT_RICHIESTA"));
						rich.setNumGiorniScadenza(rs.getInt("NUM_GIORNI_SCADENZA"));
						rich.setDescStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));
						rich.setIdStatoRichiesta(rs.getLong("ID_STATO_RICHIESTA"));
						rich.setDataScadenza(rs.getDate("DT_SCADENZA"));
						rich.setIdRichiestaIntegr(idRichiesta);
						//rich.setMotivazione(rs.getString(""));
					}
					return rich;
				}
			});
			
			
		}
						
		} catch (Exception e) {
			logger.error("errore lettura tabella richiesta integrazione "+e);
			return null;
		}
		return rich;
	}

	@Override
	public List<DocumentoIndexVO> getElencoFile(BigDecimal idControllo, BigDecimal idEntita) {
		String prf = "[AreaControlliDAOImpl::getElencoFile ]";
		logger.info(prf+"BEGIN");
		
		List<DocumentoIndexVO> documenti = new ArrayList<DocumentoIndexVO>(); 
		try {
			
			String sql = "SELECT * FROM PBANDI_T_DOCUMENTO_INDEX \r\n"
					+ "WHERE \r\n"
					+ "ID_ENTITA ="+idEntita+"\r\n"
					+ "AND id_target="+idControllo+"\r\n";
			
			RowMapper<DocumentoIndexVO> rowMapper  = new RowMapper<DocumentoIndexVO>() {
				
				@Override
				public DocumentoIndexVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					DocumentoIndexVO doc = new DocumentoIndexVO();
					
					doc.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
					doc.setIdTarget(rs.getBigDecimal("ID_TARGET"));
					doc.setIdTipoDocumentoIndex(rs.getBigDecimal("ID_TIPO_DOCUMENTO_INDEX"));
					doc.setIdEntita(rs.getBigDecimal("ID_ENTITA"));
					doc.setNomeFile(rs.getString("NOME_FILE"));
					doc.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
					doc.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
					doc.setIdStatoDocumento(rs.getBigDecimal("ID_STATO_DOCUMENTO"));
		
					/*UUID_NODO,REPOSITORY,DT_INSERIMENTO_INDEX,ID_UTENTE_INS
					ID_UTENTE_AGG,NOTE_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,VERSIONE,ID_MODELLO,NUM_PROTOCOLLO,FLAG_FIRMA_CARTACEA
					MESSAGE_DIGEST,DT_VERIFICA_FIRMA,DT_MARCA_TEMPORALE,ID_SOGG_RAPPR_LEGALE,ID_SOGG_DELEGATO,FLAG_TRASM_DEST,ID_CATEG_ANAGRAFICA_MITT
					NOME_DOCUMENTO_FIRMATO,NOME_DOCUMENTO_MARCATO,ACTA_INDICE_CLASSIFICAZ_ESTESO,FLAG_FIRMA_AUTOGRAFA,ID_DOMANDA,	ID_SOGG_BENEFICIARIO */
					
					return doc;
				}
			};
			
			documenti = getJdbcTemplate().query(sql, rowMapper); 
			
		} catch (Exception e) {
			logger.error("errore lettura tabella documento index "+e);
		}
		return documenti;
	}

	@Override
	public List<DocumentoIndexVO> getElencoFileBeneficario(BigDecimal idTarget, BigDecimal idEntita) {
		String prf = "[AreaControlliDAOImpl::getElencoFile ]";
		logger.info(prf+"BEGIN");
		
		List<DocumentoIndexVO> documenti = new ArrayList<DocumentoIndexVO>(); 
		try {
			
			String sql = "    SELECT\r\n"
					+ "    PTDI.ID_DOCUMENTO_INDEX,\r\n"
					+ "    ptfe.ID_TARGET,\r\n"
					+ "    ptdi.ID_TIPO_DOCUMENTO_INDEX,\r\n"
					+ "    ptfe.ID_ENTITA,\r\n"
					+ "    ptdi.NOME_FILE,\r\n"
					+ "    ptdi.NOME_DOCUMENTO,\r\n"
					+ "    ptdi.ID_PROGETTO,\r\n"
					+ "    ptdi.ID_STATO_DOCUMENTO,\r\n"
					+ "    ptfe.FLAG_ENTITA, ptdi.DT_INSERIMENTO_INDEX \r\n"
					+ "FROM\r\n"
					+ "    pbandi_t_file_entita ptfe\r\n"
					+ "    LEFT JOIN Pbandi_t_file ptf ON ptf.ID_FILE = ptfe.ID_FILE\r\n"
					+ "    LEFT JOIN pbandi_t_documento_index ptdi ON ptdi.ID_DOCUMENTO_INDEX = ptf.ID_DOCUMENTO_INDEX\r\n"
					+ "    LEFT JOIN pbandi_c_tipo_documento_index pctdi ON pctdi.ID_TIPO_DOCUMENTO_INDEX = ptdi.ID_TIPO_DOCUMENTO_INDEX\r\n"
					+ "WHERE\r\n"
					+ "    ptfe.ID_TARGET = ?\r\n"
					+ "    AND ptfe.ID_ENTITA = ?";
			
			RowMapper<DocumentoIndexVO> rowMapper  = new RowMapper<DocumentoIndexVO>() {
				
				@Override
				public DocumentoIndexVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					DocumentoIndexVO doc = new DocumentoIndexVO();
					
					doc.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
					doc.setIdTarget(rs.getBigDecimal("ID_TARGET"));
					doc.setIdTipoDocumentoIndex(rs.getBigDecimal("ID_TIPO_DOCUMENTO_INDEX"));
					doc.setIdEntita(rs.getBigDecimal("ID_ENTITA"));
					doc.setNomeFile(rs.getString("NOME_FILE"));
					doc.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
					doc.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
					doc.setIdStatoDocumento(rs.getBigDecimal("ID_STATO_DOCUMENTO"));
					doc.setDtInserimentoIndex(rs.getDate("DT_INSERIMENTO_INDEX"));
					doc.setFlagFirmaCartacea(rs.getString("FLAG_ENTITA"));
//					doc.setfl
		
					/*UUID_NODO,REPOSITORY,DT_INSERIMENTO_INDEX,ID_UTENTE_INS
					ID_UTENTE_AGG,NOTE_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,VERSIONE,ID_MODELLO,NUM_PROTOCOLLO,FLAG_FIRMA_CARTACEA
					MESSAGE_DIGEST,DT_VERIFICA_FIRMA,DT_MARCA_TEMPORALE,ID_SOGG_RAPPR_LEGALE,ID_SOGG_DELEGATO,FLAG_TRASM_DEST,ID_CATEG_ANAGRAFICA_MITT
					NOME_DOCUMENTO_FIRMATO,NOME_DOCUMENTO_MARCATO,ACTA_INDICE_CLASSIFICAZ_ESTESO,FLAG_FIRMA_AUTOGRAFA,ID_DOMANDA,	ID_SOGG_BENEFICIARIO */
					
					return doc;
				}
			};
			
			documenti = getJdbcTemplate().query(sql, rowMapper, new Object[] {idTarget, idEntita}); 
			
		} catch (Exception e) {
			logger.error("errore lettura tabella documento index "+e);
		}
		return documenti;
	}

	@Override
	public Boolean avviaIterEsitoPositivoControllo(Long idProgetto, Long idTarget, Boolean isAltriControlli, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::avviaIterEsitoPositivoControllo]";
		logger.info(prf+" BEGIN");

		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		boolean result;

		try {
			Long idEntita;
			if(isAltriControlli){
				idEntita = getJdbcTemplate().queryForObject("SELECT pce.ID_ENTITA FROM PBANDI_C_ENTITA pce WHERE pce.NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO_ALTRI'\n", Long.class);
			}else{
				idEntita = getJdbcTemplate().queryForObject("SELECT pce.ID_ENTITA FROM PBANDI_C_ENTITA pce WHERE pce.NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO'\n", Long.class);
			}

			String errore = iterAutoDao.avviaIterAutorizzativo(7L, idEntita, idTarget, idProgetto, userInfoSec.getIdUtente());

			if(errore.equals("")){
				result = true;
			}else{
				throw new Exception(errore);
			}

			//Aggiorno attività controllo
			if(isAltriControlli){
				getJdbcTemplate().update(
						"UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI SET \n" +
								"ID_ATTIV_CONTR_LOCO = 7,\n" +
								"ID_UTENTE_AGG = ?,\n" +
								"DT_AGGIORNAMENTO = CURRENT_DATE\n" +
								"WHERE ID_CONTROLLO = ?", userInfoSec.getIdUtente(), idTarget);
			}else{
				getJdbcTemplate().update(
						"UPDATE PBANDI_T_CONTROLLO_LOCO SET \n" +
								"ID_ATTIV_CONTR_LOCO = 7,\n" +
								"ID_UTENTE_AGG = ?,\n" +
								"DT_AGGIORNAMENTO = CURRENT_DATE\n" +
								"WHERE ID_CONTROLLO_LOCO = ?", userInfoSec.getIdUtente(), idTarget);
			}
		}catch (Exception e){
			result = false;
			logger.error("Exception while trying to avviaIterEsitoPositivoControllo: " + e);
		}finally {
			logger.info(prf+" END");
		}

		return result;
	}

	@Override
	public Object salvaAllegati(MultipartFormDataInput multipartFormData, HttpServletRequest req) {
		String prf = "[AreaControlliDAOImpl::salvaAllegati]";
		logger.info(prf+" BEGIN");
	//  lettera avvio controllo in locco :  id_tipo_documento_index =56
			//  lettera accompagnatoria controllo in locco : id_tipo... =57 
			// entita =1 corrisponde alla tabella PBANDI_T_CONTROLLO_LOCO
			// entita =2 corrisponde alla tabella PBANDI_T_RICHIESTA_INTEGRAZ
			// entita =3 corrisponde alla tabella PBANDI_T_CONTROLLO_LOCO_ALTRI
			Boolean result = null; 
			UserInfoSec user =   (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			
			try {
				int entita; 
				int idStatoControllo =  multipartFormData.getFormDataPart("idStatoControllo", Integer.class,null); 
				boolean isControlloFinp =  multipartFormData.getFormDataPart("isControlloFinp", Boolean.class,null); 
				if(isControlloFinp) {
					entita= 1; 
				} else {
					entita=3; 
				}
				
				
				Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
				List<InputPart> fileVerbaleControllo = (List<InputPart>) map.get("fileVerbaleControllo"); 
				List<InputPart> fileCheckListControllo = (List<InputPart>) map.get("fileCheckListControllo"); 
				
				byte[] fileVerbale = IOUtils.toByteArray((fileVerbaleControllo.get(0)).getBody(InputStream.class, null));
				byte[] fileChecklist = IOUtils.toByteArray((fileCheckListControllo.get(0)).getBody(InputStream.class, null));
				
				Long idUtente = user.getIdUtente(); 
				String nomeFileVerbaleControllo = getFileName(fileVerbaleControllo.get(0).getHeaders());
				String nomeFileCheckListControllo = getFileName(fileCheckListControllo.get(0).getHeaders());
				BigDecimal idControllo = multipartFormData.getFormDataPart("idControllo", BigDecimal.class, null); 
				BigDecimal idProgetto = multipartFormData.getFormDataPart("idProgetto", BigDecimal.class, null);
				switch (idStatoControllo) {
				case 5: 
				case 4:
					// devo salvare gli allegati per il controllo in loco	
					BigDecimal idRevoca = multipartFormData.getFormDataPart("idRevoca", BigDecimal.class, null); 
					//  controllo in loco per verbale di controllo 
					BigDecimal idTipoDocumentoIndex= new BigDecimal(7); 
					Boolean esito1 = salvaAllegatiFile(fileVerbale, nomeFileVerbaleControllo, idUtente, idControllo, idTipoDocumentoIndex, entita, idProgetto);
					if(esito1==false) {
						logger.error("errore salvataggio verbale di controllo ");
						return false;
					}
					// per la revoca... per la reca entita=4
					boolean esito2 = salvaAllegatiFile(fileVerbale, nomeFileVerbaleControllo, idUtente, idRevoca, idTipoDocumentoIndex, 4, idProgetto);
					if(esito2==false) {
						logger.error("errore salvataggio verbale di controllo ");
						return false;
					}
					// salvataggio checklist di controllo in loco 
					idTipoDocumentoIndex= new BigDecimal(58); 
					boolean esito3 = salvaAllegatiFile(fileChecklist, nomeFileCheckListControllo, idUtente, idControllo, idTipoDocumentoIndex, entita, idProgetto);
					if(esito3==false) {
						logger.error("errore salvataggio salvataggio file checklist di controllo in loco ");
						return false;
					}
					// per la revoca... per la reca entita=4
					boolean esito4 = salvaAllegatiFile(fileChecklist, nomeFileCheckListControllo, idUtente, idRevoca, idTipoDocumentoIndex, 4, idProgetto);
					if(esito4==false) {
						logger.error("errore salvataggio salvataggio file checklist di controllo in loco ");
						return false;
					}
					break;
					
				case 2:
					List<InputPart> fileLetteraEsitoControllo = (List<InputPart>) map.get("fileLetteraEsitoControllo"); 
					byte[] fileLetteraEsito = IOUtils.toByteArray((fileLetteraEsitoControllo.get(0)).getBody(InputStream.class, null));
					
					String nomeFileLetteraEsitoControllo = getFileName(fileLetteraEsitoControllo.get(0).getHeaders());
					BigDecimal idTipoDocumentoIndex2= new BigDecimal(7); 
					Boolean esito5 = salvaAllegatiFile(fileVerbale, nomeFileVerbaleControllo, idUtente, idControllo, idTipoDocumentoIndex2, entita, idProgetto);
					if(esito5==false) {
						logger.error("errore salvataggio file verbale di controllo ");
						return false;
					}
					
					// salvataggio file checklist di controllo in loco 
					idTipoDocumentoIndex2= new BigDecimal(58); 
					boolean esito6 = salvaAllegatiFile(fileChecklist, nomeFileCheckListControllo, idUtente, idControllo, idTipoDocumentoIndex2, entita, idProgetto);
					if(esito6==false) {
						logger.error("errore salvataggio file checklist di controllo in loco ");
						return false;
					}
					
					// salvataggio file lettera di esito di controllo in loco 
					idTipoDocumentoIndex2= new BigDecimal(57); 
					boolean esito7 = salvaAllegatiFile(fileLetteraEsito, nomeFileLetteraEsitoControllo, idUtente, idControllo, idTipoDocumentoIndex2, entita, idProgetto);
					if(esito7==false) {
						logger.error("errore salvataggio file lettera di esito di controllo in loco ");
						return false;
					}
					break; 
					

				default:
					break;
				}
				
				
			}catch (Exception e) {
				logger.error("errore salvataggio dei files");
				return false;
			}
		
		return true;
	}
	//UTILS
	public static String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith("filename")) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}
	
     private Boolean salvaAllegatiFile(byte[] file,String nomeFile, Long idUtente, BigDecimal idTarget, BigDecimal idTipoDocumentoIndex,int entita, BigDecimal idProgetto ){
//	private Boolean salvaAllegatoFrom(MultipartFormDataInput multipartFormData) {
		String prf = "[AreaControlliDAOImpl:: salva Allegato]";
		logger.info(prf+"BEGIN");
		
		//  lettera avvio controllo in locco :  id_tipo_documento_index =56
		//  lettera accompagnatoria controllo in locco : id_tipo... =57 
		// entita =1 corrisponde alla tabella PBANDI_T_CONTROLLO_LOCO
		// entita =2 corrisponde alla tabella PBANDI_T_RICHIESTA_INTEGRAZ
		// entita =3 corrisponde alla tabella PBANDI_T_CONTROLLO_LOCO_ALTRI
		Boolean result = null; 
		try {
			String getEntita=null ;
			if(entita==1) {				
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_CONTROLLO_LOCO'\r\n";
			} 
			if(entita==2) {
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_RICHIESTA_INTEGRAZ'\r\n";
			}
			if(entita==3) {
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_CONTROLLO_LOCO_ALTRI'\r\n";
			}
			if(entita==4) {
				getEntita="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_GESTIONE_REVOCA'\r\n";
			}
			
			BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
					
			// Legge il file firmato dal multipart.		
//			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
//			List<InputPart> listInputPart = map.get("file");
//			
//			if (listInputPart == null) {
//				logger.info("listInputPart NULLO");
//			} else {
//				logger.info("listInputPart SIZE = "+listInputPart.size());
//			}
//			
//			for (InputPart i : listInputPart) {
//				MultivaluedMap<String, String> m = i.getHeaders();
//				Set<String> s = m.keySet();
//				for (String x : s) {
//					logger.info("SET = "+x);
//				}
//			}
			
//			FileDTO file = new FileDTO(); 
//			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX \r\n"
					+ " where ID_TIPO_DOCUMENTO_INDEX="+idTipoDocumentoIndex, String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(numEntita);
			documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
			//PBANDI_T_PROGETTO
			documentoIndexVO.setIdProgetto(idProgetto);			//id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file, documentoIndexVO, null);
			
			
		} catch (Exception e) {
			logger.error("errore inserimento pbandi_t_documento_index "+e);
			result= false; 
		}
		return result;
	}

}
