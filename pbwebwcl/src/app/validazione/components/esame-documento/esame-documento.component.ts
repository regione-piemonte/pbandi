/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ValidazioneService } from '../../services/validazione.service';
import { RigaValidazioneItemDTO } from '../../commons/dto/riga-validazione-item-dto';
import { ValidaParzialmenteDocumentoRequest } from '../../commons/requests/valida-parzialmente-documento-request';
import { NuovoDocumentoDiSpesaDTO } from '../../commons/dto/nuovo-documento-di-spesa-dto';
import { AllegatiDettaglioDocComponent } from '../allegati-dettaglio-doc/allegati-dettaglio-doc.component';
import { NoteCreditoDettaglioDocComponent } from '../note-credito-dettaglio-doc/note-credito-dettaglio-doc.component';

@Component({
  selector: 'app-esame-documento',
  templateUrl: './esame-documento.component.html',
  styleUrls: ['./esame-documento.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class EsameDocumentoComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idBandoLinea: number
  idDichiarazioneDiSpesa: number;
  idDocumentoDiSpesa: number;
  user: UserInfoSec;
  codiceProgetto: string;
  comandiValidazioneVisible: boolean;
  isValidazioneParzialeChecked: boolean;
  docSpesa: NuovoDocumentoDiSpesaDTO;
  vociDiSpesa: Array<any>;
  BR79: string = "BR79";
  isBR79: boolean;
  isFP: boolean;
  vociSpesaModificabili: boolean;

  isVisible: boolean = false;

  dataSourceVociSpesa: MatTableDataSource<RigaValidazioneItemDTO>;
  displayedColumnsVociSpesa: string[] = ['descrizione', 'ammesso', 'residuo', 'residuovalidabile', 'associato', 'validato'];
  displayedColumnsVociSpesaBR79: string[] = ['descrizione', 'ammesso', 'residuo', 'residuovalidabile'];

  @ViewChild(AllegatiDettaglioDocComponent) allegatiDettaglioDocComponent: AllegatiDettaglioDocComponent;
  @ViewChild(NoteCreditoDettaglioDocComponent) noteCreditoDettaglioDocComponent: NoteCreditoDettaglioDocComponent;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedDettaglio: boolean;
  loadedSave: boolean = true;
  loadedInizializzaInt: boolean = true;
  loadedRegolaBR79: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  //id stato richiesta
  idStatoRichiesta: number;
  //stato richiesta
  statoRichiesta: string
  //data invio
  dataInvio: Date;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private validazioneService: ValidazioneService
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];
      this.idDocumentoDiSpesa = +params['idDocumentoDiSpesa'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
          || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
          || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE
          || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE) {
          this.comandiValidazioneVisible = true;
        }
        this.loadData();
      }
    });

    // Controllo se abilitare le nuove finzioni e disabilitare le vecchie in base all'ambiente
    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      this.isVisible = data;

      this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
        this.isFP = data;
        if (this.isVisible || !this.isFP) {
          //controllo stato 1 e 4
          this.loadedInizializzaInt = false;
          this.validazioneService.initIntegrazione(this.idDichiarazioneDiSpesa.toString()).subscribe(data => {
            console.log("DATA INIT INTEGRAZIONE", data);

            //id stato richiesta
            this.idStatoRichiesta = data?.idStatoRichiesta ? +data?.idStatoRichiesta : null;
            //stato richiesta
            this.statoRichiesta = data?.statoRichiesta;
            //data invio
            this.dataInvio = data?.dataInvio;
            this.loadedInizializzaInt = true;
          });
        }
      })
    });
  }

  loadData() {
    this.loadedDettaglio = false;
    this.subscribers.inizializza = this.validazioneService.dettaglioDocumentoDiSpesaPerValidazione(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto,
      this.idBandoLinea).subscribe(data => {
        if (data) {
          this.docSpesa = data;
          this.vociDiSpesa = this.docSpesa.rigaValidazioneItem ? this.docSpesa.rigaValidazioneItem.filter(r => !r.rigaPagamento) : null;
          this.vociDiSpesa.forEach(v => {
            v.residuoValidabile = this.calcolaResiduoValidabile(v.importoAmmesso, v.importoValidatoVoceDiSpesa);
            if (v.rigaModificabile) {
              if ((!v.importoValidato || !v.importoValidato?.length) && v.importoAssociatoVoceDiSpesa !== null) {
                v.importoValidato = this.sharedService.formatValue(v.importoAssociatoVoceDiSpesa.toString());
              }
              this.vociSpesaModificabili = true;
            }
          });
          this.dataSourceVociSpesa = new MatTableDataSource<RigaValidazioneItemDTO>(this.vociDiSpesa);

          if (this.docSpesa.descrizioneStatoValidazione === "PARZIALMENTE VALIDATO") {
            this.isValidazioneParzialeChecked = true;
          }
        }
        this.loadedDettaglio = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore durante la ricerca del dettaglio del documento selezionato.");
        this.loadedDettaglio = true;
      });
    this.loadedRegolaBR79 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR79).subscribe(res => {
      this.isBR79 = res;
      if (this.isBR79) {
        this.displayedColumnsVociSpesa = this.displayedColumnsVociSpesaBR79;
      }
      this.loadedRegolaBR79 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR79 = true;
    });
  }

  calcolaResiduoValidabile(importoAmmesso: number, importoValidatoVoceDiSpesa: number) {
    return importoAmmesso - importoValidatoVoceDiSpesa;
  }

  setImportoValidato(voce: any) {
    let importo = this.sharedService.getNumberFromFormattedString(voce.importoValidato);
    if (importo !== null) {
      voce.importoValidato = this.sharedService.formatValue(importo.toString());
    }
  }

  checkModificaImportiVs() {
    let validParzOK: boolean = false;
    for (let voce of this.vociDiSpesa) {
      if (voce.importoAssociatoVoceDiSpesa !== this.sharedService.getNumberFromFormattedString(voce.importoValidato)) {
        validParzOK = true;
      }
    }
    if (!validParzOK) {
      this.showMessageError("Non è stato variato alcun importo. Non si può procedere ad una validazione PARZIALE");
    }
    return validParzOK;
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.checkModificaImportiVs()) {
      this.loadedSave = false;
      let righe = new Array<RigaValidazioneItemDTO>();
      for (let riga of this.docSpesa.rigaValidazioneItem.filter(r => r.idRigoContoEconomico !== null)) {
        let r = new RigaValidazioneItemDTO();
        r.idRigoContoEconomico = riga.idRigoContoEconomico;
        let importo = this.vociDiSpesa.find(v => v.idRigoContoEconomico === riga.idRigoContoEconomico).importoValidato;
        r.importoValidatoVoceDiSpesa = this.sharedService.getNumberFromFormattedString(importo);
        righe.push(r);
      }
      let request = new ValidaParzialmenteDocumentoRequest(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.idBandoLinea, this.docSpesa.noteValidazione, righe);
      this.subscribers.salva = this.validazioneService.validaParzialmenteDocumento(request).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.loadData();
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError(data.messaggio);
          }
        }
        this.loadedSave = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel salvare il documento.");
        this.loadedSave = true;
      });
    }
  }

  sospendi() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSave = false;
    this.subscribers.sospendi = this.validazioneService.sospendiDocumento(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.docSpesa.noteValidazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadData();
          this.showMessageSuccess(data.messaggio);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSave = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella sospensione del documento.");
      this.loadedSave = true;
    });
  }

  respingi() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSave = false;
    this.subscribers.respingi = this.validazioneService.respingiDocumento(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.docSpesa.noteValidazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadData();
          this.showMessageSuccess(data.messaggio);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSave = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel respingere il documento.");
      this.loadedSave = true;
    });
  }

  invalida() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSave = false;
    this.subscribers.invalida = this.validazioneService.invalidaDocumento(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.docSpesa.noteValidazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadData();
          this.showMessageSuccess(data.messaggio);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSave = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nell'invalidare il documento.");
      this.loadedSave = true;
    });
  }

  valida() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSave = false;
    this.subscribers.valida = this.validazioneService.validaDocumento(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.docSpesa.noteValidazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadData();
          this.showMessageSuccess(data.messaggio);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSave = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel validare il documento.");
      this.loadedSave = true;
    });
  }

  tornaAValidazione() {
    this.router.navigate(["/drawer/" + this.idOperazione + "/validazione/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa]);
  }

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

  isLoading() {
    if (!this.loadedDettaglio || !this.loadedSave || !this.loadedInizializzaInt
      || !this.loadedRegolaBR79
      || (this.allegatiDettaglioDocComponent && this.allegatiDettaglioDocComponent.isLoading())
      || (this.noteCreditoDettaglioDocComponent && this.noteCreditoDettaglioDocComponent.isLoading())) {
      return true;
    }
    return false;
  }

}
