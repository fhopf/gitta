package org.synyx.git

class GittaServiceImpl(val messageService: PircBotMessageService, val repoService: RepositoryService, val configService: ConfigurationService) {

  def refresh() {
    val repoConfigs = configService.readRepositoryConfig("/todo")
    for (repoConfig <- repoConfigs) {
      // get current commit
      repoService.log(repoConfig)
      // update

      // log from last commit to now
    }

  }

}