/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { GesGarResponseService } from 'src/app/gestione-crediti/services/gesgar-response-service.service';
import { FiltroGestioneGaranzie } from 'src/app/gestione-garanzie/commons/filtro-gestione-garanzie';
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
import { DettaglioGaranziaVO } from '../../commons/dto/dettaglio-garanzia-vo';
import { Observable, of } from 'rxjs';
import { debounceTime, finalize, startWith, switchMap } from 'rxjs/operators';
import { GestioneRiassicurazioniService } from '../../services/gestione-riassicurazioni.service';
import { Riassicurazione_BeneficiarioDomandaVO } from '../../commons/dto/riassicurazione_BeneficiarioDomandaVO';
import { GestioneGaranzieService } from 'src/app/gestione-garanzie/services/gestione-garanzie.service';
import { DialogDettaglioRiassicurazione } from './dialog-dettaglio-riassicurazione/dialog-dettaglio-riassicurazione.component';
import { Riassicurazione_SoggettiCorrelatiVO } from '../../commons/dto/riassicurazione_SoggettiCorrelatiVO';
import { Riassicurazione_ProgettiAssociatiVO } from '../../commons/dto/riassicurazione_ProgettiAssociatiVO';

@Component({
  selector: 'app-ricerca-riassicurazioni',
  templateUrl: './ricerca-riassicurazioni.component.html',
  styleUrls: ['./ricerca-riassicurazioni.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
  //providers: [GestioneGaranzieService, NavigationGestioneGaranzieService],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaRiassicurazioniComponent implements OnInit {

  user: UserInfoSec;
  idUtente: string;
  idProgetto: any;
  subscribers: any = {};
  
  // State controls
  criteriRicercaState: boolean = true;
  isSpinnerSpinning: boolean = false;
  isData: boolean = false;
  showResults: boolean = false;

  // Error
  isMessageErrorVisible: boolean;
  messageError: string;

  // Search
  public myForm: FormGroup;
  suggDesBando: Observable<string[]>;
  suggCodProg: Observable<string[]>;
  suggCodFis: Observable<string[]>;
  suggNdg: Observable<string[]>;
  suggPartIva: Observable<string[]>;
  suggDenom: Observable<string[]>;
  suggBanca: Observable<string[]>;
  loadingSuggestions: boolean = false;
  listaStatiEscussione: Array<String>;

  // Different profiles
  canGoToGestioneEscussione: boolean = false;

  riass: Array<Riassicurazione_BeneficiarioDomandaVO>;
  dataSource: MatTableDataSource<Riassicurazione_BeneficiarioDomandaVO> = new MatTableDataSource<Riassicurazione_BeneficiarioDomandaVO>([]);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  colonneBeneficiariDomande: string[] = ['denominazione', "numeroDomanda", "statoDomanda", "bando", "importoRichiesto", "importoAmmesso", 'firstActions'];
  //colonneProgettiAssociati: string[] = ['agevolazione', 'codiceProgetto', 'totaleAmmesso', 'totaleBanca', 'secondActions'];
  colonneSoggettiCorrelati: string[] = ['denominazioneSogg', 'codiceFiscale', 'statoProgetto', "importoRichiesto", "importoAmmesso", 'dataEscussione', 'dataScarico', 'thirdActions'];
  dettRiassPerDemo: Array<Riassicurazione_SoggettiCorrelatiVO> = new Array<Riassicurazione_SoggettiCorrelatiVO>();


  //spinner: boolean;
  

  http: any;
  loaded: boolean;
  


  //forms = undefined;
  descBandoLength = 0;
  codProgLength = 0;
  codFisLength = 0;
  nagLength = 0;
  partIvaLength = 0;
  denomLength = 0;
  denomBancaLength = 0;
  thereIsEscussion: boolean = false;
  schedaClienteMain: SchedaClienteMain = new SchedaClienteMain;
  schedaClienteMain2: Array<SchedaClienteMain> = [];
  schedaClienteMain3: Array<SchedaClienteMain> = [];
  codBanca: string;
  actualId: string;
  descrizioneStato: string;


  constructor(
    private garanzieService: GestioneGaranzieService,
    private riassicurazioniService: GestioneRiassicurazioniService,
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {

    // Dichiaro i parametri di ricerca
    this.myForm = this.fb.group({
      descrizioneBando: new FormControl(""),
      codiceProgetto: new FormControl(""),
      codiceFiscale: new FormControl(""),
      ndg: new FormControl(""),
      partitaIva: new FormControl(""),
      denominazioneCognomeNome: new FormControl(""),
      statoEscussione: new FormControl(""),
      denominazioneBanca: new FormControl("")
    });

    

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        //console.log(this.navigationService.filtroGestioneGaranzieSelezionato);
        this.user = data;
        this.idUtente = String(data.idUtente);

        // Solo l'Istruttore Area Crediti può accedere alla modifica dell'escussione
        if(data.codiceRuolo == "ISTR-CREDITI") {
          this.canGoToGestioneEscussione = true;
        }

        //let filtro = this.navigationService.filtroGestioneGaranzieSelezionato;
        let filtro = JSON.parse(sessionStorage.getItem('searchedRiass'));

        if (filtro) {
          this.myForm = this.fb.group({
            descrizioneBando: new FormControl(filtro.descrizioneBando == null ? '' : filtro.descrizioneBando),
            codiceProgetto: new FormControl(filtro.codiceProgetto == null ? '' : filtro.codiceProgetto),
            codiceFiscale: new FormControl(filtro.codiceFiscale == null ? '' : filtro.codiceFiscale),
            ndg: new FormControl(filtro.nag == null ? '' : filtro.nag),
            partitaIva: new FormControl(filtro.partitaIva == null ? '' : filtro.partitaIva),
            denominazioneCognomeNome: new FormControl(filtro.denominazioneCognomeNome == null ? '' : filtro.denominazioneCognomeNome),
            statoEscussione: new FormControl(filtro.statoEscussione == null ? '' : filtro.statoEscussione),
            denominazioneBanca: new FormControl(filtro.denominazioneBanca == null ? '' : filtro.denominazioneBanca),
          });

          this.search();
        }

      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
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

    // Descrizione bando
    this.suggDesBando = this.myForm.get('descrizioneBando').valueChanges.pipe(
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
    this.suggNdg = this.myForm.get('ndg').valueChanges.pipe(
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
    this.suggPartIva = this.myForm.get('partitaIva').valueChanges.pipe(
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

    // Stati escussione
    this.subscribers.statiEscussione = this.garanzieService.initRicercaGaranzie().subscribe(data => {
      if (data) {
        this.listaStatiEscussione = data;
      }
    });

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

    //console.log("Hello!", this.myForm)
    var forms = this.myForm.getRawValue();

    if (
      (forms.descrizioneBando.length < 3) &&
      (forms.codiceProgetto.length < 3) &&
      (forms.codiceFiscale.length < 3) &&
      (forms.ndg.length < 3) &&
      (forms.partitaIva.length < 3) &&
      (forms.denominazioneCognomeNome.length < 3) &&
      (forms.statoEscussione == "") &&
      (forms.denominazioneBanca.length < 3)
    ) {
      this.showMessageError("Inserire almeno 3 caratteri per avviare una ricerca.");
    } else {
      this.isSpinnerSpinning = true;
      this.resetMessageError();
      this.subscribers.outerRiass = this.riassicurazioniService.cercaBeneficiariRiassicurazioni(forms.descrizioneBando, forms.codiceProgetto, forms.codiceFiscale, forms.ndg, forms.partitaIva, forms.denominazioneCognomeNome, forms.statoEscussione, forms.denominazioneBanca).subscribe(data => {
 
        if (data.length > 0) {

          this.isData = true;

          this.riass = data;

          // Solo per demo //
          if (this.riass[0].soggettiCorrelati && this.riass[0].soggettiCorrelati.length >= 2) {
            // Rimuoviamo i primi 4 elementi dall'array
            this.riass[0].soggettiCorrelati.splice(1, this.riass[0].soggettiCorrelati.length);
          
          }
          // --- //

          //this.dataSource = new MatTableDataSource<RiassicurazioneVO>(this.riass);
          //this.paginator.length = this.riass.length;
          //this.paginator.pageIndex = 0;
          //this.dataSource.paginator = this.paginator;
          //this.dataSource.sort = this.sort;

        } else {
          this.riass = data;
          //this.dataSource = new MatTableDataSource<RiassicurazioneVO>(this.riass);
          //this.paginator.length = 0;
          //this.paginator.pageIndex = 0;
          //this.dataSource.paginator = this.paginator;
          //this.dataSource.sort = this.sort;

        }

        this.showResults = true;
        this.criteriRicercaState = false;
        this.isSpinnerSpinning = false;
      }, err => {
        //console.log("Error: ", err);
        this.isSpinnerSpinning = false;
        this.showMessageError("Si è verificato un errore durante la ricerca, riprova con altri parametri.\n Se il problema persiste contatta l'assistenza.");
        this.handleExceptionService.handleNotBlockingError(err);
      })



    }

  }

  /* Potevo chiamare la funzione "myForm.reset()" direttamente nel click del pulsante
    ma per come è strutturato ora il codice, causa errori */
  resetCriteriRicerca() {
    this.resetMessageError();

    this.myForm.reset({
      descrizioneBando: '',
      codiceProgetto: '',
      codiceFiscale: '',
      ndg: '',
      partitaIva: '',
      denominazioneCognomeNome: '',
      statoEscussione: '',
      denominazioneBanca: ''
    });
  }

  // TODO: Da controllare ed eventualmente eliminare
  expandOcchietto(expandedElement: any, element: any, idProgetto: any) {
    this.idProgetto = idProgetto;
    return (expandedElement === element ? null : element);
  }


  openDettaglioRiassicurazione(innerRow: Riassicurazione_SoggettiCorrelatiVO, outerRow: Riassicurazione_BeneficiarioDomandaVO): void {
    //console.log("rowdata2", data2)
    let dialogRef = this.dialog.open(DialogDettaglioRiassicurazione, {
      minWidth: '50%',
      data: {
        idProgetto: innerRow.idProgetto,
        header: innerRow.nome1 + ' ' + innerRow.nome2
      }
    });

    /*dialogRef.afterClosed().subscribe(result => {
      console.log("Dialog res:", result)

      if (result != undefined && result != "" && result.save == "save") {
      
      }
    });*/
  }



  goToGestioneEscussione(element: Riassicurazione_BeneficiarioDomandaVO) {
    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_RIASSICURAZIONI + "/gestioneEscussioneRiassicurazioni"],
      { queryParams: {
        idProgetto: element.idProgetto,
        idEscussione : element.idUltimaEscussione,
      } }
    );
  }

  /*ripristinaPaginator() {

    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }

  }*/

  saveDataForNavigation() {
    let formControls = this.myForm.getRawValue();

    let rich: FiltroGestioneGaranzie = new FiltroGestioneGaranzie(
      formControls.descrizioneBando,
      //formControls.codiceProgetto,
      formControls.codiceFiscale,
      formControls.nag,
      formControls.partitaIva,
      formControls.denominazioneCognomeNome,
      formControls.statoEscussione,
      formControls.denominazioneBanca
    );

    //this.navigationService.filtroGestioneGaranzieSelezionato = rich;

    sessionStorage.setItem('searchedRiass', JSON.stringify(rich));

    //this.salvaPaginator();

  }

  /*ripristinaRicercaPerNavigazione() {
    //let filtro = this.navigationService.filtroGestioneGaranzieSelezionato;
    let filtro = JSON.parse(sessionStorage.getItem('searchedRiass'));

    if (filtro) {
      this.myForm = this.fb.group({
        descrizioneBando: new FormControl(filtro.descrizioneBando),
        //codiceProgetto: new FormControl(filtro.codiceProgetto),
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
  }*/

  /*salvaPaginator() {

    if (this.dataSource) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }

    console.log(this.navigationService.filtroGestioneGaranzieSelezionato);

  }*/



}