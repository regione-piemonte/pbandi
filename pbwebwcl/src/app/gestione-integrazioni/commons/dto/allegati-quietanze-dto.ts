import { QuietanzaDTO } from "./quietanza-dto";

export interface AllegatiQuietanzeDTO extends QuietanzaDTO {
    nomiAllegati: Array<string>;
}