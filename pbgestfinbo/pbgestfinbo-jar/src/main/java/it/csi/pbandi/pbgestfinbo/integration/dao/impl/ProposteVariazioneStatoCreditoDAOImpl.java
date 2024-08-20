/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoPropostaVarazioneStatoCreditoDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.ProposteVariazioneStatoCreditoDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaBeneficiariCreditiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoPropostaVarazioneStatoCreditoDTORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.CercaPropostaVarazioneStatoCreditoSearchVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SalvaVariazioneStatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaveSchedaClienteAllVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.StatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class ProposteVariazioneStatoCreditoDAOImpl extends JdbcDaoSupport implements ProposteVariazioneStatoCreditoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public ProposteVariazioneStatoCreditoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Autowired
	public RicercaBeneficiariCreditiDAO monitoraggioCreditiDAO;

	@Override
	public List<AttivitaDTO> getSugggest(int id, String stringa) {

//		cf: 1
//		partitaIVA:2
//		denom:3
//		bando:4
//		codicePRo:5

		List<AttivitaDTO> result = new ArrayList<AttivitaDTO>();

		switch (id) {
		case 1:
			result = listaCodiciFiscali(stringa);

			break;
		case 2:
			result = listaPartitaIVA(stringa);

			break;
		case 3:
			result = listaDenomiazioni(stringa);

			break;
		case 4:
			result = listaBando(stringa);

			break;
		case 5:
			result = listaCodiceProgetto(stringa);
			
		case 9:
			result = listaCodiceFondoFinpis(stringa);

		default:
			break;
		}

		return result;
	}

	private List<AttivitaDTO> listaCodiciFiscali(String string) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaCodiciFiscali ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaCF = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT distinct pts.ID_SOGGETTO, pts.CODICE_FISCALE_SOGGETTO \r\n"
					+ " FROM PBANDI_T_SOGGETTO pts, PBANDI_R_SOGGETTO_PROGETTO  prsp \r\n"
					+ " Where pts.ID_SOGGETTO = prsp.ID_SOGGETTO \r\n" + " AND prsp.id_tipo_anagrafica = 1"
					+ "  AND id_tipo_beneficiario<>4" + " AND  UPPER(pts.CODICE_FISCALE_SOGGETTO) LIKE UPPER('%"
					+ string + "%')\r\n" + "AND  ROWNUM <= 100";

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO cf = new AttivitaDTO();
					cf.setDescAttivita(rs.getString("CODICE_FISCALE_SOGGETTO"));
					cf.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					return cf;
				}
			};

			listaCF = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_T_SOGGETTO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaCF;
	}

	private List<AttivitaDTO> listaPartitaIVA(String string) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaPartitaIVA ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaPartitaIVA = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT distinct Partita_iva  FROM PBANDI_T_SEDE "
					+ " where  UPPER(Partita_iva) LIKE UPPER('%" + string + "%')\r\n" + " AND  ROWNUM <= 100";
			;

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO cf = new AttivitaDTO();
					cf.setDescAttivita(rs.getString("Partita_iva"));
					return cf;
				}
			};

			listaPartitaIVA = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaPartitaIVA;
	}

	public List<AttivitaDTO> listaDenomiazioni(String string) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaDenomiazioni ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaDenominazioni = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT persfis.COGNOME AS desc_1 ,persfis.NOME AS desc_2, PERSFIS .ID_SOGGETTO \r\n"
					+ "	FROM\r\n" + "	PBANDI_T_PERSONA_FISICA persfis\r\n"
					+ " where  UPPER(persfis.nome) LIKE UPPER('%" + string + "%')\r\n"
					+ " OR  UPPER(persfis.cognome) LIKE UPPER('%" + string + "%')\r\n" + " AND  ROWNUM <= 100\r\n"
					+ "UNION \r\n"
					+ "SELECT entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS desc_1, '' AS desc_2, ENTEGIU .ID_SOGGETTO \r\n"
					+ "FROM PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
					+ " where  UPPER(entegiu.DENOMINAZIONE_ENTE_GIURIDICO) LIKE UPPER('%" + string + "%')\r\n"
					+ "AND  ROWNUM <= 100";

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO denom = new AttivitaDTO();
					denom.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					denom.setCognome(rs.getString("desc_1"));
					denom.setNome(rs.getString("desc_2"));
					if (denom.getNome() == null)
						denom.setDescAttivita(denom.getCognome());
					else
						denom.setDescAttivita(denom.getCognome() + " " + denom.getNome());
					return denom;
				}
			};

			listaDenominazioni = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_T_ENTE_GIURIDICO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaDenominazioni;
	}

	@Override
	public List<AttivitaDTO> listaTipoAgevolazione() {

		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaAgevolazione ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaAgev = new ArrayList<AttivitaDTO>();

		try {

			String query = " SELECT ID_MODALITA_AGEVOLAZIONE, DESC_MODALITA_AGEVOLAZIONE FROM PBANDI_D_MODALITA_AGEVOLAZIONE "
					+ "	where ID_MODALITA_AGEVOLAZIONE  IN (1,5,10)";
			
			AttivitaDTO agev = new AttivitaDTO(); 
			agev.setIdAttivita((long)1);
			agev.setDescAttivita("Contributo");
			listaAgev.add(agev);
			AttivitaDTO agev1 = new AttivitaDTO(); 
			agev1.setIdAttivita((long)5);
			agev1.setDescAttivita("Finanziamento");
			listaAgev.add(agev1);
			AttivitaDTO agev2 = new AttivitaDTO(); 
			agev2.setIdAttivita((long)10);
			agev2.setDescAttivita("Garanzia");
			listaAgev.add(agev2);
			
//			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {
//
//				@Override
//				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//					AttivitaDTO agev = new AttivitaDTO();
//					agev.setDescAttivita(rs.getString("DESC_MODALITA_AGEVOLAZIONE"));
//					agev.setIdAttivita(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
//					return agev;
//				}
//			};
//
//			listaAgev = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_D_MODALITA_AGEVOLAZIONE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaAgev;
	}

	public List<AttivitaDTO> listaBando(String string) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaBando ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaBandi = new ArrayList<AttivitaDTO>();

		try {

			String sql = "  SELECT DISTINCT  ptb.ID_BANDO, ptb.TITOLO_BANDO \r\n"
					+ "	FROM PBANDI_T_BANDO ptb \r\n"
					+ "			Where  UPPER(ptb.TITOLO_BANDO) LIKE UPPER('%" + string + "%')"
					+ " 	    AND  ROWNUM <= 100 "
					+ "		order by ptb.titolo_bando";

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO bando = new AttivitaDTO();
					bando.setDescAttivita(rs.getString("titolo_bando"));
					bando.setIdAttivita(rs.getLong("id_bando"));
					return bando;
				}
			};

			listaBandi = getJdbcTemplate().query(sql, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_T_BANDO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaBandi;
	}

	public List<AttivitaDTO> listaCodiceFondoFinpis(String string) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaCodiceProgetto ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaCodiceProgetto = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT DISTINCT  prsp.id_soggetto , ptp.ID_PROGETTO, ptp.CODICE_FONDO_FINPIS\r\n"
					+ "FROM PBANDI_T_PROGETTO ptp, PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "WHERE prsp .ID_PROGETTO = ptp.ID_PROGETTO\r\n"
					+ "AND UPPER(ptp.CODICE_FONDO_FINPIS) LIKE UPPER('%" + string + "%')\r\n"
					+ "AND prsp.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_BENEFICIARIO <>4\r\n"
					+ "AND  ROWNUM <= 100\r\n"
					+ "ORDER BY ptp.CODICE_FONDO_FINPIS ";

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO codProg = new AttivitaDTO();
					codProg.setDescAttivita(rs.getString("CODICE_FONDO_FINPIS"));
					codProg.setIdAttivita(rs.getLong("ID_PROGETTO"));
					codProg.setIdSoggetto(rs.getLong("id_soggetto"));
					return codProg;
				}
			};

			listaCodiceProgetto = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_PROP_VAR_CRE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaCodiceProgetto;
	}
	
	public List<AttivitaDTO> listaCodiceProgetto(String string) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaCodiceProgetto ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaCodiceProgetto = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT DISTINCT  prsp .id_soggetto , ptp.ID_PROGETTO, ptp.CODICE_VISUALIZZATO \r\n"
					+ " FROM PBANDI_T_PROGETTO ptp, PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
					+ " WHERE prsp .ID_PROGETTO = ptp.ID_PROGETTO \r\n"
					+ "	AND  UPPER(ptp.CODICE_VISUALIZZATO) LIKE UPPER('%" + string + "%')"
					+ " AND prsp.ID_TIPO_ANAGRAFICA =1\r\n" + " AND prsp.ID_TIPO_BENEFICIARIO <>4\r\n"
					+ " AND  ROWNUM <= 100\r\n" + " ORDER BY ptp.CODICE_VISUALIZZATO";

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

			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_PROP_VAR_CRE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaCodiceProgetto;
	}

	@Override
	public List<AttivitaDTO> listaStatoProposta() {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaStatoProposta ]";
		LOG.info(prf + "BEGIN");

		List<AttivitaDTO> listaStatoProposta = new ArrayList<AttivitaDTO>();

		try {

			String query = "SELECT DISTINCT  PDSPVC .id_stato_prop_var_cre, PDSPVC .desc_stato_prop_var_cre \r\n"
					+ "FROM PBANDI_D_STATO_PROP_VAR_CRE pdspvc\r\n" + "ORDER BY DESC_STATO_PROP_VAR_CRE \r\n";

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO cf = new AttivitaDTO();
					cf.setDescAttivita(rs.getString("desc_stato_prop_var_cre"));
					cf.setIdAttivita(rs.getLong("id_stato_prop_var_cre"));
					return cf;
				}
			};

			listaStatoProposta = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_PROP_VAR_CRE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaStatoProposta;
	}

	@Override
	public List<StoricoPropostaVarazioneStatoCreditoDTO> getElencoProposte(CercaPropostaVarazioneStatoCreditoSearchVO statoCreditoSearchDTO) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::listaStatoProposta ]";
		LOG.info(prf + "BEGIN");

		List<StoricoPropostaVarazioneStatoCreditoDTO> storico = new ArrayList<StoricoPropostaVarazioneStatoCreditoDTO>();
		
		StringBuilder sp = new StringBuilder();
		if (statoCreditoSearchDTO.getCodiceFiscale() != null) {
			sp.append("AND prsp.id_soggetto=" + statoCreditoSearchDTO.getIdSoggettoCF() +"\r\n");
		} 
		if (statoCreditoSearchDTO.getPartitaIVA() != null) {
			sp.append("and pti.iva ='"+statoCreditoSearchDTO.getPartitaIVA()+"'\r\n");
		} 
		if (statoCreditoSearchDTO.getDenominazione() != null) {		
			if(statoCreditoSearchDTO.getIdSoggettoDenom()!=null) {
				sp.append("AND prsp.id_soggetto=" + statoCreditoSearchDTO.getIdSoggettoDenom() +"\r\n");
			} else {				
				sp.append("AND UPPER(CONCAT(CONCAT(ds.descr1,' '),ds.descr2))= UPPER ('"+ statoCreditoSearchDTO.getDenominazione() +"')\r\n");
			}
		} 
		if (statoCreditoSearchDTO.getIdAgevolazione() != null) {
			sp.append("and pdma.ID_MODALITA_AGEVOLAZIONE_rif =" + statoCreditoSearchDTO.getIdAgevolazione() +"\r\n"); 
		} 
		if (statoCreditoSearchDTO.getTitoloBando()!= null) {
			sp.append("and ptb.titolo_bando='"+ statoCreditoSearchDTO.getTitoloBando() +"'\r\n"); 
		} 
		if (statoCreditoSearchDTO.getCodiceProgetto() != null) {			
			sp.append("and PTP .CODICE_VISUALIZZATO='" + statoCreditoSearchDTO.getCodiceProgetto() +"'\r\n");		
		} 
		if (statoCreditoSearchDTO.getIdStatoProposta() != null) {
			sp.append("AND PDSPVC.ID_STATO_PROP_VAR_CRE = " + statoCreditoSearchDTO.getIdStatoProposta()  +"\r\n");
			if(statoCreditoSearchDTO.getIdStatoProposta() ==3) {
				sp.append("	--AND PRSPSCF.DT_FINE_VALIDITA IS NOT NULL \r\n"); 
				sp.append("	AND ptvsc.DT_FINE_VALIDITA IS NOT NULL \r\n"); 
			} else {
				sp.append("	AND PRSPSCF.DT_FINE_VALIDITA IS  NULL \r\n"); 
				sp.append("	AND ptvsc.DT_FINE_VALIDITA IS NULL \r\n"); 
			}
		}  else {
			sp.append("	AND PRSPSCF.DT_FINE_VALIDITA IS  NULL \r\n"); 
			sp.append("	AND ptvsc.DT_FINE_VALIDITA IS NULL \r\n"); 
			sp.append(" AND PDSPVC.ID_STATO_PROP_VAR_CRE in (1,2) \r\n"); 
		}
		if(statoCreditoSearchDTO.getIdStatoAttuale()!=null) {
			sp.append("AND PDSCF.ID_STATO_CREDITO_FP = " + statoCreditoSearchDTO.getIdStatoAttuale()  +"\r\n");
		}
		if(statoCreditoSearchDTO.getIdStatoCreditoProposto()!=null) {
			sp.append("AND PDSCF2.ID_STATO_CREDITO_FP = " + statoCreditoSearchDTO.getIdStatoCreditoProposto()  +"\r\n");
		}
		if(statoCreditoSearchDTO.getPercSconfinamentoDa()!=null) {
			sp.append("AND ptvsc.perc_sconf >= " + statoCreditoSearchDTO.getPercSconfinamentoDa()  +"\r\n");
		}
		if(statoCreditoSearchDTO.getPercSconfinamentoA()!=null) {
			sp.append("AND ptvsc.perc_sconf <= " + statoCreditoSearchDTO.getPercSconfinamentoA()  +"\r\n");
		}
		// New - ricerca per codice fondo Finpis
		if(statoCreditoSearchDTO.getCodiceFinpis()!=null) {
			sp.append("	AND PTP.CODICE_FONDO_FINPIS = '" + statoCreditoSearchDTO.getCodiceFinpis()  +"'\r\n");
		}
		
		storico = cercaConParametri( sp.toString());

		return storico;
	}

	
	
	private List<StoricoPropostaVarazioneStatoCreditoDTO> cercaConParametri(String query2part) {

		StringBuilder query = new StringBuilder();

		List<StoricoPropostaVarazioneStatoCreditoDTO> lista = new ArrayList<StoricoPropostaVarazioneStatoCreditoDTO>();
		try {

			query.append("WITH dati_soggetto AS (\r\n"
					+ "	SELECT DISTINCT a.id_soggetto,\r\n"
					+ "		a.id_tipo_anagrafica,\r\n"
					+ "		a.id_progetto,\r\n"
					+ "		a.id_ente_giuridico ,\r\n"
					+ "		A.ID_PERSONA_FISICA ,\r\n"
					+ "		b.denominazione_ente_giuridico AS descr1,\r\n"
					+ "		'' AS descr2\r\n"
					+ "	FROM PBANDI_R_SOGGETTO_PROGETTO A,\r\n"
					+ "		PBANDI_T_ENTE_GIURIDICO B,\r\n"
					+ "		PBANDI_T_VARIAZ_ST_CREDITO ptvsc,\r\n"
					+ "		PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf\r\n"
					+ "	WHERE a.id_ente_giuridico = b.id_ente_giuridico\r\n"
					+ "		AND prspscf.PROGR_SOGGETTO_PROGETTO = a.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "		AND prspscf.ID_SOGG_PROG_STATO_CREDITO_FP = ptvsc.ID_SOGG_PROG_STATO_CREDITO_FP\r\n"
					+ "	UNION\r\n"
					+ "	SELECT a.id_soggetto,\r\n"
					+ "		a.id_tipo_anagrafica,\r\n"
					+ "		a.id_progetto,\r\n"
					+ "		A.ID_ENTE_GIURIDICO ,\r\n"
					+ "		b.id_persona_fisica ,\r\n"
					+ "		b.cognome AS descr1,\r\n"
					+ "		b.nome AS descr2\r\n"
					+ "	FROM PBANDI_R_SOGGETTO_PROGETTO A,\r\n"
					+ "		PBANDI_T_PERSONA_FISICA B,\r\n"
					+ "		PBANDI_T_VARIAZ_ST_CREDITO ptvsc,\r\n"
					+ "		PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf\r\n"
					+ "	WHERE a.id_persona_fisica = b.id_persona_fisica\r\n"
					+ "		AND prspscf.PROGR_SOGGETTO_PROGETTO = a.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "		AND prspscf.ID_SOGG_PROG_STATO_CREDITO_FP = ptvsc.ID_SOGG_PROG_STATO_CREDITO_FP\r\n"
					+ "),\r\n"
					+ "partita_iva AS (\r\n"
					+ "	SELECT DISTINCT a.id_soggetto,\r\n"
					+ "		a.id_tipo_anagrafica,\r\n"
					+ "		a.id_progetto,\r\n"
					+ "		C.ID_SEDE,\r\n"
					+ "		C.PARTITA_IVA AS iva\r\n"
					+ "	FROM PBANDI_R_SOGGETTO_PROGETTO A,\r\n"
					+ "		PBANDI_R_SOGG_PROGETTO_SEDE B,\r\n"
					+ "		PBANDI_T_SEDE C,\r\n"
					+ "		PBANDI_T_VARIAZ_ST_CREDITO ptvsc,\r\n"
					+ "		PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf\r\n"
					+ "	WHERE a.progr_soggetto_progetto = b.progr_soggetto_progetto\r\n"
					+ "		AND B.ID_TIPO_SEDE = 1 AND B.ID_SEDE = C.ID_SEDE\r\n"
					+ "		AND prspscf.PROGR_SOGGETTO_PROGETTO = a.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "		AND prspscf.ID_SOGG_PROG_STATO_CREDITO_FP = ptvsc.ID_SOGG_PROG_STATO_CREDITO_FP\r\n"
					+ "),\r\n"
					+ "stato_azienda AS (\r\n"
					+ "	SELECT DISTINCT prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
					+ "	 	prsp.ID_SOGGETTO,\r\n"
					+ "		a.id_stato_azienda,\r\n"
					+ "		c.DESC_STATO_AZIENDA AS desc_stato_azienda\r\n"
					+ "	FROM PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf\r\n"
					+ "	    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prspscf.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	    LEFT JOIN PBANDI_T_SOGG_STATO_AZIENDA a ON a.ID_SOGGETTO  = prsp.ID_SOGGETTO \r\n"
					+ "	    LEFT JOIN PBANDI_D_STATO_AZIENDA c ON a.ID_STATO_AZIENDA = c.ID_STATO_AZIENDA \r\n"
					+ "	WHERE a.DT_FINE_VALIDITA IS NULL AND PRSP.id_tipo_anagrafica = 1 AND PRSP.id_tipo_beneficiario <> 4 \r\n"
					+ ")\r\n"
					+ "SELECT DISTINCT PTVSC.ID_VARIAZ_ST_CREDITO,\r\n"
					+ "	prsp.id_progetto,\r\n"
					+ "	prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
					+ "	PRSPSCF.ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
					+ "	ds.descr1,\r\n"
					+ "	ds.descr2,\r\n"
					+ "	ptb.TITOLO_BANDO,\r\n"
					+ "	pts.CODICE_FISCALE_SOGGETTO,\r\n"
					+ "	PDSCF.DESC_STATO_CREDITO_FP AS desc_stato_cred_fin,\r\n"
					+ "	PTVSC.DT_INIZIO_VALIDITA AS data_proposta,\r\n"
					+ "	pts.NDG,\r\n"
					+ "	prsp.ID_PROGETTO,\r\n"
					+ "	prsp.ID_SOGGETTO,\r\n"
					+ "	PDSCF2.DESC_STATO_CREDITO_FP AS desc_nuovo_stat_cred,\r\n"
					+ "	PDSPVC.DESC_BREVE_STATO_PROP_VAR_CRE AS desc_stat_prop,\r\n"
					+ "	PTVSC.ID_STATO_PROP_VAR_CRE,\r\n"
					+ "	pti.iva,\r\n"
					+ "	ptp.CODICE_VISUALIZZATO,\r\n"
					+ "	ptp.CODICE_FONDO_FINPIS,\r\n"
					+ "	ptvsc.ID_NUOVO_STATO_CREDITO_FP,\r\n"
					+ "	sa.desc_stato_azienda,\r\n"
					+ "	ptvsc.gg_sconf, PDSCF.ID_STATO_CREDITO_FP,\r\n"
					+ "	ptvsc.perc_sconf AS percentuale,\r\n"
					+ "	ptvsc.imp_sconf_capitale,\r\n"
					+ "	ptvsc.imp_sconf_interessi,\r\n"
					+ "	ptvsc.FLAG_CONF_NUOVO_STA_CRE,\r\n"
					+ "	ptvsc.imp_sconf_agev,\r\n"
					+ "	pdma.DESC_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	ptp.CODICE_VISUALIZZATO,\r\n"
					+ "	pdma.ID_MODALITA_AGEVOLAZIONE_rif,\r\n"
					+ "	pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "FROM PBANDI_T_VARIAZ_ST_CREDITO ptvsc\r\n"
					+ "	LEFT JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf ON ptvsc.ID_SOGG_PROG_STATO_CREDITO_FP = prspscf.ID_SOGG_PROG_STATO_CREDITO_FP\r\n"
					+ "	LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_PROGETTO = prspscf.PROGR_SOGGETTO_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
					+ "	left join partita_iva pti on prsp.id_soggetto = pti.id_soggetto\r\n"
					+ "	left join stato_azienda sa on prsp.PROGR_SOGGETTO_PROGETTO = sa.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = PTVSC.id_modalita_agevolazione\r\n"
					+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
					+ "	LEFT JOIN dati_soggetto ds ON prsp.ID_SOGGETTO = ds.id_soggetto\r\n"
					+ "	    AND (\r\n"
					+ "        ( pts.ID_TIPO_SOGGETTO = 2 AND ds.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO )\r\n"
					+ "        OR ( pts.ID_TIPO_SOGGETTO = 1 AND ds.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA )\r\n"
					+ "    	)\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_PROP_VAR_CRE pdspvc on PTVSC .ID_STATO_PROP_VAR_CRE = PDSPVC.ID_STATO_PROP_VAR_CRE\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_CREDITO_FP pdscf ON PDSCF.ID_STATO_CREDITO_FP = PRSPSCF.ID_STATO_CREDITO_FP\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_CREDITO_FP pdscf2 ON PDSCF2.ID_STATO_CREDITO_FP = ptvsc.ID_NUOVO_STATO_CREDITO_FP \r\n"
					+ "	LEFT JOIN pbandi_t_progetto ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO \r\n"
					+ "	LEFT JOIN pbandi_t_domanda ptd ON  ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "	LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptd.PROGR_BANDO_LINEA_INTERVENTO = PRBLI.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "	LEFT JOIN PBANDI_T_BANDO ptb ON PTB.id_bando = PRBLI.id_bando\r\n"
					+ "WHERE PRSP.id_tipo_anagrafica = 1\r\n"
					+ "	AND PRSP.id_tipo_beneficiario <> 4 \r\n"
					+ "	AND ptvsc.ID_VARIAZ_ST_CREDITO IN ( SELECT MAX(ptvsc4.ID_VARIAZ_ST_CREDITO) AS ID_VARIAZ_ST_CREDITO FROM PBANDI_T_VARIAZ_ST_CREDITO ptvsc4 GROUP BY ptvsc4.ID_SOGG_PROG_STATO_CREDITO_FP)\r\n"
					+ " -- AND PDSPVC.ID_STATO_PROP_VAR_CRE = 3 \r\n");

			query.append(query2part);
			query.append(" AND rownum < 2000 \r\n" 
			+ " ORDER BY prsp.ID_PROGETTO  ");
			lista = getJdbcTemplate().query(query.toString(), new StoricoPropostaVarazioneStatoCreditoDTORowMapper());

		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_D_STATO_PROP_VAR_CRE", e);
		} finally {
			LOG.info(" END");
		}

		return lista;
	}


	@Override
	public Boolean salvaStatoProposta(SalvaVariazioneStatoCreditoVO salvaVariazioneStatoCreditoVO, HttpServletRequest request) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::salvaStatoProposta ]";
		LOG.info(prf + "BEGIN");
		
		UserInfoSec user = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		boolean result = true;

		try {

			String update = "update PBANDI_T_VARIAZ_ST_CREDITO \r\n" + " set dt_aggiornamento = sysdate ,\r\n"
					+ " DT_FINE_VALIDITA = sysdate,  \r\n"
					+ " ID_STATO_PROP_VAR_CRE = 3, \r\n " 
					+ " id_utente_agg = ?,\r\n" 
					+ " Flag_conf_nuovo_sta_cre=?\r\n" 
					+ " where ID_VARIAZ_ST_CREDITO = ?";

			getJdbcTemplate().update(update, new Object[] {
					salvaVariazioneStatoCreditoVO.getIdUtenteAgg(),
					salvaVariazioneStatoCreditoVO.getFlagStatoCred(),   
					salvaVariazioneStatoCreditoVO.getIdVariazioneStatoCredito()
			});

			if (salvaVariazioneStatoCreditoVO.getFlagStatoCred().equals("S")) {
				String update2 = " UPDATE PBANDI_R_SOGG_PROG_STA_CRED_FP p \r\n"
						+ "SET p.ID_STATO_CREDITO_FP =  (SELECT PTVSC.ID_NUOVO_STATO_CREDITO_FP \r\n"
						+ "					FROM PBANDI_T_VARIAZ_ST_CREDITO ptvsc \r\n"
						+ "					WHERE PTVSC.ID_VARIAZ_ST_CREDITO ="
						+ salvaVariazioneStatoCreditoVO.getIdVariazioneStatoCredito() + ")\r\n"
						+ "WHERE p.ID_SOGG_PROG_STATO_CREDITO_FP = (SELECT PTVSC.ID_SOGG_PROG_STATO_CREDITO_FP \r\n"
						+ "					FROM PBANDI_T_VARIAZ_ST_CREDITO ptvsc \r\n"
						+ "					WHERE PTVSC.ID_VARIAZ_ST_CREDITO ="
						+ salvaVariazioneStatoCreditoVO.getIdVariazioneStatoCredito() + ")";

				//getJdbcTemplate().update(update2);
				
				
				// Recupero i dati per compilare l'obj per lo stato credito
				Map<String, Object> mapFlag = new HashMap<>();

				String sqlFlagBlocco = "SELECT ptvsc.ID_VARIAZ_ST_CREDITO ,\r\n"
						+ "    ptvsc.ID_SOGG_PROG_STATO_CREDITO_FP ,\r\n"
						+ "    prspscf.PROGR_SOGGETTO_PROGETTO , \r\n"
						+ "    prsp.ID_SOGGETTO ,\r\n"
						+ "    prsp.ID_PROGETTO ,\r\n"
						+ "    prspscf.ID_MODALITA_AGEVOLAZIONE ,\r\n"
						+ "    pdscf.DESC_STATO_CREDITO_FP \r\n"
						+ "    FROM PBANDI_T_VARIAZ_ST_CREDITO ptvsc\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf ON prspscf.ID_SOGG_PROG_STATO_CREDITO_FP  = ptvsc.ID_SOGG_PROG_STATO_CREDITO_FP \r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_CREDITO_FP pdscf ON pdscf.ID_STATO_CREDITO_FP = ptvsc.ID_NUOVO_STATO_CREDITO_FP \r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_PROGETTO  = prspscf.PROGR_SOGGETTO_PROGETTO \r\n"
						+ "    WHERE ptvsc.ID_VARIAZ_ST_CREDITO = " + salvaVariazioneStatoCreditoVO.getIdVariazioneStatoCredito();
				mapFlag = getJdbcTemplate().queryForMap(sqlFlagBlocco);
				
				LocalDate oggi = LocalDate.now();
		        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String dataOggi = oggi.format(formato);
		        
				LOG.info(prf + " ID_VARIAZ_ST_CREDITO: " + mapFlag.get("ID_VARIAZ_ST_CREDITO"));
				LOG.info(prf + " ID_SOGG_PROG_STATO_CREDITO_FP: " + mapFlag.get("ID_SOGG_PROG_STATO_CREDITO_FP"));
				LOG.info(prf + " PROGR_SOGGETTO_PROGETTO: " + mapFlag.get("PROGR_SOGGETTO_PROGETTO"));
				LOG.info(prf + " ID_SOGGETTO: " + mapFlag.get("ID_SOGGETTO"));
				LOG.info(prf + " ID_PROGETTO: " + mapFlag.get("ID_PROGETTO"));
				LOG.info(prf + " ID_MODALITA_AGEVOLAZIONE: " + mapFlag.get("ID_MODALITA_AGEVOLAZIONE"));
				LOG.info(prf + " DESC_STATO_CREDITO_FP: " + mapFlag.get("DESC_STATO_CREDITO_FP"));
				LOG.info(prf + " TODAY'S_DATE: " + dataOggi);
				
				
				SaveSchedaClienteAllVO statoCreditoObj = new SaveSchedaClienteAllVO();
				statoCreditoObj.setIdSoggetto(mapFlag.get("ID_SOGGETTO").toString());
				statoCreditoObj.setIdUtente(user.getIdUtente().toString());
				statoCreditoObj.setStaCre_idCurrentRecord(mapFlag.get("ID_SOGG_PROG_STATO_CREDITO_FP").toString());
				statoCreditoObj.setStaCre_dataMod(dataOggi);
				statoCreditoObj.setStaCre_PROGR_SOGGETTO_PROGETTO(mapFlag.get("PROGR_SOGGETTO_PROGETTO").toString());
				statoCreditoObj.setStaCre_stato(mapFlag.get("DESC_STATO_CREDITO_FP").toString());
				
				monitoraggioCreditiDAO.setStatoCredito(statoCreditoObj, Long.valueOf(mapFlag.get("ID_MODALITA_AGEVOLAZIONE").toString()), Long.valueOf(mapFlag.get("ID_PROGETTO").toString()));
			}

		} catch (Exception e) {
			LOG.error("Exception while trying to update PBANDI_T_VARIAZ_ST_CREDITO", e);
			result = false;
		} finally {
			LOG.info(" END");
		}
		return result;
	}

	@Override
	public List<StatoCreditoVO> statoCredito() {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::statoCredito ]";
		LOG.info(prf + "BEGIN");

		List<StatoCreditoVO> listaStatoCred = new ArrayList<StatoCreditoVO>();

		try {

			String query = "SELECT *\r\n"
					+ " FROM PBANDI_D_STATO_CREDITO_FP";

			RowMapper<StatoCreditoVO> lista = new RowMapper<StatoCreditoVO>() {

				@Override
				public StatoCreditoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					StatoCreditoVO cred = new StatoCreditoVO();
					cred.setIdStatoCredito(rs.getInt("ID_STATO_CREDITO_FP"));
					cred.setDescStatoCredito(rs.getString("DESC_STATO_CREDITO_FP"));
					return cred;
				}
			};

			listaStatoCred = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_D_MODALITA_AGEVOLAZIONE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaStatoCred;
	}

	@Override
	public Boolean rifiutaAccettaMassivaStatoCredito(List<Long> proposteDaConfermare, String flagConferma,
			HttpServletRequest request) {
		String prf = "[ProposteVariazioneStatoCreditoDAOImpl::confermaMassivaStatoCredito ]";
		LOG.info(prf + "BEGIN");
		
		UserInfoSec userInfoSec = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Boolean res = true; 
		try {
			boolean isCallSalvaProposta= true; 
			for (Long idVar : proposteDaConfermare) {
				
				SalvaVariazioneStatoCreditoVO proposta = new SalvaVariazioneStatoCreditoVO();
				
				proposta.setIdVariazioneStatoCredito(idVar);
				proposta.setFlagStatoCred(flagConferma);
				proposta.setIdUtenteAgg(userInfoSec.getIdUtente());
				isCallSalvaProposta= salvaStatoProposta(proposta, request);
				if(isCallSalvaProposta==false) {
					LOG.info(prf + "errore salvataggio stato credito proposta");
				}
			}
					
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to save credit state", e);
			res = false;
		}
		
		return res;
	}
	
}
