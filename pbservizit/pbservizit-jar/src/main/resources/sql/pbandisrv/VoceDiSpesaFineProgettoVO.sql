/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT
distinct
rPagDich.ID_DICHIARAZIONE_SPESA,
voce2.DESC_VOCE_DI_SPESA AS desc_voce_di_spesa_padre,
voce1.DESC_VOCE_DI_SPESA AS desc_voce_di_spesa,
sum(rpagQuotaParte.IMPORTO_QUIETANZATO) over(partition by rPagDich.ID_DICHIARAZIONE_SPESA, rigo.id_rigo_CONTO_ECONOMICO ) AS importo_quietanzato,
rigo.IMPORTO_AMMESSO_FINANZIAMENTO AS importo_ammesso_finanziamento,
(SELECT SUM(rpagQuotaParte.IMPORTO_VALIDATO)importo_validato
FROM
PBANDI_T_RIGO_CONTO_ECONOMICO rigo1,
PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpagQuotaParte,
PBANDI_T_QUOTA_PARTE_DOC_SPESA quotaParte
WHERE
rigo1.ID_RIGO_CONTO_ECONOMICO= quotaParte.ID_RIGO_CONTO_ECONOMICO
and rigo.ID_RIGO_CONTO_ECONOMICO=rigo1.ID_RIGO_CONTO_ECONOMICO
and quotaParte.ID_QUOTA_PARTE_DOC_SPESA=rpagQuotaParte.ID_QUOTA_PARTE_DOC_SPESA
group by rigo1.id_rigo_conto_economico,
rigo.IMPORTO_AMMESSO_FINANZIAMENTO,rigo.ID_VOCE_DI_SPESA) importo_validato
FROM PBANDI_R_PAGAMENTO_DICH_SPESA rPagDich,
PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpagQuotaParte,
PBANDI_T_QUOTA_PARTE_DOC_SPESA tQuotaParte,
PBANDI_T_RIGO_CONTO_ECONOMICO rigo,
PBANDI_D_VOCE_DI_SPESA voce1,
PBANDI_D_VOCE_DI_SPESA voce2,
PBANDI_T_DICHIARAZIONE_SPESA dich
WHERE rPagDich.id_pagamento=rpagQuotaParte.id_pagamento
AND rpagQuotaParte.ID_QUOTA_PARTE_DOC_SPESA=tQuotaParte.ID_QUOTA_PARTE_DOC_SPESA
AND tQuotaParte.ID_RIGO_CONTO_ECONOMICO=rigo.ID_RIGO_CONTO_ECONOMICO
AND rigo.ID_VOCE_DI_SPESA=voce1.ID_VOCE_DI_SPESA
AND voce1.id_voce_di_spesa_padre = voce2.id_voce_di_spesa(+)
AND dich.ID_DICHIARAZIONE_SPESA = rPagDich.ID_DICHIARAZIONE_SPESA
AND dich.ID_PROGETTO = tQuotaParte.ID_PROGETTO
order by voce2.DESC_VOCE_DI_SPESA,voce1.DESC_VOCE_DI_SPESA


