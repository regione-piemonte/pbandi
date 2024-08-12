select pbandi_t_fideiussione.id_fideiussione,
  pbandi_t_fideiussione.id_progetto,
  pbandi_t_erogazione.id_progetto
from pbandi_t_fideiussione,
  pbandi_d_causale_erogazione,
  pbandi_t_erogazione
where pbandi_t_erogazione.id_causale_erogazione                                = pbandi_d_causale_erogazione.id_causale_erogazione
and pbandi_t_fideiussione.id_tipo_trattamento                                 >= pbandi_d_causale_erogazione.id_tipo_trattamento
and pbandi_t_erogazione.id_progetto                                            = pbandi_t_fideiussione.id_progetto
and nvl(trunc(pbandi_d_causale_erogazione.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)