select *
from
  (
    SELECT TP.*,
           RB.PROGR_BANDO_LINEA_INTERVENTO ID_BANDO_LINEA,
           SUBSTR(ID_ISTANZA_PROCESSO, INSTR(ID_ISTANZA_PROCESSO, '$', -1, 1) -3, 3) VERSIONE_PROCESSO,
           rb.nome_bando_linea descrizione_bando,
           rb.id_processo,
           bando.titolo_bando,
     nvl2( soggPro.id_ente_giuridico,
           (select  nvl2(eg.denominazione_ente_giuridico, 
                        eg.denominazione_ente_giuridico, null
                        ) 
            from pbandi_t_ente_giuridico eg 
           where eg.id_ente_giuridico = soggPro.id_ente_giuridico
             and eg.id_soggetto       = soggPro.id_soggetto
          ),
             
          (select nvl2(pf.nome, pf.nome || ' ', null) || 
                 nvl2(pf.cognome, pf.cognome, null) 
           from pbandi_t_persona_fisica pf
          where pf.id_persona_fisica = soggPro.id_persona_fisica
            and pf.id_soggetto       = soggPro.id_soggetto)
          ) || ' - ' || sogg.codice_fiscale_soggetto
     as beneficiario
  FROM PBANDI_T_PROGETTO TP,
       PBANDI_T_DOMANDA TD,
       PBANDI_R_BANDO_LINEA_INTERVENT RB,
       pbandi_r_soggetto_progetto soggpro,
       pbandi_t_soggetto sogg,
       pbandi_t_bando bando
  WHERE TP.id_progetto = ?
    and TP.ID_DOMANDA                                           = TD.ID_DOMANDA
    and RB.PROGR_BANDO_LINEA_INTERVENTO                         = TD.PROGR_BANDO_LINEA_INTERVENTO
    and soggPro.id_progetto                                     = TP.id_progetto
    and soggPro.ID_TIPO_ANAGRAFICA = (select m.id_tipo_anagrafica from pbandi_d_tipo_anagrafica m where m.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
    and nvl(soggPro.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
    and sogg.id_soggetto = soggpro.id_soggetto
    and bando.id_bando = rb.id_bando
    and NVL(TRUNC(soggPro.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
  )
order by codice_visualizzato,
  titolo_progetto,
  beneficiario