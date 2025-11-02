package com.sportygroup.jackpotsystem.architecture;

import java.util.List;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.sportygroup.jackpotsystem.architecture.ArchitectureValidationEnforcerTest.BASE_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class})
public class CommandQueryStructureValidatorTest {

  private static final String SUFFIX_COMMAND = "Command";
  private static final String SUFFIX_QUERY = "Query";

  @ArchTest
  static final ArchRule commandQueryClassesShouldHaveExecuteMethodWithInputOutput =
    classes()
      .that().haveSimpleNameEndingWith(SUFFIX_COMMAND)
      .or().haveSimpleNameEndingWith(SUFFIX_QUERY)
      .should(haveOnlyOneExecuteMethodWithInputAndOutput())
      .allowEmptyShould(true);

  @ArchTest
  static final ArchRule commandQueryClassesShouldResideInDomainPackage =
    classes()
      .that().haveSimpleNameEndingWith(SUFFIX_COMMAND)
      .or().haveSimpleNameEndingWith(SUFFIX_QUERY)
      .should().resideInAPackage("..domain..")
      .because(
        "Command and Query classes should reside in a 'domain' package to follow DDD structure.");

  @ArchTest
  static final ArchRule commandQueryClassesShouldNotDependOnSpring =
    noClasses()
      .that().haveSimpleNameEndingWith(SUFFIX_COMMAND)
      .or().haveSimpleNameEndingWith(SUFFIX_QUERY)
      .should().dependOnClassesThat().resideInAPackage("org.springframework..")
      .because(
        "Command and Query classes should not depend on Spring, keeping domain logic framework-independent.");

  private static ArchCondition<JavaClass> haveOnlyOneExecuteMethodWithInputAndOutput() {
    return new ArchCondition<>(
      "have a single public execute(...) method with optional Input param and void or Output return type") {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {
        final List<JavaMethod> publicExecuteMethods = javaClass.getMethods().stream()
          .filter(method -> method.getName().equals("execute"))
          .filter(method -> method.getModifiers().contains(JavaModifier.PUBLIC))
          .toList();

        if (publicExecuteMethods.size() != 1) {
          events.add(SimpleConditionEvent.violated(javaClass,
            javaClass.getName() + " must have exactly one public execute(...) method."));
          return;
        }

        final JavaMethod method = publicExecuteMethods.get(0);
        final int paramCount = method.getRawParameterTypes().size();

        if (paramCount == 1) {
          final JavaClass inputType = method.getRawParameterTypes().get(0);
          if (!isInnerClassOf(inputType, javaClass)) {
            events.add(SimpleConditionEvent.violated(javaClass,
              "Parameter type " + inputType.getSimpleName() + " is not an inner class of "
                + javaClass.getSimpleName()));
          }
        } else if (paramCount > 1) {
          events.add(SimpleConditionEvent.violated(javaClass,
            method.getFullName() + " must take zero or one parameter only."));
        }

        final JavaClass returnType = method.getRawReturnType();
        if (!returnType.getSimpleName().equals("void")
          && !isInnerClassOf(returnType, javaClass)) {
          events.add(SimpleConditionEvent.violated(javaClass,
            "Return type " + returnType.getSimpleName() + " is not an inner class of "
              + javaClass.getSimpleName()));
        }
      }

      private boolean isInnerClassOf(JavaClass candidate, JavaClass parent) {
        return candidate.getEnclosingClass().map(parent::equals).orElse(false);
      }
    };
  }
}

