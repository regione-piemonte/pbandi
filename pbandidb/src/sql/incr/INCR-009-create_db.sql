/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

alter TABLE pbandi_t_dichiarazione_spesa
add flag_richiesta_integrativa  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_48 CHECK (flag_richiesta_integrativa in ('S','N'));

alter TABLE pbandi_t_progetto
add dt_chiusura_progetto  DATE  NULL;

alter TABLE pbandi_t_progetto
add note_chiusura_progetto  VARCHAR2(4000);

CREATE TABLE pbandi_t_comunicaz_fine_prog
(
	id_comunicaz_fine_prog  NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	dt_comunicazione      DATE  NOT NULL ,
	importo_rich_erogazione_saldo  NUMBER(17,2)  NULL ,
	flag_allegato_regime_iva  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_49 CHECK (flag_allegato_regime_iva in ('S','N')),
	note_comunicaz_fine_progetto  VARCHAR2(4000)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_comunicaz_fine_pro ON pbandi_t_comunicaz_fine_prog
(id_comunicaz_fine_prog  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_comunicaz_fine_prog
	ADD CONSTRAINT  PK_pbandi_t_comunicaz_fine_pro PRIMARY KEY (id_comunicaz_fine_prog);



ALTER TABLE pbandi_t_comunicaz_fine_prog
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_13 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_comunicaz_fine_prog
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_211 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_comunicaz_fine_prog
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_212 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

create sequence seq_pbandi_t_comunic_fine_prog start with 1 nocache;

/*
*** gestione data inserimento
*/
alter table pbandi_r_conto_econom_mod_agev
add dt_inserimento date;

alter table pbandi_r_conto_econom_mod_agev
add dt_aggiornamento date;

update pbandi_r_conto_econom_mod_agev a
set dt_inserimento = (select dt_inizio_validita from pbandi_t_conto_economico b where a.id_conto_economico = b.id_conto_economico);

update pbandi_r_conto_econom_mod_agev a
set dt_inserimento = sysdate
where dt_inserimento is null;

commit;

alter table pbandi_r_conto_econom_mod_agev
modify dt_inserimento not null;

alter table pbandi_r_eccez_ban_lin_doc_pag
add dt_inserimento date;

alter table pbandi_r_eccez_ban_lin_doc_pag
add dt_aggiornamento date;

update pbandi_r_eccez_ban_lin_doc_pag
set dt_inserimento = to_date('01/01/2011','dd/mm/yyyy');

commit;

alter table pbandi_r_eccez_ban_lin_doc_pag
modify dt_inserimento not null;

alter table pbandi_t_documento_di_spesa
add dt_inserimento date;

alter table pbandi_t_documento_di_spesa
add dt_aggiornamento date;

update pbandi_t_documento_di_spesa
set dt_inserimento = dt_emissione_documento
where ID_STATO_DOCUMENTO_SPESA = 1
and ID_TIPO_DOCUMENTO_SPESA != 4;

update pbandi_t_documento_di_spesa dds
set dt_inserimento = (select min(ds.dt_dichiarazione)
					  from   PBANDI_T_DICHIARAZIONE_SPESA ds,PBANDI_R_PAGAMENTO_DICH_SPESA pds,PBANDI_T_PAGAMENTO p,
							 PBANDI_R_PAGAMENTO_DOC_SPESA rpds
					  where  ds.ID_DICHIARAZIONE_SPESA = pds.ID_DICHIARAZIONE_SPESA
					  and    p.id_pagamento = pds.id_pagamento
					  and    rpds.id_pagamento = pds.id_pagamento
					  and    rpds.ID_DOCUMENTO_DI_SPESA = dds.ID_DOCUMENTO_DI_SPESA
					  group by dds.ID_DOCUMENTO_DI_SPESA)
where ID_STATO_DOCUMENTO_SPESA != 1
and ID_TIPO_DOCUMENTO_SPESA != 4;

update pbandi_t_documento_di_spesa dds
set dt_inserimento = (select dds2.dt_inserimento from pbandi_t_documento_di_spesa dds2
					  where dds.ID_DOC_RIFERIMENTO = dds2.ID_DOCUMENTO_DI_SPESA)
where ID_TIPO_DOCUMENTO_SPESA = 4;

update pbandi_t_documento_di_spesa
set dt_inserimento = sysdate
where dt_inserimento is null;

alter table pbandi_t_documento_di_spesa
modify dt_inserimento not null;

alter table pbandi_r_doc_spesa_progetto
add dt_inserimento date;

alter table pbandi_r_doc_spesa_progetto
add dt_aggiornamento date;

update pbandi_r_doc_spesa_progetto a
set a.dt_inserimento = (select b.dt_inserimento from pbandi_t_documento_di_spesa b where a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA);

update pbandi_r_doc_spesa_progetto
set dt_inserimento = sysdate
where dt_inserimento is null;

alter table pbandi_r_doc_spesa_progetto
modify dt_inserimento not null;

alter table pbandi_t_quota_parte_doc_spesa
add dt_inserimento date;

alter table pbandi_t_quota_parte_doc_spesa
add dt_aggiornamento date;

update pbandi_t_quota_parte_doc_spesa a
set a.dt_inserimento = (select b.dt_inserimento from pbandi_t_documento_di_spesa b where a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA);

update pbandi_t_quota_parte_doc_spesa
set dt_inserimento = sysdate
where dt_inserimento is null;

alter table pbandi_t_quota_parte_doc_spesa
modify dt_inserimento not null;

alter table pbandi_t_pagamento
add dt_inserimento date;

alter table pbandi_t_pagamento
add dt_aggiornamento date;

update pbandi_t_pagamento
set dt_inserimento = nvl(dt_valuta,dt_pagamento)
where ID_STATO_VALIDAZIONE_SPESA = 1;

update pbandi_t_pagamento p
set p.dt_inserimento = (select min(ds.dt_dichiarazione)
                      from   PBANDI_T_DICHIARAZIONE_SPESA ds,PBANDI_R_PAGAMENTO_DICH_SPESA pds
                      where  ds.ID_DICHIARAZIONE_SPESA = pds.ID_DICHIARAZIONE_SPESA
                      and    p.id_pagamento = pds.id_pagamento
                      group  by p.id_pagamento)
where p.ID_STATO_VALIDAZIONE_SPESA != 1;

update pbandi_t_pagamento
set dt_inserimento = sysdate
where dt_inserimento is null;
					  
alter table pbandi_t_pagamento
modify dt_inserimento not null;

alter table pbandi_r_pagamento_doc_spesa
add dt_inserimento date;

alter table pbandi_r_pagamento_doc_spesa
add dt_aggiornamento date;

update pbandi_r_pagamento_doc_spesa a
set a.dt_inserimento = (select b.dt_inserimento from pbandi_t_pagamento b where a.ID_pagamento = b.ID_pagamento);

update pbandi_r_pagamento_doc_spesa
set dt_inserimento = sysdate
where dt_inserimento is null;
					  
alter table pbandi_r_pagamento_doc_spesa
modify dt_inserimento not null;

alter table pbandi_r_pag_quot_parte_doc_sp
add dt_inserimento date;

alter table pbandi_r_pag_quot_parte_doc_sp
add dt_aggiornamento date;

update pbandi_r_pag_quot_parte_doc_sp a
set a.dt_inserimento = (select b.dt_inserimento from pbandi_t_pagamento b where a.ID_pagamento = b.ID_pagamento);

update pbandi_r_pag_quot_parte_doc_sp
set dt_inserimento = sysdate
where dt_inserimento is null;
					  
alter table pbandi_r_pag_quot_parte_doc_sp
modify dt_inserimento not null;

alter table pbandi_t_domanda
add dt_inserimento date;

alter table pbandi_t_domanda
add dt_aggiornamento date;

update pbandi_t_domanda a
set a.dt_inserimento = (select min(b.dt_inizio_validita )
						from pbandi_r_soggetto_domanda b 
						where a.ID_domanda = b.ID_domanda
						group by b.ID_domanda);

update pbandi_t_domanda
set dt_inserimento = sysdate
where dt_inserimento is null;
						
alter table pbandi_t_domanda
modify dt_inserimento not null;

alter table pbandi_r_soggetto_domanda
add dt_inserimento date;

alter table pbandi_r_soggetto_domanda
add dt_aggiornamento date;

update pbandi_r_soggetto_domanda
set dt_inserimento = dt_inizio_validita;
					  
alter table pbandi_r_soggetto_domanda
modify dt_inserimento not null;

alter table pbandi_r_sogg_dom_sogg_correl
add dt_inserimento date;

alter table pbandi_r_sogg_dom_sogg_correl
add dt_aggiornamento date;

update pbandi_r_sogg_dom_sogg_correl a
set a.dt_inserimento = (select b.dt_inserimento from pbandi_r_soggetto_domanda b where b.progr_soggetto_domanda = a.progr_soggetto_domanda);

update pbandi_r_sogg_dom_sogg_correl
set dt_inserimento = sysdate
where dt_inserimento is null;
					  
alter table pbandi_r_sogg_dom_sogg_correl
modify dt_inserimento not null;

alter table pbandi_t_progetto
add dt_inserimento date;

alter table pbandi_t_progetto
add dt_aggiornamento date;

update pbandi_t_progetto a
set a.dt_inserimento = (select min(b.dt_inizio_validita)
						  from pbandi_r_soggetto_progetto b
						  where b.id_utente_ins = 2
						  and a.id_progetto = b.id_progetto
						  group by b.id_progetto);
						  
update pbandi_t_progetto a
set a.dt_inserimento = (select min(b.dt_inizio_validita)
						  from pbandi_r_soggetto_progetto b
						  where b.id_utente_ins != 2
						  and a.id_progetto = b.id_progetto
						  group by b.id_progetto)
where a.dt_inserimento is null;						  

update pbandi_t_progetto
set dt_inserimento = sysdate
where dt_inserimento is null;

alter table pbandi_t_progetto
modify dt_inserimento not null;

alter table pbandi_r_soggetto_progetto
add dt_inserimento date;

alter table pbandi_r_soggetto_progetto
add dt_aggiornamento date;

update pbandi_r_soggetto_progetto
set dt_inserimento = dt_inizio_validita;

alter table pbandi_r_soggetto_progetto
modify dt_inserimento not null;

alter table pbandi_r_sogg_prog_sogg_correl
add dt_inserimento date;

alter table pbandi_r_sogg_prog_sogg_correl
add dt_aggiornamento date;

update pbandi_r_sogg_prog_sogg_correl a
set a.dt_inserimento = (select b.dt_inserimento from pbandi_r_soggetto_progetto b
						where a.progr_soggetto_progetto = b.progr_soggetto_progetto);

update pbandi_r_sogg_prog_sogg_correl
set dt_inserimento = sysdate
where dt_inserimento is null;						
						
alter table pbandi_r_sogg_prog_sogg_correl
modify dt_inserimento not null;

alter table pbandi_r_prog_sogg_finanziat
add dt_inserimento date;

alter table pbandi_r_prog_sogg_finanziat
add dt_aggiornamento date;

update pbandi_r_prog_sogg_finanziat a
set a.dt_inserimento = (select b.dt_inserimento 
                        from   pbandi_t_progetto b
                        where  a.id_progetto = b.id_progetto);

update pbandi_r_prog_sogg_finanziat
set dt_inserimento = sysdate
where dt_inserimento is null;
						
alter table pbandi_r_prog_sogg_finanziat
modify dt_inserimento not null;						

alter table pbandi_t_appalto
add dt_aggiornamento date;

alter table pbandi_t_bando
add dt_inserimento date;

alter table pbandi_t_bando
add dt_aggiornamento date;

update PBANDI_T_BANDO a
set a.dt_inserimento = (select min(p.dt_inserimento)
						from pbandi_t_progetto p,PBANDI_R_BANDO_LINEA_INTERVENT bli,pbandi_t_domanda d
						where bli.id_bando = a.id_bando
						and   bli.PROGR_BANDO_LINEA_INTERVENTO = d.PROGR_BANDO_LINEA_INTERVENTO
						and   d.id_domanda = p.id_domanda
						group by a.id_bando);

update PBANDI_T_BANDO a
set a.dt_inserimento = to_date('01/01/2011','dd/mm/yyyy')
where a.dt_inserimento is null;

alter table pbandi_t_bando
modify dt_inserimento not null;

alter table pbandi_t_raggruppamento
add dt_inserimento date;

alter table pbandi_t_raggruppamento
add dt_aggiornamento date;

update pbandi_t_raggruppamento a
set a.dt_inserimento = (select b.dt_inserimento
						from pbandi_t_domanda b
						where a.id_domanda = b.id_domanda);

update pbandi_t_raggruppamento
set dt_inserimento = sysdate
where dt_inserimento is null;
						
alter table pbandi_t_raggruppamento
modify dt_inserimento not null;

alter table pbandi_t_checklist
add dt_inserimento date;

alter table pbandi_t_checklist
add dt_aggiornamento date;

update pbandi_t_checklist a
set a.dt_inserimento = (select b.dt_inserimento_INDEX
						from PBANDI_T_DOCUMENTO_INDEX b
						where a.ID_DOCUMENTO_INDEX = b.ID_DOCUMENTO_INDEX);

update pbandi_t_checklist
set dt_inserimento = sysdate
where dt_inserimento is null;
						
alter table pbandi_t_checklist
modify dt_inserimento not null;

alter table pbandi_t_erogazione
add dt_inserimento date;

alter table pbandi_t_erogazione
add dt_aggiornamento date;

update pbandi_t_erogazione
set dt_inserimento = dt_contabile;

alter table pbandi_t_erogazione
modify dt_inserimento not null;

alter table pbandi_t_fideiussione
add dt_inserimento date;

alter table pbandi_t_fideiussione
add dt_aggiornamento date;

update pbandi_t_fideiussione
set dt_inserimento = dt_decorrenza;

alter table pbandi_t_fideiussione
modify dt_inserimento not null;

alter table pbandi_t_recupero
add dt_inserimento date;

alter table pbandi_t_recupero
add dt_aggiornamento date;

update pbandi_t_recupero
set dt_inserimento = dt_recupero;

alter table pbandi_t_recupero
modify dt_inserimento not null;

alter table pbandi_t_revoca
add dt_inserimento date;

alter table pbandi_t_revoca
add dt_aggiornamento date;

update pbandi_t_revoca
set dt_inserimento = dt_revoca;

alter table pbandi_t_revoca
modify dt_inserimento not null;

alter table pbandi_t_ribasso_asta
add dt_inserimento date;

alter table pbandi_t_ribasso_asta
add dt_aggiornamento date;

update pbandi_t_ribasso_asta a
set a.dt_inserimento = (select b.dt_inizio_validita
						from pbandi_t_CONTO_ECONOMICO b
						where a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO);

update pbandi_t_ribasso_asta
set dt_inserimento = sysdate
where dt_inserimento is null;
						
alter table pbandi_t_ribasso_asta
modify dt_inserimento not null;

alter table pbandi_t_soggetto
add dt_inserimento date;

alter table pbandi_t_soggetto
add dt_aggiornamento date;

update pbandi_t_soggetto a
set a.dt_inserimento = (select min(b.dt_inserimento)
						from pbandi_r_soggetto_progetto b
						where a.ID_soggetto = b.ID_soggetto
						group by b.id_soggetto);

update pbandi_t_soggetto a
set a.dt_inserimento = (select min(b.dt_inizio_validita)
						from PBANDI_R_SOGG_TIPO_ANAGRAFICA b
						where a.ID_soggetto = b.ID_soggetto
						group by b.id_soggetto)
where a.dt_inserimento is null;

update pbandi_t_soggetto a
set a.dt_inserimento = (select min(b.dt_inserimento)
						from pbandi_r_soggetto_domanda b
						where a.ID_soggetto = b.ID_soggetto
						group by b.id_soggetto)
where a.dt_inserimento is null;		

update pbandi_t_soggetto a
set a.dt_inserimento = to_date('01/01/2010','dd/mm/yyyy')
where a.dt_inserimento is null;						
						
alter table pbandi_t_soggetto
modify dt_inserimento not null;

alter table pbandi_t_utente
add dt_inserimento date;

alter table pbandi_t_utente
add dt_aggiornamento date;

update pbandi_t_utente a
set a.dt_inserimento = (select b.dt_inserimento
						from pbandi_t_soggetto b
						where a.ID_soggetto = b.ID_soggetto);

update pbandi_t_utente a
set a.dt_inserimento = to_date('01/01/2010','dd/mm/yyyy')
where a.dt_inserimento is null;
						
alter table pbandi_t_utente
modify dt_inserimento not null;

alter table pbandi_t_sospensione
add dt_inserimento date not null;

alter table pbandi_t_sospensione
add dt_aggiornamento date;

alter table pbandi_t_sal
add dt_inserimento date not null;

alter table pbandi_t_sal
add dt_aggiornamento date;

alter table pbandi_t_irregolarita
add dt_inizio_validita date;

update pbandi_t_irregolarita
set dt_inizio_validita = dt_comunicazione;

alter table pbandi_t_irregolarita
modify dt_inizio_validita not null;

alter table pbandi_r_ente_competenza_sogg
add dt_inizio_validita date;

update pbandi_r_ente_competenza_sogg
set dt_inizio_validita = to_date('01/01/2011','dd/mm/yyyy');

alter table pbandi_r_ente_competenza_sogg
modify dt_inizio_validita not null;

commit;

alter table PBANDI_T_QUOTA_PARTE_DOC_SPESA  drop (dt_inserimento);
alter table PBANDI_T_PAGAMENTO              drop (dt_inserimento);
alter table PBANDI_R_PAGAMENTO_DOC_SPESA    drop (dt_inserimento);
alter table PBANDI_R_DOC_SPESA_PROGETTO     drop (dt_inserimento);
alter table PBANDI_T_DOCUMENTO_DI_SPESA     drop (dt_inserimento);

alter table PBANDI_T_PAGAMENTO              drop (dt_aggiornamento);
alter table PBANDI_R_PAGAMENTO_DOC_SPESA    drop (dt_aggiornamento);
alter table PBANDI_R_DOC_SPESA_PROGETTO     drop (dt_aggiornamento);
alter table PBANDI_T_QUOTA_PARTE_DOC_SPESA  drop (dt_aggiornamento);
alter table PBANDI_T_DOCUMENTO_DI_SPESA     drop (dt_aggiornamento);

alter TABLE pbandi_d_stato_progetto
add flag_monitoraggio     CHAR(1)   DEFAULT  'S' NOT NULL  CONSTRAINT  ck_flag_50 CHECK (flag_monitoraggio in ('S','N'));

alter TABLE pbandi_d_tipo_dichiaraz_spesa
add flag_selezionabile    CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_51 CHECK (flag_selezionabile in ('S','N'));

alter TABLE pbandi_t_dati_progetto_monit
add flag_extra_por        CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_52 CHECK (flag_extra_por in ('S','N'));
