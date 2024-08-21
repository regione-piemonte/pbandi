/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select d.*,
  d.numero
  || ' ('
  || d.anno
  || ') '
  || d.desc_quota as desc_delibera
from pbandi_d_delibera d