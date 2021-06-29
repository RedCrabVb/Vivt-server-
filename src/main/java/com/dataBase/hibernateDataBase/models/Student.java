package com.dataBase.hibernateDataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStudent;
    private String token;
    private String mail;
    private String password;
    private String surname;
    private String name;
    private String patronymic;
    private String grade_book_number;
    private int groups_id;

    public Student() {

    }

    public Student(String token,
                   String mail,
                   String password,
                   String surname,
                   String name,
                   String patronymic,
                   String grade_book_number,
                   int groups_id) {
        this.token = token;
        this.mail = mail;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.grade_book_number = grade_book_number;
        this.groups_id = groups_id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public String getToken() {
        return token;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getGrade_book_number() {
        return grade_book_number;
    }

    public int getGroups_id() {
        return groups_id;
    }


    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", token='" + token + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", grade_book_number='" + grade_book_number + '\'' +
                ", groups_id=" + groups_id +
                '}';
    }

}
