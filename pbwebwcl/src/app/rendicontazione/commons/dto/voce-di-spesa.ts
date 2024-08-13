/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class VoceDiSpesaPadre {
	constructor(
		public idVoceDiSpesa: number,
		public idRigoContoEconomico: number,
		public descVoceDiSpesa: string,
		public importoAmmessoFinanziamento: number,
		public importoResiduoAmmesso: number,
		public idTipologiaVoceDiSpesa: number,
		public vociDiSpesaFiglie: Array<VoceDiSpesaFiglia>,
	) {}
}

export class VoceDiSpesaFiglia {
	constructor(public idVoceDiSpesa: number, public idRigoContoEconomico: number, public descVoceDiSpesa: string, public importoAmmessoFinanziamento: number, public importoResiduoAmmesso: number) {}
}
