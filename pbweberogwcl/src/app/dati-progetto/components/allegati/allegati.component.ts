/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ArchivioFileDialogComponent, ArchivioFileService, DocumentoAllegatoDTO } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { FileDTO } from '../../commons/dto/file-dto';
import { saveAs } from 'file-saver-es';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { AssociaFilesRequest } from 'src/app/erogazione/commons/requests/associa-files-request';
import { DatiProgettoService } from '../../services/dati-progetto.service';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';

@Component({
  selector: 'app-allegati',
  templateUrl: './allegati.component.html',
  styleUrls: ['./allegati.component.scss']
})
export class AllegatiComponent implements OnInit, OnChanges {

  @Input("idProgetto") idProgetto: number;
  @Input("allegati") allegati: Array<FileDTO>;
  @Input("isBR64") isBR64: boolean;
  @Input("user") user: UserInfoSec;

  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("messageSuccess") messageSuccess = new EventEmitter<string>();

  isModifica: boolean;

  subscribers: any = {}

  //LOADED
  loadedDocumento: boolean = true;
  loadedDissociateFile: boolean = true;
  loadedAssociaAllegati: boolean = true;
  loadedAllegati: boolean = true;

  constructor(
    private archivioFileService: ArchivioFileService,
    private handleExceptionService: HandleExceptionService,
    private configService: ConfigService,
    private datiProgettoService: DatiProgettoService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    //la getAllegati iniziale viene chiamata da dati-progetto.component
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.isBR64 && this.user?.idSoggetto) {
      if (this.user.codiceRuolo == Constants.CODICE_RUOLO_ADG_IST_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER) {
        this.isModifica = false;
      } else if (this.user.codiceRuolo == Constants.CODICE_RUOLO_PERSONA_FISICA || this.user.codiceRuolo == Constants.CODICE_RUOLO_BEN_MASTER) {
        this.isModifica = true;
      }
    }
  }

  getAllegati() {
    this.loadedAllegati = false;
    this.datiProgettoService.getFilesAssociatedProgetto(this.idProgetto).subscribe(data => {
      if (data) {
        this.allegati = data;
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati.");
      this.loadedAllegati = true;
    });
  }

  aggiungiAllegati() {
    this.resetMessageSuccess();
    this.resetMessageError();
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
        drawerExpanded: of(false)
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res && res.length > 0) {
        this.loadedAssociaAllegati = false;
        this.datiProgettoService.associaAllegatiAProgetto(
          new AssociaFilesRequest(res.map(r => +r.idDocumentoIndex), this.idProgetto, null)).subscribe(data => {
            if (data) {
              if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
                && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
                this.showMessageSuccess("Tutti gli allegati sono stati associati correttamente al progetto.");
                this.getAllegati();
              } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
                && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
                this.showMessageError("Errore nell'associazione degli allegati al progetto.");
              } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
                && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
                this.getAllegati();
                let message = "Errore nell'associazione dei seguenti allegati al progetto: ";
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
            this.showMessageError("Errore nell'associazione degli allegati al progetto.");
          });
      }
    });
  }
  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDocumento = false;
    this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDocumento = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file.");
      this.loadedDocumento = true;
    });
  }

  disassociaAllegato(idDocumentoIndex: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedDissociateFile = false;
    this.datiProgettoService.disassociaAllegatoProgetto(+idDocumentoIndex, this.idProgetto).subscribe(res => {
      if (res.esito) {
        this.showMessageSuccess("Disassociazione avvenuta con successo.");
        this.getAllegati();
      } else {
        this.showMessageError("Errore in fase di disassociazione del file.");
      }
      this.loadedDissociateFile = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di disassociazione del file.");
      this.loadedDissociateFile = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError.emit(msg);
  }

  resetMessageError() {
    this.messageError.emit(null);
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess.emit(msg);
  }

  resetMessageSuccess() {
    this.messageSuccess.emit(null);
  }

  isLoading() {
    if (!this.loadedDissociateFile || !this.loadedDocumento
      || !this.loadedAssociaAllegati || !this.loadedAllegati) {
      return true;
    }
    return false;
  }
}
