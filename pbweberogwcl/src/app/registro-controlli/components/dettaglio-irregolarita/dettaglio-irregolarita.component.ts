/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { ArchivioFileService, UserInfoSec } from '@pbandi/common-lib';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfigService } from 'src/app/core/services/config.service';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { UserService } from 'src/app/core/services/user.service';
import { Irregolarita } from '../../commons/models/irregolarita';
import { saveAs } from "file-saver-es";
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-dettaglio-irregolarita',
  templateUrl: './dettaglio-irregolarita.component.html',
  styleUrls: ['./dettaglio-irregolarita.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioIrregolaritaComponent implements OnInit {

  user: UserInfoSec;
  idProgetto: string;
  codiceProgetto: string;
  idIrregolarita: string;
  beneficiario: string;
  irregDefinitiva: boolean;
  isAdgCert: boolean;

  numeroVersione: string;
  provvedimento: string;
  pratUsataIrreg: string;
  soggContattChiarim: string;
  allegDatiAccessIrreg: string;
  allegVariIntegIrreg: string;
  dataComunicazioneEsito: string;
  tipoControlli: string;
  annoContabile: string;
  autoritaControllante: string;
  dataCampione: string;
  dataInizioControlli: string;
  dataFineControlli: string;
  importoSpesaIrregolare: string;
  impAgevIrr: string;
  impIrregCert: string;
  motivoIrregolarita: string;
  irregolaritaAnnullata: string;
  note: string;
  idDocIndexAccessIrreg: number;
  idDocIndexIntegIrreg: number;

  messageSuccess: any;
  isMessageSuccessVisible: any;
  isMessageErrorVisible: any;
  messageError: any;

  loadedgetDettaglioIrregolarita: boolean;
  loadedgetDettaglioIrregolaritaProvvisoria: boolean;
  loadedgetDocumento: boolean;
  loadedbloccaSbloccaIrregolarita: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private registroControlliService: RegistroControlliService,
    private userService: UserService,
    private registroControlliService2: RegistroControlliService2,
    public dialogRef: MatDialogRef<DettaglioIrregolaritaComponent>,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idIrregolarita = this.data.idIrregolarita;
    this.idProgetto = this.data.idProgetto;
    this.irregDefinitiva = this.data.irregDefinitiva;
    this.beneficiario = this.data.beneficiario;
    this.codiceProgetto = this.data.codiceProgetto;
    if (this.data.message) {
      this.showMessageSuccess(this.data.message);
    }

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT) {
          this.isAdgCert = true;
        }

        if (this.irregDefinitiva == true) {

          // RECUPERO DATI DI DETTAGLIO IRREGOLARITA' DEFINITIVA
          this.loadedgetDettaglioIrregolarita = true;
          this.subscribers.getDettaglioIrregolarita = this.registroControlliService.getDettaglioIrregolarita(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
            this.numeroVersione = res.idIrregolarita + "/" + res.numeroVersione;
            this.dataComunicazioneEsito = new Date(res.dtComunicazione).toLocaleDateString();
            this.tipoControlli = this.getDescTipoByCode(res.tipoControlli);
            this.annoContabile = res.descPeriodoVisualizzata;
            this.autoritaControllante = res.descCategAnagrafica;
            this.dataCampione = res.dataCampione == null ? null : new Date(res.dataCampione).toLocaleDateString();
            this.dataInizioControlli = new Date(res.dataInizioControlli).toLocaleDateString();
            this.dataFineControlli = res.dataFineControlli ? new Date(res.dataFineControlli).toLocaleDateString() : null;
            this.importoSpesaIrregolare = res.importoIrregolarita ? res.importoIrregolarita.toString() : null;
            this.impAgevIrr = res.importoAgevolazioneIrreg == null ? null : res.importoAgevolazioneIrreg.toString();
            this.impIrregCert = res.quotaImpIrregCertificato == null ? null : res.quotaImpIrregCertificato.toString();
            this.provvedimento = this.getDescTipoByCode(res.flagProvvedimento);
            this.motivoIrregolarita = res.descMotivoRevoca;
            this.pratUsataIrreg = res.notePraticaUsata;
            this.soggContattChiarim = res.soggettoResponsabile;
            this.irregolaritaAnnullata = res.flagBlocco;
            this.allegDatiAccessIrreg = res.schedaOLAF == undefined ? undefined : res.schedaOLAF.nomeFile;
            this.idDocIndexAccessIrreg = res.schedaOLAF == undefined ? undefined : res.schedaOLAF.idDocumentoIndex;
            this.allegVariIntegIrreg = res.datiAggiuntivi == undefined ? undefined : res.datiAggiuntivi.nomeFile;
            this.idDocIndexIntegIrreg = res.datiAggiuntivi == undefined ? undefined : res.datiAggiuntivi.idDocumentoIndex;
            this.note = res.note;
            this.loadedgetDettaglioIrregolarita = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità");
            this.loadedgetDettaglioIrregolarita = false;
          });
        } else {

          // RECUPERO DATI DI DETTAGLIO IRREGOLARITA' PROVVISORIA
          this.loadedgetDettaglioIrregolaritaProvvisoria = true;
          this.subscribers.getDettaglioIrregolaritaProvvisoria = this.registroControlliService.getDettaglioIrregolaritaProvvisoria(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
            this.dataComunicazioneEsito = new Date(res.dtComunicazioneProvv).toLocaleDateString();
            this.tipoControlli = this.getDescTipoByCode(res.tipoControlliProvv);
            this.annoContabile = res.descPeriodoVisualizzataProvv;
            this.autoritaControllante = res.descCategAnagraficaProvv;
            this.dataCampione = res.dataCampione == null ? null : new Date(res.dataCampione).toLocaleDateString();
            this.dataInizioControlli = new Date(res.dataInizioControlliProvv).toLocaleDateString();
            this.dataFineControlli = res.dataFineControlliProvv ? new Date(res.dataFineControlliProvv).toLocaleDateString() : null;
            this.importoSpesaIrregolare = res.importoIrregolaritaProvv == null ? null : res.importoIrregolaritaProvv.toString();
            this.impAgevIrr = res.importoAgevolazioneIrregProvv == null ? null : res.importoAgevolazioneIrregProvv.toString();
            this.impIrregCert = res.importoIrregolareCertificatoProvv == null ? null : res.importoIrregolareCertificatoProvv.toString();
            this.motivoIrregolarita = res.descMotivoRevocaProvv;
            this.irregolaritaAnnullata = res.flagIrregolaritaAnnullataProvv;
            this.note = res.noteProvv;
            this.loadedgetDettaglioIrregolaritaProvvisoria = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità provvisoria");
            this.loadedgetDettaglioIrregolaritaProvvisoria = false;
          });
        }
      }
    });
  }

  scaricaAllegatoAccessIrreg() {

    // CHIAMATA SERVIZI DOWNLOAD DOCUMENTO
    this.loadedgetDocumento = true;
    this.subscribers.leggiFile = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocIndexAccessIrreg.toString()).subscribe(data => {
      if (data) {
        saveAs(data, this.allegDatiAccessIrreg);
      }
      this.loadedgetDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download file");
      this.loadedgetDocumento = false;
    })
  }

  scaricaAllegatoIntegIrreg() {

    // CHIAMATA SERVIZI DOWNLOAD DOCUMENTO
    this.loadedgetDocumento = true;
    this.subscribers.getDocumento = this.registroControlliService2.getDocumento(this.idDocIndexIntegIrreg).subscribe(data => {
      if (data) {
        saveAs(data, this.allegVariIntegIrreg);
      }
      this.loadedgetDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento di download del file");
      this.loadedgetDocumento = false;
    })
  }

  blocca() {
    this.loadedbloccaSbloccaIrregolarita = true;
    this.subscribers.bloccaSbloccaIrregolarita = this.registroControlliService2.bloccaSbloccaIrregolarita(this.user.idUtente, Number(this.idIrregolarita)).subscribe(data => {
      this.loadedbloccaSbloccaIrregolarita = false;
      if (data.esito == true) {
        this.showMessageSuccess("Salvataggio avvenuto con successo.");

        // RECUPERO DATI DI DETTAGLIO IRREGOLARITA' DEFINITIVA
        this.loadedgetDettaglioIrregolarita = true;
        this.subscribers.getDettaglioIrregolarita = this.registroControlliService.getDettaglioIrregolarita(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
          this.numeroVersione = res.idIrregolarita + "/" + res.numeroVersione;
          this.dataComunicazioneEsito = new Date(res.dtComunicazione).toLocaleDateString();
          this.tipoControlli = this.getDescTipoByCode(res.tipoControlli);
          this.annoContabile = res.descPeriodoVisualizzata;
          this.autoritaControllante = res.descCategAnagrafica;
          this.dataCampione = res.dataCampione == null ? null : new Date(res.dataCampione).toLocaleDateString();
          this.dataInizioControlli = new Date(res.dataInizioControlli).toLocaleDateString();
          this.dataFineControlli = new Date(res.dataFineControlli).toLocaleDateString();
          this.importoSpesaIrregolare = res.importoIrregolarita.toString();
          this.impAgevIrr = res.importoAgevolazioneIrreg == null ? null : res.importoAgevolazioneIrreg.toString();
          this.impIrregCert = res.quotaImpIrregCertificato == null ? null : res.quotaImpIrregCertificato.toString();
          this.provvedimento = this.getDescTipoByCode(res.flagProvvedimento);
          this.motivoIrregolarita = res.descMotivoRevoca;
          this.pratUsataIrreg = res.notePraticaUsata;
          this.soggContattChiarim = res.soggettoResponsabile;
          this.irregolaritaAnnullata = res.flagBlocco;
          this.allegDatiAccessIrreg = res.schedaOLAF == undefined ? undefined : res.schedaOLAF.nomeFile;
          this.idDocIndexAccessIrreg = res.schedaOLAF == undefined ? undefined : res.schedaOLAF.idDocumentoIndex;
          this.allegVariIntegIrreg = res.datiAggiuntivi == undefined ? undefined : res.datiAggiuntivi.nomeFile;
          this.idDocIndexIntegIrreg = res.datiAggiuntivi == undefined ? undefined : res.datiAggiuntivi.idDocumentoIndex;
          this.note = res.note;
          this.loadedgetDettaglioIrregolarita = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità");
          this.loadedgetDettaglioIrregolarita = false;
        });
      } else {
        this.showMessageError("Errore in fase di bloccaggio irregolarità");
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di bloccaggio irregolarità");
      this.loadedbloccaSbloccaIrregolarita = false;
    });
  }

  registraInvio() {
    this.dialogRef.close("REGISTRA INVIO");
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
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

  getDescTipoByCode(code: string) {
    if (code == 'N') {
      return 'Nessuno';
    }
    if (code == 'R') {
      return 'Revoca';
    }
    if (code == 'S') {
      return 'Soppressione';
    }
    if (code == 'D') {
      return 'Documentali';
    }
    if (code == 'L') {
      return 'In loco';
    }
  }

  isLoading() {
    if (this.loadedgetDettaglioIrregolarita || this.loadedgetDettaglioIrregolaritaProvvisoria || this.loadedgetDocumento || this.loadedbloccaSbloccaIrregolarita) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close();
  }
}
