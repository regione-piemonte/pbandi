select ds.id_documento_di_spesa,
       qpds.id_progetto,
       ds.dt_emissione_documento,
       ds.id_tipo_documento_spesa,
       tds.desc_breve_tipo_doc_spesa,
       tds.desc_tipo_documento_spesa,
       rce.id_rigo_conto_economico,
       ds.id_doc_riferimento,
       vds.id_voce_di_spesa,
       qpds.id_quota_parte_doc_spesa,
       vds.cod_tipo_voce_di_spesa,
       vds.desc_voce_di_spesa,
       nvl2(vdsp.desc_voce_di_spesa, vdsp.desc_voce_di_spesa || ': ', '') ||
       vds.desc_voce_di_spesa             as desc_voce_di_spesa_completa,
       qpds.importo_quota_parte_doc_spesa as importo_voce_di_spesa,
       rce.importo_ammesso_finanziamento,
       qpds.ore_lavorate,
       vdsp.desc_voce_di_spesa            as desc_voce_di_spesa_padre,
       vds.id_voce_di_spesa_padre,
       vds.id_tipologia_voce_di_spesa
from pbandi_t_documento_di_spesa ds,
     pbandi_t_quota_parte_doc_spesa qpds,
     pbandi_d_voce_di_spesa vds,
     pbandi_d_voce_di_spesa vdsp,
     pbandi_t_rigo_conto_economico rce,
     pbandi_d_tipo_documento_spesa tds
where ds.id_documento_di_spesa = qpds.id_documento_di_spesa
  and rce.id_voce_di_spesa = vds.id_voce_di_spesa
  and rce.id_rigo_conto_economico = qpds.id_rigo_conto_economico 
  and vds.id_voce_di_spesa_padre = vdsp.id_voce_di_spesa (+)
  and ds.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
  and nvl(trunc(rce.dt_fine_validita), trunc(sysdate  +1)) > trunc(sysdate)
  and nvl(trunc(vds.dt_fine_validita), trunc(sysdate  +1)) > trunc(sysdate)
  and nvl(trunc(vdsp.dt_fine_validita), trunc(sysdate  +1)) > trunc(sysdate)