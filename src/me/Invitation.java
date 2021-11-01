package me;

public class Invitation {
	    protected int idinv;
	    protected Membre sender, receiver;

	    public Invitation(Membre sender, Membre receiver) {
	        this.sender = sender;
	        this.receiver = receiver;
	    }

	    public Membre getSender() {
	        return sender;
	    }

	    public Membre getReceiver() {
	        return receiver;
	    }

}
