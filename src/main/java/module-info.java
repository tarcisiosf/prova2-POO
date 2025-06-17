module br.com.aula.prova2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens br.com.aula.prova2 to javafx.fxml;
    exports br.com.aula.prova2;
}