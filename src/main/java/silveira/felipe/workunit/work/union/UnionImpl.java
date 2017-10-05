package silveira.felipe.workunit.work.union;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * This class contains the implementation of {@link Union} methods.
 */
@Component
public class UnionImpl implements Union {

    /**
     * Logger object that is used to log messages for a specific application component.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UnionImpl.class);

    /**
     * A WorkerDispatcher object to allocate workers.
     */
    private final WorkerDispatcher workerDispatcher;


    /**
     * UnionImpl constructor.
     */
    public UnionImpl() {
        this.workerDispatcher = WorkerDispatcher.getInstance();
    }


    @Override
    public Boolean requestWorkers(@Nonnull int workersNumber) {
        return workerDispatcher.assignWork(workersNumber);
    }
}
