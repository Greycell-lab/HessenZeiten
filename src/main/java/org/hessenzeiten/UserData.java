package org.hessenzeiten;

import java.util.Scanner;

public class UserData {
    private static final Scanner sc = new Scanner(System.in);
    public static boolean getUserData(){
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        try{
            new UserTokenRequest(username, password);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
