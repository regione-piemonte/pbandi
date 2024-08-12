import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { DocumentoAllegatoDTO } from 'src/app/rendicontazione/commons/dto/documento-allegato-dto';
import { saveAs } from 'file-saver-es';
import { DatiIntegrazioneDsDTO } from 'src/app/rendicontazione/commons/dto/dati-integrazione-ds-dto';
import { AnteprimaDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { VisualizzaTestoDialogComponent } from 'src/app/shared/components/visualizza-testo-dialog/visualizza-testo-dialog.component';
import { AllegaVerbaleDialogComponent } from 'src/app/checklist/components/allega-verbale-dialog/allega-verbale-dialog.component';
import { CheckListService } from 'src/app/checklist/services/checkList.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { GestioneIntegrazioniService } from 'src/app/gestione-integrazioni/services/gestione-integrazioni.service';
import { DettaglioRichiestaIntegrazioneDialogComponent } from '../dettaglio-richiesta-integrazione-dialog/dettaglio-richiesta-integrazione-dialog.component';

@Component({
  selector: 'app-allegati-dichiarazione-spesa-dialog',
  templateUrl: './allegati-dichiarazione-spesa-dialog.component.html',
  styleUrls: ['./allegati-dichiarazione-spesa-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AllegatiDichiarazioneSpesaDialogComponent implements OnInit {

  user: UserInfoSec;
  title: string;
  allegati: Array<DocumentoAllegatoDTO>;
  integrazioni: Array<DatiIntegrazioneDsDTO>;
  nascondiSizeFile: boolean;
  dataSourceTable: MatTableDataSource<InfoDocumentoVo>;
  aggiungiAllegatiEnabled: boolean;
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
  loadedLetteraAcc: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<AllegatiDichiarazioneSpesaDialogComponent>,
    private dialog: MatDialog,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private checklistService: CheckListService,
    private integrazioniService: GestioneIntegrazioniService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    console.log(this.data);

    this.title = this.data.title;
    this.allegati = this.data.allegati ? this.data.allegati.allegati : null;
    this.integrazioni = this.data.allegati ? this.data.allegati.integrazioni : null;
    this.nascondiSizeFile = this.data.nascondiSizeFile;
    this.aggiungiAllegatiEnabled = this.data.aggiungiAllegatiEnabled;
    this.codTipoChecklist = this.data.codTipoChecklist;
    this.idDocumentoIndexChecklist = this.data.idDocumentoIndexChecklist;
    this.idProgetto = this.data.idProgetto;
    this.dataSourceTable = new MatTableDataSource(this.allegati.map(a => new InfoDocumentoVo(null, a.nome, null, a.sizeFile ? a.sizeFile.toString() : null, true, null, null, a.id.toString(), null)));
    if (this.data.messageSuccess) {
      this.showMessageSuccess(this.data.messageSuccess);
    }
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
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

  visualizzaInDialog(integrazione: DatiIntegrazioneDsDTO) {
    this.loadedLetteraAcc = false;
    this.integrazioniService.getLetteraAccIntegrazRendicont(integrazione.idIntegrazioneSpesa).subscribe(data => {
      this.dialog.open(DettaglioRichiestaIntegrazioneDialogComponent, {
        minWidth: "40%",
        data: {
          motivo: integrazione.descrizione,
          title: "Richiesta integrazione",
          letteraAccompagnatoria: data.length > 0 ? data[0] : null
        }
      });
      this.loadedLetteraAcc = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento della lettera accompagnatoria dell'integrazione.");
      this.loadedLetteraAcc = true;
    });

  }

  aggiungiAllegati() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
      width: '50%',
      data: {
        required: "N",
        codTipoChecklist: this.codTipoChecklist,
        isAggiungiAllegati: true
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res.esito === "S") {
        let codTipoDocumento: string = this.codTipoChecklist === "CL" ? "VCD" : "VCV";
        this.loadedAllega = false;
        this.checklistService.allegaFilesChecklist(codTipoDocumento, this.idDocumentoIndexChecklist, this.idProgetto, res.files).subscribe(data => {
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
    if (!this.loadedDownload || !this.loadedAllega || !this.loadedLetteraAcc) {
      return true;
    }
    return false;
  }

  close() {
    this.dialogRef.close();
  }
}
