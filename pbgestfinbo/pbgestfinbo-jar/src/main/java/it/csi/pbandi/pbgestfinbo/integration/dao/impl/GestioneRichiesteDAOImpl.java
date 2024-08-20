/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.findom.finservrest.dto.AntimafiaDurcResponse;
import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.ValueDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneRichiesteDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElaboraRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.GestioneRichiesteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.NuovaRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SuggestionRichiesteVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;


@Service
public class GestioneRichiesteDAOImpl extends JdbcDaoSupport implements GestioneRichiesteDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	private  static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.finservrest");
	private static final String FINSERVREST_ENDPOINT_BASE = RESOURCE_BUNDLE.getString("finservrest.endpointService");
	private static final String FINSERVREST_SERV_RICEZANTIMAFIADURC = "/api/ricezioneAntimafiaDurc/";
	
	private  Long ID_UTENTE = 0L;
	@Autowired
	AnagraficaBeneficiarioDAO anaDao ; 
	
	@Autowired
	DocumentoManager documentoManager;
	
	@Autowired
	private  it.csi.pbandi.pbgestfinbo.integration.dao.GestioneLog logMonitoraggioService;
	
	@Autowired
	public GestioneRichiesteDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<GestioneRichiesteVO> getRichieste(HttpServletRequest req) throws DaoException {

		String prf = "[GestioneRichiesteDAOImpl::getRichieste]";		
		LOG.info(prf + " BEGIN " );

		List<GestioneRichiesteVO> response = null;

		//ftd.note_rating, ftd.note_classe_rischio, mancano queste due tabelle indicate in analisi
		String query = "WITH selezione AS (SELECT\r\n"
				+ "	ptr.ID_RICHIESTA,\r\n"
				+ "	ptr.DT_INVIO_RICHIESTA,\r\n"
				+ "	ptr.DT_CHIUSURA_RICHIESTA ,\r\n"
				+ "	ptpf.COGNOME,\r\n"
				+ "	pts.CODICE_FISCALE_SOGGETTO,\r\n"
				+ "	pdtr.DESC_TIPO_RICHIESTA ,\r\n"
				+ "	pdsr.DESC_STATO_RICHIESTA ,\r\n"
				+ "	ptd.NUMERO_DOMANDA,\r\n"
				+ "	ptp.CODICE_VISUALIZZATO,\r\n"
				+ "	prbli.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
				+ "	ptr.FLAG_URGENZA,\r\n"
				+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
				+ "	pts2.PARTITA_IVA,\r\n"
				+ "	pts.ID_SOGGETTO, pts.ndg\r\n"
				+ "FROM\r\n"
				+ "	(\r\n"
				+ "	SELECT\r\n"
				+ "		max(ptpf0.ID_PERSONA_FISICA) id_persona_fisica,\r\n"
				+ "		ptr0.ID_RICHIESTA id_richiesta\r\n"
				+ "	FROM\r\n"
				+ "		PBANDI_T_RICHIESTA ptr0\r\n"
				+ "	LEFT JOIN PBANDI_T_UTENTE ptu ON\r\n"
				+ "		ptr0.ID_UTENTE_RICHIEDENTE = ptu.ID_UTENTE\r\n"
				+ "	LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf0 ON\r\n"
				+ "		ptu.ID_SOGGETTO = ptpf0.ID_SOGGETTO\r\n"
				+ "	GROUP BY\r\n"
				+ "		id_richiesta\r\n"
				+ ") filtered\r\n"
				+ "INNER JOIN PBANDI_T_RICHIESTA ptr ON\r\n"
				+ "	filtered.id_richiesta = ptr.ID_RICHIESTA\r\n"
				+ "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON\r\n"
				+ "	filtered.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
				+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n"
				+ "		ptr.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
				+ "	AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "	AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n"
				+ "		prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
				+ "	AND pts.ID_TIPO_SOGGETTO = 2\r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON\r\n"
				+ "		ptr.ID_TIPO_RICHIESTA = pdtr.ID_TIPO_RICHIESTA\r\n"
				+ "LEFT JOIN PBANDI_D_STATO_RICHIESTA pdsr ON\r\n"
				+ "		ptr.ID_STATO_RICHIESTA = pdsr.ID_STATO_RICHIESTA\r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n"
				+ "		ptr.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON\r\n"
				+ "		ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON\r\n"
				+ "		ptr.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON\r\n"
				+ "		prsd.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n"
				+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "AND prsds.ID_TIPO_SEDE = 1\r\n"
				+ "LEFT JOIN PBANDI_T_SEDE pts2 ON prsds.ID_SEDE = pts2.ID_SEDE\r\n"
				+ "order by ptr.DT_INVIO_RICHIESTA desc)\r\n"
				+ ""
				+ "SELECT\r\n"
				+ "	*\r\n"
				+ "FROM\r\n"
				+ "	selezione\r\n"
				+ " where rownum <101";		

//		LOG.info("Query:\n" + query);
//		LOG.debug("Query:\n" + query);

		ResultSetExtractor<List<GestioneRichiesteVO>> rse = new ResultSetExtractor<List<GestioneRichiesteVO>>() {

			@Override
			public List<GestioneRichiesteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<GestioneRichiesteVO> vo = new ArrayList<GestioneRichiesteVO>();
				while (rs.next())
				{
					GestioneRichiesteVO v = new GestioneRichiesteVO();

					v.setCodiceBando(rs.getString("PROGR_BANDO_LINEA_INTERVENTO"));
					v.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					v.setCodiceProgetto(rs.getString("CODICE_VISUALIZZATO"));
					v.setDataRichiesta(rs.getDate("DT_INVIO_RICHIESTA"));
					v.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
					v.setRichiedente(rs.getString("COGNOME"));
					v.setTipoRichiesta(rs.getString("DESC_TIPO_RICHIESTA"));
					v.setStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));
					v.setNag(rs.getString("ID_SOGGETTO"));
					v.setDenominazione(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
					v.setPartitaIva(rs.getString("PARTITA_IVA"));
					String flag = null;
					flag = rs.getString("FLAG_URGENZA"); 
					if(flag!=null && flag.trim().equals("N")){
						v.setModalitaRichiesta("Ordinaria");
					}else if(flag!=null && flag.trim().equals("S")) {
						v.setModalitaRichiesta("Urgente");
					}
					v.setDataChiusura(rs.getDate("DT_CHIUSURA_RICHIESTA"));
					v.setNumeroRichiesta(rs.getString("ID_RICHIESTA"));
					v.setNdg(rs.getString("ndg"));
					vo.add(v);
				}

				return vo;
			}
		};
		response = getJdbcTemplate().query(query.toString(), rse);


//		LOG.info("Result:\n" + response);
//		LOG.debug("Result:\n" + response);
//		LOG.info(prf + "END" );
//		LOG.debug(prf + "END");

		return (response!=null) ? response : new ArrayList<GestioneRichiesteVO>();
	}

	@Override
	public List<GestioneRichiesteVO> findRichieste(BigDecimal tipoRichiesta, BigDecimal statoRichiesta, String numeroDomanda,
			String codiceFondo, String richiedente, String ordinamento, String colonna, HttpServletRequest req) throws DaoException {

		String prf = "[GestioneRichiesteDAOImpl::findRichieste]";		
		LOG.info(prf + " BEGIN " );

		List<GestioneRichiesteVO> response = null;

		StringBuilder query = new StringBuilder();
		StringBuilder queryVera = new StringBuilder();
		 queryVera.append("WITH selezione AS (SELECT\r\n"
				+ "	ptr.ID_RICHIESTA,\r\n"
				+ "	ptr.DT_INVIO_RICHIESTA,\r\n"
				+ "	ptr.DT_CHIUSURA_RICHIESTA ,\r\n"
				+ "	ptpf.COGNOME,pdsr.ID_STATO_RICHIESTA,\r\n"
				+ "	pts.CODICE_FISCALE_SOGGETTO,\r\n"
				+ "	pdtr.DESC_TIPO_RICHIESTA ,\r\n"
				+ "	pdsr.DESC_STATO_RICHIESTA ,\r\n"
				+ "	ptd.NUMERO_DOMANDA,pdtr.ID_TIPO_RICHIESTA,\r\n"
				+ "	ptp.CODICE_VISUALIZZATO,\r\n"
				+ "	prbli.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
				+ "	ptr.FLAG_URGENZA,\r\n"
				+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
				+ "	pts2.PARTITA_IVA,\r\n"
				+ "	pts.ID_SOGGETTO, PTS.NDG\r\n"
				+ "FROM\r\n"
				+ "	(\r\n"
				+ "	SELECT\r\n"
				+ "		max(ptpf0.ID_PERSONA_FISICA) id_persona_fisica,\r\n"
				+ "		ptr0.ID_RICHIESTA id_richiesta\r\n"
				+ "	FROM\r\n"
				+ "		PBANDI_T_RICHIESTA ptr0\r\n"
				+ "	LEFT JOIN PBANDI_T_UTENTE ptu ON\r\n"
				+ "		ptr0.ID_UTENTE_RICHIEDENTE = ptu.ID_UTENTE\r\n"
				+ "	LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf0 ON\r\n"
				+ "		ptu.ID_SOGGETTO = ptpf0.ID_SOGGETTO\r\n"
				+ "	GROUP BY\r\n"
				+ "		id_richiesta\r\n"
				+ ") filtered\r\n"
				+ "INNER JOIN PBANDI_T_RICHIESTA ptr ON\r\n"
				+ "	filtered.id_richiesta = ptr.ID_RICHIESTA\r\n"
				+ "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON\r\n"
				+ "	filtered.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
				+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n"
				+ "		ptr.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
				+ "	AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "	AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n"
				+ "		prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
				+ "	AND pts.ID_TIPO_SOGGETTO = 2\r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON\r\n"
				+ "		ptr.ID_TIPO_RICHIESTA = pdtr.ID_TIPO_RICHIESTA\r\n"
				+ "LEFT JOIN PBANDI_D_STATO_RICHIESTA pdsr ON\r\n"
				+ "		ptr.ID_STATO_RICHIESTA = pdsr.ID_STATO_RICHIESTA\r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n"
				+ "		ptr.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON\r\n"
				+ "		ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON\r\n"
				+ "		ptr.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON\r\n"
				+ "		prsd.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n"
				+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "AND prsds.ID_TIPO_SEDE = 1\r\n"
				+ "LEFT JOIN PBANDI_T_SEDE pts2 ON prsds.ID_SEDE = pts2.ID_SEDE\r\n"
				+ "order by ptr.DT_INVIO_RICHIESTA desc)\r\n"
				+ ""
				+ "SELECT\r\n"
				+ "	*\r\n"
				+ "FROM\r\n"
				+ "	selezione\r\n"
				+ " where rownum <101\r\n");		

		query.append("SELECT\r\n" + 
				"	ptpf.COGNOME,\r\n" + 
				"	pts.CODICE_FISCALE_SOGGETTO,\r\n" + 
				"	pdtr.DESC_TIPO_RICHIESTA,\r\n" + 
				"	pdsr.DESC_STATO_RICHIESTA,\r\n" + 
				"	ptd.NUMERO_DOMANDA,\r\n" + 
				"	ptp.CODICE_VISUALIZZATO,\r\n" + 
				"	ptd.PROGR_BANDO_LINEA_INTERVENTO,\r\n" + 
				"	pts.ID_SOGGETTO,\r\n" + 
				"	ptr.FLAG_URGENZA,\r\n" + 
				"	pteg.DENOMINAZIONE_ENTE_GIURIDICO,	\r\n" + 
				"	ptr.ID_RICHIESTA,\r\n" + 
				"	ptr.DT_INVIO_RICHIESTA,\r\n" + 
				"	ptr.DT_CHIUSURA_RICHIESTA,\r\n" + 
				"	pts2.PARTITA_IVA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_RICHIESTA ptr\r\n" + 
				"LEFT JOIN PBANDI_T_UTENTE ptu \r\n" + 
				"ON\r\n" + 
				"	ptr.ID_UTENTE_RICHIEDENTE = ptu.ID_UTENTE\r\n" + 
				"LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n" + 
				"ON\r\n" + 
				"	ptu.ID_SOGGETTO = ptpf.ID_SOGGETTO\r\n" + 
				"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n" + 
				"ON\r\n" + 
				"	ptr.ID_DOMANDA = prsd.ID_DOMANDA\r\n" + 
				"LEFT JOIN PBANDI_T_SOGGETTO pts \r\n" + 
				"ON\r\n" + 
				"	prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n" + 
				"LEFT JOIN PBANDI_D_TIPO_RICHIESTA pdtr \r\n" + 
				"ON\r\n" + 
				"	ptr.ID_TIPO_RICHIESTA = pdtr.ID_TIPO_RICHIESTA\r\n" + 
				"LEFT JOIN PBANDI_D_STATO_RICHIESTA pdsr \r\n" + 
				"ON\r\n" + 
				"	ptr.ID_STATO_RICHIESTA = pdsr.ID_STATO_RICHIESTA\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd \r\n" + 
				"ON\r\n" + 
				"	prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n" + 
				"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli \r\n" + 
				"ON\r\n" + 
				"	ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n" + 
				"LEFT JOIN PBANDI_T_PROGETTO ptp \r\n" + 
				"ON\r\n" + 
				"	ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n" + 
				"LEFT JOIN TMP_NUM_DOMANDA_GEFO tndg \r\n" + 
				"ON\r\n" + 
				"	prbli.PROGR_BANDO_LINEA_INTERVENTO = tndg.PROGR_BANDO_LINEA_INTERVENTO\r\n" + 
				"LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg \r\n" + 
				"ON\r\n" + 
				"		prsd.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n" + 
				"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON\r\n" + 
				"	prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n" + 
				"	AND prsds.ID_TIPO_SEDE = 1\r\n" + 
				"LEFT JOIN PBANDI_T_SEDE pts2 ON\r\n" + 
				"	prsds.ID_SEDE = pts2.ID_SEDE\r\n" + 
				"WHERE\r\n" + 
				"	prsd.id_tipo_anagrafica = 1\r\n" + 
				"	AND prsd.id_tipo_beneficiario <> 4" +
				"   AND ptpf.ID_PERSONA_FISICA IN (\r\n" + 
				"		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n" + 
				"		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n" + 
				"		GROUP BY perfis1.ID_SOGGETTO) ");

		List<Object> args = new ArrayList<>();

		List<Integer> types = new ArrayList<>();

		if(tipoRichiesta != null && tipoRichiesta.intValue() != 0) {
			queryVera.append("AND ID_TIPO_RICHIESTA =?\r\n");
			args.add(tipoRichiesta.intValue()) ;
			types.add(Types.BIGINT);
		}
		if(statoRichiesta != null && statoRichiesta.intValue() != 0) {
			queryVera.append("AND ID_STATO_RICHIESTA =?\r\n");
			args.add(statoRichiesta.intValue()) ;
			types.add(Types.BIGINT);
		}
		if(numeroDomanda != null && !numeroDomanda.trim().isEmpty()) {
			queryVera.append("AND NUMERO_DOMANDA =?");
			args.add(numeroDomanda);
			types.add(Types.VARCHAR);
		}
		if(codiceFondo != null && !codiceFondo.trim().isEmpty()) {
			queryVera.append("AND PROGR_BANDO_LINEA_INTERVENTO =?");
			args.add(codiceFondo);
			types.add(Types.VARCHAR);
		}
		if(richiedente != null && !richiedente.trim().isEmpty()) {
			queryVera.append("AND COGNOME =?");
			args.add(richiedente.toUpperCase());
			types.add(Types.VARCHAR);
		}
//		if(colonna != null && !colonna.trim().isEmpty()) {
//			query.append(" ORDER BY " + colonna + " " + ordinamento);
//		}

		LOG.debug("Query:\n" + queryVera);

		ResultSetExtractor<List<GestioneRichiesteVO>> rse = new ResultSetExtractor<List<GestioneRichiesteVO>>() {

			@Override
			public List<GestioneRichiesteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<GestioneRichiesteVO> vo = new ArrayList<GestioneRichiesteVO>();
				while (rs.next())
				{
					GestioneRichiesteVO v = new GestioneRichiesteVO();

					v.setCodiceBando(rs.getString("PROGR_BANDO_LINEA_INTERVENTO"));
					v.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					v.setCodiceProgetto(rs.getString("CODICE_VISUALIZZATO"));
					v.setDataRichiesta(rs.getDate("DT_INVIO_RICHIESTA"));
					v.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
					v.setRichiedente(rs.getString("COGNOME"));
					v.setTipoRichiesta(rs.getString("DESC_TIPO_RICHIESTA"));
					v.setStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));
					v.setNag(rs.getString("ID_SOGGETTO"));
					v.setDenominazione(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
					v.setPartitaIva(rs.getString("PARTITA_IVA"));
					String flag = null;
					flag = rs.getString("FLAG_URGENZA"); 
					if(flag!=null && flag.trim().equals("N")){
						v.setModalitaRichiesta("Ordinaria");
					}else if(flag!=null && flag.trim().equals("S")) {
						v.setModalitaRichiesta("Urgente");
					}
					v.setDataChiusura(rs.getDate("DT_CHIUSURA_RICHIESTA"));
					v.setNumeroRichiesta(rs.getString("ID_RICHIESTA"));
					v.setNdg(rs.getString("NDG"));

					vo.add(v);
				}

				return vo;
			}
		};

		//response = getJdbcTemplate().query(queryVera.toString(), args.toArray(new Object[args.size()]), types.stream().mapToInt(i->i).toArray(), rse);
		response = getJdbcTemplate().query(queryVera.toString(), args.toArray(new Object[args.size()]), types.stream().mapToInt(i->i).toArray(),rse);
		LOG.info("Result:\n" + response);
		LOG.debug("Result:\n" + response);
		LOG.info(prf + "END" );
		LOG.debug(prf + "END");

		return (response!=null) ? response : new ArrayList<GestioneRichiesteVO>();
	}

	@Override
	public List<SuggestionRichiesteVO> getDomandaNuovaRichiesta(String domandaNuovaRichiesta, HttpServletRequest req)
			throws DaoException {
		String prf = "[GestioneRichiesteDAOImpl::getDomandaNuovaRichiesta]";		
		LOG.info(prf + " BEGIN " );

		List<SuggestionRichiesteVO> response = null;

		StringBuilder query = new StringBuilder();

		query.append("SELECT ptd.ID_DOMANDA, ptd.NUMERO_DOMANDA FROM PBANDI_T_DOMANDA ptd WHERE ptd.NUMERO_DOMANDA LIKE ?");

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		Object[] args = new Object[]{domandaNuovaRichiesta+"%"};

		int[] types = new int[]{Types.VARCHAR};

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		ResultSetExtractor<List<SuggestionRichiesteVO>> rse = new ResultSetExtractor<List<SuggestionRichiesteVO>>() {

			@Override
			public List<SuggestionRichiesteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<SuggestionRichiesteVO> vo = new ArrayList<SuggestionRichiesteVO>();
				while (rs.next())
				{
					SuggestionRichiesteVO v = new SuggestionRichiesteVO();

					v.setIdDomanda(rs.getBigDecimal("ID_DOMANDA"));
					v.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));

					vo.add(v);
				}

				return vo;
			}
		};

		response = getJdbcTemplate().query(query.toString(), args, types, rse);

		return (response!=null) ? response : new ArrayList<SuggestionRichiesteVO>();
	}

	@Override
	public List<SuggestionRichiesteVO> getCodiceProgetto(String codiceProgetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[GestioneRichiesteDAOImpl::getCodiceProgetto]";		
		LOG.info(prf + " BEGIN " );

		List<SuggestionRichiesteVO> response = null;

		StringBuilder query = new StringBuilder();

		query.append("SELECT ptp.ID_DOMANDA, ptp.CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO ptp WHERE ptp.CODICE_VISUALIZZATO LIKE ?");

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		Object[] args = new Object[]{codiceProgetto+"%"};

		int[] types = new int[]{Types.VARCHAR};

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		ResultSetExtractor<List<SuggestionRichiesteVO>> rse = new ResultSetExtractor<List<SuggestionRichiesteVO>>() {

			@Override
			public List<SuggestionRichiesteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<SuggestionRichiesteVO> vo = new ArrayList<SuggestionRichiesteVO>();
				while (rs.next())
				{
					SuggestionRichiesteVO v = new SuggestionRichiesteVO();

					v.setIdDomanda(rs.getBigDecimal("ID_DOMANDA"));
					v.setNumeroDomanda(rs.getString("CODICE_VISUALIZZATO"));

					vo.add(v);
				}

				return vo;
			}
		};

		response = getJdbcTemplate().query(query.toString(), args, types, rse);

		return (response!=null) ? response : new ArrayList<SuggestionRichiesteVO>();
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public int insertNuovaRichiesta(NuovaRichiestaVO nuovaRichiesta, HttpServletRequest req)
			throws DaoException {

		
		List<String> response = null;
		List<String> response2 = null;
		String selectCheck = "SELECT\r\n" + 
				"	ptd.ID_DOMANDA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_DOMANDA ptd\r\n" + 
				"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
				"	ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n" + 
				"LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n" + 
				"	pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n" + 
				"LEFT JOIN PBANDI_T_PROGETTO ptp ON\r\n" + 
				"	ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n" + 
				"WHERE \r\n" + 
				"	ptd.NUMERO_DOMANDA = '"+ nuovaRichiesta.getNumeroDomanda() +"'\r\n" + 
				"	AND ptd.PROGR_BANDO_LINEA_INTERVENTO = "+ nuovaRichiesta.getCodiceBando() +"\r\n" + 
				"	AND pts.CODICE_FISCALE_SOGGETTO = '"+ nuovaRichiesta.getCodiceFiscale() +"'\r\n" + 
				"	AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n" + 
				"	AND prsd.ID_TIPO_ANAGRAFICA = 1	";
		if(nuovaRichiesta.getCodiceProgetto() != null) {
			selectCheck += "\r\n" + 
					"	AND \r\n" + 
					"ptp.CODICE_VISUALIZZATO = '"+ nuovaRichiesta.getCodiceProgetto() +"'";
		}
		
		response = getJdbcTemplate().queryForList(selectCheck.toString(), String.class);
		

		
		




		if(!nuovaRichiesta.getIdTipoRichiesta().equals(1)) {

			String selectCheck2 = "SELECT\r\n" + 
					"	ptr.ID_RICHIESTA\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_T_RICHIESTA ptr\r\n" + 
					"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
					"	ptr.ID_DOMANDA = ptd.ID_DOMANDA\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
					"	prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n" + 
					"WHERE\r\n" + 
					"	ptd.NUMERO_DOMANDA = '"+ nuovaRichiesta.getNumeroDomanda() +"'\r\n" + 
					"	AND \r\n" + 
					"	(\r\n" + 
					"	ptr.ID_STATO_RICHIESTA = 1\r\n" + 
					"		OR \r\n" + 
					"	ptr.ID_STATO_RICHIESTA = 2\r\n" + 
					"		OR \r\n" + 
					"	ptr.ID_STATO_RICHIESTA = 3\r\n" + 
					"	)\r\n" + 
					"	AND \r\n" + 
					"	prsd.ID_TIPO_BENEFICIARIO <> 4\r\n" + 
					"	AND \r\n" + 
					"	prsd.ID_TIPO_ANAGRAFICA = 1";

			response2 = getJdbcTemplate().queryForList(selectCheck2.toString(), String.class);
			
		}


		if(response.size() == 0) {
			return 0;
				
		}else {
			//MI FACCIO IL NEXTVAL DI ID_RICHIESTA
			String selectNextVal = "SELECT\r\n" + 
					"	SEQ_PBANDI_T_RICHIESTA.NEXTVAL AS nuovoIdRichiesta\r\n" + 
					"FROM\r\n" + 
					"	dual";
			List<String> nextVal = getJdbcTemplate().queryForList(selectNextVal.toString(), String.class);

			// PBANDI_T_RICHIESTA
			String sqlInsertRich = "INSERT\r\n" + 
					"	INTO\r\n" + 
					"	PBANDI_T_RICHIESTA (ID_RICHIESTA,\r\n" + 
					"	ID_TIPO_RICHIESTA,\r\n" + 
					"	ID_STATO_RICHIESTA,\r\n" + 
					"	ID_UTENTE_RICHIEDENTE,\r\n" + 
					"	ID_UTENTE_INS,\r\n" + 
					"	ID_DOMANDA,\r\n" + 
					"	DT_INVIO_RICHIESTA,\r\n" + 
					"	DT_INIZIO_VALIDITA,\r\n" + 
					"	FLAG_URGENZA)\r\n" + 
					"VALUES ("+ nextVal.get(0) +",\r\n" + 
					""+ nuovaRichiesta.getIdTipoRichiesta() +",\r\n" + 
					"1,\r\n" + 
					""+ nuovaRichiesta.getIdUtenteIns() +",\r\n" + 
					""+ nuovaRichiesta.getIdUtenteIns() +",\r\n" + 
					"(\r\n" + 
					"SELECT\r\n" + 
					"	ptd.ID_DOMANDA\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_T_DOMANDA ptd\r\n" + 
					"WHERE\r\n" + 
					"	ptd.NUMERO_DOMANDA = '"+ nuovaRichiesta.getNumeroDomanda() +"')," + 
					"SYSDATE,\r\n" + 
					"SYSDATE,\r\n" + 
					"'"+ nuovaRichiesta.getFlagUrgenza() +"'\r\n" + 
					")";
			getJdbcTemplate().update(sqlInsertRich);

			return 1;
		}
	}
	
	
	// nuovo metodo
	public String insertNuovaRichiesta() {
		// LA PRIMA COSA DA FARE è DI RICAVARE DAL DB LA RICHIESTA CON ID_DOMANDA E ID_SOGGETTO CON LO STATO .. 
		
		
		
		// SE ESISTE ALLORA FACCIO I CONTROLLI SE NON ESISTE ALLORA LA INSERISCO 

		try {
			String sql = ""; 
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null; 
	}


	@Override
	public List<StoricoRichiestaVO> getStoricoRichiesta(String idRichiesta, HttpServletRequest req)
			throws DaoException {
		String prf = "[GestioneRichiesteDAOImpl::getStoricoRichiesta]";		
		LOG.info(prf + " BEGIN " );

		List<StoricoRichiestaVO> response = null;

		//ftd.note_rating, ftd.note_classe_rischio, mancano queste due tabelle indicate in analisi
		String query = "SELECT\r\n" + 
				"	pttr.ID_RICHIESTA id_richiesta,\r\n" + 
				"	pttr.DESTINATARIO_MITTENTE destinatario_mittente,\r\n" + 
				"	pttr.DT_COMUNICAZIONE_ESTERNA dt_comunicazione_esterna,\r\n" + 
				"	pttr.NUMERO_PROTOCOLLO numero_protocollo,\r\n" + 
				"	pttr.MOTIVAZIONE motivazione,\r\n" + 
				"	ptpf.COGNOME cognome,\r\n" + 
				"	pdtc.DESC_TIPO_COMUNICAZIONE desc_tipo_comunicazione\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
				"LEFT JOIN PBANDI_T_UTENTE ptu ON\r\n" + 
				"	pttr.ID_UTENTE_INS = ptu.ID_UTENTE\r\n" + 
				"LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON\r\n" + 
				"	ptu.ID_SOGGETTO = ptpf.ID_SOGGETTO\r\n" + 
				"LEFT JOIN PBANDI_D_TIPO_COMUNICAZIONE pdtc ON\r\n" + 
				"	pttr.ID_TIPO_COMUNICAZIONE = pdtc.ID_TIPO_COMUNICAZIONE\r\n" + 
				"WHERE\r\n" + 
				"	pttr.ID_RICHIESTA = "+ idRichiesta +" \r\n" + 
				"	AND ptpf.ID_PERSONA_FISICA IN (\r\n" + 
				"	SELECT\r\n" + 
				"		MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n" + 
				"	FROM\r\n" + 
				"		PBANDI_T_PERSONA_FISICA perfis1\r\n" + 
				"	GROUP BY\r\n" + 
				"		perfis1.ID_SOGGETTO)\r\n"
				+ "order by pttr.DT_INIZIO_VALIDITA  desc";		

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		ResultSetExtractor<List<StoricoRichiestaVO>> rse = new ResultSetExtractor<List<StoricoRichiestaVO>>() {

			@Override
			public List<StoricoRichiestaVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<StoricoRichiestaVO> vo = new ArrayList<StoricoRichiestaVO>();
				while (rs.next())
				{
					StoricoRichiestaVO v = new StoricoRichiestaVO();
					v.setId_richiesta(rs.getString("ID_RICHIESTA"));
					v.setDestinatario_mittente(rs.getString("DESTINATARIO_MITTENTE"));
					v.setDt_comunicazione_esterna(rs.getDate("DT_COMUNICAZIONE_ESTERNA"));
					v.setNumero_protocollo(rs.getString("NUMERO_PROTOCOLLO"));
					v.setMotivazione(rs.getString("MOTIVAZIONE"));
					v.setCognome(rs.getString("COGNOME"));
					v.setDesc_tipo_comunicazione(rs.getString("DESC_TIPO_COMUNICAZIONE"));

					vo.add(v);
				}

				return vo;
			}
		};
		response = getJdbcTemplate().query(query.toString(), rse);


		LOG.info("Result:\n" + response);
		LOG.debug("Result:\n" + response);
		LOG.info(prf + "END" );
		LOG.debug(prf + "END");

		return (response!=null) ? response : new ArrayList<StoricoRichiestaVO>();
	}

	@Override
	public List<ElaboraRichiestaVO> getRichiesta(String idRichiesta, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteDAOImpl::getRichiesta]";		
		LOG.info(prf + " BEGIN " );

		List<ElaboraRichiestaVO> response = null;

		//ftd.note_rating, ftd.note_classe_rischio, mancano queste due tabelle indicate in analisi
		String query = "SELECT\r\n" + 
				"	ptr.ID_RICHIESTA ,\r\n" + 
				"	ptr.ID_TIPO_RICHIESTA,\r\n" + 
				"	ptr.ID_STATO_RICHIESTA,\r\n" + 
				"	ptd.NUMERO_DOMANDA, ptr.FLAG_URGENZA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_RICHIESTA ptr\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptr.ID_DOMANDA = ptd.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"	ptr.ID_RICHIESTA ="+ idRichiesta;		

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		ResultSetExtractor<List<ElaboraRichiestaVO>> rse = new ResultSetExtractor<List<ElaboraRichiestaVO>>() {

			@Override
			public List<ElaboraRichiestaVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ElaboraRichiestaVO> vo = new ArrayList<ElaboraRichiestaVO>();
				while (rs.next())
				{
					String flag =null;
					ElaboraRichiestaVO v = new ElaboraRichiestaVO();
					v.setIdRichiesta(rs.getString("ID_RICHIESTA"));
					v.setIdTipoRichiesta(rs.getString("ID_TIPO_RICHIESTA"));
					v.setIdStatoRichiesta(rs.getString("ID_STATO_RICHIESTA"));
					v.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
					flag=rs.getString("FLAG_URGENZA"); 
					
					if(flag.trim().equals("S")) {
						v.setFlagUrgenza("1");
					} else if(flag.trim().equals("N")){
						v.setFlagUrgenza("2");
					}
					vo.add(v);
				}

				return vo;
			}
		};
		response = getJdbcTemplate().query(query.toString(), rse);


		LOG.info("Result:\n" + response);
		LOG.debug("Result:\n" + response);
		LOG.info(prf + "END" );
		LOG.debug(prf + "END");

		return (response!=null) ? response : new ArrayList<ElaboraRichiestaVO>();
	}

	@Override
	public int elaboraRichiesta(ElaboraRichiestaVO elaboraRichiesta, HttpServletRequest req) {
		String prf = "[RicercaBeneficiarioDAOImpl::setSchedaCliente]";
		LOG.info(prf + " BEGIN");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dataComunicazione = df.format(elaboraRichiesta.getDataComunicazione());

		//TRACCIATURA RICHIESTA
		String sqlSelect = "SELECT\r\n" + 
				"	ID_TRACCIATURA_RICH\r\n" +
				"FROM\r\n" + 
				"	PBANDI_T_TRACCIATURA_RICH\r\n" + 
				"WHERE\r\n" + 
				"	ID_RICHIESTA = "+ elaboraRichiesta.getIdRichiesta() + " AND \r\n" + 
				"	ID_TIPO_COMUNICAZIONE = "+ elaboraRichiesta.getTipoComunicazione()+ " AND \r\n" + 
				"	DESTINATARIO_MITTENTE = '"+ elaboraRichiesta.getDestinatarioMittente() + "' AND \r\n" + 
				"	DT_COMUNICAZIONE_ESTERNA = TO_DATE('" + dataComunicazione +"',  'yyyy/mm/dd')";

		if(elaboraRichiesta.getNumeroProtocollo() != null) {
			sqlSelect += "\r\n" + 
					"	AND \r\n" + 
					"NUMERO_PROTOCOLLO = '"+ elaboraRichiesta.getNumeroProtocollo() +"'";
		}
		if(elaboraRichiesta.getMotivazione()!=null) {
			sqlSelect += "\r\n" + 
					"	AND \r\n" + 
					"MOTIVAZIONE = '"+ elaboraRichiesta.getMotivazione() +"'";
		}

		List<String> response = null;

		response = getJdbcTemplate().queryForList(sqlSelect.toString(), String.class);

		if(response.size() > 0) {
			String sqlUpdate = "UPDATE PBANDI_T_TRACCIATURA_RICH \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraRichiesta.getIdUtenteAgg() + "\r\n" + 
					"WHERE ID_TRACCIATURA_RICH = "+ response.get(0);
			getJdbcTemplate().update(sqlUpdate);
		}else{
			String sqlInsert = "INSERT\r\n" + 
					"	INTO\r\n" + 
					"	PBANDI_T_TRACCIATURA_RICH (\r\n" + 
					"	ID_TRACCIATURA_RICH,\r\n" + 
					"	ID_RICHIESTA,\r\n" + 
					"	ID_TIPO_COMUNICAZIONE,\r\n" + 
					"	DESTINATARIO_MITTENTE,\r\n" + 
					"	DT_COMUNICAZIONE_ESTERNA,\r\n" + 
					"	NUMERO_PROTOCOLLO,\r\n" + 
					"	MOTIVAZIONE,\r\n" + 
					"	ID_UTENTE_INS,\r\n" + 
					"	DT_INIZIO_VALIDITA\r\n" + 
					") VALUES (\r\n" + 
					"	SEQ_PBANDI_T_TRACC_RICHIESTA.nextval,\r\n" 
					+ elaboraRichiesta.getIdRichiesta() + ","
					+ elaboraRichiesta.getTipoComunicazione() + ","
					+ "'" + elaboraRichiesta.getDestinatarioMittente() + "',"
					+ "SYSDATE,"
					+ "'" +elaboraRichiesta.getNumeroProtocollo() + "',"
					+ "'" +elaboraRichiesta.getMotivazione() + "',"
					+ elaboraRichiesta.getIdUtenteAgg() + ","
					+ "SYSDATE" + ""
					+ "\r\n" + 
					")";
			getJdbcTemplate().update(sqlInsert);

		}

		String sqlSelectRichiesta = "SELECT\r\n" + 
				"	ID_RICHIESTA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_RICHIESTA ptr\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptr.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"	ptd.NUMERO_DOMANDA = '"+ elaboraRichiesta.getNumeroDomanda() + "'\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_TIPO_RICHIESTA = "+ elaboraRichiesta.getIdTipoRichiesta() + "\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_STATO_RICHIESTA = "+ elaboraRichiesta.getIdStatoRichiesta()	+"\r\n"
				+ "AND PTR.FLAG_URGENZA='"+elaboraRichiesta.getFlagUrgenza().trim()+"'\r\n";

		List<String> responseRichiesta = null;

		responseRichiesta = getJdbcTemplate().queryForList(sqlSelectRichiesta.toString(), String.class);

		if(responseRichiesta.size() > 0) {
			String sqlUpdateRichiesta = "UPDATE PBANDI_T_RICHIESTA \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraRichiesta.getIdUtenteAgg() + "\r\n" + 
					"WHERE ID_RICHIESTA = "+ responseRichiesta.get(0);
			getJdbcTemplate().update(sqlUpdateRichiesta);
		}else{
			String sqlUpdateRichiesta = "UPDATE\r\n" + 
					"	PBANDI_T_RICHIESTA\r\n" + 
					"SET\r\n" + 
					"	ID_TIPO_RICHIESTA = "+ elaboraRichiesta.getIdTipoRichiesta() + ",\r\n" + 
					"	ID_STATO_RICHIESTA = "+ elaboraRichiesta.getIdStatoRichiesta()+ ",\r\n" + 
					"	ID_UTENTE_AGG = "+ elaboraRichiesta.getIdUtenteAgg() + ",\r\n" + 
					"	FLAG_URGENZA ='"+elaboraRichiesta.getFlagUrgenza().trim()+"'\r\n" + 
					"WHERE\r\n" + 
					"	ID_RICHIESTA = "+ elaboraRichiesta.getIdRichiesta();
			getJdbcTemplate().update(sqlUpdateRichiesta);
		}
		// chiusura 
		if(elaboraRichiesta.getIdStatoRichiesta().equals("4")) {
			getJdbcTemplate().update("update PBANDI_T_RICHIESTA set DT_CHIUSURA_RICHIESTA=trunc(sysdate) where id_richiesta=?", new Object[] {
					elaboraRichiesta.getIdRichiesta()	
			});
		}
		
		return 0;

	}


	@Override
	public BigDecimal elaboraDurc(ElaboraRichiestaVO elaboraDurc, HttpServletRequest req) {
		String prf = "[RicercaBeneficiarioDAOImpl::setSchedaCliente]";
		LOG.info(prf + " BEGIN");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		String dataScadenza = null;

		if(elaboraDurc.getDataScadenza()!=null && !elaboraDurc.getEsito().equals("2")) {			
			dataScadenza = df.format(elaboraDurc.getDataScadenza());
		}
		
		String dataComunicazione = df.format(elaboraDurc.getDataComunicazione());
		String dataEmissione = df2.format(elaboraDurc.getDataEmissione());
		
		boolean res; 
		
		// se 'esito del durc e 2 ovvero non regolare lo inserisco dentro la tabella blocco
		if(elaboraDurc.getEsito().trim().equals("2")) {
			if(elaboraDurc.getIdTipoRichiesta().trim().equals("1")) {				
				BloccoVO blocco =  new BloccoVO(); 
				blocco.setIdCausaleBlocco(2);
				blocco.setIdSoggetto(BigDecimal.valueOf(Integer.parseInt(elaboraDurc.getNag())));
				blocco.setIdUtente(BigDecimal.valueOf(Integer.parseInt(elaboraDurc.getIdUtenteAgg())));
				res=anaDao.insertBlocco(blocco); 	
				LOG.debug(prf + "insert blocco result " + res);
			} else if(elaboraDurc.getIdTipoRichiesta().trim().equals("2")) {
				BloccoVO blocco =  new BloccoVO(); 
				blocco.setIdCausaleBlocco(16);
				blocco.setNumeroDomanda(elaboraDurc.getNumeroDomanda());
				blocco.setIdSoggetto(BigDecimal.valueOf(Integer.parseInt(elaboraDurc.getNag())));
				blocco.setIdUtente(BigDecimal.valueOf(Integer.parseInt(elaboraDurc.getIdUtenteAgg())));
				res = anaDao.insertBloccoDomanda(blocco, elaboraDurc.getNag(), blocco.getNumeroDomanda());
				LOG.debug(prf + "insert blocco result " + res);
			}
		} 


		String sqlSelect = "SELECT\r\n" + 
				"	ID_TRACCIATURA_RICH\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
				"WHERE\r\n" + 
				"	ID_RICHIESTA = "+ elaboraDurc.getIdRichiesta() +"\r\n" +
				"	AND \r\n" + 
				"ID_TIPO_COMUNICAZIONE = "+ elaboraDurc.getTipoComunicazione() +"\r\n" + 
				"	AND \r\n" + 
				"DESTINATARIO_MITTENTE = '"+ elaboraDurc.getDestinatarioMittente() +"' \r\n" + 
				"	AND \r\n" + 
				"NUMERO_PROTOCOLLO ='"+ elaboraDurc.getNumeroProtocollo() +"'\r\n" + 
				"	AND \r\n" + 
				"DT_COMUNICAZIONE_ESTERNA = TO_DATE('"+ dataComunicazione +"', 'yyyy/mm/dd')";

		String sqlSelectRichiesta = "SELECT\r\n" + 
				"	ID_RICHIESTA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_RICHIESTA ptr\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptr.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"	ptd.NUMERO_DOMANDA = '"+ elaboraDurc.getNumeroDomanda() + "'\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_TIPO_RICHIESTA = "+ elaboraDurc.getIdTipoRichiesta() + "\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_STATO_RICHIESTA <> "+ elaboraDurc.getIdStatoRichiesta();


		String sqlSelectSoggettoDurc = "SELECT\r\n" + 
				"	ID_SOGGETTO_DURC \r\n" +
				"FROM\r\n" + 
				"	PBANDI_T_SOGGETTO_DURC\r\n" + 
				"WHERE ID_SOGGETTO = "+ elaboraDurc.getNag() + "\r\n" ;

		String sqlSelectSoggettoDsan = "SELECT\r\n" + 
				"	ID_SOGGETTO_DSAN \r\n" +
				"FROM\r\n" + 
				"	PBANDI_T_SOGGETTO_DSAN ptsd \r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptsd.ID_DOMANDA\r\n" + 
				"WHERE ptd.NUMERO_DOMANDA = '"+ elaboraDurc.getNumeroDomanda() + "'  \r\n" + 
				"--AND ptsd.DT_EMISSIONE_DSAN = TO_DATE('" + dataEmissione +"',  'yyyy/mm/dd') \r\n";
				//"and ptsd.NUM_PROTOCOLLO = '"+ elaboraDurc.getNumeroProtocollo()+ "'";

		//TRACCIATURA RICHIESTA
//		List<String> response = null;
//
//		response = getJdbcTemplate().queryForList(sqlSelect.toString(), String.class);
//
//		if(response.size() > 0) {
//			String sqlUpdate = "UPDATE PBANDI_T_TRACCIATURA_RICH \r\n" + 
//					"SET ID_UTENTE_AGG = "+ elaboraDurc.getIdUtenteAgg() + "\r\n" + 
//					"WHERE ID_TRACCIATURA_RICH = "+ response.get(0);
//			getJdbcTemplate().update(sqlUpdate);
//		}else{
//			String sqlInsert = "INSERT\r\n" + 
//					"	INTO\r\n" + 
//					"	PBANDI_T_TRACCIATURA_RICH (\r\n" + 
//					"	ID_TRACCIATURA_RICH,\r\n" + 
//					"	ID_RICHIESTA,\r\n" + 
//					"	ID_TIPO_COMUNICAZIONE,\r\n" + 
//					"	DESTINATARIO_MITTENTE,\r\n" + 
//					"	DT_COMUNICAZIONE_ESTERNA,\r\n" + 
//					"	NUMERO_PROTOCOLLO,\r\n" + 
//					"	MOTIVAZIONE,\r\n" + 
//					"	ID_UTENTE_INS,\r\n" + 
//					"	DT_INIZIO_VALIDITA\r\n" + 
//					") VALUES (\r\n" + 
//					"	SEQ_PBANDI_T_TRACC_RICHIESTA.nextval,\r\n" 
//					+ elaboraDurc.getIdRichiesta() + ","
//					+ elaboraDurc.getTipoComunicazione() + ","
//					+ "'" + elaboraDurc.getDestinatarioMittente() + "',"
//					+ "SYSDATE,"
//					+ "'" +elaboraDurc.getNumeroProtocollo() + "',"
//					+ "'" +elaboraDurc.getMotivazione() + "',"
//					+ elaboraDurc.getIdUtenteAgg() + ","
//					+ "SYSDATE" + ""
//					+ "\r\n" + 
//					")";
//			getJdbcTemplate().update(sqlInsert);
//		}

		//RICHIESTA
		List<String> responseRichiesta = null;

		responseRichiesta = getJdbcTemplate().queryForList(sqlSelectRichiesta.toString(), String.class);

		if(responseRichiesta.size() > 0) {
			String sqlUpdateRichiesta = "UPDATE PBANDI_T_RICHIESTA \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraDurc.getIdUtenteAgg() + "\r\n" + 
					"WHERE ID_RICHIESTA = "+ responseRichiesta.get(0);
			getJdbcTemplate().update(sqlUpdateRichiesta);
		}else{
			StringBuilder update = new StringBuilder();
			update.append("UPDATE\r\n" + 
					"	PBANDI_T_RICHIESTA\r\n" + 
					"SET\r\n" + 
					"	ID_TIPO_RICHIESTA = "+ elaboraDurc.getIdTipoRichiesta() + ",\r\n" + 
					"	ID_STATO_RICHIESTA = "+ elaboraDurc.getIdStatoRichiesta()+ ",\r\n" + 
					"	ID_UTENTE_AGG = "+ elaboraDurc.getIdUtenteAgg() + ",\r\n" ); 
					if(elaboraDurc.getIdStatoRichiesta().equals("4")) {						
						update.append("	DT_CHIUSURA_RICHIESTA = SYSDATE,\r\n" );
					}
					update.append(	"	FLAG_URGENZA = 'N'\r\n" + 
					"WHERE\r\n" + 
					"	ID_RICHIESTA = "+ elaboraDurc.getIdRichiesta());
			getJdbcTemplate().update(update.toString());
		}


		BigDecimal seq = null ; 
		//SOGGETTO_DURC

		if(elaboraDurc.getIdTipoRichiesta().equals("1")) {
			List<BigDecimal> responseSoggettDurc= null;

			responseSoggettDurc = getJdbcTemplate().queryForList(sqlSelectSoggettoDurc.toString(), BigDecimal.class);

			seq = getJdbcTemplate().queryForObject("select SEQ_PBANDI_T_SOGGETTO_DURC.nextval from dual", BigDecimal.class); 
			if(responseSoggettDurc.size() > 0) {
				String sqlUpdateSoggettoDurc = "UPDATE PBANDI_T_SOGGETTO_DURC  \r\n" 
						+ "SET ID_UTENTE_AGG = ?, \r\n "
						+ "ID_TIPO_ESITO_DURC= ?,\r\n"
						+ "DT_SCADENZA =?,\r\n"
						+ "NUM_PROTOCOLLO_INPS=? \r\n"
						+ "WHERE ID_SOGGETTO_DURC = ?"  ;
				getJdbcTemplate().update(sqlUpdateSoggettoDurc, new Object[] {
						elaboraDurc.getIdUtenteAgg(),
						elaboraDurc.getEsito(), 
						(dataScadenza!=null && dataScadenza.trim().length()>0 &&
						!elaboraDurc.getEsito().equals("2"))?java.sql.Date.valueOf(dataScadenza):null,
						(elaboraDurc.getNumeroProtocolloInps()!=null)? elaboraDurc.getNumeroProtocolloInps():null,
						responseSoggettDurc.get(0)
				});
				seq = responseSoggettDurc.get(0);
				// UPDATE LA TABELLA T_RICHIESTA
				String updateTRichiesta="update PBANDI_T_RICHIESTA set ID_STATO_RICHIESTA=" + elaboraDurc.getIdStatoRichiesta() + "\r\n"
				+ " where id_richiesta ="+ elaboraDurc.getIdRichiesta();
				getJdbcTemplate().update(updateTRichiesta); 
			}else{
				String sqlInsertSoggettoDurc = "INSERT\r\n" + 
						"	INTO\r\n" + 
						"	PBANDI_T_SOGGETTO_DURC (ID_SOGGETTO_DURC,\r\n" + 
						"	ID_SOGGETTO,\r\n" + 
						"	ID_TIPO_ESITO_DURC,\r\n" + 
						"	DT_EMISSIONE_DURC,\r\n" + 
						"	DT_SCADENZA,\r\n" + 
						"	NUM_PROTOCOLLO_INPS,\r\n" + 
						"	ID_UTENTE_INS,\r\n" + 
						"	DT_INIZIO_VALIDITA)\r\n" + 
						"VALUES (?,?,?,\r\n" +
						"?,?,\r\n" + 
						"'"+ elaboraDurc.getNumeroProtocolloInps() +"',\r\n" + 
						""+ elaboraDurc.getIdUtenteAgg() +",\r\n" + 
						"SYSDATE\r\n" + 
						")";
				//getJdbcTemplate().update(sqlInsertSoggettoDurc);
				
				
				getJdbcTemplate().update(sqlInsertSoggettoDurc, new Object[] {
						seq,elaboraDurc.getNag(), elaboraDurc.getEsito() , 
						(dataEmissione!=null && dataEmissione.trim().length()>0)?java.sql.Date.valueOf(dataEmissione): null, 
						(dataScadenza!=null && dataScadenza.trim().length()>0 &&
						!elaboraDurc.getEsito().equals("2"))?java.sql.Date.valueOf(dataScadenza):null	
				});
				
							}
		}

		//SOGGETTO_DSAN

		if(elaboraDurc.getIdTipoRichiesta().equals("2")) {
			List<BigDecimal> responseSoggettDsan= null;

			responseSoggettDsan = getJdbcTemplate().queryForList(sqlSelectSoggettoDsan.toString(), BigDecimal.class);
		
			if(responseSoggettDsan.size() > 0) {
				String sqlUpdateSoggettoDsan = "UPDATE PBANDI_T_SOGGETTO_DSAN \r\n" + 
						"SET ID_UTENTE_AGG = "+ elaboraDurc.getIdUtenteAgg() + "\r\n,"
						+ "DT_SCADENZA=?" + 
						"WHERE ID_SOGGETTO_DSAN ="+ responseSoggettDsan.get(0);
				getJdbcTemplate().update(sqlUpdateSoggettoDsan, new Object[] {
						(elaboraDurc.getDataScadenza()!=null && elaboraDurc.getDataScadenza().toString().trim().length()>0)?java.sql.Date.valueOf(dataScadenza):null,
				});
				seq = responseSoggettDsan.get(0); 
				
				// UPDATE LA TABELLA T_RICHIESTA
				String updateTRichiesta="update PBANDI_T_RICHIESTA set ID_STATO_RICHIESTA=" + elaboraDurc.getIdStatoRichiesta() + "\r\n"
				+ " where id_richiesta ="+ elaboraDurc.getIdRichiesta();
				getJdbcTemplate().update(updateTRichiesta); 
			}else{
				seq = getJdbcTemplate().queryForObject("select SEQ_PBANDI_T_SOGGETTO_DSAN.nextval from dual", BigDecimal.class);
				String sqlInsertSoggettoDsan = "INSERT\r\n" + 
						"	INTO\r\n" + 
						"	PBANDI_T_SOGGETTO_DSAN (ID_SOGGETTO_DSAN,\r\n" + 
						"	DT_EMISSIONE_DSAN,\r\n" + 
						"	DT_SCADENZA,\r\n" + 
						"	ID_UTENTE_INS,\r\n" + 
						"	DT_INIZIO_VALIDITA,\r\n" + 
						"	ID_DOMANDA)\r\n" + 
						"VALUES (\r\n" + seq+",\r\n" + 
						"?,\r\n" + 
						"?,\r\n" + 
						""+ elaboraDurc.getIdUtenteAgg() +",\r\n" + 
						"SYSDATE,\r\n" + 
						"(\r\n" + 
						"SELECT\r\n" + 
						"	ptd2.ID_DOMANDA\r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_DOMANDA ptd2\r\n" + 
						"WHERE\r\n" + 
						"	ptd2.NUMERO_DOMANDA = '"+ elaboraDurc.getNumeroDomanda() +"')\r\n" + 
						")";
				
				//TO_DATE('"+ dataEmissione +"', 'yyyy/mm/dd')
				getJdbcTemplate().update(sqlInsertSoggettoDsan, new Object[] {
						(dataEmissione!=null && dataEmissione.trim().length()>0)?java.sql.Date.valueOf(dataEmissione): null, 
						(elaboraDurc.getDataScadenza()!=null && elaboraDurc.getDataScadenza().toString().trim().length()>0)?java.sql.Date.valueOf(dataScadenza):null	,
				});
			}
		}
		

		return seq;
	}


	@Override
	public BigDecimal elaboraBdna(ElaboraRichiestaVO elaboraBdna, HttpServletRequest req) {

		String prf = "[RicercaBeneficiarioDAOImpl::setSchedaCliente]";
		LOG.info(prf + " BEGIN");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dataComunicazione = df.format(elaboraBdna.getDataComunicazione());
		String dataRicezione = df.format(elaboraBdna.getDataRicezione());
	
		String sqlSelect = "SELECT\r\n" + 
				"	ID_TRACCIATURA_RICH\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
				"WHERE\r\n" + 
				"	ID_RICHIESTA ="+ elaboraBdna.getIdRichiesta() +"\r\n" +
				"	AND \r\n" + 
				"ID_TIPO_COMUNICAZIONE ="+ elaboraBdna.getTipoComunicazione() +"\r\n" + 
				"	AND \r\n" + 
				"DESTINATARIO_MITTENTE ='"+ elaboraBdna.getDestinatarioMittente() +"' \r\n" + 
				"	AND \r\n" + 
				"NUMERO_PROTOCOLLO ='"+ elaboraBdna.getNumeroProtocollo() +"'\r\n" + 
				"	AND \r\n" + 
				"DT_COMUNICAZIONE_ESTERNA =TO_DATE('"+ dataComunicazione +"', 'yyyy/mm/dd')";

		String sqlSelectRichiesta = "SELECT\r\n" + 
				"	ID_RICHIESTA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_RICHIESTA ptr\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptr.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"	ptd.NUMERO_DOMANDA = '"+ elaboraBdna.getNumeroDomanda() + "'\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_TIPO_RICHIESTA = "+ elaboraBdna.getIdTipoRichiesta() + "\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_STATO_RICHIESTA <> "+ elaboraBdna.getIdStatoRichiesta();

		String sqlSelectSoggettoAntimafia = "SELECT\r\n" + 
				"	ID_SOGGETTO_ANTIMAFIA \r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_SOGGETTO_ANTIMAFIA ptsa\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptsa.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"ptd.NUMERO_DOMANDA ='"+ elaboraBdna.getNumeroDomanda().trim() + "'";



		//TRACCIATURA RICHIESTA
//		List<String> response = null;

//		response = getJdbcTemplate().queryForList(sqlSelect.toString(), String.class);
//
//		if(response.size() > 0) {
//			String sqlUpdate = "UPDATE PBANDI_T_TRACCIATURA_RICH \r\n" + 
//					"SET ID_UTENTE_AGG = "+ elaboraBdna.getIdUtenteAgg() + "\r\n" + 
//					"WHERE ID_TRACCIATURA_RICH = "+ response.get(0);
//			getJdbcTemplate().update(sqlUpdate);
//		}else{
//			String sqlInsert = "INSERT\r\n" + 
//					"	INTO\r\n" + 
//					"	PBANDI_T_TRACCIATURA_RICH (\r\n" + 
//					"	ID_TRACCIATURA_RICH,\r\n" + 
//					"	ID_RICHIESTA,\r\n" + 
//					"	ID_TIPO_COMUNICAZIONE,\r\n" + 
//					"	DESTINATARIO_MITTENTE,\r\n" + 
//					"	DT_COMUNICAZIONE_ESTERNA,\r\n" + 
//					"	NUMERO_PROTOCOLLO,\r\n" + 
//					"	MOTIVAZIONE,\r\n" + 
//					"	ID_UTENTE_INS,\r\n" + 
//					"	DT_INIZIO_VALIDITA\r\n" + 
//					") VALUES (\r\n" + 
//					"	SEQ_PBANDI_T_TRACC_RICHIESTA.nextval,\r\n" 
//					+ elaboraBdna.getIdRichiesta() + ","
//					+ elaboraBdna.getTipoComunicazione() + ","
//					+ "'" + elaboraBdna.getDestinatarioMittente() + "',"
//					+ "SYSDATE,"
//					+  "?,"
//					+ "?,"
//					+ elaboraBdna.getIdUtenteAgg() + ","
//					+ "SYSDATE" + ""
//					+ "\r\n" + 
//					")";
//			getJdbcTemplate().update(sqlInsert, new Object[] {
//				(elaboraBdna.getNumeroProtocollo()!=null)?elaboraBdna.getNumeroProtocollo(): null,
//				(elaboraBdna.getMotivazione()!=null)?elaboraBdna.getMotivazione():null	
//			});
//		}

		
		
		
		
		//RICHIESTA
		List<String> responseRichiesta = null;

		responseRichiesta = getJdbcTemplate().queryForList(sqlSelectRichiesta.toString(), String.class);

		if(responseRichiesta.size() > 0) {
			String sqlUpdateRichiesta = "UPDATE PBANDI_T_RICHIESTA \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraBdna.getIdUtenteAgg() + "\r\n" + 
					"WHERE ID_RICHIESTA = "+ responseRichiesta.get(0);
			getJdbcTemplate().update(sqlUpdateRichiesta);
		}else{
			StringBuilder update = new StringBuilder(); 
			update.append("UPDATE\r\n" + 
					"	PBANDI_T_RICHIESTA\r\n" + 
					"SET\r\n" + 
					"	ID_TIPO_RICHIESTA = "+ elaboraBdna.getIdTipoRichiesta() + ",\r\n" + 
					"	ID_STATO_RICHIESTA = "+ elaboraBdna.getIdStatoRichiesta()+ ",\r\n" + 
					"	ID_UTENTE_AGG = "+ elaboraBdna.getIdUtenteAgg() + ",\r\n");
			if(elaboraBdna.getIdStatoRichiesta().equals("4")) {
				update.append("	DT_CHIUSURA_RICHIESTA = SYSDATE,\r\n" );
			}
					update.append("	FLAG_URGENZA = '"+elaboraBdna.getFlagUrgenza()+"'\r\n" + 
							"WHERE\r\n" + 
							"	ID_RICHIESTA = "+ elaboraBdna.getIdRichiesta());
					
			getJdbcTemplate().update(update.toString());
		}

		//SOGGETTO_ANTIMAFIA
		List<BigDecimal> responseSoggettoAntimafia= null;
		responseSoggettoAntimafia = getJdbcTemplate().queryForList(sqlSelectSoggettoAntimafia.toString(), BigDecimal.class);
		BigDecimal seq = getJdbcTemplate().queryForObject("select SEQ_PBANDI_T_SOGG_ANTIMAFIA.nextval from dual", BigDecimal.class);
		if(responseSoggettoAntimafia.size() > 0) {
			String sqlUpdate = "UPDATE PBANDI_T_SOGGETTO_ANTIMAFIA \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraBdna.getIdUtenteAgg() + "\r\n" + 
					"WHERE ID_SOGGETTO_ANTIMAFIA = " + responseSoggettoAntimafia.get(0) ;
			getJdbcTemplate().update(sqlUpdate);
			return responseSoggettoAntimafia.get(0); 
		}else{
			String sqlInsert = "INSERT\r\n" + 
					"	INTO\r\n" + 
					"	PBANDI_T_SOGGETTO_ANTIMAFIA (\r\n" + 
					"	ID_SOGGETTO_ANTIMAFIA,\r\n" + 
					"	ID_DOMANDA,\r\n" + 
					"	ID_TIPO_ESITO_ANTIMAFIA,\r\n" + 
					"	DT_RICEZIONE_BDNA,\r\n" + 
					"	NUMER_PROTOCOLLO_RICEVUTA,\r\n" + 
					"	DT_EMISSIONE,\r\n" + 
					"	DT_SCADENZA_ANTIMAFIA,\r\n" + 
					"	NUM_PROTOCOLLO_PREFETTURA,\r\n" + 
					"	ID_UTENTE_AGG,\r\n" + 
					"	ID_UTENTE_INS,\r\n" + 
					"	DT_INIZIO_VALIDITA,\r\n" + 
					"	DT_FINE_VALIDITA\r\n" + 
					"	)\r\n" + 
					"	VALUES (\r\n" +seq +",(\r\n" + 
					"SELECT\r\n" + 
					"	ptd.ID_DOMANDA\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_T_DOMANDA ptd\r\n" + 
					"WHERE\r\n" + 
					"	ptd.NUMERO_DOMANDA ='"+ elaboraBdna.getNumeroDomanda() +"'),\r\n" + 
					"	NULL,\r\n" + 
					"TO_DATE('" + dataRicezione +"', 'yyyy/mm/dd'),\r\n"
					+ "'"+elaboraBdna.getNumeroProtocolloRicevuta() + "',\r\n" + 
					"	NULL,\r\n" + 
					"	NULL,\r\n" + 
					"	NULL,\r\n" + 
					elaboraBdna.getIdUtenteAgg()+ ",\r\n" + 
					elaboraBdna.getIdUtenteAgg()+ ",\r\n" + 
					"	SYSDATE,\r\n" + 
					"	NULL\r\n" + 
					"	)";

			getJdbcTemplate().update(sqlInsert);
		}
		return seq;
	}



	@Override
	public BigDecimal elaboraAntimafia(ElaboraRichiestaVO elaboraAntimafia, HttpServletRequest req) {
		String prf = "[RicercaBeneficiarioDAOImpl::setSchedaCliente]";
		LOG.info(prf + " BEGIN");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
		String dataScadenza = df2.format(elaboraAntimafia.getDataScadenza());
		String dataComunicazione = df.format(elaboraAntimafia.getDataComunicazione());
		String dataEmissione = df2.format(elaboraAntimafia.getDataEmissione());
		String dataRicezione = df3.format(elaboraAntimafia.getDataRicezione());
		if(!elaboraAntimafia.getEsito().equals("2")) {			
			dataScadenza = df2.format(elaboraAntimafia.getDataScadenza());
		}
		
		boolean res;
		/// se l'esito è si allora inserisco dentro la tabella il blocco relativo a quella domanda specifca
		if(elaboraAntimafia.getEsito().trim().equals("4")) {
			BloccoVO blocco = new BloccoVO(); 
			blocco.setIdCausaleBlocco(15);
			blocco.setIdSoggetto(BigDecimal.valueOf(Integer.parseInt(elaboraAntimafia.getNag())));
			blocco.setIdUtente(BigDecimal.valueOf(Integer.parseInt(elaboraAntimafia.getIdUtenteAgg())));
			//res = anaDao.insertBloccoDomanda(blocco, );
			res = anaDao.insertBloccoDomanda(blocco, elaboraAntimafia.getNag(), elaboraAntimafia.getNumeroDomanda());
			LOG.debug(prf + "insert blocco result " + res);
			
		}

		String sqlSelect = "SELECT\r\n" + 
				"	ID_TRACCIATURA_RICH\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
				"WHERE\r\n" + 
				"	ID_RICHIESTA = "+ elaboraAntimafia.getIdRichiesta() +"\r\n" +
				"	AND \r\n" + 
				"ID_TIPO_COMUNICAZIONE = "+ elaboraAntimafia.getTipoComunicazione() +"\r\n" + 
				"	AND \r\n" + 
				"DESTINATARIO_MITTENTE = '"+ elaboraAntimafia.getDestinatarioMittente() +"' \r\n" + 
				"	AND \r\n" + 
				"NUMERO_PROTOCOLLO = '"+ elaboraAntimafia.getNumeroProtocollo() +"'\r\n" + 
				"	AND \r\n" + 
				"DT_COMUNICAZIONE_ESTERNA = TO_DATE('"+ dataComunicazione +"', 'yyyy/mm/dd')";

		String sqlSelectRichiesta = "SELECT\r\n" + 
				"	ID_RICHIESTA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_RICHIESTA ptr\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptr.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"	ptd.NUMERO_DOMANDA = '"+ elaboraAntimafia.getNumeroDomanda() + "'\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_TIPO_RICHIESTA = "+ elaboraAntimafia.getIdTipoRichiesta() + "\r\n" + 
				"	AND \r\n" + 
				"ptr.ID_STATO_RICHIESTA <> "+ elaboraAntimafia.getIdStatoRichiesta();

		String sqlSelectSoggettoAntimafia = "SELECT\r\n" + 
				"	ID_SOGGETTO_ANTIMAFIA\r\n" + 
				"FROM\r\n" + 
				"	PBANDI_T_SOGGETTO_ANTIMAFIA ptsa\r\n" + 
				"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
				"	ptd.ID_DOMANDA = ptsa.ID_DOMANDA\r\n" + 
				"WHERE\r\n" + 
				"	ptd.NUMERO_DOMANDA = '"+ elaboraAntimafia.getNumeroDomanda()+"'\r\n" + 
				"	AND \r\n" + 
				"ptsa.DT_FINE_VALIDITA IS NULL" ;


		//TRACCIATURA RICHIESTA
		List<String> response = null;

//		response = getJdbcTemplate().queryForList(sqlSelect.toString(), String.class);
//
//		if(response.size() > 0) {
//			String sqlUpdate = "UPDATE PBANDI_T_TRACCIATURA_RICH \r\n" + 
//					"SET ID_UTENTE_AGG = "+ elaboraAntimafia.getIdUtenteAgg() + "\r\n" + 
//					"WHERE ID_TRACCIATURA_RICH = "+ response.get(0);
//			getJdbcTemplate().update(sqlUpdate);
//		}else{
//			String sqlInsert = "INSERT\r\n" + 
//					"	INTO\r\n" + 
//					"	PBANDI_T_TRACCIATURA_RICH (\r\n" + 
//					"	ID_TRACCIATURA_RICH,\r\n" + 
//					"	ID_RICHIESTA,\r\n" + 
//					"	ID_TIPO_COMUNICAZIONE,\r\n" + 
//					"	DESTINATARIO_MITTENTE,\r\n" + 
//					"	DT_COMUNICAZIONE_ESTERNA,\r\n" + 
//					"	NUMERO_PROTOCOLLO,\r\n" + 
//					"	MOTIVAZIONE,\r\n" + 
//					"	ID_UTENTE_INS,\r\n" + 
//					"	DT_INIZIO_VALIDITA\r\n" + 
//					") VALUES (\r\n" + 
//					"	SEQ_PBANDI_T_TRACC_RICHIESTA.nextval,?,?,?,trunc(SYSDATE),?,?,?,trunc(sysdate))";
//			//TO_DATE('" + dataComunicazione +"', 'yyyy/mm/dd') In caso serva di nuovo;
//			getJdbcTemplate().update(sqlInsert, new Object[] {
//					elaboraAntimafia.getIdRichiesta() , 
//					elaboraAntimafia.getTipoComunicazione(),
//					elaboraAntimafia.getDestinatarioMittente(),
//					elaboraAntimafia.getNumeroProtocollo(),
//					elaboraAntimafia.getMotivazione() ,
//					 elaboraAntimafia.getIdUtenteAgg()
//					
//			});
//		}

		//RICHIESTA
		List<String> responseRichiesta = null;

		responseRichiesta = getJdbcTemplate().queryForList(sqlSelectRichiesta.toString(), String.class);

		if(responseRichiesta.size() > 0) {
			String sqlUpdateRichiesta = "UPDATE PBANDI_T_RICHIESTA \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraAntimafia.getIdUtenteAgg() + "\r\n" + 
					"WHERE ID_RICHIESTA = "+ responseRichiesta.get(0);
			getJdbcTemplate().update(sqlUpdateRichiesta);
		}else{
			StringBuilder update = new StringBuilder();
			update.append("UPDATE\r\n" + 
					"	PBANDI_T_RICHIESTA\r\n" + 
					"SET\r\n" + 
					"	ID_TIPO_RICHIESTA = "+ elaboraAntimafia.getIdTipoRichiesta() + ",\r\n" + 
					"	ID_STATO_RICHIESTA = "+ elaboraAntimafia.getIdStatoRichiesta()+ ",\r\n" + 
					"	ID_UTENTE_AGG = "+ elaboraAntimafia.getIdUtenteAgg() + ",\r\n"); 
			update.append(	"	FLAG_URGENZA = 'S'\r\n" + 
					"WHERE\r\n" + 
					"	ID_RICHIESTA = "+ elaboraAntimafia.getIdRichiesta());
			getJdbcTemplate().update(update.toString());
				
		}
		
	

		BigDecimal seq; 
		//SOGGETTO_ANTIMAFIA
		List<BigDecimal> responseSoggettAntimafia= null;
		BigDecimal idDomanda  = getJdbcTemplate().queryForObject("select id_domanda from pbandi_t_domanda "
				+ "where numero_domanda='"+elaboraAntimafia.getNumeroDomanda()+"'" , BigDecimal.class);
		
		 seq = getJdbcTemplate().queryForObject("select SEQ_PBANDI_T_SOGG_ANTIMAFIA.nextval from dual", BigDecimal.class);
		responseSoggettAntimafia = getJdbcTemplate().queryForList(sqlSelectSoggettoAntimafia.toString(), BigDecimal.class);

	
		if(responseSoggettAntimafia.size() > 0) {
			String sqlUpdateSoggettoAntimafia = "UPDATE PBANDI_T_SOGGETTO_ANTIMAFIA \r\n" + 
					"SET ID_UTENTE_AGG = "+ elaboraAntimafia.getIdUtenteAgg() + "\r\n"
					+ ", ID_TIPO_ESITO_ANTIMAFIA=" + elaboraAntimafia.getEsito() +",\r\n"
					+ "DT_EMISSIONE="+ "TO_DATE('"+ dataEmissione +"', 'yyyy/mm/dd'),\r\n"
					+ "DT_SCADENZA_ANTIMAFIA="+"TO_DATE('"+ dataScadenza +"', 'yyyy/mm/dd'),\r\n" 
					+ "NUM_PROTOCOLLO_PREFETTURA=" + "'"+ elaboraAntimafia.getNumeroProtocolloPrefettura() +"'\r\n"+
					"WHERE ID_SOGGETTO_ANTIMAFIA =" + responseSoggettAntimafia.get(0);
			getJdbcTemplate().update(sqlUpdateSoggettoAntimafia);
			seq=responseSoggettAntimafia.get(0);
		}else{
			String sqlInsertSoggettoAntimafia = "INSERT\r\n" + 
					"	INTO\r\n" + 
					"	PBANDI_T_SOGGETTO_ANTIMAFIA (ID_SOGGETTO_ANTIMAFIA,\r\n" + 
					"	ID_TIPO_ESITO_ANTIMAFIA,\r\n" + 
					"	DT_EMISSIONE,\r\n" + 
					"	DT_SCADENZA_ANTIMAFIA,\r\n" + 
					"	NUM_PROTOCOLLO_PREFETTURA,\r\n" + 
					"	ID_UTENTE_INS,\r\n" + 
					"	DT_INIZIO_VALIDITA,\r\n" + 
					"	NUMER_PROTOCOLLO_RICEVUTA,\r\n" + 
					"	DT_RICEZIONE_BDNA,"
					+ " ID_DOMANDA)\r\n" + 
					"VALUES (\r\n" + seq+",\r\n" +
					"?,\r\n" + 
					"?,\r\n" + 
					"?,\r\n" + 
					"?,\r\n" + 
					"?,\r\n" + 
					"SYSDATE,\r\n" + 
					"?,\r\n" +
					"TO_DATE('"+ dataRicezione +"', 'yyyy/mm/dd'),\r\n" +idDomanda +
					")"
					;
			getJdbcTemplate().update(sqlInsertSoggettoAntimafia, new Object[] {
			  elaboraAntimafia.getEsito(),
			 (dataEmissione!=null && dataEmissione.trim().length()>0)?java.sql.Date.valueOf(dataEmissione): null, 
		     (elaboraAntimafia.getDataScadenza()!=null && elaboraAntimafia.getDataScadenza().toString().trim().length()>0)?java.sql.Date.valueOf(dataScadenza):null	, 
		    		 elaboraAntimafia.getNumeroProtocolloPrefettura() ,
		    		 elaboraAntimafia.getIdUtenteAgg(),
		    		 elaboraAntimafia.getNumeroProtocolloRicevuta()
			});
			
			
		}
		return seq;
	}

	@Override
	public List<String> getSuggestion(String value, String id, HttpServletRequest req) {

		String prf = "[RicercaBeneficiariCreditiDAOImpl::getSuggestion]";
		LOG.info(prf + " BEGIN");

		List<String> ben = null;

		try {
			StringBuilder query = new StringBuilder();
			switch(id) {

			case "1":
				query.append("SELECT\r\n" + 
						"	DISTINCT \r\n" + 
						"	pts.CODICE_FISCALE_SOGGETTO\r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_SOGGETTO pts\r\n" + 
						"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
						"	pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(CODICE_FISCALE_SOGGETTO) LIKE UPPER('%"+ value +"%')\r\n" + 
						"	AND \r\n" + 
						"	prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
						"	AND\r\n" + 
						"	prsd.ID_TIPO_BENEFICIARIO <> 4");
				break;

			case "2":
				query.append("SELECT\r\n" + 
						"	DISTINCT ptd.NUMERO_DOMANDA\r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_DOMANDA ptd\r\n" + 
						"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
						"	ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(ptd.NUMERO_DOMANDA) LIKE UPPER('%"+ value +"%')\r\n" + 
						"	AND \r\n" + 
						"	prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
						"	AND\r\n" + 
						"	prsd.ID_TIPO_BENEFICIARIO <> 4");
				break;

			case "3":
				query.append("SELECT\r\n" + 
						"	DISTINCT ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_DOMANDA ptd\r\n" + 
						"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
						"	ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(ptd.PROGR_BANDO_LINEA_INTERVENTO) LIKE UPPER('%"+ value +"%')\r\n" + 
						"	AND \r\n" + 
						"	prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
						"	AND\r\n" + 
						"	prsd.ID_TIPO_BENEFICIARIO <> 4");
				break;

			case "4":
				query.append("WITH selezione AS (\r\n" + 
						"SELECT\r\n" + 
						"	DISTINCT ptpf.COGNOME\r\n" + 
						"FROM\r\n" + 
						"	(\r\n" + 
						"	SELECT\r\n" + 
						"		max(ptpf0.ID_PERSONA_FISICA) id_persona_fisica,\r\n" + 
						"		ptr0.ID_RICHIESTA id_richiesta\r\n" + 
						"	FROM\r\n" + 
						"		PBANDI_T_RICHIESTA ptr0\r\n" + 
						"	LEFT JOIN PBANDI_T_UTENTE ptu ON\r\n" + 
						"		ptr0.ID_UTENTE_RICHIEDENTE = ptu.ID_UTENTE\r\n" + 
						"	LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf0 ON\r\n" + 
						"		ptu.ID_SOGGETTO = ptpf0.ID_SOGGETTO\r\n" + 
						"	GROUP BY\r\n" + 
						"		id_richiesta\r\n" + 
						") filtered\r\n" + 
						"INNER JOIN PBANDI_T_RICHIESTA ptr ON\r\n" + 
						"	filtered.id_richiesta = ptr.ID_RICHIESTA\r\n" + 
						"INNER JOIN PBANDI_T_PERSONA_FISICA ptpf ON\r\n" + 
						"	filtered.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n" + 
						"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
						"		ptr.ID_DOMANDA = prsd.ID_DOMANDA\r\n" + 
						"	AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n" + 
						"	AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
						"LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n" + 
						"		prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n" + 
						"	AND pts.ID_TIPO_SOGGETTO = 2\r\n" + 
						"LEFT JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON\r\n" + 
						"		ptr.ID_TIPO_RICHIESTA = pdtr.ID_TIPO_RICHIESTA\r\n" + 
						"LEFT JOIN PBANDI_D_STATO_RICHIESTA pdsr ON\r\n" + 
						"		ptr.ID_STATO_RICHIESTA = pdsr.ID_STATO_RICHIESTA\r\n" + 
						"LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n" + 
						"		ptr.ID_DOMANDA = ptd.ID_DOMANDA\r\n" + 
						"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON\r\n" + 
						"		ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n" + 
						"LEFT JOIN PBANDI_T_PROGETTO ptp ON\r\n" + 
						"		ptr.ID_DOMANDA = ptp.ID_DOMANDA\r\n" + 
						"LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON\r\n" + 
						"		prsd.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n" + 
						"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON\r\n" + 
						"	prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n" + 
						"	AND prsds.ID_TIPO_SEDE = 1\r\n" + 
						"LEFT JOIN PBANDI_T_SEDE pts2 ON\r\n" + 
						"	prsds.ID_SEDE = pts2.ID_SEDE\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(ptpf.COGNOME) LIKE UPPER('"+ value +"%')\r\n" + 
						"	AND \r\n" + 
						"	prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
						"	AND\r\n" + 
						"	prsd.ID_TIPO_BENEFICIARIO <> 4)\r\n" + 
						"SELECT\r\n" + 
						"	*\r\n" + 
						"FROM\r\n" + 
						"	selezione\r\n" + 
						"");
				break;

			case "5":
				query.append("SELECT\r\n" + 
						"	DISTINCT \r\n" + 
						"	ptp.CODICE_VISUALIZZATO\r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_PROGETTO ptp\r\n" + 
						"LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n" + 
						"	ON \r\n" + 
						"	prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(ptp.CODICE_VISUALIZZATO) LIKE UPPER('"+ value +"%')");
				break;

			case "6":
				query.append("SELECT\r\n" + 
						"	DISTINCT \r\n" + 
						"	pttr.DESTINATARIO_MITTENTE\r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(pttr.DESTINATARIO_MITTENTE) LIKE UPPER('"+ value +"%')");
				break;

			case "7":
				query.append("SELECT\r\n" + 
						"	DISTINCT \r\n" + 
						"	pttr.NUMERO_PROTOCOLLO \r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(pttr.NUMERO_PROTOCOLLO) LIKE UPPER('"+ value +"%')");
				break;

			case "8":
				query.append("SELECT\r\n" + 
						"	DISTINCT \r\n" + 
						"	pttr.MOTIVAZIONE \r\n" + 
						"FROM\r\n" + 
						"	PBANDI_T_TRACCIATURA_RICH pttr\r\n" + 
						"WHERE\r\n" + 
						"	UPPER(pttr.MOTIVAZIONE) LIKE UPPER('"+ value +"%')");
				break;

			default:				
				LOG.error("There was an error with ids");				
			}


			ben = getJdbcTemplate().queryForList(query.toString(), String.class);


		}

		catch (RecordNotFoundException e)
		{
			throw e;
		}

		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read BeneficiarioCreditiVO - suggestion", e);
			throw new ErroreGestitoException("DaoException while trying to read BeneficiarioCreditiVO - suggestion", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}

		return ben;
	}

	@Override
	public Boolean salvaUploadAntiMafia(
			org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput multipartFormData) {
		String prf = "[GestioneRichiesteDAOImpl::salvaUploadAntiMafia]";
		LOG.info(prf + " BEGIN");
		Boolean result = true; 
		try {
			Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
			ID_UTENTE = idUtente;
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			BigDecimal idTarget = multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null); 
			String numeroDomanda = multipartFormData.getFormDataPart("numeroDomanda", String.class, null); 
			BigDecimal idDomanda  = getJdbcTemplate().queryForObject("select id_domanda from pbandi_t_domanda "
						+ "where numero_domanda='"+numeroDomanda+"'" , BigDecimal.class);
			BigDecimal idSoggetto = multipartFormData.getFormDataPart("idSoggetto", BigDecimal.class, null); 
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			ElaboraRichiestaVO elaboraVO = objectMapper.readValue(multipartFormData.getFormDataPart("richiesta", String.class, null), ElaboraRichiestaVO.class);
			
			LOG.info(prf +"ID DOMANDA: "+ idDomanda);
			LOG.info(prf +"ID UTENTE: "+ idUtente);
			LOG.info(prf +"NOME FILE: "+ nomeFile);
			
			String getEntita ="SELECT id_entita\r\n"
					+ "FROM PBANDI_C_ENTITA\r\n"
					+ "WHERE nome_entita ='PBANDI_T_SOGGETTO_ANTIMAFIA'\r\n";
			
			BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = "+x);
				}
			}
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) 41;
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=41", String.class);
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdDomanda(idDomanda);
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(41));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
			documentoIndexVO.setIdEntita(numEntita);			
			documentoIndexVO.setIdSoggettoBenef(idSoggetto); //PBANDI_T_PROGETTO
			//documentoIndexVO.setIdTarget(new BigDecimal(idProgetto));				//id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
			
		   if(elaboraVO.getIdStatoRichiesta().trim().equals("3") || elaboraVO.getIdStatoRichiesta().trim().equals("4")) {
				// chiamata al servizio di finistr
				callToFinistr(elaboraVO, nomeFile, descBreveTipoDocIndex);
				
		 }
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BeneficiarioCreditiVO - suggestion", e);
			result  = false;
		}
		return result;
	}

	@Override
	public Boolean salvaUploadDurc(MultipartFormDataInput multipartFormData) {	
		String prf = "[GestioneRichiesteDAOImpl::salvaUploadDurc]";
		LOG.info(prf + " BEGIN");
		Boolean result; 
		try {
			Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			BigDecimal idTarget = multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null); 
			String numeroDomanda = multipartFormData.getFormDataPart("numeroDomanda", String.class, null); 
			BigDecimal idDomanda  = getJdbcTemplate().queryForObject("select id_domanda from pbandi_t_domanda "
						+ "where numero_domanda='"+numeroDomanda+"'" , BigDecimal.class); 
			BigDecimal idSoggetto = multipartFormData.getFormDataPart("idSoggetto", BigDecimal.class, null); 
			int idTipoRichiesta= multipartFormData.getFormDataPart("idTipoRichiesta", int.class, null);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			ElaboraRichiestaVO elaboraVO = objectMapper.readValue(multipartFormData.getFormDataPart("richiesta", String.class, null), ElaboraRichiestaVO.class);
			String descBreveTipoDocIndex= null;
			
			// 1 durc 2 dsan
			if(idTipoRichiesta ==1) {
				String getEntita ="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_SOGGETTO_DURC'\r\n";
				
				BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
						
				// Legge il file firmato dal multipart.		
				Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
				List<InputPart> listInputPart = map.get("file");
				
				if (listInputPart == null) {
					LOG.info("listInputPart NULLO");
				} else {
					LOG.info("listInputPart SIZE = "+listInputPart.size());
				}
				
				for (InputPart i : listInputPart) {
					MultivaluedMap<String, String> m = i.getHeaders();
					Set<String> s = m.keySet();
					for (String x : s) {
						LOG.info("SET = "+x);
					}
				}
				
				FileDTO file = new FileDTO(); 
				file.setBytes(FileUtil.getBytesFromFile(filePart));
						//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
				
				//Long idtipoindex= (long) 39;
				 descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
						+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=39", String.class);	
				if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
					throw new Exception("Tipo documento index non trovato.");					
				
				
				Date currentDate = new Date(System.currentTimeMillis());
				
				DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
				documentoIndexVO.setIdDomanda(idDomanda);
				documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(39));
				documentoIndexVO.setNomeFile(nomeFile);
				documentoIndexVO.setIdTarget(idTarget);
				documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
				documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
				documentoIndexVO.setIdEntita(numEntita);
				documentoIndexVO.setIdSoggettoBenef(idSoggetto);
				documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
				//PBANDI_T_PROGETTO
				//documentoIndexVO.setIdTarget(new BigDecimal(idProgetto));				//id della PBANDI_T_PROGETTO
				documentoIndexVO.setRepository(descBreveTipoDocIndex);
				documentoIndexVO.setUuidNodo("UUID");
				
				// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
			result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
						
				
			} else {
				
				String getEntita ="SELECT id_entita\r\n"
						+ "FROM PBANDI_C_ENTITA\r\n"
						+ "WHERE nome_entita ='PBANDI_T_SOGGETTO_DSAN'\r\n";
				
				BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
						
				// Legge il file firmato dal multipart.		
				Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
				List<InputPart> listInputPart = map.get("file");
				
				if (listInputPart == null) {
					LOG.info("listInputPart NULLO");
				} else {
					LOG.info("listInputPart SIZE = "+listInputPart.size());
				}
				
				for (InputPart i : listInputPart) {
					MultivaluedMap<String, String> m = i.getHeaders();
					Set<String> s = m.keySet();
					for (String x : s) {
						LOG.info("SET = "+x);
					}
				}
				
				FileDTO file = new FileDTO(); 
				file.setBytes(FileUtil.getBytesFromFile(filePart));
						//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
				
				//Long idtipoindex= (long) 40;
				 descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
						+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=40", String.class);	
				if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
					throw new Exception("Tipo documento index non trovato.");					
				
				
				Date currentDate = new Date(System.currentTimeMillis());
				
				DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
				documentoIndexVO.setIdDomanda(idDomanda);
				documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(40));
				documentoIndexVO.setNomeFile(nomeFile);
				documentoIndexVO.setIdTarget(idTarget);
				documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
				documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
				documentoIndexVO.setIdEntita(numEntita);
				documentoIndexVO.setIdSoggettoBenef(idSoggetto);
				documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
				//PBANDI_T_PROGETTO
				//documentoIndexVO.setIdTarget(new BigDecimal(idProgetto));				//id della PBANDI_T_PROGETTO
				documentoIndexVO.setRepository(descBreveTipoDocIndex);
				documentoIndexVO.setUuidNodo("UUID");
				
				// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
			result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
			}
		   if(elaboraVO.getIdStatoRichiesta().trim().equals("3") || elaboraVO.getIdStatoRichiesta().trim().equals("4")) {
					// chiamata al servizio di finistr
					callToFinistr(elaboraVO, nomeFile, descBreveTipoDocIndex);
					
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			result = false; 
		}
	
		
		
		
		
		return result;
	}

	@Override
	public Boolean salvaUploadBdna(MultipartFormDataInput multipartFormData) {
		String prf = "[GestioneRichiesteDAOImpl::salvaUploadBdna]";
		LOG.info(prf + " BEGIN");
		Boolean result; 
		
		
		
		try {
			Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			BigDecimal idTarget = multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null); 
			String numeroDomanda = multipartFormData.getFormDataPart("numeroDomanda", String.class, null); 
			BigDecimal idDomanda  = getJdbcTemplate().queryForObject("select id_domanda from pbandi_t_domanda "
						+ "where numero_domanda='"+numeroDomanda+"'" , BigDecimal.class); 
			BigDecimal idSoggetto = multipartFormData.getFormDataPart("idSoggetto", BigDecimal.class, null); 
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			ElaboraRichiestaVO elaboraVO = objectMapper.readValue(multipartFormData.getFormDataPart("richiesta", String.class, null), ElaboraRichiestaVO.class);

			
			String getEntita ="SELECT id_entita\r\n"
					+ "FROM PBANDI_C_ENTITA\r\n"
					+ "WHERE nome_entita ='PBANDI_T_SOGGETTO_ANTIMAFIA'\r\n";
			
			BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = "+x);
				}
			}
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) 40;
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=43", String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdDomanda(idDomanda);
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(43));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(numEntita);
			documentoIndexVO.setIdSoggettoBenef(idSoggetto);
			documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
			//PBANDI_T_PROGETTO
			//documentoIndexVO.setIdTarget(new BigDecimal(idProgetto));				//id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
		    
		    // 
		    if(elaboraVO.getIdStatoRichiesta().trim().equals("3") || elaboraVO.getIdStatoRichiesta().trim().equals("4")) {
				// chiamata al servizio di finistr
				callToFinistr(elaboraVO, nomeFile, descBreveTipoDocIndex);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		

		return result;
	}

	@Override
	public Object annullaRichiesta(ElaboraRichiestaVO elaboraRichiesta) {
		String prf = "[GestioneRichiesteDAOImpl::salvaUploadBdna]";
		LOG.info(prf + " BEGIN");
		
		try {
			
			getJdbcTemplate().update("update PBANDI_T_RICHIESTA set DT_CHIUSURA_RICHIESTA=trunc(sysdate), ID_STATO_RICHIESTA =5 where id_richiesta=?", new Object[] {
					elaboraRichiesta.getIdRichiesta()	
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
		
		
		return 0;
	}

	@Override
	public Object elencoTipoRichieste() {
		String prf = "[GestioneRichiesteDAOImpl::salvaUploadBdna]";
		LOG.info(prf + " BEGIN");
		List<ValueDTO> elenco = new ArrayList<ValueDTO>();
		try {
			String getStatoRichiesta="	select * from pbandi_d_tipo_richiesta\r\n"
					+ "	where id_tipo_richiesta <> 4 order by desc_tipo_richiesta"; 
			
			RowMapper<ValueDTO> lista =  new RowMapper<ValueDTO>() {
				
				@Override
				public ValueDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ValueDTO val  = new ValueDTO();
							val.setValue(rs.getInt("id_tipo_richiesta"));
							val.setViewValue(rs.getString("desc_tipo_richiesta"));
					return val;
				}
			};
			elenco = getJdbcTemplate().query(getStatoRichiesta, lista); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elenco;
	}

	@Override
	public boolean callToFinistr(ElaboraRichiestaVO elabora, String nomeFile, String pathDocumento) {
		Long idChiamanta = null;
		try {
			
			String urlServ = FINSERVREST_ENDPOINT_BASE + FINSERVREST_SERV_RICEZANTIMAFIADURC + "getAntimafiaDurc";
			
			
			LOG.debug("urlServ="+urlServ);
		    

			// recupero del numero domanda: 
			String numDomanda= getNumericPart(elabora.getNumeroDomanda());
			
			//recupero idBando
			BigDecimal idBando = getJdbcTemplate().queryForObject("  SELECT prbli.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "  from pbandi_t_domanda ptd \r\n"
					+ "  left join pbandi_r_bando_linea_intervent prbli on prbli.progr_bando_linea_intervento = ptd.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "  WHERE ptd.NUMERO_DOMANDA =?", BigDecimal.class,  new Object[] {
						elabora.getNumeroDomanda()	
					});
			// tipoRichiesta 
			String tipoRichiesta = getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_RICHIESTA \r\n"
					+ "from pbandi_d_tipo_richiesta where id_tipo_richiesta ="+ elabora.getIdTipoRichiesta(), String.class);
			
			// codicefiscale 
			String codiceFiscale= getJdbcTemplate().queryForObject("select CODICE_FISCALE_SOGGETTO from PBANDI_T_SOGGETTO pts  where id_soggetto ="+elabora.getNag(), String.class);
			
			String esito = null; 
			if(elabora.getEsito()!=null && elabora.getEsito().trim().length()>0) {				
				// esito qua abbiamo solo l'id da elabora.getEsito()
				esito = getJdbcTemplate().queryForObject("SELECT pdter.DESC_ESITO_RICHIESTE \r\n"
						+ "FROM PBANDI_D_TIPO_ESITO_RICHIESTE pdter\r\n"
						+ "WHERE pdter.ID_TIPO_ESITO_RICHIESTE ="+ elabora.getEsito(), String.class);
			}
			// statorichiesta id da recuperare  
			String statoRichiesta = getJdbcTemplate().queryForObject("SELECT pdsr.DESC_STATO_RICHIESTA  \r\n"
					+ "FROM PBANDI_D_STATO_RICHIESTA  pdsr\r\n"
					+ "WHERE pdsr.ID_STATO_RICHIESTA = "+ elabora.getIdStatoRichiesta(), String.class);
			
			
			// data ricezione documentazione 
			
			String dataRicezione=null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			if(elabora.getDataRicezione()!=null) {
				dataRicezione = dt.format(elabora.getDataRicezione());
			}
			// data scadenza
			String dataScadenza=null; 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			if(elabora.getDataScadenza()!=null) {
				dataScadenza = dt1.format(elabora.getDataScadenza());
			}
			// data chiusura  per noi la data chiusura dovrebbe essere la data ordiena e non la data di scadenza  
			String dataChiusura=null; 
			 SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd"); 
//			if(elabora.getDataScadenza()!=null) {
//				// devo mettere sempre la data  quì
//				//dt2.format(new Date());
//				//dataChiusura = dt2.format(elabora.getDataScadenza());
//			}
			//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-DD");
			LocalDate now = LocalDate.now();
			dataChiusura = dt2.format(java.sql.Date.valueOf(now));
			
		    
//		    Response getRicezioneAntimafiaDurc( @QueryParam("domanda") Integer domanda, @QueryParam("idBando") Integer idBando,
//		    		@QueryParam("tipoRichiesta") String tipoRichiesta, @QueryParam("codiceFiscaleImpresa") String codiceFiscaleImpresa, 
//		    		@QueryParam("esitoRichiesta") String esitoRichiesta, @QueryParam("statoRichiesta") String statoRichiesta,
//		    		@QueryParam("dataRecezioneDocumentazione") String dataRecezioneDocumentazione,
//		    		@QueryParam("dataScadenza") String dataScadenza, @QueryParam("dataChiusura") String dataChiusura,
//		    		@QueryParam("urgenza") String urgenza, @QueryParam("pathDocumento") String pathDocumento, @QueryParam("nomeFile") 
		    
			
			//10.1.2 Richiamo di API REST tramite JAX-RS client standard
			Client client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target(urlServ)
			.queryParam("domanda",numDomanda)
			.queryParam("idBando",idBando)
			.queryParam("tipoRichiesta",tipoRichiesta)
			.queryParam("codiceFiscaleImpresa",codiceFiscale)
			.queryParam("esitoRichiesta",esito)
			.queryParam("statoRichiesta", statoRichiesta) 
			.queryParam("dataRecezioneDocumentazione", dataRicezione)
			.queryParam("dataScadenza", dataScadenza) 
			.queryParam("dataChiusura", dataChiusura) 
			.queryParam("urgenza", (elabora.getFlagUrgenza().trim().equals("N"))?"Ordinario" : "Urgente")
			.queryParam("pathDocumento", pathDocumento)
			.queryParam("nomeFile",nomeFile);
			
			 idChiamanta = logMonitoraggioService.trackCallPre(40L,ID_UTENTE, "R", 12L, 53L,numDomanda,idBando,tipoRichiesta,
					codiceFiscale,esito,statoRichiesta,dataRicezione,dataScadenza,dataChiusura);
			
			Response resp = (Response) target.request().get();
			
			
			LOG.info(resp);
			
			AntimafiaDurcResponse dr = resp.readEntity(AntimafiaDurcResponse.class);
			if(resp.getStatus() == 200) {
				logMonitoraggioService.trackCallPost(idChiamanta, "OK" , ""+resp.getStatus(),"", dr);
			}else {
				logMonitoraggioService.trackCallPost(idChiamanta, "KO" , ""+resp.getStatus(),"", dr);
			}
			
			// domanda 
			LOG.info("risposta durc antimafia:  "+dr);
			
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(idChiamanta, "KO" ,e.getMessage(), "500", null);
			e.printStackTrace();
			LOG.error(e);
			
		}
		

	
		
		
		return false;
	}

	public static String getNumericPart(String numeroDomanda) {
	    if (numeroDomanda.startsWith("FD")) {
	        return numeroDomanda.substring(2).replaceAll("[^\\d.]", "");
	    } else if (numeroDomanda.matches("\\d+")) {
	        return numeroDomanda;
	    } else {
	        return null;
	    }
	}

	@Override
	public String getPreviousState(String idRichiesta) {
		
		String statoPrecedente = null ; 
		
		try {
			
			statoPrecedente = getJdbcTemplate().queryForObject("SELECT ptr.ID_STATO_RICHIESTA  \r\n"
															  + " FROM PBANDI_T_RICHIESTA  ptr\r\n"
															  + "    WHERE ptr.ID_RICHIESTA  = "+ idRichiesta, String.class); 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return statoPrecedente;
	}

	@Override
	public ElaboraRichiestaVO getRichiestaUltimaRichiestaDurc(String codiceFiscale) {
		String prf = "[GestioneRichiesteDAOImpl::getRichiesta]";		
		LOG.info(prf + " BEGIN " );

		List<ElaboraRichiestaVO> response = new ArrayList<ElaboraRichiestaVO>();
		
		try {
			
		
		String query = "SELECT *\r\n"
				+ "FROM PBANDI_T_SOGGETTO_DURC ptsd \r\n"
				+ "LEFT JOIN pbandi_t_soggetto pts ON pts.ID_SOGGETTO  = ptsd.ID_SOGGETTO \r\n"
				+ "WHERE pts.CODICE_FISCALE_SOGGETTO ='"+codiceFiscale+"'\r\n"
				+ "AND ptsd.DT_SCADENZA > trunc(SYSDATE) \r\n"
				+ "ORDER BY ptsd.ID_SOGGETTO_DURC DESC ";		

			
		
			RowMapper<ElaboraRichiestaVO> rowMap =  new RowMapper<ElaboraRichiestaVO>() {
			
				@Override
				public ElaboraRichiestaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ElaboraRichiestaVO v = new ElaboraRichiestaVO();
					v.setDataScadenza(rs.getDate("DT_SCADENZA"));
					v.setEsito(rs.getString("ID_TIPO_ESITO_DURC"));
					v.setNumeroProtocolloInps(rs.getString("NUM_PROTOCOLLO_INPS"));
					v.setDataEmissione(rs.getDate("DT_EMISSIONE_DURC"));
					return v;
				}
			};

		response = getJdbcTemplate().query(query.toString(), rowMap);


		LOG.info("Result:\n" + response);
		LOG.debug("Result:\n" + response);
		LOG.info(prf + "END" );
		LOG.debug(prf + "END");

		} catch (Exception e) {
			LOG.error(e);
		}
		
		return (response.size()>0) ? response.get(0) : null;
	
	}

	@Override
	public List<ElaboraRichiestaVO> getRichiesteSoggetto(String codiceFiscale, BigDecimal idTipoRichiesta) {
		String prf = "[GestioneRichiesteDAOImpl::getRichiesta]";		
		LOG.info(prf + " BEGIN " );

		List<ElaboraRichiestaVO> response = new ArrayList<ElaboraRichiestaVO>();

		try {
	
		String query = "SELECT\r\n"
				+ "	ptr.*, \r\n"
				+ "	pdtr.DESC_TIPO_RICHIESTA, ptd.numero_domanda \r\n"
				+ "FROM\r\n"
				+ "	PBANDI_T_RICHIESTA ptr\r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptr.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
				+ "LEFT JOIN pbandi_r_soggetto_domanda prsd ON prsd.ID_DOMANDA  = ptr.ID_DOMANDA\r\n"
				+ "LEFT JOIN pbandi_t_soggetto pts ON pts.ID_SOGGETTO  = prsd.ID_SOGGETTO \r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON pdtr.ID_TIPO_RICHIESTA = ptr.ID_TIPO_RICHIESTA \r\n"
				+ "WHERE\r\n"
				+ "	prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "	AND prsd.ID_TIPO_ANAGRAFICA  = 1\r\n"
				+ "	AND pts.CODICE_FISCALE_SOGGETTO  = ?\r\n"
				+ "	AND ptr.ID_TIPO_RICHIESTA =?";		

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);

		RowMapper<ElaboraRichiestaVO> rowMap =  new RowMapper<ElaboraRichiestaVO>() {
			
			@Override
			public ElaboraRichiestaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElaboraRichiestaVO v = new ElaboraRichiestaVO();
				v.setIdRichiesta(rs.getString("ID_RICHIESTA"));
				v.setIdStatoRichiesta(rs.getString("ID_STATO_RICHIESTA"));
				v.setIdTipoRichiesta(rs.getString("DESC_TIPO_RICHIESTA"));
				v.setIdDomanda(rs.getString("ID_DOMANDA"));
				v.setNumeroDomanda(rs.getString("numero_domanda"));
				return v;
			}
		};
		
		response = getJdbcTemplate().query(query.toString(), rowMap, new Object[] {codiceFiscale, idTipoRichiesta});

		
		} catch (Exception e) {
			LOG.error(e);
		}
		return response;
	}

	@Override
	public ElaboraRichiestaVO getRichiestaUltimaRichiestaDSan(String codiceFiscale) {
		String prf = "[GestioneRichiesteDAOImpl::getRichiestaUltimaRichiestaDSan]";		
		LOG.info(prf + " BEGIN " );

		List<ElaboraRichiestaVO> response = new ArrayList<ElaboraRichiestaVO>();

		try {
			
	
		String query = "SELECT ptsd.*\r\n"
				+ "FROM PBANDI_T_SOGGETTO_DSAN ptsd\r\n"
				+ "LEFT JOIN pbandi_r_soggetto_domanda prsd ON prsd.ID_DOMANDA  = ptsd.ID_DOMANDA\r\n"
				+ "LEFT JOIN pbandi_t_soggetto pts ON pts.ID_SOGGETTO  = prsd.ID_SOGGETTO \r\n"
				+ "WHERE pts.CODICE_FISCALE_SOGGETTO ='"+codiceFiscale+"'\r\n"
				+ " AND ptsd.DT_SCADENZA >= trunc(sysdate)\r\n"
				+ "ORDER BY ptsd.ID_SOGGETTO_DSAN DESC ";		

		LOG.info("Query:\n" + query);
		LOG.debug("Query:\n" + query);
		
		RowMapper<ElaboraRichiestaVO> rowMap =  new RowMapper<ElaboraRichiestaVO>() {
			
			@Override
			public ElaboraRichiestaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElaboraRichiestaVO v = new ElaboraRichiestaVO();
				v.setDataScadenza(rs.getDate("DT_SCADENZA"));
				v.setEsito(rs.getString("ID_ESITO_DSAN"));
				v.setDataEmissione(rs.getDate("DT_EMISSIONE_DSAN"));
				
				return v;
			}
		};

		response = getJdbcTemplate().query(query.toString(), rowMap);
		
		} catch (Exception e) {
			LOG.error(e);
		}

		return (response.size()>0) ? response.get(0) :null;
	}

	@Override
	public ElaboraRichiestaVO getRichiesteAntimafia(String idDomanda) {
		String prf = "[GestioneRichiesteDAOImpl::getRichiesteAntimafia]";		
		LOG.info(prf + " BEGIN " );

		List<ElaboraRichiestaVO> response = new ArrayList<ElaboraRichiestaVO>();
		
		try {
			
		String query = "SELECT ptsa.*\r\n"
				+ "FROM PBANDI_T_SOGGETTO_ANTIMAFIA ptsa\r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA  ptd ON ptd.ID_DOMANDA  = ptsa.ID_DOMANDA \r\n"
				+ "WHERE ptd.NUMERO_DOMANDA  =?\r\n"
				+ "	and ptsa.DT_FINE_VALIDITA is null \r\n"
				+ "ORDER BY ptsa.ID_SOGGETTO_ANTIMAFIA DESC";		


			RowMapper<ElaboraRichiestaVO> rowMap =  new RowMapper<ElaboraRichiestaVO>() {
			
			@Override
			public ElaboraRichiestaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElaboraRichiestaVO v = new ElaboraRichiestaVO();
				v.setEsito(rs.getString("ID_TIPO_ESITO_ANTIMAFIA"));
				v.setDataScadenza(rs.getDate("DT_SCADENZA_ANTIMAFIA"));	
				v.setIdRichiesta(rs.getString("ID_SOGGETTO_ANTIMAFIA"));
				v.setDataEmissione(rs.getDate("DT_EMISSIONE"));
				v.setIdDomanda(rs.getString("ID_DOMANDA"));
				return v;
			}
		};

		response = getJdbcTemplate().query(query.toString(), rowMap, new Object[] {idDomanda});
		
		} catch (Exception e) {
			LOG.error(e);
		}

		return (response.size()>0) ? response.get(0) : null;
	}

	@Override
	public Boolean annullaRichiestaAntimafia(String idRichiesta, HttpServletRequest req) {
		String prf = "[GestioneRichiesteDAOImpl::annullaRichiestaAntimafia]";		
		LOG.info(prf + " BEGIN " );

		Boolean result = true; 
		try {
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			
			String update ="UPDATE PBANDI_T_SOGGETTO_ANTIMAFIA ptsa \r\n"
					+ "SET PTSA .DT_FINE_VALIDITA = TRUNC(SYSDATE),\r\n"
					+ "ptsa.ID_UTENTE_AGG  =?\r\n"
					+ "WHERE ptsa.ID_SOGGETTO_ANTIMAFIA =? ";
			getJdbcTemplate().update(update, new Object[] {
				userInfoSec.getIdUtente(), idRichiesta	
			});
			
		} catch (Exception e) {
			result  = false ;
			LOG.error(e);
		}
		
		
		return result;
	}
	
}
