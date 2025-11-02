package com.sportygroup.jackpotsystem.architecture;

import java.util.Set;
import java.util.stream.Collectors;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.sportygroup.jackpotsystem.architecture.ArchitectureValidationEnforcerTest.BASE_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class})
public class ControllerStructureValidatorTest {

  @ArchTest
  static final ArchRule controllerClassesShouldBeInPresentationEndpointPackage =
    classes()
      .that().haveSimpleNameEndingWith("Controller")
      .should().resideInAPackage("..endpoint..")
      .because(
        "Controllers must reside in the 'endpoint' package to follow the project structure.");

  @ArchTest
  static final ArchRule controllerClassesShouldBeAnnotatedWithRestController =
    classes()
      .that().haveSimpleNameEndingWith("Controller")
      .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
      .because("Controller classes must be annotated with @RestController");

  @ArchTest
  static final ArchRule controllerClassesShouldBeAnnotatedWithSwaggerTag =
    classes()
      .that().haveSimpleNameEndingWith("Controller")
      .should().beAnnotatedWith("io.swagger.v3.oas.annotations.tags.Tag")
      .because("Controller classes must be annotated with Swagger @Tag for documentation");

  @ArchTest
  static final ArchRule controllerPublicMethodsShouldHaveProperAnnotations =
    methods()
      .that().arePublic()
      .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Controller")
      .should(haveProperAnnotations());

  private static ArchCondition<JavaMethod> haveProperAnnotations() {
    return new ArchCondition<>("be annotated with @Operation and @ResponseStatus") {
      @Override
      public void check(JavaMethod method, ConditionEvents events) {
        final Set<String> annotations = method.getAnnotations().stream()
          .map(a -> a.getRawType().getFullName())
          .collect(Collectors.toSet());

        final boolean hasOperation = annotations.contains("io.swagger.v3.oas.annotations.Operation");
        final boolean hasResponseStatus =
          annotations.contains("org.springframework.web.bind.annotation.ResponseStatus");

        if (!hasOperation) {
          events.add(SimpleConditionEvent.violated(method,
            method.getFullName() + " is missing @Operation annotation"));
        }

        if (!hasResponseStatus) {
          events.add(SimpleConditionEvent.violated(method,
            method.getFullName() + " is missing @ResponseStatus annotation"));
        }
      }
    };
  }
}

