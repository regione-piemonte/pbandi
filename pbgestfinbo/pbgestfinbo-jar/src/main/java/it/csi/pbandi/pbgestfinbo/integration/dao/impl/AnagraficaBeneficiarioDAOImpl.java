/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.findom.finservrest.dto.RicezioneBloccoSbloccoResponse;
import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.AtecoDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.FormaGiuridicaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.RuoloDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.SezioneSpecialeDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.DettaglioImpresaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.ElencoRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.ElencoRowMapperPF;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.FormaGiuridicaDTORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.DatiAreaCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.DatidimensioneImpresaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.IndirizzoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SistemaDiBloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SoggettiCorrelatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AltriDatiDsan;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagAltriDati_MainVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AtlanteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DettaglioImpresaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElencoDomandeProgettiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.IscrizioneRegistroVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StatoDomandaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.RecapitoVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoDomandaSedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIndirizzoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIscrizioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSedeVO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@Service
public class AnagraficaBeneficiarioDAOImpl extends JdbcDaoSupport implements AnagraficaBeneficiarioDAO{
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	private  static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.finservrest");
	private static final String FINSERVREST_ENDPOINT_BASE = RESOURCE_BUNDLE.getString("finservrest.endpointService");
	private static final String FINSERVREST_SERV_RICEZSBLOCCOANAGRAF = "/api/ricezioneBloccoSbloccoAnagrafico/ricezione";
	private static final String FINSERVREST_SERV_RICEZ_MODIFICHE_ANAGRAFICHE = "/api/ricezioneModificheAnagrafiche/modificaAnagrafica";
	
	private static String url = FINSERVREST_ENDPOINT_BASE + FINSERVREST_SERV_RICEZSBLOCCOANAGRAF; 
	private static String urlAnagrafica = FINSERVREST_ENDPOINT_BASE + FINSERVREST_SERV_RICEZ_MODIFICHE_ANAGRAFICHE; 
	@Autowired
	public AnagraficaBeneficiarioDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	SuggestRevocheDAO suggestRevocaDao; 
	@Autowired 
	PropostaRevocaDAO propostaRevocaDao;
	
	@Autowired
	private  it.csi.pbandi.pbgestfinbo.integration.dao.GestioneLog logMonitoraggioService;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////SERVIZI PER RICERCA E DETTAGLIO /////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BigDecimal checkProgetto(String numeroDomanda) {
		BigDecimal progetto=null;
		try {
			String query = "SELECT\r\n"
					+ "    prsp.ID_PROGETTO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "WHERE\r\n"
					+ "    ptd.NUMERO_DOMANDA = '"+numeroDomanda+"'\r\n"
					+ "    AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
					+ " and rownum <2";


			progetto = getJdbcTemplate().queryForObject(query,  BigDecimal.class);
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::checkProgetto] Exception " + e.getMessage());
			return null; 
		}
		
		
		return progetto;
	}


	private int checkDomanda(Long idSoggetto) {

		String query = "SELECT COUNT(*) AS domanda\r\n"
				+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
				+ "WHERE prsd.ID_SOGGETTO = :idSoggetto \r\n"
				+ "AND prsd.ID_TIPO_ANAGRAFICA = 1 \r\n"
				+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4";

		Object[] args = new Object[]{idSoggetto};

		int[] types = new int[]{Types.NUMERIC};

		int domanda = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Integer>()
		{
			@Override
			public  Integer extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				Integer result = 0;

				while (rs.next())
				{
					result = rs.getInt("DOMANDA");
				}
				return result;
			}
		});
		return domanda;
	}

	private Long getIdEnteGiuridicoFromProgetto(Long idSoggetto) {

		String query = "WITH selezione AS (\r\n"
				+ "SELECT prsp.ID_ENTE_GIURIDICO \r\n"
				+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
				+ "WHERE  prsp.ID_TIPO_ANAGRAFICA = 1 \r\n"
				+ "AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
				+ "AND prsp.ID_SOGGETTO = :idSoggetto ORDER BY prsp.ID_PROGETTO desc) SELECT * FROM selezione where rownum < 2";

		Object[] args = new Object[]{idSoggetto};

		int[] types = new int[]{Types.NUMERIC};

		return getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Long>()
		{
			@Override
			public  Long extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				Long result = 0l;

				while (rs.next())
				{
					result = rs.getLong("ID_ENTE_GIURIDICO");
				}
				return result;
			}
		});
	}

	private Long getIdEnteGiuridicoFromDomanda(Long idSoggetto) {

		String query = "WITH selezione AS (\r\n"
				+ "SELECT prsd.ID_ENTE_GIURIDICO \r\n"
				+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
				+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 \r\n"
				+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 \r\n"
				+ "AND prsd.ID_SOGGETTO = :idSoggetto ORDER by prsd.ID_DOMANDA desc) SELECT * FROM selezione where rownum < 2";

		Object[] args = new Object[]{idSoggetto};

		int[] types = new int[]{Types.NUMERIC};


		return getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Long>()
		{
			@Override
			public  Long extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				Long result = 0l;

				while (rs.next())
				{
					result = rs.getLong("ID_ENTE_GIURIDICO");
				}
				return result;
			}
		});
	}

	@Override
	public List<AnagraficaBeneficiarioVO> getAnagBeneficiario(Long idSoggetto,Long idProgetto, String numDomanda,  HttpServletRequest req)
			throws DaoException {

		String prf = "[AnagraficaBeneficiarioDAOImpl::getAnagBeneficiario]";
		LOG.info(prf + " BEGIN");

		List<AnagraficaBeneficiarioVO> beneficiario = new ArrayList<AnagraficaBeneficiarioVO>();

		AnagraficaBeneficiarioVO anagBene = new AnagraficaBeneficiarioVO();

		//int progetto = this.checkProgetto(idSoggetto);

		//int domanda = this.checkDomanda(idSoggetto);


		try {

			//			----NUOVA ANAGRAFICA BENEFICIARIO-----------
			//
			//			-- FARE FUNZIONE DI CHECK SU ID COMUNE, SE VALORIZZATO INDICA BENEFICIARIO ITALIANO ALTRIMENTI ESTERO, QUESTO SERVE AD OVVIARE IL BUG
			//			-- A DB PER QUANTO RIGUARDA ID NAZIONE, SE ITALIA AGGIUNGERE MANUALMENTE STRINGA ITALIA

			// se c'è idProgetto controllo usando la tabella pbandi_r_soggetto_progetto senno controllo usando la tabella pbandi_r_soggetto_domanda
			
			
			// controllo se la domanda ha un progetto 
			
//			String idProgettoSql = "SELECT prsp.ID_PROGETTO\r\n"
//					+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
//					+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
//					+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
//					+ "WHERE  ptd.NUMERO_DOMANDA ='"+numDomanda+"'\r\n"
//					+ "AND rownum <2";	
//			idProgetto = getJdbcTemplate().queryForObject(idProgettoSql, Long.class);
			
			if(idProgetto>0 && idProgetto!=null) {
				String sqlGetAnag="SELECT\r\n"
						+ "    prsp.ID_SOGGETTO,\r\n"
						+ "    prsp.ID_ENTE_GIURIDICO,\r\n"
						+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "    pdta.DESC_TIPO_ANAGRAFICA,\r\n"
						+ "    pdta.ID_TIPO_ANAGRAFICA,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pdss.DESC_SEZIONE_SPECIALE,\r\n"
						+ "    pdp2.ID_PROVINCIA AS id_prov_isc,\r\n"
						+ "    pdp2.DESC_PROVINCIA AS desc_prov_isc,\r\n"
						+ "    pdp.ID_PROVINCIA,\r\n"
						+ "    pdp.DESC_PROVINCIA,\r\n"
						+ "    pdc.ID_COMUNE,\r\n"
						+ "    pdc.DESC_COMUNE,\r\n"
						+ "    pdr.ID_REGIONE,\r\n"
						+ "    pdr.DESC_REGIONE,\r\n"
						+ "    pdn.ID_NAZIONE,\r\n"
						+ "    pdn.DESC_NAZIONE,\r\n"
						+ "    ptis.NUM_ISCRIZIONE,\r\n"
						+ "    ptis.DT_ISCRIZIONE,\r\n"
						+ "    pdsa.DESC_STATO_ATTIVITA,\r\n"
						+ "    pdaa.ID_ATTIVITA_ATECO,\r\n"
						+ "    pdaa.DESC_ATECO,\r\n"
						+ "    pdaa.COD_ATECO,\r\n"
						+ "    pteg.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "    pteg.DT_INIZIO_VALIDITA,\r\n"
						+ "    --pteg.ID_DIMENSIONE_IMPRESA ,\r\n"
						+ "    pteg.FLAG_RATING_LEGALITA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "    pti.DESC_INDIRIZZO,\r\n"
						+ "    ptse.PARTITA_IVA,\r\n"
						+ "    pti.CAP,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    ptegs.ID_ENTE_SEZIONE,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    pteg.COD_UNI_IPA,\r\n"
						+ "    ptis.ID_TIPO_ISCRIZIONE,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.NDG, pteg.DT_INIZIO_ATTIVITA_ESITO ,\r\n"
						+ "    ptis.ID_ISCRIZIONE, ptse.id_sede, \r\n"
						+ "    ptr.PEC, pteg.DT_COSTITUZIONE, pdsa.ID_STATO_ATTIVITA, ptr.ID_RECAPITI,\r\n"
						+ "    ptr.EMAIL, pti.ID_INDIRIZZO, ptse.ID_SEDE,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta ON pdta.ID_TIPO_ANAGRAFICA = prsp.ID_TIPO_ANAGRAFICA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsps.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsps.ID_INDIRIZZO\r\n"
						+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = pti.ID_COMUNE\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pdaa.ID_ATTIVITA_ATECO = ptse.ID_ATTIVITA_ATECO\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_ATTIVITA pdsa ON pdsa.ID_STATO_ATTIVITA = pteg.ID_STATO_ATTIVITA\r\n"
						+ "    LEFT JOIN PBANDI_T_ISCRIZIONE ptis ON ptis.ID_ISCRIZIONE = prsp.ID_ISCRIZIONE_PERSONA_GIURID\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp2 ON pdp2.ID_PROVINCIA = ptis.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON pdss.ID_SEZIONE_SPECIALE = ptegs.ID_SEZIONE_SPECIALE\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsps.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "	   AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsps.ID_TIPO_SEDE = 1\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto+"\r\n"
						+ "    AND prsp.ID_SOGGETTO = "+idSoggetto+"\r\n";
				
				anagBene = getBenefEG(sqlGetAnag);
					
			} else {
				
				String anagSql ="SELECT\r\n"
						+ "    prsd.ID_SOGGETTO,\r\n"
						+ "    prsd.ID_ENTE_GIURIDICO,\r\n"
						+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "    pdta.DESC_TIPO_ANAGRAFICA,\r\n"
						+ "    pdta.ID_TIPO_ANAGRAFICA,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pdss.DESC_SEZIONE_SPECIALE,\r\n"
						+ "    pdp2.ID_PROVINCIA AS id_prov_isc,\r\n"
						+ "    pdp2.DESC_PROVINCIA AS desc_prov_isc,\r\n"
						+ "    pdp.ID_PROVINCIA,\r\n"
						+ "    pdp.DESC_PROVINCIA,\r\n"
						+ "    pdc.ID_COMUNE,\r\n"
						+ "    pdc.DESC_COMUNE,\r\n"
						+ "    pdr.ID_REGIONE,\r\n"
						+ "    pdr.DESC_REGIONE,\r\n"
						+ "    pdn.ID_NAZIONE,\r\n"
						+ "    pdn.DESC_NAZIONE,\r\n"
						+ "    ptis.NUM_ISCRIZIONE,\r\n"
						+ "    ptis.DT_ISCRIZIONE,\r\n"
						+ "    pdsa.DESC_STATO_ATTIVITA,\r\n"
						+ "    pdaa.ID_ATTIVITA_ATECO,\r\n"
						+ "    pdaa.DESC_ATECO,\r\n"
						+ "    pdaa.COD_ATECO,\r\n"
						+ "    pteg.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "    pteg.DT_INIZIO_VALIDITA,\r\n"
						+ "    --pteg.ID_DIMENSIONE_IMPRESA ,\r\n"
						+ "    pteg.FLAG_RATING_LEGALITA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "    pti.DESC_INDIRIZZO,\r\n"
						+ "    ptse.PARTITA_IVA, ptse.id_sede, \r\n"
						+ "    pti.CAP,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    ptegs.ID_ENTE_SEZIONE,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    pteg.COD_UNI_IPA,\r\n"
						+ "    ptis.ID_TIPO_ISCRIZIONE,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    ptis.ID_ISCRIZIONE,\r\n"
						+ "    ptr.PEC,\r\n"
						+ "    ptr.EMAIL, ptr.ID_RECAPITI,\r\n"
						+ "    pti.ID_INDIRIZZO,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    pdsa.ID_STATO_ATTIVITA,\r\n"
						+ "    prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    ptse.ID_SEDE,\r\n"
						+ "    pteg.DT_COSTITUZIONE,\r\n"
						+ "    pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n"
						+ "    prsd.ID_ENTE_GIURIDICO\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta ON pdta.ID_TIPO_ANAGRAFICA = prsd.ID_TIPO_ANAGRAFICA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsds.ID_INDIRIZZO\r\n"
						+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = pti.ID_COMUNE\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pdaa.ID_ATTIVITA_ATECO = ptse.ID_ATTIVITA_ATECO\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_ATTIVITA pdsa ON pdsa.ID_STATO_ATTIVITA = pteg.ID_STATO_ATTIVITA\r\n"
						+ "    LEFT JOIN PBANDI_T_ISCRIZIONE ptis ON ptis.ID_ISCRIZIONE = prsd.ID_ISCRIZIONE_PERSONA_GIURID\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp2 ON pdp2.ID_PROVINCIA = ptis.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON pdss.ID_SEZIONE_SPECIALE = ptegs.ID_SEZIONE_SPECIALE\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsds.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsds.ID_TIPO_SEDE = 1\r\n"
						+ "	   AND prsd.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsd.ID_SOGGETTO ="+ idSoggetto+"\r\n"
						+ "    AND ptd.ID_DOMANDA ="+numDomanda+"\r\n";
				
				anagBene = getBenefEG(anagSql);
			}

			beneficiario.add(anagBene);


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read AnagraficaBeneficiarioVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AnagraficaBeneficiarioVO", e);
			throw new DaoException("DaoException while trying to read AnagraficaBeneficiarioVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}


	private AnagraficaBeneficiarioVO getBenefEG(String anagSql) {
		AnagraficaBeneficiarioVO benef = new AnagraficaBeneficiarioVO();
		
		try {
			
			
			benef = getJdbcTemplate().query(anagSql, new ResultSetExtractor<AnagraficaBeneficiarioVO>()
			{
				@Override
				public  AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException
				{
					AnagraficaBeneficiarioVO anag = new AnagraficaBeneficiarioVO();

					while (rs.next())
					{
						anag.setCap(rs.getString("CAP"));
						anag.setCfSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
						anag.setCodAteco(rs.getString("COD_ATECO"));
						anag.setCodUniIpa(rs.getString("COD_UNI_IPA"));
						anag.setDescAteco(rs.getString("DESC_ATECO"));
						anag.setDescComune(rs.getString("DESC_COMUNE"));
						anag.setDescFormaGiur(rs.getString("DESC_FORMA_GIURIDICA"));
						anag.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
						anag.setDescNazione(rs.getString("DESC_NAZIONE"));
						anag.setDescProvincia(rs.getString("DESC_PROVINCIA"));
						anag.setDescProvinciaIscriz(rs.getString("desc_prov_isc"));
						anag.setDescRegione(rs.getString("DESC_REGIONE"));
						anag.setDescSezioneSpeciale(rs.getString("DESC_SEZIONE_SPECIALE"));
						anag.setDescStatoAttivita(rs.getString("DESC_STATO_ATTIVITA"));
						anag.setDtCostituzione(rs.getDate("DT_COSTITUZIONE"));
						anag.setDtIscrizione(rs.getDate("DT_ISCRIZIONE"));
						anag.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
						anag.setDtUltimoEseChiuso(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
						anag.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
						anag.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
						anag.setIdAttAteco(rs.getLong("ID_ATTIVITA_ATECO"));
						anag.setIdComune(rs.getLong("ID_COMUNE"));
						anag.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
						anag.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
						anag.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
						anag.setIdIscrizione(rs.getLong("id_iscrizione"));
						anag.setIdNazione(rs.getLong("ID_NAZIONE"));
						anag.setIdProvincia(rs.getLong("id_provincia"));
						anag.setIdProvinciaIscrizione(rs.getLong("id_prov_isc"));
						anag.setIdRegione(rs.getLong("ID_REGIONE"));
						anag.setIdSede(rs.getLong("ID_SEDE"));
						//anag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						anag.setIdStatoAtt(rs.getLong("ID_STATO_ATTIVITA"));
						anag.setIdTipoIscrizione(rs.getLong("id_tipo_iscrizione"));
						anag.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
						anag.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
						anag.setPeriodoScadEse(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
						anag.setpIva(rs.getString("partita_iva"));
						anag.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
						//anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						anag.setDescPec(rs.getString("PEC"));
						//anag.setQuotaPartecipazione(rs.getString("QUOTA_PARTECIPAZIONE"));
						//anag.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
						//anag.setDescTipoSoggCorr(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						anag.setIdEnteSezione(rs.getLong("id_ente_sezione"));
						anag.setProgSoggProgetto(rs.getString("PROGR_SOGGETTO_PROGETTO"));
						anag.setProgSoggDomanda(rs.getString("PROGR_SOGGETTO_DOMANDA"));
						//anag.setIdDomanda(rs.getString("ID_DOMANDA"));
						anag.setNdg(rs.getString("NDG"));
						anag.setIdRecapiti(rs.getLong("ID_RECAPITI"));
						anag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						Long regione = rs.getLong("ID_REGIONE");
						if(regione>0 && regione !=null) {
							anag.setDescNazione("ITALIA");
							anag.setIdNazione((long) 118);
						}
					}
					return anag;
				}
			});
			
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read AnagraficaBeneficiarioVO", e);
		}
		finally {
			LOG.info( " END");
			}
		return benef;
	}


	@Override
	public IscrizioneRegistroVO getIscrizioneRegistroImprese(Long idSoggetto, HttpServletRequest req)
			throws DaoException {

		String prf = "[AnagraficaBeneficiarioDAOImpl::getIscrizioneRegistroImprese]";
		LOG.info(prf + " BEGIN");

		IscrizioneRegistroVO iscrizione = new IscrizioneRegistroVO();

		try {

			String query = "SELECT pti.NUM_ISCRIZIONE, pti.DT_ISCRIZIONE, pdp.DESC_PROVINCIA \r\n"
					+ "FROM PBANDI_T_SOGGETTO pts \r\n"
					+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
					+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
					+ "JOIN PBANDI_T_ISCRIZIONE pti \r\n"
					+ "ON pti.ID_ISCRIZIONE = prsp.ID_ISCRIZIONE_PERSONA_GIURID \r\n"
					+ "JOIN PBANDI_D_PROVINCIA pdp \r\n"
					+ "ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA \r\n"
					+ "WHERE pts.ID_SOGGETTO = :idSoggetto \r\n"
					+ "AND prsp.ID_TIPO_ANAGRAFICA = 1 \r\n"
					+ "AND prsp.ID_TIPO_BENEFICIARIO <> 4 ";

			Object[] args = new Object[]{idSoggetto};

			int[] types = new int[]{Types.NUMERIC};

			iscrizione = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<IscrizioneRegistroVO>()
			{
				@Override
				public  IscrizioneRegistroVO extractData(ResultSet rs) throws SQLException, DataAccessException
				{

					IscrizioneRegistroVO iscrizione = new IscrizioneRegistroVO();

					while (rs.next())
					{
						iscrizione.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
						iscrizione.setDtIscrizione(rs.getDate("DT_ISCRIZIONE"));
						iscrizione.setDescProvincia(rs.getString("DESC_PROVINCIA"));
					}

					return iscrizione;
				}
			});

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_ISCRIZIONE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_ISCRIZIONE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_ISCRIZIONE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return iscrizione;
	}



	private int checkComune(Long idSoggetto) {

		String query = "SELECT count(pti.ID_COMUNE) as COMUNE \r\n"
				+ "FROM PBANDI_T_SOGGETTO pts \r\n"
				+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
				+ "ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
				+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
				+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
				+ "JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
				+ "ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps \r\n"
				+ "ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO \r\n"
				+ "JOIN PBANDI_T_INDIRIZZO pti \r\n"
				+ "ON pti.ID_INDIRIZZO = prsps.ID_INDIRIZZO \r\n"
				+ "JOIN PBANDI_D_COMUNE pdc \r\n"
				+ "ON pdc.ID_COMUNE = pti.ID_COMUNE \r\n"
				+ "WHERE pts.ID_SOGGETTO = :idSoggetto \r\n"
				+ "AND prsp.ID_TIPO_ANAGRAFICA = 1 \r\n"
				+ "AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
				+ "AND prsps.ID_TIPO_SEDE = 1";

		Object[] args = new Object[]{idSoggetto};

		int[] types = new int[]{Types.NUMERIC};

		return getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Integer>()
		{
			@Override
			public  Integer extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				Integer result = 0;

				while (rs.next())
				{
					result = rs.getInt("COMUNE");
				}
				return result;
			}
		});
	}

	@Override
	public List<ElencoDomandeProgettiVO> getElencoDomandeProgetti(Long idSoggetto, boolean isEnteGiuridico,HttpServletRequest req) throws DaoException {

		String prf = "[AnagraficaBeneficiarioDAOImpl::getElencoDomandeProgetti]";
		LOG.info(prf + " BEGIN");

		List<ElencoDomandeProgettiVO> elenco = new ArrayList<ElencoDomandeProgettiVO>();

		if(isEnteGiuridico) {
			try {
				
				/*recupero tutte le domande dek benef*/
				
				String sql2 ="  SELECT prsd.ID_DOMANDA , prsp.ID_PROGETTO \r\n"
						+ "    FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
						+ "    LEFT JOIN pbandi_r_soggetto_progetto prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
						+ "    										  AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    										  AND prsp.ID_TIPO_ANAGRAFICA =1\r\n"
						+ "    WHERE prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
						+ "    AND prsd.ID_SOGGETTO =?";
				
				List<AttivitaDTO> listDomande =new ArrayList<AttivitaDTO>(); 				
				RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {
					@Override
					public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						AttivitaDTO attivita = new AttivitaDTO();
						attivita.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
						attivita.setIdDomanda(rs.getBigDecimal("ID_DOMANDA"));
						return attivita;
					}
				};
				
				listDomande = getJdbcTemplate().query(sql2, lista, idSoggetto);
				
				
				for (AttivitaDTO map : listDomande) {
					List<ElencoDomandeProgettiVO>  domProg = new ArrayList<ElencoDomandeProgettiVO>(); 
					 if(map.getIdProgetto()!=null && map.getIdProgetto().intValue()>0){
						 domProg = getProgetto(map.getIdProgetto(), idSoggetto);
					 } else {
						 domProg = getDomanda(map.getIdDomanda(), idSoggetto);						 
					 }
					 elenco.addAll(domProg); 
				}
	
			}catch (IncorrectResultSizeDataAccessException e) {
				LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read ElencoDomandeProgettiVO", e);

			} catch (Exception e) {
				LOG.error(prf + "Exception while trying to read ElencoDomandeProgettiVO", e);
				throw new DaoException("DaoException while trying to read ElencoDomandeProgettiVO", e);
			}
			finally {
				LOG.info(prf + " END");
			}
			return elenco;
		}else {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("  SELECT\r\n"
						+ "    ptd.NUMERO_DOMANDA, ptd.ID_DOMANDA ,\r\n"
						+ "    ptd.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
						+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
						+ "    ptp.CODICE_VISUALIZZATO,prsp.id_progetto,\r\n"
						+ "    pdsp.DESC_STATO_PROGETTO\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON (prsd.PROGR_SOGGETTO_DOMANDA  = prsp.PROGR_SOGGETTO_DOMANDA)\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON (prsd.ID_DOMANDA = ptd.ID_DOMANDA)\r\n"
						+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON (\r\n"
						+ "        ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
						+ "    )\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON (pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA)\r\n"
						+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON (ptp.ID_PROGETTO = prsp.ID_PROGETTO)\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON (pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO)\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "ORDER BY\r\n"
						+ "    ptd.NUMERO_DOMANDA");

				elenco = getJdbcTemplate().query(sql.toString(),new ElencoRowMapperPF());

			}catch (IncorrectResultSizeDataAccessException e) {
				LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read ElencoDomandeProgettiVO", e);

			} catch (Exception e) {
				LOG.error(prf + "Exception while trying to read ElencoDomandeProgettiVO", e);
				throw new DaoException("DaoException while trying to read ElencoDomandeProgettiVO", e);
			}
			finally {
				LOG.info(prf + " END");
			}
			return elenco;
		}
	}

	private List<ElencoDomandeProgettiVO> getDomanda(BigDecimal idDomanda, Long idSoggetto) {
		StringBuilder sql = new StringBuilder(); 
		List<ElencoDomandeProgettiVO> domProg = new ArrayList<ElencoDomandeProgettiVO>(); 
		 sql.append(" SELECT\r\n"
			 		+ "	DISTINCT ptpf.ID_PERSONA_FISICA , prsd.ID_DOMANDA ,\r\n"
			 		+ "	   prsp.PROGR_SOGGETTO_PROGETTO ,\r\n"
			 		+ "    ptd.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
			 		+ "    ptd.NUMERO_DOMANDA,\r\n"
			 		+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
			 		+ "    ptp.CODICE_VISUALIZZATO,\r\n"
			 		+ "    pdsp.DESC_STATO_PROGETTO,\r\n"
			 		+ "    ptpf.NOME, prsp.ID_PROGETTO ,\r\n"
			 		+ "    ptpf.COGNOME, prsd.PROGR_SOGGETTO_DOMANDA \r\n"
			 		+ "FROM\r\n"
			 		+ "	 PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
			 		+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
			 		+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON prsdsc.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
			 		+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prspsc.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
			 		+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc2 ON prsc2.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI  AND  prsc2.ID_TIPO_SOGGETTO_CORRELATO = 1\r\n"
			 		+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI  AND  prsc.ID_TIPO_SOGGETTO_CORRELATO = 1\r\n"
			 		+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
			 		+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
			 		+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO\r\n"
			 		+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
			 		+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON  prsd.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
			 		+ "WHERE\r\n"
			 		+ "    prsc.ID_SOGGETTO_ENTE_GIURIDICO = ?\r\n"
			 		+ "    AND prsd.ID_DOMANDA = ?\r\n"
			 		+ "    ORDER BY prsd.PROGR_SOGGETTO_DOMANDA ");
		 
		 
		 domProg =  getJdbcTemplate().query(sql.toString(), new ElencoRowMapper(), new Object[] {
				 idSoggetto, idDomanda });
		 
		 
		return domProg;
	}


	private List<ElencoDomandeProgettiVO> getProgetto(BigDecimal idProgetto, Long idSoggetto) {
		
		StringBuilder sql = new StringBuilder(); 
		List<ElencoDomandeProgettiVO> domandeProg = new ArrayList<ElencoDomandeProgettiVO>();
		 sql.append("    SELECT  DISTINCT ptpf.ID_PERSONA_FISICA ,\r\n"
		 		+ "    prsp.ID_PROGETTO ,\r\n"
		 		+ "    prsd.ID_DOMANDA ,\r\n"
		 		+ "	   prsp.PROGR_SOGGETTO_PROGETTO, \r\n"
		 		+ "    ptd.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
		 		+ "    ptd.NUMERO_DOMANDA,\r\n"
		 		+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
		 		+ "    ptp.CODICE_VISUALIZZATO,\r\n"
		 		+ "    pdsp.DESC_STATO_PROGETTO,\r\n"
		 		+ "    ptpf.NOME, prsp.ID_PROGETTO ,\r\n"
		 		+ "    ptpf.COGNOME, prsd.PROGR_SOGGETTO_DOMANDA \r\n"
		 		+ "FROM\r\n"
		 		+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
		 		+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd  ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
		 		+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prspsc.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
		 		+ "    JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI  AND  prsc.ID_TIPO_SOGGETTO_CORRELATO = 1\r\n"
		 		+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
		 		+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
		 		+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO\r\n"
		 		+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
		 		+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON  prsp.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
		 		+ "WHERE\r\n"
		 		+ "    prsc.ID_SOGGETTO_ENTE_GIURIDICO = ?\r\n"
		 		+ "    AND prsp.ID_PROGETTO = ?\r\n"
		 		+ "    AND prsp.PROGR_SOGGETTO_DOMANDA IS NOT NULL\r\n "
		 		+ "    ORDER BY prsd.PROGR_SOGGETTO_DOMANDA");
		 
		 
		 domandeProg =  getJdbcTemplate().query(sql.toString(), new ElencoRowMapper(), new Object[] {
				 idSoggetto, idProgetto });
		 
		 
		return domandeProg;
	}


	@Override
	public StatoDomandaVO getStatoDomanda(Long idSoggetto,Long idDomanda,  HttpServletRequest req) throws DaoException {

		String prf = "[AnagraficaBeneficiarioDAOImpl::getStatoDomanda]";
		LOG.info(prf + " BEGIN");

		List<StatoDomandaVO> statoDomanda = new ArrayList<StatoDomandaVO>();

		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pdsd.DESC_STATO_DOMANDA \r\n"
					+ "FROM PBANDI_T_DOMANDA ptd LEFT JOIN pbandi_d_stato_domanda pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA \r\n"
					+ "WHERE ptd.ID_DOMANDA ="+idDomanda);

			statoDomanda = getJdbcTemplate().query(sql.toString(), new RowMapper<StatoDomandaVO>(){
				@Override
				public StatoDomandaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					StatoDomandaVO stato = new StatoDomandaVO();
					stato.setStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
					return stato;
				}
			});

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return statoDomanda.get(0);
	}

	@Override
	public AnagraficaBeneficiarioPfVO getAnagBeneFisico(Long idSoggetto,String numDomanda,Long idProgetto,  HttpServletRequest req) throws DaoException {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getAnagBeneficiario]";
		LOG.info(prf + " BEGIN");

		AnagraficaBeneficiarioPfVO beneficiario = new AnagraficaBeneficiarioPfVO();
		try {
  
//			String getVeroIddomanda = "select id_domanda from pbandi_t_domanda where numero_domanda='" + numDomanda
//					+ "'\r\n";
//			BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda,
//					BigDecimal.class);

			//BigDecimal idProgetto = checkProgetto(numDomanda); 
			String sqlNew = null; 
			if(idProgetto>0) {
				sqlNew="WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "        PTPF.ID_SOGGETTO,\r\n"
						+ "        ptpf.DT_NASCITA,\r\n"
						+ "        ptpf.COGNOME,\r\n"
						+ "        ptpf.NOME,\r\n"
						+ "        pdc.DESC_COMUNE AS comune_nasc,\r\n"
						+ "        pdp.DESC_PROVINCIA AS prov_nasc,\r\n"
						+ "        pdr.DESC_REGIONE AS regione_nasc,\r\n"
						+ "        pdn.DESC_NAZIONE AS nazione_nasc,\r\n"
						+ "        pdc.ID_COMUNE,\r\n"
						+ "        pdc.CAP,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg,\r\n"
						+ "        pdn.ID_NAZIONE,\r\n"
						+ "        PTPF.ID_PERSONA_FISICA,\r\n"
						+ "        pdce.ID_COMUNE_ESTERO,\r\n"
						+ "        pdce.DESC_COMUNE_ESTERO,\r\n"
						+ "        pdn2.DESC_NAZIONE AS nazione_est,\r\n"
						+ "        pdn2.ID_NAZIONE AS id_nazione_est\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_progetto prsp\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON PTPF.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE\r\n"
						+ "        AND pdce.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn2 ON pdce.ID_NAZIONE = pdn2.ID_NAZIONE\r\n"
						+ "),\r\n"
						+ "residenza AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "        prsp.ID_PROGETTO,\r\n"
						+ "        pti.ID_INDIRIZZO,\r\n"
						+ "        pti.DESC_INDIRIZZO,\r\n"
						+ "        pdc.ID_COMUNE AS id_com_res,\r\n"
						+ "        pdc.DESC_COMUNE AS com_res,pti.cap as capPF,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov_res,\r\n"
						+ "        pdp.SIGLA_PROVINCIA AS prov_res,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg_res,\r\n"
						+ "        pdr.DESC_REGIONE AS reg_res,\r\n"
						+ "        pdn.ID_NAZIONE AS id_naz_res,\r\n"
						+ "        pdn.DESC_NAZIONE AS naz_res\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_progetto prsp\r\n"
						+ "        LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsp.ID_INDIRIZZO_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    ds.id_soggetto,\r\n"
						+ "    ds.cognome,\r\n"
						+ "    ds.nome,\r\n"
						+ "    ds.DT_NASCITA,\r\n"
						+ "    ds.comune_nasc,\r\n"
						+ "    ds.prov_nasc,\r\n"
						+ "    res.capPF,\r\n"
						+ "    ds.regione_nasc,\r\n"
						+ "    ds.nazione_nasc,\r\n"
						+ "    ds.id_comune,\r\n"
						+ "    ds.id_reg,\r\n"
						+ "    ds.id_prov,\r\n"
						+ "    ds.id_nazione,\r\n"
						+ "    ds.id_nazione_est,\r\n"
						+ "    ds.nazione_est,\r\n"
						+ "    ds.ID_COMUNE_ESTERO,\r\n"
						+ "    ds.DESC_COMUNE_ESTERO,\r\n"
						+ "    ds.ID_PERSONA_FISICA,\r\n"
						+ "    res.id_progetto,\r\n"
						+ "    res.ID_INDIRIZZO,\r\n"
						+ "    res.DESC_INDIRIZZO,\r\n"
						+ "    res.id_com_res,\r\n"
						+ "    res.id_prov_res,\r\n"
						+ "    res.id_reg_res,\r\n"
						+ "    res.id_naz_res,\r\n"
						+ "    res.com_res,\r\n"
						+ "    res.prov_res,\r\n"
						+ "    res.reg_res,\r\n"
						+ "    res.naz_res,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    res.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    pdta.ID_TIPO_ANAGRAFICA,\r\n"
						+ "    pdta.DESC_TIPO_ANAGRAFICA\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON prsp.PROGR_SOGGETTO_PROGETTO = ds.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN residenza res ON res.PROGR_SOGGETTO_progetto = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON ds.id_soggetto = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta ON pdta.ID_TIPO_ANAGRAFICA = prsp.ID_TIPO_ANAGRAFICA\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND ds.id_soggetto = "+idSoggetto+"\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto; 

			} else {
				sqlNew= "WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        PTPF.ID_SOGGETTO,\r\n"
						+ "        ptpf.DT_NASCITA,\r\n"
						+ "        ptpf.COGNOME,\r\n"
						+ "        ptpf.NOME,\r\n"
						+ "        pdc.DESC_COMUNE AS comune_nasc,\r\n"
						+ "        pdp.DESC_PROVINCIA AS prov_nasc,\r\n"
						+ "        pdr.DESC_REGIONE AS regione_nasc,\r\n"
						+ "        pdn.DESC_NAZIONE AS nazione_nasc,\r\n"
						+ "        pdc.ID_COMUNE,\r\n"
						+ "        pdc.CAP,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg,\r\n"
						+ "        pdn.ID_NAZIONE,\r\n"
						+ "        PTPF.ID_PERSONA_FISICA,\r\n"
						+ "        pdce.ID_COMUNE_ESTERO,\r\n"
						+ "        pdce.DESC_COMUNE_ESTERO,\r\n"
						+ "        pdn2.DESC_NAZIONE AS nazione_est,\r\n"
						+ "        pdn2.ID_NAZIONE AS id_nazione_est\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON PTPF.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE\r\n"
						+ "        AND pdce.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn2 ON pdce.ID_NAZIONE = pdn2.ID_NAZIONE\r\n"
						+ "),\r\n"
						+ "residenza AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        prsd.ID_DOMANDA,\r\n"
						+ "        pti.ID_INDIRIZZO,\r\n"
						+ "        pti.DESC_INDIRIZZO, pti.cap as capPF, \r\n"
						+ "        pdc.ID_COMUNE AS id_com_res,\r\n"
						+ "        pdc.DESC_COMUNE AS com_res,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov_res,\r\n"
						+ "        pdp.SIGLA_PROVINCIA AS prov_res,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg_res,\r\n"
						+ "        pdr.DESC_REGIONE AS reg_res,\r\n"
						+ "        pdn.ID_NAZIONE AS id_naz_res,\r\n"
						+ "        pdn.DESC_NAZIONE AS naz_res\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsd.ID_INDIRIZZO_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    ds.id_soggetto,\r\n"
						+ "    ds.cognome,\r\n"
						+ "    ds.nome,\r\n"
						+ "    ds.DT_NASCITA,\r\n"
						+ "    ds.comune_nasc,\r\n"
						+ "    ds.prov_nasc,\r\n"
						+ "    res.capPF,\r\n"
						+ "    ds.regione_nasc,\r\n"
						+ "    ds.nazione_nasc,\r\n"
						+ "    ds.id_comune,\r\n"
						+ "    ds.id_reg,\r\n"
						+ "    ds.id_prov,\r\n"
						+ "    ds.id_nazione,\r\n"
						+ "    ds.id_nazione_est,\r\n"
						+ "    ds.nazione_est,\r\n"
						+ "    ds.ID_COMUNE_ESTERO,\r\n"
						+ "    ds.DESC_COMUNE_ESTERO,\r\n"
						+ "    ds.ID_PERSONA_FISICA,\r\n"
						+ "    res.id_domanda,\r\n"
						+ "    res.ID_INDIRIZZO,\r\n"
						+ "    res.DESC_INDIRIZZO,\r\n"
						+ "    res.id_com_res,\r\n"
						+ "    res.id_prov_res,\r\n"
						+ "    res.id_reg_res,\r\n"
						+ "    res.id_naz_res,\r\n"
						+ "    res.com_res,\r\n"
						+ "    res.prov_res,\r\n"
						+ "    res.reg_res,\r\n"
						+ "    res.naz_res,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    res.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    pdta.ID_TIPO_ANAGRAFICA,\r\n"
						+ "    pdta.DESC_TIPO_ANAGRAFICA\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON prsd.PROGR_SOGGETTO_DOMANDA = ds.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN residenza res ON res.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON ds.id_soggetto = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta ON pdta.ID_TIPO_ANAGRAFICA = prsd.ID_TIPO_ANAGRAFICA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND ds.id_soggetto = "+idSoggetto+"\r\n"
						+ "    AND prsd.ID_DOMANDA ="+numDomanda; 
				
			}
			
			beneficiario = getSoggPF(sqlNew);
			beneficiario.setIdDomanda(numDomanda.toString());
			if(beneficiario!=null) {	
				if (beneficiario.getIdNazioneEsteraNascita() >0 ) {
					beneficiario.setIdNazioneDiNascita(beneficiario.getIdNazioneEsteraNascita());
					beneficiario.setNazioneDiNascita(beneficiario.getNazioneNascitaEstera());
					beneficiario.setComuneDiNascita(beneficiario.getComuneNascitaEstero());
					beneficiario.setIdComuneDiNascita(beneficiario.getIdComuneEsteraNascita());
				}
				if (beneficiario.getRegioneDiNascita() != null) {
					beneficiario.setNazioneDiNascita("ITALIA");
					beneficiario.setIdNazioneDiNascita((long) 118);
				}
				if (beneficiario.getIdRegionePF()>0) {
					beneficiario.setNazionePF("ITALIA");
					beneficiario.setIdNazionePF((long) 118);
				}
			}

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read soggetto", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read soggetto", e);
			throw new DaoException("DaoException while trying to read soggetto", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

private AnagraficaBeneficiarioPfVO getSoggPF(String query) {

		
		AnagraficaBeneficiarioPfVO soggetto = new AnagraficaBeneficiarioPfVO();
		
		try {
			 soggetto = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>() {
					
					@Override
					public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
						AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO();
						
						while(rs.next()) {
							anag.setProgSoggDomanda(rs.getBigDecimal("PROGR_SOGGETTO_DOMANDA"));
							anag.setCapPF(rs.getString("capPF"));
							anag.setCognome(rs.getString("cognome"));
							anag.setNome(rs.getString("nome"));
							anag.setDataDiNascita(rs.getDate("DT_NASCITA"));
							anag.setComuneDiNascita(rs.getString("comune_nasc"));
							anag.setProvinciaDiNascita(rs.getString("prov_nasc"));
							anag.setRegioneDiNascita(rs.getString("regione_nasc"));
							anag.setNazioneDiNascita(rs.getString("nazione_nasc"));
							anag.setIdComuneDiNascita(rs.getLong("id_comune"));
							anag.setIdRegioneDiNascita(rs.getLong("id_reg"));
							anag.setIdProvinciaDiNascita(rs.getLong("id_prov"));
							anag.setIdNazioneDiNascita(rs.getLong("id_nazione"));
							anag.setIdPersonaFisica(rs.getLong("ID_PERSONA_FISICA"));
							anag.setIdIndirizzo(rs.getString("ID_INDIRIZZO"));
							anag.setIndirizzoPF(rs.getString("DESC_INDIRIZZO"));
							anag.setIdComunePF(rs.getLong("id_com_res"));
							anag.setIdProvinciaPF(rs.getLong("id_prov_res"));
							anag.setIdRegionePF(rs.getLong("id_reg_res"));
							anag.setIdNazionePF(rs.getLong("id_naz_res"));
							anag.setComunePF(rs.getString("com_res"));
							anag.setProvinciaPF(rs.getString("prov_res"));
							anag.setRegionePF(rs.getString("reg_res"));
							anag.setNazionePF(rs.getString("naz_res"));
							//anag.setIdDomanda(rs.getString("id_domanda"));	
							anag.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
							anag.setIdTipoAnagrafica(rs.getBigDecimal("ID_TIPO_ANAGRAFICA"));
							anag.setDescTipoAnagrafica(rs.getString("DESC_TIPO_ANAGRAFICA"));
							anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO"));
							anag.setComuneNascitaEstero(rs.getString("DESC_COMUNE_ESTERO")); 
							anag.setIdNazioneEsteraNascita(rs.getLong("id_nazione_est"));
							anag.setNazioneNascitaEstera(rs.getString("nazione_est"));
							anag.setNdg(rs.getString("NDG"));
							anag.setProgSoggProgetto(rs.getBigDecimal("PROGR_SOGGETTO_PROGETTO"));
						}
				return anag; 
					}
		   	});
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::getSoggPF] Exception " + e.getMessage());
		}
		
		return soggetto;
	}

	@Override
	public List<AtlanteVO> getNazioni(HttpServletRequest req) throws DaoException {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getNazioni]";
		LOG.info(prf + " BEGIN");
		List<AtlanteVO> nazioni = new ArrayList<AtlanteVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n" + 
					"	pdn.ID_NAZIONE ,\r\n" + 
					"	pdn.DESC_NAZIONE\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_D_NAZIONE pdn");

			ResultSetExtractor<List<AtlanteVO>> rse = new ResultSetExtractor<List<AtlanteVO>>() {

				@Override
				public List<AtlanteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

					List<AtlanteVO> vo = new ArrayList<AtlanteVO>();

					while (rs.next())
					{
						AtlanteVO v = new AtlanteVO();

						v.setIdNazione(rs.getString("ID_NAZIONE"));
						v.setDescNazione(rs.getString("DESC_NAZIONE"));
						vo.add(v);
					}
					return vo;
				}
			};
			nazioni = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return nazioni;
	}





	@Override
	public List<AtlanteVO> getRegioni(HttpServletRequest req) throws DaoException {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getRegioni]";
		LOG.info(prf + " BEGIN");
		List<AtlanteVO> regioni = new ArrayList<AtlanteVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n" + 
					"	pdr.ID_REGIONE ,\r\n" + 
					"	pdr.DESC_REGIONE\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_D_REGIONE pdr\r\n" + 
					"ORDER BY DESC_REGIONE ASC");

			ResultSetExtractor<List<AtlanteVO>> rse = new ResultSetExtractor<List<AtlanteVO>>() {

				@Override
				public List<AtlanteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

					List<AtlanteVO> vo = new ArrayList<AtlanteVO>();

					while (rs.next())
					{
						AtlanteVO v = new AtlanteVO();

						v.setIdRegione(rs.getString("ID_REGIONE"));
						v.setDescRegione(rs.getString("DESC_REGIONE"));
						vo.add(v);
					}
					return vo;
				}
			};
			regioni = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return regioni;
	}

	@Override
	public List<AtlanteVO> getProvincie(HttpServletRequest req) throws DaoException {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getProvincie]";
		LOG.info(prf + " BEGIN");
		List<AtlanteVO> provincie = new ArrayList<AtlanteVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n" + 
					"	pdp.ID_PROVINCIA ,\r\n" + 
					"	pdp.SIGLA_PROVINCIA ,"
					+ " pdp.desc_provincia, \r\n" + 
					"	pdp.ID_REGIONE\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_D_PROVINCIA pdp\r\n" + 
					"ORDER BY\r\n" + 
					"	desc_provincia");

			ResultSetExtractor<List<AtlanteVO>> rse = new ResultSetExtractor<List<AtlanteVO>>() {

				@Override
				public List<AtlanteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

					List<AtlanteVO> vo = new ArrayList<AtlanteVO>();

					while (rs.next())
					{
						AtlanteVO v = new AtlanteVO();

						v.setIdProvincia(rs.getString("ID_PROVINCIA"));
						v.setSiglaProvincia(rs.getString("SIGLA_PROVINCIA"));
						v.setIdRegione(rs.getString("ID_REGIONE"));
						v.setDescProvincia(rs.getString("desc_provincia"));
						vo.add(v);
					}
					return vo;
				}
			};
			provincie = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return provincie;
	}

	@Override
	public List<AtlanteVO> getComuni(String idProvincia, HttpServletRequest req) throws DaoException {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getComuni]";
		LOG.info(prf + " BEGIN");
		List<AtlanteVO> comuni = new ArrayList<AtlanteVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n" + 
					"	pdc.ID_COMUNE ,\r\n" + 
					"	pdc.DESC_COMUNE ,\r\n" + 
					"	pdc.ID_PROVINCIA, pdc.CAP\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_D_COMUNE pdc\r\n" + 
					"\r\n" + 
					"WHERE \r\n" + 
					"	pdc.ID_PROVINCIA = "+ idProvincia + "\r\n");

			ResultSetExtractor<List<AtlanteVO>> rse = new ResultSetExtractor<List<AtlanteVO>>() {

				@Override
				public List<AtlanteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

					List<AtlanteVO> vo = new ArrayList<AtlanteVO>();

					while (rs.next())
					{
						AtlanteVO v = new AtlanteVO();

						v.setIdComune(rs.getString("ID_COMUNE"));
						v.setDescComune(rs.getString("DESC_COMUNE"));
						v.setIdProvincia(rs.getString("ID_PROVINCIA"));
						v.setCap(rs.getString("CAP")); 
						vo.add(v);
					}
					return vo;
				}
			};
			comuni = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return comuni;
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////SERVIZI PER MODIFICA  ///////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Boolean updateAnagBeneFisico(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String numeroDomanda) throws ErroreGestitoException {
		String prf = "[AnagraficaBeneficiarioDAOImpl::updateAnagBeneFisico]";
		LOG.info(prf + " BEGIN");

		Boolean result=true; 
		try {
			BigDecimal progSoggProgetto = null;
			// questo progr soggetto progetto mi servira per poter aggiornare la tabella PROGR_SOGGETTO_PROGETTO
			try {				
				 
				progSoggProgetto = getJdbcTemplate().queryForObject("select prsp.PROGR_SOGGETTO_PROGETTO from PBANDI_R_SOGGETTO_PROGETTO prsp"
						+ " where prsp.PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda(), BigDecimal.class);
			} catch (Exception e) {
				LOG.error("[AnagraficaBeneficiarioDAOImpl::updateAnagBeneFisico] Exception " + e.getMessage());
				progSoggProgetto= null;
			}
			 
			 
			AnagraficaBeneficiarioPfVO anagDB = new AnagraficaBeneficiarioPfVO();
			BigDecimal idPersFisica = null; 
			boolean isInsertPersFisica = false;
			// recupero i dati della persona fisica della personna fisica
			String query = " SELECT\r\n"
					+ "    cognome,\r\n"
					+ "    nome,\r\n"
					+ "    sesso,\r\n"
					+ "    dt_nascita,\r\n"
					+ "    id_cittadinanza,\r\n"
					+ "    id_titolo_studio,\r\n"
					+ "    id_occupazione,\r\n"
					+ "    id_persona_fisica,\r\n"
					+ "    ID_COMUNE_ITALIANO_NASCITA ,\r\n"
					+ "    ID_COMUNE_ESTERO_NASCITA ,\r\n"
					+ "    pdc.ID_PROVINCIA,  id_persona_fisica \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_PERSONA_FISICA ptpf \r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = ptpf.ID_COMUNE_ITALIANO_NASCITA \r\n"
					+ "WHERE\r\n"
					+ "     ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "    AND ID_SOGGETTO ="+idSoggetto;
			
			anagDB = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>(){

				@Override
				public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO(); 
					while(rs.next()) {
						anag.setCognome(rs.getString("cognome"));
						anag.setNome(rs.getString("nome"));
						anag.setSesso(rs.getString("sesso"));
						anag.setIdCittadinanza(rs.getLong("id_cittadinanza"));
						anag.setIdTitoloStudio(rs.getLong("id_titolo_studio"));
						anag.setIdOccupazione(rs.getLong("id_occupazione"));
						anag.setIdPersonaFisica(rs.getLong("id_persona_fisica"));
						anag.setDataDiNascita(rs.getDate("dt_nascita"));
						if(rs.getString("ID_COMUNE_ITALIANO_NASCITA") !=null) {
							anag.setIdComuneDiNascita(rs.getLong("ID_COMUNE_ITALIANO_NASCITA"));
							anag.setIdProvinciaDiNascita(rs.getLong("ID_PROVINCIA"));
						} else {
							anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO_NASCITA"));
						}
					}
					return anag;
				}
				
			});
					
			
			if(soggetto.isDatiAnagrafici()==true) {
				// set il codice fiscale dentro la tabella PTSOGETTO; 
				String updateCFisc = "update PBANDI_T_SOGGETTO set 	codice_fiscale_soggetto='"+soggetto.getCodiceFiscale()+"'"
						+ ", id_utente_agg="+ soggetto.getIdUtenteAgg()
						+ ", dt_aggiornamento=sysdate"
						+ " where id_soggetto="+ idSoggetto;
				
				getJdbcTemplate().update(updateCFisc); 
				
				// set dati anagrafici 
				// se esiste un occorenza personna fisica allora aggiorno se no allora inserisco
				if(checkDatiAnagPf(soggetto, anagDB)==true) {
				// non faccio nulla
				
						String updatePRSdom="update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " id_persona_fisica=" + soggetto.getIdPersonaFisica()
								+ ", dt_aggiornamento=sysdate" 
								+ ", id_utente_agg="+soggetto.getIdUtenteAgg()
								+ " where id_soggetto="+ idSoggetto;
						getJdbcTemplate().update(updatePRSdom); 
					
				} else {
					
					 idPersFisica= insertPersonaFisica(soggetto, anagDB, idSoggetto); 
					 isInsertPersFisica = true;
					 String updatePRSogg = "update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " DT_AGGIORNAMENTO = sysdate, \r\n"
								+ " ID_UTENTE_AGG=?\r\n"
								+ ", ID_PERSONA_FISICA =?\r\n"
								+ " where PROGR_SOGGETTO_DOMANDA=?";					
						getJdbcTemplate().update(updatePRSogg, new Object[] {
								soggetto.getIdUtenteAgg(), idPersFisica, soggetto.getProgSoggDomanda()
						});
								
						if(soggetto.getProgSoggProgetto()!=null) {
							// aggiorno anche il suo idEnteGirudico
							String updatePrsp = "update PBANDI_R_SOGGETTO_PROGETTO set dt_aggiornamento= sysdate, id_utente_agg="+soggetto.getIdUtenteAgg()
							+ ", ID_PERSONA_FISICA="+ idPersFisica
							+ " where PROGR_SOGGETTO_PROGETTO="+soggetto.getProgSoggProgetto();
							getJdbcTemplate().update(updatePrsp); 
							
						}
			
				}			

			}

			
			// Residenza update che corrisponde all'indirizzo della persona fisica
			if(soggetto.isSedeLegale()==true) { 
				if(checkMatchCompletoIndirizzo(soggetto.getIdComunePF(), soggetto.getIdNazionePF(), soggetto.getIdProvinciaPF(),
						soggetto.getIdRegionePF(),idSoggetto, 1, soggetto.getIdDomanda())==true) {
					String updateIndirizzo="update PBANDI_T_INDIRIZZO  set"
							+ " id_utente_agg=?"
							+ " where PROGR_SOGGETTO_DOMANDA=?";
					getJdbcTemplate().update(updateIndirizzo, new Object[] {soggetto.getIdUtenteAgg(), soggetto.getProgSoggDomanda()}); 
		
				} else {
					BigDecimal idIndirizzo=	insertIndirizzoPF(soggetto, idSoggetto); 
								
						String updateIndirizzo = "update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " ID_INDIRIZZO_PERSONA_FISICA =?"
								+ ", ID_UTENTE_AGG=?"
								+ " where PROGR_SOGGETTO_DOMANDA=?" ;
						getJdbcTemplate().update(updateIndirizzo,new Object[] {
								idIndirizzo, soggetto.getIdUtenteAgg(), soggetto.getProgSoggDomanda()
						});
						
						if(soggetto.getProgSoggProgetto()!=null) {
							String update="update PBANDI_R_SOGGETTO_PROGETTO set"
									+ " ID_INDIRIZZO_PERSONA_FISICA=?\r\n"
									+ " , ID_UTENTE_AGG=?\r\n"
									+ " WHERE PROGR_SOGGETTO_PROGETTO=?";
							getJdbcTemplate().update(update,new Object[] {
									idIndirizzo, soggetto.getIdUtenteAgg(),soggetto.getProgSoggProgetto()
							});
						} 
				}
			}
			
			// CHIAMATA AL SERVIZIO DELLE SEGNALZIONI PER I CAMPI MODIFICATI 
			sistemaDiBlocchi( Long.parseLong(idSoggetto), soggetto.getIdUtenteAgg(), soggetto.getCampiModificati()); 

		} catch (Exception e) {
			result = false; 
			LOG.error(prf + "Exception during updateAnagBeneFisico", e);
			throw new ErroreGestitoException("Exception during updateAnagBeneFisico", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;


	}

	@Override
	public Boolean updateAnagBeneGiuridico(AnagraficaBeneficiarioVO soggetto,  
			String idSoggetto,String idDomanda, boolean isSoggettoCorrelato)
			throws ErroreGestitoException {

		String prf = "[AnagraficaBeneficiarioDAOImpl::updateAnagBeneGiuridico]";
		LOG.info(prf + " BEGIN");
		
		Boolean result ; 
		try {

//			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//			Date dtAggiornamento = DateUtil.utilToSqlDate(DateUtil.getDataOdierna());

			result = true; 
//			if(anagBeneGiuridico.getIsUpdate()) {
//				//UPDATE NEL CASO CHE CI SIA UN MATCH DI CAMPI SU TABELLA ENTE_GIURIDICO E CHE NON SIA STATO MODIFICATO ALCUN CAMPO
//				//AGGIORNARE DTAGGIORNAMENTO SU DOMANDA E PROGETTO
//				
//			}else {
//				//NON TROVO ALCUNA OCCORRENZA, PROCEDO CON INSERT NUOVO RECORD SU ENTE GIURIDICO
//				
//			}
			
			// recupero l'ultimo ente record presente sul db
						AnagraficaBeneficiarioVO anagDB = new AnagraficaBeneficiarioVO(); 
						String queryDB = "select * from PBANDI_T_ENTE_GIURIDICO where id_ente_giuridico=" + soggetto.getIdEnteGiuridico(); 
						anagDB=  getJdbcTemplate().query(queryDB, new ResultSetExtractor<AnagraficaBeneficiarioVO>() {
							@Override
							public AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException {
								AnagraficaBeneficiarioVO pg = new AnagraficaBeneficiarioVO();
								while (rs.next()) {
									pg.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
									pg.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
									pg.setDtCostituzione(rs.getDate("DT_COSTITUZIONE"));
									pg.setCapSociale(rs.getLong("CAPITALE_SOCIALE"));
									pg.setIdAttIuc(rs.getLong("ID_ATTIVITA_UIC"));
									pg.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
									pg.setIdClassEnte(rs.getLong("ID_CLASSIFICAZIONE_ENTE"));
									pg.setIdDimensioneImpresa(rs.getLong("ID_DIMENSIONE_IMPRESA"));
									pg.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
									pg.setDtInizioVal(rs.getDate("DT_INIZIO_VALIDITA"));
									pg.setDtFineVal(rs.getDate("DT_FINE_VALIDITA"));
									pg.setDtUltimoEseChiuso(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
									pg.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
									pg.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
									pg.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
									pg.setCodUniIpa(rs.getString("COD_UNI_IPA"));
									pg.setIdStatoAtt(rs.getLong("ID_STATO_ATTIVITA"));
									pg.setDtValEsito(rs.getDate("DT_VALUTAZIONE_ESITO"));
									pg.setPeriodoScadEse(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
									pg.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
									pg.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
									
								}
								return pg;
							}}); 
						
						// aggiorno l'id_utente_agg presente al db
						String updateIDUtenteAgg ="update PBANDI_T_ENTE_GIURIDICO set id_utente_agg = "+ soggetto.getIdUtenteAgg()
								+ ",  dt_fine_validita= trunc(sysdate)  "
								+ "where id_ente_giuridico = "+ soggetto.getIdEnteGiuridico() ;
						getJdbcTemplate().update(updateIDUtenteAgg); 
						
						
						BigDecimal idProgetto=null;
						// adesso controllo se si tratta di una modifica nella prsoggetto_progetto oppure prsoggetto_domanda; 
						 idProgetto = checkProgetto(idDomanda); 
						boolean isProgetto;
						if(idProgetto!=null) {
							isProgetto=true; 
						} else {
							isProgetto = false; 
						}
						
						// recupero il prossimo id dell'ente giuridico
						String sqlSeq = "SELECT SEQ_PBANDI_T_ENTE_GIURIDICO.nextval FROM dual";	
						BigDecimal identeGiuridico =  getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
						
						// controllo i dati anagrafici: 
						/// sezione dati anagrafici ente giuridico  se c''è stato una modifica:aggiorno questa parte
						if(soggetto.isDatiAnagrafici()==true) {		
							anagDB.setRagSoc(soggetto.getRagSoc());
							anagDB.setIdFormaGiuridica(soggetto.getIdFormaGiuridica());
							anagDB.setFlagPubblicoPrivato(soggetto.getFlagPubblicoPrivato());
							if(soggetto.getFlagPubblicoPrivato() ==2) {								
								anagDB.setCodUniIpa(soggetto.getCodUniIpa());
							}
							anagDB.setDtCostituzione(soggetto.getDtCostituzione());
							if(soggetto.getIdFormaGiuridica()!=null && soggetto.getIdFormaGiuridica()>0) {								
								anagDB.setIdFormaGiuridica(soggetto.getIdFormaGiuridica());
							}
							// aggiorno la tabella pbandi_t_sede per quanto riguarda la partita iva e la tabella pbandi_t_soggetto per il codiceFiscale, 
						} 
						// se è stata modificata la parte economica la modifico anche
						if(soggetto.isAttivitaEconomica()==true) {
							anagDB.setFlagRatingLeg(soggetto.getFlagRatingLeg());
							anagDB.setIdStatoAtt(soggetto.getIdStatoAtt());
							anagDB.setDtInizioAttEsito(soggetto.getDtInizioAttEsito());
							anagDB.setPeriodoScadEse(soggetto.getPeriodoScadEse());
							anagDB.setDtUltimoEseChiuso(soggetto.getDtUltimoEseChiuso());	
						}
						
						boolean insertEG = false;
				
						if(soggetto.isDatiAnagrafici()==true || soggetto.isAttivitaEconomica() == true) {
							anagDB.setIdUtenteIns(soggetto.getIdUtenteAgg());
							insertEG = insertDatiAnagrafici(anagDB, identeGiuridico);
								
							// controllo se il codice ateco e la partita iva sono diversi per la sede allora inserisco i dati dentro la pbandi_t_sede
							if(checkSede(soggetto.getIdSede(), soggetto.getIdAttAteco(), soggetto.getpIva()) == true) {
								// update pbandi_t_sede 
								String update = "update PBANDI_T_SEDE set id_utente_agg =?\r\n"
										+ "where id_sede=?";
								getJdbcTemplate().update(update, new Object[] {
										soggetto.getIdUtenteAgg(),
										soggetto.getIdSede()
								});
							} else {
							//inserisco nella tabella pbandi_t_sede con il codice ateco , la descrizione attivita ateco e la partita_iva
							 	insertIntoPbandiSede(soggetto); 
							}
							 
						} 
				
						if(insertEG== true) {
							// se ho fatto l'inserimento allora bisogna aggiornare le tabelle  PBANDI_R_SOGGETTO_DOMANDA o/e PBANDI_R_SOGGETTO_PROGETTO con il nuovo idEnteGiuridico
						
							String updatePRSogg = "update PBANDI_R_SOGGETTO_DOMANDA set "
									+ " DT_AGGIORNAMENTO = sysdate, "
									+ " ID_UTENTE_AGG="+ soggetto.getIdUtenteAgg()
									+ ", ID_ENTE_GIURIDICO ="+ identeGiuridico
									+ "  where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda();					
							getJdbcTemplate().update(updatePRSogg);
							
							
							if(soggetto.getProgSoggProgetto()!=null && isProgetto==true) {
								// aggiorno anche il suo idEnteGirudico
								String updatePrsp = "update PBANDI_R_SOGGETTO_PROGETTO set dt_aggiornamento=trunc(sysdate), id_utente_agg=?,\r\n"
								+ " ID_ENTE_GIURIDICO=?\r\n"
								+ "  where PROGR_SOGGETTO_PROGETTO=?";
								getJdbcTemplate().update(updatePrsp, new Object[] {
										soggetto.getIdUtenteAgg(),identeGiuridico, soggetto.getProgSoggProgetto()
								}); 
								
							}
										
						} else {
								// se non ho fatto l'inserimento allora basta aggiornare le tabelle PBANDI_R_SOGGETTO_DOMANDA o/e PBANDI_R_SOGGETTO_PROGETTO
								String updatePRSogg = "update PBANDI_R_SOGGETTO_DOMANDA set "
										+ " DT_AGGIORNAMENTO = sysdate, "
										+ " ID_UTENTE_AGG="+ soggetto.getIdUtenteAgg()
										+ " where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda();
						
								getJdbcTemplate().update(updatePRSogg);
								
								if(soggetto.getProgSoggProgetto()!=null && isProgetto==true) {
									String updatePrsp = "update PBANDI_R_SOGGETTO_PROGETTO set dt_aggiornamento= trunc(sysdate),"
											+ " id_utente_agg="+soggetto.getIdUtenteAgg()
											+ " where PROGR_SOGGETTO_PROGETTO="+soggetto.getProgSoggProgetto();
									
									getJdbcTemplate().update(updatePrsp); 
								}
							}
						// sezione sede legale PBANDI_T_INDIRIZZO 
						
						if(soggetto.isSedeLegale()==true) {
							// se presente l'id_indirizzo aggiorno i dati presenti sul db 
							if(checkMatchCompletoIndirizzo(soggetto.getIdComune(), soggetto.getIdNazione(), soggetto.getIdProvincia(),
									soggetto.getIdRegione(), idSoggetto, 0, soggetto.getIdDomanda())==true) {
								updateIndirizzoSedeLegale(soggetto, idSoggetto);
								
							} else {
								// inserisco i dati sul db e aggiorno l'id indirizzo sulla tabella PBANDI_R_SOGGETTO_DOMANDA_SEDE e PBANDI_R_SOGGETTO_DOMANDA_SEDE
								insertSedeLegale(soggetto, idSoggetto);
							}
						}
						
						
						// sezione dati iscrizione
						if(soggetto.isDatiIscrizione()==true) {
							if(checkIscrizione(soggetto, idSoggetto)) {
								// soggetto gia iscritto e faccio solo l'update
								String updateIscrizione = "update PBANDI_T_ISCRIZIONE set "
										+ " id_utente_agg="+ soggetto.getIdUtenteAgg()
										+ "  where id_iscrizione="+ soggetto.getIdIscrizione();
								getJdbcTemplate().update(updateIscrizione); 
								
							} else {
								// soggetto non iscritto e lo inserisco quando l'ho inserito aggiorno le tabelle PBANDI_R_SOGGETTO_DOMANDA e PBANDI_R_SOGGETTO_PROGETTO
								
								String getId = "SELECT SEQ_PBANDI_T_ISCRIZIONE.nextval FROM dual";	
								BigDecimal idIscrizione=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
								soggetto.setFlagIscrizione("S");
								String insertIscrizione= "insert into\r\n"
										+ "    PBANDI_T_ISCRIZIONE (\r\n"
										+ "        id_iscrizione,\r\n"
										+ "        num_iscrizione,\r\n"
										+ "        dt_iscrizione,\r\n"
										+ "        FLAG_ISCRIZIONE_IN_CORSO,\r\n"
										+ "        id_utente_ins,\r\n"
										+ "        id_tipo_iscrizione,\r\n"
										+ "        id_provincia,\r\n"
										+ "        dt_inizio_validita)\r\n"
										+ "values (?,?,?,?,?,?,?, sysdate)";
								
								getJdbcTemplate().update(insertIscrizione, new Object[] {
									idIscrizione, 
									soggetto.getNumIscrizione(), 
									(soggetto.getDtIscrizione()!=null)? soggetto.getDtIscrizione():null, 
									soggetto.getFlagIscrizione().trim(),
									soggetto.getIdUtenteAgg(), 
									(soggetto.getIdTipoIscrizione()>0)?soggetto.getIdTipoIscrizione():2, 
									soggetto.getIdProvinciaIscrizione()
								});
								// update la tabella PBANDI_R_SOGGETTO_DOMANDA
									String updateRSoggDom="update PBANDI_R_SOGGETTO_DOMANDA set "
											+ " ID_ISCRIZIONE_PERSONA_GIURID = "+idIscrizione+",\r\n "
											+ "dt_aggiornamento= sysdate"
											+ ",id_utente_agg="+ soggetto.getIdUtenteAgg()
											+ "where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda(); 
									
									getJdbcTemplate().update(updateRSoggDom); 
									
									if(soggetto.getProgSoggProgetto()!=null && isProgetto==true) {
										
										String updatePrsp = "update PBANDI_R_SOGGETTO_PROGETTO set ID_ISCRIZIONE_PERSONA_GIURID= ?\r\n, id_utente_agg=?\r\n"
										+ " where PROGR_SOGGETTO_PROGETTO=?";
										getJdbcTemplate().update(updatePrsp, new Object[] {
												idIscrizione,soggetto.getIdUtenteAgg(), soggetto.getProgSoggProgetto()
										}); 
									}
								
							}
						}
						
						// sezione di appartenenza
						Long idSezioneSpec ; 
						if(soggetto.isSezioneAppartenenza()==true) {
							try {
								String checkSezione ="select\r\n"
										+ "    ID_SEZIONE_SPECIALE\r\n"
										+ "from\r\n"
										+ "    PBANDI_T_ENTE_GIUR_SEZI\r\n"
										+ "where\r\n"
										+ "    id_soggetto ="+idSoggetto+"\r\n"
										+ "    and DT_FINE_VALIDITA is null\r\n"
										+ "	  and rownum<2";
								
								 idSezioneSpec = getJdbcTemplate().queryForObject(checkSezione, Long.class);
							} catch (Exception e) {
								idSezioneSpec = null;  
							} 
							
							// se non c'è il recod sullla tabella allora lo metto sennò
							if(idSezioneSpec==null) {
								String getId = "SELECT SEQ_PBANDI_T_ENTE_GIUR_SEZI.nextval FROM dual";	
								BigDecimal idSezione=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
								
								String insertSezione= "insert into PBANDI_T_ENTE_GIUR_SEZI ("
										+ "id_ente_sezione, "
										+ "id_soggetto, "
										+ "id_sezione_speciale, "
										+ "id_utente_ins, "
										+ "dt_inizio_validita) values(?,?,?,?,sysdate)"; 
								
								getJdbcTemplate().update(insertSezione, new Object[] {
										idSezione, 
										idSoggetto, 
										soggetto.getIdSezioneSpeciale(),
										soggetto.getIdUtenteAgg()
									});
							}
							
							// aggiorno nella tabella con il nuovo id sezione spec quando l'id_sezione speciale è diverso
							if(idSezioneSpec!=null &&  idSezioneSpec!=soggetto.getIdSezioneSpeciale()) {								
								String updateSezioneAp="update PBANDI_T_ENTE_GIUR_SEZI set "
										+ "id_utente_agg= ?"
										+ ",ID_SEZIONE_SPECIALE= ? "
										+" where id_ente_sezione=?"; 				
								getJdbcTemplate().update(updateSezioneAp, new Object[] {
										soggetto.getIdUtenteAgg(), 
										soggetto.getIdSezioneSpeciale(), 
										soggetto.getIdEnteSezione()
								}); 
							}
						
						}

		
		RecapitoVO rec = new RecapitoVO();	
		if(isSoggettoCorrelato== true) {
			rec = checkPecSoggettiCorrelati(idSoggetto, idDomanda); 
		}else {			
			rec = checkPec(idSoggetto,  idDomanda); 
		}
		Long idRecap; 
//		se la pec di rec è null e quella che mi arriva not null allora la inserisco 
		if((rec.getPec()==null) && soggetto.getDescPec()!=null) {
			idRecap = insertPec(rec, soggetto.getDescPec(), idSoggetto, soggetto.getIdUtenteAgg()); 
			if(idRecap!=null && idRecap>0 ) {
				if(rec.getProgrSoggettoProgettoSede()!=null && rec.getProgrSoggettoProgettoSede()>0) {
					// update tabella PBANDI_R_SOGG_PROGETTO_SEDE per la sede legale; 
					String update ="UPDATE PBANDI_R_SOGG_PROGETTO_SEDE SET ID_RECAPITI = "+idRecap+"\r\n"
							+ "WHERE ID_TIPO_SEDE =1  \r\n"
							+ "AND PROGR_SOGGETTO_PROGETTO_SEDE="+ rec.getProgrSoggettoProgettoSede();
					getJdbcTemplate().update(update); 
				} else {
					// update la tabella PROGR_SOGGETTO_DOMANDA_SEDE
					String update ="UPDATE PBANDI_R_SOGGETTO_DOMANDA_SEDE SET ID_RECAPITI = "+idRecap+"\r\n"
							+ "WHERE ID_TIPO_SEDE =1  \r\n"
							+ "AND PROGR_SOGGETTO_DOMANDA_SEDE ="+rec.getProgrSoggettoDomandaSede();
					getJdbcTemplate().update(update);
				}
			}
		} else if(rec.getPec()!=null && !(rec.getPec().trim().equals(soggetto.getDescPec().trim()))) {
			// inserisco la nuova pec 
			idRecap = insertPec(rec, soggetto.getDescPec(), idSoggetto, soggetto.getIdUtenteAgg()); 
			if(idRecap!=null && idRecap>0 ) {
				
				if(rec.getProgrSoggettoProgettoSede()!=null && rec.getProgrSoggettoProgettoSede()>0) {
					// update tabella PBANDI_R_SOGG_PROGETTO_SEDE per la sede legale; 
					String update ="UPDATE PBANDI_R_SOGG_PROGETTO_SEDE SET ID_RECAPITI = "+idRecap+"\r\n"
							+ "WHERE ID_TIPO_SEDE =1  \r\n"
							+ "AND PROGR_SOGGETTO_PROGETTO_SEDE="+ rec.getProgrSoggettoProgettoSede();
					getJdbcTemplate().update(update); 
				} else {
					// update la tabella PROGR_SOGGETTO_DOMANDA_SEDE
					String update ="UPDATE PBANDI_R_SOGGETTO_DOMANDA_SEDE SET ID_RECAPITI = "+idRecap+"\r\n"
							+ "WHERE ID_TIPO_SEDE =1  \r\n"
							+ "AND PROGR_SOGGETTO_DOMANDA_SEDE ="+rec.getProgrSoggettoDomandaSede();
					getJdbcTemplate().update(update);
				}
			}
		}
		result = true; 
		
		
		// CHIAMATA AL SERVIZIO DELLE SEGNALZIONI PER I CAMPI MODIFICATI 
		sistemaDiBlocchi( Long.parseLong(idSoggetto), soggetto.getIdUtenteAgg(), soggetto.getCampiModificati()); 
		
		// Chiamata di notifica Finistr 
		
		Boolean respNotiFinistr = sistemaDiNotificheFinistr(soggetto.getCampiModificati(), idDomanda, Long.parseLong(idSoggetto), soggetto.getRagSoc(), soggetto.getIdFormaGiuridica());
		LOG.info(prf + " Il metodo sistemaDiNotificheFinistr() ha risposto con " + respNotiFinistr);
		
		}catch (Exception e) {
			result = false; 
			LOG.error(prf + "Exception during updateAnagBeneGiuridico", e);
			throw new ErroreGestitoException("Exception during updateAnagBeneGiuridico", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result; 
	}
	private RecapitoVO checkPecSoggettiCorrelati(String idSoggetto, String idDomanda) {
		 
		RecapitoVO recapito = new RecapitoVO(); 
		try {
			String getPec =null; 	
		
			BigDecimal idProgetto = checkProgetto(idDomanda);
			
			if(idProgetto!=null) {
				
				 getPec ="SELECT\r\n"
				 		+ "    ptr.PEC,\r\n"
				 		+ "    ptr.ID_RECAPITI,\r\n"
				 		+ "    prsps.PROGR_SOGGETTO_PROGETTO_SEDE,\r\n"
				 		+ "    ptr.FAX,\r\n"
				 		+ "    ptr.EMAIL,\r\n"
				 		+ "    ptr.TELEFONO\r\n"
				 		+ "FROM\r\n"
				 		+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
				 		+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsps.ID_RECAPITI\r\n"
				 		+ "WHERE\r\n"
				 		+ "    --prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
				 		+ "     prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
				 		+ "    AND prsps.ID_TIPO_SEDE = 1\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto+"\r\n"
						+ "    AND prsp.ID_SOGGETTO ="+ idSoggetto; 
				 
					recapito = getJdbcTemplate().query(getPec, new ResultSetExtractor<RecapitoVO>() {

						
						@Override
						public RecapitoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
							RecapitoVO rec = new RecapitoVO();
							while(rs.next() ) {
								
								rec.setProgrSoggettoProgettoSede(rs.getLong("PROGR_SOGGETTO_PROGETTO_SEDE"));
								rec.setIdRecapiti(rs.getLong("ID_RECAPITI"));
								rec.setPec(rs.getString("PEC"));
								rec.setEmail(rs.getString("EMAIL"));
								rec.setFax(rs.getString("FAX"));
								rec.setTelefono(rs.getString("TELEFONO"));
							}
							return rec;
						}
						
					});
					
			} else {
				
				getPec="SELECT\r\n"
						+ "    ptr.PEC,\r\n"
						+ "    ptr.ID_RECAPITI,\r\n"
						+ "    prsds.PROGR_SOGGETTO_DOMANDA_SEDE,\r\n"
						+ "    ptr.FAX,\r\n"
						+ "    ptr.EMAIL,\r\n"
						+ "    ptr.TELEFONO\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsds.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    --prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "     prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND prsds.ID_TIPO_SEDE = 1\r\n"
						+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "    AND ptd.NUMERO_DOMANDA = '"+idDomanda+"'"; 
				
				recapito = getJdbcTemplate().query(getPec, new ResultSetExtractor<RecapitoVO>() {

					@Override
					public RecapitoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
						RecapitoVO rec = new RecapitoVO();
						while (rs.next())
						{							
							rec.setProgrSoggettoDomandaSede(rs.getLong("PROGR_SOGGETTO_DOMANDA_SEDE"));
							rec.setIdRecapiti(rs.getLong("ID_RECAPITI"));
							rec.setPec(rs.getString("PEC"));
							rec.setEmail(rs.getString("EMAIL"));
							rec.setFax(rs.getString("FAX"));
							rec.setTelefono(rs.getString("TELEFONO"));
						}
						return rec;
					}
					
				});			
			}
				
		} catch (Exception e) {
			LOG.error("Exception during updateAnagBeneGiuridico", e);
		}
		return recapito;
	}

	private Long insertPec(RecapitoVO rec, String descPec, String idSoggetto, Long idUtente) {
		Long idRecap ; 
		try {
			
			 idRecap =getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_RECAPITI.nextval FROM dual", Long.class);
			
			String insert = "INSERT INTO PBANDI_T_RECAPITI (ID_RECAPITI,\r\n"
					+ "EMAIL,\r\n"
					+ "FAX,\r\n"
					+ "TELEFONO,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "PEC) VALUES (?,?,?,?,?, trunc(sysdate), ?)";
			getJdbcTemplate().update(insert, new Object[] {
				idRecap, rec.getEmail(), rec.getFax(), rec.getTelefono(), idUtente, descPec	
			});
			// aggiorno 
			String update="update PBANDI_T_RECAPITI SET DT_FINE_VALIDITA= trunc(sysdate) ,ID_UTENTE_AGG="+ idUtente+"\r\n"
					+ " WHERE ID_RECAPITI="+ rec.getIdRecapiti();
			getJdbcTemplate().update(update);
			
		} catch (Exception e) {
			LOG.error("Exception during updateAnagBeneGiuridico", e);
			return null ;
		}
		
		return idRecap;
	}


	private RecapitoVO checkPec(String idSoggetto, String idDomanda) {
		 
		RecapitoVO recapito = new RecapitoVO(); 
		try {
			String getPec =null; 	
		
			BigDecimal idProgetto = checkProgetto(idDomanda);
			
			if(idProgetto!=null) {
				
				 getPec ="SELECT\r\n"
				 		+ "    ptr.PEC,\r\n"
				 		+ "    ptr.ID_RECAPITI,\r\n"
				 		+ "    prsps.PROGR_SOGGETTO_PROGETTO_SEDE,\r\n"
				 		+ "    ptr.FAX,\r\n"
				 		+ "    ptr.EMAIL,\r\n"
				 		+ "    ptr.TELEFONO\r\n"
				 		+ "FROM\r\n"
				 		+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
				 		+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsps.ID_RECAPITI\r\n"
				 		+ "WHERE\r\n"
				 		+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
				 		+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
				 		+ "    AND prsps.ID_TIPO_SEDE = 1\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto+"\r\n"
						+ "    AND prsp.ID_SOGGETTO ="+ idSoggetto; 
				 
					recapito = getJdbcTemplate().query(getPec, new ResultSetExtractor<RecapitoVO>() {

						
						@Override
						public RecapitoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
							RecapitoVO rec = new RecapitoVO();
							while(rs.next() ) {
								
								rec.setProgrSoggettoProgettoSede(rs.getLong("PROGR_SOGGETTO_PROGETTO_SEDE"));
								rec.setIdRecapiti(rs.getLong("ID_RECAPITI"));
								rec.setPec(rs.getString("PEC"));
								rec.setEmail(rs.getString("EMAIL"));
								rec.setFax(rs.getString("FAX"));
								rec.setTelefono(rs.getString("TELEFONO"));
							}
							return rec;
						}
						
					});
					
			} else {
				
				getPec="SELECT\r\n"
						+ "    ptr.PEC,\r\n"
						+ "    ptr.ID_RECAPITI,\r\n"
						+ "    prsds.PROGR_SOGGETTO_DOMANDA_SEDE,\r\n"
						+ "    ptr.FAX,\r\n"
						+ "    ptr.EMAIL,\r\n"
						+ "    ptr.TELEFONO\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsds.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsds.ID_TIPO_SEDE = 1\r\n"
						+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "    AND ptd.NUMERO_DOMANDA = '"+idDomanda+"'"; 
				
				recapito = getJdbcTemplate().query(getPec, new ResultSetExtractor<RecapitoVO>() {

					@Override
					public RecapitoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
						RecapitoVO rec = new RecapitoVO();
						while (rs.next())
						{							
							rec.setProgrSoggettoDomandaSede(rs.getLong("PROGR_SOGGETTO_DOMANDA_SEDE"));
							rec.setIdRecapiti(rs.getLong("ID_RECAPITI"));
							rec.setPec(rs.getString("PEC"));
							rec.setEmail(rs.getString("EMAIL"));
							rec.setFax(rs.getString("FAX"));
							rec.setTelefono(rs.getString("TELEFONO"));
						}
						return rec;
					}
					
				});			
			}
				
		} catch (Exception e) {
			LOG.error("Exception during updateAnagBeneGiuridico", e);
		}
		return recapito;
	}


	private BigDecimal insertIntoPbandiSede(AnagraficaBeneficiarioVO soggetto) {
		
		BigDecimal progrSoggProgettoSede = null; 
		try {
			
		BigDecimal idSede = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_SEDE.nextval FROM dual", BigDecimal.class);
			String insert = "insert into pbandi_t_sede(id_sede, partita_iva, id_attivita_ateco, id_utente_ins, dt_inizio_validita) values(?,?,?,?,trunc(sysdate))";
			
			getJdbcTemplate().update(insert, new Object[] {
				idSede, 
				soggetto.getpIva(), 
				soggetto.getIdAttAteco(), 
				soggetto.getIdUtenteAgg()
			});
			
			String updateIndirizoIntoRsoggSede = "update PBANDI_R_SOGGETTO_DOMANDA_SEDE set "
					+ " id_sede=?\r\n"
					+", id_utente_agg=?\r\n"
					+" where PROGR_SOGGETTO_DOMANDA=?\r\n" ;
			getJdbcTemplate().update(updateIndirizoIntoRsoggSede, new Object[] {
					 idSede, soggetto.getIdUtenteAgg(),soggetto.getProgSoggDomanda()
			}); 
			
			
			if(soggetto.getProgSoggProgetto()!=null) {
				// Prima controllo se dentro la tabella pbandi_r_sogg_progetto_sede ci sia il PROGR_SOGGETTO_PROGETTO_SEDE
				progrSoggProgettoSede = getProgrSoggProgettoSede(soggetto); 
				if(progrSoggProgettoSede!=null) {					
					String updateIndirizoIntoRSoggProgSede = "update PBANDI_R_SOGG_PROGETTO_SEDE set "
							+ " id_SEDE=?\r\n"
							+", id_utente_agg=?\r\n"
							+" where PROGR_SOGGETTO_PROGETTO=?\r\n";
					getJdbcTemplate().update(updateIndirizoIntoRSoggProgSede, new Object[] {
							idSede, 
							soggetto.getIdUtenteAgg(), 
							soggetto.getProgSoggProgetto(),
					}); 
				} else {
					
				progrSoggProgettoSede= 	insertIntoProgrSoggProgettoSede(soggetto, idSede);
				}
			}
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::insertIntoPbandiSede] Exception " + e.getMessage());
		}
		
		return progrSoggProgettoSede; 
	}


	private BigDecimal insertIntoProgrSoggProgettoSede(AnagraficaBeneficiarioVO soggetto, BigDecimal idSede) {
		BigDecimal progrSoggProgettoSede= null; 
		try {
			progrSoggProgettoSede = getJdbcTemplate().queryForObject("select SEQ_PBANDI_R_SOGG_PROG_SEDE.nextVal from dual", BigDecimal.class);
			String sql = "insert into PBANDI_R_SOGG_PROGETTO_SEDE(PROGR_SOGGETTO_PROGETTO_SEDE,\r\n"
					+ "PROGR_SOGGETTO_PROGETTO,\r\n"
					+ "ID_SEDE,\r\n"
					+ "ID_TIPO_SEDE,\r\n"
					+ "ID_UTENTE_INS\r\n"
					+") VALUES(?,?,?,1,?)";
			getJdbcTemplate().update(sql, new Object[] {
					progrSoggProgettoSede, 
					soggetto.getProgSoggProgetto(),
					idSede, 
					soggetto.getIdUtenteAgg()
			});
			
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::insertIntoProgrSoggProgettoSede] Exception " + e.getMessage());
			return null; 
		}
		return progrSoggProgettoSede;
	}


	private BigDecimal getProgrSoggProgettoSede(AnagraficaBeneficiarioVO soggetto) {
		BigDecimal progrSede= null; 
		try {
			String sql = "SELECT prsps.PROGR_SOGGETTO_PROGETTO_SEDE \r\n"
					+ "	FROM pbandi_r_soggetto_progetto prsp \r\n"
					+ "	LEFT JOIN pbandi_r_sogg_progetto_sede prsps ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO \r\n"
					+ "	WHERE prsp.PROGR_SOGGETTO_PROGETTO =?";
			
			progrSede = getJdbcTemplate().queryForObject(sql, new Object[] {
					soggetto.getProgSoggProgetto()
			},BigDecimal.class);
		} catch (Exception e) {
			
			return null;
		}
		return progrSede;
	}


	private boolean checkSede(Long idSede, Long idAttAteco, String getpIva) {
		try {
			String getInfo = "Select id_attivita_ateco, PARTITA_IVA from pbandi_t_sede where id_sede="+ idSede;
			
			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {

				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						AttivitaDTO att = new AttivitaDTO(); 
					
					att.setIdAttivita(rs.getLong("id_attivita_ateco"));
					att.setDescAttivita(rs.getString("PARTITA_IVA"));
					return att;
				}	
			};
			
			List<AttivitaDTO> res = new ArrayList<AttivitaDTO>(); 
			res = getJdbcTemplate().query(getInfo, lista);
			
			if(res.get(0).getIdAttivita()==idAttAteco && res.get(0).getDescAttivita().trim().equals(getpIva.trim())) {
				return true; 
			}
			
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::checkSede] Exception " + e.getMessage());
		}
		
		return false;
	}


	/////////////////////////////////////////FUNZIONI DI UTILITY///////////////////////////////////////
	private void insertEnteGiuridico(AnagraficaBeneficiarioVO anagBene, Long idUtente) throws Exception {

		PbandiTEnteGiuridicoVO pbandiTEnteGiuridicoVO = new PbandiTEnteGiuridicoVO();

		int idEnteGiuridico = anagBene.getIdEnteGiuridico().intValue()+1;

		pbandiTEnteGiuridicoVO.setIdSoggetto(BigDecimal.valueOf(anagBene.getIdSoggetto()));
		pbandiTEnteGiuridicoVO.setDenominazioneEnteGiuridico(anagBene.getRagSoc());
		pbandiTEnteGiuridicoVO.setDtCostituzione(anagBene.getDtCostituzione());
		pbandiTEnteGiuridicoVO.setCapitaleSociale(BigDecimal.valueOf(anagBene.getCapSociale()));
		pbandiTEnteGiuridicoVO.setIdAttivitaUic(BigDecimal.valueOf(anagBene.getIdAttIuc()));
		pbandiTEnteGiuridicoVO.setIdFormaGiuridica(BigDecimal.valueOf(anagBene.getIdFormaGiuridica()));
		pbandiTEnteGiuridicoVO.setIdClassificazioneEnte(BigDecimal.valueOf(anagBene.getIdClassEnte()));
		pbandiTEnteGiuridicoVO.setIdDimensioneImpresa(BigDecimal.valueOf(anagBene.getIdDimensioneImpresa()));
		pbandiTEnteGiuridicoVO.setIdEnteGiuridico(BigDecimal.valueOf(idEnteGiuridico));
		pbandiTEnteGiuridicoVO.setDtInizioValidita(anagBene.getDtInizioVal());		
		pbandiTEnteGiuridicoVO.setDtFineValidita(anagBene.getDtFineVal());
		pbandiTEnteGiuridicoVO.setDtUltimoEsercizioChiuso(anagBene.getDtUltimoEseChiuso());
		pbandiTEnteGiuridicoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
		pbandiTEnteGiuridicoVO.setFlagPubblicoPrivato(BigDecimal.valueOf(anagBene.getFlagPubblicoPrivato()));
		pbandiTEnteGiuridicoVO.setCodUniIpa(anagBene.getCodUniIpa());

		this.genericDAO.insert(pbandiTEnteGiuridicoVO);

	}

	private void insertIndirizzo(AnagraficaBeneficiarioVO anagBene, Long idUtente, Date currentDate) throws Exception {

		PbandiTIndirizzoVO pbandiTIndirizzoVO = new PbandiTIndirizzoVO();

		pbandiTIndirizzoVO.setIdIndirizzo(BigDecimal.valueOf(anagBene.getIdIndirizzo()));
		pbandiTIndirizzoVO.setDescIndirizzo(anagBene.getDescIndirizzo());
		pbandiTIndirizzoVO.setCap(anagBene.getCap());
		pbandiTIndirizzoVO.setIdNazione(BigDecimal.valueOf(anagBene.getIdNazione()));
		pbandiTIndirizzoVO.setIdComune(BigDecimal.valueOf(anagBene.getIdComune()));
		pbandiTIndirizzoVO.setIdProvincia(BigDecimal.valueOf(anagBene.getIdProvincia()));
		pbandiTIndirizzoVO.setDtInizioValidita(currentDate);
		pbandiTIndirizzoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
		pbandiTIndirizzoVO.setIdRegione(BigDecimal.valueOf(anagBene.getIdRegione()));

		this.genericDAO.insert(pbandiTIndirizzoVO);

	}

	private void insertSede(AnagraficaBeneficiarioVO anagBene, Long idUtente, Date currentDate) throws Exception {

		PbandiTSedeVO pbandiTSedeVO = new PbandiTSedeVO();

		pbandiTSedeVO.setIdSede(BigDecimal.valueOf(anagBene.getIdSede()));
		pbandiTSedeVO.setPartitaIva(anagBene.getpIva());
		pbandiTSedeVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
		pbandiTSedeVO.setDtInizioValidita(currentDate);
		pbandiTSedeVO.setIdAttivitaAteco(BigDecimal.valueOf(anagBene.getIdAttAteco()));

		this.genericDAO.insert(pbandiTSedeVO);

	}

	private void insertIscrizione(AnagraficaBeneficiarioVO anagBene, Long idUtente, Date currentDate) throws Exception {

		PbandiTIscrizioneVO pbandiTIscrizioneVO = new PbandiTIscrizioneVO();

		pbandiTIscrizioneVO.setIdIscrizione(BigDecimal.valueOf(anagBene.getIdIscrizione()));
		pbandiTIscrizioneVO.setNumIscrizione(anagBene.getNumIscrizione());
		pbandiTIscrizioneVO.setDtIscrizione(anagBene.getDtIscrizione());
		pbandiTIscrizioneVO.setFlagIscrizioneInCorso(anagBene.getFlagIscrizione());
		pbandiTIscrizioneVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
		pbandiTIscrizioneVO.setIdTipoIscrizione(BigDecimal.valueOf(anagBene.getIdTipoIscrizione()));
		pbandiTIscrizioneVO.setIdProvincia(BigDecimal.valueOf(anagBene.getIdProvinciaIscrizione()));
		pbandiTIscrizioneVO.setDtInizioValidita(currentDate);

		this.genericDAO.insert(pbandiTIscrizioneVO);

	}

	private void updateSoggDomanda(AnagraficaBeneficiarioVO anagBene, Long idUtente, Date currentDate, Boolean isUpdate) throws Exception {

		PbandiRSoggettoDomandaVO pbandiRSoggettoDomVO = new PbandiRSoggettoDomandaVO();

		if(isUpdate) {
			pbandiRSoggettoDomVO.setIdEnteGiuridico(BigDecimal.valueOf(anagBene.getIdEnteGiuridico()));
			pbandiRSoggettoDomVO.setDtAggiornamento(currentDate);
			pbandiRSoggettoDomVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
		}else {
			int idEnteGiuridico = anagBene.getIdEnteGiuridico().intValue()+1;

			pbandiRSoggettoDomVO.setIdEnteGiuridico(BigDecimal.valueOf(idEnteGiuridico));
			pbandiRSoggettoDomVO.setDtAggiornamento(currentDate);
			pbandiRSoggettoDomVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
		}

		this.genericDAO.update(pbandiRSoggettoDomVO);

	}


	private void updateSoggDomandaSede(AnagraficaBeneficiarioVO anagBene, Long idUtente, Date currentDate) throws Exception {

		PbandiRSoggettoDomandaSedeVO pbandiRSoggettoDomSedeVO = new PbandiRSoggettoDomandaSedeVO();

		pbandiRSoggettoDomSedeVO.setIdIndirizzo(BigDecimal.valueOf(anagBene.getIdIndirizzo()));
		pbandiRSoggettoDomSedeVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
		pbandiRSoggettoDomSedeVO.setIdSede(BigDecimal.valueOf(anagBene.getIdSede()));

		this.genericDAO.update(pbandiRSoggettoDomSedeVO);

	}

	private void updateSoggetto(AnagraficaBeneficiarioVO anagBene, Long idUtente, Date currentDate, Long idSoggetto) throws Exception {

		String sql = "UPDATE PBANDI_T_SOGGETTO SET CODICE_FISCALE_SOGGETTO = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = ? WHERE ID_SOGGETTO = ?";

		Object[] args = new Object[]{anagBene.getCfSoggetto(), idUtente.intValue(), currentDate, idSoggetto.intValue()};

		int[] types = new int[]{Types.VARCHAR, Types.INTEGER, Types.DATE, Types.INTEGER};

		getJdbcTemplate().update(sql, args, types);

	}

	
	@Override
	public AnagAltriDati_MainVO getAltriDati(Long idSoggetto, Long idEnteGiuridico, String numeroDomanda, HttpServletRequest req)throws DaoException {
		
	 	AnagAltriDati_MainVO main = new AnagAltriDati_MainVO();
		
		List<AnagAltriDati_MainVO> dimImpr = new ArrayList<AnagAltriDati_MainVO>();

		List<AnagAltriDati_MainVO> durc = new ArrayList<AnagAltriDati_MainVO>();
		
		List<AnagAltriDati_MainVO> bdna = new ArrayList<AnagAltriDati_MainVO>();
				
		List<AnagAltriDati_MainVO> antiMafia = new ArrayList<AnagAltriDati_MainVO>();
		
		
			// DIMENSIONE IMPRESA
		String queryDimImp = "SELECT\r\n"
				+ "    pteg.DT_VALUTAZIONE_ESITO,\r\n"
				+ "    pddi.DESC_DIMENSIONE_IMPRESA\r\n"
				+ "FROM\r\n"
				+ "    PBANDI_T_ENTE_GIURIDICO pteg\r\n"
				+ "    LEFT JOIN PBANDI_D_DIMENSIONE_IMPRESA pddi ON pteg.ID_DIMENSIONE_IMPRESA = pddi.ID_DIMENSIONE_IMPRESA\r\n"
				+ "WHERE\r\n"
				+ "    pteg.ID_ENTE_GIURIDICO  = ?\r\n"
				+ "    AND pteg.DT_VALUTAZIONE_ESITO IS NOT NULL ";

		//dimImpr = getJdbcTemplate().query(queryDimImp, new AnagBenAltriDati_DurcRowMapper());
		//main.setDimImpresa(dimImpr);
		//LOG.info(durc);

		ResultSetExtractor<List<AnagAltriDati_MainVO>> rseDimImp = new ResultSetExtractor<List<AnagAltriDati_MainVO>>()
		{
			@Override
			public List<AnagAltriDati_MainVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next())
				{
					AnagAltriDati_MainVO v = new AnagAltriDati_MainVO();

					v.setDimImpr_dataValutazione(rs.getString("DT_VALUTAZIONE_ESITO"));
					v.setDimImpr_esito(rs.getString("DESC_DIMENSIONE_IMPRESA"));
					dimImpr.add(v);
				}
				return dimImpr;
			}
		};
		main.setDimImpresa(getJdbcTemplate().query(queryDimImp, rseDimImp, new Object[] {idEnteGiuridico}));
		if(main.getDimImpresa().size() > 0) {	
			List<DettaglioImpresaVO> lista = new ArrayList<DettaglioImpresaVO>();
			lista = getDatiDimensioneSoggetto(idEnteGiuridico); 
			if(lista!=null) {				
				main.getDimImpresa().get(0).setDettaglio(lista);
			}
		}
		
		
			// DURC
		String queryDurc = "  SELECT\r\n"
				+ "    durc.DT_EMISSIONE_DURC,\r\n"
				+ "    tiesiri.DESC_ESITO_RICHIESTE,\r\n"
				+ "    durc.DT_INIZIO_VALIDITA,\r\n"
				+ "    durc.DT_SCADENZA,\r\n"
				+ "    durc.NUM_PROTOCOLLO_INPS, \r\n"
				+ "    ptdi.ID_DOCUMENTO_INDEX , \r\n"
				+ "    ptdi.NOME_DOCUMENTO, ptdi.ID_TIPO_DOCUMENTO_INDEX  \r\n"
				+ "FROM\r\n"
				+ "    PBANDI_T_SOGGETTO_DURC durc\r\n"
				+ "    LEFT JOIN PBANDI_D_TIPO_ESITO_RICHIESTE tiesiri ON durc.ID_TIPO_ESITO_DURC = tiesiri.ID_TIPO_ESITO_RICHIESTE\r\n"
				+ "    LEFT JOIN PBANDI_T_DOCUMENTO_INDEX ptdi ON ptdi.ID_TARGET = durc.ID_SOGGETTO_DURC AND ptdi.ID_ENTITA IN (SELECT c.ID_ENTITA FROM PBANDI_C_ENTITA c WHERE c.NOME_ENTITA ='PBANDI_T_SOGGETTO_DURC' )\r\n"
				+ "WHERE\r\n"
				+ "    durc.ID_SOGGETTO ="+idSoggetto;

		ResultSetExtractor<List<AnagAltriDati_MainVO>> rseDurc = new ResultSetExtractor<List<AnagAltriDati_MainVO>>()
		{
			@Override
			public List<AnagAltriDati_MainVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next())
				{
					AnagAltriDati_MainVO v = new AnagAltriDati_MainVO();

					v.setDurc_dataEmiss(rs.getDate("DT_EMISSIONE_DURC"));
					v.setDurc_esito(rs.getString("DESC_ESITO_RICHIESTE"));
					v.setDurc_dataScadenza(rs.getDate("DT_SCADENZA"));
					v.setDurc_numProto(rs.getString("NUM_PROTOCOLLO_INPS"));
					v.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
					v.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
					v.setIdTipoDocumentoIndex(rs.getLong("ID_TIPO_DOCUMENTO_INDEX"));
					durc.add(v);
				}
				return durc;
			}
		};
		main.setDurc(getJdbcTemplate().query(queryDurc, rseDurc));
		
	
		// BDNA
		String queryBdna = "with selec as (SELECT dom.NUMERO_DOMANDA, antima.DT_RICEZIONE_BDNA, antima.DT_SCADENZA_ANTIMAFIA, antima.NUMER_PROTOCOLLO_RICEVUTA ,antima.DT_INIZIO_VALIDITA\r\n"
				+ "FROM PBANDI_T_DOMANDA dom\r\n"
				+ " JOIN PBANDI_T_SOGGETTO_ANTIMAFIA antima ON dom.ID_DOMANDA = antima.ID_DOMANDA\r\n"
				+ "WHERE dom.NUMERO_DOMANDA  = '" + numeroDomanda + "'\r\n"
				+ " order by antima.DT_INIZIO_VALIDITA DESC) \r\n"
				+ " select * from selec where rownum <2 \r\n";

		ResultSetExtractor<List<AnagAltriDati_MainVO>> rseBdna = new ResultSetExtractor<List<AnagAltriDati_MainVO>>()
		{
			@Override
			public List<AnagAltriDati_MainVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next())
				{
					AnagAltriDati_MainVO v = new AnagAltriDati_MainVO();

					v.setBdna_numDomanda(rs.getString("NUMERO_DOMANDA"));
					v.setBdna_dataRic(rs.getDate("DT_RICEZIONE_BDNA"));
					v.setBdna_numProto(rs.getString("NUMER_PROTOCOLLO_RICEVUTA"));
					bdna.add(v);
				}
				return bdna;
			}
		};
		main.setBdna(getJdbcTemplate().query(queryBdna, rseBdna));

		
		
		// ANTIMAFIA
		String queryAntima = "  SELECT\r\n"
				+ "  dom.NUMERO_DOMANDA,\r\n"
				+ "  soanti.DT_EMISSIONE,\r\n"
				+ "  soanti.DT_INIZIO_VALIDITA,\r\n"
				+ "  soanti.DT_SCADENZA_ANTIMAFIA,\r\n"
				+ "  soanti.NUM_PROTOCOLLO_PREFETTURA,\r\n"
				+ "  esiri.DESC_ESITO_RICHIESTE,\r\n"
				+ "  ptdi.ID_DOCUMENTO_INDEX,\r\n"
				+ "  ptdi.NOME_DOCUMENTO\r\n"
				+ "FROM\r\n"
				+ "  PBANDI_T_DOMANDA dom\r\n"
				+ "  JOIN PBANDI_T_SOGGETTO_ANTIMAFIA soanti ON dom.ID_DOMANDA = soanti.ID_DOMANDA\r\n"
				+ "  JOIN PBANDI_D_TIPO_ESITO_RICHIESTE esiri ON soanti.ID_TIPO_ESITO_ANTIMAFIA = esiri.ID_TIPO_ESITO_RICHIESTE\r\n"
				+ "  LEFT JOIN PBANDI_T_DOCUMENTO_INDEX ptdi ON ptdi.ID_TARGET = soanti.ID_SOGGETTO_ANTIMAFIA\r\n"
				+ "  AND ptdi.ID_ENTITA IN (\r\n"
				+ "    SELECT\r\n"
				+ "      c.ID_ENTITA\r\n"
				+ "    FROM\r\n"
				+ "      PBANDI_C_ENTITA c\r\n"
				+ "    WHERE\r\n"
				+ "      c.NOME_ENTITA = 'PBANDI_T_SOGGETTO_ANTIMAFIA'\r\n"
				+ "  )\r\n"
				+ "WHERE\r\n"
				+ "  dom.NUMERO_DOMANDA = '"+numeroDomanda+"'\r\n"
				+ "";



		ResultSetExtractor<List<AnagAltriDati_MainVO>> rseAntima = new ResultSetExtractor<List<AnagAltriDati_MainVO>>()
		{
			@Override
			public List<AnagAltriDati_MainVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next())
				{
					AnagAltriDati_MainVO v = new AnagAltriDati_MainVO();

					v.setAnti_numDom(rs.getString("NUMERO_DOMANDA"));
					v.setAnti_dataEmiss(rs.getDate("DT_EMISSIONE"));
					v.setAnti_esito(rs.getString("DESC_ESITO_RICHIESTE"));
					v.setAnti_dataScad(rs.getDate("DT_SCADENZA_ANTIMAFIA"));
					v.setAnti_numProto(rs.getString("NUM_PROTOCOLLO_PREFETTURA"));
					v.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
					v.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
					antiMafia.add(v);
				}
				return antiMafia;
			}
		};
		main.setAntiMafia(getJdbcTemplate().query(queryAntima, rseAntima));
		
		
		return main;
	}
	
	
	
	public List<DettaglioImpresaVO> getDettaglioImpresa(String idSoggetto, BigDecimal anno, HttpServletRequest req)throws DaoException {
		
		List<DettaglioImpresaVO> dett = null;
		List<DettaglioImpresaVO> finalDett = new ArrayList<DettaglioImpresaVO>();

		int annoBefore = 1;
		int ID_PERIODO = 1;		
		
		
		if (anno != null) {
			annoBefore = anno.intValue() - 1;
			
			
			String queryIdPeriodo = "SELECT per.ID_PERIODO \r\n"
					+ "FROM PBANDI_T_PERIODO per\r\n"
					+ "WHERE per.DESC_PERIODO_VISUALIZZATA = '" + annoBefore + "'";
			ID_PERIODO = getJdbcTemplate().queryForObject(queryIdPeriodo, Integer.class);
			LOG.debug("[AnagraficaBeneficiarioDAOImpl::getDettaglioImpresa] ID_PERIODO="+ID_PERIODO);

		}
		
		
		for(int i=0; i<3; i++) {
			
			String query = "SELECT ptcdi.ULA, ptcdi.IMP_FATTURATO, ptcdi.TOT_BILANCIO_ANNUO, ptp.DESC_PERIODO \r\n"
				+ "FROM PBANDI_T_CALC_DIM_IMPRESA ptcdi\r\n"
				+ "LEFT JOIN PBANDI_T_PERIODO ptp ON ptcdi.ID_PERIODO = ptp.ID_PERIODO\r\n"
				+ "WHERE ptcdi.ID_SOGGETTO =" + idSoggetto + "\r\n"
						+ " AND ptp.ID_TIPO_PERIODO = 1 \r\n"
						+ " AND ptcdi.ID_PERIODO = " + ID_PERIODO;
		
			dett = getJdbcTemplate().query(query, new DettaglioImpresaRowMapper());	
			
			if (dett.size() > 0) {
				finalDett.add(dett.get(0));
			}
			
			ID_PERIODO--;
			
			LOG.debug("[AnagraficaBeneficiarioDAOImpl::getDettaglioImpresa] ID_PERIODO="+ID_PERIODO);
		}
		
		return finalDett;
	}


	
	
	/*@Override
	public List<AnagAltriDati_DimensioneImpresaVO> getDimensioneImpresa(Long idSoggetto, Long numeroDomanda, HttpServletRequest req)throws DaoException {
		AnagAltriDati_DimensioneImpresaVO data = new AnagAltriDati_DimensioneImpresaVO();
		data.setDataValutazione("01/01/2022");
		data.setEsito("Grande");
		List<AnagAltriDati_DimensioneImpresaVO> elenco = new ArrayList<AnagAltriDati_DimensioneImpresaVO>();
		elenco.add(data);
		
		return elenco;
		
	}*/

	
	/*@Override
	public List<AnagAltriDati_DurcVO> getDurc(Long idSoggetto, HttpServletRequest req)throws DaoException {
			
		List<AnagAltriDati_DurcVO> durc = null;


		String query = "SELECT durc.DT_EMISSIONE_DURC, tiesiri.DESC_ESITO_RICHIESTE, durc.DT_SCADENZA, durc.NUM_PROTOCOLLO_INPS\r\n"
				+ "FROM PBANDI_T_SOGGETTO_DURC durc\r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_ESITO_RICHIESTE tiesiri ON durc.ID_TIPO_ESITO_DURC = tiesiri.ID_TIPO_ESITO_RICHIESTE \r\n"
				+ "WHERE durc.ID_SOGGETTO = " + idSoggetto;

		durc = getJdbcTemplate().query(query, new AnagBenAltriDati_DurcRowMapper());			
		//LOG.info(durc);

		return durc;
	}*/
	
	
	
	///////// ALGORITMI COMUNI /////////
	
	@Override
	public Boolean sistemaDiBlocchi(Long idSoggetto, Long idUtente, List<Integer> listaCampi) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::sistemaDiBlocchi]";
		LOG.info(prf + " BEGIN");
				
		List<SistemaDiBloccoVO> listaBlocchi = new ArrayList<SistemaDiBloccoVO>();
		
		try { // Ricavo la lista delle domande del beneficiario con i blocchi associati
			String queryBlocchi = "SELECT DISTINCT prsd.ID_DOMANDA,\r\n"
					+ "    ptd.NUMERO_DOMANDA,\r\n"
					+ "    prrbl.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "    pdcr.ID_CAMPO_REGOLA,\r\n"
					+ "    pdcr.ID_REGOLA,\r\n"
					+ "    pdcr.ID_CAUSALE_BLOCCO\r\n"
					+ "FROM pbandi_r_soggetto_domanda prsd\r\n"
					+ "    LEFT JOIN pbandi_t_domanda ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    LEFT JOIN pbandi_r_regola_bando_linea prrbl ON prrbl.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "    INNER JOIN pbandi_d_campo_regola pdcr ON pdcr.ID_REGOLA = prrbl.ID_REGOLA\r\n"
					+ "WHERE prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsd.ID_SOGGETTO = " + idSoggetto;

			listaBlocchi = getJdbcTemplate().query(queryBlocchi, new RowMapper<SistemaDiBloccoVO>() {
				
				@Override
				public SistemaDiBloccoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					SistemaDiBloccoVO block = new SistemaDiBloccoVO();
					
					block.setIdDomanda(rs.getInt("ID_DOMANDA"));
					block.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
					block.setProgrBandoLinea(rs.getInt("PROGR_BANDO_LINEA_INTERVENTO"));
					block.setIdCampoRegola(rs.getInt("ID_CAMPO_REGOLA"));
					block.setIdRegola(rs.getInt("ID_REGOLA"));
					block.setIdCausaleBlocco(rs.getInt("ID_CAUSALE_BLOCCO"));
					
					return block;
			}});
			
		} catch (RecordNotFoundException e) {
			LOG.error(prf + " " + e);
			return false;
			
		} catch (Exception e) {
			LOG.error(prf + " " + e);
			return false;
		}
		
		// Se non sono state trovate delle regole associate, termino la procedura
		if(listaBlocchi.isEmpty()) {
				LOG.info(prf + " Nessuna regola trovata per il Soggetto: " + idSoggetto);
				LOG.info(prf + " END");
				return true;
		}
		
		
		try { // Controllo se è previsto un blocco per i campi modificati
			
			for (SistemaDiBloccoVO item : listaBlocchi) {
				if(listaCampi.contains(item.getIdCampoRegola())) {
					// Avvio blocco
					BloccoVO objBlocco = new BloccoVO();
					objBlocco.setIdCausaleBlocco(item.getIdCausaleBlocco());
					objBlocco.setIdUtente(BigDecimal.valueOf(idUtente));
					objBlocco.setIdSoggetto(BigDecimal.valueOf(idSoggetto));
					LOG.info(prf + " Inserisco il blocco per n. domanda: " + item.getNumeroDomanda());
					Boolean esitoInvioBlocco = insertBloccoDomanda(objBlocco, idSoggetto.toString(), item.getNumeroDomanda());
					LOG.info(prf + " Il metodo insertBloccoDomanda() ha restituito " + esitoInvioBlocco);
				}
			}
			
		} catch (Exception e) {
			LOG.error(prf + " " + e);
			return false;
		}
		
		LOG.info(prf + " END");
		return true;			
	}
	
	
																					  // Usato anche 	| // True = blocco
	@Override																		  // per sblocco	| // False = sblocco
	public Boolean sistemaDiNotifiche(Long idSoggetto, Long idProgetto, Long idUtente, Long idCausaleBlocco, boolean isBlocco) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::sistemaDiNotifiche]";
		LOG.info(prf + " BEGIN");
		
		if (isBlocco) {
			LOG.info(prf + " Inizializzo notifica Blocco");
		} else {
			LOG.info(prf + " Inizializzo notifica Sblocco");
		}
		
		Boolean esito = true;
				
		// Notifica a FINISTR (quando modifica della forma giuridica oppure della ragione sociale )
		
		
		
		// Notifica ad attività da svolgere
		
		String descCausaleBlocco = "";
		
		try {
			String qCauBlocco = "SELECT DESC_CAUSALE_BLOCCO\r\n"
					+ "FROM PBANDI_D_CAUSALE_BLOCCO\r\n"
					+ "WHERE ID_CAUSALE_BLOCCO = " + idCausaleBlocco;
			descCausaleBlocco = getJdbcTemplate().queryForObject(qCauBlocco, String.class);
			
			// Preparo l'oggetto per i parametri del corpo della notifica
			List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
			
			// Prendo la data di oggi e la formatto
			LocalDate dateObj = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
	        String date = dateObj.format(formatter);
			
	        // Aggiungo la data ai parametri
	        MetaDataVO metadata1 = new MetaDataVO(); 
	        if(isBlocco) { // Parametri diversi tra blocco e sblocco
	        	metadata1.setNome("DATA_INSERIMENTO_BLOCCO"); //Notification.DATA_INSERIMENTO_BLOCCO
	        } else {
	        	metadata1.setNome("DATA_INSERIMENTO_SBLOCCO"); //Notification.DATA_INSERIMENTO_SBLOCCO
			}
			metadata1.setValore(date);
	        metaDatas.add(metadata1);

			// Aggiungo la causale ai parametri
			MetaDataVO metadata2 = new MetaDataVO(); 
			metadata2.setNome("DES_CAUSALE_BLOCCO"); //Notification.DES_CAUSALE_BLOCCO
			metadata2.setValore(descCausaleBlocco);
			metaDatas.add(metadata2);
			
			String descBreveTemplateNotifica = "";
			if (isBlocco) {
				descBreveTemplateNotifica = "NotificaBloccoProgetto"; //Notification.NOTIFICA_BLOCCO_PROGETTO
			} else {
				descBreveTemplateNotifica = "NotificaSbloccoProgetto"; //Notification.NOTIFICA_SBLOCCO_PROGETTO
			}
			String ruoloProcesso = "Istruttore"; //Notification.Istruttore

			LOG.info(prf + "calling genericDAO.callProcedure().putNotificationMetadata....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);
			
			LOG.info(prf + "calling genericDAO.callProcedure().sendNotificationMessage....");
			genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto), descBreveTemplateNotifica, ruoloProcesso, idUtente);
			// La procedura stampa 0 se tutto è andato a buon fine, altrimenti 1
			
		} catch (Exception e) {
			LOG.error(prf + " " + e);
			return false;
		}
		
		LOG.info(prf + " END");
		return esito;
	}
	
	
	@Override
	public Boolean sistemaDiNotificheFinistr(List<Integer> listaCampi, String numeroDomanda, Long idSoggetto, String ragioneSociale, Long idFormaGiuridica) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::sistemaDiNotificheFinistr]";
		LOG.info(prf + " BEGIN");
		
		String codFiscale = "";
		//String newNumeroDomanda = "";
		String formaGiuridica = "";
		String newRagioneSociale = "";
		
		// Controllo se sono stati modificati i campi 'ragione sociale' (id 3) o 'forma giuridica' (id 9)
		Boolean formaGiuridicaWasChanged = listaCampi.contains(3);
		Boolean ragioneSocialeWasChanged = listaCampi.contains(9);
		Long idChiamata = null;
		try {
			String queryCodFiscale = "SELECT pts.CODICE_FISCALE_SOGGETTO\r\n"
					+ "FROM pbandi_t_soggetto pts WHERE pts.ID_SOGGETTO = " + idSoggetto;
			codFiscale = getJdbcTemplate().queryForObject(queryCodFiscale, String.class);


			if(formaGiuridicaWasChanged) {
				String queryFormaGiuridica = "SELECT COD_FORMA_GIURIDICA \r\n"
						+ "	FROM PBANDI_D_FORMA_GIURIDICA WHERE ID_FORMA_GIURIDICA = " + idFormaGiuridica;
				formaGiuridica = getJdbcTemplate().queryForObject(queryFormaGiuridica, String.class);
			}

			if(ragioneSocialeWasChanged) {
				newRagioneSociale = ragioneSociale;
			}
		} catch (Exception e) {
			LOG.error(prf + " Exception durante la lettura da db: " + e);
			return false;
		}
				
		// Non viene più utilizzato il numero domanda
		//newNumeroDomanda = getNumericPart(numeroDomanda);
		
		//if(newNumeroDomanda != null) {
			
			Client client = ClientBuilder.newBuilder().build();
			
			WebTarget target = client.target(urlAnagrafica)
					//.queryParam("numeroDomanda", newNumeroDomanda)
					.queryParam("codFiscale", codFiscale) 
					.queryParam("formaGiuridica", formaGiuridica) 
					.queryParam("ragioneSociale", newRagioneSociale);
			idChiamata = logMonitoraggioService.trackCallPre(41L,-1L, "R", 0L, 0L,codFiscale,formaGiuridica,newRagioneSociale);
			Response resp = (Response) target.request().get();
			
			LOG.info(prf + " Response da getModificaAnagrafica(): " + resp);
			if(resp.getStatus() == 200) {
				logMonitoraggioService.trackCallPost(idChiamata, "OK" , ""+resp.getStatus(),"", true);
			}else {
				logMonitoraggioService.trackCallPost(idChiamata, "KO" , ""+resp.getStatus(),"", false);
			}
			
		/*} else {
			LOG.error(prf + " Il numeroDomanda non è valido");
			return false;
		}*/
		
		LOG.info(prf + " END");
		return true;
	}
	
	
	
	
	//////////// BLOCCHI /////////////
	
	@Override
	public List<BloccoVO> getElencoBlocchi(BigDecimal idSoggetto, boolean flagErogazione) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getElencoBlocchi]";
		LOG.info(prf + " BEGIN");
		
		List<BloccoVO> listaBlocchi = new ArrayList<BloccoVO>();
		
		try {
			String sql ="SELECT\r\n"
					+ "    DISTINCT \r\n"
					+ "    pdcb.DESC_CAUSALE_BLOCCO,\r\n"
					+ "    ptsdb.ID_CAUSALE_BLOCCO,\r\n"
					+ "    PRSD.ID_SOGGETTO,\r\n"
					+ "    pdcb.DESC_CAUSALE_BLOCCO, pdcb.DESC_MACROAREA, \r\n"
					+ "   TRUNC(PTSDB.DT_INSERIMENTO_BLOCCO) as DT_INSERIMENTO_BLOCCO\r\n"
					+ "   FROM\r\n"
					+ "    PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON PRSD.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON PRSD.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON ptsdb.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO\r\n"
					+ "WHERE\r\n"
					+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND PTSDB.dt_fine_validita IS NULL\r\n"
					+ "    AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL\r\n"
					+ "    AND pdcb.flag_blocco_anagrafico = 'S'\r\n";
			
			if(flagErogazione) {				
				sql+= "    AND pdcb.FLAG_EROGAZIONE = 'S'\r\n";
			}
					sql+= "    and PRSD.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ "ORDER BY\r\n"
					+ "     DT_INSERIMENTO_BLOCCO desc"; 
			
			RowMapper<BloccoVO> lista= new RowMapper<BloccoVO>() {
				@Override
				public BloccoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BloccoVO blocco =  new BloccoVO(); 
					blocco.setDescCausaleBlocco(rs.getString("DESC_CAUSALE_BLOCCO"));
					blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					blocco.setDataInserimentoBlocco(rs.getDate("DT_INSERIMENTO_BLOCCO")); 
					blocco.setIdCausaleBlocco(rs.getLong("ID_CAUSALE_BLOCCO"));
					blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					blocco.setDescMacroArea(rs.getString("DESC_MACROAREA"));
					return blocco;
				}		
			};
			listaBlocchi = getJdbcTemplate().query(sql, lista); 
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}

		return listaBlocchi;
	}


	@Override
	public List<BloccoVO> getStoricoBlocchi(Long idSoggetto, boolean flagErogazione) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getStoricoBlocchi]";
		LOG.info(prf + " BEGIN");
		
		List<BloccoVO> listaBlocchi = new ArrayList<BloccoVO>();
		
		try {
			String sql ="    SELECT\r\n"
					+ "    DISTINCT pdcb.DESC_CAUSALE_BLOCCO,\r\n"
					+ "    PRSD.ID_SOGGETTO,\r\n"
					+ "    TRUNC(PTSDB.DT_INSERIMENTO_BLOCCO) as DT_INSERIMENTO_BLOCCO,\r\n"
					+ "    ptsdb.dt_inserimento_sblocco,\r\n"
					+ "    ptpf.COGNOME, pdcb.DESC_MACROAREA,\r\n"
					+ "    ptpf.NOME\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON PRSD.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON PRSD.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON ptsdb.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptsdb.ID_UTENTE_AGG\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "WHERE\r\n"
					+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND ptpf.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ptpf2.ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "    AND ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND PTSDB.dt_fine_validita IS not NULL\r\n"
					+ "    AND ptsdb.DT_INSERIMENTO_SBLOCCO IS not NULL\r\n"
					+ "    AND pdcb.flag_blocco_anagrafico = 'S'\r\n";
					
					if(flagErogazione) {						
						sql+= "    AND pdcb.FLAG_EROGAZIONE = 'S'\r\n";
					}
					
					sql+= "    and PRSD.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ "ORDER BY\r\n"
					+ "    DT_INSERIMENTO_BLOCCO"; 
			
			RowMapper<BloccoVO> lista= new RowMapper<BloccoVO>() {
				@Override
				public BloccoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BloccoVO blocco =  new BloccoVO(); 
					blocco.setDescCausaleBlocco(rs.getString("DESC_CAUSALE_BLOCCO"));
					blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					blocco.setDataInserimentoBlocco(rs.getDate("DT_INSERIMENTO_BLOCCO")); 
					blocco.setDataInserimentoSblocco(rs.getDate("dt_inserimento_sblocco"));
					blocco.setNome(rs.getString("NOME"));
					blocco.setCognome(rs.getString("COGNOME"));
					blocco.setDescMacroArea(rs.getString("DESC_MACROAREA,"));
					return blocco;
				}		
			};
			listaBlocchi = getJdbcTemplate().query(sql, lista); 
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}

		return listaBlocchi;

	}


	@Override
	public List<AttivitaDTO> getListaCausali(Long idSoggetto) {
		List<AttivitaDTO>listaCaus = new ArrayList<AttivitaDTO>();
		try {
			String sql ="SELECT PDCB.ID_CAUSALE_BLOCCO , PDCB.DESC_CAUSALE_BLOCCO \r\n"
					+ "	FROM PBANDI_D_CAUSALE_BLOCCO pdcb \r\n"
					+ "	WHERE PDCB.FLAG_BLOCCO_ANAGRAFICO  ='S'\r\n"
					+ "	AND PDCB.ID_CAUSALE_BLOCCO  NOT IN  (\r\n"
					+ "			SELECT DISTINCT pdcb2.ID_CAUSALE_BLOCCO \r\n"
					+ "			FROM  PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "			LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb2 ON ptsdb.ID_CAUSALE_BLOCCO  = pdcb2.ID_CAUSALE_BLOCCO\r\n"
					+ "			LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd2 ON prsd2.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "			WHERE prsd2.ID_SOGGETTO=?\r\n"
					+ "			AND prsd2.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "			AND prsd2.ID_TIPO_ANAGRAFICA =1\r\n"
					+ "			AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL\r\n"
					+ " 		AND ptsdb.DT_FINE_VALIDITA  IS  NULL)\r\n"
					+ " 		AND pdcb.DESC_MACROAREA != ('Stato credito')\r\n"
					+ "			AND pdcb.DESC_MACROAREA != ('Stato azienda')"
					+ "			ORDER BY DESC_CAUSALE_BLOCCO "; 
			
			RowMapper<AttivitaDTO> lista= new RowMapper<AttivitaDTO>() {
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO caus =  new AttivitaDTO(); 
					caus.setIdAttivita(rs.getLong("ID_CAUSALE_BLOCCO"));
					caus.setDescAttivita(rs.getString("DESC_CAUSALE_BLOCCO"));
					return caus;
				}		
			};
			listaCaus = getJdbcTemplate().query(sql, lista, new Object[] {idSoggetto}); 
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}

		
		return listaCaus;
	}


	@Override
	public boolean insertBlocco(BloccoVO bloccoVO) {
		
		Long idChiamanta = null;
		String prf = "[AnagraficaBeneficiarioDAOImpl::insertBlocco]";
		LOG.info(prf + " BEGIN");
		
		try {
			String dtInsBlocco = null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			dtInsBlocco = dt.format(Date.valueOf(LocalDate.now()));
			
			
			// recupero l'id del blocco
		//	Long idSoggDomandaBlocco = (long)getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_SOGG_DOM_BLOCCO.nextval FROM dual", long.class); 
			// recupero la lista di id_domanda del soggetto dalla tabella pbandi-r-soggetto-domanda
			String queryProgSoggDom = "SELECT  PROGR_SOGGETTO_DOMANDA \r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "WHERE ID_SOGGETTO =?\r\n"
					+ "AND prsd.ID_TIPO_BENEFICIARIO <>4\r\n"
					+ "AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
					+ "AND prsd.DT_FINE_VALIDITA IS NULL \r\n"
					+ "ORDER BY PROGR_SOGGETTO_DOMANDA ";
			
			List<String> listaProgSoggdomanda= new ArrayList<String>();
			
			RowMapper<String> lista = new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String s  = rs.getString("PROGR_SOGGETTO_DOMANDA"); 
					return s;
				}
			};
			
			boolean isInseredBlock = false; // mi serve per sapere se è già stato inserito un blocco con questa causale 
			
			listaProgSoggdomanda = getJdbcTemplate().query(queryProgSoggDom, lista, new Object [] {bloccoVO.getIdSoggetto()});
			
			if(listaProgSoggdomanda!=null) {
				for(String progsogg: listaProgSoggdomanda) {
					
					
					// prima di inserire devo controllare se esiste già un blocco per di questo tipo per questo soggetto
					String getCountBlocchiInseriti ="SELECT count( ptsdb.ID_SOGGETTO_DOMANDA_BLOCCO) \r\n"
							+ "	FROM PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
							+ "	LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd2 ON prsd2.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA \r\n"
							+ "	WHERE ptsdb.ID_CAUSALE_BLOCCO =?\r\n"
							+ "	AND prsd2.ID_SOGGETTO =?\r\n"
							+ " AND ptsdb.PROGR_SOGGETTO_DOMANDA =?\r\n"
							+ "	AND prsd2.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "	AND prsd2.ID_TIPO_ANAGRAFICA =1\r\n"
							+ "	AND ptsdb.DT_FINE_VALIDITA IS NULL \r\n"
							+ "	AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL \r\n"
							+ "	AND rownum <2"; 
					int countBlocchi=0; 
					try {
						countBlocchi =  getJdbcTemplate().queryForObject(getCountBlocchiInseriti, Integer.class, new Object[] 
									{	bloccoVO.getIdCausaleBlocco() ,
										bloccoVO.getIdSoggetto(),  
										progsogg});
					} catch (Exception e) {
						LOG.error(prf + " Exception " + e.getMessage());
					}
					
					if(countBlocchi<1) {	
						// recupero l'id del blocco
						long idSoggDomandaBlocco = (long)getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_SOGG_DOM_BLOCCO.nextval FROM dual", long.class); 
						// inserisco l'elemento
						String insert = "insert into PBANDI_T_SOGG_DOMANDA_BLOCCO(id_soggetto_domanda_blocco, \r\n"
								+ " progr_soggetto_domanda, \r\n"
								+ " id_causale_blocco, \r\n"
								+ " dt_inserimento_blocco, \r\n"
								+ " id_utente_ins, \r\n"
								+ " dt_inizio_validita)values(?,?,?,sysdate,?, sysdate)\r\n";
						getJdbcTemplate().update(insert, new Object[] {
							idSoggDomandaBlocco, 
							progsogg, 
							bloccoVO.getIdCausaleBlocco(),
							bloccoVO.getIdUtente()
						}); 
						
						
						LOG.debug(prf + "  param [idSoggDomandaBlocco] = " +idSoggDomandaBlocco);
						LOG.debug(prf + "  param [idprogsoggDomanda ] = " +progsogg);
						LOG.debug(prf + "  param [idCuasaleBlocco] = " +bloccoVO.getIdCausaleBlocco());
						LOG.debug(prf + "  param [idUtente ] = " +bloccoVO.getIdUtente());
						
						
						// Notifiche a pbandi che il soggetto è stato bloccato 
						Long idProgetto = getIdProgetto(progsogg); 
						// controllo se il progetto c'è
						if(idProgetto!=null) {						
							sistemaDiNotifiche(bloccoVO.getIdSoggetto().longValue(), idProgetto, bloccoVO.getIdUtente().longValue(), bloccoVO.getIdCausaleBlocco(), true);
						}
					} else {
						isInseredBlock= true; 
					}
				}
						
				// quindi devo recuperare
				// qui chiamo il servizio di finistr per la ricezione di blocco anagrafico
				// parametri :
				// @QueryParam("numeroDomanda") Integer numeroDomanda,
				// @QueryParam("codiceFiscale") String codiceFiscale,
				// @QueryParam("descCausaleBlocco") String descCausaleBlocco,
				// @QueryParam("dtInsBlocco") String dtInsBlocco, 
				// @QueryParam("dtInsSblocco") String dtInsSblocco,
				// @Context SecurityContext securityContext, 
				// @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest
				//  recupero tutte le domande del soggetto da bloccare .
				List <String> numeroDomande = new ArrayList<String>();	
				String queryDomande ="SELECT\r\n"
						+ "    distinct numero_domanda\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    left join pbandi_t_domanda ptd on ptd.id_domanda = prsd.id_domanda\r\n"
						+ "WHERE\r\n"
						+ "    ID_SOGGETTO ="+ bloccoVO.getIdSoggetto() + "\r\n";
				
				String codiceFiscale= getJdbcTemplate().queryForObject("select CODICE_FISCALE_SOGGETTO from PBANDI_T_SOGGETTO pts  where id_soggetto ="+bloccoVO.getIdSoggetto(), String.class);
				
				LOG.debug(prf + "  codiceFiscale="+codiceFiscale);
				
				RowMapper<String> lista2 = new RowMapper<String>() {
					
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						String s  = rs.getString("numero_domanda"); 
						return s;
					}
				}; 
				numeroDomande = getJdbcTemplate().query(queryDomande, lista2);
				String descBlocco = getJdbcTemplate().queryForObject("SELECT DESC_CAUSALE_BLOCCO  FROM PBANDI_D_CAUSALE_BLOCCO pdcb WHERE ID_CAUSALE_BLOCCO ="+bloccoVO.getIdCausaleBlocco(), String.class); 
				LOG.debug(prf + "  descBlocco="+descBlocco);
				
				
				// se non è mai stato inserito un blocco con questa causale allora chiamo finistr 
				if(isInseredBlock==false) {		
					//  CHIAMATA AL SERVIZIO 	
						
						Client client = ClientBuilder.newBuilder().build(); 
						WebTarget target = client.target(url)
						.queryParam("codiceFiscale",codiceFiscale)
						.queryParam("descCausaleBlocco", descBlocco) 
						.queryParam("dtInsBlocco", dtInsBlocco); 
						// target.queryParam("dtInsSblocco", null); 
						
						LOG.debug(prf + "  target ok");
						 idChiamanta = logMonitoraggioService.trackCallPre(39L,bloccoVO.getIdUtente().longValue(), "R", 0L, 0L,codiceFiscale,descBlocco,dtInsBlocco);
						Response resp = (Response) target.request().get();
					//	DocumentResponse dr = resp.getEntity();
						RicezioneBloccoSbloccoResponse dr = resp.readEntity(RicezioneBloccoSbloccoResponse.class);
						if(resp.getStatus() == 200) {
							logMonitoraggioService.trackCallPost(idChiamanta, "OK" , ""+resp.getStatus(),"", dr);
						}else {
							logMonitoraggioService.trackCallPost(idChiamanta, "KO" , ""+resp.getStatus(),"", dr);
						}
						LOG.debug(prf +"la risposta al servizio e':: "+dr);
				}
					
			} else {
				return false;
			}
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
			logMonitoraggioService.trackCallPost(idChiamanta, "KO" ,e.getMessage(), "500", null);
			return false;
		}
		return true;
	}


	private Long getIdProgetto(String progsogg) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getIdProgetto per le notifiche]";
		LOG.info(prf + " BEGIN");	
		Long idProgetto= null;
		try {
			String queryGetIdProgetto = "SELECT prsp.ID_PROGETTO \r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "WHERE prsd.PROGR_SOGGETTO_DOMANDA ="+ progsogg;
			
			idProgetto = getJdbcTemplate().queryForObject(queryGetIdProgetto, Long.class); 
			
			
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::getIdProgetto] Exception " + e.getMessage());
			return null; 
		}
		
		LOG.info(prf + " END progetto trovato!!");
		
		return idProgetto;
	}


	@Override // questo nuovo piu giusto.. 
	public boolean insertBloccoConDomanda(BloccoVO blocco, String numeroDomanda){
		String queryProgSoggDom = "SELECT prsd.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "    FROM PBANDI_R_SOGGETTO_DOMANDA  prsd\r\n"
				+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
				+ "    WHERE prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "    AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
				+ "    AND ptd.NUMERO_DOMANDA ='" + numeroDomanda + "'\r\n"
				+ "    AND prsd.ID_SOGGETTO = " + blocco.getIdSoggetto();
		
		List<BigDecimal> listaProgSoggdomanda= new ArrayList<BigDecimal>();
		
		String dtInsBlocco = null; 
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
		dtInsBlocco = dt.format(Date.valueOf(LocalDate.now()));
		
		listaProgSoggdomanda = getJdbcTemplate().queryForList(queryProgSoggDom, BigDecimal.class);
		boolean isBlockInsered = false ; 
		if(listaProgSoggdomanda!=null) {
			for(BigDecimal progsogg: listaProgSoggdomanda) {
				
				// prima di inserire devo controllare se esiste già un blocco per di questo tipo per questo soggetto
				String getCountBlocchiInseriti ="	SELECT count (ptsdb.ID_SOGGETTO_DOMANDA_BLOCCO )\r\n"
						+ "	FROM PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
						+ "	LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd2 ON prsd2.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA \r\n"
						+ "	LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd2.ID_DOMANDA \r\n"
						+ "	WHERE ptsdb.ID_CAUSALE_BLOCCO =?\r\n"
						+ "	AND ptd.NUMERO_DOMANDA =?\r\n"
						+ "	AND prsd2.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "	AND prsd2.ID_TIPO_ANAGRAFICA =1\r\n"
						+ "	AND ptsdb.PROGR_SOGGETTO_DOMANDA =?\r\n"
						+ "	AND ptsdb.DT_FINE_VALIDITA IS NULL \r\n"
						+ "	AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL "; 
				
				int countBlocchi=0; 
				try {
					countBlocchi =  getJdbcTemplate().queryForObject(getCountBlocchiInseriti, Integer.class, new Object[] 
								{	blocco.getIdCausaleBlocco() ,
									numeroDomanda,  
									progsogg});
				} catch (Exception e) {
					LOG.error("[AnagraficaBeneficiarioDAOImpl::insertBloccoConDomanda] Exception " + e.getMessage());
				}
				
				if(countBlocchi<1) {
					// recupero l'id del blocco
					long idSoggDomandaBlocco = (long)getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_SOGG_DOM_BLOCCO.nextval FROM dual", long.class); 
					// inserisco l'elemento
					String insert = "insert into PBANDI_T_SOGG_DOMANDA_BLOCCO(id_soggetto_domanda_blocco, \r\n"
							+ " progr_soggetto_domanda, \r\n"
							+ " id_causale_blocco, \r\n"
							+ " dt_inserimento_blocco, \r\n"
							+ " id_utente_ins, \r\n"
							+ " dt_inizio_validita)values(?,?,?,sysdate,?, sysdate)\r\n";
					getJdbcTemplate().update(insert, new Object[] {
						idSoggDomandaBlocco, 
						progsogg, 
						blocco.getIdCausaleBlocco(),
						blocco.getIdUtente()
					}); 
					
					// Notifiche a pbandi che il soggetto è stato bloccato 
					Long idProgetto = getIdProgetto(progsogg.toString()); 
					// controllo se il progetto c'è
					if(idProgetto!=null) {						
						sistemaDiNotifiche(blocco.getIdSoggetto().longValue(), idProgetto, blocco.getIdUtente().longValue(), blocco.getIdCausaleBlocco(), true);
						// controllo se la causale del blocco abbia flag_revoca a S inserisco la proposa di revoca dentro.
						//recupero da db il flag:
						String flagRevoca=null; 
						flagRevoca = getJdbcTemplate().queryForObject("SELECT FLAG_REVOCA \r\n"
								+ "FROM pbandi_d_causale_blocco \r\n"
								+ "WHERE ID_CAUSALE_BLOCCO ="+ blocco.getIdCausaleBlocco(), String.class);
						
						if(flagRevoca!=null && flagRevoca.trim().equals("S")) {
							Long idPropostaRevoca= null;	
							try {
								idPropostaRevoca = propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(), 
									idProgetto, (long)blocco.getIdCausaleBlocco(), null, Date.valueOf(LocalDate.now()), blocco.getIdUtente().longValue());
							} catch (Exception e) {
								LOG.error("[AnagraficaBeneficiarioDAOImpl::insertBloccoConDomanda] errore inserimento proposta di revoca, Exception " + e.getMessage());
							}
							if(idPropostaRevoca!=null)
							LOG.info("proposta revoca inserita bene!!");
							else LOG.info("errore inserimento proposta di revoca!!");
						}
					}
				} else {
					isBlockInsered = true;
				}
				
			}
			
			String codiceFiscale= getJdbcTemplate().queryForObject("select CODICE_FISCALE_SOGGETTO from PBANDI_T_SOGGETTO pts  where id_soggetto ="+blocco.getIdSoggetto(), String.class);
			String descBlocco = getJdbcTemplate().queryForObject("SELECT DESC_CAUSALE_BLOCCO  FROM PBANDI_D_CAUSALE_BLOCCO pdcb WHERE ID_CAUSALE_BLOCCO ="+blocco.getIdCausaleBlocco(), String.class); 
		
			
			if(numeroDomanda!=null && !isBlockInsered) {		
				String numeroDomandaFinistr= null; 
				numeroDomanda = getNumericPart(numeroDomanda);
				Client client = ClientBuilder.newBuilder().build(); 
				WebTarget target = client.target(url)
						.queryParam("numeroDomanda", numeroDomandaFinistr)
						.queryParam("codiceFiscale",codiceFiscale) 
						.queryParam("descCausaleBlocco", descBlocco) 
						.queryParam("dtInsBlocco", dtInsBlocco); 
				// target.queryParam("dtInsSblocco", null); 
				
				Response resp = (Response) target.request().get();
				
				RicezioneBloccoSbloccoResponse dr = resp.readEntity(RicezioneBloccoSbloccoResponse.class);
				LOG.debug("insertBloccoConDomanda la risposta al servizio e':: "+dr);
			} else {
				LOG.info("non faccio la chiamata siccome il numero domanda non è valido");
			}
			
		}

		return true;
	}

	@Override
	public boolean modificablocco(BloccoVO bloccoVO) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::modificablocco]";
		LOG.info(prf + " BEGIN");
		boolean result; 
		
		
		try {
			String dtInsBlocco = null, dtInsSblocco = null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			if(bloccoVO.getDataInserimentoBlocco() != null) {
				dtInsBlocco = dt.format(bloccoVO.getDataInserimentoBlocco());
			} 
			dtInsSblocco = dt1.format(Date.valueOf(LocalDate.now()));
			
			// recupero tutti i progrSoggettoDomanda di questo soggetto presente sulla tabella dei blocchi con questa causale blocco
			String getProgrSogdomanda= "  SELECT ptsdb.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    FROM pbandi_T_sogg_domanda_blocco ptsdb\r\n"
					+ "    LEFT JOIN pbandi_r_soggetto_domanda prsd ON ptsdb.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    WHERE ptsdb.ID_CAUSALE_BLOCCO =? \r\n"
					+ "    AND prsd.ID_SOGGETTO = ?\r\n"
					+ "    AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL";
			
			List<String> listaProgSoggdomanda= new ArrayList<String>();
			
			RowMapper<String> lista = new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String s  = rs.getString("PROGR_SOGGETTO_DOMANDA"); 
					return s;
				}
			};
			
			listaProgSoggdomanda = getJdbcTemplate().query(getProgrSogdomanda, lista, new Object[] {
					bloccoVO.getIdCausaleBlocco(), bloccoVO.getIdSoggetto()
			});
			
			
			String update ="update PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb set \r\n"
					+ " ptsdb.dt_inserimento_sblocco = sysdate,\r\n"
					+ " ptsdb.dt_fine_validita= sysdate,\r\n"
					+ " ptsdb.id_utente_agg ="+ bloccoVO.getIdUtente()+"\r\n"
					+ " WHERE PTSDB.ID_SOGGETTO_DOMANDA_BLOCCO IN (SELECT PTSDB2.ID_SOGGETTO_DOMANDA_BLOCCO  \r\n"
					+ " 					FROM PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb2 \r\n"
					+ " 				 	LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ " 			 		ON PRSD .PROGR_SOGGETTO_DOMANDA =PTSDB2.progr_soggetto_domanda\r\n"
					+ " 					where prsd.ID_SOGGETTO="+ bloccoVO.getIdSoggetto()+" \r\n"
					+ " 					AND PTSDB2 .ID_CAUSALE_BLOCCO ="+bloccoVO.getIdCausaleBlocco()+")";
			
			getJdbcTemplate().update(update); 
			result  = true; 	
			
			// sblocco anagrafico
			List <String> numeroDomande = new ArrayList<String>();
			String queryDomande ="SELECT\r\n"
					+ "    distinct numero_domanda\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    left join pbandi_t_domanda ptd on ptd.id_domanda = prsd.id_domanda\r\n"
					+ "WHERE\r\n"
					+ "    ID_SOGGETTO ="+ bloccoVO.getIdSoggetto() + "\r\n";
			
			String codiceFiscale= getJdbcTemplate().queryForObject("select CODICE_FISCALE_SOGGETTO from PBANDI_T_SOGGETTO pts  where id_soggetto ="+bloccoVO.getIdSoggetto(), String.class);
			RowMapper<String> lista2 = new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String s  = rs.getString("numero_domanda"); 
					return s;
				}
			}; 
			numeroDomande = getJdbcTemplate().query(queryDomande, lista2);
			String descBlocco = getJdbcTemplate().queryForObject("SELECT DESC_CAUSALE_BLOCCO  FROM PBANDI_D_CAUSALE_BLOCCO pdcb WHERE ID_CAUSALE_BLOCCO ="+bloccoVO.getIdCausaleBlocco(), String.class);
			
			
				Client client = ClientBuilder.newBuilder().build(); 
				WebTarget target = client.target(url)
				.queryParam("codiceFiscale",codiceFiscale)
				.queryParam("descCausaleBlocco", descBlocco)
				.queryParam("dtInsBlocco",dtInsBlocco) 
				.queryParam("dtInsSblocco", dtInsSblocco); 
				
				Response resp = (Response) target.request().get();
				RicezioneBloccoSbloccoResponse dr = resp.readEntity(RicezioneBloccoSbloccoResponse.class);
				LOG.info(prf + "la risposta al servizio e': "+dr);
				
				
				for (String progsogg : listaProgSoggdomanda) {
					
					// Notifiche a pbandi che il soggetto è stato bloccato 
					Long idProgetto = getIdProgetto(progsogg); 
					// controllo se il progetto c'è
					if(idProgetto!=null) {						
						sistemaDiNotifiche(bloccoVO.getIdSoggetto().longValue(), idProgetto, bloccoVO.getIdUtente().longValue(), bloccoVO.getIdCausaleBlocco(), false);
					}
				}
				
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::modificablocco] Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
			result = false; 
		}
		return result;
	}

/////////// SOGGETTI CORRELATI INDIPENDENTI DA DOMANDA////////////// 
	@Override
	public List<SoggettiCorrelatiVO> getElencoSoggCorrIndipDaDomanda(String idDomanda, String idSogetto) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::elenco soggetti correlati dipendenti da domanda]";
		LOG.info(prf + " BEGIN");
		
		
		List<SoggettiCorrelatiVO> listaSogetti = new ArrayList<SoggettiCorrelatiVO>(); 
		List<SoggettiCorrelatiVO> listaSogettiEG = new ArrayList<SoggettiCorrelatiVO>(); 
		
		try {			
			String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where numero_domanda='"+idDomanda+"'\r\n"
					+ "    --AND prsd.ID_SOGGETTO = "+idSogetto+"\r\n"
					+ "  	AND ROWNUM =1 \r\n";
				
				BigDecimal veroIdDomanda = getJdbcTemplate().queryForObject(getVeroIddomanda, BigDecimal.class); 
				BigDecimal idProgetto = checkProgetto(idDomanda); 
				String sqlPF=null; 
				String sqlEG=null; 
				
				if(idProgetto!=null && idProgetto.intValue()>0) {
					
					 sqlPF="SELECT\r\n"
					 		+ "    DISTINCT prsp.ID_SOGGETTO AS nag,\r\n"
					 		+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
					 		+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n"
					 		+ "    ptpf.COGNOME AS DESC1,\r\n"
					 		+ "    ptpf.NOME AS desc2,\r\n"
					 		+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
					 		+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
					 		+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
					 		+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
					 		+ "    pts.ndg,\r\n"
					 		+ "    prsp.ID_PERSONA_FISICA\r\n"
					 		+ "FROM\r\n"
					 		+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					 		+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO and prsp.DT_FINE_VALIDITA is null \r\n"
					 		+ "     JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
					 		+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
					 		+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
					 		+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
					 		+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA \r\n"
					 		+ "WHERE\r\n"
					 		+ "    prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
					 		+ "    AND pdtsc.FLAG_INDIPENDENTE = 'S'\r\n"
					 		+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
					 		+ "    AND prsp.ID_ENTE_GIURIDICO IS NULL\r\n"
					 		+ "    AND prsp.ID_PROGETTO = "+idProgetto+"\r\n";
					 
					 
					 sqlEG="WITH dati_soggetto AS (\r\n"
								+ "    SELECT\r\n"
								+ "        prsd2.ID_SOGGETTO,\r\n"
								+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO AS desc1,\r\n"
								+ "        '' AS desc2,\r\n"
								+ "        prsd2.ID_ENTE_GIURIDICO,\r\n"
								+ "        prsd2.PROGR_SOGGETTO_DOMANDA\r\n"
								+ "    FROM\r\n"
								+ "        PBANDI_R_SOGGETTO_PROGETTO prsd2,\r\n"
								+ "        PBANDI_T_ENTE_GIURIDICO pteg\r\n"
								+ "    WHERE\r\n"
								+ "        PRSD2.ID_TIPO_ANAGRAFICA <> 1\r\n"
								+ "        AND pteg.ID_ENTE_GIURIDICO = PRSD2.ID_ENTE_GIURIDICO\r\n"
								+ ")\r\n"
								+ "SELECT\r\n"
								+ "    DISTINCT prsp.ID_SOGGETTO AS nag,\r\n"
								+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
								+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n"
								+ "    ds.desc1,\r\n"
								+ "    ds.desc2,\r\n"
								+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
								+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
								+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
								+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
								+ "    pts.ndg, prsp.ID_ENTE_GIURIDICO \r\n"
								+ "FROM\r\n"
								+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
								+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO  = prspsc.PROGR_SOGGETTO_PROGETTO and prsp.DT_FINE_VALIDITA is null\r\n"
								+ "     JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
								+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
								+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
								+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
								+ "    LEFT JOIN dati_soggetto ds ON ds.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
								+ "WHERE\r\n"
								+ "    prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
								+ "    AND pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
								+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
								+ "    AND prsp.ID_PERSONA_FISICA IS NULL\r\n"
								+ "    AND prsp.ID_PROGETTO ="+idProgetto+"\r\n";
					 
					
					
				} else {
					 sqlPF ="WITH dati_soggetto AS (\r\n"
							+ "    SELECT\r\n"
							+ "        prsd.ID_SOGGETTO,\r\n"
							+ "        ptpf.COGNOME AS DESC1,\r\n"
							+ "        ptpf.NOME AS desc2,\r\n"
							+ "        prsd.ID_PERSONA_FISICA,\r\n"
							+ "        prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    FROM\r\n"
							+ "        PBANDI_R_SOGGETTO_DOMANDA prsd,\r\n"
							+ "        PBANDI_T_PERSONA_FISICA ptpf\r\n"
							+ "    WHERE\r\n"
							+ "        PRSD.ID_TIPO_ANAGRAFICA <> 1\r\n"
							+ "        and PRSD.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
							+ ")\r\n"
							+ "SELECT\r\n"
							+ "    DISTINCT prsd.ID_SOGGETTO AS nag,\r\n"
							+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
							+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n"
							+ "    ds.desc1,\r\n"
							+ "    ds.desc2,\r\n"
							+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
							+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
							+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
							+ "    prsd.PROGR_SOGGETTO_DOMANDA, pts.ndg\r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA and prsd.DT_FINE_VALIDITA is null\r\n"
							+ "     JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
							+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
							+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
							+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
							+ "    LEFT JOIN dati_soggetto ds ON ds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "WHERE\r\n"
							+ "    prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
							+ "    AND pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
							+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
							+ "    AND prsd.ID_ENTE_GIURIDICO IS NULL\r\n"
							+ "    AND prsd.ID_DOMANDA = "+veroIdDomanda; 

					 
					
					 sqlEG="WITH dati_soggetto AS (\r\n"
							+ "    SELECT\r\n"
							+ "        prsd2.ID_SOGGETTO,\r\n"
							+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO AS desc1,\r\n"
							+ "        '' AS desc2,\r\n"
							+ "        prsd2.ID_ENTE_GIURIDICO,\r\n"
							+ "        prsd2.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    FROM\r\n"
							+ "        PBANDI_R_SOGGETTO_DOMANDA prsd2,\r\n"
							+ "        PBANDI_T_ENTE_GIURIDICO pteg\r\n"
							+ "    WHERE\r\n"
							+ "        PRSD2.ID_TIPO_ANAGRAFICA <> 1\r\n"
							+ "        AND pteg.ID_ENTE_GIURIDICO = PRSD2.ID_ENTE_GIURIDICO\r\n"
							+ ")\r\n"
							+ "SELECT\r\n"
							+ "    DISTINCT prsd.ID_SOGGETTO AS nag,\r\n"
							+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
							+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n"
							+ "    ds.desc1,\r\n"
							+ "    ds.desc2,\r\n"
							+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
							+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
							+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
							+ "    prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
							+ "    prsc.DT_FINE_VALIDITA, pts.ndg\r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA and prsd.DT_FINE_VALIDITA is null\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
							+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
							+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
							+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
							+ "    LEFT JOIN dati_soggetto ds ON ds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "WHERE\r\n"
							+ "    prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
							+ "    AND pdtsc.FLAG_INDIPENDENTE = 'S'\r\n"
							+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
							+ "    AND prsd.ID_PERSONA_FISICA IS NULL\r\n"
							+ "    AND prsd.ID_DOMANDA = " + veroIdDomanda; 

				}
				
				
				
				RowMapper<SoggettiCorrelatiVO> lista= new RowMapper<SoggettiCorrelatiVO>() {
					@Override
					public SoggettiCorrelatiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						SoggettiCorrelatiVO soggetto =  new SoggettiCorrelatiVO(); 
						String desc1 = rs.getString("desc1"); 
						String desc2 = rs.getString("desc2"); 
						if(desc2!=null) {							
							soggetto.setCognome(desc1+" "+desc2);
						} else {
							soggetto.setCognome(desc1);
						}
						soggetto.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
						soggetto.setNag(rs.getString("nag"));
						soggetto.setTipologia(rs.getString("DESC_TIPO_SOGGETTO"));
						soggetto.setIdSoggettoCorellato(rs.getLong("PROGR_SOGGETTI_CORRELATI"));
						soggetto.setRuolo(rs.getString("ruolo"));
						soggetto.setProgSoggDomanda(rs.getString("PROGR_SOGGETTO_DOMANDA"));
						soggetto.setIdDomanda(veroIdDomanda.toString());
						soggetto.setQuotaPartecipazione(rs.getLong("QUOTA_PARTECIPAZIONE"));
						soggetto.setNdg(rs.getString("ndg"));
						return soggetto;
					}		
				};
				
				listaSogetti = getJdbcTemplate().query(sqlPF, lista); 
				listaSogettiEG = getJdbcTemplate().query(sqlEG, lista); 
				
				listaSogetti.addAll(listaSogettiEG);
		
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}

		return listaSogetti;
	}


	@Override
	public AnagraficaBeneficiarioVO getSoggCorrIndDaDomEG(String idDomanda, String idSoggetto, BigDecimal idSoggCorr) {
		String prf = "[AnagraficaBeneficiarioDAOImpl:: soggetto correlato indipendenti da domanda persona giuridica]";
		LOG.info(prf + " BEGIN");
		
		AnagraficaBeneficiarioVO soggetto = new AnagraficaBeneficiarioVO(); 
		try {
			BigDecimal idProgetto = checkProgetto(idDomanda);
			String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where numero_domanda='"+idDomanda+"'\r\n"
					+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ "    AND ROWNUM =1 \r\n"; 	
			BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda, BigDecimal.class); 
			
			String sql=null; 
			String query=null; 
			
			
			
			if(idProgetto!=null && idProgetto.intValue()>0) {
				String sqlGetAnag="SELECT\r\n"
						+ "    prsp.ID_SOGGETTO,\r\n"
						+ "    prsp.ID_ENTE_GIURIDICO,\r\n"
						+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "    pdta.DESC_TIPO_ANAGRAFICA,\r\n"
						+ "    pdta.ID_TIPO_ANAGRAFICA,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pdss.DESC_SEZIONE_SPECIALE,\r\n"
						+ "    pdp2.ID_PROVINCIA AS id_prov_isc,\r\n"
						+ "    pdp2.DESC_PROVINCIA AS desc_prov_isc,\r\n"
						+ "    pdp.ID_PROVINCIA,\r\n"
						+ "    pdp.DESC_PROVINCIA,\r\n"
						+ "    pdc.ID_COMUNE,\r\n"
						+ "    pdc.DESC_COMUNE,\r\n"
						+ "    pdr.ID_REGIONE,\r\n"
						+ "    pdr.DESC_REGIONE,\r\n"
						+ "    pdn.ID_NAZIONE,\r\n"
						+ "    pdn.DESC_NAZIONE,\r\n"
						+ "    ptis.NUM_ISCRIZIONE,\r\n"
						+ "    ptis.DT_ISCRIZIONE,\r\n"
						+ "    pdsa.DESC_STATO_ATTIVITA,\r\n"
						+ "    pdaa.ID_ATTIVITA_ATECO,\r\n"
						+ "    pdaa.DESC_ATECO,\r\n"
						+ "    pdaa.COD_ATECO,\r\n"
						+ "    pteg.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "    pteg.DT_INIZIO_VALIDITA,\r\n"
						+ "    --pteg.ID_DIMENSIONE_IMPRESA ,\r\n"
						+ "    pteg.FLAG_RATING_LEGALITA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "    pti.DESC_INDIRIZZO,\r\n"
						+ "    ptse.PARTITA_IVA,\r\n"
						+ "    pti.CAP,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    ptegs.ID_ENTE_SEZIONE,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    pteg.COD_UNI_IPA,\r\n"
						+ "    ptis.ID_TIPO_ISCRIZIONE,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.NDG, pteg.DT_INIZIO_ATTIVITA_ESITO ,\r\n"
						+ "    ptis.ID_ISCRIZIONE, ptse.id_sede, \r\n"
						+ "    ptr.PEC, pteg.DT_COSTITUZIONE, pdsa.ID_STATO_ATTIVITA, ptr.ID_RECAPITI,\r\n"
						+ "    ptr.EMAIL, pti.ID_INDIRIZZO, ptse.ID_SEDE,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "	   pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "	   pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA, prsc.QUOTA_PARTECIPAZIONE, prsps.PROGR_SOGGETTO_PROGETTO_SEDE\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta ON pdta.ID_TIPO_ANAGRAFICA = prsp.ID_TIPO_ANAGRAFICA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsps.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsps.ID_INDIRIZZO\r\n"
						+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = pti.ID_COMUNE\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pdaa.ID_ATTIVITA_ATECO = ptse.ID_ATTIVITA_ATECO\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_ATTIVITA pdsa ON pdsa.ID_STATO_ATTIVITA = pteg.ID_STATO_ATTIVITA\r\n"
						+ "    LEFT JOIN PBANDI_T_ISCRIZIONE ptis ON ptis.ID_ISCRIZIONE = prsp.ID_ISCRIZIONE_PERSONA_GIURID\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp2 ON pdp2.ID_PROVINCIA = ptis.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON pdss.ID_SEZIONE_SPECIALE = ptegs.ID_SEZIONE_SPECIALE\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsps.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "	   LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "	   LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n" 
						+ "	   LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "WHERE\r\n"
						+ "    --prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "     prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    --AND prsps.ID_TIPO_SEDE = 1\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto+"\r\n"
						+ "    AND prsp.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "	  and prsc.PROGR_SOGGETTI_CORRELATI= "+idSoggCorr+"\r\n"
						+ "	 AND pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
						+ "	 AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "	 AND prsp.ID_PERSONA_FISICA IS NULL\r\n";
				
				soggetto = getSoggIndDomaEG(sqlGetAnag);
					
			} else {
				
				String anagSql ="SELECT\r\n"
						+ "    prsd.ID_SOGGETTO,\r\n"
						+ "    prsd.ID_ENTE_GIURIDICO,\r\n"
						+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "    pdta.DESC_TIPO_ANAGRAFICA,\r\n"
						+ "    pdta.ID_TIPO_ANAGRAFICA,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pdss.DESC_SEZIONE_SPECIALE,\r\n"
						+ "    pdp2.ID_PROVINCIA AS id_prov_isc,\r\n"
						+ "    pdp2.DESC_PROVINCIA AS desc_prov_isc,\r\n"
						+ "    pdp.ID_PROVINCIA,\r\n"
						+ "    pdp.DESC_PROVINCIA,\r\n"
						+ "    pdc.ID_COMUNE,\r\n"
						+ "    pdc.DESC_COMUNE,\r\n"
						+ "    pdr.ID_REGIONE,\r\n"
						+ "    pdr.DESC_REGIONE,\r\n"
						+ "    pdn.ID_NAZIONE,\r\n"
						+ "    pdn.DESC_NAZIONE,\r\n"
						+ "    ptis.NUM_ISCRIZIONE,\r\n"
						+ "    ptis.DT_ISCRIZIONE,\r\n"
						+ "    pdsa.DESC_STATO_ATTIVITA,\r\n"
						+ "    pdaa.ID_ATTIVITA_ATECO,\r\n"
						+ "    pdaa.DESC_ATECO,\r\n"
						+ "    pdaa.COD_ATECO,\r\n"
						+ "    pteg.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "    pteg.DT_INIZIO_VALIDITA,\r\n"
						+ "    --pteg.ID_DIMENSIONE_IMPRESA ,\r\n"
						+ "    pteg.FLAG_RATING_LEGALITA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "    pti.DESC_INDIRIZZO,\r\n"
						+ "    ptse.PARTITA_IVA, ptse.id_sede, \r\n"
						+ "    pti.CAP,\r\n"
						+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
						+ "    ptegs.ID_ENTE_SEZIONE,\r\n"
						+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "    pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    pteg.COD_UNI_IPA,\r\n"
						+ "    ptis.ID_TIPO_ISCRIZIONE,\r\n"
						+ "    pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.NDG,\r\n"
						+ "    ptis.ID_ISCRIZIONE,\r\n"
						+ "    ptr.PEC,\r\n"
						+ "    ptr.EMAIL, ptr.ID_RECAPITI,\r\n"
						+ "    pti.ID_INDIRIZZO,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    pdsa.ID_STATO_ATTIVITA,\r\n"
						+ "    prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    ptse.ID_SEDE,\r\n"
						+ "    pteg.DT_COSTITUZIONE,\r\n"
						+ "	   pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "	   pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n"
						+ "    prsd.ID_ENTE_GIURIDICO, prsc.QUOTA_PARTECIPAZIONE , prsds.PROGR_SOGGETTO_DOMANDA_SEDE\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta ON pdta.ID_TIPO_ANAGRAFICA = prsd.ID_TIPO_ANAGRAFICA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsds.ID_INDIRIZZO\r\n"
						+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = pti.ID_COMUNE\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pdaa.ID_ATTIVITA_ATECO = ptse.ID_ATTIVITA_ATECO\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_ATTIVITA pdsa ON pdsa.ID_STATO_ATTIVITA = pteg.ID_STATO_ATTIVITA\r\n"
						+ "    LEFT JOIN PBANDI_T_ISCRIZIONE ptis ON ptis.ID_ISCRIZIONE = prsd.ID_ISCRIZIONE_PERSONA_GIURID\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp2 ON pdp2.ID_PROVINCIA = ptis.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON pdss.ID_SEZIONE_SPECIALE = ptegs.ID_SEZIONE_SPECIALE\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsds.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "	   LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "	   LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "	   LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "WHERE\r\n"
						+ "     prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    --AND prsds.ID_TIPO_SEDE = 1\r\n"
						+ "	 AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "	 AND prsd.ID_PERSONA_FISICA IS NULL\r\n"
						+ "	 AND prsd.ID_DOMANDA = "+veroIdDomanda+"\r\n"
						+ "	 and prsc.PROGR_SOGGETTI_CORRELATI="+idSoggCorr+"\r\n"
						+ "	 and prsd.ID_SOGGETTO="+ idSoggetto;;
				
				soggetto = getSoggIndDomaEG(anagSql);
			}
			
			
//			
//			if(idProgetto!=null) {
//				query="WITH dati_anagrafici AS (\r\n"
//						+ "	SELECT\r\n"
//						+ "		prsc.ID_SOGGETTO,\r\n"
//						+ "		pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "		prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "		pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
//						+ "		pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
//						+ "		PTEG.COD_UNI_IPA,\r\n"
//						+ "		pts.CODICE_FISCALE_SOGGETTO,\r\n"
//						+ "		pts.ndg,\r\n"
//						+ "		pdfg.DESC_FORMA_GIURIDICA,\r\n"
//						+ "		pteg.ID_FORMA_GIURIDICA,\r\n"
//						+ "		PTEG.DT_COSTITUZIONE,\r\n"
//						+ "		pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n"
//						+ "		pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
//						+ "		PTEG.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
//						+ "		pteg.FLAG_RATING_LEGALITA,\r\n"
//						+ "		pteg.ID_ENTE_GIURIDICO,\r\n"
//						+ "		prsc.PROGR_SOGGETTI_CORRELATI\r\n"
//						+ "	FROM\r\n"
//						+ "		PBANDI_T_ENTE_GIURIDICO pteg\r\n"
//						+ "		LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON PTEG.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA\r\n"
//						+ "		LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PTEG.ID_SOGGETTO\r\n"
//						+ "		LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.ID_SOGGETTO = pteg.ID_SOGGETTO\r\n"
//						+ "		LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
//						+ "),\r\n"
//						+ "indirizzo AS (\r\n"
//						+ "	SELECT\r\n"
//						+ "		prsps.PROGR_SOGGETTO_PROGETTO ,\r\n"
//						+ "		pti.DESC_INDIRIZZO,\r\n"
//						+ "		pti.id_indirizzo,\r\n"
//						+ "		pti.id_comune,\r\n"
//						+ "		pti.id_provincia,\r\n"
//						+ "		pti.id_regione,\r\n"
//						+ "		pti.id_nazione,\r\n"
//						+ "		pti.CAP,\r\n"
//						+ "		pts.id_sede,\r\n"
//						+ "		pdc.DESC_COMUNE,\r\n"
//						+ "		pdp.DESC_PROVINCIA,\r\n"
//						+ "		pdr.DESC_REGIONE,\r\n"
//						+ "		pdn.DESC_NAZIONE,\r\n"
//						+ "		pts.ID_ATTIVITA_ATECO,\r\n"
//						+ "		pdaa.COD_ATECO,\r\n"
//						+ "		pdaa.DESC_ATECO,\r\n"
//						+ "		pts.PARTITA_IVA\r\n"
//						+ "	FROM\r\n"
//						+ "		PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
//						+ "		LEFT JOIN  PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO  = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
//						+ "		LEFT JOIN PBANDI_T_SEDE pts ON pts.ID_SEDE = PRSpS.ID_SEDE\r\n"
//						+ "		LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = PRSpS.ID_INDIRIZZO\r\n"
//						+ "		LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
//						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
//						+ "		LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pti.ID_REGIONE\r\n"
//						+ "		LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
//						+ "		LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pts.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO\r\n"
//						+ "	WHERE\r\n"
//						+ "		PRSpS.ID_TIPO_SEDE = 1\r\n"
//						+ "		AND prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
//						+ "),\r\n"
//						+ "dati_iscrizione AS (\r\n"
//						+ "	SELECT\r\n"
//						+ "		pti.NUM_ISCRIZIONE,\r\n"
//						+ "		pti.DT_ISCRIZIONE,\r\n"
//						+ "		pdp.DESC_PROVINCIA AS provincia_iscriz,\r\n"
//						+ "		prsp.ID_SOGGETTO,pti.ID_TIPO_ISCRIZIONE,\r\n"
//						+ "		pdp.id_provincia as id_prov_iscriz,\r\n"
//						+ "		prsp.PROGR_SOGGETTO_PROGETTO ,\r\n"
//						+ "		pti.ID_ISCRIZIONE\r\n"
//						+ "	FROM\r\n"
//						+ "		PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
//						+ "		LEFT JOIN PBANDI_T_ISCRIZIONE pti ON prsp.ID_ISCRIZIONE_PERSONA_GIURID = pti.ID_ISCRIZIONE\r\n"
//						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
//						+ ")\r\n"
//						+ "SELECT\r\n"
//						+ "	DISTINCT prsd.ID_SOGGETTO,\r\n"
//						+ "	pdts.DESC_TIPO_SOGGETTO,\r\n"
//						+ "	da.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
//						+ "	da.FLAG_PUBBLICO_PRIVATO,\r\n"
//						+ "	da.ID_FORMA_GIURIDICA,\r\n"
//						+ "	da.COD_UNI_IPA,\r\n"
//						+ "	da.CODICE_FISCALE_SOGGETTO,\r\n"
//						+ "	da.ndg,\r\n"
//						+ "	da.DT_COSTITUZIONE,\r\n"
//						+ "	da.DESC_FORMA_GIURIDICA,\r\n"
//						+ "	pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "	pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "	ind.DESC_INDIRIZZO,\r\n"
//						+ "	ind.CAP,\r\n"
//						+ "	ind.DESC_COMUNE,\r\n"
//						+ "	ind.DESC_PROVINCIA,\r\n"
//						+ "	ind.DESC_REGIONE,\r\n"
//						+ "	ind.DESC_NAZIONE,\r\n"
//						+ "	ind.COD_ATECO,\r\n"
//						+ "	ind.DESC_ATECO,\r\n"
//						+ "	ind.ID_ATTIVITA_ATECO,\r\n"
//						+ "	ind.id_indirizzo,\r\n"
//						+ "	ind.id_comune,\r\n"
//						+ "	ind.id_regione,\r\n"
//						+ "	ind.id_provincia,\r\n"
//						+ "	ind.id_nazione,\r\n"
//						+ "	ind.id_sede,\r\n"
//						+ "	da.DT_INIZIO_ATTIVITA_ESITO,\r\n"
//						+ "	da.PERIODO_SCADENZA_ESERCIZIO,\r\n"
//						+ "	da.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
//						+ "	da.FLAG_RATING_LEGALITA,\r\n"
//						+ "	di.NUM_ISCRIZIONE,\r\n"
//						+ "	di.DT_ISCRIZIONE,\r\n"
//						+ "	di.ID_ISCRIZIONE,\r\n"
//						+ "	di.provincia_iscriz,\r\n"
//						+ "	di.id_prov_iscriz,\r\n"
//						+ "	pdss.desc_sezione_speciale,\r\n"
//						+ "	ind.PARTITA_IVA,\r\n"
//						+ "	prsd.ID_ENTE_GIURIDICO,\r\n"
//						+ "	prsd.PROGR_SOGGETTO_DOMANDA, prsc.PROGR_SOGGETTI_CORRELATI , prsp.PROGR_SOGGETTO_PROGETTO, \r\n"
//						+ " ptr.pec, ptr.id_recapiti,\r\n"
//						+ " pdss.ID_SEZIONE_SPECIALE,\r\n"
//						+ " ptegs.ID_ENTE_SEZIONE, di.ID_TIPO_ISCRIZIONE \r\n"
//						+ "FROM\r\n"
//						+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
//						+ "	LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
//						+ "	LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
//						+ "	LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
//						+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSp.ID_SOGGETTO\r\n"
//						+ "	LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
//						+ "	LEFT JOIN indirizzo ind ON prsp.PROGR_SOGGETTO_PROGETTO  = ind.PROGR_SOGGETTO_PROGETTO\r\n"
//						+ "	LEFT JOIN dati_iscrizione di ON di.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
//						+ "	LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsp.id_soggetto\r\n"
//						+ "	LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON ptegs.ID_SEZIONE_SPECIALE = pdss.ID_SEZIONE_SPECIALE\r\n"
//						+ "	LEFT JOIN dati_anagrafici da ON da.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
//						+ "	left join PBANDI_R_SOGGETTO_DOMANDA prsD on prsp.PROGR_SOGGETTO_DOMANDA= prsd.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ " LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
//						+ "	LEFT JOIN pbandi_t_recapiti ptr ON ptr.ID_RECAPITI  = prsps.ID_RECAPITI\r\n "
//						+ "WHERE\r\n"
//						+ "	prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
//						+ "	AND pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
//						+ "	AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
//						+ "	AND prsp.ID_PERSONA_FISICA IS NULL\r\n"
//						+ "	AND prsp.ID_PROGETTO  ="+idProgetto+"\r\n"
//						+ "	and prsc.PROGR_SOGGETTI_CORRELATI= "+idSoggCorr+"\r\n"
//						+ "	and prsp.ID_SOGGETTO="+ idSoggetto;
//			} else {		
//				query = "WITH dati_anagrafici AS (\r\n"
//						+ "	SELECT\r\n"
//						+ "		prsc.ID_SOGGETTO,\r\n"
//						+ "		pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "		prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "		pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
//						+ "		pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
//						+ "		PTEG.COD_UNI_IPA,\r\n"
//						+ "		pts.CODICE_FISCALE_SOGGETTO,\r\n"
//						+ "		pts.ndg,\r\n"
//						+ "		pdfg.DESC_FORMA_GIURIDICA,\r\n"
//						+ "		pteg.ID_FORMA_GIURIDICA,\r\n"
//						+ "		PTEG.DT_COSTITUZIONE,\r\n"
//						+ "		pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n"
//						+ "		pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
//						+ "		PTEG.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
//						+ "		pteg.FLAG_RATING_LEGALITA,\r\n"
//						+ "		pteg.ID_ENTE_GIURIDICO,\r\n"
//						+ "		prsc.PROGR_SOGGETTI_CORRELATI\r\n"
//						+ "	FROM\r\n"
//						+ "		PBANDI_T_ENTE_GIURIDICO pteg\r\n"
//						+ "		LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON PTEG.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA\r\n"
//						+ "		LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PTEG.ID_SOGGETTO\r\n"
//						+ "		LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.ID_SOGGETTO = pteg.ID_SOGGETTO\r\n"
//						+ "		LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
//						+ "),\r\n"
//						+ "indirizzo AS (\r\n"
//						+ "	SELECT\r\n"
//						+ "		prsds.PROGR_SOGGETTO_DOMANDA,\r\n"
//						+ "		pti.DESC_INDIRIZZO,\r\n"
//						+ "		pti.id_indirizzo,\r\n"
//						+ "		pti.id_comune,\r\n"
//						+ "		pti.id_provincia,\r\n"
//						+ "		pti.id_regione,\r\n"
//						+ "		pti.id_nazione,\r\n"
//						+ "		pti.CAP,\r\n"
//						+ "		pts.id_sede,\r\n"
//						+ "		pdc.DESC_COMUNE,\r\n"
//						+ "		pdp.DESC_PROVINCIA,\r\n"
//						+ "		pdr.DESC_REGIONE,\r\n"
//						+ "		pdn.DESC_NAZIONE,\r\n"
//						+ "		pts.ID_ATTIVITA_ATECO,\r\n"
//						+ "		pdaa.COD_ATECO,\r\n"
//						+ "		pdaa.DESC_ATECO,\r\n"
//						+ "		pts.PARTITA_IVA\r\n"
//						+ "	FROM\r\n"
//						+ "		PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds\r\n"
//						+ "		LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ "		LEFT JOIN PBANDI_T_SEDE pts ON pts.ID_SEDE = PRSDS.ID_SEDE\r\n"
//						+ "		LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = PRSDS.ID_INDIRIZZO\r\n"
//						+ "		LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
//						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
//						+ "		LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pti.ID_REGIONE\r\n"
//						+ "		LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
//						+ "		LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pts.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO\r\n"
//						+ "	WHERE\r\n"
//						+ "		PRSDS.ID_TIPO_SEDE = 1\r\n"
//						+ "		AND prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
//						+ "),\r\n"
//						+ "dati_iscrizione AS (\r\n"
//						+ "	SELECT\r\n"
//						+ "		pti.NUM_ISCRIZIONE,\r\n"
//						+ "		pti.DT_ISCRIZIONE,\r\n"
//						+ "		pdp.DESC_PROVINCIA AS provincia_iscriz,\r\n"
//						+ "		prsd.ID_SOGGETTO, pti.ID_TIPO_ISCRIZIONE,\r\n"
//						+ "		pdp.id_provincia as id_prov_iscriz,\r\n"
//						+ "		prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
//						+ "		pti.ID_ISCRIZIONE\r\n"
//						+ "	FROM\r\n"
//						+ "		PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
//						+ "		LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON PRSD.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
//						+ "		LEFT JOIN PBANDI_T_ISCRIZIONE pti ON prsd.ID_ISCRIZIONE_PERSONA_GIURID = pti.ID_ISCRIZIONE\r\n"
//						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
//						+ ")\r\n"
//						+ "SELECT\r\n"
//						+ "	DISTINCT prsd.ID_SOGGETTO,\r\n"
//						+ "	pdts.DESC_TIPO_SOGGETTO,\r\n"
//						+ "	da.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
//						+ "	da.FLAG_PUBBLICO_PRIVATO,\r\n"
//						+ "	da.ID_FORMA_GIURIDICA,\r\n"
//						+ "	da.COD_UNI_IPA,\r\n"
//						+ "	da.CODICE_FISCALE_SOGGETTO,\r\n"
//						+ "	da.ndg,\r\n"
//						+ "	da.DT_COSTITUZIONE,\r\n"
//						+ "	da.DESC_FORMA_GIURIDICA,\r\n"
//						+ "	pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "	pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
//						+ "	ind.DESC_INDIRIZZO,\r\n"
//						+ "	ind.CAP,\r\n"
//						+ "	ind.DESC_COMUNE,\r\n"
//						+ "	ind.DESC_PROVINCIA,\r\n"
//						+ "	ind.DESC_REGIONE,\r\n"
//						+ "	ind.DESC_NAZIONE,\r\n"
//						+ "	ind.COD_ATECO,\r\n"
//						+ "	ind.DESC_ATECO,\r\n"
//						+ "	ind.ID_ATTIVITA_ATECO,\r\n"
//						+ "	ind.id_indirizzo,\r\n"
//						+ "	ind.id_comune,\r\n"
//						+ "	ind.id_regione,\r\n"
//						+ "	ind.id_provincia,\r\n"
//						+ "	ind.id_nazione,\r\n"
//						+ "	ind.id_sede,\r\n"
//						+ "	da.DT_INIZIO_ATTIVITA_ESITO,\r\n"
//						+ "	da.PERIODO_SCADENZA_ESERCIZIO,\r\n"
//						+ "	da.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
//						+ "	da.FLAG_RATING_LEGALITA,\r\n"
//						+ "	di.NUM_ISCRIZIONE,\r\n"
//						+ "	di.DT_ISCRIZIONE,\r\n"
//						+ "	di.ID_ISCRIZIONE,\r\n"
//						+ "	di.provincia_iscriz,\r\n"
//						+ "	di.id_prov_iscriz,\r\n"
//						+ "	pdss.desc_sezione_speciale,\r\n"
//						+ "	ind.PARTITA_IVA,\r\n"
//						+ "	prsd.ID_ENTE_GIURIDICO,\r\n"
//						+ "	prsd.PROGR_SOGGETTO_DOMANDA, prsc.PROGR_SOGGETTI_CORRELATI , prsp.PROGR_SOGGETTO_PROGETTO , \r\n"
//						+ " ptr.pec, ptr.id_recapiti,\r\n"
//						+ " pdss.ID_SEZIONE_SPECIALE,\r\n"
//						+ " ptegs.ID_ENTE_SEZIONE, prsc.QUOTA_PARTECIPAZIONE, di.ID_TIPO_ISCRIZIONE \r\n"
//						+ "FROM\r\n"
//						+ "	PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
//						+ "	LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ "	LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
//						+ "	LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
//						+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
//						+ "	LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
//						+ "	LEFT JOIN indirizzo ind ON prsd.PROGR_SOGGETTO_DOMANDA = ind.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ "	LEFT JOIN dati_iscrizione di ON di.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ "	LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsd.id_soggetto\r\n"
//						+ "	LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON ptegs.ID_SEZIONE_SPECIALE = pdss.ID_SEZIONE_SPECIALE\r\n"
//						+ "	LEFT JOIN dati_anagrafici da ON da.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
//						+ "	left join PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.PROGR_SOGGETTO_DOMANDA= prsd.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ " LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
//						+ "	LEFT JOIN pbandi_t_recapiti ptr ON ptr.ID_RECAPITI  = prsds.ID_RECAPITI\r\n "
//						+ "WHERE\r\n"
//						+ "	prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
//						+ "	AND pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
//						+ "	AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
//						+ "	AND prsd.ID_PERSONA_FISICA IS NULL\r\n"
//						+ "	AND prsd.ID_DOMANDA = "+veroIdDomanda+"\r\n"
//						+ "	and prsc.PROGR_SOGGETTI_CORRELATI="+idSoggCorr+"\r\n"
//						+ "	and prsd.ID_SOGGETTO="+ idSoggetto;
//			}
			
			
			
//			soggetto = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioVO>() {
//				
//				@Override
//				public AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException {
//					AnagraficaBeneficiarioVO anag = new AnagraficaBeneficiarioVO();
//					
//					while(rs.next()) {		
//						anag.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
//						anag.setDescFormaGiur(rs.getString("DESC_FORMA_GIURIDICA"));
//						anag.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
//						anag.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
//						anag.setCodUniIpa(rs.getString("COD_UNI_IPA"));
//						anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
//						anag.setCfSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
//						anag.setDtCostituzione(rs.getDate("dt_costituzione"));
//						anag.setDescIndirizzo(rs.getString("desc_indirizzo"));
//						anag.setpIva(rs.getString("PARTITA_IVA"));
//						anag.setDescComune(rs.getString("DESC_COMUNE"));
//						anag.setDescProvincia(rs.getString("DESC_PROVINCIA"));
//						anag.setCap(rs.getString("CAP"));
//						anag.setDescRegione(rs.getString("DESC_REGIONE"));
//						anag.setDescNazione(rs.getString("DESC_NAZIONE"));
//						anag.setCodAteco(rs.getString("COD_ATECO"));
//						anag.setDescAteco(rs.getString("desc_ateco"));
//						anag.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
//						//anag.setDescStatoAttivita(rs.getString("desc_stato_attivita"));
//						anag.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
//						anag.setDtUltimoEseChiuso(rs.getDate("dt_ultimo_esercizio_chiuso")); 
//						anag.setPeriodoScadEse(rs.getString("periodo_scadenza_esercizio"));// periodo chiusura esercizio 
//						anag.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
//						anag.setDtIscrizione(rs.getDate("dt_iscrizione"));
//						anag.setDescProvinciaIscriz (rs.getString("provincia_iscriz")); 
//						anag.setDescSezioneSpeciale(rs.getString("desc_sezione_speciale"));
//						anag.setIdIndirizzo(rs.getLong("id_indirizzo"));
//						anag.setIdComune(rs.getLong("id_comune"));
//						anag.setIdRegione(rs.getLong("id_regione"));
//						anag.setIdProvincia(rs.getLong("id_provincia"));
//						anag.setIdNazione(rs.getLong("id_nazione"));
//						anag.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
//						//anag.setDescStatoDomanda(rs.getString("desc_stato_domanda")); 
//						anag.setProgSoggDomanda(rs.getString("PROGR_SOGGETTO_DOMANDA"));
//						anag.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
//						anag.setDescTipoSoggCorr(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
//						anag.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
//						anag.setIdDomanda(idDomanda);
//						anag.setIdIscrizione(rs.getLong("ID_ISCRIZIONE"));
//						anag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
//						anag.setIdAttAteco(rs.getLong("ID_ATTIVITA_ATECO"));
//						anag.setIdSede(rs.getLong("id_sede"));
//						anag.setIdProvinciaIscrizione(rs.getLong("id_prov_iscriz"));
//						anag.setNdg(rs.getString("ndg"));
//						anag.setDescPec(rs.getString("pec"));
//						anag.setIdRecapiti(rs.getLong("id_recapiti"));
//						anag.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
//						anag.setIdEnteSezione(rs.getLong("id_ente_sezione"));
//						anag.setQuotaPartecipazione(rs.getString("QUOTA_PARTECIPAZIONE"));
//						anag.setIdTipoIscrizione(rs.getLong("ID_TIPO_ISCRIZIONE"));
//						anag.setProgSoggProgetto(rs.getString("PROGR_SOGGETTO_PROGETTO"));
//						return anag;
//					}
//					
//					return anag;
//				}
//				});
//			
			
		
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_T_ENTE_GIURIDICO", e);
		}
			
		return soggetto;
	}


	private AnagraficaBeneficiarioVO getSoggIndDomaEG(String sqlGetAnag) {
AnagraficaBeneficiarioVO benef = new AnagraficaBeneficiarioVO();
		
		try {
			
			
			benef = getJdbcTemplate().query(sqlGetAnag, new ResultSetExtractor<AnagraficaBeneficiarioVO>()
			{
				@Override
				public  AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException
				{
					AnagraficaBeneficiarioVO anag = new AnagraficaBeneficiarioVO();

					while (rs.next())
					{
						anag.setCap(rs.getString("CAP"));
						anag.setCfSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
						anag.setCodAteco(rs.getString("COD_ATECO"));
						anag.setCodUniIpa(rs.getString("COD_UNI_IPA"));
						anag.setDescAteco(rs.getString("DESC_ATECO"));
						anag.setDescComune(rs.getString("DESC_COMUNE"));
						anag.setDescFormaGiur(rs.getString("DESC_FORMA_GIURIDICA"));
						anag.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
						anag.setDescNazione(rs.getString("DESC_NAZIONE"));
						anag.setDescProvincia(rs.getString("DESC_PROVINCIA"));
						anag.setDescProvinciaIscriz(rs.getString("desc_prov_isc"));
						anag.setDescRegione(rs.getString("DESC_REGIONE"));
						anag.setDescSezioneSpeciale(rs.getString("DESC_SEZIONE_SPECIALE"));
						anag.setDescStatoAttivita(rs.getString("DESC_STATO_ATTIVITA"));
						anag.setDtCostituzione(rs.getDate("DT_COSTITUZIONE"));
						anag.setDtIscrizione(rs.getDate("DT_ISCRIZIONE"));
						anag.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
						anag.setDtUltimoEseChiuso(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
						anag.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
						anag.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
						anag.setIdAttAteco(rs.getLong("ID_ATTIVITA_ATECO"));
						anag.setIdComune(rs.getLong("ID_COMUNE"));
						anag.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
						anag.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
						anag.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
						anag.setIdIscrizione(rs.getLong("id_iscrizione"));
						anag.setIdNazione(rs.getLong("ID_NAZIONE"));
						anag.setIdProvincia(rs.getLong("id_provincia"));
						anag.setIdProvinciaIscrizione(rs.getLong("id_prov_isc"));
						anag.setIdRegione(rs.getLong("ID_REGIONE"));
						anag.setIdSede(rs.getLong("ID_SEDE"));
						//anag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						anag.setIdStatoAtt(rs.getLong("ID_STATO_ATTIVITA"));
						anag.setIdTipoIscrizione(rs.getLong("id_tipo_iscrizione"));
						anag.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
						anag.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
						anag.setPeriodoScadEse(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
						anag.setpIva(rs.getString("partita_iva"));
						anag.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
						anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						anag.setDescPec(rs.getString("PEC"));
						anag.setQuotaPartecipazione(rs.getString("QUOTA_PARTECIPAZIONE"));
						anag.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
						anag.setDescTipoSoggCorr(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						anag.setIdEnteSezione(rs.getLong("id_ente_sezione"));
						anag.setProgSoggProgetto(rs.getString("PROGR_SOGGETTO_PROGETTO"));
						anag.setProgSoggDomanda(rs.getString("PROGR_SOGGETTO_DOMANDA"));
						//anag.setIdDomanda(rs.getString("ID_DOMANDA"));
						anag.setNdg(rs.getString("NDG"));
						anag.setIdRecapiti(rs.getLong("ID_RECAPITI"));
						anag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						Long regione = rs.getLong("ID_REGIONE");
						if(regione>0 && regione !=null) {
							anag.setDescNazione("ITALIA");
							anag.setIdNazione((long) 118);
						}
					}
					return anag;
				}
			});
			
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read AnagraficaBeneficiarioVO", e);
		}
		finally {
			LOG.info( " END");
			}
		return benef;
	}


	@Override
	public AnagraficaBeneficiarioPfVO getSoggCorrIndDaDomPF(String idDomanda, String idSoggetto) {
		String prf = "[AnagraficaBeneficiarioDAOImpl:: soggetto correlato indipendenti da domanda persona fisica]";
		LOG.info(prf + " BEGIN");
		
		AnagraficaBeneficiarioPfVO soggetto = new AnagraficaBeneficiarioPfVO(); 
		
		try {
			
			String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where numero_domanda='"+idDomanda+"'\r\n"
					+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ "    AND ROWNUM =1 \r\n";
			BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda,
					BigDecimal.class);
			// controllo se questa domanda ha un progetto e vasdo sulla prsp sennò vado
			// sulla prsd
			BigDecimal idProgetto = checkProgetto(idDomanda);
			
			if(idProgetto!=null){
				
				String query =  "WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "        PTPF.ID_SOGGETTO,\r\n"
						+ "        ptpf.DT_NASCITA,\r\n"
						+ "        ptpf.COGNOME,\r\n"
						+ "        ptpf.NOME,\r\n"
						+ "        pdc.DESC_COMUNE AS comune_nasc,\r\n"
						+ "        pdp.DESC_PROVINCIA AS prov_nasc,\r\n"
						+ "        pdr.DESC_REGIONE AS regione_nasc,\r\n"
						+ "        pdn.DESC_NAZIONE AS nazione_nasc,\r\n"
						+ "        pdc.ID_COMUNE,\r\n"
						+ "        pdc.CAP AS capPF,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg,\r\n"
						+ "        pdn.ID_NAZIONE,\r\n"
						+ "        PTPF.ID_PERSONA_FISICA,\r\n"
						+ "        pdce.ID_COMUNE_ESTERO,\r\n"
						+ "        pdce.DESC_COMUNE_ESTERO,\r\n"
						+ "        pdn2.DESC_NAZIONE AS nazione_est,\r\n"
						+ "        pdn2.ID_NAZIONE AS id_nazione_est\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_progetto prsp\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON PTPF.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE\r\n"
						+ "        AND pdce.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn2 ON pdce.ID_NAZIONE = pdn2.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "),\r\n"
						+ "residenza AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "        prsp.ID_PROGETTO,\r\n"
						+ "        pti.ID_INDIRIZZO,\r\n"
						+ "        pti.DESC_INDIRIZZO,pti.cap as capPF,\r\n"
						+ "        pdc.ID_COMUNE AS id_com_res,\r\n"
						+ "        pdc.DESC_COMUNE AS com_res,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov_res,\r\n"
						+ "        pdp.SIGLA_PROVINCIA AS prov_res,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg_res,\r\n"
						+ "        pdr.DESC_REGIONE AS reg_res,\r\n"
						+ "        pdn.ID_NAZIONE AS id_naz_res,\r\n"
						+ "        pdn.DESC_NAZIONE AS naz_res\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_progetto prsp\r\n"
						+ "        LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsp.ID_INDIRIZZO_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    ds.id_soggetto,\r\n"
						+ "    ds.cognome,\r\n"
						+ "    ds.nome,\r\n"
						+ "    ds.DT_NASCITA,\r\n"
						+ "    ds.comune_nasc,\r\n"
						+ "    ds.prov_nasc,\r\n"
						+ "    res.capPF,\r\n"
						+ "    ds.regione_nasc,\r\n"
						+ "    ds.nazione_nasc,\r\n"
						+ "    ds.id_comune,\r\n"
						+ "    ds.id_reg,\r\n"
						+ "    ds.id_prov,\r\n"
						+ "    ds.id_nazione,\r\n"
						+ "    ds.id_nazione_est,\r\n"
						+ "    ds.nazione_est,\r\n"
						+ "    ds.ID_COMUNE_ESTERO,\r\n"
						+ "    ds.DESC_COMUNE_ESTERO,\r\n"
						+ "    ds.ID_PERSONA_FISICA,\r\n"
						+ "    res.id_progetto,\r\n"
						+ "    res.ID_INDIRIZZO,\r\n"
						+ "    res.DESC_INDIRIZZO,\r\n"
						+ "    res.id_com_res,\r\n"
						+ "    res.id_prov_res,\r\n"
						+ "    res.id_reg_res,\r\n"
						+ "    res.id_naz_res,\r\n"
						+ "    res.com_res,\r\n"
						+ "    res.prov_res,\r\n"
						+ "    res.reg_res,\r\n"
						+ "    res.naz_res,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    res.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsc.ID_SOGGETTO_ENTE_GIURIDICO,\r\n"
						+ "    pts.NDG\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON prsp.PROGR_SOGGETTO_PROGETTO = ds.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN residenza res ON res.PROGR_SOGGETTO_progetto = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON ds.id_soggetto = pts.ID_SOGGETTO\r\n"
						+ "WHERE\r\n"
						+ "    pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
						+ "    and ds.id_soggetto ="+idSoggetto+"\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto;
				
				soggetto = getSogCorPf(query); 
			} else {
				String query; 
				query = "WITH \r\n"
						+ "dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        PTPF.ID_SOGGETTO,\r\n"
						+ "        ptpf.DT_NASCITA,\r\n"
						+ "        ptpf.COGNOME,\r\n"
						+ "        ptpf.NOME,\r\n"
						+ "        pdc.DESC_COMUNE AS comune_nasc,\r\n"
						+ "        pdp.DESC_PROVINCIA AS prov_nasc,\r\n"
						+ "        pdr.DESC_REGIONE AS regione_nasc,\r\n"
						+ "        pdn.DESC_NAZIONE AS nazione_nasc,\r\n"
						+ "        pdc.ID_COMUNE,\r\n"
						+ "        pdc.CAP AS capPF,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg,\r\n"
						+ "        pdn.ID_NAZIONE,\r\n"
						+ "        PTPF.ID_PERSONA_FISICA,\r\n"
						+ "        pdce.ID_COMUNE_ESTERO,\r\n"
						+ "        pdce.DESC_COMUNE_ESTERO,\r\n"
						+ "        pdn2.DESC_NAZIONE AS nazione_est,\r\n"
						+ "        pdn2.ID_NAZIONE AS id_nazione_est\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON PTPF.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE\r\n"
						+ "        AND pdce.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn2 ON pdce.ID_NAZIONE = pdn2.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsd.ID_TIPO_ANAGRAFICA <> 1    \r\n"
						+ "),\r\n"
						+ "residenza AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        prsd.id_domanda,\r\n"
						+ "        pti.ID_INDIRIZZO,\r\n"
						+ "        pti.DESC_INDIRIZZO,pti.cap as CAPPF,\r\n"
						+ "        pdc.ID_COMUNE AS id_com_res,\r\n"
						+ "        pdc.DESC_COMUNE AS com_res,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov_res,\r\n"
						+ "        pdp.SIGLA_PROVINCIA AS prov_res,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg_res,\r\n"
						+ "        pdr.DESC_REGIONE AS reg_res,\r\n"
						+ "        pdn.ID_NAZIONE AS id_naz_res,\r\n"
						+ "        pdn.DESC_NAZIONE AS naz_res\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsd.ID_INDIRIZZO_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    ds.id_soggetto,\r\n"
						+ "    ds.cognome,\r\n"
						+ "    ds.nome,\r\n"
						+ "    ds.DT_NASCITA,\r\n"
						+ "    ds.comune_nasc,\r\n"
						+ "    ds.prov_nasc,\r\n"
						+ "    res.capPF,\r\n"
						+ "    ds.regione_nasc,\r\n"
						+ "    ds.nazione_nasc,\r\n"
						+ "    ds.id_comune,\r\n"
						+ "    ds.id_reg,\r\n"
						+ "    ds.id_prov,\r\n"
						+ "    ds.id_nazione,\r\n"
						+ "    ds.id_nazione_est,\r\n"
						+ "    ds.nazione_est,\r\n"
						+ "    ds.ID_COMUNE_ESTERO,\r\n"
						+ "    ds.DESC_COMUNE_ESTERO,\r\n"
						+ "    ds.ID_PERSONA_FISICA ,\r\n"
						+ "    res.id_domanda,\r\n"
						+ "    res.ID_INDIRIZZO,\r\n"
						+ "    res.DESC_INDIRIZZO,\r\n"
						+ "    res.id_com_res,\r\n"
						+ "    res.id_prov_res,\r\n"
						+ "    res.id_reg_res,\r\n"
						+ "    res.id_naz_res,\r\n"
						+ "    res.com_res,\r\n"
						+ "    res.prov_res,\r\n"
						+ "    res.reg_res,\r\n"
						+ "    res.naz_res,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    res.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE ,\r\n"
						+ "    prsc.ID_TIPO_SOGGETTO_CORRELATO , \r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO ,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI ,\r\n"
						+ "    prsc.ID_SOGGETTO_ENTE_GIURIDICO, pts.NDG\r\n"
						+ "  FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON prsd.PROGR_SOGGETTO_DOMANDA = ds.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN residenza res ON res.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "     LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON ds.id_soggetto = pts.ID_SOGGETTO\r\n"
						+ "WHERE\r\n"	
						+ "     pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    and ds.id_soggetto ="+idSoggetto 
						+ "    AND prsd.ID_DOMANDA ="+veroIdDomanda+"\r\n"; 
				
						//+ "    and res.id_domanda ="+ veroIdDomanda; 
				
				soggetto = getSogCorPf(query);
				
			}
			
			soggetto.setIdDomanda(veroIdDomanda.toString());
			if (soggetto.getIdNazioneEsteraNascita() != 0) {
				soggetto.setIdNazioneDiNascita(soggetto.getIdNazioneEsteraNascita());
				soggetto.setNazioneDiNascita(soggetto.getNazioneNascitaEstera());
				soggetto.setComuneDiNascita(soggetto.getComuneNascitaEstero());
				soggetto.setIdComuneDiNascita(soggetto.getIdComuneEsteraNascita());
			}
			if (soggetto.getRegioneDiNascita() != null) {
				soggetto.setNazioneDiNascita("ITALIA");
				soggetto.setIdNazioneDiNascita((long) 118);
			}
			if (soggetto.getIdRegionePF()>0) {
				soggetto.setNazionePF("ITALIA");
				soggetto.setIdNazionePF((long) 118);
			}

		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_T_PERSONA_FISICA", e);
		}

		return soggetto;
	}


	private AnagraficaBeneficiarioPfVO getSogCorPf(String query) {

		
		AnagraficaBeneficiarioPfVO soggetto = new AnagraficaBeneficiarioPfVO();
		
		try {
			 soggetto = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>() {
					
					@Override
					public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
						AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO();
						
						while(rs.next()) {
							
							anag.setProgSoggDomanda(rs.getBigDecimal("PROGR_SOGGETTO_DOMANDA"));
							anag.setIdSoggettoEnteGiuridico(rs.getBigDecimal("ID_SOGGETTO_ENTE_GIURIDICO"));
							anag.setCapPF(rs.getString("capPF"));
							anag.setCognome(rs.getString("cognome"));
							anag.setNome(rs.getString("nome"));
							anag.setDataDiNascita(rs.getDate("DT_NASCITA"));
							anag.setComuneDiNascita(rs.getString("comune_nasc"));
							anag.setProvinciaDiNascita(rs.getString("prov_nasc"));
							anag.setRegioneDiNascita(rs.getString("regione_nasc"));
							anag.setNazioneDiNascita(rs.getString("nazione_nasc"));
							anag.setIdComuneDiNascita(rs.getLong("id_comune"));
							anag.setIdRegioneDiNascita(rs.getLong("id_reg"));
							anag.setIdProvinciaDiNascita(rs.getLong("id_prov"));
							anag.setIdNazioneDiNascita(rs.getLong("id_nazione"));
							anag.setIdRuoloPF(rs.getString("ID_TIPO_SOGGETTO_CORRELATO"));
							anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
							anag.setIdPersonaFisica(rs.getLong("ID_PERSONA_FISICA"));
							anag.setQuotaPartecipazione(rs.getBigDecimal("QUOTA_PARTECIPAZIONE"));
							anag.setIdIndirizzo(rs.getString("ID_INDIRIZZO"));
							anag.setIndirizzoPF(rs.getString("DESC_INDIRIZZO"));
							anag.setIdComunePF(rs.getLong("id_com_res"));
							anag.setIdProvinciaPF(rs.getLong("id_prov_res"));
							anag.setIdRegionePF(rs.getLong("id_reg_res"));
							anag.setIdNazionePF(rs.getLong("id_naz_res"));
							anag.setComunePF(rs.getString("com_res"));
							anag.setProvinciaPF(rs.getString("prov_res"));
							anag.setRegionePF(rs.getString("reg_res"));
							anag.setNazionePF(rs.getString("naz_res"));
							//anag.setIdDomanda(rs.getString("id_domanda"));	
							anag.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
							anag.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));// questo è il ruolo
							anag.setProgrSoggCorr(rs.getBigDecimal("PROGR_SOGGETTI_CORRELATI"));
							anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO"));
							anag.setComuneNascitaEstero(rs.getString("DESC_COMUNE_ESTERO")); 
							anag.setIdNazioneEsteraNascita(rs.getLong("id_nazione_est"));
							anag.setNazioneNascitaEstera(rs.getString("nazione_est"));
							anag.setNdg(rs.getString("NDG"));
							anag.setQuotaPartecipazione(rs.getBigDecimal("QUOTA_PARTECIPAZIONE"));
						}
				return anag; 
					}
		   	});
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::getSogCorPf] Exception " + e.getMessage());
		}
		
		return soggetto;
	}


	@Transactional
	@Override
	public Boolean modificaSoggcorrEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto, String idDomanda,  BigDecimal idSoggCorr) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::elenco soggetti correlati dipendenti da domanda]";
		LOG.info(prf + " BEGIN");
		
		Boolean result; 
		
		try {
			result = updateAnagBeneGiuridico(soggetto, idSoggetto, idDomanda, true);
			if(result== true) {
				
				String update = "update PBANDI_R_SOGGETTI_CORRELATI set \r\n"
						+ "  id_utente_agg= ?, \r\n" 
						+ "  QUOTA_PARTECIPAZIONE= ?\r\n"
						+"  where PROGR_SOGGETTI_CORRELATI=?";
				
				getJdbcTemplate().update(update, new Object[] {
						soggetto.getIdUtenteAgg(), soggetto.getQuotaPartecipazione(), idSoggCorr
				}); 
			} else {
				return false;
			}
		} catch (Exception e) {
			result = false; 
			LOG.error("Exception while trying to insert soggetto ente giuridico", e);
		}
		
		return result;
	}

	private boolean checkIscrizione(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {
		
		AnagraficaBeneficiarioVO anagDB= new AnagraficaBeneficiarioVO(); 
		if(soggetto.getIdIscrizione()>0) {
			
			String getIScrizione = "Select NUM_ISCRIZIONE,  TRUNC(DT_ISCRIZIONE) AS DT_ISCRIZIONE, ID_PROVINCIA, ID_REGIONE "
					+ "from PBANDI_T_ISCRIZIONE where ID_ISCRIZIONE="+ soggetto.getIdIscrizione();
			
			anagDB = getJdbcTemplate().query(getIScrizione, new ResultSetExtractor<AnagraficaBeneficiarioVO>() {

				@Override
				public AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					AnagraficaBeneficiarioVO ana = new AnagraficaBeneficiarioVO();
					while (rs.next()) {
						ana.setIdProvinciaIscrizione(rs.getLong("ID_PROVINCIA"));
						ana.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
						ana.setDtIscrizione(rs.getDate("DT_ISCRIZIONE"));
					}
					return ana;
				}
			});
				
		} else {
			return false;
		}
		if(anagDB.getIdProvinciaIscrizione()!=soggetto.getIdProvinciaIscrizione()) {			
			return false; 
		}
		
		if(!anagDB.getDtIscrizione().toString().trim().equals(soggetto.getDtIscrizione().toString().trim())) {			
			return false;
		}
		if(!anagDB.getNumIscrizione().trim().equals(soggetto.getNumIscrizione().trim())) {
			return false;			
		}
	
		
		
		return true;
	}


	private void insertSedeLegale(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {
		
		// recupero l'id_indirizzo 
		String getId = "SELECT SEQ_PBANDI_T_INDIRIZZO.nextval FROM dual";	
		BigDecimal idIndirizzo=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
		
		
		String insertIndirizzo = "insert into PBANDI_T_INDIRIZZO ("
				+ "id_indirizzo, "
				+ "desc_indirizzo, "
				+ "cap, "
				+ "id_nazione, "
				+ "id_comune, "
				+ "id_regione, "
				+ "id_provincia,"
				+ "id_utente_ins, "
				+ "dt_inizio_validita) values(?,?,?,?,?,?,?,?,sysdate)";
		getJdbcTemplate().update(insertIndirizzo, new Object[] {
			idIndirizzo,
			soggetto.getDescIndirizzo(), 
			soggetto.getCap(), 
			(soggetto.getIdNazione()>0)?soggetto.getIdNazione():null, 
			(soggetto.getIdComune()>0)?soggetto.getIdComune():null, 
			(soggetto.getIdRegione()>0)?soggetto.getIdRegione():null, 
			(soggetto.getIdProvincia()>0)?soggetto.getIdProvincia():null, 
			soggetto.getIdUtenteAgg()
			
		});
		// inserisco il nuovo indirizzo nelle tabelle PBANDI_R_SOGGETTO_DOMANDA_SEDE e PBANDI_R_SOGGETTO_DOMANDA_SEDE
		
		String updateIndirizoIntoRsoggSede = "update PBANDI_R_SOGGETTO_DOMANDA_SEDE set "
				+ " id_indirizzo="+ idIndirizzo
				+", id_utente_agg="+ soggetto.getIdUtenteAgg()
				+" where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda();
		getJdbcTemplate().update(updateIndirizoIntoRsoggSede); 
		
		
		
		if(soggetto.getProgSoggProgetto()!=null) {
			String updateIndirizoIntoRSoggProgSede = "update PBANDI_R_SOGG_PROGETTO_SEDE set "
					+ " id_indirizzo="+ idIndirizzo
					+", id_utente_agg="+ soggetto.getIdUtenteAgg()
					+" where PROGR_SOGGETTO_PROGETTO="+ soggetto.getProgSoggProgetto();
			getJdbcTemplate().update(updateIndirizoIntoRSoggProgSede); 
		}
		
	}


	private void updateIndirizzoSedeLegale(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {

		String updateSede ="update PBANDI_T_INDIRIZZO set "
				+ "desc_indirizzo="+soggetto.getDescIndirizzo()
				+ ", id_nazione="+ soggetto.getIdNazione()
				+ ", id_regione="+soggetto.getIdRegione()
				+ ", id_comune="+soggetto.getIdComune()
				+ ", id_provincia="+soggetto.getIdProvincia()
				+ ", id_utente_agg="+ soggetto.getIdUtenteAgg()
				+ " where id_indirizzo="+soggetto.getIdIndirizzo();
		
		getJdbcTemplate().update(updateSede); 
		
	}


	private void insertSoggettoCorrelatoEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {

		String getProgSoggCorrId = "SELECT SEQ_PBANDI_R_SOGG_CORRELATI.nextval FROM dual";	
		BigDecimal progSoggcorr =  getJdbcTemplate().queryForObject(getProgSoggCorrId, BigDecimal.class);
		
		String insertSoggCorr ="insert into PBANDI_R_SOGGETTI_CORRELATI ("
				+ " id_tipo_soggetto_correlato, "
				+ "id_utente_ins, "
				+ "quota_partecipazione, "
				+ "id_soggetto,"
				+ "id_soggetto_ente_giuridico, "
				+ "progr_soggetti_correlati,"
				+ "DT_INIZIO_VALIDITA ) values(?,?,?,?,?,?, trunc(sysdate))";
		
		getJdbcTemplate().update(insertSoggCorr, new Object[] {
				soggetto.getIdTipoSoggCorr(), 
				soggetto.getIdUtenteAgg(),
				soggetto.getQuotaPartecipazione(),
				idSoggetto,
				idSoggetto,
				progSoggcorr
				
		});
		
		// insert into PBANDI_R_SOGG_DOM_SOGG_CORREL il nuovo soggetto correlato
		String insertSoggDom = "insert into PBANDI_R_SOGG_DOM_SOGG_CORREL("
				+ "progr_soggetto_domanda, "
				+ "progr_soggetti_correlati, "
				+ "id_utente_ins, "
				+ "dt_inserimento) values(?,?,?,sysdate)";
		getJdbcTemplate().update(insertSoggDom, new Object[] {
				soggetto.getProgSoggDomanda(), 
				progSoggcorr,
				soggetto.getIdUtenteAgg()
		});
		
		if(soggetto.getProgSoggProgetto()!=null) {
			String insertSoggProg = "insert into PBANDI_R_SOGG_PROG_SOGG_CORREL("
					+ "PROGR_SOGGETTO_PROGETTO, "
					+ "progr_soggetti_correlati, "
					+ "id_utente_ins, "
					+ "dt_inserimento) values(?,?,?,sysdate)";
			getJdbcTemplate().update(insertSoggProg, new Object[] {
					soggetto.getProgSoggProgetto(), 
					progSoggcorr,
					soggetto.getIdUtenteAgg()
			});
		}
		
	}


	private BigDecimal checkProgetto2(String idSoggetto, BigDecimal veroIdDomanda) {

		BigDecimal idProgettoSogg = null;
		try {
			String checkProgetto = " SELECT prsp.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON PRSP .PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "WHERE prsp .ID_SOGGETTO ="+idSoggetto+"\r\n"
					+ "AND prsd.ID_DOMANDA ="+veroIdDomanda+"\r\n"
					+ " AND prsd.PROGR_SOGGETTO_DOMANDA IN ( SELECT\r\n"
					+ "        MAX(prsd.PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    FROM\r\n"
					+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    GROUP BY\r\n"
					+ "        prsd.ID_SOGGETTO)"; 
			
			idProgettoSogg = getJdbcTemplate().queryForObject(checkProgetto, BigDecimal.class); 
	
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::checkProgetto2] Exception e="+e.getMessage());
		}
		return idProgettoSogg;
	}


	private AttivitaDTO  checkRuoloSoggettoCorrelatoEG(String idSoggetto, Long idTipoSoggCorr, String ruolo) {
	
		
		AttivitaDTO att =  new AttivitaDTO(); 
		try {
			String getDescTipo="     SELECT\r\n"
					+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO, prsc.PROGR_SOGGETTI_CORRELATI, pdtsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTI_CORRELATI prsc\r\n"
					+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON PRSC.ID_TIPO_SOGGETTO_CORRELATO = pdtsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
					+ "WHERE\r\n"
					+ "    ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ "    AND PROGR_SOGGETTI_CORRELATI IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(PROGR_SOGGETTI_CORRELATI) AS PROGR_SOGGETTI_CORRELATI\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_R_SOGGETTI_CORRELATI prsc2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ID_SOGGETTO\r\n"
					+ "    )";
			
			
			att = getJdbcTemplate().query(getDescTipo, new ResultSetExtractor<AttivitaDTO>() {
				
				@Override
				public AttivitaDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					AttivitaDTO anag = new AttivitaDTO();
					
					while(rs.next()) {
				
						anag.setDescAttivita(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						anag.setIdSoggProg(rs.getLong("PROGR_SOGGETTI_CORRELATI"));
						anag.setIdAttivita(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
						
					}
			return anag; 
				}
	   	});			
	
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::checkRuoloSoggettoCorrelatoEG]Exception e="+e.getMessage());
		}
		return att;
	}


	private boolean insertDatiAnagrafici(AnagraficaBeneficiarioVO anagDB,  BigDecimal idEnteGiuridico) {

		boolean result; 
		try {
			String dataCostituzione = null;
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			
			if(anagDB.getDtCostituzione()!=null) {
				dataCostituzione = dt.format(anagDB.getDtCostituzione());
			} 
			String insertEG ="insert into PBANDI_T_ENTE_GIURIDICO ("
					+ "ID_SOGGETTO,\r\n"
					+ "DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
					+ "DT_COSTITUZIONE,\r\n"
					+ "CAPITALE_SOCIALE,\r\n"
					+ "ID_ATTIVITA_UIC,\r\n"
					+ "ID_FORMA_GIURIDICA,\r\n"
					+ "ID_CLASSIFICAZIONE_ENTE,\r\n"
					+ "ID_DIMENSIONE_IMPRESA,\r\n"
					+ "ID_ENTE_GIURIDICO,\r\n"
					+ "DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "FLAG_PUBBLICO_PRIVATO,\r\n"
					+ "COD_UNI_IPA,\r\n"
					+ "ID_STATO_ATTIVITA,\r\n"
					+ "DT_VALUTAZIONE_ESITO,\r\n"
					+ "PERIODO_SCADENZA_ESERCIZIO,\r\n"
					+ "DT_INIZIO_ATTIVITA_ESITO,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "FLAG_RATING_LEGALITA"
					+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
			
			getJdbcTemplate().update(insertEG, new Object[] {
					anagDB.getIdSoggetto(), 
					anagDB.getRagSoc(), 
					(dataCostituzione!=null && dataCostituzione.trim().length()>0)? Date.valueOf(dataCostituzione):null, 
					anagDB.getCapSociale(), 
					(anagDB.getIdAttIuc()>0)?anagDB.getIdAttIuc():null, 
					(anagDB.getIdFormaGiuridica()>0)?anagDB.getIdFormaGiuridica():null, 
					(anagDB.getIdClassEnte()>0)?anagDB.getIdClassEnte():null, 
					(anagDB.getIdDimensioneImpresa()>0)?anagDB.getIdDimensioneImpresa():null, 
					idEnteGiuridico,
					(anagDB.getDtUltimoEseChiuso()!=null)? anagDB.getDtUltimoEseChiuso():null, 
					anagDB.getIdUtenteIns(), 
					(anagDB.getFlagPubblicoPrivato()!=null && anagDB.getFlagPubblicoPrivato().longValue()>0)?anagDB.getFlagPubblicoPrivato(): null, 
					anagDB.getCodUniIpa(), 
					(anagDB.getIdStatoAtt()>0)?anagDB.getIdStatoAtt():null, 
					(anagDB.getDtValEsito()!=null)? anagDB.getDtValEsito(): null, 
					anagDB.getPeriodoScadEse(), 
					(anagDB.getDtInizioAttEsito()!=null)?anagDB.getDtInizioAttEsito():null,
					(anagDB.getFlagRatingLeg().trim().equals("S"))?anagDB.getFlagRatingLeg().trim():null
				    
			});
			
			result = true; 
			
		} catch (Exception e) {
			result  = false;
			LOG.error("[AnagraficaBeneficiarioDAOImpl::insertDatiAnagrafici] Exception e="+e.getMessage());
		}
		
	return result; 	
	}


	@Override
	public List<FormaGiuridicaDTO> elencoFormeGiuridiche() {
		
		
		List<FormaGiuridicaDTO> elenco= new ArrayList<FormaGiuridicaDTO>(); 
		try {
			String get =" SELECT pdfg.ID_FORMA_GIURIDICA , pdfg.DESC_FORMA_GIURIDICA \r\n"
					+ "    FROM PBANDI_D_FORMA_GIURIDICA pdfg \r\n"
					+ "    ORDER BY DESC_FORMA_GIURIDICA "; 
			
			elenco= getJdbcTemplate().query(get, new FormaGiuridicaDTORowMapper()); 
			
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::elencoFormeGiuridiche] Exception e="+e.getMessage());
		}
		
		return elenco;
	}


	@Override
	public Boolean modificaSoggcorrPF(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String idDomanda) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::modificaSoggcorrPF]";
		LOG.info(prf + " BEGIN");
		Boolean result=true; 
		
		try {
			AnagraficaBeneficiarioPfVO anagDB = new AnagraficaBeneficiarioPfVO();
			BigDecimal idPersFisica = null; 
			boolean isInsertPersFisica = false;
			// recupero i dati della persona fisica della personna fisica
			String query = " SELECT\r\n"
					+ "    cognome,\r\n"
					+ "    nome,\r\n"
					+ "    sesso,\r\n"
					+ "    dt_nascita,\r\n"
					+ "    id_cittadinanza,\r\n"
					+ "    id_titolo_studio,\r\n"
					+ "    id_occupazione,\r\n"
					+ "    id_persona_fisica,\r\n"
					+ "    ID_COMUNE_ITALIANO_NASCITA ,\r\n"
					+ "    ID_COMUNE_ESTERO_NASCITA ,\r\n"
					+ "    pdc.ID_PROVINCIA \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_PERSONA_FISICA ptpf \r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = ptpf.ID_COMUNE_ITALIANO_NASCITA \r\n"
					+ "WHERE\r\n"
					+ "     ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "    AND ID_SOGGETTO ="+idSoggetto;
			
			anagDB = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>(){

				@Override
				public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO(); 
					while(rs.next()) {
						anag.setCognome(rs.getString("cognome"));
						anag.setNome(rs.getString("nome"));
						anag.setSesso(rs.getString("sesso"));
						anag.setIdCittadinanza(rs.getLong("id_cittadinanza"));
						anag.setIdTitoloStudio(rs.getLong("id_titolo_studio"));
						anag.setIdOccupazione(rs.getLong("id_occupazione"));
						anag.setIdPersonaFisica(rs.getLong("id_persona_fisica"));
						anag.setDataDiNascita(rs.getDate("dt_nascita"));
						if(rs.getString("ID_COMUNE_ITALIANO_NASCITA") !=null) {
							anag.setIdComuneDiNascita(rs.getLong("ID_COMUNE_ITALIANO_NASCITA"));
							anag.setIdProvinciaDiNascita(rs.getLong("ID_PROVINCIA"));
						} else {
							anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO_NASCITA"));
						}
					}
					return anag;
				}
				
			});
					
			
			if(soggetto.isDatiAnagrafici()==true) {
				// set il codice fiscale dentro la tabella PTSOGETTO; 
				String updateCFisc = "update PBANDI_T_SOGGETTO set"
						+ " id_utente_agg="+ soggetto.getIdUtenteAgg()
						+ ", dt_aggiornamento=trunc(sysdate)"
						+ " where id_soggetto="+ idSoggetto;
				
				getJdbcTemplate().update(updateCFisc); 
				
				// set dati anagrafici 
				// se esiste un occorenza personna fisica allora aggiorno se no allora inserisco
				if(checkDatiAnagPf(soggetto, anagDB)==true) {
				// non faccio nulla
				
						String updatePRSdom="update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " id_persona_fisica=" + soggetto.getIdPersonaFisica()
								+ ", dt_aggiornamento=sysdate" 
								+ ", id_utente_agg="+soggetto.getIdUtenteAgg()
								+ " where id_soggetto="+ idSoggetto;
						getJdbcTemplate().update(updatePRSdom); 
					
				} else {
					
					 idPersFisica= insertPersonaFisica(soggetto, anagDB, idSoggetto); 
					 isInsertPersFisica = true;
			
				}
			}
				
				// nel caso di modifica di ruoli e quota partecipazione 
				// contollo se questo soggetto ha un id_soggetti_correlati
				AttivitaDTO soggCorrRuolo = new AttivitaDTO(); 
				soggCorrRuolo= checkRuoloSoggettoCorrelatoEG(idSoggetto, soggetto.getIdTipoSoggCorr(), soggetto.getRuolo()); 
				//  siccome non si può modificare il ruolo di soggetto correlato aggiorno solo la quota di partecipazione
				// faccio l'update dentro la tabella PBANDI_R_SOGGETTI_CORRELATI
				String update = "update PBANDI_R_SOGGETTI_CORRELATI set \r\n"
						+ "  id_utente_agg= ?, \r\n" 
						+ "  QUOTA_PARTECIPAZIONE= ?\r\n"
						+"  where PROGR_SOGGETTI_CORRELATI=?";
				
				getJdbcTemplate().update(update, new Object[] {
						soggetto.getIdUtenteAgg(), soggetto.getQuotaPartecipazione(), soggetto.getProgrSoggCorr()
				}); 
				
//			if(!soggetto.isQuota()) {
//				
//			} else {
//				// inserisco un nuovo record dentro la tabella PBANDI_R_SOGGETTI_CORRELATI
//				insertSoggettoCorrelatoPF(soggetto,idSoggetto); 					
//			}
			

			
			
			// update residenza persona fisica
			if(soggetto.isSedeLegale()==true) { 
				if(checkMatchCompletoIndirizzo(soggetto.getIdComunePF(), soggetto.getIdNazionePF(), soggetto.getIdProvinciaPF(),
						soggetto.getIdRegionePF(),idSoggetto, 1, soggetto.getIdDomanda())==true) {
					String updateIndirizzo="update PBANDI_T_INDIRIZZO  set"
							+ " id_utente_agg=?"
							+ " where PROGR_SOGGETTO_DOMANDA=?";
					getJdbcTemplate().update(updateIndirizzo, new Object[] {soggetto.getIdUtenteAgg(), soggetto.getProgSoggDomanda()}); 
		
				} else {
					BigDecimal idIndirizzo=	insertIndirizzoPF(soggetto, idSoggetto); 
								
						String updateIndirizzo = "update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " ID_INDIRIZZO_PERSONA_FISICA =?"
								+ ", ID_UTENTE_AGG=?"
								+ " where PROGR_SOGGETTO_DOMANDA=?" ;
						getJdbcTemplate().update(updateIndirizzo,new Object[] {
								idIndirizzo, soggetto.getIdUtenteAgg(), soggetto.getProgSoggDomanda()
						});
						
						if(soggetto.getProgSoggProgetto()!=null) {
							String update2="update PBANDI_R_SOGGETTO_PROGETTO set"
									+ " ID_INDIRIZZO_PERSONA_FISICA=?\r\n"
									+ " , ID_UTENTE_AGG=?\r\n"
									+ " WHERE PROGR_SOGGETTO_PROGETTO=?";
							getJdbcTemplate().update(update2,new Object[] {
									idIndirizzo, soggetto.getIdUtenteAgg(),soggetto.getProgSoggProgetto()
							});
						} 
				}
			}
			
		} catch (Exception e) {
			result =false; 
			LOG.error( prf + "Exception e="+e.getMessage());
		}
		
		return result;
	}


	private boolean checkDatiAnagPf(AnagraficaBeneficiarioPfVO soggetto, AnagraficaBeneficiarioPfVO anagDB) {
	
		if(soggetto.getIdProvinciaDiNascita() != anagDB.getIdProvinciaDiNascita())
			return false;
		
		if(anagDB.getIdComuneEsteraNascita()!=null && anagDB.getIdComuneEsteraNascita()>0) {
			if(anagDB.getIdComuneEsteraNascita()!=soggetto.getIdComuneDiNascita())
				return false; 
		} else {
			if(anagDB.getIdComuneDiNascita()!=soggetto.getIdComuneDiNascita())
				return false;
		}
		if(soggetto.getNome()!= anagDB.getNome())
			return false; 
		if(soggetto.getCognome() != anagDB.getCognome())
			return false; 	
		if(soggetto.getDataDiNascita() != anagDB.getDataDiNascita())
			return false;  
		
			
		return true;
	}


	private boolean checkMatchCompletoIndirizzo(Long idComune, Long idNazione, Long idProvincia, Long idRegione,
			String idSoggetto, int pf, String idDomanda) {
		 
		String select;
		if(pf==1) {
			
		 select ="  	SELECT\r\n"
		 		+ "    pti.id_indirizzo,\r\n"
		 		+ "    pti.ID_NAZIONE,\r\n"
		 		+ "    pti.ID_COMUNE,\r\n"
		 		+ "    PTI.ID_PROVINCIA,\r\n"
		 		+ "    PTI.ID_REGIONE,\r\n"
		 		+ "    pti.ID_COMUNE_ESTERO\r\n"
		 		+ "FROM\r\n"
		 		+ "    PBANDI_T_INDIRIZZO pti\r\n"
		 		+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_INDIRIZZO_PERSONA_FISICA = pti.ID_INDIRIZZO\r\n"
		 		+ "WHERE\r\n"
		 		+ "    prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
		 		+ "    AND prsd.PROGR_SOGGETTO_DOMANDA IN (\r\n"
		 		+ "        SELECT\r\n"
		 		+ "            MAX(PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA\r\n"
		 		+ "        FROM\r\n"
		 		+ "            PBANDI_R_SOGGETTO_DOMANDA\r\n"
		 		+ "        where\r\n"
		 		+ "            id_domanda ="+idDomanda+"\r\n"
		 		+ "            and prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
		 		+ "        GROUP BY\r\n"
		 		+ "            ID_SOGGETTO\r\n"
		 		+ "    )"; 
		} else {
			select = " SELECT\r\n"
					+ "    pti.id_indirizzo,\r\n"
					+ "    pti.ID_NAZIONE,\r\n"
					+ "    pti.ID_COMUNE,\r\n"
					+ "    PTI.ID_PROVINCIA,\r\n"
					+ "    PTI.ID_REGIONE,\r\n"
					+ "    pti.ID_COMUNE_ESTERO,\r\n"
					+ "    prsd.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_INDIRIZZO pti\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "WHERE\r\n"
					+ "    prsd.ID_SOGGETTO ="+idSoggetto+"\r\n"
					+ "    AND prsd.PROGR_SOGGETTO_DOMANDA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_R_SOGGETTO_DOMANDA\r\n"
					+ "        where\r\n"
					+ "            id_domanda = "+idDomanda+"\r\n"
					+ "            and prsd.ID_TIPO_ANAGRAFICA <> 1 \r\n"
					+ "        GROUP BY\r\n"
					+ "            ID_SOGGETTO\r\n"
					+ "    )"; 
		}
		
		IndirizzoVO indirizzo = new IndirizzoVO(); 
		
		indirizzo = getJdbcTemplate().query(select, new ResultSetExtractor<IndirizzoVO>() {

			@Override
			public IndirizzoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				IndirizzoVO ind = new IndirizzoVO(); 
				while (rs.next()) {
						ind.setIdIndirizzo(rs.getLong("id_indirizzo"));
						ind.setIdComuneEstero(rs.getLong("ID_COMUNE_ESTERO"));
						ind.setIdComune(rs.getLong("ID_COMUNE"));
						ind.setIdRegione(rs.getLong("ID_REGIONE"));
						ind.setIdProvincia(rs.getLong("ID_PROVINCIA"));
						ind.setIdNazione(rs.getLong("ID_NAZIONE"));
				}
				return ind;
			}
		}); 
				
		// verifico se è un  indirizzo estero quindi con comune estera e se è uguale alla comune passata in questa funzione
		if(indirizzo.getIdComuneEstero()!=null && indirizzo.getIdComuneEstero()==idComune) {
			if(idNazione == indirizzo.getIdNazione()) {
				return true; 
			} else {
				return false; 
			}
		} 
		
		if(indirizzo.getIdComune()==idComune  && indirizzo.getIdRegione()==idRegione && indirizzo.getIdProvincia()== idProvincia
				&& indirizzo.getIdNazione()==idNazione) {
			return true; 
		}
		return false;
	}


	private BigDecimal insertIndirizzoPF(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto) {
		// recupero l'id_indirizzo 
		String getId = "SELECT SEQ_PBANDI_T_INDIRIZZO.nextval FROM dual";	
		BigDecimal idIndirizzo=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
		
		
		StringBuilder insert = new StringBuilder();
		
		insert.append("insert into PBANDI_T_INDIRIZZO("
				+ "id_indirizzo, "
				+ "desc_indirizzo,");
		if(soggetto.getIdNazionePF()==118) {
			
			insert.append("cap, "
					+ "id_nazione, "
					+ "id_comune, "
					+ "id_regione, "
					+ "id_provincia,"
					+ "id_utente_ins, "
					+ "dt_inizio_validita) values(?,?,?,?,?,?,?,?,sysdate)"); 
		getJdbcTemplate().update(insert.toString(), new Object[] {
			idIndirizzo,
			soggetto.getIndirizzoPF(), 
			soggetto.getCapPF(), 
			soggetto.getIdNazionePF(), 
			soggetto.getIdComunePF(), 
			soggetto.getIdRegionePF(), 
			soggetto.getIdProvinciaPF(), 
			soggetto.getIdUtenteAgg()
		});

		} else {
			insert.append("id_comune_estero,"
					+ "id_nazione, "
					+ "id_utente_ins, "
					+ "ddt_inizio_validita) values(?,?,?,?,?,sysdate)");
			getJdbcTemplate().update(insert.toString(), new Object[] {
					idIndirizzo,
					soggetto.getIndirizzoPF(), 
					soggetto.getIdNazionePF(), 
					soggetto.getIdComunePF(), 
					soggetto.getIdUtenteAgg()
				});
		}
		return idIndirizzo;
	}


	private String getProgDomandaSede(BigDecimal progSoggDomanda) {
		
		String get="    SELECT max( prsds.PROGR_SOGGETTO_DOMANDA_SEDE ) AS PROGR_SOGGETTO_DOMANDA_SEDE \r\n"
				+ "    FROM PBANDI_R_SOGGETTO_DOMANDA prsd LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
				+ "   	ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "   	WHERE prsds.PROGR_SOGGETTO_DOMANDA="+progSoggDomanda; 
		String progDomandaSede = getJdbcTemplate().queryForObject(get, String.class);
		return progDomandaSede;
	}


	private BigDecimal insertPersonaFisica(AnagraficaBeneficiarioPfVO soggetto, AnagraficaBeneficiarioPfVO anagDB, String idSoggetto) {
		
		String getId = "SELECT SEQ_PBANDI_T_PERSONA_FISICA.nextval FROM dual";	
		BigDecimal IdPersonaFisica =  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
		
		StringBuilder insert = new StringBuilder();
		insert.append("insert into PBANDI_T_PERSONA_FISICA (\r\n"
				+ "cognome, \r\n"
				+ "nome, \r\n"
				+ "dt_nascita, \r\n"
				+ "	sesso, \r\n"
				+ "id_persona_fisica,\r\n"
				+ "dt_inizio_validita, ");
		if(soggetto.getIdNazioneDiNascita()==118) {
			insert.append(" id_comune_italiano_nascita,\r\n"
					+ "id_nazione_nascita,\r\n"
					+"id_utente_ins ,"); 
		} else {
			insert.append(" id_comune_estero_nascita,\r\n"
					+ " id_nazione_nascita,\r\n"
					+" id_utente_ins,");
		}
		insert.append(" ID_CITTADINANZA, "
				+ " id_titolo_studio, "
				+ " id_occupazione, id_soggetto)values(?,?,?,?,?,sysdate,?,?,?,?,?,?,?)");	
		getJdbcTemplate().update(insert.toString(), new Object[] {
				soggetto.getCognome(), 
				soggetto.getNome(),
				soggetto.getDataDiNascita(), 
				anagDB.getSesso(), 
				IdPersonaFisica,
				soggetto.getIdComuneDiNascita(), 
				soggetto.getIdNazioneDiNascita(), 
				soggetto.getIdUtenteAgg(),
				(anagDB.getIdCittadinanza()>0)?anagDB.getIdCittadinanza():null,
				(anagDB.getIdTitoloStudio()>0)?anagDB.getIdTitoloStudio():null,
				(anagDB.getIdOccupazione()>0)?anagDB.getIdOccupazione():null, 
				idSoggetto,
		});
		
		if(soggetto.getProgSoggDomanda()!=null) {
			String updatePRSdom="update PBANDI_R_SOGGETTO_DOMANDA set "
					+ " id_persona_fisica=" + IdPersonaFisica
					+ ", dt_aggiornamento=sysdate" 
					+ ", id_utente_agg="+soggetto.getIdUtenteAgg()
					+ " where id_soggetto="+ idSoggetto;
			getJdbcTemplate().update(updatePRSdom); 
		}
		return IdPersonaFisica;
	}

	private void insertSoggettoCorrelatoPF(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto) {

		String getProgSoggCorrId = "SELECT SEQ_PBANDI_R_SOGG_CORRELATI.nextval FROM dual";	
		BigDecimal progSoggcorr =  getJdbcTemplate().queryForObject(getProgSoggCorrId, BigDecimal.class);
		
		String insertSoggCorr ="insert into PBANDI_R_SOGGETTI_CORRELATI ("
				+ " ID_TIPO_SOGGETTO_CORRELATO, "
				+ "ID_UTENTE_INS, "
				+ "QUOTA_PARTECIPAZIONE, "
				+ "ID_SOGGETTO,"
				+ "ID_SOGGETTO_ENTE_GIURIDICO, "
				+ "PROGR_SOGGETTI_CORRELATI, "
				+ "	DT_INIZIO_VALIDITA) values(?,?,?,?,?,?,  trunc(sysdate))";
		
		getJdbcTemplate().update(insertSoggCorr, new Object[] {
				soggetto.getIdTipoSoggCorr(), 
				soggetto.getIdUtenteAgg(),
				soggetto.getQuotaPartecipazione(),
				idSoggetto,
				soggetto.getIdSoggettoEnteGiuridico(),
				progSoggcorr
				
		});
		
		// insert into PBANDI_R_SOGG_DOM_SOGG_CORREL il nuovo soggetto correlato
		String insertSoggDom = "insert into PBANDI_R_SOGG_DOM_SOGG_CORREL("
				+ "PROGR_SOGGETTO_DOMANDA, "
				+ "PROGR_SOGGETTI_CORRELATI, "
				+ "id_utente_ins, "
				+ "dt_inserimento) values(?,?,?,trunc(sysdate))";
		getJdbcTemplate().update(insertSoggDom, new Object[] {
				soggetto.getProgSoggDomanda(), 
				progSoggcorr,
				soggetto.getIdUtenteAgg()
		});
			
	}


//	@Override
//	public List<AttivitaPrevalenteDTO> getElencoAttivita(String descAttivita) {
//		
//		
//		List<AttivitaPrevalenteDTO> elenco = new ArrayList<AttivitaPrevalenteDTO>(); 
//		
//		try {
//			String query = " SELECT ID_ATTIVITA_ATECO , DESC_ATECO \r\n"
//					+ "   FROM PBANDI_D_ATTIVITA_ATECO pdaa \r\n"
//					+ "   WHERE  upper(DESC_ATECO) LIKE upper(('%"+descAttivita+"%'))\r\n"
//					+ "   AND ROWNUM<=100\r\n"
//					+ "   ORDER BY COD_ATECO " ; 
//			
//			
//		RowMapper<AttivitaPrevalenteDTO> lista = new RowMapper<AttivitaPrevalenteDTO>() {
//				
//				@Override
//				public AttivitaPrevalenteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//					AttivitaPrevalenteDTO at = new AttivitaPrevalenteDTO();				
//					return at;
//				}
//			};
//			
//		} catch (Exception e) {
//		}
//		
//		return elenco;
//	}


	@Override
	public List<RuoloDTO> getElencoRuoloIndipendente() {
		
		List<RuoloDTO> elenco = new ArrayList<RuoloDTO>(); 
		
		try {
			String sql = "SELECT\r\n"
					+ "    DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
					+ "    ID_TIPO_SOGGETTO_CORRELATO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_D_TIPO_SOGG_CORRELATO pdtsc\r\n"
					+ "WHERE\r\n"
					+ "    PDTSC.FLAG_INDIPENDENTE = 'S'\r\n"
					+ "ORDER BY\r\n"
					+ "    DESC_TIPO_SOGGETTO_CORRELATO "; 
			
			RowMapper<RuoloDTO> lista = new RowMapper<RuoloDTO>() {

				@Override
				public RuoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					RuoloDTO ruolo = new RuoloDTO();
					ruolo.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
					ruolo.setDescTipoSoggCorr(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
					return ruolo;
				}
			};
			
			elenco = getJdbcTemplate().query(sql, lista);
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::getElencoRuoloIndipendente] Exception e="+e.getMessage());
		}
		
		return elenco;
	}


	@Override
	public List<SezioneSpecialeDTO> getElencoSezioni() {
		
		
		List<SezioneSpecialeDTO> elenco = new ArrayList<SezioneSpecialeDTO>();
		
		try {
			String query = " SELECT pdss.ID_SEZIONE_SPECIALE , DESC_SEZIONE_SPECIALE \r\n"
					+ "    FROM PBANDI_D_SEZIONE_SPECIALE pdss \r\n"
					+ "    ORDER BY DESC_SEZIONE_SPECIALE ";
			
			RowMapper<SezioneSpecialeDTO> lista = new RowMapper<SezioneSpecialeDTO>() {
				
				@Override
				public SezioneSpecialeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					SezioneSpecialeDTO ss = new SezioneSpecialeDTO();
					
					ss.setDescSezioneSpeciale(rs.getString("DESC_SEZIONE_SPECIALE"));
					ss.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
					
					return ss;
				}
			};
			
			elenco = getJdbcTemplate().query(query, lista); 
			
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::getElencoSezioni] Exception e="+e.getMessage());
		}
		
		return elenco;
	}


	@Override
	public List<AtecoDTO> getElencoAteco(String idAttivitaAteco) {
		
		
		List<AtecoDTO> elenco = new ArrayList<AtecoDTO>();
		try {
			
			String query= " SELECT ID_ATTIVITA_ATECO , COD_ATECO, DESC_ATECO \r\n"
					+ "   FROM PBANDI_D_ATTIVITA_ATECO pdaa \r\n"
					+ "   WHERE COD_ATECO LIKE ('%"+idAttivitaAteco+"%')\r\n"
					+ "   AND ROWNUM<=100\r\n"
					+ "   ORDER BY COD_ATECO ";
			
			RowMapper<AtecoDTO> lista = new RowMapper<AtecoDTO>() {
				
				@Override
				public AtecoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AtecoDTO at = new AtecoDTO();
					at.setIdAttAteco(rs.getLong("ID_ATTIVITA_ATECO"));
					at.setCodAteco(rs.getString("COD_ATECO"));
					at.setDescAttivitaAteco(rs.getString("DESC_ATECO"));
					return at;
				}
			};
			
			elenco= getJdbcTemplate().query(query, lista); 
		} catch (Exception e) {
			LOG.error("[AnagraficaBeneficiarioDAOImpl::getElencoAteco] Exception e="+e.getMessage());
		}
		return elenco;
	}


	@Override
	public DatiAreaCreditiVO getDatiAreaCrediti(String idSoggetto) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getDatiAreaCrediti]";
		LOG.info(prf + " BEGIN");
		
		DatiAreaCreditiVO dati = new DatiAreaCreditiVO();
		
		try {
			String datiAreaCred = "SELECT\r\n"
					+ "    DISTINCT PTSSA.ID_SOGGETTO,\r\n"
					+ "    PTSSA.DT_INIZIO_VALIDITA,\r\n"
					+ "    PTSSA.ID_STATO_AZIENDA,\r\n"
					+ "    pdsa.DESC_STATO_AZIENDA,\r\n"
					+ "    PTSSA.ID_SOGGETTO_STATO_AZIENDA,\r\n"
					+ "    ptsr.ID_RATING,\r\n"
					+ "    ptsr.ID_SOGGETTO_RATING,\r\n"
					+ "    ptsr.DT_CLASSIFICAZIONE,\r\n"
					+ "    pdr.ID_PROVIDER,\r\n"
					+ "    pdr.CODICE_RATING,\r\n"
					+ "    pdr.DESC_RATING,\r\n"
					+ "    pdp.DESC_PROVIDER\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SOGG_STATO_AZIENDA ptssa\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_AZIENDA pdsa ON PTSSA.ID_STATO_AZIENDA = pdsa.ID_STATO_AZIENDA\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO_RATING ptsr ON PTSSA.ID_SOGGETTO = ptsr.ID_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_D_RATING pdr ON pdr.ID_RATING = ptsr.ID_RATING\r\n"
					+ "    LEFT JOIN PBANDI_D_PROVIDER pdp ON pdp.ID_PROVIDER = pdr.ID_PROVIDER\r\n"
					+ "WHERE\r\n"
					+ "    PTSSA.DT_FINE_VALIDITA IS NULL\r\n"
					+ "    AND ptsr.DT_FINE_VALIDITA IS NULL\r\n"
					+ "    AND PTSSA.ID_SOGGETTO ="+idSoggetto;
			
			
			dati = getJdbcTemplate().query(datiAreaCred, new ResultSetExtractor<DatiAreaCreditiVO>() {

				@Override
				public DatiAreaCreditiVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					DatiAreaCreditiVO dati = new DatiAreaCreditiVO();
					
					while(rs.next()) {
						dati.setIdProvider(rs.getLong("ID_PROVIDER"));
						dati.setCodiceRating(rs.getString("CODICE_RATING"));
						dati.setDataClassificazione(rs.getDate("DT_CLASSIFICAZIONE"));
						dati.setDescProvider(rs.getString("DESC_PROVIDER"));
						dati.setDescRating(rs.getString("DESC_RATING"));
						dati.setDescStatoAzienda(rs.getString("DESC_STATO_AZIENDA"));
						dati.setIdStatoAzienda(rs.getLong("ID_STATO_AZIENDA"));
						dati.setIdSogetto(rs.getString("ID_SOGGETTO"));
						dati.setIdSoggStatoAzienda(rs.getString("ID_SOGGETTO_STATO_AZIENDA"));
						dati.setDataInizioVal(rs.getDate("DT_INIZIO_VALIDITA"));
						dati.setIdSoggettoRating(rs.getString("ID_SOGGETTO_RATING"));
						dati.setIdRating(rs.getLong("ID_RATING"));
					}
					
					
					return dati;
				}
				
			});
			
			
			
		} catch (Exception e) {
			LOG.error(prf +" Exception e="+e.getMessage());
			}
		
		
		return dati;
	}

//// BLOCCHI
	@Override
	public List<BloccoVO> getElencoBlocchiSoggettoDomanda(String idSoggetto , String numeroDomnanda,  boolean flagErogazione) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getElencoBlocchi]";
		LOG.info(prf + " BEGIN");
//		String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
//				+ "    LEFT JOIN pbandi_t_domanda PTD ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
//				+ "    where numero_domanda='"+idDomanda+"'\r\n"
//				+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
//					+ "  AND ROWNUM =1 \r\n"; 	
//		Long veroIdDomanda = getJdbcTemplate().queryForObject(getVeroIddomanda, Long.class); 
		List<BloccoVO> listaBlocchi = new ArrayList<BloccoVO>(); 
		
		try {
			String get ="SELECT\r\n"
					+ "    PTSDB.ID_SOGGETTO_DOMANDA_BLOCCO,\r\n"
					+ "    pdcb.ID_CAUSALE_BLOCCO,\r\n"
					+ "    pdcb.DESC_CAUSALE_BLOCCO,\r\n"
					+ "    TRUNC(PTSDB.DT_INSERIMENTO_BLOCCO) as DT_INSERIMENTO_BLOCCO,\r\n"
					+ "    prsd.PROGR_SOGGETTO_DOMANDA, prsd.ID_SOGGETTO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON PTSDB.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON PTSDB.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO\r\n"
					+ "	   LEFT JOIN pbandi_t_domanda PTD ON ptd.id_domanda = prsd.ID_DOMANDA "
					+ "WHERE\r\n"
					+ "    prsd.ID_SOGGETTO =?\r\n"
					+ "	   and ptd.numero_domanda=?\r\n"
					+ "    AND PTSDB .DT_INSERIMENTO_SBLOCCO IS NULL \r\n"
					+ "AND pdcb.flag_blocco_anagrafico ='N'\r\n";
			if(flagErogazione) {				
				get += "    AND pdcb.FLAG_EROGAZIONE = 'S'\r\n";
			}
				get	+= "ORDER BY\r\n"
					+ "    DT_INSERIMENTO_BLOCCO desc"; 
			
			
			RowMapper<BloccoVO> lista= new RowMapper<BloccoVO>() {
				@Override
				public BloccoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BloccoVO blocco =  new BloccoVO(); 
					blocco.setIdBlocco(rs.getLong("ID_SOGGETTO_DOMANDA_BLOCCO"));
					blocco.setDescCausaleBlocco(rs.getString("DESC_CAUSALE_BLOCCO"));
					blocco.setDataInserimentoBlocco(rs.getDate("DT_INSERIMENTO_BLOCCO")); 
					blocco.setIdProgSoggDomanda(rs.getBigDecimal("PROGR_SOGGETTO_DOMANDA"));
					blocco.setIdCausaleBlocco(rs.getLong("ID_CAUSALE_BLOCCO"));
					blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					return blocco;
				}		
			};
			listaBlocchi = getJdbcTemplate().query(get, lista, new Object[] {
					idSoggetto, 
					numeroDomnanda
			}); 
		} catch (Exception e) {
			LOG.error(prf + " Error while trying to read PBANDI_T_SOGG_DOMANDA_BLOCCO");
		}
		return listaBlocchi;
	}


	@Override															// in realta questo è il numero domanda
	public Boolean insertBloccoDomanda(BloccoVO blocco, String idSoggetto, String idDomanda) {
		
		Boolean result = true; 
		try {
			String dtInsBlocco = null, dtInsSblocco = null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			dtInsBlocco = dt.format(Date.valueOf(LocalDate.now()));
			dtInsSblocco = dt1.format(Date.valueOf(LocalDate.now()));
		boolean isBlockInsered = false; 
		long idSoggDomandaBlocco = (long)getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_SOGG_DOM_BLOCCO.nextval FROM dual", long.class); 

		BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate()
				.queryForObject("select id_domanda from pbandi_t_domanda where numero_domanda='"+idDomanda+"'\r\n", BigDecimal.class); 
		// recupero il progr_soggetto_domanda
			String progr= "SELECT MAX(PROGR_SOGGETTO_DOMANDA) \r\n"
				+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
				+ "WHERE ID_DOMANDA ="+veroIdDomanda+"\r\n"
				+ "  AND ID_SOGGETTO ="+idSoggetto+"\r\n"
				+ "  and  prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "    AND PRSD.ID_TIPO_BENEFICIARIO <> 4"; 
		String progrDomanda =getJdbcTemplate().queryForObject(progr,  String.class);
		
		
			// prima di inserire devo controllare se esiste già un blocco per di questo tipo per questo soggetto
			String getCountBlocchiInseriti ="	SELECT count (ptsdb.ID_SOGGETTO_DOMANDA_BLOCCO )\r\n"
					+ "	FROM PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "	LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd2 ON prsd2.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "	LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd2.ID_DOMANDA \r\n"
					+ "	WHERE ptsdb.ID_CAUSALE_BLOCCO =?\r\n"
					+ "	AND ptd.NUMERO_DOMANDA =?\r\n"
					+ "	AND prsd2.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	AND prsd2.ID_TIPO_ANAGRAFICA =1\r\n"
					+ "	AND ptsdb.PROGR_SOGGETTO_DOMANDA =?\r\n"
					+ "	AND ptsdb.DT_FINE_VALIDITA IS NULL \r\n"
					+ "	AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL "; 
			
			int countBlocchi=0; 
			try {
				countBlocchi =  getJdbcTemplate().queryForObject(getCountBlocchiInseriti, Integer.class, new Object[] 
							{	blocco.getIdCausaleBlocco() ,
								idDomanda,  
								progrDomanda
								});
			} catch (Exception e) {
				LOG.error("[AnagraficaBeneficiarioDAOImpl::insertBloccoDomanda] Exception " + e.getMessage());
			}
			
			
	 
			if(countBlocchi<1) {
				
			
				// inserisco il blocco su quella domanda per quel soggetto		
				String insert="insert into PBANDI_T_SOGG_DOMANDA_BLOCCO (ID_SOGGETTO_DOMANDA_BLOCCO, "
						+ "progr_soggetto_domanda, "
						+ "id_causale_blocco, "
						+ "dt_inserimento_blocco, "
						+ "id_utente_ins, "
						+ "dt_inizio_validita) values (?,?,?,sysdate,?, sysdate)"; 
				
				getJdbcTemplate().update(insert, new Object[] {
					idSoggDomandaBlocco	, 
					progrDomanda, 
					blocco.getIdCausaleBlocco(), 
					blocco.getIdUtente()
				});
				
				// Notifiche a pbandi che il soggetto è stato bloccato 
				Long idProgetto = getIdProgetto(progrDomanda); 
				// controllo se il progetto c'è
				if(idProgetto!=null) {						
					sistemaDiNotifiche(blocco.getIdSoggetto().longValue(), idProgetto, blocco.getIdUtente().longValue(), blocco.getIdCausaleBlocco(), true);
					// controllo se la causale del blocco abbia flag_revoca a S inserisco la proposa di revoca dentro.
					//recupero da db il flag:
					String flagRevoca=null; 
					flagRevoca = getJdbcTemplate().queryForObject("SELECT FLAG_REVOCA \r\n"
							+ "FROM pbandi_d_causale_blocco \r\n"
							+ "WHERE ID_CAUSALE_BLOCCO ="+ blocco.getIdCausaleBlocco(), String.class);
					if(flagRevoca!=null && flagRevoca.trim().equals("S")) {
						Long idPropostaRevoca= null;	
						try {						
							idPropostaRevoca = propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(), 
									idProgetto, (long)blocco.getIdCausaleBlocco(), null, Date.valueOf(LocalDate.now()), blocco.getIdUtente().longValue());
						} catch (Exception e) {
							LOG.error("errore inserimento proposta revoca");
						}
						if(idPropostaRevoca!=null)
						LOG.info("proposta revoca inserita bene!!");
						else LOG.info("errore inserimento proposta di revoca!!");
					}
		
				}
			} else {
				isBlockInsered = true; 
			}
			
		// CHIAMATA AL SERVIZIO DA VEDERE DOPO	
			String codiceFiscale= getJdbcTemplate().queryForObject("select CODICE_FISCALE_SOGGETTO from PBANDI_T_SOGGETTO pts  where id_soggetto ="+blocco.getIdSoggetto(), String.class);
			String descBlocco = getJdbcTemplate().queryForObject("SELECT DESC_CAUSALE_BLOCCO  FROM PBANDI_D_CAUSALE_BLOCCO pdcb WHERE ID_CAUSALE_BLOCCO ="+blocco.getIdCausaleBlocco(), String.class); 
		String numeroDomanda= null; 
		numeroDomanda = getNumericPart(idDomanda); 
		
		if(numeroDomanda!=null && !isBlockInsered) {		
			Client client = ClientBuilder.newBuilder().build(); 
			WebTarget target = client.target(url)
					.queryParam("numeroDomanda", numeroDomanda)
					//.queryParam("codiceFiscale",codiceFiscale) 
					.queryParam("descCausaleBlocco", descBlocco) 
					.queryParam("dtInsBlocco", dtInsBlocco); 
			// target.queryParam("dtInsSblocco", null); 
			
			Response resp = (Response) target.request().get();
			
			RicezioneBloccoSbloccoResponse dr = resp.readEntity(RicezioneBloccoSbloccoResponse.class);
			LOG.info("la risposta al servizio e': "+dr);
		} else {
			LOG.info("non faccio la chiamata siccome il numero domanda non è valido");
		}
		
		} catch (Exception e) {
			LOG.error("Exception while trying to insert into PBANDI_T_SOGG_DOMANDA_BLOCCO", e);
			result = false; 
		}
		
		return result;
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
	public List<AttivitaDTO> getListaCausaliDomanda(String idSoggetto, String idDomanda) {
		
		
		List<AttivitaDTO>listaCaus = new ArrayList<AttivitaDTO>();
		try {
			
			String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where numero_domanda='"+idDomanda+"'\r\n"
					+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ "    AND ROWNUM =1 \r\n";	
			BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda, BigDecimal.class); 
			
			String sql ="SELECT\r\n"
					+ "    PDCB.ID_CAUSALE_BLOCCO,\r\n"
					+ "    PDCB.DESC_CAUSALE_BLOCCO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_D_CAUSALE_BLOCCO pdcb\r\n"
					+ "WHERE\r\n"
					+ "    PDCB.FLAG_BLOCCO_ANAGRAFICO = 'N'\r\n"
					+ "    AND PDCB.ID_CAUSALE_BLOCCO NOT IN (\r\n"
					+ "        SELECT\r\n"
					+ "            ptsdb.ID_CAUSALE_BLOCCO\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "            LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "        WHERE\r\n"
					+ "            ptsdb.PROGR_SOGGETTO_DOMANDA =? \r\n"
					+ "            AND prsd.ID_SOGGETTO =?\r\n"
					+ "            AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL\r\n"
					+ "    )\r\n"
					+ " 		AND pdcb.DESC_MACROAREA != ('Stato credito')\r\n"
					+ "ORDER BY\r\n"
					+ "    DESC_CAUSALE_BLOCCO"; 
			
			RowMapper<AttivitaDTO> lista= new RowMapper<AttivitaDTO>() {
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO caus =  new AttivitaDTO(); 
					caus.setIdAttivita(rs.getLong("ID_CAUSALE_BLOCCO"));
					caus.setDescAttivita(rs.getString("DESC_CAUSALE_BLOCCO"));
					return caus;
				}		
			};
			listaCaus = getJdbcTemplate().query(sql, lista, new Object[] {
					veroIdDomanda, idSoggetto }); 
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_D_CAUSALE_BLOCCO", e);
		}

		
		return listaCaus;

	}


	@Override
	public boolean updateBloccoDomanda(BloccoVO blocco, String numeroDomanda) {

		boolean result ; 
		try {
			String dtInsBlocco = null, dtInsSblocco = null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			dtInsSblocco = dt1.format(Date.valueOf(LocalDate.now()));
			if(blocco.getDataInserimentoBlocco() != null) {
				dtInsBlocco = dt.format(blocco.getDataInserimentoBlocco());
			}
			result = true; 
			
			String update="update PBANDI_T_SOGG_DOMANDA_BLOCCO "
					+ " set dt_inserimento_sblocco=sysdate ,"
					+ " id_utente_agg ="+blocco.getIdUtente()
					+ ", dt_fine_validita=sysdate"
					+ " where ID_SOGGETTO_DOMANDA_BLOCCO="+blocco.getIdBlocco(); 
			
			getJdbcTemplate().update(update); 
			
			String progrSoggettoDomanda = getJdbcTemplate().queryForObject("  SELECT prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    FROM PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
					+ "    WHERE\r\n"
					+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
					+ "    AND prsd.DT_FINE_VALIDITA IS NULL \r\n"
					+ "    AND ptd.NUMERO_DOMANDA ='"+numeroDomanda+"'", String.class);
			
			// Notifiche a pbandi che il soggetto è stato bloccato 
			Long idProgetto = getIdProgetto(progrSoggettoDomanda); 
			// controllo se il progetto c'è
			if(idProgetto!=null) {						
				sistemaDiNotifiche(blocco.getIdSoggetto().longValue(), idProgetto, blocco.getIdUtente().longValue(), blocco.getIdCausaleBlocco(), false);
			}
			
			
			String codiceFiscale= getJdbcTemplate().queryForObject("select CODICE_FISCALE_SOGGETTO from PBANDI_T_SOGGETTO pts  where id_soggetto ="+blocco.getIdSoggetto(), String.class);
			String descBlocco = getJdbcTemplate().queryForObject("SELECT DESC_CAUSALE_BLOCCO  FROM PBANDI_D_CAUSALE_BLOCCO pdcb WHERE ID_CAUSALE_BLOCCO ="+blocco.getIdCausaleBlocco(), String.class); 
		// CHIAMATA AL SERVIZIO DA VEDERE DOPO
			// se la domanda inizia per FD allora  devo recuperare solo la parte del numero e passarla nel caso contrario non chiamo neanche il servizio
			// se il numero domanda inizia per un nu
			
			String parteNumerica= null; 
			parteNumerica = getNumericPart(numeroDomanda); 
			
			if(numeroDomanda!=null) {		
				Client client = ClientBuilder.newBuilder().build(); 
				WebTarget target = client.target(url)
				.queryParam("numeroDomanda", parteNumerica) 
				//.queryParam("codiceFiscale",codiceFiscale)
				.queryParam("descCausaleBlocco", descBlocco) 
				.queryParam("dtInsBlocco", dtInsBlocco) 
				.queryParam("dtInsSblocco", dtInsSblocco); 
				
				Response resp = (Response) target.request().get();
				
				RicezioneBloccoSbloccoResponse dr = resp.readEntity(RicezioneBloccoSbloccoResponse.class);
				LOG.info("la risposta al servizio e':: "+dr);
			} else {
				LOG.info("non faccio la chiamata siccome il numero domanda non è valido");
			}
				
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_D_CAUSALE_BLOCCO", e);
			result = false; 
		}
		return result;
	}
	
	
	@Override
	public List<BloccoVO> getStoricoBlocchiDomanda(Long idSoggetto, String numeroDomanda, boolean flagErogazione) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getStoricoBlocchiDomanda]";
		LOG.info(prf + " BEGIN");
		
		List<BloccoVO> listaBlocchi = new ArrayList<BloccoVO>();
		
		try {
			String sql =" SELECT\r\n"
					+ "    DISTINCT ptsdb.ID_SOGGETTO_DOMANDA_BLOCCO,\r\n"
					+ "    PRSD.ID_SOGGETTO,\r\n"
					+ "    pdcb.DESC_CAUSALE_BLOCCO,\r\n"
					+ "    TRUNC(PTSDB.DT_INSERIMENTO_BLOCCO) as DT_INSERIMENTO_BLOCCO,\r\n"
					+ "    ptsdb.dt_inserimento_sblocco,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptpf.NOME\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON PRSD.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON PRSD.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON ptsdb.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptsdb.ID_UTENTE_AGG\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "WHERE\r\n"
					+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND ptpf.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ptpf2.ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "    AND PTSDB.dt_fine_validita IS not NULL\r\n"
					+ "    AND ptsdb.DT_INSERIMENTO_SBLOCCO IS not NULL\r\n"
					+ "    and PRSD.ID_SOGGETTO =?\r\n"
					+ "    AND ptd.NUMERO_DOMANDA =?\r\n"
					+ "    AND pdcb.flag_blocco_anagrafico ='N'\r\n";
			
			if(flagErogazione) {				
				sql += "    AND pdcb.FLAG_EROGAZIONE = 'S'\r\n";
			}
				sql+= " ORDER BY\r\n"
					+ "    DT_INSERIMENTO_BLOCCO"; 
			
			RowMapper<BloccoVO> lista= new RowMapper<BloccoVO>() {
				@Override
				public BloccoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BloccoVO blocco =  new BloccoVO(); 
					blocco.setIdBlocco(rs.getLong("ID_SOGGETTO_DOMANDA_BLOCCO"));
					blocco.setDescCausaleBlocco(rs.getString("DESC_CAUSALE_BLOCCO"));
					blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					blocco.setDataInserimentoBlocco(rs.getDate("DT_INSERIMENTO_BLOCCO")); 
					blocco.setDataInserimentoSblocco(rs.getDate("dt_inserimento_sblocco"));
					blocco.setCognome(rs.getString("COGNOME"));
					blocco.setNome(rs.getString("NOME"));
					return blocco;
				}		
			};
			listaBlocchi = getJdbcTemplate().query(sql,new Object[] {
					idSoggetto, numeroDomanda}, lista); 
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}
		LOG.info(prf + " END");
		return listaBlocchi;

	}

	@Override
	public List<DettaglioImpresaVO> getDatiDimensioneSoggetto(Long idSoggetto) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getDatiDimensioneSoggetto]";
		LOG.info(prf + " BEGIN");	
		List<DettaglioImpresaVO> dati = new ArrayList<DettaglioImpresaVO>(); 
		try {
			String queryDimImp = "   WITH selec AS ( SELECT\r\n"
					+ "    pteg.DT_VALUTAZIONE_ESITO,\r\n"
					+ "    pddi.DESC_DIMENSIONE_IMPRESA, pteg.ID_SOGGETTO , pteg.ID_ENTE_GIURIDICO \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_ENTE_GIURIDICO pteg\r\n"
					+ "    LEFT JOIN PBANDI_D_DIMENSIONE_IMPRESA pddi ON pteg.ID_DIMENSIONE_IMPRESA = pddi.ID_DIMENSIONE_IMPRESA\r\n"
					+ "WHERE\r\n"
					+ "    pteg.ID_ENTE_GIURIDICO = "+idSoggetto+"\r\n" // in realta idSoggetto è l'idEnteGiuridico
					+ "    AND pteg.DT_VALUTAZIONE_ESITO IS NOT NULL \r\n"
					+ "ORDER BY\r\n"
					+ "    pteg.DT_VALUTAZIONE_ESITO DESC)\r\n"
					+ "    SELECT EXTRACT (YEAR FROM DT_VALUTAZIONE_ESITO) AS year FROM selec \r\n"
					+ "    WHERE rownum <2";
			int anno = getJdbcTemplate().queryForObject(queryDimImp, int.class); 
			
			int periodo1=anno-1;
			int periodo2=anno-2;
			int perdiodo3=anno-3;
				
			String dettagliDati="SELECT ptp.DESC_PERIODO ,  ptcdi.ULA , ptcdi.IMP_FATTURATO , ptcdi.TOT_BILANCIO_ANNUO \r\n"
					+ "	FROM PBANDI_T_PERIODO ptp \r\n"
					+ "	LEFT JOIN PBANDI_D_TIPO_PERIODO pdtp ON ptp.ID_TIPO_PERIODO = pdtp.ID_TIPO_PERIODO \r\n"
					+ "	LEFT JOIN PBANDI_T_CALC_DIM_IMPRESA ptcdi ON PTP .ID_PERIODO = ptcdi.ID_PERIODO AND ptcdi.DT_FINE_VALIDITA IS  NULL \r\n"
					+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_SOGGETTO = ptcdi.ID_SOGGETTO \r\n"
					+ "	WHERE \r\n"
					+ "	 pteg.ID_ENTE_GIURIDICO ="+idSoggetto+"\r\n"
					+ "	AND ptp.DESC_PERIODO IN ('"+periodo1+"', '"+periodo2+"', '"+perdiodo3+"')\r\n"
					+ "	AND pdtp.ID_TIPO_PERIODO =1 \r\n"
					+ "	ORDER BY ptp.DESC_PERIODO DESC "; 
			
			RowMapper<DettaglioImpresaVO> lista = new RowMapper<DettaglioImpresaVO>() {
				
				@Override
				public DettaglioImpresaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					DettaglioImpresaVO di = new  DettaglioImpresaVO(); 
					
					di.setAnnoRiferimento(rs.getString("DESC_PERIODO"));
					di.setFatturato(rs.getString("IMP_FATTURATO"));
					di.setTotaleBilancioAnnuale(rs.getString("TOT_BILANCIO_ANNUO"));
					di.setUnitaLavorativeAnnue(rs.getString("ULA"));
					return di;
				}
			};
			
			dati = getJdbcTemplate().query(dettagliDati, lista) ;
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dati;
	}
	

	@Override
	public List<DatidimensioneImpresaVO> getDatiDimensione(Long idSoggetto, String numeroDomanda) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getDatiDimensione]";
		LOG.info(prf + " BEGIN");		
		DatidimensioneImpresaVO dati = new DatidimensioneImpresaVO();
		List<DatidimensioneImpresaVO> listDati = new ArrayList<DatidimensioneImpresaVO>(); 
		try {
			
			String getIdProgetto ="  SELECT ptp.ID_PROGETTO \r\n"
					+ "    FROM pbandi_t_progetto ptp\r\n"
					+ "    JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \r\n"
					+ "    WHERE ptd.NUMERO_DOMANDA =?";
				
				BigDecimal idProgetto = getJdbcTemplate().queryForObject(getIdProgetto, BigDecimal.class, numeroDomanda); 
				
				String getDatImpr = null;
			if(idProgetto!=null && idProgetto.intValue()>0) {
				
				getDatImpr ="SELECT\r\n"
						+ "    PRSD.ID_SOGGETTO,\r\n"
						+ "    prsd.ID_ENTE_GIURIDICO,\r\n"
						+ "    pteg.DT_VALUTAZIONE_ESITO,\r\n"
						+ "    pddi.DESC_DIMENSIONE_IMPRESA,\r\n"
						+ "    EXTRACT (YEAR FROM pteg.DT_VALUTAZIONE_ESITO) AS year\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_progetto ptd ON ptd.ID_PROGETTO  = prsd.ID_PROGETTO \r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = PRSD.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_D_DIMENSIONE_IMPRESA pddi ON pteg.ID_DIMENSIONE_IMPRESA = pddi.ID_DIMENSIONE_IMPRESA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    --AND prsd.ID_SOGGETTO = "+idSoggetto+" \r\n"
						+ "    AND ptd.ID_PROGETTO  ="+idProgetto+"\r\n"
						+ "    AND pteg.DT_VALUTAZIONE_ESITO IS NOT NULL";
				
			}else {
				getDatImpr = "SELECT\r\n"
						+ "    PRSD.ID_SOGGETTO,\r\n"
						+ "    prsd.ID_ENTE_GIURIDICO,\r\n"
						+ "    ptd.NUMERO_DOMANDA,\r\n"
						+ "    pteg.DT_VALUTAZIONE_ESITO,\r\n"
						+ "    pddi.DESC_DIMENSIONE_IMPRESA,"
						+ "    EXTRACT (YEAR FROM pteg.DT_VALUTAZIONE_ESITO) AS year\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = PRSD.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_D_DIMENSIONE_IMPRESA pddi ON pteg.ID_DIMENSIONE_IMPRESA = pddi.ID_DIMENSIONE_IMPRESA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    --AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "    AND ptd.numero_domanda = '"+numeroDomanda+"'\r\n"
						+ " AND pteg.DT_VALUTAZIONE_ESITO IS NOT NULL";
				
			}
			dati = getJdbcTemplate().query(getDatImpr, new  ResultSetExtractor<DatidimensioneImpresaVO>(){
				
				@Override
				public DatidimensioneImpresaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					DatidimensioneImpresaVO dati = new DatidimensioneImpresaVO(); 
					while (rs.next()) {
						dati.setDataInseriemnto(rs.getDate("DT_VALUTAZIONE_ESITO"));
						dati.setDescDimImpresa(rs.getString("DESC_DIMENSIONE_IMPRESA"));
						//dati.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
						dati.setAnno(rs.getLong("year")); 
						dati.setDataValutazioneEsito(rs.getDate("DT_VALUTAZIONE_ESITO"));
						dati.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					}
					return dati;
				}
				
			});
			
			dati.setNumeroDomanda(numeroDomanda);
			
			if(dati.getAnno() == null) {
				// recupero la data di valutazione a partire della tabella pbandi t domanda 
				String getAnno="SELECT EXTRACT (YEAR FROM ptd.DT_PRESENTAZIONE_DOMANDA) AS year \r\n"
						+ "	FROM PBANDI_T_DOMANDA ptd \r\n"
						+ "	WHERE NUMERO_DOMANDA ='"+numeroDomanda+"'"; 
				
				String getdataValu="SELECT ptd.DT_PRESENTAZIONE_DOMANDA \r\n"
						+ "	FROM PBANDI_T_DOMANDA ptd \r\n"
						+ "	WHERE NUMERO_DOMANDA ='"+numeroDomanda+"'"; 
				dati.setAnno(getJdbcTemplate().queryForObject(getAnno, Long.class));
				
				dati.setDataValutazioneEsito(getJdbcTemplate().queryForObject(getdataValu, Date.class));
			}
			
		// recupero i dati sugli utlimi 3 periodi
			
			
			Long periodo1=dati.getAnno()-1;
			Long periodo2=dati.getAnno()-2;
			Long perdiodo3=dati.getAnno()-3;
				
			String dettagliDati="SELECT ptp.DESC_PERIODO ,  ptcdi.ULA , ptcdi.IMP_FATTURATO , ptcdi.TOT_BILANCIO_ANNUO \r\n"
					+ "	FROM PBANDI_T_PERIODO ptp \r\n"
					+ "	LEFT JOIN PBANDI_D_TIPO_PERIODO pdtp ON ptp.ID_TIPO_PERIODO = pdtp.ID_TIPO_PERIODO \r\n"
					+ "	LEFT JOIN PBANDI_T_CALC_DIM_IMPRESA ptcdi ON PTP .ID_PERIODO = ptcdi.ID_PERIODO \r\n"
					+ "	WHERE \r\n"
					+ "	ptcdi.ID_SOGGETTO ="+dati.getIdSoggetto()+"\r\n"
					+ "	AND ptp.DESC_PERIODO IN ('"+periodo1+"', '"+periodo2+"', '"+perdiodo3+"')\r\n"
					+ "	AND pdtp.ID_TIPO_PERIODO =1 \r\n"
					+ "	ORDER BY ptp.DESC_PERIODO DESC "; 
			
			RowMapper<DettaglioImpresaVO> lista = new RowMapper<DettaglioImpresaVO>() {
				
				@Override
				public DettaglioImpresaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					DettaglioImpresaVO di = new  DettaglioImpresaVO(); 
					
					di.setAnnoRiferimento(rs.getString("DESC_PERIODO"));
					di.setFatturato(rs.getString("IMP_FATTURATO"));
					di.setTotaleBilancioAnnuale(rs.getString("TOT_BILANCIO_ANNUO"));
					di.setUnitaLavorativeAnnue(rs.getString("ULA"));
					return di;
				}
			};
			
			dati.setDettaglio(getJdbcTemplate().query(dettagliDati, lista));
			
			listDati.add(dati); 
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_T_PERIODO", e);
		}
		
		return listDati;
	}


	@Override
	public List<AltriDatiDsan> getDsan(Long idSoggetto, String numeroDomanda) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getDSAN]";
		LOG.info(prf + " BEGIN");
		List<AltriDatiDsan> elenco = new ArrayList<AltriDatiDsan>(); 
		try {
			String getDsan = "   SELECT\r\n"
					+ "    ptsd.DT_EMISSIONE_DSAN,\r\n"
					+ "    ptsd.DT_SCADENZA,\r\n"
					+ "    ptdi.ID_DOCUMENTO_INDEX,\r\n"
					+ "    ptdi.NOME_DOCUMENTO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SOGGETTO_DSAN ptsd\r\n"
					+ "      JOIN PBANDI_T_DOMANDA ptd ON ptsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "      LEFT JOIN PBANDI_T_DOCUMENTO_INDEX ptdi ON ptdi.ID_TARGET = ptsd.ID_SOGGETTO_DSAN \r\n"
					+ "  AND ptdi.ID_ENTITA IN (\r\n"
					+ "    SELECT\r\n"
					+ "      c.ID_ENTITA\r\n"
					+ "    FROM\r\n"
					+ "      PBANDI_C_ENTITA c\r\n"
					+ "    WHERE\r\n"
					+ "      c.NOME_ENTITA = 'PBANDI_T_SOGGETTO_DSAN'\r\n"
					+ "  )\r\n"
					+ "WHERE\r\n"
					+ "    ptd.NUMERO_DOMANDA =?\r\n"
					+ "    ORDER by ptsd.DT_EMISSIONE_DSAN  DESC "; 
			
			RowMapper<AltriDatiDsan> lista = new RowMapper<AltriDatiDsan>() {
				
				@Override
				public AltriDatiDsan mapRow(ResultSet rs, int rowNum) throws SQLException {
					AltriDatiDsan dsan = new AltriDatiDsan(); 
						dsan.setDataEmissioneDsan(rs.getDate("DT_EMISSIONE_DSAN"));
						dsan.setDataScadenza(rs.getDate("DT_SCADENZA"));
						dsan.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
						dsan.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
						return dsan;
				}
			};
			
			elenco = getJdbcTemplate().query(getDsan, lista, new Object[] {
					numeroDomanda
			});
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_T_PERIODO", e);
		}
		return elenco;
	}


	@Override
	public List<AtlanteVO> getComuniEsteri(Long idNazioneEstera) {
		
		String prf = "[AnagraficaBeneficiarioDAOImpl::getComuni]";
		LOG.info(prf + " BEGIN");
		List<AtlanteVO> comuni = new ArrayList<AtlanteVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n"
					+ "    pdc.ID_COMUNE_ESTERO,\r\n"
					+ "    pdc.DESC_COMUNE_ESTERO,\r\n"
					+ "    pdc.ID_NAZIONE\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_D_COMUNE_ESTERO pdc\r\n" + 
					" WHERE \r\n" + 
					"	pdc.ID_NAZIONE = "+ idNazioneEstera + "\r\n");

			ResultSetExtractor<List<AtlanteVO>> rse = new ResultSetExtractor<List<AtlanteVO>>() {

				@Override
				public List<AtlanteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

					List<AtlanteVO> vo = new ArrayList<AtlanteVO>();

					while (rs.next())
					{
						AtlanteVO v = new AtlanteVO();

						v.setIdComune(rs.getString("ID_COMUNE_ESTERO"));
						v.setDescComune(rs.getString("DESC_COMUNE_ESTERO"));
						v.setIdNazione(rs.getString("ID_NAZIONE"));
						vo.add(v);
					}
					return vo;
				}
			};
			comuni = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return comuni;
	}


	@Override
	public List<AtlanteVO> getProvincieConidRegioni(Long idRegione) {
		String prf = "[AnagraficaBeneficiarioDAOImpl::getProvincie]";
		LOG.info(prf + " BEGIN");
		List<AtlanteVO> provincie = new ArrayList<AtlanteVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n" + 
					"	pdp.ID_PROVINCIA ,\r\n" + 
					"	pdp.SIGLA_PROVINCIA ,\r\n" + 
					"	pdp.ID_REGIONE, pdp.DESC_PROVINCIA\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_D_PROVINCIA pdp\r\n"
					+ " where id_regione="+idRegione+ 
					"ORDER BY\r\n" + 
					"	DESC_PROVINCIA desc");

			ResultSetExtractor<List<AtlanteVO>> rse = new ResultSetExtractor<List<AtlanteVO>>() {

				@Override
				public List<AtlanteVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

					List<AtlanteVO> vo = new ArrayList<AtlanteVO>();

					while (rs.next())
					{
						AtlanteVO v = new AtlanteVO();

						v.setIdProvincia(rs.getString("ID_PROVINCIA"));
						v.setDescProvincia(rs.getString("DESC_PROVINCIA"));
						v.setIdRegione(rs.getString("ID_REGIONE"));
						v.setSiglaProvincia(rs.getString("SIGLA_PROVINCIA"));
						vo.add(v);
					}
					return vo;
				}
			};
			provincie = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return provincie;
	}


	@Override
	public List<RuoloDTO> getElencoRuoliDipendente() {

		List<RuoloDTO> elenco = new ArrayList<RuoloDTO>(); 
		
		try {
			String sql = "SELECT\r\n"
					+ "    DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
					+ "    ID_TIPO_SOGGETTO_CORRELATO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_D_TIPO_SOGG_CORRELATO pdtsc\r\n"
					+ "WHERE\r\n"
					+ "    PDTSC.FLAG_INDIPENDENTE = 'N'\r\n"
					+ "ORDER BY\r\n"
					+ "    DESC_TIPO_SOGGETTO_CORRELATO "; 
			
			RowMapper<RuoloDTO> lista = new RowMapper<RuoloDTO>() {

				@Override
				public RuoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					RuoloDTO ruolo = new RuoloDTO();
					ruolo.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
					ruolo.setDescTipoSoggCorr(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
					return ruolo;
				}
			};
			
			elenco = getJdbcTemplate().query(sql, lista);
		} catch (Exception e) {
		}
		
		return elenco;
	}


	@Override
	public List<AttivitaDTO> getStatoAttivita() {
		
		List<AttivitaDTO> attivita = new ArrayList<AttivitaDTO>(); 
		try {
			String getAttivita =  " SELECT pdsa.ID_STATO_ATTIVITA , DESC_STATO_ATTIVITA \r\n"
								+ "FROM PBANDI_D_STATO_ATTIVITA pdsa ";

			RowMapper< AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {			
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO att = new AttivitaDTO();
					att.setIdAttivita(rs.getLong("ID_STATO_ATTIVITA"));
					att.setDescAttivita(rs.getString("DESC_STATO_ATTIVITA"));
					return att;
				}
			};
			
			attivita = getJdbcTemplate().query(getAttivita, lista); 
		} catch (Exception e) {
			
		}
		return attivita;
	}


	@Override
	public Object getElencoTipoAnag() {
		
		List<AttivitaDTO> attivita = new ArrayList<AttivitaDTO>(); 
		try {
			String getAttivita =  " SELECT ID_TIPO_ANAGRAFICA , DESC_TIPO_ANAGRAFICA \r\n"
								+ "FROM PBANDI_D_TIPO_ANAGRAFICA ";

			RowMapper< AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {			
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO att = new AttivitaDTO();
					att.setIdAttivita(rs.getLong("ID_TIPO_ANAGRAFICA"));
					att.setDescAttivita(rs.getString("DESC_TIPO_ANAGRAFICA"));
					return att;
				}
			};
			
			attivita = getJdbcTemplate().query(getAttivita, lista); 
		} catch (Exception e) {
			
		}
		return attivita;
	}


	@Override
	public Object getListTipoDocumento() {
		
		
		List<AttivitaDTO> attivita = new ArrayList<AttivitaDTO>(); 
		try {
			String getAttivita =  "    SELECT  pdtd.ID_TIPO_DOCUMENTO , pdtd.DESC_TIPO_DOCUMENTO \r\n"
					+ "    FROM PBANDI_D_TIPO_DOCUMENTO pdtd";

			RowMapper< AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {			
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO att = new AttivitaDTO();
					att.setIdAttivita(rs.getLong("ID_TIPO_DOCUMENTO"));
					att.setDescAttivita(rs.getString("DESC_TIPO_DOCUMENTO"));
					return att;
				}
			};
			
			attivita = getJdbcTemplate().query(getAttivita, lista); 
		} catch (Exception e) {
			
		}
		return attivita;
	}
	
	
}
