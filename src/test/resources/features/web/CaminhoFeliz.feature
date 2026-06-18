# language: pt
@Web
@CaminhoFeliz
Funcionalidade: Navegação feliz do cliente

  Contexto:
    Dado que estou utilizando o navegador "Chrome"
    Dado que estou na home page da Casa do Construtor
    Quando defino a localização para "SP" e "Rio Claro"
    E defino que deve ser a loja "Av. Brasil"
#    E defino que deve ser a "primeira" loja

  @Positivo
  Cenário: Buscar betoneira/andaime e validar detalhes
    Dado que pesquiso pelo produto "Betoneira"
#    Dado que pesquiso pelo produto "Andaime"
    E seleciono o primeiro produto disponível na grid de resultados
    Então valido que nome selecionado na busca está contido ao nome do primeiro produto