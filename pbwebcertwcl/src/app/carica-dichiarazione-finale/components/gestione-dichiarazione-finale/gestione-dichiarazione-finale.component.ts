/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { DocumentoCertificazioneDTO } from 'src/app/shared/commons/dto/documento-certificazione-dto';
import { DocumentoDescrizione } from 'src/app/shared/commons/dto/documento-descrizione';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { CancellaAllegatiRequest } from 'src/app/shared/commons/requests/cancella-allegati-request';
import { ModificaAllegatiRequest } from 'src/app/shared/commons/requests/modifica-allegato-request';
import { PropostaCertificazioneAllegatiRequest } from 'src/app/shared/commons/requests/proposta-certificazione-allegati-request';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { ConfermaDialog } from '../../../shared/components/conferma-dialog/conferma-dialog';

@Component({
  selector: 'app-gestione-dichiarazione-finale',
  templateUrl: './gestione-dichiarazione-finale.component.html',
  styleUrls: ['./gestione-dichiarazione-finale.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneDichiarazioneFinaleComponent implements OnInit {
  i: number;
  idProposta: number;
  proposta: PropostaCertificazioneDTO;
  file: File;

  allegati: Array<DocumentoCertificazioneDTO>;
  isAllegaFile: boolean = true;

  idAllegatiChecked: Array<number> = new Array<number>();
  allChecked: boolean;

  dataSource: MatTableDataSource<DocumentoCertificazioneDTO>;
  displayedColumns: string[] = ['check', 'nomefile', 'descrizione', 'tipodoc'];

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  messageSuccessAllegati: string;
  isMessageSuccessAllegatiVisible: boolean;
  messageAllegatiError: string;
  isMessageErrorAllegatiVisible: boolean;

  //LOADED
  loadedProposta: boolean;
  loadedAllegati: boolean;
  loadedAggiungi: boolean = true;
  loadedElimina: boolean = true;
  loadedModifica: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProposta = +params['id'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {

          this.loadedProposta = false;
          this.subscribers.proposta = this.certificazioneService.getProposta(this.idProposta).subscribe(data => {
            if (data) {
              this.proposta = data;
            }
            this.loadedProposta = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedProposta = true;
          });
          this.getAllegati();
        }
      });
    });
  }

  getAllegati() {
    this.loadedAllegati = false;
    let s = new Array<string>();
    s.push(Constants.DESC_BREVE_TIPO_DOC_DICHIARAZIONE_FINALE_CERTIF);
    this.subscribers.allegati = this.certificazioneService.getAllegatiPropostaCertificazione(new PropostaCertificazioneAllegatiRequest(this.idProposta, s)).subscribe(data => {
      if (data) {
        this.allegati = data;
        if (this.allegati && this.allegati.length > 0) {
          this.isAllegaFile = false;
        }
        this.allegati.forEach(a => a.checked = false);
        this.idAllegatiChecked = new Array<number>();
        this.dataSource = new MatTableDataSource<DocumentoCertificazioneDTO>(this.allegati);
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  selectAllCheckbox(e: any) {
    this.dataSource.filteredData = this.dataSource._filterData(this.dataSource.data);
    this.dataSource.filteredData.map(data => {
      data.checked = e.checked;
    });
    if (e.checked) {
      this.idAllegatiChecked = this.allegati.map(n => n.idDocumentoIndex);
    } else {
      this.idAllegatiChecked = new Array<number>();
    }
  }

  selectCheckbox(idDocumentoIndex: number, e: any) {
    if (e.checked) {
      this.idAllegatiChecked.push(idDocumentoIndex);
    } else {
      this.idAllegatiChecked = this.idAllegatiChecked.filter(id => id !== idDocumentoIndex);
    }
  }

  goToCaricaDichiarazioneFinale() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_DICHIARAZIONE_FINALE + "/caricaDichiarazioneFinale");
  }

  handleFileInput(file: File) {
    this.file = file;
  }

  aggiungiAllegato() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageAllegatiSuccess();
    this.resetMessageAllegatiError();
    this.loadedAggiungi = false;
    this.subscribers.allega = this.certificazioneService.allegaFileProposta(this.idProposta, null, this.file, Constants.DESC_BREVE_TIPO_DOC_DICHIARAZIONE_FINALE_CERTIF).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.allChecked = false;
          this.file = null;
          this.showMessageSuccess(data.msg);
          this.getAllegati();
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedAggiungi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel salvataggio dell'allegato.");
      this.loadedAggiungi = true;
    });
  }

  modificaDescrizioneAllegati() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageAllegatiSuccess();
    this.resetMessageAllegatiError();
    this.loadedModifica = false;
    let docDesc = new Array<DocumentoDescrizione>();
    for (let id of this.idAllegatiChecked) {
      docDesc.push(new DocumentoDescrizione(id, this.allegati.find(a => a.idDocumentoIndex === id).noteDocumentoIndex));
    }
    let request = new ModificaAllegatiRequest(docDesc);
    this.subscribers.modifica = this.certificazioneService.modificaAllegati(this.idProposta, request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.allChecked = false;
          this.showMessageSuccess(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedModifica = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedModifica = true;
      this.showMessageError("Errore nel salvataggio della descrizione.");
    })
  }

  eliminaAllegati() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageAllegatiSuccess();
    this.resetMessageAllegatiError();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '30%',
      data: {
        message: "Confermi l’eliminazione dei documenti selezionati dall’archivio allegati?"
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === "SI") {
        this.loadedElimina = false;
        let request = new CancellaAllegatiRequest(this.idAllegatiChecked.map(id => id.toString()));
        this.subscribers.cancella = this.certificazioneService.cancellaAllegati(request).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.allChecked = false;
              for (let id of this.idAllegatiChecked) {
                this.allegati.splice(this.allegati.findIndex(a => a.idDocumentoIndex === id), 1);
              }
              this.dataSource.data = this.allegati;
              if (this.allegati.length === 0) {
                this.isAllegaFile = true;
              }
              this.idAllegatiChecked = new Array<number>();
              this.showMessageAllegatiSuccess(data.msg);
            } else {
              this.showMessageAllegatiError(data.msg);
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageAllegatiError("Errore nella cancellazione dell'allegato.");
          this.loadedElimina = true;
        });
      }
    });
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

  showMessageAllegatiSuccess(msg: string) {
    this.messageSuccessAllegati = msg;
    this.isMessageSuccessAllegatiVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageAllegatiError(msg: string) {
    this.messageAllegatiError = msg;
    this.isMessageErrorAllegatiVisible = true;
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

  resetMessageAllegatiSuccess() {
    this.messageSuccessAllegati = null;
    this.isMessageSuccessAllegatiVisible = false;
  }

  resetMessageAllegatiError() {
    this.messageAllegatiError = null;
    this.isMessageErrorAllegatiVisible = false;
  }

  isLoading() {
    if (!this.loadedProposta || !this.loadedAllegati || !this.loadedElimina || !this.loadedModifica) {
      return true;
    }
    return false;
  }
}
