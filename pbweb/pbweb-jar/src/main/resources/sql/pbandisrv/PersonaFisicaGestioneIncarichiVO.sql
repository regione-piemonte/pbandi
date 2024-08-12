select *
from PBandi_T_Persona_Fisica
where nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate+1)) > trunc(sysdate)