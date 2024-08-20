/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { IterAutorizzativiService } from '../../services/iter-autorizzativi-service';
import { RespingiIterComponent } from '../respingi-iter/respingi-iter.component';
import { AllegatiIterComponent } from '../allegati-iter/allegati-iter.component';
import { DettaglioIterComponent } from '../dettaglio-iter/dettaglio-iter.component';
import { OkDialogComponent } from './ok-dialog/ok-dialog.component';
import { AvviaIterAutorizzativoComponent } from '../avvia-iter-autorizzativo/avvia-iter-autorizzativo.component';
import { SuggestIterVO } from '../../commons/suggestIter-vo';
import { SelectionModel } from '@angular/cdk/collections';
import { SuggStatoIterVO } from '../../commons/sugg-stato-iter-vo';
import { ParametroIterAutorizzativoVO } from '../../commons/parametro-iter-autorizzativo-vo';
import { ListaIterVO } from '../../commons/lista-iter-vo';
import { IterAutorizzativiVO } from '../../commons/iter-autorizzativi-vo';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-iter-autorizzativi',
  templateUrl: './iter-autorizzativi.component.html',
  styleUrls: ['./iter-autorizzativi.component.scss']
})
export class IterAutorizzativiComponent implements OnInit {
  vuoto: string = '- -';
  spinner: boolean = false;
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;
  criteriRicercaOpened: boolean = true;
  mostraTab: boolean = false;
  myForm: FormGroup;
  errore: boolean ;
  message: string;
  ricercaInCorso:boolean;
  ricercaSenzaParametri :boolean = null;
  ricerca : string = "ricerca in corso attendere...";
  suggStatoIter : SuggStatoIterVO[] = [];
  idProgetto;
  idBandoLinea ;
  subscribers: any = {};
  idUtente;

  listTab : ListaIterVO[] = [];
  elencoIter = new Array;
  dataSource = new MatTableDataSource<ListaIterVO>();
  datiIterRicevutiNull : SuggestIterVO = new SuggestIterVO();
  datiIterRicevutiBando : SuggestIterVO [] = [] ;
  datiIterRicevutiBeneficiario : SuggestIterVO [] = [] ;
  datiIterRicevutiProgetto : SuggestIterVO [] = [] ;
  datiBackEnd : IterAutorizzativiVO = {};
  selection = new SelectionModel<any>(true, []);
  user: UserInfoSec;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  displayedColumns:string[]=["select","descrizioneIter","descrizioneBando","beneficiario","descrizioneProgetto","descrizioneStato","polliceSu","polliceGiu","graffetta","toggleEye"];
  

  constructor(
    private iterAutorizzativiService : IterAutorizzativiService ,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private location: Location,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    ) { 
    this.myForm = this.fb.group({
      bando: new FormControl(""),
      beneficiario: new FormControl(""),
      progetto: new FormControl(""),
      statoIter: new FormControl(""),
      dataDal: new FormControl(""),
      dataAl: new FormControl(""),
    });
  }

  ngOnInit(): void {
    
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.idUtente = data.idUtente;
          this.cercaNoParametri();
          this.selezionareStatoIter();
        }
      })
    });
  }

  valorizzaInput(element){
    this.datiIterRicevutiBeneficiario = [element];
    this.datiIterRicevutiBando = [element];
    this.datiIterRicevutiProgetto = [element];
  }

  popolareSelectCausaEStato(){
    
    /* this.suggerimentoCausaProcedimentoRevoca();
    this.suggerimentoStatoProcedimentoRevoca();
    this.suggerimentoAttivitaProcedimentoRevoca(); 
    progBandoLinea
    idSoggetto
    idProgetto
    */
  }

 

  suggerimentoIterBando(value: string, id : number) {
    let formControls = this.myForm.getRawValue();

  this.datiBackEnd = {
         id : id,
         value: value, 
         idProgetto:this.myForm.get('progetto')?.value?.idProgetto,
         progBandoLinea: null,  
         idSoggetto : this.myForm.get('beneficiario')?.value?.idSoggetto,
        }
      this.iterAutorizzativiService.suggerimentoIter(this.datiBackEnd).subscribe(data => {

        if (data.length > 0) {
          this.datiIterRicevutiBando = data;
        } else {
          this.datiIterRicevutiBando = [
            {
              nomeBando: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
   
  }

  suggerimentoXBando(value: string, id : number) {
    this.ricercaInCorso = true;
    let formControls = this.myForm.getRawValue();
  this.datiBackEnd = {
         id : id,
         value: value, 
         idProgetto:this.myForm.get('progetto')?.value?.idProgetto,
         progBandoLinea: null,  
         idSoggetto : this.myForm.get('beneficiario')?.value?.idSoggetto,
        }
      this.iterAutorizzativiService.suggerimentoIter(this.datiBackEnd).subscribe(data => {
        if (data.length > 0) {
          this.ricercaInCorso = false;
          this.datiIterRicevutiBando = data;
        } else {
          this.ricercaInCorso = false;
          this.datiIterRicevutiBando = [
            {
              denominazione: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
  }

  suggerimentoIterBeneficiario(value: string, id : number) {
   // this.spinner = true;
    let formControls = this.myForm.getRawValue();

  this.datiBackEnd = {
            id : id,
            value: value, 
            idProgetto:this.myForm.get('progetto')?.value?.idProgetto,
            progBandoLinea: this.myForm.get('bando')?.value?.progBandoLinea,  
            idSoggetto : null,
        }
        
      this.iterAutorizzativiService.suggerimentoIter(this.datiBackEnd).subscribe(data => {
        if (data.length > 0) {
          this.spinner = false;
          this.datiIterRicevutiBeneficiario = data;
        } 
        else if(data.length <=0){
          this.spinner = false;
          this.datiIterRicevutiBeneficiario = [
            {
              denominazione: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
  
  } 

  suggerimentoXBeneficiario(value: string, id : number) {
    this.ricercaInCorso = true;
    let formControls = this.myForm.getRawValue();

  this.datiBackEnd = {
            id : id,
            value: value, 
            idProgetto:this.myForm.get('progetto')?.value?.idProgetto,
            progBandoLinea: this.myForm.get('bando')?.value?.progBandoLinea,  
            idSoggetto : null,
        }
        
      this.iterAutorizzativiService.suggerimentoIter(this.datiBackEnd).subscribe(data => {
        if (data.length > 0) {
          this.ricercaInCorso = false;
          this.datiIterRicevutiBeneficiario = data;
        } else {
          this.ricercaInCorso = false;
          this.datiIterRicevutiBeneficiario = [
            {
              denominazione: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
    
   
  }
  
  suggerimentoIterProgetto(value: string, id : number) {
  //  this.spinner = true;
    let formControls = this.myForm.getRawValue();

  this.datiBackEnd = {
          id : id,
          value: value, 
          idProgetto:null,
          progBandoLinea: this.myForm.get('bando')?.value?.progBandoLinea,  
          idSoggetto : this.myForm.get('beneficiario')?.value?.idSoggetto,
        }
      this.iterAutorizzativiService.suggerimentoIter(this.datiBackEnd).subscribe(data => {
        if (data.length > 0) {
          this.spinner = false;
          this.datiIterRicevutiProgetto = data;
        } else {
          this.spinner = false;
          this.datiIterRicevutiProgetto = [
            {
              codiceVisualizzato: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
    
   
  }
  suggerimentoXProgetto(value: string, id : number) {
   /*  this.datiIterRicevutiProgetto.codiceVisualizzato = "caricamento..."
    let formControls = this.myForm.getRawValue();
    this.datiBackEnd.push(this.datiIterRicevutiProgetto); */

      this.datiBackEnd = {
              id : id,
              value: value, 
              idProgetto:null,
              progBandoLinea: this.myForm.get('bando')?.value?.progBandoLinea,  
              idSoggetto : this.myForm.get('beneficiario')?.value?.idSoggetto,
            }
      this.iterAutorizzativiService.suggerimentoIter(this.datiBackEnd).subscribe(data => {
        if (data.length > 0) {
          this.ricercaInCorso = false;
          this.datiIterRicevutiProgetto = data;
        } else {
          this.ricercaInCorso = false;
          this.datiIterRicevutiProgetto = [
            {
              codiceVisualizzato: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
   
   
  }

  selezionareStatoIter() {
    this.iterAutorizzativiService.getStatoIter().subscribe(data => {
        this.suggStatoIter = data;
      } 
    ) 
  }

  cercaNoParametri(){
    this.ricercaSenzaParametri = true;
    let parametro : ParametroIterAutorizzativoVO = {} ;
    this.iterAutorizzativiService.cercaIterAutorizzativi(parametro).subscribe(data =>  {
       this.listTab = data;
       this.dataSource = new MatTableDataSource<ListaIterVO>(this.listTab);
       this.paginator.length = this.listTab.length;
       this.paginator.pageIndex = 0;
       this.dataSource.paginator = this.paginator;
       this.dataSource.sort = this.sort;
       this.mostraTab = true;
       this.criteriRicercaOpened = false;
       this.spinner = false;
       
   }, err => {
     this.spinner = false;
     this.showMessageError("Errore in fase di ricerca: la ricerca non ha prodotto risultati.")
     this.handleExceptionService.handleNotBlockingError(err);
 
   });
  }

  search() {
    this.ricercaSenzaParametri = false;
    this.spinner = true;
    let formControls = this.myForm.getRawValue();
        if ((formControls.bando.length < 3) &&
            (formControls.beneficiario.length < 3) &&
            (formControls.progetto.length < 3) &&
            (formControls.statoIter == "" || formControls.statoIter == null || formControls.statoIter == undefined) &&
            (formControls.dataDal == "") &&
            (formControls.dataAl == "") ) 
                {
                  this.spinner = false;
                  this.showMessageError("Inserire almeno un parametro di ricerca.")
                } 
         else {
             let parametro : ParametroIterAutorizzativoVO;
             
               if(formControls.statoIter === undefined)
                 {
                    parametro  = {
                                    idSoggetto: formControls?.beneficiario.idSoggetto,
                                    progrBandoLinea: formControls?.bando.progBandoLinea,
                                    idProgetto: formControls?.progetto.idProgetto,
                                    idStatoIter: undefined,
                                    dataInizio: formControls?.dataDal != "" ? formControls.dataDal : undefined,
                                    dataFine: formControls?.dataAl != "" ? formControls.dataAl : undefined   
                                  }
                    }
                else{

                     parametro  = {
                                    idSoggetto: formControls?.beneficiario.idSoggetto,
                                    progrBandoLinea: formControls?.bando.progBandoLinea,
                                    idProgetto: formControls?.progetto.idProgetto,
                                    idStatoIter: formControls?.statoIter.idStatoIter,
                                    dataInizio: formControls?.dataDal != "" ? formControls.dataDal : undefined,
                                    dataFine: formControls?.dataAl != "" ? formControls.dataAl : undefined
                                  }
                  }
      
        this.resetMessageError();
        this.iterAutorizzativiService.cercaIterAutorizzativi(parametro).subscribe(data =>  {
          if(data.length == 0) {
            this.spinner = false;
            this.showMessageError("Nessuna iter autorizzativo trovato per i parametri inseriti.");
            this.dataSource = new MatTableDataSource<ListaIterVO>();

          } else {
            this.listTab = data;
            this.dataSource = new MatTableDataSource<ListaIterVO>(this.listTab);
            //memorizzo paginator
            this.paginator.length = this.listTab.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;

            this.mostraTab = true;
            this.criteriRicercaOpened = false;

            this.spinner = false;
          }

        }, err => {

          this.spinner = false;
          this.showMessageError("Errore in fase di ricerca: la ricerca non ha prodotto risultati.")
          this.handleExceptionService.handleNotBlockingError(err);

        });

    }

  }  

   listaIterSceltiCheckBox = [];
  scegliDomandeCheckbox(domandaSceltaCheckbox) {
    if (this.listaIterSceltiCheckBox.includes(domandaSceltaCheckbox)) {
      this.listaIterSceltiCheckBox = this.listaIterSceltiCheckBox.filter(
        (e) => e != domandaSceltaCheckbox
      );
    } else this.listaIterSceltiCheckBox.push(domandaSceltaCheckbox);
    
  } 

  tutteDomandeCheckBoxBoolean = false;
  scegliTutteDomandeCheckbox() {
    this.tutteDomandeCheckBoxBoolean = this.tutteDomandeCheckBoxBoolean
      ? false
      : true;
    if (this.tutteDomandeCheckBoxBoolean) {
      this.listaIterSceltiCheckBox = this.elencoIter;
    } else {
      this.listaIterSceltiCheckBox = [];
    }
  } 

   isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data?.filter(d => d.abilitaGestioneIter != false ));
    
  }

  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
  }


  annullaFiltri() {
    this.myForm.reset();
    this.myForm = this.fb.group({
      bando: new FormControl(""),
      beneficiario: new FormControl(""),
      progetto: new FormControl(""),
      statoIter: new FormControl(""),
      dataDal: new FormControl(""),
      dataAl: new FormControl(""),
    });
  }

  allegatiIter(element) {
    const dialogRef = this.dialog.open(AllegatiIterComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
       // dataKey2:this.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
    //  this.ngOnInit();
    }); 
  }

  dettaglioIter(element) {
    const dialogRef = this.dialog.open(DettaglioIterComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
       // dataKey2:this.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
     // this.search();
    }); 
  }

  autorizzaIter(element) {
    const dialogRef = this.dialog.open(OkDialogComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        title: 'Autorizza iter',
        message: 'Sei sicuro di voler autorizzare gli iter selezionati?',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if(result === true){
        let dataBack =  {
          idWorkFlowList :[element.idWorkFlow],
          motivazione:null,
         }
      this.iterAutorizzativiService.autorizzaIter(dataBack).subscribe((data) => {
        if (data == true) {
          this.message = "iter autorizzato con successo";
          this.errore = false;
          setTimeout(() => {
            this.errore = null;
            if(this.ricercaSenzaParametri === true){
                this.cercaNoParametri();
            }else {
                this.search();
            }
          }, 2000); 
        }
        else if (data == false) {
          this.errore = true;
          this.message =  "Errore non è stato possibile autorizzare l'iter";
          setTimeout(() => {
            this.errore = null;
            if(this.ricercaSenzaParametri === true){
                this.cercaNoParametri();
            }else {
                this.search();
            }
          }, 2000); 
        }
      },
        (err) => {
            this.errore = true;
            this.message = err.statusText;
        }
      );
      }else return;
      
    });
  }

  autorizzaSelezionati(){
    if (this.selection.selected.length <= 0) {
      const confirmDialog = this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Nessun iter selezionato',
          message: 'si prega di selezionare almeno un iter',
          conferma : false
        },
      });
    } else {
      this.selection.selected;
      const dialogRef = this.dialog.open(OkDialogComponent, {
        height: 'auto',
        width: '50rem',
        data: {
          title: 'Autorizza iter',
          message: 'Sei sicuro di voler autorizzare gli iter selezionati?',
        },
      });
      dialogRef.afterClosed().subscribe((result) => {
        if(result === true){
          let idWorkFlowList = [];
          let datiBackEnd  = this.selection.selected;
          idWorkFlowList = this.selection.selected.map(p =>p.idWorkFlow);
           let dataBack =  {
            idWorkFlowList :idWorkFlowList,
            motivazione:null,
           } 
      this.iterAutorizzativiService.autorizzaIter(dataBack).subscribe((data) => {
          if (data == true) {
            this.message = "Iter selezionati autorizzati con successo";
            this.errore = false;
            setTimeout(() => {
              this.errore = null;
              this.selection.clear();
              if(this.ricercaSenzaParametri === true){
                  this.cercaNoParametri();
              }else {
                  this.search();
              }
            }, 2000); 
          }
          else if (data == false) {
            this.errore = true;
            this.message = "Errore non è stato possibile autorizzare gli iter selezionati";
            setTimeout(() => {
              this.errore = null;
              this.selection.clear();
              if(this.ricercaSenzaParametri === true){
                  this.cercaNoParametri();
              }else {

                  this.search();
              }
            }, 2000); 
          }
        },
          (err) => {
              this.errore = true;
              this.message = err.statusText;
          }
        );
        }else return;
      
      }); 
    } 
    
  }

  respingiIter(element) {
    const dialogRef = this.dialog.open(RespingiIterComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: [element], 
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      setTimeout(() => {
        this.errore = null;
        if(this.ricercaSenzaParametri === true){
            this.cercaNoParametri();
        }else {
            this.search();
        }
      }, 2000); 
    }); 
  }

  respingiSelezionati() {
    if (this.selection.selected.length <= 0) {
      const confirmDialog = this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Nessun iter selezionato',
          message: 'si prega di selezionare almeno un iter',
          conferma : false
        },
      });
    } else {
      this.selection.selected;
      const dialogRef = this.dialog.open(RespingiIterComponent, {
        height: 'auto',
        width: '50rem',
        data: {
          dataKey: this.selection.selected,
        },
      });
      dialogRef.afterClosed().subscribe((result) => {
        if(result != 'chiudi'){
          setTimeout(() => {
              this.errore = null;
              this.selection.clear();
              if(this.ricercaSenzaParametri === true){
                  this.cercaNoParametri();
              }else {
                  this.search();
              }
            }, 2000); 
        }
        
      });
    } 
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.error = false;
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  goBack() {
    this.location.back();
  }

  displayBeneficiario(element): string {
    return element && element.denominazione ? element.denominazione : '';
  }

  displayBando(element): string {
    return element && element.nomeBando ? element.nomeBando : '';
  }

  displayProgetto(element): string {
    return element && element.codiceVisualizzato ? element.codiceVisualizzato : '';
  }

  /* avviaIterAutorizzativo(){
    let element = "Richiesta integrazione"
    const dialogRef = this.dialog.open(AvviaIterAutorizzativoComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
       
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.ngOnInit();
    }); 
  } */

/* suggerimentoBeneficiario(value: string) {
      this.iterAutorizzativiService.suggestBeneficiario(value).subscribe(data => {
        if (data.length > 0) {
          this.suggBeneficiario = data;
        } else {
          this.suggBeneficiario = [
            {
              denominazione: "Nessuna corrispondenza",
              idSoggetto: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });
  }

  suggerimentoBando(value: string) {
    let formControls = this.myForm.getRawValue();
    this.resetMessageError();
    if (
      (formControls.beneficiario == "undefined" || formControls.beneficiario == null || formControls.beneficiario == "") 
      ) {
        this.showMessageError("Per inserire il bando è necessario aver indicato prima il beneficiario .")
    } else if
    (value.length >= 3) {
      this.suggBando = [
        {
          nomeBandoLineaIntervent: "Caricamento...",
          idBandoLineaIntervent: "",
        }
      ]
      this.iterAutorizzativiService.suggestBando(value,formControls.beneficiario.idSoggetto).subscribe(data => {
        if (data.length > 0) {
          this.suggBando = data;
        } else {
          this.suggBando = [
            {
              nomeBandoLineaIntervent: "Nessuna corrispondenza",
              idBandoLineaIntervent: "",
            }
          ]
        }
      })
    } else {
      this.suggBando = []
    }
  }

  suggerimentoProgetto(value: string) {
    let formControls = this.myForm.getRawValue();
    this.resetMessageError();
    if (
      (formControls.beneficiario == "undefined" || formControls.beneficiario == null || formControls.beneficiario == "") ||
      (formControls.bando == undefined || formControls.bando == null || formControls.bando == "")
      ) {
        this.showMessageError("Per inserire il progetto è necessario aver indicato prima il beneficiario e il bando.")
    } else if (value.length >= 3) {

      this.suggProgetto =  [
        {
          codiceVisualizzato: "Caricamento...",
          idProgetto: "",
          titoloProgetto: "Caricamento..."
        }
      ];

      //chiamata al BE
      this.iterAutorizzativiService.suggestProgetto(value, formControls.beneficiario.idSoggetto, formControls.bando.idBandoLineaIntervent).subscribe(data => {


        if (data.length > 0) {
          this.suggProgetto = data;
        } else {
          this.suggProgetto =  [
            {
              codiceVisualizzato: "Nessuna corrispondenza",
              idProgetto: "",
              titoloProgetto: "Nessuna corrispondenza"
            }
          ];

        }
      })
    } else {
      this.suggProgetto = []
    }
  }
 */

  
  /* getTendinaBando(){
    this.iterAutorizzativiService.getTendinaBando().subscribe(data => {
      this.datiIterRicevutiBando =  data ;
           [{"id":0,
           "idSoggetto":397,
           "idProgetto":83,
           "progBandoLinea":2,
           "denominazione":"NEGRI F.LLI S.R.L.",
           "codiceVisualizzato":"0111000009",
           "nomeBando":"POR. FESR 07-13 - ASSE II.1.1-3 - ENERGIA - PRODUZIONE DI ENERGIE RINNOVABILI",
           "value":null},
           
           {"id":0,
           "idSoggetto":318,
           "idProgetto":12145,
           "progBandoLinea":103,
           "denominazione":"BRANDONI SPA",
           "codiceVisualizzato":"0228000056",
           "nomeBando":"POR. FESR 07-13 - ASSE II.1.1-3 - ENERGIA - PRODUZIONE DI ENERGIE RINNOVABILI",
           "value":null}]

           this.datiIterRicevutiBando = data;
           this.datiIterRicevutiBeneficiario = data;
           this.datiIterRicevutiProgetto = data;
        
      } 
    ) 
  }

   getTendinaProgetto(){
    this.iterAutorizzativiService.getTendinaProgetto().subscribe(data => {
      this.datiIterRicevutiProgetto = data;
      [{"id":0,
      "idSoggetto":397,
      "idProgetto":83,
      "progBandoLinea":2,
      "denominazione":"NEGRI F.LLI S.R.L.",
      "codiceVisualizzato":"0111000009",
      "nomeBando":"POR. FESR 07-13 - ASSE II.1.1-3 - ENERGIA - PRODUZIONE DI ENERGIE RINNOVABILI",
      "value":null},
      {"id":0,
      "idSoggetto":318,
      "idProgetto":12145,
      "progBandoLinea":103,
      "denominazione":"BRANDONI SPA",
      "codiceVisualizzato":"0228000056",
      "nomeBando":"PAR-FSC ASSE I.4.1414 PIU' EXPORT VOUCHER SINGOLI 2012 3° EDIZIONE",
      "value":null}]
        
      } 
    ) 
  }

  getTendinaBeneficiario(){
    this.iterAutorizzativiService.getTendinaBeneficiario().subscribe(data => {
      this.datiIterRicevutiBeneficiario = data;
      [{"id":0,
      "idSoggetto":397,
      "idProgetto":83,
      "progBandoLinea":2,
      "denominazione":"NEGRI F.LLI S.R.L.",
      "codiceVisualizzato":"0111000009",
      "nomeBando":"POR. FESR 07-13 - ASSE II.1.1-3 - ENERGIA - PRODUZIONE DI ENERGIE RINNOVABILI",
      "value":null},
      {"id":0,
      "idSoggetto":318,
      "idProgetto":12145,
      "progBandoLinea":103,
      "denominazione":"BRANDONI SPA",
      "codiceVisualizzato":"0228000056",
      "nomeBando":"PAR-FSC ASSE I.4.1414 PIU' EXPORT VOUCHER SINGOLI 2012 3° EDIZIONE",
      "value":null}]
      } 
    ) 

  }  */
}
