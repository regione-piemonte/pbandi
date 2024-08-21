/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select l.desc_breve_linea,
       bl.*, 
       areasci.desc_area_scientifica, 
       areasci.cod_area_scientifica,
       tb.TITOLO_BANDO as DESC_BANDO,
       tb.titolo_bando,
       tb.determina_approvazione,
       bl.NOME_BANDO_LINEA as DESC_BANDO_LINEA,
       bl.progr_bando_linea_intervento id_bando_linea,
       cdp.uuid_processo
from pbandi_r_bando_linea_intervent bl,
     pbandi_c_definizione_processo cdp,
     pbandi_t_bando tb,
 pbandi_d_area_scientifica areasci,
 (
select substr(sys_connect_by_path(l.desc_breve_linea,' - '),3) desc_breve_linea, connect_by_root l.id_linea_di_intervento_padre as id_linea_di_intervento_padre, l.id_linea_di_intervento
from pbandi_d_linea_di_intervento l, 
 pbandi_d_tipo_linea_intervento tl
 where l.id_tipo_linea_intervento = tl.id_tipo_linea_intervento
 CONNECT by prior l.id_linea_di_intervento = l.id_linea_di_intervento_padre 
 order by tl.livello_tipo_linea 
 ) l 
 where l.id_linea_di_intervento = bl.id_linea_di_intervento
 and l.id_linea_di_intervento_padre is null
 and areasci.id_area_scientifica(+) = bl.id_area_scientifica
 and tb.id_bando = bl.id_bando
 and bl.id_definizione_processo = cdp.id_definizione_processo(+)