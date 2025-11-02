package com.sportygroup.jackpotsystem.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.sportygroup.jackpotsystem.architecture.ArchitectureValidationEnforcerTest.BASE_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class})
public class PackageStructureValidatorTest {

  @ArchTest
  static final ArchRule domainShouldNotDependOnSpringOrJakarta =
    noClasses()
      .that().resideInAPackage("..domain..")
      .should().dependOnClassesThat()
      .resideInAnyPackage("org.springframework..", "jakarta.validation..")
      .because("Domain layer must not depend on external frameworks like Spring or Jakarta.");

  @ArchTest
  static final ArchRule domainShouldNotDependOnInfrastructure =
    noClasses()
      .that().resideInAPackage("..domain..")
      .should().dependOnClassesThat()
      .resideInAPackage("..infrastructure..")
      .because("Domain layer must not depend on infrastructure layer.");

  @ArchTest
  static final ArchRule configurationsShouldBeInConfigPackage =
    classes()
      .that().areAnnotatedWith("org.springframework.context.annotation.Configuration")
      .should().resideInAPackage("..infrastructure..config..")
      .because(
        "Spring @Configuration classes should reside in a dedicated infrastructure.config package.");

  @ArchTest
  static final ArchRule entitiesShouldResideInEntityPackage =
    classes()
      .that().areAnnotatedWith("jakarta.persistence.Entity")
      .should().resideInAPackage("..infrastructure..entity..")
      .because("JPA entities should reside in infrastructure.entity package.");
}

