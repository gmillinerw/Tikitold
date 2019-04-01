package Tikit;

import javax.swing.*;
import java.util.ArrayList;

public abstract class absCaches extends absDAL {

    Configuration cacheConfiguration() {
        Configuration Configuration = new Configuration();
        Configuration.setNumMovies(intQuerry("SELECT setting FROM ROOT.Configuration where property ='numMovies'"));
        Configuration.setNumSeats(intQuerry("SELECT setting FROM ROOT.Configuration where property ='numSeats'"));
        Configuration.setNumTandas(intQuerry("SELECT setting FROM ROOT.Configuration where property ='numTandas'"));
        Configuration.setPrice(intQuerry("SELECT setting FROM ROOT.Configuration where property ='price'"));
        return Configuration;
    }

    User cacheUser(int userID) {
        User user = new User();
        user.setUserID(userID);
        user.setfName(stringQuerry("SELECT fNAME FROM ROOT.USERS where userID = " + userID));
        user.setlName(stringQuerry("SELECT lName FROM ROOT.USERS where userID = " + userID));
        String EMail = stringQuerry("SELECT EMail FROM ROOT.USERS where userID = " + userID);
        user.setUserName(stringQuerry("SELECT UserName FROM ROOT.USERS where userID = " + userID), EMail);
        user.setPasword(stringQuerry("SELECT password FROM ROOT.USERS where userID = " + userID));
        user.setBankCard(stringQuerry("SELECT BankCard FROM ROOT.USERS where userID = " + userID));
        user.setLogin(booleanQuerry("SELECT isLogin FROM ROOT.USERS where userID = " + userID));
        user.setAdmin(booleanQuerry("SELECT isAdmin FROM ROOT.USERS where userID = " + userID));

        return user;
    }

    Hall cacheHall(String movieSelected, int tandaSelected) {
        Hall hall = new Hall();

        hall.setMovieName(movieSelected);
        hall.setTandaNumber(tandaSelected);
        String query = "select MovieID from  ROOT.Movies where MovieName = '"
                + hall.getMovieName() + "'";
        hall.setMovieID(intQuerry(query));
        return hall;
    }

    class Configuration {

        private int numMovies;
        private int numTandas;
        private int numSeats;
        private int price;

        public int getNumMovies() {
            return numMovies;
        }

        public void setNumMovies(int numMovies) {
            this.numMovies = numMovies;
        }

        public int getNumTandas() {
            return numTandas;
        }

        public void setNumTandas(int numTandas) {
            this.numTandas = numTandas;
        }

        public int getNumSeats() {
            return numSeats;
        }

        public void setNumSeats(int numSeats) {
            this.numSeats = numSeats;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

    }

    class User {

        private int userID;
        private String fName;
        private String lName;
        private String EMail;
        private String userName;
        private String pasword;
        private String BankCard;
        private boolean Login;
        private boolean admin;

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getfName() {
            return fName;
        }

        public void setfName(String fName) {
            this.fName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
        }

        public String getlName() {
            return lName;
        }

        public void setlName(String lName) {
            this.lName = lName.substring(0, 1).toUpperCase() + lName.substring(1);
        }

        public String getBankCard() {
            return BankCard;
        }

        public void setBankCard(String BankCard) {

            this.BankCard = BankCard;
        }

        public String getEMail() {
            return EMail;
        }

        public void setEMail(String EMail) {
            while (!(isEmailUnique(EMail) && isEmailSyntaxProper(EMail))) {
                System.out.println("The logic for setEMail is: True");
                EMail = JOptionPane.showInputDialog(null, "Ingrese correo del cliente:", "Agregar.", 3);
            }
            setUserName(EMail);
        }

        boolean isEmailUnique(String EMail) {

            ArrayList<String> querryResults;
            querryResults = ExeDDL("SELECT Email FROM ROOT.USERS where Email='" + EMail + "'");
            boolean logicResult = querryResults.isEmpty();
            System.out.println("The logic result for isEmailUnique is: " + logicResult);
            if (logicResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The Email:\n\t\t " + EMail + "\n Already exist in our database.", "Error.", 0);
            }
            return false;
        }

        boolean isEmailSyntaxProper(String EMail) {
            boolean logicResult = (EMail.indexOf("@") <= EMail.length() - 3 && EMail.indexOf("@") >= 1);
            System.out.println("The logic result for isEmailSyntaxProper is: " + logicResult);
            if (logicResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The format of the Email is invalit.", "Error.", 0);
            }
            return false;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setUserName(String userName, String EMail) {
            this.userName = userName;
            this.EMail = EMail;
        }

        public String getPasword() {
            return pasword;
        }

        public void setPasword(String pasword) {
            this.pasword = pasword;
        }

        public boolean isLogin() {
            return Login;
        }

        public void setLogin(boolean Login) {
            this.Login = Login;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }

    class Hall {

        private int movieID;
        private String movieName;
        private int tandaNumber;

        private int seatNum;

        public int getMovieID() {
            return movieID;
        }

        public void setMovieID(int movieID) {
            this.movieID = movieID;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public int getTandaNumber() {
            return tandaNumber;
        }

        public void setTandaNumber(int tandaNumber) {
            this.tandaNumber = tandaNumber;
        }

        public int getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(int seatNum) {
            this.seatNum = seatNum;
        }

    }
}
