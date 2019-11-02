package com.sparkans.banqi.server;

import com.sparkans.banqi.game.BanqiBoard;
import com.sparkans.banqi.game.BanqiPiece;
import com.sparkans.banqi.game.GameManager;
import com.sparkans.banqi.user.*;

import spark.Request;
import spark.Response;
import spark.Spark;

import com.google.gson.*;

import java.util.ArrayList;

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

	private GameManager gameManager = new GameManager();
	private ArrayList<Invitation> invites = new ArrayList<>();

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
		get("/invite", this::invite);
		get("/sendInvite", this::sendInvite);
		get("/acceptInvite", this::acceptInvite);
		get("/waitingInvite", this::waitingInvite);
		get("/getGame", this::getGame);

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
        catch (Exception e){
            return "{\"registered\": \"false\"}";
        }
		return "{\"registered\": \"true\"}";

	}

	private String signin(Request request, Response response) {

		response.type("application/json");
		response.header("Access-Control-Allow-Headers", "*");
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
		catch (Exception e){
		    return "{\"signedin\": \"false\"}";
        }
		return "{\"signedin\": \"true\"}";

	}

	private String invite(Request request, Response response) {

		response.type("application/json");
		response.header("Access-Control-Allow-Headers", "*");
		//@TODO add DB connection
		String user = request.queryParams("user");
		//query DB for user
		//store possible users in ArrayList
		ArrayList<String> possibleUsers = new ArrayList<>();
		Gson gson = new Gson();
		possibleUsers.add(user);
		return gson.toJson(possibleUsers);
	}

	private String sendInvite(Request request, Response response) {

		response.type("application/json");
		response.header("Access-Control-Allow-Headers", "*");
		//@TODO add DB connection
		String user = request.queryParams("to");
		String fromUser = request.queryParams("from");
		/*
		UserInvite userInvite = new UserInvite();
		try 
		{
			userInvite.invitedUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return "{\"invitedUser\": \"false\"}";
		}
        catch (Exception e){
            return "{\"invitedUser\": \"false\"}";
        }
		 */
        Invitation invite = new Invitation(user,fromUser);
        invites.add(invite);
        System.out.println("sent invite to " + user + " from "  + fromUser);
		return "[{\"inviteFor\":\"" + user + "\"}, {\"from\": \"" + fromUser + "\"}]";

	}

	private String waitingInvite(Request request,Response response){
        response.type("application/json");
        response.header("Access-Control-Allow-Headers", "*");

        String User = request.queryParams("user");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        for(Invitation in:invites){
            if((in.to.equals(User) || in.from.equals(User)) && in.accepted == true){
                System.out.println(User +"[{\"inviteStatus\":\"accepted\"}]");
                return "[{\"inviteStatus\":\"accepted\"}]";
            }
            if(in.to.equals(User)){
                System.out.println(User + "[{\"inviteStatus\":\"not accepted\"},{\"inviteFrom\":\"" + in.from + "\"}]");
                return "[{\"inviteStatus\":\"not accepted\"},{\"inviteFrom\":\"" + in.from + "\"}]";
            }
            //else if(in.from.equals(User) && in.accepted == true){
                //invites.remove(in);
              //  return gson.toJson(gameManager.getGame(in.to,in.from), BanqiPiece[][].class);
            //}
        }
        System.out.println(User +"[{\"inviteStatus\":\"not accepted\"}]");
        return "[{\"inviteStatus\":\"not accepted\"}]";

    }


	private String acceptInvite(Request request, Response response) {
		response.type("application/json");
		response.header("Access-Control-Allow-Headers", "*");

		//for now we create UserBean users from the name given but eventually we will pull users from DB
		String user = request.queryParams("user");
        UserObject userObj = new UserObject();

		for(Invitation i : invites){
		    if(i.to.equals(user)){
		        i.accepted = true;
                UserBean user1 = new UserBean();
                UserBean user2 = new UserBean();

                user1.setNickName(i.to);
                user2.setNickName(i.from);

                gameManager.addGame(user1,user2);
                return "[{\"inviteStatus\":\"accepted\"}]";
            }
        }

		return "[{\"inviteStatus\":\"no invites\"}]";
	}

	private String getGame(Request request, Response response){
        response.type("application/json");
        response.header("Access-Control-Allow-Headers", "*");

        String user1 = request.queryParams("user1");
        String user2 = request.queryParams("user2");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        BanqiBoard b = gameManager.getGame(user1,user2);
        if(b == null){
        	b = gameManager.getGame(user2,user1);
		}

	    return gson.toJson(b);
    }

	private String team(Request request, Response response) {

		response.type("text/plain");

		return name;
	}

}
