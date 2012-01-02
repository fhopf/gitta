package org.synyx.git

import org.eclipse.jgit.revwalk.RevCommit

class Commit(val repoName: String, private val revCommit: RevCommit) {

  def getPaths(): Iterable[String] = {
    // TODO read from commit
    List()
  }
  
  def getMessage(): String = {
    revCommit.getShortMessage
  }

  def getAuthorName(): String = {
    revCommit.getAuthorIdent.getName
  }
  
  def getAuthorEmail(): String = {
    revCommit.getAuthorIdent.getEmailAddress
  }
}