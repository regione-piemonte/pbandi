/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select psf.id_progetto,
       psf.id_soggetto_finanziatore,
       nvl(psf.imp_quota_sogg_finanziatore,0) as imp_quota_sogg_finanziatore, 
       sf.desc_sogg_finanziatore,
       sf.desc_breve_sogg_finanziatore,
       sf.flag_agevolato
from pbandi_r_prog_sogg_finanziat psf,
     pbandi_d_soggetto_finanziatore sf
where psf.id_soggetto_finanziatore = sf.id_soggetto_finanziatore