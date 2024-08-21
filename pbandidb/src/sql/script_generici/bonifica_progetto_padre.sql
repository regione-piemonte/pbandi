/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

declare
  cursor curDomanda is select id_domanda,id_domanda_padre 
                       from   pbandi_t_domanda
                       where  id_domanda_padre is not null;
  
  nIdProgettoPadre  pbandi_t_progetto.id_progetto_padre%TYPE;
begin
  for recDomanda in curDomanda loop
    select id_progetto
    into   nIdProgettoPadre
    from   pbandi_t_progetto
    where  id_domanda = recDomanda.id_domanda_padre;
    
    update pbandi_t_progetto
    set    id_progetto_padre = nIdProgettoPadre,
           id_utente_agg = 1
    where  id_domanda = recDomanda.id_domanda;
  end loop;
end;     
