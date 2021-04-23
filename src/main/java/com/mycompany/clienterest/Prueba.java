/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clienterest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 *
 * @author smarv
 */
public class Prueba {

    public static void main(String[] args) {

        //Esta variable res la usaremos únicamente para dar un respuesta final
        String res = "";
        String URL = "http://localhost:8080/ServicioRest/app/";

        try {
            //Creamos el cliente de conexión al API Restful
            Client client = ClientBuilder.newClient();

            //Creamos el target lo cuál es nuestra URL junto con el nombre del método a llamar
            WebTarget target = client.target(URL + "datosPOSTJSON");

            //Creamos nuestra solicitud que realizará el request
            Invocation.Builder solicitud = target.request();
            System.out.println("target.request(): " + target.request());

            //Creamos y llenamos nuestro objeto BaseReq con los datos que solicita el API
            ParametrosVO parametrosVO = new ParametrosVO();
            parametrosVO.setNombre("Mario");
            parametrosVO.setApellido("Ramirez");
            parametrosVO.setEdad(33);
            
            //Convertimos el objeto req a un json
            Gson gson = new Gson();
            String jsonString = gson.toJson(parametrosVO);
            System.out.println("jsonString: " + jsonString);

            //Enviamos nuestro json vía post al API Restful
            Response post = solicitud.post(Entity.json(jsonString));
            
            //Recibimos la respuesta y la leemos en una clase de tipo String, en caso de que el json sea tipo json y no string,
            //debemos usar la clase de tipo JsonObject.class en lugar de String.class
            String responseJson = post.readEntity(String.class);
            res = responseJson;

            //Imprimimos el status de la solicitud
            System.out.println("Estatus: " + post.getStatus());

            switch (post.getStatus()) {
                case 200:
                    res = responseJson;
                    break;
                default:
                    res = "Error";
                    break;
            }

        } catch (Exception e) {
            //En caso de un error en la solicitud, llenaremos res con la exceptión para verificar que sucedió
            res = e.toString();
        }
        //Imprimimos la respuesta del API Restful
        System.out.println("res: " + res);
    }

}


