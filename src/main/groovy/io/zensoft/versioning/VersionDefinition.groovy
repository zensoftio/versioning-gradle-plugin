package io.zensoft.versioning

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevWalk

class VersionDefinition {

    private Integer majorVersion = 0
    private Integer minorVersion = 0
    private Integer patchVersion = 0
    private Boolean snapshot = true

    private static final String TAG_PATTERN = "[vV]\\d{1,2}.\\d{1,2}.\\d{1,2}"

    VersionDefinition(Repository repo) {
        Git git = new Git(repo)
        List<Ref> tags = git.tagList().call()
        if (!tags.isEmpty()) {
            Ref lastTag = tags.last()
            String refName = lastTag.name.substring(lastTag.name.lastIndexOf('/') + 1)
            if (!refName.matches(TAG_PATTERN)) {
                throw new IllegalArgumentException("Wrong version format in tag name")
            }
            String[] targetVersion = refName[1..-1].split("\\.")
            this.majorVersion = new Integer(targetVersion[0])
            this.minorVersion = new Integer(targetVersion[1])
            this.patchVersion = new Integer(targetVersion[2])
            String headCommit = git.log().setMaxCount(1).call().last().name
            String tagCommit = new RevWalk(repo).parseCommit(lastTag.objectId).toObjectId().name
            this.snapshot = tagCommit != headCommit
        }
        if (this.snapshot) {
            minorVersion++
            patchVersion = 0
        }
    }

    Integer getMajorVersion() {
        return majorVersion
    }

    Integer getMinorVersion() {
        return minorVersion
    }

    Integer getPatchVersion() {
        return patchVersion
    }

    String getVersion() {
        return "$majorVersion.$minorVersion.$patchVersion${snapshot ? "-SNAPSHOT" : ""}"
    }
}
