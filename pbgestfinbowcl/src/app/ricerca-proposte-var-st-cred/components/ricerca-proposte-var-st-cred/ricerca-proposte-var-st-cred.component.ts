/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { HandleExceptionService } from '@pbandi/common-lib';
import { of } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CercaPropostaVarazioneStatoCreditoSearchVO } from '../../commons/dto/prop-var-search-VO';
import { RicercaProposteVarStCredService } from '../../services/ricerca-proposte-var-st-cred.service';
import { DialogConfermaStatoPropostaVarCredComponent } from '../dialog-conferma-stato-proposta-var-cred/dialog-conferma-stato-proposta-var-cred.component';
import { MatSort, Sort } from '@angular/material/sort';
import { DataSource, SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { SharedService } from 'src/app/shared/services/shared.service';
import { OkDialogComponent } from 'src/app/iter-autorizzativi/components/iter-autorizzativi/ok-dialog/ok-dialog.component';
import * as XLSX from 'xlsx';
import { PropostaVarazioneStatoCreditoDTO } from '../../commons/dto/storico-proposta-var-st-cred-DTO';
import { FormGroup } from '@angular/forms';
import { Workbook } from 'exceljs';
import * as FileSaver from 'file-saver'
import { DialogPianoAmmortamento } from 'src/app/gestione-crediti/components/dialog-piano-ammortamento/dialog-piano-ammortamento.component';
import { DialogEstrattoConto } from 'src/app/gestione-crediti/components/dialog-estratto-conto/dialog-estratto-conto.component';


@Component({
  selector: 'app-ricerca-proposte-var-st-cred',
  templateUrl: './ricerca-proposte-var-st-cred.component.html',
  styleUrls: ['./ricerca-proposte-var-st-cred.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaProposteVarStCredComponent implements OnInit {
  @ViewChild("proposteSort") sort: MatSort;
  @ViewChild("propostePaginator") paginator: MatPaginator;


  subscribers: any = {};
  user: UserInfoSec;
  idUtente: number;
  userloaded: boolean;
  listaCF: Array<AttivitaDTO>;
  loadedCF: boolean;
  listaDenomin: Array<AttivitaDTO>;
  loadedDenom: boolean;
  listaPartiaIVA: Array<AttivitaDTO>;
  loadedPIVA: boolean;
  listaCodiceProgetto: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  listaCodiceFondoFinpis: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  loadedCodProg: boolean;
  listaTipoAgev: Array<AttivitaDTO>;
  loadedTipAgev: boolean
  listaBando: Array<AttivitaDTO>;
  loadedBando: boolean;
  listaStatoProposta: Array<AttivitaDTO>;
  loadedStProp: boolean;
  suggesttionnull: AttivitaDTO = new AttivitaDTO();
  codeiFiscAttDTO: AttivitaDTO = new AttivitaDTO();
  partitaIVADTO: AttivitaDTO = new AttivitaDTO();
  denomDTO: AttivitaDTO = new AttivitaDTO();
  agevDTO: AttivitaDTO = new AttivitaDTO();
  bandoDTO: AttivitaDTO = new AttivitaDTO();
  codProgDTO: AttivitaDTO = new AttivitaDTO();
  statoPropDTO: AttivitaDTO = new AttivitaDTO();
  criteriRicercaState: boolean = true;

  // Campi di Ricerca
  codFisIns: string;
  parIvaIns: string;
  denIns: string;
  desBanIns: string;
  idTipoAgevIns: number;
  codProIns: string;
  codiceFinpis: string;

  isMessageErrorVisible: boolean;
  propostaVO: CercaPropostaVarazioneStatoCreditoSearchVO = new CercaPropostaVarazioneStatoCreditoSearchVO();
  idStatoPropos: number;
  storicoProposte: Array<PropostaVarazioneStatoCreditoDTO> = new Array<PropostaVarazioneStatoCreditoDTO>();
  isStorico: boolean = false;
  storicoLoaded: boolean = true;
  results: boolean;
  dataSource: MatTableDataSource<PropostaVarazioneStatoCreditoDTO> = new MatTableDataSource<PropostaVarazioneStatoCreditoDTO>([]);
  
  isSalvato: boolean;
  isModificatoSuccess: boolean;
  isModificatoError: boolean;
  messageSuccess: string = "Stato modificato correttamente";
  messageError: string = "errore in faso di modifica dello stato";
  importo: number;
  importoFormatted: string;
  sortedData: PropostaVarazioneStatoCreditoDTO[];
  idBando: number;

displayedColumns:string[]=["select","nag","titoloBando","denominazione","partitaIva","codiceFiscale","descModalitaAgevolaz"
,"descStatoProposta",'flagAccettatoRifiutato',"dataProposta"/* ,"descStatoCredFin","descNuovoStatoCred" */,"statoCreditoAttuale","statoCreditoProposto"
,"descStatAzienda","percSconf","impSconfCapitale","impSconfInteressi","impSconfAgev","giorniSconf",/*"polliceSu","polliceGiu"*/ 'actions'];

elencoIter = new Array;
selection = new SelectionModel<any>(true, []);
errore;
message;
numeroSelezionati: number = 0;
excelFile: string = 'Proposte di variazione Stato Credito.xlsx';
myForm: FormGroup;

  listaStatiCredito: any;
  idStatoAttuale: any;
  idStatoCreditoProposto: any;
  percSconfinamentoDa: any;
  percSconfinamentoA: any;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ricPropostaService: RicercaProposteVarStCredService,
    private userService: UserService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    public dialog: MatDialog

  ) { }

  ngOnInit(): void {
    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
        this.loadListe();

      }
    });
  }

  loadListe() {
    this.loadedTipAgev = false;
    this.subscribers.agev = this.ricPropostaService.getListaAgev().subscribe(data => {
      if (data)
        this.listaTipoAgev = data;
      this.loadedTipAgev = true
    });
    this.loadedStProp = false;
    this.subscribers.staprop = this.ricPropostaService.getListaStatoProposta().subscribe(data => {
      if (data) {
        this.listaStatoProposta = data;
        this.loadedStProp = true;
      }
    });

    this.subscribers.staprop = this.ricPropostaService.getStatoCredito().subscribe(data => {
      if (data) {
        this.listaStatiCredito = data;
        console.log(data);
        
       // this.loadedStProp = true;
      }
    });
    this.search()
  }



  search() {
    this.isModificatoError = false;
    this.isModificatoSuccess = false;
    this.propostaVO = new CercaPropostaVarazioneStatoCreditoSearchVO();
    
   
    
    if (this.codFisIns != null && this.codFisIns.length > 0) {
      this.propostaVO.codiceFiscale = this.codFisIns;
      for (let attivita of this.listaCF) {
        if (attivita.descAttivita == this.codFisIns) {
          this.propostaVO.idSoggettoCF = attivita.idSoggetto;
        }
      }

    }

    if (this.parIvaIns != null && this.parIvaIns.length > 0) {
      this.propostaVO.partitaIVA = this.parIvaIns;
    }

    if (this.denIns != null && this.denIns.length > 0) {

      for (let denom of this.listaDenomin) {
        if (denom.descAttivita == this.denIns) {
          this.propostaVO.denominazione = denom.cognome;
          this.propostaVO.idSoggettoDenom = denom.idSoggetto;
        }
      }
      this.propostaVO.denominazione = this.denIns;
    }

    if (this.idTipoAgevIns != null && this.idTipoAgevIns.toString().length > 0) {
      this.propostaVO.idAgevolazione = this.idTipoAgevIns;
    }

    if (this.desBanIns != null && this.desBanIns.length > 0) {

      for (let bando of this.listaBando) {
        if (bando.descAttivita == this.desBanIns) {
          this.idBando = bando.idAttivita;
        }
      }
      this.propostaVO.titoloBando = this.desBanIns; 
      this.propostaVO.idBando = this.idBando;
    }

    if (this.idStatoPropos != null && this.idStatoPropos.toString().length > 0) {
      this.propostaVO.idStatoProposta = this.idStatoPropos;
    }
    if (this.codProIns != null && this.codProIns.length > 0) {
      this.propostaVO.codiceProgetto = this.codProIns;
    }

    if (this.idStatoAttuale != null && this.idStatoAttuale.toString().length > 0) {
      this.propostaVO.idStatoAttuale = this.idStatoAttuale;
    }


    if (this.idStatoCreditoProposto != null && this.idStatoCreditoProposto.toString().length > 0) {
      this.propostaVO.idStatoCreditoProposto = this.idStatoCreditoProposto;
    }

    if (this.percSconfinamentoDa != null ) {
      this.propostaVO.percSconfinamentoDa = this.percSconfinamentoDa;
    }

    if (this.percSconfinamentoA != null ) {
      this.propostaVO.percSconfinamentoA = this.percSconfinamentoA;
    }

    if (this.codiceFinpis != null && this.codiceFinpis.length > 0) {
      this.propostaVO.codiceFinpis = this.codiceFinpis;
    }

    this.cerca(this.propostaVO);


  }

  cerca(propostaVO: CercaPropostaVarazioneStatoCreditoSearchVO) {

    this.results = true;
    this.storicoLoaded = false
    this.subscribers.storico = this.ricPropostaService.getElencoProposte(propostaVO).subscribe(data => {
      if (data) {
        this.storicoLoaded = true;
        this.dataSource = new MatTableDataSource<PropostaVarazioneStatoCreditoDTO>(data);
        this.paginator.length = data.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        this.storicoLoaded = true;
        this.isStorico = false;
      }
      
      data?.length > 0 ? this.isStorico = true : this.isStorico = false;
    }, err => {

      this.showMessageError("Errore in fase di ricerca.")
      this.handleExceptionService.handleNotBlockingError(err);
    });
    this.criteriRicercaState = false;

  }

  suggest(id: number, value: string) {
    this.isModificatoError = false;
    this.isModificatoSuccess = false;
    switch (id) {
      case 1:
        if (value.length >= 3) {
          this.listaCF = new Array<AttivitaDTO>();
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaCF.push(this.suggesttionnull);
          this.subscribers.listaCF = this.ricPropostaService.getListaSuggest(id, value).subscribe(data => {
            if (data.length > 0) {
              this.listaCF = data;
            } else {
              this.listaCF = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaCF.push(this.suggesttionnull);
            }
          });
        }
        break;

      case 2:
        this.listaPartiaIVA = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaPartiaIVA.push(this.suggesttionnull);
          this.subscribers.listaPartiaIVA = this.ricPropostaService.getListaSuggest(id, value).subscribe(data => {
            if (data.length > 0) {
              this.listaPartiaIVA = data;
            } else {
              this.listaPartiaIVA = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaPartiaIVA.push(this.suggesttionnull);
            }
          });
        }
        break;

      case 3:
        this.listaDenomin = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaDenomin.push(this.suggesttionnull);
          this.subscribers.listaDenomin = this.ricPropostaService.getListaSuggest(id, value).subscribe(data => {
            if (data.length > 0) {
              this.listaDenomin = data;
            } else {
              this.listaDenomin = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaDenomin.push(this.suggesttionnull);
            }
          });
        }
        break;

      case 4:
        this.listaBando = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaBando.push(this.suggesttionnull);
          this.subscribers.listaBando = this.ricPropostaService.getListaSuggest(id, value).subscribe(data => {
            if (data.length > 0) {
              this.listaBando = data;
            } else {
              this.listaBando = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaBando.push(this.suggesttionnull);
            }
          });
        }
        break;

      case 5:
        this.listaCodiceProgetto = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaCodiceProgetto.push(this.suggesttionnull);
          this.subscribers.listaCodiceProgetto = this.ricPropostaService.getListaSuggest(id, value).subscribe(data => {
            if (data.length > 0) {
              this.listaCodiceProgetto = data;
            } else {
              this.listaCodiceProgetto = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaCodiceProgetto.push(this.suggesttionnull);
            }
          });
        }
        break;

      case 9:
        this.listaCodiceFondoFinpis = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaCodiceFondoFinpis.push(this.suggesttionnull);
          this.subscribers.listaCodiceFondoFinpis = this.ricPropostaService.getListaSuggest(id, value).subscribe(data => {
            if (data.length > 0) {
              this.listaCodiceFondoFinpis = data;
            } else {
              this.listaCodiceFondoFinpis = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaCodiceFondoFinpis.push(this.suggesttionnull);
            }
          });
        }
        break;

      default:
        break;
    }
  }


  resetCriteriRicerca() {

    this.codFisIns = "";
    this.parIvaIns = "";
    this.denIns = "";
    this.idTipoAgevIns = null;
    this.desBanIns = "";
    this.codProIns = "";
    this.idStatoPropos = null;
    this.idStatoAttuale = null;
    this.idStatoCreditoProposto = null;
    this.percSconfinamentoDa = "";
    this.percSconfinamentoA = "";

    this.loadListe();
  }

  openDialog(element: PropostaVarazioneStatoCreditoDTO, tipoConferma: boolean) {
    let dialogRef = this.dialog.open(DialogConfermaStatoPropostaVarCredComponent, {
      width: '40%',
      //panelClass: 'marty-class',
      data: {
        element: element,
        tipoConferma: tipoConferma
      }
    });
    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        
        this.isSalvato = true;
        this.isModificatoSuccess = true;
        setTimeout(() => {
          this.isModificatoSuccess = null;
          this.search();
        }, 3000); 
      }
    });
  }

  


  isLoading() {
    if (!this.userloaded || !this.loadedStProp || !this.loadedTipAgev || !this.storicoLoaded) {
      return true;
    }
    return false;
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
  criteriRicercaOpenClose() {
    this.criteriRicercaState = !this.criteriRicercaState;
  }



  




  setImporto(importo: string) {
    this.importo = this.sharedService.getNumberFromFormattedString(importo);
    if (this.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }

  }


   isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data?.filter(d => d.confirmable != false ).length;
    return numSelected === numRows;
  }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      this.numeroSelezionati = this.selection.selected.length;
      return;
    }
  this.selection.select(...this.dataSource.data?.filter(d => d.confirmable != false ));
  this.numeroSelezionati = this.selection.selected.length;
  }

  checkboxLabel(row?: any): string {
    if (!row) {
      
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }this.numeroSelezionati = this.selection.selected.length;
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
    
  }

 autorizzaSelezionati(){
    if (this.selection.selected.length <= 0) {
      const confirmDialog = this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Nessun iter selezionato',
          message: 'si prega di selezionare almeno un elemento',
          conferma : false
        },
      });
    } else {
      let prova  = this.selection.selected.map(p =>p.idVariazStatoCredito);
      console.log(prova);
      const dialogRef = this.dialog.open(OkDialogComponent, {
        height: 'auto',
        width: '50rem',
        data: {
          title: 'Autorizza',
          message: 'Sei sicuro di voler autorizzare gli elementi selezionati?',
        },
      });
      dialogRef.afterClosed().subscribe((result) => {
        if(result === true){
          let datiBackEnd  = this.selection.selected.map(p =>p.idVariazStatoCredito);
          let flagConferma = 'S';
       this.ricPropostaService.rifiutaAccettaMassiva(datiBackEnd,flagConferma).subscribe((data) => {
          if (data == true) {
            console.log(data);
            
            this.message = "Elementi selezionati accettati con successo";
            this.errore = false;
            setTimeout(() => {
              this.errore = null;
              this.selection.clear();
                  this.search();
            }, 4000); 
          }
          else if (data == false) {
            this.errore = true;
            this.message = "Errore non è stato possibile accettare gli elementi selezionati";
            setTimeout(() => {
              this.errore = null;
              this.selection.clear();
            }, 4000); 
          }
        },
          (err) => {
              this.errore = true;
              this.message = err.statusText;
          }
        );  
        }else return;
      
      }); 
    } 
    
  } 


  respingiSelezionati(){
    if (this.selection.selected.length <= 0) {
      const confirmDialog = this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Nessun iter selezionato',
          message: 'si prega di selezionare almeno un elemento',
          conferma : false
        },
      });
    } else {
      this.selection.selected;
      const dialogRef = this.dialog.open(OkDialogComponent, {
        height: 'auto',
        width: '50rem',
        data: {
          title: 'Respingi',
          message: 'Sei sicuro di voler respingere gli elementi selezionati?',
        },
      });
      dialogRef.afterClosed().subscribe((result) => {
        if(result === true){
          let datiBackEnd  = this.selection.selected.map(p =>p.idVariazStatoCredito);
          let flagConferma = 'N';
      this.ricPropostaService.rifiutaAccettaMassiva(datiBackEnd,flagConferma).subscribe((data) => {
          if (data == true) {
            this.message = "Elementi selezionati riufiutati con successo";
            this.errore = false;
            setTimeout(() => {
              this.errore = null;
              this.selection.clear();
                  this.search();
            }, 4000); 
          }
          else if (data == false) {
            this.errore = true;
            this.message = "Errore non è stato possibile rifiutare gli elementi selezionati";
            setTimeout(() => {
              this.errore = null;
              this.selection.clear();
            }, 4000); 
          }
        },
          (err) => {
              this.errore = true;
              this.message = err.statusText;
          }
        ); 
        }else return;
      
      }); 
    } 
    
  }

  apriEstrattoConto(row: PropostaVarazioneStatoCreditoDTO) {
    this.dialog.open(DialogEstrattoConto, {
      minWidth: '40%',
      //maxWidth: '77%',
      data: {
        componentFrom: 1,
        bando: row.titoloBando,
        codProgetto: row.codiceVisualizzatoProgetto,
        beneficiario: row.denominazione,
        debitoResiduo: 0,
        idProgetto: row.idProgetto,
        ndg: row.ndg,
        idModalitaAgevolazione: row.idModalitaAgevolazione, 
        idModalitaAgevolazioneRif:  row.idModalitaAgevolazioneRif,
       }
    });
  }

  apriPianoAmmortamento(row: PropostaVarazioneStatoCreditoDTO) {
    this.dialog.open(DialogPianoAmmortamento, {
      minWidth: '40%',
      //height: '60%',
      data: {
        componentFrom: 1,
        bando: row.titoloBando,
        codProgetto: row.codiceVisualizzatoProgetto,
        beneficiario: row.denominazione,
        debitoResiduo: 0,
        idProgetto: row.idProgetto,
        ndg: row.ndg,
        idModalitaAgevolazione: row.idModalitaAgevolazione, 
        idModalitaAgevolazioneRif:  row.idModalitaAgevolazioneRif,
       }
    });
  }

  scaricaExcel(){

    let nomeFile: string = "Proposte variazione Stato Credito";

    /*  OLD
    let element = document.getElementById('excel');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element,{dateNF:'dd/mm/yyyy',cellDates:true, raw: true});
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Excel');
    XLSX.writeFile(wb, this.excelFile);*/

    const header = ['Progetto', 'Bando', 'Beneficiario', 'Partita IVA', 'Codice Fiscale', 'Tipo agevolazione', 'Stato proposta','Azione Effettuata', 'Data proposta', 'Stato credito attuale', 'Stato credito proposto', 'Stato azienda', 'Percentuale sconfinamento', 'Sconfinamento capitale', 'Sconfinamento interessi', 'Sconfinamento agevolazione', 'Giorni sconfinamento'];
    
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Proposte variazione');
    
    // Add Header Row
    const headerRow = worksheet.addRow(header);
    // Cell Style : Fill and Border
    headerRow.eachCell((cell, number) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'CECECE' },
      };
      cell.border = { top: { style: 'thin' }, left: { style: 'thin' }, bottom: { style: 'thin' }, right: { style: 'thin' } };
    });

    const data = new Array<any>();
    this.dataSource.data.map(p => {
      data.push([p.nag, p.titoloBando, p.denominazione, p.partitaIva, p.codiceFiscale, p.descModalitaAgevolaz, p.descStatoProposta, (p.flagAccettatoRifiutato!=null)? p.flagAccettatoRifiutato:'-',
      p.dataProposta, p.statoCreditoAttuale, p.statoCreditoProposto, p.descStatAzienda, p.percSconf, p.impSconfCapitale, p.impSconfInteressi, p.impSconfAgev, p.giorniSconf]);
    })
    worksheet.addRows(data);

    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      FileSaver.saveAs(blob, nomeFile + '.xlsx');
    });
  }

  toDateForExcel(unixTimestamp: Date): string {
    if (unixTimestamp) {
      const date = new Date(unixTimestamp);
      const day = date.getDate();
      const month = date.getMonth() + 1;
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;
    } else {
      return null
    }

  }


}


