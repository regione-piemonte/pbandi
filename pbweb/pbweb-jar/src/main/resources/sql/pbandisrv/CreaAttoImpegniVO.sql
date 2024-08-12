select 
rl.id_atto_liquidazione,
tc.numero_capitolo as nro_capitolo,
ti.anno_esercizio as anno_esercizio,
prov.anno_provvedimento as anno_prov, 
prov.numero_provvedimento as nro_prov, 
prov.tipo_provvedimento as tipo_prov, 
substr(prov.desc_breve_ente, 0, 4) as direzione,
ti.anno_impegno as anno_imp,
ti.numero_impegno as nro_imp,
rl.importo_liquidato as importo,
rl.cup_liquidazione as cup,
rl.cig_liquidazione as cig 
from 
pbandi_t_impegno ti,
pbandi_r_liquidazione rl,
pbandi_t_capitolo tc,
( /* provvedimento */
        select provv.id_provvedimento,
               tipo_provv.desc_breve_tipo_provvedimento as tipo_provvedimento,
               provv.anno_provvedimento, 
               provv.numero_provvedimento, 
               entec.desc_breve_ente
          from pbandi_t_provvedimento provv,
               pbandi_t_ente_competenza entec,
               pbandi_d_tipo_provvedimento tipo_provv
         where tipo_provv.id_tipo_provvedimento (+)= provv.id_tipo_provvedimento and
          entec.id_ente_competenza (+)= provv.id_ente_competenza and
          NVL(TRUNC(tipo_provv.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) and
          NVL(TRUNC(entec.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) prov
where rl.id_impegno = ti.id_impegno and
      prov.id_provvedimento (+) = ti.id_provvedimento and
      tc.id_capitolo = ti.id_capitolo and
      NVL(TRUNC(rl.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)