<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# LamaPlugin Changelog

## [Unreleased]
### Fixed 
- Unresolved reference problems in syntax expression & repeat statements
- Prioritize dot on list constructor operator
- Support 2022.2 EAP

## [0.2.1]
### Added
- Exception hyperlink with navigation
- Alignment options for case/syntax branches


### Fixed
- Improve syntax expressions support
- Improve syntax formatter support

## [0.2.0]
### Added
- Renaming
- Indent `esac`, `od`, `fi` on typing 
- Prioritize single argument functions on completion
- Live Templates for functions, `case`/`for`/`while`/`if` expressions
- Wrong argument count inspection
- Unresolved inspection with import quick fix
- Improve dot functions support


### Fixed
- Go To Declaration/Find Usages for operators/patterns
- Documentation comment highlight
- Completion/Go To Declaration for local symbols, which defined later than current
- No completion on numeric literals and strings
- Completion after dot
- No need to mount `/tmp` for `lima`
- Missing pair double quote

## [0.1.2]
### Added
- Quote typing helper



### Fixed
- No continuation indent on enter at the end of the expression

## [0.1.1]
### Fixed
- Errors during compiler fetch

## [0.1.0]
### Added
- Init project
- Parser/lexer
- Commenter
- Formatter
- Indexing
- Completion 
   - Stdlib symbols
   - Global and Local user symbols
   - Units
   - Some keywords
- Go To Declaration & Find Usages
- Parameter Info
- GitHub CI/CD