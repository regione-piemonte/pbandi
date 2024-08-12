import { AppaltoProgetto } from "./appalto-progetto";

export class InizializzaPopupChiudiValidazioneDTO {
    constructor(
        public note: string,
        public checkDsIntegrativaVisibile: boolean,
        public checkDsIntegrativaSelezionatoENonModificabile: boolean,
        public messaggi: Array<string>,
        public warningImportoMaggioreAmmesso: boolean,
        public appalti: Array<AppaltoProgetto>
    ) { }
}