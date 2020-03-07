package co.com.publishapp;

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
            .importPackages("co.com.publishapp");

        noClasses()
            .that()
                .resideInAnyPackage("co.com.publishapp.service..")
            .or()
                .resideInAnyPackage("co.com.publishapp.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..co.com.publishapp.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
