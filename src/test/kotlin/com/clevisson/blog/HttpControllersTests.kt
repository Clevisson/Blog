package com.clevisson.blog
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {
    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val Clevisson = User("clevisson123", "Clévisson", "Ribeiro da Cruz")
        val kotlinLanguage = Article("Linguagem Kotlin com Spring Boot", "O Spring suporta o kotlin...",
                "Kotlin com spring é a combinação perfeita pra quem deseja desenvolver para web com a segunraça" +
                        "para nulos e toda sintaxe consisa que o koltin oferece ", Clevisson)
        val springFramework = Article("Spring Framework", "O Spring  é bom framework...", "Suporta o Java e Kotlin", Clevisson)
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(kotlinLanguage, springFramework)

        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].author.login").value(Clevisson.login))
                .andExpect(jsonPath("\$.[0].slug").value(kotlinLanguage.slug))
                .andExpect(jsonPath("\$.[1].author.login").value(Clevisson.login))
                .andExpect(jsonPath("\$.[1].slug").value(springFramework.slug))
    }
        @Test
        fun `List users`() {
            val Clevisson = User("clevisson123", "Clevisson", "Ribeiro da Cruz")
            val Maria = User("maria123", "Maria", "da Silva")
            every { userRepository.findAll() } returns listOf(Clevisson, Maria)
            mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("\$.[0].login").value(Clevisson.login))
                    .andExpect(jsonPath("\$.[1].login").value(Maria.login))
        }
}