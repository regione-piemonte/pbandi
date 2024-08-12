select 
      voceQuiet.id_dichiarazione_spesa id_dichiarazione_spesa,
      voceQuiet.id_progetto,
      voceQuiet.id_voce_di_spesa,
      vds.id_voce_di_spesa_padre,
      vds.desc_voce_di_spesa,
      case when vds.id_voce_di_spesa_padre is not null
        then ( select desc_voce_di_spesa from pbandi_d_voce_di_spesa where id_voce_di_spesa = vds.id_voce_di_spesa_padre )
      end as desc_voce_di_spesa_padre,
      voceVal.totale_validato,
      voceQuiet.totale_quietanzato,
      voceRend.totale_rendicontato,
      voceQuiet.importo_ammesso_finanziamento
from pbandi_d_voce_di_spesa vds,
 /* conto economico master */
( 
    select ce.id_conto_economico,
           p.id_progetto
     from pbandi_d_tipologia_conto_econ tce,
          pbandi_d_stato_conto_economico sce,
          pbandi_t_conto_economico ce,
          pbandi_t_progetto p
     where tce.id_tipologia_conto_economico = sce.id_tipologia_conto_economico
       and ce.id_stato_conto_economico = sce.id_stato_conto_economico
       and ce.id_domanda = p.id_domanda
       and tce.desc_breve_tipologia_conto_eco = 'MASTER'
       /* controllo della dt_fine_validita */
       and nvl(trunc(tce.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
       and nvl(trunc(sce.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
       and nvl(trunc(ce.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
) ceMaster,
/* validato */
(
  select distinct
         rce.id_voce_di_spesa,
         qpds.id_progetto,
         rce.id_conto_economico,
         sum(pqpds.importo_validato) over (partition by  rce.id_voce_di_spesa , qpds.id_progetto,  rce.id_conto_economico) as totale_validato
    from pbandi_r_pag_quot_parte_doc_sp pqpds,
         pbandi_t_quota_parte_doc_spesa qpds,
         pbandi_t_rigo_conto_economico rce
   where pqpds.id_quota_parte_doc_spesa = qpds.id_quota_parte_doc_spesa
     and qpds.id_rigo_conto_economico = rce.id_rigo_conto_economico
/* controllo della dt_fine_validita */
     and nvl(trunc(rce.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
) voceVal,
/* quietanzato, rendicontabile e ammesso a finanziamento */
(
  select distinct
         PAGDICHSPESA.ID_DICHIARAZIONE_SPESA,
         docPrj.ID_PROGETTO,
         rce.id_voce_di_spesa,
         rce.id_conto_economico,
         rce.importo_ammesso_finanziamento,
         sum (pqpds.importo_quietanzato) over (partition by pagdichspesa.id_dichiarazione_spesa,docPrj.ID_PROGETTO, rce.id_voce_di_spesa, rce.id_conto_economico, rce.importo_ammesso_finanziamento) as totale_quietanzato,
         sum (qpds.importo_quota_parte_doc_spesa) over (partition by pagdichspesa.id_dichiarazione_spesa,docPrj.ID_PROGETTO, rce.id_voce_di_spesa, rce.id_conto_economico, rce.importo_ammesso_finanziamento) as totale_rendicontato
  from pbandi_r_pagamento_dich_spesa pagDichSpesa,
       pbandi_r_pagamento_doc_spesa pagDocSpesa,
       pbandi_t_quota_parte_doc_spesa qpds,
       PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
       PBANDI_T_RIGO_CONTO_ECONOMICO RCE,
       PBANDI_R_DOC_SPESA_PROGETTO docPrj
  where pagDichSpesa.id_pagamento = pagdocspesa.id_pagamento
    and qpds.id_documento_di_spesa = pagdocspesa.id_documento_di_spesa
    AND QPDS.ID_PROGETTO = DOCPRJ.ID_PROGETTO
    and QPDS.ID_DOCUMENTO_DI_SPESA = DOCPRJ.ID_DOCUMENTO_DI_SPESA
    and qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa
    and pqpds.id_pagamento = pagdichspesa.id_pagamento
    and  rce.id_rigo_conto_economico = qpds.id_rigo_conto_economico 
    /* controllo della dt_fine_validita */
    AND NVL(TRUNC(RCE.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) voceQuiet,
(      select id_progetto,
      id_dichiarazione_spesa,
      id_voce_di_spesa,
      id_conto_economico,
      sum(importo_quota_parte_doc_spesa) as totale_rendicontato
from (
select distinct qpds.id_progetto,
      pagDichSpesa.id_dichiarazione_spesa,
      rce.id_voce_di_spesa,
      rce.id_conto_economico,
      qpds.id_documento_di_spesa,
      qpds.ID_QUOTA_PARTE_DOC_SPESA,
     qpds.importo_quota_parte_doc_spesa
from pbandi_r_pagamento_dich_spesa pagDichSpesa,
     pbandi_r_pagamento_doc_spesa pagDocSpesa,
     PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
     PBANDI_T_RIGO_CONTO_ECONOMICO RCE,
     PBANDI_R_DOC_SPESA_PROGETTO docPrj
where pagDichSpesa.id_pagamento = pagdocspesa.id_pagamento
  AND PAGDOCSPESA.ID_DOCUMENTO_DI_SPESA = QPDS.ID_DOCUMENTO_DI_SPESA
  AND DOCPRJ.ID_PROGETTO = QPDS.ID_PROGETTO
  and DOCPRJ.ID_DOCUMENTO_DI_SPESA = PAGDOCSPESA.ID_DOCUMENTO_DI_SPESA
  and rce.id_rigo_conto_economico = qpds.id_rigo_conto_economico
  AND NVL(TRUNC(RCE.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
   )
   group by  id_progetto,
     id_dichiarazione_spesa,
      id_voce_di_spesa,
      id_conto_economico)
voceRend
where ( ceMaster.id_conto_economico = voceVal.id_conto_economico and ceMaster.id_progetto = voceVal.id_progetto )
  and ( ceMaster.id_conto_economico = voceQuiet.id_conto_economico and ceMaster.id_progetto = voceQuiet.id_progetto )
  and ( ceMaster.id_conto_economico = voceRend.id_conto_economico and ceMaster.id_progetto = voceRend.id_progetto )
  and ( voceVal.id_voce_di_spesa = voceQuiet.id_voce_di_spesa and voceVal.id_progetto = voceQuiet.id_progetto and voceVal.id_conto_economico = voceQuiet.id_conto_economico )
  and ( voceVal.id_voce_di_spesa = voceRend.id_voce_di_spesa and voceVal.id_progetto = voceRend.id_progetto and voceVal.id_conto_economico = voceRend.id_conto_economico )
  and voceQuiet.id_dichiarazione_spesa = voceRend.id_dichiarazione_spesa
  and vds.id_voce_di_spesa = voceVal.id_voce_di_spesa
  AND NVL(TRUNC(VDS.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
order by desc_voce_di_spesa_padre, vds.desc_voce_di_spesa