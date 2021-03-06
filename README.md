## Bomb Party Assistant

![Windows support](https://img.shields.io/badge/Platform-Windows-lightgrey)
![MacOS support](https://img.shields.io/badge/Platform-MacOS-lightgrey)
![Linux support](https://img.shields.io/badge/Platform-Linux-lightgrey)

![Java JDK requirement](https://img.shields.io/badge/JDK-12-orange)

An application that helps you to come up with an answer in a bomb party game at [JKLM.fun](https://JKLM.fun) in
realtime. This application is built using desktop compose.

![Application demo](image/demo.gif)

## Requirements

- Java JDK 12

## Getting Started

### Add the Required Driver

1. Check your Google Chrome version
2. Download the driver from [here](https://chromedriver.chromium.org/home). Make sure it matches your Google Chrome
   version and your OS.
3. Name the driver "chromedriver.exe", and place it in `/src/main/resources`.

### Run the App

Run the following command in the root project directory.

```
gradlew run
```

If it does not work, try to run the project from [IntelliJ IDEA](https://www.jetbrains.com/idea/).

## Features

### Realtime Word Suggestion

The app gives you suggestion on what words you can enter for the given prompt in realtime. If you prefer a shorter word,
you can reduce the maximum suggested word length.

Win easily by setting the word length to a low value, or win with honor by setting the word length to a high value. The
choice is yours.

### Automatically Copy to Clipboard

Don't feel like typing in the answer? You can enable the settings to automatically copy the first suggested word to your
clipboard.