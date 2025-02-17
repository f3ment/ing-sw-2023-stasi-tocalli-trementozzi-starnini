# Prova Finale di Ingegneria del Software - AA 2022-2023
![alt text](src/main/resources/Images/Publishermaterial/Display_1.jpg)

- ###  [Michelangelo Stasi](https://github.com/Myke01-Poli) <br> michelangelo.stasi@mail.polimi.it
- ###  [Nicolò Tocalli](https://github.com/nicolotocalli) <br> nicolo.tocalli@mail.polimi.it
- ###  [Giuseppe Starnini](https://github.com/Sterning171177) <br> giuseppe.starnini@mail.polimi.it
- ###  [Francesco Trementozzi](https://github.com/f3ment) <br> francesco.trementozzi@mail.polimi.it

Implementazione del gioco da tavolo [MyShelfie](https://www.craniocreations.it/prodotto/my-shelfie).

Il progetto consiste nell'implementazione di un sistema distribuito con tecnologia client-server. Una istanza del server è in grado di gestire più partite contemporaneamente e due o più client
(uno per giocatore) che possono partecipare ad una sola partita alla volta. Si richiede l'utilizzo del pattern
Model-View-Controller - MVC per progettare l'intero sistema.

### Legenda
| Colore                        |                 Significato                 |
|:------------------------------|:-------------------------------------------:|
| ![#c5f015](https://dummyimage.com/15/0af52d/c4f015) |     Funzionalità implementata e testata     |
| ![#c5f015](https://dummyimage.com/15/f5f10c/f0da16) | Funzionalità implementata non completamente |
| ![#c5f015](https://dummyimage.com/15/f50c0c/c4f015) |        Funzionalità non implementata        |

## Funzionalità implementate

| Funzionalità avanzata          |                      Stato                                               |
|:-------------------------------|:------------------------------------------------------------------------:|
| Regole semplificate            |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| Regole complete                |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| Socket                         |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| RMI                            |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| GUI                            |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| CLI                            |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| Partite multiple               |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| Resilienza alle disconnessioni |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| Chat                           |     ![#c5f015](https://dummyimage.com/15/0af52d/c4f015)                  |
| Persistenza                    |     ![#c5f015](https://dummyimage.com/15/f50c0c/c4f015)                  |


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

## Other info
- Supervisor: [Prof. G. Cugola](https://cugola.faculty.polimi.it/)
- Grade: 30 cum laude

> **_NOTA_**: My Shelfie è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.
