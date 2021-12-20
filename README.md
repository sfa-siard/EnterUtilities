# EnterUtils - SIARD 2.2 Enter Utilities


This package contains the Enter Utilities used for SIARD 2.1.

## Getting started (for devs)

For building the binaries, Java JDK (1.8 or higher), Ant, and Git must
have been installed. A copy of build.properties.template must be called
build.properties. In it using a text editor the local values must be
entered as directed by the comments.

EnterUtilities 2.1 has been built and tested with JAVA JDK 1.8, 9, and 10.

Run all tests

```shell
ant test
```

Build the project

```shell
ant build
```

This task increments the version number in the project [MANIFEST.MF](./src/META-INF/MANIFEST.MF)

### Build with gradle

Migration to gradle is a work in progress. Some tests are picked up twice, some tests fail - probably due to class path issues.

Run tests with gradle:

```shell
./gradlew check
```

Build the jar with gradle:

```shell
./gradlew build
```

_important: the version number set in `build.gradle` has to be set manually!

## Documentation

[./doc/manual/user/index.html](./doc/manual/user/index.html) contains the manual for using the binaries.
[./doc/manual/developer/index.html](./doc/manual/user/index.html) is the manual for developers wishing
build the binaries or work on the code.  

More information about the build process can be found in
[./doc/manual/developer/build.html](./doc/manual/developer/build.html)

