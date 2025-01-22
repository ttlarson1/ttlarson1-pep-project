package Service;

import DAO.Thingy;
import Model.Message;

import java.util.List;

public class Connector {
    private Thingy messageDAO;

    public Connector(){
        messageDAO = new Thingy();
    }

    public Connector(Thingy t){
        this.messageDAO = t;
    }

    public List<Message> getAllmessages(){
        return messageDAO.getAllmessages();
    }

    public Message insertMessage(Message mes){
        return messageDAO.insertMessage(mes);
    }

    public Message findMessageById(int id){
        return messageDAO.findMessageById(id);
    }

    public Message deleteMessageById(int id){
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessageById(String text, int id){
        return messageDAO.updateMessageById(text, id);
    }

    public List<Message> allPostsByUserId(int id){
        return messageDAO.allPostsByUserId(id);
    }
}
