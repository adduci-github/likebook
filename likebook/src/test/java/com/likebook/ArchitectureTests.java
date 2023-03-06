package com.likebook;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.likebook")
public class ArchitectureTests {
    @ArchTest
    public static final ArchRule domain_layer_is_accessed_only_by_application_layer = classes().that().resideInAPackage("..domain..")
            .should().onlyHaveDependentClassesThat().resideInAPackage("..application");

    @ArchTest
    public static final ArchRule application_layer_is_accessed_only_by_port = classes().that().resideInAPackage("..application")
            .should().onlyHaveDependentClassesThat().resideInAPackage("..application.port..");

    @ArchTest
    public static final ArchRule port_define_in_application_layer = classes().that().haveSimpleNameEndingWith("Port")
            .should().resideInAPackage("..port..");

    @ArchTest
    public static final ArchRule adapter_only_accessible_port = classes().that().resideInAPackage("..adapter..")
            .should().onlyHaveDependentClassesThat().resideInAPackage("..port..");
}
