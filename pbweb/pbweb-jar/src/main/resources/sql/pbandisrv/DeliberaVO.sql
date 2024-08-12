select d.*,
  d.numero
  || ' ('
  || d.anno
  || ') '
  || d.desc_quota as desc_delibera
from pbandi_d_delibera d