/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--PBANDI_D_SOGGETTO_FINANZIATORE
ALTER TABLE PBANDI_D_SOGGETTO_FINANZIATORE ADD FLAG_AMM_FESR VARCHAR2(1);
COMMENT ON COLUMN PBANDI_D_SOGGETTO_FINANZIATORE.FLAG_AMM_FESR IS 'Concorre al calcolo dell''ammissibile FESR';

