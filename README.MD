# ThemeSwitcher

Kotlin Multiplatform Library

### Publish to MavenCentral

1) Registering a Sonatype account as described here: 
   https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
2) Add developer id, name, email and the project url to
   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
3) Add the secrets to `local.properties`:

```
signing.keyId=...
signing.password=...
signing.secretKeyRingFile=...
ossrhUsername=...
ossrhPassword=...
```

4) Run `./gradlew :theme-swithcer:publishAllPublicationsToSonatypeRepository`

### Build platform artifacts

#### Android aar

- Run `./gradlew :theme-swithcer:assembleRelease`
- Output: `/theme-swithcer/build/outputs/aar/theme-swithcer-release.aar`

#### JVM jar

- Run `./gradlew :theme-swithcer:jvmJar`
- Output: `/theme-swithcer/build/libs/theme-swithcer-jvm-1.0.jar`

#### iOS Framework

- Run `./gradlew :theme-swithcer:linkReleaseFrameworkIosArm64`
- Output: `/theme-swithcer/build/bin/iosArm64/releaseFramework/theme-swithcer.framework`

#### JS file

- Run `./gradlew :theme-swithcer:jsBrowserProductionWebpack`
- Output: `/theme-swithcer/build/dist/js/productionExecutable/theme-swithcer.js`

#### macOS Framework

- Run `./gradlew :theme-swithcer:linkReleaseFrameworkMacosArm64`
- Output: `/theme-swithcer/build/bin/macosArm64/releaseFramework/theme-swithcer.framework`

#### Linux static library

- Run `./gradlew :theme-swithcer:linkReleaseStaticLinuxX64`
- Output: `/theme-swithcer/build/bin/linuxX64/releaseStatic/libtheme-swithcer.a`

#### Windows static library

- Run `./gradlew :theme-swithcer:linkReleaseStaticMingwX64`
- Output: `/theme-swithcer/build/bin/mingwX64/releaseStatic/libtheme-swithcer.a`

#### Wasm binary file

- Run `./gradlew :theme-swithcer:wasmJsBrowserDistribution`
- Output: `/theme-swithcer/build/dist/wasmJs/productionExecutable/theme-swithcer-wasm-js.wasm`
