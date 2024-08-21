/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--PBANDI_D_RUOLO_HELP
Insert into PBANDI_D_RUOLO_HELP
   (ID_RUOLO_HELP, DESC_RUOLO_HELP)
 Values
   (1, 'Beneficiario');
Insert into PBANDI_D_RUOLO_HELP
   (ID_RUOLO_HELP, DESC_RUOLO_HELP)
 Values
   (2, 'Funzionario');
Insert into PBANDI_D_RUOLO_HELP
   (ID_RUOLO_HELP, DESC_RUOLO_HELP)
 Values
   (3, 'Manager');
COMMIT;

--PBANDI_D_CAUSALE_EROGAZIONE (INVIO DATI MONIT)
update PBANDI_D_CAUSALE_EROGAZIONE
set cod_igrue = 'S'
where upper(desc_causale) like '%SALDO%';
COMMIT;

-- PBANDI_R_PAG_QUOT_PARTE_DOC_SP (Valorizzazione id_dichiarazione_spesa)
UPDATE PBANDI_R_PAG_QUOT_PARTE_DOC_SP a
set id_dichiarazione_spesa = (select  id_dichiarazione_spesa
                                            from (select /*+FIRST_ROWS */ a.id_dichiarazione_spesa,
														   c.id_documento_di_spesa,
														   c.id_progetto,
														   d.progr_pag_quot_parte_doc_sp,
														   d.id_pagamento
													from pbandi_t_dichiarazione_spesa a,
														   pbandi_r_pagamento_dich_spesa b,
														   pbandi_t_quota_parte_doc_spesa c,
														   pbandi_r_pag_quot_parte_doc_sp d
													where  b.id_dichiarazione_spesa = a.id_dichiarazione_spesa
													and c.id_progetto = a.id_progetto
													and d.id_quota_parte_doc_spesa = c.id_quota_parte_doc_spesa
													and d.id_pagamento = b.id_pagamento
													and a.dt_chiusura_validazione IS NULL)
                                            where a.progr_pag_quot_parte_doc_sp = progr_pag_quot_parte_doc_sp)
where exists
(select null
from (select /*+FIRST_ROWS */ a.id_dichiarazione_spesa,
						   c.id_documento_di_spesa,
						   c.id_progetto,
						   d.progr_pag_quot_parte_doc_sp,
						   d.id_pagamento
					from pbandi_t_dichiarazione_spesa a,
						   pbandi_r_pagamento_dich_spesa b,
						   pbandi_t_quota_parte_doc_spesa c,
						   pbandi_r_pag_quot_parte_doc_sp d
					where  b.id_dichiarazione_spesa = a.id_dichiarazione_spesa
					and c.id_progetto = a.id_progetto
					and d.id_quota_parte_doc_spesa = c.id_quota_parte_doc_spesa
					and d.id_pagamento = b.id_pagamento
					and a.dt_chiusura_validazione IS NULL)
		where progr_pag_quot_parte_doc_sp = a.progr_pag_quot_parte_doc_sp);

-- FAS --> FSC
		
UPDATE PBANDI_T_BANDO
SET titolo_bando = REPLACE(titolo_bando,'PAR-FAS','PAR-FSC')
WHERE INSTR(titolo_bando,'PAR-FAS') > 0;

UPDATE PBANDI_R_BANDO_LINEA_INTERVENT
SET Nome_Bando_Linea = REPLACE(Nome_Bando_Linea,'PAR-FAS','PAR-FSC')
WHERE INSTR(Nome_Bando_Linea,'PAR-FAS') > 0;

UPDATE PBANDI_D_LINEA_DI_INTERVENTO
SET desc_linea = REPLACE(desc_linea,' FAS ',' FSC ')
WHERE INSTR(desc_linea,' FAS ') > 0;

UPDATE  PBANDI_D_MICRO_SEZIONE_MODULO 
SET Contenuto_Micro_Sezione = REPLACE(Contenuto_Micro_Sezione,' FAS ',' FSC ')
where upper(Contenuto_Micro_Sezione) like '% FAS %';


-- PBANDI_D_ERRORE_BATCH
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W090', 'Inserimento di un utente già  presente ma non più valido');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E149', 'Errore nel caricamento del file CSV degli utenti');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E150', 'Errore imprevisto nel trattamento dei dati degli utenti');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E151', 'Errore nella creazione del file degli esiti del caricamento utenti');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E152', 'Errore nella creazione del file degli indirizzi mail a cui spedire il file degli esiti del caricamento utenti');
   
COMMIT;
--PBANDI_D_STATO_DOCUMENTO_SPESA
Insert into PBANDI_D_STATO_DOCUMENTO_SPESA
   (ID_STATO_DOCUMENTO_SPESA, DESC_BREVE_STATO_DOC_SPESA, DESC_STATO_DOCUMENTO_SPESA, DT_INIZIO_VALIDITA)
 Values
   (7, 'R', 'RESPINTO', TRUNC(SYSDATE));
Insert into PBANDI_D_STATO_DOCUMENTO_SPESA
   (ID_STATO_DOCUMENTO_SPESA, DESC_BREVE_STATO_DOC_SPESA, DESC_STATO_DOCUMENTO_SPESA, DT_INIZIO_VALIDITA)
 Values
   (8, 'DC', 'DA COMPLETARE', TRUNC(SYSDATE));
COMMIT;

-- Trattamento STATO_DOCUMENTO_SPESA (DA COMPLETARE) PBANDI_R_DOC_SPESA_PROGETTO
UPDATE pbandi_r_doc_spesa_progetto
set id_stato_documento_spesa = 8
where (id_documento_di_spesa,id_progetto) IN
(
WITH VALIDAZIONE AS
(
select a.id_documento_di_spesa,a.id_progetto,a.id_stato_documento_spesa, f.importo_totale_documento,dt_dichiarazione,dt_chiusura_validazione, c.id_dichiarazione_spesa,e.id_pagamento
from pbandi_r_doc_spesa_progetto a,pbandi_d_stato_documento_spesa b,pbandi_t_dichiarazione_spesa c,pbandi_r_pagamento_doc_spesa d, pbandi_r_pagamento_dich_spesa e,pbandi_t_documento_di_spesa f
where a.id_stato_documento_spesa = b.id_stato_documento_spesa
and c.id_progetto = a.id_progetto
and d.id_documento_di_spesa = a.id_documento_di_spesa
and e.id_pagamento = d.id_pagamento
and e.id_dichiarazione_spesa = c.id_dichiarazione_spesa
and f.id_documento_di_spesa = a.id_documento_di_spesa
and b.id_stato_documento_spesa = 2
),
W_NOTA_CREDITO AS 
(
select notecredito.id_doc_riferimento,
             sum (importo_totale_documento) importo_totale_documento_nc
from pbandi_t_documento_di_spesa notecredito
where id_doc_riferimento is not null
group by notecredito.id_doc_riferimento
),
W_PAGAMENTO AS
(
select distinct  id_documento_di_spesa,   SUM (IP)
                                    OVER (PARTITION BY ID_DOCUMENTO_DI_SPESA, ID_PROGETTO) importo_pagamento
from PBANDI_V_PAGAM_PROG_DOC_SPESA
order by id_documento_di_spesa
)
SELECT id_documento_di_spesa,id_progetto
FROM
(
SELECT a.id_documento_di_spesa,id_progetto, min(dt_dichiarazione) dt_dichiarazione ,min(dt_chiusura_validazione) dt_chiusura_validazione, id_dichiarazione_spesa,
            min(a.importo_totale_documento - nvl(b.importo_totale_documento_nc,0) - nvl(c.importo_pagamento,0)) IMPORTO,
        (CASE WHEN min(dt_chiusura_validazione) IS NULL
                THEN 'IN_VALIDAZIONE'
              ELSE
                 CASE WHEN  min(a.importo_totale_documento - nvl(b.importo_totale_documento_nc,0) - nvl(c.importo_pagamento,0)) > 0
                    THEN 'DA_COMPLETARE'
                 ELSE
                 'IN_VALIDAZIONE'
                 END
        END)  STATO_DOCUMENTO_SPESA
FROM VALIDAZIONE a, W_NOTA_CREDITO b, W_PAGAMENTO c
WHERE dt_dichiarazione = (select max(dt_dichiarazione)
                                  from VALIDAZIONE
                                  where id_documento_di_spesa =  a.id_documento_di_spesa
                                  and   id_progetto = a.id_progetto)
and b.id_doc_riferimento(+) = a.id_documento_di_spesa
and c.id_documento_di_spesa(+) = a.id_documento_di_spesa
group by a.id_documento_di_spesa,a.id_progetto,a.id_dichiarazione_spesa
order by a.id_documento_di_spesa,a.id_progetto,a.id_dichiarazione_spesa
)
WHERE STATO_DOCUMENTO_SPESA = 'DA_COMPLETARE');

COMMIT;

-- Trattamento STATO_DOCUMENTO_SPESA_VALID  PBANDI_R_DOC_SPESA_PROGETTO
UPDATE pbandi_r_doc_spesa_progetto a
SET id_stato_documento_spesa_valid = (
WITH 
QUOTA_PARTE AS
(select c.id_documento_di_spesa,c.id_progetto,
	   sum(d.importo_validato) importo_validato,sum(d.importo_quietanzato) importo_quietanzato
		from pbandi_t_dichiarazione_spesa a,
			   pbandi_r_pagamento_dich_spesa b,
			   pbandi_t_quota_parte_doc_spesa c,
			   pbandi_r_pag_quot_parte_doc_sp d
		where  b.id_dichiarazione_spesa = a.id_dichiarazione_spesa
		and c.id_progetto = a.id_progetto
		and d.id_quota_parte_doc_spesa = c.id_quota_parte_doc_spesa
		and d.id_pagamento = b.id_pagamento
		and a.dt_chiusura_validazione IS NULL
		group by c.id_documento_di_spesa,
			   c.id_progetto)
--MAIN
SELECT (CASE STATO_DOCUMENTO_SPESA WHEN  'IN_VALIDAZIONE' THEN 2
                                   WHEN   'NON_VALIDATO' THEN 6
                                   WHEN   'PARZIALMENTE_VALIDATO' THEN 4
                                   WHEN    'VALIDATO' THEN 5
        END) ID_STATO_DOCUMENTO_SPESA
FROM
(
SELECT id_documento_di_spesa,id_progetto,importo_validato, importo_quietanzato ,
       (CASE WHEN importo_validato IS NULL THEN 'IN_VALIDAZIONE'
             WHEN importo_validato = 0     THEN 'NON_VALIDATO'    
             WHEN importo_validato <  importo_quietanzato    THEN 'PARZIALMENTE_VALIDATO'
             WHEN importo_validato =  importo_quietanzato    THEN 'VALIDATO'
             ELSE '**'             
        END)  STATO_DOCUMENTO_SPESA
FROM QUOTA_PARTE
)

where id_documento_di_spesa = a.id_documento_di_spesa
and id_progetto = a.id_progetto
and a.id_stato_documento_spesa > 1)
;

COMMIT;

-- Trattamento STATO_DOCUMENTO_SPESA_VALID  PBANDI_R_DOC_SPESA_PROGETTO -- Da completare
UPDATE pbandi_r_doc_spesa_progetto a
SET id_stato_documento_spesa_valid = (
WITH 
VALIDAZIONE_CHIUSA AS
(
select a.id_documento_di_spesa,a.id_progetto,a.id_stato_documento_spesa, dt_dichiarazione,dt_chiusura_validazione, c.id_dichiarazione_spesa
from pbandi_r_doc_spesa_progetto a,pbandi_d_stato_documento_spesa b,pbandi_t_dichiarazione_spesa c
where a.id_stato_documento_spesa = b.id_stato_documento_spesa
and c.id_progetto = a.id_progetto
and b.id_stato_documento_spesa = 8 -- Da completare
and dt_chiusura_validazione IS NOT NULL
),
QUOTA_PARTE AS
(select id_documento_di_spesa,id_progetto,c.id_dichiarazione_spesa,sum(importo_validato) importo_validato,sum(importo_quietanzato) importo_quietanzato
from pbandi_t_quota_parte_doc_spesa a, pbandi_r_pag_quot_parte_doc_sp b,
     pbandi_r_pagamento_dich_spesa c
where a.id_quota_parte_doc_spesa = b.id_quota_parte_doc_spesa
and c.id_pagamento = b.id_pagamento
group by  id_documento_di_spesa,id_progetto,c.id_dichiarazione_spesa)
--MAIN
SELECT (CASE STATO_DOCUMENTO_SPESA WHEN  'IN_VALIDAZIONE' THEN 2
                                   WHEN   'NON_VALIDATO' THEN 6
                                   WHEN   'PARZIALMENTE_VALIDATO' THEN 4
                                   WHEN    'VALIDATO' THEN 5
        END) ID_STATO_DOCUMENTO_SPESA
FROM
(
SELECT a.id_documento_di_spesa,a.id_progetto,a.id_dichiarazione_spesa,a.id_stato_documento_spesa,importo_validato, importo_quietanzato ,
       (CASE WHEN b.id_documento_di_spesa IS NULL THEN 'VALIDATO'
             WHEN NVL(importo_validato,0) = 0     THEN 'NON_VALIDATO'    
             WHEN importo_validato <  importo_quietanzato    THEN 'PARZIALMENTE_VALIDATO'
             WHEN importo_validato =  importo_quietanzato    THEN 'VALIDATO'
             ELSE '**'             
        END)  STATO_DOCUMENTO_SPESA
FROM VALIDAZIONE_CHIUSA a,QUOTA_PARTE b
where a.id_documento_di_spesa = b.id_documento_di_spesa(+)
and a.id_progetto = b.id_progetto(+)
and a.id_dichiarazione_spesa = b.id_dichiarazione_spesa(+)
)

where id_documento_di_spesa = a.id_documento_di_spesa
and id_progetto = a.id_progetto
and rownum = 1)

WHERE EXISTS
(SELECT NULL
FROM
(
WITH 
VALIDAZIONE_CHIUSA AS
(
select a.id_documento_di_spesa,a.id_progetto,a.id_stato_documento_spesa, dt_dichiarazione,dt_chiusura_validazione, c.id_dichiarazione_spesa
from pbandi_r_doc_spesa_progetto a,pbandi_d_stato_documento_spesa b,pbandi_t_dichiarazione_spesa c
where a.id_stato_documento_spesa = b.id_stato_documento_spesa
and c.id_progetto = a.id_progetto
and b.id_stato_documento_spesa = 8 -- Da completare
and dt_chiusura_validazione IS NOT NULL
),
QUOTA_PARTE AS
(select id_documento_di_spesa,id_progetto,c.id_dichiarazione_spesa,sum(importo_validato) importo_validato,sum(importo_quietanzato) importo_quietanzato
from pbandi_t_quota_parte_doc_spesa a, pbandi_r_pag_quot_parte_doc_sp b,
     pbandi_r_pagamento_dich_spesa c
where a.id_quota_parte_doc_spesa = b.id_quota_parte_doc_spesa
and c.id_pagamento = b.id_pagamento
group by  id_documento_di_spesa,id_progetto,c.id_dichiarazione_spesa)
--MAIN

SELECT a.id_documento_di_spesa,a.id_progetto,a.id_dichiarazione_spesa,a.id_stato_documento_spesa,importo_validato, importo_quietanzato ,
       (CASE WHEN b.id_documento_di_spesa IS NULL THEN 'VALIDATO'
             WHEN NVL(importo_validato,0) = 0     THEN 'NON_VALIDATO'    
             WHEN importo_validato <  importo_quietanzato    THEN 'PARZIALMENTE_VALIDATO'
             WHEN importo_validato =  importo_quietanzato    THEN 'VALIDATO'
             ELSE '**'             
        END)  STATO_DOCUMENTO_SPESA
FROM VALIDAZIONE_CHIUSA a,QUOTA_PARTE b
where a.id_documento_di_spesa = b.id_documento_di_spesa(+)
and a.id_progetto = b.id_progetto(+)
and a.id_dichiarazione_spesa = b.id_dichiarazione_spesa(+)
)

where id_documento_di_spesa = a.id_documento_di_spesa
and id_progetto = a.id_progetto)
;

COMMIT;

--Trattamento STATO_DOCUMENTO_SPESA_VALID  PBANDI_R_DOC_SPESA_PROGETTO casi "RESPINTO"

DECLARE
  CURSOR c1 IS
		WITH VALIDAZIONE AS
		(
		select a.id_documento_di_spesa,a.id_progetto,a.id_stato_documento_spesa, dt_dichiarazione,dt_chiusura_validazione, c.id_dichiarazione_spesa, desc_stato_documento_spesa
		from pbandi_r_doc_spesa_progetto a,pbandi_d_stato_documento_spesa b,pbandi_t_dichiarazione_spesa c
		where a.id_stato_documento_spesa = b.id_stato_documento_spesa
		and c.id_progetto = a.id_progetto
		),
		PAGAMENTI AS
		(
		select a.id_documento_di_spesa,a.id_progetto,a.id_stato_documento_spesa, dt_dichiarazione,dt_chiusura_validazione, c.id_dichiarazione_spesa,e.id_pagamento
		from pbandi_r_doc_spesa_progetto a,pbandi_t_dichiarazione_spesa c,pbandi_r_pagamento_doc_spesa d, pbandi_r_pagamento_dich_spesa e,pbandi_t_pagamento f
		where c.id_progetto = a.id_progetto
		and d.id_documento_di_spesa = a.id_documento_di_spesa
		and e.id_pagamento = d.id_pagamento
		and e.id_dichiarazione_spesa = c.id_dichiarazione_spesa
		and f.id_pagamento = d.id_pagamento
		and f.id_stato_validazione_spesa_bck IN(7,8) -- Da respingere,Respinto (Almeno uno)
		)
		SELECT id_documento_di_spesa,id_progetto,id_dichiarazione_spesa,id_stato_documento_spesa, desc_stato_documento_spesa,dt_chiusura_validazione,
		(CASE WHEN dt_chiusura_validazione IS NULL 
				  THEN 2 -- In validazione
				  ELSE
					   1 -- Dichiarabile
			  END)  ID_STATO_DOCUMENTO_SPESA_CALC,
		(CASE WHEN dt_chiusura_validazione IS NULL 
		  THEN 7 -- Respinto
		  ELSE
			   cast('' as number)
		END)  ID_STATO_DOCUMENTO_SPESA_VALID
		FROM
		(
		SELECT a.id_documento_di_spesa,a.id_progetto, min(a.id_stato_documento_spesa) id_stato_documento_spesa , min(desc_stato_documento_spesa) desc_stato_documento_spesa,min(a.dt_dichiarazione) dt_dichiarazione ,min(a.dt_chiusura_validazione) dt_chiusura_validazione, a.id_dichiarazione_spesa
		FROM VALIDAZIONE a, PAGAMENTI b
		WHERE a.dt_dichiarazione = (select max(dt_dichiarazione)
										  from VALIDAZIONE
										  where id_documento_di_spesa =  a.id_documento_di_spesa
										  and   id_progetto = a.id_progetto)
		AND b.id_documento_di_spesa = a.id_documento_di_spesa
		AND b.id_dichiarazione_spesa = a.id_dichiarazione_spesa
		group by a.id_documento_di_spesa,a.id_progetto,a.id_dichiarazione_spesa
		order by a.id_documento_di_spesa,a.id_progetto,a.id_dichiarazione_spesa);
		
BEGIN
  FOR r1 IN c1 LOOP
     --if r1.ID_STATO_DOCUMENTO_SPESA_VALID = 7 THEN -- Respinto
	    UPDATE PBANDI_R_DOC_SPESA_PROGETTO
		  SET ID_STATO_DOCUMENTO_SPESA_VALID = r1.ID_STATO_DOCUMENTO_SPESA_VALID,
		       ID_STATO_DOCUMENTO_SPESA = r1.ID_STATO_DOCUMENTO_SPESA_CALC
		WHERE id_documento_di_spesa = r1.id_documento_di_spesa
		AND id_progetto = r1.id_progetto;
		
--	 end if;
  END LOOP;
END;
/

-- Elimino i record su "pbandi_r_pagamento_dich_spesa"  "pbandi_r_pag_quot_parte_doc_spesa"  con data chiusura validazione  per i RESPINTI"
DECLARE
   cursor c1 IS
   select  a.id_pagamento, b.id_dichiarazione_spesa, c.id_progetto, 
           a.id_stato_validazione_spesa_bck
		from pbandi_t_pagamento a,
		pbandi_r_pagamento_dich_spesa b,
		pbandi_t_dichiarazione_spesa c
		where a.id_stato_validazione_spesa_bck = 8
		and b.id_pagamento = a.id_pagamento
		and c.id_dichiarazione_spesa = b.id_dichiarazione_spesa
		and c.dt_chiusura_validazione IS NOT NULL;
		
	cursor c2 (c_id_pagamento NUMBER,c_id_progetto NUMBER)IS
	   select progr_pag_quot_parte_doc_sp, id_dichiarazione_spesa
	   from  pbandi_r_pag_quot_parte_doc_sp a, pbandi_t_quota_parte_doc_spesa b
	   where id_pagamento = c_id_pagamento
	   and a.id_quota_parte_doc_spesa = b.id_quota_parte_doc_spesa
	   and b.id_progetto = c_id_progetto;
		
	v_count_pds number := 0;
	v_count_pqds number := 0;	
	v_count_pqds_dich number := 0;	

BEGIN
   FOR r1 in c1 LOOP
   
      for r2 in c2(r1.id_pagamento, r1.id_progetto)  LOOP
   
      if r2.id_dichiarazione_spesa IS NULL THEN
		  delete pbandi_r_pag_quot_parte_doc_sp
		  where progr_pag_quot_parte_doc_sp  = r2.progr_pag_quot_parte_doc_sp;
		  
	      v_count_pqds  := v_count_pqds + sql%rowcount;
	  else
	      v_count_pqds_dich  := v_count_pqds_dich + 1;
	  end if;
	  
	  END LOOP;
	  
										
      delete pbandi_r_pagamento_dich_spesa
      where id_pagamento = r1.id_pagamento
      and  id_dichiarazione_spesa = r1.id_dichiarazione_spesa;
	  
	  v_count_pds  := v_count_pds + sql%rowcount;
	  
   END LOOP;
   
   DBMS_OUTPUT.PUT_LINE('Record cancellati da  pbandi_r_pagamento_dich_spesa '||v_count_pds);   
   DBMS_OUTPUT.PUT_LINE('Record cancellati da  pbandi_r_pag_quot_parte_doc_spesa '||v_count_pqds);
   DBMS_OUTPUT.PUT_LINE('Record su  pbandi_r_pag_quot_parte_doc_spesa con dich. valorizzata '||v_count_pqds_dich);
END;
/

COMMIT;
/*
-- Elimino i record su  "pbandi_r_pag_quot_parte_doc_spesa"  con data chiusura validazione  per i RESPINTI per quelli che sono rimasti fuori dallo step precedente
select distinct  PROGR_PAG_QUOT_PARTE_DOC_SP
from pbandi_r_pag_quot_parte_doc_sp a, pbandi_t_pagamento b, pbandi_t_quota_parte_doc_spesa c, pbandi_t_dichiarazione_spesa d
where a.id_pagamento = b.id_pagamento
and d.id_progetto = c.id_progetto
and c.id_quota_parte_doc_spesa = a.id_quota_parte_doc_spesa
and b.id_stato_validazione_spesa_bck = 8
and not exists
(select null
from pbandi_r_pagamento_dich_spesa
where id_pagamento = a.id_pagamento
and id_dichiarazione_spesa = d.id_dichiarazione_spesa);

*/

-- Popolamento PBANDI_S_DICH_DOC_SPESA
DECLARE
   cursor c1 IS
      select DISTINCT ID_DICHIARAZIONE_SPESA,
             ID_DOCUMENTO_DI_SPESA
      from pbandi_r_pagamento_dich_spesa a,
           pbandi_r_pagamento_doc_spesa b
           where a.id_pagamento = b.id_pagamento
           order by ID_DICHIARAZIONE_SPESA,
             ID_DOCUMENTO_DI_SPESA;

  cursor c2 (C_ID_DICHIARAZIONE_SPESA NUMBER,
             C_ID_DOCUMENTO_DI_SPESA NUMBER) IS
      select a.ID_PAGAMENTO
      from pbandi_r_pagamento_dich_spesa a,
           pbandi_r_pagamento_doc_spesa b
           where a.id_pagamento = b.id_pagamento
		   and ID_DICHIARAZIONE_SPESA = C_ID_DICHIARAZIONE_SPESA
		   and ID_DOCUMENTO_DI_SPESA = C_ID_DOCUMENTO_DI_SPESA;
		   
	V_ID_PAGAMENTO_NT T_NUMBER_ARRAY := T_NUMBER_ARRAY();
	v_x  NUMBER;
 

BEGIN
   for r1 in c1 LOOP
      V_ID_PAGAMENTO_NT.delete;
	  	v_x  := 0;

      for r2 in c2 (r1.ID_DICHIARAZIONE_SPESA, r1.ID_DOCUMENTO_DI_SPESA) loop
	     v_x  := v_x+1;
         V_ID_PAGAMENTO_NT.extend;
	     V_ID_PAGAMENTO_NT(v_x) := r2.ID_PAGAMENTO;
	  end loop;
	  
	  insert into PBANDI_S_DICH_DOC_SPESA VALUES (r1.ID_DICHIARAZIONE_SPESA, r1.ID_DOCUMENTO_DI_SPESA, V_ID_PAGAMENTO_NT);
   
   end loop;
END;
/
COMMIT;

/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.8.0';

insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

commit;

declare
  cursor curAttr is select col.COLUMN_NAME,en.id_ENTITA,col.TABLE_NAME
                    from all_tab_cols col,PBANDI_C_ENTITA en
                    where col.owner = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
                    and col.TABLE_NAME like 'PBANDI_%'
                    and col.COLUMN_NAME not like 'SYS_%'
                    and col.TABLE_NAME = en.NOME_ENTITA
                    and not exists (select 'x' from PBANDI_C_ATTRIBUTO att 
                                    where col.COLUMN_NAME = att.NOME_ATTRIBUTO
                                    and att.id_entita = en.id_ENTITA);
                                    
  nPosiz  PLS_INTEGER := NULL;                                                                          
begin
  for recAttr in curAttr loop

    BEGIN
      SELECT POSITION
      INTO   nPosiz 
      FROM   all_CONSTRAINTS A,all_CONS_COLUMNS B
      WHERE  A.CONSTRAINT_TYPE = 'P'
      AND    A.TABLE_NAME      = recAttr.TABLE_NAME
      AND    A.CONSTRAINT_NAME = B.CONSTRAINT_NAME
      AND    COLUMN_NAME       = recAttr.COLUMN_NAME;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        nPosiz := NULL;
    END;
    
    insert into PBANDI_C_ATTRIBUTO
    (ID_ATTRIBUTO, 
     NOME_ATTRIBUTO, 
     FLAG_DA_TRACCIARE, 
     ID_ENTITA,
     KEY_POSITION_ID) 
    values
    (SEQ_PBANDI_C_ATTRIBUTO.NEXTVAL,
     recAttr.COLUMN_NAME,
     'N',
     recAttr.id_ENTITA,
     nPosiz);
  end loop;
  COMMIT;
end;
/



