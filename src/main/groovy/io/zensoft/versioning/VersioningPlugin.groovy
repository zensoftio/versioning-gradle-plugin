package io.zensoft.versioning

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class VersioningPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder()
        Repository repo = repositoryBuilder
                .setGitDir(new File("${project.projectDir}/.git"))
                .setMustExist(true)
                .build()
        VersionDefinition versionDefinition = new VersionDefinition(repo)
        project.version = versionDefinition.getVersion()
    }

}
