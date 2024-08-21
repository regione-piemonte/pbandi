/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select atto.ID_ATTO_LIQUIDAZIONE,
atto.ANNO_ATTO,
atto.NUMERO_ATTO,
atto.DESC_ATTO,
atto.DT_EMISSIONE_ATTO,
atto.DT_AGGIORNAMENTO_BILANCIO,
atto.DT_SCADENZA_ATTO,
atto.DT_COMPLETAMENTO_ATTO,
atto.DT_ANNULAMENTO_ATTO,
atto.DT_RICEZIONE_ATTO,
atto.DT_RICHIESTA_MODIFICA,
atto.TESTO_RICHIESTA_MODIFICA,
atto.NOTE_ATTO,
atto.FLAG_ALLEGATI_FATTURE,
atto.FLAG_ALLEGATI_ESTRATTO_PROV,
atto.FLAG_ALLEGATI_DOC_GIUSTIFICAT,
atto.FLAG_ALLEGATI_DICHIARAZIONE,
atto.TESTO_ALLEGATI_ALTRO,
atto.IMP_NON_SOGGETTO_RITENUTA,
atto.DT_INPS_DAL,
atto.DT_INPS_AL,
atto.DT_INSERIMENTO,
atto.DT_AGGIORNAMENTO,
atto.ID_UTENTE_INS,
atto.ID_UTENTE_AGG,
atto.ID_PROGETTO,
atto.ID_STATO_ATTO,
atto.ID_BENEFICIARIO_BILANCIO,
atto.ID_BENEFIC_BILANCIO_CEDENTE,
atto.ID_BENEFIC_BILANCIO_CEDUTO,
atto.ID_DATI_PAGAMENTO_ATTO,
atto.ID_DATI_PAGAM_ATTO_BEN_CEDUTO,
atto.ID_SITUAZIONE_INPS,
atto.ID_ATTIVITA_INPS,
atto.ID_RISCHIO_INAIL,
atto.ID_ALTRA_CASSA_PREVIDENZ,
atto.ID_TIPO_ALTRA_CASSA_PREV,
atto.ID_ALIQUOTA_RITENUTA,
atto.ID_MODALITA_AGEVOLAZIONE,
atto.ID_CAUSALE_EROGAZIONE,
atto.ID_SETTORE_ENTE,
atto.NUMERO_TELEFONO_LIQUIDATORE,
atto.NOME_LIQUIDATORE,
atto.NOME_DIRIGENTE_LIQUIDATORE,
atto.DT_RIFIUTO_RAGIONERIA,
atto.NUMERO_DOCUMENTO_SPESA,
nvl(atto.IMPORTO_ATTO,0) as importo_atto,
nvl(liquidazione.importo_liquidato_atto,0) as importo_liquidato_atto
from  pbandi_t_atto_liquidazione atto left outer join
(
  select liq.id_atto_liquidazione, 
       sum(liq.importo_liquidato) as importo_liquidato_atto
  from pbandi_r_liquidazione liq
 where liq.progr_liquidazione_precedente is null
/* stato liquidazione diverso da ANNULLATO */
   and liq.id_stato_liquidazione not in  ( select id_stato_liquidazione 
                                             from pbandi_d_stato_liquidazione
                                            where desc_breve_stato_liquidazione = 3 
                                      )
  and nvl(trunc(liq.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
  group by liq.id_atto_liquidazione
) liquidazione  on  atto.id_atto_liquidazione = liquidazione.id_atto_liquidazione
/* stato atto uguale a BOZZA, IN LAVORAZIONE o BLOCCATO */
where atto.id_stato_atto in (1,9,10) 