select i.*, ds.DT_DICHIARAZIONE DATA_DICHIARAZIONE, ds.ID_PROGETTO
from PBANDI_T_DICHIARAZIONE_SPESA ds, PBANDI_T_INTEGRAZIONE_SPESA i
where i.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA