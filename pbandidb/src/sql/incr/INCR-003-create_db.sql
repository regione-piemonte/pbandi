/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

alter table pbandi_r_regola_tipo_anag
add progr_bando_linea_intervento  NUMBER(8);

ALTER TABLE pbandi_r_regola_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_08 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento) ON DELETE SET NULL);
	
update pbandi_r_regola_tipo_anag
set progr_bando_linea_intervento = 8;

commit;

ALTER TABLE pbandi_r_regola_tipo_anag
modify progr_bando_linea_intervento not null;

ALTER TABLE pbandi_r_regola_tipo_anag
drop constraints AK1_pbandi_r_regola_tipo_anag;

drop index AK1_pbandi_r_regola_tipo_anag;

CREATE UNIQUE INDEX AK1_pbandi_r_regola_tipo_anag ON pbandi_r_regola_tipo_anag
(id_tipo_anagrafica  ASC,id_regola  ASC,id_tipo_beneficiario  ASC,progr_bando_linea_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_regola_tipo_anag
ADD CONSTRAINT  AK1_pbandi_r_regola_tipo_anag UNIQUE (id_tipo_anagrafica,id_regola,id_tipo_beneficiario,progr_bando_linea_intervento);


alter table PBANDI_R_BANDO_VOCE_SPESA
add progr_ordinamento number(3);	 

declare
  cursor cur is select PROGR_ORDINAMENTO,ID_VOCE_DI_SPESA
				from   pbandi_d_voce_di_spesa;
begin
  for rec in cur loop
    update PBANDI_R_BANDO_VOCE_SPESA bvs
    set    bvs.progr_ordinamento = rec.progr_ordinamento
	where  ID_VOCE_DI_SPESA 	 = rec.ID_VOCE_DI_SPESA; 
  end loop;
  commit;
end;
/  
	
alter table PBANDI_R_BANDO_VOCE_SPESA
modify progr_ordinamento not null;

alter table pbandi_d_voce_di_spesa
drop (progr_ordinamento);

CREATE TABLE pbandi_d_tipologia_conto_econ
(
	id_tipologia_conto_economico  NUMBER(3)  NOT NULL ,
	desc_breve_tipologia_conto_eco  VARCHAR2(20)  NOT NULL ,
	desc_tipologia_conto_economico  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipologia_conto_ec ON pbandi_d_tipologia_conto_econ
(id_tipologia_conto_economico  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipologia_conto_econ
	ADD CONSTRAINT  PK_pbandi_d_tipologia_conto_ec PRIMARY KEY (id_tipologia_conto_economico);


ALTER TABLE pbandi_d_stato_conto_economico
ADD id_tipologia_conto_economico  NUMBER(3);

ALTER TABLE pbandi_d_stato_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_CONTO_ECON_01 FOREIGN KEY (id_tipologia_conto_economico) REFERENCES pbandi_d_tipologia_conto_econ(id_tipologia_conto_economico));
	
alter table pbandi_d_causale_erogazione
add progr_ordinamento number(3);

update pbandi_d_causale_erogazione
set progr_ordinamento = id_causale_erogazione;

commit;

alter table pbandi_d_causale_erogazione
modify progr_ordinamento not null;

ALTER TABLE PBANDI_D_UNITA_ORGANIZZATIVA
RENAME COLUMN DESC_ENTE TO desc_unita_organizzativa;

alter TABLE pbandi_d_causale_erogazione
add flag_iter_standard    CHAR(1);

ALTER TABLE pbandi_d_causale_erogazione
	MODIFY flag_iter_standard CONSTRAINT  ck_flag_09 CHECK (flag_iter_standard in ('S','N'));

CREATE INDEX IE1_pbandi_t_progetto ON pbandi_t_progetto
(id_istanza_processo  ASC)
compute statistics    TABLESPACE PBANDI_SMALL_IDX;

	