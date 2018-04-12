package main

import cats.implicits._

object Main extends App {
  type Occurrence = Map[Char, Int]

  object Occurrence {
    def apply(chars: Char*): Occurrence = Map[Char, Int](chars map(_ -> 1) : _*) withDefaultValue 0
  }
  /**
    * Calculate the frequency of occurrence of each character of English alphabet in the string (large and small characters count as the same).
    * @param str the `String` of characters
    * @return a `List` of frequencies as `Double` of occurrences all the English alphabet characters in alphabet order.
    */
  def frequencies(str: String): List[Double] = {
    /**
      * Add character to occurrences.
      * @param dict the `Occurrence` to combine
      * @param char the `Char` to merge
      * @return new `Occurrence` that contains all the occurrences with the new one.
      */
    def collectDictionary(dict: Occurrence, char: Char) = {
      dict |+| Occurrence(char.toLower)
    }
    lazy val occurrences = str.foldLeft(Occurrence())(collectDictionary)
    lazy val alphabet = ('a' to 'z').toList
    lazy val total = str.length

    alphabet.map(occurrences(_).toDouble / total)
  }
}
