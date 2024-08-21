/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select VPAGPRJDOC.ID_DOCUMENTO_DI_SPESA,
       VPAGPRJDOC.ID_PAGAMENTO,
       VPAGPRJDOC.ID_PROGETTO,
       DOC.IMPORTO_TOTALE_DOCUMENTO,
       vpagprjdoc.ir as importo_rendicontazione_netto,
       nvl(VPAGPRJDOC.IR_NC,0) as importo_rendicontazione_nc,
       VPAGPRJDOC.IP as importo_pagamento,
       VPAGPRJDOC.IQ as importo_quietanzato,
       VPAGPRJDOC.IV as importo_validato,
       sum(vpagprjdoc.delta_i) as residuo_utile_pagamento,
       case when exists ( select 'x' 
          from pbandi_r_pagamento_dich_spesa pagdich,
               pbandi_t_dichiarazione_spesa dich
          where pagdich.id_pagamento = VPAGPRJDOC.ID_PAGAMENTO
                and dich.id_dichiarazione_spesa = pagdich.id_dichiarazione_spesa
                and dich.id_progetto = VPAGPRJDOC.ID_PROGETTO
                )
         then 'S'
       else
        'N'
        end flag_is_used_dich_prj
from PBANDI_V_PAGAM_PROG_DOC_SPESA vpagprjdoc,
     PBANDI_T_DOCUMENTO_DI_SPESA doc
where DOC.ID_DOCUMENTO_DI_SPESA = VPAGPRJDOC.ID_DOCUMENTO_DI_SPESA
group by VPAGPRJDOC.ID_DOCUMENTO_DI_SPESA,
       VPAGPRJDOC.ID_PAGAMENTO,
       VPAGPRJDOC.ID_PROGETTO,
       DOC.IMPORTO_TOTALE_DOCUMENTO,
       vpagprjdoc.ir,
       VPAGPRJDOC.IR_NC,
       VPAGPRJDOC.IP,
       VPAGPRJDOC.IQ,
       VPAGPRJDOC.IV