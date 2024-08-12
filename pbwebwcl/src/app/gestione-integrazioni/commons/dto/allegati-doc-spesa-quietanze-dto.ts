import { AllegatiQuietanzeDTO } from "./allegati-quietanze-dto";
import { DocumentoDiSpesaSospesoDTO } from "./documento-di-spesa-sospeso-dto";

export interface AllegatiDocSpesaQuietanzeDTO extends DocumentoDiSpesaSospesoDTO {
    nomiAllegati: Array<string>;
    allegatiQuietanze: Array<AllegatiQuietanzeDTO>;
}