select docdich.id_dichiarazione_spesa,
       NVL2(statodoc.DESC_breve_stato_doc_spesa,statodoc.DESC_breve_stato_doc_spesa,'totale') AS DESC_STATO,
       nvl2(SUM(docdich.num_documenti), SUM(docdich.num_documenti), 0) num_documenti
from (
select distinct dich.id_dichiarazione_spesa,
	       docprj.id_stato_documento_spesa_valid,
          count(distinct docprj.id_documento_di_spesa)  as num_documenti
	from pbandi_r_pagamento_dich_spesa pagDich,
	     pbandi_t_dichiarazione_spesa dich,
	     pbandi_r_pagamento_doc_spesa pagdoc,
	     pbandi_r_doc_spesa_progetto docprj
	where dich.id_dichiarazione_spesa = pagdich.id_dichiarazione_spesa 
	  and pagdoc.id_pagamento = pagdich.id_pagamento
	  and pagdoc.id_documento_di_spesa = docprj.id_documento_di_spesa
	  and dich.id_progetto = docprj.id_progetto
    --and dich.id_dichiarazione_spesa = :idDichiarazioneSpesa
    group by 
	  dich.id_dichiarazione_spesa, 
	  docprj.id_stato_documento_spesa_valid
union all
select distinct dich.id_dichiarazione_spesa,
	       noteprj.id_stato_documento_spesa_valid,
         count(distinct noteprj.id_documento_di_spesa)  as num_documenti
	from pbandi_r_pagamento_dich_spesa pagDich,
	     pbandi_t_dichiarazione_spesa dich,
	     pbandi_r_pagamento_doc_spesa pagdoc,
	     pbandi_r_doc_spesa_progetto docprj,
       pbandi_t_documento_di_spesa note,
       pbandi_r_doc_spesa_progetto noteprj
	where dich.id_dichiarazione_spesa = pagdich.id_dichiarazione_spesa 
	  and pagdoc.id_pagamento = pagdich.id_pagamento
	  and pagdoc.id_documento_di_spesa = docprj.id_documento_di_spesa
	  and dich.id_progetto = docprj.id_progetto
    and note.id_doc_riferimento = docprj.id_documento_di_spesa
    and note.id_documento_di_spesa = noteprj.id_documento_di_spesa
    and docprj.id_progetto =  noteprj.id_progetto
    --and dich.id_dichiarazione_spesa = :idDichiarazioneSpesa
   group by 
	  dich.id_dichiarazione_spesa, 
	  noteprj.id_stato_documento_spesa_valid
) docdich,
pbandi_d_stato_documento_spesa statodoc
where docdich.id_stato_documento_spesa_valid = statodoc.id_stato_documento_spesa
GROUP BY ROLLUP(
   docdich.ID_DICHIARAZIONE_SPESA,
   statodoc.DESC_breve_stato_doc_spesa
)