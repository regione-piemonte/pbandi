/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

Select checklist.*, ac.id_appalto from pbandi_t_checklist checklist join pbandi_t_appalto_checklist ac on ac.id_checklist = checklist.id_checklist