# Lama plugin for the IntelliJ Platform

![Build](https://github.com/Mervap/LamaPlugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/18577-lama.svg)](https://plugins.jetbrains.com/plugin/18577-lama)

[//]: # ([![Downloads]&#40;https://img.shields.io/jetbrains/plugin/d/18577-lama.svg&#41;]&#40;https://plugins.jetbrains.com/plugin/18577-lama&#41;)

<!-- Plugin description -->
Plugin for [Lama](https://github.com/JetBrains-Research/Lama) language
<!-- Plugin description end -->

To get full list of supported features check [FEATURES.md](docs/FEATURES.md)

## Supported OS

- [x] Linux
- [x] Mac OS (see [workaround](#macos-workaround))
- [ ] Windows

## Installation

### Marketplace

Open `Settings > Plugins > Marketplace` in your IDE, search for `Lama` and install the plugin

### Manual installation

Download the [latest release](https://github.com/Mervap/LamaPlugin/releases/latest) and install it manually using
`Settings/Preferences > Plugins > ⚙️ > Install plugin from disk...`

## macOS Workaround

`Lama` executables is 32-bit, so it's impossible to compile and run such programs on OSX.
[Lima](https://github.com/lima-vm/lima) uses as a workaround.

If you on macOS and want to have a good experience you need to:

* Install `lima`
* Start Linux instance (`limactl start`)
* Enable `lima` support (`Preferences > Languages & Frameworks > Lama > Enable lima support`)
* If you whatever don't have stdlib completion/resolve/etc try `Restart IDE`

## Contributing

Any contributing are welcome. You're encouraged to contribute to the plugin if you've found any issues or missing
functionality that you would want to see. Check out [CONTRIBUTING.md](CONTRIBUTING.md) to learn how to set up the
project. If you don't know what to do, check open [Issues](https://github.com/Mervap/LamaPlugin/issues).
