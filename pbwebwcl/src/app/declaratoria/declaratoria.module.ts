import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MaterialModule } from "@pbandi/common-lib";
import { SharedModule } from "../shared/shared.module";
import { DeclaratoriaComponent } from "./components/declaratoria/declaratoria.component";
import { DeclaratoriaService } from "./services/declaratoria.service";

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
    ],
    exports: [
        DeclaratoriaComponent
    ],
    declarations: [
        DeclaratoriaComponent
    ],
    entryComponents: [
    ],
    providers: [
        DeclaratoriaService
    ]
})
export class DeclaratoriaModule { }
