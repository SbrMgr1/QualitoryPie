package helpers;


public class ApiHelper{

    public String node_connect_path;
    public String host;
    public String api_version;

    private ApiHelper() {

        String active = "local";
        if(active == "local"){
            node_connect_path = "http://192.168.254.7:3030";
            host = "http://192.168.254.7";
            api_version = "app";
        }else{
            node_connect_path = "http://192.168.254.7:3030";
            host = "http://192.168.254.7";
            api_version = "app";
        }
    }

    public static ApiHelper config(){
        return new ApiHelper();
    }
}
