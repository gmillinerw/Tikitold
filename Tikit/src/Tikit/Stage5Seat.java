package Tikit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Stage5Seat extends absGUI {

    int userID;
    String movieSelected;
    int tandaSelected;
    int errorCount;
    User user;
    Hall hall;
    Configuration configuration;
    Stage3Home Stage3Home;
    JButton[] seatButton;
    //    this.movieImage = new ImageIcon[3];
    ImageIcon[] movieImage = {
            new ImageIcon(getClass().getResource("/Media/FreeSeat.png")),
            new ImageIcon(getClass().getResource("/Media/OccupiedSeat.png")),
            new ImageIcon(getClass().getResource("/Media/selectedSeat.png"))
    };
    int FreeSeat = 0;
    int OccupiedSeat = 1;
    int selectedSeat = 2;
//        ArrayList<JButton> seatButton = new ArrayList<>();
//        ArrayList<ImageIcon> seaticon = new ArrayList<>();
//        seaticon.add(0, new ImageIcon("/Media/FreeSeat.png"));
//        seaticon.add(1, new ImageIcon("/Media/OccupiedSeat.png"));
//        seaticon.add(2, new ImageIcon("/Media/selectedSeat.png"));
    JLabel listSeats = new JLabel("No seats has been selected");
    JLabel totalCost = new JLabel("The toal cost is: 0");
    Stage5Seat(User user, Configuration configuration) {
        this.user = user;
        this.Stage3Home = new Stage3Home(user);
        this.configuration = configuration;

        System.out.println("\n");
        System.out.println("The number of movies is: " + this.configuration.getNumMovies());
        System.out.println("The number of tandas is: " + this.configuration.getNumTandas());
        System.out.println("The number of seats is: " + this.configuration.getNumSeats());
        System.out.println("\n");
        System.out.println("The User Setting UserID is: " + this.user.getUserID());
        System.out.println("The User Setting fName is: " + this.user.getfName());
        System.out.println("The User Setting lName is: " + this.user.getlName());
        System.out.println("The User Setting BankCard is: " + this.user.getBankCard());
        System.out.println("The User Setting EMail is: " + this.user.getEMail());
        System.out.println("The User Setting UserName is: " + this.user.getUserName());
        System.out.println("The User Setting Pasword is: " + this.user.getPasword());
        System.out.println("The User Setting Login is: " + this.user.isLogin());
        System.out.println("The User Setting Admin is: " + this.user.isAdmin());
        System.out.println("\n");
    }

    void getMainDisplay(String movieSelected, int tandaSelected) {
        System.out.println("METHOD INFO: " + Arrays.toString(Thread.currentThread().getStackTrace()));
        setGUI("Chose Setas");
        hall = cacheHall(movieSelected, tandaSelected);
        this.seatButton = new JButton[configuration.getNumSeats()];

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(7, 7, 5, 5));
        topLeftPanel.setOpaque(false);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new GridLayout(7, 7, 5, 5));
        topRightPanel.setOpaque(false);

        for (int i = 0; i < configuration.getNumSeats(); i++) {
            seatButton[i] = new JButton(Integer.toString((i + 1)));
            seatButton[i].setFont(new Font("Ariel", Font.BOLD, 15));
            seatButton[i].setHorizontalTextPosition(AbstractButton.CENTER);
            seatButton[i].setVerticalTextPosition(AbstractButton.BOTTOM);
            seatButton[i].setIcon(movieImage[FreeSeat]);
            if (i < configuration.getNumSeats() / 2) {
                topLeftPanel.add(seatButton[i]);
            } else {
                topRightPanel.add(seatButton[i]);
            }
            seatButton[i].addActionListener((ActionEvent evt) -> {
                System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
                seatButtonEvt(evt);
            });
        }
        topPanel.setLayout(new GridLayout(1, 2, 30, 15));
        topPanel.setOpaque(false);
        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);

        JButton selectButton = new JButton("select");
        selectButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            Stage3Home.getMainDisplayDSP();
            mainFrame.dispose();
        });

        JButton BackButton = new JButton("Back");
        BackButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            Stage4Movie Stage4Movie = new Stage4Movie(user, configuration);
            Stage4Movie.getMainDisplay();
            mainFrame.dispose();
        });

        listSeats.setForeground(Color.WHITE);
        listSeats.setFont(new Font("Ariel", Font.BOLD, 20));
        totalCost.setForeground(Color.WHITE);
        totalCost.setFont(new Font("Ariel", Font.BOLD, 20));

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new GridLayout(2, 1, 20, 5));
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.add(listSeats);
        bottomRightPanel.add(totalCost);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new GridLayout(1, 2, 20, 5));
        bottomLeftPanel.setOpaque(false);
        bottomLeftPanel.add(BackButton);
        bottomLeftPanel.add(selectButton);

        bottomPanel.add(bottomRightPanel);
        bottomPanel.add(bottomLeftPanel);
        basePanel.setLayout(new GridLayout(2, 5, 40, 5));
        basePanel.add(topPanel);
        basePanel.add(bottomPanel);
        setGUI(new JLabel(new ImageIcon(getClass().getResource("/Media/Seats.jpg"))));
        mainFrame.setLocationRelativeTo(null);

        setseatIcons();
    }

    void setseatIcons() {
        String query;
        query = "SELECT SeatNum FROM ROOT.SEATS where movieID = " + hall.getMovieID()
                + " and Tanda = " + hall.getTandaNumber() + " and isBooked = True";
        ArrayList<Integer> OccupiedSeatList = getintListQuerry(query);
        for (int i = 0; i < OccupiedSeatList.size(); i++) {
            seatButton[OccupiedSeatList.get(i) - 1].setIcon(movieImage[OccupiedSeat]);
        }

        query = "SELECT seatNum FROM ROOT.RESERVATIONS where movieID = " + hall.getMovieID()
                + " and Tanda = " + hall.getTandaNumber() + " and BookedBy = '" + user.getUserName() + "'";
        ArrayList<Integer> selectedSeatList = getintListQuerry(query);
        for (int i = 0; i < selectedSeatList.size(); i++) {
            seatButton[selectedSeatList.get(i) - 1].setIcon(movieImage[selectedSeat]);
        }

        ArrayList<Integer> RemovedSeatList = new ArrayList<>(selectedSeatList);
        RemovedSeatList.removeAll(OccupiedSeatList);
        RemovedSeatList.forEach((seat) -> {
            System.out.println(seat);
        });
    }

    private void seatButtonEvt(ActionEvent evt) {
        System.out.println("METHOD INFO: " + Arrays.toString(Thread.currentThread().getStackTrace()));
        String query;
        int DSPSeatNumber = Integer.parseInt(evt.getActionCommand());
        int ArraySeatNumber = DSPSeatNumber - 1;
        query = "SELECT isBooked FROM ROOT.SEATS where movieID = " + hall.getMovieID()
                + " and Tanda = " + hall.getTandaNumber() + " and seatNum = " + DSPSeatNumber;

        if (!booleanQuerry(query)) {
            query = "SELECT ReservationID FROM ROOT.RESERVATIONS where movieID = " + hall.getMovieID() + " and Tanda = "
                    + hall.getTandaNumber() + " and seatNum = " + DSPSeatNumber + " and BookedBy = '" + user.getUserName() + "'";
            if (intQuerry(query) > 0) {

                ExeDML("DELETE FROM RESERVATIONS WHERE movieID=" + hall.getMovieID() + " and tanda=" + hall.getTandaNumber()
                        + " and seatNum=" + DSPSeatNumber + " and BookedBy='" + user.getUserName() + "'");
                seatButton[ArraySeatNumber].setIcon(movieImage[FreeSeat]);
                System.out.println("UNselected Seat #: " + DSPSeatNumber);
            } else {
                ExeDML("INSERT INTO RESERVATIONS (SEATID,movieID, tanda, seatNum, BookedBy) VALUES (" + 99 + "," + hall.getMovieID() + ","
                        + hall.getTandaNumber() + "," + DSPSeatNumber + ",'" + user.getUserName() + "')");
                System.out.println("Selected Seat #: " + DSPSeatNumber);
                seatButton[ArraySeatNumber].setIcon(movieImage[selectedSeat]);
            }
            tallySeats(evt);
        } else {
            errorCount++;
            seatButton[ArraySeatNumber].setIcon(movieImage[OccupiedSeat]);
            System.out.println("Seat: " + DSPSeatNumber + " ...  " + errorCount
                    + " times user tryed to select a seat that was ocupied");
        }
    }

    public void tallySeats(ActionEvent evt) {
        System.out.println("METHOD INFO: " + Arrays.toString(Thread.currentThread().getStackTrace()));

        String query = "SELECT seatNum FROM ROOT.RESERVATIONS where movieID = " + hall.getMovieID() + " and Tanda = "
                + hall.getTandaNumber() + " and BookedBy = '" + user.getUserName() + "'";
        ArrayList<String> takenSeats = ExeDDL(query);

        StringBuilder takenSeatsSB = new StringBuilder();
        for (String Seat : takenSeats) {
            takenSeatsSB.append(Seat);
            takenSeatsSB.append(", ");
        }
        String message = "Seats seleted: " + takenSeatsSB.toString();
        listSeats.setText(message);
        System.out.println(message);

        message = "Total cost: " + takenSeats.size() * configuration.getPrice();
        totalCost.setText(message);
        System.out.println(message);
    }
}
