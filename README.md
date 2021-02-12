# What is this Library?

`DisplayInfo` is a library for getting display information.

# Example

```kotlin
val info = DisplayInfo.newInstance(context)
Log.d("DisplayInfo", "${info.widthPixel} x ${info.heightPixel}, ${info.dpi} dpi")
```

# How to Install

```groovy
// /app/build.gradle
dependencies {
    implementation 'io.github.eaglesakura.displayinfo:displayinfo:+'
}
```
