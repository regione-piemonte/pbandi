import os

def aggiungi_testo_in_testa(directory_root, testo_da_aggiungere_ts, testo_da_aggiungere_java):
    # Itera su tutte le sottodirectory e i file nella directory radice
    for root, _, files in os.walk(directory_root):
        for file_name in files:
            # Costruisci il percorso completo del file corrente
            file_path = os.path.join(root, file_name)

            # Verifica se il file è un file di testo (puoi aggiungere altri controlli se necessario)
            if file_path.endswith('.ts'):
                # Leggi il contenuto attuale del file
                with open(file_path, 'r') as file:
                    file_content = file.read()

                # Apri il file in modalità scrittura e scrivi il testo in testa
                with open(file_path, 'w') as file:
                    file.write(testo_da_aggiungere_ts + '\n' + file_content)

                #print(f"Aggiunto testo in testa a {file_path}")

            # Verifica se il file è un file di java (puoi aggiungere altri controlli se necessario)
            if file_path.endswith('.java'):
                try:
                    # Leggi il contenuto attuale del file
                    with open(file_path, 'r') as file:
                        file_content = file.read()

                    # Apri il file in modalità scrittura e scrivi il testo in testa
                    with open(file_path, 'w') as file:
                        file.write(testo_da_aggiungere_java + '\n' + file_content)
                except:
                    print(f"File non elavorato per caratteri speciali: {file_path}")
                #print(f"Aggiunto testo in testa a {file_path}")

# Directory radice delle sottodirectory da esaminare
directory_radice = '.'

# Testo da aggiungere in testa ai file
testo_da_aggiungere_ts = "/*\n * Copyright Regione Piemonte - 2024 \n * SPDX-License-Identifier: EUPL-1.2 \n*/\n"
testo_da_aggiungere_java = '/*******************************************************************************\n* Copyright Regione Piemonte - 2024\n* SPDX-License-Identifier: EUPL-1.2\n******************************************************************************/\n'
# Chiama la funzione per aggiungere il testo in testa ai file
aggiungi_testo_in_testa(directory_radice, testo_da_aggiungere_ts, testo_da_aggiungere_java)

