/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

  select rce.id_conto_economico,
       rce.id_voce_di_spesa,
       case
         when vs.id_voce_di_spesa_padre is null
         then vs.desc_voce_di_spesa
         else (
                select distinct desc_voce_di_spesa || ' : : ' || vs.desc_voce_di_spesa
                from PBANDI_D_VOCE_DI_SPESA
                where id_voce_di_spesa = vs.id_voce_di_spesa_padre
             )
       end desc_voce_di_spesa,
       quotaPagDoc.id_progetto,
       quotaPagDoc.id_documento_di_spesa,
       quotaPagDoc.id_quota_parte_doc_spesa,
       docProg.id_tipo_documento_spesa,
       docProg.desc_tipo_documento_spesa,
       docProg.id_stato_documento_spesa,
       docProg.desc_stato_documento_spesa,
       pagDich.id_pagamento,
       pagDich.id_dichiarazione_spesa,
       docProg.id_fornitore,
       docProg.tipo_invio,
       case 
         when docProg.id_fornitore is not null
            then( 
                  select id_tipo_soggetto
                  from pbandi_t_fornitore  
                  where id_fornitore = docProg.id_fornitore
                 )
       END ID_TIPO_FORNITORE,
       docProg.desc_breve_tipo_doc_spesa,
       docProg.numero_documento,
       docProg.dt_emissione_documento,
       docProg.task,       
       case 
         when docProg.id_fornitore is not null
            then( 
                  select nvl(nome_fornitore,' ')
                  from pbandi_t_fornitore  
                  where id_fornitore = docProg.id_fornitore
                 )
            else ' '
       end nome_fornitore,
      case 
         when docProg.id_fornitore is not null
            then( 
                  select nvl(cognome_fornitore,' ')
                  from pbandi_t_fornitore  
                  where id_fornitore = docProg.id_fornitore
                 )
              else ' '
       end cognome_fornitore,
       case 
         when docProg.id_fornitore is not null
            then( 
                  select nvl(denominazione_fornitore,' ')
                  from pbandi_t_fornitore  
                  where id_fornitore = docProg.id_fornitore
                 )
             else ' '
       end denominazione_fornitore,
       case 
         when docProg.id_fornitore is not null
            then( 
                  select codice_fiscale_fornitore
                  from pbandi_t_fornitore  
                  where id_fornitore = docProg.id_fornitore
                 )
             else ' '
       end codice_fiscale_fornitore,
       case 
         when docProg.id_fornitore is not null
            then( 
                  select partita_iva_fornitore
                  from pbandi_t_fornitore  
                  where id_fornitore = docProg.id_fornitore
                 )
       end partita_iva_fornitore,
       pagDich.desc_modalita_pagamento,
       pagDich.desc_breve_modalita_pagamento,
       pagDich.dt_pagamento,
       pagDich.dt_valuta,
       nvl(pagDich.importo_pagamento,0) as importo_pagamento,
       nvl(docProg.importo_totale_documento,0) as importo_totale_documento,
       nvl(quotaPagDoc.importo_quietanzato,0) as importo_quietanzato,
       nvl(quotaPagDoc.importo_validato,0) as importo_validato,
       docProg.desc_breve_stato_doc_spesa
       ,quotaPagDoc.progr_pag_quot_parte_doc_sp
       ,nvl(quotaPagDoc.importo_totale_rettifica,0) as importo_totale_rettifica
       ,nvl(quotaPagDoc.importo_validato,0)-nvl(quotaPagDoc.importo_totale_rettifica,0) as importo_validato_lordo
       ,sum(quotaPagDoc.importo_totale_rettifica) over (partition by quotaPagDoc.id_progetto,quotaPagDoc.id_documento_di_spesa,ID_DICHIARAZIONE_SPESA) as totale_rettifica_doc
       ,subimportovalidato.VALIDATO_PER_DICHIARAZIONE
from 
(
select
PQPDS.ID_DICHIARAZIONE_SPESA SUB_ID_DICHIARAZIONE_SPESA,
qpds.ID_DOCUMENTO_DI_SPESA id_doc,
sum(pqpds.importo_validato) VALIDATO_PER_DICHIARAZIONE
from pbandi_r_pag_quot_parte_doc_sp pqpds, pbandi_r_pagamento_dich_spesa pds,
pbandi_t_quota_parte_doc_spesa qpds
where pds.id_dichiarazione_spesa = pqpds.id_dichiarazione_spesa
and PQPDS.ID_QUOTA_PARTE_DOC_SPESA = QPDS.ID_QUOTA_PARTE_DOC_SPESA
and PDS.ID_PAGAMENTO = PQPDS.ID_PAGAMENTO
group by PQPDS.ID_DICHIARAZIONE_SPESA,qpds.ID_DOCUMENTO_DI_SPESA
) subImportoValidato
,
PBANDI_T_RIGO_CONTO_ECONOMICO rce,
     PBANDI_D_VOCE_DI_SPESA vs,
     (   
        select distinct upd.id_pagamento,  
                        ds.id_progetto,
                        p.dt_pagamento,
                        p.dt_valuta,
                        p.importo_pagamento,
                        p.id_modalita_pagamento,
                        mp.id_modalita_pagamento,
                        mp.desc_modalita_pagamento,
                        MP.DESC_BREVE_MODALITA_PAGAMENTO,
                        rpds.id_documento_di_spesa,
                        ds.id_dichiarazione_spesa
                        
        from (
          select distinct
           pds.ID_PAGAMENTO,
           ds.id_progetto,
           first_value(ds.ID_DICHIARAZIONE_SPESA) over (partition by pds.ID_PAGAMENTO,ds.id_progetto order by ds.DT_DICHIARAZIONE desc, ds.ID_DICHIARAZIONE_SPESA desc) as ID_DICHIARAZIONE_SPESA
          from
           PBANDI_R_PAGAMENTO_DICH_SPESA pds,
           PBANDI_T_DICHIARAZIONE_SPESA ds
          where pds.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA            
        ) upd,
        PBANDI_T_DICHIARAZIONE_SPESA ds,
        PBANDI_T_PAGAMENTO p,
        PBANDI_D_MODALITA_PAGAMENTO MP,
        PBANDI_R_PAGAMENTO_DOC_SPESA rpds
        where ds.id_dichiarazione_spesa = upd.id_dichiarazione_spesa
          and p.id_pagamento = upd.id_pagamento
          AND P.ID_MODALITA_PAGAMENTO = MP.ID_MODALITA_PAGAMENTO
          and rpds.id_pagamento = p.id_pagamento
          and ds.dt_chiusura_validazione is not null
     ) pagDich,
     (
       select ds.id_documento_di_spesa,
              ds.id_fornitore,
              ds.numero_documento,
              ds.dt_emissione_documento,
              ds.importo_totale_documento,
              ds.id_tipo_documento_spesa,
              tds.desc_tipo_documento_spesa,
              tds.desc_breve_tipo_doc_spesa,
              rdsp.id_stato_documento_spesa,
              sds.desc_stato_documento_spesa,
              sds.desc_breve_stato_doc_spesa,
              rdsp.id_progetto,
              rdsp.task,
              rdsp.tipo_invio
       from PBANDI_T_DOCUMENTO_DI_SPESA ds,
            PBANDI_D_TIPO_DOCUMENTO_SPESA tds,
            PBANDI_D_STATO_DOCUMENTO_SPESA sds,
            PBANDI_R_DOC_SPESA_PROGETTO rdsp
       where ds.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
         and rdsp.id_stato_documento_spesa = sds.id_stato_documento_spesa
         and ds.id_documento_di_spesa = rdsp.id_documento_di_spesa
     ) docProg,
     (
      select qpds.id_quota_parte_doc_spesa,
             qpds.id_documento_di_spesa,
             qpds.id_progetto,
             qpds.id_rigo_conto_economico,
             rpqpds.id_pagamento,
             rpqpds.importo_quietanzato,
             rpqpds.importo_validato,
             rpqpds.progr_pag_quot_parte_doc_sp,
             sum(rett.importo_rettifica) as importo_totale_rettifica
      from PBANDI_T_QUOTA_PARTE_DOC_SPESA qpds
           LEFT OUTER JOIN  
           		PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpqpds
           ON 
           		qpds.id_quota_parte_doc_spesa = rpqpds.id_quota_parte_doc_spesa
           LEFT OUTER JOIN 
          		PBANDI_T_RETTIFICA_DI_SPESA rett
	       ON 
	          rpqpds.PROGR_PAG_QUOT_PARTE_DOC_SP = rett.PROGR_PAG_QUOT_PARTE_DOC_SP
	       GROUP BY qpds.id_quota_parte_doc_spesa,
	          qpds.id_documento_di_spesa,
	          qpds.id_progetto,
	          qpds.id_rigo_conto_economico,
	          rpqpds.id_pagamento,
	          rpqpds.importo_quietanzato,
	          rpqpds.importo_validato,
	          rpqpds.progr_pag_quot_parte_doc_sp
     ) quotaPagDoc
where rce.id_rigo_conto_economico = quotaPagDoc.id_rigo_conto_economico
  and pagDich.id_documento_di_spesa = docProg.id_documento_di_spesa
  and pagDich.id_progetto = docProg.id_progetto
  and SUBIMPORTOVALIDATO.SUB_ID_DICHIARAZIONE_SPESA = ID_DICHIARAZIONE_SPESA
  and subImportoValidato.ID_DOC = docProg.id_documento_di_spesa
  and quotaPagDoc.id_documento_di_spesa = pagDich.id_documento_di_spesa
  and quotaPagDoc.id_progetto = pagDich.id_progetto
  and (quotaPagDoc.id_pagamento = pagDich.id_pagamento  or quotaPagDoc.id_pagamento is null)
  AND VS.ID_VOCE_DI_SPESA = RCE.ID_VOCE_DI_SPESA