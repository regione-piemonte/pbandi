/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.dto.InfoMensilita;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.EsposizioneMensilitaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class EsposizioneMensilitaDAOImpl extends BaseDAO implements EsposizioneMensilitaDAO {
	
	private Logger LOG;
	
	public EsposizioneMensilitaDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public List<Integer> getListaProgetti(String numeroDomanda) {
		String prf = "[EsposizioneMensilitaDAOImpl::getListaProgetti]";
		LOG.info(prf + " BEGIN");
		List<Integer> listaProgetti = new ArrayList<Integer>();
		LOG.info(prf + ", numeroDomanda=" + numeroDomanda);
		try {
			String sql = "SELECT PTD.ID_DOMANDA,									\n" + 
					"       PTP.ID_PROGETTO											\n" + 
					"FROM PBANDI_T_DOMANDA PTD										\n" + 
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_DOMANDA = PTD.ID_DOMANDA	\n" + 
					"WHERE NUMERO_DOMANDA= :numeroDomanda";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			listaProgetti = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				List<Integer> lista = new ArrayList<Integer>();
				while (rs.next()) {
					Integer idProgetto= rs.getInt("ID_PROGETTO");
					lista.add(idProgetto);
				}
				return lista;
			});

		} finally {
			LOG.info(prf + " END");
		}

		return listaProgetti;
	}

	@Override
	public List<InfoMensilita> recuperaMensilita(Integer idProgetto, Integer identificativoDichiarazioneDiSpesa) {
		String prf = "[EsposizioneMensilitaDAOImpl::recuperaMensilita]";
		LOG.info(prf + " BEGIN");
		List<InfoMensilita> listaInfoMensilita = new ArrayList<InfoMensilita>();
		LOG.info(prf + ", idProgetto=" + idProgetto + ", identificativoDichiarazioneDiSpesa=" + identificativoDichiarazioneDiSpesa);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ptdmw.ANNO,																					\n" + 
					"       ptdmw.MESE,																							\n" + 
					"       ptdmw.ESITO,																						\n" + 
					"       ptdmw.FLAG_SABBATICO,																				\n" + 
					"       pte.NOTE_EROGAZIONE,																				\n" + 
					"       ptdmw.DT_AGGIORNAMENTO,																				\n" + 
					"       ptdmw.ID_PROGETTO,																					\n" + 
					"       ptdmw.ID_DICHIARAZIONE_SPESA,																		\n" + 
					"       ptds.DT_CHIUSURA_VALIDAZIONE																		\n" + 
					"FROM PBANDI_T_DICH_MENS_WS ptdmw																			\n" + 
					"JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON ptdmw.ID_DICHIARAZIONE_SPESA = ptds.ID_DICHIARAZIONE_SPESA		\n" + 
					"LEFT JOIN PBANDI_T_EROGAZIONE pte ON pte.ID_PROGETTO = ptds.ID_PROGETTO									\n" + 
					"AND TO_CHAR(ptds.ID_DICHIARAZIONE_SPESA) = pte.NOTE_EROGAZIONE												\n" + 
					"WHERE ptdmw.ID_PROGETTO= :idProgetto																		\n" + 
					"  AND PTDMW.DT_FINE_VALIDITA IS NULL																		\n");
			
			if(identificativoDichiarazioneDiSpesa != null) {
				sql.append("AND PTDMW.ID_DICHIARAZIONE_SPESA= :identificativoDichiarazioneDiSpesa \n");
			}
			sql.append("ORDER BY ptdmw.ANNO, ptdmw.MESE");

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			if(identificativoDichiarazioneDiSpesa != null) {
				param.addValue("identificativoDichiarazioneDiSpesa", identificativoDichiarazioneDiSpesa, Types.INTEGER);
			}

			listaInfoMensilita = this.namedParameterJdbcTemplate.query(sql.toString(), param, rs -> {
				List<InfoMensilita> lista = new ArrayList<InfoMensilita>();
				while (rs.next()) {
					InfoMensilita info = new InfoMensilita();
					info.setAnno(rs.getInt("ANNO"));
					info.setMese(rs.getString("MESE"));
					info.setEsitoValidazione(rs.getString("ESITO"));
					info.setSabbatico("S".equals(rs.getString("FLAG_SABBATICO")));
					info.setNoteErogazioni(rs.getString("NOTE_EROGAZIONE"));
					info.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
					info.setIdProgetto(rs.getInt("ID_PROGETTO"));
					info.setDichiarazioneSpesa(rs.getInt("ID_DICHIARAZIONE_SPESA"));
					info.setDtChiusuraValidazione(rs.getDate("DT_CHIUSURA_VALIDAZIONE"));
					lista.add(info);
				}
				return lista;
			});

		} finally {
			LOG.info(prf + " END");
		}

		return listaInfoMensilita;
	}

}
