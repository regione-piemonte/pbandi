/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select PBANDI_D_FASE_MONIT.ID_FASE_MONIT ID_FASE_MONIT,    
       PBANDI_D_FASE_MONIT.COD_IGRUE_T35    COD_IGRUE_T35    ,
       ID_PROGETTO,
       DESC_FASE_MONIT    ,
       PBANDI_D_FASE_MONIT.ID_ITER ID_ITER,
       DESC_ITER,
       PBANDI_D_NATURA_CIPE.DESC_NATURA_CIPE DESC_TIPO_OPERAZIONE,
       FLAG_OBBLIGATORIO,    
       FLAG_CONTROLLO_INDICAT,
       DT_INIZIO_PREVISTA,
       DT_FINE_PREVISTA,
       DT_INIZIO_EFFETTIVA,
       DT_FINE_EFFETTIVA,
       DT_AGGIORNAMENTO,
       PBANDI_R_PROGETTO_FASE_MONIT.ID_MOTIVO_SCOSTAMENTO ID_MOTIVO_SCOSTAMENTO
       ,DESC_MOTIVO_SCOSTAMENTO
from
    PBANDI_D_FASE_MONIT,
    PBANDI_R_PROGETTO_FASE_MONIT,
    PBANDI_D_MOTIVO_SCOSTAMENTO,
    PBANDI_D_ITER,
    PBANDI_D_NATURA_CIPE
    where PBANDI_D_FASE_MONIT.ID_FASE_MONIT=PBANDI_R_PROGETTO_FASE_MONIT.ID_FASE_MONIT(+)
and PBANDI_D_MOTIVO_SCOSTAMENTO.ID_MOTIVO_SCOSTAMENTO(+)=PBANDI_R_PROGETTO_FASE_MONIT.ID_MOTIVO_SCOSTAMENTO
and NVL(TRUNC(PBANDI_D_FASE_MONIT.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
and PBANDI_D_ITER.ID_ITER=PBANDI_D_FASE_MONIT.ID_ITER
and PBANDI_D_ITER.ID_NATURA_CIPE = PBANDI_D_NATURA_CIPE.ID_NATURA_CIPE