# Projeto multi-módulo

Projeto com outros, pode ser útil para dividir monolitos em módulos ou deixar explicito alguma arquitetura,
como por exemplo, clean architecture.
Cada módulo pode depender do outro ou ser independente e ter configurações específicas.
`settings.gradle` contém nome dos projetos, módulos, pode conter configurações de repositórios
e outras configurações (presente no projeto raiz)

## Criando um projeto multi-módulo

Para isso criar uma pasta nova e ir no settings.gradle e adicionar `include "project-name"`
e adicionar build.gradle no módulo. Isso também pode ser feito pela ide.

## Tipos de projeto multi-módulo

- [Multi-project `buildSrc`](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html)
    - Onde tem uma pasta com nome `buildSrc` contendo a lógica necessária para o build dos projetos, plugins e plugins
      customs
    - Diretório é reconhecido pelo gradle automaticamente e adicionado no build se tiver a pasta com `build.gradle`
    - Uso de dependências no shared - `apply from: rootProject.file('buildSrc/shared.gradle')`
    - [coisas em comuns entre projetos](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html#sec:convention_plugins_vs_cross_configuration),

- [Composite builds](https://docs.gradle.org/current/userguide/composite_builds.html)
    - Bom para compartilhar lógica de build ou isolar acesso a plugins com convenções
    - Cada build é isolada

## Algumas tasks úteis

`gradlew tasks` - ver tasks
`gradlew projects` - ver estrutura do projetos
`gradlew build --info` - consegue ver informações das tasks rodadas e as tasks em si
`gradlew :module-name:task-name` - consegue rodar a task de um módulo especifico
`gradlew :module-name:dependencies` - lista dependências do projeto

## Plugin (Configurações adicionais para build do projeto)

Plugins no projeto raiz ou em sub-módulos só funciona para eles.
Adiciona tasks como build, clean, compileJava, jar, assemble,... relacionadas ao java no submódulo onde está presente

```
plugins {
    id 'java'
}
```

Gradle não recomenda subprojects e allprojects para
configurar [coisas em comuns entre projetos](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html#sec:convention_plugins_vs_cross_configuration),
pois deixa implicito as configurações, pode-se tornar complexo, pode trazer acomplamento entre os projetos e problemas
de otimização.

O `build.gradle` na raiz permite centralizar dependências e configurações nos filhos
utilizando subprojects ou allprojects (que inclui o projeto raiz também)
**gradle não recomenda isso**
Configuração condicional no build.gradle raiz não faz muito sentido é melhor configurar no projeto especifico

```
subprojects {
    if(["app"].contains(project.name)) { //adiciona configuração apenas no módulo app
        println("Configurating project = ${project.name}")
        apply plugin: "java"

        group = 'br.com.cleanarch'
        version = '1.0-SNAPSHOT'

        dependencies {
            testImplementation platform('org.junit:junit-bom:5.10.0')
            testImplementation 'org.junit.jupiter:junit-jupiter'
        }

        test {
            useJUnitPlatform()
        }
    }
}
```

Pode-se criar configure para os projetos especificos ao invés de fazer condições em subprojects

```
def javaProjects = [
        project(":app")
]

configure(javaProjects) {
    println("Configurating project = ${project.name}")
    apply plugin: "java"

    group = 'br.com.cleanarch'
    version = '1.0-SNAPSHOT'

    dependencies {
        testImplementation platform('org.junit:junit-bom:5.10.0')
        testImplementation 'org.junit.jupiter:junit-jupiter'
    }

    test {
        useJUnitPlatform()
    }
}
```

Pode-se também configurar um projeto project

```
project(":app") {
    task("test2").doLast {
        println("Some task on project $project.name")
    }
}
```

### allproject

Pode ser útil para configurações em todos os projetos incluindo o raiz como versão do java, encoding, repositórios e
alguma dependência. Válido quando projeto raiz tem classes.

## Dependências entre projetos

Ir no projeto que depende do outro e adicionar

```
dependencies {
    implementation project(":module-name")
}
```

Para configurar dependências transitivas entre os projetos pode ser feita por
plugin `java-library` e o uso de api ao invés de implementation

```
dependencies {
    api "some-deps-that-will-be-shared-who-use-that-module"
}
```

Pode-se criar um arquivo chamado dependencies.gradle para centralizar dependências

```
ext.libs = [
    "deps-some-key": "dependency",
    ...
]

ext.libsTest = [
   "deps-some-key-test": "dependency",
   ...
]
```

### Version catalog - Para usar as libs é necessário importar a file por

```
apply from: "$rootProject.projectDir/dependencies.gradle"
```

e para usar

```
dependencies {
    implementation libs.depsSomeKey
    implementation libsTest.depsSomeKeyTest
}
```

Alternativamente pode-se usar um arquivo toml que deve ficar localizado em `gradle` com nome `libs.versions.toml`

```
[versions]
junit = "5.10.0"

[libraries]
junit = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit" }

[plugins]
androidApplication = { id = "com.android.application", version.ref = "androidGradlePlugin" }
```

e para usar

```
plugins {
   alias(libs.plugins.plugin_name)
}

dependencies {
    implementation libs.depsSomeKey
    implementation libsTest.depsSomeKeyTest
}
```

## Sobre java plugin

- Task `processResources` copia arquivos da pasta resources em `build/resources/main`
- Task `jar` gera jar em `build/libs` como nome <project-name>.<version>.jar
- Task build

```
:build (aggregate tasks)
    :assemble (depends on check tasks)
       :jar
            :classes
               :compileJava
               :processResources
    :check
       :test
           :classes
               :compileJava
               :processResources
       :testClasses
           :compileTestJava
              :classes
                   ...
           :processTestResources
```

## Gradle lifecycle

1. Inicialization phase (começa olhando o settings.gradle e projetos)
2. Configuration phase (olha os build.gradle e suas tasks - `configure` e tasks que possuem lógica fora de `doFirst`,
   `doLast` são executadas)
3. Execution phase (ao rodar as tasks)

## settings.gradle

Define configurações gerais do projeto como módulos e configurações de plugins, repositórios etc.

- `pluginManagement` - bloco que serve para definir repositórios gerais de plugins e alguns plugins utilizados no build
  do projeto
- `plugins` - bloco de plugins mas específico para build time
- `dependencyResolutionManagement` - lugar centralizado para definir configurações de repositório de dependências

## Sobre tipos de plugins

- `core` - plugins nativos do gradle
- `community` - não é nativo do gradle, mas pode ser adicionado
- `local` - plugins criados localmente

#### [Plugin for convensions](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:convention_plugins)

- Adiciona convenções compartilhadas entre o projeto (lugar centralizado para configurações em comuns entre projetos)
- É possível definir versão do java, configurações de plugins, criação de tasks, dependências etc

[Clean arch](https://developers.redhat.com/articles/2023/08/08/implementing-clean-architecture-solutions-practical-example)

## Api First, Openapi e Openapi generator
- Openapi (Swagger): especificação que poder ser utilizada na abordagem Api First (definição do contrato da api "endpoints e modelos" antes de iniciar o desenvolvimento);

- Openapi generator: gerador de código a partir da especificação (openapi - arquivo yaml ou json).

No projeto foi feito uma poc utilizando api first e o openapi generator. As configurações do gerador de código está presente na pasta app. Já
a especificação swagger utilizada pelo gerador da poc está presente na raiz do projeto. Nela está definido os endpoints e modelos de uma api fake para encontrar anúncios de carros (ideia baseada no web-scrapping-car-ads).


#### Alguns tradeoff do openapi generator
- Algumas configurações tem acoplamento, ou seja, desligando uma desliga outra, por exemplo, delegatorPattern, interfaceOnly, skipInterfaceDefault. E algumas configurações parecem confusas, por exemplo, performBeanValidations, useBeanValidations;
- Códigos dos modelos gerados tem setters (o que não parece legal para dtos que deveriam ser imutáveis);
- Classes geradas possuem imports não utilizados. O que pode fazer o build do projeto demorar um pouco mais, no caso de existir muitas classes (é possível usar um linter como spotless, mas imports * não são removidos);
- Padrão delegator parece um pouco complicado e não parece trazer tanta vantagem (é feito um mix de conceitos entre controller e service, isso porque, ao utilizar delegator é dado uma classe que deve ser um service. Essa classe contém a  responsabilidade de retornar um ResponseEntity que faria mais sentido controller para um controller - acredito que é possível fazer a modificação para não retornar esse wrapper).


[Especificação Openapi - Swagger](https://swagger.io/specification/)
[Openapi editor](https://editor-next.swagger.io/)
[Openapi Generator - spring](https://openapi-generator.tech/docs/generators/spring)
[Openapi Generator - plugin gradle](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-gradle-plugin)
[Openapi generator - templates dos códigos gerados](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources/JavaSpring)


