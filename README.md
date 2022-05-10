# EnterUtils - SIARD 2.2 Enter Utilities


This package contains the Enter Utilities used for SIARD 2.1.

## Getting started (for devs)

For building the binaries, Java JDK (1.8 or higher) and Ant must
have been installed. Adjust build.properties if necessary.

Run all tests

```shell
ant test
```

Create a release

```shell
ant release
```

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

_important: the version number set in `build.gradle` has to be set manually!_

## Documentation

[./doc/manual/user/index.html](./doc/manual/user/index.html) contains the manual for using the binaries.
[./doc/manual/developer/index.html](./doc/manual/user/index.html) is the manual for developers wishing
build the binaries or work on the code.  

More information about the build process can be found in
[./doc/manual/developer/build.html](./doc/manual/developer/build.html)

