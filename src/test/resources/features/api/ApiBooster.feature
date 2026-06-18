# language: pt
@Api
Funcionalidade: Testes da API Booster

  Cenário: Consultar áreas e validar primeiro item
    Dado que eu configure a API para o ambiente de testes
    Quando consulto a lista de áreas
    Então o status code deve ser 200
    E o tempo de resposta deve ser menor que 2 segundos
    E o corpo da resposta não está vazio
    E capturo o ID do primeiro item
    Quando consulto a área pelo ID capturado
    Então o status code deve ser 200
    E o ID retornado deve ser igual ao ID capturado

  Cenário: Verificar saúde da API
    Quando consulto o health check
    Então o status code deve ser 200
    E o tempo de resposta deve ser menor que 1 segundos
    E o status do serviço é "UP"