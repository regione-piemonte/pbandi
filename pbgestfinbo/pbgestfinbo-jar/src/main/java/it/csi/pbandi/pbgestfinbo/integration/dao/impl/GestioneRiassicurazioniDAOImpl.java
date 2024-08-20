/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneRiassicurazioniDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.GaranziaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.ModificaEscussioneRiassicurazioniDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.Riassicurazione_BeneficiarioDomandaVO;
//import it.csi.pbandi.pbgestfinbo.integration.vo.Riassicurazione_ProgettiAssociatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.Riassicurazione_RiassicurazioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.Riassicurazione_SoggettiCorrelatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaDatiAnagraficiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.initGestioneEscussioneRiassicurazioniVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GestioneRiassicurazioniDAOImpl extends JdbcDaoSupport implements GestioneRiassicurazioniDAO {
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public GestioneRiassicurazioniDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public GestioneRiassicurazioniDAOImpl() {

	}

	@Autowired
	DocumentoManager documentoManager;

	// @Autowired
	// NeofluxBusinessImpl neofluxBusinessImpl;

	// Cerca Riassicurazioni //

	@Override
	public List<Riassicurazione_BeneficiarioDomandaVO> ricercaBeneficiariRiassicurazioni(String descrizioneBando,
			String codiceProgetto, String codiceFiscale, String ndg, String partitaIva, String denominazioneCognomeNome,
			String statoEscussione, String denominazioneBanca) {
		String prf = "[GestioneRiassicurazioniDAOImpl::ricercaBeneficiariRiassicurazioni]";
		LOG.info(prf + " BEGIN");

		List<Riassicurazione_BeneficiarioDomandaVO> riass_benefDom = new ArrayList<Riassicurazione_BeneficiarioDomandaVO>();
		StringBuilder query = new StringBuilder();

		String newDescBan = descrizioneBando.replaceAll("'", "''");
		String newDenom = denominazioneCognomeNome.replaceAll("'", "''");
		String newBanca = denominazioneBanca.replaceAll("'", "''");
		
		String tempTest = "L.R.34/04 FONDO REG. RIASS. PMI SET. AGR";

		// try {

		// Query tabella esterna - elenco beneficiari
		query.append("WITH denom AS (\r\n"
				+ "    SELECT pteg.ID_SOGGETTO, \r\n"
				+ "           pteg.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione, \r\n"
				+ "           prsp.ID_ENTE_GIURIDICO AS id_ente_giu, \r\n"
				+ "           NULL AS id_pers_fis\r\n"
				+ "    FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
				+ "    LEFT JOIN pbandi_t_ente_giuridico pteg  ON prsp.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO \r\n"
				+ "    WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "    UNION \r\n"
				+ "    SELECT ptpf.ID_SOGGETTO, \r\n"
				+ "           CONCAT(ptpf.COGNOME, CONCAT(' ', ptpf.NOME)) AS denominazione, \r\n"
				+ "           NULL AS id_ente_giu, \r\n"
				+ "           prsp.ID_PERSONA_FISICA AS id_pers_fis\r\n"
				+ "    FROM pbandi_t_persona_fisica ptpf\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PERSONA_FISICA  = ptpf.ID_PERSONA_FISICA \r\n"
				+ "    WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ ")\r\n"
				+ "	SELECT DISTINCT prsp.ID_SOGGETTO,\r\n"
				+ "	prsp.ID_PROGETTO,\r\n"
				+ "	( SELECT MAX(ID_ESCUSSIONE)\r\n"
				+ "    FROM PBANDI_T_ESCUSSIONE T2\r\n"
				+ "    WHERE T2.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
				+ "	) AS ID_ULTIMA_ESCUSSIONE,\r\n"
				+ "	denom.denominazione,\r\n"
				+ "	ptd.ID_DOMANDA,\r\n"
				+ "	ptd.NUMERO_DOMANDA,\r\n"
				+ "	ptd.ID_STATO_DOMANDA,\r\n"
				+ "	pdsd.DESC_BREVE_STATO_DOMANDA,\r\n"
				+ "	pdsd.DESC_STATO_DOMANDA,\r\n"
				+ "	ptb.TITOLO_BANDO,\r\n"
				+ "	NULL AS Importo_richiesto,\r\n"
				+ "	NULL AS Importo_ammesso\r\n"
				+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
				+ "	LEFT JOIN PBANDI_T_ESCUSS_CONFIDI ptec ON ptec.ID_PROGETTO = prsp.ID_PROGETTO  AND ptec.DT_FINE_VALIDITA IS NULL\r\n"
				+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO \r\n"
				+ "	LEFT JOIN denom ON denom.id_soggetto = prsp.ID_SOGGETTO  \r\n"
				+ "    AND ( (pts.ID_TIPO_SOGGETTO = 2 AND denom.id_ente_giu = prsp.ID_ENTE_GIURIDICO) OR\r\n"
				+ "        (pts.ID_TIPO_SOGGETTO = 1 AND denom.id_pers_fis = prsp.ID_PERSONA_FISICA) )\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO \r\n"
				+ "	LEFT JOIN pbandi_t_progetto ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO \r\n"
				+ "	LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
				+ "	LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON ptd.ID_STATO_DOMANDA = pdsd.ID_STATO_DOMANDA\r\n"
				+ "	LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT  prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO \r\n"
				+ "	LEFT JOIN pbandi_t_bando ptb ON ptb.ID_BANDO  = prbli.ID_BANDO \r\n"
				+ "	LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsp.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
				+ "    INNER JOIN PBANDI_T_RIASSICURAZIONI ptr ON ptr.ID_PROGETTO = ptp.ID_PROGETTO AND ptr.DATA_FINE IS NULL \r\n"
				+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO \r\n"
				+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI  = prspsc.PROGR_SOGGETTI_CORRELATI \r\n"
				+ "WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA  = 1 AND prsp.DT_FINE_VALIDITA IS NULL\r\n");
		
		// Solo per test
		//query.append("AND UPPER(ptb.TITOLO_BANDO) = UPPER('" + tempTest + "') \r\n");

		if (!"".equals(descrizioneBando)) {
			query.append("AND UPPER(ptb.TITOLO_BANDO) = UPPER('" + newDescBan + "') \r\n");
		}

		if (!"".equals(codiceProgetto)) {
			query.append("AND UPPER(ptp.CODICE_VISUALIZZATO) = UPPER('" + codiceProgetto + "') \r\n");
		}

		if (!"".equals(codiceFiscale)) {
			query.append("AND UPPER(pts.CODICE_FISCALE_SOGGETTO) = UPPER('" + codiceFiscale + "') \r\n");
		}

		if (!"".equals(ndg)) {
			query.append("AND UPPER(pts.ndg) = UPPER('" + ndg + "') \r\n");
		}

		/*if (!"".equals(partitaIva)) {
			query.append("AND UPPER(ptse.PARTITA_IVA) = UPPER('" + partitaIva + "') \r\n");
		}*/

		if (!"".equals(denominazioneCognomeNome)) {
			query.append("AND UPPER(denom.denominazione) = UPPER('" + newDenom + "') \r\n");
		}

		// if (!"".equals(statoEscussione)) {query.append("AND
		// UPPER(pdse.DESC_STATO_ESCUSSIONE) = UPPER('" + statoEscussione + "') \r\n");}

		/*if (!"".equals(denominazioneBanca)) {
			query.append("AND (UPPER(pdb.DESC_BANCA) = UPPER('" + newBanca + "') OR UPPER(pdb2.DESC_BANCA) = UPPER('" + newBanca + "')) \r\n");
		}*/

		query.append("AND rownum < 101");

		// LOG.debug(sql);

		riass_benefDom = getJdbcTemplate().query(query.toString(),
				new RowMapper<Riassicurazione_BeneficiarioDomandaVO>() {
					@Override
					public Riassicurazione_BeneficiarioDomandaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						Riassicurazione_BeneficiarioDomandaVO rowRiass_benefDom = new Riassicurazione_BeneficiarioDomandaVO();

						rowRiass_benefDom.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						rowRiass_benefDom.setIdProgetto(rs.getLong("ID_PROGETTO"));
						rowRiass_benefDom.setIdUltimaEscussione(rs.getLong("ID_ULTIMA_ESCUSSIONE"));

						rowRiass_benefDom.setDenominazione(rs.getString("DENOMINAZIONE"));
						rowRiass_benefDom.setIdDomanda(rs.getLong("ID_DOMANDA"));
						rowRiass_benefDom.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
						rowRiass_benefDom.setIdStatoDomanda(rs.getLong("ID_STATO_DOMANDA"));
						rowRiass_benefDom.setDescBreveStatoDomanda(rs.getString("DESC_BREVE_STATO_DOMANDA"));
						rowRiass_benefDom.setDescStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
						rowRiass_benefDom.setTitoloBando(rs.getString("TITOLO_BANDO"));
						rowRiass_benefDom.setImportoRichiesto(rs.getBigDecimal("IMPORTO_RICHIESTO"));
						rowRiass_benefDom.setImportoAmmesso(rs.getBigDecimal("IMPORTO_AMMESSO"));

						return rowRiass_benefDom;
					}
				});

		// Trovo i progetti associati ai beneficiari estratti sopra
		/*
		 * if(riass_benefDom.size() > 0) {
		 * 
		 * for(Riassicurazione_BeneficiarioDomandaVO itemRiass_benef: riass_benefDom) {
		 * 
		 * StringBuilder queryProgAss = new StringBuilder();
		 * 
		 * queryProgAss.append("SELECT\r\n" + "    DISTINCT sopro.ID_PROGETTO,\r\n" +
		 * "    sopro.ID_SOGGETTO,\r\n" + "    ptb.ID_BANDO,\r\n" +
		 * "    sopro.PROGR_SOGGETTO_PROGETTO,\r\n" + "    pro.CODICE_VISUALIZZATO,\r\n"
		 * + "    pte.ID_ESCUSSIONE ,\r\n" +
		 * "    modag.ID_MODALITA_AGEVOLAZIONE AS ID_MODALITA_AGEVOLAZIONE_ORIG,\r\n" +
		 * "    modag.DESC_BREVE_MODALITA_AGEVOLAZ AS DESC_BREVE_MOD_AG_ORIG,\r\n" +
		 * "    modag.DESC_MODALITA_AGEVOLAZIONE AS DESC_MOD_AG_ORIG,\r\n" +
		 * "    modag.ID_MODALITA_AGEVOLAZIONE_RIF,\r\n" +
		 * "    modag2.DESC_MODALITA_AGEVOLAZIONE AS DESC_MODALITA_AGEVOLAZIONE_RIF,\r\n"
		 * + "    modag2.DESC_BREVE_MODALITA_AGEVOLAZ AS DESC_BREVE_MOD_AG_RIF,\r\n" +
		 * "    (\r\n" + "        SELECT\r\n" +
		 * "            SUM(IMPORTO_FINANZIAMENTO_BANCA)\r\n" + "        FROM\r\n" +
		 * "            PBANDI_R_SOGGETTO_PROGETTO a,\r\n" +
		 * "            PBANDI_R_SOGGETTO_DOMANDA b,\r\n" +
		 * "            PBANDI_T_CONTO_ECONOMICO c\r\n" +
		 * "            JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = c.ID_CONTO_ECONOMICO\r\n"
		 * + "        WHERE\r\n" + "            a.ID_PROGETTO = sopro.ID_PROGETTO\r\n" +
		 * "            AND a.ID_SOGGETTO = sopro.ID_SOGGETTO\r\n" +
		 * "            AND a.id_tipo_anagrafica = 1\r\n" +
		 * "            AND a.id_tipo_beneficiario <> 4\r\n" +
		 * "            AND a.PROGR_SOGGETTO_DOMANDA = b.PROGR_SOGGETTO_DOMANDA\r\n" +
		 * "            AND b.ID_DOMANDA = c.ID_DOMANDA\r\n" +
		 * "            AND c.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO\r\n" +
		 * "            AND prcema.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
		 * + "            AND c.DT_FINE_VALIDITA IS NULL\r\n" +
		 * "    ) AS totale_banca,\r\n" + "    (\r\n" + "        select\r\n" +
		 * "            SUM(ptrce.IMPORTO_AMMESSO_FINANZIAMENTO)\r\n" +
		 * "        FROM\r\n" + "            PBANDI_T_RIGO_CONTO_ECONOMICO ptrce,\r\n" +
		 * "            PBANDI_T_CONTO_ECONOMICO ptce,\r\n" +
		 * "            PBANDI_T_PROGETTO ptp\r\n" + "        WHERE\r\n" +
		 * "            ptrce.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO\r\n" +
		 * "            AND ptce.ID_DOMANDA = ptp.ID_DOMANDA\r\n" +
		 * "            AND ptce.DT_FINE_VALIDITA IS NULL\r\n" +
		 * "            AND ptp.ID_PROGETTO = sopro.ID_PROGETTO\r\n" +
		 * "    ) AS totale_ammesso\r\n" + "FROM\r\n" +
		 * "    PBANDI_R_SOGGETTO_PROGETTO sopro\r\n" +
		 * "    LEFT JOIN PBANDI_T_PROGETTO pro ON sopro.ID_PROGETTO = pro.ID_PROGETTO\r\n"
		 * +
		 * "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO coneco ON pro.ID_DOMANDA = coneco.ID_DOMANDA\r\n"
		 * + "    AND coneco.DT_FINE_VALIDITA IS NULL\r\n" +
		 * "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV conecomodag ON coneco.ID_CONTO_ECONOMICO = conecomodag.ID_CONTO_ECONOMICO\r\n"
		 * +
		 * "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag ON conecomodag.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
		 * +
		 * "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag2 ON modag2.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE_RIF\r\n"
		 * + "    LEFT JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA = pro.ID_DOMANDA\r\n"
		 * +
		 * "    LEFT JOIN pbandi_r_bando_linea_intervent prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
		 * + "    LEFT JOIN pbandi_t_bando ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n" +
		 * "    INNER JOIN PBANDI_T_RIASSICURAZIONI ptr ON ptr.ID_PROGETTO = sopro.ID_PROGETTO AND ptr.DATA_FINE IS NULL\r\n"
		 * +
		 * "    LEFT JOIN PBANDI_T_ESCUSSIONE pte ON pte.ID_PROGETTO = sopro.ID_PROGETTO AND pte.DT_FINE_VALIDITA IS NULL \r\n"
		 * + "WHERE\r\n" + "    sopro.ID_TIPO_ANAGRAFICA = '1'\r\n" +
		 * "    AND sopro.ID_TIPO_BENEFICIARIO <> '4'\r\n" +
		 * "    AND sopro.DT_FINE_VALIDITA IS NULL\r\n" + "    AND sopro.id_soggetto = "
		 * + itemRiass_benef.getIdSoggetto() + "\r\n");
		 * 
		 * itemRiass_benef.setProgettiAssociati(getJdbcTemplate().query(queryProgAss.
		 * toString(), new RowMapper<Riassicurazione_ProgettiAssociatiVO>() {
		 * 
		 * @Override public Riassicurazione_ProgettiAssociatiVO mapRow(ResultSet rs, int
		 * rowNum) throws SQLException { Riassicurazione_ProgettiAssociatiVO
		 * rowRiass_progAss = new Riassicurazione_ProgettiAssociatiVO();
		 * 
		 * rowRiass_progAss.setIdProgetto(rs.getLong("ID_PROGETTO"));
		 * rowRiass_progAss.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		 * rowRiass_progAss.setIdBando(rs.getLong("ID_BANDO"));
		 * rowRiass_progAss.setProgrSoggettoProgetto(rs.getLong(
		 * "PROGR_SOGGETTO_PROGETTO"));
		 * rowRiass_progAss.setIdEscussione(rs.getLong("ID_ESCUSSIONE"));
		 * 
		 * rowRiass_progAss.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
		 * rowRiass_progAss.setIdModalitaAgevolazioneOrig(rs.getLong(
		 * "ID_MODALITA_AGEVOLAZIONE_ORIG"));
		 * rowRiass_progAss.setDescBreveModalitaAgevolazioneOrig(rs.getString(
		 * "DESC_BREVE_MOD_AG_ORIG"));
		 * rowRiass_progAss.setDescModalitaAgevolazioneOrig(rs.getString(
		 * "DESC_MOD_AG_ORIG"));
		 * rowRiass_progAss.setIdModalitaAgevolazioneRif(rs.getLong(
		 * "ID_MODALITA_AGEVOLAZIONE_RIF"));
		 * rowRiass_progAss.setDescBreveModalitaAgevolazioneRif(rs.getString(
		 * "DESC_BREVE_MOD_AG_RIF"));
		 * rowRiass_progAss.setDescModalitaAgevolazioneRif(rs.getString(
		 * "DESC_MODALITA_AGEVOLAZIONE_RIF"));
		 * rowRiass_progAss.setTotaleBanca(rs.getBigDecimal("TOTALE_BANCA"));
		 * rowRiass_progAss.setTotaleAmmesso(rs.getBigDecimal("TOTALE_AMMESSO"));
		 * 
		 * return rowRiass_progAss; } }));
		 */

		// Trovo l'elenco dei soggetti correlati alla domanda
		if (riass_benefDom.size() > 0) {

			for (Riassicurazione_BeneficiarioDomandaVO itemRiass_benef : riass_benefDom) {

				StringBuilder querySoggCorr = new StringBuilder();

				querySoggCorr.append("WITH dati_soggetto AS (\r\n" + "    SELECT prsd2.ID_SOGGETTO,\r\n"
						+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO AS desc1,\r\n" + "        '' AS desc2,\r\n"
						+ "        prsd2.ID_ENTE_GIURIDICO,\r\n" + "        prsd2.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    FROM PBANDI_R_SOGGETTO_PROGETTO prsd2,\r\n" + "        PBANDI_T_ENTE_GIURIDICO pteg\r\n"
						+ "    WHERE PRSD2.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "        AND pteg.ID_ENTE_GIURIDICO = PRSD2.ID_ENTE_GIURIDICO\r\n" + ")\r\n"
						+ "SELECT DISTINCT prsp.ID_PROGETTO,\r\n" + "    prsp.ID_SOGGETTO,\r\n"
						+ "    --prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    --pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n" + "    ds.desc1,\r\n"
						+ "    ds.desc2,\r\n" + "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    --pdts.DESC_TIPO_SOGGETTO,\r\n" + "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n" + "    pts.ndg,\r\n"
						+ "    prsp.ID_ENTE_GIURIDICO AS id_ente,\r\n" + "    --pdtsc.FLAG_INDIPENDENTE\r\n"
						+ "    NULL AS stato_progetto,\r\n" + "    ptr.ID_RIASSICURAZIONE,\r\n"
						+ "    ptr.DATA_ESCUSSIONE,\r\n" + "    ptr.DATA_SCARICO,\r\n"
						+ "    NULL AS Importo_richiesto,\r\n" + "	NULL AS Importo_ammesso\r\n"
						+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO  = prspsc.PROGR_SOGGETTO_PROGETTO and prsp.DT_FINE_VALIDITA is null\r\n"
						+ "     JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    --LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
						+ "    --LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_T_RIASSICURAZIONI ptr ON prsp.ID_PROGETTO = ptr.ID_PROGETTO AND ptr.DATA_FINE IS NULL\r\n"
						+ "WHERE prsp.ID_TIPO_ANAGRAFICA <> 1\r\n" + "    --AND pdtsc.FLAG_INDIPENDENTE ='S'\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n" + "    AND prsp.ID_PERSONA_FISICA IS NULL\r\n"
						+ "    AND prsp.ID_PROGETTO = " + itemRiass_benef.getIdProgetto() + "\r\n" + "UNION ALL\r\n"
						+ "SELECT DISTINCT prsp.ID_PROGETTO,\r\n" + "    prsp.ID_SOGGETTO,\r\n"
						+ "    --prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    --pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n" + "    ptpf.COGNOME AS DESC1,\r\n"
						+ "    ptpf.NOME AS desc2,\r\n" + "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    --pdts.DESC_TIPO_SOGGETTO,\r\n" + "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n" + "    pts.ndg,\r\n"
						+ "    prsp.ID_PERSONA_FISICA AS id_ente,\r\n" + "    --pdtsc.FLAG_INDIPENDENTE \r\n"
						+ "    NULL AS stato_progetto,\r\n" + "    ptr.ID_RIASSICURAZIONE,\r\n"
						+ "    ptr.DATA_ESCUSSIONE,\r\n" + "    ptr.DATA_SCARICO,\r\n"
						+ "    NULL AS Importo_richiesto,\r\n" + "	NULL AS Importo_ammesso\r\n"
						+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO and prsp.DT_FINE_VALIDITA is null \r\n"
						+ "    JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    --LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN PBANDI_T_RIASSICURAZIONI ptr ON prsp.ID_PROGETTO = ptr.ID_PROGETTO AND ptr.DATA_FINE IS NULL\r\n"
						+ "WHERE prsp.ID_TIPO_ANAGRAFICA <> 1\r\n" + "   -- AND pdtsc.FLAG_INDIPENDENTE = 'S'\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n" + "    AND prsp.ID_ENTE_GIURIDICO IS NULL\r\n"
						+ "    AND prsp.ID_PROGETTO = " + itemRiass_benef.getIdProgetto() + "\r\n");

				itemRiass_benef.setSoggettiCorrelati(getJdbcTemplate().query(querySoggCorr.toString(),
						new RowMapper<Riassicurazione_SoggettiCorrelatiVO>() {

							@Override
							public Riassicurazione_SoggettiCorrelatiVO mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Riassicurazione_SoggettiCorrelatiVO rowRiass_soggCorr = new Riassicurazione_SoggettiCorrelatiVO();

								rowRiass_soggCorr.setIdProgetto(rs.getLong("ID_PROGETTO"));
								rowRiass_soggCorr.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
								rowRiass_soggCorr.setNdg(rs.getString("NDG"));
								rowRiass_soggCorr.setIdEnte(rs.getLong("ID_ENTE"));
								rowRiass_soggCorr.setProgrSoggettiCorrelati(rs.getLong("PROGR_SOGGETTI_CORRELATI"));
								rowRiass_soggCorr.setProgrSoggettoDomanda(rs.getLong("PROGR_SOGGETTO_DOMANDA"));
								rowRiass_soggCorr.setIdRiassicurazione(rs.getLong("ID_RIASSICURAZIONE"));

								rowRiass_soggCorr.setNome1(rs.getString("DESC1"));
								rowRiass_soggCorr.setNome2(rs.getString("DESC2"));
								rowRiass_soggCorr.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
								rowRiass_soggCorr.setStatoProgetto(rs.getString("STATO_PROGETTO"));
								rowRiass_soggCorr.setDataEscussione(rs.getDate("DATA_ESCUSSIONE"));
								rowRiass_soggCorr.setDataScarico(rs.getDate("DATA_SCARICO"));
								rowRiass_soggCorr.setImportoRichiesto(rs.getBigDecimal("IMPORTO_RICHIESTO"));
								rowRiass_soggCorr.setImportoAmmesso(rs.getBigDecimal("IMPORTO_AMMESSO"));

								return rowRiass_soggCorr;
							}
						}));
			}
		}
		// }
		// }

		/*
		 * } catch (Exception e) { LOG.
		 * info("Error in ricercaRiassicurazioni while trying to get the fucking riassicurazioni with RiassicurazioneVO: "
		 * + e); riass = new ArrayList<>(); }
		 */

		LOG.info(prf + " END");

		return riass_benefDom;
	}

	@Override
	public List<Riassicurazione_RiassicurazioniVO> getDettaglioRiassicurazioni(Long idProgetto, Long idRiassicurazione, Boolean getStorico) {
		String prf = "[GestioneRiassicurazioniDAOImpl::getDettaglioRiassicurazioni]";
		LOG.info(prf + " BEGIN");

		List<Riassicurazione_RiassicurazioniVO> riass_riass = new ArrayList<Riassicurazione_RiassicurazioniVO>();
		StringBuilder query = new StringBuilder();

		query.append("SELECT *\r\n" + "FROM PBANDI_T_RIASSICURAZIONI ptr\r\n"
				+ "WHERE DATA_FINE IS NULL AND ID_PROGETTO = " + idProgetto);


		riass_riass = getJdbcTemplate().query(query.toString(), new RowMapper<Riassicurazione_RiassicurazioniVO>() {
			@Override
			public Riassicurazione_RiassicurazioniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Riassicurazione_RiassicurazioniVO rowRiass_riass = new Riassicurazione_RiassicurazioniVO();

				rowRiass_riass.setIdProgetto(rs.getLong("ID_PROGETTO"));
				rowRiass_riass.setIdRiassicurazione(rs.getLong("ID_RIASSICURAZIONE"));

				rowRiass_riass.setLineaInterventoSogg(rs.getString("LINEA_INTERVENTO_SOGGETTO"));
				rowRiass_riass.setRagioneSociale(rs.getString("RAGIONE_SOCIALE"));
				rowRiass_riass.setFormaGiuridica(rs.getString("FORMA_GIURIDICA"));
				rowRiass_riass.setDescFormaGiuridica(rs.getString("DESCR_FORMA_GIURIDICA"));
				rowRiass_riass.setCodiceFiscale(rs.getString("CODICE_FISCALE"));
				rowRiass_riass.setIndirizzoSedeDestinatario(rs.getString("INDIRIZZO_SEDE_DESTINATARIA"));
				rowRiass_riass.setLocalitaSedeDestinatario(rs.getString("LOCALITA_SEDE_DESTINATARIA"));
				rowRiass_riass.setProvinciaSedeDestinatario(rs.getString("PROVINCIA_SEDE_DESTINATARIA"));
				rowRiass_riass.setAteco(rs.getString("ATECO"));
				rowRiass_riass.setDescAteco(rs.getString("DESCRIZIONE_ATECO"));
				rowRiass_riass.setImportoFinanziato(rs.getBigDecimal("IMPORTO_FINANZIATO"));
				rowRiass_riass.setImportoGaranzia(rs.getBigDecimal("IMPORTO_GARANZIA"));
				rowRiass_riass.setImportoAmmesso(rs.getBigDecimal("IMPORTO_AMMESSO"));
				rowRiass_riass.setImportoEscusso(rs.getBigDecimal("IMPORTO_ESCUSSO"));
				rowRiass_riass.setPercentualeGaranzia(rs.getBigDecimal("PERCENTUALE_GARANZIA"));
				rowRiass_riass.setPercentualeRiassicurato(rs.getBigDecimal("PERCENTUALE_RIASSICURATO"));
				rowRiass_riass.setDataErogazioneMutuo(rs.getDate("DT_EROGAZIONE_MUTUO"));
				rowRiass_riass.setDataDeliberaMutuo(rs.getDate("DT_DELIBERA_MUTUO"));
				rowRiass_riass.setDataEmissioneGaranzia(rs.getDate("DT_EMISSIONE_GARANZIA"));
				rowRiass_riass.setDataScadenzaMutuo(rs.getDate("DT_SCADENZA_MUTUO"));
				rowRiass_riass.setDataScarico(rs.getDate("DATA_SCARICO"));
				rowRiass_riass.setDataRevoca(rs.getDate("DATA_REVOCA"));

				rowRiass_riass.setDataInizioValidita(rs.getDate("DATA_INIZIO"));

				return rowRiass_riass;
			}
		});

		LOG.info(prf + " END");

		return riass_riass;
	}

	@Override
	public Boolean modificaRiassicurazione(Long idRiassicurazione, String importoEscusso, Date dataEscussione,
			Date dataScarico, Long idUtente) throws Exception {
		String prf = "[GestioneRiassicurazioniDAOImpl::modificaRiassicurazione]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " IdUtente: " + idUtente);
		LOG.info(prf + " idRiassicurazione: " + idRiassicurazione);
		LOG.info(prf + " importoEscusso: " + importoEscusso);
		LOG.info(prf + " dataEscussione: " + dataEscussione);
		LOG.info(prf + " dataScarico: " + dataScarico);

		Boolean esitoInsert = false;
		boolean esitoUpdate = false;

		if (importoEscusso == null) {
			importoEscusso = "0";
		}

		String query = "INSERT INTO PBANDI_T_RIASSICURAZIONI\r\n" + " (ID_RIASSICURAZIONE,\r\n"
				+ "	LINEA_INTERVENTO_SOGGETTO,\r\n" + "	RAGIONE_SOCIALE,\r\n" + "	FORMA_GIURIDICA,\r\n"
				+ "	DESCR_FORMA_GIURIDICA,\r\n" + "	CODICE_FISCALE,\r\n" + "	INDIRIZZO_SEDE_DESTINATARIA,\r\n"
				+ "	LOCALITA_SEDE_DESTINATARIA,\r\n" + "	PROVINCIA_SEDE_DESTINATARIA,\r\n" + "	ATECO,\r\n"
				+ "	DESCRIZIONE_ATECO,\r\n" + "	IMPORTO_FINANZIATO,\r\n" + "	ID_PROGETTO,\r\n"
				+ "	IMPORTO_GARANZIA,\r\n" + "	IMPORTO_AMMESSO,\r\n" + "	PERCENTUALE_GARANZIA,\r\n"
				+ "	PERCENTUALE_RIASSICURATO,\r\n" + "	DT_EROGAZIONE_MUTUO,\r\n" + "	DT_DELIBERA_MUTUO,\r\n"
				+ "	DT_EMISSIONE_GARANZIA,\r\n" + "	DT_SCADENZA_MUTUO,\r\n" + "	IMPORTO_ESCUSSO,\r\n"
				+ "	DATA_SCARICO,\r\n" + "	DATA_REVOCA,\r\n" + "	DATA_INIZIO,\r\n" + "	DATA_FINE,\r\n"
				+ "	ID_UTENTE_INS,\r\n" + "	ID_UTENTE_AGG,\r\n" + "	DATA_ESCUSSIONE)\r\n" + "SELECT \r\n"
				+ "	SEQ_PBANDI_T_RIASSICURAZIONI.NEXTVAL,\r\n" + "	LINEA_INTERVENTO_SOGGETTO,\r\n"
				+ "	RAGIONE_SOCIALE,\r\n" + "	FORMA_GIURIDICA,\r\n" + "	DESCR_FORMA_GIURIDICA,\r\n"
				+ "	CODICE_FISCALE,\r\n" + "	INDIRIZZO_SEDE_DESTINATARIA,\r\n" + "	LOCALITA_SEDE_DESTINATARIA,\r\n"
				+ "	PROVINCIA_SEDE_DESTINATARIA,\r\n" + "	ATECO,\r\n" + "	DESCRIZIONE_ATECO,\r\n"
				+ "	IMPORTO_FINANZIATO,\r\n" + "	ID_PROGETTO,\r\n" + "	IMPORTO_GARANZIA,\r\n"
				+ "	IMPORTO_AMMESSO,\r\n" + "	PERCENTUALE_GARANZIA,\r\n" + "	PERCENTUALE_RIASSICURATO,\r\n"
				+ "	DT_EROGAZIONE_MUTUO,\r\n" + "	DT_DELIBERA_MUTUO,\r\n" + "	DT_EMISSIONE_GARANZIA,\r\n"
				+ "	DT_SCADENZA_MUTUO,\r\n" + "	:importoEscusso,\r\n" + "	:dataScarico,\r\n" + "	DATA_REVOCA,\r\n"
				+ "	SYSDATE,\r\n" + "	NULL,\r\n" + "	:idUtente,\r\n" + "	NULL,\r\n" + "	:dataEscussione\r\n"
				+ "FROM PBANDI_T_RIASSICURAZIONI\r\n" + "WHERE ID_RIASSICURAZIONE = :idRiassicurazione";

		// LOG.debug(query);

		Object[] args = new Object[] { importoEscusso, dataScarico, idUtente, dataEscussione, idRiassicurazione };
		int[] types = new int[] { (importoEscusso != null ? Types.DECIMAL : Types.NULL), Types.DATE, Types.INTEGER,
				Types.DATE, Types.INTEGER };

		try {
			getJdbcTemplate().update(query, args, types);
			/*
			 * esitoInsert = getJdbcTemplate().execute(query,
			 * (PreparedStatementCallback<Boolean>) ps -> { ps.setString(1, importoEscusso);
			 * ps.setDate(2, dataScarico);
			 * 
			 * return ps.execute(); }) != null;
			 */

			// esitoInsert = true;

		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'inserire il record", e);
		}

		String queryUpdate = "UPDATE PBANDI_T_RIASSICURAZIONI\r\n"
				+ "SET DATA_FINE=SYSDATE, ID_UTENTE_AGG=:idUtente\r\n" + "WHERE ID_RIASSICURAZIONE=:idRiassicurazione";

		// LOG.debug(query);

		Object[] args2 = new Object[] { idUtente, idRiassicurazione };
		int[] types2 = new int[] { Types.INTEGER, Types.INTEGER };

		try {
			getJdbcTemplate().update(queryUpdate, args2, types2);

			esitoUpdate = true;

		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record precedente", e);
		}

		LOG.info(prf + " END");

		return true;
	}

	
	
	
	
	
	// Gestione Escussione Riassicurazioni //

	@Override
	public initGestioneEscussioneRiassicurazioniVO initGestioneEscussioneRiassicurazioni(Long idProgetto) {
		String prf = "[GestioneRiassicurazioniDAOImpl::initGestioneEscussioneRiassicurazioni]";
		LOG.info(prf + " BEGIN");

		//LOG.info(prf + " idEscussione: " + idEscussione);
		LOG.info(prf + " idProgetto: " + idProgetto);

		initGestioneEscussioneRiassicurazioniVO paginaEscussioneObj = new initGestioneEscussioneRiassicurazioniVO();

		try { // Ottengo i dati di testata e anagrafici

			paginaEscussioneObj.setSezioniTestataAndDatiAnagrafici(valorizzaTestataAndDatiAnagrafici(idProgetto));

		} catch (Exception e) {
			LOG.error(prf + "Exception in valorizzaTestataAndDatiAnagrafici()", e);
			throw e;
		}
		
		
		try { // Ottengo il dettaglio delle altre escussioni aperte o completate
			
			paginaEscussioneObj.setEscussioniPassate(valorizzaEscussioniPassate(idProgetto));
			
		} catch (Exception e) {
			LOG.error(prf + "Exception in valorizzaEscussioniPassate()", e);
			throw e;
		}

		

		try { // Ottengo il dettaglio dell'ultima escussione

			paginaEscussioneObj.setSezioneEscussione(valorizzaDatiUltimaEscussione(idProgetto));

		} catch (Exception e) {
			LOG.error(prf + "Exception in valorizzaDatiUltimaEscussione()", e);
			throw e;
		}
		

		try {
			
			paginaEscussioneObj.setLogicaEscussione(setLogicaEscussioneRiassicurazioni(paginaEscussioneObj.getEscussione_idTipoEscussione(), paginaEscussioneObj.getEscussione_idStatoEscussione(), idProgetto));
		
		} catch (Exception e) {
			LOG.error(prf + "Exception in setLogicaEscussione()", e);
			throw e;
		}
		

		LOG.info(prf + " END");

		return paginaEscussioneObj;
	}
	
	
	initGestioneEscussioneRiassicurazioniVO valorizzaTestataAndDatiAnagrafici(Long idProgetto) {
		List<initGestioneEscussioneRiassicurazioniVO> datiAnagraficiTemp = new ArrayList<initGestioneEscussioneRiassicurazioniVO>();
		
		String queryDatiAnag = " SELECT prsp.ID_SOGGETTO,\r\n"
				+ " 	ptp.ID_PROGETTO,\r\n"
				+ " 	prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
				+ " 	ptb.TITOLO_BANDO,\r\n"
				+ "	ptp.codice_visualizzato,\r\n"
				+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
				+ "	pts.PARTITA_IVA,\r\n"
				+ "	pdfg.DESC_FORMA_GIURIDICA,\r\n"
				+ "	pdts.desc_tipo_soggetto,\r\n"
				+ "	pts2.CODICE_FISCALE_SOGGETTO,\r\n"
				+ "	CASE WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.DESC_BANCA\r\n"
				+ "		ELSE pdb.DESC_BANCA\r\n"
				+ "	END AS desc_banca,\r\n"
				+ "	CASE WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.COD_BANCA\r\n"
				+ "		ELSE pdb.COD_BANCA\r\n"
				+ "	END AS COD_BANCA,\r\n"
				+ "	CASE WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.ID_BANCA\r\n"
				+ "		ELSE pdb.ID_BANCA\r\n"
				+ "	END AS id_banca\r\n"
				+ "FROM PBANDI_R_SOGG_PROGETTO_SEDE prsps\r\n"
				+ "LEFT JOIN PBANDI_T_SEDE pts ON prsps.ID_SEDE = pts.ID_SEDE\r\n"
				+ "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
				+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON prsp.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n"
				+ "LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pteg.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA\r\n"
				+ "LEFT JOIN PBANDI_T_SOGGETTO pts2 ON prsp.ID_SOGGETTO = pts2.ID_SOGGETTO\r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pts2.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO\r\n"
				+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptp.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento\r\n"
				+ "LEFT JOIN PBANDI_T_BANDO ptb ON prbli.ID_BANDO = ptb.ID_BANDO\r\n"
				+ "LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsp.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
				+ "LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA\r\n"
				+ "LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
				+ "LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA\r\n"
				+ "WHERE ptp.ID_PROGETTO = :idProgetto \r\n"
				+ "	AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "	AND prsp.ID_TIPO_BENEFICIARIO ^ = 4\r\n"
				+ "	AND prsps.ID_TIPO_SEDE = 1";
		Object[] args1 = new Object[] { idProgetto };
		int[] types1 = new int[] { Types.INTEGER };
		
		datiAnagraficiTemp = getJdbcTemplate().query(queryDatiAnag, args1, types1, new RowMapper<initGestioneEscussioneRiassicurazioniVO>() {
			@Override
			public initGestioneEscussioneRiassicurazioniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				initGestioneEscussioneRiassicurazioniVO datoAnag = new initGestioneEscussioneRiassicurazioniVO();

				datoAnag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
				datoAnag.setIdProgetto(rs.getLong("ID_PROGETTO"));
				datoAnag.setProgrSoggProg(rs.getLong("PROGR_SOGGETTO_PROGETTO"));

				datoAnag.setTestata_bando(rs.getString("TITOLO_BANDO"));
				datoAnag.setTestata_codProgetto(rs.getString("CODICE_VISUALIZZATO"));

				datoAnag.setDatiAnagrafici_beneficiario(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
				datoAnag.setDatiAnagrafici_codFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
				datoAnag.setDatiAnagrafici_partitaIva(rs.getString("PARTITA_IVA"));
				datoAnag.setDatiAnagrafici_formaGiuridica(rs.getString("DESC_FORMA_GIURIDICA"));
				datoAnag.setDatiAnagrafici_tipoAnagrafica(rs.getString("DESC_TIPO_SOGGETTO"));
				datoAnag.setDatiAnagrafici_denomBanca(rs.getString("DESC_BANCA"));

				return datoAnag;
			}
		});
		
		if (datiAnagraficiTemp.size() > 0) {
			return datiAnagraficiTemp.get(0);
		}
		return null;
	}
	
	List<initGestioneEscussioneRiassicurazioniVO> valorizzaEscussioniPassate(Long idProgetto) {
		List<initGestioneEscussioneRiassicurazioniVO> escussioniPassateTemp = new ArrayList<initGestioneEscussioneRiassicurazioniVO>();
		
		String queryDatiAnag = "-- recupero la lista delle escussioni passate o aperte, differisce dallo storico\r\n"
				+ "SELECT pte.ID_ESCUSSIONE,\r\n"
				+ "	pte.ID_STATO_ESCUSSIONE,\r\n"
				+ "	pte.ID_TIPO_ESCUSSIONE,\r\n"
				+ "	pdte.DESC_TIPO_ESCUSSIONE,\r\n"
				+ "	pdse.DESC_STATO_ESCUSSIONE,\r\n"
				+ "	pte.DT_RIC_RICH_ESCUSSIONE,\r\n"
				+ "	pte.DT_INIZIO_VALIDITA,\r\n"
				+ "	pte.DT_NOTIFICA,\r\n"
				+ "	pte.NUM_PROTOCOLLO_RICH,\r\n"
				+ "	pte.NUM_PROTOCOLLO_NOTIF,\r\n"
				+ "	pte.IMP_RICHIESTO,\r\n"
				+ "	pte.IMP_APPROVATO,\r\n"
				+ "	pte.CAUSALE_BONIFICO,\r\n"
				+ "	pte.NOTE,\r\n"
				+ "	pte.FLAG_ESITO\r\n"
				+ "FROM PBANDI_T_ESCUSSIONE pte\r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_ESCUSSIONE pdte ON pdte.ID_TIPO_ESCUSSIONE = pte.ID_TIPO_ESCUSSIONE\r\n"
				+ "LEFT JOIN PBANDI_D_STATO_ESCUSSIONE pdse ON pdse.ID_STATO_ESCUSSIONE = pte.ID_STATO_ESCUSSIONE\r\n"
				+ "WHERE pte.DT_FINE_VALIDITA IS NULL AND ID_PROGETTO = ? \r\n"
				+ "ORDER BY ID_ESCUSSIONE ASC ";
		Object[] args1 = new Object[] { idProgetto };
		int[] types1 = new int[] { Types.INTEGER };
		
		escussioniPassateTemp = getJdbcTemplate().query(queryDatiAnag, args1, types1, new RowMapper<initGestioneEscussioneRiassicurazioniVO>() {
			@Override
			public initGestioneEscussioneRiassicurazioniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				initGestioneEscussioneRiassicurazioniVO dettEscussione = new initGestioneEscussioneRiassicurazioniVO();

				dettEscussione.setEscussione_idEscussioneCorrente(rs.getLong("ID_ESCUSSIONE"));
				dettEscussione.setEscussione_idStatoEscussione(rs.getLong("ID_STATO_ESCUSSIONE"));
				dettEscussione.setEscussione_idTipoEscussione(rs.getLong("ID_TIPO_ESCUSSIONE"));
				dettEscussione.setEscussione_tipoEscussione(rs.getString("DESC_TIPO_ESCUSSIONE"));
				dettEscussione.setEscussione_statoEscussione(rs.getString("DESC_STATO_ESCUSSIONE"));
				dettEscussione.setEscussione_dataRicevimentoRichEscuss(rs.getDate("DT_RIC_RICH_ESCUSSIONE"));
				dettEscussione.setEscussione_dataStatoEscussione(rs.getDate("DT_INIZIO_VALIDITA"));
				dettEscussione.setEscussione_dataNotificaEscussione(rs.getDate("DT_NOTIFICA"));
				dettEscussione.setEscussione_numeroProtocolloRichiesta(rs.getString("NUM_PROTOCOLLO_RICH"));
				dettEscussione.setEscussione_numeroProtocolloNotifica(rs.getString("NUM_PROTOCOLLO_NOTIF"));
				dettEscussione.setEscussione_importoRichiesto(rs.getBigDecimal("IMP_RICHIESTO"));
				dettEscussione.setEscussione_importoApprovato(rs.getBigDecimal("IMP_APPROVATO"));
				dettEscussione.setEscussione_causaleBonifico(rs.getString("CAUSALE_BONIFICO"));
				//dettEscussione.setEscussione_ibanBanca(rs.getString("IBAN_BANCA_BENEF"));
				//dettEscussione.setEscussione_denomBanca(rs.getString("DESC_BANCA"));
				dettEscussione.setEscussione_note(rs.getString("NOTE"));

				String flag = rs.getString("FLAG_ESITO");
				// LOG.info(prf + " Flag esito: " + flag);
				if ("P".equals(flag)) {
					dettEscussione.setEsitoInviato(true);
				} else {
					dettEscussione.setEsitoInviato(false);
				}

				return dettEscussione;
			}
		});
		
		if (escussioniPassateTemp.size() > 0) {
			
			for (initGestioneEscussioneRiassicurazioniVO item : escussioniPassateTemp) {
				item.setLogicaEscussione(setLogicaEscussioneRiassicurazioni(item.getEscussione_idTipoEscussione(), item.getEscussione_idStatoEscussione(), idProgetto));
				item.setSezioniTestataAndDatiAnagrafici(valorizzaTestataAndDatiAnagrafici(idProgetto));
				item.setAllegatiInseriti(getAllegati(item.getEscussione_idEscussioneCorrente()));
			}
						
			
			return escussioniPassateTemp;
		}
		return null;
	}
	
	initGestioneEscussioneRiassicurazioniVO valorizzaDatiUltimaEscussione(Long idProgetto) {
		List<initGestioneEscussioneRiassicurazioniVO> datiEscussioneTemp = new ArrayList<initGestioneEscussioneRiassicurazioniVO>();
		
		StringBuilder queryEscussione = new StringBuilder();
		queryEscussione.append("SELECT DISTINCT\r\n"
				+ "	pte.ID_ESCUSSIONE,\r\n"
				+ "	ptb.ID_BANDO,\r\n"
				+ "	ptb.TITOLO_BANDO,\r\n"
				+ "	ptp.ID_PROGETTO,\r\n"
				+ "	ptp.CODICE_VISUALIZZATO,\r\n"
				+ "	prsp.ID_SOGGETTO,\r\n"
				+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
				+ "	pte.DT_RIC_RICH_ESCUSSIONE,\r\n"
				+ "	pte.ID_TIPO_ESCUSSIONE,\r\n"
				+ "	pdte.DESC_TIPO_ESCUSSIONE,\r\n"
				+ "	pte.ID_STATO_ESCUSSIONE,\r\n"
				+ "	pdse.DESC_STATO_ESCUSSIONE,\r\n"
				+ "	pte.DT_INIZIO_VALIDITA,\r\n"
				+ "	pte.IMP_RICHIESTO,\r\n"
				+ "	pte.IMP_APPROVATO,\r\n"
				+ "	pts.CODICE_FISCALE_SOGGETTO,\r\n"
				+ "	pts.ndg,\r\n"
				+ "	pts2.PARTITA_IVA,\r\n"
				+ "	pte.DT_INSERIMENTO,\r\n"
				+ "	pte.DT_NOTIFICA,\r\n"
				+ "	pte.NUM_PROTOCOLLO_RICH,\r\n"
				+ "	pte.NUM_PROTOCOLLO_NOTIF,\r\n"
				+ "	pte.CAUSALE_BONIFICO,\r\n"
				+ "	pte.IBAN_BONIFICO AS iban_banca_benef,\r\n"
				+ "	pteb.ID_BANCA,\r\n"
				+ "	pdb.COD_BANCA,\r\n"
				+ "	pdb.DESC_BANCA,\r\n"
				+ "	pte.NOTE,\r\n"
				+ "	prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
				+ "	pte.FLAG_ESITO\r\n"
				+ "FROM PBANDI_T_PROGETTO ptp\r\n"
				+ "	LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
				+ "	LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA\r\n"
				+ "	LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO\r\n"
				+ "	LEFT JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce ON pdsce.ID_STATO_CONTO_ECONOMICO = ptce.ID_STATO_CONTO_ECONOMICO\r\n"
				+ "	LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "	LEFT JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
				+ "	LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
				+ "	LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
				+ "	LEFT JOIN PBANDI_T_ESCUSSIONE pte ON ptp.ID_PROGETTO = pte.ID_PROGETTO\r\n"
				+ "	LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.IBAN = pte.IBAN_BONIFICO AND pteb.ID_BANCA IS NOT NULL\r\n"
				+ "	LEFT JOIN PBANDI_D_BANCA pdb ON pdb.ID_BANCA = pteb.ID_BANCA\r\n"
				+ "	LEFT JOIN PBANDI_D_TIPO_ESCUSSIONE pdte ON pdte.ID_TIPO_ESCUSSIONE = pte.ID_TIPO_ESCUSSIONE\r\n"
				+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
				+ "	LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
				+ "	LEFT JOIN PBANDI_T_SEDE pts2 ON pts2.ID_SEDE = prsps.ID_SEDE\r\n"
				+ "	LEFT JOIN PBANDI_D_STATO_ESCUSSIONE pdse ON pdse.ID_STATO_ESCUSSIONE = pte.ID_STATO_ESCUSSIONE\r\n"
				+ "WHERE prsp.ID_TIPO_ANAGRAFICA = '1'\r\n"
				+ "	AND prsp.ID_TIPO_BENEFICIARIO <> '4'\r\n"
				+ "	AND prcema.ID_MODALITA_AGEVOLAZIONE = 10\r\n"
				+ "	AND prsp.ID_PROGETTO = :idProgetto \r\n"
				+ "	AND pte.id_escussione = (SELECT MAX(ID_ESCUSSIONE) FROM PBANDI_T_ESCUSSIONE WHERE ID_PROGETTO = :idProgetto )\r\n");
		Object[] args2 = new Object[] { idProgetto, idProgetto };
		int[] types2 = new int[] { Types.INTEGER, Types.INTEGER };

		datiEscussioneTemp = getJdbcTemplate().query(queryEscussione.toString(), args2, types2,
				new RowMapper<initGestioneEscussioneRiassicurazioniVO>() {
					@Override
					public initGestioneEscussioneRiassicurazioniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						initGestioneEscussioneRiassicurazioniVO dettEscussione = new initGestioneEscussioneRiassicurazioniVO();

						dettEscussione.setEscussione_idEscussioneCorrente(rs.getLong("ID_ESCUSSIONE"));
						dettEscussione.setEscussione_idStatoEscussione(rs.getLong("ID_STATO_ESCUSSIONE"));
						dettEscussione.setEscussione_idTipoEscussione(rs.getLong("ID_TIPO_ESCUSSIONE"));
						dettEscussione.setEscussione_tipoEscussione(rs.getString("DESC_TIPO_ESCUSSIONE"));
						dettEscussione.setEscussione_statoEscussione(rs.getString("DESC_STATO_ESCUSSIONE"));
						dettEscussione.setEscussione_dataRicevimentoRichEscuss(rs.getDate("DT_RIC_RICH_ESCUSSIONE"));
						dettEscussione.setEscussione_dataStatoEscussione(rs.getDate("DT_INIZIO_VALIDITA"));
						dettEscussione.setEscussione_dataNotificaEscussione(rs.getDate("DT_NOTIFICA"));
						dettEscussione.setEscussione_numeroProtocolloRichiesta(rs.getString("NUM_PROTOCOLLO_RICH"));
						dettEscussione.setEscussione_numeroProtocolloNotifica(rs.getString("NUM_PROTOCOLLO_NOTIF"));
						dettEscussione.setEscussione_importoRichiesto(rs.getBigDecimal("IMP_RICHIESTO"));
						dettEscussione.setEscussione_importoApprovato(rs.getBigDecimal("IMP_APPROVATO"));
						dettEscussione.setEscussione_causaleBonifico(rs.getString("CAUSALE_BONIFICO"));
						dettEscussione.setEscussione_ibanBanca(rs.getString("IBAN_BANCA_BENEF"));
						dettEscussione.setEscussione_denomBanca(rs.getString("DESC_BANCA"));
						dettEscussione.setEscussione_note(rs.getString("NOTE"));

						String flag = rs.getString("FLAG_ESITO");
						// LOG.info(prf + " Flag esito: " + flag);
						if ("P".equals(flag)) {
							dettEscussione.setEsitoInviato(true);
						} else {
							dettEscussione.setEsitoInviato(false);
						}

						return dettEscussione;
					}
				});
		
		if (datiEscussioneTemp.size() > 0) {
			
			datiEscussioneTemp.get(0).setAllegatiInseriti(getAllegati(datiEscussioneTemp.get(0).getEscussione_idEscussioneCorrente()));
			
			return datiEscussioneTemp.get(0);
		}
		
		return null;
	}
	
	
	initGestioneEscussioneRiassicurazioniVO setLogicaEscussioneRiassicurazioni(Long idTipoEscussione, Long idStatoEscussione, Long idProgetto) {
		initGestioneEscussioneRiassicurazioniVO logicaTemp = new initGestioneEscussioneRiassicurazioniVO();

		
		// Logica Tipi escussione
		
		// Controllo se per il progetto è già stata inserita un escussione parziale, allora continuo a visualizzare il tipo parziale
		String queryControlloParziali = "-- controllo se per il progetto esiste già un escussione parziale\r\n"
				+ "SELECT COUNT(ID_ESCUSSIONE) AS num\r\n"
				+ "FROM PBANDI_T_ESCUSSIONE pte \r\n"
				+ "WHERE ID_TIPO_ESCUSSIONE = 4 AND ID_PROGETTO = " + idProgetto;
		Integer numParziali = getJdbcTemplate().queryForObject(queryControlloParziali, Integer.class);
		
		StringBuilder queryTipi = new StringBuilder();
		queryTipi.append("SELECT pdte.DESC_TIPO_ESCUSSIONE \r\n"
				+ "FROM PBANDI_D_TIPO_ESCUSSIONE pdte \r\n");
		if(numParziali > 0) {
			queryTipi.append("WHERE ID_TIPO_ESCUSSIONE = 4\r\n");
		}
		queryTipi.append("ORDER BY pdte.ID_TIPO_ESCUSSIONE");
		logicaTemp.setTipiEscussione(getJdbcTemplate().queryForList(queryTipi.toString(), String.class));



		// Logica Stati escussione
		StringBuilder queryStati = new StringBuilder();
		queryStati.append("SELECT pdse.DESC_STATO_ESCUSSIONE\r\n"
				+ "FROM PBANDI_D_STATO_ESCUSSIONE pdse\r\n");

		Integer idStato = 0;
		if (idStatoEscussione != null) {
			idStato = idStatoEscussione.intValue();
		}

		switch (idStato) {
		case 1: // Ricezione escussione
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 2"); // In istruttoria
			logicaTemp.setStatoPulsanteEscussione(2); // stato Modifica escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(false); // NON posso inviare l'esito
			break;
		case 2: // In istruttoria
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 3 OR ID_STATO_ESCUSSIONE = 5 OR ID_STATO_ESCUSSIONE = 6"); // Richiesta integrazione - Esito positivo - Esito negativo
			logicaTemp.setStatoPulsanteEscussione(2); // stato Modifica escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(false); // NON posso inviare l'esito
			break;
		case 3: // Richiesta integrazione
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 4 OR ID_STATO_ESCUSSIONE = 5 OR ID_STATO_ESCUSSIONE = 6"); // Ricezione integrazione - Esito positivo - Esito negativo
			logicaTemp.setStatoPulsanteEscussione(2); // stato Modifica escussione
			logicaTemp.setCanSendIntegrazione(true); // POSSO inviare la richiesta d'integrazione
			logicaTemp.setCanSendEsito(false); // NON posso inviare l'esito
			break;
		case 4: // Ricezione integrazione
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 5 OR ID_STATO_ESCUSSIONE = 6"); // Esito positivo - Esito negativo
			logicaTemp.setStatoPulsanteEscussione(2); // stato Modifica escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(false); // NON posso inviare l'esito
			break;
		case 5: // Esito positivo
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 5"); // Esito positivo
			logicaTemp.setStatoPulsanteEscussione(2); // stato Modifica escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(true); // POSSO inviare l'esito
			break;
		case 6: // Esito negativo
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 6"); // Esito negativo
			logicaTemp.setStatoPulsanteEscussione(2); // stato Modifica escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(true); // POSSO inviare l'esito
			break;
		case 0: // Nessuna
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 1"); // Ricezione escussione
			logicaTemp.setStatoPulsanteEscussione(1); // stato Nuova escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(false); // NON posso inviare l'esito
			break;
		default: // Nessuna
			queryStati.append("WHERE ID_STATO_ESCUSSIONE = 1"); // Ricezione escussione
			logicaTemp.setStatoPulsanteEscussione(1); // stato Nuova escussione
			logicaTemp.setCanSendIntegrazione(false); // NON posso inviare la richiesta di integrazione
			logicaTemp.setCanSendEsito(false); // NON posso inviare l'esito
			break;
		}
		queryStati.append("ORDER BY pdse.ID_STATO_ESCUSSIONE");

		logicaTemp.setStatiEscussione(getJdbcTemplate().queryForList(queryStati.toString(), String.class));
		
		// Stato di partenza nel caso in cui volessi inserire una nuova escussione
		String queryTipiIniz = "SELECT pdse.DESC_STATO_ESCUSSIONE\r\n"
				+ "FROM PBANDI_D_STATO_ESCUSSIONE pdse\r\n"
				+ "WHERE ID_STATO_ESCUSSIONE = 1";
		logicaTemp.setStatiNuovaEscussione(getJdbcTemplate().queryForList(queryTipiIniz, String.class));
		
		
		// Recupero la somma degli importi approvati inseriti nelle varie escussioni Parziali
		String querySommaImportiApprovati = "-- Somma importi approvati delle escussioni con stato parziale per quel progetto\r\n"
				+ "SELECT NVL(SUM(pte.IMP_APPROVATO), 0) AS sum\r\n"
				+ "FROM PBANDI_T_ESCUSSIONE pte\r\n"
				+ "WHERE ID_TIPO_ESCUSSIONE = 4 AND ID_PROGETTO = " + idProgetto;
		logicaTemp.setSommaImportiApprovatiInseriti(getJdbcTemplate().queryForObject(querySommaImportiApprovati, BigDecimal.class));


		return logicaTemp;

	}
	
	

	@Override
	public EsitoDTO modificaEscussioneRiassicurazioni(ModificaEscussioneRiassicurazioniDTO dati, Boolean inserisciNuovo, Long idUtente) throws Exception {
		String prf = "[GestioneRiassicurazioniDAOImpl::modificaEscussioneRiassicurazioni]";
    	LOG.info(prf + " BEGIN");

    	EsitoDTO esito = new EsitoDTO();
    	
    	Long newIdEscussione = null;
    	try {
    		
    		String queryIdEscussione = "select SEQ_PBANDI_T_ESCUSSIONE.NEXTVAL from dual";
    		newIdEscussione = getJdbcTemplate().queryForObject(queryIdEscussione, Long.class);
    		
    		LOG.info(prf + " Nuovo idEscussione: " + newIdEscussione);
    	} catch (Exception e) {
    		esito.setEsito(false);
    		esito.setException("Exception in getNewIdEscussione: " + e.toString());
    		LOG.error(prf, e);
    		return esito;
    	}
    	
    	//if(dati.getIdEscussioneCorrente() != null && dati.getIdEscussioneCorrente() != 0) {
    	if(!inserisciNuovo) {
    		try {

    			StringBuilder queryCopiaEInserisci = new StringBuilder();
    			queryCopiaEInserisci.append("-- copia e inserisci record precedente\r\n"
    					+ "INSERT INTO PBANDI_T_ESCUSSIONE\r\n"
    					+ " ( ID_ESCUSSIONE,\r\n"
    					+ "  ID_PROGETTO,\r\n"
    					+ "  ID_TIPO_ESCUSSIONE,\r\n"
    					+ "  ID_STATO_ESCUSSIONE,\r\n"
    					+ "  DT_RIC_RICH_ESCUSSIONE,\r\n"
    					+ "  NUM_PROTOCOLLO_RICH,\r\n"
    					+ "  DT_NOTIFICA,\r\n"
    					+ "  NUM_PROTOCOLLO_NOTIF,\r\n"
    					+ "  IMP_RICHIESTO,\r\n"
    					+ "  IMP_APPROVATO,\r\n"
    					+ "  CAUSALE_BONIFICO,\r\n"
    					+ "  IBAN_BONIFICO,\r\n"
    					+ "  NOTE,\r\n"
    					+ "  DT_INIZIO_VALIDITA,\r\n"
    					+ "  ID_UTENTE_INS,\r\n"
    					+ "  DT_INSERIMENTO )\r\n"
    					+ "SELECT\r\n"
    					+ " ?,	-- ID_ESCUSSIONE\r\n"
    					+ " ?,	-- ID_PROGETTO\r\n"
    					+ " (SELECT ID_TIPO_ESCUSSIONE FROM PBANDI_D_TIPO_ESCUSSIONE\r\n"
    					+ " WHERE DESC_TIPO_ESCUSSIONE = ?),	-- ID_TIPO_ESCUSSIONE\r\n"
    					+ " (SELECT ID_STATO_ESCUSSIONE FROM PBANDI_D_STATO_ESCUSSIONE pdse\r\n"
    					+ " WHERE DESC_STATO_ESCUSSIONE = ?),	-- ID_STATO_ESCUSSIONE\r\n"
    					+ " ?,	-- DT_RIC_RICH_ESCUSSIONE\r\n"
    					+ " ?,	-- NUM_PROTOCOLLO_RICH\r\n"
    					+ " ?,	-- DT_NOTIFICA\r\n"
    					+ " ?,	-- NUM_PROTOCOLLO_NOTIF\r\n"
    					+ " ?,	-- IMP_RICHIESTO\r\n"
    					+ " ?,	-- IMP_APPROVATO\r\n"
    					+ " ?,	-- CAUSALE_BONIFICO\r\n"
    					+ " ?,	-- IBAN_BONIFICO\r\n"
    					+ " ?,	-- NOTE\r\n"
    					+ " ?,	-- DT_INIZIO_VALIDITA\r\n"
    					+ " ?,	-- ID_UTENTE_INS\r\n"
    					+ " SYSDATE	-- DT_INSERIMENTO\r\n"
    					+ " FROM PBANDI_T_ESCUSSIONE\r\n"
    					+ " WHERE ID_ESCUSSIONE = ?");


    			Object[] args1 = new Object[]{
    					newIdEscussione,
    					dati.getIdProgetto(),
    					dati.getTipoEscussione(),
    					dati.getStatoEscussione(),
    					dati.getDataRicevimentoRichEscuss(),
    					dati.getNumeroProtocolloRichiesta(),
    					dati.getDataNotificaEscussione(),
    					dati.getNumeroProtocolloNotifica(),
    					dati.getImportoRichiesto(),
    					dati.getImportoApprovato(),
    					dati.getCausaleBonifico(),
    					dati.getIbanBanca(),
    					dati.getNote(),
    					dati.getDataStatoEscussione(),
    					idUtente,
    					dati.getIdEscussioneCorrente() };
    			int[] types1 = new int[]{
    					Types.INTEGER,
    					Types.INTEGER,
    					Types.VARCHAR,
    					Types.VARCHAR,
    					Types.DATE,
    					Types.VARCHAR,
    					Types.DATE,
    					Types.VARCHAR,
    					Types.DECIMAL, // importo richiesto
    					Types.DECIMAL, // importo approvato
    					Types.VARCHAR,
    					Types.VARCHAR,
    					Types.VARCHAR,
    					Types.DATE,
    					Types.INTEGER,
    					Types.INTEGER };

    			getJdbcTemplate().update(queryCopiaEInserisci.toString(), args1, types1);

    		} catch (Exception e) {
    			//throw new ErroreGestitoException("Errore nell'inserire il record", e);
    			esito.setEsito(false);
    			esito.setException("Exception nell'inserimento dell'escussione: " + e.toString());
    			LOG.error(prf, e);
    		}

    		

    		try {

    			String queryAggRecordPrec = "UPDATE PBANDI_T_ESCUSSIONE\r\n"
    					+ " SET ID_UTENTE_AGG = ?,\r\n"
    					+ " DT_FINE_VALIDITA = ?,\r\n"
    					+ " DT_AGGIORNAMENTO = CURRENT_DATE,\r\n"
    					+ " DT_NOTIFICA  = ?\r\n"
    					+ " WHERE ID_ESCUSSIONE = ?	";

    			Object[] args2 = new Object[]{
    					idUtente,
    					dati.getDataStatoEscussione(),
    					dati.getDataNotificaEscussione(),
    					dati.getIdEscussioneCorrente() };
    			int[] types2 = new int[]{
    					Types.INTEGER,
    					Types.DATE,
    					Types.DATE,
    					Types.INTEGER};

    			int rowsUpdated = getJdbcTemplate().update(queryAggRecordPrec, args2, types2);
    			
    			

    		} catch (Exception e) {
    			//throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
    			esito.setEsito(false);
    			esito.setException("Exception durante l'aggiornamento del record escussione precedente: " + e.toString());
    			LOG.error(prf, e);
    			return esito;
    		}	
    		
    		try {
    			
				// Aggiorno allegati
    			aggiornaAllegati(dati.getAllegatiInseriti(), newIdEscussione);
			} catch (Exception e) {
				LOG.error(e);
			}


    	} else {
    		try {
    			StringBuilder queryInserisci = new StringBuilder();
    			queryInserisci.append("-- inserisci nuovo record - stato ricezione escussione\r\n"
    					+ "INSERT INTO PBANDI_T_ESCUSSIONE\r\n"
    					+ " ( ID_ESCUSSIONE,\r\n"
    					+ "  ID_PROGETTO,\r\n"
    					+ "  ID_TIPO_ESCUSSIONE,\r\n"
    					+ "  ID_STATO_ESCUSSIONE,\r\n"
    					+ "  DT_RIC_RICH_ESCUSSIONE,\r\n"
    					+ "  NUM_PROTOCOLLO_RICH,\r\n"
    					+ "  IMP_RICHIESTO,\r\n"
    					+ "  NOTE,\r\n"
    					+ "  DT_INIZIO_VALIDITA,\r\n"
    					+ "  ID_UTENTE_INS,\r\n"
    					+ "  DT_INSERIMENTO )\r\n"
    					+ "VALUES\r\n"
    					+ " (?,	-- ID_ESCUSSIONE\r\n"
    					+ " ?,	-- ID_PROGETTO\r\n"
    					+ " (SELECT ID_TIPO_ESCUSSIONE FROM PBANDI_D_TIPO_ESCUSSIONE\r\n"
    					+ " WHERE DESC_TIPO_ESCUSSIONE = ?),	-- ID_TIPO_ESCUSSIONE\r\n"
    					+ " (SELECT ID_STATO_ESCUSSIONE FROM PBANDI_D_STATO_ESCUSSIONE pdse\r\n"
    					+ " WHERE DESC_STATO_ESCUSSIONE = ?),	-- ID_STATO_ESCUSSIONE\r\n"
    					+ " ?,	-- DT_RIC_RICH_ESCUSSIONE\r\n"
    					+ " ?,	-- NUM_PROTOCOLLO_RICH\r\n"
    					+ " ?,	-- IMP_RICHIESTO\r\n"
    					+ " ?,	-- NOTE\r\n"
    					+ " ?,	-- DT_INIZIO_VALIDITA\r\n"
    					+ " ?,	-- ID_UTENTE_INS\r\n"
    					+ " SYSDATE )	-- DT_INSERIMENTO");


    			Object[] args3 = new Object[]{
    					newIdEscussione,
    					dati.getIdProgetto(),
    					dati.getTipoEscussione(),
    					dati.getStatoEscussione(),
    					dati.getDataRicevimentoRichEscuss(),
    					dati.getNumeroProtocolloRichiesta(),
    					dati.getImportoRichiesto(),
    					dati.getNote(),
    					dati.getDataStatoEscussione(),
    					idUtente };
    			int[] types3 = new int[]{
    					Types.INTEGER,
    					Types.INTEGER,
    					Types.VARCHAR,
    					Types.VARCHAR,
    					Types.DATE,
    					Types.VARCHAR,
    					Types.DECIMAL, // importo richiesto
    					Types.VARCHAR,
    					Types.DATE,
    					Types.INTEGER };

    			getJdbcTemplate().update(queryInserisci.toString(), args3, types3);
    			

    		} catch (Exception e) {
    			LOG.error(prf, e);
    			esito.setEsito(false);
    			esito.setException("Exception nell'inserimento dell'escussione: " + e.toString());
    		}

    	}

    	
    	
    	LOG.info(prf + " END");

    	esito.setEsito(true);
    	esito.setId(newIdEscussione);
    	return esito;
	}
	
	
	public List<GestioneAllegatiVO> getAllegati(Long idEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::getAllegati]";
		LOG.info(prf + " BEGIN");
		List<GestioneAllegatiVO> va;
		try {
		String query =
				"select nome_file, ID_DOCUMENTO_INDEX from PBANDI_T_DOCUMENTO_INDEX   \r\n"
				+ "where id_tipo_documento_index = 36             \r\n"
				+ "and id_entita = 605                            \r\n"
				+ "and id_target = :idEscussione        \r\n"
				+ "ORDER BY DT_INSERIMENTO_INDEX DESC";
		
		LOG.debug(query);

		Object[] args = new Object[]{idEscussione};
		int[] types = new int[]{Types.INTEGER};  

		va = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<List<GestioneAllegatiVO>>() {
			@Override
			public List<GestioneAllegatiVO> extractData(ResultSet rs) throws SQLException {
				List<GestioneAllegatiVO> elencoDati = new ArrayList<>();
				
				
				while(rs.next())
				{
					GestioneAllegatiVO item= new GestioneAllegatiVO();
			     item.setNomeFile(rs.getString("nome_file"));
			     item.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
			     elencoDati.add(item);
				}
			return elencoDati;		
			}});
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(" END");
	}
	return va;	

	}
	
	@Override
	public Boolean aggiornaAllegati(List<GestioneAllegatiVO> allegatiPresenti, Long idTarget){
			String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl:: aggiornaAllegati]";
			logger.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
				for (GestioneAllegatiVO item : allegatiPresenti) {
									
					String queryModifica = "UPDATE PBANDI.PBANDI_T_DOCUMENTO_INDEX\r\n"
							+ "SET ID_TARGET = " + idTarget + "\r\n"
							+ "WHERE ID_DOCUMENTO_INDEX = " + item.getIdDocumentoIndex();

					getJdbcTemplate().update(queryModifica);
					
					logger.info(prf + " The document idIndex: " + item.getIdDocumentoIndex() + " has been updated with idTarget: " + idTarget);
				}
				
				
				
			} catch (Exception e) {
				logger.error("errore durante l'update di id_target in pbandi_t_documento_index "+e);
				result= false; 
			}
			
			
			logger.info(prf + " END");
			return result;
		}
}
