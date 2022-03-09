<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# LamaPlugin Changelog

## [Unreleased]
### Added
- Renaming
- Indent `esac`, `od`, `fi` on typing 
- Prioritize single argument functions on completion
- Live Templates for functions, `case`/`for`/`while`/`if` expressions
- Wrong argument count inspection
- Unresolved inspection with import quick fix

### Fixed
- Go To Declaration/Find Usages for operators/patterns
- Documentation comment highlight
- Completion/Go To Declaration for local symbols, which defined later than current
- No completion on numeric literals and strings
- Completion after dot

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