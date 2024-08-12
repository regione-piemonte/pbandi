import { DatiDeclaratoriaDTO } from "../dto/dati-declaratoria-dto";
import { TipoAllegatoDTO } from "../dto/tipo-allegato-dto";

export class AnteprimaDichiarazioneDiSpesaRequest {
    constructor(
        public idBandoLinea: number,
        public idProgetto: number,
        public idProgettoContributoPiuGreen: number,				// valorizzato solo in caso di progetti +Green.
        public idSoggetto: number,
        public dataLimiteDocumentiRendicontabili: Date,
        public codiceTipoDichiarazioneDiSpesa: string,			// I (intermedia), F (finale), IN (integrativa).
        public idRappresentanteLegale: number,
        public idDelegato: number,								// non obbligatorio.
        public idSoggettoBeneficiario: number,
        public importoRichiestaSaldo: number,                   // Campo per la DS Finale + Comunicazione Fine Progetto.
        public note: string,									// a video sono le Osservazioni. Campo per la DS Finale + Comunicazione Fine Progetto.
        public listaTipoAllegati: Array<TipoAllegatoDTO>
    ) { }
}


export class AnteprimaDichiarazioneDiSpesaCulturaRequest {
	constructor(
			public idBandoLinea: number,
			public idProgetto: number,
			public idProgettoContributoPiuGreen: number,				// valorizzato solo in caso di progetti +Green.
			public idSoggetto: number,
			public dataLimiteDocumentiRendicontabili: Date,
			public codiceTipoDichiarazioneDiSpesa: string,			// I (intermedia), F (finale), IN (integrativa).
			public idRappresentanteLegale: number,
			public idDelegato: number,								// non obbligatorio.
			public idSoggettoBeneficiario: number,
			public importoRichiestaSaldo: number,                   // Campo per la DS Finale + Comunicazione Fine Progetto.
			public note: string,									// a video sono le Osservazioni. Campo per la DS Finale + Comunicazione Fine Progetto.
			public listaTipoAllegati: Array<TipoAllegatoDTO>,
      public allegatiCultura : DatiDeclaratoriaDTO,
	) { }
}