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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.ProgettiEscludiEstrattiDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.NuovoCampionamentoDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.affidamenti.ProgettoCampioneRowmapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.DichiarazioneSpesaCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ElenchiProgettiCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.NormativaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.NuovoCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoCampioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoEstrattoCampVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroAcqProgettiVO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoElencoProgettiCampione;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNormativaAffidamentoVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbgestfinbo.util.Constants;

public class NuovoCampionamentoDAOImpl extends JdbcDaoSupport implements NuovoCampionamentoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public NuovoCampionamentoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	@Transactional
	public List<ProgettoEstrattoCampVO> elaboraNuovoCampionamento(NuovoCampionamentoVO nuovoCampVO) {
		String prf = "[NuovoCampionamentoDAOImpl::elaboraNuovoCampionamento]";
		LOG.info(prf + "BEGIN");

		BigDecimal idNuovoCampionamento = null;
		List<ProgettoEstrattoCampVO> listaProgettiEstratti = new ArrayList<ProgettoEstrattoCampVO>();

		try {
			// INSERIMENTO DENTRO LA TABELLA PBANDI_T_CAMPIONAMENTO_FINP con ritorno dell'id
			// appena inserito.
			idNuovoCampionamento = insertCampionamento(nuovoCampVO);
			if (idNuovoCampionamento == null) {
				LOG.info("errore inserimento dentro PBANDI_T_CAMPIONAMENTO_FINP");
				return null;
			}

			// INSERIMENTO DENTRO LA TABELLA PBANDI_R_RIC_CAMP_BANDO_LINEA DI TUTTI I BANDI
			// SCELTI DALL'UTENTE PER CAMPIONARE
			boolean esitoInsertBandi;
			esitoInsertBandi = insertBandi(nuovoCampVO.getBandi(), idNuovoCampionamento);
			if (!esitoInsertBandi)
				LOG.info("errore inserimento dei bandi");

			// INSERIMENTO DELLA DICHIARAZIONE DI SPESA DENTRO LA TABBELLA
			// PBANDI_T_RIC_CAMP_DICH_SP
			boolean esitoInsertDichSpesa;
			esitoInsertDichSpesa = insertDichSpesa(nuovoCampVO.getDichiarazioneSpesa(), idNuovoCampionamento);
			if (!esitoInsertDichSpesa)
				LOG.info("errore inserimento dichiarazione di spesa");

			// INSERIMENTO DEI TIPO DI DICHIARAZIONE DI SPESA IN
			// PBANDI_R_RIC_CAMP_TIPO_DICH_SP
			boolean esitoinsertTipiDichSp;
			esitoinsertTipiDichSp = insertTipiDichSpesa(nuovoCampVO.getDichiarazioneSpesa().getTipoDichiarazione(),
					idNuovoCampionamento);
			if (!esitoinsertTipiDichSp)
				LOG.info("errore inserimento dichiarazione di spesa");

			// RECUPERO PROGETTI
			List<BigDecimal> listaProgetti = new ArrayList<BigDecimal>();
			listaProgetti = getProgetti(idNuovoCampionamento);
			if (listaProgetti == null)
				LOG.info("non ci sono progetti per questo campionamento");

			// SALVATAGGIO PROGETTI DENTRO DA CONCLUDERE dentro la tabella
			// PBANDI_R_CAMP_PROGETTI
			boolean esitoSalvaProgetti;
//			esitoSalvaProgetti = salvaProgettiEstratti(listaProgetti, idNuovoCampionamento,
//					nuovoCampVO.getIdUtenteIns());
//			if (!esitoSalvaProgetti)
				LOG.info("errore salvataggio progetti dentro tabella PBANDI_R_CAMP_PROGETTI");

			// ESTRAZIONE DEI SOGEETTO PER OGNI PROGETTO
			listaProgettiEstratti = estrazioneProgetti(idNuovoCampionamento, listaProgetti, false);

		} catch (Exception e) {
			LOG.error("Exception" + e);
			throw e;
		}
		LOG.info(prf + "END");
		return listaProgettiEstratti;
	}

	private List<ProgettoEstrattoCampVO> estrazioneProgetti(BigDecimal idNuovoCampionamento,
			List<BigDecimal> listaProgetti, boolean isCampionamento) {
		String prf = "[NuovoCampionamentoDAOImpl::elaboraNuovoCampionamento -> estrazioneProgetti]";
		LOG.info(prf + "BEGIN");

		// per ogni progetto salvato devo recuperare i dati di tutti i soggetti che
		// hanno fatto richiesta
		List<ProgettoEstrattoCampVO> listaProgettiEstratti = new ArrayList<ProgettoEstrattoCampVO>();
		try {
			String progetti = listaProgetti.toString().replace("[", "").replace("]", "");
			String sql1 = "WITH SEDE_LEGALE as(\r\n" + "    SELECT\r\n" + "        DISTINCT prsp.ID_SOGGETTO,\r\n"
					+ "        prsp.ID_PROGETTO,\r\n" + "        pdts.DESC_TIPO_SEDE,\r\n"
					+ "        pdc.DESC_COMUNE,\r\n" + "        pdp.DESC_PROVINCIA\r\n" + "    FROM\r\n"
					+ "        PBANDI_R_SOGGETTO_PROGETTO prsp,\r\n" + "        PBANDI_R_SOGG_PROGETTO_SEDE prsps,\r\n"
					+ "        PBANDI_T_INDIRIZZO pti,\r\n" + "        PBANDI_D_TIPO_SEDE pdts,\r\n"
					+ "        PBANDI_D_COMUNE pdc,\r\n" + "        PBANDI_D_PROVINCIA pdp\r\n" + "    WHERE\r\n"
					+ "        prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "        AND prsps.ID_TIPO_SEDE = pdts.id_TIPO_SEDE\r\n"
					+ "        AND prsps.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
					+ "        AND pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
					+ "        AND pti.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
					+ "        AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n" + "        AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "        AND prsps.ID_TIPO_SEDE = 1\r\n" + "),\r\n" + "SEDE_INTERVENTO AS (\r\n"
					+ "    SELECT\r\n" + "        DISTINCT prsp.ID_SOGGETTO,\r\n" + "        prsp.ID_PROGETTO,\r\n"
					+ "        pdts.DESC_TIPO_SEDE,\r\n" + "        pdc.DESC_COMUNE,\r\n"
					+ "        pdp.DESC_PROVINCIA\r\n" + "    FROM\r\n" + "        PBANDI_R_SOGGETTO_PROGETTO prsp,\r\n"
					+ "        PBANDI_R_SOGG_PROGETTO_SEDE prsps,\r\n" + "        PBANDI_T_INDIRIZZO pti,\r\n"
					+ "        PBANDI_D_TIPO_SEDE pdts,\r\n" + "        PBANDI_D_COMUNE pdc,\r\n"
					+ "        PBANDI_D_PROVINCIA pdp\r\n" + "    WHERE\r\n"
					+ "        prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "        AND prsps.ID_TIPO_SEDE = pdts.id_TIPO_SEDE\r\n"
					+ "        AND prsps.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
					+ "        AND pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
					+ "        AND pti.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
					+ "        AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n" + "        AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "        AND prsps.ID_TIPO_SEDE = 2\r\n" + ")\r\n" 
					+ "SELECT\r\n"
					+ "    DISTINCT sl.ID_SOGGETTO,\r\n" + "    sl.id_progetto,\r\n"
					+ "    sl.DESC_TIPO_SEDE as desc_tipo_sl,\r\n" + "    sl.DESC_COMUNE as desc_com_sl,\r\n"
					+ "    sl.DESC_PROVINCIA as desc_prov_sl,\r\n" + "    si.DESC_TIPO_SEDE as desc_tipo_si,\r\n"
					+ "    si.DESC_COMUNE as desc_com_si,\r\n" + "    si.DESC_PROVINCIA as desc_prov_si,\r\n"
					+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n" + "    pteg.ID_ENTE_GIURIDICO,\r\n"
					+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO AS DENOMINAZIONE,\r\n" + "    prbli.NOME_BANDO_LINEA,\r\n"
					+ "    ptd.ID_DOMANDA,\r\n" + "    ptd.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "    pdfg.DESC_FORMA_GIURIDICA, ptp.CODICE_VISUALIZZATO\r\n" 
					+ "FROM\r\n" + "    SEDE_LEGALE sl\r\n"
					+ "    JOIN SEDE_INTERVENTO si ON si.id_soggetto = sl.id_soggetto AND sl.id_progetto=si.id_progetto\r\n"
					+ "    JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = sl.id_soggetto\r\n"
					+ "    --JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = si.id_progetto\r\n"
					+ "    JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = si.id_progetto AND prsp.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
					+ "    JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "    JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "    JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n"
					+ "WHERE\r\n" + "    sl.id_progetto in (" + progetti + ")\r\n" + "    AND si.id_progetto in ("
					+ progetti + ")\r\n" + "    order by sl.id_progetto";

			String sqlFisiche = "SELECT\r\n" + "    DISTINCT pts.ID_SOGGETTO,\r\n"
					+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n" + "    ptpf.ID_PERSONA_FISICA,\r\n"
					+ "    prbli.PROGR_BANDO_LINEA_INTERVENTO, ptp.CODICE_VISUALIZZATO,\r\n"
					+ "    CONCAT(ptpf.NOME, ptpf.COGNOME) AS DENOMINAZIONE,\r\n" + "    prbli.NOME_BANDO_LINEA,\r\n"
					+ "    ptp.ID_PROGETTO \r\n" + "FROM\r\n" + "    PBANDI_T_PERSONA_FISICA ptpf\r\n"
					+ "    JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptpf.ID_SOGGETTO\r\n"
					+ "    JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_SOGGETTO = ptpf.ID_SOGGETTO\r\n"
					+ "    JOIN PBANDI_T_PROGETTO ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
					+ "    JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "WHERE\r\n" + "    prsp.ID_PROGETTO in (" + progetti + ")\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n" + "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	   order by ptp.id_progetto" + "    /*AND ptpf.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n" + "            MAX(ptpf.ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
					+ "        FROM\r\n" + "            PBANDI_T_PERSONA_FISICA ptpf\r\n" + "        GROUP BY\r\n"
					+ "            ptpf.ID_SOGGETTO\r\n" + "   )*/\r\n";

			RowMapper<ProgettoEstrattoCampVO> listaPersoneGiuridiche = new RowMapper<ProgettoEstrattoCampVO>() {
				@Override
				public ProgettoEstrattoCampVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ProgettoEstrattoCampVO progetto = new ProgettoEstrattoCampVO();
					progetto.setCodiceFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
					progetto.setComuneSedeIntervento(rs.getString("desc_com_si"));
					progetto.setComuneSedeLegale(rs.getString("desc_com_sl"));
					// progetto.setDataUltimoControllo(rs.getDate("")); per dopo
					progetto.setDenominazione(rs.getString("DENOMINAZIONE"));
					progetto.setDescBando(rs.getString("NOME_BANDO_LINEA"));
					progetto.setDescFormaGiuridica(rs.getString("DESC_FORMA_GIURIDICA"));
					progetto.setProvSedeIntervento(rs.getString("desc_prov_si"));
					progetto.setProvSedeLegale(rs.getString("desc_prov_sl"));
					progetto.setIdProgetto(rs.getBigDecimal("id_progetto"));
					progetto.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					progetto.setIdCampionamento(idNuovoCampionamento);
					progetto.setCodVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
					return progetto;
				}
			};

			RowMapper<ProgettoEstrattoCampVO> listaPersoneFisiche = new RowMapper<ProgettoEstrattoCampVO>() {
				@Override
				public ProgettoEstrattoCampVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ProgettoEstrattoCampVO progetto = new ProgettoEstrattoCampVO();
					progetto.setCodiceFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
					progetto.setDenominazione(rs.getString("DENOMINAZIONE"));
					progetto.setDescBando(rs.getString("NOME_BANDO_LINEA"));
					progetto.setIdProgetto(rs.getBigDecimal("id_progetto"));
					progetto.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					progetto.setIdCampionamento(idNuovoCampionamento);
					progetto.setCodVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
					return progetto;
				}
			};

			List<ProgettoEstrattoCampVO> list1 = new ArrayList<ProgettoEstrattoCampVO>();
			list1 = getJdbcTemplate().query(sql1, listaPersoneGiuridiche);

			listaProgettiEstratti.addAll(list1);

			List<ProgettoEstrattoCampVO> list2 = new ArrayList<ProgettoEstrattoCampVO>();
			list2 = getJdbcTemplate().query(sqlFisiche, listaPersoneFisiche);

			listaProgettiEstratti.addAll(list2);

		} catch (Exception e) {
			LOG.error("errore estrazione progetti" + e);
		}
		return listaProgettiEstratti;
	}

	private List<BigDecimal> getProgetti(BigDecimal idNuovoCampionamento) {
		String prf = "[NuovoCampionamentoDAOImpl::elaboraNuovoCampionamento -> getProgetti]";
		LOG.info(prf + "BEGIN");

		List<BigDecimal> listaProgetti = new ArrayList<BigDecimal>();

		try {
			String sql = "WITH bandi AS (\r\n" + "    SELECT\r\n" + "        DISTINCT ptp.ID_progetto,\r\n"
					+ "        ptcf.ID_CAMPIONAMENTO\r\n" + "    FROM\r\n"
					+ "        PBANDI_R_BANDO_LINEA_INTERVENT pbli,\r\n" + "        PBANDI_T_DOMANDA ptd,\r\n"
					+ "        PBANDI_T_PROGETTO ptp,\r\n" + "        PBANDI_T_CAMPIONAMENTO_FINP ptcf\r\n"
					+ "    WHERE\r\n" + "        pbli.PROGR_BANDO_LINEA_INTERVENTO IN (\r\n" + "            SELECT\r\n"
					+ "                PROGR_BANDO_LINEA_INTERVENTO\r\n" + "            FROM\r\n"
					+ "                PBANDI_R_RIC_CAMP_BANDO_LINEA\r\n" + "            WHERE\r\n"
					+ "                ID_CAMPIONAMENTO = ptcf.ID_CAMPIONAMENTO\r\n" + "        )\r\n"
					+ "        AND pbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "        AND ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n" + "),\r\n" + "tipo_dich_spesa AS (\r\n"
					+ "    SELECT\r\n" + "        DISTINCT ptp.ID_PROGETTO,\r\n" + "        ptcf.ID_CAMPIONAMENTO\r\n"
					+ "    FROM\r\n" + "        PBANDI_T_PROGETTO ptp,\r\n"
					+ "        PBANDI_T_CAMPIONAMENTO_FINP ptcf,\r\n" + "        PBANDI_T_DICHIARAZIONE_SPESA ptds\r\n"
					+ "    WHERE\r\n" + "        ptds.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
					+ "        AND ptds.ID_TIPO_DICHIARAZ_SPESA IN (\r\n" + "            SELECT\r\n"
					+ "                ID_TIPO_DICHIARAZ_SPESA\r\n" + "            FROM\r\n"
					+ "                PBANDI_R_RIC_CAMP_TIPO_DICH_SP\r\n" + "            WHERE\r\n"
					+ "                ID_CAMPIONAMENTO = ptcf.ID_CAMPIONAMENTO\r\n" + "        )\r\n"
					+ "        AND ptds.DT_CHIUSURA_VALIDAZIONE IS NOT NULL\r\n" + "),\r\n" + "dich_spesa AS (\r\n"
					+ "    SELECT\r\n" + "        DISTINCT ptp.ID_progetto,\r\n" + "        ptcf.ID_CAMPIONAMENTO\r\n"
					+ "    FROM\r\n" + "        PBANDI_T_PROGETTO ptp,\r\n"
					+ "        PBANDI_T_CAMPIONAMENTO_FINP ptcf,\r\n" + "        PBANDI_T_DICHIARAZIONE_SPESA ptds,\r\n"
					+ "        PBANDI_T_RIC_CAMP_DICH_SP ptrcds\r\n" + "    WHERE\r\n"
					+ "        ptds.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
					+ "        AND ptds.DT_CHIUSURA_VALIDAZIONE IS NOT NULL\r\n"
					+ "        AND ptrcds.ID_CAMPIONAMENTO = ptcf.ID_CAMPIONAMENTO\r\n"
					+ "        AND ptds.DT_DICHIARAZIONE >= ptrcds.DT_RICEZIONE_DA\r\n"
					+ "        AND ptds.DT_DICHIARAZIONE <= ptrcds.DT_RICEZIONE_A\r\n"
					+ "        AND ptds.DT_CHIUSURA_VALIDAZIONE >= ptrcds.DT_ULTIMO_ESITO_DA\r\n"
					+ "        AND ptds.DT_CHIUSURA_VALIDAZIONE <= ptrcds.DT_ULTIMO_ESITO_A\r\n"
					+ "        AND ptrcds.IMP_RENDICONTATO_A >= (\r\n" + "            SELECT\r\n"
					+ "                SUM(prpqp.IMPORTO_QUIETANZATO)\r\n" + "            FROM\r\n"
					+ "                PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqp\r\n" + "            WHERE\r\n"
					+ "                prpqp.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "            GROUP BY\r\n" + "                prpqp.ID_DICHIARAZIONE_SPESA\r\n" + "        )\r\n"
					+ "        AND ptrcds.IMP_RENDICONTATO_DA <= (\r\n" + "            SELECT\r\n"
					+ "                SUM(prpqp.IMPORTO_QUIETANZATO)\r\n" + "            FROM\r\n"
					+ "                PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqp\r\n" + "            WHERE\r\n"
					+ "                prpqp.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "            GROUP BY\r\n" + "                prpqp.ID_DICHIARAZIONE_SPESA\r\n" + "        )\r\n"
					+ "        AND ptrcds.IMP_VALIDATO_DA <= (\r\n" + "            SELECT\r\n"
					+ "                SUM(prpqp.IMPORTO_VALIDATO)\r\n" + "            FROM\r\n"
					+ "                PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqp\r\n" + "            WHERE\r\n"
					+ "                prpqp.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "            GROUP BY\r\n" + "                prpqp.ID_DICHIARAZIONE_SPESA\r\n" + "        )\r\n"
					+ "        AND ptrcds.IMP_VALIDATO_A >= (\r\n" + "            SELECT\r\n"
					+ "                SUM(prpqp.IMPORTO_VALIDATO)\r\n" + "            FROM\r\n"
					+ "                PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqp\r\n" + "            WHERE\r\n"
					+ "                prpqp.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "            GROUP BY\r\n" + "                prpqp.ID_DICHIARAZIONE_SPESA\r\n" + "        )\r\n"
					+ ")\r\n" + "SELECT\r\n" + "    DISTINCT bandi.id_progetto\r\n" + "FROM\r\n" + "    bandi\r\n"
					+ "    JOIN tipo_dich_spesa tdc ON bandi.id_progetto = tdc.id_progetto\r\n"
					+ "    JOIN dich_spesa ds ON ds.id_progetto = tdc.id_progetto\r\n" + "WHERE\r\n"
					+ "    bandi.id_campionamento = " + idNuovoCampionamento;

			RowMapper<BigDecimal> lista = new RowMapper<BigDecimal>() {
				@Override
				public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
					BigDecimal idProgetto;
					idProgetto = rs.getBigDecimal("ID_PROGETTO");
					return idProgetto;
				}
			};

//			listaProgettiBando = getJdbcTemplate().query(getProgettiDaBandoLinea,  lista); 
//			listaProgettiBando = getJdbcTemplate().query(getProgettiDaTipoDichSpesa,  lista); 
//			listaProgettiBando = getJdbcTemplate().query(getProgettoDaDichSpesa,  lista); 

			listaProgetti = getJdbcTemplate().query(sql, lista);

		} catch (Exception e) {
			LOG.error("errore in fase di recupero dei progetti: " + e);
		}
		return listaProgetti;
	}

	private boolean insertTipiDichSpesa(AttivitaDTO[] tipoDichiarazione, BigDecimal idNuovoCampionamento) {
		String prf = "[NuovoCampionamentoDAOImpl::elaboraNuovoCampionamento -> insertTipiDichSpesa]";
		LOG.info(prf + "BEGIN");

		try {

			String insert = "insert into PBANDI_R_RIC_CAMP_TIPO_DICH_SP(ID_CAMPIONAMENTO,ID_TIPO_DICHIARAZ_SPESA)\r\n"
					+ "values(?,?)";

			for (AttivitaDTO tipo : tipoDichiarazione) {
				getJdbcTemplate().update(insert, new Object[] { idNuovoCampionamento, tipo.getIdAttivita() });
			}

		} catch (Exception e) {
			LOG.error("errore inserimento dei tipi di dichiarazione di spesa: " + e);
			return false;
		}

		return true;
	}

	private boolean insertDichSpesa(DichiarazioneSpesaCampionamentoVO dichiarazioneSpesa,
			BigDecimal idNuovoCampionamento) {
		String prf = "[NuovoCampionamentoDAOImpl::elaboraNuovoCampionamento -> insertDichSpesa]";
		LOG.info(prf + "BEGIN");

		try {
			String dtRicezDa = null, dtRicezA = null, dtUltEsDa = null, dtUltEsA = null;
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt3 = new SimpleDateFormat("yyyy-MM-dd");

			if (dichiarazioneSpesa.getDataRicezioneInizio() != null)
				dtRicezDa = dt.format(dichiarazioneSpesa.getDataRicezioneInizio());
			if (dichiarazioneSpesa.getDataRicezioneFine() != null)
				dtRicezA = dt1.format(dichiarazioneSpesa.getDataRicezioneFine());
			if (dichiarazioneSpesa.getDataUltimoEsitoInizio() != null)
				dtUltEsDa = dt2.format(dichiarazioneSpesa.getDataUltimoEsitoInizio());
			if (dichiarazioneSpesa.getDataUltimoEsitoFine() != null)
				dtUltEsA = dt3.format(dichiarazioneSpesa.getDataUltimoEsitoFine());

			String insertDich = "insert into PBANDI_T_RIC_CAMP_DICH_SP(ID_CAMPIONAMENTO,\r\n" + "DT_RICEZIONE_DA,\r\n"
					+ "DT_RICEZIONE_A,\r\n" + "DT_ULTIMO_ESITO_DA,\r\n" + "DT_ULTIMO_ESITO_A,\r\n"
					+ "IMP_RENDICONTATO_DA,\r\n" + "IMP_RENDICONTATO_A,\r\n" + "IMP_VALIDATO_DA,\r\n"
					+ "IMP_VALIDATO_A) VALUES(?,?,?,?,?,?,?,?,?) ";

			getJdbcTemplate().update(insertDich, new Object[] { idNuovoCampionamento,
					(dtRicezDa != null && dtRicezDa.trim().length() > 0) ? Date.valueOf(dtRicezDa) : null,
					(dtRicezA != null && dtRicezA.trim().length() > 0) ? Date.valueOf(dtRicezA) : null,
					(dtUltEsDa != null && dtUltEsDa.trim().length() > 0) ? Date.valueOf(dtUltEsDa) : null,
					(dtUltEsA != null && dtUltEsA.trim().length() > 0) ? Date.valueOf(dtUltEsA) : null,
					dichiarazioneSpesa.getImporRendicontatoInizio(), dichiarazioneSpesa.getImporRendicontatoFine(),
					dichiarazioneSpesa.getImportoValidatoInizio(), dichiarazioneSpesa.getImportoValidatoFine() });

		} catch (Exception e) {
			LOG.error("errore inserimento dichiarazione di spesa: " + e);
			return false;
		}

		return true;
	}

	private BigDecimal insertCampionamento(NuovoCampionamentoVO nuovoCampVO) {
		String prf = "[NuovoCampionamentoDAOImpl:: elaboraNuovoCampionamento -> insertCampionamento]";
		LOG.info(prf + "BEGIN");
		BigDecimal idCamp;
		try {
			String getId = "SELECT SEQ_PBANDI_T_CAMPIONAMENT_FINP.nextval  FROM dual";
			idCamp = getJdbcTemplate().queryForObject(getId, BigDecimal.class);

			String insertCcampionamento = "insert into PBANDI_T_CAMPIONAMENTO_FINP(ID_CAMPIONAMENTO,\r\n"
					+ "ID_FASE_CAMP,\r\n" + "ID_TIPO_CAMP,\r\n" + "DESC_CAMPIONAMENTO,\r\n" + "ID_UTENTE_INS,\r\n"
					+ "DT_CAMPIONAMENTO,\r\n" + "DT_INSERIMENTO,\r\n" + "DT_INIZIO_VALIDITA )\r\n"
					+ " values(?,1,?,?,?,trunc(sysdate), trunc(sysdate), trunc(sysdate))";

			getJdbcTemplate().update(insertCcampionamento, new Object[] { idCamp, nuovoCampVO.getIdTipoCampionamento(),
					nuovoCampVO.getDescCampionamento(), nuovoCampVO.getIdUtenteIns() });

		} catch (Exception e) {
			LOG.error("errore inserimento campionamento" + e);
			return null;
		}

		return idCamp;
	}

	private boolean insertBandi(List<AttivitaDTO> bandi, BigDecimal idCampionamento) {
		String prf = "[NuovoCampionamentoDAOImpl:: elaboraNuovoCampionamento -> insertBandi]";
		LOG.info(prf + "BEGIN");

		try {

			String insertBando = "insert into PBANDI_R_RIC_CAMP_BANDO_LINEA(ID_CAMPIONAMENTO,\r\n"
					+ "PROGR_BANDO_LINEA_INTERVENTO)\r\n" + "values(?,?)";

			for (AttivitaDTO bando : bandi) {
				getJdbcTemplate().update(insertBando, new Object[] { idCampionamento, bando.getIdAttivita() });
			}

		} catch (Exception e) {
			LOG.error("errore inserimento dentro la tabella PBANDI_R_RIC_CAMP_BANDO_LINEA" + e);
			return false;
		}

		return true;
	}

	@Override
	public Boolean estrazioneProgetti(ProgettiEscludiEstrattiDTO progetti, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl -> estrazioneProgetti]";
		LOG.info(prf + "BEGIN");

		Boolean result = true;

		try {
			// AGGIORNAMENTO DELLA TABELLA PBANDI_R_CAMP_PROGETTI CON I PROGETTI ESCLUSI
			boolean isEsclusi;
			isEsclusi = escludoProgetti(progetti.getEsclusi(), idUtente);
			if (!isEsclusi)
				LOG.info("errore in fase di aggiornamento dei progetti esclusi");

			boolean isEstratti;
			isEstratti = estraggoProgetti(progetti.getEstratti(), idUtente);
			if (!isEstratti)
				LOG.info("errore in fase di aggiornamento dei progetti estratti");

		} catch (Exception e) {
			LOG.error("errore inserimento dentro la tabella PBANDI_R_CAMP_PROGETTI" + e);
			result = false;
			// LOG.info(prf + "FINE");
		}
		LOG.info(prf + "FINE");
		return result;
	}

	// questo metodo mi potra servire per recuperare la somma degli importi validati
	// totale
	@Override
	public BigDecimal getImportoValidatoTotale(NuovoCampionamentoVO nuovoCampVO) {
		String prf = "[NuovoCampionamentoDAOImpl -> getImportoValidatoTotale]";
		LOG.info(prf + "BEGIN");
		// List<ProgettoEstrattoCampVO> progetti = new
		// ArrayList<ProgettoEstrattoCampVO>();
		// Long sommaImporto = (long) 0.00;
		BigDecimal totale = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("WITH imp_validato AS (\r\n" + "    SELECT\r\n"
					+ "        SUM(prpqpds.IMPORTO_VALIDATO) AS imp_val,\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    FROM\r\n"
					+ "        PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqpds\r\n"
					+ "        JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON prpqpds.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "    GROUP BY\r\n" + "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    ORDER BY\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "),\r\n" + "imp_rendicontato AS (\r\n"
					+ "    SELECT\r\n" + "        SUM(prpqpds.IMPORTO_QUIETANZATO) AS imp_rend,\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    FROM\r\n"
					+ "        PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqpds\r\n"
					+ "        JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON prpqpds.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "    GROUP BY\r\n" + "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    ORDER BY\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "),\r\n" + "progetti AS (\r\n" + "    SELECT\r\n"
					+ "        sum(iv.imp_val) importo_val,\r\n" + "        SUM(ir.imp_rend) importo_rend,\r\n"
					+ "        prcp.ID_PROGETTO,\r\n" + "        prcp.ID_CAMPIONAMENTO \r\n" + "    FROM\r\n"
					+ "        PBANDI_R_CAMP_PROGETTI prcp\r\n"
					+ "        JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON ptds.ID_PROGETTO = prcp.ID_PROGETTO\r\n"
					+ "        LEFT JOIN imp_rendicontato ir ON ir.id_dichiarazione_spesa = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "        LEFT JOIN imp_validato iv ON iv.id_dichiarazione_spesa = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "        LEFT JOIN PBANDI_T_RIC_CAMP_DICH_SP ptrcds ON ptrcds.ID_CAMPIONAMENTO = prcp.ID_CAMPIONAMENTO\r\n"
					+ "    WHERE\r\n" + "        --prcp.ID_CAMPIONAMENTO = " + nuovoCampVO.getIdCampionamento() + "\r\n"
					+ "         ptds.DT_CHIUSURA_VALIDAZIONE IS NOT NULL\r\n" + "        AND prcp.FLAG_ESTRATTO ='S'");
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataRicezioneInizio() != null) {
				sql.append("  AND ptds.DT_DICHIARAZIONE >= ptrcds.DT_RICEZIONE_DA ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataRicezioneFine() != null) {
				sql.append("  AND ptds.DT_DICHIARAZIONE <= ptrcds.DT_RICEZIONE_A ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataUltimoEsitoInizio() != null) {
				sql.append("  AND ptds.DT_CHIUSURA_VALIDAZIONE >= ptrcds.DT_ULTIMO_ESITO_DA  ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataUltimoEsitoFine() != null) {
				sql.append(" AND ptds.DT_CHIUSURA_VALIDAZIONE <= ptrcds.DT_ULTIMO_ESITO_A ");
			}

			sql.append(" GROUP BY\r\n" + "        prcp.ID_PROGETTO, prcp.ID_CAMPIONAMENTO \r\n" + "), prog as (\r\n"
					+ "SELECT\r\n" + "    p.id_progetto,\r\n" + "    p.importo_val AS IMPORTO_VALIDATO,\r\n"
					+ "    p.ID_CAMPIONAMENTO \r\n" + "FROM\r\n" + "    progetti p\r\n"
					+ "    LEFT JOIN PBANDI_T_RIC_CAMP_DICH_SP ptrcds ON ptrcds.ID_CAMPIONAMENTO = p.id_campionamento\r\n"
					+ "WHERE\r\n" + "   p.id_campionamento= " + nuovoCampVO.getIdCampionamento() + "\r\n");
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImporRendicontatoInizio() != null) {
				sql.append(" AND ptrcds.IMP_RENDICONTATO_DA <= p.importo_rend");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImporRendicontatoFine() != null) {
				sql.append(" AND ptrcds.IMP_RENDICONTATO_A >= p.importo_rend ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImportoValidatoInizio() != null) {
				sql.append(" AND ptrcds.IMP_VALIDATO_DA <= p.importo_val");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImportoValidatoInizio() != null) {
				sql.append(" AND ptrcds.IMP_VALIDATO_A >= p.importo_val ");
			}

			sql.append("ORDER BY p.importo_val) select sum(prog.IMPORTO_VALIDATO) as totale from prog");

			// LOG.info(sql.toString());
			LOG.info(prf +"sql = " + sql.toString());
			totale = getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);

//			RowMapper<ProgettoEstrattoCampVO>  lista = new RowMapper<ProgettoEstrattoCampVO>() {
//				@Override
//				public ProgettoEstrattoCampVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//					ProgettoEstrattoCampVO progetto = new ProgettoEstrattoCampVO(); 
//					progetto.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
//					progetto.setImportoValidato(rs.getLong("IMPORTO_VALIDATO"));
//					progetto.setIdCampionamento(rs.getBigDecimal("ID_CAMPIONAMENTO"));
//					return progetto;
//				}
//			};

//			progetti = getJdbcTemplate().query(sql.toString(), lista); 

//			for (ProgettoEstrattoCampVO progettoEstrattoCampVO : progetti) {
//				sommaImporto = sommaImporto + progettoEstrattoCampVO.getImportoValidato(); 
//			}
		} catch (Exception e) {
			LOG.error("errore fase di importo totale : " + e);
		}

		LOG.info(prf + "END");
		return totale;
	}

	private boolean estraggoProgetti(ArrayList<ProgettoEstrattoCampVO> estratti, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: estrazioneProgetti -> estraggoProgetti]";
		LOG.info(prf + "BEGIN");

		try {
			/// aggiorno la tabella campionamento
			String updateTCamp = "update PBANDI_T_CAMPIONAMENTO_FINP " + "set id_fase_camp=2,"
					+ " dt_aggiornamento=trunc(sysdate), " + " id_utente_agg=" + idUtente + " where id_campionamento="
					+ estratti.get(0).getIdCampionamento();

			getJdbcTemplate().update(updateTCamp);

			// per ogni progetto estratto aggiorno anche la tabella PBANDI_R_CAMP_PROGETTI
			String esc = "update PBANDI_R_CAMP_PROGETTI set" + " FLAG_ESTRATTO='S',"
					+ " DT_AGGIORNAMENTO=trunc(sysdate)," + " ID_UTENTE_AGG=" + idUtente + "" + " where id_progetto= ?"
					+ " and ID_CAMPIONAMENTO=?";

			for (ProgettoEstrattoCampVO progetto : estratti) {
				getJdbcTemplate().update(esc, new Object[] { progetto.getIdProgetto(), progetto.getIdCampionamento() });
			}

		} catch (Exception e) {
			LOG.error("errore aggiornamento tabella PBANDI_T_CAMPIONAMENTO_FINP" + e);
			return false;
		}
		return true;
	}

	private boolean escludoProgetti(ArrayList<ProgettoEstrattoCampVO> esclusi, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: estrazioneProgetti -> escludoProgetti]";
		LOG.info(prf + "BEGIN");
		try {
			String esc = "update PBANDI_R_CAMP_PROGETTI set" + " flag_escludi='S',"
					+ " DT_AGGIORNAMENTO=trunc(sysdate)," + " ID_UTENTE_AGG=" + idUtente + "," + " motivo_esclusione=?"
					+ " where id_progetto= ?" + " and ID_CAMPIONAMENTO=?";

			for (ProgettoEstrattoCampVO progetto : esclusi) {
				getJdbcTemplate().update(esc, new Object[] { progetto.getMotivazione(), progetto.getIdProgetto(),
						progetto.getIdCampionamento() });
			}

		} catch (Exception e) {
			LOG.error("errore aggiornamento tabella PBANDI_R_CAMP_PROGETTI" + e);
			return false;
		}

		LOG.info(prf + "FINE");
		return true;
	}

	@Override
	public List<ProgettoEstrattoCampVO> campionaProgetti(NuovoCampionamentoVO nuovoCampVO, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: campionaPrpgetti]";
		LOG.info(prf + "BEGIN");

		List<ProgettoEstrattoCampVO> progetti = new ArrayList<ProgettoEstrattoCampVO>();

		// nel caso di "numero azienda" recupero dei progetti casulamente in base a
		// quelli estratti
		if (nuovoCampVO.getIdTipoCampionamento() == 1) {
			progetti = getProgettiDaNumeroDomanda(nuovoCampVO, idUtente);
		} else {
			progetti = getProgettiDaImportoValidato(nuovoCampVO, idUtente);
		}
		LOG.info(prf + "END");
		return progetti;
	}

	private List<ProgettoEstrattoCampVO> getProgettiDaImportoValidato(NuovoCampionamentoVO nuovoCampVO, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: campionaPrpgetti->getProgettiDaImportoValidato]";
		LOG.info(prf + "BEGIN");
		List<ProgettoEstrattoCampVO> progetti = new ArrayList<ProgettoEstrattoCampVO>();
		List<BigDecimal> progettiEstratti = new ArrayList<BigDecimal>();
		// nel caso di "importo validato" eseguo l'algoritmo per fare la sommma ecc
		try {

			StringBuilder sql = new StringBuilder();
			// questa parte mi serve per quanto riguarda eìnel caso di un estrazione con
			// tipo
			sql.append("WITH imp_validato AS (\r\n" + "    SELECT\r\n"
					+ "        SUM(prpqpds.IMPORTO_VALIDATO) AS imp_val,\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    FROM\r\n"
					+ "        PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqpds\r\n"
					+ "        JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON prpqpds.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "    GROUP BY\r\n" + "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    ORDER BY\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "),\r\n" + "imp_rendicontato AS (\r\n"
					+ "    SELECT\r\n" + "        SUM(prpqpds.IMPORTO_QUIETANZATO) AS imp_rend,\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    FROM\r\n"
					+ "        PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqpds\r\n"
					+ "        JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON prpqpds.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "    GROUP BY\r\n" + "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "    ORDER BY\r\n"
					+ "        prpqpds.ID_DICHIARAZIONE_SPESA\r\n" + "),\r\n" + "progetti AS (\r\n" + "    SELECT\r\n"
					+ "        sum(iv.imp_val) importo_val,\r\n" + "        SUM(ir.imp_rend) importo_rend,\r\n"
					+ "        prcp.ID_PROGETTO,\r\n" + "        prcp.ID_CAMPIONAMENTO \r\n" + "    FROM\r\n"
					+ "        PBANDI_R_CAMP_PROGETTI prcp\r\n"
					+ "        JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON ptds.ID_PROGETTO = prcp.ID_PROGETTO\r\n"
					+ "        LEFT JOIN imp_rendicontato ir ON ir.id_dichiarazione_spesa = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "        LEFT JOIN imp_validato iv ON iv.id_dichiarazione_spesa = ptds.ID_DICHIARAZIONE_SPESA\r\n"
					+ "        LEFT JOIN PBANDI_T_RIC_CAMP_DICH_SP ptrcds ON ptrcds.ID_CAMPIONAMENTO = prcp.ID_CAMPIONAMENTO\r\n"
					+ "    WHERE\r\n" + "        --prcp.ID_CAMPIONAMENTO = " + nuovoCampVO.getIdCampionamento() + "\r\n"
					+ "         ptds.DT_CHIUSURA_VALIDAZIONE IS NOT NULL\r\n" + "        AND prcp.FLAG_ESTRATTO ='S'");
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataRicezioneInizio() != null) {
				sql.append("  AND ptds.DT_DICHIARAZIONE >= ptrcds.DT_RICEZIONE_DA ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataRicezioneFine() != null) {
				sql.append("  AND ptds.DT_DICHIARAZIONE <= ptrcds.DT_RICEZIONE_A ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataUltimoEsitoInizio() != null) {
				sql.append("  AND ptds.DT_CHIUSURA_VALIDAZIONE >= ptrcds.DT_ULTIMO_ESITO_DA  ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getDataUltimoEsitoFine() != null) {
				sql.append(" AND ptds.DT_CHIUSURA_VALIDAZIONE <= ptrcds.DT_ULTIMO_ESITO_A ");
			}

			sql.append(" GROUP BY\r\n" + "        prcp.ID_PROGETTO, prcp.ID_CAMPIONAMENTO \r\n" + ")\r\n" + "SELECT\r\n"
					+ "    p.id_progetto,\r\n" + "    p.importo_val AS IMPORTO_VALIDATO,\r\n"
					+ "    p.ID_CAMPIONAMENTO \r\n" + "FROM\r\n" + "    progetti p\r\n"
					+ "    LEFT JOIN PBANDI_T_RIC_CAMP_DICH_SP ptrcds ON ptrcds.ID_CAMPIONAMENTO = p.id_campionamento\r\n"
					+ "WHERE\r\n" + "   p.id_campionamento= " + nuovoCampVO.getIdCampionamento() + "\r\n");
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImporRendicontatoInizio() != null) {
				sql.append(" AND ptrcds.IMP_RENDICONTATO_DA <= p.importo_rend");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImporRendicontatoFine() != null) {
				sql.append(" AND ptrcds.IMP_RENDICONTATO_A >= p.importo_rend ");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImportoValidatoInizio() != null) {
				sql.append(" AND ptrcds.IMP_VALIDATO_DA <= p.importo_val");
			}
			if (nuovoCampVO.getDichiarazioneSpesa() != null
					&& nuovoCampVO.getDichiarazioneSpesa().getImportoValidatoInizio() != null) {
				sql.append(" AND ptrcds.IMP_VALIDATO_A >= p.importo_val ");
			}

			sql.append("ORDER BY p.importo_val");

			RowMapper<ProgettoEstrattoCampVO> lista = new RowMapper<ProgettoEstrattoCampVO>() {
				@Override
				public ProgettoEstrattoCampVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ProgettoEstrattoCampVO progetto = new ProgettoEstrattoCampVO();
					progetto.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
					progetto.setImportoValidato(rs.getLong("IMPORTO_VALIDATO"));
					progetto.setIdCampionamento(rs.getBigDecimal("ID_CAMPIONAMENTO"));
					return progetto;
				}
			};

			progetti = getJdbcTemplate().query(sql.toString(), lista);

			Long sommaImporto = (long) 0.00;

			for (ProgettoEstrattoCampVO progettoEstrattoCampVO : progetti) {
				sommaImporto = sommaImporto + progettoEstrattoCampVO.getImportoValidato();
			}
			nuovoCampVO.setImportoValidato(sommaImporto);
			// aggiorno la percentuale
			sommaImporto = sommaImporto * nuovoCampVO.getPercEstrazione() / 100;
			Long importoTemp = (long)0;
			nuovoCampVO.setImportEstrattoValidato(sommaImporto);
			int i = 0;
			// faccio il ciclo per recuperare i progetti di cui la somma temp è inferiore a
			// la somma ridotta
			if (i < progetti.size()) {
				while (importoTemp < sommaImporto) {
					progettiEstratti.add(progetti.get(i).getIdProgetto());
					importoTemp = importoTemp + progetti.get(i).getImportoValidato();
					i++;
				}
			}

			
			// aggiorno la tabella PBANDI_T_CAMPIONAMENTO_FINP
			boolean isUpdateTCamp;
			nuovoCampVO.setNumProgettiSel((long) progettiEstratti.size());
			isUpdateTCamp = aggiornaTcampionamento(nuovoCampVO, idUtente);
			if (!isUpdateTCamp)
				LOG.info("errore aggiornamento pbandi_t_campionamento");

			// aggiorno i progetti dentro la tabella PBANDI_ R_CAMP_PROGETTI per ogni
			// progetto estratto
			for (BigDecimal progetto : progettiEstratti) {

				String updateProgetti = "update PBANDI_R_CAMP_PROGETTI set" + " flag_campionato = 'S',\r\n"
						+ " dt_aggiornamento = trunc(sysdate),\r\n" + " id_utente_agg=" + idUtente + "\r\n"
						+ " where id_campionamento = " + nuovoCampVO.getIdCampionamento() + "\r\n" + " and id_progetto="
						+ progetto;
				getJdbcTemplate().update(updateProgetti);
			}

			// reucupero id dati dei progetti scelti dopomestrazione;
			progetti = estrazioneProgetti(nuovoCampVO.getIdCampionamento(), progettiEstratti, true);

		} catch (Exception e) {
			LOG.error("errore" + e);
		}

		return progetti;
	}

	private List<ProgettoEstrattoCampVO> getProgettiDaNumeroDomanda(NuovoCampionamentoVO nuovoCampVO, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: campionaPrpgetti->getProgettiDaNumeroDomanda]";
		LOG.info(prf + "BEGIN");

		List<ProgettoEstrattoCampVO> elenco = new ArrayList<ProgettoEstrattoCampVO>();

		try {
			// recupero i progetti con flag_estratto =S ppoi ne prendo la percentuale
			// inserita
			// List<ProgettoEstrattoCampVO> estratti = new
			// ArrayList<ProgettoEstrattoCampVO>();
			List<BigDecimal> idProgetti = new ArrayList<BigDecimal>();
			List<BigDecimal> idProgettiRecuperati = new ArrayList<BigDecimal>();
			String getProgetti = "select prcp.id_progetto from PBANDI_R_CAMP_PROGETTI prcp\r\n"
					+ " where flag_estratto ='S' and id_campionamento=" + nuovoCampVO.getIdCampionamento();

			RowMapper<BigDecimal> lista = new RowMapper<BigDecimal>() {
				@Override
				public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
					BigDecimal idProgetto;
					idProgetto = rs.getBigDecimal("ID_PROGETTO");
					return idProgetto;
				}
			};

			idProgetti = getJdbcTemplate().query(getProgetti, lista);

			int dim = (int) (idProgetti.size() * nuovoCampVO.getPercEstrazione() / 100);
			nuovoCampVO.setNumProgettiEstratti((long) dim);
			
			// recupero da questo elenco il
			dim = dim-1; 
			while (dim >= 0) {
				idProgettiRecuperati.add(idProgetti.get(dim));
				dim--;
			}
			elenco = estrazioneProgetti(nuovoCampVO.getIdCampionamento(), idProgettiRecuperati, false);
			//nuovoCampVO.setNumProgettiSel((long)dim);
			//nuovoCampVO.setNumProgettiTotali((long) idProgetti.size());
			// aggiorno i progetti dentro la tabella PBANDI_ R_CAMP_PROGETT
			for (BigDecimal progetto : idProgettiRecuperati) {

				String updateProgetti = "update PBANDI_R_CAMP_PROGETTI set" + " flag_campionato = 'S',\r\n"
						+ " dt_aggiornamento = trunc(sysdate),\r\n" + " id_utente_agg=" + idUtente + "\r\n"
						+ " where id_campionamento = " + nuovoCampVO.getIdCampionamento() + "\r\n" + " and id_progetto="
						+ progetto;
				getJdbcTemplate().update(updateProgetti);
			}
			// aggiorno la fase di campionameto e recupero i progetti
			String updateTcamp = "update PBANDI_T_CAMPIONAMENTO_FINP set\r\n" 
					+ " id_fase_camp = 3, \r\n"
					+ " dt_aggiornamento = trunc(sysdate),\r\n" 
					+ " dt_campionamento= trunc(sysdate), \r\n"
					+ " perc_estratta=" + nuovoCampVO.getPercEstrazione() + ",\r\n" 
					+ " num_progetti_sel="+ idProgetti.size() + ",\r\n"
					+ " imp_val_perc_estratta=" + nuovoCampVO.getNumProgettiEstratti() + ",\r\n" 
					+ " id_utente_agg="+ idUtente + "\r\n" 
					+ " where id_campionamento=" + nuovoCampVO.getIdCampionamento();

			getJdbcTemplate().update(updateTcamp);

		} catch (Exception e) {
			LOG.error("errore" + e);
		}

		return elenco;
	}

	private boolean aggiornaTcampionamento(NuovoCampionamentoVO nuovoCampVO, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: campionaPrpgetti -> aggiornaTcampionamento]";
		LOG.info(prf + "BEGIN");

		try {
			String updateTcamp = "update PBANDI_T_CAMPIONAMENTO_FINP set\r\n" + " id_fase_camp = 3, \r\n"
					+ " dt_aggiornamento = trunc(sysdate),\r\n" + " dt_campionamento= trunc(sysdate), \r\n"
					+ " perc_estratta=" + nuovoCampVO.getPercEstrazione() + ",\r\n"
					// + " num_progetti_sel="+ nuovoCampVO.getNumProgetiSel() +",\r\n"
					+ " importo_validato=" + nuovoCampVO.getImportoValidato() + ",\r\n" 
					+ " imp_val_perc_estratta="+ nuovoCampVO.getImportEstrattoValidato() + ",\r\n" 
					+" id_utente_agg=" + idUtente + "\r\n"
					+ " where id_campionamento=" + nuovoCampVO.getIdCampionamento();

			getJdbcTemplate().update(updateTcamp);

		} catch (Exception e) {
			LOG.error( prf + "errore" + e);
		}

		return true;
	}
	
//	------------------ CONTROLLO IN LOCCO -------------------- CONTROLLO IN LOCCO -----------------

	@Override
	public Boolean creaControlloLoco(ProgettiEscludiEstrattiDTO progetti, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: creaControlloLocco";
		LOG.info(prf + "BEGIN");
		Boolean result= true; 
		
		try {
			String getId = "SELECT SEQ_PBANDI_T_CONTROLLO_LOCO.nextval  FROM dual";
			BigDecimal idControllo;
			
			// inserimento nella tabella PBANDI_T_CONTROLLO_LOCO  un nuovo controllo
			String sql = "insert into PBANDI_T_CONTROLLO_LOCO (id_controllo_loco, id_stato_contr_loco,"
					+ " dt_inizio_validita,"
					+ "id_categ_anagrafica,"
					+ " id_utente_ins,"
					+ " dt_inserimento, DT_AVVIO_CONTROLLI) values(?,1,trunc(sysdate), 4,?,trunc(sysdate), trunc(sysdate))";
			
			// per ogni progetto aggiungo un record nella tabella PBANDI_R_CAMP_CONTR_LOCO 
			
			String sql2 ="insert into PBANDI_R_CAMP_CONTR_LOCO (id_controllo_loco, id_campionamento, id_progetto)"
					+ "values(?,?,?)"; 
			for(ProgettoEstrattoCampVO progetto : progetti.getCampionati()) {
				
				idControllo= getJdbcTemplate().queryForObject(getId, BigDecimal.class);
				
				getJdbcTemplate().update(sql, new Object[] {
						idControllo, idUtente
				});
				
				getJdbcTemplate().update(sql2, new Object[] {
						idControllo, 
						progetto.getIdCampionamento(), 
						progetto.getIdProgetto()
				});
			}
			
			
		} catch (Exception e) {
			LOG.error(prf+"errore" + e);
			result = false;
		}
		
		return result;
	}

	@Override
	public Boolean annullaCampionamento(Long idCampionamento, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: annullaCampionamento";
		LOG.info(prf + "BEGIN");
		
		try {
			
			String annulla ="update PBANDI_T_CAMPIONAMENTO_FINP set "
					+ " id_utente_agg =" + idUtente +",\r\n"
					+ " dt_aggiornamento= trunc(sysdate),\r\n"
					+ " flag_annullato= 'S'\r\n"
					+ " where id_campionamento=" + idCampionamento;
			
			getJdbcTemplate().update(annulla); 
			
		} catch (Exception e) {
			LOG.error(prf + "errore" + e);
			return false;
		}
		
		return true;
	}

	// ------------ IMPORT PROGETTI CAMPIONAMENTI FINPIEMONTE ------------------
	
	@Override
	public Object getNormative(String suggest) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		
		List<NormativaVO> elenco= new ArrayList<NormativaVO>();
		
		StringBuilder query= new StringBuilder();
			query.append("SELECT\r\n"
				+ "    t.ID_LINEA_DI_INTERVENTO,\r\n"
				+ "    DESC_LINEA,\r\n"
				+ "    prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "FROM\r\n"
				+ "    pbandi_d_linea_di_intervento t\r\n"
				+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_LINEA_DI_INTERVENTO = t.ID_LINEA_DI_INTERVENTO\r\n"
				+ "WHERE\r\n"
				+ "    id_linea_di_intervento_padre IS NULL\r\n"
				+ "    AND id_tipo_linea_intervento = 1\r\n"
				+ "    AND FLAG_CAMPIONAMENTO_ESTERNO = 'S'");
		
		if(suggest!=null && suggest.trim().length()>0) {
			query.append(" AND UPPER( t.DESC_LINEA) LIKE UPPER ('%"+suggest+"%') ");
		}
		
		RowMapper<NormativaVO> lista = new RowMapper<NormativaVO>() {

			@Override
			public NormativaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				NormativaVO norm = new NormativaVO();
				norm.setDescLinea(rs.getString("DESC_LINEA"));
				norm.setIdLineaIntervento(rs.getLong("ID_LINEA_DI_INTERVENTO"));
				norm.setProgrBandoLineaIntervento(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
				return norm;
			}
		};
		
		try {
			
			elenco = getJdbcTemplate().query(query.toString(), lista); 
			
		} catch (Exception e) {
			LOG.error(prf + "errore" + e);
		}
		
		return elenco;
	}

	@Override
	@Transactional
	public ElenchiProgettiCampionamentoVO acquisisciProgetti(FiltroAcqProgettiVO filtro, ArrayList<String>  elencoProgetti) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		ElenchiProgettiCampionamentoVO elenco = new ElenchiProgettiCampionamentoVO(); 
		try {
			/// 
			
			String progettiScartati =""; 
			
			// devo controllare che per ogni progetto esisti almeno sia nella pbandi_t_progeto e corrisponda al bando linea
			ArrayList<ProgettoCampioneVO> progettiDaAggiungere = new ArrayList<ProgettoCampioneVO>(); 
			for (String codVisProgetto : elencoProgetti) {
				ProgettoCampioneVO progetto =  new ProgettoCampioneVO();
				try {
				 progetto = getPrgettoAcquisito(codVisProgetto, filtro.getIdBandoLineaIntervent());
				 if(progetto != null) {
					 progettiDaAggiungere.add(progetto);
				 } else {
					 progettiScartati += codVisProgetto + ";"; 
				 }
				
				} catch (Exception e) {
					LOG.error(prf + "errore" + e);
				}
				
			}
			 elenco.setProgettiScartati(progettiScartati);
			 elenco.setProgettiDaAggiungere(progettiDaAggiungere);
		
			
		} catch (Exception e) {
			LOG.error(prf + "errore" + e);
		}
		
		return elenco;
	}
	private ProgettoCampioneVO getPrgettoAcquisito(String codVisProgetto, Long idBandoLineaIntervent){
		
		ProgettoCampioneVO progetto =  new ProgettoCampioneVO();
		
		try {
			String checkProgetto = "SELECT ptd.PROGR_BANDO_LINEA_INTERVENTO, ptp.TITOLO_PROGETTO, prbli.NOME_BANDO_LINEA, prsp.PROGR_SOGGETTO_PROGETTO , \r\n"
					+ "pdldi.DESC_LINEA, ptp.ID_PROGETTO, ptp.CODICE_VISUALIZZATO , pteg.DENOMINAZIONE_ENTE_GIURIDICO , ptpf.NOME , ptpf.COGNOME,\r\n"
					+ "    prsp.ID_SOGGETTO  \r\n"
					+ "FROM pbandi_r_soggetto_progetto prsp \r\n"
					+ "LEFT JOIN pbandi_t_progetto ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL \r\n"
					+ "LEFT JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA  = ptp.ID_DOMANDA \r\n"
					+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO=ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "LEFT JOIN PBANDI_D_LINEA_DI_INTERVENTO pdldi ON  prbli.ID_LINEA_DI_INTERVENTO = pdldi.ID_LINEA_DI_INTERVENTO \r\n"
					+ "LEFT JOIN pbandi_t_ente_giuridico pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO \r\n"
					+ "LEFT JOIN pbandi_t_persona_fisica ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA \r\n"
					+ "WHERE prsp.ID_TIPO_BENEFICIARIO <>4 \r\n"
					+ "AND prsp.ID_TIPO_ANAGRAFICA =1\r\n"
					+ "AND  ptp.CODICE_VISUALIZZATO =:codVisProgetto\r\n"
					+ "AND pdldi.ID_LINEA_DI_INTERVENTO =:idBandoLineaIntervent";
			
			Object[] args = new Object[]{codVisProgetto,idBandoLineaIntervent};
			int[] types = new int[]{Types.CHAR, Types.INTEGER };
			
			progetto = getJdbcTemplate().queryForObject(checkProgetto,args, types,new ProgettoCampioneRowmapper());
			
		} catch (Exception e) {
			LOG.error("progetto inesistente o non compatibile con il bando");
			return null; 
		}
		
		
		return progetto; 
	}

	@Override
	public Long checkCampionamento(Long numCampionamento) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		Long idCampionamento = null; 
		
		try {
			
			idCampionamento = getJdbcTemplate().queryForObject("SELECT ptcf.ID_CAMPIONAMENTO \r\n"
					+ "				FROM PBANDI_T_CAMPIONAMENTO_FINP ptcf\r\n"
					+ "				WHERE ptcf.NUM_CAMPIONAMENTO_ESTERNO = ? \r\n"
					+ "				AND ptcf.FLAG_CAMPIONAMENTO_ESTERNO ='S'\r\n"
					+ "				AND rownum =1", Long.class, new Object[] {numCampionamento}); 
			
		} catch (Exception e) {
			LOG.error("campionamento non esistente"+ e );
			return null; 
		}
		return idCampionamento;
	}

	@Override
	@Transactional
	public Object confermaAcquisizione(List<Long> progettiDaConfermare, FiltroAcqProgettiVO filtro,Long idUtente) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		EsitoDTO esito = new EsitoDTO(); 
//		HttpServletRequest request = new  
//		UserInfoSec userInfoSec = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {
			BigDecimal idCampionamento= null; 
			/// INSERIMENTO DATI NELLA TABELLA PBANDI_T_CAMPIONAMENTO_FINP
			idCampionamento=  insertCampionamentoFinp(filtro, idUtente);
			if(idCampionamento==null) {
				esito.setEsito(false);
				esito.setMessaggio("errore durante acquisizione progetti");
				return esito; 
			}
			// SALVATAGGIO PROGETTI DENTRO DA CONCLUDERE dentro la tabella
			// PBANDI_R_CAMP_PROGETTI
		   // BigDecimal	idUtente2= new BigDecimal(idUtente);
			boolean esitoSalvaProgetti;
			esitoSalvaProgetti = salvaProgettiEstratti(progettiDaConfermare, idCampionamento,new BigDecimal(idUtente));
			if (!esitoSalvaProgetti)
			LOG.info("errore salvataggio progetti dentro tabella PBANDI_R_CAMP_PROGETTI");
			
			// CREAZIONE CONTROLLI 
			Boolean esitoInsControlli = creaControlloLocoFinp(progettiDaConfermare, idCampionamento, new BigDecimal(idUtente));
			if (!esitoInsControlli)
				LOG.info("errore salvataggio progetti dentro tabella PBANDI_R_CAMP_PROGETTI");

			
			esito.setEsito(true);
			esito.setMessaggio("acquisizione avvenuta con successo!");
		} catch (Exception e) {
			LOG.error("errore acquisizione progetti: " + e);
			esito.setEsito(false);
			esito.setMessaggio("errore durante acquisizione progetti");
			return esito;
		}
		
		return esito;
	}
	
	private BigDecimal insertCampionamentoFinp(FiltroAcqProgettiVO nuovoCampVO, Long idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: elaboraNuovoCampionamento -> insertCampionamento]";
		LOG.info(prf + "BEGIN");
		BigDecimal idCamp;
		try {
			String getId = "SELECT SEQ_PBANDI_T_CAMPIONAMENT_FINP.nextval  FROM dual";
			idCamp = getJdbcTemplate().queryForObject(getId, BigDecimal.class);
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			String dataAbba = (nuovoCampVO.getDataCampione()!=null)?  dt.format(nuovoCampVO.getDataCampione()): null; 
			String insertCcampionamento = "insert into PBANDI_T_CAMPIONAMENTO_FINP("
					+ "ID_CAMPIONAMENTO,\r\n"
					+ "ID_FASE_CAMP,\r\n" 
					+ "ID_TIPO_CAMP,\r\n" 
					+ "DESC_CAMPIONAMENTO,\r\n" 
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO,\r\n" 
					+ "DT_INIZIO_VALIDITA , NUM_CAMPIONAMENTO_ESTERNO, FLAG_CAMPIONAMENTO_ESTERNO,\r\n"
					+ "DT_CAMPIONAMENTO)\r\n" 
					+ " values(?,3,1,?,?,trunc(sysdate), trunc(sysdate), ?, 'S',?)";

			getJdbcTemplate().update(insertCcampionamento, new Object[] { idCamp,
					nuovoCampVO.getDescCampionamento(), idUtente, nuovoCampVO.getNumCampionamento(), 
					(dataAbba!=null && dataAbba.trim().length()>0)?Date.valueOf(dataAbba): null,});

		} catch (Exception e) {
			LOG.error("errore inserimento campionamento" + e);
			return null;
		}

		return idCamp;
	}

	private boolean salvaProgettiEstratti(List<Long> progettiDaConfermare, BigDecimal idNuovoCampionamento,
			BigDecimal idUtenteIns) {
		String prf = "[NuovoCampionamentoDAOImpl::elaboraNuovoCampionamento -> salvaProgettiEstratti]";
		LOG.info(prf + "BEGIN");

		try {
			String insert = " INSERT INTO\r\n"
					+ "    PBANDI_R_CAMP_PROGETTI (ID_CAMPIONAMENTO,\r\n"
					+ "     ID_PROGETTO,\r\n"
					+ "     DT_INSERIMENTO,\r\n"
					+ "     DT_INIZIO_VALIDITA,\r\n"
					+ "     ID_UTENTE_INS\r\n"
					+ "     )\r\n"
					+ "VALUES(?,?, sysdate, sysdate,?)";

			for (Long progetto : progettiDaConfermare) {
				getJdbcTemplate().update(insert,
						new Object[] { idNuovoCampionamento, progetto, idUtenteIns });
			}

		} catch (Exception e) {
			LOG.error("errore salvataggio PBANDI_R_CAMP_PROGETTI" + e);
			return false;
		}
		return true;
	}
	
	
	private Boolean creaControlloLocoFinp( List<Long> progettiDaConfermare, BigDecimal idNuovoCampionamento, BigDecimal idUtente) {
		String prf = "[NuovoCampionamentoDAOImpl:: creaControlloLocco";
		LOG.info(prf + "BEGIN");
		Boolean result= true; 
		
		try {
			String getId = "SELECT SEQ_PBANDI_T_CONTROLLO_LOCO.nextval  FROM dual";
			BigDecimal idControllo;
			
			// inserimento nella tabella PBANDI_T_CONTROLLO_LOCO  un nuovo controllo
			String sql = "insert into PBANDI_T_CONTROLLO_LOCO (id_controllo_loco, id_stato_contr_loco,"
					+ " dt_inizio_validita,"
					+ "id_categ_anagrafica,"
					+ " id_utente_ins,"
					+ " dt_inserimento) values(?,1,trunc(sysdate), 4,?,trunc(sysdate))";
			
			// per ogni progetto aggiungo un record nella tabella PBANDI_R_CAMP_CONTR_LOCO 
			
			String sql2 ="insert into PBANDI_R_CAMP_CONTR_LOCO (id_controllo_loco, id_campionamento, id_progetto)"
					+ "values(?,?,?)"; 
			for(Long progetto : progettiDaConfermare) {
				
				idControllo= getJdbcTemplate().queryForObject(getId, BigDecimal.class);
				
				getJdbcTemplate().update(sql, new Object[] {
						idControllo, idUtente
				});
				
				getJdbcTemplate().update(sql2, new Object[] {
						idControllo, 
						idNuovoCampionamento, 
						progetto
				});
			}
			
			
		} catch (Exception e) {
			LOG.error(prf + "errore" + e);
			result = false;
		}
		
		return result;
	}

}
