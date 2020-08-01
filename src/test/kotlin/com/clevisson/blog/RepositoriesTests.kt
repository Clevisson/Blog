package com.clevisson.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests  @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository,
        val articleRepository: ArticleRepository) {
@Test
    fun `When findyByIdOrNull then return Article`() {
        val Clevisson = User(
                "clevisson123",
                "Clévisson",
                "Ribeiro da Cruz")

        entityManager.persist(Clevisson)
        val article = Article(
                "Aprendendo Spring Boot",
                "Escrevendo teste JPA...",
                "Aprendendo Spring Boot pela documentação do framework",
                Clevisson)
    entityManager.persist(article)
    entityManager.flush()
    val found = articleRepository.findByIdOrNull(article.id!!)
    assertThat(found).isEqualTo(article)
    }

    @Test
    fun `Wen findByLogin then return User`(){
        val Clevisson = User("clevisson123", "Clévisson", "Ribeiro da Cruz")
        entityManager.persist(Clevisson)
        entityManager.flush()
        val user = userRepository.findByLogin(Clevisson.login)
        assertThat(user).isEqualTo(Clevisson)
    }

}

