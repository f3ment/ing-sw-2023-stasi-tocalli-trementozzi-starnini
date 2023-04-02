# Peer-Review 1: UML

---

Michelangelo Stasi, Francesco Trementozzi, Nicolò Tocalli, Giuseppe Starnini

Gruppo 46

Valutazione del diagramma UML delle classi del gruppo 36.

---
## Lati positivi

### I. Controller
_(A causa della nostra mancata implementazione del controller e di alcune funzionalità avanzate, in aggiunta alla seguente valutazione, potrebbero sorgere future valutazioni che verranno comunicate ASAP al referente del gruppo via email)_
- Riteniamo positivo separare il controller per la gestione della chat da da quello\i per la gestione delle partite\turni
- Personal Goal diviso in 6 carte è un'ottima idea che rende bene il concetto di scalabilità.

### II. Model
- Riteniamo funzionale la necessità di avere l'interfaccia GameManager per la gestione di più partite
- L'inizializzazione dell'ambiente di gioco per ogni partita è ben gestita (Game, \<\<interface\>\> LivingRoom, StandardLivingRoom)
- Lo Stack di Token è un'ottima idea per gestire la pila dei commongoal
- La classe player soddisfa tutte le mosse che devono essere compiute da un giocatore
- La chat adempie a memorizzare tutti i messaggi di una partita con annessi il mittente, il destinatario e il conenuto. Il TimeStamp va bene così implementato, se il tempo di gioco parte da 0 ad ogni sessione. Se la vostra idea era invece quella di mostrare l'orario corrente, potrebbe essere meglio delegare la gestione del TimeStamp in maniera centralizzata, causa eventuali differenze tra gli orari locali dei giocatori.


## Lati negativi
- Il numero di classi rappresentanti ciascun common goal può essere ridotto sfruttando dei pattern comuni tra alcuni algoritmi di validazione: `evaluate(playerShelf : Tile[][]) : bool`.  
- GameType usato in GameManagerController non è definito
- La scelta di implementare come attributi alcuni elementi di gioco come lo stack dei token è sicuramente utile ma a volte, come nel caso della Bookshelf, potrebbe risultare eccessivamente semplificativa, facendo perdere modularità e non seguendo il "single responsability principle".

## Confronto tra le architetture

* Anche noi abbiamo implementato una classe Game simile che contiene i riferimenti a tutte le variabili di gioco, però:
  * `LivingRoom` è compatta e riesce a racchiudere al suo interno tutte le componenti del tavolo da gioco, noi abbiamo
  fatto lo stesso in maniera meno compatta, separando la Board e la Bag.
  * Accorpare la classe di Giocatore alla classe di postazione da Tavolo in un'unica classe `Player` rende il codice più snello senza perdere modularità. 
* Il Controller è sviluppato correttamente e in modo completo, e le entità per la chat già implementate, elementi che noi dobbiamo ancora implementare.
* Il metodo `getDescription(): void` è un'ottima idea per comprendere meglio le regole di gioco in partita.<sup>1</sup>
* In generale l'uml delle classi evidenzia come si sia preferito un approccio più compatto per alleggerire il codice e il modello, rispetto al nostro con una netta divisione fra classi.




> **_NOTE AGGIUNTIVE:_**
> * Variabili "globali" come per esempio i `Token` con i loro punteggi o i `Tile` possono essere allocate in un file di configurazione/properties<sup>2</sup> in favore di una maggiore scalabilità dell'intero codice in termini di eventuali modifiche ai parametri di gioco.

---
<sup>1</sup> Magari stampando la descrizione del singolo Goal sotto ciascuna commonGoal nella view;\
<sup>2</sup> Sfruttando `java.util.Properties`.

