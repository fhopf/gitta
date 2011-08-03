package org.synyx.git

import scala.io.Source
import scala.collection.immutable
import java.io.File

object ConfigurationService {

  def readRepositoryConfig(path: String) = {
    val lines = Source.fromFile(path).getLines
    val filtered = lines.filter(_.split(" ").length == 3)
    filtered
  }

//      for (line <- ) {
//      val tokens = line.split(" ")
//      if (tokens.length < 3) {
//        println("Skipping line");
//      } else {
//        result.add(new Repository(tokens(0), new File(tokens(1)), tokens(2)))
//      }
//    }
//    result


}
