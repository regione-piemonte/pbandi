select *
from PBANDI_V_BANDO_LINEA_ENTE_COMP 
where TRUNC(sysdate) < NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(sysdate) +1)