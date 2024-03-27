import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DBGui extends JFrame{
    private JPanel jp1;
    private JButton connection_btn;
    private JButton query2_btn;
    private JButton query1_btn;
    private JButton query6_btn;
    private JButton query5_btn;
    private JButton query3_btn;
    private JTextArea result_textarea;
    private JTextField db_insertion;
    private JButton query4_btn;
    private JButton clear_area_btn;
    private JButton query7_btn;
    private JLabel conn;
    private JButton insert_button;
    private JButton update_Button;

    public DBGui(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,600);
        setLayout(new GridLayout());
        this.jp1 = new JPanel();
        jp1.setVisible(true);
        jp1.setLayout(new FlowLayout ());
        this.query1_btn = new JButton();
        this.query2_btn = new JButton();
        this.query3_btn = new JButton();
        this.query4_btn = new JButton();
        this.query5_btn = new JButton();
        this.query6_btn = new JButton();
        this.query7_btn = new JButton();
        this.insert_button= new JButton();
        this.update_Button=new JButton();
        this.conn= new JLabel();
        this.clear_area_btn = new JButton();
        this.connection_btn= new JButton();
        this.db_insertion = new JTextField(30);
        this.result_textarea= new JTextArea(40,80);

        connection_btn.setText("Connetti al Database");
        db_insertion.setText("cancella e inserisci");
        query1_btn.setText("Query 1");
        query2_btn.setText("Query 2");
        query3_btn.setText("Query 3");
        query4_btn.setText("Query 4");
        query5_btn.setText("Query 5");
        query6_btn.setText("Query 6");
        query7_btn.setText("Query 7");
        update_Button.setText("Update query");
        insert_button.setText("Insert query");
        clear_area_btn.setText("Svuota l'area dei risultati");
        connection_btn.setText("Connetti al Database");
        conn.setText("Inserisci il nome del database e premi per connettere");

        jp1.add(connection_btn);
        jp1.add(db_insertion);
        jp1.add(conn);
        jp1.add(query1_btn);
        jp1.add(query2_btn);
        jp1.add(query3_btn);
        jp1.add(query4_btn);
        jp1.add(query5_btn);
        jp1.add(query6_btn);
        jp1.add(insert_button);
        jp1.add(update_Button);
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


        query1_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("QUERY 1: selezione su attributo di una tabella con AND e OR\n"+
                        "trova i prodotti con un prezzo base compreso tra 20 e 40, OPPURE che si trovano nel reparto 003\n\nRISULTATO QUERY:\n-----------------------------------------------------------------------------+\n");
                result_textarea.append(" codice\t| nome\t\t| prezzo\t| reparto\t| brand\t          |\n");
                result_textarea.append("-----------------------------------------------------------------------------|\n");
                String codice = null;
                String nome = null;
                float prezzo=0;
                String reparto = null;
                String brand = null;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select *\n" +
                            "from prodotto p\n" +
                            "where (p.prezzo<20 and p.prezzo>45) or p.reparto=\"003\"\n" +
                            "order by p.nome;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()){
                        codice = rs.getString("codice");
                        nome = rs.getString("nome");
                        brand = rs.getString("brand");
                        prezzo = rs.getFloat("prezzo");
                        reparto = rs.getString("reparto");
                        result_textarea.append("  "+codice+"\t| "+nome+"\t| "+prezzo+"\t| "+reparto+"\t| "+brand+"   |\n");
                        result_textarea.append("-----------------------------------------------------------------------------+\n");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        clear_area_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.setText("");
            }
        });
        query2_btn.addActionListener(new ActionListener() {
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
                                    "from reparto r,commesso c\n" +
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
        query3_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("QUERY 3: selezione aggregata su tutti i valori \n" +
                        "calcola la SOMMA di tutti gli stipendi degli impiegati\n\nRISULTATO QUERY:\n");
                result_textarea.append("stipendiTotali\n");
                int stipendiTotali=0;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select  sum(c.stipendioTot)as stipendiTotali\n" +
                            "from commesso c;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()){
                        stipendiTotali = rs.getInt("stipendiTotali");
                        result_textarea.append("  "+stipendiTotali+"\n");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        query4_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("QUERY 4: selezione aggregata su raggruppamenti\n" +
                        "calcola la somma degli stipendi per ogni reparto\n\nRISULTATO QUERY:\n");
                result_textarea.append("reparto\t| stipendi reparto |\n------------|-------------+\n");
                String reparto=null;
                int stipendirep=0;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select l.reparto,sum(c.stipendioTot)as stipendiRep\n" +
                                    "from lavoro l, commesso c\n" +
                                    "where l.commesso=c.cf\n" +
                                    "group by l.reparto;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()){
                        reparto = rs.getString("reparto");
                        stipendirep= rs.getInt("stipendiRep");
                        result_textarea.append("  "+reparto+"\t| "+stipendirep+"\t    |\n");
                        result_textarea.append("--------------------------+\n");

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        query5_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("QUERY 5: selezione aggregata su raggruppamenti con condizione\n" +
                        "-- trova i reparti la cui somma degli stipendi supera i 3mila euro\n\nRISULTATO QUERY:\n------------------------------------------------+\n");
                result_textarea.append(" codice\t| nome\t\t| somma\t |\n");
                result_textarea.append("------------------------------------------------+\n");

                String codice = null;
                int somma=0;
                String nome = null;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery="select r.codice,r.nome,sum(c.stipendioTot) as sommaStipendiRep\n" +
                            "from reparto r,lavoro l,commesso c\n" +
                            "where r.codice=l.reparto and c.cf=l.commesso\n" +
                            "group by r.codice,r.nome\n" +
                            "having sum(c.stipendioTot)>3000;";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()){
                        nome = rs.getString("nome");
                        codice = rs.getString("codice");
                        somma = rs.getInt("sommaStipendiRep");
                        if(nome.equals("Pokemon TCG")){
                            result_textarea.append("  "+codice+"\t| "+nome+"\t| "+somma+"\t|\n");
                            result_textarea.append("------------------------------------------------+\n");
                        }else {
                            result_textarea.append("  " + codice + "\t| " + nome + "\t\t| " + somma + "\t|\n");
                            result_textarea.append("------------------------------------------------+\n");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        query6_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result_textarea.append("QUERY 6:  una selezione con l'uso appropriato dal doppio not exists\n" +
                        "seleziona gli impiegati che lavorano in tutti i reparti\n\nRISULTATO QUERY:\n--------------------------------------------------------------------------------------------------------------+\n");
                result_textarea.append("  cf\t| nome\t| cognome\t| civico\t| cap\t| via\t\t| stipendio\t| stipendioTot     |\n");
                result_textarea.append("------------|-----------+-----------+-----------+----------+-----------------------+-----------+-------------+\n");

                String cf = null;
                int civico=0;
                String nome = null;
                String cognome=null;
                String cap=null;
                String via=null;
                int stipendio=0;
                int stipendioTot=0;
                String url = "jdbc:mysql://localhost:3306/progetto_fumetteria?user=root&password=m51098guerrera";
                try {
                    String sqlQuery=
                            "select * " +
                            "from commesso c " +
                            "where not exists (select * " +
                            "from reparto r\n" +
                            "                where not exists( select *\n" +
                            " from lavoro l\n" +
                            "                                where l.reparto=r.codice and\n" +
                            "                                l.commesso=c.cf));";
                    Connection con = DriverManager.getConnection(url);
                    Statement st= con.createStatement();
                    ResultSet rs= st.executeQuery(sqlQuery);
                    while(rs.next()){
                        nome = rs.getString("nome");
                        cognome = rs.getString("cognome");
                        cf = rs.getString("cf");
                        civico= rs.getInt("civico");
                        via = rs.getString("via");
                        stipendio=rs.getInt("stipendioBase");
                        stipendioTot=rs.getInt("stipendioTot");
                        cap=rs.getString("cap");
                        result_textarea.append("  "+cf+"\t| "+nome+"\t| "+cognome+"\t| "+civico+"\t| "+cap+"\t| "+via+"\t| "+stipendio+"\t | "+stipendioTot+"                  |\n");
                        result_textarea.append("--------------------------------------------------------------------------------------------------------------+\n");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
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
        update_Button.addActionListener(new ActionListener() {
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
