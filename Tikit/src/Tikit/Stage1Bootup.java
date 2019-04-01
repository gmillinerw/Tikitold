package Tikit;

import java.util.*;

public class Stage1Bootup extends absGUI {

    Configuration configuration = new Configuration();

    void startProgram() {
        Stage2login DSP1_login = new Stage2login();
        dropTables();
        CR8ConfigurationTable();
        setConfiguration();
        this.configuration = cacheConfiguration();
        CR8UserTable();
        CR8SeatsTable();
        CR8ReservationsTable();
        DSP1_login.getMainDisplay();
        CR8Users();
        CR8MovieTable();
        CR8Movies();
        cr8Seats(.9F, .3F, cacheConfiguration());

//        String Querry = "SELECT seatID,movieID,Tanda,seatNum,isBooked,BookedBy FROM ROOT.SEATS";
//        Map<String, List< Object>> map = getintListQuerry(Querry);
//        map.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    void dropTables() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        ArrayList<String> querryResults;
        String query = "select CAST(TABLENAME as varchar(32)) from SYS.SYSTABLES where tabletype <> 'S'";
        querryResults = ExeDDL(query);
        for (String tabeName : querryResults) {
            System.out.println("Dropping table: " + tabeName);
            System.out.println((ExeDML("DROP TABLE ROOT." + tabeName) == 0) ? tabeName + "  Table was drop" : "Fail to drop table " + tabeName);
        }
    }

    void CR8ConfigurationTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE ROOT.Configuration\n\t\t"
                + "(property VARCHAR(15) NOT NULL,\n\t\t"
                + "setting INTEGER NOT NULL)";
        System.out.println((ExeDML(query) == 0) ? "User table was created" : "[Error] USERS Table was not Created");
    }

    void setConfiguration() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String[] property = {"numMovies", "numTandas", "numSeats", "price", "Property5", "Property6"};
        int[] setting = {6, 5, (6 * 7 * 2), 6000, 0, 0};
        for (int index = 0; index < property.length; index++) {
            String query = "INSERT INTO Configuration (Property, setting)\n\t\t"
                    + "VALUES('" + property[index] + "'," + setting[index] + ")";
            System.out.println((ExeDML(query) == 0) ? "User was created" : "[Error] USER was not Created");
        }
    }

    void CR8UserTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE ROOT.USERS\n\t\t"
                + "(userID INTEGER not null primary key\n\t\t"
                + "GENERATED ALWAYS AS IDENTITY (START WITH 100, INCREMENT BY 1),\n\t\t"
                + "FNAME VARCHAR(15) DEFAULT 'First Name' NOT NULL,\n\t\t"
                + "lName VARCHAR(15) DEFAULT 'Last Name' NOT NULL,\n\t\t"
                + "EMail VARCHAR(25) DEFAULT 'some@one.Com' NOT NULL UNIQUE,\n\t\t"
                + "username VARCHAR(25) DEFAULT 'some@one.Com' NOT NULL,\n\t\t"
                + "password VARCHAR(10) DEFAULT 'secreat' NOT NULL,\n\t\t"
                + "BankCard VARCHAR(25) DEFAULT '4444333322221111' NOT NULL,\n\t\t"
                + "isLogin BOOLEAN DEFAULT false NOT NULL,\n\t\t"
                + "isAdmin BOOLEAN DEFAULT true NOT NULL,\n\t\t"
                + "Token INTEGER)";

        System.out.println((ExeDML(query) == 0) ? "User table was created" : "[Error] USERS Table was not Created");
    }

    void CR8Users() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "INSERT INTO Users (FNAME, lName, EMail,username, password, BankCard, isLogin,isAdmin)\n\t\t"
                + "VALUES('Guilder','Milliner','guilderw@gmail.com','guilderw@gmail.com','123','444333222111',false,true)";
        System.out.println((ExeDML(query) == 0) ? "User was created" : "[Error] USER was not Created");

        query = "INSERT INTO Users (FNAME, lName, EMail,username, password, BankCard, isLogin, isAdmin)\n\t\t"
                + "VALUES ('Glennmar','Milliner','glennmarm@gmail.com','glennmarm@gmail.com','123','444333222111',false,true)";
        System.out.println((ExeDML(query) == 0) ? "User wascreated" : "[Error] USER was not Created");

        query = "INSERT INTO Users (FNAME, lName, EMail,username, password, BankCard, isLogin, isAdmin)\n\t\t"
                + "VALUES ('Mauricio','Barquero','maubarqueror@gmail.com','maubarqueror@gmail.com','123','444333222111',false,true)";
        System.out.println((ExeDML(query) == 0) ? "User was created" : "[Error] USER was not Created");
    }

    void CR8SeatsTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE ROOT.SEATS\n\t\t"
                + "(seatID INTEGER not null primary key\n\t\t"
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n\t\t"
                + "movieID INTEGER NOT NULL,\n\t\t"
                + "Tanda INTEGER NOT NULL,\n\t\t"
                + "seatNum INTEGER not null,\n\t\t"
                + "isBooked BOOLEAN DEFAULT false NOT NULL,\n\t\t"
                + "BookedBy VARCHAR(25))";
        System.out.println((ExeDML(query) == 0) ? "SEATS table was created" : "[Error] SEATS Table was not Created");
    }

    void CR8ReservationsTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE ROOT.Reservations\n\t\t"
                + "(ReservationID INTEGER not null primary key\n\t\t"
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n\t\t"
                + "seatID INTEGER not null,\n\t\t"
                + "seatNum INTEGER not null,\n\t\t"
                + "movieID INTEGER NOT NULL,\n\t\t"
                + "Tanda INTEGER NOT NULL,\n\t\t"
                + "BookedBy VARCHAR(25) NOT NULL)";
        System.out.println((ExeDML(query) == 0) ? "SEATS table was created" : "[Error] SEATS Table was not Created");
    }

    void CR8MovieTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE ROOT.Movies\n\t\t"
                + "(MovieID INTEGER not null primary key\n\t\t"
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n\t\t"
                + "MovieName VARCHAR(20) DEFAULT 'Movie Name' NOT NULL,\n\t\t"
                + "iconImageLocation VARCHAR(75) DEFAULT 'Relative URL' NOT NULL)";
        System.out.println((ExeDML(query) == 0) ? "Movie table was created" : "[Error] Movie Table was not Created");
    }

    void CR8Movies() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        String[] movieName = {"BABY DRIVER", "INTERSTELLAR", "LOGAN", "THE DARK KNIGHT", "THE GODFATHER", "WONDER WOMAN"};
        String[] iconImageLocation = {"/Media/BABY DRIVER.png", "/Media/INTERSTELLAR.png", "/Media/LOGAN.png", "/Media/THE DARK KNIGHT.png", "/Media/THE GODFATHER", "/Media/WONDER WOMAN.png"};
        String query;
        for (int index = 0; index < movieName.length; index++) {
            query = "INSERT INTO movies (MovieName, iconImageLocation)\n\t\t"
                    + "VALUES ('" + movieName[index] + "','\"" + iconImageLocation[index] + "\"')";
            System.out.println((ExeDML(query) == 0) ? "Movie table was created" : "[Error] Movie Table was not Created");
        }
    }

    //maxPCT: is the maximum persentave of seats that will be randamise
    //minPCT: is the minumun persentave of seats that will be randamise
    void cr8Seats(float maxPCT, float minPCT, Configuration Configuration) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        int seatCoaunter = 0;
        int numSeats = Configuration.getNumSeats();
        Map<String, List<Integer>> Hall = new HashMap<>();
        Hall.put("MovieList", new ArrayList<>());
        Hall.put("TandaList", new ArrayList<>());
        Hall.put("SeatList", new ArrayList<>());

        for (int M = 1; M < 1 + Configuration.getNumMovies(); M++) {
            for (int T = 1; T < 1 + Configuration.getNumTandas(); T++) {
                for (int S = 1; S < 1 + numSeats; S++) {
                    Hall.get("MovieList").add(M);
                    Hall.get("TandaList").add(T);
                    Hall.get("SeatList").add(S);
                    seatCoaunter++;
                }
            }
        }

        String query = "INSERT INTO Seats (movieID, tanda, seatNum, isBooked) VALUES (?, ?, ?, false)";
        ExeBulkPreparedSTMT(query, Hall, seatCoaunter);

        System.out.println(seatCoaunter + " seats shuld have been cretated");
        randSeats(maxPCT, minPCT, Configuration);
    }

    void randSeats(float maxPCT, float minPCT, Configuration Configuration) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        int randCoaunter = 0;
        Utilities Utilities = new Utilities();
        int numSeats = Configuration.getNumSeats();
        Map<String, List<Integer>> Hall = new HashMap<>();
        Hall.put("MovieList", new ArrayList<>());
        Hall.put("TandaList", new ArrayList<>());
        Hall.put("SeatList", new ArrayList<>());

        for (int M = 1; M < 1 + Configuration.getNumMovies(); M++) {
            for (int T = 1; T < 1 + Configuration.getNumTandas(); T++) {
                for (int S = 0; S < (Utilities.GenerateRanNum((int) (numSeats * maxPCT), (int) (numSeats * minPCT))); S++) {
                    Hall.get("MovieList").add(M);
                    Hall.get("TandaList").add(T);
                    Hall.get("SeatList").add(Utilities.GenerateRanNum(numSeats, 1));
                    randCoaunter++;
                }
            }
        }
        String query = "UPDATE Seats SET isBooked=true where movieID = ? and tanda = ? and seatNum=?";
        ExeBulkPreparedSTMT(query, Hall, randCoaunter);
        System.out.println(randCoaunter + " seats shuld have been randamice");
    }
}
