/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AnteprimaDialogComponent } from './../anteprima-dialog/anteprima-dialog.component';
import { UploadDialogComponent } from './../upload-dialog/upload-dialog.component';
import { InfoDialogComponent } from './../info-dialog/info-dialog.component';
import { SpostaDialogComponent } from './../sposta-dialog/sposta-dialog.component';
import { RinominaDialogComponent } from './../rinomina-dialog/rinomina-dialog.component';
import { EliminaDialogComponent } from './../elimina-dialog/elimina-dialog.component';
import { NuovaCartellaDialogComponent } from './../nuova-cartella-dialog/nuova-cartella-dialog.component';
import { SalvaFileVo } from './../../commons/vo/salva-file-vo';
import { FoldersVo } from './../../commons/vo/folders-vo';
import { LeftTreeVo } from '../../commons/vo/left-tree-vo';
import { ArchivioFileService } from './../../services/archivio-file.service';
import { HandleExceptionService } from './../../../core/services/handle-exception.service';
import { InfoDocumentoVo } from '../../commons/infoDocumento-vo';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FlatTreeControl } from '@angular/cdk/tree';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter, HostListener } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { ProgressBarMode } from '@angular/material/progress-bar';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';
import { saveAs } from "file-saver-es";
import { MatSnackBar } from '@angular/material/snack-bar';
import { ResizeEvent } from 'angular-resizable-element';
import { UserInfoSec } from '../../../core/commons/dto/user-info';
import { AutoUnsubscribe } from '../../../shared/decorator/auto-unsubscribe';
import { Observable } from 'rxjs';

class TreeNode {
  constructor(
    public name: string,
    public children?: Array<TreeNode>
  ) { }
}

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

export interface UserData {
  tipo: string;
  nome: string;
  ultimaModifica: string;
  dimensione: string;
}

@Component({
  selector: 'archivio-file',
  templateUrl: './archivio-file.component.html',
  styleUrls: ['./archivio-file.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ArchivioFileComponent implements OnInit {

  @Input('apiURL') apiURL: string;
  @Input('user') user: UserInfoSec;
  @Input('drawerExpanded') drawerExpanded: Observable<boolean>;


  @ViewChild(MatSort, { static: false }) set content(sort: MatSort) {
    this.dataSourceTable.sort = sort;
  }

  private _transformer = (node: TreeNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  }

  @Input() isDialog: boolean;
  @Output() closeDialogEvent = new EventEmitter<boolean>();
  @Output() allegaFilesDialogEvent = new EventEmitter<InfoDocumentoVo[]>();

  public styleLeftCard: object = {};
  public styleRightCard: object = {};
  public styleTree: object = {};

  mostraInviatiChecked = true;
  cartellaSelezionata: string;
  path: string;
  maxFileSize: number;
  estensioniAmmesse: string;
  listDocumenti: Array<InfoDocumentoVo>;
  listDocumentiNonInviati: Array<InfoDocumentoVo>;
  leftTree: LeftTreeVo;
  rightTree: Array<FoldersVo>;
  dataTreeRoot: Array<TreeNode>;
  totalMemoryValue: any;
  usedMemoryValue: any;
  isLoading: boolean;
  cartellaVuota: boolean;
  heightCard = '500px';
  leftLeftCard: string;
  selectedTreeFolder: string;
  subscribers: any = {};
  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level, node => node.expandable);
  treeFlattener = new MatTreeFlattener(
    this._transformer, node => node.level, node => node.expandable, node => node.children);
  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
  displayedColumns: string[] = ['select', 'nome', 'ultimaModifica', 'dimensione', 'info', 'anteprima'];
  dataSourceTable: MatTableDataSource<InfoDocumentoVo>;
  selection = new SelectionModel<InfoDocumentoVo>(true, []);
  color: ThemePalette = 'primary';
  mode: ProgressBarMode = 'determinate';
  value = 0.4;
  bufferValue = 100;

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  constructor(
    private _snackBar: MatSnackBar,
    private archivioFileService: ArchivioFileService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog
  ) {
    this.listDocumentiNonInviati = new Array<InfoDocumentoVo>();
    this.listDocumenti = new Array<InfoDocumentoVo>();
    this.dataSourceTable = new MatTableDataSource(this.listDocumenti);
  }

  @ViewChild('uploader') uploader;

  clearFile() {
    this.uploader.nativeElement.value = '';
  }

  ngOnInit(): void {

    // Gestione pagina all'apertura/chiusura del drawer
    this.subscribers.expanded = this.drawerExpanded.subscribe(data => {
      var cardLeftId = document.getElementById("cardLeft");
      var widthLeftCard = cardLeftId.offsetWidth;

      setTimeout(() => {
        // settaggio stile per card sinistra
        var sideNavId = document.getElementById("sideNavId");
        this.styleLeftCard = {
          left: sideNavId.offsetWidth + "px",
          width: widthLeftCard - 1 + "px"
        };

        // settaggio stile per card destra

        this.styleRightCard = {
          left: widthLeftCard - 23 + "px",
          height: this.heightCard,
          width: window.innerWidth - sideNavId.offsetWidth - widthLeftCard - 3 + "px",
        }

        // settaggio stile per alberatura
        var cardLeftId = document.getElementById("contentCardLeft");
        this.styleTree = {
          width: cardLeftId.scrollWidth + "px"
        }
      }, 100)
    });
    this.isLoading = true;
    if (this.user) {
      // costruzione array per alberatura a sinistra
      this.archivioFileService.inizializzaArchivioFile(this.apiURL, this.user).subscribe(data => {
        this.leftTree = data;
        this.dataTreeRoot = new Array<TreeNode>();
        for (var val of this.leftTree.folders) {
          if (val.folders == null) {
            this.dataTreeRoot.push(new TreeNode(val.nomeFolder + '.*.' + val.idFolder));
          } else {
            var arrayCostruito = this.costruireArrayConCartelleFiglie(val.folders);
            this.dataTreeRoot.push(new TreeNode(val.nomeFolder + '.*.' + val.idFolder, arrayCostruito));
          }
          this.dataSource.data = this.dataTreeRoot;
        }
        this.totalMemoryValue = (this.leftTree.userSpaceDTO.total / 1024000).toFixed(2);
        this.usedMemoryValue = (this.leftTree.userSpaceDTO.used / 1024000000).toFixed(2);
        this.value = ((this.leftTree.userSpaceDTO.used / 1024) * 100) / this.leftTree.userSpaceDTO.total;
        if (this.dataTreeRoot[0]) {
          var idFolderRoot = this.dataTreeRoot[0].name.split(".*.")[1];
        }

        // costruzione stringa per estensioni ammesse
        var comodoEstensioniConsentite = "";
        for (let i = 0; i < this.leftTree.estensioniConsentite.length; i++) {
          if (i == 0) {
            comodoEstensioniConsentite = "." + this.leftTree.estensioniConsentite[i].trim();
          } else {
            comodoEstensioniConsentite = comodoEstensioniConsentite + ", ." + this.leftTree.estensioniConsentite[i].trim();
          }
        }
        this.estensioniAmmesse = comodoEstensioniConsentite;

        // recuperare valore dimensioni massime file
        this.maxFileSize = this.leftTree.dimMaxSingoloFile;

        // costruzione array per alberatura a destra
        this.leggiFolder(idFolderRoot, true);
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    }
  }

  @HostListener('window:resize', ['$event'])
  sizeChange(event) {
    setTimeout(() => {
      // settaggio altezza card
      var divId = document.getElementById("div1");
      var divIdTopDistance = divId.getBoundingClientRect().top; // The distance (length) of div from the top of screen
      var cardHeight = window.innerHeight - divIdTopDistance;
      if (this.isDialog) {
        var divActionButtons = document.getElementById("divActionButtons");
        this.heightCard = cardHeight - 50 - divActionButtons.offsetHeight + "px";
      } else {
        this.heightCard = cardHeight + "px";
      }

      // settaggio stile per card sinistra
      var sideNavId = document.getElementById("sideNavId");
      if (this.isDialog) {
        this.styleLeftCard = {
          left: "50px"
        };
      } else {
        this.styleLeftCard = {
          left: sideNavId.offsetWidth + "px"
        };
      }

      // settaggio stile per card destra
      var cardLeftId = document.getElementById("cardLeft");
      if (this.isDialog) {
        this.styleRightCard = {
          left: cardLeftId.offsetWidth - 23 + "px",
          height: this.heightCard,
          width: window.innerWidth - cardLeftId.offsetWidth - 100 + "px",
        }
      } else {
        this.styleRightCard = {
          left: cardLeftId.offsetWidth - 24 + "px",
          height: this.heightCard,
          width: window.innerWidth - sideNavId.offsetWidth - cardLeftId.offsetWidth + "px",
        }
      }

      // settaggio stile per alberatura
      var cardLeftId = document.getElementById("contentCardLeft");
      this.styleTree = {
        width: cardLeftId.scrollWidth + "px"
      }
    }, 100)
  }

  onResizeEnd(event: ResizeEvent): void {
    var cardLeftId = document.getElementById("contentCardLeft");

    if (Number(`${event.rectangle.width}`) > cardLeftId.scrollWidth) {
      // espansione
      this.styleTree = {
        width: cardLeftId.scrollWidth + "px"
      }
    } else {
      // riduzione
      if (Number(`${event.rectangle.width}`) > 550) {
        this.styleTree = {
          width: cardLeftId.scrollWidth + "px"
        }
      } else {
        if (cardLeftId.scrollWidth > 550) {
          this.styleTree = {
            width: "550px"
          }
        } else {
          this.styleTree = {
            width: cardLeftId.scrollWidth + "px"
          }
        }
      }
    }
  }

  resizing(event: ResizeEvent): void {

    // settaggio stile card sinistra
    var sideNavId = document.getElementById("sideNavId");
    if (this.isDialog) {
      this.styleLeftCard = {
        position: 'fixed',
        width: `${event.rectangle.width}px`,
        left: "50px"
      };
    } else {
      this.styleLeftCard = {
        position: 'fixed',
        width: `${event.rectangle.width}px`,
        left: sideNavId.offsetWidth + "px"
      };
    }

    // settaggio stile card destra
    if (this.isDialog) {
      this.styleRightCard = {
        left: event.rectangle.width - 24 + 2 + "px",
        height: this.heightCard,
        width: window.innerWidth - event.rectangle.width - 2 - 100 + "px",
      }
    } else {
      this.styleRightCard = {
        left: event.rectangle.width - 24 + 2 + "px",
        height: this.heightCard,
        width: window.innerWidth - sideNavId.offsetWidth - event.rectangle.width - 2 + "px",
      }
    }
  }

  leggiFolder(idFolder: string, ngOnInit: boolean) {
    this.selectedTreeFolder = idFolder;
    this.cartellaSelezionata = idFolder;
    this.isLoading = true;

    // costruzione array per alberatura a destra
    this.archivioFileService.leggiFolder(this.apiURL, idFolder, this.user).subscribe(data => {
      this.rightTree = data;
      this.listDocumenti = new Array<InfoDocumentoVo>();
      this.listDocumentiNonInviati = new Array<InfoDocumentoVo>();

      // leggi cartelle
      if (!(this.rightTree[0].folders == null)) {
        for (var val of this.rightTree[0].folders) {
          this.listDocumenti.push(new InfoDocumentoVo('Cartella', val.nomeFolder, new Date(val.dtInserimento).toLocaleDateString(), '', false, false, 'ddd', null, val.idFolder.toString()));
          this.listDocumentiNonInviati.push(new InfoDocumentoVo('Cartella', val.nomeFolder, new Date(val.dtInserimento).toLocaleDateString(), '', false, false, 'ddd', null, val.idFolder.toString()));
        }
      }

      // leggi file
      if (!(this.rightTree[0].files == null)) {
        for (var val1 of this.rightTree[0].files) {
          var associato = false;
          if (val1.entityAssociated == null) {
            associato = false;
          } else {
            associato = true;
          }
          this.listDocumenti.push(new InfoDocumentoVo('File', val1.nomeFile, new Date(val1.dtInserimento).toLocaleDateString(), (val1.sizeFile / 1024).toFixed(2).toString() + " KB", associato, val1.isLocked, 'ddd', val1.idDocumentoIndex.toString(), val1.idFolder.toString()));
          if (!val1.isLocked) {
            this.listDocumentiNonInviati.push(new InfoDocumentoVo('File', val1.nomeFile, new Date(val1.dtInserimento).toLocaleDateString(), (val1.sizeFile / 1024).toFixed(2).toString() + " KB", associato, val1.isLocked, 'ddd', val1.idDocumentoIndex.toString(), val1.idFolder.toString()));
          }
        }
      }

      if (this.mostraInviatiChecked) {
        this.dataSourceTable = new MatTableDataSource(this.listDocumenti);
      } else {
        this.dataSourceTable = new MatTableDataSource(this.listDocumentiNonInviati);
      }

      if (this.listDocumenti.length == 0 && this.listDocumentiNonInviati.length == 0) {
        this.cartellaVuota = true;
      } else {
        this.cartellaVuota = false;
      }

      // azzerare checkbox selezionate
      this.selection = new SelectionModel<InfoDocumentoVo>(true, []);
      this.isLoading = false;
      this.path = this.generaPath(idFolder);

      if (ngOnInit) {
        setTimeout(() => {
          // settaggio altezza card
          var divId = document.getElementById("div1");
          var divIdTopDistance = divId.getBoundingClientRect().top; // The distance (length) of div from the top of screen
          var cardHeight = window.innerHeight - divIdTopDistance;
          if (this.isDialog) {
            var divActionButtons = document.getElementById("divActionButtons");
            this.heightCard = cardHeight - 50 - divActionButtons.offsetHeight + "px";
          } else {
            this.heightCard = cardHeight + "px";
          }

          // settaggio stile per card sinistra
          var sideNavId = document.getElementById("sideNavId");
          if (this.isDialog) {
            this.styleLeftCard = {
              left: "50px"
            };
          } else {
            this.styleLeftCard = {
              left: sideNavId.offsetWidth + "px"
            };
          }

          // settaggio stile per card destra
          var cardLeftId = document.getElementById("cardLeft");
          if (this.isDialog) {
            this.styleRightCard = {
              left: cardLeftId.offsetWidth - 23 + "px",
              height: this.heightCard,
              width: window.innerWidth - cardLeftId.offsetWidth - 100 + "px",
            }
          } else {
            this.styleRightCard = {
              left: cardLeftId.offsetWidth - 24 + "px",
              height: this.heightCard,
              width: window.innerWidth - sideNavId.offsetWidth - cardLeftId.offsetWidth + "px",
            }
          }

          // settaggio stile per alberatura
          var cardLeftId = document.getElementById("contentCardLeft");
          this.styleTree = {
            width: cardLeftId.scrollWidth + "px"
          }

          window.dispatchEvent(new Event('resize'));
        }, 100)
      }
    }, err => {
      this.openSnackBar("Non è possibile caricare il contenuto della cartella");
      this.isLoading = false;

      if (ngOnInit) {
        setTimeout(() => {
          // settaggio altezza card
          var divId = document.getElementById("div1");
          var divIdTopDistance = divId.getBoundingClientRect().top; // The distance (length) of div from the top of screen
          var cardHeight = window.innerHeight - divIdTopDistance;
          if (this.isDialog) {
            var divActionButtons = document.getElementById("divActionButtons");
            this.heightCard = cardHeight - 50 - divActionButtons.offsetHeight + "px";
          } else {
            this.heightCard = cardHeight + "px";
          }

          // settaggio stile per card sinistra
          var sideNavId = document.getElementById("sideNavId");
          if (this.isDialog) {
            this.styleLeftCard = {
              left: "50px"
            };
          } else {
            this.styleLeftCard = {
              left: sideNavId.offsetWidth + "px"
            };
          }

          // settaggio stile per card destra
          var cardLeftId = document.getElementById("cardLeft");
          if (this.isDialog) {
            this.styleRightCard = {
              left: cardLeftId.offsetWidth - 23 + "px",
              height: this.heightCard,
              width: window.innerWidth - cardLeftId.offsetWidth - 100 + "px",
            }
          } else {
            this.styleRightCard = {
              left: cardLeftId.offsetWidth - 24 + "px",
              height: this.heightCard,
              width: window.innerWidth - sideNavId.offsetWidth - cardLeftId.offsetWidth + "px",
            }
          }

          // settaggio stile per alberatura
          var cardLeftId = document.getElementById("contentCardLeft");
          this.styleTree = {
            width: cardLeftId.scrollWidth + "px"
          }

          window.dispatchEvent(new Event('resize'));
        }, 100)
      }
    });
  }

  espandereCartella() {

    // settaggio stile per alberatura
    var cardLeftId = document.getElementById("contentCardLeft");
    this.styleTree = {
      width: cardLeftId.scrollWidth + "px"
    }
  }

  validate(event: ResizeEvent): boolean {
    const MIN_DIMENSIONS_PX: number = 50;
    if (
      event.rectangle.width &&
      event.rectangle.height &&
      (event.rectangle.width < MIN_DIMENSIONS_PX ||
        event.rectangle.height < MIN_DIMENSIONS_PX)
    ) {
      return false;
    }
    return true;
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSourceTable.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSourceTable.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: InfoDocumentoVo): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'}`;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceTable.filter = filterValue.trim().toLowerCase();
  }

  mostrareAnteprima(nomeFile: string) {
    var splitted = nomeFile.split(".");
    if (splitted.length == 1) {
      return false;
    } else {
      if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "P7M" || splitted[splitted.length - 1] == "p7m") {
        return true;
      } else {
        return false;
      }
    }
  }

  costruireArrayConCartelleFiglie(folders: Array<FoldersVo>) {
    var comodo = new Array<TreeNode>();

    for (var val of folders) {
      if (val.folders == null) {
        comodo.push(new TreeNode(val.nomeFolder + '.*.' + val.idFolder));
      } else {
        var arrayCostruito = this.costruireArrayConCartelleFiglie(val.folders);
        comodo.push(new TreeNode(val.nomeFolder + '.*.' + val.idFolder, arrayCostruito));
      }
    }

    return comodo;
  }

  esplodiContenutoCartella(cartella: string) {
    var idFolder = cartella.split(".*.")[1];
    this.leggiFolder(idFolder, false);
  }

  ottieniNomeFolderDaStringa(testo: string) {
    return testo.split(".*.")[0];
  }

  ottieniIdFolderDaStringa(testo: string) {
    return testo.split(".*.")[1];
  }

  uploadFile($event: any) {
    console.log("inizio upload file");
    var comodo = new Array<any>();
    comodo.push($event.target.files);
    comodo.push(this.maxFileSize);
    comodo.push(this.dataSourceTable);

    const dialogRef = this.dialog.open(UploadDialogComponent, {
      data: comodo,
      width: '30%'
    });
    console.log("inizio dialogRef.afterClosed");
    dialogRef.afterClosed().subscribe(result => {
      if (!(result.data == null)) {
        this.isLoading = true;
        this.archivioFileService.salvaFiles(this.apiURL, this.cartellaSelezionata, result.data, this.user).subscribe(data => {
          this.isLoading = false;
          var esitoSalvataggio: Array<SalvaFileVo>;
          esitoSalvataggio = data;
          var unErrore = false;
          var unCorretto = false;
          for (var val of esitoSalvataggio) {
            if (val.esito) {
              unCorretto = true;
            } else {
              unErrore = true;
            }
          }

          if (unCorretto) {
            this.openSnackBar("Upload avvenuto con successo");
          } else {
            this.openSnackBar("Upload non avvenuto con successo");
          }

          // Refresh contenuto cartella
          this.leggiFolder(this.cartellaSelezionata, false);

          // Refresh root
          this.refreshRoot();
        }, err => {
          this.openSnackBar("Upload non avvenuto con successo");

          // Refresh contenuto cartella
          this.leggiFolder(this.cartellaSelezionata, false);

          // Refresh root
          this.refreshRoot();
        });
      }
    })
    console.log("fine dialogRef.afterClosed");
    console.log("fine upload file");
  }

  generaPath(idFolderClicked: string) {
    var percorso: string;
    for (var val of this.dataTreeRoot) {
      var nomeFolder = this.ottieniNomeFolderDaStringa(val.name);
      var idFolder = this.ottieniIdFolderDaStringa(val.name);

      if (nomeFolder.startsWith("/")) {
        percorso = nomeFolder;
      } else {
        percorso = "/" + nomeFolder;
      }

      if (idFolder == idFolderClicked) {
        return percorso;
      } else {
        for (var val1 of val.children) {
          var comodoPercorso = this.esploraChildren(percorso, val1, idFolderClicked);
          if (!(comodoPercorso == null)) {
            return comodoPercorso;
          }
        }
      }
    }
  }

  esploraChildren(percorso: string, item: TreeNode, idFolderClicked: string) {
    var nomeFolder = this.ottieniNomeFolderDaStringa(item.name);
    var idFolder = this.ottieniIdFolderDaStringa(item.name);
    var percorso1 = "";

    if (nomeFolder.startsWith("/")) {
      percorso1 = percorso + nomeFolder;
    } else {
      percorso1 = percorso + "/" + nomeFolder;
    }

    if (idFolderClicked == idFolder) {
      return percorso1;
    } else {
      if (item.children == undefined) {
        return null;
      } else {
        for (var val of item.children) {
          var comodoPercorso1 = this.esploraChildren(percorso1, val, idFolderClicked);
          if (!(comodoPercorso1 == null)) {
            return comodoPercorso1;
          }
        }
        return null;
      }
    }
  }

  createFolder() {
    const dialogRef = this.dialog.open(NuovaCartellaDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (!(result.data == null)) {
        this.isLoading = true;
        this.archivioFileService.creaFolder(this.apiURL, result.data, this.cartellaSelezionata, this.user).subscribe(data => {

          // Refresh contenuto cartella
          this.leggiFolder(this.cartellaSelezionata, false);

          // Refresh root
          this.refreshRoot();

          this.isLoading = false;

          this.openSnackBar("La cartella è stata creata");
        }, err => {
          this.isLoading = false;
          this.openSnackBar("Non è possibile creare la cartella");
        });
      }
    })
  }

  elimina() {
    if (this.selection.selected.length == 0) {
      this.openSnackBar("Selezionare almeno un file / cartella");
    } else {
      for (var val of this.selection.selected) {
        if (val.inviato) {
          this.openSnackBar("Non è possibile eliminare un file inviato");
          return;
        }

        if (val.tipo == "Cartella") {
          if (val.nome.substring(0, 1) == '/') {
            this.openSnackBar("Non è consentito eliminare le cartelle di progetto");
            return;
          }
        }
      }

      const dialogRef = this.dialog.open(EliminaDialogComponent, {});
      dialogRef.afterClosed().subscribe(result => {
        if (result.data) {
          var allFileDeleted = true;
          this.isLoading = true;

          for (let i = 0; i < this.selection.selected.length; i++) {
            if (this.selection.selected[i].tipo == "Cartella") {

              this.archivioFileService.cancellaFolder(this.apiURL, this.selection.selected[i].idFolder, this.user).subscribe(data => {
                if (i == (this.selection.selected.length - 1)) {
                  if (allFileDeleted) {
                    this.openSnackBar("File e/o cartelle eliminati");
                  } else {
                    this.openSnackBar("Non è possibile eliminare tutti gli elementi selezionati");
                  }

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                }

                this.isLoading = false;
              }, err => {
                if (i == (this.selection.selected.length - 1)) {
                  this.openSnackBar("Non è possibile eliminare tutti gli elementi selezionati");

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                } else {
                  allFileDeleted = false;
                }

                this.isLoading = false;
              });
            } else {
              this.archivioFileService.cancellaFile(this.apiURL, this.selection.selected[i].idDocumentoIndex, this.user).subscribe(data => {
                if (i == (this.selection.selected.length - 1)) {
                  if (allFileDeleted) {
                    this.openSnackBar("File e/o cartelle eliminati");
                  } else {
                    this.openSnackBar("Non è possibile eliminare tutti gli elementi selezionati");
                  }

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                }

                this.isLoading = false;
              }, err => {
                if (i == (this.selection.selected.length - 1)) {
                  this.openSnackBar("Non è possibile eliminare tutti gli elementi selezionati");

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                } else {
                  allFileDeleted = false;
                }

                this.isLoading = false;
              });
            }
          }
        }
      })
    }
  }

  rinomina() {
    if (this.selection.selected.length == 0) {
      this.openSnackBar("Selezionare un file o una cartella");
    } else {
      if (this.selection.selected.length > 1) {
        this.openSnackBar("Selezionare solo un file o una cartella");
      } else {
        for (var val of this.selection.selected) {
          if (val.tipo == "Cartella") {
            if (val.nome.substring(0, 1) == '/') {
              this.openSnackBar("Non è possiible rinominare cartelle di progetto");
              break;
            }
          } else {
            if (val.inviato) {
              this.openSnackBar("Non è possibile rinominare un file inviato");
              break;
            }
          }

          var comodo = new Array<string>();
          comodo.push(val.tipo);
          comodo.push(val.nome);
          const dialogRef = this.dialog.open(RinominaDialogComponent, {
            data: comodo
          });
          dialogRef.afterClosed().subscribe(result => {
            if (!(result.data == null)) {
              if (val.tipo == "Cartella") {
                this.isLoading = true;
                this.archivioFileService.rinominaFolder(this.apiURL, val.idFolder, result.data, this.user).subscribe(data => {

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();

                  this.isLoading = false;
                  this.openSnackBar("Cartella rinominata con successo");
                }, err => {
                  this.openSnackBar("Non è possibile rinominare la cartella");
                });
              } else {
                this.isLoading = true;
                this.archivioFileService.rinominaFile(this.apiURL, val.idDocumentoIndex, result.data, this.user).subscribe(data => {

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();

                  this.isLoading = false;
                  this.openSnackBar("File rinominato con successo");
                }, err => {
                  this.openSnackBar("Non è possibile rinominare il file");
                });
              }
            }
          })
        }
      }
    }
  }

  sposta() {
    if (this.selection.selected.length == 0) {
      this.openSnackBar("Selezionare almeno un file o cartella");
    } else {
      for (var row of this.selection.selected) {
        if (row.tipo == "Cartella") {
          if (row.nome.substring(0, 1) == '/') {
            this.openSnackBar("Non è consentito spostare le cartelle di progetto");
            return;
          }
        }
      }

      const dialogRef = this.dialog.open(SpostaDialogComponent, {
        data: this.dataSource.data
      });
      dialogRef.afterClosed().subscribe(result => {
        if (!(result.data == 0)) {
          var allFileMoved = true;
          this.isLoading = true;
          for (let i = 0; i < this.selection.selected.length; i++) {
            if (this.selection.selected[i].tipo == "Cartella") {

              this.archivioFileService.spostaFolder(this.apiURL, this.selection.selected[i].idFolder, result.data, this.user).subscribe(data => {

                if (i == (this.selection.selected.length - 1)) {
                  if (allFileMoved) {
                    this.openSnackBar("File e/o cartelle spostati");
                  } else {
                    this.openSnackBar("Non è possibile spostare tutti gli elementi selezionati");
                  }

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                }

                this.isLoading = false;
              }, err => {
                if (i == (this.selection.selected.length - 1)) {
                  this.openSnackBar("Non è possibile spostare tutti gli elementi selezionati");

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                } else {
                  allFileMoved = false;
                }

                this.isLoading = false;
              });
            } else {
              this.archivioFileService.spostaFile(this.apiURL, this.selection.selected[i].idDocumentoIndex, this.selection.selected[i].idFolder, result.data, this.user).subscribe(data => {

                if (i == (this.selection.selected.length - 1)) {
                  if (allFileMoved) {
                    this.openSnackBar("File e/o cartelle spostati");
                  } else {
                    this.openSnackBar("Non è possibile spostare tutti gli elementi selezionati");
                  }

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                }

                this.isLoading = false;
              }, err => {
                if (i == (this.selection.selected.length - 1)) {
                  this.openSnackBar("Non è possibile spostare tutti gli elementi selezionati");

                  // Refresh contenuto cartella
                  this.leggiFolder(this.cartellaSelezionata, false);

                  // Refresh root
                  this.refreshRoot();
                } else {
                  allFileMoved = false;
                }

                this.isLoading = false;
              });
            }
          }
        }
      })
    }
  }

  refreshRoot() {
    this.archivioFileService.leggiRoot(this.apiURL, this.user).subscribe(data => {
      this.dataTreeRoot = new Array<TreeNode>();
      for (var val of data) {
        if (val.folders == null) {
          this.dataTreeRoot.push(new TreeNode(val.nomeFolder + '.*.' + val.idFolder));
        } else {
          var arrayCostruito = this.costruireArrayConCartelleFiglie(val.folders);
          this.dataTreeRoot.push(new TreeNode(val.nomeFolder + '.*.' + val.idFolder, arrayCostruito));
        }
        this.dataSource.data = this.dataTreeRoot;
      }
    }, err => {
      this.openSnackBar("Non è possibile caricare il contenuto della root");
      this.isLoading = false;
    });
  }

  mostraInviati(param: any) {
    this.mostraInviatiChecked = param.checked;

    if (param.checked) {
      this.dataSourceTable = new MatTableDataSource(this.listDocumenti);
    } else {
      this.dataSourceTable = new MatTableDataSource(this.listDocumentiNonInviati);
    }
  }

  download(idDocumentoIndex: string, nomeDocumento: string) {
    this.isLoading = true;
    this.archivioFileService.leggiFile(this.apiURL, idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.isLoading = false;
    }, error => {
      this.openSnackBar("Errore in fase di download del file");
      this.isLoading = false;
    });
  }

  infoFile(idDocumentoIndex: string, idFolder: string) {
    this.isLoading = true;
    this.archivioFileService.infoFile(this.apiURL, idDocumentoIndex, idFolder, this.user).subscribe(res => {
      this.dialog.open(InfoDialogComponent, {
        data: res
      });
      this.isLoading = false;
    }, error => {
      this.openSnackBar("Errore in fase di recupero delle info del file");
      this.isLoading = false;
    });
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  anteprima(nomeFile: string, idDocumentoIndex: string) {
    var comodo = new Array<any>();
    comodo.push(nomeFile);
    comodo.push(idDocumentoIndex);
    comodo.push(this.dataSourceTable);
    comodo.push(this.apiURL);

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  allegaFilesDialog() {
    let files = this.selection.selected.filter(s => s.tipo === "File");
    if (this.selection.selected.length === 0 || files.length === 0) {
      this.openSnackBar("Selezionare almeno un file");
      return;
    }
    this.openSnackBar(files.length === 1 ? "File allegato con successo." : "Files allegati con successo.");
    this.allegaFilesDialogEvent.emit(files);
  }

  chiudiDialog() {
    this.closeDialogEvent.emit(true);
  }

  getTooltipText(tipo: string, inviato: boolean) {
    if (tipo == "description") {
      if (inviato) {
        return "Il file non risulta essere associato ma risulta essere inviato"
      } else {
        return "Il file non risulta essere ne associato e ne inviato"
      }
    }

    if (tipo == "attachment") {
      if (inviato) {
        return "Il file risulta essere associato e inviato"
      } else {
        return "Il file risulta essere associato e non risulta essere inviato"
      }
    }
  }
}