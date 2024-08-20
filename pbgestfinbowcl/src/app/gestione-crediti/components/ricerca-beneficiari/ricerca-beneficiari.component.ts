/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { FinanziamentoErogato } from '../../commons/dto/finanziamento-erogato';
import { RicBenResponseService } from '../../services/ricben-response-service.service';
import { Constants } from '../../../core/commons/util/constants';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { Observable, Subscriber, Subscription, observable, of } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfigService } from 'src/app/core/services/config.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { NavigationRicercaBeneficiariService } from '../../services/navigation-ricerca-beneficiari.service';
import { FiltroRicercaBeneficiari } from '../../commons/dto/filtro-ricerca-beneficiari';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RevAmmDialogComponent } from '../dialog-revoca-amministrativa/dialog-rev-amm.component';
import { DettaglioFinanziamentoErogato } from '../../commons/dto/dettaglio-finanziamento-erogato';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { debounceTime, distinctUntilChanged, switchMap, startWith, filter, finalize, map, defaultIfEmpty, isEmpty } from 'rxjs/operators';
import { DialogEstrattoConto } from '../dialog-estratto-conto/dialog-estratto-conto.component';
import { DialogPianoAmmortamento } from '../dialog-piano-ammortamento/dialog-piano-ammortamento.component';
import { Workbook } from 'exceljs';
import * as FileSaver from 'file-saver'

@Component({
  selector: 'app-ricerca-beneficiari',
  templateUrl: './ricerca-beneficiari.component.html',
  styleUrls: ['./ricerca-beneficiari.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaBeneficiariComponent implements OnInit, OnDestroy {

  user: UserInfoSec;
  idUtente: string;
  http: any;

  public myForm: FormGroup;

  fin: Array<FinanziamentoErogato>;
  debitoResiduo: any;

  public subscribers: any = {};
  
  suggCodFis: Observable<string[]>;
  suggNag: Observable<string[]>;
  suggPartIva: Observable<string[]>;
  suggDenom: Observable<string[]>;
  suggDescBan: Observable<string[]>;
  suggCodPro: Observable<string[]>;

  spinner: boolean; 
  loadingSuggestions: boolean = false;
  loadingFromAmmvo: number = 0;

  displayedColumns: string[] = ['ndg', "denominazione", "codiceFiscale", "partitaIva", "formaGiuridica", "statoAzienda", "rating", "dataProcedura",'actions'];
  displayedInnerColumns: string[] = ['titoloBando','codProgetto', 'modAgevolazione','statoAgevolazione','statoCredito','statoCess', 'importoAmmesso', 'totaleErogato', 'totaleErogatoFin', 'totaleErogatoBanca', 'creditoResiduo', 'agevolazione','dataRevocaBancaria','revocaAmministrativa', 'libMandatoBanca', 'actions'];
  dataSource: MatTableDataSource<FinanziamentoErogato> = new MatTableDataSource<FinanziamentoErogato>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  criteriRicercaOpened: boolean = true;
  criteriRicercaState: boolean = true;

  showResults: boolean = false;

  isTableExpanded: boolean = false;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    private resService: RicBenResponseService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private navigationService: NavigationRicercaBeneficiariService,
    public dialog: MatDialog,
  ) { }

  subscribersList:Subscription[]=[]

  subscribeAdd(sub:Subscription):Subscription{
    this.subscribersList.push(sub);
    return sub;
  }

  ngOnDestroy(): void {
   for(let i = 0; i < this.subscribersList.length; i++){
    this.subscribersList[i].unsubscribe()
   }
  }


  ngOnInit(): void {

    this.myForm = this.fb.group({
      codiceFiscale: new FormControl(""),
      nag: new FormControl(""),
      partitaIva: new FormControl(""),
      denominazione: new FormControl(""),
      descBando: new FormControl(""),
      codiceProgetto: new FormControl(""),
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.ripristinaRicerca();
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

    this.initSuggest();
      
  }



  search() {

    let formControls = this.myForm.getRawValue();
    if ((formControls.codiceFiscale.length < 3) &&
      (formControls.nag.length < 3) &&
      (formControls.partitaIva.length < 3) &&
      (formControls.denominazione.length < 3) &&
      (formControls.descBando.length < 3) &&
      (formControls.codiceProgetto.length < 3)) {

      this.showMessageError("Inserire almeno 3 caratteri per avviare una ricerca.");

    } else {

      this.spinner = true;

      this.resetMessageError();

      this.subscribers.cerca = this.resService.cercaBeneficiari(
        formControls.codiceFiscale,
        formControls.nag,
        formControls.partitaIva,
        formControls.denominazione,
        formControls.descBando,
        formControls.codiceProgetto).subscribe(data => {
          if (data) {
            this.fin = data;
            //console.log(this.fin);

            /* Imposto il caricamento degli importi che saranno recuperati in un secondo momento
              per motivi di performance */
            this.loadingFromAmmvo = 0;
            this.fin.forEach(outer => {
              outer.listaDettagli.forEach(inner => {
                inner.isAgevolazioneLoading = true;
                inner.isCreditoResiduoLoading = true;
                this.loadingFromAmmvo = this.loadingFromAmmvo + 1;
              })
            })

            this.dataSource = new MatTableDataSource<FinanziamentoErogato>(this.fin);

            this.ripristinaPaginator();

          }

          //console.log(this.fin);
          this.showResults = true;
          this.spinner = false;

          this.criteriRicercaState = false;

          this.valorizzaCreditoResiduo();

          this.salvaRicerca();
          
        }, err => {
          this.spinner = false;
          this.showMessageError("Si è verificato un errore in fase di ricerca, riprova fra poco. Se il problema persiste, contatta l'assistenza.")
          this.handleExceptionService.handleBlockingError(err);
        });
    }
  }

  // Metodo che popola le colonne "Credito residuo" e "Agevolazione" della tabella di dettaglio del beneficiario (agevolazioni)
  valorizzaCreditoResiduo() {

    this.fin.forEach((outer, outIndex) => {
      outer.listaDettagli.forEach((dett, inIndex) => {

        this.subscribeAdd(this.resService.getCreditoResiduoEAgevolazione(dett.idProgetto, dett.idBando, dett.idModalitaAgevolazioneOrig, dett.idModalitaAgevolazioneRif).subscribe(debitoResiduoRes => {
          if (debitoResiduoRes) {
            this.debitoResiduo = debitoResiduoRes;

            dett.creditoResiduo = this.debitoResiduo.debitoResiduo;
            dett.agevolazione = this.debitoResiduo.oneriAgevolazione;
          }
          
          dett.isCreditoResiduoLoading = false;
          dett.isAgevolazioneLoading = false;
          this.loadingFromAmmvo = this.loadingFromAmmvo - 1;

          this.fin[outIndex].listaDettagli[inIndex] = {...dett};
          this.dataSource.data = [...this.fin];
        }, err => {
          dett.isCreditoResiduoLoading = false;
          dett.isAgevolazioneLoading = false;
          this.loadingFromAmmvo = this.loadingFromAmmvo - 1;

          this.fin[outIndex].listaDettagli[inIndex] = {...dett};
          this.dataSource.data = [...this.fin];
          
          this.handleExceptionService.handleNotBlockingError(err);
        }))
      })
    })
  } 

  reset() {
    this.myForm.reset({
      codiceFiscale: '',
      nag: '',
      partitaIva: '',
      denominazione: '',
      descBando: '',
      codiceProgetto: ''});

    this.resetMessageError()
    this.showResults = false;

    // Se stavo recuperando ancora gli importi, annullo la chiamata
    for(let i = 0; i < this.subscribersList.length; i++){
      this.subscribersList[i].unsubscribe()
    }

    // Elimino la ricerca salvata
    sessionStorage.removeItem('searched_crediti_obj');

    // TEST
    //this.spinner = true;
  }

  salvaRicerca() {
    let formControls = this.myForm.getRawValue();

    
    let ricerca: FiltroRicercaBeneficiari = new FiltroRicercaBeneficiari(
      formControls.codiceFiscale || '',
      formControls.nag || '',
      formControls.partitaIva || '',
      formControls.denominazione || '',
      formControls.descBando || '',
      formControls.codiceProgetto || ''
    );

    //this.navigationService.filtroRicercaBeneficiariSelezionato = ricerca
    
    sessionStorage.setItem('searched_crediti_obj', JSON.stringify(ricerca));
    
    // salva Paginator
    if (this.dataSource) {
      sessionStorage.setItem('searched_crediti_paginatorPageIndexTable', this.dataSource.paginator.pageIndex.toString());
      sessionStorage.setItem('searched_crediti_paginatorPageSizeTable', this.dataSource.paginator.pageSize.toString());
      //this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      //this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }

  }

  ripristinaRicerca() {
    //let filtro = this.navigationService.filtroRicercaBeneficiariSelezionato;

    let filtro = JSON.parse(sessionStorage.getItem('searched_crediti_obj'));

    if (filtro) {
      this.myForm = this.fb.group({
        codiceFiscale: new FormControl(filtro.codiceFiscale),
        nag: new FormControl(filtro.nag),
        partitaIva: new FormControl(filtro.partitaIva),
        denominazione: new FormControl(filtro.denominazione),
        descBando: new FormControl(filtro.descBando),
        codiceProgetto: new FormControl(filtro.codiceProgetto),
      });
      
      this.initSuggest();

      this.search();
    }


  }

  ripristinaPaginator() {
    let filtro = JSON.parse(sessionStorage.getItem('searched_crediti_obj'));
    let searched_crediti_paginatorPageIndexTable = JSON.parse(sessionStorage.getItem('searched_crediti_paginatorPageIndexTable'));
    let searched_crediti_paginatorPageSizeTable = JSON.parse(sessionStorage.getItem('searched_crediti_paginatorPageSizeTable'));

    if (filtro) {
      if (searched_crediti_paginatorPageIndexTable != null && searched_crediti_paginatorPageIndexTable != undefined) {
        //this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
        //this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
        this.paginator.pageIndex = searched_crediti_paginatorPageIndexTable;
        this.paginator.pageSize = searched_crediti_paginatorPageSizeTable;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }

    } else {
      this.paginator.length = this.fin.length;
      this.paginator.pageIndex = 0;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }

  rowEdit(dettRow) {
    this.salvaRicerca();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI + "/modificaBeneficiario"],
      {
        queryParams: {
          idProgetto: dettRow.idProgetto,
          idModalitaAgevolazione: dettRow.idModalitaAgevolazioneOrig,
          totEro: dettRow.totaleErogato, // importo concesso 
          totFin: dettRow.importoAmmesso, //  importo erogato finpiemonte
        }
      }
    );

  }

  /* NON PIU' USATO
  goToAIAC(progetto: any, totFin: any, totBan: any) {
    this.saveDataForNavigation();
    var totEro: number = Number(totFin) + Number(totBan);

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI + "/attivitaIstruttoreAreaCrediti"],
      { queryParams: { progetto, totFin, totEro } }
    );
  }*/

  initSuggest() {

    // Codice fiscale
    this.suggCodFis = this.myForm.get('codiceFiscale').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.resService.getSuggestion(value, 1).pipe(
            //startWith(['Caricamento...']), // Funziona ma l'opzione rimane selezionabile
            startWith([]),
            finalize(() => {
              this.loadingSuggestions = false;
            }));
        } else {
          return of([])
        }
      }),
    )

    // NDG
    this.suggNag = this.myForm.get('nag').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.resService.getSuggestion(value, 2).pipe(
            //startWith(['Caricamento...']), // Funziona ma l'opzione rimane selezionabile
            startWith([]),
            finalize(() => {
              this.loadingSuggestions = false;
            }));
        } else {
          return of([])
        }
      }),
    )

    // Partita iva
    this.suggPartIva = this.myForm.get('partitaIva').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.resService.getSuggestion(value, 3).pipe(
            //startWith(['Caricamento...']), // Funziona ma l'opzione rimane selezionabile
            startWith([]),
            finalize(() => {
              this.loadingSuggestions = false;
            }));
        } else {
          return of([])
        }
      }),
    )

    // Denominazione
    this.suggDenom = this.myForm.get('denominazione').valueChanges.pipe(
      debounceTime(400),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.resService.getSuggestion(value, 4).pipe(
            //startWith(['Caricamento...']), // Funziona ma l'opzione rimane selezionabile
            startWith([]),
            finalize(() => {
              this.loadingSuggestions = false;
            }));
        } else {
          return of([])
        }
      }),
    )

    // Descrizione bando
    this.suggDescBan = this.myForm.get('descBando').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.resService.getSuggestion(value, 5).pipe(
            //startWith(['Caricamento...']), // Funziona ma l'opzione rimane selezionabile
            startWith([]),
            finalize(() => {
              this.loadingSuggestions = false;
            }));
        } else {
          return of([])
        }
      }),
    )

    // Codice progetto
    this.suggCodPro = this.myForm.get('codiceProgetto').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.resService.getSuggestion(value, 6).pipe(
            //startWith(['Caricamento...']), // Funziona ma l'opzione rimane selezionabile
            startWith([]),
            finalize(() => {
              this.loadingSuggestions = false;
            }));
        } else {
          return of([])
        }
      }),
    )


    /*if (id == 1) {

      if (value.length >= 3) {
        this.suggCodFis = ["Caricamento..."];
        this.resService.getSuggestion(value, 1).subscribe(data => { if (data.length > 0) { this.suggCodFis = data } else { this.suggCodFis = ["Nessuna corrispondenza"] } })
      } else { this.suggCodFis = [] }

    } else if (id == 2) {

      if (value.length >= 3) {
        this.suggNag = ["Caricamento..."];
        this.resService.getSuggestion(value, 2).subscribe(data => { if (data.length > 0) { this.suggNag = data } else { this.suggNag = ["Nessuna corrispondenza"] } })
      } else { this.suggNag = [] }

    } else if (id == 3) {

      if (value.length >= 3) {
        this.suggPartIva = ["Caricamento..."];
        this.resService.getSuggestion(value, 3).subscribe(data => { if (data.length > 0) { this.suggPartIva = data } else { this.suggPartIva = ["Nessuna corrispondenza"] } })
      } else { this.suggPartIva = [] }

    } else if (id == 4) {

      if (value.length >= 3) {
        this.suggDenom = ["Caricamento..."];
        this.resService.getSuggestion(value, 4).subscribe(data => { if (data.length > 0) { this.suggDenom = data } else { this.suggDenom = ["Nessuna corrispondenza"] } })
      } else { this.suggDenom = [] }

    } else if (id == 5) {

      if (value.length >= 3) {
        this.suggDescBan = ["Caricamento..."];
        this.resService.getSuggestion(value, 5).subscribe(data => { if (data.length > 0) { this.suggDescBan = data } else { this.suggDescBan = ["Nessuna corrispondenza"] } })
      } else { this.suggDescBan = [] }

    } else if (id == 6) {

      if (value.length >= 3) {
        this.suggCodPro = ["Caricamento..."];
        this.resService.getSuggestion(value, 6).subscribe(data => { if (data.length > 0) { this.suggCodPro = data } else { this.suggCodPro = ["Nessuna corrispondenza"] } })
      } else { this.suggCodPro = [] }

    }*/

  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }


  openRevDialog(row: DettaglioFinanziamentoErogato): void {
    this.dialog.open(RevAmmDialogComponent, {
      width: '60%',
      //height: '60%',
      panelClass: "mat-dialog-height-transition",
      data: { row: row }
    });

  }


  // aggiungere colonna causale dettaglio erogato

  // Sommare solo se più soluzioni
  checkTotaleErogato(row: DettaglioFinanziamentoErogato) {
    //console.log("dett", row)
    var totBanca: number = 0;
    var totFin: number = 0;

    // if (row.numAgevolazioni > 1) {
    //   if (row.totaleBanca != null) {totBanca = row.totaleBanca}
    //   if (row.importoFinpiemonte != null) {totFin = row.importoFinpiemonte}

    //   return totBanca + totFin;
    // } else {
    //   return row.importoFinpiemonte
    // }


    /*  Ho aggiornato l'oggetto DettaglioFinanziamentoErogato facendolo corrispondere a quello in BE,
        questo metodo usava proprietà che esistevano nell'oggetto in FE ma non venivano mai popolati in BE.
        Non ho idea di come potesse essere accettata una cosa del genere ma prossima volta FATE CORRISPONEDERE I DUE OGGETTI!

    totBanca = this.getTotaleBanca(row); 
    if(row.idModalitaAgevolazioneOrig==1){
      return row.importoFinpiemonte; 
    }
    return totBanca + row?.importoFinpiemonte; */

    totBanca = this.getTotaleBanca(row); 
    if(row.idModalitaAgevolazioneOrig==1){
      return row.totaleErogatoFin; 
    }
    return totBanca + row?.totaleErogatoFin;
  }

  getTotaleBanca(row: DettaglioFinanziamentoErogato){
    var importo: any; 
    var perc: any;

    /*  Ho aggiornato l'oggetto DettaglioFinanziamentoErogato facendolo corrispondere a quello in BE,
        questo metodo usava proprietà che esistevano nell'oggetto in FE ma non venivano mai popolati in BE.
        Non ho idea di come potesse essere accettata una cosa del genere ma prossima volta FATE CORRISPONEDERE I DUE OGGETTI!

    perc = (row.importoFinpiemonte / row.quotaImpAgevolato) ;
    importo = perc * row.importoFinanziamentoBanca; 
    if(row.importoFinpiemonte!=0 && row.quotaImpAgevolato!=0){
      return importo; 
    }  else {
      return 0; 
    } */

    perc = (row.totaleErogatoFin / row.agevolazione) ;
    importo = perc * row.totaleErogatoBanca; 
    if(row.totaleErogatoFin!=0 && row.agevolazione!=0){
      return importo; 
    }  else {
      return 0; 
    }

  }

  /*checkStatoAgevolazione(row: DettaglioFinanziamentoErogato) {
    if(row.numErogazioni > 1 || (row.numErogazioni == 1 && row.idUltimaCausale == 1)) {
      return "Più soluzioni";
    } else {
      return "Unica soluzione";
    }
  }*/

  openEstrattoConto(row: FinanziamentoErogato, dettRow: DettaglioFinanziamentoErogato) {
    this.dialog.open(DialogEstrattoConto, {
      minWidth: '40vw',
      data: {
        componentFrom: 1,

        bando: dettRow.titoloBando,
        codProgetto: dettRow.codProgetto,
        beneficiario: row.denominazione,

        idProgetto: dettRow.idProgetto,
        ndg: row.ndg,
        idModalitaAgevolazione: dettRow.idModalitaAgevolazioneOrig, 
        idModalitaAgevolazioneRif:  dettRow.idModalitaAgevolazioneRif,
       }
    });
  }

  openPianoAmmortamento(row: FinanziamentoErogato, dettRow: DettaglioFinanziamentoErogato) {
    this.dialog.open(DialogPianoAmmortamento, {
      minWidth: '40vw',
      data: {
        componentFrom: 1,

        bando: dettRow.titoloBando,
        codProgetto: dettRow.codProgetto,
        beneficiario: row.denominazione,

        idProgetto: dettRow.idProgetto,
        ndg: row.ndg,
        idModalitaAgevolazione: dettRow.idModalitaAgevolazioneOrig, 
        idModalitaAgevolazioneRif:  dettRow.idModalitaAgevolazioneRif,
       }
    });
  }


  downloadExcel(){
    let fileName: string = 'Estrazione Monitoraggio Crediti';

    let outerHeader = ['NDG', 'Denominazione', 'Codice fiscale', 'Partita iva', 'Forma giuridica', 'Stato azienda', 'Rating', 'Data procedura concorsuale'];
    let innerHeader = ['Bando', 'Progetto', 'Tipo Agevolazione', 'Stato Agevolazione', 'Stato credito', 'Stato cessione', 'Importo ammesso', 'Importo concesso Finpiemonte', 'Totale erogato Finpiemonte', 'Totale erogato Banca', 'Credito residuo', 'Agevolazione', 'Data revoca bancaria', 'Revoca amministrativa', 'Liberazione mandato banca'];

    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Monitoraggio Crediti');

    const formValues = this.myForm.getRawValue();

    // Aggiungo righe di dettaglio
    worksheet.addRow(['Codice Fiscale', formValues.codiceFiscale ? formValues.codiceFiscale : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['NDG', formValues.nag ? formValues.nag : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Partita IVA', formValues.partitaIva ? formValues.partitaIva : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Denominazione / Cognome e Nome', formValues.denominazione ? formValues.denominazione : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Descrizione Bando', formValues.descBando ? formValues.descBando : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Codice Progetto', formValues.codiceProgetto ? formValues.codiceProgetto : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    
    //worksheet.addRow([]);

    let tablePosition: number = 8;  // A8

    this.fin.forEach(outerItem => {
      
      // Aggiungo tabella esterna
      let outerCols = outerHeader.map(colName => ({ name: colName, filterButton: true }));
      let outerData = new Array<any>();
      // Estendo le righe della prima tabella arrivando alla lunghezza della seconda
      outerItem.listaDettagli.map(() => {
        outerData.push(
          [outerItem.ndg,
            outerItem.denominazione,
            outerItem.codiceFiscale,
            outerItem.partitaIva,
            outerItem.formaGiuridica,
            outerItem.statoAzienda,
            outerItem.rating,
            ((outerItem.statoAzienda=='Attiva' || outerItem.statoAzienda=='Inattiva' || outerItem.statoAzienda=='ND') ? null : outerItem.dataProcedura),
          ]
        );
      })
      worksheet.addTable({
        name: ('tabA' + tablePosition),
        ref: ('A' + tablePosition),
        headerRow: true,
        totalsRow: false,
        style: {
          theme: 'TableStyleLight8',
          showRowStripes: false,
        },
        columns: outerCols,
        rows: outerData,
      });

      
      // Aggiungo tabella interna
      let innerCols = innerHeader.map(colName => ({ name: colName, filterButton: true }));
      let innerData = new Array<any>();
      outerItem.listaDettagli.map(inElem => {
        innerData.push(
          [inElem.titoloBando,
          inElem.codProgetto,
          inElem.dispDescAgevolazione,
          inElem.dispStatoAgevolazione,
          inElem.descStatoCredito,
          ((inElem.statoCessione === null || inElem.idModalitaAgevolazioneRif==10) ? null : inElem.statoCessione),
          inElem.importoAmmesso,
          inElem.totaleErogato,
          inElem.totaleErogatoFin,
          inElem.totaleErogatoBanca,
          inElem.creditoResiduo,
          inElem.agevolazione,
          inElem.dataRevocaBancaria,
          inElem.revocaAmministrativa,
          (inElem.libMandatoBanca == '-' ? null : inElem.libMandatoBanca)]
        );
      })
      worksheet.addTable({
        name: ('tabB' + tablePosition),
        ref: ('I' + tablePosition),
        headerRow: true,
        totalsRow: false,
        style: {
          theme: 'TableStyleMedium9',
          showRowStripes: false,
        },
        columns: innerCols,
        rows: innerData,
      });

      tablePosition = tablePosition + outerItem.listaDettagli.length + 2;
    });


    /*worksheet.getColumn('E').numFmt = '#,##0.00';
    worksheet.getColumn('F').numFmt = '#,##0.00';
    worksheet.getColumn('G').numFmt = '#,##0.00';
    worksheet.getColumn('H').numFmt = '#,##0.00';
    worksheet.getColumn('I').numFmt = '#,##0.00';*/

    
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        FileSaver.saveAs(blob, fileName + '.xlsx');
    });
  }

}