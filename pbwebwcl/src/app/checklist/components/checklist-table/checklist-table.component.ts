import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { DocumentiProgettoService } from 'src/app/documenti-progetto/services/documenti-progetto.service';
import { AllegatiDichiarazioneDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/allegati-dichiarazione-di-spesa-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AllegatiDichiarazioneSpesaDialogComponent } from 'src/app/validazione/components/allegati-dichiarazione-spesa-dialog/allegati-dichiarazione-spesa-dialog.component';
import { ValidazioneService } from 'src/app/validazione/services/validazione.service';
import { CheckListItem } from '../../commons/dto/CheckListItem';
import { NavigationChecklistService } from '../../services/navigation-checklist.service';
import { AnteprimaDialogComponent, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';

@Component({
  selector: 'app-checklist-table',
  templateUrl: './checklist-table.component.html',
  styleUrls: ['./checklist-table.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ChecklistTableComponent implements OnInit, OnChanges {

  @Input('elencoChecklist') elencoChecklist: Array<CheckListItem>;
  @Input('isEliminabile') isEliminabile: boolean;
  @Input('isModificabile') isModificabile: boolean;
  @Input('idProgetto') idProgetto: number;

  @Output('download') download = new EventEmitter<CheckListItem>();
  @Output('elimina') elimina = new EventEmitter<CheckListItem>();
  @Output('modifica') modifica = new EventEmitter<CheckListItem>();

  dataSource: MatTableDataSource<CheckListItem>;
  displayedColumns: string[] = ['allegati', 'nomedoc', 'tipodoc', 'stato', 'versione', 'data', 'soggetto', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  messageError: string;
  isMessageErrorVisible: boolean;
  //visualizzazione modifiche
  isVisible: boolean = false;
  // Costante Bandi FP
  isFP: boolean = true;

  //LOADED
  loadedAllegati: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  idBandoLinea;
  constructor(
    private documentiProgettoService: DocumentiProgettoService,
    private handleExceptionService: HandleExceptionService,
    private navigationService: NavigationChecklistService,
    private dialog: MatDialog, private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private configService: ConfigService
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idBandoLinea = params['idBandoLinea'] ? +params['idBandoLinea'] : null;
      console.log('this.idBandoLinea');
      console.log(this.idBandoLinea);
      this.isVisibleFunc();
      this.isFpFunc();
    });
  }

  isVisibleFunc() {
    // Controllo se abilitare le nuove finzioni e disabilitare le vecchie in base all'ambiente
    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      console.log("isCostanteFinpiemonte: ", data)

      this.isVisible = data;
    })
  }

  isFpFunc() {
    console.log("THIS:IDBANDOLINEA", this.idBandoLinea);
    this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
      this.isFP = data;
      console.log("IS FP: ", this.isFP)
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.elencoChecklist && this.elencoChecklist) {
      this.dataSource = new MatTableDataSource<CheckListItem>();
      this.dataSource.data = this.elencoChecklist;
      this.paginator.length = this.elencoChecklist.length;
      this.paginator.pageIndex = 0;
      this.dataSource.paginator = this.paginator;
      this.ripristinaPaginator();
    }
  }

  downloadFile(item: CheckListItem) {
    this.resetMessageError();
    this.download.emit(item);
  }

  eliminaFile(item: CheckListItem) {
    this.resetMessageError();
    this.elimina.emit(item);
  }

  modificaFile(item: CheckListItem) {
    this.resetMessageError();
    this.modifica.emit(item);
  }

  anteprimaAllegato(nomeFile: string, idDocumentoIndex: string, codTipoDoc: string) {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(nomeFile);
    comodo.push(idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(codTipoDoc, nomeFile, null, null, null, null, null, idDocumentoIndex, null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(codTipoDoc);

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  openAllegati(doc: CheckListItem, messageSuccess?: string) {
    this.resetMessageError();
    this.loadedAllegati = false;
    if (this.isVisible && this.isFP) {
      if (doc.codiceTipo === "CLIL" || doc.codiceTipo === "CLILA" || doc.codiceTipo === "CL") {
        //this.subscribers.integrazioni = this.documentiProgettoService.allegatiVerbaleChecklist(+doc.idDichiarazione,doc.codiceTipo === "CL" ? "VCD" : "VCV").subscribe(data => {
        this.subscribers.integrazioni = this.documentiProgettoService.getAllegatiChecklist(+doc.idDichiarazione, doc.codiceTipo === "CL" ? "VCD" : "VCV", +doc.idDocumentoIndex).subscribe(data => {
          let allegati = new AllegatiDichiarazioneDiSpesaDTO(data, null);
          this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
            {
              width: '50%',
              maxHeight: '90%',
              data: {
                allegati: allegati,
                title: doc.codiceTipo === "CL" ? `Verbali allegati alla Checklist di Validazione ${doc.idDichiarazione}`
                  : `Verbali allegati alla Checklist in Loco ${doc.idDichiarazione}`,
                nascondiSizeFile: true
              }
            });
          this.loadedAllegati = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore nel recupero dgli allegati.");
          this.loadedAllegati = true;
        });
      }
    }

    else {
      if (doc.codiceTipo === "CLIL" || doc.codiceTipo === "CLILA" || doc.codiceTipo === "CL") {
        //this.subscribers.integrazioni = this.documentiProgettoService.allegatiVerbaleChecklist(+doc.idDichiarazione,doc.codiceTipo === "CL" ? "VCD" : "VCV").subscribe(data => {
        this.subscribers.integrazioni = this.documentiProgettoService.allegatiVerbaleChecklist(+doc.idDichiarazione, doc.codiceTipo === "CL" ? "VCD" : "VCV", +doc.idDocumentoIndex).subscribe(data => {
          let allegati = new AllegatiDichiarazioneDiSpesaDTO(data, null);
          let dialogRef = this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
            {
              width: '50%',
              maxHeight: '90%',
              data: {
                allegati: allegati,
                title: doc.codiceTipo === "CL" ? `Verbali allegati alla Checklist di Validazione ${doc.idDichiarazione}`
                  : `Verbali allegati alla Checklist in Loco ${doc.idDichiarazione}`,
                nascondiSizeFile: true,
                aggiungiAllegatiEnabled: true,
                codTipoChecklist: doc.codiceTipo,
                idDocumentoIndexChecklist: doc.idDocumentoIndex,
                idProgetto: this.idProgetto,
                messageSuccess: messageSuccess
              }
            });
          dialogRef.afterClosed().subscribe(res => {
            if (res && res.length > 0) {
              this.openAllegati(doc, res);
            }
          });
          this.loadedAllegati = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore nel recupero dgli allegati.");
          this.loadedAllegati = true;
        });
      }
    }
  }

  salvaPaginator() {
    if (this.dataSource) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }

  ripristinaPaginator() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollTable');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

}
