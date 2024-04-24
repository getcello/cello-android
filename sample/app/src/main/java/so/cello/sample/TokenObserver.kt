package so.cello.sample

import com.cello.cello_sdk.Cello
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date


class TokenObserver(
    private val productId: String,
    private val productUserId: String
) {
    private val secretString = getSecretFromConfig()

    fun getToken(): String? {
        val nowMillis = System.currentTimeMillis()
        val now = Date(nowMillis)

        val jwt = Jwts.builder()
            .header()
                .add("alg", "HS512")
                .add("typ", "JWT")
            .and()
            .claim("productId", productId)
            .claim("productUserId", productUserId)
            .issuedAt(now)
            .signWith(SignatureAlgorithm.HS512, secretString.toByteArray())
            .compact()

        return jwt
    }

    private fun getSecretFromConfig(): String {
        return "secret"
    }

    private fun updateCelloToken() {
        getToken()?.let {
            Cello.client()?.updateToken(it)
        }
    }

    fun startObserving() {
        Cello.client()?.addTokenAboutToExpireListener {
            updateCelloToken()
        }

        Cello.client()?.addTokenExpiredListener() {
            updateCelloToken()
        }
    }
}