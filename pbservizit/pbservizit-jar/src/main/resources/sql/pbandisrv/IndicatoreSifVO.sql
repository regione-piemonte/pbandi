/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select pdti.id_tipo_indicatore,
       pdti.desc_tipo_indicatore,
       pdi.id_indicatori,
       pdi.desc_indicatore,
       pdi.cod_igrue,
       pdum.desc_unita_misura,
       pdti.flag_monit,
       pdi.flag_obbligatorio,
       pvsi.ID_PROGETTO,
       pvsi.VALORE_PROG_INIZIALE_SUM AS valore_iniziale,
       pvsi.VALORE_CONCLUSO_SUM AS valore_finale,
       pvsi.VALORE_PROG_AGG_SUM AS valore_aggiornamento
from  PBANDI_D_INDICATORI pdi,
      PBANDI_D_TIPO_INDICATORE pdti,
      PBANDI_D_UNITA_MISURA pdum,
      PBANDI_V_SIF_INDICATORI_21_27 pvsi 
where pdi.id_tipo_indicatore=pdti.id_tipo_indicatore
and pdum.id_unita_misura=pdi.id_unita_misura
AND pvsi.ID_INDICATORI = pdi.ID_INDICATORI 
and nvl(trunc(pdi.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
and nvl(trunc(pdti.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
and nvl(trunc(pdum.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
order by desc_tipo_indicatore, desc_indicatore