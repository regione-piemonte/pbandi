/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/disimpegni/commons/models/codice-descrizione';
import { ChecklistRettificaForfettariaDTO } from '../../commons/dto/checklist-rettifica-forfettaria-dto';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { saveAs } from "file-saver-es";
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';
import { AffidamentiNewDTO } from '../../commons/dto/affidamenti-new-dto';
import { ChecklistNewDTO } from '../../commons/dto/checklist-new';
import { RettificaForfettariaDTO } from '../../commons/dto/rettifica-forfettaria-dto';
import { EsitoSalvaRettificaForfettariaDTO } from '../../commons/dto/esito-salva-rettifica-forfettaria-dto';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-rettifica-forfettaria',
  templateUrl: './rettifica-forfettaria.component.html',
  styleUrls: ['./rettifica-forfettaria.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RettificaForfettariaComponent implements OnInit {

  idOperazione: number;
  idProgetto: string;
  params: URLSearchParams;
  codiceProgetto: string;

  beneficiario: string;
  user: UserInfoSec;

  dataInserimento: string;

  autoritasControllante: Array<CodiceDescrizione>;
  autoritaControllanteSelected: CodiceDescrizione;

  percRett: string;

  //SUBSCRIBERS
  subscribers: any = {};

  displayedColumns: string[] = ['select', 'tipologia', 'oggetto', 'CPA-CIG', 'importo', 'esiti', 'checklist'];
  dataSource: MatTableDataSource<AffidamentiNewDTO>;
  selection = new SelectionModel<AffidamentiNewDTO>(true, []);

  myDate = new Date();

  messageError: string;
  isMessageErrorVisible: boolean;

  messageWarning: string;
  isMessageWarningVisible: boolean;

  idChecklistSelected: number;

  loadedgetChecklistRettifiche: boolean;
  loadedgetCategAnagrafica: boolean;
  loadedsalvaRettificaForfettaria: boolean;
  loadedgetDocumento: boolean;
  loadedpropostaAperta: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    private registroControlliService2: RegistroControlliService2,
    private dialog: MatDialog,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private datePipe: DatePipe
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    // RECUPERO PARAMETRI DA URL
    this.codiceProgetto = this.params.get('codProg');
    this.idProgetto = this.params.get('idProgetto');

    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.loadedpropostaAperta = true;
        this.subscribers.propostaAperta = this.registroControlliService2.propostaAperta(Number(this.idProgetto), this.user.beneficiarioSelezionato.idBeneficiario).subscribe(data => {
          this.loadedpropostaAperta = false;
          if (data.esito) {
            this.showMessageWarning(data.msg);
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di chiamata del servizio propostaAperta");
          this.loadedpropostaAperta = false;
        })

        this.loadedgetChecklistRettifiche = true;
        this.subscribers.getChecklistRettifiche = this.registroControlliService.getChecklistRettifiche(Number(this.idProgetto)).subscribe((res: any) => {

          this.loadedgetChecklistRettifiche = false;

          if (!(res.length == 0)) {
            this.dataSource = new MatTableDataSource(this.createNewArray(res));
          }

        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento delle checklist rettifiche");
          this.loadedgetChecklistRettifiche = false;
        });

        this.loadedgetCategAnagrafica = true;
        this.subscribers.getCategAnagrafica = this.registroControlliService.getCategAnagrafica().subscribe((res: Array<CodiceDescrizione>) => {
          this.autoritasControllante = res;
          this.loadedgetCategAnagrafica = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento delle categ anagrafiche");
          this.loadedgetCategAnagrafica = false;
        });
      }
    });

    const d = new Date();
    this.dataInserimento = d.toLocaleDateString().split(", ")[0];
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    var numRows;
    if (this.selection == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSource.data.length;
    }

    return numSelected === numRows;
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: AffidamentiNewDTO): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idAppalto + 1}`;
  }

  changeSingleRowCheckbox(row: any) {
    this.idChecklistSelected = undefined;
    if (this.selection.selected.length == 0) {
      this.selection.toggle(row);
    } else {
      if (this.selection.selected[0] == row) {
        this.selection.toggle(row);
      } else {
        this.selection.clear();
        this.selection.toggle(row);
      }
    }
  }

  indietro() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/controlli/` + this.idProgetto);
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

  salva() {

    if (this.autoritaControllanteSelected == undefined || !(this.autoritaControllanteSelected.descrizione == 'RdCA')) {
      if (!(this.autoritaControllanteSelected == undefined) && !(this.percRett == undefined) && this.selection.selected.length == 1) {
        let request: RettificaForfettariaDTO = {
          cigProcAgg: this.selection.selected[0].cigProcAgg,
          codProcAgg: this.selection.selected[0].codProcAgg,
          dataInserimento: this.datePipe.transform(this.myDate, 'yyyy-MM-dd'),
          denominazioneBeneficiario: this.user.beneficiarioSelezionato.denominazione,
          descCategAnagrafica: this.autoritaControllanteSelected.descrizione,
          esitoDefinitivo: this.selection.selected[0].esitoDefinitivo,
          esitoIntermedio: this.selection.selected[0].esitoIntermedio,
          idAppalto: this.selection.selected[0].idAppalto,
          idAppaltoChecklist: null,
          idCategAnagrafica: Number(this.autoritaControllanteSelected.codice),
          idDocumentoIndex: null,
          idEsitoDefinitivo: null,
          idEsitoIntermedio: null,
          idProceduraAggiudicaz: this.selection.selected[0].idProceduraAggiudicaz,
          idProgetto: Number(this.idProgetto),
          nomeFile: null,
          percRett: Number(this.percRett)
        };

        this.loadedsalvaRettificaForfettaria = true;
        this.subscribers.salvaRettificaForfettaria = this.registroControlliService.salvaRettificaForfettaria(request).subscribe((res: EsitoSalvaRettificaForfettariaDTO) => {
          this.loadedsalvaRettificaForfettaria = false;
          if (res.esito == true) {
            this.router.navigateByUrl(`drawer/` + this.idOperazione + `/controlli/` + this.idProgetto + "?postIns=true");
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di salvataggio rettifica forfettaria");
          this.loadedsalvaRettificaForfettaria = false;
        });
      } else {
        this.showMessageError("I seguenti campi sono obbligatori: Autorità controllante; Percentuale di rettifica. E' obbligatorio selezionare un affidamento.");
      }
    } else {
      if (this.idChecklistSelected == undefined) {
        this.showMessageError("E' obbligatorio selezionare una checklist!");
      } else {
        var comodoidAppaltoChecklist;
        var comodoidDocumentoIndex;
        var comodoidEsitoDefinitivo;
        var comodoidEsitoIntermedio;
        var comodonomeFile;


        this.dataSource.data.forEach(element => {
          element.checklist.forEach(element2 => {
            if (element2.idChecklist == this.idChecklistSelected) {
              comodoidAppaltoChecklist = element2.idAppaltoChecklist;
              comodoidDocumentoIndex = element2.idDocumentoIndex;
              comodoidEsitoDefinitivo = element2.idEsitoDefinitivo;
              comodoidEsitoIntermedio = element2.idEsitoIntermedio;
              comodonomeFile = element2.nomeFile;
            }
          });
        });

        if (!(this.autoritaControllanteSelected == undefined) && !(this.percRett == undefined) && this.selection.selected.length == 1) {
          let request: RettificaForfettariaDTO = {
            cigProcAgg: this.selection.selected[0].cigProcAgg,
            codProcAgg: this.selection.selected[0].codProcAgg,
            dataInserimento: this.datePipe.transform(this.myDate, 'yyyy-MM-dd'),
            denominazioneBeneficiario: this.user.beneficiarioSelezionato.denominazione,
            descCategAnagrafica: this.autoritaControllanteSelected.descrizione,
            esitoDefinitivo: this.selection.selected[0].esitoDefinitivo,
            esitoIntermedio: this.selection.selected[0].esitoIntermedio,
            idAppalto: this.selection.selected[0].idAppalto,
            idAppaltoChecklist: comodoidAppaltoChecklist,
            idCategAnagrafica: Number(this.autoritaControllanteSelected.codice),
            idDocumentoIndex: comodoidDocumentoIndex,
            idEsitoDefinitivo: comodoidEsitoDefinitivo,
            idEsitoIntermedio: comodoidEsitoIntermedio,
            idProceduraAggiudicaz: this.selection.selected[0].idProceduraAggiudicaz,
            idProgetto: Number(this.idProgetto),
            nomeFile: comodonomeFile,
            percRett: Number(this.percRett)
          };

          this.loadedsalvaRettificaForfettaria = true;
          this.subscribers.salvaRettificaForfettaria = this.registroControlliService.salvaRettificaForfettaria(request).subscribe((res: EsitoSalvaRettificaForfettariaDTO) => {
            this.loadedsalvaRettificaForfettaria = false;
            if (res.esito == true) {
              this.router.navigateByUrl(`drawer/` + this.idOperazione + `/controlli/` + this.idProgetto + "?postIns=true");
            }
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di salvataggio rettifica forfettaria");
            this.loadedsalvaRettificaForfettaria = false;
          });
        } else {
          this.showMessageError("I seguenti campi sono obbligatori: Autorità controllante; Percentuale di rettifica. E' obbligatorio selezionare un affidamento.");
        }
      }
    }
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  getEsitiDef(esito: string, flagRettifica: string) {
    if (esito == null) {
      return null;
    } else {
      let s = "Def.: ";
      switch (esito) {
        case "POSITIVO":
          s += "REGOLARE";
          break;
        case "NEGATIVO":
          s += "IRREGOLARE";
          break;
        default:
          break;
      }
      s += flagRettifica === "S" ? " CON RETTIFICA" : "";
      return s;
    }
  }

  getEsitiInt(esito: string, flagRettifica: string) {
    if (esito == null) {
      return null;
    } else {
      let s = "Int.: ";
      switch (esito) {
        case "POSITIVO":
          s += "REGOLARE";
          break;
        case "NEGATIVO":
          s += "IRREGOLARE";
          break;
        default:
          break;
      }
      s += flagRettifica === "S" ? " CON RETTIFICA" : "";
      return s;
    }
  }

  downloadChecklist(element: ChecklistNewDTO) {
    this.loadedgetDocumento = true;
    this.subscribers.getDocumento = this.registroControlliService2.getDocumento(element.idDocumentoIndex).subscribe(data => {
      this.loadedgetDocumento = false;
      if (data) {
        saveAs(data, element.nomeFile);
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del documento");
      this.loadedgetDocumento = false;
    })
  }

  isLoading() {
    if (this.loadedgetChecklistRettifiche || this.loadedpropostaAperta || this.loadedgetCategAnagrafica || this.loadedsalvaRettificaForfettaria || this.loadedgetDocumento) {
      return true;
    } else {
      return false;
    }
  }

  // Trasformazione array con scopo di mostrare più checklist in ogni riga della tabella degli affidamenti
  createNewArray(oldArray: Array<ChecklistRettificaForfettariaDTO>) {
    var newArray = new Array<AffidamentiNewDTO>();

    let ids = new Array<number>();
    oldArray.forEach(o => {
      if (!ids.includes(o.idAppalto)) {
        ids.push(o.idAppalto);
      }
    });

    for (let id of ids) {
      let appalti = oldArray.filter(o => o.idAppalto === id);
      let itemNewArray: AffidamentiNewDTO = {
        cigProcAgg: appalti[0].cigProcAgg,
        codProcAgg: appalti[0].codProcAgg,
        descTipologiaAppalto: appalti[0].descTipologiaAppalto,
        esitoDefinitivo: appalti.find(a => a.esitoDefinitivo !== null)?.esitoDefinitivo || null,
        esitoIntermedio: appalti.find(a => a.esitoIntermedio !== null)?.esitoIntermedio || null,
        flagRettificaDefinitivo: appalti.find(a => a.flagRettificaDefinitivo !== null)?.flagRettificaDefinitivo || null,
        flagRettificaIntermedio: appalti.find(a => a.flagRettificaIntermedio !== null)?.flagRettificaIntermedio || null,
        idAppalto: appalti[0].idAppalto,
        idProceduraAggiudicaz: appalti[0].idProceduraAggiudicaz,
        idStatoTipoDocIndex: appalti[0].idStatoTipoDocIndex,
        idTipologiaAppalto: appalti[0].idTipologiaAppalto,
        importo: appalti[0].importo,
        importoContratto: appalti[0].importoContratto,
        oggettoAppalto: appalti[0].oggettoAppalto,
        checklist: []
      };
      let checklists = new Array<ChecklistNewDTO>();
      for (let appalto of appalti) {
        if (appalto.idAppaltoChecklist) {
          let ck: ChecklistNewDTO = {
            idAppaltoChecklist: appalto.idAppaltoChecklist,
            idChecklist: appalto.idChecklist,
            idDocumentoIndex: appalto.idDocumentoIndex,
            idEsitoDefinitivo: appalto.idEsitoDefinitivo,
            idEsitoIntermedio: appalto.idEsitoIntermedio,
            nomeFile: appalto.nomeFile
          };
          checklists.push(ck);
        }
      }
      itemNewArray.checklist = checklists;
      newArray.push(itemNewArray);
    }
    return this.popolaEsiti(newArray, oldArray);
  }

  popolaEsiti(newArray: Array<AffidamentiNewDTO>, oldArray: Array<ChecklistRettificaForfettariaDTO>) {
    newArray.forEach(element => {
      element.esitoDefinitivo = null;
      element.esitoIntermedio = null;

      var trovato = false;

      for (let i = (oldArray.length - 1); i > -1; i--) {
        if (!trovato) {
          if (element.cigProcAgg == oldArray[i].cigProcAgg) {
            if (oldArray[i].esitoDefinitivo == null && oldArray[i].esitoIntermedio == null) {

            } else {
              trovato = true;
              element.esitoDefinitivo = oldArray[i].esitoDefinitivo;
              element.esitoIntermedio = oldArray[i].esitoIntermedio;
            }
          }
        }
      }
    });

    return newArray;
  }

  changeCheckBox(idChecklist: number, event: any) {
    if (event.checked) {
      this.idChecklistSelected = idChecklist;

      this.dataSource.data.forEach(element => {
        element.checklist.forEach(element2 => {
          if (element2.idChecklist == this.idChecklistSelected) {
            this.selection.clear();
            this.selection.select(element);
          }
        });
      });
    } else {
      this.idChecklistSelected = undefined;
    }
  }
}
