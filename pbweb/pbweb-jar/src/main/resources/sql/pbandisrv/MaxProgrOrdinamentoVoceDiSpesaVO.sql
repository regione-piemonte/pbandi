SELECT R.ID_BANDO          AS ID_BANDO,
  MAX(R.PROGR_ORDINAMENTO) AS MAX_PROGR_ORDINAMENTO
FROM PBANDI_R_BANDO_VOCE_SPESA R
GROUP BY (R.ID_BANDO)