import java.io.Serializable;
import java.io.*;
import java.net.*;
public class Request implements Serializable {

    private Object[] args;
    private String method;

    public Request(String method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }
}
