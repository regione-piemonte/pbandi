/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { xsltProcess, xmlParse } from 'xslt-processor';
import { ViewEncapsulation } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ArchivioFileService } from './../../services/archivio-file.service';
import { InfoDocumentoVo } from './../../commons/infoDocumento-vo';
import { animate, state, style, transition, trigger } from '@angular/animations';


@Component({
  selector: 'app-anteprima-dialog',
  templateUrl: './anteprima-dialog.component.html',
  styleUrls: ['./anteprima-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None,   //PK : da mettere affinche vengano applicate le classi degi stili al parametro "html" 
  /*animations: [
    // Each unique animation requires its own trigger. The first argument of the trigger function is the name
    trigger('rotatedState', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated90', style({ transform: 'rotate(90deg)' })),
      state('rotated180', style({ transform: 'rotate(180deg)' })),
      state('rotated270', style({ transform: 'rotate(270deg)' })),
      //  transition('rotated => default', animate('400ms ease-out')),
      // transition('default => rotated', animate('400ms ease-in'))
    ])
  ]*/
})
export class AnteprimaDialogComponent implements OnInit {

  apiURL: string;

  pdfSrc = "";

  html: Document;      // elemento HTML trasformato (xml+xslt) da visualizzare
  xsltString: string;  // rimane in assets/xslt/ .... TODO si potrebbe portare su DB
  xmlString: string;   // il file della fattura e' un xml e va caricare dallo storage
  pdfFile: boolean;
  leftArrow: boolean;
  rightArrow: boolean;
  nomeFile: string;
  idDocumentoIndex: string;
  tipo: string;
  isFirstFile: boolean;
  isLastFile: boolean;
  dataSourceTable: MatTableDataSource<InfoDocumentoVo>;
  isLoading: boolean;
  anteprimaNonDisponibile: boolean;
  gradi: number = 0;
  //state: string = 'default';
  zoom: number = 1;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<AnteprimaDialogComponent>,
    private httpClient: HttpClient,
    private _snackBar: MatSnackBar,
    private archivioFileService: ArchivioFileService,
    @Inject(MAT_DIALOG_DATA) public params: Array<any>
  ) { }

  generaFattura() {
    // PK : Nel xml delle fatture elettroniche usano un namespace non predicibile
    // Nella file xsl sono definiti i namespace a,b,c,d
    // Cerco il namespace ignoto nella fattura e se non corrisponde a nessuno dei 4 noti lo sostituisco con a.

    //<ns3:FatturaElettronica xmlns:n.....
    var index = this.xmlString.indexOf(":FatturaElettronica ");
    //console.log("index of FatturaElettronica="+index);

    var newXMLstring = this.xmlString;

    if (index > 10) {
      //vado indietro sino al primo <
      var indexMinus = this.xmlString.indexOf("<", index - 10);
      //console.log("index of < ="+indexMinus);

      // namespaceString = namespace definito nella fattura
      var namespaceString = this.xmlString.substring(indexMinus + 1, index);
      console.log("namespace in fattura =" + namespaceString);

      if (namespaceString == 'a' || namespaceString == 'b' || namespaceString == 'c' || namespaceString == 'd') {
        // namespace valido, proseguo.
        console.log('namespace valido');
      } else {
        // namespace NON valido, lo sostituisco con "a"
        console.log('namespace NON riconosciuto, diverso da (a,b,c,d)');
        // sostituisco tag di apertura
        newXMLstring = this.xmlString.replace("<" + namespaceString + ":", "<a:");
        // sostituisco tag di chiusura
        newXMLstring = newXMLstring.replace("</" + namespaceString + ":", "</a:");
        //console.log("newXMLstring ="    + newXMLstring); 
      }
    } else {
      console.log('String ":FatturaElettronica " NON trovata o in posizione errata');
    }
    if (xmlParse(this.xsltString) && xmlParse(newXMLstring)) {
      this.html = xsltProcess(xmlParse(newXMLstring), xmlParse(this.xsltString));
    }

    // console.log('------------------------------------------------------');
    // console.log( this.html );
    console.log('------------------------------------------------------');
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.nomeFile = this.params[0];
    this.idDocumentoIndex = this.params[1];
    this.dataSourceTable = this.params[2];
    this.apiURL = this.params[3];
    this.tipo = this.params[4] && this.params[4].length ? this.params[4] : null;
    if (this.isFileWithPreview(this.nomeFile)) {
      if (this.isPdfFile(this.nomeFile)) {
        this.loadPdfFile();
      } else {
        this.pdfFile = false;
        this.loadFiles();
      }
    } else {
      this.anteprimaNonDisponibile = true;
      this.pdfFile = false;
      this.isLoading = false;
    }

    this.leftArrow = !this.checkIsFirstFile();
    this.rightArrow = !this.checkIsLastFile();

    //console.log('ngOnInit xmlString='+this.xmlString);
  }

  loadPdfFile() {
    this.pdfFile = true;
    this.isLoading = true;
    this.archivioFileService.leggiFile(this.apiURL, this.idDocumentoIndex).subscribe(res => {
      if (typeof (FileReader) !== 'undefined') {
        let reader = new FileReader();

        reader.onload = (e: any) => {
          this.pdfSrc = e.target.result;
        };

        reader.readAsArrayBuffer(new File([res], this.nomeFile, { type: "", lastModified: Date.now() }));
      }
      this.isLoading = false;
    }, error => {
      this.isLoading = false;
      this.openSnackBar("Errore in fase di download del file");
    });
  }

  showError(event) {
    console.log(event, 'errorCalback')
    if (event) {
      this.showMessageError("Struttura file non valida.");
    }
  }

  checkIsFirstFile() {
    var firstFileOfArrayPassed = false;
    for (var item of this.dataSourceTable.data) {
      if (this.isFileWithPreview(item.nome)) {
        if (item.idDocumentoIndex == this.idDocumentoIndex && (!this.tipo || (item.tipo === this.tipo))) { //controllo che il file appartenga alla stessa sezione (per validazione, esame documento)
          if (firstFileOfArrayPassed) {
            return false;
          } else {
            return true;
          }
        }
        firstFileOfArrayPassed = true;
      }
    }
  }

  checkIsLastFile() {
    var checkRemainingItems = false;
    var isLast = true;
    for (var item of this.dataSourceTable.data) {
      if (this.isFileWithPreview(item.nome)) {
        if (checkRemainingItems) {
          isLast = false;
        } else {
          if (item.idDocumentoIndex == this.idDocumentoIndex && (!this.tipo || (item.tipo === this.tipo))) { //controllo che il file appartenga alla stessa sezione (per validazione, esame documento)
            checkRemainingItems = true;
          }
        }
      }
    }

    return isLast;
  }

  annulla() {
    this.dialogRef.close({ data: null });
  }

  isPdfFile(nomeFile: string) {
    var splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF") {
      return true;
    } else {
      if (splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
        if (splitted[splitted.length - 2] == "pdf" || splitted[splitted.length - 2] == "PDF") {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }

  isFileWithPreview(nomeFile: string) {
    var splitted = nomeFile.split(".");
    // .pdf,  .xml, .pdf.p7m, .xml.p7m
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml"
      || splitted[splitted.length - 1] == "XML" || ((splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M")
        && (splitted[splitted.length - 2] == "pdf" || splitted[splitted.length - 2] == "PDF")
        || (splitted[splitted.length - 2] == "xml" || splitted[splitted.length - 2] == "XML"))) {
      return true;
    } else {
      return false;
    }
  }

  async loadFiles() {
    this.isLoading = true;
    var xsltFileName2 = "template.xsl";

    // Get XSL stylesheet
    this.httpClient.get('assets/xslt/' + xsltFileName2, { responseType: 'text' })
      .subscribe(data => {
        if (data) {
          this.xsltString = data;
          //console.log('xsltString=' + this.xsltString);

          this.archivioFileService.leggiFile(this.apiURL, this.idDocumentoIndex).subscribe(res => {
            res.text().then(text => {

              var splitted = this.nomeFile.split(".");
              if (splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
                this.xmlString = this.getXmlByXmlP7m(text);
              } else {
                this.xmlString = text;
              }

              //console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
              //console.log('xmlString=' + this.xmlString);
              //console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
              this.isLoading = false;
              this.generaFattura();
            });
          }, error => {
            this.isLoading = false;
            this.openSnackBar("Errore in fase di download del file");
          });
        }
      });
  }

  findPreviousFile() {
    var idDocInd = "comodo";
    var nomeFil = "comodo";
    for (var item of this.dataSourceTable.data) {
      if (this.isFileWithPreview(item.nome)) {
        if (item.idDocumentoIndex == this.idDocumentoIndex) {
          var com = new Array<string>();
          com.push(nomeFil);
          com.push(idDocInd);
          return com;
        } else {
          idDocInd = item.idDocumentoIndex;
          nomeFil = item.nome;
        }
      }
    }
  }

  findNextFile() {
    var afterCurrentFile = false;

    for (var item of this.dataSourceTable.data) {
      if (this.isFileWithPreview(item.nome)) {
        if (afterCurrentFile) {
          var com = new Array<string>();
          com.push(item.nome);
          com.push(item.idDocumentoIndex);
          return com;
        } else {
          if (item.idDocumentoIndex == this.idDocumentoIndex) {
            afterCurrentFile = true;
          }
        }
      }
    }
  }

  leftButton() {
    this.resetMessageError();
    this.pdfSrc = null;
    this.nomeFile = this.findPreviousFile()[0];
    this.idDocumentoIndex = this.findPreviousFile()[1]

    if (this.isPdfFile(this.nomeFile)) {
      this.loadPdfFile();
    } else {
      this.pdfFile = false;
      this.loadFiles();
    }

    this.leftArrow = !this.checkIsFirstFile();
    this.rightArrow = !this.checkIsLastFile();
    // this.state = "default";
    this.gradi = 0;
    this.zoom = 1;
  }

  rightButton() {
    this.resetMessageError();
    this.pdfSrc = null;
    this.nomeFile = this.findNextFile()[0];
    this.idDocumentoIndex = this.findNextFile()[1]

    if (this.isPdfFile(this.nomeFile)) {
      this.loadPdfFile();
    } else {
      this.pdfFile = false;
      this.loadFiles();
    }

    this.leftArrow = !this.checkIsFirstFile();
    this.rightArrow = !this.checkIsLastFile();
    // this.state = "default";
    this.gradi = 0;
    this.zoom = 1;
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  /* XML / XML.P7M READER */
  getXmlByXmlP7m(xmlP7m: string) {
    var responseText = xmlP7m;
    var start = responseText.indexOf('<?xml'); 	// serve nel caso di P7M per escludere tutto il codice relativo alla firma elettronica
    var end = responseText.indexOf('FatturaElettronica>');
    responseText = responseText.substring(start, end + 19);
    responseText = this.pulisciXML(responseText);
    return responseText;
  }

  // Codice per mettere una pezza all'errore dei caratteri strani con p7m (vedi funzione PHP in fondo al file).
  pulisciXML(input) {
    input = input.replace(/\u0004.\u0003N/, '');		// . indica un carattere qualsiasi.
    var output = "";
    for (let i = 0; i < input.length; i++) {
      var unicode = input.charCodeAt(i);
      if ((unicode >= 31 && unicode <= 127) || unicode == 10 || unicode == 13) {
        output += input.charAt(i);
      }
    }
    return output;
  }

  rotateRight() {
    if (this.gradi < 270) {
      this.gradi += 90;
    } else {
      this.gradi = 0;
    }
    /* if (!this.isPdfFile(this.nomeFile)) {
       this.setState();
     }*/
  }

  rotateLeft() {
    if (this.gradi > 0) {
      this.gradi -= 90;
    } else {
      this.gradi = 270;
    }
    /*if (!this.isPdfFile(this.nomeFile)) {
      this.setState();
    }*/
  }

  /* setState() {
     switch (this.gradi) {
       case 90:
         this.state = "rotated90";
         break;
       case 180:
         this.state = "rotated180";
         break;
       case 270:
         this.state = "rotated270";
         break;
       default:
         this.state = "default";
         break;
     }
   }*/

  zoomIn() {
    this.zoom++;
  }

  zoomOut() {
    if (this.zoom > 1) {
      this.zoom--;
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
}
