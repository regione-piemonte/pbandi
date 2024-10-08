/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select r.ID_BANDO, d.ID_ATECO, d.COD_ATECO, d.COD_ATECO_NORM, d.DESC_ATECO, d.COD_SEZIONE, d.COD_LIVELLO
from PBANDI_R_BANDI_ATECO_ESCL r, PBANDI_D_ATECO d
where d.ID_ATECO = r.ID_ATECO