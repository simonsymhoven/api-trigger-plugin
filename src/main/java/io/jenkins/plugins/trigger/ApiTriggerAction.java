package io.jenkins.plugins.trigger;

import hudson.model.Action;
import java.util.Collections;
import java.util.Map;

public class ApiTriggerAction implements Action {

    private final Map<String, String> params;

    public ApiTriggerAction(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "ApiTrigger";
    }

    @Override
    public String getUrlName() {
        return "api-trigger";
    }

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(this.params);
    }
}
