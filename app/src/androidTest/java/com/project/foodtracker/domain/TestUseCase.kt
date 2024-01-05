import com.project.foodtracker.data.database.entities.asProductDetailModel
import com.project.foodtracker.data.mock.MockProductEntityProvider
import com.project.foodtracker.domain.repository.IProductRepository
import com.project.foodtracker.domain.use_case.UpsertProductUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UpsertProductUseCaseTest {

    @Test
    fun testUpsertProductUseCase() = runBlocking {

        val product = MockProductEntityProvider.createMockProductEntity().asProductDetailModel()
        val mockRepository = mock(IProductRepository::class.java)

        val upsertProductUseCase = UpsertProductUseCase(mockRepository)

         `when`(mockRepository.updateProduct(product)).thenReturn(Unit)

        upsertProductUseCase(product)

        verify(mockRepository).updateProduct(product)
    }
}
