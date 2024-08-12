select qpds.id_progetto,
       sum(importo_quietanzato) as importo_quietanzato,
       sum(nvl(importo_validato,0)) as importo_validato
from pbandi_r_pag_quot_parte_doc_sp pqpds,
     pbandi_t_quota_parte_doc_spesa qpds
where pqpds.id_quota_parte_doc_spesa = qpds.id_quota_parte_doc_spesa
group by qpds.id_progetto