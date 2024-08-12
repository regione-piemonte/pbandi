select ce.nome_entita, ca.*
  from PBANDI_C_ATTRIBUTO ca,
       PBANDI_C_ENTITA ce
 where ce.ID_ENTITA = ca.ID_ENTITA
order by ca.KEY_POSITION_ID