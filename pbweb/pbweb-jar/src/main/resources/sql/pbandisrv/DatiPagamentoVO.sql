select   
		 attoliq.id_atto_liquidazione, 
		 cod_mod_pag_bilancio as prog_mod_pag, 
         desc_breve_modalita_erogazione as cod_accre, 
         abi, 
         cab, 
         numero_conto as nro_cc, 
         cin, 
         iban, 
         bic, 
         denominazione as rag_soc_agg,
         desc_indirizzo as via_sede, 
         ti.cap as cap_sede,
         desc_comune as comune_sede,
         desc_comune_estero as comune_estero, 
         desc_provincia as prov_sede,
         email as mail_mod_pag
from
        pbandi_t_dati_pagamento_atto tdpa,
        pbandi_d_modalita_erogazione me,
        pbandi_t_estremi_bancari eb,
        pbandi_t_sede ts,
        pbandi_t_indirizzo ti,
        pbandi_t_recapiti tr,
        pbandi_d_comune dc,
        pbandi_d_provincia dp,
        pbandi_d_comune_estero ce,
        pbandi_t_atto_liquidazione attoliq
where 
        tdpa.id_dati_pagamento_atto = attoliq.id_dati_pagamento_atto and
        me.id_modalita_erogazione (+) = tdpa.id_modalita_erogazione and
        eb.id_estremi_bancari (+) = tdpa.id_estremi_bancari and
        ts.id_sede (+) = tdpa.id_sede and
        tr.id_recapiti (+) = tdpa.id_recapiti and
        ti.id_indirizzo (+) = tdpa.id_indirizzo and
        dc.id_comune(+) = ti.id_comune and
        dp.id_provincia(+) = ti.id_provincia and
        ce.id_comune_estero (+) = ti.id_comune_estero and
        NVL(TRUNC(ti.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) and
        NVL(TRUNC(ts.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) and
        NVL(TRUNC(tr.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)