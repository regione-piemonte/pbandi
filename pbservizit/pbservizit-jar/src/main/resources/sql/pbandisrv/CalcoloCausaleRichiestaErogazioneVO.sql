/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select sum(ceAge.QUOTA_IMPORTO_AGEVOLATO) IMPORTO_AGEVOLATO,
  richieste.*
from
  (select proCauErog.*,
    sum(richErog.IMPORTO_EROGAZIONE_RICHIESTO) GIA_RICHIESTO_CAUSALE
  from
    (select pro.ID_PROGETTO,
      banCauErog.ID_BANDO,
      ce.ID_CONTO_ECONOMICO,
      cauErog.ID_CAUSALE_EROGAZIONE,
      cauErog.DESC_BREVE_CAUSALE,
      cauErog.PROGR_ORDINAMENTO,
      cauErog.DESC_CAUSALE,
      banCauErog.PERC_EROGAZIONE,
      banCauErog.PERC_LIMITE
    from PBANDI_R_BANDO_CAUSALE_EROGAZ banCauErog,
      PBANDI_D_CAUSALE_EROGAZIONE cauErog,
      PBANDI_T_PROGETTO pro,
      PBANDI_T_DOMANDA dom,
      PBANDI_R_BANDO_LINEA_INTERVENT banLin,
      PBANDI_T_CONTO_ECONOMICO ce,
      PBANDI_D_STATO_CONTO_ECONOMICO statoCe,
      PBANDI_D_TIPOLOGIA_CONTO_ECON tipoCe,
      (select soggPro.ID_PROGETTO,
        soggPro.ID_SOGGETTO,
        ente.ID_FORMA_GIURIDICA,
        ente.ID_DIMENSIONE_IMPRESA
      from PBANDI_R_SOGGETTO_PROGETTO soggPro,
        PBANDI_D_TIPO_BENEFICIARIO tipoBen,
        pbandi_t_ente_giuridico ente
      where soggPro.ID_TIPO_ANAGRAFICA           = 1
      and soggPro.DT_FINE_VALIDITA              is null
      and nvl(soggPro.id_tipo_beneficiario, -1) <> 4
      and soggPro.ID_TIPO_BENEFICIARIO           = tipoBen.ID_TIPO_BENEFICIARIO
      and soggPro.ID_ENTE_GIURIDICO              = ente.ID_ENTE_GIURIDICO
      ) ben
    where banCauErog.ID_CAUSALE_EROGAZIONE         = cauErog.ID_CAUSALE_EROGAZIONE
    and pro.ID_DOMANDA                             = dom.ID_DOMANDA
    and dom.PROGR_BANDO_LINEA_INTERVENTO           = banLin.PROGR_BANDO_LINEA_INTERVENTO
    and banLin.ID_BANDO                            = banCauErog.ID_BANDO
    and pro.ID_PROGETTO                            = ben.ID_PROGETTO
    and (banCauErog.ID_FORMA_GIURIDICA             = ben.ID_FORMA_GIURIDICA
    or (banCauErog.ID_DIMENSIONE_IMPRESA           = ben.ID_DIMENSIONE_IMPRESA
    and banCauErog.ID_FORMA_GIURIDICA             is null)
    or (banCauErog.ID_FORMA_GIURIDICA             is null
    and banCauErog.ID_DIMENSIONE_IMPRESA          is null))
    and ce.ID_DOMANDA                              = dom.ID_DOMANDA
    and ce.DT_FINE_VALIDITA                       is null
    and ce.ID_STATO_CONTO_ECONOMICO                = statoCe.ID_STATO_CONTO_ECONOMICO
    and statoCe.ID_TIPOLOGIA_CONTO_ECONOMICO       = tipoCe.ID_TIPOLOGIA_CONTO_ECONOMICO
    and tipoCe.DESC_BREVE_TIPOLOGIA_CONTO_ECO not in ('COPY_IST', 'COPY_BEN')
    ) proCauErog,
    PBANDI_T_RICHIESTA_EROGAZIONE richErog
  where richErog.ID_PROGETTO(+)         = proCauErog.ID_PROGETTO
  and richErog.ID_CAUSALE_EROGAZIONE(+) = proCauErog.ID_CAUSALE_EROGAZIONE
  group by proCauErog.ID_PROGETTO,
    proCauErog.ID_BANDO,
    proCauErog.ID_CAUSALE_EROGAZIONE,
    proCauErog.PROGR_ORDINAMENTO,
    proCauErog.DESC_BREVE_CAUSALE,
    proCauErog.DESC_CAUSALE,
    proCauErog.PERC_EROGAZIONE,
    proCauErog.PERC_LIMITE,
    proCauErog.ID_CONTO_ECONOMICO
  order by proCauErog.ID_CAUSALE_EROGAZIONE
  ) richieste,
  PBANDI_R_CONTO_ECONOM_MOD_AGEV ceAge
where ceAge.ID_CONTO_ECONOMICO = richieste.ID_CONTO_ECONOMICO
group by richieste.ID_PROGETTO,
  richieste.ID_BANDO,
  richieste.ID_CAUSALE_EROGAZIONE,
  richieste.PROGR_ORDINAMENTO,
  richieste.DESC_BREVE_CAUSALE,
  richieste.DESC_CAUSALE,
  richieste.PERC_EROGAZIONE,
  richieste.PERC_LIMITE,
  richieste.ID_CONTO_ECONOMICO,
  richieste.GIA_RICHIESTO_CAUSALE