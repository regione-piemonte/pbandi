/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DESC_TIPO_ANAGRAFICA AS descTipoAnagrafica,
       DESC_PERMESSO AS descPermesso,
       DESC_BREVE_TIPO_ANAGRAFICA AS descBreveTipoAnagrafica,
       DESC_BREVE_PERMESSO AS descBrevePermesso,
       DT_INIZIO_VALIDITA AS dtInizioValidita,
       ID_SOGGETTO AS idSoggetto,
       ID_PERMESSO AS idPermesso,
       DT_FINE_VALIDITA AS dtFineValidita,
       ID_TIPO_ANAGRAFICA AS idTipoAnagrafica
FROM (SELECT pt.*,
             rta.id_soggetto,
             ta.desc_breve_tipo_anagrafica,
             ta.desc_tipo_anagrafica,
             p.desc_breve_permesso,
             p.desc_permesso
      FROM pbandi_r_permesso_tipo_anagraf pt,
           pbandi_d_tipo_anagrafica ta,
           pbandi_d_permesso p,
           pbandi_r_sogg_tipo_anagrafica rta
      WHERE p.id_permesso = pt.id_permesso
      AND   ta.id_tipo_anagrafica = pt.id_tipo_anagrafica
      AND   rta.id_tipo_anagrafica = ta.id_tipo_anagrafica
      AND   (TRUNC(SYSDATE) >= NVL(TRUNC(p.DT_INIZIO_VALIDITA),TRUNC(SYSDATE) -1) AND TRUNC(SYSDATE) < NVL(TRUNC(p.DT_FINE_VALIDITA),TRUNC(SYSDATE) +1))
      AND   (TRUNC(SYSDATE) >= NVL(TRUNC(ta.DT_INIZIO_VALIDITA),TRUNC(SYSDATE) -1) AND TRUNC(SYSDATE) < NVL(TRUNC(ta.DT_FINE_VALIDITA),TRUNC(SYSDATE) +1))
      AND   (TRUNC(SYSDATE) >= NVL(TRUNC(pt.DT_INIZIO_VALIDITA),TRUNC(SYSDATE) -1) AND TRUNC(SYSDATE) < NVL(TRUNC(pt.DT_FINE_VALIDITA),TRUNC(SYSDATE) +1))
      AND   (TRUNC(SYSDATE) >= NVL(TRUNC(rta.DT_INIZIO_VALIDITA),TRUNC(SYSDATE) -1) AND TRUNC(SYSDATE) < NVL(TRUNC(rta.DT_FINE_VALIDITA),TRUNC(SYSDATE) +1)))
WHERE DESC_BREVE_TIPO_ANAGRAFICA = ?
AND   DESC_BREVE_PERMESSO = ?
AND   ID_SOGGETTO = ?