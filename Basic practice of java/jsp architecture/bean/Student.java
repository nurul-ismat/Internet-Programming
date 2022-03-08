/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author GATES
 */
public class Student {

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the matriks
     */
    public String getMatriks() {
        return matriks;
    }

    /**
     * @param matriks the matriks to set
     */
    public void setMatriks(String matriks) {
        this.matriks = matriks;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
    private String name;
    private String matriks;
    private int age;
}
