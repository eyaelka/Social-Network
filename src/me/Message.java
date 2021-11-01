package me;

import sun.security.x509.X500Name;

public class Message {
    private Membre sender;
    private String content;
    private int likes;



    public Message(Membre sender, String content, int likes)
    {
        this.sender = sender;
        this.content = content;
        this.likes=0;
    }

    public Membre getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
    int getLikes()
    {
        return this.likes;
    }
    public String toString()
    {
        return " From: "+this.getSender().getNom()+" "+this.getSender().getPrenom()+"\n\t"+this.getContent()+"\t"+"Likes:"+this.getLikes()+"\n";
    }



}
}