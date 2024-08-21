/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select affidamenti.*, tipologia.DESCRIZIONE as descrizione_tipologia_variante
from pbandi_t_varianti_affidamenti affidamenti
join PBANDI_D_TIPOLOGIE_VARIANTI tipologia on affidamenti.ID_TIPOLOGIA_VARIANTE = tipologia.ID_TIPOLOGIA_VARIANTE