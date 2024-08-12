select * 
from (
select i.*,
       pe.desc_periodo_visualizzata,
       cat.desc_categ_anagrafica,
       p.codice_visualizzato,
       mr.DESC_MOTIVO_REVOCA,
       ti.desc_tipo_irregolarita,
       ti.desc_breve_tipo_irregolarita,
       qi.desc_qualificazione_irreg,
       qi.desc_breve_qualific_irreg,
       dc.desc_disp_comunitaria,
       dc.desc_breve_disp_comunitaria,
       mi.desc_metodo_ind,
       mi.desc_breve_metodo_ind,
       sa.desc_stato_amministrativo,
       sa.desc_breve_stato_amministrativ,
       sf.desc_stato_finanziario,
       sf.desc_breve_stato_finanziario,
       ns.desc_natura_sanzione,
       ns.desc_breve_natura_sanzione,
       rsp.id_soggetto as id_soggetto_beneficiario,
       case
     	when rsp.id_persona_fisica is null 
        then (
          select distinct eg.denominazione_ente_giuridico
          from PBANDI_T_ENTE_GIURIDICO eg
          where eg.id_ente_giuridico = rsp.id_ente_giuridico 
        ) else ( 
           select distinct pf.cognome || ' ' || pf.nome
           from PBANDI_T_PERSONA_FISICA pf
           where pf.id_persona_fisica = rsp.id_persona_fisica
        )
      end denominazione_beneficiario
from 
     PBANDI_T_IRREGOLARITA i 
       LEFT JOIN PBANDI_D_MOTIVO_REVOCA mr on i.ID_MOTIVO_REVOCA = mr.ID_MOTIVO_REVOCA
       LEFT JOIN PBANDI_T_PERIODO pe on i.ID_PERIODO = pe.ID_PERIODO
       LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA cat on i.ID_CATEG_ANAGRAFICA = cat.ID_CATEG_ANAGRAFICA
       LEFT JOIN PBANDI_D_TIPO_IRREGOLARITA ti on i.id_tipo_irregolarita = ti.id_tipo_irregolarita
       LEFT JOIN PBANDI_D_QUALIFICAZIONE_IRREG qi on i.id_qualificazione_irreg = qi.id_qualificazione_irreg
       LEFT JOIN PBANDI_D_DISP_COMUNITARIA dc on i.id_disp_comunitaria = dc.id_disp_comunitaria
       LEFT JOIN PBANDI_D_METODO_INDIVIDUAZIONE mi on i.id_metodo_individuazione = mi.id_metodo_individuazione
       LEFT JOIN PBANDI_D_STATO_AMMINISTRATIVO sa on i.id_stato_amministrativo = sa.id_stato_amministrativo
       LEFT JOIN PBANDI_D_STATO_FINANZIARIO sf on i.id_stato_finanziario = sf.id_stato_finanziario
       LEFT JOIN PBANDI_D_NATURA_SANZIONE ns on i.id_natura_sanzione = ns.id_natura_sanzione,
     PBANDI_T_PROGETTO p,
     PBANDI_R_SOGGETTO_PROGETTO rsp
where p.id_progetto = i.id_progetto
  and p.id_progetto = rsp.id_progetto 
  and rsp.id_tipo_anagrafica = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and nvl(rsp.id_tipo_beneficiario,'-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
  and NVL(TRUNC(i.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) irr
start with id_irregolarita_collegata is null
connect by prior id_irregolarita = id_irregolarita_collegata 
order siblings by numero_versione