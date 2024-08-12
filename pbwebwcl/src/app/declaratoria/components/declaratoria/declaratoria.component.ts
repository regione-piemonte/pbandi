import { SelectionModel } from '@angular/cdk/collections';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSelectChange } from '@angular/material/select';
import { MatTableDataSource } from '@angular/material/table';
import { RendicontazioneA20Service } from 'src/app/rendicontazione-a20/services/rendicontazione-a20.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
	selector: 'app-declaratoria',
	templateUrl: './declaratoria.component.html',
	styleUrls: ['./declaratoria.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DeclaratoriaComponent implements OnInit {
	@ViewChild('myTable', { static: false }) myTable: ElementRef;
	public subscribers: any;
	ritenutaIres = [
		'è assoggettato',
		'non è assoggettato in quanto l’ente richiedente è una ONLUS (organizzazione non lucrativa di utilità sociale) D.Lgs. 117/2017',
		'non è assoggettato in quanto l’ente richiedente non svolge, neppure occasionalmente, attività commerciale ai sensi dell’art. 55 del D.p.r. 917/1986',
		'non è assoggettato in quanto  il contributo è destinato ad attività istituzionale che non ha natura commerciale (si precisa che le entrate da sbigliettamento o altri ricavi rendono il contributo assoggettabile a ritenuta IRES)',
		' non è assoggettato in quanto l’ente è esente in virtù di espressa deroga ai sensi della legge',
	];

	iva = ['l’IVA costituisce un costo d’esercizio per l’ente e va conteggiata ai fini della determinazione del contributo', 'l’IVA non costituisce un costo d’esercizio per l’ente e viene recuperata'];

	collegiali = [
		'rispettano le previsioni in materia di gratuità di tutti gli organi di amministrazione ovvero che gli stessi percepiscono unicamente un gettone di presenza da una data anteriore al 31 maggio 2010 e che lo stesso è stato ridotto a non oltre 30 euro a seduta giornaliera, non rientrando quindi nell’esclusione operata dal comma 2 dell’art. 6, in merito agli enti che non possono ricevere, neanche indirettamente, contributi o utilità a carico delle finanze pubbliche',
		"non sono assoggettate alle disposizioni di cui al comma 2 dell’art. 6 del decreto legge 31 maggio 2010, n. 78, convertito in legge 30 luglio 2010, n. 122 in materia di gratuità di tutti gli organi di amministrazione ovvero di partecipazione ai medesimi remunerata con un gettone di presenza non superiore a 30 euro a seduta giornaliera, in quanto l'ente richiedente ha una delle seguenti forme giuridiche: Ente previsto nominativamente dal d.lgs. n. 300/1999 o dal d.lgs. n. 165/2001, Università, Ente o fondazione di ricerca o organismo equiparato, ONLUS, Associazione di promozione sociale, Società, Ente previdenziale ed assistenziale nazionale, Camera di commercio, Ente pubblico economico individuato con decreto del Ministero dell’Economia e Finanze, Ente indicato nella tabella C della legge finanziaria, Ente del Servizio Sanitario Nazionale",
	];
	documentoUnico = [
		"l'Ente impiega lavoratori subordinati o parasubordinati nello svolgimento delle proprie attività",
		"l'Ente non impiega lavoratori subordinati o parasubordinati nello svolgimento delle proprie attività",
		"l'Ente presieduto non è iscritto ad alcun Ente di previdenza e Assistenza, né all'INAIL",
	];
	contributiStrutture = ['non ha presentato', 'ha presentato e/o intende presentare'];

	contributiStataliComunitarie = ['non ha presentato', 'ha presentato e/o intende presentare'];

	contributiSuccessivi = [
		"non sono sostenute ai sensi delle leggi regionali 13/2018 (ecomusei), 24/1990 (SOMS), 28/1980 (Istituti Storici della Resistenza), 41/1985 (Luoghi della lotta di liberazione), per cui l'istanza di contributo PUO' essere presentata su questa linea di finanziamento",
		"sono sostenute ai sensi delle leggi regionali 13/2018 (ecomusei), 24/1990 (SOMS), 28/1980 (Istituti Storici della Resistenza), 41/1985 (Luoghi della lotta di liberazione), per cui l'istanza di contributo NON PUO' essere presentata su questa linea di finanziamento",
	];
	dataSourceStrutture;
  dataSourceStataleComunitaria;

	tabellaInizioComunitaria = [
		{
			struttura: '',
			programma: '',
			delete : false
		},
	];

	tabellaInizio = [
		{
			direzione: '',
			settore:'' ,
			normativa: '',
			delete : false
		},
	];

	loadedDeclaratoria: boolean = true;
	myForm: FormGroup;
	selection = new SelectionModel<any>(true, []);
	displayedColumnsStrutture: string[] = ['direzione', 'settore', 'normativa', 'actions'];
	displayedColumnsStataleComunitaria: string[] = ['a', 'b', 'actions'];
	data: { Direzione: number; Settore: string; Normativa: string }[];
	dati;
	selectedDirezione = new FormControl();
	selectedSettore = new FormControl();
	direzioni: any[];
	datiDeclaratoria: any;
	validFormDeclaratoria: boolean;
  	newArrayStataleComunitaria: { struttura: string; programma: string; delete:boolean  }[];
 	newArrayStrutture: { direzione: string; settore: string; normativa: string; delete:boolean }[];
	contributiStruttureVisibility: boolean;
	contributiStataliComunitarieVisibility:boolean;

	constructor(private formBuilder: FormBuilder, public rendicontazioneA20Service: RendicontazioneA20Service) {
		this.myForm = this.formBuilder.group({
			strutture: this.formBuilder.array([
				this.formBuilder.group({
					direzione: new FormControl('', Validators.required),
					settore: new FormControl('', Validators.required),
					normativa: new FormControl('', Validators.required),
				}),
			]),

			stataleComunitaria: this.formBuilder.array([
				this.formBuilder.group({
					struttura: new FormControl('', Validators.required),
					programma: new FormControl('', Validators.required),
				}),
			]),
			ritenutaIres: new FormControl('', Validators.required),
			iva: new FormControl('', Validators.required),
			collegiali: new FormControl('', Validators.required),
			documentoUnico: new FormControl('', Validators.required),
			contributiStataliComunitarie: new FormControl('', Validators.required),
			contributiStrutture: new FormControl('', Validators.required),
			contributiSuccessivi: new FormControl('', Validators.required),
		});
	}

	ngOnInit(): void {
		this.getDirezioniSettori();
		this.dataSourceStrutture = new MatTableDataSource(this.tabellaInizio);
    this.dataSourceStataleComunitaria = new MatTableDataSource(this.tabellaInizioComunitaria);
	}

	contributiStruttureChange(value){
		const struttureArray = this.myForm.get('strutture') as FormArray;
		if(value === "non ha presentato"){
			this.contributiStruttureVisibility = false; 
			 for(const childGroup of struttureArray.controls){
				childGroup.get('direzione')?.clearValidators(); 
				childGroup.get('settore')?.clearValidators(); 
				childGroup.get('normativa')?.clearValidators();
				childGroup.get('direzione')?.reset(); 
				childGroup.get('settore')?.reset(); 
				childGroup.get('normativa')?.reset();
				childGroup.get('direzione')?.updateValueAndValidity(); 
				childGroup.get('settore')?.updateValueAndValidity(); 
				childGroup.get('normativa')?.updateValueAndValidity(); 
			}
			
			 

		}else{
			this.contributiStruttureVisibility = true;
			for(const childGroup of struttureArray.controls){
				childGroup.get('direzione')?.setValidators([Validators.required]); 
				childGroup.get('settore')?.setValidators([Validators.required]); 
				childGroup.get('normativa')?.setValidators([Validators.required]);
				childGroup.get('direzione')?.updateValueAndValidity(); 
				childGroup.get('settore')?.updateValueAndValidity(); 
				childGroup.get('normativa')?.updateValueAndValidity(); 
			}
			
		}

		
		
	//	(value === "non ha presentato") ? this.contributiStruttureVisibility == true : this.contributiStruttureVisibility == false;
		
	}

	contributiStataliComunitarieChange(value){
		
		const struttureArray = this.myForm.get('stataleComunitaria') as FormArray;
		if(value === "non ha presentato"){
			this.contributiStataliComunitarieVisibility = false; 
			 for(const childGroup of struttureArray.controls){
				childGroup.get('struttura')?.clearValidators(); 
				childGroup.get('programma')?.clearValidators(); 
				childGroup.get('struttura')?.reset(); 
				childGroup.get('programma')?.reset(); 
				childGroup.get('struttura')?.updateValueAndValidity(); 
				childGroup.get('programma')?.updateValueAndValidity(); 
			}
			
			 

		}else{
			this.contributiStataliComunitarieVisibility = true;
			for(const childGroup of struttureArray.controls){
				childGroup.get('struttura')?.setValidators([Validators.required]); 
				childGroup.get('programma')?.setValidators([Validators.required]); 
				childGroup.get('struttura')?.updateValueAndValidity(); 
				childGroup.get('programma')?.updateValueAndValidity(); 
			}
			
		}

	}

	aggiungiRigaStrutture() {
		this.tabellaInizio.push({
			direzione: '',
			settore: '',
			normativa: '',
			delete : true,
		});
		this.dataSourceStrutture = new MatTableDataSource(this.tabellaInizio);
		const struttureFormArray = this.myForm.get('strutture') as FormArray;
		const nuovaRigaStrutture = this.formBuilder.group({
			direzione: new FormControl('', Validators.required),
			settore: new FormControl(null, Validators.required),
			normativa: new FormControl('', Validators.required),
		});
		struttureFormArray.push(nuovaRigaStrutture);
	}

	aggiungiRigaStataleComunitaria() {
		this.tabellaInizioComunitaria.push({
			struttura: '',
			programma: '',
			delete : true,
		});
		this.dataSourceStataleComunitaria = new MatTableDataSource(this.tabellaInizioComunitaria);
		const stataleComunitariaFormArray = this.myForm.get('stataleComunitaria') as FormArray;
		const nuovaRigaStataleComunitaria = this.formBuilder.group({
			struttura: new FormControl('', Validators.required),
			programma: new FormControl(null, Validators.required),
		});
		stataleComunitariaFormArray.push(nuovaRigaStataleComunitaria);
    
	}

	
	eliminaRigaStrutture(element) {
		this.newArrayStrutture = this.tabellaInizio.filter(item => item !== element);
		this.tabellaInizio = this.newArrayStrutture;
		this.dataSourceStrutture = new MatTableDataSource(this.newArrayStrutture);
		const struttureFormArray = this.myForm.get('strutture') as FormArray;
		const indexToRemove = struttureFormArray.controls.findIndex(item =>(item.value) === element);
		  struttureFormArray.removeAt(indexToRemove);
		  this.myForm.get('strutture').updateValueAndValidity();
	}
	
		eliminaRigaStataleComunitaria(element) {
		this.newArrayStataleComunitaria = this.tabellaInizioComunitaria.filter(item => item !== element);
		this.tabellaInizioComunitaria = this.newArrayStataleComunitaria;
		this.dataSourceStataleComunitaria = new MatTableDataSource(this.newArrayStataleComunitaria);
		const stataleComunitariaFormArray = this.myForm.get('stataleComunitaria') as FormArray;
		const indexToRemove = stataleComunitariaFormArray.controls.findIndex(item => JSON.stringify(item.value) === element);
		stataleComunitariaFormArray.removeAt(indexToRemove);
		this.myForm.get('stataleComunitaria').updateValueAndValidity();
	}

	onDirezioneChange(event: MatSelectChange, rowIndex: number): void {
		this.tabellaInizio[rowIndex].direzione = event.value;
	}

 	 onSettoreChange(event: MatSelectChange, rowIndex: number): void {
		this.tabellaInizio[rowIndex].settore = event.value;
	}

 	 onNormativaChange(event, rowIndex: number): void {
		this.tabellaInizio[rowIndex].normativa = event;
	}

	  onStrutturaChange(event, rowIndex: number): void {
		this.tabellaInizioComunitaria[rowIndex].struttura = event;
	}

	  onProgrammaChange(event, rowIndex: number): void {
		this.tabellaInizioComunitaria[rowIndex].programma = event;
	}

	// utilizzato su dichiarazioneSpesaComponent
		controllaFormDeclaratoria() {
		return (this.validFormDeclaratoria = this.myForm.valid);
	}

	

	settoreOptions(desc: string): string[] {
		const direzioneFiltrata = this.dati.find(direzione => direzione.descBreveDirezione === desc);
		return direzioneFiltrata ? direzioneFiltrata.settori : [];
	}

	getDirezioniSettori() {
		this.subscribers = this.rendicontazioneA20Service.getDirezioniSettori().subscribe(data => {
			if (data) {
				this.dati = data;
				this.direzioni = this.dati.map(direzione => ({ value: direzione.descBreveDirezione, viewValue: direzione.descBreveDirezione }));
        this.direzioni.toString();
        
			}
		});
	}

	isLoading() {
		return !this.loadedDeclaratoria;
	}
}
