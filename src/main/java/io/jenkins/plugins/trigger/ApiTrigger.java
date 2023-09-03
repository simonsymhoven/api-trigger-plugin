package io.jenkins.plugins.trigger;

import com.fasterxml.jackson.databind.JsonNode;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.*;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import io.jenkins.cli.shaded.org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Map;
import jenkins.model.ParameterizedJobMixIn;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class ApiTrigger extends Trigger<Job<?, ?>> {

    @DataBoundConstructor
    public ApiTrigger() {}

    @Override
    public void start(Job<?, ?> project, boolean newInstance) {
        super.start(project, newInstance);
        AllTriggers.INSTANCE.add(this);
    }

    @Override
    public void stop() {
        AllTriggers.INSTANCE.remove(this);
        super.stop();
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    public void buildJob() {
        JsonNode tests = HttpRequest.getJsonResponse();

        for (JsonNode test : tests) {
            Map<String, String> params = new HashMap<>();
            params.put("User Id", test.get("userId").asText());
            params.put("Id", test.get("id").asText());
            params.put("Title", test.get("title").asText());
            params.put("Body", test.get("body").asText());

            ParameterizedJobMixIn.scheduleBuild2(
                    job, 0, new CauseAction(new ApiTriggerCause()), new ApiTriggerAction(params));
        }
    }

    @Extension
    @Symbol("api")
    public static class DescriptorImpl extends TriggerDescriptor {
        @Override
        public boolean isApplicable(Item item) {
            return false;
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return StringUtils.EMPTY;
        }
    }

    public static class ApiTriggerCause extends Cause {
        @Override
        public String getShortDescription() {
            return "Triggered due to changes in Testing Portal API";
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof ApiTriggerCause;
        }

        @Override
        public int hashCode() {
            return 5;
        }
    }
}
