package br.com.carads;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {
    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("br.com.cleanarch");

    @ParameterizedTest
    @ValueSource(classes = {Component.class, Service.class, Repository.class, Controller.class})
    void classInCoreCannotBeComponentBasedAnnotation(final Class<? extends Annotation> clazz) {
        final ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..core..")
                .should()
                .notBeAnnotatedWith(clazz);

        rule.check(importedClasses);
    }

    @Test
    void classInCoreCannotBeAnyDependencyOfAnyAnotherLayer() {
        final ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..core..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..configuration..", "..entrypoint..", "..dataproviders..");

        rule.check(importedClasses);
    }

    @Test
    void classInConfigurationCannotBeAnyDependencyOfAnyAnotherLayer() {
        final ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..configuration..")
                .should()
                .onlyBeAccessed()
                .byAnyPackage();

        rule.check(importedClasses);
    }
}
