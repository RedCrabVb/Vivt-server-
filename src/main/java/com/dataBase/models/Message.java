package com.dataBase.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMessage;
    private int sender_id;
    private boolean is_read;
    private String header;
    private String text;
    private long student_ID;

    public Message() {


    }

    public Message(int sender_id,
                   String header,
                   String text,
                   long student_id) {
        this.sender_id = sender_id;
        this.is_read = false;
        this.header = header;
        this.text = text;
        this.student_ID = student_id;
    }

    public long getIdMessage() {
        return idMessage;
    }

    public int getSender_id() {
        return sender_id;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public long getStudent_ID() {
        return student_ID;
    }
}
