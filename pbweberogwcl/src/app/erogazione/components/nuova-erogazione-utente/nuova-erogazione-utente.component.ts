/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfigService } from "../../../core/services/config.service";
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { EsitoRichiestaErogazioneDTO } from "../../commons/dto/esito-richiesta-erogazione-dto";
import { FideiussioneTipoTrattamentoDTO } from "../../commons/dto/fideiussione-tipo-trattamento-dto";
import { TipoAllegatoRichiestaErogDTO } from "../../commons/dto/tipo-allegato-dto";
import { RappresentanteLegaleDTO } from "../../commons/dto/rappresentante-legale-dto";
import { ErogazioneService } from "../../services/erogazione.service";
import { ArchivioFileDialogComponent, DatiProgettoAttivitaPregresseDialogComponent, DocumentoAllegatoDTO, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { SharedService } from 'src/app/shared/services/shared.service';
import { FileDTO } from 'src/app/dati-progetto/commons/dto/file-dto';
import { Erogazione2Service } from '../../services/erogazione2.service';
import { AssociaFilesRequest } from '../../commons/requests/associa-files-request';
import { CreaRichiestaErogazioneRequest } from '../../commons/requests/crea-richiesta-erogazione-request';
import { ConfermaRichiestaDialogComponent } from '../conferma-richiesta-dialog/conferma-richiesta-dialog.component';
import { AffidServtecArDTO } from '../../commons/dto/affid-servtec-ar-dto';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-nuova-erogazione-utente',
  templateUrl: './nuova-erogazione-utente.component.html',
  styleUrls: ['./nuova-erogazione-utente.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovaErogazioneUtenteComponent implements OnInit {

  idProgetto: number;
  idBandoLinea: number;
  codCausale: string;
  codiceProgetto: string;
  idErogazione: number;
  inputNumberType: string = 'float';
  user: UserInfoSec;
  isBR59: boolean;

  //LOADED
  loadedCodiceProgetto: boolean = true;
  loadedDatiRiepilogoRichiestaErogazione: boolean = true;
  loadedRappresentanti: boolean = true;
  loadedDelegati: boolean = true;
  loadedBeneficiari: boolean = true;
  loadedInserisci: boolean = true;
  loadedAssociaAllegati: boolean = true;
  loadedAllegati: boolean = true;
  loadedDisassocia: boolean = true;
  loadedRegolaBR59: boolean;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccessAllegati: string;
  isMessageSuccessAllegatiVisible: boolean;
  messageAllegatiError: string;
  isMessageErrorAllegatiVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];
  datiRiepilogoRichiestaErogazione: EsitoRichiestaErogazioneDTO;
  rappresentanteSelected: CodiceDescrizione;
  delegatoSelected: CodiceDescrizione;
  dataInizioLavori: Date;
  residenza: string;
  dataContratti: Date;
  codDirezione: string;

  //TABLE FIDEIUSSIONI TIPO TRATTAMENTO
  displayedColumnsFideiussioniTipoTrattamento: string[] = ['descrizioneTipoTrattamento', 'importo'];
  displayedHeadersFideiussioniTipoTrattamento: string[] = ['Tipo trattamento', 'Importo'];
  displayedFootersFideiussioniTipoTrattamento: string[] = [];
  displayedColumnsCustomFideiussioniTipoTrattamento: string[] = ['', 'number'];
  displayedHeadersCustomFideiussioniTipoTrattamento: string[] = this.displayedColumnsCustomFideiussioniTipoTrattamento;
  dataSourceFideiussioniTipoTrattamento: MatTableDataSource<FideiussioneTipoTrattamentoDTO> = new MatTableDataSource<FideiussioneTipoTrattamentoDTO>();

  //FORM FIELDS
  fieldPercentualeErogazioneEffettiva: number;
  fieldImportoErogazioneEffettivo: number;
  graduatoriaRadio: string = '1';
  iban: string;
  tipoAllegatiCompleti: TipoAllegatoRichiestaErogDTO[] = [];
  allegati: Array<FileDTO>;
  ibanConfirmed: string;
  rappresentanti: CodiceDescrizione[];
  rappresentantiLegali: RappresentanteLegaleDTO[];
  delegati: CodiceDescrizione[];
  totaleSpesaSostenuta: number;

  displayedColumnsAff: string[] = ["fornitore", "servizio", "documento", "nomeFile"];
  dataSourceAffServizi = new MatTableDataSource<AffidServtecArDTO>([new AffidServtecArDTO(null, null, "S", "", "", "", "")]);
  dataSourceAffLavori = new MatTableDataSource<AffidServtecArDTO>([new AffidServtecArDTO(null, null, "L", "", "", "", "")]);

  @ViewChild('affidamentiForm', { static: true }) affidamentiForm: NgForm;

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private sharedService: SharedService,
    private erogazioneService: ErogazioneService,
    private erogazione2Service: Erogazione2Service,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.idBandoLinea = +params['idBando'];
            this.codCausale = params['codCausale'];
            this.loadData();
            this.loadDatiRiepilogoRichiestaErogazione();
          });
        }
      }
    });
  }

  loadData() {
    this.loadedBeneficiari = true;
    this.loadedRappresentanti = false;
    this.subscribers.rappresentanti = this.erogazioneService.getRappresentantiLegali(this.idProgetto).subscribe((res: RappresentanteLegaleDTO[]) => {
      if (res) {
        console.log('getRappresentanti', res);
        this.rappresentantiLegali = res;
        this.rappresentanti = [];
        let res2: RappresentanteLegaleDTO[] = [];
        if (!Array.isArray(res)) {
          res2 = [res];
        } else {
          res2 = res;
        }
        res2.forEach((element, index) => {
          this.rappresentanti.push({
            codice: element.idSoggetto.toString(),
            descrizione: element.cognome + ' ' + element.nome
          });
        });
      }
      this.loadedRappresentanti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRappresentanti = true;
    });
    this.loadedDelegati = false;
    this.subscribers.delegati = this.erogazioneService.getDelegati(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        console.log('getDelegati', res);
        this.delegati = res;
      }
      this.loadedDelegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDelegati = true;
    });
    //LOAD CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        console.log('getCodiceProgetto', res);
        this.codiceProgetto = res;
        this.setupHeaderLabelsButtons();
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });
    this.loadedRegolaBR59 = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR59").subscribe(res => {
      if (res) {
        this.isBR59 = res;
      }
      this.loadedRegolaBR59 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero della regola BR59.");
      this.loadedRegolaBR59 = true;
    });
  }

  loadDatiRiepilogoRichiestaErogazione() {
    this.loadedDatiRiepilogoRichiestaErogazione = false;
    let datiRiepilogoRichiestaErogazioneParams: ErogazioneService.GetDatiRiepilogoRichiestaErogazioneParams = {
      idProgetto: this.idProgetto,
      codCausale: this.codCausale
    };
    this.subscribers.datiRiepilogoRichiestaErogazione = this.erogazioneService.getDatiRiepilogoRichiestaErogazione(datiRiepilogoRichiestaErogazioneParams).subscribe((res: EsitoRichiestaErogazioneDTO) => {
      if (res) {
        this.datiRiepilogoRichiestaErogazione = res;
        this.totaleSpesaSostenuta = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.spesaProgetto.totaleSpesaSostenuta;
        this.fieldPercentualeErogazioneEffettiva = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.percentualeErogazione;
        this.fieldImportoErogazioneEffettivo = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.importoRichiesto;
        if (this.datiRiepilogoRichiestaErogazione.richiestaErogazione.estremiBancari) {
          this.iban = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.estremiBancari.iban;
        } else {
          this.showMessageError("Iban non trovato");
        }
        this.allegati = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.allegati;
        this.tipoAllegatiCompleti = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.tipoAllegatiCompleti;
        if (this.tipoAllegatiCompleti && this.tipoAllegatiCompleti.length > 0) {
          this.tipoAllegatiCompleti.forEach(t => {
            if (t.flagAllegato === "S") {
              t.checked = true;
            }
          })
        }
        if (!this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioniTipoTrattamento) {
          this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioniTipoTrattamento = [];
        }
        this.dataSourceFideiussioniTipoTrattamento = new FideiussioneTipoTrattamentoDatasource(this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioniTipoTrattamento); // FideiussioneTipoTrattamentoDTO
        this.loadData();
      }
      this.loadedDatiRiepilogoRichiestaErogazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDatiRiepilogoRichiestaErogazione = true;
    });
  }

  aggiungiRigaAffServizi() {
    this.dataSourceAffServizi.data.push(new AffidServtecArDTO(null, null, "S", "", "", "", ""));
    this.dataSourceAffServizi.data = this.dataSourceAffServizi.data; //senza questo non refresha la visualizzazione
  }

  aggiungiRigaAffLavori() {
    this.dataSourceAffLavori.data.push(new AffidServtecArDTO(null, null, "L", "", "", "", ""));
    this.dataSourceAffLavori.data = this.dataSourceAffLavori.data; //senza questo non refresha la visualizzazione
  }

  disabledFormDataSubmit() {
    return (
      !this.ibanConfirmed ||
      !this.rappresentanteSelected
    );
  }

  onTorna() {
    this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE}/avvio-lavori-richiesta-erogazione-acconto/${this.idProgetto}/${this.idBandoLinea}/${this.codCausale}`);
  }

  isLoading() {
    if (!this.loadedBeneficiari || !this.loadedCodiceProgetto || !this.loadedDatiRiepilogoRichiestaErogazione
      || !this.loadedDelegati || !this.loadedInserisci || !this.loadedRappresentanti || !this.loadedAssociaAllegati
      || !this.loadedAllegati || !this.loadedDisassocia) {
      return true;
    }
    return false;
  }

  setupHeaderLabelsButtons() {
    this.headerLabels = [
      {
        codice: 'Beneficiario',
        descrizione: ((this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) ? this.user.beneficiarioSelezionato.denominazione : '')
      },
      {
        codice: 'Codice progetto',
        descrizione: this.codiceProgetto
      }
    ];
    this.headerButtons = [
      {
        codice: 'dati-progetto-e-attivita-pregresse',
        descrizione: 'Dati progetto e attività pregresse'
      },
      {
        codice: 'conto-economico',
        descrizione: 'Conto economico'
      }
    ];
  }

  compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
    return value && (option.codice === value.codice);
  }

  onHeaderButtonsClick(btn: CodiceDescrizione) {
    switch (btn.codice) {
      case 'dati-progetto-e-attivita-pregresse':
        this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
          width: '90%',
          maxHeight: '95%',
          data: {
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      case 'conto-economico':
        this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
          width: "90%",
          maxHeight: '90%',
          data: {
            idBandoLinea: this.idBandoLinea,
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      default:
    }
  }

  onGraduatoriaRadioChange = (e: string) => {
    if (this.datiRiepilogoRichiestaErogazione && this.datiRiepilogoRichiestaErogazione.richiestaErogazione) {
      if (e == '1') {
        this.fieldPercentualeErogazioneEffettiva = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.percentualeErogazione;
        this.fieldImportoErogazioneEffettivo = this.datiRiepilogoRichiestaErogazione.richiestaErogazione.importoRichiesto;
      } else if (e == '2') {
        // Jira PBANDI-2799.
        // Se l'utente ha dichiarato di non richiedere il primo acconto, azzero percentuale e importo che verranno stampati nel pdf.
        this.fieldPercentualeErogazioneEffettiva = 0;
        this.fieldImportoErogazioneEffettivo = 0;
      }
    }
  }

  aggiungiAllegato() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageAllegatiSuccess();
    this.resetMessageAllegatiError();
    let all = new Array<DocumentoAllegatoDTO>();
    this.allegati.forEach(a => {
      all.push(new DocumentoAllegatoDTO(null, null, a.idDocumentoIndex, null, null, null, this.idProgetto, a.nomeFile, null, a.sizeFile, null, true));
    });
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: all,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res && res.length > 0) {
        this.loadedAssociaAllegati = false;
        this.subscribers.associa = this.erogazione2Service.associaAllegatiARichiestaErogazione(
          new AssociaFilesRequest(res.map(r => +r.idDocumentoIndex), this.idErogazione ? this.idErogazione : null, this.idProgetto)).subscribe(data => {
            if (data) {
              if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
                && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
                this.showMessageSuccess("Tutti gli allegati sono stati associati correttamente alla richiesta erogazione.");
                this.loadAllegati();
              } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
                && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
                this.showMessageError("Errore nell'associazione degli allegati alla richiesta erogazione.");
              } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
                && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
                this.loadAllegati();
                let message = "Errore nell'associazione dei seguenti allegati alla richiesta erogazione: ";
                data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
                  let allegato = this.allegati.find(a => a.idDocumentoIndex === id);
                  message += allegato.nomeFile + ", ";
                });
                message = message.substr(0, message.length - 2);
                this.showMessageError(message);
              }
            }
            this.loadedAssociaAllegati = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedAssociaAllegati = true;
            this.showMessageError("Errore nell'associazione degli allegati alla richiesta erogazione.");
          });
      }
    });
  }

  loadAllegati() {
    this.loadedAllegati = false;
    this.subscribers.allegati = this.erogazione2Service.getFilesAssociatedRichiestaErogazioneByIdProgetto(this.idProgetto).subscribe(data => {
      if (data) {
        this.allegati = data;
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedAllegati = true;
      this.showMessageError("Errore in fase di recupero degli allegati.")
    });
  }

  disassociaAllegato(idDocumentoIndex: number) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDisassocia = false;
    this.subscribers.disassociaAllegato = this.erogazione2Service.disassociaAllegatiARichiestaErogazione(this.idProgetto, this.idErogazione ? this.idErogazione : null, idDocumentoIndex).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Allegato disassociato con successo.");
          this.loadAllegati();
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedDisassocia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella disassociazione dell'allegato.");
      this.loadedDisassocia = true;
    });
  }

  crea() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.isBR59) {
      //controllo campi tabelle affidamento servizi e lavori
      let rigaNonCompletaServizi = this.dataSourceAffServizi.data.find(d => !((d.fornitore?.length > 0 && d.servizioAffidato?.length > 0 && d.documento?.length > 0 && d.nomeFile?.length > 0) ||
        (d.fornitore?.length === 0 && d.servizioAffidato?.length === 0 && d.documento?.length === 0 && d.nomeFile?.length === 0)));
      let rigaNonCompletaLavoro = this.dataSourceAffLavori.data.find(d => !((d.fornitore?.length > 0 && d.servizioAffidato?.length > 0 && d.documento?.length > 0 && d.nomeFile?.length > 0) ||
        (d.fornitore?.length === 0 && d.servizioAffidato?.length === 0 && d.documento?.length === 0 && d.nomeFile?.length === 0)));

      let msgError: string = "I dati nelle seguenti sezioni non sono completi: <br />";
      if (rigaNonCompletaServizi) {
        msgError += "- Affidamento dei servizi tecnici di ingegneria <br />";
      }
      if (rigaNonCompletaLavoro) {
        msgError += "- Affidamento dei lavori/forniture/servizi";
      }
      if (rigaNonCompletaLavoro || rigaNonCompletaServizi) {
        this.showMessageError(msgError);
        this.affidamentiForm?.form?.markAllAsTouched();
        return;
      }
    }
    let dialogRef = this.dialog.open(ConfermaRichiestaDialogComponent, {
      width: '50%'
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.salva();
      }
    });
  }

  salva() {
    let rappresentanteLegale: RappresentanteLegaleDTO = this.rappresentantiLegali.find(rappresentante => rappresentante.idSoggetto.toString() == this.rappresentanteSelected.codice)
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.dtInizioLavori = this.dataInizioLavori;
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.dtStipulazioneContratti = this.dataContratti;
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.direttoreLavori = this.codDirezione;
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.residenzaDirettoreLavori = this.residenza;
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.allegati = this.allegati;
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.percentualeErogazione = this.fieldPercentualeErogazioneEffettiva;
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.importoRichiesto = this.fieldImportoErogazioneEffettivo;
    let tipi = new Array<TipoAllegatoRichiestaErogDTO>();
    this.tipoAllegatiCompleti.forEach(t => tipi.push(Object.assign({}, t)));
    tipi.forEach(t => {
      t.flagAllegato = t.checked ? "S" : "N";
      delete t['checked'];
    });
    this.datiRiepilogoRichiestaErogazione.richiestaErogazione.tipoAllegatiCompleti = tipi;
    let creaRichiestaErogazione: CreaRichiestaErogazioneRequest = {
      idDelegato: this.delegatoSelected && this.delegatoSelected.codice ? +this.delegatoSelected.codice : null,
      idProgetto: this.idProgetto,
      rappresentante: rappresentanteLegale,
      richiesta: this.datiRiepilogoRichiestaErogazione.richiestaErogazione,
      affidamentiServiziLavori: this.isBR59 ? new Array<AffidServtecArDTO>(...this.dataSourceAffServizi?.data?.filter(d => d.fornitore?.length > 0),
        ...this.dataSourceAffLavori?.data?.filter(d => d.fornitore?.length > 0)) : null
    };
    this.loadedInserisci = false;
    this.subscribers.creaRichiestaErogazione = this.erogazioneService.creaRichiestaErogazione(creaRichiestaErogazione).subscribe(data => {
      if (data) { // .isRegolaAttiva
        if (data.esito && data.esito) {
          this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE}/invio-richiesta-erogazione/${this.idProgetto}/${this.idBandoLinea}/${data.idDocIndex};nomeFile=${data.nomeReport}`);
        } else {
          console.log('showMessageError', data);
          this.showMessageError(data.messages.map(o => o).join("<br>"));
        }
      }
      this.loadedInserisci = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedInserisci = true;
      this.showMessageError("Errore nell'inserimento dell'erogazione.");
    });
  }

  //MESSAGGI SUCCESS E ERROR - start
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  showMessageAllegatiSuccess(msg: string) {
    this.messageSuccessAllegati = msg;
    this.isMessageSuccessAllegatiVisible = true;
    const element = document.querySelector('#scrollIAllegatid');
    element.scrollIntoView();
  }

  showMessageAllegatiError(msg: string) {
    this.messageAllegatiError = msg;
    this.isMessageErrorAllegatiVisible = true;
    const element = document.querySelector('#scrollAllegatiId');
    element.scrollIntoView();
  }

  resetMessageAllegatiSuccess() {
    this.messageSuccessAllegati = null;
    this.isMessageSuccessAllegatiVisible = false;
  }

  resetMessageAllegatiError() {
    this.messageAllegatiError = null;
    this.isMessageErrorAllegatiVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end
}

export class FideiussioneTipoTrattamentoDatasource extends MatTableDataSource<FideiussioneTipoTrattamentoDTO> {

  _orderData(data: FideiussioneTipoTrattamentoDTO[]): FideiussioneTipoTrattamentoDTO[] {
    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta"; // Todo: param
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    switch (this.sort.active) {
      case "":
      default:
        sortedData = data;
        break;
    }

    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: FideiussioneTipoTrattamentoDTO[]) {
    super(data);
  }

}