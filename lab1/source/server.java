import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(4080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String requestParamValue = null; 
            String response = "Hello World!\n";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
	    System.out.println("Served hello world...");
        }
    }
    
    private Map<String, String> getRequestHandler(HttpExchange httpExchange) {
    	return queryToMap(httpExchange.getRequestURI().toString());
    }
    
    private Map<String, String> queryToMap(String query){

    	Map<String, String> result = new HashMap<String, String>();

    	for (String param : query.split("&")) {
            String pair[] = param.split("=");

            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }

        return result;
    }

}
