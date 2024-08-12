export class ApiMessage {
    constructor(
        public code: number,
        public message: string,
        public error: boolean
    ) { }
}