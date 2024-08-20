/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AnagraficaBeneFisico } from 'src/app/gestione-anagrafica/commons/dto/anagraficaBeneFisico';
import { AtlanteVO } from 'src/app/gestione-anagrafica/commons/dto/atlante-vo';
import { BeneficiarioSoggCorrPF } from 'src/app/gestione-anagrafica/commons/dto/beneficiario-sogg-corr-PF';
import { BloccoVO } from 'src/app/gestione-anagrafica/commons/dto/bloccoVO';
import { DatiAreaCreditiSoggetto } from 'src/app/gestione-anagrafica/commons/dto/datiSoggettoAreaCreditiVO';
import { ElencoDomandeProgettiSec } from 'src/app/gestione-anagrafica/commons/dto/elenco-domande-progetti';
import { Nazioni } from 'src/app/gestione-anagrafica/commons/dto/nazioni';
import { Regioni } from 'src/app/gestione-anagrafica/commons/dto/regioni';
import { AnagraficaBeneficiarioService } from 'src/app/gestione-anagrafica/services/anagrafica-beneficiario.service';
import { DatiDomandaService } from 'src/app/gestione-anagrafica/services/dati-domanda-service';
import { ModificaAnagraficaService } from 'src/app/gestione-anagrafica/services/modifica-anagrafica.service';
import { EditBloccoComponent } from '../../edit-blocco/edit-blocco.component';
import { StoricoBlocchiAttiviComponent } from '../../storico-blocchi-attivi/storico-blocchi-attivi.component';
import { map, startWith } from 'rxjs/operators';
import { ElencoFormaGiuridica } from 'src/app/gestione-anagrafica/commons/dto/elencoFormaGiuridica';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-persona-fisica-container',
  templateUrl: './persona-fisica-container.component.html',
  styleUrls: ['./persona-fisica-container.component.scss']
})
export class PersonaFisicaContainerComponent implements OnInit, AfterViewChecked {

  displayedColumns: string[] = ['codiceBando', 'idDomanda', 'descStatoDomanda', 'descProgetto', 'descStatoProgetto', 'azioni'];

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;

  public myForm: FormGroup;

  dataSource: MatTableDataSource<ElencoDomandeProgettiSec> = new MatTableDataSource<ElencoDomandeProgettiSec>([]);

  public subscribers: any = {};

  isSaveBlocco: boolean;
  isBlocchi: boolean;
  idSoggetto: any;
  isEnteGiuridico: boolean;
  idEnteGiuridico: any;
  verificaTipoSoggetto: any;
  numeroDomanda: any;

  spinner: boolean;
  getConcluse: number = 0;

  colonna: string = "NUMERO_DOMANDA";
  ordinamentoString: string = "DESC";
  ordinamento: boolean = false;

  elenco: ElencoDomandeProgettiSec[]=[];

  beneficiarioFisico: AnagraficaBeneFisico;
  listaBlocchi: Array<BloccoVO> = new Array<BloccoVO>();
  displayedColumnsBlocchi: string[] = ['dataBlocco', 'causale', 'azioni'];
  datiAreaCreditiSoggetto: DatiAreaCreditiSoggetto = new DatiAreaCreditiSoggetto();


  nazioneData: Nazioni[] = [];
  filteredOptionsNazioni: Observable<Nazioni[]>;
  nazioneSelected: FormControl = new FormControl();

  nazioneNascitaData: Nazioni[] = [];
  filteredOptionsNazioniNascita: Observable<Nazioni[]>;
  nazioneNascitaSelected: FormControl = new FormControl();

  regioneData: Regioni[] = [];
  filteredOptionsRegioni: Observable<Regioni[]>;
  regioneSelected: FormControl = new FormControl();

  regioneNascitaData: Regioni[] = [];
  filteredOptionsRegioniNascita: Observable<Regioni[]>;
  regioneNascitaSelected: FormControl = new FormControl();

  provinciaData: AtlanteVO[] = [];
  filteredOptionsProvince: Observable<AtlanteVO[]>;
  provinciaSelected: FormControl = new FormControl();

  provinciaNascitaData: AtlanteVO[] = [];
  filteredOptionsProvinceNascita: Observable<AtlanteVO[]>;
  provinciaNascitaSelected: FormControl = new FormControl();

  comuneData: AtlanteVO[] = [];
  filteredOptionsComuni: Observable<AtlanteVO[]>;
  comuneSelected: FormControl = new FormControl();

  comuneNascitaData: AtlanteVO[] = [];
  filteredOptionsComuniNascita: Observable<AtlanteVO[]>;
  comuneNascitaSelected: FormControl = new FormControl();

  loadedNazioni: boolean;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;

  loadedNazioniNascita: boolean;
  loadedRegioniNascita: boolean = true;
  loadedProvinceNascita: boolean = true;
  loadedComuniNascita: boolean = true;

  editForm: boolean = false;
  loadedSalva: boolean = true;
  isSave: boolean;
  modificaControl: boolean;
  errorModifica: boolean;
  userloaded: boolean;
  user: UserInfoSec;
  idUtente: number;
  messageError: string;
  isMessageErrorVisible: boolean;
  ndg: string;
  formSoggetto!: FormGroup;
  soggObj: BeneficiarioSoggCorrPF = new BeneficiarioSoggCorrPF();
  formaGiuridicaData: ElencoFormaGiuridica[] = [];
  tipoAnagraficaData: AttivitaDTO[] = [];

  @ViewChild('tabs', { static: false }) tabs;
  veroIdDomanda: any;
  idProgetto: any;

  constructor(private route: ActivatedRoute, private anagBeneService: AnagraficaBeneficiarioService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router,
    private userService: UserService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private handleExceptionService: HandleExceptionService,
    private datiDomandaService: DatiDomandaService,
  ) { }
  @ViewChild("elencoDomandeProgetti") paginator: MatPaginator;
  @ViewChild("elencoDomandeProgettiSort") sort: MatSort;
  ngOnInit(): void {
    this.spinner = true;
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idTipoSoggetto');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    this.veroIdDomanda = this.route.snapshot.queryParamMap.get("numeroDomanda");
    this.idProgetto = this.route.snapshot.queryParamMap.get("idProgetto");
    this.ndg = this.route.snapshot.queryParamMap.get("ndg");
    // this.isEnteGiuridico = false;
    //PERSONA FISICA:
    // this.myForm = this.fb.group({
    //   //DATI ANAGRAFICI:
    //   cognome: new FormControl({ value: '', disabled: true }),
    //   nome: new FormControl({ value: '', disabled: true }),
    //   tipoSoggetto: new FormControl({ value: 'BENEFICIARIO', disabled: true }),
    //   codiceFiscale: new FormControl({ value: '', disabled: true }),
    //   dataDiNascita: new FormControl({ value: '', disabled: true }),
    //   comuneDiNascita: new FormControl({ value: '', disabled: true }),
    //   provinciaDiNascita: new FormControl({ value: '', disabled: true }),
    //   regioneDiNascita: new FormControl({ value: '', disabled: true }),
    //   nazioneDiNascita: new FormControl({ value: '', disabled: true }),
    //   //RESIDENZA:
    //   indirizzoPF: new FormControl({ value: '', disabled: true }),
    //   comunePF: new FormControl({ value: '', disabled: true }),
    //   provinciaPF: new FormControl({ value: '', disabled: true }),
    //   capPF: new FormControl({ value: '', disabled: true }),
    //   regionePF: new FormControl({ value: '', disabled: true }),
    //   nazionePF: new FormControl({ value: '', disabled: true }),
    //   //ELENCO DOMANDE E PROGETTI
    //   codiceFondo: new FormControl({ value: '', disabled: true }),
    //   numeroDomanda: new FormControl({ value: '', disabled: true }),
    //   statoDomanda: new FormControl({ value: '', disabled: true }),
    //   codiceProgetto: new FormControl({ value: '', disabled: true }),
    //   statoProgetto: new FormControl({ value: '', disabled: true }),
    // });

    //SETTING DEL FORM PER PERSONA FISICA
    // this.anagBeneService.getAnagBeneFisico(this.idSoggetto, this.numeroDomanda);
    // this.subscribers.anagBeneFisicoInfo$ = this.anagBeneService.anagBeneFisicoInfo$.subscribe(data => {
    //   if (data) {
    //     this.beneficiarioFisico = data;

    //     // this.myForm.setValue({
    //     //   //DATI ANAGRAFICI:
    //     //   cognome: [this.beneficiarioFisico.cognome],
    //     //   nome: [this.beneficiarioFisico.nome],
    //     //   tipoSoggetto: "BENEFICIARIO",
    //     //   codiceFiscale: [this.beneficiarioFisico.codiceFiscale],
    //     //   dataDiNascita: new Date(Date.parse(this.beneficiarioFisico.dataDiNascita)),
    //     //   comuneDiNascita: [this.beneficiarioFisico.comuneDiNascita],
    //     //   provinciaDiNascita: [this.beneficiarioFisico.provinciaDiNascita],
    //     //   regioneDiNascita: [this.beneficiarioFisico.regioneDiNascita],
    //     //   nazioneDiNascita: [this.beneficiarioFisico.nazioneDiNascita],
    //     //   //RESIDENZA:
    //     //   indirizzoPF: [this.beneficiarioFisico.indirizzoPF],
    //     //   comunePF: [this.beneficiarioFisico.comunePF],
    //     //   provinciaPF: [this.beneficiarioFisico.provinciaPF],
    //     //   capPF: [this.beneficiarioFisico.capPF],
    //     //   regionePF: [this.beneficiarioFisico.regionePF],
    //     //   nazionePF: [this.beneficiarioFisico.nazionePF],
    //     //   //ELENCO DOMANDE
    //     //   codiceFondo: [null],
    //     //   numeroDomanda: [null],
    //     //   statoDomanda: [null],
    //     //   codiceProgetto: [null],
    //     //   statoProgetto: [null]
    //     // });
    //     this.bloccaSpinner();
    //   }
    // });
    this.getElencoBlocchi();
    this.getDatiAreaCrediti();

    this.formSoggetto = this.fb.group({
      'dati-anagrafici': this.fb.group({
        cognome: [{ value: '', disabled: true }],
        nome: [{ value: '', disabled: true }],
        ruolo: [{ value: '', disabled: true }],
        codiceFiscale: [{ value: '', disabled: true }],
        dtNascita: [{ value: '', disabled: true }],
        comune: [{ value: '', disabled: true }],
        provincia: [{ value: '', disabled: true }],
        regione: [{ value: '', disabled: true }],
        nazione: [{ value: '', disabled: true }]
      }),
      'residenza': this.fb.group({
        indirizzo: [{ value: '', disabled: true }],
        comuneResidenza: [{ value: '', disabled: true }],
        provinciaResidenza: [{ value: '', disabled: true }],
        cap: [{ value: '', disabled: true }],
        regioneResidenza: [{ value: '', disabled: true }],
        nazioneResidenza: [{ value: '', disabled: true }]
      })
    });
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
    });
    this.getBenefFisico();

  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }


  getBenefFisico() {


    this.subscribers.benefPF = this.anagBeneService.getAnagBeneFisicoNew(this.idSoggetto, this.veroIdDomanda, this.idProgetto).subscribe(data => {

      if (data) {
        this.soggObj = data;
        this.ndg = this.soggObj.ndg;
        this.formSoggetto = this.fb.group({
          'dati-anagrafici': this.fb.group({
            cognome: [{ value: this.soggObj.cognome, disabled: true }],
            nome: [{ value: this.soggObj.nome, disabled: true }],
            ruolo: [{ value: null, disabled: true }],
            codiceFiscale: [{ value: this.soggObj.codiceFiscale, disabled: true }],
            dtNascita: [{ value: this.soggObj.dataDiNascita, disabled: true }],
            comune: [{ value: this.soggObj.comuneDiNascita, disabled: true }],
            provincia: [{ value: this.soggObj.provinciaDiNascita, disabled: true }],
            regione: [{ value: this.soggObj.regioneDiNascita, disabled: true }],
            nazione: [{ value: this.soggObj.nazioneDiNascita, disabled: true }]
          }),
          'residenza': this.fb.group({
            indirizzo: [{ value: this.soggObj.indirizzoPF, disabled: true }],
            comuneResidenza: [{ value: this.soggObj.comunePF, disabled: true }],
            provinciaResidenza: [{ value: this.soggObj.provinciaPF, disabled: true }],
            cap: [{ value: this.soggObj.capPF, disabled: true }],
            regioneResidenza: [{ value: this.soggObj.regionePF, disabled: true }],
            nazioneResidenza: [{ value: this.soggObj.nazionePF, disabled: true }]
          })
        })
        this.loadData();
        //this.regioneNascitaSelected.value.idRegione = this.benefSoggCorrPF.idRegioneDiNascita;

      } else {
        this.spinner = true;
      }
    })

  }

  loadData() {
    this.subscribers.getElencoTipoAnag = this.anagBeneService.getElencoTipoAnag().subscribe(data => {
      if (data) {
        this.tipoAnagraficaData = data;
        if (this.soggObj && this.soggObj.idTipoAnagrafica && this.formSoggetto.get("dati-anagrafici")) {
          this.formSoggetto.get("dati-anagrafici").get("ruolo").setValue(this.tipoAnagraficaData.find(t => t.idAttivita === this.soggObj.idTipoAnagrafica));
        }
      }
      // this.loadedFormeGiuridiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del tipo anagrafica");
      // this.loadedFormeGiuridiche = true;

    });
    this.loadedNazioniNascita = false;
    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.formSoggetto.get('residenza.nazioneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.formSoggetto.get("residenza") && this.soggObj.idNazionePF) {
          this.formSoggetto.get("residenza.nazioneResidenza").setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazionePF.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazionePF.toString()));
          if (this.soggObj.idNazionePF == 118)
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

    this.subscribers.getNazioniNascita = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneNascitaData = data;
        this.filteredOptionsNazioniNascita = this.formSoggetto.get('dati-anagrafici.nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioniNascita(name) : this.nazioneNascitaData.slice())
          );

        if (this.formSoggetto.get("dati-anagrafici") && this.soggObj.idNazioneDiNascita) {
          this.formSoggetto.get("dati-anagrafici.nazione").setValue(this.nazioneNascitaData.find(f => f.idNazione === this.soggObj.idNazioneDiNascita.toString()));
          this.nazioneNascitaSelected.setValue(this.nazioneNascitaData.find(f => f.idNazione === this.soggObj.idNazioneDiNascita.toString()));
          if (this.soggObj.idNazioneDiNascita == 118)
            this.loadRegioniNascita(true);
          else
            this.loadComuniNascitaEsteri(true);
        }
      }
      this.loadedNazioniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioniNascita = true;
    });
  }

  loadRegioniNascita(init?: boolean) {
    this.loadedRegioniNascita = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneNascitaData = data;
        this.filteredOptionsRegioniNascita = this.formSoggetto.get('dati-anagrafici.regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name, 1) : this.regioneNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.nazione") && init) {

          this.formSoggetto.get("dati-anagrafici.regione").setValue(this.regioneNascitaData.find(f => f.idRegione === this.soggObj.idRegioneDiNascita.toString()));
          this.regioneNascitaSelected.setValue(this.regioneNascitaData.find(f => f.idRegione === this.soggObj.idRegioneDiNascita.toString()));
          if (this.regioneNascitaSelected.value != null)
            this.loadProvinceNascita(init);
        }
      }
      this.loadedRegioniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      this.loadedRegioniNascita = true;
    });
  }

  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.formSoggetto.get('residenza.regioneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name, 0) : this.regioneData.slice())
          );
        if (this.formSoggetto.get("residenza") && init) {
          this.formSoggetto.get("residenza.regioneResidenza").setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegionePF.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegionePF.toString()));
          if (this.regioneSelected.value != null)
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

  loadProvinceNascita(init?: boolean) {
    this.loadedProvinceNascita = false;

    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneNascitaSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaNascitaData = data;
        this.filteredOptionsProvinceNascita = this.formSoggetto.get('dati-anagrafici.provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name, 1) : this.provinciaNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.regione") && this.soggObj.idProvinciaDiNascita && init) {
          this.formSoggetto.get("dati-anagrafici.provincia").setValue(this.provinciaNascitaData.find(f => f.idProvincia === this.soggObj.idProvinciaDiNascita.toString()));
          this.provinciaNascitaSelected.setValue(this.provinciaNascitaData.find(f => f.idProvincia === this.soggObj.idProvinciaDiNascita.toString()));
          if (this.provinciaNascitaSelected.value != null)
            this.loadComuniNascitaItalia(init);

        }
      }
      this.loadedProvinceNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province.");
      this.loadedProvinceNascita = true;
    });
  }

  loadProvince(init?: boolean) {
    this.loadedProvince = false;
    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.formSoggetto.get('residenza.provinciaResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name, 0) : this.provinciaData.slice())
          );
        if (this.formSoggetto.get("residenza.regioneResidenza") && init && this.soggObj) {
          this.formSoggetto.get("residenza.provinciaResidenza").setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvinciaPF.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvinciaPF.toString()));
          if (this.provinciaSelected.value != null)
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
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggetto.get('residenza.comuneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 0) : this.comuneData.slice())
          );
        if (this.formSoggetto.get("residenza.provinciaResidenza") && this.soggObj && init) {
          this.formSoggetto.get("residenza.comuneResidenza").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
          
          // if (this.comuneSelected.value)
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

  loadComuniNascitaItalia(init?: boolean) {
    this.loadedComuniNascita = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaNascitaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneNascitaData = data;
        this.filteredOptionsComuniNascita = this.formSoggetto.get('dati-anagrafici.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 1) : this.comuneNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.provincia") && init && this.soggObj.idComuneDiNascita > 0) {
          this.formSoggetto.get("dati-anagrafici.comune").setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
          this.comuneNascitaSelected.setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
          //this.setCap();
        }
      }
      this.loadedComuniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuniNascita = true;
    });
  }

  loadComuniEsteri(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggetto.get('residenza.comuneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 0) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComunePF && this.formSoggetto.get("residenza") && init) {
          this.formSoggetto.get("residenza.comuneResidenza").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
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

  loadComuniNascitaEsteri(init?: boolean) {
    this.loadedComuniNascita = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneNascitaSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneNascitaData = data;
        this.filteredOptionsComuniNascita = this.formSoggetto.get('dati-anagrafici.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 1) : this.comuneNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.comune") && init) {
          this.formSoggetto.get("dati-anagrafici.comune").setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
          this.comuneSelected.setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
          //this.setCap();
        }
      }
      this.loadedComuniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuniNascita = true;
    });
  }

  setCap() {
    this.formSoggetto.get('residenza.cap').setValue(this.comuneSelected.value.cap);
    if (this.comuneSelected.value.cap) {
      this.formSoggetto.get("residenza.cap").disable();
    } else {
      this.formSoggetto.get("residenza.cap").enable();
    }
  }


  click(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option.value !== this.nazioneSelected.value) {
          this.nazioneSelected.setValue(event.option.value);
          this.resetRegione();
          if (this.nazioneSelected.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioni();
          } else {
            this.loadComuniEsteri();
          }
        }
        break;
      case "R":
        if (event.option.value !== this.regioneSelected.value) {
          this.regioneSelected.setValue(event.option.value);
          this.resetProvincia();
          this.loadProvince();
        }
        break;
      case "P":
        if (event.option.value !== this.provinciaSelected.value) {
          this.provinciaSelected.setValue(event.option.value);
          this.resetComune();
          this.loadComuniItalia();
        }
        break;
      case "C":
        if (event.option.value !== this.comuneSelected.value) {
          this.comuneSelected.setValue(event.option.value);
          //this.setCap();
          console.log(this.comuneSelected);
          
          this.resetCap()
        }
        break;
      default:
        break;
    }
  }

  clickNascita(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option.value !== this.nazioneNascitaSelected.value) {
          this.nazioneNascitaSelected.setValue(event.option.value);
          this.resetRegioneNascita();
          if (this.nazioneNascitaSelected.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioniNascita();
          } else {
            this.loadComuniNascitaEsteri();
          }
        }
        break;
      case "R":
        if (event.option.value !== this.regioneNascitaSelected.value) {
          this.regioneNascitaSelected.setValue(event.option.value);
          this.resetProvinciaNascita();
          this.loadProvinceNascita();
        }
        break;
      case "P":
        if (event.option.value !== this.provinciaNascitaSelected.value) {
          this.provinciaNascitaSelected.setValue(event.option.value);
          this.resetComuneNascita();
          this.loadComuniNascitaItalia();
        }
        break;
      case "C":
        if (event.option.value !== this.comuneNascitaSelected.value) {
          this.comuneNascitaSelected.setValue(event.option.value);
          // this.setCap();
        }
        break;
      default:
        break;
    }
  }

  check(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneSelected || (this.formSoggetto.get('residenza.nazioneResidenza') && this.nazioneSelected.value !== this.formSoggetto.get('residenza.nazioneResidenza').value)) {
            this.formSoggetto.get('residenza.nazioneResidenza').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.formSoggetto.get('residenza.regioneResidenza') && this.regioneSelected.value !== this.formSoggetto.get('residenza.regioneResidenza').value)) {
            this.formSoggetto.get('residenza.regioneResidenza').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.formSoggetto.get('residenza.provinciaResidenza') && this.provinciaSelected.value !== this.formSoggetto.get('residenza.provinciaResidenza').value)) {
            this.formSoggetto.get('residenza.provinciaResidenza').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.formSoggetto.get('residenza.comuneResidenza') && this.comuneSelected.value !== this.formSoggetto.get('residenza.comuneResidenza').value)) {
            this.formSoggetto.get('residenza.comuneResidenza').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        default:
          break;
      }
    }, 200);
  }

  checkNascita(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneNascitaSelected || (this.formSoggetto.get('dati-anagrafici.nazione') && this.nazioneNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.nazione').value)) {
            this.formSoggetto.get('dati-anagrafici.nazione').setValue(null);
            this.nazioneNascitaSelected = new FormControl();
            this.resetRegioneNascita();
          }
          break;
        case "R":
          if (!this.regioneNascitaSelected || (this.formSoggetto.get('dati-anagrafici.regione') && this.regioneNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.regione').value)) {
            this.formSoggetto.get('dati-anagrafici.regione').setValue(null);
            this.regioneNascitaSelected = new FormControl();
            this.resetProvinciaNascita()
          }
          break;
        case "P":
          if (!this.provinciaNascitaSelected || (this.formSoggetto.get('dati-anagrafici.provincia') && this.provinciaNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.provincia').value)) {
            this.formSoggetto.get('dati-anagrafici.provincia').setValue(null);
            this.provinciaNascitaSelected = new FormControl();
            this.resetComuneNascita();
          }
          break;
        case "C":
          if (!this.comuneNascitaSelected || (this.formSoggetto.get('dati-anagrafici.comune') && this.comuneNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.comune').value)) {
            this.formSoggetto.get('dati-anagrafici.comune').setValue(null);
            this.comuneNascitaSelected = new FormControl();
            //this.resetComuneNascita();
          }
          break;
        default:
          break;
      }
    }, 200);
  }


  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.formSoggetto.get('residenza.regioneResidenza').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggetto.get('residenza.regioneResidenza').enable();
    } else {
      this.formSoggetto.get('residenza.regioneResidenza').disable();
    }
    this.resetProvincia();
  }
  
  resetRegioneNascita() {
    this.regioneNascitaData = [];
    this.regioneNascitaSelected = new FormControl();
    this.formSoggetto.get('dati-anagrafici.regione').setValue(null);
    if (this.nazioneNascitaSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggetto.get('dati-anagrafici.regione').enable();
    } else {
      this.formSoggetto.get('dati-anagrafici.regione').disable();
    }
    this.resetProvinciaNascita();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.formSoggetto.get('residenza.provinciaResidenza').setValue(null);
    if (this.regioneSelected?.value) {
      this.formSoggetto.get('residenza.provinciaResidenza').enable();
    } else {
      this.formSoggetto.get('residenza.provinciaResidenza').disable();
    }
    this.resetComune();
  }

  resetProvinciaNascita() {
    this.provinciaNascitaData = [];
    this.provinciaNascitaSelected = new FormControl();
    this.formSoggetto.get('dati-anagrafici.provincia').setValue(null);
    if (this.regioneNascitaSelected?.value) {
      this.formSoggetto.get('dati-anagrafici.provincia').enable();
    } else {
      this.formSoggetto.get('dati-anagrafici.provincia').disable();
    }
    this.resetComuneNascita();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.formSoggetto.get('residenza.comuneResidenza').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggetto.get('residenza.comuneResidenza').enable();
    } else {
      this.formSoggetto.get('residenza.comuneResidenza').disable();
    }
    this.resetCap();
  }
  
  resetComuneNascita() {
    this.comuneNascitaData = [];
    this.comuneNascitaSelected = new FormControl();
    this.formSoggetto.get('dati-anagrafici.comune').setValue(null);
    if (this.provinciaNascitaSelected?.value || (this.nazioneNascitaSelected?.value?.idNazione && this.nazioneNascitaSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggetto.get('dati-anagrafici.comune').enable();
    } else {
      this.formSoggetto.get('dati-anagrafici.comune').disable();
    }
    //this.resetCap();
  }

  resetCap() {
    this.formSoggetto.get('residenza.cap').setValue(null);
    // this.formSoggetto.get('residenza.cap').disable();
  }
  // resetCapNascita() {
  //   this.formSoggettoCorrelato.get('dati-anagrafici.cap').setValue(null);
  //   this.formSoggettoCorrelato.get('dati-anagrafici.cap').disable();
  // }

  modificaBSoggettoCorr() {

    this.formSoggetto.enable();
    if (this.formSoggetto.get("residenza") && this.formSoggetto.get("residenza").get("indirizzo")
      && (!this.formSoggetto.get("residenza").get("indirizzo").value || !this.formSoggetto.get("residenza").get("indirizzo").value.length)) {
      this.formSoggetto.get("residenza").get("indirizzo").setErrors({ error: "required" })
    }
    this.formSoggetto.markAllAsTouched();

    // this.router.navigate(["/drawer/" + this.idOp + "/modificaSoggCorrDipDom"],
    //   {
    //     queryParams: {
    //       idSoggetto: this.idSoggetto,
    //       numeroDomanda: this.numeroDomanda,
    //       idDomanda: this.idDomanda,
    //       tipoSoggetto: this.tipoSoggetto,
    //       idSoggettoCorr: this.idSoggettoCorr,
    //       tipoSoggettoCorr: this.tipoSoggettoCorr
    //     }
    //   });

  }

  editSoggetto(): void {
    this.isSave = false;
    this.editForm = true;

    this.formSoggetto.enable();
    this.formSoggetto.get("dati-anagrafici.codiceFiscale").disable();
    this.formSoggetto.get("dati-anagrafici.ruolo").disable();

    if (!this.nazioneSelected?.value || this.nazioneSelected.value.idNazione.toString() !== "118") {
      this.formSoggetto.get("dati-anagrafici.regione").disable();
      this.formSoggetto.get("dati-anagrafici.provincia").disable();
    }
    // if (!this.nazioneNascitaSelected?.value || this.nazioneNascitaSelected.value.idNazione.toString() !== "118") {
    //   this.formSoggetto.get("residenza.regioneResidenza").disable();
    //   this.formSoggetto.get("residenza.provinciaResidenza").disable();
    // }

    this.formSoggetto.get("dati-anagrafici").disable();
  }

  salvaModifiche() {

    this.loadedSalva = false;
    console.log(this.comuneNascitaSelected);
    console.log(this.comuneSelected);
    let elencoCampiModificati : Array<Number> = [];
    
    
    if (this.soggObj.nome != this.formSoggetto.get("dati-anagrafici.nome").value) {
      this.soggObj.nome = this.formSoggetto.get("dati-anagrafici.nome").value;
      this.soggObj.datiAnagrafici = true;
      this.modificaControl = true;
      elencoCampiModificati.push(9); 
    }
    if (this.soggObj.cognome != this.formSoggetto.get("dati-anagrafici.cognome").value) {
      this.soggObj.cognome = this.formSoggetto.get("dati-anagrafici.cognome").value;
      this.soggObj.datiAnagrafici = true;
      this.modificaControl = true;
      let insert9inCampiModificati:  boolean = false; 
      elencoCampiModificati.forEach(a => {
        if(a==9){
          insert9inCampiModificati= true;
        }
      });
      if(!insert9inCampiModificati){
        elencoCampiModificati.push(9);
      }
    }
    if (this.soggObj.dataDiNascita != this.formSoggetto.get("dati-anagrafici.dtNascita").value) {
      this.soggObj.dataDiNascita = this.formSoggetto.get("dati-anagrafici.dtNascita").value;
      this.soggObj.datiAnagrafici = true;
      this.modificaControl = true;
    }
    //  if(this.benefSoggCorrPF.idTipoSoggCorr != this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").value.idTipoSoggCorr){
    //   this.benefSoggCorrPF.datiAnagrafici = true;
    //   this.benefSoggCorrPF.idTipoSoggCorr = this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").value.idTipoSoggCorr;
    //  }
    if (this.nazioneNascitaSelected.value.idNazione == 118) {
      this.soggObj.idNazioneDiNascita = this.nazioneNascitaSelected.value.idNazione;
      if(this.regioneNascitaSelected.value.idRegione!=this.soggObj.idRegioneDiNascita){
        this.modificaControl = true;
        this.soggObj.datiAnagrafici = true;
        this.soggObj.idRegioneDiNascita = this.regioneNascitaSelected.value.idRegione;
      }
      if(this.soggObj.idProvinciaDiNascita!= this.provinciaNascitaSelected.value.idProvincia){
        this.modificaControl = true;
        this.soggObj.datiAnagrafici = true;
        this.soggObj.idRegioneDiNascita = this.regioneNascitaSelected.value.idRegione;
        this.soggObj.idProvinciaDiNascita = this.provinciaNascitaSelected.value.idProvincia;
      }
      if (this.soggObj.idComuneDiNascita != this.comuneNascitaSelected.value.idComune) {
        this.soggObj.datiAnagrafici = true;
        this.soggObj.idComuneDiNascita = this.comuneNascitaSelected.value.idComune
        this.soggObj.idRegioneDiNascita = this.regioneNascitaSelected.value.idRegione;
        this.soggObj.idProvinciaDiNascita = this.provinciaNascitaSelected.value.idProvincia;
        this.modificaControl = true;
      } 
    } else {
      this.soggObj.datiAnagrafici = true;
      this.soggObj.idNazioneDiNascita = this.nazioneNascitaSelected.value.idNazione;
      this.soggObj.idComuneDiNascita = this.comuneNascitaSelected.value.idComune;
      this.modificaControl = true;
    }

    if (this.soggObj.indirizzoPF != this.formSoggetto.get("residenza.indirizzo").value) {
      this.soggObj.sedeLegale = true;
      this.soggObj.indirizzoPF = this.formSoggetto.get("residenza.indirizzo").value;
      this.modificaControl = true;
      elencoCampiModificati.push(6);
    }
    if (this.nazioneSelected.value.idNazione == 118) {
      this.soggObj.idNazionePF = this.nazioneSelected.value.idNazione;

      if(this.regioneSelected?.value.idRegione!=this.soggObj.idRegionePF){
        this.modificaControl = true;
        this.soggObj.sedeLegale = true;
        this.soggObj.idRegionePF = this.regioneSelected?.value.idRegione;
        elencoCampiModificati.push(2);
      }
      if(this.soggObj.idProvinciaPF!= this.provinciaSelected?.value.idProvincia){
        this.modificaControl = true;
        this.soggObj.sedeLegale = true;
        this.soggObj.idRegionePF = this.regioneSelected?.value.idRegione;
        this.soggObj.idProvinciaPF = this.provinciaSelected.value.idProvincia;
        elencoCampiModificati.push(7);
      }
      if (this.soggObj.idComunePF != this.comuneSelected?.value.idComune) {
        this.modificaControl = true;
        this.soggObj.sedeLegale = true;
        this.soggObj.idComunePF= this.comuneSelected.value.idComune
        this.soggObj.idRegionePF = this.regioneSelected.value.idRegione;
        this.soggObj.idProvinciaPF = this.provinciaSelected.value.idProvincia;
        this.soggObj.capPF = this.formSoggetto.get("residenza.cap").value;
        elencoCampiModificati.push(8);
      } 
    } else {
      if (this.soggObj.idNazionePF > 0 && this.nazioneSelected.value && this.soggObj.idNazionePF != this.nazioneSelected.value?.idNazione) {
      this.soggObj.sedeLegale = true;
      this.soggObj.idNazionePF = this.nazioneSelected.value.idNazione;
      this.soggObj.idComunePF = this.comuneSelected.value.idComune;
      this.soggObj.capPF = this.formSoggetto.get("residenza.cap").value;
      this.modificaControl = true;
      elencoCampiModificati.push(10);
      elencoCampiModificati.push(8);
      }
    }

    this.soggObj.idUtenteAgg = this.idUtente;
    console.log(this.soggObj);

    if (this.modificaControl == true) {
      this.soggObj.campiModificati= elencoCampiModificati; 
      this.subscribers.salvaModifiche = this.modificaAnagraficaService.updateAnagraficaPF(this.soggObj, this.idSoggetto, this.numeroDomanda).subscribe(data => {
        if (data == true) {
          this.loadedSalva = true
          this.isSave = data;
          this.isNotEditSoggetto();
        } else {
          this.isSave = data;
        }

      });
      
    } else {
      this.errorModifica = true;
      this.loadedSalva = true;
      this.messageError = "Modificare almeno un campo!";
    }



  }

  isNotEditSoggetto(): void {
    //this.location.back();
    this.modificaControl = false;
    this.errorModifica = false;
    this.editForm = false;
    this.formSoggetto.disable();
    this.getBenefFisico();
    setTimeout(() => {
      this.ngOnInit()
    }, 3000);
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

  displayFnProvinceNascita(prov: AtlanteVO): string {
    return prov && prov.siglaProvincia ? prov.descProvincia : '';
  }

  displayFnComuni(com: AtlanteVO): string {
    return com && com.descComune ? com.descComune : '';
  }

  private _filterNazioni(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }
  
  private _filterNazioniNascita(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneNascitaData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }

  private _filterRegioni(descrizione: string, flagNascita: number): Regioni[] {
    const filterValue = descrizione.toLowerCase();
    if (flagNascita == 0) {
      return this.regioneData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
    } else {
      return this.regioneNascitaData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
    }
  }

  private _filterProvince(descrizione: string, flagNascita: number): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    if (flagNascita == 0) {
      return this.provinciaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));
    } else {
      return this.provinciaNascitaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));

    }
  }

  private _filterComuni(descrizione: string, flasgNascita: number): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    if (flasgNascita == 0) {
      return this.comuneData.filter(option => option.descComune.toLowerCase().includes(filterValue));
    } else {
      return this.comuneNascitaData.filter(option => option.descComune.toLowerCase().includes(filterValue));
    }
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

  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 0) {
      this.spinner = false;
    }
  }

  sortColonna(indiceColonna?: HTMLSelectElement) {
    let indiceColonnaString: string;
    this.ordinamento = !this.ordinamento;
    // if (this.ordinamento) {
    //   this.ordinamentoString = "DESC";
    // } else {
    //   this.ordinamentoString = "ASC";
    // }

    // if (indiceColonna.toString() == "codiceBando") {
    //   indiceColonnaString = "PROGR_BANDO_LINEA_INTERVENTO";
    // } else if (indiceColonna.toString() == 'numeroDomanda') {
    //   indiceColonnaString = "NUMERO_DOMANDA";
    // } else if (indiceColonna.toString() == 'statoDomanda') {
    //   indiceColonnaString = "DESC_STATO_DOMANDA";
    // } else if (indiceColonna.toString() == 'codiceProgetto') {
    //   indiceColonnaString = "CODICE_VISUALIZZATO";
    // } else if (indiceColonna.toString() == 'statoProgetto') {
    //   indiceColonnaString = "DESC_STATO_PROGETTO";
    // }
    // this.spinner = true;

  }

  modificaBeneficiario() {
    this.router.navigate(["/drawer/" + this.idOp + "/modificaAnagraficaPF"], { queryParams: { idSoggetto: this.idSoggetto, verificaTipoSoggetto: this.verificaTipoSoggetto, numeroDomanda: this.numeroDomanda } });
  }

  goToStorico() {
    this.router.navigate(["/drawer/" + this.idOp + "/storicoBeneficiario"], {
      queryParams: {
        idSoggetto: this.idSoggetto,
        isEnteGiuridico: this.verificaTipoSoggetto,
        idEnteGiuridico: this.idEnteGiuridico,
        ndg: this.soggObj.ndg,
        idDomanda: this.veroIdDomanda,
        idProgetto: this.idProgetto
      }
    });
  }

  visualizzaDatiDomanda(row) {
    this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaPF"], {
      queryParams: {
        idSoggetto: this.idSoggetto,
        idDomanda: row.numeroDomanda,
        tipoSoggetto: this.verificaTipoSoggetto,
        ndg: this.ndg,
        idProgetto: row.idProgetto,
        numeroDomanda: row.numeroDomanda,
        nome: this.soggObj.nome,
        cognome: this.soggObj.cognome,
        codiceFiscale: this.soggObj.codiceFiscale
      }
    });
    
  }

  modificaDatiDomandaPF(row) {
    this.router.navigate(["/drawer/" + this.idOp + "/modificaDatiDomandaPF"], { queryParams: { idSoggetto: this.idSoggetto, idDomanda: row.numeroDomanda, tipoSoggetto: this.verificaTipoSoggetto } });
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
        this.isSaveBlocco = true
        this.getElencoBlocchi()
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
        this.isSaveBlocco = true
        this.getElencoBlocchi()
      }

    })
  }
  getDatiAreaCrediti() {
    this.spinner = true;
    this.subscribers.getAltriDati = this.anagBeneService.getDatiAreaCreditiSoggetto(this.idSoggetto).subscribe(data => {
      if (data) {
        this.datiAreaCreditiSoggetto = data;
        this.spinner = false;
      } else {
        this.spinner = false;
      }
    });

    this.anagBeneService.getElencoDomandeProgetti(this.idSoggetto, false).subscribe(data => {
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
      }
    });
  }
  downloadExcel(){
    this.anagBeneService.generaExcel('elenco-domande-progetti', this.dataSource.data);
  }
  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }
}