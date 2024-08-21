/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- PBANDI_T_DETT_PROPOSTA_CERTIF
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF ADD
(valore_n NUMBER (17,2),
 valore_n_1 NUMBER(17,2),
 somma_valori_n NUMBER(17,2)
);
-- pbandi_v_cert_chiusura_conti
CREATE OR REPLACE VIEW PBANDI_V_CERT_CHIUSURA_CONTI AS
SELECT
             annual.id_proposta_certificaz,
             tdett.id_dett_proposta_certif,
             tdett.id_progetto,
             NVL(annual.importo_certif_netto_annual,0) - NVL(tdett.importo_cert_net_anno_in_corso,0) as DIFF_CNA,
             NVL(annual.IMPORTO_REVOCHE_RILEV_CUM,0) - NVL(tdett.IMP_REVOCHE_NETTO_SOPPRESSIONI,0)as DIFF_REV,
             NVL(annual.IMPORTO_RECUPERI_CUM,0) - NVL(tdett.importo_recuperi,0)as DIFF_REC,
             NVL(annual.importo_soppressioni_cum,0) - NVL(tdett.importo_soppressioni,0) as DIFF_SOPPR
FROM         PBANDI_T_DETT_PROPOSTA_CERTIF tdett,
             pbandi_t_dett_prop_cert_annual annual
where        annual.id_proposta_certificaz = (SELECT max(ID_PROPOSTA_CERTIFICAZ)
                                              FROM PBANDI_T_PROPOSTA_CERTIFICAZ
                                              WHERE ID_STATO_PROPOSTA_CERTIF = 17 ) AND -- chiusura conti
             annual.id_dett_proposta_certif = tdett.id_dett_proposta_certif;