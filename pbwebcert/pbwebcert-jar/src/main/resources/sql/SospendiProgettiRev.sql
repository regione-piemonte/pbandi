update PBANDI_T_PREVIEW_DETT_PROP_REV 
set ID_UTENTE_AGG = ? , 
FLAG_ATTIVO = 'N' 
where ID_PREVIEW_DETT_PROP_CER = (SELECT ptpdpr.id_preview_dett_prop_cer 
									FROM pbandi_t_preview_dett_prop_rev ptpdpr
									JOIN pbandi_t_preview_dett_prop_cer ptpdpc 
										ON ptpdpr.id_proposta_certificaz = ptpdpc.id_proposta_certificaz 
										AND ptpdpr.id_progetto = ptpdpc.id_progetto
									WHERE ptpdpc.id_preview_dett_prop_cer = ?
									)