# study_code
学习记录

## How to

**Step 1.** Add the JitPack repository to your build file

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
**Step 2.** Add the dependency

```groovy
dependencies {
        compile 'com.github.songsongtao:study_code:v1.0'
}
```