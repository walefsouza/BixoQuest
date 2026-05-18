import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ModelEntidadesTest.class,
        ModelAcademicoTest.class,
        ModelMapaTest.class,
        ModelAtividadesTest.class,
        AcademicoServiceTest.class,
        AtividadeServiceTest.class,
        LocalServiceTest.class,
        InteracaoServiceTest.class,
        JogadorServiceTest.class,
        TurnoServiceTest.class,
        GameServiceTest.class
})
public class AAllTest {
}