select * from PBANDI_D_INVIO_PROPOSTA_CERTIF where  ( ID_STATO_PROPOSTA_CERTIF = 18 ) 
  and  ( trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) 
  and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1) )  and  ( ID_LINEA_DI_INTERVENTO in (?) )
