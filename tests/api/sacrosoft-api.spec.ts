import { expect, test } from '@playwright/test';

let usuarioLogin: string;
let usuarioSenha: string;

test.describe.serial('SacroSoft API', () => {
  test('Fluxo 1: configuracao inicial', async ({ request }) => {
    const statusResponse = await request.get('/api/configuracao/status');
    expect(statusResponse.ok()).toBeTruthy();

    const configurado = await statusResponse.json();

    if (configurado) {
      const getResponse = await request.get('/api/configuracao');
      expect(getResponse.ok()).toBeTruthy();

      const configuracao = await getResponse.json();
      expect(configuracao).toHaveProperty('nomeParoquia');
      expect(configuracao).toHaveProperty('nomeDiocese');
      return;
    }

    const createResponse = await request.post('/api/configuracao', {
      data: {
        nomeParoquia: 'Paroquia Sao Jose',
        nomeDiocese: 'Diocese Teste',
      },
    });

    expect([200, 201]).toContain(createResponse.status());

    const configuracao = await createResponse.json();
    expect(configuracao.nomeParoquia).toBe('Paroquia Sao Jose');
    expect(configuracao.nomeDiocese).toBe('Diocese Teste');
    expect(configuracao.configurado).toBe(true);
  });

  test('Fluxo 2: criacao de usuario', async ({ request }) => {
    const response = await request.post('/api/usuarios', {
      data: {
        nome: 'Secretaria Teste',
      },
    });

    expect(response.status()).toBe(201);

    const usuario = await response.json();
    expect(usuario.nome).toBe('Secretaria Teste');
    expect(usuario.login).toBeTruthy();
    expect(usuario.senhaGerada).toBeTruthy();
    expect(usuario.perfil).toBe('SECRETARIO');
    expect(usuario.paroquia).toBeTruthy();

    usuarioLogin = usuario.login;
    usuarioSenha = usuario.senhaGerada;
  });

  test('Fluxo 3: login', async ({ request }) => {
    const response = await request.post('/api/auth/login', {
      data: {
        login: usuarioLogin,
        senha: usuarioSenha,
      },
    });

    expect(response.status()).toBe(200);

    const login = await response.json();
    expect(login.nome).toBe('Secretaria Teste');
    expect(login.login).toBe(usuarioLogin);
    expect(login.perfil).toBe('SECRETARIO');
  });

  test('Fluxo 4: livro', async ({ request }) => {
    const numeroLivro = Math.floor(Math.random() * 100000);

    const createResponse = await request.post('/api/livros', {
      data: {
        tipoSacramento: 'BATISMO',
        numero: numeroLivro,
        anoInicio: 2026,
        anoFim: 2026,
        celebranteResponsavel: 'Padre Teste',
      },
    });

    expect(createResponse.status()).toBe(201);

    const livro = await createResponse.json();
    expect(livro.id).toBeTruthy();
    expect(livro.tipoSacramento).toBe('BATISMO');
    expect(livro.paroquia).toBeTruthy();

    const getResponse = await request.get(`/api/livros/${livro.id}`);
    expect(getResponse.status()).toBe(200);

    const updateResponse = await request.put(`/api/livros/${livro.id}`, {
      data: {
        tipoSacramento: 'BATISMO',
        numero: livro.numero,
        anoInicio: 2026,
        anoFim: 2027,
        celebranteResponsavel: 'Padre Teste Atualizado',
      },
    });
    expect(updateResponse.status()).toBe(200);

    const deleteResponse = await request.delete(`/api/livros/${livro.id}`);
    expect(deleteResponse.status()).toBe(204);
  });

  test('Fluxo 5: pessoa', async ({ request }) => {
    const createResponse = await request.post('/api/pessoas', {
      data: {
        nome: 'Pessoa Teste',
        dataNascimento: '2000-01-01T00:00:00',
        nomePai: 'Pai Teste',
        nomeMae: 'Mae Teste',
        documentos: {
          cpf: 12345678901,
          rg: 1234567,
        },
        endereco: {
          rua: 'Rua Teste',
          bairro: 'Centro',
          cidade: 'Cidade Teste',
          estado: 'SP',
          cep: '01000-000',
          numero: 100,
        },
        contato: {
          telefone: '11999999999',
          email: 'teste@sacrosoft.local',
        },
      },
    });

    expect(createResponse.status()).toBe(201);

    const pessoa = await createResponse.json();
    expect(pessoa.id).toBeTruthy();

    const getResponse = await request.get(`/api/pessoas/${pessoa.id}`);
    expect(getResponse.status()).toBe(200);

    const updateResponse = await request.put(`/api/pessoas/${pessoa.id}`, {
      data: {
        ...pessoa,
        nome: 'Pessoa Teste Atualizada',
      },
    });
    expect(updateResponse.status()).toBe(200);

    const deleteResponse = await request.delete(`/api/pessoas/${pessoa.id}`);
    expect(deleteResponse.status()).toBe(204);
  });

  test('Fluxo 6: regra de precedencia sacramental', async ({ request }) => {
    const response = await request.post('/api/pessoas', {
      data: {
        nome: 'Pessoa Sem Batismo',
        dataNascimento: '2001-01-01T00:00:00',
        eucaristia: {
          dataCelebracao: '2026-01-01T10:00:00',
          celebrante: 'Padre Teste',
          localCelebracao: 'Paroquia Teste',
          livroId: 'livro-teste',
          folha: 1,
          numeroRegistro: 1,
          catequista: 'Catequista Teste',
        },
      },
    });

    expect(response.status()).toBe(400);
    const mensagemErro = await response.text();
    expect(mensagemErro).toContain('batizada');
  });
});
