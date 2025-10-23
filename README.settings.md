# Uso do settings.xml (template)

Este repositório inclui um `settings.xml` de exemplo (`settings.xml`) que serve como template para publicar artefatos no GitHub Packages.

IMPORTANTE: NUNCA comite credenciais reais no repositório. Preencha os placeholders localmente ou use secrets no CI.

## Uso local (apenas para desenvolvimento)

1. Copie o arquivo para o seu diretório Maven local (substitua qualquer arquivo existente):

   Linux / macOS:
   ```bash
   cp settings.xml ~/.m2/settings.xml
   ```

   Windows (PowerShell):
   ```powershell
   Copy-Item -Path .\settings.xml -Destination $env:USERPROFILE\.m2\settings.xml -Force
   ```

2. Edite `~/.m2/settings.xml` e substitua os placeholders `REPLACE_WITH_USERNAME` e `REPLACE_WITH_PASSWORD` pelas suas credenciais (username do GitHub e um PAT com `write:packages`).

3. Rode `mvn deploy` localmente para enviar artefatos para o GitHub Packages (somente se realmente quiser publicar).

## Uso em GitHub Actions (recomendado)

Não é necessário commitar secrets. Recomenda-se usar a action `actions/setup-java` para gerar um `settings.xml` temporário no runner e injetar as credenciais a partir de secrets.

Exemplo (trecho do workflow):

```yaml
uses: actions/setup-java@v4
with:
  distribution: 'temurin'
  java-version: '17'
  server-id: 'github'
  server-username: '${{ github.actor }}'
  server-password: '${{ secrets.GITHUB_TOKEN }}' # ou um secret com PAT
  settings-path: '${{ github.workspace }}'
```

Depois, o workflow pode chamar `mvn deploy --settings ${{ github.workspace }}/settings.xml`.

### Quando usar um PAT (Personal Access Token)

Se `GITHUB_TOKEN` não tiver permissão (por exemplo: publicação a partir de fork, ou políticas organizacionais), crie um PAT com os scopes necessários:

- `repo` (se o repositório for privado)
- `write:packages`

Adicione o PAT como secret no repo: `Settings` → `Secrets and variables` → `Actions` → `New repository secret` (ex.: `MAVEN_PUBLISH_TOKEN`).

No workflow, passe `${{ secrets.MAVEN_PUBLISH_TOKEN }}` como `server-password` ou em `env` quando chamar `mvn deploy`.

## Dicas de segurança

- Nunca imprima secrets nos logs.
- Use `GITHUB_TOKEN` quando possível (tem permissões automáticas e expira por execução).
- Para publicar releases automáticos, prefira criar um workflow que rode apenas em `release: created` e valide que a versão no `pom.xml` não contenha `-SNAPSHOT`.

## Verificação rápida

- Se o workflow falhar em `Deploy to GitHub Packages`, cheque o log do Maven (401/403 indicam problema de credencial; 400/404 indicam URL incorreta; SNAPSHOT indica versão inadequada).
