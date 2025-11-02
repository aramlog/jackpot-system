package com.sportygroup.jackpotsystem.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.sportygroup.jackpotsystem.architecture.CommandQueryStructureValidatorTest.commandQueryClassesShouldHaveExecuteMethodWithInputOutput;
import static com.sportygroup.jackpotsystem.architecture.CommandQueryStructureValidatorTest.commandQueryClassesShouldNotDependOnSpring;
import static com.sportygroup.jackpotsystem.architecture.CommandQueryStructureValidatorTest.commandQueryClassesShouldResideInDomainPackage;
import static com.sportygroup.jackpotsystem.architecture.ControllerStructureValidatorTest.controllerClassesShouldBeAnnotatedWithRestController;
import static com.sportygroup.jackpotsystem.architecture.ControllerStructureValidatorTest.controllerClassesShouldBeAnnotatedWithSwaggerTag;
import static com.sportygroup.jackpotsystem.architecture.ControllerStructureValidatorTest.controllerClassesShouldBeInPresentationEndpointPackage;
import static com.sportygroup.jackpotsystem.architecture.ControllerStructureValidatorTest.controllerPublicMethodsShouldHaveProperAnnotations;
import static com.sportygroup.jackpotsystem.architecture.PackageStructureValidatorTest.configurationsShouldBeInConfigPackage;
import static com.sportygroup.jackpotsystem.architecture.PackageStructureValidatorTest.domainShouldNotDependOnInfrastructure;
import static com.sportygroup.jackpotsystem.architecture.PackageStructureValidatorTest.domainShouldNotDependOnSpringOrJakarta;
import static com.sportygroup.jackpotsystem.architecture.PackageStructureValidatorTest.entitiesShouldResideInEntityPackage;

class ArchitectureValidationEnforcerTest {

  public static final String BASE_PACKAGE = "com.sportygroup.jackpotsystem";

  @Test
  void enforceCommandQueryArchitectureRules() {
    final var importedClasses = new ClassFileImporter()
      .withImportOption(new ImportOption.DoNotIncludeTests())
      .importPackages(BASE_PACKAGE);

    // Command/Query structure checks
    commandQueryClassesShouldHaveExecuteMethodWithInputOutput.check(importedClasses);
    commandQueryClassesShouldResideInDomainPackage.check(importedClasses);
    commandQueryClassesShouldNotDependOnSpring.check(importedClasses);

    // Controllers structure checks
    controllerClassesShouldBeInPresentationEndpointPackage.check(importedClasses);
    controllerClassesShouldBeAnnotatedWithRestController.check(importedClasses);
    controllerClassesShouldBeAnnotatedWithSwaggerTag.check(importedClasses);
    controllerPublicMethodsShouldHaveProperAnnotations.check(importedClasses);

    // Packages structure checks
    domainShouldNotDependOnSpringOrJakarta.check(importedClasses);
    domainShouldNotDependOnInfrastructure.check(importedClasses);
    configurationsShouldBeInConfigPackage.check(importedClasses);
    entitiesShouldResideInEntityPackage.check(importedClasses);
  }
}

