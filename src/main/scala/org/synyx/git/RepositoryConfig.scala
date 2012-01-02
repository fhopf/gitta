package org.synyx.git

import java.io.File

class RepositoryConfig(val name: String, val folder: File, val url: String) {

  def isCheckedOut(): Boolean = {
    new File(folder, ".git").exists()
  }
}
