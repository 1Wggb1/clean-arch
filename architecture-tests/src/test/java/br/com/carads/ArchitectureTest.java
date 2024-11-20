package br.com.carads;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
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
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {
    private static final JavaClasses IMPORTED_CLASSES = new ClassFileImporter()
            .importPackages("br.com.cleanarch");

    private static final String CONFIGURATION_LAYER_NAME = "Configuration";
    private static final String ENTRYPOINTS_LAYER_NAME = "Entrypoints";
    private static final String CORE_LAYER_NAME = "Core";
    private static final String DATAPROVIDERS_LAYER_NAME = "Dataproviders";

    private static final Architectures.LayeredArchitecture LAYERS = layeredArchitecture().consideringOnlyDependenciesInLayers()
            .layer(CONFIGURATION_LAYER_NAME).definedBy("..configuration..")
            .layer(ENTRYPOINTS_LAYER_NAME).definedBy("..entrypoints..")
            .layer(CORE_LAYER_NAME).definedBy("..core..")
            .layer(DATAPROVIDERS_LAYER_NAME).definedBy("..dataproviders..");

    @ParameterizedTest
    @ValueSource(classes = {Component.class, Service.class, Repository.class, Controller.class})
    void classesInCoreCannotHaveComponentBasedAnnotations(final Class<? extends Annotation> clazz) {
        final ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..core..")
                .should()
                .notBeAnnotatedWith(clazz);

        rule.check(IMPORTED_CLASSES);
    }

    @Test
    void classesInCoreCannotBeAnyDependencyOfAnyAnotherLayer() {
        final ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..core..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..configuration..", "..entrypoint..", "..dataproviders..", "..dto..", "..api..");

        rule.check(IMPORTED_CLASSES);
    }

    @Test
    void classesInConfigurationCannotBeAccessedByAnyLayer() {
        final ArchRule rule = LAYERS.whereLayer(CONFIGURATION_LAYER_NAME)
                .mayNotBeAccessedByAnyLayer();

        rule.check(IMPORTED_CLASSES);
    }

    @Test
    void classesInDataprovidersCannotBeAccessedByAnyLayerExceptConfiguration() {
        final ArchRule rule = LAYERS.whereLayer(DATAPROVIDERS_LAYER_NAME)
                .mayOnlyBeAccessedByLayers(CONFIGURATION_LAYER_NAME);

        rule.check(IMPORTED_CLASSES);
    }

    @Test
    void classesInEntrypointsCannotBeAccessedByAnyLayerExceptConfiguration() {
        final ArchRule rule = LAYERS.whereLayer(ENTRYPOINTS_LAYER_NAME)
                .mayOnlyBeAccessedByLayers(CONFIGURATION_LAYER_NAME);

        rule.check(IMPORTED_CLASSES);
    }
}
