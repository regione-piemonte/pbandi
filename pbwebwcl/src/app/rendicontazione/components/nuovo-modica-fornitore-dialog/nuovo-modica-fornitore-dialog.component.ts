/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterContentChecked, ChangeDetectorRef, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';
import { DocumentoAllegatoDTO } from '../../commons/dto/documento-allegato-dto';
import { FornitoreFormDTO } from '../../commons/dto/fornitore-form-dto';
import { QualificaDTO } from '../../commons/dto/qualifica-dto';
import { QualificaFormDTO } from '../../commons/dto/qualifica-form-dto';
import { AssociaFilesRequest } from '../../commons/requests/associa-files-request';
import { SalvaQualificheRequest } from '../../commons/requests/salva-qualifiche-request';
import { FornitoreService } from '../../services/fornitore.service';
import { FormNuovoModificaFornitoreComponent } from '../form-nuovo-modifica-fornitore/form-nuovo-modifica-fornitore.component';
import { saveAs } from 'file-saver-es';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ArchivioFileDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';

@Component({
  selector: 'app-nuovo-modica-fornitore-dialog',
  templateUrl: './nuovo-modica-fornitore-dialog.component.html',
  styleUrls: ['./nuovo-modica-fornitore-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoModicaFornitoreDialogComponent implements OnInit, AfterContentChecked {

  user: UserInfoSec;
  idProgetto: number;
  fornitore: FornitoreFormDTO;
  fornitoreAnagrafica: FornitoreFormDTO;
  fornitoreFattura: FornitoreFormDTO;
  isNuovo: boolean;
  isSeleziona: boolean;
  isXml: boolean;
  tipologie: Array<DecodificaDTO>;
  qualifiche: Array<DecodificaDTO>;
  qualificaSelected: DecodificaDTO;
  panelOpenState: boolean;
  aggiungiModificaQualificaVisible: boolean;
  costoOrario: number;
  costoOrarioFormatted: string;
  nota: string;
  qualificheAdded: Array<QualificaDTO> = new Array<QualificaDTO>();
  idModificaQualifica: number;
  allegatiAdded: Array<DocumentoAllegatoDTO> = new Array<DocumentoAllegatoDTO>();

  displayedColumnsQualifiche: string[] = ['qualifica', 'costoOrario', 'note', 'azioni'];
  dataSourceQualifiche: MatTableDataSource<QualificaDTO>;
  displayedColumnsAllegati: string[] = ['allegati', 'azioni'];
  dataSourceAllegati: MatTableDataSource<DocumentoAllegatoDTO>;

  @ViewChild(FormNuovoModificaFornitoreComponent) formComponent: FormNuovoModificaFornitoreComponent;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedQualifiche: boolean = true;
  loadedAllegati: boolean = true;
  loadedDownload: boolean = true;
  loadedSaveQualifiche: boolean = true;
  loadedSaveAllegati: boolean = true;
  loadedVerificaQualifica: boolean = true;
  loadedElimina: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<NuovoModicaFornitoreDialogComponent>,
    public sharedService: SharedService,
    private cdRef: ChangeDetectorRef,
    public dialog: MatDialog,
    private archivioFileService: ArchivioFileService,
    private handleExceptionService: HandleExceptionService,
    private configService: ConfigService,
    private fornitoreService: FornitoreService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.isNuovo = this.data.isNuovo;
    this.isSeleziona = this.data.isSeleziona;
    this.isXml = this.data.isXml;
    this.idProgetto = this.data.idProgetto;
    this.tipologie = this.data.tipologie;
    this.qualifiche = this.data.qualifiche;
    this.user = this.data.user;
    let nessunaQualifica = new Array<QualificaDTO>();
    nessunaQualifica.push(new QualificaDTO(0, null, "Nessuna qualifica associata", null, null, null, null, null));
    this.dataSourceQualifiche = new MatTableDataSource<QualificaDTO>();
    let nessunAllegato = new Array<DocumentoAllegatoDTO>();
    nessunAllegato.push(new DocumentoAllegatoDTO(null, null, 0, null, null, null, null, "Nessun allegato presente", null, null, null));
    this.dataSourceAllegati = new MatTableDataSource<DocumentoAllegatoDTO>();
    if (this.isSeleziona) {
      this.fornitoreFattura = this.data.fornitoreFattura;
      this.fornitoreAnagrafica = this.data.fornitoreAnagrafica;
    }
    if (!this.isNuovo || (this.isXml)) {
      this.fornitore = this.data.fornitore;
    }
    if (!this.isNuovo && !this.isXml) {
      this.loadedQualifiche = false;
      this.subscribers.qualifiche = this.fornitoreService.getQualificheFornitore(this.user, this.fornitore.idFornitore).subscribe(data => {
        if (data) {
          this.qualificheAdded = data;
          if (this.qualificheAdded.length > 0) {
            this.qualificheAdded.forEach((q, i) => {
              q.idTable = i + 1;
            })
            this.dataSourceQualifiche.data = this.qualificheAdded;
          } else {
            this.dataSourceQualifiche.data = nessunaQualifica;
          }
        }
        this.loadedQualifiche = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
      if (this.data.allegati && this.data.allegati.length > 0) {
        this.allegatiAdded = this.data.allegati;
        this.allegatiAdded.forEach(a => { a.associato = true });
        this.dataSourceAllegati.data = this.allegatiAdded;
      } else {
        this.dataSourceAllegati.data = nessunAllegato;
      }
    } else {
      this.dataSourceQualifiche.data = nessunaQualifica;
      this.dataSourceAllegati.data = nessunAllegato;
    }
  }

  isPersonaFisica() {
    if (this.formComponent) {
      return this.formComponent.tipologiaSelected.id === Constants.ID_TIPO_FORNITORE_PERSONA_FISICA;
    }
    return false;
  }

  setCostoOrario() {
    this.costoOrario = this.sharedService.getNumberFromFormattedString(this.costoOrarioFormatted);
    if (this.costoOrario !== null) {
      this.costoOrarioFormatted = this.sharedService.formatValue(this.costoOrario.toString());
    }
  }

  isSalvaDisabled() {
    if (this.formComponent) {
      return this.formComponent.isSalvaDisabled();
    }
    return true;
  }

  aggiungiQualifica() {
    if (this.idModificaQualifica) {
      this.qualificheAdded.forEach(q => {
        if (q.idTable === this.idModificaQualifica) {
          q.costoOrario = this.costoOrario;
          q.descQualifica = this.qualificaSelected.descrizione;
          q.idQualifica = this.qualificaSelected.id;
          q.noteQualifica = this.nota;
          q.modificata = true;
        }
      });
      this.idModificaQualifica = null;
    } else {
      let num = this.qualificheAdded.length > 0 ? this.qualificheAdded[this.qualificheAdded.length - 1].idTable + 1 : 1;
      let qualifica = new QualificaDTO(null, this.costoOrario, this.qualificaSelected.descrizione, null, this.isNuovo ? null : this.fornitore.idFornitore, this.qualificaSelected.id, this.nota, null, num);
      this.qualificheAdded.push(qualifica);
    }
    this.dataSourceQualifiche.data = this.qualificheAdded;
    this.resetCampi();
  }

  openModificaQualifica(qualifica: QualificaDTO) {
    this.costoOrario = qualifica.costoOrario;
    this.costoOrarioFormatted = this.sharedService.formatValue(this.costoOrario.toString());
    this.qualificaSelected = this.qualifiche.find(q => q.id === qualifica.idQualifica);
    this.nota = qualifica.noteQualifica;
    this.aggiungiModificaQualificaVisible = true;
    this.idModificaQualifica = qualifica.idTable;
  }

  eliminaQualifica(qualifica: QualificaDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    if (!qualifica.progrFornitoreQualifica) { //nuova qualifica appena inserita
      let dialogRef = this.dialog.open(ConfermaDialog, {
        width: '40%',
        data: {
          message: "Confermi l'eliminazione della qualifica?"
        }
      });
      dialogRef.afterClosed().subscribe(res => {
        if (res === "SI") {
          this.qualificheAdded = this.qualificheAdded.filter(q => q.idTable !== qualifica.idTable);
          this.dataSourceQualifiche.data = this.qualificheAdded;
          this.showMessageSuccess("Cancellazione avvenuta con successo.");
        }
      });
    } else { //qualifica già associata su db
      this.loadedVerificaQualifica = false;
      this.subscribers.docAssociatiQualifica = this.fornitoreService.esistonoDocumentiAssociatiAQualificaFornitore(qualifica.progrFornitoreQualifica).subscribe(data1 => {
        this.loadedVerificaQualifica = true;
        let message: string = "";
        if (data1) {
          message = "Il costo orario della qualifica che si vuole eliminare è usata in uno o più documenti di spesa.<br>";
        }
        message += "Confermi l'eliminazione della qualifica?";
        let dialogRef = this.dialog.open(ConfermaDialog, {
          width: '40%',
          data: {
            message: message
          }
        });
        dialogRef.afterClosed().subscribe(res => {
          if (res === "SI") {
            this.loadedElimina = false;
            this.subscribers.eliminaQualifica = this.fornitoreService.eliminaQualifica(qualifica.progrFornitoreQualifica).subscribe(data => {
              if (data) {
                if (data.esito) {
                  this.showMessageSuccess(data.messaggio);
                  this.qualificheAdded = this.qualificheAdded.filter(q => q.idTable !== qualifica.idTable);
                  this.dataSourceQualifiche.data = this.qualificheAdded;
                } else {
                  this.showMessageError(data.messaggio);
                }
              }
              this.loadedElimina = true;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.loadedElimina = true;
              this.showMessageError("Errore nell'eliminazione della qualifica.");
            });
          }
        });
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nell'eliminazione della qualifica.");
        this.loadedVerificaQualifica = true;
      });
    }
    if (this.dataSourceQualifiche.data.length === 0) {
      let nessunaQualifica = new Array<QualificaDTO>();
      nessunaQualifica.push(new QualificaDTO(0, null, "Nessuna qualifica associata", null, null, null, null, null));
      this.dataSourceQualifiche.data = nessunaQualifica;
    }
  }

  resetCampi() {
    this.costoOrario = null;
    this.costoOrarioFormatted = null;
    this.qualificaSelected = null;
    this.nota = null;
    this.aggiungiModificaQualificaVisible = false;
  }

  aggiungiAllegato() {
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: this.allegatiAdded,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });

    dialogRef.afterClosed().subscribe((res: Array<InfoDocumentoVo>) => {
      if (res && res.length > 0) {
        this.allegatiAdded.push(...res.map(r => new DocumentoAllegatoDTO(null, null, +r.idDocumentoIndex, null, true, null, null, r.nome, null, null, null)));
        this.dataSourceAllegati.data = this.allegatiAdded;
      }
    });
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
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

  eliminaAllegato(allegato: DocumentoAllegatoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    if (!allegato.associato) { //nuovo allegato appena inserito
      this.allegatiAdded = this.allegatiAdded.filter(q => q.id !== allegato.id);
      this.dataSourceAllegati.data = this.allegatiAdded;
    } else { //allegato già associato su db
      this.loadedElimina = false;
      this.subscribers.disassociaAllegato = this.fornitoreService.disassociaAllegatoFornitore(allegato.id, this.fornitore.idFornitore, this.idProgetto).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.showMessageSuccess(data.messaggio);
            this.allegatiAdded = this.allegatiAdded.filter(q => q.id !== allegato.id);
            this.dataSourceAllegati.data = this.allegatiAdded;
          } else {
            this.showMessageError(data.messaggio);
          }
        }
        this.loadedElimina = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nella disassociazione dell'allegato'.");
        this.loadedElimina = true;
      });
    }
    if (this.dataSourceAllegati.data.length === 0) {
      let nessunAllegato = new Array<DocumentoAllegatoDTO>();
      nessunAllegato.push(new DocumentoAllegatoDTO(null, null, 0, null, null, null, null, "Nessun allegato presente", null, null, null));
      this.dataSourceAllegati.data = nessunAllegato;
    }
  }

  salva() {
    this.formComponent.salva();
  }

  saved(event: any) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.fornitore = event.fornitore;
    this.isNuovo = false;
    if (this.qualificheAdded && this.qualificheAdded.length > 0 && this.qualificheAdded.filter(q => q.progrFornitoreQualifica == null || q.modificata).length > 0) {
      this.loadedSaveQualifiche = false;
      this.subscribers.salvaQualifiche = this.fornitoreService.salvaQualifiche(new SalvaQualificheRequest(this.qualificheAdded.filter(q => q.progrFornitoreQualifica == null || q.modificata)
        .map(q => new QualificaFormDTO(q.progrFornitoreQualifica, this.fornitore.idFornitore, q.idQualifica, q.costoOrario, q.noteQualifica)))).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.qualificheAdded.forEach(q => q.modificata = false);
              this.messageSuccess = data.messaggio;
            } else {
              this.messageError = data.messaggio;
            }
          }
          if (this.allegatiAdded && this.allegatiAdded.length > 0 && this.allegatiAdded.filter(a => !a.associato).length > 0) {
            this.associaAllegatiFornitore();
          } else {
            if (this.messageSuccess) {
              this.showMessageSuccess(this.messageSuccess);
            } else {
              this.showMessageError(this.messageError);
            }
          }
          this.loadedSaveQualifiche = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.messageError = "Errore nel salvataggio delle qualifiche"
          if (this.allegatiAdded && this.allegatiAdded.length > 0) {
            this.associaAllegatiFornitore();
          } else {
            this.showMessageError(this.messageError);
          }

          this.loadedSaveQualifiche = true;
        });
    } else if (this.allegatiAdded && this.allegatiAdded.length > 0 && this.allegatiAdded.filter(a => !a.associato).length > 0) {
      this.associaAllegatiFornitore();
    }
    if (!(this.qualificheAdded && this.qualificheAdded.length > 0 && this.qualificheAdded.filter(q => q.progrFornitoreQualifica == null || q.modificata).length > 0)
      && !(this.allegatiAdded && this.allegatiAdded.length > 0 && this.allegatiAdded.filter(a => !a.associato).length > 0)) {
      // Se siamo nel caso della selezione dei fornitori per la fattura elettronica:
      // per evitare il rischio di storicizzare inutilmente, salvo solo se l'utente ha scelto di usare i dati della fattura
      // o se ha scelto i dati del db e li ha modificati.
      if (typeof event.esito === "string" && !this.isSeleziona) { //Nessun dato è stato modificato.
        this.showMessageError(event.esito);
      } else {
        this.showMessageSuccess("Salvataggio avvenuto con successo");
      }
    }

  }

  associaAllegatiFornitore() {
    this.loadedSaveAllegati = false;
    this.subscribers.associaAllegati = this.fornitoreService.associaAllegatiAFornitore(
      new AssociaFilesRequest(this.allegatiAdded.filter(a => !a.associato).map(a => a.id), this.fornitore.idFornitore, this.idProgetto)).subscribe(data => {
        if (data) {
          if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
            && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
            this.allegatiAdded.forEach(a => a.associato = true);
            this.messageSuccess = this.messageSuccess ? this.messageSuccess + "<br>" : "";
            this.messageSuccess += "Tutti gli allegati sono stati associati correttamente al fornitore.";
            this.showMessageSuccess(this.messageSuccess);
          } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
            && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
            this.messageError = this.messageError ? this.messageError + "<br>" : "";
            this.messageError += "Errore nell'associazione degli allegati al fornitore.";
            this.showMessageError(this.messageError);
          } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
            && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
            this.allegatiAdded.forEach(a => {
              if (data.elencoIdDocIndexFilesAssociati.find(id => id === a.id)) {
                a.associato = true;
              }
            })
            this.messageError = this.messageError ? this.messageError + "<br>" : "";
            this.messageError += "Errore nell'associazione dei seguenti allegati al fornitore: ";
            data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
              let allegato = this.allegatiAdded.find(a => a.id === id);
              this.messageError += allegato.nome + ", ";
            });
            this.messageError = this.messageError.substr(0, this.messageError.length - 2);
            this.showMessageError(this.messageError);
          }
        }
        this.loadedSaveAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.messageError = this.messageError ? this.messageError + "<br>" : "";
        this.showMessageError(this.messageError + "Errore nell'associazione degli allegati al fornitore");
        this.loadedSaveAllegati = true;
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
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close() {
    let res = this.isNuovo ? { isNuovo: true } : {
      isNuovo: false,
      idFornitore: this.fornitore.idFornitore,
      idTipoFornitore: this.fornitore.idTipoSoggetto
    };
    this.dialogRef.close(res);
  }

  isLoading() {
    if (!this.loadedQualifiche || !this.loadedAllegati || !this.loadedDownload || !this.loadedSaveQualifiche || !this.loadedSaveAllegati
      || !this.loadedVerificaQualifica || !this.loadedElimina) {
      return true;
    }
    return this.formComponent ? this.formComponent.isLoading() : true;
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }

}
