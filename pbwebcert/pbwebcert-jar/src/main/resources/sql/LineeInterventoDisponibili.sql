select alias0.ID_UTENTE_AGG as c0 , 
alias0.ID_SOGGETTO as c1 ,alias0.ID_LINEA_DI_INTERVENTO as c2 ,
  alias0.ID_TIPO_ANAGRAFICA as c3 , alias0.ID_UTENTE_INS as c4 ,
   alias1.DESC_LINEA as c5 , alias1.ANNO_DELIBERA as c6 ,
    alias1.COD_LIV_GERARCHICO as c7 , alias1.COD_IGRUE_T13_T14 as c8 ,
     alias1.ID_TIPO_LINEA_INTERVENTO as c9 , alias1.ID_LINEA_DI_INTERVENTO as c10 ,
      alias1.ID_PROCESSO as c11 , alias1.ID_LINEA_DI_INTERVENTO_PADRE as c12 ,
       alias1.NUM_DELIBERA as c13 , alias1.FLAG_CAMPION_RILEV as c14 ,
        alias1.ID_TIPO_STRUMENTO_PROGR as c15 , alias1.DT_INIZIO_VALIDITA as c16 ,
         alias1.DESC_BREVE_LINEA as c17 , alias1.DT_FINE_VALIDITA as c18 ,
          alias1.ID_STRUMENTO_ATTUATIVO as c19 
          from  ( 
            select * from PBANDI_R_SOGG_TIPO_ANAG_LINEA 
              where ID_SOGGETTO = 2130090 and ID_TIPO_ANAGRAFICA = 11 
         ) alias0 ,  
         ( select * from PBANDI_D_LINEA_DI_INTERVENTO 
            where trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) 
            and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1) ) alias1
             where alias0.ID_LINEA_DI_INTERVENTO = alias1.ID_LINEA_DI_INTERVENTO