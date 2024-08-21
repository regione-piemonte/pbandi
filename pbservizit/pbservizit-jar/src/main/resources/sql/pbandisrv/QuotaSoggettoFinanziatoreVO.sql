/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select f.id_soggetto_finanziatore, t.id_progetto, f.desc_sogg_finanziatore, t.perc_quota_sogg_finanziatore, f.flag_agevolato
from pbandi.PBANDI_R_PROG_SOGG_FINANZIAT t, 
pbandi_d_soggetto_finanziatore f 
where t.id_soggetto_finanziatore = f.id_soggetto_finanziatore