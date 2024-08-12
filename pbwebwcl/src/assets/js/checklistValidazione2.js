
// PK : preso da TEST /ris/js/finanziamenti/pbandi/pbandiweb/checklistValidazione.js 
///////////////// PK mie modifiche 

function inizializzaCheckListValidazione() {
	// scrivo questa function e la inizializzo nella checklist.component.ts
	console.log('   checklistValidazione2.js -> inizializzaCheckListValidazione -> BEGIN');

	//////////// codice preso dalla $(function (){   ///////////////////////////////////////////////////////
	$('#checklistHtmlDiv').before("<div id=\"dialog\" ><p id=\"msg\"></p></div>");
	$("#dialog").hide();
	enableTextarea();
	gestioneCheckbox();

	//			 genera pulsante scarica pdf piu green
	if ($("#mostraEsitoSuccessoConfermaSalvaCheckListValidazione").length > 0) {
		if ($("#widg_urlPdfPiuGreen").length > 0 && $("#widg_urlPdfPiuGreen").val() != '') {
			var href = $("#widg_urlPdfPiuGreen").val();
			var buttonPdfPiuGreen = '<a id="linkPdfPiuGreen" href=' + href + ' class="noWaitMsg"><span class="xls noWaitMsg">PDF checklist validazione C</span></a>';
			$(".pulsNavSx").prepend(buttonPdfPiuGreen);
		}
	}

	//PK TODO : forse serve in pagina successiva????
	$('input#widg_btnScaricaValidazione').val('PDF checklist validazione');

	//PK TODO : verificare se serve
	// EVITA LA VALIDAZIONE dei campi obbligatori al click sui seguenti bottoni.
	$("input#widg_btIndietro").attr('formnovalidate', 'formnovalidate');
	$("input#widg_btSalvaChecklistInBozza").attr('formnovalidate', 'formnovalidate');


	// modifiche Manetti per gestire le sezioni 04.D1 e 04.D2

	var toggleD1Open = true;
	var toggleD2Open = true;

	apritoggleD1();
	apritoggleD2();

	$("#toggleD1").click(function () {
		console.log('   checklistValidazione2.js -> inizializzaCheckListValidazione -> toggleD1 CLICK toggleD1Open=' + toggleD1Open);
		if (toggleD1Open) {
			chiuditoggleD1();
			toggleD1Open = false;
		} else {
			apritoggleD1();
			toggleD1Open = true;
		}
	});

	$("#toggleD2").click(function () {
		console.log('   checklistValidazione2.js -> inizializzaCheckListValidazione -> toggleD2 CLICK toggleD2Open=' + toggleD2Open);
		if (toggleD2Open) {
			chiuditoggleD2();
			toggleD2Open = false;
		} else {
			apritoggleD2();
			toggleD2Open = true;
		}
	});
	// fine modifiche Manetti

	//modifiche appalti Jira PBANDI-3545

	if ($('.wAppalti').length) {

		$('#caricaappalti').remove();
		idSectionAppalti();

		$("#section").css("display", "none");

		var r = "<mat-icon role=\"img\" class=\"mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\" aria-hidden=\"true\" data-mat-icon-type=\"font\">expand_more</mat-icon>";
		$(".toggleappalto").append(r);

		$(".toggleappalto").click(function () {
			$(this).parents('tbody.appaltotitle').next('tbody.appalto').slideToggle();
			//$(this).find("i.fa").toggleClass("fa-chevron-down fa-chevron-up")
			if ($(this).find("mat-icon")[0].innerHTML == "expand_less") {
				$(this).find("mat-icon")[0].innerHTML = "expand_more";
			} else if ($(this).find("mat-icon")[0].innerHTML == "expand_more") {
				$(this).find("mat-icon")[0].innerHTML = "expand_less";
			}
		});

		gestioneCheckbox();
	}

	//fine modifiche appalti

	console.log('   checklistValidazione2.js -> inizializzaCheckListValidazione -> toggleD1 e toggleD2');
	//////////// FINE codice preso dalla $(function (){   ///////////////////////////////////////////////////////
	console.log('   checklistValidazione2.js -> inizializzaCheckListValidazione -> END');
}

function apritoggleD1() {
	console.log('   checklistValidazione2.js -> apritoggleD1 -> BEGIN');
	$("#d1Section").css("display", "table-row-group");

	//cambio la direzione della freccia
	$("#toggleD1 mat-icon").remove();
	var r = "<mat-icon role=\"img\" class=\"mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\" aria-hidden=\"true\" data-mat-icon-type=\"font\" style=\"\">expand_less</mat-icon>";
	$("#toggleD1").append(r);

	console.log('   checklistValidazione2.js -> apritoggleD1 -> END');
}

function chiuditoggleD1() {
	console.log('   checklistValidazione2.js -> chiuditoggleD1 -> BEGIN');

	$("#d1Section").css("display", "none");

	//cambio la direzione della freccia
	$("#toggleD1 mat-icon").remove();
	var r = "<mat-icon role=\"img\" class=\"mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\" aria-hidden=\"true\" data-mat-icon-type=\"font\">expand_more</mat-icon>";
	$("#toggleD1").append(r);

	console.log('   checklistValidazione2.js -> chiuditoggleD1 -> END');
}

function apritoggleD2() {
	console.log('   checklistValidazione2.js -> apritoggleD2 -> BEGIN');
	$("#d2Section").css("display", "table-row-group");

	//cambio la direzione della freccia
	$("#toggleD2 mat-icon").remove();
	var r = "<mat-icon role=\"img\" class=\"mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\" aria-hidden=\"true\" data-mat-icon-type=\"font\" style=\"\">expand_less</mat-icon>";
	$("#toggleD2").append(r);

	console.log('   checklistValidazione2.js -> apritoggleD2 -> END');
}

function chiuditoggleD2() {
	console.log('   checklistValidazione2.js -> chiuditoggleD2 -> BEGIN');

	$("#d2Section").css("display", "none");

	//cambio la direzione della freccia
	$("#toggleD2 mat-icon").remove();
	var r = "<mat-icon role=\"img\" class=\"mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\" aria-hidden=\"true\" data-mat-icon-type=\"font\">expand_more</mat-icon>";
	$("#toggleD2").append(r);

	console.log('   checklistValidazione2.js -> chiuditoggleD2 -> END');
}

// PK : copia del metodo updateValues , fatta per rendere univoco il nome
function updateValuesCLValidazione() {
	console.log('   checklistValidazione2.js -> updateValuesCLValidazione -> BEGIN');
	$("input, select, textarea").each(function () {
		var $this = $(this);

		if ($this.is("[type='radio']") || $this.is("[type='checkbox']")) {
			if ($this.prop("checked")) {
				$this.attr("checked", "checked");
			} else {
				$this.removeAttr("checked");
			}
		} else {
			if ($this.is("select")) {
				$this.find(":selected").attr("selected", "selected");
			}
			else if ($this.is("textarea")) {
				$this.text($this.val());
			}
			else {
				$this.attr("value", $this.val());
			}
		}
	});
	console.log('   checklistValidazione2.js -> updateValuesCLValidazione -> END');
}

// PK : copia del metodo updateValues , fatta per rendere univoco il nome
function verifichePreSalvaChecklistValidazioneDefinitivo() {

	console.log('   checklistValidazione2.js -> verifichePreSalvaChecklistValidazioneDefinitivo -> BEGIN');
	var verOK = true;
	$("#errorMsg").remove();
	$('#errorMsg p.d1d2').remove();
	$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;"><span><p>Attenzione! &Egrave; necessario valorizzare i campi obbligatori e definire un esito per tutte le righe evidenziate.</p></span>');

	$("#chekListHtml tr.row_check").removeClass('invalid');

	if (!checkVerifyCLV() || !validazioneCLV() || !wPrint('d')) {
		if (!wPrint('d')) {
			$('#errorMsg').append('<p class="d1d2">Attenzione! La compilazione delle sezioni 04.D1 e 04.D2 &egrave; obbligatoria ed esclusiva.</p>');
		};

		$('#errorMsg').append('<a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>');
		$("#errorMsg").show();
		$("#errorMsg .chiudiMsg").click(function () {
			$("#errorMsg").hide();
		});
		verOK = false;
	}
	console.log('   checklistValidazione2.js -> verifichePreSalvaChecklistValidazioneDefinitivo -> END, verOK=' + verOK);
	return verOK;
}

function chiudiErrorMsg() {
	console.log("checklistValidazione2.js errorMsg BEGIN");
	$("#errorMsg").hide();

}
///////////////// PK mie modifiche FINE


function gestioneCheckbox() {
	/* checkbox come radio */
	$("input:checkbox").click(function () {
		var group = "input:checkbox[name='" + $(this).prop("name") + "']";
		$(group).not(this).prop("checked", false).attr('checked', false);
	});

	/* __________seleziona-deseleziona tutti________*/
	$("input.allsi").click(function () {
		$(".genTab tbody input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("input.allsi").prop("checked", true);
			$("table.genTab input:checkbox.si").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});
		} else {
			$(".genTab tbody input:checkbox.si").each(function () {
				$(this).prop("checked", false);
				$("input.allsi").prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

	$("input.allno").click(function () {
		$(".genTab tbody input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("input.allno").prop("checked", true);
			$("table.genTab input:checkbox.no").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});
		} else {
			$(".genTab tbody input:checkbox.no").each(function () {
				$(this).prop("checked", false);
				$("input.allno").prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

	$("input.allna").click(function () {
		$(".genTab tbody input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("input.allna").prop("checked", true);
			$(".genTab tbody input:checkbox.na").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});
		} else {
			$(".genTab tbody input:checkbox.na").each(function () {
				$(this).prop("checked", false);
				$("input.allna").prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

	/* Per la sezione D1*/
	$("input#checkD1si").click(function () {
		$('#headD1 tr , #d1Section tr').removeClass('invalid');
		$(".subcontentD1 input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$(".subcontentD1 input:checkbox.si").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});

		} else {
			$(".subcontentD1 input:checkbox.si").each(function () {
				$(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

	$("input#checkD1no").click(function () {
		$('#headD1 tr , #d1Section tr').removeClass('invalid');

		$(".subcontentD1 input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$(".subcontentD1 input:checkbox.no").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});
		} else {
			$(".subcontentD1 input:checkbox.no").each(function () {
				$(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

	$("input#checkD1na").click(function () {
		$('#headD1 tr , #d1Section tr').removeClass('invalid');

		$(".subcontentD1 input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$(".subcontentD1 input:checkbox.na").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});
		} else {
			$(".subcontentD1 input:checkbox.na").each(function () {
				$(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

	/* Per la sezione D2*/
	$("input#checkD2si").click(function () {
		$('#headD2 tr , #d2Section tr').removeClass('invalid');
		$(".subcontentD2 input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$(".subcontentD2 input:checkbox.si").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});

		} else {
			$(".subcontentD2 input:checkbox.si").each(function () {
				$(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}

	});
	$("input#checkD2no").click(function () {
		$('#headD2 tr , #d2Section tr').removeClass('invalid');
		$(".subcontentD2 input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$(".subcontentD2 input:checkbox.no").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});

		} else {
			$(".subcontentD2 input:checkbox.no").each(function () {
				$(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});
	$("input#checkD2na").click(function () {
		$('#headD2 tr , #d2Section tr').removeClass('invalid');
		$(".subcontentD2 input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$(".subcontentD2 input:checkbox.na").each(function () {
				$(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled', false).removeClass('is-disabled');
			});

		} else {
			$(".subcontentD2 input:checkbox.na").each(function () {
				$(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled', true);
			});
		}
	});

}

/* abilita o disabilita le textarea delle note in presenza di un esito*/
function enableTextarea() {
	$('tr.row_check td.note textarea').prop('disabled', true);

	$('tr.row_check td input').click(function () {
		if ($(this).is(':checked')) {
			$(this).parent().parent().find('td.note textarea').prop('disabled', false).removeClass('is-disabled');
		} else {
			$(this).parent().parent().find('td.note textarea').prop('disabled', true);
		}
	});
	/* in partenza verifico se non sia già stato salvato un esito per abilitare la textarea*/
	$('tr.row_check').each(function () {
		if ($(this).find('td input').is(':checked')) {
			var esito = true;
			//console.log(esito);
			$(this).find('td.note textarea').prop('disabled', false).removeClass('is-disabled');
		}
	});

}

function validazioneCLV() {
	console.log('   checklistValidazione2.js -> validazioneCLV()');
	var valRes = true;


	var attoConcessioneContributo = $("#attoConcessioneContributo");
	if (attoConcessioneContributo.attr("class") == "error") {
		// elimino eventuali errori segnalati da un precedente controllo 
		console.log('   checklistValidazione2.js -> validazioneCLV() attoConcessioneContributo has error');
		attoConcessioneContributo.attr({ class: "" });
		// rimuovo anche il div con la scritta "Campo obbligatorio"
		var idd = attoConcessioneContributo.attr("name") + '-error';
		$('#' + idd).remove();
	}
	if (!attoConcessioneContributo.val()?.trim()) {
		console.log('   checklistValidazione2.js -> validazioneCLV() attoConcessioneContributo NULLO');
		valRes = false;
		// bordo di rosso i campi
		attoConcessioneContributo.attr({ class: "error" });
		var $label = '<div id="' + attoConcessioneContributo.attr("name") + '-error" class="error" for="' + attoConcessioneContributo.attr("name") + '">Campo obbligatorio</div>';
		attoConcessioneContributo.after($label);
	}

	var firmaResponsabile = $("#firmaResponsabile");
	if (firmaResponsabile.attr("class") == "error") {
		// elimino eventuali errori segnalati da un precedente controllo 
		console.log('   checklistValidazione2.js -> validazioneCLV() firmaResponsabile has error');
		firmaResponsabile.attr({ class: "" });
		// rimuovo anche il div con la scritta "Campo obbligatorio"
		var idd = firmaResponsabile.attr("name") + '-error';
		$('#' + idd).remove();
	}
	console.log('   checklistValidazione2.js -> validazioneCLV() firmaResponsabile=' + firmaResponsabile);
	if (!firmaResponsabile.val().trim()) {
		console.log('   checklistValidazione2.js -> validazioneCLV() firmaResponsabile NULLO');
		valRes = false;
		//TODO PK : aggingo errore con nota:"Campo obbligatorio"
		firmaResponsabile.attr({ class: "error" });
		var $label = '<div id="' + firmaResponsabile.attr("name") + '-error" class="error" for="' + firmaResponsabile.attr("name") + '">Campo obbligatorio</div>';
		firmaResponsabile.after($label);

	} else {
		console.log('   checklistValidazione2.js -> validazioneCLV() firmaResponsabile =' + firmaResponsabile.length);
		if (firmaResponsabile.val().length < 4) {
			console.log('   checklistValidazione2.js -> validazioneCLV() firmaResponsabile < 4');
			valRes = false;
			firmaResponsabile.attr({ class: "error" });
			var $label = '<div id="' + firmaResponsabile.attr("name") + '-error" class="error" for="' + firmaResponsabile.attr("name") + '">Inserire almento 3 caratteri</div>';
			firmaResponsabile.after($label);
		}
	}

	var dataControllo = $("#dataControllo");
	if (dataControllo.attr("class") == "error") {
		// elimino eventuali errori segnalati da un precedente controllo 
		console.log('   checklistValidazione2.js -> validazioneCLV() dataControllo has error');
		dataControllo.attr({ class: "" });
		// rimuovo anche il div con la scritta "Campo obbligatorio"
		var idd = dataControllo.attr("name") + '-error';
		$('#' + idd).remove();
	}
	if (!dataControllo.val().trim()) {
		console.log('   checklistValidazione2.js -> validazioneCLV() dataControllo NULLO');
		valRes = false;
		// bordo di rosso i campi
		dataControllo.attr({ class: "error" });
		var $label = '<div id="' + dataControllo.attr("name") + '-error" class="error" for="' + dataControllo.attr("name") + '">Inserire la data del controllo in loco</div>';
		dataControllo.after($label);
	}

	//required: "#irregSi:checked"
	if ($("#irregSi").is(":checked")) {
		console.log('   checklistValidazione2.js -> validazioneCLV() irregSi CHECKED');
		var descIrregolarita = $("#descIrregolarita");
		if (!descIrregolarita.val().trim()) {
			console.log('   checklistValidazione2.js -> validazioneCLV() descIrregolarita NULLO');
			valRes = false;
			// bordo di rosso i campi
			descIrregolarita.attr({ class: "error" });
			var $label = '<div id="' + descIrregolarita.attr("name") + '-error" class="error" for="' + descIrregolarita.attr("name") + '">Campo obbligatorio</div>';
			descIrregolarita.after($label);
		}

	}
	return valRes;
}

//PK : verifico che siano valorizzati tutti i checkbox
function checkVerifyCLV() {
	console.log('   checklistValidazione2.js -> checkVerifyCLV()');
	var flagVer = true;
	$("#chekListHtml tr.row_check").removeClass('invalid');
	// PK escludo le sezioni 04.D1 e 04.D2 che hanno rispettivamente le classi subcontentD1 e subcontentD2
	// queste sezioni sono controllate in apposita funzione
	$("#chekListHtml .toprint tr.row_check:not(tr.subcontentD1, tr.subcontentD2)").each(function () {
		if ($(this).find("td.esito input").length > 0) {
			var esito = $(this).find("td.esito input:checked").length > 0;
			if (!esito) {
				$(this).addClass('invalid');
				flagVer = false;
			}
		}
	});
	console.log('   checklistValidazione2.js -> checkVerifyCLV() flagVer=' + flagVer);
	return flagVer;
}



function wPrint(k) { /* serve a verificare che solo una delle due sezioni D1 o D2 sia stata compilata*/
	//console.log(k);
	flagPrint = true;
	console.log('   checklistValidazione2.js -> wPrint -> BEGIN');

	if ($('#d1Section').length) {

		console.log('   checklistValidazione2.js -> wPrint -> length=' + $('#d1Section').length);

		$('#d1Section tr').each(function () {
			if ($(this).find('td input').is(':checked') || $.trim($(this).find('td.note textarea').val()).length > 0) {
				chkD1 = 1;
				console.log('   checklistValidazione2.js -> wPrint ->CH1 = ', chkD1);
				return false
			} else {
				chkD1 = 0;
			}
		});

		$('#d2Section tr').each(function () {
			if ($(this).find('td input').is(':checked') || $.trim($(this).find('td.note textarea').val()).length > 0) {
				chkD2 = 1;
				console.log('   checklistValidazione2.js -> wPrint ->CH2 = ', chkD2);
				return false
			} else {
				chkD2 = 0;
			}
		});

		console.log('   checklistValidazione2.js -> wPrint -> chkD1=' + chkD1);
		console.log('   checklistValidazione2.js -> wPrint -> chkD2=' + chkD2);

		if (k != 'b') {
			if (chkD1 == 0 && chkD2 == 1) {
				flagPrint = true;
				$('.hD1 , .hD2').removeClass('invalid');
				$('tbody#headD1').removeClass('toprint').addClass('noprint');
				$('tbody#headD2').removeClass('noprint').addClass('toprint');
				$('#d1Section, tr.hD1').removeClass('toprint').addClass('noprint');
				$('#d2Section, tr.hD2').removeClass('noprint').addClass('toprint');
			} else if (chkD1 == 1 && chkD2 == 0) {
				flagPrint = true;
				$('.hD1 , .hD2').removeClass('invalid');
				$('tbody#headD2').removeClass('toprint').addClass('noprint');
				$('tbody#headD1').removeClass('noprint').addClass('toprint');
				$('#d2Section, tr.hD2').removeClass('toprint').addClass('noprint');
				$('#d1Section, tr.hD1').removeClass('noprint').addClass('toprint');
			} else if ((chkD1 == 1 && chkD2 == 1) || (chkD1 == 0 && chkD2 == 0) || (chkD1 == '' && chkD2 == '')) {
				$('.hD1 , .hD2').addClass('invalid');
				flagPrint = false;
			}
		} else {

			console.log('   checklistValidazione2.js -> wPrint -> Bozza');

			if (chkD1 == 0 && chkD2 == 1) {
				flagPrint = true;
				$('.hD1 , .hD2').removeClass('invalid');
				$('tbody#headD1').removeClass('toprint').addClass('noprint');
				$('tbody#headD2').removeClass('noprint').addClass('toprint');
				$('#d1Section, tr.hD1').removeClass('toprint').addClass('noprint');
				$('#d2Section, tr.hD2').removeClass('noprint').addClass('toprint');
			} else if (chkD1 == 1 && chkD2 == 0) {
				flagPrint = true;
				$('.hD1 , .hD2').removeClass('invalid');
				$('tbody#headD2').removeClass('toprint').addClass('noprint');
				$('tbody#headD1').removeClass('noprint').addClass('toprint');
				$('#d2Section, tr.hD2').removeClass('toprint').addClass('noprint');
				$('#d1Section, tr.hD1').removeClass('noprint').addClass('toprint');
			} else if ((chkD1 == 1 && chkD2 == 1)) {
				$('.hD1 , .hD2').addClass('invalid');
				flagPrint = false;
			}

		}

	}
	console.log('   checklistValidazione2.js -> wPrint -> END, flagPrint=' + flagPrint);
	return flagPrint;
}
/*function idSectionAppalti(){
	$('#chekListHtml tbody.appaltotitle').each(function(index) {
		var K =  parseInt(index)+1;											
		$(this).find('td.codiceControllo').text('18.'+K);
		$(this).attr('id','titleappalto_'+index);
		$(this).next('tbody.appalto').attr('id','section_'+index);
		$(this).find('td.esito input').attr('id','chkappalto_'+index+'_all');
		$(this).find('td.esito input').attr('name','chkappalto_'+index+'_all');
	});
	
	$('#chekListHtml tbody.appalto tr').each(function(index) {
		var idPadre = $(this).parents('tbody').attr('id');
		var K =  parseInt(idPadre.split('_')[1])+1;

		if ($(this).attr('class')== 'row_check'){
			var nRow = $(this).find('td.codiceControllo').text().split('.')[2];
			$(this).find('td.codiceControllo').text('18.'+K+'.'+nRow);
			}
		$(this).find('td.esito input').attr('id','chkappalto_'+index);
		$(this).find('td.esito input').attr('name','chkappalto_'+index);
		$(this).find('td.note textarea').attr('name','noteappalto_'+index);
	});
	
	checkAllAppalti ();	
}*/

function idSectionAppalti() {
	$('#chekListHtml tbody.appaltotitle').each(function (index) {
		var K = parseInt(index) + 1;
		var counter = $('input#counterappalti').val();
		var chrApp = $('input#chrappalti').val();
		console.log(counter, chrApp);
		$(this).find('td.codiceControllo').text(counter + '.' + K);
		$(this).attr('id', 'titleappalto_' + index);
		$(this).next('tbody.appalto').attr('id', 'section_' + index);
		$(this).find('td.esito input').attr('id', 'chkappalto_' + index + '_all');
		$(this).find('td.esito input').attr('name', 'chkappalto_' + index + '_all');
		$('.descrizioneAttivitaDiControllo').each(function () {
			$(this).find('span.chrApp').text(chrApp);
		});
	});

	$('#chekListHtml tbody.appalto tr').each(function (index) {
		var idPadre = $(this).parents('tbody').attr('id');
		var K = parseInt(idPadre.split('_')[1]) + 1;
		var counter = $('input#counterappalti').val();
		if ($(this).attr('class') == 'row_check') {
			var nRow = $(this).find('td.codiceControllo').text().split('.')[2];
			$(this).find('td.codiceControllo').text(counter + '.' + K + '.' + nRow);
		}
		$(this).find('td.esito input').attr('id', 'chkappalto_' + index);
		$(this).find('td.esito input').attr('name', 'chkappalto_' + index);
		$(this).find('td.note textarea').attr('name', 'noteappalto_' + index);
	});

	checkAllAppalti();
}

function checkAllAppalti() {
	/* Per la sezione appalti*/
	$("input.si_all_app").click(function (el) {
		var idPadre = $(this).parents('tbody').attr('id');
		var K = idPadre.split('_')[1];
		$("#section_" + K + " input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("#section_" + K + " input:checkbox.si").each(function () {
				$(this).prop("checked", true);
			});

		} else {
			$("#section_" + K + " input:checkbox.si").each(function () {
				$(this).prop("checked", false);
			});
		}
	});
	$("input.no_all_app").click(function () {
		var idPadre = $(this).parents('tbody').attr('id');
		var K = idPadre.split('_')[1];
		$("#section_" + K + " input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("#section_" + K + " input:checkbox.no").each(function () {
				$(this).prop("checked", true);
			});

		} else {
			$("#section_" + K + " input:checkbox.no").each(function () {
				$(this).prop("checked", false);
			});
		}
	});
	$("input.na_all_app").click(function () {
		var idPadre = $(this).parents('tbody').attr('id');
		var K = idPadre.split('_')[1];
		$("#section_" + K + " input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("#section_" + K + " input:checkbox.na").each(function () {
				$(this).prop("checked", true);
			});

		} else {
			$("#section_" + K + " input:checkbox.na").each(function () {
				$(this).prop("checked", false);
			});
		}
	});
}