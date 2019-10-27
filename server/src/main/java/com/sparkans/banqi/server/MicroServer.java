package com.sparkans.banqi.server;

import com.sparkans.banqi.user.UserBean;
import com.sparkans.banqi.user.UserRegistration;
import com.sparkans.banqi.user.UserSignIn;

import spark.Request;
import spark.Response;
import spark.Spark;

import com.google.gson.*;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.sql.SQLException;

import static spark.Spark.options;

/** A simple micro-server for the web.  Just what we need, nothing more.
 *
 */
public class MicroServer {

  private int    port;
  private String name;
  private String path = "/public";

  /** Creates a micro-server to load static files and provide REST APIs.
   *
   * @param port
   * @param name
   */
 public MicroServer(int port, String name) {
    this.port = port;
    this.name = name;

    port(this.port);

    // serve the static files: index.html and bundle.js
    Spark.staticFileLocation(this.path);
    get("/", (req, res) -> {
      res.redirect("index.html");
      return null;
    });

    // register all micro-services and the function that services them.
    // start with HTTP GET
     //apply CorsFilter
     CorsFilter.apply();
    get("/about", this::about);
    get("/echo", this::echo);
    get("/hello/:name", this::hello);
    //for client sending data, HTTP POST is used instead of a GET
    post("/register", this::register);
    post("/signin", this::signin);
    post("/invite", this::invite);

     options("/*", (request,response)->{
         String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
         if (accessControlRequestHeaders != null) {
             response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
         }
         String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
         if(accessControlRequestMethod != null){
             response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
         }

         return "OK";
     });
    
    System.out.println("\n\nServer running on port: " + this.port + "\n\n");
  }

  /** A REST API that describes the server.
   *
   * @param request
   * @param response
   * @return about string
   */
  private String about(Request request, Response response) {

    response.type("text/html");

    return "<html><head></head><body><h1>"+name+" Micro-server on port "+port+"</h1></body></html>";
  }

  /** A REST API that echos the client request.
   *
   * @param request
   * @param response
   * @return string echo response
   */
  private String echo(Request request, Response response) {

    response.type("application/json");

    return HTTP.echoRequest(request);
  }

  /** A REST API demonstrating the use of a parameter.
   *
   * @param request
   * @param response
   * @return hello string
   */
  private String hello(Request request, Response response) {

    response.type("text/html");

    return Greeting.html(request.params(":name"));
  }

	private String register(Request request, Response response) {

		response.type("application/json");
		response.header("Access-Control-Allow-Headers", "*");
		// @TODO add DB connection
		Gson gson = new Gson();
		UserBean user = gson.fromJson(request.body(), UserBean.class);
		// user contains the user we want to register
		UserRegistration userRegistration = new UserRegistration();
		try {
			userRegistration.createUser(user);

		} catch (SQLException e) {
			e.printStackTrace();
			return "{\"registered\": \"false\"}";
		}
		return "{\"registered\": \"true\"}";

	}
 
 private String signin(Request request, Response response) {
	 
	 response.type("application/json");
     response.header("Access-Control-Allow-Headers", "*");
     //@TODO add DB connection
     Gson gson = new Gson();
     UserBean user = gson.fromJson(request.body(),UserBean.class);
     //user contains the user to sign in
     UserSignIn userSignin = new UserSignIn();
     try {
		userSignin.signInUser(user);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "{\"signedin\": \"false\"}";
	}
	 return "{\"signedin\": \"true\"}";
	 
 }
 
 private String invite(Request request, Response response) {
	 
	 response.type("application/json");
     response.header("Access-Control-Allow-Headers", "*");
     //@TODO add DB connection
     Gson gson = new Gson();
     UserBean user = gson.fromJson(request.body(),UserBean.class);
     // user contains the user to invite
	 return "{\"invite\": \"true\"}";
	 
 }
 
  private String team(Request request, Response response) {

    response.type("text/plain");

    return name;
  }

}
