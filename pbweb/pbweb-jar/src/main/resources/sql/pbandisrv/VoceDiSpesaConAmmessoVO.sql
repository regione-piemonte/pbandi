select
vociBando.ID_PROGETTO ID_PROGETTO,
vociBando.DESC_VOCE_DI_SPESA DESC_VOCE_DI_SPESA,
vociBando.ID_VOCE_DI_SPESA ID_VOCE_DI_SPESA,
vociBando.ID_MACRO_VOCE ID_VOCE_DI_SPESA_PADRE,
masterCE.ID_RIGO_CONTO_ECONOMICO ID_RIGO_CONTO_ECONOMICO,
masterCE.DT_INIZIO_VALIDITA DT_INIZIO_VALIDITA , 
masterCE.ULTIMA_SPESA_AMMESSA ULTIMA_SPESA_AMMESSA
from
(
select
macroMicro.*,
bandoMicro.MASSIMO_IMPORTO_AMMISSIBILE MICRO_VOCE_MAX_AMMESSO
from
(
select
bandoMacro.ID_BANDO,
pro.ID_PROGETTO,
pro.CODICE_VISUALIZZATO,
macroMicro.ID_VOCE_DI_SPESA_PADRE ID_MACRO_VOCE,
macro.DESC_VOCE_DI_SPESA DESC_MACRO,
macroMicro.ID_VOCE_DI_SPESA ID_VOCE_DI_SPESA,
macroMicro.DESC_VOCE_DI_SPESA 
from
pbandi_d_voce_di_spesa macroMicro,
pbandi_d_voce_di_spesa macro,
PBANDI_R_BANDO_VOCE_SPESA bandoMacro,
PBANDI_T_PROGETTO pro,
PBANDI_T_DOMANDA dom,
PBANDI_R_BANDO_LINEA_INTERVENT banLin
where
pro.ID_DOMANDA = dom.ID_DOMANDA
and dom.PROGR_BANDO_LINEA_INTERVENTO = banLin.PROGR_BANDO_LINEA_INTERVENTO
and banLin.ID_BANDO = bandoMacro.ID_BANDO
and macroMicro.ID_VOCE_DI_SPESA = bandoMacro.ID_VOCE_DI_SPESA
and macro.ID_VOCE_DI_SPESA(+) = macroMicro.ID_VOCE_DI_SPESA_PADRE
) macroMicro,
PBANDI_R_BANDO_VOCE_SPESA bandoMicro
where
macroMicro.ID_BANDO = bandoMicro.ID_BANDO(+)
and macroMicro.ID_VOCE_DI_SPESA = bandoMicro.ID_VOCE_DI_SPESA(+)
) vociBando,
(
select
bl.ID_BANDO,
p.ID_PROGETTO,
rce.ID_VOCE_DI_SPESA,
rce.ID_RIGO_CONTO_ECONOMICO, 
rce.DT_INIZIO_VALIDITA,
rce.IMPORTO_SPESA RICHIESTO_ULTIMA_PROPOSTA,
rce.IMPORTO_AMMESSO_FINANZIAMENTO ULTIMA_SPESA_AMMESSA
from
pbandi_t_rigo_conto_economico rce,
pbandi_t_conto_economico ce,
pbandi_d_stato_conto_economico sce,
pbandi_d_tipologia_conto_econ tce,
pbandi_t_progetto p,
pbandi_t_domanda d,
pbandi_r_bando_linea_intervent bl
where
p.ID_DOMANDA = d.ID_DOMANDA
and d.ID_DOMANDA = ce.ID_DOMANDA
and ce.ID_CONTO_ECONOMICO = rce.ID_CONTO_ECONOMICO
and rce.DT_FINE_VALIDITA is null
and ce.ID_STATO_CONTO_ECONOMICO = sce.ID_STATO_CONTO_ECONOMICO
and sce.ID_TIPOLOGIA_CONTO_ECONOMICO = tce.ID_TIPOLOGIA_CONTO_ECONOMICO
and d.PROGR_BANDO_LINEA_INTERVENTO = bl.PROGR_BANDO_LINEA_INTERVENTO
and ce.DT_FINE_VALIDITA is null
and tce.DESC_BREVE_TIPOLOGIA_CONTO_ECO = 'MASTER'
and rce.DT_FINE_VALIDITA is null
) masterCE
where
vociBando.ID_BANDO = masterCE.ID_BANDO(+)
and vociBando.ID_PROGETTO = masterCE.ID_PROGETTO(+)
and vociBando.ID_VOCE_DI_SPESA = masterCE.ID_VOCE_DI_SPESA(+)
order by 
DESC_MACRO || DESC_VOCE_DI_SPESA