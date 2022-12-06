Admin Panel

Aufgaben:

- Anmeldung mit „Admin Account“
- Fetchen der Datenbankeinträge
- Darstellung der erhaltenen Daten
  - Kategorie X Schema: {name: String, questions: [{}]}
    - Frage Y: Schema: { frage: String, antworten: {a: String, b: String, c:String, d:String}, correctAnswer: Char}
    - Frage Y: ...
- POST Request an das Backend
  - 1 Erstelle neue Collection für Kategorie
  - 2 Erstelle neue Frage in Collection X
  - 3 Loeschen von Fragen
  - 4 Update von bereits bestehneden Fragen (Fragestellung ueberarbeiten etc.)
