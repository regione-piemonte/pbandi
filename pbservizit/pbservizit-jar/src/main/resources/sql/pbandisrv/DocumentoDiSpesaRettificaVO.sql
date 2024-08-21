/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct ds1.*,
      rdsp1.id_progetto,
      rdsp1.importo_rendicontazione,
      RDSP1.ID_STATO_DOCUMENTO_SPESA,
      nvl( sum(nc.importo_totale_documento) over (partition by ds1.id_documento_di_spesa),0) as totale_importo_note,
      nvl( sum(nc.importo_rendicontazione) over (partition by ds1.id_documento_di_spesa),0) as totale_rendicontabile_note,
	  dichiarazioni_aperte.dich_aperte_progetto,
      dichiarazioni_aperte.dich_aperte_totale
from PBANDI_T_DOCUMENTO_DI_SPESA ds1,
     PBANDI_R_DOC_SPESA_PROGETTO rdsp1,
(select ds.id_documento_di_spesa,
       ds.id_doc_riferimento,
       rdsp.id_progetto,
       ds.importo_totale_documento,
       0 as importo_rendicontazione
from PBANDI_T_DOCUMENTO_DI_SPESA ds,
     PBANDI_R_DOC_SPESA_PROGETTO rdsp
where ds.id_doc_riferimento is not null
  and ds.id_doc_riferimento = rdsp.id_documento_di_spesa
) nc,
(select 
   DISTINCT RPAGDOC.ID_DOCUMENTO_DI_SPESA,
  	DICH.ID_PROGETTO,
   SUM(NVL2(DICH.DT_CHIUSURA_VALIDAZIONE,0,1)) OVER (PARTITION BY RPAGDOC.ID_DOCUMENTO_DI_SPESA) AS DICH_APERTE_TOTALE
   ,sum(nvl2(dich.dt_chiusura_validazione,0,1)) over (partition BY rpagdoc.id_documento_di_spesa,DICH.ID_PROGETTO) as dich_aperte_progetto
  from PBANDI_R_PAGAMENTO_DICH_SPESA rpagDich,
       PBANDI_R_PAGAMENTO_DOC_SPESA  RPAGDOC,
       PBANDI_T_DICHIARAZIONE_SPESA DICH,
       PBANDI_R_DOC_SPESA_PROGETTO docprj
  where rpagDich.id_pagamento = rpagdoc.id_pagamento
    AND DICH.ID_DICHIARAZIONE_SPESA = RPAGDICH.ID_DICHIARAZIONE_SPESA
    AND DOCPRJ.ID_PROGETTO = DICH.ID_PROGETTO
    and DOCPRJ.ID_DOCUMENTO_DI_SPESA = RPAGDOC.ID_DOCUMENTO_DI_SPESA
) dichiarazioni_aperte
where rdsp1.id_documento_di_spesa = nc.id_doc_riferimento(+)
  and rdsp1.id_documento_di_spesa = ds1.id_documento_di_spesa
  and rdsp1.id_progetto = nc.id_progetto(+)
  AND DICHIARAZIONI_APERTE.ID_DOCUMENTO_DI_SPESA = DS1.ID_DOCUMENTO_DI_SPESA
  AND DICHIARAZIONI_APERTE.ID_PROGETTO = RDSP1.ID_PROGETTO