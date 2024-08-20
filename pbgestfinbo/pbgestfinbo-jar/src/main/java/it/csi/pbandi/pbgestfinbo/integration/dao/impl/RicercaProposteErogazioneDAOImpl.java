/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaProposteErogazioneDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.PropostaErogazioneVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.PropostaErogazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.SuggestIdDescVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RicercaProposteErogazioneDAOImpl extends JdbcDaoSupport implements RicercaProposteErogazioneDAO {
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public RicercaProposteErogazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<PropostaErogazioneVO> getProposteErogazione(Long progrBandoLinea, Long idModalitaAgevolazione, Long idSoggetto, Long idProgetto, String contrPreErogazione) {
		String prf = "[RicercaProposteErogazioneDAOImpl::getProposteErogazione]";
		LOG.info(prf + " BEGIN");
		List<PropostaErogazioneVO> lista;

		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT DISTINCT \n" +
							"propostaErogazione.id_proposta AS idProposta, \n" +
							"progetto.id_progetto AS idProgetto, \n" +
							"progetto.codice_visualizzato AS codiceProgetto, \n" +
							"(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione, \n" +
							"propostaErogazione.imp_lordo AS importoLordo, \n" +
							"propostaErogazione.imp_ires AS importoIres, \n" +
							"propostaErogazione.imp_da_erogare AS importoNetto, \n" +
							"progetto.dt_concessione AS dataConcessione, \n" +
							"propostaErogazione.flag_ctrl_pre_erogazione AS controlliPreErogazione, \n" +
							"propostaErogazione.flag_finistr AS flagFinistr \n" +
							"FROM \n" +
							"PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione \n" +
							"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = propostaErogazione.id_modalita_agevolazione \n" +
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
							"JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
							"\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
							"JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
							"JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
							"WHERE soggettoProgetto.id_tipo_anagrafica = 1 \n" +
							"AND soggettoProgetto.id_tipo_beneficiario != 4\n"
			);

			List<Long> args = new ArrayList<>();

			if (progrBandoLinea != -1) {
				args.add(progrBandoLinea);
				query.append("AND bandoLineaIntervento.progr_bando_linea_intervento = ?\n");
			}

			if (idModalitaAgevolazione != -1) {
				args.add(idModalitaAgevolazione);
				query.append("AND modalitaAgevolazione.id_modalita_agevolazione_rif = ?\n");
			}

			if (idSoggetto != -1) {
				args.add(idSoggetto);
				query.append("AND soggetto.id_soggetto = ?\n");
			}

			if (idProgetto != -1) {
				args.add(idProgetto);
				query.append("AND progetto.id_progetto = ?\n");
			}

			if (!"".equals(contrPreErogazione)) {
				if (contrPreErogazione.equals("S"))
					query.append("AND propostaErogazione.flag_ctrl_pre_erogazione = 'S'\n");
				if (contrPreErogazione.equals("N"))
					query.append("AND propostaErogazione.flag_ctrl_pre_erogazione = 'N'\n");
			}

			LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + args);

			lista = getJdbcTemplate().query(
					query.toString(),
					ps -> {
						for(int i = 0; i < args.size(); i++){
							ps.setLong(i + 1, args.get(i));
						}
					},
					new PropostaErogazioneVORowMapper()
			);

			for(PropostaErogazioneVO propostaErogazioneVO : lista) {
				//RICHIESTA ANTIMAFIA E RICHIESTA DURC
				try {
					String sql = "SELECT * FROM (\n" +
							"\tSELECT \n" +
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
					propostaErogazioneVO.setStatoRichiestaDurc(getJdbcTemplate().queryForObject(
							sql, String.class, propostaErogazioneVO.getIdProgetto()
					));
				}catch (Exception e) {
					String sql = "SELECT * FROM (\n" +
							"\tSELECT \n" +
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
					propostaErogazioneVO.setStatoRichiestaDurc(getJdbcTemplate().queryForObject(
							sql, String.class, propostaErogazioneVO.getIdProgetto()
					));
				}
				try {
					String sql = "SELECT * FROM (\n" +
							"\tSELECT\n" +
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
					propostaErogazioneVO.setStatoRichiestaAntimafia(getJdbcTemplate().queryForObject(
							sql, String.class, propostaErogazioneVO.getIdProgetto()
					));
				}catch (Exception ignored) {}
			}
		} catch (RecordNotFoundException e) {
			throw e;
		}

		catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PropostaErogazioneVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PropostaErogazioneVO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return lista;
	}

	@Override
	public List<SuggestIdDescVO> getSuggestion(String value, String id) throws Exception {
		String prf = "[RicercaPRoposteErogazioneDAOImpl::getSuggestion]";
		LOG.info(prf + " BEGIN");
		List<SuggestIdDescVO> lista;

		try {
			StringBuilder query = new StringBuilder();

			switch (id) {
				case "1":
					query.append(
							"SELECT DISTINCT \n" +
									"bandoLineaIntervent.PROGR_BANDO_LINEA_INTERVENTO AS id,\n" +
									"bandoLineaIntervent.NOME_BANDO_LINEA AS sugg\n" +
									"FROM PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervent\n" +
									"JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP enteComp ON enteComp.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento \n" +
									"JOIN PBANDI_T_DOMANDA domanda ON domanda.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento\n" +
									"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda \n" +
									"JOIN PBANDI_R_SOGGETTO_PROGETTO soggetto ON soggetto.id_progetto = progetto.id_progetto \n" +
									"WHERE soggetto.id_tipo_anagrafica = 1 AND soggetto.id_tipo_beneficiario <> 4 AND \n" +
									"enteComp.ID_ENTE_COMPETENZA = '2' \n" +
									"AND enteComp.ID_RUOLO_ENTE_COMPETENZA = '1' \n" +
									"AND UPPER(bandoLineaIntervent.NOME_BANDO_LINEA) LIKE CONCAT('%', CONCAT(TRIM(?),'%')) \n" +
									"ORDER BY bandoLineaIntervent.NOME_BANDO_LINEA"
					);
					break;
				case "2":
					query.append(
							"SELECT DISTINCT \n" +
							"modalitaAgevolazione.id_modalita_agevolazione AS id, \n" +
							"modalitaAgevolazione.desc_modalita_agevolazione as sugg \n" +
							"FROM PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione \n" +
							"WHERE UPPER(modalitaAgevolazione.desc_modalita_agevolazione) like CONCAT(CONCAT('%',TRIM(?)),'%') \n" +
							"AND modalitaAgevolazione.id_modalita_agevolazione in (1, 5, 10)"
					);
					break;
				case "3":
					query.append(
							"WITH lastSoggProg AS (\n" +
									"\tSELECT max(soggettoProgetto.progr_soggetto_progetto) AS maxProgrSoggProg\n" +
									"\tFROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto\n" +
									"\tGROUP BY soggettoProgetto.id_soggetto\n" +
									")\n" +
									"SELECT DISTINCT \n" +
									"soggetto.id_soggetto AS id, \n" +
									"(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) || ' - ' || soggetto.codice_fiscale_soggetto AS sugg \n" +
									"FROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto \n" +
									"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto\n" +
									"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
									"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica\n" +
									"JOIN lastSoggProg ON lastSoggProg.maxProgrSoggProg = soggettoProgetto.progr_soggetto_progetto\n" +
									"WHERE UPPER(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) LIKE CONCAT('%', CONCAT(?,'%'))\n" +
									"AND soggettoProgetto.id_tipo_anagrafica = 1 AND soggettoProgetto.id_tipo_beneficiario <> 4");

					break;
				case "4":
					query.append(
							"SELECT DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO AS id,\n" +
									"prbmaeb.CODICE_FONDO_FINPIS AS sugg\n" +
									"FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb \n" +
									"JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = prbmaeb.ID_BANDO \n" +
									"WHERE prbmaeb.CODICE_FONDO_FINPIS LIKE CONCAT('%', CONCAT(?,'%'))"
					);

					break;
				case "5":
					query.append(
							"SELECT DISTINCT " +
									"progetto.id_progetto AS id, " +
									"progetto.codice_visualizzato as sugg " +
									"FROM PBANDI_T_PROGETTO progetto " +
									"WHERE UPPER(progetto.codice_visualizzato) like CONCAT(CONCAT('%',TRIM(?)),'%')"
					);
					break;
				default:
					LOG.error("There was an error with ids");
			}

			lista = getJdbcTemplate().query(
					query.toString(),
					ps -> ps.setString(1, value),
					(rs, rowNum) -> {
						SuggestIdDescVO suggestIdDescVO = new SuggestIdDescVO();

						suggestIdDescVO.setId(rs.getLong("id"));
						suggestIdDescVO.setDesc(rs.getString("sugg"));

						return suggestIdDescVO;
					}
			);

			LOG.debug(prf + " query: " + query + "\n" + prf + " arg: " + value);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PropostaErogazione - suggestion", e);
			throw new ErroreGestitoException("DaoException while trying to read PropostaErogazione - suggestion", e);
		} finally {
			LOG.info(prf + " END");
		}

		return lista;

	}

}
