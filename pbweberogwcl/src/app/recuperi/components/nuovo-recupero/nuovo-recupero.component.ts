/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/shared/commons/models/codice-descrizione';
import { InizializzazioneService } from 'src/app/shared/services/inizializzazione.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { EsitoOperazioni } from 'src/shared/api/models/esito-operazioni';
import { EsitoSalvaRecuperoDTO } from '../../commons/dto/esito-salva-recupero-dto';
import { RigaRecuperoItem } from '../../commons/models/riga-recupero-item';
import { RequestSalvaRecuperi } from '../../commons/requests/request-salva-recuperi';
import { RecuperiService } from '../../services/recuperi.service';

@Component({
  selector: 'app-nuovo-recupero',
  templateUrl: './nuovo-recupero.component.html',
  styleUrls: ['./nuovo-recupero.component.scss']
})
export class NuovoRecuperoComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  tipologie: Array<CodiceDescrizione>;
  tipologiaSelected: CodiceDescrizione;
  processo: number;
  dataRecupero: Date;
  estremiDetermina: string;
  note: string;

  requestSalvaRecuperi: RequestSalvaRecuperi
  esitoCheckRecuperi: EsitoSalvaRecuperoDTO;
  confermaSalvataggio: boolean = false;
  modalita: Array<RigaRecuperoItem>;

  displayedColumns: string[] = ['modalitaDiAgevolazione', 'ultimoImportoAgevolato', 'importoGiaErogato', 'importoGiaRevocato', 'dataRevoca', 'estremiDetermina', 'importoGiaRecuperato', 'importoNuovoRecupero'];
  dataSource: MatTableDataSource<RigaRecuperoItem> = new MatTableDataSource<RigaRecuperoItem>([]);

  @ViewChild("nuovoForm", { static: true }) nuovoForm: NgForm;

  isMessageSuccessVisible: boolean;
  isMessageWarningVisible: boolean;
  isMessageErrorVisible: boolean;

  messageSuccess: string;
  messageWarning: string;
  messageError: string;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedTipologie: boolean;
  loadedProcesso: boolean;
  loadedModalita: boolean;
  loadedCheckRecuperi: boolean = true;
  loadedSalvaRecupero: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private recuperiService: RecuperiService,
    private inizializzazioneService: InizializzazioneService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private router: Router,
    private dialog: MatDialog,
    private configService: ConfigService,
    private datePipe: DatePipe
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['idProgetto'];
            this.loadData();
          });
        }
      }
    });
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del codice del progetto.");
      this.loadedCodiceProgetto = true;
    });
    this.loadedProcesso = false;
    this.subscribers.processo = this.recuperiService.getProcesso(this.idProgetto).subscribe(res => {
      if (res) {
        this.processo = res;
      }
      this.loadedProcesso = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del processo.");
      this.loadedProcesso = true;
    });
    this.loadedTipologie = false;
    this.subscribers.tipoRecuperi = this.recuperiService.getTipologieRecuperi().subscribe(res => {
      if (res) {
        this.tipologie = res;
      }
      this.loadedTipologie = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle tipologie.");
      this.loadedTipologie = true;
    });
    this.loadedModalita = false;
    this.subscribers.recuperi = this.recuperiService.getRecuperi(this.idProgetto).subscribe(res => {
      if (res) {
        this.modalita = res;
        this.dataSource = new MatTableDataSource<RigaRecuperoItem>(this.modalita);
      }
      this.loadedModalita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle modalità.");
      this.loadedModalita = true;
    });
  }

  setImportoNuovoRecupero(riga: RigaRecuperoItem) {
    riga.importoNuovoRecupero = this.sharedService.getNumberFromFormattedString(riga.importoNuovoRecuperoFormatted);
    if (riga.importoNuovoRecupero != null) {
      riga.importoNuovoRecuperoFormatted = this.sharedService.formatValue(riga.importoNuovoRecupero.toString());
    }
  }

  recuperoTutto() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    for (let item of this.modalita) {
      if (item.isRigaModalita && item.importoTotaleRecuperato !== 0) {
        item.importoNuovoRecupero = item.importoTotaleRevoche - item.importoTotaleRecuperato;
        if (item.importoNuovoRecupero !== null) {
          item.importoNuovoRecuperoFormatted = this.sharedService.formatValue(item.importoNuovoRecupero.toString());
        }
      }
    }
    this.showMessageWarning("Il Sistema ha calcolato gli importi da recuperare per modalità di agevolazione.");
  }

  annulla() {
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.resetMessageError();
    this.confermaSalvataggio = false;
  }

  checkRecuperi() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    let hasError: boolean;
    if (this.tipologiaSelected == null || this.tipologiaSelected == undefined) {
      this.nuovoForm.form.get('tipologia').setErrors({ error: 'required' });
      hasError = true;
    }
    if (!this.dataRecupero) {
      this.nuovoForm.form.get('dataRecupero').setErrors({ error: 'required' });
      hasError = true;
    }
    this.nuovoForm.form.markAllAsTouched();
    if (hasError) {
      this.showMessageError("Inserire tutti i campi obbligatori.")
      return;
    }
    this.loadedCheckRecuperi = false;
    let rec = new Array<RigaRecuperoItem>();
    this.modalita.forEach(r => rec.push(Object.assign({}, r)));
    rec.forEach(r => {
      r.importoRecupero = r.importoNuovoRecupero;
      delete r['importoNuovoRecupero'];
      delete r['importoNuovoRecuperoFormatted'];
    });
    this.requestSalvaRecuperi = {
      idTipologiaRecupero: this.tipologiaSelected.codice ? parseInt(this.tipologiaSelected.codice) : undefined,
      dtRecupero: this.dataRecupero ? this.datePipe.transform(this.dataRecupero, 'dd/MM/yyyy') : undefined,
      recuperi: rec,
      idProgetto: this.idProgetto, noteRecupero: this.note ? this.note : undefined,
      estremiRecupero: this.estremiDetermina ? this.estremiDetermina : undefined,
    }
    this.recuperiService.checkRecuperi(this.requestSalvaRecuperi).subscribe(res => {
      if (res) {
        let messaggio: string = '';
        if (res.msgs && res.msgs.length > 0) {
          res.msgs.forEach(m => {
            messaggio += m.msgKey + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (res.esito) {
          this.esitoCheckRecuperi = res;
          this.confermaSalvataggio = true;
          this.showMessageWarning(messaggio);

        } else {
          this.showMessageError(messaggio);
        }
      }
      this.loadedCheckRecuperi = true;

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di validazione dei campi.");
      this.loadedCheckRecuperi = true;
    });
  }


  conferma() {
    this.loadedSalvaRecupero = false;
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.subscribers.codice = this.recuperiService.salvaRecuperi(this.requestSalvaRecuperi).subscribe(res => {
      if (res) {
        let messaggio: string = '';
        if (res.msgs && res.msgs.length > 0) {
          res.msgs.forEach(m => {
            messaggio += m.msgKey + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (res.esito) {
          this.goToRecuperi("Salvataggio avvenuto con successo.");
        } else {
          if (res && res.msgs) {
            this.showMessageError(messaggio);
          } else {
            this.showMessageError("Errore in fase di salvataggio.");
          }
        }
      }
      this.loadedSalvaRecupero = true;
      this.confermaSalvataggio = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSalvaRecupero = true;
      this.confermaSalvataggio = false;
    });
  }

  goToRecuperi(message?: string) {
    let url: string = "drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_RECUPERI + "/recuperi/" + this.idProgetto;
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
  }


  goToDatiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedProcesso || !this.loadedTipologie
      || !this.loadedCheckRecuperi || !this.loadedSalvaRecupero || !this.loadedModalita) {
      return true;
    }
    return false;
  }

}
