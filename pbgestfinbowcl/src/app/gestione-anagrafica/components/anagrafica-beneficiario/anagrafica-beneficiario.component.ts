/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AnagraficaBeneficiarioSec } from '../../commons/dto/anagrafica-beneficiario';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { Constants } from '../../../core/commons/util/constants';
import { AnagraficaBeneFisico } from '../../commons/dto/anagraficaBeneFisico';
import { ElencoDomandeProgettiSec } from '../../commons/dto/elenco-domande-progetti';
import { statoDomanda } from '../../commons/dto/statoDomanda';
//import { AltriDati_DimensioneImpresa } from '../../commons/dto/altriDati_dimensioneImpresa';
import { AnagAltriDati_Main } from '../../commons/dto/anagAltriDati_Main';
import { DettaglioDatiDimensioneComponent } from '../dettaglio-dati-dimensione/dettaglio-dati-dimensione.component';
import { MatDialog } from '@angular/material/dialog';
import { DettaglioImpresa } from '../../commons/dettaglioImpresa';
import { ConditionalExpr } from '@angular/compiler';
import { StoricoBlocchiAttiviComponent } from '../storico-blocchi-attivi/storico-blocchi-attivi.component';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { BloccoVO } from '../../commons/dto/bloccoVO';
import { EditBloccoComponent } from '../edit-blocco/edit-blocco.component';

@Component({
  selector: 'app-anagrafica-beneficiario',
  templateUrl: './anagrafica-beneficiario.component.html',
  styleUrls: ['./anagrafica-beneficiario.component.scss']
})
export class AnagraficaBeneficiarioComponent implements OnInit {

  // public myForm: FormGroup;

  // idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;

  // beneficiario: AnagraficaBeneficiarioSec;
  // beneficiarioFisico: AnagraficaBeneFisico;
  // elenco: ElencoDomandeProgettiSec;
  // altriDati: AnagAltriDati_Main;
  statoUltimaDomanda: statoDomanda;
  idSoggetto: any;
  idEnteGiuridico: any;
  isEnteGiuridico: boolean;
  verificaTipoSoggetto: any;
  // spinner: boolean;
  getConcluse: number = 0;
  numeroDomanda: any;

  //VARIABILI PER ORDINAMENTO DELL'ELENCO DOMANDE/PROGETTI
  // colonna: string = "NUMERO_DOMANDA";
  // ordinamentoString: string = "DESC";
  // ordinamento: boolean = false;


  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  //PERSONA GIURIDUCA:
  // displayedColumnsEg: string[] = ['codiceBando', 'idDomanda', 'descStatoDomanda', 'idProgetto', 'descStatoProgetto', 'legaleRappresentante', 'azioni'];
  // columnsAltriDatiDimImpr: string[] = ['dataValutazione', 'esito', 'azioni'];
  // columnsAltriDatiDurc: string[] = ['dataEmissione', 'esito', 'dataScadenza', 'numProto'];
  // columnsAltriDatiAntiMafia: string[] = ['dataEmissione', 'esito', 'dataScadenza', 'numProto', 'numeroDomanda'];
  // columnsAltriDatiBdna: string[] = ['dataRicezione', 'numProto', 'numeroDomanda'];


  //PERSONA FISICA:
  // displayedColumns: string[] = ['codiceBando', 'idDomanda', 'descStatoDomanda', 'idProgetto', 'descStatoProgetto', 'azioni'];

  dataSource: any;
  // dataSourceAltriDati_DimImpr: any;
  // dataSourceAltriDati_Durc: any;
  // dataSourceAltriDati_Bdna: any;
  // dataSourceAltriDati_AntiMafia: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // BLOCCHI: 
  // listaBlocchi: Array<BloccoVO> = new Array<BloccoVO>();
  // isBlocchi: boolean;
  displayedColumnsBlocchi: string[] = ['dataBlocco', 'causale', 'azioni'];
  ndg: string;
  veroIdDomanda: any;
  idProgetto: any;
  // isSaveBlocco: boolean;


  constructor(
    private anagBeneService: AnagraficaBeneficiarioService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router,
    private sharedService: SharedService,
  ) { }

  ngOnInit() {
    // this.spinner = true;
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    //this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idTipoSoggetto');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    this.ndg =this.route.snapshot.queryParamMap.get("ndg");
    this.veroIdDomanda = this.route.snapshot.queryParamMap.get("numeroDomanda");
    this.idProgetto = this.route.snapshot.queryParamMap.get("idProgetto");
    if (this.verificaTipoSoggetto == "Persona Giuridica") {
      this.isEnteGiuridico = true;
    }else{
      this.isEnteGiuridico = false;
    }
    // if (this.verificaTipoSoggetto == "Persona Giuridica") {
    //   // this.isEnteGiuridico = true;
    //   // // PERSONA GIURIDUCA:
    //   // this.myForm = this.fb.group({
    //   //   // //DATI ANAGRAFICI:
    //   //   ragSociale: new FormControl({ value: '', disabled: true }),
    //   //   formaGiuridica: new FormControl({ value: '', disabled: true }),
    //   //   descTipoAnagrafica: new FormControl({ value: '', disabled: true }),
    //   //   flagPubblicoPrivato: new FormControl({ value: '', disabled: true }),
    //   //   codUniIpa: new FormControl({ value: '', disabled: true }),
    //   //   iva: new FormControl({ value: '', disabled: true }),
    //   //   descTipoSoggetto: new FormControl({ value: '', disabled: true }),
    //   //   codiceFiscale: new FormControl({ value: '', disabled: true }),
    //   //   dataCostituzione: new FormControl({ value: '', disabled: true }),
    //   //   pec: new FormControl({ value: '', disabled: true }),
    //   //   //SEDE LEGALE:
    //   //   indirizzoSede: new FormControl({ value: '', disabled: true }),
    //   //   ivaSede: new FormControl({ value: '', disabled: true }),
    //   //   descComuneSede: new FormControl({ value: '', disabled: true }),
    //   //   idComuneSede: new FormControl({ value: '', disabled: true }),
    //   //   descProvinciaSede: new FormControl({ value: '', disabled: true }),
    //   //   idProvinciaSede: new FormControl({ value: '', disabled: true }),
    //   //   capSede: new FormControl({ value: '', disabled: true }),
    //   //   idRegioneSede: new FormControl({ value: '', disabled: true }),
    //   //   descRegioneSede: new FormControl({ value: '', disabled: true }),
    //   //   idNazioneSede: new FormControl({ value: '', disabled: true }),
    //   //   descNazioneSede: new FormControl({ value: '', disabled: true }),
    //   //   //DATI ISCRIZIONE:
    //   //   numIscrizione: new FormControl({ value: '', disabled: true }),
    //   //   dtIiscrizione: new FormControl({ value: '', disabled: true }),
    //   //   descProvinciaIscrizione: new FormControl({ value: '', disabled: true }),
    //   //   idProvinciaIscrizione: new FormControl({ value: '', disabled: true }),
    //   //   //ATTIVITA':
    //   //   /* stato: new FormControl({ value: '', disabled: true }),
    //   //   dataCessazione: new FormControl({ value: '', disabled: true }),
    //   //   causaleCessazione: new FormControl({ value: '', disabled: true }), */
    //   //   ateco: new FormControl({ value: '', disabled: true }),
    //   //   descAttivita: new FormControl({ value: '', disabled: true }),
    //   //   flagRatingLeg: new FormControl({ value: '', disabled: true }),
    //   //   descStatoAttivita: new FormControl({ value: '', disabled: true }),
    //   //   dtInizioAttEsito: new FormControl({ value: '', disabled: true }),
    //   //   periodoScadEse: new FormControl({ value: '', disabled: true }),
    //   //   dtUltimoEseChiuso: new FormControl({ value: '', disabled: true })
    //   // });

    //   // //SETTING DEL FORM PER PERSONA GIURIDICA
    //   // this.anagBeneService.getAnagBeneficiario(this.idSoggetto);
    //   // this.subscribers.anagBeneficiarioInfo$ = this.anagBeneService.anagBeneficiarioInfo$.subscribe(data => {
    //   //   if (data) {
    //   //     this.beneficiario = data;
    //   //     this.myForm.setValue({
    //   //       //DATI ANAGRAFICI
    //   //       ragSociale: this.beneficiario[0].ragSoc,
    //   //       formaGiuridica: this.beneficiario[0].descFormaGiur,
    //   //       descTipoAnagrafica: this.beneficiario[0].descTipoAnagrafica,
    //   //       flagPubblicoPrivato: this.beneficiario[0].flagPubblicoPrivato,
    //   //       codUniIpa: this.beneficiario[0].codUniIpa,
    //   //       iva: this.beneficiario[0].pIva,
    //   //       descTipoSoggetto: this.beneficiario[0].descTipoSoggetto,
    //   //       codiceFiscale: this.beneficiario[0].cfSoggetto,
    //   //       //dataCostituzione: new Date(Date.parse(this.beneficiario[0].dtCostituzione)),
    //   //       dataCostituzione: 'Da caricare sul D.B.',
    //   //       pec: this.controlloCampoValido(this.beneficiario[0].pec) ? this.beneficiario[0].pec : 'N.D.',
    //   //       //SEDE LEGALE:
    //   //       indirizzoSede: this.beneficiario[0].descIndirizzo,
    //   //       ivaSede: this.beneficiario[0].pIva,
    //   //       descComuneSede: this.beneficiario[0].descComune,
    //   //       idComuneSede: this.beneficiario[0].idComune,
    //   //       descProvinciaSede: this.beneficiario[0].descProvincia,
    //   //       idProvinciaSede: this.beneficiario[0].idProvincia,
    //   //       capSede: this.beneficiario[0].cap,
    //   //       idRegioneSede: this.beneficiario[0].idRegione,
    //   //       descRegioneSede: this.beneficiario[0].descRegione,
    //   //       idNazioneSede: this.beneficiario[0].idNazione,
    //   //       descNazioneSede: this.beneficiario[0].descNazione,
    //   //       //DATI ISCRIZIONE:
    //   //       numIscrizione: this.controlloCampoValido(this.beneficiario[0].rea) ? this.beneficiario[0].rea : 'N.D.',
    //   //       dtIiscrizione: new Date(Date.parse(this.beneficiario[0].dtIiscrizione)),
    //   //       descProvinciaIscrizione: this.controlloCampoValido(this.beneficiario[0].descProvinciaIscrizione) ? this.beneficiario[0].descProvinciaIscrizione : 'N.D.',
    //   //       idProvinciaIscrizione: this.beneficiario[0].idProvinciaIscrizione,
    //   //       //ATTIVITA':
    //   //       //stato: this.controlloCampoValido(this.beneficiario[0].stato) ? this.beneficiario[0].stato : 'N.D.',
    //   //       //dataCessazione: 'N.D.',
    //   //       //causaleCessazione: this.controlloCampoValido(this.beneficiario[0].causaleCessazione) ? this.beneficiario[0].causaleCessazione : 'N.D.',
    //   //       ateco: this.beneficiario[0].codAteco,
    //   //       descAttivita: this.controlloCampoValido(this.beneficiario[0].descAteco) ? this.beneficiario[0].descAteco : 'N.D.',
    //   //       flagRatingLeg: this.beneficiario[0].flagRatingLeg,
    //   //       descStatoAttivita: this.controlloCampoValido(this.beneficiario[0].descStatoAttivita) ? this.beneficiario[0].descStatoAttivita : 'N.D.',
    //   //       dtInizioAttEsito: this.controlloCampoValido(this.beneficiario[0].dtInizioAttEsito) ? this.beneficiario[0].dtInizioAttEsito : 'N.D.',
    //   //       periodoScadEse: this.controlloCampoValido(this.beneficiario[0].periodoScadEse) ? this.beneficiario[0].periodoScadEse : 'N.D.',
    //   //       dtUltimoEseChiuso: this.controlloCampoValido(this.beneficiario[0].dtUltimoEseChiuso) ? this.beneficiario[0].dtUltimoEseChiuso : 'N.D.'
    //   //     });
    //   //     this.bloccaSpinner();
    //   //   }
    //   // });
    // } else {
    //   // this.isEnteGiuridico = false;
    //   // //PERSONA FISICA:
    //   // this.myForm = this.fb.group({
    //   //   //DATI ANAGRAFICI:
    //   //   cognome: new FormControl({ value: '', disabled: true }),
    //   //   nome: new FormControl({ value: '', disabled: true }),
    //   //   tipoSoggetto: new FormControl({ value: 'BENEFICIARIO', disabled: true }),
    //   //   codiceFiscale: new FormControl({ value: '', disabled: true }),
    //   //   dataDiNascita: new FormControl({ value: '', disabled: true }),
    //   //   comuneDiNascita: new FormControl({ value: '', disabled: true }),
    //   //   provinciaDiNascita: new FormControl({ value: '', disabled: true }),
    //   //   regioneDiNascita: new FormControl({ value: '', disabled: true }),
    //   //   nazioneDiNascita: new FormControl({ value: '', disabled: true }),
    //   //   //RESIDENZA:
    //   //   indirizzoPF: new FormControl({ value: '', disabled: true }),
    //   //   comunePF: new FormControl({ value: '', disabled: true }),
    //   //   provinciaPF: new FormControl({ value: '', disabled: true }),
    //   //   capPF: new FormControl({ value: '', disabled: true }),
    //   //   regionePF: new FormControl({ value: '', disabled: true }),
    //   //   nazionePF: new FormControl({ value: '', disabled: true }),
    //   //   //ELENCO DOMANDE E PROGETTI
    //   //   codiceFondo: new FormControl({ value: '', disabled: true }),
    //   //   numeroDomanda: new FormControl({ value: '', disabled: true }),
    //   //   statoDomanda: new FormControl({ value: '', disabled: true }),
    //   //   codiceProgetto: new FormControl({ value: '', disabled: true }),
    //   //   statoProgetto: new FormControl({ value: '', disabled: true }),
    //   // });

    //   // //SETTING DEL FORM PER PERSONA FISICA
    //   // this.anagBeneService.getAnagBeneFisico(this.idSoggetto);
    //   // this.subscribers.anagBeneFisicoInfo$ = this.anagBeneService.anagBeneFisicoInfo$.subscribe(data => {
    //   //   if (data) {
    //   //     this.beneficiarioFisico = data;

    //   //     this.myForm.setValue({
    //   //       //DATI ANAGRAFICI:
    //   //       cognome: [this.beneficiarioFisico.cognome],
    //   //       nome: [this.beneficiarioFisico.nome],
    //   //       tipoSoggetto: "BENEFICIARIO",
    //   //       codiceFiscale: [this.beneficiarioFisico.codiceFiscale],
    //   //       dataDiNascita: new Date(Date.parse(this.beneficiarioFisico.dataDiNascita)),
    //   //       comuneDiNascita: [this.beneficiarioFisico.comuneDiNascita],
    //   //       provinciaDiNascita: [this.beneficiarioFisico.provinciaDiNascita],
    //   //       regioneDiNascita: [this.beneficiarioFisico.regioneDiNascita],
    //   //       nazioneDiNascita: [this.beneficiarioFisico.nazioneDiNascita],
    //   //       //RESIDENZA:
    //   //       indirizzoPF: [this.beneficiarioFisico.indirizzoPF],
    //   //       comunePF: [this.beneficiarioFisico.comunePF],
    //   //       provinciaPF: [this.beneficiarioFisico.provinciaPF],
    //   //       capPF: [this.beneficiarioFisico.capPF],
    //   //       regionePF: [this.beneficiarioFisico.regionePF],
    //   //       nazionePF: [this.beneficiarioFisico.nazionePF],
    //   //       //ELENCO DOMANDE
    //   //       codiceFondo: [null],
    //   //       numeroDomanda: [null],
    //   //       statoDomanda: [null],
    //   //       codiceProgetto: [null],
    //   //       statoProgetto: [null]
    //   //     });
    //   //     this.bloccaSpinner();
    //   //   }
    //   // });
    // };

    // this.anagBeneService.getElencoDomandeProgetti(this.idSoggetto, this.isEnteGiuridico, this.colonna, this.ordinamentoString).subscribe(data => {
    //   if (data) {
    //     this.elenco = data;
    //     this.dataSource = this.elenco;
    //     this.numeroDomanda = this.elenco[0].numeroDomanda;

    //     this.anagBeneService.getAltriDati(this.idSoggetto, this.numeroDomanda, this.idEnteGiuridico).subscribe(data => {
    //       if (data) {
    //         console.log(data);
    //         this.altriDati = data;
    //         this.dataSourceAltriDati_DimImpr = this.altriDati.dimImpresa;
    //         this.dataSourceAltriDati_Durc = this.altriDati.durc;
    //         this.dataSourceAltriDati_Bdna = this.altriDati.bdna;
    //         this.dataSourceAltriDati_AntiMafia = this.altriDati.antiMafia;
    //       }
    //     })

    //     /*this.anagBeneService.getDimensioneImpresa(this.idSoggetto, this.numeroDomanda).subscribe(data => {
    //       if (data) {
    //         this.altriDati_dimImpr = data;
    //         //this.dataSourceAltriDati_DimImpr = this.altriDati_dimImpr;
    //         console.log("ALTRI DATI - DIM IMP : ", this.dataSourceAltriDati_DimImpr);
    //       }
    //     })*/

    //     /*this.anagBeneService.getDurc(this.idSoggetto).subscribe(data => {
    //       if (data) {
    //         this.altriDati_durc = data;
    //         this.dataSourceAltriDati_Durc = this.altriDati_durc;
    //         console.log("ALTRI DATI - DURC: ", this.dataSourceAltriDati_Durc);
    //       }
    //     })*/

    //     this.bloccaSpinner();
    //   }
    // });

    this.anagBeneService.getStatoDomanda(this.idSoggetto, this.veroIdDomanda);

    this.subscribers.statoUltimaDomandaInfo$ = this.anagBeneService.statoUltimaDomandaInfo$.subscribe(data => {
      if (data) {
        this.statoUltimaDomanda = data;
        // this.bloccaSpinner();
      }
    });

    // this.getElencoBlocchi();

  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  // controlloCampoValido(valore) {
  //   return valore != null && valore != '';
  // }


  //FUNZIONE CHE SI OCCUPA DI INTERROMPERE LO SPINNER
  // bloccaSpinner() {
  //   this.getConcluse += 1;
  //   if (this.getConcluse > 2) {
  //     this.spinner = false;
  //   }
  // }

  //DIALOG PER MOSTRARE I DETTAGLI DELLA DIMENSIONE IMPRESA
  visualizzaDettaglioDimensioneImpresa(dataValEsito?: HTMLSelectElement) {
    let parse = dataValEsito.toString();
    let annoDataValEsito = parse.replace(/.*\//, '');
    this.anagBeneService.getDettaglioImpresa(this.idSoggetto, annoDataValEsito).subscribe(data => {
      if (data) {
        let dettaglio: Array<DettaglioImpresa> = data;
        this.dialog.open(DettaglioDatiDimensioneComponent, {
          width: '70%',
          data: {
            colonne: ['annoRiferimento', 'unitaLavorativeAnnue', 'fatturato', 'totaleBilancioAnnuale'],
            dettaglio,
          },
        });
      }
    });
  }
 
  //SORT PER ELENCO DOMANDE/PROGETTI
  // sortColonna(indiceColonna?: HTMLSelectElement) {
  //   let indiceColonnaString: string;
  //   this.ordinamento = !this.ordinamento;
  //   if (this.ordinamento) {
  //     this.ordinamentoString = "DESC";
  //   } else {
  //     this.ordinamentoString = "ASC";
  //   }

  //   if (indiceColonna.toString() == "codiceBando") {
  //     indiceColonnaString = "PROGR_BANDO_LINEA_INTERVENTO";
  //   } else if (indiceColonna.toString() == 'numeroDomanda') {
  //     indiceColonnaString = "NUMERO_DOMANDA";
  //   } else if (indiceColonna.toString() == 'statoDomanda') {
  //     indiceColonnaString = "DESC_STATO_DOMANDA";
  //   } else if (indiceColonna.toString() == 'codiceProgetto') {
  //     indiceColonnaString = "CODICE_VISUALIZZATO";
  //   } else if (indiceColonna.toString() == 'statoProgetto') {
  //     indiceColonnaString = "DESC_STATO_PROGETTO";
  //   }
  //   this.spinner = true;
  //   this.anagBeneService.getElencoDomandeProgetti(this.idSoggetto, this.isEnteGiuridico, indiceColonnaString, this.ordinamentoString).subscribe(data => {
  //     if (data) {
  //       this.elenco = data;
  //       this.dataSource = this.elenco;
  //       this.spinner = false;
  //     }
  //   });
  // }

  
  

  // //PROVA STORICO BLOCCHI ATTIVI
  // visualizzaStoricoBlocchiAttivi(row) {
  //   //this.router.navigate(["/drawrd/" + this.idOp + "storicoBlocchiAttivi"]);
  //   if (this.verificaTipoSoggetto == "Persona Giuridica") {
  //     this.router.navigate(["/drawer/" + this.idOp + "/storicoBlocchiAttivi"], {   queryParams: { idSoggetto: this.idSoggetto, idEnteGiuridico: this.idEnteGiuridico, isEnteGiuridico: this.verificaTipoSoggetto } });
  //   } else {
  //     this.router.navigate(["/drawer/" + this.idOp + "/storicoBlocchiAttivi"], { queryParams: { idSoggetto: this.idSoggetto, tipoSoggetto: this.verificaTipoSoggetto } });
  //   }
  // }

  // openStoricoBlocchi() {
  //   this.isSaveBlocco = false; 
  //   this.dialog.open(StoricoBlocchiAttiviComponent, {
  //     width: '60%',
  //   });
  // }

  // getElencoBlocchi() {
  //   this.spinner = true;
  //   this.subscribers.getElencoBlocchi = this.anagBeneService.getElencoBlocchi(this.idSoggetto).subscribe(data => {
  //     if (data.length > 0) {
  //       this.listaBlocchi = data;
  //       this.isBlocchi = true;
  //       this.spinner = false;
  //     } else{
  //       this.listaBlocchi = data; 
  //       this.isBlocchi = false; 
  //      this.spinner= false; 
  //     }
  //   }, err => {
  //     this.bloccaSpinner();
  //     /// faccio qualcosa dopo
  //   });
  //   console.log("numero domanda: " + this.numeroDomanda);

  // }
  // inserisciBlocco() {
  //   this.isSaveBlocco= false; 
  //   let dialogRef = this.dialog.open(EditBloccoComponent, {
  //     width: '50%',
  //     data: {
  //       modifica: 1,
  //     }
  //   });

  //   dialogRef.afterClosed().subscribe(data => {

  //     if(data ==true){
  //       this.isSaveBlocco =true
  //       this.getElencoBlocchi()
  //     }

  //   })
  // }
  // modificaBlocco(bloccoVO: BloccoVO) {
  //   this.isSaveBlocco= false; 
  //   let dialogRef = this.dialog.open(EditBloccoComponent, {
  //     width: '50%',
  //     data: {
  //       modifica: 2,
  //       bloccoVO: bloccoVO
  //     }
  //   });

  //   dialogRef.afterClosed().subscribe(data => {

  //     if(data ==true){
  //       this.isSaveBlocco =true
  //       this.getElencoBlocchi()
  //     }

  //   })
  // }

  goBack(){
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA + "/ricercaSoggetti"], { queryParams: {} });
  }

}




