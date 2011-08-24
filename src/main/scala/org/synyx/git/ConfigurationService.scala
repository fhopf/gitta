package org.synyx.git

import scala.io.Source
import scala.collection.immutable
import java.io.File

object ConfigurationService {

  def readRepositoryConfig(path: String) = {
    val lines = Source.fromFile(path).getLines
    val filtered = lines.map(_.split(" ")).filter(_.length == 3).map {
      tokens: Array[String] => new RepositoryConfig(tokens lift 0 get, new File(tokens lift 1 get), tokens lift 2 get)
    }
    filtered
  }

  def readIrcConfig(path: String) = {
    val line = Source.fromFile(path).getLines.toList (0)
    val tokens = line.split(" ")
    new IrcServer(tokens(0), tokens.slice(1, tokens.length).toList)
  }
}
