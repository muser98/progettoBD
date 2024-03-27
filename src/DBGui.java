import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DBGui extends JFrame{
    private JPanel jp1;
    private JButton connection_btn;
    private JButton delete_btn;
    private JTextArea result_textarea;
    private JTextField db_insertion;
    private JButton select_btn;
    private JButton clear_area_btn;
    private JLabel conn;
    private JButton insert_button;
    private JButton update_button;

    public DBGui(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,600);
        setLayout(new GridLayout());
        this.jp1 = new JPanel();
        jp1.setVisible(true);
        jp1.setLayout(new FlowLayout ());
        this.insert_button= new JButton();
        this.update_button=new JButton();
        this.conn= new JLabel();
        this.clear_area_btn = new JButton();
        this.connection_btn= new JButton();
        this.db_insertion = new JTextField(30);
        this.result_textarea= new JTextArea(40,80);

        connection_btn.setText("Connetti al Database");
        db_insertion.setText("cancella e inserisci");
        insert_button.setText("Insert");
        update_button.setText("Update query");
        clear_area_btn.setText("Svuota l'area dei risultati");
        connection_btn.setText("Connetti al Database");
        conn.setText("Inserisci il nome del database e premi per connettere");

        jp1.add(conn);
        jp1.add(db_insertion);
        jp1.add(connection_btn);
        jp1.add(insert_button);
        jp1.add(update_button);
        jp1.add(delete_btn);
        jp1.add(select_btn);
        jp1.add(clear_area_btn);
        jp1.add(result_textarea);
        add(jp1);
        setVisible(true);
        setSize(1000,900);

        connection_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String dbName = db_insertion.getText();
                if (dbName == "") {
                    conn.setText("Errore nella connessione, riprova.");
                } else {
                    try {
                        connettiDB(dbName);
                    } catch (SQLException e) {
                        conn.setText("Errore nella connessione, riprova.");
                        throw new RuntimeException(e);
                    }
                    conn.setText("Connessione avvenuta con successo! premi per eseguire");
                }
            }
        });
        clear_area_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.setText("");
            }
        });
        select_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("QUERY 2: selezione su due o piu tabelle con condizioni\n" +
                        "trova gli impiegati DIRETTORI che guadagnano un incentivo SUPERIORE ai 20 euro\n\nRISULTATO QUERY:\n----------------------------------------------------------+\n");
                result_textarea.append(" nome\t| cognome\t| incentivo  |\n");
                result_textarea.append("-----------------------------------------------------------|\n");

                String nome = null;
                int incentivo=0;
                String cognome = null;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select c.nome,c.cognome,r.incentivo\n" +
                                    "from reparto r,commesso c " +
                                    "where r.commesso=c.cf and r.incentivo>20;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()){
                        nome = rs.getString("nome");
                        cognome = rs.getString("cognome");
                        incentivo = rs.getInt("incentivo");
                        result_textarea.append("  "+nome+"\t| "+cognome+"\t| "+incentivo+"             |\n");
                        result_textarea.append("----------------------------------------------------------+\n");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        delete_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("INSERTION QUERY: inserimento di un nuovo prodotto nella tabella 'prodotto'\n"+
                        "\n\nRISULTATO PRIMA DELLA ESECUZIONE DELL'INSERIMENTO:\n-----------------------------------------------------------------------------+\n");
                result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                result_textarea.append("-----------------------------------------------------------------------------|\n");
                String codice = null;
                String nome = null;
                float prezzo=0;
                String reparto = null;
                String brand = null;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select * from prodotto;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()) {
                        codice = rs.getString("codice");
                        nome = rs.getString("nome");
                        brand = rs.getString("brand");
                        prezzo = rs.getFloat("prezzo");
                        reparto = rs.getString("reparto");
                        result_textarea.append("  " + codice + "\t| " + nome + "\t| " + prezzo + "\t| " + reparto + "\t| " + brand + "   |\n");
                        System.out.println("  " + codice + "\t| " + nome + "\t| " + prezzo + "\t| " + reparto + "\t| " + brand + "   |\n");
                        result_textarea.append("-----------------------------------------------------------------------------+\n");
                    }
                    String sqlQuery2="delete from progetto_fumetteria.prodotto where codice=171;";
                    Statement st2= con.createStatement();
                    System.out.print(st2.executeUpdate(sqlQuery2));
                    result_textarea.append("\nINSERTION QUERY: inserimento di un nuovo prodotto nella tabella 'prodotto'\n"+
                            "\nRISULTATO DOPO L'ESECUZIONE DELL'INSERIMENTO:\n-----------------------------------------------------------------------------+\n");
                    result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                    result_textarea.append("-----------------------------------------------------------------------------|\n");
                    ResultSet rs2=st.executeQuery(sqlQuery);

                    while(rs2.next()){
                        codice = rs2.getString("codice");
                        nome = rs2.getString("nome");
                        brand = rs2.getString("brand");
                        prezzo = rs2.getFloat("prezzo");
                        reparto = rs2.getString("reparto");
                        result_textarea.append("  "+codice+"\t| "+nome+"\t| "+prezzo+"\t| "+reparto+"\t| "+brand+"   |\n");
                        result_textarea.append("-----------------------------------------------------------------------------+\n");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        insert_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("INSERTION QUERY: inserimento di un nuovo prodotto nella tabella 'prodotto'\n"+
                        "\n\nRISULTATO PRIMA DELLA ESECUZIONE DELL'INSERIMENTO:\n-----------------------------------------------------------------------------+\n");
                result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                result_textarea.append("-----------------------------------------------------------------------------|\n");
                String codice = null;
                String nome = null;
                float prezzo=0;
                String reparto = null;
                String brand = null;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select * from prodotto;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()) {
                        codice = rs.getString("codice");
                        nome = rs.getString("nome");
                        brand = rs.getString("brand");
                        prezzo = rs.getFloat("prezzo");
                        reparto = rs.getString("reparto");
                        result_textarea.append("  " + codice + "\t| " + nome + "\t| " + prezzo + "\t| " + reparto + "\t| " + brand + "   |\n");
                        System.out.println("  " + codice + "\t| " + nome + "\t| " + prezzo + "\t| " + reparto + "\t| " + brand + "   |\n");
                        result_textarea.append("-----------------------------------------------------------------------------+\n");
                    }
                        String sqlQuery2="insert into prodotto values " +
                                "('171','Minato Uzumaki',35,'001', 'Bandai');";
                        Statement st2= con.createStatement();
                        System.out.print(st2.executeUpdate(sqlQuery2));
                        result_textarea.append("\nINSERTION QUERY: inserimento di un nuovo prodotto nella tabella 'prodotto'\n"+
                               "\nRISULTATO DOPO L'ESECUZIONE DELL'INSERIMENTO:\n-----------------------------------------------------------------------------+\n");
                        result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                        result_textarea.append("-----------------------------------------------------------------------------|\n");
                        ResultSet rs2=st.executeQuery(sqlQuery);

                        while(rs2.next()){
                            codice = rs2.getString("codice");
                            nome = rs2.getString("nome");
                            brand = rs2.getString("brand");
                            prezzo = rs2.getFloat("prezzo");
                            reparto = rs2.getString("reparto");
                            result_textarea.append("  "+codice+"\t| "+nome+"\t| "+prezzo+"\t| "+reparto+"\t| "+brand+"   |\n");
                            result_textarea.append("-----------------------------------------------------------------------------+\n");
                        }
                    } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        update_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("INSERTION QUERY: inserimento di un nuovo prodotto nella tabella 'prodotto'\n"+
                        "\n\nRISULTATO PRIMA DELLA ESECUZIONE DELL'UPDATE:\n-----------------------------------------------------------------------------+\n");
                result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                result_textarea.append("-----------------------------------------------------------------------------|\n");
                String codice = null;
                String nome = null;
                float prezzo=0;
                String reparto = null;
                String brand = null;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select * from prodotto;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()) {
                        codice = rs.getString("codice");
                        nome = rs.getString("nome");
                        brand = rs.getString("brand");
                        prezzo = rs.getFloat("prezzo");
                        reparto = rs.getString("reparto");
                        result_textarea.append("  " + codice + "\t| " + nome + "\t| " + prezzo + "\t| " + reparto + "\t| " + brand + "   |\n");
                        System.out.println("  " + codice + "\t| " + nome + "\t| " + prezzo + "\t| " + reparto + "\t| " + brand + "   |\n");
                        result_textarea.append("-----------------------------------------------------------------------------+\n");
                    }
                    String sqlQuery2="UPDATE prodotto set nome ='Minato Namikaze' where codice=171";
                    Statement st2= con.createStatement();
                    System.out.print(st2.executeUpdate(sqlQuery2));
                    result_textarea.append("\nUPDATE QUERY: inserimento di un nuovo prodotto nella tabella 'prodotto'\n"+
                            "\nRISULTATO DOPO L'ESECUZIONE DELL'UPDATE:\n-----------------------------------------------------------------------------+\n");
                    result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                    result_textarea.append("-----------------------------------------------------------------------------|\n");
                    ResultSet rs2=st.executeQuery(sqlQuery);

                    while(rs2.next()){
                        codice = rs2.getString("codice");
                        nome = rs2.getString("nome");
                        brand = rs2.getString("brand");
                        prezzo = rs2.getFloat("prezzo");
                        reparto = rs2.getString("reparto");
                        result_textarea.append("  "+codice+"\t| "+nome+"\t| "+prezzo+"\t| "+reparto+"\t| "+brand+"   |\n");
                        result_textarea.append("-----------------------------------------------------------------------------+\n");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private void connettiDB(String DBName) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/"+DBName+"?user=root&password=m51098guerrera";
        Connection con = DriverManager.getConnection(url);
    }

}
