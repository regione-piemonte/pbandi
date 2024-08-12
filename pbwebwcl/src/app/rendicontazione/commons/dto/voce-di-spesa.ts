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
