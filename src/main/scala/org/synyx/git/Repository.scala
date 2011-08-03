package org.synyx.git

import java.io.File

class Repository(nameParam: String, folderParam: File, urlParam: String) {

  def folder = folderParam;
  def url = urlParam;
  def name = nameParam;
}
