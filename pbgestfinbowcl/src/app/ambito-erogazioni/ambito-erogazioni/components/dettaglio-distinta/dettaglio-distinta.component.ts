/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DistintaPropostaErogazioneVO } from '../../commons/distinta-proposta-erogazione-vo';
import { EstremiBancariVO } from '../../commons/estremo-bancari-vo';
import { DistinteResponseService } from '../../services/distinte-response.service';
import { DatiDistintaVO } from '../../commons/dati-distinta-vo';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ProgettoAllegatoVO } from '../../commons/progetto-allegato.vo';

@Component({
  selector: 'app-dettaglio-distinta',
  templateUrl: './dettaglio-distinta.component.html',
  styleUrls: ['./dettaglio-distinta.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioDistintaComponent implements OnInit {

  spinner: boolean = false;
  success: boolean = false;
  error: boolean = false;

  messaggioErrore: string;
  messaggioSuccesso: string;

  //valori ottenuti dall'URL
  idDistinta: string;

  //oggetti ottenuti da fetch data
  titoloBando: string;
  descModalitaAgevolazione: string;
  idModalitaAgevolazione: string;
  idModalitaAgevolazioneRif: string;
  
  isBozza: boolean = false;
  bancaTesoriera: string;
  listaBancaTesoriera: EstremiBancariVO[] = [];
  verificaPosizioneAll: boolean = true;

  //allegati
  progettoAllegatoVOList: ProgettoAllegatoVO[];

  //tabella
  displayedColumns: string[] = ["progetto", "denominazione", "beneficiario", "importoLordo", "importoIres", "importoNetto", "dataConcessione", "dataEsitoDS", "verificaPosizione", "flagFinistr"]; //"actions",
  listaOggetti: Array<DistintaPropostaErogazioneVO>;
  dataSource: MatTableDataSource<DistintaPropostaErogazioneVO>;

  //form
  myForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private resService: DistinteResponseService,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.spinner = true;
    this.removeMessageErrorAndSuccess();

    //creo form
    this.myForm = this.fb.group({
      descrizione: new FormControl({ disabled: true, value: '' }),
      bancaTesoriera: new FormControl({ disabled: true, value: '' })
    })

    //recupero parametri
    this.route.queryParams.subscribe(params => {
      this.idDistinta = params['idDistinta']
    });

    //fetchData
    if (this.idDistinta) {
      this.resService.fetchDataDettaglioDistinta(this.idDistinta).subscribe(data => {
        console.log("FETCH DATA DETTAGLIO DISTINTA", data);

        //dettaglio distinta
        this.titoloBando = data?.titoloBando;
        this.descModalitaAgevolazione = data?.descModalitaAgevolazione;
        this.idModalitaAgevolazione = data.idModalitaAgevolazione;
        this.idModalitaAgevolazioneRif = data.idModalitaAgevolazioneRif;

        if(data?.estremiBancariList?.length > 0) {
          this.myForm.get("bancaTesoriera").setValue(data?.estremiBancariVO);
          this.listaBancaTesoriera = data?.estremiBancariList;
        }

        /*
        if (data?.estremiBancariList?.length == 1) {
          this.myForm.get("bancaTesoriera").setValue(data?.estremiBancariList[0]);
          this.bancaTesoriera = data?.estremiBancariList[0].iban;
        }else {
          this.myForm.get("bancaTesoriera").setValue({});
          this.bancaTesoriera = "Nessuna banca trovata";
        }
        */

        this.isBozza = data?.bozza;

        this.myForm.get("descrizione").setValue(data?.descDistinta);
        this.myForm.get("bancaTesoriera").setValue(data?.estremiBancariVO);

        this.listaOggetti = data?.distintaPropostaErogazioneList;
        this.dataSource = new MatTableDataSource<DistintaPropostaErogazioneVO>(this.listaOggetti);
        this.listaOggetti.forEach(el => {
          if (el.verificaPosizione == false) {
            this.verificaPosizioneAll = false;
          }
        })

        //allegati
        this.progettoAllegatoVOList = data.progettoAllegatoVOList;
        this.progettoAllegatoVOList.forEach(element => {
          element.salvato = true;
          this.myForm.addControl("letteraAccompagnatoria" + element.codiceVisualizzatoProgetto, new FormControl(""));
          this.myForm.addControl("checklistInterna" + element.codiceVisualizzatoProgetto, new FormControl(""));
          this.myForm.addControl("reportValidazione" + element.codiceVisualizzatoProgetto, new FormControl(""));
          if(element.flagFinistr == "NO") {
            this.myForm.get("letteraAccompagnatoria" + element.codiceVisualizzatoProgetto).setValidators(Validators.required);
            this.myForm.get("checklistInterna" + element.codiceVisualizzatoProgetto).setValidators(Validators.required);
            this.myForm.updateValueAndValidity();
          }
        })
        this.myForm.disable();

        this.spinner = false;
      }, err => {
        this.spinner = false;
        this.showMessageError("Errore durante il caricamento dei dati");
        this.handleExceptionService.handleNotBlockingError(err);
      });
    }
  }

  goBack() {
    this.removeMessageErrorAndSuccess();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/elencoDistinte"], {});
  };

  compareEstremiBancari(o1: any, o2: any): boolean {
    return o1.idEstremiBancari === o2?.idEstremiBancari && o1.iban === o2?.iban;
  }

  //quando clicco su un progetto
  goToElencoDistinte(element: DistintaPropostaErogazioneVO) {

    this.removeMessageErrorAndSuccess();

    //mi ricollego al CDU2
    //secondo il CDU4 dovrei passare: idBando, idModalitaAgevolazione, idProgetto e idProposta
    //per come è stato costruito il CDU2 però passo solo idProposta
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_PROPOSTE + "/controlliPreErogazione"], {
      queryParams: {
        "idProposta": element.idPropostaErogazione
      }
    });

  }

  //allegati

  handleFileLetteraAccompagnatoria(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.visibilitaReport = true;
        element.salvato = false;
        element.letteraAccompagnatoria = files[0];
        console.log("Lettera accompagnatoria caricata", element.letteraAccompagnatoria)
      }
    });
  }
  eliminaLetteraAccompagnatoria(codiceVisualizzato: string, uploadFile: any) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        if (element.letteraAccompagnatoria?.idDocumentoIndex != null) {
          this.resService.deleteFile(element.letteraAccompagnatoria.idDocumentoIndex).subscribe();
        }
        element.letteraAccompagnatoria = null;
      }
    });
    uploadFile.value = '';
  }
  handleFileChecklistInterna(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.visibilitaChecklist = false;
        element.salvato = false;
        element.checklistInterna = files[0];
        console.log("Checklist interna caricata", element.checklistInterna);
      }
    });
  }
  eliminaChecklistInterna(codiceVisualizzato: string, uploadFile2: any) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        if (element.checklistInterna?.idDocumentoIndex != null) {
          this.resService.deleteFile(element.checklistInterna.idDocumentoIndex).subscribe();
        }
        element.checklistInterna = null;
      }
    });
    uploadFile2.value = '';
  }
  handleFileReportValidazione(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.visibilitaReport = true;
        element.salvato = false;
        element.reportValidazione = files[0];
        console.log("Report validazione caricata", element.reportValidazione);
      }
    });
  }
  eliminaReportValidazione(codiceVisualizzato: string, uploadFile3: any) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        if (element.reportValidazione?.idDocumentoIndex != null) {
          this.resService.deleteFile(element.reportValidazione.idDocumentoIndex).subscribe();
        }
        element.reportValidazione = null;
      }
    });
    uploadFile3.value = '';
  }
  
  toggleVisibilita(element: ProgettoAllegatoVO, ambito: number) {
    switch(ambito) {
      case 1: {
        element.visibilitaLettera = !element.visibilitaLettera;
        break;
      }
      case 2: {
        element.visibilitaReport = !element.visibilitaReport;
        break;
      }
      case 3: {
        element.visibilitaChecklist = !element.visibilitaChecklist;
        break;
      }
    }
  }

  modifica() {
    this.removeMessageErrorAndSuccess();

    this.myForm.enable();
  };

  async salvaAllegati() {
    this.progettoAllegatoVOList.forEach(element => {
      if(!element.salvato){
        if (element.letteraAccompagnatoria instanceof File) {
          this.resService.uploadFile(element.letteraAccompagnatoria, element.visibilitaLettera, 42, this.idDistinta, element.idProgetto).subscribe();
        }
        if (element.checklistInterna instanceof File) {
          this.resService.uploadFile(element.checklistInterna, element.visibilitaChecklist, 33, this.idDistinta, element.idProgetto).subscribe();
        }
        if (element.reportValidazione instanceof File) {
          this.resService.uploadFile(element.reportValidazione, element.visibilitaReport, 63, this.idDistinta, element.idProgetto).subscribe();
        }
      }else {
        if(element.letteraAccompagnatoria != null) {
          this.resService.updateVisibilita(element.letteraAccompagnatoria.idDocumentoIndex, element.visibilitaLettera).subscribe();
        }
        if(element.checklistInterna != null) {
          this.resService.updateVisibilita(element.checklistInterna.idDocumentoIndex, element.visibilitaChecklist).subscribe();
        }
        if(element.reportValidazione != null) {
          this.resService.updateVisibilita(element.reportValidazione.idDocumentoIndex, element.visibilitaReport).subscribe();
        }
      }
    });
  }

  async salva() {
    this.removeMessageErrorAndSuccess();
    this.spinner = true;

    await this.salvaAllegati();

    //recupero valore idTipoDistinta
    let idTipoDistinta: number = this.sceltaIdTipoDistinta();

    let datiDistintaVO: DatiDistintaVO = {
      idTipoDistinta: idTipoDistinta.toString(),
      idModalitaAgevolazione: this.idModalitaAgevolazione,
      descrizione: this.myForm.get("descrizione").value,
      idEstremiBancari: this.myForm.get("bancaTesoriera").value.idEstremiBancari,

      listaIdProposteErogazione: null,
    }

    if (!datiDistintaVO.descrizione) {
      this.spinner = false;
      this.showMessageError("Inserire la descrizione per salvare la distinta!");
    } else {
      this.resService.modificaDistinta(this.idDistinta, datiDistintaVO).subscribe(data => {
        console.log("DATA", data);
        this.spinner = false;

        if (data) {
          this.myForm.disable();
          this.showMessageSuccess("Dati modificati correttamente")
        } else {
          this.showMessageError("Errore durante il salvataggio dei dati")
        }
      }, err => {
        this.spinner = false;
        this.error = true;
        this.showMessageError("Errore durante la modifica della distinta!");
        this.handleExceptionService.handleNotBlockingError(err);
      });
    }
    setTimeout(() => {
      this.ngOnInit();
    }, 2000);
  }

  async avviaIter() {
    this.removeMessageErrorAndSuccess();
    this.spinner = true;

    await this.salvaAllegati();

    this.resService.checkAllegati(this.idDistinta).subscribe(data => {
      if (data) {
        this.resService.avviaIter(this.idDistinta).subscribe(data => {

          this.spinner = false;

          console.log("DATA", data)

          if (data == null) {
            this.showMessageSuccess("Avvia iter concluso correttamente correttamente")
          } else {
            this.showMessageError(data)
          }

        }, err => {
          this.spinner = false;
          this.error = true;
          this.messaggioErrore = "Errore durante l'avvia iter'!";
          console.log(err);
        });
      } else {
        this.spinner = false;
        this.showMessageError("Verificare gli allegati!");
      }
    });

    setTimeout(() => {
      this.ngOnInit();
    }, 2000);
  };

  sceltaIdTipoDistinta() {
    if (this.idModalitaAgevolazioneRif == '1') {
      return 6;
    }
    if (this.idModalitaAgevolazioneRif == '5') {
      return 7;
    }
    if (this.idModalitaAgevolazioneRif == '10') {
      return 8;
    }
  }

  showMessageError(mex: string) {
    this.error = true;
    this.messaggioErrore = mex;
  }
  showMessageSuccess(mex: string) {
    this.success = true;
    this.messaggioSuccesso = mex;
  }
  removeMessageErrorAndSuccess() {
    this.error = false;
    this.messaggioErrore = "";
    this.success = false;
    this.messaggioSuccesso = "";
  }

}
