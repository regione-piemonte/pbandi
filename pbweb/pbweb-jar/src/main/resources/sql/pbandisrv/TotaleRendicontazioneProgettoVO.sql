
select dsp.id_progetto,
       sum (case when tipodoc.desc_breve_tipo_doc_spesa = 'NC'
         then -1*dsp.importo_rendicontazione
         else dsp.importo_rendicontazione
       end  )as importo_rendicontazione
from pbandi_r_doc_spesa_progetto dsp,
     pbandi_t_documento_di_spesa ds,
     pbandi_d_tipo_documento_spesa tipodoc
where dsp.id_documento_di_spesa = ds.id_documento_di_spesa
  and ds.id_tipo_documento_spesa = tipodoc.id_tipo_documento_spesa
  and nvl(trunc(tipodoc.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
  group by dsp.id_progetto