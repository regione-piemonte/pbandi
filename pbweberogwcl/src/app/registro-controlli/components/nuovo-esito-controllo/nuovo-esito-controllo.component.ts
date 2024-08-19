/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from '@pbandi/common-lib';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/disimpegni/commons/models/codice-descrizione';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { EsitoSalvaIrregolaritaDTO } from '../../commons/dto/esito-salva-irregolarita-dto';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RequestInserisciIrreg } from '../../commons/models/request-inserisci-irreg';
import { RequestModificaIrreg } from '../../commons/models/request-modifica-irreg';
import { RequestSalvaIrregolarita } from '../../commons/models/request-salva-irregolarita';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';

@Component({
  selector: 'app-nuovo-esito-controllo',
  templateUrl: './nuovo-esito-controllo.component.html',
  styleUrls: ['./nuovo-esito-controllo.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoEsitoControlloComponent implements OnInit {

  beneficiario: string;
  user: UserInfoSec;
  codiceProgetto: string;
  idProgetto: string;
  esitoControlloRadio: string = '1';
  tipoControlliRadio: string = '1';
  anniContabile: Array<CodiceDescrizione>;
  annoContabileSelected: CodiceDescrizione;
  autoritasControllante: Array<CodiceDescrizione>;
  autoritaControllanteSelected: CodiceDescrizione;
  dateCampione: Array<CodiceDescrizione>;
  dataCampioneSelected: CodiceDescrizione;
  noteRegolare: string;
  dataComunicazioneEsito: string;
  importoSpesaIrregolareIrregProvv: string;
  importoAgevIrrIrregProvv: string;
  importoIrregCertIrregProvv: string;
  motivoIrregIrregProvvSelected: CodiceDescrizione;
  motiviIrregIrregProvv: Array<CodiceDescrizione>;
  motivoIrregIrregDefSelected: CodiceDescrizione;;
  motiviIrregIrregDef: Array<CodiceDescrizione>;
  noteIrregProvv: string;
  noteIrregDef: string;
  allegDatiAccessIrreg: string;

  praticaUsataIrregIrregDef: string;
  soggContatChiarimIrregDef: string;

  importoSpesaIrregolareIrregDef: string;
  importoAgevIrrIrregDef: string;
  importoIrregCertIrregDef: string;

  allegatiVariIntegIrreg: string;

  provvedimentoRadio: string = '1';

  dataInizioControlli: FormControl = new FormControl();
  dataFineControlli: FormControl = new FormControl();

  @ViewChild(NgForm) esitoForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  fileOLAF: File;
  fileDatiAgg: File;

  //SUBSCRIBERS
  subscribers: any = {};

  @ViewChild('uploader') uploader;
  @ViewChild('uploaderDatiAgg') uploaderDatiAgg;

  loadedgetPeriodi: boolean;
  loadedgetCategAnagrafica: boolean;
  loadedgetMotiviIrregolarita: boolean;
  loadedgetDataCampione: boolean;
  loadedinserisciIrregolaritaRegolare: boolean;
  loadedinserisciIrregolaritaProvvisoria: boolean;
  loadedinserisciIrregolaritaDefinitiva: boolean;

  constructor(
    private userService: UserService,
    private sharedService: SharedService,
    private registroControlliService: RegistroControlliService,
    private registroControlliService2: RegistroControlliService2,
    public dialogRef: MatDialogRef<NuovoEsitoControlloComponent>,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    // RECUPERO PARAMETRI DA URL
    this.codiceProgetto = this.data.codiceProgetto;
    this.idProgetto = this.data.idProgetto;

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.loadedgetPeriodi = true;
        this.subscribers.getPeriodi = this.registroControlliService.getPeriodi().subscribe((res: Array<CodiceDescrizione>) => {
          this.anniContabile = res;
          this.loadedgetPeriodi = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento dei periodi");
          this.loadedgetPeriodi = false;
        });

        this.loadedgetCategAnagrafica = true;
        this.subscribers.getCategAnagrafica = this.registroControlliService.getCategAnagrafica().subscribe((res: Array<CodiceDescrizione>) => {
          this.autoritasControllante = res;
          this.loadedgetCategAnagrafica = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento delle categ anagrafiche");
          this.loadedgetCategAnagrafica = false;
        });

        this.loadedgetMotiviIrregolarita = true;
        this.subscribers.getMotiviIrregolarita = this.registroControlliService.getMotiviIrregolarita().subscribe((res: Array<CodiceDescrizione>) => {
          this.motiviIrregIrregDef = res;
          this.motiviIrregIrregProvv = res;
          this.loadedgetMotiviIrregolarita = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento dei motivi irregolarità");
          this.loadedgetMotiviIrregolarita = false;
        });
      }
    });

    const d = new Date();
    this.dataComunicazioneEsito = d.toLocaleDateString().split(", ")[0];
  }

  annullaDatiAccessIrreg() {
    this.fileOLAF = undefined;
    this.allegDatiAccessIrreg = undefined;
  }

  annullaAllegVariIntegIrreg() {
    this.fileDatiAgg = undefined;
    this.allegatiVariIntegIrreg = undefined;
  }

  changeAnnoContabile() {
    this.loadDataCampione();
  }

  loadDataCampione() {
    if (!(this.annoContabileSelected == undefined) && !(this.autoritaControllanteSelected == undefined)) {
      let request: RegistroControlliService.GetDataCampioneParams = {
        tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
        idProgetto: Number(this.idProgetto),
        idPeriodo: Number(this.annoContabileSelected.codice),
        idCategAnagrafica: Number(this.autoritaControllanteSelected.codice)
      };

      this.loadedgetDataCampione = true;
      this.subscribers.getDataCampione = this.registroControlliService.getDataCampione(request).subscribe((res: Array<CodiceDescrizione>) => {
        this.loadedgetDataCampione = false;
        if (res.length == 0) {

          let comodo: CodiceDescrizione = {
            codice: '-1',
            descrizione: 'Nessuno'
          }

          this.dateCampione = new Array<CodiceDescrizione>();
          this.dateCampione.push(comodo)
        } else {
          this.dateCampione = res;

          let comodo: CodiceDescrizione = {
            codice: '-1',
            descrizione: 'Nessuno'
          }

          this.dateCampione.push(comodo)
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ottenimento delle date campione");
        this.loadedgetDataCampione = false;
      });
    } else {
      this.dataCampioneSelected = undefined;
      this.dateCampione = new Array<CodiceDescrizione>();
    }
  }

  changeAutControl() {
    this.loadDataCampione();
  }

  changeDataFineControlli() {
    if (this.dataInizioControlli && this.dataInizioControlli.errors && this.dataInizioControlli.errors.error === 'notValid' &&
      (!this.dataFineControlli || !this.dataFineControlli.value
        || (this.dataFineControlli && this.dataFineControlli.value && this.dataInizioControlli && this.dataInizioControlli.value
          && this.dataInizioControlli.value <= this.dataFineControlli.value))) {
      this.dataInizioControlli.setErrors(null);
    }
  }

  setImportoSpesaIrregolareIrregProvv() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregProvv);
    if (imp !== null) {
      this.importoSpesaIrregolareIrregProvv = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoAgevIrrIrregProvv() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregProvv);
    if (imp !== null) {
      this.importoAgevIrrIrregProvv = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoIrregCertIrregProvv() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregProvv);
    if (imp !== null) {
      this.importoIrregCertIrregProvv = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoSpesaIrregolareIrregDef() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregDef);
    if (imp !== null) {
      this.importoSpesaIrregolareIrregDef = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoAgevIrrIrregDef() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregDef);
    if (imp !== null) {
      this.importoAgevIrrIrregDef = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoIrregCertIrregDef() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregDef);
    if (imp !== null) {
      this.importoIrregCertIrregDef = this.sharedService.formatValue(imp.toString());
    }
  }

  creaEsito() {
    this.resetMessageError();
    if (this.registroControlliService2.validate(this.esitoForm, this.esitoControlloRadio, this.dataInizioControlli, this.dataFineControlli, this.importoSpesaIrregolareIrregDef,
      this.importoAgevIrrIrregDef, this.importoIrregCertIrregDef)) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
      return;
    }

    if (this.esitoControlloRadio == "1") {
      // Creazione esito regolare
      let irregolarita: Irregolarita = {
        esitoControllo: "REGOLARE",
        idPeriodo: Number(this.annoContabileSelected.codice),
        idCategAnagrafica: Number(this.autoritaControllanteSelected.codice),
        tipoControlli: this.tipoControlliRadio == "1" ? "D" : "L",
        dtComunicazioneIrregolarita: this.dataComunicazioneEsito,
        dataInizioControlli: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
        dataFineControlli: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
        note: this.noteRegolare ? this.noteRegolare : "",
        dataCampione: this.dataCampioneSelected == undefined ? null : this.dataCampioneSelected.descrizione
      }

      let request: RequestSalvaIrregolarita = {
        idProgetto: Number(this.idProgetto),
        idU: this.user.idUtente,
        idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
        codiceFiscale: this.user.beneficiarioSelezionato.codiceFiscale,
        irregolarita: irregolarita
      };

      this.loadedinserisciIrregolaritaRegolare = true;
      this.subscribers.inserisciIrregolaritaRegolare = this.registroControlliService.inserisciIrregolaritaRegolare(request).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
        this.loadedinserisciIrregolaritaRegolare = false;

        if (res.esito == true) {
          this.dialogRef.close({ esitoControllo: "REG", message: "Salvataggio avvenuto con successo." });
        } else {
          this.showMessageError("Errore in fase di inserimento della regolarità");
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di inserimento della regolarità");
        this.loadedinserisciIrregolaritaRegolare = false;
      });
    } else {
      if (this.esitoControlloRadio == "2") {
        // Creazione irregolarità provvisoria
        let irregolarita: Irregolarita = {
          idProgettoProvv: Number(this.idProgetto),
          idMotivoRevocaProvv: Number(this.motivoIrregIrregProvvSelected.codice),
          esitoControllo: "REGOLARE",
          importoIrregolaritaProvv: this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregProvv),
          importoAgevolazioneIrregProvv: this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregProvv),
          importoIrregolareCertificatoProvv: this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregProvv),
          idPeriodoProvv: Number(this.annoContabileSelected.codice),
          idCategAnagraficaProvv: Number(this.autoritaControllanteSelected.codice),
          tipoControlliProvv: this.tipoControlliRadio == "1" ? "D" : "L",
          dtComunicazioneProvv: this.dataComunicazioneEsito,
          dataInizioControlliProvv: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
          dataFineControlliProvv: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
          noteProvv: this.noteIrregProvv ? this.noteIrregProvv : "",
          dataCampioneProvv: this.dataCampioneSelected == undefined ? null : this.dataCampioneSelected.descrizione
        }

        let request: RequestSalvaIrregolarita = {
          idProgetto: Number(this.idProgetto),
          idU: this.user.idUtente,
          idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
          codiceFiscale: this.user.beneficiarioSelezionato.codiceFiscale,
          irregolarita: irregolarita
        };

        this.loadedinserisciIrregolaritaProvvisoria = true;
        this.subscribers.inserisciIrregolaritaProvvisoria = this.registroControlliService.inserisciIrregolaritaProvvisoria(request).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
          this.loadedinserisciIrregolaritaProvvisoria = false;

          if (res.esito == true) {
            this.dialogRef.close({ esitoControllo: "IRR", message: "Salvataggio avvenuto con successo." });
          } else {
            this.showMessageError("Errore in fase di inserimento dell'irregolarità provvisoria");
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di inserimento dell'irregolarità provvisoria");
          this.loadedinserisciIrregolaritaProvvisoria = false;
        });
      } else {
        // Creazione irregolarità definitiva
        let comodoRequestIrreg: RequestModificaIrreg = {
          idU: this.user.idUtente,
          idIride: this.user.idIride,
          modificaDatiAggiuntivi: this.fileDatiAgg == undefined ? "false" : "true",
          idProgetto: +this.idProgetto,
          idMotivoRevoca: +this.motivoIrregIrregDefSelected.codice,
          importoIrregolarita: this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregDef.toString()),
          importoAgevolazioneIrreg: this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregDef.toString()),
          quotaImpIrregCertificato: this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregDef.toString()),
          idPeriodo: +this.annoContabileSelected.codice,
          idCategAnagrafica: +this.autoritaControllanteSelected.codice,
          tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
          note: this.noteIrregDef ? this.noteIrregDef : "",
          dataCampione: this.dataCampioneSelected ? this.dataCampioneSelected.codice : null,
          dataInizioControlli: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
          dataFineControlli: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
          flagProvvedimento: this.provvedimentoRadio == '1' ? 'N' : (this.provvedimentoRadio == '2' ? 'D' : 'S'),
          notePraticaUsata: this.praticaUsataIrregIrregDef,
          soggettoResponsabile: this.soggContatChiarimIrregDef,
          fileDatiAggiuntivi: this.fileDatiAgg,
          fileOlaf: this.fileOLAF,
          idIrregolarita: null,
          idIrregolaritaProvv: null
        };

        this.loadedinserisciIrregolaritaDefinitiva = true;
        this.subscribers.inserisciIrregolaritaDefinitiva = this.registroControlliService2.inserisciIrregolaritaDefinitiva(comodoRequestIrreg).subscribe(res => {
          this.loadedinserisciIrregolaritaDefinitiva = false;

          if (res.esito == true) {
            this.dialogRef.close({ esitoControllo: "IRR", message: "Salvataggio avvenuto con successo." });
          } else {
            this.showMessageError("Errore in fase di inserimento dell'irregolarità definitiva");
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di inserimento dell'irregolarità definitiva");
          this.loadedinserisciIrregolaritaDefinitiva = false;
        });
      }
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollTop');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  clearFileOlaf() {
    this.uploader.nativeElement.value = '';
  }

  clearFileDatiAgg() {
    this.uploaderDatiAgg.nativeElement.value = '';
  }

  uploadFileOlaf($event: any) {
    this.fileOLAF = $event.target.files[0];
    this.allegDatiAccessIrreg = this.fileOLAF.name;
  }

  uploadFileDatiAgg($event: any) {
    this.fileDatiAgg = $event.target.files[0];
    this.allegatiVariIntegIrreg = this.fileDatiAgg.name;
  }

  isLoading() {
    if (this.loadedgetPeriodi || this.loadedgetCategAnagrafica || this.loadedgetMotiviIrregolarita || this.loadedgetDataCampione || this.loadedinserisciIrregolaritaRegolare
      || this.loadedinserisciIrregolaritaProvvisoria || this.loadedinserisciIrregolaritaDefinitiva) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close();
  }
}

