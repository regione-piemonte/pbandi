/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import {AppaltoDTO} from "../../appalti/commons/dto/appalto-dto";
import {DatePipe} from "@angular/common";
import * as fs from 'file-saver-es';
import {Workbook} from "exceljs";
import {Appalto} from "../../appalti/commons/dto/appalto";

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor(private datePipe: DatePipe) { }

  esportaDettaglioAppalti(elencoAppalti: Array<Appalto>, idProgetto: number) {
    const title = 'Report Appalti';
    const header = ['Data Prevista Inizio Lavori', 'Data Consegna Lavori',
      'Data Firma Contratto', 'Importo Contratto', 'Oggetto Appalto', 'Procedura Aggiudicazione'];
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Sheet1');

    const titleRow = worksheet.addRow([title]);
    titleRow.font = { name: 'Comic Sans MS', family: 4, size: 14, bold: true };
    worksheet.addRow([]);
   // const subTitleRow = worksheet.addRow(['Date : ' + this.datePipe.transform(new Date(), 'medium')]);
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
    elencoAppalti.map(p => {
      data.push([p.dtPrevistaInizioLavori, p.dtConsegnaLavori, p.dtFirmaContratto, p.importoContratto, p.oggettoAppalto, p.descrizioneProceduraAggiudicazione]);
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
    worksheet.getColumn(1).width = 30;
    worksheet.getColumn(2).width = 30;
    worksheet.getColumn(3).width = 30;
    worksheet.getColumn(4).width = 25;
    worksheet.getColumn(5).width = 60;
    worksheet.getColumn(6).width = 40;

    // worksheet.mergeCells(`A${headerRow.number}:F${headerRow.number}`);
    let fileName: string = "reportAppalti" + "" + idProgetto;
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, fileName + '.xlsx');
    });

  }
}
