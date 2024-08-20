Select checklist.*, ac.id_appalto 
from pbandi_t_checklist checklist 
join pbandi_t_appalto_checklist ac on ac.id_checklist = checklist.id_checklist
where checklist.id_documento_index = ?
and ac.id_appalto = ?