![Image](sistema-de-gestão-escolar.png "Sistema de Gestão Escolar")

# School System
Esse sistema foi desenvolvido na intenção de implementar alguns tópicos mais profundos do Spring

# Regras de negócio
- uma classe só pode ter um professor
- uma classe pode ter no máximo 30 alunos
- um aluno pode ter no máximo 10 classes
- um professor pode ter quantas classes quiser
- um professor deve ser cadastrado antes de criar-se a disciplina
- o professor só pode ser designado à uma turma da disciplina que ele leciona

# Como rodar esse projeto?

Certifique-se de ter o Java e o Gradle instalados e configurados
- eu usei o java 21 e o gradle 8.9

Crie um banco de dados PostgreSQL
- eu criei um container com o postgres a partir do comando a seguir:
- docker run --name pg_school --env POSTGRES_PASSWORD=«sua-senha» --env POSTGRES_USER=«seu-usuario» --publish 5432:5432 --detach postgres

As rotas para teste já estão no arquivo requestRoutes.json, basta importá-lo no Insomnia (o Bruno também aceita esse formato)

# Próximos passos
Sigo agora para a implementação do Spring Security no escopo do projeto!
