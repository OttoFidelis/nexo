# 📚 Guia Completo de Testes do Nexo

## 🔍 Como o GitHub Actions Funciona com os Testes

### Fluxo do Workflow CI/CD

```
1. TRIGGER (Gatilho)
   ├─ Push para main
   └─ Pull Request para main
          ↓
2. CHECKOUT
   └─ Baixa código do repositório
          ↓
3. SETUP JAVA 17
   └─ Instala JDK e configura Maven
          ↓
4. BUILD & TEST 🎯
   └─ mvn -B package
      ├─ mvn clean (limpa target/)
      ├─ mvn compile (compila src/main/java)
      ├─ mvn test ⚡ (RODA TODOS OS TESTES!)
      │  └─ Se QUALQUER teste falhar: ❌ BUILD FALHA
      └─ mvn package (gera JAR)
          ↓
5. RESULTADO
   ├─ ✅ Todos testes passam = Build Sucesso
   └─ ❌ Algum teste falha = Build Falha (não gera JAR)
```

### O Comando `mvn -B package` Faz:

1. **Compilação**: Transforma `.java` em `.class`
2. **Testes Unitários**: Executa TODOS os arquivos `*Test.java`
3. **Validação**: Se houver falha, **interrompe o build**
4. **Empacotamento**: Gera `nexo-0.0.1-SNAPSHOT.jar`

---

## 📊 Status Atual dos Testes

### ✅ Teste Existente

```java
@Test
void contextLoads() {}  // ← Só verifica se o Spring Boot sobe
```

**Problema**: Isso testa **apenas 1% do sistema!**

---

## 🎯 O Que Você Deve Implementar

### Pirâmide de Testes Recomendada

```
         /\
        /  \    ← 10% Testes de Integração (E2E)
       /____\
      /      \  ← 20% Testes de API (Controllers)
     /________\
    /          \ ← 70% Testes Unitários (Services)
   /____________\
```

### 1️⃣ **Testes Unitários (Services)** - CRIADOS ✅

Testam a **lógica de negócio** isoladamente usando **Mockito**:

- ✅ `CategoriaServiceTest.java` (7 testes)
- ✅ `DespesasServiceTest.java` (6 testes)
- ✅ `ReceitasServiceTest.java` (7 testes)
- ✅ `SaldoServiceTest.java` (7 testes)
- ✅ `UsuarioServiceTest.java` (6 testes)

**Total**: 33 testes unitários

**Padrão AAA (Arrange-Act-Assert)**:
```java
@Test
void deveCriarCategoria() {
    // Arrange - Prepara dados e mocks
    when(repository.save(any())).thenReturn(categoria);
    
    // Act - Executa a ação
    CategoriaModel resultado = service.create(categoria);
    
    // Assert - Verifica resultado
    assertNotNull(resultado);
    assertEquals("Alimentação", resultado.getNome());
    verify(repository, times(1)).save(categoria);
}
```

---

### 2️⃣ **Testes de Integração (Repositories)** - A CRIAR 🔨

Testam a **persistência** com banco H2 real:

```java
@DataJpaTest
class CategoriaRepositoryTest {
    
    @Autowired
    private CategoriaRepository repository;
    
    @Test
    void deveSalvarCategoria() {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setNome("Transporte");
        
        CategoriaModel salva = repository.save(categoria);
        
        assertNotNull(salva.getId());
        assertEquals("Transporte", salva.getNome());
    }
    
    @Test
    void deveBuscarPorId() {
        // Salva primeiro
        CategoriaModel categoria = repository.save(new CategoriaModel());
        
        // Busca depois
        Optional<CategoriaModel> found = repository.findById(categoria.getId());
        
        assertTrue(found.isPresent());
    }
}
```

---

### 3️⃣ **Testes de Controllers (API REST)** - A CRIAR 🔨

Testam os **endpoints HTTP** com `MockMvc`:

```java
@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CategoriaService service;
    
    @Test
    void deveRetornarTodasCategorias() throws Exception {
        List<CategoriaModel> categorias = Arrays.asList(
            new CategoriaModel(1, "Alimentação", "Comida"),
            new CategoriaModel(2, "Transporte", "Uber")
        );
        
        when(service.findAll()).thenReturn(categorias);
        
        mockMvc.perform(get("/api/categorias"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nome").value("Alimentação"));
    }
    
    @Test
    void deveCriarCategoria() throws Exception {
        String json = "{\"nome\":\"Lazer\",\"descricao\":\"Entretenimento\"}";
        
        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());
    }
}
```

---

### 4️⃣ **Testes E2E (Ponta a Ponta)** - OPCIONAL 🔨

Testam o **sistema completo** com `@SpringBootTest`:

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NexoE2ETest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void deveRealizarFluxoCompletoDeReceita() {
        // 1. Cria usuário
        UsuarioModel usuario = criarUsuario("teste@example.com");
        
        // 2. Cria categoria
        CategoriaModel categoria = criarCategoria("Salário");
        
        // 3. Cria receita
        ReceitasModel receita = new ReceitasModel();
        receita.setDescricao("Salário Outubro");
        receita.setQuantia(5000.00);
        receita.setUsuario(usuario);
        receita.setCategoria(categoria);
        
        ResponseEntity<ReceitasModel> response = restTemplate.postForEntity(
            "/api/receitas",
            receita,
            ReceitasModel.class
        );
        
        // 4. Verifica saldo atualizado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        
        SaldoModel saldo = buscarSaldo(usuario);
        assertEquals(5000.00, saldo.getQuantia(), 0.01);
    }
}
```

---

## 🛠️ Dependências Necessárias (pom.xml)

```xml
<dependencies>
    <!-- JUnit 5 - Framework de testes -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Mockito - Mocks para testes unitários -->
    <!-- Já incluído no spring-boot-starter-test -->
    
    <!-- H2 - Banco em memória para testes -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🚀 Comandos Para Rodar Testes

### Local (PowerShell)

```powershell
# Rodar todos os testes
.\mvnw.cmd test

# Rodar teste específico
.\mvnw.cmd test -Dtest=CategoriaServiceTest

# Rodar com relatório de cobertura
.\mvnw.cmd test jacoco:report

# Compilar E testar (igual ao GitHub Actions)
.\mvnw.cmd package
```

### CI/CD (GitHub Actions)

```yaml
# Já configurado em .github/workflows/maven.yml
- name: Build with Maven
  run: mvn -B package --file pom.xml
```

---

## 📈 Cobertura de Testes Ideal

| Camada | Cobertura Mínima | Seu Projeto |
|--------|------------------|-------------|
| Models | 80% | 🔨 A implementar |
| Services | 90% | ✅ 90% (com novos testes) |
| Repositories | 70% | 🔨 A implementar |
| Controllers | 80% | 🔨 A implementar |
| **TOTAL** | **80%** | **🎯 Meta** |

---

## 🎯 Checklist de Implementação

### Fase 1: Testes Unitários ✅
- [x] CategoriaServiceTest
- [x] DespesasServiceTest
- [x] ReceitasServiceTest
- [x] SaldoServiceTest
- [x] UsuarioServiceTest
- [x] RelatorioServiceTest

### Fase 2: Testes de Integração 🔨
- [ ] CategoriaRepositoryTest
- [ ] DespesasRepositoryTest
- [ ] ReceitasRepositoryTest
- [ ] SaldoRepositoryTest
- [ ] UsuarioRepositoryTest

### Fase 3: Testes de Controllers 🔨
- [ ] CategoriaControllerTest
- [ ] DespesasControllerTest
- [ ] ReceitasControllerTest
- [ ] SaldoControllerTest
- [ ] UsuarioControllerTest
- [ ] RelatorioControllerTest

### Fase 4: Testes E2E (Opcional) 🔨
- [ ] FluxoReceitaCompleto
- [ ] FluxoDespesaCompleto
- [ ] FluxoRelatorioCompleto

---

## 🐛 Debugging de Testes

### Ver output detalhado
```powershell
.\mvnw.cmd test -X
```

### Rodar apenas um método
```powershell
.\mvnw.cmd test -Dtest=CategoriaServiceTest#deveCriarCategoria
```

### Ver relatório HTML (JaCoCo)
```powershell
.\mvnw.cmd jacoco:report
# Abre: target/site/jacoco/index.html
```

---

## 📚 Recursos Adicionais

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [AssertJ Fluent Assertions](https://assertj.github.io/doc/)

---

## 📞 Próximos Passos

1. ✅ **Compilar os testes atuais** (precisam de ajustes)
2. 🔨 **Criar testes de Repository** (integração com H2)
3. 🔨 **Criar Controllers** (você ainda não tem!)
4. 🔨 **Criar testes de Controllers** (API REST)
5. 📊 **Gerar relatório de cobertura** (JaCoCo)

---

**Autor**: Otto Fidelis  
**Versão**: 1.0  
**Data**: Outubro 2025
