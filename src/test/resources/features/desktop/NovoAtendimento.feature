# language: pt
@Desktop
@NovoAtendimento
Funcionalidade: Validar a abertura da tela 'Novo Atendimento' no CCL

  Contexto:
#    Dado que estou com o app CCL aberto
    Dado que eu abro o aplicativo "CCL"

  @Positivo
  Cenário: Acessar tela 'Novo Atendimento'
    Quando tento interagir com o aplicativo
#    Quando acesso a tela "Novo atendimento"