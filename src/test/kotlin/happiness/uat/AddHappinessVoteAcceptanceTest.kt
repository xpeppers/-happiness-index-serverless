package happiness.uat

import happiness.BASE_URL
import happiness.infrastructure.*
import io.restassured.RestAssured.post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import software.amazon.awssdk.services.s3.S3Client

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddHappinessVoteAcceptanceTest {

    private val s3 by lazy { S3Client.create() }

    @BeforeEach
    fun setUp() {
        s3.createBucketIfNotExists(BUCKET_NAME)
        s3.emptyBucketKey(BUCKET_NAME, KEY_NAME)
    }

    @Test
    fun `http calls should append the vote to s3 bucket`() {
        post("$BASE_URL/happiness/1")
            .then()
            .statusCode(201)

        assertThat(votes()).containsExactly("1")

        post("$BASE_URL/happiness/2")
            .then()
            .statusCode(201)

        assertThat(votes()).containsExactly("1", "2")
    }

    private fun votes() = s3.readFromBucket(BUCKET_NAME, KEY_NAME)
}