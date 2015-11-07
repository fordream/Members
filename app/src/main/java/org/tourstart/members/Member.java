package org.tourstart.members;


public class Member {

    public String firstName;
    public String lastName;
    public String gender;
    public String birthDay;
    public String address;
    public String location;
    public int id;

    public Member(String name, String filName, String gen, String day, String home, String loc, int index){
        firstName = name;
        lastName = filName;
        gender = gen;
        birthDay = day;
        address = home;
        location = loc;
        id = index;
    }

}
