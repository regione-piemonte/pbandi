/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NullTemplateVisitor } from "@angular/compiler";

export class BeneficiarioSoggCorrPF{
    


    public cognome: any;
    public nome: any;
    public tipoSoggetto: any;
    public codiceFiscale: any;
    public dataDiNascita: any;
    public idComuneDiNascita: any;
    public idProvinciaDiNascita: any;
    public idRegioneDiNascita: any;
    public idNazioneDiNascita: any;
    public comuneDiNascita: any;
    public provinciaDiNascita: any;
    public regioneDiNascita: any;
    public nazioneDiNascita: any;
    //RESIDENZA:
    public indirizzoPF: any;
    public idComunePF: any;
    public idProvinciaPF: any;
    public idRegionePF: any;
    public idNazionePF: any;
    public comunePF: any;
    public provinciaPF: any;
    public capPF: any;
    public regionePF: any;
    public nazionePF: any;
    public descTipoSoggCorr: any; 
    public idUtenteAgg: any
    public  idRuoloPF: any;
    public idIndirizzo: any; 
    public descStatoDomanda: any; 
    public idTipoSoggCorr: any; 
    public datiAnagrafici:any;
    public sedeLegale: any; 
    public quotaPartecipazione:any;
    public quota: boolean; 
    public ndg:any; 
    public descTipoAnagrafica:any; 
	public idTipoAnagrafica:any;
    public campiModificati: Array<Number>
    public  idDocumento: number; 
	public  documentoIdentita:  string;
	public idTipoDocumentoIdentita: number;  
	public numeroDocumento: any;
	public  dataRilascio: Date;
	public  enteRilascio: Date;
	public  scadenzaDocumento: Date;
}