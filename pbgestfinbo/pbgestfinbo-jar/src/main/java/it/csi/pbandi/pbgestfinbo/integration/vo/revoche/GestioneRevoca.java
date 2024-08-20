/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class GestioneRevoca implements Serializable, RowMapper<GestioneRevoca> {
	
	private Long ID_GESTIONE_REVOCA;
	private Long NUMERO_REVOCA;
	private Long ID_TIPOLOGIA_REVOCA;
	private Long ID_PROGETTO;
	private Long ID_CAUSALE_BLOCCO;
	private Long ID_CATEG_ANAGRAFICA;
	private Date DT_GESTIONE;
	private String NUM_PROTOCOLLO;
	private String FLAG_ORDINE_RECUPERO;
	private Long ID_MANCATO_RECUPERO;
	private Long ID_STATO_REVOCA;
	private Date DT_STATO_REVOCA;
	private Date DT_NOTIFICA;
	private Integer GG_RISPOSTA;
	private String FLAG_PROROGA;
	private Integer IMP_DA_REVOCARE_CONTRIB;
	private Integer IMP_DA_REVOCARE_FINANZ;
	private Integer IMP_DA_REVOCARE_GARANZIA;
	private String FLAG_DETERMINA;
	private String ESTREMI;
	private Date DT_DETERMINA;
	private String NOTE;
	private Long ID_ATTIVITA_REVOCA;
	private Date DT_ATTIVITA;
	private Long ID_MOTIVO_REVOCA;
	private String FLAG_CONTRIB_REVOCA;
	private String FLAG_CONTRIB_MINOR_SPESE;
	private String FLAG_CONTRIB_DECURTAZ;
	private String FLAG_FINANZ_REVOCA;
	private String FLAG_FINANZ_MINOR_SPESE;
	private String FLAG_FINANZ_DECURTAZ;
	private String FLAG_GARANZIA_REVOCA;
	private String FLAG_GARANZIA_MINOR_SPESE;
	private String FLAG_GARANZIA_DECURTAZ;
	private Integer IMP_CONTRIB_REVOCA_NO_RECU;
	private Integer IMP_CONTRIB_REVOCA_RECU;
	private Integer IMP_CONTRIB_INTERESSI;
	private Integer IMP_FINANZ_REVOCA_NO_RECU;
	private Integer IMP_FINANZ_REVOCA_RECU;
	private Integer IMP_FINANZ_INTERESSI;
	private Integer IMP_GARANZIA_REVOCA_NO_RECU;
	private Integer IMP_GARANZIA_REVOCA_RECUPERO;
	private Integer IMP_GARANZIA_INTERESSI;
	private Date DT_INIZIO_VALIDITA;
	private Date DT_FINE_VALIDITA;
	private Long ID_UTENTE_INS;
	private Long ID_UTENTE_AGG;
	private Date DT_INSERIMENTO;
	private Date DT_AGGIORNAMENTO;
	
	/*idGestioneRevoca
numeroRevoca
idTipologiaRevoca
idProgetto
idCausaleBlocco
idCategAnagrafica
dtGestione
numProtocollo
flagOrdineRecupero
idMancatoRecupero
idStatoRevoca
dtStatoRevoca
dtNotifica
ggRisposta
flagProroga
impDaRevocareContrib
impDaRevocareFinanz
impDaRevocareGaranzia
flagDetermina
estremi
dtDetermina
note
idAttivitaRevoca
dtAttivita
idMotivoRevoca
flagContribRevoca
flagContribMinorSpese
flagContribDecurtaz
flagFinanzRevoca
flagFinanzMinorSpese
flagFinanzDecurtaz
flagGaranziaRevoca
flagGaranziaMinorSpese
flagGaranziaDecurtaz
impContribRevocaNoRecu
impContribRevocaRecu
impContribInteressi
impFinanzRevocaNoRecu
impFinanzRevocaRecu
impFinanzInteressi
impGaranziaRevocaNoRecu
impGaranziaRevocaRecupero
impGaranziaInteressi
dtInizioValidita
dtFineValidita
idUtenteIns
idUtenteAgg
dtInserimento
dtAggiornamento

	 */
	
	
	
	
	
	@Override
	public GestioneRevoca mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
	
		return null;
	}

}
