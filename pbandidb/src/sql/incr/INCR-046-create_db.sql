/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--									  
-- PBANDI_V_DOC_INDEX_BATCH_FINP
CREATE OR REPLACE FORCE VIEW PBANDI_V_DOC_INDEX_BATCH_FINP
(
   ID_DOCUMENTO_INDEX,
   UUID_NODO,
   ID_PROGETTO,
   NOME_FILE,
   NOME_FILE_FIRMATO,
   DT_MARCA_TEMPORALE,
   TIPO_INVIO,
   FLAG_FIRMA_CARTACEA,
   FLAG_TRASM_DEST,
   ID_DICHIARAZIONE_SPESA,
   DESC_BREVE_TIPO_DOC_INDEX,
   ID_INTEGRAZIONE_SPESA
)
AS
   WITH SF_INVIO
        AS (  SELECT c.ID_DICHIARAZIONE_SPESA,
                     c.ID_PROGETTO,
                     CASE
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'CC'
                        THEN
                           'C'
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'DD'
                        THEN
                           'E'
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'CD'
                        THEN
                           'I'
                        ELSE
                           NULL
                     END
                        TIPO_INVIO
                FROM PBANDI_S_DICH_DOC_SPESA a,
                     PBANDI_R_DOC_SPESA_PROGETTO b,
                     PBANDI_T_DICHIARAZIONE_SPESA c
               WHERE     a.ID_DICHIARAZIONE_SPESA = c.ID_DICHIARAZIONE_SPESA
                     AND a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA
                     AND b.ID_PROGETTO = c.ID_PROGETTO
            GROUP BY c.ID_DICHIARAZIONE_SPESA, c.ID_PROGETTO)
   SELECT ID_DOCUMENTO_INDEX,
          UUID_NODO,
          x.ID_PROGETTO,
          NOME_FILE,
          NOME_FILE_FIRMATO,
		  DT_MARCA_TEMPORALE,
          CASE
             WHEN x.NOME_ENTITA IN
                     ('PBANDI_T_DICHIARAZIONE_SPESA',
                      'PBANDI_T_COMUNICAZ_FINE_PROG')
             THEN
                i.TIPO_INVIO
             ELSE
                NULL                               --Comunicazione di rinuncia
          END
             TIPO_INVIO,
          FLAG_FIRMA_CARTACEA,
          FLAG_TRASM_DEST,
          x.ID_DICHIARAZIONE_SPESA,
          DESC_BREVE_TIPO_DOC_INDEX,
		  ID_INTEGRAZIONE_SPESA
     FROM    (SELECT ID_DOCUMENTO_INDEX,
                     UUID_NODO,
                     di.ID_PROGETTO,
                     p.ID_PROGETTO_FINANZ,
                     NOME_FILE,
                     NOME_ENTITA,
                     CASE
                        WHEN FLAG_FIRMA_CARTACEA = 'N'
                        THEN
                           NOME_FILE
                           || (SELECT valore
                                 FROM PBANDI_C_COSTANTI
                                WHERE attributo =
                                         'conf.firmaDigitaleFileExtension')
                        ELSE
                           NULL
                     END
                        NOME_FILE_FIRMATO,
					 DT_MARCA_TEMPORALE,
                     FLAG_FIRMA_CARTACEA,
                     NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
                     --Dichiarazione di spesa(id_target di Id_entita 63 PBANDI_T_DICHIARAZIONE_SPESA)
                     --Comunicazione di fine progetto (id_entita 270  PBANDI_T_COMUNICAZ_FINE_PROG cercare la dichiarazione di spesa di tipo DESC_BREVE_TIPO_DICHIARA_SPESA=FC)
                     --Comunicazione di rinuncia (id_entita 249 PBANDI_T_RINUNCIA id_dichiarazione di spesa=NULL
                     CASE
                        WHEN en.NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'
                        THEN
                           di.ID_TARGET
                        WHEN en.NOME_ENTITA = 'PBANDI_T_COMUNICAZ_FINE_PROG'
                        THEN
                           (SELECT MAX (id_dichiarazione_spesa)
                              FROM PBANDI_T_DICHIARAZIONE_SPESA dich
                             -- id_progetto = di.id_progetto
                             WHERE id_progetto =
                                      NVL (p.id_progetto_finanz,
                                           p.id_progetto)
                                   AND id_tipo_dichiaraz_spesa IN
                                          (SELECT id_tipo_dichiaraz_spesa
                                             FROM PBANDI_D_TIPO_DICHIARAZ_SPESA
                                            WHERE DESC_BREVE_TIPO_DICHIARA_SPESA =
                                                     'FC')) -- Finale con comunicazione
                        ELSE
                           NULL                    --Comunicazione di rinuncia
                     END
                        ID_DICHIARAZIONE_SPESA,
                     tdi.DESC_BREVE_TIPO_DOC_INDEX,
					 NULL ID_INTEGRAZIONE_SPESA
                FROM PBANDI_T_DOCUMENTO_INDEX di
                     JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
                        USING (ID_TIPO_DOCUMENTO_INDEX)
                     JOIN PBANDI_C_ENTITA en
                        USING (ID_ENTITA)
                     LEFT JOIN PBANDI_D_STATO_DOCUMENTO_INDEX sdi
                        USING (ID_STATO_DOCUMENTO)
                     JOIN PBANDI_T_PROGETTO p
                        ON p.ID_PROGETTO = di.ID_PROGETTO
                     JOIN PBANDI_T_DOMANDA
                        USING (ID_DOMANDA)
                     JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli
                        USING (PROGR_BANDO_LINEA_INTERVENTO)
                     JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP blec
                        USING (PROGR_BANDO_LINEA_INTERVENTO)
                     JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA rec
                        USING (ID_RUOLO_ENTE_COMPETENZA)
                     JOIN PBANDI_T_ENTE_COMPETENZA ec
                        USING (ID_ENTE_COMPETENZA)
               WHERE tdi.FLAG_FIRMABILE = 'S'
                     AND en.NOME_ENTITA IN
                            ('PBANDI_T_DICHIARAZIONE_SPESA',
                             'PBANDI_T_COMUNICAZ_FINE_PROG',
                             'PBANDI_T_RINUNCIA')
					-- Se Dematerializzato allora deve essere inviato altrimenti no
                     AND ( (NVL(di.FLAG_FIRMA_CARTACEA, 'N') = 'N' AND sdi.DESC_BREVE = 'INVIATO') OR 
					      ((di.FLAG_FIRMA_CARTACEA = 'S' AND sdi.DESC_BREVE != 'INVIATO'))
						 )
					-- Se Dematerializzato allora deve essere marcato temporalmente altrimenti no
                     AND ( (NVL(di.FLAG_FIRMA_CARTACEA, 'N') = 'N' AND di.DT_MARCA_TEMPORALE IS NOT NULL) OR 
					      ((di.FLAG_FIRMA_CARTACEA = 'S' AND di.DT_MARCA_TEMPORALE IS  NULL))
						 )
                     AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
                     AND ec.DESC_BREVE_ENTE = 'FIN') x
          LEFT JOIN
             SF_INVIO i
          ON (i.ID_DICHIARAZIONE_SPESA = x.ID_DICHIARAZIONE_SPESA
              AND i.ID_PROGETTO = NVL (x.ID_PROGETTO_FINANZ, x.ID_PROGETTO))
          -- Integrazione Dichiarazione di Spesa
		  UNION ALL
          SELECT di.ID_DOCUMENTO_INDEX,
                 di.UUID_NODO,
                 p.ID_PROGETTO,
                 di.NOME_FILE,
				 CASE
					WHEN FLAG_FIRMA_CARTACEA = 'N'
					THEN
					   di.NOME_FILE
					   || (SELECT valore
							 FROM PBANDI_C_COSTANTI
							WHERE attributo =
									 'conf.firmaDigitaleFileExtension')
					ELSE
					   NULL
				 END
					NOME_FILE_FIRMATO,
				NULL DT_MARCA_TEMPORALE,
				NULL TIPO_INVIO,
				NULL  FLAG_FIRMA_CARTACEA,
                NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
                a.ID_DICHIARAZIONE_SPESA,
                'INT-DS'  DESC_BREVE_TIPO_DOC_INDEX,
				a.ID_INTEGRAZIONE_SPESA
           FROM PBANDI_T_INTEGRAZIONE_SPESA a
           JOIN PBANDI_T_FILE_ENTITA b ON b.id_target = a.ID_INTEGRAZIONE_SPESA
           JOIN PBANDI_T_FILE f ON  f.ID_FILE = b.ID_FILE
           JOIN PBANDI_C_ENTITA en ON en.ID_ENTITA = b.ID_ENTITA
           JOIN PBANDI_T_DICHIARAZIONE_SPESA c ON  c.ID_DICHIARAZIONE_SPESA = a.ID_DICHIARAZIONE_SPESA
           JOIN PBANDI_T_DOCUMENTO_INDEX di ON  di.ID_DOCUMENTO_INDEX = f.ID_DOCUMENTO_INDEX
           JOIN PBANDI_T_PROGETTO p  ON p.ID_PROGETTO = c.ID_PROGETTO
           JOIN PBANDI_T_DOMANDA d  ON d.ID_DOMANDA = p.ID_DOMANDA
           JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli ON BLI.PROGR_BANDO_LINEA_INTERVENTO = d.PROGR_BANDO_LINEA_INTERVENTO
           JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP blec ON blec.PROGR_BANDO_LINEA_INTERVENTO  = bli.PROGR_BANDO_LINEA_INTERVENTO
           JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA rec  ON rec.ID_RUOLO_ENTE_COMPETENZA = blec.ID_RUOLO_ENTE_COMPETENZA
           JOIN PBANDI_T_ENTE_COMPETENZA ec         ON ec.ID_ENTE_COMPETENZA = blec.ID_ENTE_COMPETENZA
           WHERE en.NOME_ENTITA = 'PBANDI_T_INTEGRAZIONE_SPESA'
             AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
             AND ec.DESC_BREVE_ENTE = 'FIN'
             -- Integrazione DS
             AND b.FLAG_ENTITA = 'I';

