/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.security.InvalidParameterException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbservizit.dto.help.HelpDTO;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.integration.dao.HelpDAO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.HelpRowMapper;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;

@Component
public class HelpDAOImpl extends JdbcDaoSupport implements HelpDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	public HelpDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public Boolean getFlagEditHelpUser(Long idUtente, String idIride) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT FLAG_EDIT_HELP FROM PBANDI_T_UTENTE ptu WHERE ID_UTENTE = ?";

			Object[] args = new Object[] { idUtente };
			LOG.info("<idUtente>: " + idUtente);
			LOG.info(prf + "\n" + sql + "\n");

			String flag = getJdbcTemplate().queryForObject(sql, args, String.class);
			if (flag != null && flag.equals("S")) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getFlagEditHelpUser: ", e);
			throw new ErroreGestitoException(" ERRORE in getFlagEditHelpUser.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Boolean hasTipoAnagHelp(String codiceRuolo, Long idUtente, String idIride) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (codiceRuolo == null) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT COUNT(*) \r\n" + "FROM PBANDI_R_GROUP_HELP_TIPO_ANAG gh \r\n"
					+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta  ON ta.ID_TIPO_ANAGRAFICA = gh.ID_TIPO_ANAGRAFICA\r\n"
					+ "WHERE ta.DESC_BREVE_TIPO_ANAGRAFICA = ?";

			Object[] args = new Object[] { codiceRuolo };
			LOG.info("<codiceRuolo>: " + codiceRuolo);
			LOG.info(prf + "\n" + sql + "\n");

			int count = getJdbcTemplate().queryForObject(sql, args, int.class);
			if (count > 0) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in hasTipoAnagHelp: ", e);
			throw new ErroreGestitoException(" ERRORE in hasTipoAnagHelp.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Boolean esisteHelpByPaginaAndTipoAnag(String descBrevePagina, String codiceRuolo, Long idUtente,
			String idIride) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (descBrevePagina == null) {
			throw new InvalidParameterException("descBrevePagina non valorizzato");
		}
		if (codiceRuolo == null) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT COUNT(*) \r\n" + "FROM PBANDI_T_HELP h \r\n"
					+ "JOIN PBANDI_D_PAGINA_HELP ph ON ph.ID_PAGINA_HELP = h.ID_PAGINA_HELP \r\n"
					+ "JOIN PBANDI_R_GROUP_HELP_TIPO_ANAG gh ON gh.ID_GROUP_HELP = h.ID_GROUP_HELP\r\n"
					+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta ON ta.ID_TIPO_ANAGRAFICA = gh.ID_TIPO_ANAGRAFICA\r\n"
					+ "WHERE ph.DESC_BREVE_PAGINA_HELP = ? AND ta.DESC_BREVE_TIPO_ANAGRAFICA = ?";

			Object[] args = new Object[] { descBrevePagina, codiceRuolo };
			LOG.info("<descBrevePagina>: " + descBrevePagina + ", <codiceRuolo>: " + codiceRuolo);
			LOG.info(prf + "\n" + sql + "\n");

			int count = getJdbcTemplate().queryForObject(sql, args, int.class);
			LOG.info(prf + " trovati " + count + " risutati");

			if (count > 1) {
				throw new Exception("Risultato atteso 0 o 1, trovati " + count);
			}
			if (count == 1) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in esisteHelpByPaginaAndTipoAnag: ", e);
			throw new ErroreGestitoException(" ERRORE in esisteHelpByPaginaAndTipoAnag.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public HelpDTO getHelpByPaginaAndTipoAnag(String descBrevePagina, String codiceRuolo, Long idUtente, String idIride)
			throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (descBrevePagina == null) {
			throw new InvalidParameterException("descBrevePagina non valorizzato");
		}
		if (codiceRuolo == null) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT h.ID_HELP, h.TESTO_HELP \r\n" + "FROM PBANDI_T_HELP h \r\n"
					+ "JOIN PBANDI_D_PAGINA_HELP ph ON ph.ID_PAGINA_HELP = h.ID_PAGINA_HELP \r\n"
					+ "JOIN PBANDI_R_GROUP_HELP_TIPO_ANAG gh ON gh.ID_GROUP_HELP = h.ID_GROUP_HELP\r\n"
					+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta ON ta.ID_TIPO_ANAGRAFICA = gh.ID_TIPO_ANAGRAFICA\r\n"
					+ "WHERE ph.DESC_BREVE_PAGINA_HELP = ? AND ta.DESC_BREVE_TIPO_ANAGRAFICA = ?";

			Object[] args = new Object[] { descBrevePagina, codiceRuolo };
			LOG.info("<descBrevePagina>: " + descBrevePagina + ", <codiceRuolo>: " + codiceRuolo);
			LOG.info(prf + "\n" + sql + "\n");

			List<HelpDTO> helps = getJdbcTemplate().query(sql, args, new HelpRowMapper());
			if (helps == null || helps.size() == 0) {
				LOG.info(prf + " trovati 0 risutati");
			} else {
				LOG.info(prf + " trovati " + helps.size() + " risutati");
			}
			if (helps.size() > 1) {
				throw new Exception("Risultato atteso 0 o 1, trovati " + helps.size());
			}
			if (helps.size() == 1) {
				return helps.get(0);
			}
			return null;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getHelpByPaginaAndTipoAnag: ", e);
			throw new ErroreGestitoException(" ERRORE in getHelpByPaginaAndTipoAnag.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public EsitoOperazioni saveHelp(String descBrevePagina, HelpDTO helpDTO, String codiceRuolo, Long idUtente,
			String idIride) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (descBrevePagina == null) {
			throw new InvalidParameterException("descBrevePagina non valorizzato");
		}
		if (codiceRuolo == null) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}
		if (helpDTO == null || (helpDTO.getIdHelp() == null
				&& (helpDTO.getTestoHelp() == null || helpDTO.getTestoHelp().length() == 0))) {
			throw new InvalidParameterException("helpDTO non valorizzato");
		}

		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(Boolean.FALSE);

		try {
			String sql;
			// recupero idPagina
			sql = "SELECT ID_PAGINA_HELP \r\n" + "FROM PBANDI_D_PAGINA_HELP \r\n" + "WHERE DESC_BREVE_PAGINA_HELP = ?";
			Object[] args = new Object[] { descBrevePagina };
			LOG.info("<descBrevePagina>: " + descBrevePagina);
			LOG.info(prf + "\n" + sql + "\n");

			List<Long> idPagine = getJdbcTemplate().queryForList(sql, args, Long.class);

			if (idPagine == null || idPagine.size() == 0) {
				throw new InvalidParameterException(
						"parametro descBrevePagina non corretto, nessuna corrispondeza trovata");
			}
			Long idPagina = idPagine.get(0);

			// recupero idGroup
			sql = "SELECT gh.ID_GROUP_HELP\r\n" + "FROM PBANDI_R_GROUP_HELP_TIPO_ANAG gh \r\n"
					+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta  ON ta.ID_TIPO_ANAGRAFICA = gh.ID_TIPO_ANAGRAFICA\r\n"
					+ "WHERE ta.DESC_BREVE_TIPO_ANAGRAFICA = ?";
			args = new Object[] { codiceRuolo };
			LOG.info("<codiceRuolo>: " + codiceRuolo);
			LOG.info(prf + "\n" + sql + "\n");

			List<Long> idGroups = getJdbcTemplate().queryForList(sql, args, Long.class);

			if (idGroups == null || idGroups.size() == 0) {
				throw new InvalidParameterException("nessun gruppo trovato per il codiceRuolo " + codiceRuolo);
			}
			Long idGroup = idGroups.get(0);

			if (helpDTO.getIdHelp() == null) {
				// insert
				sql = "INSERT INTO PBANDI.PBANDI_T_HELP\r\n"
						+ "(ID_HELP, ID_PAGINA_HELP, ID_GROUP_HELP, TESTO_HELP, DT_INSERIMENTO, ID_UTENTE_AGG)\r\n"
						+ "VALUES(SEQ_PBANDI_T_HELP.nextval, ?, ?, ?, SYSDATE, ?)";
				args = new Object[] { idPagina, idGroup, helpDTO.getTestoHelp(), idUtente };
				LOG.info("<idPagina>: " + idPagina + ", <idGroup>: " + idGroup + ", <testoHelp>: "
						+ helpDTO.getTestoHelp() + ", <idUtente>: " + idUtente);
				LOG.info(prf + "\n" + sql + "\n");
				int row = getJdbcTemplate().update(sql, args);
				if (row != 1) {
					esito.setMsg("Errore durante il salvataggio dell'help.");
					return esito;
				}
			} else {
				if (helpDTO.getTestoHelp() != null && helpDTO.getTestoHelp().length() > 0) {
					// update
					sql = "UPDATE PBANDI.PBANDI_T_HELP \r\n"
							+ "SET TESTO_HELP = ?, DT_AGGIORNAMENTO = SYSDATE, ID_UTENTE_AGG = ? \r\n"
							+ "WHERE ID_HELP = ?";
					args = new Object[] { helpDTO.getTestoHelp(), idUtente, helpDTO.getIdHelp() };
					LOG.info("<idHelp>: " + helpDTO.getIdHelp() + ", <testoHelp>: " + helpDTO.getTestoHelp()
							+ ", <idUtente>: " + idUtente);
					LOG.info(prf + "\n" + sql + "\n");
					int row = getJdbcTemplate().update(sql, args);
					if (row != 1) {
						esito.setMsg("Errore durante il salvataggio dell'help.");
						return esito;
					}
				} else {
					// delete
					esito = deleteHelp(helpDTO.getIdHelp(), idUtente, idIride);
					if (esito.getEsito().equals(Boolean.FALSE)) {
						return esito;
					}
				}
			}
			esito.setEsito(Boolean.TRUE);
			esito.setMsg("Help salvato con successo.");
			return esito;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in saveHelp: ", e);
			throw new ErroreGestitoException(" ERRORE in saveHelp.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public EsitoOperazioni deleteHelp(Long idHelp, Long idUtente, String idIride) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idHelp == null) {
			throw new InvalidParameterException("idHelp non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(Boolean.FALSE);

		try {
			String sql;

			sql = "DELETE\r\n" + "FROM PBANDI_T_HELP \r\n" + "WHERE ID_HELP = ?";
			Object[] args = new Object[] { idHelp };
			LOG.info("<idHelp>: " + idHelp);
			LOG.info(prf + "\n" + sql + "\n");
			int row = getJdbcTemplate().update(sql, args);
			if (row != 1) {
				esito.setMsg("Errore durante l'eliminazione dell'help.");
				return esito;
			}
			esito.setEsito(Boolean.TRUE);
			esito.setMsg("Help eliminato con successo.");
			return esito;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in deleteHelp: ", e);
			throw new ErroreGestitoException(" ERRORE in deleteHelp.");
		} finally {
			LOG.info(prf + " END");
		}
	}

}
