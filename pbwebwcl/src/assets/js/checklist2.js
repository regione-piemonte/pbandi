
///////////////// PK mie modifiche 

function salvaChecklistDefinitivo(){

	// PK : guardo /ris/js/finanziamenti/pbandi/pbandiweb/checklist.js in TEST
	
	console.log('   checklist2.js -> salvaChecklistDefinitivo -> BEGIN');

	$('#errorMsg p.d1d2').remove();
	$("#checklistHtmlDiv").append('<div id="errorMsg" class="errorMsg" style="display:none;"><span><p>Attenzione! &Egrave; necessario valorizzare i campi obbligatori e definire un esito per tutte le righe evidenziate.</p></span><a href="#" id="errorMsgLink" onclick="chiudiErrorMsg();return false;" >chiudi</a></div>');
	$("#chekListHtml tr.row_check").removeClass('invalid');

	if(!checkVerify() || !validazione() || !wPrint('d')){
		console.log('   checklist2.js -> salvaChecklistDefinitivo ->  VALIDAZIONE OK');
		if(!wPrint('d')){
			$('#errorMsg').append('<p class="d1d2">Attenzione! La compilazione delle sezioni 04.D1 e 04.D2 &egrave; obbligatoria ed esclusiva.</p>');
		};
		$("#errorMsg").show();
		console.log('   checklist2.js -> salvaChecklistDefinitivo ->  END return error');
		return 'KO';
	}else{
		console.log('   checklist2.js -> salvaChecklistDefinitivo ->  nessun ERRORE');
		$("#errorMsg").remove();
		updateValues();
		console.log('   checklist2.js -> salvaChecklistDefinitivo -> END');
		return 'OK';
	}
}

function chiudiErrorMsg(){
	console.log("checklist2.js errorMsg BEGIN");
	$("#errorMsg").hide();
}

function inizializzaCheckList(){
    // scrivo questa function e la inizializzo nella checklist.component.ts
	console.log('   checklist2.js -> inizializzaCheckList -> BEGIN');
	
    //////////// codice preso dalla $(function (){   ///////////////////////////////////////////////////////

	$("#errorMsg").remove();
	$('#widg_btVisualizza').val('visualizza PDF');
	
	$("input#widg_btIndietro").attr('formnovalidate','formnovalidate');
	$("input#widg_btSalvaChecklistInBozza").attr('formnovalidate','formnovalidate');


/* prova per caricare i blocchi per gli appalti */
	if($('.wAppalti').length){

        console.log('   checklist2.js -> inizializzaCheckList -> in wAppalti');

			$('#caricaappalti').remove();
			idSectionAppalti ();

			$(".toggleappalto").click(function() {
				$(this).parents('tbody.appaltotitle').next('tbody.appalto').slideToggle();
				$(this).find("i.fa").toggleClass("fa-chevron-down fa-chevron-up")
			});	
				
			gestioneCheckbox();		
	}
/* fine prova per caricare i blocchi per gli appalti */

	if($('#checklistHtmlDiv').length){

        console.log('   checklist2.js -> inizializzaCheckList -> in checklistHtmlDiv.length BEGIN');
		$('#checklistHtmlDiv').before("<div id=\"dialog\"><p id=\"msg\"></p></div>");
		$("#dialog").hide();
		gestioneCheckbox();
		console.log('   checklist2.js -> inizializzaCheckList -> in checklistHtmlDiv.length END');
	}

	/*-------------------*/
	if($("a#menu_items_mnuGestioneSpesaValidata").length){ // serve a nascondere il pulsante SALVA BOZZA in gestione spesa validata
		$("#widg_btSalvaChecklistInBozza").parent().hide();
	}

	$("#toggleD1").click(function() {
		$('tbody#d1Section').slideToggle('slow');
		$("#toggleD1 i.fa").toggleClass("fa-chevron-down fa-chevron-up")
	});	
	
	$("#toggleD2").click(function() {
		$('tbody#d2Section').slideToggle('slow');
		$("#toggleD2 i.fa").toggleClass("fa-chevron-down fa-chevron-up")
	});	
	
/* LP: permette di inserire il valore della spesa validata della riga 09.4 in un campo span per poter essere stampato sul pdf*/

	if($("span#spesaValidataCLPrint").length < 1){
	  $("#spesaValidataCL").parent().append('<span id="spesaValidataCLPrint" class="alt"></span>');
	}
	
	$("#spesaValidataCLPrint").text($("#spesaValidataCL").val());
	
	$("#spesaValidataCL").keyup(function(){
		$("#spesaValidataCLPrint").text($("#spesaValidataCL").val()); 
		$("#spesaValidataCL").val($("#spesaValidataCLPrint").text());	 
	   });


	if($("span#spesaValidataCL2Print").length < 1){
		$("#spesaValidataCL2").parent().append('<span id="spesaValidataCL2Print" class="alt"></span>');
	}
	  
	  $("#spesaValidataCL2Print").text($("#spesaValidataCL2").val());
	  
	  $("#spesaValidataCL2").keyup(function(){
		  $("#spesaValidataCL2Print").text($("#spesaValidataCL2").val()); 
		  $("#spesaValidataCL2").val($("#spesaValidataCL2Print").text());	 
		 });
/*_________*/

console.log('   checklist2.js -> inizializzaCheckList -> END');
//////////////////////////////////////////////////////////////
}

//PK fa le veci di  #widg_btSalvaChecklistInBozza').click(function(event){
function salvaChecklistInBozza(){
	console.log('salvaChecklistInBozza BEGIN');
	updateValues();
	console.log('salvaChecklistInBozza END');
}

////////////////////////////////////////////////
// PK prese da bandi_ui.js
/*
function removeProgressBar() {
	$("#loading").hide().css('z-index', '-1');
	$("#contenitore").removeClass('blur');
	$("#pagina").removeClass('blur');
	$("#portalFooter").removeClass('blur');
}

function progressBar() {
	$('#loading').show().css('z-index', '200000');
	$("#contenitore").addClass('blur');
	$("#pagina").addClass('blur');
	$("#portalFooter").addClass('blur');
}

function widthOnResize() {
	var wb = parseInt($('#colonnaDestra').width());
	var w = parseInt($("#chekListHtml").outerWidth());
	var maxLength = wb - wb * 30 / 100;
	$("#fixH").outerWidth(w);
	$('#widg_cmbBando').css({ 'width': maxLength });

	$('#backOfficeRicercaDocumentazione #widg_cmbBeneficiari').css({ 'width': maxLength });

	$('#widg_cmbBando > option').text(function (i, text) {

		if (text.length > maxLength / 6) {
			return text.substr(0, maxLength / 6) + '...';
		}
	});
	$('#backOfficeRicercaDocumentazione #widg_cmbBeneficiari > option').text(function (i, text) {

		if (text.length > maxLength / 6) {
			return text.substr(0, maxLength / 6) + '...';
		}
	});
}
*/






/////////////////////// ORIG ///////////////////////////////////


/* FUNCTION */

/*
(function($){
    $.fn.getStyleObject = function(){
        var dom = this.get(0);
        var style;
        var returns = {};
        if(window.getComputedStyle){
            var camelize = function(a,b){
                return b.toUpperCase();
            };
            style = window.getComputedStyle(dom, null);
            for(var i = 0, l = style.length; i < l; i++){
                var prop = style[i];
                var camel = prop.replace(/\-([a-z])/g, camelize);
                var val = style.getPropertyValue(prop);
                returns[camel] = val;
            };
            return returns;
        };
        if(style = dom.currentStyle){
            for(var prop in style){
                returns[prop] = style[prop];
            };
            return returns;
        };
        return this.css();
    }
})(jQuery);

   
//Floating Header of long table  PiotrC
(function ( $ ) {
    var tableTop,tableBottom,ClnH;
    $.fn.FixedHeader = function(){
       // var w = this.outerWidth();
		tableTop=this.offset().top,
        tableBottom=this.outerHeight()+tableTop;
        //Add Fixed header
        this.before('<table id="fixH"></table>');
        //Clone Header
        ClnH = $("#fixH").html(this.find("thead").clone());
        gestioneCheckbox();
		//set style
        ClnH.css({'position':'fixed', 'top':'0', 'display':'none',
        'border-collapse': this.css('border-collapse'),
		'border-spacing': this.css('border-spacing'),
        'margin-left': this.css('margin-left')
		//,'width': this.css('width')  // ATTENZIONE! la larghezza viene ricalcolata con la funzione widthOnResize() in bandi_ui.js          
        });
		
		//ClnH.width(w);
        //rewrite style cell of header
        $.each(this.find("thead>tr>th"), function(ind,val){
           // $(ClnH.find('tr>th')[ind]).css($(val).getStyleObject());
        });
    return ClnH;}
    
    $.fn.moveScroll=function(){
        var offset = $(window).scrollTop();
        if (offset > tableTop && offset<tableBottom){
            if(ClnH.is(":hidden"))ClnH.show();
            $("#fixH").css('margin-left',"-"+$(window).scrollLeft()+"px");
			$('.up').show();
        }
        else if (offset < tableTop || offset>tableBottom){
            if(!ClnH.is(':hidden'))ClnH.fadeOut();
			$('.up').hide();
        }

    };
	
})( jQuery );
*/

function updateValues(){

	console.log('   checklist2.js -> updateValues ->  BEGIN');

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

	console.log('   checklist2.js -> updateValues ->  END');
}



function gestioneCheckbox(){

	console.log('   checklist2.js -> gestioneCheckbox -> BEGIN');

/* checkbox come radio */	
	$("input:checkbox").click(function(){	
		var group = "input:checkbox[name='"+$(this).prop("name")+"']";
        $(group).not(this).prop("checked",false).attr('checked', false);
	});	
	
/* __________seleziona-deseleziona tutti________*/	
	$("input.allsi").click(function(){
	 	$(".genTab tbody input:checkbox").prop("checked",false).attr('checked', false);
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
	
	$("input.allno").click(function(){
	 	$(".genTab tbody input:checkbox").prop("checked",false).attr('checked', false);
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
	
	$("input.allna").click(function(){
	 	$(".genTab tbody input:checkbox").prop("checked",false).attr('checked', false);
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
	
/* Per la sezione D1*/
	$("input#checkD1si").click(function(){
	$('#headD1 tr , #d1Section tr').removeClass('invalid');	 	
	 	$(".subcontentD1 input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $(".subcontentD1 input:checkbox.si").each(function () {
                $(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled',false).removeClass('is-disabled');
            });

        } else {
            $(".subcontentD1 input:checkbox.si").each(function () {
                $(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled',true);
            });
        }	
	});
	
	$("input#checkD1no").click(function(){
	$('#headD1 tr , #d1Section tr').removeClass('invalid');	 	
	 	$(".subcontentD1 input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $(".subcontentD1 input:checkbox.no").each(function () {
                $(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled',false).removeClass('is-disabled');
            });
        } else {
            $(".subcontentD1 input:checkbox.no").each(function () {
                $(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled',true);
            });
        }	
	});
	
	$("input#checkD1na").click(function(){
	$('#headD1 tr , #d1Section tr').removeClass('invalid');	 	
	 	$(".subcontentD1 input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $(".subcontentD1 input:checkbox.na").each(function () {
                $(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled',false).removeClass('is-disabled');
            });
        } else {
            $(".subcontentD1 input:checkbox.na").each(function () {
                $(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled',true);
            });
        }
	});
	
		/* Per la sezione D2*/
	$("input#checkD2si").click(function(){
	$('#headD2 tr , #d2Section tr').removeClass('invalid');	 	
	 	$(".subcontentD2 input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $(".subcontentD2 input:checkbox.si").each(function () {
                $(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled',false).removeClass('is-disabled');
            });

        } else {
            $(".subcontentD2 input:checkbox.si").each(function () {
                $(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled',true);
            });
        }
		
	});
	$("input#checkD2no").click(function(){
	$('#headD2 tr , #d2Section tr').removeClass('invalid');	 	
	 	$(".subcontentD2 input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $(".subcontentD2 input:checkbox.no").each(function () {
                $(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled',false).removeClass('is-disabled');
            });

        } else {
            $(".subcontentD2 input:checkbox.no").each(function () {
                $(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled',true);
            });
        }
	});
	$("input#checkD2na").click(function(){
	$('#headD2 tr , #d2Section tr').removeClass('invalid');	 	
	 	$(".subcontentD2 input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $(".subcontentD2 input:checkbox.na").each(function () {
                $(this).prop("checked", true);
				$(this).parent().parent().find(".note textarea").prop('disabled',false).removeClass('is-disabled');
            });

        } else {
            $(".subcontentD2 input:checkbox.na").each(function () {
                $(this).prop("checked", false);
				$(this).parent().parent().find(".note textarea").prop('disabled',true);
            });
        }
	});
	
	console.log('   checklist2.js -> gestioneCheckbox -> END');
}



function idSectionAppalti(){
	$('#chekListHtml tbody.appaltotitle').each(function(index) {
		var K =  parseInt(index)+1;	
		var counter = $('input#counterappalti').val();
		var chrApp = $('input#chrappalti').val();
		console.log(counter, chrApp);
		$(this).find('td.codiceControllo').text(counter+'.'+K);
		$(this).attr('id','titleappalto_'+index);
		$(this).next('tbody.appalto').attr('id','section_'+index);
		$(this).find('td.esito input').attr('id','chkappalto_'+index+'_all');
		$(this).find('td.esito input').attr('name','chkappalto_'+index+'_all');
		$('.descrizioneAttivitaDiControllo').each(function(){
			$(this).find('span.chrApp').text(chrApp);  
			  });
	});
	
	$('#chekListHtml tbody.appalto tr').each(function(index) {
		var idPadre = $(this).parents('tbody').attr('id');
		var K =  parseInt(idPadre.split('_')[1])+1;
		var counter = $('input#counterappalti').val();
		if ($(this).attr('class')== 'row_check'){
			var nRow = $(this).find('td.codiceControllo').text().split('.')[2];
			$(this).find('td.codiceControllo').text(counter+'.'+K+'.'+nRow);
			}
		$(this).find('td.esito input').attr('id','chkappalto_'+index);
		$(this).find('td.esito input').attr('name','chkappalto_'+index);
		$(this).find('td.note textarea').attr('name','noteappalto_'+index);
	});
	
checkAllAppalti ();	
}

function checkAllAppalti (){
/* Per la sezione appalti*/
	$("input.si_all_app").click(function(el){
		var idPadre = $(this).parents('tbody').attr('id');
		var K = idPadre.split('_')[1];
	 	$("#section_"+K+" input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $("#section_"+K+" input:checkbox.si").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#section_"+K+" input:checkbox.si").each(function () {
                $(this).prop("checked", false);
            });
        }
	});
	$("input.no_all_app").click(function(){
		var idPadre = $(this).parents('tbody').attr('id');
		var K = idPadre.split('_')[1];
	 	$("#section_"+K+" input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $("#section_"+K+" input:checkbox.no").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#section_"+K+" input:checkbox.no").each(function () {
                $(this).prop("checked", false);
            });
        }
	});
	$("input.na_all_app").click(function(){
		var idPadre = $(this).parents('tbody').attr('id');
		var K = idPadre.split('_')[1];
	 	$("#section_"+K+" input:checkbox").prop("checked",false).attr('checked', false);
		if ($(this).is(':checked')) {
            $("#section_"+K+" input:checkbox.na").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#section_"+K+" input:checkbox.na").each(function () {
                $(this).prop("checked", false);
            });
        }
	});	
}
/*
function reloadGestioneChecklist() {
	$("#checklistHtmlDiv").prepend($("<form id='gestioneChecklistForm' action='gestioneCheckList.do' method='post'> </form>"));
	var form = $("#gestioneChecklistForm");
	form.submit();
}

function goToCheckListDispatcher() {
	$("#checklistHtmlDiv").prepend($("<form id='goToCheckListDispatcher' action='checkListHtmlDispatcher.do?tipoOperazione=saveDefinitivo&tipoModulo=INLOCO' method='post'> </form>"));
	var form = $("#goToCheckListDispatcher");
	form.submit();
}
*/
function removePrevError(nome){
	$( "#"+nome ).removeAttr("class");
	$( "#"+nome+"-error").remove();
}

function createErrorField(nome){
	$( "#"+nome ).attr({class: "error"});
	var $label = '<div id="'+nome+'-error" class="error" for="'+nome+'">Campo obbligatorio</div>';
	$( "#"+nome ).after($label);
}

function createErrorFieldMinLength(nome, len){
	$( "#"+nome ).attr({class: "error"});
	var $label = '<div id="'+nome+'-error" class="error" for="'+nome+'">Inserire almeno '+len+' caratteri</div>';
	$( "#"+nome ).after($label);
}

function createErrorFieldMsg(nome, msg){
	$( "#"+nome ).attr({class: "error"});
	var $label = '<div id="'+nome+'-error" class="error" for="'+nome+'">'+msg+'</div>';
	$( "#"+nome ).after($label);
}

function validazione(){
	console.log('   checklist2.js ->   validazione BEGIN');
	var valRes = true;

	var attoConcessioneContributo = $( "#attoConcessioneContributo" );
	removePrevError("attoConcessioneContributo");
	if(attoConcessioneContributo.length>0 && !attoConcessioneContributo.val().trim()){
		console.log('   checklist2.js -> validazione() attoConcessioneContributo NULLO');
		valRes = false;
		createErrorField("attoConcessioneContributo");
	}

	var spesaValidataCL = $( "#spesaValidataCL" );
	removePrevError("spesaValidataCL");
	if(spesaValidataCL.length>0 && !spesaValidataCL.val().trim()){
		console.log('   checklist2.js -> validazione() spesaValidataCL NULLO');
		valRes = false;
		createErrorField("spesaValidataCL");
	}
		
	var firmaResponsabile = $( "#firmaResponsabile" );
	removePrevError("firmaResponsabile");
	if(firmaResponsabile.length>0 && !firmaResponsabile.val().trim()){
		console.log('   checklist2.js -> validazione() firmaResponsabile NULLO');
		valRes = false;
		createErrorField("firmaResponsabile");
	}
	if(firmaResponsabile.length>0 && firmaResponsabile.val().length<3){
		console.log('   checklist2.js ->  validazione -> firmaResponsabile < 3 char');
		valRes = false;
		createErrorFieldMinLength("firmaResponsabile", 3);
	}
	
	var referenteBeneficiario = $( "#referenteBeneficiario" );
	removePrevError("referenteBeneficiario");
	if(referenteBeneficiario.length>0 && !referenteBeneficiario.val().trim()){
		console.log('   checklist2.js -> validazione() referenteBeneficiario NULLO');
		valRes = false;
		createErrorField("referenteBeneficiario");
	}
	if(referenteBeneficiario.length>0 && referenteBeneficiario.val().length < 3 ){
		console.log('   checklist2.js ->  validazione -> referenteBeneficiario < 3 char');
		valRes = false;
		createErrorFieldMinLength("referenteBeneficiario", 3);
	}
	
	removePrevError("irregSi");
	if($("#irregSi").is(":checked")) {
		console.log('   checklist2.js -> validazioneCLV() irregSi CHECKED');
		var descIrregolarita = $( "#descIrregolarita" );
		if(descIrregolarita.length>0 && !descIrregolarita.val().trim()){
			console.log('   checklist2.js -> validazioneCLV() descIrregolarita NULLO');
			valRes = false;
			createErrorField("descIrregolarita");
		}
	}

	var dataControllo = $( "#dataControllo" );
	removePrevError("dataControllo");
	if(dataControllo.length>0 && !dataControllo.val().trim()){
		console.log('   checklist2.js -> validazione() dataControllo NULLO');
		valRes = false;
		createErrorFieldMsg("dataControllo", "Inserire la data del controllo in loco");
		dataControllo: "Inserire la data del controllo in loco"
	}

	var luogoControllo = $( "#luogoControllo" );
	removePrevError("luogoControllo");
	if(luogoControllo.length>0 && !luogoControllo.val().trim()){
		console.log('   checklist2.js -> validazione() luogoControllo NULLO');
		valRes = false;
		createErrorField("luogoControllo");
	}
	return valRes;
}

/*
function salvaBozzaAut(){
	$('#widg_btSalvaChecklistInBozza').click();
	console.log('salvataggio bozza');
}
*/


function checkVerify(){ // verifico che tutti i checkbox siano selezionati
	console.log('   checklist2.js -> checkVerify BEGIN');
	var flagVer = true;
	$("#chekListHtml tr.row_check").removeClass('invalid');
	//$("#chekListHtml .toprint tr.row_check").each(function(){
	// PK escludo le sezioni 04.D1 e 04.D2 che hanno rispettivamente le classi subcontentD1 e subcontentD2
	// queste sezioni sono controllate in apposita funzione
	$("#chekListHtml .toprint tr.row_check:not(tr.subcontentD1, tr.subcontentD2)").each(function(){	
		//console.log('   checklist2.js -> checkVerify length='+$(this).find("td.esito input").length);

		if ($(this).find("td.esito input").length>0){
			var esito = $(this).find("td.esito input:checked").length>0;
			//console.log('   checklist2.js -> checkVerify esito='+esito);
			if(!esito){
				console.log(' >>>>>>>  checklist2.js -> checkVerify length checked='+$(this).find("td.esito input:checked").length);
				$(this).addClass('invalid');
				flagVer = false;
			}
		}
	 });
	 console.log('   checklist2.js -> checkVerify END, return='+flagVer);
	 return flagVer;
}


function wPrint(k){ /* serve a verificare che solo una delle due sezioni D1 o D2 sia stata compilata*/

	console.log('   checklist2.js ->  wPrint BEGIN');
	flagPrint = true;

	if($('#d1Section').length){
			$('#d1Section tr').each(function(){
				if ($(this).find('td input').is(':checked') || $.trim($(this).find('td.note textarea').val()).length > 0 ) {
					chkD1 = 1;
					console.log('CH1 = ',chkD1);
					return false
				}else{
					chkD1 = 0;
					}
			});
				
			$('#d2Section tr').each(function(){
				if ($(this).find('td input').is(':checked') || $.trim($(this).find('td.note textarea').val()).length > 0) {
					chkD2 = 1;
					console.log('CH2 = ',chkD2);
					return false
				}else{
					chkD2 = 0;
					}
			});
		if(k!='b'){
				if(chkD1 == 0 && chkD2 == 1){
					flagPrint = true;
					$('#headD1 tr , #headD2 tr, #d1Section tr, #d2Section tr').removeClass('invalid');
					$('tbody#headD1').removeClass('toprint').addClass('noprint');
					$('tbody#headD2').removeClass('noprint').addClass('toprint');
					$('#d1Section, tr.hD1').removeClass('toprint').addClass('noprint');
					$('#d2Section, tr.hD2').removeClass('noprint').addClass('toprint');
				}else if(chkD1 == 1 && chkD2 == 0){
					flagPrint = true;
					$('#headD1 tr , #headD2 tr, #d1Section tr, #d2Section tr').removeClass('invalid');
					$('tbody#headD2').removeClass('toprint').addClass('noprint');
					$('tbody#headD1').removeClass('noprint').addClass('toprint');
					$('#d2Section, tr.hD2').removeClass('toprint').addClass('noprint');
					$('#d1Section, tr.hD1').removeClass('noprint').addClass('toprint');
				}else if((chkD1 == 1 && chkD2 == 1)||(chkD1 == 0 && chkD2 == 0)||(chkD1 == '' && chkD2 == '')){
					$('#headD1 tr , #headD2 tr, #d1Section tr, #d2Section tr').addClass('invalid');
					flagPrint = false;
				}
		}else{
				if(chkD1 == 0 && chkD2 == 1){
					flagPrint = true;
					$('#headD1 tr , #headD2 tr, #d1Section tr, #d2Section tr').removeClass('invalid');
					$('tbody#headD1').removeClass('toprint').addClass('noprint');
					$('tbody#headD2').removeClass('noprint').addClass('toprint');
					$('#d1Section, tr.hD1').removeClass('toprint').addClass('noprint');
					$('#d2Section, tr.hD2').removeClass('noprint').addClass('toprint');
				}else if(chkD1 == 1 && chkD2 == 0){
					flagPrint = true;
					$('#headD1 tr , #headD2 tr, #d1Section tr, #d2Section tr').removeClass('invalid');
					$('tbody#headD2').removeClass('toprint').addClass('noprint');
					$('tbody#headD1').removeClass('noprint').addClass('toprint');
					$('#d2Section, tr.hD2').removeClass('toprint').addClass('noprint');
					$('#d1Section, tr.hD1').removeClass('noprint').addClass('toprint');
				}else if((chkD1 == 1 && chkD2 == 1)){
					$('#headD1 tr , #headD2 tr, #d1Section tr, #d2Section tr').addClass('invalid');
					flagPrint = false;
				}
			
		}
			
	}
		console.log('   checklist2.js ->  wPrint flagPrint = ',flagPrint);
		console.log('   checklist2.js ->  wPrint END, return='+flagPrint);
		return flagPrint;
}
