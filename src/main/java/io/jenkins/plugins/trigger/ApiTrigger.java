package io.jenkins.plugins.trigger;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.*;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import jenkins.model.ParameterizedJobMixIn;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;


public class ApiTrigger extends Trigger<Job<?, ?>> {

    private String url;

    @DataBoundConstructor
    public ApiTrigger() {
        
    }
    
    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }
    
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
        ParameterizedJobMixIn.scheduleBuild2(job, 0,
                new CauseAction(new ApiTriggerCause()));
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
            return "Build when Items changed in Testing Portal";
        }
    }

    public static class ApiTriggerCause extends Cause {
        @Override
        public String getShortDescription() {
            return "Triggered by API call";
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