package com.example.pruebacoink

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.codec.binary.Base64
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val pruebas =
            Pruebas("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBcGlLZXkiOiI0NDEyNTIiLCJWZXJzaW9uIjoiMS4wLjAifQ.OHyAIQwymM8QKo0ETrP8QIpneMEvJncMdgb3YCBbPTY")
        pruebas.harden(
            "{\"document_number\":\"1030653649\",\"document_type\":\"1\",\"birth_date\":\"1995-04-29\",\"expedition_date\":\"2013-05-29\",\"is_Ordinary\":false}"
        )*/
    }

    private fun encrypt(key: String, unencryptedString: String?): String {
        if (unencryptedString == null)
            return ""
        try {
            val md = MessageDigest.getInstance("md5")
            val digestOfPassword = md.digest(key.toByteArray(charset("utf-8")))
            val keyBytes = Arrays.copyOf(digestOfPassword, 24)

            var j = 0
            var k = 16
            while (j < 8)
                keyBytes[k++] = keyBytes[j++]

            val secretKey = SecretKeySpec(keyBytes, "DESede")
            val cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val plainTextBytes = unencryptedString.toByteArray(charset("utf-8"))
            val buf = cipher.doFinal(plainTextBytes)
            return String(Base64.encodeBase64(buf))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}
