package silveira.felipe.workunit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import silveira.felipe.workunit.work.union.Union;
import silveira.felipe.workunit.work.union.WorkerDispatcher;

@Service
@ComponentScan
@EnableAutoConfiguration
public class WorkerManagerImpl implements WorkerManager {

    /**
     * {@link WorkerDispatcher}.
     */
    @Autowired
    private Union union;

    @Override
    public Boolean requestWorkers(int workersNumber) {
        return union.requestWorkers(workersNumber);
    }
}
