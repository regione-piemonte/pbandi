/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select pbandi_d_tipo_indicatore.id_tipo_indicatore id_tipo_indicatore,
       desc_tipo_indicatore,
       pbandi_d_indicatori.id_indicatori  id_indicatori,
       desc_indicatore,
       cod_igrue,
       desc_unita_misura,
       flag_monit,
       flag_obbligatorio,
       flag_non_applicabile,
       valore_prog_iniziale,
       valore_concluso,
       valore_prog_agg,
       id_domanda
from  pbandi_d_indicatori,
      pbandi_d_tipo_indicatore ,
      pbandi_r_domanda_indicatori,
      pbandi_d_unita_misura
where pbandi_d_indicatori.id_tipo_indicatore=pbandi_d_tipo_indicatore.id_tipo_indicatore
and pbandi_r_domanda_indicatori.id_indicatori=pbandi_d_indicatori.id_indicatori
and pbandi_d_unita_misura.id_unita_misura=pbandi_d_indicatori.id_unita_misura
and nvl(trunc(pbandi_d_indicatori.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
and nvl(trunc(pbandi_d_tipo_indicatore.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
order by desc_tipo_indicatore,desc_indicatore