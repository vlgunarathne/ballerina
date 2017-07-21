import ballerina.net.http;

@http:configuration {basePath:"/echo"}
service<http> echo {
    
    @http:POST{}
    @http:Path {value:"/"}
    resource echo (message m) {
        http:convertToResponse(m);
        reply m;
    
    }
    
}
