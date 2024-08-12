import { Injectable } from '@angular/core';
import { Workbook } from "exceljs";
import { DatePipe } from "@angular/common";
import * as fs from 'file-saver-es';
import { ElencoDocumentiSpesaItem } from '../commons/dto/elenco-documenti-spesa-item';
import { SharedService } from 'src/app/shared/services/shared.service';


@Injectable()
export class ExcelRendicontazioneService {

  constructor(private sharedService: SharedService) { }

  generaExcel(elenco: Array<ElencoDocumentiSpesaItem>) {
    //const title = 'Report Proposta Certificazione';
    const header = ['Tipologia', 'Dig/cart', 'Con allegati', 'Estremi', 'Denominazione fornitore', 'Progetto', 'Stato', 'Totale documento', 'Validato', 'Rendicontabile associato'];
    // Crea workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('reportDocumentiDiSpesa');

    //const titleRow = worksheet.addRow([title]);
    //titleRow.font = { name: 'Comic Sans MS', family: 4, size: 14, bold: true };
    //worksheet.addRow([]);
    //const subTitleRow = worksheet.addRow(['Date : ' + this.datePipe.transform(new Date(), 'medium')]);
    // Blank Row
    // worksheet.addRow([]);
    // Add Header Row
    worksheet.addRow(header);
    const data = [];
    elenco.map(p => {
      data.push([p.tipologia, p.tipoInvio === "D" ? "Digitale" : "Cartaceo", p.allegatiPresenti ? "Sì" : "No", p.estremi, p.fornitore, p.progetti, p.stato,
      p.importo !== null && p.importo !== undefined ? this.sharedService.formatValue(p.importo.toString()) : null, p.validato !== null && p.validato !== undefined ? this.sharedService.formatValue(p.validato.toString()) : null, p.importi]);
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
    worksheet.getColumn(8).alignment = { horizontal: 'right' };
    worksheet.getColumn(9).alignment = { horizontal: 'right' };
    worksheet.getColumn(10).alignment = { horizontal: 'right' };

    for (let i = 0; i < worksheet.columns.length; i += 1) {
      let dataMax = 0;
      const column = worksheet.columns[i];
      for (let j = 1; j < column.values.length; j += 1) {
        let columnLength ;
        columnLength ? column.values[j].toString().length : null;
       // const columnLength = column.values[j].toString().length;
        if (columnLength > dataMax) {
          dataMax = columnLength;
        }
      }
      column.width = dataMax < 10 ? 10 : dataMax + 5;
    }

    // worksheet.mergeCells(`A${headerRow.number}:F${headerRow.number}`);
    let fileName: string = "ReportDocumentiDiSpesa.xlsx";
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, fileName);
    });

  }
}
