package com.sunflower;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sunflower");

        noClasses()
            .that()
                .resideInAnyPackage("com.sunflower.service..")
            .or()
                .resideInAnyPackage("com.sunflower.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.sunflower.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
