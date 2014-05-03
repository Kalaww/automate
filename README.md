#AUTOMATES – MODE D’EMPLOI  

###COMMANDE :
`Main`

###OPERATIONS :  
- `-c` : compare deux automates/expressions rationnelles afin de vérifier si elles sont équivalentes. Si une seule entrée est spécifiée, elle doit contenir une expression rationnelle (la comparaison se fera sur les automates obtenus par l’algorithme de Moore et l’algorithme de calcul des résiduels).  
- `-M` : applique l’algorithme de minimisation de Moore sur un automate.  
- `-r` : applique l’algorithme de minimisation par le calcul des résiduels sur une expression rationnelle.  
- `-G` : applique l’algorithme de Glushkov pour transformer une expression rationnelle en automate.  
- `-cp` : calcul le complémentaire de l’automate/expression rationnelle.  
- `-m` : calcul le miroir de l’automate/expression rationnelle.  
- `-d` : déterminise l’automate/expression rationnelle.  
- `-cmp` : calcul le complémentaire de l’automate/expression rationnelle.  
- `-a [mot]` : vérifie si le mot appartient au langage de l’automate/expression rationnelle.  
- `-u` : calcul l’union de deux automates/expressions rationnelles.  
- `-in` : calcul l’intersection de deux automates/expression rationnelles.  
###ENTREES :  
- `-f [fichier]` : prend le nom du fichier contenant un automate ou une expression rationnelle sous forme post-fixe.  
- `-f2 [fichier]` : prend le nom du fichier contenant un automate ou une expression rationnelle sous forme post-fixe. Dois être utilisé seulement comme deuxième entrée fichier s’ils l’ont souhaite comparer deux automates (donc avec `–f` ou `–g`).  
- `-g [nombre d’états] [alphabet]` : génère un automate aléatoirement contenant un nombre d’états défini et un alphabet sous forme d’une chaîne de caractères contenant les lettres de l’alphabet.  
###SORTIES :
- `-w [fichier]` : une fois qu’une opération est terminée, il y a possibilité de sauvegarder l’automate obtenu dans un fichier. Pour une comparaison, la sauvegarde est réalisé que si les deux entrées sont bien équivalentes et sauvegarde l’automate minimal de la première entrée.  
###OPTIONS :
- `-i` : affiche des informations supplémentaires pendant les opérations. (ex : les étapes de l’algorithme de minimisation de Moore).  

###EXEMPLES :
`Main –c –f fichier.txt –f2 fichier2.txt`  
`Main –c –f fichier.txt –w sauvegegarde.txt`  
`Main –M –g 14 abcdef –w sauvegarde.txt`  
`Main –G –f fichier.txt`  
`Main –a ababab –f fichier.txt`  
`Main –u –g 4 abc –f2 fichier2.txt –w sauvegarde.txt`  
`Main –cmp  -g 56 abdefghijkl`  

