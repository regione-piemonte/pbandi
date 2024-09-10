# Prodotto
PBANDI
# Descrizione del prodotto
Il prodotto ha come obiettivo generale quello di mettere a disposizione una serie di moduli applicativi per la gestione del ciclo di vita delle agevolazioni pubbliche, come finanziamenti, contributi o altre agevolazioni, concesse dall'ente gestore, fornendo all’utente (PA e beneficiari) tutte le funzioni necessarie al controllo e monitoraggio degli iter amministrativi. In particolare, tali moduli avranno come obiettivo primario quello di generalizzare il più possibile le diverse fasi del ciclo di vita della procedura di attuazione, rispettando le specificità delle singole materie coinvolte. La presenza di un unico sistema, costituito da un insieme di moduli applicativi configurabili, favorisce una serie di aspetti, tra cui:

La centralizzazione delle logiche e delle regole legate alle agevolazioni pubbliche;
La riduzione del tempo di operatività legato alla gestione di nuove linee di finanziamento;
La focalizzazione dei sistemi applicativi esterni sulle funzionalità specifiche del proprio business;
Una generale economia di scala e maggiore sinergia e coesione all’interno del Sistema Informativo regionale.

Il prodotto si compone sostanzialmente di otto componenti web con i rispettivi moduli angular:

- [PBWORKSPACE](https://github.com/regione-piemonte/pbandi/tree/main/pbworkspace)  	Workspace per BPM
- [PBWORKSPACEWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbworkspacewcl)  Workspace per BPM (Front-End in Angular)
- [PBWEB](https://github.com/regione-piemonte/pbandi/tree/main/pbweb)  	        Gestione fase di rendicontazione
- [PBWEBWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbwebwcl)  	    Gestione fase di rendicontazione (Front-End in Angular)
- [PBWEBBO](https://github.com/regione-piemonte/pbandi/tree/main/pbwebbo)  	    Gestione back-office
- [PBWEBBOWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbwebbowcl)  	Gestione back-office (Front-End in Angular)
- [PBWEBCERT](https://github.com/regione-piemonte/pbandi/tree/main/pbwebcert)  	    Certificazione dei finanziamenti pubblici erogati 
- [PBWEBCERTWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbwebcertwcl)  	Certificazione dei finanziamenti pubblici erogati (Front-End in Angular)
- [PBWEBEROG](https://github.com/regione-piemonte/pbandi/tree/main/pbweberog)  	    Gestione dell'erogazione dei finanziamenti pubblici
- [PBWEBEROGWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbweberogwcl)  	Gestione dell'erogazione dei finanziamenti pubblici (Front-End in Angular)
- [PBWEBFIN](https://github.com/regione-piemonte/pbandi/tree/main/pbwebfin)  	    Gestione integrazione con Bilancio Regionale
- [PBWEBFINWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbwebfinwcl)  	Gestione integrazione con Bilancio Regionale (Front-End in Angular)
- [PBWEBRCE](https://github.com/regione-piemonte/pbandi/tree/main/pbwebrce)  	    Rimodulazione del conto economico
- [PBWEBRCEWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbwebrcewcl)  	Rimodulazione del conto economico (Front-End in Angular)
- [PBGESTFINBO](https://github.com/regione-piemonte/pbandi/tree/main/pbgestfinbo)  	Servizi REST trasversali del BackOffice Gestionale Finanziamenti 
- [PBGESTFINBOWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbgestfinbowcl)  Servizi REST trasversali del BackOffice Gestionale Finanziamenti (Front-End in Angular)

una componente trasversale per le componenti Angular
- [PBCOMMONWCL](https://github.com/regione-piemonte/pbandi/tree/main/pbcommonwcl)     Componente funzionalità comuni per BPM (Angular)

tre componenti di esposizione servizi:
- [PBSERVWELFARE](https://github.com/regione-piemonte/pbandi/tree/main/pbservwelfare)   Componente di esposizione servizi per i bandi welfare 
- [PBSERVIZIT](https://github.com/regione-piemonte/pbandi/tree/main/pbservizit)  	Servizi REST trasversali  	
- [PBSERVREST](https://github.com/regione-piemonte/pbandi/tree/main/pbservrest)  	Servizi esposti verso fruitori esterni 	

una componente trasversale di utility per la gesione dei file da archiviare
- [FILESTORAGE](https://github.com/regione-piemonte/pbandi/tree/main/filestorage)  	Componente trasversale (jar) per la gestione degli storage su fileSystem

due componenti di utilizi per le componenti online e batch
- [UTILITYBATCH](https://github.com/regione-piemonte/pbandi/tree/main/utilitybatch)    Package plSql di utility batch 
- [UTILITIONLINE](https://github.com/regione-piemonte/pbandi/tree/main/utilityonline)   Componente logica plsql usata dall'online 

tre componenti Sql-Schema DB
- [PBANDIDB](https://github.com/regione-piemonte/pbandi/tree/main/pbandidb)        Componente schema pbandi
- [PBANDIDBRO](https://github.com/regione-piemonte/pbandi/tree/main/pbandidbro)      Componente schema pbandi_ro
- [PBANDIDBRW](https://github.com/regione-piemonte/pbandi/tree/main/pbandidbrw)      Componente schema pbandi_rw

sei componenti ORACLE/PLSQL
- [PROCESSO](https://github.com/regione-piemonte/pbandi/tree/main/processo)  	    Gestione del nuovo processo interno  	
- [RESTITUZIONECUP](https://github.com/regione-piemonte/pbandi/tree/main/restituzionecup) Componente pl/sql per procedura restituzione cup  	
- [RETTIFICAFORF](https://github.com/regione-piemonte/pbandi/tree/main/rettificaforf)  	Calcolo importo validato rettificato  	
- [RICHIESTACUP](https://github.com/regione-piemonte/pbandi/tree/main/richiestacup)  	Procedura PLSQL di richiesta CUP  	
- [CAMPIONAMENTO](https://github.com/regione-piemonte/pbandi/tree/main/campionamento)  	Procedura di campionamento che estrae un campione delle operazioni (progetti certificati) da controllare per FSE, FESR e FSC in modo automatico e di tenerne traccia  	
- [CARICAMENTOUTENTI](https://github.com/regione-piemonte/pbandi/tree/main/caricamentoutenti)  Package plsql per caricamento utenti da csv proveniente da frontend applicativo  
# Configurazioni iniziali
Nei file README.md delle singole componenti verranno elencati i dettagli per la loro configurazione iniziale.
# Prerequisiti di sistema
* [Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)
## Software
* [Java Development Kit (jdk): 8](https://www.oracle.com/java)
* [Angular CLI: 10.2.0](https://angular.io)
* [Node: 12.20.1](https://nodejs.org)
* [Maven: 3.2.5](https://maven.apache.org)
* [Ant: 1.6.2](https://ant.apache.org/)
# Installazione
Nei file README.md delle singole componenti verranno elencati i dettagli per la loro installazione.
# Esecuzione dei test
I test di vulnerabilità sono stati eseguiti con HCL AppScan e non sono state 
evidenziate problematiche rilevanti. 
# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).
# Copyrights
(C) Copyright 2024 Regione Piemonte
# License
European Union Public License 1.2