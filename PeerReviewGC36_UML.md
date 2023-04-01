# Peer-Review 1: UML

---

Michelangelo Stasi, Francesco Trementozzi, Nicolò Tocalli, Giuseppe Starnini

Gruppo 46

Valutazione del diagramma UML delle classi del gruppo 36.

---
## Lati positivi

### I. Controller
_(le valutazioni alla parte del controller sono basate su valutazioni oggettive del diagramma a causa della nostra mancata implementazione dalla quale potrebbero sorgere future valutazioni che verranno comunicate ASAP al referente del gruppo via email)_
- GameType usato in GameManagerController non è definito
- Riteniamo positivo separare il controller per la gestione della chat da da quello\i per la gestione delle partite\turni \
- Personal Goal diviso in 6 carte è un'ottima idea che rende bene il concetto di scalabilità.

### II. Model
- Riteniamo funzionale la necessita di avere l'interfaccia GameManager per la gestione di più partite
- L'inizializzazione dell'ambiente di gioco per ogni partita è ben gestita (Game, \<\<interface\>\> LivingRoom, StandardLivingRoom)
- Lo Stack di Token è un'ottima idea per gestire la pila dei commongoal
- La classe player soddisfa i tutte le mosse che devono essere compiute da un giocatore
- La chat adempie a memorizzare tutti i messaggi di una partita con annessi il mittente, il destinatario e il conenuto. Il TimeStamp va bene così implementato, se il tempo di gioco parte da 0 ad ogni sessione. Se la vostra idea era invece quella di mostrare l'orario corrente, potrebbe essere meglio delegare la gestione del TimeStamp in maniera centralizzata, causa eventuali differenze tra gli orari locali dei giocatori.

~~Indicare in questa sezione quali sono secondo voi i lati positivi dell'UML
dell'altro gruppo. Se avete qualche difficoltà, provate a simulare il gioco a
mano, immaginandovi quali sono le invocazioni di metodo che avvengono in certe
situazioni che vi sembrano importanti (ad esempio, la fusione delle isole oppure
il calcolo dell’influenza).~~

## Lati negativi
- Il numero classi rappresentanti ciascuna common goal può essere ridotto sfruttando dei pattern che di alcuni algoritmi per implementare `evaluate(playerShelf : Tile[][]) : bool`.  

~~Come nella sezione precedente, indicare quali sono secondo voi i lati negativi.~~

## Confronto tra le architetture

* Anche noi abbiamo implemetato una classe Game simile che "tiene" i riferimenti a tutte le variabili di gioco, però:
  * `LivingRoom` è compatta e riesce a racchiudere al suo interno tutto le componenti del tavolo da gioco, noi abbiamo
  fatto lo stesso in maniera meno compatta, separando la Board e la Bag.
  * Accorpando la classe di Giocatore alla classe di postazione da Tavolo in un'unica classe `Player` in modo tale da rendere il codice più snello ~~e senza perdere di modularità~~. 
* Controller sviluppato correttamente e in modo completo, entità per la chat già implementate.
* Il metodo `getDescription(): void` è un'ottima idea per comprendere meglio le regole di gioco in partita.<sup>1</sup>

\
\
~~Individuate i punti di forza dell'architettura dell'altro gruppo rispetto alla
vostra, e quali sono le modifiche che potete fare alla vostra architettura per
migliorarla.~~

> **_NOTE AGGIUNTIVE:_**
> * Variabili "globali" come per esempio i `Token` con i loro punteggi o i `Tile` possono essere allocate in un file di configurazione/properties<sup>2</sup> in favore di una maggiore scalabilità dell'intero codice in termini di eventuali modifiche ai parametri di gioco.

---
<sup>1</sup> Magari stampando la descrizione del singolo Goal sotto ciascuna commonGoal nella view;\
<sup>2</sup> Sfruttando `java.util.Properties`.

