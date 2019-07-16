package com.example.pruebacoink

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pruebacoink.Base.BaseActivity
import com.example.pruebacoink.Model.Payload
import com.example.pruebacoink.Model.User
import com.example.pruebacoink.Model.ValidateUser
import com.example.pruebacoink.Retrofit.requestService
import com.example.pruebacoink.Utils.Constants
import com.example.pruebacoink.Utils.setWarningsRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.codec.binary.Base64
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest

class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listTypeUser =
            arrayListOf<String>(getString(R.string.cardCitizenship))
        val spinnerAdapter = ArrayAdapter<String>(this, R.layout.view_spinner, listTypeUser)
        spinnerAdapter.setDropDownViewResource(R.layout.view_spinner)
        spDocumentType.adapter = spinnerAdapter
        spDocumentType.onItemSelectedListener = this

        /*val pruebas =
            Pruebas("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBcGlLZXkiOiI0NDEyNTIiLCJWZXJzaW9uIjoiMS4wLjAifQ.OHyAIQwymM8QKo0ETrP8QIpneMEvJncMdgb3YCBbPTY")
        pruebas.harden(
            "{\"document_number\":\"1030653649\",\"document_type\":\"1\",\"birth_date\":\"1995-04-29\",\"expedition_date\":\"2013-05-29\",\"is_Ordinary\":false}"
        )*/
    }

    private fun encrypt(unencryptedString: String?): String {
        if (unencryptedString == null)
            return ""
        try {
            val md = MessageDigest.getInstance("md5")
            val digestOfPassword = md.digest(getString(R.string.key).toByteArray(charset("utf-8")))
            val keyBytes = digestOfPassword.copyOf(24)

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

    override fun onClick(view: View?) {
        when (view) {
            btContinue -> {
                if (clRegister.setWarningsRequest())
                    return
                else {
                    showProgress()
                    val user = ValidateUser()
                    user.document_number = etDocumentNumber.text.toString()
                    user.birth_date = ("${etRectangle6.text}-${etRectangle5.text}-${etRectangle4.text}")
                    user.expedition_date = ("${etRectangle3.text}-${etRectangle2.text}-${etRectangle1.text}")
                    val jsonString = encrypt(user.toJsonString())
                    val payload = Payload()
                    payload.payload = jsonString
                    closeProgress()
                    requestService(payload, Constants.HttpMethodsType.POST, "cifin?apiKey=441252", true)
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onDataReturn(user: User?) {
        if (user?.first_name == null) {
            val toast = Toast.makeText(this, getString(R.string.userNotExist), Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        } else {
            val toast = Toast.makeText(
                this,
                "${getString(R.string.name)} ${user.first_name} ${user.second_name}\n" +
                        "${getString(R.string.lastName)} ${user.first_last_name} ${user.second_last_name}\n" +
                        "${getString(R.string.gender)} ${user.gender.Name} ",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    override fun onFailedResponse(message: String?) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}
