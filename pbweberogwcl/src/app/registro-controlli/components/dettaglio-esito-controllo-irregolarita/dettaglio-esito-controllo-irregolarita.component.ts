/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';
import { saveAs } from "file-saver-es";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-dettaglio-esito-controllo-irregolarita',
  templateUrl: './dettaglio-esito-controllo-irregolarita.component.html',
  styleUrls: ['./dettaglio-esito-controllo-irregolarita.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioEsitoControlloIrregolaritaComponent implements OnInit {

  user: UserInfoSec;
  idProgetto: string;
  idIrregolarita: string;
  irregDefinitiva: boolean;
  beneficiario: string;
  codiceProgetto: string;

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

  isMessageErrorVisible: any;
  messageError: any;

  loadedGetDettaglioIrregolarita: boolean;
  loadedGetDettaglioIrregolaritaProvvisoria: boolean;
  loadedGetDocumento: boolean;

  constructor(
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    private registroControlliService2: RegistroControlliService2,
    public dialogRef: MatDialogRef<DettaglioEsitoControlloIrregolaritaComponent>,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  //SUBSCRIBERS
  subscribers: any = {};

  ngOnInit(): void {
    // RECUPERO PARAMETRI DA URL
    this.idIrregolarita = this.data.idIrregolarita;
    this.idProgetto = this.data.idProgetto;
    this.irregDefinitiva = this.data.irregDefinitiva;
    this.beneficiario = this.data.beneficiario;
    this.codiceProgetto = this.data.codiceProgetto;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;

        if (this.irregDefinitiva == true) {
          this.loadedGetDettaglioIrregolarita = true;

          // RECUPERO DATI DI DETTAGLIO IRREGOLARITA' DEFINITIVA
          this.subscribers.getDettaglioIrregolarita = this.registroControlliService.getDettaglioIrregolarita(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
            this.numeroVersione = res.idIrregolarita + "/" + res.numeroVersione;
            this.dataComunicazioneEsito = new Date(res.dtComunicazione).toLocaleDateString();
            this.tipoControlli = this.getDescTipoByCode(res.tipoControlli);
            this.annoContabile = res.descPeriodoVisualizzata;
            this.autoritaControllante = res.descCategAnagrafica;
            this.dataCampione = res.dataCampione == null ? null : new Date(res.dataCampione).toLocaleDateString();
            this.dataInizioControlli = new Date(res.dataInizioControlli).toLocaleDateString();
            this.dataFineControlli = new Date(res.dataFineControlli).toLocaleDateString();
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
            this.loadedGetDettaglioIrregolarita = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità");
            this.loadedGetDettaglioIrregolarita = false;
          });
        } else {
          this.loadedGetDettaglioIrregolaritaProvvisoria = true;

          // RECUPERO DATI DI DETTAGLIO IRREGOLARITA' PROVVISORIA
          this.subscribers.getDettaglioIrregolaritaProvvisoria = this.registroControlliService.getDettaglioIrregolaritaProvvisoria(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
            this.dataComunicazioneEsito = new Date(res.dtComunicazioneProvv).toLocaleDateString();
            this.tipoControlli = this.getDescTipoByCode(res.tipoControlliProvv);
            this.annoContabile = res.descPeriodoVisualizzataProvv;
            this.autoritaControllante = res.descCategAnagraficaProvv;
            this.dataCampione = res.dataCampione == null ? null : new Date(res.dataCampione).toLocaleDateString();
            this.dataInizioControlli = new Date(res.dataInizioControlliProvv).toLocaleDateString();
            this.dataFineControlli = new Date(res.dataFineControlliProvv).toLocaleDateString();
            this.importoSpesaIrregolare = res.importoIrregolaritaProvv == null ? null : res.importoIrregolaritaProvv.toString();
            this.impAgevIrr = res.importoAgevolazioneIrregProvv == null ? null : res.importoAgevolazioneIrregProvv.toString();
            this.impIrregCert = res.importoIrregolareCertificatoProvv == null ? null : res.importoIrregolareCertificatoProvv.toString();
            this.motivoIrregolarita = res.descMotivoRevocaProvv;
            this.irregolaritaAnnullata = res.flagIrregolaritaAnnullataProvv;
            this.note = res.noteProvv;
            this.loadedGetDettaglioIrregolaritaProvvisoria = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità provvisoria");
            this.loadedGetDettaglioIrregolaritaProvvisoria = false;
          });
        }
      }
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
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

  scaricaAllegatoAccessIrreg() {
    this.loadedGetDocumento = true;

    // CHIAMATA SERVIZI DOWNLOAD DOCUMENTO
    this.subscribers.getDocumentoAccess = this.registroControlliService2.getDocumento(this.idDocIndexAccessIrreg).subscribe(data => {
      if (data) {
        saveAs(data, this.allegDatiAccessIrreg);
      }
      this.loadedGetDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download file");
      this.loadedGetDocumento = false;
    })
  }

  scaricaAllegatoIntegIrreg() {
    this.loadedGetDocumento = true;

    // CHIAMATA SERVIZI DOWNLOAD DOCUMENTO
    this.subscribers.getDocumentoInteg = this.registroControlliService2.getDocumento(this.idDocIndexIntegIrreg).subscribe(data => {
      if (data) {
        saveAs(data, this.allegVariIntegIrreg);
      }
      this.loadedGetDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento di download del file");
      this.loadedGetDocumento = false;
    })
  }

  // AVVIO SPINNER
  isLoading() {
    if (this.loadedGetDettaglioIrregolarita || this.loadedGetDettaglioIrregolaritaProvvisoria || this.loadedGetDocumento) {
      return true;
    } else {
      return false;
    }
  }

}
