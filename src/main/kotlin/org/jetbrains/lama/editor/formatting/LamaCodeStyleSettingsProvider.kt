package org.jetbrains.lama.editor.formatting

import com.intellij.application.options.*
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*
import com.intellij.psi.codeStyle.CommonCodeStyleSettings.IndentOptions
import com.intellij.ui.components.JBCheckBox
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.psi.LamaLanguage

private typealias Common = CommonCodeStyleSettings

class LamaCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

  override fun createConfigurable(
    settings: CodeStyleSettings,
    originalSettings: CodeStyleSettings,
  ): CodeStyleConfigurable {
    return object : CodeStyleAbstractConfigurable(settings, originalSettings, configurableDisplayName) {
      override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
        return LamaCodeStylePanel(currentSettings, settings)
      }
    }
  }

  override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
    when (settingsType) {
      SettingsType.SPACING_SETTINGS -> {
        consumer.showStandardOptions(
          Common::SPACE_BEFORE_METHOD_CALL_PARENTHESES.name,
          Common::SPACE_BEFORE_METHOD_PARENTHESES.name,
          Common::SPACE_AROUND_ASSIGNMENT_OPERATORS.name,
          Common::SPACE_AROUND_RELATIONAL_OPERATORS.name,
          Common::SPACE_AROUND_ADDITIVE_OPERATORS.name,
          Common::SPACE_AROUND_MULTIPLICATIVE_OPERATORS.name,
          Common::SPACE_WITHIN_METHOD_CALL_PARENTHESES.name,
          Common::SPACE_WITHIN_EMPTY_METHOD_CALL_PARENTHESES.name,
          Common::SPACE_WITHIN_METHOD_PARENTHESES.name,
          Common::SPACE_WITHIN_EMPTY_METHOD_PARENTHESES.name,
          Common::SPACE_WITHIN_BRACES.name,
          Common::SPACE_WITHIN_BRACKETS.name,
          Common::SPACE_WITHIN_PARENTHESES.name,
          Common::SPACE_AFTER_COMMA.name,
          Common::SPACE_BEFORE_COMMA.name
        )

        consumer.renameStandardOption(
          Common::SPACE_BEFORE_METHOD_CALL_PARENTHESES.name,
          LamaBundle.message("code.style.function.call.parentheses")
        )
        consumer.renameStandardOption(
          Common::SPACE_BEFORE_METHOD_PARENTHESES.name,
          LamaBundle.message("code.style.function.declaration.parentheses")
        )
        consumer.renameStandardOption(
          Common::SPACE_AROUND_ASSIGNMENT_OPERATORS.name,
          LamaBundle.message("code.style.assignment.operators")
        )
        consumer.renameStandardOption(
          Common::SPACE_WITHIN_BRACES.name,
          LamaBundle.message("code.style.braces")
        )
        consumer.renameStandardOption(
          Common::SPACE_WITHIN_PARENTHESES.name,
          LamaBundle.message("code.style.parentheses")
        )

        val customizableOptions = CodeStyleSettingsCustomizableOptions.getInstance()
        consumer.showCustomOption(
          LamaCodeStyleSettings::class.java,
          LamaCodeStyleSettings::SPACE_AROUND_DISJUNCTION_OPERATORS.name,
          LamaBundle.message("code.style.disjunction.operator"),
          customizableOptions.SPACES_AROUND_OPERATORS
        )

        consumer.showCustomOption(
          LamaCodeStyleSettings::class.java,
          LamaCodeStyleSettings::SPACE_AROUND_CONJUNCTION_OPERATORS.name,
          LamaBundle.message("code.style.conjunction.operator"),
          customizableOptions.SPACES_AROUND_OPERATORS
        )

        consumer.showCustomOption(
          LamaCodeStyleSettings::class.java,
          LamaCodeStyleSettings::SPACE_AROUND_INFIX_OPERATOR.name,
          LamaBundle.message("code.style.infix.operators"),
          customizableOptions.SPACES_AROUND_OPERATORS
        )

        consumer.showCustomOption(
          LamaCodeStyleSettings::class.java,
          LamaCodeStyleSettings::SPACE_AROUND_LIST_CONS_OPERATOR.name,
          LamaBundle.message("code.style.list.cons.operator"),
          customizableOptions.SPACES_AROUND_OPERATORS
        )

        consumer.showCustomOption(
          LamaCodeStyleSettings::class.java,
          LamaCodeStyleSettings::SPACE_AROUND_DOT_OPERATOR.name,
          LamaBundle.message("code.style.dot.operator"),
          customizableOptions.SPACES_AROUND_OPERATORS
        )
      }
      SettingsType.BLANK_LINES_SETTINGS -> {
        consumer.showStandardOptions(Common::KEEP_BLANK_LINES_IN_CODE.name)
      }
      SettingsType.WRAPPING_AND_BRACES_SETTINGS -> {
        consumer.showStandardOptions(
          Common::ALIGN_MULTILINE_PARAMETERS.name,
          Common::ALIGN_MULTILINE_PARAMETERS_IN_CALLS.name
        )
        consumer.showCustomOption(
          LamaCodeStyleSettings::class.java,
          LamaCodeStyleSettings::ALIGN_MULTILINE_FOR.name,
          LamaBundle.message("code.style.align.multiline.for"),
          null
        )
      }
      else -> {} // do nothing
    }
  }

  override fun getIndentOptionsEditor(): IndentOptionsEditor = LamaIndentOptionsEditor()

  override fun getConfigurableDisplayName(): String = LamaLanguage.displayName

  override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
    return LamaCodeStyleSettings(settings)
  }

  override fun getLanguage(): Language = LamaLanguage

  override fun getCodeSample(settingsType: SettingsType): String = CODE_SAMPLE

  override fun customizeDefaults(commonSettings: CommonCodeStyleSettings, indentOptions: IndentOptions) {
    commonSettings.SPACE_WITHIN_BRACES = true
    indentOptions.INDENT_SIZE = 2
    indentOptions.CONTINUATION_INDENT_SIZE = 2
    indentOptions.USE_TAB_CHARACTER = false
    indentOptions.TAB_SIZE = 2
  }
}

private class LamaIndentOptionsEditor : SmartIndentOptionsEditor() {

  private val useRelativeIndent = JBCheckBox(LamaBundle.message("code.style.use.revalive.indent"))
  private val indentBranches = JBCheckBox(LamaBundle.message("code.style.indent.branches"))

  override fun addComponents() {
    super.addComponents()
    add(useRelativeIndent)
    add(indentBranches)
  }

  override fun apply(settings: CodeStyleSettings, options: IndentOptions) {
    super.apply(settings, options)
    options.USE_RELATIVE_INDENTS = useRelativeIndent.isSelected
    settings.lamaSettings().USE_INDENT_BEFORE_BRANCHES = indentBranches.isSelected
  }

  override fun isModified(settings: CodeStyleSettings, options: IndentOptions): Boolean {
    if (super.isModified(settings, options)) return true
    return isFieldModified(useRelativeIndent, options.USE_RELATIVE_INDENTS) ||
        isFieldModified(indentBranches, settings.lamaSettings().USE_INDENT_BEFORE_BRANCHES)
  }

  override fun reset(settings: CodeStyleSettings, options: IndentOptions) {
    super.reset(settings, options)
    useRelativeIndent.isSelected = options.USE_RELATIVE_INDENTS
    indentBranches.isSelected = settings.lamaSettings().USE_INDENT_BEFORE_BRANCHES
  }
}

private class LamaCodeStylePanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
  TabbedLanguageCodeStylePanel(LamaLanguage, currentSettings, settings)

private val CODE_SAMPLE = """
  import Collection;
  import Ref;

  -- Generic comparison for shared/cyclic data structures
  public infix =?= at < (x, y) {
    var m = ref (emptyMap (flatCompare));

    fun alreadyEq (x, y) {
      fun find (x) {
        fun walk (r) {
          fun walkrec (p1, p2, r) {
            case p2 of
              [_] -> p2 [0] := r
            |  _  -> skip
            esac;
            
            case r of
              [#val] -> r
            | [x]    -> walkrec (r, p1, x)
            esac
          }

          walkrec ({}, {}, r)
        }
        
        case findMap (deref (m), x) of
          Some (r) -> Some (walk (r))
        | x        -> x
        esac
      }

      case [find (x), find (y)] of
        [None, None] ->
          var v = [1];
          m ::= addMap (addMap (deref (m), x, v), y, v);
          false
           
      | [None, Some (ry)] ->
          m ::= addMap (deref (m), x, ry);
          false
           
      | [Some (rx), None] ->
          m ::= addMap (deref (m), y, rx);
          false
          
      | [Some (rx), Some (ry)] ->
          if rx == ry
          then true
          else
            if rx[0] < ry[0]
            then
              ry [0] := ry [0] + rx [0];
              rx [0] := ry
            else
              rx [0] := rx [0] + ry [0];
              ry [0] := rx
            fi;
            false
          fi
      esac    
    }

    fun cmpargs (x, y, from) {
      var diff = x.length - y.length;

      for var i = from;, i < x.length && diff == 0, i := i + 1 do
        diff := cmprec (x[i], y[i]) 
      od;
      
      diff
    }
    
    fun cmprec (x, y) {
      if alreadyEq (x, y)
      then 0
      else
        var diff = x.kindOf - y.kindOf;
        
        if diff != 0 then diff
        else
          case x of
            #array -> cmpargs (x, y, 0)
          | #fun   -> if (diff := x[0] - y[0]) == 0
                      then cmpargs (x, y, 1)
                      else diff
                      fi
          | #sexp  -> if (diff := compareTags (x, y)) == 0
                      then cmpargs (x, y, 0)
                      else diff
                      fi
          | _      -> compare (x, y)
          esac
        fi
      fi
    }

    cmprec (x, y)  
  }

  -- Generic equaliry for shared/cyclic data structures
  public infix === at == (x, y) {
    (x =?= y) == 0
  }
""".trimIndent()