/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { NavigationGestioneGaranzieService } from 'src/app/gestione-crediti/services/navigation-gestione-garanzie.service';
import { FiltroGestioneGaranzie } from 'src/app/gestione-garanzie/commons/filtro-gestione-garanzie';
import { GestioneGaranzieService } from '../../services/gestione-garanzie.service';
import { FinanziamentoErogato } from 'src/app/gestione-crediti/commons/dto/finanziamento-erogato';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DettaglioFinanziamentoErogato } from 'src/app/gestione-crediti/commons/dto/dettaglio-finanziamento-erogato';
import { RevAmmDialogComponent } from 'src/app/gestione-crediti/components/dialog-revoca-amministrativa/dialog-rev-amm.component';
import { DialogRevocaBancariaComponent } from 'src/app/gestione-crediti/components/dialog-revoca-bancaria/dialog-revoca-bancaria.component';
import { DialogAzioniRecuperoBanca } from 'src/app/gestione-crediti/components/dialog-azioni-recupero-banca/dialog-azioni-recupero-banca.component';
import { DialogSaldoStralcio } from 'src/app/gestione-crediti/components/dialog-saldo-stralcio/dialog-saldo-stralcio.component';
import { ModGarResponseService } from 'src/app/gestione-crediti/services/modgar-response-service.service';
import { SchedaClienteMain } from 'src/app/gestione-crediti/commons/dto/scheda-cliente-main';
import { NumberFormatPipe } from 'src/app/gestione-crediti/components/number-format/number.pipe';
import { GaranziaVO } from '../../commons/garanzia-vo';
import { DettaglioGaranziaVO } from '../../commons/dettaglio-garanzia-vo';
import { Observable, Subscription, observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, startWith, filter, finalize, map, defaultIfEmpty, isEmpty } from 'rxjs/operators';
import { DialogEstrattoConto } from 'src/app/gestione-crediti/components/dialog-estratto-conto/dialog-estratto-conto.component';
import { DialogPianoAmmortamento } from 'src/app/gestione-crediti/components/dialog-piano-ammortamento/dialog-piano-ammortamento.component';
import { Workbook } from 'exceljs';
import * as FileSaver from 'file-saver'
import { DatiGaranziaVO } from '../../commons/dati-garanzia-vo';
import { DialogDettaglioRicercaGaranzieComponent } from '../dialog-dettaglio-ricerca-garanzie/dialog-dettaglio-ricerca-garanzie.component';

@Component({
  selector: 'app-gestione-garanzie',
  templateUrl: './gestione-garanzie.component.html',
  styleUrls: ['./gestione-garanzie.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
  providers: [GestioneGaranzieService, NavigationGestioneGaranzieService],
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class GestioneGaranzieComponent implements OnInit, OnDestroy {

  // object subscriber
  subscribers: any = {};

  // data objects
  listaGaranzie: Array<DatiGaranziaVO> = new Array<DatiGaranziaVO>();
  dataSource: MatTableDataSource<DatiGaranziaVO> = new MatTableDataSource<DatiGaranziaVO>([]);
  listaStatiEscussione: Array<String>;
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  user: UserInfoSec;
  idProgetto: any;
  idUtente: string;
  spinner: boolean;

  // Per capire quando tutti gli importi di amm.vo hanno finito di caricare, metodo più efficiente
  loadingFromAmmvo: number = 0;
  
  // Error message
  isMessageErrorVisible: boolean;
  messageError: string;

  criteriRicercaState: boolean = true;
    

  displayedColumns: string[] = ['progetto', "ndg", "beneficiario", "banca", "dataRicevimentoEscussione", "tipoEscussione", "statoEscussione", "dataStato", "importoRichiesto", "importoApprovato", 'actions'];
  displayedInnerColumns: string[] = ['tipoAgevolazione', 'importoAmmesso', 'totaleFondo', 'totaleBanca', 'dtConcessione', 'dtErogazioneFinanziamento', 'importoDebitoResiduo', 'importoEscusso', 'statoCredito', 'revocaBancaria', 'azioniRecuperoBanca', 'saldoEStralcio', 'actions'];
  showResults: boolean = false;

  // Autocomplete
  public myForm: FormGroup;
  loadingSuggestions: boolean = false;
  suggBando: Observable<string[]>;
  suggCodProg: Observable<string[]>;
  suggCodFis: Observable<string[]>;
  suggNdg: Observable<string[]>;
  suggIva: Observable<string[]>;
  suggDenom: Observable<string[]>;
  suggBanca: Observable<string[]>;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private garanzieService: GestioneGaranzieService,
    private navigationService: NavigationGestioneGaranzieService,
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
      descrizioneBando: new FormControl(""),
      codiceProgetto: new FormControl(""),
      codiceFiscale: new FormControl(""),
      nag: new FormControl(""),
      partitaIva: new FormControl(""),
      denominazioneCognomeNome: new FormControl(""),
      statoEscussione: new FormControl(""),
      denominazioneBanca: new FormControl("")
    });
    
    
    // Carico gli stati dell'escussione per il dropdown
    this.subscribers.statiEscussione = this.garanzieService.initRicercaGaranzie().subscribe(data => {
      if (data) {
        this.listaStatiEscussione = data;
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });


    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        //console.log(this.navigationService.filtroGestioneGaranzieSelezionato);
        this.user = data;
        this.idUtente = String(data.idUtente);

        //let filtro = this.navigationService.filtroGestioneGaranzieSelezionato;
        /*let filtro = JSON.parse(sessionStorage.getItem('search'));

        if (filtro) {
          this.myForm = this.fb.group({
            descrizioneBando: new FormControl(filtro.descrizioneBando),
            codiceProgetto: new FormControl(filtro.codiceProgetto),
            codiceFiscale: new FormControl(filtro.codiceFiscale),
            nag: new FormControl(filtro.nag),
            partitaIva: new FormControl(filtro.partitaIva),
            denominazioneCognomeNome: new FormControl(filtro.denominazioneCognomeNome),
            statoEscussione: new FormControl(filtro.statoEscussione),
            denominazioneBanca: new FormControl(filtro.denominazioneBanca),
          });
          
          this.initSuggest();

          this.search();
        }*/

        this.ripristinaRicerca();

      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

    this.initSuggest();

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



  initSuggest() {

    // Denominazione bando
    this.suggBando = this.myForm.get('descrizioneBando').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 1).pipe(
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
    this.suggCodProg = this.myForm.get('codiceProgetto').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 2).pipe(
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

    // Codice fiscale
    this.suggCodFis = this.myForm.get('codiceFiscale').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 3).pipe(
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
    this.suggNdg = this.myForm.get('nag').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 4).pipe(
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
    this.suggIva = this.myForm.get('partitaIva').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 5).pipe(
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

    // Denominazione Cognome Nome
    this.suggDenom = this.myForm.get('denominazioneCognomeNome').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 6).pipe(
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

    // Denominazione Banca
    this.suggBanca = this.myForm.get('denominazioneBanca').valueChanges.pipe(
      debounceTime(300),
      switchMap(value => {
        if (value && value.length >= 3) {
          this.loadingSuggestions = true;
          return this.garanzieService.getSuggestion(value, 7).pipe(
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
  }

  search() {

    let forms = this.myForm.getRawValue();

    if ((forms.descrizioneBando.length < 3) &&
      (forms.codiceProgetto.length < 3) &&
      (forms.codiceFiscale.length < 3) &&
      (forms.nag.length < 3) &&
      (forms.partitaIva.length < 3) &&
      (forms.denominazioneCognomeNome.length < 3) &&
      (forms.statoEscussione == "") &&
      (forms.denominazioneBanca.length < 3)) {

      this.showMessageError("Inserire almeno 3 caratteri per avviare una ricerca.");

    } else {

      this.spinner = true;

      this.resetMessageError();

      this.subscribers.cerca = this.garanzieService.cercaGaranzie(
        forms.descrizioneBando,
        forms.codiceProgetto,
        forms.codiceFiscale,
        forms.nag,
        forms.partitaIva,
        forms.denominazioneCognomeNome,
        forms.statoEscussione,
        forms.denominazioneBanca,
      ).subscribe(data => {
 
        if (data) {
          this.listaGaranzie = data;

          /* Imposto il caricamento degli importi che saranno recuperati in un secondo momento
              per motivi di performance */
          this.loadingFromAmmvo = 0;
          this.listaGaranzie.forEach(outer => {
            outer.listaDettagli.forEach(inner => {
              inner.isImportoDebitoLoading = true;
              this.loadingFromAmmvo = this.loadingFromAmmvo + 1;
            })
          })

          this.dataSource = new MatTableDataSource<DatiGaranziaVO>(this.listaGaranzie);

          this.ripristinaPaginator();
          
          /*this.paginator.length = this.listaGaranzie.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;*/

          this.valorizzaDebitoResiduo();

          this.salvaRicerca();
        } 

        this.showResults = true;
        this.criteriRicercaState = false;
        this.spinner = false;

      }, err => {
        this.spinner = false;
        this.showMessageError("Si è verificato un errore in fase di ricerca, riprova fra poco. Se il problema persiste, contatta l'assistenza.")
        this.handleExceptionService.handleBlockingError(err);
      })



    }

  }

  // Metodo che popola la colonna "Importo Debito Residuo" della tabella di dettaglio della garanzia
  valorizzaDebitoResiduo() {

    this.listaGaranzie.forEach((outer, outIndex) => {
      outer.listaDettagli.forEach((dett, inIndex) => {

        this.subscribeAdd(this.garanzieService.getDebitoResiduo(dett.idProgetto, outer.idBando, dett.idModalitaAgevolazione, dett.idModalitaAgevolazioneRif).subscribe(debitoResiduoRes => {
          if (debitoResiduoRes) {
            let debitoResiduo = debitoResiduoRes;

            dett.importoDebitoResiduo = debitoResiduo.debitoResiduo;
          }
          
          dett.isImportoDebitoLoading = false;
          this.loadingFromAmmvo = this.loadingFromAmmvo - 1;

          this.listaGaranzie[outIndex].listaDettagli[inIndex] = {...dett};
          this.dataSource.data = [...this.listaGaranzie];
        }, err => {
          dett.isImportoDebitoLoading = false;
          this.loadingFromAmmvo = this.loadingFromAmmvo - 1;

          this.listaGaranzie[outIndex].listaDettagli[inIndex] = {...dett};
          this.dataSource.data = [...this.listaGaranzie];

          this.handleExceptionService.handleNotBlockingError(err);
        }))
      })
    })
  }


  reset() {
    this.myForm.reset({
      descrizioneBando: '',
      codiceProgetto: '',
      codiceFiscale: '',
      nag: '',
      partitaIva: '',
      denominazioneCognomeNome: '',
      statoEscussione: '',
      denominazioneBanca: ''});

    this.resetMessageError()
    this.showResults = false;

    // Se stavo recuperando ancora gli importi, annullo la chiamata
    for(let i = 0; i < this.subscribersList.length; i++){
      this.subscribersList[i].unsubscribe()
    }

    // Elimino la ricerca salvata in sessione
    sessionStorage.removeItem('searched_garanzie_obj');
  }


  ripristinaPaginator() {
    let filtro = JSON.parse(sessionStorage.getItem('searched_garanzie_obj'));
    let searched_garanzie_paginatorPageIndexTable = JSON.parse(sessionStorage.getItem('searched_garanzie_paginatorPageIndexTable'));
    let searched_garanzie_paginatorPageSizeTable = JSON.parse(sessionStorage.getItem('searched_garanzie_paginatorPageSizeTable'));

    if (filtro) {
      if (searched_garanzie_paginatorPageIndexTable != null && searched_garanzie_paginatorPageIndexTable != undefined) {
        this.paginator.pageIndex = searched_garanzie_paginatorPageIndexTable;
        this.paginator.pageSize = searched_garanzie_paginatorPageSizeTable;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    } else {
      this.paginator.length = 0;
      this.paginator.pageIndex = 0;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }


  salvaRicerca() {
    let formControls = this.myForm.getRawValue();

    let ricerca: FiltroGestioneGaranzie = new FiltroGestioneGaranzie(
      formControls.descrizioneBando || '',
      formControls.codiceProgetto || '',
      formControls.codiceFiscale || '',
      formControls.nag || '',
      formControls.partitaIva || '',
      formControls.denominazioneCognomeNome || '',
      formControls.statoEscussione || '',
      formControls.denominazioneBanca || ''
    );

    //this.navigationService.filtroGestioneGaranzieSelezionato = rich;

    sessionStorage.setItem('searched_garanzie_obj', JSON.stringify(ricerca));

    // salva Paginator
    if (this.dataSource) {
      sessionStorage.setItem('searched_garanzie_paginatorPageIndexTable', this.dataSource.paginator.pageIndex.toString());
      sessionStorage.setItem('searched_garanzie_paginatorPageSizeTable', this.dataSource.paginator.pageSize.toString());
      //this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      //this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }


  ripristinaRicerca() {
    //let filtro = this.navigationService.filtroGestioneGaranzieSelezionato;

    let filtro = JSON.parse(sessionStorage.getItem('searched_garanzie_obj'));

    if (filtro) {
      this.myForm = this.fb.group({
        descrizioneBando: new FormControl(filtro.descrizioneBando),
        codiceProgetto: new FormControl(filtro.codiceProgetto),
        codiceFiscale: new FormControl(filtro.codiceFiscale),
        nag: new FormControl(filtro.nag),
        partitaIva: new FormControl(filtro.partitaIva),
        denominazioneCognomeNome: new FormControl(filtro.denominazioneCognomeNome),
        statoEscussione: new FormControl(filtro.statoEscussione),
        denominazioneBanca: new FormControl(filtro.denominazioneBanca),
      });
      
      this.initSuggest();

      this.search();
    }
  }


  

  goToAttivitaIstruttore(progetto: any, totFin: any, totBan: any) {
    this.salvaRicerca();
    var totEro: number = Number(totFin) + Number(totBan);
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_GARANZIE + "/attivitaIstruttoreGaranzie"],
      { queryParams: { idProgetto: progetto, totFin: 100, totEro: 0 } }
    );
  }

  goToGestioneEscussione(element) {
    this.salvaRicerca();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_GARANZIE + "/modificaGaranzia"],
      { queryParams: {
        idUtente: this.idUtente,
        idProgetto: element.idProgetto,
        idModalitaAgevolazione : element.idModalitaAgevolazioneOrig,
        idEscussione : element.idEscussione,
        componentFrom: 1
      } }
    );
  }

  openDettaglioRevocaBancaria(idProgetto: number) {
    this.dialog.open(DialogDettaglioRicercaGaranzieComponent, {
      //width: '800px',
      minWidth: '10vw',
      minHeight: '10vh',
      data: {idComponent: 1, idProgetto}
    });
  }

  openDettaglioAzioniRecuperoBanca(idProgetto: any) {
    this.dialog.open(DialogDettaglioRicercaGaranzieComponent, {
      //width: '800px',
      minWidth: '10vw',
      minHeight: '10vh',
      data: {idComponent: 2, idProgetto}
    });
  }

  openDettaglioSaldoStralcio(idProgetto: any) {
    this.dialog.open(DialogDettaglioRicercaGaranzieComponent, {
      //width: '800px',
      minWidth: '10vw',
      minHeight: '10vh',
      data: {idComponent: 3, idProgetto}
    });
  }

  openEstrattoConto(row: GaranziaVO, dettRow: DettaglioGaranziaVO) {

    let idModAgPerCont: number;

    // Se si tratta di un bando misto, estraggo il conto del finanziamento, non della garanzia
    if(row.isFondoMisto) {
      idModAgPerCont = 5 // id Finanziamento
    } else {
      idModAgPerCont = row.idModalitaAgevolazione; // dettRow.idTipoAgevolazione A VOLTE E' ZERO!!
    }

    this.dialog.open(DialogEstrattoConto, {
      minWidth: '40vw',
      data: {
        componentFrom: 2,

        // TODO: passare il bando
        codProgetto: row.codiceProgetto,
        beneficiario: row.denominazioneCognomeNome,

        idProgetto: row.idProgetto,
        ndg: row.ndg,
        idModalitaAgevolazione: idModAgPerCont, 
        idModalitaAgevolazioneRif: idModAgPerCont,
       }
    });
  }

  openPianoAmmortamento(row: GaranziaVO, dettRow: DettaglioGaranziaVO) {
    this.dialog.open(DialogPianoAmmortamento, {
      minWidth: '40vw',
      data: {
        codProgetto: row.codiceProgetto,
        idProgetto: row.idProgetto,
        ndg: row.ndg,
        idModalitaAgevolazione: row.idModalitaAgevolazione, 
        idModalitaAgevolazioneRif: row.idModalitaAgevolazioneRif,
        componentFrom: 2, 
        beneficiario: row.denominazioneCognomeNome
       }
    });
  }


  
  download(){
    let fileName: string = 'Estrazione Gestione Garanzie';

    let outerHeader = ['Progetto', 'NDG', 'Beneficiario', 'Banca', 'Data Richiesta Escussione', 'Tipo Escussione', 'Stato Escussione', 'Data Stato', 'Importo Richiesto Totale', 'Importo Approvato Totale'];
    let innerHeader = ['Tipo Agevolazione', 'Importo Ammesso', 'Totale Fondo', 'Totale Banca', 'Data Concessione', 'Data Erogazione', 'Importo Debito Residuo', 'Importo Escusso', 'Stato Credito', 'Revoca Bancaria', 'Azioni Recupero Banca', 'Saldo e Stralcio'];

    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Gestione Garanzie');

    const formValues = this.myForm.getRawValue();

    // Aggiungo righe di dettaglio
    worksheet.addRow(['Descrizione Bando', formValues.descrizioneBando ? formValues.descrizioneBando : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Codice Progetto', formValues.codiceProgetto ? formValues.codiceProgetto : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
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
    worksheet.addRow(['Denominazione / Cognome e Nome', formValues.denominazioneCognomeNome ? formValues.denominazioneCognomeNome : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Stato Escussione', formValues.statoEscussione ? (formValues.statoEscussione == -1 ? 'Domande Concesse' : formValues.statoEscussione) : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Denominazione Banca', formValues.denominazioneBanca ? formValues.denominazioneBanca : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow([]);


    let outerTablePosition: number = 10;  // A10
    let innerTablePosition: number// = 10;  // K10


    this.listaGaranzie.forEach(outerItem => {

      // Aggiungo tabella esterna
      //console.log("EXCEL - disegno tabella esterna in A" + outerTablePosition)
      let outerCols = outerHeader.map(colName => ({ name: colName, filterButton: false }));
      let outerData = [
        [outerItem.codiceProgetto,
        outerItem.ndg,
        outerItem.denomBeneficiario,
        outerItem.denomBanca,
        outerItem.dataRichiestaEscussione,
        outerItem.descTipoEscussione,
        outerItem.descStatoEscussione,
        outerItem.dataStato,
        outerItem.importoRichiesto,
        outerItem.importoApprovato]
      ]
      worksheet.addTable({
        name: ('tabA' + outerTablePosition),
        ref: ('A' + outerTablePosition),
        headerRow: true,
        totalsRow: false,
        style: {
          theme: 'TableStyleLight8',
          showRowStripes: false,
        },
        columns: outerCols,
        rows: outerData,
      });

      
      innerTablePosition = outerTablePosition;
      //console.log("Setto innerTablePosition a outerTablePosition + 2 = " + innerTablePosition)

      
      // Aggiungo tabella interna
      //console.log("EXCEL - disegno tabella interna in K" + innerTablePosition)
      let innerCols = innerHeader.map(colName => ({ name: colName, filterButton: false }));
      let innerData = new Array<any>();
      outerItem.listaDettagli.map(inElem => {
        innerData.push(
          [inElem.descBreveModalitaAgevolazione,
          inElem.importoAmmesso,
          inElem.totaleFondo,
          inElem.totaleBanca,
          inElem.dataConcessione,
          inElem.dataErogazione,
          inElem.importoDebitoResiduo,
          inElem.importoEscusso,
          inElem.statoCredito,
          inElem.revocaBancaria,
          inElem.azioniRecuperoBanca,
          inElem.saldoStralcio]
        );
      })
      worksheet.addTable({
        name: ('tabB' + innerTablePosition),
        ref: ('K' + innerTablePosition),
        headerRow: true,
        totalsRow: false,
        style: {
          theme: 'TableStyleLight8',
          showRowStripes: false,
        },
        columns: innerCols,
        rows: innerData,
      });

      //worksheet.addRow([]);

      outerTablePosition = innerTablePosition + outerItem.listaDettagli.length + 2;
      //console.log("Setto outerTablePosition a innerTablePosition + outerItem.listaDettagli.length + 4 = " + outerTablePosition + " (len = "+outerItem.listaDettagli.length+")");

      //outerItem.listaDettagli.forEach(innerItem => { });

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


  /*toDateFromTimestamp(unixTimestamp: Date): string {
    if (unixTimestamp) {
      const date = new Date(unixTimestamp);

      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;
    } else {
      return null
    }
  }*/

}