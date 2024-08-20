/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { saveAs } from 'file-saver-es';
import { AnteprimaDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DocumentoAllegatoDTO } from '../../commons/dto/documento-allegato-dto';
import { AffidamentiService } from '../../services/affidamenti.service';
import { AllegaVerbaleDialogComponent } from '../allega-verbale-dialog/allega-verbale-dialog.component';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { Constants } from 'src/app/core/commons/util/constants';

@Component({
  selector: 'app-allegati-checklist-dialog',
  templateUrl: './allegati-checklist-dialog.component.html',
  styleUrls: ['./allegati-checklist-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AllegatiChecklistDialogComponent implements OnInit {

  user: UserInfoSec;
  title: string;
  allegati: Array<DocumentoAllegatoDTO>;
  nascondiSizeFile: boolean;
  dataSourceTable: MatTableDataSource<InfoDocumentoVo>;
  codTipoChecklist: string;
  idDocumentoIndexChecklist: number;
  idProgetto: number;
  isAggiungiAllegatiVisible: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  //LOADED
  loadedDownload: boolean = true;
  loadedAllega: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<AllegatiChecklistDialogComponent>,
    private dialog: MatDialog,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private affidamentiService: AffidamentiService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.title = this.data.title;
    this.allegati = this.data.allegati;
    this.nascondiSizeFile = this.data.nascondiSizeFile;
    this.dataSourceTable = new MatTableDataSource(this.allegati.map(a => new InfoDocumentoVo(null, a.nome, null, a.sizeFile ? a.sizeFile.toString() : null, true, null, null, a.id.toString(), null)));
    this.idProgetto = this.data.idProgetto;
    this.idDocumentoIndexChecklist = this.data.idDocumentoIndexChecklist;
    this.codTipoChecklist = this.data.codTipoChecklist;
    if (this.data.messageSuccess) {
      this.showMessageSuccess(this.data.messageSuccess);
    }
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADC_CERT
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_ISTR_PROG
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_CREDITI) {
            this.isAggiungiAllegatiVisible = true;
          }
        }
      }
    });
  }

  anteprimaAllegato(nomeFile: string, idDocumentoIndex: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    let comodo = new Array<any>();
    comodo.push(nomeFile);
    comodo.push(idDocumentoIndex);
    comodo.push(this.dataSourceTable);
    comodo.push(this.configService.getApiURL());

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  aggiungiAllegati() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
      width: '50%',
      data: {
        codTipoChecklist: this.codTipoChecklist
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res.esito === "S") {
        let codTipoDocumento: string = this.codTipoChecklist === "CLILA" ? "VCVA" : "VCDA";
        this.loadedAllega = false;
        this.affidamentiService.allegaFilesChecklist(codTipoDocumento, this.idDocumentoIndexChecklist, this.idProgetto, res.files).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.dialogRef.close(data.codiceMessaggio);
            } else {
              this.showMessageError(data.codiceMessaggio);
            }
          } else {
            this.showMessageError("Errore in fase di salvataggio degli allegati.");
          }
          this.loadedAllega = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di salvataggio degli allegati.");
          this.loadedAllega = true;
        });
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

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  isLoading() {
    if (!this.loadedDownload || !this.loadedAllega) {
      return true;
    }
    return false;
  }

  close() {
    this.dialogRef.close();
  }
}
