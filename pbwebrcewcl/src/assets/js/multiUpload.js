

/*
	Cose da fare nella finestra chiamante:
	1) creare da qualche parte un tag <ol> con classe ".fileList" destinato a contenere l'elenco dei file selezionati e cancellabili.
	   es:   <ol class='fileList'></ol>
	2) chiamare il metodo inizializzaMultiUpload() passando i parametri richiesti.
	   es:   inizializzaMultiUpload("pdf,txt","4096","#divUploadFile");
	3) all'evento change del tag <input> chiamare il metodo gestisciEventoChange()
	   es:   $('#fileUpload').change(function(event) {  gestisciEventoChange(event);  });
	4a) Nota: dà errore con versioni di Firefox vecchie (non usato).
           quando è ora di salvare i file selezionati (ad es al click di un bottone 'Salva') chiamare il metodo popolaTagInput().
	   es:   $("#widg_btSalvaChecklistDefinitivo").click(function(event){ 
			popolaTagInput();
			....
		 });
	4b) usare il metodo getFileDaAllegare() per ottenere l'elenco dei file da allegare (di norma per essere passati a java via AJAX).
*/


var filesToUpload = [];			// Copia dell'elenco di file caricati tramite <imput> in modo da renderli cancellabili.
var estensioniAmmesse = "";		// Stringa contenente le estensioni dei file ammesse (es: "pdf,doc,xls").
var dimMax = "";			// Dimensione massima in kilobyte di un singolo file (es: "4059").
var selettoreTagInputFile = "";		// Selettore con cui trovare nel DOM il tag <input> con cui si selezionano i file (es: "#fileUpload").
var selettoreTagInputNomiFile = "";	// Selettore con cui trovare nel DOM il tag <input> che deve contenere i nomi dei file selezionati (es: "#hNomiVerbali").
var selettoreMsgErrore = "";		// Selettore con cui trovare l'oggetto del DOM a cui agganciare i msg di errore (es: "#divUploadFile" oppure ".classeQualsiasi").

$(function () {

	// Gestisce l'eliminazione di uno dei file prima caricati con <input>.
	$(document).on("click",".removeFile", function(e){
		e.preventDefault();
		cancellaFile($(this));
	});

});

// Metodo da richiamare quando si seleziona un file tramite <input>.
function gestisciEventoChange(event) {

	// Copia i file selezionati nell'array 'filesToUpload'.
	for (var i = 0; i < event.target.files.length; i++) {
		console.log('file caricato: ' + event.target.files[i].name);
		filesToUpload.push(event.target.files[i]);
	};

	// Crea l'html per visualizzare a video l'elenco dei file con il link per poterli cancellare.
	creaElencoFile(event);

	// Aggiorna la scritta "n file selezionati" dentro il campo <input> di selezione dei file.
	aggiornaNumFileSelezionati();

	// Mostra un messaggio a video se è stato caricato un file non consentito per tipo o dimensione.
	segnalaFileNonValidi(filesToUpload);
}


// Metodo da usare nella finestra chiamante per passare i parametri necessari.
// Per il significato dei parametri, vedere i commenti in cima al file.
function inizializzaMultiUpload(pEstensioniAmmesse, pDimMax, pSelettoreTagInputFile, pSelettoreTagInputNomiFile, pSelettoreMsgErrore) {
	console.log('pEstensioniAmmesse = '+pEstensioniAmmesse+"; pDimMax = "+pDimMax+"; pSelettoreTagInputFile = "+pSelettoreTagInputFile+"; pSelettoreTagInputNomiFile = "+pSelettoreTagInputNomiFile+"; pSelettoreMsgErrore = "+pSelettoreMsgErrore);
	estensioniAmmesse = pEstensioniAmmesse;
	dimMax = pDimMax;
	selettoreTagInputFile = pSelettoreTagInputFile;
        selettoreTagInputNomiFile = pSelettoreTagInputNomiFile;
	selettoreMsgErrore = pSelettoreMsgErrore;
}


/* Non usato in quanto "new DataTransfer()" dà errore su versioni di Firefox vecchie.
// Assegna i file da allegare (cioè i file selezionati con <input> meno quelli poi cancellati) al tag <input> della finestra chiamante.
// Nota: DataTransfer serve per assegnare dinamicamente un insieme di file ad un tag <input>.
//       Normalmente gli <input type="file"> sono in sola lettura, ma l'uso di DataTransfer simula un drag&drop.
//       In questo modo il passaggio dei file a java può essere fatto tramite una normale submit e senza dover ricorrere ad AJAX.
function popolaTagInput() {
	const dt = new DataTransfer();
	var nomiFile = "";
	for(i = 0; i < filesToUpload.length; ++i) {
		dt.items.add(filesToUpload[i]);
		console.log('file num = '+i);

		if (nomiFile.length > 0)
			nomiFile = nomiFile + ",";
		nomiFile = nomiFile + filesToUpload[i].name;
	}
	$(selettoreTagInputFile).onchange = null 		// rimuove l'event listener
  	$(selettoreTagInputFile)[0].files = dt.files		// questo provoca un change event; assegna i file al tag <input> di tipo file
  	$(selettoreTagInputNomiFile).val(nomiFile);		// essegna i nomi dei file al tag <input> della finestra chiamante destinato a contenerli.
	console.log('NomiFile = '+nomiFile);
}
*/


// Restituisce l'oggetto filesToUpload di tipo vettore: serve per passare alla finestra chiamante l'oggetto che contiene i file da allegare.
function getFileDaAllegare() {
	console.log("multiUpload.js getFileDaAllegare BEGIN");
	return filesToUpload;
}


// Crea l'html per visualizzare a video l'elenco dei file con il link per poterli cancellare.
function creaElencoFile(event) {
	var output = [];
	var fileNonValidi = 0;
	for (var i = 0, f; f = event.target.files[i]; i++) {

		// Non so bene a cosa serva escape(), nel dubbio lo lascio; in più trasformo i %20 in spazi (serve per i file con spazi vuoti nel nome).
		var nomeFile = escape(f.name);
		nomeFile = nomeFile.replace(/%20/g, " ");

		var removeLink = "<a title=\"cancella\" class=\"removeFile\" href=\"#\" data-fileid=\"" + i + "\"><i class=\"fa fa-times fa-1\" aria-hidden=\"true\"></i></a>";
		
		var msgFormatoErrato = (verificaFormato(nomeFile)) ? "" : "&nbsp;&nbsp;&nbsp;Attenzione: il file &egrave; in un formato non previsto.";

		var msgDimensioneErrata = (verificaDimensione(f.size)) ? "" : "&nbsp;&nbsp;&nbsp;Attenzione: il file supera le dimensioni consentite ("+$("#hArchivioFileSizeLimitUpload").val()+" kB).";

		output.push("<li><strong>", nomeFile, "</strong> - ",f.size, " bytes. &nbsp; &nbsp; ", removeLink, msgFormatoErrato, msgDimensioneErrata , "</li> ");

		if (msgFormatoErrato == '' || msgDimensioneErrata == '')
			fileNonValidi = fileNonValidi +1 ;
	}
	$(".fileList").append(output.join(""));

}


// Gestisce la cancellazione di un file; tagA corrisponde al link di cancellazione selezionato a video dall'utente.
function cancellaFile(tagA) {

	// Cancella il file dall'array filesToUpload.
	var fileName = tagA.parent().children("strong").text();		
	for(i = 0; i < filesToUpload.length; ++ i) {
		if(filesToUpload[i].name == fileName) {
			filesToUpload.splice(i, 1);
		}	
	}
		
	// Cancella il file dall'elenco a video (tag <li>).
	tagA.parent().remove();

	// Aggiorna la scritta "n file selezionati" dentro il campo <input> di selezione dei file.
	aggiornaNumFileSelezionati();

	segnalaFileNonValidi(filesToUpload);
}


// Aggiorna la scritta "n file selezionati" dentro il campo <input> di selezione dei file.
function aggiornaNumFileSelezionati() {
	var msg = "";
	if (filesToUpload.length == 0)
		msg = "nessun file selezionato";
	else if (filesToUpload.length == 1)
		msg = "1 file selezionato";
	else
		msg = filesToUpload.length + " file selezionati";
	$('#upLoadfile_0').val(msg);
}


// Mostra un messaggio a video se è stato caricato un file non consentito per tipo o dimensione.
function segnalaFileNonValidi() {
	$(".messaggioKo").remove();
	if (fileNonValidi()) {
		showErrorMessage('Prima di confermare, cancellare i file non validi.');
		return true;
	}
	return false;
}


// Restituisce true se è stato caricato almeno 1 file non consentito per tipo o dimensione.
function fileNonValidi() {
	for(i = 0; i < filesToUpload.length; ++ i) {
		if (!verificaFormato(filesToUpload[i].name) || !verificaDimensione(filesToUpload[i].size))
			return true;
	};
	return false;
}


function verificaFormato(nomeFile) {
	if (estensioniAmmesse == null || estensioniAmmesse == '')
		return true;
	var estensione = nomeFile.slice((nomeFile.lastIndexOf('.') - 1 >>> 0) + 2);
	var esito = (estensioniAmmesse.indexOf(estensione) > -1);
	//console.log("estensioniAmmesse = " + estensioniAmmesse);
	console.log("estensione = " + estensione + "; formato corretto = " + esito);
	return esito;
}


function verificaDimensione(dim) {
	if (dimMax == null || dimMax == '')
		return true;
	var esito = (dim <= (dimMax * 1000));
	console.log("dimensione = " + dim + "; dimensione max = " + (dimMax * 1000) + "; dimensione corretta = " + esito);
	return esito;
}


function showErrorMessage(msg) {
	$(".messaggioKo").remove();
	$(selettoreMsgErrore).before($("<div class='messaggioKo'><div class='iconMsg'><i class='fa fa-times fa-3x red'></i></div>" + msg + "</div>"));
}