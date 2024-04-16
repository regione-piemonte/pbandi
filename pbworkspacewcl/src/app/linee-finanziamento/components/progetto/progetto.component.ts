/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { patternCap, patternEmail, patternTelFax, SharedService } from 'src/app/shared/services/shared.service';
import { BeneficiarioProgetto } from '../../commons/dto/beneficario-progetto';
import { BeneficiarioCspDTO } from '../../commons/dto/beneficiario-csp-dto';
import { CodiceDescrizione } from '../../commons/dto/codice-descrizione';
import { Comune } from '../../commons/dto/comune';
import { DatiGeneraliProgetto } from '../../commons/dto/dati-generali-progetto';
import { IdDescBreveDescEstesa } from '../../commons/dto/id-desc-breve-desc-estesa';
import { InformazioniBase } from '../../commons/dto/informazioni-base';
import { InizializzaComboDTO } from '../../commons/dto/inizializza-combo-dto';
import { RapprLegaleCspDTO } from '../../commons/dto/rappr-legale-csp-dto';
import { RappresentanteLegaleProgetto } from '../../commons/dto/rappresentante-legale-progetto';
import { SchedaProgetto } from '../../commons/dto/scheda-progetto';
import { SedeIntervento } from '../../commons/dto/sede-intervento';
import { SoggettoGenerico } from '../../commons/dto/soggetto-generico';
import { SoggettoPF } from '../../commons/dto/soggetto-pf';
import { SoggettoPG } from '../../commons/dto/soggetto-pg';
import { TabDirRegSogg } from '../../commons/dto/tab-dir-reg-sogg';
import { SalvaSchedaProgettoRequest } from '../../commons/requests/salva-scheda-progetto-request';
import { SchedaProgettoService } from '../../services/scheda-progetto.service';
import { BeneficiarioProgettoDialogComponent } from '../beneficiario-progetto-dialog/beneficiario-progetto-dialog.component';
import { RapprLegaleProgettoDialogComponent } from '../rappr-legale-progetto-dialog/rappr-legale-progetto-dialog.component';
import { SedeInterventoDialogComponent } from '../sede-intervento-dialog/sede-intervento-dialog.component';
import { DatiSif } from '../../commons/dto/dati-sif';

@Component({
  selector: 'app-progetto',
  templateUrl: './progetto.component.html',
  styleUrls: ['./progetto.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ProgettoComponent implements OnInit, AfterViewChecked {

  user: UserInfoSec;
  idLinea: number;
  idProgetto: number;
  lineaFinanziamento: string;
  idProcesso: number;
  combo: InizializzaComboDTO;
  datiGen: DatiGeneraliProgetto = new DatiGeneraliProgetto();
  beneficiario: BeneficiarioProgetto = new BeneficiarioProgetto();
  rappresentante: RappresentanteLegaleProgetto = new RappresentanteLegaleProgetto();
  flagSalvaIntermediario: string;
  idLineaAsse: string;
  idLineaNormativa: string;
  intermediario: SoggettoGenerico;
  altriSoggetti: Array<SoggettoGenerico>;
  altroSoggettoDefault: SoggettoGenerico;
  isBandoSif: boolean;

  errorNecessarioAvvio: string = "Campo necessario per avviare il progetto";

  attivitaAtecoDatiGen: Array<CodiceDescrizione>;
  attivitaAtecoBen: Array<CodiceDescrizione>;
  prioritaQsn: Array<CodiceDescrizione>;
  obiettiviGenQsn: Array<CodiceDescrizione>;
  obiettiviSpecQsn: Array<CodiceDescrizione>;
  sottoSettoriCipe: Array<CodiceDescrizione>;
  categorieCipe: Array<CodiceDescrizione>;
  tipologieCipe: Array<CodiceDescrizione>;
  classificazioniRA: Array<CodiceDescrizione>;
  regioniNascitaBen: Array<CodiceDescrizione>;
  provinceNascitaBen: Array<CodiceDescrizione>;
  comuniNascitaBen: Array<CodiceDescrizione>;
  comuniNascitaEsteriBen: Array<CodiceDescrizione>;

  regioniResidenzaBen: Array<CodiceDescrizione>;
  provinceResidenzaBen: Array<CodiceDescrizione>;
  comuniResidenzaBen: Array<CodiceDescrizione>;
  comuniResidenzaEsteriBen: Array<CodiceDescrizione>;

  regioniSedeLegaleBen: Array<CodiceDescrizione>;
  provinceSedeLegaleBen: Array<CodiceDescrizione>;
  comuniSedeLegaleBen: Array<CodiceDescrizione>;
  comuniSedeLegaleEsteriBen: Array<CodiceDescrizione>;

  regioniSedeLegaleBenDR: Array<CodiceDescrizione>;
  provinceSedeLegaleBenDR: Array<CodiceDescrizione>;
  comuniSedeLegaleBenDR: Array<CodiceDescrizione>;
  comuniSedeLegaleEsteriBenDR: Array<CodiceDescrizione>;

  regioniSedeLegaleBenPA: Array<CodiceDescrizione>;
  provinceSedeLegaleBenPA: Array<CodiceDescrizione>;
  comuniSedeLegaleBenPA: Array<CodiceDescrizione>;
  comuniSedeLegaleEsteriBenPA: Array<CodiceDescrizione>;

  regioniNascitaRappr: Array<CodiceDescrizione>;
  provinceNascitaRappr: Array<CodiceDescrizione>;
  comuniNascitaRappr: Array<CodiceDescrizione>;
  comuniNascitaEsteriRappr: Array<CodiceDescrizione>;

  regioniResidenzaRappr: Array<CodiceDescrizione>;
  provinceResidenzaRappr: Array<CodiceDescrizione>;
  comuniResidenzaRappr: Array<CodiceDescrizione>;
  comuniResidenzaEsteriRappr: Array<CodiceDescrizione>;

  settoriDR: Array<CodiceDescrizione>;
  settoriPA: Array<CodiceDescrizione>;
  dipartimenti: Array<CodiceDescrizione>;
  isCfConfirmedBen: boolean;
  isCfConfirmedRappr: boolean;

  today: Date;

  sedeInterventoDefault: SedeIntervento;
  sediIntervento: Array<SedeIntervento>;
  dataSource: MatTableDataSource<SedeIntervento>;
  displayedColumns: string[] = ['piva', 'indirizzo', 'email', 'telefono', 'fax', 'azioni'];

  datiSif: DatiSif = new DatiSif();
  isProgrammazione2127: boolean;

  @ViewChild("paginator", { static: true }) paginator: MatPaginator;


  @ViewChild('tabs', { static: false }) tabs;
  @ViewChild('soggettiForm', { static: true }) soggettiForm: NgForm;
  @ViewChild('datiGeneraliForm', { static: true }) datiGeneraliForm: NgForm;
  @ViewChild('datiSifForm', { static: true }) datiSifForm: NgForm;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializzaSchedaProgetto: boolean;
  loadedInizializzaCombo: boolean;
  loadedCombo: boolean = true;
  loadedCarica: boolean = true;
  loadedConferma: boolean = true;
  loadedVerificaNumDomanda: boolean = true;
  loadedVerificaCup: boolean = true;
  loadedSalva: boolean = true;
  loadedIsSif: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private schedaProgettoService: SchedaProgettoService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.paramMap.get('idLinea')) {
      this.idLinea = +this.activatedRoute.snapshot.paramMap.get('idLinea'); // = PROGR_BANDO_LINEA_INTERVENTO
    }
    if (this.activatedRoute.snapshot.paramMap.get('idProgetto')) {
      this.idProgetto = +this.activatedRoute.snapshot.paramMap.get('idProgetto');
    }
    if (this.activatedRoute.snapshot.paramMap.get('messaggio')) {
      this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('messaggio'));
    }
    this.today = new Date();
    this.today.setHours(0, 0, 0, 0);
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.loadedInizializzaSchedaProgetto = false;
        this.subscribers.init = this.schedaProgettoService.inizializzaSchedaProgetto(this.idLinea, this.idProgetto, this.user.idSoggetto, this.user.codiceRuolo).subscribe(init => {
          if (init) {
            this.lineaFinanziamento = init.nomeBandoLinea;
            this.idProcesso = init.idProcesso;
            this.prioritaQsn = init.comboPrioritaQsn;
            this.isProgrammazione2127 = init.isProgrammazione2127;
            this.loadedInizializzaCombo = false;
            this.subscribers.combo = this.schedaProgettoService.inizializzaCombo(this.idLinea).subscribe(data => {
              if (data) {
                this.combo = data;
                this.setData(init.schedaProgetto);
              }
              this.loadedInizializzaCombo = true;
            }, err => {
              this.handleExceptionService.handleBlockingError(err);
            });
          }
          this.loadedInizializzaSchedaProgetto = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
        this.loadedIsSif = false;
        this.userService.isBandoSif(this.idLinea).subscribe(data => {
          this.isBandoSif = data;
          this.loadedIsSif = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica bando SIF.");
          this.loadedIsSif = true;
        });
      }
    });
  }

  setData(schedaProgetto: SchedaProgetto) {
    this.flagSalvaIntermediario = schedaProgetto.flagSalvaIntermediario;
    this.idLineaAsse = schedaProgetto.idLineaAsse;
    this.idLineaNormativa = schedaProgetto.idLineaNormativa;
    this.intermediario = schedaProgetto.intermediario;
    this.altriSoggetti = schedaProgetto.altriSoggetti;
    this.altroSoggettoDefault = schedaProgetto.altroSoggettoDefault;
    this.setDatiGenerali(schedaProgetto.informazioniBase);
    this.setBeneficiario(schedaProgetto.beneficiario);
    this.setRappresentante(schedaProgetto.rappresentanteLegale);
    this.sedeInterventoDefault = schedaProgetto.sedeInterventoDefault;
    this.sediIntervento = schedaProgetto.sediIntervento;
    this.dataSource = new MatTableDataSource<SedeIntervento>(this.sediIntervento);
    this.paginator.length = this.sediIntervento.length;
    this.paginator.pageIndex = 0;
    this.dataSource.paginator = this.paginator;
    if (this.isBandoSif) {
      this.datiSif.dataFirmaAccordo.setValue(schedaProgetto.informazioniBase.dtFirmaAccordo ? new Date(schedaProgetto.informazioniBase.dtFirmaAccordo) : null);
      this.datiSif.dataComplValut.setValue(schedaProgetto.informazioniBase.dtCompletamentoValutazione ? new Date(schedaProgetto.informazioniBase.dtCompletamentoValutazione) : null);
    }
  }

  setDatiGenerali(infoBase: InformazioniBase) {
    this.datiGen.cup = infoBase.cup;
    this.datiGen.titoloProgetto = infoBase.titoloProgetto;
    this.datiGen.codProgettoNumDomanda = infoBase.numeroDomanda;
    if (infoBase.dataPresentazioneDomanda) {
      this.datiGen.dataPresentazioneDomanda.setValue(new Date(this.sharedService.parseDate(infoBase.dataPresentazioneDomanda)));
    }
    if (infoBase.dataComitato) {
      this.datiGen.dataComitato.setValue(new Date(this.sharedService.parseDate(infoBase.dataComitato)));
    }
    if (infoBase.dataConcessione) {
      this.datiGen.dataConcessione.setValue(new Date(this.sharedService.parseDate(infoBase.dataConcessione)));
    }
    this.datiGen.annoConcessioneOld = infoBase.annoConcessioneOld; //va modificato???
    this.datiGen.dataDecorrenza = infoBase.dataDecorrenza;
    this.datiGen.dataGenerazione = infoBase.dataGenerazione;
    if (infoBase.idSettoreAttivita) {
      this.datiGen.settoreAttivita = this.combo.comboSettoreAttivita.find(s => s.codice === infoBase.idSettoreAttivita);
      this.changeSettoreAttivitaDatiGen(infoBase.idAttivitaAteco);
    }
    if (infoBase.idTipoOperazione) {
      this.datiGen.tipoOperazione = this.combo.comboTipoOperazione.find(s => s.codice === infoBase.idTipoOperazione);
    }
    if (infoBase.idPrioritaQsn) {
      this.datiGen.prioritaQsn = this.prioritaQsn.find(s => s.codice === infoBase.idPrioritaQsn);
      this.changePrioritaQsn(infoBase.idObiettivoGeneraleQsn, infoBase.idObiettivoSpecificoQsn);
    }
    if (infoBase.idStrumentoAttuativo) {
      this.datiGen.strumento = this.combo.comboStrumentoAttuativo.find(s => s.codice === infoBase.idStrumentoAttuativo);
    }
    if (infoBase.idSettoreCpt) {
      this.datiGen.settoreCpt = this.combo.comboSettoreCpt.find(s => s.codice === infoBase.idSettoreCpt);
    }
    if (infoBase.idTemaPrioritario) {
      this.datiGen.temaPrioritario = this.combo.comboTemaPrioritario.find(s => s.codice === infoBase.idTemaPrioritario);
    }
    if (infoBase.idIndicatoreRisultatoProgramma) {
      this.datiGen.indicatoreRisProg = this.combo.comboIndicatoreRisultatoProgramma.find(s => s.codice === infoBase.idIndicatoreRisultatoProgramma);
    }
    if (infoBase.idIndicatoreQsn) {
      this.datiGen.indicatoreQsn = this.combo.comboIndicatoreQsn.find(s => s.codice === infoBase.idIndicatoreQsn);
    }
    if (infoBase.idTipoAiuto) {
      this.datiGen.tipoAiuto = this.combo.comboTipoAiuto.find(s => s.codice === infoBase.idTipoAiuto);
    }
    if (infoBase.idTipoStrumentoProgrammazione) {
      this.datiGen.tipoStrumentoProg = this.combo.comboStrumentoProgrammazione.find(s => s.codice === infoBase.idTipoStrumentoProgrammazione);
    }
    if (infoBase.idDimensioneTerritoriale) {
      this.datiGen.dimTerritoriale = this.combo.comboDimensioneTerritoriale.find(s => s.codice === infoBase.idDimensioneTerritoriale);
    }
    if (infoBase.idProgettoComplesso) {
      this.datiGen.progettoComplesso = this.combo.comboProgettoComplesso.find(s => s.codice === infoBase.idProgettoComplesso);
    }
    if (infoBase.idObiettivoTematico) {
      this.datiGen.obiettivoTematico = this.combo.comboObiettivoTematico.find(s => s.codice === infoBase.idObiettivoTematico);
      this.changeObiettivoTematico(infoBase.idClassificazioneRA);
    }
    if (infoBase.idSettoreCipe) {
      this.datiGen.settoreCipe = this.combo.comboSettoreCipe.find(s => s.codice === infoBase.idSettoreCipe);
      this.changeSettoreCipe(infoBase.idSottoSettoreCipe, infoBase.idCategoriaCipe);
    }
    if (infoBase.idGrandeProgetto) {
      this.datiGen.grandeProgetto = this.combo.comboGrandeProgetto.find(s => s.codice === infoBase.idGrandeProgetto);
    }
    if (infoBase.idNaturaCipe) {
      this.datiGen.naturaCipe = this.combo.comboNaturaCipe.find(s => s.codice === infoBase.idNaturaCipe);
      this.changeNaturaCipe(infoBase.idTipologiaCipe)
    }
    this.datiGen.flagCardine = infoBase.flagCardine;
    this.datiGen.flagGeneratoreEntrate = infoBase.flagGeneratoreEntrate;
    this.datiGen.flagLeggeObiettivo = infoBase.flagLeggeObiettivo;
    this.datiGen.flagAltroFondo = infoBase.flagAltroFondo;
    this.datiGen.flagStatoProgettoProgramma = infoBase.flagStatoProgettoProgramma;
    this.datiGen.flagDettaglioCup = infoBase.flagDettaglioCup;
    this.datiGen.flagAggiungiCup = infoBase.flagAggiungiCup;
    this.datiGen.flagBeneficiarioCup = infoBase.flagBeneficiarioCup;
    this.datiGen.flagProgettoDaInviareAlMonitoraggio = infoBase.flagProgettoDaInviareAlMonitoraggio;
    this.datiGen.flagRichiestaAutomaticaDelCup = infoBase.flagRichiestaAutomaticaDelCup;
    this.datiGen.ppp = infoBase.flagPPP?.length > 0 ? infoBase.flagPPP : "N";
    this.datiGen.flagImportanzaStrategica = infoBase.flagStrategico?.length > 0 ? infoBase.flagStrategico : "N";

  }

  setBeneficiario(ben: SoggettoGenerico) {
    this.beneficiario.id = ben.id;
    this.beneficiario.tipoSoggetto = ben.flagPersonaFisica === "S" ? "PF" : "EG";
    this.setBenPF(ben.datiPF);
    this.setBenEG(ben.datiPG);
  }

  setBenPF(sogg: SoggettoPF) {
    this.beneficiario.codiceFiscalePF = sogg.codiceFiscale ? sogg.codiceFiscale.toUpperCase() : null;
    if (sogg.ruolo && sogg.ruolo.length > 0) {
      for (let ruolo of sogg.ruolo) {
        let ruoloChecked = this.combo.comboRuoli.find(r => r.codice === ruolo);
        ruoloChecked.checked = true;
        this.beneficiario.ruoli.push(ruoloChecked);
      }
    }
    this.beneficiario.cognome = sogg.cognome;
    this.beneficiario.nome = sogg.nome;
    this.beneficiario.sesso = sogg.sesso ? sogg.sesso : "M";
    this.beneficiario.iban = sogg.iban;
    if (sogg.dataNascita) {
      this.beneficiario.dataNascita.setValue(new Date(this.sharedService.parseDate(sogg.dataNascita)));
    }
    if (sogg.comuneNas.idNazione) {
      this.beneficiario.nazioneNascita = this.combo.comboNazioneNascita.find(n => n.id === sogg.comuneNas.idNazione);
      this.changeNazioneNascitaBen(sogg.comuneNas.idRegione, sogg.comuneNas.idProvincia, sogg.comuneNas.idComune);
    }
    this.beneficiario.indirizzoResidenza = sogg.indirizzoRes;
    this.beneficiario.civicoResidenza = sogg.numCivicoRes;
    this.beneficiario.capResidenza = sogg.capRes;
    if (sogg.comuneRes.idNazione) {
      this.beneficiario.nazioneResidenza = this.combo.comboNazione.find(n => n.id === sogg.comuneRes.idNazione);
      this.changeNazioneResidenzaBen(sogg.comuneRes.idRegione, sogg.comuneRes.idProvincia, sogg.comuneRes.idComune);
    }
    this.beneficiario.email = sogg.emailRes;
    this.beneficiario.fax = sogg.faxRes;
    this.beneficiario.telefono = sogg.telefonoRes;
  }

  setBenEG(sogg: SoggettoPG) {
    this.beneficiario.tipoSoggGiur = sogg.tipoDipDir;
    switch (this.beneficiario.tipoSoggGiur) {
      case "DU":
        this.setTabsBenEG(sogg.tabDipUni);
        break;
      case "DR":
        this.setTabsBenEG(sogg.tabDirReg);
        break;
      case "PA":
        this.setTabsBenEG(sogg.tabPA);
        break;
      case "NA":
        this.setTabsBenEG(sogg.tabAltro);
        break;
      default:
        break;
    }
  }

  setTabsBenEG(sogg: TabDirRegSogg) {
    this.beneficiario.codiceFiscaleEG = sogg.codiceFiscale ? sogg.codiceFiscale.toUpperCase() : null;
    if (this.beneficiario.codiceFiscaleEG) {
      this.isCfConfirmedBen = true;
    }
    if (sogg.ateneo) {
      this.beneficiario.ateneo = this.combo.comboAteneo.find(e => e.codice === sogg.ateneo);
      this.changeAteneo(sogg.denominazioneEnteDipUni);

    }
    if (sogg.denominazioneEnteDirReg) {
      this.beneficiario.enteDR = this.combo.comboDenominazioneEnteDipReg.find(e => e.codice === sogg.denominazioneEnteDirReg);
      this.changeEnteDR(false, sogg.idSettore);
    }
    if (sogg.denominazioneEntePA) {
      this.beneficiario.entePA = this.combo.comboDenominazioneEntePA.find(e => e.codice === sogg.denominazioneEntePA);
      this.changeEntePA(false, sogg.idSettore);
    }
    this.beneficiario.ruoli = [];
    if (sogg.ruolo && sogg.ruolo.length > 0) {
      for (let ruolo of sogg.ruolo) {
        let ruoloChecked = this.combo.comboRuoli.find(r => r.codice === ruolo);
        ruoloChecked.checked = true;
        this.beneficiario.ruoli.push(ruoloChecked);
      }
    }
    if (sogg.dataCostituzioneAzienda) {
      this.beneficiario.dataCostituzione.setValue(new Date(this.sharedService.parseDate(sogg.dataCostituzioneAzienda)));
    }
    this.beneficiario.denominazione = sogg.denominazione;
    if (sogg.formaGiuridica) {
      this.beneficiario.formaGiuridica = this.combo.comboFormaGiuridica.find(f => f.codice === sogg.formaGiuridica);
    }
    this.beneficiario.iban = sogg.iban;
    if (sogg.sedeLegale.idNazione) {
      let n = this.combo.comboNazione.find(n => n.id === sogg.sedeLegale.idNazione);
      if (this.beneficiario.tipoSoggGiur === "DU" || this.beneficiario.tipoSoggGiur === "NA") {
        this.beneficiario.partitaIva = sogg.partitaIvaSedeLegale;
        this.beneficiario.indirizzoSedeLegale = sogg.indirizzoSedeLegale;
        this.beneficiario.civicoSedeLegale = sogg.numCivicoSedeLegale;
        this.beneficiario.capSedeLegale = sogg.capSedeLegale;
        this.beneficiario.emailSedeLegale = sogg.emailSedeLegale;
        this.beneficiario.pecSedeLegale = sogg.pecSedeLegale;
        this.beneficiario.telefonoSedeLegale = sogg.telefonoSedeLegale;
        this.beneficiario.faxSedeLegale = sogg.faxSedeLegale;
        this.beneficiario.nazioneSedeLegale = n;
        this.changeNazioneSedeLegale(sogg.sedeLegale.idRegione, sogg.sedeLegale.idProvincia, sogg.sedeLegale.idComune);
      } else if (this.beneficiario.tipoSoggGiur === "DR") {
        this.beneficiario.partitaIvaDR = sogg.partitaIvaSedeLegale;
        this.beneficiario.indirizzoSedeLegaleDR = sogg.indirizzoSedeLegale;
        this.beneficiario.civicoSedeLegaleDR = sogg.numCivicoSedeLegale;
        this.beneficiario.capSedeLegaleDR = sogg.capSedeLegale;
        this.beneficiario.emailSedeLegaleDR = sogg.emailSedeLegale;
        this.beneficiario.pecSedeLegaleDR = sogg.pecSedeLegale;
        this.beneficiario.telefonoSedeLegaleDR = sogg.telefonoSedeLegale;
        this.beneficiario.faxSedeLegaleDR = sogg.faxSedeLegale;
        this.beneficiario.nazioneSedeLegaleDR = n;
        this.changeNazioneSedeLegaleDR(sogg.sedeLegale.idRegione, sogg.sedeLegale.idProvincia, sogg.sedeLegale.idComune);
      } else if (this.beneficiario.tipoSoggGiur === "PA") {
        this.beneficiario.partitaIvaPA = sogg.partitaIvaSedeLegale;
        this.beneficiario.indirizzoSedeLegalePA = sogg.indirizzoSedeLegale;
        this.beneficiario.civicoSedeLegalePA = sogg.numCivicoSedeLegale;
        this.beneficiario.capSedeLegalePA = sogg.capSedeLegale;
        this.beneficiario.emailSedeLegalePA = sogg.emailSedeLegale;
        this.beneficiario.pecSedeLegalePA = sogg.pecSedeLegale;
        this.beneficiario.telefonoSedeLegalePA = sogg.telefonoSedeLegale;
        this.beneficiario.faxSedeLegalePA = sogg.faxSedeLegale;
        this.beneficiario.nazioneSedeLegalePA = n;
        this.changeNazioneSedeLegalePA(sogg.sedeLegale.idRegione, sogg.sedeLegale.idProvincia, sogg.sedeLegale.idComune);
      }
    }
    if (sogg.settoreAttivita) {
      this.beneficiario.settoreAttivita = this.combo.comboSettoreAttivita.find(s => s.codice === sogg.settoreAttivita);
      this.changeSettoreAttivitaBen(sogg.attivitaAteco);
    }
  }

  setRappresentante(rappr: SoggettoGenerico) {
    this.rappresentante.id = rappr.id;
    this.rappresentante.codiceFiscale = rappr.datiPF.codiceFiscale ? rappr.datiPF.codiceFiscale.toUpperCase() : null;
    if (this.rappresentante.codiceFiscale) {
      this.isCfConfirmedRappr = true;
    }
    this.rappresentante.cognome = rappr.datiPF.cognome;
    this.rappresentante.nome = rappr.datiPF.nome;
    this.rappresentante.sesso = rappr.datiPF.sesso ? rappr.datiPF.sesso : "M";
    if (rappr.datiPF.dataNascita) {
      this.rappresentante.dataNascita.setValue(rappr.datiPF.dataNascita ? this.sharedService.parseDate(rappr.datiPF.dataNascita) : null);
    }
    if (rappr.datiPF.comuneNas.idNazione) {
      this.rappresentante.nazioneNascita = this.combo.comboNazioneNascita.find(n => n.id === rappr.datiPF.comuneNas.idNazione);
      this.changeNazioneNascitaRappr(rappr.datiPF.comuneNas.idRegione, rappr.datiPF.comuneNas.idProvincia, rappr.datiPF.comuneNas.idComune);
    }
    this.rappresentante.indirizzoResidenza = rappr.datiPF.indirizzoRes;
    this.rappresentante.civicoResidenza = rappr.datiPF.numCivicoRes;
    this.rappresentante.capResidenza = rappr.datiPF.capRes;
    if (rappr.datiPF.comuneRes.idNazione) {
      this.rappresentante.nazioneResidenza = this.combo.comboNazione.find(n => n.id === rappr.datiPF.comuneRes.idNazione);
      this.changeNazioneResidenzaRappr(rappr.datiPF.comuneRes.idRegione, rappr.datiPF.comuneRes.idProvincia, rappr.datiPF.comuneRes.idComune);
    }
    this.rappresentante.email = rappr.datiPF.emailRes;
    this.rappresentante.fax = rappr.datiPF.faxRes;
    this.rappresentante.telefono = rappr.datiPF.telefonoRes;
  }

  changeSettoreAttivitaDatiGen(idAttivitaAteco?: string) {
    this.attivitaAtecoDatiGen = null;
    this.datiGen.attivitaAteco = null;
    if (this.datiGen.settoreAttivita) {
      this.loadedCombo = false;
      this.subscribers.attivitaAteco = this.schedaProgettoService.popolaComboAttivitaAteco(+this.datiGen.settoreAttivita.codice).subscribe(data => {
        if (data) {
          this.attivitaAtecoDatiGen = data;
          if (idAttivitaAteco) {
            this.datiGen.attivitaAteco = this.attivitaAtecoDatiGen.find(s => s.codice === idAttivitaAteco);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle attività Ateco.");
        this.loadedCombo = true;
      });
    }
  }

  changeSettoreAttivitaBen(idAttivitaAteco?: string) {
    this.attivitaAtecoBen = null;
    this.beneficiario.attivitaAteco = null;
    if (this.beneficiario.settoreAttivita) {
      this.loadedCombo = false;
      this.subscribers.attivitaAteco = this.schedaProgettoService.popolaComboAttivitaAteco(+this.beneficiario.settoreAttivita.codice).subscribe(data => {
        if (data) {
          this.attivitaAtecoBen = data;
          if (idAttivitaAteco) {
            this.beneficiario.attivitaAteco = this.attivitaAtecoBen.find(s => s.codice === idAttivitaAteco);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle attività Ateco.");
        this.loadedCombo = true;
      });
    }
  }

  changePrioritaQsn(idObiettivoGeneraleQsn?: string, idObiettivoSpecificoQsn?: string) {
    this.obiettiviGenQsn = null;
    this.datiGen.obiettivoGenQsn = null;
    this.obiettiviSpecQsn = null;
    this.datiGen.obiettivoSpecQsn = null;
    if (this.datiGen.prioritaQsn) {
      this.loadedCombo = false;
      this.subscribers.obiettiviGenQsn = this.schedaProgettoService.popolaComboObiettivoGeneraleQsn(+this.datiGen.prioritaQsn.codice).subscribe(data => {
        if (data) {
          this.obiettiviGenQsn = data;
          if (idObiettivoGeneraleQsn) {
            this.datiGen.obiettivoGenQsn = this.obiettiviGenQsn.find(s => s.codice === idObiettivoGeneraleQsn);
            this.changeObiettivoGenQsn(idObiettivoSpecificoQsn);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero degli obiettivi generali Qsn.");
        this.loadedCombo = true;
      });
    }
  }

  changeObiettivoGenQsn(idObiettivoSpecificoQsn?: string) {
    this.obiettiviSpecQsn = null;
    this.datiGen.obiettivoSpecQsn = null;
    if (this.datiGen.obiettivoGenQsn) {
      this.loadedCombo = false;
      this.subscribers.obiettiviSpecQsn = this.schedaProgettoService.popolaComboObiettivoSpecificoQsn(+this.datiGen.obiettivoGenQsn.codice).subscribe(data => {
        if (data) {
          this.obiettiviSpecQsn = data;
          if (idObiettivoSpecificoQsn) {
            this.datiGen.obiettivoSpecQsn = this.obiettiviSpecQsn.find(s => s.codice === idObiettivoSpecificoQsn);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero degli obiettivi specifici Qsn.");
        this.loadedCombo = true;
      });
    }
  }

  changeObiettivoTematico(idClassificazioneRA?: string) {
    this.classificazioniRA = null;
    this.datiGen.classificazioneRA = null;
    if (this.datiGen.obiettivoTematico) {
      this.loadedCombo = false;
      this.subscribers.classificazioniRA = this.schedaProgettoService.popolaComboClassificazioneRA(+this.datiGen.obiettivoTematico.codice).subscribe(data => {
        if (data) {
          this.classificazioniRA = data;
          if (idClassificazioneRA) {
            this.datiGen.classificazioneRA = this.classificazioniRA.find(s => s.codice === idClassificazioneRA);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle classificazioni RA.");
        this.loadedCombo = true;
      });
    }
  }

  changeSettoreCipe(idSottoSettoreCipe?: string, idCategoriaCipe?: string) {
    this.sottoSettoriCipe = null;
    this.datiGen.sottoSettoreCipe = null;
    this.categorieCipe = null;
    this.datiGen.categoriaCipe = null;
    if (this.datiGen.settoreCipe) {
      this.loadedCombo = false;
      this.subscribers.sottoSettoriCipe = this.schedaProgettoService.popolaComboSottoSettoreCipe(+this.datiGen.settoreCipe.codice).subscribe(data => {
        if (data) {
          this.sottoSettoriCipe = data;
          if (idSottoSettoreCipe) {
            this.datiGen.sottoSettoreCipe = this.sottoSettoriCipe.find(s => s.codice === idSottoSettoreCipe);
            this.changeSottoSettoreCipe(idCategoriaCipe);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei sotto-settori CIPE.");
        this.loadedCombo = true;
      });
    }
  }

  changeSottoSettoreCipe(idCategoriaCipe?: string) {
    this.categorieCipe = null;
    this.datiGen.categoriaCipe = null;
    if (this.datiGen.sottoSettoreCipe) {
      this.loadedCombo = false;
      this.subscribers.categoriaCipe = this.schedaProgettoService.popolaComboCategoriaCipe(+this.datiGen.sottoSettoreCipe.codice).subscribe(data => {
        if (data) {
          this.categorieCipe = data;
          if (idCategoriaCipe) {
            this.datiGen.categoriaCipe = this.categorieCipe.find(s => s.codice === idCategoriaCipe);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle categorie CIPE.");
        this.loadedCombo = true;
      });
    }
  }

  changeNaturaCipe(idTipologiaCipe?: string) {
    this.tipologieCipe = null;
    this.datiGen.tipologiaCipe = null;
    if (this.datiGen.naturaCipe) {
      this.loadedCombo = false;
      this.subscribers.tipologieCipe = this.schedaProgettoService.popolaComboTipologiaCipe(+this.datiGen.naturaCipe.codice).subscribe(data => {
        if (data) {
          this.tipologieCipe = data;
          if (idTipologiaCipe) {
            this.datiGen.tipologiaCipe = this.tipologieCipe.find(s => s.codice === idTipologiaCipe);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle tipologie CIPE.");
        this.loadedCombo = true;
      });
    }
  }

  changeRuoli(event: MatCheckboxChange, ruolo: CodiceDescrizione) {
    if (event.checked) {
      this.beneficiario.ruoli.push(ruolo);
    } else {
      this.beneficiario.ruoli = this.beneficiario.ruoli.filter(r => r.codice !== ruolo.codice);
    }
  }

  changeNazioneNascitaBen(idRegioneNascita?: string, idProvinciaNascita?: string, idComuneNascita?: string) {
    this.regioniNascitaBen = null;
    this.beneficiario.regioneNascita = null;
    this.provinceNascitaBen = null;
    this.beneficiario.provinciaNascita = null;
    this.comuniNascitaBen = null;
    this.comuniNascitaEsteriBen = null;
    this.beneficiario.comuneNascita = null;
    if (this.beneficiario.nazioneNascita) {
      if (this.beneficiario.nazioneNascita.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniNascitaBen = this.schedaProgettoService.popolaComboRegioneNascita().subscribe(data => {
          if (data) {
            this.regioniNascitaBen = data;
            if (idRegioneNascita) {
              this.beneficiario.regioneNascita = this.regioniNascitaBen.find(s => s.codice === idRegioneNascita);
              this.changeRegioneNascitaBen(idProvinciaNascita, idComuneNascita);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni di nascita.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniNascitaEsteriBen = this.schedaProgettoService.popolaComboComuneEsteroNascita(+this.beneficiario.nazioneNascita.id).subscribe(data => {
          if (data) {
            this.comuniNascitaEsteriBen = data;
            if (idComuneNascita) {
              this.beneficiario.comuneNascita = this.comuniNascitaEsteriBen.find(s => s.codice === idComuneNascita);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri di nascita.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneNascitaBen(idProvinciaNascita?: string, idComuneNascita?: string) {
    this.provinceNascitaBen = null;
    this.beneficiario.provinciaNascita = null;
    this.comuniNascitaBen = null;
    this.comuniNascitaEsteriBen = null;
    this.beneficiario.comuneNascita = null;
    if (this.beneficiario.regioneNascita) {
      this.loadedCombo = false;
      this.subscribers.provinceNascitaBen = this.schedaProgettoService.popolaComboProvinciaNascita(+this.beneficiario.regioneNascita.codice).subscribe(data => {
        if (data) {
          this.provinceNascitaBen = data;
          if (idProvinciaNascita) {
            this.beneficiario.provinciaNascita = this.provinceNascitaBen.find(s => s.codice === idProvinciaNascita);
            this.changeProvinciaNascitaBen(idComuneNascita);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province di nascita.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaNascitaBen(idComuneNascita?: string) {
    this.comuniNascitaBen = null;
    this.comuniNascitaEsteriBen = null;
    this.beneficiario.comuneNascita = null;
    if (this.beneficiario.provinciaNascita) {
      this.loadedCombo = false;
      this.subscribers.comuniNascitaBen = this.schedaProgettoService.popolaComboComuneItalianoNascita(+this.beneficiario.provinciaNascita.codice).subscribe(data => {
        if (data) {
          this.comuniNascitaBen = data;
          if (idComuneNascita) {
            this.beneficiario.comuneNascita = this.comuniNascitaBen.find(s => s.codice === idComuneNascita);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni di nascita.");
        this.loadedCombo = true;
      });
    }
  }

  changeNazioneResidenzaBen(idRegioneResidenza?: string, idProvinciaResidenza?: string, idComuneResidenza?: string) {
    this.regioniResidenzaBen = null;
    this.beneficiario.regioneResidenza = null;
    this.provinceResidenzaBen = null;
    this.beneficiario.provinciaResidenza = null;
    this.comuniResidenzaBen = null;
    this.comuniResidenzaEsteriBen = null;
    this.beneficiario.comuneResidenza = null;
    if (this.beneficiario.nazioneResidenza) {
      if (this.beneficiario.nazioneResidenza.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniResidenzaBen = this.schedaProgettoService.popolaComboRegione().subscribe(data => {
          if (data) {
            this.regioniResidenzaBen = data;
            if (idRegioneResidenza) {
              this.beneficiario.regioneResidenza = this.regioniResidenzaBen.find(s => s.codice === idRegioneResidenza);
              this.changeRegioneResidenzaBen(idProvinciaResidenza, idComuneResidenza);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni di residenza.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniResidenzaEsteriBen = this.schedaProgettoService.popolaComboComuneEstero(+this.beneficiario.nazioneResidenza.id).subscribe(data => {
          if (data) {
            this.comuniResidenzaEsteriBen = data;
            if (idComuneResidenza) {
              this.beneficiario.comuneResidenza = this.comuniResidenzaEsteriBen.find(s => s.codice === idComuneResidenza);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri di residenza.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneResidenzaBen(idProvinciaResidenza?: string, idComuneResidenza?: string) {
    this.provinceResidenzaBen = null;
    this.beneficiario.provinciaResidenza = null;
    this.comuniResidenzaBen = null;
    this.comuniResidenzaEsteriBen = null;
    this.beneficiario.comuneResidenza = null;
    if (this.beneficiario.regioneResidenza) {
      this.loadedCombo = false;
      this.subscribers.provinceResidenzaBen = this.schedaProgettoService.popolaComboProvincia(+this.beneficiario.regioneResidenza.codice).subscribe(data => {
        if (data) {
          this.provinceResidenzaBen = data;
          if (idProvinciaResidenza) {
            this.beneficiario.provinciaResidenza = this.provinceResidenzaBen.find(s => s.codice === idProvinciaResidenza);
            this.changeProvinciaResidenzaBen(idComuneResidenza);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province di residenza.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaResidenzaBen(idComuneResidenza?: string) {
    this.comuniResidenzaBen = null;
    this.comuniResidenzaEsteriBen = null;
    this.beneficiario.comuneResidenza = null;
    if (this.beneficiario.provinciaResidenza) {
      this.loadedCombo = false;
      this.subscribers.comuniResidenzaBen = this.schedaProgettoService.popolaComboComuneItaliano(+this.beneficiario.provinciaResidenza.codice).subscribe(data => {
        if (data) {
          this.comuniResidenzaBen = data;
          if (idComuneResidenza) {
            this.beneficiario.comuneResidenza = this.comuniResidenzaBen.find(s => s.codice === idComuneResidenza);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni di residenza.");
        this.loadedCombo = true;
      });
    }
  }

  changeNazioneSedeLegale(idRegioneSedeLegale?: string, idProvinciaSedeLegale?: string, idComuneSedeLegale?: string) {
    this.regioniSedeLegaleBen = null;
    this.beneficiario.regioneSedeLegale = null;
    this.provinceSedeLegaleBen = null;
    this.beneficiario.provinciaSedeLegale = null;
    this.comuniSedeLegaleBen = null;
    this.comuniSedeLegaleEsteriBen = null;
    this.beneficiario.comuneSedeLegale = null;
    if (this.beneficiario.nazioneSedeLegale) {
      if (this.beneficiario.nazioneSedeLegale.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniSedeLegaleBen = this.schedaProgettoService.popolaComboRegione().subscribe(data => {
          if (data) {
            this.regioniSedeLegaleBen = data;
            if (idRegioneSedeLegale) {
              this.beneficiario.regioneSedeLegale = this.regioniSedeLegaleBen.find(s => s.codice === idRegioneSedeLegale);
              this.changeRegioneSedeLegale(idProvinciaSedeLegale, idComuneSedeLegale);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni della sede legale.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniSedeLegaleEsteriBen = this.schedaProgettoService.popolaComboComuneEstero(+this.beneficiario.nazioneSedeLegale.id).subscribe(data => {
          if (data) {
            this.comuniSedeLegaleEsteriBen = data;
            if (idComuneSedeLegale) {
              this.beneficiario.comuneSedeLegale = this.comuniSedeLegaleEsteriBen.find(s => s.codice === idComuneSedeLegale);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri della sede legale.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneSedeLegale(idProvinciaSedeLegale?: string, idComuneSedeLegale?: string) {
    this.provinceSedeLegaleBen = null;
    this.beneficiario.provinciaSedeLegale = null;
    this.comuniSedeLegaleBen = null;
    this.comuniSedeLegaleEsteriBen = null;
    this.beneficiario.comuneSedeLegale = null;
    if (this.beneficiario.regioneSedeLegale) {
      this.loadedCombo = false;
      this.subscribers.provinceSedeLegaleBen = this.schedaProgettoService.popolaComboProvincia(+this.beneficiario.regioneSedeLegale.codice).subscribe(data => {
        if (data) {
          this.provinceSedeLegaleBen = data;
          if (idProvinciaSedeLegale) {
            this.beneficiario.provinciaSedeLegale = this.provinceSedeLegaleBen.find(s => s.codice === idProvinciaSedeLegale);
            this.changeProvinciaSedeLegale(idComuneSedeLegale);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaSedeLegale(idComuneSedeLegale?: string) {
    this.comuniSedeLegaleBen = null;
    this.comuniSedeLegaleEsteriBen = null;
    this.beneficiario.comuneSedeLegale = null;
    if (this.beneficiario.provinciaSedeLegale) {
      this.loadedCombo = false;
      this.subscribers.comuniSedeLegaleBen = this.schedaProgettoService.popolaComboComuneItaliano(+this.beneficiario.provinciaSedeLegale.codice).subscribe(data => {
        if (data) {
          this.comuniSedeLegaleBen = data;
          if (idComuneSedeLegale) {
            this.beneficiario.comuneSedeLegale = this.comuniSedeLegaleBen.find(s => s.codice === idComuneSedeLegale);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeNazioneSedeLegaleDR(idRegioneSedeLegale?: string, idProvinciaSedeLegale?: string, idComuneSedeLegale?: string) {
    this.regioniSedeLegaleBenDR = null;
    this.beneficiario.regioneSedeLegaleDR = null;
    this.provinceSedeLegaleBenDR = null;
    this.beneficiario.provinciaSedeLegaleDR = null;
    this.comuniSedeLegaleBenDR = null;
    this.comuniSedeLegaleEsteriBenDR = null;
    this.beneficiario.comuneSedeLegaleDR = null;
    if (this.beneficiario.nazioneSedeLegaleDR) {
      if (this.beneficiario.nazioneSedeLegaleDR.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniSedeLegaleBenDR = this.schedaProgettoService.popolaComboRegione().subscribe(data => {
          if (data) {
            this.regioniSedeLegaleBenDR = data;
            if (idRegioneSedeLegale) {
              this.beneficiario.regioneSedeLegaleDR = this.regioniSedeLegaleBenDR.find(s => s.codice === idRegioneSedeLegale);
              this.changeRegioneSedeLegaleDR(idProvinciaSedeLegale, idComuneSedeLegale);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni della sede legale.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniSedeLegaleEsteriBenDR = this.schedaProgettoService.popolaComboComuneEstero(+this.beneficiario.nazioneSedeLegaleDR.id).subscribe(data => {
          if (data) {
            this.comuniSedeLegaleEsteriBenDR = data;
            if (idComuneSedeLegale) {
              this.beneficiario.comuneSedeLegaleDR = this.comuniSedeLegaleEsteriBenDR.find(s => s.codice === idComuneSedeLegale);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri della sede legale.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneSedeLegaleDR(idProvinciaSedeLegale?: string, idComuneSedeLegale?: string) {
    this.provinceSedeLegaleBenDR = null;
    this.beneficiario.provinciaSedeLegaleDR = null;
    this.comuniSedeLegaleBenDR = null;
    this.comuniSedeLegaleEsteriBenDR = null;
    this.beneficiario.comuneSedeLegaleDR = null;
    if (this.beneficiario.regioneSedeLegaleDR) {
      this.loadedCombo = false;
      this.subscribers.provinceSedeLegaleBenDR = this.schedaProgettoService.popolaComboProvincia(+this.beneficiario.regioneSedeLegaleDR.codice).subscribe(data => {
        if (data) {
          this.provinceSedeLegaleBenDR = data;
          if (idProvinciaSedeLegale) {
            this.beneficiario.provinciaSedeLegaleDR = this.provinceSedeLegaleBenDR.find(s => s.codice === idProvinciaSedeLegale);
            this.changeProvinciaSedeLegaleDR(idComuneSedeLegale);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaSedeLegaleDR(idComuneSedeLegale?: string) {
    this.comuniSedeLegaleBenDR = null;
    this.comuniSedeLegaleEsteriBenDR = null;
    this.beneficiario.comuneSedeLegaleDR = null;
    if (this.beneficiario.provinciaSedeLegaleDR) {
      this.loadedCombo = false;
      this.subscribers.comuniSedeLegaleBenDR = this.schedaProgettoService.popolaComboComuneItaliano(+this.beneficiario.provinciaSedeLegaleDR.codice).subscribe(data => {
        if (data) {
          this.comuniSedeLegaleBenDR = data;
          if (idComuneSedeLegale) {
            this.beneficiario.comuneSedeLegaleDR = this.comuniSedeLegaleBenDR.find(s => s.codice === idComuneSedeLegale);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeNazioneSedeLegalePA(idRegioneSedeLegale?: string, idProvinciaSedeLegale?: string, idComuneSedeLegale?: string) {
    this.regioniSedeLegaleBenPA = null;
    this.beneficiario.regioneSedeLegalePA = null;
    this.provinceSedeLegaleBenPA = null;
    this.beneficiario.provinciaSedeLegalePA = null;
    this.comuniSedeLegaleBenPA = null;
    this.comuniSedeLegaleEsteriBenPA = null;
    this.beneficiario.comuneSedeLegalePA = null;
    if (this.beneficiario.nazioneSedeLegalePA) {
      if (this.beneficiario.nazioneSedeLegalePA.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniSedeLegaleBenPA = this.schedaProgettoService.popolaComboRegione().subscribe(data => {
          if (data) {
            this.regioniSedeLegaleBenPA = data;
            if (idRegioneSedeLegale) {
              this.beneficiario.regioneSedeLegalePA = this.regioniSedeLegaleBenPA.find(s => s.codice === idRegioneSedeLegale);
              this.changeRegioneSedeLegalePA(idProvinciaSedeLegale, idComuneSedeLegale);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni della sede legale.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniSedeLegaleEsteriBenPA = this.schedaProgettoService.popolaComboComuneEstero(+this.beneficiario.nazioneSedeLegalePA.id).subscribe(data => {
          if (data) {
            this.comuniSedeLegaleEsteriBenPA = data;
            if (idComuneSedeLegale) {
              this.beneficiario.comuneSedeLegalePA = this.comuniSedeLegaleEsteriBenPA.find(s => s.codice === idComuneSedeLegale);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri della sede legale.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneSedeLegalePA(idProvinciaSedeLegale?: string, idComuneSedeLegale?: string) {
    this.provinceSedeLegaleBenPA = null;
    this.beneficiario.provinciaSedeLegalePA = null;
    this.comuniSedeLegaleBenPA = null;
    this.comuniSedeLegaleEsteriBenPA = null;
    this.beneficiario.comuneSedeLegalePA = null;
    if (this.beneficiario.regioneSedeLegalePA) {
      this.loadedCombo = false;
      this.subscribers.provinceSedeLegaleBenPA = this.schedaProgettoService.popolaComboProvincia(+this.beneficiario.regioneSedeLegalePA.codice).subscribe(data => {
        if (data) {
          this.provinceSedeLegaleBenPA = data;
          if (idProvinciaSedeLegale) {
            this.beneficiario.provinciaSedeLegalePA = this.provinceSedeLegaleBenPA.find(s => s.codice === idProvinciaSedeLegale);
            this.changeProvinciaSedeLegalePA(idComuneSedeLegale);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaSedeLegalePA(idComuneSedeLegale?: string) {
    this.comuniSedeLegaleBenPA = null;
    this.comuniSedeLegaleEsteriBenPA = null;
    this.beneficiario.comuneSedeLegalePA = null;
    if (this.beneficiario.provinciaSedeLegalePA) {
      this.loadedCombo = false;
      this.subscribers.comuniSedeLegaleBenPA = this.schedaProgettoService.popolaComboComuneItaliano(+this.beneficiario.provinciaSedeLegalePA.codice).subscribe(data => {
        if (data) {
          this.comuniSedeLegaleBenPA = data;
          if (idComuneSedeLegale) {
            this.beneficiario.comuneSedeLegalePA = this.comuniSedeLegaleBenPA.find(s => s.codice === idComuneSedeLegale);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeNazioneNascitaRappr(idRegioneNascita?: string, idProvinciaNascita?: string, idComuneNascita?: string) {
    this.regioniNascitaRappr = null;
    this.rappresentante.regioneNascita = null;
    this.provinceNascitaRappr = null;
    this.rappresentante.provinciaNascita = null;
    this.comuniNascitaRappr = null;
    this.comuniNascitaEsteriRappr = null;
    this.rappresentante.comuneNascita = null;
    if (this.rappresentante.nazioneNascita) {
      if (this.rappresentante.nazioneNascita.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniNascitaRappr = this.schedaProgettoService.popolaComboRegioneNascita().subscribe(data => {
          if (data) {
            this.regioniNascitaRappr = data;
            if (idRegioneNascita) {
              this.rappresentante.regioneNascita = this.regioniNascitaRappr.find(s => s.codice === idRegioneNascita);
              this.changeRegioneNascitaRappr(idProvinciaNascita, idComuneNascita);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni di nascita.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniNascitaEsteriRappr = this.schedaProgettoService.popolaComboComuneEsteroNascita(+this.rappresentante.nazioneNascita.id).subscribe(data => {
          if (data) {
            this.comuniNascitaEsteriRappr = data;
            if (idComuneNascita) {
              this.rappresentante.comuneNascita = this.comuniNascitaEsteriRappr.find(s => s.codice === idComuneNascita);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri di nascita.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneNascitaRappr(idProvinciaNascita?: string, idComuneNascita?: string) {
    this.provinceNascitaRappr = null;
    this.rappresentante.provinciaNascita = null;
    this.comuniNascitaRappr = null;
    this.comuniNascitaEsteriRappr = null;
    this.rappresentante.comuneNascita = null;
    if (this.rappresentante.regioneNascita) {
      this.loadedCombo = false;
      this.subscribers.provinceNascitaRappr = this.schedaProgettoService.popolaComboProvinciaNascita(+this.rappresentante.regioneNascita.codice).subscribe(data => {
        if (data) {
          this.provinceNascitaRappr = data;
          if (idProvinciaNascita) {
            this.rappresentante.provinciaNascita = this.provinceNascitaRappr.find(s => s.codice === idProvinciaNascita);
            this.changeProvinciaNascitaRappr(idComuneNascita);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province di nascita.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaNascitaRappr(idComuneNascita?: string) {
    this.comuniNascitaRappr = null;
    this.comuniNascitaEsteriRappr = null;
    this.rappresentante.comuneNascita = null;
    if (this.rappresentante.provinciaNascita) {
      this.loadedCombo = false;
      this.subscribers.comuniNascitaRappr = this.schedaProgettoService.popolaComboComuneItalianoNascita(+this.rappresentante.provinciaNascita.codice).subscribe(data => {
        if (data) {
          this.comuniNascitaRappr = data;
          if (idComuneNascita) {
            this.rappresentante.comuneNascita = this.comuniNascitaRappr.find(s => s.codice === idComuneNascita);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni di nascita.");
        this.loadedCombo = true;
      });
    }
  }

  changeNazioneResidenzaRappr(idRegioneResidenza?: string, idProvinciaResidenza?: string, idComuneResidenza?: string) {
    this.regioniResidenzaRappr = null;
    this.rappresentante.regioneResidenza = null;
    this.provinceResidenzaRappr = null;
    this.rappresentante.provinciaResidenza = null;
    this.comuniResidenzaRappr = null;
    this.comuniResidenzaEsteriRappr = null;
    this.rappresentante.comuneResidenza = null;
    if (this.rappresentante.nazioneResidenza) {
      if (this.rappresentante.nazioneResidenza.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioniResidenzaRappr = this.schedaProgettoService.popolaComboRegione().subscribe(data => {
          if (data) {
            this.regioniResidenzaRappr = data;
            if (idRegioneResidenza) {
              this.rappresentante.regioneResidenza = this.regioniResidenzaRappr.find(s => s.codice === idRegioneResidenza);
              this.changeRegioneResidenzaRappr(idProvinciaResidenza, idComuneResidenza);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni di residenza.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniResidenzaEsteriRappr = this.schedaProgettoService.popolaComboComuneEstero(+this.rappresentante.nazioneResidenza.id).subscribe(data => {
          if (data) {
            this.comuniResidenzaEsteriRappr = data;
            if (idComuneResidenza) {
              this.rappresentante.comuneResidenza = this.comuniResidenzaEsteriRappr.find(s => s.codice === idComuneResidenza);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri di residenza.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegioneResidenzaRappr(idProvinciaResidenza?: string, idComuneResidenza?: string) {
    this.provinceResidenzaRappr = null;
    this.rappresentante.provinciaResidenza = null;
    this.comuniResidenzaRappr = null;
    this.comuniResidenzaEsteriRappr = null;
    this.rappresentante.comuneResidenza = null;
    if (this.rappresentante.regioneResidenza) {
      this.loadedCombo = false;
      this.subscribers.provinceResidenzaRappr = this.schedaProgettoService.popolaComboProvincia(+this.rappresentante.regioneResidenza.codice).subscribe(data => {
        if (data) {
          this.provinceResidenzaRappr = data;
          if (idProvinciaResidenza) {
            this.rappresentante.provinciaResidenza = this.provinceResidenzaRappr.find(s => s.codice === idProvinciaResidenza);
            this.changeProvinciaResidenzaRappr(idComuneResidenza);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province di residenza.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvinciaResidenzaRappr(idComuneResidenza?: string) {
    this.comuniResidenzaRappr = null;
    this.comuniResidenzaEsteriRappr = null;
    this.rappresentante.comuneResidenza = null;
    if (this.rappresentante.provinciaResidenza) {
      this.loadedCombo = false;
      this.subscribers.comuniResidenzaRappr = this.schedaProgettoService.popolaComboComuneItaliano(+this.rappresentante.provinciaResidenza.codice).subscribe(data => {
        if (data) {
          this.comuniResidenzaRappr = data;
          if (idComuneResidenza) {
            this.rappresentante.comuneResidenza = this.comuniResidenzaRappr.find(s => s.codice === idComuneResidenza);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni di residenza.");
        this.loadedCombo = true;
      });
    }
  }

  changeEnteDR(fromSelect: boolean, idSettore?: string) {
    this.settoriDR = null;
    this.beneficiario.settoreDR = null;
    if (this.beneficiario.enteDR) {
      if (fromSelect) { //se l'ente viene selezionato dalla combo dall'utente
        this.loadedCarica = false;
        this.subscribers.caricaInfoDR = this.schedaProgettoService.caricaInfoDirezioneRegionale(+this.beneficiario.enteDR.codice).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadSettoriDR(data.datiPG.idSettoreEnte);
              if (data.datiPG.ruolo && data.datiPG.ruolo.length > 0) {
                for (let ruolo of data.datiPG.ruolo) {
                  let ruoloChecked = this.combo.comboRuoli.find(r => r.codice === ruolo);
                  ruoloChecked.checked = true;
                  this.beneficiario.ruoli.push(ruoloChecked);
                }
              }
              this.beneficiario.iban = data.datiPG.iban;
              this.beneficiario.partitaIvaDR = data.datiPG.partitaIvaSedeLegale;
              this.beneficiario.indirizzoSedeLegaleDR = data.datiPG.indirizzoSedeLegale;
              this.beneficiario.civicoSedeLegaleDR = data.datiPG.numCivicoSedeLegale;
              this.beneficiario.capSedeLegaleDR = data.datiPG.capSedeLegale;
              if (data.datiPG.sedeLegale.idNazione) {
                this.beneficiario.nazioneSedeLegaleDR = this.combo.comboNazione.find(n => n.id === data.datiPG.sedeLegale.idNazione);
                this.changeNazioneSedeLegaleDR(data.datiPG.sedeLegale.idRegione, data.datiPG.sedeLegale.idProvincia, data.datiPG.sedeLegale.idComune);
              }
              this.beneficiario.emailSedeLegaleDR = data.datiPG.emailSedeLegale;
              this.beneficiario.pecSedeLegaleDR = data.datiPG.pecSedeLegale;
              this.beneficiario.telefonoSedeLegaleDR = data.datiPG.telefonoSedeLegale;
              this.beneficiario.faxSedeLegaleDR = data.datiPG.faxSedeLegale;
            }
          }
          this.loadedCarica = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero dati.");
          this.loadedCarica = true;
        });
      } else { //se si arriva dal precaricamento in modifica
        this.loadSettoriDR(idSettore);
      }
    }
  }

  loadSettoriDR(idSettore: string) {
    this.loadedCombo = false;
    this.subscribers.settoriDR = this.schedaProgettoService.popolaComboDenominazioneSettori(+this.beneficiario.enteDR.codice).subscribe(data => {
      if (data) {
        this.settoriDR = data;
        if (idSettore) {
          this.beneficiario.settoreDR = this.settoriDR.find(s => s.codice === idSettore);
        }
      }
      this.loadedCombo = true;
    }, err => {
      this.resetMessageSuccess();
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei settori.");
      this.loadedCombo = true;
    });
  }

  changeEntePA(fromSelect: boolean, idSettore?: string) {
    this.settoriPA = null;
    this.beneficiario.settorePA = null;
    if (this.beneficiario.entePA) {
      if (fromSelect) { //se l'ente viene selezionato dalla combo dall'utente
        this.loadedCarica = false;
        this.subscribers.caricaInfoPA = this.schedaProgettoService.caricaInfoPubblicaAmministrazione(+this.beneficiario.entePA.codice).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadSettoriPA(data.datiPG.idSettoreEnte);
              if (data.datiPG.ruolo && data.datiPG.ruolo.length > 0) {
                for (let ruolo of data.datiPG.ruolo) {
                  let ruoloChecked = this.combo.comboRuoli.find(r => r.codice === ruolo);
                  ruoloChecked.checked = true;
                  this.beneficiario.ruoli.push(ruoloChecked);
                }
              }
              this.beneficiario.iban = data.datiPG.iban;
              this.beneficiario.partitaIvaPA = data.datiPG.partitaIvaSedeLegale;
              this.beneficiario.indirizzoSedeLegalePA = data.datiPG.indirizzoSedeLegale;
              this.beneficiario.civicoSedeLegalePA = data.datiPG.numCivicoSedeLegale;
              this.beneficiario.capSedeLegalePA = data.datiPG.capSedeLegale;
              if (data.datiPG.sedeLegale.idNazione) {
                this.beneficiario.nazioneSedeLegalePA = this.combo.comboNazione.find(n => n.id === data.datiPG.sedeLegale.idNazione);
                this.changeNazioneSedeLegalePA(data.datiPG.sedeLegale.idRegione, data.datiPG.sedeLegale.idProvincia, data.datiPG.sedeLegale.idComune);
              }
              this.beneficiario.emailSedeLegalePA = data.datiPG.emailSedeLegale;
              this.beneficiario.pecSedeLegalePA = data.datiPG.pecSedeLegale;
              this.beneficiario.telefonoSedeLegalePA = data.datiPG.telefonoSedeLegale;
              this.beneficiario.faxSedeLegalePA = data.datiPG.faxSedeLegale;
            }
          }
          this.loadedCarica = true;
        }, err => {
          this.resetMessageSuccess();
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero dati.");
          this.loadedCarica = true;
        });
      } else { //se si arriva dal precaricamento in modifica
        this.loadSettoriPA(idSettore);
      }

    }
  }

  loadSettoriPA(idSettore: string) {
    this.loadedCombo = false;
    this.subscribers.settoriPA = this.schedaProgettoService.popolaComboDenominazioneSettori(+this.beneficiario.entePA.codice).subscribe(data => {
      if (data) {
        this.settoriPA = data;
        if (idSettore) {
          this.beneficiario.settorePA = this.settoriPA.find(s => s.codice === idSettore);
        }
      }
      this.loadedCombo = true;
    }, err => {
      this.resetMessageSuccess();
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei settori.");
      this.loadedCombo = true;
    });
  }

  changeAteneo(idDipartimento?: string) {
    this.dipartimenti = null;
    this.beneficiario.dipartimento = null;
    if (this.beneficiario.ateneo) {
      this.loadedCombo = false;
      this.subscribers.dipartimenti = this.schedaProgettoService.popolaComboDenominazioneEnteDipUni(+this.beneficiario.ateneo.codice).subscribe(data => {
        if (data) {
          this.dipartimenti = data;
          if (idDipartimento) {
            this.beneficiario.dipartimento = this.dipartimenti.find(s => s.codice === idDipartimento);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.resetMessageSuccess();
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei dipartimenti.");
        this.loadedCombo = true;
      });
    }
  }

  keyUpCfBenEG() {
    if (this.isCfConfirmedBen) {
      this.isCfConfirmedBen = false;
    } else {
      this.beneficiario.codiceFiscaleEG = this.beneficiario.codiceFiscaleEG.toUpperCase();
    }
  }

  keyUpCfRappr() {
    if (this.isCfConfirmedRappr) {
      this.isCfConfirmedRappr = false;
    } else {
      this.rappresentante.codiceFiscale = this.rappresentante.codiceFiscale.toUpperCase();
    }
  }

  /*CF prova 00518460019*/
  confermaCfBen() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedConferma = false;
    this.subscribers.confermaCfBen = this.schedaProgettoService.ricercaBeneficiarioCsp(this.beneficiario.codiceFiscaleEG).subscribe(data => {
      if (!data || data.length == 0) {
        this.showMessageError("La ricerca non ha trovato alcun beneficiario.");
        this.isCfConfirmedBen = true;
      } else {
        let dialogRef = this.dialog.open(BeneficiarioProgettoDialogComponent, {
          width: '60%',
          height: '530px',
          maxHeight: '95%',
          data: {
            codiceFiscale: this.beneficiario.codiceFiscaleEG,
            beneficiari: data
          }
        });
        dialogRef.afterClosed().subscribe((res: BeneficiarioCspDTO) => {
          this.isCfConfirmedBen = true;
          if (res) {
            this.beneficiario.denominazione = res.denominazioneEnteGiuridico;
            this.beneficiario.dataCostituzione.setValue(res.dtCostituzione ? this.sharedService.parseDate(res.dtCostituzione) : null);
            this.beneficiario.formaGiuridica = res.idFormaGiuridica ? this.combo.comboFormaGiuridica.find(f => f.codice === res.idFormaGiuridica.toString()) : null;
            this.beneficiario.iban = res.iban;
            this.beneficiario.partitaIva = res.sedeSelected.partitaIva;
            this.beneficiario.indirizzoSedeLegale = res.sedeSelected.descIndirizzo;
            this.beneficiario.civicoSedeLegale = res.sedeSelected.civico;
            this.beneficiario.capSedeLegale = res.sedeSelected.cap;
            this.beneficiario.nazioneSedeLegale = res.sedeSelected.idNazione ? this.combo.comboNazione.find(n => n.id === res.sedeSelected.idNazione.toString()) : this.beneficiario.nazioneSedeLegale;
            if (!this.beneficiario.nazioneSedeLegale) {
              this.regioniSedeLegaleBen = null;
              this.beneficiario.regioneSedeLegale = null;
              this.provinceSedeLegaleBen = null;
              this.beneficiario.provinciaSedeLegale = null;
              this.comuniSedeLegaleBen = null;
              this.comuniSedeLegaleEsteriBen = null;
              this.beneficiario.comuneSedeLegale = null;
            } else {
              this.changeNazioneSedeLegale(res.sedeSelected.idRegione ? res.sedeSelected.idRegione.toString() : null,
                res.sedeSelected.idProvincia ? res.sedeSelected.idProvincia.toString() : null,
                res.sedeSelected.idComune ? res.sedeSelected.idComune.toString() : (res.sedeSelected.idComuneEstero ? res.sedeSelected.idComuneEstero.toString() : null));
            }
            this.beneficiario.emailSedeLegale = res.sedeSelected.email;
            this.beneficiario.pecSedeLegale = res.sedeSelected.pec;
            this.beneficiario.telefonoSedeLegale = res.sedeSelected.telefono;
            this.beneficiario.faxSedeLegale = res.sedeSelected.fax;
            this.beneficiario.settoreAttivita = res.idSettoreAttivita ? this.combo.comboSettoreAttivita.find(s => s.codice === res.idSettoreAttivita.toString()) : null;
            if (!this.beneficiario.settoreAttivita) {
              this.attivitaAtecoBen = null;
              this.beneficiario.attivitaAteco = null;
            } else {
              this.changeSettoreAttivitaBen(res.idAttivitaAteco.toString());
            }
          }
        });
      }
      this.loadedConferma = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca del beneficiario.");
      this.isCfConfirmedBen = true;
      this.loadedConferma = true;
    });
  }

  /*CF prova SRCGDU65S24L219S*/
  confermaCfRappr() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedConferma = false;
    this.subscribers.confermaCfBen = this.schedaProgettoService.ricercaRapprLegaleCsp(this.rappresentante.codiceFiscale).subscribe(data => {
      if (!data || data.length == 0) {
        this.showMessageError("La ricerca non ha trovato alcun rappresentante legale.");
        this.isCfConfirmedRappr = true;
      } else {
        console.log(data);
        let dialogRef = this.dialog.open(RapprLegaleProgettoDialogComponent, {
          width: '60%',
          height: '530px',
          maxHeight: '95%',
          data: {
            codiceFiscale: this.rappresentante.codiceFiscale,
            rappresentanti: data
          }
        });
        dialogRef.afterClosed().subscribe((res: RapprLegaleCspDTO) => {
          this.isCfConfirmedRappr = true;
          if (res) {
            this.rappresentante.cognome = res.cognome;
            this.rappresentante.nome = res.nome;
            this.rappresentante.sesso = res.sesso ? res.sesso : "M";
            this.rappresentante.dataNascita.setValue(res.dtNascitaStringa ? new Date(this.sharedService.parseDate(res.dtNascitaStringa)) : null);
            this.rappresentante.nazioneNascita = res.indirizzoSelected.idNazioneNascita ? this.combo.comboNazioneNascita.find(n => n.id === res.indirizzoSelected.idNazioneNascita.toString()) : this.rappresentante.nazioneNascita;
            if (!this.rappresentante.nazioneNascita) {
              this.regioniNascitaRappr = null;
              this.rappresentante.regioneNascita = null;
              this.provinceNascitaRappr = null;
              this.rappresentante.provinciaNascita = null;
              this.comuniNascitaRappr = null;
              this.comuniNascitaEsteriRappr = null;
              this.rappresentante.comuneNascita = null;
            } else {
              this.changeNazioneNascitaRappr(res.indirizzoSelected.idRegioneNascita ? res.indirizzoSelected.idRegioneNascita.toString() : null,
                res.indirizzoSelected.idProvinciaNascita ? res.indirizzoSelected.idProvinciaNascita.toString() : null,
                res.indirizzoSelected.idComuneNascita ? res.indirizzoSelected.idComuneNascita.toString() :
                  (res.indirizzoSelected.idComuneEsteroNascita ? res.indirizzoSelected.idComuneEsteroNascita.toString() : null));
            }
            this.rappresentante.indirizzoResidenza = res.indirizzoSelected.descIndirizzo;
            this.rappresentante.civicoResidenza = res.indirizzoSelected.civico;
            this.rappresentante.capResidenza = res.indirizzoSelected.cap;
            this.rappresentante.nazioneResidenza = res.indirizzoSelected.idNazioneRes ? this.combo.comboNazione.find(n => n.id === res.indirizzoSelected.idNazioneRes.toString()) : this.rappresentante.nazioneResidenza;
            if (!this.rappresentante.nazioneResidenza) {
              this.regioniResidenzaRappr = null;
              this.rappresentante.regioneResidenza = null;
              this.provinceResidenzaRappr = null;
              this.rappresentante.provinciaResidenza = null;
              this.comuniResidenzaRappr = null;
              this.comuniResidenzaEsteriRappr = null;
              this.rappresentante.comuneResidenza = null;
            } else {
              this.changeNazioneResidenzaRappr(res.indirizzoSelected.idRegioneRes ? res.indirizzoSelected.idRegioneRes.toString() : null,
                res.indirizzoSelected.idProvinciaRes ? res.indirizzoSelected.idProvinciaRes.toString() : null,
                res.indirizzoSelected.idComuneRes ? res.indirizzoSelected.idComuneRes.toString() :
                  (res.indirizzoSelected.idComuneEsteroRes ? res.indirizzoSelected.idComuneEsteroRes.toString() : null));
            }
            this.rappresentante.email = res.indirizzoSelected.email;
            this.rappresentante.telefono = res.indirizzoSelected.telefono;
            this.rappresentante.fax = res.indirizzoSelected.fax;
          }
        });
      }
      this.loadedConferma = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca del rappresentante legale.");
      this.isCfConfirmedRappr = true;
      this.loadedConferma = true;
    });
  }

  aggiungiSedeIntervento() {
    let dialogRef = this.dialog.open(SedeInterventoDialogComponent, {
      width: '60%',
      maxHeight: '95%',
      data: {
        nazioni: this.combo.comboNazione,
        sedeInterventoDefault: this.sedeInterventoDefault
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.sediIntervento.push(res);
        this.dataSource.data = this.sediIntervento;
      }
    });
  }

  eliminaSedeIntervento(index: number) {
    this.sediIntervento.splice(index, 1);
    this.dataSource.data = this.sediIntervento;
  }

  checkFieldRequired(form: NgForm, field: any, fieldName: string, error: string) {
    if (!field) {
      form.form.get(fieldName).setErrors({ error: error });
      form.form.get(fieldName).markAsTouched();
      return true;
    }
    return false
  }

  checkDatiRequired() {
    let erroreRequired: boolean = false;
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.titoloProgetto, 'titoloProgetto', 'required')) {
      erroreRequired = true;
    }
    if (!this.datiGen.dataPresentazioneDomanda.value) {
      this.datiGen.dataPresentazioneDomanda.setErrors({ error: 'required' });
      this.datiGen.dataPresentazioneDomanda.markAsTouched();
      erroreRequired = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.tipoOperazione, 'tipoOperazione', 'required')) {
      erroreRequired = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.strumento, 'strumento', 'required')) {
      erroreRequired = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.settoreCpt, 'settoreCpt', 'required')) {
      erroreRequired = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.tipoStrumentoProg, 'tipoStrumentoProg', 'required')) {
      erroreRequired = true;
    }
    return erroreRequired;
  }

  checkDatiGenCongruita() {
    let erroreCongruitaDatiGen: boolean = false;

    if (this.datiGen.cup && this.datiGen.cup.length !== 15) {
      this.datiGeneraliForm.form.get('cup').setErrors({ error: 'length' });
      this.datiGeneraliForm.form.get('cup').markAsTouched();
      erroreCongruitaDatiGen = true;
    }
    //le date non devono superare la data odierna
    if (this.datiGen.dataPresentazioneDomanda && this.datiGen.dataPresentazioneDomanda.value && this.datiGen.dataPresentazioneDomanda.value > this.today) {
      this.datiGen.dataPresentazioneDomanda.setErrors({ error: 'maggioreToday' });
      this.datiGen.dataPresentazioneDomanda.markAsTouched();
      erroreCongruitaDatiGen = true;
    }
    if (this.datiGen.dataComitato && this.datiGen.dataComitato.value && this.datiGen.dataComitato.value > this.today) {
      this.datiGen.dataComitato.setErrors({ error: 'maggioreToday' });
      this.datiGen.dataComitato.markAsTouched();
      erroreCongruitaDatiGen = true;
    }
    if (this.datiGen.dataConcessione && this.datiGen.dataConcessione.value && this.datiGen.dataConcessione.value > this.today) {
      this.datiGen.dataConcessione.setErrors({ error: 'maggioreToday' });
      this.datiGen.dataConcessione.markAsTouched();
      erroreCongruitaDatiGen = true;
    }
    if (this.datiGen.dataPresentazioneDomanda && this.datiGen.dataPresentazioneDomanda.value &&
      ((this.datiGen.dataConcessione && this.datiGen.dataConcessione.value && this.datiGen.dataConcessione.value < this.datiGen.dataPresentazioneDomanda.value)
        || (this.datiGen.dataComitato && this.datiGen.dataComitato.value && this.datiGen.dataComitato.value < this.datiGen.dataPresentazioneDomanda.value))) {
      this.datiGen.dataPresentazioneDomanda.setErrors({ error: 'maggioreConcessioneComitato' });
      this.datiGen.dataPresentazioneDomanda.markAsTouched();
      erroreCongruitaDatiGen = true;
    }
    return erroreCongruitaDatiGen;
  }

  checkBenCongruita() {
    let erroreCongruitaBen: boolean = false;

    if (this.beneficiario.dataNascita && this.beneficiario.dataNascita.value && this.beneficiario.dataNascita.value > this.today) {
      this.beneficiario.dataNascita.setErrors({ error: 'maggioreToday' });
      this.beneficiario.dataNascita.markAsTouched();
      erroreCongruitaBen = true;
    }
    if (this.beneficiario.dataCostituzione && this.beneficiario.dataCostituzione.value && this.beneficiario.dataCostituzione.value > this.today) {
      this.beneficiario.dataCostituzione.setErrors({ error: 'maggioreToday' });
      this.beneficiario.dataCostituzione.markAsTouched();
      erroreCongruitaBen = true;
    }
    //se in Benef. PG è selezionato il check Altro e dataCostituzioneAzienda è valorizzata, allora deve essere precedente alla data comitato e alla data concessione, se valorizzate.
    if (this.beneficiario.dataCostituzione && this.beneficiario.dataCostituzione.value && this.beneficiario.tipoSoggGiur === "NA" &&
      ((this.datiGen.dataConcessione && this.datiGen.dataConcessione.value && this.datiGen.dataConcessione.value < this.beneficiario.dataCostituzione.value)
        || (this.datiGen.dataComitato && this.datiGen.dataComitato.value && this.datiGen.dataComitato.value < this.beneficiario.dataCostituzione.value))) {
      this.beneficiario.dataCostituzione.setErrors({ error: 'maggioreConcessioneComitato' });
      this.beneficiario.dataCostituzione.markAsTouched();
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.email, 'emailBen', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.emailSedeLegale, 'emailSedeLegale', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.emailSedeLegaleDR, 'emailSedeLegaleDR', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.emailSedeLegalePA, 'emailSedeLegalePA', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.pecSedeLegale, 'pecSedeLegale', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.pecSedeLegaleDR, 'pecSedeLegaleDR', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.pecSedeLegalePA, 'pecSedeLegalePA', patternEmail)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.capResidenza, 'capResidenzaBen', patternCap)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.capSedeLegale, 'capSedeLegaleBen', patternCap)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.capSedeLegaleDR, 'capSedeLegaleBenDR', patternCap)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.capSedeLegalePA, 'capSedeLegaleBenPA', patternCap)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.telefono, 'telefonoBen', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.telefonoSedeLegale, 'telefonoSedeLegaleBen', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.telefonoSedeLegaleDR, 'telefonoSedeLegaleBenDR', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.telefonoSedeLegalePA, 'telefonoSedeLegaleBenPA', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.fax, 'faxBen', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.faxSedeLegale, 'faxSedeLegaleBen', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.faxSedeLegaleDR, 'faxSedeLegaleBenDR', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.beneficiario.faxSedeLegalePA, 'faxSedeLegaleBenPA', patternTelFax)) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkCodiceFiscalePF(this.soggettiForm, this.beneficiario.codiceFiscalePF, 'codiceFiscaleBenPF')) {
      erroreCongruitaBen = true;
    }
    if (this.sharedService.checkCodiceFiscaleEG(this.soggettiForm, this.beneficiario.codiceFiscaleEG, 'codiceFiscaleBenEG')) {
      if (this.sharedService.checkCodiceFiscalePF(this.soggettiForm, this.beneficiario.codiceFiscaleEG, 'codiceFiscaleBenEG')) {
        erroreCongruitaBen = true;
      }
    }
    if (this.sharedService.checkCodiceFiscaleEG(this.soggettiForm, this.beneficiario.partitaIva, 'partitaIva')) {
      if (this.sharedService.checkCodiceFiscalePF(this.soggettiForm, this.beneficiario.partitaIva, 'partitaIva')) {
        erroreCongruitaBen = true;
      }
    }
    if (this.sharedService.checkCodiceFiscaleEG(this.soggettiForm, this.beneficiario.partitaIvaDR, 'partitaIvaDR')) {
      if (this.sharedService.checkCodiceFiscalePF(this.soggettiForm, this.beneficiario.partitaIvaDR, 'partitaIvaDR')) {
        erroreCongruitaBen = true;
      }
    }
    if (this.sharedService.checkCodiceFiscaleEG(this.soggettiForm, this.beneficiario.partitaIvaPA, 'partitaIvaPA')) {
      if (this.sharedService.checkCodiceFiscalePF(this.soggettiForm, this.beneficiario.partitaIvaPA, 'partitaIvaPA')) {
        erroreCongruitaBen = true;
      }
    }
    return erroreCongruitaBen;
  }

  checkRapprCongruita() {
    let erroreCongruitaRappr: boolean = false;

    if (this.rappresentante.dataNascita && this.rappresentante.dataNascita.value && this.rappresentante.dataNascita.value > this.today) {
      this.rappresentante.dataNascita.setErrors({ error: 'maggioreToday' });
      this.rappresentante.dataNascita.markAsTouched();
      erroreCongruitaRappr = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.rappresentante.email, 'emailRappr', patternEmail)) {
      erroreCongruitaRappr = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.rappresentante.capResidenza, 'capResidenzaRappr', patternCap)) {
      erroreCongruitaRappr = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.rappresentante.telefono, 'telefonoRappr', patternTelFax)) {
      erroreCongruitaRappr = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.soggettiForm, this.rappresentante.fax, 'faxRappr', patternTelFax)) {
      erroreCongruitaRappr = true;
    }
    if (this.sharedService.checkCodiceFiscalePF(this.soggettiForm, this.rappresentante.codiceFiscale, 'codiceFiscaleRappr')) {
      erroreCongruitaRappr = true;
    }
    return erroreCongruitaRappr;
  }

  checkDatiGenRequiredAvvio() {
    let erroreRequiredAvvioDatiGen: boolean = false;

    if (!this.datiGen.dataConcessione.value) {
      this.datiGen.dataConcessione.setErrors({ error: 'requiredAvvio' });
      this.datiGen.dataConcessione.markAsTouched();
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.settoreAttivita, 'settoreAttivitaDatiGen', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.attivitaAteco, 'attivitaAtecoDatiGen', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.idProcesso === 1) {
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.prioritaQsn, 'prioritaQsn', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.obiettivoGenQsn, 'obiettivoGenQsn', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.obiettivoSpecQsn, 'obiettivoSpecQsn', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
    } else if (this.idProcesso === 2 || this.idProcesso === 3 || this.idProcesso === 4) {
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.obiettivoTematico, 'obiettivoTematico', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.classificazioneRA, 'classificazioneRA', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.temaPrioritario, 'temaPrioritario', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.indicatoreRisProg, 'indicatoreRisProg', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.indicatoreQsn, 'indicatoreQsn', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.tipoAiuto, 'tipoAiuto', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.dimTerritoriale, 'dimTerritoriale', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.settoreCipe, 'settoreCipe', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.sottoSettoreCipe, 'sottoSettoreCipe', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.categoriaCipe, 'categoriaCipe', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.naturaCipe, 'naturaCipe', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.tipologiaCipe, 'tipologiaCipe', 'requiredAvvio')) {
      erroreRequiredAvvioDatiGen = true;
    }
    if (this.isProgrammazione2127) {
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.ppp, 'ppp', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
      if (this.checkFieldRequired(this.datiGeneraliForm, this.datiGen.flagImportanzaStrategica, 'flagImportanzaStrategica', 'requiredAvvio')) {
        erroreRequiredAvvioDatiGen = true;
      }
    }

    return erroreRequiredAvvioDatiGen;
  }

  checkSedeLegale(pIva: string, namePIva: string, indirizzo: string, nameIndirizzo: string, civico: string, nameCivico: string, cap: string, nameCap: string,
    nazione: IdDescBreveDescEstesa, nameNazione: string, regione: CodiceDescrizione, nameRegione: string, provincia: CodiceDescrizione, nameProvincia: string,
    comune: CodiceDescrizione, nameComune: string, nameComuneEstero: string) {
    let erroreRequiredAvvioBen: boolean;
    if (this.checkFieldRequired(this.soggettiForm, pIva, namePIva, 'requiredAvvio')) {
      erroreRequiredAvvioBen = true;
    }
    if ((this.beneficiario.tipoSoggGiur === "DU" || this.beneficiario.tipoSoggGiur === "NA")
      && this.checkFieldRequired(this.soggettiForm, indirizzo, nameIndirizzo, 'requiredAvvio')) {
      erroreRequiredAvvioBen = true;
    }
    if ((this.beneficiario.tipoSoggGiur === "DU" || this.beneficiario.tipoSoggGiur === "NA")
      && this.checkFieldRequired(this.soggettiForm, civico, nameCivico, 'requiredAvvio')) {
      erroreRequiredAvvioBen = true;
    }
    if (this.checkFieldRequired(this.soggettiForm, cap, nameCap, 'requiredAvvio')) {
      erroreRequiredAvvioBen = true;
    }
    if (this.checkFieldRequired(this.soggettiForm, nazione, nameNazione, 'requiredAvvio')) {
      erroreRequiredAvvioBen = true;
    } else {
      if (this.beneficiario.nazioneResidenza.descEstesa === "ITALIA") {
        if (this.checkFieldRequired(this.soggettiForm, regione, nameRegione, 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, provincia, nameProvincia, 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if ((this.beneficiario.tipoSoggGiur === "DU" || this.beneficiario.tipoSoggGiur === "NA")
          && this.checkFieldRequired(this.soggettiForm, comune, nameComune, 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
      } else {
        if ((this.beneficiario.tipoSoggGiur === "DU" || this.beneficiario.tipoSoggGiur === "NA")
          && this.checkFieldRequired(this.soggettiForm, comune, nameComuneEstero, 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
      }
    }
    if (this.beneficiario.tipoSoggGiur === 'DU' || this.beneficiario.tipoSoggGiur === 'NA') {
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.emailSedeLegale, 'emailSedeLegale', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.telefonoSedeLegale, 'telefonoSedeLegaleBen', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
    } else if (this.beneficiario.tipoSoggGiur === 'DR') {
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.emailSedeLegaleDR, 'emailSedeLegaleDR', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.telefonoSedeLegaleDR, 'telefonoSedeLegaleBenDR', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
    } else if (this.beneficiario.tipoSoggGiur === 'PA') {
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.emailSedeLegalePA, 'emailSedeLegalePA', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.telefonoSedeLegalePA, 'telefonoSedeLegaleBenPA', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
    }
    return erroreRequiredAvvioBen;
  }

  checkBenRequiredAvvio() {
    let erroreRequiredAvvioBen: boolean = false;
    if (this.beneficiario.tipoSoggetto === "PF") {
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.codiceFiscalePF, 'codiceFiscaleBenPF', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.iban, 'iban', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.nazioneNascita, 'nazioneNascitaBen', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      } else {
        if (this.beneficiario.nazioneNascita.descEstesa === "ITALIA") {
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.regioneNascita, 'regioneNascitaBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.provinciaNascita, 'provinciaNascitaBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.comuneNascita, 'comuneNascitaBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
        } else {
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.comuneNascita, 'comuneNascitaEsteroBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
        }
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.indirizzoResidenza, 'indirizzoResidenzaBen', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.civicoResidenza, 'civicoResidenzaBen', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.capResidenza, 'capResidenzaBen', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      }
      if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.nazioneResidenza, 'nazioneResidenzaBen', 'requiredAvvio')) {
        erroreRequiredAvvioBen = true;
      } else {
        if (this.beneficiario.nazioneResidenza.descEstesa === "ITALIA") {
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.regioneResidenza, 'regioneResidenzaBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.provinciaResidenza, 'provinciaResidenzaBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.comuneResidenza, 'comuneResidenzaBen', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
        } else {
          if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.comuneResidenza, 'comuneResidenzaEstero', 'requiredAvvio')) {
            erroreRequiredAvvioBen = true;
          }
        }
      }
    } else if (this.beneficiario.tipoSoggetto === "EG") {
      if (this.beneficiario.tipoSoggGiur === "DU") {
        if (this.checkSedeLegale(this.beneficiario.partitaIva, 'partitaIva', this.beneficiario.indirizzoSedeLegale, 'indirizzoSedeLegaleBen', this.beneficiario.civicoSedeLegale,
          'civicoSedeLegaleBen', this.beneficiario.capSedeLegale, 'capSedeLegaleBen', this.beneficiario.nazioneSedeLegale, 'nazioneSedeLegaleBen', this.beneficiario.regioneSedeLegale,
          'regioneSedeLegaleBen', this.beneficiario.provinciaSedeLegale, 'provinciaSedeLegaleBen', this.beneficiario.comuneSedeLegale, 'comuneSedeLegaleBen', 'comuneSedeLegaleEsteroBen')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.ateneo, 'ateneo', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.dipartimento, 'dipartimento', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
      } else if (this.beneficiario.tipoSoggGiur === "DR") {
        if (this.checkSedeLegale(this.beneficiario.partitaIvaDR, 'partitaIvaDR', this.beneficiario.indirizzoSedeLegaleDR, 'indirizzoSedeLegaleBenDR',
          this.beneficiario.civicoSedeLegaleDR, 'civicoSedeLegaleBenDR', this.beneficiario.capSedeLegaleDR, 'capSedeLegaleBenDR', this.beneficiario.nazioneSedeLegaleDR,
          'nazioneSedeLegaleBenDR', this.beneficiario.regioneSedeLegaleDR, 'regioneSedeLegaleBenDR', this.beneficiario.provinciaSedeLegaleDR, 'provinciaSedeLegaleBenDR',
          this.beneficiario.comuneSedeLegaleDR, 'comuneSedeLegaleBenDR', 'comuneSedeLegaleEsteroBenDR')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.enteDR, 'enteDR', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.settoreDR, 'settoreDR', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
      } else if (this.beneficiario.tipoSoggGiur === "PA") {
        if (this.checkSedeLegale(this.beneficiario.partitaIvaPA, 'partitaIvaPA', this.beneficiario.indirizzoSedeLegalePA, 'indirizzoSedeLegaleBenPA',
          this.beneficiario.civicoSedeLegalePA, 'civicoSedeLegaleBenPA', this.beneficiario.capSedeLegalePA, 'capSedeLegaleBenPA', this.beneficiario.nazioneSedeLegalePA,
          'nazioneSedeLegaleBenPA', this.beneficiario.regioneSedeLegalePA, 'regioneSedeLegaleBenPA', this.beneficiario.provinciaSedeLegalePA, 'provinciaSedeLegaleBenPA',
          this.beneficiario.comuneSedeLegalePA, 'comuneSedeLegaleBenPA', 'comuneSedeLegaleEsteroBenPA')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.entePA, 'entePA', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
      } else if (this.beneficiario.tipoSoggGiur === "NA") {
        if (this.checkSedeLegale(this.beneficiario.partitaIva, 'partitaIva', this.beneficiario.indirizzoSedeLegale, 'indirizzoSedeLegaleBen', this.beneficiario.civicoSedeLegale,
          'civicoSedeLegaleBen', this.beneficiario.capSedeLegale, 'capSedeLegaleBen', this.beneficiario.nazioneSedeLegale, 'nazioneSedeLegaleBen', this.beneficiario.regioneSedeLegale,
          'regioneSedeLegaleBen', this.beneficiario.provinciaSedeLegale, 'provinciaSedeLegaleBen', this.beneficiario.comuneSedeLegale, 'comuneSedeLegaleBen', 'comuneSedeLegaleEsteroBen')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.codiceFiscaleEG, 'codiceFiscaleBenEG', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.denominazione, 'denominazione', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.formaGiuridica, 'formaGiuridica', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.iban, 'iban', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.settoreAttivita, 'settoreAttivitaBen', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.beneficiario.attivitaAteco, 'attivitaAtecoBen', 'requiredAvvio')) {
          erroreRequiredAvvioBen = true;
        }
      }
    }


    return erroreRequiredAvvioBen;
  }

  checkRapprRequiredAvvio() {
    let erroreRequiredAvvioRappr: boolean = false;

    if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.codiceFiscale, 'codiceFiscaleRappr', 'requiredAvvio')) {
      erroreRequiredAvvioRappr = true;
    }
    if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.nazioneNascita, 'nazioneNascitaRappr', 'requiredAvvio')) {
      erroreRequiredAvvioRappr = true;
    } else {
      if (this.rappresentante.nazioneNascita.descEstesa === "ITALIA") {
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.regioneNascita, 'regioneNascitaRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.provinciaNascita, 'provinciaNascitaRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.comuneNascita, 'comuneNascitaRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
      } else {
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.comuneNascita, 'comuneNascitaEsteroRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
      }
    }
    if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.indirizzoResidenza, 'indirizzoResidenzaRappr', 'requiredAvvio')) {
      erroreRequiredAvvioRappr = true;
    }
    if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.civicoResidenza, 'civicoResidenzaRappr', 'requiredAvvio')) {
      erroreRequiredAvvioRappr = true;
    }
    if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.capResidenza, 'capResidenzaRappr', 'requiredAvvio')) {
      erroreRequiredAvvioRappr = true;
    }
    if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.nazioneResidenza, 'nazioneResidenzaRappr', 'requiredAvvio')) {
      erroreRequiredAvvioRappr = true;
    } else {
      if (this.rappresentante.nazioneResidenza.descEstesa === "ITALIA") {
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.regioneResidenza, 'regioneResidenzaRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.provinciaResidenza, 'provinciaResidenzaRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.comuneResidenza, 'comuneResidenzaRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
      } else {
        if (this.checkFieldRequired(this.soggettiForm, this.rappresentante.comuneResidenza, 'comuneResidenzaEsteroRappr', 'requiredAvvio')) {
          erroreRequiredAvvioRappr = true;
        }
      }
    }
    return erroreRequiredAvvioRappr;
  }

  checkDatiSifRequiredAvvio() {
    let erroreRequiredAvvioDatiSif: boolean = false;

    if (!this.datiSif.dataFirmaAccordo.value) {
      this.datiSif.dataFirmaAccordo.setErrors({ error: 'requiredAvvio' });
      this.datiSif.dataFirmaAccordo.markAsTouched();
      erroreRequiredAvvioDatiSif = true;
    }
    if (!this.datiSif.dataComplValut.value) {
      this.datiSif.dataComplValut.setErrors({ error: 'requiredAvvio' });
      this.datiSif.dataComplValut.markAsTouched();
      erroreRequiredAvvioDatiSif = true;
    }
    return erroreRequiredAvvioDatiSif;
  }

  salvaVerifica() {
    this.resetMessageError();
    this.resetMessageSuccess();
    //ERRORI DI CONGRUITA'
    let erroreCongruitaDatiGen: boolean = this.checkDatiGenCongruita();
    let erroreCongruitaBen: boolean = this.checkBenCongruita();
    let erroreCongruitaRappr: boolean = this.checkRapprCongruita();

    //ERRORI DI OBBLIGATORIETA'
    let erroreRequired: boolean = this.checkDatiRequired();

    //ERRORI DI OBBLIGATORIETA' PER AVVIO
    let erroreRequiredAvvioDatiGen: boolean = this.checkDatiGenRequiredAvvio();
    let erroreRequiredAvvioBen: boolean = this.checkBenRequiredAvvio();
    let erroreRequiredAvvioRappr: boolean = this.checkRapprRequiredAvvio();

    let erroreRequiredAvvioDatiSif: boolean = false;
    if (this.isBandoSif) {
      erroreRequiredAvvioDatiSif = this.checkDatiSifRequiredAvvio();
    }

    this.loadedVerificaCup = false;
    this.subscribers.verificaCup = this.schedaProgettoService.verificaCupUnico(this.datiGen.cup, this.idProgetto).subscribe(data => {
      //data = true se il cup è unico
      if (!data) {
        this.datiGeneraliForm.form.get('cup').setErrors({ error: 'notUnico' });
        erroreCongruitaDatiGen = true;
      }
      this.loadedVerificaCup = true;
      this.loadedVerificaNumDomanda = false;
      this.subscribers.verificaNumDomanda = this.schedaProgettoService.verificaNumeroDomandaUnico(this.datiGen.codProgettoNumDomanda, this.idProgetto, this.idLinea).subscribe(data => {
        //data = true se il num domanda è unico
        if (!data) {
          this.datiGeneraliForm.form.get('codProgettoNumDomanda').setErrors({ error: 'notUnico' });
          erroreCongruitaDatiGen = true;
        }
        this.setMessageError(erroreCongruitaDatiGen || erroreRequired || erroreRequiredAvvioDatiGen, erroreCongruitaBen || erroreRequiredAvvioBen, erroreCongruitaRappr || erroreRequiredAvvioRappr, erroreRequiredAvvioDatiSif);
        if (!erroreCongruitaDatiGen && !erroreCongruitaBen && !erroreCongruitaRappr && !erroreRequired) {
          this.salva(!erroreRequiredAvvioDatiGen && !erroreRequiredAvvioBen && !erroreRequiredAvvioRappr && !erroreRequiredAvvioDatiSif);
        }
        this.loadedVerificaNumDomanda = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di verifica del numero domanda.");
        this.loadedVerificaNumDomanda = true;
        return;
      });
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica del cup.");
      this.loadedVerificaCup = true;
      return;
    });
  }

  getInformazioniBaseFromDatiGen() {
    return new InformazioniBase(this.datiGen.titoloProgetto, this.datiGen.codProgettoNumDomanda, this.datiGen.cup, this.datiGen.dataDecorrenza, this.datiGen.dataGenerazione,
      this.sharedService.formatDateToString(this.datiGen.dataConcessione.value), this.sharedService.formatDateToString(this.datiGen.dataComitato.value),
      this.datiGen.settoreAttivita ? this.datiGen.settoreAttivita.codice : null,
      this.datiGen.attivitaAteco ? this.datiGen.attivitaAteco.codice : null, this.datiGen.prioritaQsn ? this.datiGen.prioritaQsn.codice : null,
      this.datiGen.obiettivoGenQsn ? this.datiGen.obiettivoGenQsn.codice : null, this.datiGen.obiettivoSpecQsn ? this.datiGen.obiettivoSpecQsn.codice : null,
      this.datiGen.strumento ? this.datiGen.strumento.codice : null, this.datiGen.settoreCpt ? this.datiGen.settoreCpt.codice : null,
      this.datiGen.temaPrioritario ? this.datiGen.temaPrioritario.codice : null, this.datiGen.indicatoreRisProg ? this.datiGen.indicatoreRisProg.codice : null,
      this.datiGen.indicatoreQsn ? this.datiGen.indicatoreQsn.codice : null, this.datiGen.tipoAiuto ? this.datiGen.tipoAiuto.codice : null,
      this.datiGen.tipoStrumentoProg ? this.datiGen.tipoStrumentoProg.codice : null, this.datiGen.dimTerritoriale ? this.datiGen.dimTerritoriale.codice : null,
      this.datiGen.progettoComplesso ? this.datiGen.progettoComplesso.codice : null, this.datiGen.settoreCipe ? this.datiGen.settoreCipe.codice : null,
      this.datiGen.sottoSettoreCipe ? this.datiGen.sottoSettoreCipe.codice : null, this.datiGen.categoriaCipe ? this.datiGen.categoriaCipe.codice : null,
      this.datiGen.naturaCipe ? this.datiGen.naturaCipe.codice : null, this.datiGen.tipologiaCipe ? this.datiGen.tipologiaCipe.codice : null, this.datiGen.flagCardine,
      this.datiGen.flagGeneratoreEntrate, this.datiGen.flagLeggeObiettivo, this.datiGen.flagAltroFondo, this.datiGen.flagStatoProgettoProgramma, this.datiGen.flagDettaglioCup,
      this.datiGen.tipoOperazione ? this.datiGen.tipoOperazione.codice : null, this.datiGen.flagAggiungiCup, this.datiGen.flagBeneficiarioCup, this.datiGen.annoConcessioneOld,
      this.sharedService.formatDateToString(this.datiGen.dataPresentazioneDomanda.value), this.datiGen.flagProgettoDaInviareAlMonitoraggio, this.datiGen.flagRichiestaAutomaticaDelCup,
      this.datiGen.obiettivoTematico ? this.datiGen.obiettivoTematico.codice : null, this.datiGen.classificazioneRA ? this.datiGen.classificazioneRA.codice : null,
      this.datiGen.grandeProgetto ? this.datiGen.grandeProgetto.codice : null, this.datiGen.ppp,
      this.datiGen.flagImportanzaStrategica,
      this.isBandoSif && this.datiSif.dataFirmaAccordo ? this.datiSif.dataFirmaAccordo.value : null,
      this.isBandoSif && this.datiSif.dataComplValut ? this.datiSif.dataComplValut.value : null);
  }

  getSoggettoPFFromBeneficiario() {
    return new SoggettoPF(this.beneficiario.iban, this.beneficiario.cognome,
      this.beneficiario.nome, this.beneficiario.sesso, this.sharedService.formatDateToString(this.beneficiario.dataNascita.value), this.beneficiario.indirizzoResidenza,
      this.beneficiario.capResidenza, this.beneficiario.email, this.beneficiario.fax, this.beneficiario.telefono,
      new Comune(this.beneficiario.comuneResidenza ? this.beneficiario.comuneResidenza.descrizione : null,
        this.beneficiario.nazioneResidenza ? this.beneficiario.nazioneResidenza.descEstesa : null,
        this.beneficiario.provinciaResidenza ? this.beneficiario.provinciaResidenza.descrizione : null,
        this.beneficiario.regioneResidenza ? this.beneficiario.regioneResidenza.descrizione : null,
        this.beneficiario.nazioneResidenza && this.beneficiario.nazioneResidenza.descEstesa === "ITALIA" ? "S" : "N",
        this.beneficiario.comuneResidenza ? this.beneficiario.comuneResidenza.codice : null, this.beneficiario.nazioneResidenza ? this.beneficiario.nazioneResidenza.id : null,
        this.beneficiario.provinciaResidenza ? this.beneficiario.provinciaResidenza.codice : null,
        this.beneficiario.regioneResidenza ? this.beneficiario.regioneResidenza.codice : null),
      new Comune(this.beneficiario.comuneNascita ? this.beneficiario.comuneNascita.descrizione : null,
        this.beneficiario.nazioneNascita ? this.beneficiario.nazioneNascita.descEstesa : null,
        this.beneficiario.provinciaNascita ? this.beneficiario.provinciaNascita.descrizione : null,
        this.beneficiario.regioneNascita ? this.beneficiario.regioneNascita.descrizione : null,
        this.beneficiario.nazioneNascita && this.beneficiario.nazioneNascita.descEstesa === "ITALIA" ? "S" : "N",
        this.beneficiario.comuneNascita ? this.beneficiario.comuneNascita.codice : null, this.beneficiario.nazioneNascita ? this.beneficiario.nazioneNascita.id : null,
        this.beneficiario.provinciaNascita ? this.beneficiario.provinciaNascita.codice : null,
        this.beneficiario.regioneNascita ? this.beneficiario.regioneNascita.codice : null), this.beneficiario.codiceFiscalePF,
      this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], null, null, this.beneficiario.civicoResidenza);
  }

  getTab() {
    switch (this.beneficiario.tipoSoggGiur) {
      case "DU":
      case "NA":
        return new TabDirRegSogg(this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], this.beneficiario.iban,
          this.beneficiario.tipoSoggGiur === "DU" && this.beneficiario.ateneo ? this.beneficiario.ateneo.codice : null,
          this.beneficiario.tipoSoggGiur === "DU" && this.beneficiario.dipartimento ? this.beneficiario.dipartimento.codice : null, null, null, null,
          this.beneficiario.tipoSoggGiur === "NA" ? this.beneficiario.denominazione : null, this.beneficiario.tipoSoggGiur === "NA" ? this.beneficiario.codiceFiscaleEG : null,
          this.beneficiario.tipoSoggGiur === "NA" && this.beneficiario.formaGiuridica ? this.beneficiario.formaGiuridica.codice : null,
          this.beneficiario.tipoSoggGiur === "NA" && this.beneficiario.settoreAttivita ? this.beneficiario.settoreAttivita.codice : null,
          this.beneficiario.tipoSoggGiur === "NA" && this.beneficiario.attivitaAteco ? this.beneficiario.attivitaAteco.codice : null, null,
          new Comune(this.beneficiario.comuneSedeLegale ? this.beneficiario.comuneSedeLegale.codice : null,
            this.beneficiario.nazioneSedeLegale ? this.beneficiario.nazioneSedeLegale.descEstesa : null,
            this.beneficiario.provinciaSedeLegale ? this.beneficiario.provinciaSedeLegale.descrizione : null,
            this.beneficiario.regioneSedeLegale ? this.beneficiario.regioneSedeLegale.descrizione : null,
            this.beneficiario.nazioneSedeLegale && this.beneficiario.nazioneSedeLegale.descEstesa === "ITALIA" ? "S" : "N",
            this.beneficiario.comuneSedeLegale ? this.beneficiario.comuneSedeLegale.codice : null,
            this.beneficiario.nazioneSedeLegale ? this.beneficiario.nazioneSedeLegale.id : null,
            this.beneficiario.provinciaSedeLegale ? this.beneficiario.provinciaSedeLegale.codice : null,
            this.beneficiario.regioneSedeLegale ? this.beneficiario.regioneSedeLegale.codice : null), this.beneficiario.partitaIva, this.beneficiario.indirizzoSedeLegale,
          this.beneficiario.capSedeLegale, this.beneficiario.emailSedeLegale, this.beneficiario.pecSedeLegale, this.beneficiario.faxSedeLegale, this.beneficiario.telefonoSedeLegale,
          this.beneficiario.tipoSoggGiur === "NA" ? this.sharedService.formatDateToString(this.beneficiario.dataCostituzione.value) : null, this.beneficiario.civicoSedeLegale,
          null, null);
      case "DR":
        return new TabDirRegSogg(this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], this.beneficiario.iban, null, null,
          this.beneficiario.tipoSoggGiur === "DR" && this.beneficiario.enteDR ? this.beneficiario.enteDR.codice : null, null, null,
          null, null, null, null, null, null, new Comune(this.beneficiario.comuneSedeLegaleDR ? this.beneficiario.comuneSedeLegaleDR.codice : null,
            this.beneficiario.nazioneSedeLegaleDR ? this.beneficiario.nazioneSedeLegaleDR.descEstesa : null,
            this.beneficiario.provinciaSedeLegaleDR ? this.beneficiario.provinciaSedeLegaleDR.descrizione : null,
            this.beneficiario.regioneSedeLegaleDR ? this.beneficiario.regioneSedeLegaleDR.descrizione : null,
            this.beneficiario.nazioneSedeLegaleDR && this.beneficiario.nazioneSedeLegaleDR.descEstesa === "ITALIA" ? "S" : "N",
            this.beneficiario.comuneSedeLegaleDR ? this.beneficiario.comuneSedeLegaleDR.codice : null,
            this.beneficiario.nazioneSedeLegaleDR ? this.beneficiario.nazioneSedeLegaleDR.id : null,
            this.beneficiario.provinciaSedeLegaleDR ? this.beneficiario.provinciaSedeLegaleDR.codice : null,
            this.beneficiario.regioneSedeLegaleDR ? this.beneficiario.regioneSedeLegaleDR.codice : null), this.beneficiario.partitaIvaDR, this.beneficiario.indirizzoSedeLegaleDR,
          this.beneficiario.capSedeLegaleDR, this.beneficiario.emailSedeLegaleDR, this.beneficiario.pecSedeLegaleDR, this.beneficiario.faxSedeLegaleDR, this.beneficiario.telefonoSedeLegaleDR, null,
          this.beneficiario.civicoSedeLegaleDR, this.beneficiario.settoreDR ? this.beneficiario.settoreDR.codice : null, null);
      case "PA":
        return new TabDirRegSogg(this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], this.beneficiario.iban, null, null, null, null, null, null, null, null,
          null, null, null, new Comune(this.beneficiario.comuneSedeLegalePA ? this.beneficiario.comuneSedeLegalePA.codice : null,
            this.beneficiario.nazioneSedeLegalePA ? this.beneficiario.nazioneSedeLegalePA.descEstesa : null,
            this.beneficiario.provinciaSedeLegalePA ? this.beneficiario.provinciaSedeLegalePA.descrizione : null,
            this.beneficiario.regioneSedeLegalePA ? this.beneficiario.regioneSedeLegalePA.descrizione : null,
            this.beneficiario.nazioneSedeLegalePA && this.beneficiario.nazioneSedeLegalePA.descEstesa === "ITALIA" ? "S" : "N",
            this.beneficiario.comuneSedeLegalePA ? this.beneficiario.comuneSedeLegalePA.codice : null,
            this.beneficiario.nazioneSedeLegalePA ? this.beneficiario.nazioneSedeLegalePA.id : null,
            this.beneficiario.provinciaSedeLegalePA ? this.beneficiario.provinciaSedeLegalePA.codice : null,
            this.beneficiario.regioneSedeLegalePA ? this.beneficiario.regioneSedeLegalePA.codice : null), this.beneficiario.partitaIvaPA, this.beneficiario.indirizzoSedeLegalePA,
          this.beneficiario.capSedeLegalePA, this.beneficiario.emailSedeLegalePA, this.beneficiario.pecSedeLegalePA, this.beneficiario.faxSedeLegalePA, this.beneficiario.telefonoSedeLegalePA, null,
          this.beneficiario.civicoSedeLegalePA, this.beneficiario.settorePA ? this.beneficiario.settorePA.codice : null,
          this.beneficiario.entePA ? this.beneficiario.entePA.codice : null);
    }
  }

  getSoggettoEGFromBeneficiario(tabEGVuoto: TabDirRegSogg) {
    switch (this.beneficiario.tipoSoggGiur) {
      case "DU":
      case "NA":
        return new SoggettoPG(this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], this.beneficiario.iban,
          this.beneficiario.tipoSoggGiur === "NA" ? this.beneficiario.denominazione : null,
          this.beneficiario.tipoSoggGiur === "NA" && this.beneficiario.formaGiuridica ? this.beneficiario.formaGiuridica.codice : null,
          this.beneficiario.tipoSoggGiur === "NA" && this.beneficiario.settoreAttivita ? this.beneficiario.settoreAttivita.codice : null,
          this.beneficiario.tipoSoggGiur === "NA" && this.beneficiario.attivitaAteco ? this.beneficiario.attivitaAteco.codice : null, null,
          this.beneficiario.tipoSoggGiur, this.beneficiario.partitaIva, this.beneficiario.indirizzoSedeLegale, this.beneficiario.capSedeLegale, this.beneficiario.emailSedeLegale,
          this.beneficiario.pecSedeLegale, this.beneficiario.faxSedeLegale, this.beneficiario.telefonoSedeLegale,
          this.beneficiario.tipoSoggGiur === "NA" ? this.sharedService.formatDateToString(this.beneficiario.dataCostituzione.value) : null,
          null, this.beneficiario.tipoSoggGiur === "DU" && this.beneficiario.ateneo ? this.beneficiario.ateneo.codice : null,
          this.beneficiario.tipoSoggGiur === "DU" && this.beneficiario.dipartimento ? this.beneficiario.dipartimento.codice : null,
          new Comune(this.beneficiario.comuneSedeLegale ? this.beneficiario.comuneSedeLegale.codice : null,
            this.beneficiario.nazioneSedeLegale ? this.beneficiario.nazioneSedeLegale.descEstesa : null,
            this.beneficiario.provinciaSedeLegale ? this.beneficiario.provinciaSedeLegale.descrizione : null,
            this.beneficiario.regioneSedeLegale ? this.beneficiario.regioneSedeLegale.descrizione : null,
            this.beneficiario.nazioneSedeLegale && this.beneficiario.nazioneSedeLegale.descEstesa === "ITALIA" ? "S" : "N",
            this.beneficiario.comuneSedeLegale ? this.beneficiario.comuneSedeLegale.codice : null,
            this.beneficiario.nazioneSedeLegale ? this.beneficiario.nazioneSedeLegale.id : null,
            this.beneficiario.provinciaSedeLegale ? this.beneficiario.provinciaSedeLegale.codice : null,
            this.beneficiario.regioneSedeLegale ? this.beneficiario.regioneSedeLegale.codice : null),
          this.beneficiario.tipoSoggGiur === "NA" ? this.beneficiario.codiceFiscaleEG : null, null, null, this.beneficiario.tipoSoggGiur === "DU" ? this.getTab() : tabEGVuoto,
          tabEGVuoto, this.beneficiario.tipoSoggGiur === "NA" ? this.getTab() : tabEGVuoto, this.beneficiario.civicoSedeLegale, null, tabEGVuoto, null);
      case "DR":
        return new SoggettoPG(this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], this.beneficiario.iban, null, null, null, null, null,
          this.beneficiario.tipoSoggGiur, this.beneficiario.partitaIvaDR, this.beneficiario.indirizzoSedeLegaleDR, this.beneficiario.capSedeLegaleDR,
          this.beneficiario.emailSedeLegaleDR, this.beneficiario.pecSedeLegaleDR, this.beneficiario.faxSedeLegaleDR, this.beneficiario.telefonoSedeLegaleDR, null,
          this.beneficiario.enteDR ? this.beneficiario.enteDR.codice : null, null, null,
          new Comune(this.beneficiario.comuneSedeLegaleDR ? this.beneficiario.comuneSedeLegaleDR.codice : null,
            this.beneficiario.nazioneSedeLegaleDR ? this.beneficiario.nazioneSedeLegaleDR.descEstesa : null,
            this.beneficiario.provinciaSedeLegaleDR ? this.beneficiario.provinciaSedeLegaleDR.descrizione : null,
            this.beneficiario.regioneSedeLegaleDR ? this.beneficiario.regioneSedeLegaleDR.descrizione : null,
            this.beneficiario.nazioneSedeLegaleDR && this.beneficiario.nazioneSedeLegaleDR.descEstesa === "ITALIA" ? "S" : "N",
            this.beneficiario.comuneSedeLegaleDR ? this.beneficiario.comuneSedeLegaleDR.codice : null,
            this.beneficiario.nazioneSedeLegaleDR ? this.beneficiario.nazioneSedeLegaleDR.id : null,
            this.beneficiario.provinciaSedeLegaleDR ? this.beneficiario.provinciaSedeLegaleDR.codice : null,
            this.beneficiario.regioneSedeLegaleDR ? this.beneficiario.regioneSedeLegaleDR.codice : null), null, null, null, tabEGVuoto, this.getTab(), tabEGVuoto,
          this.beneficiario.civicoSedeLegaleDR, this.beneficiario.settoreDR ? this.beneficiario.settoreDR.codice : null, tabEGVuoto, null);
      case "PA":
        return new SoggettoPG(this.beneficiario.ruoli ? this.beneficiario.ruoli.map(r => r.codice) : [], this.beneficiario.iban, null, null, null, null, null,
          this.beneficiario.tipoSoggGiur, this.beneficiario.partitaIvaPA, this.beneficiario.indirizzoSedeLegalePA, this.beneficiario.capSedeLegalePA,
          this.beneficiario.emailSedeLegalePA, this.beneficiario.pecSedeLegalePA, this.beneficiario.faxSedeLegalePA, this.beneficiario.telefonoSedeLegalePA, null, null, null, null,
          new Comune(this.beneficiario.comuneSedeLegalePA ? this.beneficiario.comuneSedeLegalePA.codice : null,
            this.beneficiario.nazioneSedeLegalePA ? this.beneficiario.nazioneSedeLegalePA.descEstesa : null,
            this.beneficiario.provinciaSedeLegalePA ? this.beneficiario.provinciaSedeLegalePA.descrizione : null,
            this.beneficiario.regioneSedeLegalePA ? this.beneficiario.regioneSedeLegalePA.descrizione : null,
            this.beneficiario.nazioneSedeLegalePA && this.beneficiario.nazioneSedeLegalePA.descEstesa === "ITALIA" ? "S" : "N",
            this.beneficiario.comuneSedeLegalePA ? this.beneficiario.comuneSedeLegalePA.codice : null,
            this.beneficiario.nazioneSedeLegalePA ? this.beneficiario.nazioneSedeLegalePA.id : null,
            this.beneficiario.provinciaSedeLegalePA ? this.beneficiario.provinciaSedeLegalePA.codice : null,
            this.beneficiario.regioneSedeLegalePA ? this.beneficiario.regioneSedeLegalePA.codice : null), null, null, null, tabEGVuoto, tabEGVuoto, tabEGVuoto,
          this.beneficiario.civicoSedeLegalePA, this.beneficiario.settorePA ? this.beneficiario.settorePA.codice : null, this.getTab(),
          this.beneficiario.entePA ? this.beneficiario.entePA.codice : null);
    }
  }

  getSoggettoPFFromRappresentante() {
    return new SoggettoPF(null, this.rappresentante.cognome, this.rappresentante.nome, this.rappresentante.sesso,
      this.sharedService.formatDateToString(this.rappresentante.dataNascita.value),
      this.rappresentante.indirizzoResidenza, this.rappresentante.capResidenza, this.rappresentante.email, this.rappresentante.fax, this.rappresentante.telefono,
      new Comune(this.rappresentante.comuneResidenza ? this.rappresentante.comuneResidenza.descrizione : null,
        this.rappresentante.nazioneResidenza ? this.rappresentante.nazioneResidenza.descEstesa : null,
        this.rappresentante.provinciaResidenza ? this.rappresentante.provinciaResidenza.descrizione : null,
        this.rappresentante.regioneResidenza ? this.rappresentante.regioneResidenza.descrizione : null,
        this.rappresentante.nazioneResidenza && this.rappresentante.nazioneResidenza.descEstesa === "ITALIA" ? "S" : "N",
        this.rappresentante.comuneResidenza ? this.rappresentante.comuneResidenza.codice : null,
        this.rappresentante.nazioneResidenza ? this.rappresentante.nazioneResidenza.id : null,
        this.rappresentante.provinciaResidenza ? this.rappresentante.provinciaResidenza.codice : null,
        this.rappresentante.regioneResidenza ? this.rappresentante.regioneResidenza.codice : null),
      new Comune(this.rappresentante.comuneNascita ? this.rappresentante.comuneNascita.descrizione : null,
        this.rappresentante.nazioneNascita ? this.rappresentante.nazioneNascita.descEstesa : null,
        this.rappresentante.provinciaNascita ? this.rappresentante.provinciaNascita.descrizione : null,
        this.rappresentante.regioneNascita ? this.rappresentante.regioneNascita.descrizione : null,
        this.rappresentante.nazioneNascita && this.rappresentante.nazioneNascita.descEstesa === "ITALIA" ? "S" : "N",
        this.rappresentante.comuneNascita ? this.rappresentante.comuneNascita.codice : null,
        this.rappresentante.nazioneNascita ? this.rappresentante.nazioneNascita.id : null,
        this.rappresentante.provinciaNascita ? this.rappresentante.provinciaNascita.codice : null,
        this.rappresentante.regioneNascita ? this.rappresentante.regioneNascita.codice : null), this.rappresentante.codiceFiscale, [], null, null,
      this.rappresentante.civicoResidenza);
  }

  salva(datiCompletiPerAvvio: boolean) {
    this.loadedSalva = false;
    let comuneVuoto = new Comune(null, null, null, null, "S", null, "118", null, "1");
    let tabEGVuoto = new TabDirRegSogg([], null, null, null, null, null, null, null, null, null, null, null, null, comuneVuoto, null, null, null, null, null, null, null,
      null, null, null, null);
    let soggPFVuoto = new SoggettoPF(null, null, null, null, null, null, null, null, null, null, comuneVuoto, comuneVuoto, null, [], null, null, null);
    let soggEGVuoto = new SoggettoPG([], null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, comuneVuoto, null, null, null,
      tabEGVuoto, tabEGVuoto, tabEGVuoto, null, null, tabEGVuoto, null);
    let ben = new SoggettoGenerico(this.beneficiario.id, this.beneficiario.tipoSoggetto === "PF" ? this.getSoggettoPFFromBeneficiario() : soggPFVuoto,
      this.beneficiario.tipoSoggetto === "PF" ? soggEGVuoto : this.getSoggettoEGFromBeneficiario(tabEGVuoto), this.beneficiario.tipoSoggetto === "PF" ? "S" : "N");
    let rappr = new SoggettoGenerico(this.rappresentante.id, this.getSoggettoPFFromRappresentante(), soggEGVuoto, "S");
    let schedaProgetto = new SchedaProgetto(this.idProgetto ? this.idProgetto.toString() : null, this.idLinea.toString(), this.idLineaNormativa, this.idLineaAsse,
      this.flagSalvaIntermediario, this.getInformazioniBaseFromDatiGen(), this.sediIntervento, ben, rappr, this.intermediario, this.altriSoggetti, this.sedeInterventoDefault,
      this.altroSoggettoDefault);
    let request = new SalvaSchedaProgettoRequest(schedaProgetto, datiCompletiPerAvvio);
    console.log(request);
    this.subscribers.salva = this.schedaProgettoService.salvaSchedaProgetto(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          if (!this.idProgetto) {
            let params = { messaggio: messaggio };
            this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO}/progetto/${this.idLinea}/${data.id}`, params]);
          } else {
            this.showMessageSuccess(messaggio);
          }
        } else {
          this.showMessageError(messaggio);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  setMessageError(erroreDatiGen: boolean, erroreBen: boolean, erroreRappr: boolean, erroreDatiSif: boolean) {
    let message: string = "";
    if (erroreDatiGen || erroreBen || erroreRappr || (erroreDatiSif && this.isBandoSif)) {
      message += "Dati formalmente errati o non congrui nelle sezioni: "
      if (erroreDatiGen) {
        message += "Scheda Dati Generali"
      }
      if (erroreDatiGen && (erroreBen || erroreRappr)) {
        message += ", ";
      }
      if (erroreBen || erroreRappr) {
        message += "Schede Soggetti / (" + (erroreBen ? "Scheda Beneficiario" : "") + (erroreBen && erroreRappr ? ", " : "")
          + (erroreRappr ? "Scheda Rappr.Legale)" : ")");
      }
      if ((erroreDatiGen || erroreBen || erroreRappr) && erroreDatiSif && this.isBandoSif) {
        message += ", ";
      }
      if (erroreDatiSif && this.isBandoSif) {
        message += "Scheda Dati SIF";
      }
    }
    if ((erroreDatiGen || erroreBen || erroreRappr || (erroreDatiSif && this.isBandoSif))
      && (!this.sediIntervento || this.sediIntervento.length === 0 || !this.beneficiario.ruoli || !this.beneficiario.ruoli.find(r => r.codice === "3" || r.codice === "11"))) {
      message += "<br>";
    }
    if (!this.sediIntervento || this.sediIntervento.length === 0) {
      message += "Nessuna <span class='bold-text'>sede intervento</span> inserita.";
    }
    if ((!this.sediIntervento || this.sediIntervento.length === 0) && (!this.beneficiario.ruoli || !this.beneficiario.ruoli.find(r => r.codice === "3" || r.codice === "11"))) {
      message += "<br>";
    }
    if (!this.beneficiario.ruoli || !this.beneficiario.ruoli.find(r => r.codice === "3" || r.codice === "11")) {
      message += "Per poter avviare il progetto è necessario associare il ruolo <span class='bold-text'>Attuatore</span> al Beneficiario.";
    }
    if (message.length > 0) {
      this.showMessageError(message);
    }
  }

  goToAvvioProgetto() {
    this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO}/avvioProgetto/${this.idLinea}`)
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializzaSchedaProgetto || !this.loadedInizializzaCombo || !this.loadedCombo || !this.loadedCarica
      || !this.loadedConferma || !this.loadedVerificaNumDomanda || !this.loadedVerificaCup || !this.loadedSalva
      || !this.loadedIsSif) {
      return true;
    }
    return false;
  }

  ngAfterViewChecked() {
    this.tabs.realignInkBar();
  }

}
