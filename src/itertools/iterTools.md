#Itertools for Kotlin

Kotlin already implements many of the functions from Python's itertools. However some convient once are missing.

####Infinite Iterators:

+ **count()**:Produces and infinite Sequence counting up:
  
     `count(0, 2).take(10): 0 2 4 6 8 10 12 14 16 18`

+ **cycle()**: Produces an infinite Sequence cycling through any Iterable:

  `(1..3).cycle().take(6): 1 2 3 1 2 3 `
 
+ **repeat()**: Produces a possible infinite Sequence repeating a single element:
   `1.repeat(10): 1 1 1 1 1 1 1 1 1 1`  
   `'c' * 3: ccc`

####Iterators terminating on the shortest input sequence:   
 

  
  val s = "ABCDEFGHIJKLMN"
  print("\"$s\".chunksOfSize(3): ")
  for (chunk in s.chunksOfSize(3)) print("$chunk ")
  println()

  println("'c' * 3 = ${'c' * 3}")

  print("\"$s\".sliding(3): ")
  for (window in s.sliding(3)) print("$window ")
  println()

  print("(1..2).cartProd(2): ")
  for (prod in (1..2).cartProd(2)) print("$prod ")
  print("\n(a..b).cartProd(3): ")
  for (prod in ('a'..'b').cartProd(3)) print("$prod ")
  print("\n(1..2) * (1..2): ")
  for (prod in (1..2) * (1..2)) print("$prod ")
  print("\ncartProd((1..2), (3..4), (5..6)): ")
  for (prod in cartProd((1..2), (3..4), (5..6))) print("$prod ")
  println()

  print("1.repeat(10): ")
  for (i in 1.repeat(10)) print("$i ")
  println()

  print("(1..13).intoPairs(): ")
  for (pair in (1..13).intoPairs()) print("$pair ")
  println()

  print("(1..2).chain((3..4),(5..6),(7..8)): ")
  for (elem in (1..2).chain((3..4), (5..6), (7..8))) print("$elem ")
  println()

  print("\"abcd\".combinations(2): ")
  for (elem in "abcd".combinations(2)) print("$elem ")
  print("\n\"abcd\".combinations(2, true): ")
  for (elem in "abcd".combinations(2, true)) print("$elem ")
  print("\nIterable<T>(1,2,3,4).combinations(2): ")
  for (elem in (1..4).combinations(2)) print("$elem ")
  print("\nIterable<T>(1,2,3,4).combinations(2, true): ")
  for (elem in (1..4).combinations(2, true)) print("$elem ")
  println()

  print("count(0, 2).take(10): ")
  for (i in count(0, 2).take(10)) print("$i ")
  println()

  print("(1..3).cycle().take(6): ")
  for (i in (1..3).cycle().take(6)) print("$i ")
  println()

  print("\"abcd\".permutations(): ")
  for (elem in "abcd".permutations()) print("$elem ")
  print("\n\"abcd\".permutations(2): ")
  for (elem in "abcd".permutations(2)) print("$elem ")
  print("\nIterable<T>(1,2,3).permutations(): ")
  for (elem in (1..3).permutations()) print("$elem ")
  print("\nIterable<T: Comparable<T>>(1,2,3,4).permutations(2): ")
  for (elem in (1..4).permutations(2)) print("$elem ")
  println()
  
  "ABCDEFGHIJKLMN".chunksOfSize(3): ABC DEF GHI JKL MN 
  'c' * 3 = ccc
  "ABCDEFGHIJKLMN".sliding(3): ABC BCD CDE DEF EFG FGH GHI HIJ IJK JKL KLM LMN 
  (1..2).cartProd(2): [1, 1] [1, 2] [2, 1] [2, 2] 
  (a..b).cartProd(3): [a, a, a] [a, a, b] [a, b, a] [a, b, b] [b, a, a] [b, a, b] [b, b, a] [b, b, b] 
  (1..2) * (1..2): (1, 1) (1, 2) (2, 1) (2, 2) 
  cartProd((1..2), (3..4), (5..6)): [1, 3, 5] [1, 3, 6] [1, 4, 5] [1, 4, 6] [2, 3, 5] [2, 3, 6] [2, 4, 5] [2, 4, 6] 
  1.repeat(10): 1 1 1 1 1 1 1 1 1 1 
  (1..13).intoPairs(): (1, 2) (3, 4) (5, 6) (7, 8) (9, 10) (11, 12) (13, null) 
  (1..2).chain((3..4),(5..6),(7..8)): 1 2 3 4 5 6 7 8 
  "abcd".combinations(2): ab ac ad bc bd cd 
  "abcd".combinations(2, true): aa ab ac ad bb bc bd cc cd dd 
  Iterable<T>(1,2,3,4).combinations(2): [1, 2] [1, 3] [1, 4] [2, 3] [2, 4] [3, 4] 
  Iterable<T>(1,2,3,4).combinations(2, true): [1, 1] [1, 2] [1, 3] [1, 4] [2, 2] [2, 3] [2, 4] [3, 3] [3, 4] [4, 4] 
  count(0, 2).take(10): 0 2 4 6 8 10 12 14 16 18 
  (1..3).cycle().take(6): 1 2 3 1 2 3 
  "abcd".permutations(): abcd bacd cabd acbd bcad cbad dbac bdac adbc dabc badc abdc acdb cadb dacb adcb cdab dcab dcba cdba bdca dbca cbda bcda 
  "abcd".permutations(2): ab ac ad ba bc bd ca cb cd da db dc 
  Iterable<T>(1,2,3).permutations(): [1, 2, 3] [2, 1, 3] [3, 1, 2] [1, 3, 2] [2, 3, 1] [3, 2, 1] 
  Iterable<T: Comparable<T>>(1,2,3,4).permutations(2): [1, 2] [1, 3] [1, 4] [2, 1] [2, 3] [2, 4] [3, 1] [3, 2] [3, 4] [4, 1] [4, 2] [4, 3] 