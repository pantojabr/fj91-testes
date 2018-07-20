# Cadastro de Cargos

Para registrar os cargos e suas faixas salariais de acordo com as normas da empresa, como funcionário do DP, quero uma funcionalidade que permita o cadastro dos cargos e salários da empresa.

## Critérios de aceitação

* A funcionalidade deve permitir o cadastro, listagem e exclusão dos cargos;
* As seguintes informações dos cargos devem ser cadastradas: Nome e Faixa Salarial(Salário Mínimo e Máximo);
* O valor do salário mínimo do cargo deve respeitar o salário mínimo nacional Brasileiro(R$954,00);
* O valor do salário máximo de qualquer cargo não deve ultrapassar o valor de R$100.000,00;
* O valor do salário mínimo não pode ser superior ao valor do salário máximo;
* O nome do cargo deve ser único no sistema;
* O sistema não deve permitir a exclusão de um cargo que ainda esteja atribuido a algum funcionário;