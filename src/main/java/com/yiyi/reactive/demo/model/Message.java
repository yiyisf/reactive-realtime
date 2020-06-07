package com.yiyi.reactive.demo.model;

public class Message {
    private String from;
    private String to;
    private String message;

    public Message() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (from != null ? !from.equals(message1.from) : message1.from != null) return false;
        if (to != null ? !to.equals(message1.to) : message1.to != null) return false;
        return message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + message.hashCode();
        return result;
    }
}
