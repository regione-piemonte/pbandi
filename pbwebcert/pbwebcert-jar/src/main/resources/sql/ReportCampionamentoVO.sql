select DATA_CAMPIONAMENTO as dataCampionamento , TIPO_CONTROLLI as tipoControlli , ID_DOCUMENTO_INDEX as idDocumentoIndex , ID_PERIODO as idPeriodo , ID_NORMATIVA as idNormativa , DESC_PERIODO_VISUALIZZATA as descPeriodoVisualizzata , NOME_FILE as nomeFile , ID_CAMPIONE as idCampione , ID_LINEA_DI_INTERVENTO as idLineaDiIntervento , ID_CATEG_ANAGRAFICA as idCategAnagrafica , ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz , NORMATIVA as normativa from (
SELECT DISTINCT
                camp.*,
                dindex.ID_DOCUMENTO_INDEX,
                DINDEX.NOME_FILE,
                INTERVENTO.ID_LINEA_DI_INTERVENTO ID_NORMATIVA,
                INTERVENTO.DESC_LINEA NORMATIVA,
                PERIODO.DESC_PERIODO_VISUALIZZATA
        FROM
                PBANDI_T_CAMPIONAMENTO camp LEFT JOIN pbandi_t_periodo periodo ON camp.ID_PERIODO = PERIODO.ID_PERIODO,
                PBANDI_T_DOCUMENTO_INDEX dindex,
                PBANDI_D_LINEA_DI_INTERVENTO intervento
        WHERE
                CAMP.ID_CAMPIONE = dindex.ID_TARGET
                AND INTERVENTO.ID_LINEA_DI_INTERVENTO = CAMP.ID_LINEA_DI_INTERVENTO
                AND DINDEX.ID_ENTITA = (
                                        SELECT
                                                id_entita
                                        FROM
                                                PBANDI_C_ENTITA entita
                                        WHERE
                                                ENTITA.NOME_ENTITA LIKE 'PBANDI_T_CAMPIONAMENTO')
)