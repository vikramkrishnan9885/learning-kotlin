package com.vk.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.vk.serverless.models.HandlerRequest
import com.vk.serverless.models.HandlerResponse


/**class MyLambdaHandler: RequestHandler<HandlerRequest, HandlerResponse>{

    override fun handleRequest(p0: HandlerRequest, p1: Context): HandlerResponse {
        //val inputObj = mapper.readValue<HandlerRequest>(p0)
        p1.getLogger().log("Hello "+ p0.name)
        return HandlerResponse("Hello "+ p0.name)
    }

}
*/
class MyLambdaHandler : RequestHandler<HandlerRequest, HandlerResponse> {
    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    override fun handleRequest(request: HandlerRequest,
                               context: Context): HandlerResponse {
        context.logger.log("Hello " + request.name)
        return HandlerResponse("Hello " + request.name)
    }
}

