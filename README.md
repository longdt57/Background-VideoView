# Background-VideoView
[![Version](https://jitpack.io/v/longdt57/Background-VideoView.svg)](https://github.com/longdt57/Background-VideoView/releases)

Set Video as a Background.

Example:
<video src="https://user-images.githubusercontent.com/106943585/177454594-746cf8e0-2296-4368-a70b-ce751dd8db3b.mp4" />

## Implementation
build.gradle
```
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.longdt57:Background-VideoView:{version}'
}
```
## Usage
XML
```
<lee.module.video.RawVideoView
    app:video_loop="true"
    app:video_raw_res="@raw/sample_raw_video" />
```
Kotlin: `lifecycle.addObserver(RawVideoView)`