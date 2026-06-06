# language: pt
@BuscaFiltros
Funcionalidade: Validação do motor de busca e filtros

  Contexto:
    Dado que estou na home page da Casa do Construtor
    Quando defino a localização para "SP" e "Rio Claro"
    E defino que deve ser a loja "Rua 06"
#    E defino que deve ser a "segunda" loja

  @Positivo
  Cenário: Busca por termo válido retorna resultados
    Dado que pesquiso pelo produto "Limpeza"
    Então valido os resultados de produtos exibidos na grid

  @Negativo
  Cenário: Busca por termo inexistente exibe mensagem adequada
    Dado que retorno ao campo de pesquisa e o limpo
    Quando que pesquiso por um produto inexistente como: "produtoteste12345"
#    Quando que pesquiso por um produto inexistente como: "Limpeza"
    Então valido mensagem de que nenhum resultado foi encontrado na busca