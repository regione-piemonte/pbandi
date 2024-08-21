/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select conto1.*,
       erog.importo_liquidazioni
  from
(
select conto.*,
       ma.id_modalita_agevolazione,
       ma.desc_breve_modalita_agevolaz,
       ma.desc_modalita_agevolazione,
       ma.flag_lvlprj,
       ma.massimo_importo_agevolato, 
       ma.perc_importo_agevolato_bando,
       ma.quota_importo_agevolato,
       ma.perc_importo_agevolato,
       ma.quota_importo_richiesto
  from
(
select tce.*,
       rbl.id_bando,
       tp.id_progetto
  from pbandi_t_conto_economico tce,
       pbandi_t_domanda td,
       pbandi_t_progetto tp,
       pbandi_r_bando_linea_intervent rbl
 where tce.id_domanda = td.id_domanda
   and td.id_domanda = tp.id_domanda
   and td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
) conto,
(
select dma.id_modalita_agevolazione,
       dma.desc_breve_modalita_agevolaz,
       dma.desc_modalita_agevolazione,
       rbma.massimo_importo_agevolato,
       rbma.percentuale_importo_agevolato perc_importo_agevolato_bando,
       nvl(rcema.id_conto_economico, rbma.id_conto_economico) id_conto_economico,
       nvl2(rcema.id_conto_economico, 'S', 'N') flag_lvlprj,
       rcema.quota_importo_agevolato,
       nvl(rcema.perc_importo_agevolato, rbma.percentuale_importo_agevolato) perc_importo_agevolato,
       rcema.quota_importo_richiesto
  from pbandi_r_conto_econom_mod_agev rcema
full outer join
(
select rbma.*,
       tce.id_conto_economico
  from pbandi_r_bando_modalita_agevol rbma,
       pbandi_r_bando_linea_intervent rbl,
       pbandi_t_domanda td,
       pbandi_t_conto_economico tce
 where rbma.id_bando = rbl.id_bando
   and rbl.progr_bando_linea_intervento = td.progr_bando_linea_intervento
   and td.id_domanda = tce.id_domanda
) rbma 
    on rbma.id_modalita_agevolazione = rcema.id_modalita_agevolazione
   and rbma.id_conto_economico = rcema.id_conto_economico,
       pbandi_d_modalita_agevolazione dma
 where dma.id_modalita_agevolazione = nvl(rcema.id_modalita_agevolazione, rbma.id_modalita_agevolazione)
) ma
 where ma.id_conto_economico = conto.id_conto_economico
) conto1,
(
select tal.id_progetto,
       tal.id_modalita_agevolazione,
       sum(tal.importo_atto) importo_liquidazioni
  from pbandi_t_atto_liquidazione tal,
       pbandi_d_stato_atto sda
  where tal.id_stato_atto = sda.id_stato_atto
       and sda.desc_breve_stato_atto <> '0' 
       and sda.desc_breve_stato_atto <> '3' 
group by tal.id_progetto, tal.id_modalita_agevolazione
) erog
 where conto1.id_progetto = erog.id_progetto (+)
   and conto1.id_modalita_agevolazione = erog.id_modalita_agevolazione (+)
order by desc_modalita_agevolazione