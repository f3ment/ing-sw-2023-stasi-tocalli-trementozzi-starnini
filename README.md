# Prova Finale di Ingegneria del Software - AA 2022-2023
![alt text](src/main/resources/Images/Publishermaterial/Display_1.jpg)

- ###  [Michelangelo Stasi](https://github.com/Myke01-Poli) <br> michelangelo.stasi@mail.polimi.it
- ###  [Nicolò Tocalli](https://github.com/nicolotocalli) <br> nicolo.tocalli@mail.polimi.it
- ###  [Giuseppe Starnini](https://github.com/Sterning171177) <br> giuseppe.starnini@mail.polimi.it
- ###  [Francesco Trementozzi](https://github.com/f3ment) <br> francesco.trementozzi@mail.polimi.it

Implementazione del gioco da tavolo [MyShelfie](https://www.craniocreations.it/prodotto/my-shelfie).

Il progetto consiste nell'implementazione di un sistema distribuito con tecnologia client-server. Per ogni
partita verrà lanciata una istanza del server in grado di gestire una partita alla volta e due o più client
(uno per giocatore) che possono partecipare ad una sola partita alla volta. Si richiede l'utilizzo del pattern
Model-View-Controller - MVC per progettare l'intero sistema.

### Legenda
| Colore                        |                 Significato                 |
|:------------------------------|:-------------------------------------------:|
| ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |     Funzionalità implementata e testata     |
| ![#c5f015](https://placehold.it/15/ffdd00/ffdd00) | Funzionalità implementata non completamente |
| ![#c5f015](https://via.placeholder.com/15/ff0000/000000?text=+) |        Funzionalità non implementata        |

## Funzionalità implementate

| Funzionalità avanzata          |                      Stato                                               |
|:-------------------------------|:------------------------------------------------------------------------:|
| Regole semplificate            |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| Regole complete                |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| Socket                         |                 ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)        |
| RMI                            |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| GUI                            |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| CLI                            |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| Partite multiple               |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| Resilienza alle disconnessioni |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
| Chat                           |     ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)      |
|Persistenza|     ![#c5f015](https://via.placeholder.com/15/ff0000/000000?text=+)      |


## Requisiti di esecuzione

Per eseguire correttamente il server e il client, assicurati di avere installato sul tuo sistema:

- [Java Development Kit (JDK)](https://www.oracle.com/it/java/technologies/downloads/) versione 20 o successiva.

## Esecuzione del Server

Per avviare il server, segui questi passaggi:

1. Apri il terminale o la riga di comando.
2. Posizionati nella directory in cui si trova il file eseguibile `softeng-gc46-server.jar`.
3. Esegui il seguente comando:

   ```bash
   java -jar softeng-gc46-server.jar


## Esecuzione del Client

Per avviare il client, segui questi passaggi:

1. Apri il terminale o la riga di comando.
2. Posizionati nella directory in cui si trova il file eseguibile `softeng-gc46-client.jar`.
3. Esegui il seguente comando:

   ```bash
   java -jar softeng-gc46-client.jar