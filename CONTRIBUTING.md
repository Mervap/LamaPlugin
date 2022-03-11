# Kotlin

Plugin is written on [Kotlin](https://kotlinlang.org/) language. If you can program in Java, you should be able to read
and write Kotlin code right away. Kotlin is deeply similar to Java, but has less verbose syntax and better safety.

# Getting started

## Environment

Java 11 is required for development. For example, you can
install [Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html). You can
also [install](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) Java just from your IntelliJ IDEA.

## Clone

```
git clone git@github.com:Mervap/LamaPlugin.git
cd LamaPlugin
```

## Building & Running

Gradle with [gradle-intellij](https://github.com/JetBrains/gradle-intellij-plugin) plugin used to build the plugin. It
comes with a wrapper script (`gradlew` in the root of the repository) which downloads appropriate version of gradle
automatically as long as you have JDK installed.

Common Gradle tasks are:

- `./gradlew buildPlugin` -- fully build plugin and create an archive at
  `build/distributions` which can be installed into your IDE via `Install plugin from disk...` action found
  in `Settings > Plugins`.

- `./gradlew runIde` -- run a development IDE with the plugin installed

- `./gradlew test` -- run tests. We love tests!

## Development in IntelliJ IDEA

You might want to install the following plugins:

- [Grammar-Kit](https://plugins.jetbrains.com/plugin/6606-grammar-kit) to get highlighting for the files with BNFish grammar.
- [PsiViewer](https://plugins.jetbrains.com/plugin/227-psiviewer) to view the AST of Lama files right in the IDE.

## Code style

Please use **reformat code** action to maintain consistent style. Pay attention to IDEA's warning and suggestions, and
try to keep the code green. If you are sure that the warning is false positive, use an annotation to suppress it.

Try to avoid copy-paste and boilerplate as much as possible. For example, proactively use `?:` to deal with nullable
values.
