/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select rce.id_conto_economico,
       rce.id_rigo_conto_economico,
       rce.id_voce_di_spesa,
       vds.id_voce_di_spesa_padre,
       vds.desc_voce_di_spesa,
       case
           when vds.id_voce_di_spesa_padre is not null
               then (select desc_voce_di_spesa
                     from pbandi_d_voce_di_spesa
                     where id_voce_di_spesa = vds.id_voce_di_spesa_padre)
           end as desc_voce_di_spesa_padre,
       vds.id_tipologia_voce_di_spesa,
       rce.importo_ammesso_finanziamento
from pbandi_t_rigo_conto_economico rce,
     pbandi_d_voce_di_spesa vds
where rce.id_voce_di_spesa = vds.id_voce_di_spesa
  and NVL(TRUNC(rce.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  and NVL(TRUNC(vds.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)