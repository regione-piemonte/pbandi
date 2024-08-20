/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AnagAltriDati_Main } from 'src/app/gestione-anagrafica/commons/dto/anagAltriDati_Main';
import { AnagraficaBeneficiarioSec } from 'src/app/gestione-anagrafica/commons/dto/anagrafica-beneficiario';
import { AtecoDTO } from 'src/app/gestione-anagrafica/commons/dto/atecoDTO';
import { AtlanteVO } from 'src/app/gestione-anagrafica/commons/dto/atlante-vo';
import { BeneficiarioSoggCorrEG } from 'src/app/gestione-anagrafica/commons/dto/beneficiario-sogg-corr-EG';
import { BloccoVO } from 'src/app/gestione-anagrafica/commons/dto/bloccoVO';
import { DatiAreaCreditiSoggetto } from 'src/app/gestione-anagrafica/commons/dto/datiSoggettoAreaCreditiVO';
import { ElencoDomandeProgettiSec } from 'src/app/gestione-anagrafica/commons/dto/elenco-domande-progetti';
import { ElencoFormaGiuridica } from 'src/app/gestione-anagrafica/commons/dto/elencoFormaGiuridica';
import { Nazioni } from 'src/app/gestione-anagrafica/commons/dto/nazioni';
import { Regioni } from 'src/app/gestione-anagrafica/commons/dto/regioni';
import { RuoloDTO } from 'src/app/gestione-anagrafica/commons/dto/ruoloDTO';
import { SezioneSpecialeDTO } from 'src/app/gestione-anagrafica/commons/dto/sezioneSpecialeDTO';
import { AnagraficaBeneficiarioService } from 'src/app/gestione-anagrafica/services/anagrafica-beneficiario.service';
import { DatiDomandaService } from 'src/app/gestione-anagrafica/services/dati-domanda-service';
import { ModificaAnagraficaService } from 'src/app/gestione-anagrafica/services/modifica-anagrafica.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { EditBloccoComponent } from '../../edit-blocco/edit-blocco.component';
import { StoricoBlocchiAttiviComponent } from '../../storico-blocchi-attivi/storico-blocchi-attivi.component';
import { map, startWith } from 'rxjs/operators';
import { Province } from 'src/app/gestione-anagrafica/commons/dto/provincie';
import { DettaglioDatiDimensioneComponent } from '../../dettaglio-dati-dimensione/dettaglio-dati-dimensione.component';
import { DettaglioImpresa } from 'src/app/gestione-anagrafica/commons/dettaglioImpresa';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { ArchivioFileService } from '@pbandi/common-lib/';
import { ConfigService } from 'src/app/core/services/config.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-persona-giuridica-container',
  templateUrl: './persona-giuridica-container.component.html',
  styleUrls: ['./persona-giuridica-container.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class PersonaGiuridicaContainerComponent implements OnInit, AfterViewChecked {
  today = new Date(); 
  displayedColumnsEg: string[] = ['codiceBando', 'idDomanda', 'descStatoDomanda', 'descProgetto', 'descStatoProgetto', 'legaleRappresentante', 'azioni'];
  columnsAltriDatiDimImpr: string[] = ['dataValutazione', 'esito', 'azioni'];
  columnsAltriDatiDurc: string[] = ['dataEmissione', 'esito', 'dataScadenza', 'numProto', 'nomeDocumento', 'action'];
  columnsAltriDatiAntiMafia: string[] = ['dataEmissione', 'esito', 'dataScadenza', 'numProto', 'numeroDomanda'];
  columnsAltriDatiBdna: string[] = ['dataRicezione', 'numProto', 'numeroDomanda'];
  displayedColumnsBlocchi: string[] = ['dataBlocco', 'causale', 'azioni'];
  displayedInnerColumns: string[] = ['annoRiferimento', 'unitaLavorativeAnnue', 'fatturato', 'bilancio'];
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  
  public myForm: FormGroup;

  dataSource: MatTableDataSource<ElencoDomandeProgettiSec> = new MatTableDataSource<ElencoDomandeProgettiSec>([]);
  dataSourceAltriDati_DimImpr: Array<AnagAltriDati_Main> = [];
  dataSourceAltriDati_Durc: any;
  dataSourceAltriDati_Bdna: any;
  dataSourceAltriDati_AntiMafia: any;
  listaBlocchi: Array<BloccoVO> = new Array<BloccoVO>();
  altriDati: AnagAltriDati_Main;
  messageError: string;
  isMessageErrorVisible: boolean;
  beneficiario: AnagraficaBeneficiarioSec;

  isSaveBlocco: boolean;
  isBlocchi: boolean;
  idSoggetto: any;
  isEnteGiuridico: boolean;
  idEnteGiuridico: any;
  verificaTipoSoggetto: any;
  numeroDomanda: any;

  spinner: boolean;
  getConcluse: number = 0;
  idProgetto: any;
  modificaControl: boolean = false;
  errorModifica: boolean = false;
  editForm: boolean = false;
  formSoggetto!: FormGroup;


  colonna: string = "NUMERO_DOMANDA";
  ordinamentoString: string = "DESC";
  ordinamento: boolean = false;

  elenco: ElencoDomandeProgettiSec[] = [];
  datiAreaCreditiSoggetto: DatiAreaCreditiSoggetto = new DatiAreaCreditiSoggetto();

  @ViewChild('tabs', { static: false }) tabs;

  public subscribers: any = {};
  ndg: any;
  nazioneData: Nazioni[] = [];
  filteredOptionsNazioni: Observable<Nazioni[]>;
  nazioneSelected: FormControl = new FormControl();

  regioneData: Regioni[] = [];
  filteredOptionsRegioni: Observable<Regioni[]>;
  regioneSelected: FormControl = new FormControl();

  provinciaData: AtlanteVO[] = [];
  filteredOptionsProvince: Observable<AtlanteVO[]>;
  provinciaSelected: FormControl = new FormControl();

  comuneData: AtlanteVO[] = [];
  filteredOptionsComuni: Observable<AtlanteVO[]>;
  comuneSelected: FormControl = new FormControl();

  codiceAtecoData: Array<AtecoDTO>;
  descrizioneAttivitaData: any[] = [];

  provinciaIscrizioneData: AtlanteVO[] = [];

  user: UserInfoSec;
  setBackText: string = "Torna all'elenco soggetti correlati";

  tipologiaSoggettoCorrelato!: string;
  nagSoggettoCorrelato!: string;

  //soggObj: BeneficiarioSoggCorrEG = new BeneficiarioSoggCorrEG();
  //soggObj:  


  formaGiuridicaData: ElencoFormaGiuridica[] = [];
  ruoloData: RuoloDTO[] = [];

  isMessageError: boolean = false;
  isPecValid: boolean = false;
  isPartitaIvaValid: boolean = false;
  sezioneAppartenanzaData: SezioneSpecialeDTO[] = [];



  // da togliere
  datiFormNonValidi: boolean = false;

  ragioneSocialeRichiesto!: boolean;


  maxData = new Date();

  //LOADED
  loadedSoggetto: boolean = true;
  loadedFormeGiuridiche: boolean = true;
  loadedRuoli: boolean = true;
  loadedNazioni: boolean = true;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;

  codiceAteco: string;
  elencoAteco: Array<AtecoDTO>;
  codiceAtecoIns: any;
  atecoSelected: FormControl = new FormControl();
  statoAttivitaData: AttivitaDTO[];
  loadedSave: boolean = true;
  isSaved: boolean;
  userloaded: boolean;
  idUtente: number;
  soggObj: BeneficiarioSoggCorrEG = new BeneficiarioSoggCorrEG();
  soggetti: BeneficiarioSoggCorrEG[];
  idDomanda: any;
  veroIdDomanda: any;
  isDatiImpresa: boolean;
  isDurcTable: boolean;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute, private fb: FormBuilder,
    private sharedService: SharedService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private anagBeneService: AnagraficaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,
    private datiDomandaService: DatiDomandaService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    public dialog: MatDialog, private router: Router) { }

  @ViewChild("elencoDomandeProgetti") paginator: MatPaginator;
  @ViewChild("elencoDomandeProgettiSort") sort: MatSort;
  ngOnInit(): void {
    this.spinner = true;
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    //this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idTipoSoggetto');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    this.ndg = this.route.snapshot.queryParamMap.get("ndg");
    this.idProgetto = this.route.snapshot.queryParamMap.get("idProgetto");
    this.idDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    this.veroIdDomanda = this.route.snapshot.queryParamMap.get("numeroDomanda");

    this.anagBeneService.getElencoDomandeProgetti(this.idSoggetto, true).subscribe(data => {
      if (data) {
        this.elenco = data;
        this.dataSource = new MatTableDataSource<ElencoDomandeProgettiSec>(this.elenco);
        this.paginator.length = data.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.dataSource.sortingDataAccessor = (item, property) => {
          switch (property) {
            case 'idDomanda':
              return item.numeroDomanda;
            default: return item[property];
          }
        };
        this.bloccaSpinner();
      }
    });

    this.getElencoBlocchi();
    this.getDatiAreaCrediti();

    this.formSoggetto = this.fb.group({
      'dati-anagrafici': this.fb.group({
        ragioneSociale: [{ value: '', disabled: true }],
        formaGiuridica: [{ value: '', disabled: true }],
        pubblicoPrivatoRadio: [{ value: '', disabled: true }],
        codiceUniIpa: [{ value: '', disabled: true }],
        ruolo: [{ value: '', disabled: true }],
        codiceFiscale: [{ value: '', disabled: true }],
        dtCostituzione: [{ value: '', disabled: true }],
        pec: [{ value: '', disabled: true }]
      }),
      'sede-legale': this.fb.group({
        indirizzo: [{ value: '', disabled: true }],
        partitaIva: [{ value: '', disabled: true }],
        comune: [{ value: '', disabled: true }],
        provincia: [{ value: '', disabled: true }],
        cap: [{ value: '', disabled: true }],
        regione: [{ value: '', disabled: true }],
        nazione: [{ value: '', disabled: true }]
      }),
      'attivita-economica': this.fb.group({
        codiceAteco: [{ value: '', disabled: true }],
        descrizioneAttivita: [{ value: '', disabled: true }],
        ratingLegalita: [{ value: '', disabled: true }],
        statoAttivita: [{ value: '', disabled: true }],
        dtInizioAttivita: [{ value: '', disabled: true }],
        periodoChiusuraEsercizio: [{ value: '', disabled: true }],
        dtChiusuraEsercizio: [{ value: '', disabled: true }]
      }),
      'dati-iscrizione': this.fb.group({
        numeroRea: [{ value: '', disabled: true }],
        dtIscrizione: [{ value: '', disabled: true }],
        provinciaIscrizione: [{ value: '', disabled: true }]
      }),
      'sezione-appartenenza': this.fb.group({
        sezioneAppartenenza: [{ value: '', disabled: true }]
      })
    });

    this.userService.userInfo$.subscribe(user => {
      if (user) {
        this.user = user;
        this.idUtente = this.user.idUtente;
        this.loadSoggetto();
        this.userloaded = true;
      }
    });

  }

  loadSoggetto() {
    this.loadedSoggetto = false;

    this.subscribers.anagBeneficiarioInfo$ = this.anagBeneService.getAnagBeneficiario(this.idSoggetto, this.idProgetto, this.veroIdDomanda).subscribe(data => {
      if (data) {
        this.soggetti = data;
        if (this.soggetti.length > 0) {
          this.soggObj = this.soggetti[0];
          this.idEnteGiuridico= this.soggObj.idEnteGiuridico;
          this.ndg = this.soggObj.ndg;
          this.setValueForm();
          this.anagBeneService.getAltriDati(this.idSoggetto, this.numeroDomanda, this.soggObj.idEnteGiuridico).subscribe(data => {
            if (data) {
              console.log(data);
              this.altriDati = data;
              this.dataSourceAltriDati_DimImpr = this.altriDati.dimImpresa;
              this.dataSourceAltriDati_Durc = this.altriDati.durc;
              this.dataSourceAltriDati_Bdna = this.altriDati.bdna;
              this.dataSourceAltriDati_AntiMafia = this.altriDati.antiMafia;

              if (this.altriDati.dimImpresa[0] && this.altriDati.dimImpresa[0].dimImpr_dataValutazione != null) {
                this.isDatiImpresa = true;
              }
              if(this.dataSourceAltriDati_Durc.length>0){
                this.isDurcTable= true;
              }
              console.log(this.altriDati.dimImpresa.length);

            }
          })
        }
      }
      this.loadData();
      this.loadedSoggetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del dettaglio del soggetto.");
      this.loadedSoggetto = true;
    })
  }
  getElencoCodiceAteco(atecoCod: string, init?: boolean) {


    if (atecoCod)
      this.subscribers.getElencoAteco = this.anagBeneService.getElencoAteco(atecoCod).subscribe(data => {
        if (data) {

          this.codiceAtecoData = data;
          if (init) {
            this.atecoSelected.setValue(this.codiceAtecoData.find(f => f.codAteco == atecoCod));
            this.formSoggetto.get("attivita-economica.codiceAteco").setValue(this.codiceAtecoData.find(f => f.codAteco == atecoCod));
          }
        } else {
          this.elencoAteco = [];
        }
      });
  }
  displayFnAteco(ateco: AtecoDTO) {
    return ateco && ateco.codAteco ? ateco.codAteco : '';
  }

  loadData() {
    this.loadedFormeGiuridiche = false;
    this.subscribers.getElencoFormaGiuridica = this.modificaAnagraficaService.getElencoFormaGiuridica().subscribe(data => {
      if (data) {
        this.formaGiuridicaData = data;
        if (this.soggObj && this.soggObj.idFormaGiuridica && this.formSoggetto.get("dati-anagrafici")) {
          this.formSoggetto.get("dati-anagrafici").get("formaGiuridica").setValue(this.formaGiuridicaData.find(f => f.idFormaGiuridica === this.soggObj.idFormaGiuridica));
        }
      }
      this.loadedFormeGiuridiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle forme giuridiche.");
      this.loadedFormeGiuridiche = true;
    });
    this.loadedRuoli = false;
    this.subscribers.getRuoli = this.modificaAnagraficaService.getElencoRuolo().subscribe(data => {
      if (data) {
        this.ruoloData = data;
        if (this.soggObj && this.soggObj.idTipoSoggCorr) {
          this.formSoggetto.get("dati-anagrafici").get("ruolo").setValue(this.ruoloData.find(f => f.idTipoSoggCorr.toString() === this.soggObj.idTipoSoggCorr.toString()));
        }
      }
      this.loadedRuoli = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei ruoli.");
      this.loadedRuoli = true;
    });

    this.subscribers.getStatoAttivita = this.anagBeneService.getListaAttivita().subscribe(data => {
      if (data) {
        this.statoAttivitaData = data;
        if (this.soggObj && this.soggObj.idAttAteco)
          this.formSoggetto.get("attivita-economica").get("statoAttivita")
            .setValue(this.statoAttivitaData.find(f => f.idAttivita.toString() === this.soggObj.idStatoAtt.toString()));
      }
    });

    this.subscribers.getProvinceIscrizioni = this.datiDomandaService.getProvince().subscribe(data => {
      if (data) {
        this.provinciaIscrizioneData = data;
        if (this.soggObj && this.soggObj.idProvinciaIscrizione) {
          this.formSoggetto.get("dati-iscrizione.provinciaIscrizione").setValue(this.provinciaIscrizioneData.find(f => f.idProvincia.toString() === this.soggObj.idProvinciaIscrizione.toString()));
        }

      }
    });
    this.subscribers.getElencoSezioneSpeciale = this.anagBeneService.getElencoSezioneSpeciale().subscribe(data => {
      if (data) {
        this.sezioneAppartenanzaData = data;
        if (this.soggObj && this.soggObj.idSezioneSpeciale) {
          this.formSoggetto.get("sezione-appartenenza.sezioneAppartenenza").setValue(
            this.sezioneAppartenanzaData.find(s => s.idSezioneSpeciale === this.soggObj.idSezioneSpeciale));
        }
      }
    })

    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.formSoggetto.get('sede-legale.nazione')?.valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.soggObj && this.soggObj.idNazione >0  && this.formSoggetto.get("sede-legale")) {
          this.formSoggetto.get("sede-legale.nazione").setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazione.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazione.toString()));
          if (this.soggObj.idNazione == 118)
            this.loadRegioni(true);
          else {
            this.loadComuniEsteri(true);
          }
        }
      }
      this.loadedNazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioni = true;
    });
    console.log(this.soggObj.codAteco);

    this.getElencoCodiceAteco(this.soggObj.codAteco, true);

  }

  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.formSoggetto.get('sede-legale.regione')?.valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name) : this.regioneData.slice())
          );
        if (this.soggObj && this.soggObj.idRegione && init) {
          this.formSoggetto.get("sede-legale.regione").setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegione.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegione.toString()));
          console.log(this.regioneSelected);


          if (this.regioneSelected?.value != null)
            this.loadProvince(init);
        }
      }
      this.loadedRegioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      this.loadedRegioni = true;
    });
  }

  loadProvince(init?: boolean) {
    this.loadedProvince = false;
    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSelected?.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.formSoggetto.get('sede-legale.provincia')?.valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name) : this.provinciaData.slice())
          );
        if (this.soggObj && this.soggObj.idProvincia && init) {
          this.formSoggetto.get("sede-legale.provincia").setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvincia.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvincia.toString()));

          if (this.provinciaSelected?.value != null)
            this.loadComuniItalia(init);
        }
      }
      this.loadedProvince = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province.");
      this.loadedProvince = true;
    });
  }

  loadComuniItalia(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaSelected?.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggetto.get('sede-legale.comune')?.valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComune && this.formSoggetto.get("sede-legale") && init) {
          this.formSoggetto.get("sede-legale.comune").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));

          // if (this.comuneSelected?.value != null)
          //   this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  loadComuniEsteri(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSelected?.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggetto.get('sede-legale.comune')?.valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComune && this.formSoggetto.get("sede-legale") && init) {
          this.formSoggetto.get("sede-legale.comune").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
          //this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  displayFnNazioni(nazione: Nazioni): string {
    return nazione && nazione.descNazione ? nazione.descNazione : '';
  }

  displayFnRegioni(regione: Regioni): string {
    return regione && regione.descRegione ? regione.descRegione : '';

  }

  displayFnProvince(prov: AtlanteVO): string {
    return prov && prov.siglaProvincia ? prov.siglaProvincia : '';
  }
  displayFnProvinceIscrizione(prov: Province): string {
    return prov && prov.descProvincia ? prov.descProvincia : '';
  }

  displayFnComuni(com: AtlanteVO): string {
    return com && com.descComune ? com.descComune : '';
  }

  private _filterNazioni(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }

  private _filterRegioni(descrizione: string): Regioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.regioneData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
  }

  private _filterProvince(descrizione: string): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.provinciaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));
  }

  private _filterComuni(descrizione: string): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.comuneData.filter(option => option.descComune.toLowerCase().includes(filterValue));
  }

  check(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneSelected || (this.formSoggetto.get('sede-legale.nazione') && this.nazioneSelected?.value !== this.formSoggetto.get('sede-legale.nazione')?.value)) {
            this.formSoggetto.get('sede-legale.nazione').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.formSoggetto.get('sede-legale.regione') && this.regioneSelected?.value !== this.formSoggetto.get('sede-legale.regione')?.value)) {
            this.formSoggetto.get('sede-legale.regione').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.formSoggetto.get('sede-legale.provincia') && this.provinciaSelected?.value !== this.formSoggetto.get('sede-legale.provincia')?.value)) {
            this.formSoggetto.get('sede-legale.provincia').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.formSoggetto.get('sede-legale.comune') && this.comuneSelected?.value !== this.formSoggetto.get('sede-legale.comune')?.value)) {
            this.formSoggetto.get('sede-legale.comune').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        case "A":
          if (!this.atecoSelected || (this.formSoggetto.get('attivita-economica.codiceAteco') && this.atecoSelected?.value !== this.formSoggetto.get('attivita-economica.codiceAteco')?.value)) {
            this.formSoggetto.get('attivita-economica.codiceAteco').setValue(null);
            this.atecoSelected = new FormControl();
            this.resetAttivitaPrevalente();
          }
          break;
        default:
          break;
      }
    }, 200);
  }
  resetAttivitaPrevalente() {
    this.formSoggetto.get("attivita-economica.descrizioneAttivita").setValue(null)
  }
  setAttivitaPrevalente() {
    this.formSoggetto.get("attivita-economica.descrizioneAttivita").setValue(this.atecoSelected?.value.descAttivitaAteco)
  }

  click(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option?.value !== this.nazioneSelected?.value) {
          this.nazioneSelected.setValue(event.option?.value);
          this.resetRegione();
          if (this.nazioneSelected?.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioni();
          } else {
            this.loadComuniEsteri();
          }
        }
        break;
      case "R":
        if (event.option?.value !== this.regioneSelected?.value) {
          this.regioneSelected.setValue(event.option?.value);
          this.resetProvincia();
          this.loadProvince();
        }
        break;
      case "P":
        if (event.option?.value !== this.provinciaSelected?.value) {
          this.provinciaSelected.setValue(event.option?.value);
          this.resetComune();
          this.loadComuniItalia();
        }
        break;
      case "C":
        if (event.option?.value !== this.comuneSelected?.value) {
          this.comuneSelected.setValue(event.option?.value);
          this.setCap();
        }
        break;
      case "A":
        if (event.option?.value !== this.atecoSelected?.value) {
          this.atecoSelected.setValue(event.option?.value);
          this.setAttivitaPrevalente();
          console.log(this.atecoSelected?.value.idAttAteco);
        }
        break;
      default:
        break;
    }
  }

  setCap() {
    //this.formSoggetto.get('sede-legale.cap').setValue(this.comuneSelected?.value.cap);
    // if (this.comuneSelected?.value.cap) {
    //   this.formSoggetto.get("sede-legale.cap").disable();
    // } else {
    // }
    this.formSoggetto.get("sede-legale.cap").enable();
  }

  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.formSoggetto.get('sede-legale.regione').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggetto.get('sede-legale.regione').enable();
    } else {
      this.formSoggetto.get('sede-legale.regione').disable();
    }
    this.resetProvincia();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.formSoggetto.get('sede-legale.provincia').setValue(null);
    if (this.regioneSelected?.value) {
      this.formSoggetto.get('sede-legale.provincia').enable();
    } else {
      this.formSoggetto.get('sede-legale.provincia').disable();
    }
    this.resetComune();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.formSoggetto.get('sede-legale.comune').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected?.value.idNazione?.toString() !== "118")) {
      this.formSoggetto.get('sede-legale.comune').enable();
    } else {
      this.formSoggetto.get('sede-legale.comune').disable();
    }
    this.resetCap();
  }

  resetCap() {
    this.formSoggetto.get('sede-legale.cap').setValue(null);
    //this.formSoggetto.get('sede-legale.cap').disable();
  }

  private setValueForm(): void {

    let valRating;
    if (this.soggObj.flagRatingLeg) {
      valRating = 'S'
    } else {
      valRating = 'N'
    }
    this.formSoggetto = this.fb.group({
      'dati-anagrafici': this.fb.group({
        ragioneSociale: [{ value: this.soggObj.ragSoc, disabled: true }],
        formaGiuridica: [{ value: null, disabled: true }],
        pubblicoPrivatoRadio: [{ value: this.soggObj.flagPubblicoPrivato, disabled: true }],
        codiceUniIpa: [{ value: this.soggObj.codUniIpa, disabled: true }],
        ruolo: [{ value: this.soggObj.idTipoSoggCorr, disabled: true }],
        codiceFiscale: [{ value: this.soggObj.cfSoggetto, disabled: true }],
        dtCostituzione: [{ value: this.soggObj.dtCostituzione, disabled: true }],
        pec: [{ value: this.soggObj.descPec, disabled: true }]
      }),
      'sede-legale': this.fb.group({
        indirizzo: [{ value: this.soggObj.descIndirizzo, disabled: true }],
        partitaIva: [{ value: this.soggObj.pIva, disabled: true }],
        comune: [{ value: this.soggObj.descComune, disabled: true }],
        provincia: [{ value: this.soggObj.descProvincia, disabled: true }],
        cap: [{ value: this.soggObj.cap, disabled: true }],
        regione: [{ value: this.soggObj.descRegione, disabled: true }],
        nazione: [{ value: this.soggObj.descNazione, disabled: true }]
      }),
      'attivita-economica': this.fb.group({
        codiceAteco: [{ value: null, disabled: true }],
        descrizioneAttivita: [{ value: this.soggObj.descAteco, disabled: true }],
        ratingLegalita: [{ value: valRating, disabled: true }],
        statoAttivita: [{ value: this.soggObj.descStatoAttivita, disabled: true }],
        dtInizioAttivita: [{ value: this.soggObj.dtInizioAttEsito, disabled: true }],
        periodoChiusuraEsercizio: [{ value: this.soggObj.periodoScadEse, disabled: true }],
        dtChiusuraEsercizio: [{ value: this.soggObj.dtUltimoEseChiuso, disabled: true }]
      }),
      'dati-iscrizione': this.fb.group({
        numeroRea: [{ value: this.soggObj.numIscrizione, disabled: true }],
        dtIscrizione: [{ value: this.soggObj.dtIscrizione, disabled: true }],
        provinciaIscrizione: [{ value: this.soggObj.descProvinciaIscriz, disabled: true }]
      }),
      'sezione-appartenenza': this.fb.group({
        sezioneAppartenenza: [{ value: null, disabled: true }]
      })
    });
    //this.formSoggetto.get("dati-anagrafici.ragioneSociale").setValue(this.soggObj[0].ragSoc);
    console.log("siamo dentro al form");
    console.log(this.soggObj);
    console.log(this.formSoggetto);

  }

  get ragioneSociale(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.ragioneSociale") as FormControl;
  }

  get pubblicoPrivatoRadio(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.pubblicoPrivatoRadio") as FormControl;
  }

  get codiceUniIpa(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.codiceUniIpa") as FormControl;
  }

  get dtCostituzione(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.dtCostituzione") as FormControl;
  }

  get pec(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.pec") as FormControl;
  }

  get indirizzo(): FormControl {
    return this.formSoggetto.get("sede-legale.indirizzo") as FormControl;
  }

  get partitaIva(): FormControl {
    return this.formSoggetto.get("sede-legale.partitaIva") as FormControl;
  }


  editSoggetto(): void {
    this.editForm = true;
    this.isSaved = false;
    console.log(this.idProgetto);

    if (this.idProgetto != null) {
      this.formSoggetto.enable();
      this.formSoggetto.get("dati-anagrafici.codiceFiscale").disable();
      this.formSoggetto.get("sede-legale.partitaIva").disable();
      this.formSoggetto.get("attivita-economica.descrizioneAttivita").disable();

      if (!this.nazioneSelected?.value || this.nazioneSelected?.value.idNazione.toString() !== "118") {
        this.formSoggetto.get("sede-legale.regione").disable();
        this.formSoggetto.get("sede-legale.provincia").disable();
      }
    } else {
      this.formSoggetto.enable();
      this.formSoggetto.get("dati-anagrafici.codiceFiscale").disable();
      this.formSoggetto.get("sede-legale.partitaIva").disable();
      this.formSoggetto.get("attivita-economica.descrizioneAttivita").disable();

      if (!this.nazioneSelected?.value || this.nazioneSelected?.value.idNazione.toString() !== "118") {
        this.formSoggetto.get("sede-legale.regione").disable();
        this.formSoggetto.get("sede-legale.provincia").disable();
      }
    }

  }

  isNotEditSoggetto(): void {
    this.modificaControl = false;
    this.errorModifica = false;
    this.editForm = false;

    this.formSoggetto.disable();
    setTimeout(() => {
      this.ngOnInit()
    }, 3000);

  }

  validate() {
    this.resetMessageError();
    let hasError: boolean;

    if (this.sharedService.checkFormFieldRequired(this.formSoggetto, 'dati-anagrafici.ragioneSociale')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldMinlength(this.formSoggetto, 'dati-anagrafici.ragioneSociale', 2)) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldRequired(this.formSoggetto, 'dati-anagrafici.pubblicoPrivatoRadio')) {
      hasError = true;
    }
    if (this.formSoggetto.get('dati-anagrafici.pubblicoPrivatoRadio')?.value === 2
      && this.sharedService.checkFormFieldRequired(this.formSoggetto, 'dati-anagrafici.codiceUniIpa')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldRequired(this.formSoggetto, 'dati-anagrafici.ruolo')) {
      hasError = true;
    }
    if (this.sharedService.checkDateAfterToday(this.formSoggetto.get('dati-anagrafici.dtCostituzione'))) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldEmailPattern(this.formSoggetto, 'dati-anagrafici.pec')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldMinlength(this.formSoggetto, 'sede-legale.indirizzo', 4)) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldMinlength(this.formSoggetto, 'sede-legale.partitaIva', 11)) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldNumberPattern(this.formSoggetto, 'sede-legale.partitaIva')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldNumberPattern(this.formSoggetto, 'sede-legale.cap')) {
      hasError = true;
    }
    if (hasError) {
      this.showMessageError("È necessario indicare i dati non obbligatori.");
    }
    return hasError;
  }

  onSubmit(): void {
    this.validate();
    // this.isMessageError = false;

    // if (this.formSoggetto.invalid) {
    //   this.isMessageError = true;
    //   return;
    // }

    // console.log('@@@@@formValue');
    // console.log(this.formSoggetto?.value);

  }


  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }

  goToStorico() {
    this.router.navigate(["/drawer/" + this.idOp + "/storicoBeneficiario"], {
      queryParams:
      {
        denominazione: this.soggObj.ragSoc,
        idSoggetto: this.idSoggetto, isEnteGiuridico: this.verificaTipoSoggetto,
        idEnteGiuridico: this.idEnteGiuridico,
        idDomanda: this.veroIdDomanda,
        idProgetto: this.idProgetto
      }
    });
  }

  visualizzaDatiDomanda(row) {

    this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaEG"], {
      queryParams:
      {
        idSoggetto: this.idSoggetto,
        ndg: this.ndg, // server per il goback con nome ndg
        numeroDomanda: row.numeroDomanda,
        idEnteGiuridico: this.idEnteGiuridico,
        isEnteGiuridico: this.verificaTipoSoggetto, // serve per il goback con il nome tipoSoggetto
        idDomanda: this.numeroDomanda, // mi serve per il goback con nome idDomanda
        idProgetto: this.idProgetto,  // mi serve per il goback con nome idProgetto
        veroIdDomanda: this.veroIdDomanda,// mi serve peril goback con nome numeroDomanda
        denominazione: this.soggObj.ragSoc, 
        partitaIva: this.soggObj.pIva

      }
    });
  }

  modificaDatiDomandaPF(row) {
    this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaEG"], { queryParams: { idSoggetto: this.idSoggetto, idDomanda: row.numeroDomanda, idEnteGiuridico: this.idEnteGiuridico, isEnteGiuridico: this.verificaTipoSoggetto } });
  }

  openStoricoBlocchi() {
    this.isSaveBlocco = false;
    this.dialog.open(StoricoBlocchiAttiviComponent, {
      width: '60%',
    });
  }

  inserisciBlocco() {
    this.isSaveBlocco = false;
    let dialogRef = this.dialog.open(EditBloccoComponent, {
      width: '50%',
      data: {
        modifica: 1,
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        this.isSaveBlocco = true;
        this.getElencoBlocchi();
      } else {
        this.getElencoBlocchi();
      }
    })
  }
  modificaBlocco(bloccoVO: BloccoVO) {
    this.isSaveBlocco = false;
    let dialogRef = this.dialog.open(EditBloccoComponent, {
      width: '40%',
      data: {
        modifica: 2,
        bloccoVO: bloccoVO
      }
    });

    dialogRef.afterClosed().subscribe(data => {

      if (data == true) {
        this.isSaveBlocco = true;
        this.getElencoBlocchi();
      } else {
        this.getElencoBlocchi();
      }

    })
  }

  getElencoBlocchi() {
    this.spinner = true;
    this.subscribers.getElencoBlocchi = this.anagBeneService.getElencoBlocchi(this.idSoggetto).subscribe(data => {
      if (data.length > 0) {
        this.listaBlocchi = data;
        this.isBlocchi = true;
        this.spinner = false;
      } else {
        this.listaBlocchi = data;
        this.isBlocchi = false;
        this.spinner = false;
      }
    }, err => {
      this.bloccaSpinner();
      /// faccio qualcosa dopo
    });
    console.log("numero domanda: " + this.numeroDomanda);
  }

  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 2) {
      this.spinner = false;
    }
  }
  getDatiAreaCrediti() {
    this.spinner = true;
    this.subscribers.getAltriDati = this.anagBeneService.getDatiAreaCreditiSoggetto(this.idSoggetto).subscribe(data => {
      if (data) {
        this.datiAreaCreditiSoggetto = data;
        this.spinner = false;
        console.log("dati area crediti!!!!!");

        console.log(this.datiAreaCreditiSoggetto);

      } else {
        this.spinner = false;
      }
    })
  }

  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  salvaModifiche() {

    this.loadedSave = false;
    this.soggObj.idUtenteAgg = this.idUtente;
    this.modificaControl = false;
    this.errorModifica = false;
    let elencoCampiModificati : Array<Number> = []; 


    let soggettoDaSalvare: BeneficiarioSoggCorrEG;
    soggettoDaSalvare = this.soggObj
    if (this.soggObj.ragSoc != this.formSoggetto.get("dati-anagrafici.ragioneSociale")?.value) {
      soggettoDaSalvare.datiAnagrafici = true;
      soggettoDaSalvare.ragSoc = this.formSoggetto.get("dati-anagrafici.ragioneSociale")?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(9); 
    }
    if (this.soggObj.cfSoggetto != this.formSoggetto.get("dati-anagrafici.codiceFiscale")?.value) {
      soggettoDaSalvare.datiAnagrafici = true;
      soggettoDaSalvare.cfSoggetto = this.formSoggetto.get("dati-anagrafici.codiceFiscale")?.value;
      this.modificaControl = true;
      //elencoCampiModificati.push(1); 
    }
    if (this.soggObj.idFormaGiuridica != this.formSoggetto.get("dati-anagrafici.formaGiuridica")?.value.idFormaGiuridica) {
      soggettoDaSalvare.datiAnagrafici = true;
      soggettoDaSalvare.idFormaGiuridica = this.formSoggetto.get("dati-anagrafici.formaGiuridica")?.value.idFormaGiuridica;
      this.modificaControl = true;
      elencoCampiModificati.push(3);
    }
    if (this.soggObj.flagPubblicoPrivato != this.formSoggetto.get('dati-anagrafici.pubblicoPrivatoRadio')?.value) {
      soggettoDaSalvare.datiAnagrafici = true;
      soggettoDaSalvare.flagPubblicoPrivato = this.formSoggetto.get('dati-anagrafici.pubblicoPrivatoRadio')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(1); 
    }
    if (this.formSoggetto.get('dati-anagrafici.codiceUniIpa')?.value != null && this.soggObj.codUniIpa != this.formSoggetto.get('dati-anagrafici.codiceUniIpa')?.value) {
      soggettoDaSalvare.codUniIpa = this.formSoggetto.get('dati-anagrafici.codiceUniIpa')?.value;
      soggettoDaSalvare.datiAnagrafici = true;
      this.modificaControl = true;
    }

    if (this.soggObj.descPec != this.formSoggetto.get('dati-anagrafici.pec')?.value) {
      soggettoDaSalvare.datiAnagrafici = true;
      soggettoDaSalvare.descPec = this.formSoggetto.get('dati-anagrafici.pec')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(4);
    }
    if (this.soggObj.dtCostituzione != this.formSoggetto.get('dati-anagrafici.dtCostituzione')?.value) {
      soggettoDaSalvare.datiAnagrafici = true;
      soggettoDaSalvare.dtCostituzione = this.formSoggetto.get('dati-anagrafici.dtCostituzione')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(5);
    }
    if (this.soggObj.descIndirizzo != this.formSoggetto.get('sede-legale.indirizzo')?.value) {
      soggettoDaSalvare.sedeLegale = true;
      soggettoDaSalvare.descIndirizzo = this.formSoggetto.get('sede-legale.indirizzo')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(6);
    }
    // if (this.soggObj.pIva != this.formSoggetto.get('sede-legale.partitaIva')?.value) {
    //   soggettoDaSalvare.sedeLegale = true;
    //   soggettoDaSalvare.pIva = this.formSoggetto.get('sede-legale.partitaIva')?.value;
    //   this.modificaControl = true;
    // }
    if (this.nazioneSelected?.value?.idNazione == 118) {
      soggettoDaSalvare.idNazione = 118;

      if (this.soggObj.idRegione != this.regioneSelected?.value.idRegione) {
        soggettoDaSalvare.sedeLegale = true;
        this.modificaControl = true;
        soggettoDaSalvare.idRegione = this.regioneSelected?.value.idRegione;
        elencoCampiModificati.push(2);
      }

      if (this.soggObj.idProvincia != this.provinciaSelected?.value.idProvincia) {
        this.modificaControl = true;
        this.soggObj.datiAnagrafici = true;
        this.soggObj.idRegione = this.regioneSelected?.value.idRegione;
        this.soggObj.idProvincia = this.provinciaSelected?.value.idProvincia;
        elencoCampiModificati.push(7);
      }

      if (this.soggObj.idComune != this.comuneSelected?.value.idComune) {
        soggettoDaSalvare.sedeLegale = true;
        soggettoDaSalvare.idRegione = this.regioneSelected?.value.idRegione;
        soggettoDaSalvare.idProvincia = this.provinciaSelected?.value.idProvincia;
        soggettoDaSalvare.idComune = this.comuneSelected?.value.idComune;
        elencoCampiModificati.push(8);
        soggettoDaSalvare.cap = this.formSoggetto.get('sede-legale.cap')?.value;
        this.modificaControl = true;
      }


    } else {
      if (this.soggObj.idNazione > 0 && this.soggObj.idNazione != this.nazioneSelected?.value?.idNazione) {
        soggettoDaSalvare.sedeLegale = true;
        soggettoDaSalvare.idNazione = this.nazioneSelected?.value.idNazione;
        soggettoDaSalvare.idComune = this.comuneSelected?.value.idComune;
        elencoCampiModificati.push(10);
        elencoCampiModificati.push(8);
        soggettoDaSalvare.cap = this.formSoggetto.get('sede-legale.cap')?.value;
        this.modificaControl = true;
      }
    }

    if (this.soggObj.idAttAteco != this.atecoSelected?.value.idAttAteco) {
      soggettoDaSalvare.attivitaEconomica = true;
      soggettoDaSalvare.idAttAteco = this.atecoSelected?.value.idAttAteco;
      elencoCampiModificati.push(11);
      this.modificaControl = true;
    }
    if (this.soggObj.flagRatingLeg != this.formSoggetto.get('attivita-economica.ratingLegalita')?.value) {
      soggettoDaSalvare.attivitaEconomica = true;
      soggettoDaSalvare.flagRatingLeg = this.formSoggetto.get('attivita-economica.ratingLegalita')?.value;
      this.modificaControl = true;
    }

    if (this.formSoggetto.get("attivita-economica.statoAttivita")?.value && this.formSoggetto.get("attivita-economica.statoAttivita")?.value.idAttivita != this.soggObj.idStatoAtt) {
      soggettoDaSalvare.attivitaEconomica = true;
      soggettoDaSalvare.idStatoAtt = this.formSoggetto.get("attivita-economica.statoAttivita")?.value.idAttivita;
      this.modificaControl = true;
      elencoCampiModificati.push(12);
    }
    if (this.soggObj.dtInizioAttEsito != this.formSoggetto.get('attivita-economica.dtInizioAttivita')?.value) {
      soggettoDaSalvare.attivitaEconomica = true;
      soggettoDaSalvare.dtInizioAttEsito = this.formSoggetto.get('attivita-economica.dtInizioAttivita')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(13);
    }
    if (this.soggObj.periodoScadEse != this.formSoggetto.get('attivita-economica.periodoChiusuraEsercizio')?.value) {
      soggettoDaSalvare.attivitaEconomica = true;
      soggettoDaSalvare.periodoScadEse = this.formSoggetto.get('attivita-economica.periodoChiusuraEsercizio')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(14);
    }
    if (this.soggObj.dtUltimoEseChiuso != this.formSoggetto.get('attivita-economica.dtChiusuraEsercizio')?.value) {
      soggettoDaSalvare.attivitaEconomica = true;
      soggettoDaSalvare.dtUltimoEseChiuso = this.formSoggetto.get('attivita-economica.dtChiusuraEsercizio')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(15);
    }

    if (this.soggObj.numIscrizione != this.formSoggetto.get('dati-iscrizione.numeroRea')?.value) {
      soggettoDaSalvare.datiIscrizione = true;
      soggettoDaSalvare.numIscrizione = this.formSoggetto.get('dati-iscrizione.numeroRea')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(17);
    }
    if (this.soggObj.dtIscrizione != this.formSoggetto.get('dati-iscrizione.dtIscrizione')?.value) {
      soggettoDaSalvare.datiIscrizione = true;
      soggettoDaSalvare.dtIscrizione = this.formSoggetto.get('dati-iscrizione.dtIscrizione')?.value;
      this.modificaControl = true;
      elencoCampiModificati.push(18);

    }
    if (this.formSoggetto.get('dati-iscrizione.provinciaIscrizione')?.value && this.soggObj.idProvinciaIscrizione != this.formSoggetto.get('dati-iscrizione.provinciaIscrizione')?.value.idProvincia) {
      soggettoDaSalvare.datiIscrizione = true;
      soggettoDaSalvare.idProvinciaIscrizione = this.formSoggetto.get('dati-iscrizione.provinciaIscrizione')?.value.idProvincia;
      this.modificaControl = true;
      elencoCampiModificati.push(19);

    }

    if (this.formSoggetto.get('sezione-appartenenza.sezioneAppartenenza')?.value && (this.formSoggetto.get('sezione-appartenenza.sezioneAppartenenza')?.value.idSezioneSpeciale != this.soggObj.idSezioneSpeciale)) {
      soggettoDaSalvare.idSezioneSpeciale = this.formSoggetto.get('sezione-appartenenza.sezioneAppartenenza')?.value.idSezioneSpeciale;
      this.modificaControl = true;
      soggettoDaSalvare.sezioneAppartenenza = true;
      elencoCampiModificati.push(19);
      
    }
    if (this.formSoggetto.get("dati-anagrafici.pec")?.value != null) {
      soggettoDaSalvare.descPec = this.formSoggetto.get("dati-anagrafici.pec")?.value;
    }
    console.log(soggettoDaSalvare);


    if (this.modificaControl) {
      soggettoDaSalvare.campiModificati= elencoCampiModificati; 
      this.subscribers.salvaModifiche = this.modificaAnagraficaService.updateAnagraficaEG(soggettoDaSalvare, this.idSoggetto, this.idDomanda).subscribe(data => {
        if (data == true) {
          this.loadedSave = true;
          this.isSaved = true;
          //this.editForm = false; 
          this.isNotEditSoggetto();

        } else {
          this.isSaved = false;
          this.loadedSave = true;
          this.isNotEditSoggetto();
        }
      })
    } else {
      this.errorModifica = true;
      this.loadedSave = true;
      this.messageError = "Modificare almeno un campo!"
    }
  }

  //DIALOG PER MOSTRARE I DETTAGLI DELLA DIMENSIONE IMPRESA
  visualizzaDettaglioDimensioneImpresa(dataValEsito: Date) {
    let parse;
    let annoDataValEsito;
    annoDataValEsito = dataValEsito.toString().substring(0, 4)

    if (dataValEsito != null) {
      parse = dataValEsito.toString();
      this.dialog.open(DettaglioDatiDimensioneComponent, {
        width: '70%',
        data: {
          idSoggetto: this.idSoggetto,
          nuumeroDomanda: this.idDomanda,
        },
      });
    }
  }


  downloadExcel(){
    this.anagBeneService.generaExcel('elenco-domande-progetti', this.dataSource.data);
  }

  downloadAllegato( nomeDocumento, idDocumentoIndex) {
    //this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      //  this.loadedDownload = true;
    });
  }

}
