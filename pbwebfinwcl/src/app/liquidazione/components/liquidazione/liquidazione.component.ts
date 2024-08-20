/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizioneDTO } from 'src/app/gestione-disimpegni/commons/codice-descrizione-vo';
import { EnteCompetenzaDTO } from 'src/app/gestione-disimpegni/commons/ente-competenza-vo';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AliquotaRienutaDTO } from '../../commons/aliquota-ritenuta-vo';
import { Beneficiario } from '../../commons/beneficiario';
import { BeneficiarioBilancioDTO } from '../../commons/beneficiario-bilancio-vo';
import { CausaleLiquidazioneDTO } from '../../commons/causale-liquidazione-vo';
import { DatiIntegrativiDTO } from '../../commons/dati-integrativi-vo';
import { DettaglioBeneficiarioBilancioDTO } from '../../commons/dettaglio-beneficiario-bilancio-vo';
import { FideiussioneDTO } from '../../commons/fideussione-vo';
import { FonteFinanziariaDTO } from '../../commons/fonte-finanziaria-vo';
import { ImpegniLiquidazioneDTO } from '../../commons/impegni-liquidazione-vo';
import { Liquidazione } from '../../commons/liquidazione';
import { LiquidazioneDTO } from '../../commons/liquidazione-vo';
import { RichiestaErogazioneDTO } from '../../commons/richiesta-erogazione-vo';
import { RipartizioneImpegniLiquidazioneDTO } from '../../commons/ripartizione-impegni-liquidazione-vo';
import { SettoreEnteDTO } from '../../commons/settore-ente-vo';
import { LiquidazioneService } from '../../services/liquidazione.service';

@Component({
  selector: 'app-liquidazione',
  templateUrl: './liquidazione.component.html',
  styleUrls: ['./liquidazione.component.scss']
})
export class LiquidazioneComponent implements OnInit {

  displayedColumnsFideusBancProg: string[] = ['numero', 'data', 'importo', 'dataScadenza'];
  dataSourceFideusBancProg: MatTableDataSource<FideiussioneDTO>;

  displayedColumnsRichLiquidProg: string[] = ['causaleLiquidazione', 'numero', 'data', 'importoRichiesto'];
  dataSourceRichLiquidProg: MatTableDataSource<RichiestaErogazioneDTO>;

  displayedColumnsQuotLiquid: string[] = ['desc', 'dataAtto', 'numeroAtto', 'importoAtto'];
  dataSourceQuotLiquid: MatTableDataSource<CausaleLiquidazioneDTO>;

  displayedColumnsRipFontiFinanz: string[] = ['fonteFinanziaria', 'perc', 'importo'];
  dataSourceRipFontiFinanz: MatTableDataSource<FonteFinanziariaDTO>;

  displayedColumnsRipartizImpLiqui: string[] = ['fonteFinanziaria', 'annoEsercizio', 'capNum', 'impAnnNum', 'impReimpAnnNum', 'importoImpegno', 'dispResid', 'impDaliquidSuImp', 'CUPimP'];
  dataSourceRipartizImpLiqui: MatTableDataSource<RipartizioneImpegniLiquidazioneDTO>;

  dataSourceBeneficiarioLiquidazione: MatTableDataSource<BeneficiarioBilancioDTO>;
  displayedColumnsBeneficiarioLiquidazione = ['codiceBilancio', 'codFisc', 'pIVA', 'nominativo', 'sede', 'IBAN', 'modPag'];

  dataSourceBeneficiarioBilancio: MatTableDataSource<DettaglioBeneficiarioBilancioDTO>;
  displayedColumnsBeneficiarioBilancio = ['select', 'codiceBilancio', 'codFisc', 'pIVA', 'nominativo', 'sede', 'IBAN', 'modPag'];

  beneficiario: string;
  codiceProgetto: string;
  panelOpenState = false;

  causaleLiquidazioneSelected: CodiceDescrizioneDTO;
  causaliLiquidazione: Array<CodiceDescrizioneDTO>;

  enteCompetenzaSelected: EnteCompetenzaDTO;
  entiCompetenza: Array<EnteCompetenzaDTO>;

  settoreCompetenzaSelected: SettoreEnteDTO;
  settoriCompetenza: Array<SettoreEnteDTO>;

  naturaOnereCodiceTributoSelected: string;
  natureOnereCodiceTributo: Array<string>;

  idProgetto: number;
  idBando: number;

  percLiquidEffet: number;
  percLiquidEffetFormatted: string;
  impLiquidEffet: number;
  impLiquidEffetFormatted: string;
  causalePagamento: string;
  noteAttoLiquid: string;
  funzionarioLiquidatore: string;
  nTelefono: string;
  diretDirigResponsabile: string;
  imponibile: number;
  imponibileFormatted: string;
  sommaNonSoggetta: number;
  sommaNonSoggettaFormatted: string;
  percCaricoEnte: number;
  percCaricoEnteFormatted: string;
  percCaricoSoggetto: number;
  percCaricoSoggettoFormatted: string;
  importoCaricoEnte: number;
  importoCaricoEnteFormatted: string;
  importoCaricoSoggetto: number;
  importoCaricoSoggettoFormatted: string;

  showBeneficiariBilancio: boolean;

  dataScadenza: FormControl = new FormControl();

  selectionBeneficiariBilancio = new SelectionModel<DettaglioBeneficiarioBilancioDTO>(true, []);
  showButtonAssociaBeneficiariBilancio: boolean;
  showEsitoAttoLiquidazione: boolean;

  datiIntegrativi: DatiIntegrativiDTO;
  beneficiarioLiquidazioneData: Beneficiario;

  showFieldsRitenute = true;

  // Dichiarazione variabili liquidazione
  tipoOperazione: string;
  totaleSpesaAmmessa: string;
  totSpesaSostDich: string;
  avanzamentoSpesaSostDich: string;
  totSpesaValidata: string;
  avanzamentoSpesaValidata: string;
  modalitaAgevolazioneQuoteLiquid: string;
  ultimoImportoAgevolato: string;
  importoLiquidato: string;
  importoResiduoDaLiquidare: string;
  statoLiquidazione: string;
  percLiquidIterFinanziario: string;
  importoDaLiquidareIterFinanziario: string;

  disableFieldsLiquidContrInCorso: boolean;

  totaleRipImpLiquid: string;

  // Dichiarazione variabili riepilogo fondi
  cupProgetto: string;

  // Dichiarazione variabili ritenute
  naturaOnereCodiceTributo: string;

  // Dichiarazione variabili creazione atto
  importoAttoLiquidazione: string;
  direzione: string;
  settore: string;
  codiceProgettoCreazioneAtto: string;
  beneficiarioCreazioneAtto: string;
  causale: string;
  note: string;
  cupProgettoCreazioneAtto: string;
  numeroDocumentoSpesa: string;
  dataAtto: string;
  annoAtto: string;
  numeroAtto: string;
  esitoOperazione: string;
  statoOperazione: string;
  tabIndex = 0;
  showTabLiquidazione = true;
  showTabRiepilogoFondi = false;
  showTabBeneficiario = false;
  showTabDatiIntegrativi = false;
  showTabRitenute = false;
  showTabCreazioneAtto = false;
  showCreaAttoButton = true;

  // Dichiarazione variabili dettaglio beneficiario
  codiceBilancioDettBen: string;
  progressivoBilancioDettBen: string;
  codiceFiscaleDettBen: string;
  pIvaDettBen: string;
  ragioneSocialeDettBen: string;
  ragioneSocialeSedeDettBen: string;
  statoDettBen: string;
  provinciaDettBen: string;
  comuneDettBen: string;
  indirizzoDettBen: string;
  capDettBen: string;
  emailDettBen: string;
  modalitaPagamentoDettBen: string;
  ibanDettBen: string;
  bicDettBen: string;
  bancaDettBen: string;
  agenziaDettBen: string;
  contoCorrenteDettBen: string;

  ibanPresent: boolean;
  user: UserInfoSec;

  datiGenerali: Liquidazione;
  datiRitenute: AliquotaRienutaDTO;

  messageWarning: string;
  isMessageWarningVisible: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;

  totaleInizialeRipartizImpLiquid: number;

  @ViewChild("liquidazioneForm", { static: false }) liquidazioneForm: NgForm;
  @ViewChild("riepFondiForm", { static: false }) riepFondiForm: NgForm;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // loaaded
  loadedCaricaDatiGenerali: boolean;
  loadedRiepilogoFondi: boolean;
  loadedBeneficiario: boolean;
  loadedRitenute: boolean;
  loadedTabCreaAtto: boolean;
  loadedDatiIntegrativi: boolean;
  loadedCreaAtto: boolean;
  loadedAggiornaBeneficiario: boolean;
  loadedApriDettaglioBeneficiario: boolean;
  loadedCercaSettoreAppartenenza: boolean;
  loadedChiudiAttivita: boolean = true;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  showIndietroCreaAttoButton = true;
  showAvantiRiepFondiButton = true;
  showAvantiBenefButton = true;

  constructor(
    private sharedService: SharedService,
    private activatedRoute: ActivatedRoute,
    private configService: ConfigService,
    private liquidazioneService: LiquidazioneService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private router: Router
  ) {
    this.dataSourceQuotLiquid = new MatTableDataSource();
    this.dataSourceFideusBancProg = new MatTableDataSource();
    this.dataSourceRichLiquidProg = new MatTableDataSource();
    this.dataSourceRipFontiFinanz = new MatTableDataSource();
    this.dataSourceRipartizImpLiqui = new MatTableDataSource();
    this.dataSourceBeneficiarioLiquidazione = new MatTableDataSource();
    this.dataSourceBeneficiarioBilancio = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBando = +params['idBando'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
        this.codiceProgetto = this.idProgetto.toString();

        this.loadedCaricaDatiGenerali = true;
        this.liquidazioneService.caricaDatiGenerali(this.user, this.user.beneficiarioSelezionato.idBeneficiario.toString(), this.idBando.toString(), this.idProgetto.toString()).subscribe(data => {
          this.loadedCaricaDatiGenerali = false;
          this.datiGenerali = data;
          this.codiceProgetto = data.datiGeneraliDTO.codiceProgetto;
          // Assegnazione variabili liquidazione
          this.tipoOperazione = data.esitoDatiProgettoDTO.spesaProgetto.tipoOperazione;
          this.totaleSpesaAmmessa = data.esitoDatiProgettoDTO.spesaProgetto.totaleSpesaAmmessa.toString();
          this.totSpesaSostDich = data.esitoDatiProgettoDTO.spesaProgetto.totaleSpesaSostenuta.toString();
          this.avanzamentoSpesaSostDich = data.esitoDatiProgettoDTO.spesaProgetto.avanzamentoSpesaSostenuta == null ? null : data.esitoDatiProgettoDTO.spesaProgetto.avanzamentoSpesaSostenuta.toString();
          this.totSpesaValidata = data.esitoDatiProgettoDTO.spesaProgetto.totaleSpesaValidata.toString();
          this.avanzamentoSpesaValidata = data.esitoDatiProgettoDTO.spesaProgetto.avanzamentoSpesaValidata == null ? null : data.esitoDatiProgettoDTO.spesaProgetto.avanzamentoSpesaValidata.toString();
          this.modalitaAgevolazioneQuoteLiquid = data.modalitaAgevolazioneLiquidazioneDTO[0].descModalitaAgevolazione;
          this.ultimoImportoAgevolato = data.modalitaAgevolazioneLiquidazioneDTO[0].quotaImportoAgevolato.toString();
          this.importoLiquidato = data.modalitaAgevolazioneLiquidazioneDTO[0].totaleImportoLiquidato.toString();
          this.importoResiduoDaLiquidare = (data.modalitaAgevolazioneLiquidazioneDTO[0].quotaImportoAgevolato - data.modalitaAgevolazioneLiquidazioneDTO[0].totaleImportoLiquidato).toString();

          if (!(data.esitoLeggiAttoLiquidazioneDTO.message == null)) {
            this.showMessageError("ATTENZIONE! La liquidazione è ritornata in stato BOZZA a causa del seguente errore: " + data.esitoLeggiAttoLiquidazioneDTO.message + "Correggere prima di proseguire con la creazione dell'atto. La finestra consente di modificare i dati dell'atto e inviare un nuovo atto a Contabilia.");
            this.statoLiquidazione = "Bozza"
          } else {
            if (data.esitoLeggiAttoLiquidazioneDTO.liquidazione.idStatoAtto == 1) {
              this.statoLiquidazione = "Bozza"
              this.showMessageWarning("E’ stata trovata una liquidazione in stato bozza. E’ possibile completarla.");
            } else {
              this.statoLiquidazione = "Nuova"
            }
          }

          this.impLiquidEffet = data.esitoLeggiAttoLiquidazioneDTO.liquidazione.importoLiquidazioneEffettiva == null ? 0 : data.esitoLeggiAttoLiquidazioneDTO.liquidazione.importoLiquidazioneEffettiva;
          this.setImpLiquidEffFormatted();
          this.dataSourceQuotLiquid = new MatTableDataSource(data.modalitaAgevolazioneLiquidazioneDTO[0].causaliLiquidazioni);
          this.dataSourceFideusBancProg = new MatTableDataSource(data.esitoDatiProgettoDTO.fideiussioni);
          this.dataSourceRichLiquidProg = new MatTableDataSource(data.esitoDatiProgettoDTO.richiesteErogazione);
          this.causaliLiquidazione = data.codiceDescrizioneErogazioneDTO;
          this.causaliLiquidazione.push(new CodiceDescrizioneDTO("PA-INS@0@0", "primo acconto - iter non standard"));
          this.causaliLiquidazione.push(new CodiceDescrizioneDTO("SA-INS@0@0", "successivo acconto - iter non standard"));
          this.causaliLiquidazione.push(new CodiceDescrizioneDTO("SAL-INS@0@0", "saldo - iter non standard"));
          this.causaleLiquidazioneSelected = this.causaliLiquidazione.find(x => x.codice.toString() == data.esitoLeggiAttoLiquidazioneDTO.liquidazione.codCausaleLiquidazione);
          if (this.causaleLiquidazioneSelected) {
            this.percLiquidEffet = +this.causaleLiquidazioneSelected.codice.split("@")[1] || null;
            this.setPercLiquidEffFormatted();
            this.percLiquidIterFinanziario = this.causaleLiquidazioneSelected.codice.split("@")[1];
          }
          if (this.percLiquidIterFinanziario) {
            this.importoDaLiquidareIterFinanziario = ((Number(this.ultimoImportoAgevolato) / 100) * Number(this.percLiquidIterFinanziario)).toString();
          }

          if (this.causaleLiquidazioneSelected && (this.causaleLiquidazioneSelected.codice == "PA-INS@0@0" || this.causaleLiquidazioneSelected.codice == "SA-INS@0@0"
            || this.causaleLiquidazioneSelected.codice == "SAL-INS@0@0")) {
            this.disableFieldsLiquidContrInCorso = false;
          } else {
            this.disableFieldsLiquidContrInCorso = true;
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedCaricaDatiGenerali = false;
          this.showMessageError("Errore in fase di ottenimento dei dati generali");
        });

        this.showBeneficiariBilancio = false;
        this.showButtonAssociaBeneficiariBilancio = false;
        this.showEsitoAttoLiquidazione = false;

        // Assegnazione variabili creazione atto
        this.importoAttoLiquidazione = "100,00";
        this.direzione = "A12000 DIREZIONE GABINETTO DELLA PRESIDENZA DELLA GIUNTA REGIONALE";
        this.settore = "01A Affari internazionali e cooperazione decentrata";
        this.codiceProgettoCreazioneAtto = "A19_POR-FESR-2014-2020_2019_FD5888";
        this.beneficiarioCreazioneAtto = "COMUNE DI SAN DAMIANO D'ASTI";
        this.causale = "successivo acconto - iter non standard";
        this.note = "N.D.";
        this.cupProgettoCreazioneAtto = "Lorem ipsum";
        this.numeroDocumentoSpesa = "2800864A19_POR-FESR-2014-2020_2019_FD5888";
        this.dataAtto = "13/07/2021";
        this.annoAtto = "2021";
        this.numeroAtto = "N.D.";
        this.esitoOperazione = "SUCCESSO";
        this.statoOperazione = "AVVIATA";
      }
    });
  }



  setPercLiquidEff() {
    this.percLiquidEffet = this.sharedService.getNumberFromFormattedString(this.percLiquidEffetFormatted);
    this.setPercLiquidEffFormatted();
    this.percLiquidEffetChanged();
  }

  setPercLiquidEffFormatted() {
    if (this.percLiquidEffet !== null) {
      this.percLiquidEffetFormatted = this.sharedService.formatValue(this.percLiquidEffet.toString());
    }
  }

  setImpLiquidEff() {
    this.impLiquidEffet = this.sharedService.getNumberFromFormattedString(this.impLiquidEffetFormatted);
    this.setImpLiquidEffFormatted();
    this.impLiquidEffetChanged();
  }

  setImpLiquidEffFormatted() {
    if (this.impLiquidEffet !== null) {
      this.impLiquidEffetFormatted = this.sharedService.formatValue(this.impLiquidEffet.toString());
    }
  }

  setImpDaliquidSuImp(element: RipartizioneImpegniLiquidazioneDTO) {
    element.importoDaLiquidare = this.sharedService.getNumberFromFormattedString(element.importoDaLiquidareFormatted);
    this.setImpDaliquidSuImpFormatted(element);
  }

  setImpDaliquidSuImpFormatted(element: RipartizioneImpegniLiquidazioneDTO) {
    if (element.importoDaLiquidare !== null) {
      element.importoDaLiquidareFormatted = this.sharedService.formatValue(element.importoDaLiquidare.toString());
    }
  }

  setImponibile() {
    this.imponibile = this.sharedService.getNumberFromFormattedString(this.imponibileFormatted);
    this.setImponibileFormatted();
    this.imponibileChanged();
  }

  setImponibileFormatted() {
    if (this.imponibile !== null) {
      this.imponibileFormatted = this.sharedService.formatValue(this.imponibile.toString());
    }
  }

  setSommaNonSoggetta() {
    this.sommaNonSoggetta = this.sharedService.getNumberFromFormattedString(this.sommaNonSoggettaFormatted);
    this.setSommaNonSoggettaFormatted();
    this.imponibileChanged();
  }

  setSommaNonSoggettaFormatted() {
    if (this.sommaNonSoggetta !== null) {
      this.sommaNonSoggettaFormatted = this.sharedService.formatValue(this.sommaNonSoggetta.toString());
    }
  }

  setPercCaricoEnte() {
    this.percCaricoEnte = this.sharedService.getNumberFromFormattedString(this.percCaricoEnteFormatted);
    this.setPercCaricoEnteFormatted();
  }

  setPercCaricoEnteFormatted() {
    if (this.percCaricoEnte !== null) {
      this.percCaricoEnteFormatted = this.sharedService.formatValue(this.percCaricoEnte.toString());
    }
  }

  setPercCaricoSoggetto() {
    this.percCaricoSoggetto = this.sharedService.getNumberFromFormattedString(this.percCaricoSoggettoFormatted);
    this.setPercCaricoSoggettoFormatted();
  }

  setPercCaricoSoggettoFormatted() {
    if (this.percCaricoSoggetto !== null) {
      this.percCaricoSoggettoFormatted = this.sharedService.formatValue(this.percCaricoSoggetto.toString());
    }
  }

  setImpCaricoEnte() {
    this.importoCaricoEnte = this.sharedService.getNumberFromFormattedString(this.importoCaricoEnteFormatted);
    this.setImpCaricoEnteFormatted();
  }

  setImpCaricoEnteFormatted() {
    if (this.importoCaricoEnte !== null) {
      this.importoCaricoEnteFormatted = this.sharedService.formatValue(this.importoCaricoEnte.toString());
    }
  }

  setImpCaricoSoggetto() {
    this.importoCaricoSoggetto = this.sharedService.getNumberFromFormattedString(this.importoCaricoSoggettoFormatted);
    this.setImpCaricoSoggettoFormatted();
  }

  setImpCaricoSoggettoFormatted() {
    if (this.importoCaricoSoggetto !== null) {
      this.importoCaricoSoggettoFormatted = this.sharedService.formatValue(this.importoCaricoSoggetto.toString());
    }
  }


  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isAllSelectedBeneficiariBilancio() {
    const numSelected = this.selectionBeneficiariBilancio.selected.length;
    let numRows;
    if (this.dataSourceBeneficiarioBilancio == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSourceBeneficiarioBilancio.data.length;
    }

    return numSelected === numRows;
  }

  checkboxLabelBeneficiariBilancio(row?: DettaglioBeneficiarioBilancioDTO): string {
    if (!row) {
      return `${this.isAllSelectedBeneficiariBilancio() ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionBeneficiariBilancio.isSelected(row) ? 'deselect' : 'select'} row ${row.idBeneficiarioBilancio + 1}`;
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }

  changeSingleRowBeneficiariBilancio(row: any) {
    if (this.selectionBeneficiariBilancio.selected.length == 0) {
      this.selectionBeneficiariBilancio.toggle(row);
    } else {
      if (this.selectionBeneficiariBilancio.selected[0] == row) {
        this.selectionBeneficiariBilancio.toggle(row);
      } else {
        this.selectionBeneficiariBilancio.clear();
        this.selectionBeneficiariBilancio.toggle(row);
      }
    }

    if (this.selectionBeneficiariBilancio.selected.length == 0) {
      this.showButtonAssociaBeneficiariBilancio = false;
    } else {
      this.showButtonAssociaBeneficiariBilancio = true;
    }
  }

  tornaAlleAttivitaDaSvolg() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_LIQUIDAZIONE).subscribe(() => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  avantiLiquid() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.impLiquidEffet || this.impLiquidEffet <= 0) {
      this.liquidazioneForm.form.get("impLiquidEffet").setErrors({ error: "notPositive" });
    }
    if (!this.percLiquidEffet || this.percLiquidEffet <= 0) {
      this.liquidazioneForm.form.get("percLiquidEffet").setErrors({ error: "notPositive" });
    }
    this.liquidazioneForm.form.markAllAsTouched();
    if (!this.causaleLiquidazioneSelected || !this.liquidazioneForm.form.valid) {
      this.showMessageError("ATTENZIONE! Compilare i campi obbligatori.");
      return;
    }
    if (Number(this.importoResiduoDaLiquidare) == 0) {
      this.showMessageError("ATTENZIONE! Impossibile proseguire il residuo spettante è uguale a 0");
    } else {
      if (Number(this.impLiquidEffet) > Number(this.importoResiduoDaLiquidare)) {
        this.showMessageError("ATTENZIONE! La quota da liquidare è superiore al residuo spettante");
      } else {
        this.showTabLiquidazione = false;
        this.showTabRiepilogoFondi = true;
        this.tabIndex = 1;
        this.isMessageWarningVisible = false;
        this.isMessageErrorVisible = false;
        this.showAvantiRiepFondiButton = true;

        let oggettoLiquidazione = new LiquidazioneDTO(this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idAttoLiquidazione,
          this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idCausaleLiquidazione, Number(this.percLiquidEffet), Number(this.impLiquidEffet),
          this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idModalitaAgevolazione, this.causaleLiquidazioneSelected ? this.causaleLiquidazioneSelected.codice.toString() : null, this.idProgetto,
          this.user.idSoggetto, this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idSettoreEnte,
          this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.descStatoAtto, this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idStatoAtto,
          this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.numeroDocumentoSpesa);

        this.loadedRiepilogoFondi = true;
        this.liquidazioneService.riepilogoFondi(this.user, oggettoLiquidazione, this.user.idSoggetto.toString(), this.user.beneficiarioSelezionato.idBeneficiario.toString(), this.idBando.toString()).subscribe(data => {
          this.loadedRiepilogoFondi = false;
          this.dataSourceRipFontiFinanz = new MatTableDataSource(data.fontiFinanziarie);
          let ripartizioneImpegniLiquidazione = data.ripartizioneImpegniLiquidazione;
          ripartizioneImpegniLiquidazione.forEach(r => this.setImpDaliquidSuImpFormatted(r));
          this.dataSourceRipartizImpLiqui = new MatTableDataSource(ripartizioneImpegniLiquidazione);
          this.totaleRipImpLiquid = this.calcolaTotaleRipImpLiquid().toString();
          this.totaleInizialeRipartizImpLiquid = this.calcolaTotaleRipFontiFinanz();
          this.cupProgetto = this.datiGenerali.datiGeneraliDTO.cup;

          if (data.message == "Per la verifica del residuo disponibile aggiornato, il sistema esterno del Bilancio Regionale non è al momento accessibile. <br />Utilizzare il residuo proposto dal sistema.") {
            this.showMessageWarning("Per la verifica del residuo disponibile aggiornato, il sistema esterno del Bilancio Regionale non è al momento accessibile. Utilizzare il residuo proposto dal sistema.")
          } else {
            if (data.message == "Errore: java.lang.NullPointerException") {
              this.showMessageError("ATTENZIONE! Non sono state definite le fonti finanziare oppure non sono stati associati gli impegni al bando linea o al progetto. Contattare l'AdG.");
              this.showAvantiRiepFondiButton = false;
            }
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedRiepilogoFondi = false;
          this.showMessageError("Errore in fase di ottenimento dei dati riepilogo fondi");
        });
      }
    }
  }

  percLiquidEffetChanged() {
    this.impLiquidEffet = +(((Number(this.ultimoImportoAgevolato) / 100) * Number(this.percLiquidEffet)).toFixed(2));
    this.setImpLiquidEffFormatted();
  }

  impLiquidEffetChanged() {
    this.percLiquidEffet = +(((Number(this.impLiquidEffet) * 100) / Number(this.ultimoImportoAgevolato)).toFixed(2));
    this.setPercLiquidEffFormatted();
  }

  imponibileChanged() {
    this.importoCaricoEnte = +(((Number(this.imponibile) - Number(this.sommaNonSoggetta)) * Number(this.percCaricoEnte) / 100).toFixed(2));
    this.importoCaricoSoggetto = +(((Number(this.imponibile) - Number(this.sommaNonSoggetta)) * Number(this.percCaricoSoggetto) / 100).toFixed(2));
    this.setImpCaricoEnteFormatted();
    this.setImpCaricoSoggettoFormatted();
  }

  changeNaturaOnereCodiceTributo() {
    if (this.naturaOnereCodiceTributoSelected == undefined) {
      this.showFieldsRitenute = false;
      this.imponibile = 0;
      this.sommaNonSoggetta = 0;
      this.percCaricoEnte = 0;
      this.percCaricoSoggetto = 0;
      this.importoCaricoEnte = 0;
      this.importoCaricoSoggetto = 0;
    } else {
      this.showFieldsRitenute = true;
      this.percCaricoEnte = this.datiRitenute.aliquotaRitenutaAttoDTO[0].percCaricoEnte == null ? null : (+this.datiRitenute.aliquotaRitenutaAttoDTO[0].percCaricoEnte.toFixed(2));
      this.percCaricoSoggetto = this.datiRitenute.aliquotaRitenutaAttoDTO[0].percCaricoSoggetto == null ? 0 : +(this.datiRitenute.aliquotaRitenutaAttoDTO[0].percCaricoSoggetto.toFixed(2));
      this.imponibile = 0;
      this.sommaNonSoggetta = 0;
      this.importoCaricoEnte = (+((Number(this.imponibile) - Number(this.sommaNonSoggetta)) * Number(this.percCaricoEnte) / 100).toFixed(2));
      this.importoCaricoSoggetto = (+((Number(this.imponibile) - Number(this.sommaNonSoggetta)) * Number(this.percCaricoSoggetto) / 100).toFixed(2));
    }
    this.setImponibileFormatted();
    this.setSommaNonSoggettaFormatted();
    this.setPercCaricoEnteFormatted();
    this.setPercCaricoSoggettoFormatted();
    this.setImpCaricoEnteFormatted();
    this.setImpCaricoSoggettoFormatted();
  }

  changeModalitaLiquidazione() {
    if (!this.causaleLiquidazioneSelected) {
      this.percLiquidEffet = 0;
      this.percLiquidIterFinanziario = "0";
      this.impLiquidEffet = 0;

      this.importoDaLiquidareIterFinanziario = "0";
      this.disableFieldsLiquidContrInCorso = true;
    } else {
      if (this.causaleLiquidazioneSelected.codice == "PA-INS@0@0" || this.causaleLiquidazioneSelected.codice == "SA-INS@0@0"
        || this.causaleLiquidazioneSelected.codice == "SAL-INS@0@0") {
        this.disableFieldsLiquidContrInCorso = false;
        this.percLiquidEffet = 0;
        this.percLiquidIterFinanziario = "0";
        this.impLiquidEffet = 0;
        this.importoDaLiquidareIterFinanziario = "0";
      } else {
        this.disableFieldsLiquidContrInCorso = true;
        if (this.causaleLiquidazioneSelected) {
          this.percLiquidEffet = +this.causaleLiquidazioneSelected.codice.split("@")[1];
          this.percLiquidIterFinanziario = this.causaleLiquidazioneSelected.codice.split("@")[1];
        }

        this.impLiquidEffet = (+((Number(this.ultimoImportoAgevolato) / 100) * Number(this.percLiquidEffet)).toFixed(2));
        this.importoDaLiquidareIterFinanziario = ((Number(this.ultimoImportoAgevolato) / 100) * Number(this.percLiquidIterFinanziario)).toString();
      }
    }
    this.setPercLiquidEffFormatted();
    this.setImpLiquidEffFormatted();
  }

  indietroRiepFondi() {
    this.showTabLiquidazione = true;
    this.showTabRiepilogoFondi = false;
    this.tabIndex = 0;
    this.resetMessageError();
    this.resetMessageSuccess();
    this.isMessageWarningVisible = false;
  }

  avantiRiepFondi() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.showAvantiBenefButton = true;
    if (Number(this.totaleRipImpLiquid) == this.totaleInizialeRipartizImpLiquid) {
      let i = 0;
      let isError: boolean;
      for (let r of this.dataSourceRipartizImpLiqui.data) {
        if (!r.importoDaLiquidare || r.importoDaLiquidare <= 0) {
          this.riepFondiForm.form.get("impDaliquidSuImp" + i).setErrors({ error: "notPositive" });
          isError = true;
        }
        i++;
      }
      this.riepFondiForm.form.markAllAsTouched();
      if (isError) {
        this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
        return;
      }
      this.showTabRiepilogoFondi = false;
      this.showTabBeneficiario = true;
      this.tabIndex = 2;
      this.isMessageWarningVisible = false;

      let oggettoLiquidazione = new LiquidazioneDTO(this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idAttoLiquidazione,
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idCausaleLiquidazione, Number(this.percLiquidEffet), Number(this.impLiquidEffet),
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idModalitaAgevolazione, this.causaleLiquidazioneSelected ? this.causaleLiquidazioneSelected.codice.toString() : null,
        this.idProgetto, this.user.idSoggetto, this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idSettoreEnte,
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.descStatoAtto, this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idStatoAtto,
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.numeroDocumentoSpesa);

      let ripartizioni: Array<RipartizioneImpegniLiquidazioneDTO> = new Array<RipartizioneImpegniLiquidazioneDTO>();
      this.dataSourceRipartizImpLiqui.data.forEach(d => ripartizioni.push(Object.assign({}, d)));
      ripartizioni = ripartizioni.map(r => {
        delete r['importoDaLiquidareFormatted'];
        return r;
      });

      let oggettoImpegniLiquidazione = new ImpegniLiquidazioneDTO(oggettoLiquidazione, ripartizioni);

      this.loadedBeneficiario = true;
      this.liquidazioneService.beneficiario(this.user, oggettoImpegniLiquidazione, this.user.idSoggetto.toString(), this.user.beneficiarioSelezionato.idBeneficiario.toString(), this.idBando.toString()).subscribe(data => {
        this.loadedBeneficiario = false;
        this.beneficiarioLiquidazioneData = data;
        let comodo = new Array<BeneficiarioBilancioDTO>();
        comodo.push(data.beneficiarioBilancioDTO);
        this.dataSourceBeneficiarioLiquidazione = new MatTableDataSource(comodo);
        if (data.dettaglioBeneficiarioBilancioDTO == null) {
          this.dataSourceBeneficiarioBilancio = new MatTableDataSource();
        } else {
          this.dataSourceBeneficiarioBilancio = new MatTableDataSource(data.dettaglioBeneficiarioBilancioDTO);
        }
        if (data.responseCodeMessage?.code == "E.ELB4") {
          this.showMessageError("Il servizio 'Consulta beneficiari bilancio' non è al momento disponibile. Utilizzare i dati del beneficiario del progetto.")
        }
      }, err => {
        this.loadedBeneficiario = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ottenimento dei dati beneficiario");
      });
    } else {
      this.showMessageError("La somma degli importi da liquidare sugli impegni è diverso dall'importo totale da liquidare.")
    }
  }

  indietroDatiIntegr() {
    this.showTabDatiIntegrativi = false;
    this.showTabBeneficiario = true;
    this.tabIndex = 2;
    this.resetMessageError();
    this.resetMessageSuccess();
  }

  avantiDatiIntegr() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let chiamaServizio: boolean;

    if (this.dataScadenza.value == undefined || this.dataScadenza.value == null) {
      chiamaServizio = true;
    } else {
      if (new Date(this.dataScadenza.value).getTime() > new Date().getTime()) {
        chiamaServizio = true;
      } else {
        chiamaServizio = false;
      }
    }

    if (chiamaServizio) {
      this.resetMessageError();
      this.showTabDatiIntegrativi = false;
      this.showTabRitenute = true;
      this.tabIndex = 4;

      let oggettoDatiIntegrativi = new DatiIntegrativiDTO(this.dataScadenza?.value ? new Date(this.dataScadenza.value) : null, this.datiIntegrativi.descAtto, this.datiIntegrativi.flagAllegatiFatture, this.datiIntegrativi.flagAllegatiEstrattoProv, this.datiIntegrativi.flagAllegatiDocGiustificat, this.datiIntegrativi.flagAllegatiDichiarazione, this.datiIntegrativi.idAttoLiquidazione, this.settoreCompetenzaSelected.idSettoreEnte, this.noteAttoLiquid, this.diretDirigResponsabile, this.funzionarioLiquidatore, this.nTelefono, this.datiIntegrativi.testoAllegatiAltro);

      this.loadedRitenute = true;
      this.liquidazioneService.ritenute(this.user, this.idProgetto.toString(), oggettoDatiIntegrativi).subscribe(data => {
        this.datiRitenute = data;
        this.loadedRitenute = false;
        let comodo = new Array<string>();
        comodo.push(data.aliquotaRitenutaAttoDTO[0].descNaturaOnere);
        this.natureOnereCodiceTributo = comodo;

        this.naturaOnereCodiceTributo = data.aliquotaRitenutaAttoDTO[0].codNaturaOnere === "Nessuno" ? data.aliquotaRitenutaAttoDTO[0].codNaturaOnere : data.aliquotaRitenutaAttoDTO[0].descNaturaOnere;

        if (data.esitoRitenuteDTO.ritenuta.imponibile == null) {
          this.naturaOnereCodiceTributoSelected = undefined;
          this.percCaricoEnte = 0;
          this.percCaricoSoggetto = 0;
        } else {
          this.naturaOnereCodiceTributoSelected = data.aliquotaRitenutaAttoDTO[0].descNaturaOnere;
          this.percCaricoEnte = data.aliquotaRitenutaAttoDTO[0].percCaricoEnte == null ? null : +(data.aliquotaRitenutaAttoDTO[0].percCaricoEnte.toFixed(2));
          this.percCaricoSoggetto = data.aliquotaRitenutaAttoDTO[0].percCaricoSoggetto == null ? 0 : +(data.aliquotaRitenutaAttoDTO[0].percCaricoSoggetto.toFixed(2));
        }

        this.imponibile = data.esitoRitenuteDTO.ritenuta.imponibile == null ? 0 : +(data.esitoRitenuteDTO.ritenuta.imponibile.toFixed(2));
        this.sommaNonSoggetta = data.esitoRitenuteDTO.ritenuta.sommeNonImponibili == null ? 0 : +(data.esitoRitenuteDTO.ritenuta.sommeNonImponibili.toFixed(2));
        this.importoCaricoEnte = +(((Number(this.imponibile) - Number(this.sommaNonSoggetta)) * Number(this.percCaricoEnte) / 100).toFixed(2));
        this.importoCaricoSoggetto = +(((Number(this.imponibile) - Number(this.sommaNonSoggetta)) * Number(this.percCaricoSoggetto) / 100).toFixed(2));
        this.setImponibileFormatted();
        this.setSommaNonSoggettaFormatted();
        this.setPercCaricoEnteFormatted();
        this.setPercCaricoSoggettoFormatted();
        this.setImpCaricoEnteFormatted();
        this.setImpCaricoSoggettoFormatted();
      }, err => {
        this.loadedRitenute = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ottenimento dei dati delle ritenute");
      });
    } else {
      this.showMessageError("Il campo Data di scadenza non puo essere inferiore alla data odierna");
    }
  }

  indietroRitenute() {
    this.showTabRitenute = false;
    this.showTabDatiIntegrativi = true;
    this.tabIndex = 3;
    this.resetMessageError();
    this.resetMessageSuccess();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  avantiRitenute() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.datiRitenute?.aliquotaRitenutaAttoDTO?.find(a => a.idAliquotaRitenuta != null) && (this.imponibile === null || this.imponibile === undefined)) {
      //se esiste l'aliquota, verifico che l'imponibile sia valorizzato
      this.showMessageError("Valorizzare tutti i campi obbligatori");
      return;
    }
    if (Number(this.sommaNonSoggetta) > Number(this.imponibile)) {
      this.showMessageError("La somma non soggetta deve essere minore dell'imponibile ");
    } else {
      if (+this.imponibile > this.impLiquidEffet) {
        this.showMessageError("L'imponibile non deve superare l'importo dell'atto di liquidazione");
      } else {
        if (this.naturaOnereCodiceTributoSelected == undefined || this.naturaOnereCodiceTributoSelected == null || this.naturaOnereCodiceTributoSelected == "") {
          this.resetMessageError()
          this.showTabRitenute = false;
          this.showTabCreazioneAtto = true;
          this.tabIndex = 5;

          let idAliquotaRitenuta;
          if (this.naturaOnereCodiceTributoSelected == undefined) {
            idAliquotaRitenuta = null;
          } else {
            this.datiRitenute.aliquotaRitenutaAttoDTO.forEach(element => {
              if (element.descNaturaOnere == this.naturaOnereCodiceTributo) {
                idAliquotaRitenuta = element.idAliquotaRitenuta;
              }
            });
          }

          this.loadedTabCreaAtto = true;
          this.liquidazioneService.tabCreaAtto(this.user, this.beneficiarioLiquidazioneData.idAttoLiquidazione.toString(), null, null, idAliquotaRitenuta).subscribe(data => {
            this.loadedTabCreaAtto = false;
            if (data.infoCreaAtto.importoAtto) {
              this.importoAttoLiquidazione = this.sharedService.formatValue(data.infoCreaAtto.importoAtto.toString());
            }
            this.direzione = data.infoCreaAtto.descEnte;
            this.settore = data.infoCreaAtto.descSettore;
            this.codiceProgettoCreazioneAtto = this.codiceProgetto;
            this.beneficiarioCreazioneAtto = this.user.beneficiarioSelezionato.denominazione;
            this.causale = data.infoCreaAtto.descCausale;
            this.note = data.infoCreaAtto.noteAtto;
            this.cupProgettoCreazioneAtto = this.datiGenerali.datiGeneraliDTO.cup;
          }, err => {
            this.loadedTabCreaAtto = false;
            this.showMessageError("Errore in fase di ottenimento dei dati crea atto");
            this.handleExceptionService.handleNotBlockingError(err);
          });
        } else {
          if (this.imponibile == undefined || this.imponibile == null) {
            this.showMessageError("Si prega di popolare i campi obbligatori.");
          } else {
            this.resetMessageError()
            this.showTabRitenute = false;
            this.showTabCreazioneAtto = true;
            this.tabIndex = 5;

            this.loadedTabCreaAtto = true;

            let idAliquotaRitenuta;
            if (this.naturaOnereCodiceTributoSelected == undefined) {
              idAliquotaRitenuta = null;
            } else {
              this.datiRitenute.aliquotaRitenutaAttoDTO.forEach(element => {
                if (element.descNaturaOnere == this.naturaOnereCodiceTributo) {
                  idAliquotaRitenuta = element.idAliquotaRitenuta;
                }
              });
            }

            this.liquidazioneService.tabCreaAtto(this.user, this.beneficiarioLiquidazioneData.idAttoLiquidazione.toString(), this.imponibile.toString(), this.sommaNonSoggetta.toString(),
              idAliquotaRitenuta).subscribe(data => {
                this.loadedTabCreaAtto = false;
                if (data.infoCreaAtto.importoAtto) {
                  this.importoAttoLiquidazione = this.sharedService.formatValue(data.infoCreaAtto.importoAtto.toString());
                }
                this.direzione = data.infoCreaAtto.descEnte;
                this.settore = data.infoCreaAtto.descSettore;
                this.codiceProgettoCreazioneAtto = this.datiGenerali.datiGeneraliDTO.codiceProgetto;
                this.beneficiarioCreazioneAtto = this.user.beneficiarioSelezionato.denominazione;
                this.causale = data.infoCreaAtto.descCausale;
                this.note = data.infoCreaAtto.noteAtto;
                this.cupProgettoCreazioneAtto = this.datiGenerali.datiGeneraliDTO.cup;
              }, err => {
                this.loadedTabCreaAtto = false;
                this.showMessageError("Errore in fase di ottenimento dei dati crea atto");
                this.handleExceptionService.handleNotBlockingError(err);
              });
          }
        }
      }
    }
  }

  indietroBeneficiario() {
    this.showTabBeneficiario = false;
    this.showTabRiepilogoFondi = true;
    this.tabIndex = 1;
    this.resetMessageError();
    this.resetMessageSuccess();
  }

  avantiBeneficiario() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.dataSourceBeneficiarioLiquidazione.data[0].modalitaPagamento == null) {
      this.showMessageError("Si prega di indicare la modalità di pagamento.")
      this.showAvantiBenefButton = false;
    } else {
      this.showTabBeneficiario = false;
      this.showTabDatiIntegrativi = true;
      this.tabIndex = 3;
      this.resetMessageError();

      this.loadedDatiIntegrativi = true;
      this.liquidazioneService.datiIntegrativi(this.user, this.beneficiarioLiquidazioneData.idAttoLiquidazione.toString(), this.user.idSoggetto.toString()).subscribe(data => {
        this.loadedDatiIntegrativi = false;
        this.datiIntegrativi = data.datiIntegrativi;
        this.entiCompetenza = data.listEnteDiCompetenza;
        this.enteCompetenzaSelected = this.entiCompetenza.find(x => x.idEnteCompetenza == data.idEnteCompetenza);
        this.settoriCompetenza = data.listSettoreDiAppartenenza;
        this.settoreCompetenzaSelected = this.settoriCompetenza.find(x => x.idSettoreEnte == data.idSettoreDiAppartenenza);
        this.causalePagamento = data.causaleDiPagamento;
        this.noteAttoLiquid = data.datiIntegrativi.noteAtto;
        if (data.datiIntegrativi.dtScadenzaAtto !== null) {
          this.dataScadenza.setValue(new Date(data.datiIntegrativi.dtScadenzaAtto));
        } else {
          this.dataScadenza.setValue(undefined);
        }
        this.funzionarioLiquidatore = data.datiIntegrativi.nomeLiquidatore;
        this.nTelefono = data.datiIntegrativi.numeroTelefonoLiquidatore;
        this.diretDirigResponsabile = data.datiIntegrativi.nomeDirigenteLiquidatore;
      }, err => {
        this.loadedDatiIntegrativi = false;
        this.showMessageError("Errore in fase di ottenimento dei dati integrativi");
        this.handleExceptionService.handleNotBlockingError(err);
      });
    }
  }

  calcolaTotaleRiepFondi() {
    this.totaleRipImpLiquid = this.calcolaTotaleRipImpLiquid().toString();
  }

  indietroCreaAtto() {
    this.showTabCreazioneAtto = false;
    this.showTabRitenute = true;
    this.tabIndex = 4;
    this.resetMessageError();
    this.resetMessageSuccess();
  }

  creaAtto() {
    this.loadedCreaAtto = true;
    this.liquidazioneService.creaAtto(this.user, this.beneficiarioLiquidazioneData.idAttoLiquidazione.toString(), this.idProgetto.toString()).subscribe(data => {
      this.loadedCreaAtto = false;
      this.showEsitoAttoLiquidazione = true;
      this.numeroDocumentoSpesa = data.numeroDocSpesa;
      this.dataAtto = new Date(Number(data.dataAtto)).toLocaleDateString();
      this.annoAtto = data.annoAtto;
      this.numeroAtto = data.numeroAtto;
      this.esitoOperazione = data.esitoOperazione;
      this.statoOperazione = data.descEsitoOperazione;

      if (data.esitoOperazione == "SUCCESSO") {
        this.showMessageSuccess("L'elaborazione dell'atto è stata avviata ed è stato posto in stato IN LAVORAZIONE. Verificare i successivi stati di lavorazione e il numero atto tramite l'attività Consulta Atto!");
      }

      this.showCreaAttoButton = false;
      this.showIndietroCreaAttoButton = false;
    }, err => {
      this.loadedCreaAtto = false;
      this.showMessageError("Errore in fase di creazione dell'atto");
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  stampa() {
    window.print();
  }

  associaAltroBeneficiario() {
    this.showBeneficiariBilancio = true;
  }

  associaBeneficiariBilancio() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAggiornaBeneficiario = true;
    let com = this.selectionBeneficiariBilancio.selected[0];
    com.idSoggetto = this.beneficiarioLiquidazioneData.beneficiarioBilancioDTO.idSoggetto;
    com.idModalitaErogazione = this.getModErogFromModPag(com.modalitaPagamento);

    let ripartizioni: Array<RipartizioneImpegniLiquidazioneDTO> = new Array<RipartizioneImpegniLiquidazioneDTO>();
    this.dataSourceRipartizImpLiqui.data.forEach(d => ripartizioni.push(Object.assign({}, d)));
    ripartizioni = ripartizioni.map(r => {
      delete r['importoDaLiquidareFormatted'];
      return r;
    });

    this.liquidazioneService.aggiornaBeneficiario(this.user, this.idProgetto.toString(), this.beneficiarioLiquidazioneData.idAttoLiquidazione.toString(), com).subscribe(() => {
      this.showMessageSuccess("Beneficiario associato con successo.");
      this.showAvantiBenefButton = true;
      this.loadedAggiornaBeneficiario = false;
      let oggettoLiquidazione = new LiquidazioneDTO(this.beneficiarioLiquidazioneData.idAttoLiquidazione,
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idCausaleLiquidazione, Number(this.percLiquidEffet), Number(this.impLiquidEffet),
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idModalitaAgevolazione,
        this.causaleLiquidazioneSelected ? this.causaleLiquidazioneSelected.codice.toString() : null, this.idProgetto, this.user.idSoggetto,
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idSettoreEnte, this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.descStatoAtto,
        this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.idStatoAtto, this.datiGenerali.esitoLeggiAttoLiquidazioneDTO.liquidazione.numeroDocumentoSpesa);
      this.loadedBeneficiario = true;

      let oggettoImpegniLiquidazione = new ImpegniLiquidazioneDTO(oggettoLiquidazione, ripartizioni);


      this.liquidazioneService.beneficiario(this.user, oggettoImpegniLiquidazione, this.user.idSoggetto.toString(), this.user.beneficiarioSelezionato.idBeneficiario.toString(), this.idBando.toString()).subscribe(data2 => {
        this.loadedBeneficiario = false;
        let comodo = new Array<BeneficiarioBilancioDTO>();
        comodo.push(data2.beneficiarioBilancioDTO);
        this.dataSourceBeneficiarioLiquidazione = new MatTableDataSource(comodo);
        this.dataSourceBeneficiarioBilancio = new MatTableDataSource(data2.dettaglioBeneficiarioBilancioDTO);
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedBeneficiario = false;
        this.showMessageError("Errore in fase di ottenimento dei dati beneficiario");
      });
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedAggiornaBeneficiario = false;
      this.showMessageError("Errore in fase di aggiornamento del beneficiario");
    });
  }

  getModErogFromModPag(modPag: string) {
    switch (modPag) {
      case "BONIFICO POSTALE":
        return 6;
      case "GIRO FONDI":
        return 9;
      case "CONTO CORRENTE BANCARIO":
        return 1;
      case "ASSEGNO DI TRAENZA":
        return 3;
      case "ASSEGNO CIRCOLARE":
        return 4;
      case "CONTO CORRENTE POSTALE":
        return 5;
    }
  }

  closeBeneficiariBilancio() {
    this.showBeneficiariBilancio = false;
  }

  convertDate(inputData: Date) {
    return new Date(Number(inputData)).toLocaleDateString();
  }

  changeEnte() {
    this.loadedCercaSettoreAppartenenza = true;
    this.liquidazioneService.cercaSettoreAppartenenza(this.user, this.user.idSoggetto.toString(), this.enteCompetenzaSelected.idEnteCompetenza.toString()).subscribe(data => {
      this.loadedCercaSettoreAppartenenza = false;
      this.settoriCompetenza = data;
      this.settoreCompetenzaSelected = undefined;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCercaSettoreAppartenenza = false;
      this.showMessageError("Errore in fase di ottenimento dei settori di appartenenza");
    });
  }

  calcolaTotaleRichLiquidProg() {
    let totale = 0;
    this.dataSourceRichLiquidProg.data.forEach(element => {
      totale = totale + element.importoRichiestaErogazione;
    });
    return totale;
  }

  calcolaTotaleRipFontiFinanz() {
    let totale = 0;
    this.dataSourceRipFontiFinanz.data.forEach(element => {
      totale = totale + element.importoRispTotale;
    });
    return totale;
  }

  calcolaTotaleRipImpLiquid() {
    let totale = 0;
    this.dataSourceRipartizImpLiqui.data.forEach(element => {
      totale = totale + element.importoDaLiquidare;
    });
    return totale;
  }

  isLoading() {
    if (this.loadedCaricaDatiGenerali || this.loadedRiepilogoFondi || this.loadedBeneficiario || this.loadedRitenute
      || this.loadedTabCreaAtto || this.loadedDatiIntegrativi || this.loadedCreaAtto || this.loadedAggiornaBeneficiario
      || this.loadedApriDettaglioBeneficiario || this.loadedCercaSettoreAppartenenza || !this.loadedChiudiAttivita) {
      return true;
    } else {
      return false;
    }
  }
}
