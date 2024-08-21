/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT nome_bando_linea, progr_bando_linea_intervento, processo,id_soggetto,desc_breve_tipo_anagrafica,id_soggetto_beneficiario
FROM  pbandi_v_processo_ben_bl
where 1=1