package org.synyx.git

import scala.io.Source
import scala.collection.immutable
import java.io.File

class ConfigurationService {

  def readRepositoryConfig(path: String): Iterable[RepositoryConfig] = {
    val lines = Source.fromFile(path).getLines
    lines.map(_.split(" ")).filter(_.length == 3).map {
      tokens: Array[String] => new RepositoryConfig(tokens(0), new File(tokens(1)), tokens(2))
    }.toIterable
  }

  def readIrcConfig(path: String): IrcServer = {
    val line = Source.fromFile(path).getLines.toList (0)
    val tokens = line.split(" ")
    new IrcServer(tokens(0), tokens.slice(1, tokens.length).toList)
  }
}
