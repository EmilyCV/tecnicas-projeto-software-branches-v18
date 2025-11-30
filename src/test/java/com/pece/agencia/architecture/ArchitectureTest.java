package com.pece.agencia.architecture;

import com.pece.agencia.api.ApiApplication;
import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import static java.lang.String.format;

class ArchitectureTest {
    private static class ExceptionGetMessageCalledByNonSuppressedPredicate extends DescribedPredicate<JavaMethodCall> {
        public ExceptionGetMessageCalledByNonSuppressedPredicate() {
            super("call to Exception.getMessage by methods not annotated with @SuppressWarnings");
        }

        private boolean isTargetExceptionGetMessageCall(JavaMethodCall call) {
            boolean isException = call.getTarget().getOwner().isAssignableTo(Throwable.class);
            String targetName = call.getTarget().getName();
            return isException && "getMessage".equals(targetName);
        }

        private boolean callerMayNotParseExceptionMessage(JavaMethodCall call) {
            return !call.getOrigin().isAnnotatedWith(MayParseExceptionMessage.class);
        }
        @Override
        public boolean test(JavaMethodCall call) {
            return isTargetExceptionGetMessageCall(call) && callerMayNotParseExceptionMessage(call);
        }
    }

    @Nested
    @DisplayName("ACL upstream tests")
    class AclUpstreamRulesTest {
        private void checkAclUpstreamRule(String acl) {
            JavaClasses importedClasses = importMainClasses();
            ArchRuleDefinition.noClasses()
                    .that()
                    .resideOutsideOfPackages(
                            format("com.pece.agencia.api.core.service.acl.%s..", acl),
                            format("com.pece.agencia.api.%s..", acl)
                    )
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(format("com.pece.agencia.api.%s..", acl))
                    .because(format("Apenas classes do pacote acl.%s podem acessar o context %s", acl, acl))
                    .check(importedClasses);

        }


        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado locacao veiculo")
        void onlyAclVeiculoServiceCanAccessVeiculoPackage() {
            checkAclUpstreamRule("veiculo");
        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado hoteleira")
        void onlyAclHotelariaServiceCanAccessHotelariaPackage() {
            checkAclUpstreamRule("hotelaria");
        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado translado aereo")
        void onlyAclTransladoAereoServiceCanAccessAereoPackage() {
            checkAclUpstreamRule("aereo");
        }


        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado de pagamento")
        void onlyAclPagamentoServiceCanAccessPagamentoPackage() {
            checkAclUpstreamRule("pagamento");
        }
    }


    @DisplayName("Garanta que não há violações de arquitetura modular (acesso via APIs)")
    @Test
    void ensureModuleAccessThruAPIs() {
        ApplicationModules.of(ApiApplication.class)
                .detectViolations()
                .throwIfPresent();
    }

    @Test
    @DisplayName("Somente classes do pacote stripe podem acessar com.stripe")
    void onlyStripeServiceCanAccessStripePackage() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that()
                .resideOutsideOfPackages("com.pece.agencia.api.pagamento.internal.stripe..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.stripe..")
                .because("Somente classes do pacote stripe podem acessar com.stripe e seus subpacotes")
                .check(importedClasses);
    }

    private JavaClasses importMainClasses() {
        // Importa apenas classes do diretório de produção (target/classes)
        return new ClassFileImporter().importPath("target/classes");
    }


    @Test
    void servicesShouldResideInServicePackage() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().resideInAPackage("..service..")
                .check(importedClasses);
    }

    @Test
    void controllersShouldOnlyDependOnServiceAndDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..repository..")
                .because("Controllers não devem depender de repositórios diretamente, apenas de services e domain").check(importedClasses);
    }

    @Test
    void servicesShouldOnlyDependOnRepositoryAndDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..")
                .because("Services não devem depender de controllers, apenas de repository e domain").check(importedClasses);
    }

    @Test
    void repositoriesShouldOnlyDependOnDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..")
                .because("Repositories não devem depender de controllers ou services, apenas de domain").check(importedClasses);
    }

    @Test
    void domainShouldNotDependOnOtherLayers() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..", "..repository..")
                .because("Domain não deve depender de nenhuma camada externa").check(importedClasses);
    }

    @Test
    @DisplayName("Não permitir parsing da mensagem de exceção (getMessage)")
    void shouldNotParseExceptionMessage() {
        JavaClasses importedClasses = importMainClasses();

        ArchRuleDefinition.noClasses()
                .should().callMethodWhere(new ExceptionGetMessageCalledByNonSuppressedPredicate())
                .because("Não é permitido fazer parsing da mensagem de exceção (getMessage)")
                .check(importedClasses);
    }
}
