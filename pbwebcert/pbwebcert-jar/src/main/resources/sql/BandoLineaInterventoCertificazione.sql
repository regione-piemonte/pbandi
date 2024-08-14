select DESC_LINEA as descLinea , ANNO_DELIBERA as annoDelibera , COD_LIV_GERARCHICO as codLivGerarchico , COD_IGRUE_T13_T14 as codIgrueT13T14 , ID_TIPO_LINEA_INTERVENTO as idTipoLineaIntervento , ID_LINEA_DI_INTERVENTO as idLineaDiIntervento , ID_LINEA_DI_INTERVENTO_PADRE as idLineaDiInterventoPadre , ID_PROCESSO as idProcesso , ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz , NUM_DELIBERA as numDelibera , FLAG_CAMPION_RILEV as flagCampionRilev , ID_TIPO_STRUMENTO_PROGR as idTipoStrumentoProgr , DT_INIZIO_VALIDITA as dtInizioValidita , DESC_BREVE_LINEA as descBreveLinea , DT_FINE_VALIDITA as dtFineValidita , ID_STRUMENTO_ATTUATIVO as idStrumentoAttuativo from (
select id_proposta_certificaz, l.*
from pbandi_r_proposta_certif_linea t, pbandi_d_linea_di_intervento l
where t.ID_LINEA_DI_INTERVENTO = l.ID_LINEA_DI_INTERVENTO
) where ID_PROPOSTA_CERTIFICAZ = ?