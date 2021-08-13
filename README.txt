Toader George-Catalin 321CB

In implementarea mea am 4 packages, impartite astfel: 
-auction -> Contine clasele ce au legatura cu desfasurarea licitatiilor.
-clients -> Contine clasele ce au legatura cu clientii si crearea acestora.
-products -> Contine clasele ce au legatura cu produsele si crearea acestora.
-proiect -> Contine clasele ce au legatura cu citirea si prelucrarea datelor
	din fisierul de input.
	Citirea si prelucrarea datelor are loc in clasa Database, pentru care am 
folosit si Singleton Pattern. Fiecare linie din fisierul de input reprezinta datele
unui obiect pe care il creez folosind un Factory ce la randul lui foloseste
3 Buildere pentru construirea celor 3 tipuri de obiecte, Client, Product si Auction.
Tot aici am folosit si Null Object Pattern, pentru input gresit. Listele create
cu aceste obiecte sunt folosite pentru a construi casa de licitatii.
	Licitatiile sunt citite din fisierul de input, alaturi de proprietatile lor
precum nrPasiMaxim si nrMinimParticipanti. Toate aceste licitatii se vor desfasura
simultan folosind multithreading. Dupa terminarea unei runde de licitatii se sterg
datele din brokeri si clienti, incepand apoi o noua runda de licitatii si continuand
astfel pana se epuizeaza toate produsele din casa de licitatii. Spre exemplu, pentru
6 licitatii si 21 de produse, cele 6 licitatii se vor desfasura simultan avand astfel
4 runde de licitatii. Dupa fiecare runda se asteapta 4 secunde pentru terminarea tuturor
licitatiilor.