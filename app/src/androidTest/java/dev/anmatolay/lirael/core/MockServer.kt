package dev.anmatolay.lirael.core

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber

class MockServer(private val mockEndpointList: List<MockEndpoint>) : Dispatcher() {

    private val server = MockWebServer()

    fun start() {
        Timber.d("MockServer STARTED")
        server.start(DEFAULT_PORT)
    }

    fun stop() {
        Timber.d("MockServer STOPPED")
        server.shutdown()
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val mock =
            mockEndpointList.find { it.identifier == request.identifier }
                ?: throw IllegalArgumentException("No matching endpoint!")

        Timber.d("Matching endpoint found with identifier: ${mock.identifier}")

        val response =
            MockResponse()
                .setResponseCode(mock.responseCode)

        if (mock.responseBody == null) {
            response.setHeader("Content-Length", 0)
        } else {
            response.apply {
                setHeader("Content-Type", "application/json")
                response.setBody(
                    if (mock.responseBody!!.jsonString?.isNotBlank() == true)
                        mock.responseBody!!.jsonString!!
                    else if (mock.responseBody!!.pathToJson?.isNotBlank() == true)
                        mock.responseBody!!.pathToJson!!.getJsonString()
                    else
                        throw illegalArgumentException
                )
            }
        }
        return response
    }

    enum class HttpMethod {
        GET,
        POST,
        PUT,
        DELETE,
    }

    data class MockEndpoint(
        val url: String,
        val method: HttpMethod = HttpMethod.GET,
        val responseCode: Int = 200,
    ) {
        val identifier = "$method $url"

        var responseBody: ResponseBody? = null
            set(value) {
                if (value != null && value.isResponseBodyInvalid())
                    throw illegalArgumentException
                field = value
            }

        data class ResponseBody(
            val pathToJson: String? = null,
            val jsonString: String? = null,
        )

        private fun ResponseBody.isResponseBodyInvalid() =
            this.jsonString.isNullOrBlank() && this.pathToJson.isNullOrBlank()
    }

    private fun String.getJsonString(): String =
        InstrumentationRegistry.getInstrumentation().context.assets.open(this)
            .bufferedReader()
            .readText()

    companion object {
        private const val DEFAULT_PORT = 0
    }
}

private val RecordedRequest.identifier: String
    get() {
        return "${this.method} ${this.path}"
    }

private val illegalArgumentException = IllegalArgumentException(
    "ResponseBody jsonString OR pathToJson properties should be not null, empty or blank!"
)
