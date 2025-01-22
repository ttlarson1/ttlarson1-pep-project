package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.Connector;
import Service.Connector2;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    Connector messageService;
    Connector2 accountService;
    
    public SocialMediaController(){
        this.messageService = new Connector();
        this.accountService = new Connector2();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages", this::postMessage);
        app.get("/messages", this::getAllmessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessageByID);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    private void register(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addacc = accountService.insertUser(acc);
        if(addacc!=null){
            ctx.json(mapper.writeValueAsString(addacc));
        }else{
            ctx.status(400);
        }
    }

     private void login(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addacc = accountService.validateLogin(acc);
        if(addacc!=null){
            ctx.json(mapper.writeValueAsString(addacc));
        }else{
            ctx.status(401);
        }
    }

    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message mes = mapper.readValue(ctx.body(), Message.class);
        Message addmes = messageService.insertMessage(mes);
        if(addmes!=null){
            ctx.json(mapper.writeValueAsString(addmes));
        }else{
            ctx.status(400);
        }
    }

    private void getAllmessages(Context ctx) {
        List<Message> messages = messageService.getAllmessages();
        ctx.json(messages);
    }

    private void getMessageByID(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message addmes = messageService.findMessageById(messageId);
        if(addmes != null){
            ctx.json(addmes);
        } else{
            ctx.json("");
        }
    }

    private void deleteMessageByID(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message addmes = messageService.findMessageById(messageId);
        if(addmes != null){
            ctx.json(addmes);
        } else{
            ctx.json("");
        }
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        String text = mapper.readTree(ctx.body()).get("message_text").asText();
        Message updatedMessage = messageService.updateMessageById(text, messageId);
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }
    
    private void getAllMessagesFromUser(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.allPostsByUserId(messageId);
        ctx.json(messages);
    }
}