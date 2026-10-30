package com.example

import com.example.util.SecurityHelper
import com.example.util.TextPaginator
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testSecurityHelperHashing() {
        val salt = SecurityHelper.generateSalt()
        assertNotNull(salt)
        assertTrue(salt.isNotEmpty())

        val password = "SecretPassword123"
        val hash = SecurityHelper.hashPassword(password, salt)
        assertNotNull(hash)
        assertEquals(64, hash.length) // SHA-256 produces 64 hex chars

        assertTrue(SecurityHelper.verifyPassword(password, salt, hash))
        assertFalse(SecurityHelper.verifyPassword("WrongPassword", salt, hash))
    }

    @Test
    fun testTextPaginatorEmpty() {
        val pages = TextPaginator.paginate("")
        assertEquals(1, pages.size)
        assertTrue(pages[0].isFirstPage)
    }

    @Test
    fun testTextPaginatorMultiPage() {
        // Create large content that spans multiple notebook pages
        val paragraph1 = "Paragraf pertama materi perkuliahan algoritma pemodelan. ".repeat(20)
        val paragraph2 = "Paragraf kedua membahas tentang pengujian empiris dan hasil riset. ".repeat(20)
        val paragraph3 = "Paragraf ketiga evaluasi studi kasus industri. ".repeat(20)

        val fullContent = "$paragraph1\n\n$paragraph2\n\n$paragraph3"
        val pages = TextPaginator.paginate(fullContent, firstPageCharLimit = 500, regularPageCharLimit = 600)

        assertTrue(pages.size > 1)
        assertEquals(0, pages[0].pageIndex)
        assertTrue(pages[0].isFirstPage)
        assertFalse(pages[1].isFirstPage)
        assertEquals(pages.size, pages[0].totalPages)
    }
}
