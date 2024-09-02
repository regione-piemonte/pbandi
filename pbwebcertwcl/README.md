# Componente di Prodotto
PBWEBCERTWCL

## Versione
1.8.0

## Descrizione della componente
Si tratta della parte di front-end della componente [PBWEBCERT](../pbwebcert). 

## Configurazioni iniziali
Questa deve essere compilata prima della componente PBWEBCERT. I file da configurare in base alle proprie esigenze, sono sotto la cartella pbwebcertwcl\buildfiles.

## Prerequisiti di sistema
* [Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)
## Software
* [Java Development Kit (jdk): 8](https://www.oracle.com/java)
* [Angular CLI: 10.2.0](https://angular.io)
* [Node: 12.20.1](https://nodejs.org)
* [Maven: 3.2.5](https://maven.apache.org)

## Installazione
Sostituire in tutto il codice, se presenti, i seguenti placeholders con gli opportuni VH dedicati:
* <VH_ESPOSIZIONE_SERVIZI>
* <VH_API_MANAGER>
* <VH_SECURE>
* <VH_DB>
* <VH_DOMICILIARITA_RESIDENZIALITA>
* <VH_OAUTH>
* <VH_APACHE>

Per generare i pacchetti, che dovranno poi essere inglobati nella compoennte java [PBWEBCERT](../pbwebcert), effettuare i seguenti step:

0. Modificare i parametri di configurazione che sono contenuti nei file pbwebcertwcl\buildfiles\environment.prod-rp-01.ts sostituendo <VH_APACHE> con  il Virtual Host Apache su cui saranno presenti le locations
1. cd pbwebcertwcl
2. npm install
3. npm run build-prod
4. rmdir /S /Q node_modules
   (la cartella node_modules viene rimossa dopo l'utilizzo per non occupare spazio)
5. cd dist\prod-rp-01
6. zip -r ..\..\dist_wcl_prod_rp_01.zip ./* -x "./assets/.svn/*"
7. cd ..\..\..
8. xcopy pbwebcertwcl\dist_wcl_prod_rp_01.zip pbwebcert\pbwebcert-web\src\main\webapp\
   (il file .zip viene copiato nella cartella /webapp della componente [PBWEBCERT](../pbwebcert) )
9. del pbwebcertwcl\dist_wcl_prod_rp_01.zip

## Deployment
Questa componente non deve essere deployata ma dovrà essere inglobata nella componente [PBWEBCERT](../pbwebcert).
Seguire le istruzioni del README.md della componente [PBWEBCERT](../pbwebcert)

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”