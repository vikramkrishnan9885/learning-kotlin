provider "aws" {
  region                  = "us-east-1"
  shared_credentials_file = "/Users/vikram.krishnan/.aws/credentials"
  profile                 = "personal"
}

resource "aws_api_gateway_rest_api" "MyDemoAPI" {
  name = "Greeting-API"
  description = "This is my API for demonstration purposes"

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

resource "aws_api_gateway_resource" "MyDemoResource" {
  rest_api_id = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  parent_id   = "${aws_api_gateway_rest_api.MyDemoAPI.root_resource_id}"
  path_part   = "greeting"
}

resource "aws_api_gateway_resource" "MyDemoResource2" {
  rest_api_id = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  parent_id   = "${aws_api_gateway_resource.MyDemoResource.id}"
  path_part   = "{name}"
}

resource "aws_api_gateway_method" "MyDemoMethod" {
  rest_api_id   = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  resource_id   = "${aws_api_gateway_resource.MyDemoResource2.id}"
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_method_response" "response_200" {
  rest_api_id = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  resource_id = "${aws_api_gateway_resource.MyDemoResource2.id}"
  http_method = "${aws_api_gateway_method.MyDemoMethod.http_method}"
  status_code = "200"
}

resource "aws_api_gateway_integration" "MyDemoIntegration" {
  rest_api_id          = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  resource_id          = "${aws_api_gateway_resource.MyDemoResource2.id}"
  http_method          = "${aws_api_gateway_method.MyDemoMethod.http_method}"
  type                 = "MOCK"
  #cache_key_parameters = ["method.request.path.param"]
  cache_namespace      = "foobar"
  timeout_milliseconds = 29000

  #request_parameters = {
  #  "integration.request.header.X-Authorization" = "'static'"
  #}

  # Transforms the incoming XML request to JSON
  request_templates = {
    "application/json" = <<EOF
{
   "status_code" : 200
}
EOF
  }
}

# https://github.com/terraform-providers/terraform-provider-aws/issues/6790
resource "aws_api_gateway_integration_response" "MyDemoIntegrationResponse" {
  depends_on = ["aws_api_gateway_integration.MyDemoIntegration"]
  
  rest_api_id = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  resource_id = "${aws_api_gateway_resource.MyDemoResource2.id}"
  http_method = "${aws_api_gateway_method.MyDemoMethod.http_method}"
  status_code = "${aws_api_gateway_method_response.response_200.status_code}"

  # Transforms the backend JSON response to XML
  response_templates = {
    "application/json" = <<EOF
"{\"message\": \"Hello $input.params('name')\" }"
EOF
  }
}

resource "aws_api_gateway_deployment" "MyDemoDeployment" {
  depends_on = ["aws_api_gateway_integration.MyDemoIntegration"]

  rest_api_id = "${aws_api_gateway_rest_api.MyDemoAPI.id}"
  stage_name  = "test"

  //variables = {
  //  "name" = "Vikram"
  //}
}

output "url" {
  value = "${aws_api_gateway_deployment.MyDemoDeployment.invoke_url}"
}