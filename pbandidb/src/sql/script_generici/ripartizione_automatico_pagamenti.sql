/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

declare
  cursor curPagamenti is select distinct pag.id_pagamento,importo_pagamento,doc.ID_DOCUMENTO_DI_SPESA,
                             docSpesa.IMPORTO_QUOTA_PARTE_DOC_SPESA,docSpesa.ID_QUOTA_PARTE_DOC_SPESA        
                      from   PBANDI_T_PAGAMENTO pag,PBANDI_R_PAGAMENTO_DICH_SPESA dicSpesa,
                             PBANDI_R_PAGAMENTO_DOC_SPESA doc,PBANDI_T_QUOTA_PARTE_DOC_SPESA docSpesa
                      where  pag.id_pagamento = dicSpesa.id_pagamento
                      and    pag.id_pagamento = doc.id_pagamento
                      and    doc.ID_PROGETTO = docSpesa.ID_PROGETTO
                      and    docSpesa.ID_DOCUMENTO_DI_SPESA = doc.ID_DOCUMENTO_DI_SPESA 
                      and    not exists (select 'x' from PBANDI_R_PAG_QUOT_PARTE_DOC_SP parte
                                         where  pag.id_pagamento = parte.id_pagamento)
                      order by pag.id_pagamento;    
  
  nImpQ      PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO%TYPE;  
  nResiduoQ  NUMBER;
  nPagRes    NUMBER;
  nIdPag     PBANDI_T_PAGAMENTO.ID_PAGAMENTO%TYPE := 0;   
begin
  for recPagamenti in curPagamenti loop
    if nIdPag != recPagamenti.ID_PAGAMENTO then 
      nIdPag  := recPagamenti.ID_PAGAMENTO;
      nPagRes := recPagamenti.importo_pagamento; 
    end if;
    
    if nPagRes > 0 then     
      begin
        select sum(IMPORTO_QUIETANZATO)
        into   nImpQ
        from   PBANDI_R_PAG_QUOT_PARTE_DOC_SP
        where  ID_QUOTA_PARTE_DOC_SPESA = recPagamenti.ID_QUOTA_PARTE_DOC_SPESA
        group by ID_QUOTA_PARTE_DOC_SPESA;
      exception
        when no_data_found then
          nImpQ := 0;    
      end;
    
      nResiduoQ := recPagamenti.IMPORTO_QUOTA_PARTE_DOC_SPESA - nImpQ;
    
      if nResiduoQ > 0 then
        if nPagRes > nResiduoQ then
          insert into PBANDI_R_PAG_QUOT_PARTE_DOC_SP
          (ID_PAGAMENTO, ID_QUOTA_PARTE_DOC_SPESA, IMPORTO_QUIETANZATO, ID_UTENTE_INS)
          values
          (recPagamenti.ID_PAGAMENTO,recPagamenti.ID_QUOTA_PARTE_DOC_SPESA,nResiduoQ,0);
        
          nPagRes := nPagRes - nResiduoQ;
        else
          insert into PBANDI_R_PAG_QUOT_PARTE_DOC_SP
          (ID_PAGAMENTO, ID_QUOTA_PARTE_DOC_SPESA, IMPORTO_QUIETANZATO, ID_UTENTE_INS)
          values
          (recPagamenti.ID_PAGAMENTO,recPagamenti.ID_QUOTA_PARTE_DOC_SPESA,nPagRes,0);
        
          nPagRes := 0;
        end if;
      end if;
    end if;  
  end loop;
end;  
                                         
                          