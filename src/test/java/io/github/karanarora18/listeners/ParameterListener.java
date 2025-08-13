package io.github.karanarora18.listeners;

import io.github.karanarora18.frameworkconfig.TestConfig;
import org.testng.*;

import java.util.Map;

public class ParameterListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestContext context = testResult.getTestContext();
        Map<String, String> params = context.getCurrentXmlTest().getAllParameters();
        params.forEach(TestConfig::set);

    }
}
