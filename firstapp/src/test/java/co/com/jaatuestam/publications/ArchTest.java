package co.com.jaatuestam.publications;

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
            .importPackages("co.com.jaatuestam.publications");

        noClasses()
            .that()
                .resideInAnyPackage("co.com.jaatuestam.publications.service..")
            .or()
                .resideInAnyPackage("co.com.jaatuestam.publications.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..co.com.jaatuestam.publications.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
