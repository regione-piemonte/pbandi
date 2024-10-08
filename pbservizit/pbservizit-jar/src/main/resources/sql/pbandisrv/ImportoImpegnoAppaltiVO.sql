/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select SUM(APP.IMP_RENDICONTABILE) as Importo_Impegno, pa.id_progetto from PBANDI_R_PROGETTI_APPALTI PA, PBANDI_T_APPALTO APP
    where PA.ID_APPALTO = APP.ID_APPALTO
    AND APP.ID_STATO_AFFIDAMENTO IN (2,3)
    group by id_progetto