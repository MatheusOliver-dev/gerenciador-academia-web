//ESPECIALIDADES
$(document).ready(function () {
    console.log('Script carregado');
    $('#tabelaEspecialidades tbody').on('click', 'tr', function () {
        var id = $(this).data('id');
        var nome = $(this).data('nome');

        $('#tabelaEspecialidades tbody tr').removeClass('table-active');

        $(this).addClass('table-active');

        $('#idEdicaoEspecialidade').val(id);
        $('#nomeEdicaoEspecialidade').val(nome);

        console.log("ID:", id, "Nome:", nome);

        $('#btnEditarEspecialidade').prop('disabled', false);
        $('#btnRemoverEspecialidade').prop('disabled', false);
    });

    $('#btnRemoverEspecialidade').on('click', function () {
        var id = $('#idEdicaoEspecialidade').val();

        $.ajax({
            url: '/especialidade/remover/' + id,
            method: 'DELETE',
            success: function (response) {
                alert(response);
                $('tr[data-id="' + id + '"]').remove();
            },
            error: function (xhr, status, error) {
                alert('Erro ao remover especialidade!');
            }
        });
    });

    //INSTRUTORES
    $('#tabelaInstrutores tbody').on('click', 'tr', function () {
        var id = $(this).data('id');
        var nome = $(this).data('nome');
        var cpf = $(this).data('cpf');
        var telefone = $(this).data('telefone');
        var email = $(this).data('email');
        var especialidadeId = $(this).data('especialidade-id');

        $('#tabelaInstrutores tbody tr').removeClass('table-active');
        $(this).addClass('table-active');

        $('#idEdicaoInstrutor').val(id);
        $('#nomeEdicaoInstrutor').val(nome);
        $('#cpfEdicaoInstrutor').val(cpf);
        $('#telefoneEdicaoInstrutor').val(telefone);
        $('#emailEdicaoInstrutor').val(email);
        $('#especialidadeEdicaoInstrutor').val(especialidadeId);

        $('#btnEditarInstrutor, #btnRemoverInstrutor').prop('disabled', false);

        console.log("ID:", id, "Nome:", nome, "CPF:", cpf, "Telefone:", telefone, "Email:", email, "Especialidade:", especialidadeId);
    });

    $('#btnRemoverInstrutor').on('click', function () {
        var id = $('#idEdicaoInstrutor').val();

        $.ajax({
            url: '/instrutor/remover/' + id,
            method: 'DELETE',
            success: function (response) {
                alert(response);
                $('tr[data-id="' + id + '"]').remove();
                $('#btnEditarInstrutor').prop('disabled', true);
                $('#btnRemoverInstrutor').prop('disabled', true);
            },
            error: function (xhr, status, error) {
                alert('Erro ao remover instrutor!');
            }
        });
    });

    $('#btnCadastrarInstrutor').on('click', function () {
        event.preventDefault();

        var nome = $('#nomeCadastroInstrutor').val();
        var cpf = $('#cpfCadastroInstrutor').val();
        var telefone = $('#telefoneCadastroInstrutor').val();
        var email = $('#emailCadastroInstrutor').val();
        var especialidadeId = $('#especialidadeCadastroInstrutor').val();
        var especialidadeNome = $('#especialidadeCadastroInstrutor option:selected').text();

        if (nome === "" || cpf === "" || telefone === "" || email === "" || especialidadeId === "") {
            alert("Por favor, preencha todos os campos.");
            return false;
        }

        var cpfRegex = /\d{3}\.\d{3}\.\d{3}-\d{2}/;
        var telefoneRegex = /\(\d{2}\) \d{5}-\d{4}/;
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!cpfRegex.test(cpf)) {
            alert("CPF inválido.");
            return false;
        }

        if (!telefoneRegex.test(telefone)) {
            alert("Telefone inválido.");
            return false;
        }

        if (!emailRegex.test(email)) {
            alert("E-mail inválido.");
            return false;
        }

        $.ajax({
            url: '/instrutor/cadastrar',
            method: 'POST',
            data: {
                nome: nome,
                cpf: cpf,
                telefone: telefone,
                email: email,
                especialidadeId: especialidadeId
            },
            success: function (response) {
                alert('Instrutor cadastrado com sucesso!');
                $('#tabelaInstrutores tbody').append('<tr data-id="' + response.id + '" data-nome="' + nome + '" data-cpf="' + cpf + '" data-telefone="' + telefone + '" data-email="' + email + '" data-especialidade="' + especialidadeNome + '">\\n\
                    <td>' + response.id + '</td>\
                    <td>' + nome + '</td>\
                    <td>' + email + '</td>\
                    <td>' + cpf + '</td>\
                    <td>' + telefone + '</td>\
                    <td>' + especialidadeNome + '</td>\
                </tr>');
                $('#formCadastroInstrutor')[0].reset();
                $('#modalCadastroInstrutor').modal('hide');
            },
            error: function (xhr, status, error) {
                alert('Erro ao cadastrar instrutor!');
            }
        });
    });

    $('#btnEditarInstrutor').click(function () {
        $('#modalEdicaoInstrutor').modal('show');
    });

    $('#btnSalvarEdicaoInstrutor').on('click', function (e) {
        e.preventDefault();

        var id = $('#idEdicaoInstrutor').val();
        var nome = $('#nomeEdicaoInstrutor').val();
        var cpf = $('#cpfEdicaoInstrutor').val();
        var telefone = $('#telefoneEdicaoInstrutor').val();
        var email = $('#emailEdicaoInstrutor').val();
        var especialidadeId = $('#especialidadeEdicaoInstrutor').val();
        var especialidadeNome = $('#especialidadeEdicaoInstrutor option:selected').text();

        if (!nome || !cpf || !telefone || !email || !especialidadeId) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        var cpfRegex = /\d{3}\.\d{3}\.\d{3}-\d{2}/;
        var telefoneRegex = /\(\d{2}\) \d{5}-\d{4}/;
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!cpfRegex.test(cpf)) {
            alert("CPF inválido.");
            return false;
        }

        if (!telefoneRegex.test(telefone)) {
            alert("Telefone inválido.");
            return false;
        }

        if (!emailRegex.test(email)) {
            alert("E-mail inválido.");
            return false;
        }

        $.ajax({
            url: '/instrutor/editar/' + id,
            method: 'PUT',
            data: {nome, cpf, telefone, email, especialidadeId: especialidadeId},
            success: function () {
                alert('Instrutor editado com sucesso!');
                var linha = $('tr[data-id="' + id + '"]');
                linha.attr("data-nome", nome)
                        .attr("data-cpf", cpf)
                        .attr("data-telefone", telefone)
                        .attr("data-email", email)
                        .attr("data-especialidade-id", especialidadeId);

                linha.find('td').eq(1).text(nome);
                linha.find('td').eq(2).text(email);
                linha.find('td').eq(3).text(cpf);
                linha.find('td').eq(4).text(telefone);
                linha.find('td').eq(5).text(especialidadeNome);

                $('#modalEdicaoInstrutor').modal('hide');
            },
            error: function () {
                alert('Erro ao editar instrutor!');
            }
        });
    });

    $('#cpfCadastroInstrutor, #cpfEdicaoInstrutor').on('input', function () {
        var cpf = $(this).val().replace(/\D/g, '');
        if (cpf.length <= 11) {
            cpf = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
        }
        $(this).val(cpf);
    });

    $('#telefoneCadastroInstrutor, #telefoneEdicaoInstrutor').on('input', function () {
        var telefone = $(this).val().replace(/\D/g, '');
        if (telefone.length <= 11) {
            telefone = telefone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
        }
        $(this).val(telefone);
    });

    //AULAS
    $('#tabelaAulas tbody').on('click', 'tr', function () {
        var id = $(this).data('id');
        var nome = $(this).data('nome');
        var descricao = $(this).data('descricao');
        var duracao = $(this).data('duracao');
        var instrutorId = $(this).data('instrutor-id');

        $('#tabelaAulas tbody tr').removeClass('table-active');
        $(this).addClass('table-active');

        $('#idEdicaoAula').val(id);
        $('#nomeEdicaoAula').val(nome);
        $('#descricaoEdicaoAula').val(descricao);
        $('#duracaoEdicaoAula').val(duracao);
        $('#instrutorEdicaoAula').val(instrutorId);

        $('#btnEditarAula, #btnRemoverAula').prop('disabled', false);

        console.log("ID:", id, "Nome:", nome, "Descrição:", descricao, "Duração:", duracao, "Instrutor:", instrutorId);
    });

    $('#btnRemoverAula').on('click', function () {
        var id = $('#idEdicaoAula').val();

        $.ajax({
            url: '/aula/remover/' + id,
            method: 'DELETE',
            success: function (response) {
                alert(response);
                $('tr[data-id="' + id + '"]').remove();
                $('#btnEditarAula').prop('disabled', true);
                $('#btnRemoverAula').prop('disabled', true);
            },
            error: function (xhr, status, error) {
                alert('Erro ao remover aula!');
            }
        });
    });

    $('#btnCadastrarAula').on('click', function () {
        event.preventDefault();

        var nome = $('#nomeCadastroAula').val();
        var descricao = $('#descricaoCadastroAula').val();
        var duracao = $('#duracaoCadastroAula').val();
        var instrutorId = $('#instrutorCadastroAula').val();
        var instrutorNome = $('#instrutorCadastroAula option:selected').text();

        if (nome === "" || duracao === "" || descricao === "" || instrutorId === "") {
            alert("Por favor, preencha todos os campos.");
            return false;
        }

        if (!/^\d{2}:\d{2}:\d{2}$/.test(duracao)) {
            alert("Por favor, digite a duração no formato hh:mm:ss.");
            return false;
        }

        $.ajax({
            url: '/aula/cadastrar',
            method: 'POST',
            data: {
                nome: nome,
                descricao: descricao,
                duracao: duracao,
                instrutorId: instrutorId
            },
            success: function (response) {
                alert('Aula cadastrada com sucesso!');
                $('#tabelaAulas tbody').append('<tr data-id="' + response.id + '" data-nome="' + nome + '" data-descricao="' + descricao + '" data-duracao="' + duracao + '" data-instrutor-nome="' + instrutorNome + '">\
                <td>' + response.id + '</td>\
                <td>' + nome + '</td>\
                <td>' + descricao + '</td>\
                <td>' + duracao + '</td>\
                <td>' + instrutorNome + '</td>\
            </tr>');
                $('#formCadastroAula')[0].reset();
                $('#modalCadastroAula').modal('hide');
            },
            error: function (xhr, status, error) {
                alert('Erro ao cadastrar aula!');
            }
        });
    });

    $('#btnEditarAula').click(function () {
        $('#modalEdicaoAula').modal('show');
    });

    $('#btnSalvarEdicaoAula').on('click', function (e) {
        e.preventDefault();

        var id = $('#idEdicaoAula').val();
        var nome = $('#nomeEdicaoAula').val();
        var descricao = $('#descricaoEdicaoAula').val();
        var duracao = $('#duracaoEdicaoAula').val();
        var instrutorId = $('#instrutorEdicaoAula').val();
        var instrutorNome = $('#instrutorEdicaoAula option:selected').text();

        if (!nome || !duracao || !descricao || !instrutorId) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        if (!/^\d{2}:\d{2}:\d{2}$/.test(duracao)) {
            alert("Por favor, digite a duração no formato hh:mm:ss.");
            return false;
        }

        $.ajax({
            url: '/aula/editar/' + id,
            method: 'PUT',
            data: {nome, duracao, descricao, instrutorId},
            success: function () {
                alert('Aula editada com sucesso!');
                var linha = $('tr[data-id="' + id + '"]');
                linha.attr("data-nome", nome)
                        .attr("data-descricao", descricao)
                        .attr("data-duracao", duracao)
                        .attr("data-instrutor-nome", instrutorNome);

                linha.find('td').eq(1).text(nome);
                linha.find('td').eq(2).text(descricao);
                linha.find('td').eq(3).text(duracao);
                linha.find('td').eq(4).text(instrutorNome);

                $('#modalEdicaoAula').modal('hide');
            },
            error: function () {
                alert('Erro ao editar aula!');
            }
        });
    });

    $('#duracaoCadastroAula, #duracaoEdicaoAula').on('input', function () {
        var duracao = $(this).val().replace(/\D/g, '');

        if (duracao.length <= 2) {
            duracao = duracao.replace(/(\d{2})/, '$1');
        } else if (duracao.length <= 4) {
            duracao = duracao.replace(/(\d{2})(\d{2})/, '$1:$2');
        } else if (duracao.length <= 6) {
            duracao = duracao.replace(/(\d{2})(\d{2})(\d{2})/, '$1:$2:$3');
        }

        $(this).val(duracao);

        if (duracao.length === 5 || duracao.length === 8) {
            $('#btnCadastrarAula, #btnSalvarEdicaoAula').prop('disabled', false);
        } else {
            $('#btnCadastrarAula, #btnSalvarEdicaoAula').prop('disabled', true);
        }
    });

    //AGENDAMENTOS
    let agendamentoIdSelecionado = null;

    $("#tabelaAgendamentos").on("click", "tr", function () {
        $("#tabelaAgendamentos tr").removeClass("selecionado");
        $(this).addClass("selecionado");

        $('#tabelaAgendamentos tbody tr').removeClass('table-active');
        $(this).addClass('table-active');

        agendamentoIdSelecionado = $(this).find("td").first().text();

        $('#btnEditarAgendamento, #btnRemoverAgendamento').prop("disabled", false);
    });

    $("#btnEditarAgendamento").click(function () {
        if (agendamentoIdSelecionado === null) {
            alert("Por favor, selecione um agendamento para editar.");
            return;
        }

        $.ajax({
            type: "GET",
            url: "/agendamentos/" + agendamentoIdSelecionado,
            success: function (agendamento) {
                $("#idEdicaoAgendamento").val(agendamento.id);
                $("#aulaEdicaoAgendamento").val(agendamento.aula.id);
                $("#inicioEdicaoAgendamento").val(agendamento.dataHoraInicio);
                $("#fimEdicaoAgendamento").val(agendamento.dataHoraFim);
                $("#obsEdicaoAgendamento").val(agendamento.observacao);
                $("#statusEdicaoAgendamento").val(agendamento.status);

                $("#modalEdicaoAgendamento").modal("show");
            },
            error: function () {
                alert("Erro ao carregar agendamento para edição!");
            }
        });
    });

    $("#btnSalvarEdicaoAgendamento").click(function (event) {
        event.preventDefault();

        var agendamentoId = $("#idEdicaoAgendamento").val();
        var aulaId = $("#aulaEdicaoAgendamento").val();
        var inicio = $("#inicioEdicaoAgendamento").val();
        var fim = $("#fimEdicaoAgendamento").val();
        var observacao = $("#obsEdicaoAgendamento").val();
        var status = $("#statusEdicaoAgendamento").val();

        if (!aulaId || !inicio || !fim || !status) {
            alert("Todos os campos são obrigatórios!");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/agendamentos/editar",
            data: {
                id: agendamentoId,
                aulaId: aulaId,
                inicio: inicio,
                fim: fim,
                observacao: observacao,
                status: status
            },
            success: function (response) {
                $("#modalEdicaoAgendamento").modal('hide');

                alert("Agendamento atualizado com sucesso!");
                location.reload();
            },
            error: function () {
                alert("Erro ao editar agendamento.");
            }
        });
    });

    $("#btnCadastrarAgendamento").click(function (event) {
        event.preventDefault();

        var aulaId = $("#aulaCadastroAgendamento").val();
        var inicio = $("#inicioCadastroAgendamento").val();
        var fim = $("#fimCadastroAgendamento").val();
        var observacao = $("#obsCadastroAgendamento").val();
        var status = $("#statusCadastroAgendamento").val();

        if (!aulaId || !inicio || !fim || !status) {
            alert("Todos os campos são obrigatórios!");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/agendamentos/cadastrar",
            data: {
                aulaId: aulaId,
                inicio: inicio,
                fim: fim,
                observacao: observacao,
                status: status
            },
            success: function (response) {
                $("#modalCadastroAgendamento").modal('hide');
                $("#formCadastroAgendamento")[0].reset();

                alert("Agendamento cadastrado com sucesso!");
                location.reload();
            },
            error: function () {
                alert("Ocorreu um erro ao cadastrar o agendamento.");
            }
        });
    });

    $("#formCadastroAgendamento").submit(function (event) {
        event.preventDefault();

        let formData = {
            inicio: $("#inicioCadastroAgendamento").val(),
            fim: $("#fimCadastroAgendamento").val(),
            aulaId: $("#aulaCadastroAgendamento").val(),
            observacao: $("#obsCadastroAgendamento").val(),
            status: $("#statusCadastroAgendamento").val()
        };

        $.ajax({
            url: "/agendamentos/cadastrar",
            method: "POST",
            data: formData,
            success: function () {
                alert("Agendamento cadastrado com sucesso!");
                location.reload();
            },
            error: function () {
                alert("Erro ao cadastrar agendamento!");
            }
        });
    });

    $("#btnRemoverAgendamento").click(function () {
        if (agendamentoIdSelecionado !== null) {
            $.ajax({
                url: "/agendamentos/remover/" + agendamentoIdSelecionado,
                method: "GET",
                success: function () {
                    alert("Agendamento removido com sucesso!");
                    location.reload();
                },
                error: function () {
                    alert("Erro ao remover agendamento!");
                }
            });
        }
    });

    // CARGOS
    $('#tabelaCargos').on('click', '.clickable-row', function () {
        var id = $(this).data('id');
        var nome = $(this).data('nome');
        var salario = $(this).data('salario');
        var comissao = $(this).data('comissao');

        $('#tabelaCargos tbody tr').removeClass('table-active');
        $(this).addClass('table-active');

        $('#btnEditarCargo').prop('disabled', false);
        $('#btnRemoverCargo').prop('disabled', false);

        $('#idEdicaoCargo').val(id);
        $('#nomeEdicaoCargo').val(nome);
        $('#salarioEdicaoCargo').val(formatarSalario(salario));
        $('#comissaoEdicaoCargo').val(formatarComissao(comissao));
    });

    $('#btnRemoverCargo').click(function () {
        var id = $('#idEdicaoCargo').val();
        $.ajax({
            url: '/cargo/remover/' + id,
            type: 'DELETE',
            success: function (response) {
                alert(response);
                location.reload();
            },
            error: function () {
                alert('Erro ao remover cargo.');
            }
        });
    });

    $('input[name="salario"], #salarioEdicaoCargo').on('input', function () {
        var valorFormatado = formatarSalario($(this).val());
        $(this).val(valorFormatado);
    });

    $('input[name="comissao"], #comissaoEdicaoCargo').on('input', function () {
        var valorFormatado = formatarComissao($(this).val());
        $(this).val(valorFormatado);
    });

    $('form').submit(function () {
        var campoSalario = $(this).find('input[name="salario"]');
        var campoComissao = $(this).find('input[name="comissao"]');

        if (campoSalario.val().trim() === "" || campoSalario.val().trim() === "R$ 0,00") {
            alert("Por favor, informe o salário corretamente.");
            campoSalario.focus();
            event.preventDefault();
            return;
        }

        campoSalario.val(removerFormatacaoSalario(campoSalario.val()));

        if (campoComissao.val().trim() === "") {
            campoComissao.val("0");
        } else {
            campoComissao.val(removerFormatacaoComissao(campoComissao.val()));
        }
    });

    function formatarSalario(valor) {
        valor = valor.replace(/\D/g, "");
        if (valor === "")
            return "";

        valor = (parseFloat(valor) / 100).toFixed(2);
        return "R$ " + valor.replace(".", ",").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    }

    function removerFormatacaoSalario(valor) {
        return valor.replace("R$", "").replace(/\./g, "").replace(",", ".").trim();
    }

    function formatarComissao(valor) {
        valor = valor.replace(/\D/g, "");
        if (valor === "")
            return "";

        return valor.slice(0, 2) + "%";
    }

    function removerFormatacaoComissao(valor) {
        return valor.replace("%", "").trim();
    }

    //FUNCIONÁRIOS
    $('#tabelaFuncionarios tbody').on('click', 'tr', function () {
        var id = $(this).data('id');
        var nome = $(this).data('nome');
        var cpf = $(this).data('cpf');
        var cargoId = $(this).data('cargo-id');

        $('#tabelaFuncionarios tbody tr').removeClass('table-active');
        $(this).addClass('table-active');

        $('#idEdicaoFuncionario').val(id);
        $('#nomeEdicaoFuncionario').val(nome);
        $('#cpfEdicaoFuncionario').val(cpf);
        $('#cargoEdicaoFuncionario').val(cargoId);

        $('#btnEditarFuncionario, #btnRemoverFuncionario').prop('disabled', false);

        console.log("ID:", id, "Nome:", nome, "CPF:", cpf, "Cargo ID:", cargoId);
    });

    $('#btnRemoverFuncionario').on('click', function () {
        var id = $('#idEdicaoFuncionario').val();

        $.ajax({
            url: '/funcionario/remover/' + id,
            method: 'DELETE',
            success: function (response) {
                alert(response);
                $('tr[data-id="' + id + '"]').remove();
                $('#btnEditarFuncionario').prop('disabled', true);
                $('#btnRemoverFuncionario').prop('disabled', true);
            },
            error: function (xhr, status, error) {
                alert('Erro ao remover funcionário!');
            }
        });
    });

    $('#btnCadastrarFuncionario').on('click', function () {
        event.preventDefault();

        var nome = $('#nomeCadastroFuncionario').val();
        var cpf = $('#cpfCadastroFuncionario').val();
        var cargoId = $('#cargoCadastroFuncionario').val();
        var cargoNome = $('#cargoCadastroFuncionario option:selected').text();

        if (nome === "" || cpf === "" || cargoId === "") {
            alert("Por favor, preencha todos os campos.");
            return false;
        }

        var cpfFuncionarioRegex = /\d{3}\.\d{3}\.\d{3}-\d{2}/;

        if (!cpfFuncionarioRegex.test(cpf)) {
            alert("CPF inválido.");
            return false;
        }

        $.ajax({
            url: '/funcionario/cadastrar',
            method: 'POST',
            data: {
                nome: nome,
                cpf: cpf,
                cargoId: cargoId
            },
            success: function (response) {
                alert('Funcionário cadastrado com sucesso!');
                $('#tabelaFuncionarios tbody').append('<tr data-id="' + response.id + '" data-nome="' + nome + '" data-cpf="' + cpf + '" data-cargo-id="' + cargoId + '">\\n\
                <td>' + response.id + '</td>\
                <td>' + nome + '</td>\
                <td>' + cpf + '</td>\
                <td>' + cargoNome + '</td>\
            </tr>');
                $('#formCadastroFuncionario')[0].reset();
                $('#modalCadastroFuncionario').modal('hide');
            },
            error: function (xhr, status, error) {
                alert('Erro ao cadastrar funcionário!');
            }
        });
    });

    $('#btnEditarFuncionario').click(function () {
        $('#modalEdicaoFuncionario').modal('show');
    });

    $('#btnSalvarEdicaoFuncionario').on('click', function (e) {
        e.preventDefault();

        var id = $('#idEdicaoFuncionario').val();
        var nome = $('#nomeEdicaoFuncionario').val();
        var cpf = $('#cpfEdicaoFuncionario').val();
        var cargoId = $('#cargoEdicaoFuncionario').val();
        var cargoNome = $('#cargoEdicaoFuncionario option:selected').text();

        if (!nome || !cpf || !cargoId) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        var cpfFuncionarioRegex = /\d{3}\.\d{3}\.\d{3}-\d{2}/;

        if (!cpfFuncionarioRegex.test(cpf)) {
            alert("CPF inválido.");
            return false;
        }

        $.ajax({
            url: '/funcionario/editar/' + id,
            method: 'PUT',
            data: {nome, cpf, cargoId},
            success: function () {
                alert('Funcionário editado com sucesso!');
                var linha = $('tr[data-id="' + id + '"]');
                linha.attr("data-nome", nome)
                        .attr("data-cpf", cpf)
                        .attr("data-cargo-id", cargoId);

                linha.find('td').eq(1).text(nome);
                linha.find('td').eq(2).text(cpf);
                linha.find('td').eq(3).text(cargoNome);

                $('#modalEdicaoFuncionario').modal('hide');
            },
            error: function () {
                alert('Erro ao editar funcionário!');
            }
        });
    });

    $('#cpfCadastroFuncionario, #cpfEdicaoFuncionario').on('input', function () {
        var cpf = $(this).val().replace(/\D/g, '');
        if (cpf.length <= 11) {
            cpf = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
        }
        $(this).val(cpf);
    });

    //PLANOS
    $('#tabelaPlanos').on('click', '.clickable-row', function () {
        var id = $(this).data('id');
        var nome = $(this).data('nome');
        var precoMensal = $(this).data('preco-mensal');
        var recursosIncluidos = $(this).data('recursos-incluidos');

        $('#tabelaPlanos tbody tr').removeClass('table-active');
        $(this).addClass('table-active');

        $('#btnEditarPlano').prop('disabled', false);
        $('#btnRemoverPlano').prop('disabled', false);

        $('#idEdicaoPlano').val(id);
        $('#nomeEdicaoPlano').val(nome);
        $('#precoMensalEdicaoPlano').val(formatarPrecoMensal(precoMensal));
        $('#recursosIncluidosEdicaoPlano').val(recursosIncluidos);
    });

    $('#btnRemoverPlano').click(function () {
        var id = $('#idEdicaoPlano').val();
        $.ajax({
            url: '/plano/remover/' + id,
            type: 'DELETE',
            success: function (response) {
                alert(response);
                location.reload();
            },
            error: function () {
                alert('Erro ao remover plano.');
            }
        });
    });

    $('input[name="precoMensal"], #precoMensalEdicaoPlano').on('input', function () {
        var precoMensalFormatado = formatarPrecoMensal($(this).val());
        $(this).val(precoMensalFormatado);
    });

    $('form').submit(function (event) {
        var campoPrecoMensal = $(this).find('input[name="precoMensal"]');

        if (campoPrecoMensal.val().trim() === "" || campoPrecoMensal.val().trim() === "R$ 0,00") {
            alert("Por favor, informe o preço mensal corretamente.");
            campoPrecoMensal.focus();
            event.preventDefault();
            return;
        }

        campoPrecoMensal.val(removerFormatacaoPrecoMensal(campoPrecoMensal.val()));
    });


    function formatarPrecoMensal(valor) {
        valor = valor.replace(/\D/g, "");
        if (valor === "")
            return "";

        valor = (parseFloat(valor) / 100).toFixed(2);
        return "R$ " + valor.replace(".", ",").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    }

    function removerFormatacaoPrecoMensal(valor) {
        return valor.replace("R$", "").replace(/\./g, "").replace(",", ".").trim();
    }
});