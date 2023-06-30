Michelangelo Stasi, Francesco Trementozzi, Nicolò Tocalli, Giuseppe Starnini

Gruppo 46

Valutazione del diagramma UML e del sequence diagram della parte di rete del gruppo 36.

---
## Lati positivi

### I. GamesManagerController
Ottimo utilizzo del game manager controller soprattutto per l'eventuale gestione delle multi-partite.


### II. AppServer
Interessante la gestione del appServer distinto dagli altri controller per connettere e disconnetere i client.

### III. Package ModelView
Modularizzazione della view del model molto efficace e serializzata correttamente

## Lati negativi


## Confronto tra le architetture
implementazione molto simile per la comunicazione client e server, sopratutto per l'utilizzo del gamesManagerController , tuttavia  le view nel nostro caso sono contenute all'interno di un unico oggetto invece che divise in più oggetti. Di conseguenza viene effettuata un'unica update passando come parametro una modelview appena creata che rappresenta lo stato del gioco nel momento in cui viene effettuata la chiamata.
Non è presente un equivalente del vostro object socket e nemmeno dell'appServer che implementeremo per garantire la funzione di disconnessione.




> **_NOTE AGGIUNTIVE:_**
Il Sequence Diagram poteva essere organizzato diversamente separando i vari eventi e le varie tipologie di interazioni invece di allinearli sulle stesse timeline. A livello logico è giusto ma a livello sintattico non c'è corrispondenza con la effettiva vita delle classi e delle loro interazioni.
Non è chiaro perchè ClientInterface implementi tutte le views che avete gestito sempre separatamente. C'è un motivo funzionale?
Non è chiaro se lo stato mostrato sulla view rappresenta quello del giocatore che sta giocando oppure contiene le informazioni di tutti i giocatori.
