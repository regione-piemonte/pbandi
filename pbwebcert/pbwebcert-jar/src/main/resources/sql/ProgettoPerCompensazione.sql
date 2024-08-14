select *
  from (
select (
select desc_breve_linea 
  from (select connect_by_root dl.id_linea_di_intervento_padre id_linea_radice,
         dl.id_linea_di_intervento
          from pbandi_d_linea_di_intervento dl
        connect by prior dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre
       ) dlr,
       (
select nvl((select rlim.id_linea_di_intervento_attuale from pbandi_r_linea_interv_mapping rlim where rlim.id_linea_di_intervento_migrata = id_linea_di_intervento), id_linea_di_intervento) id_linea_di_intervento,
       progr_bando_linea_intervento
  from pbandi_r_bando_linea_intervent
       ) rbl,
       pbandi_d_linea_di_intervento dl,
       pbandi_d_tipo_linea_intervento dtl
 where rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
   and dl.id_linea_di_intervento = dlr.id_linea_radice
   and dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
   and dtl.livello_tipo_linea = '2'
   and td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
       ) desc_asse,
       tp.codice_visualizzato,
       tdp.*
  from pbandi_t_dett_proposta_certif tdp,
       pbandi_t_progetto tp,
       pbandi_t_domanda td
 where tdp.id_progetto = tp.id_progetto
   and td.id_domanda = tp.id_progetto and id_proposta_certificaz = ?
   and flag_comp = 'S'
       )
order by id_proposta_certificaz, desc_asse, codice_visualizzato