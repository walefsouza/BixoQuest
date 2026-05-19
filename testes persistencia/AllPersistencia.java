import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ArquivosFixosTest.class,
        GamePersistenciaTest.class,
        RepositoryGenericTest.class
})

public class AllPersistencia {
}
