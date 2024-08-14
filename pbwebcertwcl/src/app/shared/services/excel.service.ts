/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { ProgettoNuovaCertificazioneDTO } from "../../gestione-proposte/commons/dto/progetto-nuova-certificazione-dto";
import { Workbook } from "exceljs";
import { DatePipe } from "@angular/common";
import * as fs from 'file-saver-es';
import { ProgettoCertificazioneIntermediaFinaleDTO } from "../../gestione-proposte/commons/dto/progetto-certificazione-intermedia-finale-dto";
import { PropostaProgettoDTO } from "../../crea-proposta/commons/dto/proposta-progetto-dto";
import { ReportCampionamentoDTO } from "../../campionamento/commons/dto/report-campionamento-dto";


@Injectable()
export class ExcelService {

  constructor(private datePipe: DatePipe) { }

  generaExcel(idProposta: string, elencoProgetti: Array<ProgettoNuovaCertificazioneDTO>) {
    //const title = 'Report Proposta Certificazione';
    const header = ['Codice Progetto', 'Beneficiario', 'Imp certificato netto ADC', 'Imp certificato netto ADG', 'Note'];
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Sheet1');

    //const titleRow = worksheet.addRow([title]);
    //titleRow.font = { name: 'Comic Sans MS', family: 4, size: 14, bold: true };
    //worksheet.addRow([]);
    //const subTitleRow = worksheet.addRow(['Date : ' + this.datePipe.transform(new Date(), 'medium')]);
    // Blank Row
    // worksheet.addRow([]);
    // Add Header Row
    const headerRow = worksheet.addRow(header);
    // Cell Style : Fill and Border
    headerRow.eachCell((cell, number) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'FFFFFF00' },
        bgColor: { argb: 'FF0000FF' }
      };
      cell.border = { top: { style: 'thin' }, left: { style: 'thin' }, bottom: { style: 'thin' }, right: { style: 'thin' } };
    });

    const data = [[]];
    elencoProgetti.map(p => {
      data.push([p.codiceProgetto, p.denominazioneBeneficiario, p.importoCertificazioneNetto, p.impCertifNettoPremodifica, p.nota]);
    })
    worksheet.addRows(data);
    /*    data.forEach(d => {
          const row = worksheet.addRow(d);
          const qty = row.getCell(5);
          let color = 'FF99FF99';
          if (+qty.value < 500) {
            color = 'FF9999';
          }
          qty.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: color }
          };
        }
        );*/
    worksheet.getColumn(1).width = 30;
    worksheet.getColumn(2).width = 60;
    worksheet.getColumn(3).width = 30;
    worksheet.getColumn(4).width = 30;
    worksheet.getColumn(5).width = 30;

    // worksheet.mergeCells(`A${headerRow.number}:F${headerRow.number}`);
    let fileName: string = "Export" + "_" + idProposta;
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, fileName + '.xlsx');
    });

  }

  generaExcelIF(idProposta: string, elencoProgettiIF: Array<ProgettoCertificazioneIntermediaFinaleDTO>) {
    //const title = 'Report Proposta Certificazione';
    const header = ['Asse', 'Codice Progetto', 'Beneficiario', 'Importo pagamenti validati cum', 'Importo erogazioni cum',
      'Importo revoche rilevanti cum', 'Importo recuperi cum', 'Importo soppressioni cum', 'Certificato lordo cum',
      'Certificato netto cum', 'Certificato netto annuale', 'Colonna C'];
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Sheet1');

    //const titleRow = worksheet.addRow([title]);
    //titleRow.font = { name: 'Comic Sans MS', family: 4, size: 14, bold: true };
    // worksheet.addRow([]);
    // const subTitleRow = worksheet.addRow(['Date : ' + this.datePipe.transform(new Date(), 'medium')]);
    /* // Blank Row
     worksheet.addRow([]);*/
    // Add Header Row
    const headerRow = worksheet.addRow(header);
    // Cell Style : Fill and Border
    headerRow.eachCell((cell, number) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'FFFFFF00' },
        bgColor: { argb: 'FF0000FF' }
      };
      cell.border = { top: { style: 'thin' }, left: { style: 'thin' }, bottom: { style: 'thin' }, right: { style: 'thin' } };
    });

    const data = [];
    elencoProgettiIF.map(item => {
      data.push([item.asse, item.codiceProgetto, item.beneficiario, item.importoPagamValidCum, item.importoErogazioniCum,
      item.importoRevocheRilevCum, item.importoRecuperiCum, item.importoSoppressioniCum, item.certificatoLordoCumulato, item.certificatoNettoCumulato,
      item.importoCertifNettoAnnual, item.colonnaC]);
    })
    worksheet.addRows(data);
    /*    data.forEach(d => {
          const row = worksheet.addRow(d);
          const qty = row.getCell(5);
          let color = 'FF99FF99';
          if (+qty.value < 500) {
            color = 'FF9999';
          }
          qty.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: color }
          };
        }
        );*/
    worksheet.getColumn(1).width = 8;
    worksheet.getColumn(2).width = 25;
    worksheet.getColumn(3).width = 60;
    worksheet.getColumn(4).width = 25;
    worksheet.getColumn(5).width = 25;
    worksheet.getColumn(6).width = 20;
    worksheet.getColumn(7).width = 20;
    worksheet.getColumn(8).width = 20;
    worksheet.getColumn(9).width = 20;
    worksheet.getColumn(10).width = 20;
    worksheet.getColumn(11).width = 20;
    worksheet.getColumn(12).width = 20;


    // worksheet.mergeCells(`A${headerRow.number}:F${headerRow.number}`);
    let fileName: string = "Export" + "_" + idProposta;
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, fileName + '.xlsx');
    });
  }

  generaExcelPropostaProgetto(idProposta: number, elencoProgettiSospesi: Array<PropostaProgettoDTO>) {
    const header = ['Codice Progetto', 'Beneficiario', 'Importo certificato(ultima proposta approvata)', 'Pagamenti (IGRUE)', 'Importo revoche'];
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Sheet1');
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
    elencoProgettiSospesi.map(p => {
      data.push([p.codiceProgetto, p.denominazioneBeneficiario, p.importoCertificazioneNetto, p.importoPagamenti, p.importoRevoche]);
    })
    worksheet.addRows(data);

    worksheet.getColumn(1).width = 30;
    worksheet.getColumn(2).width = 60;
    worksheet.getColumn(3).width = 45;
    worksheet.getColumn(4).width = 30;
    worksheet.getColumn(5).width = 30;

    let fileName: string = "Export" + "_" + idProposta;
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, fileName + '.xlsx');
    });
  }

  /*  NON NECESSARIO: scarica excel riepilogativo della tabella
  scaricaExcelCampionamento(elencoReport: Array<ReportCampionamentoDTO>) {
    const title = 'Report Campionamento';
    const header = ['Nome File', 'Desc Normativa', 'Desc Anno Contabile', 'Data Campionamento'];
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Sheet1');

    const titleRow = worksheet.addRow([title]);
    titleRow.font = { name: 'Comic Sans MS', family: 4, size: 14, bold: true };
    worksheet.addRow([]);
    const subTitleRow = worksheet.addRow(['Date : ' + this.datePipe.transform(new Date(), 'medium')]);
    // Blank Row
    worksheet.addRow([]);
    // Add Header Row
    const headerRow = worksheet.addRow(header);
    // Cell Style : Fill and Border
    headerRow.eachCell((cell, number) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'FFFFFF00' },
        bgColor: { argb: 'FF0000FF' }
      };
      cell.border = { top: { style: 'thin' }, left: { style: 'thin' }, bottom: { style: 'thin' }, right: { style: 'thin' } };
    });

    const data = [[]];
    elencoReport.map(p => {
      data.push([p.nomeFile, p.descNormativa, p.descAnnoContabile, p.dataCampionamento]);
    })
    worksheet.addRows(data);
    data.forEach(d => {
      const row = worksheet.addRow(d);
      const qty = row.getCell(5);
      let color = 'FF99FF99';
      if (+qty.value < 500) {
        color = 'FF9999';
      }
      qty.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: color }
      };
    }
    );
    worksheet.getColumn(1).width = 60;
    worksheet.getColumn(2).width = 60;
    worksheet.getColumn(3).width = 25;
    worksheet.getColumn(4).width = 25;

    // worksheet.mergeCells(`A${headerRow.number}:F${headerRow.number}`);
    let fileName: string = "Export" + "_" + this.datePipe.transform(new Date()).toString();
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, fileName + '.xlsx');
    });
  }
  */
}
