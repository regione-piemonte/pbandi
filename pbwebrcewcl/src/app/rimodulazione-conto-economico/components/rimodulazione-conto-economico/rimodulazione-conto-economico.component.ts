/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ContoEconomicoItem } from '../../commons/dto/conto-economico-item';
import { EsitoFindContoEconomicoDTO } from '../../commons/dto/esito-find-conto-economico-dto';
import { SalvaPropostaRimodulazioneRequest, SalvaRimodulazioneRequest } from '../../commons/request/salva-rimodulazione-request';
import { RimodulazioneContoEconomicoService } from '../../services/rimodulazione-conto-economico.service';
import { saveAs } from 'file-saver-es';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { AltriCostiDTO } from '../../commons/dto/altri-costi-dto';

@Component({
  selector: 'app-rimodulazione-conto-economico',
  templateUrl: './rimodulazione-conto-economico.component.html',
  styleUrls: ['./rimodulazione-conto-economico.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RimodulazioneContoEconomicoComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idBando: number;
  idContoEconomico: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isProposta: boolean = false;
  isIstruttoria: boolean = false;
  isRichiesta: boolean = false;
  righeContoEconomico: Array<ContoEconomicoItem>;
  esitoFindContoEconomicoDTO: EsitoFindContoEconomicoDTO;
  altriCosti: Array<AltriCostiDTO>;
  sommaEconomieUtilizzate: number;
  progettoRicevente: boolean;
  isRimodula: boolean;
  isSavedRimod: boolean;
  isNuovaProposta: boolean;
  isSavedProposta: boolean;
  isConfirmedProposta: boolean;
  isConfirmedRimodIstr: boolean;
  isCreaRichiesta: boolean;
  isSavedRichiesta: boolean;
  isConfirmedRichiesta: boolean;
  isRipristinaRichiestoInDomanda: boolean;
  isRipristinaSpesaAmmessaIstruttoria: boolean;
  isRipristinaUltimaProposta: boolean;
  isRipristinaSpesaAmmessaUltima: boolean;
  isBR59: boolean;

  displayedColumnsRimodProp: string[] = ['vociSpesa', 'richiesto', 'ultimaProposta', 'richiestoNuovaProposta', 'spesaIstruttoria', 'spesaAmmessa', 'spesaAmmessaRimod', 'spesaRendicontata', 'spesaQuietanzata', 'qa', 'spesaValidata', 'via'];
  displayedColumnsIstr: string[] = ['vociSpesa', 'richiesto'];
  displayedColumns: string[] = [];
  dataSource: MatTableDataSource<ContoEconomicoItem> = new MatTableDataSource<ContoEconomicoItem>([]);

  displayedColumnsAltriCosti: string[] = ['vociCosto', 'importoCeApprovato', 'importoCeProposto'];
  dataSourceAltriCosti: MatTableDataSource<AltriCostiDTO> = new MatTableDataSource<AltriCostiDTO>([]);

  @ViewChild('tableForm', { static: true }) tableForm: NgForm;
  @ViewChild('tableAltriCostiForm', { static: true }) tableAltriCostiForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedBR59: boolean = true;
  loadedChiudiAttivita: boolean = true;
  loadedNuovaProposta: boolean = true;
  loadedSalva: boolean = true;
  loadedDownload: boolean = true;
  loadedAltriCosti: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private sharedService: SharedService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private rimodulazioneContoEconomicoService: RimodulazioneContoEconomicoService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];

      this.subscribers.router = this.activatedRoute.params.subscribe(params => {
        this.idProgetto = +params['idProgetto'];
        this.idBando = +params['idBando'];
        this.idContoEconomico = params['idContoEconomico'] ? +params['idContoEconomico'] : null;
        if (this.idContoEconomico !== null) {
          if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA) {
            this.showMessageSuccess("La richiesta del conto economico in domanda è stata inviata correttamente.");
          } else {
            this.showMessageSuccess("La rimodulazione del conto economico è stata conclusa con successo.");
          }
        }
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
          if (data) {
            this.user = data;
            this.loadData();
          }
        });
      });
    });
  }

  loadData() {
    if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO) {
      this.isProposta = true;
      this.displayedColumns = this.displayedColumnsRimodProp;
    } else if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA) {
      this.isIstruttoria = true;
      this.displayedColumns = this.displayedColumnsIstr;
    } else if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA) {
      this.isRichiesta = true;
      this.displayedColumns = this.displayedColumnsIstr;
    } else {
      this.displayedColumns = this.displayedColumnsRimodProp;
    }
    if (this.isProposta) {
      this.loadedInizializza = false;
      this.subscribers.codice = this.rimodulazioneContoEconomicoService.inizializzaPropostaRimodulazione(this.idProgetto).subscribe(data => {
        if (data) {
          this.codiceProgetto = data.codiceVisualizzatoProgetto;
          this.esitoFindContoEconomicoDTO = data.esitoFindContoEconomicoDTO;
          this.righeContoEconomico = data.righeContoEconomico;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (!this.esitoFindContoEconomicoDTO.copiaModificataPresente) {
            this.displayedColumns = this.displayedColumns.filter(d => d !== "richiestoNuovaProposta");
          } else {
            this.isSavedProposta = true;
            this.isConfirmedProposta = true;
          }
          this.displayedColumns = this.displayedColumns.filter(d => d !== "spesaAmmessaRimod");
          if (!this.esitoFindContoEconomicoDTO.dataUltimaProposta) {
            this.displayedColumns = this.displayedColumns.filter(d => d !== "ultimaProposta");
          }
          if (!this.esitoFindContoEconomicoDTO.dataUltimaRimodulazione) {
            this.displayedColumns = this.displayedColumns.filter(d => d !== "spesaAmmessa");
          }
          this.calcolaTotaliNuovaProposta();
          this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        }
        this.loadedInizializza = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    } else if (this.isIstruttoria) {
      this.loadedInizializza = false;
      this.subscribers.codice = this.rimodulazioneContoEconomicoService.inizializzaRimodulazioneIstruttoria(this.idProgetto).subscribe(data => {
        if (data) {
          this.codiceProgetto = data.codiceVisualizzatoProgetto;
          this.esitoFindContoEconomicoDTO = data.esitoFindContoEconomicoDTO;
          this.righeContoEconomico = data.righeContoEconomico;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (this.esitoFindContoEconomicoDTO.copiaModificataPresente) {
            this.displayedColumns.splice(2, 0, "spesaAmmessaRimod");
            this.isSavedRimod = true;
            if (this.isIstruttoria) {
              this.isConfirmedRimodIstr = true;
            }
          }
          this.calcolaTotaliSpesaAmmessaRimodulazione();
          this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        }
        this.loadedInizializza = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    } else if (this.isRichiesta) {
      this.loadedInizializza = false;
      this.subscribers.codice = this.rimodulazioneContoEconomicoService.inizializzaPropostaRimodulazioneInDomanda(this.idProgetto).subscribe(data => {
        if (data) {
          this.codiceProgetto = data.codiceVisualizzatoProgetto;
          this.esitoFindContoEconomicoDTO = data.esitoFindContoEconomicoDTO;
          this.righeContoEconomico = data.righeContoEconomico;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (!this.esitoFindContoEconomicoDTO.isContoMainNew && !this.esitoFindContoEconomicoDTO.inStatoRichiesto) {
            this.isSavedRichiesta = true;
            this.isConfirmedRichiesta = true;
          }
          this.calcolaTotaliRichiesto();
          this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        }
        this.loadedInizializza = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    } else {
      this.loadedInizializza = false;
      this.subscribers.codice = this.rimodulazioneContoEconomicoService.inizializzaRimodulazione(this.idProgetto).subscribe(data => {
        if (data) {
          this.codiceProgetto = data.codiceVisualizzatoProgetto;
          this.esitoFindContoEconomicoDTO = data.esitoFindContoEconomicoDTO;
          this.sommaEconomieUtilizzate = data.sommaEconomieUtilizzate;
          this.progettoRicevente = data.progettoRicevente;
          this.righeContoEconomico = data.righeContoEconomico;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (!this.esitoFindContoEconomicoDTO.copiaModificataPresente) {
            this.displayedColumns = this.displayedColumns.filter(d => d !== "spesaAmmessaRimod");
          } else {
            this.isSavedRimod = true;
          }
          if (!this.esitoFindContoEconomicoDTO.dataUltimaProposta) {
            this.displayedColumns = this.displayedColumns.filter(d => d !== "ultimaProposta");
          }
          if (!this.esitoFindContoEconomicoDTO.dataUltimaRimodulazione) {
            this.displayedColumns = this.displayedColumns.filter(d => d !== "spesaAmmessa");
          }
          this.displayedColumns = this.displayedColumns.filter(d => d !== "richiestoNuovaProposta");
          this.calcolaTotaliSpesaAmmessaRimodulazione();
          this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        }
        this.loadedInizializza = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    }

    if (!this.isRichiesta) {
      //proposta, rimodulazione, istruttoria
      this.loadedBR59 = false;
      this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR59").subscribe(data1 => {
        this.isBR59 = data1;
        if (this.isBR59) {
          this.loadedAltriCosti = false;
          this.rimodulazioneContoEconomicoService.getAltriCosti(this.idBando, this.idProgetto).subscribe(data2 => {
            this.altriCosti = data2;
            if (this.altriCosti?.length) {
              for (let item of this.altriCosti) {
                if (item.impCeApprovato !== null && item.impCeApprovato !== undefined) {
                  item.impCeApprovatoFormatted = this.sharedService.formatValue(item.impCeApprovato.toString());
                }
                if (item.impCePropmod !== null && item.impCePropmod !== undefined) {
                  item.impCePropmodFormatted = this.sharedService.formatValue(item.impCePropmod.toString());
                }
              }
            }
            this.dataSourceAltriCosti.data = data2;
            this.loadedAltriCosti = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di lettura delle voci di costo.");
            this.loadedAltriCosti = true;
          });
        }
        this.loadedBR59 = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di lettura della regola BR59.");
        this.loadedBR59 = true;
      });
    }
  }

  openCloseVoce(id: string) {
    let righe = this.righeContoEconomico.filter(r => r.idPadre === id);
    let padre = this.righeContoEconomico.find(r => r.id === id);
    if (righe && !padre.figliVisible) {
      righe.forEach(r => { r.visible = true });
      padre.figliVisible = true;
    } else if (righe && padre.figliVisible) {
      righe.forEach(r => { r.visible = false });
      padre.figliVisible = false;
    }
  }

  formatRichiestoNuovaProposta(item: ContoEconomicoItem) {
    if (item.importoRichiestoNuovaProposta) {
      let imp: number = this.sharedService.getNumberFromFormattedString(item.importoRichiestoNuovaProposta);
      if (imp !== null) {
        item.importoRichiestoNuovaProposta = this.sharedService.formatValue(imp.toString());
      }
    }
  }

  formatSpesaAmmessaRimodulazione(item: ContoEconomicoItem) {
    if (item.importoSpesaAmmessaRimodulazione) {
      let imp: number = this.sharedService.getNumberFromFormattedString(item.importoSpesaAmmessaRimodulazione);
      if (imp !== null) {
        item.importoSpesaAmmessaRimodulazione = this.sharedService.formatValue(imp.toString());
      }
    }
  }

  formatRichiesto(item: ContoEconomicoItem) {
    if (item.importoRichiestoInDomanda) {
      let imp: number = this.sharedService.getNumberFromFormattedString(item.importoRichiestoInDomanda);
      if (imp !== null) {
        item.importoRichiestoInDomanda = this.sharedService.formatValue(imp.toString());
      }
    }
  }

  formatImportoCeApprovato(item: AltriCostiDTO) {
    if (item.impCeApprovatoFormatted) {
      item.impCeApprovato = this.sharedService.getNumberFromFormattedString(item.impCeApprovatoFormatted);
      if (item.impCeApprovato !== null) {
        item.impCeApprovatoFormatted = this.sharedService.formatValue(item.impCeApprovato.toString());
      }
    }
  }

  formatImportoCeProposto(item: AltriCostiDTO) {
    if (item.impCePropmodFormatted) {
      item.impCePropmod = this.sharedService.getNumberFromFormattedString(item.impCePropmodFormatted);
      if (item.impCePropmod !== null) {
        item.impCePropmodFormatted = this.sharedService.formatValue(item.impCePropmod.toString());
      }
    }
  }

  modelChangeRichiestoNuovaProposta(inputValue: string, id: string, index: number) {
    if (!this.tableForm.form.get('importoRichiestoNuovaProposta' + index).hasError('pattern')) {
      this.calcolaTotaliNuovaProposta(id, this.sharedService.getNumberFromFormattedString(inputValue));
    }
  }

  modelChangeSpesaAmmessaRimodulazione(inputValue: string, id: string, index: number) {
    if (!this.tableForm.form.get('importoSpesaAmmessaRimodulazione' + index).hasError('pattern')) {
      this.calcolaTotaliSpesaAmmessaRimodulazione(id, this.sharedService.getNumberFromFormattedString(inputValue));
    }
  }

  modelChangeRichiesto(inputValue: string, id: string, index: number) {
    if (!this.tableForm.form.get('importoRichiestoInDomanda' + index).hasError('pattern')) {
      this.calcolaTotaliRichiesto(id, this.sharedService.getNumberFromFormattedString(inputValue));
    }
  }

  calcolaTotaliNuovaProposta(id?: string, importoNuovo?: number) {
    let padri = this.righeContoEconomico.filter(r => r.level === 1);
    let conto = this.righeContoEconomico.find(r => r.level === 0 && r.haFigli);
    let importoRichiestoNuovaPropostaConto = this.sharedService.getNumberFromFormattedString(conto.importoRichiestoNuovaProposta);
    conto.perc = 0;
    for (let padre of padri) {
      importoRichiestoNuovaPropostaConto -= this.sharedService.getNumberFromFormattedString(padre.importoRichiestoNuovaProposta);
      if (padre.haFigli) {
        let importoRichiestoNuovaPropostaPadre = 0;
        for (let riga of this.righeContoEconomico.filter(r => r.idPadre === padre.id)) {
          //somma importi dei figli
          if (id && riga.id === id) {
            riga.importoRichiestoNuovaProposta = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
            importoRichiestoNuovaPropostaPadre += importoNuovo ? importoNuovo : 0;
          } else {
            riga.importoRichiestoNuovaProposta = riga.importoRichiestoNuovaProposta ? riga.importoRichiestoNuovaProposta : "0,00";
            importoRichiestoNuovaPropostaPadre += riga.importoRichiestoNuovaProposta ? this.sharedService.getNumberFromFormattedString(riga.importoRichiestoNuovaProposta) : 0;
          }
        }
        importoRichiestoNuovaPropostaConto += importoRichiestoNuovaPropostaPadre;
        padre.importoRichiestoNuovaProposta = this.sharedService.formatValue(importoRichiestoNuovaPropostaPadre.toString());
      } else {
        if (id && padre.id === id) {
          padre.importoRichiestoNuovaProposta = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
          importoRichiestoNuovaPropostaConto += importoNuovo ? importoNuovo : 0;
        } else {
          padre.importoRichiestoNuovaProposta = padre.importoRichiestoNuovaProposta ? padre.importoRichiestoNuovaProposta : "0,00";
          importoRichiestoNuovaPropostaConto += padre.importoRichiestoNuovaProposta ? this.sharedService.getNumberFromFormattedString(padre.importoRichiestoNuovaProposta) : 0;
        }
      }
    }
    conto.importoRichiestoNuovaProposta = this.sharedService.formatValue(importoRichiestoNuovaPropostaConto.toString());
    for (let padre of padri) {
      if (padre.haFigli) {
        let figli = this.righeContoEconomico.filter(r => r.idPadre === padre.id);
        for (let figlio of figli) {
          //percentuale dei figli
          figlio.perc = this.sharedService.getNumberFromFormattedString(figlio.importoRichiestoNuovaProposta) * 100 / importoRichiestoNuovaPropostaConto;
          if (!figlio.perc) {
            figlio.perc = 0;
          }
        }
        //percentuale padre
        padre.perc = 0;
        figli.forEach(f => {
          padre.perc += f.perc;
        });
      } else {
        padre.perc = this.sharedService.getNumberFromFormattedString(padre.importoRichiestoNuovaProposta) * 100 / importoRichiestoNuovaPropostaConto;
      }
      //percentuale conto
      conto.perc += padre.perc;
    }
    if (this.isNuovaProposta) {
      let rigaDifferenza = this.righeContoEconomico.find(r => r.level === null);
      let importoDifferenza = 0;
      importoDifferenza = importoRichiestoNuovaPropostaConto - this.sharedService.getNumberFromFormattedString(conto.importoSpesaAmmessaUltima);
      rigaDifferenza.importoRichiestoNuovaProposta = this.sharedService.formatValue(importoDifferenza.toString());
      rigaDifferenza.perc = importoDifferenza * 100 / importoRichiestoNuovaPropostaConto;
    }
  }

  calcolaTotaliSpesaAmmessaRimodulazione(id?: string, importoNuovo?: number) {
    let padri = this.righeContoEconomico.filter(r => r.level === 1);
    let conto = this.righeContoEconomico.find(r => r.level === 0 && r.haFigli);
    let importoSpesaAmmessaRimodulazioneConto = this.sharedService.getNumberFromFormattedString(conto.importoSpesaAmmessaRimodulazione);
    conto.perc = 0;
    for (let padre of padri) {
      importoSpesaAmmessaRimodulazioneConto -= this.sharedService.getNumberFromFormattedString(padre.importoSpesaAmmessaRimodulazione);
      if (padre.haFigli) {
        let importoSpesaAmmessaRimodulazionePadre = 0;
        for (let riga of this.righeContoEconomico.filter(r => r.idPadre === padre.id)) {
          //somma importi dei figli
          if (id && riga.id === id) {
            riga.importoSpesaAmmessaRimodulazione = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
            importoSpesaAmmessaRimodulazionePadre += importoNuovo ? importoNuovo : 0;
          } else {
            riga.importoSpesaAmmessaRimodulazione = riga.importoSpesaAmmessaRimodulazione ? riga.importoSpesaAmmessaRimodulazione : "0,00";
            importoSpesaAmmessaRimodulazionePadre += riga.importoSpesaAmmessaRimodulazione ? this.sharedService.getNumberFromFormattedString(riga.importoSpesaAmmessaRimodulazione) : 0;
          }
        }
        importoSpesaAmmessaRimodulazioneConto += importoSpesaAmmessaRimodulazionePadre;
        padre.importoSpesaAmmessaRimodulazione = this.sharedService.formatValue(importoSpesaAmmessaRimodulazionePadre.toString());
      } else {
        if (id && padre.id === id) {
          padre.importoSpesaAmmessaRimodulazione = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
          importoSpesaAmmessaRimodulazioneConto += importoNuovo ? importoNuovo : 0;
        } else {
          padre.importoSpesaAmmessaRimodulazione = padre.importoSpesaAmmessaRimodulazione ? padre.importoSpesaAmmessaRimodulazione : "0,00";
          importoSpesaAmmessaRimodulazioneConto += padre.importoSpesaAmmessaRimodulazione ? this.sharedService.getNumberFromFormattedString(padre.importoSpesaAmmessaRimodulazione) : 0;
        }
      }
    }
    conto.importoSpesaAmmessaRimodulazione = this.sharedService.formatValue(importoSpesaAmmessaRimodulazioneConto.toString());
    for (let padre of padri) {
      if (padre.haFigli) {
        let figli = this.righeContoEconomico.filter(r => r.idPadre === padre.id);
        for (let figlio of figli) {
          //percentuale dei figli
          figlio.perc = this.sharedService.getNumberFromFormattedString(figlio.importoSpesaAmmessaRimodulazione) * 100 / importoSpesaAmmessaRimodulazioneConto;
          if (!figlio.perc) {
            figlio.perc = 0;
          }
        }
        //percentuale padre
        padre.perc = 0;
        figli.forEach(f => {
          padre.perc += f.perc;
        });
      } else {
        padre.perc = this.sharedService.getNumberFromFormattedString(padre.importoSpesaAmmessaRimodulazione) * 100 / importoSpesaAmmessaRimodulazioneConto;
      }
      //percentuale conto
      conto.perc += padre.perc;
    }
    if (this.isRimodula && !this.isIstruttoria) {
      let rigaDifferenza = this.righeContoEconomico.find(r => r.level === null);
      let importoDifferenza = 0;
      importoDifferenza = importoSpesaAmmessaRimodulazioneConto - this.sharedService.getNumberFromFormattedString(conto.importoSpesaAmmessaUltima);
      rigaDifferenza.importoSpesaAmmessaRimodulazione = this.sharedService.formatValue(importoDifferenza.toString());
      rigaDifferenza.perc = importoDifferenza * 100 / importoSpesaAmmessaRimodulazioneConto;
    }
  }

  calcolaTotaliRichiesto(id?: string, importoNuovo?: number) {
    let padri = this.righeContoEconomico.filter(r => r.level === 1);
    let conto = this.righeContoEconomico.find(r => r.level === 0 && r.haFigli);
    let importoRichiestoConto = this.sharedService.getNumberFromFormattedString(conto.importoRichiestoInDomanda);
    conto.perc = 0;
    for (let padre of padri) {
      importoRichiestoConto -= this.sharedService.getNumberFromFormattedString(padre.importoRichiestoInDomanda);
      if (padre.haFigli) {
        let importoRichiestoPadre = 0;
        for (let riga of this.righeContoEconomico.filter(r => r.idPadre === padre.id)) {
          //somma importi dei figli
          if (id && riga.id === id) {
            riga.importoRichiestoInDomanda = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
            importoRichiestoPadre += importoNuovo ? importoNuovo : 0;
          } else {
            riga.importoRichiestoInDomanda = riga.importoRichiestoInDomanda ? riga.importoRichiestoInDomanda : "0,00";
            importoRichiestoPadre += riga.importoRichiestoInDomanda ? this.sharedService.getNumberFromFormattedString(riga.importoRichiestoInDomanda) : 0;
          }
        }
        importoRichiestoConto += importoRichiestoPadre;
        padre.importoRichiestoInDomanda = this.sharedService.formatValue(importoRichiestoPadre.toString());
      } else {
        if (id && padre.id === id) {
          padre.importoRichiestoInDomanda = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
          importoRichiestoConto += importoNuovo ? importoNuovo : 0;
        } else {
          padre.importoRichiestoInDomanda = padre.importoRichiestoInDomanda ? padre.importoRichiestoInDomanda : "0,00";
          importoRichiestoConto += padre.importoRichiestoInDomanda ? this.sharedService.getNumberFromFormattedString(padre.importoRichiestoInDomanda) : 0;
        }
      }
    }
    conto.importoRichiestoInDomanda = this.sharedService.formatValue(importoRichiestoConto.toString());
  }

  changeRichiestoNuovaProposta(item: ContoEconomicoItem, index: number) {
    if (!this.tableForm.form.get('importoRichiestoNuovaProposta' + index).hasError('pattern')) {
      this.formatRichiestoNuovaProposta(item);
      this.calcolaTotaliNuovaProposta();
    }
  }

  changeSpesaAmmessaRimodulazione(item: ContoEconomicoItem, index: number) {
    if (!this.tableForm.form.get('importoSpesaAmmessaRimodulazione' + index).hasError('pattern')) {
      this.formatSpesaAmmessaRimodulazione(item);
      this.calcolaTotaliSpesaAmmessaRimodulazione();
    }
  }

  changeRichiesto(item: ContoEconomicoItem, index: number) {
    if (!this.tableForm.form.get('importoRichiestoInDomanda' + index).hasError('pattern')) {
      this.formatRichiesto(item);
      this.calcolaTotaliRichiesto();
    }
  }

  changeImportoCeApprovato(item: any, index: number) {
    if (!this.tableAltriCostiForm.form.get('importoCeApprovato' + index).hasError('pattern')) {
      this.formatImportoCeApprovato(item);
    }
  }

  changeImportoCeProposto(item: any, index: number) {
    if (!this.tableAltriCostiForm.form.get('importoCeProposto' + index).hasError('pattern')) {
      this.formatImportoCeProposto(item);
    }
  }

  nuovaProposta() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedNuovaProposta = false;
    this.subscribers.nuovaProposta = this.rimodulazioneContoEconomicoService.contoEconomicoLocked(this.idProgetto).subscribe(data => {
      if (data) {
        this.esitoFindContoEconomicoDTO.locked = true;
        const element = document.querySelector('#scrollId');
        element.scrollIntoView();
      } else {
        this.isNuovaProposta = true;
        let index = this.displayedColumns.findIndex(d => d === "spesaIstruttoria");
        this.displayedColumns.splice(index, 0, "richiestoNuovaProposta");
        this.righeContoEconomico.splice(1, 0, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
          null, null, null, null, null, null, null, true, null));
        this.calcolaTotaliNuovaProposta();
      }
      this.loadedNuovaProposta = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica.");
      this.loadedNuovaProposta = true;
    });

  }

  rimodula() {
    this.isRimodula = true;
    if (!this.isIstruttoria) {
      let index = this.displayedColumns.findIndex(d => d === "spesaRendicontata");
      this.displayedColumns.splice(index, 0, "spesaAmmessaRimod");
    } else {
      this.displayedColumns.splice(2, 0, "spesaAmmessaRimod");
    }
    this.righeContoEconomico.splice(1, 0, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, true, null));
    this.calcolaTotaliSpesaAmmessaRimodulazione();
  }

  creaRichiesta() {
    this.isCreaRichiesta = true;
  }

  salvaProposta() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    for (let i = 1; i < this.righeContoEconomico.length; i++) {
      if (this.tableForm.form.get('importoRichiestoNuovaProposta' + i).hasError('pattern')) {
        this.showMessageError("Gli importi evidenziati non sono validi.");
        return;
      }
    }
    if (this.dataSourceAltriCosti.data.length) {
      for (let i = 0; i < this.dataSourceAltriCosti.data.length; i++) {
        if (this.tableAltriCostiForm.form.get('importoCeApprovato' + i).hasError('pattern')
          || this.tableAltriCostiForm.form.get('importoCeProposto' + i).hasError('pattern')) {
          this.showMessageError("Gli importi evidenziati non sono validi.");
          return;
        }
        if (this.tableAltriCostiForm.form.get('importoCeApprovato' + i).hasError('required')
          || this.tableAltriCostiForm.form.get('importoCeProposto' + i).hasError('required')) {
          this.showMessageError("Sono presenti campi obbligatori da compilare.");
          return;
        }
      }
    }
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.filter(r => r.level !== null).forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let request = new SalvaPropostaRimodulazioneRequest(this.idProgetto, righeConto);
    this.subscribers.salva = this.rimodulazioneContoEconomicoService.salvaPropostaRimodulazione(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isSavedProposta = true;
          this.isNuovaProposta = false;
          this.righeContoEconomico = data.righeContoEconomicoAggiornato;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (messaggio.length > 0) {
            this.showMessageWarning(messaggio);
          }
        } else {
          this.righeContoEconomico.splice(1, 1, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, true, null));
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
        this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        this.calcolaTotaliNuovaProposta();
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  salvaRI() {
    if (this.isIstruttoria) {
      this.validaRimodulazioneIstruttoria();
    } else {
      this.salvaRimod();
    }
  }

  salvaRimod() {
    this.resetMessageSuccess();
    this.resetMessageError();
    for (let i = 1; i < this.righeContoEconomico.length; i++) {
      if (this.tableForm.form.get('importoSpesaAmmessaRimodulazione' + i).hasError('pattern')) {
        this.showMessageError("Gli importi evidenziati non sono validi.");
        return;
      }
    }
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.filter(r => r.level !== null).forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let request = new SalvaRimodulazioneRequest(this.idProgetto, righeConto);
    this.subscribers.salva = this.rimodulazioneContoEconomicoService.salvaRimodulazione(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isSavedRimod = true;
          this.isRimodula = false;
          this.righeContoEconomico = data.righeContoEconomicoAggiornato;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (messaggio.length > 0) {
            this.showMessageSuccess(messaggio);
          }
        } else {
          this.righeContoEconomico.splice(1, 1, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, true, null));
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
        this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        this.calcolaTotaliSpesaAmmessaRimodulazione();
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  validaRimodulazioneIstruttoria() {
    this.resetMessageSuccess();
    this.resetMessageError();
    for (let i = 2; i < this.righeContoEconomico.length; i++) {
      if (this.tableForm.form.get('importoSpesaAmmessaRimodulazione' + i).hasError('pattern')) {
        this.showMessageError("Gli importi evidenziati non sono validi.");
        return;
      }
    }
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.filter(r => r.level !== null).forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let request = new SalvaRimodulazioneRequest(this.idProgetto, righeConto);
    this.subscribers.salva = this.rimodulazioneContoEconomicoService.validaRimodulazioneIstruttoria(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isSavedRimod = true;
          this.isRimodula = false;
          this.righeContoEconomico = data.righeContoEconomicoAggiornato;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (messaggio.length > 0) {
            this.showMessageWarning(messaggio);
          }
        } else {
          this.righeContoEconomico.splice(1, 1, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, true, null));
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
        this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        this.calcolaTotaliSpesaAmmessaRimodulazione();
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  salvaRichiesta() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    for (let i = 1; i < this.righeContoEconomico.length; i++) {
      if (this.tableForm.form.get('importoRichiestoInDomanda' + i).hasError('pattern')) {
        this.showMessageError("Gli importi evidenziati non sono validi.");
        return;
      }
    }
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.filter(r => r.level !== null).forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let request = new SalvaPropostaRimodulazioneRequest(this.idProgetto, righeConto);
    this.subscribers.salva = this.rimodulazioneContoEconomicoService.validaPropostaRimodulazioneInDomanda(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isSavedRichiesta = true;
          this.isCreaRichiesta = false;
          this.righeContoEconomico = data.righeContoEconomicoAggiornato;
          this.righeContoEconomico.forEach(r => { r.visible = true });
          for (let riga of this.righeContoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.righeContoEconomico.find(r => r.id === riga.idPadre);
            padre.figliVisible = true;
          }
          if (messaggio.length > 0) {
            this.showMessageWarning(messaggio);
          }
        } else {
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
        this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
        this.calcolaTotaliRichiesto();
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  confermaProposta() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let array: Array<AltriCostiDTO> = null;
    if (this.isBR59 && !this.isRichiesta && this.dataSourceAltriCosti?.data?.length) {
      array = new Array<AltriCostiDTO>();
      this.dataSourceAltriCosti.data.forEach(item => array.push(Object.assign({}, item)));
      array.forEach(r => {
        //campi usati a video per formattazione
        delete r['impCeApprovatoFormatted'];
        delete r['impCePropmodFormatted'];
      });
    }
    let request = new SalvaPropostaRimodulazioneRequest(this.idProgetto, righeConto, array);
    this.subscribers.conferma = this.rimodulazioneContoEconomicoService.salvaPropostaRimodulazioneConfermata(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isConfirmedProposta = true;
          if (messaggio.length > 0) {
            this.showMessageSuccess(messaggio);
          }
        } else {
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di conferma.");
      this.loadedSalva = true;
    });
  }

  confermaRimodulazioneIstruttoria() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let request = new SalvaRimodulazioneRequest(this.idProgetto, righeConto);
    this.subscribers.conferma = this.rimodulazioneContoEconomicoService.salvaRimodulazioneIstruttoria(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isConfirmedRimodIstr = true;
          if (messaggio.length > 0) {
            this.showMessageSuccess(messaggio);
          }
        } else {
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di conferma.");
      this.loadedSalva = true;
    });
  }

  confermaRichiesta() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.loadedSalva = false;
    let righeConto = new Array<ContoEconomicoItem>();
    this.righeContoEconomico.forEach(r => righeConto.push(Object.assign({}, r)));
    righeConto.forEach(r => {
      delete r['visible'];
      delete r['figliVisible'];
      delete r['perc'];
    });
    let request = new SalvaPropostaRimodulazioneRequest(this.idProgetto, righeConto);
    this.subscribers.conferma = this.rimodulazioneContoEconomicoService.salvaPropostaRimodulazioneInDomanda(request).subscribe(data => {
      if (data) {
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data.esito) {
          this.isConfirmedRichiesta = true;
          if (messaggio.length > 0) {
            this.showMessageSuccess(messaggio);
          }
        } else {
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di conferma.");
      this.loadedSalva = true;
    });
  }


  modificaProposta() {
    this.isConfirmedProposta = false;
    this.isSavedProposta = false;
    this.isNuovaProposta = true;
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.righeContoEconomico.splice(1, 0, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, true, null));
    this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
    this.calcolaTotaliNuovaProposta();
  }

  modificaRimod() {
    if (this.isIstruttoria) {
      this.isConfirmedRimodIstr = false;
    }
    this.isSavedRimod = false;
    this.isRimodula = true;
    this.resetMessageError();
    this.resetMessageSuccess();
    this.righeContoEconomico.splice(1, 0, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, true, null));
    this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
    this.calcolaTotaliSpesaAmmessaRimodulazione();
  }

  modificaRichiesta() {
    this.isConfirmedRichiesta = false;
    this.isSavedRichiesta = false;
    this.isCreaRichiesta = true;
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
  }

  procediProposta() {
    this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO}/procediProposta/${this.idProgetto}`
      + `/${this.idBando}/${this.esitoFindContoEconomicoDTO.contoEconomico.idContoEconomico}`]);
  }

  concludiRimodulazione() {
    if (!this.isIstruttoria) {
      this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO}/concludiRimodulazione/${this.idProgetto}`
        + `/${this.idBando}/${this.esitoFindContoEconomicoDTO.contoEconomico.idContoEconomico}`]);
    } else {
      this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA}/concludiRimodulazione/${this.idProgetto}`
        + `/${this.idBando}/${this.esitoFindContoEconomicoDTO.contoEconomico.idContoEconomico}`]);
    }
  }

  inviaRichiesta() {
    this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA}/inviaRichiesta/${this.idProgetto}`
      + `/${this.idBando}/${this.esitoFindContoEconomicoDTO.contoEconomico.idContoEconomico}`]);
  }

  goToEconomie() {
    let url: string = `${this.configService.getPbwebURL()}/#/drawer/${Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE}/gestioneEconomie;`
      + `idProgetto=${this.idProgetto};idBando=${this.idBando}?idSg=${this.user.idSoggetto}`
      + `&idSgBen=${(this.user.beneficiarioSelezionato ? this.user.beneficiarioSelezionato.idBeneficiario : "0")}&idU=${this.user.idUtente}&role=${this.user.codiceRuolo}`;
    window.location.href = url;
  }

  indietro() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    if (this.isProposta) {
      this.isNuovaProposta = false;
      let index = this.displayedColumns.findIndex(d => d === "richiestoNuovaProposta");
      this.displayedColumns.splice(index, 1);
    } else if (this.isIstruttoria) {
      this.isRimodula = false;
      this.displayedColumns.splice(2, 1);
    } else if (this.isRichiesta) {
      this.isCreaRichiesta = false;
    } else {
      this.isRimodula = false;
      let index = this.displayedColumns.findIndex(d => d === "spesaAmmessaRimod");
      this.displayedColumns.splice(index, 1);
    }
    this.isRipristinaSpesaAmmessaIstruttoria = false;
    this.isRipristinaUltimaProposta = false;
    this.isRipristinaSpesaAmmessaUltima = false;
    this.isRipristinaRichiestoInDomanda = false;
    this.loadData();
  }

  annulla() {
    if (this.messageWarning && this.isMessageWarningVisible) {
      this.messageWarning = this.messageWarning.replace(/Confermare il salvataggio dei dati inseriti\./g, "");
      if (this.messageWarning.length === 0) {
        this.resetMessageWarning();
      }
    }
    if (this.isProposta) {
      this.isSavedProposta = false;
      this.isNuovaProposta = true;
      this.righeContoEconomico.splice(1, 0, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, true, null));
      this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
      this.calcolaTotaliNuovaProposta();
    } else if (this.isIstruttoria) {
      this.isSavedRimod = false;
      this.isRimodula = true;
      this.righeContoEconomico.splice(1, 0, new ContoEconomicoItem(null, null, null, null, null, null, null, null, null, "0,00", null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, true, null));
      this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.righeContoEconomico);
      this.calcolaTotaliSpesaAmmessaRimodulazione();
    } else if (this.isRichiesta) {
      this.isSavedRichiesta = false;
      this.isCreaRichiesta = true;
    }
  }

  ripristinaRichiestoInDomanda() {
    for (let riga of this.righeContoEconomico) {
      if (this.isProposta) {
        riga.importoRichiestoNuovaProposta = riga.importoRichiestoInDomanda;
      } else {
        riga.importoSpesaAmmessaRimodulazione = riga.importoRichiestoInDomanda;
      }
    }
    this.isRipristinaSpesaAmmessaIstruttoria = false;
    this.isRipristinaUltimaProposta = false;
    this.isRipristinaSpesaAmmessaUltima = false;
    this.isRipristinaRichiestoInDomanda = true;
    if (this.isProposta) {
      this.calcolaTotaliNuovaProposta();
    } else {
      this.calcolaTotaliSpesaAmmessaRimodulazione();
    }
  }

  ripristinaSpesaAmmessaIstruttoria() {
    for (let riga of this.righeContoEconomico) {
      if (this.isProposta) {
        riga.importoRichiestoNuovaProposta = riga.importoSpesaAmmessaInDetermina;
      } else {
        riga.importoSpesaAmmessaRimodulazione = riga.importoSpesaAmmessaInDetermina;
      }
    }
    this.isRipristinaRichiestoInDomanda = false;
    this.isRipristinaSpesaAmmessaUltima = false;
    this.isRipristinaUltimaProposta = false;
    this.isRipristinaSpesaAmmessaIstruttoria = true;
    if (this.isProposta) {
      this.calcolaTotaliNuovaProposta();
    } else {
      this.calcolaTotaliSpesaAmmessaRimodulazione();
    }
  }
  ripristinaUltimaProposta() {
    for (let riga of this.righeContoEconomico) {
      if (this.isProposta) {
        riga.importoRichiestoNuovaProposta = riga.importoRichiestoUltimaProposta;
      } else {
        riga.importoSpesaAmmessaRimodulazione = riga.importoRichiestoUltimaProposta;
      }
    }
    this.isRipristinaRichiestoInDomanda = false;
    this.isRipristinaSpesaAmmessaUltima = false;
    this.isRipristinaSpesaAmmessaIstruttoria = false;
    this.isRipristinaUltimaProposta = true;
    if (this.isProposta) {
      this.calcolaTotaliNuovaProposta();
    } else {
      this.calcolaTotaliSpesaAmmessaRimodulazione();
    }
  }

  ripristinaSpesaAmmessaUltima() {
    for (let riga of this.righeContoEconomico) {
      if (this.isProposta) {
        riga.importoRichiestoNuovaProposta = riga.importoSpesaAmmessaUltima;
      } else {
        riga.importoSpesaAmmessaRimodulazione = riga.importoSpesaAmmessaUltima;
      }
    }
    this.isRipristinaRichiestoInDomanda = false;
    this.isRipristinaSpesaAmmessaIstruttoria = false;
    this.isRipristinaUltimaProposta = false;
    this.isRipristinaSpesaAmmessaUltima = true;
    if (this.isProposta) {
      this.calcolaTotaliNuovaProposta();
    } else {
      this.calcolaTotaliSpesaAmmessaRimodulazione();
    }
  }

  downloadRimod() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.rimodulazioneContoEconomicoService.scaricaRimodulazione(this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, this.idContoEconomico)
      .subscribe(res => {
        let nomeFile = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nomeFile);
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
  }

  getDescBreveTask() {
    let descBreveTask: string;
    switch (this.idOperazione) {
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO:
        descBreveTask = Constants.DESCR_BREVE_TASK_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO;
        break;
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO:
        descBreveTask = Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO;
        break;
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA:
        descBreveTask = Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA;
        break;
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA:
        descBreveTask = Constants.DESCR_BREVE_TASK_RICHIESTA_CONTO_ECONOMICO_DOMANDA;
        break;
      default:
        break;
    }
    return descBreveTask;
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, this.getDescBreveTask()).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
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

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
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
    if (!this.loadedChiudiAttivita || !this.loadedInizializza || !this.loadedNuovaProposta || !this.loadedSalva
      || !this.loadedDownload || !this.loadedBR59 || !this.loadedAltriCosti) {
      return true;
    }
    return false;
  }
}
