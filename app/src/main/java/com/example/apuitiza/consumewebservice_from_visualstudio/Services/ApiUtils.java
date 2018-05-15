package com.example.apuitiza.consumewebservice_from_visualstudio.Services;

public class ApiUtils {
    private ApiUtils() {}

//    public static final String BASE_URL = "http://192.168.43.176:15021/Service1.svc/"; //red de alex
   // public static final String BASE_URL = "http://192.168.8.23:15021/Service1.svc/"; //red de lucky
    public static final String BASE_URL = "http://192.168.8.25:6592/Service1.svc/"; //Basic Authentication


        public static ResultadoService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ResultadoService.class);
    }
}
