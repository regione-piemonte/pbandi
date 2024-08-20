/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.ControlloPreErogazioneDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ControlloAntimafiaVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ControlloDeggendorfVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ControlloDurcVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.DatiPreErogazioneVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.*;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class ControlloPreErogazioneDAOImpl extends JdbcDaoSupport implements ControlloPreErogazioneDAO{
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);


	@Autowired
	DocumentoManager documentoManager;

	@Autowired
	public ControlloPreErogazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public DatiPreErogazioneVO fetchData(Long idProposta) {
		String prf = "[ControlloPreErogazioneDAOImpl::fetchData]";
		LOG.info(prf + " BEGIN");
		DatiPreErogazioneVO datiPreErogazioneVO = null;
		try{
			String getIdPropostaPadre = "SELECT CASE \n" +
					"\tWHEN ptpe.ID_PROPOSTA_PADRE IS NOT NULL THEN ptpe.ID_PROPOSTA_PADRE\n" +
					"\tELSE ptpe.ID_PROPOSTA\n" +
					"END AS idProposta \n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA = ?";
			idProposta = getJdbcTemplate().queryForObject(getIdPropostaPadre, Long.class, idProposta);
			Long finalIdProposta = idProposta;

			String query = "SELECT DISTINCT \n" +
					"propostaErogazione.id_proposta AS idProposta,  \n" +
					"propostaErogazione.imp_da_erogare AS importoBeneficiario, \n" +
					"propostaErogazione.imp_lordo AS importoLordo, \n" +
					"estremiBancari.iban AS ibanBeneficiario,\n" +
					"progetto.codice_visualizzato AS codiceProgetto, \n" +
					"progetto.id_progetto AS idProgetto, \n" +
					"(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione, \n" +
					"bando.titolo_bando AS titoloBando, \n" +
					"modalitaAgevolazione.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif, \n" +
					"modalitaAgevolazione.desc_modalita_agevolazione AS descrModalitaAgevolazione, \n" +
					"propostaErogazione.dt_controlli as dataControlli, \n" +
					"CASE WHEN regolaBR57.id_regola IS NOT NULL \n" +
					"\tTHEN \n" +
					"\t\t(CASE WHEN enteGiuridico.flag_pubblico_privato IS NOT NULL \n" +
					"\t\t\tTHEN \n" +
					"\t\t\t\tenteGiuridico.flag_pubblico_privato \n" +
					"\t\t\tELSE 2 \n" +
					"\t\tEND)\n" +
					"\tELSE \n" +
					"\t\t2 \n" +
					"END AS flagPubblicoPrivato,\n" +
					"soggetto.id_soggetto as idSoggetto, \n" +
					"soggetto.codice_fiscale_soggetto AS codiceFiscale,\n" +
					"domanda.numero_domanda as numeroDomanda, \n" +
					"domanda.progr_bando_linea_intervento AS codiceBando, \n" +
					"propostaErogazione.flag_finistr AS flagFinistr \n" +
					"FROM \n" +
					"PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione \n" +
					"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
					"JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
					"LEFT JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = soggettoProgetto.id_estremi_bancari\n" +
					"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
					"JOIN (SELECT DISTINCT \n" +
					"\tsoggetto.id_soggetto,\n" +
					"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
					"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
					"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
					"\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
					"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
					"JOIN (SELECT DISTINCT \n" +
					"\tsoggetto.id_soggetto,\n" +
					"\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
					"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
					"\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
					"\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
					"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
					"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = propostaErogazione.id_modalita_agevolazione \n" +
					"JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
					"JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
					"LEFT JOIN (\n" +
					"\tSELECT DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO, pcr.ID_REGOLA \n" +
					"\tFROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
					"\tJOIN PBANDI_R_REGOLA_BANDO_LINEA prrbl ON  prrbl.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \n" +
					"\tJOIN PBANDI_C_REGOLA pcr ON pcr.ID_REGOLA = prrbl.ID_REGOLA AND pcr.DESC_BREVE_REGOLA = 'BR57'\n" +
					") regolaBR57 ON regolaBR57.PROGR_BANDO_LINEA_INTERVENTO = bandoLineaIntervento.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"JOIN PBANDI_T_BANDO bando ON bandoLineaIntervento.id_bando = bando.id_bando \n" +
					"WHERE \n" +
					"soggettoProgetto.id_tipo_anagrafica = 1 \n" +
					"AND soggettoProgetto.id_tipo_beneficiario != 4 \n" +
					"AND propostaErogazione.id_proposta = ?";

			LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idProposta);

			List<DatiPreErogazioneVO> list = getJdbcTemplate().query(
					query,
					ps -> ps.setLong(1, finalIdProposta),
					new DatiPreErogazioneVORowMapper()
			);

			if(list.size()>0) {
				datiPreErogazioneVO = list.get(0);

				String sqlIterInCorso = "SELECT count(1)\n" +
						"FROM PBANDI_T_WORK_FLOW ptwf \n" +
						"JOIN PBANDI_T_WORK_FLOW_ENTITA ptwfe ON ptwfe.ID_WORK_FLOW = ptwf.ID_WORK_FLOW \n" +
						"JOIN PBANDI_T_DETT_WORK_FLOW ptdwf ON ptdwf.ID_WORK_FLOW = ptwf.ID_WORK_FLOW \n" +
						"JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA \n" +
						"WHERE pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
						"AND ptdwf.DT_APPROVAZIONE IS NULL\n" +
						"AND ptdwf.DT_ANNULLAMENTO IS NULL\n" +
						"AND ptwfe.ID_TARGET = :idPropostaErogazione\n" +
						"AND ptwf.ID_TIPO_ITER = :idTipoIter";

				String sqlIterConcluso = "SELECT count(1)\n" +
						"FROM PBANDI_T_WORK_FLOW ptwf \n" +
						"JOIN PBANDI_T_WORK_FLOW_ENTITA ptwfe ON ptwfe.ID_WORK_FLOW = ptwf.ID_WORK_FLOW \n" +
						"JOIN PBANDI_T_DETT_WORK_FLOW ptdwf ON ptdwf.ID_WORK_FLOW = ptwf.ID_WORK_FLOW \n" +
						"JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA \n" +
						"WHERE pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
						"AND ptdwf.ID_STATO_ITER = 5\n" +
						"AND ptwfe.ID_TARGET = :idPropostaErogazione\n" +
						"AND ptwf.ID_TIPO_ITER = :idTipoIter";

				String sqlIterInCorsoConcluso = "SELECT count(1)\n" +
						"FROM PBANDI_T_WORK_FLOW ptwf \n" +
						"JOIN PBANDI_T_WORK_FLOW_ENTITA ptwfe ON ptwfe.ID_WORK_FLOW = ptwf.ID_WORK_FLOW \n" +
						"JOIN PBANDI_T_DETT_WORK_FLOW ptdwf ON ptdwf.ID_WORK_FLOW = ptwf.ID_WORK_FLOW \n" +
						"JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA \n" +
						"JOIN PBANDI_T_DISTINTA ptd ON ptd.ID_DISTINTA = ptwfe.ID_TARGET \n" +
						"JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
						"WHERE pce.NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
						"AND (ptdwf.ID_STATO_ITER = 5 \n" +
						"\tOR (ptdwf.DT_APPROVAZIONE IS NULL AND ptdwf.DT_ANNULLAMENTO IS NULL)\n" +
						") AND ptdd.ID_TARGET = :idPropostaErogazione\n" +
						"AND ptwf.ID_TIPO_ITER = :idTipoIter";

				datiPreErogazioneVO.setIterEsitoValidazioneInCorso(getJdbcTemplate().queryForObject(sqlIterInCorso, Long.class, idProposta, 18) > 0);
				datiPreErogazioneVO.setIterEsitoValidazioneConcluso(getJdbcTemplate().queryForObject(sqlIterConcluso, Long.class, idProposta, 18) > 0);
				datiPreErogazioneVO.setIterCommunicazioneInterventoInCorso(getJdbcTemplate().queryForObject(sqlIterInCorso, Long.class, idProposta, 19) > 0);
				datiPreErogazioneVO.setIterCommunicazioneInterventoConcluso(getJdbcTemplate().queryForObject(sqlIterConcluso, Long.class, idProposta, 19) > 0);
				datiPreErogazioneVO.setDistintaCreata(getJdbcTemplate().queryForObject(sqlIterInCorsoConcluso, Long.class, idProposta, 13) > 0);

				String sqlListaInterventi = "SELECT \n" +
						"pddis.ID_DESTINATARIO_INTERVENTO AS idDestinatario,\n" +
						"pddis.DESC_DESTINATARIO_INTERVENTO AS denominazione,\n" +
						"pddis.IBAN AS iban,\n" +
						"ptpe.IMP_DA_EROGARE AS importoDaErogare\n" +
						"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
						"JOIN PBANDI_D_DEST_INTERV_SOSTITUT pddis ON pddis.ID_DESTINATARIO_INTERVENTO = ptpe.ID_DESTINATARIO_INTERVENTO \n" +
						"WHERE ptpe.ID_PROPOSTA_PADRE = ?";
				datiPreErogazioneVO.setListaInterventi(getJdbcTemplate().query(sqlListaInterventi, (rs, rowNum) -> {
					InterventoSostitutivoVO interventoSostitutivoVO = new InterventoSostitutivoVO();

					interventoSostitutivoVO.setIdDestinatario(rs.getLong("idDestinatario"));
					interventoSostitutivoVO.setDenominazione(rs.getString("denominazione"));
					interventoSostitutivoVO.setIban(rs.getString("iban"));
					interventoSostitutivoVO.setImportoDaErogare(rs.getBigDecimal("importoDaErogare"));

					return interventoSostitutivoVO;
				}, idProposta));

				String sql =
						"SELECT IMP_LORDO \n" +
								"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
								"WHERE ptpe.ID_PROPOSTA = ?";
				BigDecimal importoAgevolato = getJdbcTemplate().queryForObject(sql, BigDecimal.class, idProposta);
				datiPreErogazioneVO.setVerificaAntimafia(importoAgevolato.compareTo(BigDecimal.valueOf(150000)) >= 0);
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read ControlloDeggendorfVO", e);
			throw new ErroreGestitoException("DaoException while trying to read ControlloDeggendorfVO", e);
		} finally {
			LOG.info(prf + " Status operazione: " + (datiPreErogazioneVO!=null));
			LOG.info(prf + " END");
		}

		return datiPreErogazioneVO;
	}

	@Override
	public ControlloDurcVO getControlloDurc(Long idProgetto) {
		String prf = "[ControlloPreErogazioneDAOImpl::getControlloDurc]";
		LOG.info(prf + " BEGIN");
		ControlloDurcVO controlloDurcVO = null;
		String query;
		try{
			try{
				query = "SELECT * FROM (\n" +
						"\tSELECT \n" +
						"\tsoggettoDurc.dt_emissione_durc AS dtEmissioneDurc, \n" +
						"\tsoggettoDurc.dt_scadenza AS dtScadenza, \n" +
						"\ttipoEsitoRichieste.desc_esito_richieste AS descEsitoRichieste,\n" +
						"\ttipoRichiesta.desc_tipo_richiesta AS descTipoRichiesta,\n" +
						"\tstatoRichiesta.desc_stato_richiesta AS descStatoRichiesta\n" +
						"\tFROM PBANDI_T_DOMANDA domanda\n" +
						"\tJOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda\n" +
						"\tJOIN PBANDI_R_SOGGETTO_DOMANDA soggettoDomanda ON soggettoDomanda.id_domanda = domanda.id_domanda\n" +
						"\tJOIN PBANDI_T_RICHIESTA richiesta ON richiesta.id_domanda = domanda.id_domanda\n" +
						"\tLEFT JOIN PBANDI_D_TIPO_RICHIESTA tipoRichiesta ON tipoRichiesta.id_tipo_richiesta = richiesta.id_tipo_richiesta\n" +
						"\tLEFT JOIN PBANDI_D_STATO_RICHIESTA statoRichiesta ON statoRichiesta.id_stato_richiesta = richiesta.id_stato_richiesta\n" +
						"\tLEFT JOIN PBANDI_T_SOGGETTO_DURC soggettoDurc ON soggettoDurc.id_soggetto = soggettoDomanda.id_soggetto\n" +
						"\t\tAND (soggettoDurc.dt_scadenza > CURRENT_DATE OR soggettoDurc.dt_scadenza IS NULL)\n" +
						"\t\tAND statoRichiesta.desc_breve_stato_richiesta = 'C'\n" +
						"\tLEFT JOIN PBANDI_D_TIPO_ESITO_RICHIESTE tipoEsitoRichieste ON tipoEsitoRichieste.id_tipo_esito_richieste = soggettoDurc.id_tipo_esito_durc\n" +
						"\tWHERE progetto.id_progetto = ?\n" +
						"\tAND soggettoDomanda.id_tipo_anagrafica = 1\n" +
						"\tAND soggettoDomanda.id_tipo_beneficiario <> 4\n" +
						"\tAND tipoRichiesta.desc_breve_tipo_richiesta = 'DURC'\n" +
						"\tORDER BY richiesta.dt_invio_richiesta DESC\n" +
						") WHERE ROWNUM <= 1";
				controlloDurcVO = getJdbcTemplate().queryForObject(
						query,
						new Object[] {idProgetto},
						new ControlloDurcVORowMapper()
				);
			}catch (EmptyResultDataAccessException emptyDurc){
				try{
					query = "SELECT * FROM (\n" +
							"\tSELECT \n" +
							"\tsoggettoDsan.dt_emissione_dsan AS dtEmissioneDurc, \n" +
							"\tsoggettoDsan.dt_scadenza AS dtScadenza, \n" +
							"\t'DSAN' AS descEsitoRichieste,\n" +
							"\ttipoRichiesta.desc_tipo_richiesta AS descTipoRichiesta,\n" +
							"\tstatoRichiesta.desc_stato_richiesta AS descStatoRichiesta\n" +
							"\tFROM PBANDI_T_DOMANDA domanda \n" +
							"\tJOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda\n" +
							"\tJOIN PBANDI_R_SOGGETTO_DOMANDA soggettoDomanda ON soggettoDomanda.id_domanda = domanda.id_domanda\n" +
							"\tJOIN PBANDI_T_RICHIESTA richiesta ON richiesta.id_domanda = domanda.id_domanda\n" +
							"\tLEFT JOIN PBANDI_D_TIPO_RICHIESTA tipoRichiesta ON tipoRichiesta.id_tipo_richiesta = richiesta.id_tipo_richiesta\n" +
							"\tLEFT JOIN PBANDI_D_STATO_RICHIESTA statoRichiesta ON statoRichiesta.id_stato_richiesta = richiesta.id_stato_richiesta\n" +
							"\tLEFT JOIN PBANDI_T_SOGGETTO_DSAN soggettoDsan ON soggettoDsan.id_domanda = domanda.id_domanda\n" +
							"\t\tAND (soggettoDsan.dt_scadenza > CURRENT_DATE OR soggettoDsan.dt_scadenza IS NULL)\n" +
							"\t\tAND statoRichiesta.desc_breve_stato_richiesta = 'C'\n" +
							"\tWHERE progetto.id_progetto = ?\n" +
							"\tAND soggettoDomanda.id_tipo_anagrafica = 1\n" +
							"\tAND soggettoDomanda.id_tipo_beneficiario <> 4\n" +
							"\tAND tipoRichiesta.desc_breve_tipo_richiesta = 'DSAN'\n" +
							"\tORDER BY richiesta.dt_invio_richiesta DESC\n" +
							") WHERE ROWNUM <= 1";
					controlloDurcVO = getJdbcTemplate().queryForObject(
							query,
							new Object[] {idProgetto},
							new ControlloDurcVORowMapper()
					);
				}catch (EmptyResultDataAccessException ignored){}
			}
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read controlloDurcVO", e);
			throw new ErroreGestitoException("DaoException while trying to read controlloDurcVO", e);
		} finally {
			LOG.info(prf + " Status operazione: " + (controlloDurcVO!=null));
			LOG.info(prf + " END");
		}

		return controlloDurcVO;
	}

	@Override
	public ControlloAntimafiaVO getControlloAntimafia(Long idProgetto) {
		String prf = "[ControlloPreErogazioneDAOImpl::getControlloAntimafia]";
		LOG.info(prf + " BEGIN");
		ControlloAntimafiaVO controlloAntimafiaVO = null;
		String query;
		try{
			try{
				query = "SELECT * FROM (\n" +
						"\tSELECT\n" +
						"\tstatoRichiesta.desc_breve_stato_richiesta,\n" +
						"\tsoggettoAntimafia.dt_emissione AS dtEmissioneAntimafia, \n" +
						"\tsoggettoAntimafia.dt_ricezione_bdna AS dtRicezioneBdna,\n" +
						"\tsoggettoAntimafia.dt_scadenza_antimafia AS dtScadenza, \n" +
						"\ttipoEsitoRichieste.desc_esito_richieste AS descEsitoRichieste,\n" +
						"\ttipoRichiesta.desc_tipo_richiesta AS descTipoRichiesta,\n" +
						"\tstatoRichiesta.desc_stato_richiesta AS descStatoRichiesta\n" +
						"\tFROM PBANDI_T_DOMANDA domanda\n" +
						"\tJOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda\n" +
						"\tJOIN PBANDI_R_SOGGETTO_DOMANDA soggettoDomanda ON soggettoDomanda.id_domanda = domanda.id_domanda\n" +
						"\tJOIN PBANDI_T_RICHIESTA richiesta ON richiesta.id_domanda = domanda.id_domanda\n" +
						"\tLEFT JOIN PBANDI_D_TIPO_RICHIESTA tipoRichiesta ON tipoRichiesta.id_tipo_richiesta = richiesta.id_tipo_richiesta\n" +
						"\tLEFT JOIN PBANDI_D_STATO_RICHIESTA statoRichiesta ON statoRichiesta.id_stato_richiesta = richiesta.id_stato_richiesta\n" +
						"\tLEFT JOIN PBANDI_T_SOGGETTO_ANTIMAFIA soggettoAntimafia ON soggettoAntimafia.id_domanda = domanda.id_domanda \n" +
						"\t\tAND (\n" +
						"\t\t\tsoggettoAntimafia.dt_scadenza_antimafia > CURRENT_DATE \n" +
						"\t\t\tOR (soggettoAntimafia.dt_scadenza_antimafia IS NULL AND soggettoAntimafia.DT_RICEZIONE_BDNA IS NOT NULL)\n" +
						"\t\t) AND (\n" +
						"\t\t\tstatoRichiesta.desc_breve_stato_richiesta = 'C'\n" +
						"\t\t\tOR statoRichiesta.desc_breve_stato_richiesta = 'IST'\n" +
						"\t\t)\n" +
						"\tLEFT JOIN PBANDI_D_TIPO_ESITO_RICHIESTE tipoEsitoRichieste ON tipoEsitoRichieste.id_tipo_esito_richieste = soggettoAntimafia.id_tipo_esito_antimafia\n" +
						"\tWHERE progetto.id_progetto = ?\n" +
						"\tAND soggettoDomanda.id_tipo_anagrafica = 1\n" +
						"\tAND soggettoDomanda.id_tipo_beneficiario <> 4\n" +
						"\tAND tipoRichiesta.desc_breve_tipo_richiesta = 'ANTIMAFIA'\n" +
						"\tORDER BY richiesta.dt_invio_richiesta DESC\n" +
						") WHERE ROWNUM <= 1";
				controlloAntimafiaVO = getJdbcTemplate().queryForObject(
						query,
						new Object[] {idProgetto},
						new ControlloAntimafiaVORowMapper()
				);
			}catch (EmptyResultDataAccessException ignored){}
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read controlloAntimafiaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read controlloAntimafiaVO", e);
		} finally {
			LOG.info(prf + " Status operazione: " + (controlloAntimafiaVO!=null));
			LOG.info(prf + " END");
		}

		return controlloAntimafiaVO;
	}

	@Override
	public ControlloDeggendorfVO getControlloDeggendorf(Long idSoggetto) {
		String prf = "[ControlloPreErogazioneDAOImpl::getControlloDeggendorf]";
		LOG.info(prf + " BEGIN");
		ControlloDeggendorfVO controlloDeggendorfVO = null;

		try{
			String query = "SELECT * FROM (\n" +
					"\tSELECT \n" +
					"\tpecr.dt_emissione AS dataEmissione,\n" +
					"\tpecr.codice_univoco AS vercor,\n" +
					"\tpecr.esito AS esitoRichiesta,\n" +
					"\tpecr.dt_scadenza AS dataScadenza\n" +
					"\tFROM \n" +
					"\tPBANDI_T_PROP_EROG_CTRL_RNA pecr\n" +
					"\tWHERE \n" +
					"\tpecr.id_soggetto = ?\n" +
					"\tAND \n" +
					"\tpecr.dt_scadenza > CURRENT_DATE \n" +
					"\tAND \n" +
					"\t(pecr.dt_fine_validita IS NULL \n" +
					"\tOR \n" +
					"\tpecr.dt_fine_validita > CURRENT_DATE) \n" +
					"\tORDER BY pecr.dt_emissione DESC \n" +
					") WHERE ROWNUM <= 1";

			LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idSoggetto);

			List<ControlloDeggendorfVO> list = getJdbcTemplate().query(
					query,
					ps -> ps.setLong(1, idSoggetto),
					new ControlloDeggendorfVORowMapper()
			);

			if(list.size()>0)
				controlloDeggendorfVO = list.get(0);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read ControlloDeggendorfVO", e);
			throw new ErroreGestitoException("DaoException while trying to read ControlloDeggendorfVO", e);
		} finally {
			LOG.info(prf + " Status operazione: " + (controlloDeggendorfVO!=null));
			LOG.info(prf + " END");
		}

		return controlloDeggendorfVO;
	}

	@Override
	public String getControlloNonApplicabile(Long idProposta, Long idTipoRichiesta) {
		String prf = "[ControlloPreErogazioneDAOImpl::getControlloNonApplicabile]";
		LOG.info(prf + " BEGIN");
		String controlloNonApplicabile = null;

		try{
			String query = "SELECT \n" +
					"cne.motivazione\n" +
					"FROM \n" +
					"PBANDI_R_CTRL_NA_EROGAZIONE cne\n" +
					"WHERE \n" +
					"cne.id_proposta = ?\n" +
					"AND\n" +
					"cne.id_tipo_richiesta = ?" +
					"AND\n" +
					"cne.dt_fine_validita IS NULL";

			LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idProposta + ", " + idTipoRichiesta);

			List<String> list = getJdbcTemplate().query(
					query,
					ps -> {
						ps.setLong(1, idProposta);
						ps.setLong(2, idTipoRichiesta);
					},
					(rs, rowNum) -> rs.getString("motivazione")
			);

			if(list.size()>0)
				controlloNonApplicabile = list.get(0);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read String", e);
			throw new ErroreGestitoException("DaoException while trying to read String", e);
		} finally {
			LOG.info(prf + " Status operazione: " + (controlloNonApplicabile!=null));
			LOG.info(prf + " END");
		}

		return controlloNonApplicabile;
	}

	@Override
	public Boolean controlloNonApplicabile(Long idProposta, Long idTipoRichiesta, String motivazione, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::ControlloNonApplicabile]";
		LOG.info(prf + " BEGIN");
		Long idUtente = ((UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR)).getIdUtente();

		Boolean status = null;
		String sql;
		try{
			if(motivazione!=null){
				Long results;
				sql =
						"SELECT count(1) AS results\n" +
						"FROM PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile\n" +
						"WHERE controlloNonApplicabile.id_proposta = ?\n" +
						"AND controlloNonApplicabile.id_tipo_richiesta = ?\n" +
						"AND controlloNonApplicabile.dt_fine_validita IS NULL ";

				LOG.debug(prf + " query: " + sql + "\n" + prf + " args: " + idProposta + ", " + idTipoRichiesta);

				results = getJdbcTemplate().queryForObject(sql, new Object[]{idProposta, idTipoRichiesta}, Long.class);
				status = true;

				if(results == 0){
					sql = "INSERT INTO PBANDI_R_CTRL_NA_EROGAZIONE (id_proposta, id_tipo_richiesta, motivazione, \n" +
							"dt_inizio_validita, id_utente_ins, dt_inserimento)" +
							"VALUES\n" +
							"(?, ?, ?, CURRENT_DATE, ?, CURRENT_DATE)";

					LOG.debug(prf + " query: " + sql + "\n" + prf + " args: " + idProposta + ", " + idTipoRichiesta + ", " + motivazione + ", " + idUtente);
					getJdbcTemplate().execute(sql,
							(PreparedStatementCallback<Boolean>) ps -> {
								ps.setLong(1, idProposta);
								ps.setLong(2, idTipoRichiesta);
								ps.setString(3, motivazione);
								ps.setLong(4, idUtente);
								return ps.execute();
							});
				}else{
					sql = "UPDATE PBANDI_R_CTRL_NA_EROGAZIONE set " +
							"motivazione = ?, " +
							"id_utente_agg = ?, " +
							"dt_aggiornamento = CURRENT_DATE " +
							"WHERE id_proposta = ? AND id_tipo_richiesta = ? AND dt_fine_validita IS NULL";

					LOG.debug(prf + " query: " + sql + "\n" + prf + " args: " + motivazione + ", " + idUtente + ", " + idProposta + ", " + idTipoRichiesta);
					getJdbcTemplate().update(sql, motivazione, idUtente, idProposta, idTipoRichiesta);
				}
			}else{
				status = false;
				sql = "UPDATE PBANDI_R_CTRL_NA_EROGAZIONE set " +
						"motivazione = NULL, " +
						"dt_fine_validita = CURRENT_DATE, " +
						"id_utente_agg = ?, " +
						"dt_aggiornamento = CURRENT_DATE " +
						"WHERE id_proposta = ? AND id_tipo_richiesta = ? AND dt_fine_validita IS NULL";

				LOG.debug(prf + " query: " + sql + "\n" + prf + " args: " + idUtente + ", " + idProposta + ", " + idTipoRichiesta);
				getJdbcTemplate().update(sql, idUtente, idProposta, idTipoRichiesta);
			}

			checkControlliPreErogazione(idProposta, req);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read/modify ControlloNonApplicabile", e);
			throw new ErroreGestitoException("DaoException while trying to read/modify ControlloNonApplicabile", e);
		} finally {
			LOG.info(prf + " Status operazione: " + (status!=null));
			LOG.info(prf + " END");
		}
		return status;
	}

	@Override
	public Boolean saveControlliPreErogazione(Long idProposta, Long idSoggetto, Boolean esitoDeggendorf, String vercor, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::saveControlliPreErogazione]";
		LOG.info(prf + " BEGIN");
		Long idUtente = ((UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR)).getIdUtente();

		boolean status = false;
		try{
			String isPrivato = "SELECT CASE WHEN regolaBR57.id_regola IS NOT NULL \n" +
					"\tTHEN \n" +
					"\t\t(CASE WHEN enteGiuridico.flag_pubblico_privato IS NOT NULL \n" +
					"\t\t\tTHEN \n" +
					"\t\t\t\tenteGiuridico.flag_pubblico_privato \n" +
					"\t\t\tELSE 2 \n" +
					"\t\tEND)\n" +
					"\tELSE \n" +
					"\t\t2\n" +
					"END AS flagPubblicoPrivato\n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione \n" +
					"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
					"JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
					"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
					"JOIN (SELECT DISTINCT \n" +
					"\tsoggetto.id_soggetto,\n" +
					"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
					"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
					"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
					"\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
					"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
					"JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
					"JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
					"LEFT JOIN (\n" +
					"\tSELECT DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO, pcr.ID_REGOLA \n" +
					"\tFROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
					"\tJOIN PBANDI_R_REGOLA_BANDO_LINEA prrbl ON  prrbl.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \n" +
					"\tJOIN PBANDI_C_REGOLA pcr ON pcr.ID_REGOLA = prrbl.ID_REGOLA AND pcr.DESC_BREVE_REGOLA = 'BR57'\n" +
					") regolaBR57 ON regolaBR57.PROGR_BANDO_LINEA_INTERVENTO = bandoLineaIntervento.PROGR_BANDO_LINEA_INTERVENTO\n" +
					"JOIN PBANDI_T_BANDO bando ON bandoLineaIntervento.id_bando = bando.id_bando \n" +
					"WHERE \n" +
					"soggettoProgetto.id_tipo_anagrafica = 1 \n" +
					"AND soggettoProgetto.id_tipo_beneficiario != 4 \n" +
					"AND propostaErogazione.id_proposta = ?";

			String getGiorniScadenzaDeggendorf = "SELECT pcc.valore AS giorniScadenzaDeggendorf\n" +
					"FROM PBANDI_C_COSTANTI pcc \n" +
					"WHERE pcc.ATTRIBUTO = 'GIORNI_SCADENZA_DEGGENDORF'";

			String updateControlli = "UPDATE PBANDI_T_PROP_EROG_CTRL_RNA set " +
					"codice_univoco = ?, " +
					"esito = ?, " +
					"dt_emissione = CURRENT_DATE, " +
					"dt_scadenza = CURRENT_DATE + ?, " +
					"id_utente_agg = ? " +
					"WHERE \n" +
					"id_soggetto = ?\n" +
					"AND \n" +
					"dt_scadenza > CURRENT_DATE \n" +
					"AND \n" +
					"dt_fine_validita IS NULL";

			String insertControlli = "INSERT INTO PBANDI_T_PROP_EROG_CTRL_RNA (id_prop_erog_ctrl_rna, codice_univoco, id_soggetto, esito, dt_emissione, dt_scadenza, \n" +
					"id_utente_ins, dt_inizio_validita)" +
					"VALUES\n" +
					"(?, ?, ?, ?, CURRENT_DATE, CURRENT_DATE+?, ?, CURRENT_DATE)";

			LOG.debug(prf + " query: " + isPrivato + "\n" + prf + " args: " + idSoggetto);
			boolean privato = getJdbcTemplate().queryForObject(isPrivato, Long.class, idProposta) == 1L;
			if(privato){
				//update deggendorf
				LOG.debug(prf + " query: " + updateControlli + "\n" + prf + " args: " + vercor + ", " + esitoDeggendorf + ", " + idUtente + ", " + idSoggetto);

                Long giorniScadenzaDeggendorf = getJdbcTemplate().queryForObject(getGiorniScadenzaDeggendorf, Long.class);

				status = getJdbcTemplate().update(updateControlli, vercor, esitoDeggendorf ? 1L : 2L, giorniScadenzaDeggendorf, idUtente, idSoggetto) != 0;
				if(!status){
					String getNewId ="select SEQ_PBANDI_T_PROP_EROGAZIONE.nextval from dual";

					//insert deggendorf
					LOG.debug(prf + " query: " + insertControlli + "\n" + prf + " args: " + vercor + ", " + idSoggetto + ", " + esitoDeggendorf + ", " + giorniScadenzaDeggendorf + ", " + idUtente);
					status = getJdbcTemplate().execute(
							insertControlli,
							(PreparedStatementCallback<Boolean>) ps -> {
								ps.setBigDecimal(1, getJdbcTemplate().queryForObject(getNewId, BigDecimal.class));
								ps.setString(2, vercor);
								ps.setLong(3, idSoggetto);
								ps.setLong(4, esitoDeggendorf ? 1L : 2L);
								ps.setLong(5, giorniScadenzaDeggendorf);
								ps.setLong(6, idUtente);
								return ps.execute();
							}) != null;
				}
			}

			checkControlliPreErogazione(idProposta, req);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to save controlli pre erogazione", e);
			throw new ErroreGestitoException("DaoException while trying to save controlli pre erogazione", e);
		} finally {
			LOG.info(prf + " Status operazione: " + status);
			LOG.info(prf + " END");
		}
		return status;
	}

	@Override
	public Boolean checkControlliPreErogazione(Long idPropostaErogazione, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::checkControlliPreErogazione]";
		LOG.info(prf + " BEGIN");

		boolean status = false;
		try{
			String sql =
					"SELECT propostaErogazione.id_progetto\n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione\n" +
					"WHERE propostaErogazione.id_proposta = ?";
			Long idProgetto = getJdbcTemplate().queryForObject(sql, Long.class, idPropostaErogazione);

			sql =
					"SELECT soggettoProgetto.id_soggetto\n" +
					"FROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto\n" +
					"WHERE soggettoProgetto.id_progetto = ?\n" +
					"AND soggettoProgetto.id_tipo_anagrafica = 1\n" +
					"AND soggettoProgetto.id_tipo_beneficiario <> 4";
			Long idSoggetto = getJdbcTemplate().queryForObject(sql, Long.class, idProgetto);

			sql =
					"SELECT IMP_LORDO \n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA = ?";
			BigDecimal importoAgevolato = getJdbcTemplate().queryForObject(sql, BigDecimal.class, idPropostaErogazione);

			boolean controlliPreErogazione = true;

			{
				LOG.info(prf + " Verifico la proposta " + idPropostaErogazione);
				//1.	Blocco anagrafico
				if (
						getJdbcTemplate().queryForObject(
								"SELECT COUNT(*) as results\n" +
										"FROM PBANDI_T_SOGG_DOMANDA_BLOCCO soggettoDomandaBlocco \n" +
										"JOIN PBANDI_R_SOGGETTO_DOMANDA soggettoDomanda ON soggettoDomanda.progr_soggetto_domanda = soggettoDomandaBlocco.progr_soggetto_domanda \n" +
										"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = soggettoDomanda.id_domanda \n" +
										"JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = soggettoDomandaBlocco.id_causale_blocco \n" +
										"WHERE causaleBlocco.flag_erogazione = 'S'\n" +
										"AND soggettoDomanda.id_soggetto = ? \n" +
										"AND progetto.id_progetto = ? \n" +
										"AND soggettoDomandaBlocco.dt_inserimento_sblocco IS NULL",
								Integer.class,
								idSoggetto, idProgetto
						) > 0) {
					//ho trovato almeno un blocco per questa domanda/soggetto senza data sblocco
					LOG.info("La proposta con codice: " + idPropostaErogazione + "presenta un blocco soggetto/domanda senza data di sblocco!");
					controlliPreErogazione = false;
				}
				//2.	Forzatura controllo non applicabile per Antimafia
				if (controlliPreErogazione &&
						getJdbcTemplate().queryForObject(
								"SELECT COUNT(*) as results \n" +
									"FROM \n" +
									"PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" +
									"WHERE \n" +
									"controlloNonApplicabile.id_tipo_richiesta = 3 \n" +
									"AND \n" +
									"controlloNonApplicabile.id_proposta = ? " +
									"AND \n" +
									"controlloNonApplicabile.dt_fine_validita IS NULL",
								Integer.class,
								idPropostaErogazione
						) == 0
						//ANTIMAFIA SOLO SE IMPORTO < 150.000 (condizione rimossa)
						/*importoAgevolato.compareTo(BigDecimal.valueOf(150000)) >= 0*/) {
					//non ho trovato alcun controllo non applicabile di tipo antimafia per questa proposta di erogazione
					//3.	Scaduto un controllo antimafia
					if (
							getJdbcTemplate().queryForObject(
									"SELECT COUNT(*) as results \n" +
											"FROM PBANDI_T_SOGGETTO_ANTIMAFIA antimafia \n" +
											"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = antimafia.id_domanda \n" +
											"AND progetto.id_progetto = ?\n" +
											"AND ( \n" +
											"\tantimafia.dt_scadenza_antimafia > CURRENT_DATE \n" +
											"\tOR ( \n" +
											"\t\tantimafia.dt_scadenza_antimafia IS NULL\n" +
											"\t\tAND antimafia.DT_RICEZIONE_BDNA IS NOT NULL\n" +
											"\t)\n" +
											")",
									Integer.class,
									idProgetto
							) == 0) {
						//non ho trovato alcun controllo antimafia valido per questa proposta
						LOG.info("La proposta con codice: " + idPropostaErogazione + " non presenta controlli antimafia validi!");
						controlliPreErogazione = false;
					}
				}
				//4.	Forzatura controllo non applicabile per DURC
				if (controlliPreErogazione &&
						getJdbcTemplate().queryForObject(
								"SELECT COUNT(*) as results \n" +
									"FROM \n" +
									"PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" +
									"WHERE \n" +
									"controlloNonApplicabile.id_tipo_richiesta = 1 \n" +
									"AND \n" +
									"controlloNonApplicabile.id_proposta = ? " +
									"AND \n" +
									"controlloNonApplicabile.dt_fine_validita IS NULL",
								Integer.class,
								idPropostaErogazione
						) == 0) {
					//non ho trovato alcun controllo non applicabile di tipo DURC per questa proposta di erogazione
					//5.	Scaduto un controllo DURC
					if (
							getJdbcTemplate().queryForObject(
									"SELECT COUNT(*) AS results\n" +
										"FROM PBANDI_T_SOGGETTO_DURC durc\n" +
										"WHERE durc.id_soggetto = ?\n" +
										"AND durc.dt_scadenza > CURRENT_DATE",
									Integer.class,
									idSoggetto
							) == 0) {
						if(
								getJdbcTemplate().queryForObject(
										"SELECT COUNT(*) AS results\n" +
											"FROM PBANDI_T_SOGGETTO_DSAN dsan\n" +
											"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = dsan.id_domanda\n" +
											"WHERE progetto.id_progetto = ?\n" +
											"AND dsan.dt_scadenza > CURRENT_DATE",
										Integer.class,
										idProgetto
								) == 0) {
							//non ho trovato alcun controllo DURC valido per questa proposta
							LOG.info("La proposta con codice: " + idPropostaErogazione + " non presenta controlli ne DURC ne DSAN validi!");
							controlliPreErogazione = false;
						}
					}
				}
				Long privato = getJdbcTemplate().queryForObject(
						"SELECT CASE WHEN regolaBR57.id_regola IS NOT NULL \n" +
								"\tTHEN \n" +
								"\t\t(CASE WHEN enteGiuridico.flag_pubblico_privato IS NOT NULL \n" +
								"\t\t\tTHEN \n" +
								"\t\t\t\tenteGiuridico.flag_pubblico_privato \n" +
								"\t\t\tELSE 2 \n" +
								"\t\tEND)\n" +
								"\tELSE \n" +
								"\t\t2\n" +
								"END AS flagPubblicoPrivato\n" +
								"FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione \n" +
								"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
								"JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
								"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
								"JOIN (SELECT DISTINCT \n" +
								"\tsoggetto.id_soggetto,\n" +
								"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
								"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
								"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
								"\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
								"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
								"JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
								"JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
								"LEFT JOIN (\n" +
								"\tSELECT DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO, pcr.ID_REGOLA \n" +
								"\tFROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
								"\tJOIN PBANDI_R_REGOLA_BANDO_LINEA prrbl ON  prrbl.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \n" +
								"\tJOIN PBANDI_C_REGOLA pcr ON pcr.ID_REGOLA = prrbl.ID_REGOLA AND pcr.DESC_BREVE_REGOLA = 'BR57'\n" +
								") regolaBR57 ON regolaBR57.PROGR_BANDO_LINEA_INTERVENTO = bandoLineaIntervento.PROGR_BANDO_LINEA_INTERVENTO\n" +
								"JOIN PBANDI_T_BANDO bando ON bandoLineaIntervento.id_bando = bando.id_bando \n" +
								"WHERE \n" +
								"soggettoProgetto.id_tipo_anagrafica = 1 \n" +
								"AND soggettoProgetto.id_tipo_beneficiario != 4 \n" +
								"AND propostaErogazione.id_proposta = ?",
						Long.class,
						idPropostaErogazione
				);
				if (controlliPreErogazione && privato == 1L) {
					//6.	Forzatura controllo non applicabile per Deggendorf
					if (
							getJdbcTemplate().queryForObject(
									"SELECT COUNT(*) as results \n" +
										"FROM \n" +
										"PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" +
										"WHERE \n" +
										"controlloNonApplicabile.id_tipo_richiesta = 4 \n" +
										"AND \n" +
										"controlloNonApplicabile.id_proposta = ? \n" +
										"AND \n" +
										"controlloNonApplicabile.dt_fine_validita IS NULL",
									Integer.class,
									idPropostaErogazione
							) == 0) {
						//non ho trovato alcun controllo non applicabile di tipo Deggendorf per questa proposta di erogazione
						//7.	Scaduto un controllo Deggendorf (con esito 1)
						if (
								getJdbcTemplate().queryForObject(
										"SELECT COUNT(*) AS results \n" +
											"FROM \n" +
											"PBANDI_T_PROP_EROG_CTRL_RNA deggendorf \n" +
											"WHERE \n" +
											"deggendorf.id_soggetto = ? \n" +
											"AND \n" +
											"deggendorf.dt_scadenza > CURRENT_DATE \n" +
											"AND \n" +
											"deggendorf.esito = 1 ",
										Integer.class,
										idSoggetto
								) == 0) {
							//non ho trovato alcun controllo Deggendrof valido per questa proposta
							LOG.info("La proposta con codice: " + idPropostaErogazione + " non presenta controlli Deggendrof validi!");
							controlliPreErogazione = false;
						}
					}
				}
				UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
				//Aggiornamento flag ctrl pre erogazione
				sql =
						"UPDATE PBANDI_T_PROPOSTA_EROGAZIONE \n" +
						"SET FLAG_CTRL_PRE_EROGAZIONE  = ?," +
						"DT_AGGIORNAMENTO = CURRENT_DATE," +
						"DT_CONTROLLI = CURRENT_DATE," +
						"ID_UTENTE_AGG = ?\n" +
						"WHERE ID_PROPOSTA = ?";
				getJdbcTemplate().update(sql, controlliPreErogazione ? "S" : "N", userInfoSec.getIdUtente(), idPropostaErogazione);
			}

			status = controlliPreErogazione;

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to checkControlliPreErogazione", e);
			throw new ErroreGestitoException("DaoException while trying to checkControlliPreErogazione", e);
		} finally {
			LOG.info(prf + " Status operazione: " + status);
			LOG.info(prf + " END");
		}
		return status;
	}

	@Override
	public List<DestinatarioInterventoSostVO> getDestinatariInterventi() {
		String prf = "[ControlloPreErogazioneDAOImpl::getDestinatariInterventi]";
		LOG.info(prf + " BEGIN");

		List<DestinatarioInterventoSostVO> destinatarioInterventoSostVOList;
		try{
			String query = "SELECT \n" +
					"ptdi.ID_DESTINATARIO_INTERVENTO AS idDestinatario, \n" +
					"ptdi.DESC_DESTINATARIO_INTERVENTO as denominazione, \n" +
					"ptdi.IBAN as iban \n" +
					"FROM PBANDI_D_DEST_INTERV_SOSTITUT ptdi \n";

			LOG.debug(prf + " query: " + query + "\n");

			destinatarioInterventoSostVOList = getJdbcTemplate().query(query, (rs, rowNum) -> {
				DestinatarioInterventoSostVO destinatarioInterventoSostVO = new DestinatarioInterventoSostVO();

				destinatarioInterventoSostVO.setIdDestinatario(rs.getLong("idDestinatario"));
				destinatarioInterventoSostVO.setDenominazione(rs.getString("denominazione"));
				destinatarioInterventoSostVO.setIban(rs.getString("iban"));

				return destinatarioInterventoSostVO;
			});
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DestinatarioInterventoSostVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DestinatarioInterventoSostVO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return destinatarioInterventoSostVOList;
	}

	@Override
	public void inserisciInterventiSostitutivi(Long idProposta, List<InterventoSostitutivoVO> interventiSostitutivi, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::inserisciInterventiSostitutivi]";
		LOG.info(prf + " BEGIN");

		try{
			String query;
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			//Aggiorna proposta erogazione
			query = "SELECT ptpe.IMP_LORDO \n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA = ?";
			BigDecimal importoLordo = getJdbcTemplate().queryForObject(query, BigDecimal.class, idProposta);
			LOG.info(prf + " Aggiornata proposte di erogazione con id: " + idProposta);
			LOG.info(prf + " Importo iniziale = " + importoLordo);
			for(InterventoSostitutivoVO interventoSostitutivoVO : interventiSostitutivi){
				importoLordo = importoLordo.subtract(interventoSostitutivoVO.getImportoDaErogare());
				LOG.info(prf + " Importo sottratto = " + interventoSostitutivoVO.getImportoDaErogare());
			}

			query = "UPDATE PBANDI_T_PROPOSTA_EROGAZIONE SET \n" +
					"IMP_LORDO = ?,\n" +
					"IMP_DA_EROGARE = ? - IMP_IRES,\n" +
					"DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
					"ID_UTENTE_AGG = ?\n" +
					"WHERE ID_PROPOSTA = ?";

			LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idProposta);
			getJdbcTemplate().update(
					query,
					importoLordo,
					importoLordo,
					userInfoSec.getIdUtente(),
					idProposta
			);

			//Crea proposte erogazioni figlie per interventi sostitutivi
			String getNewIdPropErog ="select SEQ_PBANDI_T_PROP_EROGAZIONE.nextval from dual";
			query = "SELECT ID_PROGETTO, ID_MODALITA_AGEVOLAZIONE, ID_CAUSALE_EROGAZIONE, FLAG_FINISTR \n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA = ?";
			HashMap<String, Object> infoPropErog = getJdbcTemplate().queryForObject(query, (rs, rownum) -> {
				HashMap<String, Object> map = new HashMap<>();
				map.put("idProgetto", rs.getLong("ID_PROGETTO"));
				map.put("idModalitaAgevolazione", rs.getString("ID_MODALITA_AGEVOLAZIONE"));
				map.put("idCausaleErogazione", rs.getLong("ID_CAUSALE_EROGAZIONE"));
				map.put("flagFinistr", rs.getString("FLAG_FINISTR"));
				return map;
			}, idProposta);

			query = "INSERT INTO PBANDI_T_PROPOSTA_EROGAZIONE (\n" +
					"ID_PROPOSTA, ID_PROGETTO, FLAG_CTRL_PRE_EROGAZIONE, \n" +
					"IMP_LORDO, IMP_IRES, IMP_DA_EROGARE, \n" +
					"ID_MODALITA_AGEVOLAZIONE, ID_CAUSALE_EROGAZIONE, \n" +
					"DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO,\n" +
					"FLAG_FINISTR, ID_PROPOSTA_PADRE, ID_DESTINATARIO_INTERVENTO\n" +
					") VALUES (\n" +
					"?, ?, 'N',\n" +
					"?, 0, ?,\n" +
					"?, ?, \n" +
					"CURRENT_DATE, ?, CURRENT_DATE,\n" +
					"?, ?, ?\n" +
					")";
			for(InterventoSostitutivoVO interventoSostitutivoVO : interventiSostitutivi) {
				Long idInterventoSostitutivo = getJdbcTemplate().queryForObject(getNewIdPropErog, Long.class);
				LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idProposta);
				getJdbcTemplate().update(
						query,
						idInterventoSostitutivo,
						infoPropErog.get("idProgetto"),
						interventoSostitutivoVO.getImportoDaErogare(),
						interventoSostitutivoVO.getImportoDaErogare(),
						infoPropErog.get("idModalitaAgevolazione"),
						infoPropErog.get("idCausaleErogazione"),
						userInfoSec.getIdUtente(),
						infoPropErog.get("flagFinistr"),
						idProposta,
						interventoSostitutivoVO.getIdDestinatario()
				);
				LOG.info("Inserito nuovo intervento sostitutivo con idProposta: " + idInterventoSostitutivo);
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to inserisciInterventiSostitutivi", e);
			throw new ErroreGestitoException("DaoException while trying to inserisciInterventiSostitutivi", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Long creaDistintaInterventiSostitutivi(Long idPropostaPadre, String descrizione, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::creaDistintaInterventiSostitutivi]";
		LOG.info(prf + " BEGIN");

		Long idDistinta;
		try{
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			String sqlGetIdEstremiBancari = "SELECT \n" +
					"pteb.id_estremi_bancari AS idEstremiBancari\n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptpe.ID_PROGETTO \n" +
					"JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n" +
					"JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO \n" +
					"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = ptpe.ID_MODALITA_AGEVOLAZIONE \n" +
					"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma2 ON pdma2.ID_MODALITA_AGEVOLAZIONE_RIF = pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
					"JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.ID_BANDO = prbli.ID_BANDO AND\n" +
					"\tprbmaeb.ID_MODALITA_AGEVOLAZIONE = pdma2.ID_MODALITA_AGEVOLAZIONE \n" +
					"JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.ID_ESTREMI_BANCARI = prbmaeb.ID_ESTREMI_BANCARI \n" +
					"WHERE ptpe.ID_PROPOSTA = ?";
			Long idEstremiBancari = getJdbcTemplate().queryForObject(sqlGetIdEstremiBancari, Long.class, idPropostaPadre);

			String sqlGetIdModAgev = "SELECT ptpe.ID_MODALITA_AGEVOLAZIONE \n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA = ?";
			Long idModalitaAgevolazione = getJdbcTemplate().queryForObject(sqlGetIdModAgev, Long.class, idPropostaPadre);

			String sqlGetIdTipoDist = "SELECT CASE \n" +
					"\tWHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 1 THEN 6\n" +
					"\tWHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 5 THEN 7\n" +
					"\tWHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 10 THEN 8\n" +
					"END AS idTipoDistinta\n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
					"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
			Long idTipoDistinta = getJdbcTemplate().queryForObject(sqlGetIdTipoDist, Long.class, idModalitaAgevolazione);

			String getNewIdDistinta = "select SEQ_PBANDI_T_DISTINTA.nextval from dual";
			idDistinta = getJdbcTemplate().queryForObject(getNewIdDistinta, Long.class);

			String insertDistinta = "INSERT INTO PBANDI_T_DISTINTA \n" +
					"(ID_DISTINTA, ID_ENTITA, ID_TIPO_DISTINTA, ID_MODALITA_AGEVOLAZIONE, DESCRIZIONE, ID_ESTREMI_BANCARI, ID_STATO_DISTINTA, DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO)\n" +
					"VALUES\n" +
					"(?, (\n" +
					"\tSELECT id_entita\n" +
					"\tFROM PBANDI_C_ENTITA\n" +
					"\tWHERE nome_entita ='PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
					"), ?, ?, ?, ?, 1, CURRENT_DATE, ?, CURRENT_DATE)";
			getJdbcTemplate().update(insertDistinta, idDistinta,
					idTipoDistinta, idModalitaAgevolazione,
					descrizione, idEstremiBancari, userInfoSec.getIdUtente());


			String sqlGetListaInterventi = "SELECT ptpe.ID_PROPOSTA AS idProposta\n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA_PADRE = ?";

			String getNewIdDistintaDett = "select SEQ_PBANDI_T_DISTINTA_DETT.nextval from dual";
			Long idDistintaDett = getJdbcTemplate().queryForObject(getNewIdDistintaDett, Long.class);
			String getRigaDistinta = "SELECT COUNT(1) + 1 FROM PBANDI_T_DISTINTA_DETT ptdd WHERE ptdd.ID_DISTINTA = ?";
			Long rigaDistinta = getJdbcTemplate().queryForObject(getRigaDistinta, Long.class, idDistinta);

			String sqlInsertDistintaDett = "INSERT INTO PBANDI_T_DISTINTA_DETT\n" +
					"(ID_DISTINTA_DETT, ID_DISTINTA, RIGA_DISTINTA, ID_TARGET, DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO)\n" +
					"VALUES\n" +
					"(?,?,?,?,CURRENT_DATE,?, CURRENT_DATE)";
			getJdbcTemplate().update(sqlInsertDistintaDett,
					idDistintaDett, idDistinta, rigaDistinta, idPropostaPadre, userInfoSec.getIdUtente()
			);
			for(Long idProposta : getJdbcTemplate().query(sqlGetListaInterventi, (rs, rowNum) -> rs.getLong("idProposta"), idPropostaPadre)) {
				idDistintaDett = getJdbcTemplate().queryForObject(getNewIdDistintaDett, Long.class);
				rigaDistinta = getJdbcTemplate().queryForObject(getRigaDistinta, Long.class, idDistinta);
				getJdbcTemplate().update(sqlInsertDistintaDett,
						idDistintaDett, idDistinta, rigaDistinta, idProposta, userInfoSec.getIdUtente()
				);
			}
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read Long", e);
			throw new ErroreGestitoException("DaoException while trying to read Long", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idDistinta;
	}

	@Override
	public Long getIdProgetto(Long idPropostaErogazione) {
		String prf = "[ControlloPreErogazioneDAOImpl::getIdProgetto]";
		LOG.info(prf + " BEGIN");

		Long idProgetto;
		try{
			String query = "SELECT ptpe.ID_PROGETTO \n" +
					"FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
					"WHERE ptpe.ID_PROPOSTA = :idProposta";

			LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idPropostaErogazione);

			idProgetto = getJdbcTemplate().queryForObject(query, Long.class, idPropostaErogazione);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read Long", e);
			throw new ErroreGestitoException("DaoException while trying to read Long", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idProgetto;
	}

	@Override
	public Long getIdEntitaPropostaErogazione() {
		String prf = "[ControlloPreErogazioneDAOImpl::getIdEntitaPropostaErogazione]";
		LOG.info(prf + " BEGIN");

		Long idEntita;
		try{
			String query = "SELECT pce.ID_ENTITA \n" +
					"FROM PBANDI_C_ENTITA pce \n" +
					"WHERE pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'";

			LOG.debug(prf + " query: " + query + "\n" + prf);

			idEntita = getJdbcTemplate().queryForObject(query, Long.class);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read Long", e);
			throw new ErroreGestitoException("DaoException while trying to read Long", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idEntita;
	}

	@Override
	public Long getIdEntitaDistinta() {
		String prf = "[ControlloPreErogazioneDAOImpl::getIdEntitaDistinta]";
		LOG.info(prf + " BEGIN");

		Long idEntita;
		try{
			String query = "SELECT pce.ID_ENTITA \n" +
					"FROM PBANDI_C_ENTITA pce \n" +
					"WHERE pce.NOME_ENTITA = 'PBANDI_T_DISTINTA'";

			LOG.debug(prf + " query: " + query + "\n" + prf);

			idEntita = getJdbcTemplate().queryForObject(query, Long.class);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read Long", e);
			throw new ErroreGestitoException("DaoException while trying to read Long", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idEntita;
	}

	@Override
	public void aggiungiAllegato(BigDecimal idTarget, BigDecimal idEntita, Long idProgetto, BigDecimal idTipoDocumentoIndex, BigDecimal idUtente, byte[] file, String nomeFile, String visibilita) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		FileDTO fileDto = new FileDTO();
		fileDto.setBytes(file);
		DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
		documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		documentoIndexVO.setIdEntita(idEntita);
		documentoIndexVO.setIdTarget(idTarget);
		if(idProgetto != null) {
			documentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		}
		documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
		documentoIndexVO.setIdUtenteIns(idUtente);
		documentoIndexVO.setNomeFile(nomeFile);
		documentoIndexVO.setFlagVisibileBen(visibilita);
		documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
		documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
				"SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
				new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
				String.class
		));
		/*Dati Obbligatori*/
		documentoIndexVO.setUuidNodo("UUID");

		//salva allegato
		if(!documentoManager.salvaFileConVisibilita(fileDto.getBytes(), documentoIndexVO, null)) {
			throw new ErroreGestitoException("Errore durante il salvataggio dell'allegato");
		}

		LOG.info(prf + "END");
	}

	@Override
	public boolean salvaImportoDaErogare(Long idProposta, BigDecimal importoDaErogare, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::salvaImportoDaErogare]";
		LOG.info(prf + " BEGIN");

		boolean output;
		try{
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			String query = "UPDATE PBANDI_T_PROPOSTA_EROGAZIONE SET \n" +
					"IMP_LORDO = :importoDaErogare,\n" +
					"IMP_DA_EROGARE = :importoDaErogare - IMP_IRES,\n" +
					"ID_UTENTE_AGG = :idUtente,\n" +
					"DT_AGGIORNAMENTO = CURRENT_DATE\n" +
					"WHERE ID_PROPOSTA = :idProposta";

			LOG.debug(prf + " query: " + query + "\n" + prf);

			output = getJdbcTemplate().update(query, importoDaErogare, importoDaErogare, userInfoSec.getIdUtente(), idProposta) > 0;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to salvaImportoDaErogare", e);
			throw new ErroreGestitoException("DaoException while trying to salvaImportoDaErogare", e);
		} finally {
			LOG.info(prf + " END");
		}

		return output;
	}
}
