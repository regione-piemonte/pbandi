/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select t.*, r.id_progetto from PBANDI_T_CAMPIONAMENTO t, PBANDI_R_CAMPIONAMENTO r
where r.id_campione= t.id_campione