## Gradle semantic versioning plugin

### Description

Plugin aimed to set project version, according to git tag, which does not requires user input in buildscript

### How to use

In buildscript, simply apply it as in a code snippet below
```groovy
plugins {
    id 'io.zensoft.semver' version '1.0.0'
}
```

Consider that if you'll specify version explicitly, value will override one, which plugin calculated.

This plugin searches for tags, which name matches regular expression `[vV]\d{1,2}.\d{1,2}.\d{1,2}`
(for example v1.5.7). If HEAD commit is also tagged with appropriate name, then
version is extracted as it is specified. In case if HEAD commit located ahead of
tagged one, minor version will be incremented, patch version will be reset and
`-SNAPSHOT` suffix will be added.

Example: got version `2.4.1`, in case tag and HEAD located in different places,
result version of artifact is `2.5.0-SNAPSHOT`

 