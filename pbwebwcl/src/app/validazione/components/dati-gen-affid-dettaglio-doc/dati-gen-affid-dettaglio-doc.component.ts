import { Component, Input, OnInit } from '@angular/core';
import { NuovoDocumentoDiSpesaDTO } from '../../commons/dto/nuovo-documento-di-spesa-dto';

@Component({
  selector: 'app-dati-gen-affid-dettaglio-doc',
  templateUrl: './dati-gen-affid-dettaglio-doc.component.html',
  styleUrls: ['./dati-gen-affid-dettaglio-doc.component.scss']
})
export class DatiGenAffidDettaglioDocComponent implements OnInit {

  @Input("docSpesa") docSpesa: NuovoDocumentoDiSpesaDTO;
  @Input("isBR79") isBR79: boolean;

  constructor() { }

  ngOnInit(): void {
  }

}
