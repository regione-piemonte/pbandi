select distinct cig_proc_agg, id_progetto
from pbandi_t_procedura_aggiudicaz pa
where nvl(trunc(dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
	  and cig_proc_agg is not null

