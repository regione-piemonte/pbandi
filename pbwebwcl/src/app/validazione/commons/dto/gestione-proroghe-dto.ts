export class GestioneProrogheVO {

    constructor(
       public  descStatoProroga?: string,
       public  descStatoRichiestaIntegrazione?: string,
       public  dtRichiestaIntegrazione ?: Date,
       public  dtRichiestaProroga?: Date,
       public  idRichiestaIntegrazione ?: number,
       public  idRichiestaProroga?: number,
       public  idStatoProroga?: number ,
       public  idStatoRichiestaIntegrazione?: number ,
       public  motivazioneProroga?: string,
       public  numGiorniApprovProroga?: number ,
       public  numGiorniRichProroga?: number ,
    ) { }
}
