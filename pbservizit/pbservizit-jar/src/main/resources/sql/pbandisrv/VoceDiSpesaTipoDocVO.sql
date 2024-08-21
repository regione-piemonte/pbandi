/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select dvds.ID_VOCE_DI_SPESA,
       dvds.DESC_VOCE_DI_SPESA,
       dvds.ID_VOCE_DI_SPESA_PADRE,
       case when dvds.id_voce_di_spesa_padre is not null
         then (select desc_voce_di_spesa from pbandi_d_voce_di_spesa where id_voce_di_spesa = dvds.id_voce_di_spesa_padre)
       end as DESC_VOCE_DI_SPESA_PADRE,
       rbvstd.ID_BANDO,
       rbvstd.ID_TIPO_DOCUMENTO_SPESA
from PBANDI_R_BANDO_VOCE_SP_TIP_DOC rbvstd, PBANDI_D_VOCE_DI_SPESA dvds
where dvds.ID_VOCE_DI_SPESA = rbvstd.ID_VOCE_DI_SPESA 
  and NVL(TRUNC(rbvstd.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)