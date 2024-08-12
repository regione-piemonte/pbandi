SELECT t.ID_PROGETTO , t.ID_FASE_MONIT , t.dt_fine_effettiva, t.estremi_atto_amministrativo, fm.ID_ITER , fm.COD_IGRUE_T35,
t.ID_UTENTE_INS, t.DT_INIZIO_EFFETTIVA, t.DT_AGGIORNAMENTO, t.ID_MOTIVO_SCOSTAMENTO, t.DT_FINE_PREVISTA, t.DT_INIZIO_PREVISTA, t.ID_UTENTE_AGG, t.DT_INSERIMENTO
FROM PBANDI_R_PROGETTO_FASE_MONIT t, pbandi_d_fase_monit fm
where t.id_fase_monit = fm.id_fase_monit
