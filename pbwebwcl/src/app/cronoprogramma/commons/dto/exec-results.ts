import {KeyValue} from "./key-value";

export class ExecResults{
  constructor(public resultCode: string,
              public fldErrors: Array<KeyValue>,
              public globalErrors: Array<string>,
              public globalMessages: Array<string>,
              public model: any) {
  }
}
