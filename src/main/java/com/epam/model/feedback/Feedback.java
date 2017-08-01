package com.epam.model.feedback;

import java.util.ArrayList;

public class Feedback {
    private ArrayList<Message> messages = new ArrayList<>();

    public void putMessage(Message message){
        messages.add(message);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        return messages != null ? messages.equals(feedback.messages) : feedback.messages == null;
    }

    @Override
    public int hashCode() {
        return messages != null ? messages.hashCode() : 0;
    }

    @Override
    public String toString() {
        final String[] str = {""};
        messages.stream().forEach(m -> {
                str[0] += "=============================================\n";
                str[0] += "SSH: " + m.getCommit().getName() + "\n";
                str[0] += "Message: " + m.getCommit().getFullMessage();
                str[0] += "Result: " + m.getResult() + "\n";
                str[0] += "Status: " + m.getStatus() + "\n";
                str[0] += m.getStatus() == 0 ? "" : m.getOutput() + "\n";
            }
        );
        return str[0];
    }
}
