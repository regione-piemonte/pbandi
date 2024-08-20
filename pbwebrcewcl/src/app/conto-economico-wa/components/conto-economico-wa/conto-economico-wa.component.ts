/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, InfoDocumentoVo, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { AffidamentiService } from 'src/app/affidamenti/services/affidamenti.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DatePipe } from '@angular/common';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
pdfMake.vfs = pdfFonts.pdfMake.vfs;
import htmlToPdfmake from 'html-to-pdfmake';
import { ContoEconomicoWaService } from '../../services/conto-economico-wa.service';
import { QteHtmlDTO } from '../../commons/dto/qte-html-dto';
import { QteProgettoDTO } from '../../commons/dto/qte-progetto-dto';
import { DatiQteDTO } from '../../commons/dto/dati-qte-dto';
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { QteFaseDTO } from '../../commons/dto/qte-fase-dto';
import { saveAs } from 'file-saver-es';
import { MatTableDataSource } from '@angular/material/table';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { DatiCccDTO } from '../../commons/dto/dati-ccc-dto';
import { ConfermaWarningDialogComponent } from 'src/app/shared/components/conferma-warning-dialog/conferma-warning-dialog.component';

declare const updateValues: any;  //funzione definita in assets/js/conto-economico-wa.js

export interface IdDescBreve {
  descBreve: string;
  id: string;
}

export interface NumberDescBreve {
  descBreve: string;
  number: number;
}

export interface CheckDescBreve {
  descBreve: string;
  check: boolean;
}

@Component({
  selector: 'app-conto-economico-wa',
  templateUrl: './conto-economico-wa.component.html',
  styleUrls: ['./conto-economico-wa.component.scss'],
  encapsulation: ViewEncapsulation.None
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ContoEconomicoWaComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idBando: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isBeneficiario: boolean;
  isVariante1Visible: boolean;
  isVariante2Visible: boolean;
  isStampaPdf: boolean = false;
  cccExists: boolean;
  idDocumentoIndexCCC: number;
  datiCCC: DatiCccDTO;

  qteHtml: QteHtmlDTO;

  htmlContent: string;

  nCofinanziamentoFondi: string = "";
  dtCofinanziamentoFondi: Date;

  elencoColonneQte: Array<QteFaseDTO>;

  elencoIdDatiEconomiciHTML: IdDescBreve[] = [
    { id: "A", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_FONDO_COMPL },
    { id: "B", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_FONDI_PROPRI_ENTE },
    { id: "C", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_COFIN_560 },
    { id: "D", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_COFIN_513 },
    { id: "E", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_AVVIO_OPERE },
    { id: "F", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_ALTRE_FONTI_PUBBL },
    { id: "H", descBreve: Constants.DESC_BREVE_SOGG_FINANZIATORE_IMP_FIN_PRIVATO },
  ];

  elencoIdTipoIntervHTML: IdDescBreve[] = [
    { id: "A", descBreve: Constants.DESC_BREVE_TIPO_INTERV_QTES_INT_A },
    { id: "B", descBreve: Constants.DESC_BREVE_TIPO_INTERV_QTES_INT_B },
    { id: "C", descBreve: Constants.DESC_BREVE_TIPO_INTERV_QTES_INT_C },
    { id: "D", descBreve: Constants.DESC_BREVE_TIPO_INTERV_QTES_INT_D },
    { id: "E", descBreve: Constants.DESC_BREVE_TIPO_INTERV_QTES_INT_E },
    { id: "F", descBreve: Constants.DESC_BREVE_TIPO_INTERV_QTES_INT_F },
  ];

  elencoIdAltriValoriHTML: IdDescBreve[] = [
    { id: "NAlloggi", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_NA },
    { id: "SC", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_SCM },
    { id: "SCPerc", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_SCS },
    { id: "CR", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_CR },
    { id: "ST1", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_ST1 },
    { id: "ST2", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_ST2 },
    { id: "AOC", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_AOC },
    { id: "IMPR", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_IMPR },
    { id: "RibassoAsta", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_RA },
    { id: "CI", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_CI },
    { id: "IVA", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_IVA },
    { id: "IRAP", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_IRAP },
    { id: "CT", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_CT },
    { id: "IVADet", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_IVAD },
    { id: "Rientri", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_RI },
    { id: "CostoEff", descBreve: Constants.DESC_BREVE_ALTRI_VALORI_QTES_CE }
  ];
  elencoIdAttoHTML: string[] = ["qeProgettoAtto", "qeAggiudicazioneDefAtto", "qeRendicontazioneAtto"];
  elencoIdAttoCertHTML: string[] = ["", "", "qeRendicontazioneAttoCert"];

  @ViewChild('stampaPDF') stampaPDF: ElementRef;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string[];
  isMessageErrorVisible: boolean;
  messageWarning: string[];
  isMessageWarningVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedModelloQte: boolean;
  loadedColonneQte: boolean;
  loadedSalva: boolean = true;
  loadedChiudiAttivita: boolean = true;
  loadedDatiCCC: boolean = true;
  loadedSalvaCCC: boolean = true;
  loadedIdDocumentoIndexCCC: boolean = true;
  loadedDownload: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  elRef: ElementRef;

  constructor(
    private configService: ConfigService,
    private sharedService: SharedService,
    private userService: UserService,
    private affidamentiService: AffidamentiService,
    private contoEconomicoWaService: ContoEconomicoWaService,
    private archivioFileService: ArchivioFileService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private datePipe: DatePipe,
    private router: Router,
    elRef: ElementRef
  ) {
    this.elRef = elRef;
  }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];

      this.subscribers.router = this.activatedRoute.params.subscribe(params => {
        this.idProgetto = +params['idProgetto'];
        this.idBando = +params['idBando'];

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
          if (data) {
            this.user = data;
            if (this.user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA) {
              this.isBeneficiario = true;
            }
            this.loadData();
          }
        });
      });
    });
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.affidamentiService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });
    this.loadedColonneQte = false;
    this.contoEconomicoWaService.getColonneModelloQte(this.idBando).subscribe(res => {
      if (res) {
        this.elencoColonneQte = res;
        this.loadQte();
      }
      this.loadedColonneQte = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore durante il caricamento delle colonne del QTE."]);
      this.loadedColonneQte = true;
    });
    this.loadedDatiCCC = false;
    this.contoEconomicoWaService.getDatiCCC(this.idBando, this.idProgetto).subscribe(data => {
      if (data) {
        this.datiCCC = data;
      }
      this.loadedDatiCCC = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore durante il caricamento dei dati per il certificato chiusura conti."]);
      this.loadedDatiCCC = true;
    });
  }

  loadQte() {
    this.loadedModelloQte = false;
    //cerco qte gia esistente per questo progetto
    this.contoEconomicoWaService.getQteProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.qteHtml = res;
        this.htmlContent = res.htmlQtesProgetto;
        this.prepareHtml();
        //verifico se esiste gia il ccc
        this.loadedIdDocumentoIndexCCC = false;
        this.contoEconomicoWaService.getIdDocumentoIndexCCC(this.idProgetto, this.qteHtml.idQtesHtmlProgetto).subscribe(res => {
          if (res) {
            this.cccExists = true;
            this.idDocumentoIndexCCC = res;
          }
          this.loadedIdDocumentoIndexCCC = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError(["Errore durante la verifica di presenza del certificato chiusura conti definitivo."]);
          this.loadedIdDocumentoIndexCCC = true;
        });
        this.loadedModelloQte = true;
      } else {
        //se non trovo un qte gia esistente, carico il modello
        this.contoEconomicoWaService.getModelloQte(this.idBando).subscribe(res => {
          if (res) {
            this.qteHtml = res;
            this.htmlContent = res.htmlQtesProgetto;
            this.prepareHtml();
          }
          this.loadedModelloQte = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError(["Errore durante il caricamento del modello del QTE."]);
          this.loadedModelloQte = true;
        });
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore durante il caricamento del QTE del progetto."]);
      this.loadedModelloQte = true;
    });
  }

  prepareHtml() {
    setTimeout(() => {
      this.addEventListener();
      let values1: any = document.getElementsByClassName("variante1Visible");
      let values2: any = document.getElementsByClassName("variante2Visible");
      if (values1?.length > 0 && values1[0]?.style.display === "table-cell") {
        this.showHideVariante("variante1Visible", true);
        this.isVariante1Visible = true;
        this.columns.push(this.variante1Col);
      } else {
        this.showHideVariante("variante1Visible", false);
      }
      if (values2?.length > 0 && values2[0]?.style.display === "table-cell") {
        this.showHideVariante("variante2Visible", true);
        this.isVariante2Visible = true;
        this.columns.push(this.variante2Col);
        document.getElementById("aggiungiVariante").style.display = "none";
      } else {
        this.showHideVariante("variante2Visible", false);
      }

      this.disableColumns();
      this.checkWarnings();
      // if (!this.isBeneficiario) {
      //   this.disableAll();
      // }
    }, 0);
  }

  disableAll() {
    let form: any = document.getElementById("formId");
    let allElements = form.elements;
    for (let i = 0, l = allElements.length; i < l; ++i) {
      if (allElements[i].type === "checkbox") {
        allElements[i].disabled = true;
      } else {
        allElements[i].readOnly = true;
      }
    }
  }

  disableColumns() {
    //se l'atto di approvazione e' stato compilato, disabilito i campi della colonna corrispondente
    //se l'atto di approvazione e' stato compilato, disabilito gli atti di approvazione delle colonne precedenti
    let idAttoPrec: string = "";
    let i: number = 0;
    for (let column of this.columns) {
      let idAtto: string = column + "Atto";
      let idAttoCert: string = column + "AttoCert";
      if ((<HTMLInputElement>document.getElementById(idAtto))?.value?.length > 0) {
        this.disableByName(column, [idAtto, idAttoCert]);
        this.disableByName(column + "ContoEconSum", [idAtto, idAttoCert]);
        this.disableByName(column + "QESumCI", [idAtto, idAttoCert]);
        this.disableByName("checkST", []);
        this.disableByName("checkSC", []);
        this.disableByName("checkSCPerc", []);
        if (idAttoPrec?.length > 0) {
          (<HTMLInputElement>document.getElementById(idAttoPrec)).readOnly = true;
        }
        this.elencoColonneQte[i].isAttoPopolato = true;
      }
      i++;
      idAttoPrec = idAtto;
    }
  }

  disableByName(column: string, idExcludedElements: string[]) {
    let elements: any = document.getElementsByName(column);
    for (let i = 0, l = elements.length; i < l; ++i) {
      if (!idExcludedElements.includes(elements[i].id)) {
        if (elements[i].type === "checkbox") {
          elements[i].disabled = true;
        } else {
          elements[i].readOnly = true;
        }
      }
    }
  }

  showHideVariante(col: string, isShow: boolean) {
    let values: any = document.getElementsByClassName(col);
    for (let i = 0; i < values.length; i++) {
      values[i].style.display = isShow ? "table-cell" : "none";
    }
  }

  columnsContoEcon: string[] = ["qeProgetto", "qeAggiudicazioneDef"];
  columns: string[] = ["qeProgetto", "qeAggiudicazioneDef", "qeRendicontazione"];
  variante1Col: string = "qeVariante1";
  variante2Col: string = "qeVariante2";

  addEventListener() {
    this.columnsContoEcon.forEach((c) => {
      this.addEventListenerImpPubblicoContoEcon(c);
      this.addEventListenerImpPrivatoContoEcon(c);
    });
    this.addEventListenerEconomiaContoEcon(this.columns[0], this.columns[1], this.columns[2]);
    this.columns.forEach(c => {
      this.addEventListenerSCDatiMetrici(c);
      this.addEventListenerQuadroEcon(c);
      this.addEventListenerCostoTotMedioAlloggio(c);
    });
    this.addEventListenerFormatCurrency();
    this.addEventListenerAggiungiVariante();
  }

  addEventListenerFormatCurrency() {
    let values: any = document.getElementsByClassName("decimal");
    for (let i = 0; i < values.length; i++) {
      values[i].addEventListener("blur", (event: any) => {
        this.formatCurrency();
      });
    }
  }

  addEventListenerAggiungiVariante() {
    let button: any = document.getElementById("aggiungiVariante");
    button.addEventListener("click", (event: any) => {
      let dialogRef = this.dialog.open(ConfermaDialog, {
        width: '40%',
        data: {
          message: "Confermare l'aggiunta della variante?"
        }
      });
      dialogRef.afterClosed().subscribe(res => {
        if (res === "SI") {
          if (!this.isVariante1Visible) {
            this.isVariante1Visible = true;
            this.showHideVariante("variante1Visible", true);
            this.columns.push(this.variante1Col);
            setTimeout(() => {
              this.addEventListenerSCDatiMetrici(this.variante1Col);
              this.addEventListenerQuadroEcon(this.variante1Col);
              this.addEventListenerCostoTotMedioAlloggio(this.variante1Col);
              this.addEventListenerFormatCurrency();
            });
          } else if (!this.isVariante2Visible) {
            this.isVariante2Visible = true;
            this.showHideVariante("variante2Visible", true);
            this.columns.push(this.variante2Col);
            document.getElementById("aggiungiVariante").style.display = "none";
            setTimeout(() => {
              this.addEventListenerSCDatiMetrici(this.variante2Col);
              this.addEventListenerQuadroEcon(this.variante2Col);
              this.addEventListenerCostoTotMedioAlloggio(this.variante2Col);
              this.addEventListenerFormatCurrency();
            });
          }
        }
      })
      event.preventDefault(); //per non far refreshare la pagina
    });
  }

  addEventListenerQuadroEcon(col: string) {
    this.addEventListenerCRQuadroEcon(col);
    this.addEventListenerSTQuadroEcon(col);
    this.addEventListenerCIQuadroEcon(col);
    this.addEventListenerCTQuadroEcon(col);
    this.addEventListenerCostoEffettivoQuadroEcon(col);
  }

  addEventListenerImpPubblicoContoEcon(pre: string) {
    let values: any = document.getElementsByName(pre + "ContoEconSum");
    for (let i = 0; i < values.length; i++) {
      values[i].addEventListener("input", (event: any) => {
        this.calcolaImpPubblicoContoEcon(pre + 'ContoEconSum', pre + 'ImpPubblico', pre + 'ContoEconH', pre + 'TotaleInterv');
      });
    }
  }

  addEventListenerImpPrivatoContoEcon(pre: string) {
    let value: any = document.getElementById(pre + "ContoEconH");
    value.addEventListener("input", (event: any) => {
      this.calcolaTotaleInterventoContoEcon(pre + 'ImpPubblico', pre + 'ContoEconH', pre + 'TotaleInterv');
    });
  }

  addEventListenerEconomiaContoEcon(colConcesso: string, colSpeso: string, colEconomia: string) {
    let rows: string[] = ["ContoEconA", "ContoEconB", "ContoEconC", "ContoEconD", "ContoEconE", "ContoEconF", "ContoEconH"];
    for (let row of rows) {
      let concesso: any = document.getElementById(colConcesso + row);
      concesso.addEventListener("input", (event: any) => {
        this.calcolaEconomiaContoEcon(colConcesso + row, colSpeso + row, colEconomia + row);
      });
      let speso: any = document.getElementById(colSpeso + row);
      speso.addEventListener("input", (event: any) => {
        this.calcolaEconomiaContoEcon(colConcesso + row, colSpeso + row, colEconomia + row);
      });
    }
  }

  addEventListenerSCDatiMetrici(pre: string) {
    let values = [];
    values.push(document.getElementById(pre + 'SU'));
    values.push(document.getElementById(pre + 'SNRAlloggi'));
    values.push(document.getElementById(pre + 'SNROrgAbit'));
    values.push(document.getElementById(pre + 'SP'));
    let checkSC = this.checkSC;
    let checkSCPerc = this.checkSCPerc;
    values.forEach(v => {
      v.addEventListener("input", (event: any) => {
        this.calcolaSCDatiMetrici(pre + 'SU', pre + 'SNRAlloggi', pre + 'SNROrgAbit', pre + 'SP', pre + 'SC', pre + 'SCPerc',
          checkSC.checked, checkSCPerc.checked, pre + "CR", pre + "CRDivisoSC", pre + "CT", pre + "CTDivisoSC");
      });
    });
    checkSC.addEventListener("change", (event: any) => {
      checkSCPerc.checked = false;
      this.resetSCDatiMetrici(checkSC.checked, checkSCPerc.checked);
      this.calcolaSCDatiMetrici(pre + 'SU', pre + 'SNRAlloggi', pre + 'SNROrgAbit', pre + 'SP', pre + 'SC', pre + 'SCPerc',
        checkSC.checked, checkSCPerc.checked, pre + "CR", pre + "CRDivisoSC", pre + "CT", pre + "CTDivisoSC");
    });
    checkSCPerc.addEventListener("change", (event: any) => {
      checkSC.checked = false;
      this.resetSCDatiMetrici(checkSC.checked, checkSCPerc.checked);
      this.calcolaSCDatiMetrici(pre + 'SU', pre + 'SNRAlloggi', pre + 'SNROrgAbit', pre + 'SP', pre + 'SC', pre + 'SCPerc',
        checkSC.checked, checkSCPerc.checked, pre + "CR", pre + "CRDivisoSC", pre + "CT", pre + "CTDivisoSC");
    });
  }

  addEventListenerCRQuadroEcon(pre: string) {
    let valueCR: any = document.getElementById(pre + "CR");
    let checkSC = this.checkSC;
    let checkSCPerc = this.checkSCPerc;
    valueCR.addEventListener("input", (event: any) => {
      this.calcolaCRDivisoSC(pre + 'CR', pre + 'SC', pre + 'SCPerc', checkSC.checked, checkSCPerc.checked, pre + 'CRDivisoSC');
    });
  }

  addEventListenerSTQuadroEcon(pre: string) {
    let checkST1 = this.checkST1;
    let checkST2 = this.checkST2;
    this.enableDisableST1(pre, !checkST1.checked);
    this.enableDisableST2(pre, !checkST2.checked);
    checkST1.addEventListener("change", (event: any) => {
      checkST2.checked = false;
      this.enableDisableST1(pre, !checkST1.checked);
      this.enableDisableST2(pre, true);
      this.resetSTQuadroEcon(checkST1.checked, checkST2.checked);
    });
    checkST2.addEventListener("change", (event: any) => {
      checkST1.checked = false;
      this.enableDisableST2(pre, !checkST2.checked);
      this.enableDisableST1(pre, true);
      this.resetSTQuadroEcon(checkST1.checked, checkST2.checked);
    });
  }

  enableDisableST1(pre: string, disabled: boolean) {
    let ST1: any = document.getElementById(pre + "ST1");
    if (disabled) {
      ST1.setAttribute("disabled", disabled.toString());
    } else {
      ST1.removeAttribute("disabled");
    }
  }

  enableDisableST2(pre: string, disabled: boolean) {
    let ST2: any = document.getElementById(pre + "ST2");
    if (disabled) {
      ST2.setAttribute("disabled", disabled.toString());
    } else {
      ST2.removeAttribute("disabled");
    }
  }


  addEventListenerCIQuadroEcon(pre: string) {
    let values: any = document.getElementsByName(pre + "QESumCI");
    let checkSC = this.checkSC;
    let checkSCPerc = this.checkSCPerc;
    for (let i = 0; i < values.length; i++) {
      values[i].addEventListener("input", (event: any) => {
        this.calcolaCIQuadroEcon(pre + 'QESumCI', pre + 'CI', pre + 'IVA', pre + 'IRAP', pre + 'CT', pre + 'IVADet', pre + 'Rientri', pre + 'CostoEff',
          pre + 'NAlloggi', pre + 'CostoTotMedioAlloggio', pre + 'SC', pre + 'SCPerc', checkSC?.checked, checkSCPerc?.checked, pre + 'CTDivisoSC');
      });
    }
  }

  addEventListenerCTQuadroEcon(pre: string) {
    let values = [];
    values.push(document.getElementById(pre + 'IVA'));
    values.push(document.getElementById(pre + 'IRAP'));
    let checkSC = this.checkSC;
    let checkSCPerc = this.checkSCPerc;
    values.forEach(v => {
      v.addEventListener("input", (event: any) => {
        this.calcolaCTQuadroEcon(pre + 'CI', pre + 'IVA', pre + 'IRAP', pre + 'CT', pre + 'IVADet', pre + 'Rientri', pre + 'CostoEff', pre + 'NAlloggi',
          pre + 'CostoTotMedioAlloggio', pre + 'SC', pre + 'SCPerc', checkSC?.checked, checkSCPerc?.checked, pre + 'CTDivisoSC');
      });
    })
  }

  addEventListenerCostoEffettivoQuadroEcon(pre: string) {
    let values = [];
    values.push(document.getElementById(pre + 'IVADet'));
    values.push(document.getElementById(pre + 'Rientri'));
    values.forEach(v => {
      v.addEventListener("input", (event: any) => {
        this.calcolaCostoEffettivoQuadroEcon(pre + 'CT', pre + 'IVADet', pre + 'Rientri', pre + 'CostoEff', pre + 'NAlloggi', pre + 'CostoTotMedioAlloggio');
      });
    })
  }

  addEventListenerCostoTotMedioAlloggio(pre: string) {
    let values = [];
    values.push(document.getElementById(pre + 'NAlloggi'));
    values.forEach(v => {
      v.addEventListener("input", (event: any) => {
        this.calcolaCostoTotMedioAlloggio(pre + 'NAlloggi', pre + 'CostoEff', pre + 'CostoTotMedioAlloggio');
      });
    })
  }

  get checkSC() {
    return <HTMLInputElement>document.getElementById('checkSC');
  }

  get checkSCPerc() {
    return <HTMLInputElement>document.getElementById('checkSCPerc');
  }

  get checkST1() {
    return <HTMLInputElement>document.getElementById('checkST1');
  }

  get checkST2() {
    return <HTMLInputElement>document.getElementById('checkST2');
  }

  //CALCOLI inizio

  formatCurrency() {
    let values: any = document.getElementsByClassName("decimal");
    for (let i = 0; i < values.length; i++) {
      if (values[i]) {
        let num: number = this.sharedService.getNumberFromFormattedString(values[i].value);
        if (num !== null) {
          values[i].value = this.sharedService.formatValue(num.toString());
        }
      }
    }
  }

  //sezione conto economico

  calcolaImpPubblicoContoEcon(nameImporti: string, idTotPubblico: string, idImpPrivato: string, idTotaleInterv: string) {
    let values: any = document.getElementsByName(nameImporti);
    let res: number = 0;
    for (let i = 0; i < values.length; i++) {
      res += this.sharedService.getNumberFromFormattedString(values[i]?.value) || 0;
    }
    (<HTMLInputElement>document.getElementById(idTotPubblico)).value = this.sharedService.formatValue(res.toFixed(2));
    const row: string = "ImpPubblico";
    this.calcolaEconomiaContoEcon(this.columns[0] + row, this.columns[1] + row, this.columns[2] + row);
    this.calcolaTotaleInterventoContoEcon(idTotPubblico, idImpPrivato, idTotaleInterv);
  }

  calcolaTotaleInterventoContoEcon(idImpPubblico: string, idImpPrivato: string, idTotaleInterv: string) {
    let valuePubblico = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idImpPubblico))?.value) || 0;
    let valuePrivato = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idImpPrivato))?.value) || 0;
    let res: number = valuePubblico + valuePrivato;
    (<HTMLInputElement>document.getElementById(idTotaleInterv)).value = this.sharedService.formatValue(res.toFixed(2));
    const row: string = "TotaleInterv";
    this.calcolaEconomiaContoEcon(this.columns[0] + row, this.columns[1] + row, this.columns[2] + row);
  }

  calcolaEconomiaContoEcon(idImpConcesso: string, idImpSpeso: string, idEconomia: string) {
    let valueConcesso = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idImpConcesso))?.value) || null;
    let valueSpeso = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idImpSpeso))?.value) || null;
    let res: number = null;
    if (valueConcesso !== null && valueSpeso !== null) {
      res = valueConcesso - valueSpeso;
    }
    (<HTMLInputElement>document.getElementById(idEconomia)).value = res !== null ? this.sharedService.formatValue(res.toFixed(2)) : null;
  }



  //sezione dati metrici immobile
  calcolaSCDatiMetrici(idSU: string, idSNRAlloggi: string, idSNROrgAbit: string, idSP: string, idSC: string, idSCPerc: string,
    checkSCChecked: boolean, checkSCPercChecked: boolean, idCR: string, idCRDivisoSC: string, idCT: string, idCTDivisoSC: string) {
    let su = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSU))?.value) || 0;
    let snrAlloggi = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSNRAlloggi))?.value) || 0;
    let snrOrgAbit = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSNROrgAbit))?.value) || 0;
    let sp = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSP))?.value) || 0;
    let res: number = su + snrAlloggi + snrOrgAbit + sp;
    let resPerc: number = su + 0.6 * (snrAlloggi + snrOrgAbit + sp);
    if (checkSCChecked) {
      (<HTMLInputElement>document.getElementById(idSC)).value = this.sharedService.formatValue(res.toFixed(2));
    } else if (checkSCPercChecked) {
      (<HTMLInputElement>document.getElementById(idSCPerc)).value = this.sharedService.formatValue(resPerc.toFixed(2));
    }
    this.calcolaCRDivisoSC(idCR, idSC, idSCPerc, checkSCChecked, checkSCPercChecked, idCRDivisoSC);
    this.calcolaCTDivisoSC(idCT, idSC, idSCPerc, checkSCChecked, checkSCPercChecked, idCTDivisoSC);
  }

  resetSCDatiMetrici(checkSCChecked: boolean, checkSCPercChecked: boolean) {
    if (!checkSCChecked) {
      (<HTMLInputElement>document.getElementById('qeProgettoSC')).value = null;
      (<HTMLInputElement>document.getElementById('qeAggiudicazioneDefSC')).value = null;
      (<HTMLInputElement>document.getElementById('qeRendicontazioneSC')).value = null;
      if (this.isVariante1Visible) {
        (<HTMLInputElement>document.getElementById('qeVariante1SC')).value = null;
      }
      if (this.isVariante2Visible) {
        (<HTMLInputElement>document.getElementById('qeVariante2SC')).value = null;
      }
    }
    if (!checkSCPercChecked) {
      (<HTMLInputElement>document.getElementById('qeProgettoSCPerc')).value = null;
      (<HTMLInputElement>document.getElementById('qeAggiudicazioneDefSCPerc')).value = null;
      (<HTMLInputElement>document.getElementById('qeRendicontazioneSCPerc')).value = null;
      if (this.isVariante1Visible) {
        (<HTMLInputElement>document.getElementById('qeVariante1SCPerc')).value = null;
      }
      if (this.isVariante2Visible) {
        (<HTMLInputElement>document.getElementById('qeVariante2SCPerc')).value = null;
      }
    }
  }

  //sezione quadro economico
  calcolaCIQuadroEcon(nameImporti: string, idCI: string, idIVA: string, idIRAP: string, idCT: string, idIVADet: string, idRientri: string, idCostoEff: string,
    idNAlloggi: string, idCostoTotMedioAlloggio: string, idSC: string, idSCPerc: string, checkSCChekcked: boolean, checkSCPercChekcked: boolean, idCTDivisoSC: string) {
    let values: any = document.getElementsByName(nameImporti);
    let res: number = 0;
    for (let i = 0; i < values.length; i++) {
      res += this.sharedService.getNumberFromFormattedString(values[i]?.value) || 0;
    }
    (<HTMLInputElement>document.getElementById(idCI)).value = this.sharedService.formatValue(res.toFixed(2));
    this.calcolaCTQuadroEcon(idCI, idIVA, idIRAP, idCT, idIVADet, idRientri, idCostoEff, idNAlloggi, idCostoTotMedioAlloggio,
      idSC, idSCPerc, checkSCChekcked, checkSCPercChekcked, idCTDivisoSC);
  }

  calcolaCTQuadroEcon(idCI: string, idIVA: string, idIRAP: string, idCT: string, idIVADet: string, idRientri: string, idCostoEff: string, idNAlloggi: string,
    idCostoTotMedioAlloggio: string, idSC: string, idSCPerc: string, checkSCChekcked: boolean, checkSCPercChekcked: boolean, idCTDivisoSC: string) {
    let valueCI = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idCI))?.value) || 0;
    let valueIVA = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idIVA))?.value) || 0;
    let valueIRAP = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idIRAP))?.value) || 0;
    let res: number = valueCI + valueIVA + valueIRAP;
    (<HTMLInputElement>document.getElementById(idCT)).value = this.sharedService.formatValue(res.toFixed(2));
    this.calcolaCostoEffettivoQuadroEcon(idCT, idIVADet, idRientri, idCostoEff, idNAlloggi, idCostoTotMedioAlloggio);
    this.calcolaCTDivisoSC(idCT, idSC, idSCPerc, checkSCChekcked, checkSCPercChekcked, idCTDivisoSC);
  }

  calcolaCostoEffettivoQuadroEcon(idCT: string, idIVADet: string, idRientri: string, idCostoEff: string, idNAlloggi: string, idCostoTotMedioAlloggio: string) {
    let valueCT = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idCT))?.value) || 0;
    let valueIVADet = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idIVADet))?.value) || 0;
    let valueRientri = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idRientri))?.value) || 0;
    let res: number = valueCT - valueIVADet - valueRientri;
    (<HTMLInputElement>document.getElementById(idCostoEff)).value = this.sharedService.formatValue(res.toFixed(2));
    this.calcolaCostoTotMedioAlloggio(idNAlloggi, idCostoEff, idCostoTotMedioAlloggio);
  }

  resetSTQuadroEcon(checkST1Checked: boolean, checkST2Checked: boolean) {
    if (!checkST1Checked) {
      (<HTMLInputElement>document.getElementById('qeProgettoST1')).value = null;
      (<HTMLInputElement>document.getElementById('qeAggiudicazioneDefST1')).value = null;
      (<HTMLInputElement>document.getElementById('qeRendicontazioneST1')).value = null;
    }
    if (!checkST2Checked) {
      (<HTMLInputElement>document.getElementById('qeProgettoST2')).value = null;
      (<HTMLInputElement>document.getElementById('qeAggiudicazioneDefST2')).value = null;
      (<HTMLInputElement>document.getElementById('qeRendicontazioneST2')).value = null;
    }
  }

  //sezione calcolo costo unitario
  calcolaCostoTotMedioAlloggio(idNAlloggi: string, idCostoEff: string, idCostoTotMedioAlloggio: string) {
    let valueNAlloggi = +(<HTMLInputElement>document.getElementById(idNAlloggi))?.value || 0;
    let valueCostoEff = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idCostoEff))?.value) || 0;
    let costoTotMedioAlloggio = (<HTMLInputElement>document.getElementById(idCostoTotMedioAlloggio));
    if (costoTotMedioAlloggio) {
      if (valueNAlloggi !== 0) {
        let res: number = valueCostoEff / valueNAlloggi;
        costoTotMedioAlloggio.value = this.sharedService.formatValue(res.toFixed(2));
      } else {
        costoTotMedioAlloggio.value = null;
      }
    }
  }

  calcolaCRDivisoSC(idCR: string, idSC: string, idSCPerc: string, checkSCChecked: boolean, checkSCPercChecked: boolean, idCRDivisoSC: string) {
    let valueCR = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idCR))?.value) || 0;
    let valueSC = null;
    if (checkSCChecked) {
      valueSC = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSC))?.value) || 0;
    } else if (checkSCPercChecked) {
      valueSC = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSCPerc))?.value) || 0;
    }
    let crDivisoSC = (<HTMLInputElement>document.getElementById(idCRDivisoSC));
    if (crDivisoSC) {
      if (valueSC !== null && valueSC !== 0) {
        let res: number = valueCR / valueSC;
        crDivisoSC.value = this.sharedService.formatValue(res.toFixed(2));
      } else {
        crDivisoSC.value = null;
      }
    }
  }

  calcolaCTDivisoSC(idCT: string, idSC: string, idSCPerc: string, checkSCChecked: boolean, checkSCPercChecked: boolean, idCTDivisoSC: string) {
    let valueCT = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idCT))?.value) || 0;
    let valueSC = null;
    if (checkSCChecked) {
      valueSC = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSC))?.value) || 0;
    } else if (checkSCPercChecked) {
      valueSC = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(idSCPerc))?.value) || 0;
    }
    let ctDivisoSC = (<HTMLInputElement>document.getElementById(idCTDivisoSC));
    if (ctDivisoSC) {
      if (valueSC !== null && valueSC !== 0) {
        let res: number = valueCT / valueSC;
        ctDivisoSC.value = this.sharedService.formatValue(res.toFixed(2));
      } else {
        ctDivisoSC.value = null;
      }
    }
  }

  //CALCOLI fine

  getTitleEnd(): string {
    let costoEff1 = this.getHTMLInputElementValueById("qeProgettoCostoEff");
    let costoEff2 = this.getHTMLInputElementValueById("qeAggiudicazioneDefCostoEff");
    let costoEff3 = this.getHTMLInputElementValueById("qeRendicontazioneCostoEff");
    if (costoEff1?.length > 0 && costoEff2?.length > 0 && costoEff3?.length > 0) {
      return "di rendicontazione";
    } else if (costoEff1?.length > 0 && costoEff2?.length > 0) {
      return "definitivo";
    } else if (costoEff1?.length > 0) {
      return "di gara";
    }
    return "";
  }

  noneString: string = "none";
  downloadAsPDF() {
    this.isStampaPdf = true;
    setTimeout(() => { //per nascondere elementi dalla pagina
      updateValues();
      let hiddens: any = document.getElementsByClassName("hidden");
      for (let i = 0; i < hiddens.length; i++) {
        hiddens[i].style.display = 'none';
      }
      const pdfTable = this.stampaPDF.nativeElement;

      let html = htmlToPdfmake(pdfTable.innerHTML, { showHidden: false });
      let content = html[0].stack;
      let title = { text: 'Quadro tecnico economico ' + this.getTitleEnd(), style: 'header' };
      let ben = { text: 'Beneficiario: ' + this.user?.beneficiarioSelezionato?.denominazione };
      let prog = { text: 'Codice progetto: ' + this.codiceProgetto };
      content.splice(0, 0, title, ben, prog);
      this.mapTable(content);

      let styles = {
        header: {
          fontSize: 22,
          bold: true,
          marginBottom: 16
        },
      }

      const footer = (currentPage) => {
        let t = {
          layout: "noBorders",
          fontSize: 8,
          margin: [25, 0, 25, 0],
          table: {
            widths: ["*", "*"],
            body: [
              [
                { text: currentPage.toString() },
                { text: this.datePipe.transform(new Date(), 'dd/MM/yyyy') }
              ]
            ]
          }
        };

        return t;
      }
      const documentDefinition = { pageOrientation: 'landscape', footer: footer, content: html, styles: styles };
      pdfMake.createPdf(documentDefinition).open();
      this.isStampaPdf = false;
      hiddens = document.getElementsByClassName("hidden");
      for (let i = 0; i < hiddens.length; i++) {
        hiddens[i].style.display = 'table-cell';
      }
    });
  }

  mapTable(array: any) {
    let tables = array.filter(c => c.nodeName === 'TABLE').map(t => t.table.body);
    for (let table of tables) {
      for (let element of table) {
        let tds = element.filter(t => t.nodeName === 'TD');
        for (let td of tds) {
          if (td.text instanceof Array) {
            let spans = td.text?.filter(t => t.nodeName === 'SPAN');
            for (let span of spans) {
              if (span && span.text instanceof Array) {
                let input = span.text?.find(s => s.nodeName === 'INPUT');
                if (input && input.id) {
                  let innerspan = span.text?.find(s => s.nodeName === 'SPAN');
                  if (innerspan) {
                    let inputDocument = <HTMLInputElement>document.getElementById(input.id);
                    if (inputDocument?.type === 'text') {
                      innerspan.text = inputDocument?.value;
                    } else if (inputDocument?.type === 'date') {
                      innerspan.text = this.datePipe.transform(inputDocument.valueAsDate, "dd/MM/yyyy");
                    } else if (inputDocument?.type === "checkbox") {
                      innerspan.text = inputDocument.checked ? "sì" : "no";
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  toDataURL = async (url) => {
    var res = await fetch(url);
    var blob = await res.blob();

    const result = await new Promise((resolve, reject) => {
      var reader = new FileReader();
      reader.addEventListener("load", function () {
        resolve(reader.result);
      }, false);

      reader.onerror = () => {
        return reject(this);
      };
      reader.readAsDataURL(blob);
    })

    return result
  };


  creaCCC(isAnteprima?: boolean) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    if (!isAnteprima) {
      let dialogRef = this.dialog.open(ConfermaDialog, {
        width: '40%',
        data: {
          message: "Confermare il salvataggio del Certificato Chiusura Conti definitivo?"
        }
      });
      dialogRef.afterClosed().subscribe(res => {
        if (res === "SI") {
          this.creaPdfCCC(isAnteprima);
        }
      });
    } else {
      this.creaPdfCCC(isAnteprima);
    }
  }

  creaPdfCCC(isAnteprima: boolean) {
    updateValues();
    let content = [
      { text: this.datiCCC.descEnte, alignment: "center", fontSize: 8, color: "grey" },
      { text: "SETTORE " + this.datiCCC.descSettore, alignment: "center", fontSize: 8, color: "grey" },
      { text: this.datiCCC.titoloBando, alignment: "center", bold: true, fontSize: 9, margin: [0, 10, 0, 10] },
      { text: "PROGRAMMA", alignment: "center", bold: true, fontSize: 9 },
      { text: `"${this.datiCCC.nomeBandoLinea}"`, alignment: "center", bold: true, italics: true, fontSize: 9 },
      { text: "(D.L. n. 59/2021 conv. con modif. dalla L. n. 101/2021 - D.D. MIMS n. 52 del 30 marzo 2022)", alignment: "center", fontSize: 9, margin: [0, 0, 0, 10] },
      { text: "CERTIFICATO CHIUSURA CONTI", alignment: "center", bold: true, fontSize: 11 },
      { text: "(riferibile esclusivamente alla porzione di immobile di proprietà del soggetto attuatore)", alignment: "center", fontSize: 9, margin: [0, 0, 0, 10] },
      { text: "Identificativo intervento: " + this.datiCCC.numeroDomanda, bold: true, fontSize: 9 },
      { text: "Codice progetto: " + this.codiceProgetto, bold: true, fontSize: 9, margin: [0, 5, 0, 0] },
      { text: "Codice unico di progetto C.U.P.: " + this.datiCCC.cup, bold: true, fontSize: 9, margin: [0, 5, 0, 0] },
      { text: "Comune: " + (this.datiCCC.descComune?.length > 0 ? this.datiCCC.descComune : ""), bold: true, fontSize: 9, margin: [0, 5, 0, 0] },
      { text: "Provincia: " + (this.datiCCC.descProvincia?.length > 0 ? this.datiCCC.descProvincia : ""), bold: true, fontSize: 9, margin: [0, 5, 0, 0] },
      { text: "Soggetto attuatore: " + this.user.beneficiarioSelezionato.denominazione, bold: true, fontSize: 9, margin: [0, 5, 0, 0] },
      { text: "FONTI FINANZIARIE", decoration: 'underline', fontSize: 10, margin: [0, 20, 0, 0] },
    ];
    let htmlDatiEconomici = htmlToPdfmake(document.getElementById("datiEconomiciTable").outerHTML);
    this.mapTable(htmlDatiEconomici);
    htmlDatiEconomici[0].style.push("styleTable");
    content.push(...htmlDatiEconomici)
    let otherContent: any[] = [
      { text: "ATTI E CERTIFICATI AMMINISTRATIVI", decoration: 'underline', fontSize: 10, margin: [0, 20, 0, 10] },
      { text: "Certificato di collaudo o di regolare esecuzione: " + (this.datiCCC.dtFineEffettiva ? this.sharedService.formatDateToString(this.datiCCC.dtFineEffettiva) : "n.d."), fontSize: 9, margin: [0, 5, 0, 0] },
      { text: "Approvazione certificato di collaudo o di regolare esecuzione: " + (this.datiCCC.estremiAttoAmministrativo?.length > 0 ? this.datiCCC.estremiAttoAmministrativo : "n.d."), fontSize: 9, margin: [0, 5, 0, 0] },
    ]
    content.push(...otherContent);

    let styles = {
      styleTable: {
        margin: [0, 10, 0, 20],
        fontSize: 9
      }
    }

    const footer = (currentPage) => {
      let t = {
        layout: "noBorders",
        fontSize: 8,
        margin: [25, 0, 25, 0],
        table: {
          widths: ["*", "*"],
          body: [
            [
              { text: currentPage.toString() },
              { text: this.datePipe.transform(new Date(), 'dd/MM/yyyy') }
            ]
          ]
        }
      };
      return t;
    }
    this.toDataURL('assets/bozza.png').then(res => {
      let background = [
        {
          image: res,
          width: 600,
          lignment: 'center',
          margin: [0, 150, 0, 0],
          opacity: 0.2
        }
      ];
      let fileName: string = "CertificatoChiusuraConti_" + this.datiCCC.numeroDomanda + "_" + this.datePipe.transform(new Date(), "ddMMyyyy") + ".pdf";
      const documentDefinition = { info: { title: fileName }, pageMargins: [50, 100, 50, 50], content: content, footer: footer, styles: styles, background: isAnteprima ? background : null };
      const pdfDocGenerator = pdfMake.createPdf(documentDefinition);

      if (!isAnteprima) {
        //salvo ccc definitivo
        pdfDocGenerator.getBlob((blob) => {
          let file = new File([blob], fileName);
          this.loadedSalvaCCC = false;
          this.contoEconomicoWaService.salvaCCCDefinitivo(file, this.idProgetto, this.qteHtml.idQtesHtmlProgetto).subscribe(data => {
            if (data) {
              this.cccExists = true;
              this.showMessageSuccess("Salvataggio del certificato chiusura conti avvenuto con successo.");
              pdfDocGenerator.open();
            } else {
              this.showMessageError(["Errore durante il salvataggio del certificato chiusura conti."]);
            }
            this.loadedSalvaCCC = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError(["Errore durante il salvataggio del certificato chiusura conti."]);
            this.loadedSalvaCCC = true;
          });
        });
      } else {
        pdfDocGenerator.open();
      }
    });

  }

  visualizzaCCC() {

    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.loadedDownload = false;
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), this.idDocumentoIndexCCC.toString()).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        let comodo = new Array<any>();
        comodo.push(nome);
        comodo.push(this.idDocumentoIndexCCC);
        comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(null, nome, null, null, null, null, null, this.idDocumentoIndexCCC.toString(), null)]));
        comodo.push(this.configService.getApiURL());

        this.dialog.open(AnteprimaDialogComponent, {
          data: comodo,
          panelClass: 'anteprima-dialog-container'
        });
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore in fase di download del file"]);
      this.loadedDownload = true;
    });

  }

  downloadCCC() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.loadedDownload = false;
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), this.idDocumentoIndexCCC.toString()).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore in fase di download del file"]);
      this.loadedDownload = true;
    });

  }

  getColumnDesc(colName: string) {
    switch (colName) {
      case this.columns[0]:
        return "Q.E. DI PROGETTO";
      case this.columns[1]:
        return "Q.E. DI AGGIUDICAZIONE DEFINITIVA";
      case this.columns[2]:
        return "Q.E. DI RENDICONTAZIONE FINALE";
      case this.columns[3]:
        return "Q.E. VARIANTE 1";
      case this.columns[4]:
        return "Q.E. VARIANTE 2";
      default:
        return "";
    }
  }

  checkWarnings() {
    let warningElements = document.getElementsByClassName("border-color-orange");
    if (warningElements && warningElements.length > 0) {
      for (let dim: number = warningElements.length; dim > 0; dim--) {
        warningElements[0].classList.remove("border-color-orange");
      }
    }
    let warnings: string[] = ["Attenzione:"];
    for (let pre of this.columns) {
      let preWarnings: string[] = [this.getColumnDesc(pre)];
      const su = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "SU"))?.value) || 0;
      const sp = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "SP"))?.value) || 0;
      if (sp > +((su * 45 / 100).toFixed(2))) {  //IL CONTROLLO NON DEVE ESSERE BLOCCANTE: mostro warning
        document.getElementById(pre + "SP").classList.add("border-color-orange");
        preWarnings.push("- la S.P. è maggiore del 45% della S.U.");
      }

      if (preWarnings.length > 1) {
        warnings.push(...preWarnings);
      }
    }
    if (warnings.length > 1) {
      this.showMessageWarning(warnings);
    }
  }

  validaCampi() {
    //elimino gli errori vecchi
    let errorElements = document.getElementsByClassName("border-color-red");
    if (errorElements && errorElements.length > 0) {
      for (let dim: number = errorElements.length; dim > 0; dim--) {
        errorElements[0].classList.remove("border-color-red");
      }
    }

    //verifico i nuovi errori
    let errors: string[] = ["Attenzione! Sono presenti i seguenti errori:"];
    let i: number = 0;
    for (let pre of this.columns) {
      let preErrors: string[] = [this.getColumnDesc(pre)];
      let preWarnings: string[] = [this.getColumnDesc(pre)];
      const cr = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "CR"))?.value) || 0;
      const st1 = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "ST1"))?.value) || 0;
      const st2 = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "ST2"))?.value) || 0;
      if (cr <= 1000000) {
        if (st1 > +((cr * 19 / 100).toFixed(2))) {
          document.getElementById(pre + "ST1").classList.add("border-color-red");
          preErrors.push("- le Spese Tecniche (b1) devono essere minori o uguale del 19% del C.R.");
        }
      } else {
        if (st1 > +((cr * 16 / 100).toFixed(2))) {
          document.getElementById(pre + "ST1").classList.add("border-color-red");
          preErrors.push("- le Spese Tecniche (b1) devono essere minori o uguale del 16% del C.R.");
        }
      }
      if (cr <= 500000) {
        if (st2 > +((cr * 19 / 100).toFixed(2))) {
          document.getElementById(pre + "ST2").classList.add("border-color-red");
          preErrors.push("- le Spese Tecniche (b2) devono essere minori o uguale del 19% del C.R.");
        }
      } else {
        if (st2 > +((cr * 16 / 100).toFixed(2))) {
          document.getElementById(pre + "ST2").classList.add("border-color-red");
          preErrors.push("- le Spese Tecniche (b2) devono essere minori o uguale del 16% del C.R.");
        }
      }
      //(**) La somma di Spese Tecniche (b) + Altri Oneri Complementari (c) + Imprevisti (d) non deve superare il 30% del Costo di Realizzazione Tecnica (C.R.)
      const aoc = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "AOC"))?.value) || 0;
      const impr = this.sharedService.getNumberFromFormattedString((<HTMLInputElement>document.getElementById(pre + "IMPR"))?.value) || 0;
      if ((st1 + st2 + aoc + impr) > +((cr * 30 / 100).toFixed(2))) {
        document.getElementById(pre + "ST1").classList.add("border-color-red");
        document.getElementById(pre + "ST2").classList.add("border-color-red");
        document.getElementById(pre + "AOC").classList.add("border-color-red");
        if (document.getElementById(pre + "IMPR")) {
          document.getElementById(pre + "IMPR").classList.add("border-color-red");
        }
        preErrors.push("- la somma di Spese Tecniche (b1 + b2) + Altri Oneri Complementari (c) + Imprevisti (d) non deve superare il 30% del Costo di Realizzazione Tecnica (C.R.)");
      }
      console.log(this.elencoColonneQte[i]);
      let atto = (<HTMLInputElement>document.getElementById(pre + "Atto"));
      if (this.elencoColonneQte[i].isAttoPopolato && (!atto?.value || atto.value.length === 0)) {
        atto.classList.add("border-color-red");
        preErrors.push("- l'atto di approvazione QTES non può essere cancellato");
      }

      if (preErrors.length > 1) {
        errors.push(...preErrors);
      }
      i++;
    }


    let inputDecimal: any = document.getElementsByClassName("decimal");
    const regex = new RegExp('^[-]?([0-9]+[\\.])*[0-9]*([,][0-9]+)?$');
    let isErrorNotValid: boolean;
    for (let i = 0; i < inputDecimal.length; i++) {
      if (inputDecimal[i].value) {
        let res: boolean = regex.test(inputDecimal[i].value);
        if (!res) {
          inputDecimal[i].classList.add("border-color-red");
          if (!isErrorNotValid) {
            errors.push("Sono presenti campi numerici non validi.");
            isErrorNotValid = true;
          }
        }
      }
    }
    this.checkWarnings();
    if (errors.length > 1) {
      this.showMessageError(errors);
      return false;
    }
    return true;
  }

  getHTMLInputElementValueById(id: string): string {
    let field = <HTMLInputElement>document.getElementById(id);
    return field?.value;
  }

  getHTMLInputElementCheckedById(id: string): boolean {
    let field = <HTMLInputElement>document.getElementById(id);
    return field?.checked || false;
  }

  popolaDatiQteRequest() {
    let datiQte = new Array<DatiQteDTO>();
    let i: number = 0;
    for (let colonna of this.elencoColonneQte) {
      if (!this.isVariante1Visible && colonna.descBreveColonnaQtes === Constants.DESC_BREVE_COLONNA_QTE_VAR1) {
        console.log("Variante 1 non visibile, non la salvo su db");
      } else if (!this.isVariante2Visible && colonna.descBreveColonnaQtes === Constants.DESC_BREVE_COLONNA_QTE_VAR2) {
        console.log("Variante 2 non visibile, non la salvo su db");
      } else {
        //dati economici
        let importiFonti: Array<NumberDescBreve> = new Array<NumberDescBreve>();
        if (i < this.columns.length) {
          for (let row of this.elencoIdDatiEconomiciHTML) {
            let importoFormatted: string = this.getHTMLInputElementValueById(this.columns[i] + "ContoEcon" + row.id);
            let importo: number = this.sharedService.getNumberFromFormattedString(importoFormatted);
            let impF: NumberDescBreve = { number: importo, descBreve: row.descBreve };
            importiFonti.push(impF);
          }
        }
        //tipo intervento
        let tipiIntervento: Array<CheckDescBreve> = new Array<CheckDescBreve>();
        if (i < this.columns.length) {
          for (let row of this.elencoIdTipoIntervHTML) {
            let checked: boolean = this.getHTMLInputElementCheckedById(this.columns[i] + "TipoInter" + row.id);
            let check: CheckDescBreve = { check: checked, descBreve: row.descBreve };
            tipiIntervento.push(check);
          }
        }
        //altri valori
        let altriValori: Array<NumberDescBreve> = new Array<NumberDescBreve>();
        if (i < this.columns.length) {
          for (let row of this.elencoIdAltriValoriHTML) {
            let valueFormatted: string = this.getHTMLInputElementValueById(this.columns[i] + row.id);
            let value: number = this.sharedService.getNumberFromFormattedString(valueFormatted);
            let v: NumberDescBreve = { number: value, descBreve: row.descBreve };
            altriValori.push(v);
          }
        }
        //dati
        let estremiAttoApprovazione = this.getHTMLInputElementValueById(this.elencoIdAttoHTML[i]);
        let estremiAttoApprovazioneCert = this.getHTMLInputElementValueById(this.elencoIdAttoCertHTML[i]);

        let dato: DatiQteDTO = {
          idColonnaQtes: colonna.idColonnaQtes,
          estremiAttoApprovazione: estremiAttoApprovazione,
          estremiAttoApprovazioneCert: estremiAttoApprovazioneCert,
          importiFonti: importiFonti,
          tipiIntervento: tipiIntervento,
          altriValori: altriValori
        }
        datiQte.push(dato);
      }
      i++;
    }
    return datiQte;
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();

    if (!this.validaCampi()) {
      return;
    }

    //se sto salvando un atto di approvazione per la prima volta mostro messaggio
    let chiediConferma: boolean;
    let colonna: QteFaseDTO;
    let i: number = 0;
    for (let column of this.columns) {
      let idAtto: string = column + "Atto";
      if ((<HTMLInputElement>document.getElementById(idAtto))?.value?.length > 0
        && !this.elencoColonneQte[i].isAttoPopolato) {
        chiediConferma = true;
        colonna = this.elencoColonneQte[i];
      }
      i++;
    }
    if (chiediConferma) {
      let message: string = "Attenzione! Procedendo con il salvataggio dell'atto di approvazione della colonna " + colonna.descColonnaQtes + " i dati della colonna stessa non saranno più modificabili";
      if (colonna.descBreveColonnaQtes === Constants.DESC_BREVE_COLONNA_QTE_REND_3) {
        message = "Attenzione! Procedendo con il salvataggio dell'atto di approvazione della colonna " + colonna.descColonnaQtes + " i dati della colonna stessa e delle eventuali varianti non saranno più modificabili";
      }
      let dialogRef = this.dialog.open(ConfermaWarningDialogComponent, {
        width: '60%',
        data: {
          title: "Conferma",
          messageWarning: message

        }
      });
      dialogRef.afterClosed().subscribe(res => {
        if (res) {
          this.salvaQte();
        }
      });
    } else {
      this.salvaQte();
    }
  }

  salvaQte() {
    updateValues();
    const nativeElement = this.stampaPDF.nativeElement;
    this.htmlContent = nativeElement.innerHTML;

    let request: QteProgettoDTO = {
      idQtesHtmlProgetto: this.qteHtml.idQtesHtmlProgetto, //null se nuovo inserimento
      htmlQtesProgetto: this.htmlContent,
      idProgetto: this.idProgetto,
      datiQte: this.popolaDatiQteRequest()
    }
    this.loadedSalva = false;
    this.contoEconomicoWaService.salvaQteProgetto(request).subscribe(res => {
      if (res) {
        if (res.esito) {
          this.showMessageSuccess(res.messaggio);
          this.loadQte();
        } else {
          this.showMessageError([res.messaggio]);
        }
      } else {
        this.showMessageError(["Errore durante il salvataggio del quadro tecnico economico."]);
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore durante il salvataggio del quadro tecnico economico."]);
      this.loadedSalva = true;
    });
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.resetMessageWarning();
    this.resetMessageSuccess();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_QTES).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError(["ATTENZIONE: non è stato possibile chiudere l'attività."]);
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


  contoEconomico() {
    this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
      width: "90%",
      maxHeight: '90%',
      data: {
        idBandoLinea: this.idBando,
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

  showMessageError(msg: string[]) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string[]) {
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
    if (!this.loadedCodiceProgetto || !this.loadedModelloQte || !this.loadedSalva
      || !this.loadedChiudiAttivita || !this.loadedDatiCCC || !this.loadedSalvaCCC
      || !this.loadedIdDocumentoIndexCCC || !this.loadedDownload) {
      return true;
    }
    return false;
  }

}
