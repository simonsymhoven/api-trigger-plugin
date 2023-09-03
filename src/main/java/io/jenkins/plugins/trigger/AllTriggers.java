package io.jenkins.plugins.trigger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AllTriggers {

    public static final AllTriggers INSTANCE = new AllTriggers();
    private final List<ApiTrigger> triggerList = new CopyOnWriteArrayList<>();

    private AllTriggers() {}

    public void add(ApiTrigger trigger) {
        triggerList.add(trigger);
    }

    public void remove(ApiTrigger trigger) {
        triggerList.remove(trigger);
    }

    public List<ApiTrigger> getAll() {
        return triggerList;
    }
}
