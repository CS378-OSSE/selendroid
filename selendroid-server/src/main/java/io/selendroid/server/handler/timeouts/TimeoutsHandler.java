package io.selendroid.server.handler.timeouts;

import io.selendroid.server.ServerInstrumentation;
import io.selendroid.server.common.Response;
import io.selendroid.server.common.SelendroidResponse;
import io.selendroid.server.common.StatusCode;
import io.selendroid.server.common.http.HttpRequest;
import io.selendroid.server.handler.SafeRequestHandler;
import org.json.JSONException;

public class TimeoutsHandler extends SafeRequestHandler {
  public TimeoutsHandler(String uri) {
    super(uri);
  }
  public Response safeHandle(HttpRequest request) throws JSONException {
    long timeout = getPayload(request).getLong("ms");
    String type = getPayload(request).getString("type");
    if (type.equals("script")) {
      getSelendroidDriver(request).setAsyncTimeout(timeout);
    } else if (type.equals("implicit")) {
        ServerInstrumentation.getInstance().setImplicitWait(timeout);
    } else if (type.equals("page load")) {
        selendroidWebDriver.setPageLoadTimeout(timeout);
    } else {
      return new SelendroidResponse(getSessionId(request),
          StatusCode.UNKNOWN_COMMAND,
          new Exception("Unsupported timeout type: " + type));
    }
    return new SelendroidResponse(getSessionId(request), null);
  }
}
