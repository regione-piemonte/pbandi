/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct se.id_settore_ente, se.desc_settore, t.id_ente_competenza, t.id_soggetto, tec.desc_breve_tipo_ente_competenz
from PBANDI_R_ENTE_COMPETENZA_SOGG t, 
PBANDI_D_TIPO_ENTE_COMPETENZA tec, 
PBANDI_T_ENTE_COMPETENZA ec, 
pbandi_d_settore_ente se
where t.ID_ENTE_COMPETENZA = ec.ID_ENTE_COMPETENZA
AND ec.ID_TIPO_ENTE_COMPETENZA = tec.ID_TIPO_ENTE_COMPETENZA
and se.id_ente_competenza = ec.id_ente_competenza
and NVL(TRUNC(t.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
and NVL(TRUNC(tec.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
and NVL(TRUNC(ec.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
and NVL(TRUNC(se.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)