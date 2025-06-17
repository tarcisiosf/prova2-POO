module br.com.aula.prova2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports br.com.aula.prova2;

    opens br.com.aula.prova2.controller to javafx.fxml;
    opens br.com.aula.prova2.view       to javafx.fxml;
}
