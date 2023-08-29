package io.jenkins.plugins.trigger;

import hudson.Extension;
import hudson.model.AsyncPeriodicWork;
import hudson.model.TaskListener;
import org.jenkinsci.Symbol;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Extension
@Restricted(NoExternalUse.class)
@Symbol("api")
public class ApiPollTask extends AsyncPeriodicWork {

    public ApiPollTask() {
        super("api");
    }

    @Override
    protected void execute(TaskListener listener) {
        List<ApiTrigger> triggers = AllTriggers.INSTANCE.getAll();

        triggers.stream()
                .parallel()
                .forEach(ApiTrigger::buildJob);
    }

    @Override
    public long getRecurrencePeriod() {
        return TimeUnit.MINUTES.toMillis(1);
    }
}
