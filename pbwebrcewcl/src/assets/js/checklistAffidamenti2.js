
var isChecklistDocumentale;

//$(function () {  // PK : viene eseguita al caricamento di Angular.... non utilizzare....
function inizializzaCheckList() {

	console.log("checklistAffidamenti2.js inizializzaCheckList BEGIN");

	// leggo elementi da HTML checklist
	//console.log('checklistAffidamenti2.js -> inizializzaCheckList -> chk_all_si checked='+ $('#chk_all_si').is(":checked"));
	//console.log('checklistAffidamenti2.js -> inizializzaCheckList -> neg_1 checked='+ $('#neg_1').is(":checked"));
	//console.log('checklistAffidamenti2.js -> inizializzaCheckList -> na_1 checked='+ $('#na_1').is(":checked") );
	//console.log('checklistAffidamenti2.js -> inizializzaCheckList -> na_14 checked='+ $('#na_14').is(":checked"));

	// Variabile da usare per distinguere tra 'documentale' e 'in loco' (Jira Pbandi-2859).
	isChecklistDocumentale = ($('#widg_hiddenTipologiaChecklist').val() == 'CLA');

	//console.log("checklistAffidamenti2.js inizializzaCheckList #widg_hiddenTipologiaChecklist="+$('#widg_hiddenTipologiaChecklist').val());
	console.log("checklistAffidamenti2.js inizializzaCheckList isChecklistDocumentale=" + isChecklistDocumentale);

	// Se la checlist e' documentale e si sta caricamdo una nuova checklist mai salvata prima, aggiungo al fondo gli esiti:
	console.log("checklistAffidamenti2.js inizializzaCheckList #divEsiti=" + $('#divEsiti').length);
	if (isChecklistDocumentale && $('#divEsiti').length == 0) {
		agganciaDivEsiti();
	}


	// Se la checlist e' in loco:
	//   - nasconde il bottone 'richiesta integrazione'.
	//   - se si sta caricando una nuova checklist mai salvata prima, aggiungo al fondo l'upload del verbale.

	// widthOnResize();  TODO PK da dove arriva ??
	$('#checklistHtmlDiv').before("<div id=\"dialog\" ><p id=\"msg\"></p></div>");
	$("#dialog").hide();
	gestioneCheckbox();
	//$("table.genTab th:contains('NOTE')").css('width','220px');
	$("table.genTab th:eq(4)").css('width', '220px');
	$("table#fixH th:eq(4)").css('width', '220px');
	$("tr.row_check textarea").attr('cols', '100');
	// autosize($('textarea'));  TODO PK da dove arriva ??
	//$("#widg_btSalvaChecklistDefinitivo").val('salva');
	//$("#widg_btSalvaChecklistInBozza").val('bozza');

	// $("#dataControllo").datepicker();  TODO PK da dove arriva ?? dice datepicker is not a function...
	//var $myfixedHeader = $("table#chekListHtml").FixedHeader(); //TODO PK da dove arriva ?? dice FixedHeader is not a function...
	// $(window).scroll($myfixedHeader.moveScroll);   TODO PK
	// $.cookie('mostramenu', 'si', {path : "/finanziamenti/bandi/"});  TODO PK da dove arriva ?? dice cookie is not a function...

	$('textarea#noteVerificaCL').attr('placeholder', 'Note - (queste note non saranno riportate sul PDF)').css('width', '100%');

	$(".up").click(function () {
		$('html, body').animate({
			scrollTop: 0
		}, 2000);
	});


	// Jira PBANDI-2829 - INIZIO

	// Se gli esiti sono gie' valorizzati, ne disabilita la modifica.
	var esitoParzialeGiaValorizzato = (!isNull($('#widg_hiddenIdEsitoFase1').val()));
	console.log("checklistAffidamenti2.js inizializzaCheckList esitoParzialeGiaValorizzato=" + esitoParzialeGiaValorizzato);

	var esitoDefinitivoGiaValorizzato = (!isNull($('#widg_hiddenIdEsitoFase2').val()));
	console.log("checklistAffidamenti2.js inizializzaCheckList esitoDefinitivoGiaValorizzato=" + esitoDefinitivoGiaValorizzato);

	//if (esitoParzialeGiaValorizzato) {
	//	$('#checkboxEsitoParzialePositivo').attr('disabled','disabled');
	//	$('#checkboxEsitoParzialeNegativo').attr('disabled','disabled');
	//	$('#checkboxEsitoParzialeConRettifica').attr('disabled','disabled');
	//} else {
	abilitaDisabilitaRettificaParziale();
	//}

	//if (esitoDefinitivoGiaValorizzato) {
	//	$('#checkboxEsitoDefinitivoPositivo').attr('disabled','disabled');
	//	$('#checkboxEsitoDefinitivoNegativo').attr('disabled','disabled');
	//	$('#checkboxEsitoDefinitivoConRettifica').attr('disabled','disabled');
	//} else {
	abilitaDisabilitaRettificaDefinitivo();
	//}

	$('#checkboxEsitoParzialePositivo').click(function () {
		abilitaDisabilitaRettificaParziale()
	});

	$('#checkboxEsitoParzialeNegativo').click(function () {
		abilitaDisabilitaRettificaParziale()
	});

	$('#checkboxEsitoDefinitivoPositivo').click(function () {
		abilitaDisabilitaRettificaDefinitivo()
	});

	$('#checkboxEsitoDefinitivoNegativo').click(function () {
		abilitaDisabilitaRettificaDefinitivo()
	});

	// Jira PBANDI-2829 - FINE


	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//    INIZIO GESTIONE MULTI-UPLOAD VERBALI CHECKLIST IN LOCO (prevede di aver incluso multiUpload.js)
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	/* PK : spostato su Angular
		if (!isChecklistDocumentale) {
			$('#pUploadVerbale tbody').append("<tr><th scope='row'></th><td><ol class='fileList'></ol></td></tr>");
	
			var pEstensioni = $('#hArchivioAllowedFileExtensions').val();
			console.log("checklistAffidamenti2.js inizializzaCheckList pEstensioni = " + pEstensioni);
	
			var pDim = $('#hArchivioFileSizeLimitUpload').val();
			console.log("checklistAffidamenti2.js inizializzaCheckList pDim = " + pDim);
	
			inizializzaMultiUpload(pEstensioni, pDim, "#fileUpload", "#hNomiVerbali", "#divUploadFile");
	
			$('#fileUpload').change(function(event) {
				gestisciEventoChange(event);
			});
		}
	*/
	// ///////////////////////////////////////////////////////////////////////////
	//    FINE GESTIONE MULTI-UPLOAD VERBALI CHECKLIST IN LOCO
	// ///////////////////////////////////////////////////////////////////////////

	console.log("checklistAffidamenti2.js inizializzaCheckList END");

	//});
}

// Jira PBANDI-2829: abilita o disabilita il check rettifica dell'esito intermedio.
function abilitaDisabilitaRettificaParziale() {
	console.log("checklistAffidamenti2.js inizializzaCheckList abilitaDisabilitaRettificaParziale BEGIN");

	var positivoChecked = $('#checkboxEsitoParzialePositivo').is(':checked');
	var negativoChecked = $('#checkboxEsitoParzialeNegativo').is(':checked');
	var checkRettifica = $('#checkboxEsitoParzialeConRettifica');
	if (negativoChecked) {
		checkRettifica.removeAttr('disabled').removeClass('is-disabled');
	} else {
		checkRettifica.attr('disabled', 'disabled').prop('checked', false);
	}
	console.log("checklistAffidamenti2.js inizializzaCheckList abilitaDisabilitaRettificaParziale END");
}

// Jira PBANDI-2829: abilita o disabilita il check rettifica dell'esito definitivo.
function abilitaDisabilitaRettificaDefinitivo() {
	console.log("checklistAffidamenti2.js inizializzaCheckList abilitaDisabilitaRettificaDefinitivo BEGIN");
	var positivoChecked = $('#checkboxEsitoDefinitivoPositivo').is(':checked');
	var negativoChecked = $('#checkboxEsitoDefinitivoNegativo').is(':checked');
	var checkRettifica = $('#checkboxEsitoDefinitivoConRettifica');
	if (negativoChecked) {
		checkRettifica.removeAttr('disabled').removeClass('is-disabled');
	} else {
		checkRettifica.attr('disabled', 'disabled').prop('checked', false);
	}
	console.log("checklistAffidamenti2.js inizializzaCheckList abilitaDisabilitaRettificaDefinitivo END");
}

function getChecklistHtmlBody(event, isBozza, salvaDefinitivo) {
	console.log("checklistAffidamenti2.js getChecklistHtmlBody BEGIN, isBozza=" + isBozza + " ,salvaDefinitivo=" + salvaDefinitivo);
	updateValues();

	var res = "OK";

	if (validazione(isBozza, salvaDefinitivo)) {

		$("#errorMsg").remove();

		// Salva in sessione l'html della checklist.
		var checklistHtmlContent = $("#checklistHtmlContent")[0].outerHTML;
		//console.log("checklistAffidamenti2.js checklistHtmlContent="+checklistHtmlContent);

		$("#hiddenChecklistHtmlContent").val(checklistHtmlContent);

	} else {

		// validazione dei dati fallita
		$("#errorMsg").remove();

		console.log("checklistAffidamenti2.js getChecklistHtmlBody ELSE");
		if (isChecklistDocumentale) {
			if (isBozza) {
				visualizzaErrorMsg2('FORMAT_DATACONTROLLO_ERR');
			} else {
				visualizzaErrorMsg2('REQUIRED_AND_ESITO_ERR');
			}
			console.log("checklistAffidamenti2.js getChecklistHtmlBody ELSE IF");
		} else {
			visualizzaErrorMsg2('REQUIRED_AND_VERBALE_ERR');
			console.log("checklistAffidamenti2.js getChecklistHtmlBody ELSE ELSE");
		}
		res = "KO"; // per evitare che Angular esegua il salvataggio su DB
	}

	console.log("checklistAffidamenti2.js getChecklistHtmlBody END");
	return res;
}

function visualizzaErrorMsg2(tipo) {
	console.log("checklistAffidamenti2.js visualizzaErrorMsg2 tipo= " + tipo);
	$("#errorMsg").remove();
	if (tipo == "CHECKBOX_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! &Egrave; necessario compilare tutti i campi checkbox. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>'); //
	}
	if (tipo == "FILE_VERBALE_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! &Egrave; necessario caricare il verbale. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>'); //
	}
	if (tipo == "REQUIRED_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! &Egrave; necessario valorizzare i campi obbligatori. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>'); //
	}
	if (tipo == "REQUIRED_ESITO_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! &Egrave; necessario valorizzare l&lsquo;esito. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>'); //
	}
	if (tipo == "REQUIRED_AND_ESITO_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! &Egrave; necessario valorizzare i campi obbligatori e definire un esito. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>');
	}
	if (tipo == "REQUIRED_AND_VERBALE_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! &Egrave; necessario valorizzare i campi obbligatori e caricare il verbale. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>');
	}
	if (tipo == "FORMAT_DATACONTROLLO_ERR") {
		$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;">Attenzione! Il formato della data del controllo non &egrave; corretto. Utilizzare il formato dd/mm/yyyy. <a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>');
	}

	$("#errorMsg").show();
}

function chiudiErrorMsg() {
	console.log("checklistAffidamenti2.js errorMsg BEGIN");
	$("#errorMsg").hide();

}
function updateValues() {
	console.log("checklistAffidamenti2.js updateValues BEGIN");
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
	console.log("checklistAffidamenti2.js updateValues END");
}


function validazione(isBozza, salvaDefinitivo) {
	console.log("checklistAffidamenti2.js validazione BEGIN, isBozza=" + isBozza + " ,salvaDefinitivo=" + salvaDefinitivo);
	console.log("checklistAffidamenti2.js validazione, isChecklistDocumentale=" + isChecklistDocumentale);


	var datacontrolloCorretta = validazioneDataControllo();

	//applico controllo sulla "datacontrollo" solo sulle bozze delle checklist documentali
	if (isChecklistDocumentale && isBozza && !datacontrolloCorretta) {
		visualizzaErrorMsg2('FORMAT_DATACONTROLLO_ERR');
		return false;
	}
	// La validazione non va fatta per le bozze.
	if (isBozza) {
		return true;
	}

	// controllo che siano valorizzati tutti i campi con l'attributo "required"
	var ris = validazioneForm();
	console.log("checklistAffidamenti2.js validazione ris=" + ris);


	if (isChecklistDocumentale) {
		console.log("checklistAffidamenti2.js validazione, isChecklistDocumentale TRUE TRUE");
		//	console.log("checklistAffidamenti2.js validazione isChecklistDocumentale TRUE TRUE ris="+ris);
		if (!ris) {
			console.log("checklistAffidamenti2.js validazione validazioneForm non superata ");
			visualizzaErrorMsg2('REQUIRED_ERR');
			return false;
		} else {
			if (!datacontrolloCorretta) {
				visualizzaErrorMsg2('FORMAT_DATACONTROLLO_ERR');
				return false;
			}
			console.log("checklistAffidamenti2.js validazione validazioneForm superata ");
			// verifico valorizzazione campo "esito"
			if ($("[type=checkbox][name=esitoParziale][checked=checked]").length == 0 &&
				$("[type=checkbox][name=esitoDefinitivo][checked=checked]").length == 0) {
				console.log("checklistAffidamenti2.js validazione esito non valorizzato ");
				visualizzaErrorMsg2('REQUIRED_ESITO_ERR');
				return false;
			}

			// verifico che i campi note non superino i 4000 char

			var noteEsitoParziale = $("#noteEsitoParziale");
			console.log("checklistAffidamenti2.js validazione noteEsitoParziale= [" + noteEsitoParziale.val().length + "]");

			noteEsitoParziale.attr({ class: "" });
			// rimuovo anche il div con la scritta dell'errore
			$('#noteEsitoParziale-error').remove();

			if (noteEsitoParziale.val().length > 4000) {
				console.log("checklistAffidamenti2.js validazione noteEsitoParziale troppo lungo");

				// bordo di rosso i campi
				noteEsitoParziale.attr({ class: "error" });
				var $label = '<div id="noteEsitoParziale-error" class="error" for="dataControllo">Consentiti 4000 caratteri massimi</div>';
				noteEsitoParziale.after($label);

				return false;
			}


			var noteEsitoDefinitivo = $("#noteEsitoDefinitivo");
			console.log("checklistAffidamenti2.js validazione noteEsitoDefinitivo= [" + noteEsitoDefinitivo.val().length + "]");

			noteEsitoDefinitivo.attr({ class: "" });
			// rimuovo anche il div con la scritta dell'errore
			$('#noteEsitoDefinitivo-error').remove();

			if (noteEsitoDefinitivo.val().length > 4000) {
				console.log("checklistAffidamenti2.js validazione noteEsitoDefinitivo troppo lungo");

				// bordo di rosso i campi
				noteEsitoDefinitivo.attr({ class: "error" });
				var $label = '<div id="noteEsitoDefinitivo-error" class="error" for="dataControllo">Consentiti 4000 caratteri massimi</div>';
				noteEsitoDefinitivo.after($label);

				return false;
			}

		}

		return true;

	} else {

		//salvaDefinitivo=="D", sono in una "checklist in loco" ed ho premuto "salva definitivo"
		console.log("checklistAffidamenti2.js validazione isChecklistDocumentale FALSE FALSE ris=" + ris);

		if (!datacontrolloCorretta) {
			visualizzaErrorMsg2('FORMAT_DATACONTROLLO_ERR');
			return false;
		}

		if (salvaDefinitivo == "D" && !ris) {
			console.log("checklistAffidamenti2.js validazione isChecklistDocumentale secondo IF ");
			return false;
		}
		return (ris);
	}
}


// controllo che il campo "dataControllo" abbia un formato corretto
function validazioneDataControllo() {
	console.log("checklistAffidamenti2.js validazioneDataControllo BEGIN");

	var dataOK = false;
	var reg = /(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d/;
	var dataControllo = $("#dataControllo");
	if (dataControllo.val().trim()) {
		console.log("checklistAffidamenti2.js validazioneDataControllo dataControllo=[" + dataControllo.val() + "]");
		if (dataControllo.val().match(reg)) {
			console.log("checklistAffidamenti2.js validazioneDataControllo dataControllo VALIDA");
			dataOK = true;
		} else {
			console.log("checklistAffidamenti2.js validazioneDataControllo dataControllo NON VALIDA");
			dataOK = false;
		}
	}

	dataControllo.attr({ class: "" });
	// rimuovo anche il div con la scritta "Campo obbligatorio"
	$('#dataControllo-error').remove();

	//mostro errore
	if (!dataOK) {
		console.log("checklistAffidamenti2.js validazioneDataControllo mostro rosso");

		// bordo di rosso i campi
		dataControllo.attr({ class: "error" });
		var $label = '<div id="dataControllo-error" class="error" for="dataControllo">Formato non valido</div>';
		dataControllo.after($label);
		//console.log("checklistAffidamenti2.js validazioneDataControllo mostro rosso label ["+$label+"]");
	}

	console.log("checklistAffidamenti2.js validazioneDataControllo END, dataOK=" + dataOK);
	return dataOK;
}

// controllo che siano valorizzati tutti i campi con l'attributo "required"
function validazioneForm() {
	console.log("checklistAffidamenti2.js validazioneForm BEGIN");
	var ret = true;
	// per ChecklistDocumentale: attoConcessioneContributo, firmaResponsabile, luogoControllo, dataControllo

	$("input, select, textarea").each(function () {
		var $this = $(this);

		if ($this.is("[required='']")) {
			console.log("checklistAffidamenti2.js validazioneForm campo required [" + $this.attr("name") + "] valore=[" + $this.val() + "] class 2 = [" + $this.attr("class") + "]");

			// elimino eventuali errori segnalati da un precedente controllo
			if ($this.attr("class") == "error") {
				//console.log("checklistAffidamenti2.js validazioneForm class error found");
				$this.attr({ class: "" });

				// rimuovo anche il div con la scritta "Campo obbligatorio"
				var idd = $this.attr("name") + '-error';
				//console.log("checklistAffidamenti2.js validazioneForm idd =["+idd+"]");
				$('#' + idd).remove();
			}

			//controllo se string vuota
			if (!$this.val().trim()) {
				console.log("checklistAffidamenti2.js validazioneForm campo required [" + $this.attr("name") + "] nullo");
				console.log("checklistAffidamenti2.js validazioneForm failed");

				// bordo di rosso i campi
				$this.attr({ class: "error" });

				var $label = '<div id="' + $this.attr("name") + '-error" class="error" for="' + $this.attr("name") + '">Campo obbligatorio</div>';
				$this.after($label);
				ret = false;
				return;
			}
		}

	});

	console.log("checklistAffidamenti2.js validazioneForm END, ret=" + ret);
	return ret;
}



function agganciaDivEsiti() {
	console.log("checklistAffidamenti2.js agganciaDivEsiti BEGIN");
	var html = "";
	html += "<div id='divEsiti' class='toprint'>";
	html += "  <table id='esitiVerifica' cellpadding='0' cellspacing='0'> ";
	html += "   <tbody>";
	html += "    <tr> ";
	html += "     <th class='section' colspan='4'><h4>Esiti verifica affidamento</h4></th> ";
	html += "    </tr> ";
	html += "    <tr> ";
	html += "     <th class='firstCol'>ESITO INTERMEDIO</th> ";
	html += "     <td class='esitoCol'><label>Regolare <input type='checkbox' name='esitoParziale' id='checkboxEsitoParzialePositivo' value='POSITIVO'></label> <label>Irregolare <input type='checkbox' name='esitoParziale' id='checkboxEsitoParzialeNegativo' value='NEGATIVO'> </label></td> ";
	html += "     <td class='esitoCol'><label>con rettifica <input type='checkbox' name='esitoParzialeConRettifica' id='checkboxEsitoParzialeConRettifica' value='S'></label></td> ";
	html += "     <td><textarea cols='' id='noteEsitoParziale' name='txtNoteEsitoParziale' placeholder='Note' rows=''></textarea></td> ";
	html += "    </tr> ";
	html += "    <tr> ";
	html += "     <th class='firstCol'>ESITO DEFINITIVO</th> ";
	html += "     <td class='esitoCol'><label>Regolare <input type='checkbox' name='esitoDefinitivo' id='checkboxEsitoDefinitivoPositivo' value='POSITIVO'></label> <label>Irregolare <input type='checkbox' name='esitoDefinitivo' id='checkboxEsitoDefinitivoNegativo' value='NEGATIVO'> </label></td> ";
	html += "     <td class='esitoCol'><label>con rettifica <input type='checkbox' name='esitoDefinitivoConRettifica' id='checkboxEsitoDefinitivoConRettifica' value='S'></label></td> ";
	html += "     <td><textarea cols='' id='noteEsitoDefinitivo' name='txtNoteEsitoDefinitivo' placeholder='Note' rows=''></textarea></td> ";
	html += "    </tr> ";
	html += "   </tbody>";
	html += "  </table> ";
	html += "</div> ";
	$('#checklistHtmlContent').append(html);
	console.log("checklistAffidamenti2.js agganciaDivEsiti END");
}


function isNull(val) {
	return (val == null || val == "");
}

function isListaVuota(lista) {
	return (lista == null || lista.length == 0);
}

function gestioneCheckbox() {
	console.log("checklistAffidamenti2.js gestioneCheckbox BEGIN");
	/* checkbox come radio */
	$("input:checkbox").click(function () {
		var group = "input:checkbox[name='" + $(this).prop("name") + "']";
		$(group).not(this).prop("checked", false).attr('checked', false);
	});

	/* __________seleziona-deseleziona tutti________*/
	$("input.allsi").click(function () {
		console.log("checklistAffidamenti2.js gestioneCheckbox input.allsi");
		$(".genTab tbody input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("input.allsi").prop("checked", true);
			$("table.genTab input:checkbox.si").each(function () {
				$(this).prop("checked", true);
			});
		} else {
			$(".genTab tbody input:checkbox.si").each(function () {
				$(this).prop("checked", false);
				$("input.allsi").prop("checked", false);
			});
		}
	});

	$("input.allno").click(function () {
		$(".genTab tbody input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("input.allno").prop("checked", true);
			$("table.genTab input:checkbox.no").each(function () {
				$(this).prop("checked", true);
			});
		} else {
			$(".genTab tbody input:checkbox.no").each(function () {
				$(this).prop("checked", false);
				$("input.allno").prop("checked", false);
			});
		}
	});

	$("input.allna").click(function () {
		$(".genTab tbody input:checkbox").prop("checked", false).attr('checked', false);
		if ($(this).is(':checked')) {
			$("input.allna").prop("checked", true);
			$(".genTab tbody input:checkbox.na").each(function () {
				$(this).prop("checked", true);
			});
		} else {
			$(".genTab tbody input:checkbox.na").each(function () {
				$(this).prop("checked", false);
				$("input.allna").prop("checked", false);
			});
		}
	});
	console.log("checklistAffidamenti2.js gestioneCheckbox END");
}


/* FUNCTION */

/*
 * getStyleObject Plugin for jQuery JavaScript Library
 * From: http://upshots.org/?p=112

Basic usage:
$.fn.copyCSS = function(source){
  var styles = $(source).getStyleObject();
  this.css(styles);
}
*/


(function ($) {
	$.fn.getStyleObject = function () {
		var dom = this.get(0);
		var style;
		var returns = {};
		if (window.getComputedStyle) {
			var camelize = function (a, b) {
				return b.toUpperCase();
			};
			style = window.getComputedStyle(dom, null);
			for (var i = 0, l = style.length; i < l; i++) {
				var prop = style[i];
				var camel = prop.replace(/\-([a-z])/g, camelize);
				var val = style.getPropertyValue(prop);
				returns[camel] = val;
			};
			return returns;
		};
		if (style = dom.currentStyle) {
			for (var prop in style) {
				returns[prop] = style[prop];
			};
			return returns;
		};
		return this.css();
	}
})(jQuery);


// Restituisce un oggetto di tipo FormData contenente
function creaFormData() {
	//var fileDaAllegare = getFileDaAllegare();			// Metodo definito in multiUpload.js per il MULTI-UPLOAD degli allegati.
	var checklistHtml = $("#checklistHtmlContent")[0].outerHTML;

	var formData = new FormData();
	formData.append("checklistHtml", checklistHtml);
	/*
	for(i = 0; i < fileDaAllegare.length; ++ i) {
		formData.append("fileVerbali", fileDaAllegare[i]);
		formData.append("nomiFileVerbali", fileDaAllegare[i].name);
	};
	*/
	return formData;
}


/* ****************************************************************** */
/* INIZIO FUNZIONI PER POPUP RICHIEDI INTEGRAZIONE (Jira PBANDI-2773) */
/* ****************************************************************** */

function creaPopupIntegra() {
	if ($('#divIntegra').length == 0) {
		var html = "";
		html = html + "<div id='divIntegra' style='display:none'>";
		//html = html + "  <table id='tblIntegra'>";
		//html = html + "    <tr>";
		//html = html + "      <td><b>Inserire la motivazione per cui si richiede l&rsquo;integrazione.</b></td>";
		//html = html + "    </tr>";
		//html = html + "    <tr>";
		//html = html + "      <td><textarea id='noteIntegra' rows='' cols=''></textarea></td>";
		//html = html + "    </tr>";
		//html = html + "  </table>";
		//html = html + "<textarea id='noteIntegra' onkeyup='maxlength(this,3500)' rows='' cols='140'></textarea>";
		html = html + "<textarea id='noteIntegra' rows='' cols='140'></textarea>";
		//html = html + "<p>Hai a disposizione ancora <span id='conta'>3500</span> caratteri</p>";
		html = html + "</div>";
		$('#divPopup').before(html);

		//autosize($('#noteIntegra'));

		/*-------- Luigi 25072019
		//-------- Prova per aggiunta della editor toolbar
		// ------- https://www.jqueryscript.net/text/Rich-Text-Editor-jQuery-RichText.html -----------*/
		$('#noteIntegra').richText();
		/*----------------------*/

	}
}

function maxlength(area, max) {
	var conta = max - area.value.length;
	var id_campo = $('#conta');
	if (id_campo != null) {
		$('#conta').html(conta);
	}
	if (conta < 0) {
		area.value = area.value.substring(0, max);
		if (id_campo != null) {
			$('#conta').html('0');
		}
	}
}

function apriPopupIntegra() {
	var dialog = $("#divIntegra").dialog({
		autoOpen: false,
		title: 'Motivazione richiesta integrazione',
		modal: true,
		resizable: true,
		width: '900px',
		close: function (event, ui) {
			noWaitMsg = false;
			dialog.find('#noteIntegra').val('');
			$('.messaggioKo').remove();
		},
		buttons: [
			{
				id: "btn_integra_annulla",
				text: "annulla",
				click: function () {
					$(this).dialog("close");
				}
			},
			{
				id: "btn_integra_salva",
				text: "procedi",
				click: function () {
					$('.messaggioKo').remove();
					var msgErrore = validaCampiIntegra();
					if (msgErrore == '') {

						// Salva in sessione l'html della checklist.
						var checklistHtmlContent = $("#checklistHtmlContent")[0].outerHTML;
						$("#hiddenChecklistHtmlContent").val(checklistHtmlContent);

						// Salva in sessione le note.
						var note = $('#noteIntegra').val();
						$("#hiddenNoteRichiestaIntegrazione").val(note);

						// Chiude la popup ed esegue il metodo MDD associato al click del bottone 'richiedi integrazione'.
						$(this).dialog("close");
						var form = $("#checklistAffidamentoHtml");
						form.attr('action', 'checklistAffidamentoHtml!handleBtSalvaChecklistInBozzaIntegrazione_CLICKED.do');
						form.submit();

					} else {
						dialog.prepend($("<div class='messaggioKo'></div>").html(msgErrore));
					}
				}
			}
		]
	});
	dialog.dialog("open");
}


function validaCampiIntegra() {
	var note = $('#noteIntegra').val();
	if (isNull(note))
		return 'Inserire la motivazione della richiesta di integrazione.';
	return '';
}


/* ***************************************************************** */
/* FINE FUNZIONI PER POPUP RICHIEDI INTEGRAZIONE (Jira PBANDI-2773)  */
/* ***************************************************************** */
