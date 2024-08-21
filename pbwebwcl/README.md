# Componente di Prodotto
PBWEBWCL

## Versione
1.8.0

## Descrizione della componente
Si tratta della parte di front-end della componente [PBWEB](../pbweb). 

## Configurazioni iniziali
Questa deve essere compilata prima della componente PBWEB. I file da configurare in base alle proprie esigenze, sono sotto la cartella pbwebwcl\buildfiles.

## Prerequisiti di sistema
* [Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.org)
## Software
* [Java Development Kit (jdk): 8](https://www.oracle.org)
* [Angular CLI: 10.2.0](https://angular.io)
* [Node: 12.20.1](https://nodejs.org)
* [Maven: 3.2.5](https://maven.apache.org)

## Installazione
Per generare i pacchetti, che dovranno poi essere inglobati nella compoennte java [PBWEB](../pbweb), effettuare i seguenti step:

0. Modificare i parametri di configurazione che sono contenuti nei file pbworkspacewcl\buildfiles\environment.prod-rp-01.ts sostituendo <vh-di prod> con  il Virtual Host Apache su cui saranno presenti le locations
1. cd pbwebwcl
2. npm install
3. npm run build-prod
4. rmdir /S /Q node_modules
   (la cartella node_modules viene rimossa dopo l'utilizzo per non occupare spazio)
5. cd dist\prod-rp-01
6. zip -r ..\..\dist_wcl_prod_rp_01.zip ./* -x "./assets/.svn/*"
7. cd ..\..\..
8. xcopy pbwebwcl\dist_wcl_prod_rp_01.zip pbweb\pbweb-web\src\main\webapp\
   (il file .zip viene copiato nella cartella /webapp della componente [PBWEB](../pbweb) )
9. del pbwebwcl\dist_wcl_prod_rp_01.zip

## Deployment
Questa componente non deve essere deployata ma dovrà essere inglobata nella componente [PBWEB](../pbweb).
Seguire le istruzioni del README.md della componente [PBWEB](../pbweb)

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”